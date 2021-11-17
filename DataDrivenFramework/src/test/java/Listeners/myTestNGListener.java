package Listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class myTestNGListener implements ITestListener{
	
	@Override
	public void onTestFailure(ITestResult result) {
		
		System.out.println("Test Failed " + result.getName());
		System.out.println(result.getThrowable().getMessage());
		ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
		test.log(Status.FAIL,result.getThrowable().getMessage());
		
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		
		System.out.println("Test Passed " + result.getName());
		ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
		test.log(Status.PASS,result.getName()+"- Test Passed");
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("TestSkipped " +result.getName());
		ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
		test.log(Status.SKIP, result.getName()+"-  Test Skip");
	}

}
