package TestFrameWork.UnifiaAdminScopeSafetyPage;

import TestFrameWork.Unifia_Admin_Selenium;

//import java.util.ArrayList;
//import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
//import java.io.IOException;

//import org.apache.commons.lang.StringUtils;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;

//import TestFrameWork.Unifia_Admin_Selenium;
//import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions;

public class ScopeSafety_Verification extends Unifia_Admin_Selenium{
	//To Verify Scope Safety screen
	public static String Verify_ScopeSafety_Page(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		String strMain = driver.findElementById("gview_jqgrid_scope_safety").getText();
		  String[] arrSplit = strMain.split("\n");
		  String Actual = (arrSplit[0]);
		  //String ScreenName;
		if(Actual.equals("Scope Safety List")){
			Result = "Pass";
			 return Result;
		  }else{
			  Result = "#Failed!#";
			  return Result;
		  }		  
		}
	//To verify modified Scope hang time
	public static String Verify_ModHangTime(String GridID, String HangTime) {
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
	public static String Verify_ScopeSafety_Facilty() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		String SearchRes=driver.findElementById("jqgrid_scope_safety_pager_right").getText();
		return SearchRes;
	}

}
