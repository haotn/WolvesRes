package com.wolvesres.login.testclass;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.model.ModelTaiKhoan;

import junit.framework.Assert;

public class TestLogin {
	private TaiKhoanDAO tkDao;

	@BeforeClass(groups = { "loginSuccess", "loginFail" })
	public void initDao() {
		tkDao = new TaiKhoanDAO();
	}

	/**
	 * Data for testLoginFail
	 * 
	 * @return
	 */
	@DataProvider
	public Object[][] dbFail() {
		return new Object[][] { { "user0001", "12345678", false }, { "user00002", "123456", false },
				{ "nguoidungnn", "123456", false }, { "BOSS01", "12345678", false } };
	}

	/**
	 * Test login fail
	 * 
	 * @param username
	 * @param password
	 * @param result
	 */
	@Test(dataProvider = "dbFail", groups = "loginFail")
	public void testLoginFail(String username, String password, boolean result) {
		ModelTaiKhoan taiKhoan = tkDao.checkLogin(username, password);
		boolean isSuccess = true;
		if (taiKhoan == null) {
			isSuccess = false;
		}
		Assert.assertEquals(result, isSuccess);
	}

	/**
	 * Data for testLoginSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dbSuccess() {
		return new Object[][] { { "BOSS01", "12345678", true }, { "BOSS02", "123456", true },
				{ "BOSS03", "123456", true } };
	}

	/**
	 * Test login success
	 * 
	 * @param username
	 * @param password
	 * @param result
	 */
	@Test(dataProvider = "dbSuccess", groups = "loginSuccess")
	public void testLoginSuccess(String username, String password, boolean result) {
		ModelTaiKhoan taiKhoan = tkDao.checkLogin(username, password);
		boolean isSuccess = false;
		if (taiKhoan != null) {
			isSuccess = true;
		}
		Assert.assertEquals(result, isSuccess);
	}
}
