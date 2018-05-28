package ExamSummary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TimeZone;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.UnifiaAdminUserPage.User_Actions;


public class ExamSummary_Historical {

	public TestFrameWork.Emulator.GetIHValues IHV;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public GeneralFunc GF;
	private TestDataFunc TDF;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public User_Actions ua; //shortcut to link to the UnifiaAdminUserPage.User_Actions java class.
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private ITConsole.ITConScanSimActions IT_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_EM.EM_Actions EM_A;
	public static TestFrameWork.Unifia_EM.EM_Verification EM_V;
	public static TestFrameWork.Unifia_ExamSummary.ES_Actions ES_A; 
	public static TestFrameWork.Unifia_ExamSummary.ES_Verification ES_V;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	private static TestFrameWork.Emulator.Emulator_Actions Emul_A;
	
	public int UTCTimeDiffInHours=0;		
	
	public String stmt;
    public String stmt1;
	public ResultSet PR_RS;  //result set used to get the PR record to be modified.
	public Boolean Res;
	
	private SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	Calendar cal2 = Calendar.getInstance();
	private String facility1abb="FAC1";
	private String facility2abb="FAC2";
	private String facility3abb="FAC3";
	private String facility1="Facility 1";
	private String facility2="Facility 2";
	private String facility3="Facility 3";
	private String result_ES="";
	private String result_EM="";
	
	private String active_PR="Active Procedure Rooms";
	private String scopesUsed="Scopes Used";
	private String exam_Pat="Exams with Patient ID";
	private String exam_Scope_NoPat="Exams with Scope, but no Patient ID";
	private String exam_Scope_PreClean_Nopat="Exams with Scope and Pre-Clean, but no Patient ID";
	private String exam_Pat_Scope_NoPreClean="Exams with Patient ID and Scope, but no Pre-Clean";
	private String exam_Pat_AfterPreClean="Exams with Patient ID Scan after Pre-Clean Scan";
	private String exam__Pat_NoScope="Exams with Patient ID, but no Scope";
	
