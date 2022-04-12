package com.wolvesres.ducvh.ban;

import org.testng.annotations.Test;

import com.wolvesres.ducvh.module.ExtractedModule;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

public class checkTableSuccess {
	@Test(dataProvider = "tablesuccess")
	public void f(String tenBan, boolean expect) {
		boolean actual = ExtractedModule.checkTable(tenBan);
		assertEquals(actual, expect);
	}

	@DataProvider
	public Object[][] tablesuccess() {
		return new Object[][] { { "Ten Ban 1", true }, { "Ten Ban 2", true } };
	}
}
