package TestFrameWork.Emulator;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;
//import TestFrameWork

public class CycleMgmt_Regression {
	
	/**
	 * J.Fetrow 7/12/1026
	 * This test server a dual purpose:  1.  POC with and Verifying the DB, 2.  Regression Test of Cycle Mgmt
	 * The Test will First Scan a scope the whole way through the workflow
	 * Then it will verify cycle mgmt works in reversing scenarios
	 * Then it will verify that cycle count does no increase while checking in and out of the cabinet
	 * This test will not verify anything with KE
	 * 
	 * The Test Data it will use
	 * 
	 *  Scopes:  Scope7, Scope8 and Scope9
	 *  Assumption is that Scope7 and Scope 8 both are in the cabinet at the start of the test
	 *  Location Scanners:
	 * 	-  Procedure Room:  Procedure Room 7 Assumption is that it is closed at the start of the Test
	 * 	-  Soiled Room:  Sink 5
	 * 	-  Bioburden:
	 * 	-  Reprocessing:  F2 Reprocessor 7
	 * 	-  Culturing:  CultureA
	 * 	-  Cabinet:  F2 Storage Area A (2 cabinets)
	 * 	-  Admin:  F2 Administration
	 * 
	 * Out of Scope:
	 * Waiting Room
	 * In Procedure Room Pt Reg
	 * Staff
	 * 	-  Staff1
	 * 	-  Staff2
	 * 	-
	 * 
	 */
	
	TestFrameWork.Emulator.GetIHValues IHV;
	TestFrameWork.Emulator.Emulator_Actions Act;
	TestFrameWork.Emulator.Emulator_Verifications Verf;
	TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	
	public String ConnString;
	public String EmUrl;
	
	public String Scp1Cyc_Curr;
	public String Scp1Cyc_Prev;
	public String Scp2Cyc_Curr;
	public String Scp2Cyc_Prev;
	//
	public String Scp1Assoc_Curr;
	public String Scp1Assoc_Prev;
	public String Scp2Assoc_Curr;
	public String Scp2Assoc_Prev;
	public String Scp3Assoc_Curr;
	public String Scp3Assoc_Prev;
	
	public String LocationName;
	public String[] ReturnResult;
	
	public String PRScan;
	public String SRScan;
	public String ReproScan;
	public String CultScan;
	public String AdminScan;
	public String BioScan;
	public String CabScan;
	
	public String Scope1;
	public String Scope2;
	public String Scope3;
	public String Scope1State;
	public String Scope1Loc;
	public String Scope2State;
	public String Scope2Loc;
	public String Scope3State;
	public String Scope3Loc;
	
	public String Scope1IH;
	public String Staff2_Assoc;
	public String Scope2IH;
	public String Staff1_Assoc;
	public String Phys_Assoc;
	public String Staff1IH;
	public String Staff2IH;
	public String CultureIH;
	
	public String Staff1;
	public String Staff2;
	public String StaffAssoc;
	public String Phys;
	
	
	public String PR_Assoc;
	public String SR_Assoc;
	public String R_Assoc;
	public String Bio_Assoc;
	public String Cult_Assoc;
	public String Cab_Assoc;
	public String Admin_Assoc;
	public String Actual_Assoc;
	
	public String RmState;
	
	public String ResultSet[];
	
	public String FileName;
	public int StepNum;
	public String Description;
	public String Expected;
	public String ExpectedRmSt;
	public String ExpectedState;
	public String Result;
	public String CycleEvent;
	public String ActualCyc;
	public String TCResult;
	public String Act_Assoc; 
	public String Patient;
	public int RICount;
	
	@BeforeTest
	public void PreTest(){
		System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");	
	}
	
