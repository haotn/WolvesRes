package com.wolvesres.ducvh.hoadonchitiet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.HoaDonChiTietDAO;
import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.ducvh.module.DTool;
import com.wolvesres.form.hoadon.HoaDonChiTiet;
import com.wolvesres.model.ModelHoaDon;
import com.wolvesres.model.ModelHoaDonChiTiet;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelSanPham;

import exceldoing.ExcelGo;

public class TestHoaDonChiTiet {
	private ModelKho khoTemp;
	private ModelHoaDonChiTiet hdctTemp;
	private HoaDonChiTietDAO hdctDAO;
	private HoaDonDAO hdDAO;
	private SanPhamDAO spDAO;
	private KhoDAO khoDAO;
	private List<ModelHoaDon> hdList = new ArrayList<>();
	private List<ModelSanPham> spList = new ArrayList<>();
	private int minwhilereal = 0;;
	private int minwhilewant = 3;

	// Declare DAO
	@BeforeClass
	public void beforeClass() {
		khoTemp = new ModelKho();
		hdctTemp = new ModelHoaDonChiTiet();
		hdctDAO = new HoaDonChiTietDAO();
		hdDAO = new HoaDonDAO();
		spDAO = new SanPhamDAO();
		khoDAO = new KhoDAO();
		hdList = hdDAO.selectAll();
		spList = spDAO.selectAll();
		minwhilereal = DTool.minInThem(hdList.size(), spList.size());
	}

	// Data Provider
	@DataProvider(name = "dataThemHDCT")
	public Object[][] dataThemHDCT() {
//		ModelHoaDonChiTiet hdct = new ModelHoaDonChiTiet(int maHDCT, String maHD, String maSP, int soLuong, float donGia);
		List<Object[]> listObjects = new ArrayList<Object[]>();
		int checkminwhile = 0;
		int i = 0;
		while (checkminwhile < minwhilewant) {
			if (i == minwhilereal) {
				break;
			}
			List<ModelKho> listTemp = khoDAO.selectBySQL("select * from kho where masp = ?", spList.get(i).getMaSP());
			int checksl = 1;
			if(listTemp.size() > 0) {
				checkminwhile++;
			}else {
				i++;
				continue;
			}
			listObjects.add(new Object[] {new ModelHoaDonChiTiet(0, hdList.get(i).getMaHD(), spList.get(i).getMaSP(), DTool.OneOrZero(checksl), spList.get(i).getGiaBan()), true});
			i++;
		}
		Object[][] data = new Object[listObjects.size()][2];
		for (int j = 0; j < listObjects.size(); j++) {
			data[j][0] = listObjects.get(j)[0];
			data[j][1] = listObjects.get(j)[1];
			
		}
		return data;
	}

	@Test(description = "ThemHDCT", dataProvider = "dataThemHDCT", groups = "dataThemHDCT", priority = 1)
	public void testThemHDCT(Object[] data) {
		hdctTemp = (ModelHoaDonChiTiet) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;

		hdctDAO.insert(hdctTemp);
		List<ModelHoaDonChiTiet> insertTempList = hdctDAO.selectBySQL("select top 1 * from HOADONCHITIET order by MaHoaDonCT desc");
		ModelHoaDonChiTiet insertTemp = insertTempList.get(0);
		
		if (hdctTemp.toString().equalsIgnoreCase(insertTemp.toString())) {
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}

//	@DataProvider(name = "dataCapNhatNhanVien")
//	public Object[][] dataCapNhatNhanVien() {
//		return new Object[][] {
//				{ new ModelNhanVien("NV020", "Lê Huyền Ngọc Thanh", false, "02-05-1995", "031199532143", "0728445847",
//						"thuylh@fpt.edu.vn", "anhNhanVien55", 3, true), true },
//				{ new ModelNhanVien("NV021", "Trần Văn Kiên", true, "02-03-1992", "042199232143", "0898100847",
//						"kientv@fpt.edu.vn", "anhNhanVien52", 4, true), true },
//				{ new ModelNhanVien("NV022", "Phan Kiên", true, "07-06-1998", "031199832143", "0998727433",
//						"quynhph@gmail.com", "anhNhanVien57", 3, true), true },
//				{ new ModelNhanVien("NV023", "Nguyễn Lê Quỳnh An", false, "28-01-1999", "080099306020", "0395100410",
//						"anhlh@gmail.com", "anhNhanVien60", 4, true), true },
//				{ new ModelNhanVien("NV024", "Lê Thanh Mạnh Tuấn", true, "03-11-1994", "036199695572", "0729870705",
//						"manhlt@fpt.edu.vn", "anhNhanVien68", 4, true), true } };
//	}
//
//	@Test(description = "Test dao cap nhat nhan vien", dataProvider = "dataCapNhatNhanVien", priority = 2)
//	public void testCapNhatNhanVien(Object[] data) {
//		ModelNhanVien nhanVien = (ModelNhanVien) data[0];
//		Boolean expected = (Boolean) data[1];
//		Boolean actual = false;
//		nvDao.update(nhanVien, nhanVien.getMaNV());
//		ModelNhanVien fromDatabase = nvDao.selectById(nhanVien.getMaNV());
//		System.out.println("Du lieu mong muon chinh sua: " + nhanVien.toString());
//		System.out.println("Du lieu sau khi cap nhat: " + fromDatabase.toString());
//		System.out.println("\n");
//		if (nhanVien.toString().equals(fromDatabase.toString())) {
//			actual = true;
//		}
//		Assert.assertEquals(actual, expected);
//	}
//
//	@DataProvider(name = "dataDeleteNhanVien")
//	public Object[][] dataDeleteNhanVien() {
//		return new Object[][] { { "NV020", true }, { "NV021", true }, { "NV022", true } };
//	}
//
//	@Test(description = "Test delete nhan vien", dataProvider = "dataDeleteNhanVien", priority = 3)
//	public void testDeleteNhanVien(String maNhanVien, Boolean expected) {
//		Boolean actual = false;
//		ModelNhanVien entity = nvDao.selectById(maNhanVien);
//		nvDao.delete(maNhanVien);
//		ModelNhanVien nhanVien = nvDao.selectById(maNhanVien);
//		System.out.println("Truoc khi xoa: " + entity);
//		System.out.println("Sau khi xoa: " + nhanVien);
//		if (nhanVien == null) {
//			actual = true;
//		}
//		Assert.assertEquals(actual, expected);
//	}
//
//	@DataProvider(name = "dataTimKiemNhanVien")
//	public Object[][] timKiemNhanVien() {
//		return new Object[][] { { "Trường", true }, { "Quang", true }, { "Thành", true } };
//	}
//
//	@Test(description = "Test tim kiem nhan vien theo ho ten", dataProvider = "dataTimKiemNhanVien", priority = 4)
//	public void testTimKiemNhanVien(String keyword, Boolean expected) {
//		Boolean actual = false;
//		List<ModelNhanVien> list = nvDao.findNhanVien(keyword);
//		if (list.size() > 0) {
//			list.forEach((item) -> {
//				System.out.println("Tim thay: " + item);
//			});
//			actual = true;
//		}
//		Assert.assertEquals(actual, expected);
//	}
}
