package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class StockQuanImportABCTestFail {
	@Test(dataProvider = "stockquanimportabc")
	public void f(String txtSoLuong, boolean expect) {
		boolean actual = DTool.checkStockImport(txtSoLuong);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockquanimportabc() {
		return new Object[][] { { "a", false }, { "abc", false }, { "a1b2c3", false }, { "aaaa2222aaaa", false },
				{ "1111a", false } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "slXuatKho", stockquanimportabc());
	}
}
