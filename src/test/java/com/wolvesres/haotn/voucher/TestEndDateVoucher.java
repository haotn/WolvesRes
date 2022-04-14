package com.wolvesres.haotn.voucher;

import java.io.IOException;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
/**
 * Test end date voucher
 * @author Brian
 *
 */

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;

import exceldoing.ExcelGo;

public class TestEndDateVoucher {
	private DataGenerator dataGenerator;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dataGenerator = new DataGenerator();
	}

	/**
	 * DataProvider for testEndDateVoucherFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataForValidEndDate")
	public Object[][] dataForEndateVoucher() {
		Object[][] data = new Object[5][3];
		for (int i = 0; i < 5; i++) {
			data[i][0] = dataGenerator.generateDate(2017, 2019);
			data[i][1] = dataGenerator.generateDate(2020, 2021);
			data[i][2] = false;
		}
		return data;
	}

	/**
	 * TestCase testEndDateVoucherFail
	 * 
	 * @param endDate
	 * @param startDate
	 * @param expected
	 */
	@Test(dataProvider = "dataForValidEndDate")
	public void testEndDateVoucherFail(Date endDate, Date startDate, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isDateAfter(endDate, startDate)) {
			actual = false;
			System.out.println("Ngày kết thúc phải sau ngày bắt đầu!");
		} else {
			System.out.println("Ngày kết thúc hợp lệ!");
		}
		Assert.assertEquals(actual, expected);
	}

//	@AfterClass
//	public void writeResult() {
//		Object[][] dataWrite = new Object[dataForEndateVoucher().length][2];
//		for (int i = 0; i < dataForEndateVoucher().length; i++) {
//			dataWrite[i][0] = XDate.toString((Date) dataForEndateVoucher()[i][0], "dd-MM-yyyy");
//			dataWrite[i][1] = XDate.toString((Date) dataForEndateVoucher()[i][1], "dd-MM-yyyy");
//			System.out.println(dataWrite[i][0]);
//			System.out.println(dataWrite[i][1]);
//		}
//		try {
//			ExcelGo.writeExcelv2("excel-file/asm-temp-demo.xlsx", 2, 290, 6, "NgayBatDau,NgayKetThuc", dataWrite);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
