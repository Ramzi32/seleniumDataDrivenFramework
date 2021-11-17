package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRunner {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		
		Map<String,String> classMethods = new DataUtil().loadClassMethods();
		String path = System.getProperty("user.dir")+"//src//test//resources//jsons//testconfig.json";
		JSONParser parse = new JSONParser();
		JSONObject json = (JSONObject)parse.parse(new FileReader(new File(path)));
		String Parallelsuites = (String)json.get("parallelsuites");
		TestNGRunner testnG = new TestNGRunner(Integer.parseInt(Parallelsuites));
		
		JSONArray testsuites = (JSONArray)json.get("testsuites");
		
		for(int sID=0;sID<testsuites.size();sID++) {
			JSONObject suitedetails = (JSONObject)testsuites.get(sID);
			String runmode = (String)suitedetails.get("runmode");
			if(runmode.equals("Y")) {
			String name = (String)suitedetails.get("name");
			String testdatajsonfile = System.getProperty("user.dir")+"//src//test//resources//jsons//"+(String)suitedetails.get("testdatajsonfile");
			String testdataxlsfile = (String)suitedetails.get("testdataxlsfile");
			String suitefilename = (String)suitedetails.get("suitefilename");
			String paralleltests = (String)suitedetails.get("paralleltests");
			boolean pTest = false;
			if(paralleltests.equals("Y"))
				pTest = true;
			System.out.println(runmode +"------"+name);
			testnG.CreateSuite("name", pTest);
			testnG.addListener("Listeners.myTestNGListener");
			
			String pathSuiteJSON = System.getProperty("user.dir")+"//src//test//resources//jsons//"+suitefilename;
			JSONParser suiteParser = new JSONParser();
			JSONObject suiteJSON = (JSONObject)suiteParser.parse(new FileReader(new File(pathSuiteJSON)));
			JSONArray suiteTestCases = (JSONArray)suiteJSON.get("testcases");
			for(int sTID=0;sTID<suiteTestCases.size();sTID++) {
				JSONObject suiteTestCase = (JSONObject)suiteTestCases.get(sTID);
				//String tRunMode = (String)suiteTestCase.get("runmode");
				
				String tName = (String)suiteTestCase.get("name");
				JSONArray parameternames = (JSONArray)suiteTestCase.get("parameternames");
				JSONArray executions = (JSONArray)suiteTestCase.get("executions");
				for(int eID=0;eID<executions.size();eID++) {
					JSONObject testcase = (JSONObject)executions.get(eID);
					String tRunMode = (String)testcase.get("runmode");
					if(tRunMode != null && tRunMode.equals("Y")) {
					String executionname = (String)testcase.get("executionname");
					String dataflag = (String)testcase.get("dataflag");
					int dataSet = new DataUtil().getDataIterations(testdatajsonfile, dataflag);
					for(int dID=0;dID<dataSet;dID++) {
					JSONArray parametervalues = (JSONArray)testcase.get("parametervalues");
					JSONArray methods = (JSONArray)testcase.get("methods");
					//add to testng
					testnG.addTest(tName+"--"+executionname+"-It-"+(dID+1));
					for(int pID=0;pID<parameternames.size();pID++) {
						testnG.addTestParameters((String)parameternames.get(pID),(String)parametervalues.get(pID));
					}
					testnG.addTestParameters("datafilepath", testdatajsonfile);
					testnG.addTestParameters("dataflag", dataflag);
					testnG.addTestParameters("Iteration", String.valueOf(dID));
					List<String> includedMethods=  new ArrayList<String>();
					for(int mID=0;mID<methods.size();mID++) {
						String method = (String)methods.get(mID);
						String methodClass = classMethods.get(method);
						if(mID==methods.size()-1 ||!((String)classMethods.get((String)methods.get(mID+1))).equals(methodClass)) {
							includedMethods.add(method);
							testnG.addTestClasses(methodClass, includedMethods);
							includedMethods = new ArrayList<String>();
						}else {
							includedMethods.add(method);
						}
						
					}
					System.out.println("----------------------");
					}
					}
			}
			}
			
			}
		}
		testnG.run();
	}

}
