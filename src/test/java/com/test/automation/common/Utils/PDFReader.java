package com.test.automation.common.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import com.test.automation.common.SeHelper;

public class PDFReader {
	
	WebDriver driver;
	String filepath = null;
	
	public PDFReader(SeHelper se) {
		driver = se.driver();	
	}
	
	public PDFReader(SeHelper se, String filepath) {
		this.driver = se.driver();
		this.filepath = filepath;
	}
		
	 // To verify PDF content in the pdf document
	
public boolean verifyPDFInBrowser(String reqTextInPDF) {
		
		boolean flag = false;
		
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;

		String[] windowsArray = (String[]) driver.getWindowHandles().toArray();
		driver.switchTo().window(windowsArray[1]);
		
		try {
			URL url = new URL(driver.getCurrentUrl());
			BufferedInputStream file = new BufferedInputStream(url.openStream());
			PDFParser parser = new PDFParser((RandomAccessRead) file);
			
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(1);
			
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (MalformedURLException e2) {
			System.err.println("URL string could not be parsed "+e2.getMessage());
		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser. " + e.getMessage());
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
			driver.close();
			if (!driver.getWindowHandle().contentEquals(windowsArray[0]))
			driver.switchTo().window(windowsArray[0]);
		}
		
		System.out.println("+++++++++++++++++");
		System.out.println(parsedText);
		System.out.println("+++++++++++++++++");

		if(parsedText.contains(reqTextInPDF)) {
			flag=true;
		}
		
		return flag;
	}

/*	
	// To verify pdf in the URL 
	
	public void verifyPDFInURL() {
		driver.get("http://www.princexml.com/samples/");
		driver.findElement(By.linkText("PDF flyer")).click();
		String getURL = driver.getCurrentUrl();
		Assert.assertTrue(getURL.contains(".pdf"));
	}
*/
	
	public boolean verifyPDFContent(String reqTextInPDF) {
		if (filepath == null) {
			verifyPDFInBrowser(reqTextInPDF);
			return true;
		}
		return verifyPDFContent(filepath, reqTextInPDF);
	}
	
	public boolean verifyPDFContent(String fileName, String reqTextInPDF) {
		
		boolean flag = false;
		
		String parsedText = null;

		try {
			if (!fileName.contains(".pdf"))
				fileName = fileName.concat(".pdf");
			if (fileName.charAt(1) != ':') {
				String home = System.getProperty("user.home");
				fileName = (home + "\\Downloads\\" + fileName);
			}
			
			File file = new File(fileName);
			PDDocument document = PDDocument.load(file);
			PDFTextStripper strip = new PDFTextStripper();
			strip.setEndPage(2);
			parsedText = strip.getText(document);
//			PDFParser parser = new PDFParser(null, parsedText);
			document.close();

		} catch (IOException e) {
			System.err.println("Unable to parse PDF");
			e.printStackTrace();
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