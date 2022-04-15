package com.wolvesres.quanghn.nhanvien;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
		return new Object[][] { { "quanghnpc01597@fpt.edu.vn", true }, { "truongnvnpc01752@gmail.com", true }, { "haotnpc01545@canhsat-cnc.vn", true }, { "hottpc02096@lookout.vn", true }, { "ducvhpc01395@mmgroup.vn", true }};
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
	}
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass
//	public void InFileExcel() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Email", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
