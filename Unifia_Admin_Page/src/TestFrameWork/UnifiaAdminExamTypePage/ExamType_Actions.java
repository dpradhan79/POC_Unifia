package TestFrameWork.UnifiaAdminExamTypePage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import TestFrameWork.Unifia_Admin_Selenium;

public class ExamType_Actions extends Unifia_Admin_Selenium {

	public static void Add_New_ExamType(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_exam_type_iladd").click();
		//jqgrid_ExamType_iladd
	}
	
	public static void Refresh_ExamType_Grid(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_exam_type").click();
		}
	
	public static void Cancel_ExamType_Edit(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_exam_type_ilcancel").click();;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

	}
	
	public static void Save_ExamType_Edit(){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("jqgrid_exam_type_ilsave").click(); 
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}
	
	public static void Enter_New_ExamType_Name(String ExamTypeName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Thread.sleep(500);
		driver.findElementById("-1_Name").sendKeys(ExamTypeName);
		System.out.println("ExamType name sent = "+ExamTypeName);
	}
	
	public static void Enter_New_ExamType_Abbreviation(String ExamTypeAbbreviation){
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("-1_Abbreviation").sendKeys(ExamTypeAbbreviation);
		System.out.println("ExamType Abbreviation sent = "+ExamTypeAbbreviation);
	}
	

	
	

	public static void Selct_New_ExamType_Active (String Active){
		if(Active.equals("True")){
			//do nothing, new exam type is True by default
		}else if(Active.equals("False")){
			driver.findElementByXPath("//div/div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
			//driver.findElementById("-1_IsActive").click();   
		}
	}
	
	public static String GetGridID_ExamType_To_Modify(String ExamTypeAbbrv){
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		GridID=driver.findElement(By.xpath("//table['jqgrid_exam_type']/tbody/tr/td[text()='"+ExamTypeAbbrv+"']//..")).getAttribute("id");
		return GridID;
	}
	
	public static void Select_ExamType_To_Modify(String ExamTypeAbbrv) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(1000);
		WebElement myElemment=driver.findElementByXPath("//table['jqgrid_exam_type']/tbody/tr/td[text()='"+ExamTypeAbbrv+"']");
		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}
	
	public static void SearchExamTypeByAbbrv(String ETAbr) throws InterruptedException{
		Thread.sleep(1500);
		driver.findElementById("gs_Abbreviation").clear();
		driver.findElementById("gs_Abbreviation").sendKeys(ETAbr);
		System.out.println(ETAbr+" is entered in the search");
		driver.findElementById("gs_Abbreviation").sendKeys(Keys.RETURN);
		Thread.sleep(2000);
	}
	
	public static void ModifyExamTypeName(String GridID, String ETName){
		driver.findElementById(GridID+"_Name").clear();
		driver.findElementById(GridID+"_Name").sendKeys(ETName);
	}
	
	public static void ModifyExamTypeAbbrv(String GridID, String ETAbr){
		driver.findElementById(GridID+"_Abbreviation").clear();
		driver.findElementById(GridID+"_Abbreviation").sendKeys(ETAbr);
		System.out.println("ExamType Abbreviation sent = "+ETAbr);
	}
	
	public static String ExamType_Active_Value(String Abbrev){
		
		//ModETAct_Val=driver.findElementByXPath("//div[3]/div/table/tbody/tr/td[text()='"+ETAbr+"']//..").findElement(By.xpath("//td[3]/input")).getAttribute("value");
		//ModETAct_Val=driver.findElementById(GridID+"_IsActive").getAttribute("checked");
		//ModETAct_Val=driver.findElementByXPath("//div/div[3]/div[3]/div/table/tbody/tr/td[3]/button").getAttribute("checked");
		
		ModETAct_Val=driver.findElementByXPath("//div[3]/div[3]/div/table/tbody/tr/td[text()='"+Abbrev+"']//..").findElement(By.xpath("//td[3]/input")).getAttribute("value");
		//System.out.println("ModETAct_Val="+ModETAct_Val);
		if (ModETAct_Val==null){
			//it is false
			ModETAct_Val="False";
		} else if(ModETAct_Val.equalsIgnoreCase("true")) {
			ModETAct_Val="True";
		}  else if(ModETAct_Val.equalsIgnoreCase("false")) {
			ModETAct_Val="False";
		}
		//System.out.println("ModETAct_Val="+ModETAct_Val);

		return ModETAct_Val ;		
	}
	
	public static void Modify_ExamType_IsActive(String GridID, String ModETAct_Val, String Active){
		if(Active.equalsIgnoreCase("true")){
			if(ModETAct_Val.equalsIgnoreCase("true")){
				//Do nothing the facility is already set to active
			}else {
				driver.findElementByXPath("//div/div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
				//driver.findElementById(GridID+"_IsActive").click();
			}
			
		}else if(Active.equalsIgnoreCase("false")){
			if(ModETAct_Val.equalsIgnoreCase("true")){
				driver.findElementByXPath("//div/div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();
				//driver.findElementById(GridID+"_IsActive").click();
			}else{
				//Do nothing the facility is already set to inactive
			}
			
		}else{
			driver.findElementByXPath("//div/div[3]/div[3]/div/table/tbody/tr/td[3]/button").click();			
			//driver.findElementById(GridID+"_IsActive").click();
		}
	}
	/***
	 * Created the following actions to aid in the verification of the exam type save.
	 */
	
	public static void EnterExamTypeNameSearch_SearchActionNotIncluded(String ETName){
		driver.findElementById("gs_Name").clear();
		driver.findElementById("gs_Name").sendKeys(ETName);
		System.out.println(ETName+" was ented in the name search.");
	}
	
	public static void ClearExamTypeSrchCritera() throws InterruptedException{
		Thread.sleep(1500);
		//Clear Name search string
		driver.findElementByXPath("//th[1]/div/table/tbody/tr/td[3]/a").click();
		//Clear Abbreviation String
		driver.findElementByXPath("//th[2]/div/table/tbody/tr/td[3]/a").click();
		//clear active search
		driver.findElementByXPath("//th[3]/div/table/tbody/tr/td[3]/a").click();
	}
	
	public static String GetCurrentExamTypeAbr(String GridID){
		Result=driver.findElementById(GridID+"_Abbreviation").getAttribute("value");
		return Result;
	}
	
	public static String GetCurrentExamTypeName(String GridID){
		Result=driver.findElementById(GridID+"_Name").getAttribute("value");
		return Result;
	}
	
	public static String Valueof_NEW_ExamTypeName(){
		Result=driver.findElementById("-1_Name").getAttribute("value");
		return Result;
	}
}
