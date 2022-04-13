package com.wolvesres.truongnvn.nhanvien;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
import junit.framework.Assert;

/**
 * Kiểm tra định dạng họ tên thất bại do nhập tên không có khoảng trắng
 * 
 * */
public class TestValidFullname {
	/**
	 * 
	 * */
	@BeforeClass(groups = { ""})
	public void beforClass() {
 
	}
	/**
	 * DataProvider for Full name ko co khoảng trắng
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataFullname")
	public Object[][] data() {
		 return new Object[][] {
			 {"nguyenvannhuttruong",false}, {"huynhnhatquang",false},{"tieunhuthao",false},{"tranthanhho",false},{"vanhuuduc",false}
		 };
	}
	/**
	 * TestCse fullname không có ký tự khoảng trắng
	 * 
	 * @param sdt
	 * @param expected
	 */
	@Test(dataProvider = "dataFullname", groups = "fullnamenospace", priority = 0)
	public void testFullNameNoSpace( String fullnamae, Boolean expected) {
		Boolean actual = FormValidator.isTextContainsSpace(fullnamae);
		Assert.assertEquals(expected, actual);
	}
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "Fullname",data());
//	}
}
