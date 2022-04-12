package com.wolvesres.truongnvn.nhanvien;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.form.nhanvien.EditNhanVien;
import com.wolvesres.helper.Auth;
import com.wolvesres.main.Main;
import com.wolvesres.model.ModelNhanVien;

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
	 * Test them nhan vien that bai voi du lieu trong
	 * 
	 * @param 
	 */
	@Test(groups = "themnhanvienthatbai", priority = 0)
	public void testThemNhanVien() {
		Boolean actual;
		Auth.user = nvdao.selectById("BOSS02");
		Main main = new Main();
		EditNhanVien edit = new EditNhanVien(main, false);
		edit.setNhanVien(new ModelNhanVien("NV01", "", false, "2000-10-10", "", "", "", "", 0, false));
		edit.setForm();
		if (edit.valideForm()) {
			actual = false;
		} else {
			actual= true;
		}
		Assert.assertTrue(actual);
	}
}
