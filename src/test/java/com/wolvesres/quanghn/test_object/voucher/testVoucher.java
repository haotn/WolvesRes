package com.wolvesres.quanghn.test_object.voucher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.VoucherDAO;
import com.wolvesres.model.ModelTaiKhoan;
import com.wolvesres.model.ModelVouCher;

import exceldoing.ExcelGo;

public class testVoucher {
	private VoucherDAO vDao;
	
	// Declare DAO
	@BeforeClass
	public void beforeClassNhanVien() {
		vDao = new VoucherDAO();
	}
	
	/**
	 * insert voucher
	 * @return
	 */
	// Data Provider
	@DataProvider(name = "dataThemVoucher")
	public Object[][] dataThemVoucher() {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("Excel_File/Test_Voucher_true.xlsx", 0, 4, 0);
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelVouCher emp = new ModelVouCher();
			emp.setMaVoucher(String.valueOf(list.get(i)[0]));
			emp.setNgayBatDau(String.valueOf(list.get(i)[1]));
			emp.setNgayKetThuc(String.valueOf(list.get(i)[2]));
			emp.setGiamGia(Float.parseFloat(String.valueOf(list.get(i)[3])));
			emp.setSoLuong(Integer.parseInt(String.valueOf(list.get(i)[4])));
			emp.setPathQR(String.valueOf(list.get(i)[5]));
			emp.setTrangThai(Boolean.parseBoolean(String.valueOf(list.get(i)[6])));
			data[i][0] = emp;
			data[i][1] = true;
		}
		return data;
	}
	
	@Test(description = "Test dao them Voucher", dataProvider = "dataThemVoucher", groups = "themVoucher", priority = 1)
	public void testThemVoucher(Object[] data) {
		ModelVouCher voucher = (ModelVouCher) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		
		vDao.insert(voucher);
		ModelVouCher fromDatabase = vDao.selectById(voucher.getMaVoucher());
		if (voucher.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Du lieu truyen vao: " + voucher);
		System.out.println("Sau khi them: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}
	
	/**
	 * Update voucher
	 * @return
	 */
	@DataProvider(name = "dataCapNhatVoucher")
	public Object[][] dataCapNhatVoucher() {
		return new Object[][] {
				{ new ModelVouCher("VC01", "23-04-2022", "23-05-2022", 30, 50, "EEEE.png", true), true },
				{ new ModelVouCher("VC02", "23-04-2022", "23-06-2022", 20, 10, "TTTT.png", true), true },
				{ new ModelVouCher("VC03", "23-04-2022", "23-07-2022", 10, 20, "UUUU.png", true), true },
				{ new ModelVouCher("VC04", "23-04-2022", "23-08-2022", 40, 30, "GGGG.png", true), true },
				{ new ModelVouCher("VC05", "23-04-2022", "23-09-2022", 50, 40, "KKKK.png", true), true },
		};
	}

	@Test(description = "Test dao cap nhat voucher", dataProvider = "dataCapNhatVoucher", priority = 2)
	public void testCapNhatVoucher(Object[] data) {
		ModelVouCher voucher = (ModelVouCher) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		vDao.update(voucher, voucher.getMaVoucher());
		ModelVouCher fromDatabase = vDao.selectById(voucher.getMaVoucher());
		System.out.println("Du lieu mong muon chinh sua: " + voucher.toString());
		System.out.println("Du lieu sau khi cap nhat: " + fromDatabase.toString());
		System.out.println("\n");
		if (voucher.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}
	
	/**
	 * Delete voucher
	 * @return
	 */
	@DataProvider(name = "dataDeleteVoucher")
	public Object[][] dataDeleteVoucher() {
		return new Object[][] { { "VC095", true }, { "VC096", true }, { "VC097", true } };
	}

	@Test(description = "Test delete voucher", dataProvider = "dataDeleteVoucher", priority = 3)
	public void testDeleteVoucher(String maVocher, Boolean expected) {
		Boolean actual = false;
		ModelVouCher entity = vDao.selectById(maVocher);
		vDao.delete(maVocher);
		ModelVouCher voucher = vDao.selectById(maVocher);
		System.out.println("Truoc khi xoa: " + entity);
		System.out.println("Sau khi xoa: " + voucher);
		if (voucher == null) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}
	
	/**
	 * Tìm kiếm voucher
	 * @return
	 */
	@DataProvider(name = "dataTimKiemVoucher")
	public Object[][] dataTimKiemVoucher() {
		return new Object[][] { { "VC01", true }, { "14-04-2021", true }, { "15-01-2022", true }};
	}

	@Test(description = "Test tim kiem voucher theo ma voucher, ngay bat dau, ngay ket thuc", dataProvider = "dataTimKiemVoucher", priority = 4)
	public void testTimKiemTaiKhoan(String keyword, Boolean expected) {
		Boolean actual = false;
		List<ModelVouCher> list = vDao.findVoucher(keyword);
		if (list.size() > 0) {
			list.forEach((item) -> {
				System.out.println("Tim thay: " + item);
			});
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}
}
