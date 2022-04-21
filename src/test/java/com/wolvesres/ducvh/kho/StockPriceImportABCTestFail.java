package com.wolvesres.ducvh.kho;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class StockPriceImportABCTestFail {
	@Test(dataProvider = "stockpriceabc")
	public void f(String txtGia, boolean expect) {
		boolean actual = DTool.checkStockPriceImport(txtGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] stockpriceabc() {
		return new Object[][] { { "a11", false }, { "12a", false }, { "1a3", false }, { "aaa", false }, { "AZV", false }, { "giá có dấu", false }  };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "giaNhapKho", stockpriceabc());
	}
}
