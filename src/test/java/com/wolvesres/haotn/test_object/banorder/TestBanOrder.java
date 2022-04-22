package com.wolvesres.haotn.test_object.banorder;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.BanDAO;
import com.wolvesres.dao.BanOrderDAO;
import com.wolvesres.dao.VoucherDAO;
import com.wolvesres.model.ModelBanOrder;
//import com.wolvesres.model.ModelBan;
//import com.wolvesres.model.ModelBanOrder;
//import com.wolvesres.model.ModelVouCher;

public class TestBanOrder {
	private BanOrderDAO banOrderDao;
	private BanDAO banDao;
	private VoucherDAO vcDao;
//	private List<ModelBanOrder> listBanOrder;
//	private List<ModelBan> listBan;
//	private List<ModelVouCher> listVoucher;

	@BeforeClass
	public void beforClassTestBanOrder() {
		banOrderDao = new BanOrderDAO();
		banDao = new BanDAO();
		vcDao = new VoucherDAO();
//		listBan = banDao.selectAll();
//		listVoucher = vcDao.selectAll();
//		listBanOrder = banOrderDao.selectAll();
	}

	@DataProvider(name = "dataThemBanOrder")
	public Object[][] dataThemBanOrder() {
		return new Object[][] { { new ModelBanOrder("B02", null, "EEEEEERD", null), true },
				{ new ModelBanOrder("B03", null, "GIAMSOC", null), true },
				{ new ModelBanOrder("B04", null, "EEEEEER", null), true },
				{ new ModelBanOrder("B05", null, "WWWWW", null), true },
				{ new ModelBanOrder("B06", null, "VCKTCL", null), true },
				{ new ModelBanOrder("B010", null, "VCKTCL", null), true },
				{ new ModelBanOrder("B011", null, "EEEEEER", null), true } };
	}

	@Test(description = "Test them ban order", dataProvider = "dataThemBanOrder", priority = 1)
	public void testThemBanOrder(Object[] data) {
		ModelBanOrder entity = (ModelBanOrder) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		banOrderDao.insert(entity);
		ModelBanOrder fromDatabase = banOrderDao.selectById(entity.getMaBan());
		if (entity.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu thêm mới mong muốn: " + entity);
		System.out.println("Dữ liệu thêm mới thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataCapNhatBanOrder")
	public Object[][] dataCapNhatBanOrder() {
		return new Object[][] { { new ModelBanOrder("B02", "Chưa thanh toán", "EEEEEERD", "B07"), true },
				{ new ModelBanOrder("B03", "Xuất hóa đơn trước", "GIAMSOC", null), true },
				{ new ModelBanOrder("B04", "Khách mượn bật lửa", "EEEEEER", "B08"), true },
				{ new ModelBanOrder("B05", "Phụ thu bếp gas", "WWWWW", null), true },
				{ new ModelBanOrder("B06", "Phụ thu mang món bên ngoài vào quán", "VCKTCL", null), true } };
	}

	@Test(description = "Test cap nhat ban order", dataProvider = "dataCapNhatBanOrder", priority = 2)
	public void testCapNhatBanOrder(Object[] data) {
		ModelBanOrder entity = (ModelBanOrder) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		banOrderDao.update(entity, entity.getMaBan());
		ModelBanOrder fromDatabase = banOrderDao.selectById(entity.getMaBan());
		if (entity.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu cập nhật mong muốn: " + entity);
		System.out.println("Dữ liệu cập nhật thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

	@DataProvider(name = "dataDeleteBanOrder")
	public Object[][] dataDeleteBanOrder() {
		return new Object[][] { { "B02", true }, { "B03", true }, { "B05", true } };
	}

	@Test(description = "Test delete ban order", dataProvider = "dataDeleteBanOrder", priority = 3)
	public void testDeleteBanOrder(String maban, Boolean expected) {
		banOrderDao.delete(maban);
		Boolean actual = false;
		ModelBanOrder fromDatabase = banOrderDao.selectById(maban);
		if (fromDatabase == null) {
			actual = true;
			System.out.println("Xóa thành công!");
		} else {
			System.out.println("Xóa thất bại, dữ liệu còn tồn tại là: " + fromDatabase);
		}
		Assert.assertEquals(actual, expected);
	}
}
