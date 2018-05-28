package TestFrameWork.Unifia_ExamSummary;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class ES_Verification extends Unifia_Admin_Selenium{
	public static TestFrameWork.Unifia_Admin_Selenium UAS; 
	
	public static String verify_ES_Header(){
		if(driver.findElementsByXPath("//*[@id='bootstrapContainer']/div[2]/div/div/div[1]/span").size()>0){
			String title=driver.findElementByXPath("//*[@id='bootstrapContainer']/div[2]/div/div/div[1]/span").getText();
			title=title.replace("\n", " ");
			if(title.equalsIgnoreCase("Infection Prevention Exam Summary")){
				Result="Pass - Infection Prevention Exam Summary table is present";
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Infection Prevention Exam Summary table is not present";
			}
		}
		return Result;
	}

	public static String verify_Facility(String expFac){
		Select droplist = new Select(driver.findElementById("ddlSelectedFacility")); 
		WebElement selected=droplist.getFirstSelectedOption();
		String actualFac=selected.getText();
		if(actualFac.equalsIgnoreCase(expFac)){
			Result="Pass - Selected Facility is "+expFac;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Facility was supposed to be "+expFac+" but it was "+actualFac;
		}
		return Result;
	}
}
