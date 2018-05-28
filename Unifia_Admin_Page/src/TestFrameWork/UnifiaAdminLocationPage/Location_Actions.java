package TestFrameWork.UnifiaAdminLocationPage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class Location_Actions extends Unifia_Admin_Selenium {
	//NR 18may15 updated elelement ID's throughout this file. Location changed from inline edit to a pop up form for new and editing resulting in all new element IDs
	//Also the element ids are the same for new locations and editing existing locations, so commenting out functions related to 'modify' and updating the names of 'new' to be generic.

	public static void Add_New_Location() throws InterruptedException{ 
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("add_jqgrid_location").click(); 
		Thread.sleep(500);
	}
	
	public static void Refresh_Location_Grid() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_location").click();
		Thread.sleep(1500);

		}
	
	public static void Cancel_Location_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		try{
			driver.findElementById("cData").click();
		}catch(NoSuchElementException e){
			System.out.println("Cancel button is not found");
		}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(1000);

	}
	
	public static void Save_Location_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Thread.sleep(3000);
		driver.findElementById("sData").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(1000);

	}
	
	public static void Search_Location_ByName(String LocationName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(LocationName);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(Keys.RETURN);
		Thread.sleep(1500);
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
	
	public static void Search_Location_ByFacility(String FacName) throws InterruptedException{
		Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_FacilityID_FK"));   
		droplist.selectByVisibleText("");
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_FacilityID_FK"));   
		droplist2.selectByVisibleText(FacName);

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	//	driver.findElementById("gs_FacilityID_FK").sendKeys(Keys.RETURN);
		Thread.sleep(5000);
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	}
	
	
	public static void Select_Location_Rows(String Rows) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementByClassName("ui-pg-selbox"));   
		droplist.selectByVisibleText(Rows);
		Thread.sleep(1000);

	}
	
	public static void Enter_Location_Name(String LocationName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("Name").sendKeys(LocationName);
		//System.out.println("Location name sent = "+LocationName);
	}
	
	public static void Selct_New_Location_Active (String Active){
		if(Active.equals("True")){
			//do nothing, active is set to true by default on new locations.  

		}else if(Active.equals("False")){
			driver.findElementByXPath("//div/form/table/tbody/tr[6]/td[2]/button").click();

			//driver.findElementById("IsActive").click();   
		}
	}
	
	public static void Selct_Location_Type(String LocType){
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("LocationTypeID_FK"));   
		droplist.selectByVisibleText(LocType);
		//System.out.println("Location Type sent = "+LocType);
	}

	public static void Selct_Location_Facility(String LocFacility){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("FacilityID_FK"));   
		droplist.selectByVisibleText(LocFacility);
		//System.out.println("Location Facility sent = "+LocFacility);
	}
	
	public static void Selct_Location_SSID(String SSID){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("AccessPointID_FK"));   
		droplist.selectByVisibleText(SSID);
		//System.out.println("Location SSID sent = "+SSID);
	}

	public static void Confirmation_Window(String response) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		Thread.sleep(3000);
		//driver.switchTo().defaultContent(); //switches out of the iframe

		if(response.equalsIgnoreCase("Yes")){
			if (driver.findElementByXPath("//div[5]/div[3]/div/button[1]").isDisplayed()){
				driver.findElementByXPath("//div[5]/div[3]/div/button[1]").click();
			}
		} else if(response.equalsIgnoreCase("No")){
			if (driver.findElementByXPath("//div[5]/div[3]/div/button[2]").isDisplayed()){
				driver.findElementByXPath("//div[5]/div[3]/div/button[2]").click();
			}
		}
		//System.out.println("user clicked "+response+" on the confirmation window");
	}

	public static void Enter_StorageCabinets(String Cabinets){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("StorageCabinetCount").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("StorageCabinetCount").sendKeys(Cabinets);
		//System.out.println("Storage Cabinet Count sent = "+Cabinets);
	}
	
	public static void Enter_AERModel(String AERModel){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.Model").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		driver.findElementById("AERDetail.Model").sendKeys(AERModel);
		System.out.println("AER Model sent = "+AERModel);
	}
	
	public static void Enter_AERSerialNo(String AERSerialNo){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.SerialNumber").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.SerialNumber").sendKeys(AERSerialNo);
		System.out.println("AER Serial Number sent = "+AERSerialNo);
	}
	
	public static void Enter_DisinfectantCycles(String DisinfectantCycles){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.DisinfectantCycles").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.DisinfectantCycles").sendKeys(DisinfectantCycles);
		//System.out.println("AER Disinfectant Cycles sent = "+DisinfectantCycles);
	}
	
	public static void Enter_DisinfectantDays(String DisinfectantDays){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.DisinfectantDays").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.DisinfectantDays").sendKeys(DisinfectantDays);
		//System.out.println("AER Disinfectant Days sent = "+DisinfectantDays);
	}
	
	public static void Enter_AirFilterCycles(String AirFilterCycles){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.AirFilterCycles").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.AirFilterCycles").sendKeys(AirFilterCycles);
		//System.out.println("AER Air Filter Cycles sent = "+AirFilterCycles);
	}
	
	public static void Enter_AirFilterDays(String AirFilterDays){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.AirFilterDays").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.AirFilterDays").sendKeys(AirFilterDays);
		//System.out.println("AER Air Filter Days sent = "+AirFilterDays);
	}
	
	public static void Enter_WaterFilterCycles(String WaterFilterCycles){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.WaterFilterCycles").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.WaterFilterCycles").sendKeys(WaterFilterCycles);
		//System.out.println("AER Water Filter Cycles sent = "+WaterFilterCycles);
	}
	
	public static void Enter_WaterFilterDays(String WaterFilterDays){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.WaterFilterDays").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.WaterFilterDays").sendKeys(WaterFilterDays);
		//System.out.println("AER Water Filter Days sent = "+WaterFilterDays);
	}
	
	public static void Enter_VaporFilterCycles(String VaporFilterCycles){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.VaporFilterCycles").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.VaporFilterCycles").sendKeys(VaporFilterCycles);
		//System.out.println("AER Vapor Filter Cycles sent = "+VaporFilterCycles);
	}
	
	public static void Enter_VaporFilterDays(String VaporFilterDays){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.VaporFilterDays").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.VaporFilterDays").sendKeys(VaporFilterDays);
		//System.out.println("AER Vapor Filter Days sent = "+VaporFilterDays);
	}
	
	public static void Enter_DetergentCycles(String DetergentCycles){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.DetergentCycles").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.DetergentCycles").sendKeys(DetergentCycles);
		//System.out.println("AER Detergent Cycles sent = "+DetergentCycles);
	}
	
	public static void Enter_DetergentDays(String DetergentDays){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.DetergentDays").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.DetergentDays").sendKeys(DetergentDays);
		//System.out.println("AER Detergent Days sent = "+DetergentDays);
	}
	
	public static void Enter_AlcoholCycles(String AlcoholCycles){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.AlcoholCycles").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.AlcoholCycles").sendKeys(AlcoholCycles);
		//System.out.println("AER Alcohol Cycles sent = "+AlcoholCycles);
	}
	
	public static void Enter_AlcoholDays(String AlcoholDays){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.AlcoholDays").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.AlcoholDays").sendKeys(AlcoholDays);
		//System.out.println("AER Alcohol Days sent = "+AlcoholDays);
	}
	
	public static void Enter_PMCycles(String PMCycles){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.PMCycles").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.PMCycles").sendKeys(PMCycles);
		//System.out.println("AER PM Cycles sent = "+PMCycles);
	}
	
	public static void Enter_PMDays(String PMDays){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.PMDays").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.PMDays").sendKeys(PMDays);
		//System.out.println("AER PM Days sent = "+PMDays);
	}
	
	public static void Enter_CycleTime(String CycleTime){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.CycleTime").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("AERDetail.CycleTime").sendKeys(CycleTime);
		//System.out.println("AER Cycle time sent = "+CycleTime);
	}

	public static String GetGridID_Location_To_Modify(String LocationName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		GridID=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+LocationName+"']//..").getAttribute("id");
		Thread.sleep(500);

		return GridID;
		
		
	}
	
	public static void Select_Location_To_Modify(String LocationName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(500);

		WebElement myElemment=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+LocationName+"']//..");
		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
		Thread.sleep(750);

	}
	
