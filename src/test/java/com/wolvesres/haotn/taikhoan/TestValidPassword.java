package com.wolvesres.haotn.taikhoan;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;

/**
 * Test valid password length - Password length must be less than 17 letter
 * 
 * @author Brian
 *
 */
public class TestValidPassword {
	private DataGenerator dataGenerator;

	/**
	 * Generate global variable value
	 */
	@BeforeClass(groups = { "failLength", "failSpace", "failConfirmNotMath" })
	public void beforeClass() {
		dataGenerator = new DataGenerator();

	}

	/**
	 * DataProvider for testValidPasswordFailLength
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataForFailLength")
	public Object[][] dataForFailLength() {
		Object[][] data = new Object[5][2];
		for (int i = 0; i < 5; i++) {
			data[i][0] = this.dataGenerator.generatePassword(17, 25, false);
			data[i][1] = false;
		}
		return data;
	}

	/**
	 * TestCase testValidPasswordFailLength
	 * 
	 * @param password
	 * @param expectes
	 */
	@Test(dataProvider = "dataForFailLength", groups = "failLength", priority = 0)
	public void testValidPassworLengthFail(String password, Boolean expectes) {
		Boolean actual = true;
		if (!FormValidator.isValidTextMaxLength(password, 16)) {
			actual = false;
			System.out.println("Độ dài mật khẩu chỉ chấp nhật từ 6 đến 16 ký tự!");
		} else {
			System.out.println("Độ dài mật khẩu hợp lệ!");
		}
		Assert.assertEquals(expectes, actual);
	}

	/**
	 * DataProvider for testValidPasswordFailSpace
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataForFailSpace")
	public Object[][] dataForFailSpace() {
		Object[][] data = new Object[5][2];
		for (int i = 0; i < 5; i++) {
			data[i][0] = this.dataGenerator.generatePassword(6, 16, true);
			data[i][1] = false;
		}
		return data;
	}

	/**
	 * TestCase testValidPasswordFailSpace
	 * 
	 * @param password
	 * @param expected
	 */
	@Test(dataProvider = "dataForFailSpace", groups = "failSpace", priority = 1)
	public void testPasswordSpaceFail(String password, Boolean expected) {
		Boolean actual = true;
		if (FormValidator.isTextContainsSpace(password)) {
			actual = false;
			System.out.println("Mật khẩu không hợp lệ (không được chứa khoảng trắng)!");
		} else {
			System.out.println("Mật khẩu hợp lệ (không chứa khoảng trắng)!");
		}
		Assert.assertEquals(expected, actual);
	}

	/**
	 * DataProvider for testConfirmPasswordNotMath
	 */
	@DataProvider(name = "dataForConfirmNotMath")
	public Object[][] dataForConfirmNotMath() {
		Object[][] data = new Object[5][3];
		for (int i = 0; i < 5; i++) {
			String password = this.dataGenerator.generatePassword(6, 16, false);
			String confirm = dataGenerator.generatePassword(6, 16, false);
			while (confirm.equals(password)) {
				confirm = dataGenerator.generatePassword(6, 16, false);
			}
			data[i][0] = password;
			data[i][1] = confirm;
			data[i][2] = false;
		}
		return data;
	}

	/**
	 * TestCase testConfirmPasswordNotMath
	 * 
	 */
	@Test(dataProvider = "dataForConfirmNotMath", groups = "confirmNotMath", priority = 2)
	public void tesConfirmPasswordNotMathFail(String password, String confirm, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextEqual(password, confirm)) {
			actual = false;
			System.out.println("Mật khẩu và xác nhận mật khẩu không khớp!");
		} else {
			System.out.println("Mật khẩu và xác nhận mật khẩu hợp lệ!");
		}
		Assert.assertEquals(expected, actual);
	}

//	@AfterClass
//	public void writeFile() {
//		Object[][] dataWrite = new Object[dataForConfirmNotMath().length][2];
//		for (int i = 0; i < dataForConfirmNotMath().length; i++) {
//			dataWrite[i][0] = dataForConfirmNotMath()[i][0];
//			dataWrite[i][1] = dataForConfirmNotMath()[i][1];
//		}
//		try {
//			ExcelGo.writeExcelv2("excel-file/asm-temp-demo.xlsx", 2, 210, 6, "MatKhau,XacNhanMatKhau", dataWrite);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
