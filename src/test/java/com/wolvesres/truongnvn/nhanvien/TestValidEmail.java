package com.wolvesres.truongnvn.nhanvien;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
import junit.framework.Assert;

public class TestValidEmail {

	/**
	 * Before class
	 * 
	 */
	@BeforeClass(groups = {"emailspace","emailempty"})
	public void beforClass() {

	}

	/**
	 * DataProvider for testValidEmail
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "data")
	public Object[][] data() {
		return new Object[][] { { " testemail123@gmail.com", false }, { "test email 123 @gmail.com", false },
				{ "test email123@gmail.com", false }, { " t e s t e m a i l 1 2 3 @gmail.com", false },
				{ " testemail123 @gmail.com", false }, { " t e s t e m a i l 1 2 3 @ g m a i l . c o m " ,false} };
	}

	@DataProvider(name = "dataEmpty")
	public Object[][] dataEmpty() {
		return new Object[][] { {" ", false }};
	}
	/**
	 * TestCase testValidEmail có khoảng trắng
	 * 
	 * @param email
	 * @param expected
	 */
	@Test(dataProvider = "data", groups = "emailspace" , priority = 0)
	public void testValidEmailCoKhoangTrangFalse(String email, Boolean expected) {
		Boolean actual = FormValidator.isValidEmail(email);
		Assert.assertEquals(expected, actual);
	}

	/**
	 * TestCase testValidEmail trống
	 * 
	 * @param email
	 * @param expected
	 */
	@Test(dataProvider = "dataEmpty", groups = "emailempty" , priority = 1)
	public void testValidEmailEmpty(String email, Boolean expected) {
		Boolean actual = FormValidator.isTextIsNotEmpty(email);
		Assert.assertEquals(expected, actual);
	}
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "email", dataEmpty());
//	}
}
