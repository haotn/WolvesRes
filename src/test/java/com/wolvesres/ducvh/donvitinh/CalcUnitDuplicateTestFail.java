package com.wolvesres.ducvh.donvitinh;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class CalcUnitDuplicateTestFail {
	@Test(dataProvider = "calcunitduplicate")
	public void f(boolean insert, String DVT, boolean expect) {
		boolean actual = DTool.checkCalcUnit(insert, DVT);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] calcunitduplicate() {
		// Ly: đơn vị tính đã có trong CSDL
		return new Object[][] { { true, "Lon", false }, { true, "Chai", false }, { true, "Chiếc", false },
				{ true, "Ly", false }, { true, "Hộp", false } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "isInsert, donViTinh", calcunitduplicate());
	}
}
