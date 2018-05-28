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
import TestFrameWork.RegressionTest.Culture.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class CultureHappyPath_CycleMgmt {
	
	/**
	 * Nicole McKinley 8/2/2016 
	 * 
	 * Regression Test of Cycle Mgmt - Culture Happy path
	 * The Test will First initialize 2 scopes by scanning the whole way through the workflow
	 * This test will not verify anything with KE, bioburden
	 * The Test Data it will use
	 *  Scopes:  Scope1, Scope2 
	 *  
	 *  Location Scanners:
	 * 	-  Procedure Room:  Procedure Room 1
	 * 	-  Soiled Room:  Sink 1
	 * 	-  Reprocessing:  Reprocessor 1
	 * 	-  Cabinet:  Storage Area A (4 cabinets)
	 * 	-  Admin:  Administration
	 *  - CultureScan: CultureA
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
	public TestFrameWork.RegressionTest.Culture.CultureRegression_Actions Culture_A;
	public TestFrameWork.RegressionTest.Regression_Actions R_A;
	public TestFrameWork.RegressionTest.Regression_Verifications R_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public GeneralFunc GF;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestDataFunc TDF;

	
	public String OverallResult="Pass";	
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public String StartProcScan="Yes";
	public String EndProcScan="Yes";
	public String Room="";

	public String Reason1="New Scope", Reason2="New Scope";

	String [] temp= new String[2];

	public Boolean Res;
	
	public String Scope1="Scope1";
	public String Scope2="Scope2";
	public int Scope1ID;
	public int Scope2ID;

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
	public String Staff6="Tech6 Tech6(T06)";
	public String Staff6ID="T06";
	public String Phy="Physician1 Physician1(MD01)";
	public String Phy2="";
	
	public String PR1="Procedure Room 1";
	public String PR2="Procedure Room 2";
	public String Repro1="Reprocessor 1";
	public String Sink1="Sink 1";
	public String Sink2="Sink 2";
	public String StorageA="Storage Area A";
	public static String CultureScanner="CultureA";
	public int CycleCount=0;
	
	public String Patient="MRN111111";
	public String AdmitPatientBarcode="Yes";
	public String PhyScannedInWaiting="No";

	public String Culture1="No";
	public String CultureAssociationID1="0";
	public String Culture2="No";
	public String CultureAssociationID2="0";
	public String LTFail="Leak Test Fail";
	public String LTPass="Leak Test Pass";
	public String MRCPass="MRC Pass";
	public String MRCFail="MRC Fail";
	public String MCStart="Yes";
	public String MCEnd="Yes";
	public String CultureObtained="No";
	public String Cabinet="1";
	public String Description;
	public static Connection conn= null;
	public int NumAssociations=7;
	public int[] ExpectedScope1Cycle1= new int[NumAssociations];
	public int[] ExpectedScope2Cycle1= new int[NumAssociations];
	public int[] ExpectedScope1Cycle2= new int[NumAssociations];
	public int[] ExpectedScope2Cycle2= new int[NumAssociations];
	public int[] ExpectedScope2Cycle3= new int[NumAssociations];
	public int KE=0;
	public int Bioburden=0;
	public int Culture=1;
	

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, AWTException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		/*if (Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			Unifia_Admin_Selenium.setDriver(browserP);
			Unifia_Admin_Selenium.setEnvironment(URL);
		}else if(Unifia_Admin_Selenium.driverType.equalsIgnoreCase("GRID")){
			Unifia_Admin_Selenium.setGridDriver(browserP,URL);
		}*/
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		
		Unifia_Admin_Selenium.resultFlag="Pass";
		//Inserting DB data
		/*GF.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);*/
		
		//Insert MasterData
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
		
		for(int i=0; i<NumAssociations;i++){
			ExpectedScope1Cycle1[i]=999999999;
			ExpectedScope2Cycle1[i]=999999999;
			ExpectedScope1Cycle2[i]=999999999;
			ExpectedScope2Cycle2[i]=999999999;
			ExpectedScope2Cycle3[i]=999999999;
		}

		
		Unifia_Admin_Selenium.XMLFileName="CultureHappyPathCycleMgmt_Regression_";
		Unifia_Admin_Selenium.XMLFileName=IHV.Start_Exec_Log(Unifia_Admin_Selenium.XMLFileName);

		Unifia_Admin_Selenium.StepNum=1;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
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
		
		String[] InitialResult=R_A.InitialScans(Scope1, Scope2, ScanInfo);
		String Result=InitialResult[0];
		CycleCount++;
		ExpectedScope1Cycle1[0]=5;
		ExpectedScope2Cycle1[0]=5;
		ExpectedScope1Cycle1[1]=Integer.parseInt(InitialResult[1]);
		ExpectedScope1Cycle1[2]=Integer.parseInt(InitialResult[2]);
		ExpectedScope2Cycle1[1]=Integer.parseInt(InitialResult[1]);
		ExpectedScope2Cycle1[2]=Integer.parseInt(InitialResult[3]);
		ExpectedScope1Cycle1[3]=Integer.parseInt(InitialResult[4]);
		ExpectedScope2Cycle1[3]=Integer.parseInt(InitialResult[5]);
		ExpectedScope1Cycle1[4]=Integer.parseInt(InitialResult[6]);
		ExpectedScope2Cycle1[4]=Integer.parseInt(InitialResult[6]);
		ExpectedScope1Cycle1[5]=Integer.parseInt(InitialResult[7]);
		ExpectedScope2Cycle1[5]=Integer.parseInt(InitialResult[8]);

		
		Arrays.sort(ExpectedScope1Cycle1);
		Arrays.sort(ExpectedScope2Cycle1);

		
		temp=Result.split("-");
		OverallResult=GF.FinalResult(temp[0], Result, OverallResult);
		String Expected="Scopes "+Scope1+" and "+Scope2+" initial scans ending in "+StorageA;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallResult);

		Thread.sleep(60000); //Wait 1 minute before scanning the scope out of the storage area.
		
		//scan scope 1 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+StorageA);
		Description="Scan Scope "+Scope1+" out of "+StorageA;
		Culture1="No";
			
		String[] ResultStorageOutScope1=Storage_A.OutOfStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Culture1,CultureAssociationID1,Integer.toString(CycleCount));
		String StorageOut_AssocScope1=ResultStorageOutScope1[0];
		String OverallStorageOut_Result1=ResultStorageOutScope1[1];
		int[] ActualScope1Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount);

		String ResultScope1Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle1, ActualScope1Cycle1);
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
			
		String[] ResultStorageOutScope2=Storage_A.OutOfStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Culture2,CultureAssociationID2,Integer.toString(CycleCount));
		String StorageOut_AssocScope2=ResultStorageOutScope1[0];
		String OverallStorageOut_Result2=ResultStorageOutScope1[1];
		int[] ActualScope2Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount);

		String ResultScope2Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle1, ActualScope2Cycle1);
		temp=ResultScope2Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);

		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle1="+ResultScope2Cycle1);
		Result="ResultScope2Cycle1="+ResultScope2Cycle1;

		Expected="Scope "+Scope2+" was scanned out of "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		System.out.println("OverallResult"+OverallResult);
		Expected="Overall Result is expected to pass";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallResult);

		//scan scope 1 & 2 into PR1
		Unifia_Admin_Selenium.StepNum++;
		CycleCount++;
		Culture1="NA";
		Culture2="NA";

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+PR1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+PR1;
		
		String[] ResultPRScans=PR_A.PR_Scans(PR1,Scope1,Scope2,Staff2,Staff2ID,Phy,Phy2,AdmitPatientBarcode,Patient,StartProcScan,EndProcScan,Culture1,Integer.parseInt(CultureAssociationID1),Culture2,Integer.parseInt(CultureAssociationID2),Integer.toString(CycleCount));
		
		int PR_Assoc=Integer.parseInt(ResultPRScans[0]);
		String OverallPR_Result=ResultPRScans[1];
		temp=OverallPR_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallPR_Result, OverallResult);

		ExpectedScope1Cycle2[0]=1;
		ExpectedScope1Cycle2[1]=PR_Assoc;
		ExpectedScope2Cycle2[0]=1;
		ExpectedScope2Cycle2[1]=PR_Assoc;

		int[] ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount);
		int[] ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount);

		String ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		String ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);
		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2+". ResultScope2Cycle2="+ResultScope2Cycle2;

		Expected="Scopes "+Scope1+" and "+Scope2+" were used in "+PR1+". The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//scan scope 1 into Sink1
		Unifia_Admin_Selenium.StepNum++;
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+Sink1);
		Description="Scan Scopes "+Scope1+" into "+Sink1+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans1=Soiled_A.SoiledRoomScans(Sink1,Scope1,Staff3,Staff3ID,LTPass,MCStart,MCEnd,Integer.toString(CycleCount));
		int Scope1_SR_Assoc=Integer.parseInt(ResultSoiledScans1[0]);
		String OverallSoiled_Result=ResultSoiledScans1[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);
		
		ExpectedScope1Cycle2[0]=2;
		ExpectedScope1Cycle2[2]=Scope1_SR_Assoc;

		Arrays.sort(ExpectedScope1Cycle2);

		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount);
		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		
		Expected="Scopes "+Scope1+" was manually cleaned in "+Sink1+". The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope1Cycle2);

		
		//Step 5 scan scope 2 into Sink2
		Unifia_Admin_Selenium.StepNum++;
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+Sink2);
		Description="Scan Scopes "+Scope2+" into "+Sink2+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans2=Soiled_A.SoiledRoomScans(Sink2,Scope2,Staff4,Staff4ID,LTPass,MCStart,MCEnd,Integer.toString(CycleCount));
		int Scope2_SR_Assoc=Integer.parseInt(ResultSoiledScans2[0]);
		OverallSoiled_Result=ResultSoiledScans2[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);

		ExpectedScope2Cycle2[0]=2;
		ExpectedScope2Cycle2[2]=Scope2_SR_Assoc;
		
		Arrays.sort(ExpectedScope2Cycle2);
		
		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount);
		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Expected="Scopes "+Scope2+" was manually cleaned in "+Sink2+". The ScopeCycle table is correct for CycleCount="+CycleCount;
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

		System.out.println(Unifia_Admin_Selenium.StepNum+":  "+Result);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//scan scope 1 & 2 into reprocessor
		Unifia_Admin_Selenium.StepNum++;
		Reason1="Used in Procedure";
		Reason2="Used in Procedure";
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+Repro1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+Repro1;
		
		String[] ResultReproScans=Repro_A.ReprocessingRoomScans(Repro1,Scope1,Scope2,Staff1,Staff1ID,Reason1,Reason2,MRCPass,Integer.toString(CycleCount),Integer.toString(CycleCount));
		
		int Scope1InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		int Scope2InReproAssociationID=Integer.parseInt(ResultReproScans[1]);
		int MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		int Scope1OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		int Scope2OutReproAssociationID=Integer.parseInt(ResultReproScans[4]);
		String OverallReproResult2=ResultReproScans[5];
		String ResultScope1Reason=ResultReproScans[6];
		String ResultScope2Reason=ResultReproScans[7];
		temp=OverallReproResult2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallReproResult2, OverallResult);

		System.out.println("OverallResult="+OverallResult);

		ExpectedScope1Cycle2[0]=5;
		ExpectedScope1Cycle2[3]=Scope1InReproAssociationID;
		ExpectedScope1Cycle2[4]=MRCAssociationID;
		ExpectedScope1Cycle2[5]=Scope1OutReproAssociationID;

		ExpectedScope2Cycle2[0]=5;
		ExpectedScope2Cycle2[3]=Scope2InReproAssociationID;
		ExpectedScope2Cycle2[4]=MRCAssociationID;
		ExpectedScope2Cycle2[5]=Scope2OutReproAssociationID;
		Arrays.sort(ExpectedScope1Cycle2);
		Arrays.sort(ExpectedScope2Cycle2);

		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount);
		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);

		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2+". ResultScope1Reason="+ResultScope1Reason+". ResultScope2Cycle2="+ResultScope2Cycle2+". ResultScope2Reason="+ResultScope2Reason;

		Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro1+". The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//scan scope 1 with culture scanner - culture obtained. 
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" with "+CultureScanner);
		Description="Scan Scopes "+Scope1+" with "+CultureScanner;
		
		String[] ResultCultureObtainedScope1=Culture_A.CultureObtainedScans(CultureScanner,Scope1,Staff6,Staff6ID,Integer.toString(CycleCount)); //Storage_A.IntoStorageAreaScans(StorageA,Scope1,Staff5,Cabinet,CultureObtained,Integer.toString(CycleCount));
		String CultureObtained1="Yes";
		CultureAssociationID1=ResultCultureObtainedScope1[0];
		String OverallCultureObtained_Result1=ResultCultureObtainedScope1[1];
		temp=OverallCultureObtained_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallCultureObtained_Result1, OverallResult);
		
		ExpectedScope1Cycle2[0]=6;
		ExpectedScope1Cycle2[6]=Integer.parseInt(CultureAssociationID1);
		Arrays.sort(ExpectedScope1Cycle2);

		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);

		Expected="Scopes "+Scope1+" culture obtained with "+CultureScanner+". The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Expected, Description, ResultScope1Cycle2);

		//scan scope 2 with culture scanner - culture obtained. 
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" with "+CultureScanner);
		Description="Scan Scopes "+Scope2+" with "+CultureScanner;
		
		String[] ResultCultureObtainedScope2=Culture_A.CultureObtainedScans(CultureScanner,Scope2,Staff6,Staff6ID,Integer.toString(CycleCount)); //Storage_A.IntoStorageAreaScans(StorageA,Scope1,Staff5,Cabinet,CultureObtained,Integer.toString(CycleCount));
		
		CultureAssociationID2=ResultCultureObtainedScope2[0];
		String OverallCultureObtained_Result2=ResultCultureObtainedScope2[1];
		temp=OverallCultureObtained_Result2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallCultureObtained_Result2, OverallResult);
		String CultureObtained2="Yes";

		ExpectedScope2Cycle2[0]=6;
		ExpectedScope2Cycle2[6]=Integer.parseInt(CultureAssociationID2);
		Arrays.sort(ExpectedScope2Cycle2);

		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount);

		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);

		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);

		Expected="Scopes "+Scope2+" culture obtained with "+CultureScanner+". The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope2Cycle2);

		
		
		//scan scope 1 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+StorageA);
		Description="Scan Scopes "+Scope1+" into "+StorageA;
		
		String[] ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Cabinet,CultureObtained1,Integer.toString(CycleCount));
		
		String StorageIn_AssocScope1=ResultStorageInScope1[0];
		String OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);
		
		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		
		System.out.println("end Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2;

		Expected="Scopes "+Scope1+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//Step 9 scan scope 2 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+StorageA);
		Description="Scan Scopes "+Scope2+" into "+StorageA;
		
		ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Cabinet,CultureObtained2,Integer.toString(CycleCount));
		
		String StorageIn_AssocScope2=ResultStorageInScope1[0];
		OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);

		
		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount);

		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Result="ResultScope2Cycle2="+ResultScope2Cycle2;

		Expected="Scopes "+Scope2+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//Change the date for item history and scopestatus to -4 days
		try{ //Get a value that exists in Unifia to modify.
			//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    		Statement statement = conn.createStatement();
			String stmt="update ScopeStatus set LastUpdatedDateTime=DATEADD(Day,-4,LastUpdatedDateTime);\r\n"
					+ "update ItemHistory set LastUpdatedDateTime=DATEADD(Day,-4,LastUpdatedDateTime), ReceivedDateTime=DATEADD(Day,-4,LastUpdatedDateTime), ProcessedDateTime=DATEADD(Day,-4,LastUpdatedDateTime);\r\n"
					+ "update LocationStatus set LastUpdatedDateTime=DATEADD(Day,-4,LastUpdatedDateTime);";
			System.out.println(stmt);
			statement.executeUpdate(stmt);

			statement.close();

			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}

		//scan scope 1 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" out of "+StorageA+" and enter a culture result of "+Culture1);
		Description="Scan Scope "+Scope1+" out of "+StorageA;
		Culture1="Fail";
			
		ResultStorageOutScope1=Storage_A.OutOfStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Culture1,CultureAssociationID1,Integer.toString(CycleCount));
		StorageOut_AssocScope1=ResultStorageOutScope1[0];
		OverallStorageOut_Result1=ResultStorageOutScope1[1];
		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2;

		Expected="Scope "+Scope1+" was scanned out of "+StorageA+" and a culture result of "+Culture1+" was entered. The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//scan scope 2 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" out of "+StorageA+" without enter a culture result.");
		 
		Description="Scan Scope "+Scope2+" out of "+StorageA;
		Culture2="Yes";
			
		ResultStorageOutScope2=Storage_A.OutOfStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Culture2,CultureAssociationID2,Integer.toString(CycleCount));
		StorageOut_AssocScope2=ResultStorageOutScope1[0];
		OverallStorageOut_Result2=ResultStorageOutScope1[1];
		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount);

		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);

		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Result="ResultScope2Cycle2="+ResultScope2Cycle2;

		Expected="Scope "+Scope2+" was scanned out of "+StorageA+" without entering a culture result. The ScopeCycle table is correct for CycleCount="+CycleCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		System.out.println("OverallResult"+OverallResult);
		Expected="Overall Result is pass";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallResult);

		//scan scope 2 into PR1
		Unifia_Admin_Selenium.StepNum++;
		CycleCount++;
		Culture1="Pass";
		Culture2="";

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+PR1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+PR1;
		
		ResultPRScans=PR_A.PR_Scans(PR1,Scope2,"",Staff2,Staff2ID,Phy,Phy2,AdmitPatientBarcode,Patient,StartProcScan,EndProcScan,Culture1,Integer.parseInt(CultureAssociationID2),"",0,Integer.toString(CycleCount));
		
		PR_Assoc=Integer.parseInt(ResultPRScans[0]);
		OverallPR_Result=ResultPRScans[1];
		temp=OverallPR_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallPR_Result, OverallResult);

		ExpectedScope2Cycle3[0]=1;
		ExpectedScope2Cycle3[1]=PR_Assoc;

		int[] ActualScope2Cycle3=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount);


		String ResultScope2Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle3, ActualScope2Cycle3);
		temp=ResultScope2Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle3, OverallResult);
		
		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle3="+ResultScope2Cycle3);
		Result="ResultScope2Cycle3="+ResultScope2Cycle3;

		Expected="Scope "+Scope2+" was used in "+PR1+" and entered a culture result of "+Culture1+". The ScopeCycle table is correct for CycleCount="+CycleCount;
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
