package Regression;

import java.io.IOException;
import java.net.MalformedURLException;
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

import com.mysql.jdbc.Driver;

import ITConsole.ITConScanSimActions;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;
import TestFrameWork.RegressionTest.*;
import TestFrameWork.RegressionTest.ReprocessingRoom.*;
import TestFrameWork.RegressionTest.ProcedureRoom.*;
import TestFrameWork.RegressionTest.SoiledArea.*;
import TestFrameWork.RegressionTest.StorageArea.*;
import TestFrameWork.RegressionTest.Bioburden.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;

public class BioburdenHappyPath_CycleMgmt {
	/**
	 * Nicole McKinley 8/2/2016 
	 * 
	 * Regression Test of Cycle Mgmt - Bioburden Happy path
	 * The Test will First initialize 2 scopes by scanning the whole way through the workflow
	 * This test will not verify anything with KE or culture
	 * 
	 * The Test Data it will use
	 * 
	 *  Scopes:  Scope1, Scope2 
	 *  
	 *  Location Scanners:
	 * 	-  Procedure Room:  Procedure Room 1
	 * 	-  Soiled Room:  Sink 1
	 *  -  Bioburden: BioburdenA
	 * 	-  Reprocessing:  Reprocessor 1
	 * 	-  Cabinet:  Storage Area A (4 cabinets)
	 * 	-  Admin:  Administration
	 * 
	 * 	-  Staff1, Staff2, Staff3, Staff4, Staff5, Staff6
	 * 	-  
	 * 
	 */
	
	public TestFrameWork.Emulator.GetIHValues IHV;
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.RegressionTest.ReprocessingRoom.ReprocessingRegression_Actions Repro_A;
	public TestFrameWork.RegressionTest.ProcedureRoom.PR_Regression_Actions PR_A;
	public TestFrameWork.RegressionTest.SoiledArea.SoiledRegression_Actions Soiled_A;
	public TestFrameWork.RegressionTest.StorageArea.StorageRegression_Actions Storage_A;
	public TestFrameWork.RegressionTest.Bioburden.BioburdenRegression_Actions Bio_A;
	public TestFrameWork.RegressionTest.Regression_Actions R_A;
	public TestFrameWork.RegressionTest.Regression_Verifications R_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public GeneralFunc GF;
	private TestDataFunc TDF;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public TestFrameWork.RegressionTest.ExamQueue.ExamQueue_Regression_Actions EQ_A;
	public static TestFrameWork.Unifia_Admin_Selenium UAS;

	public String OverallResult="Pass";	
	
	public String StartProcScan="Yes";
	public String EndProcScan="Yes";
	public String Room="";
	public String ExpectedRmSt="";
	public String RmState="";

	public String Reason1="New Scope", Reason2="New Scope", Reason3="", Reason4="";

	String [] temp= new String[2];
	public Boolean Res;
	
	public String Scope1="Scope1";  //will be used for Bioburden Pass, scan Staff (no optional result)
	public String Scope2="Scope2"; //will be used for Bioburden Fail, Scan Blue, scan Staff, MC a second time (no LT), Bioburden Pass, scan red, staff
	public String Scope3="Scope3";  //will be used for Bioburden Pass, scan Staff (no optional result)
	public String Scope4="Scope4"; //will be used for Bioburden Fail, key entry of 123, scan Staff, MC a second time (no LT), Bioburden Pass, key entry of 42, staff
	public int Scope1ID;
	public int Scope2ID;
	public int Scope3ID;
	public int Scope4ID;

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

	public String Phy1="Physician1 Physician1(MD01)";
	public String Phy2="Physician2 Physician2(MD02)";
	String Exam="Colonoscopy";
	public String Waiting1="Waiting1";
	
	public String PR1="Procedure Room 1";
	public String PR2="Procedure Room 2";
	public String Repro1="Reprocessor 1";
	public String Sink1="Sink 1";
	public String Sink2="Sink 2";
	public String StorageA="Storage Area A";
	public String Bioburden="Bioburden1";
	public int Scope1CycleCount=0;
	public int Scope2CycleCount=0;
	public int Scope3CycleCount=0;
	public int Scope4CycleCount=0;
	
	public String Patient="MRN111111"; //PatientID
	public String AdmitPatientBarcode="Yes";
	
	public String Culture1="NA";
	public String CultureAssociationID1="0";
	public String Culture2="NA";
	public String CultureAssociationID2="0";
	public String Culture3="NA";
	public String CultureAssociationID3="0";
	public String Culture4="NA";
	public String CultureAssociationID4="0";

	public String LTFail="Leak Test Fail";
	public String LTPass="Leak Test Pass";
	public String MRCPass="MRC Pass";
	public String MRCFail="MRC Fail";
	public String MCStart="Yes";
	public String MCEnd="Yes";
	public String CultureObtained="No";
	public String CultureObtained1="No";
	public String CultureObtained2="No";
	public String Cabinet="1";
	public String BioPass="Pass"; 
	public String BioFail="Fail";
	public String BioOptional="";
	public String KeyOrScan=""; //should be "", "Key Entry" or "Scan"
	public int MRCAssociationID;
	public int KE=0;
	public int BioburdenEnabled=1;
	public int Culture=0;
	
