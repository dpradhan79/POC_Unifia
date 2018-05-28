package TestFrameWork;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.*;

public class OER_Simulator_SE_objects {

	public static RemoteWebDriver driver=Unifia_Admin_Selenium.driver;
	
	public static String Result;
	public static String Actual;
	public static String GridID; 
	public static String ModFacAct_Val; 
	

	// Generic Functions
	public static String Verify_ErrMsg(String ErrMsg){
		Actual=driver.findElementById("message").getText();
		System.out.println("Actual message = '"+Actual+"'");
		if(ErrMsg.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}
	
	/**
	 * implments the object son the unifia logon page
	 */
		//implements the action on the logon page
	public static void Launch_Unifia(){
		driver.get("http://10.170.93.180:8081/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void Logon_Username(String UN){
		driver.findElementById("txt_userid").sendKeys(UN);
	}

	public static void Logon_Password(String PW){
		driver.findElementById("txt_password").sendKeys(PW);
	}
	
	public static void Click_Submit(){
		driver.findElementById("loginButton").click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void Click_Reset(){
		driver.findElementById("resetButton").click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	
	//implements the verification on the logon page
	
	public static String Admin_Login_Pg_Verf(){
		if(driver.findElementById("txt_userid").getAttribute("type").equals("text")){
			Result="Pass";
		}else{
			Result="Fail";
		}
	
		if(Result.equals("Pass")){
			if(driver.findElementById("txt_password").getAttribute("type").equals("password")){
				Result="Pass";
			}else{
				Result="Fail";
			}
		}
		
		if(Result.equals("Pass")){
			if(driver.findElementById("loginButton").getAttribute("role").equals("button")){
				Result="Pass";
			}else{
				Result="Fail";
			}
		}
		
		if (Result.equals("Pass")){
			if(driver.findElementById("resetButton").getAttribute("role").equals("button")){
				Result="Pass";
			}else{
				Result="Fail";
			}
		}
		return Result;
	}
	
	public static String Verify_Username(String UN){
		Actual=driver.findElementById("txt_userid").getAttribute("value");
		if(UN.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}
	
	public static String Verify_Password(String PW){
		Actual=driver.findElementById("txt_password").getAttribute("value");
		
		System.out.println(Actual);
		
		if(PW.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}
	
	/**
	 * Implements the objects on the Unifia Landing Page
	 */
		//Actions on Landing page
	public static void Click_Logout(){
		driver.findElementById("logoutButton").click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public static void Click_Admin_Tab(){
		driver.findElementById("tab_admin").click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	public static void Click_Analysis_Tab(){
		driver.findElementById("tab_analysis").click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	public static void Click_Admin_Menu(String Menu){
		driver.findElementById("ft_menu_"+Menu).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public static String Verify_Admin_Function(String AdmFunc){
		Actual=driver.findElementByClassName("ui-jqgrid-title").getText();
		if(Actual.equals(AdmFunc+" List")){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}
	
		//Verification on Landing page
	
	public static String Admin_Landing_Pg_Verf() throws InterruptedException{
		Thread.sleep(750);
		if(driver.findElementById("logoutButton").getAttribute("role").equals("button")){
				Result="Pass";
			}else{
				Result="Fail";
			}
			
			if(Result.equals("Pass")){
				//xpath for Admin tab of unifia console 
				////*[@id="ui-id-1"]
				if(driver.findElementById("ui-id-1").getAttribute("text").equals("Admin")){
					Result="Pass";
				}else{
					Result="Fail";
				}
				
			}
			
			if(Result.equals("Pass")){
				if(driver.findElementById("ui-id-3").getAttribute("text").equals("Analysis")){
					Result="Pass";
				}else{
					Result="Fail";
				}
			}
			return Result;
	}
	
	/**
	 * Implements the facility object functions
	 * 
	 */
	
	public static void Add_New_Facility(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
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
	
	public static void Save_Facility_Edit(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_facility_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}
	
	public static void Search_Facility_ByName(String FacName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(FacName);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
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
	
/**	public static void Search_Facility_ByCustomerNumber(String FacCustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_CustomerNumber").clear();
		driver.findElementById("gs_CustomerNumber").sendKeys(FacCustNum);
		driver.findElementById("gs_CustomerNumber").sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}**/
	
	public static void Select_Facility_Rows(String Rows){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	Select droplist = new Select(driver.findElementByClassName("ui-pg-selbox"));   
	droplist.selectByVisibleText(Rows);
	}
	
	public static void Enter_New_Facility_Name(String FacilityName){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_Name").sendKeys(FacilityName);
		System.out.println("Facility name sent = "+FacilityName);
	}
	
	public static void Enter_New_Facility_Abbreviation(String FacilityAbbrv){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_Abbreviation").sendKeys(FacilityAbbrv);
		System.out.println("Abbreviation name sent = "+FacilityAbbrv);
	}
	
/**	public static void Enter_New_Facility_CustomerNumber(String CustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_CustomerNumber").sendKeys(CustNum);
		System.out.println("Customer number sent = "+CustNum);
	}**/
	
	public static void Selct_New_Facility_Active (String Active){
		if(Active.equals("True")){
			//Select droplist = new Select(driver.findElementById("-1_Active"));   
			//droplist.selectByVisibleText("Yes");
		}else if(Active.equals("False")){
			driver.findElementById("-1_Active").click();   
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
		
		ModFacAct_Val=driver.findElementByXPath("//div[2]/div/div[1]/div[2]/div[2]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+FacName+"']//..").findElement(By.xpath("//td[4]/input")).getAttribute("value");
		
		return ModFacAct_Val ;
		
		
	}
	
	public static String GetGridID_Facility_To_Modify(String FacName){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		GridID=driver.findElementByXPath("//div[2]/div/div[1]/div[2]/div[2]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+FacName+"']//..").getAttribute("id");
		return GridID;
		
		
	}
	
	public static void Select_Facility_To_Modify(String FacName){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement myElemment=driver.findElementByXPath("//div[2]/div/div[1]/div[2]/div[2]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+FacName+"']");
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
	
/**	public static void Modify_Facility_CustomerNumber(String GridID, String NewCustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_CustomerNumber").clear();
		driver.findElementById(GridID+"_CustomerNumber").sendKeys(NewCustNum);
	}**/
	
	public static void Modify_Facility_Active(String GridID, String ModFacAct_Val,String Active){
		if(Active.equals("True")){
			if(ModFacAct_Val.equals("True")){
				//Do nothing the facility is already set to active
			}else{
				driver.findElementById(GridID+"_Active").click();
			}
			
		}else if(Active.equals("False")){
			if(ModFacAct_Val.equals("True")){
				driver.findElementById(GridID+"_Active").click();
			}else{
				//Do nothing the facility is already set to inactive
			}
			
		}else{
			driver.findElementById(GridID+"_Active").click();
		}
	}
	
	public static String Verify_NewFacilityName(String FacName){
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		Actual=driver.findElementById("-1_Name").getAttribute("value");
		System.out.println("Actual value ="+Actual);

		if(FacName.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}
	

	public static String Verify_NewAbbreviation(String Abbreviation){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById("-1_Abbreviation").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(Abbreviation.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}
	public static String Verify_ModifyFacilityName(String GridID, String FacName){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById(GridID+"_Name").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(FacName.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}

	/**public static String Verify_CustomerNumber(String GridID, String CustNum){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById(GridID+"_CustomerNumber").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(CustNum.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}**/
	
	public static String Verify_Abbreviation(String GridID, String Abbreviation){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Actual=driver.findElementById(GridID+"_Abbreviation").getAttribute("value");
		System.out.println("Actual value ="+Actual);
		if(Abbreviation.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}
}
