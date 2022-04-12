package com.wolvesres.hott.donvitinh;

//import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import junit.framework.Assert;

//Kiểm tra định dạng đơn vị tính thành công

public class TestUnit {

	/**
	 * DataProvider for testUnitSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { "lon", true }, { "chai", true }, { "ly", true }};
	}

	/**
	 * TestCase testUnitSuccess
	 * 
	 * @param unit
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testUnitSuccess(String unit, Boolean expected) {
		Boolean actual = false;
		if (FormValidator.isTextIsNotEmpty(unit)) {
			actual = true;
		}
		Assert.assertEquals(expected, actual);

	}
}
