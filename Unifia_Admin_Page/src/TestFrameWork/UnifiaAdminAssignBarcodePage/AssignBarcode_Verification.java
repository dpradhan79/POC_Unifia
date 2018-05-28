package TestFrameWork.UnifiaAdminAssignBarcodePage;

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

public class AssignBarcode_Verification extends Unifia_Admin_Selenium{
	
	public static String Verify_NewName(String Name){
		Actual= driver.findElementById("-1_Name").getAttribute("value");
		//System.out.println("Barcode name value is:"+Actual);
		if(Name.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Name;
		}
		return Result;
	}
	
	public static String Verify_ModifyName(String GridID, String Name){
		Actual=driver.findElementById(GridID+"_Name").getAttribute("value");
		if(Actual.equalsIgnoreCase(Name)){
			Result="Pass";
		}else{
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Name;
		}
		return Result;
	}
	
	public static String Verify_NewBarcodeType(String BarcodeType) throws InterruptedException {
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("-1_BarcodeTypeID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(BarcodeType)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+BarcodeType+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	public static String Verify_ModBarcodeType(String GridID, String BarcodeType) throws InterruptedException {
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById(GridID+"_BarcodeTypeID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equalsIgnoreCase(BarcodeType)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+BarcodeType+" , the Actual is:  "+Actual;
		}
		return Result;
	}

	public static String Verify_Barcode_Type_List_Options(String BCTypeOptions) throws InterruptedException{
		Thread.sleep(500);
		ArrayList<String> BarcodeTypes = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("-1_BarcodeTypeID_FK"));
		for(WebElement option : droplist.getOptions()) {
			BarcodeTypes.add(option.getAttribute("text"));
		}
		
		String BarcodeTypeList = StringUtils.join(BarcodeTypes.toArray(), ", ");
		System.out.println("Barcode Type List is " + BarcodeTypeList);
		Actual=BarcodeTypeList;
		if(Actual.equals(BCTypeOptions)){
			Result="Pass";
		}else{
			Result="#Failed!# bug 4909 opened -  Actual Barcode Type Options:  "+Actual+", Expected Barcode Type Options:  "+BCTypeOptions;
		}
		return Result;
	}

}
