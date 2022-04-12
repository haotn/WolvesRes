package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class StockPriceImportMinusTestFail {
	@Test(dataProvider = "stockpriceminus")
	public void f(String txtGia, boolean expect) {
		boolean actual = ExtractedModule.checkStockPriceImport(txtGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockpriceminus() {
		return new Object[][] { { "-1", false }, { "-100", false }, { "-999", false } };
	}
}
