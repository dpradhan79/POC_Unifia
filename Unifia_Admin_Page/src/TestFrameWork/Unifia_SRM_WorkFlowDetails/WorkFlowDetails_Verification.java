package TestFrameWork.Unifia_SRM_WorkFlowDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class WorkFlowDetails_Verification extends Unifia_Admin_Selenium {
	public static TestFrameWork.Unifia_Admin_Selenium UAS; 
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	
	public static String Verify_ScopeModel(String ScopeModel){
		Actual=driver.findElementById("ScopeModel").getAttribute("value");		
		if(Actual.equalsIgnoreCase(ScopeModel)){
			Result="Pass - Scope Model ="+ScopeModel;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Scope Model was supposed to be "+ScopeModel+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ScopeName(String ScopeName){
		Actual=driver.findElementById("ScopeName").getAttribute("value");		
		if(Actual.equalsIgnoreCase(ScopeName)){
			Result="Pass - Scope Name ="+ScopeName;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Scope Name was supposed to be "+ScopeName+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ScopeSerialNum(String ScopeSerialNum){
		Actual=driver.findElementById("ScopeSrNo").getAttribute("value");		
		if(Actual.equalsIgnoreCase(ScopeSerialNum)){
			Result="Pass - Scope Serial Number ="+ScopeSerialNum;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Scope Serial Number was supposed to be "+ScopeSerialNum+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_RefNum(String RefNum){		
//		Actual=driver.findElement(By.xpath("//*[@id=\"workflowform\"]/h4")).getText();
		Actual=driver.findElement(By.xpath("//*[@id=\"workflowform\"]/div/div[1]/h4")).getText();
		if(Actual.equalsIgnoreCase("Workflow Details for Reference Number "+RefNum)){
			Result="Pass - Ref# ="+RefNum;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Ref# was supposed to be "+RefNum+" but it was "+Actual;
		}
		return Result;
	}
	public static String Verify_Comment(String Comment){
		Actual=driver.findElementById("comments").getAttribute("value");
		
		if(Actual.trim().equalsIgnoreCase(Comment.trim())){
			Result="Pass - The comment ="+Comment;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - The comment was supposed to be "+Comment+" but it was "+Actual;
		}
		return Result;
	}
	
	//Procedure Room
	public static String Verify_PR(String Location){
		Actual=driver.findElementById("ProcedureRoom").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="Procedure Room 1";
			break;
		case "22":
			Actual="Procedure Room 2";
			break;
		case "23":
			Actual="Procedure Room 3";
			break;
		case "24":
			Actual="Procedure Room 4";
			break;
		case "25":
			Actual="Procedure Room 5";
			break;
		case "26":
			Actual="Procedure Room 6";
			break;
		case "27":
			Actual="Procedure Room 7";
			break;
		case "28":
			Actual="Procedure Room 8";
			break;
		case "29":
			Actual="Procedure Room 9";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Procedure Room  value ="+Actual);

		if(Actual.equalsIgnoreCase(Location)){
			Result="Pass - Procedure Room ="+Location;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Procedure Room was supposed to be "+Location+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ExamDate(String ExamDate){
		Actual=driver.findElementById("ExamDateTime").getAttribute("value");
		System.out.println("Actual Exam Date ="+Actual);
		
		IP_V.verifyDateFormat("SRM Details - Exam Date Time",Actual );
		
		if(Actual.equalsIgnoreCase(ExamDate)){
			Result="Pass - Exam Date ="+ExamDate;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(ExamDate)){
						Result="Pass - Exam Date ="+ExamDate;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Exam Date was supposed to be "+ExamDate+" but it was "+Actual+".";
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Exam Date was supposed to be "+ExamDate+" but it was "+Actual+".";
			}
			
		}
		return Result;
	}

	public static String Verify_ProcStart(String Date){
		//Actual=driver.findElementById("CycleDetails_ProcedureRoomFields_5__FieldValue").getAttribute("value");
		Actual=driver.findElementById("ProcedureStartDate").getAttribute("value");
		System.out.println("Actual Procedure Start ="+Actual);
		
		IP_V.verifyDateFormat("SRM Details - Procedure Start Date Time",Actual );

		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - Procedure Start ="+Date;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(Date)){
						Result="Pass - Procedure Start ="+Date;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Procedure Start was supposed to be "+Date+" but it was "+Actual;
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Procedure Start was supposed to be "+Date+" but it was "+Actual;
			}
		}
		return Result;
	}

	public static String Verify_ProcEnd(String Date){
		//Actual=driver.findElementById("CycleDetails_ProcedureRoomFields_6__FieldValue").getAttribute("value");
		Actual=driver.findElementById("ProcedureEndDate").getAttribute("value");
		System.out.println("Actual Procedure End ="+Actual);
		
		IP_V.verifyDateFormat("SRM Details - Procedure End Date Time",Actual );

		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - Procedure End ="+Date;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(Date)){
						Result="Pass - Procedure End ="+Date;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Procedure End was supposed to be "+Date+" but it was "+Actual;
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Procedure End was supposed to be "+Date+" but it was "+Actual;
			}
		}
		return Result;
	}

	public static String Verify_Physician(String Staff){ 
		boolean isFound = false;
		/*Actual=driver.findElementById("PhysicianID").getAttribute("value");
		switch (Actual) {
		case "1":
			Actual="MD01";
			break;
		case "2":
			Actual="MD02";
			break;
		case "3":
			Actual="MD03";
			break;
		case "4":
			Actual="MD04";
			break;
		case "5":
			Actual="MD05";
			break;
		case "6":
			Actual="MD06";
			break;
		case "7":
			Actual="MD07";
			break;
		case "8":
			Actual="MD08";
			break;
		case "9":
			Actual="MD09";
			break;
		case "10":
			Actual="MD10";
			break;
		case "11":
			Actual="MD11";
			break;
		case "12":
			Actual="MD12";
			break;
		case "13":
			Actual="MD13";
			break;
		case "14":
			Actual="MD14";
			break;
		case "15":
			Actual="MD15";
			break;
		case "16":
			Actual="MD16";
			break;
		case "17":
			Actual="MD17";
			break;
		case "18":
			Actual="MD18";
			break;
		case "19":
			Actual="MD19";
			break;
		case "20":
			Actual="MD20";
			break;
			
		default:
			Actual="";
			break;
		}*/
		
		String selectedOptions=driver.findElementByXPath("//*[@id='PhysicianID']/following-sibling::p/span").getText();
		
		if (Staff.trim().equalsIgnoreCase("")){
			if (selectedOptions.trim().equalsIgnoreCase("")){
				Result="Pass - PR in staff ="+Staff;
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - PR physician was supposed to be "+Staff+" but it was "+selectedOptions;
			}
		}else{
		
			String [] actualSelected=selectedOptions.trim().split(",");
			String[] expSelected=Staff.trim().split(",");
					
			for (int cnt=0 ; cnt<actualSelected.length;cnt++){
				isFound=false;
				String actual=actualSelected[cnt].trim();
				for (int expCnt=0; expCnt<expSelected.length;expCnt++){
					if (expSelected[expCnt].trim().equalsIgnoreCase(actual)){
						isFound=true;
						break;
					}
				}
				if (!isFound){
					break;
				}
			}
			
			
			if (isFound){
				Result="Pass - PR in staff ="+Staff;
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - PR physician was supposed to be "+Staff+" but it was "+selectedOptions;
			}
		}
		
		System.out.println("Actual PR in Staff="+selectedOptions);
		return Result;
	}	
	public static String Verify_PRInStaff(String Staff){ 
		Actual=driver.findElementById("StaffID").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="T01";
			break;
		case "22":
			Actual="T02";
			break;
		case "23":
			Actual="T03";
			break;
		case "24":
			Actual="T04";
			break;
		case "25":
			Actual="T05";
			break;
		case "26":
			Actual="T06";
			break;
		case "27":
			Actual="T07";
			break;
		case "28":
			Actual="T08";
			break;
		case "29":
			Actual="T09";
			break;
		case "30":
			Actual="T10";
			break;
		case "31":
			Actual="T11";
			break;
		case "32":
			Actual="T12";
			break;
		case "33":
			Actual="T13";
			break;
		case "34":
			Actual="T14";
			break;
		case "35":
			Actual="T15";
			break;
		case "36":
			Actual="T16";
			break;
		case "37":
			Actual="T17";
			break;
		case "38":
			Actual="T18";
			break;
		case "39":
			Actual="T19";
			break;
		case "40":
			Actual="T20";
			break;
			
		default:
			Actual="";
			break;
		}
		System.out.println("Actual PR in Staff="+Actual);

		if(Actual.equalsIgnoreCase(Staff)){
			Result="Pass - PR in staff ="+Staff;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - PR in staff was supposed to be "+Staff+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_PreCleanStaff(String Staff){ 
		Actual=driver.findElementById("PreCleanStaffID").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="T01";
			break;
		case "22":
			Actual="T02";
			break;
		case "23":
			Actual="T03";
			break;
		case "24":
			Actual="T04";
			break;
		case "25":
			Actual="T05";
			break;
		case "26":
			Actual="T06";
			break;
		case "27":
			Actual="T07";
			break;
		case "28":
			Actual="T08";
			break;
		case "29":
			Actual="T09";
			break;
		case "30":
			Actual="T10";
			break;
		case "31":
			Actual="T11";
			break;
		case "32":
			Actual="T12";
			break;
		case "33":
			Actual="T13";
			break;
		case "34":
			Actual="T14";
			break;
		case "35":
			Actual="T15";
			break;
		case "36":
			Actual="T16";
			break;
		case "37":
			Actual="T17";
			break;
		case "38":
			Actual="T18";
			break;
		case "39":
			Actual="T19";
			break;
		case "40":
			Actual="T20";
			break;
			
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Preclean Staff="+Actual);

		if(Actual.equalsIgnoreCase(Staff)){
			Result="Pass - Preclean staff ="+Staff;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Preclean staff was supposed to be "+Staff+" but it was "+Actual;
		}
		return Result;
	}
	
	public static String Verify_PreClean(String PreClean){
		Actual=driver.findElementById("PrecleanComplete").getAttribute("value");
		switch (Actual) {
		case "0":
			Actual="No";
			break;
		case "1":
			Actual="Yes";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual PreClean value ="+Actual);

		if(Actual.equalsIgnoreCase(PreClean)){
			Result="Pass - PreClean ="+PreClean;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - PreClean was supposed to be "+PreClean+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_Patient(String Patient){
		Actual=driver.findElementById("PatientIDText").getAttribute("value");
		
		if(Actual.equalsIgnoreCase(Patient)){
			Result="Pass - Patient ="+Patient;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Patient was supposed to be "+Patient+" but it was "+Actual;
		}
		return Result;
	}

	//Soiled Area:
	public static String Verify_SoiledArea(String Location){
		Actual=driver.findElementById("SoiledArea").getAttribute("value");
		switch (Actual) {
		case "41":
			Actual="Sink 1";
			break;
		case "42":
			Actual="Sink 2";
			break;
		case "43":
			Actual="Sink 3";
			break;
		case "44":
			Actual="Sink 4";
			break;
		case "45":
			Actual="Sink 5";
			break;
		case "46":
			Actual="Sink 6";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Soiled Area value ="+Actual);

		if(Actual.equalsIgnoreCase(Location)){
			Result="Pass - Soiled Area ="+Location;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Soiled Area was supposed to be "+Location+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_SoiledStaff(String Staff){ 
		Actual=driver.findElementById("LTMCStaff").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="T01";
			break;
		case "22":
			Actual="T02";
			break;
		case "23":
			Actual="T03";
			break;
		case "24":
			Actual="T04";
			break;
		case "25":
			Actual="T05";
			break;
		case "26":
			Actual="T06";
			break;
		case "27":
			Actual="T07";
			break;
		case "28":
			Actual="T08";
			break;
		case "29":
			Actual="T09";
			break;
		case "30":
			Actual="T10";
			break;
		case "31":
			Actual="T11";
			break;
		case "32":
			Actual="T12";
			break;
		case "33":
			Actual="T13";
			break;
		case "34":
			Actual="T14";
			break;
		case "35":
			Actual="T15";
			break;
		case "36":
			Actual="T16";
			break;
		case "37":
			Actual="T17";
			break;
		case "38":
			Actual="T18";
			break;
		case "39":
			Actual="T19";
			break;
		case "40":
			Actual="T20";
			break;
			
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Sink in Staff="+Actual);

		if(Actual.equalsIgnoreCase(Staff)){
			Result="Pass - sink in staff ="+Staff;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - sink in staff was supposed to be "+Staff+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_LT(String LT){
		Actual=driver.findElementById("LeakTestStatus").getAttribute("value");
		switch (Actual) {
		case "0":
			Actual="";
			break;
		case "1":
			Actual="Pass";
			break;
		case "2":
			Actual="Fail";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual LT value ="+Actual);

		if(Actual.equalsIgnoreCase(LT)){
			Result="Pass - Leak Test ="+LT;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Leak Test was supposed to be "+LT+" but it was "+Actual;
		}
		return Result;
	}
	
	public static String Verify_LTMCDate(String Date){
		Actual=driver.findElementById("LTDateTime").getAttribute("value");
		System.out.println("Actual Procedure Start ="+Actual);
		
		IP_V.verifyDateFormat("SRM Details - Soiled Area Scan In Time ",Actual );


		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - LT/MC Date ="+Date;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(Date)){
						Result="Pass - LT/MC Date ="+Date;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - LT/MC Date was supposed to be "+Date+" but it was "+Actual+".";
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - LT/MC Date was supposed to be "+Date+" but it was "+Actual+".";
			}
		}
		return Result;
	}

	public static String Verify_MCStart(String Date){
		Actual=driver.findElementById("ManualCleanStart").getAttribute("value");
		System.out.println("Actual Manual Clean Start ="+Actual);
		
		IP_V.verifyDateFormat("SRM Details - Manual Clean Start date time",Actual );

		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - Manual Clean Start ="+Date;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(Date)){
						Result="Pass - Manual Clean Start ="+Date;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Manual Clean Start was supposed to be "+Date+" but it was "+Actual+"";
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Manual Clean Start was supposed to be "+Date+" but it was "+Actual+"";
			}
			
		}
		return Result;
	}

	public static String Verify_MCEnd(String Date){
		Actual=driver.findElementById("ManualCleanEnd").getAttribute("value");
		System.out.println("Actual Manual Clean End ="+Actual);
		
		IP_V.verifyDateFormat("SRM Details - Manual Clean End Date Time",Actual);

		if(Actual.equalsIgnoreCase(Date)){
			Result="Pass - Manual Clean End ="+Date;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(Date)){
						Result="Pass - Manual Clean End ="+Date;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Manual Clean End was supposed to be "+Date+" but it was "+Actual;
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Manual Clean End was supposed to be "+Date+" but it was "+Actual;
			}
		}
		return Result;
	}

	//Bioburden
	public static String Verify_BioResult(String BioResult){
		Actual=driver.findElementById("BioburdenResult").getAttribute("value");
		switch (Actual) {
		case "0":
			Actual="";
			break;
		case "1":
			Actual="Pass";
			break;
		case "2":
			Actual="Fail";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Bioburden Result value ="+Actual);

		if(Actual.equalsIgnoreCase(BioResult)){
			Result="Pass - Bioburden Result ="+BioResult;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Bioburden Result was supposed to be "+BioResult+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_BioScanValue(String BioValue){
		Actual=driver.findElementById("BioburdenValue").getAttribute("value");
		switch (Actual) {
		case "17":
			Actual="Blue";
			break;
		case "18":
			Actual="Red";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Bioburden scan value ="+Actual);

		if(Actual.equalsIgnoreCase(BioValue)){
			Result="Pass - Bioburden scan value ="+BioValue;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Bioburden scan value was supposed to be "+BioValue+" but it was "+Actual;
		}
		return Result;
	}
	
	public static String Verify_BioKeyValue(String BioValue){
		Actual=driver.findElementById("BioburdenKeyvalue").getAttribute("value");
		
		if(Actual.equalsIgnoreCase(BioValue)){
			Result="Pass - Bioburden key value ="+BioValue;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Bioburden key value was supposed to be "+BioValue+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_BioStaff(String Staff){ //ScanOutStaff
		Actual=driver.findElementById("BioburdenStaff").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="T01";
			break;
		case "22":
			Actual="T02";
			break;
		case "23":
			Actual="T03";
			break;
		case "24":
			Actual="T04";
			break;
		case "25":
			Actual="T05";
			break;
		case "26":
			Actual="T06";
			break;
		case "27":
			Actual="T07";
			break;
		case "28":
			Actual="T08";
			break;
		case "29":
			Actual="T09";
			break;
		case "30":
			Actual="T10";
			break;
		case "31":
			Actual="T11";
			break;
		case "32":
			Actual="T12";
			break;
		case "33":
			Actual="T13";
			break;
		case "34":
			Actual="T14";
			break;
		case "35":
			Actual="T15";
			break;
		case "36":
			Actual="T16";
			break;
		case "37":
			Actual="T17";
			break;
		case "38":
			Actual="T18";
			break;
		case "39":
			Actual="T19";
			break;
		case "40":
			Actual="T20";
			break;
			
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Bioburden Staff="+Actual);

		if(Actual.equalsIgnoreCase(Staff)){
			Result="Pass - Bioburden staff ="+Staff;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Bioburden staff was supposed to be "+Staff+" but it was "+Actual;
		}
		return Result;
	}
	
	//Culture
	public static String Verify_Culture(String Culture){
		Actual=driver.findElementById("CultureStatus").getAttribute("value");
		switch (Actual) {
		case "6":
			Actual="Fail";
			break;
		case "5":
			Actual="Pass";
			break;
		case "7":
			Actual="No Results";
			break;
		case "8":
			Actual="Awaiting Results";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Culture ="+Actual);

		if(Actual.equalsIgnoreCase(Culture)){
			Result="Pass - Culture ="+Culture;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Culture was supposed to be "+Culture+" but it was "+Actual;
		}
		return Result;
	}
	

	//Reprocessing Area:
	public static String Verify_Reprossor(String Repro){
		Actual=driver.findElementById("Reprocessor").getAttribute("value");
		switch (Actual) {
		case "51":
			Actual="Reprocessor 1";
			break;
		case "52":
			Actual="Reprocessor 2";
			break;
		case "53":
			Actual="Reprocessor 3";
			break;
		case "54":
			Actual="Reprocessor 4";
			break;
		case "55":
			Actual="Reprocessor 5";
			break;
		case "56":
			Actual="Reprocessor 6";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Reprocessor value ="+Actual);

		if(Actual.equalsIgnoreCase(Repro)){
			Result="Pass - Reprocessor ="+Repro;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Reprocessor was supposed to be "+Repro+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ReproScopeInTime(String ReproDate){
		Actual=driver.findElementById("ScanInDateTime").getAttribute("value");
		System.out.println("Actual Scope scan into repro time ="+Actual);
		
		IP_V.verifyDateFormat("SRM Details - Reprocessor Scan In Date Time",Actual );

		if(Actual.equalsIgnoreCase(ReproDate)){
			Result="Pass - Scope scan into repro time ="+ReproDate;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(ReproDate)){
						Result="Pass - Scope scan into repro time ="+ReproDate;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Scope scan into repro time was supposed to be "+ReproDate+" but it was "+Actual;
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Scope scan into repro time was supposed to be "+ReproDate+" but it was "+Actual;
			}
			
		}
		return Result;
	}
	
	public static String Verify_ReproScopeOutTime(String ReproDate){
		Date d1 = null;
		Date d2 = null;
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Actual=driver.findElementById("ScanOutDateTime").getAttribute("value");
		System.out.println("Actual Scope scan out of repro time ="+Actual);
		
		IP_V.verifyDateFormat("SRM Details - Reprocessor Scan out Date Time",Actual );
		
		if(Actual.trim().equalsIgnoreCase(ReproDate.trim())){
			Result="Pass - Scope scan out of repro time ="+ReproDate;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.trim().equalsIgnoreCase(ReproDate.trim())){
						Result="Pass - Scope scan out of repro time ="+ReproDate;
					}else{
						try {
							d1 = format.parse(ReproDate);
							d2 = format.parse(Actual);
							long TimeDiff = d1.getTime() - d2.getTime(); 
							System.out.println("Date field value is:"+Actual);
							if(TimeDiff==0){
								Result="Pass";
							}else if (TimeDiff<60000){
								Result="Pass - difference of less than 1 minute";
							}else{
								UAS.resultFlag="#Failed!#";
								Result="#Failed!# - Scope scan out of repro time was supposed to be "+ReproDate+" but it was "+Actual;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}else{
				try {
					d1 = format.parse(ReproDate);
					d2 = format.parse(Actual);
					long TimeDiff = d1.getTime() - d2.getTime(); 
					System.out.println("Date field value is:"+Actual);
					if(TimeDiff==0){
						Result="Pass";
					}else if (TimeDiff<60000){
						Result="Pass - difference of less than 1 minute";
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Scope scan out of repro time was supposed to be "+ReproDate+" but it was "+Actual;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return Result;
	}
	
	public static String Verify_ReproReason(String Reason){
		Actual=driver.findElementById("ReprocessingReason").getAttribute("value");
		switch (Actual) {
		case "5":
			Actual="Exceeded Hang Time";
			break;
		case "7":
			Actual="New Scope";
			break;
		case "6":
			Actual="Repeat Reprocessing";
			break;
		case "8":
			Actual="Returned to Facility";
			break;
		case "4":
			Actual="Used in Procedure";
			break;
		case "23":
			Actual="Custom Reason1";
			break;
		case "24":
			Actual="Custom Reason2";
			break;
		case "25":
			Actual="Custom Reason3";
			break;
		case "22":
			Actual="Double Wash";
			break;
		case "20":
			Actual="MRC Fail";
			break;
		case "21":
			Actual="Received Loaner Scope";
			break;
			
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Reprocessor value ="+Actual);

		if(Actual.equalsIgnoreCase(Reason)){
			Result="Pass - Reason ="+Reason;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Reason was supposed to be "+Reason+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ReproInStaff(String Staff){ //ScanOutStaff
		Actual=driver.findElementById("ScanInStaff").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="T01";
			break;
		case "22":
			Actual="T02";
			break;
		case "23":
			Actual="T03";
			break;
		case "24":
			Actual="T04";
			break;
		case "25":
			Actual="T05";
			break;
		case "26":
			Actual="T06";
			break;
		case "27":
			Actual="T07";
			break;
		case "28":
			Actual="T08";
			break;
		case "29":
			Actual="T09";
			break;
		case "30":
			Actual="T10";
			break;
		case "31":
			Actual="T11";
			break;
		case "32":
			Actual="T12";
			break;
		case "33":
			Actual="T13";
			break;
		case "34":
			Actual="T14";
			break;
		case "35":
			Actual="T15";
			break;
		case "36":
			Actual="T16";
			break;
		case "37":
			Actual="T17";
			break;
		case "38":
			Actual="T18";
			break;
		case "39":
			Actual="T19";
			break;
		case "40":
			Actual="T20";
			break;
			
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Repro in Staff="+Actual);

		if(Actual.equalsIgnoreCase(Staff)){
			Result="Pass - Repro in staff ="+Staff;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Repro in staff was supposed to be "+Staff+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ReproOutStaff(String Staff){ 
		Actual=driver.findElementById("ScanOutStaff").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="T01";
			break;
		case "22":
			Actual="T02";
			break;
		case "23":
			Actual="T03";
			break;
		case "24":
			Actual="T04";
			break;
		case "25":
			Actual="T05";
			break;
		case "26":
			Actual="T06";
			break;
		case "27":
			Actual="T07";
			break;
		case "28":
			Actual="T08";
			break;
		case "29":
			Actual="T09";
			break;
		case "30":
			Actual="T10";
			break;
		case "31":
			Actual="T11";
			break;
		case "32":
			Actual="T12";
			break;
		case "33":
			Actual="T13";
			break;
		case "34":
			Actual="T14";
			break;
		case "35":
			Actual="T15";
			break;
		case "36":
			Actual="T16";
			break;
		case "37":
			Actual="T17";
			break;
		case "38":
			Actual="T18";
			break;
		case "39":
			Actual="T19";
			break;
		case "40":
			Actual="T20";
			break;
			
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Repro out Staff="+Actual);

		if(Actual.equalsIgnoreCase(Staff)){
			Result="Pass - Repro out staff ="+Staff;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Repro out staff was supposed to be "+Staff+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ReproStartTime(String ReproStartTime){
		Actual=driver.findElementById("ReprocessorDateTime").getAttribute("value");
		
		IP_V.verifyDateFormat("SRM Details - Reprocessor Start Time",Actual );
		
		if(Actual.equalsIgnoreCase(ReproStartTime)){
			Result="Pass - Reprocessing Start Time ="+ReproStartTime;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(ReproStartTime)){
						Result="Pass - Reprocessing Start Time ="+ReproStartTime;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Reprocessing Start Time was supposed to be "+ReproStartTime+" but it was "+Actual;
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Reprocessing Start Time was supposed to be "+ReproStartTime+" but it was "+Actual;
			}
			
		}
		return Result;
	}
	
	public static String Verify_ReproStartStaff(String Staff){
		Actual=driver.findElementById("ReprocessingStartStaff").getAttribute("value");
		switch (Actual) {
		case "21":
			Actual="T01";
			break;
		case "22":
			Actual="T02";
			break;
		case "23":
			Actual="T03";
			break;
		case "24":
			Actual="T04";
			break;
		case "25":
			Actual="T05";
			break;
		case "26":
			Actual="T06";
			break;
		case "27":
			Actual="T07";
			break;
		case "28":
			Actual="T08";
			break;
		case "29":
			Actual="T09";
			break;
		case "30":
			Actual="T10";
			break;
		case "31":
			Actual="T11";
			break;
		case "32":
			Actual="T12";
			break;
		case "33":
			Actual="T13";
			break;
		case "34":
			Actual="T14";
			break;
		case "35":
			Actual="T15";
			break;
		case "36":
			Actual="T16";
			break;
		case "37":
			Actual="T17";
			break;
		case "38":
			Actual="T18";
			break;
		case "39":
			Actual="T19";
			break;
		case "40":
			Actual="T20";
			break;
		default:
			Actual="";
			break;
		}
		System.out.println("Actual Repro start Staff="+Actual);

		if(Actual.equalsIgnoreCase(Staff)){
			Result="Pass - Repro start staff ="+Staff;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Repro start staff was supposed to be "+Staff+" but it was "+Actual;
		}
		return Result;
	}
	
	public static String Verify_ReproCompleteTime(String ReproEndTime){
		Actual=driver.findElementById("ReprocessorCompleteDateTime").getAttribute("value");
		IP_V.verifyDateFormat("SRM Details - Reprocessor Complete Time",Actual );
		if(Actual.equalsIgnoreCase(ReproEndTime)){
			Result="Pass - Reprocessing Complete Time ="+ReproEndTime;
		}else{
			if (!Unifia_Admin_Selenium.isRecon){
				if(Actual.equalsIgnoreCase("")||Actual==null){
					driver.navigate().refresh();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Actual.equalsIgnoreCase(ReproEndTime)){
						Result="Pass - Reprocessing Complete Time ="+ReproEndTime;
					}else{
						UAS.resultFlag="#Failed!#";
						Result="#Failed!# - Reprocessing Complete Time was supposed to be "+ReproEndTime+" but it was "+Actual;
					}
				}
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Reprocessing Complete Time was supposed to be "+ReproEndTime+" but it was "+Actual;
			}
			
		}
		return Result;
	}
	
	public static String Verify_ReproTemp(String ReproTemp){
		Actual=driver.findElementById("DisinfectantTemperature").getAttribute("value");
		
		if(Actual.equalsIgnoreCase(ReproTemp)){
			Result="Pass - Disinfectant Temp ="+ReproTemp;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Disinfectant Temp was supposed to be "+ReproTemp+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ReproStatus(String ReproStatus){
		Actual=driver.findElementById("ReprocessingStatus").getAttribute("value");
		switch (Actual) {
		case "0":
			Actual="Unknown";
			break;
		case "1":
			Actual="Processing";
			break;
		case "2":
			Actual="Unexpected Termination";
			break;
		case "3":
			Actual="Scope Removed";
			break;
		case "5":
			Actual="Processing Complete";
			break;
		default:
			Actual="Unknown";
			break;
		}
		System.out.println("Actual Reprocessing Status ="+Actual);

		if(Actual.equalsIgnoreCase(ReproStatus)){
			Result="Pass - Reprocessing Status ="+ReproStatus;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Reprocessing Status was supposed to be "+ReproStatus+" but it was "+Actual;
		}
		return Result;
	}
	
public static String Verify_CultureResult(String Result){
		Actual=driver.findElementById("CultureStatus").getAttribute("value");
		switch (Actual) {
		case "0":
			Actual="";
			break;
		case "5":
			Actual="Pass";
			break;
		case "6":
			Actual="Fail";
			break;
		case "7":
			Actual="No Results";
			break;
		case "8":
			Actual="Awaiting Results";
			break;
		default:
			Actual="Unknown";
			break;
		}
		System.out.println("Actual Culture Status ="+Actual);

		if(Actual.equalsIgnoreCase(Result)){
			Result="Pass - Culture Status ="+Result;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Culture Status was supposed to be "+Result+" but it was "+Actual;
		}
		return Result;
	}

	public static String Verify_ErrorMessage(String Message){
		//Actual=driver.findElement(By.xpath("//*[@id=\"workflowform\"]/div/div[1]/div[4]/div[4]/h4")).getText();
		//Actual=driver.findElement(By.xpath("//*[@id=\"workflowform\"]/div/div[1]/div[2]/div/h4")).getText();
		Actual=driver.findElement(By.xpath("//*[@id=\"workflowform\"]/div/div[1]/div[4]/div/h4")).getText();
				
		if(Actual.equalsIgnoreCase("Data not saved")){
			Actual=driver.findElement(By.xpath("//*[@id=\"errorSection\"]/li")).getText();
			if(Actual.equalsIgnoreCase(Message)){
				Result="Pass - Error Message ="+Message;

			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - Error Message was supposed to be: Data not saved//n "+Message+" but it was Data not saved//n"+Actual;
			}
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Error Message was supposed to be: Data not saved//n "+Message+" but it was "+Actual;
		}
		return Result;
	}
	public static String VerifyInactiveElements(String elementID, String[] InactiveItems,int NumInactiveItems){
		String[] InactiveElements=new String[10];
		int j=0;
		for(int i=0;i<10;i++){
			InactiveElements[i]="zz";
		}
		Select dropdown= new Select(driver.findElement(By.id(elementID)));
		List <WebElement> ddvalues = dropdown.getOptions();
		int listsize=ddvalues.size();
		System.out.println(ddvalues.size());
		Result="";
		for (int lendd=1;lendd<listsize;lendd++){
			System.out.println(ddvalues.get(lendd).getText());				
			if(ddvalues.get(lendd).isEnabled()){
				System.out.println(ddvalues.get(lendd).getText()+"=Enabled");
			} else {
				InactiveElements[j]=ddvalues.get(lendd).getText();
				System.out.println(ddvalues.get(lendd).getText()+"=Disabled");
				j++;
			}
		}
		if(j>1){
			Arrays.sort(InactiveElements); 
			Arrays.sort(InactiveItems);

		}
		for(int i=0;i<NumInactiveItems;i++){
			if(InactiveElements[i].equalsIgnoreCase(InactiveItems[i])){
				Result=Result+" Pass - elementID has inactive item ="+InactiveItems[i];
			}else{
				Result="#Failed!# - Inactive item "+InactiveItems[i]+" is not disabled. "+InactiveElements[i]+" is disabled.";

			}
		}

		return Result;
	}
	
	public static String Verify_ChevronColor(String location, String refNo, String expectedColor){
		String actualColor=WorkFlowDetails_Actions.getChevronColor(location, refNo);	
		String workflowStatus="";
		if(expectedColor.equalsIgnoreCase(DBP.rgbOfCompletedFlow)){
			workflowStatus="Complete";
		}else if(expectedColor.equalsIgnoreCase(DBP.rgbOfIncompleteFlow)){
			workflowStatus="In-complete";
		}
		if(actualColor.equalsIgnoreCase(expectedColor)){
			Result="Pass - Chevron color is as expected for RefNo = "+refNo+" at "+location+" which is = "+expectedColor+" as it is "+workflowStatus;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Chevron color for RefNo = "+refNo+" at "+location+" which was supposed to be "+expectedColor+" but it was "+actualColor+" as it is "+workflowStatus;
		}
		return Result;
	}
	
	public static String Verify_SelectedPhysician(String expPhyicians){ 
		boolean isFound = false;
		String selectedPhysicians="";
		List<WebElement> webele=Unifia_Admin_Selenium.driver.findElementsByXPath("//select[@id='PhysicianID']//following-sibling::div/ul/li[@class='opt selected']/label");
		if(webele.size()>0){
			for(WebElement we: webele){
				selectedPhysicians+=we.getAttribute("innerHTML")+",";
			}
		}
		
		if(!selectedPhysicians.equals("")){
			selectedPhysicians=selectedPhysicians.substring(0, selectedPhysicians.length()-1); //removing comma at the end
		}
		if (expPhyicians.trim().equalsIgnoreCase("")){
			if (selectedPhysicians.trim().equalsIgnoreCase("")){
				Result="Pass - PR in staff ="+expPhyicians;
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - PR physician was supposed to be "+expPhyicians+" but it was "+selectedPhysicians;
			}
		}else{
		
			String [] actualSelected=selectedPhysicians.trim().split(",");
			String[] expSelected=expPhyicians.trim().split(",");
					
			for (int cnt=0 ; cnt<actualSelected.length;cnt++){
				isFound=false;
				String actual=actualSelected[cnt].trim();
				for (int expCnt=0; expCnt<expSelected.length;expCnt++){
					if (expSelected[expCnt].trim().equalsIgnoreCase(actual)){
						isFound=true;
						break;
					}
				}
				if (!isFound){
					break;
				}
			}
			if (isFound){
				Result="Pass - PR in staff ="+expPhyicians;
			}else{
				UAS.resultFlag="#Failed!#";
				Result="#Failed!# - PR physician was supposed to be "+expPhyicians+" but it was "+selectedPhysicians;
			}
		}
		
		System.out.println("Actual PR in Staff="+selectedPhysicians);
		return Result;
	}	
	

}
