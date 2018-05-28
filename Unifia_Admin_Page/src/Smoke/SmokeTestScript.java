
package Smoke;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
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
import java.text.ParseException;
import java.util.Calendar;

import ITConsole.ITConScanSimActions;
import TestFrameWork.UnifiaAdminLoginPage.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminFacilityPage.*;
import TestFrameWork.UnifiaAdminLocationPage.*;
import TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification;
import TestFrameWork.UnifiaAdminScannerPage.*;
import TestFrameWork.UnifiaAdminAccessPointPage.*;
import TestFrameWork.UnifiaAdminStaffPage.*;
import TestFrameWork.UnifiaAdminUserPage.*;
import TestFrameWork.UnifiaAdminAssignBarcodePage.*;
import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.QlikView.*;
import TestFrameWork.Unifia_Admin_Selenium;

/**
 * Smoke Test Script - Verifies all Smoke test Scenarios to ensure new build is acceptable
The smoke test is not a detailed test but just touches upon key functionalities and would ideally be run 
after a new build is deployed
It would take about 10 minutes to complete which includes reloading the data via QMC.
 * 
 * 
Step 1- Clear current test data
Step 2- reset ship data
Step 3- Logon the admin desktop, verify the admin menu -- done
Step 4- Verify the Facility is set to My Facility--done
Step 5- Setup Access point-
step 6- Setup locations
Step 7- Setup Scanners
Step 8- Setup Staff
Step 9- Logout of admin
Step 10-access dashboard
step 11-Verify the dashboard matches the locations that where setup
step 12-Load some historical data
Step 13-Verify the IP page has the historical data displayed (simple verification)
Step 14-Load small sample of recon data. (Can use data from Step 12, 13) Verify its available on recon screens and make modifications. 
(Modify the patient and physician on the Procedure Room)*
*/
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
public class SmokeTestScript {
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private User_Actions ua; //shortcut to link to the UnifiaAdminUserPage.User_Actions java class.
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestDataFunc TDF;
	private ITConsole.ITConScanSimActions IT_A;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private static TestFrameWork.Emulator.Emulator_Actions EM_A;
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Verification MRC_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	private static TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private  static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions mrc_a;
	private TestFrameWork.Emulator.GetIHValues IHV;
    public boolean blnStatus;
    public String GridID;
    public long cal = Calendar.getInstance().getTimeInMillis(); 
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
    Connection conn= null;
    public String Green="rgba(137, 187, 64, 1)";
   	public String Purple="rgba(134, 92, 161, 1)";
   	public String Orange="rgba(239, 113, 65, 1)";
   	public String Black="rgba(82, 82, 82, 1)";
   	public String Red="rgba(209, 51, 56, 1)";
    
    public String FinalResult="------------------Smoke Test Script TestSummary-----------------------";
    public String Result1; 
    public String Result2;
    public String Result3;  
    public String Result4; 
    public String Result5; 
    public String Result6;  
    public String Result7; 
    public String Result8; 
    public String Result8a;
    public String Result8b;
    public String Result8c;
    public String Result9;
    public String Result10;
    public String Result11;
    public String Result12;
    public String Result13;
    public String Result14;
  
    public String username="qvtest01";
    
    public int KE=0;
	public int Bioburden=1;
	public int Culture=0;
    
    Login_Actions LGPA;
    LandingPage_Actions LPA;
    GeneralFunc GF;
    Facility_Actions FPA;
    Facility_Verification FPV;
    Location_Actions LOCPA;
    Scanner_Actions SCNPA;
    AccessPoint_Actions APA;
    Staff_Actions STAFFA;
    LandingPage_Verification LPV;
    Login_Verification LV;
    QV_GeneralFunc QV_Gen;
    Dashboard_Verification DV;
    ReconcilationAuditLog_Verification ALV;
    AssignBarcode_Verification SE_LV;
    
