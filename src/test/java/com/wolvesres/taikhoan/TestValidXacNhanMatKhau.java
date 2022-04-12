package com.wolvesres.taikhoan;

import javax.swing.JFrame;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;
/**
 * Kiểm tra trường xác nhận mật khẩu thành công
 * @author huynh
 *
 */
public class TestValidXacNhanMatKhau {
	
  @BeforeClass
	public void beforClass() {
	}
  
  /**
	 * DataProvider for testValidXacNhanMatKhauSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"12345678", "12345678", true}, {"0987654", "0987654", true}, {"098123", "098123", true}};
	}

	/**
	 * TestCase testValidXacNhanMatKhauSuccess
	 * 
	 * @param password
	 * @param ConfirmNewPass
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidXacNhanMatKhauSuccess(String password,String ConfirmNewPass, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextEqual(password, ConfirmNewPass)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
}
