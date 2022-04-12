package com.wolvesres.ducvh.donvitinh;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class CalcUnitEmptyTestFail {
	@Test(dataProvider = "calcunitempty")
	public void f(boolean insert, String DVT, boolean expect) {
		boolean actual = ExtractedModule.checkCalcUnit(insert, DVT);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] calcunitempty() {
		// empty: rỗng, khoảng cách, tab, null
		return new Object[][] { { true, "", false }, { true, " ", false }, { true, "\t", false }, { true, null, false } };
	}
}
