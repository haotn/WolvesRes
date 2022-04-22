package com.wolvesres.hott.test_object.hoadon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.model.ModelHoaDon;


import exceldoing.ExcelGo;

public class TestHoaDon {
	private HoaDonDAO hoaDonDao;
	private AutoDAO autoDao;

	// Declare DAO
	@BeforeClass
	public void beforeClassKhuBan() {
		hoaDonDao = new HoaDonDAO();
		autoDao = new AutoDAO();
	}

	// Data Provider
	@DataProvider(name = "dataThemHoaDon")
	public Object[][] dataThemHoaDon() {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/hoadon-data.xlsx", 0, 9, 0);
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelHoaDon emp = new ModelHoaDon();
		//	emp.setMaHD(String.valueOf(list.get(i)[0]));
			emp.setNguoiXuat(String.valueOf(list.get(i)[1]));
			emp.setMaBan(String.valueOf(list.get(i)[2]));
			emp.setMaVoucher(String.valueOf(list.get(i)[3]));
			emp.setThue(Float.parseFloat(String.valueOf(list.get(i)[4])));
			emp.setTienHang(Float.parseFloat(String.valueOf(list.get(i)[5])));
			emp.setTrangThai(Boolean.parseBoolean(String.valueOf(list.get(i)[6])));

			data[i][0] = emp;
			data[i][1] = true;
		}
		return data;
	}

	@Test(description = "Test dao them hoa don", dataProvider = "dataThemHoaDon", groups = "themHoaDon", priority = 1)
	public void testThemHoaDon(Object[] data) {
		ModelHoaDon hoaDon = (ModelHoaDon) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		String maHoaDon = autoDao.AuToHoaDon();
		hoaDon.setMaHD(maHoaDon);

		hoaDonDao.insert(hoaDon);
		ModelHoaDon fromDatabase = hoaDonDao.selectById(maHoaDon);
		hoaDon.setNgayXuat(fromDatabase.getNgayXuat());
		if (hoaDon.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu thêm mới mong muốn: " + hoaDon);
		System.out.println("Dữ liệu thêm mới thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCapNhatHoaDon")
	public Object[][] dataCapNhatHoaDon() {
		return new Object[][] {
				{ hoaDonDao.selectById("HD013"), true },
				{ hoaDonDao.selectById("HD014"), true },
				{ hoaDonDao.selectById("HD015"), true },
				{ hoaDonDao.selectById("HD016"), true },
				{ hoaDonDao.selectById("HD017"), true } };
	}

	@Test(description = "Test dao cap nhat hoa don", dataProvider = "dataCapNhatHoaDon", priority = 2)
	public void testCapNhatHoaDon(Object[] data) {
		ModelHoaDon hoaDon = (ModelHoaDon) data[0];
		hoaDon.setTrangThai(false);
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		hoaDonDao.update(hoaDon, hoaDon.getMaHD());
		ModelHoaDon fromDatabase = hoaDonDao.selectById(hoaDon.getMaHD());
		System.out.println("Dữ liệu cập nhật mong muốn: " + hoaDon.toString());
		System.out.println("Dữ liệu cập nhật thực tế: " + fromDatabase.toString());
		System.out.println("\n");
		if (hoaDon.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}


	@DataProvider(name = "dataTimKiemHoaDon")
	public Object[][] timKiemHoaDon() {
		return new Object[][] { { "HD021", true }, { "HD022", true } };
	}

	@Test(description = "Test tim kiem hoa don theo ma hoa don", dataProvider = "dataTimKiemHoaDon", priority = 4)
	public void testTimKiemHoaDon(String keyword, Boolean expected) {
		Boolean actual = false;
		List<ModelHoaDon> list = hoaDonDao.timKiem(keyword);
		if (list.size() > 0) {
			list.forEach((item) -> {
				System.out.println(item);
			});
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}
}
