package com.wolvesres.quanghn.kho;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelNhapKho;

import exceldoing.ExcelGo;

/**
 * Kiểm tra số lượng nhập kho thất bại do nhập số âm
 * 
 * @author huynh
 *
 */
public class TestValidNhapKho {
	/**
	 * Before class
	 */
	@BeforeClass
	public void beforClass() {
	}

	/**
	 * DataProvider for testValidNhapKhoSoAmFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { { -5, false }, { -56, false }, { -12, 9, false }, { -593485, false }, { -231, false } };
	}

	/**
	 * TestCase testValidNhapKhoSoAmFail
	 * 
	 * @param soluong
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidNhapKhoSoAmFail(int soluong, Boolean expected) {
		Boolean actual = true;
		if (FormValidator.isLessThan(soluong, 1)) {
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
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "So Luong", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
