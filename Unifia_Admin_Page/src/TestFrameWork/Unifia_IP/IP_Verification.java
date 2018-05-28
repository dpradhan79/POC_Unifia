package TestFrameWork.Unifia_IP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;

public class IP_Verification extends Unifia_Admin_Selenium {
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	private static TestFrameWork.Emulator.GetIHValues IHV;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	
	public static void verifyDateFormatINSRMScreen(){
		
		if (driver.findElements( By.xpath("//*[@id='smGrid']/div[2]/table/tbody/tr[1]/td[2]/div/span")).size() != 0){
			String dateValue=driver.findElementByXPath("//*[@id='smGrid']/div[2]/table/tbody/tr[1]/td[2]/div/span").getText();
			String Description = "Verifying date format in SRM page in workFlowStartDate column";
			String Expected= "Date format should be like "+UAS.expDateExample+" or "+UAS.expDateExample2;
			if (GF.isDateinValidFormat(UAS.expDateFormat, dateValue)){
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, "Passed - The date format is correct");
			}else{
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, "#Warning!# - The actual date displayed is "+dateValue+" which is not in valid date format. Bug 12108");
			}	
		}
	}
	
	public static void verifyDateFormat(String dateFieldName, String actualDate){
		String Description = "Verify date format for "+dateFieldName;
		String Expected = "Date format should be like "+UAS.expDateExample+" or "+UAS.expDateExample2;
		
		if(!(actualDate.equalsIgnoreCase("")||actualDate==null||actualDate.equalsIgnoreCase(" ")||actualDate.equalsIgnoreCase("-"))){
			if (GF.isDateinValidFormat(UAS.expDateFormat, actualDate)){
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, "Passed - The date format is correct");
			}else{
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, "#Warning!# - The actual date displayed is "+actualDate+" which is not in valid date format. Bug 12108");
			}	
		}
	}
	
	public static void verifyTimeFormat(String timeFieldName, String xpath){
		String actualtime;
		String Description = "Verify time format for "+timeFieldName;
		String Expected = "Time format should be like "+UAS.expTimeFormatExample;
		
		if (driver.findElements(By.xpath(xpath)).size() != 0){
			actualtime=driver.findElementByXPath(xpath).getText();
			if(!(actualtime.equalsIgnoreCase("")||actualtime==null||actualtime.equalsIgnoreCase(" ")||actualtime.equalsIgnoreCase("-"))){
				if (GF.isDateinValidFormat(UAS.expTimeFormat, actualtime)){
					IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, "Passed - The time format is correct");
					System.out.println ("Passed");
				}else{
					IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, "#Warning!# - The actual time displayed is "+actualtime+" which is not in valid time format. Bug 12108");
					System.out.println ("Failed");
				}	
			}
		}
	}
	
}
