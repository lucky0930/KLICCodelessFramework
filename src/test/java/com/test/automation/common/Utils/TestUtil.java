package com.test.automation.common.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.secura.ap.pages.StartupPage;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.framework.XlsData;

import jxl.Workbook;

public class TestUtil {

	static XlsData objXlsData = new XlsData();
	private static org.apache.poi.ss.usermodel.Workbook workbook;
	static String TESTDATA_SHEET_PATH = "C:\\Users\\VMQApractice\\Desktop\\VAMQAPractice\\VM_Framework_Base\\VM_Framework_Base\\resources\\test_data\\VM_TestData_Sample1.xlsx";

	LinkedHashMap<String, LinkedHashMap<String, String>> tableData = new LinkedHashMap<String, LinkedHashMap<String, String>>();
	List<String> sheetCollection = new ArrayList<String>();
	WebDriver driver;

	public void ExecuteTest(String TestCaseNumber) {

		tableData = ExcelReader(TestCaseNumber, TESTDATA_SHEET_PATH);
		for (int i = 0; i < tableData.size(); i++) {
			String sheetName = sheetCollection.get(i);
			LinkedHashMap<String, String> actualData = tableData.get(sheetCollection.get(i));

			actualData.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " => " + entry.getValue());

				if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

				} else {
					// WebElement element = pageProcess(sheetName, entry.getKey());
					// FillElement(element, entry.getValue());
				}
			});
		}
	}

	public void ExecuteTest(String TestCaseNumber, SeHelper se) {
		se.browser().get(SystemPropertyUtil.getBaseStoreUrl());
		tableData = ExcelReader(TestCaseNumber, TESTDATA_SHEET_PATH);
		for (int i = 0; i < tableData.size(); i++) {
			String sheetName = sheetCollection.get(i);
			LinkedHashMap<String, String> actualData = tableData.get(sheetCollection.get(i));

			actualData.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " => " + entry.getValue());

				if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

				} else {
					WebElement element = pageProcess(se, sheetName, entry.getKey());
					FillElement(element, entry.getValue());
				}
			});
		}
	}

	private void FillElement(WebElement element, String value) {

		if (element != null && (element).isDisplayed() && element.isEnabled()) {

			if ("input".equals(element.getTagName())) {
				element.sendKeys(value);
			} else {
				// new Actions(se.driver()).moveToElement(element).perform();
			}

			if ("button".equals(element.getTagName())) {
				element.click();
			}
		}

	}

	private WebElement pageProcess(SeHelper se, String ClassSheet, String key) {
		// String ClassName = key.toString();
		Class objClass = null;
		WebElement element = null;
		try {
			objClass = Class.forName("com.test.automation.repository." + ClassSheet);

			// var classPage = TestPageFactory.initElements(driver, objClass);

			// driver = CallDriver(objClass);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		Constructor constuctor = null;
		try {
			constuctor = objClass.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {

			e.printStackTrace();
		}
		try {
			Object obj = constuctor.newInstance();

			try {
				Method callMethod = obj.getClass().getMethod(key, SeHelper.class);
				// Method callMethod = obj.getClass().getDeclaredMethod(key);
				callMethod.setAccessible(true);

				element = (WebElement) callMethod.invoke(obj, se);
				// element = (WebElement) callMethod.invoke(obj);

				if (element != null) {
					System.out.println("Success");
				}
			} catch (NoSuchMethodException e) {

				e.printStackTrace();
			} catch (SecurityException e) {

				e.printStackTrace();
			}
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.getCause().printStackTrace();
			e.printStackTrace();
		}
		return element;

	}

	private WebDriver CallDriver(Class objClass) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\VMQApractice\\Desktop\\VAMQAPractice\\VM_Framework_Base\\VM_Framework_Base\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://vm-bd-idsweb-common-wapp.azurewebsites.net");

		return driver;
	}

	private LinkedHashMap<String, LinkedHashMap<String, String>> ExcelReader(String testCaseNumber,
			String TESTDATA_SHEET_PATH) {

		workbook = openworkbook(TESTDATA_SHEET_PATH);

		int numberOfSheets = workbook.getNumberOfSheets();
		int flowIndex = 1;

		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			String sheeTname = sheet.getSheetName();

			for (int row = 1; row <= sheet.getLastRowNum(); row++) {
				String TCNumber = CheckNumeric(sheet.getRow(row).getCell(0));
				String flow = CheckNumeric(sheet.getRow(row).getCell(1));

				if (TCNumber.equals(testCaseNumber)) {

					GetTestData(sheet, row);
//					LinkedHashMap<String, String> duplicateData = data;
//					if (!tableData.containsValue(data)) {
//						tableData.put(sheeTname, data);
//					}

					flowIndex++;
					sheetCollection.add(sheeTname);
				}
			}
		}

		// SordByFlow(tableData);

		return tableData;
	}

	private void SordByFlow(LinkedHashMap<String, LinkedHashMap<String, String>> tableData2) {
		for (Entry<String, LinkedHashMap<String, String>> entry : tableData2.entrySet()) {
			String key = entry.getKey();

		}
	}

	private LinkedHashMap<String, LinkedHashMap<String, String>> GetTestData(Sheet sheet, int rowNum) {

		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
			String colKey = CheckNumeric(sheet.getRow(0).getCell(k));
			String rowValue = CheckNumeric(sheet.getRow(rowNum).getCell(k));

			data.put(colKey, rowValue);

		}

		return UpdateTableData(sheet.getSheetName(), data);
	}

	private LinkedHashMap<String, LinkedHashMap<String, String>> UpdateTableData(String sheetName,
			LinkedHashMap<String, String> data2) {
		LinkedHashMap<String, LinkedHashMap<String, String>> myData = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		if (!tableData.containsValue(data2)) {
			tableData.put(sheetName, data2);
		}

		// tableData = myData;
		// tableData.add(myData);
		return tableData;
	}

	private String CheckNumeric(Cell cell) {
		String cellValue = null;
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
		} else {
			cellValue = cell.getStringCellValue();
		}
		return cellValue;
	}

	private org.apache.poi.ss.usermodel.Workbook openworkbook(String filepath2) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workbook;

	}

}