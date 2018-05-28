package TestFrameWork.UnifiaAdminScopeSafetyPage;

import java.util.concurrent.TimeUnit;
//import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.Select;
import TestFrameWork.Unifia_Admin_Selenium;

public class ScopeSafety_Actions extends Unifia_Admin_Selenium{

	public static String ModiBioTesting_Active_Value() throws InterruptedException{
		ScopBiobTest_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[2]/input").getAttribute("value");
		System.out.println(ScopBiobTest_Val);
		if(ScopBiobTest_Val==null){
			ScopBiobTest_Val="False";
		} else if(ScopBiobTest_Val.equalsIgnoreCase("true")) {
			ScopBiobTest_Val="True";
		}  else if(ScopBiobTest_Val.equalsIgnoreCase("false")) {
			ScopBiobTest_Val="False";
		}
		System.out.println("Bioburden Testing is set to ="+ScopBiobTest_Val);
		return ScopBiobTest_Val;
	}	
	
	public static String ModiCulturing_Active_Value() {
		ScopCult_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/input").getAttribute("value");
		System.out.println(ScopCult_Val);
		if (ScopCult_Val==null){
			//it is false
			ScopCult_Val="False";
		} else if(ScopCult_Val.equalsIgnoreCase("true")) {
			ScopCult_Val="True";
		}  else if(ScopCult_Val.equalsIgnoreCase("false")) {
			ScopCult_Val="False";
		}
		System.out.println("Culturing is set to ="+ScopCult_Val);
		return ScopCult_Val ;
	}	
	
	public static String Default_ScopeHangTime_Value() {
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		DefaultHangTime_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[4]").getAttribute("title");
		System.out.println("Hangtime is set to ="+DefaultHangTime_Val);
		return DefaultHangTime_Val;
	}	
	
	public static String GetGridID_ScopeSafety_To_Modify(String FacName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		GridID=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..").getAttribute("id");
		return GridID;
	}	
	
	public static void Select_ScopeSafety_To_Modify(String FacName){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement myElemment=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..");
		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}	
	
	public static void Modify_BiobTesting_Active(String ModBiobAct_Val,String Active){
		if(Active.equals("True")){
			if(ModBiobAct_Val.equals("True")){
				//Do nothing the Bioburden Testing is already set to active
			}else{
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[2]/button").click();
			}
		}else if(Active.equals("False")){
			if(ModBiobAct_Val.equals("True")){
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[2]/button").click();
			}else{
				//Do nothing the Bioburden Testing is already set to inactive
			}
		}
		
	}	

	public static void Modify_ScopeSafety_HangTime(String GridID,String ModHang_Time){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_HangTime").clear();
		driver.findElementById(GridID+"_HangTime").sendKeys(ModHang_Time);
	}	
	

	
	public static void Search_FacilityName(String FacName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(FacName);
		Thread.sleep(1500);
		driver.findElementById("gs_Name").sendKeys(Keys.RETURN);
		Thread.sleep(1500);
	}		
	
	
	public static void Modify_Culturing_Active(String ModCultAct_Val,String Active){
		if(Active.equals("True")){
			if(ModCultAct_Val.equals("True")){
				//Do nothing the Culturing is already set to active
			}else{
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();

			}
		}else if(Active.equals("False")){
			if(ModCultAct_Val.equals("True")){
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
			}else{
				//Do nothing the Culturing is already set to inactive
			}
			
		}
	}	
	
	
	public static void Cancel_ScopeSafety_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scope_safety_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(500);
	}
	
	public static void Save_ScopeSafety_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scope_safety_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(500);
	}

}
