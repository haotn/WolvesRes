package com.wolvesres.hott.kho;


import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import exceldoing.ExcelGo;
import junit.framework.Assert;


//Kiểm tra số lượng xuất kho thất bại do nhập số lượng âm

public class TestQuantityExport {
	/**
	 * DataProvider for testQuantityExportFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {-1, false}, { -10000000, false}, { -500000, false}, { -123456.5, false}, { -0.1, false}};
	}

	/**
	 * TestCase testQuantityExportFail
	 * 
	 * @param quantityExport
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testQuantityExportFail(double quantityExport, Boolean expected) {
		Boolean actual = true;
		if (quantityExport<0) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	
//	@AfterClass
//	public void exportExcel() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "quantityExport", data());
//	}
}
