package com.wolvesres.haotn.voucher;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

/**
 * Test voucher discount
 * 
 * @author Brian
 *
 */
public class TestVoucherDiscount {
	private DataGenerator dataGenerator;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dataGenerator = new DataGenerator();
	}

	/**
	 * DataProvider for TestVoucherDiscount
	 * 
	 * @return
	 */
	@DataProvider(name = "dataForTestVoucherDiscount")
	public Object[][] dataForTestVoucherDiscount() {
		Object[][] data = new Object[50][2];
		for (int i = 0; i < 50; i++) {
			data[i][0] = dataGenerator.randomMinMax(0.1, 100);
			data[i][1] = true;
		}
		return data;
	}

	/**
	 * TestCase testVoucherDiscount
	 * 
	 * @param discount
	 * @param expected
	 */
	@Test(dataProvider = "dataForTestVoucherDiscount")
	public void testVoucherDiscount(double discount, Boolean expected) {
		Boolean actual = false;
		if (discount <= 100 && discount > 0) {
			actual = true;
		}
		Assert.assertEquals(expected, actual);
	}

	/**
	 * DataProvider for testVoucherDiscountLetter
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataForTestVoucherDiscountLetter")
	public Object[][] dataForTestVoucherDiscountLetter() {
		Object[][] data = new Object[100][2];
		for (int i = 0; i < 100; i++) {
			data[i][0] = dataGenerator.generateTextAndNumber(0.1, 100);
			data[i][1] = false;
		}
		return data;
	}

	/**
	 * TestCase testVoucherDiscountLetter
	 * 
	 * @param discount
	 * @param expected
	 */
	@Test(dataProvider = "dataForTestVoucherDiscountLetter")
	public void testVoucherDiscountLetter(String discount, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isNumber(discount)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}

	@DataProvider(name = "dataForTestVoucherDiscountGreaterThan100")
	public Object[][] dataForTestVoucherDiscountGreaterThan100() {
		Object[][] data = new Object[100][2];
		for (int i = 0; i < 100; i++) {
			data[i][0] = dataGenerator.randomMinMax(101.1, 999.9);
			data[i][1] = false;
		}
		return data;
	}

	@Test(dataProvider = "dataForTestVoucherDiscountGreaterThan100", groups = "discountGreaterThan100")
	public void testVoucherDiscountGreaterThan100(double discount, Boolean expected) {
		Boolean actual = true;
		if (discount > 100) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
}
