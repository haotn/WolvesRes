package com.wolvesres.ducvh.voucher;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;
import com.wolvesres.helper.XDate;

import static org.testng.Assert.assertEquals;

import java.util.Date;

import org.testng.annotations.DataProvider;

public class VoucherEndPastTestFail {
	@Test(dataProvider = "voucherquanreal")
	public void f(boolean insert, String maVoucher, String soLuong, String ngayKetThuc, String ngayBatDau,
			String giamGia, boolean expect) {
		boolean actual = ExtractedModule.checkVoucher(insert, maVoucher, soLuong, ngayKetThuc, ngayBatDau, giamGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] voucherquanreal() {
		// ngày kết thúc - quá khứ
		return new Object[][] { { true, "v0001", "1", XDate.toString(XDate.addDays(new Date(), -1), "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"), "1", false },
				{ true, "v0001", "1", XDate.toString(XDate.addDays(new Date(), -365), "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"), "1", false },
				{ true, "v0001", "1", XDate.toString(XDate.addDays(new Date(), -999), "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"), "1", false } };
	}
}
