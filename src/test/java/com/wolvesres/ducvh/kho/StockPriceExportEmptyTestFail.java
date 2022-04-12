package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class StockPriceExportEmptyTestFail {
	@Test(dataProvider = "stockpriceempty")
	public void f(String txtGia, boolean expect) {
		boolean actual = ExtractedModule.checkStockPriceExport(txtGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockpriceempty() {
		return new Object[][] { { "", false }, { " ", false }, { "\t", false }, { null, false } };
	}
}
