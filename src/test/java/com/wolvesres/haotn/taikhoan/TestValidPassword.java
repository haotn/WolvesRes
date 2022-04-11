package com.wolvesres.haotn.taikhoan;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import junit.framework.Assert;

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
		Object[][] data = new Object[100][2];
		for (int i = 0; i < 100; i++) {
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
	public void testValidPasswordFailLength(String password, Boolean expectes) {
		Boolean actual = true;
		if (!FormValidator.isValidTextMaxLength(password, 16)) {
			actual = false;
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
		Object[][] data = new Object[100][2];
		for (int i = 0; i < 100; i++) {
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
	public void testPasswordFailSpace(String password, Boolean expected) {
		Boolean actual = true;
		if (FormValidator.isTextContainsSpace(password)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}

	/**
	 * DataProvider for testConfirmPasswordNotMath
	 */
	@DataProvider(name = "dataForConfirmNotMath")
	public Object[][] dataForConfirmNotMath() {
		Object[][] data = new Object[100][3];
		for (int i = 0; i < 100; i++) {
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
	@Test(dataProvider = "dataForConfirmNotMath", groups = "failConfirmNotMath", priority = 2)
	public void tesConfirmPasswordNotMath(String password, String confirm, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextEqual(password, confirm)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
}