    private String fileDestFolder="\\XMLFolder";
	private String scenarioFilePath="TestData\\AppTestData";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	private String testScenarioXML="TestData\\AppTestData\\TestScenarios.xml";
	//private String ReconBioburdenXMLFile="ReconScanData_BioBurden_ITConsole";
	private String ReconScanDataXMLFile="ComplexReconData";
	private String PR="Procedure Room 1";
	private String scope="Scope1";
	private String scopeSRNo="1122334";
	private String scopeRefNo="1-3";
	private String CurrentInStaffID="-";
	private String ModInStaffID="T10";
	private String CurrentMRCStaffID="T13";
	private String ModMRCStaffID="T11";
	public static String user =Unifia_Admin_Selenium.user;
    public static String pass = Unifia_Admin_Selenium.pass;
    public static String connstring = Unifia_Admin_Selenium.connstring;
	public static String url = Unifia_Admin_Selenium.url;
	
	
	/*Result Detail
	
	*********Step 3*********
	Result1 :  Verify whether 'Admin' Tab is displayed in the application Home page
	*********Step 4*********
	Result2 :  Verify the Facility is set to My Facility
	*********Step 5*********
	Result3 :  Verify if the user is able to create 'Access Point' 
	*********Step 6*********
	Result4 :  Verify if the user is able to create 'Location - New_Procedure_Room' 
	Result5 :  Verify if the user is able to create 'Location - New_Scope_Storage_Area' 
	*********Step 7*********
	Result6 :  Verify if the user is able to create 'Scanner - Scanner1' 
	Result7 :  Verify if the user is able to create 'Scanner - Scanner2' 
	*********Step 8*********
	Result8 :  Verify if the user is able to create 'Staff - Jane Thomas' 
	*********Step 8b*********
	Result8b :  Verify if the user is able to click on Custom Values and Print Barcode 
	*********Step 9*********
	Result9 : Verify if the user is able to log out of Admin
	*********Step 10*********
	Result10 : Verifying Procedure Room1 has scope1 in Dashboard screen 
	*********Step 11*********
	Result11 : Verify StaffIDChanged in SRM screen is reflected in auditlog screen
	*********Step 12*********
	Result12 : Verify StaffIDChanged in MRC screen is reflected in auditlog screen
	*********Step 13*********
	*Result13 : Verify MAM Screen for Scope scanned in Procedure Room
	*/
    
    @Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws Exception {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}

    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
    		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
    		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
    		
    		String FileName="SmokeTestScript_TestSummary_";
    		FileName=IHV.Start_Exec_Log(FileName);
    		UAS.XMLFileName=FileName;
    		UAS.TestCaseNumber=1;
			
		/**
		 *!!The assumption for this test is that before test runs and that all non-shipped data has been delete and 2 Facilities are added
		 *!!The two facilities are "Your Facility Name" and "Inactive_Facility", only "Your Facility Name" is active
		 * The plan for this test is to login and verify that the facility "Your Facility Name"  will be displayed in the Location Facility field.*/
		 
    	try{
    		//Step 1- Clear current test data
    		//Step 2- reset ship data
    		//conn= DriverManager.getConnection(url, user, pass);
    		//System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user,Unifia_Admin_Selenium.pass);
    		String stmt;
    		stmt = "delete from SoiledAreaSignOff;delete from relateditem;delete from KeyEntryScans;delete from ReconciliationActivityLogValue;delete from Barcode where IsShipped=0;delete from ReconciliationActivityLog;"
    				+ "delete from ReconciliationReportComment;DELETE FROM ItemHistory;delete from ScopeCycle;DELETE FROM ScopeStatus;DELETE FROM Association;delete from ProcedureRoomStatus;"
    				+ "DELETE FROM LocationStatus;DELETE FROM Patient;delete from Scanner;delete from Scope;delete from ExamType where IsShipped=0;"
    				+ "delete from User_Facility_Assoc where UserID_PK>2;delete from User_Role_Assoc where UserID_PK>2;delete from [User] where UserID_PK > 2;"
    				+ "delete from ScopeType where IsShipped=0;delete from AERDetail;delete from Location;delete from AccessPoint;delete from Staff;"
    				+ "delete from Facility where FacilityID_PK > 1;delete from Role_Task_Assoc where RoleID_PK>7;delete from Role where RoleID_PK>7;"
    				+ "update ExamType set IsActive=0 where ExamTypeID_PK=13; update dbo.[User] set isactive=1;"
    				+ "update Facility set Name='Your Facility Name', Abbreviation='YFN', IsBioburdenTestingPerformed=1 where FacilityID_PK=1;";
    	
   			System.out.println("stmt="+stmt);
   			Statement update1 = conn.createStatement();
   			update1.executeUpdate(stmt);
   			conn.close();
   			FinalResult =  FinalResult + "\r\n Reset ShipData";
   			FinalResult=FinalResult+"\r\n -------------------Step 1 & 2 - done----------";
   		}
   		catch (SQLException ex){
   		    // handle any errors
   		    System.out.println("SQLException: " + ex.getMessage());
   		    System.out.println("SQLState: " + ex.getSQLState());
   		    System.out.println("VendorError: " + ex.getErrorCode());	
   		}
		
