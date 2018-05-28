package AdminUserStories.Recon;

import org.graphwalker.core.condition.StopConditionException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminFacilityPage.*;
import TestFrameWork.UnifiaAdminLocationPage.*;
import TestFrameWork.UnifiaAdminExamTypePage.*;
import TestFrameWork.UnifiaAdminScopeTypePage.*;
import TestFrameWork.UnifiaAdminScannerPage.*;
import TestFrameWork.UnifiaAdminScopePage.*;
import TestFrameWork.UnifiaAdminAccessPointPage.*;
import TestFrameWork.UnifiaAdminUserPage.*;
import TestFrameWork.UnifiaAdminStaffPage.*;
import TestFrameWork.UnifiaAdminScopeSafetyPage.*; //RK - 28Jan2016 
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Verification; //RK - 26Feb2016
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

/**
 * @author Nicole MckInley
 * The purpose of this test is to verify the complex acceptance criteria that can not be easily tested by our 
 * model based testing approach. This scripted test will cover items from the following product backlog items:
 * 1. one of the room data is blank , we would like to add 
    Example Data for Procedure room and Reprocessing Room are there and the soiled area is blank and we want to     add the details for soiled room
(This we will do for each of the rooms in complex )

2. Date validation for not able to save with a future date. 

3. Culture room case would be added in complex as we just have culture results field and no staff

4. verify comment only changes in audit log

5. Bioburden - complex scenarios - skip the pass/ fail test result but perform an optional result (scanned or key entry) and a staff scan. 
modify these items and verify the data can be saved. then go back into the same record and add a pass/fail save. go back in again and remove the pass/fail and verify all bioburden 
fields are removed and the audit log tracks all the changes done. 
 */
	public class ComplexReconTest {
		
    TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LPA;
    TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
    TestFrameWork.UnifiaAdminUserPage.User_Verification UV;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.Emulator.GetIHValues IHV;
	TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	private ITConsole.ITConScanSimActions IT_A;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Verification MRC_V;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralMethods GM;

	public String Description;  //the description written to the test step log or printed to the console
	public String Expected; //The expected result written to the test step log
	public String FileName="";
	private int Scenario=1;

	private TestDataFunc TDF;
	public int KE=1;
	public int Bioburden=1;
	public int Culture=0;
	private String presWorkDir=System.getProperty("user.dir");
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";

	private String fileDestFolder="\\XMLFolder";
	private String ReconScanDataXMLFile="ComplexReconData";
	public String ScopeName;
	public String ScopeSerialNo;
	public String ScopeModel;
	public int ScopePK;
	public int CurrentIHPK; // used to get the current records scan history primary key
	public String Comment; //the text entered into the comment box 
	public String RefNo; //the reference number of the record being modified.
	public int AssociationID; //association ID of the record being modified. 
	public int LocationID; //the location's primary key
	public String LocationName; //location name (i.e. Sink 1) 
	public int LocationID_PK;
	public String ModLocationName;
	public String[] InactiveLocationList=new String[10];
	public int NumInactiveLocation=0;
	public String ResultInactiveLocation;
	public String InactiveItem;
	public String ResultInactiveStaff;
	public String[] InactiveStaffList=new String[10];
	public int NumInactiveStaff=0;
	public String ResultInactivePhy;
	public String[] InactivePhyList=new String[10];
	public int NumInactivePhy=0;
	public String ResultInactiveReason;
	public String[] InactiveReasonList=new String[10];
	public int NumInactiveReason=0;
	public String ResultInactiveBio;
	public String[] InactiveBioList=new String[10];
	public int NumInactiveBio=0;

	
	public String SoiledDate;
	public String SoiledTime;
	public Date CurrentSoiledDate=new Date(),CurrentSoiledTime=new Date();
	public String ExamDate; //the date of the record being modified.
	public String ExamTime; //the time of the record being modified.
	public String ExamDateTime;
	public String ExamDateTimeNoSec;
	public Date CurrentExamDate=new Date(),ModExamDate=new Date(),CurrentExamTime=new Date(),ModExamTime=new Date();
	public String ModExamDateTime="";
	public String ModProcStartDateTime, ModProcEndDateTime;
	public int ModInStaffPK; //the primary key of the updated staff for scanning the scope into the procedure room. 
	public int ModPreCleanStaffPK; //the primary key of the updated staff for scanning preclean complete  
	public int ModPhyPK; //the primary key of the updated physician.
	public int ModPhyPK2;
	public String ModInStaffID; //the Staff ID of the updated staff for scanning the scope into the procedure room. 
	public String ModPreCleanStaff; //the Staff ID of the updated staff for scanning preclean complete
	public String ModPhyID; //the Staff ID of the updated physician
	public String ModPhyID2;
	public int ModPatientPK; //the primary key of the updated patient 
	public String ModPatientID; //the Patient ID of the updated patient
	public String ModPreCleanStatus;
	public String ReconDateTime;
	public String Result_Audit_RefNo;
	public String Result_Audit_ReconDate;
	public String Result_Audit_ScopeName;
	public String Result_Audit_OriginalScanDate;
	public String Result_Audit_Comment;
	public String Result_Audit_UserName;
	public String Result_Audit_Location;
	public String Result_Audit_WhatChanged;
	public String Result_Audit_Previous;
	public String Result_Audit_Modified;
	public String Result_AuditLog;
	public String UserName="qvtest01";

	public String SinkStaffID;
	public int SinkStaffPK;
	public String PreCleanDate;
	public String PreCleanTime;
	public Date CurrentPreCleanDate=new Date(),CurrentPreCleanTime=new Date();
	public String ModLT;
	public String ModSoiledDateTime;
	public String ModMCStartDateTime;
	public String ModMCEndDateTime;
	
	public String MCEndDate,MCEndTime;
	public Date CurrentMCEndDate=new Date(),CurrentMCEndTime=new Date();
	public int ReproInStaffPK,ReproOutStaffPK;
	public String ReproInStaffID,ReproOutStaffID;
	public String ModReason;
	public String ModReproInDateTime;
	public String ModReproOutDateTime;

	public String BioStaffID;
	public int BioStaffPK;
	public String ModBioResult;
	public String ModBioScanResult;
	
	public String CultureDate,CultureTime,CultureDateTime;
	public String ModCultureResult;
	
	public String BioDate,BioTime;
	
	public int CurrentStaffPK,CurrentBioScanResultPK;
	public String CurrentStaffID,StaffScanDate,CurrentBioScanResult,BioScanResultScanDate;
	
	public String ReproDate,ReproTime;
	
	public int CurrentPhyPK=0,CurrentPatientPK=0,CurrentPreCleanStaffPK=0,RelatedItem=0;
	public String CurrentPhyID="",PhyScanDate="",CurrentPatientID="",PatientScanDate="",CurrentPreCleanStaff="";
	public String CurrentProcStartDateTime="",CurrentProcStartDateTimeNoSec="",CurrentProcEndDateTime="",CurrentProcEndDateTimeNoSec="",PrecleanStaffScanDate="";
	public Date CurrentProcStartDate=new Date(),CurrentProcStartTime=new Date();
	public Date ModProcStartDate=new Date(),ModProcStartTime=new Date();
	public Date CurrentProcEndDate=new Date(),CurrentProcEndTime=new Date();
	public Date ModProcEnd=new Date();
	
	public SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
	public static SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	public static Calendar cal2 = Calendar.getInstance();
	public int NumChanges=0;
	public String[] WhatChanged=new String[10];
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	public long cal = Calendar.getInstance().getTimeInMillis(); 

	public ResultSet RS;
	public String stmt;
    public String GridID;
    Connection conn= null;
	public int UTCTimeDiffInHours=0;
	private String popUpText="You have not saved your changes. Are you sure you want to leave?";
	private String Result_ChevronColor;
	private boolean incompleteDetailsFlag=false;
	private static String Result_Audit_RowsCount;
	
   @Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws Exception {
    		//select the Driver type Grid or local

		
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    	}		

		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
		UAS.resultFlag="Pass";
		Unifia_Admin_Selenium.isRecon=true;
		//DB  Updates
		GF.SyncRemoteMachineTime(Unifia_Admin_Selenium.KE_Env, Unifia_Admin_Selenium.KEMachine_Username, Unifia_Admin_Selenium.KEMachine_pswd, URL);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);

		boolean isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, ReconScanDataXMLFile+".xml",fileSource,fileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+ReconScanDataXMLFile+".xml"+" is copied to "+fileDestination);
		}else{
			System.out.println("#Failed!#- "+ReconScanDataXMLFile+".xml"+" is not copied to "+fileDestination);
		}
		//Copying Runbatch.bat file to server machine
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.batchFile,fileSource,batchFileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+UAS.batchFile+" is copied to "+batchFileDestination);
		}else{
			System.out.println("#Failed!#- "+UAS.batchFile+" is not copied to "+batchFileDestination);
		}
		//Update the environment and xml file in  Runbatch.bat file
		IT_A.modifyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.scanSimPath, UAS.batchFile,ReconScanDataXMLFile+".xml", xmlPath);
		//execute ScanSimUI
		IT_A.executeScanSim(UAS.Env, UAS.userName, UAS.IISPass,UAS.scanSimPathTool,UAS.ITConsoleExecTime,0);

		String stmt1 = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
    	String stmt2="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
  		try{
    		Connection conn = DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
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

		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		FileName="Modify_Workflow_Details_Complex_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		Scenario=0;
	   	UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();

		//Verify the user is able to modify common fields (Physician, Patient, PR Start and PR End) in Procedure room that has 2 scopes for one scope (scope11) and both scopes (scoope11 and 12) are updated. 
		NumChanges=0;
		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		RefNo="11-1";
		ScopeSerialNo="9988776";
		ScopeModel="GIF-HQ190";
		ScopeName="Scope11";
		ModLocationName="Procedure Room 1";
		Description="Update Scope 11 In staff, Phys, Patient, Proc Start and Proc End and verify all but the In Staff update occured for Scope 12";

    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=12 and SC.CycleID=1 and "
					+ "IH.LocationID_FK=21 and IH.CycleEventID_FK=8 and IH.ScanItemID_FK=11 and IH.ScanItemTypeID_FK=1;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime,IH.ReceivedDateTime, IH.ItemHistoryID_PK from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=12 and SC.CycleID=1 and IH.LocationID_FK=21 and IH.CycleEventID_FK=8 and IH.ScanItemID_FK=11 and IH.ScanItemTypeID_FK=1;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				PreCleanDate=RS.getString(2);
				PreCleanTime=RS.getString(3);
				CurrentPreCleanDate=RS.getDate(4);
				CurrentPreCleanTime=RS.getTime(4);
				CurrentIHPK=RS.getInt(5);
			}
			stmt="select RelatedItemHistoryID_FK from RelatedItem RI  where ItemHistoryID_FK="+CurrentIHPK;
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				RelatedItem = RS.getInt(1); 
			}		
			RS.close();
			
			stmt="select IH.ScanItemID_FK, ST.StaffID, convert(varchar, format(cast(DATEADD(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where ItemHistoryID_PK="+RelatedItem;
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				CurrentPreCleanStaffPK=RS.getInt(1); 
				CurrentPreCleanStaff=RS.getString(2);
				PrecleanStaffScanDate=RS.getString(3);
			}		
			RS.close();

			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentPreCleanStaffPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentPreCleanStaffPK+")";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				ModPreCleanStaffPK= RS.getInt(1); 
				ModPreCleanStaff = RS.getString(2);

			}		
			RS.close();
			stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModPreCleanStaffPK;
			statement.executeUpdate(stmt1);

			stmt="select IH.ScanItemID_FK, ST.StaffID,convert(varchar, format(cast(DATEADD(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where AssociationID_FK="+AssociationID+" and CycleEventID_FK=5 and ReceivedDateTime=(select MAX(ReceivedDateTime) from ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=5)";
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				CurrentPhyPK=RS.getInt(1); 
				CurrentPhyID=RS.getString(2);
				PhyScanDate=RS.getString(3);
				
			}		
			RS.close();
			
			stmt="select ReceivedDateTime, convert(varchar, format(cast(DATEADD(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(DATEADD(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
					+ "where AssociationID_FK="+AssociationID+" and CycleEventID_FK=6";
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				CurrentProcStartDate=RS.getDate(1);
				CurrentProcStartTime=RS.getTime(1);
				CurrentProcStartDateTime=RS.getString(2);
				CurrentProcStartDateTimeNoSec=RS.getString(3);
			}		
			RS.close();

			stmt="select ReceivedDateTime, convert(varchar, format(cast(DATEADD(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(DATEADD(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
					+ "where AssociationID_FK="+AssociationID+" and CycleEventID_FK=7";

			RS = statement.executeQuery(stmt);
			while(RS.next()){
				CurrentProcEndDate=RS.getDate(1);
				CurrentProcEndTime=RS.getTime(1);
				CurrentProcEndDateTime=RS.getString(2);
				CurrentProcEndDateTimeNoSec=RS.getString(3);
			}		
			RS.close();
			stmt="Select PatientID_PK, PatientID, convert(varchar, format(cast(DATEADD(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Patient on IH.ScanItemID_FK=Patient.PatientID_PK where AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=4";

			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				CurrentPatientPK = RS.getInt(1); 
				CurrentPatientID = RS.getString(2);
				PatientScanDate=RS.getString(3);

			}
	
			RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
		   	if (CurrentPatientID.equalsIgnoreCase("")){
		   		CurrentPatientID="";
			}
		   	else{
		   		RS=statement.executeQuery(stmtPatEncr);
				while(RS.next()){
					CurrentPatientPK = RS.getInt(1); 
					//LastUpdatedDate= RS.getString(2);
			   		CurrentPatientID = RS.getString(3);
				}
			   	RS.close();
		   	}
			
			stmt="select PatientID_PK, PatientID from Patient where	PatientID_PK !="+CurrentPatientPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Patient where PatientID_PK !="+CurrentPatientPK+")";
			System.out.println("stmt="+stmt);

		RS= statement.executeQuery(stmt);
	 	while(RS.next()){
	 		ModPatientPK=RS.getInt(1);
	 		ModPatientID=RS.getString(2);
	 	}
		RS.close();
		stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ ModPatientPK+"'";
   		RS=statement.executeQuery(stmtPatEncr);
   		while(RS.next()){
   			ModPatientPK = RS.getInt(1); 
			//LastUpdatedDate= RS.getString(2);
   			ModPatientID = RS.getString(3);
		}
   		RS.close();		

		Statement update = conn.createStatement();
		stmt1="update Patient set LastUpdatedDateTime=GETUTCDATE() where PatientID_PK="+ModPatientPK;
		update.executeUpdate(stmt1);

			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=1 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=1)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				ModPhyPK= RS.getInt(1); 
				ModPhyID = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModPhyPK;
			statement.executeUpdate(stmt);
			
			
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=1 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=1)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				ModPhyPK2= RS.getInt(1); 
				ModPhyID2 = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModPhyPK2;
			statement.executeUpdate(stmt);
    		conn.close();
    		ModPhyID=ModPhyID2+", "+ModPhyID;
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
			
			
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(PreCleanDate,PreCleanDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);

		WF_A.UpdatePatient(ModPatientID);
    	WhatChanged[NumChanges]="Patient ID";
    	NumChanges++;			
		Expected="verify Patient is set to "+ModPatientID;
		String Result_Patient=WF_V.Verify_Patient(ModPatientID);
		Description="change the Patient ID to "+ModPatientID;		
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Patient);

		WF_A.UpdatePhysician(ModPhyID);
    	WhatChanged[NumChanges]="Physician ID";
    	NumChanges++;			

		Description="change the Physician ID to "+ModPhyID;		
		Expected="verify Physician is set to "+ModPhyID;
		String Result_Phy=WF_V.Verify_Physician(ModPhyID);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Phy);
	    
		cal2.setTime(CurrentProcStartTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... 
		cal2.add(Calendar.MINUTE, -1); //change the time by subtracting one minute.
		ModProcStartDateTime=df2.format(CurrentProcStartDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateProcStart(ModProcStartDateTime);
    	WhatChanged[NumChanges]="Procedure Start";
    	NumChanges++;			    	
    	Description="change the Procedure Start to "+ModProcStartDateTime;				
		Expected="verify Procedure Start is set to "+ModProcStartDateTime;
		String Result_ProcStart=WF_V.Verify_ProcStart(ModProcStartDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ProcStart);

    	
		cal2.setTime(CurrentProcEndTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... 
		cal2.add(Calendar.MINUTE, +1); //change the time by adding one minute.
		ModProcEndDateTime=df2.format(CurrentProcEndDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateProcEnd(ModProcEndDateTime);
    	WhatChanged[NumChanges]="Procedure End";
    	NumChanges++;			
		Description="change the Procedure End to "+ModProcEndDateTime;				
		Expected="verify Procedure End is set to "+ModProcEndDateTime;
		String Result_ProcEnd=WF_V.Verify_ProcEnd(ModProcEndDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ProcEnd);

		WF_A.UpdatePreCleanStaff(ModPreCleanStaff);
    	WhatChanged[NumChanges]="Pre-Clean Staff ID";
    	NumChanges++;			
		Description="change the Preclean staff to "+ModPreCleanStaff;						
		Expected="verify Preclean staff is set to "+ModPreCleanStaff;
		String Result_PrecleanStaff=WF_V.Verify_PreCleanStaff(ModPreCleanStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_PrecleanStaff);
		if(CurrentPreCleanStaff.equalsIgnoreCase("")){
			CurrentPreCleanStaff="-";
		}

		
		Comment="Change scope11 and verify Scope12 is changed";
		WF_A.EnterComment(Comment);

		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		String expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color";
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
		Description="Verify the audit log for Scope11";
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		Integer rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		if (rowComp==0){				
			Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description + " Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);	
		if (rowComp==0||NumChanges<rowComp){ 	
			for(int i=0;i<NumChanges;i++){
				switch (WhatChanged[i]) {
				case "Patient ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PatientScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPatientID);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPatientID);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	
				case "Physician ID":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
						System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PhyScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						
						if (Result_Audit_OriginalScanDate.contains("#Failed!#")){
							Result_Audit_OriginalScanDate=Result_Audit_OriginalScanDate+". Bug 11950 opened - Audit log entry when changing the physician gives the Scan Date/Time field is set to the exam date/time instead of the orignal physician scan/date time";
						}
						
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPhyID);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPhyID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
	
					break;
				case "Procedure Start":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CurrentProcStartDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentProcStartDateTimeNoSec);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModProcStartDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Procedure End":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CurrentProcEndDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentProcEndDateTimeNoSec);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModProcEndDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Pre-Clean Staff ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
					if(PrecleanStaffScanDate.equalsIgnoreCase("")){
						PrecleanStaffScanDate=ReconDateTime;
					}
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PrecleanStaffScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPreCleanStaff);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPreCleanStaff);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
					
				default:
	
					break;
	
				}
				Expected="v_AuditLog - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}
    	
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		//Verify the audit log for scope12 and then verify the SRM workflow details for scope 12..
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);

		NumChanges--;
		ScopeName="Scope12";
		ScopeSerialNo="2808645";
		RefNo="12-1";
		Description="Verify the audit log for Scope12";
		AL_A.ClearAuditLogSrchCritera();
		rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		if (rowComp==0){				
			Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description+ ". Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);	
		if (rowComp==0||NumChanges<rowComp){ 	
		
			for(int i=0;i<NumChanges;i++){
				switch (WhatChanged[i]) {
				case "Patient ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PatientScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPatientID);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPatientID);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	
				case "Physician ID":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
						System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PhyScanDate);
						if (Result_Audit_OriginalScanDate.contains("#Failed!#")){
							Result_Audit_OriginalScanDate=Result_Audit_OriginalScanDate+". Bug 11950 opened - Audit log entry when changing the physician gives the Scan Date/Time field is set to the exam date/time instead of the orignal physician scan/date time";
						}
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPhyID);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPhyID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
	
					break;
				case "Procedure Start":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CurrentProcStartDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentProcStartDateTimeNoSec);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModProcStartDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Procedure End":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CurrentProcEndDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentProcEndDateTimeNoSec);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModProcEndDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Pre-Clean Staff ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PrecleanStaffScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPreCleanStaff);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPreCleanStaff);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
					
				default:
	
					break;
	
				}
				Expected="v_AuditLog - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(PreCleanDate,PreCleanDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);
	    
	    Expected="verify Patient is set to "+ModPatientID;
		Result_Patient=WF_V.Verify_Patient(ModPatientID);
		Description="change the Patient ID to "+ModPatientID;		
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Patient);

		Description="change the Physician ID to "+ModPhyID;		
		Expected="verify Physician is set to "+ModPhyID;
		Result_Phy=WF_V.Verify_Physician(ModPhyID);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Phy);

    	Description="change the Procedure Start to "+ModProcStartDateTime;				
		Expected="verify Procedure Start is set to "+ModProcStartDateTime;
		Result_ProcStart=WF_V.Verify_ProcStart(ModProcStartDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ProcStart);

		Description="change the Procedure End to "+ModProcEndDateTime;				
		Expected="verify Procedure End is set to "+ModProcEndDateTime;
		Result_ProcEnd=WF_V.Verify_ProcEnd(ModProcEndDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ProcEnd);

		//Commented this as change in pre-clean staff for scope11 will not have any effect for scope12 because it has a different pre-clean staff
		
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		WF_A.Cancel("No");
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    

		//Verify the user is able to add Procedure Room activity to an existing workflow. This will be for scope1 cycle 2.
		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		for(int i=0;i<10;i++){
			InactiveLocationList[i]="zz";
		}
		NumInactiveLocation=0;
		for(int i=0;i<10;i++){
			InactiveStaffList[i]="zz";
		}
		NumInactiveStaff=0;
		for(int i=0;i<10;i++){
			InactivePhyList[i]="zz";
		}
		NumInactivePhy=0;
		for(int i=0;i<10;i++){
			InactiveReasonList[i]="zz";
		}
		NumInactiveReason=0;
		for(int i=0;i<10;i++){
			InactiveBioList[i]="zz";
		}
		NumInactiveBio=0;

		Comment="";
		RefNo="1-2";
		ScopeSerialNo="1122334";
		ScopeModel="GIF-H190";
		ScopeName="Scope1";
		ModLocationName="Procedure Room 1";
		Description="Add Procedure Room activity to an existing workflow";
    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=1 "
					+ "and SC.CycleID=2 and IH.LocationID_FK=41 and IH.ScanItemID_FK=1 and IH.ScanItemTypeID_FK=1;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime,IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=1 and SC.CycleID=2 and "
					+ "IH.LocationID_FK=41 and IH.ScanItemID_FK=1 and IH.ScanItemTypeID_FK=1;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				SoiledDate=RS.getString(2);
				SoiledTime=RS.getString(3);
				CurrentSoiledDate=RS.getDate(4);
				CurrentSoiledTime=RS.getTime(4);
			}			
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				ModInStaffPK= RS.getInt(1); 
				ModInStaffID = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModInStaffPK;
			statement.executeUpdate(stmt);
			
			stmt="select PatientID_PK, PatientID from Patient where	LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Patient)";
			System.out.println("stmt="+stmt);
			RS= statement.executeQuery(stmt);
		 	while(RS.next()){
		 		ModPatientPK=RS.getInt(1);
		 		ModPatientID=RS.getString(2);
		 	}
			RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ ModPatientPK+"'";
	   		RS=statement.executeQuery(stmtPatEncr);
	   		while(RS.next()){
	   			ModPatientPK = RS.getInt(1); 
				//LastUpdatedDate= RS.getString(2);
	   			ModPatientID = RS.getString(3);
			}
	   		RS.close();		

			Statement update = conn.createStatement();
			stmt="update Patient set LastUpdatedDateTime=GETUTCDATE() where PatientID_PK="+ModPatientPK;
			update.executeUpdate(stmt);

			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=1 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=1)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				ModPhyPK= RS.getInt(1); 
				ModPhyID = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModPhyPK;
			statement.executeUpdate(stmt);
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				ModPreCleanStaffPK= RS.getInt(1); 
				ModPreCleanStaff = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModPreCleanStaffPK;
			statement.executeUpdate(stmt);
			
			//get inactive locations
			stmt="Select Name from Location where IsActive=0 and LocationTypeID_FK=2;";
			System.out.println("stmt="+stmt);    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				InactiveItem = RS.getString(1);
				InactiveLocationList[NumInactiveLocation]=InactiveItem;
				NumInactiveLocation++;
			}		
			RS.close();
			//get inactive physicians
			stmt="Select StaffID from Staff where IsActive=0 and StaffTypeID_FK=1;";
			System.out.println("stmt="+stmt);    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				InactiveItem = RS.getString(1);
				InactivePhyList[NumInactivePhy]=InactiveItem;
				NumInactivePhy++;
			}		
			RS.close();
			//get inactive staff
			stmt="Select StaffID from Staff where IsActive=0;";
			System.out.println("stmt="+stmt);    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				InactiveItem = RS.getString(1);
				InactiveStaffList[NumInactiveStaff]=InactiveItem;
				NumInactiveStaff++;
			}		
			RS.close();
			//get inactive reasons for reprocessing
			stmt="Select Name from Barcode where IsActive=0 and BarcodeTypeID_FK=3;";
			System.out.println("stmt="+stmt);    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				InactiveItem = RS.getString(1);
				InactiveReasonList[NumInactiveReason]=InactiveItem;
				NumInactiveReason++;
			}		
			RS.close();
			//get inactive Bioburden
			stmt="Select Name from Barcode where IsActive=0 and BarcodeTypeID_FK=4;";
			System.out.println("stmt="+stmt);    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				InactiveItem = RS.getString(1);
				InactiveBioList[NumInactiveBio]=InactiveItem;
				NumInactiveBio++;
			}		
			RS.close();
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(SoiledDate,SoiledDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		

	    //Verify inactive items are not selectable.
	    Expected="Verify inactive Procedure rooms";
	    ResultInactiveLocation=WF_V.VerifyInactiveElements("ProcedureRoom",InactiveLocationList,NumInactiveLocation);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveLocation);				

	    Expected="Verify inactive Staff";
	    ResultInactiveStaff=WF_V.VerifyInactiveElements("StaffID",InactiveStaffList,NumInactiveStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveStaff);				

	    Expected="Verify inactive Physician";
	    ResultInactivePhy=WF_V.VerifyInactiveElements("PhysicianID",InactivePhyList,NumInactivePhy);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactivePhy);				

	    Expected="Verify inactive pre-clean Staff";
	    ResultInactiveStaff=WF_V.VerifyInactiveElements("PreCleanStaffID",InactiveStaffList,NumInactiveStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveStaff);				
		
	    Expected="Verify inactive LT/MC Staff";
	    ResultInactiveStaff=WF_V.VerifyInactiveElements("LTMCStaff",InactiveStaffList,NumInactiveStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveStaff);				
		
	    Expected="Verify inactive Bioburden Staff";
	    ResultInactiveStaff=WF_V.VerifyInactiveElements("BioburdenStaff",InactiveStaffList,NumInactiveStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveStaff);				
		
	    Expected="Verify inactive Bioburden results";
	    ResultInactiveBio=WF_V.VerifyInactiveElements("BioburdenValue",InactiveBioList,NumInactiveBio);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveBio);				
		
	    Expected="Verify inactive Repro In Staff";
	    ResultInactiveStaff=WF_V.VerifyInactiveElements("ScanInStaff",InactiveStaffList,NumInactiveStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveStaff);				
		
	    Expected="Verify inactive Reason for Repro";
	    ResultInactiveReason=WF_V.VerifyInactiveElements("ReprocessingReason",InactiveReasonList,NumInactiveReason);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveReason);				
		
	    Expected="Verify inactive Repro Out Staff";
	    ResultInactiveStaff=WF_V.VerifyInactiveElements("ScanOutStaff",InactiveStaffList,NumInactiveStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveStaff);				
		
		
		  
    	WF_A.UpdateProcedureRoom(ModLocationName);
    	WhatChanged[NumChanges]="Procedure Room";
    	NumChanges++;

		cal2.setTime(CurrentSoiledTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, -25);
		ModExamDateTime=df2.format(CurrentSoiledDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateExamDate(ModExamDateTime);
//    	WhatChanged[NumChanges]="Exam Date";
    	WhatChanged[NumChanges]="Procedure Room Scope Scan In";
    	NumChanges++;

    	WF_A.UpdatePRInStaff(ModInStaffID);
//    	WhatChanged[NumChanges]="Procedure Room Staff";
    	WhatChanged[NumChanges]="Procedure Room Staff ID";
    	NumChanges++;

		WF_A.UpdatePatient(ModPatientID);
    	WhatChanged[NumChanges]="Patient ID";
    	NumChanges++;			

		WF_A.UpdatePhysician(ModPhyID);
    	WhatChanged[NumChanges]="Physician ID";
    	NumChanges++;			

		cal2.setTime(CurrentSoiledTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, -22);
		ModProcStartDateTime=df2.format(CurrentSoiledDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateProcStart(ModProcStartDateTime);
    	WhatChanged[NumChanges]="Procedure Start";
    	NumChanges++;			

		cal2.setTime(CurrentSoiledTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, -8);
		ModProcEndDateTime=df2.format(CurrentSoiledDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateProcEnd(ModProcEndDateTime);
    	WhatChanged[NumChanges]="Procedure End";
    	NumChanges++;			

		ModPreCleanStatus="Yes";
		WF_A.UpdatePreClean(ModPreCleanStatus);
//    	WhatChanged[NumChanges]="Pre-Clean Y/N";
    	WhatChanged[NumChanges]="Pre-Clean Complete";
    	NumChanges++;			

		WF_A.UpdatePreCleanStaff(ModPreCleanStaff);
    	WhatChanged[NumChanges]="Pre-Clean Staff ID";
    	NumChanges++;			
		  calint++;
		  calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
			  calint=0;
			  calchk="0";
			}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    //Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	   
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		Description="Verifying Audit log for RefNo="+RefNo;
		
		rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		if (rowComp==0){				
			Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description+". Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);	
		if (rowComp==0||NumChanges<rowComp){ 	
			for(int i=0;i<NumChanges;i++){
				switch (WhatChanged[i]) {
				case "Procedure Room":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModLocationName);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Procedure Room Scope Scan In":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModExamDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Procedure Room Staff ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
					
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModInStaffID);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Patient ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPatientID);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	
				case "Physician ID":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
						System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPhyID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
	
					break;
				case "Procedure Start":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModProcStartDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Procedure End":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModProcEndDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Pre-Clean Y/N":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"No");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPreCleanStatus);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Pre-Clean Staff ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPreCleanStaff);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
					
				default:
	
					break;
	
				}
				Expected="v_AuditLog - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}
    	
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		//Verify the user is able to add Soiled Area activity to an existing workflow. This will be for scope2 cycle 2.
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		for(int i=0;i<10;i++){
			InactiveLocationList[i]="zz";
		}
		NumInactiveLocation=0;
		
		RefNo="2-2";
		ScopeSerialNo="2233445";
		ScopeName="Scope2";
		ModLocationName="Sink 2";
		Description="Add Soiled Area activity to an existing workflow";

    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK where "
					+ "SC.ScopeID_FK=2 and SC.CycleID=2 and IH.LocationID_FK=22 and IH.CycleEventID_FK=8 and IH.ScanItemTypeID_FK=1;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime,IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=2 and SC.CycleID=2 and IH.LocationID_FK=22 and IH.CycleEventID_FK=8 and IH.ScanItemTypeID_FK=1;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				PreCleanDate=RS.getString(2);
				PreCleanTime=RS.getString(3);
				CurrentPreCleanDate=RS.getDate(4);
				CurrentPreCleanTime=RS.getTime(4);
			}
			
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				SinkStaffPK= RS.getInt(1); 
				SinkStaffID = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+SinkStaffPK;
			statement.executeUpdate(stmt);
			
			//get inactive locations
			stmt="Select Name from Location where IsActive=0 and LocationTypeID_FK=4;";
			System.out.println("stmt="+stmt);    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				InactiveItem = RS.getString(1);
				InactiveLocationList[NumInactiveLocation]=InactiveItem;
				NumInactiveLocation++;
			}		
			RS.close();
			
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(PreCleanDate,PreCleanDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		

	    //Verify inactive items are not selectable.
	    Expected="Verify inactive Soiled Area";
	    ResultInactiveLocation=WF_V.VerifyInactiveElements("SoiledArea",InactiveLocationList,NumInactiveLocation);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveLocation);				

	    
    	WF_A.UpdateSoiledArea(ModLocationName);
    	WhatChanged[NumChanges]="Soiled Area";
    	NumChanges++;
	    Thread.sleep(1000);

		cal2.setTime(CurrentPreCleanTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, +5); //change the time by subtracting two minute.
		ModSoiledDateTime=df2.format(CurrentPreCleanDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateSoiledDate(ModSoiledDateTime);
//    	WhatChanged[NumChanges]="LT/MC Date";
    	WhatChanged[NumChanges]="Soiled Area Scope Scan In";
    	NumChanges++;			
	    Thread.sleep(1000);

    	
    	WF_A.UpdateSoiledStaff(SinkStaffID);
//    	WhatChanged[NumChanges]="LT/MC Staff ID";
    	WhatChanged[NumChanges]="Soiled Area Staff ID";
    	NumChanges++;			

    	ModLT="Pass";
		WF_A.UpdateLTStatus(ModLT);
//    	WhatChanged[NumChanges]="Leak Test (p/f)";
    	WhatChanged[NumChanges]="Leak Test Result";
    	NumChanges++;		
    	ModLT="Leak Test Pass";
    	
		cal2.setTime(CurrentPreCleanTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... TODO: need to make this smarter to account for different time zones and day light savings.
		cal2.add(Calendar.MINUTE, +7); //change the time by subtracting one minute.
		ModMCStartDateTime=df2.format(CurrentPreCleanDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateMCStart(ModMCStartDateTime);
    	WhatChanged[NumChanges]="Manual Clean Start";
    	NumChanges++;			

		cal2.setTime(CurrentPreCleanTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... TODO: need to make this smarter to account for different time zones and day light savings.
		cal2.add(Calendar.MINUTE, +12); //change the time by adding one minute.
		ModMCEndDateTime=df2.format(CurrentPreCleanDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateMCEnd(ModMCEndDateTime);
//    	WhatChanged[NumChanges]="Manual Clean End";
    	WhatChanged[NumChanges]="Manual Clean Complete";
    	NumChanges++;			

		  calint++;
		  calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
			  calint=0;
			  calchk="0";
			}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		if (rowComp==0){				
			Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description +". Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);	
		
		if (rowComp==0||NumChanges<rowComp){ 	
			for(int i=0;i<NumChanges;i++){
				switch (WhatChanged[i]) {
				case "Soiled Area":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModLocationName);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	//			case "LT/MC Date":
				case "Soiled Area Scope Scan In":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModSoiledDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	//			case "LT/MC Staff ID":
				case "Soiled Area Staff ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
					
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, SinkStaffID);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				//case "Leak Test (p/f)":
				case "Leak Test Result":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModLT);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	
				case "Manual Clean Start":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModMCStartDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	//			case "Manual Clean End":
				case "Manual Clean Complete":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModMCEndDateTime);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				default:
	
					break;
	
				}
				Expected="v_AuditLog - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}
		
		//Verify the user is able to add Reprocessing Area activity to an existing workflow. This will be for scope3 cycle 2.
  		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		for(int i=0;i<10;i++){
			InactiveLocationList[i]="zz";
		}
		NumInactiveLocation=0;
		RefNo="3-2";
		ScopeSerialNo="3344556";
		ScopeName="Scope3";
		ModLocationName="Reprocessor 4";
		ModReason="Used in Procedure";
    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK where "
					+ "SC.ScopeID_FK=3 and SC.CycleID=2 and IH.LocationID_FK=44 and IH.CycleEventID_FK=14;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime,IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=3 and SC.CycleID=2 and IH.LocationID_FK=44 and IH.CycleEventID_FK=14;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				MCEndDate=RS.getString(2);
				MCEndTime=RS.getString(3);
				CurrentMCEndDate=RS.getDate(4);
				CurrentMCEndTime=RS.getTime(4);
			}
			
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5);";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				ReproInStaffPK= RS.getInt(1); 
				ReproInStaffID = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ReproInStaffPK;
			statement.executeUpdate(stmt);
			
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5);";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				ReproOutStaffPK= RS.getInt(1); 
				ReproOutStaffID = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ReproOutStaffPK;
			statement.executeUpdate(stmt);
			
			//get inactive locations
			stmt="Select Name from Location where IsActive=0 and LocationTypeID_FK=5;";
			System.out.println("stmt="+stmt);    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				InactiveItem = RS.getString(1);
				InactiveLocationList[NumInactiveLocation]=InactiveItem;
				NumInactiveLocation++;
			}		
			RS.close();
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(MCEndDate,MCEndDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		

	    //Verify inactive items are not selectable.
	    Expected="Verify inactive Reprocessing Area";
	    ResultInactiveLocation=WF_V.VerifyInactiveElements("Reprocessor",InactiveLocationList,NumInactiveLocation);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveLocation);				

	    WF_A.updateReprocessingArea(ModLocationName);
    	WhatChanged[NumChanges]="Reprocessor";
    	NumChanges++;

    	WF_A.updateReasonforReprocessing(ModReason);
