package com.wolvesres.haotn.voucher;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;

/**
 * Test Voucher Code
 * 
 * @author Brian
 *
 */
public class TestVoucherCode {
	public DataGenerator dataGenerator;

	/**
	 * BeforeClass - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dataGenerator = new DataGenerator();
	}

	@DataProvider(name = "dataForTestVoucherCode")
	public Object[][] dataForTestVoucherCode() {
		Object[][] data = new Object[][] { { "", false }, { " ", false }, { "\t", false } };
		return data;
	}

	/**
	 * TestCase testInvalidVoucherCode
	 */
	@Test(dataProvider = "dataForTestVoucherCode", groups = "testVoucherCodeEmpty")
	public void testVoucherCodeNotEmptyFail(String voucherCode, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(voucherCode)) {
			actual = false;
			System.out.println("Mã voucher không được để trống!");
		} else {
			System.out.println("Mã voucher hợp lệ!");
		}
		Assert.assertEquals(actual, expected);
	}

	/**
	 * DataProvider for testVoucherCodeLength
	 * 
	 * @return
	 */
	@DataProvider(name = "dataForTestVoucherCodeLength")
	public Object[][] dataForTestVoucherCodeLength() {
		Object[][] data = new Object[][] { { "t", false }, { "$", false }, { "38", false }, { "wNk", false },
				{ "7m", false }, { "dvg3", false } };
		return data;
	}

	@Test(dataProvider = "dataForTestVoucherCodeLength", groups = "testVoucherCodeLength")
	public void testVoucherCodeLengthFail(String voucherCode, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidTextMinLength(voucherCode, 5)) {
			actual = false;
			System.out.println("Mã voucher phải có ít nhất 5 ký tự!");
		} else {
			System.out.println("Mã voucher hợp lệ!");
		}
		Assert.assertEquals(actual, expected);
	}

//	@AfterClass
//	public void writeResult() {
//		Object[][] dataWrite = new Object[dataForTestVoucherCodeLength().length][1];
//		for (int i = 0; i < dataForTestVoucherCodeLength().length; i++) {
//			dataWrite[i][0] = dataForTestVoucherCodeLength()[i][0];
//		}
//		try {
//			ExcelGo.writeExcelv2("excel-file/asm-temp-demo.xlsx", 2, 258, 6, "MaVoucher", dataWrite);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
