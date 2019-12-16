package com.test.automation.common.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.jboss.netty.handler.codec.http.HttpHeaders.Values;

public class ExcelReader {

	private static final boolean String = false;

	private org.apache.poi.ss.usermodel.Workbook workbook;

	protected List<String> sheetCollection = new ArrayList<String>();
	int indexflow;

	protected LinkedHashMap<String, LinkedHashMap<String, String>> GetTestData(String testCaseNumber,
			String TESTDATA_SHEET_PATH) {
		LinkedHashMap<String, LinkedHashMap<String, String>> tableData = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		workbook = openworkbook(TESTDATA_SHEET_PATH);

		int numberOfSheets = workbook.getNumberOfSheets();

		for (int i = 0; i < numberOfSheets; i++) {
			indexflow = 0;
			Sheet sheet = workbook.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			int StartrowNum = 1;
			if (!testCaseNumber.equalsIgnoreCase("Xpath")) {
				StartrowNum = StartrowNum + 1;
			}

			for (int row = StartrowNum; row <= sheet.getLastRowNum(); row++) {
				String TCNumber = CheckNumeric(sheet.getRow(row).getCell(0));
				String flow = CheckNumeric(sheet.getRow(row).getCell(1));

				if (TCNumber != null) {
					if (TCNumber.equals(testCaseNumber)) {

						GetTestData(tableData, sheet, row);

						sheetCollection.add(sheetName);
						indexflow++;
					}

				}
			}

		}

		if (testCaseNumber.equalsIgnoreCase("Xpath"))
			return tableData;
		else
			return SortByFlow(tableData);

		// If you want to run in parallel, comment out SortByFlow(tableData) and
		// uncomment return tableData.

	}

	private LinkedHashMap<String, LinkedHashMap<String, String>> SortByFlow(
			LinkedHashMap<String, LinkedHashMap<String, String>> tableData2) {

		LinkedHashMap<String, LinkedHashMap<String, String>> tableDataSorted = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		int flowCount = 1;

		while (tableDataSorted.size() != tableData2.size()) {
			for (Entry<String, LinkedHashMap<String, String>> entry : tableData2.entrySet()) {
				String key = entry.getKey();
				String currentFlow = entry.getValue().get("Flow");

				if (Integer.parseInt(currentFlow) == flowCount) {
					tableDataSorted.put(key, entry.getValue());
					break; // Remove this break if there are duplicate flow numbers possible.
				}
			}

			flowCount++;
		}

		return tableDataSorted;
	}

	private LinkedHashMap<String, LinkedHashMap<String, String>> GetTestData(
			LinkedHashMap<String, LinkedHashMap<String, String>> tableData, Sheet sheet, int rowNum) {

		LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
		for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
			String colKey = CheckNumeric(sheet.getRow(0).getCell(k));
			String rowValue = CheckNumeric(sheet.getRow(rowNum).getCell(k));

			data.put(colKey, rowValue);

		}

