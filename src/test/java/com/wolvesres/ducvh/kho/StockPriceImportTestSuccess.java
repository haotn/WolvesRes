package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class StockPriceImportTestSuccess {
	@Test(dataProvider = "stockprice")
	public void f(String txtGia, boolean expect) {
		boolean actual = DTool.checkStockPriceImport(txtGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockprice() {
		return new Object[][] { { "1", true }, { "10", true }, { "100", true }, { "500", true }, { "999", true } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "giaNhapKho", stockprice());
	}
}
