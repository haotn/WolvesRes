package com.wolvesres.hott.nhanvien;

import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;

import exceldoing.ExcelGo;
import junit.framework.Assert;

//Kiểm tra định dạng thứ tự trước sau của ngày thất bại do chọn dưới 18 tuổi

public class TestValidYearOlds {

	/**
	 * DataProvider for testValidYearOldsFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"7-5-2014", false }, { "8-10-2006", false }, { "12-11-2007", false }};
	}
	

	/**
	 * TestCase testValidYearOldsFail
	 * 
	 * @param birthday
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidYearOldsFail(String birthday, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidAge(XDate.toDate(birthday, "dd-MM-yyyy"))) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	
//	@AfterClass
//	public void exportExcel() throws Exception {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "birthday", data());
//	}
}
