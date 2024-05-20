package com.qa.opencart.tests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataproviders.ProductDataProvider;
import com.qa.opencart.pojo.Product;
import com.qa.opencart.pojo.Product;

public class SearchDataTest extends BaseTest {

	@BeforeClass
	public void serachSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider(name = "productData")    // we can set the name also for the DataProvider, while mapping you can either use the method name or DataProvider name also
	public Object[][] getProductTestData() {
		return new Object[][] {
			{new Product("Macbook", "MacBook Pro", 4)},                        
			{new Product("iMac","iMac", 3)},
			{new Product("Samsung","Samsung SyncMaster 941BW", 1)},
			{new Product("Samsung","Samsung Galaxy Tab 10.1", 7)},
			};
		}    // we can use this in a seperate class also 

	
	@Test(dataProvider ="productData", dataProviderClass = ProductDataProvider.class)    
	public void searchProductResultscountTest(Product product) {
		resultsPage = accPage.doSerach(product.getSearchKey());    
		Assert.assertTrue(resultsPage.getProductResultCount()>0);
	}
	
	@Test(dataProvider ="productData", dataProviderClass = ProductDataProvider.class)    
	public void searchPageTitleTest(Product product) {
		resultsPage = accPage.doSerach(product.getSearchKey());
		String actSearchTitle = resultsPage.getResultPageTitle(product.getSearchKey());
		System.out.println("Search Page Title : "+ actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - "+ product.getSearchKey());
	}
	
	
	@Test(dataProvider ="productData", dataProviderClass = ProductDataProvider.class)    

	public void selectProductTest(Product product) {
		resultsPage = accPage.doSerach(product.getSearchKey());
		productInfoPage = resultsPage.selectProduct(product.getProductName());
		String actproductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual Product Name : "+ actproductHeaderName);
		Assert.assertEquals(actproductHeaderName, product.getProductName());
	}

	
	@Test(dataProvider ="productData", dataProviderClass = ProductDataProvider.class)    
	public void ProductImagesTest(Product product) {
		resultsPage = accPage.doSerach(product.getSearchKey());
		productInfoPage = resultsPage.selectProduct(product.getProductName());
		int actProductImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual Product Images Count : "+ actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, product.getProductImages());
	}
}
