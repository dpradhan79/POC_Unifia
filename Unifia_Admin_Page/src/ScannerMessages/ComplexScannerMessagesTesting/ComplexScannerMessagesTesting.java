package ScannerMessages.ComplexScannerMessagesTesting;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.mysql.jdbc.Driver;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;


public class ComplexScannerMessagesTesting {
	
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
	public GeneralFunc gf;
	private TestDataFunc TDF;
	private ITConsole.ITConScanSimActions IT_A;
	public String Description,actualResult="\t\t\t\t ComplexScannerMessges \r\n";
	public String Scanner,ScopeName,Location,ScopeSerialNumber,HangTime;
	public String verifyMsg,ScanPatient;
	
	public Boolean Res;

	public Connection conn= null;
	public ResultSet Scope_rs,MCStart_rs,MCEnd_rs;
	
	public String TestResFileName="ComplexScannerMessges";
	public String ForFileName="";
	public String MCStart,stmt,AssociationID,MCEnd;
	public Date MCStartTime= new Date();
	public Date MCEndTime= new Date();
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	public String MCTimePeriod="";
	public String BioStatus,Status,ResStatus;
	public int KE=1;
	public int Bioburden=1;
	public int Culture=1;
	private String presWorkDir=System.getProperty("user.dir");
	
