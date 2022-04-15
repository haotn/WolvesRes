package com.wolvesres.quanghn.danhmuc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.model.ModelDanhMuc;

import exceldoing.ExcelGo;
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
			new ModelDanhMuc("DM04", "Nước ngọt", true),
			new ModelDanhMuc("DM05", "Nước Suối", true),
		});
		
	}
	
	/**
	 * DataProvider for testValidDanhMucFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {"Salad", false}, {"Bia", false}, {"Rượu", false}, {"Nước ngọt", false}, {"Nước Suối", false} };
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
				break;
			}
		}
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Hàm xuất file Excel
	 */
//	@AfterClass
//	public void InFileExcel() {
//		try {
//			ExcelGo.writeExcelv2("D:\\Excel_File\\Xuat_File_Excel.xlsx", 0, 1, 6, "TenDanhMuc", data());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
