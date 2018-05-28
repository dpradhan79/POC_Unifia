package LocationDashboard.SoiledArea;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.annotations.Test;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Verification;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

	public class LD_SoiledRoom_TJFQ180V {
		
    TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LPA;
    TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.Emulator.GetIHValues IHV;
	TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_LocationDashboard.LD_Actions LD_A;
	public static TestFrameWork.Unifia_LocationDashboard.LD_Verification LD_V;
	public static TestFrameWork.Unifia_EM.EM_Actions MultiFac_A;

	private TestFrameWork.Unifia_Admin_Selenium UAS;

	public String Description;  //the description written to the test step log or printed to the console
	public String Expected; //The expected result written to the test step log
	public String FileName="";
	private int Scenario=1;
	private TestDataFunc TDF;
	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;
	public String ScopeName;
	public String NoScopeScan;
	public String ScopeSerialNo;
	public String ScopeModel;
	public int ScopePK;
	public int CurrentIHPK; // used to get the current records scan history primary key
	public String RefNo; //the reference number of the record being modified.
	public int AssociationID; //association ID of the record being modified. 
	public int LocationID; //the location's primary key
	public String LocationName; //location name (i.e. Sink 1) 
	public int LocationID_PK;
	public String[] InactiveLocationList=new String[10];
	public int NumInactiveLocation=0;
	public String ResultInactiveLocation;
	public String InactiveItem;
	
	public String UserName="qvtest01";

	public String StaffID="";
	public String StaffFirstName="";
	public String StaffLastName="";
	public String StaffScan;
	public String LeakTestRes="";
	public String MCTimePeriod="";
	public Boolean Res;
	public String Scanner="";
	
	public String ResultMessage="";
	public String Message="";
	public String CurrentStepNum="Step 1";
	public String CurrentStepText="Scan \"Manual Clean Start\" to play";
	public String NextStepNum="Step 2";
	public String NextStepText="Scan your Staff ID to proceed";
	public String PreviousStepNum="Step 1";
	public String PreviousStepText="Scan \"Go Back\" to previous";
	public String ResultLocationFacility="";
	public String ResultCurrentStep="";
	public String ResultNextStep="";
	public String ResultPrevStep="";
	public ResultSet Scope_rs;  //result set used to get ScopeName from the Test DB
	public ResultSet Staff_rs;
	public ResultSet Scanner_ID_rs;
	public String ScanScope="";
	public String ScopeSerialNumber="";
	public int ScopeID;	
	private String Facility1="FAC1";
	private String Facility2="FAC2";
	private String Facility3="FAC3";
	private String Facility1Name="Facility 1";
	private String Facility2Name="Facility 2";
	private String Facility3Name="Facility 3";

	public ResultSet RS;
	public String stmt;
	public String stmt1;
    public String GridID;
    Connection conn= null;
	public int UTCTimeDiffInHours=0;
	
   @Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws Exception {
    		//select the Driver type Grid or local

		
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    	}		

		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		UAS.resultFlag="Pass";
		Unifia_Admin_Selenium.isRecon=false;
		//DB  Updates
		GF.SyncRemoteMachineTime(Unifia_Admin_Selenium.KE_Env, Unifia_Admin_Selenium.KEMachine_Username, Unifia_Admin_Selenium.KEMachine_pswd, URL);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMultiFacilityMasterData(UAS.url, UAS.user, UAS.pass,KE, Bioburden, Culture);
		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);

		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, UAS.roleMgr, UAS.userQV1, UAS.userQV1Pwd,Facility2,Facility3);
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);

		FileName="LD_SoiledRoom_TJFQ180V_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		Scenario=0;
	   	UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();
		for(int i=0;i<10;i++){
			InactiveLocationList[i]="zz";
		}
		NumInactiveLocation=0;

	   	ScanScope="F1 Scope10";
	   	ScopeSerialNumber="7654321";
	   	LocationID_PK=41;
	   	Scanner="F1 Sink 1";
	   	StaffID="T01";
	   	StaffFirstName="Tech1";
	   	StaffLastName="Tech1";

   		IP_A.Click_LD();
   		//verify inactive location is disabled.

    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			//get inactive locations for Facility 1

			stmt="Select Name from Location where IsActive=0 and LocationTypeID_FK=4 and FacilityID_FK=1;";
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
    	Description="Verify inactive items are disabled";
    	Expected="F1 Sink 3 is inactive";
    	
    	ResultInactiveLocation=LD_V.VerifyInactiveLocations(InactiveLocationList,NumInactiveLocation);
		System.out.println(Scenario+":  "+ResultInactiveLocation);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveLocation);

   		LD_A.ClickLocation(LocationID_PK);
    	Description="Verify default MCLD screen for "+Facility1Name+" "+Scanner;
    	Expected="The location and facility are displayed in the upper right.";

   		ResultLocationFacility=LD_V.VerifyLocationFacility(Scanner,Facility1Name);
		System.out.println(Scenario+":  "+ResultLocationFacility);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultLocationFacility);
		
    	Expected="The main massage is Scan the scope.";
   		Message="Scan the scope.";
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+":  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
   		   		
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
    	Description="Verify MCLD screen after scanning "+ScanScope;
    	Expected="The main massage is updated to Scan your badge and "+ScanScope+" is added to the header and progress bar.";

		Message="Scan your badge.";
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+":  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
	   		   	
   		StaffScan=StaffFirstName+" "+StaffLastName+"("+StaffID+")";
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
    	Description="Verify MCLD screen after scanning "+StaffScan;
    	Expected="The main massage is updated to conduct LT and scan result and "+StaffScan+" is added to the header and progress bar.";

    	Message="Conduct leak test then scan\n\"Leak Test Passed\"\nor \"Leak Test Failed\"";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+":  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
    	Description="Verify MCLD screen after scanning LT Pass";
    	Expected="The main massage is updated to scan MC start and Leak Test Pass is added to the header and progress bar.";

    	Message="Scan Manual Clean Start\nthen begin manual cleaning process.";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
    	Description="Verify MCLD screen after scanning MC Start";
    	Expected="The steps of the MCLD TJF-Q180V Manual cleaning procedure is displayed.";

		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultCurrentStepNum:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);
	   	
		//start video verify current step text is updated. nicole
		CurrentStepText="Scan \"Manual Clean Start\" to replay";
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		//CurrentStepText="Scan \"Manual Clean Start\" to replay";
		CurrentStepNum="Step 2";
		NextStepNum="Step 3.1";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 2";
		CurrentStepNum="Step 3.1";
		NextStepNum="Step 3.2";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.1";
		CurrentStepNum="Step 3.2";
		NextStepNum="Step 3.3";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.2";
		CurrentStepNum="Step 3.3";
		NextStepNum="Step 3.4";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.3";
		CurrentStepNum="Step 3.4";
		NextStepNum="Step 4";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.4";
		CurrentStepNum="Step 4";
		NextStepNum="Step 5.1";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 4";
		CurrentStepNum="Step 5.1";
		NextStepNum="Step 5.2";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 5.1";
		CurrentStepNum="Step 5.2";
		NextStepNum="Step 5.3";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 5.2";
		CurrentStepNum="Step 5.3";
		NextStepNum="Step 5.4";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 5.3";
		CurrentStepNum="Step 5.4";
		NextStepNum="Step 5.5";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 5.4";
		CurrentStepNum="Step 5.5";
		NextStepNum="Step 5.6";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 5.5";
		CurrentStepNum="Step 5.6";
		NextStepNum="Step 6";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 5.6";
		CurrentStepNum="Step 6";
		NextStepNum="Step 7";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 6";
		CurrentStepNum="Step 7";
		NextStepNum="Step 8";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 7";
		CurrentStepNum="Step 8";
		NextStepNum="Step 9";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   			
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 8";
		CurrentStepNum="Step 9";
		NextStepNum="Scan Manual Clean Complete";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		Message="All steps have been completed.";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyFinalMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		String FirstLineBottomMessage="You have completed the cleaning steps for this scope.";
		String SecondLineBottomMessage="Scan \"Manual Clean Complete \" to Continue your usual protocols.";		

		String ResultBottomMessage=LD_V.VerifyTJFBottomMessage(FirstLineBottomMessage,SecondLineBottomMessage);
		System.out.println(Scenario+" ResultBottomMessage:  "+ResultBottomMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMessage);

		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		Message="Scan the scope.";
		String StaffNameTop="Scan Staff ID";
		String StaffNameBottom="Scan Badge";
		String TopLT="Leak Test Not Started";
		String BottomLT="Scan Pass/Fail";
		String BottomMC="Scan Manual Clean Start";
		ScopeName="Scan the scope.";
		NoScopeScan="Scan Scope";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		String ResultTopScope=LD_V.VerifyTopScopeName(NoScopeScan);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		String ResultBottomScope=LD_V.VerifyBottomScopeName(NoScopeScan);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		String ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		String ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		String ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		String ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		String ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);

		LD_A.ClickHome();

		MultiFac_A.Change_Facility(Facility2Name);
		Thread.sleep(3000);

		
		for(int i=0;i<10;i++){
			InactiveLocationList[i]="zz";
		}
		NumInactiveLocation=0;

	   	ScanScope="F2 Scope7";
	   	ScopeSerialNumber="7654232";
	   	LocationID_PK=142;
	   	Scanner="F2 Sink 2";
	   	StaffID="T02";
	   	StaffFirstName="Tech2";
	   	StaffLastName="Tech2";
		CurrentStepNum="Step 1";
		CurrentStepText="Scan \"Manual Clean Start\" to play";
		NextStepNum="Step 2";
		NextStepText="Scan your Staff ID to proceed";
		PreviousStepNum="Step 1";
		PreviousStepText="Scan \"Go Back\" to previous";

   		IP_A.Click_LD();
   		//verify inactive location is disabled.


    	try{
			conn= DriverManager.getConnection(UAS.url,UAS.user,UAS.pass);		
			Statement statement = conn.createStatement();
			//get inactive locations for Facility 1

			stmt="Select Name from Location where IsActive=0 and LocationTypeID_FK=4 and FacilityID_FK=2;";
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
    	Description="Verify inactive items are disabled";
    	Expected="F2 Sink 3 is inactive";
    	
    	ResultInactiveLocation=LD_V.VerifyInactiveLocations(InactiveLocationList,NumInactiveLocation);
		System.out.println(Scenario+":  "+ResultInactiveLocation);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultInactiveLocation);

   		LD_A.ClickLocation(LocationID_PK);
    	Description="Verify default MCLD screen for "+Facility1Name+" "+Scanner;
    	Expected="The location and facility are displayed in the upper right.";

   		ResultLocationFacility=LD_V.VerifyLocationFacility(Scanner,Facility2Name);
		System.out.println(Scenario+":  "+ResultLocationFacility);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultLocationFacility);
		
    	Expected="The main massage is Scan the scope.";
   		Message="Scan the scope.";
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+":  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
   		   		
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
    	Description="Verify MCLD screen after scanning "+ScanScope;
    	Expected="The main massage is updated to Scan your badge and "+ScanScope+" is added to the header and progress bar.";

		Message="Scan your badge.";
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+":  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
	   		   	
   		StaffScan=StaffFirstName+" "+StaffLastName+"("+StaffID+")";
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
    	Description="Verify MCLD screen after scanning "+StaffScan;
    	Expected="The main massage is updated to conduct LT and scan result and "+StaffScan+" is added to the header and progress bar.";

    	Message="Conduct leak test then scan\n\"Leak Test Passed\"\nor \"Leak Test Failed\"";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+":  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
    	Description="Verify MCLD screen after scanning LT Pass";
    	Expected="The main massage is updated to scan MC start and Leak Test Pass is added to the header and progress bar.";

    	Message="Scan Manual Clean Start\nthen begin manual cleaning process.";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
    	Description="Verify MCLD screen after scanning MC Start";
    	Expected="The steps of the MCLD TJF-Q180V Manual cleaning procedure is displayed.";

		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultCurrentStepNum:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		//CurrentStepText="Scan \"Manual Clean Start\" to replay";
		CurrentStepNum="Step 2";
		NextStepNum="Step 3.1";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 2";
		CurrentStepNum="Step 3.1";
		NextStepNum="Step 3.2";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);

		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.1";
		CurrentStepNum="Step 3.2";
		NextStepNum="Step 3.3";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);

		//verify the go back scan		
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Go Back", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 2";
		CurrentStepNum="Step 3.1";
		NextStepNum="Step 3.2";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		CurrentStepText="Scan \"Manual Clean Start\" to replay";
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.1";
		CurrentStepNum="Step 3.2";
		NextStepNum="Step 3.3";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);

		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.2";
		CurrentStepNum="Step 3.3";
		NextStepNum="Step 3.4";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		//verify the go back scan		
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Go Back", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.1";
		CurrentStepNum="Step 3.2";
		NextStepNum="Step 3.3";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.2";
		CurrentStepNum="Step 3.3";
		NextStepNum="Step 3.4";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);
	   	
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		PreviousStepNum="Step 3.3";
		CurrentStepNum="Step 3.4";
		NextStepNum="Step 4";
		ResultCurrentStep=LD_V.VerifyCurrentStep(CurrentStepNum,CurrentStepText);
		System.out.println(Scenario+" ResultCurrentStep:  "+ResultCurrentStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCurrentStep);

		ResultNextStep=LD_V.VerifyNextStep(NextStepNum,NextStepText);
		System.out.println(Scenario+" ResultNextStep:  "+ResultNextStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultNextStep);

		ResultPrevStep=LD_V.VerifyPreviousStep(PreviousStepNum,PreviousStepText);
		System.out.println(Scenario+" ResultPrevStep:  "+ResultPrevStep);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPrevStep);

		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		Message="Scan the scope.";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName="Scan the scope.";
		NoScopeScan="Scan Scope";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(NoScopeScan);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(NoScopeScan);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);

		LD_A.ClickHome();

		LPA.Click_Logout();
		Thread.sleep(1000);

		if (UAS.resultFlag.contains("#Failed!#")) {
			org.testng.Assert.fail("Test has failed");
		}

		IHV.Close_Exec_Log(FileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		LP_A.CloseDriver();
		}
   
	  @AfterTest
	  public void PostTest() throws IOException{
	  	LP_A.CloseDriver();
	  }

}