		//Step 3-  Logon the admin desktop, verify the admin menu
		//The user logs onto the application
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		LGPA.Logon_Username(Unifia_Admin_Selenium.appUser); 
		LGPA.Logon_Password(Unifia_Admin_Selenium.appPassword);
		LGPA.Click_Submit();
		//Verify Admin Tab exists
		String Login=LPV.Admin_Landing_Pg_Verf();
		if (Login.equalsIgnoreCase("Pass")){
			Result1="Passed: 'Admin' Tab is displayed in the application Home page.";
		}else{
			Result1="#Failed!#: 'Admin' Tab is not displayed in the application Home page.";
		}
		FinalResult =  FinalResult + "\r\n Result1="+Result1;
		FinalResult=FinalResult+"\r\n -------------------Step 3 - done----------";
		
		//Step 4-  Verify the Facility is set to My Facility
		String myFacility="Your Facility Name";
		String AbbreviationValue="YFN";
		LPA.Click_Admin_Menu("Facility");
		FPA.Search_Facility_ByName(myFacility);
		System.out.println("Search facility by name");
		GridID=FPA.GetGridID_Facility_To_Modify(myFacility);
		System.out.println("Grid ID="+GridID);
		String ResultFacilityActivee=FPA.Facility_Active_Value(myFacility); 
		FPA.Select_Facility_To_Modify(myFacility);
		String ResultAbbv=FPV.Verify_Abbreviation(GridID,AbbreviationValue);
		if (!GridID.equals(null) && ResultFacilityActivee.equalsIgnoreCase("true")&& ResultAbbv.contains("Pass")){
			Result2="Passed: 'Your Facility Name' is available in the application and it is active";
		}else{
			Result2="#Failed!#: 'Your Facility Name' is not available in the application and/or it is not active";
		}
		FinalResult =  FinalResult + "\r\n Result2="+Result2;
		FinalResult=FinalResult+"\r\n -------------------Step 4 - done----------";
		
		//Step 5-  Setup Access point
		LPA.Click_Admin_Menu("AccessPoint");
		APA.Add_New_AccessPoint();
		calint++;
		calchk=String.valueOf(calint);
		String SSID="SSID"+cal+calchk; //Create a unique SSID by converting the current time to an integer.
		APA.Enter_SSID_New(SSID); 
		calint++;
		calchk=String.valueOf(calint);
		String Password="Psw"+cal+calchk; 
		APA.Enter_Password_New(Password);
		APA.Selct_New_AccessPointStatus("True");
		APA.Save_AccessPoint_Edit();
		
		APA.Search_SSID(SSID);
		GridID=APA.GetGridID_AccessPoint_To_Modify(SSID);
		System.out.println("GridID in e_Modify function is: "+GridID); 
		if (!GridID.equals(null)){
			Result3="Passed: 'Access Point' is created successfully.";
		}else{
			Result3="#Failed!#: Failed to create' Access Point.";
		}
		FinalResult =  FinalResult + "\r\n Result3="+Result3;
		FinalResult=FinalResult+"\r\n -------------------Step 5 - done----------";
		
		//Step 6-  Setup locations
		LPA.Click_Admin_Menu("Location");
		LOCPA.Add_New_Location();
		LOCPA.Selct_Location_Facility("Your Facility Name");
		LOCPA.Enter_Location_Name("New_Procedure_Room");
		LOCPA.Selct_Location_SSID(SSID);
		LOCPA.Selct_Location_Type("Procedure Room");
		LOCPA.Selct_New_Location_Active("True");
		LOCPA.Save_Location_Edit();
		
