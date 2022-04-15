package com.wolvesres.truongnvn.nhanvien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import exceldoing.ExcelGo;
import junit.framework.Assert;

/**
 * Kiểm tra định dạng căn cước công dân thành công 
 * 
 * Kiểm tra định dạng căn cước  công dân thất bại do nhập dư số 
 * 
 * Kiểm tra định dạng căn cước công dân thất bại  do nhập sai mã giới tính theo năm sinh
 */
public class TestisValidCCCD {
	private DataGenerator data;
	private List<String> listCCCD;

	/**
	 * 
	 * */
	@BeforeClass(groups = { "cccdsuccess", "cccdfalseduso","cccdfalsemagioitinh","cccdfalsenamtheotheki"})
	public void beforClass() {
		data = new DataGenerator();
		listCCCD = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			String cccd = data.generateIdNational();
			listCCCD.add(cccd);
		}

	}

	/**
	 * DataProvider for testValid CCCD thành công
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataCCCDSuccess")
	public Object[][] data() {
		Object[][] data = new Object[listCCCD.size()][2];
		for (int i = 0; i < listCCCD.size(); i++) {
			data[i][0] = listCCCD.get(i);
			data[i][1] = true;
		}
		return data;
	}

	/**
	 * DataProvider for testValid CCCD nhập dư số
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataCCCDFalse")
	public Object[][] dataFailGT() {
		return new Object[][] { { "06425873074712312", false }, { "034002211636312312312323", false },
				{ "0830742495082342341341421324", false }, { "034002211636312312312323", false },
				{ "09623372741234718356", false } };
	}

	/**
	 * DataProvider for testValid CCCD giớ tình theo năm sinh
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataCCCDFalseGTNam")
	public Object[][] dataFalseGTNS() {
		return new Object[][] { { true, 1, 20, false }, { false, 5, 21, false }, { true, 4, 21, false },
				{ true, 2, 20, false } };
	}
	/**
	 * DataProvider for testValid CCCD năm theo tehes kỷ
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataCCCDFalseNamTK")
	public Object[][] dataFalseNSTK() {
		return new Object[][] { {2001, 20, false } ,{2012, 20, false } ,{1990, 21, false } ,{2212, 20, false }};
	}

	/**
	 * TestCse CCCD hợp lệ
	 * 
	 * @param cccd
	 * @param expected
	 */
	@Test(dataProvider = "dataCCCDSuccess", groups = "cccdsuccess", priority = 0)
	public void testValidCCCD(String cccd, Boolean expected) {
		Boolean actual = FormValidator.isValidIdNational(cccd);
		Assert.assertEquals(expected, actual);
	}

	/**
	 * TestCse CCCD nhập dư
	 * 
	 * @param sdt
	 * @param expected
	 */
	@Test(dataProvider = "dataCCCDFalse", groups = "cccdfalseduso", priority = 1)
	public void testValidCCCDDu(String cccd, Boolean expected) {
		Boolean actual = FormValidator.isValidTextMaxLength(cccd, 12);
		Assert.assertEquals(expected, actual);
	}

	/**
	 * TestCse CCCD Sai giới tính theo năm sinh
	 * 
	 * @param sdt
	 * @param expected
	 */
	@Test(dataProvider = "dataCCCDFalseGTNam", groups = "cccdfalsemagioitinh", priority = 2)
	public void testValidCCCDGioiTinhTheoNam(Boolean gioiTinh, int maGioTinh, int maTheKi, Boolean expected) {
		Boolean actual = FormValidator.isValidGenderCodeInCentury(gioiTinh,maGioTinh,maTheKi);
		Assert.assertEquals(expected, actual);
	}

	/**
	 * TestCse CCCD Sai năm sinh theo thế kỷ
	 * 
	 * @param sdt
	 * @param expected
	 */
//	@Test(dataProvider = "dataCCCDFalseNamTK", groups = "cccdfalsenamtheotheki", priority = 3)
//	public void testValidCCCDNamTheoTheKi( int nam, int maTheKi, Boolean expected) {
//		Boolean actual = FormValidator.isYearOfBirthInCentury(nam,maTheKi);
//		Assert.assertEquals(expected, actual);
//	}
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "maNam,maTheKi", dataFalseNSTK() );
//	}
}
