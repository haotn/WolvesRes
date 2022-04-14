package com.wolvesres.hott.ban;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
import junit.framework.Assert;

//Kiểm tra định dạng tên bàn thất bại do bỏ trống

public class TestTableName {
	/**
	 * DataProvider for testTableNameFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { "", false}, { " ", false}, { "\t", false}};
	}

	/**
	 * TestCase testTableNameFail
	 * 
	 * @param tableName
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testTableNameFail(String tableName, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(tableName)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	
//	@AfterClass
//	public void exportExcel() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "tableName", data());
//	}
}
