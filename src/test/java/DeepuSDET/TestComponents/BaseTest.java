package DeepuSDET.TestComponents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import DeepuSDET.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
/*we can create a global properties file and use properties class to parse the properties file and use that globally in testcases.
 * for example we can set values/methods in property file and decide which browser to invoke as per requirement*/
	public WebDriver driver; //declaring Webdriver gloabally
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws IOException {
		
		/*Lets create an object for property class
		 * --then use FileInputStream to invoke or call the property file and start loading/using the file*/
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\DeepuSDET\\resources\\GlobalData.properties");
		prop.load(fis);
		/*Let see how we cam drive the globalProperities from maven
		 * follow below line of code- we are going to use Java Ternary operatot*/
		String browsername = System.getProperty("browser")!=null ? System.getProperty("browser"):prop.getProperty("browser");
		/*above code means that, if the value that u get from global property file (using system.getproperty) is not equal to null,
		 * then execute the first condition(meaning it will check what value is set for browser in property file and execute the 
		 * test accordingly. If  the value that u get from global property file (using system.getproperty) is  equal to null,
		 * then execute the second condition (that is get it from the property file and run accordingly*/
		//String browsername = prop.getProperty("browser");
		
		if(browsername.contains("chrome"))
		{
			ChromeOptions options = new ChromeOptions(); //we can use ChromeOptions class for running test in headless mode
			
		WebDriverManager.chromedriver().setup();  /* with WebDriverManager we can invoke chrome browser without having chrome driver 
		in our system  */
	//we have to add WebdriveManager jar from mvn repo.
		if (browsername.contains("headless")) {
			options.addArguments("headless");
		}
		
		/* explanation for above statement- if ur mvn command conatains onlu chrome, it will go inside the loop and check
		 * if there is headless present or not. If present, then the chrome browser wil run in headless mode
		 * else it will run in normal mode*/
		/*Instead of creating seperate objects for each page object class in main class, we can create object in the respective Page 
		 * Object classes*/
		 driver = new ChromeDriver(options); //send ChromeOptions object as an argument
		 driver.manage().window().setSize(new Dimension(1440,900)); //recommeneded to run application on full screen mode.
		
		}
		else if(browsername.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "C:\\DEEPU\\chromedriver\\geckodriver.exe");
			driver = new FirefoxDriver();
			
		}
		else if(browsername.equalsIgnoreCase("edge"))
		{
			//code for invoking edge browser
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
		
	}
	//lets write method to call DataReader file in this Base Test
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException 
	{
		//read Json file to String
		String JsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);  //give filePath and give arguments as String/Object filePath in method
		
		//now String to HashMap using Jackson DataBIND
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(JsonContent, new TypeReference<List<HashMap<String ,String>>>(){});
		 /* Convert the String and create two Hashmaps(in this case) and the put that in one list*/
		return data;
		
		//{map, map1} data will be returned like this
	}
	
	//utility for Screenshots
		public String getScreenshot(String testCaseName,WebDriver driver) throws IOException
		{
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File file = new File(System.getProperty("user.dir")+"\\Reports\\" + testCaseName + ".png");
			FileUtils.copyFile(source, file);
			return System.getProperty("user.dir") + "\\Reports\\" + testCaseName +".png";
			/*Above lines of code wil Take a screenshot, and save it in a source filw and then copy that to 'file'
			 * and ultimately returning the path where the screenshot is present*/
		}
	
	
	@BeforeMethod(alwaysRun=true) //will run always no matter what the group tag is set to run
	public LandingPage launchApplication() throws IOException {
		 
		driver = initializeDriver(); /* Initialize driver again to continue with the rest of steps(hitting url)*/
		landingPage = new LandingPage(driver); //sending driver as arg to LandingPage class
		landingPage.goTo();
		return landingPage;
	}
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
}
