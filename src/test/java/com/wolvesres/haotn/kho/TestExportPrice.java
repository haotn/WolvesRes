package com.wolvesres.haotn.kho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
/**
 * Test product export price
 * @author Brian
 *
 */

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.model.ModelNhanVien;

import exceldoing.ExcelGo;

public class TestExportPrice {
	private DataGenerator dataGenerator;
	private List<ModelNhanVien> listNhanVien;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dataGenerator = new DataGenerator();
		listNhanVien = new ArrayList<ModelNhanVien>();
		try {
			List<Object[]> readData = ExcelGo.readExcel("excel-file/nhanvien-data.xlsx", 0, 99, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * DataProvider for testProductExportPrice
	 * 
	 * @return
	 */
	@DataProvider(name = "dataForExportPrice")
	public Object[][] dataForExportPrice() {
		Object[][] data = new Object[100][2];
		for (int i = 0; i < 100; i++) {
			data[i][0] = dataGenerator.randomMinMax(-100000000.1, -0.000000000001);
			data[i][1] = false;
		}
		return data;
	}

	/**
	 * TestCase
	 * 
	 * @param price    testProductExportPrice
	 * @param expected
	 */
	@Test(dataProvider = "dataForExportPrice")
	public void testProductExportPrice(double price, Boolean expected) {
		Boolean actual = true;
		if (price < 0) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}

}
