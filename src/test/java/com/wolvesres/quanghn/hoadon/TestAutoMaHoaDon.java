package com.wolvesres.quanghn.hoadon;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.model.ModelHoaDon;

/**
 * Kiểm tra mã hóa đơn tự sinh thành công
 * @author huynh
 *
 */
public class TestAutoMaHoaDon {
	private HoaDonDAO hddao;
	private AutoDAO autodao;
	private String idProduct;
	private List<ModelHoaDon> listProduct;
	
	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		hddao = new HoaDonDAO();
		autodao = new AutoDAO();
		listProduct = new ArrayList<ModelHoaDon>();
		listProduct.addAll(hddao.selectAll());
	}
	
	@BeforeMethod
	public void beforeTest() {
		this.idProduct = autodao.AuToHoaDon();
	}

	@Test
	public void testAutoGenerateIdProduct() {
		Boolean exist = false;
		for (ModelHoaDon item : listProduct) {
			if (item.getMaHD().equals(this.idProduct)) {
				exist = true;
				break;
			}
		}
		Assert.assertTrue(!exist);
	}
	
}
