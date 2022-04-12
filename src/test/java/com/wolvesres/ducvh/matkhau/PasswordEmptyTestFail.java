package com.wolvesres.ducvh.matkhau;

import org.testng.annotations.Test;

import com.wolvesres.form.taikhoan.EditTaiKhoan;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class PasswordEmptyTestFail {
	@Test(dataProvider = "passwordempty")
	public void f(String password, boolean expect) {
		boolean actual = EditTaiKhoan.validatePass(password);
		assertEquals(actual, expect);

	}

	@DataProvider
	public Object[][] passwordempty() {
//		rỗng; null; khoảng cách; tab
		return new Object[][] { { "", false }, { null, false }, { " ", false }, { "\t", false }};
	}
}
