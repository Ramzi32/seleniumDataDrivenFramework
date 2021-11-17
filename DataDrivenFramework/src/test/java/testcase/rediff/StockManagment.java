package testcase.rediff;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import BaseTest.BaseTest;
import keywords.ApplicationsKeywords;

public class StockManagment extends BaseTest{
	
	@Test
	public void addNewStock(ITestContext context) {
		JSONObject data = (JSONObject)context.getAttribute("data");
		String CompanyName = (String)data.get("stockname");
		String selectDate =(String)data.get("date");
		String stockQuantity = (String)data.get("quantity");
		String stockPrice = (String)data.get("price");
		
		app.log("Adding "+stockQuantity+"stocks of "+CompanyName);
		int quantityBeforeModification = app.findCurrentStockQuantity("stocktable_css" , CompanyName);
		context.setAttribute("quantityBeforeModification", quantityBeforeModification);
		
		app.click("addstock_id");
		app.type("addstockname_id", CompanyName);
		app.wait(5);
		app.clickEnterButton("addstockname_id");
		app.click("stockPurchaseDate_id");
		app.selectDateFromCalendar(selectDate);
		app.type("addstockqty_id", stockQuantity);
		app.type("addstockprice_id", stockPrice);
		
		app.click("addStockButton_id");
		app.takeScreenShot();
		app.waitForPageToLoad();
		//app.takeScreenShot();
		app.log("Stock of "+CompanyName+" added sucessfully");
		
	}

	@Test
	public void verifyStockPresence(ITestContext context) {
		String CompanyName = "Birla Corporation Ltd";

		app.log("Verifying presence of Stock  "+CompanyName);
		int row = app.findCurrentStockQuantity("stocktable_css" , CompanyName);
		if(row==-1)
			app.reportFailures("Stock "+CompanyName+" is not present in Portfolio", true);
		else
			app.log("Stock "+CompanyName+" is Present in portfolio" );
			app.takeScreenShot();
	}
	
	@Parameters({"action"})
	@Test
	public void verifyStockQuantity(String action ,ITestContext context) {
		String CompanyName = "Birla Corporation Ltd";
		String selectDate = "2-10-2021";
		String stockQuantity = "100";
		String stockPrice = "200";
		
		app.log("Verifying Stock Quantity after action "+action);
		//quantity after adding stocks
		int quantityafterAddingStocks = app.findCurrentStockQuantity("stocktable_css" , CompanyName);
		int modifiedQuantity = Integer.parseInt(stockQuantity);
		int expectedmodifiedQuantity = 0;
		
		
		//quantity before adding stocks
		int quantitybeforeAddingStocks = (Integer)context.getAttribute("quantityBeforeModification");
		if(action.equals("addstock"))
			expectedmodifiedQuantity = quantityafterAddingStocks - quantitybeforeAddingStocks;
		else if(action.equals("sellstock"))
				expectedmodifiedQuantity = quantitybeforeAddingStocks -quantityafterAddingStocks;
		
		if(modifiedQuantity !=expectedmodifiedQuantity )
			app.reportFailures("Stock quantity does not match", true);
		app.log("Stock modified quantity as per expected");
	}
	
	//verify transaction history
	@Parameters({"action"})
	@Test
	public void verifyTransactionHistory(String action) {
		String CompanyName = "Birla Corporation Ltd";
		String selectDate = "2-10-2021";
		String stockQuantity = "100";
		String stockPrice = "200";
		
		app.log("Verifying Transaction History of " +CompanyName);
		app.goToTransactionHistory("stocktable_css", CompanyName);
		
		String changedStockQuantity = app.getText("lastsharequantity_xpath");
		app.log("Changed Stock Quantity is " + changedStockQuantity);
		
		if(action.equals("sellstock"))
			stockQuantity = "-"+stockQuantity;
		
		if(!changedStockQuantity.equals(stockQuantity))
			app.reportFailures("Got change is transaction history as " + changedStockQuantity, true);
		
		app.log("Transaction History ok");
	}
	
	@Parameters({"action"})
	@Test
	public void modifyStock(String action , ITestContext context) {
		String CompanyName = "Birla Corporation Ltd";
		String selectDate = "2-10-2021";
		String stockQuantity = "100";
		String stockPrice = "200";
		
		app.log("modifying stock quantity by "+action+" stocks");
		int quantitybeforeModifaction = app.findCurrentStockQuantity("stocktable_css", CompanyName);
		context.setAttribute("quantityBeforeModification", quantitybeforeModifaction);
		
		app.goToBuySellstock("stocktable_css", CompanyName);
		if(action.equals("sellstock"))
			app.selectPortfolio("selectaction_id", "Sell");
		else
			app.selectPortfolio("selectaction_id", "Buy");
		
		app.click("buySellCalendar_id");
		app.log("selecting date "+selectDate);
		app.selectDateFromCalendar(selectDate);
		app.type("buysellqty_id", stockQuantity);
		app.type("buysellprice_id", stockPrice);
		app.click("buySellStockButton_id");
		app.takeScreenShot();
		app.waitForPageToLoad();
		app.log("stock "+action);
		
	}
	
}