	public String description,expected, result;
	public static Connection conn= null;
	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	private String XMLFile="Epic10883_Quality_Issue_Data_Historical.xml";
	private String expAPR,actAPR;
	
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, Exception {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		
		//Step -1
		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
		//Inserting DB data
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
		TDF.insertMultiFacilityMasterData(UAS.url, UAS.user, UAS.pass,KE, Bioburden, Culture);
		String stmt1 = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
    	String stmt2="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
  		try{
    		Connection conn = DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
    		Statement update1 = conn.createStatement();
    		System.out.println("stmt1="+stmt1);
    		update1.executeUpdate(stmt1);
    		System.out.println("stmt2="+stmt2);
    		update1.executeUpdate(stmt2);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		GF.RestartIISServices(UAS.Env, UAS.userName, UAS.IISPass);
		
		UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();
		UAS.XMLFileName="Exam_Summary_Historical_";
		UAS.XMLFileName=IHV.Start_Exec_Log(UAS.XMLFileName);
		UAS.TestCaseNumber=1;
		
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		
		Res = Emul_A.ScanItem("F1 Procedure Room 1", "Workflow Event", "", "Available", "");
		Res = Emul_A.ScanItem("F1 Procedure Room 2", "Workflow Event", "", "Available", "");
		Res = Emul_A.ScanItem("F1 Procedure Room 3", "Workflow Event", "", "Available", "");
		Res = Emul_A.ScanItem("F1 Procedure Room 5", "Workflow Event", "", "Available", "");
		Res = Emul_A.ScanItem("F1 Procedure Room 6", "Workflow Event", "", "Available", "");
		Res = Emul_A.ScanItem("F1 Procedure Room 7", "Workflow Event", "", "Available", "");
		Res = Emul_A.ScanItem("F1 Procedure Room 8", "Workflow Event", "", "Available", "");
		Res = Emul_A.ScanItem("F1 Procedure Room 9", "Workflow Event", "", "Available", "");
		
		//Scan a physician for procedure room1 for facility1 and then verify active PR =0
		Res = Emul_A.ScanItem("F1 Procedure Room 1", "Staff", "Physician", "Physician1 Physician1(MD01)", "");
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		description="verify when physician is scannned into procedure room 1";
		expected="Active Procedure Rooms value should not be increassed, it should be 0";
		expAPR="0";
		Map<String, String> actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Scan a technician for procedure room2 for facility1 and then verify active PR =0
		Res = Emul_A.ScanItem("F1 Procedure Room 2", "Staff", "Tech", "Tech1 Tech1(T01)", "");
		IP_A.Click_IP_Dashboard();
		description="Verify when technician is scannned into procedure room 2";
		expected="Active Procedure Rooms value should not be increassed, it should be 0";
		expAPR="0";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Scan a PR Start for procedure room3 for facility1 and then verify active PR =0
		Res = Emul_A.ScanItem("F1 Procedure Room 3", "Workflow Event", "", "Procedure Start", "");
		IP_A.Click_IP_Dashboard();
		description="Verify when Procedure Start is scannned into procedure room 3";
		expected="Active Procedure Rooms value should not be increassed, it should be 0";
		expAPR="0";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Scan a PR End for procedure room5 for facility1 and then verify active PR =0
		Res = Emul_A.ScanItem("F1 Procedure Room 5", "Workflow Event", "", "Procedure End", "");
		IP_A.Click_IP_Dashboard();
		description="Verify when Procedure End is scannned into procedure room 5";
		expected="Active Procedure Rooms value should not be increassed, it should be 0";
		expAPR="0";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Scan a patient/Procedure Status for procedure room6 for facility1 and then verify active PR =0
		Res = Emul_A.ScanItem("F1 Procedure Room 6", "Workflow Event", "", "Patient/Procedure Status", "");
		IP_A.Click_IP_Dashboard();
		description="Verify when patient/Procedure Status is scannned into procedure room 6";
		expected="Active Procedure Rooms value should not be increassed, it should be 0";
		expAPR="0";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Scan a patient for procedure room6 for facility1 and then verify active PR =1
		Res = Emul_A.ScanItem("F1 Procedure Room 6", "Patient", "", "MRN00001", "");
		IP_A.Click_IP_Dashboard();
		description="Verify when patient is scannned into procedure room 6";
		expected="Active Procedure Rooms value should be increassed, it should be 1";
		expAPR="1";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//navigate to exam management screen and verify one record for PR6
		//IP_A.Click_EM();
		IP_A.Click_ActiveProcedureRooms();
		List<String> completeColumnValues=new ArrayList<String>();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Procedure Room"));
		description="Verify when patient is scannned into procedure room 6";
		expected="In Exam Managent screen a row for Procedure Room 6 should be present";
		
		if (completeColumnValues.contains("F1 Procedure Room 6")){
			result="Passed- a row for Procedure Room 6 is present";
		}else{
			result="#Failed!# - a row for Procedure Room 6 is not present";
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Scan a scope for procedure room7 for facility1 and then verify active PR =2
		IP_A.Click_IP_Dashboard();
		Res = Emul_A.ScanItem("F1 Procedure Room 7", "Scope", "", "F1 Scope10", "");
		Thread.sleep(2000);
		IP_A.Click_IP_Dashboard();
		description="Verify when scope is scannned into procedure room 7";
		expected="Active Procedure Rooms value should be increassed, it should be 2";
		expAPR="2";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//navigate to exam management screen and verify one record each for PR6,PR7
		//IP_A.Click_EM();
		IP_A.Click_ActiveProcedureRooms();
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Procedure Room"));
		description="Verify rows are present for Procedure Room 6 and 7 ";
		expected=description;
		
		if (completeColumnValues.contains("F1 Procedure Room 6") && completeColumnValues.contains("F1 Procedure Room 7")){
			result="Passed- a row each for Procedure Room 6 and Procedure Room 7 are present";
		}else{
			result="#Failed!# - a row each for Procedure Room 6 and Procedure Room 7 is not present";
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Scan a patient for procedure room8 for facility1 and then verify active PR =3
		Res = Emul_A.ScanItem("F1 Procedure Room 8", "Patient", "", "MRN00002", "");
		IP_A.Click_IP_Dashboard();
		description="Verify when patient is scannned into procedure room 8";
		expected="Active Procedure Rooms value should be increassed, it should be 3";
		expAPR="3";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Scan a needscleaning for procedure room9 for facility1 and then verify active PR 3
		Res = Emul_A.ScanItem("F1 Procedure Room 9", "Workflow Event", "", "Needs Cleaning", "");
		IP_A.Click_IP_Dashboard();
		description="Verify when Needs Cleaning is scannned into procedure room 9";
		expected="Active Procedure Rooms value should not be increassed, it should be 3";
		expAPR="3";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//navigate to exam management screen and verify one record each for  PR6,PR7,PR8
		//IP_A.Click_EM();
		IP_A.Click_ActiveProcedureRooms();
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Procedure Room"));
		description="Verify rows are present for Procedure Room 6,7,8";
		expected=description;
		
		if (completeColumnValues.contains("F1 Procedure Room 6") && completeColumnValues.contains("F1 Procedure Room 7") && completeColumnValues.contains("F1 Procedure Room 8")){
			result="Passed- a row each for Procedure Room 6, 7 & 8 are present";
		}else{
			result="#Failed!# - a row each for Procedure Room 6, 7 & 8 are not present";
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		
		//Scan a Scope for procedure room1 for facility1 and then verify active PR 4
		Res = Emul_A.ScanItem("F1 Procedure Room 1", "Scope", "", "F1 Scope1", "");
		IP_A.Click_IP_Dashboard();
		description="Verify when scope is scannned into procedure room 1";
		expected="Active Procedure Rooms value should be increassed, it should be 4";
		expAPR="4";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//navigate to exam management screen and verify one record each for  PR1,PR6,PR7,PR8
		//IP_A.Click_EM();
		IP_A.Click_ActiveProcedureRooms();
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Procedure Room"));
		description="Verify rows are present for Procedure Room 1,6,7,8";
		expected=description;
		
		if (completeColumnValues.contains("F1 Procedure Room 1") && completeColumnValues.contains("F1 Procedure Room 6") && completeColumnValues.contains("F1 Procedure Room 7") && completeColumnValues.contains("F1 Procedure Room 8")){
			result="Passed- a row each for Procedure Room 1,6,7 & 8 are present";
		}else{
			result="#Failed!# - a row each for Procedure Room 1,6,7 & 8 are not present";
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		
		//Scan a patient for procedure room2 for facility1 and then verify active PR 5
		Res = Emul_A.ScanItem("F1 Procedure Room 2", "Patient", "", "MRN00002", "");
		IP_A.Click_IP_Dashboard();
		
		description="Verify when patient is scannned into procedure room 2";
		expected="Active Procedure Rooms value should be increassed, it should be 5";
		expAPR="5";
		actual_ES_Values.clear();
		actual_ES_Values = ES_A.get_ES_TableValues();
		actAPR=actual_ES_Values.get("Active Procedure Rooms");
		if(actAPR.equalsIgnoreCase(expAPR)){
			result="Passed- Active Procedure Rooms value is "+actAPR;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Active Procedure Rooms value is supposed to be "+expAPR+" but it is "+actAPR+", Bug 12109 is opened for this issue." ;
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//navigate to exam management screen and verify one record each for  PR1,PR6,PR7,PR8
		//IP_A.Click_EM();
		IP_A.Click_ActiveProcedureRooms();
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Procedure Room"));
		description="Verify rows are present for Procedure Room 1,2,6,7,8";
		expected=description;
		
		if (completeColumnValues.contains("F1 Procedure Room 1") && completeColumnValues.contains("F1 Procedure Room 2") && completeColumnValues.contains("F1 Procedure Room 6") && completeColumnValues.contains("F1 Procedure Room 7") && completeColumnValues.contains("F1 Procedure Room 8")){
			result="Passed- a row each for Procedure Room 1,2,6,7 & 8 are present";
		}else{
			result="#Failed!# - a row each for Procedure Room 1,2,6,7 & 8 are not present";
		}
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
				
		// Insert multi facility Master data
		UAS.driver2.close();
		UAS.driver.close();
		
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
		TDF.insertMultiFacilityMasterData(UAS.url, UAS.user, UAS.pass,KE, Bioburden, Culture);
		stmt1 = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
    	stmt2="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
  		try{
    		Connection conn = DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
    		Statement update1 = conn.createStatement();
    		System.out.println("stmt1="+stmt1);
    		update1.executeUpdate(stmt1);
    		System.out.println("stmt2="+stmt2);
    		update1.executeUpdate(stmt2);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		GF.RestartIISServices(UAS.Env, UAS.userName, UAS.IISPass);
		
		//Copying XMLFile
		boolean isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, XMLFile,fileSource,fileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+XMLFile+" is copied to "+fileDestination);
		}else{
			System.out.println("#Failed!#- "+XMLFile+" is not copied to "+fileDestination);
		}
		//Copying Runbatch.bat file to server machine
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.batchFile,fileSource,batchFileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+UAS.batchFile+" is copied to "+batchFileDestination);
		}else{
			System.out.println("#Failed!#- "+UAS.batchFile+" is not copied to "+batchFileDestination);
		}
	
		//Update the environment and xml file in  Runbatch.bat file
		IT_A.modifyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.scanSimPath, UAS.batchFile,XMLFile, xmlPath);
		//execute ScanSimUI
		IT_A.executeScanSim(UAS.Env, UAS.userName, UAS.IISPass,UAS.scanSimPathTool,UAS.ITConsoleExecTime,0);
		Thread.sleep(2000);
		IHV.Exec_Test_Case(UAS.XMLFileName,"End",UAS.TestCaseNumber);
		
		UAS.TestCaseNumber=2;

		//Step-2
		UAS.StepNum=1;
		LGPA.Launch_Unifia(UAS.Admin_URL);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, UAS.roleMgr, UAS.userQV1, UAS.userQV1Pwd,facility2abb,facility3abb);
		
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date currentDate=new Date();
		String today=df.format(currentDate);
		cal2.setTime(currentDate);
		cal2.add(Calendar.DATE, -7);
		Date DayMinus7=cal2.getTime();
		String dayMinus7=df.format(DayMinus7);
		System.out.println("DayMinus7="+dayMinus7);
		cal2.setTime(currentDate);
		cal2.add(Calendar.DATE, -1);
		Date DayMinus1=cal2.getTime();
		String dayMinus1=df.format(DayMinus1);
		System.out.println("DayMinus1="+dayMinus1);
		
		//Step-3
		IP_A.Click_InfectionPrevention();
		Thread.sleep(2000);
		ES_A.DateFilter(today, today);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		IHV.Exec_Test_Case(UAS.XMLFileName,"Start",UAS.TestCaseNumber);
		
		//checking that there is table Infection Prevention Exam Summary Table
		description="Checking that there is table Infection Prevention Exam Summary Table when clicked on Infection Prevention tab";
		expected="There should be a table Infection Prevention Exam Summary Table when clicked on Infection Prevention tab";
		System.out.println(description);
		result=ES_V.verify_ES_Header();
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Checking whether the Facility selected is Facility 1
		description="Checking whether the Facility selected is "+facility1;
		expected="The Facility selected should be "+facility1;
		System.out.println(description);
		result=ES_V.verify_Facility(facility1);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Step-4
		//Checking the Exam Summary table values
		description="Checking the Exam Summary table values";
		expected="Exam Summary table values should match with the expected values";
		System.out.println(description);
		Map<String, String> expected_ES_Values=new LinkedHashMap<String,String>();
		expected_ES_Values.put(active_PR,"3");
		expected_ES_Values.put(scopesUsed,"0");
		expected_ES_Values.put(exam_Pat,"0");
		expected_ES_Values.put(exam_Scope_NoPat,"0");
		expected_ES_Values.put(exam_Scope_PreClean_Nopat,"0");
		expected_ES_Values.put(exam_Pat_Scope_NoPreClean,"0");
		expected_ES_Values.put(exam_Pat_AfterPreClean,"0");
		expected_ES_Values.put(exam__Pat_NoScope,"0");
		
		actual_ES_Values = ES_A.get_ES_TableValues();
		
		if(actual_ES_Values.size() != expected_ES_Values.size()){
			result_ES="#Failed!# - Fields mismatch in Exam Summary table. Expected number of fields are "+expected_ES_Values.size()+" but the fields that are displayed was "+actual_ES_Values.size();
		}else{
			Iterator it =expected_ES_Values.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				String expected_Val=expected_ES_Values.get(pairs.getKey());
				String actual_Val=actual_ES_Values.get(pairs.getKey());
				if(actual_Val.equalsIgnoreCase(expected_Val)){
					result="Pass - "+pairs.getKey()+" value is "+actual_Val;
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!# - "+pairs.getKey()+" value was supposed to be "+expected_Val+" but it was "+actual_Val;
				}
				result_ES+=result+"; ";
			}
		}		
		System.out.println(result_ES);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result_ES);
		
