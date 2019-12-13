package com.test.automation.common.framework;


import org.apache.commons.lang.time.DateUtils;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import com.test.automation.common.SeHelper;
import com.test.automation.common.SystemPropertyUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import java.net.URI;
//import java.net.URISyntaxException;

public class Util {
	public static final SimpleDateFormat ISO8601_DATESTAMP = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat ISO8601_DATETIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	//public final SimpleDateFormat ISO8601_DATETIMESTAMP_FILE = new SimpleDateFormat("yyyyMMdd kkmmssSSS");
	public static final SimpleDateFormat ISO8601_TIMESTAMP = new SimpleDateFormat("HH:mm:ss.SSS");
 
    
	public Calendar cal = Calendar.getInstance();

	public Util()
	{
	}
	/**
	 * Sleep some ms
	 * @param ms
	 */
	public void sleep(int ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * generates a random int between Min and Max inclusive, returns Integer instead of int so user is able to perform Integer.toString()
	 * Created by An Doan.
	 *
	 * @param Min : min number
	 * @param Max : max number
	 * @return Integer : Integer between min and max inclusive
	 *
	 */
	public static Integer randomNum(int Min, int Max) {
		return Min + (int) (Math.random() * ((Max - Min) + 1));
	}
	
	public static Long randomNum(long Min, long Max) {
		return Min + (long) (Math.random() * ((Max - Min) + 1));
	}
	

	/**
	 * get the date stamp
	 * @param dateFormat
	 * @return
	 */
	public static String getDateStamp(SimpleDateFormat dateFormat)
	{
		//dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		if (dateFormat.equals(ISO8601_DATETIMESTAMP))
			return dateFormat.format(new Date()).replace(" ", "T") + "Z";
		else
			return dateFormat.format(new Date());
	}

	
	public int randomnumber() {
	    Random r = new Random( System.currentTimeMillis() );
	    return 10000 + r.nextInt(20000);
	}
	
/*	public int randomPolicyNo() {
	    Random r = new Random();
	    return 001111111 + r.nextInt(001234567);
	}*/
	// ADDED FOR RANDOM NUMBER - EXTERNAL - NILESH
	public String randomApplicantNameNo() {
	    Random r = new Random();
	    int no = 00000 + r.nextInt(99999);
	    return Integer.toString(no);
	}
	
	public String gettodaydate(String format){
				
		SimpleDateFormat dateOnly = new SimpleDateFormat(format);
		String date = dateOnly.format(cal.getTime());
		return date;
		
	}	
	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String date = sdf.format(cal.getTime()).toString();
		return date;
	}

