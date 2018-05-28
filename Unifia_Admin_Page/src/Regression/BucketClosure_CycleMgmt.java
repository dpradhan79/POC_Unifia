package Regression;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class BucketClosure_CycleMgmt {
	private GeneralFunc GF;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.Emulator.Emulator_Actions EM_A;
	private TestFrameWork.Emulator.Emulator_Verifications EM_V;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestFrameWork.RegressionTest.ReprocessingRoom.ReprocessingRegression_Actions Repro_A;
	private TestFrameWork.RegressionTest.ProcedureRoom.PR_Regression_Actions PR_A;
	private TestFrameWork.RegressionTest.SoiledArea.SoiledRegression_Actions Soiled_A;
	private TestDataFunc TDF;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;

	private String Scope1="Scope1";
	private String Scope2="Scope2";
	private String Scope3="Scope3";
	
	// Location names used for scanning.
	private String PR1 = "Procedure Room 1";
	private String Repro1 = "Reprocessor 1";
	private String Sink1 = "Sink 1";
	private String Sink2 = "Sink 2";
	private String StorageB = "Storage Area B";
	private String CultureA="CultureA";
	
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
	private String Phy="Physician1 Physician1(MD01)";
	private String phyID="MD01";
	private String Patient="MRN111111";
	
	private String LTFail="Leak Test Fail";
	private String LTPass="Leak Test Pass";
	private String MCStart="Yes"; //perform the Manual Clean Start Scan 
	private String MCEnd="Yes"; //perform the Manual Clean End Scan 
	private String MRCPass="MRC Pass";
	public String Reason1="Used in Procedure";
	
	private boolean Res=false;
	private String Description="";
	private String Expected="";
	private String Result="";
	
	private Connection conn= null;
	private ResultSet scope;
		
	private int KE=0;
	private int Bioburden=0;
	private int Culture=0;	
	public String Changes="No";

	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, AWTException {
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
		Unifia_Admin_Selenium.Scope2ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope2ExpectedExamCount=0;
		Unifia_Admin_Selenium.Scope3ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope3ExpectedExamCount=0;
		Unifia_Admin_Selenium.Scope4ExpectedReproCount=0;
		Unifia_Admin_Selenium.Scope4ExpectedExamCount=0;

		Unifia_Admin_Selenium.XMLFileName="BucketClosureCycleMgmt_";
		Unifia_Admin_Selenium.XMLFileName=IHV.Start_Exec_Log(Unifia_Admin_Selenium.XMLFileName);
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.StepNum=1;
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		
		Thread.sleep(500);
		int StaffPK=0;
		String OverallResult="Pass";
		//Start of Test
		//Scenario - 1
		// verify correct cycle event of 3 is displayed when second time the scope is scanned into procedure room (defect 8436) 
		System.out.println("Start of Scenario -1");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		String Location=PR1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Available", "");
		Description="Scan Scope "+Scope1+" into "+PR1;
		Expected="Scope "+Scope1+" is Scanned into "+PR1;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope1, "");
		Thread.sleep(10000);
		
		String ExpectedCycleEvent="Pre-Procedure";
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String InitialScopeIn_AssocID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		String ResultCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		
		// Scope info
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,InitialScopeIn_AssocID);
		String Scope1RefNo=ScopeInfo[0];
		String Scope1SerialNo=ScopeInfo[1];
		String Scope1Model=ScopeInfo[2];
		String Scope1ExamTime=ScopeInfo[3];
		//
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];

		int LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		String ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		String []temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		String ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		String ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);

		Result="ResultCycleEvent = "+ResultCycleEvent+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+".";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Available", "");
		
		//SRM validations
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+Scope1+" into "+Location+". ";

		String Result_RefNum=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		String Result_ScopeName1=WF_V.Verify_ScopeName(Scope1);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

		String Result_Scope1PR=WF_V.Verify_PR(Location);
		System.out.println("Result_Scope1PR=:"+Result_Scope1PR);
		temp=Result_Scope1PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1PR,OverallResult);

		String Result_Scope1ExamDate=WF_V.Verify_ExamDate(Scope1ExamTime);
		System.out.println("Result_Scope1ExamDate=:"+Result_Scope1ExamDate);
		temp=Result_Scope1ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ExamDate,OverallResult);

		String Result_Scope1InStaff=WF_V.Verify_PRInStaff("");
		System.out.println("Result_Scope1InStaff=:"+Result_Scope1InStaff);
		temp=Result_Scope1InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1InStaff,OverallResult);
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope1_PRIn="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1PR="+Result_Scope1PR+". Result_Scope1ExamDate"+Result_Scope1ExamDate
				+". Result_Scope1InStaff="+Result_Scope1InStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning into "+Location;
		Expected=Description;
		
		
		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID==-"
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope1ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope1ExpectedReproCount);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);
		//
		
		//Scanning same scope into same procedure room again to see that a new AssociationID is created
		Description="Scan Scope "+Scope1+" into "+PR1;
		Expected="Scope "+Scope1+" is Scanned into "+PR1+" and a new AssociationID is created";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		
		EM_A.ScanItem(PR1, "Scope", "", Scope1, "");
		ExpectedCycleEvent="Pre-Procedure";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String Scope_IHID=Scope_IH[0];
		String ScopeIn_AssocID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		ResultCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		String ResultAssoc=IHV.Result_Different_Assoc(InitialScopeIn_AssocID, ScopeIn_AssocID);
		
		// Scope info
		
		String ScopeInfo2[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,ScopeIn_AssocID);
		Scope1RefNo=ScopeInfo2[0];
		Scope1SerialNo=ScopeInfo2[1];
		Scope1Model=ScopeInfo2[2];
		Scope1ExamTime=ScopeInfo2[3];
		//
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];

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
		Result="ResultAssoc = "+ResultAssoc+" ResultCycleEvent = "+ResultCycleEvent+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+".";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		
		Description="Scan Staff "+Staff1+" into "+PR1;
		Expected="Staff "+Staff1+" is Scanned into "+PR1;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Tech", Staff1, "");
		String Staff_IH[]=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		String ActualStaff_IHID=Staff_IH[0];
		String Staff_AssocID=Staff_IH[1];
		ResultAssoc=IHV.Result_AssociationID(ScopeIn_AssocID, Staff_AssocID);
		String ExpectedStaff_IHID=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope_IHID);
		String ResultRelatedItem=IHV.RelatedItem_Verf(ExpectedStaff_IHID,ActualStaff_IHID);
				 
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff1ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		Result="ResultAssoc = "+ResultAssoc+" ResultRelatedItem"+ResultRelatedItem+". ResultLastStaff="+ResultLastStaff+".";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		
		// SRM Verification after staff is scanned when scope is scanned for the second time
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+Scope1+" staff "+ Staff1 +" into "+Location+". ";

		Result_RefNum=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		Result_ScopeName1=WF_V.Verify_ScopeName(Scope1);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

		Result_Scope1PR=WF_V.Verify_PR(Location);
		System.out.println("Result_Scope1PR=:"+Result_Scope1PR);
		temp=Result_Scope1PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1PR,OverallResult);

		Result_Scope1ExamDate=WF_V.Verify_ExamDate(Scope1ExamTime);
		System.out.println("Result_Scope1ExamDate=:"+Result_Scope1ExamDate);
		temp=Result_Scope1ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ExamDate,OverallResult);

		Result_Scope1InStaff=WF_V.Verify_PRInStaff(Staff1ID);
		System.out.println("Result_Scope1InStaff=:"+Result_Scope1InStaff);
		temp=Result_Scope1InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1InStaff,OverallResult);
		WF_A.Cancel(Changes);
		Expected=Description;
		Result_SRM_Scope1_PRIn="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1PR="+Result_Scope1PR+". Result_Scope1ExamDate"+Result_Scope1ExamDate
				+". Result_Scope1InStaff="+Result_Scope1InStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning into "+Location;
		Expected=Description;
		
		resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+Staff1ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope1ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope1ExpectedReproCount);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);
		//
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Patient/Procedure Status", "");
		Description="Scan Patient "+Patient+" into "+PR1;
		Expected="Patient "+Patient+" is Scanned into "+PR1;
		ExpectedCycleEvent="Patient In";
		Unifia_Admin_Selenium.Scope1ExpectedExamCount++;
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Patient", "", Patient, "");
		String Patient_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		ActualCycleEvent=Patient_IH[5];
		ResultCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedReproCount));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(Unifia_Admin_Selenium.Scope1ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);

		
		Result="ResultCycleEvent = "+ResultCycleEvent+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Physician", Phy, "");
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", 	"Procedure Start", "");
		Thread.sleep(60000);//waiting for 1 min to perform procedure
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", 	"Procedure End", "");
		
		Description="Scan Scope "+Scope1+" precleaned for "+PR1;
		Expected="Scope "+Scope1+" is Scanned into "+PR1+" for pre-clean";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope1, "");
		ExpectedCycleEvent="Pre-Clean Complete";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		Scope_IHID=Scope_IH[0];
		String ScopePre_AssocID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		ResultCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		StaffPK=0;
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
		
		Result="ResultCycleEvent = "+ResultCycleEvent+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+".";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		
		Description="Scan Staff "+Staff1+" into "+PR1;
		Expected="Staff "+Staff1+" is Scanned into "+PR1;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Tech", Staff1, "");
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PR1);
		ActualStaff_IHID=Staff_IH[0];
		Staff_AssocID=Staff_IH[1];
		ResultAssoc=IHV.Result_AssociationID(ScopePre_AssocID, Staff_AssocID);
		ExpectedStaff_IHID=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope_IHID);
		ResultRelatedItem=IHV.RelatedItem_Verf(ExpectedStaff_IHID,ActualStaff_IHID);
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,Staff1ID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		
		Result="ResultAssoc = "+ResultAssoc+" ResultRelatedItem = "+ResultRelatedItem+". ResultLastStaff="+ResultLastStaff+".";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Needs Cleaning", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Available", "");		
		
		IHV.Exec_Test_Case(Unifia_Admin_Selenium.XMLFileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(Unifia_Admin_Selenium.XMLFileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		System.out.println("End of Scenario -1");
		
		
		// SRM Verification after staff is scanned when scope is scanned for the second time
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+Scope1+" into "+Location+". ";

		Result_RefNum=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		Result_ScopeName1=WF_V.Verify_ScopeName(Scope1);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

		Result_Scope1PR=WF_V.Verify_PR(Location);
		System.out.println("Result_Scope1PR=:"+Result_Scope1PR);
		temp=Result_Scope1PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1PR,OverallResult);

		Result_Scope1ExamDate=WF_V.Verify_ExamDate(Scope1ExamTime);
		System.out.println("Result_Scope1ExamDate=:"+Result_Scope1ExamDate);
		temp=Result_Scope1ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ExamDate,OverallResult);

		Result_Scope1InStaff=WF_V.Verify_PRInStaff(Staff1ID);
		System.out.println("Result_Scope1InStaff=:"+Result_Scope1InStaff);
		temp=Result_Scope1InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1InStaff,OverallResult);
		
		
		String Result_Scope1Phy=WF_V.Verify_Physician(phyID);
		temp=Result_Scope1Phy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Phy,OverallResult);
		System.out.println("Result_Scope1Phy"+Result_Scope1Phy);

		String Result_Scope1Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope1Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Patient,OverallResult);
		System.out.println("Result_Scope1Patient=:"+Result_Scope1Patient);
		
		ScopeInfo=IHV.GetProcStartEndTime(Unifia_Admin_Selenium.connstring,ScopeIn_AssocID);
		String ProcStartTime=ScopeInfo[0];
		String ProcEndTime=ScopeInfo[1];
		
		String Result_Scope1ProcStart=WF_V.Verify_ProcStart(ProcStartTime);
		temp=Result_Scope1ProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ProcStart,OverallResult);
		System.out.println("Result_Scope1ProcStart=:"+Result_Scope1ProcStart);

		String Result_Scope1ProcEnd=WF_V.Verify_ProcEnd(ProcEndTime);
		temp=Result_Scope1ProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ProcEnd,OverallResult);
		System.out.println("Result_Scope1ProcEnd=:"+Result_Scope1ProcEnd);

		String Result_Scope1Preclean=WF_V.Verify_PreClean("Yes");
		temp=Result_Scope1Preclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Preclean,OverallResult);
		System.out.println("Result_Scope1Preclean=:"+Result_Scope1Preclean);

		String Result_Scope1StaffPreclean=WF_V.Verify_PreCleanStaff(Staff1ID);		
		temp=Result_Scope1StaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1StaffPreclean,OverallResult);
		System.out.println("Result_Scope1StaffPreclean=:"+Result_Scope1StaffPreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		Result_SRM_Scope1_PRIn="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1PR="+Result_Scope1PR+". Result_Scope1ExamDate"+Result_Scope1ExamDate
				+". Result_Scope1InStaff="+Result_Scope1InStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning into "+Location;
		Expected=Description;
		
		resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+Staff1ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope1ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope1ExpectedReproCount);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);
				//
		
		//Scenario-2
		// First time when scope is scanned in sink area manual clean end is skipped. 
		// Verify the cycle count does not increase after the scope is again scanned (second time) into the sink area 
		
		//Scope into Procedure Room
		System.out.println("Start of Scenario-2");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope2, "");
		Thread.sleep(10000);
		String Scope_IH2[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String InitialScopeIn_AssocID2=Scope_IH2[1];
		ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope2,InitialScopeIn_AssocID2);
		String Scope2RefNo=ScopeInfo[0];
		String Scope2SerialNo=ScopeInfo[1];
		String Scope2Model=ScopeInfo[2];
		String Scope2ExamTime=ScopeInfo[3];
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Tech", Staff2, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Patient/Procedure Status", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Patient", "", Patient, "");
		Unifia_Admin_Selenium.Scope2ExpectedExamCount++;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Physician", Phy, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", 	"Procedure Start", "");
		Thread.sleep(60000);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", 	"Procedure End", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope2, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Tech", Staff2, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Needs Cleaning", "");
		//
		
		
		// SRM Verification after staff is scanned when scope is scanned for the second time
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope2RefNo);
		
		Description="Verify Scope Record Management of "+Scope2+" into "+PR1+". ";

		String Result_RefNum2=WF_V.Verify_RefNum(Scope2RefNo);
		temp=Result_RefNum2.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum2,OverallResult);
		
		String Result_ScopeModel2=WF_V.Verify_ScopeModel(Scope2Model);
		temp=Result_ScopeModel2.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel2,OverallResult);

		String Result_ScopeName2=WF_V.Verify_ScopeName(Scope2);
		temp=Result_ScopeName2.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName2,OverallResult);

		String Result_ScopeSerialNo2=WF_V.Verify_ScopeSerialNum(Scope2SerialNo);
		temp=Result_ScopeSerialNo2.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo2,OverallResult);

		System.out.println("Result_RefNum2=:"+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". Result_ScopeSerialNo2="+Result_ScopeSerialNo2);

		String Result_Scope2PR=WF_V.Verify_PR(PR1);
		System.out.println("Result_Scope2PR=:"+Result_Scope2PR);
		temp=Result_Scope2PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2PR,OverallResult);

		String Result_Scope2ExamDate=WF_V.Verify_ExamDate(Scope2ExamTime);
		System.out.println("Result_Scope2ExamDate=:"+Result_Scope2ExamDate);
		temp=Result_Scope2ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ExamDate,OverallResult);

		String Result_Scope2InStaff=WF_V.Verify_PRInStaff(Staff2ID);
		System.out.println("Result_Scope2InStaff=:"+Result_Scope2InStaff);
		temp=Result_Scope2InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2InStaff,OverallResult);
		
		
		String Result_Scope2Phy=WF_V.Verify_Physician(phyID);
		System.out.println("Result_Scope2Phy"+Result_Scope2Phy);
		temp=Result_Scope2Phy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Phy,OverallResult);
		
		String Result_Scope2Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope2Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Patient,OverallResult);
		System.out.println("Result_Scope2Patient=:"+Result_Scope2Patient);
		
		ScopeInfo=IHV.GetProcStartEndTime(Unifia_Admin_Selenium.connstring,InitialScopeIn_AssocID2);
		 ProcStartTime=ScopeInfo[0];
		 ProcEndTime=ScopeInfo[1];
		
		String Result_Scope2ProcStart=WF_V.Verify_ProcStart(ProcStartTime);
		temp=Result_Scope2ProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ProcStart,OverallResult);
		System.out.println("Result_Scope2ProcStart=:"+Result_Scope2ProcStart);

		String Result_Scope2ProcEnd=WF_V.Verify_ProcEnd(ProcEndTime);
		temp=Result_Scope2ProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ProcEnd,OverallResult);
		System.out.println("Result_Scope2ProcEnd=:"+Result_Scope2ProcEnd);

		String Result_Scope2Preclean=WF_V.Verify_PreClean("Yes");
		temp=Result_Scope2Preclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Preclean,OverallResult);
		System.out.println("Result_Scope2Preclean=:"+Result_Scope2Preclean);

		String Result_Scope2StaffPreclean=WF_V.Verify_PreCleanStaff(Staff2ID);		
		temp=Result_Scope2StaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2StaffPreclean,OverallResult);
		System.out.println("Result_Scope2StaffPreclean=:"+Result_Scope2StaffPreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope2_PRIn="Result_RefNum2=:"+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". "
				+ "Result_ScopeSerialNo2="+Result_ScopeSerialNo2+". Result_Scope2PR="+Result_Scope2PR+". Result_Scope2ExamDate"+Result_Scope2ExamDate
				+". Result_Scope2InStaff="+Result_Scope2InStaff+". Result_Scope2Phy="+Result_Scope2Phy+". Result_Scope2Patient="+Result_Scope2Patient
				+". Result_Scope2ProcStart="+Result_Scope2ProcStart+". Result_Scope2ProcEnd="+Result_Scope2ProcEnd+". Result_Scope2Preclean="+Result_Scope2Preclean
				+". Result_Scope2StaffPreclean="+Result_Scope2StaffPreclean;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope2+" after scanning into "+PR1;
		Expected=Description;
		
		String resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope2ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope2ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);
		//
		
		
		//Scope into Sink
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Scope", "", Scope2, "");
		
		 Scope_IH2=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Sink1);
		 String  SinkScopeIn_AssocID2=Scope_IH2[1];
		 ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope2,SinkScopeIn_AssocID2);
		 Scope2RefNo=ScopeInfo[0];
		 Scope2SerialNo=ScopeInfo[1];
		 Scope2Model=ScopeInfo[2];
		 Scope2ExamTime=ScopeInfo[3];
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Staff", "Tech", Staff2, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Test Result", "", LTPass, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Workflow Event", "", "Manual Clean Start", "");
		System.out.println("Manual Clean End is skipped");
		//
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope2RefNo);
		
		Description="Verify Scope Record Management of "+Scope2+" and Staff "+Staff2+" into "+Sink1+". ";

		Result_RefNum=WF_V.Verify_RefNum(Scope2RefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(Scope2Model);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(Scope2);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(Scope2SerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		System.out.println("Result_RefNum1=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo);
		
		ScopeInfo=IHV.GetMCStartTime(Unifia_Admin_Selenium.connstring,SinkScopeIn_AssocID2);
		String MCStartTime=ScopeInfo[0];
		
		String Result_Sink=WF_V.Verify_SoiledArea(Sink1);
		System.out.println("Result_Sink=:"+Result_Sink);
		temp=Result_Sink.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Sink,OverallResult);

		String Result_LTMCDate=WF_V.Verify_LTMCDate(Scope2ExamTime);
		System.out.println("Result_LTMCDate=:"+Result_LTMCDate);
		temp=Result_LTMCDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LTMCDate,OverallResult);

		String Result_SinkStaff=WF_V.Verify_SoiledStaff(Staff2ID);
		System.out.println("Result_SinkStaff=:"+Result_SinkStaff);
		temp=Result_SinkStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_SinkStaff,OverallResult);
		
		String LeakTestResult[]=LTPass.split(" ");//Taking Pass/Fail from Leak Test Pass/Leak Test Fail
		String Result_LT=WF_V.Verify_LT(LeakTestResult[2]);
		
		System.out.println("Result_LT=:"+Result_LT);
		temp=Result_LT.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LT,OverallResult);
		
		String Result_MCStart=WF_V.Verify_MCStart(MCStartTime);
		System.out.println("Result_MCStart=:"+Result_MCStart);
		temp=Result_MCStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_MCStart,OverallResult);
		
		String Result_MCEnd=WF_V.Verify_MCEnd("");
		System.out.println("Result_MCEnd=:"+Result_MCEnd);
		temp=Result_MCEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_MCEnd,OverallResult);
		
		WF_A.Cancel(Changes);
		
		Expected=Description;
		String Result_SRM_Scope_Soiled="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel
				+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_Sink"+Result_Sink
				+". Result_LTMCDate="+Result_LTMCDate+". Result_LTMCDate"+Result_LTMCDate+". Result_SinkStaff="+Result_SinkStaff
				+". Result_LT="+Result_LT+". Result_MCStart"+Result_MCStart+". Result_MCEnd"+Result_MCEnd;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope_Soiled);
		
		//Verify MAM details
		Description="Verify Management and Asset Management of "+Scope2+" and Staff "+Staff2ID+" into "+Sink1+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		String result_MAM=MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+Sink1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope2ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope2ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
		//
		
		
		//Scope into Reprocessor
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Scope", "", Scope2, "");
		Unifia_Admin_Selenium.Scope2ExpectedReproCount++;
		
		 Scope_IH2=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Repro1);
		 String  ReproScopeIn_AssocID2=Scope_IH2[1];
		 ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope2,ReproScopeIn_AssocID2);
		 Scope2RefNo=ScopeInfo[0];
		 Scope2SerialNo=ScopeInfo[1];
		 Scope2Model=ScopeInfo[2];
		 String Scope2ReproInTime=ScopeInfo[3];
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Staff", "Tech", Staff3, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Test Result", "", MRCPass, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Staff", "Tech", Staff3, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Scope", "", Scope2, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Staff", "Tech", Staff3, "");
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope2RefNo);
		
		Description="Verify Scope Record Management of "+Scope2+" and Staff "+Staff3ID+" into "+Repro1+". ";

		Result_RefNum=WF_V.Verify_RefNum(Scope2RefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		Result_ScopeModel=WF_V.Verify_ScopeModel(Scope2Model);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		Result_ScopeName=WF_V.Verify_ScopeName(Scope2);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(Scope2SerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo);

		String Result_Scope2Reprocessor=WF_V.Verify_Reprossor(Repro1);
		System.out.println("Result_Scope2Reprocessor=:"+Result_Scope2Reprocessor);
		temp=Result_Scope2Reprocessor.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Reprocessor,OverallResult);

		String Result_Scope2ReproReason=WF_V.Verify_ReproReason(Reason1);
		System.out.println("Result_Scope2ReproReason=:"+Result_Scope2ReproReason);
		temp=Result_Scope2ReproReason.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproReason,OverallResult);

		String Result_Scope2ReproInStaff=WF_V.Verify_ReproInStaff(Staff3ID);
		System.out.println("Result_Scope2ReproInStaff=:"+Result_Scope2ReproInStaff);
		temp=Result_Scope2ReproInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproInStaff,OverallResult);

		String Result_Scope2ReproInTime=WF_V.Verify_ReproScopeInTime(Scope2ReproInTime);
		System.out.println("Result_Scope2ReproInTime=:"+Result_Scope2ReproInTime);
		temp=Result_Scope2ReproInTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproInTime,OverallResult);

		String Result_Scope2ReproStart=WF_V.Verify_ReproStartTime("");
		System.out.println("Result_Scope2ReproStart=:"+Result_Scope2ReproStart);
		temp=Result_Scope2ReproStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStart,OverallResult);

		String Result_Scope2ReproStartStaff=WF_V.Verify_ReproStartStaff("");
		System.out.println("Result_Scope2ReproStartStaff=:"+Result_Scope2ReproStartStaff);
		temp=Result_Scope2ReproStartStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStartStaff,OverallResult);

		String Result_Scope2ReproTemp=WF_V.Verify_ReproTemp("");
		System.out.println("Result_Scope2ReproTemp=:"+Result_Scope2ReproTemp);
		temp=Result_Scope2ReproTemp.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproTemp,OverallResult);
	
		String Result_Scope2ReproStatus=WF_V.Verify_ReproStatus("Unknown");
		System.out.println("Result_Scope2ReproStatus=:"+Result_Scope2ReproStatus);
		temp=Result_Scope2ReproStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStatus,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope2_ReproIn="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_Scope2Reprocessor="+Result_Scope2Reprocessor+". Result_Scope2ReproReason"+Result_Scope2ReproReason
				+". Result_Scope2ReproInStaff"+Result_Scope2ReproInStaff+". Result_Scope2ReproTemp="+Result_Scope2ReproTemp
				+". Result_Scope2ReproStatus="+Result_Scope2ReproStatus;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_ReproIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope2+" after scanning into "+Repro1;
		Expected=Description;

		resultScope2_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+Repro1+" (YFN);LAST SCAN STAFF ID=="+Staff3ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope2ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope2ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);
		
		//Making Procedure Room Available
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Available", "");
		
		//Scope into Procedure Room
		int ExpectedCycleCount=1;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope2, "");
		Scope_IH2=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		 String  ProcScopeIn_AssocID2=Scope_IH2[1];
		 ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope2,ProcScopeIn_AssocID2);
		 Scope2RefNo=ScopeInfo[0];
		 Scope2SerialNo=ScopeInfo[1];
		 Scope2Model=ScopeInfo[2];
		 Scope2ExamTime=ScopeInfo[3];
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Tech", Staff4, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Patient/Procedure Status", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Patient", "", Patient, "");
		Unifia_Admin_Selenium.Scope2ExpectedExamCount++;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Physician", Phy, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", 	"Procedure Start", "");
		Thread.sleep(60000);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", 	"Procedure End", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope2, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Tech", Staff4, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Needs Cleaning", "");
		
		// SRM Verification after staff is scanned when scope is scanned for the second time
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope2RefNo);
		
		Description="Verify Scope Record Management of "+Scope2+" into "+PR1+". ";

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

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". Result_ScopeSerialNo2="+Result_ScopeSerialNo2);

		Result_Scope2PR=WF_V.Verify_PR(PR1);
		System.out.println("Result_Scope2PR=:"+Result_Scope2PR);
		temp=Result_Scope2PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2PR,OverallResult);

		Result_Scope2ExamDate=WF_V.Verify_ExamDate(Scope2ExamTime);
		System.out.println("Result_Scope2PR=:"+Result_Scope1ExamDate);
		temp=Result_Scope2ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2ExamDate,OverallResult);

		Result_Scope2InStaff=WF_V.Verify_PRInStaff(Staff4ID);
		System.out.println("Result_Scope2InStaff=:"+Result_Scope2InStaff);
		temp=Result_Scope2InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1InStaff,OverallResult);
		
		
		Result_Scope2Phy=WF_V.Verify_Physician(phyID);
		temp=Result_Scope2Phy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Phy,OverallResult);
		System.out.println("Result_Scope2Phy"+Result_Scope2Phy);

		Result_Scope2Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope2Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Patient,OverallResult);
		System.out.println("Result_Scope2Patient=:"+Result_Scope2Patient);
		
		ScopeInfo=IHV.GetProcStartEndTime(Unifia_Admin_Selenium.connstring,ProcScopeIn_AssocID2);
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

		Result_Scope2Preclean=WF_V.Verify_PreClean("Yes");
		temp=Result_Scope2Preclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2Preclean,OverallResult);
		System.out.println("Result_Scope2Preclean=:"+Result_Scope2Preclean);

		Result_Scope2StaffPreclean=WF_V.Verify_PreCleanStaff(Staff4ID);		
		temp=Result_Scope2StaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope2StaffPreclean,OverallResult);
		System.out.println("Result_Scope2StaffPreclean=:"+Result_Scope2StaffPreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		Result_SRM_Scope2_PRIn="Result_RefNum2=:"+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". "
				+ "Result_ScopeSerialNo2="+Result_ScopeSerialNo2+". Result_Scope2PR="+Result_Scope2PR+". Result_Scope2ExamDate"+Result_Scope2ExamDate
				+". Result_Scope2InStaff="+Result_Scope2InStaff+". Result_Scope2Phy="+Result_Scope2Phy+". Result_Scope2Patient="+Result_Scope2Patient
				+". Result_Scope2ProcStart="+Result_Scope2ProcStart+". Result_Scope2ProcEnd="+Result_Scope2ProcEnd
				+". Result_Scope2Preclean="+Result_Scope2Preclean+". Result_Scope2StaffPreclean="+Result_Scope2StaffPreclean;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope2+" after scanning into "+PR1;
		Expected=Description;
		
		resultScope2_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff4ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope2ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope2ExpectedReproCount);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);
		
		//Scope into Sink
		Description="Scan Scope "+Scope2+" into "+Sink1;
		Expected="Scope "+Scope2+" is Scanned into "+Sink1+" and CycleCount is not increased";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Scope", "", Scope2, "");
		int CycleCount=0;
		try{
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
			Statement statement =conn.createStatement();
			String stmt="select CycleCount from ScopeStatus where ScopeID_FK=(select ScopeID_PK from Scope where Name='"+Scope2+"')";
			ResultSet Scope=statement.executeQuery(stmt);
			while(Scope.next()){
				CycleCount=Scope.getInt(1);
			}
			statement.close();
			conn.close();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		if(CycleCount==ExpectedCycleCount){
			Result="ResultCycleCount = Pass- The CycleCount is same as that of the CycleCount in Procedure Room";
			System.out.println("Pass - Cycle count is not increased when the scope is scanned into sink for the second time (manual clean end is skipped in first time)");
		}else{
			Result="ResultCycleCount = Error- The CycleCount did not match the CycleCount in Procedure Room";
			System.out.println("Fail - Cycle count is increased when the scope is scanned into sink for the second time (manual clean end is skipped in first time)");
		}
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		
		IHV.Exec_Test_Case(Unifia_Admin_Selenium.XMLFileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(Unifia_Admin_Selenium.XMLFileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		System.out.println("End of Scenario -2");

		//Scenario-3
		//Making Procedure Room Available
		// Enter the culture result and do not scan the staff. 
		// Scan scope into procedure room then it should not prompt for entering the culture results. 
		System.out.println("Start of Scenario-3");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Available", "");
		
		//Scope into ProcedureRoom
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope3, "");
		String Scope_IH3[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		 String  ProcScopeIn_AssocID3=Scope_IH3[1];
		 Scope_IH3=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope3,ProcScopeIn_AssocID3);
		 String Scope3RefNo=Scope_IH3[0];
		 String Scope3SerialNo=Scope_IH3[1];
		 String Scope3Model=Scope_IH3[2];
		 String Scope3ExamTime=Scope_IH3[3];
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Tech", Staff4, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Patient/Procedure Status", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Patient", "", Patient, "");
		Unifia_Admin_Selenium.Scope3ExpectedExamCount++;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Physician", Phy, "");
		Thread.sleep(60000);//waiting 1 min
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope3, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Staff", "Tech", Staff4, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Needs Cleaning", "");
		
		// SRM Verification after staff is scanned when scope is scanned for the second time
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope3RefNo);
		
		Description="Verify Scope Record Management of "+Scope3+" into "+PR1+". ";

		Result_RefNum=WF_V.Verify_RefNum(Scope3RefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		Result_ScopeModel=WF_V.Verify_ScopeModel(Scope3Model);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		Result_ScopeName=WF_V.Verify_ScopeName(Scope3);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(Scope3SerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo);

		String Result_Scope3PR=WF_V.Verify_PR(PR1);
		System.out.println("Result_Scope3PR=:"+Result_Scope3PR);
		temp=Result_Scope3PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3PR,OverallResult);

		String Result_Scope3ExamDate=WF_V.Verify_ExamDate(Scope3ExamTime);
		System.out.println("Result_Scope3ExamDate=:"+Result_Scope3ExamDate);
		temp=Result_Scope3ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ExamDate,OverallResult);

		String Result_Scope3InStaff=WF_V.Verify_PRInStaff(Staff4ID);
		System.out.println("Result_Scope3InStaff=:"+Result_Scope3InStaff);
		temp=Result_Scope3InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3InStaff,OverallResult);
		
		
		String Result_Scope3Phy=WF_V.Verify_Physician(phyID);
		temp=Result_Scope3Phy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Phy,OverallResult);
		System.out.println("Result_Scope3Phy"+Result_Scope3Phy);

		String Result_Scope3Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope3Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Patient,OverallResult);
		System.out.println("Result_Scope3Patient=:"+Result_Scope3Patient);
		
		
		String Result_Scope3ProcStart=WF_V.Verify_ProcStart("");
		temp=Result_Scope3ProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ProcStart,OverallResult);
		System.out.println("Result_Scope3ProcStart=:"+Result_Scope3ProcStart);

		String Result_Scope3ProcEnd=WF_V.Verify_ProcEnd("");
		temp=Result_Scope3ProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ProcEnd,OverallResult);
		System.out.println("Result_Scope3ProcEnd=:"+Result_Scope3ProcEnd);

		String Result_Scope3Preclean=WF_V.Verify_PreClean("Yes");
		temp=Result_Scope3Preclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Preclean,OverallResult);
		System.out.println("Result_Scope3Preclean=:"+Result_Scope3Preclean);

		String Result_Scope3StaffPreclean=WF_V.Verify_PreCleanStaff(Staff4ID);		
		temp=Result_Scope3StaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3StaffPreclean,OverallResult);
		System.out.println("Result_Scope3StaffPreclean=:"+Result_Scope3StaffPreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope3_PRIn="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_Scope3PR="+Result_Scope3PR+". Result_Scope3ExamDate"+Result_Scope3ExamDate
				+". Result_Scope3InStaff="+Result_Scope3InStaff+". Result_Scope3Phy="+Result_Scope3Phy+". Result_Scope3Patient="+Result_Scope3Patient
				+". Result_Scope3ProcStart="+Result_Scope3ProcStart+". Result_Scope3ProcEnd="+Result_Scope3ProcEnd
				+". Result_Scope3Preclean="+Result_Scope3Preclean+". Result_Scope3StaffPreclean="+Result_Scope3StaffPreclean;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope3_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope3+" after scanning into "+PR1;
		Expected=Description;
		
		String resultScope3_MAM="Scope3 MAM Result ="+MAM_V.verifyScopeDetails(Scope3, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID=="+Staff4ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope3ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope3ExpectedReproCount);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope3_MAM);
		
		//Scope into Sink
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Scope", "", Scope3, "");
		
		Scope_IH3=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Sink1);
		 String  SinkScopeIn_AssocID3=Scope_IH3[1];
		 ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope3,SinkScopeIn_AssocID3);
		 Scope3RefNo=ScopeInfo[0];
		 Scope3SerialNo=ScopeInfo[1];
		 Scope3Model=ScopeInfo[2];
		 Scope3ExamTime=ScopeInfo[3]; 
		 
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Staff", "Tech", Staff2, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Test Result", "", LTPass, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Workflow Event", "", "Manual Clean Start", "");
		Thread.sleep(60000);//waiting 1 min
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Sink1, "Workflow Event", "", "Manual Clean End", "");
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope3RefNo);
		
		Description="Verify Scope Record Management of "+Scope3+" and Staff "+Staff2+" into "+Sink1+". ";

		Result_RefNum=WF_V.Verify_RefNum(Scope3RefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		Result_ScopeModel=WF_V.Verify_ScopeModel(Scope3Model);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		Result_ScopeName=WF_V.Verify_ScopeName(Scope3);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(Scope3SerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo);
		
		ScopeInfo=IHV.GetMCStartEndTime(Unifia_Admin_Selenium.connstring,SinkScopeIn_AssocID3);
		MCStartTime=ScopeInfo[0];
		String MCEndTime=ScopeInfo[1];
		
		Result_Sink=WF_V.Verify_SoiledArea(Sink1);
		System.out.println("Result_Sink=:"+Result_Sink);
		temp=Result_Sink.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Sink,OverallResult);

		Result_LTMCDate=WF_V.Verify_LTMCDate(Scope3ExamTime);
		System.out.println("Result_LTMCDate=:"+Result_LTMCDate);
		temp=Result_LTMCDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LTMCDate,OverallResult);

		Result_SinkStaff=WF_V.Verify_SoiledStaff(Staff2ID);
		System.out.println("Result_SinkStaff=:"+Result_SinkStaff);
		temp=Result_SinkStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_SinkStaff,OverallResult);
		
		LeakTestResult=LTPass.split(" ");//Taking Pass/Fail from Leak Test Pass/Leak Test Fail
		Result_LT=WF_V.Verify_LT(LeakTestResult[2]);
		
		System.out.println("Result_LT=:"+Result_LT);
		temp=Result_LT.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LT,OverallResult);
		
		Result_MCStart=WF_V.Verify_MCStart(MCStartTime);
		System.out.println("Result_MCStart=:"+Result_MCStart);
		temp=Result_MCStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_MCStart,OverallResult);
		
		Result_MCEnd=WF_V.Verify_MCEnd(MCEndTime);
		System.out.println("Result_MCEnd=:"+Result_MCEnd);
		temp=Result_MCEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_MCEnd,OverallResult);
		
		WF_A.Cancel(Changes);
		
		Expected=Description;
		Result_SRM_Scope_Soiled="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel
				+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_Sink"+Result_Sink
				+". Result_LTMCDate="+Result_LTMCDate+". Result_LTMCDate"+Result_LTMCDate+". Result_SinkStaff="+Result_SinkStaff
				+". Result_LT="+Result_LT+". Result_MCStart"+Result_MCStart+". Result_MCEnd"+Result_MCEnd;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope_Soiled);
		
		//Verify MAM details
		Description="Verify Management and Asset Management of "+Scope2+" and Staff "+Staff2ID+" into "+Sink1+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		result_MAM=MAM_V.verifyScopeDetails(Scope3, "LAST SCOPE LOCATION=="+Sink1+" (YFN);LAST SCAN STAFF ID=="+Staff2ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope3ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope3ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
		
		//Scope into Reprocessor
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Scope", "", Scope3, "");
		Unifia_Admin_Selenium.Scope3ExpectedReproCount++;
		
		Scope_IH2=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Repro1);
		 String  ReproScopeIn_AssocID3=Scope_IH2[1];
		 ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope3,ReproScopeIn_AssocID3);
		 Scope3RefNo=ScopeInfo[0];
		 Scope3SerialNo=ScopeInfo[1];
		 Scope3Model=ScopeInfo[2];
		 String Scope3ReproInTime=ScopeInfo[3];
		 
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Staff", "Tech", Staff3, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Test Result", "", MRCPass, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Staff", "Tech", Staff3, "");
		Thread.sleep(60000);//waiting 1 min
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Scope", "", Scope3, "");
		Scope_IH2=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Repro1);
		String  ReproScopeOut_AssocID3=Scope_IH2[1];
		ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope3,ReproScopeOut_AssocID3);
		String Scope3ReproOutTime=ScopeInfo[3];
			
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Repro1, "Staff", "Tech", Staff3, "");
		
		//SRM Verification
		
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope3RefNo);
		
		Description="Verify Scope Record Management of "+Scope3+" and Staff "+Staff3ID+" into "+Repro1+". ";

		Result_RefNum=WF_V.Verify_RefNum(Scope3RefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		Result_ScopeModel=WF_V.Verify_ScopeModel(Scope3Model);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		Result_ScopeName=WF_V.Verify_ScopeName(Scope3);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(Scope3SerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo);

		String Result_Scope3Reprocessor=WF_V.Verify_Reprossor(Repro1);
		System.out.println("Result_Scope3Reprocessor=:"+Result_Scope3Reprocessor);
		temp=Result_Scope3Reprocessor.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3Reprocessor,OverallResult);

		String Result_Scope3ReproReason=WF_V.Verify_ReproReason(Reason1);
		System.out.println("Result_Scope3ReproReason=:"+Result_Scope3ReproReason);
		temp=Result_Scope3ReproReason.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ReproReason,OverallResult);

		String Result_Scope3ReproInStaff=WF_V.Verify_ReproInStaff(Staff3ID);
		System.out.println("Result_Scope3ReproInStaff=:"+Result_Scope3ReproInStaff);
		temp=Result_Scope3ReproInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ReproInStaff,OverallResult);

		String Result_Scope3ReproInTime=WF_V.Verify_ReproScopeInTime(Scope3ReproInTime);
		System.out.println("Result_Scope3ReproInTime=:"+Result_Scope3ReproInTime);
		temp=Result_Scope3ReproInTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ReproInTime,OverallResult);
		
		String Result_Scope3ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope3ReproOutTime);
		System.out.println("Result_Scope3ReproOutTime=:"+Result_Scope3ReproOutTime);
		temp=Result_Scope3ReproOutTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ReproOutTime,OverallResult);
		
		String Result_Scope3ReproOutStaff =WF_V.Verify_ReproOutStaff(Staff3ID);
		System.out.println("Result_Scope3ReproOutStaff=:"+Result_Scope3ReproOutStaff);
		temp=Result_Scope3ReproOutStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ReproOutStaff,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope3_ReproIn="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_Scope3Reprocessor="+Result_Scope3Reprocessor+". Result_Scope3ReproReason"+Result_Scope3ReproReason
				+". "+Result_Scope3ReproInStaff+". Result_Scope3ReproInTime="+Result_Scope3ReproInTime
				+". Result_Scope3ReproOutTime="+Result_Scope3ReproOutTime+". Result_Scope3ReproOutStaff"+Result_Scope3ReproOutStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope3_ReproIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope3+" after scanning into "+Repro1;
		Expected=Description;

		resultScope3_MAM="Scope3 MAM Result ="+MAM_V.verifyScopeDetails(Scope3, "LAST SCOPE LOCATION=="+Repro1+" (YFN);LAST SCAN STAFF ID=="+Staff3ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope3ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope3ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope3_MAM);
		
		//Scope Culture Result
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(CultureA, "Scope", "", Scope3, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(CultureA, "Staff", "Tech", Staff3, "");
		
		//Scope into Storage
		Description="Scan Scope "+Scope3+" into StorageArea";
		Expected="Scope "+Scope3+" is Scanned into StorageArea";
		String ExpectedOtherScopeState="0";
		String ResultOtherScopeState="";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(StorageB, "Scope", "", Scope3, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(StorageB, "Staff", "Tech", Staff3, "");
		
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+Scope3+" and Staff "+Staff3ID+" into "+StorageB+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		result_MAM=MAM_V.verifyScopeDetails(Scope3, "LAST SCOPE LOCATION=="+StorageB+" (YFN);LAST SCAN STAFF ID=="+Staff3ID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope3ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope3ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
				
		Thread.sleep(120000);//waiting 2 mins
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(StorageB, "Scope", "", Scope3, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(StorageB, "Key Entry", "", "", "1");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		String ActualOtherScopeState=Scope_IH[2];
		if(ExpectedOtherScopeState.equalsIgnoreCase(ActualOtherScopeState)){
			ResultOtherScopeState=" Pass- OtherScopeState is okay";
		}else{
			ResultOtherScopeState=" Error- the OtherScopeState was ecpected to be:  "+ExpectedOtherScopeState+".  However it was "+ActualOtherScopeState;
		}
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultOtherScopeState);
		
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+Scope3+" and Staff - into "+StorageB+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		result_MAM=MAM_V.verifyScopeDetails(Scope3, "LAST SCOPE LOCATION=="+StorageB+" (YFN);LAST SCAN STAFF ID==-"
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope3ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope3ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
		
		//Scope into ProcedureRoom
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Workflow Event", "", "Available", "");
		String Hangtime="";
		String ScopeSerialNum="";
		Description="Scan Scope "+Scope3+" into ProcedureRoom";
		Expected="Scope "+Scope3+" is Scanned into ProcedureRoom";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(PR1, "Scope", "", Scope3, "");
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PR1);
		String ScopeIN_IH=Scope_IH[0];
		String ScopeIN_AssocID=Scope_IH[1];
		
		String Scope3Info[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope3,ScopeIN_AssocID);
		Scope3RefNo=Scope3Info[0];
		Scope3SerialNo=Scope3Info[1];
		Scope3Model=Scope3Info[2];
		Scope3ExamTime=Scope3Info[3];
		
		try{
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
			Statement statement=conn.createStatement();
			String stmt="Select DateDiff(hh, IH.ReceivedDateTime, GETUTCDATE())/24 AS HangTime,S.SerialNumber from ItemHistory IH join Scope s on IH.ScanItemID_FK=S.ScopeID_PK where IH.ScanItemTypeID_FK=1 and IH.ItemHistoryID_PK="+ScopeIN_IH+" and IH.AssociationID_FK="+ScopeIN_AssocID;
			scope=statement.executeQuery(stmt);
			while(scope.next()){
				Hangtime=scope.getString(1);
				ScopeSerialNum=scope.getString(2);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		Res = EM_V.VerifyScanMsg("3344556 Hang Time "+Hangtime+" days", Unifia_Admin_Selenium.ScannerCount);
		if(Res==true){
			Result="Pass- the Scanner message is as expected";
			System.out.println("Pass - The scanner message did not prompt for culture result entry. (The culture results are already entered while checking out of the cabinet but staff was not scanned)");
		}else{
			Result=Unifia_Admin_Selenium.Result;
			System.out.println("#Failed!# - The scanner message prompted for culture result entry. (The culture results are already entered while checking out of the cabinet but staff was not scanned)");
		}
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope3RefNo);
		
		Description="Verify Scope Record Management of "+Scope3+" and Staff - into "+PR1+". ";

		String Result_RefNum3=WF_V.Verify_RefNum(Scope3RefNo);
		temp=Result_RefNum3.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum3,OverallResult);
		
		String Result_ScopeModel3=WF_V.Verify_ScopeModel(Scope3Model);
		temp=Result_ScopeModel3.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel3,OverallResult);

		String Result_ScopeName3=WF_V.Verify_ScopeName(Scope3);
		temp=Result_ScopeName3.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName3,OverallResult);

		String Result_ScopeSerialNo3=WF_V.Verify_ScopeSerialNum(Scope3SerialNo);
		temp=Result_ScopeSerialNo3.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo3,OverallResult);

		System.out.println("Result_RefNum3=:"+Result_RefNum3+". Result_ScopeModel3="+Result_ScopeModel3+". Result_ScopeName3="+Result_ScopeName3+". Result_ScopeSerialNo3="+Result_ScopeSerialNo3);

		Result_Scope3PR=WF_V.Verify_PR(PR1);
		System.out.println("Result_Scope3PR=:"+Result_Scope3PR);
		temp=Result_Scope3PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3PR,OverallResult);

		Result_Scope3ExamDate=WF_V.Verify_ExamDate(Scope3ExamTime);
		System.out.println("Result_Scope3ExamDate=:"+Result_Scope3ExamDate);
		temp=Result_Scope3ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3ExamDate,OverallResult);

		Result_Scope3InStaff=WF_V.Verify_PRInStaff("");
		System.out.println("Result_Scope3InStaff=:"+Result_Scope3InStaff);
		temp=Result_Scope3InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope3InStaff,OverallResult);

		WF_A.Cancel(Changes);
		Expected=Description;
		Result_SRM_Scope3_PRIn="Result_RefNum3=:"+Result_RefNum3+". Result_ScopeModel3="+Result_ScopeModel3+". Result_ScopeName3="+Result_ScopeName3+". "
				+ "Result_ScopeSerialNo3="+Result_ScopeSerialNo3+". Result_Scope3PR="+Result_Scope3PR+". Result_Scope3ExamDate"+Result_Scope3ExamDate
				+". Result_Scope3InStaff="+Result_Scope3InStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope3_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope3+" after scanning into "+PR1;
		Expected=Description;

		resultScope3_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope3, "LAST SCOPE LOCATION=="+PR1+" (YFN);LAST SCAN STAFF ID==-"
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope3ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope3ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope3_MAM);
		
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		System.out.println("End of Scenario -3");
		if (Unifia_Admin_Selenium.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
	}
	
	@AfterTest
	public void PostTTest() throws IOException{
		//System.out.println("The Test Case Run was:  "+TCResult);
		//IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, TCResult);
		LP_A.CloseDriver();
	}
}
