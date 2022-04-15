package com.wolvesres.haotn.nhanvien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelNhanVien;

import exceldoing.ExcelGo;

/**
 * Test valid fullname
 * 
 * @author Brian
 *
 */
public class TestValidFullname {
	private NhanVienDAO nvDao;

	/**
	 * Before class - Generate global variable value
	 * 
	 */
	@BeforeClass(groups = { "testValidFullnamePass" })
	public void beforClass() {
		nvDao = new NhanVienDAO();
	}

	/**
	 * DataProvider for testValidFullnameSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "data")
	public Object[][] data() {
		/**
		 * New data
		 */
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/nhanvien-fullname-true-data.xlsx", 0, 11, 0);
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
	@Test(dataProvider = "data", groups = "testValidFullnamePass")
	public void testValidFullnamePass(Object[] o) {
		ModelNhanVien emp = (ModelNhanVien) o[0];
		Boolean expected = (Boolean) o[1];
		Boolean actual = false;
		if (FormValidator.isTextContainsSpace(emp.getHoTen())) {
			actual = true;
			System.out.println("Thông tin hợp lệ!");
		} else {
			System.out.println("Họ tên phải có từ hai từ!");
		}
		Assert.assertEquals(actual, expected);
	}

//	@AfterClass
//	public void writeResult() {
//		Object[][] datac = new Object[data().length][10];
//		for (int i = 0; i < data().length; i++) {
//			System.err.println(data()[i][0]);
//			if (data()[i][0] instanceof ModelNhanVien) {
//				System.out.println("Converted");
//				ModelNhanVien nv = (ModelNhanVien) data()[i][0];
//				datac[i][0] = nv.getMaNV();
//				datac[i][1] = nv.getHoTen();
//				datac[i][2] = nv.getChucVu();
//				datac[i][3] = nv.getCMND();
//				datac[i][4] = nv.getEmail();
//				datac[i][5] = nv.getSoDT();
//				datac[i][6] = nv.getNgaySinh();
//				datac[i][7] = nv.getPathHinhAnh();
//				datac[i][8] = nv.isGioiTinh();
//				datac[i][9] = nv.isTrangThai();
//			}
//		}
//		try {
//			ExcelGo.writeExcelv2("excel-file/asm-temp.xlsx", 2, 169, 6,
//					"MaNhanVien,HoTen,ChucVu,CCCD/CMND,Email,SDT,NgaySinh,PathHinhAnh,Gender,TrangThai", datac);
//			System.err.println("Save Success");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
