package TestFrameWork.UnifiaAdminAssignBarcodePage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class AssignBarcode_Actions extends Unifia_Admin_Selenium{
	
	public static void Add_New_Barcode() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_assign_barcode_iladd").click();
		Thread.sleep(500);
	}
	
	public static void Refresh_Barcode_Grid() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_assign_barcode").click();
		Thread.sleep(500);
		}
	
	public static void Cancel_Barcode_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_assign_barcode_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(500);
	}
	
	public static void Save_Barcode_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_assign_barcode_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(500);
	}
	

	public static void Enter_Name_New(String Name){
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_Name").sendKeys(Name);
		//System.out.println("Barcode Name sent = "+Name);
	}

	public static void Modify_Name(String GridID, String Name){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		driver.findElementById(GridID+"_Name").clear();
		driver.findElementById(GridID+"_Name").sendKeys(Name);
	}
	
			
	public static void Select_New_Barcode_Type(String BarcodeType) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById("-1_BarcodeTypeID_FK"));   
		droplist.selectByVisibleText(BarcodeType);
		Thread.sleep(500);
	}
	
	public static void Select_Modify_Barcode_Type(String GridID, String BarcodeType) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(GridID+"_BarcodeTypeID_FK"));   
		droplist.selectByVisibleText(BarcodeType);
		Thread.sleep(500);
		//System.out.println("Barcode Type sent = "+BarcodeType);
	}
	
	public static String GetGridID_Barcode_To_Modify(String Name){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		GridID=driver.findElement(By.xpath("//table['jqgrid_assign_barcode']/tbody/tr/td[text()='"+Name+"']//..")).getAttribute("id");		
		return GridID;
	}
	
	public static void Select_Barcode_To_Modify(String Name) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(500);
		WebElement myElemment=driver.findElementByXPath("//table['jqgrid_assign_barcode']/tbody/tr/td[text()='"+Name+"']");
		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}
	
	public static void ClearBarcodeSrchCritera() throws InterruptedException{
		Thread.sleep(100);
		//Clear BarcodeName search string
		//driver.findElementByXPath("//th[1]/div/table/tbody/tr/td[3]/a").click();
		driver.findElementById("gs_Name").clear();
		//driver.findElementByXPath("//th[1]/div/table/tbody/tr/td[3]/@id="gs_Name").click();
		//*[@id="gs_Name"]
		
		Thread.sleep(300);
		//Clear BarcodeType search string
		driver.findElementById("gs_Name").clear();
		//driver.findElementByXPath("//th[2]/div/table/tbody/tr/td[3]/a").click();
		Thread.sleep(300);
	}
	
	public static void Search_Name(String Name) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("gs_Name").sendKeys(Name);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		System.out.println(Name+" was entered in the BarcodeAssignment Name search field.");
		Thread.sleep(1500);
		driver.findElementById("gs_Name").sendKeys(Keys.RETURN);
		Thread.sleep(1500);
	}
	
	public static void Selct_New_BarcodeStatus(String BarcodeStatus){
		if(BarcodeStatus.equalsIgnoreCase("True")){
			//do nothing, active is set to true by default on new staff.  
		}else if(BarcodeStatus.equalsIgnoreCase("False")){
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[3]/button").click();
		}
	}
	public static void Selct_Modify_BarcodeStatus(String ModBarcodeAct_Val, String Status){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		if(Status.equalsIgnoreCase("True")){
			if(ModBarcodeAct_Val.equalsIgnoreCase("True")){
				//Do nothing the staff is already set to active
			}else{

				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[3]/button").click();
			}
		}else if(Status.equalsIgnoreCase("False")){
			if(ModBarcodeAct_Val.equalsIgnoreCase("True")){
				driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[3]/button").click();
			}else{
				//Do nothing the staff is already set to inactive
			}
		}else{
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[3]/button").click();
		}
	}

	public static String Barcode_Active_Value(String ModName) throws InterruptedException {	
		//ModFacAct_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+FacName+"']//..").findElement(By.xpath("//td[5]/input")).getAttribute("value");
		Thread.sleep(1000);
		ModBarcodeStatus=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[2]/td[text()='"+ModName+"']//..").findElement(By.xpath("//td[3]/input")).getAttribute("value");
		System.out.println(ModBarcodeStatus);
		if (ModBarcodeStatus==null){
			//it is false
			ModBarcodeStatus="False";
		} else if(ModBarcodeStatus.equalsIgnoreCase("true")) {
			ModBarcodeStatus="True";
		}  else if(ModBarcodeStatus.equalsIgnoreCase("false")) {
			ModBarcodeStatus="False";
		}
		System.out.println("ModABarcodeStatus="+ModBarcodeStatus);
		return ModBarcodeStatus ;
	}
}
