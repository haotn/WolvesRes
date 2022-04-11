package com.wolvesres.haotn.nhanvien;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

import junit.framework.Assert;

/**
 * Test valid fullname
 * 
 * @author Brian
 *
 */
public class TestValidFullname {

	private DataGenerator data;

	private List<String> listFullname;

	/**
	 * Before class - Generate global variable value
	 * 
	 */
	@BeforeClass
	public void beforClass() {
		data = new DataGenerator();
		listFullname = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			String fullname = data.generateFullname();
			listFullname.add(fullname);
		}
	}

	/**
	 * DataProvider for testValidFullnameSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "data")
	public Object[][] data() {
		Object[][] data = new Object[listFullname.size()][2];
		for (int i = 0; i < listFullname.size(); i++) {
			data[i][0] = listFullname.get(i);
			data[i][1] = true;
		}
		return data;
	}

	/**
	 * TestCase testValidFullnameSuccess
	 * 
	 * @param fullname
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidFullnameSuccess(String fullname, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextContainsSpace(fullname)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}

}
