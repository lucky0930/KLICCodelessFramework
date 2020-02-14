package com.test.automation.customs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import com.test.automation.common.SeHelper;
import com.test.automation.common.Utils.PDFReader;

public class CustomHandler {
	
	private static SeHelper se;
	
	static String setNewValue = null;
	
	public  CustomHandler(SeHelper se) {
		CustomHandler.se = se;
	}
	
	public static void setValue(String CapturedValue) {
		setNewValue = CapturedValue;
	}
	
	public String handle(String function)
	{
		String var = "";
		
		var = function.substring((function.indexOf('(') + 1), (function.length() - 1));
		System.out.println(var);
		function = function.substring(0,function.indexOf('('));
		Class[] cArgs = new Class[1];
		cArgs[0] = String.class;
				
		try {
			Method callMethod = this.getClass().getDeclaredMethod(function, cArgs);
			callMethod.setAccessible(true);
//			if (var.contains(",")) {
//				return (String) callMethod.invoke(null, varArgs);
//			} else if (var.contentEquals("null")) {
//				return (String) callMethod.invoke(null);
//			} else {
				return (String) callMethod.invoke(null, var);
//			}
		} catch (NoSuchMethodException e) {
			se.log().error("Method: " + function + " does not exist.", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static String RandomName(String var)
	{
		int lowerLimitA = 65; // letter 'A'
	    int upperLimitZ = 90; // letter 'Z'
	    int targetStringLength = 10;
	    if(!var.contentEquals("")) {
	    targetStringLength = Integer.parseInt(var);
	    }
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    Random randomChar = new Random();
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = lowerLimitA + 
	        	(int) (randomChar.nextFloat() * (upperLimitZ - lowerLimitA + 1) + /*chooses A-Z*/
	        	(randomChar.nextBoolean()? 0 : 32)); //chooses 'UPPER' or 'lower' case
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedSequence = buffer.toString();

	    System.out.println(generatedSequence);
	    return generatedSequence;
	}

	private static String RandomNameWithNumbers(String var)
	{
		int lowerLimitA = 65; // letter 'A'
	    int upperLimitZ = 90; // letter 'Z'
	    int targetSequenceLength = 10;
	    if(!var.contentEquals("")) {
	    targetSequenceLength = Integer.parseInt(var);
	    }
	    StringBuilder buffer = new StringBuilder(targetSequenceLength);
	    Random random = new Random(); 
	    for (int i = 0; i < targetSequenceLength; i++) {
	    	if(random.nextInt(4) == 3)
	    	{
	    		buffer.append(random.nextInt(10));
	    	}
	    	else
	    	{
		        int randomLimitedInt = lowerLimitA + 
		        	(int) (random.nextFloat() * (upperLimitZ - lowerLimitA + 1) + /*chooses A-Z*/
		        	(random.nextBoolean()? 0 : 32)); //chooses 'UPPER' or 'lower' case
		        buffer.append((char) randomLimitedInt);
		    }
	    }
	    String generatedSequence = buffer.toString();

	    System.out.println(generatedSequence);
	    return generatedSequence;
	}

	private static String RandomNumbers(String var)
	{
		int targetNumLength = 10;
	    if(!var.contentEquals("")) {
	    targetNumLength = Integer.parseInt(var);
	    }
	    StringBuilder buffer = new StringBuilder(targetNumLength);
	    Random random= new Random(); 
	    for (int i = 0; i < targetNumLength; i++)
    		buffer.append(random.nextInt(10));

	    String generatedSequence = buffer.toString();

	    System.out.println(generatedSequence);
	    return generatedSequence;
	}
	
	private static String RandomEmail(String var) {
		String email = RandomNameWithNumbers("");
		if(var.equals(""))
			email = email.concat("@" + RandomName("5") + ".com");
		else
			email = email.concat("@" + var);

		return email;
	}
	
	private static String screenshot(String var) {
		se.reporter().screenCapture(se);
		return "sc";
	}
	
	
	public static String GeneratePath()
	{
		String home = System.getProperty("user.home");
		//String txt = se.savedData().get("QuoteNumber");
		File file = new File(home+"/Downloads/" + setNewValue + "_Benefitillustration.pdf"); 
		String path = file.toString();
		return path;
	}
	
	
	
}
