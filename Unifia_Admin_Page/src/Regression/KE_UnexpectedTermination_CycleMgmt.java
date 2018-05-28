package Regression;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

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
import OER_Simulator.*;
public class KE_UnexpectedTermination_CycleMgmt {	
	/**
	 * Nicole McKinley 10/5/2016 
	 * 
	 * Regression Test of Cycle Mgmt - with KE and an unexpected termination 
	 * The Test will First initialize 2 scopes by scanning the whole way through the workflow
	 * This test will not verify anything with culture or bioburden
	 * 
	 * The Test Data it will use
	 *  Scopes:  Scope1, Scope2 
	 *  Location Scanners:
	 * 	-  Procedure Room:  Procedure Room 1
	 * 	-  Soiled Room:  Sink 1
	 * 	-  Reprocessing:  Reprocessor 1 - unexpected termination
	 * 	- reprocess again normally
	 * 	-  Cabinet:  Storage Area A (4 cabinets)
	 * 	-  Admin:  Administration
	 * 
	 * 	-  Staff1, Staff2, Staff3, Staff4, Staff5, Staff6
	 */
	
	public TestFrameWork.Emulator.GetIHValues IHV;
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.RegressionTest.ReprocessingRoom.ReprocessingRegression_Actions Repro_A;
	public TestFrameWork.RegressionTest.ProcedureRoom.PR_Regression_Actions PR_A;
	public TestFrameWork.RegressionTest.SoiledArea.SoiledRegression_Actions Soiled_A;
	public TestFrameWork.RegressionTest.StorageArea.StorageRegression_Actions Storage_A;
	public TestFrameWork.RegressionTest.Regression_Actions R_A;
	public TestFrameWork.RegressionTest.Regression_Verifications R_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public GeneralFunc GF;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public String OverallResult="Pass";	
	public OER_Simulator.OERGeneralFunc OER_GF;
	private TestDataFunc TDF;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;

	public String StartProcScan="Yes"; //perform the Start Procedure Scan
	public String EndProcScan="Yes"; //perform the End Procedure Scan
	public String Room="";

	public String Reason1="New Scope", Reason2="New Scope";

	String [] temp= new String[2];

	public Boolean Res;
	
	//Scope names used in this test
	public String Scope1="Scope1";
	public String Scope2="Scope2";
	public int Scope1ID; //Scope1 primary key
	public int Scope2ID; //Scope2 primary key
	public String Scope1SerialNo="1122334";
	public String Scope1Model="183";
	public String Scope2SerialNo="2233445";
	public String Scope2Model="184";
	
	
	public String Staff1="Tech1 Tech1(T01)";
	public String Staff1ID="T01";
	public String Staff2="Tech2 Tech2(T02)";
	public String Staff2ID="T02";
	public String Staff3="Tech3 Tech3(T03)";
	public String Staff3ID="T03";
	public String Staff4="Tech4 Tech4(T04)";
	public String Staff4ID="T04";
	public String Staff5="Tech5 Tech5(T05)";
	public String Staff5ID="T05";

	public String Phy="Physician1 Physician1(MD01)";
	public String Phy2="";

	//Location names used for scanning.
	public String PR1="Procedure Room 1"; 
	public String Repro1="Reprocessor 1";
	public String Repro2="Reprocessor 2";
	public String ReproRoom="ReprocessingRoom1";
	public String Sink1="Sink 1";
	public String Sink2="Sink 2";
	public String StorageA="Storage Area A";
	
