package com.wolvesres.ducvh.ban;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class checkTableSuccess {
	@Test(dataProvider = "tablesuccess")
	public void f(String tenBan, boolean expect) {
		boolean actual = DTool.checkTable(tenBan);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] tablesuccess() {
		return new Object[][] { { "ten ban khong dau", true }, { "tên bàn có dấu", true }, { "tên bàn có só 2", true },
				{ "tên bàn có !@#", true }, { "123345", true }, { "!@#$%^", true } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "tenBan", tablesuccess());
	}

}
