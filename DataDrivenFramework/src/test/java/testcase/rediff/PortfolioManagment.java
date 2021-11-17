package testcase.rediff;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import BaseTest.BaseTest;

public class PortfolioManagment extends BaseTest{

	@Test
	public void CreatePortfolio(ITestContext context) {
		JSONObject data = (JSONObject)context.getAttribute("data");
		String portfolioname = (String)data.get("portfolioname");
		
		app.waitForPageToLoad();
		app.validateTitle("portfiliotitle_xpath");
		app.click("createPortfolio_id");
		app.clear("myPortfolio_id");
		app.type("myPortfolio_id",portfolioname);
		app.click("createportfoliobutton_id");
		app.waitForPageToLoad();
		app.validateSelectedValueInDropDown("dropdown_xpath", portfolioname);
		app.takeScreenShot();
		
		
	}
	
	
	@Test
	public void DeletePortfolio(ITestContext context) {
		JSONObject data = (JSONObject)context.getAttribute("data");
		String deleteportfolio = (String)data.get("portfolioname");
		app.waitForPageToLoad();
		test.log(Status.INFO,"Delete Portfolio");
		app.selectPortfolio("dropdown_xpath", deleteportfolio);
		app.waitForPageToLoad();
		app.click("deletePortfolio_xpath");
		app.acceptAlert();
		app.waitForPageToLoad();
		app.validateSelectedValueNotInDropDown("dropdown_xpath", deleteportfolio);
		app.takeScreenShot();
		
	}
	
	@Test
	public void SelectPortfolio(ITestContext context) {
		JSONObject data = (JSONObject)context.getAttribute("data");
		String portfolioname = (String)data.get("portfolioname");
		app.log("Selecting Portfolio "+portfolioname);
		app.selectPortfolio("dropdown_xpath", portfolioname);
		app.waitForPageToLoad();
		app.takeScreenShot();
	}
}
