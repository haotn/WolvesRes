package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class StockQuanExportOverTestFail {
	@Test(dataProvider = "stockquan")
	public void f(String txtSoluong, boolean expect) {
		boolean actual = ExtractedModule.checkStockQuanExport(txtSoluong);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockquan() {
		return new Object[][] { { "2", true }, { "100", true }, { "999", true } };
	}
}
