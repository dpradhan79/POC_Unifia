package TestFrameWork.UnifiaAdminReconMRC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class MRC_Actions extends Unifia_Admin_Selenium{
	
	private static GeneralFunc gf;
	
	public static String newMRCDate="//input[@id='txtMrcTestDateTime']";
	public static String newReprocessor="//select[@id='txtReprocessor']";
	public static String newMRCResult="//select[@id='txtMrcTestResult']";
	public static String newMRCStaff="//select[@id='txtMrcTestStaffId']";
	public static String newMRCComment="//*[@id='txtComments']";
	
	public static void Click_MRCRecordManagement(){
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@href='/InfectionPrevention/InfectionPrevention/MrcRecordManagement']")).click();
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Map<String,String> getMRCColumns(){
		Map<String,String> columns=new LinkedHashMap<String, String>();
		int i=1;
		String columnName;
		while(driver.findElementsByXPath("//div[@id='mrcGrid']/div[1]/table/tr[1]/th["+i+"]").size()!=0){
			columnName=driver.findElementByXPath("//div[@id='mrcGrid']/div[1]/table/tr[1]/th["+i+"]").getText().replaceAll("\\s+","");
			columns.put(columnName.toLowerCase(), Integer.toString(i));
			i++;
		}
		return columns;
	}
	
	public static String GetMRCTime(String ConnString, String AssociationID){
		Timestamp MRCDateTime  = null;
		String MRCScanDateTime="";
		String stmt="";
		try {
			Connection conn=DriverManager.getConnection(ConnString);
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=17;";
			System.out.println(stmt);
			Statement Statement = conn.createStatement();
			ResultSet IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				MRCDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
		MRCScanDateTime = format.format(MRCDateTime );
		System.out.println (MRCScanDateTime);
		
		return MRCScanDateTime;
	}
	
	public static String getMRCGridID(String MRCDateTime){
		int gridID=0,i=1;
		while(driver.findElementsByXPath("//div[@id='mrcGrid']/div[2]/table/tbody/tr["+i+"]/td[1]/div/span").size()!=0){
			if(MRCDateTime.equalsIgnoreCase(driver.findElementByXPath("//div[@id='mrcGrid']/div[2]/table/tbody/tr["+i+"]/td[1]/div/span").getText())){
				gridID=i;
				break;
			}else{
				i++;
			}
		}
		return String.valueOf(gridID);
	}
	
	public static void editMRCRecord(String gridID) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//div[@id='mrcGrid']/div[2]/table/tbody/tr["+gridID+"]/td[5]/input").click();
		Thread.sleep(1000);
	}
	
	public static void updateReprocessor(String Reprocessor) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath("//div[@id='mrcGrid']//tr[@id='editRow']//select[@id='ReprocessorLocationId']"));   
		droplist.selectByVisibleText(Reprocessor);
		Thread.sleep(500);
	}
	
	public static void updateMRCResult(String MRCResult) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath("//div[@id='mrcGrid']//tr[@id='editRow']//select[@id='MrcTestResultId']"));   
		droplist.selectByVisibleText(MRCResult);
		Thread.sleep(500);
	}
	
	public static void updateMRCStaff(String MRCStaff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath("//div[@id='mrcGrid']//tr[@id='editRow']//select[@id='MrcTestStaffId']"));   
		droplist.selectByVisibleText(MRCStaff);
		Thread.sleep(500);
	}
	
	public static void EnterComment(String Comment) throws InterruptedException{
		Thread.sleep(500);
		//driver.findElementById("Comments").sendKeys(Comment);
		driver.findElementByXPath("//div[@id='mrcGrid']//tr[@id='editRow']//input[@id='Comments']").sendKeys(Comment);
		Thread.sleep(1000);
	}
	
	public static void saveMRCChanges() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//input[@title='Update']").click();
		Thread.sleep(500);
	}
	
	public static void cancelMRCChanges() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//input[@title='Cancel edit']").click();
		Thread.sleep(500);
	}
	
	public static void addMRCRecord() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//input[@title='Add Record']").click();
		Thread.sleep(1000);
	}
	
	public static void addReprocessor(String Reprocessor) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath(newReprocessor));   
		droplist.selectByVisibleText(Reprocessor);
		Thread.sleep(500);
	}
	
	public static void addMRCResult(String MRCResult) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath(newMRCResult));   
		droplist.selectByVisibleText(MRCResult);
		Thread.sleep(500);
	}
	
	public static void addMRCStaff(String MRCStaff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath(newMRCStaff));   
		droplist.selectByVisibleText(MRCStaff);
		Thread.sleep(500);
	}
	
	public static void enterNewComment(String Comment) throws InterruptedException{
		Thread.sleep(500);
		//driver.findElementById("Comments").sendKeys(Comment);
		driver.findElementByXPath(newMRCComment).sendKeys(Comment);
		Thread.sleep(1000);
	}
	
	public static void saveNewMRCRecord() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//*[@id='btnSave']").click();
		Thread.sleep(500);
	}
	
	public static void cancelNewMRCRecord() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//*[@id='btnCancel']").click();
		Thread.sleep(500);
	}
	
	public static void updateMRCDateTime(String Date) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath(newMRCDate).sendKeys(Keys.chord(Keys.CONTROL, "a"), Date);		
		Thread.sleep(500);
	}
	
	public static String getBGColor(String xpathOfColor){
		String color=driver.findElementByXPath(xpathOfColor).getCssValue("background-color");
		return color;
	}
	public static String getReprocessor(String GridID) throws InterruptedException{
		Thread.sleep(500);
		String reprocessor=driver.findElementByXPath("//*[@id='mrcGrid']/div[2]/table/tbody/tr["+GridID+"]/td[2]/span").getText();   
		return reprocessor;
	}
	
	public static String getAddMRCErrorText() throws InterruptedException{
		Thread.sleep(1000);
		String errorText=driver.findElementById("errorText").getText();
		return errorText;
	}
	
	public static List<String> getMRCColumnNames(){
		List<String> columns=new ArrayList<String>();
		int i=1;
		String columnName;
		while(driver.findElementsByXPath("//div[@id='mrcGrid']/div[1]/table/tr[1]/th["+i+"]").size()!=0){
			columnName=driver.findElementByXPath("//div[@id='mrcGrid']/div[1]/table/tr[1]/th["+i+"]").getText();
			if(!columnName.equals("")){ //eliminating last column as it is for editing a record
				columns.add(columnName);
			}
			i++;
		}
		return columns;
	}
	
	public static List<String> getMRCColumnContent(String column){
		List<String> columnValues=new ArrayList<String>();
		String content="";
		String mrcTableXpath="//*[@id='mrcGrid']/div[2]/table/tbody";
		int rowCount=gf.getTableRowCount(mrcTableXpath);
		Map<String,String> columnsWithIndex=MRC_Actions.getMRCColumns();//this method will return the column names with their index in the table
		String columnIndex=columnsWithIndex.get(column.toLowerCase().replaceAll("\\s+","")); //getting the index of the given column in the MRC table 
		if(rowCount>0){
			if(rowCount==1){
				if(driver.findElementsByXPath(mrcTableXpath+"/tr[1]/td["+columnIndex+"]").size()>0){
					content=driver.findElementByXPath(mrcTableXpath+"/tr[1]/td["+columnIndex+"]").getText();
					columnValues.add(content);
				}else{
					content+=driver.findElementByXPath(mrcTableXpath+"/tr[1]/td[1]").getText();
					columnValues.add(content);
				}
			}else{
				for(int i=1;i<=rowCount;i++){
					content=driver.findElementByXPath(mrcTableXpath+"/tr["+i+"]/td["+columnIndex+"]").getText(); //getting the value of the given column")
					columnValues.add(content);
				}
			}
		}
		return columnValues;
	}
	
	public static void click_MRCFilter() throws InterruptedException{
		Thread.sleep(1000);
		driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[1]/th[5]/input[2]").click();
		Thread.sleep(1000);
	}
	
	public static boolean verify_ReprocessorFilter() throws InterruptedException{
		boolean isPresent=false;
		Thread.sleep(500);
		if(driver.findElementsByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[2]/select").size()>0){
			isPresent=true;
		}
		return isPresent;
	}
	
	public static boolean verify_MRCResultFilter() throws InterruptedException{
		boolean isPresent=false;
		Thread.sleep(500);
		if(driver.findElementsByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[3]/select").size()>0){
			isPresent=true;
		}
		return isPresent;
	}
	
	public static boolean verify_MRCStaffFilter() throws InterruptedException{
		boolean isPresent=false;
		Thread.sleep(500);
		if(driver.findElementsByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[4]/select").size()>0){
			isPresent=true;
		}
		return isPresent;
	}
	
	public static void applyMRCReprocessorFilter(String reprocessor) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[2]/select"));   
		droplist.selectByVisibleText(reprocessor);
		Thread.sleep(500);
	}
	
	public static void applyMRCTestResultFilter(String result) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[3]/select"));   
		droplist.selectByVisibleText(result);
		Thread.sleep(500);
	}
	
	public static void applyMRCStaffFilter(String staff) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[2]/td[4]/select"));   
		droplist.selectByVisibleText(staff);
		Thread.sleep(500);
	}
	
	public static void click_MRCClearFilter() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//input[@title='Clear filter']").click();
		Thread.sleep(1000);
		
	}
	
	public static void click_MRCError() throws InterruptedException{
		Thread.sleep(500);
		Alert alert=Unifia_Admin_Selenium.driver.switchTo().alert();
		alert.accept();
		Thread.sleep(1000);
		
	}
	
	public static void click_MRCDateFilter(String datefilter, String startDate, String endDate) throws InterruptedException{
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"reportrange\"]")).click();
		
		switch(datefilter){
			case "Today" : 
				driver.findElementByXPath("//li[contains(text(),'Today')]").click();
				Thread.sleep(1000);
				break;
			case "Yesterday" :
				driver.findElementByXPath("//li[contains(text(),'Yesterday')]").click();
				Thread.sleep(1000);
				break;
			case "Last 7 Days" :
				driver.findElementByXPath("//li[contains(text(),'Last 7 Days')]").click();
				Thread.sleep(1000);
				break;
			case "Last 30 Days" :
				driver.findElementByXPath("//li[contains(text(),'Last 30 Days')]").click();
				Thread.sleep(1000);
				break;
			case "This Month" :
				driver.findElementByXPath("//li[contains(text(),'This Month')]").click();
				Thread.sleep(1000);
				break;
			case "Last Month" :
				driver.findElementByXPath("//li[contains(text(),'Last Month')]").click();
				Thread.sleep(1000);
				break;
			case "Custom Range" :
				driver.findElementByXPath("//li[contains(text(),'Custom Range')]").click();
				driver.findElementByXPath("//input[@name='daterangepicker_start']").sendKeys(Keys.chord(Keys.CONTROL, "a"), startDate);	
				driver.findElementByXPath("//input[@name='daterangepicker_end']").sendKeys(Keys.chord(Keys.CONTROL, "a"),endDate); 		
				driver.findElementByXPath("//input[@name='daterangepicker_start']").click();
				Thread.sleep(1000);
				driver.findElementByXPath("//button[text()='Apply']").click();
		}
		Thread.sleep(500);
	}
	
	public static void click_MRCDateClearFilter() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//*[@id='clearfilter']").click();
		Thread.sleep(2000);
	}
}
