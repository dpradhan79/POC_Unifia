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

public class PR3Scopes_CycleMgmt {
	
	/**
	 * Nicole McKinley 8/2/2016 
	 * 
	 * Regression Test of Cycle Mgmt - verify cycle management with 2 scopes in a procedure room, 
	 * and halfway through the exam a third scope is scanned into the procedure room.
	 * The Test will First initialize 2 scopes by scanning the whole way through the workflow
	 * This test will not verify anything with KE, culture or bioburden
	 * The Test Data it will use
	 * 
	 *  Scopes:  Scope1, Scope2 
	 *  
	 *  Location Scanners:
	 * 	-  Procedure Room:  Procedure Room 1
	 * 	-  Soiled Room:  Sink 1
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
	public TestFrameWork.RegressionTest.Regression_Actions R_A;
	public TestFrameWork.RegressionTest.Regression_Verifications R_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public GeneralFunc GF;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestDataFunc TDF;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;

	public String OverallResult="Pass";	
	
	public String StartProcScan="Yes"; //perform the Start Procedure Scan
	public String EndProcScan="Yes"; //perform the End Procedure Scan
	public String Room="";

	public String Reason1="New Scope", Reason2="New Scope";

	String [] temp= new String[2];

	public Boolean Res;
	
	//Scope names used in this test
	public String Scope1="Scope1";
	public String Scope2="Scope2";
	public String Scope3="Scope3";
	public int Scope1ID; //Scope1 primary key
	public int Scope2ID; //Scope2 primary key
	public int Scope3ID; //Scope2 primary key

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
	
	//Location names used for scanning.
	public String PR1="Procedure Room 1"; 
	public String Repro1="Reprocessor 1";
	public String Sink1="Sink 1";
	public String Sink2="Sink 2";
	public String StorageA="Storage Area A";
	
	public int CycleCount1=0;  //Cycle count for scope1
	public int CycleCount2=0; //Cycle count for scope2
	public int CycleCount3=0; //Cycle count for scope3
	public String Patient="MRN111111"; //PatientID
	public String AdmitPatientBarcode="Yes"; //Scan the Admit Patient barcode
	public String Culture1="NA"; //Culture was not obtained for scope1
	public String CultureAssociationID1="0"; //associationID if culture was obtained for scope1, since it was not obtained =0
	public String Culture2="NA"; //Culture was not obtained for scope2
	public String CultureAssociationID2="0";//associationID if culture was obtained for scope2, since it was not obtained =0
	public String Culture3="NA"; //Culture was not obtained for scope3
	public String CultureAssociationID3="0"; //associationID if culture was obtained for scope3, since it was not obtained =0
	public String LTFail="Leak Test Fail";
	public String LTPass="Leak Test Pass";
	public String MRCPass="MRC Pass";
	public String MRCFail="MRC Fail";
	public String MCStart="Yes"; //perform the Manual Clean Start Scan 
	public String MCEnd="Yes"; //perform the Manual Clean End Scan 
	public String CultureObtained="No";//culture is not obtained
	public String Cabinet="1"; //check the scopes into cabinet 1
	public String Description;
	public String Expected;
	public static Connection conn= null;
	public int NumAssociations=7;
	public int[] ExpectedScope1Cycle1= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle1. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope1Cycle2= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle2. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle1= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle1. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle2= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle2. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope3Cycle1= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope3 cycle1. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope3Cycle2= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope3 cycle2. the first item specifies how many associationIDs there will be
	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;
	public String Changes="No";

	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, AWTException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.resultFlag="Pass";
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);

		//Inserting DB data
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
		Scope3ID=R_A.GetScopeID(Unifia_Admin_Selenium.connstring, Scope3);
		
		for(int i=0; i<NumAssociations;i++){ //initialize the arrays for scope cycles
			ExpectedScope1Cycle1[i]=999999999;
			ExpectedScope2Cycle1[i]=999999999;
			ExpectedScope1Cycle2[i]=999999999;
			ExpectedScope2Cycle2[i]=999999999;
			ExpectedScope3Cycle1[i]=999999999;
			ExpectedScope3Cycle2[i]=999999999;
		}
		
		Unifia_Admin_Selenium.XMLFileName="PR3Scopes_CycleMgmt_Regression_";
		Unifia_Admin_Selenium.XMLFileName=IHV.Start_Exec_Log(Unifia_Admin_Selenium.XMLFileName);
		Unifia_Admin_Selenium.TestCaseNumber=1;

		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.StepNum=1;
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);

		Thread.sleep(500);
		//Start of the Test
		String [] phy_split= new String[2];
		String [] phy_split1= new String[2];

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
		String Expected="Scopes "+Scope1+" and "+Scope2+" initial scans ending in "+StorageA;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallResult);

		
		//scan scope 1 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;
		Description="Scan Scope "+Scope3+" starting at reprocessor into PR, sink, repro and then check into "+StorageA+" to initilize the scope";
		
		CycleCount3++;
			
		String[] InitialResult2=R_A.InitialScans(Scope3, "", ScanInfo);
		String Result2=InitialResult2[0];
		
		ExpectedScope3Cycle1[0]=5;
		ExpectedScope3Cycle1[1]=Integer.parseInt(InitialResult2[1]);
		ExpectedScope3Cycle1[2]=Integer.parseInt(InitialResult2[2]);
		ExpectedScope3Cycle1[3]=Integer.parseInt(InitialResult2[4]);
		ExpectedScope3Cycle1[4]=Integer.parseInt(InitialResult2[6]);
		ExpectedScope3Cycle1[5]=Integer.parseInt(InitialResult2[7]);
		
		Arrays.sort(ExpectedScope3Cycle1);
	
		temp=Result2.split("-");
			OverallResult=GF.FinalResult(temp[0], Result2, OverallResult);
			Expected="Scope "+Scope3+" initial scans ending in "+StorageA;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallResult);

			Thread.sleep(30000); //Wait 30 seconds before scanning the scope out of the storage area.

		
		//scan scope 1 out of Storage Area A
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
		Culture3="NA";

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+PR1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+PR1;
		
		//String[] ResultPRScans=PR_A.PR_Scans(PR1, Scope1, Scope2, Staff2, Phy, AdmitPatientBarcode, Patient, StartProcScan, EndProcScan, Culture1, Integer.parseInt(CultureAssociationID1), Culture2, Integer.parseInt(CultureAssociationID2), Integer.toString(CycleCount1));
		
		String Staff_IH[];
		String StaffInIH;
		String StaffIn_Assoc="";
		String ResultStaffIn1="NA";
		String StaffIn_RI1;
		String ResultIn_RI1="";
		
		String ResultScope2Association;
		String StaffIn_RI2;
		String ResultIn_RI2;
		String Phy_IH;
		String Phy_Assoc="";
		String ResultPhy="";
		String ResultPhyCycleEvent="";
		String ResultPRInScope2;		
		
		String AdmitPatient_IH;
		String AdmitPatient_Assoc="";
		String ResultAdmitPatient;
		String Patient_IH;
		String PatientAssociationID="";
		String ResultPatientCycleEvent;
		String ResultPatient="";
		String ResultStaffIn2="";

		String StartIH;
		String Start_Assoc;
		String ResultStartCycleEvent;
		String ResultStart;
		String EndIH;
		String End_Assoc;
		String ResultEndCycleEvent;
		String ResultEnd;		
		
		String StaffOut_Assoc;
		String ResultStaffOut1="";
		String StaffOut_RI1;
		String ResultOut_RI1="";
		String ResultStaffOut2="";
		String ResultPreCleanScope2="";
		String StaffOutIH;
		String Scope2OutAssociationID="";
		String Scope2InAssociationID="";
		String ResultScope2OutAssociation;
		String StaffOut_RI2;
		String ResultOut_RI2="";
		
		String Scope2RefNo="";
		String Scope2SerialNo="";
		String Scope2Model="";
		String Result_RefNum2="";
		String Result_ScopeModel2="";
		String Result_ScopeName2="";
		String Result_ScopeSerialNo2="";
		String Result_Scope2PR="";
		String Result_Scope2ExamDate="";
		String Result_Scope2InStaff="";
		String Result_Scope2ProcStart="";
		String Result_Scope2ProcEnd="";
		String Scope1ExamTime="";
		String Scope2ExamTime="";
		String ProcStartTime="";
		String ProcEndTime="";
		String Scope1Preclean="";
		String Scope2Preclean="";
		String Scope3Preclean="";
		String Result_Scope1Preclean="";
		String Result_Scope2Preclean="";
		String Result_Scope1StaffPreclean="";
		String Result_Scope2StaffPreclean="";
		String Result_Scope1Phy="";
		String Result_Scope2Phy="";
		String Result_Scope1Patient="";
		String Result_Scope2Patient="";
		String PhyID="";

		String Scope3RefNo="";
		String Scope3SerialNo="";
		String Scope3Model="";
		String Result_RefNum3="";
		String Result_ScopeModel3="";
		String Result_ScopeName3="";
		String Result_ScopeSerialNo3="";
		String Result_Scope3PR="";
		String Result_Scope3ExamDate="";
		String Result_Scope3InStaff="";
		String Result_Scope3ProcStart="";
		String Result_Scope3ProcEnd="";
		String Scope3ExamTime="";

		
		String RoomStatus_IH;
		String RoomStatus_Assoc;
		String ResultRoomStatusCycle;
		String ResultRoomStatus;
		String ResultScopeInState2;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ PR1;
		String CycleEvent="Pre-Procedure";
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String Scope1InIH=Scope_IH[0];
		String Scope1InAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		System.out.println(Scope1InIH+" = Scope "+Scope1+" into PR ItemHistory_PK");
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,Scope1InAssociationID);
		String Scope1RefNo=ScopeInfo[0];
		String Scope1SerialNo=ScopeInfo[1];
		String Scope1Model=ScopeInfo[2];
		Scope1ExamTime=ScopeInfo[3];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeInLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		int StaffPK=0;
		
		
		String ExpectedState="0";
		int OtherScopeStateID1=0;
		int OtherScopeStateID2=0;
		int OtherScopeStateID3=0;
		String ExpectedCabinet="0";
		String ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeInCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScopeInCycle,OverallResult);

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
		
		String ResultScopeInLoc=IHV.Result_Location(PR1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		temp=ResultScopeInLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);

		String ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID1),ActualOtherScopeState);
		temp=ResultScopeInState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, Integer.toString(CycleCount1));
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultPRInScope1=Scope1+": ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". "
				+ "ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		System.out.println(ResultPRInScope1);
		Expected="Scope Scanned in Procedure Room";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultPRInScope1);

		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Staff", "Tech", Staff2, "");
		Description="Scan of Staff '" +Staff2+"' is done in "+ PR1+" for "+Scope1+" into Procedure Room";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		StaffInIH=Staff_IH[0];
		StaffIn_Assoc=Staff_IH[1];
		ResultStaffIn1=IHV.Result_Same_Assoc(Scope1InAssociationID,StaffIn_Assoc)+" for staff and "+Scope1+" into PR.";
		temp=ResultStaffIn1.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultStaffIn1, OverallResult);

		System.out.println(ResultStaffIn1);
		StaffIn_RI1=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope1InIH);
		ResultIn_RI1= IHV.RelatedItem_Verf(StaffInIH, StaffIn_RI1);
		temp=ResultIn_RI1.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultIn_RI1, OverallResult);

		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff2ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		System.out.println(ResultIn_RI1);
		ResultStaffIn1="ResultStaffIn1 for associations:"+ResultStaffIn1+". ResultIn_RI1="+ResultIn_RI1+". ResultLastStaff="+ResultLastStaff+".";
		System.out.println(ResultStaffIn1);
		Expected="Staff scanned in the Procedure Room";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffIn1);

		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+Scope1+" and Staff "+Staff2ID+" into "+PR1+". ";

		String Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
		
		String Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		String Result_ScopeName1=WF_V.Verify_ScopeName(Scope1);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		String Result_Scope1PR=WF_V.Verify_PR(PR1);
		System.out.println("Result_Scope1PR=:"+Result_Scope1PR);
		temp=Result_Scope1PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1PR,OverallResult);

		String Result_Scope1ExamDate=WF_V.Verify_ExamDate(Scope1ExamTime);
		System.out.println("Result_Scope1ExamDate=:"+Result_Scope1ExamDate);
		temp=Result_Scope1ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ExamDate,OverallResult);

		String Result_Scope1InStaff=WF_V.Verify_PRInStaff(Staff2ID);
		System.out.println("Result_Scope1InStaff=:"+Result_Scope1InStaff);
		temp=Result_Scope1InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1InStaff,OverallResult);

		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope1_PRIn="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1PR="+Result_Scope1PR+". Result_Scope1ExamDate="+Result_Scope1ExamDate
				+". Result_Scope1InStaff="+Result_Scope1InStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning into "+PR1;
		Expected=Description;

		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope1ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope1ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);

		
		
		//Scan scope2 into PR 
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Scope", "", Scope2, "");
		Description="Scan of Scope '" +Scope2+"' is done in "+ PR1;
		CycleEvent="Pre-Procedure";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String Scope2InIH=Scope_IH[0];
		Scope2InAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(Scope2InIH+" = Scope into PR ItemHistory_PK");
		
		ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope2,Scope2InAssociationID);
		Scope2RefNo=ScopeInfo[0];
		Scope2SerialNo=ScopeInfo[1];
		Scope2Model=ScopeInfo[2];
		Scope2ExamTime=ScopeInfo[3];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope2);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualCycleCount=Scope_IH[4];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		
		StaffPK=0;

		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeInCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInCycle, OverallResult);

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
		
		ResultScope2Association=IHV.Result_Same_Assoc(Scope1InAssociationID,Scope2InAssociationID)+" for "+Scope2+" and "+Scope1+" into PR.";
		temp=ResultScope2Association.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScope2Association, OverallResult);

		ResultScopeInLoc=IHV.Result_Location(PR1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		temp=ResultScopeInLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);

		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID2),ActualOtherScopeState);
		temp=ResultScopeInState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

		ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, Integer.toString(CycleCount2));
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		ResultPRInScope2=Scope2+": ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScope2Association="+ResultScope2Association+". "
				+ "ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		System.out.println(ResultPRInScope2);
		Expected="Scope Scanned in Procedure Room";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultPRInScope2);
		
		//Scan staff that brought scope2 into PR2
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Staff", "Tech", Staff2, "");
		Description="Scan of Staff '" +Staff2+"' is done in "+ PR1+" for Scope into Reprocessor";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		StaffInIH=Staff_IH[0];
		StaffIn_Assoc=Staff_IH[1];
		ResultStaffIn2=IHV.Result_Same_Assoc(Scope2InAssociationID,StaffIn_Assoc)+" for staff and "+Scope2+" into PR.";
		temp=ResultStaffIn2.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultStaffIn2, OverallResult);

		//System.out.println(ResultStaffIn2);
		StaffIn_RI2=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope2InIH);
		ResultIn_RI2= IHV.RelatedItem_Verf(StaffInIH, StaffIn_RI2);
		temp=ResultIn_RI2.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultIn_RI2, OverallResult);
		//System.out.println(ResultIn_RI2);
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff2ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultStaffIn2="ResultStaffIn2 for associations:"+ResultStaffIn2+". ResultIn_RI2="+ResultIn_RI2+". ResultLastStaff="+ResultLastStaff+".";
		System.out.println(ResultStaffIn2);
		Expected="Staff scanned in the Procedure Room";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffIn2);

		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope2RefNo);
		
		Description="Verify Scope Record Management of "+Scope2+" and Staff "+Staff2ID+" into "+PR1+". ";

		Result_RefNum2=WF_V.Verify_RefNum(Scope2RefNo);
		temp=Result_RefNum2.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum2,OverallResult);
		
		Result_ScopeModel2=WF_V.Verify_ScopeModel(Scope2Model);
		temp=Result_ScopeModel2.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel2,OverallResult);

		Result_ScopeName2=WF_V.Verify_ScopeName(Scope2);
		temp=Result_ScopeName2.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName2,OverallResult);

		Result_ScopeSerialNo2=WF_V.Verify_ScopeSerialNum(Scope2SerialNo);
		temp=Result_ScopeSerialNo2.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo2,OverallResult);

		Result_Scope2PR=WF_V.Verify_PR(PR1);
		System.out.println("Result_Scope2PR=:"+Result_Scope2PR);
		temp=Result_Scope2PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2PR,OverallResult);

		Result_Scope2ExamDate=WF_V.Verify_ExamDate(Scope2ExamTime);
		System.out.println("Result_Scope2ExamDate=:"+Result_Scope2ExamDate);
		temp=Result_Scope2ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ExamDate,OverallResult);

		Result_Scope2InStaff=WF_V.Verify_PRInStaff(Staff2ID);
		System.out.println("Result_Scope2InStaff=:"+Result_Scope2InStaff);
		temp=Result_Scope2InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2InStaff,OverallResult);

		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope2_PRIn="Result_RefNum2="+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". "
				+ "Result_ScopeSerialNo2="+Result_ScopeSerialNo2+". Result_Scope2PR="+Result_Scope2PR+". Result_Scope2ExamDate="+Result_Scope2ExamDate
				+". Result_Scope2InStaff="+Result_Scope2InStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope2+" after scanning into "+PR1;
		Expected=Description;

		String resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope2ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope2ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);

		
		
		//Scan the Admit Patient barcode if specified
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Workflow Event", "", "Patient/Procedure Status", "");
		System.out.println(Res);
		Description="Scan of Patient/Procedure Status is done in "+ PR1;

		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		AdmitPatient_IH=Staff_IH[0];
		AdmitPatient_Assoc=Staff_IH[1];
		ResultAdmitPatient=IHV.Result_Same_Assoc(Scope1InAssociationID,AdmitPatient_Assoc)+" for Admit patient barcode scan and scope into PR.";
		temp=ResultAdmitPatient.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultAdmitPatient, OverallResult);

		System.out.println(ResultAdmitPatient);
		Expected="Scan of Patient/Procedure Status is done in the Procedure Room";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultAdmitPatient);

		//Scan patient
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Patient", "", Patient, "");
		Description="Scan of Patient '" +Patient+"' is done in "+ PR1;

		Expected="Patient scanned into PR";
		Unifia_Admin_Selenium.Scope1ExpectedExamCount++;
		Unifia_Admin_Selenium.Scope2ExpectedExamCount++;
		CycleEvent="Patient In";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		Patient_IH=Scope_IH[0];
		PatientAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(Patient_IH+" = Patient ItemHistory_PK");

		ResultPatientCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultPatientCycleEvent.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultPatientCycleEvent, OverallResult);

		ResultPatient=IHV.Result_Same_Assoc(Scope1InAssociationID,PatientAssociationID)+" for patient scan and scope into PR.";
		temp=ResultPatient.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultPatient, OverallResult);
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];

		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);

		ResultPatient=ResultPatient+" and ResultPatientCycle"+ResultPatientCycleEvent+". Scope1 ResultReproCount="+ResultReproCount
				+". ResultExamCount="+ResultExamCount+".";
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope2);
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];

		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope2ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope2ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		ResultPatient=ResultPatient+" Scope2 ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount+".";
		
		System.out.println(ResultPatient);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultPatient);

		//Scan Physician
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Staff", "Physician", Phy, "");
		Description="Scan of Physician '" +Phy+"' is done in "+ PR1+".";
		phy_split=Phy.split("\\(");
		String nicole=phy_split[1];
		phy_split1=nicole.split("\\)");
		PhyID=phy_split1[0];

		
		CycleEvent="Physician";
		Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		Phy_IH=Staff_IH[0];
		Phy_Assoc=Staff_IH[1];
		ActualCycleEvent=Staff_IH[5];
		ResultPhyCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultPhyCycleEvent.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultPhyCycleEvent, OverallResult);

		ResultPhy=IHV.Result_Same_Assoc(Scope1InAssociationID,Phy_Assoc)+" for Physician that performed the exam.";
		temp=ResultPhy.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultPhy, OverallResult);

		ResultPhy=ResultPhy+" ResultPhyCycleEvent="+ResultPhyCycleEvent;
		System.out.println(ResultPhy);
		Expected="Physician is scanned in the Procedure Room";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultPhy);

		//Scan procedure start
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Workflow Event", "", 	"Procedure Start", "");
		Description="Scan of Start Procedure is done in "+ PR1+".";
		
		CycleEvent="Procedure Start";
		Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		StartIH=Staff_IH[0];
		Start_Assoc=Staff_IH[1];
		ActualCycleEvent=Staff_IH[5];
		ResultStartCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultStartCycleEvent.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultStartCycleEvent, OverallResult);

		ResultStart=IHV.Result_Same_Assoc(Scope1InAssociationID,Start_Assoc)+" for Start Procedure scan for the exam.";
		temp=ResultStart.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultStart, OverallResult);

		ResultStart=ResultStart+" ResultStartCycleEvent="+ResultStartCycleEvent;
		System.out.println(ResultStart);
		Expected="Procedure Start is Scanned";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStart);
		
		
		Thread.sleep(60000); //Wait 1 minute before scanning Procedure End

		//scan scope 3 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope3+" out of "+StorageA);
		Description="Scan Scope "+Scope3+" out of "+StorageA;
		Culture3="No";
			
		String[] ResultStorageOutScope3=Storage_A.OutOfStorageAreaScans(StorageA,Scope3,Staff5,Staff5ID,Culture3,CultureAssociationID3,Integer.toString(CycleCount3));
		String StorageOut_AssocScope3=ResultStorageOutScope1[0]; //AssociationID for Scope3 Storage Out scans
		String OverallStorageOut_Result3=ResultStorageOutScope1[1];//overall pass/fail of storage out scans. 
		int[] ActualScope3Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope3ID, CycleCount3);

		String ResultScope3Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle1, ActualScope3Cycle1);
		temp=ResultScope3Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle1, OverallResult);

		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope3Cycle1="+ResultScope3Cycle1);
		Result="ResultScope3Cycle1="+ResultScope3Cycle1;

		Expected="Scope "+Scope3+" was scanned out of "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		System.out.println("OverallResult"+OverallResult);
		Expected="Overall Result is pass";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallResult);
		
		//Scan scope3 into PR 
		CycleCount3++;

		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Scope", "", Scope3, "");
		Description="Scan of Scope '" +Scope3+"' is done in "+ PR1;
		CycleEvent="Pre-Procedure";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String Scope3InIH=Scope_IH[0];
		String Scope3InAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(Scope3InIH+" = Scope into PR ItemHistory_PK");
		
		ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope3,Scope3InAssociationID);
		Scope3RefNo=ScopeInfo[0];
		Scope3SerialNo=ScopeInfo[1];
		Scope3Model=ScopeInfo[2];
		Scope3ExamTime=ScopeInfo[3];

		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope3);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualCycleCount=Scope_IH[4];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		StaffPK=0;
		Unifia_Admin_Selenium.Scope3ExpectedExamCount++;

		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeInCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInCycle, OverallResult);

		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope3);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope3ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope3ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		
		String ResultScope3Association=IHV.Result_Same_Assoc(Scope1InAssociationID,Scope3InAssociationID)+" for "+Scope3+" and "+Scope1+" into PR.";
		temp=ResultScope3Association.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScope3Association, OverallResult);

		ResultScopeInLoc=IHV.Result_Location(PR1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		temp=ResultScopeInLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);

		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID3),ActualOtherScopeState);
		temp=ResultScopeInState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

		ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, Integer.toString(CycleCount3));
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultPRInScope3=Scope3+": ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScope3Association="+ResultScope3Association+". "
				+ "ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		System.out.println(ResultPRInScope3);
		Expected="Scope Scanned in Procedure Room";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultPRInScope3);
		
		//Scan staff that brought scope3 into PR1
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Staff", "Tech", Staff2, "");
		Description="Scan of Staff '" +Staff2+"' is done in "+ PR1+" for Scope into "+PR1;
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		StaffInIH=Staff_IH[0];
		StaffIn_Assoc=Staff_IH[1];
		String ResultStaffIn3=IHV.Result_Same_Assoc(Scope3InAssociationID,StaffIn_Assoc)+" for staff and "+Scope3+" into PR.";
		temp=ResultStaffIn3.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultStaffIn3, OverallResult);

		System.out.println(ResultStaffIn3);
		String StaffIn_RI3=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope3InIH);
		String ResultIn_RI3= IHV.RelatedItem_Verf(StaffInIH, StaffIn_RI3);
		temp=ResultIn_RI3.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultIn_RI3, OverallResult);
		System.out.println(ResultIn_RI3);
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff2ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope3);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultStaffIn3="ResultStaffIn3 for associations:"+ResultStaffIn3+". ResultIn_RI3="+ResultIn_RI3+". ResultLastStaff="+ResultLastStaff+".";
		System.out.println(ResultStaffIn3);
		Expected="Staff scanned in the Procedure Room";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffIn3);

		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope3RefNo);
		
		Description="Verify Scope Record Management of "+Scope3+" and Staff "+Staff2ID+" into "+PR1+". ";

		Result_RefNum3=WF_V.Verify_RefNum(Scope3RefNo);
		temp=Result_RefNum3.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum3,OverallResult);
		
		Result_ScopeModel3=WF_V.Verify_ScopeModel(Scope3Model);
		temp=Result_ScopeModel3.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel3,OverallResult);

		Result_ScopeName3=WF_V.Verify_ScopeName(Scope3);
		temp=Result_ScopeName3.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName3,OverallResult);

		Result_ScopeSerialNo3=WF_V.Verify_ScopeSerialNum(Scope3SerialNo);
		temp=Result_ScopeSerialNo3.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo3,OverallResult);

		Result_Scope3PR=WF_V.Verify_PR(PR1);
		System.out.println("Result_Scope3PR=:"+Result_Scope3PR);
		temp=Result_Scope3PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3PR,OverallResult);

		Result_Scope3ExamDate=WF_V.Verify_ExamDate(Scope3ExamTime);
		System.out.println("Result_Scope3ExamDate=:"+Result_Scope3ExamDate);
		temp=Result_Scope3ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ExamDate,OverallResult);

		Result_Scope3InStaff=WF_V.Verify_PRInStaff(Staff2ID);
		System.out.println("Result_Scope3InStaff=:"+Result_Scope3InStaff);
		temp=Result_Scope3InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3InStaff,OverallResult);

		String Result_Scope3Phy=WF_V.Verify_Physician(PhyID);
		temp=Result_Scope3Phy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Phy,OverallResult);
		System.out.println("Result_Scope3Phy"+Result_Scope3Phy);

		String Result_Scope3Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope3Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Patient,OverallResult);
		System.out.println("Result_Scope3Patient=:"+Result_Scope3Patient);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope3_PRIn="Result_RefNum3="+Result_RefNum3+". Result_ScopeModel3="+Result_ScopeModel3+". Result_ScopeName3="+Result_ScopeName3+". "
				+ "Result_ScopeSerialNo3="+Result_ScopeSerialNo3+". Result_Scope3PR="+Result_Scope3PR+". Result_Scope3ExamDate="+Result_Scope3ExamDate
				+". Result_Scope3InStaff="+Result_Scope3InStaff+". Result_Scope3Phy="+Result_Scope3Phy+". Result_Scope3Patient="+Result_Scope3Patient;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope3_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope3+" after scanning into "+PR1;
		Expected=Description;

		String resultScope3_MAM="Scope3 MAM Result ="+MAM_V.verifyScopeDetails(Scope3, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope3ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope3ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope3_MAM);

		
		
		//Scan Procedure End if provided. 
		Thread.sleep(60000); //Wait 1 minute before scanning Procedure End

		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Workflow Event", "", 	"Procedure End", "");
		Description="Scan of End Procedure is done in "+ PR1+".";
		
		CycleEvent="Procedure End";
		Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		EndIH=Staff_IH[0];
		End_Assoc=Staff_IH[1];
		ActualCycleEvent=Staff_IH[5];
		ResultEndCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultEndCycleEvent.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultEndCycleEvent, OverallResult);

		ResultEnd=IHV.Result_Same_Assoc(Scope1InAssociationID,End_Assoc)+" for end Procedure scan for the exam.";
		temp=ResultEnd.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultEnd, OverallResult);

		ResultEnd=ResultEnd+" ResultEndCycleEvent="+ResultEndCycleEvent;
		System.out.println(ResultEnd);
		Expected="Procedure End is Scanned";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultEnd);

		//Scan Scope1 Preclean
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ PR1;
		Scope1Preclean="Yes";
		CycleEvent="Pre-Clean Complete";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String Scope1OutIH=Scope_IH[0];
		String Scope1OutAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(Scope1OutIH+" = Scope Preclean Complete ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);

		ActualScopeState=Scope_IH[0];
		String ScopeOutLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		StaffPK=0;
		
		ExpectedState="3";
		ExpectedCabinet="0";

		String ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeOutCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutCycle, OverallResult);

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
		
		String ResultScope1OutAssociation=IHV.Result_Same_Assoc(Scope1InAssociationID,Scope1OutAssociationID)+" for "+Scope1+" into PR and Preclean Complete.";
		temp=ResultScope1OutAssociation.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScope1OutAssociation, OverallResult);

		String ResultScopeOutLoc=IHV.Result_Location(PR1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		temp=ResultScopeOutLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutLoc, OverallResult);

		String ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID1),ActualOtherScopeState);
		temp=ResultScopeOutState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutState, OverallResult);

		String ResultPreCleanScope1=Scope1+": ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScope1OutAssociation="+ResultScope1OutAssociation+". ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		System.out.println(ResultPreCleanScope1);
		Expected="Pre Clean Scope is Completed";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultPreCleanScope1);


		//Scan Staff that performed pre-clean on scope1
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Staff", "Tech", Staff2, "");
		Description="Scan of Staff '" +Staff2+"' is done in "+ PR1+" for Scope Preclean complete";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		StaffOutIH=Staff_IH[0];
		StaffOut_Assoc=Staff_IH[1];
		ResultStaffOut1=IHV.Result_Same_Assoc(Scope1OutAssociationID,StaffOut_Assoc)+" for staff that performed Preclean of "+Scope1;
		temp=ResultStaffOut1.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultStaffOut1, OverallResult);
		//System.out.println(ResultStaffOut1);
		
		StaffOut_RI1=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope1OutIH);
		ResultOut_RI1= IHV.RelatedItem_Verf(StaffOutIH, StaffOut_RI1);
		temp=ResultOut_RI1.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultOut_RI1, OverallResult);
		//System.out.println(ResultOut_RI1);
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff2ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultStaffOut1="ResultStaffOut1 for associations:"+ResultStaffOut1+". ResultOut_RI1="+ResultOut_RI1+". ResultLastStaff="+ResultLastStaff+".";
		System.out.println(ResultStaffOut1);
		Expected="Pre Clean Staff is Completed";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffOut1);

		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);

		Result_Scope1ExamDate=WF_V.Verify_ExamDate(Scope1ExamTime);
		System.out.println("Result_Scope1ExamDate=:"+Result_Scope1ExamDate);
		temp=Result_Scope1ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ExamDate,OverallResult);

		Result_Scope1Phy=WF_V.Verify_Physician(PhyID);
		temp=Result_Scope1Phy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Phy,OverallResult);
		System.out.println("Result_Scope1Phy"+Result_Scope1Phy);

		Result_Scope1Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope1Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Patient,OverallResult);
		System.out.println("Result_Scope1Patient=:"+Result_Scope1Patient);
		
		ScopeInfo=IHV.GetProcStartEndTime(Unifia_Admin_Selenium.connstring,Scope1InAssociationID);
		ProcStartTime=ScopeInfo[0];
		ProcEndTime=ScopeInfo[1];
		
		String Result_Scope1ProcStart=WF_V.Verify_ProcStart(ProcStartTime);
		temp=Result_Scope1ProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ProcStart,OverallResult);
		System.out.println("Result_Scope1ProcStart=:"+Result_Scope1ProcStart);

		String Result_Scope1ProcEnd=WF_V.Verify_ProcEnd(ProcEndTime);
		temp=Result_Scope1ProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ProcEnd,OverallResult);
		System.out.println("Result_Scope1ProcEnd=:"+Result_Scope1ProcEnd);

		Result_Scope1Preclean=WF_V.Verify_PreClean(Scope1Preclean);
		temp=Result_Scope1Preclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Preclean,OverallResult);
		System.out.println("Result_Scope1Preclean=:"+Result_Scope1Preclean);

		Result_Scope1StaffPreclean=WF_V.Verify_PreCleanStaff(Staff2ID);		
		temp=Result_Scope1StaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1StaffPreclean,OverallResult);
		System.out.println("Result_Scope1StaffPreclean=:"+Result_Scope1StaffPreclean);

		WF_A.Cancel(Changes);
		Description="Scan "+Scope1+" Preclean in "+PR1+" verify Scope Record Management";
		Expected=Description;
		String Result_SRM_Scope1_PR="Result_Scope1ExamDate="+Result_Scope1ExamDate+". Result_Scope1Phy="+Result_Scope1Phy+". Result_Scope1Patient="+Result_Scope1Patient+". Result_Scope1ProcStart="+Result_Scope1ProcStart+". "
				+ "Result_Scope1ProcEnd="+Result_Scope1ProcEnd+". Result_Scope1Preclean="+Result_Scope1Preclean+". Result_Scope1StaffPreclean="+Result_Scope1StaffPreclean;
		
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_PR);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning out of "+PR1;
		Expected=Description;

		resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope1ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope1ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);

		
		
		//Scan Scope2 Preclean
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Scope", "", Scope2, "");
		Description="Scan of Scope '" +Scope2+"' is done in "+ PR1;
		CycleEvent="Pre-Clean Complete";
		Scope2Preclean="Yes";
		ExpectedState="3";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String ScopeOutIH2=Scope_IH[0];
		Scope2OutAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(ScopeOutIH2+" = Scope Preclean ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope2);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		StaffPK=0;
		
		ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeOutCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutCycle, OverallResult);

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
		
		ResultScope2OutAssociation=IHV.Result_Same_Assoc(Scope2InAssociationID,Scope2OutAssociationID)+" for "+Scope2+" into PR and Preclean Complete.";
		temp=ResultScope2OutAssociation.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScope2OutAssociation, OverallResult);

		ResultScopeOutLoc=IHV.Result_Location(PR1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		temp=ResultScopeOutLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutLoc, OverallResult);

		ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID2),ActualOtherScopeState);
		temp=ResultScopeOutState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutState, OverallResult);

		ResultPreCleanScope2=Scope2+": ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScope2OutAssociation="+ResultScope2OutAssociation+". "
				+ "ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		System.out.println(ResultPreCleanScope2);
		Expected="Pre Clean Scope is Completed";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultPreCleanScope2);

		//Scan Staff that performed pre-clean on scope2
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Staff", "Tech", Staff2, "");
		Description="Scan of Staff '" +Staff2+"' is done in "+ PR1+" for Scope Preclean complete";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		StaffOutIH=Staff_IH[0];
		StaffOut_Assoc=Staff_IH[1];
		ResultStaffOut2=IHV.Result_Same_Assoc(Scope2OutAssociationID,StaffOut_Assoc)+" for staff and "+Scope2+" Preclean complete.";
		temp=ResultStaffOut2.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultStaffOut2, OverallResult);		
		//System.out.println(ResultStaffOut2);
		
		StaffOut_RI2=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, ScopeOutIH2);
		ResultOut_RI2= IHV.RelatedItem_Verf(StaffOutIH, StaffOut_RI2);
		temp=ResultOut_RI2.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultOut_RI2, OverallResult);
		//System.out.println(ResultOut_RI2);
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff2ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultStaffOut2="ResultStaffOut2 for associations:"+ResultStaffOut2+". ResultOut_RI2="+ResultOut_RI2+". ResultLastStaff="+ResultLastStaff+".";
		System.out.println(ResultStaffOut2);
		Expected="Pre Clean Staff is Completed";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffOut2);

		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope2RefNo);

		Result_Scope2ExamDate=WF_V.Verify_ExamDate(Scope2ExamTime);
		System.out.println("Result_Scope2ExamDate=:"+Result_Scope2ExamDate);
		temp=Result_Scope2ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ExamDate,OverallResult);

		Result_Scope2Phy=WF_V.Verify_Physician(PhyID);
		temp=Result_Scope2Phy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Phy,OverallResult);
		System.out.println("Result_Scope2Phy"+Result_Scope2Phy);

		Result_Scope2Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope2Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Patient,OverallResult);
		System.out.println("Result_Scope2Patient=:"+Result_Scope2Patient);
		
		ScopeInfo=IHV.GetProcStartEndTime(Unifia_Admin_Selenium.connstring,Scope2InAssociationID);
		ProcStartTime=ScopeInfo[0];
		ProcEndTime=ScopeInfo[1];
		
		Result_Scope2ProcStart=WF_V.Verify_ProcStart(ProcStartTime);
		temp=Result_Scope2ProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ProcStart,OverallResult);
		System.out.println("Result_Scope2ProcStart=:"+Result_Scope2ProcStart);

		Result_Scope2ProcEnd=WF_V.Verify_ProcEnd(ProcEndTime);
		temp=Result_Scope2ProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ProcEnd,OverallResult);
		System.out.println("Result_Scope2ProcEnd=:"+Result_Scope2ProcEnd);

		Result_Scope2Preclean=WF_V.Verify_PreClean(Scope2Preclean);
		temp=Result_Scope2Preclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Preclean,OverallResult);
		System.out.println("Result_Scope2Preclean=:"+Result_Scope2Preclean);

		Result_Scope2StaffPreclean=WF_V.Verify_PreCleanStaff(Staff2ID);		
		temp=Result_Scope2StaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2StaffPreclean,OverallResult);
		System.out.println("Result_Scope2StaffPreclean=:"+Result_Scope2StaffPreclean);

		WF_A.Cancel(Changes);
		Description="Scan "+Scope2+" Preclean in "+PR1+" verify Scope Record Management";
		Expected=Description;
		String Result_SRM_Scope2_PR="Result_Scope2ExamDate="+Result_Scope2ExamDate+". Result_Scope2Phy="+Result_Scope2Phy+". Result_Scope2Patient="+Result_Scope2Patient+". Result_Scope2ProcStart="+Result_Scope2ProcStart+". "
				+ "Result_Scope2ProcEnd="+Result_Scope2ProcEnd+". Result_Scope2Preclean="+Result_Scope2Preclean+". Result_Scope2StaffPreclean="+Result_Scope2StaffPreclean;
		
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_PR);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope2+" after scanning out of "+PR1;
		Expected=Description;

		resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope2ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope2ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);

		
		//Scan Scope3 Preclean
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Scope", "", Scope3, "");
		Description="Scan of Scope '" +Scope3+"' is done in "+ PR1;
		CycleEvent="Pre-Clean Complete";
		Scope3Preclean="Yes";

		ExpectedState="3";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String ScopeOutIH3=Scope_IH[0];
		String Scope3OutAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(ScopeOutIH3+" = Scope Preclean ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope3);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		StaffPK=0;
		
		ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeOutCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutCycle, OverallResult);

		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope3);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope3ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope3ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		
		String ResultScope3OutAssociation=IHV.Result_Same_Assoc(Scope3InAssociationID,Scope3OutAssociationID)+" for "+Scope3+" into PR and Preclean Complete.";
		temp=ResultScope3OutAssociation.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScope3OutAssociation, OverallResult);

		ResultScopeOutLoc=IHV.Result_Location(PR1, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		temp=ResultScopeOutLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutLoc, OverallResult);

		ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID2),ActualOtherScopeState);
		temp=ResultScopeOutState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutState, OverallResult);

		String ResultPreCleanScope3=Scope3+": ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScope3OutAssociation="+ResultScope3OutAssociation+". "
				+ "ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		System.out.println(ResultPreCleanScope3);
		Expected="Pre Clean Scope is Completed";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultPreCleanScope3);

		//Scan Staff that performed pre-clean on scope3
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Staff", "Tech", Staff2, "");
		Description="Scan of Staff '" +Staff2+"' is done in "+ PR1+" for Scope Preclean complete";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		StaffOutIH=Staff_IH[0];
		StaffOut_Assoc=Staff_IH[1];
		String ResultStaffOut3=IHV.Result_Same_Assoc(Scope3OutAssociationID,StaffOut_Assoc)+" for staff and "+Scope3+" Preclean complete.";
		temp=ResultStaffOut3.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultStaffOut3, OverallResult);		
		//System.out.println(ResultStaffOut3);
		
		String StaffOut_RI3=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, ScopeOutIH3);
		String ResultOut_RI3= IHV.RelatedItem_Verf(StaffOutIH, StaffOut_RI3);
		temp=ResultOut_RI3.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultOut_RI3, OverallResult);
		//System.out.println(ResultOut_RI3);
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff2ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope3);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		ResultStaffOut3="ResultStaffOut3 for associations:"+ResultStaffOut3+". ResultOut_RI3="+ResultOut_RI3+". ResultLastStaff="+ResultLastStaff+".";
		System.out.println(ResultStaffOut3);
		Expected="Pre Clean Staff is Completed";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffOut3);

		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope3RefNo);

		Result_Scope3ExamDate=WF_V.Verify_ExamDate(Scope3ExamTime);
		System.out.println("Result_Scope3ExamDate=:"+Result_Scope3ExamDate);
		temp=Result_Scope3ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ExamDate,OverallResult);

		Result_Scope3Phy=WF_V.Verify_Physician(PhyID);
		temp=Result_Scope3Phy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Phy,OverallResult);
		System.out.println("Result_Scope3Phy"+Result_Scope3Phy);

		Result_Scope3Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope3Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Patient,OverallResult);
		System.out.println("Result_Scope3Patient=:"+Result_Scope3Patient);
		
		ScopeInfo=IHV.GetProcStartEndTime(Unifia_Admin_Selenium.connstring,Scope3InAssociationID);
		ProcStartTime=ScopeInfo[0];
		ProcEndTime=ScopeInfo[1];
		
		Result_Scope3ProcStart=WF_V.Verify_ProcStart(ProcStartTime);
		temp=Result_Scope3ProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ProcStart,OverallResult);
		System.out.println("Result_Scope3ProcStart=:"+Result_Scope3ProcStart);

		Result_Scope3ProcEnd=WF_V.Verify_ProcEnd(ProcEndTime);
		temp=Result_Scope3ProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ProcEnd,OverallResult);
		System.out.println("Result_Scope3ProcEnd=:"+Result_Scope3ProcEnd);

		String Result_Scope3Preclean=WF_V.Verify_PreClean(Scope3Preclean);
		temp=Result_Scope3Preclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Preclean,OverallResult);
		System.out.println("Result_Scope3Preclean=:"+Result_Scope3Preclean);

		String Result_Scope3StaffPreclean=WF_V.Verify_PreCleanStaff(Staff2ID);		
		temp=Result_Scope3StaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3StaffPreclean,OverallResult);
		System.out.println("Result_Scope3StaffPreclean=:"+Result_Scope3StaffPreclean);

		WF_A.Cancel(Changes);
		Description="Scan "+Scope3+" Preclean in "+PR1+" verify Scope Record Management";
		Expected=Description;
		String Result_SRM_Scope3_PR="Result_Scope3ExamDate="+Result_Scope3ExamDate+". Result_Scope3Phy="+Result_Scope3Phy+". Result_Scope3Patient="+Result_Scope3Patient+". Result_Scope3ProcStart="+Result_Scope3ProcStart+". "
				+ "Result_Scope3ProcEnd="+Result_Scope3ProcEnd+". Result_Scope3Preclean="+Result_Scope3Preclean+". Result_Scope3StaffPreclean="+Result_Scope3StaffPreclean;
		
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope3_PR);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope3+" after scanning out of "+PR1;
		Expected=Description;

		resultScope3_MAM="Scope3 MAM Result ="+MAM_V.verifyScopeDetails(Scope3, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope3ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope3ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope3_MAM);
		
		
		//Scan Room Needs Cleaning barcode
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Workflow Event", "", "Needs Cleaning", "");
		System.out.println(Res);
		Description="Scan of Work flow event 'Needs Cleaning' is done in "+ PR1;

		CycleEvent="Room Status Change";
		
		
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		RoomStatus_IH=Scope_IH[0];
		RoomStatus_Assoc=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println("Room Status of Needs Cleaning AssociationID="+RoomStatus_Assoc);

		OtherScopeStateID1=0;  //OtherScopeState is changed to zero when the bucket is closed which is the room status change.  
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];

		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID1),ActualOtherScopeState);
		temp=ResultScopeInState.split("-");		
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);
		
		OtherScopeStateID2=0;  //OtherScopeState is changed to zero when the bucket is closed which is the room status change.  
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope2);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		
		ResultScopeInState2=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID2),ActualOtherScopeState);
		temp=ResultScopeInState2.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState2, OverallResult);

		OtherScopeStateID3=0;  //OtherScopeState is changed to zero when the bucket is closed which is the room status change.  
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope3);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		
		String ResultScopeInState3=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID3),ActualOtherScopeState);
		temp=ResultScopeInState3.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState3, OverallResult);

		ResultRoomStatusCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultRoomStatusCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultRoomStatusCycle, OverallResult);

		ResultRoomStatus=IHV.Result_Same_Assoc(Scope1InAssociationID,RoomStatus_Assoc)+" for Room Status scan in PR.";
		temp=ResultRoomStatus.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultRoomStatus, OverallResult);


		ResultRoomStatus=ResultRoomStatus+" and ResultRoomStatusCycle"+ResultRoomStatusCycle+". "+Scope1+" scopestatus result="+ResultScopeInState+". "
				+Scope2+ " scopestatus result="+ResultScopeInState2+". "+Scope3+" scopestatus result="+ResultScopeInState3;
		System.out.println(ResultRoomStatus);
		Expected="Room Status should be changed to Needs Cleaning";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultRoomStatus);
		System.out.println("OverallResult="+OverallResult);

		
		ExpectedScope1Cycle2[0]=1;//there's one item in ScopeCycle table for scope1 cycle 2
		ExpectedScope1Cycle2[1]=Integer.parseInt(Scope1InAssociationID);
		ExpectedScope2Cycle2[0]=1;//there's one item in ScopeCycle table for scope2 cycle 2
		ExpectedScope2Cycle2[1]=Integer.parseInt(Scope2InAssociationID);
		ExpectedScope3Cycle2[0]=1;//there's one item in ScopeCycle table for scope3 cycle 2
		ExpectedScope3Cycle2[1]=Integer.parseInt(Scope3InAssociationID);

		int[] ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
		int[] ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);
		int[] ActualScope3Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope3ID, CycleCount3);

		String ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		String ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);
		
		String ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);
		
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2+". ResultScope2Cycle2="+ResultScope2Cycle2+". ResultScope3Cycle2="+ResultScope3Cycle2;

		Expected="Scopes "+Scope1+", "+Scope2+", and "+Scope3+" were used in "+PR1+". The ScopeCycle table is correct for "
				+ "CycleCount="+CycleCount1+" for all 3 scopes";
		Description="ScopeCycle table entries are checked";
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

		//scan scope 3 into Sink1
		Unifia_Admin_Selenium.StepNum++;
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope3+" into "+Sink1);
		Description="Scan Scopes "+Scope3+" into "+Sink1+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans3=Soiled_A.SoiledRoomScans(Sink1,Scope3,Staff3,Staff3ID,LTPass,MCStart,MCEnd,Integer.toString(CycleCount3));
		int Scope3_SR_Assoc=Integer.parseInt(ResultSoiledScans3[0]);
		 OverallSoiled_Result=ResultSoiledScans3[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);
		
		ExpectedScope3Cycle2[0]=2;
		ExpectedScope3Cycle2[2]=Scope3_SR_Assoc;

		Arrays.sort(ExpectedScope3Cycle2);

		ActualScope3Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope3ID, CycleCount3);
		ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		
		Expected="Scopes "+Scope3+" was manually cleaned in "+Sink1+". The ScopeCycle table is correct for CycleCount3="+CycleCount3;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope3Cycle2);

		

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
		
		String[] ResultReproScans=Repro_A.ReprocessingRoomScans(Repro1,Scope1,Scope2,Staff1,Staff1ID,Reason1,Reason2,MRCPass,Integer.toString(CycleCount1),Integer.toString(CycleCount2));
		
		int Scope1InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		int Scope2InReproAssociationID=Integer.parseInt(ResultReproScans[1]);
		int MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		int Scope1OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		int Scope2OutReproAssociationID=Integer.parseInt(ResultReproScans[4]);
		String OverallReproResult2=ResultReproScans[5];
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

		Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro1+". The ScopeCycle table is correct for CycleCount="+CycleCount1;
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
		
		ActualScope1Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);

		ResultScope1Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle2, ActualScope1Cycle2);
		temp=ResultScope1Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle2, OverallResult);

		
		System.out.println("end Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle2="+ResultScope1Cycle2);
		Result="ResultScope1Cycle2="+ResultScope1Cycle2;

		Expected="Scopes "+Scope1+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//scan scope 2 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+StorageA);
		Description="Scan Scopes "+Scope2+" into "+StorageA;
		
		ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Cabinet,CultureObtained,Integer.toString(CycleCount2));
		
		String StorageIn_AssocScope2=ResultStorageInScope1[0];
		OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);

		
		ActualScope2Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		ResultScope2Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle2, ActualScope2Cycle2);
		temp=ResultScope2Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle2, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle2="+ResultScope2Cycle2);
		Result="ResultScope2Cycle1="+ResultScope2Cycle1;

		Expected="Scopes "+Scope2+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount2;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//scan scope 3 into reprocessor
		Unifia_Admin_Selenium.StepNum++;
		String Reason3="Used in Procedure";
		Reason2="";
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope3+" into "+Repro1);
		Description="Scan Scopes "+Scope3+" into "+Repro1;
		
		ResultReproScans=Repro_A.ReprocessingRoomScans(Repro1,Scope3,"",Staff1,Staff1ID,Reason3,Reason2,MRCPass,Integer.toString(CycleCount3),"0");
		
		int Scope3InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		int Scope3OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		String OverallReproResult3=ResultReproScans[5];
		temp=OverallReproResult3.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallReproResult3, OverallResult);

		System.out.println("OverallResult="+OverallResult);

		ExpectedScope3Cycle2[0]=5;
		ExpectedScope3Cycle2[3]=Scope3InReproAssociationID;
		ExpectedScope3Cycle2[4]=MRCAssociationID;
		ExpectedScope3Cycle2[5]=Scope3OutReproAssociationID;

		Arrays.sort(ExpectedScope3Cycle2);

		ActualScope3Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope3ID, CycleCount3);

		ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);

		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		Result="ResultScope3Cycle2="+ResultScope3Cycle2;

		Expected="Scopes "+Scope3+" was reprocessed in "+Repro1+". The ScopeCycle table is correct for CycleCount="+CycleCount3;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//scan scope 3 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope3+" into "+StorageA);
		Description="Scan Scopes "+Scope3+" into "+StorageA;
		
		ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope3,Staff5,Staff5ID,Cabinet,CultureObtained,Integer.toString(CycleCount3));
		
		String StorageIn_AssocScope3=ResultStorageInScope1[0];
		String OverallStorageIn_Result3=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result3.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result3, OverallResult);

		
		ActualScope3Cycle2=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope3ID, CycleCount3);

		ResultScope3Cycle2=R_V.VerifyScopeCycleAssociations(ExpectedScope3Cycle2, ActualScope3Cycle2);
		temp=ResultScope3Cycle2.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope3Cycle2, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope3Cycle2="+ResultScope3Cycle2);
		Result="ResultScope3Cycle2="+ResultScope3Cycle2;

		Expected="Scopes "+Scope3+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount3;
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