//    	WhatChanged[NumChanges]="Reason For Reprocessing";
    	WhatChanged[NumChanges]="Reprocessing Reason";
    	NumChanges++;

    	WF_A.updateScopeInStaff(ReproInStaffID);
    	WhatChanged[NumChanges]="Scope Scan In Staff ID";
    	NumChanges++;
    	
    	cal2.setTime(CurrentMCEndTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, +5);
		ModReproInDateTime=df2.format(CurrentMCEndDate)+" "+df.format(cal2.getTime());
		WF_A.updateScopeInStaffDate(ModReproInDateTime);
//    	WhatChanged[NumChanges]="Scope Scan In Time";
    	WhatChanged[NumChanges]="Reprocessor Scope Scan In";
    	NumChanges++;

    	WF_A.updateScopeOutStaff(ReproOutStaffID);
    	WhatChanged[NumChanges]="Scope Scan Out Staff ID";
    	NumChanges++;

    	cal2.setTime(CurrentMCEndTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, +35);
		ModReproOutDateTime=df2.format(CurrentMCEndDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateScopeOutStaffDate(ModReproOutDateTime);
//    	WhatChanged[NumChanges]="Scope Scan Out Time";
    	WhatChanged[NumChanges]="Reprocessor Scope Scan Out";
    	NumChanges++;
		  calint++;
		  calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
			  calint=0;
			  calchk="0";
			}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);

		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		if (rowComp==0){				
			Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description+ ". Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);	
		
		if (rowComp==0||NumChanges<rowComp){ 	
			for(int i=0;i<NumChanges;i++){
				//AL_A.ClearAuditLogSrchCritera();
				AL_A.Search_RefNo(RefNo);
				AL_A.Search_ScopeName(ScopeName);
				if (!Comment.equalsIgnoreCase("")){
					AL_A.Search_Comments(Comment);
				}
				
				AL_A.Search_Location(ModLocationName);
				AL_A.Search_WhatChanged(WhatChanged[i]);
				
				GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
				Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
				System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
				Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
				System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
				Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
				System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
				Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
				System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
				Result_Audit_UserName=AL_V.Verify_Username(GridID, Unifia_Admin_Selenium.userQV1);
				System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
				Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
				System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
				System.out.println("WhatChanged[i]="+WhatChanged[i]);
				
				switch (WhatChanged[i]) {
					case "Reprocessor":
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModLocationName);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
	//				case "Reason For Reprocessing":
					case "Reprocessing Reason":
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModReason);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
	//				case "Scope Scan In Time":
					case "Reprocessor Scope Scan In":
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);					
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModReproInDateTime);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Scope Scan In Staff ID":
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ReproInStaffID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
	//				case "Scope Scan Out Time":
					case "Reprocessor Scope Scan Out":
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.verify_PreviousValueDate(GridID,"-");
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModReproOutDateTime);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Scope Scan Out Staff ID":
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ReproOutStaffID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);	
						break;	
						
					default:
							break;
				}
				Expected="v_AuditLog - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		//Verify the user is able to add Bioburden activity to an existing workflow. This will be for scope4 cycle 2.
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		RefNo="4-2";
		ScopeSerialNo="4455667";
		ScopeName="Scope4";
		ModLocationName="Bioburden1";
		ModBioResult="Pass";
		ModBioScanResult="Red";
		Description="Add Bioburden activity to an existing workflow";

    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK where "
					+ "SC.ScopeID_FK=3 and SC.CycleID=2 and IH.LocationID_FK=44 and IH.CycleEventID_FK=14;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime,IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=3 and SC.CycleID=2 and IH.LocationID_FK=44 and IH.CycleEventID_FK=14;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				MCEndDate=RS.getString(2);
				MCEndTime=RS.getString(3);
				CurrentMCEndDate=RS.getDate(4);
				CurrentMCEndTime=RS.getTime(4);
			}			
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				BioStaffPK= RS.getInt(1); 
				BioStaffID = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+BioStaffPK;
			statement.executeUpdate(stmt);						
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(MCEndDate,MCEndDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		

		WF_A.UpdateBioStatus(ModBioResult);
//    	WhatChanged[NumChanges]="Bioburden Test Status";
    	WhatChanged[NumChanges]="Bioburden Test Result";
    	NumChanges++;
    	
		WF_A.UpdateBioScannedResult(ModBioScanResult);
    	WhatChanged[NumChanges]="Bioburden Scanned Result";
    	NumChanges++;

    	WF_A.UpdateBioStaff(BioStaffID);
    	WhatChanged[NumChanges]="Bioburden Staff ID";
    	NumChanges++;			
    	calint++;
    	calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		Unifia_Admin_Selenium.driver.navigate().refresh();
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.ApplyFilter();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    
	    Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		
		rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		if (rowComp==0){				
			Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description+". Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);	
		
		if (rowComp==0||NumChanges<rowComp){ 	
			for(int i=0;i<NumChanges;i++){
				switch (WhatChanged[i]) {
	//			case "Bioburden Test Status":
				case "Bioburden Test Result":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioResult);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Bioburden Scanned Result":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioScanResult);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Bioburden Staff ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
					
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, BioStaffID);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	
				default:
	
					break;
	
				}
				Expected="Add Bioburden - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}	

		//skip the pass/ fail test result but perform an optional result (scanned or key entry) and a staff scan. modify these items and verify the data can be saved. 
		//then go back into the same record and add a pass/fail save. go back in again and remove the pass/fail and verify all bioburden fields are removed and the audit log tracks all the changes done.
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		RefNo="9-2";
		ScopeSerialNo="9876432";
		ScopeName="Scope9";
		ModLocationName="Bioburden1";
		ModBioResult="Pass";
		ModBioScanResult="Blue";
		Description="Scope was scanned with Bioburden scanner, pass/fail was skipped, optional red was scanned, and staff scanned. Verify the user can change the optional scan "
				+ "and staff without adding a pass/fail result and verify the audit log. go back to the same record, add pass, verify audit log, go back to same record and "
				+ "remove pass and verify all bioburden fields are removed ";
    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK "
					+ "where SC.ScopeID_FK=9 and SC.CycleID=2 and IH.CycleEventID_FK=27;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=9 and SC.CycleID=2 and IH.CycleEventID_FK=27;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				BioDate=RS.getString(2);
				BioTime=RS.getString(3);
			}
			RS.close();
			stmt="select IH.ScanItemID_FK, ST.StaffID, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where CycleEventID_FK=43 and AssociationID_FK="+AssociationID;
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				CurrentStaffPK=RS.getInt(1); 
				CurrentStaffID=RS.getString(2);
				StaffScanDate=RS.getString(3);
			}		
			RS.close();
			stmt="select IH.ScanItemID_FK, Barcode.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Barcode on ScanItemID_FK=BarcodeID_PK where CycleEventID_FK=29 and AssociationID_FK="+AssociationID;
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				CurrentBioScanResultPK=RS.getInt(1); 
				CurrentBioScanResult=RS.getString(2);
				BioScanResultScanDate=RS.getString(3);
			}		
			RS.close();

			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		RS = statement.executeQuery(stmt);
			while(RS.next()){
				BioStaffPK= RS.getInt(1); 
				BioStaffID = RS.getString(2);
			}		
			RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+BioStaffPK;
			statement.executeUpdate(stmt);
						
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(BioDate,BioDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		

		WF_A.UpdateBioScannedResult(ModBioScanResult);
    	WhatChanged[NumChanges]="Bioburden Scanned Result";
    	NumChanges++;

    	WF_A.UpdateBioStaff(BioStaffID);
    	WhatChanged[NumChanges]="Bioburden Staff ID";
    	NumChanges++;			
    	calint++;
    	calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
		Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		
		rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		if (rowComp==0){				
			Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description+" Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);
	
		if (rowComp==0||NumChanges<rowComp){ 	
			for(int i=0;i<NumChanges;i++){
				switch (WhatChanged[i]) {
	//			case "Bioburden Test Status":
				case "Bioburden Test Result":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioResult);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Bioburden Scanned Result":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, BioScanResultScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentBioScanResult);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioScanResult);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Bioburden Staff ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
					
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, StaffScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentStaffID);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, BioStaffID);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	
				default:
	
					break;
	
				}
				Expected="Bioburden pass/fail missing, change other items - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(BioDate,BioDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    
		WF_A.UpdateBioStatus(ModBioResult);
//    	WhatChanged[NumChanges]="Bioburden Test Status";
    	WhatChanged[NumChanges]="Bioburden Test Result";
    	NumChanges++;			

    	calint++;
    	calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    
	    Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		
		AL_A.Search_RefNo(RefNo);
		AL_A.Search_ScopeName(ScopeName);
		AL_A.Search_Comments(Comment);
		AL_A.Search_Location(ModLocationName);
		AL_A.Search_WhatChanged(WhatChanged[0]);
		System.out.println("WhatChanged[0]="+WhatChanged[0]);

		GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[0]);
		Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
		Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
		Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
		System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
		Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
		Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
		System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
		Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[0]);
		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);

		Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
		System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
		Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
		Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioResult);
		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);

		Expected="Bioburden pass added - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[0];
		Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
				+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
				+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		String BioTestResultDateTime=ReconDateTime;

		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		ModBioResult="";
		Expected="Bioburden Status pass is removed and as a result values and staff are cleared";
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(BioDate,BioDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    
		WF_A.UpdateBioStatus(ModBioResult);
//    	WhatChanged[NumChanges]="Bioburden Test Status";
    	WhatChanged[NumChanges]="Bioburden Test Result";
    	NumChanges++;			
		String Result_BioResult=WF_V.Verify_BioResult(ModBioResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_BioResult);
		ModBioResult="-";
    	
    	WhatChanged[NumChanges]="Bioburden Scanned Result";
    	NumChanges++;
    	ModBioScanResult="";
		String Result_BioScan=WF_V.Verify_BioScanValue(ModBioScanResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_BioScan);
		ModBioScanResult="-";

    	WhatChanged[NumChanges]="Bioburden Staff ID";
    	NumChanges++;	
		String Result_Staff=WF_V.Verify_BioStaff("");
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Staff);

    	calint++;
    	calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		
		rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		//Integer newNumChangesforRowCount=8;
		//rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,newNumChangesforRowCount); // num of changes as 8 because for the same scope bioburden status is updated and removed
		if (rowComp==0){				
			Result_Audit_RowsCount="Pass -  Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM "+NumChanges+" did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM "+ NumChanges+" , did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description+". Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);
		
		
		if (rowComp==0||NumChanges<rowComp){ 	
			for(int i=0;i<NumChanges;i++){
				switch (WhatChanged[i]) {
	//			case "Bioburden Test Status":
				case "Bioburden Test Result":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, BioTestResultDateTime);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"Pass");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioResult);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Bioburden Scanned Result":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, BioScanResultScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"Blue");
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioScanResult);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				case "Bioburden Staff ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
					
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, StaffScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,BioStaffID);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID,"-");
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
	
				default:
	
					break;
	
				}
				Expected="Bioburden pass/fail missing, change other items - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		//Verify Culture Awaiting Results - modify it to pass. 
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		NumChanges=0;
		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		RefNo="5-2";
		ScopeSerialNo="5566778";
		ScopeName="Scope5";
		ModLocationName="Culturing1";
		ModCultureResult="Pass";
		Description="Modify the Culture status from Awaiting Results to pass.";
    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=5 and SC.CycleID=2 and IH.CycleEventID_FK=30;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=5 and SC.CycleID=2 and IH.CycleEventID_FK=30;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				CultureDate=RS.getString(2);
				CultureTime=RS.getString(3);
			}								
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(CultureDate,CultureDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    
		WF_A.UpdateCultureResult(ModCultureResult);
    	WhatChanged[NumChanges]="Culture Status";
    	NumChanges++;
    	Description="Set Culture Status from Awaiting Result to Pass";
    	Expected="Culture Status = Pass";
    	String CultureResult=WF_V.Verify_CultureResult(ModCultureResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, CultureResult);				
    	ModCultureResult="Culture Pass";

    	calint++;
    	calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    
	    Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();

		AL_A.Search_RefNo(RefNo);
		AL_A.Search_ScopeName(ScopeName);
		AL_A.Search_Comments(Comment);
		AL_A.Search_Location(ModLocationName);
		AL_A.Search_WhatChanged(WhatChanged[0]);
		System.out.println("WhatChanged[i]="+WhatChanged[0]);

		GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[0]);
		Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
		Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
		Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
		System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
		Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
		Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
		System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
		Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[0]);
		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
		
		Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
		System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
		Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"Awaiting Results");
		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
		Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModCultureResult);
		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
		Expected="Modify Culture Results - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[0];
		Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
				+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
				+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				

		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		//Verify Culture No Results - modify it to fail. 
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";

		RefNo="6-2";
		ScopeSerialNo="6677889";
		ScopeName="Scope6";
		ModLocationName="Culture Hold Cabinet";
		String CultureLocation="Culturing1";
		ModCultureResult="Fail";
		Description="Modify the Culture status from No Results to Fail.";

    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=6 and SC.CycleID=2 and IH.CycleEventID_FK=33;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime,"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=6 and SC.CycleID=2 and IH.CycleEventID_FK=33;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				CultureDate=RS.getString(2);
				CultureTime=RS.getString(3);
				CultureDateTime=RS.getString(4);
			}
								
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(CultureDate,CultureDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    WF_A.UpdateCultureResult(ModCultureResult);
    	WhatChanged[NumChanges]="Culture Status";
    	NumChanges++;
    	Description="Set Culture Status from No Results to Fail";
    	Expected="Culture Status = Fail";
    	CultureResult=WF_V.Verify_CultureResult(ModCultureResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, CultureResult);				
		ModCultureResult="Culture Fail";
    	calint++;
    	calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		incompleteDetailsFlag=WF_A.getStatusOfLocation(CultureLocation); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(CultureLocation, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    
	    Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();

		AL_A.Search_RefNo(RefNo);
		AL_A.Search_ScopeName(ScopeName);
		AL_A.Search_Comments(Comment);
		AL_A.Search_Location(ModLocationName);
		AL_A.Search_WhatChanged(WhatChanged[0]);
		System.out.println("WhatChanged[i]="+WhatChanged[0]);

		GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[0]);
		Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
		Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
		Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
		System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
		Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
		Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
		System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
		Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[0]);
		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
		
		Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CultureDateTime);
		System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
		Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"Culture No Results");
		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
		Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModCultureResult);
		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
		Expected="Modify Culture Results - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[0];
		Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
				+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
				+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				

		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		//Verify adding Culture Results. 
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";

		RefNo="7-2";
		ScopeSerialNo="7654231";
		ScopeName="Scope7";
		ModLocationName="Culturing1";
		ModCultureResult="Awaiting Results";
		Description="Add culture awaiting results. ";
    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=7 and SC.CycleID=2 and IH.CycleEventID_FK=18;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=7 and SC.CycleID=2 and IH.CycleEventID_FK=18;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				ReproDate=RS.getString(2);
				ReproTime=RS.getString(3);
			}
								
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(ReproDate,ReproDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    
		WF_A.UpdateCultureResult(ModCultureResult);
    	WhatChanged[NumChanges]="Culture Status";
    	NumChanges++;
    	Description="Add Culture Status Awaiting Results";
    	Expected="Culture Status = Awaiting Results";
    	CultureResult=WF_V.Verify_CultureResult(ModCultureResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, CultureResult);				
		ModCultureResult="Awaiting Results";
    	calint++;
    	calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Comment="Comment"+cal+calchk;
		WF_A.EnterComment(Comment);
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		Unifia_Admin_Selenium.driver.navigate().refresh();
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.ApplyFilter();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    
	    Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();

		AL_A.Search_RefNo(RefNo);
		AL_A.Search_ScopeName(ScopeName);
		AL_A.Search_Comments(Comment);
		AL_A.Search_Location(ModLocationName);
		AL_A.Search_WhatChanged(WhatChanged[0]);
		System.out.println("WhatChanged[i]="+WhatChanged[0]);

		GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[0]);
		Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
		Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
		Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
		System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
		Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
		Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
		System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
		Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[0]);
		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
		
		Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReconDateTime);
		System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
		Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
		Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModCultureResult);
		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
		Expected="Modify Culture Results - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[0];
		Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
				+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
				+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				

		
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		//Verify comment only change. 
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		NumChanges=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";

		RefNo="7-1";
		ScopeSerialNo="7654231";
		ScopeName="Scope7";
		ModLocationName="Reprocessor 1";
		Description="Comment only no change to data.";
    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=7 and SC.CycleID=1 and IH.CycleEventID_FK=18;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=7 and SC.CycleID=1 and IH.CycleEventID_FK=18;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				ReproDate=RS.getString(2);
				ReproTime=RS.getString(3);
			}
								
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(ReproDate,ReproDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    
    	calint++;
    	calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Comment="Comment"+cal+calchk;
		WhatChanged[0]="Comments";
		WF_A.EnterComment(Comment);
		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    
	    Description="Verifying Audit log for RefNo="+RefNo;
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();

		AL_A.Search_RefNo(RefNo);
		AL_A.Search_ScopeName(ScopeName);
		AL_A.Search_Comments(Comment);
		//AL_A.Search_Location(ModLocationName);
		AL_A.Search_WhatChanged(WhatChanged[0]);
		System.out.println("WhatChanged[i]="+WhatChanged[0]);

		GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[0]);
		Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
		Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
		Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
		System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
		Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
		Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
		System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
		Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[0]);
		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
		
		Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, "-");
		System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
		Result_Audit_Location=AL_V.Verify_Location(GridID, "-");

		System.out.println("Result_Audit_Location="+Result_Audit_Location);
		Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
		Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, "-");
		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
		Expected="Modify Culture Results - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[0];
		Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
				+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
				+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Location="+Result_Audit_Location+". Result_Audit_Previous="
				+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				

		//verify whether the pop-up which says leave or stay is coming properly or not while trying to change patient details and navigate off the details page without saving/cancelling
		NumChanges=0;
		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Comment="";
		RefNo="8-1";
		ScopeSerialNo="6543216";
		ScopeModel="GIF-1TH190";
		ScopeName="Scope8";
		ModLocationName="Procedure Room 1";
		Description="Update Scope 8 Patient and try navigating without save/cancel and check for leave/stay pop-up and then click stay and save the changes and verify the same in Auditlog";
		
		try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			stmt="Select SC.AssociationID_FK, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate from ScopeCycle SC join ItemHistory IH on "
					+ "SC.AssociationID_FK=IH.AssociationID_FK where SC.ScopeID_FK=8 and SC.CycleID=1 and IH.LocationID_FK=21 and IH.CycleEventID_FK=8 and IH.ScanItemID_FK=8 and IH.ScanItemTypeID_FK=1;";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				AssociationID = RS.getInt(1);
				PreCleanDate=RS.getString(2);
			}
			RS.close();	
			
			stmt="Select IH.ReceivedDateTime from ItemHistory IH join Patient on IH.ScanItemID_FK=Patient.PatientID_PK where AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=4";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(RS.next()){
				receivedDataTime=RS.getTimestamp(1);
			}
			RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select PatientID_PK, PatientID, convert(varchar, format(cast(DATEADD(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) "
					+ "from ItemHistory IH join Patient on IH.ScanItemID_FK=Patient.PatientID_PK where AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=4";

			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				CurrentPatientPK = RS.getInt(1); 
				CurrentPatientID = RS.getString(2);
				PatientScanDate=RS.getString(3);
			}
	
			RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
		   	if (CurrentPatientID.equalsIgnoreCase("")){
		   		CurrentPatientID="";
			}
		   	else{
		   		RS=statement.executeQuery(stmtPatEncr);
				while(RS.next()){
					CurrentPatientPK = RS.getInt(1); 
					CurrentPatientID = RS.getString(3);
				}
			   	RS.close();
		   	}
			stmt="select PatientID_PK, PatientID from Patient where	PatientID_PK !="+CurrentPatientPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Patient where PatientID_PK !="+CurrentPatientPK+")";
			System.out.println("stmt="+stmt);

			RS= statement.executeQuery(stmt);
		 	while(RS.next()){
		 		ModPatientPK=RS.getInt(1);
		 		ModPatientID=RS.getString(2);
		 	}
			RS.close();
			stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ ModPatientPK+"'";
			RS=statement.executeQuery(stmtPatEncr);
			while(RS.next()){
				ModPatientPK = RS.getInt(1); 
				ModPatientID = RS.getString(3);
			}
			RS.close();		
		
			Statement update = conn.createStatement();
			stmt1="update Patient set LastUpdatedDateTime=GETUTCDATE() where PatientID_PK="+ModPatientPK;
			update.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(PreCleanDate,PreCleanDate);
	    IP_A.ApplyFilter();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(PreCleanDate,PreCleanDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);
	    
		WF_A.UpdatePatient(ModPatientID);
    	WhatChanged[NumChanges]="Patient ID";
    	NumChanges++;			
		Expected="verify Patient is set to "+ModPatientID;
		Result_Patient=WF_V.Verify_Patient(ModPatientID);
		Description="change the Patient ID to "+ModPatientID+" of Scope8 inorder to verify the Leave/Stay Pop-Up";		
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Patient);
		
		IP_A.Click_SRM();
		Thread.sleep(2000);
		String resultPopUp=null;
		try{
			Alert alert=Unifia_Admin_Selenium.driver.switchTo().alert();
			//String alertText=alert.getText();
			alert.dismiss();
			resultPopUp="Pass - The alert popup is displayed";
		}catch (NoAlertPresentException Ex) { 
			resultPopUp="#Failed!# - The alert popup is not displayed";
	    }   
		Thread.sleep(2000);
		/*Expected="Alert should pop-up with text as '"+popUpText+"'";
		
		if(alertText.equalsIgnoreCase(popUpText)){
			resultPopUp="Pass - The popup is displayed properly with expected text as '"+popUpText+"'";
		}else{
			UAS.resultFlag="#Failed!#";
			resultPopUp="#Failed!# - PopUp text was supposed to be "+popUpText+" but it was "+alertText;
		}*/
		Expected="Alert  pop-up should be displayed"; 
		Description="Verifying the alert pop up is coming or not when trying to navigate away from the page without saving or cancelling the changes made";		
		IHV.Exec_Log_Result(FileName, Description, Expected, resultPopUp);
		
		Comment="Changed scope8 patient ID";
		calint++;
		  calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
			  calint=0;
			  calchk="0";
			}
		Comment="Changed scope8 patient ID_"+cal+calchk;
		WF_A.EnterComment(Comment);

		incompleteDetailsFlag=WF_A.getStatusOfLocation(ModLocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(UAS.url, UAS.user, UAS.pass);
		WF_A.Save();
		
		expectedColor="";
		if(incompleteDetailsFlag){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		Description="verifying Chevron Color for Refno="+RefNo;
		Expected="Chevron Color should be "+expectedColor;
	    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor); //Verifying the Chevron Color
	    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
	    
		Description="Verify the audit log for Scope 8 Patient change which was saved after clicking 'Stay' on the popup";
		IP_A.Click_AuditLog();
		AL_A.click_AuditSearch();
		AL_A.ClearAuditLogSrchCritera();
		rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
		if (rowComp==0){				
			Result_Audit_RowsCount="Passed: Num of changes done in SRM matched with num of rows in audit log ";
		}else{
			UAS.resultFlag="#Failed!#";
			if (NumChanges+1==rowComp){
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
			}else{
				Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
			}
		}
		System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
		Description=Description+". Num of changes done in SRM matched with num of rows in audit log";
		Expected="Num of changes done in SRM equals num of rows in audit log";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);	
		
		if (rowComp==0||NumChanges<rowComp){ 	
			for(int i=0;i<NumChanges;i++){
				switch (WhatChanged[i]) {
				case "Patient ID":
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					AL_A.Search_Comments(Comment);
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
					Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PatientScanDate);
					System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
					Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPatientID);
					System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
					Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPatientID);
					System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					break;
				default:
					break;
				}
				Expected="v_AuditLog - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
				Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName="+Result_Audit_ScopeName
						+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
						+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
			}
		}
		
		//Verifying MRC Screen sorting both Ascending and Decending
		IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    String result="";
		
	    List<String> tableColumns=MRC_A.getMRCColumnNames();
	    for(String column: tableColumns) {
	        Unifia_Admin_Selenium.driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[1]/th[contains(text(),'"+column+"')]").click();
	        Thread.sleep(1000);
	        List<String> columnValues=MRC_A.getMRCColumnContent(column);
	        String[] columnData = new String[columnValues.size()];
	        columnData = columnValues.toArray(columnData);
			boolean isAscending=GF.isSortedInAscending(columnData);
			if(isAscending){
				result="Pass - "+column+" values in MRC table are displayed in Ascending order when sorted";
			}else{
				UAS.resultFlag="#Failed!#";
				result="#Failed!#- "+column+" values in MRC table are not displayed in Ascending order when sorted";
			}
			System.out.println(result);
			Description="Verifying whether "+column+" values in MRC table are displayed in Ascending order when sorted";
			Expected =column+" values in MRC table should be displayed in Ascending order when sorted";
			IHV.Exec_Log_Result(FileName, Description, Expected, result);
			
			Unifia_Admin_Selenium.driver.findElementByXPath("//*[@id='mrcGrid']/div[1]/table/tr[1]/th[contains(text(),'"+column+"')]").click();
	        Thread.sleep(1000);
	        columnValues=MRC_A.getMRCColumnContent(column);
	        columnData = columnValues.toArray(columnData);
			boolean isDecending=GF.isSortedInDecending(columnData);
			if(isAscending){
				result="Pass - "+column+" values in MRC table are displayed in Decending order when sorted";
			}else{
				UAS.resultFlag="#Failed!#";
				result="#Failed!#- "+column+" values in MRC table are not displayed in Decending order when sorted";
			}
			System.out.println(result);
			Description="Verifying whether "+column+" values in MRC table are displayed in Decending order when sorted";
			Expected =column+" values in MRC table should be displayed in Decending order when sorted";
			IHV.Exec_Log_Result(FileName, Description, Expected, result);
	    }
	    
		IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    MRC_A.click_MRCFilter();
	    
	    //checking whether screen is displaying current filters or not
	    result="";
	    boolean ispresent=MRC_A.verify_ReprocessorFilter();
	    if(!ispresent){
	    	UAS.resultFlag="#Failed!#";
	    	result="#Failed!# - Reprocessor filter is not present on MRC screen after clicking filter button; ";
	    }else{
	    	result="Pass - Reprocessor filter is present on MRC screen after clicking filter button; ";
	    }
	    
	    ispresent=MRC_A.verify_MRCResultFilter();
	    if(!ispresent){
	    	UAS.resultFlag="#Failed!#";
	    	result+="#Failed!# - MRC Test Result filter is not present on MRC screen after clicking filter button; ";
	    }else{
	    	result+="Pass - MRC Test Result filter is present on MRC screen after clicking filter button; ";
	    }
	    
	    ispresent=MRC_A.verify_MRCStaffFilter();
	    if(!ispresent){
	    	UAS.resultFlag="#Failed!#";
	    	result+="#Failed!# - MRC Test StaffID filter is not present on MRC screen after clicking filter button; ";
	    }else{
	    	result+="Pass - MRC Test StaffID filter is present on MRC screen after clicking filter button; ";
	    }
	    System.out.println(result);
		Description="Verifying whether MRC screen displays current filter selections after clicking Filter button";
		Expected ="MRC screen should display current filter selections after clicking Filter button";
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
	    
	    //Verifying the Reoprocessor Filter
	    String reproFilter="Reprocessor 1";
	    MRC_A.applyMRCReprocessorFilter(reproFilter); //Applying Reprocessor Filter
	    List<String> columnData=MRC_A.getMRCColumnContent("Reprocessor");
	    boolean filteredData=false;
	   
    	for(String columnContent:columnData){
	    	if(!columnContent.equalsIgnoreCase(reproFilter)){
	    		filteredData=false;
	    		break;
	    	}else{
	    		filteredData=true;
	    	}
	    }
	    if(filteredData){
			result="Pass - Reprocessor values in MRC table when filtered with "+reproFilter+" displayed records only with "+columnData.get(0);
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Reprocessor values in MRC table when filtered with "+reproFilter+" did not displayed records with "+reproFilter+" but the data was "+columnData.get(0);
		}
	    
	    System.out.println(result);
		Description="Verifying whether MRC table displays records only with Reprocessor value as "+reproFilter+" when filtered with "+reproFilter;
		Expected ="MRC table should display records only with Reprocessor value as "+reproFilter+" when filtered with "+reproFilter;
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
		
		//Verifying the clear filters option
		Description="Checking whether all filter values are blank when clear filters is clicked";
		Expected="All filter values should be blank when clear filters is clicked";
		MRC_A.click_MRCClearFilter();
		result=MRC_V.areAllMRCFiltersBlank();
	    if(result.contains("#Failed!#")){
	    	Unifia_Admin_Selenium.resultFlag="#Failed!#";
	    }
	    System.out.println("Result for All blank fields = "+result);
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
	    
		//Verifying the MRC Test Result Filter
		String testResultFilter = "Pass";
		MRC_A.applyMRCTestResultFilter(testResultFilter); //Applying Test Result Filter
	    columnData=MRC_A.getMRCColumnContent("MRC Test Result");
	    filteredData=false;
    	for(String columnContent:columnData){
	    	if(!columnContent.equalsIgnoreCase(testResultFilter)){
	    		filteredData=false;
	    		break;
	    	}else{
	    		filteredData=true;
	    	}
	    }
	    
	    if(filteredData){
			result="Pass - MRC Test Result values in MRC table when filtered with "+testResultFilter+" displayed records only with "+columnData.get(0);
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - MRC Test Result values in MRC table when filtered with "+testResultFilter+" did not displayed records with "+testResultFilter+" but the data was "+columnData.get(0);
		}
	    
	    System.out.println(result);
		Description="Verifying whether MRC table displays records only with MRC Test Result value as "+testResultFilter+" when filtered with "+testResultFilter;
		Expected ="MRC table should display records only with MRC Test Result value as "+testResultFilter+" when filtered with "+testResultFilter;
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
		
		//Verifying the MRC Test StaffID Filter
		MRC_A.click_MRCClearFilter();
	    String mrcStaffFilter ="T01";
	    
		MRC_A.applyMRCStaffFilter(mrcStaffFilter); //Applying Test Staff Filter
	    columnData=MRC_A.getMRCColumnContent("MRC Test Staff ID");
	    filteredData=false;
	    for(String columnContent:columnData){
		   	if(!columnContent.equalsIgnoreCase(mrcStaffFilter)){
		   		filteredData=false;
		   		break;
		   	}else{
		   		filteredData=true;
		   	}
	    }
		    
	    if(filteredData){
			result="Pass - MRC Test Result values in MRC table when filtered with "+mrcStaffFilter+" displayed records only with "+columnData.get(0);
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - MRC Test Result values in MRC table when filtered with "+mrcStaffFilter+" did not displayed records with "+reproFilter+" but the data was "+columnData.get(0);
		}
	    
		System.out.println(result);
		Description="Verifying whether MRC table displays records only with MRC Test Result value as "+mrcStaffFilter+" when filtered with "+mrcStaffFilter;
		Expected ="MRC table should display records only with MRC Test Result value as "+mrcStaffFilter+" when filtered with "+mrcStaffFilter;
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
		
		//Verifying multiple filter selection
		MRC_A.click_MRCClearFilter();
	    
	    reproFilter="Reprocessor 2";
	    testResultFilter="Pass";
	    mrcStaffFilter ="T05";
		
	    MRC_A.applyMRCReprocessorFilter(reproFilter); //Applying Reprocessor Filter
	    MRC_A.applyMRCTestResultFilter(testResultFilter); //Applying Test Result Filter
	    MRC_A.applyMRCStaffFilter(mrcStaffFilter); //Applying Test Staff Filter
	    
	    String columns[]={"Reprocessor","MRC Test Result","MRC Test Staff ID"};
	    String filterValue="";
	    for(String column : columns){
	    	if(column.equalsIgnoreCase("Reprocessor")){
	    		filterValue=reproFilter;
	    	}else if(column.equalsIgnoreCase("MRC Test Result")){
	    		filterValue=testResultFilter;
	    	}else{
	    		filterValue=mrcStaffFilter;
	    	}
	    	columnData=MRC_A.getMRCColumnContent(column);
		    filteredData=false;
		    for(String columnContent:columnData){
			   	if(!columnContent.equalsIgnoreCase(filterValue)){
			   		filteredData=false;
			   		break;
			   	}else{
			   		filteredData=true;
			   	}
		    }
	    }
	    if(filteredData){
			result="Pass - MRC Table when filtered with "+reproFilter+","+testResultFilter+","+mrcStaffFilter+" displayed records only with filtered data";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - MRC Table when filtered with "+reproFilter+","+testResultFilter+","+mrcStaffFilter+" did not displayed records with filtered data";
		}
	    
		System.out.println(result);
		Description="Verifying whether MRC table displays records only with MRC Test Result value as "+mrcStaffFilter+" when filtered with "+mrcStaffFilter;
		Expected ="MRC table should display records only with MRC Test Result value as "+mrcStaffFilter+" when filtered with "+mrcStaffFilter;
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
	    
		//Verifying Not found message when filtered
		MRC_A.click_MRCClearFilter();
	    mrcStaffFilter ="MD09";
	    MRC_A.applyMRCStaffFilter(mrcStaffFilter); //Applying Test Staff Filter
	    columnData=MRC_A.getMRCColumnContent("MRC Test Staff ID");
	    filteredData=false;
	    if(columnData.size()==1){
	    	if(columnData.get(0).equalsIgnoreCase("Not found")){
	    		result="Pass - 'Not found' is displayed when filtered with "+mrcStaffFilter;
	    	}else{
				UAS.resultFlag="#Failed!#";
				result="#Failed!# - MRC table should display 'Not found' when filtered with "+mrcStaffFilter+" however "+columnData.get(0)+" was displayed";
			}
	    }else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - MRC table should display 'Not found' when filtered with "+mrcStaffFilter+" however "+columnData.get(0)+" was displayed";
		}
		System.out.println(result);
		Description="Verifying whether MRC table displays 'Not found' when filtered with MRC Test StaffID as "+mrcStaffFilter;
		Expected ="MRC table should display 'Not found' when filtered with MRC Test StaffID as "+mrcStaffFilter;
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
		
		//Verifying Date range filters on MRC Screen.
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		UAS.TestCaseNumber++;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		
		IP_A.Click_InfectionPrevention();
		MRC_A.Click_MRCRecordManagement();
		
		//verifying the Default Date Range filter is Last 7 Days
		String startDate="",endDate="";
		startDate=GM.get_last7DaysDate()[0];				//getting Last 7 days dates and assigning it to start and end dates
		endDate=GM.get_last7DaysDate()[1];
		Description="verifying the Default Date Range filter is Last 7 Days";
		Expected="Default Date Range filter on the MRC Screen should display "+startDate+" - "+endDate;
		result=MRC_V.verify_MRCDateRangeText(startDate, endDate);
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
		
		String dateFilters[]={"Today","Yesterday","Last 7 Days","Last 30 Days","This Month","Last Month","Custom Range"};
		for(String dateFilter : dateFilters){
			if(dateFilter.equalsIgnoreCase("Today")){
				startDate=GM.get_TodayDate();						//getting Today's date and assigning it to start and end dates
				endDate=startDate;
			}else if(dateFilter.equalsIgnoreCase("Yesterday")){
				startDate=GM.get_YesterdayDate();					//getting Yesterday's date and assigning it to start and end dates
				endDate=startDate;
			}else if(dateFilter.equalsIgnoreCase("Last 7 Days")){
				startDate=GM.get_last7DaysDate()[0];				//getting Last 7 days dates and assigning it to start and end dates
				endDate=GM.get_last7DaysDate()[1];
			}else if(dateFilter.equalsIgnoreCase("Last 30 Days")){
				startDate=GM.get_last30DaysDate()[0];				//getting Last 30 days dates and assigning it to start and end dates
				endDate=GM.get_last30DaysDate()[1];
			}else if(dateFilter.equalsIgnoreCase("This Month")){
				startDate=GM.get_ThisMonthDates()[0];				//getting This month dates and assigning it to start and end dates
				endDate=GM.get_ThisMonthDates()[1];
			}else if(dateFilter.equalsIgnoreCase("Last Month")){
				startDate=GM.get_LastMonthDates()[0];				//getting Last month dates and assigning it to start and end dates
				endDate=GM.get_LastMonthDates()[1];
			}else if(dateFilter.equalsIgnoreCase("Custom Range")){
				startDate=GM.get_CustomDate(25);					//getting last 25 days date and assigning it to start date
				endDate=GM.get_CustomDate(3);						//getting last 10 days date and assigning it to end date
			}
			
			if(dateFilter.equalsIgnoreCase("Custom Range")){
				MRC_A.click_MRCDateFilter(dateFilter, startDate, endDate);
			}else{
				MRC_A.click_MRCDateFilter(dateFilter, "", "");
			}
			IP_A.ApplyMRCFilter();
			Thread.sleep(2000);
			result=MRC_V.verify_MRCDateRangeText(startDate, endDate);
			
			Description="Verifying dates in date filter tab on the MRC Screen when "+dateFilter+" is selected";
			Expected="Date filter on the MRC Screen should display "+startDate+" - "+endDate;
			IHV.Exec_Log_Result(FileName, Description, Expected, result);
			
			columnData=MRC_A.getMRCColumnContent("MRC Test Date/Time");
			if(columnData.size()==1 && columnData.get(0).equalsIgnoreCase("Not found")){
				result="Pass - There is no data when filtered with "+startDate+" - "+endDate;
			}else{
				result=GM.Verify_EM_DateRange(columnData, df2.parse(startDate), df2.parse(endDate), df2);
			}
			Description="Verifying MRC Test Date/Time column data on the MRC screen when "+dateFilter+" is selected";
			Expected="MRC Test Date/Time column data on the MRC screen should display records ranging between or equal to "+startDate+" - "+endDate;
			IHV.Exec_Log_Result(FileName, Description, Expected, result);
		}
		
		//verifying that after clicking Clear filters Default Date Range filter is Last 7 Days
		MRC_A.click_MRCDateClearFilter();
		Thread.sleep(2000);
		startDate=GM.get_last7DaysDate()[0];				//getting Last 7 days dates and assigning it to start and end dates
		endDate=GM.get_last7DaysDate()[1];
		Description="verifying that after clicking Clear filters Default Date Range filter is Last 7 Days";
		Expected="After clicking Clear filtersDefault Date Range filter on the MRC Screen should display "+startDate+" - "+endDate;
		result=MRC_V.verify_MRCDateRangeText(startDate, endDate);
		if(result.contains("#Failed!#")){
			result+="; Bug - 11764: MRC - When I select a date range filter such as Last Month and click apply filter the screen selects the date range.  However, when I click Filter the filter is not reset";
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
				
		IHV.Close_Exec_Log(FileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		LPA.Click_Logout();
		Thread.sleep(1000);

		if (UAS.resultFlag.contains("#Failed!#")) {
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
   
   	@AfterTest
  	public void PostTest() throws IOException{
	  LP_A.CloseDriver();
  	}
}