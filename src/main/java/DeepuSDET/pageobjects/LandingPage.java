package DeepuSDET.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import DeepuSDET.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent { //this is constructor class
	//Assigining Life of driver to this class/test
	/* we have to create a constructor class to get the driver object from parent class and use that here  
	 * constructor take the same name as class name , but that will be first method to execute when we touch the class*/
	WebDriver driver;
	public LandingPage(WebDriver driver)

	{
		super(driver); //super keyword helps to send driver object/knowledge from child(LandingPage) to Parent(AbstractComponent)
		//inititialization
		this.driver= driver;
		PageFactory.initElements(driver, this); //initializing elements for pageFactory design
	}

	//WebElement useremail = driver.findElement(By.id("userEmail"));
	
	//we can use pageFactory design pattern to reduce the above synatx and write the script in a diffrent way
	
	@FindBy(id="userEmail")  //at run time, this script/code will be constructed like the above code
	WebElement useremail; //then the same will be stored in useremail variable
	
	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement error;
	/* Now lets write a public class method to provide values for username and password- remember that page object class will only handle 
	 * elements and actions. the data should come from  original class
	 * */
	public ProductCatalogue loginApplication(String email,String password)
	{   //this is  our action method
		useremail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		ProductCatalogue productcatalogue = new ProductCatalogue(driver);
		return productcatalogue;
	}
	
	//lets create another action method here for invoking url
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMsg() {
		waitforWebElementToAppear(error);
		String errorMsg =error.getText();
		return errorMsg;
	}
	
}
