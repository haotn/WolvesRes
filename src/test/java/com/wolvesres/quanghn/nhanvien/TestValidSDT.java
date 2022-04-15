package com.wolvesres.quanghn.nhanvien;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
/**
 * Kiểm tra định dạng số điện thoại thất bại do nhập chữ
 * @author huynh
 *
 */
public class TestValidSDT {
	/**
	 * Before class
	 */
	@BeforeClass
	public void beforClass() {
	}

	/**
	 * DataProvider for testValidSDTFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { "09qq784933", false }, { "09117849op", false }, { "la11784933", false }, { "09d1784n33", false }, { "0v11m84933", false }};
	}

	/**
	 * TestCase testValidSDTFail
	 * 
	 * @param sdt
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidSDTFail(String sdt, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidPhoneNumber(sdt)) {
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
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "SDT", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
