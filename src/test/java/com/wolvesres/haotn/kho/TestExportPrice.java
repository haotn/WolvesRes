package com.wolvesres.haotn.kho;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import exceldoing.ExcelGo;

public class TestExportPrice {

	/**
	 * DataProvider for testProductExportPrice
	 * 
	 * @return
	 */
	@DataProvider(name = "dataForExportPrice")
	public Object[][] dataForExportPrice() {
		Object[][] data = new Object[][] { { -0.000001, false }, { -100000.000001, false }, { -50000.0000050, false },
				{ -750000.0000075, false }, { -250000.0000025, false }, { -125000.0000125, false },
				{ -175000.0000175, false } };

		return data;
	}

	/**
	 * TestCase
	 * 
	 * @param price    testProductExportPrice
	 * @param expected
	 */
	@Test(dataProvider = "dataForExportPrice", groups = "testPriceExport")
	public void testProductExportPriceFail(double price, Boolean expected) {
		Boolean actual = true;
		if (price < 0) {
			actual = false;
			System.out.println("Giá sản phẩm phải lớn hơn 0!");
		} else {
			System.out.println("Giá sản phẩm hợp lệ (lớn hơn 0)!");
		}
		Assert.assertEquals(actual, expected);
	}

//	@AfterClass
//	public void writeResult() {
//		Object[][] dataWrite = new Object[dataForExportPrice().length][1];
//		for (int i = 0; i < dataForExportPrice().length; i++) {
//			dataWrite[i][0] = dataForExportPrice()[i][0];
//		}
//		try {
//			ExcelGo.writeExcelv2("excel-file/asm-temp-demo.xlsx", 2, 286, 6, "GiaXuatKho", dataWrite);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
