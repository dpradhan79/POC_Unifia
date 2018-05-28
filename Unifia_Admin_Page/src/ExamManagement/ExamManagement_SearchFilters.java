package ExamManagement;


import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

public class ExamManagement_SearchFilters {
	
	public TestFrameWork.Emulator.GetIHValues IHV;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public GeneralFunc GF;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public User_Actions ua; //shortcut to link to the UnifiaAdminUserPage.User_Actions java class.
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_EM.EM_Actions EM_A;
	public static TestFrameWork.Unifia_EM.EM_Verification EM_V;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	
	private SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private Calendar cal2 = Calendar.getInstance();
	private int UTCTimeDiffInHours=0;
	private boolean res=false;
	
	private String active_PR="Active Procedure Rooms";
	private String scopesUsed="Scopes Used";
	private String exam_Pat="Exams with Patient ID";
	private String exam_Scope_NoPat="Exams with Scope, but no Patient ID";
	private String exam_Scope_PreClean_Nopat="Exams with Scope and Pre-Clean, but no Patient ID";
	private String exam_Pat_Scope_NoPreClean="Exams with Patient ID and Scope, but no Pre-Clean";
	private String exam_Pat_AfterPreClean="Exams with Patient ID Scan after Pre-Clean Scan";
	private String exam__Pat_NoScope="Exams with Patient ID, but no Scope";
	
	private String description,expected, result;
	private String result_EM="";
	
	public static Connection conn= null;
	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	private String XMLFile="Epic10883_Quality_Issue_Data_Historical.xml";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, Exception {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		
		//To-run this script individually uncomment the following code
		
		/*String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
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
		Thread.sleep(2000);*/

	   	UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();
		UAS.XMLFileName="ExamManagement_SearchFilters_";
		UAS.XMLFileName=IHV.Start_Exec_Log(UAS.XMLFileName);
		UAS.TestCaseNumber=1;
		
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

		UAS.StepNum=1;
		LGPA.Launch_Unifia(UAS.Admin_URL);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, UAS.roleMgr, UAS.userQV1, UAS.userQV1Pwd,"FAC2","FAC3");
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_EM();
		Thread.sleep(2000);		
		
		EM_A.DateFilter(dayMinus7, dayMinus1);
		EM_A.ApplyFilter();
		
		//Step-5
		EM_A.Click_ColumnFilter();
		
		//verifying the column filters
		description="Verifying whether Procedure Room, Exam ID, Patient ID, # of Scopes, Scope Model, Scope Serial Number, Patient after Pre-Clean, and Ref # column search filters are present or not";
		expected="Procedure Room, Exam ID, Patient ID, # of Scopes, Scope Model, Scope Serial Number, Patient after Pre-Clean, and Ref # search filters should be present";
		result=EM_V.Verify_EM_SearchFilters();
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//step-6
		String ProcRoom="Procedure Room 1";
		EM_A.search_ProcRoom(ProcRoom);
		EM_A.Click_Search();
		
		List<String> completeColumnValues=EM_A.get_EM_ColumnValues("Procedure Room");
		for(String procRoom: completeColumnValues){
			if(!procRoom.contains(ProcRoom)){
				res=false;
				break;
			}else{
				res=true;
			}	
		}
		
		if(res){
			result="Pass - Procedure Room column data displayed only procedure rooms whose name contains "+ProcRoom ;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Procedure Room column data displayed contains Procedure Rooms other than "+ProcRoom;
		}
		description="Verifying whether Procedure Room column display records that contains "+ProcRoom+" when filtered with "+ProcRoom;
		expected="Procedure Room column should display records that contains "+ProcRoom+" when filtered with "+ProcRoom;
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		EM_A.clear_ProcRoomSearch();
		
		//Verifying Date format for Exam Date/Time column
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		
		//Verifying Date format for Pre-Clean Date/Time column
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Pre-Clean Date/Time"));
		IP_V.verifyDateFormat("Exam Management - Pre-Clean Date/Time",completeColumnValues.get(0) );
		
		//Verifying Date format for Patient Scan In Date/Time column
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient Scan In Date/Time"));
		IP_V.verifyDateFormat("Exam Management - Patient Scan In Date/Time",completeColumnValues.get(0) );
		
		//Verifying Date format for Scope Scan In Date/Time column
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Scope Scan In Date/Time"));
		IP_V.verifyDateFormat("Exam Management - Scope Scan In Date/Time",completeColumnValues.get(0) );
		
