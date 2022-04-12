package com.wolvesres.haotn.sanpham;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.model.ModelSanPham;

/**
 * Test auto generate idProduct
 * 
 * @author Brian
 *
 */
public class TestAutoGenerateIdProduct {
	private SanPhamDAO spDao;
	private AutoDAO autoDao;
	private String idProduct;
	private List<ModelSanPham> listProduct;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		spDao = new SanPhamDAO();
		autoDao = new AutoDAO();
		listProduct = new ArrayList<ModelSanPham>();
		listProduct.addAll(spDao.selectAll());
	}

	/**
	 * Before test Generate value for this.idProduct
	 */
	@BeforeMethod
	public void beforeTest() {
		this.idProduct = autoDao.AuToSanPham();
	}

	/**
	 * TestCase testAutoGenerateIdProduct
	 */
	@Test
	public void testAutoGenerateIdProduct() {
		Boolean exist = false;
		for (ModelSanPham item : listProduct) {
			if (item.getMaSP().equals(this.idProduct)) {
				exist = true;
				break;
			}
		}
		Assert.assertTrue(!exist);
	}

}
