package com.wolvesres.hott.voucher;

import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.XDate;

import junit.framework.Assert;


//Kiểm tra ngày kết thúc của voucher thất bại do chọn ngày kết thúc trong quá khứ

public class TestVoucherEndDay {
	/**
	 * DataProvider for testVoucherEndDayFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { XDate.toDate("13-4-2021", "dd-MM-yyyy"), false}, { XDate.toDate("14-4-2021", "dd-MM-yyyy"), false}};
	}

	/**
	 * TestCase testVoucherEndDayFail
	 * 
	 * @param voucherEndDay
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testVoucherEndDayFail(Date voucherEndDay, Boolean expected) {
		Boolean actual = true;
		if (voucherEndDay.before(new Date())) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
}
