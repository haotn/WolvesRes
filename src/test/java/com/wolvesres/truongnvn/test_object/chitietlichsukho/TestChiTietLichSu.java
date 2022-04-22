package com.wolvesres.truongnvn.test_object.chitietlichsukho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.ChiTietLichSuDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.LichSuDAO;
import com.wolvesres.model.ModelChiTietLichSu;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelLichSu;
import com.wolvesres.model.ModelNhapKho;

import exceldoing.ExcelGo;

public class TestChiTietLichSu {
	private ModelNhapKho nhapkho;
	private ChiTietLichSuDAO ctdao;
	@BeforeClass
	public void beforeClass() {
		nhapkho = new ModelNhapKho();
		ctdao = new ChiTietLichSuDAO();
	}

//data base them ctls
	@DataProvider(name = "chitietlskho")
	public Object[][] ctlskho() {
		return new Object[][] { { new ModelChiTietLichSu(4, "SP01", 20, 15000), true },
				{ new ModelChiTietLichSu(5, "SP01", 20, 15000), true } };
	}

//	Them lsct
	@Test(description = "Test chi tiet", dataProvider = "chitietlskho", groups = "dataThemlsct", priority = 1)
	public void testLSCT(Object[] data) {
		ModelChiTietLichSu ct = (ModelChiTietLichSu) data[0];
		Boolean expected = (Boolean) data[1];
		Boolean actual = false;
		ctdao.insert(ct);
		ModelChiTietLichSu fromDatabase = ctdao.selectByObject(ct.getIdLS(), ct.getMaSP());
		ct.setId(fromDatabase.getId());
		if (ct.toString().equals(fromDatabase.toString())) {
			actual = true;
		}
		System.out.println("Dữ liệu mong muốn: " + ct);
		System.out.println("Dữ liệu thực tế: " + fromDatabase);
		System.out.println("\n");
		Assert.assertEquals(actual, expected);
	}

}
