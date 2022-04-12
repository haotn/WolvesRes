package com.wolvesres.ducvh.danhmuc;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class CategoryTestSuccess {
	@Test(dataProvider = "categorysuccess")
	public void f(boolean insert, String MaDM, String TenDM, boolean matHang, boolean expect) {
		boolean actual = ExtractedModule.checkCategory(insert, MaDM, TenDM, matHang);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] categorysuccess() {
		return new Object[][] { new Object[] { true, "DM1", "Ten Danh Muc 1", true, true } };
	}
}
