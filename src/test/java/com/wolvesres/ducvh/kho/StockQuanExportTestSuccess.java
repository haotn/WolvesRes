package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class StockQuanExportTestSuccess {
	@Test(dataProvider = "stockquan")
	public void f(String txtSoluong, boolean expect) {
		boolean actual = DTool.checkStockQuanExport(txtSoluong);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockquan() {
		return new Object[][] { { "2", true }, { "10", true }, { "100", true }, { "500", true }, { "999", true } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "slXuatKho", stockquan());
	}
}
