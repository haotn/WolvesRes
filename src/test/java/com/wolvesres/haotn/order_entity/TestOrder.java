package com.wolvesres.haotn.order_entity;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.OrderDAO;
import com.wolvesres.model.ModelOrder;

public class TestOrder {
	private OrderDAO orderDao;

	@BeforeClass
	public void beforeTestOrder() {
		orderDao = new OrderDAO();
	}

	@DataProvider(name = "dataThemOrder")
	public Object[][] dataThemOrder() {
		return new Object[][] { { new ModelOrder("SP06", "B04", 15000, 3), true },
				{ new ModelOrder("SP01", "B04", 15000, 3), true }, { new ModelOrder("SP01", "B010", 15000, 3), true },
				{ new ModelOrder("SP02", "B04", 15000, 4), true }, { new ModelOrder("SP06", "B010", 20000, 10), true },
				{ new ModelOrder("SP03", "B04", 22000, 1), true }, { new ModelOrder("SP07", "B010", 2000000, 2), true },
				{ new ModelOrder("SP04", "B04", 18000, 6), true }, { new ModelOrder("SP09", "B010", 50000, 4), true },
				{ new ModelOrder("SP05", "B04", 25000, 2), true } };
	}

	@Test(description = "Test them order", dataProvider = "dataThemOrder", priority = 1)
	public void testThemOrder(Object[] data) {
		ModelOrder entity = (ModelOrder) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		orderDao.insert(entity);
		ModelOrder fromDatabase = orderDao.selectOrderByMaBanVaMaSanPham(entity.getMaBan(), entity.getMaSP());
		if (entity.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu thêm mới mong muốn: " + entity);
		System.out.println("Dữ liệu thêm mới thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCapNhatOrder")
	public Object[][] dataCapNhatOrder() {
		return new Object[][] { { new ModelOrder("SP01", "B04", 15000, 8), true },
				{ new ModelOrder("SP01", "B010", 15000, 10), true }, { new ModelOrder("SP03", "B04", 22000, 4), true },
				{ new ModelOrder("SP09", "B010", 50000, 15), true },
				{ new ModelOrder("SP05", "B04", 22000, 8), true } };
	}

	@Test(description = "Test delete order", dataProvider = "dataCapNhatOrder", priority = 2)
	public void testCapNhatOrder(Object[] data) {
		ModelOrder entity = (ModelOrder) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		entity = orderDao.selectOrderByMaBanVaMaSanPham(entity.getMaBan(), entity.getMaSP());
		orderDao.updateByIdOder(entity);
		ModelOrder fromDatabase = orderDao.selectOrderByMaBanVaMaSanPham(entity.getMaBan(), entity.getMaSP());
		if (entity.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu mong muốn cập nhật: " + entity);
		System.out.println("Dữ liệu thực tế được cập nhật: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataDeleteOrder")
	public Object[][] dataDeleteOrder() {
		return new Object[][] { { "SP01", "B010", true }, { "SP05", "B04", true }, { "SP09", "B010", true } };
	}

	@Test(description = "Test delete order", dataProvider = "dataDeleteOrder", priority = 3)
	public void testDeleteOrder(String maSanPham, String maBan, Boolean expected) {
		Boolean actual = false;
		orderDao.deleteProduct(maSanPham, maBan);
		ModelOrder entity = orderDao.selectOrderByMaBanVaMaSanPham(maBan, maSanPham);
		if (entity == null) {
			actual = true;
			System.out.println("Xóa thành công!");
		} else {
			System.out.println("Xóa thất bại, dữ liệu còn tồn tại là: " + entity);
		}
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

}
