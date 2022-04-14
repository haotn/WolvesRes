package com.wolvesres.hott.voucher;

import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.XDate;

import exceldoing.ExcelGo;
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
		return new Object[][] { { XDate.toDate("12-4-2020", "dd-MM-yyyy"), false}, { XDate.toDate("13-4-2020", "dd-MM-yyyy"), false}, 
			{ XDate.toDate("12-4-2021", "dd-MM-yyyy"), false}, { XDate.toDate("13-4-2021", "dd-MM-yyyy"), false},
			{ XDate.toDate("12-4-2021", "dd-MM-yyyy"), false}, { XDate.toDate("13-4-2021", "dd-MM-yyyy"), false}};
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
	
//	@AfterClass
//	public void exportExcel() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "voucherEndDay", data());
//	}
}
