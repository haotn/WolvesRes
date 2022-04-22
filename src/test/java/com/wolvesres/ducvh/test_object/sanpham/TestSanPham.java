package com.wolvesres.ducvh.test_object.sanpham;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.dao.HoaDonChiTietDAO;
import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.ducvh.module.DTool;
import com.wolvesres.form.hoadon.HoaDonChiTiet;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelDonViTinh;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.model.ModelHoaDonChiTiet;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelSanPham;

import exceldoing.ExcelGo;

public class TestSanPham {
	private AutoDAO autoDAO;
	private DanhMucDAO dmDAO;
	private DonViTinhDAO dvtDAO;
	private SanPhamDAO spDAO;
	private int forwant = 4;
	private List<ModelDanhMuc> dmList = new ArrayList<ModelDanhMuc>();
	private List<ModelDonViTinh> dvtList = new ArrayList<ModelDonViTinh>();

	// Declare DAO
	@BeforeClass
	public void beforeClass() {
		autoDAO = new AutoDAO();
		dmDAO = new DanhMucDAO();
		dvtDAO = new DonViTinhDAO();
		spDAO = new SanPhamDAO();
		dmList = dmDAO.selectAll();
		dvtList = dvtDAO.selectAll();
	}

	// Data Provider
	@DataProvider(name = "dataThemSP")
	public Object[][] dataThemSP() {
//		ModelSanPham sp = new ModelSanPham(String maSP, String tenSP, float giaBan, String maDanhMuc, String pathAnh, int maDVT,
//				boolean trangThai);
		Object[][] data = new Object[forwant][2];
		for (int i = 0; i < forwant; i++) {
			data[i][0] = new ModelSanPham("SP0"+(Integer.valueOf(autoDAO.AuToSanPham().substring(2))+i), String.format("Tên sp %s", i), 999f, dmList.get(0).getMaDanhMuc(), "", dvtList.get(0).getMaDVT(), true);
			data[i][1] = true; 
		}
		return data;
	}
	
	public Object[][] dataThemSPv2(Object[][] datav1) {
		Object[][] data = new Object[datav1.length][7];
		for (int i = 0; i < datav1.length; i++) {
			ModelSanPham sp = (ModelSanPham) datav1[i][0];
			data[i][0] = sp.getMaSP();
			data[i][1] = sp.getTenSP();
			data[i][2] = sp.getGiaBan();
			data[i][3] = sp.getMaDanhMuc();
			data[i][4] = sp.getPathAnh();
			data[i][5] = sp.getMaDVT();
			data[i][6] = sp.isTrangThai();
		}
		return data;
	}