		//Step-7
		String ExamID="";
		try{
    		conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			String stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK "
					+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where Loc.Facilityid_fk=1 and IH.CycleEventID_FK=3 and "
					+ "IH.LastUpdatedDateTime=(Select min(IH.LastUpdatedDateTime) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK where Loc.Facilityid_fk=1 and IH.CycleEventID_FK=3);";
			System.out.println("stmt="+stmt);
			ResultSet PR_RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(PR_RS.next()){
				receivedDataTime=PR_RS.getTimestamp(1);
			}
			PR_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select distinct(IH.AssociationID_FK), Concat(IH.ScanItemID_FK,'-', SC.CycleID) as RefNo, Loc.Name, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate, "
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime, ItemHistoryID_PK,Scope.name as SName,Scope.SerialNumber,Scope.ScopeID_PK,IH.ReceivedDateTime,SM.Name, "
					+ "convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), "
					+ "'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK "
					+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where Loc.Facilityid_fk=1 and IH.CycleEventID_FK=3 and "
					+ "IH.LastUpdatedDateTime=(Select min(IH.LastUpdatedDateTime) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK where Loc.Facilityid_fk=1 and IH.CycleEventID_FK=3);";
			System.out.println("stmt="+stmt);
    		
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				ExamID = PR_RS.getString(1); 
			}
		}catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		
		EM_A.Search_ExamID(ExamID);
		EM_A.Click_Search();
		
		completeColumnValues.clear();
		completeColumnValues=EM_A.get_EM_ColumnValues("Exam ID");
		
		if(completeColumnValues.size()==1){
			if(completeColumnValues.get(0).equalsIgnoreCase(ExamID)){
				result="Pass - Exam Management Screen when filtered with ExamID="+ExamID+" displayed only one record";
			}else{
				UAS.resultFlag="#Failed!#";
				result="#Failed!# - Exam Management Screen when filtered with ExamID="+ExamID+" has not displayed records with ExamID="+ExamID+" but the records displayed was "+completeColumnValues.get(0);
			}
		}else if(completeColumnValues.size()>1){
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Exam Management screen when filtered with ExamID="+ExamID+" has displayed more than one record";
		}
		
		description="Verifying whether Exam ID column display records that contains "+ExamID+" when filtered with ExamID="+ExamID;
		expected="Exam ID column should display records that contains "+ExamID+" when filtered with ExamID="+ExamID;
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		EM_A.clear_ExamIDSearch();
		
		//Step-8
		String PatientID="03";
		EM_A.search_PatientID(PatientID);
		EM_A.Click_Search();
		
		res=false;
		completeColumnValues.clear();
		completeColumnValues=EM_A.get_EM_ColumnValues("Patient ID");
		for(String patID: completeColumnValues){
			if(!patID.contains(PatientID)){
				res=false;
				break;
			}else{
				res=true;
			}	
		}
		
		if(res){
			result="Pass - Filtered records Patient ID column values contain "+PatientID+" in the displayed records" ;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Filtered records Patient ID column values does not contain "+PatientID+" in the displayed records" ;
		}
		description="Verifying whether Patient ID column display records that contains "+PatientID+" when filtered with "+PatientID;
		expected="Patient ID column should display records that contains "+PatientID+" when filtered with "+PatientID;
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		EM_A.clear_PatientIDSearch();
		
		//Step-9
		String scopeCount="2";
		EM_A.search_NumOfScopes(scopeCount);
		EM_A.Click_Search();
		
		res=false;
		completeColumnValues.clear();
		completeColumnValues=EM_A.get_EM_ColumnValues("# of Scopes");
		for(String numOfScopes: completeColumnValues){
			if(!numOfScopes.equalsIgnoreCase(scopeCount)){
				res=false;
				break;
			}else{
				res=true;
			}	
		}
		
		if(res){
			result="Pass - Filtered records # of Scopes column values contain "+scopeCount+" in the displayed records" ;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Filtered records # of Scopes column values does not contain "+scopeCount+" in the displayed records" ;
		}
		description="Verifying whether # of Scopes column display records that contains "+scopeCount+" when filtered with "+scopeCount;
		expected="# of Scopes column should display records that contains "+scopeCount+" when filtered with "+scopeCount;
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		EM_A.clear_NumOfScopesSearch();
		
		//Step-10
		String scopeModel="GIF";
		EM_A.search_ScopeModel(scopeModel);
		EM_A.Click_Search();
		Thread.sleep(2000);
		
		res=false;
		completeColumnValues.clear();
		completeColumnValues=EM_A.get_EM_ColumnValues("Scope Model");
		for(String model: completeColumnValues){
			if(!model.contains(scopeModel)){
				res=false;
				break;
			}else{
				res=true;
			}	
		}
		
		if(res){
			result="Pass - Filtered records Scope Model column values contain "+scopeModel+" in the displayed records" ;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Filtered records Scope Model column values does not contain "+scopeModel+" in the displayed records" ;
		}
		description="Verifying whether Scope Model column display records that contains "+scopeModel+" when filtered with "+scopeModel;
		expected="Scope Model column should display records that contains "+scopeModel+" when filtered with "+scopeModel;
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		EM_A.clear_ScopeModelSearch();
		
		//Step-11
		String scopeSerialNum="65";
		EM_A.search_ScopeSerialNum(scopeSerialNum);
		EM_A.Click_Search();
		Thread.sleep(2000);
		
		res=false;
		completeColumnValues.clear();
		completeColumnValues=EM_A.get_EM_ColumnValues("Scope Serial Number");
		for(String num: completeColumnValues){
			if(!num.contains(scopeSerialNum)){
				res=false;
				break;
			}else{
				res=true;
			}	
		}
		
		if(res){
			result="Pass - Filtered records Scope Serial Number column values contain "+scopeSerialNum+" in the displayed records" ;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Filtered records Scope Serial Number column values does not contain "+scopeSerialNum+" in the displayed records" ;
		}
		description="Verifying whether Scope Serial Number column display records that contains "+scopeSerialNum+" when filtered with "+scopeSerialNum;
		expected="Scope Serial Number column should display records that contains "+scopeSerialNum+" when filtered with "+scopeSerialNum;
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		EM_A.clear_ScopeSerialNumSearch();
		
		//Step-12
		String preClean="Yes";
		EM_A.search_PreCleanBeforePatient(preClean);
		EM_A.Click_Search();
		Thread.sleep(2000);
		
		res=false;
		completeColumnValues.clear();
		completeColumnValues=EM_A.get_EM_ColumnValues("Patient after Pre-Clean");
		for(String num: completeColumnValues){
			if(!num.contains(preClean)){
				res=false;
				break;
			}else{
				res=true;
			}	
		}
		
		if(res){
			result="Pass - Filtered records Patient after Pre-Clean column values contain "+preClean+" in the displayed records" ;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Filtered records Patient after Pre-Clean column values does not contain "+preClean+" in the displayed records" ;
		}
		description="Verifying whether Patient after Pre-Clean column display records that contains "+preClean+" when filtered with "+preClean;
		expected="Patient after Pre-Clean column should display records that contains "+preClean+" when filtered with "+preClean;
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		EM_A.clear_PreCleanBeforePatientSearch();
		
		//Step-13
		String refNo="7-1";
		EM_A.Search_RefNo(refNo);
		EM_A.Click_Search();
		Thread.sleep(2000);
		
		res=false;
		completeColumnValues.clear();
		completeColumnValues=EM_A.get_EM_ColumnValues("Ref #");
		for(String num: completeColumnValues){
			if(!num.contains(refNo)){
				res=false;
				break;
			}else{
				res=true;
			}	
		}
		
		if(res){
			result="Pass - Filtered records Ref # column values contain "+refNo+" in the displayed records" ;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Filtered records Ref # column values does not contain "+refNo+" in the displayed records" ;
		}
		description="Verifying whether Ref # column display records that contains "+refNo+" when filtered with "+refNo;
		expected="Ref # column should display records that contains "+refNo+" when filtered with "+refNo;
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		EM_A.clear_RefNoSearch();
		
		//TestCaseID - 11160
		//step-5
		EM_A.select_EM_Filter(active_PR);
		EM_A.ApplyFilter();
		Thread.sleep(2000);

		description="Verifying the Active Procedure Rooms list for "+dayMinus7+" to "+dayMinus1+" range";
		expected="The Active Procedure Rooms list must contain F1 Procedure Room 1, F1 Procedure Room 2, F1 Procedure Room 8";
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
		
		//Step-6
		EM_A.select_EM_Filter(scopesUsed);
		EM_A.ApplyFilter();
		Thread.sleep(5000);
		
		//Verify all the scan dates are from Day -7 to Day -1 for all records
		description="Verify all the scan dates are from "+dayMinus7+" to "+dayMinus1+" for all records";
		expected="All the scan dates are from yesterday for all records";
		System.out.println(description);
		
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		UAS.Result="";
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verify whether # of Scopes column does not contain 0
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
		
		//step-7
		EM_A.select_EM_Filter(exam_Pat);
		EM_A.ApplyFilter();
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
		description="verify whether PatientID is not blank when filtered  with "+exam_Pat+" in "+dayMinus7+" to "+dayMinus1+" range";
		expected="Patient ID should not be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient ID"));
		if(!completeColumnValues.contains("-")){
			result="Pass - Patient ID does not have blank values when filtered with "+exam_Pat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# -Patient ID contains Blank values when filtered with "+exam_Pat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Step-8
		EM_A.select_EM_Filter(exam_Scope_NoPat);
		EM_A.ApplyFilter();
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
		description="verify whether PatientID is blank when filtered with "+exam_Scope_NoPat+" in "+dayMinus7+" to "+dayMinus1+" range";
		expected="Patient ID should be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient ID"));
		result="";
		for(String patientID: completeColumnValues){
			if(!patientID.equalsIgnoreCase("-")){
				UAS.resultFlag="#Failed!#";
				result="#Failed!# -Patient ID does not contains Blank values when filtered with "+exam_Scope_NoPat+" for "+dayMinus7+" to "+dayMinus1+" range";
				break;
			}
		}
		if(!result.contains("#Failed!#")){
			result="Pass - Patient ID has all blank values when filtered with "+exam_Scope_NoPat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verifying the Ref# for facility 1 for Day-7 to Day-1 Range
		description="Verifying the Ref# column values by clicking Exams with Scope, but no Patient ID on Exam Summary screen with "+exam_Scope_NoPat+" in "+dayMinus7+" to "+dayMinus1+" range";
		expected="The Ref# should contain 7-2, 3-3";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Ref #"));
		List<String> expectedRefNo=new ArrayList<String>();
		expectedRefNo.add("7-2");
		expectedRefNo.add("3-3");
		if(completeColumnValues.equals(expectedRefNo)){
			result="Pass - The Ref# has the expected values when filtered with "+exam_Scope_NoPat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The Ref# does not have the expected values when filtered with "+exam_Scope_NoPat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Step-9
		EM_A.select_EM_Filter(exam_Scope_PreClean_Nopat);
		EM_A.ApplyFilter();
		Thread.sleep(2000);
		
		//Verify all the scan dates are with Today's date for all records
		description="Verify all the scan dates are with Today's date which is "+today+" for all records";
		expected="All the scan dates are with Today's date which is "+today+" for all records";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Exam Date/Time"));
		//Verifying Date format for Exam Date/Time column
		IP_V.verifyDateFormat("Exam Management - Exam Date/Time",completeColumnValues.get(0) );
		result=EM_V.Verify_EM_DateRange(completeColumnValues, DayMinus7, DayMinus1, df);
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verifying that Pre-Clean Date/Time is not blank
		description="Verifying that Pre-Clean Date/Time is not blank after clicking Exams with Scope and Pre-Clean, but no Patient ID on Exam Summary screen with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		expected="The Pre-Clean Date/Time should not be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Pre-Clean Date/Time"));
		//Verifying Date format for Pre-Clean Date/Time column
		IP_V.verifyDateFormat("Exam Management - Pre-Clean Date/Time",completeColumnValues.get(0) );
		if(!completeColumnValues.contains("-")){
			result="Pass - The Pre-Clean Date/Time is not blank when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The Pre-Clean Date/Time is blank when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verify whether Patient ID is blank for all the records with exam with Scope and Pre-Clean with No patientID for dayMinus7 to dayMinus1 range; 
		description="verify whether PatientID is blank when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		expected="Patient ID should be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient ID"));
		result="";
		for(String patientID: completeColumnValues){
			if(!patientID.equalsIgnoreCase("-")){
				UAS.resultFlag="#Failed!#";
				result="#Failed!# -Patient ID does not contains Blank values when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
				break;
			}
		}
		if(!result.contains("#Failed!#")){
			result="Pass - Patient ID has all blank values when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verifying the Ref# for facility 1 for Today's date
		description="Verifying the Ref# column values by clicking Exams with Scope and Pre-Clean, but no Patient ID on Exam Summary screen with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		expected="The Ref# should contain 7-2";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Ref #"));
		expectedRefNo.clear();
		expectedRefNo.add("7-2");
		if(completeColumnValues.equals(expectedRefNo)){
			result="Pass - The Ref# has the expected values when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The Ref# does not have the expected values when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Step-11
		EM_A.select_EM_Filter(exam_Pat_Scope_NoPreClean);
		EM_A.ApplyFilter();
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
		
		//verify whether PatientID is blank when filtered with Exams with Patient ID and Scope, but no Pre-Clean in Day -7 to Day -1 range 
		description="verify whether PatientID is not blank when filtered for Facility 1 in "+dayMinus7+" to "+dayMinus1+" range";
		expected="Patient ID should not be blank";
		System.out.println(description);
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Patient ID"));
		if(!completeColumnValues.contains("-")){
			result="Pass - Patient ID does not have blank values when filtered with "+exam_Pat_Scope_NoPreClean+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# -Patient ID contains Blank values when filtered with "+exam_Pat_Scope_NoPreClean+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//verifying the Pre-Clean Date/Time for the records after filtering
		description="verifying the Pre-Clean Date/Time for the Exam Management records after filtering with "+exam_Pat_Scope_NoPreClean+" for "+dayMinus7+" to "+dayMinus1+" range";
		expected="Pre-clean Date/Time Should be blank for 9-2, 2-2, 4-2, 4-3 and not for 3-2, 10-2";
		System.out.println(description);
		List<String> refNoColumnValues=new ArrayList<String>();
		refNoColumnValues.addAll(EM_A.get_EM_ColumnValues("Ref #"));
		
		List<String> preCleanDateColumnValues=new ArrayList<String>();
		preCleanDateColumnValues.addAll(EM_A.get_EM_ColumnValues("Pre-Clean Date/Time"));
		
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
		
		//Step-12
		EM_A.select_EM_Filter(exam_Pat_AfterPreClean);
		EM_A.ApplyFilter();
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
		
		//verifying the Patient after Pre-Clean for the records after filtering
		description="verifying the Patient after Pre-Clean for the Exam Management records after filtering with "+exam_Pat_AfterPreClean+" for "+dayMinus7+" to "+dayMinus1+" range";
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
					result+="Pass - The Patient after Pre-Clean is "+entry.getKey()+" for "+entry.getKey()+"; ";
				}else{
					UAS.resultFlag="#Failed!#";
					result+="#Failed!# - The Patient after Pre-Clean should be Yes it was "+entry.getValue()+" for "+entry.getKey()+"; ";
				}
			}else if(entry.getKey().equalsIgnoreCase("6-2")) {
				if(entry.getValue().equalsIgnoreCase("No")){
					result+="Pass - The Patient after Pre-Clean is "+entry.getKey()+" for "+entry.getKey()+"; ";
				}else{
					UAS.resultFlag="#Failed!#";
					result+="#Failed!# - The Patient after Pre-Clean should be No it was "+entry.getValue()+" for "+entry.getKey()+"; ";
				}
			}
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//Step-13
		EM_A.select_EM_Filter(exam__Pat_NoScope);
		EM_A.ApplyFilter();
		Thread.sleep(2000);
		
		String patientID="MRN00028";
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
		description="Verifying that Scope is blank and PatientID is not blank when filtered with Exams with PatientID, but no Scope";
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
			result+="#Failed!# - More than one row was displayed when filtered with Exams with PatientID, but no Scope; ";
		}
		
		//step-14
		EM_A.select_EM_Filter(exam_Scope_PreClean_Nopat);
		EM_A.ApplyFilter();
		Thread.sleep(2000);
		refNo="7-2";
		
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Ref #"));
		expectedRefNo.clear();
		expectedRefNo.add(refNo);
		if(completeColumnValues.equals(expectedRefNo)){
			result="Pass - The Ref# has the expected values when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The Ref# does not have the expected values when filtered with "+exam_Scope_PreClean_Nopat+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		//step-15
		EM_A.Click_Details(refNo);
		WF_V.Verify_RefNum(refNo);
		
		WF_A.UpdatePatient(patientID);
		WF_A.Save();
		Thread.sleep(5000);
		
		completeColumnValues.clear();
		completeColumnValues.addAll(EM_A.get_EM_ColumnValues("Procedure Room"));
		
		if(completeColumnValues.contains("Not found")){
			result="Pass- There were no records after adding patient to RefNo="+refNo+" and then filtering with "+exam__Pat_NoScope;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - There were some records after adding patient to RefNo="+refNo+" and then filtering with "+exam__Pat_NoScope+" for "+dayMinus7+" to "+dayMinus1+" range";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(UAS.XMLFileName, description, expected, result);
		
		IHV.Close_Exec_Log(UAS.XMLFileName, "Test Completed", UAS.TestCaseNumber);
		if (UAS.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	@AfterTest
	public void PostTTest() throws IOException{
		LP_A.CloseDriver();
	}
}
