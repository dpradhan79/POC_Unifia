package TestFrameWork.UnifiaAdminStaffPage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class Staff_Actions extends Unifia_Admin_Selenium {

	public static void Add_New_Staff()throws InterruptedException{ 
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_staff_iladd").click();
		Thread.sleep(1000);
	}
	
	public static void Refresh_Staff_Grid()throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_staff").click();
		Thread.sleep(1000);
		}
	
	public static void Cancel_Staff_Edit()throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_staff_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(1000);

	}
	
	public static void Save_Staff_Edit()throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_staff_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(1000);

	}
	
	public static void ClearAllSearchCriteria() throws InterruptedException{
		Select droplist = new Select(driver.findElementById("gs_TitleID_FK"));   
		droplist.selectByVisibleText("");
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//driver.findElementById("gs_TitleID_FK").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_FirstName").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_LastName").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_StaffID").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_BadgeID").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_StaffTypeID_FK"));   
		droplist2.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist3 = new Select(driver.findElementById("gs_IsActive"));   
		droplist3.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		Thread.sleep(1000);
	}
	
	public static void Search_Staff_ByTitle(String Title) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_TitleID_FK"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_TitleID_FK"));   
		droplist2.selectByVisibleText(Title);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1000);
		
	}
	
	public static void Search_Staff_ByStaffID(String StaffID) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_StaffID").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_StaffID").sendKeys(StaffID);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_StaffID").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		
	}
	
	public static void Search_Staff_ByStaffBadge(String StaffBadge) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_BadgeID").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_BadgeID").sendKeys(StaffBadge);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_BadgeID").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		
	}
	
	public static void Search_Staff_ByLastName(String LastName) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_LastName").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_LastName").sendKeys(LastName);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_LastName").sendKeys(Keys.RETURN);
		Thread.sleep(1000);

	}
	
	public static void Search_Staff_ByFirstName(String FirstName) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_FirstName").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_FirstName").sendKeys(FirstName);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_FirstName").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		
	}
	
	public static void Search_Staff_ByStaffType(String StaffType) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_StaffTypeID_FK"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_StaffTypeID_FK"));   
		droplist2.selectByVisibleText(StaffType);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1000);
	}
	

	
	public static void Search_Staff_ByStaffStatus(String StaffStatus) throws InterruptedException{
		Thread.sleep(500);
		//may need to be modified or deleted
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_IsActive"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_IsActive"));   
		droplist2.selectByVisibleText(StaffStatus);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1000);
		
	}
	

	
	public static void Select_Staff_Rows(String Rows) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementByClassName("ui-pg-selbox"));   
		droplist.selectByVisibleText(Rows);
		Thread.sleep(500);

	}
	
	public static void Selct_New_Title(String Title) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("-1_TitleID_FK"));   
		droplist.selectByVisibleText(Title);
		Thread.sleep(500);

		
	}
	
	public static void Enter_New_StaffID(String StaffID){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_StaffID").sendKeys(StaffID);
		//System.out.println("StaffID sent = "+StaffID
	}
	
	public static void Enter_New_StaffBadge(String StaffBadge){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_BadgeID").sendKeys(StaffBadge);
		//System.out.println("StaffBadge sent = "+StaffBadge
	}
	
	public static void Enter_New_Staff_FirstName(String FirstName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_FirstName").sendKeys(FirstName);
		//System.out.println("First name sent = "+FirstName);
	}
	public static void Enter_New_Staff_LastName(String LastName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_LastName").sendKeys(LastName);
		//System.out.println("Last name sent = "+LastName);
	}
	
	public static void Selct_New_Staff_Type(String StaffType) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("-1_StaffTypeID_FK"));   
		droplist.selectByVisibleText(StaffType);
		Thread.sleep(500);

		
	}
	
	public static void Selct_New_StaffStatus(String StaffStatus){
		if(StaffStatus.equalsIgnoreCase("True")){
			//do nothing, active is set to true by default on new staff.  

		}else if(StaffStatus.equalsIgnoreCase("False")){
			//driver.findElementById("-1_IsActive").click();   
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();
		}
		
		
		//System.out.println("Staff Status sent = "+StaffStatus);
	}
	


	public static String GetGridID_Staff_To_Modify(String StaffID){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		//GridID=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+StaffID+"']//..").getAttribute("id");
		GridID=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+StaffID+"']//..").getAttribute("id");
		return GridID;
	}
	
	public static void Select_Staff_To_Modify(String StaffID){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement myElemment=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+StaffID+"']//..");
		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}
	
	public static void Selct_Modify_Staff(String GridID, String StaffID){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_gs_StaffID"));   
		droplist.selectByVisibleText(StaffID);
		//System.out.println("Staff ID sent = "+StaffID);
	}
	
	public static void Modify_StaffID(String GridID, String StaffID){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_StaffID").clear();
		driver.findElementById(GridID+"_StaffID").sendKeys(StaffID);
	}
	
	public static void Modify_StaffBadge(String GridID, String StaffBadge){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_BadgeID").clear();
		driver.findElementById(GridID+"_BadgeID").sendKeys(StaffBadge);
	}
	
	public static void Modify_First_Name(String GridID,String NewFirstName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_FirstName").clear();
		driver.findElementById(GridID+"_FirstName").sendKeys(NewFirstName);
	}
	public static void Modify_Last_Name(String GridID,String NewLastName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_LastName").clear();
		driver.findElementById(GridID+"_LastName").sendKeys(NewLastName);
	}
	
	public static void Selct_Modify_Staff_Type(String GridID, String StaffType){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_StaffTypeID_FK"));   
		droplist.selectByVisibleText(StaffType);
		//System.out.println("Staff Type sent = "+StaffType);
	}
	
	public static void Selct_Modify_Staff_Title(String GridID, String Title){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_TitleID_FK"));   
		droplist.selectByVisibleText(Title);
		//System.out.println("Title sent = "+Title);
	}

	
	public static String Staff_Active_Value(String StaffID){
		//ModStaffAct_Val=null;
		//ModStaffAct_Val=driver.findElementById(GridID+"_IsActive").getAttribute("checked");
		
		ModStaffAct_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[text()='"+StaffID+"']//..").findElement(By.xpath("//td[7]/input")).getAttribute("value");
		System.out.println("ModStaffAct_Val="+ModStaffAct_Val);
		if (ModStaffAct_Val==null){
			//it is false
			ModStaffAct_Val="False";
		} else if(ModStaffAct_Val.equalsIgnoreCase("true")) {
			ModStaffAct_Val="True";
		}  else if(ModStaffAct_Val.equalsIgnoreCase("false")) {
			ModStaffAct_Val="False";
		}
		System.out.println("ModStaffAct_Val="+ModStaffAct_Val);
		
		return ModStaffAct_Val ;
	}
	

	public static void Selct_Modify_StaffStatus(String GridID,String ModStaffAct_Val, String Status){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		if(Status.equals("True")){
			if(ModStaffAct_Val.equals("True")){
				//Do nothing the staff is already set to active
			}else{
				//driver.findElementById(GridID+"_IsActive").click();
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();
			}
			
		}else if(Status.equals("False")){
			if(ModStaffAct_Val.equals("True")){
				//driver.findElementById(GridID+"_IsActive").click();
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();
			}else{
				//Do nothing the staff is already set to inactive
			}
			
		}else{
			//driver.findElementById(GridID+"_IsActive").click();
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();
		}
	}
		  
		
}
