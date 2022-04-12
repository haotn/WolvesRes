package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class StockQuanExportTestSuccess {
	@Test(dataProvider = "stockquanover")
	public void f(String txtSoluong, boolean expect) {
		boolean actual = ExtractedModule.checkStockQuanExport(txtSoluong);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockquanover() {
		return new Object[][] { { "2000", false }, { "100000", false }, { "999999999", false } };
	}
}
