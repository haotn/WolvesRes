package com.wolvesres.ducvh.voucher;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.DTool;
import com.wolvesres.helper.XDate;

import exceldoing.ExcelGo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;

public class VoucherQuanMinusTestFail {
	@Test(dataProvider = "voucherquanminus")
	public void f(boolean insert, String maVoucher, String soLuong, String ngayKetThuc, String ngayBatDau,
			String giamGia, boolean expect) {
		boolean actual = DTool.checkVoucher(insert, maVoucher, soLuong, ngayKetThuc, ngayBatDau, giamGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] voucherquanminus() {
		// số lượng - âm
		return new Object[][] { { true, "v0001", "-1", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "1", false },
				{ true, "v0001", "-10", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "1", false },
				{ true, "v0001", "-777", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "1", false },
				{ true, "v0001", "-1000000", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "1", false },
				{ true, "v0001", "-999999999", XDate.toString(new Date(), "dd-MM-yyyy"), "17-02-2022", "1", false } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "isInsert,maVoucher,soLuong,ngayKetThuc,NgayBatDau,giamGia", voucherquanminus());
	}
}
