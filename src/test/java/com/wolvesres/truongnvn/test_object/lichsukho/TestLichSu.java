package com.wolvesres.truongnvn.test_object.lichsukho;

import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.LichSuDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.model.ModelDonViTinh;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelLichSu;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelNhapKho;

import exceldoing.ExcelGo;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class TestLichSu {
	private ModelNhapKho nhapkho;
	private KhoDAO khodao;
	private LichSuDAO lsdao;
	@BeforeClass
	public void beforeClass() {
		nhapkho = new ModelNhapKho();
		khodao = new KhoDAO();
		lsdao = new LichSuDAO();
	}
//data base them ls
	@DataProvider(name = "dataThemLichSuKho")
	public Object[][] lichsu() {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = ExcelGo.readExcel("excel-file/lichsu.xlsx", 0, 3, 0);
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		Object[][] data = new Object[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			ModelLichSu emp = new ModelLichSu();
			emp.setNhapKho(Boolean.parseBoolean(String.valueOf(list.get(i)[0])));
			emp.setNguoiNhap(String.valueOf(list.get(i)[1]));
			emp.setTongTien(Float.parseFloat(String.valueOf(list.get(i)[2])));
			data[i][0] = emp;
			data[i][1] = true;
		}
		return data;
	}
//	Them Lich Su Kho
	@Test(description = "Test Them Lich Su Kho", dataProvider = "dataThemLichSuKho", groups = "themLichSuKho", priority = 1)
	public void testThemLichSuKho(Object[] data) {
		ModelLichSu lichsu = (ModelLichSu) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		lsdao.insert(lichsu);
		List<ModelLichSu> list = lsdao.selectAll();
		ModelLichSu fromDatabase = lsdao.selectById(list.get(list.size()-1).getId());
		lichsu.setId(fromDatabase.getId());
		lichsu.setThoiGian(fromDatabase.getThoiGian());
		if (lichsu.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu mong muốn: " + lichsu);
		System.out.println("Dữ liệu thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}
//	data tim kiem lich su

	@DataProvider(name = "dataTimKiemLichSu")
	public Object[][] timKiemLichSu() {
		return new Object[][] { { "14-11-2021", true }, { "20-11-2021", true }, { "15-12-2021", true } };
	}
// tim kiem lich su
	@Test(description = "Test tim kiem lich su theo ngay", dataProvider = "dataTimKiemLichSu", priority = 2)
	public void testTimKiemNhanVien(String keyword, Boolean expected) {
		Boolean actual = false;
		List<ModelLichSu> list = lsdao.TIMKIEM(keyword);
		if (list.size() > 0) {
			list.forEach((item) -> {
				System.out.println("Dữ liệu thực tế: " + item);
			});
			actual = true;
		}
		Assert.assertEquals(actual, expected);
	}
//	
}
