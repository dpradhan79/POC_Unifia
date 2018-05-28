package TestFrameWork.UnifiaAdminScannerPage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class Scanner_Actions extends Unifia_Admin_Selenium{
	
	public static void Add_New_Scanner() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scanner_iladd").click();
		Thread.sleep(500);
	}
	
	public static void Refresh_Scanner_Grid() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_scanner").click();
		Thread.sleep(500);
		}
	
	public static void Cancel_Scanner_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scanner_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(500);
	}
	
	public static void Save_Scanner_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scanner_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(500);
	}
	
	//start
	public static void Enter_Location_New(String Loc_Act) throws InterruptedException{
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Thread.sleep(100);
		//driver.findElementById("-1_LocationID_FK").sendKeys(Loc_Act);
		//System.out.println("Location sent = "+Loc_Act);
		Select droplist = new Select(driver.findElementById("-1_LocationID_FK"));   
		droplist.selectByVisibleText(Loc_Act);
		System.out.println("Scanner Location sent = "+Loc_Act);
	}
	
	public static void Enter_ScanID_New(String ScanID_Act) throws InterruptedException{
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//Thread.sleep(100);
		driver.findElementById("-1_ScannerID").sendKeys(ScanID_Act);
		System.out.println("Scanner ID sent = "+ScanID_Act);
	}
	
	public static void Enter_ScanName_New(String ScanName_Act){
		
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_Name").sendKeys(ScanName_Act);
		System.out.println("Scanner name sent = "+ScanName_Act);
	}
	
		
	public static void Select_New_Scanner_Active (String ScanAct){
		if(ScanAct.equals("True")){
			
		}else if(ScanAct.equals("False")){
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").click();
			//driver.findElementById("-1_IsActive").click();  
		}
	}
	
	
	public static String GetGridID_Scanner_To_Modify(String ScanID_Act){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		GridID=driver.findElement(By.xpath("//table['jqgrid_scanner']/tbody/tr/td[text()='"+ScanID_Act+"']//..")).getAttribute("id");		

		return GridID;
	}
	
		
	public static void Select_Scanner_To_Modify(String ScanID_Act) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(500);
		WebElement myElemment=driver.findElementByXPath("//table['jqgrid_scanner']/tbody/tr/td[text()='"+ScanID_Act+"']");

		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}
	
	public static void ClearScannerSrchCritera() throws InterruptedException{
		Thread.sleep(100);
		//Clear Location search string
		driver.findElementByXPath("//th[1]/div/table/tbody/tr/td[3]/a").click();
		//Clear Scanner ID search string
		driver.findElementByXPath("//th[2]/div/table/tbody/tr/td[3]/a").click();
		//Clear Scanner Name search String
		driver.findElementByXPath("//th[3]/div/table/tbody/tr/td[3]/a").click();
		//clear active search
		driver.findElementByXPath("//th[4]/div/table/tbody/tr/td[3]/a").click();
	}
	
		
	public static void Modify_ScanID(String GridID, String ScanID_Act){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);//wei
		driver.findElementById(GridID+"_ScannerID").clear();
		driver.findElementById(GridID+"_ScannerID").sendKeys(ScanID_Act);
	}
	
	public static void Modify_ScanName(String GridID, String ScanName_Act) throws InterruptedException{
		driver.findElementById(GridID+"_Name").clear();
		Thread.sleep(2000);
		driver.findElementById(GridID+"_Name").sendKeys(ScanName_Act);
		System.out.println("Scanner Name sent = "+ScanName_Act);
	}
	
	
	public static void Modify_Location(String GridID, String Loc_Act){
		Select droplist = new Select(driver.findElementById(GridID+"_LocationID_FK"));   
		droplist.selectByVisibleText(Loc_Act);
		System.out.println("Scanner Location sent = "+Loc_Act);
	}
	
	
	public static void Search_ScanID(String ScanID_Act) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("gs_ScannerID").clear();
		driver.findElementById("gs_ScannerID").sendKeys(ScanID_Act);
		System.out.println(ScanID_Act+" was ented in the Scanner ID search.");
		Thread.sleep(750);

		driver.findElementById("gs_ScannerID").sendKeys(Keys.RETURN);
		Thread.sleep(1500);
	}
	
	public static void Search_ScanName(String ScanName_Act){
		driver.findElementById("gs_Name").clear();
		driver.findElementById("gs_Name").sendKeys(ScanName_Act);
		driver.findElementById("gs_Name").sendKeys(Keys.RETURN);
		//System.out.println(ScanName_Act+" was ented in the name search.");
	}
	
	public static void Search_Location(String Loc_Act) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("gs_LocationID_FK")); 
		droplist.selectByVisibleText("");
		Thread.sleep(500);
		droplist.selectByVisibleText("Loc_Act");
		System.out.println(Loc_Act+" is entered in the search");
		Thread.sleep(500);

	}

	
	public static String Scanner_Active_Value(String ScanID){
		////finds current active value
		System.out.println(ScanID);

		//ModScannerAct_Val=driver.findElementById(GridID+"_IsActive").getAttribute("checked");
//		ModScannerAct_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").getAttribute("checked");
		ModScannerAct_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[text()='"+ScanID+"']//..").findElement(By.xpath("//td[4]/input")).getAttribute("value");
		System.out.println(ModScannerAct_Val);
		if (ModScannerAct_Val==null){
			//it is false
			ModScannerAct_Val="False";
		} else if(ModScannerAct_Val.equalsIgnoreCase("true")) {
			ModScannerAct_Val="True";
		}  else if(ModScannerAct_Val.equalsIgnoreCase("false")) {
			ModScannerAct_Val="False";
		}
		System.out.println("ModScannerAct_Val="+ModScannerAct_Val);
		

		return ModScannerAct_Val;
	}
	
	
	public static void Modify_Scanner_IsActive(String GridID, String ModScanAct_Val, String ScanAct){
		if(ScanAct.equalsIgnoreCase("true")){
			if(ModScanAct_Val.equalsIgnoreCase("true")){
				//Do nothing the facility is already set to active
			}else {
				//driver.findElementById(GridID+"_IsActive").click();
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").click();
			}
			
		}else if(ScanAct.equalsIgnoreCase("false")){
			if(ModScanAct_Val.equalsIgnoreCase("true")){
				//driver.findElementById(GridID+"_IsActive").click();
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").click();
			}else{
				//Do nothing the facility is already set to inactive
			}
			
		}else{
			//driver.findElementById(GridID+"_IsActive").click();
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").click();
		}
	} 
	
	
	public static String GetCurrentLocation(String GridID){
	Result=driver.findElementById(GridID+"_LocationID_FK").getAttribute("value");
	return Result;
	}
	
	public static String GetCurrentScanID(String GridID){
		Result=driver.findElementById(GridID+"_ScannerID").getAttribute("value");
		return Result;
	}
	
	public static String GetCurrentScanName(String GridID){
		Result=driver.findElementById(GridID+"__Name").getAttribute("value");
		return Result;
	}
	

	/*public static String InactiveLoc(String Loc_Act){
		try{
			//Select droplist = new Select(driver.findElementById(GridID+"_LocationID_FK"));
			Select droplist = new Select(driver.findElementById("gs_LocationID_FK"));
			droplist.selectByVisibleText("Loc_Act");
			Result="Fail";
		}
		catch (Exception msg) {
			// handle any errors
		    //System.out.println("SQLException: " + ex.getMessage());
		    //System.out.println("SQLState: " + ex.getSQLState());
		    //System.out.println("VendorError: " + ex.getErrorCode());
			Result="Pass";
		}
		return Result;
	} */
}
