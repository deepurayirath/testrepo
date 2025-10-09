package DeepuSDET.tests;



import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import DeepuSDET.TestComponents.BaseTest;
import DeepuSDET.pageobjects.CartPage;
import DeepuSDET.pageobjects.CheckoutPage;
import DeepuSDET.pageobjects.ConfirmationPage;
import DeepuSDET.pageobjects.LandingPage;
import DeepuSDET.pageobjects.OrderPage;
import DeepuSDET.pageobjects.ProductCatalogue;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

public class StandaloneOrderSubmit extends BaseTest {
//adding comments for tetsing githubwebhook
//one more comment for github webhook retest
	@Test(dataProvider="getData",groups= {"purchase"})
	public void orderSubmit(HashMap<String,String> input) throws IOException
	{
		// TODO Auto-generated method stub
		//send HashMap <oBJECT,oBJECT> input(any name) in arguemengt for the Method/test that we need to run
		
		//String productName = "ZARA COAT 3";
		ProductCatalogue productcatalogue = landingPage.loginApplication(input.get("email"), input.get("password")); /*catching productcatalogue
		object from LandingPage*/
		List<WebElement> products = productcatalogue.getProductList();
		productcatalogue.addProductToCart(input.get("product"));
		CartPage cartpage = productcatalogue.goToCartPage(); /*catching cartpage object from Abstractcomponent class*/
		Boolean cartpresent = cartpage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(cartpresent);
		CheckoutPage checkoutPage = cartpage.goToCheckOut();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmPage = checkoutPage.submitOrder();
		String confirmMsg = confirmPage.getconfirmationPage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));
		
		
		
	}
	
	@Test(dependsOnMethods= {"orderSubmit"})
	public void OrderHistoryTest() 
	{
		String productName = "ZARA COAT 3";
		ProductCatalogue productcatalogue = landingPage.loginApplication("deepu9656@gmail.com", "Pa$$w0rd");
		OrderPage orderhistory = productcatalogue.goToOrderHistoryPage(); //calling OrderPage and gotoorderhistory method
		Assert.assertTrue(orderhistory.verifyOrderDisplay(productName));
		
	}
	
	
	
	@DataProvider
	public Object[][] getData() throws IOException /*we can use Hashmap if we have to pass multiple data to our test*/
	{
		//this is direct HashMap method- commentwed out bcoz we are using 
		/*HashMap<String,String> map = new HashMap<String,String>(); //syntax for hashmap-can be object or string based on data type
		map.put("email", "deepu9656@gmail.com");
		map.put("password", "Pa$$w0rd");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String,String> map1 = new HashMap<String,String>(); //syntax for hashmap-can be object or string based on data type
		map1.put("email", "deepu9611@gmail.com");
		map1.put("password", "Pa$$w0rd");
		map1.put("product", "ADIDAS ORIGINAL");*/
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\DeepuSDET\\Data\\PurchaseOrder.Json"); /*call getJsonDataToMap 
		method from DataReader class and store it as Hashmap.Also give the required filepath in the main Test rather than Base Test*/
		return new Object[][] {{data.get(0)},{data.get(1)}}; //this will store any type of data(string, integer, float etc and return that when called in main test
		/*Above line of code- "data" has the list of HashMaps- now call that using index to retrieve the value present in that.*/
	}
		
		
		
		

	

}
