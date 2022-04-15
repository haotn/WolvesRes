package com.wolvesres.quanghn.kho;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
/**
 * Kiểm tra giá xuất kho thất bại do nhập chữ
 * @author huynh
 *
 */
public class TestValidXuatKho {
	/**
	 * DataProvider for testValidXuatKhoNhapChuFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"fgjkfdkj", false}, {"hgjdsf", false}, {"Hvbxcb", false}, {"reytU", false}, {"jlhj2334", false}};
	}

	/**
	 * TestCase testValidXuatKhoNhapChuFail
	 * 
	 * @param gia
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidXuatKhoNhapChuFail(String gia, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isIntNumber(gia)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass
//	public void InFileExcel() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "Gia", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
