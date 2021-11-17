package testcase.rediff;

import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import BaseTest.BaseTest;



public class Session extends BaseTest{
	
	@Test
	public void doLogin(ITestContext context) {
		app.log("Logging In");
		app.openBrowser("Chrome");
		app.navigate("url");
		app.type("username_id", "sidana96@gmail.com");
		app.type("password_id", "sidana96");
		app.validateElementPresent("loginbutton_id");
		app.click("loginbutton_id");
		app.takeScreenShot();
		
	}

	@Test
	public void doLogOut() {
		System.out.println("Logged out");
		
	}
}
