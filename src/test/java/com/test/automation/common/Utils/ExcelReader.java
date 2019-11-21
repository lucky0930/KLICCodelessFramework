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

public class ExcelReader {

	private org.apache.poi.ss.usermodel.Workbook workbook;
	LinkedHashMap<String, LinkedHashMap<String, String>> tableData = new LinkedHashMap<String, LinkedHashMap<String, String>>();
	protected List<String> sheetCollection = new ArrayList<String>();
	int indexflow;

	protected LinkedHashMap<String, LinkedHashMap<String, String>> GetTestData(String testCaseNumber,
			String TESTDATA_SHEET_PATH) {

		workbook = openworkbook(TESTDATA_SHEET_PATH);

		int numberOfSheets = workbook.getNumberOfSheets();

		for (int i = 0; i < numberOfSheets; i++) {
			indexflow = 0;
			Sheet sheet = workbook.getSheetAt(i);
			String sheeTname = sheet.getSheetName();

			for (int row = 1; row <= sheet.getLastRowNum(); row++) {
				String TCNumber = CheckNumeric(sheet.getRow(row).getCell(0));
				String flow = CheckNumeric(sheet.getRow(row).getCell(1));

				if (TCNumber != null) {
					if (TCNumber.equals(testCaseNumber)) {

						GetTestData(sheet, row);

						sheetCollection.add(sheeTname);
						indexflow++;
					}
					
				}
			}
			
		}

		// If you want to run in parallel, comment out SortByFlow(tableData) and uncomment return tableData.
		//return tableData;
		return SortByFlow(tableData);
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

}
