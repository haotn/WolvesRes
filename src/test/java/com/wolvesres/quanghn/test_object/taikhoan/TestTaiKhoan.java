package com.wolvesres.quanghn.test_object.taikhoan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.helper.Auth;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelTaiKhoan;

import exceldoing.ExcelGo;

public class TestTaiKhoan {
	private TaiKhoanDAO tkDao;
	private NhanVienDAO nvDao;
	
	// Declare DAO
		@BeforeClass
		public void beforeClassNhanVien() {
			tkDao = new TaiKhoanDAO();
			nvDao = new NhanVienDAO();
			Auth.user = nvDao.selectById("BOSS02");
		}
		/**
		 * insert tài khoản
		 * @return
		 */
		// Data Provider
		@DataProvider(name = "dataThemTaiKhoan")
		public Object[][] dataThemTaiKhoan() {
			List<Object[]> list = new ArrayList<Object[]>();
			try {
				list = ExcelGo.readExcel("Excel_File/Test_TaiKhoan_True.xlsx", 0, 7, 0);
			} catch (

			IOException e) {
				e.printStackTrace();
			}
			Object[][] data = new Object[list.size()][2];
			for (int i = 0; i < list.size(); i++) {
				ModelTaiKhoan emp = new ModelTaiKhoan();
				emp.setTaiKhoan(String.valueOf(list.get(i)[0]));
				emp.setMatKhau(String.valueOf(list.get(i)[1]));
				emp.setTrangThai(Boolean.parseBoolean(String.valueOf(list.get(i)[2])));
				data[i][0] = emp;
				data[i][1] = true;
			}
			return data;
		}
		
		@Test(description = "Test dao them tai khoan", dataProvider = "dataThemTaiKhoan", groups = "themTaiKhoan", priority = 1)
		public void testThemTaiKhoan(Object[] data) {
			ModelTaiKhoan taikhoan = (ModelTaiKhoan) data[0];
			Boolean expected = (Boolean) data[1];
			Boolean actual = false;
			
			tkDao.insert(taikhoan);
			ModelTaiKhoan fromDatabase = tkDao.selectById(taikhoan.getTaiKhoan());
			if (taikhoan.toString().equals(fromDatabase.toString())) {
				actual = true;
			}
			System.out.println("Du lieu truyen vao: " + taikhoan);
			System.out.println("Sau khi them: " + fromDatabase);
			System.out.println("\n");
			Assert.assertEquals(actual, expected);
		}
		
		/**
		 * Update tài khoản
		 * @return
		 */
		@DataProvider(name = "dataCapNhatTaiKhoan")
		public Object[][] dataCapNhatTaiKhoan() {
			return new Object[][] {
					{ new ModelTaiKhoan("NV01", "111111", true), true },
					{ new ModelTaiKhoan("NV010", "222222", true), true },
					{ new ModelTaiKhoan("NV013", "333333", true), true },
					{ new ModelTaiKhoan("NV02", "444444", true), true },
					{ new ModelTaiKhoan("NV04", "555555", true), true },
			};
		}

		@Test(description = "Test dao cap nhat tai khoan", dataProvider = "dataCapNhatTaiKhoan", priority = 2)
		public void testCapNhatTaiKhoan(Object[] data) {
			ModelTaiKhoan taikhoan = (ModelTaiKhoan) data[0];
			Boolean expected = (Boolean) data[1];
			Boolean actual = false;
			tkDao.update(taikhoan, taikhoan.getTaiKhoan());
			ModelTaiKhoan fromDatabase = tkDao.selectById(taikhoan.getTaiKhoan());
			System.out.println("Du lieu mong muon chinh sua: " + taikhoan.toString());
			System.out.println("Du lieu sau khi cap nhat: " + fromDatabase.toString());
			System.out.println("\n");
			if (taikhoan.toString().equals(fromDatabase.toString())) {
				actual = true;
			}
			Assert.assertEquals(actual, expected);
		}
		
		/**
		 * Delete tài khoản
		 * @return
		 */
		@DataProvider(name = "dataDeleteTaiKhoan")
		public Object[][] dataDeleteTaiKhoan() {
			return new Object[][] { { "NV05", true }, { "NV07", true }, { "NV09", true } };
		}

		@Test(description = "Test delete tai khoan", dataProvider = "dataDeleteTaiKhoan", priority = 3)
		public void testDeleteTaiKhoan(String maNhanVien, Boolean expected) {
			Boolean actual = false;
			ModelTaiKhoan entity = tkDao.selectById(maNhanVien);
			tkDao.delete(maNhanVien);
			ModelTaiKhoan taikhoan = tkDao.selectById(maNhanVien);
			System.out.println("Truoc khi xoa: " + entity);
			System.out.println("Sau khi xoa: " + taikhoan);
			if (taikhoan == null) {
				actual = true;
			}
			Assert.assertEquals(actual, expected);
		}
		
		@DataProvider(name = "dataTimKiemTaiKhoan")
		public Object[][] dataTimKiemTaiKhoan() {
			return new Object[][] { { "NV01", true }, { "NV010", true }, { "NV02", true }};
		}

		@Test(description = "Test tim kiem tai khoan theo ma tai khoan", dataProvider = "dataTimKiemTaiKhoan", priority = 4)
		public void testTimKiemTaiKhoan(String keyword, Boolean expected) {
			Boolean actual = false;
			
			List<ModelTaiKhoan> list = tkDao.timkiem(keyword);
			if (list.size() > 0) {
				list.forEach((item) -> {
					System.out.println("Tim thay: " + item);
				});
				actual = true;
			}
			Assert.assertEquals(actual, expected);
		}

}
