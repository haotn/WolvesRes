package com.wolvesres.hott.test_object.khuban;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.KhuBanDAO;
import com.wolvesres.model.ModelKhuBan;

import exceldoing.ExcelGo;

public class TestKhuBan {
	
	private KhuBanDAO khubanDao;
	private AutoDAO autoDao;

	// Declare DAO
	@BeforeClass
	public void beforeClassKhuBan() {
		khubanDao = new KhuBanDAO();
		autoDao = new AutoDAO();
	}

	// Data Provider
	@DataProvider(name = "dataThemKhuBan")
	public Object[][] dataThemKhuBan() {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/khuban-data.xlsx", 0, 9, 0);
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelKhuBan emp = new ModelKhuBan();
	//		emp.setMaKhuBan(String.valueOf(list.get(i)[0]));
			emp.setTenKhuBan(String.valueOf(list.get(i)[1]));
			emp.setGhiChu(String.valueOf(list.get(i)[2]));

			data[i][0] = emp;
			data[i][1] = true;
		}
		return data;
	}

	@Test(description = "Test dao them khu ban", dataProvider = "dataThemKhuBan", groups = "themKhuBan", priority = 1)
	public void testThemKhuBan(Object[] data) {
		ModelKhuBan khuBan = (ModelKhuBan) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		String makhuban = autoDao.AuToKhuBan();
		khuBan.setMaKhuBan(makhuban);

		khubanDao.insert(khuBan);
		ModelKhuBan fromDatabase = khubanDao.selectById(makhuban);
		if (khuBan.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu thêm mới mong muốn: " + khuBan);
		System.out.println("Dữ liệu thêm mới thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCapNhatKhuBan")
	public Object[][] dataCapNhatKhuBan() {
		return new Object[][] {
				{ new ModelKhuBan("KB06", "Khu Sáu Update", "Khu Bình Thường Update"), true },
				{ new ModelKhuBan("KB07", "Khu Bảy Update", "Khu Bình Thường Update"), true },
				{ new ModelKhuBan("KB08", "Khu Tám Update", "Khu Bình Thường Update"), true },
				{ new ModelKhuBan("KB09", "Khu Chín Update", "Khu Bình Thường Update"), true },
				{ new ModelKhuBan("KB010", "Khu Mười Update", "Khu Bình Thường Update"), true } };
	}

	@Test(description = "Test dao cap nhat khu ban", dataProvider = "dataCapNhatKhuBan", priority = 2)
	public void testCapNhatKhuBan(Object[] data) {
		ModelKhuBan khuBan = (ModelKhuBan) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		khubanDao.update(khuBan, khuBan.getMaKhuBan());
		ModelKhuBan fromDatabase = khubanDao.selectById(khuBan.getMaKhuBan());
		System.out.println("Dữ liệu cập nhật mong muốn: " + khuBan.toString());
		System.out.println("Dữ liệu cập nhật thực tế: " + fromDatabase.toString());
		System.out.println("\n");
		if (khuBan.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataDeleteKhuBan")
	public Object[][] dataDeleteKhuBan() {
		return new Object[][] { { "KB011", true }, { "KB012", true }, { "KB013", true } };
	}

	@Test(description = "Test delete khu ban", dataProvider = "dataDeleteKhuBan", priority = 3)
	public void testDeleteKhuBan(String maKhuBan, Boolean expected) {
		Boolean actual = false;
		ModelKhuBan entity = khubanDao.selectById(maKhuBan);
		khubanDao.delete(maKhuBan);
		ModelKhuBan khuBan = khubanDao.selectById(maKhuBan);
		System.out.println("Xóa thành công!");
		if (khuBan == null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataTimKiemKhuBan")
	public Object[][] timKiemKhuBan() {
		return new Object[][] { { "Mười Bốn", true }, { "Mười Lăm", true } };
	}

	@Test(description = "Test tim kiem khu ban theo ten khu ban", dataProvider = "dataTimKiemKhuBan", priority = 4)
	public void testTimKiemKhuBan(String keyword, Boolean expected) {
		Boolean actual = false;
		List<ModelKhuBan> list = khubanDao.timkiem(keyword);
		if (list.size() > 0) {
			list.forEach((item) -> {
				System.out.println(item);
			});
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}
	
}
