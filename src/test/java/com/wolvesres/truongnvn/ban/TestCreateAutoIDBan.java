package com.wolvesres.truongnvn.ban;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelNhanVien;

import junit.framework.Assert;
/**
 * Kiểm tra mã khu bàn tự sinh thành công
 * */
public class TestCreateAutoIDBan {
	private BanDAO bandao;
	private AutoDAO autodao;
	private String maBan;
	private List<ModelBan> listBan;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		bandao = new BanDAO();
		autodao = new AutoDAO();
		listBan = new ArrayList<ModelBan>();
		listBan.addAll(bandao.selectAll());
	}

	/**
	 * @return maNV tự sinh
	 */
	@BeforeMethod
	public void beforeTest() {
		this.maBan = autodao.AuToNhanVien();
	}

	/**
	 * Test tự sinh mã bàn
	 * 
	 * @param maBan
	 */
	@Test
	public void testAutoGenerateIdProduct() {
		Boolean exist = false;
		if (!FormValidator.isTextIsNotEmpty(this.maBan)) {
			exist = true;
		} else {
			for (ModelBan item : listBan) {
				if (item.getMaBan().equals(this.maBan)) {
					exist = true;
					break;
				}
			}
		}
		Assert.assertTrue(!exist);
	}
}
