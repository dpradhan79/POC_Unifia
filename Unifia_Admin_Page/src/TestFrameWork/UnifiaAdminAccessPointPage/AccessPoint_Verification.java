package TestFrameWork.UnifiaAdminAccessPointPage;

import TestFrameWork.Unifia_Admin_Selenium;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.IOException;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions;

public class AccessPoint_Verification extends Unifia_Admin_Selenium{
	
	public static String Verify_NewSSID(String SSID){
		Actual= driver.findElementById("-1_SSID").getAttribute("value");
		//System.out.println("SSID field value is:"+Actual);
		if(SSID.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+SSID;
		}
		return Result;
	}
	
	public static String Verify_NewPassword(String Password){
		Actual= driver.findElementById("-1_Password").getAttribute("value");
		//System.out.println("Password field value is:"+Actual);
		if(Password.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Password;
		}
		return Result;
	}
	
	

	public static String Verify_ModifySSID(String GridID, String SSID){
		Actual=driver.findElementById(GridID+"_SSID").getAttribute("value");
		if(Actual.equalsIgnoreCase(SSID)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+SSID;
		}
		return Result;
	}
		
	public static String Verify_ModifyPassword(String GridID, String Password){
		Actual=driver.findElementById(GridID+"_Password").getAttribute("value"); 
		if(Actual.equalsIgnoreCase(Password)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Password;
		}
		return Result;
	}
	

}
