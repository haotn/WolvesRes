package com.wolvesres.ducvh.voucher;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;
import com.wolvesres.helper.XDate;

import static org.testng.Assert.assertEquals;

import java.util.Date;

import org.testng.annotations.DataProvider;

public class VoucherQuanMinusTestFail {
	@Test(dataProvider = "voucherquanminus")
	public void f(boolean insert, String maVoucher, String soLuong, String ngayKetThuc, String ngayBatDau,
			String giamGia, boolean expect) {
		boolean actual = ExtractedModule.checkVoucher(insert, maVoucher, soLuong, ngayKetThuc, ngayBatDau, giamGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] voucherquanminus() {
		// số lượng - âm
		return new Object[][] { { true, "v0001", "-1", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "1", false },
				{ true, "v0001", "-1000", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "1", false },
				{ true, "v0001", "-999999999", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "1", false } };
	}
}
