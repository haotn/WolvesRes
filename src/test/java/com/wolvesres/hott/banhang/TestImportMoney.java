package com.wolvesres.hott.banhang;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
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
		return new Object[][] { { -505.2, false }, { -100, false }, { -1, false }, { -0.9, false }, { -1000, false } };
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
		if (money < 0) {
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
		return new Object[][] { { "cs3", false }, { "heheehe", false }, { "a", false }, { "Z", false },
				{ "aaaaaaaaa", false }, { "ZZZZZZZZ", false } };
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
		if (!FormValidator.isFloatNumber(money)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}

//	@AfterClass(groups = "ImportMoneyFailA")
//	public void exportExcelA() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "money", dataA());
//
//	}

//	@AfterClass(groups = "ImportMoneyFailB")
//	public void exportExcelB() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "money", dataB());
//	}
}
