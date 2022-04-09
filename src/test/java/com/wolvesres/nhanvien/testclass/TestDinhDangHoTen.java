package com.wolvesres.nhanvien.testclass;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wolvesres.test.data.DataGenerator;

public class TestDinhDangHoTen {
	private String hoTen;
	private DataGenerator data;

	@BeforeTest
	public void beforeTest() {
		data = new DataGenerator();
		hoTen = data.generateFullname();
	}
	@Test
	private void checkFullname() {
		for (int i = 0; i < 100; i++) {
			testCase();
		}
	}

	@Test
	public void testCase() {
		if (!hoTen.contains(" ")) {
			Assert.assertFalse(false);
		}
		System.out.println("Loop");
	}

}