		LOCPA.Search_Location_ByName("New_Procedure_Room");  
		GridID=LOCPA.GetGridID_Location_To_Modify("New_Procedure_Room");
		System.out.println("GridID = "+GridID);
		if (!GridID.equals(null)){
			Result4="Passed: 'Location - New_Procedure_Room' is created successfully.";
		}else{
			Result4="#Failed!#: Failed to create Location - New_Procedure_Room.";
		}
		FinalResult =  FinalResult + "\r\n Result4="+Result4;
		
		LOCPA.Add_New_Location();
		LOCPA.Enter_Location_Name("New_Scope_Storage_Area");
		LOCPA.Selct_Location_SSID(SSID);
		LOCPA.Selct_Location_Type("Scope Storage Area");
		LOCPA.Selct_New_Location_Active("True");
		LOCPA.Enter_StorageCabinets("1");
		LOCPA.Save_Location_Edit();
		
		LOCPA.Search_Location_ByName("New_Scope_Storage_Area"); 
		GridID=LOCPA.GetGridID_Location_To_Modify("New_Scope_Storage_Area");
		System.out.println("GridID = "+GridID);
		if (!GridID.equals(null)){
			Result5="Passed: 'Location - New_Scope_Storage_Area' is created successfully.";
		}else{
			Result5="#Failed!#: Failed to create Location - New_Scope_Storage_Area.";
		}
		FinalResult =  FinalResult + "\r\n Result5="+Result5;
		FinalResult=FinalResult+"\r\n -------------------Step 6 - done----------";
		
		//Step 7- Setup Scanners
		LPA.Click_Admin_Menu("Scanner");
		SCNPA.Add_New_Scanner(); 
		SCNPA.Enter_Location_New("New_Procedure_Room (YFN)");
		SCNPA.Enter_ScanID_New("RFSCID1");
		SCNPA.Enter_ScanName_New("Scanner1");
		SCNPA.Save_Scanner_Edit();
		
		SCNPA.Search_ScanID("RFSCID1");
		GridID=SCNPA.GetGridID_Scanner_To_Modify("RFSCID1");
		if (!GridID.equals(null)){
			Result6="Passed: 'Scanner - Scanner1' is created successfully.";
		}else{
			Result6="#Failed!#: Failed to create Scanner - Scanner1.";
		}
		FinalResult =  FinalResult + "\r\n Result6="+Result6;
		
		SCNPA.Add_New_Scanner(); 
		SCNPA.Enter_Location_New("New_Scope_Storage_Area (YFN)");
		SCNPA.Enter_ScanID_New("RFSCID2");
		SCNPA.Enter_ScanName_New("Scanner2");
		SCNPA.Save_Scanner_Edit();
		
		SCNPA.Search_ScanID("RFSCID2");
		GridID=SCNPA.GetGridID_Scanner_To_Modify("RFSCID2");
		if (!GridID.equals(null)){
			Result7="Passed: 'Scanner - Scanner2' is created successfully.";
		}else{
			Result7="#Failed!#: Failed to create Scanner - Scanner2.";
		}
		FinalResult =  FinalResult + "\r\n Result7="+Result7;
		FinalResult=FinalResult+"\r\n -------------------Step 7 - done----------";
		
		//Step 8- Setup Staff
		
		LPA.Click_Admin_Menu("Staff");
		STAFFA.Add_New_Staff();
		STAFFA.Selct_New_Title("Dr.");
		STAFFA.Enter_New_StaffID("SmokeID1");
		STAFFA.Enter_New_Staff_FirstName("Jane");
		STAFFA.Enter_New_Staff_LastName("Thomas");
		STAFFA.Selct_New_Staff_Type("Physician");
		STAFFA.Save_Staff_Edit();
		
		STAFFA.Search_Staff_ByLastName("Thomas");
		STAFFA.Search_Staff_ByFirstName("Jane");
		STAFFA.Search_Staff_ByStaffID("SmokeID1");
		GridID=STAFFA.GetGridID_Staff_To_Modify("SmokeID1");
		
