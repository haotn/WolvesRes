package com.wolvesres.ducvh.voucher;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;
import com.wolvesres.helper.XDate;

import static org.testng.Assert.assertEquals;

import java.util.Date;

import org.testng.annotations.DataProvider;

public class VoucherDiscountEmptyTestFail {
	@Test(dataProvider = "voucherdiscountempty")
	public void f(boolean insert, String maVoucher, String soLuong, String ngayKetThuc, String ngayBatDau,
			String giamGia, boolean expect) {
		boolean actual = ExtractedModule.checkVoucher(insert, maVoucher, soLuong, ngayKetThuc, ngayBatDau, giamGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] voucherdiscountempty() {
		// giảm giá - trống: rỗng, khoảng cách, tab, null
		return new Object[][] { { true, "v0001", "1", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "", false },
				{ true, "v0001", "1", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", " ", false },
				{ true, "v0001", "1", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "\t", false },
				{ true, "v0001", "1", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", null, false }  };
	}
}
