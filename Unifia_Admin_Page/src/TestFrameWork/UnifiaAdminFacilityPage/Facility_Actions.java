package TestFrameWork.UnifiaAdminFacilityPage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class Facility_Actions extends Unifia_Admin_Selenium {

	public static void Add_New_Facility(){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_facility_iladd").click();
		//jqgrid_facility_iladd
	}
	
	public static void Refresh_Facility_Grid(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_facility").click();
		}
	
	public static void Cancel_Facility_Edit(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_facility_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

	}
	
	public static void Save_Facility_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_facility_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(1500);

	}
	
	public static void Search_Facility_ByName(String FacName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(FacName);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(Keys.RETURN);
		Thread.sleep(1500);
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
	
	public static void Search_Facility_ByAbbreviation(String FacAbbrv){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Abbreviation").clear();
		driver.findElementById("gs_Abbreviation").sendKeys(FacAbbrv);
		driver.findElementById("gs_Abbreviation").sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public static void Search_Facility_ByCustomerNumber(String FacCustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_CustomerNumber").clear();
		driver.findElementById("gs_CustomerNumber").sendKeys(FacCustNum);
		driver.findElementById("gs_CustomerNumber").sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public static void Select_Facility_Rows(String Rows){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	Select droplist = new Select(driver.findElementByClassName("ui-pg-selbox"));   
	droplist.selectByVisibleText(Rows);
	}
	
	public static void Enter_New_Facility_Name(String FacilityName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_Name").sendKeys(FacilityName);
		//System.out.println("Facility name sent = "+FacilityName);
	}
	
	public static void Enter_New_Facility_Abbreviation(String FacilityAbbrv){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_Abbreviation").sendKeys(FacilityAbbrv);
		//System.out.println("Abbreviation name sent = "+FacilityAbbrv);
	}
	
	public static void Enter_New_Facility_CustomerNumber(String CustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_CustomerNumber").sendKeys(CustNum);
		//System.out.println("Customer number sent = "+CustNum);
	}
	
	public static void Enter_New_Facility_HangTime(String HangTime){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_HangTime").clear();
		driver.findElementById("-1_HangTime").sendKeys(HangTime);
		//System.out.println("Scope Hang Time sent = "+HangTime);
	}
	
	public static void Selct_New_Facility_Active (String Active){
		if(Active.equals("True")){
			//Select droplist = new Select(driver.findElementById("-1_Active"));   
			//droplist.selectByVisibleText("Yes");
		}else if(Active.equals("False")){
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[6]/button").click();
			//driver.findElementById("-1_IsActive").click();   
		}
		
	}
	
	public static void Enter_New_Facility_USSN(String USSN){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_SerialNumber").sendKeys(USSN);
	}
	
	
	public static void Selct_New_Facility_Primary (boolean isPrimary){
		if(isPrimary){
			driver.findElementByXPath("//*[@id='-1']/td[5]/button").click();
		}
	}
	
	
	/**
	 * WebElement myElemment=driver.findElementByXPath("//td[text()='Jon-Test']");
	 * GridID=driver.findElementByXPath("//td[text()='Jon-Test']//..").getAttribute("id");
	 * isActive=driver.findElementByXPath("//td[text()='Jon-Test']//..").findElement(By.xpath("//td[4]/input")).getAttribute("value");
	**/
	
	//Facility_Active_Value needs to be run prior to running Modify_Facility_Active
	//so that you know if the facility is active or not
	public static String Facility_Active_Value(String FacName){
		
		//ModFacAct_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..").findElement(By.xpath("//td[4]/input")).getAttribute("value");
		// deleted [2] after tr -LCS 3/27/15
		//ModFacAct_Val=driver.findElementByXPath("//div[2]/div/div[1]/div[2]/div[2]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+FacName+"']//..").findElement(By.xpath("//td[4]/input")).getAttribute("value");
		//ModFacAct_Val=driver.findElementById(GridID+"_IsActive").getAttribute("checked");
		//ModFacAct_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[5]").getAttribute("checked");
												// driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..")
		String ModFacAct_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..").findElement(By.xpath("//td[6]/input")).getAttribute("value");
		System.out.println(ModFacAct_Val);
		if (ModFacAct_Val==null){
			//it is false
			ModFacAct_Val="False";
		} else if(ModFacAct_Val.equalsIgnoreCase("true")) {
			ModFacAct_Val="True";
		}  else if(ModFacAct_Val.equalsIgnoreCase("false")) {
			ModFacAct_Val="False";
		}
		System.out.println("ModFacAct_Val="+ModFacAct_Val);

		return ModFacAct_Val ;
		
		
	}
	
	public static String GetGridID_Facility_To_Modify(String FacName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		Thread.sleep(5000);
		GridID=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..").getAttribute("id");
		// deleted [2] after tr -LCS 3/27/15
		//GridID=driver.findElementByXPath("//div[2]/div/div[1]/div[2]/div[2]/div/div/div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+FacName+"']//..").getAttribute("id");
		//GridID=driver.findElementByXPath("//div[2]/div/div[1]/div[2]/div[2]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+FacName+"']//..").getAttribute("id");
		return GridID;
		
		
	}
	
	public static void Select_Facility_To_Modify(String FacName){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement myElemment=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..");
		// deleted [2] after tr -LCS 3/27/15
		//WebElement myElemment=driver.findElementByXPath("//div[2]/div/div[1]/div[2]/div[2]/div/div/div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+FacName+"']//..");

		//		WebElement myElemment=driver.findElementByXPath("//div[2]/div/div[1]/div[2]/div[2]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+FacName+"']");
		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}
	
	public static void Modify_Facility_Name(String GridID, String NewFacName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_Name").clear();
		driver.findElementById(GridID+"_Name").sendKeys(NewFacName);
	}
	
	public static void Modify_Facility_Abbreviation(String GridID, String NewAbbrv){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_Abbreviation").clear();
		driver.findElementById(GridID+"_Abbreviation").sendKeys(NewAbbrv);
	}
	
	public static void Modify_Facility_CustomerNumber(String GridID, String NewCustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_CustomerNumber").clear();
		driver.findElementById(GridID+"_CustomerNumber").sendKeys(NewCustNum);
	}
	//Validate element ID for HangTime
	public static void Modify_Facility_HangTime(String GridID,String ModHang_Time){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_HangTime").clear();
		driver.findElementById(GridID+"_HangTime").sendKeys(ModHang_Time);
	}
	
	public static void Modify_Facility_Active(String GridID, String ModFacAct_Val,String Active){
		if(Active.equals("True")){
			if(ModFacAct_Val.equals("True")){
				//Do nothing the facility is already set to active
			}else{
				//driver.findElementById(GridID+"_IsActive").click();
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[6]/button").click();

			}
			
		}else if(Active.equals("False")){
			if(ModFacAct_Val.equals("True")){
				//driver.findElementById(GridID+"_IsActive").click();
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[6]/button").click();
			}else{
				//Do nothing the facility is already set to inactive
			}
			
		}else{
			//driver.findElementById(GridID+"_IsActive").click();
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[6]/button").click();
		}
	}

	public static void Enter_New_Facility_SerialNum(String SerialNum) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Thread.sleep(1000);
		driver.findElementById("-1_SerialNumber").sendKeys(SerialNum);
	}
	
	public static void Modify_Facility_SerialNum(String GridID, String SerialNumber) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Thread.sleep(1000);
		driver.findElementById(GridID+"_SerialNumber").clear();
		driver.findElementById(GridID+"_SerialNumber").sendKeys(SerialNumber);
	}
	
	public static void Select_New_Facility_Prime (String Active){
		if(Active.equals("True")){
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[5]/button").click();
		}else if(Active.equals("False")){
			//Do nothing
		}
	}
	
	public static void Modify_Facility_Prime(String GridID, String ModFacPrime_Val,String IsPrime) throws InterruptedException{
		Thread.sleep(1000);
		if(IsPrime.equals("True")){
			if(ModFacPrime_Val.equals("True")){
				//Do nothing the facility is already set to Primary
			}else{
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[5]/button").click();
			}
		}else if(IsPrime.equals("False")){
			if(ModFacPrime_Val.equals("True")){
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[5]/button").click();
			}else{
				//Do nothing the facility is already set to non primary
			}
		}
	}
	
	public static void clearFacilitySearch() throws InterruptedException{
		Thread.sleep(1000);
		driver.findElementById("gs_Name").clear();
		Thread.sleep(1000);
		driver.findElementById("gs_Abbreviation").clear();
		Thread.sleep(1000);
		driver.findElementById("gs_SerialNumber").clear();
	}
	
}
