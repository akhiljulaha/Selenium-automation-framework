package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNagativeTest extends BaseTest {
	
	@DataProvider
	public Object[][] incorrectLoginTest() {
		return new Object[][] {
			{"auto123@gmail.com", "123456"},
			{"test@@gmail.com", "123456"},
			{"auto", "test"},
			{" "," "}
		};
	}

	@Test(dataProvider="incorrectLoginTest")
	public void loginWithWrongCredentialsTest(String username, String password) {
		Assert.assertTrue(loginPage.doLoginWithWrongCredentials(username, password));
	}
}
