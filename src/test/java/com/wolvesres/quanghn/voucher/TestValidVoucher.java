package com.wolvesres.quanghn.voucher;

import org.testng.Assert;
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
		return new Object[][] { {"-23412", false}, {"-23324", false}, {"-56654", false}, {"-456223", false}, {"-76747", false}};
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * DataProvider for testValidVoucherSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data2() {
		return new Object[][] { {"232", true}, {"231", true}, {"456", true}, {"6754", true}, {"1234", true}};
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
		if (!FormValidator.isNumber(soluong) || !FormValidator.isGreaterThan(Integer.parseInt(soluong), 0)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * DataProvider for testValidVoucherChonNgayFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data3() {
		return new Object[][] { {"04-07-2020", false}, {"04-07-2020", false}, {"11-03-2021", false}, {"02-12-2020", false}, {"09-09-2021", false}};
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
	
}
