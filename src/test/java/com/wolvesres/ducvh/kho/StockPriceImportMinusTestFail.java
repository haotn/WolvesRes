package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class StockPriceImportMinusTestFail {
	@Test(dataProvider = "stockpriceminus")
	public void f(String txtGia, boolean expect) {
		boolean actual = ExtractedModule.checkStockPriceImport(txtGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockpriceminus() {
		return new Object[][] { { "-1", false }, { "-100", false }, { "-999", false }, { "-99999999999", false } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "giaNhapKho", stockpriceminus());
	}
}
