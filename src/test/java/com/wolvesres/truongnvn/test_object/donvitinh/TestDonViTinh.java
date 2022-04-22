package com.wolvesres.truongnvn.test_object.donvitinh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.model.ModelDonViTinh;
import com.wolvesres.model.ModelNhanVien;

import exceldoing.ExcelGo;

public class TestDonViTinh {
	private DonViTinhDAO dvtdao;

	// Declare DAO
	@BeforeClass
	public void beforeClassNhanVien() {
		dvtdao = new DonViTinhDAO();
	}

	// Data Provider

	@DataProvider(name = "dataThemDonViTinh")
	public Object[][] dataDonViTinh() {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/donvitinh.xlsx", 0, 3, 0);
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelDonViTinh emp = new ModelDonViTinh();
			emp.setTenDVT(String.valueOf(list.get(i)[0]));
			data[i][0] = emp;
			data[i][1] = true;
		}
		return data;
	}

	@Test(description = "Test dao them don vi tinh", dataProvider = "dataThemDonViTinh", groups = "themdonvitinh", priority = 1)
	public void testDonViTinh(Object[] data) {
		ModelDonViTinh dvt = (ModelDonViTinh) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		dvtdao.insert(dvt);
		List<ModelDonViTinh> listDVT = dvtdao.selectAll();
		ModelDonViTinh fromDatabase = listDVT.get(listDVT.size() - 1);
		dvt.setMaDVT(listDVT.get(listDVT.size() - 1).getMaDVT());
		if (dvt.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu thêm mới mong muốn: " + dvt);
		System.out.println("Dữ liệu thêm mới thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

//	Cap nhat dvt
	@DataProvider(name = "dataCapNhatDonViTinh")
	public Object[][] dataCapNhatDonViTinh() {
		return new Object[][] { { new ModelDonViTinh(1, "Lon nhựa"), true },
				{ new ModelDonViTinh(2, "Chai Kiểu"), true }, { new ModelDonViTinh(3, "Miếng"), true },
				{ new ModelDonViTinh(4, "Ly nhựa"), true }, { new ModelDonViTinh(5, "Túi"), true } };
	}
//	test cap nhat
	@Test(description = "Test dao cap nhat don vi tinh", dataProvider = "dataCapNhatDonViTinh", priority = 2)
	public void testCapNhatNhanVien(Object[] data) {
		ModelDonViTinh dvt = (ModelDonViTinh) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		dvtdao.update(dvt, dvt.getMaDVT());
		ModelDonViTinh fromDatabase = dvtdao.selectById(dvt.getMaDVT());
		System.out.println("Dữ liệu sửa mong muốn: " + dvt.toString());
		System.out.println("Dữ liệu sửa thực tế: " + fromDatabase.toString());
		System.out.println("\n");
		if (dvt.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}
	
	@DataProvider(name = "dataDeleteDonViTinh")
	public Object[][] dataDeleteNhanVien() {
		return new Object[][] { { 38, true }, {39, true }};
	}

	@Test(description = "Test delete don vi tinh", dataProvider = "dataDeleteDonViTinh", priority = 3)
	public void testDeleteDonViTinh(int madvt, Boolean expected) {
		Boolean actual = false;
		ModelDonViTinh entity = dvtdao.selectById(madvt);
		dvtdao.delete(madvt);
		ModelDonViTinh dvt = dvtdao.selectById(madvt);
		System.out.println("Dữ liệu mong muốn: " + entity);
		System.out.println("Dữ liệu thực tế: " + dvt);
		if (dvt == null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataTimKiemDonViTinh")
	public Object[][] timKiemDonViTinh() {
		return new Object[][] { { "Chén", true }, { "Hộp", true }, { "Đĩa", true } };
	}

	@Test(description = "Test tim kiem don vi tinh theo ten", dataProvider = "dataTimKiemDonViTinh", priority = 4)
	public void testTimKiemNhanVien(String keyword, Boolean expected) {
		Boolean actual = false;
		List<ModelDonViTinh> list = dvtdao.timkiem(keyword);
		if (list.size() > 0) {
			list.forEach((item) -> {
				System.out.println("Dữ liệu thực tế: " + item);
			});
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

}
