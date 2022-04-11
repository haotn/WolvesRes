package com.wolvesres.haotn.donvi;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.model.ModelDonViTinh;

/**
 * Test auto generate id unit
 * 
 * @author Brian
 *
 */
public class TestAutoGenerateIdUnit {
	private DonViTinhDAO dvtDao;
	private List<ModelDonViTinh> listUnit;
	private String idUnit;

	/**
	 * Before Class - Generate global variable value
	 */
	@BeforeClass
	public void beforeClass() {
		dvtDao = new DonViTinhDAO();
		listUnit = new ArrayList<ModelDonViTinh>();
	}

	@BeforeMethod
	public void beforeMethod() {
		listUnit.clear();
		listUnit.addAll(dvtDao.selectAll());
	}

	@DataProvider(name = "data")
	public Object[][] data() {
		return new Object[][] { { "Bình", true }, { "Thố", true }, { "Lọ", true }, { "Chai", true } };
	}

	@Test
	public void testAutoGenerateIdUnit(String unitName, Boolean expected) {
		ModelDonViTinh entity = new ModelDonViTinh();
		entity.setTenDVT(unitName);
	}

}
