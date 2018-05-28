package TestFrameWork.Emulator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class Emulator_Verifications extends Unifia_Admin_Selenium {
	public static TestFrameWork.Unifia_Admin_Selenium UAS; 

	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	//[RK]-27June16
	public static boolean VerifyScanMsg(String ExpectScanMsg, Integer ExpScanCount) throws InterruptedException{
		Unifia_Admin_Selenium.isEmulator=true;
		String ActualScanMsg,ActualMsg;
		if (ExpScanCount.equals(1)){
			if (driverCount.equalsIgnoreCase("second")){
				driver2.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			}else{
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			}
			
			Thread.sleep(20000);
		}else{
			if (driverCount.equalsIgnoreCase("second")){
				driver2.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			}else{
				driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);	
			}
				
		Thread.sleep(1000);
		}
		//Verify ScanCount
		String ActScanCount=GF.GetTextfrmElement(GF.EmulatorX_txt_ScanCount);
		Integer ActScanCountInt=Integer.parseInt(ActScanCount);
		if (ExpScanCount.equals(ActScanCountInt)){
			Result= "Pass - Comparison of Scan count. Expected ="+ExpScanCount+"; Actual = "+ActScanCountInt+";";
			System.out.println(Result);
		}else{
			Thread.sleep(6000);
			ActScanCount=GF.GetTextfrmElement(GF.EmulatorX_txt_ScanCount);
			ActScanCountInt=Integer.parseInt(ActScanCount);
			if (ExpScanCount.equals(ActScanCountInt)){
				Result= "Pass - Comparison of Scan count. Expected ="+ExpScanCount+"; Actual = "+ActScanCountInt+";";
				System.out.println(Result);
			}else{
			UAS.resultFlag="#Failed!#";
			Result= "#Failed!# - Comparison of Scan count. Expected ="+ExpScanCount+"; Actual = "+ActScanCountInt+";";
			System.out.println(Result);
			}
		}	
		//Verify Scanner Message
		ActualScanMsg=GF.GetValfrmTextBox(GF.EmulatorX_txt_ScanMsg);
		ActualMsg=ActualScanMsg.trim();
		ActualMsg=ActualMsg.replaceAll("\\s+"," ");
		if (VerifyMsg(ExpectScanMsg,ActualMsg)){
			return true;
		}else{
			return false;
		}
	}
	//[RK]-27June16
	public static boolean VerifyMsg(String ExpectedValue, String ActualValue){
		Unifia_Admin_Selenium.isEmulator=true;
		//String Result;
		//Comparing expected and Actual Values	
		if (ExpectedValue.equalsIgnoreCase(ActualValue)){
			Result= "Pass - Comparison of Scan Message. Expected ="+ExpectedValue+"; Actual = "+ActualValue+";";
			System.out.println(Result);
			return true;
		}else{
			Result= "#Failed!# - Comparison of Scan Message. Expected ="+ExpectedValue+"; Actual = "+ActualValue+";";
			System.out.println(Result);
			return false;
		}	
	}
	
}
