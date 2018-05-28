package TestFrameWork.UnifiaAdminScopePage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class Scope_Verification extends Unifia_Admin_Selenium  {
	
	public static String Verify_NewScopeType(String ScopeType) throws InterruptedException {
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(500);

		Select droplist = new Select(driver.findElementById("-1_ScopeTypeID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(ScopeType)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+ScopeType+" , the Actual is:  "+Actual;
		}
		return Result;
		

	}
	
	public static String Verify_ModScopeType(String GridID, String ScopeType) throws InterruptedException {

		Thread.sleep(500);

		Select droplist = new Select(driver.findElementById(GridID+"_ScopeTypeID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(ScopeType)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+ScopeType+" , the Actual is:  "+Actual;
		}
		return Result;

	}

	public static String Verify_NewRFUID(String RFUID) throws InterruptedException {
		Thread.sleep(500);
		
		Actual=driver.findElementById("-1_RFUID").getAttribute("value");
		System.out.println("Actual RFUID value ="+Actual);

		if(RFUID.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Scope RFUID should be "+RFUID+" but it is "+Actual;
		}
		return Result;
	}
	public static String Verify_ModRFUID(String GridID, String RFUID) throws InterruptedException {
		Thread.sleep(1500);
		
		Actual=driver.findElementById(GridID+"_RFUID").getAttribute("value");
		System.out.println("Actual RFUID value ="+Actual);

		if(RFUID.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Scope RFUID should be "+RFUID+" but it is "+Actual;
		}
		return Result;
	}
	
	public static String Verify_NewScopeName(String ScopeName) throws InterruptedException {
		Thread.sleep(1000);
		
		Actual=driver.findElementById("-1_Name").getAttribute("value");
		System.out.println("Actual Name value ="+Actual);

		if(ScopeName.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Scope name should be "+ScopeName+" but it is "+Actual;
		}
		return Result;
	}
	public static String Verify_ModScopeName(String GridID, String ScopeName) throws InterruptedException {
		Thread.sleep(1000);
		
		Actual=driver.findElementById(GridID+"_Name").getAttribute("value");
		System.out.println("Actual Modified Name value ="+Actual+" it should be "+ScopeName);

		if(ScopeName.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Scope name should be "+ScopeName+" but it is "+Actual;
		}
		return Result;
	}

	public static String Verify_NewSerialNumber(String SerialNumber) throws InterruptedException {
		Thread.sleep(500);
		
		Actual=driver.findElementById("-1_SerialNumber").getAttribute("value");
		System.out.println("Actual SerialNumber value ="+Actual);

		if(SerialNumber.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Serial Number should be "+SerialNumber+" but it is "+Actual;
		}
		return Result;
	}
	
	public static String Verify_ModSerialNumber(String GridID, String SerialNumber) throws InterruptedException {
		Thread.sleep(500);
		
		Actual=driver.findElementById(GridID+"_SerialNumber").getAttribute("value");
		System.out.println("Actual Modified SerialNumber value ="+Actual);

		if(SerialNumber.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Serial Number should be "+SerialNumber+" but it is "+Actual;
		}
		return Result;
	}
	



	public static String Verify_NewScopeStatus(String Status) throws InterruptedException {
		Thread.sleep(500);
	
		Actual=driver.findElementById("-1_IsActive").getAttribute("value");
		System.out.println("Actual Status value ="+Actual);

		if(Status.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - Expected:  "+Status+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	
	public static String Verify_ModScopeStatus(String GridID, String Status) throws InterruptedException {
	
		
		Thread.sleep(500);
		Actual=driver.findElementById(GridID+"_IsActive").getAttribute("value");
		
		System.out.println("Actual Status value ="+Actual);

		if(Status.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - Expected:  "+Status+" , the Actual is:  "+Actual;
		}
		return Result;
	}

	public static String Verify_NewComment(String Comment) throws InterruptedException {
		Thread.sleep(500);
		
		Actual=driver.findElementById("-1_Comments").getAttribute("value");
		System.out.println("Actual Comment value ="+Actual);

		if(Comment.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Comment should be "+Comment+" but it is "+Actual;
		}
		return Result;
	}
	
	public static String Verify_ModComment(String GridID, String Comment) throws InterruptedException {
		Thread.sleep(500);
		
		Actual=driver.findElementById(GridID+"_Comments").getAttribute("value");
		System.out.println("Actual Modified Comment value ="+Actual);

		if(Comment.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Comment should be "+Comment+" but it is "+Actual;
		}
		return Result;
	}
	
	// Implemented by Jon for the Complex test Scenarios
	//Verify selected Facility droplist Value
	//Verify the contents of the Facility droplist
	//Verify the contents of the Scope Type droplist
	
	public static String Verify_New_Facility_Selection(String Fac){
		Select droplist = new Select(driver.findElementById("-1_FacilityID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(Fac)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Fac+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	
	public static String Verify_Modify_Facility_Selection(String GridID, String Fac){
		Select droplist = new Select(driver.findElementById(GridID+"_FacilityID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(Fac)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Fac+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	
	public static String Verify_Facility_List_Options(String FacOptions){
		ArrayList<String> facilityLocations = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("-1_FacilityID_FK"));
		for(WebElement option : droplist.getOptions()) {
			facilityLocations.add(option.getAttribute("text"));
		}
		
		String facilityLocationList = StringUtils.join(facilityLocations.toArray(), ", ");
		System.out.println("Facility Location List is " + facilityLocationList);
		Actual=facilityLocationList;
		if(Actual.equalsIgnoreCase(FacOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual Facility Options:  "+Actual+", Expected Facility Options:  "+FacOptions;
		}
		return Result;
	}
	
	public static String Verify_ScopeType_List_Options(String STOptions){
		ArrayList<String> ScopeType = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("-1_ScopeTypeID_FK"));
		for(WebElement option : droplist.getOptions()) {
			ScopeType.add(option.getAttribute("text"));
		}
		
		String ScopeTypeList = StringUtils.join(ScopeType.toArray(), ", ");
		System.out.println("Scope Type List is " + ScopeTypeList);
		Actual=ScopeTypeList;
		if(Actual.equalsIgnoreCase(STOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual Scope Type Options:  "+Actual+", Expected Scope Type Options:  "+STOptions;
		}
		return Result;
	}
	
	public static String Verify_ScopeStatus_New(String Status){
		
		Actual=driver.findElementById("-1_IsActive").getAttribute("value");
		System.out.println("Actual Status value ="+Actual);
		if (Actual==null){
			//it is false
			Actual="False";
		}
		if(Status.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#: Status should be "+Status+" but it is "+Actual;
		}
		return Result;		
		
	}
	
	
}
