package com.wolvesres.hott.voucher;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import exceldoing.ExcelGo;
import junit.framework.Assert;

//Kiểm tra định dạng mã voucher thành công

public class TestVoucherCode {
	/**
	 * DataProvider for testVoucherCodeSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { "A4dn3ndgjd", true}, { "Bj3djdbfhd8", true}, { "giftcodevip", true}, { "GIFTCODEVIP", true}, {"DAYlaMAvoucher", true}};
	}

	/**
	 * TestCase testVoucherCodeSuccess
	 * 
	 * @param voucherCode
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testVoucherCodeSuccess(String voucherCode, Boolean expected) {
		Boolean actual = false;
		if (voucherCode.length()>=5) {
			actual = true;
		}
		Assert.assertEquals(expected, actual);

	}
	
//	@AfterClass
//	public void exportExcel() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "voucherCode", data());
//	}
}
