package ExamManagement;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;
import TestFrameWork.RegressionTest.*;
import TestFrameWork.RegressionTest.ReprocessingRoom.*;
import TestFrameWork.RegressionTest.ProcedureRoom.*;
import TestFrameWork.RegressionTest.SoiledArea.*;
import TestFrameWork.RegressionTest.StorageArea.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.UnifiaAdminUserPage.User_Actions;

public class ExamManagement_Today {	
	/**
	 * Nicole McKinley 10/25/2017 
	 * 
	 * Testing the Exam Management screen
	 */
	
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
	
	public int CurrentPatientPK;  //patient's primary key
	public String CurrentPatientID;
	public int AssociationID; //association ID of the record being modified. 
	public int LocationID; //the location's primary key
	public String LocationName; //location name (i.e. Procedure Room 1) 
	public int LocationID_PK;
	public String RefNo; //the reference number of the record being modified.
	public String ScopeName;
	public String ScopeSerialNo;
	public String ScopeModel;
	public int ScopePK;
	public int NumScopes;
	public String RefNo2; //the reference number of the record being modified.
	public String ScopeName2;
	public String ScopeSerialNo2;
	public String ScopeModel2;
	public int ScopePK2;

	public int CurrentIHPK; // used to get the current records scan history primary key

	public String ExamDate; //the date of the record being modified.
	public String ExamTime; //the time of the record being modified.
	public String Scope1InDateTime,Scope2InDateTime;
	public String Scope1InDateTimeNoSec,Scope2InDateTimeNoSec;
	public Date CurrentExamDate=new Date(),CurrentExamTime=new Date();
	public Date CurrentPatientDateTime=new Date();
	public Date Scope1PreCleanDateTime=new Date();
	public Date Scope1InDateTimeStamp=new Date();
	public Date Scope2PreCleanDateTime=new Date();
	public String ExamDateTime;

	public String PatientScanDate,Scope1PreCleanScanDate,Scope2PreCleanScanDate;
	public String Scope1PrecleanBeforePatient="",Scope2PrecleanBeforePatient="";
	
	public String Result_ProcRoom;
	public String Result_ExamDate;
	public String Result_ExamID;
	public String Result_Patient;
	public String Result_PatientScanDate;
	public String Result_NumScopes;
	public String Result_Scope1Model;
	public String Result_Scope1Name;
	public String Result_Scope1SerialNo;
	public String Result_RefNum1;
	public String Result_Scope1InDate;
	public String Result_Scope1PrecleanDateTime;
	public String Result_Scope1PrecleanBeforePatient;
	public String Result_Scope2Model;
	public String Result_Scope2Name;
	public String Result_Scope2SerialNo;
	public String Result_RefNum2;
	public String Result_Scope2InDate;
	public String Result_Scope2PrecleanDateTime;
	public String Result_Scope2PrecleanBeforePatient;
	public String Result_Patient_NoScopes;

	public String CurrentPhyID,ModPhyID;
	public int CurrentPhyPK,ModPhyPK;
	
	public int UTCTimeDiffInHours=0;		
	
	public String stmt;
    public String stmt1;
	public ResultSet PR_RS;  //result set used to get the PR record to be modified.
	public Boolean Res;
	
	public String Description,Expected, Result;

