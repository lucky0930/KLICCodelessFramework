package com.test.automation.common.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import com.test.automation.common.SeHelper;

public class PDFReader {
	
	WebDriver driver;
	
	public PDFReader(SeHelper se) {
		driver = se.driver();	
	}
	
	/**
	 * To verify PDF content in the pdf document
	 */
	public void verifyPDFTextInBrowser() {
		
		driver.get("http://www.princexml.com/samples/");
		driver.findElement(By.linkText("PDF flyer")).click();
		Assert.assertTrue(verifyPDFContent(driver.getCurrentUrl(), "Prince Cascading"));
	}

	/**
	 * To verify pdf in the URL 
	 */
	public void verifyPDFInURL() {
		driver.get("http://www.princexml.com/samples/");
		driver.findElement(By.linkText("PDF flyer")).click();
		String getURL = driver.getCurrentUrl();
		Assert.assertTrue(getURL.contains(".pdf"));
	}

	
	public boolean verifyPDFContent(String fileName, String reqTextInPDF) {
		
		boolean flag = false;
		
		String parsedText = null;

		try {
			if (!fileName.contains(".pdf"))
				fileName = fileName.concat(".pdf");
			
			String home = System.getProperty("user.home");
			File file = new File(home + "\\Downloads\\" + fileName);
			PDDocument document = PDDocument.load(file);
			PDFTextStripper strip = new PDFTextStripper();
			strip.setEndPage(2);
			parsedText = strip.getText(document);
//			PDFParser parser = new PDFParser(null, parsedText);
			document.close();

		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser. " + e.getMessage());
		}
		
		//System.out.println("+++++++++++++++++\n" + parsedText + "\n+++++++++++++++++");

		if(parsedText.contains(reqTextInPDF)) {
			flag=true;
		}
		
		return flag;
	}
	
	public void tearDown() {
		driver.quit();
	}
}