package TestFrameWork.UnifiaAdminScopeTypePage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;


public class ScopeType_Actions extends Unifia_Admin_Selenium{

	public static void Add_New_ScopeType() throws InterruptedException{
		Thread.sleep(500);
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scope_model_iladd").click();
		
	}
	
	public static void Refresh_ScopeType_Grid() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_scope_model").click();
		Thread.sleep(500);
		}
	
	public static void Cancel_ScopeType_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scope_model_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Thread.sleep(500);

	}
	
	public static void Save_ScopeType_Edit() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_scope_model_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Thread.sleep(1500);

	}
	
	public static void Select_ExamType_New(String ET_Act){
		Select droplist = new Select(driver.findElementById("-1_ExamTypeID_FK"));   
		droplist.selectByVisibleText(ET_Act);
			}


	public static void Select_New_ScopeType_Active (String Active){
	if(Active.equals("True")){
		//do nothing, the default value is True.
	}else if(Active.equals("False")){
		//driver.findElementById("-1_IsActive").click();
		//driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").click();
		driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
	}
}
	public static void Select_New_ScopeType_ECN_Checked (String Active){
	if(Active.equals("True")){
		//do nothing, the default value is True.
	}else if(Active.equals("False")){
		//driver.findElementById("-1_HasElevatorCleaningNotice").click();
		driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
	}
}
	public static void enterNewScopeType(String Name_Act){
		driver.findElementById("-1_Name").sendKeys(Name_Act);
	}

public static String GetGridID_ScopeType_To_Modify(String ScopeType){
	System.out.println("Get Grid ID for Scope Model ="+ScopeType);
	driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
	GridID=driver.findElement(By.xpath("//table['jqgrid_scope_model']/tbody/tr/td[text()='"+ScopeType+"']//..")).getAttribute("id");
	return GridID;
}

public static void Select_ScopeType_To_Modify(String ScopeType) throws InterruptedException{
	System.out.println("Select Scope Model to modify"+ScopeType);

	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	Thread.sleep(1000);
	WebElement myElemment=driver.findElementByXPath("//table['jqgrid_scope_model']/tbody/tr/td[text()='"+ScopeType+"']");
	Actions action = new Actions(driver);
	action.doubleClick(myElemment);
	action.perform();
}

public static void SearchExamType(String ET_Act) throws InterruptedException{
	Thread.sleep(1500);
	Select droplist = new Select(driver.findElementById("gs_ExamTypeID_FK"));   
	droplist.selectByVisibleText("");
	Thread.sleep(500);   
	droplist.selectByVisibleText(ET_Act);
	System.out.println(ET_Act+" is entered in the search");
	Thread.sleep(2000);
}

public static String Scope_ECN_Value(String Name){
	//ModECN_Val=driver.findElementById(GridID+"_HasElevatorCleaningNotice").getAttribute("checked");
	//System.out.println(ModECN_Val);
	
	ModECN_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+Name+"']//..").findElement(By.xpath("//td[3]/input")).getAttribute("value");
	System.out.println(ModScopeTypeAct_Val);
	if (ModECN_Val==null){
		//it is false
		ModECN_Val="False";
	} else if(ModECN_Val.equalsIgnoreCase("true")) {
		ModECN_Val="True";
	}  else if(ModECN_Val.equalsIgnoreCase("false")) {
		ModECN_Val="False";
	}
	System.out.println("ModECN_Val="+ModECN_Val);
	return ModECN_Val ;
}

public static void Search_ScopeType_ByECN(String ECN_Val) throws InterruptedException{
	driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	driver.findElementById("gs_HasElevatorCleaningNotice").clear();
	driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	driver.findElementById("gs_HasElevatorCleaningNotice").sendKeys(ECN_Val);
	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	driver.findElementById("gs_HasElevatorCleaningNotice").sendKeys(Keys.RETURN);
	Thread.sleep(1000);
	
}

public static void SearchScopeType(String Name_Act) throws InterruptedException{
	Thread.sleep(500);
	driver.findElementById("gs_Name").clear();
	Thread.sleep(500);
	driver.findElementById("gs_Name").sendKeys(Name_Act);
	Thread.sleep(1500);
	driver.findElementById("gs_Name").sendKeys(Keys.RETURN);
	Thread.sleep(500);
}

public static void ModifyExamType(String GridID, String ET_Act){
	System.out.println("GridID = "+GridID);
	System.out.println("Exam Type ="+ET_Act);
	Select droplist = new Select(driver.findElementById(GridID+"_ExamTypeID_FK"));   
	droplist.selectByVisibleText(ET_Act);
	}

