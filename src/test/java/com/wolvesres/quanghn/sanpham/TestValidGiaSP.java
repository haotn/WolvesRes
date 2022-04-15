package com.wolvesres.quanghn.sanpham;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;
/**
 * Kiểm tra giá sản phẩm thất bại do nhập chữ
 * tên groups: GiaSP
 * @author huynh
 */

import exceldoing.ExcelGo;
/**
 * Kiểm tra giá sản phẩm thất bại do nhập chữ
 * tên groups: GiaSPNhapChu
 * @author huynh
 */

/**
 * Kiểm tra giá sản phẩm thất bại do nhập kí tự đặc biệt
 * tên groups: GiaSPKiTuDacBiet
 * @author huynh
 */
public class TestValidGiaSP {

	/**
	 * DataProvider for testValidGiaSPFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"abcdi", false}, {"posdfghfd", false}, {"fhhfdaa", false}, {"bnmc", false}, {"eeqqwr", false}};
	}

	/**
	 * TestCase testValidGiaSPFail
	 * 
	 * @param gia
	 * @param expected
	 */
	@Test(dataProvider = "data", groups = "GiaSPNhapChu")
	public void testValidGiaSPFail(String gia, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isFloatNumber(gia)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "GiaSP")
//	public void InFileExcel() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Gia", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	
	/**
	 * DataProvider for testValidGiaSPKiTuDatBietFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data2() {
		return new Object[][] { {"@@2#$22", false}, {"0@#034@#", false}, {"23@#42@!", false}, {"@#123@#@#4", false}, {"$%#1234@", false}};
	}

	/**
	 * TestCase testValidGiaSPKiTuDatBietFail
	 * 
	 * @param gia
	 * @param expected
	 */
	@Test(dataProvider = "data2", groups = "GiaSPKiTuDacBiet")
	public void testValidGiaSPKiTuDacBietFail(String gia, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isFloatNumber(gia)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "GiaSPKiTuDacBiet")
//	public void InFileExcel2() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Gia", data2());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
