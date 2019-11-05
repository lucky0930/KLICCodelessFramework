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
//import com.secura.ap.pages.StartupPage;
import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.framework.XlsData;

import jxl.Workbook;

public class TestUtil {

	static XlsData objXlsData = new XlsData();

	static String TESTDATA_SHEET_PATH = "C:\\Users\\VMQApractice\\Desktop\\VAMQAPractice\\VM_Framework_Base\\VM_Framework_Base\\resources\\test_data\\VM_TestData_Sample1.xlsx";

	ExcelReader excelReader = new ExcelReader();
	List<String> sheetCollection = excelReader.sheetCollection;

	public void ExecuteTest(String TestCaseNumber) {

		LinkedHashMap<String, LinkedHashMap<String, String>> tableData = excelReader.GetTestData(TestCaseNumber,
				TESTDATA_SHEET_PATH);

		for (int i = 0; i < tableData.size(); i++) {
			String sheetName = sheetCollection.get(i);
			LinkedHashMap<String, String> actualData = tableData.get(sheetCollection.get(i));

			actualData.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " => " + entry.getValue());

				if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

				} else {

				}
			});
		}
	}

	public void ExecuteTest(String TestCaseNumber, SeHelper se) {
		se.browser().get(SystemPropertyUtil.getBaseStoreUrl());
		LinkedHashMap<String, LinkedHashMap<String, String>> tableData = excelReader.GetTestData(TestCaseNumber,
				TESTDATA_SHEET_PATH);
		// sheetCollection = excelReader.GetSheetCollection();
		for (int i = 0; i < tableData.size(); i++) {
			String sheetName = sheetCollection.get(i);
			LinkedHashMap<String, String> actualData = tableData.get(sheetCollection.get(i));

			actualData.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " => " + entry.getValue());

				if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

				} else {
					PageProcess.findElement(se, sheetName, entry.getKey(), entry.getValue());
					// FillElement(element, entry.getValue());
				}
			});
		}
	}

}