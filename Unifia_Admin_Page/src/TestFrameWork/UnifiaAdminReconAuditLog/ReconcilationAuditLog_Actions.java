package TestFrameWork.UnifiaAdminReconAuditLog;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class ReconcilationAuditLog_Actions extends Unifia_Admin_Selenium{
	
	private static GeneralFunc gf;
	
	private static String refnoXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[1]/input";
	private static String scopeNameXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[3]/input";
	private static String scanDateTimeXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[4]/input";
	private static String commentsXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[5]/input";
	private static String userNameXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[6]/input";
	private static String locationXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[7]/input";
	private static String whatChangedXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[8]/input";
	private static String previousValueXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[9]/input";
	private static String newValueXpath="//*[@id='auditLogGrid']/div[1]/table/tr[2]/td[10]/input";
	
	
	public static void Refresh_AuditLog_Grid() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementById("refresh_jqgrid_audit_log").click();
		Thread.sleep(500);
	}
	
	public static String GetGridID_AuditLog_ByRefNo(String RefNo) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		Thread.sleep(3000);
		GridID=driver.findElement(By.xpath("//table['jqgrid_audit_log']/tbody/tr/td[text()='"+RefNo+"']//..")).getAttribute("id");		
		return GridID;
	}
	
	public static String GetGridID_AuditLog_ByRefNo(String refNo, String whatChanged) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		Thread.sleep(3000);
		//Verify the number of rows in the table
		int totalRows=gf.getTableRowCount("//*[@id='auditLogGrid']/div[2]/table/tbody");
		int i=1;
		while(i<=totalRows){
			if(whatChanged.equalsIgnoreCase(driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+i+"]/td[8]").getText())){
				if(refNo.equalsIgnoreCase(driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+i+"]/td[1]").getText())){
					break;
				}
			}else{
				i++;
			}
		}
			
		GridID=String.valueOf(i);		
		return GridID;
	}
	
		
	public static void Select_AuditLog_To_Modify(String GridID, String RefNo) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(500);
		WebElement myElemment=driver.findElementByXPath("//table['jqgrid_audit_log']/tbody/tr/td[text()='"+RefNo+"']");

		Actions action = new Actions(driver);
		action.doubleClick(myElemment);
		action.perform();
	}
	
	public static void ClearAuditLogSrchCritera() throws InterruptedException{
		Thread.sleep(100);
		driver.findElementByXPath("//input[@title='Clear filter']").click();

		Thread.sleep(300);

	}
	public static void Search_RefNo(String RefNo) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementByXPath(refnoXpath).clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementByXPath(refnoXpath).sendKeys(RefNo);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		System.out.println(RefNo+" was entered in the Reference number search.");
		Thread.sleep(1500);
		driver.findElementByXPath(refnoXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1500); 
	}
	
	public static void Search_ScopeName(String ScopeName) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElementByXPath(scopeNameXpath).clear();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementByXPath(scopeNameXpath).sendKeys(ScopeName);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		System.out.println(ScopeName+" was entered in the Scope Name.");
		Thread.sleep(1500);
		driver.findElementByXPath(scopeNameXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1500); 
	}
	public static void Search_AL_Date(String Date) throws InterruptedException{
		driver.findElementById("gs_ReconciliationDate").clear();
		driver.findElementById("gs_ReconciliationDate").sendKeys(Date);
		driver.findElementById("gs_ReconciliationDate").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}
	
	public static void Search_ScanDateTime(String Date) throws InterruptedException{
		driver.findElementByXPath(scanDateTimeXpath).clear();
		driver.findElementByXPath(scanDateTimeXpath).sendKeys(Date);
		driver.findElementByXPath(scanDateTimeXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}
	
	public static void Search_Comments(String Comments) throws InterruptedException{
		driver.findElementByXPath(commentsXpath).clear();
		driver.findElementByXPath(commentsXpath).sendKeys(Comments);
		driver.findElementByXPath(commentsXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1000);

	}
	
	public static void Search_UserName(String user) throws InterruptedException{
		driver.findElementByXPath(userNameXpath).clear();
		driver.findElementByXPath(userNameXpath).sendKeys(user);
		driver.findElementByXPath(userNameXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}

	public static void Search_Location(String Location) throws InterruptedException{
		driver.findElementByXPath(locationXpath).clear();
		driver.findElementByXPath(locationXpath).sendKeys(Location);
		driver.findElementByXPath(locationXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}

	public static void Search_WhatChanged(String WhatChanged) throws InterruptedException{
		driver.findElementByXPath(whatChangedXpath).clear();
		driver.findElementByXPath(whatChangedXpath).sendKeys(WhatChanged);
		driver.findElementByXPath(whatChangedXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}

	public static void Search_ScanItemBefore(String ScanBefore) throws InterruptedException{
		driver.findElementByXPath(previousValueXpath).clear();
		driver.findElementByXPath(previousValueXpath).sendKeys(ScanBefore);
		driver.findElementByXPath(previousValueXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}

	public static void Search_ScanItemAfter(String ScanAfter) throws InterruptedException{
		driver.findElementByXPath(newValueXpath).clear();
		driver.findElementByXPath(newValueXpath).sendKeys(ScanAfter);
		driver.findElementByXPath(newValueXpath).sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}
	
	public static String GetGridID_AuditLog_ByScanDate(String ScanDateTime) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		Thread.sleep(2000);
		int totalRows=gf.getTableRowCount("//*[@id='auditLogGrid']/div[2]/table/tbody");
		int i=1;
		boolean isFound=false;
		while(i<=totalRows){
			String expScanDateTime=ScanDateTime.replaceAll("/0", "/");
			String appScanDateTime=driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+i+"]/td[4]").getText();
			appScanDateTime=appScanDateTime.replaceAll("/0", "/");
			if(expScanDateTime.contains(appScanDateTime)){
				isFound=true;
				break;
			}else{
				i++;
			}
		}
	
		GridID=String.valueOf(i);	
		return GridID;
	}

	public static void Search_ReconDateTime(String Date) throws InterruptedException{
		driver.findElementById("gs_ReconciliationDate").clear();
		driver.findElementById("gs_ReconciliationDate").sendKeys(Date);
		driver.findElementById("gs_ReconciliationDate").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}
	
	public static String GetGridID_AuditLog_ByReconDate(String ReconDateTime) throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		Thread.sleep(2000);
		GridID=driver.findElement(By.xpath("//table['jqgrid_audit_log']/tbody/tr/td[contains(text(),'"+ReconDateTime+"')]//..")).getAttribute("id");		
		return GridID;
	}
	
	public static String GetGridID_AuditLog_ByComments(String comments) throws InterruptedException{
		boolean isFound=false;
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		Thread.sleep(2000);
		//Verify the number of rows in the table
		int totalRows=gf.getTableRowCount("//*[@id='auditLogGrid']/div[2]/table/tbody");
		int i=1;
		try{
			while(i<=totalRows){
				if(comments.equalsIgnoreCase(driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+i+"]/td[5]").getText())){
					isFound=true;
					break;
				}else{
					i++;
				}
			}
		}catch (Exception e){
			e.getMessage();
			GridID="0";
		}
		if (!isFound){
			GridID="0";
		}else{
			GridID=String.valueOf(i);
		}
				
		return GridID;
	}
	
	public static void click_AuditSearch() throws InterruptedException{
		Thread.sleep(1000);
		driver.findElementByXPath("//*[@id='auditLogGrid']/div[1]/table/tr[1]/th[11]/input").click();
		Thread.sleep(1000);
	}
	
}
