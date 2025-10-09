package DeepuSDET.tests;



import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import DeepuSDET.TestComponents.BaseTest;
import DeepuSDET.TestComponents.Retry;
import DeepuSDET.pageobjects.CartPage;
import DeepuSDET.pageobjects.ProductCatalogue;

/*Lets comvert this project to TESTNG and use TestNG xml file to run all 3 testcases in parallel- errorvalidation, standaloneordersubmit
 * and standalone test*/

public class ErrorValidationTest extends BaseTest {
//adding comments for github webook testing
	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class) /*retryAnalyzer is used to tell this specific test to rerun.no other test
	will be retried*/
	public void LoginErrorValidation() throws IOException
	{
		// TODO Auto-generated method stub
		
		
		landingPage.loginApplication("deepu9656@gmail.com", "Pa$w0rd"); 
		Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMsg());
		
		
	}
	@Test
	
	public void ProductErrorValidation() throws IOException
	{
		// TODO Auto-generated method stub
		
		String productName = "ZARA COAT 3";
		ProductCatalogue productcatalogue = landingPage.loginApplication("deepu9611@gmail.com", "Pa$$w0rd"); /*catching productcatalogue
		object from LandingPage*/
		List<WebElement> products = productcatalogue.getProductList();
		productcatalogue.addProductToCart(productName);
		CartPage cartpage = productcatalogue.goToCartPage(); /*catching cartpage object from Abstractcomponent class*/
		Boolean cartpresent = cartpage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(cartpresent);	
		
		
		
	}
	

}
