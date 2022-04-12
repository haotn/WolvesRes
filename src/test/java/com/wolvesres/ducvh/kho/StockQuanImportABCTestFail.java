package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class StockQuanImportABCTestFail {
	@Test(dataProvider = "stockquanimportabc")
	public void f(String txtSoLuong, boolean expect) {
		boolean actual = ExtractedModule.checkStockImport(txtSoLuong);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockquanimportabc() {
		return new Object[][] { { "a", false }, { "abc", false }, { "a1b2c3", false } };
	}
}