		return UpdateTableData(tableData, sheet.getSheetName(), data);
	}

	private LinkedHashMap<String, LinkedHashMap<String, String>> UpdateTableData(
			LinkedHashMap<String, LinkedHashMap<String, String>> tableData, String sheetName,
			LinkedHashMap<String, String> data2) {
		LinkedHashMap<String, LinkedHashMap<String, String>> myData = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		if (!tableData.containsValue(data2)) {
			if (indexflow > 0) {
				sheetName = sheetName + "$" + indexflow;
			}
			tableData.put(sheetName, data2);
		}

		return tableData;
	}

	private String CheckNumeric(Cell cell) {
		String cellValue = null;
		if (!(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
			} else {
				cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}

	private org.apache.poi.ss.usermodel.Workbook openworkbook(String filepath2) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(filepath2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;

	}

	public List<String> GetSheetCollection() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> GetTestRunnerData(String TESTDATA_SHEET_PATH) {
		List<String> lstOfTestCaseNumber = new ArrayList<String>();

		workbook = openworkbook(TESTDATA_SHEET_PATH);
		Sheet sheet = workbook.getSheetAt(0);
		List<String> columnList = ColumnTitles(sheet);

		// String typeOfExecution = CheckNumeric(sheet.getRow(1).getCell(2));

		String typeOfExecution = CheckNumeric(sheet.getRow(1).getCell(columnList.indexOf("Execution")));
		
		if (typeOfExecution.contains("Groups")) {
			String[] split = typeOfExecution.split("=");
			String typeOfGroup = split[1].trim();

			String[] values = typeOfGroup.split(",");
			List<String> lstGroups = new ArrayList<String>();

			for (int i = 0; i < values.length; i++) {
				lstGroups.add(values[i]);
			}

			for (int row = 0; row <= sheet.getLastRowNum(); row++) {
				String GroupName = CheckNumeric(sheet.getRow(row).getCell(columnList.indexOf("Group")));
				if (lstGroups.contains(GroupName)) {
					String TCNumber = CheckNumeric(sheet.getRow(row).getCell(columnList.indexOf("TestCaseNumber")));
					if (!TCNumber.isEmpty())
						lstOfTestCaseNumber.add(TCNumber);
				}
			}
		} else if (typeOfExecution.contains("TestCaseNumber")) {
			String[] split = typeOfExecution.split("=");
			String typeOfGroup = split[1].trim();

			String[] values = typeOfGroup.split(",");
			if (values[0].contains("All")) {
				int column = columnList.indexOf("TestCaseNumber");
				for (int row = 1; row <= sheet.getLastRowNum(); row++) {
					lstOfTestCaseNumber.add(CheckNumeric(sheet.getRow(row).getCell(column)));
				}
			} else {
				for (int i = 0; i < values.length; i++) {
					lstOfTestCaseNumber.add(values[i]);
				}
			}
		}
		
		return sortByPriority(lstOfTestCaseNumber, sheet);
	}

	public List<String> ColumnTitles(Sheet sheet) {

		List<String> titles = new ArrayList<String>();

		for (Cell column : sheet.getRow(0)) {
			titles.add(column.getStringCellValue());
		}

		return titles;
	}
		
	public List<String> ColumnValues(Sheet sheet, int columnIndex) {

		List<String> columnValues = new ArrayList<String>();
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
			columnValues.add(sheet.getRow(i).getCell(columnIndex).getStringCellValue());
		}

		return columnValues;
	}

	public List<String> sortByPriority(List<String> unsortedTests, Sheet sheet) {
		List<String> priorityColumn = GetColumn("Priority", sheet);
		List<String> testCaseColumn = GetColumn("TestCaseNumber", sheet);
		Integer[] priorityArray = insertionSort(stringListToIntegerArray(priorityColumn));
		List<String> sortedList = new ArrayList<String>();
		
		for (Integer test : priorityArray) {
			sortedList.add(testCaseColumn.get(priorityColumn.indexOf(test.toString())));
			priorityColumn.set(priorityColumn.indexOf(test.toString()), "-1");
		}

		return sortedList;
	}

	public List<String> GetColumn(String columnHead, Sheet sheet) {
		int index = ColumnTitles(sheet).indexOf(columnHead);
		List<String> column = new ArrayList<String>();

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			if (sheet.getRow(i).getCell(index).getCellType() == Cell.CELL_TYPE_STRING)
				column.add(sheet.getRow(i).getCell(index).getStringCellValue());
			else if (sheet.getRow(i).getCell(index).getCellType() == Cell.CELL_TYPE_NUMERIC)
			{
				int cellValue = (int)sheet.getRow(i).getCell(index).getNumericCellValue();
				column.add(java.lang.String.valueOf(cellValue));
			}
		}

		return column;
	}
	
	public Integer[] stringListToIntegerArray(List<String> list)
	{
		Integer[] array = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = Integer.valueOf(list.get(i));
		}
		return array;
	}

	public Integer[] insertionSort(Integer[] unsortedArray)
	{
		Integer[] sortArray = unsortedArray;
		
        int n = sortArray.length; 
        for (int i = 1; i < n; ++i) { 
            int key = sortArray[i]; 
            int j = i - 1; 
            
            while (j >= 0 && sortArray[j] > key) { 
                sortArray[j + 1] = sortArray[j]; 
                j = j - 1; 
            } 
            sortArray[j + 1] = key; 
        }
        return sortArray;
    } 

}
