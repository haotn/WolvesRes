package com.wolvesres.hott.banhang;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import junit.framework.Assert;

//Kiểm tra nhập tiền khách đưa thất bại do nhập số âm
//Kiểm tra nhập tiền khách đưa thất bại do nhập chữ

public class TestImportMoney {
	/**
	 * DataProvider for testImportMoneyFailA (Thất bại do nhập số âm)
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dataA() {
		return new Object[][] {{ -505.2, false}, {-100, false}};
	}

	/**
	 * TestCase testImportMoneyFailA (Thất bại do nhập số âm)
	 * 
	 * @param money
	 * @param expected
	 */
	@Test(dataProvider = "dataA", groups = "ImportMoneyFailA")
	public void testImportMoneyFailA(double money, Boolean expected) {
		Boolean actual = true;
		if (money<0) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	/******************************************************************************/
	/**
	 * DataProvider for testImportMoneyFailB (Thất bại do nhập chữ)
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dataB() {
		return new Object[][] {{ "cs3", false}, {"heheehe", false}};
	}

	/**
	 * TestCase testImportMoneyFailB (Thất bại do nhập chữ)
	 * 
	 * @param money
	 * @param expected
	 */
	@Test(dataProvider = "dataB", groups = "ImportMoneyFailB")
	public void testImportMoneyFailB(String money, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isNumber(money)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
}
