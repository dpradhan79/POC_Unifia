package TestFrameWork.UnifiaAdminAccessPointPage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class AccessPoint_Actions extends Unifia_Admin_Selenium{
	
	public static void Add_New_AccessPoint() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_accesspoint_iladd").click();
		Thread.sleep(500);
	}
	
	public static void Refresh_AccessPoint_Grid() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_accesspoint").click();
		Thread.sleep(500);
		}
	
	public static void Cancel_AccessPoint_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_accesspoint_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(500);
	}
	
	public static void Save_AccessPoint_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_accesspoint_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(500);
	}
	

	public static void Enter_SSID_New(String SSID) throws InterruptedException{
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Thread.sleep(3000);
		driver.findElementById("-1_SSID").sendKeys(SSID);
		//System.out.println("AccessPoint SSID sent = "+SSID);
	}
	
	public static void Enter_Password_New(String Password){
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_Password").sendKeys(Password);
		//System.out.println("AccessPoint Password sent = "+Password);
	}
	
	public static String GetGridID_AccessPoint_To_Modify(String SSID){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		GridID=driver.findElement(By.xpath("//table['jqgrid_accesspoint']/tbody/tr/td[text()='"+SSID+"']//..")).getAttribute("id");		

		return GridID;
	}
	
		
	public static void Select_AccessPoint_To_Modify(String SSID) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(500);
		WebElement myElemment=driver.findElementByXPath("//table['jqgrid_accesspoint']/tbody/tr/td[text()='"+SSID+"']");

		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}
	
	public static void ClearAccessPointSrchCritera() throws InterruptedException{
		Thread.sleep(100);
		//Clear SSID search string
		driver.findElementByXPath("//th[1]/div/table/tbody/tr/td[3]/a").click();
		Thread.sleep(300);

		//Clear AccessPoint Password search string
		driver.findElementByXPath("//th[2]/div/table/tbody/tr/td[3]/a").click();
		Thread.sleep(300);

	}
	
	public static void Modify_SSID(String GridID, String SSID){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_SSID").clear();
		driver.findElementById(GridID+"_SSID").sendKeys(SSID);
	}
	
	public static void Modify_Password(String GridID, String Password){
		driver.findElementById(GridID+"_Password").clear();
		driver.findElementById(GridID+"_Password").sendKeys(Password);
		//System.out.println("AccessPoint Password sent = "+Password);
	}
	
	
	public static void Search_SSID(String SSID) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_SSID").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_SSID").sendKeys(SSID);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		System.out.println(SSID+" was entered in the AccessPoint SSID search.");
		Thread.sleep(1500);
		driver.findElementById("gs_SSID").sendKeys(Keys.RETURN);
		Thread.sleep(1500); 
	}
	
	public static void Search_Password(String Password) throws InterruptedException{
		driver.findElementById("gs_Password").clear();
		driver.findElementById("gs_Password").sendKeys(Password);
		driver.findElementById("gs_Password").sendKeys(Keys.RETURN);
		Thread.sleep(1000);

		//System.out.println(ScanName_Act+" was ented in the name search.");
	}
	public static void Selct_New_AccessPointStatus(String AcccessPointStatus){
		if(AcccessPointStatus.equalsIgnoreCase("True")){
			//do nothing, active is set to true by default on new staff.  
		}else if(AcccessPointStatus.equalsIgnoreCase("False")){
			//driver.findElementById("-1_IsActive").click();   
			//driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click(); -- to delete
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[3]/button").click();
		}
	}
	public static void Selct_Modify_AccessPointStatus(String GridID,String ModAccessPointAct_Val, String Status){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		if(Status.equals("True")){
			if(ModAccessPointAct_Val.equals("True")){
				//Do nothing the staff is already set to active
			}else{
				//driver.findElementById(GridID+"_IsActive").click();
				//driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click(); -- to delete
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[3]/button").click();
			}
		}else if(Status.equals("False")){
			if(ModAccessPointAct_Val.equals("True")){
				//driver.findElementById(GridID+"_IsActive").click();
				//driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click(); -- to delete
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[3]/button").click();
			}else{
				//Do nothing the staff is already set to inactive
			}
		}else{
			//driver.findElementById(GridID+"_IsActive").click();
			//driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[7]/button").click();- to delete
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[3]/button").click();
		}
	}

	public static String AccessPoint_Active_Value(String SSID_Actual) throws InterruptedException {	
		//ModFacAct_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..").findElement(By.xpath("//td[5]/input")).getAttribute("value");
		Thread.sleep(1000);
		ModAccessPointStatus=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+SSID_Actual+"']//..").findElement(By.xpath("//td[3]/input")).getAttribute("value");
		System.out.println(ModAccessPointStatus);
		if (ModAccessPointStatus==null){
			//it is false
			ModAccessPointStatus="False";
		} else if(ModAccessPointStatus.equalsIgnoreCase("true")) {
			ModAccessPointStatus="True";
		}  else if(ModAccessPointStatus.equalsIgnoreCase("false")) {
			ModAccessPointStatus="False";
		}
		System.out.println("ModAccessPointStatus="+ModAccessPointStatus);
		return ModAccessPointStatus ;
	}
}