		if (!GridID.equals(null)){
			Result8="Passed: 'Staff - Jane Thomas' is created successfully.";
		}else{
			Result8="#Failed!#: Failed to create Staff - Jane Thomas.";
		}
		FinalResult =  FinalResult + "\r\n Result8="+Result8;
		FinalResult=FinalResult+"\r\n -------------------Step 8 - done----------";
		
		//Step 8b- Click on Custom and Print Barcode
		LPA.Click_Admin_Menu("AssignBarcode");
		Result8a= LPV.Verify_Admin_Function("Custom Values");
		FinalResult =  FinalResult + "\r\n Result8a="+Result8a;
		FinalResult=FinalResult+"\r\n -------------------Step 8a - done----------";
		
		LPA.Click_Admin_Menu("PrintBarcode");
		String PageTitle=LPV.driver.findElementByClassName("title").getText();
		if(PageTitle.equalsIgnoreCase("Print Barcodes")){
			Result8b="Test Passed as Print Barcodes page is displayed";
		}
		else 
		{ Result8b="Test Failed as Print Barcode page is not displayed";
		}
		
		FinalResult =  FinalResult + "\r\n Result8b="+Result8b;
		FinalResult=FinalResult+"\r\n -------------------Step 8b - done----------";
		
		String ButtonLabel=LPV.driver.findElementById("printBarcodeButton").getText();
		//*[@id="printBarcodeButton"]/span/span
		if(ButtonLabel.equalsIgnoreCase("Print Barcodes")){
			Result8c="Test Passed as Print Barcodes button is displayed";
		}
		else 
		{ Result8c="Test Failed as button is not displayed";
		}

		FinalResult =  FinalResult + "\r\n Result8c="+Result8c;
		FinalResult=FinalResult+"\r\n -------------------Step 8c - done----------";
		
		//Step 9-  Logout of admin
		LPA.Click_Logout();
		String Result=LV.Admin_Login_Pg_Verf();	
		if (Result.equalsIgnoreCase("Pass")){
			Result9="Passed: Logged out of Admin screen successfully.";
		}else{
			Result9="#Failed!#: Unable to log out out of Admin screen.";
		}
		FinalResult =  FinalResult + "\r\n Result9="+Result9;
		FinalResult=FinalResult+"\r\n -------------------Step 9 - done----------";
		
