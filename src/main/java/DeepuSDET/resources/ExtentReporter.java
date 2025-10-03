spackage DeepuSDET.resources;

import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {
	
	public static ExtentReports getReporterObject()
	{
		
			//we need two classes for extent reports-- one is ExtentReports and other is ExtendSparkReporter
			//basic configs of a report are set using ExtentSparksReporter
			String path = System.getProperty("user.dir")+"\\Reports\\index.html"; /* creating a path for the reports to store*/
			ExtentSparkReporter reporter = new ExtentSparkReporter(path);
			reporter.config().setReportName("web Automation Results");
			reporter.config().setDocumentTitle("Test Results");
			
			//now lets create object for main Reports class which is ExtentReports
			
			ExtentReports extent = new ExtentReports(); // - declaring it globally outside this Test
			extent.attachReporter(reporter); //attaching extentetsparksreporter to extent report
			extent.setSystemInfo("Tester", "Deepu");
			return extent; //returns extent to other required classes - also make sure to update the return type to static and 
			//ExtentReports
			
			
			
			
		
	}
	

}
