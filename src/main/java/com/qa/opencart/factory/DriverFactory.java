package com.qa.opencart.factory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.frameworkexception.FrameException;

public class DriverFactory {
	WebDriver driver;
	OptionsManager optionsManager;
	public static String highlightElement;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); 
		
		public WebDriver initDriver(Properties prop){   //passing the object referance	
//			String browserName = prop.getProperty("browser").trim();       //1
//			String browserName = System.getProperty("browser");       //2
			String browserName = System.getProperty("browser"); //2
			if (browserName.equals(null))
			{
				 browserName = prop.getProperty("browser").trim();       //1
			}
			System.out.println("browser name issssss : "+ browserName);
			highlightElement = prop.getProperty("highlight");
			
			optionsManager = new OptionsManager(prop);
			
			switch (browserName.toLowerCase()) {
			case "chrome":
//				driver = new ChromeDriver(optionsManager.getChromeOptions());   // chromeoptions ref. variable need to store inside the chromedriver
				tlDriver.set(driver = new ChromeDriver(optionsManager.getChromeOptions()));
				break;
			case "edge":
//				driver = new EdgeDriver(optionsManager.getEdgeOptions());
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				break;
			case "firefox":
//				driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				break;
			default:
				System.out.println("Please pass the right browser.... "+ browserName);
				throw new FrameException("NOBROWSERFOUNDEXCEPTION");
			}
//			driver.manage().deleteAllCookies();
//			driver.manage().window().maximize();
//			driver.get(prop.getProperty("url"));       
//			return driver;
			getDriver().manage().deleteAllCookies();    //2
			getDriver().manage().window().maximize();
			getDriver().get(prop.getProperty("url"));       
			return getDriver();
		}
		// return the thread local copy of the driver
		public synchronized static WebDriver getDriver() {   // synchronized > not mandatory to write
			return tlDriver.get();       //2
		}
////////////		 
		public Properties initProp() {
		Properties prop = new Properties(); // design to intract with .propeties file
		FileInputStream ip = null;
		String envName = System.getProperty("env");
		System.out.println ("Running test cases on env: " + envName);
		try {
			if (envName == null) {
				System.out.println("no env is given ... hence running it on QA env....");
				ip = new FileInputStream("./src/main/resources/confiq/qa.confiq.properties");
			} else {
				System.out.println("Running test cases on env: "+ 	envName);
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/main/resources/confiq/qa.confiq.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/main/resources/confiq/dev.confiq.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/main/resources/confiq/stage.confiq.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/main/resources/confiq/uat.confiq.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/main/resources/confiq/confiq.properties"); // prod
					break; 
				default:
					System.out.println("plz pass the right env name..." + envName);
					throw new FrameException("NOVALIDENVGIVEN");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);                   // while loading if any exception will come then this try will handle 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;	
	}
		
		/**
		 * take screenshot
		 */
		public static String getScreenshot() {
			File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
			File destination = new File(path);

			try {
				FileUtils.copyFile(srcFile, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return path;
		}
		
/////////////
	
//		 public Properties initProp() {
//				Properties prop = new Properties();  // design to intract with .propeties file
//				try {
//					FileInputStream ip = new FileInputStream("./src/main/resources/confiq/confiq.properties");
//				    try {
//						prop.load(ip);   // while loading the property any exception will come then 2 nd try catch will handle 
//						} catch (IOException e) {
//						e.printStackTrace();
//					}
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				}
//				return prop;
//			}
//
//
}
/*
 * FileInputStream > It will create the coonnection with the . properties file, once connection is 
 * established and the load the properties eith the help of prop(ref)   -> pass the file ref
// thread local > is good option if you using parllel execution
 * if 2 to 4 parllel execution are there then that is fine but when so many parlel excection the use TL
 * TL >Give me a local copy of the driver for each and every thread without any dead lock codition
 *                                    OR
 * Create the local copy of the driver for each and every thead independently	
 */
 