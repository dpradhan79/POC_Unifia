package dailydashboardmultifacility;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
//import org.sikuli.script.*;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QlikView.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.QVDashboard.*;
public class ExamQueueMulti {
	
	
	private QV_GeneralFunc QV_Gen;
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private String TestSummary= "\t\t\t DailyDashboard_ExamQueue_TestSummary \r\n"; 
	private String ResFileName="Multi_DailyDashboard_ExamQueue_TestSummary";
	private String ResFileNameXML="Multi_DailyDashboard_ExamQueue_Result";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	private String ForFileName;
	private Dashboard_Actions DA;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	 public void examQueueVerificaitons(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException{
		String result_PatCount,result_ExamCount, result_ExamType1,result_ExamType1Count,result_ExamType2,result_ExamType2Count;
		String eachPatID=null,patientName=null; 
		String patientsCount= null,examsCount=null, examType1=null, examType2=null, examType1Count=null, examType2Count=null;
		StringBuffer exams = new StringBuffer("");
		StringBuffer physicians = new StringBuffer("");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		
		//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Exam Queue");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		String Expected,Description;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(5000);
		//UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		for (int eachFac=1;eachFac<=3;eachFac++){
			IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
			String facilityName= "Facility "+eachFac;
			System.out.println("Verifying for "+facilityName);
			gf.selectFromListBox(DBP.selectFacility,facilityName);
			Thread.sleep(2000);
			
			qvd_v.clickDashBoard();
			DA.clickElement(DBP.examQueueHeaderDDB);
			//Getting the drilldown popup data from database
			Connection conn=null;
			Statement statement=null;
			String stmt="";
			List<String> patID = new ArrayList<String>();
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select distinct (PatientID_FK) from ExamQueue";

			stmt="select  distinct (PatientID_FK) from examqueue where locationid_fk in (select locationid_pk from location where facilityid_fk="+eachFac+")";
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				patID.add(RS.getString(1));
			}
			System.out.println ("Need to verify "+patID.size()+" rows in Exam Queue");
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			for (int eachRec=0;eachRec<patID.size();eachRec++){
				eachPatID=patID.get(eachRec);
				stmt="select StaffID from Staff where StaffID_PK in "
						+ "(select Distinct(PhysicianID_FK) from examqueue where patientid_fk="+eachPatID+")";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					if (physicians.length()==0){
						physicians.append(RS.getString(1));
					}else{
						physicians.append (","+RS.getString(1));
					}
				}
				
				stmt="select abbreviation from examtype where examtypeid_pk in "
						+ "(select examtypeid_fk from examqueue where patientid_fk="+eachPatID+")";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					if (exams.length()==0){
						exams.append(RS.getString(1));
					}else{
						exams.append (","+RS.getString(1));
					}
				}
				stmt = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
				Statement update1 = conn.createStatement();
	    		System.out.println(stmt);
	    		update1.executeUpdate(stmt);
		    	stmt="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
		    	System.out.println(stmt);
	    		update1.executeUpdate(stmt);
				stmt="Execute dbo.sp_GetPatients_EQUAL '"+ eachPatID+"'";
			  	RS=statement.executeQuery(stmt);
			  	System.out.println("stmt="+stmt);
				while(RS.next()){
			   		patientName = RS.getString(3);
				}
				String colsNvalues="Exam(s)=="+exams.toString()+";patientName=="+patientName+";Physician(s)=="+physicians.toString();
				StringBuffer resultDrillDown=gf.verifyTableContents(DBP.examQueueTableContent,colsNvalues,"patientName");
				System.out.println(resultDrillDown);
				Description="Verifying drill down popup contents of Exam Queue area for Row -"+(eachRec+1) + " for "+facilityName;
				Expected =Description;
				TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
				TestSummary=TestSummary+TestRes+"\r\n";
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
				exams.setLength(0);
				resultDrillDown.setLength(0);
				physicians.setLength(0);
			}
			qvd_v.clickDashBoard();
			
			patientsCount=qvd_v.getPatients();
			examsCount=qvd_v.getExams();
			examType1=qvd_v.getExamType1();
			examType2=qvd_v.getExamType2();
			examType1Count=qvd_v.getExamType1Count();
			examType2Count=qvd_v.getExamType2Count();
			//Patientcount
			if (qvd_v.compareRes(DBP.expPatientCountMulti,patientsCount,true)){
				result_PatCount="Passed: Expected= "+DBP.expPatientCountMulti+";Actual= "+patientsCount;
			}else{
				result_PatCount="#Failed!#: Expected= "+DBP.expPatientCountMulti+";Actual= "+patientsCount;
			}
			Description="Verifying Patient count on Exam Queue tab for " + facilityName;
			Expected =Description;
			TestRes=Description+result_PatCount+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PatCount);
			//ExamCount
			if (qvd_v.compareRes(DBP.expExamCountMulti,examsCount,true)){
				result_ExamCount="Passed: Expected= "+DBP.expExamCountMulti+";Actual= "+examsCount;
			}else{
				result_ExamCount="#Failed!#: Expected= "+DBP.expExamCountMulti+";Actual= "+examsCount;
			}
			Description="Verifying Exam count on Exam Queue tab for " + facilityName;
			Expected =Description;
			TestRes=TestRes+Description+result_ExamCount+"\r\n";;
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ExamCount);
			//ExamType1
			if (qvd_v.compareRes(DBP.expExamType1Multi,examType1,true)){
				result_ExamType1="Passed: Expected Exam Name= "+DBP.expExamType1Multi+";Actual Patient Count = "+examType1;
			}else{
				result_ExamType1="#Failed!#: Expected Exam Name= "+DBP.expExamType1Multi+";Actual Patient Count = "+examType1;
			}
			Description="Verifying Exam Type1 Name for " + facilityName;
			Expected =Description;
			TestRes=TestRes+Description+result_ExamType1+"\r\n";;
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ExamType1);
			//ExamType1Count
			if (qvd_v.compareRes(DBP.expExamType1CountMulti,examType1Count,true)){
				result_ExamType1Count="Passed: Expected= "+DBP.expExamType1CountMulti+";Actual= "+examType1Count;
			}else{
				result_ExamType1Count="#Failed!#: Expected= "+DBP.expExamType1CountMulti+";Actual= "+examType1Count;
			}
			Description="Verifying Exam Type count for "+DBP.expExamType1Multi+ "for " + facilityName;
			Expected =Description;
			TestRes=TestRes+Description+result_ExamType1Count+"\r\n";;
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ExamType1Count);
			//ExamType2
			if (qvd_v.compareRes(DBP.expExamType2Multi,examType2,true)){
				result_ExamType2="Passed: Expected= "+DBP.expExamType2Multi+";Actual= "+examType2;
			}else{
				result_ExamType2="#Failed!#: Expected= "+DBP.expExamType2Multi+";Actual= "+examType2;
			}
			Description="Verifying Exam Type2 Name " + facilityName;
			Expected =Description;
			TestRes=TestRes+Description+result_ExamType2+"\r\n";;
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ExamType2);
			//ExamType2Count
			if (qvd_v.compareRes(DBP.expExamType2CountMulti,examType2Count,true)){
				result_ExamType2Count="Passed: Expected= "+DBP.expExamType2CountMulti+";Actual= "+examType2Count;
			}else{
				result_ExamType2Count="#Failed!#: Expected= "+DBP.expExamType2CountMulti+";Actual= "+examType2Count;
			}
			Description=Description="Verifying Exam Type count for "+DBP.expExamType2Multi +"for "+ facilityName;
			Expected =Description;
			TestRes=TestRes+Description+result_ExamType2Count+"\r\n";;
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ExamType2Count);
			
			/*TestRes="\r\nVerification of Exams and Patients in Exam Queue :\r\nresult_PatCount="+result_PatCount+";\r\n"
					+ "result_ExamCount="+result_ExamCount+";\r\nresult_ExamType1="+result_ExamType1+";\r\nresult_ExamType1Count="+result_ExamType1Count+";\r\n"
					+ "result_ExamType2="+result_ExamType2+";\r\nresult_ExamType2Count="+result_ExamType2Count+";";*/
			
			TestSummary=TestSummary+TestRes+"\r\n";
			TH.WriteToTextFile(ResFileName, TestSummary);
			IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
			
		}
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		
		if (TestSummary.contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.contains("#Failed!#"))) {
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();		
	}
	
	@AfterTest
	  public void PostTest() throws IOException{
		LandingPage_Actions.CloseDriver();
	  }
}