package com.wolvesres.quanghn.banhang;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;
/**
 * Kiểm tra nhập tiền khách đưa thất bại do nhập kí tự đặc biệt
 * Tên groups: NhapTienCoKiTyDB
 * @author huynh
 *
 */

/**
 * Kiểm tra nhập tiền khách đưa thất bại do bỏ trống
 * Tên groups: NhapTienBoTrong
 * @author huynh
 *
 */
public class TestValidNhapTien {
	/**
	 * Before class
	 */
	@BeforeClass
	public void beforClass() {
	}


	/**
	 * DataProvider for testValidNhapTienFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"324@#$#42324@#4", false}, {"349$#234@424234", false}, {"678^*78^*785685", false}, {"@@312321%45", false}, {"23!23@!#123", false}};
	}

	/**
	 * TestCase testValidNhapTienFail
	 * 
	 * @param tien
	 * @param expected
	 */
	@Test(dataProvider = "data", groups = "NhapTienCoKiTyDB")
	public void testValidNhapTienFail(String tien, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isNumber(tien)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
/////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * DataProvider for testValidNhapTienBoTrongFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data2() {
		return new Object[][] { { " ", false}};
	}

	/**
	 * TestCase testValidNhapTienBoTrongFail
	 * 
	 * @param tien
	 * @param expected
	 */
	@Test(dataProvider = "data2", groups = "NhapTienBoTrong")
	public void testValidNhapTienBoTrongFail(String tien, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(tien)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
}
