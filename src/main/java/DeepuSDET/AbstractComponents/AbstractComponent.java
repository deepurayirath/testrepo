package DeepuSDET.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import DeepuSDET.pageobjects.CartPage;
import DeepuSDET.pageobjects.OrderPage;

public class AbstractComponent {
	/*if we have many reusable code in our main test, then lets reuse them by creating  new resuable page object class*/
	WebDriver driver;
	public AbstractComponent(WebDriver driver) { /* first create constructor class and then create an object for driver outside the constructor
		class .after that use this.driver = driver to give driver knowledge to constructor class and reusable class */
		// TODO Auto-generated constructor stub
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(css="[routerlink*='cart']") //customized CSS
	WebElement cartHeader;
	
	@FindBy(css="[routerlink*='myorders']") 
	WebElement OrderHistory;

	public void waitForElementToAppear(By findBy)
	{

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	//when its findBy -- we can use "By" in  reusable method
	//wjhen its driver.findBy-- then we can use' webelement' - also we can use pagefactory when its driver.By
	public void waitforInvisibility(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}
	
	public void waitforWebElementToAppear(WebElement findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}

	public CartPage goToCartPage() /*using this as resuable method as there are various options that we have available on header section 
	of webpage*/
	{
		cartHeader.click();
		CartPage cartpage = new CartPage(driver);
		return cartpage;
	}
	
	public OrderPage goToOrderHistoryPage()
	{
		OrderHistory.click();
		OrderPage orderpage = new OrderPage(driver);
		return orderpage;
	}
}
