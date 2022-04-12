package com.wolvesres.quanghn.nhanvien;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

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
	 * DataProvider for testValidCCCDFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data2() {
		return new Object[][] { { "091202014730", false }};
	}

	/**
	 * TestCase testValidCCCDFail
	 * 
	 * @param cccd
	 * @param expected
	 */
	@Test(dataProvider = "data2", groups = "CCCDFailTinh")
	public void testValidCCCDMaTinhFail(String cccd, Boolean expected) {
		
		Boolean actual = true;
		if (!datage.listTinh().equals(cccd)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	
	
}
