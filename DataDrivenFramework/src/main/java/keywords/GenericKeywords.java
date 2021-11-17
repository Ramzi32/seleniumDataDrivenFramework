package keywords;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Reports.ExtentManager;

public class GenericKeywords {
	
	public WebDriver driver;
	public Properties prop;
	public Properties envProp;
	public ExtentTest test;
	public SoftAssert softassert;
	public Select s;
	
	
	
	
	
	public void openBrowser(String bName) {
		
	log("Opening The Browser " + bName);
	
	if(prop.get("grid_run").equals("Y")) {
		
		DesiredCapabilities cap = new DesiredCapabilities();
		if(bName.equals("Mozilla")) {
			cap.setBrowserName("firefox");
			cap.setJavascriptEnabled(true);
			cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		}else if(bName.equals("Chrome")) {
			cap.setBrowserName("chrome");
			cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		}
		
		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444"), cap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}else {
	
		if(bName.equals("Mozilla")) {
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"logs\\firefox.log");
			FirefoxOptions options = new FirefoxOptions();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("dom.webnotifications.enabled",false);
			options.setProfile(profile);
			driver = new FirefoxDriver(options);
			}
		else if(bName.equals("Chrome")) {
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			ChromeOptions ops = new ChromeOptions();
			//capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			ops.setPageLoadStrategy(PageLoadStrategy.EAGER);
			ops.addArguments("--disable-notifications");
			ops.addArguments("--start-maximized");
			driver = new ChromeDriver(ops);
			
		}
			
			else if(bName.equals("Edge")) {
				System.setProperty("webdriver.edge.driver", "C:\\Users\\sidan\\Drivers\\msedgedriver.exe");
				driver = new EdgeDriver();
				
			}
	}
		//Implicit wait 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				
	}
	
	//Central function to Extract Elements
	public WebElement getElement(String LocatorKey) {
		//check the presence of webElement
		if(!isElementPresent(LocatorKey)) {
			//report faliure
			log("Element not present "+LocatorKey);
		}
		//Check visibility of WebElement 
		if(!isElementVisible(LocatorKey)) {
			//report failure
			log("Element not visible " +LocatorKey);
		}
		
		WebElement e = driver.findElement(getLocator(LocatorKey));
		
		return e;
		}
		
	
	
	
	//Checks if element is present on webpage
	public boolean isElementPresent(String LocatorKey) {
		
		log("Checking Presence of " + LocatorKey);
		WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
		
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(LocatorKey)));
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	
	//Checks if element is visible on webpage
    public boolean isElementVisible(String LocatorKey) {
    	
    	WebDriverWait wait   = new WebDriverWait(driver , Duration.ofSeconds(10));
    	try {
    			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(LocatorKey)));
    	}catch(Exception e) {
    		return false;
    	}
		
    	return true;
	}
	
    
    //navigate to url
	public void navigate(String urlkey) {
		
		log("Navigating to "+urlkey);
		String url = envProp.getProperty(urlkey);
		driver.get(url);
		
	}

	//click on webemelent 
	public void click(String LocatorKey) {
	log("Clicking on "+LocatorKey);
		//String Locator = prop.getProperty(LocatorKey);
		
		getElement(LocatorKey).click();
		
		
	}
	
	public void clickEnterButton(String LocatorKey) {
		log("Clicking Enter Button ");
		getElement(LocatorKey).sendKeys(Keys.ENTER);
	}
	
	//type in a text box(Webelement)
	public void type(String locatorkey , String data) {
		log("Typing in "+locatorkey);
		//String Locator = prop.getProperty(locatorkey);
		
		getElement(locatorkey).sendKeys(data);
		
		
	}
	
	
	// find locatpr by locator id
	public By getLocator(String LocatorKey) {
		By by = null;
		if(LocatorKey.endsWith("_id"))
			by = By.id(prop.getProperty(LocatorKey)); 
		else if (LocatorKey.endsWith("_xpath")) 
			by = By.xpath(prop.getProperty(LocatorKey));
		else if(LocatorKey.endsWith("_css"))
			by = By.cssSelector(prop.getProperty(LocatorKey));
		else if(LocatorKey.endsWith("_name"))
			by = By.name(prop.getProperty(LocatorKey));
		else if(LocatorKey.endsWith("_classname"))
			by = By.className(prop.getProperty(LocatorKey));
		else if(LocatorKey.endsWith("_linktext"))
			by = By.linkText(prop.getProperty(LocatorKey));
		else
			by = By.partialLinkText(prop.getProperty(LocatorKey));
		
		return by;
	}
	
	
	// reporting function
	public void log(String msg) {
		System.out.println(msg);
		test.log(Status.INFO, msg);
	}
	
	// reporting failure
	public void reportFailures(String FailureMsg , boolean StopOnFailure) {
		System.out.println(FailureMsg);
		test.log(Status.FAIL,FailureMsg); // failure in Extent reports
		takeScreenShot();
		softassert.fail(FailureMsg); // failure in TestNG
		if(StopOnFailure) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("CriticalFailure", "Y");
			softassert.assertAll(); // assert all failures
		}
	}
	
	public void assertAll() {
		
		softassert.assertAll();
	}
	
	public void takeScreenShot() {
		
		//fileName of Screenshot
		
		Date d = new Date();
		String ScreenShotFile = d.toString().replace(":","_").replace(" ", "_")+".png";
		// take Screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
			//get dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+"//"+ScreenShotFile));
			
			//attach screenshot to reports
			test.log(Status.INFO,"Screenshot -->"+test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+"//"+ScreenShotFile));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clear(String LocatorKey) {
		log("clearing " +LocatorKey);
		getElement(LocatorKey).clear();
	}
	
	public void waitForPageToLoad(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i=0;
		// ajax status
		while(i!=10){
		String state = (String)js.executeScript("return document.readyState;");
		System.out.println(state);

		if(state.equals("complete"))
			break;
		else
			wait(2);

		i++;
		}
		// check for jquery status
		i=0;
		while(i!=10){
	
			Long d= (Long) js.executeScript("return jQuery.active;");
			System.out.println(d);
			if(d.longValue() == 0 )
			 	break;
			else
				 wait(2);
			 i++;
				
			}
		
		}
	
	public void wait(int time) {
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void selectPortfolio(String LocatorKey , String Value) {
		
		s = new Select(getElement(LocatorKey));
		 s.selectByVisibleText(Value);
		 
	}
	
	public void acceptAlert(){
		test.log(Status.INFO, "Switching to alert");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.alertIsPresent());
		try{
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			test.log(Status.INFO, "Alert accepted successfully");
		}catch(Exception e){
				reportFailures("Alert not found when mandatory",true);
		}
		
	}
	
	public int getRowNumWithCellData(String tableLocator , String Data) {
		
		WebElement table = driver.findElement(getLocator(tableLocator));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		System.out.println(rows.size());
		for(int rNum=0;rNum<rows.size();rNum++) {
			WebElement row = rows.get(rNum);
			List<WebElement> cells = row.findElements(By.tagName("td"));
			System.out.println(cells.size());
			for(int cNum=0;cNum<cells.size();cNum++) {
				WebElement cell = cells.get(cNum);
				System.out.println("Text "+cell.getText());
				if(!cell.getText().trim().equals(""))
					if(Data.startsWith(cell.getText()))
						return (rNum+1);
			}
			
		}
		
		return -1;
		
	}
	
	public String getText(String LocatorKey) {
		String val = getElement(LocatorKey).getText();
		return val;
	}
		
		
		
	}
	

