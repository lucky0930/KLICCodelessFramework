package com.test.automation.common.framework;

import java.util.Random;

public class CustomHandler {
	
	public String handle(String function)
	{
		switch(function)
		{
			case "RandomName()":
				int lowerLimitA = 65; // letter 'A'
			    int upperLimitZ = 90; // letter 'Z'
			    int targetStringLength = 10;
			    Random randomChar = new Random();
			    StringBuilder buffer = new StringBuilder(targetStringLength);
			    for (int i = 0; i < targetStringLength; i++) {
			        int randomLimitedInt = lowerLimitA + 
			        	(int) (randomChar.nextFloat() * (upperLimitZ - lowerLimitA + 1) + /*chooses A-Z*/
			        	(randomChar.nextBoolean()? 0 : 32)); //chooses 'UPPER' or 'lower' case
			        buffer.append((char) randomLimitedInt);
			    }
			    String generatedString = buffer.toString();
			 
			    System.out.println(generatedString);
			    return generatedString;
			    
			case "RandomNameWithNumbers()":
				int aLowerLimit = 65; // letter 'A'
			    int zUpperLimit = 90; // letter 'Z'
			    int targetSequenceLength = 10;
			    Random randomAll = new Random();
			    StringBuilder bufferNum = new StringBuilder(targetSequenceLength);
			    for (int i = 0; i < targetSequenceLength; i++) {
			    	if(randomAll.nextInt(4) == 3)
			    	{
			    		bufferNum.append(randomAll.nextInt(10));
			    	}
			    	else
			    	{
				        int randomLimitedInt = aLowerLimit + 
				        	(int) (randomAll.nextFloat() * (zUpperLimit - aLowerLimit + 1) + /*chooses A-Z*/
				        	(randomAll.nextBoolean()? 0 : 32)); //chooses 'UPPER' or 'lower' case
				        bufferNum.append((char) randomLimitedInt);
				    }
			    }
			    String generatedSequence = bufferNum.toString();
			 
			    System.out.println(generatedSequence);
			    return generatedSequence;
				
			    default:
			    	System.out.println("WARNING: Unhandled custom function");
				
		}
		return null;
	}

}
