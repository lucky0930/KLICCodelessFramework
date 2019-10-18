package com.test.automation.common.framework;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Data {
	private Workbook workbook;	
	private static File myKeyFile;

    //
    private final ConcurrentMap<String, Object> sessionData = new ConcurrentHashMap<String, Object>();

	@SuppressWarnings("unused")
	public Data() { }

    /**
     * Adds a named object to the current session.
     *
     * @param name
     * @param object
     */
    public void addToSession(String name, Object object) {
        sessionData.put(name, object);
    }

    /**
     * Gets a String object from the current session.
     *
     * @param name Name of the String to return
     * @return Null if it doesn't exist, or the output of toString() on the object
     */
    public String sessionString(String name) {
        Object theObject = sessionObject(name);
        return theObject != null ? theObject.toString() : null;
    }

    /**
     * Returns the named session object.
     *
     * @param name Name of the object
     * @return The Object, or null if it doesn't exist
     */
    public Object sessionObject(String name) {
        return sessionData.get(name);
    }
	
	
	
	/**
	 * Opens an xls workbook
	 * @param filename
	 */
	public Workbook openWorkbook(String filename)
	{
		try {
			//System.out.println(new URI(filename));
			File f = new File(new URI(filename));
			//InputStream f = new FileInputStream(filename);
			return workbook = Workbook.getWorkbook(f);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Opens an xls workbook
	 * @param stream
	 */
	public Workbook openWorkbook(InputStream stream)
	{
		try {
			return workbook = Workbook.getWorkbook(stream);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String[][] parseXLSSheet(String filename, int sheetNumber)
	{
		openWorkbook(filename);
		Sheet sheet = workbook.getSheet(sheetNumber);
		int rows = sheet.getRows();
		int cols = sheet.getColumns();
		String[][] data = new String[rows][cols];
		//for each row
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) 
			{
				data[row][col] = sheet.getCell(col,row).getContents();
			}
		}
		
		closeWorkbook();
		
		return data;
	}
	
	public String[][] parseXLSSheet(InputStream stream, int sheetNumber)
	{
		openWorkbook(stream);
		Sheet sheet = workbook.getSheet(sheetNumber);
		int rows = sheet.getRows();
		int cols = sheet.getColumns();
		String[][] data = new String[rows][cols];
		//for each row
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) 
			{
				data[row][col] = sheet.getCell(col,row).getContents();
			}
		}
		
		closeWorkbook();
		
		return data;
	}
	/**
	 * Closes the workbook
	 */
	public void closeWorkbook(){
		workbook.close();
	}
	
	
	/**
	 * creates an object array of browser objects from a delimited list
	 * @param delimitedString
	 * @param delimiter
	 * @return
	 */
    public static Object[][] createDataProviderFromDelimitedString(String delimitedString, String delimiter) {
        List<List<Object>> returnData = new ArrayList<List<Object>>();
        for (String cellContents : delimitedString.split(delimiter)) {
            List<Object> column = new ArrayList<Object>();
            if (cellContents.equals("Random")) {
                int chosenOption = com.test.automation.common.framework.Util.randomNum(0, 4);
                if (chosenOption == 0)
                    column.add(Browser.Browsers.GridInternetExplorer);
                if (chosenOption == 1)
                    //column.add(Browser.Browsers.GridFirefox);
                if (chosenOption == 2)
                    column.add(Browser.Browsers.GridChrome);               
               
            } else {
                try {
                    column.add(Browser.Browsers.valueOf(cellContents));
                } catch (IllegalArgumentException e) {
                    System.out.println("unrecognized browser in pom.xml : " + cellContents);
                }
            }

            returnData.add(column);
        }

        Object[][] convertedReturnData = new Object[returnData.size()][];
        for (int row = 0; row < returnData.size(); row++) {
            convertedReturnData[row] = returnData.get(row).toArray();
        }
        return convertedReturnData;
    }	

}

