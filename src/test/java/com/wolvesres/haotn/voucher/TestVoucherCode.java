package com.wolvesres.haotn.voucher;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

/**
 * Test Voucher Code
 * 
 * @author Brian
 *
 */
public class TestVoucherCode {
	public DataGenerator dataGenerator;

	/**
	 * BeforeClass - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dataGenerator = new DataGenerator();
	}

	/**
	 * TestCase testInvalidVoucherCode
	 */
	@Test(groups = "testVoucherCodeEmpty")
	public void testVoucherCodeEmpty() {
		String voucherCode = "";
		Boolean expected = false;
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(voucherCode)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}

	/**
	 * DataProvider for testVoucherCodeLength
	 * 
	 * @return
	 */
	@DataProvider(name = "dataForTestVoucherCodeLength")
	public Object[][] dataForTestVoucherCodeLength() {
		Object[][] data = new Object[100][2];
		for (int i = 0; i < 100; i++) {
			data[i][0] = dataGenerator.generatePassword(1, 4, false);
			data[i][1] = false;
		}
		return data;
	}

	@Test(dataProvider = "dataForTestVoucherCodeLength", groups = "testVoucherCodeLength")
	public void testVoucherCodeLength(String voucherCode, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidTextMinLength(voucherCode, 5)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
}
