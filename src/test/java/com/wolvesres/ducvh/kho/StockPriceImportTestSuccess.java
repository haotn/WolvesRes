package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class StockPriceImportTestSuccess {
	@Test(dataProvider = "stockprice")
	public void f(String txtGia, boolean expect) {
		boolean actual = ExtractedModule.checkStockPriceImport(txtGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockprice() {
		return new Object[][] { { "1", true }, { "100", true }, { "999", true } };
	}
}
