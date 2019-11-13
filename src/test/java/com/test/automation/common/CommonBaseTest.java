package com.test.automation.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.CommonBaseTest.TestDataXLS;
import com.test.automation.common.framework.Data;
import com.test.automation.common.framework.Browser.Browsers;
import com.test.automation.common.framework.XlsData;

public class CommonBaseTest {
	
	protected String sFileName="";
	public static String myMethod = null;
	public static String[] myMethods = new String[50];
	public static int myMethodCount=0;
	private SeHelper se;
	private Log logger = LogFactory.getLog(CommonBaseTest.class);
	
	public String getMethod(Method method) {

		String sMethod = method.getName();
		myMethodCount++;
		myMethods[myMethodCount] = sMethod;
		//System.out.println("Running the methods :");
		//System.out.println(sMethod + " " + myMethodCount);
		return myMethods[myMethodCount];
		
		
	}
	
	public String getEnv() {
		
		String Strurl = SystemPropertyUtil.getBaseStoreUrl();
		//String[] env = Strurl.split("/");
		//return env[2];
		return Strurl;
	}
	public SeHelper se() {
		
		return this.se;
	}
	public void setSe(SeHelper se) {
		this.se = se;
	}
	
	
	@BeforeMethod(alwaysRun = true)
	protected void beforeMethod(Method method, Object[] params) {
		Test test = method.getAnnotation(Test.class);
		//Browsers myBrowser = (Browsers) params[0];		
		//SeHelper se = ((SeHelper) params[1]);
		Browsers myBrowser = se.currentBrowser();
		getMethod(method);
		se.log().trace("Test Method: " + method.getName());
		se.log().trace("Description: " + test.description());
		se.log().trace("Browser: " + myBrowser.toString());		
		se.util().sleep(1000);

		
	}
	
	@AfterMethod(alwaysRun = true)
	protected void afterMethod(Method method,  ITestResult result, Object[] params) {
		//SeHelper se = ((SeHelper) params[1]);
		se.log().trace("End of " + method.getName() + " Result: " + result.isSuccess() + "\n");
		se.reporter().endResult(result.isSuccess(), se);
		//params[params.length-1]="";
		se.log().printLogBuilder();
		se.log().testSeperator();
		se.log().couchDb(result.isSuccess(), String.valueOf(result.isSuccess()));
		se.browser().quit();
	}
	
	
	
	
	/**    
     * @author SaiGnapika
     * @param baseParameters Object[][] to weave into
     * @param newParameters List of objects to add
     * @return new Object[][]
     */
    public static Object[][] weaveInParameters(Object[][] baseParameters, List<?> newParameters) {
        List<List<Object>> returnData = new ArrayList<List<Object>>();
        for (Object[] baseParameter : baseParameters) {
            for (Object newParameter : newParameters) {
                List<Object> column = new ArrayList<Object>();
                Collections.addAll(column, baseParameter);
                column.add(newParameter);
                returnData.add(column);
            }
        }

        Object[][] convertedReturnData = new Object[returnData.size()][];
        for (int row = 0; row < returnData.size(); row++) {
            convertedReturnData[row] = returnData.get(row).toArray();
        }
        return convertedReturnData;
    }
    
    public static Object[][] weaveInSeHelpers(Object[][] baseParameters) {
        List<List<Object>> returnData = new ArrayList<List<Object>>();
        for (Object[] baseParameter : baseParameters) {
            List<Object> column = new ArrayList<Object>();
            Collections.addAll(column, baseParameter);
            column.add(new SeHelper());
            returnData.add(column);
        }

        Object[][] convertedReturnData = new Object[returnData.size()][];
        for (int row = 0; row < returnData.size(); row++) {
            convertedReturnData[row] = returnData.get(row).toArray();
        }
        return convertedReturnData;
    }
	
