package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;

public class BaseTest {
	// for all pages referance variable we are manintaining here 
	protected WebDriver driver;             // make it public then only child class inherit the properties of the parent class
	protected LoginPage loginPage;          //protected > Access by only those classes which are the child of the BaseTest, does't matter they are in the same package or outside the package 
	protected AccountsPage accPage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	protected DriverFactory df;
	protected Properties prop;
	protected SoftAssert softAssert;
	
//	@Parameters({"browser"})	        //2 //testng > we have to read the data then only it will work according to the xml sheet
	@BeforeTest
/**
 *  this method is associated with the @parameter, @parameter reading the browser parameter from the xml file 
 *  in the setup we gave to porvide the holding parameter> String browserName	
 * @param browserName
 */
	public void setup() {    //   String browserName
		df = new DriverFactory();
		prop = df.initProp();  // In this prop we having a confiq properties, here we have to give first pref. to the xml	
//		if(browserName!=null) {
//			prop.setProperty("browser", browserName);  // we can not see the changes in th confiq sheet, because it will change at the run time 
//		}		
		driver = df.initDriver(prop);    // this driver will be a thread local driver we using TL concept		
		loginPage = new LoginPage(driver);	
		softAssert = new SoftAssert();
//		loginPage.doLogin("akhiljulaha@gmail.com", "Akhil123");                                           // don't do this it will create problem for the login page, it will login the page and if you login the page then how you check the login page test cases
	}
		
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

	

}
