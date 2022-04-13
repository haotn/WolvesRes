package com.wolvesres.truongnvn.nhanvien;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.form.nhanvien.EditNhanVien;
import com.wolvesres.helper.Auth;
import com.wolvesres.main.Main;
import com.wolvesres.model.ModelNhanVien;

import exceldoing.ExcelGo;

/**
 * Them nhan vien do bo trong thong tin
 */
public class TestAddNV {
	private NhanVienDAO nvdao;
	private List<ModelNhanVien> listNhanVien;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		nvdao = new NhanVienDAO();

	}

	/**
	 * DataProvider for them nha nvien that bai
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataNV")
	public Object[][] data() {
		return new Object[][] { { "NV01", "", false, "2000-10-10", "", "", "", "", 0, false ,false} };
	}

	/**
	 * Test them nhan vien that bai voi du lieu trong
	 * 
	 * @param
	 */
	@Test(dataProvider = "dataNV", groups = "themnhanvienthatbai", priority = 0)
	public void testThemNhanVien(String maNV, String hoTen, boolean gioiTinh, String ngaySinh, String CMND, String soDT,
			String email, String pathHinhAnh, int ChucVu, boolean trangThai, boolean expected) {
		boolean actual = true;
		Auth.user = nvdao.selectById("BOSS02");
		Main main = new Main();
		EditNhanVien edit = new EditNhanVien(main, false);
		edit.setNhanVien(
				new ModelNhanVien(maNV, hoTen, gioiTinh, ngaySinh, CMND, soDT, email, pathHinhAnh, ChucVu, trangThai));
		edit.setForm();
		if (edit.valideForm()) {
			actual = true;
		} else {
			actual = false;
		}
		Assert.assertEquals(actual, expected);
	}
	
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "maNV,hoTen,gioiTinh,ngaySinh,CMND,soDT,email,pathHinhAnh,ChucVu,trangThai", data());
//	}
}