	@Test
	public void Test()throws Exception{
		TCResult="Pass";
		ConnString="jdbc:sqlserver://10.170.93.224\\sqlexpress;databaseName=Unifia;user=unifia;password=0lympu$123";
		//EmUrl="https://sprinttest-09.mitlab.com:9751/";
		PRScan="Procedure Room 7";
		SRScan="Sink 5";
		ReproScan="F2 Reprocessor 7";
		CultScan="";
		AdminScan="F2 Administration";
		BioScan="";
		CabScan="F2 Storage Area A";
		Scope1="Scope7";
		Scope2="Scope8";
		Scope3="Scope9";
		Staff1="Tech1 Tech1(T01)";
		Staff2="Tech2 Tech1(T02)";
		Phys="Physician20 Physician20(MD20)";
		
		FileName="CycleMgmt_Regression_";
		StepNum=1;
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.TestCaseNumber=1;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL);
		//Start of the Test
		//Step 1-  Set Room needs cleaning
		System.out.println("Starting Step:  "+StepNum+" scan room Needs Cleaning");
		ExpectedRmSt="Needs Cleaning";
		Description="Set Room:  "+ExpectedRmSt;
		Act.ScanItem(PRScan,"Workflow Event","","Needs Cleaning","");
		Thread.sleep(4000);
		Expected="Room State is needs cleaning";
		RmState=IHV.Room_State(ConnString, PRScan);
		Result=IHV.Result_Room_State(RmState, ExpectedRmSt, PRScan);
		System.out.println(StepNum+":  "+Result+" 1");
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
				
		//Step 2 set room available
		StepNum++;
		System.out.println("Starting Step:  "+StepNum+" scan room Available");
		ExpectedRmSt="Available";
		Description="Set Room:  "+ExpectedRmSt;
		Act.ScanItem(PRScan,"Workflow Event","","Available","");
		Expected="Room State is available";
		RmState=IHV.Room_State(ConnString, PRScan);
		Result=IHV.Result_Room_State(RmState, ExpectedRmSt, PRScan);
		System.out.println(StepNum+":  "+Result+" 2");
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 3 scan scope 1 in room
		StepNum++;
		System.out.println("Starting Step:  "+StepNum+" scan scope 1 in room");
		CycleEvent="Pre-Procedure";
		Description="Scan Scope "+Scope1+" into "+PRScan;
		Expected=Scope1+" is scanned into "+PRScan+" as "+CycleEvent;
		
		Act.ScanItem(PRScan,"Scope","",Scope1,"");
		ResultSet=IHV.GetItemHistoryData(ConnString, PRScan);
		Scope1IH=ResultSet[0];
		Scp1Assoc_Curr=ResultSet[1];
		PR_Assoc=Scp1Assoc_Curr;
		ActualCyc=ResultSet[5];
		System.out.println(Scope1+"-  This is a change!!!!!!");
		ResultSet=IHV.Scp_State_Loc(ConnString, Scope1);
				
