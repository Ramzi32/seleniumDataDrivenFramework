package runner;

import java.util.ArrayList;
import java.util.List;

public class Runner {
	
	public static void main(String[] args) {
		TestNGRunner testnG = new TestNGRunner(1);
		
		testnG.CreateSuite("StockManagment", false);
		testnG.addListener("Listeners.myTestNGListener");
		testnG.addTest("Add New Stock");
		testnG.addTestParameters("action", "addstock");
		List<String> includedMethods = new ArrayList<String>();
		includedMethods.add("doLogin");
		testnG.addTestClasses("testcase.rediff.Session", includedMethods);
		includedMethods = new ArrayList<String>();
		includedMethods.add("SelectPortfolio");
		testnG.addTestClasses("testcase.rediff.PortfolioManagment", includedMethods);
		includedMethods = new ArrayList<String>();
		includedMethods.add("addNewStock");
		includedMethods.add("verifyStockPresence");
		includedMethods.add("verifyStockQuantity");
		includedMethods.add("verifyTransactionHistory");
		testnG.addTestClasses("testcase.rediff.StockManagment", includedMethods);
		testnG.run();
		
		
		
		
	}

}