	public int CycleCount1=0;  //Cycle count for scope1
	public int CycleCount2=0; //Cycle count for scope2
	public String Patient="MRN111111"; //PatientID
	public String AdmitPatientBarcode="Yes"; //Scan the Admit Patient barcode
	public String PhyScannedInWaiting="No";
	public String Culture1="NA"; //Culture was not obtained for scope1
	public String CultureAssociationID1="0"; //associationID if culture was obtained for scope1, since it was not obtained =0
	public String Culture2="NA"; //Culture was not obtained for scope2
	public String CultureAssociationID2="0";//associationID if culture was obtained for scope2, since it was not obtained =0
	public String LTFail="Leak Test Fail";
	public String LTPass="Leak Test Pass";
	public String MRCPass="MRC Pass";
	public String MRCFail="MRC Fail";
	public String MCStart="Yes"; //perform the Manual Clean Start Scan 
	public String MCEnd="Yes"; //perform the Manual Clean End Scan 
	public String CultureObtained="No";//culture is not obtained
	public String Cabinet="1"; //check the scopes into cabinet 1
	public String Description;
	public static Connection conn= null;
	public int NumAssociations=8;
	public int[] ExpectedScope1Cycle1= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle1. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle1= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle1. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope1Cycle2= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle2. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle2= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle2. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope1Cycle3= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle2. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle3= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle2. the first item specifies how many associationIDs there will be

	public int KE=1;
	public int Bioburden=0;
	public int Culture=0;
	
