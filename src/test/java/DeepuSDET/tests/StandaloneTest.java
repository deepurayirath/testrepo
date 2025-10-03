package DeepuSDET.tests;



import java.time.Duration;
import java.util.List;
import org.testng.Assert;

import DeepuSDET.pageobjects.LandingPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

public class StandaloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();  /* with WebDriverManager we can invoke chrome browser without having chrome driver 
		in our system  */
	//we have to add WebdriveManager jar from mvn repo.
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage landingPage = new LandingPage(driver); //sending driver as arg to LandingPage class
		driver.findElement(By.id("userEmail")).sendKeys("deepu9656@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Pa$$w0rd");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
		.orElse(null);/* store/convert webelement to steams, then filter through the stream by css selector and get text with Zara coat 3.
		Then find the first option retrieved , else return null to webelement 'prod'*/ 
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		//adding expliciyt wait for webpage elements to fully load
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		List<WebElement> cartItems = driver.findElements(By.cssSelector(".items h3"));
		Boolean cartpresent = cartItems.stream().anyMatch(item->item.getText().equalsIgnoreCase(productName)); /*.anyMatch will  return boolean value based 
		whether it finds that value or not */
		Assert.assertTrue(cartpresent);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		//lets try to deal with auto suggestive drop down using actions class.
		//we can also try this with for loop and if and also with java streams
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")),"india").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		//lets try regular expression xpath now to find and select india to click
		
		driver.findElement(By.xpath("//button[contains(@class,'ta-item')][2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		String confirmMsg= driver.findElement(By.cssSelector(".hero-primary")).getText();
		 //this means the msg that we stored is equal as the msg provided.
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));
		driver.close();
		
		
		
		

	}

}
