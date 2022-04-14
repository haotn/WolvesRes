package com.wolvesres.haotn.sanpham;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;

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
	public Object[][] dataForTestProductPriceIsNotEmptyFail() {
		return new Object[][] { { "", false }, { "\t", false }, { " ", false } };
	}

	/**
	 * TestCase testProductPriceIsNotNull
	 * 
	 * @param productPrice
	 * @param expected
	 */
	@Test(dataProvider = "dataForTestEmpty", groups = { "productPriceIsEmpty" })
	public void testProductPriceEmptyFail(String productPrice, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(productPrice)) {
			actual = false;
			System.out.println("Giá sản phẩm không được để trống!");
		} else {
			System.out.println("Giá sản phẩm hợp lệ (không để trống)!");
		}
		Assert.assertEquals(actual, expected);
	}

	/**
	 * DataProvider for testProductPriceNegative
	 * 
	 * @return
	 */
	@DataProvider(name = "dataForTestNegative")
	public Object[][] dataForTestProductPriceNegativeFail() {
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
	@Test(dataProvider = "dataForTestNegative", groups = { "productPrigeNegative" })
	public void testProductPriceNegativeFail(double productPrice, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isGreaterThan(productPrice, 0)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}

//	@AfterClass
//	public void write() {
//		Object[][] dataWrite = new Object[dataForTestProductPriceIsNotEmpty().length][1];
//		for (int i = 0; i < dataForTestProductPriceIsNotEmpty().length; i++) {
//			if (dataForTestProductPriceIsNotEmpty()[0].equals("")) {
//				dataWrite[i][0] = "\"empty\"";
//			} else if (dataForTestProductPriceIsNotEmpty()[0].equals(" ")) {
//				dataWrite[i][0] = "\"space\"";
//			} else if (dataForTestProductPriceIsNotEmpty()[0].equals("\t")) {
//				dataWrite[i][0] = "\"\t\"";
//			}
//			dataWrite[i][0] = dataForTestProductPriceIsNotEmpty()[0];
//		}
//		try {
//			ExcelGo.writeExcel("excel-file/asm-temp-demo.xlsx", 2, 224, 6, "GiaNhapKho", dataWrite);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
