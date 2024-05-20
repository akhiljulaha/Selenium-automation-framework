package com.qa.opencart.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

public class AccountsPageTest extends BaseTest {
	/*
	 * here no need to create a object of the account page, in the LoginPage class, doLogin method itself
	 * giving the object of the AccountsPage class, just call the doLogin method
	 */
	@BeforeClass
	public void accPageSetup() {
      accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));  // accPage ref. inherit from the BaseTest class   // ?????DDD
	}
	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
	}
	@Test
	public void idLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void isMyAccLinkExistTest() {
		Assert.assertTrue(accPage.isMyAccountLinkExist());
	}
	@Test
	public void accPageHeadersCountTest() {
		List<String> actAccHeadersList = accPage.getAccountPageHeaderList();
		Assert.assertEquals(actAccHeadersList.size(), 4);
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> actAccHeadersList = accPage.getAccountPageHeaderList();
//		List<String> expAccHeadersList = Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter");// sort and then compare 
		
		Assert.assertEquals(actAccHeadersList, AppConstants.EXP_ACCOUNTS_HEADERS_LIST);
	}
	
	
	
}
