package TestFrameWork.Unifia_EM;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.Select;

import com.thoughtworks.selenium.webdriven.commands.IsAlertPresent;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class EM_Actions extends Unifia_Admin_Selenium{
	public static GeneralFunc SE_Gen;
	
	public static void Click_ColumnFilter() throws InterruptedException{
		Thread.sleep(2000);
		driver.findElementById("btnColumnFilters").click();
		Thread.sleep(1000);

	}
	
	public static void Search_RefNo(String RefNo) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopeReference").sendKeys(Keys.chord(Keys.CONTROL, "a"),  RefNo);
		Thread.sleep(500);
		//driver.findElementById("txtFilterScopeReference").click();
	}

	public static void Search_ExamID(String ExamID){
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterExamId").sendKeys(Keys.chord(Keys.CONTROL, "a"),  ExamID);
	}
	public static void Click_Search() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='thHeaderFilters']/div[2]/div[6]/div[8]/input[1]")).click();	
		Thread.sleep(500);
		}

	public static void Clear_Filter() throws InterruptedException{
		Thread.sleep(100);

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("btnClearFilter").click();	
		Thread.sleep(100);
		}

	public static void Clear_ColumnFilter() throws InterruptedException{
		Thread.sleep(100);

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='thHeaderFilters']/div[2]/div[6]/div[8]/input[2]")).click();	
		Thread.sleep(100);
		}

	public static void Change_Facility(String Facility) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("ddlSelectedFacility"));   
		droplist.selectByVisibleText(Facility);
		Thread.sleep(5*1000);
		if(isAlertPresent()){
			resultFlag="#Failed!#";
			Alert alert=Unifia_Admin_Selenium.driver.switchTo().alert();
			System.out.println("Unexpected Alert popped up whose text is "+alert.getText());
			alert.accept();
		}
		Thread.sleep(2000);
	}
	public static void DateFilter(String StartDate,String EndDate) throws InterruptedException{
		int divNum=4;
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"reportrange\"]")).click();
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[1]/ul/li[7]")).click(); //this is for custom rang and then need to click 'apply' 
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[2]/div[1]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), StartDate);	
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[3]/div[1]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"),EndDate); 		
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[2]/div[1]/input")).click();
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[1]/div/button[1]")).click();
		Thread.sleep(500);
	}

	public static void ApplyFilter() throws InterruptedException{
		driver.findElementById("btnApplyFilter").click();
		Thread.sleep(500);
	}
	
	public static int get_NumOfProcRoomCount(){
		int numOfProcRoomRec=1;
		while(driver.findElementsByXPath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr["+numOfProcRoomRec+"]").size()>0){
			numOfProcRoomRec++;
		}
		return numOfProcRoomRec-1;
	}
	
	public static int[] get_Column_Index(String column){
		int[] columnIndex={0,0};
		String header="";
		int i=1;
		
		while(driver.findElementsByXPath("//*[@id='examManagementGrid']/div[1]/table/tr[1]/th/div["+i+"]").size()>0){
			header=driver.findElementByXPath("//*[@id='examManagementGrid']/div[1]/table/tr[1]/th/div["+i+"]").getText();
			if(header.equalsIgnoreCase(column)){
				columnIndex[0]=i;
				columnIndex[1]=1; //EM Table Part1
				break;
			}
			i++;
		}
		
		i=1;
		while(driver.findElementsByXPath("//*[@id='examManagementGrid']/div[1]/table/tr[1]/th/div[2]/div["+i+"]").size()>0){
			header=driver.findElementByXPath("//*[@id='examManagementGrid']/div[1]/table/tr[1]/th/div[2]/div["+i+"]").getText();
			if(header.equalsIgnoreCase(column)){
				columnIndex[0]=i;
				columnIndex[1]=2; //EM Table Part2
				break;
			}
			i++;
		}
		
		i=1;
		while(driver.findElementsByXPath("//*[@id='examManagementGrid']/div[1]/table/tr[1]/th/div[2]/div[6]/div["+i+"]").size()>0){
			header=driver.findElementByXPath("//*[@id='examManagementGrid']/div[1]/table/tr[1]/th/div[2]/div[6]/div["+i+"]").getText();
			if(header.equalsIgnoreCase(column)){
				columnIndex[0]=i;
				columnIndex[1]=3; //EM Table Part3
				break;
			}
			i++;
		}
		return columnIndex;
	}
	
	public static List<String> get_EM_ColumnValues(String column){
		List<String> columnValues=new ArrayList<String>();
		String innerTableXpath="/div/div[2]/table/tbody/";
		
		int[] colDetails=get_Column_Index(column); //To get the required column index and as well as the part of the EM table in which the required column exists
		
		if(colDetails[1]==1){ //For first Part of EM Table 
			int numOfProcRoomRec=1;
			String name=null;
			while(driver.findElementsByXPath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr["+numOfProcRoomRec+"]").size()>0){
				name=driver.findElementByXPath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr["+numOfProcRoomRec+"]/td[1]").getText();
				numOfProcRoomRec++;
				columnValues.add(name);
			}
			return columnValues;
		}else if(colDetails[1]==2){ //For second Part of EM Table
			int numOfProcRooms=get_NumOfProcRoomCount();
			int columnIndex=colDetails[0];
			int i=1;
			int numOfRows=1;
			String columnValue=null;
			while(i<=numOfProcRooms){
				numOfRows=1;
				while(driver.findElementsByXPath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr["+i+"]/td[2]"+innerTableXpath+"tr["+numOfRows+"]/td["+columnIndex+"]").size()>0){
					columnValue=driver.findElementByXPath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr["+i+"]/td[2]"+innerTableXpath+"tr["+numOfRows+"]/td["+columnIndex+"]").getText();
					numOfRows++;
					if(columnValue.equalsIgnoreCase("")){
						columnValue="-";
					}
					columnValues.add(columnValue);
				}
				i++;
			}
			return columnValues;
		}else if(colDetails[1]==3){ //For third part of EM Table
			int numOfProcRooms=get_NumOfProcRoomCount();
			int columnIndex=colDetails[0];
			int i=1;
			int numOfRows=1;
			int numOfInnerRows=1;
			String columnValue=null;
			while(i<=numOfProcRooms){
				numOfRows=1;
				while(driver.findElementsByXPath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr["+i+"]/td[2]"+innerTableXpath+"tr["+numOfRows+"]").size()>0){
					numOfInnerRows=1;
					while(driver.findElementsByXPath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr["+i+"]/td[2]"+innerTableXpath+"tr["+numOfRows+"]/td[6]"+innerTableXpath+"tr["+numOfInnerRows+"]/td["+columnIndex+"]").size()>0){
						columnValue=driver.findElementByXPath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr["+i+"]/td[2]"+innerTableXpath+"tr["+numOfRows+"]/td[6]"+innerTableXpath+"tr["+numOfInnerRows+"]/td["+columnIndex+"]").getText();
						if(columnValue.equalsIgnoreCase("")){
							columnValue="-";
						}
						columnValues.add(columnValue);
						numOfInnerRows++;
					}
					numOfRows++;
				}
				i++;
			}
			return columnValues;
		}
		return columnValues;
	}
	
	public static void search_ProcRoom(String ProcRoom) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterLocation").sendKeys(Keys.chord(Keys.CONTROL, "a"),  ProcRoom);
		Thread.sleep(500);
	}
	
	public static void search_PatientID(String PID) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterPatientId").sendKeys(Keys.chord(Keys.CONTROL, "a"),  PID);
		Thread.sleep(500);
	}
	
	public static void search_NumOfScopes(String ScopeCount) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopeCount").sendKeys(Keys.chord(Keys.CONTROL, "a"),  ScopeCount);
		Thread.sleep(500);
	}
	
	public static void search_ScopeModel(String scopeModel) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopeModel").sendKeys(Keys.chord(Keys.CONTROL, "a"),  scopeModel);
		Thread.sleep(500);
	}
	
	public static void search_ScopeSerialNum(String scopeSerialNum) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopeSerialNumber").sendKeys(Keys.chord(Keys.CONTROL, "a"),  scopeSerialNum);
		Thread.sleep(500);
	}
	
	public static void search_PreCleanBeforePatient(String preCleanBeforePatient) throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopePreCleanBefore").sendKeys(Keys.chord(Keys.CONTROL, "a"),  preCleanBeforePatient);
		Thread.sleep(500);
	}
	
	public static void clear_ProcRoomSearch() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("txtFilterLocation").clear();
		Thread.sleep(500);
	}
	
	public static void clear_ExamIDSearch() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterExamId").clear();
		Thread.sleep(500);
	}
	
	public static void clear_PatientIDSearch() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterPatientId").clear();
		Thread.sleep(500);
	}
	
	public static void clear_NumOfScopesSearch() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopeCount").clear();
		Thread.sleep(500);
	}
	
	public static void clear_ScopeModelSearch() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopeModel").clear();
		Thread.sleep(500);
	}
	
	public static void clear_ScopeSerialNumSearch() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopeSerialNumber").clear();
		Thread.sleep(500);
	}
	
	public static void clear_PreCleanBeforePatientSearch() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopePreCleanBefore").clear();
		Thread.sleep(500);
	}
	
	public static void clear_RefNoSearch() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("txtFilterScopeReference").clear();
		Thread.sleep(500);
	}
	
	public static void select_EM_Filter(String option) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("selectFilter"));   
		droplist.selectByVisibleText(option);
		Thread.sleep(100);
	}
	
	public static void Click_Details(String RefNo) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("details_"+RefNo).click();
		Thread.sleep(2000);
	}
	
	public static boolean isAlertPresent() throws InterruptedException {
		Thread.sleep(1000);
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}
}
