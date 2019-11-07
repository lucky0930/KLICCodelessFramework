package com.test.automation.common.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.framework.XlsData;

public class TestUtil {

	static XlsData objXlsData = new XlsData();

	static String TESTDATA_SHEET_PATH = SystemPropertyUtil.getTestDataSheetPath();

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
				SystemPropertyUtil.getTestDataSheetPath());

		List<String> sheetCollection1 = new ArrayList<String>();
		List<String> actualSheetCollection = new ArrayList<String>();

		tableData.entrySet().forEach(entry -> {
			String sheetName = entry.getKey();

			actualSheetCollection.add(entry.getKey());
			if (sheetName.contains("$")) {
				sheetName = sheetName.split(Pattern.quote("$"))[0];
			}
			sheetCollection1.add(sheetName);
		});

		for (int i = 0; i < tableData.size(); i++) {
			LinkedHashMap<String, String> actualData = tableData.get(actualSheetCollection.get(i));
			ExecuteTestProcess(se, sheetCollection1.get(i), actualData);
		}
	}

	private void ExecuteTestProcess(SeHelper se, String sheetName, LinkedHashMap<String, String> actualData) {

		actualData.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " => " + entry.getValue());

			if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

			} else {
				if (entry.getValue() != null)
					PageProcess.findElement(se, sheetName, entry.getKey(), entry.getValue());
			}
		});
	}
}