package TestFrameWork.UnifiaAdminScannerPage;

import TestFrameWork.Unifia_Admin_Selenium;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions;

public class Scanner_Verification extends Unifia_Admin_Selenium{
	

	// Verify new scanner ID
	public static String Verify_NewScanID(String ScanID_Act){
		Actual= driver.findElementById("-1_ScannerID").getAttribute("value");
		System.out.println("Scanner ID field value is:"+Actual);
		if(ScanID_Act.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ScanID_Act;
		}
		return Result;
	}
	
	// Verify new scanner name
	public static String Verify_NewScanName(String ScanName_Act){
		Actual= driver.findElementById("-1_Name").getAttribute("value");
		System.out.println("Scanner name field value is:"+Actual);
		if(ScanName_Act.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ScanName_Act;
		}
		return Result;
	}
	
	
	// Verify modifying scanner location
	public static String Verify_ModifyScanLoc(String GridID, String Loc_Act){
		
		Select droplist = new Select(driver.findElementById(GridID+"_LocationID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(Loc_Act)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Loc_Act+" , the Actual is:  "+Actual;
		}
		return Result;
		

	}
		
	// Verify Modifing scanner ID 
	public static String Verify_ModifyScanID(String GridID, String ScanID_Act){
		Actual=driver.findElementById(GridID+"_ScannerID").getAttribute("value");
		if(Actual.equals(ScanID_Act)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ScanID_Act;
		}
		return Result;
	}
		
	// Verify modifying scanner name
	public static String Verify_ModifyScanName(String GridID, String ScanName){
		Actual=driver.findElementById(GridID+"_Name").getAttribute("value"); 
		if(Actual.equals(ScanName)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ScanName;
		}
		return Result;
	}
	
	// Verify saving scanner
	public static String Verify_ScanSave(String Loc_Act, String ScanID_Act, String ScanName_Act) throws InterruptedException{
		Scanner_Actions.ClearScannerSrchCritera();
	//	Scanner_Actions.Search_Location(Loc_Act);
		try{
			GridID=driver.findElement(By.xpath("//table['jqgrid_scanner']/tbody/tr/td[text()='"+ScanID_Act+"']//..")).getAttribute("id");
			//Result="Pass:  The saved ScanID exists as ScanID Grid ID:  "+GridID;
			Result="Passed";
		}catch(NoSuchElementException e){
			//Result="#Failed!#:  The Scanner was not saved with Name:  "+ScanID_Act+" and Abbreviation:  "+ScanName_Act;
			Result="#Failed!#";
		}
		System.out.println("Saving of the Exam type: "+Result);
		Scanner_Actions.ClearScannerSrchCritera();
		return Result;
	}
	
	public static String Verify_New_ScannerLocation(String Loc){
		Select droplist = new Select(driver.findElementById("-1_LocationID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(Loc)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Loc+" , the Actual is:  "+Actual;
		}
		return Result;
	}




	public static String Verify_Scanner_Location_List_Options(String LocOptions){
		ArrayList<String> ScannerLocations = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("-1_LocationID_FK"));
		for(WebElement option : droplist.getOptions()) {
			ScannerLocations.add(option.getAttribute("text"));
		}

		String ScannerLocationList = StringUtils.join(ScannerLocations.toArray(), ", ");
		System.out.println("Scanner Location List is " + ScannerLocationList);
		Actual=ScannerLocationList;
		if(Actual.equals(LocOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual Location Options:  "+Actual+", Expected Location Options:  "+LocOptions;
		}
		return Result;
	}
}
