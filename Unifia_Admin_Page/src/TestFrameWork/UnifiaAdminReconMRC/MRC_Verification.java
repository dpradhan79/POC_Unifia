package TestFrameWork.UnifiaAdminReconMRC;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class MRC_Verification extends Unifia_Admin_Selenium{
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	
	public static StringBuffer verifyMRCDetails(String gridID, String columnsWithValues) throws InterruptedException{
		StringBuffer result= new StringBuffer();
		
		Map<String,String> actualColumnValues=new LinkedHashMap<String, String>();
		Map<String,String> expectedColumnValues=new LinkedHashMap<String, String>();
		
		Map<String,String> columnsWithIndex=MRC_Actions.getMRCColumns();//this method will return the column names with their index in the table
		String []getcolumns=columnsWithValues.split(";");
		
		for(String column: getcolumns){
			String []columnValue=column.split("==");
			expectedColumnValues.put(columnValue[0].toLowerCase().replaceAll("\\s+",""), columnValue[1]);
		}
		
		//getting the UI values for the columns requested
		String actualValue;
		String columnIndex;
		for(String column: expectedColumnValues.keySet()){
			if(columnsWithIndex.containsKey(column)){
				columnIndex=columnsWithIndex.get(column); //getting the index of the given column in the MRC table 
				actualValue=driver.findElementByXPath("//div[@id='mrcGrid']/div[2]/table/tbody/tr["+gridID+"]/td["+columnIndex+"]").getText(); //getting the value of the given column
				actualColumnValues.put(column, actualValue);
			}else{
				result.append("#Failed!# - "+column+" doesnot exists in the table");
			}
		}
		
		try{
	        for (String columnName : expectedColumnValues.keySet())
	        {
	        	if(columnName.equalsIgnoreCase("mrctestdate/time")){
	        		IP_V.verifyDateFormat("MRC Record Management - MRC Test Date/Time",actualColumnValues.get(columnName) );
	        	}
	            if ((actualColumnValues.get(columnName).trim()).equalsIgnoreCase(expectedColumnValues.get(columnName).trim())) {
	            	System.out.println("Pass - "+columnName+" values matched");
	                result.append("Pass - "+columnName+" values matched; ");
	            }else if(columnName.equalsIgnoreCase("REPROCESSOR")){
	            	System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+". Bug 10580: MRC Record Management displays incorrect Reprocessor name when KE-Reprocessor is used. ");
	            	result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+"; "+". Bug 10580: MRC Record Management displays incorrect Reprocessor name when KE-Reprocessor is used. ");
	            }else{
	            	System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+". MRC screen will be further enhanced accordingly script will be fixed later. ");
	            	result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+"; "+". MRC screen will be further enhanced accordingly script will be fixed later. ");
	            }
	        } 
	    } catch (NullPointerException np) {
	    	System.out.println("NullPointerException: " + np.getMessage());
	    }
		
		return result;
	}

	public static String verifyReprocessor(String Reprocessor){
		Actual=driver.findElementByXPath("//div[@id='mrcGrid']//tr[@id='editRow']//select[@id='ReprocessorLocationId']").getAttribute("value");
		switch (Actual) {
		case "51":
			Actual="Reprocessor 1";
			break;
		case "52":
			Actual="Reprocessor 2";
			break;
		case "53":
			Actual="Reprocessor 3";
			break;
		case "54":
			Actual="Reprocessor 4";
			break;
		case "55":
			Actual="Reprocessor 5";
			break;
		case "56":
			Actual="Reprocessor 6";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Reprocessor value ="+Actual);
		if(Actual.equalsIgnoreCase(Reprocessor)){
			Result="Pass - Reprocessor ="+Reprocessor;
		}else{
			Result="#Failed!# - Reprocessor was supposed to be "+Reprocessor+" but it was "+Actual;
		}
		return Result;
	}
	
	public static String verifyMRCRes(String MRCRes) {
		Actual=driver.findElementByXPath("//div[@id='mrcGrid']//tr[@id='editRow']//select[@id='MrcTestResultId']").getAttribute("value");
		switch (Actual) {
		case "3":
			Actual="Pass";
			break;
		case "4":
			Actual="Fail";
			break;
		case "0":
			Actual="";
			break;
		}
		System.out.println("Actual MRC Result value ="+Actual);
		if(Actual.equalsIgnoreCase(MRCRes)){
			Result="Pass - MRC Result ="+MRCRes;
		}else{
			Result="#Failed!# - MRC Result was supposed to be "+MRCRes+" but it was "+Actual;
		}
		return Result;
	}

	public static String verifyMRCStaff(String MRCStaff) {
		Actual=driver.findElementByXPath("//div[@id='mrcGrid']//tr[@id='editRow']//select[@id='MrcTestStaffId']").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="T01";
			break;
		case "22":
			Actual="T02";
			break;
		case "23":
			Actual="T03";
			break;
		case "24":
			Actual="T04";
			break;
		case "25":
			Actual="T05";
			break;
		case "26":
			Actual="T06";
			break;
		case "27":
			Actual="T07";
			break;
		case "28":
			Actual="T08";
			break;
		case "29":
			Actual="T09";
			break;
		case "30":
			Actual="T10";
			break;
		case "31":
			Actual="T11";
			break;
		case "32":
			Actual="T12";
			break;
		case "33":
			Actual="T13";
			break;
		case "34":
			Actual="T14";
			break;
		case "35":
			Actual="T15";
			break;
		case "36":
			Actual="T16";
			break;
		case "37":
			Actual="T17";
			break;
		case "38":
			Actual="T18";
			break;
		case "39":
			Actual="T19";
			break;
		case "40":
			Actual="T20";
			break;
			
		default:
			Actual="";
			break;
		}
		System.out.println("Actual PR in Staff="+Actual);
		if(Actual.equalsIgnoreCase(MRCStaff)){
			Result="Pass - MRC StaffID ="+MRCStaff;
		}else{
			Result="#Failed!# - MRC StaffID was supposed to be "+MRCStaff+" but it was "+Actual;
		}
		return Result;
	}
	
	public static String Verify_Comment(String Comment){
		Actual=driver.findElementByXPath("//div[@id='mrcGrid']//tr[@id='editRow']//input[@id='Comments']").getAttribute("value");
		
		if(Actual.equalsIgnoreCase(Comment)){
			Result="Pass - The comment ="+Comment;
		}else{
			Result="#Failed!# - The comment was supposed to be "+Comment+" but it was "+Actual;
		}
		return Result;
	}
	
	public static String areAllAddMRCFieldsBlank(){
		String result="";
		if(driver.findElementByXPath("//*[@id='txtMrcTestDateTime']").getText().equalsIgnoreCase("")){
			result="Pass - MRC Test Date / Time field value is blank; ";
		}else{
			result="#Failed!# - MRC Test Date / Time field value is not blank; but it is "+driver.findElementByXPath("//*[@id='txtMrcTestDateTime']").getText()+"; ";
		}
		
		Select droplist = new Select(driver.findElementByXPath("//*[@id='txtReprocessor']"));   
		WebElement selected=droplist.getFirstSelectedOption();
		if(selected.getText().equalsIgnoreCase(" ")){
			result+="Pass - Reprocessor field value is blank; ";
		}else{
			result+="#Failed!# - Reprocessor field value is not blank; but it is "+selected.getText()+"; ";
		}
		
		droplist = new Select(driver.findElementByXPath("//*[@id='txtMrcTestResult']"));   
		try{ //workaround: as no element was highlighted when clicked on the test result dropdown and if no option was selected it throws exception which is taken as a positive scenario that the dropdown value is empty
			selected=droplist.getFirstSelectedOption();
			if(selected.getText().equalsIgnoreCase(" ")){
				result+="Pass - Test Result field value is blank; ";
			}else{
				result+="#Failed!# - Test Result field value is not blank; but it is "+selected.getText()+"; ";
			}
		}catch(NoSuchElementException e){
			result+="Pass - Test Result field value is blank; ";
		}
		
		droplist = new Select(driver.findElementByXPath("//*[@id='txtMrcTestStaffId']"));   
		selected=droplist.getFirstSelectedOption();
		if(selected.getText().equalsIgnoreCase(" ")){
			result+="Pass - MRC Test Staff ID field value is blank; ";
		}else{
			result+="#Failed!# - MRC Test Staff ID field value is not blank; but it is "+selected.getText()+"; ";
		}
		
		if(driver.findElementByXPath("//*[@id='txtComments']").getText().equalsIgnoreCase("")){
			result+="Pass - Comments field value is blank; ";
		}else{
			result+="#Failed!# - Comments field value is not blank; but it is "+selected.getText()+"; ";
		}
		return result;
	}
	
	public static String areAllMRCFiltersBlank(){
		String result="";
		Select droplist = new Select(driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[2]/select"));   
		WebElement selected=droplist.getFirstSelectedOption();
		if(selected.getText().equalsIgnoreCase(" ")){
			result+="Pass - Reprocessor filter value is blank; ";
		}else{
			result+="#Failed!# - Reprocessor filter value is not blank; but it is "+selected.getText()+"; ";
		}
		
		droplist = new Select(driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[3]/select"));   
		selected=droplist.getFirstSelectedOption();
		if(selected.getText().equalsIgnoreCase(" ")){
			result+="Pass - MRC Test Result filter value is blank; ";
		}else{
			result+="#Failed!# - MRC Test Result filter value is not blank; but it is "+selected.getText()+"; ";
		}
		
		droplist = new Select(driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[4]/select"));   
		selected=droplist.getFirstSelectedOption();
		if(selected.getText().equalsIgnoreCase(" ")){
			result+="Pass - MRC Test Staff ID filter value is blank; ";
		}else{
			result+="#Failed!# - MRC Test Staff ID filter value is not blank; but it is "+selected.getText();
		}
		
		return result;
	}
	
	public static String verify_MRCError(String Repro, String MRCRes) throws InterruptedException{
		String result="", resValues="";
		Thread.sleep(500);
		Alert alert=Unifia_Admin_Selenium.driver.switchTo().alert();
		alert.getText();
		if (Repro.trim().equalsIgnoreCase("")){
			resValues="Reprocessor value is blank";
		}

		if (resValues.equals("")){
			if (MRCRes.trim().equalsIgnoreCase("")){
				resValues="MRC Result value is blank";
			}
		}else{
			if (MRCRes.trim().equalsIgnoreCase("")){
				resValues=resValues+" and MRC Result value is blank";
			}
		}
		
		if (alert.getText().equalsIgnoreCase("Invalid data entered!")){
			result+="Pass - error is displayed correctly when "+resValues;
		}else{
			result+="#Failed!# - error is not display correctly when "+resValues;
		}
		Thread.sleep(1000);
		return result;
	}
	
	public static String verify_MRCDateRangeText(String startDate, String endDate) throws ParseException{
		String result="";
		String fromDate=driver.findElementByXPath("//*[@id='fromdate']").getAttribute("value").split(" ")[0];
		String[] from=fromDate.split("/");
		fromDate=from[1]+"/"+from[2]+"/"+from[0];
		String toDate=driver.findElementByXPath("//*[@id='todate']").getAttribute("value").split(" ")[0];
		String[] to=toDate.split("/");
		toDate=to[1]+"/"+to[2]+"/"+to[0];
		if(fromDate.equalsIgnoreCase(startDate)){
			if(toDate.equalsIgnoreCase(endDate)){
				result="Pass - The Date Range is displaying correct dates with starting date as '"+startDate+"' and end date as '"+endDate+"'";
			}else{
				result="#Failed!# - The Date Range is displaying correct starting date as '"+startDate+"' but end date is expected to be '"+endDate+"' but however it is '"+toDate+"'";
			}
		}else{
			if(toDate.equalsIgnoreCase(endDate)){
				result="#Failed!# - The Date Range is displaying correct end date as '"+endDate+"' but starting date is expected to be '"+startDate+"' but however it is '"+fromDate+"'";
			}else{
				result="#Failed!# - The Date Range is not displaying correct dates. The start date is expected to be '"+startDate+"' but however it is '"+fromDate+"' and "
						+ "end date is expected to be '"+endDate+"' but however it is '"+toDate+"'";
			}
		}
		if(result.contains("#Failed!#")){
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
		}
		return result;
	}
}
