package AdminUserStories.Recon.MRC;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class AddMRCRecord {
	
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Verification MRC_V;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	private static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	private TestDataFunc TDF;
	public int KE=0;
	public int Bioburden=1;
	public int Culture=0;
	private Connection conn= null;
	private String stmt;
	private ResultSet MRC_RS;
	private SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
	private SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	private static String ModRepro;
	private String MRCTestDateTime="";
	private String ModMRCRes="";
	private String ModMRCStaff="";
	private String Comment="";
	private String UserName="qvtest01";
	private String GridID;
	private String ReconDateTime="";
	private String ReconScanDateTime="";
	private String WhatChanged="New MRC Record Added";
	
	private String Result_Audit_RefNo;
	private String Result_Audit_ReconDate;
	private String Result_Audit_OriginalScanDate;
	private String Result_Audit_Comment;
	private String Result_Audit_UserName;
	private String Result_Audit_WhatChanged;
	private String Result_Audit_Location;
	private String Result_AuditLog;
	private String Result;
	
	private String FileName="";
	private String Description;
	private String Expected;
	private int calint;
	private String calchk;
	private long cal = Calendar.getInstance().getTimeInMillis();
	
	private Calendar cal2 = Calendar.getInstance();
	private String expectedBGColor="rgba(255, 227, 229, 1)"; //Expected Background color for Missed column values while adding a New MRC Record
	private String actualBGColor="";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void edge(String browserP, String URL,String AdminDB) throws InterruptedException, AWTException, IOException, ParseException, SQLException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		
		//TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		//TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
    	//GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
    
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		
		FileName="Add_MRC_Workflow_Details";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		
		//Scenario -1 Checking whether all field values are blank when Add MRC window is opened.
		Description="Checking whether all field values are blank when Add MRC window is opened";
		Expected="All field values are blank when Add MRC window is opened";
		IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
	    MRC_A.addMRCRecord();
	    String result=MRC_V.areAllAddMRCFieldsBlank();
	    if(result.contains("#Failed!#")){
	    	Unifia_Admin_Selenium.resultFlag="#Failed!#";
	    	result+="; BUG : 11716 - MRC - When adding an MRC record, the screen does not default a blank value for Reprocessor (which is a required field) and Staff ID (which is not a required value).  Blank options do appear however when editing a row ";
	    }
	    System.out.println("Result for All blank fields = "+result);
		IHV.Exec_Log_Result(FileName, Description, Expected, result);
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		MRC_A.cancelNewMRCRecord();
		
		//Scenario -2 Checking that a New MRC Record Should not be added without MRC Test Date, Reprocessor, Test Result and it should throw error
		Description="Checking that a New MRC Record should not be added without MRC Test Date, Reprocessor, Test Result and it should throw error";
		Expected="New MRC Record Should not be added without without MRC Test Date, Reprocessor, Test Result and it should throw error";
		IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
	    MRC_A.addMRCRecord();
	    MRC_A.saveNewMRCRecord();
	    String errorText=MRC_A.getAddMRCErrorText();
	    //errorText=errorText.replaceAll("\n", "");
	    if(errorText.equalsIgnoreCase("Please provide a date and time.\nPlease provide a location.\nPlease provide a test result.")){
			Result="Passed - Error text was 'Please provide a date and time.Please provide a location.Please provide a test result.'";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!# - Error text was expected to be 'Please provide a date and time.Please provide a location.Please provide a test result.' but it is '"+errorText+"'";
		}
		System.out.println("Result for blank Reprocessor = "+Result);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		MRC_A.cancelNewMRCRecord();
		
		//Scenario -3 Checking that a New MRC Record Should not be added without MRC Test Date and it should throw Alert saying that Invalid data entered!
		Description="Checking that a New MRC Record should not be added without MRC Test Date and it should throw Alert saying that Invalid data entered!";
		Expected="New MRC Record Should not be added without MRC Test Date and it should throw Alert saying that Invalid data entered!";
		try{
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.connstring);		
    		Statement statement = conn.createStatement();
			stmt="Select Name from Location where IsActive=1 and LocationTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Location where IsActive=1 and LocationTypeID_FK=5)";
			System.out.println("stmt="+stmt);
    		
    		MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				ModRepro = MRC_RS.getString(1);
			}		
			MRC_RS.close();
			stmt="update Location set LastUpdatedDateTime=GETUTCDATE() where Name='"+ModRepro+"'";
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
	    MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
	    MRC_A.addMRCRecord();
	    MRC_A.addReprocessor(ModRepro);
	    ModMRCRes="Pass";
	    MRC_A.addMRCResult(ModMRCRes);
	    ReconDateTime=GF.ServerDateTime(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    MRC_A.saveNewMRCRecord();
	    errorText=MRC_A.getAddMRCErrorText();
	    if(errorText.contains("Please provide a date and time.")){
			Result="Passed - Error text was 'Please provide a date and time.'";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!# - Error text was expected to be 'Please provide a date and time.' but it is '"+errorText+"'";
		}
		System.out.println("Result for blank MRC Date = "+Result);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		MRC_A.cancelNewMRCRecord();
		
		
		//Scenario -4 Checking that a New MRC Record Should not be added without Reprocessor and it should throw Alert saying that Invalid data entered!
		Description="Checking that a New MRC Record should not be added without Reprocessor and it should throw Alert saying that Invalid data entered!";
		Expected="New MRC Record Should not be added without Reprocessor and it should throw Alert saying that Invalid data entered!";
		IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
	    MRC_A.addMRCRecord();
	    MRCTestDateTime=df2.format(cal2.getTime())+" "+df.format(cal2.getTime());
	    MRC_A.updateMRCDateTime(MRCTestDateTime);
	    ModMRCRes="Pass";
	    MRC_A.addMRCResult(ModMRCRes);
	    ReconDateTime=GF.ServerDateTime(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    MRC_A.saveNewMRCRecord();
	    errorText=MRC_A.getAddMRCErrorText();
	    if(errorText.contains("Please provide a location.")){
			Result="Passed - Error text was 'Please provide a location.'";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!# - Error text was expected to be 'Please provide a location.' but it is '"+errorText+"'";
		}
		System.out.println("Result for blank Reprocessor = "+Result);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		MRC_A.cancelNewMRCRecord();
		
		//Scenario -5 Checking that a New MRC Record Should not be added without MRC Test Result and it should throw Alert saying that Invalid data entered!
		Description="Checking that a New MRC Record Should not be added without MRC Test Result and it should throw Alert saying that Invalid data entered!";
		Expected="New MRC Record Should not be added without MRC Test Result and it should throw Alert saying that Invalid data entered!";
		try{
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.connstring);		
    		Statement statement = conn.createStatement();
			stmt="Select Name from Location where IsActive=1 and LocationTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Location where IsActive=1 and LocationTypeID_FK=5)";
			System.out.println("stmt="+stmt);
    		
    		MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				ModRepro = MRC_RS.getString(1);
			}		
			MRC_RS.close();
			stmt="update Location set LastUpdatedDateTime=GETUTCDATE() where Name='"+ModRepro+"'";
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
	    MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
	    MRC_A.addMRCRecord();
	    MRCTestDateTime=df2.format(cal2.getTime())+" "+df.format(cal2.getTime());
	    MRC_A.updateMRCDateTime(MRCTestDateTime);
	    MRC_A.addReprocessor(ModRepro);
	    ReconDateTime=GF.ServerDateTime(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    MRC_A.saveNewMRCRecord();
	    errorText=MRC_A.getAddMRCErrorText();
	    if(errorText.contains("Please provide a test result.")){
			Result="Passed - Error text was 'Please provide a test result.'";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result="#Failed!# - Error text was expected to be 'Please provide a test result.' but it is '"+errorText+"'";
		}
		System.out.println("Result for blank MRC Test Result = "+Result);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		MRC_A.cancelNewMRCRecord();
		
		//Scenario -6 Adding a new MRC Record with all required fields
		Description="Checking to add a New MRC Record with all required data and verifying whether it is saved proprly or not";
		Expected="A new MRC record should be added with all the data that is entered";
		IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
	    MRCTestDateTime=df2.format(cal2.getTime())+" "+df.format(cal2.getTime());
	    try{
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.connstring);		
    		Statement statement = conn.createStatement();
			stmt="Select Name from Location where IsActive=1 and LocationTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Location where IsActive=1 and LocationTypeID_FK=5)";
			System.out.println("stmt="+stmt);
    		
    		MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				ModRepro = MRC_RS.getString(1);
			}		
			MRC_RS.close();
			stmt="update Location set LastUpdatedDateTime=GETUTCDATE() where Name='"+ModRepro+"'";
			statement.executeUpdate(stmt);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
	    
	    MRC_A.addMRCRecord();
	    MRC_A.updateMRCDateTime(MRCTestDateTime);
	    MRC_A.addReprocessor(ModRepro);
	    ModMRCRes="Pass";
	    MRC_A.addMRCResult(ModMRCRes);
	    ModMRCStaff="T04";
	    MRC_A.addMRCStaff(ModMRCStaff);
	    calint++;
		calchk = String.valueOf(calint);
		if (calchk.equals(1000)) {
			calint = 0;
			calchk = "0";
		}
		Comment = "NewMRCRecord " + cal + calchk;
	    MRC_A.enterNewComment(Comment);
	    ReconDateTime=GF.ServerDateTime(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    ReconScanDateTime=ReconDateTime.substring(0, ReconDateTime.length()-5);
	    MRC_A.saveNewMRCRecord();
	    System.out.println("ReconDateTime="+ReconDateTime);
	    
	    String mrcDateTime[]=MRCTestDateTime.split(" ");
		String MRCDate=mrcDateTime[0];
		System.out.println("Applying date filter from "+MRCDate+" to "+MRCDate);
		
		//Verifying MRC Screen for new Record
		IP_A.Click_InfectionPrevention();
		MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
		IP_A.DateFilter(MRCDate, MRCDate);
		Thread.sleep(2000);
		IP_A.ApplyMRCFilter();
		Thread.sleep(2000);
		GridID=MRC_A.getMRCGridID(MRCTestDateTime);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(GridID,"MRC TEST DATE/TIME=="+MRCTestDateTime+";REPROCESSOR=="+ModRepro+";MRC TEST Result=="+ModMRCRes+";MRC TEST STAFF ID=="+ModMRCStaff).toString();
		System.out.println("result_MRC = "+result_MRC);
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
	    
	    //Verifying Audit Log for the newly added MRC Record
		Description="Verifying Audit log for the newly added MRC record";
	    IP_A.Click_AuditLog();
	    AL_A.click_AuditSearch();
	   // AL_A.Search_ReconDateTime(ReconScanDateTime);
	    AL_A.Search_WhatChanged(WhatChanged);
	    AL_A.Search_UserName(UserName);
		AL_A.Search_Comments(Comment);
		//AL_A.Search_Location(ModRepro);
		
		GridID=AL_A.GetGridID_AuditLog_ByComments(Comment);
		if (GridID.equalsIgnoreCase("0")){
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			Result_AuditLog="#Failed!#:  No Record is found in Audit log screen for newly added MRC Record. Bug 12565 opened - Adding a new MRC Record does not create audit details entry";
		}else{
			Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,"-");
			System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
			Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
			System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
			Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, "-");
			System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
			Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
			System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
			Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
			System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
			Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged);
			System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
			Result_Audit_Location=AL_V.Verify_Location(GridID, ModRepro);
			if(Result_Audit_Location.contains("#Failed!#")){
				Result_Audit_Location+=": BUG 11917 - Adding a new MRC Record adds a row in the Audit Details screen with Location as '-' instead of re-processor name";
			}
			System.out.println("Result_Audit_Location="+Result_Audit_Location);
			Expected="v_AuditLog - verify the Audit Log for Ref No=-  and WhatChanged="+WhatChanged;
			Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate
					+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
					+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Location="+Result_Audit_Location+".";
		}
		
		if(Result_AuditLog.contains("#Failed!#")){
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		
		if (Unifia_Admin_Selenium.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
	}
	
	@AfterTest
    public void PostTest() throws IOException{
    	LP_A.CloseDriver();
    }

}
