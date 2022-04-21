package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class StockQuanExportOverTestFail {
	@Test(dataProvider = "stockquanover")
	public void f(String txtSoluong, boolean expect) {
		boolean actual = DTool.checkStockQuanExport(txtSoluong);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockquanover() {
		return new Object[][] { { "1001", false }, { "2000", false }, { "5000", false }, { "1000000", false }, { "999999999", false } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "slXuatKho", stockquanover());
	}
}
