package com.wolvesres.hott.test_object.ban;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.model.ModelBan;

import exceldoing.ExcelGo;

public class TestBan {
	private BanDAO banDao;
	private AutoDAO autoDao;

	// Declare DAO
	@BeforeClass
	public void beforeClassKhuBan() {
		banDao = new BanDAO();
		autoDao = new AutoDAO();
	}

	// Data Provider
	@DataProvider(name = "dataThemBan")
	public Object[][] dataThemBan() {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/ban-data.xlsx", 0, 9, 0);
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelBan emp = new ModelBan();
		//	emp.setMaBan(String.valueOf(list.get(i)[0]))
			emp.setTenBan(String.valueOf(list.get(i)[1]));
			emp.setHoatDong(Boolean.parseBoolean(String.valueOf(list.get(i)[2])));
			emp.setMaKhuBan(String.valueOf(list.get(i)[3]));

			data[i][0] = emp;
			data[i][1] = true;
		}
		return data;
	}

	@Test(description = "Test dao them ban", dataProvider = "dataThemBan", groups = "themBan", priority = 1)
	public void testThemBan(Object[] data) {
		ModelBan ban = (ModelBan) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		String maban = autoDao.AuToBan();
		ban.setMaBan(maban);

		banDao.insert(ban);
		ModelBan fromDatabase = banDao.selectById(maban);
		if (ban.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu thêm mới mong muốn: " + ban);
		System.out.println("Dữ liệu thêm mới thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCapNhatBan")
	public Object[][] dataCapNhatBan() {
		return new Object[][] {
				{ new ModelBan("B010", "Bàn 10 Update", true, "KB02"), true },
				{ new ModelBan("B011", "Bàn 11 Update", true, "KB02"), true },
				{ new ModelBan("B012", "Bàn 12 Update", true, "KB02"), true },
				{ new ModelBan("B013", "Bàn 13 Update", true, "KB02"), true },
				{ new ModelBan("B014", "Bàn 14 Update", true, "KB02"), true } };
	}

	@Test(description = "Test dao cap nhat ban", dataProvider = "dataCapNhatBan", priority = 2)
	public void testCapNhatBan(Object[] data) {
		ModelBan ban = (ModelBan) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		banDao.update(ban, ban.getMaBan());
		ModelBan fromDatabase = banDao.selectById(ban.getMaBan());
		System.out.println("Dữ liệu cập nhật mong muốn: " + ban.toString());
		System.out.println("Dữ liệu cập nhật thực tế: " + fromDatabase.toString());
		System.out.println("\n");
		if (ban.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataDeleteBan")
	public Object[][] dataDeleteKhuBan() {
		return new Object[][] { { "B015", true }, { "B016", true }, { "B017", true } };
	}

	@Test(description = "Test delete ban", dataProvider = "dataDeleteBan", priority = 3)
	public void testDeleteBan(String maBan, Boolean expected) {
		Boolean actual = false;
		ModelBan entity = banDao.selectById(maBan);
		banDao.delete(maBan);
		ModelBan ban = banDao.selectById(maBan);
		System.out.println("Xóa thành công!");
		if (ban == null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

}
