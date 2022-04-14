package com.wolvesres.hott.sanpham;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

//import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;

//Kiểm tra giá sản phẩm thành công

public class TestProductPrice {

	/**
	 * DataProvider for testProductPriceSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { 20.5, true }, { 1, true }, { 500, true }, { 1000, true }, { 59999, true }};
	}

	/**
	 * TestCase testProductPriceSuccess
	 * 
	 * @param product price
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testProductPriceSuccess(double price, Boolean expected) {
		Boolean actual = false;
		if (price>0 && FormValidator.isFloatNumber(String.valueOf(price))) {
			actual = true;
		}
		AssertJUnit.assertEquals(expected, actual);

	}
	
//	@AfterClass
//	public void exportExcel() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "price", data());
//		
//	}
}
