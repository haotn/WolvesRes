package com.wolvesres.hott.sanpham;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelDanhMuc;

import exceldoing.ExcelGo;
import junit.framework.Assert;

//Kiểm tra mã danh mục sản phẩm tự sinh thành công

public class TestAutoProductCategoryCode {
	private DanhMucDAO dmDao;
	private AutoDAO autoDao;
	private String categoryProduct;
	private List<ModelDanhMuc> listCategory;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dmDao = new DanhMucDAO();
		autoDao = new AutoDAO();
		listCategory = new ArrayList<ModelDanhMuc>();
		listCategory.addAll(dmDao.selectAll());
	}

	@BeforeMethod
	public void beforeTest() {
		this.categoryProduct = autoDao.AuToDanhMuc();
	}

	@Test
	public void testAutoGenerateIdProduct() {
		Boolean exist = false;
		if (!FormValidator.isTextIsNotEmpty(this.categoryProduct)) {
			exist = true;
		} else {
			for (ModelDanhMuc item : listCategory) {
				if (item.getMaDanhMuc().equals(this.categoryProduct)) {
					exist = true;
					break;
				}
			}
		}
		Assert.assertTrue(!exist);
	}
	
}
