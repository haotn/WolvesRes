package com.wolvesres.haotn.test_object.nhanvien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.model.ModelNhanVien;

import exceldoing.ExcelGo;

public class TestNhanVien {
	private NhanVienDAO nvDao;
	private AutoDAO autoDao;

	// Declare DAO
	@BeforeClass
	public void beforeClassNhanVien() {
		nvDao = new NhanVienDAO();
		autoDao = new AutoDAO();
	}

	// Data Provider
	@DataProvider(name = "dataThemNhanVien")
	public Object[][] dataThemNhanVien() {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/nhanvien-fullname-true-data.xlsx", 0, 11, 0);
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelNhanVien emp = new ModelNhanVien();
			// emp.setMaNV(String.valueOf(list.get(i)[0]));
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

	@Test(description = "Test dao them nhan vien", dataProvider = "dataThemNhanVien", groups = "themNhanVien", priority = 1)
	public void testThemNhanVien(Object[] data) {
		ModelNhanVien nhanVien = (ModelNhanVien) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		String manv = autoDao.AuToNhanVien();
		nhanVien.setMaNV(manv);

		nvDao.insert(nhanVien);
		ModelNhanVien fromDatabase = nvDao.selectById(manv);
		if (nhanVien.print().equals(fromDatabase.print())) {
			actual = true;
		}
		System.out.println("Dữ liệu thêm mới mong muốn: " + nhanVien.print());
		System.out.println("Dữ liệu thêm mới thực tế: " + fromDatabase.print());
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCapNhatNhanVien")
	public Object[][] dataCapNhatNhanVien() {
		return new Object[][] {
				{ new ModelNhanVien("NV020", "Lê Huyền Ngọc Thanh", false, "02-05-1995", "031199532143", "0728445847",
						"thuylh@fpt.edu.vn", "anhNhanVien55", 3, true), true },
				{ new ModelNhanVien("NV021", "Trần Văn Kiên", true, "02-03-1992", "042199232143", "0898100847",
						"kientv@fpt.edu.vn", "anhNhanVien52", 4, true), true },
				{ new ModelNhanVien("NV022", "Phan Kiên", true, "07-06-1998", "031199832143", "0998727433",
						"quynhph@gmail.com", "anhNhanVien57", 3, true), true },
				{ new ModelNhanVien("NV023", "Nguyễn Lê Quỳnh An", false, "28-01-1999", "080099306020", "0395100410",
						"anhlh@gmail.com", "anhNhanVien60", 4, true), true },
				{ new ModelNhanVien("NV024", "Lê Thanh Mạnh Tuấn", true, "03-11-1994", "036199695572", "0729870705",
						"manhlt@fpt.edu.vn", "anhNhanVien68", 4, true), true } };
	}

	@Test(description = "Test dao cap nhat nhan vien", dataProvider = "dataCapNhatNhanVien", priority = 2)
	public void testCapNhatNhanVien(Object[] data) {
		ModelNhanVien nhanVien = (ModelNhanVien) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		nvDao.update(nhanVien, nhanVien.getMaNV());
		ModelNhanVien fromDatabase = nvDao.selectById(nhanVien.getMaNV());
		System.out.println("Dữ liệu cập nhật mong muốn: " + nhanVien.print());
		System.out.println("Dữ liệu cập nhật thực tế: " + fromDatabase.print());
		System.out.println("\n");
		if (nhanVien.print().equals(fromDatabase.print())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataDeleteNhanVien")
	public Object[][] dataDeleteNhanVien() {
		return new Object[][] { { "NV020", true }, { "NV021", true }, { "NV022", true } };
	}

	@Test(description = "Test delete nhan vien", dataProvider = "dataDeleteNhanVien", priority = 3)
	public void testDeleteNhanVien(String maNhanVien, Boolean expected) {
		Boolean actual = false;
		ModelNhanVien entity = nvDao.selectById(maNhanVien);
		nvDao.delete(maNhanVien);
		ModelNhanVien nhanVien = nvDao.selectById(maNhanVien);
		if (nhanVien == null) {
			actual = true;
			System.out.println("Xóa thành công!");
		} else {
			System.out.println("Xóa thất bại, dữ liệu còn tồn tại là: " + nhanVien.print());
		}
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataTimKiemNhanVien")
	public Object[][] timKiemNhanVien() {
		return new Object[][] { { "Trường", true }, { "Quang", true }, { "Thành", true } };
	}

	@Test(description = "Test tim kiem nhan vien theo ho ten", dataProvider = "dataTimKiemNhanVien", priority = 4)
	public void testTimKiemNhanVien(String keyword, Boolean expected) {
		Boolean actual = false;
		List<ModelNhanVien> list = nvDao.findNhanVien(keyword);
		if (list.size() > 0) {
			list.forEach((item) -> {
				System.out.println("Tim thay: " + item.print());
			});
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}
}
