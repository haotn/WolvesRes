package com.wolvesres.quanghn.voucher;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
/**
 * Kiểm tra giá giảm của voucher thất bại do nhập số âm
 * Tên groups: VoucherGia
 * @author huynh
 */

import exceldoing.ExcelGo;

/**
 * Kiểm tra số lượng voucher thành công
 * Tên groups: VoucherSoLuong
 * @author huynh
 */

/**
 * Kiểm tra ngày bắt đầu của voucher thất bại do chọn ngày bắt đầu trong quá khứ
 * Tên groups: VoucherChonNgay
 * @author huynh
 */
public class TestValidVoucher {
 
	/**
	 * Before class
	 */
	@BeforeClass
	public void beforClass() {
	}

	/**
	 * DataProvider for testValidVoucherFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"-23412", false}, {"-2332", false}, {"-56,89", false}, {"-456223", false}, {"-767,8", false}};
	}

	/**
	 * TestCase testValidVoucherFail
	 * 
	 * @param giamGia
	 * @param expected
	 */
	@Test(dataProvider = "data", groups = "VoucherGia")
	public void testValidVoucherFail(String giamGia, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isGreaterThan(Float.parseFloat(giamGia), 0)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "VoucherGia")
//	public void InFileExcel() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Gia Voucher", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * DataProvider for testValidVoucherSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data2() {
		return new Object[][] { {"232", true}, {"23", true}, {"4", true}, {"6754", true}, {"123456", true}};
	}

	/**
	 * TestCase testValidVoucherSuccess
	 * 
	 * @param soluong
	 * @param expected
	 */
	@Test(dataProvider = "data2", groups = "VoucherSoLuong")
	public void testValidVoucherSuccess(String soluong, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isIntNumber(soluong) || !FormValidator.isGreaterThan(Integer.parseInt(soluong), 0)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "VoucherSoLuong")
//	public void InFileExcel2() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "So Luong Voucher", data2());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * DataProvider for testValidVoucherChonNgayFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data3() {
		return new Object[][] { {"14-07-2020", false}, {"04-06-2018", false}, {"11-03-2015", false}, {"02-12-2019", false}, {"09-09-2021", false}};
	}

	/**
	 * TestCase testValidVoucherChonNgayFail
	 * 
	 * @param ngayBatDau
	 * @param expected
	 */
	@Test(dataProvider = "data3", groups = "VoucherChonNgay")
	public void testValidVoucherChonNgayFail(String ngayBatDau, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isBeginDateValid(XDate.toDate(ngayBatDau, "dd-MM-yyyy"))) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "VoucherChonNgay")
//	public void InFileExcel3() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Ngay Bat Dau Voucher", data3());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