	private String fileDestFolder="\\XMLFolder";
	private String scenarioFilePath="TestData\\AppTestData";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	private String testScenarioXML="TestData\\AppTestData\\TestScenarios.xml";
	private String storageAreaXMLFile="StorageArea_ITConsole.xml";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void edge(String browserP, String URL, String AdminDB) throws Exception {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	
		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
		Unifia_Admin_Selenium.resultFlag="Pass";
		//DB  Updates		
		gf.SyncRemoteMachineTime(UAS.KE_Env, UAS.KEMachine_Username, UAS.KEMachine_pswd, URL);
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
		TDF.insertMasterData(UAS.url, UAS.user, UAS.pass,KE, Bioburden, Culture);
		try{
		   	String stmt1 = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
			String stmt2="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
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
		gf.RestartIISServices(UAS.Env, UAS.userName, UAS.IISPass);
		

		boolean isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, storageAreaXMLFile,fileSource,fileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+storageAreaXMLFile+" is copied to "+fileDestination);
		}else{
			System.out.println("#Failed!#- "+storageAreaXMLFile+" is not copied to "+fileDestination);
		}
		//Copying Runbatch.bat file to server machine
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.batchFile,fileSource,batchFileDestination);
									
		if(isFileCopied){
			System.out.println("Pass- "+UAS.batchFile+" is copied to "+batchFileDestination);
		}else{
			System.out.println("#Failed!#- "+UAS.batchFile+" is not copied to "+batchFileDestination);
		}
	
		//Update the environment and xml file in  Runbatch.bat file
		IT_A.modifyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.scanSimPath, UAS.batchFile,storageAreaXMLFile, xmlPath);
		//execute ScanSimUI
		IT_A.executeScanSim(UAS.Env, UAS.userName, UAS.IISPass,UAS.scanSimPathTool,UAS.ITConsoleExecTime,0);
		Thread.sleep(2000);
		// Run KE_UT
		TDF.insertKE_UT_Data(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		Thread.sleep(120000);
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
		Unifia_Admin_Selenium.ScannerCount=0;

		// Scenario#1 - Get the  KE Status failure scan messages 
		/*Description ="Start of new Scenario 1";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);*/
		ScopeName="Scope46";
		ScopeSerialNumber="2600815";
		ScanPatient="MRN999999";
		Scanner="CultureA";
		Description ="-----------------\nStart of new Scenario 1 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		// Culture Area
		e_ScanScope();
		verifyMsg="Culture Obtained";
		v_VerifyScanMsg();
		// Staff
		e_ScanStaff();
		verifyMsg="Staff T01 Scanned";
		v_VerifyScanMsg();
		// Storage Area Checkin 
		Scanner="Storage Area A";
		e_ScanScope();
		verifyMsg="Prior Scope Reprocessing Cycle Failed";
		v_VerifyScanMsg();
		// staff
		e_ScanStaff();
		verifyMsg="Staff T01 Scanned";
		v_VerifyScanMsg();
		// Storage Area Checkout
		Scanner="Storage Area A";
		e_ScanScope();
		e_KeyEntry("2");
		verifyMsg="Culture Fail";
		v_VerifyScanMsg();
		// Staff
		e_ScanStaff();
		verifyMsg="Staff T01 Scanned";
		v_VerifyScanMsg();
		
		// Procedure room check in (verify culture record msg
		Scanner="Procedure Room 1";
		e_ScanScope();
		verifyMsg="Check Scope Culturing Test Record";
		v_VerifyScanMsg();
		
		// Scenario#2 - Bioburden fail record with Manual Clean End
		TDF.insertMasterData(UAS.url, UAS.user, UAS.pass,KE, Bioburden, Culture);
		//Restart of IIS 
		gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);				
		//Close and start
		//UAS.driver.close();
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
		LP_A.CloseDriver();
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
		Unifia_Admin_Selenium.ScannerCount=0;
		ScopeName="Scope82";
		ScopeSerialNumber="5600819";
		HangTime="0";
		ScanPatient="MRN010101";
		Scanner="Storage Area D";
	
		Description ="-----------------\nStart of new Scenario 2 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	
		//Check in and out of the cabinet
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Enter Cabinet #";
		e_KeyEntry("1");
		e_ScanStaff();
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Hang Time "+ HangTime+" days";
		v_VerifyScanMsg();
		
		//Scope into Procedure
		Scanner="Procedure Room 1";
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Hang Time "+ HangTime+" days";
		v_VerifyScanMsg();
		//Scan Patient
		e_ScanPatient();
		verifyMsg="Patient "+ScanPatient+" Scanned";
		v_VerifyScanMsg();
		//preclean complete
		e_ScanScope();
		verifyMsg=ScopeSerialNumber +" Pre Clean Completed";
		v_VerifyScanMsg();
		
		//Scan scope into Soiled Room
		Scanner="Sink 1";	
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		e_LTPass();
		verifyMsg="Leak Test Passed";
		e_MCStart();
		verifyMsg=ScopeSerialNumber+" Manual Clean Started";
		v_VerifyScanMsg();
		e_MCEnd();
		v_ManualCleanEnd();
		//Bio Fail
		Scanner="Bioburden1";	
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		ResStatus="Fail";
		e_TestStatus(ResStatus);
		verifyMsg="Bioburden Test Fail";
		v_VerifyScanMsg();
		//Scan in Reprocessor
		Scanner="Reprocessor 1";
		e_ScanScope();
		verifyMsg="Check Scope Bioburden Test Record";
		v_VerifyScanMsg();
		
		// Scenario#3 - Bioburden fail record with no Manual Clean End
		
		ScopeName="Scope81";
		ScopeSerialNumber="5600818";
		HangTime="0";
		ScanPatient="MRN010101";
		Scanner="Storage Area A";
		
		Description ="-----------------\nStart of new Scenario 3 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	
		//Check in and out of the cabinet
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Enter Cabinet #";
		e_KeyEntry("1");
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Hang Time "+ HangTime+" days";
		v_VerifyScanMsg();
		
		//Scope into Procedure
		Scanner="Procedure Room 2";
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Hang Time "+ HangTime+" days";
		v_VerifyScanMsg();
		//Scan Patient
		e_ScanPatient();
		verifyMsg="Patient "+ScanPatient+" Scanned";
		v_VerifyScanMsg();
		//preclean complete
		e_ScanScope();
		verifyMsg=ScopeSerialNumber +" Pre Clean Completed";
		v_VerifyScanMsg();
		//Scan scope into Soiled Room
		Scanner="Sink 2";	
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		e_LTPass();
		verifyMsg="Leak Test Passed";
		e_MCStart();
		verifyMsg=ScopeSerialNumber+" Manual Clean Started";
		v_VerifyScanMsg();
		//Bio Fail
		Scanner="Bioburden1";	
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		ResStatus="Fail";
		e_TestStatus(ResStatus);
		verifyMsg="Bioburden Test Fail";
		v_VerifyScanMsg();
		//Scan in Reprocessor
		Scanner="Reprocessor 2";
		e_ScanScope();
		verifyMsg="Check Scope Bioburden Test Record";
		v_VerifyScanMsg();
		// Scenario#3b - check the scope bioburden Pass record with no Manual Clean End
	
		ScopeName="Scope80";
		ScopeSerialNumber="5600817";
		HangTime="0";
		ScanPatient="MRN010101";
		Scanner="Storage Area B";
		
		Description ="-----------------\nStart of new Scenario 3b ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		//Check in and out of the cabinet
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Enter Cabinet #";
		e_KeyEntry("1");
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Hang Time "+ HangTime+" days";
		v_VerifyScanMsg();
		
		//Scope into Procedure
		Scanner="Procedure Room 4";
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Hang Time "+ HangTime+" days";
		v_VerifyScanMsg();
		//Scan Patient
		e_ScanPatient();
		verifyMsg="Patient "+ScanPatient+" Scanned";
		v_VerifyScanMsg();
		//preclean complete
		e_ScanScope();
		verifyMsg=ScopeSerialNumber +" Pre Clean Completed";
		v_VerifyScanMsg();
		
		//Scan scope into Soiled Room
		Scanner="Sink 4";	
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		e_LTPass();
		verifyMsg="Leak Test Passed";
		e_MCStart();
		verifyMsg=ScopeSerialNumber+" Manual Clean Started";
		v_VerifyScanMsg();
		//Bio Fail
		Scanner="Bioburden1";	
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		ResStatus="Pass";
		e_TestStatus(ResStatus);
		verifyMsg="Bioburden Test Pass";
		v_VerifyScanMsg();
		//Scan in Reprocessor
		Scanner="Reprocessor 2";
		e_ScanScope();
		verifyMsg="Check Scope Manual Clean Record";
		v_VerifyScanMsg();
			
	// Scenario#4 - Bioburden Pass record with no Manual Clean End
	
		ScopeName="Scope79";
		ScopeSerialNumber="5600826";
		HangTime="0";
		ScanPatient="MRN010101";
		Scanner="Storage Area A";
		
		Description ="-----------------\nStart of new Scenario 4 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		//Check in and out of the cabinet
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Enter Cabinet #";
		e_KeyEntry("1");
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Hang Time "+ HangTime+" days";
		v_VerifyScanMsg();
		
		//Scope into Procedure
		Scanner="Procedure Room 5";
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Hang Time "+ HangTime+" days";
		v_VerifyScanMsg();
		//Scan Patient
		e_ScanPatient();
		verifyMsg="Patient "+ScanPatient+" Scanned";
		v_VerifyScanMsg();
		//preclean complete
		e_ScanScope();
		verifyMsg=ScopeSerialNumber +" Pre Clean Completed";
		v_VerifyScanMsg();
		
		//Scan scope into Soiled Room
		Scanner="Sink 1";	
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		e_LTPass();
		verifyMsg="Leak Test Passed";
		e_MCStart();
		verifyMsg=ScopeSerialNumber+" Manual Clean Started";
		v_VerifyScanMsg();
		e_MCEnd();
		v_ManualCleanEnd();
		//Bio Fail
		Scanner="Bioburden1";	
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		ResStatus="Pass";
		e_TestStatus(ResStatus);
		verifyMsg="Bioburden Test Pass";
		v_VerifyScanMsg();
		//Scan in Reprocessor
		Scanner="Reprocessor 2";
		e_ScanScope();
		verifyMsg="5600826 Reprocessing Started";
		v_VerifyScanMsg();

				
	//Scenario 5-Last manual clean > 60 and Bioburden Fail
		Description ="-----------------\nStart of new Scenario 5 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	
		ScopeName="Scope10";
		Scanner="Sink 2";
		ScopeSerialNumber="7654321";
		//Scope Scanned into Soiled Room
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		//Manual Clean Start
		e_MCStart();
		verifyMsg="Leak Test Pass or Fail?";
		v_VerifyScanMsg();
		//Manual Clean End
		e_MCEnd();
		v_ManualCleanEnd();
		//Bioburden Scan
		Scanner="Bioburden1";
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		//Bioburden test Fail scanned
		BioStatus="Fail";
		e_TestStatus(BioStatus);
		verifyMsg="Bioburden Test Fail";
		v_VerifyScanMsg();
		//Scope scanned into Reprocessor
		Scanner="Reprocessor 4";
		e_MoreThan1hr();
		verifyMsg="Check Scope Bioburden Test Record";
		v_VerifyScanMsg();

	//Scenario 6 - No Bioburden and no Manaul Clean 
		Description ="-----------------\nStart of new Scenario 6 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		ScopeName="Scope1";
		Scanner="Procedure Room 1";
		ScanPatient="MRN151515";
		ScopeSerialNumber="1122334";
		e_ScanRoomAvailable();

		//Scope Scanned into Procedure Room
		e_ScanScope();
		verifyMsg="Check Scope Reprocessing Record";
		v_VerifyScanMsg();
		//Scan Patient
		e_ScanPatient();
		verifyMsg="Patient "+ScanPatient+" Scanned";
		v_VerifyScanMsg();
		//preclean complete
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Pre Clean Completed";
		v_VerifyScanMsg();
		//Scope scanned into Reprocessor
		Scanner="Reprocessor 2";
		e_ScanScope();
		verifyMsg="Check Scope Manual Clean Record";
		v_VerifyScanMsg();
		
	//Scenario 7 - Bioburden pass and no manual clean
		Description ="-----------------\nStart of new Scenario 7 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		ScopeName="Scope3";
		Scanner="Procedure Room 2";
		ScanPatient="MRN111111";
		ScopeSerialNumber="3344556";
		e_ScanRoomAvailable();

		//Scope Scanned into Procedure Room
		e_ScanScope();
		verifyMsg="Check Scope Reprocessing Record";
		v_VerifyScanMsg();
		//Scan Patient
		e_ScanPatient();
		verifyMsg="Patient "+ScanPatient+" Scanned";
		v_VerifyScanMsg();
		//preclean complete
		Thread.sleep(30000);
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Pre Clean Completed";
		v_VerifyScanMsg();
		//Bioburden Scan
		Scanner="Bioburden1";
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		//Bioburden test Pass scanned
		BioStatus="Pass";
		e_TestStatus(BioStatus);
		verifyMsg="Bioburden Test Pass";
		v_VerifyScanMsg();
		//Scope scanned into Reprocessor
		Scanner="Reprocessor 3";
		Thread.sleep(30000);
		e_ScanScope();
		verifyMsg="Check Scope Manual Clean Record";
		v_VerifyScanMsg();
		
	//Scenario 8 - Culture fail and no previous reprocessing record
		Description ="-----------------\nStart of new Scenario 8 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		ScopeName="Scope4";
		Scanner="CultureA";
		ScopeSerialNumber="4455667";
		//culture scan
		e_ScanScope();
		verifyMsg="Culture obtained";
		v_VerifyScanMsg();
		//Scope checked into Storage area
		Scanner="Storage Area A";
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Enter Cabinet #";
		v_VerifyScanMsg();
		//cabinet selected
		e_KeyEntry("1");
		verifyMsg="Cabinet 1";
		v_VerifyScanMsg();
		//Scope checked out of Storage area
		e_ScanScope();
		verifyMsg="Results ? 1)Pass 2)Fail 3) No Results";
		v_VerifyScanMsg();
		//culture result entered
		e_KeyEntry("2");
		verifyMsg="Culture Fail";
		v_VerifyScanMsg();
		//Scope Scanned into Procedure Room
		Scanner="Procedure Room 3";
		e_ScanScope();
		verifyMsg="Check Scope Culturing Test Record";
		v_VerifyScanMsg();
		
	//Scenario 9 - Scope in PR w/o Pt assoc, No Scan in SR, Fail BB Test (BB Fail Msg)
		Description ="-----------------\nStart of new Scenario 9 ";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		ScopeName="Scope21";
		ScopeSerialNumber="2112233";
		Scanner="Procedure Room 4";
		//Scope Scanned into Procedure Room
		e_ScanScope();
		verifyMsg="Check Scope Reprocessing Record";
		v_VerifyScanMsg();
		//preclean complete
		Thread.sleep(30000);
		e_ScanScope();
		verifyMsg=ScopeSerialNumber+" Pre Clean Completed";
		v_VerifyScanMsg();
		//Bioburden Scan
		Scanner="Bioburden1";
		e_ScanScope();
		verifyMsg="Scope "+ScopeSerialNumber+" Scanned";
		v_VerifyScanMsg();
		//Bioburden test Fail scanned
		BioStatus="Fail";
		e_TestStatus(BioStatus);
		verifyMsg="Bioburden Test Fail";
		v_VerifyScanMsg();
		//Scope scanned into Reprocessor
		Scanner="Reprocessor 4";
		Thread.sleep(30000);
		e_ScanScope();
		verifyMsg="Check Scope Bioburden Test Record";
		v_VerifyScanMsg();
		
		if (actualResult.contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.contains("#Failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		
		//Close Browser
		LP_A.CloseDriver();
	}
	

	public void e_ScanScope() throws InterruptedException{
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScopeName, "");
		System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanRoomAvailable() throws InterruptedException{
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Available", "");
		System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanStaff() throws InterruptedException{
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech","Tech1 Tech1(T01)", "");
		System.out.println(Res);
		Description="Scan of Staff T01 is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanPatient() throws InterruptedException{
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Patient", "", ScanPatient, "");
		System.out.println(Res);
		Description="Scan of Patient '" +ScanPatient+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_VerifyScanMsg() throws InterruptedException{
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg(verifyMsg, Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult=actualResult+"\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTPass() throws InterruptedException{
		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Test Result - Leak Test Pass is done";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	

	public void e_MCStart() throws InterruptedException, ParseException{
		//Scan the Workflow Event
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		if(Res){
			MCStart="Scanned";
		}
	
		try{ //Get a value that exists in Unifia to modify.
			Thread.sleep(2000);
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select LastUpdatedDateTime,AssociationID_FK from ItemHistory where ScanItemTypeID_FK=7 and ScanItemID_FK =1 and LastUpdatedDateTime=(Select Max(LastUpdatedDateTime) from ItemHistory)"; //put sql statement here to find ID
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			MCStart_rs = statement.executeQuery(stmt);
			while(MCStart_rs.next()){
				MCStartTime = formatter.parse(MCStart_rs.getString(1));//the first variable in the set is the LastUpdatedDateTime row in the database.
				AssociationID=MCStart_rs.getString(2);//the Second variable in the set is the AssociationID row in the database.
			}		
			MCStart_rs.close();
			System.out.println("MC Start Time= "+MCStartTime);
			statement.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Workflow Event - Manual Clean Start is done";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MCEnd() throws InterruptedException, ParseException{
		Thread.sleep(60000);//waiting for 1 minute to get the time difference between Manual clean Start and Manual clean End
		//Scan the Workflow Event
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		if(Res){
			MCEnd="Scanned";
		}
		
		try{ //Get a value that exists in Unifia to modify.
			Thread.sleep(2000);
		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		stmt="select LastUpdatedDateTime from ItemHistory where ScanItemTypeID_FK=7 and ScanItemID_FK =2 and AssociationID_FK="+AssociationID+" and LastUpdatedDateTime=(Select Max(LastUpdatedDateTime) from ItemHistory where ScanItemTypeID_FK=7 and ScanItemID_FK =2)"; //put sql statement here to find ID
		System.out.println(stmt);
		Statement statement = conn.createStatement();
		MCEnd_rs = statement.executeQuery(stmt);
		while(MCEnd_rs.next()){
			MCEndTime = formatter.parse(MCEnd_rs.getString(1)); //the first variable in the set is the LastUpdatedDateTime row in the database.
		}		
		MCEnd_rs.close();
		System.out.println("MC End Time= "+MCEndTime);
		statement.close();
		conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Workflow Event - Manual Clean End is done";
		actualResult=actualResult+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
	public void v_ManualCleanEnd() throws InterruptedException{
		long diff=MCEndTime.getTime()-MCStartTime.getTime();//Calculating time difference between MCStartTime and MCEndTime
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
		if (diffInMinutes == 0) {
			MCTimePeriod = "00:00";
		} else if (diffInMinutes < 10) {
			MCTimePeriod = "00:0" + Long.toString(diffInMinutes);
		} else {
			Long Hours = diffInMinutes / 60;
			Long Minutes = diffInMinutes % 60;
			if (Hours < 10 && Minutes < 10) {
				MCTimePeriod = "0" + Long.toString(Hours) + ":0"
						+ Long.toString(Minutes);
			} else if (Hours > 10 && Minutes < 10) {
				MCTimePeriod = Long.toString(Hours) + ":0"
						+ Long.toString(Minutes);
			} else if (Hours > 10 && Minutes > 10) {
				MCTimePeriod = Long.toString(Hours)
						+ ":"+ Long.toString(Minutes);
			} else if (Hours < 10 && Minutes > 10) {
				MCTimePeriod = "0" + Long.toString(Hours) + ":"
						+ Long.toString(Minutes);
			}
		}
		
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg(ScopeSerialNumber+" Completed "+MCTimePeriod, Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult=actualResult+"\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
		public void e_TestStatus(String ResStatus) throws InterruptedException{
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			EM_A.ScanItem(Scanner, "Bioburden Testing", "", ResStatus, "");
			System.out.println(Res);
			Description="Scan of Bioburden "+ResStatus+" is done in "+ Scanner;
			actualResult=actualResult+"\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			BioStatus="Bioburden Test "+ResStatus;
			Status=ResStatus;
			}
		public void e_KeyEntry(String KE) throws InterruptedException{
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Scanner, "Key Entry", "", "", KE);
			System.out.println(Res);
			Description="Key Entry  option "+KE+" is selected  for scope  "+ ScopeName;
			actualResult=actualResult+"\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
		}			
		
		public void e_MoreThan1hr() throws InterruptedException{
			Boolean Res;
	   		try{ //Get a value that exists in Unifia to modify.
	   			Thread.sleep(2000);
	   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				Statement update = conn.createStatement();
				update.execute("update itemhistory set ReceivedDateTime = DATEADD(hh,-1,GETDATE()),LastUpdatedDateTime = DATEADD(hh,-1,GETDATE()), ProcessedDateTime = DATEADD(hh,-1,GETDATE()) where ScanItemId_FK=2 and ScanItemTypeID_FK=7  and ItemHistoryID_PK=(Select max (ItemhistoryID_PK) from itemhistory where scanItemID_fK=2 and ScanItemTypeID_FK=7 )");//Updating the time so as to get the time difference between Manual Clean End and scope scan
				update.close();
	   		}catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}		
	   		Res = EM_A.ScanItem(Scanner, "Scope", "", ScopeName, "");
			System.out.println(Res);
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Description="Scan of Scope '" +ScopeName+"' is done";
			actualResult=actualResult+"\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		 @AfterTest
	    public void PostTest() throws IOException{
	    	LP_A.CloseDriver();
	    }
}