package com.wolvesres.login.testclass;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.model.ModelTaiKhoan;

import junit.framework.Assert;

public class TestLoginSuccessWithBossRole {
	private TaiKhoanDAO tkDao;

	@BeforeClass
	public void initDao() {
		tkDao = new TaiKhoanDAO();
	}

	@DataProvider
	public Object[][] db() {
		return new Object[][] { { "BOSS01", "12345678", true }, { "BOSS02", "123456", true },
				{ "BOSS03", "123456", true } };
	}

	@Test(dataProvider = "db")
	public void testLogin(String username, String password, boolean result) {
		ModelTaiKhoan taiKhoan = tkDao.checkLogin(username, password);
		boolean isSuccess = false;
		if (taiKhoan != null) {
			isSuccess = true;
		}
		Assert.assertEquals(result, isSuccess);
	}
}