		Scope1Loc=ResultSet[1];
		//Result=IHV.Result_CycleEvent(ActualCyc, CycleEvent, PRScan, Scope1Loc);
		System.out.println(StepNum+":  "+Result+" 3");
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 4 scan scope 2 in room
		StepNum++;
		System.out.println("Starting Step:  "+StepNum+" scan scope 2 in room");
		CycleEvent="Pre-Procedure";
		Description="Scan Scope "+Scope2+" into "+PRScan;
		Expected=Scope2+" is scanned into "+PRScan+" as "+CycleEvent;
		Act.ScanItem(PRScan,"Scope","",Scope2,"");
		ResultSet=IHV.GetItemHistoryData(ConnString, PRScan);
		Scope2IH=ResultSet[0];
		Scp2Assoc_Curr=ResultSet[1];
		PR_Assoc=Scp2Assoc_Curr;
		ActualCyc=ResultSet[5];
		ResultSet=IHV.Scp_State_Loc(ConnString, Scope2);
		Scope2Loc=ResultSet[1];
		//Result=IHV.Result_CycleEvent(ActualCyc, CycleEvent, PRScan, Scope2Loc);
		System.out.println(StepNum+":  "+Result+" 4");
		System.out.println(StepNum+":  "+Result+" ?");
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 5 verify scope 1 and 2 are i the same assoc
		StepNum++;
		System.out.println("Starting Step:  "+StepNum+" verify scope 1 and 2 are i the same assoc");
		Description="Verify that "+Scope1+" and "+Scope2+" are in the same association";
		Result=IHV.Result_Same_Assoc(Scp1Assoc_Curr, Scp2Assoc_Curr);
		System.out.println(StepNum+":  "+Result+" 5");
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		
		//Step6  Scan staff to associate to scope 2
		StepNum++;
		System.out.println("Starting Step:  "+StepNum+" Scan staff to associate to scope 2");
		Description="Scan Staff:  "+Staff1+" in "+PRScan;
		Expected=Staff1+ "is assocaited to "+Scope2+" , both Association ID should be:  "+PR_Assoc;
		System.out.println("Made it here #1");
		System.out.println("Made it here #2");
		Act.ScanItem(PRScan, "Staff", "Tech", Staff1, "");
		ResultSet=IHV.GetItemHistoryNoCycleEvent(ConnString, PRScan);
		Staff1IH=ResultSet[0];
		Staff1_Assoc=ResultSet[1];
		Act_Assoc=IHV.GetRelatedITem_IHKey(ConnString, Scope2IH);
		Result= IHV.RelatedItem_Verf(Staff1IH, Act_Assoc);
		System.out.println(StepNum+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 6 (Repeated) additional Verify
		Description="Scan Staff:  "+Staff1+" in "+PRScan;
		Expected=Staff1+ "is assocaited to "+Scope2+" , both Association ID should be:  "+PR_Assoc;
		Result=IHV.Result_Same_Assoc(PR_Assoc, Staff1_Assoc);
		System.out.println(StepNum+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 7  Verify Scope 1 has no staff associated
		StepNum++;
		Description="Verify the "+Scope1+"pre-procedure scan should have no records in the Related Item table";
		Expected="The Related Item count for IH:  "+Scope1IH+" is 0.";
		RICount=IHV.GetRelatedItem_Count(ConnString, Scope1IH);
		Result=IHV.RelatedItemCount_Verf(RICount, 0);
		System.out.println(StepNum+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 8 Scan Patient into room
		StepNum++;
		String Pt="PID101010";
		Description="Scan Patient into Procedure Room:  "+PRScan;
		Expected="Patient:  "+Pt+" is added to the PPR association:  "+PR_Assoc;
		Act.ScanItem(PRScan, "Patient", "", Pt, "");
		ResultSet=IHV.GetItemHistoryData(ConnString, PRScan);
		Result=IHV.RelatedItem_Verf(PR_Assoc, ResultSet[1]);
		System.out.println(StepNum+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 9 Verify that the room is set to in use
		StepNum++;
		Description="Verify "+PRScan+" is set to to a State of In-use";
		Expected=PRScan+" is set to In-use";
		RmState=IHV.Room_State(ConnString, PRScan);
		Result=IHV.Result_Room_State(RmState, "In-use", PRScan);
		System.out.println(StepNum+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 10  Scan Doc to PR
		StepNum++;
		Description="Scan Physician:  "+Phys+" into "+PRScan;
		Expected="Physcian is gets the assocation:  "+PR_Assoc;
		Act.ScanItem(PRScan, "Staff", "Physician", Phys, "");
		ResultSet=IHV.GetItemHistoryData(ConnString, PRScan);
		Phys_Assoc=ResultSet[1];
		Result=IHV.Result_Same_Assoc(PR_Assoc, Phys_Assoc);
		System.out.println(StepNum+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step 11  Scan StartProcedure
		StepNum++;
		Description="Scan Start Procedure for the the current exam";
		Expected="Start Procedure event is associated to Association:  "+PR_Assoc;
		Act.ScanItem(PRScan, "Workflow Event", "", "Start Procedure", "");
		ResultSet=IHV.GetItemHistoryData(ConnString, PRScan);
		Actual_Assoc=ResultSet[1];
		Result=IHV.Result_Same_Assoc(PR_Assoc, Actual_Assoc);
		System.out.println(StepNum+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		TCResult=IHV.TCResult(TCResult, Result);
		
		//Step11 (Repeated)  Very WorkflowEvent
		Description="Verify that the Workflow Event is Start Procedure";
		Expected="The Workflow event in Item history is Start Procedure";
		
		//Step 12  Add scope 3 to PR
		StepNum++;
		
		//Step 13  Scan Stop Procedure
		StepNum++;
		
		//Step 14  PreClean Scope1
		StepNum++;
		
		//Step 15  Scan PreClean Staff for Scope 1
		StepNum++;
		
		//Step 16  Scan Room Needs Cleaning
		StepNum++;
		
		//Step 17  Scan Scope 2 PreClean
		StepNum++;
		
		//Step 18 Verify Scope 1 Association
		StepNum++;
		
		//Step 19  Verify Scope 2 Association
		StepNum++;
		
		//Step 20 verify Scope 1 and Scope 2 association is the same
		StepNum++;
		
		//Step 21 Verify Scope1 State-  Awaiting Manual Cleaning
		StepNum++;
		
		//Step 22 Verify Scope2 State-  Awaiting Manual Cleaning
		StepNum++;
		
		//Step 23 Scan Scope 1 into SR
		StepNum++;
		
		//Step 24 Scan Staff
		StepNum++;
		
		//Step 25 Scan LT Pass
		StepNum++;
		
		//Step 26 Scan MC Start
		StepNum++;
		
		//Step 27  Scan MC End
		StepNum++;
		
		//Step 28  Scan Scope 2 in SR
		StepNum++;
		
		//Step 29 Scan Staff
		StepNum++;
		
		//Step 30 Scan LT Fail
		StepNum++;
				
		//Step 31 Scan MC Start
		StepNum++;
				
		//Step 32  Scan MC End
		StepNum++;
		
		//Step 33 Verify different Associations for Scope 1 and 2
		StepNum++;
		
		//Step 34 Verify Cycle for Scope 1
		StepNum++;
		
		//Step 35 Verify Cycle for Scope 2
		StepNum++;
		
		//Step 36 Scan BioBurden Pass for Scope 1
		StepNum++;
		
		//Step 37 Scan BioBurden Result
		StepNum++;
		
		//Step 38 Verify Scope 1 Cycle
		StepNum++;
		
		//Step 39 Verify Scope 1 Association
		StepNum++;
		
		//Step 40 Scan Staff
		StepNum++;
		
		//Step 41 Scan Scope 1 in Reprocessor
		StepNum++;
		
		//Step 42 Scan Staff 
		StepNum++;
		
		//Step 43 Scan Scope 2 in Reprocessor
		StepNum++;
		
		//Step 44 Scan Reason for Reprocessing
		StepNum++;
		
		//Step 45 Scan Staff
		StepNum++;
		
		//Step 46 Scan MRC Test
		StepNum++;
		
		//Step 47 Scan Scope 1 out of Reprocessor
		StepNum++;
		
		//Step 48 Scan Staff
		StepNum++;
		
		//Step 49 Scan Scope 2 out of Reprocessor
		StepNum++;
		
		//Step 50 Scan Staff
		StepNum++;
		
		//Step 51 Verify Scope 1 Association
		StepNum++;
		
		//Step 52 Verify Scope 2 Association
		StepNum++;
		
		//Step 53 Verify Scope 1 and Scope 2 associations are <>
		StepNum++;
		
		//Step 54 Verify Scope 1 Cycle
		StepNum++;
		
		//Step 55 Verify Scope 2 Cycle
		StepNum++;
		
		//Step 56 Scan Scope 2 for culture Result
		StepNum++;
		
		//Step 57 Scan Staff
		StepNum++;
		
		//Step 58 Scan Scope 1 for culture Result
		StepNum++;
		
		//Step 59 scan Staff
		StepNum++;
		
		//Step 60  Scan Scope 1 into Cabinet
		StepNum++;
		
		//Step 61 Enter Cabinet #
		StepNum++;
		
		//Step 62  Scan Staff
		StepNum++;
		
		//Step 63 Scan Scope 2 into cabinet
		StepNum++;
		
		//Step 64 Enter Cabinet #
		StepNum++;
		
		//Step 65 Scan Staff
		StepNum++;
		
		//Step 66 Verify Scope 1 association
		StepNum++;
		
		//Step 67 Verify Scope 2 association
		StepNum++;
		
		//Step 68 Verify Cycle for Scope 1
		StepNum++;
		
		//Step 69 Verify Cycle for Scope 2
		StepNum++;
		
		//Step 70 set PR to Available
		StepNum++;
		
		//Step 71 Scan Scope 1 out of the cabinet
		StepNum++;
		
		//Step 72 Scan Staff
		StepNum++;
		
		//Step 73 Verify Scope 1 association
		StepNum++;
		
		//Step 74 Scan Scope 1 into the cabinet
		StepNum++;
		
		//Step 75 Scan Staff
		StepNum++;
		
		//Step 76 Verify Scope 1 Association
		StepNum++;
		
		//Step 77 Verify Scope Cycle -it should not increase
		StepNum++;
		
		//Step 78 Scan Scope 1 out of the cabinet
		StepNum++;
		
		//Step 79 Scan Staff
		StepNum++;
		
		//Step 80 Verify Scope 1 association
		StepNum++;
		
		//Step 81 Scan Scope 1 into the cabinet
		StepNum++;
		
		//Step 82 Scan Staff
		StepNum++;
		
		//Step 83 Verify Scope 1 Association
		StepNum++;
		
		//Step 84 Verify Scope Cycle- it should not increase
		StepNum++;
		
		//Step 85 Scan Scope 1 out of cabinet
		
		//Step 86 Enter 1 MRC pass
		
		//Step 87 Scan Staff
		
		//Step 88 Scan Scope 2 out of Cabinet 
		StepNum++;
		
		//Step 89 Scan Staff
		
		//Step 90 Scan Scope 2 in PR
		
		//Step 91 Enter Culture Result for Scope 2
		
		//Step 92 Scan Staff
		
		//Step 93 Verify Scope 2 cycle Increased
		
		//Step 94 Scan Patient into PR
		
	
		//Step 95 Scan Scope 2 in Different Procedure Room (PR2)
		
		//Step 96 Scan Staff
		
		//Step 97 Verify Cycle increased
		
		//Step 98 Scan Patient into PR (PR2)
		
		//Step 99 Scan Needs Cleaning in PR (PR1)
		
		//Step 100 Scan Needs Cleaning in PR (PR2)
		
		//Step 101 Scan Scope 2 into Soiled Room (SR1)
		
		//Step 102 Verify Cycle did not increase
		
		//Step 103 Scan Scope into Soiled Room (SR2)
		
		//Step 104 Verify Cycle increased
		
		//Step 105 Scan Scope 2 into PR (PR1)
		
		//Step 106 Verify Cycle increased
		
		//Step 107 Scan Staff
		
		//Step 108 Verify Location State PR1
		
		//Step 109 Scan Scope 2 for Pre-clean in PR1
		
		//step 110 Scan Staff 
		
		//Step 111 Scan Needs Cleaning in PR (PR1)
		
		//Step 112 Scan Scope in SR (SR1)
		
		//Step 113 Verify Cycle did not increase
		
		//Step 114 Scan Scope in Reprocessor 1
		
		//Step 115 Verify Cycle did not increase
		
		//Step 116 Scan Scope in Reprocesssor 2
		
		//Step 117 Verify Cycle increased
		
		//Step 118 Scan Scope in SR1
		
		//Step 119 Verify Cycle increased
		
		//Step 120 Scan Scope into Reprocessor 1
		
		//Step 121 Verify Cycle increased
		
		//Step 122 Scan Scope into Cabinet
		
		//Step 123 Enter Cabinet Number
		
		//Step 124 Verify Cycle did not increase
		
		//Step 125 Scan Scope into Reprocessor 1
		
		//Step 126 Verify Cycle increased
		
		//Step 127 Scan Scope into Cabinet
		
		//Step 128 Enter Cabinet Number
		
		//Step 129  Verify the cycle did not increase
		
		//Step 130 Scan Scope 1 out of facility
		
		//Step 131 Scan Out of facility Location
		
		//Step 132 Scan Staff
		
		//Step 133 Scan Scope into Facility
		
		//Step 134 Verify Scope cycle did not increase
		
		//Step 135 Scan Staff
		
		
		
		
	}
	
	@AfterTest
	public void PostTTest(){
		System.out.println("The Test Case Run was:  "+TCResult);
		IHV.Close_Exec_Log(FileName, TCResult, Unifia_Admin_Selenium.TestCaseNumber);
	}
}
