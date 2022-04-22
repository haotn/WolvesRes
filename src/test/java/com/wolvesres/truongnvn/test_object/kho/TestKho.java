package com.wolvesres.truongnvn.test_object.kho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.LichSuDAO;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelLichSu;
import com.wolvesres.model.ModelNhapKho;

import exceldoing.ExcelGo;

public class TestKho {
	private ModelNhapKho nhapkho;
	private KhoDAO khodao;
	private LichSuDAO lsdao;

	@BeforeClass
	public void beforeClass() {
		nhapkho = new ModelNhapKho();
		khodao = new KhoDAO();
	}

//data base them ls
	@DataProvider(name = "dataThemnhapkho")
	public Object[][] nhapkho() {
		return new Object[][] { {new ModelKho(4,"SP01",20,"14-11-2025",true), true },{new ModelKho(5,"SP01",20,"14-11-2025",true), true }};
	}

//	Them nhap Kho
	@Test(description = "Test nhap kho", dataProvider = "dataThemnhapkho", groups = "dataThemnhapkho", priority = 1)
	public void testNhapKho(Object[] data) {
		ModelKho kho = (ModelKho) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		khodao.insert(kho);
		List<ModelKho> list = khodao.selectAll();
		ModelKho fromDatabase = khodao.selectById(list.get(list.size()-1).getId());
		kho.setId(fromDatabase.getId());
		if (kho.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu mong muốn: " + kho);
		System.out.println("Dữ liệu thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}
//xuat kho 
 
		@DataProvider(name = "dataThemxuatkho")
		public Object[][] xuatkho() {
			return new Object[][] { {new ModelKho(4,"SP01",0,"14-11-2025",false), true },{new ModelKho(5,"SP01",10,"14-11-2025",true), true }};
		}
		
//		Them nhap Kho
		@Test(description = "Test xuat kho", dataProvider = "dataThemxuatkho", groups = "dataThemxuatkho", priority = 2)
		public void testXuatKho(Object[] data) {
			ModelKho kho = (ModelKho) data[0];
			Boolean expected = (Boolean) data[1];
			ModelKho khodb = khodao.selectByObject(kho.getMaSP(), kho.getIdls());
			kho.setId(khodb.getId());
			Boolean actual = false;
			khodao.update(kho, kho.getId());
			ModelKho fromDatabase = khodao.selectById(kho.getId());
			if (kho.toString().equals(fromDatabase.toString())) {
				actual = true;
			}
			System.out.println("Dữ liệu mong muốn: " + kho);
			System.out.println("Dữ liệu thực tế: " + fromDatabase);
			System.out.println("\n");
			Assert.assertEquals(actual, expected);
		}
		
 
}
