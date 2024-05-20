package com.qa.opencart.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmailId() {
		return "testautomaton"+System.currentTimeMillis()+"@gmail.com";
//		return "testautomation"+ UUID.randomUUID()+"@gmail.com";
	}
	
//	@DataProvider(name="regData")
//	public Object[][] getUserRegTestData() {                                                 //1
//		return new Object[][] {
//			{"abhi","anand","1234567890","abhi098@gmail.com", "yes"},
//			{"abhiii","anandiii","1234567899","abhi929292@gmail.com", "no"},
//			{"abhiuuuu","anand000","1234567882","abhi77777@gmail.com", "yes"},
//				
//		};
//	}
	
	
	@DataProvider(name ="regExcelData")                //2
	public Object[][] getRegExcelTestData() {
	 Object regData[][] = ExcelUtil.getTextData(AppConstants.REGISTER_SHEET_NAME);
	 return regData;
	}
	
//	@Test(dataProvider = "regData")           //1
	@Test(dataProvider = "regExcelData")
	public void userRegisterTest(String firstName,String lastName, String telephone, String password, String subscribe) {
		String actRegSuccMessg = registerPage.registeruser(firstName, lastName, getRandomEmailId(), telephone, password, subscribe);
	Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
	}
	
	
	
}
