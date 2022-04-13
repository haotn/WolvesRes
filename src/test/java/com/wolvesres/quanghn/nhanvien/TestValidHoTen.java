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
 * Kiểm tra định dạng họ tên thất bại do bỏ trống
 * Tên groups: FullNameBoTrong
 * @author huynh
 *
 */

/**
 * Kiểm tra định dạng họ tê thất bại do nhập số
 * Tên groups: FullNameNhapSo
 * @author huynh
 *
 */
public class TestValidHoTen {
	/**
	 * Before class
	 */
	@BeforeClass
	public void beforClass() {
	}


	/**
	 * DataProvider for testValidFullNameFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { " ", false}, { "", false}, { null, false}, { "\t", false}};
	}

	/**
	 * TestCase testValidFullNameFail
	 * 
	 * @param fullname
	 * @param expected
	 */
	@Test(dataProvider = "data", groups = "FullNameBoTrong")
	public void testValidFullNameFail(String fullname, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(fullname)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	
	/**
	 * Hàm xuất file Excel
	 */
	@AfterClass(groups = "FullNameBoTrong")
	public void InFileExcel() {
		try {
			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Fullname", data());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * DataProvider for testValidFullNameNhapSoFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data2() {
		return new Object[][] { {"345345435", false}, {"343424", false}, {"2343415", false}, {"76796", false}, {"12368", false}};
	}

	/**
	 * TestCase testValidFullNameNhapSoFail
	 * 
	 * @param fullname
	 * @param expected
	 */
	@Test(dataProvider = "data2", groups = "FullNameNhapSo")
	public void testValidFullNameNhapSoFail(String fullname, Boolean expected) {
		Boolean actual = true;
		if (FormValidator.isNumber(fullname)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Hàm xuất file Excel
	 */
	@AfterClass(groups = "FullNameNhapSo")
	public void InFileExcel2() {
		try {
			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Fullname", data2());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
