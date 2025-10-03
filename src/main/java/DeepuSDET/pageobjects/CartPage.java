package DeepuSDET.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import DeepuSDET.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent { //this is constructor class
	//Assigining Life of driver to this class/test
	/* we have to create a constructor class to get the driver object from parent class and use that here  
	 * constructor take the same name as class name , but that will be first method to execute when we touch the class*/
	WebDriver driver;
	public CartPage(WebDriver driver)

	{
		//inititialization
		super(driver); //every child have to give super keyword to parent
		this.driver= driver;
		PageFactory.initElements(driver, this); //initializing elements for pageFactory design
	}

	//List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
	
	//we can use pageFactory design pattern to reduce the above synatx and write the script in a diffrent way
	
	@FindBy(css=".items h3")
	List<WebElement> cartItems;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutEle;
	
	
	public Boolean verifyProductDisplay(String productName)
	{
		Boolean cartpresent = cartItems.stream().anyMatch(item->item.getText().equalsIgnoreCase(productName));
		return cartpresent;
	}
	
	public CheckoutPage goToCheckOut()
	{
		checkoutEle.click();
		return new CheckoutPage(driver);
	}
	
	
	
	
}

