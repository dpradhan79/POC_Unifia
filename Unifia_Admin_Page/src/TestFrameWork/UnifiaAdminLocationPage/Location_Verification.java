package TestFrameWork.UnifiaAdminLocationPage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class Location_Verification extends Unifia_Admin_Selenium  {
	//NR 18may15 updated elelement ID's throughout this file. Location changed from inline edit to a pop up form for new and editing resulting in all new element IDs
	//Also the element ids are the same for new locations and editing existing locations, so commenting out functions related to 'modify' and updating the names of 'new' to be generic.
	
	
	public static String Verify_LocationName(String LocationName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1500);
		
		Actual=driver.findElementById("Name").getAttribute("value");
		System.out.println("Actual Name value ="+Actual);

		if(LocationName.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - Name is "+Actual+" but it should be "+LocationName;
		}
		return Result;
	}

	
	public static String Verify_LocationType(String LocType) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1500);

		Actual=driver.findElementById("LocationTypeID_FK").getAttribute("value");
		switch (Actual) {
		case "-1":
			Actual="";
			break;
		case "1":
			Actual="Administration";
			break;
		case "2":
			Actual="Procedure Room";
			break;
		case "3":
			Actual="Scope Storage Area";
			break;
		case "4":
			Actual="Soiled Area";
			break;
		case "5":
			Actual="Reprocessor";
			break;
		case "6":
			Actual="Travel Cart";
			break;		
		case "7":
			Actual="Waiting Room";
			break;
		case "8":
			Actual="Bioburden Testing";
			break;
		case "9":
			Actual="Culturing";
			break;
		case "10":
			Actual="Reprocessing Room";
			break;
		case "11":
			Actual="Auto Leak Test";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Location Type value ="+Actual);

		if(LocType.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - Location Type is "+Actual+" but it should be "+LocType;
		}
		return Result;
	}

	public static String Verify_Confirmation() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(500);
		
		Actual=driver.findElementById("confirmYesNoMessage").getAttribute("value");
		System.out.println("Actual Name value ="+Actual);

		if(Actual.equalsIgnoreCase("Are you sure you want to change Location Type?")){
			Result="Pass";
		}else{
			Result="#Failed!# - Confirmation Text is "+Actual+" but it should be 'Are you sure you want to change Location Type?'";
		}
		return Result;
	}

	
	public static String Verify_LocationActive(String LocationName){
		
		ModLocationAct_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr[2]/td[text()='"+LocationName+"']//..").findElement(By.xpath("//input")).getAttribute("value");
		//ModLocationAct_Val=driver.findElementById(GridID).findElement(By.id("jqgrid_location_IsActive")).getAttribute("checked");
		return ModLocationAct_Val ;
		
		
	}
	
	/**
	 * 
	 *These functions were added by Jon to implement functions for complex testing scenarios
	 */
	public static String Verify_LocationFacility(String Facility){
		Select droplist = new Select(driver.findElementById("FacilityID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(Facility)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+Facility+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	

	public static String Verify_StorageCabinets(String Cabinets) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("StorageCabinetCount").getAttribute("value");
		System.out.println("Actual Storage Cabinet Count value ="+Actual+"; Expected value= "+Cabinets);

		if(Cabinets.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - StorageCabinetCount is "+Actual+" but it should be "+Cabinets;
		}
		return Result;
	}
	
	public static String Verify_AERModel(String AERModel) throws InterruptedException {
		Thread.sleep(500);
		Actual=driver.findElementById("AERDetail.Model").getAttribute("value");
		System.out.println("Actual AER Model value ="+Actual+"; Expected value= "+AERModel);

		if(AERModel.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - AERModel is "+Actual+" but it should be "+AERModel;
		}
		return Result;
	}
	
	public static String Verify_AERSerialNo(String AERSerialNo) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.SerialNumber").getAttribute("value");
		System.out.println("Actual AER Model value ="+Actual+"; Expected value= "+AERSerialNo);

		if(AERSerialNo.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - SerialNumber is "+Actual+" but it should be "+AERSerialNo;
		}
		return Result;
	}
	
	public static String Verify_DisinfectantCycles(String DisinfectantCycles) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.DisinfectantCycles").getAttribute("value");
		System.out.println("Actual AER Disinfectant Cycles value ="+Actual+"; Expected value= "+DisinfectantCycles);

		if(DisinfectantCycles.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - DisinfectantCycles is "+Actual+" but it should be "+DisinfectantCycles;
		}
		return Result;
	}
	
	public static String Verify_DisinfectantDays(String DisinfectantDays) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.DisinfectantDays").getAttribute("value");
		System.out.println("Actual AER Disinfectant Days value ="+Actual+"; Expected value= "+DisinfectantDays);

		if(DisinfectantDays.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - DisinfectantDays is "+Actual+" but it should be "+DisinfectantDays;
		}
		return Result;
	}
	
	public static String Verify_AirFilterCycles(String AirFilterCycles) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.AirFilterCycles").getAttribute("value");
		System.out.println("Actual AER Air Filter Cycles value ="+Actual+"; Expected value= "+AirFilterCycles);

		if(AirFilterCycles.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - AirFilterCycles is "+Actual+" but it should be "+AirFilterCycles;
		}
		return Result;
	}
	
	public static String Verify_AirFilterDays(String AirFilterDays) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.AirFilterDays").getAttribute("value");
		System.out.println("Actual AER Air Filter Days value ="+Actual+"; Expected value= "+AirFilterDays);

		if(AirFilterDays.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - AirFilterDays is "+Actual+" but it should be "+AirFilterDays;
		}
		return Result;
	}
	
	public static String Verify_WaterFilterCycles(String WaterFilterCycles) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.WaterFilterCycles").getAttribute("value");
		System.out.println("Actual AER Water Filter Cycles value ="+Actual+"; Expected value= "+WaterFilterCycles);

		if(WaterFilterCycles.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - WaterFilterCycles is "+Actual+" but it should be "+WaterFilterCycles;
		}
		return Result;
	}
	
	public static String Verify_WaterFilterDays(String WaterFilterDays) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.WaterFilterDays").getAttribute("value");
		System.out.println("Actual AER Water Filter Days value ="+Actual+"; Expected value= "+WaterFilterDays);

		if(WaterFilterDays.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - WaterFilterDays is "+Actual+" but it should be "+WaterFilterDays;
		}
		return Result;
	}
	
	public static String Verify_VaporFilterCycles(String VaporFilterCycles) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.VaporFilterCycles").getAttribute("value");
		System.out.println("Actual AER Vapor Filter Cycles value ="+Actual+"; Expected value= "+VaporFilterCycles);

		if(VaporFilterCycles.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - VaporFilterCycles is "+Actual+" but it should be "+VaporFilterCycles;
		}
		return Result;
	}
	
	public static String Verify_VaporFilterDays(String VaporFilterDays) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.VaporFilterDays").getAttribute("value");
		System.out.println("Actual AER Vapor Filter Days value ="+Actual+"; Expected value= "+VaporFilterDays);

		if(VaporFilterDays.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - VaporFilterDays is "+Actual+" but it should be "+VaporFilterDays;
		}
		return Result;
	}
	
	public static String Verify_DetergentCycles(String DetergentCycles) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.DetergentCycles").getAttribute("value");
		System.out.println("Actual AER Detergent Cycles value ="+Actual+"; Expected value= "+DetergentCycles);

		if(DetergentCycles.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - DetergentCycles is "+Actual+" but it should be "+DetergentCycles;
		}
		return Result;
	}
	
	public static String Verify_DetergentDays(String DetergentDays) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.DetergentDays").getAttribute("value");
		System.out.println("Actual AER Detergent Days value ="+Actual+"; Expected value= "+DetergentDays);

		if(DetergentDays.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - DetergentDays is "+Actual+" but it should be "+DetergentDays;
		}
		return Result;
	}
	
	public static String Verify_AlcoholCycles(String AlcoholCycles) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.AlcoholCycles").getAttribute("value");
		System.out.println("Actual AER Alcohol Cycles value ="+Actual+"; Expected value= "+AlcoholCycles);

		if(AlcoholCycles.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - AlcoholCycles is "+Actual+" but it should be "+AlcoholCycles;
		}
		return Result;
	}
	
	public static String Verify_AlcoholDays(String AlcoholDays) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.AlcoholDays").getAttribute("value");
		System.out.println("Actual AER Alcohol Days value ="+Actual+"; Expected value= "+AlcoholDays);

		if(AlcoholDays.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - AlcoholDays is "+Actual+" but it should be "+AlcoholDays;
		}
		return Result;
	}
	
	public static String Verify_PMCycles(String PMCycles) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.PMCycles").getAttribute("value");
		System.out.println("Actual AER PM Cycles value ="+Actual+"; Expected value= "+PMCycles);

		if(PMCycles.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - PMCycles is "+Actual+" but it should be "+PMCycles;
		}
		return Result;
	}
	
	public static String Verify_PMDays(String PMDays) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.PMDays").getAttribute("value");
		System.out.println("Actual AER PM Days value ="+Actual+"; Expected value= "+PMDays);

		if(PMDays.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - PMDays is "+Actual+" but it should be "+PMDays;
		}
		return Result;
	}
	
	public static String Verify_CycleTime(String CycleTime) throws InterruptedException {
		Thread.sleep(500);		
		Actual=driver.findElementById("AERDetail.CycleTime").getAttribute("value");
		System.out.println("Actual AER PM Cycles value ="+Actual+"; Expected value= "+CycleTime);

		if(CycleTime.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!# - CycleTime is "+Actual+" but it should be "+CycleTime;
		}
		return Result;
	}
	

	
	public static String Verify_Location_Facility_List_Options(String FacOptions){
		ArrayList<String> facilityLocations = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("FacilityID_FK"));
		for(WebElement option : droplist.getOptions()) {
			facilityLocations.add(option.getAttribute("text"));
		}
		
		String facilityLocationList = StringUtils.join(facilityLocations.toArray(), ", ");
		System.out.println("Facility Location List is " + facilityLocationList);
		Actual=facilityLocationList;
		if(Actual.equals(FacOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual Facility Options:  "+Actual+"  Expected Facility Options:  "+FacOptions;
		}
		return Result;
	}
	
	public static String Verify_LocationSSID(String SSID){
		Select droplist = new Select(driver.findElementById("AccessPointID_FK"));
		Actual=droplist.getFirstSelectedOption().getAttribute("text");
		if(Actual.equals(SSID)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Expected:  "+SSID+" , the Actual is:  "+Actual;
		}
		return Result;
	}
	
	public static String Verify_Location_SSID_List_Options(String SSIDOptions){
		ArrayList<String> SSIDLocations = new ArrayList<String>();
		Select droplist = new Select(driver.findElementById("AccessPointID_FK"));
		for(WebElement option : droplist.getOptions()) {
			SSIDLocations.add(option.getAttribute("text"));
		}
		
		String SSIDLocationList = StringUtils.join(SSIDLocations.toArray(), ", ");
		System.out.println("SSID Location List is " + SSIDLocationList);
		Actual=SSIDLocationList;
		if(Actual.equals(SSIDOptions)){
			Result="Pass";
		}else{
			Result="#Failed!#-  Actual SSID Options:  "+Actual+", Expected SSID Options:  "+SSIDOptions;
		}
		return Result;
	}
	
	public static String Verify_LTModel(String LTModel) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1500);
		Actual=driver.findElementById("LeakTesterDetail.Model").getAttribute("value");
		System.out.println("Actual Leak TestModel value ="+Actual);

		if(LTModel.equalsIgnoreCase(Actual)){
			Result="Pass - Leak Test Model is "+Actual;
		}else{
			Result="#Failed!# - Leak Test Model is "+Actual+" but it should be "+LTModel;
		}
		return Result;
	}
	
	public static String Verify_LTSernum(String LTSernum) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1500);
		
		Actual=driver.findElementById("LeakTesterDetail.SerialNumber").getAttribute("value");
		System.out.println("Actual Leak Test Serial Number value ="+Actual);

		if(LTSernum.equalsIgnoreCase(Actual)){
			Result="Pass - Leak Test Serial Number is "+Actual;
		}else{
			Result="#Failed!# - Leak Test Serial Number is "+Actual+" but it should be "+LTSernum;
		}
		return Result;
	}
}
