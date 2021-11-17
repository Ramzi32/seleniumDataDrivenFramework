package keywords;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ValidationKeywords extends GenericKeywords{
	
	public void validateTitle(String LocatorKey) {
		log("checking title of " + LocatorKey);
		getElement(LocatorKey).getText();
		
	}
	
	public void validateText(String LocatorKey, String Value) {
		
		validateSelectedValueInDropDown(LocatorKey, Value);
		log("Validating if " +Value+ "is deleted" + LocatorKey);
		
	}

	public void validateElementPresent(String LocatorKey) {
		
		log("Checking avaliability of" +LocatorKey);
		
		getElement(LocatorKey).isDisplayed();
		
	}
	
	public void validateLogin() {
		
	}
	
	public void validateSelectedValueInDropDown(String LocatorKey , String Value) {
		
		 s = new Select(getElement(LocatorKey));
		 String selectedvalue = s.getFirstSelectedOption().getText();
		if(!selectedvalue.equals(Value)) {
			System.out.println("Selected value doesnot match" + Value);
		reportFailures("Value" +Value+"not present in DropDown "+LocatorKey,true);
		}
		else
			System.out.println("Selected value is " + selectedvalue);
		
			
		
	}
	
	
	public void validateSelectedValueNotInDropDown(String LocatorKey , String Value) {
		s = new Select(getElement(LocatorKey));
		String selectedvalue = s.getFirstSelectedOption().getText();
		if(selectedvalue.equals(Value)) {
			System.out.println("Selected value is " + selectedvalue);
		reportFailures("Value" +Value+" is still present in DropDown "+LocatorKey,true);
		}
		else
			System.out.println("Selected value doesnot match" + Value);
		
	}
		
		
		
	}

