package com.wolvesres.hott.khuban;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import junit.framework.Assert;

//Kiểm tra định dạng tên khu bàn thành công

public class TestTableAreaName {
	/**
	 * DataProvider for testTableAreaNameSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { "A", true}, { "Tần Thượng", true}};
	}

	/**
	 * TestCase testTableAreaNameSuccess
	 * 
	 * @param tableName
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testTableAreaNameSuccess(String tableAreaName, Boolean expected) {
		Boolean actual = false;
		if (FormValidator.isTextIsNotEmpty(tableAreaName)) {
			actual = true;
		}
		Assert.assertEquals(expected, actual);

	}
}
