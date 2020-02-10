package com.test.automation.common.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.test.automation.common.SystemPropertyUtil;
import com.test.automation.common.framework.Util;

public class ExcelWriter {
	
	private static Workbook workbook = new XSSFWorkbook();
	private static Sheet sheet = workbook.createSheet("Saved Data");

	public void writeSavedData(String testCaseNumber, LinkedHashMap<String, String> savedData) {
		
		//Sheet sheet = workbook.createSheet("Saved Data");
		Row headerRow;
		
		// add column names if they have not been created yet
		if (sheet.getRow(0) == null) {
			
			headerRow = sheet.createRow(0);
			Cell c = headerRow.createCell(0);
			c.setCellValue("TestCaseNumber"); // first cell in row is test case number
			
			int columnNum = 1;
			Set<String> keys = savedData.keySet();
			for (String k : keys) {
				
				// Set column name (key)
				Cell headerCell = headerRow.createCell(columnNum);
				headerCell.setCellValue(k);
				
				columnNum++;
			}
		}
		
		// Create new row to store values
		Row valueRow = sheet.createRow(sheet.getLastRowNum() + 1);
		Cell c = valueRow.createCell(0);
		c.setCellValue(testCaseNumber); // first cell in row is test case number
		
		int columnNum = 1;
		Set<String> keys = savedData.keySet();
		for (String k : keys) {
			
			// Set value cell (element value)
			Cell valueCell = valueRow.createCell(columnNum);
			valueCell.setCellValue(savedData.get(k));
			
			columnNum++;
		}
	}

	public static void saveFile() {
		
		// create the directory if it does not already exist
	    File directory = new File (SystemPropertyUtil.getSavedDataPath());
	    if (!directory.exists()){
	        directory.mkdir();
	    }

	    // save the excel file to the saves directory
		try (FileOutputStream outputStream = new FileOutputStream("" + SystemPropertyUtil.getSavedDataPath() + 
				"Saved_Data_" + Util.getCurrentDate() + "_" + Util.getCurrentTime() + ".xlsx")) {
			
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (IOException e) {
			System.out.println("Failed to save data.");
		}
	}
}