	public static Connection conn= null;
	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;
	private String presWorkDir=System.getProperty("user.dir");
	private String fileDestFolder="\\XMLFolder";
	private String scenarioFilePath="TestData\\AppTestData";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	private String testScenarioXML="TestData\\AppTestData\\TestScenarios.xml";
	private String XMLFile="Epic10883_Quality_Issue_Data_Today.xml";

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, Exception {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		//
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
		UAS.XMLFileName="Exam_Management_Today_";
		UAS.XMLFileName=IHV.Start_Exec_Log(UAS.XMLFileName);
		UAS.TestCaseNumber=1;

		UAS.StepNum=1;
		LGPA.Launch_Unifia(UAS.Admin_URL);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, UAS.roleMgr, UAS.userQV1, UAS.userQV1Pwd,"FAC2","FAC3");
		
		
		for(int i=0;i<20;i++){
			NumScopes=0;
			AssociationID=0;
			RefNo="";
			ScopeName="";
			ScopeSerialNo="";
			ScopeModel="";
			ScopePK=0;
			RefNo2="";
			ScopeName2="";
			ScopeSerialNo2="";
			ScopeModel2="";
			ScopePK2=0;
			LocationName="";
			CurrentIHPK=0;
			Scope1InDateTime="";
			PatientScanDate="";
			CurrentPatientID="";
			CurrentPatientPK=0;
			CurrentPatientDateTime=new Date();
			Scope1InDateTimeStamp=new Date();
			Scope1PreCleanScanDate="";
			Scope1PreCleanDateTime=new Date();
			Scope2PreCleanScanDate="";
			Scope2PreCleanDateTime=new Date();
	    	try{
	    		conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
				Statement statement = conn.createStatement();
				
				stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK "
						+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where Loc.Facilityid_fk=1 and IH.CycleEventID_FK=3 and "
						+ "IH.LastUpdatedDateTime=(Select min(IH.LastUpdatedDateTime) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK where Loc.Facilityid_fk=1 and IH.CycleEventID_FK=3);";
				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
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
					AssociationID = PR_RS.getInt(1); 
					RefNo = PR_RS.getString(2);
					LocationName=PR_RS.getString(3);
					ExamDate=PR_RS.getString(4);
					ExamTime=PR_RS.getString(5);
					CurrentIHPK=PR_RS.getInt(6);
					ScopeName=PR_RS.getString(7);
					ScopeSerialNo=PR_RS.getString(8);
					ScopePK=PR_RS.getInt(9);
					CurrentExamDate=PR_RS.getDate(10);
					CurrentExamTime=PR_RS.getTime(10);
					ScopeModel=PR_RS.getString(11);
					Scope1InDateTime=PR_RS.getString(12);
					Scope1InDateTimeNoSec=PR_RS.getString(13);
					Scope1InDateTimeStamp=PR_RS.getTimestamp(14);
				}		
				PR_RS.close();
				stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
				statement.executeUpdate(stmt1);
				
				stmt="select count(case when CycleEventID_FK=3 then CycleEventID_FK else null end) from  ItemHistory h where AssociationID_FK="+AssociationID+";";
				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					NumScopes = PR_RS.getInt(1); 
				}
				PR_RS.close();
				stmt="Select PatientID_PK, PatientID, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime "
						+ "from ItemHistory IH join Patient on IH.ScanItemID_FK=Patient.PatientID_PK where AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=4";

				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					CurrentPatientPK = PR_RS.getInt(1); 
					CurrentPatientID = PR_RS.getString(2);
					PatientScanDate=PR_RS.getString(3);
					CurrentPatientDateTime=PR_RS.getTimestamp(4);
				}
		
				PR_RS.close();
				String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			   	if (CurrentPatientID.equalsIgnoreCase("")){
			   		CurrentPatientID="";
				}
			   	else{
			   		PR_RS=statement.executeQuery(stmtPatEncr);
					while(PR_RS.next()){
						CurrentPatientPK = PR_RS.getInt(1); 
						//LastUpdatedDate= PR_RS.getString(2);
				   		CurrentPatientID = PR_RS.getString(3);
					}
				   	PR_RS.close();
			   	}
			   	stmt="select ItemHistoryID_PK, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime from ItemHistory where CycleEventID_FK=8 and ScanItemID_FK="+ScopePK+" and AssociationID_FK="+AssociationID;
			   	CurrentIHPK=0;
				System.out.println("stmt="+stmt);

	    		PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					CurrentIHPK =PR_RS.getInt(1); 
					Scope1PreCleanScanDate=PR_RS.getString(2);
					Scope1PreCleanDateTime=PR_RS.getTimestamp(3);
				}		
				PR_RS.close();
				if(i==1){//update one record's physician to verify navigating to and from EM to Workflow details. 
					stmt="select IH.ScanItemID_FK, ST.StaffID from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where AssociationID_FK="+AssociationID+" and CycleEventID_FK=5 and ReceivedDateTime=(select MAX(ReceivedDateTime) from ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=5)";
		    		PR_RS = statement.executeQuery(stmt);
					while(PR_RS.next()){
						CurrentPhyPK=PR_RS.getInt(1); 
						CurrentPhyID=PR_RS.getString(2);
						
					}		
					PR_RS.close();

					stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=1 and StaffID_PK!="+CurrentPhyPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=1 and StaffID_PK!="+CurrentPhyPK+")";
					System.out.println("stmt="+stmt);
		    		
		    		PR_RS = statement.executeQuery(stmt);
					while(PR_RS.next()){
						ModPhyPK= PR_RS.getInt(1); 
						ModPhyID = PR_RS.getString(2);

					}		
					PR_RS.close();
				}				
				conn.close();
	    	}
	    	catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
	    	
	    	if(!PatientScanDate.equalsIgnoreCase("")){
		    	if(CurrentPatientDateTime.after(Scope1InDateTimeStamp)){
					ExamDateTime=Scope1InDateTimeNoSec;
				}else {
					ExamDateTime=PatientScanDate;
				}
	    	}else {
	    		ExamDateTime=Scope1InDateTimeNoSec;
	    	}
	    	
		    System.out.println("AssociationID="+AssociationID);
		    System.out.println("RefNo1="+RefNo);
		    System.out.println("LocationName"+LocationName);
		    System.out.println("Date1="+ExamDate);
		    System.out.println("Scope1InDateTime="+Scope1InDateTime);
		    System.out.println("Scope1PreCleanScanDate="+Scope1PreCleanScanDate);
		    System.out.println("CurrentPatientID="+CurrentPatientID);
		    System.out.println("PatientScanDate="+PatientScanDate);
		    System.out.println("NumScopes="+NumScopes);
		    
		    
		    if(!Scope1PreCleanScanDate.equalsIgnoreCase("")&&!PatientScanDate.equalsIgnoreCase("")){
				if(CurrentPatientDateTime.after(Scope1PreCleanDateTime)){ 
					Scope1PrecleanBeforePatient="Yes";
				}else {
					Scope1PrecleanBeforePatient="No";
				}
		    }else {
				Scope1PrecleanBeforePatient="No";
			}
		    System.out.println("Scope1PrecleanBeforePatient="+Scope1PrecleanBeforePatient);

			IP_A.Click_InfectionPrevention();
			IP_A.Click_EM();
			EM_A.Click_ColumnFilter();
			EM_A.Search_RefNo(RefNo);
			EM_A.Click_Search();

			Description="Verify Procedure Room";
			Expected="Procedure Room="+LocationName;
			Result_ProcRoom=EM_V.Verify_ProcedureRoom(LocationName);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ProcRoom);

			Description="Verify Exam Date";
			Expected="Exam Date ="+ExamDateTime;
			Result_ExamDate=EM_V.Verify_ExamDate(ExamDateTime);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamDate);

			Description="Verify Exam ID";
			Expected="Exam ID ="+AssociationID;
			Result_ExamID=EM_V.Verify_ExamID(Integer.toString(AssociationID));
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamID);

			Description="Verify Patient ID";
			Expected="Patient ID ="+CurrentPatientID;
			Result_Patient=EM_V.Verify_Patient(CurrentPatientID);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Patient);

			Description="Verify Patient Scan Date/Time";
			Expected="Patient Scan Date/Time ="+PatientScanDate;
			Result_PatientScanDate=EM_V.Verify_PatientDate(PatientScanDate);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_PatientScanDate);

			Description="Verify # of Scopes";
			Expected="Verify # of Scopes ="+NumScopes;
			Result_NumScopes=EM_V.Verify_NumScpopes(Integer.toString(NumScopes));
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_NumScopes);

			Description="Verify Scope Scan in Time";
			Expected="Verify Scope Scan in Time="+Scope1InDateTimeNoSec;
			Result_Scope1InDate=EM_V.Verify_ScopeIn(Scope1InDateTimeNoSec);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1InDate);

			Description="Verify Scope Model";
			Expected="Verify Scope Model="+ScopeModel;
			Result_Scope1Model=EM_V.Verify_ScopeModel(ScopeModel);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1Model);

			Description="Verify Scope Serial Number";
			Expected="Verify Scope Serial Number="+ScopeSerialNo;
			Result_Scope1SerialNo=EM_V.Verify_ScopeSerialNo(ScopeSerialNo);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1SerialNo);

			Description="Verify Pre-Clean Scan Date/Time";
			Expected="Verify Pre-Clean Scan Date/Time="+Scope1PreCleanScanDate;
			Result_Scope1PrecleanDateTime=EM_V.Verify_Preclean(Scope1PreCleanScanDate);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1PrecleanDateTime);

			Description="Verify Pre-Clean Before Patient";
			Expected="Verify Pre-Clean Before Patient="+Scope1PrecleanBeforePatient;
			Result_Scope1PrecleanBeforePatient=EM_V.Verify_PrecleanBeforePatient(Scope1PrecleanBeforePatient);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1PrecleanBeforePatient);

			Description="Verify Ref #";
			Expected="Verify Ref #="+RefNo;
			Result_RefNum1=EM_V.Verify_RefNo(RefNo);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_RefNum1);
			
			if(i==0){
			    IP_A.Click_Details(RefNo);				
				WF_A.Cancel("No");
				EM_A.Click_ColumnFilter();
				String Result_FilterAfterCancel=EM_V.Verify_RefNoFilter(RefNo);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_FilterAfterCancel);

			}else if(i==1){
			    IP_A.Click_Details(RefNo);
				WF_A.UpdatePhysician(ModPhyID);
				WF_A.Save();
				EM_A.Click_ColumnFilter();
				String Result_FilterAfterSave=EM_V.Verify_RefNoFilter(RefNo);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_FilterAfterSave);
			}


			if(NumScopes>1){
		    	CurrentIHPK=0;
		    	try{
		    		conn= DriverManager.getConnection(UAS.url, UAS.user,UAS.pass);		
					Statement statement = conn.createStatement();
					
					stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK "
							+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where IH.AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=3 and"
							+ " Scope.ScopeID_PK!="+ScopePK+";";
					System.out.println("stmt="+stmt);
					PR_RS = statement.executeQuery(stmt);
					Timestamp receivedDataTime=null;
					while(PR_RS.next()){
						receivedDataTime=PR_RS.getTimestamp(1);
					}
					PR_RS.close();	
					UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
					System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
					
					stmt="Select Concat(IH.ScanItemID_FK,'-', SC.CycleID) as RefNo, Scope.name as SName,Scope.SerialNumber,Scope.ScopeID_PK,IH.ReceivedDateTime,SM.Name, "
							+ "convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
							+ "IH.ItemHistoryID_PK from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK "
							+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where IH.AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=3 and"
							+ " Scope.ScopeID_PK!="+ScopePK+";";
					System.out.println("stmt="+stmt);
		    		
		    		PR_RS = statement.executeQuery(stmt);
					while(PR_RS.next()){
						RefNo2 = PR_RS.getString(1);
						ScopeName2=PR_RS.getString(2);
						ScopeSerialNo2=PR_RS.getString(3);
						ScopePK2=PR_RS.getInt(4);
						CurrentExamDate=PR_RS.getDate(5);
						CurrentExamTime=PR_RS.getTime(5);
						ScopeModel2=PR_RS.getString(6);
						Scope2InDateTime=PR_RS.getString(7);
						Scope2InDateTimeNoSec=PR_RS.getString(8);
						CurrentIHPK=PR_RS.getInt(9);
						
					}		
					PR_RS.close();
					stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
					statement.executeUpdate(stmt1);
			
					PR_RS.close();

				   	stmt="select ItemHistoryID_PK, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime from ItemHistory where CycleEventID_FK=8 and ScanItemID_FK="+ScopePK2+" and AssociationID_FK="+AssociationID;
				   	CurrentIHPK=0;
					System.out.println("stmt="+stmt);

		    		PR_RS = statement.executeQuery(stmt);
					while(PR_RS.next()){
						CurrentIHPK =PR_RS.getInt(1); 
						Scope2PreCleanScanDate=PR_RS.getString(2);
						Scope2PreCleanDateTime=PR_RS.getTimestamp(3);
					}		
					PR_RS.close();
					conn.close();
		    	}
		    	catch (SQLException ex){
		    	    // handle any errors
		    	    System.out.println("SQLException: " + ex.getMessage());
		    	    System.out.println("SQLState: " + ex.getSQLState());
		    	    System.out.println("VendorError: " + ex.getErrorCode());	
		    	}
			    System.out.println("AssociationID="+AssociationID);
			    System.out.println("RefNo2="+RefNo2);
			    System.out.println("LocationName"+LocationName);
			    System.out.println("Date1="+ExamDate);
			    System.out.println("Scope2InDateTime="+Scope2InDateTime);
			    System.out.println("Scope2PreCleanScanDate="+Scope2PreCleanScanDate);
			    System.out.println("CurrentPatientID="+CurrentPatientID);
			    System.out.println("PatientScanDate="+PatientScanDate);
			    System.out.println("NumScopes="+NumScopes);
			    if(!Scope2PreCleanScanDate.equalsIgnoreCase("")&&!PatientScanDate.equalsIgnoreCase("")){
					if(CurrentPatientDateTime.after(Scope2PreCleanDateTime)){ 
						Scope2PrecleanBeforePatient="Yes";
					}else {
						Scope2PrecleanBeforePatient="No";
					}
			    }else {
					Scope2PrecleanBeforePatient="No";
				}
			    System.out.println("Scope2PrecleanBeforePatient="+Scope2PrecleanBeforePatient);
				IP_A.Click_InfectionPrevention();
				IP_A.Click_EM();
				//EM_A.Clear_Filter();
				EM_A.Click_ColumnFilter();
				EM_A.Search_RefNo(RefNo2);
				EM_A.Click_Search();

				Description="Verify Procedure Room";
				Expected="Procedure Room="+LocationName;
				Result_ProcRoom=EM_V.Verify_ProcedureRoom(LocationName);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ProcRoom);

				Description="Verify Exam Date";
				Expected="Exam Date ="+ExamDateTime;
				Result_ExamDate=EM_V.Verify_ExamDate(ExamDateTime);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamDate);

				Description="Verify Exam ID";
				Expected="Exam ID ="+AssociationID;
				Result_ExamID=EM_V.Verify_ExamID(Integer.toString(AssociationID));
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamID);

				Description="Verify Patient ID";
				Expected="Patient ID ="+CurrentPatientID;
				Result_Patient=EM_V.Verify_Patient(CurrentPatientID);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Patient);

				Description="Verify Patient Scan Date/Time";
				Expected="Patient Scan Date/Time ="+PatientScanDate;
				Result_PatientScanDate=EM_V.Verify_PatientDate(PatientScanDate);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_PatientScanDate);

				Description="Verify # of Scopes";
				Expected="Verify # of Scopes ="+NumScopes;
				Result_NumScopes=EM_V.Verify_NumScpopes(Integer.toString(NumScopes));
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_NumScopes);

				Description="Verify Scope2 Scan in Time";
				Expected="Verify Scope2 Scan in Time="+Scope2InDateTimeNoSec;
				Result_Scope2InDate=EM_V.Verify_ScopeIn(Scope2InDateTimeNoSec);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope2InDate);

				Description="Verify Scope2 Model";
				Expected="Verify Scope2 Model="+ScopeModel2;
				Result_Scope2Model=EM_V.Verify_ScopeModel(ScopeModel2);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope2Model);

				Description="Verify Scope2 Serial Number";
				Expected="Verify Scope2 Serial Number="+ScopeSerialNo;
				Result_Scope2SerialNo=EM_V.Verify_ScopeSerialNo(ScopeSerialNo2);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope2SerialNo);

				Description="Verify Scope2 Pre-Clean Scan Date/Time";
				Expected="Verify Pre-Clean Scan Date/Time="+Scope2PreCleanScanDate;
				Result_Scope2PrecleanDateTime=EM_V.Verify_Preclean(Scope2PreCleanScanDate);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope2PrecleanDateTime);

				Description="Verify Scope2 Pre-Clean Before Patient";
				Expected="Verify Scope2 Pre-Clean Before Patient="+Scope2PrecleanBeforePatient;
				Result_Scope2PrecleanBeforePatient=EM_V.Verify_PrecleanBeforePatient(Scope2PrecleanBeforePatient);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope2PrecleanBeforePatient);

				Description="Verify Scope2 Ref #";
				Expected="Verify Scope2 Ref #="+RefNo2;
				Result_RefNum2=EM_V.Verify_RefNo(RefNo2);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_RefNum2);
		    }
		}
		NumScopes=0;
    	try{
    		conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();

			stmt="Select IH.ReceivedDateTime from ItemHistory IH Join Location l on IH.locationID_FK=l.locationID_PK "
					+ "where IH.ScanItemTypeID_FK in (3) and IH.CycleEventID_FK in (4) and Facilityid_fk=1 and convert(Date,receiveddatetime)= CONVERT(Date,GETDATE()) and "
					+ "associationID_FK not in (select associationID_FK from ItemHistory where ScanItemTypeID_FK =1 and CycleEventID_FK =3);";
			System.out.println("stmt="+stmt);
			PR_RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(PR_RS.next()){
				receivedDataTime=PR_RS.getTimestamp(1);
			}
			PR_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select distinct(IH.AssociationID_FK),l.Name, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate, "
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime, ItemHistoryID_PK from ItemHistory IH Join Location l on IH.locationID_FK=l.locationID_PK "
					+ "where IH.ScanItemTypeID_FK in (3) and IH.CycleEventID_FK in (4) and Facilityid_fk=1 and convert(Date,receiveddatetime)= CONVERT(Date,GETDATE()) and "
					+ "associationID_FK not in (select associationID_FK from ItemHistory where ScanItemTypeID_FK =1 and CycleEventID_FK =3);";
			System.out.println("stmt="+stmt);
    		
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				AssociationID = PR_RS.getInt(1); 
				LocationName=PR_RS.getString(2);
				ExamDate=PR_RS.getString(3);
				ExamTime=PR_RS.getString(4);
				CurrentIHPK=PR_RS.getInt(5);
			}		
			PR_RS.close();
			stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
			statement.executeUpdate(stmt1);

			stmt="Select PatientID_PK, PatientID, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime "
					+ "from ItemHistory IH join Patient on IH.ScanItemID_FK=Patient.PatientID_PK where AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=4";

			System.out.println("stmt="+stmt);
			PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				CurrentPatientPK = PR_RS.getInt(1); 
				CurrentPatientID = PR_RS.getString(2);
				PatientScanDate=PR_RS.getString(3);
				CurrentPatientDateTime=PR_RS.getTimestamp(4);
			}
	
			PR_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";

	   		PR_RS=statement.executeQuery(stmtPatEncr);
			while(PR_RS.next()){
				CurrentPatientPK = PR_RS.getInt(1); 
				//LastUpdatedDate= PR_RS.getString(2);
		   		CurrentPatientID = PR_RS.getString(3);
			}
		   	PR_RS.close();
		   	
		   	conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	ExamDateTime=PatientScanDate;	
	    System.out.println("AssociationID="+AssociationID);
	    System.out.println("LocationName"+LocationName);
	    System.out.println("Date1="+ExamDateTime);
	    System.out.println("CurrentPatientID="+CurrentPatientID);
	    System.out.println("PatientScanDate="+PatientScanDate);

		IP_A.Click_InfectionPrevention();
		IP_A.Click_EM();
		EM_A.Clear_Filter();
		EM_A.Click_ColumnFilter();
		EM_A.Search_ExamID(Integer.toString(AssociationID));
		EM_A.Click_Search();

		Description="Verify Procedure Room";
		Expected="Procedure Room="+LocationName;
		Result_ProcRoom=EM_V.Verify_ProcedureRoom(LocationName);
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ProcRoom);

		Description="Verify Exam Date";
		Expected="Exam Date ="+ExamDateTime;
		Result_ExamDate=EM_V.Verify_ExamDate(ExamDateTime);
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamDate);

		Description="Verify Exam ID";
		Expected="Exam ID ="+AssociationID;
		Result_ExamID=EM_V.Verify_ExamID(Integer.toString(AssociationID));
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamID);

		Description="Verify Patient ID";
		Expected="Patient ID ="+CurrentPatientID;
		Result_Patient=EM_V.Verify_Patient(CurrentPatientID);
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Patient);

		Description="Verify Patient Scan Date/Time";
		Expected="Patient Scan Date/Time ="+PatientScanDate;
		Result_PatientScanDate=EM_V.Verify_PatientDate(PatientScanDate);
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_PatientScanDate);

		Description="Verify # of Scopes";
		Expected="Verify # of Scopes ="+NumScopes;
		Result_NumScopes=EM_V.Verify_NumScpopes(Integer.toString(NumScopes));
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_NumScopes);

		Description="Verify No scopes associated with exam";
		Expected="No scopes are associated with this exam.";
		Result_Patient_NoScopes=EM_V.Verify_NoScopes();
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Patient_NoScopes);
    	
		//check Facility 2
		EM_A.Clear_Filter();
		EM_A.Change_Facility("Facility 2");
		for(int i=0;i<2;i++){
			NumScopes=0;
			AssociationID=0;
			RefNo="";
			ScopeName="";
			ScopeSerialNo="";
			ScopeModel="";
			ScopePK=0;
			RefNo2="";
			ScopeName2="";
			ScopeSerialNo2="";
			ScopeModel2="";
			ScopePK2=0;
			LocationName="";
			CurrentIHPK=0;
			Scope1InDateTime="";
			PatientScanDate="";
			CurrentPatientID="";
			CurrentPatientPK=0;
			CurrentPatientDateTime=new Date();
			Scope1InDateTimeStamp=new Date();
			Scope1PreCleanScanDate="";
			Scope1PreCleanDateTime=new Date();
			Scope2PreCleanScanDate="";
			Scope2PreCleanDateTime=new Date();
	    	try{
	    		conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
	    		//Statement update1 = conn.createStatement();
				Statement statement = conn.createStatement();
				
				stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK "
						+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where Loc.Facilityid_fk=2 and IH.CycleEventID_FK=3 and "
						+ "IH.LastUpdatedDateTime=(Select min(IH.LastUpdatedDateTime) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK where Loc.Facilityid_fk=2 and IH.CycleEventID_FK=3);";
				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
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
						+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where Loc.Facilityid_fk=2 and IH.CycleEventID_FK=3 and "
						+ "IH.LastUpdatedDateTime=(Select min(IH.LastUpdatedDateTime) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK where Loc.Facilityid_fk=2 and IH.CycleEventID_FK=3);";
				System.out.println("stmt="+stmt);
	    		
	    		PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					AssociationID = PR_RS.getInt(1); 
					RefNo = PR_RS.getString(2);
					LocationName=PR_RS.getString(3);
					ExamDate=PR_RS.getString(4);
					ExamTime=PR_RS.getString(5);
					CurrentIHPK=PR_RS.getInt(6);
					ScopeName=PR_RS.getString(7);
					ScopeSerialNo=PR_RS.getString(8);
					ScopePK=PR_RS.getInt(9);
					CurrentExamDate=PR_RS.getDate(10);
					CurrentExamTime=PR_RS.getTime(10);
					ScopeModel=PR_RS.getString(11);
					Scope1InDateTime=PR_RS.getString(12);
					Scope1InDateTimeNoSec=PR_RS.getString(13);
					Scope1InDateTimeStamp=PR_RS.getTimestamp(14);
				}		
				PR_RS.close();
				stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
				statement.executeUpdate(stmt1);
				
				stmt="select count(case when CycleEventID_FK=3 then CycleEventID_FK else null end) from  ItemHistory h where AssociationID_FK="+AssociationID+";";
				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					NumScopes = PR_RS.getInt(1); 
				}
				PR_RS.close();
				stmt="Select PatientID_PK, PatientID, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime "
						+ "from ItemHistory IH join Patient on IH.ScanItemID_FK=Patient.PatientID_PK where AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=4";

				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					CurrentPatientPK = PR_RS.getInt(1); 
					CurrentPatientID = PR_RS.getString(2);
					PatientScanDate=PR_RS.getString(3);
					CurrentPatientDateTime=PR_RS.getTimestamp(4);
				}
		
				PR_RS.close();
				String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			   	if (CurrentPatientID.equalsIgnoreCase("")){
			   		CurrentPatientID="";
				}
			   	else{
			   		PR_RS=statement.executeQuery(stmtPatEncr);
					while(PR_RS.next()){
						CurrentPatientPK = PR_RS.getInt(1); 
						//LastUpdatedDate= PR_RS.getString(2);
				   		CurrentPatientID = PR_RS.getString(3);
					}
				   	PR_RS.close();
			   	}
			   	stmt="select ItemHistoryID_PK, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime from ItemHistory where CycleEventID_FK=8 and ScanItemID_FK="+ScopePK+" and AssociationID_FK="+AssociationID;
			   	CurrentIHPK=0;
				System.out.println("stmt="+stmt);

	    		PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					CurrentIHPK =PR_RS.getInt(1); 
					Scope1PreCleanScanDate=PR_RS.getString(2);
					Scope1PreCleanDateTime=PR_RS.getTimestamp(3);
				}		
				PR_RS.close();
				conn.close();
	    	}
	    	catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
	    	if(!PatientScanDate.equalsIgnoreCase("")){
		    	if(CurrentPatientDateTime.after(Scope1InDateTimeStamp)){
					ExamDateTime=Scope1InDateTimeNoSec;
				}else {
					ExamDateTime=PatientScanDate;
				}
	    	}else {
	    		ExamDateTime=Scope1InDateTimeNoSec;
	    	}
  	
	    	System.out.println("AssociationID="+AssociationID);
		    System.out.println("RefNo1="+RefNo);
		    System.out.println("LocationName"+LocationName);
		    System.out.println("Date1="+ExamDate);
		    System.out.println("Scope1InDateTime="+Scope1InDateTime);
		    System.out.println("Scope1PreCleanScanDate="+Scope1PreCleanScanDate);
		    System.out.println("CurrentPatientID="+CurrentPatientID);
		    System.out.println("PatientScanDate="+PatientScanDate);
		    System.out.println("NumScopes="+NumScopes);
		    
		    if(!Scope1PreCleanScanDate.equalsIgnoreCase("")&&!PatientScanDate.equalsIgnoreCase("")){
				if(CurrentPatientDateTime.after(Scope1PreCleanDateTime)){ 
					Scope1PrecleanBeforePatient="Yes";
				}else {
					Scope1PrecleanBeforePatient="No";
				}
		    }else {
				Scope1PrecleanBeforePatient="No";
			}
		    System.out.println("Scope1PrecleanBeforePatient="+Scope1PrecleanBeforePatient);

			IP_A.Click_InfectionPrevention();
			IP_A.Click_EM();
			//EM_A.Clear_Filter();
			EM_A.Click_ColumnFilter();
			EM_A.Search_RefNo(RefNo);
			EM_A.Click_Search();

			Description="Verify Facility 2 Procedure Room";
			Expected="Procedure Room="+LocationName;
			Result_ProcRoom=EM_V.Verify_ProcedureRoom(LocationName);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ProcRoom);

			Description="Verify Facility 2 Exam Date";
			Expected="Exam Date ="+ExamDateTime;
			Result_ExamDate=EM_V.Verify_ExamDate(ExamDateTime);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamDate);

			Description="Verify Facility 2 Exam ID";
			Expected="Exam ID ="+AssociationID;
			Result_ExamID=EM_V.Verify_ExamID(Integer.toString(AssociationID));
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamID);

			Description="Verify Facility 2 Patient ID";
			Expected="Patient ID ="+CurrentPatientID;
			Result_Patient=EM_V.Verify_Patient(CurrentPatientID);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Patient);

			Description="Verify Facility 2 Patient Scan Date/Time";
			Expected="Patient Scan Date/Time ="+PatientScanDate;
			Result_PatientScanDate=EM_V.Verify_PatientDate(PatientScanDate);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_PatientScanDate);

			Description="Verify Facility 2 # of Scopes";
			Expected="Verify Facility 2 # of Scopes ="+NumScopes;
			Result_NumScopes=EM_V.Verify_NumScpopes(Integer.toString(NumScopes));
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_NumScopes);

			Description="Verify Facility 2 Scope Scan in Time";
			Expected="Verify Facility 2 Scope Scan in Time="+Scope1InDateTimeNoSec;
			Result_Scope1InDate=EM_V.Verify_ScopeIn(Scope1InDateTimeNoSec);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1InDate);

			Description="Verify Facility 2 Scope Model";
			Expected="Verify Facility 2 Scope Model="+ScopeModel;
			Result_Scope1Model=EM_V.Verify_ScopeModel(ScopeModel);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1Model);

			Description="Verify Facility 2 Scope Serial Number";
			Expected="Verify Facility 2 Scope Serial Number="+ScopeSerialNo;
			Result_Scope1SerialNo=EM_V.Verify_ScopeSerialNo(ScopeSerialNo);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1SerialNo);

			Description="Verify Facility 2 Pre-Clean Scan Date/Time";
			Expected="Verify Facility 2 Pre-Clean Scan Date/Time="+Scope1PreCleanScanDate;
			Result_Scope1PrecleanDateTime=EM_V.Verify_Preclean(Scope1PreCleanScanDate);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1PrecleanDateTime);

			Description="Verify Facility 2 Pre-Clean Before Patient";
			Expected="Verify Facility 2 Pre-Clean Before Patient="+Scope1PrecleanBeforePatient;
			Result_Scope1PrecleanBeforePatient=EM_V.Verify_PrecleanBeforePatient(Scope1PrecleanBeforePatient);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1PrecleanBeforePatient);

			Description="Verify Facility 2 Ref #";
			Expected="Verify Facility 2 Ref #="+RefNo;
			Result_RefNum1=EM_V.Verify_RefNo(RefNo);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_RefNum1);

		
		}

		//check Facility 3
		EM_A.Clear_Filter();
		EM_A.Change_Facility("Facility 3");
		for(int i=0;i<2;i++){
			NumScopes=0;
			AssociationID=0;
			RefNo="";
			ScopeName="";
			ScopeSerialNo="";
			ScopeModel="";
			ScopePK=0;
			RefNo2="";
			ScopeName2="";
			ScopeSerialNo2="";
			ScopeModel2="";
			ScopePK2=0;
			LocationName="";
			CurrentIHPK=0;
			Scope1InDateTime="";
			PatientScanDate="";
			CurrentPatientID="";
			CurrentPatientPK=0;
			CurrentPatientDateTime=new Date();
			Scope1InDateTimeStamp=new Date();
			Scope1PreCleanScanDate="";
			Scope1PreCleanDateTime=new Date();
			Scope2PreCleanScanDate="";
			Scope2PreCleanDateTime=new Date();
	    	try{
	    		conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
	    		//Statement update1 = conn.createStatement();
				Statement statement = conn.createStatement();
				
				stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK "
						+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where Loc.Facilityid_fk=3 and IH.CycleEventID_FK=3 and "
						+ "IH.LastUpdatedDateTime=(Select min(IH.LastUpdatedDateTime) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK where Loc.Facilityid_fk=3 and IH.CycleEventID_FK=3);";
				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
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
						+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK where Loc.Facilityid_fk=3 and IH.CycleEventID_FK=3 and "
						+ "IH.LastUpdatedDateTime=(Select min(IH.LastUpdatedDateTime) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK where Loc.Facilityid_fk=3 and IH.CycleEventID_FK=3);";
				System.out.println("stmt="+stmt);
	    		
	    		PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					AssociationID = PR_RS.getInt(1); 
					RefNo = PR_RS.getString(2);
					LocationName=PR_RS.getString(3);
					ExamDate=PR_RS.getString(4);
					ExamTime=PR_RS.getString(5);
					CurrentIHPK=PR_RS.getInt(6);
					ScopeName=PR_RS.getString(7);
					ScopeSerialNo=PR_RS.getString(8);
					ScopePK=PR_RS.getInt(9);
					CurrentExamDate=PR_RS.getDate(10);
					CurrentExamTime=PR_RS.getTime(10);
					ScopeModel=PR_RS.getString(11);
					Scope1InDateTime=PR_RS.getString(12);
					Scope1InDateTimeNoSec=PR_RS.getString(13);
					Scope1InDateTimeStamp=PR_RS.getTimestamp(14);
				}		
				PR_RS.close();
				stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
				statement.executeUpdate(stmt1);
				
				stmt="select count(case when CycleEventID_FK=3 then CycleEventID_FK else null end) from  ItemHistory h where AssociationID_FK="+AssociationID+";";
				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					NumScopes = PR_RS.getInt(1); 
				}
				PR_RS.close();
				stmt="Select PatientID_PK, PatientID, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime "
						+ "from ItemHistory IH join Patient on IH.ScanItemID_FK=Patient.PatientID_PK where AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=4";

				System.out.println("stmt="+stmt);
				PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					CurrentPatientPK = PR_RS.getInt(1); 
					CurrentPatientID = PR_RS.getString(2);
					PatientScanDate=PR_RS.getString(3);
					CurrentPatientDateTime=PR_RS.getTimestamp(4);
				}
		
				PR_RS.close();
				String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			   	if (CurrentPatientID.equalsIgnoreCase("")){
			   		CurrentPatientID="";
				}
			   	else{
			   		PR_RS=statement.executeQuery(stmtPatEncr);
					while(PR_RS.next()){
						CurrentPatientPK = PR_RS.getInt(1); 
						//LastUpdatedDate= PR_RS.getString(2);
				   		CurrentPatientID = PR_RS.getString(3);
					}
				   	PR_RS.close();
			   	}
			   	stmt="select ItemHistoryID_PK, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),ReceivedDateTime from ItemHistory where CycleEventID_FK=8 and ScanItemID_FK="+ScopePK+" and AssociationID_FK="+AssociationID;
			   	CurrentIHPK=0;
				System.out.println("stmt="+stmt);

	    		PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					CurrentIHPK =PR_RS.getInt(1); 
					Scope1PreCleanScanDate=PR_RS.getString(2);
					Scope1PreCleanDateTime=PR_RS.getTimestamp(3);
				}		
				PR_RS.close();
				conn.close();
	    	}
	    	catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
	    	if(!PatientScanDate.equalsIgnoreCase("")){
		    	if(CurrentPatientDateTime.after(Scope1InDateTimeStamp)){
					ExamDateTime=Scope1InDateTimeNoSec;
				}else {
					ExamDateTime=PatientScanDate;
				}
	    	}else {
	    		ExamDateTime=Scope1InDateTimeNoSec;
	    	}

		    System.out.println("AssociationID="+AssociationID);
		    System.out.println("RefNo1="+RefNo);
		    System.out.println("LocationName"+LocationName);
		    System.out.println("Date1="+ExamDate);
		    System.out.println("Scope1InDateTime="+Scope1InDateTime);
		    System.out.println("Scope1PreCleanScanDate="+Scope1PreCleanScanDate);
		    System.out.println("CurrentPatientID="+CurrentPatientID);
		    System.out.println("PatientScanDate="+PatientScanDate);
		    System.out.println("NumScopes="+NumScopes);
		    
		    if(!Scope1PreCleanScanDate.equalsIgnoreCase("")&&!PatientScanDate.equalsIgnoreCase("")){
				if(CurrentPatientDateTime.after(Scope1PreCleanDateTime)){ 
					Scope1PrecleanBeforePatient="Yes";
				}else {
					Scope1PrecleanBeforePatient="No";
				}
		    }else {
				Scope1PrecleanBeforePatient="No";
			}
		    System.out.println("Scope1PrecleanBeforePatient="+Scope1PrecleanBeforePatient);

			IP_A.Click_InfectionPrevention();
			IP_A.Click_EM();
			//EM_A.Clear_Filter();
			EM_A.Click_ColumnFilter();
			EM_A.Search_RefNo(RefNo);
			EM_A.Click_Search();

			Description="Verify Facility 3 Procedure Room";
			Expected="Procedure Room="+LocationName;
			Result_ProcRoom=EM_V.Verify_ProcedureRoom(LocationName);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ProcRoom);

			Description="Verify Facility 3 Exam Date";
			Expected="Exam Date ="+ExamDateTime;
			Result_ExamDate=EM_V.Verify_ExamDate(ExamDateTime);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamDate);

			Description="Verify Facility 3 Exam ID";
			Expected="Exam ID ="+AssociationID;
			Result_ExamID=EM_V.Verify_ExamID(Integer.toString(AssociationID));
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_ExamID);

			Description="Verify Facility 3 Patient ID";
			Expected="Patient ID ="+CurrentPatientID;
			Result_Patient=EM_V.Verify_Patient(CurrentPatientID);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Patient);

			Description="Verify Facility 3 Patient Scan Date/Time";
			Expected="Patient Scan Date/Time ="+PatientScanDate;
			Result_PatientScanDate=EM_V.Verify_PatientDate(PatientScanDate);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_PatientScanDate);

			Description="Verify Facility 3 # of Scopes";
			Expected="Verify Facility 3 # of Scopes ="+NumScopes;
			Result_NumScopes=EM_V.Verify_NumScpopes(Integer.toString(NumScopes));
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_NumScopes);

			Description="Verify Facility 3 Scope Scan in Time";
			Expected="Verify Facility 3 Scope Scan in Time="+Scope1InDateTimeNoSec;
			Result_Scope1InDate=EM_V.Verify_ScopeIn(Scope1InDateTimeNoSec);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1InDate);

			Description="Verify Facility 3 Scope Model";
			Expected="Verify Facility 3 Scope Model="+ScopeModel;
			Result_Scope1Model=EM_V.Verify_ScopeModel(ScopeModel);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1Model);

			Description="Verify Facility 3 Scope Serial Number";
			Expected="Verify Facility 3 Scope Serial Number="+ScopeSerialNo;
			Result_Scope1SerialNo=EM_V.Verify_ScopeSerialNo(ScopeSerialNo);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1SerialNo);

			Description="Verify Facility 3 Pre-Clean Scan Date/Time";
			Expected="Verify Facility 3 Pre-Clean Scan Date/Time="+Scope1PreCleanScanDate;
			Result_Scope1PrecleanDateTime=EM_V.Verify_Preclean(Scope1PreCleanScanDate);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1PrecleanDateTime);

			Description="Verify Facility 3 Pre-Clean Before Patient";
			Expected="Verify Facility 3 Pre-Clean Before Patient="+Scope1PrecleanBeforePatient;
			Result_Scope1PrecleanBeforePatient=EM_V.Verify_PrecleanBeforePatient(Scope1PrecleanBeforePatient);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_Scope1PrecleanBeforePatient);

			Description="Verify Facility 3 Ref #";
			Expected="Verify Facility 3 Ref #="+RefNo;
			Result_RefNum1=EM_V.Verify_RefNo(RefNo);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_RefNum1);		
		}
		
		IHV.Close_Exec_Log(UAS.XMLFileName, "Test Completed", UAS.TestCaseNumber);
		if (UAS.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	@AfterTest
	public void PostTTest() throws IOException{
		//System.out.println("The Test Case Run was:  "+TCResult);
		//IHV.Close_Exec_Log(UAS.FileName, TCResult);
		UAS.IsHappyPath=false;
		LP_A.CloseDriver();
	}
}