		//Step-5
		//Drilldown of Active ProcedureRoom of Facility 1 for Today's date
		description="Checking the list of ProcedureRooms that are displayed when drildowned to ExamManagement Screen of Facility 1 for Today's Date";
		expected="The list of ProcedureRooms that are displayed when drildowned to ExamManagement Screen of Facility 1 for Today's Date should be as expected";
		System.out.println(description);
		ES_A.click_ActiveProcedureRoomCount();
		Thread.sleep(2000);

		List<String> procRoomList=EM_A.get_EM_ColumnValues("Procedure Room");
		String[] expected_ProcRoomValues={"F1 Procedure Room 1","F1 Procedure Room 2","F1 Procedure Room 8"};
		if(expected_ProcRoomValues.length==procRoomList.size()){
			for(String procRoom:expected_ProcRoomValues){
				if(procRoomList.contains(procRoom)){
					result="Pass - Exam Management screen is displaying "+procRoom;
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!# - Exam Management screen is not displaying "+procRoom;
				}
				result_EM+=result+"; ";
			}
		}else{
			UAS.resultFlag="#Failed!#";
			result_EM="#Failed!# - The number of Procedure Rooms should be 3 but it is displaying only "+procRoomList.size();
		}
		System.out.println(result_EM);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result_EM);
		
		if(procRoomList.size()==0){
			//Verify all the scan dates are from yesterday for all records
			description="Verify all the scan dates are from yesterday for all records";
			expected="All the scan dates are from yesterday for all records";
			System.out.println(description);
			//List<String> eachProcRealtedValues=new ArrayList<String>();
			completeColumnValues.clear();
			completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
			//Verifying Date format for Exam Date/Time column
			IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
			result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus1, DayMinus1, df);
			System.out.println(result);
			IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);

		}
		
		
		//Step-6
		IP_A.Click_InfectionPrevention();
		Thread.sleep(2000);
		
		//checking that there is table Infection Prevention Exam Summary Table
		description="Checking that there is table Infection Prevention Exam Summary Table when clicked on Infection Prevention tab";
		expected="There should be a table Infection Prevention Exam Summary Table when clicked on Infection Prevention tab";
		System.out.println(description);
		result=ES_V.verify_ES_Header();
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Checking whether the Facility selected is Facility 1
		description="Checking whether the Facility selected is "+facility1;
		expected="The Facility selected should be "+facility1;
		System.out.println(description);
		result=ES_V.verify_Facility(facility1);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Step-7-15
		//Checking Day -7 to Day -1 data for Facility 1 of Exam Summary table
		description="Checking the Exam Summary table values for Day -7 to Day -1 data of "+facility1;
		expected="Exam Summary table values should match with the expected values";
		System.out.println(description);
		IP_A.Click_InfectionPrevention();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		expected_ES_Values.clear();
		actual_ES_Values.clear();
		result_ES="";
		
		expected_ES_Values.put(active_PR,"3");
		expected_ES_Values.put(scopesUsed,"27");
		expected_ES_Values.put(exam_Pat,"22");
		expected_ES_Values.put(exam_Scope_NoPat,"2");
		expected_ES_Values.put(exam_Scope_PreClean_Nopat,"1");
		expected_ES_Values.put(exam_Pat_Scope_NoPreClean,"4");
		expected_ES_Values.put(exam_Pat_AfterPreClean,"1");
		expected_ES_Values.put(exam__Pat_NoScope,"1");
		
		actual_ES_Values = ES_A.get_ES_TableValues();
		
		if(actual_ES_Values.size() != expected_ES_Values.size()){
			System.out.println("Mismatch of number of columns in table ");
		}else{
			Iterator it =expected_ES_Values.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				String expected_Val=expected_ES_Values.get(pairs.getKey());
				String actual_Val=actual_ES_Values.get(pairs.getKey());
				if(actual_Val.equalsIgnoreCase(expected_Val)){
					result="Pass - "+pairs.getKey()+" value is "+actual_Val;
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!# - "+pairs.getKey()+" value was supposed to be "+expected_Val+" but it was "+actual_Val;
				}
				result_ES+=result+"; ";
			}
		}
		System.out.println(result_ES);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result_ES);
		
		//Step-16
		ES_A.Clear_Filter();
		
		//Step-17-21
		//Checking Day -1 data for Facility 1 of Exam Summary table
		description="Checking the Exam Summary table values for Day -1 data of "+facility1;
		expected="Exam Summary table values should match with the expected values";
		System.out.println(description);
		Thread.sleep(2000);
		ES_A.DateFilter(dayMinus1, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		expected_ES_Values.clear();
		actual_ES_Values.clear();
		result_ES="";
		
		expected_ES_Values.put(active_PR,"3");
		expected_ES_Values.put(scopesUsed,"3");
		expected_ES_Values.put(exam_Pat,"3");
		expected_ES_Values.put(exam_Scope_NoPat,"0");
		expected_ES_Values.put(exam_Scope_PreClean_Nopat,"0");
		expected_ES_Values.put(exam_Pat_Scope_NoPreClean,"0");
		expected_ES_Values.put(exam_Pat_AfterPreClean,"0");
		expected_ES_Values.put(exam__Pat_NoScope,"0");
		
		actual_ES_Values = ES_A.get_ES_TableValues();
		
		if(actual_ES_Values.size() != expected_ES_Values.size()){
			System.out.println("Mismatch of number of columns in table ");
		}else{
			Iterator it =expected_ES_Values.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				String expected_Val=expected_ES_Values.get(pairs.getKey());
				String actual_Val=actual_ES_Values.get(pairs.getKey());
				if(actual_Val.equalsIgnoreCase(expected_Val)){
					result="Pass - "+pairs.getKey()+" value is "+actual_Val;
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!# - "+pairs.getKey()+" value was supposed to be "+expected_Val+" but it was "+actual_Val;
				}
				result_ES+=result+"; ";
			}
		}
		System.out.println(result_ES);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result_ES);
		
		//Step- 22 to 27
		//Checking Day -7 to Day -1 data for Facility 2 of Exam Summary table
		description="Checking the Exam Summary table values for Day -7 to Day -1 data of "+facility2;
		expected="Exam Summary table values should match with the expected values";
		System.out.println(description);
		EM_A.Change_Facility(facility2);
		Thread.sleep(3000);
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		expected_ES_Values.clear();
		actual_ES_Values.clear();
		result_ES="";
		
		expected_ES_Values.put(active_PR,"2");
		expected_ES_Values.put(scopesUsed,"8");
		expected_ES_Values.put(exam_Pat,"8");
		expected_ES_Values.put(exam_Scope_NoPat,"0");
		expected_ES_Values.put(exam_Scope_PreClean_Nopat,"0");
		expected_ES_Values.put(exam_Pat_Scope_NoPreClean,"0");
		expected_ES_Values.put(exam_Pat_AfterPreClean,"0");
		expected_ES_Values.put(exam__Pat_NoScope,"0");
		
		actual_ES_Values = ES_A.get_ES_TableValues();
		
		if(actual_ES_Values.size() != expected_ES_Values.size()){
			System.out.println("Mismatch of number of columns in table ");
		}else{
			Iterator it =expected_ES_Values.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				String expected_Val=expected_ES_Values.get(pairs.getKey());
				String actual_Val=actual_ES_Values.get(pairs.getKey());
				if(actual_Val.equalsIgnoreCase(expected_Val)){
					result="Pass - "+pairs.getKey()+" value is "+actual_Val;
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!# - "+pairs.getKey()+" value was supposed to be "+expected_Val+" but it was "+actual_Val;
				}
				result_ES+=result+"; ";
			}
		}
		System.out.println(result_ES);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result_ES);
		
		//Step- 28 to 33
		//Checking Day -7 to Day -1 data for Facility 3 of Exam Summary table
		description="Checking the Exam Summary table values for Day -7 to Day -1 data of "+facility3;
		expected="Exam Summary table values should match with the expected values";
		System.out.println(description);
		EM_A.Change_Facility(facility3);
		Thread.sleep(3000);
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		expected_ES_Values.clear();
		actual_ES_Values.clear();
		result_ES="";
		
		expected_ES_Values.put(active_PR,"0");
		expected_ES_Values.put(scopesUsed,"8");
		expected_ES_Values.put(exam_Pat,"8");
		expected_ES_Values.put(exam_Scope_NoPat,"0");
		expected_ES_Values.put(exam_Scope_PreClean_Nopat,"0");
		expected_ES_Values.put(exam_Pat_Scope_NoPreClean,"0");
		expected_ES_Values.put(exam_Pat_AfterPreClean,"0");
		expected_ES_Values.put(exam__Pat_NoScope,"0");
		
		actual_ES_Values = ES_A.get_ES_TableValues();
		
		if(actual_ES_Values.size() != expected_ES_Values.size()){
			System.out.println("Mismatch of number of columns in table ");
		}else{
			Iterator it =expected_ES_Values.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				String expected_Val=expected_ES_Values.get(pairs.getKey());
				String actual_Val=actual_ES_Values.get(pairs.getKey());
				if(actual_Val.equalsIgnoreCase(expected_Val)){
					result="Pass - "+pairs.getKey()+" value is "+actual_Val;
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!# - "+pairs.getKey()+" value was supposed to be "+expected_Val+" but it was "+actual_Val;
				}
				result_ES+=result+"; ";
			}
		}
		System.out.println(result_ES);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result_ES);
		
		//Step- 34 
		EM_A.Change_Facility(facility1);
		Thread.sleep(3000);
		
		//Step-35
		ES_A.Clear_Filter();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		//Step-36
		ES_A.click_ActiveProcedureRoomCount();
		Thread.sleep(2000);
		description="verifying the Active Procedure Room names on Exam Management Screen";
		expected="Exam Management should displaye the expected Procedure Rooms list";
		System.out.println(description);
		procRoomList.clear();
		procRoomList=EM_A.get_EM_ColumnValues("Procedure Room");
		
		String[] expected_ProcRooms={"F1 Procedure Room 1","F1 Procedure Room 2","F1 Procedure Room 8"};
		if(expected_ProcRooms.length==procRoomList.size()){
			for(String procRoom:expected_ProcRooms){
				if(procRoomList.contains(procRoom)){
					result="Pass - Exam Management screen is displaying "+procRoom;
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!# - Exam Management screen is not displaying "+procRoom;
				}
				result_EM+=result+"; ";
			}
		}else{
			UAS.resultFlag="#Failed!#";
			result_EM="#Failed!# - The number of Procedure Rooms should be 3 but it is displaying only "+procRoomList.size();
		}
		System.out.println(result_EM);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result_EM);
		
		//Step-37
		IP_A.Click_IP_Dashboard();
		EM_A.Change_Facility(facility1);
		Thread.sleep(3000);
		ES_A.Clear_Filter();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		//Step-38
		ES_A.click_ScopesUsedCount();
		Thread.sleep(2000);
		
		//Verify all the scan dates are from Day -7 to Day -1 for all records
		description="Verify all the scan dates are from "+dayMinus7+" to "+dayMinus1+" for all records";
		expected="All the scan dates are from yesterday for all records";
		System.out.println(description);
		
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verify whether # of Scopes column contains 0
		description="Verify whether # of Scopes column does not contain 0 for "+dayMinus7+" to "+dayMinus1;
		expected="# of Scopes column should not contain 0";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("# of Scopes"));
		if(!completeColumnValues.contains("0")){
			result="Pass - # of Scopes does not have 0";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# -# of Scopes column contains 0 for "+dayMinus7+" to "+dayMinus1;
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		
		//Step-39
		IP_A.Click_IP_Dashboard();
		EM_A.Change_Facility(facility1);
		Thread.sleep(3000);
		ES_A.Clear_Filter();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		//Step-40
		ES_A.click_ExamWithPatientCount();
		Thread.sleep(2000);
		//Verify all the scan dates are from Day -7 to Day -1 for all records
		description="Verify all the scan dates are from "+dayMinus7+" to "+dayMinus1+" for all records";
		expected="All the scan dates are from yesterday for all records";
		System.out.println(description);
		
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verify whether PatientID is blank when filtered for Facility 1 in Day -7 to Day -1 range 
		description="verify whether PatientID is not blank when filtered for Facility 1 in "+dayMinus7+" to "+dayMinus1+" range";
		expected="Patient ID should not be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient ID"));
		if(!completeColumnValues.contains("-")){
			result="Pass - Patient ID does not have blank values when filtered with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# -Patient ID contains Blank values when filtered with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Step-41
		IP_A.Click_IP_Dashboard();
		EM_A.Change_Facility(facility1);
		Thread.sleep(3000);
		ES_A.Clear_Filter();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		//step-42
		ES_A.click_ExamWithScope_NoPatCount();
		Thread.sleep(2000);
		//Verify all the scan dates are from Day -7 to Day -1 for all records
		description="Verify all the scan dates are from "+dayMinus7+" to "+dayMinus1+" for all records";
		expected="All the scan dates are from yesterday for all records";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result_EM);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verify whether Patient ID is blank for all the records for Facility 1 in Day -7 to Day -1 range 
		description="verify whether PatientID is blank when filtered for "+facility1+" in "+dayMinus7+" to "+dayMinus1+" range";
		expected="Patient ID should be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient ID"));
		result="";
		for(String patientID: completeColumnValues){
			if(!patientID.equalsIgnoreCase("-")){
				UAS.resultFlag="#Failed!#";
				result="#Failed!# -Patient ID does not contains Blank values when filtered with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
				break;
			}
		}
		if(!result.contains("#Failed!#")){
			result="Pass - Patient ID has all blank values when filtered with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verifying the Ref# for facility 1 for Day-7 to Day-1 Range
		description="Verifying the Ref# column values by clicking Exams with Scope, but no Patient ID on Exam Summary screen for "+facility1+" in "+dayMinus7+" to "+dayMinus1+" range";
		expected="The Ref# should contain 7-2, 3-3";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Ref #"));
		List<String> expectedRefNo=new ArrayList<String>();
		expectedRefNo.add("7-2");
		expectedRefNo.add("3-3");
		if(completeColumnValues.equals(expectedRefNo)){
			result="Pass - The Ref# has the expected values when filtered with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The Ref# does not have the expected values when filtered with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//step-43
		IP_A.Click_IP_Dashboard();
		EM_A.Change_Facility(facility1);
		Thread.sleep(3000);
		ES_A.Clear_Filter();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		//step-44
		ES_A.click_ExamWithScopePreclean_NoPatCount();
		Thread.sleep(2000);
		
		//Verify all the scan dates are from Day -7 to Day -1 for all records
		description="Verify all the scan dates are from "+dayMinus7+" to "+dayMinus1+" for all records";
		expected="All the scan dates are from yesterday for all records";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		
		//verifying that Pre-Clean Date/Time is not blank
		description="Verifying that Pre-Clean Date/Time is not blank after clicking Exams with Scope and Pre-Clean, but no Patient ID on Exam Summary screen for "+facility1+" in "+dayMinus7+" to "+dayMinus1+" range";
		expected="The Pre-Clean Date/Time should not be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Pre-Clean Date/Time"));
		//Verifying Date format for Pre-Clean Date/Time column
		IP_V.verifyDateFormat("Exam Management - Pre-Clean Date/Time",completeColumnValues.get(0) );
		if(!completeColumnValues.contains("-")){
			result="Pass - The Pre-Clean Date/Time is not blank when filtered with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The Pre-Clean Date/Time is blank when filtered with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);

		//verify whether Patient ID is blank for all the records for Facility 1 Day -7 to Day -1
		description="verify whether PatientID is blank when filtered  with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		expected="Patient ID should be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient ID"));
		result="";
		for(String patientID: completeColumnValues){
			if(!patientID.equalsIgnoreCase("-")){
				UAS.resultFlag="#Failed!#";
				result="#Failed!# -Patient ID does not contains Blank values when filtered  with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
				break;
			}
		}
		if(!result.contains("#Failed!#")){
			result="Pass - Patient ID has all blank values when filtered  with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verifying the Ref# for facility 1 for Day -7 to Day -1
		description="Verifying the Ref# column values by clicking Exams with Scope and Pre-Clean, but no Patient ID on Exam Summary screen for "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		expected="The Ref# should contain 7-2";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Ref #"));
		expectedRefNo.clear();
		expectedRefNo.add("7-2");
		if(completeColumnValues.equals(expectedRefNo)){
			result="Pass - The Ref# has the expected values when filtered  with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The Ref# does not have the expected values when filtered  with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		
		//step-45
		IP_A.Click_IP_Dashboard();
		EM_A.Change_Facility(facility1);
		Thread.sleep(3000);
		ES_A.Clear_Filter();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		//Step-46
		ES_A.click_ExamWithPatScope_NoPrecleanCount();
		Thread.sleep(2000);
		
		//Verify all the scan dates are from Day -7 to Day -1 for all records
		description="Verify all the scan dates are from "+dayMinus7+" to "+dayMinus1+" for all records";
		expected="All the scan dates are from yesterday for all records";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verifying the Pre-Clean Date/Time for the records after filtering
		description="verifying the Pre-Clean Date/Time for the Exam Management records after filtering with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range and then clicking Exam with Patient ID and Scope, but no Pre-Clean";
		expected="Pre-clean Date/Time Should be blank for 9-2, 2-2, 4-2, 4-3 and not for 3-2, 10-2";
		System.out.println(description);
		List<String> refNoColumnValues=new ArrayList<String>();
		refNoColumnValues.addAll(EM_A.get_EM_ColumnValues("Ref #"));
		
		List<String> preCleanDateColumnValues=new ArrayList<String>();
		preCleanDateColumnValues.addAll(EM_A.get_EM_ColumnValues("Pre-Clean Date/Time"));
		//Verifying Date format for Pre-Clean Date/Time column
		IP_V.verifyDateFormat("Exam Management - Pre-Clean Date/Time",completeColumnValues.get(0) );
		
		Map<String, String> preCleanDate_RefNo= new HashMap<String, String>();
		ListIterator<String> refNoItr=refNoColumnValues.listIterator();
		ListIterator<String> preCleanItr=preCleanDateColumnValues.listIterator();
		while(refNoItr.hasNext()){
			preCleanDate_RefNo.put(refNoItr.next(), preCleanItr.next());
		}
		
		result="";
		for(Map.Entry<String, String> entry: preCleanDate_RefNo.entrySet()){
			if(entry.getKey().equalsIgnoreCase("3-2")||entry.getKey().equalsIgnoreCase("10-2")){
				if(!entry.getValue().equalsIgnoreCase("-")){
					result+="Pass - The Pre-Clean Date/Time is not blank for "+entry.getKey()+"; ";
				}else{
					UAS.resultFlag="#Failed!#";
					result+="#Failed!# - The Pre-Clean Date/Time should not be blank but it was blank for "+entry.getKey()+"; ";
				}
			}else{
				if(entry.getValue().equalsIgnoreCase("-")){
					result+="Pass - The Pre-Clean Date/Time is blank for "+entry.getKey()+"; ";
				}else{
					UAS.resultFlag="#Failed!#";
					result+="#Failed!# - The Pre-Clean Date/Time should be blank but it was "+entry.getValue()+" for "+entry.getKey()+"; ";
				}
			}
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//step-47
		IP_A.Click_IP_Dashboard();
		EM_A.Change_Facility(facility1);
		Thread.sleep(3000);
		ES_A.Clear_Filter();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		//step-48
		ES_A.click_ExamWithPatAfterPrecleanCount();
		Thread.sleep(2000);
		
		//Verify all the scan dates are from Day -7 to Day -1 for all records
		description="Verify all the scan dates are from "+dayMinus7+" to "+dayMinus1+" for all records";
		expected="All the scan dates are from yesterday for all records";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verifying the Pre-Clean Before Patient for the records after filtering
		description="verifying the Patient after Pre-Clean for the Exam Management records after filtering with "+facility1+" for "+dayMinus7+" to "+dayMinus1+" range and then clicking Exams with Patient ID Scan after Pre-Clean Scan";
		expected="Patient after Pre-Clean Should be blank Yes for 5-2 and No for 6-2";
		System.out.println(description);
		List<String> preCleanBeforePatValues=new ArrayList<String>();
		preCleanBeforePatValues.addAll(EM_A.get_EM_ColumnValues("Patient after Pre-Clean"));
		
		refNoColumnValues.clear();
		refNoColumnValues.addAll(EM_A.get_EM_ColumnValues("Ref #"));
		
		Map<String, String> preCleanBeforePatient_RefNo= new HashMap<String, String>();
		ListIterator<String> preCleanBeforeItr=preCleanBeforePatValues.listIterator();
		ListIterator<String> refItr=refNoColumnValues.listIterator();
		while(refItr.hasNext()){
			preCleanBeforePatient_RefNo.put(refItr.next(), preCleanBeforeItr.next());
		}
		
		result="";
		for(Map.Entry<String, String> entry: preCleanBeforePatient_RefNo.entrySet()){
			if(entry.getKey().equalsIgnoreCase("5-2")){
				if(entry.getValue().equalsIgnoreCase("Yes")){
					result+="Pass - The Patient after Pre-Clean is "+entry.getValue()+" for "+entry.getKey()+"; ";
				}else{
					UAS.resultFlag="#Failed!#";
					result+="#Failed!# - The Patient after Pre-Clean should be Yes but it was "+entry.getValue()+" for "+entry.getKey()+"; ";
				}
			}else if(entry.getKey().equalsIgnoreCase("6-2")) {
				if(entry.getValue().equalsIgnoreCase("No")){
					result+="Pass - The Patient after Pre-Clean is "+entry.getValue()+" for "+entry.getKey()+"; ";
				}else{
					UAS.resultFlag="#Failed!#";
					result+="#Failed!# - The Patient after Pre-Clean should be No but it was "+entry.getValue()+" for "+entry.getKey()+"; ";
				}
			}
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//step-49
		IP_A.Click_IP_Dashboard();
		EM_A.Change_Facility(facility1);
		Thread.sleep(3000);
		ES_A.Clear_Filter();
		ES_A.DateFilter(dayMinus7, dayMinus1);
		ES_A.ApplyFilter();
		Thread.sleep(2000);
		
		//step-50
		String patientID="MRN00028";
		ES_A.click_ExamWithPat_NoScopeCount();
		Thread.sleep(2000);
		
		//Verify all the scan dates are from Day -7 to Day -1 for all records
		description="Verify all the scan dates are from "+dayMinus7+" to "+dayMinus1+" for all records";
		expected="All the scan dates are from yesterday for all records";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Verifying that Scope is blank and PatientID is not blank
		description="Verifying that Scope is blank and PatientID is not blank";
		expected="The Patient ID should be "+patientID+" and there should not be any scope for this exam";
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient ID"));
		
		if(completeColumnValues.size()==1){
			if(completeColumnValues.contains(patientID)){
				result="Pass - The Patient ID displayed is "+patientID+"; ";
			}else{
				UAS.resultFlag="#Failed!#";
				result+="#Failed!# - The Patient ID is suppossed to be "+patientID+" but it was "+completeColumnValues.get(completeColumnValues.size())+"; ";
			}
		}else{
			UAS.resultFlag="#Failed!#";
			result+="#Failed!# - More than one row was displayed when clicked on Exams with PatientID, but no Scope; ";
		}
		
		//Verifying that there is no scope associated
		description="Verifying that there is no scope associated";
		expected="There should be no scopes associated with this exam";
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Scope Scan In Date/Time"));
		
		if(completeColumnValues.get(0).equalsIgnoreCase("No scopes are associated with this exam.")){
			result+="Pass - There is no scope associated with this exam";
		}else{
			UAS.resultFlag="#Failed!#";
			result+="#Failed!# - There should be no Scope associated with this exam but it was "+completeColumnValues.get(0);
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);

		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (Unifia_Admin_Selenium.resultFlag.toLowerCase().contains("#failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	 @AfterTest
	  public void PostTest() throws IOException{
	  	LP_A.CloseDriver();
	  }
}
