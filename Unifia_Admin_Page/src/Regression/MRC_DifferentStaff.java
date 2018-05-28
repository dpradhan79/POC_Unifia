package Regression;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
 
public class MRC_DifferentStaff {
	
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
	private TestDataFunc TDF;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;

	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;
	
	public String Scope1="Scope1";
	public String Scope2="Scope2";
	public int Scope1ID=1; //Scope1 primary key
	public int Scope2ID=2; //Scope2 primary key
	public int CycleCount1=0;  //Cycle count for scope1
	public int CycleCount2=0; //Cycle count for scope2

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

	public String PR1="Procedure Room 1";
	public String PR2="Procedure Room 2";
	public String Repro1="Reprocessor 1";
	public String Sink1="Sink 1";
	public String Sink2="Sink 2";
	public String StorageA="Storage Area A";

	public String Patient="MRN111111"; //PatientID
	public String AdmitPatientBarcode="Yes";
	public String PhyScannedInWaiting="No";
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
	public String StartProcScan="Yes";
	public String EndProcScan="Yes";
	public String Reason1="New Scope", Reason2="New Scope";
	public int NumAssociations=7;
	public int[] ExpectedScope1Cycle1= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle1. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle1= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle1. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope1Cycle2= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle2. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle2= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle2. the first item specifies how many associationIDs there will be
	public String Description;
	String [] temp= new String[2];
	public String OverallResult="Pass";	
	public String Room="";
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)

	public void Test(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, AWTException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		//Inserting DB data
		//GF.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
		Unifia_Admin_Selenium.Scope1ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope1ExpectedExamCount=0;
		
		Unifia_Admin_Selenium.Scope1ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope1ExpectedExamCount=0;
		Unifia_Admin_Selenium.Scope2ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope2ExpectedExamCount=0;
		Unifia_Admin_Selenium.Scope3ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope3ExpectedExamCount=0;
		Unifia_Admin_Selenium.Scope4ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope4ExpectedExamCount=0;
		
		for(int i=0; i<NumAssociations;i++){ //initialize the arrays for scope cycles
			ExpectedScope1Cycle1[i]=999999999;
			ExpectedScope2Cycle1[i]=999999999;
			ExpectedScope1Cycle2[i]=999999999;
			ExpectedScope2Cycle2[i]=999999999;

		}
		
		Unifia_Admin_Selenium.XMLFileName="MRCCycleMgmt_Regression_";
		Unifia_Admin_Selenium.XMLFileName=IHV.Start_Exec_Log(Unifia_Admin_Selenium.XMLFileName);
		Unifia_Admin_Selenium.TestCaseNumber=1;


		Unifia_Admin_Selenium.StepNum=1;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		
		Description="Scan Scope "+Scope1+" and "+Scope2+" starting at reprocessor into PR, sink, repro and then check into "+StorageA+" to initilize the scopes";
		System.out.println("Starting Step:  "+Description);

		String ResultReason1="";
		String ResultReason2="";
		boolean Res=false;
		String Description=null;
		String Expected=null;
		String MRC="MRC Pass";
		
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
			String Result=InitialResult[0];
			
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
			
			String[] ResultPRScans=PR_A.PR_Scans(PR1, Scope1, Scope2, Staff2,Staff2ID, Phy,Phy2, AdmitPatientBarcode, Patient, StartProcScan, EndProcScan, Culture1, Integer.parseInt(CultureAssociationID1), Culture2, Integer.parseInt(CultureAssociationID2), Integer.toString(CycleCount1));
			
			int PR_Assoc=Integer.parseInt(ResultPRScans[0]); //Procedure Room associationID
			String OverallPR_Result=ResultPRScans[1];
			temp=OverallPR_Result.split("-");
			OverallResult=GF.FinalResult(temp[0], OverallPR_Result, OverallResult);

			ExpectedScope1Cycle2[0]=1;//there's one item in ScopeCycle table for scope1 cycle 1
			ExpectedScope1Cycle2[1]=PR_Assoc;
			ExpectedScope2Cycle2[0]=1;//there's one item in ScopeCycle table for scope2 cycle 1
			ExpectedScope2Cycle2[1]=PR_Assoc;

			int[] ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
			int[] ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

			String ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
			temp=ResultScope1Cycle2.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

			String ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
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
			
			String[] ResultSoiledScans1=Soiled_A.SoiledRoomScans(Sink1, Scope1, Staff3,Staff3ID,LTPass,MCStart, MCEnd, Integer.toString(CycleCount1));
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
			
			String[] ResultSoiledScans2=Soiled_A.SoiledRoomScans(Sink2,Scope2,Staff4,Staff4ID,LTPass, MCStart,MCEnd,Integer.toString(CycleCount2));
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



		
		//Scanning Scope1 into Reprocessor1 
		Unifia_Admin_Selenium.StepNum++;

		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Scope", "", Scope1, "");
		//Thread.sleep(15000);
		Description="Scan of Scope '" +Scope1+"' is done in "+ Repro1;
		String CycleEvent="Reprocessing In";
		String Scope_IH[]=IHV.GetReprocessorData(Unifia_Admin_Selenium.connstring, Repro1);
		String Scope1InAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeInLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		String ExpectedState="0";
		int OtherScopeStateID=0;
		String ExpectedCabinet="0";
		String ExpectedCycleCount1="2";
		int StaffPK=0;
		Unifia_Admin_Selenium.Scope1ExpectedReproCount++;
		
		String ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		
		int LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		String ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		String ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		String ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		
		String ResultScopeInLoc=IHV.Result_Location(Repro1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		
		String ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		
		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount1);
		
		String ResultReproInScope1=Scope1+": ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". "
				+ "ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReproInScope1);
		
		Reason1="Used in Procedure";
		Reason2="Used in Procedure";

		Description="Reason for Reprocessing "+Reason1+" is derived in "+ Repro1 +" for "+Scope1;
		String ActualReason=null;
		CycleEvent="Reason For Reprocessing";
		ActualReason=IHV.GetReasonForReprocessing(Unifia_Admin_Selenium.connstring, Scope1InAssociationID);
		ResultReason1=IHV.Result_Same_Reason(Reason1,ActualReason);
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Repro1);
		String Reason_Assoc=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		
		ResultReason1=IHV.Result_Same_Assoc(Scope1InAssociationID,Reason_Assoc);
		
		String ResultReasonCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		
		ResultReason1=ResultReason1+" for "+Scope1+" into "+Repro1+" and reason for reprocessing of "+Reason1+". "
				+ "CycleEvent is also correct = "+ResultReasonCycle;
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReason1);
		
		
		//Scanning Staff for Scope1 In
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Staff", "Tech", Staff1, "");
		Description="Scan of Staff '" +Staff1+"' is done in "+ Repro1+" for "+Scope1+" into Reprocessor";
		String Staff_IH[]=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Repro1);
		String StaffInIH=Staff_IH[0];
		String StaffIn_Assoc=Staff_IH[1];
		String ResultStaffIn1=IHV.Result_Same_Assoc(Scope1InAssociationID,StaffIn_Assoc)+" for staff and scope into reprocessor.";
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff1ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		ResultLastStaff=ResultLastStaff+". ResultLastStaff="+ResultLastStaff+".";
		
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffIn1);
		
		
		//Scanning Scope2 into Reprocessor1
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Scope", "", Scope2, "");
		Description="Scan of Scope '" +Scope2+"' is done in "+ Repro1;
		CycleEvent="Reprocessing In";
		Scope_IH=IHV.GetReprocessorData(Unifia_Admin_Selenium.connstring, Repro1);
		String Scope2InAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope2);
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualCycleCount=Scope_IH[4];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		StaffPK=0;
		String ExpectedCycleCount2="2";
		Unifia_Admin_Selenium.Scope2ExpectedReproCount++;
		
		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope2ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope2ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		ResultScopeInLoc=IHV.Result_Location(Repro1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		
		ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount2);
		
		String ResultScopesIn_AssocDiff=IHV.Result_Different_Assoc(Scope1InAssociationID, Scope2InAssociationID)+" Scopes have different associationIDs";
		
		String ResultReproInScope2="Scope2: ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState+" "
				+ "ResultScopesIn_AssocDiff="+ResultScopesIn_AssocDiff;
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReproInScope2);
		
		Description="Reason for Reprocessing "+Reason2+" is derived in "+ Repro1 +" for "+Scope2;
		ActualReason=null;
		CycleEvent="Reason For Reprocessing";
		ActualReason=IHV.GetReasonForReprocessing(Unifia_Admin_Selenium.connstring, Scope2InAssociationID);
		ResultReason2=IHV.Result_Same_Reason(Reason2,ActualReason);
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Repro1);
		String Reason2_Assoc=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		
		ResultReason2=IHV.Result_Same_Assoc(Scope2InAssociationID,Reason2_Assoc);
		
		ResultReasonCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		
		ResultReason2=ResultReason2+" for "+Scope2+" into "+Repro1+" and reason for reprocessing of "+Reason2+". "
			+ "CycleEvent is also correct = "+ResultReasonCycle;
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReason2);

		//Scanning Staff for Scope2 into Reprocessor1
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Staff", "Tech", Staff2, "");
		Description="Scan of Staff '" +Staff2+"' is done in "+ Repro1+" for "+Scope2+" into Reprocessor";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Repro1);
		StaffInIH=Staff_IH[0];
		StaffIn_Assoc=Staff_IH[1];
		String ResultStaffIn2=IHV.Result_Same_Assoc(Scope2InAssociationID,StaffIn_Assoc)+" for staff and scope into reprocessor.";
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff2ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		ResultStaffIn2=ResultStaffIn2+". ResultLastStaff="+ResultLastStaff+".";
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffIn2);
		
		
		//Scanning MRC Pass
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Test Result", "", MRC, "");
		Description="Scan of "+MRC+" is done in "+ Repro1;
		CycleEvent="MRC Test";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Repro1);
		String MRC_IH=Scope_IH[0];
		String MRCAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		
		String ResultMRCCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		
		String ResultMRCScope1_Assoc=IHV.Result_Different_Assoc(MRCAssociationID,Scope1InAssociationID)+" for MRC and "+Scope1;
		String ResultMRCScope2_Assoc=IHV.Result_Different_Assoc(MRCAssociationID,Scope2InAssociationID)+" for MRC and "+Scope2;
		String ResultMRC="MRC ResultMRCCycle="+ResultMRCCycle+". ResultMRCScope1_Assoc"+ResultMRCScope1_Assoc+". ResultMRCScope2_Assoc"+ResultMRCScope2_Assoc;
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultMRC);
		
		
		//Scanning Staff for MRC
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Staff", "Tech", Staff3, "");
		Description="Scan of Staff '" +Staff3+"' is done in "+ Repro1+" after MRCTest is done";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Repro1);
		String StaffMRCIH=Staff_IH[0];
		String StaffMRC_Assoc=Staff_IH[1];
		String ResultStaffMRC=IHV.Result_Same_Assoc(MRCAssociationID,StaffMRC_Assoc)+" for staff that performed the MRC Test.";
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffMRC);
		
		Thread.sleep(60000); //Wait 1 minute before scanning the scope out of the reprocessor
		
		//Scan Scope1 out of Reprocessor1
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ Repro1+" for "+Scope1+" out of Reprocessor";
		CycleEvent="Reprocessing Out";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Repro1);
		String Scope1OutIH=Scope_IH[0];
		String Scope1OutAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		ActualScopeState=Scope_IH[0];
		String ScopeOutLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		StaffPK=0;
		ExpectedState="5";
		OtherScopeStateID=0;
		ExpectedCabinet="0";

		String ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent)+" for "+Description;
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		String ResultScopesOut_AssocDiff=IHV.Result_Different_Assoc(Scope1InAssociationID, Scope1OutAssociationID)+" for Scope into and out of Reprocessor";
		
		String ResultScopeOutLoc=IHV.Result_Location(Repro1, ScopeInLoc, ExpectedCabinet,ActualSubloc)+" for "+Description;
		
		String ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState)+" for "+Description;
		
		String ResultReproOutScope1="Scope1: ResultScopeOutCycle= "+ResultScopeOutCycle+".. ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReproOutScope1);
		
		//Staff Scan for Scope1 out of Reprocessor1
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Staff", "Tech", Staff4, "");
		Description="Scan of Staff '" +Staff4+"' is done in "+ Repro1+" for "+Scope1+" out of Reprocessor";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Repro1);
		String StaffOut_Assoc=Staff_IH[1];
		String ResultStaffOut1=IHV.Result_Same_Assoc(Scope1OutAssociationID,StaffOut_Assoc)+" for staff and scope out of reprocessor.";
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff4ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultStaffOut1=ResultStaffOut1+". ResultLastStaff="+ResultLastStaff+".";
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffOut1);
		
		
		//Scan Scope2 out of Reprocessor1
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Scope", "", Scope2, "");
		Description="Scan of Scope '" +Scope2+"' is done in "+ Repro1+" for "+Scope2+" out of Reprocessor";
		CycleEvent="Reprocessing Out";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Repro1);
		String Scope2OutAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope2);
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		StaffPK=0;
		
		ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope2ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope2ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		ResultScopesOut_AssocDiff=IHV.Result_Different_Assoc(Scope2InAssociationID, Scope2OutAssociationID)+" for Scope into and out of Reprocessor";
		
		String ResultScopesOut_AssocDiff2=IHV.Result_Different_Assoc(Scope1OutAssociationID, Scope2OutAssociationID)+" for "+Scope1+" and "+Scope2+" out of Reprocessor";
		
		ResultScopeOutLoc=IHV.Result_Location(Repro1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		
		ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		
		String ResultReproOutScope2="Scope2: ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopesOut_AssocDiff="+ResultScopesOut_AssocDiff+". "
				+ "ResultScopesOut_AssocDiff2="+ResultScopesOut_AssocDiff2+". ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReproOutScope2);
		
		//Staff Scan for Scope2 out of Reprocessor1
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Repro1, "Staff", "Tech", Staff5, "");
		Description="Scan of Staff '" +Staff5+"' is done in "+ Repro1+" for "+Scope2+" out of Reprocessor";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Repro1);
		StaffOut_Assoc=Staff_IH[1];
		String ResultStaffOut2=IHV.Result_Same_Assoc(Scope2OutAssociationID,StaffOut_Assoc)+" for staff and scope into reprocessor.";
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff5ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		ResultStaffOut2=ResultStaffOut2+". ResultLastStaff="+ResultLastStaff+".";
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffOut2);
		
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (Unifia_Admin_Selenium.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	@AfterTest
	public void PostTTest() throws IOException{
		LP_A.CloseDriver();
	}
}
