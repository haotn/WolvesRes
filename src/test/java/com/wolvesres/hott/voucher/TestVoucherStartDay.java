package com.wolvesres.hott.voucher;

import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.XDate;

import exceldoing.ExcelGo;
import junit.framework.Assert;

public class TestVoucherStartDay {
	/**
	 * DataProvider for testVoucherStartDaySuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { XDate.toDate("15-4-2022", "dd-MM-yyyy"), true}, { XDate.toDate("16-4-2022", "dd-MM-yyyy"), true}, 
			{ XDate.toDate("15-4-2023", "dd-MM-yyyy"), true}, { XDate.toDate("16-4-2023", "dd-MM-yyyy"), true},
			{ XDate.toDate("15-4-2024", "dd-MM-yyyy"), true}, { XDate.toDate("16-4-2024", "dd-MM-yyyy"), true}};
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
	
//	@AfterClass
//	public void exportExcel() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "voucherStartDay", data());
//	}
}
