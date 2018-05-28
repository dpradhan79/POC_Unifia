package TestFrameWork.UnifiaAdminExamTypePage;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions;

public class ExamType_Verification extends Unifia_Admin_Selenium {
	
	public static String Verify_NEWExamTypeName(String ETName){
		Actual= driver.findElementById("-1_Name").getAttribute("value");
		System.out.println("Exam type name field value is:"+Actual);
		if(ETName.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ETName;
		}
		return Result;
	}
	
	public static String Verify_NEWExamTypeAbbrv(String ETAbr){
		Actual= driver.findElementById("-1_Abbreviation").getAttribute("value");
		if(ETAbr.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ETAbr;
		}
		return Result;
	}
	
	public static void Selct_New_ExamType_Active (String Active){
		//This may not be possible to verify on a new entry
	}
	
	public static String Verify_ModifyExamTypeName(String GridID, String ETName){
		Actual=driver.findElementById(GridID+"_Name").getAttribute("value");
		if(Actual.equals(ETName)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ETName;
		}
		return Result;
	}
	
	public static String Verify_ModifyExamTypeAbbrv(String GridID, String ETAbr){
		Actual=driver.findElementById(GridID+"_Abbreviation").getAttribute("value");
		if(Actual.equals(ETAbr)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ETAbr;
		}
		return Result;
	}
	
	public static String VerifyExamTypeSave(String ETName, String ETAbr) throws InterruptedException{
		ExamType_Actions.ClearExamTypeSrchCritera();
		ExamType_Actions.EnterExamTypeNameSearch_SearchActionNotIncluded(ETName);
		ExamType_Actions.SearchExamTypeByAbbrv(ETAbr);
		try{
			GridID=driver.findElement(By.xpath("//table['jqgrid_exam_type']/tbody/tr/td[text()='"+ETAbr+"']//..")).getAttribute("id");
			Result="Pass:  The saved Exam type exists as Exam Type Grid ID:  "+GridID;
		}catch(NoSuchElementException e){
			Result="#Failed!#:  The Exam type was not saved with Name:  "+ETName+" and Abbreviation:  "+ETAbr;
		}
		System.out.println("Saving of the Exam type: "+Result);
		ExamType_Actions.ClearExamTypeSrchCritera();
		return Result;
	}
}
