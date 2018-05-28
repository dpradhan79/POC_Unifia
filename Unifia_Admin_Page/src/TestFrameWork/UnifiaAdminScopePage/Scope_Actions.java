package TestFrameWork.UnifiaAdminScopePage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class Scope_Actions extends Unifia_Admin_Selenium {

	public static void Add_New_Scope()throws InterruptedException{ 
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scope_iladd").click();
		Thread.sleep(1000);
	}
	
	public static void Refresh_Scope_Grid()throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_scope").click();
		Thread.sleep(1000);
		}
	
	public static void Cancel_Scope_Edit()throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scope_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(1000);

	}
	
	public static void Save_Scope_Edit()throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scope_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(1000);

	}
	
	public static void ClearAllSearchCriteria() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_RFUID").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_SerialNumber").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_Comments").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_FacilityID_FK"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_ScopeTypeID_FK"));   
		droplist2.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist3 = new Select(driver.findElementById("gs_IsActive"));   
		droplist3.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		Thread.sleep(1000);
	}
	
	public static void Search_Scope_ByScopeType(String ScopeType) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_ScopeTypeID_FK"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_ScopeTypeID_FK"));   
		droplist2.selectByVisibleText(ScopeType);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1000);
		
	}
	
	public static void Search_Scope_ByRFUID(String ScopeRFUID) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_RFUID").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_RFUID").sendKeys(ScopeRFUID);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_RFUID").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		
	}
	
	public static void Search_Scope_ByName(String ScopeName) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Thread.sleep(2000);
		driver.findElementById("gs_Name").sendKeys(ScopeName);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(Keys.RETURN);
		Thread.sleep(1000);

	}
	
	public static void Search_Scope_BySerialNumber(String SerialNumber) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_SerialNumber").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_SerialNumber").sendKeys(SerialNumber);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_SerialNumber").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		
	}
	
	public static void Search_Scope_ByFacility(String FacName) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_FacilityID_FK"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_FacilityID_FK"));   
		droplist2.selectByVisibleText(FacName);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1000);
	}
	

	
	public static void Search_Scope_ByScopeStatus(String ScopeStatus) throws InterruptedException{
		Thread.sleep(500);
		//may need to be modified or deleted
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("gs_IsActive"));   
		droplist.selectByVisibleText("");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Select droplist2 = new Select(driver.findElementById("gs_IsActive"));   
		droplist2.selectByVisibleText(ScopeStatus);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Thread.sleep(1000);
		
	}
	
	public static void Search_Scope_ByComment(String Comment) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Comments").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_Comments").sendKeys(Comment);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.findElementById("gs_Comments").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		
	}
	
	public static void Select_Scope_Rows(String Rows) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementByClassName("ui-pg-selbox"));   
		droplist.selectByVisibleText(Rows);
		Thread.sleep(500);

	}
	
	public static void Selct_New_ScopeType(String ScopeType) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("-1_ScopeTypeID_FK"));   
		droplist.selectByVisibleText(ScopeType);
		Thread.sleep(500);

		//System.out.println("Scope Type sent = "+ScopeType);
	}
	
	public static void Enter_New_RFUID(String RFUID){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_RFUID").sendKeys(RFUID);
		//System.out.println("Scope RFUID sent = "+RFUID);
	}
	
	public static void Enter_New_Scope_Name(String ScopeName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_Name").sendKeys(ScopeName);
		//System.out.println("Scope name sent = "+ScopeName);
	}
	
	public static void Enter_New_SerialNumber(String SerialNumber){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_SerialNumber").sendKeys(SerialNumber);
		//System.out.println("Scope Serial Number sent = "+SerialNumber);
	}
	
	public static void Selct_New_Scope_Facility(String Facility){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("-1_FacilityID_FK"));   
		droplist.selectByVisibleText(Facility);
		//System.out.println("Scope Facility sent = "+Facility);
	}
	
	public static void Selct_New_ScopeStatus(String ScopeStatus){
		if(ScopeStatus.equalsIgnoreCase("True")){
			//do nothing, active is set to true by default on new locations.  

		}else if(ScopeStatus.equalsIgnoreCase("False")){
			//driver.findElementById("-1_IsActive").click();
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();
		}
		
		/**ScopeStatus=driver.findElementById("-1_IsActive").getAttribute("checked");
		System.out.println(ScopeStatus);
		if (ScopeStatus==null){
			//it is false
			ScopeStatus="False";
		} else {
			ScopeStatus="True";
		}
		
		return ScopeStatus ;**/
	
		/**driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("-1_ScopeStatusTypeID_FK"));   
		droplist.selectByVisibleText(Status);**/
		//System.out.println("Scope Status sent = "+Status);
	}
	
	public static void Enter_New_Comment(String Comment){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById("-1_Comments").sendKeys(Comment);
		//System.out.println("Scope Comment = "+Comment);
	}


	public static String GetGridID_Scope_To_Modify(String ScopeName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		Thread.sleep(5000);
		GridID=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+ScopeName+"']//..").getAttribute("id");
		return GridID;
	}
	
	public static void Select_Scope_To_Modify(String ScopeName){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement myElemment=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+ScopeName+"']//..");
		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}
	
	public static void Selct_Modify_ScopeType(String GridID, String ScopeType){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_ScopeTypeID_FK"));   
		droplist.selectByVisibleText(ScopeType);
		//System.out.println("Scope Type sent = "+ScopeType);
	}
	
	public static void Modify_RFUID(String GridID, String RFUID){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_RFUID").clear();
		driver.findElementById(GridID+"_RFUID").sendKeys(RFUID);
	}
	
	public static void Modify_Scope_Name(String GridID,String NewScopeName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_Name").clear();
		driver.findElementById(GridID+"_Name").sendKeys(NewScopeName);
	}
	
	public static void Modify_SerialNumber(String GridID,String SerialNumber){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_SerialNumber").clear();
		driver.findElementById(GridID+"_SerialNumber").sendKeys(SerialNumber);
	}
	
	public static void Selct_Modify_Scope_Facility(String GridID, String Facility){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_FacilityID_FK"));   
		droplist.selectByVisibleText(Facility);
		//System.out.println("Faciltiy sent = "+Facility);
	}
	
	public static String Scope_Active_Value(String ScopeName){
		//ModScopeAct_Val=null; //findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button")
//		ModScopeAct_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").getAttribute("checked");
//		
		ModScopeAct_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+ScopeName+"']//..").findElement(By.xpath("//td[7]/input")).getAttribute("value");
		System.out.println(ModScopeAct_Val);
		if (ModScopeAct_Val==null){
			//it is false
			ModScopeAct_Val="False";
		} else if(ModScopeAct_Val.equalsIgnoreCase("true")) {
			ModScopeAct_Val="True";
		}  else if(ModScopeAct_Val.equalsIgnoreCase("false")) {
			ModScopeAct_Val="False";
		}
		System.out.println("ModScopeAct_Val="+ModScopeAct_Val);
		return ModScopeAct_Val ;
	}
	public static void Selct_Modify_ScopeStatus(String GridID,String ModScopeAct_Val, String Status){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		if(Status.equalsIgnoreCase("True")){
			if(ModScopeAct_Val.equalsIgnoreCase("True")){
				//Do nothing the facility is already set to active
			}else{
				//driver.findElementById(GridID+"_IsActive").click();
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();

			}
			
		}else if(Status.equalsIgnoreCase("False")){
			if(ModScopeAct_Val.equalsIgnoreCase("True")){
				//driver.findElementById(GridID+"_IsActive").click();
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();

			}else{
				//Do nothing the facility is already set to inactive
			}
			
		}else{
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();
			//driver.findElementById(GridID+"_IsActive").click();
		}
	}
		//Select droplist = new Select(driver.findElementById(GridID+"_IsActive"));   
		//droplist.selectByVisibleText(Status);
		//System.out.println("Status sent = "+Status);
	

	public static void Modify_Comment(String GridID, String Comment){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_Comments").clear();
		driver.findElementById(GridID+"_Comments").sendKeys(Comment);
	}
	
}
