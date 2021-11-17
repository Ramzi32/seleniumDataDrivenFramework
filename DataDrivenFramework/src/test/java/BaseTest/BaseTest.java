package BaseTest;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Reports.ExtentManager;
import keywords.ApplicationsKeywords;
import runner.DataUtil;

public class BaseTest {
	
	public ApplicationsKeywords app;
	public ExtentReports rep;
	public ExtentTest test;
	
	@BeforeTest(alwaysRun = true)
	public void beforetest(ITestContext context) throws IOException, NumberFormatException, ParseException{
		
		System.out.println("-----------Before Test-----------------");
		String datafilepath = context.getCurrentXmlTest().getParameter("datafilepath");
		String dataflag = context.getCurrentXmlTest().getParameter("dataflag");
		String Iteration = context.getCurrentXmlTest().getParameter("Iteration");
		JSONObject data =new DataUtil().getTestData(datafilepath, dataflag, Integer.parseInt(Iteration));
		context.setAttribute("data", data);
		String runmode = (String)data.get("runmode");
		
		//Init app Keyword and share it with all tests
		app = new ApplicationsKeywords();
		context.setAttribute("app", app);
		
		//Init reports for testcases
		rep = ExtentManager.getReports();
		test = rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO ,"Starting Test"+context.getCurrentXmlTest().getName());
		app.setReports(test);
		context.setAttribute("reports", rep);
		context.setAttribute("test", test);
		if(!runmode.equals("Y")){
			test.log(Status.SKIP, "Skipping test as runmode is N");
			throw new SkipException("Skipping test as runmode is N");
		}
		
	}
	
	@BeforeMethod(alwaysRun = true)
	public void BeforeMethod(ITestContext context) {
		System.out.println("-----------Before Method----------------");
		
		app = (ApplicationsKeywords)context.getAttribute("app");
		rep = (ExtentReports)context.getAttribute("reports");
		test = (ExtentTest)context.getAttribute("test");
		
		String CriticalFailure = (String)context.getAttribute("CriticalFailure");
		if(CriticalFailure != null && CriticalFailure.equals("Y")) {
			test.log(Status.FAIL,"Critical Failure in Previous test");
		throw new SkipException("Critical Failure in Previous test");
		
}
		
	}
	
	@AfterTest(alwaysRun = true)
	public void AfterTest(ITestContext context) {
		
		app = (ApplicationsKeywords)context.getAttribute("app");
		if(app!=null)
			app.driver.quit();
		
		
		rep = (ExtentReports)context.getAttribute("reports");
		if(rep!= null)
		rep.flush();
		
	}

}