public static void ModifyScopeType(String GridID, String Name_Act){
	driver.findElementById(GridID+"_Name").clear();
	driver.findElementById(GridID+"_Name").sendKeys(Name_Act);
	System.out.println("ScopeType sent = "+Name_Act);
}

public static String ScopeType_Active_Value(String Name, String GridID){
	//ModScopeTypeAct_Val=driver.findElementById(GridID+"_IsActive").getAttribute("checked");
	//ModScopeTypeAct_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+Name+"']//..").findElement(By.xpath("//td[4]/input")).getAttribute("value");
	System.out.println(ModScopeTypeAct_Val);

	ModScopeTypeAct_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[@id="+GridID+"]/td[3]/input").getAttribute("value");
	System.out.println(ModScopeTypeAct_Val);
	if (ModScopeTypeAct_Val==null){
		//it is false
		ModScopeTypeAct_Val="False";
	} else if(ModScopeTypeAct_Val.equalsIgnoreCase("true")) {
		ModScopeTypeAct_Val="True";
	}  else if(ModScopeTypeAct_Val.equalsIgnoreCase("false")) {
		ModScopeTypeAct_Val="False";
	}
	System.out.println("ModScopeTypeAct_Val="+ModScopeTypeAct_Val);
	
	return ModScopeTypeAct_Val ;
	//finds current active value
	
}
public static void Selct_Modify_ECN(String GridID,String ModECN_Val, String Status){
	driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	if(Status.equalsIgnoreCase("True")){
		if(ModECN_Val.equalsIgnoreCase("True")){
			//Do nothing the ECN is already set to active
		}else{
			//driver.findElementById(GridID+"_HasElevatorCleaningNotice").click();
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
		}
		
	}else if(Status.equalsIgnoreCase("False")){
		if(ModECN_Val.equalsIgnoreCase("True")){
			//driver.findElementById(GridID+"_HasElevatorCleaningNotice").click();
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
		}else{
			//Do nothing the ECN is already set to inactive
		}
		
	}else{
		//driver.findElementById(GridID+"_HasElevatorCleaningNotice").click();
		driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
	}
}
	
	
public static void Modify_ScopeType_IsActive(String GridID, String ModSTAct_Val, String Active){
	if(Active.equalsIgnoreCase("true")){
		if(ModSTAct_Val.equalsIgnoreCase("true")){
			//Do nothing the Scope Type is already set to active
		}else {
			//driver.findElementById(GridID+"_IsActive").click();
			//driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").click();
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
		}
		
	}else if(Active.equalsIgnoreCase("false")){
		if(ModSTAct_Val.equalsIgnoreCase("true")){
			//driver.findElementById(GridID+"_IsActive").click();
			//driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").click();
			driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr[@id="+GridID+"]/td[3]/button").click();
		}else{
			//Do nothing the Scope Type is already set to inactive
		}
		
	}else{
		//driver.findElementById(GridID+"_IsActive").click();
		driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[4]/button").click();
	}
}
/***
 * Created the following actions to aid in the verification of the exam type save.
 */



public static void ClearScopeTypeSrchCritera() throws InterruptedException{
	Thread.sleep(100);
	//Clear Name search string
	driver.findElementByXPath("//th[1]/div/table/tbody/tr/td[3]/a").click();
	//Clear Abbreviation String
	driver.findElementByXPath("//th[2]/div/table/tbody/tr/td[3]/a").click();
	//clear active search
	driver.findElementByXPath("//th[3]/div/table/tbody/tr/td[3]/a").click();
}

public static String GetCurrentExamType(String GridID){
	Result=driver.findElementById(GridID+"_ExamTypeID_FK").getAttribute("value");
	return Result;
}

public static String GetCurrentScopeTypeName(String GridID){
	Result=driver.findElementById(GridID+"_Name").getAttribute("value");
	return Result;
}

public static String Valueof_NEW_ScopeTypeName(){
	Result=driver.findElementById("-1_Name").getAttribute("value");
	return Result;
}
public static String InactiveExamType(String ETAct){
	try{
		Select droplist = new Select(driver.findElementById("ExamTypeID_FK"));   
		droplist.selectByVisibleText("ET_Act");
		Result="Error";
	}
	catch (Exception msg){
		//System.out.println("VendorError: " + ex.getErrorCode());
		Result="Pass";
	}
	return Result;
}
}

