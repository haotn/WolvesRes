package com.wolvesres.quanghn.nhanvien;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
/**
 * Kiểm tra định dạng email thành công
 * @author huynh
 *
 */
public class TestValidEmail {
	/**
	 * Before class
	 */
	@BeforeClass
	public void beforClass() {
	}


	/**
	 * DataProvider for testValidEmailSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { "huynhnhatquang281@gmail.com", true }, { "nhuttruong282@gmail.com", true }, { "haotnpc01545@fpt.edu.vn", true }, { "hottpc02096@fpt.edu.vn", true }, { "ducvhpc01395@fpt.edu.vn", true }};
	}

	/**
	 * TestCase testValidEmailSuccess
	 * 
	 * @param email
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidEmailSuccess(String email, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidEmail(email)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
		try {
			ExcelGo.writeExcel("D:\\demo.xlsx", 0, 1, 0, "email", data());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
