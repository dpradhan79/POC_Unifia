package TestFrameWork.Unifia_SRM_WorkFlowDetails;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;
import TestFrameWork.UnifiaAdminUserPage.*;



public class WorkFlowDetails_Actions extends Unifia_Admin_Selenium{
	public static GeneralFunc SE_Gen;
	public static User_Actions UA;
	public static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage dbp;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.workFlowDetailsTestData wfTD;
	
	public static void Cancel(String Changes) throws AWTException, InterruptedException{
		/*driver.findElementById("cancelButton").click();*/
		Thread.sleep(500);
		SE_Gen.scrollDownnClickBtn("cancelButton");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Thread.sleep(3000);
		System.out.println("yes");
		if(Changes.equalsIgnoreCase("Yes")){
			//UA.RobotSendKeys(KeyEvent.VK_ENTER);
			Alert alert=Unifia_Admin_Selenium.driver.switchTo().alert();
			alert.accept();
		}
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Thread.sleep(2000);
	}
	
	public static void Save() throws InterruptedException{
		Thread.sleep(500);
		SE_Gen.scrollDownnClickBtn("submitButton");
		//driver.findElementById("submitButton").click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	public static void selectSerNum(String serNum) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath(dbp.scopeSerNum));   
		droplist.selectByVisibleText(serNum);
		Thread.sleep(500);
	}
	
	
	public static void UpdateProcedureRoom(String Location) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("ProcedureRoom"));   
		droplist.selectByVisibleText(Location);
		Thread.sleep(500);
	}

	public static void UpdateExamDate(String Date) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("ExamDateTime").sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);		
		Thread.sleep(500);
	}

	public static void UpdatePRInStaff(String Staff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("StaffID"));   
		droplist.selectByVisibleText(Staff);
		Thread.sleep(500);
	}

	public static void UpdatePhysician(String staff) throws InterruptedException{
		Thread.sleep(500);
		String eachPhy,selectedOptions;
		Select droplist = new Select(driver.findElementById("PhysicianID"));
		//Check list box is already selected with items and remove them before selecting new items
		selectedOptions=driver.findElementByXPath("//*[@id='PhysicianID']/following-sibling::p/span").getText();
		driver.findElementByXPath("//*[@id='PhysicianID']/following-sibling::p/span").click();
		if (!selectedOptions.trim().equalsIgnoreCase("")){
			selectMultiPhysicians(selectedOptions);
		}
		//Select new items in the drop down if staff is blank remove the physician ids
		if (!staff.trim().equalsIgnoreCase("")){
			selectMultiPhysicians(staff);
		}
		driver.findElementByXPath("//*[@id='PhysicianID']/following-sibling::p/span").click();
	}
	
	public static void selectMultiPhysicians(String options){
		String eachPhy,selPhysInd;
		Select droplist = new Select(driver.findElementById("PhysicianID"));
		String[] selPhys=options.split(",");
		for (int selPhyCntr=0; selPhyCntr<=selPhys.length-1;selPhyCntr++){
			selPhysInd = selPhys[selPhyCntr];
			for (int i=1;i<= droplist.getOptions().size();i++){
				eachPhy=driver.findElementByXPath(wfTD.physDropDown+"/li["+i+"]/Label").getText();
				if (selPhysInd.trim().equalsIgnoreCase(eachPhy.trim())){
					driver.findElementByXPath(wfTD.physDropDown+"/li["+i+"]/Label").click();
					break;
				}
			}
		}
	}

	public static void UpdatePatient(String Patient) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("PatientIDText").sendKeys(Keys.chord(Keys.CONTROL, "a"), Patient);		
		Thread.sleep(500);
	}
	
	public static void UpdateProcStart(String Date) throws InterruptedException{
		Thread.sleep(500);
		//driver.findElementById("CycleDetails_ProcedureRoomFields_5__FieldValue").sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);		
		driver.findElementById("ProcedureStartDate").sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);		
		Thread.sleep(500);
	}

	public static void UpdateProcEnd(String Date) throws InterruptedException{
		Thread.sleep(500);
		//driver.findElementById("CycleDetails_ProcedureRoomFields_6__FieldValue").sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);		
		driver.findElementById("ProcedureEndDate").sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);		
		Thread.sleep(500);
	}

	public static void UpdatePreClean(String PreCleanStatus) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("PrecleanComplete"));   
		droplist.selectByVisibleText(PreCleanStatus);
		Thread.sleep(500);
	}
	
	public static void UpdatePreCleanStaff(String Staff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("PreCleanStaffID"));   
		droplist.selectByVisibleText(Staff);
		Thread.sleep(500);
	}

	//Soiled Area:
	public static void UpdateSoiledArea(String Location) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("SoiledArea"));   
		droplist.selectByVisibleText(Location);
		Thread.sleep(500);
	}

	public static void UpdateSoiledDate(String Date) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("LTDateTime").sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);		
		Thread.sleep(500);
	}

	public static void UpdateSoiledStaff(String Staff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("LTMCStaff"));   
		droplist.selectByVisibleText(Staff);
		Thread.sleep(500);
	}

	public static void UpdateLTStatus(String LTStatus) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("LeakTestStatus"));   
		droplist.selectByVisibleText(LTStatus);
		Thread.sleep(500);
	}

	public static void UpdateMCStart(String Date) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("ManualCleanStart").sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);	
		Thread.sleep(1000);
		driver.findElementById("ManualCleanStart").sendKeys(Keys.TAB);
		Thread.sleep(2000);
	}

	public static void UpdateMCEnd(String Date) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("ManualCleanEnd").sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);		
		Thread.sleep(1000);
		driver.findElementById("ManualCleanEnd").sendKeys(Keys.TAB);
		Thread.sleep(2000);
	}

	//Bioburden:
	public static void UpdateBioStatus(String BioStatus) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("BioburdenResult"));   
		droplist.selectByVisibleText(BioStatus);
		Thread.sleep(500);
	}

	public static void UpdateBioScannedResult(String BioStatus) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("BioburdenValue"));   
		droplist.selectByVisibleText(BioStatus);
		Thread.sleep(500);
	}

	public static void UpdateBioKeyResult(String BioStatus) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("BioburdenKeyvalue").sendKeys(Keys.chord(Keys.CONTROL, "a"), BioStatus);		
		Thread.sleep(500);
	}
	
	public static void UpdateBioStaff(String Staff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("BioburdenStaff"));   
		droplist.selectByVisibleText(Staff);
		Thread.sleep(500);
	}

	public static void EnterComment(String Comment) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("comments").clear();
		driver.findElementById("comments").sendKeys(Comment);		
		Thread.sleep(1000);
	}
	
	//Reprocessing Area
	public static void updateReprocessingArea(String location) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("Reprocessor"));   
		droplist.selectByVisibleText(location);
		Thread.sleep(500);
	}
	
	public static void updateReprocessingDate(String date) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("ReprocessingDateTime").sendKeys(Keys.chord(Keys.CONTROL, "a"), date);		
		Thread.sleep(500);
	}
	
	public static void updateReasonforReprocessing(String reasonForRepro) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("ReprocessingReason"));   
		droplist.selectByVisibleText(reasonForRepro);
		Thread.sleep(500);
	}
	
	public static void updateScopeInStaff(String scopeInStaff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("ScanInStaff"));   
		droplist.selectByVisibleText(scopeInStaff);
		Thread.sleep(500);
	}
	public static void updateScopeInStaffDate(String scopeInDate) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("ScanInDateTime").sendKeys(Keys.chord(Keys.CONTROL, "a"), scopeInDate);		
		Thread.sleep(500);
	}
	
	public static void updateScopeOutStaff(String scopeOuttaff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("ScanOutStaff"));   
		droplist.selectByVisibleText(scopeOuttaff);
		Thread.sleep(500);
	}
	
	public static void UpdateScopeOutStaffDate(String scopeOutDate) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("ScanOutDateTime").sendKeys(Keys.chord(Keys.CONTROL, "a"), scopeOutDate);		
		Thread.sleep(500);
	}
	
	//Culture results
	public static void UpdateCultureResult(String result) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("CultureStatus"));   
		droplist.selectByVisibleText(result);
		Thread.sleep(500);
	}
	
	public static String getChevronColor(String location, String refNo){
		String text=Unifia_Admin_Selenium.driver.findElementByXPath("//a[@id='details_"+refNo+"']/../../td/div[@title='"+location+"']").getAttribute("class");
		String classStatus=text.split(" ")[1];
		System.out.println("WorkActivity for Location "+location+" and RefNo "+refNo+" is "+classStatus);
		
		String script = "return window.getComputedStyle(document.querySelector('.work_activity."+classStatus+"'),':before').getPropertyValue('background-color')";
		JavascriptExecutor js = (JavascriptExecutor)Unifia_Admin_Selenium.driver;
		String color = (String) js.executeScript(script);
		System.out.println("Chevron color is "+color);
		return color;
	}
	
	public static boolean getStatusOfLocation(String location){
		boolean incompleteWorkFlowFlag=false;
		if(location.contains("Bioburden")){
			if(driver.findElementById("BioburdenResult").getAttribute("value").equalsIgnoreCase("0")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("BioburdenStaff").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
		}else if(location.contains("Procedure Room")){
			if(driver.findElementById("ProcedureRoom").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ExamDateTime").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("StaffID").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("PatientIDText").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("PhysicianID").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ProcedureStartDate").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ProcedureEndDate").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("PreCleanStaffID").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}			
		}else if(location.contains("Reprocessor")){
			if(driver.findElementById("Reprocessor").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ScanInDateTime").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ReprocessingReason").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ScanInStaff").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ScanOutDateTime").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ScanOutStaff").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
		}else if(location.contains("Sink")){
			if(driver.findElementById("SoiledArea").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("LTMCStaff").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("LeakTestStatus").getAttribute("value").equalsIgnoreCase("0")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("LTDateTime").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ManualCleanStart").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
			if(driver.findElementById("ManualCleanEnd").getAttribute("value").equalsIgnoreCase("")){
				incompleteWorkFlowFlag=true;
			}
		}else if(location.contains("Cultur")){
			if(driver.findElementById("CultureStatus").getAttribute("value").equalsIgnoreCase("8")||driver.findElementById("CultureStatus").getAttribute("value").equalsIgnoreCase("0")){
				incompleteWorkFlowFlag=true;
			}
		}
		return incompleteWorkFlowFlag;
	}
}
