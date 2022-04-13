package com.wolvesres.truongnvn.voucher;



import java.io.IOException;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;

import exceldoing.ExcelGo;
import junit.framework.Assert;
/**
 * Kiểm tra ngày bắt đầu của voucher thất bại do chọn ngày bắt đầu sau ngày kết thúc
 * 
 * Kiểm tra ngày kết thúc của voucher thành công
 * */
public class TestDateVoucher {
	/**
	 * 
	 * */
	@BeforeClass(groups = { "" })
	public void beforClass() {

	}

	/**
	 * DataProvider for ngay ket thuc truoc ngay bat dau
	 * Kiểm tra ngày kết thúc của voucher thành công
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "datangaytruoc")
	public Object[][] data() {
		return new Object[][] { {XDate.toDate("12-04-2022", "dd-MM-yyyy"),XDate.toDate("11-04-2022", "dd-MM-yyyy"), false } };
	}

	/**
	 * DataProvider for ngay ket thuc sau ngay bat dau
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "datangaysau")
	public Object[][] datadateafter() {
		return new Object[][] { {XDate.toDate("12-04-2022", "dd-MM-yyyy"),XDate.toDate("14-04-2022", "dd-MM-yyyy"), false } };
	}
	/**
	 * TestCase ngay ket thuc sau ngay bat dau
	 * 
	 * @param date
	 * @param expected
	 */
	@Test(dataProvider = "datangaytruoc", groups = "ngaybatdausaungayketthuc", priority = 0)
	public void testDateVoucherBefore(Date startdate, Date enddate, Boolean expected) {
		Boolean actual = FormValidator.isDateBefore(startdate, enddate);
		Assert.assertEquals(expected, actual);
	}
	
	@Test(dataProvider = "datangaysau", groups = "ngaybatdautruocngayketthuc", priority = 1)
	public void testDateVoucherAfer(Date startdate, Date enddate, Boolean expected) {
		Boolean actual = FormValidator.isDateAfter(startdate, enddate);
		Assert.assertEquals(expected, actual);
	}
	
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "ngayBatDau,ngayKetThuc",datadateafter());
//	}
}
