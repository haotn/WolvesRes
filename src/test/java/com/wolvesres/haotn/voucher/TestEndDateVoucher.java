package com.wolvesres.haotn.voucher;

import java.util.Date;

import org.testng.Assert;
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
		Object[][] data = new Object[100][3];
		for (int i = 0; i < 100; i++) {
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
		}
		Assert.assertEquals(expected, actual);

	}

}
