package Testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import keywords.ApplicationsKeywords;

public class Testcases {

	
	@Test
	public void createPortfolioTest() throws IOException {
		
		ApplicationsKeywords app = new ApplicationsKeywords(); // initialize properties files
		app.openBrowser("Chrome");
		app.navigate("url");
		app.type("username_id", "sidana96@gmail.com");
		app.type("password_id", "sidana96");
		app.validateElementPresent("loginbutton_id");
		app.click("loginbutton_id");
		
		
	}
}
