package com.wolvesres.truongnvn.sanpham;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
import junit.framework.Assert;
/**
 * Kiểm tra tên sản phẩm thất bại do bỏ trống
 * */
public class TestNameSanPham {
	/**
	 * 
	 * */
	@BeforeClass(groups = {"sanphamnospace"})
	public void beforClass() {

	}

	/**
	 * DataProvider for Full name ko co khoảng trắng
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataSanPham")
	public Object[][] data() {
		return new Object[][] { { "", false }};
	}

	/**
	 * TestCse Kiểm tra tên sản phẩm thất bại do bỏ trống
	 * 
	 * @param sdt
	 * @param expected
	 */
	@Test(dataProvider = "dataSanPham", groups = "sanphamnospace", priority = 0)
	public void testValidSpDoBoTrong(String sanPham, Boolean expected) {
		Boolean actual = FormValidator.isTextIsNotEmpty(sanPham);
		Assert.assertEquals(expected, actual);
	}
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "tenSanPham",data());
//	}
}
