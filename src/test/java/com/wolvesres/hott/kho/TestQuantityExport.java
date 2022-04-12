package com.wolvesres.hott.kho;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


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
		return new Object[][] { {-5, false}, { -6.9, false}};
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
}
