package com.wolvesres.truongnvn.khuban;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.dao.KhuBanDAO;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelKhuBan;

import exceldoing.ExcelGo;
import junit.framework.Assert;
/**
 * Kiểm tra tên khu bàn thất bại do nhập trùng khu bàn
 * 
 * */
public class TestEqualsKhuBan {
	private KhuBanDAO khubandao;
	private List<ModelKhuBan> listKhuBan;

	/**
	 * Before class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		khubandao = new KhuBanDAO();
		listKhuBan = new ArrayList<ModelKhuBan>();
		listKhuBan.addAll(khubandao.selectAll());
	}

	@DataProvider(name = "dataKhuBan")
	public Object[][] data() {
		return new Object[][] { {"Sảnh Chính", false }, {"Trệch", false } ,{"Khu A", false }};
	}

	/**
	 * Test trùng tên bàn
	 * 
	 * @param sdt
	 * @param expected
	 */
	@Test(dataProvider = "dataKhuBan", groups = "khubantrung", priority = 0)
	public void testTrungTenKhuBan(String khuban, Boolean expected) {
		Boolean actual = true;
		for(ModelKhuBan item: listKhuBan) {
			if(FormValidator.isTextEqual(khuban, item.getTenKhuBan())){
				actual = false;
				break;
			}
		}
		
		Assert.assertEquals(expected, actual);
	}
//	@AfterClass
//	public void writreExcel() throws IOException{
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "khuBan",data());
//	}
}
