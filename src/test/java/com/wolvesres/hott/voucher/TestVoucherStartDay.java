package com.wolvesres.hott.voucher;

import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.XDate;

import junit.framework.Assert;

public class TestVoucherStartDay {
	/**
	 * DataProvider for testVoucherStartDaySuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { XDate.toDate("13-4-2022", "dd-MM-yyyy"), true}, { XDate.toDate("14-4-2022", "dd-MM-yyyy"), true}};
	}

	/**
	 * TestCase testVoucherStartDaySuccess
	 * 
	 * @param voucherStartDay
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testVoucherStartDaySuccess(Date voucherStartDay, Boolean expected) {
		Boolean actual = false;
		if (voucherStartDay.equals(new Date()) || voucherStartDay.after(new Date())) {
			actual = true;
		}
		Assert.assertEquals(expected, actual);

	}
}
