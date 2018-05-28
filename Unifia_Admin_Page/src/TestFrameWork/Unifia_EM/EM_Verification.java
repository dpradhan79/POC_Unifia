package TestFrameWork.Unifia_EM;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import TestFrameWork.Unifia_Admin_Selenium;

public class EM_Verification extends Unifia_Admin_Selenium {
	public static TestFrameWork.Unifia_Admin_Selenium UAS; 
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;

	public static String Verify_ProcedureRoom(String ProcRoom) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[1]")).getText();		
		if(Actual.equalsIgnoreCase(ProcRoom)){
			Result="Pass - Procedure Room ="+ProcRoom;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Procedure Room was supposed to be "+ProcRoom+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ExamDate(String Date) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[1]")).getText();		
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",Actual );
		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - Exam Date ="+Date;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Exam Date was supposed to be "+Date+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ExamID(String ExamID) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[2]")).getText();		
		if(Actual.equalsIgnoreCase(ExamID)){
			Result="Pass - Exam ID ="+ExamID;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Exam ID was supposed to be "+ExamID+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_Patient(String Patient) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[3]")).getText();		
		if(Actual.equalsIgnoreCase(Patient)){
			Result="Pass - Patient ID ="+Patient;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Patient ID was supposed to be "+Patient+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_PatientDate(String Date) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[4]")).getText();		
		IP_V.verifyDateFormat("Exam Management - Patient Scan In Date/Time",Actual );
		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - Patient Scan Date ="+Date;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Patient Scan Date was supposed to be "+Date+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_NumScpopes(String NumScopes) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[5]")).getText();		
		if(Actual.equalsIgnoreCase(NumScopes)){
			Result="Pass - # of Scopes ="+NumScopes;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - # of Scopes was supposed to be "+NumScopes+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ScopeIn(String Date) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[6]/div/div[2]/table/tbody/tr/td[1]")).getText();	
		IP_V.verifyDateFormat("Exam Management - Scope Scan In Date/Time",Actual );
		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - Scope scan in date/time ="+Date;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Scope scan in date/time was supposed to be "+Date+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ScopeModel(String ScopeModel) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[6]/div/div[2]/table/tbody/tr/td[2]")).getText();		
		if(Actual.equalsIgnoreCase(ScopeModel)){
			Result="Pass - Scope Model ="+ScopeModel;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Scope Model was supposed to be "+ScopeModel+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ScopeSerialNo(String ScopeSerialNo) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[6]/div/div[2]/table/tbody/tr/td[3]")).getText();		
		if(Actual.equalsIgnoreCase(ScopeSerialNo)){
			Result="Pass - Scope Serial Number ="+ScopeSerialNo;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Scope Serial Number was supposed to be "+ScopeSerialNo+" but it was "+Actual;
		}
		return Result;
	}
	public static String Verify_Preclean(String Date) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[6]/div/div[2]/table/tbody/tr/td[4]")).getText();		
		IP_V.verifyDateFormat("Exam Management - Pre-Clean Date/Time",Actual );
		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - Pre-Clean scan date/time ="+Date;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Pre-Clean scan date/time was supposed to be "+Date+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_PrecleanBeforePatient(String Status) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[6]/div/div[2]/table/tbody/tr/td[5]")).getText();		
		if(Actual.equalsIgnoreCase(Status)){
			Result="Pass - Pre-Clean Before Patient ="+Status;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Pre-Clean Before Patient was supposed to be "+Status+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_RefNo(String RefNo) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[6]/div/div[2]/table/tbody/tr/td[6]")).getText();		
		if(Actual.equalsIgnoreCase(RefNo)){
			Result="Pass - Ref# ="+RefNo;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Ref# was supposed to be "+RefNo+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_NoScopes() throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElement(By.xpath("//*[@id='examManagementGrid']/div[2]/table/tbody/tr/td[2]/div/div[2]/table/tbody/tr/td[6]/div/div[2]/table/tbody/tr/td")).getText();		
		if(Actual.equalsIgnoreCase("No scopes are associated with this exam.")){
			Result="Pass - No scopes are associated with this exam. is displayed";
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Expected: No scopes are associated with this exam. but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_RefNoFilter(String RefNo) throws InterruptedException{
		Thread.sleep(500);
		Actual=driver.findElementById("txtFilterScopeReference").getAttribute("value");		
		if(Actual.equalsIgnoreCase(RefNo)){
			Result="Pass - Ref# Filter ="+RefNo;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Ref# Filter was supposed to be "+RefNo+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_EM_DateRange(List<String> columnValues, Date StartDate, Date EndDate, DateFormat df) throws ParseException{
		String start=df.format(StartDate);
		String end=df.format(EndDate);
		for(String columnValue: columnValues){
			String date=columnValue.split(" ")[0];
			Date dateValue=df.parse(date);
			if(StartDate.equals(EndDate)){
				if(!date.equals(start)){
					UAS.resultFlag="#Failed!#";
					Result+="#Failed!#- expected date was "+start+" but it was "+date;
				}
			}else if((dateValue.before(StartDate)||dateValue.after(EndDate))){
				UAS.resultFlag="#Failed!#";
				Result+="#Failed!#- "+date+" is not in the range of "+start+" and "+end+"; ";
			}
		}
		if(!Result.contains("#Failed!#")){
			Result="Pass - The dates displayed are in the range of "+start+" and "+end;
		}
		return Result;
	}
	
	public static String Verify_EM_SearchFilters(){
		String res="";
		if(driver.findElementsById("txtFilterLocation").size()>0){
			res+="Pass- Procedure Room Search filter is present; ";
		}else{
			res+="#Failed!#- Procedure Room Search filter is not present; ";
		}
		if(driver.findElementsById("txtFilterExamId").size()>0){
			res+="Pass- ExamId Search filter is present; ";
		}else{
			res+="#Failed!#- ExamId Search filter is not present; ";
		}
		if(driver.findElementsById("txtFilterPatientId").size()>0){
			res+="Pass- PatientId Search filter is present; ";
		}else{
			res+="#Failed!#- PatientId Search filter is not present; ";
		}
		if(driver.findElementsById("txtFilterScopeCount").size()>0){
			res+="Pass- # of Scopes Search filter is present; ";
		}else{
			res+="#Failed!#- # of Scopes Search filter is not present; ";
		}
		if(driver.findElementsById("txtFilterScopeModel").size()>0){
			res+="Pass- ScopeModel Search filter is present; ";
		}else{
			res+="#Failed!#- ScopeModel Search filter is not present; ";
		}
		if(driver.findElementsById("txtFilterScopeSerialNumber").size()>0){
			res+="Pass- ScopeSerialNumber Search filter is present; ";
		}else{
			res+="#Failed!#- ScopeSerialNumber Search filter is not present; ";
		}
		if(driver.findElementsById("txtFilterScopePreCleanBefore").size()>0){
			res+="Pass- Scope PreClean Before Search filter is present; ";
		}else{
			res+="#Failed!#- Scope PreClean Before Search filter is not present; ";
		}
		if(driver.findElementsById("txtFilterScopeReference").size()>0){
			res+="Pass- Ref # Search filter is present; ";
		}else{
			res+="#Failed!#- Ref # Search filter is not present; ";
		}
		if(res.contains("#Failed!#")){
			UAS.resultFlag="#Failed!#";
		}
		return res;
	}
}
