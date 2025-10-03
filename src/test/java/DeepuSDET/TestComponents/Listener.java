package DeepuSDET.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import DeepuSDET.resources.ExtentReporter;

public class Listener extends BaseTest implements ITestListener{
	ExtentTest test;
	ExtentReports extent = ExtentReporter.getReporterObject(); /* creating object and calling getReporterObject method from ExtentReporter
	class*/
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); /*Thread Local is used make sure to the variables are thread safe while 
	running a tests in parallel.*/

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return ITestListener.super.isEnabled();
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
		test = extent.createTest(result.getMethod().getMethodName()); /*this will check the name of every method/test that are running
		and dynamically get the method name and assign that to the report name*/
		extentTest.set(test); //this will set a unique thread ID for each testcases in parallel mode.
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
		extentTest.get().log(Status.PASS, "Test Passes");
	}

	@Override
	public void onTestFailure(ITestResult result) {  //this result will have knowledge about driver on each test/method.
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
		extentTest.get().fail(result.getThrowable()); /* Another method to print error message in report.with that error message we will be
		failing this test as it reached this onTestFailure methods block already*/
		extentTest.get().log(Status.FAIL, "Test Failed"); //this is just for logging in report
		//lets attach screenshot for failed testcase
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			/* Lets understand above code- First it will get the the information about class( testcase thats currently running) 
			 * from 'result and when we say realclass,from the xml file it will go to real class/testcase .
			 * from that class, it will get the field/value of driver.*/
			
			e1.printStackTrace();
		} 
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
		/*Initializing the file path to null first. Once the screenshot is taken , it will be send to addScreenCaptureFromPath method
		 * and that method takes the path of file from our system and attach to the extents report( "test")*/
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		
		extent.flush(); //to stop listening to the tests and get ultimate report
	}
	
	//ItestListener contains various Listener methods that this Listener class can utilize
	

}
