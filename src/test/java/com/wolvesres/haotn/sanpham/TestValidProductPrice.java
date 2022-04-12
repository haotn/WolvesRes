package com.wolvesres.haotn.sanpham;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

/**
 * Test valid product price (not empty)
 * 
 * @author Brian
 *
 */
public class TestValidProductPrice {
	/**
	 * DataProvider for testProductPriceIsNotEmpty
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataForTestEmpty")
	public Object[][] dataForTestProductPriceIsNotEmpty() {
		return new Object[][] { { "", false } };
	}

	/**
	 * TestCase testProductPriceIsNotNull
	 * 
	 * @param productPrice
	 * @param expected
	 */
	@Test(dataProvider = "dataForTestEmpty", groups = { "productPriceIsEmpty" })
	public void testProductPriceEmpty(String productPrice, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(productPrice)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}

	/**
	 * DataProvider for testProductPriceNegative
	 * 
	 * @return
	 */
	@DataProvider(name = "dataForTestNegative")
	public Object[][] dataForTestProductPriceNegative() {
		return new Object[][] { { -0.1, false }, { -100000000.1f, false }, { -11f, false }, { -999999999f, false },
				{ -50000000f, false }, { -7500000f, false }, { -25000000f, false }, { -0.0000000000001f, false }

		};
	}

	/**
	 * TestCase testProductPriceNegative
	 * 
	 * @param productPrice
	 * @param expected
	 */
	@Test(dataProvider = "dataForTestNegative")
	public void testProductPriceNegative(double productPrice, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isGreaterThan(productPrice, 0)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
}
