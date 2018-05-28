package TestFrameWork.UnifiaAdminScopeTypePage;

import TestFrameWork.Unifia_Admin_Selenium;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminScopeTypePage.ScopeType_Actions;

public class ScopeType_Verification extends Unifia_Admin_Selenium {
	
	public static String Verify_NEWExamTypeName(String Name){
		Actual= driver.findElementById("-1_ExamTypeID_FK").getAttribute("value");
		System.out.println("Exam type name field value is:"+Actual);
		if(Name.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Name;
		}
		return Result;
	}
	
	public static String Verify_NEWScopeType(String Name){
		Actual= driver.findElementById("-1_Name").getAttribute("value");
		if(Name.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Name;
		}
		return Result;
	}
	
	public static String New_ScopeType_Active (String Active){
		//This may not be possible to verify on a new entry
		Actual= driver.findElementById("-1_IsActive").getAttribute("value");
		if(Active.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Active;
		}
		return Result;
	}
	public static String Verify_New_ScopeType_ECN (String Active){
		//This may not be possible to verify on a new entry
		Actual= driver.findElementById("-1_HasElevatorCleaningNotice").getAttribute("value");
		if(Active.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Active;
		}
		return Result;
	}
	
	public static String Verify_ScopeType_ECN_Checked (String GridID,String Active){
		//This may not be possible to verify on a new entry
		Actual= driver.findElementById(GridID+"_HasElevatorCleaningNotice").getAttribute("value");
		if(Active.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Active;
		}
		return Result;
	}
	
	public static String Verify_ModifyExamTypeName(String GridID, String Name){
		Actual=driver.findElementById(GridID+"ExamTypeID_FK").getAttribute("value");
		if(Actual.equals(Name)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Name;
		}
		return Result;
	}
	
	public static String Verify_ModifyScopeType(String GridID, String Name){
		Actual=driver.findElementById(GridID+"_Name").getAttribute("value");
		if(Actual.equals(Name)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Name;
		}
		return Result;
	}
	
	public static String VerifyScopeTypeSave(String ETName, String ST_Name) throws InterruptedException{
		ScopeType_Actions.ClearScopeTypeSrchCritera();
		ScopeType_Actions.SearchScopeType(ST_Name);
		ScopeType_Actions.SearchExamType(ST_Name);
		try{
			GridID=driver.findElement(By.xpath("//table['jqgrid_scope_model']/tbody/tr[2]/td[text()='"+ST_Name+"']//..")).getAttribute("id");
			Result="Pass:  The saved Scope type exists as Scope Type Grid ID:  "+GridID;
		}catch(NoSuchElementException e){
			Result="#Failed!#:  The Scope type was not saved with Name:  "+ST_Name+" and Exam Type: "+ETName+"";
		}
		System.out.println("Saving of the Scope type: "+Result);
		ScopeType_Actions.ClearScopeTypeSrchCritera();
		return Result;
	}
	

	//Added by Jon to Very the complex test scenarios(mostly the drop down fields default and active values)
	

	
	public static String Verify_New_Exam_Type(String EType){
		Select droplist = new Select(driver.findElementById("-1_ExamTypeID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(EType)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+EType+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	
	public static String Verify_Exam_Type_List_Options(String ETOptions){
		ArrayList<String> ScopeTypeExamTypes = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("-1_ExamTypeID_FK"));
		for(WebElement option : droplist.getOptions()) {
			ScopeTypeExamTypes.add(option.getAttribute("text"));
		}
		
		String ScopeTypeExamTypesList = StringUtils.join(ScopeTypeExamTypes.toArray(), ", ");
		System.out.println("Exam Type List is " + ScopeTypeExamTypesList);
		Actual=ScopeTypeExamTypesList;
		if(Actual.equals(ETOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual Exam Type Options:  "+Actual+", Expected Exam Type Options:  "+ETOptions;
		}
		return Result;
	}
	
	public static String Verify_ScopeType_Page(String Pages){
		Actual=driver.findElementById("sp_1_jqgrid_scope_model_pager").getText();
		if(Actual.equals(Pages)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Pages;
		}
		return Result;
	}
}


