package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.xpath("//div[@id='content']//h1");
	private By productImages = By.xpath("//ul[@class='thumbnails']//img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	
	private Map<String, String> productInfoMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	public String getProductHeaderName() {
	 return	eleUtil.doGetElemntText(productHeader);
	}
	
	public int getProductImagesCount() {
		return eleUtil.waitForElementsVisible(productImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
	}
	
	public Map<String, String> getProductInfo() {
//		productInfoMap = new HashMap<String, String>();  // if want to maintain order then use LinkeshashMap instead of HashMap 
//		productInfoMap = new LinkedHashMap<String, String>();
		productInfoMap = new TreeMap<String, String>();  // it will store the data on the sorted order Capital letter > small letter > numeric keys
		getProductMetaData();
		getProductPriceData();
		productInfoMap.put("productname", getProductHeaderName());
		return productInfoMap;                // here we have 2 info
	}
	
/**
Brand: Apple
Product Code: Product 18
Reward Points: 800
Availability: In Stock
 */
	private void getProductMetaData() {   // private because we not returning anything here so no nees to expose this method 
		List<WebElement> metaList =  eleUtil.getElements(productMetaData);
		for(WebElement e : metaList) {
			String metaText = e.getText();
			String metaInfo[] = metaText.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}
// $2,000.00
// Ex Tax: $2,000.00	
	private void getProductPriceData() {     
		List<WebElement> priceList =  eleUtil.getElements(productPriceData);
		String priceValue = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText();
		String exTaxPricevalue = exTaxPrice.split(":")[1].trim();           // ignoring the left part --- $2,000.00
		productInfoMap.put("productprice", priceValue);    // if we don't have key value, then we can pass our own key alos like we passing here -- productprice
		productInfoMap.put("extraprice", exTaxPricevalue); 
		}
	
	
	
}
