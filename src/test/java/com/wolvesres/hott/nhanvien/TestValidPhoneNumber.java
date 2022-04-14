package com.wolvesres.hott.nhanvien;

import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
import junit.framework.Assert;

//Kiểm tra định dạng số điện thoại thất bại do bỏ trống thông tin

public class TestValidPhoneNumber {
	/**
	 * DataProvider for testValidPhoneNumberFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] {{ "", false}, {" ", false}, {"\t", false}};
	}

	/**
	 * TestCase testValidPhoneNumberFail
	 * 
	 * @param phone number
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidPhoneNumberFail(String phoneNumber, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(phoneNumber)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	
//	@AfterClass
//	public void exportExcel() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "phoneNumber", data());
//	}
}