	public static int ScopeCnt=2;
	public static String CycleEnd="Unexpected Termination";
	public static String OERModel="OER-Pro";
	public static String OERSerialNum="2000002";
	public int Duration=5;
	String ResultScope1Cycle2;
	String ResultScope2Cycle2;


	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, ClassNotFoundException, SQLException, AWTException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.resultFlag="Pass";
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);

		GF.SyncRemoteMachineTime(Unifia_Admin_Selenium.KE_Env, Unifia_Admin_Selenium.KEMachine_Username, Unifia_Admin_Selenium.KEMachine_pswd, URL);
		//Inserting DB data
		//GF.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		//GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
		
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

		//OER_GF.InsertOracleLData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);

		Unifia_Admin_Selenium.Scope1ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope1ExpectedExamCount=0;
		Unifia_Admin_Selenium.Scope2ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope2ExpectedExamCount=0;
		Unifia_Admin_Selenium.Scope3ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope3ExpectedExamCount=0;
		Unifia_Admin_Selenium.Scope4ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope4ExpectedExamCount=0;

		Scope1ID=R_A.GetScopeID(Unifia_Admin_Selenium.connstring, Scope1);
		Scope2ID=R_A.GetScopeID(Unifia_Admin_Selenium.connstring, Scope2);
		
		for(int i=0; i<NumAssociations;i++){ //initialize the arrays for scope cycles
			ExpectedScope1Cycle1[i]=999999999;
			ExpectedScope2Cycle1[i]=999999999;
			ExpectedScope1Cycle2[i]=999999999;
			ExpectedScope2Cycle2[i]=999999999;
			ExpectedScope1Cycle3[i]=999999999;
			ExpectedScope2Cycle3[i]=999999999;
		}

		String Result;
		String Expected;
		int[] ActualScope1Cycle2;
		int[] ActualScope2Cycle2;
		Unifia_Admin_Selenium.XMLFileName="KE_UnexpectedTerminationCycleMgmt_Regression_";
		Unifia_Admin_Selenium.XMLFileName=IHV.Start_Exec_Log(Unifia_Admin_Selenium.XMLFileName);
		Unifia_Admin_Selenium.TestCaseNumber=1;

		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.StepNum=1;
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		
		Thread.sleep(500);
		//Start of the Test
		
		Description="Scan Scope "+Scope1+" and "+Scope2+" starting at reprocessor into PR, sink, repro and then check into "+StorageA+" to initilize the scopes";
		System.out.println("Starting Step:  "+Description);

		String[][] ScanInfo= new String[][]{
				{"Reprocessing1", Repro1,Staff1,Staff1ID, Reason1, Reason2,MRCPass},
				{"ProcedureRoom", PR1, Staff2,Staff2ID,Phy, AdmitPatientBarcode, Patient, StartProcScan, EndProcScan, Culture1, CultureAssociationID1, Culture2, CultureAssociationID2},
				{"Sink1",Sink1,Staff3,Staff3ID,LTPass, MCStart, MCEnd},
				{"Sink2",Sink2,Staff4,Staff4ID,LTPass, MCStart, MCEnd},
				{"Reprocessing2",Repro1,Staff1,Staff1ID, "Used in Procedure", "Used in Procedure",MRCPass},
				{"StorageIn1",StorageA,Staff5,Staff5ID,Cabinet,CultureObtained},
				{"StorageIn2",StorageA,Staff5,Staff5ID,Cabinet,CultureObtained},
			};

		CycleCount1++;
		CycleCount2++;
		String[] InitialResult=R_A.InitialScans(Scope1, Scope2, ScanInfo);
		Result=InitialResult[0];
		
		ExpectedScope1Cycle1[0]=5; //The number of associations in the ScopeCycle table for scope1 cycle1 
		ExpectedScope2Cycle1[0]=5; //The number of associations in the ScopeCycle table for scope2 cycle1
		ExpectedScope1Cycle1[1]=Integer.parseInt(InitialResult[1]); //Procedure Room AssociationID
		ExpectedScope1Cycle1[2]=Integer.parseInt(InitialResult[2]); //Scope1 Soiled Room AssociationID
		ExpectedScope2Cycle1[1]=Integer.parseInt(InitialResult[1]); //Procedure Room AssociationID
		ExpectedScope2Cycle1[2]=Integer.parseInt(InitialResult[3]); //Scope2 Soiled Room AssociationID
		ExpectedScope1Cycle1[3]=Integer.parseInt(InitialResult[4]); //Scope1 into Reprocessor AssociationID
		ExpectedScope2Cycle1[3]=Integer.parseInt(InitialResult[5]); //Scope2 into Reprocessor AssociationID
		ExpectedScope1Cycle1[4]=Integer.parseInt(InitialResult[6]); //MRC AssociationID
		ExpectedScope2Cycle1[4]=Integer.parseInt(InitialResult[6]); //MRC AssociationID
		ExpectedScope1Cycle1[5]=Integer.parseInt(InitialResult[7]); //Scope1 out of Reprocessor AssociationID
		ExpectedScope2Cycle1[5]=Integer.parseInt(InitialResult[8]); //Scope3 out of Reprocessor AssociationID
		
		Arrays.sort(ExpectedScope1Cycle1); 
		Arrays.sort(ExpectedScope2Cycle1);

		
		temp=Result.split("-");
		OverallResult=GF.FinalResult(temp[0], Result, OverallResult);
		Expected="Scopes "+Scope1+" and "+Scope2+" initial scans ending in "+StorageA;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallResult);

		Thread.sleep(60000); //Wait 1 minute before scanning the scope out of the storage area.
		
		//Step 2 scan scope 1 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+StorageA);
		Description="Scan Scope "+Scope1+" out of "+StorageA;
		Culture1="No"; //No culture obtained for scope
			
		String[] ResultStorageOutScope1=Storage_A.OutOfStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Culture1,CultureAssociationID1,Integer.toString(CycleCount1));
		String StorageOut_AssocScope1=ResultStorageOutScope1[0]; //AssociationID for Scope1 Storage Out scans
		String OverallStorageOut_Result1=ResultStorageOutScope1[1]; //overall pass/fail of storage out scans. 
		int[] ActualScope1Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);//get the associationIDs in ScopeCycle table for scope1 cycle 1

		String ResultScope1Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle1, ActualScope1Cycle1); //verify if the scopecycle table actual values match the expected values. 
		temp=ResultScope1Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle1, OverallResult);
		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle1="+ResultScope1Cycle1);
		Result="ResultScope1Cycle1="+ResultScope1Cycle1;

		Expected="Scope "+Scope1+" was scanned out of "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//scan scope 2 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" out of "+StorageA);
		Description="Scan Scope "+Scope2+" out of "+StorageA;
		Culture2="No";
			
		String[] ResultStorageOutScope2=Storage_A.OutOfStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Culture2,CultureAssociationID2,Integer.toString(CycleCount2));
		String StorageOut_AssocScope2=ResultStorageOutScope1[0]; //AssociationID for Scope1 Storage Out scans
		String OverallStorageOut_Result2=ResultStorageOutScope1[1];//overall pass/fail of storage out scans. 
		int[] ActualScope2Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		String ResultScope2Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle1, ActualScope2Cycle1);
		temp=ResultScope2Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);

		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle1="+ResultScope2Cycle1);
		Result="ResultScope2Cycle1="+ResultScope2Cycle1;

		Expected="Scope "+Scope2+" was scanned out of "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		System.out.println("OverallResult"+OverallResult);
		Expected="Overall Result is pass";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallResult);

		//scan scope 1 & 2 into PR1 - cycle count increases 
		Unifia_Admin_Selenium.StepNum++;
		CycleCount1++;
		CycleCount2++;
		Culture1="NA";
		Culture2="NA";

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+PR1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+PR1;
		
		String[] ResultPRScans=PR_A.PR_Scans(PR1,Scope1,Scope2,Staff2,Staff2ID,Phy,Phy2,AdmitPatientBarcode,Patient,StartProcScan,EndProcScan,Culture1,Integer.parseInt(CultureAssociationID1),Culture2,Integer.parseInt(CultureAssociationID2),Integer.toString(CycleCount1));
		
		int PR_Assoc=Integer.parseInt(ResultPRScans[0]); //Procedure Room associationID
		String OverallPR_Result=ResultPRScans[1];
		temp=OverallPR_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallPR_Result, OverallResult);

		ExpectedScope1Cycle2[0]=1;//there's one item in ScopeCycle table for scope1 cycle 1
		ExpectedScope1Cycle2[1]=PR_Assoc;
		ExpectedScope2Cycle2[0]=1;//there's one item in ScopeCycle table for scope2 cycle 1
		ExpectedScope2Cycle2[1]=PR_Assoc;

		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);
		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2+". ResultScope2Cycle2="+ResultScope2Cycle2;

		Expected="Scopes "+Scope1+" and "+Scope2+" were used in "+PR1+". The ScopeCycle table is correct for CycleCount="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//scan scope 1 into Sink1
		Unifia_Admin_Selenium.StepNum++;
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+Sink1);
		Description="Scan Scopes "+Scope1+" into "+Sink1+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans1=Soiled_A.SoiledRoomScans(Sink1,Scope1,Staff3,Staff3ID,LTPass,MCStart,MCEnd,Integer.toString(CycleCount1));
		int Scope1_SR_Assoc=Integer.parseInt(ResultSoiledScans1[0]);
		String OverallSoiled_Result=ResultSoiledScans1[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);
		
		ExpectedScope1Cycle2[0]=2;
		ExpectedScope1Cycle2[2]=Scope1_SR_Assoc;

		Arrays.sort(ExpectedScope1Cycle2);

		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		
		Expected="Scopes "+Scope1+" was manually cleaned in "+Sink1+". The ScopeCycle table is correct for CycleCount1="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope1Cycle2);

		
		//Step 5 scan scope 2 into Sink2
		Unifia_Admin_Selenium.StepNum++;
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+Sink2);
		Description="Scan Scopes "+Scope2+" into "+Sink2+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans2=Soiled_A.SoiledRoomScans(Sink2,Scope2,Staff4,Staff4ID,LTPass,MCStart,MCEnd,Integer.toString(CycleCount2));
		int Scope2_SR_Assoc=Integer.parseInt(ResultSoiledScans2[0]);
		OverallSoiled_Result=ResultSoiledScans2[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);

		ExpectedScope2Cycle2[0]=2;
		ExpectedScope2Cycle2[2]=Scope2_SR_Assoc;
		
		Arrays.sort(ExpectedScope2Cycle2);
		
		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);
		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Expected="Scopes "+Scope2+" was manually cleaned in "+Sink2+". The ScopeCycle table is correct for CycleCount2="+CycleCount2;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope2Cycle1);

		
		//set PR to available
		Unifia_Admin_Selenium.StepNum++;
		Room="Available";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Workflow Event", "", Room, "");
		System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PR1;
		Thread.sleep(8000);
		
		Expected="Room State is Avialable";
		String ExpectedRmSt="Available";
		
		String RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, PR1);
		Result=IHV.Result_Room_State(RmState, ExpectedRmSt, PR1);
		temp=Result.split("-");
		OverallResult=GF.FinalResult(temp[0], Result, OverallResult);

		System.out.println(Unifia_Admin_Selenium.StepNum+":  "+RmState);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, RmState);

		
		//scan scope 1 & 2 into reprocessor
		Unifia_Admin_Selenium.StepNum++;
		Reason1="Used in Procedure";
		Reason2="Used in Procedure";
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+Repro2);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+Repro2;
		
		String[] ResultReproScans=Repro_A.KEReprocessingRoomScans(ReproRoom,Repro2, Scope1, Scope2, Staff1, Staff1ID, Reason1, Reason2,MRCPass, Integer.toString(CycleCount1),Integer.toString(CycleCount2), OERModel, OERSerialNum,CycleEnd,
				ScopeCnt,Scope1SerialNo,Scope1Model,Scope2SerialNo,Scope2Model,Duration,"New");
		
		/*int Scope1InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		int Scope2InReproAssociationID=Integer.parseInt(ResultReproScans[1]);*/
		/*int Scope1OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		int Scope2OutReproAssociationID=Integer.parseInt(ResultReproScans[4]);*/
		int Scope1KEReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		int Scope2KEReproAssociationID=Integer.parseInt(ResultReproScans[1]);
		int MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		String OverallReproResult2=ResultReproScans[3];
		temp=OverallReproResult2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallReproResult2, OverallResult);

		System.out.println("OverallResult="+OverallResult);

		ExpectedScope1Cycle2[0]=4;
		/*ExpectedScope1Cycle2[3]=Scope1InReproAssociationID;*/
		ExpectedScope1Cycle2[4]=MRCAssociationID;
		/*ExpectedScope1Cycle2[5]=Scope1OutReproAssociationID;*/
		ExpectedScope1Cycle2[3]=Scope1KEReproAssociationID;

		ExpectedScope2Cycle2[0]=4;
		/*ExpectedScope2Cycle2[3]=Scope2InReproAssociationID;*/
		ExpectedScope2Cycle2[4]=MRCAssociationID;
		/*ExpectedScope2Cycle2[5]=Scope2OutReproAssociationID;*/
		ExpectedScope2Cycle2[3]=Scope2KEReproAssociationID;
		Arrays.sort(ExpectedScope1Cycle2);
		Arrays.sort(ExpectedScope2Cycle2);

		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);

		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2+". ResultScope2Cycle2="+ResultScope2Cycle2;

		Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro2+". The ScopeCycle table is correct for CycleCount="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//reprocess scopes a second time after the error on the reprocessor was corrected. 
		Unifia_Admin_Selenium.StepNum++;
		CycleCount1++;
		CycleCount2++;
		Reason1="Repeat Reprocessing";
		Reason2="Repeat Reprocessing";
		CycleEnd="Normal";
		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+Repro2);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+Repro2;
		
		ResultReproScans=Repro_A.KEReprocessingRoomScans(ReproRoom,Repro2, Scope1, Scope2, Staff1, Staff1ID, Reason1, Reason2,MRCPass, Integer.toString(CycleCount1),Integer.toString(CycleCount2), OERModel, OERSerialNum,CycleEnd,
				ScopeCnt,Scope1SerialNo,Scope1Model,Scope2SerialNo,Scope2Model,Duration,"Append");
		
		/*Scope1InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		Scope2InReproAssociationID=Integer.parseInt(ResultReproScans[1]);*/
		/*Scope1OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		Scope2OutReproAssociationID=Integer.parseInt(ResultReproScans[4]);*/
		Scope1KEReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		Scope2KEReproAssociationID=Integer.parseInt(ResultReproScans[1]);
		MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		OverallReproResult2=ResultReproScans[3];
		temp=OverallReproResult2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallReproResult2, OverallResult);

		System.out.println("OverallResult="+OverallResult);
		ExpectedScope1Cycle3[0]=2;
		/*ExpectedScope1Cycle3[1]=Scope1InReproAssociationID;*/
		ExpectedScope1Cycle3[1]=Scope1KEReproAssociationID;
		ExpectedScope1Cycle3[2]=MRCAssociationID;
		/*ExpectedScope1Cycle3[3]=Scope1OutReproAssociationID;*/
		

		ExpectedScope2Cycle3[0]=2;
		/*ExpectedScope2Cycle3[1]=Scope2InReproAssociationID;*/
		ExpectedScope2Cycle3[1]=Scope2KEReproAssociationID;
		ExpectedScope2Cycle3[2]=MRCAssociationID;
		/*ExpectedScope2Cycle3[3]=Scope2OutReproAssociationID;*/
		
		Arrays.sort(ExpectedScope1Cycle3);
		Arrays.sort(ExpectedScope2Cycle3);

		int[] ActualScope1Cycle3=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
		int[] ActualScope2Cycle3=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		String ResultScope1Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle3, ActualScope1Cycle3);
		temp=ResultScope1Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle3, OverallResult);

		String ResultScope2Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle3, ActualScope2Cycle3);
		temp=ResultScope2Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle3, OverallResult);

		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle3="+ResultScope1Cycle3);
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle3="+ResultScope2Cycle3);
		Result="ResultScope1Cycle3="+ResultScope1Cycle3+". ResultScope2Cycle3="+ResultScope2Cycle3;

		Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro2+". The ScopeCycle table is correct for "+Scope1+" "
				+ "CycleCount="+CycleCount1+". The ScopeCycle table is correct for "+Scope2+" "
				+ "CycleCount="+CycleCount2;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//scan scope 1 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+StorageA);
		Description="Scan Scopes "+Scope1+" into "+StorageA;
		
		String[] ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Cabinet,CultureObtained,Integer.toString(CycleCount1));
		
		String StorageIn_AssocScope1=ResultStorageInScope1[0];
		String OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);
		
		ActualScope1Cycle3=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);

		ResultScope1Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle3, ActualScope1Cycle3);
		temp=ResultScope1Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle3, OverallResult);

		
		System.out.println("end Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle3="+ResultScope1Cycle3);
		Result="ResultScope1Cycle3="+ResultScope1Cycle3;

		Expected="Scopes "+Scope1+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//Step 9 scan scope 2 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+StorageA);
		Description="Scan Scopes "+Scope2+" into "+StorageA;
		
		ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Cabinet,CultureObtained,Integer.toString(CycleCount2));
		
		String StorageIn_AssocScope2=ResultStorageInScope1[0];
		OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);

		
		ActualScope2Cycle3=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		ResultScope2Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle3, ActualScope2Cycle3);
		temp=ResultScope2Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle3, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle3="+ResultScope2Cycle3);
		Result="ResultScope2Cycle3="+ResultScope2Cycle3;

		Expected="Scopes "+Scope2+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount2;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (Unifia_Admin_Selenium.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	@AfterTest
	public void PostTTest() throws IOException{
		//System.out.println("The Test Case Run was:  "+TCResult);
		//IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, TCResult);
		LP_A.CloseDriver();
	}
}
