package com.test.automation.customs;

import java.util.Random;

public class CustomHandler {
	
	public String handle(String function)
	{
		String var = "";
		
		var = function.substring((function.indexOf('(') + 1), (function.length() - 1));
		System.out.println(var);
		function = function.replace(var, "");
			
		System.out.println(function);
		switch(function)
		{
			case "RandomName()":
				return RandomName(var);
			    
			case "RandomNameWithNumbers()":
				return RandomNameWithNumbers(var);
				
			case "RandomNumbers()":
				return RandomNumbers(var);
				
			case "RandomEmail()":
				String email = RandomNameWithNumbers("");
				if(var.equals(""))
					email = email.concat("@" + RandomName("5") + ".com");
				else
					email = email.concat("@" + var);

				return email;

		    default:
		    	System.out.println("WARNING: Unhandled custom function");
				
		}
		return null;
	}

	private String RandomName(String var)
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

	private String RandomNameWithNumbers(String var)
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

	private String RandomNumbers(String var)
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

}
