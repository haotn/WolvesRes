package com.wolvesres.hott.ban;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelBan;

import junit.framework.Assert;

//Kiểm tra mã bàn tự sinh thành công

public class TestAutoTableCode {
	private BanDAO banDao;
	private AutoDAO autoDao;
	private String table;
	private List<ModelBan> listTable;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		banDao = new BanDAO();
		autoDao = new AutoDAO();
		listTable = new ArrayList<ModelBan>();
		listTable.addAll(banDao.selectAll());
	}

	@BeforeMethod
	public void beforeTest() {
		this.table = autoDao.AuToBan();
	}

	@Test
	public void testAutoGenerateIdProduct() {
		Boolean exist = false;
		if (!FormValidator.isTextIsNotEmpty(this.table)) {
			exist = true;
		} else {
			for (ModelBan item : listTable) {
				if (item.getMaBan().equals(this.table)) {
					exist = true;
					break;
				}
			}
		}
		Assert.assertTrue(!exist);
	}
}
