package com.qa.opencart.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By; 
public class LoginPage {                   //never extends the loginPagetest
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	//1 const of the page class
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	// 2 private By loactors
	private By emailId = By.id("input-email");    // loactors are very sensitive so declared private
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdlink = By.linkText("Forgotten Password11");
	private By footerLinks = By.xpath("//footer//a");
	private By loginErrorMessg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	private By registerLink = By.linkText("Register");    // this locator for register page test case

	// 3 public page actions/mehods
	@Step("getting login page title")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIsAndCapture(AppConstants.LOGIN_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
	}
	@Step("getting login page url")
	public String getLoginURL() {
	return eleUtil.waitForURLContainsAndCapture(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
	}
	@Step("checking forgot pwd link exist on the login page")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.CheckElementIsDisplayed(forgotPwdlink);
	}
	@Step("getting footer link")
	public List<String> getFooterLinksList() {
		List<WebElement> footerLinksList = eleUtil.waitForElementsVisible(footerLinks, AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> footerTextList = new ArrayList<String>();
		for(WebElement e: footerLinksList) {
			String text = e.getText();
			footerTextList.add(text);
		}
		return footerTextList;
	}
	@Step("login with username {0} and password{1}")
	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("correct creds are : "+ userName + ":"+ pwd);
		eleUtil.waitForElementVisble(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(emailId, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		// return the next landing page ----AccountsPage ---- that is called page chaining model 
//	Page Chaining Model> whenever you clicking any button or click and it will navigating to other page, it's method
//  responsiblity to return the next landing page class object 
		return new AccountsPage(driver);	
	}
	
	@Step("login with wrong username {0} and password{1}")
	public boolean doLoginWithWrongCredentials(String userName, String pwd) {
		System.out.println("Wrong creds are : "+ userName + ":"+ pwd);
		eleUtil.waitForElementVisble(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(emailId, userName);    //need to clear the data is imp
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn); 
		String errorMessg = eleUtil.doGetElemntText(loginErrorMessg);
		System.out.println(errorMessg);
		if(errorMessg.contains(AppConstants.LOGIN_ERROR_MESSAGE)) {
			return true;
		}
		return false;
			
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
