package com.wolvesres.haotn.nhanvien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelNhanVien;

import exceldoing.ExcelGo;
import exceldoing.WriteResult;

/**
 * Test valid idNational fail
 * 
 * @author Brian
 *
 */
public class TestValidIdNational {
	private List<String> listIdNational;
	private DataGenerator data;
	private NhanVienDAO nvDao;

	/**
	 * Before class Generate global variable value
	 */
	@BeforeClass(groups = "testIdNationalFail")
	public void beforeClass() {
		nvDao = new NhanVienDAO();
//		data = new DataGenerator();
//		listIdNational = new ArrayList<String>();
//		// Generate 100 idNational
//		for (int i = 0; i < 100; i++) {
//			Boolean isMale = false;
//			int gender = data.randomMinMax(0, 1);
//			switch (gender) {
//			case 0:
//				isMale = false;
//				break;
//			case 1:
//				isMale = true;
//				break;
//			}
//			String idNational = data.generateIdNationalNotValid(data.generateDate(1990, 2003), isMale);
//			listIdNational.add(idNational);
//		}
	}

	/**
	 * Data for testIdNationalFail4
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "data")
	public Object[][] data() {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/nhanVien-fail-idNational-data.xlsx", 0, 4, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelNhanVien emp = new ModelNhanVien();
			emp.setMaNV(String.valueOf(list.get(i)[0]));
			emp.setHoTen(String.valueOf(list.get(i)[1]));
			emp.setChucVu(Integer.parseInt(String.valueOf(list.get(i)[2])));
			emp.setCMND(String.valueOf(list.get(i)[3]));
			emp.setEmail(String.valueOf(list.get(i)[4]));
			emp.setSoDT(String.valueOf(list.get(i)[5]));
			emp.setNgaySinh(String.valueOf(list.get(i)[6]));
			emp.setPathHinhAnh(String.valueOf(list.get(i)[7]));
			emp.setGioiTinh(Boolean.parseBoolean(String.valueOf(list.get(i)[8])));
			emp.setTrangThai(Boolean.parseBoolean(String.valueOf(list.get(i)[9])));
			data[i][0] = emp;
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
	@Test(dataProvider = "data", groups = "testIdNationalFail")
	public void testIdNationalFail(Object[] o) {
		Boolean actual = true;
		Boolean expected = Boolean.parseBoolean(String.valueOf(o[1]));
		ModelNhanVien emp = (ModelNhanVien) o[0];
		if (!FormValidator.isValidFormNhanVien(true, emp, nvDao.selectAll())) {
			actual = false;
		} else {
			System.out.println("Thông tin hợp lệ!");
		}
		Assert.assertEquals(actual, expected);
	}

	/**
	 * Write result to file
	 */
//	@AfterClass
//	public void writeResult() {
//		WriteResult.writeResultNhanVien(data(), 165);
//	}
}
