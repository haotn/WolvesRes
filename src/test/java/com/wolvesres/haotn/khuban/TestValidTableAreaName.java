package com.wolvesres.haotn.khuban;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

/**
 * Test valid table area name
 * 
 * @author Brian
 *
 */
public class TestValidTableAreaName {
	/**
	 * DataProvider for testInvalidTableAreaName
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataForTestTableAreaName")
	public Object[][] dataForTestTableAreaName() {
		return new Object[][] { { "", false }, { " ", false }, { "\t", false } };
	}

	@Test(dataProvider = "dataForTestTableAreaName", groups = "testTableAreaName")
	public void testInvalidTableAreaNameFail(String tableAreaName, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(tableAreaName)) {
			actual = false;
			System.out.println("Tên khu bàn không được để trống!");
		}else {
			System.out.println("Tên khu bàn hợp lệ!");
		}
		Assert.assertEquals(expected, actual);
	}
}
