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

public class VoucherEndPastTestFail {
	@Test(dataProvider = "voucherendpast")
	public void f(boolean insert, String maVoucher, String soLuong, String ngayKetThuc, String ngayBatDau,
			String giamGia, boolean expect) {
		boolean actual = DTool.checkVoucher(insert, maVoucher, soLuong, ngayKetThuc, ngayBatDau, giamGia);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] voucherendpast() {
		// ngày kết thúc - quá khứ
		return new Object[][] { { true, "v0001", "1", XDate.toString(XDate.addDays(new Date(), -1), "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"), "1", false },
				{ true, "v0001", "1", XDate.toString(XDate.addDays(new Date(), -30), "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"), "1", false },
				{ true, "v0001", "1", XDate.toString(XDate.addDays(new Date(), -49), "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"), "1", false },
				{ true, "v0001", "1", XDate.toString(XDate.addDays(new Date(), -365), "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"), "1", false },
				{ true, "v0001", "1", XDate.toString(XDate.addDays(new Date(), -999), "dd-MM-yyyy"), XDate.toString(new Date(), "dd-MM-yyyy"), "1", false } };
	}

	@AfterClass
	public void excelGo() throws IOException {
//		ExcelGo.writeExcelv2("D:\\demo.xlsx", 0, 0, 6, "isInsert,maVoucher,soLuong,ngayKetThuc,NgayBatDau,giamGia", voucherendpast());
	}
}
