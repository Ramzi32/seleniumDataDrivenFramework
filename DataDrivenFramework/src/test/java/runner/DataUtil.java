package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataUtil {

	
	public Map<String,String> loadClassMethods() throws FileNotFoundException, IOException, ParseException{
			
			String path = System.getProperty("user.dir")+"//src//test//resources//jsons//classmethods.json";
			Map<String , String> classMethodMap = new HashMap<String , String>();
			JSONParser parser = new JSONParser();
			
			JSONObject json = (JSONObject)parser.parse(new FileReader(new File(path)));
			JSONArray classdetails = (JSONArray)json.get("classdetails"); //JsonArray
			
			for(int cMID=0;cMID<classdetails.size();cMID++) {
				JSONObject classdetail = (JSONObject)classdetails.get(cMID);
				String className = (String)classdetail.get("class");
				JSONArray classmethods = (JSONArray)classdetail.get("methods");
				//System.out.println(className);
				for(int mID=0;mID<classmethods.size();mID++) {
					String methodName = (String)classmethods.get(mID);
					//System.out.println(methodName);
					classMethodMap.put(methodName, className);
				}
				//System.out.println("--------------------------");
			}
			
			//System.out.println(classMethodMap);
			return classMethodMap;
		}
	
	public int getDataIterations(String FilePath , String dataFlag ) throws FileNotFoundException, IOException, ParseException {
		
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject)parser.parse(new FileReader(new File(FilePath)));
		
		JSONArray testDataSets = (JSONArray)json.get("testdata");
		for(int dSetID=0;dSetID<testDataSets.size();dSetID++) {
			JSONObject testdata = (JSONObject)testDataSets.get(dSetID); 
			String flag = (String)testdata.get("flag");
			if(dataFlag.equals(flag)) {
			JSONArray datasets = (JSONArray)testdata.get("data");
			//for(int d=0;d<datasets.size();d++) {
				//JSONObject dataset = (JSONObject)datasets.get(d);
				return datasets.size();
				//String portfolioname = (String)dataset.get("portfolioname");
				//String runmode = (String)dataset.get("runmode");
			//}
		}
		}
		return -1;
		
		
	}
	
	public JSONObject  getTestData(String FilePath , String dataFlag, int iteration) throws FileNotFoundException, IOException, ParseException{
		
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject)parser.parse(new FileReader(new File(FilePath)));
		
		JSONArray testDataSets = (JSONArray)json.get("testdata");
		for(int dSetID=0;dSetID<testDataSets.size();dSetID++) {
			JSONObject testdata = (JSONObject)testDataSets.get(dSetID); 
			String flag = (String)testdata.get("flag");
			if(dataFlag.equals(flag)) {
			JSONArray datasets = (JSONArray)testdata.get("data");
			JSONObject dataset = (JSONObject)datasets.get(iteration);
			//String portfolioname = (String)dataset.get("portfolioname");
			//String runmode = (String)dataset.get("runmode");
			return dataset;
			
		}
	}
		return null;
	}
	}

