package TestFrameWork.Unifia_IP;

import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;

public class IP_Actions extends Unifia_Admin_Selenium{
	public static GeneralFunc SE_Gen;
	

	public static void Click_Admin_Tab(){
		driver.findElementById("tab_admin").click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	public static void Click_InfectionPrevention() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.switchTo().defaultContent(); //switches out of the iframe
		Thread.sleep(1000);
		driver.switchTo().defaultContent();

		driver.findElement(By.xpath("//a[@href='/InfectionPrevention/InfectionPrevention']")).click();
		Thread.sleep(4*1000);
	}
	
	public static void Click_IP_Dashboard(){
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@href='/InfectionPrevention/InfectionPrevention/Dashboard']")).click();
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Click_SRM(){
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@href='/InfectionPrevention/InfectionPrevention/ScopeRecordManagement']")).click();
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Click_LD(){
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@href='/IFU/RoomDashboard']")).click();
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Click_EM() throws InterruptedException{
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@href='/InfectionPrevention/InfectionPrevention/ExamManagement']")).click();
		Thread.sleep(1000);

	}
	
	public static void Click_AuditLog() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[contains(text(),'Audit Log')]")).click();
		
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
		//driver.switchTo().frame(0); //switches into the iframe
	}
	
	
	public static void Click_Details(String RefNo) throws InterruptedException{
		Thread.sleep(500);
		driver.findElementById("details_"+RefNo).click();
		Thread.sleep(2000);

		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public static void ScopeFilter(String ScopeSerialNo) throws InterruptedException{
		Thread.sleep(500);
		Select droplist = new Select(driver.findElementById("ScopeSrNo"));   
		droplist.selectByVisibleText(ScopeSerialNo);
		Thread.sleep(500);
	}
	
	public static void DateFilter(String StartDate,String EndDate) throws InterruptedException{
		int divNum=4;
		String SRMClass=driver.findElementByXPath("//a[@href='/InfectionPrevention/InfectionPrevention/ScopeRecordManagement']/ancestor::li").getAttribute("class");
		if(SRMClass.equalsIgnoreCase("active")){
			divNum=5;
		}
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"reportrange\"]")).click();
		//driver.findElement(By.xpath("/html/body/div[4]/div[1]/ul/li[4]")).click(); //this is for 'last 30 days then you don't need the 'apply' click 
		//driver.findElement(By.xpath("/html/body/div[3]/div[1]/ul/li[7]")).click(); //this is for custom rang and then need to click 'apply' 
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[1]/ul/li[7]")).click(); //this is for custom rang and then need to click 'apply'

		
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[2]/div[1]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), StartDate);	
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[3]/div[1]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"),EndDate); 		
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[2]/div[1]/input")).click();
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[1]/div/button[1]")).click();
		Thread.sleep(500);
	}

	public static void ApplyFilter() throws InterruptedException{
		driver.findElementById("filteredWorkflows").click();
		Thread.sleep(500);
	}
	
	public static void ClearFilter() throws InterruptedException{
		driver.findElementById("clearfilter").click();
		Thread.sleep(500);
	}
	
	public static void ApplyMRCFilter() throws InterruptedException{
		driver.findElementById("filteredMrcRecords").click();
		Thread.sleep(500);
	}
	
	public static String getHeader(String xpathOfHeader){
		String header=driver.findElementByXPath(xpathOfHeader).getText();
		return header;
	}
	
	public static String getScopeCount(String xpathOfScopeCount){
		String scopeCount=driver.findElementByXPath(xpathOfScopeCount).getText();
		return scopeCount;
	}
	
	public static String getColor(String xpathOfColor){
		String color=driver.findElementByXPath(xpathOfColor).getCssValue("background-color");
		return color;
	}
	
	public static void Click_ActiveProcedureRooms() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[1]/td[2]").click();
		Thread.sleep(2000);
		//*[@id="divDashboardSummary"]/table/tbody/tr[1]/td[2]
	}
	

}
