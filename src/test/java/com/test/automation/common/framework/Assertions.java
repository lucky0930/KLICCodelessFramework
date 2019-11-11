package com.test.automation.common.framework;

import com.test.automation.common.SeHelper;

import org.openqa.selenium.JavascriptExecutor;
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
				se.reporter().reportPass("URL is: " + expectedValue, "Actual: " + se.browser().getCurrentUrl() + "\nExpected: " + expectedValue);
				return true;
			}
			se.log().logSeStep("VERIFY FAILED - CheckURL: URL does not match expected: " + expectedValue);
			se.reporter().reportFail("URL is: " + expectedValue, "Actual: " + se.browser().getCurrentUrl() + "\nExpected: " + expectedValue);
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
			if (result) {
				se.log().logSeStep("VERIFY " + assertion + ": " + element.getTagName() + " is visible");
				se.reporter().reportPass(element.getTagName() + " Is Visible", "Actual: " + result + "Expected: true");
			} else {
				se.log().logSeStep("VERIFY FAILED: " + element.getTagName() + " is NOT visible");
				se.reporter().reportFail(element.getTagName() + " Is Visible", "Actual: " + result + "Expected: true");
			}
			return result;

		case "IsEnable":
			// actualValue = element.getAttribute("value");
			result = element.isEnabled();
			if (result) {
				se.log().logSeStep("VERIFY " + assertion + ": " + element.getTagName() + " is enabled");
				se.reporter().reportPass(element.getTagName() + " Is Enabled", "Actual: " + result + "Expected: true");
			} else {
				se.log().logSeStep("VERIFY FAILED: " + element.getTagName() + " is NOT enabled");
				se.reporter().reportFail(element.getTagName() + " Is Enabled", "Actual: " + result + "Expected: true");
			}
			return result;

		case "IsSelected":
			// actualValue = element.getAttribute("value");
			result = element.isSelected();
			if (result) {
				se.log().logSeStep("VERIFY " + assertion + ": " + element.getTagName() + " is selected");
				se.reporter().reportPass(element.getTagName() + " Is Selected", "Actual: " + result + "Expected: true");
			} else {
				se.log().logSeStep("VERIFY FAILED: " + element.getTagName() + " is NOT selected");
				se.reporter().reportFail(element.getTagName() + " Is Selected", "Actual: " + result + "Expected: true");
			}
			return result;

		case "IsSelectedValue":
			if (element.getAttribute("checked").equals("checked")) {
				se.log().logSeStep("VERIFY " + assertion + ": " + element.getTagName() + " is selected");
				se.reporter().reportPass(element.getTagName() + " Is Selected", "Actual: " + result + "Expected: true");
				return true;
			} else {
				se.log().logSeStep("VERIFY FAILED: " + element.getTagName() + " is NOT selected");
				se.reporter().reportFail(element.getTagName() + " Is Selected", "Actual: " + result + "Expected: true");
				return false;
			}

		case "CheckMultipleValues":
			String eVals[] = expectedValue.split("||");
			actualValue = element.getAttribute("value").trim();
			for (String value : eVals) {
				if (value.equals(actualValue)) {
					result = true;
					expectedValue = value;
				}
			}
			break;

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
			se.reporter().reportError("Invalid Assertion", "No valid assertion provided.");
			return false;
		}

		if (result == true) {
			se.log().logSeStep("VERIFY " + assertion + ": " + expectedValue);
			se.reporter().reportPass("Verify " + assertion, "Actual: " + actualValue + "\nExpected: " + expectedValue);
		} else {
			se.log().logSeStep("VERIFY FAILED - " + assertion + ": " + expectedValue
					+ " does not match actual value -> " + actualValue);
			se.reporter().reportFail("Verify " + assertion, "Actual: " + actualValue + "\nExpected: " + expectedValue);
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
