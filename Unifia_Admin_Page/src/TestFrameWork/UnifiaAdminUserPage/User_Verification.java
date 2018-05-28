package TestFrameWork.UnifiaAdminUserPage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class User_Verification extends Unifia_Admin_Selenium  {
	
	public static String Verify_UserName(String UserName) throws InterruptedException {
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(500);
		Actual=driver.findElementById("Name").getAttribute("value");

		if(Actual.equalsIgnoreCase(UserName)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+UserName+" , the Actual is:  "+Actual;
		}
		return Result;
		

	}	

	public static String Verify_Staff_List_Options(String StaffOptions){
		ArrayList<String> ScopeType = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("StaffID_FK"));
		for(WebElement option : droplist.getOptions()) {
			ScopeType.add(option.getAttribute("text"));
		}
		
		String StaffTypeList = StringUtils.join(ScopeType.toArray(), ", ");
		System.out.println("Staff List is " + StaffTypeList);
		Actual=StaffTypeList;
		if(Actual.equalsIgnoreCase(StaffOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual Staff Options:  "+Actual+", Expected Staff Options:  "+StaffOptions;
		}
		return Result;
	}
	
	public static String Verify_Staff_Selection(String Staff){
		Select droplist = new Select(driver.findElementById("StaffID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(Staff)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Staff+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	
	public static String Verify_Role_List_Options(String RoleOptions){
		ArrayList<String> Roles = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("RoleID_FK"));
		for(WebElement option : droplist.getOptions()) {
			Roles.add(option.getAttribute("text"));
		}
		
		String RoleList = StringUtils.join(Roles.toArray(), ", ");
		System.out.println("Role List is " + RoleList);
		Actual=RoleList;
		if(Actual.equalsIgnoreCase(RoleOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual Role Options:  "+Actual+", Expected Role Options:  "+RoleOptions;
		}
		return Result;
	}
	
	public static String Verify_Role_Selection(String Role){
		Select droplist = new Select(driver.findElementById("RoleID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(Role)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Role+" , the Actual is:  "+Actual;
		}
		return Result;
	}

	
	
	public static String Verify_DefaultFacility_List_Options(String DefaultFacOptions){
		ArrayList<String> DefFacility = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("FacilityID_FK"));
		for(WebElement option : droplist.getOptions()) {
			DefFacility.add(option.getAttribute("text"));
		}
		
		String DefFacilityList = StringUtils.join(DefFacility.toArray(), ", ");
		System.out.println("Default Facility List is " + DefFacilityList);
		Actual=DefFacilityList;
		if(Actual.equalsIgnoreCase(DefaultFacOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual Default Facility Options:  "+Actual+", Expected Default Facility Options:  "+DefaultFacOptions;
		}
		return Result;
	}
	
	public static String Verify_DefaultFacility_Selection(String DefFacility){
		Select droplist = new Select(driver.findElementById("FacilityID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(DefFacility)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+DefFacility+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	

}
