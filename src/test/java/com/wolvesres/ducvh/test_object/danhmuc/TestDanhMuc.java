package com.wolvesres.ducvh.test_object.danhmuc;

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
import com.wolvesres.dao.HoaDonChiTietDAO;
import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.ducvh.module.DTool;
import com.wolvesres.form.hoadon.HoaDonChiTiet;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.model.ModelHoaDonChiTiet;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelSanPham;

import exceldoing.ExcelGo;

public class TestDanhMuc {
	private AutoDAO autoDAO;
	private DanhMucDAO dmDAO;
	private int forwant = 4;

	// Declare DAO
	@BeforeClass
	public void beforeClass() {
		autoDAO = new AutoDAO();
		dmDAO = new DanhMucDAO();
	}

	// Data Provider
	@DataProvider(name = "dataThemDM")
	public Object[][] dataThemDM() {
//		ModelDanhMuc dm = new ModelDanhMuc(String maDanhMuc, String tenDanhMuc, boolean matHang);
		Object[][] data = new Object[forwant][2];
		for (int i = 0; i < forwant; i++) {
			data[i][0] = new ModelDanhMuc("DM0"+(Integer.valueOf(autoDAO.AuToDanhMuc().substring(2))+i), String.format("Tên danh mục %s", i), true);
			data[i][1] = true; 
		}
		return data;
	}
	
	public Object[][] dataThemDMv2(Object[][] datav1) {
		Object[][] data = new Object[datav1.length][7];
		for (int i = 0; i < datav1.length; i++) {
			ModelDanhMuc sp = (ModelDanhMuc) datav1[i][0];
			data[i][0] = sp.getMaDanhMuc();
			data[i][1] = sp.getTenDanhMuc();
			data[i][2] = sp.isMatHang();
		}
		return data;
	}

	@Test(description = "Test ThemDM", dataProvider = "dataThemDM", groups = "dataThemDM", priority = 1)
	public void ThemDM(Object[] data) {
		ModelDanhMuc dmTemp = (ModelDanhMuc) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;

		dmDAO.insert(dmTemp);
		ModelDanhMuc insertTemp = (ModelDanhMuc) dmDAO.selectById(dmTemp.getMaDanhMuc());
		
		if (dmTemp.toString().equalsIgnoreCase(insertTemp.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCapNhatDM")
	public Object[][] dataCapNhatDM() {
		return new Object[][] {
				{ new ModelDanhMuc("", "Tên danh mục cập nhật 1", true), "DM013", true },
				{ new ModelDanhMuc("", "Tên danh mục cập nhật 2", false), "DM014", true },
				{ new ModelDanhMuc("", "Tên danh mục cập nhật 3", false), "DM015", true },
				{ new ModelDanhMuc("", "Tên danh mục cập nhật 3", true), "DM016", true }};
	}
	
	public Object[][] dataCapNhatDMv2(Object[][] datav1) {
		Object[][] data = new Object[datav1.length][7];
		for (int i = 0; i < datav1.length; i++) {
			ModelDanhMuc sp = (ModelDanhMuc) datav1[i][0];
			data[i][0] = sp.getMaDanhMuc();
			data[i][1] = sp.getTenDanhMuc();
			data[i][2] = sp.isMatHang();
		}
		return data;
	}

	@Test(description = "Test CapNhatDM", dataProvider = "dataCapNhatDM", priority = 2)
	public void CapNhatDM(Object[] data) {
		ModelDanhMuc dmTemp = (ModelDanhMuc) data[0];
		String maDM = String.valueOf(data[1]);
		Boolean expected = (Boolean) data[2];
		Boolean actual = false;
		
		dmTemp.setMaDanhMuc(maDM);
		dmDAO.update(dmTemp, maDM);
		ModelDanhMuc insertTemp = (ModelDanhMuc) dmDAO.selectById(dmTemp.getMaDanhMuc());
		
		if (dmTemp.toString().equalsIgnoreCase(insertTemp.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataXoaDM")
	public Object[][] dataXoaDM() {
		return new Object[][] { { "DM016", true }};
	}

	@Test(description = "Test XoaDM", dataProvider = "dataXoaDM", priority = 3)
	public void XoaDM(String maDM, Boolean expected) {
		Boolean actual = false;
		dmDAO.delete(maDM);
		ModelDanhMuc dmAfter = null;
		try {
			dmAfter = dmDAO.selectById(maDM);
		} catch (Exception e) {
			dmAfter = null;
		}
		if (dmAfter == null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataTimKiemDM")
	public Object[][] dataTimKiemDM() {
		return new Object[][] { { "Bia", true }, { "Điểm tâm", true }, { "Rượu", true }, { "Salad", true } };
	}

	@Test(description = "Test TimKiemDM", dataProvider = "dataTimKiemDM", priority = 4)
	public void TimKiemDM(String keyword, Boolean expected) {
		Boolean actual = false;
		List<ModelDanhMuc> list = dmDAO.timkiem(keyword);
		if (list.size() > 0) {
			list.forEach((item) -> {
				System.out.println("Tim thay: " + item);
			});
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 9, 6, "maDanhMuc,tenDanhMuc,matHang", dataThemDMv2(dataThemDM()));
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 10, 6, "maDanhMuc,tenDanhMuc,matHang", dataThemDMv2(dataCapNhatDM()));
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 11, 6, "maDanhMuc", dataXoaDM());
//		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 12, 6, "tenDanhMuc", dataTimKiemDM());
	}
	
	//
}
