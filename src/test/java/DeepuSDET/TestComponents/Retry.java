package DeepuSDET.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	//initializing number of rerun counts
	int count = 0;
	int maxRetry = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		
		if(count<maxRetry)
		{
			count++;
			return true;
		}
		return false;
	}
	//for Retrying flaky failed testcases

}
