package com.wolvesres.haotn.kho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelKho;

import exceldoing.ExcelGo;

/**
 * Test date product import
 * 
 * @author Brian
 *
 */
public class TestDateImport {
	private DataGenerator dataGenerator;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dataGenerator = new DataGenerator();
	}

	@DataProvider(name = "dateProductImport")
	public Object[][] dataForDateProductImport() {

		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/sanpham-fail-date.xlsx", 0, 11, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelKho item = new ModelKho();
			item.setId(Integer.parseInt(String.valueOf(list.get(i)[0])));
			item.setIdls(Integer.parseInt(String.valueOf(list.get(i)[1])));
			item.setMaSP(String.valueOf(list.get(i)[2]));
			item.setSoLuong(Integer.parseInt(String.valueOf(list.get(i)[3])));
			item.setHanSuDung(String.valueOf(list.get(i)[4]));
			item.setTrangThai(Boolean.parseBoolean(String.valueOf(list.get(i)[5])));
			data[i][0] = item;
			data[i][1] = false;
		}
		return data;
	}

	@Test(dataProvider = "dateProductImport", groups = "testDateImport")
	public void testDateProductImportFail(Object[] o) {
		Boolean expected = Boolean.parseBoolean(String.valueOf(o[1]));
		ModelKho entity = (ModelKho) o[0];
		Boolean actual = true;
		if (!FormValidator.validFormKho(String.valueOf(entity.getSoLuong()), "50000",
				XDate.toDate(entity.getHanSuDung(), "dd-MM-yyyy"))) {
			actual = false;
		} else {
			System.out.println("Dữ liệu hợp lệ");
		}
		Assert.assertEquals(actual, expected);
	}
}
