package DeepuSDET.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import DeepuSDET.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent { //this is constructor class
	//Assigining Life of driver to this class/test
	/* we have to create a constructor class to get the driver object from parent class and use that here  
	 * constructor take the same name as class name , but that will be first method to execute when we touch the class*/
	WebDriver driver;
	public ProductCatalogue(WebDriver driver)

	{
		//inititialization
		super(driver); //every child have to give super keyword to parent
		this.driver= driver;
		PageFactory.initElements(driver, this); //initializing elements for pageFactory design
	}

	//List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
	
	//we can use pageFactory design pattern to reduce the above synatx and write the script in a diffrent way
	
	@FindBy(css=".mb-3")   //This is page factory add List<> if its Findelements
	List<WebElement> products; 
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	
	By productsBy = By.cssSelector(".mb-3");/*creating an obhject with the List of webelements(products) and passing it to abstract 
	class below as an arugment */
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastContainer = By.cssSelector(".toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productsBy);
		return products; /*this will return all the products in the page, but before taht we have to call wait function to make sure 
		all the page elements are visible or not before collecting all products.The above line of code will help for that */
	}
	
	//creating another action method for capturing webelement of the required product and then clicking it
	public WebElement getProductByName(String productName)
	{
		WebElement prod = getProductList().stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName)
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastContainer);
		waitforInvisibility(spinner);
		
	}
	
	
}

