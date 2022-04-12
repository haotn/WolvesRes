package com.wolvesres.quanghn.kho;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

public class TestValidXuatKho {
	/**
	 * DataProvider for testValidXuatKhoNhapChuFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"fgjkfdkj", false}, {"hgjdsf", false}, {"cvbxcb", false}, {"reytu", false}, {"jlhj", false}};
	}

	/**
	 * TestCase testValidXuatKhoNhapChuFail
	 * 
	 * @param gia
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidXuatKhoNhapChuFail(String gia, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isNumber(gia)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
}
