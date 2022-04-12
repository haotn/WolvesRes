package com.wolvesres.haotn.nhanvien;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

/**
 * Test valid idNational fail
 * 
 * @author Brian
 *
 */
public class TestValidIdNational {
	private List<String> listIdNational;
	private DataGenerator data;

	/**
	 * Before class Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		data = new DataGenerator();
		listIdNational = new ArrayList<String>();
		// Generate 100 idNational
		for (int i = 0; i < 100; i++) {
			Boolean isMale = false;
			int gender = data.randomMinMax(0, 1);
			switch (gender) {
			case 0:
				isMale = false;
				break;
			case 1:
				isMale = true;
				break;
			}
			String idNational = data.generateIdNationalNotValid(data.generateDate(1990, 2003), isMale);
			listIdNational.add(idNational);
		}
	}

	/**
	 * Data for testIdNationalFail4
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "data")
	public Object[][] data() {
		Object[][] data = new Object[listIdNational.size()][2];
		for (int i = 0; i < listIdNational.size(); i++) {
			data[i][0] = listIdNational.get(i);
			data[i][1] = false;
		}
		return data;
	}

	/**
	 * Test Case testIdNationalFail
	 * 
	 * @param idNational
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testIdNationalFail(String idNational, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isValidIdNational(idNational)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
}
