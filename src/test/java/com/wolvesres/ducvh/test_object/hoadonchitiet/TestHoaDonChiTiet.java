package com.wolvesres.ducvh.test_object.hoadonchitiet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
import com.wolvesres.model.ModelDanhMuc;
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
	
	public Object[][] dataThemHDCTv2(Object[][] datav1) {
		Object[][] data = new Object[datav1.length][5];
		for (int i = 0; i < datav1.length; i++) {
			ModelHoaDonChiTiet sp = (ModelHoaDonChiTiet) datav1[i][0];
			data[i][0] = sp.getMaHDCT();
			data[i][1] = sp.getMaHD();
			data[i][2] = sp.getMaSP();
			data[i][3] = sp.getDonGia();
			data[i][4] = sp.getSoLuong();
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

	@AfterClass
	public void excelGo() throws IOException {
		ExcelGo.writeExcelv3("D:\\demo.xlsx", 0, 13, 6, "maHDCT,maHD,maSP,soLuong,donGia", dataThemHDCTv2(dataThemHDCT()));
	}

}
