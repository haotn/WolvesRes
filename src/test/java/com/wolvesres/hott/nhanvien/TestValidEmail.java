package com.wolvesres.hott.nhanvien;

//import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import junit.framework.Assert;

//Kiểm tra định dạng email thất bại do viết số ở đầu

public class TestValidEmail {

	/**
	 * DataProvider for testValidEmailFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { "1testmotcaigido@gmail.com", false }, { "2testmotcaigido@gmail.com", false }, { "3testmotcaigido@gmail.com", false }};
	}

	/**
	 * TestCase testValidEmailFail
	 * 
	 * @param email
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidEmailFail(String email, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidEmail(email)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
}
