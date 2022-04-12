package com.wolvesres.haotn.kho;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;


/**
 * Test date product import
 * 
 * @author Brian
 *
 */
public class TestDateProduct {
	private DataGenerator dataGenerator;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dataGenerator = new DataGenerator();
	}

//	public static void main(String[] args) {
//		NhanVienDAO dao = new NhanVienDAO();
//		Auth.user = dao.selectById("BOSS01");
//		Main main = new Main();
//		EditNhanVien editForm = new EditNhanVien(main, false);
//		editForm.setNhanVien(new ModelNhanVien("NV001", "", false, "2000-11-11", "", "", "", "", 0, false));
//		editForm.setForm();
//		if (editForm.valideForm()) {
//			System.out.println("Is valid");
//		} else {
//			System.out.println("In valid");
//		}
//	}
	@DataProvider(name = "dateProductImport")
	public Object[][] dataForDateProductImport() {
		Object[][] data = new Object[100][2];
		for (int i = 0; i < 100; i++) {
			data[i][0] = dataGenerator.generateDate(2022, 2022);
			data[i][1] = false;
		}
		return data;
	}

	@Test(dataProvider = "dateProductImport")
	public void testDateProductImportFail(Date dateProduct, Boolean expected) {
		Boolean actual = true;
		Date date = XDate.addDays(new Date(), 365);
		if (FormValidator.isDateBefore(dateProduct, date)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}
}