	/**
	 * Method level annotation to specify xml file
	 * @author SaiGnapika
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface Mobile
	{
		String orientation();
	}
	
	/**
	 * Method level annotation to specify xml file
	 * @author SaiGnapika
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface TestData
	{
		String fileName();
	}
	
	protected void testTearDown(SeHelper se) {
		se.verify().checkForFail();
	}
	
	/**
	 * Method level annotation to specify XLS file and Sheet Number
	 * @author SaiGnapika
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface TestDataXLS
	{
		String fileName();
		String sheetVersion();
		//int sheetNumber();
		String sheetName();
	}
	
	/**
	 * DataProvider to each set of Excel blocks, against each comma separated browsers list
	 * @author SaiGnapika
	 //* @param testMethod
	 * @return Object[][]
	 */
	@SuppressWarnings("unchecked")
	//@DataProvider(name = "browserXlsByCol", parallel = true)
	//public Object[][] XLSDataProviderByCol(Method method) {
		//Array of browsers to parameterize test with
	//	Object[][] browsersParameters = Data.createDataProviderFromDelimitedString(SystemPropertyUtil.getBrowsers(), ",");
		//List of parameters to weave in with browsers
		//List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		///get annotation from test method
	//	TestDataXLS testData = method.getAnnotation(TestDataXLS.class);
		
		//check for annotation for filename
	//	if(testData == null ||  testData.fileName() == null || testData.fileName().length() == 0)
	//	{
	//		throw new SkipException(method.getName() + " : TEST REQUIRES DATA FILE TO BE DEFINED");
	//	}
		
		//sFileName = testData.fileName();
		
	/*	
		
		Object testObject = getXLSTestDataByCol(testData.fileName(), testData.sheetVersion(), testData.sheetName()).get("test");
		
		//if there are more than one rows / iterations in XLS
		if (testObject instanceof String[][]) {
			String[][] obj = (String[][]) testObject;
			List<Map<String, String>> table = new ArrayList<Map<String, String>>();	
			HashMap<String, String> row = null;
			System.out.println();
			for(int i=1; i<obj.length; i++){
			     // System.out.print(obj[i][0] + ": ");
				row = new HashMap<String, String>();
			      for (int j = 0; j < obj[i].length; j++) {
			    		  row.put(obj[0][j], obj[i][j]);
			      }
			      System.out.println();	
			      table.add(row);
			    }
			Map<String, Object> xlsMap = new HashMap<String, Object>();
			xlsMap.put("xlsMap", table);

			dataList.add((Map<String, Object>) xlsMap);
		} else if(testObject instanceof List) {

			Map<String, Object> xlsMap = new HashMap<String, Object>();
			xlsMap.put("xlsMap", testObject);
	
			dataList.add((Map<String, Object>) xlsMap);
		}
		//something is wrong with the file
		else {
			throw new SkipException(method.getName() + " : TEST FORMAT IS INCORRECT");
		}
		
		//add sehelper
		Object[][] returnDataWithSeHelper  = weaveInSeHelpers(browsersParameters);
		
		//run each set of test parameters in each browser
		Object[][] returnData  = weaveInParameters(returnDataWithSeHelper, dataList);
		
		return returnData;
	}
	
	*/
	/**
	 * Description: getXLSTestData to get the parsed excel data to place it in map
	 * @param filename
	 * @param sheetVersion
	 * @param sheetName
	 * @return
	 */
	public Map<String, Object> getXLSTestDataByCol(String filename, String sheetVersion, String sheetName){
		Map<String, Object> map = new HashMap<String, Object>();
		XlsData xlsdata = new XlsData();
		String filenam =null;
		if(sheetVersion.equalsIgnoreCase("new")){
			List<Map<String, String>> testTable = null;
			try{
				 // filenam = "../../resources/smoke_test_data/" + filename;
				filenam = System.getProperty("user.dir") + filename;
		          testTable = xlsdata.parseXLSXSheetByCol(filenam, sheetName);
			}catch(Exception e){				 
				 //filenam = ".//resources//smoke_test_data///" + filename;
				 filenam = System.getProperty("user.dir") + filename;
		          testTable = xlsdata.parseXLSXSheetByCol(filenam, sheetName);
			}
			map.put("test", testTable);
		}			
		sFileName = filenam;		
		return map;
	}
	
	
	
