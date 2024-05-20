package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataproviders.ProductDataProvider;

public class SearchTest extends BaseTest{
// for all pages referance variable we are manintaining here Ex.accPage , resultsPage

	@BeforeClass
	public void serachSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
//	@DataProvider                             // Ex > C1 ,R5
//	public Object[][] getProductSearchKeyData() {               // No need to maintain Excel sheet for this 
//		return new Object[][] {
//			
//			{"Macbook"},                  // 3R 1C
//			{"iMac"},           
//			{"Samsung"}         
//		};
//	}
/**
 * 	this test will run 3 times because 3 rows are there this concept is called data driven approach
 * here we driving the data from method or Excel sheet
 * @param searchKey
 */
	
//	@Test(dataProvider ="getProductSearchKeyData")    
	@Test(dataProvider = "productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)
	public void searchProductResultscountTest(String searchKey) {
		resultsPage = accPage.doSerach(searchKey);   // this line is repeated in every TEST but it's fine 
		Assert.assertTrue(resultsPage.getProductResultCount()>0);
	}
//	@Test(dataProvider ="getProductSearchKeyData") 
	@Test(dataProvider = "productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)
	public void searchPageTitleTest(String searchKey) {   //imac
		resultsPage = accPage.doSerach(searchKey);
		String actSearchTitle = resultsPage.getResultPageTitle(searchKey);
		System.out.println("Search Page Title : "+ actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - "+ searchKey);
	}
//	@DataProvider
//	public Object[][] getProductTestData() {
//		return new Object[][] {
//			
//			{"Macbook", "MacBook Pro"},       // 4R 2C
//			{"iMac","iMac"},
//			{"Samsung","Samsung SyncMaster 941BW"},
//			{"Samsung","Samsung Galaxy Tab 10.1"},
//
//		};
//	}
	
//	@Test(dataProvider="getProductTestData")                                 //here we need to data 1> search  2> click , we need another data provider
	@Test(dataProvider = "productDataWithName", dataProviderClass = ProductDataProvider.class)
	public void selectProductTest(String searchKey, String productName) {
		resultsPage = accPage.doSerach(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		String actproductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual Product Name : "+ actproductHeaderName);
		Assert.assertEquals(actproductHeaderName, productName);
	}

//	@DataProvider
//	public Object[][] getProductImagesTestData() {
//		return new Object[][] {
//			
//			{"Macbook", "MacBook Pro", 4},       // 4R 3C
//			{"iMac","iMac", 3},
//			{"Samsung","Samsung SyncMaster 941BW", 1},
//			{"Samsung","Samsung Galaxy Tab 10.1", 7},
//
//		};
//	}
//	@Test(dataProvider="getProductImagesTestData")  
	@Test(dataProvider = "productDataWithImage", dataProviderClass = ProductDataProvider.class)
	public void ProductImagesTest(String searchKey, String productName, int expImagesCount) {
		resultsPage = accPage.doSerach(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		int actProductImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual Product Images Count : "+ actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, expImagesCount);
	}
	
	
}
