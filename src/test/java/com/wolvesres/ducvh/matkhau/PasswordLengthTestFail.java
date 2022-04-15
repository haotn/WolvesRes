package com.wolvesres.ducvh.matkhau;

import org.testng.annotations.Test;

import com.wolvesres.form.taikhoan.EditTaiKhoan;

import exceldoing.ExcelGo;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

public class PasswordLengthTestFail {
	@Test(dataProvider = "passwordlenght")
	public void f(String password, boolean expect) {
		boolean actual = EditTaiKhoan.validatePass(password);
		assertEquals(actual, expect);

	}

	@DataProvider
	public Object[][] passwordlenght() {
		return new Object[][] { { "12345", false }, { "123", false }, { "1", false }, { "", false }, { "abc", false },
				{ "-_-", false }, { "   ", false } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "matKhau", passwordlenght());
	}

}
