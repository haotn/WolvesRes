package com.wolvesres.truongnvn.voucher;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
import junit.framework.Assert;
/**
 * Kiểm tra số lượng voucher thất bại do bỏ trống
 * */
public class TestSoluongVouvherBoTrong {
	/**
	 * 
	 * */
	@BeforeClass(groups = { "" })
	public void beforClass() {

	}

	/**
	 * DataProvider for Full name ko co khoảng trắng
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataSoluong")
	public Object[][] data() {
		return new Object[][] { {"", false },{"\t", false },{" ", false }};
	}

	/**
	 * TestCase số lượng Voucher bị bỏ trống
	 * 
	 * @param sl
	 * @param expected
	 */
	@Test(dataProvider = "dataSoluong", groups = "soluongnull", priority = 0)
	public void testValidSoLuongEmpty(String soluong, Boolean expected) {
		Boolean actual = FormValidator.isTextIsNotEmpty(soluong);
		Assert.assertEquals(expected, actual);
	}
//	
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "soLuong",data());
//	}
}
