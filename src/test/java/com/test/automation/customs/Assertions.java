package com.test.automation.customs;

import com.test.automation.common.SeHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Assertions {
	private SeHelper se;

	public Assertions() {
		se = new SeHelper();
	}

	public Assertions(SeHelper seIN) {
		se = seIN;
	}

	public boolean verify(String arg) {
		if (!arg.contains(">")) {
			se.log().logSeStep("VERIFY FAILED: No assertion provided - '>' missing");
			return false;
		}

		String assertion = arg.substring(0, arg.indexOf('>'));
		String expectedValue = arg.substring(arg.indexOf('>') + 1);

		if (assertion.equals("CheckURL")) {
			if (expectedValue.equals(se.browser().getCurrentUrl())) {
				se.log().logSeStep("VERIFY CheckURL: " + expectedValue);
				se.reporter().reportVerifyPass(assertion, se.browser().getCurrentUrl(), expectedValue);
				return true;
			}
			se.log().logSeStep("VERIFY FAILED - CheckURL: URL does not match expected: " + expectedValue);
			se.reporter().reportFailCapture(assertion, se.browser().getCurrentUrl(), expectedValue, se);
			return false;
		} else {
			System.out.println("WARNING: verify() calls must include WebElement unless asserting 'CheckURL'");
			se.log().logSeStep("VERIFY FAILED: No valid assertion provided");
			return false;
		}
	}

	public boolean verify(WebElement element, String arg) // arg should be in the format: assertion>value
	{
		if (element == null) {
			return verify(arg);
		}

		boolean result = false;
		if (!arg.contains(">")) {
			se.log().logSeStep("VERIFY FAILED: No assertion provided - '>' missing");
			return false;
		}

		String assertion = arg.substring(0, arg.indexOf('>'));
		String expectedValue = arg.substring(arg.indexOf('>') + 1);
		String actualValue = new String();

		switch (assertion) {
		case "AreEqual":
			actualValue = element.getText();
			if (actualValue == null)
				actualValue = element.getAttribute("value");

			result = (expectedValue.equals(actualValue));
			break;

		case "AreNotEqual":
			actualValue = element.getText();
			if (actualValue == null)
				actualValue = element.getAttribute("value");
			result = (!expectedValue.equals(actualValue));
			break;

		case "IsVisible":
			result = element.isDisplayed();
			if (result == Boolean.parseBoolean(expectedValue)) {
				se.log().logSeStep("VERIFY " + assertion + ": " + element.getTagName() + " is visible");
				se.reporter().reportVerifyPass(element.getTagName() + " Is Visible", "" + result, expectedValue);
			} else {
				se.log().logSeStep("VERIFY FAILED: " + element.getTagName() + " is NOT visible");
				se.reporter().reportFailCapture(element.getTagName() + " Is Visible", "" + result, expectedValue, se);
			}
			return result;

		case "IsEnable":
			// actualValue = element.getAttribute("value");
			result = element.isEnabled();
			if (result == Boolean.parseBoolean(expectedValue)) {
				se.log().logSeStep("VERIFY " + assertion + ": " + element.getTagName() + " is enabled");
				se.reporter().reportVerifyPass(element.getTagName() + " Is Enabled", "" + result, expectedValue);
			} else {
				se.log().logSeStep("VERIFY FAILED: " + element.getTagName() + " is NOT enabled");
				se.reporter().reportFailCapture(element.getTagName() + " Is Enabled", "" + result, expectedValue, se);
			}
			return result;

		case "IsSelected":
			// actualValue = element.getAttribute("value");
			result = element.isSelected();
			if (result == Boolean.parseBoolean(expectedValue)) {
				se.log().logSeStep("VERIFY " + assertion + ": " + element.getTagName() + " is selected");
				se.reporter().reportVerifyPass(element.getTagName() + " Is Selected", "" + result, expectedValue);
			} else {
				se.log().logSeStep("VERIFY FAILED: " + element.getTagName() + " is NOT selected");
				se.reporter().reportFailCapture(element.getTagName() + " Is Enabled", "" + result, expectedValue, se);
			}
			return result;

		case "IsSelectedValue":
			if (element.getAttribute("checked").equals(expectedValue)) {
				se.log().logSeStep("VERIFY " + assertion + ": " + element.getTagName() + " is selected");
				se.reporter().reportVerifyPass(element.getTagName() + " Is Selected Value", "" + result, expectedValue);
				return true;
			} else {
				se.log().logSeStep("VERIFY FAILED: " + element.getTagName() + " is NOT selected");
				se.reporter().reportFailCapture(element.getTagName() + " Is Selected Value", "" + result, expectedValue, se);
				return false;
			}

		case "CheckMultipleValues":
			String eVals[] = expectedValue.split("\\|\\|");
			String resultString = new String();
			result = true;

			element.sendKeys(Keys.ENTER);			
			String elementPrintOut = element.findElement(By.tagName("option")).toString();
			String xpath = elementPrintOut.substring(elementPrintOut.indexOf("//*[@id"), elementPrintOut.indexOf("]]]")+1);
			System.out.println(element.findElements(By.xpath(xpath)));
			System.out.println("xpath: " + xpath);

			for (String expectedOption : eVals) {
				int check = 0;
				for (WebElement option : se.driver().findElements(By.xpath(xpath + "/child::option"))) {

				if(expectedOption.equals(option.getText())) {
						resultString = resultString.concat("true, ");
						break;
					}
					check++;
				}
				if(check == eVals.length)
				{
					resultString = resultString.concat("false, ");
					result = false;
				}
			}
			if(result == Boolean.parseBoolean(expectedValue)) {
				se.log().logSeStep("VERIFY " + assertion + ": " + expectedValue);
			}
			else {
				resultString = resultString.substring(0, resultString.length()-2);
				se.log().logSeStep("VERIFY FAILED - " + assertion + ": " + expectedValue + " resolved " + resultString);
			}
			return result;

		case "CheckColor":
			String color = element.getCssValue("color");
			String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");

			int hexValueR = Integer.parseInt(hexValue[0]);
			hexValue[1] = hexValue[1].trim();
			int hexValueG = Integer.parseInt(hexValue[1]);
			hexValue[2] = hexValue[2].trim();
			int hexValueB = Integer.parseInt(hexValue[2]);

			actualValue = String.format("#%02x%02x%02x", hexValueR, hexValueG, hexValueB);

			if (actualValue.equals(expectedValue)) {
				result = true;
				break;
			}

			break;

		default:
			se.log().logSeStep("VERIFY FAILED: No valid assertion provided");
			se.reporter().reportStepFail("Invalid Assertion", "No valid assertion provided.");
			return false;
		}

		if (result == true) {
			se.log().logSeStep("VERIFY " + assertion + ": " + expectedValue);
			se.reporter().reportVerifyPass("Verify " + assertion, "" + actualValue, expectedValue);
		} else {
			se.log().logSeStep("VERIFY FAILED - " + assertion + ": " + expectedValue
					+ " does not match actual value -> " + actualValue);
			se.reporter().reportFailCapture("Verify " + assertion, "" + actualValue, expectedValue, se);
		}

		return result;
	}

	public boolean[] verify(WebElement[] element, String[] arg) {
		boolean[] result = new boolean[arg.length];
		for (int i = 0; i < arg.length; i++) {
			result[i] = verify(element[i], arg[i]);
		}

		return result;
	}

}
