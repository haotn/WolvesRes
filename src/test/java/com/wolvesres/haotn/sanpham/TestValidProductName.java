package com.wolvesres.haotn.sanpham;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;

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
	public void testValidProductNamePass(String productName, Boolean expected) {
		Boolean actual = false;
		if (FormValidator.isTextContainsSpace(productName)) {
			actual = true;
			System.out.println("Tên sản phẩm hợp lệ!");
		} else {
			System.out.println("Tên sản phẩm không hợp lệ!");
		}
		Assert.assertEquals(actual, expected);
	}

//	@AfterClass
//	public void writeResult() {
//		Object[][] dataWrite = new Object[data().length][1];
//		for (int i = 0; i < data().length; i++) {
//			dataWrite[i][0] = data()[i][0];
//		}
//		try {
//			ExcelGo.writeExcelv2("excel-file/asm-temp-demo.xlsx", 2, 216, 6, "TenSanPham", dataWrite);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
