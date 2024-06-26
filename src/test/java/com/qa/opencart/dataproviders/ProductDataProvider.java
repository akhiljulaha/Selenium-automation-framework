package com.qa.opencart.dataproviders;

import org.testng.annotations.DataProvider;

import com.qa.opencart.pojo.Product;

public class ProductDataProvider {

	
	@DataProvider(name = "productData")    // we can set the name also for the DataProvider, while mapping you can either use the method name or DataProvider name also
	public Object[][] getProductTestData() {
		return new Object[][] {
			{new Product("Macbook", "MacBook Pro", 4)},                          //calling constructor for the product 
			{new Product("iMac","iMac", 3)},
			{new Product("Samsung","Samsung SyncMaster 941BW", 1)},
			{new Product("Samsung","Samsung Galaxy Tab 10.1", 7)},
			};
		}
	
	@DataProvider(name = "productDataWithSearchKey")                             // Ex > C1 ,R5
	public Object[][] getProductSearchKeyData() {               // No need to maintain Excel sheet for this 
		return new Object[][] {
			
			{"Macbook"},          //R1        // 3R 1C
			{"iMac"},            // R2
			{"Samsung"}          //R3
		};
	}
	
	@DataProvider(name = "productDataWithName") 
	public Object[][] getProductData() {
		return new Object[][] {
			
			{"Macbook", "MacBook Pro"},       // 4R 2C
			{"iMac","iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"},

		};
	}
	
	@DataProvider(name = "productDataWithImage") 
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			
			{"Macbook", "MacBook Pro", 4},       // 4R 2C
			{"iMac","iMac", 3},
			{"Samsung","Samsung SyncMaster 941BW", 1},
			{"Samsung","Samsung Galaxy Tab 10.1", 7},

		};
	}
	
}
