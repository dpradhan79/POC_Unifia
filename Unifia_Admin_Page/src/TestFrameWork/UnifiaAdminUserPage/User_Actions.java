package TestFrameWork.UnifiaAdminUserPage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import DailyDashBoard.QvTabsAuthorization;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Verification;

public class User_Actions extends Unifia_Admin_Selenium {
	public static TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public static LandingPage_Actions SE_LA;
	public static User_Actions ua;
	public static LandingPage_Verification SE_LV;

	public static void Refresh_User_Grid()throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_user").click();
		Thread.sleep(1000);
		}
	
	public static void Cancel_User_Edit()throws InterruptedException, AWTException{
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		if(!ClickElementbyID("cData")){
			System.out.println("could not click on cancel button");			
			RobotSendKeys(KeyEvent.VK_ESCAPE);
		}
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(1000);
	}
	
	public static void Save_User_Edit()throws InterruptedException, AWTException{
		System.out.println("Save User Edit");
		Thread.sleep(500);
		if(!ClickElementbyID("sData")){
			System.out.println("could not click on save button");
			RobotSendKeys(KeyEvent.VK_ENTER);
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(1500);
	}
	
	public static void ClearAllSearchCriteria() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Thread.sleep(2000);
		Select droplist = new Select(driver.findElementById("gs_StaffID_FK"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
/**		Select droplist2 = new Select(driver.findElementById("gs_IsActive"));   
		droplist2.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);**/
		Thread.sleep(1000);
	}
	
	public static void Search_User_ByStaffName(String StaffName) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_StaffID_FK"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_StaffID_FK"));   
		droplist2.selectByVisibleText(StaffName);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1000);
		
	}
	
	public static void Search_User_ByName(String UserName) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(UserName);
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(Keys.RETURN);
		Thread.sleep(2500);		
	}
	
	/** NM 17oct2016 active checkbox is no loner available.  all users are always active. 	
	public static void Search_UserActive(String UserActive) throws InterruptedException{
		Thread.sleep(500);
		//may need to be modified or deleted
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_IsActive"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_IsActive"));   
		droplist2.selectByVisibleText(UserActive);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1000);
		
	}**/
	
	public static void Select_User_Rows(String Rows) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementByClassName("ui-pg-selbox"));   
		droplist.selectByVisibleText(Rows);
		Thread.sleep(500);

	}
	
	public static void Select_User_DefaultFacility(String DefaultFacility) throws AWTException, InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Integer tabcntr;
		boolean found=false;
		for (tabcntr=1;tabcntr<=6;tabcntr++){
			 if (ClickElementbyID("FacilityID_FK")){
				 found=true;
					System.out.println("Default facility is found");
					break;
				}else{		
					RobotSendKeys(KeyEvent.VK_TAB);
					System.out.println(" pagedown: "+tabcntr);
					
				}
			}
		if (!found){
			System.out.println("Default facility is not found");
		}
		Select droplist = new Select(driver.findElementById("FacilityID_FK"));   
		droplist.selectByVisibleText(DefaultFacility);
	}
	
	public static void Select_User_Role(String Role) throws AWTException, InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Integer tabcntr;
		boolean found=false;
		for (tabcntr=1;tabcntr<=6;tabcntr++){
			 if (ClickElementbyID("RoleID_FK")){
				 found=true;
					System.out.println("Role is found");
					break;
				}else{		
					RobotSendKeys(KeyEvent.VK_TAB);
					System.out.println(" pagedown: "+tabcntr);
					
				}
			}
			if (!found){
				System.out.println("Role is not found");
			}
		Select droplist = new Select(driver.findElementById("RoleID_FK"));  
		Thread.sleep(2000);
		droplist.selectByVisibleText(Role);
	}
	
	public static void Select_User_Staff(String Staff){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("StaffID_FK"));   
		droplist.selectByVisibleText(Staff);
		//System.out.println("User Staff sent = "+Staff);
	}
	
	public static String GetGridID_User_To_Modify(String UserName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		GridID=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+UserName+"']//..").getAttribute("id");
		Thread.sleep(500);

		return GridID;
	}
	
	public static void Select_User_To_Modify(String UserName) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement myElemment=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+UserName+"']//..");
		Actions action = new Actions(driver);
		action.click(myElemment);
		action.perform();
		Thread.sleep(1500);
		
		if (ExecBrowser.equalsIgnoreCase("Iexplore")){
			action.click(myElemment);
			action.perform();
			WebElement eleEdit=driver.findElementByXPath("//*[@id='edit_jqgrid_user']/div/span");
			driver.findElementByXPath("//*[@id='edit_jqgrid_user']/div/span").click();
		}else if (ExecBrowser.equalsIgnoreCase("Chrome")){
			action.doubleClick(myElemment);
			action.perform();
		}else{
			action.doubleClick(myElemment);
			action.perform();
		}
	}
	
	//Task_Value needs to be run prior to modifying the task (SelectTask) to determine if the task is selected or not.
		
	public static int Facility_Value(String Facility){
		UserFacility_UI_ID=driver.findElementByXPath("//span[text()='"+Facility+"']//..//..").getAttribute("id");
		System.out.println("Task_UI_ID:  "+UserFacility_UI_ID);
		FacilityCheckBoxStatus=driver.findElementByXPath("//span[text()='"+Facility+"']//..").getAttribute("class");
		System.out.println(FacilityCheckBoxStatus);
		if(FacilityCheckBoxStatus.contains("fancytree-selected")){
			FacilitySelected=1;
		}else{
			FacilitySelected=0;
		}
		System.out.println(Facility+" Selected="+FacilitySelected);
		return FacilitySelected;
	}

	public static void Select_UserFacility(String Facility) throws AWTException, InterruptedException{
		System.out.println(Facility);
		Integer tabcntr;
		boolean found=false;
		for (tabcntr=1;tabcntr<=6;tabcntr++){
			 if (ClickElement("//span[text()='"+Facility+"']")){
				 	found=true;
					System.out.println("Clicked on "+Facility);
					UserFacilityID=driver.findElementByXPath("//span[text()='"+Facility+"']//..//..").getAttribute("id");
					break;
				}else{

					RobotSendKeys(KeyEvent.VK_TAB);
				}
			}
		
		if (!found){
			System.out.println("Error in clicking on "+Facility);
		}
	}
	
	
	public static boolean ClickElement(String xpath) {
	    try {
	    	driver.findElementByXPath(xpath).click();
	    	System.out.println("ClickElement : true");
	    	return true;
	    } catch (WebDriverException e) {
	    	System.out.println("ClickElement : False");
	        return false;
	    } 
	}
	
	public static boolean ClickElementbyID(String id) {
	    try {
	    	driver.findElementById(id).click();
	    	return true;
	    } catch (WebDriverException e) {
	        return false;
	    } 
	}
	
	public static boolean sendKeysbyObj(String ObjectXapth,Keys strKeys) {
	    try {
	    	driver.findElementById(ObjectXapth).sendKeys(strKeys);
	    	return true;
	    } catch (WebDriverException e) {
	        return false;
	    } 
	}

	public static void selectUserRoleNLogin(String browserP, String URL, String role, String user, String userPswd) throws InterruptedException, AWTException, IOException{
		if (!loginUnifia(appUser,appPassword)){
			driver.close();
			System.out.println("Not able to login after waiting for 2 minutes");
		}
		LandingPage_Actions.Click_Admin_Menu("User");
		Thread.sleep(10000);
		User_Actions.ClearAllSearchCriteria(); //Clear all search criteria
		User_Actions.Search_User_ByName(user); //Search for the user name to be modified.
		GridID=User_Actions.GetGridID_User_To_Modify(user); //Get the grid id for the user name to be modified.
		User_Actions.Select_User_To_Modify(user); //Select the user name to be modified.
		User_Actions.Select_User_Role(role);
		User_Actions.Save_User_Edit();
		LandingPage_Actions.Click_Logout();
		if (!loginUnifia(user,userPswd)){
			driver.close();
			System.out.println("Not able to login after waiting for 2 minutes");
		}
	}
	
	public static void selectUserRoleMultiFacilityNLogin(String browserP, String URL, String role, String user, String userPswd,String Facility2,String Facility3) throws InterruptedException, AWTException, IOException{
		if (!loginUnifia(appUser,appPassword)){
			driver.close();
			System.out.println("Not able to login after waiting for 2 minutes");
		}
		SE_LA.Click_Admin_Menu("User");
		Thread.sleep(10000);
		ua.ClearAllSearchCriteria(); //Clear all search criteria
		ua.Search_User_ByName(user); //Search for the user name to be modified.
		GridID=ua.GetGridID_User_To_Modify(user); //Get the grid id for the user name to be modified.
		ua.Select_User_To_Modify(user); //Select the user name to be modified.
		ua.Select_User_Role(role);
		
		if(ua.Facility_Value(Facility2)==0){
			ua.Select_UserFacility(Facility2);
		}else if(ua.Facility_Value(Facility2)==1){
			System.out.println(Facility2+" is already selected");
		}
		if(ua.Facility_Value(Facility3)==0){
			ua.Select_UserFacility(Facility3);
		}else if(ua.Facility_Value(Facility3)==1){
			System.out.println(Facility3+" is already selected");
		}
		ua.Save_User_Edit();
		SE_LA.Click_Logout();
		if (!loginUnifia(user,userPswd)){
			driver.close();
			System.out.println("Not able to login after waiting for 2 minutes");
		}
	}
	
	public static void RobotSendKeys(int strKeys) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		robot.keyPress(strKeys);
		Thread.sleep(5000);
	}
	
	public static boolean loginUnifia(String userLogin, String pwdLogin) throws InterruptedException{
		boolean flag = false;
		Integer pageLoadTimeout=1;
		while (!flag&&pageLoadTimeout<=60){
			LGPA.Logon_Username(userLogin);
			LGPA.Logon_Password(pwdLogin);
			LGPA.Click_Submit();
			if (driver.findElements(By.id("accountControl")).size()>0){
				System.out.println("login is successfull");
				flag=true;
			}else{
				pageLoadTimeout++;
				flag = false;
				System.out.println("login is not successfull");
				Thread.sleep(2000);
			}
		}
		return flag;
	}
	
	public static void selectUserRoleMultiFacility(String browserP, String URL,  String userRoleFacility) throws InterruptedException, AWTException, IOException{
		
		SE_LA.Click_Admin_Menu("User");
		Thread.sleep(10000);
		ua.ClearAllSearchCriteria(); //Clear all search criteria
		
		String[] userFac = userRoleFacility.split(";");
		for (int cntr=0; cntr<userFac.length; cntr++){
			String [] eachSet=userFac[cntr].split("==");
			ua.Search_User_ByName(eachSet[0]); //Search for the user name to be modified.
			GridID=ua.GetGridID_User_To_Modify(eachSet[0]); //Get the grid id for the user name to be modified.
			ua.Select_User_To_Modify(eachSet[0]); //Select the user name to be modified.
			ua.Select_User_Role(eachSet[1]);
			
			//uncheck default facility
			if (ua.Facility_Value(Unifia_Admin_Selenium.defaultFacility)==1){
				ua.Select_UserFacility(Unifia_Admin_Selenium.defaultFacility);
			}

			if(ua.Facility_Value(eachSet[2])==0){
				ua.Select_UserFacility(eachSet[2]);
			}else if(ua.Facility_Value(eachSet[2])==1){
				System.out.println(eachSet[2]+" is already selected");
			}
			ua.Select_User_DefaultFacility(eachSet[2]);
			ua.Save_User_Edit();
			
		}
		SE_LA.Click_Logout();
	}
	
}
		

	
