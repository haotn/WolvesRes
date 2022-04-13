package com.wolvesres.truongnvn.kho;

import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.form.kho.JDialogXuatKho;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.main.Main;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelNhapKho;

import exceldoing.ExcelGo;
import junit.framework.Assert;

/**
 * Kiểm tra số lượng xuất kho thất bại do bỏ trống
 * 
 * Kiểm tra giá xuất kho thành công
 * 
 * @author nvnhu
 */
public class TestSoluongXuatKho {
	/**
	 * 
	 * */

	private List<Module> listNhapKho;
	private JDialogXuatKho xuatkho;

	@BeforeClass(groups = { "" })
	public void beforClass() {
		List<Module> listNhapKho = new ArrayList<>();
		listNhapKho = new ArrayList<>();
		JFrame frame = new JFrame();
		xuatkho = new JDialogXuatKho(frame, false);
		ModelNhapKho kho = new ModelNhapKho(1, 20000, 100, "2022/04/12", "SP01");
		xuatkho.addToListNhapKho(kho);
	}

	/**
	 * DataProvider for so luong xuat kho bi bo trong
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "dataSoluong")
	public Object[][] data() {
		return new Object[][] { { "", false } ,{ "\t", false } ,{ " ", false } };
	}

	/**
	 * DataProvider for gia xuat kho bi bo trong
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "datagia")
	public Object[][] dataGia() {
		return new Object[][] { { 0, false }, { 9000, false } };
	}

	/**
	 * TestCase số lượng xuat kho bị bỏ trống
	 * 
	 * @param sl
	 * @param expected
	 */
	@Test(dataProvider = "dataSoluong", groups = "soluongnull", priority = 0)
	public void testValidSoLuongEmpty(String soluong, Boolean expected) {
		Boolean actual = FormValidator.isTextIsNotEmpty(soluong);
		Assert.assertEquals(expected, actual);
	}

	/**
	 * kiem tra gia
	 */
	@Test(dataProvider = "datagia", groups = "soluongnull", priority = 1)
	public void testValidGia(float gia, Boolean expected) {
		xuatkho.getTxtGia().setText(String.valueOf(gia));
		Boolean actual = xuatkho.checkGia(0);
		Assert.assertEquals(expected, actual);
	}
	
	@AfterClass
	public void writreExcel() throws IOException{
		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 1, 6, "gia",dataGia() );
	}
}
