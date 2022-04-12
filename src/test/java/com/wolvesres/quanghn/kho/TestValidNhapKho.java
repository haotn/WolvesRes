package com.wolvesres.quanghn.kho;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelNhapKho;
/**
 * Kiểm tra số lượng nhập kho thất bại do nhập số âm
 * @author huynh
 *
 */
public class TestValidNhapKho {
//	private List<ModelNhapKho> listNhapKho;
	/**
	 * Before class
	 */
	@BeforeClass
	public void beforClass() {
//		listNhapKho = Arrays.asList(new ModelNhapKho[] {
//			new ModelNhapKho(1, "nước ngọt", 20, 10, "14-05-2025", true);
//		});
	}

	/**
	 * DataProvider for testValidNhapKhoSoAmFail
	 * 
	 * @return Object[][]
	 */
	@DataProvider
	public Object[][] data() {
		return new Object[][] { {-5, false}, {-56, false}, {-12, false}, {-593485, false}, {-231, false}};
	}

	/**
	 * TestCase testValidNhapKhoSoAmFail
	 * 
	 * @param soluong
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidNhapKhoSoAmFail(int soluong, Boolean expected) {
		Boolean actual = true;
		if (FormValidator.isLessThan(soluong, 1)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);

	}
	
}
