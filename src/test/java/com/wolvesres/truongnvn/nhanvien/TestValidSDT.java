package com.wolvesres.truongnvn.nhanvien;
import java.io.IOException;
/**
 * Kiểm tra định dạng số điện thoại thành công
 * 
 * Kiểm tra định dạng số điện thoại thất bại do nhập đầu số không hợp lệ
 * */
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

public class TestValidSDT {
	private DataGenerator data;

	private List<String> listSDT;

	/**
	 * 
	 * */
	@BeforeClass(groups = { "sdtsuccess" })
	public void beforClass() {
		data = new DataGenerator();
		listSDT = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			String sdt = data.generateSDT();
			listSDT.add(sdt);
		}
	}

	/**
	 * DataProvider for testValidSDTSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataSDTSuccess")
	public Object[][] data() {
		Object[][] data = new Object[listSDT.size()][2];
		for (int i = 0; i < listSDT.size(); i++) {
			data[i][0] = listSDT.get(i);
			data[i][1] = true;
		}
		return data;
	}

	/**
	 * DataProvider for testValidSDTFail do sai đầu số
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataSDTFalse")
	public Object[][] datafalse() {
		return new Object[][] { { "0155425546", false }, { "01666156144", false }, { "0266254712", false },
				{ "1702154687", false }, { "2315264812", false }, { "0456154812", false }, { "0515234815", false },
				{ "0615986345", false }, { "006514782", false } };
	}

	/**
	 * TestCase testValidSDT
	 * 
	 * @param sdt
	 * @param expected
	 */
	@Test(dataProvider = "dataSDTSuccess", groups = "sdtsuccess", priority = 0)
	public void testValidSDTSuccess(String sdt, Boolean expected) {
		Boolean actual = FormValidator.isValidPhoneNumber(sdt);
		Assert.assertEquals(expected, actual);
	}

	/**
	 * TestCse Số điện thoại sai dầu số
	 * 
	 * @param sdt
	 * @param expected
	 * */
	@Test(dataProvider = "dataSDTFalse", groups = "sdtfalse", priority = 0)
	public void testValidSDTFails(String sdt, Boolean expected) {
		Boolean actual = FormValidator.isValidPhoneNumber(sdt);
		Assert.assertEquals(expected, actual);
	}
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "SDT", datafalse());
//	}
}
