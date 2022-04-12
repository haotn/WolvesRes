package com.wolvesres.truongnvn.nhanvien;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelNhanVien;

import junit.framework.Assert;
/**
 * Kiểm tra mã nhân viên tự sinh thành công
 * */
public class TetAutoCreateIDNhanVien {
	private NhanVienDAO nvdao;
	private AutoDAO autodao;
	private String maNV;
	private List<ModelNhanVien> listNhanVien;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		nvdao = new NhanVienDAO();
		autodao = new AutoDAO();
		listNhanVien = new ArrayList<ModelNhanVien>();
		listNhanVien.addAll(nvdao.selectAll());
	}

	/**
	 * @return maNV tự sinh
	 */
	@BeforeMethod
	public void beforeTest() {
		this.maNV = autodao.AuToNhanVien();
	}

	/**
	 * Test tự sinh mã nhân viên
	 * @param maNV
	 * */
	@Test
	public void testAutoGenerateIdNhanVien() {
		Boolean exist = false;
		if (!FormValidator.isTextIsNotEmpty(this.maNV)) {
			exist = true;
		} else {
			for (ModelNhanVien item : listNhanVien) {
				if (item.getMaNV().equals(this.maNV)) {
					exist = true;
					break;
				}
			}
		}
		Assert.assertTrue(!exist);
	}
}
