package runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;





public class TestNGRunner {
	
	TestNG testng ;
	List<XmlSuite> allSuites; //All test suites to Run
	XmlSuite suite;			//Single test suite
	XmlTest test;			//Single test case
	List<XmlTest> Alltest;  //All test cases in one Testsuite
	Map<String , String> testngparas; //parameters
	List<XmlClass> testclasses ;   //All classes in one testcase
	
	public TestNGRunner(int threadpoolsize) {
		allSuites = new ArrayList<XmlSuite>(); //Initialize all test suite
		testng = new TestNG();
		testng.setSuiteThreadPoolSize(threadpoolsize); //telling testng how many times a suite should run parallely
		testng.setXmlSuites(allSuites);   //inform testng about test suites
		}
	public void CreateSuite(String Suitename , boolean parallelTests) {
		suite = new XmlSuite();
		
		suite.setName(Suitename);
		if(parallelTests == true)
			suite.setParallel(ParallelMode.TESTS);
		allSuites.add(suite);
		}
	
	public void addTest(String TestName) {
		test = new XmlTest(suite);
		test.setName(TestName);
		testngparas =new  HashMap<String , String>(); //Init test parameters blank
		testclasses = new ArrayList<XmlClass>(); // emptytestclasses
		test.setParameters(testngparas); // blank 0 parametrers
		test.setXmlClasses(testclasses); // blank 0 classes
		}
	
	public void addTestParameters(String map , String value) {
		testngparas.put(map, value);
		}
	
	public void addTestClasses(String classnames , List<String> includedMethodNames) {
		XmlClass testclass = new XmlClass(classnames);
		List<XmlInclude> classmethods = new ArrayList<XmlInclude>();
		int priority =1;
		for(String methodname:includedMethodNames) {
			XmlInclude methods = new XmlInclude(methodname , priority);
			classmethods.add(methods);
			priority ++;
			}
		testclass.setIncludedMethods(classmethods);
		testclasses.add(testclass);
	}
	
	public void addListener(String ListenerFile) {
		suite.addListener("Listeners.myTestNGListener");
		
	}
	
	public void run() {
		testng.run();
	}
	
	
	

	
}
