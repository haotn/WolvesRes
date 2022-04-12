package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class StockPriceImportABCTestFail {
	@Test(dataProvider = "stockpriceabc")
	public void f(String txtGia, boolean expect) {
		boolean actual = ExtractedModule.checkStockPriceImport(txtGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockpriceabc() {
		return new Object[][] { { "a", false }, { "abc", false }, { "a1b2bc", false } };
	}
}
