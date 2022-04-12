package com.wolvesres.ducvh.danhmuc;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class CategoryEmptyTestFail {
	@Test(dataProvider = "categoryemptyfail")
	public void f(boolean insert, String MaDM, String TenDM, boolean matHang, boolean expect) throws IOException {
		boolean actual = ExtractedModule.checkCategory(insert, MaDM, TenDM, matHang);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] categoryemptyfail() {
		// empty: rỗng, khoảng cách, tab, null
		return new Object[][] { { true, "", "Ten Danh Muc 1", true, false },
				{ true, " ", "Ten Danh Muc 1", true, false }, { true, "\t", "Ten Danh Muc 1", true, false },
				{ true, null, "Ten Danh Muc 1", true, false } };
	}
	
	@AfterClass
	public void excelGo() throws IOException {
		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 4, 6, "insert,MaDM,TenDM,matHang", categoryemptyfail());
	}
}