	@Test(description = "Test ThemSP", dataProvider = "dataThemSP", groups = "dataThemSP", priority = 1)
	public void testThemSP(Object[] data) {
		ModelSanPham spTemp = (ModelSanPham) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;

		spDAO.insert(spTemp);
		ModelSanPham insertTemp = (ModelSanPham) spDAO.selectById(spTemp.getMaSP());
		
		if (spTemp.toString().equalsIgnoreCase(insertTemp.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCapNhatSP")
	public Object[][] dataCapNhatSP() {
		return new Object[][] {
				{ new ModelSanPham("SP016", "Tên sản phẩm cập nhật 1", 997f, dmList.get(0).getMaDanhMuc(), "", dvtList.get(0).getMaDVT(), true), true},
				{ new ModelSanPham("SP017", "Tên sản phẩm cập nhật 2", 922f, dmList.get(0).getMaDanhMuc(), "", dvtList.get(0).getMaDVT(), true), true}
				};
	}
	
	public Object[][] dataCapNhatSPv2(Object[][] datav1) {
//		ModelSanPham sp = new ModelSanPham(String maSP, String tenSP, float giaBan, String maDanhMuc, String pathAnh, int maDVT,
//				boolean trangThai);
		Object[][] data = new Object[datav1.length][7];
		for (int i = 0; i < datav1.length; i++) {
			ModelSanPham sp = (ModelSanPham) datav1[i][0];
			data[i][0] = sp.getMaSP();
			data[i][1] = sp.getTenSP();
			data[i][2] = sp.getGiaBan();
			data[i][3] = sp.getMaDanhMuc();
			data[i][4] = sp.getPathAnh();
			data[i][5] = sp.getMaDVT();
			data[i][6] = sp.isTrangThai();
		}
		return data;
	}

	@Test(description = "Test CapNhatSP", dataProvider = "dataCapNhatSP", priority = 2)
	public void testCapNhatSP(Object[] data) {
		ModelSanPham spTemp = (ModelSanPham) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		
		spDAO.update(spTemp, spTemp.getMaSP());
		ModelSanPham updateTemp = (ModelSanPham) spDAO.selectById(spTemp.getMaSP());
		
		System.err.println(spTemp.getMaSP());
		System.err.println(spTemp);
		System.err.println(updateTemp);
		
		if (spTemp.toString().equals(updateTemp.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataXoaSP")
	public Object[][] dataXoaSP() {
		return new Object[][] { { "SP016", true }};
	}

	@Test(description = "Test XoaSP", dataProvider = "dataXoaSP", priority = 3)
	public void testXoaSP(String maSP, Boolean expected) {
		Boolean actual = false;
		spDAO.delete(maSP);
		ModelSanPham spAfter = null;
		try {
			spAfter = spDAO.selectById(maSP);
		} catch (Exception e) {
			spAfter = null;
		}
		if (spAfter == null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataTimKiemSP")
	public Object[][] dataTimKiemSP() {
		return new Object[][] { { "7 Up", true }, { "Bán", true }, { "Bia", true }, { "Pepsi", false } };
	}

	@Test(description = "Test TimKiemSP", dataProvider = "dataTimKiemSP", priority = 4)
	public void testTimKiemSP(String keyword, Boolean expected) {
		Boolean actual = false;
		List<ModelSanPham> list = spDAO.timkiem(keyword);
		if (list.size() > 0) {
			list.forEach((item) -> {
				System.out.println("Tim thay: " + item);
			});
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCheckForeign")
	public Object[][] dataCheckForeign() {
		return new Object[][] { { "SP03", true }};
	}

	@Test(description = "Test CheckForeign", dataProvider = "dataCheckForeign", priority = 5)
	public void testCheckForeignChiTietLS(String id, Boolean expected) {
		Boolean actual = false;
		ModelSanPham spTemp = spDAO.checkForeignChiTietLS(id);
		if (spTemp != null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "Test CheckForeign", dataProvider = "dataCheckForeign", priority = 6)
	public void testCheckForeignHoaDonCT(String id, Boolean expected) {
		Boolean actual = false;
		ModelSanPham spTemp = spDAO.checkForeignHoaDonCT(id);
		if (spTemp != null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "Test CheckForeign", dataProvider = "dataCheckForeign", priority = 7)
	public void testCheckForeignKho(String id, Boolean expected) {
		Boolean actual = false;
		ModelSanPham spTemp = spDAO.checkForeignKho(id);
		if (spTemp != null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "Test CheckForeign", dataProvider = "dataCheckForeign", priority = 8)
	public void testCheckForeignLichSuGia(String id, Boolean expected) {
		Boolean actual = false;
		ModelSanPham spTemp = spDAO.checkForeignLichSuGia(id);
		if (spTemp != null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 1, 6, "maSP,tenSP,giaBan,maDanhMuc,pathAnh,maDVT,trangThai", dataThemSPv2(dataThemSP()));
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 2, 6, "maSP,tenSP,giaBan,maDanhMuc,pathAnh,maDVT,trangThai", dataCapNhatSPv2(dataCapNhatSP()));
////		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 3, 6, "maSP", dataXoaSP());
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 4, 6, "keyword", dataTimKiemSP());
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 5, 6, "maSP", dataCheckForeign());
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 6, 6, "maSP", dataCheckForeign());
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 7, 6, "maSP", dataCheckForeign());
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 8, 6, "maSP", dataCheckForeign());
	}
	
	//
}
