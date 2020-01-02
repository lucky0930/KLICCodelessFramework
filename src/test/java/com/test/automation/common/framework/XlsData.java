package com.test.automation.common.framework;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsData {
	private Workbook workbook;
	private XSSFWorkbook workbk;

	public XlsData() {
	}

	/**
	 * Opens an xls workbook
	 * 
	 * @param filename
	 */
	public Workbook openWorkbook(String filename) {
		try {
			// System.out.println(new URI(filename));
			File f = new File(filename);
			// InputStream f = new FileInputStream(filename);
			return workbook = Workbook.getWorkbook(f);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// System.out.println("The file not found: " + e.getMessage());
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Opens an xls workbook
	 * 
	 * @param stream
	 */
	public Workbook openWorkbook(InputStream stream) {
		try {
			// System.out.println(new URI(filename));
			// File f = new File(filename);
			// InputStream f = new FileInputStream(filename);
			return workbook = Workbook.getWorkbook(stream);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// System.out.println("The file not found: " + e.getMessage());
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// public String[][] parseXLSSheet(String filename, int sheetNumber)
	public String[][] parseXLSSheet(String filename, String sheetName) {
		openWorkbook(filename);
		// Sheet sheet = workbook.getSheet(sheetNumber);
		Sheet sheet = workbook.getSheet(sheetName);
		int rows = sheet.getRows();
		int cols = sheet.getColumns();
		String[][] data = new String[rows][cols];
		// for each row
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				// We are commenting this line to get right row count
				// and ignoring null row values
				// data[row][col] = sheet.getCell(col,row).getContents();
				if (sheet.getCell(col, row).getContents().trim().length() >= 1
						&& sheet.getCell(col, row).getContents() != null) {
					data[row][col] = sheet.getCell(col, row).getContents();
				}
			}
		}

		closeWorkbook();

		return data;
	}

	/**
	 * 
	 * @param filename
	 * @param sheetName
	 * @return
	 */
	public List<Map<String, String>> getTestEnvironmentDetails(String sRunApplicationType) {
		try {
			int ZERO = 0;

			// Columns defined in ConfigurationFile.xls in EnvData Sheet
			int ENV_APPLICATION_TYPE = 1;
			int ENV_TEST_ENVIRONMENT = 2;
			int ENV_EXECUTION_STATUS = 3;
			int ENV_LOGIN_ID = 4;
			int ENV_PASSWORD = 5;
			int ENV_APP_ADDRESS = 6;

			// Columns defined in ConfigurationFile.xls in AppLinks Sheet
			int APP_TEST_ENVIRONMENT = 0;
			int APP_APP_ADDRESS = 1;

			String sExpString = "YES";

			List<Map<String, String>> table = new ArrayList<Map<String, String>>();
			HashMap<String, String> rowMap = null;
			List<String> firstRow = new ArrayList<String>();

			XlsData xlsdata = new XlsData();
			String[][] envdata;
			String[][] appdata = null;
			String[][] retdata = null;
			retdata = new String[1][7];

			String sFileName = "C:\\navymutual\\trunk\\resources\\ConfigurationFile.xls";
			String sEnvSheetName = "EnvData";
			String sAppSheetName = "AppLinks";
			envdata = parseXLSSheet(sFileName, sEnvSheetName);
			appdata = parseXLSSheet(sFileName, sAppSheetName);

			if (envdata.length != appdata.length) {
				System.out.println(
						"Please define Test Config details all environments between EnvData and AppLinks data sheets. The number of rows should be the same.");
				return (null);
			}
			int iFound = 0;
			int iDataRow = 0;
			for (int row = 1; row < envdata.length; row++) {
				if (envdata[row][ENV_APPLICATION_TYPE].equalsIgnoreCase(sRunApplicationType)) {
					if (envdata[row][ENV_EXECUTION_STATUS].equalsIgnoreCase(sExpString)) {
						iFound++;
						iDataRow = row;
					}
				}
			}

			if (iFound == ZERO) {
				System.out.println("No Environment is configured for " + sRunApplicationType
						+ " execution in Test Configuration file.");
				return (null);
			} else if (iFound > 1) {
				System.out.println("Please set 'YES' for only one Test environment...");
				return (null);
			} else {
				if (!envdata[iDataRow][ENV_TEST_ENVIRONMENT]
						.equalsIgnoreCase(appdata[iDataRow][APP_TEST_ENVIRONMENT])) {
					System.out.println(
							"Environment Name does not match. Please maintain the same order of environments between EnvData and AppLinks Sheets...");
					return (null);
				} else {
					rowMap = new HashMap<String, String>();
					rowMap.put(envdata[ZERO][ENV_TEST_ENVIRONMENT], envdata[iDataRow][ENV_TEST_ENVIRONMENT]);
					rowMap.put(envdata[ZERO][ENV_EXECUTION_STATUS], envdata[iDataRow][ENV_EXECUTION_STATUS]);
					rowMap.put(envdata[ZERO][ENV_LOGIN_ID], envdata[iDataRow][ENV_LOGIN_ID]);
					rowMap.put(envdata[ZERO][ENV_PASSWORD], envdata[iDataRow][ENV_PASSWORD]);
					rowMap.put(appdata[ZERO][APP_APP_ADDRESS], appdata[iDataRow][APP_APP_ADDRESS]);
					table.add(rowMap);
					return table;

				}
			}
		} catch (Exception e) {
			System.out.println("getTestEnvironmentDetails failed...");
			return (null);
			// TODO Auto-generated catch block

		}
	}

	// public String[][] parseXLSSheet(InputStream stream, int sheetNumber)
	public String[][] parseXLSSheet(InputStream stream, String sheetName) {
		openWorkbook(stream);
		// Sheet sheet = workbook.getSheet(sheetNumber);
		Sheet sheet = workbook.getSheet(sheetName);
		int rows = sheet.getRows();
		int cols = sheet.getColumns();
		String[][] data = new String[rows][cols];
		// for each row

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				// We are commenting this line to get right row count
				// and ignoring null row values
				// data[row][col] = sheet.getCell(col,row).getContents();
				if (sheet.getCell(col, row).getContents().trim().length() >= 1
						&& sheet.getCell(col, row).getContents() != null) {
					data[row][col] = sheet.getCell(col, row).getContents();
				}
			}
		}

		closeWorkbook();

		return data;
	}

	/**
	 * Closes the workbook
	 */
	public void closeWorkbook() {
		workbook.close();
	}

	/**
	 * Opens an xlsx workbook
	 * 
	 * @param stream
	 */
	public XSSFWorkbook openWorkbk(String filename) {
		try {
			//System.out.println(new URI(filename));
			
			File f = new File(filename);
			FileInputStream file = new FileInputStream(f);
			// Get the workbook instance for XLS file
			workbk = new XSSFWorkbook(file);
			System.out.println("workbk : "+workbk);
			return workbk;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// System.out.println("The file not found: " + e.getMessage());
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Map<String, String>> parseXLSXSheetByCol(String filename, String sheetName) {
//	XSSFWorkbook wk = 	openWorkbk(filename);
		openWorkbk(filename);
		XSSFSheet sheet = workbk.getSheet(sheetName);
		XSSFRow row = null;
		List<Map<String, String>> table = new ArrayList<Map<String, String>>();
		HashMap<String, String> rowMap = null;
		List<String> firstRow = new ArrayList<String>();

		int i = 0;
		for (int colnum = sheet.getRow(i).getFirstCellNum() + 1; colnum < sheet.getRow(i).getLastCellNum(); colnum++) {
			if (sheet.getRow(colnum) != null) {
				rowMap = new HashMap<String, String>();
				int j = 0;
				for (int rownum = sheet.getFirstRowNum() + 1; rownum <= sheet.getLastRowNum(); rownum++) {
					row = sheet.getRow(rownum);
					if (colnum == row.getFirstCellNum() + 1) {
						if (sheet.getRow(rownum).getCell(colnum).getStringCellValue() != null
								&& sheet.getRow(rownum).getCell(colnum).getStringCellValue().length() != 0) {
							firstRow.add(sheet.getRow(rownum).getCell(colnum).getStringCellValue());
						}
					} else {
						try {
							if (sheet.getRow(rownum).getCell(colnum).getStringCellValue() != null
									&& sheet.getRow(rownum).getCell(colnum).getStringCellValue().length() > 0) {
								rowMap.put(firstRow.get(j), sheet.getRow(rownum).getCell(colnum).getStringCellValue());
								j++;
							}
						} catch (Exception e) {
						}
					}
				}
			}
			if (colnum != sheet.getRow(i).getFirstCellNum() + 1)
				table.add(rowMap);
			i++;
		}
		System.out.println(table.size());
		return table;
	}
	
	public List<Map<String, String>> parseXLSXSheetTypeByRow(String filename, String sheetName) {
		openWorkbk(filename);
		// System.out.println("Inside parseXLSXSheet");
		XSSFSheet sheet = workbk.getSheet(sheetName);
		XSSFRow row = null;
		XSSFCell cell = null;
		List<Map<String, String>> table = new ArrayList<Map<String, String>>();
		HashMap<String, String> rowMap = null;
		
		List<String> firstRow = new ArrayList<String>();

		int i = 0;
		for (int rownum = sheet.getFirstRowNum(); rownum <= sheet.getLastRowNum(); rownum++) {
			row = sheet.getRow(rownum);
			if (row != null) {
				rowMap = new HashMap<String, String>();
				int j = 0;
				for (int cellnum = row.getFirstCellNum(); cellnum < row.getLastCellNum(); cellnum++) {
					cell = row.getCell(cellnum);
					/*
					 * if(cell.getStringCellValue() == null ||
					 * cell.getRawValue() == null){ continue; }else{
					 */
					if (rownum == sheet.getFirstRowNum()) {
						firstRow.add(cell.getStringCellValue());
					} else {
						try {
							if (cell.getStringCellValue().length() > 0) {
								rowMap.put(firstRow.get(j), cell.getStringCellValue());
							}
						} catch (Exception e) {

						}
					}
					j++;
				}
			}
			if (rownum != sheet.getFirstRowNum())
				table.add(rowMap);

			i++;
		}

		return table;
	}

}
