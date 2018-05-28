package TestFrameWork.UnifiaAdminStaffPage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class Staff_Verification extends Unifia_Admin_Selenium  {
	
	public static String Verify_NewStaffTitle(String Title) throws InterruptedException {
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(500);

		Select droplist = new Select(driver.findElementById("-1_TitleID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(Title)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Title+" , the Actual is:  "+Actual;
		}
		return Result;
		

	}
	
	public static String Verify_ModStaffTitle(String GridID, String Title) throws InterruptedException {

		Thread.sleep(500);

		Select droplist = new Select(driver.findElementById(GridID+"_TitleID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(Title)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Title+" , the Actual is:  "+Actual;
		}
		return Result;

	}

	public static String Verify_NewStaffID(String StaffID) throws InterruptedException {
		Thread.sleep(500);
		
		Actual=driver.findElementById("-1_StaffID").getAttribute("value");
		System.out.println("Actual StaffID value ="+Actual);

		if(StaffID.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	public static String Verify_ModStaffID(String GridID, String StaffID) throws InterruptedException {
		Thread.sleep(1500);
		
		Actual=driver.findElementById(GridID+"_StaffID").getAttribute("value");
		System.out.println("Actual StaffID value ="+Actual);

		if(StaffID.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	
	public static String Verify_NewStaffBadge(String StaffBadge) throws InterruptedException {
		Thread.sleep(500);
		
		Actual=driver.findElementById("-1_BadgeID").getAttribute("value");
		System.out.println("Actual StaffID value ="+Actual);

		if(StaffBadge.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	public static String Verify_ModStaffBadge(String GridID, String StaffBadge) throws InterruptedException {
		Thread.sleep(1500);
		
		Actual=driver.findElementById(GridID+"_BadgeID").getAttribute("value");
		System.out.println("Actual StaffID value ="+Actual);

		if(StaffBadge.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	
	public static String Verify_NewStaffFirstName(String FirstName) throws InterruptedException {
		Thread.sleep(1000);
		
		Actual=driver.findElementById("-1_FirstName").getAttribute("value");
		System.out.println("Actual Name value ="+Actual);

		if(FirstName.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	public static String Verify_ModStaffFirstName(String GridID, String FirstName) throws InterruptedException {
		Thread.sleep(1000);
		
		Actual=driver.findElementById(GridID+"_FirstName").getAttribute("value");
		System.out.println("Actual Modified First Name value ="+Actual);

		if(FirstName.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}

	public static String Verify_NewStaffLastName(String LastName) throws InterruptedException {
		Thread.sleep(1000);
		
		Actual=driver.findElementById("-1_LastName").getAttribute("value");
		System.out.println("Actual Name value ="+Actual);

		if(LastName.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	public static String Verify_ModStaffLastName(String GridID, String LastName) throws InterruptedException {
		Thread.sleep(1000);
		
		Actual=driver.findElementById(GridID+"_LastName").getAttribute("value");
		System.out.println("Actual Modified Last Name value ="+Actual);

		if(LastName.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}

	public static String Verify_NewStaffType(String SType) throws InterruptedException {
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(500);

		Select droplist = new Select(driver.findElementById("-1_StaffTypeID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(SType)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+SType+" , the Actual is:  "+Actual;
		}
		return Result;
		

	}
	public static String Verify_ModStaffType(String GridID, String SType) throws InterruptedException {

		Thread.sleep(500);

		Select droplist = new Select(driver.findElementById(GridID+"_StaffTypeID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(SType)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+SType+" , the Actual is:  "+Actual;
		}
		return Result;

	}

	public static String Verify_NewStaffStatus(String Status) throws InterruptedException {
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
	
	public static String Verify_ModStaffStatus(String GridID, String Status) throws InterruptedException {
	
		
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

}
