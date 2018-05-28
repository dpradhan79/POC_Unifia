package TestFrameWork.UnifiaAdminReconAuditLog;

import TestFrameWork.Unifia_Admin_Selenium;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminReconAuditLog.*;

public class ReconcilationAuditLog_Verification extends Unifia_Admin_Selenium{
	
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	private static GeneralFunc gf;
	
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions RALA;
	
	public static String Verify_Username(String GridID, String Username){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[6]").getText();
		//System.out.println("SSID field value is:"+Actual);
		if(Username.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Username;
		}
		return Result;
	}	
	
	public static String Verify_ScopeName(String GridID, String ScopeName){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[3]").getText(); 
		//System.out.println("SSID field value is:"+Actual);
		if(ScopeName.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else if(ScopeName.equalsIgnoreCase("-") && Actual.equalsIgnoreCase("")){
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ScopeName+"; Bug-12341: Audit log Scope Name field and Comment field should display a '-' when there is no value instead of being blank to match the other fields on the audit log page";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ScopeName;
		}
		return Result;
	}
	
	public static String Verify_ScanDateTime(String GridID, String Date) throws ParseException{
			
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[4]").getText(); 
		
		IP_V.verifyDateFormat("AuditLog - Scan Date Time",Actual );
		
		//Verify the time difference'
		SimpleDateFormat format = new SimpleDateFormat(expDateFormat);
		Date d1 = null;
		Date d2 = null;
		if(Date.equalsIgnoreCase("-")){
			if(Actual.equalsIgnoreCase(Date)){				
				Result="Pass - difference of less than 1 minute";
			}else{
				Unifia_Admin_Selenium.resultFlag="#Failed!#";
				Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Date;
			}
		}else {
			d1 = format.parse(Date);
	        d2 = format.parse(Actual);
			long TimeDiff = d1.getTime() - d2.getTime(); 
			
			System.out.println("Date field value is:"+Actual);
			if(TimeDiff==0){
				Result="Pass";
			}else if (TimeDiff<=60000){
				Result="Pass - difference of less than 1 minute";
			}else{
				Unifia_Admin_Selenium.resultFlag="#Failed!#";
				Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Date;
			}
		}
		return Result;
	}
	
	
	public static String Verify_Comment(String GridID, String Comment){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[5]").getText(); 
		//System.out.println("SSID field value is:"+Actual);
		if(Comment.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Comment;
		}
		return Result;
	}
	
	public static String Verify_Location(String GridID, String Location){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[7]").getText(); 
		//System.out.println("SSID field value is:"+Actual);
		if(Location.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Location;
			if(Location.equalsIgnoreCase("-")){
				Result=Result+" Bug 12676 opened for this issue.";
			}
		}
		return Result;
	}
	
	public static String Verify_PreviousValue(String GridID, String PreviousValue){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[9]").getText(); 
		//System.out.println("SSID field value is:"+Actual);
		
		if(PreviousValue.contains("/")||PreviousValue.contains(":")){
			IP_V.verifyDateFormat("AuditLog - Previous Value Date",Actual );
		}
		if(PreviousValue.equalsIgnoreCase(" ")){
			PreviousValue="";
		}
		if(PreviousValue.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+PreviousValue;
		}
		return Result;
	}
	
	public static String verify_PreviousValueDate(String gridID, String previousValue) throws ParseException{
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[9]").getText(); 
		
		IP_V.verifyDateFormat("AuditLog - Previous Value Date",Actual );
		
		if(previousValue.equalsIgnoreCase("-")){
			if(Actual.equalsIgnoreCase(previousValue)){				
				Result="Pass - difference of less than 1 minute";
			}else{
				Unifia_Admin_Selenium.resultFlag="#Failed!#";
				Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+previousValue;
			}
		}else {
			try{
				//Verify the time difference'
				SimpleDateFormat format = new SimpleDateFormat(expDateFormat);
				Date d1 = null;
				Date d2 = null;
				d1 = format.parse(previousValue);
		        d2 = format.parse(Actual);
				long TimeDiff = d1.getTime() - d2.getTime(); 
				
				System.out.println("previeous field value is:"+Actual);
				if(TimeDiff==0){
					Result="Pass";
				}else if (TimeDiff<=60000){
					Result="Pass - difference of less than 1 minute";
				}else{
					Unifia_Admin_Selenium.resultFlag="#Failed!#";
					Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+previousValue;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return Result;
		
}
	
	public static String Verify_ModifiedValue(String GridID, String ModifiedValue){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[10]").getText(); 
		//System.out.println("SSID field value is:"+Actual);
		
		if(ModifiedValue.contains("/")||ModifiedValue.contains(":")){
			IP_V.verifyDateFormat("AuditLog - Modified Value Date",Actual );
		}
		if(ModifiedValue.equalsIgnoreCase(" ")){
			ModifiedValue="";
		}
		if(ModifiedValue.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+ModifiedValue;
		}
		return Result;
	}
	
	public static String Verify_Test(String GridID, String Test){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[2]").getText(); 
		System.out.println("Password field value is:"+Actual);
		if(Test.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Test;
		}
		return Result;
	}
	
	public static String Verify_RefNum(String GridID, String refNum){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[1]").getText(); 
		System.out.println("Ref# field value is:"+Actual);
		if(refNum.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+refNum;
		}
		return Result;
	}
		
	public static String Verify_ReconDate(String GridID, String Date) throws ParseException{
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[2]").getText();
		
		IP_V.verifyDateFormat("AuditLog - Recon Date",Actual );
		
		//Verify the time difference'
		SimpleDateFormat format = new SimpleDateFormat(expDateFormat);
		Date d1 = null;
		Date d2 = null;
		d1 = format.parse(Date);
        d2 = format.parse(Actual);
		long TimeDiff = d1.getTime() - d2.getTime(); 
		
		System.out.println("Date field value is:"+Actual);
		if(TimeDiff==0){
			Result="Pass";
		}else if (TimeDiff<60000){
			Result="Pass - difference of less than 1 minute";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+Date;
		}
		return Result;
	}

	public static String Verify_WhatChanged(String GridID, String WhatChanged){
		Actual= driver.findElementByXPath("//*[@id='auditLogGrid']/div[2]/table/tbody/tr["+GridID+"]/td[8]").getText(); 
		System.out.println("WhatChanged field value is:"+Actual);
		if(WhatChanged.equalsIgnoreCase(Actual)){
			Result="Pass";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!#:  The Actual value was:  "+Actual+", but the expected value was:  "+WhatChanged;
		}
		return Result;
	}
	
	
public static String VerifyReconsiliationAuditLog(String refNum,String reconDate, String scopeName, String scanDate, String comments, String userName, String location, String whatchanged, String prevValue, String ModValue) throws InterruptedException, ParseException{
		
		String Result_refNum, Result_reconDate,Result_scopeName,Result_scanDate,Result_comments,Result_userName,Result_location,Result_whatchanged,Result_prevValue,Result_ModValue;
		
		//Filter with the column values
		//RefNum
		Integer cntr;
		for (cntr=1;cntr<=10;cntr++) {
			Thread.sleep(1500);
			driver.findElementByXPath(".//*[@id='gview_jqgrid_audit_log']/div[2]/div/table/thead/tr[2]/th["+cntr+"]/div/table/tbody/tr/td[3]/a").click();
		}
		if (refNum!="" && refNum!="-"){
			Thread.sleep(1500);
			driver.findElementById("gs_ReferenceNumber");
			driver.findElementById("gs_ReferenceNumber").clear();
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.findElementById("gs_ReferenceNumber").sendKeys(refNum);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println(refNum+" was entered in the Audit Log Ref # field.");
			driver.findElementById("gs_ReferenceNumber").sendKeys(Keys.RETURN);
			Thread.sleep(1500);
		}
		//ScopeName
		if (scopeName!="" && scopeName!="-"){
			Thread.sleep(1500);
			driver.findElementById("gs_ScopeName");
			driver.findElementById("gs_ScopeName").clear();
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.findElementById("gs_ScopeName").sendKeys(scopeName);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println(scopeName+" was entered in the Audit Log Scope Name 	field.");
			driver.findElementById("gs_ScopeName").sendKeys(Keys.RETURN);
			Thread.sleep(1500);
		}
		//Comments
		if (comments!="" && comments!="-"){
			Thread.sleep(1500);
			driver.findElementById("gs_Comments");
			driver.findElementById("gs_Comments").clear();
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.findElementById("gs_Comments").sendKeys(comments);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println(comments+" was entered in the Audit Log Comments field.");
			driver.findElementById("gs_Comments").sendKeys(Keys.RETURN);
			Thread.sleep(1500);
		}
		//userName
		if (userName!="" && userName!="-"){
			Thread.sleep(1500);
			driver.findElementById("gs_UserID");
			//driver.findElementById("gs_UserID").clear();
			Select droplist = new Select(driver.findElementById("gs_UserID"));   
			droplist.selectByVisibleText("");
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			//driver.findElementById("gs_UserID").sendKeys(userName);
			Select droplist1 = new Select(driver.findElementById("gs_UserID"));   
			droplist1.selectByVisibleText(userName);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println(userName+" was entered in the Audit Log Username field.");
			//driver.findElementById("gs_UserID").sendKeys(Keys.RETURN);
			Thread.sleep(1500);
		}
		//Location
		if (location!="" && location!="-"){
			Thread.sleep(1500);
			driver.findElementById("gs_LocationID");
			//driver.findElementById("gs_LocationID").clear();
			Select droplist = new Select(driver.findElementById("gs_LocationID"));   
			droplist.selectByVisibleText("");
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			Select droplist1 = new Select(driver.findElementById("gs_LocationID"));   
			droplist1.selectByVisibleText(location);
			//driver.findElementById("gs_LocationID").sendKeys(location);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println(location+" was entered in the Audit Log Location field.");
			//driver.findElementById("gs_LocationID").sendKeys(Keys.RETURN);
			Thread.sleep(1500);
		}
		//what changed
		if (whatchanged!="" && whatchanged!="-"){
			Thread.sleep(1500);
			driver.findElementById("gs_ReconciliationTypeID");
			//driver.findElementById("gs_ReconciliationTypeID").clear();
			Select droplist = new Select(driver.findElementById("gs_ReconciliationTypeID"));   
			droplist.selectByVisibleText("");
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			//driver.findElementById("gs_ReconciliationTypeID").sendKeys(whatchanged);
			Select droplist1 = new Select(driver.findElementById("gs_ReconciliationTypeID"));   
			droplist1.selectByVisibleText(whatchanged);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println(whatchanged+" was entered in the Audit Log what changed field.");
			//driver.findElementById("gs_ReconciliationTypeID").sendKeys(Keys.RETURN);
			Thread.sleep(1500);
		}
		//prevValue
		if (prevValue!="" && prevValue!="-"){
			Thread.sleep(1500);
			driver.findElementById("gs_OldScanItemValue");
			driver.findElementById("gs_OldScanItemValue").clear();
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.findElementById("gs_OldScanItemValue").sendKeys(prevValue);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println(prevValue+" was entered in the Audit Log Previous Value field.");
			driver.findElementById("gs_OldScanItemValue").sendKeys(Keys.RETURN);
			Thread.sleep(1500);
		}
		//Modified Value
		if (ModValue!="" && ModValue!="-"){
			Thread.sleep(5000);
			driver.findElementById("gs_NewScanItemValue");
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			driver.findElementById("gs_NewScanItemValue").clear();
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.findElementById("gs_NewScanItemValue").sendKeys(ModValue);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			System.out.println(ModValue+" was entered in the Audit Log Modified Value field.");
			driver.findElementById("gs_NewScanItemValue").sendKeys(Keys.RETURN);
			Thread.sleep(1500); 
		}
		Thread.sleep(10000); 
		String gridID;
		gridID=RALA.GetGridID_AuditLog_ByRefNo(refNum,whatchanged);

		if (refNum!=""){
			Result_refNum=Verify_RefNum(gridID,refNum);
		}
		else{
			Result_refNum="Pass";
		}
		if (reconDate!=""){
			Result_reconDate=Verify_ReconDate(gridID,reconDate);
		}
		else{
			Result_reconDate="Pass";
		}
		if (scopeName!=""){
			Result_scopeName=Verify_ScopeName(gridID,scopeName);
		}
		else{
			Result_scopeName="Pass";
		}
		if (scanDate!=""){
			Result_scanDate=Verify_ScanDateTime(gridID,scanDate);
		}
		else{
			Result_scanDate="Pass";
		}
		if (comments!=""){	
			Result_comments=Verify_Comment(gridID,comments);
		}
		else{
			Result_comments="Pass";
		}
		if (userName!=""){
			Result_userName=Verify_Username(gridID,userName);
		}
		else{
			Result_userName="Pass";
		}
		if (location!=""){
			Result_location=Verify_Location(gridID,location);
		}
		else{
			Result_location="Pass";
		}
		if (whatchanged!=""){
			Result_whatchanged= Verify_WhatChanged(gridID,whatchanged);
		}
		else{
			Result_whatchanged="Pass";
		}
		if (prevValue!=""){
			Result_prevValue= Verify_PreviousValue(gridID,prevValue);
		}
		else{
			Result_prevValue="Pass";
		}
		if (ModValue!=""){
			Result_ModValue=Verify_ModifiedValue(gridID,ModValue);
		}
		else{
			Result_ModValue="Pass";
		}
		//verify all fields are passed
		String ResultAll="Result_refNum="+Result_refNum+",\r\nResult_reconDate="+Result_reconDate+",\r\nResult_scopeName="+Result_scopeName+",\r\nResult_scanDate="+Result_scanDate+",\r\nResult_comments="+Result_comments+",\r\nResult_userName="+Result_userName+",\r\nResult_location="+Result_location+",\r\nResult_whatchanged="+Result_whatchanged+",\r\nResult_prevValue="+Result_prevValue+",\r\nResult_ModValue="+Result_ModValue;
		Thread.sleep(5000);
		return ResultAll;
	}	

		public static Integer compRowsCountinAL(String refNo, String ScopeName,String comment,Integer numOfChanges) throws InterruptedException{
		RALA.Search_RefNo(refNo);
		RALA.Search_ScopeName(ScopeName);
		RALA.Search_Comments(comment);
		int totalRows=gf.getTableRowCount("//*[@id='auditLogGrid']/div[2]/table/tbody");
		if (numOfChanges==totalRows){
		return 0;
			}else{
				return totalRows;
			}
		}
		
		public static Integer compRowsCountinAL_MRC(String comment,Integer numOfChanges) throws InterruptedException{
			Integer totalRows;
			RALA.Search_Comments(comment);
			totalRows=gf.getTableRowCount("//*[@id='auditLogGrid']/div[2]/table/tbody");
			if (numOfChanges==totalRows){
				return 0;
			}else{
				return totalRows;
			}
		}
}
		

