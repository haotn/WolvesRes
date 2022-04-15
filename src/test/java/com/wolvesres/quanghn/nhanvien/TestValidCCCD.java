package com.wolvesres.quanghn.nhanvien;

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
 * Kiểm tra định dạng căn cước công dân thất bại do bỏ trống
 * tên groups: CCCDBoTrong
 * @author huynh
 *
 */
/**
 * Kiểm tra định dạng căn cước công dân thất bại do nhập sai mã tỉnh "Fail do thiếu mã tỉnh"
 * tên groups: CCCDFailTinh
 * @author huynh
 *
 */
public class TestValidCCCD {
	private DataGenerator datage;

	/**
	 * Before class
	 */
	@BeforeClass(groups = { "CCCDFailTinh", "CCCDBoTrong" })
	public void beforClass() {
		datage = new DataGenerator();
	}

	/**
	 * DataProvider for testValidCCCDFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dataFail() {
		return new Object[][] { { " ", false}, { "", false}, { null, false}, { "\t", false}};
	}

	/**
	 * TestCase testValidCCCDFail
	 * 
	 * @param sdt
	 * @param expected
	 */
	@Test(dataProvider = "dataFail", groups = "CCCDBoTrong")
	public void testValidCCCDFail(String cccd, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextIsNotEmpty(cccd)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "CCCDBoTrong")
//	public void InFileExcel() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "CCCD", dataFail());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	/**
	 * DataProvider for testValidCCCDFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] dataMaTinh() {
		return new Object[][] { {"091202014730", false }, {"096091276189", false }, {"093091826874", false }, {"092091273586", false }, {"099162763975", false }};
	}

	/**
	 * TestCase testValidCCCDFail
	 * 
	 * @param cccd
	 * @param expected
	 */
	@Test(dataProvider = "dataMaTinh", groups = "CCCDFailTinh")
	public void testValidCCCDMaTinhFail(String cccd, Boolean expected) {
		
		Boolean actual = true;
		if (!datage.listTinh().equals(cccd)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass(groups = "CCCDFailTinh")
//	public void InFileExcel2() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "CCCD", dataMaTinh());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