	public String Description;
	public String Expected="";
	public String Result;
	public static Connection conn= null;
	public int NumAssociations=7;
	public int[] ExpectedScope1Cycle1= new int[NumAssociations];
	public int[] ExpectedScope1Cycle2= new int[NumAssociations];
	public int[] ExpectedScope2Cycle1= new int[NumAssociations];
	public int[] ExpectedScope2Cycle2= new int[NumAssociations];
	public int[] ExpectedScope2Cycle3= new int[NumAssociations];
	public int[] ExpectedScope3Cycle1= new int[NumAssociations];
	public int[] ExpectedScope3Cycle2= new int[NumAssociations];
	public int[] ExpectedScope4Cycle2= new int[NumAssociations];
	public int[] ExpectedScope4Cycle1= new int[NumAssociations];
	public int[] ExpectedScope4Cycle3= new int[NumAssociations];
	private ITConsole.ITConScanSimActions IT_A;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL,String AdminDB) throws Exception {
		//select the Driver type Grid or local
		if (UAS.parallelExecutionType && UAS.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		UAS.resultFlag="Pass";
		UAS.PhyScannedInWaiting=false;
		UAS.DriverSelection(browserP,URL,AdminDB);
		
		//GF.InsertSimulatedScanSeedData(UAS.url, UAS.user, UAS.pass,KE, BioburdenEnabled, Culture);
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
		TDF.insertMasterData(UAS.url, UAS.user, UAS.pass,KE, BioburdenEnabled, Culture);		
		GF.RestartIISServices(UAS.Env, UAS.userName, UAS.IISPass);
		
		UAS.Scope1ExpectedReproCount=0;
		UAS.Scope1ExpectedExamCount=0;
		UAS.Scope2ExpectedReproCount=0;
		UAS.Scope2ExpectedExamCount=0;
		UAS.Scope3ExpectedReproCount=0;
		UAS.Scope3ExpectedExamCount=0;
		UAS.Scope4ExpectedReproCount=0;
		UAS.Scope4ExpectedExamCount=0;
		
		Scope1ID=R_A.GetScopeID(UAS.connstring, Scope1);
		Scope2ID=R_A.GetScopeID(UAS.connstring, Scope2);
		Scope3ID=R_A.GetScopeID(UAS.connstring, Scope3);
		Scope4ID=R_A.GetScopeID(UAS.connstring, Scope4);

		for(int i=0; i<NumAssociations;i++){
			ExpectedScope1Cycle1[i]=999999999;
			ExpectedScope1Cycle2[i]=999999999;
			ExpectedScope2Cycle1[i]=999999999;
			ExpectedScope2Cycle2[i]=999999999;
			ExpectedScope2Cycle3[i]=999999999;
			ExpectedScope3Cycle1[i]=999999999;
			ExpectedScope3Cycle2[i]=999999999;
			ExpectedScope4Cycle1[i]=999999999;
			ExpectedScope4Cycle2[i]=999999999;
			ExpectedScope4Cycle3[i]=999999999;
		}
		
		UAS.XMLFileName="BioburdenHappyPathCycleMgmt_Regression_";
		UAS.XMLFileName=IHV.Start_Exec_Log(UAS.XMLFileName);
		UAS.TestCaseNumber=1;
		
		UAS.StepNum=1;
		LGPA.Launch_Unifia(UAS.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, UAS.roleMgr, UAS.userQV1, UAS.userQV1Pwd);
		UAS.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(UAS.Emulator_URL);
			
		
		Thread.sleep(500);

		//Start of the Test
		String[][] ScanInfo= new String[][]{
				{"Reprocessing1", Repro1,Staff1,Staff1ID, Reason1, Reason2,MRCPass},
				{"ProcedureRoom", PR1, Staff2,Staff2ID,Phy1, AdmitPatientBarcode, Patient, StartProcScan, EndProcScan, Culture1, CultureAssociationID1, Culture2, CultureAssociationID2},
				{"Sink1",Sink1,Staff3,Staff3ID,LTPass, MCStart, MCEnd},
				{"Sink2",Sink2,Staff4,Staff4ID,LTPass, MCStart, MCEnd},
				{"Reprocessing2",Repro1,Staff1,Staff1ID, "Used in Procedure", "Used in Procedure",MRCPass},
				{"StorageIn1",StorageA,Staff5,Staff5ID,Cabinet,CultureObtained},
				{"StorageIn2",StorageA,Staff5,Staff5ID,Cabinet,CultureObtained},
			};

		Description="Scan Scope "+Scope1+" and "+Scope2+" starting at reprocessor into PR, sink, repro and then check into "+StorageA+" to initilize the scopes";
		System.out.println("Starting Step:  "+Description);

		Scope1CycleCount++;
		Scope2CycleCount++;
		
		String[] InitialResult=R_A.InitialScans(Scope1, Scope2, ScanInfo);
		Result=InitialResult[0];
		
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
		Expected="Scopes "+Scope1+" and "+Scope2+" initial scans ending in "+StorageA;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, OverallResult);
		
		Description="Scan Scope "+Scope3+" and "+Scope4+" starting at reprocessor into PR, sink, repro and then check into "+StorageA+" to initilize the scopes";

		Scope3CycleCount++;
		Scope4CycleCount++;
			
		String[] InitialResult2=R_A.InitialScans(Scope3, Scope4, ScanInfo);
		String Result2=InitialResult2[0];
		
		ExpectedScope3Cycle1[0]=5;
		ExpectedScope4Cycle1[0]=5;
		ExpectedScope3Cycle1[1]=Integer.parseInt(InitialResult2[1]);
		ExpectedScope3Cycle1[2]=Integer.parseInt(InitialResult2[2]);
		ExpectedScope4Cycle1[1]=Integer.parseInt(InitialResult2[1]);
		ExpectedScope4Cycle1[2]=Integer.parseInt(InitialResult2[3]);
		ExpectedScope3Cycle1[3]=Integer.parseInt(InitialResult2[4]);
		ExpectedScope4Cycle1[3]=Integer.parseInt(InitialResult2[5]);
		ExpectedScope3Cycle1[4]=Integer.parseInt(InitialResult2[6]);
		ExpectedScope4Cycle1[4]=Integer.parseInt(InitialResult2[6]);
		ExpectedScope3Cycle1[5]=Integer.parseInt(InitialResult2[7]);
		ExpectedScope4Cycle1[5]=Integer.parseInt(InitialResult2[8]);
		
		Arrays.sort(ExpectedScope3Cycle1);
		Arrays.sort(ExpectedScope4Cycle1);
	
		temp=Result2.split("-");
			OverallResult=GF.FinalResult(temp[0], Result2, OverallResult);
			Expected="Scopes "+Scope3+" and "+Scope4+" initial scans ending in "+StorageA;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, OverallResult);

		Thread.sleep(30000); //Wait 1 minute before scanning the scope out of the storage area.
		
		//scan scope 1 out of Storage Area A
		UAS.StepNum++;

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope1+" into "+StorageA);
		Description="Scan Scope "+Scope1+" out of "+StorageA;
		Culture1="No";
			
		String[] ResultStorageOutScope1=Storage_A.OutOfStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Culture1,CultureAssociationID1,Integer.toString(Scope1CycleCount));
		String StorageOut_AssocScope1=ResultStorageOutScope1[0];
		String OverallStorageOut_Result1=ResultStorageOutScope1[1];
		int[] ActualScope1Cycle1=R_A.GetCycleIDs(UAS.connstring, Scope1ID, Scope1CycleCount);

		String ResultScope1Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle1, ActualScope1Cycle1);
		temp=ResultScope1Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle1, OverallResult);
		
		System.out.println("Starting Step:  "+UAS.StepNum+" ResultScope1Cycle1="+ResultScope1Cycle1);
		Result="ResultScope1Cycle1="+ResultScope1Cycle1;

		Expected="Scope "+Scope1+" was scanned out of "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		//scan scope 2 out of Storage Area A
		UAS.StepNum++;

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope2+" out of "+StorageA);
		Description="Scan Scope "+Scope2+" out of "+StorageA;
		Culture2="No";
			
		String[] ResultStorageOutScope2=Storage_A.OutOfStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Culture2,CultureAssociationID2,Integer.toString(Scope2CycleCount));
		String StorageOut_AssocScope2=ResultStorageOutScope1[0];
		String OverallStorageOut_Result2=ResultStorageOutScope1[1];
		int[] ActualScope2Cycle1=R_A.GetCycleIDs(UAS.connstring, Scope2ID, Scope2CycleCount);

		String ResultScope2Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle1, ActualScope2Cycle1);
		temp=ResultScope2Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);

		
		System.out.println("Starting Step:  "+UAS.StepNum+" ResultScope2Cycle1="+ResultScope2Cycle1);
		Result="ResultScope2Cycle1="+ResultScope2Cycle1;

		Expected="Scope "+Scope2+" was scanned out of "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);
		System.out.println("OverallResult"+OverallResult);
		Expected="Overall Result is expected to pass";
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, OverallResult);

		//scan scope 1 & 2 into PR1
		UAS.StepNum++;
		Scope1CycleCount++;
		Scope2CycleCount++;
		Culture1="NA";
		Culture2="NA";

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope1+" and "+Scope2+" into "+PR1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+PR1;
		UAS.PhyScannedInWaiting=true;

		//NM need to update PR_Scans to have 2 cycle counts.
		EQ_A.EQ_Scans(Waiting1,Exam,Phy1,Phy2,Patient);

		
		String[] ResultPRScans=PR_A.PR_Scans(PR1,Scope1,Scope2,Staff2,Staff2ID,Phy1,Phy2,AdmitPatientBarcode,Patient,StartProcScan,EndProcScan,Culture1,Integer.parseInt(CultureAssociationID1),Culture2,Integer.parseInt(CultureAssociationID2),Integer.toString(Scope1CycleCount));
		
		int PR_Assoc=Integer.parseInt(ResultPRScans[0]);
		String OverallPR_Result=ResultPRScans[1];
		temp=OverallPR_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallPR_Result, OverallResult);

		ExpectedScope1Cycle2[0]=1;
		ExpectedScope1Cycle2[1]=PR_Assoc;
		ExpectedScope2Cycle2[0]=1;
		ExpectedScope2Cycle2[1]=PR_Assoc;

		int[] ActualScope1Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope1ID, Scope1CycleCount);
		int[] ActualScope2Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope2ID, Scope2CycleCount);

		String ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		String ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);
		
		System.out.println("Starting Step:  "+UAS.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		System.out.println("Starting Step:  "+UAS.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2+". ResultScope2Cycle2="+ResultScope2Cycle2;

		Expected="Scopes "+Scope1+" and "+Scope2+" were used in "+PR1+". The ScopeCycle table is correct for CycleCount="+Scope1CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		//scan scope 1 into Sink1
		UAS.StepNum++;
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope1+" into "+Sink1);
		Description="Scan Scopes "+Scope1+" into "+Sink1+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans1=Soiled_A.SoiledRoomScans(Sink1,Scope1,Staff3,Staff3ID,LTPass,MCStart,MCEnd,Integer.toString(Scope1CycleCount));
		int Scope1_SR_Assoc=Integer.parseInt(ResultSoiledScans1[0]);
		String OverallSoiled_Result=ResultSoiledScans1[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);
		
		ExpectedScope1Cycle2[0]=2;
		ExpectedScope1Cycle2[2]=Scope1_SR_Assoc;

		Arrays.sort(ExpectedScope1Cycle2);

		ActualScope1Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope1ID, Scope1CycleCount);
		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		
		Expected="Scopes "+Scope1+" was manually cleaned in "+Sink1+". The ScopeCycle table is correct for CycleCount="+Scope1CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope1Cycle2);

		
		//scan scope 2 into Sink2
		UAS.StepNum++;
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope2+" into "+Sink2);
		Description="Scan Scopes "+Scope2+" into "+Sink2+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans2=Soiled_A.SoiledRoomScans(Sink2,Scope2,Staff4,Staff4ID,LTPass,MCStart,MCEnd,Integer.toString(Scope2CycleCount));
		int Scope2_SR_Assoc=Integer.parseInt(ResultSoiledScans2[0]);
		OverallSoiled_Result=ResultSoiledScans2[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);

		ExpectedScope2Cycle2[0]=2;
		ExpectedScope2Cycle2[2]=Scope2_SR_Assoc;
		
		Arrays.sort(ExpectedScope2Cycle2);
		
		ActualScope2Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope2ID, Scope2CycleCount);
		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Expected="Scopes "+Scope2+" was manually cleaned in "+Sink2+". The ScopeCycle table is correct for CycleCount="+Scope2CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope2Cycle1);

		
		//Scan scope1 with Bioburden scanner and with a test status =Pass. no optional result entered.
		UAS.StepNum++;
		KeyOrScan="";
		BioOptional="";
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope1+" with bioburden scanner="+Bioburden);
		Description="Scan Scopes "+Scope1+" with "+Bioburden+" and Test Status="+BioPass+"; Optional Test Result="+BioOptional+" and Staff="+Staff6;
		
		String[] ResultBioScans1=Bio_A.BioburdenScans(Bioburden,Scope1,BioPass,KeyOrScan,BioOptional,Staff6,Staff6ID,Integer.toString(Scope1CycleCount));
		int Scope1_Bio_Assoc=Integer.parseInt(ResultBioScans1[0]);
		String OverallBio_Result1=ResultBioScans1[1];
		temp=OverallBio_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallBio_Result1, OverallResult);
		
		ExpectedScope1Cycle2[0]=3;
		ExpectedScope1Cycle2[3]=Scope1_Bio_Assoc;

		Arrays.sort(ExpectedScope1Cycle2);
		
		ActualScope1Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope1ID, Scope1CycleCount);
		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		
		Expected="Scopes "+Scope1+" Bioburden Tested with "+Bioburden+". The ScopeCycle table is correct for CycleCount="+Scope1CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope1Cycle2);


		//Scan scope2 with Bioburden scanner and with a test status =Fail. optional result=Blue.
		UAS.StepNum++;
		KeyOrScan="Scan";
		BioOptional="Blue";
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope2+" with bioburden scanner="+Bioburden);
		Description="Scan Scopes "+Scope2+" with "+Bioburden+" and Test Status="+BioFail+"; Optional Test Result="+BioOptional+" and Staff="+Staff6;
		
		String[] ResultBioScans2=Bio_A.BioburdenScans(Bioburden,Scope2,BioFail,KeyOrScan,BioOptional,Staff6,Staff6ID,Integer.toString(Scope2CycleCount));
		int Scope2_Bio_Assoc=Integer.parseInt(ResultBioScans2[0]);
		String OverallBio_Result2=ResultBioScans2[1];
		temp=OverallBio_Result2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallBio_Result2, OverallResult);
		
		ExpectedScope2Cycle2[0]=3;
		ExpectedScope2Cycle2[3]=Scope2_Bio_Assoc;

		Arrays.sort(ExpectedScope2Cycle2);
		
		ActualScope2Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope2ID, Scope2CycleCount);
		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		
		Expected="Scope "+Scope2+" Bioburden Tested with "+Bioburden+". The ScopeCycle table is correct for CycleCount="+Scope2CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope2Cycle2);
		
		//scan scope 2 into Sink2
		UAS.StepNum++;
		Scope2CycleCount++;
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope2+" into "+Sink2);
		Description="Scan Scopes "+Scope2+" into "+Sink2+" and perform Leak Test and Manual Cleaning.";
		
		ResultSoiledScans2=Soiled_A.SoiledRoomScans(Sink2,Scope2,Staff4,Staff4ID,LTPass,MCStart,MCEnd,Integer.toString(Scope2CycleCount));
		Scope2_SR_Assoc=Integer.parseInt(ResultSoiledScans2[0]);
		OverallSoiled_Result=ResultSoiledScans2[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);

		ExpectedScope2Cycle3[0]=1;
		ExpectedScope2Cycle3[1]=Scope2_SR_Assoc;
		
		Arrays.sort(ExpectedScope2Cycle2);
		
		int[] ActualScope2Cycle3=R_A.GetCycleIDs(UAS.connstring, Scope2ID, Scope2CycleCount);
		String ResultScope2Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle3, ActualScope2Cycle3);
		temp=ResultScope2Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle3, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope2Cycle3="+ResultScope2Cycle3);
		Expected="Scopes "+Scope2+" was manually cleaned in "+Sink2+". The ScopeCycle table is correct for CycleCount="+Scope2CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope2Cycle3);

		//Scan scope2 with Bioburden scanner and with a test status =Pass. optional result =Red.
		UAS.StepNum++;
		KeyOrScan="Scan";
		BioOptional="Red";
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope2+" with bioburden scanner="+Bioburden);
		Description="Scan Scopes "+Scope2+" with "+Bioburden+" and Test Status="+BioPass+"; Optional Test Result="+BioOptional+" and Staff="+Staff6;
		
		ResultBioScans2=Bio_A.BioburdenScans(Bioburden,Scope2,BioPass,KeyOrScan,BioOptional, Staff6,Staff6ID,Integer.toString(Scope2CycleCount));
		Scope2_Bio_Assoc=Integer.parseInt(ResultBioScans2[0]);
		OverallBio_Result2=ResultBioScans2[1];
		temp=OverallBio_Result2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallBio_Result2, OverallResult);
		
		ExpectedScope2Cycle3[0]=2;
		ExpectedScope2Cycle3[2]=Scope2_Bio_Assoc;

		Arrays.sort(ExpectedScope2Cycle3);
		
		ActualScope2Cycle3=R_A.GetCycleIDs(UAS.connstring, Scope2ID, Scope2CycleCount);
		ResultScope2Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle3, ActualScope2Cycle3);
		temp=ResultScope2Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle3, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope2Cycle3="+ResultScope2Cycle3);
		
		Expected="Scope "+Scope2+" Bioburden Tested with "+Bioburden+". The ScopeCycle table is correct for CycleCount="+Scope2CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope2Cycle3);
		
		
		//set PR to available
		UAS.StepNum++;
		Room="Available";
		UAS.ScannerCount=UAS.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Workflow Event", "", Room, "");
		System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PR1;
		Thread.sleep(8000);
		
		Expected="Room State is Avialable";
		ExpectedRmSt="Available";
		
		RmState=IHV.Room_State(UAS.connstring, PR1);
		Result=IHV.Result_Room_State(RmState, ExpectedRmSt, PR1);
		temp=Result.split("-");
		OverallResult=GF.FinalResult(temp[0], Result, OverallResult);

		System.out.println(UAS.StepNum+":  "+Result);
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		
		//scan scope 1 & 2 into reprocessor
		UAS.StepNum++;
		Reason1="Used in Procedure";
		Reason2="";
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope1+" and "+Scope2+" into "+Repro1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+Repro1;
		
		String[] ResultReproScans=Repro_A.ReprocessingRoomScans(Repro1,Scope1,Scope2,Staff1,Staff1ID,Reason1,Reason2,MRCPass,Integer.toString(Scope1CycleCount),Integer.toString(Scope2CycleCount));
		
		int Scope1InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		int Scope2InReproAssociationID=Integer.parseInt(ResultReproScans[1]);
		MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		int Scope1OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		int Scope2OutReproAssociationID=Integer.parseInt(ResultReproScans[4]);
		String OverallReproResult2=ResultReproScans[5];
		temp=OverallReproResult2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallReproResult2, OverallResult);

		System.out.println("OverallResult="+OverallResult);

		ExpectedScope1Cycle2[0]=6;
		ExpectedScope1Cycle2[4]=Scope1InReproAssociationID;
		ExpectedScope1Cycle2[5]=MRCAssociationID;
		ExpectedScope1Cycle2[6]=Scope1OutReproAssociationID;

		ExpectedScope2Cycle3[0]=5;
		ExpectedScope2Cycle3[3]=Scope2InReproAssociationID;
		ExpectedScope2Cycle3[4]=MRCAssociationID;
		ExpectedScope2Cycle3[5]=Scope2OutReproAssociationID;
		Arrays.sort(ExpectedScope1Cycle2);
		Arrays.sort(ExpectedScope2Cycle3);

		ActualScope1Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope1ID, Scope1CycleCount);
		ActualScope2Cycle3=R_A.GetCycleIDs(UAS.connstring, Scope2ID, Scope2CycleCount);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		ResultScope2Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle3, ActualScope2Cycle3);
		temp=ResultScope2Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle3, OverallResult);

		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope2Cycle3="+ResultScope2Cycle3);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2+". ResultScope2Cycle3="+ResultScope2Cycle3;

		Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro1+". The ScopeCycle table is correct for "+Scope1+""
				+ " CycleCount="+Scope1CycleCount+" and "+Scope2+" CycleCount="+Scope2CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		//scan scope 1 into Storage Area A
		UAS.StepNum++;

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope1+" into "+StorageA);
		Description="Scan Scopes "+Scope1+" into "+StorageA;
		
		String[] ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Cabinet,CultureObtained1,Integer.toString(Scope1CycleCount));
		
		String StorageIn_AssocScope1=ResultStorageInScope1[0];
		String OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);
		
		ActualScope1Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope1ID, Scope1CycleCount);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		
		System.out.println("end Step:  "+UAS.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2;

		Expected="Scopes "+Scope1+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+Scope1CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		
		//Step 9 scan scope 2 into Storage Area A
		UAS.StepNum++;

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope2+" into "+StorageA);
		Description="Scan Scopes "+Scope2+" into "+StorageA;
		
		ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Cabinet,CultureObtained2,Integer.toString(Scope2CycleCount));
		
		String StorageIn_AssocScope2=ResultStorageInScope1[0];
		OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);

		
		ActualScope2Cycle3=R_A.GetCycleIDs(UAS.connstring, Scope2ID, Scope2CycleCount);

		ResultScope2Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle3, ActualScope2Cycle3);
		temp=ResultScope2Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle3, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope2Cycle3="+ResultScope2Cycle3);
		Result="ResultScope2Cycle3="+ResultScope2Cycle3;

		Expected="Scopes "+Scope2+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+Scope2CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		// Scope 3 and 4 used to verify key entry for bioburden 
		//scan scope 3 out of Storage Area A
		UAS.StepNum++;

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope3+" into "+StorageA);
		Description="Scan Scope "+Scope3+" out of "+StorageA;
		Culture3="No";
			
		String[] ResultStorageOutScope3=Storage_A.OutOfStorageAreaScans(StorageA,Scope3,Staff5,Staff5ID,Culture3,CultureAssociationID3,Integer.toString(Scope3CycleCount));
		String StorageOut_AssocScope3=ResultStorageOutScope3[0];
		String OverallStorageOut_Result3=ResultStorageOutScope3[1];
		int[] ActualScope3Cycle1=R_A.GetCycleIDs(UAS.connstring, Scope3ID, Scope3CycleCount);

		String ResultScope3Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle1, ActualScope3Cycle1);
		temp=ResultScope3Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle1, OverallResult);

		
		System.out.println("Starting Step:  "+UAS.StepNum+" ResultScope3Cycle1="+ResultScope3Cycle1);
		Result="ResultScope3Cycle1="+ResultScope3Cycle1;

		Expected="Scope "+Scope3+" was scanned out of "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		//scan scope 4 out of Storage Area A
		UAS.StepNum++;

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope4+" out of "+StorageA);
		Description="Scan Scope "+Scope4+" out of "+StorageA;
		Culture4="No";
			
		String[] ResultStorageOutScope4=Storage_A.OutOfStorageAreaScans(StorageA,Scope4,Staff5,Staff5ID,Culture4,CultureAssociationID4,Integer.toString(Scope4CycleCount));
		String StorageOut_AssocScope4=ResultStorageOutScope4[0];
		String OverallStorageOut_Result4=ResultStorageOutScope4[1];
		int[] ActualScope4Cycle1=R_A.GetCycleIDs(UAS.connstring, Scope4ID, Scope4CycleCount);

		String ResultScope4Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope4Cycle1, ActualScope4Cycle1);
		temp=ResultScope4Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope4Cycle1, OverallResult);

		
		System.out.println("Starting Step:  "+UAS.StepNum+" ResultScope4Cycle1="+ResultScope4Cycle1);
		Result="ResultScope4Cycle1="+ResultScope4Cycle1;

		Expected="Scope "+Scope4+" was scanned out of "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);
		System.out.println("OverallResult"+OverallResult);
		Expected="Overall Result is expected to pass";
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, OverallResult);

		//scan scope 3 & 4 into PR1
		UAS.StepNum++;
		Scope3CycleCount++;
		Scope4CycleCount++;
		Culture3="NA";
		Culture4="NA";
		Patient="MRN222222";
		Phy1="Physician3 Physician3(MD03)";
		Phy2="";
		
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope3+" and "+Scope4+" into "+PR1);
		Description="Scan Scopes "+Scope3+" and "+Scope4+" into "+PR1;
		
		//NM need to update PR_Scans to have 2 cycle counts.
		EQ_A.EQ_Scans(Waiting1,Exam,Phy1,Phy2,Patient);

		String[] ResultPRScans2=PR_A.PR_Scans(PR1,Scope3,Scope4,Staff2,Staff2ID,Phy1,Phy2,AdmitPatientBarcode,Patient,StartProcScan,EndProcScan,Culture3,Integer.parseInt(CultureAssociationID3),Culture4,Integer.parseInt(CultureAssociationID4),Integer.toString(Scope3CycleCount));
		
		int PR_Assoc2=Integer.parseInt(ResultPRScans2[0]);
		String OverallPR_Result2=ResultPRScans2[1];
		temp=OverallPR_Result2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallPR_Result2, OverallResult);

		ExpectedScope3Cycle2[0]=1;
		ExpectedScope3Cycle2[1]=PR_Assoc2;
		ExpectedScope4Cycle2[0]=1;
		ExpectedScope4Cycle2[1]=PR_Assoc2;

		int[] ActualScope3Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope3ID, Scope3CycleCount);
		int[] ActualScope4Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope4ID, Scope4CycleCount);

		String ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);

		String ResultScope4Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope4Cycle2, ActualScope4Cycle2);
		temp=ResultScope4Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope4Cycle1, OverallResult);
		
		System.out.println("ending Step:  "+UAS.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		System.out.println("ending Step:  "+UAS.StepNum+" ResultScope4Cycle2="+ResultScope4Cycle2);
		Result="ResultScope3Cycle2="+ResultScope3Cycle2+". ResultScope4Cycle2="+ResultScope4Cycle2;

		Expected="Scopes "+Scope3+" and "+Scope4+" were used in "+PR1+". The ScopeCycle table is correct for CycleCount="+Scope3CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		//scan scope 3 into Sink1
		UAS.StepNum++;
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope3+" into "+Sink1);
		Description="Scan Scopes "+Scope3+" into "+Sink1+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans3=Soiled_A.SoiledRoomScans(Sink1,Scope3,Staff3,Staff3ID,LTPass,MCStart,MCEnd,Integer.toString(Scope3CycleCount));
		int Scope3_SR_Assoc=Integer.parseInt(ResultSoiledScans3[0]);
		String OverallSoiled_Result3=ResultSoiledScans3[1];
		temp=OverallSoiled_Result3.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result3, OverallResult);
		
		ExpectedScope3Cycle2[0]=2;
		ExpectedScope3Cycle2[2]=Scope3_SR_Assoc;

		Arrays.sort(ExpectedScope3Cycle2);

		ActualScope3Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope3ID, Scope3CycleCount);
		ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		
		Expected="Scopes "+Scope3+" was manually cleaned in "+Sink1+". The ScopeCycle table is correct for CycleCount="+Scope3CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope3Cycle2);

		
		//scan scope 4 into Sink2
		UAS.StepNum++;
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope4+" into "+Sink2);
		Description="Scan Scopes "+Scope4+" into "+Sink2+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans4=Soiled_A.SoiledRoomScans(Sink2,Scope4,Staff4,Staff4ID,LTPass,MCStart,MCEnd,Integer.toString(Scope4CycleCount));
		int Scope4_SR_Assoc=Integer.parseInt(ResultSoiledScans4[0]);
		OverallSoiled_Result=ResultSoiledScans4[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);

		ExpectedScope4Cycle2[0]=2;
		ExpectedScope4Cycle2[2]=Scope4_SR_Assoc;
		
		Arrays.sort(ExpectedScope4Cycle2);
		
		ActualScope4Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope4ID, Scope4CycleCount);
		ResultScope4Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope4Cycle2, ActualScope4Cycle2);
		temp=ResultScope4Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope4Cycle2, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope4Cycle2="+ResultScope4Cycle2);
		Expected="Scopes "+Scope4+" was manually cleaned in "+Sink2+". The ScopeCycle table is correct for CycleCount="+Scope4CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope4Cycle1);

		
		//Scan scope3 with Bioburden scanner and with a test status =Pass. no optional result entered.
		UAS.StepNum++;
		KeyOrScan="";
		BioOptional="";
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope3+" with bioburden scanner="+Bioburden);
		Description="Scan Scopes "+Scope3+" with "+Bioburden+" and Test Status="+BioPass+"; Optional Test Result="+BioOptional+" and Staff="+Staff6;
		
		String[] ResultBioScans3=Bio_A.BioburdenScans(Bioburden,Scope3,BioPass,KeyOrScan,BioOptional,Staff6,Staff6ID,Integer.toString(Scope3CycleCount));
		int Scope3_Bio_Assoc=Integer.parseInt(ResultBioScans3[0]);
		String OverallBio_Result3=ResultBioScans3[1];
		temp=OverallBio_Result3.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallBio_Result3, OverallResult);
		
		ExpectedScope3Cycle2[0]=3;
		ExpectedScope3Cycle2[3]=Scope3_Bio_Assoc;

		Arrays.sort(ExpectedScope3Cycle2);
		
		ActualScope3Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope3ID, Scope3CycleCount);
		ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		
		Expected="Scopes "+Scope3+" Bioburden Tested with "+Bioburden+". The ScopeCycle table is correct for CycleCount="+Scope3CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope3Cycle2);


		//Scan scope4 with Bioburden scanner and with a test status =Fail. optional result=Blue.
		UAS.StepNum++;
		KeyOrScan="Key Entry";
		BioOptional="1";
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope4+" with bioburden scanner="+Bioburden);
		Description="Scan Scopes "+Scope4+" with "+Bioburden+" and Test Status="+BioFail+"; Optional Test Result="+BioOptional+" and Staff="+Staff6;
		
		String[] ResultBioScans4=Bio_A.BioburdenScans(Bioburden,Scope4,BioFail,KeyOrScan,BioOptional,Staff6,Staff6ID,Integer.toString(Scope4CycleCount));
		int Scope4_Bio_Assoc=Integer.parseInt(ResultBioScans4[0]);
		String OverallBio_Result4=ResultBioScans4[1];
		temp=OverallBio_Result4.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallBio_Result4, OverallResult);
		
		ExpectedScope4Cycle2[0]=3;
		ExpectedScope4Cycle2[3]=Scope4_Bio_Assoc;

		Arrays.sort(ExpectedScope4Cycle2);
		
		ActualScope4Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope4ID, Scope4CycleCount);
		ResultScope4Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope4Cycle2, ActualScope4Cycle2);
		temp=ResultScope4Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope4Cycle2, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope4Cycle2="+ResultScope4Cycle2);
		
		Expected="Scope "+Scope4+" Bioburden Tested with "+Bioburden+". The ScopeCycle table is correct for CycleCount="+Scope4CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope4Cycle2);
		
		//scan scope 4 into Sink2
		UAS.StepNum++;
		Scope4CycleCount++;
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope4+" into "+Sink2);
		Description="Scan Scopes "+Scope4+" into "+Sink2+" and perform Leak Test and Manual Cleaning.";
		
		ResultSoiledScans4=Soiled_A.SoiledRoomScans(Sink2,Scope4,Staff4,Staff4ID,LTPass,MCStart,MCEnd,Integer.toString(Scope4CycleCount));
		Scope4_SR_Assoc=Integer.parseInt(ResultSoiledScans4[0]);
		OverallSoiled_Result=ResultSoiledScans4[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);

		ExpectedScope4Cycle3[0]=1;
		ExpectedScope4Cycle3[1]=Scope4_SR_Assoc;
		
		Arrays.sort(ExpectedScope4Cycle3);
		
		int[] ActualScope4Cycle3=R_A.GetCycleIDs(UAS.connstring, Scope4ID, Scope4CycleCount);
		String ResultScope4Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope4Cycle3, ActualScope4Cycle3);
		temp=ResultScope4Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope4Cycle3, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope4Cycle3="+ResultScope4Cycle3);
		Expected="Scopes "+Scope4+" was manually cleaned in "+Sink2+". The ScopeCycle table is correct for CycleCount="+Scope4CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope4Cycle3);

		//Scan scope4 with Bioburden scanner and with a test status =Pass. optional result =42.
		UAS.StepNum++;
		KeyOrScan="Key Entry";
		BioOptional="4";
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope4+" with bioburden scanner="+Bioburden);
		Description="Scan Scopes "+Scope4+" with "+Bioburden+" and Test Status="+BioPass+"; Optional Test Result="+BioOptional+" and Staff="+Staff6;
		
		ResultBioScans4=Bio_A.BioburdenScans(Bioburden,Scope4,BioPass,KeyOrScan,BioOptional,Staff6,Staff6ID,Integer.toString(Scope4CycleCount));
		Scope4_Bio_Assoc=Integer.parseInt(ResultBioScans4[0]);
		OverallBio_Result4=ResultBioScans4[1];
		temp=OverallBio_Result4.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallBio_Result4, OverallResult);
		
		ExpectedScope4Cycle3[0]=2;
		ExpectedScope4Cycle3[2]=Scope4_Bio_Assoc;

		Arrays.sort(ExpectedScope4Cycle3);
		
		ActualScope4Cycle3=R_A.GetCycleIDs(UAS.connstring, Scope4ID, Scope4CycleCount);
		ResultScope4Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope4Cycle3, ActualScope4Cycle3);
		temp=ResultScope4Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope4Cycle3, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope4Cycle3="+ResultScope4Cycle3);
		
		Expected="Scope "+Scope4+" Bioburden Tested with "+Bioburden+". The ScopeCycle table is correct for CycleCount="+Scope4CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultScope4Cycle3);
		
		
		//set PR to available
		UAS.StepNum++;
		Room="Available";
		UAS.ScannerCount=UAS.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Workflow Event", "", Room, "");
		System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PR1;
		Thread.sleep(8000);
		
		Expected="Room State is Avialable";
		ExpectedRmSt="Available";
		
		RmState=IHV.Room_State(UAS.connstring, PR1);
		Result=IHV.Result_Room_State(RmState, ExpectedRmSt, PR1);
		temp=Result.split("-");
		OverallResult=GF.FinalResult(temp[0], Result, OverallResult);

		System.out.println(UAS.StepNum+":  "+Result);
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		
		//scan scope 3 & 4 into reprocessor
		UAS.StepNum++;
		Reason3="Used in Procedure";
		Reason4="";
		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope3+" and "+Scope4+" into "+Repro1);
		Description="Scan Scopes "+Scope3+" and "+Scope4+" into "+Repro1;
		
		String[] ResultReproScans3=Repro_A.ReprocessingRoomScans(Repro1,Scope3,Scope4,Staff1,Staff1ID,Reason3,Reason4,MRCPass,Integer.toString(Scope3CycleCount),Integer.toString(Scope4CycleCount));
		
		int Scope3InReproAssociationID=Integer.parseInt(ResultReproScans3[0]);
		int Scope4InReproAssociationID=Integer.parseInt(ResultReproScans3[1]);
		MRCAssociationID=Integer.parseInt(ResultReproScans3[2]);
		int Scope3OutReproAssociationID=Integer.parseInt(ResultReproScans3[3]);
		int Scope4OutReproAssociationID=Integer.parseInt(ResultReproScans3[4]);
		String OverallReproResult3=ResultReproScans3[5];
		temp=OverallReproResult3.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallReproResult3, OverallResult);

		System.out.println("OverallResult="+OverallResult);

		ExpectedScope3Cycle2[0]=6;
		ExpectedScope3Cycle2[4]=Scope3InReproAssociationID;
		ExpectedScope3Cycle2[5]=MRCAssociationID;
		ExpectedScope3Cycle2[6]=Scope3OutReproAssociationID;

		ExpectedScope4Cycle3[0]=5;
		ExpectedScope4Cycle3[3]=Scope4InReproAssociationID;
		ExpectedScope4Cycle3[4]=MRCAssociationID;
		ExpectedScope4Cycle3[5]=Scope4OutReproAssociationID;
		Arrays.sort(ExpectedScope3Cycle2);
		Arrays.sort(ExpectedScope4Cycle3);

		ActualScope3Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope3ID, Scope3CycleCount);
		ActualScope4Cycle3=R_A.GetCycleIDs(UAS.connstring, Scope4ID, Scope4CycleCount);

		ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);

		ResultScope4Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope4Cycle3, ActualScope4Cycle3);
		temp=ResultScope4Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope4Cycle3, OverallResult);

		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope4Cycle3="+ResultScope4Cycle3);
		Result="ResultScope3Cycle2="+ResultScope3Cycle2+". ResultScope4Cycle3="+ResultScope4Cycle3;

		Expected="Scopes "+Scope3+" and "+Scope4+" were reprocessed in "+Repro1+". The ScopeCycle table is correct for "+Scope3+""
				+ " CycleCount="+Scope3CycleCount+" and "+Scope4+" CycleCount="+Scope4CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		//scan scope 3 into Storage Area A
		UAS.StepNum++;

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope3+" into "+StorageA);
		Description="Scan Scopes "+Scope3+" into "+StorageA;
		
		String[] ResultStorageInScope3=Storage_A.IntoStorageAreaScans(StorageA,Scope3,Staff5,Staff5ID,Cabinet,CultureObtained1,Integer.toString(Scope3CycleCount));
		
		String StorageIn_AssocScope3=ResultStorageInScope3[0];
		String OverallStorageIn_Result3=ResultStorageInScope3[1];
		temp=OverallStorageIn_Result3.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result3, OverallResult);
		
		ActualScope3Cycle2=R_A.GetCycleIDs(UAS.connstring, Scope3ID, Scope3CycleCount);

		ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);

		
		System.out.println("end Step:  "+UAS.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		Result="ResultScope3Cycle2="+ResultScope3Cycle2;

		Expected="Scopes "+Scope3+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+Scope3CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		
		// scan scope 4 into Storage Area A
		UAS.StepNum++;

		System.out.println("Starting Step:  "+UAS.StepNum+" scan "+Scope4+" into "+StorageA);
		Description="Scan Scopes "+Scope4+" into "+StorageA;
		
		String[] ResultStorageInScope4=Storage_A.IntoStorageAreaScans(StorageA,Scope4,Staff5,Staff5ID,Cabinet,CultureObtained2,Integer.toString(Scope4CycleCount));
		
		String StorageIn_AssocScope4=ResultStorageInScope4[0];
		String OverallStorageIn_Result4=ResultStorageInScope4[1];
		temp=OverallStorageIn_Result4.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result4, OverallResult);

		
		ActualScope4Cycle3=R_A.GetCycleIDs(UAS.connstring, Scope4ID, Scope4CycleCount);

		ResultScope4Cycle3=R_V.VerifyScopeCycleAssociations(ExpectedScope4Cycle3, ActualScope4Cycle3);
		temp=ResultScope4Cycle3.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope4Cycle3, OverallResult);
		
		System.out.println("End Step:  "+UAS.StepNum+" ResultScope4Cycle3="+ResultScope4Cycle3);
		Result="ResultScope4Cycle3="+ResultScope4Cycle3;

		Expected="Scopes "+Scope4+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+Scope4CycleCount;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result);

		IHV.Close_Exec_Log(UAS.XMLFileName, "Test Completed", UAS.TestCaseNumber);
		if (UAS.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();

	}

	@AfterTest
	public void PostTTest() throws IOException{
		//System.out.println("The Test Case Run was:  "+TCResult);
		//IHV.Close_Exec_Log(UAS.XMLFileName, TCResult);
		LP_A.CloseDriver();
	}
}
