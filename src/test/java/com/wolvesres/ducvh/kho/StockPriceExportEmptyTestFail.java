package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class StockPriceExportEmptyTestFail {
	@Test(dataProvider = "stockpriceempty")
	public void f(String txtGia, boolean expect) {
		boolean actual = DTool.checkStockPriceExport(txtGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockpriceempty() {
		return new Object[][] { { "", false }, { " ", false }, { "\t", false }, { null, false } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "giaXuatKho", stockpriceempty());
	}
}
