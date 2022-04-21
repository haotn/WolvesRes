package com.wolvesres.ducvh.danhmuc;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class CategoryTestSuccess {
	@Test(dataProvider = "categorysuccess")
	public void f(boolean insert, String MaDM, String TenDM, boolean matHang, boolean expect) {
		boolean actual = DTool.checkCategory(insert, MaDM, TenDM, matHang);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] categorysuccess() {
		return new Object[][] { { true, "ma danh muc", "ten danh muc", true, true },
				{ true, "mã danh mục", "tên danh mục", true, true },
				{ true, "mã danh mục 1", "tên danh mục 2", true, true },
				{ true, "MDM001", "@#!@#!", true, true },
				{ false, "mã danh mục 1", "tên danh mục 2", true, true } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "isInsert,maDanhMuc,tenDanhMuc,isMatHang", categorysuccess());
	}
}
