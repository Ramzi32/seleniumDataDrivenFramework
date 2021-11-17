package keywords;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationsKeywords extends ValidationKeywords{
	
	public Select s;
	
	public ApplicationsKeywords() throws IOException {
		String path = System.getProperty("user.dir")+"//src//test//resources//env.properties";
		prop = new Properties();
		
			FileInputStream fs = new FileInputStream(path);
			prop.load(fs);
			String env = prop.getProperty("env")+".properties";
			path = System.getProperty("user.dir")+"//src//test//resources//"+env;
			envProp = new Properties();
			 fs = new FileInputStream(path);
			 envProp.load(fs);
			
		
			System.out.println(prop.getProperty("url"));
		
		
		
	}
	
	public void defaultlogin() {
		navigate("url");
		type("username_id",envProp.getProperty("admin_user_name"));
		type("password_id",envProp.getProperty("admin_password"));
		//validateElementPresent("loginbutton_id");
		click("loginbutton_id");
		takeScreenShot();
		waitForPageToLoad();
		wait(5);
		
	}
	
	
	public void setReports(ExtentTest test) {
		this.test = test;
	}
	
	public int findCurrentStockQuantity(String LocatorKey ,String CompanyName) {
		log("Checking current quantity of Stock "+CompanyName);
		
		int row = getRowNumWithCellData(LocatorKey, CompanyName);
		if(row == -1) {
			System.out.println("Current Stock quantity is 0");
			return 0;
		}
		
		String quantity = driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+"> tr:nth-child("+row+") > td:nth-child(4)")).getText();
		log("Current Stock Quantity "+quantity);
		return Integer.parseInt(quantity);
		
	}
	
	public void selectDateFromCalendar(String date) {
		log("Selecting Date "+date);
		
		try {
			Date currentDate = new Date();
			Date dateToSel=new SimpleDateFormat("d-MM-yyyy").parse(date);
			String day=new SimpleDateFormat("d").format(dateToSel);
			String month=new SimpleDateFormat("MMMM").format(dateToSel);
			String year=new SimpleDateFormat("yyyy").format(dateToSel);
			String monthYearToBeSelected=month+" "+year;
			String monthYearDisplayed=getElement("monthyear_css").getText();
			
			while(!monthYearToBeSelected.equals(monthYearDisplayed)) {
				click("datebackButoon_xpath");
				monthYearDisplayed=getElement("monthyear_css").getText();
			}
			driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void goToTransactionHistory(String LocatorKey , String CompanyName) {
		
		int row = getRowNumWithCellData(LocatorKey, CompanyName);
		if(row ==-1)
			log("Stock is not present in the list");
		
		else
			driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+" > tr:nth-child("+row+") > td:nth-child(1)")).click();
		driver.findElement(By.cssSelector(prop.getProperty("stocktable_css") +" tr:nth-child("+row+") input.equityTransaction")).click();
		waitForPageToLoad();
			
	}
	
	
	public void goToBuySellstock(String LocatorKey , String CompanyName) {
		
		int row = getRowNumWithCellData(LocatorKey, CompanyName);
		if(row == -1) 
			log("Stock is not present in the list");
			
			else
		driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+" > tr:nth-child("+row+") >td:nth-child(1)")).click();
		driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+"  tr:nth-child("+row+") input.buySell" )).click();
		
	}

	
}
