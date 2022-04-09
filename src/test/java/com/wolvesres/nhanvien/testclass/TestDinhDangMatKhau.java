package com.wolvesres.nhanvien.testclass;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wolvesres.test.data.DataGenerator;

public class TestDinhDangMatKhau {
	private DataGenerator data;

	@BeforeClass
	public void beforeTest() {
		data = new DataGenerator();
	}

	@Test
	public void testPasswordTrue() {
		for (int i = 0; i < 100; i++) {
			testTrue();
		}
	}

	@Test
	public void testPasswordFalse() {
		for (int i = 0; i < 100; i++) {
			testFalse();
		}
	}

	@Test
	public void testTrue() {
		String password = data.generatePassword(8, false);
		assertTrue(!password.contains(" "));
	}

	@Test
	public void testFalse() {
		String password = data.generatePassword(8, true);
		assertFalse(!password.contains(" "));
	}
}
