package com.wolvesres.ducvh.matkhau;

import org.testng.annotations.Test;

import com.wolvesres.form.taikhoan.EditTaiKhoan;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class PasswordEmptyTestFail {
	@Test(dataProvider = "passwordempty")
	public void f(String password, boolean expect) {
		boolean actual = true;
		try {
			actual = EditTaiKhoan.validatePass(password);
		} catch (Exception e) {
			actual = false;
		}
		assertEquals(actual, expect);

	}

	@DataProvider
	public Object[][] passwordempty() {
//		rỗng; null; khoảng cách; tab
		return new Object[][] { { "", false }, { " ", false }, { "\t", false }};
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "matKhau", passwordempty());
	}
}
