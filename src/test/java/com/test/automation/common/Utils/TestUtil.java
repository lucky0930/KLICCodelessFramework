package com.test.automation.common.Utils;

import java.util.LinkedHashMap;
import java.util.List;

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
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedHashMap<String, LinkedHashMap<String, String>> tableData = excelReader.GetTestData(TestCaseNumber,
				SystemPropertyUtil.getTestDataSheetPath());
		// sheetCollection = excelReader.GetSheetCollection();
		for (int i = 0; i < tableData.size(); i++) {
			String sheetName = sheetCollection.get(i);
			LinkedHashMap<String, String> actualData = tableData.get(sheetCollection.get(i));

			actualData.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " => " + entry.getValue());

				if (entry.getKey().equalsIgnoreCase("TestCaseNumber") || entry.getKey().equalsIgnoreCase("Flow")) {

				} else {
					if (entry.getValue() != null)
						PageProcess.findElement(se, sheetName, entry.getKey(), entry.getValue());
					// FillElement(element, entry.getValue());
				}
			});
		}
	}

}