package com.wolvesres.quanghn.taikhoan;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
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
		return new Object[][] { {"12345678", "12345678", true}, {"0987654", "0987654", true}, {"098123", "098123", true}, {"345678", "345678", true}, {"293847", "293847", true}};
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
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass
//	public void InFileExcel() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Password,ConfirmNewPass", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
