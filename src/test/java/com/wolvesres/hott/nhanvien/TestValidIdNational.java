package com.wolvesres.hott.nhanvien;

//import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import junit.framework.Assert;

//Kiểm tra định dạng căn cước công dân thất bại do nhập chữ 
//Kiểm tra định dạng căn cước công dân thất bại do nhập chứa kí tự khoảng trắng

public class TestValidIdNational {

	/**
	 * DataProvider for testValidIdNationalFailA (Thất bại do nhập chữ)
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dataA() {
		return new Object[][] {{ "06019y984842", false}, {"093202z05144", false}};
	}

	/**
	 * TestCase testValidIdNationalFailA (Thất bại do nhập chữ)
	 * 
	 * @param Id National
	 * @param expected
	 */
	@Test(dataProvider = "dataA", groups = "IdNationalFailA")
	public void testValidIdNationalFailA(String IdNational, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidIdNational(IdNational)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	/******************************************************************************/
	/**
	 * DataProvider for testValidIdNationalFailB (Thất bại do chứa kí tự khoảng trống)
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dataB() {
		return new Object[][] {{ "06019 984842", false}, {"093202 05144", false}};
	}

	/**
	 * TestCase testValidIdNationalFailB (Thất bại do chứa kí tự khoảng trống)
	 * 
	 * @param Id National
	 * @param expected
	 */
	@Test(dataProvider = "dataB", groups = "IdNationalFailB")
	public void testValidIdNationalFailB(String IdNational, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidIdNational(IdNational)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
}