/**	public static void Modify_Location_Name(String GridID, String NewLocationName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_Name").clear();
		driver.findElementById(GridID+"_Name").sendKeys(NewLocationName);
	}
	public static void Modify_Location_SSID(String GridID, String NewLocationSSID){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_AccessPointID_FK"));   
		droplist.selectByVisibleText(NewLocationSSID);
		System.out.println("Location AccessPoint sent = "+NewLocationSSID);
	}**/
	
	//Location_Active_Value needs to be run prior to running Modify_Location_Active
	//so that you know if the location is active or not
	public static String Location_Active_Value(){
		//ModLocationAct_Val=null;
		ModLocationAct_Val=driver.findElementById("IsActive").getAttribute("checked");
		System.out.println(ModLocationAct_Val);
		if (ModLocationAct_Val==null){
			//it is false
			ModLocationAct_Val="False";
		} else {
			ModLocationAct_Val="True";
		}
		return ModLocationAct_Val ;
	}	
	
	
	public static void Modify_Location_Active(String ModLocationAct_Val,String Active){
		System.out.println("Active="+Active+" ModLocationAct_Val="+ModLocationAct_Val);
 
		if(Active.equalsIgnoreCase("True")){
			if(ModLocationAct_Val.equalsIgnoreCase("True")){
				System.out.println("Do nothing the Location is already set to active");
				//Do nothing the Location is already set to active
			}else{
				driver.findElementByXPath("//div/form/table/tbody/tr[6]/td[2]/button").click();

				//driver.findElementById("IsActive").click();   
				System.out.println("Click to make the Location active");

			}
			
		}else if(Active.equalsIgnoreCase("False")){
			if(ModLocationAct_Val.equalsIgnoreCase("True")){
				driver.findElementByXPath("//div/form/table/tbody/tr[6]/td[2]/button").click();
				//driver.findElementById("IsActive").click();  
				System.out.println("Click to make the Location inactive");

			}else{
				System.out.println("Do nothing the Location is already set to inactive");
				//Do nothing the Location is already set to inactive
			}
			
		}else{
			driver.findElementByXPath("//div/form/table/tbody/tr[6]/td[2]/button").click();
			//driver.findElementById("IsActive").click();   
		}
		System.out.println("Active="+Active+" ModLocationAct_Val="+ModLocationAct_Val);

	}
	
	public static void enter_LTModel(String LTModel){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("LeakTesterDetail.Model").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("LeakTesterDetail.Model").sendKeys(LTModel);
	}

	public static void enter_LTSerNum(String LTSerialNumber){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("LeakTesterDetail.SerialNumber").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("LeakTesterDetail.SerialNumber").sendKeys(LTSerialNumber);
	}
	
	public static String getLTModel(){
		String LTModel=driver.findElementById("LeakTesterDetail.Model").getAttribute("value");
		return LTModel;
	}
	
	public static String getLTSerNum(){
		String LTSerNum=driver.findElementById("LeakTesterDetail.SerialNumber").getAttribute("value");
		return LTSerNum;
	}
	
/**	public static void Selct_Modify_Location_Type(String GridID, String LocType){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_LocationTypeID_FK"));   
		droplist.selectByVisibleText(LocType);
		System.out.println("Location Type sent = "+LocType);
	}
	
	public static void Selct_Modify_Location_Facility(String GridID, String LocFacility){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_FacilityID_FK"));   
		droplist.selectByVisibleText(LocFacility);
		System.out.println("Location Facility sent = "+LocFacility);
	} **/
}