	public static String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ssa");
		String time = sdf.format(cal.getTime()).toString();
		return time;
	}
	public String replacetext(String value, String Character){
		
		String newvalue = value.replace(Character,"");		
		return newvalue;	
	}
	
	public String tomorrowsdate(){
		
		SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DATE, 1 );    
		String convertedDate=dateOnly.format(cal.getTime());    
		return  convertedDate;
		
	}
	
	public String nextmonth_date(){
		
		SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.MONTH, 1 );    
		String convertedDate=dateOnly.format(cal.getTime());    
		return  convertedDate;
		
	}
	
	public String futuredate(String cycledate) throws ParseException{
	
			SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
			Date date = dateOnly.parse(cycledate);
			cal.setTime(date);
			cal.add(Calendar.MONTH, 3);    
			String futureDate = dateOnly.format(cal.getTime());    
			return  futureDate;		
	}
	
	public String futureyear(String cycledate, int noofyears) throws ParseException{
		
		SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
		Date date = dateOnly.parse(cycledate);
		cal.setTime(date);
		cal.add(Calendar.YEAR, noofyears);    
		String futureDate = dateOnly.format(cal.getTime());    
		return  futureDate;		
	}
	
	public String nextmonthdate(){
		
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		LocalDate today = LocalDate.now();
		LocalDate nextmonth = today.plus(1, ChronoUnit.MONTHS);
		String futuredate = nextmonth.format(formatter);	
		return  futuredate;
		
	}
	
	public String nextmonthdateformat(){
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate today = LocalDate.now();
		LocalDate nextmonth = today.plus(1, ChronoUnit.MONTHS);
		String futuredate = nextmonth.format(formatter);	
		return  futuredate;
		
	}
	
	public String twomonthsdateformat(){
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate today = LocalDate.now();
		LocalDate nextmonth = today.plus(2, ChronoUnit.MONTHS);
		String futureDate = nextmonth.format(formatter);
		String futuredate = change_dateformat(futureDate);
		return  futuredate;
		
	}
	
	public String quaterlydate(){
		
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		LocalDate today = LocalDate.now();
		LocalDate nextmonth = today.plus(3, ChronoUnit.MONTHS);
		String futuredate = nextmonth.format(formatter);	
		return  futuredate;
		
	}
	
	public String twomonthsdate(){
		
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		LocalDate today = LocalDate.now();
		LocalDate nextmonth = today.plus(2, ChronoUnit.MONTHS);
		String futuredate = nextmonth.format(formatter);	
		return  futuredate;
		
	}	
	
	public String nextmonth_date(String datevalue){
		
		SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.MONTH, 1 );		
		String convertedDate=dateOnly.format(cal.getTime());		
		return  convertedDate;
		
		
		
	}
	
	public String getFutureDay(String date,int noOfDays){
		
		String[] dateval = date.split("/");
		
		int month = Integer.parseInt(dateval[0]);
		int day = Integer.parseInt(dateval[1]);
		int year = Integer.parseInt(dateval[2]);
				
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate today = LocalDate.of(year, month, day);
		LocalDate nextmonth = today.plus(noOfDays, ChronoUnit.DAYS);
		String futuredate = nextmonth.format(formatter);	
		String finalDate = change_dateformat(futuredate);
		return finalDate;
		
	}
	
	public String getFutureMonth(String date,int noOfMonths){
		
		String[] dateval = date.split("-");
		
		int month = Integer.parseInt(dateval[0]);
		int day = Integer.parseInt(dateval[1]);
		int year = Integer.parseInt(dateval[2]);
				
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate today = LocalDate.of(year, month, day);
		LocalDate nextmonth = today.plus(noOfMonths, ChronoUnit.MONTHS);
		String futuredate = nextmonth.format(formatter);	
		String finalDate = change_dateformat(futuredate);
		return finalDate;
		
	}
	
	public String getFutureYear(String date,int noOfYears){
		
		String[] dateval = date.split("-");
		
		int month = Integer.parseInt(dateval[0]);
		int day = Integer.parseInt(dateval[1]);
		int year = Integer.parseInt(dateval[2]);
				
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate today = LocalDate.of(year, month, day);
		LocalDate nextmonth = today.plus(noOfYears, ChronoUnit.YEARS);
		String futuredate = nextmonth.format(formatter);	
		String finalDate = change_dateformat(futuredate);
		return finalDate;
		
	}
	
	
	public String getEarlierMonth(String date,int noOfMonths){
		
		String[] dateval = date.split("/");
		
		int month = Integer.parseInt(dateval[0]);
		int day = Integer.parseInt(dateval[1]);
		int year = Integer.parseInt(dateval[2]);
				
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate today = LocalDate.of(year, month, day);
		LocalDate nextmonth = today.minus(noOfMonths, ChronoUnit.MONTHS);
		String earlierdate = nextmonth.format(formatter);	
		String finalDate = change_dateformat(earlierdate);
		return finalDate;
		
	}
	
	public String getEarlierDay(String date,int noOfDays){
		
		String[] dateval = date.split("/");
		
		int month = Integer.parseInt(dateval[0]);
		int day = Integer.parseInt(dateval[1]);
		int year = Integer.parseInt(dateval[2]);
				
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate today = LocalDate.of(year, month, day);
		LocalDate nextmonth = today.minus(noOfDays, ChronoUnit.DAYS);
		String earlierdate = nextmonth.format(formatter);	
		String finalDate = change_dateformat(earlierdate);	
		return finalDate;
		
	}
	
	public String getEarlierYear(String date,int noOfMonths){
		
		String[] dateval = date.split("/");
		
		int month = Integer.parseInt(dateval[0]);
		int day = Integer.parseInt(dateval[1]);
		int year = Integer.parseInt(dateval[2]);
				
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate today = LocalDate.of(year, month, day);
		LocalDate nextyear = today.minus(noOfMonths, ChronoUnit.YEARS);
		String earlierdate = nextyear.format(formatter);	
		String finalDate = change_dateformat(earlierdate);
		return finalDate;
		
	}

	public String dateformat(String date){
		
		String[] dateval = date.split("/");
		
		int month = Integer.parseInt(dateval[0]);
		int day = Integer.parseInt(dateval[1]);
		int year = Integer.parseInt(dateval[2]);
				
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		LocalDate datevalue = LocalDate.of(year, month, day);		
		String formateddate = datevalue.format(formatter);
			
		return formateddate;
		
	}
	
	public String changedateformat(String date){
				
			String[] dateval = date.split("-");
			String formatedate = dateval[0]+dateval[1]+dateval[2];		
			return formatedate;
	}	
	
	public String change_dateformat(String date){
		
		String[] dateval = date.split("-");		                                                           
		String formatedate = dateval[1]+"-"+dateval[2]+"-"+dateval[0];		
		return formatedate;
	}	
	
	public String convertStringToDollorFormat(String sString){
		 Double dAmount =  Double.parseDouble(sString);		
		 NumberFormat fmt = NumberFormat.getCurrencyInstance();
		 String  sDollor = fmt.format(dAmount);	
		return sDollor;
	}
	
	
	public String getEarlierdate(){
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate today = LocalDate.now();
		LocalDate earlierdate = today.plus(-15, ChronoUnit.DAYS);
		String Earlierdate = earlierdate.format(formatter);	
		String formattedate = change_dateformat(Earlierdate);
		return  formattedate;
		
	}
	
	public String getTodaydate(){
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		LocalDate today = LocalDate.now();		
		String Todaydate = today.format(formatter);	
		String todaydate = change_dateformat(Todaydate);
		return  todaydate;
		
	}
	
	public static String captureScreenshot(String screenshotName, SeHelper se) throws IOException{
		
		File screenshotSource = null;
		screenshotSource = ((TakesScreenshot) se.driver()).getScreenshotAs(OutputType.FILE);
		String dest = SystemPropertyUtil.getScreenshotPath()+"Run_"+Util.getCurrentDate()+"_"+Util.getCurrentTime()+"\\Automation\\"+screenshotName+".png";
		File screenshotDest = new File(dest);
		FileUtils.copyFile(screenshotSource, screenshotDest);
		return dest;
	}
	
	
	public static final Map<String, String> STATE_MAP;
	static {
	    STATE_MAP = new HashMap<String, String>();
	    STATE_MAP.put("AL", "Alabama");
	    STATE_MAP.put("AK", "Alaska");
	    STATE_MAP.put("AB", "Alberta");
	    STATE_MAP.put("AZ", "Arizona");
	    STATE_MAP.put("AR", "Arkansas");
	    STATE_MAP.put("BC", "British Columbia");
	    STATE_MAP.put("CA", "California");
	    STATE_MAP.put("CO", "Colorado");
	    STATE_MAP.put("CT", "Connecticut");
	    STATE_MAP.put("DE", "Delaware");
	    STATE_MAP.put("DC", "District Of Columbia");
	    STATE_MAP.put("FL", "Florida");
	    STATE_MAP.put("GA", "Georgia");
	    STATE_MAP.put("GU", "Guam");
	    STATE_MAP.put("HI", "Hawaii");
	    STATE_MAP.put("ID", "Idaho");
	    STATE_MAP.put("IL", "Illinois");
	    STATE_MAP.put("IN", "Indiana");
	    STATE_MAP.put("IA", "Iowa");
	    STATE_MAP.put("KS", "Kansas");
	    STATE_MAP.put("KY", "Kentucky");
	    STATE_MAP.put("LA", "Louisiana");
	    STATE_MAP.put("ME", "Maine");
	    STATE_MAP.put("MB", "Manitoba");
	    STATE_MAP.put("MD", "Maryland");
	    STATE_MAP.put("MA", "Massachusetts");
	    STATE_MAP.put("MI", "Michigan");
	    STATE_MAP.put("MN", "Minnesota");
	    STATE_MAP.put("MS", "Mississippi");
	    STATE_MAP.put("MO", "Missouri");
	    STATE_MAP.put("MT", "Montana");
	    STATE_MAP.put("NE", "Nebraska");
	    STATE_MAP.put("NV", "Nevada");
	    STATE_MAP.put("NB", "New Brunswick");
	    STATE_MAP.put("NH", "New Hampshire");
	    STATE_MAP.put("NJ", "New Jersey");
	    STATE_MAP.put("NM", "New Mexico");
	    STATE_MAP.put("NY", "New York");
	    STATE_MAP.put("NF", "Newfoundland");
	    STATE_MAP.put("NC", "North Carolina");
	    STATE_MAP.put("ND", "North Dakota");
	    STATE_MAP.put("NT", "Northwest Territories");
	    STATE_MAP.put("NS", "Nova Scotia");
	    STATE_MAP.put("NU", "Nunavut");
	    STATE_MAP.put("OH", "Ohio");
	    STATE_MAP.put("OK", "Oklahoma");
	    STATE_MAP.put("ON", "Ontario");
	    STATE_MAP.put("OR", "Oregon");
	    STATE_MAP.put("PA", "Pennsylvania");
	    STATE_MAP.put("PE", "Prince Edward Island");
	    STATE_MAP.put("PR", "Puerto Rico");
	    STATE_MAP.put("QC", "Quebec");
	    STATE_MAP.put("RI", "Rhode Island");
	    STATE_MAP.put("SK", "Saskatchewan");
	    STATE_MAP.put("SC", "South Carolina");
	    STATE_MAP.put("SD", "South Dakota");
	    STATE_MAP.put("TN", "Tennessee");
	    STATE_MAP.put("TX", "Texas");
	    STATE_MAP.put("UT", "Utah");
	    STATE_MAP.put("VT", "Vermont");
	    STATE_MAP.put("VI", "Virgin Islands");
	    STATE_MAP.put("VA", "Virginia");
	    STATE_MAP.put("WA", "Washington");
	    STATE_MAP.put("WV", "West Virginia");
	    STATE_MAP.put("WI", "Wisconsin");
	    STATE_MAP.put("WY", "Wyoming");
	    STATE_MAP.put("YT", "Yukon Territory");
	}
	
	public String toStates(String s) {
	    return STATE_MAP.get(s);
	}
	
	
	public static final Map<String, String> STATES;
	static {
		STATES = new HashMap<String, String>();
		STATES.put("Alabama", "AL");
		STATES.put("Alaska", "AK");
		STATES.put("Alberta", "AB");
		STATES.put("American Samoa", "AS");
		STATES.put("Arizona", "AZ");
		STATES.put("Arkansas", "AR");
		STATES.put("Armed Forces (AE)", "AE");
		STATES.put("Armed Forces Americas", "AA");
		STATES.put("Armed Forces Pacific", "AP");
		STATES.put("British Columbia", "BC");
		STATES.put("California", "CA");
		STATES.put("Colorado", "CO");
		STATES.put("Connecticut", "CT");
		STATES.put("Delaware", "DE");
		STATES.put("District Of Columbia", "DC");
		STATES.put("Florida", "FL");
		STATES.put("Georgia", "GA");
		STATES.put("Guam", "GU");
		STATES.put("Hawaii", "HI");
		STATES.put("Idaho", "ID");
		STATES.put("Illinois", "IL");
		STATES.put("Indiana", "IN");
		STATES.put("Iowa", "IA");
		STATES.put("Kansas", "KS");
		STATES.put("Kentucky", "KY");
		STATES.put("Louisiana", "LA");
		STATES.put("Maine", "ME");
		STATES.put("Manitoba", "MB");
		STATES.put("Maryland", "MD");
		STATES.put("Massachusetts", "MA");
		STATES.put("Michigan", "MI");
		STATES.put("Minnesota", "MN");
		STATES.put("Mississippi", "MS");
		STATES.put("Missouri", "MO");
		STATES.put("Montana", "MT");
		STATES.put("Nebraska", "NE");
		STATES.put("Nevada", "NV");
		STATES.put("New Brunswick", "NB");
		STATES.put("New Hampshire", "NH");
		STATES.put("New Jersey", "NJ");
		STATES.put("New Mexico", "NM");
		STATES.put("New York", "NY");
		STATES.put("Newfoundland", "NF");
		STATES.put("North Carolina", "NC");
		STATES.put("North Dakota", "ND");
		STATES.put("Northwest Territories", "NT");
		STATES.put("Nova Scotia", "NS");
		STATES.put("Nunavut", "NU");
		STATES.put("Ohio", "OH");
		STATES.put("Oklahoma", "OK");
		STATES.put("Ontario", "ON");
		STATES.put("Oregon", "OR");
		STATES.put("Pennsylvania", "PA");
		STATES.put("Prince Edward Island", "PE");
		STATES.put("Puerto Rico", "PR");
		STATES.put("Quebec", "QC");
		STATES.put("Rhode Island", "RI");
		STATES.put("Saskatchewan", "SK");
		STATES.put("South Carolina", "SC");
		STATES.put("South Dakota", "SD");
		STATES.put("Tennessee", "TN");
		STATES.put("Texas", "TX");
		STATES.put("Utah", "UT");
		STATES.put("Vermont", "VT");
		STATES.put("Virgin Islands", "VI");
		STATES.put("Virginia", "VA");
		STATES.put("Washington", "WA");
		STATES.put("West Virginia", "WV");
		STATES.put("Wisconsin", "WI");
		STATES.put("Wyoming", "WY");
		STATES.put("Yukon Territory", "YT");
	}
	
	public String toStateCodes(String s) {
	    return STATES.get(s);
	}
	
	public void removeDuplicateStringsFrmList(List<String> stringElemets) {

		LinkedHashSet<String> lhs = new LinkedHashSet<String>();
		lhs.addAll(stringElemets);
		stringElemets.clear();
		stringElemets.addAll(lhs);

	}
	
	public String stripStateFrmString(String locAdd){
		
		String[] parseAddress = locAdd.split(",");
		String getStateandZip = parseAddress[1];
		String[] parseStateFromZip = getStateandZip.split("(?<=\\D)(?=\\d)");
		String getState = parseStateFromZip[0].trim();
		String convertAbbtoState = toStates(getState);
		return convertAbbtoState;
	}
	

}