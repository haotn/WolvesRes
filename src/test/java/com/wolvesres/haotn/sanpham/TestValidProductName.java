package com.wolvesres.haotn.sanpham;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

/**
 * Test valid product name (not empty)
 * 
 * @author Brian
 *
 */
public class TestValidProductName {
	/**
	 * DataProvider for testValidProductName
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "data")
	public Object[][] data() {
		return new Object[][] { { "Bò kho", true }, { "Bánh bao", true }, { "Lẩu thái", true },
				{ "Gà tiềm thuốc bắc", true }, { "Bò leo núi", true }, { "Gà nướng", true } };
	}

	/**
	 * TestCase testValidProductName
	 * 
	 * @param productName
	 * @param expected
	 */

	@Test(dataProvider = "data")
	public void testValidProductName(String productName, Boolean expected) {
		Boolean actual = false;
		if (FormValidator.isTextContainsSpace(productName)) {
			actual = true;
		}
		Assert.assertEquals(expected, actual);
	}
}
