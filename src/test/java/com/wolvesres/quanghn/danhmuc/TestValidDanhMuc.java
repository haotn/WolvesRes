package com.wolvesres.quanghn.danhmuc;

import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.model.ModelDanhMuc;
/**
 * Kiểm tra danh mục sản phẩm thất bại do trùng tên danh mục
 * @author huynh
 *
 */
public class TestValidDanhMuc {
	private List<ModelDanhMuc> listDM;
	/**
	 * Before class
	 */
	@BeforeClass
	public void beforClass() {
		listDM = Arrays.asList(new ModelDanhMuc[] {
			new ModelDanhMuc("DM01", "Salad", true),
			new ModelDanhMuc("DM02", "Bia", true),
			new ModelDanhMuc("DM03", "Rượu", true),
		});
		
	}
	
	/**
	 * DataProvider for testValidDanhMucFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"Salad", false}, {"Bia", false}, {"Rượu", false} };
	}

	/**
	 * TestCase testValidDanhMucFail
	 * 
	 * @param TenDanhMuc
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidFullNameFail(String TenDanhMuc, Boolean expected) {
		Boolean actual = true;
		for (int i = 0; i < listDM.size(); i++) {
			if (listDM.get(i).getTenDanhMuc().equalsIgnoreCase(TenDanhMuc)) {
				actual = false;
				System.out.print("dfsddssdsfsfsdf");
				break;
			}
		}
		Assert.assertEquals(expected, actual);
	}
}