	/**
	 * DataProvider to each set of Excel blocks, against each comma separated browsers list
	 * @author saiGnapika
	 //* @param testMethod
	 * @return Object[][]
	 */
	@SuppressWarnings("unchecked")
	@DataProvider(name = "browserXlsByRow", parallel = true)
	public Object[][] XLSDataProviderByRow(Method method) {
		//Array of browsers to parameterize test with
		Object[][] browsersParameters = Data.createDataProviderFromDelimitedString(SystemPropertyUtil.getBrowsers(), ",");
		//List of parameters to weave in with browsers
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		//get annotation from test method
		TestDataXLS testData = method.getAnnotation(TestDataXLS.class);
		
		//check for annotation for filename
		if(testData == null ||  testData.fileName() == null || testData.fileName().length() == 0)
		{
			throw new SkipException(method.getName() + " : TEST REQUIRES DATA FILE TO BE DEFINED");
		}
		
		//sFileName = testData.fileName();
		
		
		
		Object testObject = getXLSTestDataByRow(testData.fileName(), testData.sheetVersion(), testData.sheetName()).get("test");
		
		//if there are more than one rows / iterations in XLS
		if (testObject instanceof String[][]) {
			String[][] obj = (String[][]) testObject;
			List<Map<String, String>> table = new ArrayList<Map<String, String>>();	
			HashMap<String, String> row = null;
			System.out.println();
			for(int i=1; i<obj.length; i++){
			     // System.out.print(obj[i][0] + ": ");
				row = new HashMap<String, String>();
			      for (int j = 0; j < obj[i].length; j++) {
			    		  row.put(obj[0][j], obj[i][j]);
			      }
			      System.out.println();	
			      table.add(row);
			    }
			Map<String, Object> xlsMap = new HashMap<String, Object>();
			xlsMap.put("xlsMap", table);

			dataList.add((Map<String, Object>) xlsMap);
		} else if(testObject instanceof List) {

			Map<String, Object> xlsMap = new HashMap<String, Object>();
			xlsMap.put("xlsMap", testObject);
	
			dataList.add((Map<String, Object>) xlsMap);
		}
		//something is wrong with the file
		else {
			throw new SkipException(method.getName() + " : TEST FORMAT IS INCORRECT");
		}
		
		//add sehelper
		Object[][] returnDataWithSeHelper  = weaveInSeHelpers(browsersParameters);
		
		//run each set of test parameters in each browser
		Object[][] returnData  = weaveInParameters(returnDataWithSeHelper, dataList);
		
		return returnData;
	}
	
	/**
	 * Description: getXLSTestData to get the parsed excel data to place it in map
	 * @param filename
	 * @param sheetVersion
	 * @param sheetName
	 * @return
	 */
	public Map<String, Object> getXLSTestDataByRow(String filename, String sheetVersion, String sheetName){
		Map<String, Object> map = new HashMap<String, Object>();
		XlsData xlsdata = new XlsData();
		String filenam = null;
		if(sheetVersion.equalsIgnoreCase("new")){
			List<Map<String, String>> testTable = null;
			try{
				  filenam = "../../resources/smoke_test_data/" + filename;
		          testTable = xlsdata.parseXLSXSheetTypeByRow(filenam, sheetName);
			}catch(Exception e){				 
				 filenam = ".//resources//smoke_test_data///" + filename;
		          testTable = xlsdata.parseXLSXSheetTypeByRow(filenam, sheetName);
			}
			map.put("test", testTable);
		}			
		sFileName = filenam;		
		return map;
	}
	
	
	
	
	
	
}
