package com.wolvesres.quanghn.banhang;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;
import exceldoing.ExcelGo;

/**
 * Kiểm tra nhập tiền khách đưa thất bại do nhập kí tự đặc biệt
 * Tên groups: NhapTienCoKiTyDB
 * @author huynh
 *
 */

/**
 * Kiểm tra nhập tiền khách đưa thất bại do bỏ trống Tên groups: NhapTienBoTrong
 * 
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
		return new Object[][] { { "324@#$#42324@#4", false }, { "349$#234@424234", false },
				{ "678^*78^*785685", false }, { "@@312321%45", false }, { "23!23@!#123", false } };
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
		if (!FormValidator.isFloatNumber(tien)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}

	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "NhapTienCoKiTyDB")
//	public void InFileExcel() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Tien", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
/////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * DataProvider for testValidNhapTienBoTrongFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data2() {
		return new Object[][] { { " ", false }, { "", false }, { "\t", false } };
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

	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "NhapTienBoTrong")
//	public void InFileExcel2() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Tien", data2());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
