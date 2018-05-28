package TestFrameWork.UnifiaAdminFacilityPage;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import TestFrameWork.Unifia_Admin_Selenium;

public class Facility_Verification extends Unifia_Admin_Selenium  {

	public static String Verify_NewFacilityName(String FacName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(500);

		Actual=driver.findElementById("-1_Name").getAttribute("value");
		System.out.println("Actual value ="+Actual);

		if(FacName.equals(Actual)){
			Result="Pass - The Facility name value displayed was "+FacName;
		}else{
			Result="#Failed!# - The expected Facility name value is "+FacName+" however the actual was "+Actual;
		}
		return Result;
	}
	
	public static String Verify_NewCustomerNumber(String CustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById("-1_CustomerNumber").getAttribute("value");
		System.out.println("Actual value ="+Actual);

		if(CustNum.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}

	public static String Verify_NewAbbreviation(String Abbreviation) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById("-1_Abbreviation").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(Abbreviation.equals(Actual)){
			Result="Pass - The Abbreviation value displayed was "+Abbreviation;
		}else{
			Result="#Failed!# - The expected value is "+Abbreviation+" however the actual was "+Actual;
		}
		return Result;
	}
	public static String Verify_ModifyFacilityName(String GridID, String FacName){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById(GridID+"_Name").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(FacName.equals(Actual)){
			Result="Pass - The Facility name value displayed was "+FacName;
		}else{
			Result="#Failed!# - The expected value is "+FacName+" however the actual was "+Actual;
		}
		return Result;
	}

	public static String Verify_CustomerNumber(String GridID, String CustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById(GridID+"_CustomerNumber").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(CustNum.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	
	public static String Verify_Abbreviation(String GridID, String Abbreviation){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById(GridID+"_Abbreviation").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(Abbreviation.equals(Actual)){
			Result="Pass - The Abbreviation value displayed was "+Abbreviation;
		}else{
			Result="#Failed!# - The expected value is "+Abbreviation+" however the actual was "+Actual;
		}
		return Result;
	}
	
	public static String Verify_ModHangTime(String GridID, String HangTime){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById(GridID+"_HangTime").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(HangTime.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	public static String Verify_NewHangTime(String HangTime){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById("-1_HangTime").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(HangTime.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	
	public static String getfacilityPrimaryValue(String FacName){
		String modFacIsPrimary_Val = null;
		try{
			modFacIsPrimary_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..").findElement(By.xpath("//td[5]/input")).getAttribute("value");
		}catch(NoSuchElementException e){
			modFacIsPrimary_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[5]/input").getAttribute("value");
		}
		
		System.out.println(modFacIsPrimary_Val);
		if (modFacIsPrimary_Val==null){
			//it is false
			modFacIsPrimary_Val="False";
		} else if(modFacIsPrimary_Val.equalsIgnoreCase("true")) {
			modFacIsPrimary_Val="True";
		}  else if(modFacIsPrimary_Val.equalsIgnoreCase("false")) {
			modFacIsPrimary_Val="False";
		}
		System.out.println("modFacIsPrimary_Val="+modFacIsPrimary_Val);
		return modFacIsPrimary_Val ;
		
	}
	
	public static String Verify_NewSerialNum(String SerialNum) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById("-1_SerialNumber").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(SerialNum.equals(Actual)){
			Result="Pass - The Serial Number value displayed was "+SerialNum;
		}else{
			Result="#Failed!# - The Serial Number is expected to be "+SerialNum+" however it was "+Actual;
		}
		return Result;
	}
	
	public static String Verify_SerialNumber(String GridID, String SerialNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById(GridID+"_SerialNumber").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(SerialNum.equals(Actual)){
			Result="Pass - The Serial Number value displayed was "+SerialNum;
		}else{
			Result="#Failed!# - The Serial Number is expected to be "+SerialNum+" however it was "+Actual;
		}
		return Result;
	}
}