		//Load complexRecon
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
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL);
		//PR Available
		
		boolean Res= EM_A.ScanItem(PR, "Workflow Event", "", "Available", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem(PR, "Scope", "", scope, "");

		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		
		//Verify Dashboard 
		qvd_v.clickDashBoard();
		//String xpath=DBP.PR1Name;
		String PR1Scopes=qvd_v.getPR1Scopes();
		System.out.println(PR1Scopes);
		String expPR1Scopes=scope;
		String result_PR1Scopes;
		if (qvd_v.compareRes(expPR1Scopes,PR1Scopes,true)){
			Result10="Passed: Expected= "+expPR1Scopes+";Actual= "+PR1Scopes;
		}else{
			Result10="#Failed!#: Expected= "+expPR1Scopes+";Actual= "+PR1Scopes;
		}
		
		FinalResult =  FinalResult + "\r\n Result10= Verify Procedure Room1 has scope1 in Dashboard screen; "+Result10;
		FinalResult=FinalResult+"\r\n -------------------Step 10 - done----------";
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(scopeSRNo);
	    IP_A.ApplyFilter();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(scopeSRNo);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(scopeRefNo);
	    
	    // Add the StaffID
	    String comment="Procedure room staffID is changed";
	    String StaffInScanDate="";
	    WF_A.UpdatePRInStaff(ModInStaffID);
	    String ReconDateTime=GF.ServerDateTime(Unifia_Admin_Selenium.url, user, pass);
	    WF_A.EnterComment(comment);
	    WF_A.Save();
	    String whatChanged="Procedure Room Staff ID";
	    
	    //verify AuditLog
	    IP_A.Click_AuditLog();
	    AL_A.click_AuditSearch();
	    AL_A.Search_RefNo(scopeRefNo);
		AL_A.Search_ScopeName(scope);
		AL_A.Search_Location(PR);
		AL_A.Search_WhatChanged(whatChanged);
		
		GridID=AL_A.GetGridID_AuditLog_ByRefNo(scopeRefNo,whatChanged);
		String Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,scopeRefNo);
		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
		String Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
		String Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,scope);
		System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
		String Result_Audit_Comment=AL_V.Verify_Comment(GridID, comment);
		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
		String Result_Audit_UserName=AL_V.Verify_Username(GridID, Unifia_Admin_Selenium.userQV1);
		System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
		String Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, whatChanged);
		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
		String Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentInStaffID);
		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
		String Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModInStaffID);
		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
		
		Result11="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName="+Result_Audit_ScopeName+
				". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged+
				". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified;
		
		FinalResult =  FinalResult + "\r\n Result11= Verify StaffIDChanged in SRM screen is reflected in auditlog screen; "+Result11;
		FinalResult=FinalResult+"\r\n -------------------Step 11 - done----------";
		
		//MRC Details
		String gridId="1";
		IP_A.Click_InfectionPrevention();
		MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
		MRC_A.editMRCRecord(gridId);
		MRC_A.updateMRCStaff(ModMRCStaffID);
    	whatChanged="MRC Test Staff ID";
    	String result_MRC="MRC Result ="+MRC_V.verifyMRCStaff(ModMRCStaffID);
    	comment = "MRC StaffID is changed";
		MRC_A.EnterComment(comment);
    	MRC_A.saveMRCChanges();
    	
	    //verify AuditLog
    	String locationMRC="Reprocessor 5";
	    IP_A.Click_AuditLog();
	    AL_A.click_AuditSearch();
		AL_A.Search_Location(locationMRC);
		AL_A.Search_WhatChanged(whatChanged);
		
		Result_Audit_RefNo=AL_V.Verify_RefNum(gridId,"-");
		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
		Result_Audit_ReconDate=AL_V.Verify_ReconDate(gridId, ReconDateTime);
		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
		Result_Audit_ScopeName=AL_V.Verify_ScopeName(gridId,"-");
		System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
		Result_Audit_Comment=AL_V.Verify_Comment(gridId, comment);
		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
		Result_Audit_UserName=AL_V.Verify_Username(gridId, Unifia_Admin_Selenium.userQV1);
		System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
		Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(gridId, whatChanged);
		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
		Result_Audit_Previous=AL_V.Verify_PreviousValue(gridId,CurrentMRCStaffID);
		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
		Result_Audit_Modified=AL_V.Verify_ModifiedValue(gridId, ModMRCStaffID);
		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
		
		Result12="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName="+Result_Audit_ScopeName+
				". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged+
				". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified;
    	
    	FinalResult =  FinalResult + "\r\n Result12= Verify StaffIDChanged in MRC screen is reflected in auditlog screen;"+Result12;
		FinalResult=FinalResult+"\r\n -------------------Step 12 - done----------";
    	//MAM
    	MAM_A.Click_MaterialsAndAssetManagement();
		String examCount="1";
		String reprocessingCount="3";
		String Result13="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(scope, "LAST SCOPE LOCATION=="+PR+" (YFN);LAST SCAN STAFF ID==-;EXAM COUNT=="+examCount+";REPROCESSING COUNT=="+reprocessingCount).toString();
		FinalResult =  FinalResult + "\r\n Result13= Verify MAM Screen for Scope scanned in Procedure Room; "+Result13;
		FinalResult=FinalResult+"\r\n -------------------Step 13 - done----------";
		System.out.println("Overall Test status is "+FinalResult);	
		try{ 
			  // Create file 
			  FileWriter fstream = new FileWriter("SmokeTestScript_TestSummary.txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(FinalResult);
			  //Close the output stream
			  out.close();
		  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		  }
		IHV.Close_Exec_Log(UAS.XMLFileName, "Test Completed", UAS.TestCaseNumber);
		if (FinalResult.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
    @AfterTest
    public void PostTest() throws IOException{
    	LP_A.CloseDriver();
    }
	
}

