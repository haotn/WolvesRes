package com.wolvesres.hott.nhanvien;

import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
import junit.framework.Assert;

//Kiểm tra định dạng mật khẩu thành công
//Kiểm tra trường xác nhận mật khẩu thất bại do bỏ trống

public class TestValidPassword {

	/**
	 * DataProvider for testValidPasswordSuccess (Thành công)
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dataA() {
		return new Object[][] {{ "ksng39sd", true}, {"hfnfkd32sdd", true}, {"123hde", true}, {"123456789mjskehf", true}, {"123456789", true}, {"abchsjds", true}};
	}

	/**
	 * TestCase testValidPasswordSuccess (Thành công)
	 * 
	 * @param Password
	 * @param expected
	 */
	@Test(dataProvider = "dataA", groups = "PasswordSuccess")
	public void testValidPasswordSuccess(String password, Boolean expected) {
		Boolean actual = false;
		if (FormValidator.validatePass(password)) {
			actual = true;
		}
		Assert.assertEquals(expected, actual);

	}
	/******************************************************************************/
	/**
	 * DataProvider for testValidPasswordFail(Thất bại do bỏ trống)
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dataB() {
		return new Object[][] {{ "", false}, {" ", false}, {"\t", false}};
	}

	/**
	 * TestCase testValidPasswordFail(Thất bại do bỏ trống)
	 * 
	 * @param Password
	 * @param expected
	 */
	@Test(dataProvider = "dataB", groups = "PasswordFail")
	public void testValidPasswordFail(String password, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(password)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	

//	@AfterClass(groups = "PasswordSuccess")
//	public void exportExcelA() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "password", dataA());
//	}
	
//	@AfterClass(groups = "PasswordFail")
//	public void exportExcelB() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "password", dataB());
//	}
}
