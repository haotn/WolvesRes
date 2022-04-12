package com.wolvesres.ducvh.donvitinh;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class CalcUnitDuplicateTestFail {
	@Test(dataProvider = "calcunitduplicate")
	public void f(boolean insert, String DVT, boolean expect) {
		boolean actual = ExtractedModule.checkCalcUnit(insert, DVT);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] calcunitduplicate() {
		// Ly: đơn vị tính đã có trong CSDL
		return new Object[][] { { true, "Ly", false } };
	}
}
