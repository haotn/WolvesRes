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
 * Test voucher discount
 * 
 * @author Brian
 *
 */
public class TestVoucherDiscount {
	private DataGenerator dataGenerator;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dataGenerator = new DataGenerator();
	}

	/**
	 * DataProvider for TestVoucherDiscount
	 * 
	 * @return
	 */
	@DataProvider(name = "dataForTestVoucherDiscount")
	public Object[][] dataForTestVoucherDiscount() {
		Object[][] data = new Object[][] { { 0.1, true }, { 99.9, true }, { 50.5, true }, { 75.75, true },
				{ 25.25, true } };
		return data;
	}

	/**
	 * TestCase testVoucherDiscount
	 * 
	 * @param discount
	 * @param expected
	 */
	@Test(dataProvider = "dataForTestVoucherDiscount")
	public void testVoucherDiscountPass(double discount, Boolean expected) {
		Boolean actual = false;
		if (discount <= 100 && discount > 0) {
			actual = true;
			System.out.println("Phần trăm giảm giá của voucher hợp lệ!");
		} else {
			System.out.println("Phần trăm giảm giá của voucher không hợp lệ!");
		}
		Assert.assertEquals(actual, expected);
	}

	/**
	 * DataProvider for testVoucherDiscountLetter
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataForTestVoucherDiscountLetter")
	public Object[][] dataForTestVoucherDiscountLetter() {
		Object[][] data = new Object[5][2];
		for (int i = 0; i < 5; i++) {
			data[i][0] = dataGenerator.generateTextAndNumber(0.1, 100);
			data[i][1] = false;
		}
		return data;
	}

	/**
	 * TestCase testVoucherDiscountLetter
	 * 
	 * @param discount
	 * @param expected
	 */
	@Test(dataProvider = "dataForTestVoucherDiscountLetter")
	public void testVoucherDiscountIsNumberFail(String discount, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isIntNumber(discount)) {
			actual = false;
			System.out.println("Phần trăm giảm giá phải là số!");
		} else {
			System.out.println("Phần trăm giảm hợp lệ (là số)!");
		}
		Assert.assertEquals(expected, actual);
	}

	@DataProvider(name = "dataForTestVoucherDiscountGreaterThan100")
	public Object[][] dataForTestVoucherDiscountGreaterThan100() {
		Object[][] data = new Object[][] { { 526.255, false }, { 809.036, false }, { 794.352, false },
				{ 348.832, false }, { 523.381, false }, { 100.001, false }, { 101.101, false } };
		return data;
	}

	@Test(dataProvider = "dataForTestVoucherDiscountGreaterThan100", groups = "discountGreaterThan100")
	public void testVoucherDiscountGreaterThan100Fail(double discount, Boolean expected) {
		Boolean actual = true;
		if (discount > 100) {
			actual = false;
			System.out.println("Phần trăm giảm giá phải là số nằm trong khoảng 0 đến 100!");
		} else {
			System.out.println("Phần trăm giảm giá hợp lệ!");
		}
		Assert.assertEquals(expected, actual);
	}

//	@AfterClass
//	public void writeResult() {
//		Object[][] dataWrite = new Object[dataForTestVoucherDiscountGreaterThan100().length][1];
//		for (int i = 0; i < dataForTestVoucherDiscountGreaterThan100().length; i++) {
//			dataWrite[i][0] = dataForTestVoucherDiscountGreaterThan100()[i][0];
//		}
//		try {
//			ExcelGo.writeExcelv2("excel-file/asm-temp-demo.xlsx", 2, 275, 6, "PhanTramGiamGia", dataWrite);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
