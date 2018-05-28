package TestFrameWork.RegressionTest.SoiledArea;
import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage;
import TestFrameWork.Unifia_MAM.MAM_Actions;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;


public class SoiledRegression_Actions extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	static TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	private static TestFrameWork.QVDashboard.Dashboard_Verification qvd_v;
	private static Dashboard_Verification DV;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;

	public static String[] SoiledRoomScans (String Location, String Scope1, String Staff,String StaffID,String LT, String MCStart, String MCEnd, String ExpectedCycleCount)throws InterruptedException, AWTException{
		String Changes="No";
		String[] SoiledResult = new String[2];
		String [] temp= new String[2];
		String OverallResult="Pass";
		Boolean Res;
		String Description;
		String Expected;
		String ResultLT="";
		String Staff_IH[];
		String StaffIH;
		String Staff_Assoc;
		String ResultStaff="";
		String MCStart_IH;
		String MCStart_Assoc;
		String ResultMCStart="";
		String ResultMCEnd;
		String MCEnd_Assoc;
		int StaffPK=0;
		int LocalScope1ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
		
		String Scope1SinkInTime="";
		String Result_RefNum1="";
		String Result_ScopeModel1="";
		String Result_ScopeName1="";
		String Result_ScopeSerialNo1="";
		String Result_Sink="";
		String Result_LTMCDate="";
		String Result_SinkStaff="";
		String ResultStaffCycle="";
		String Result_LT="";
		String Result_MCStart="";
		String Result_MCEnd="";
		String Result_SRM_Scope_Soiled="";
		String MCStartTime="";
		String MCEndTime="";
		
		String expectedColor=""; 
		String resultChevronColor="";

		if(Scope1.equalsIgnoreCase("Scope1")){
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope1ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope1ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope2")){
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope2ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope2ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope3")){
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope3ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope3ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope4")){
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope4ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope4ExpectedExamCount;
		}
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ Location;
		String CycleEvent="Scope In Sink";
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		String Scope1InIH=Scope_IH[0];
		String Scope1InAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		//System.out.println(Scope1InIH+" = Scope into Sink ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,Scope1InAssociationID);
		String Scope1RefNo=ScopeInfo[0];
		String Scope1SerialNo=ScopeInfo[1];
		String Scope1Model=ScopeInfo[2];
		Scope1SinkInTime=ScopeInfo[3];
		
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

		String ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeInCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScopeInCycle,OverallResult);

		int LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		String ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);

		String ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(LocalScope1ExpectedRepro));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		String ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(LocalScope1ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		
		String ResultScopeInLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		temp=ResultScopeInLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);

		String ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		temp=ResultScopeInState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount);
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultSinkScope1="Scope1: ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		//System.out.println(ResultSinkScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultSinkScope1);

		//Scan Staff if provided. 
		if(Staff.equalsIgnoreCase("")){
			ResultStaff="No Staff for Soiled Area provided for "+Scope1;
			//System.out.println(ResultStaff);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope into Sink";
			//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			CycleEvent="SoiledRoomStaff";
			Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);	
			StaffIH=Staff_IH[0];
			Staff_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			
			ResultStaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultStaffCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultStaffCycle,OverallResult);
			
			ResultStaff=IHV.Result_Same_Assoc(Scope1InAssociationID,Staff_Assoc)+" for staff and scope into Sink.";
			temp=ResultStaff.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaff, OverallResult);

			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);

			ResultStaff=ResultStaff+". ResultStaffCycle="+ResultStaffCycle+". ResultLastStaff="+ResultLastStaff+".";
			
			//System.out.println(ResultStaff);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaff);
		}
		
		//DailyDashboard Verification
		if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether "+Scope1+" is getting displayed in "+Location;
			Expected=Description;
			String result=DV.verifyDashboard(Location, Scope1, "", "", "");
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		// Verify Date format in SRM table in workFlowStart Date Column
		IP_V.verifyDateFormatINSRMScreen();
		
		//Verifying Chevron Color
		expectedColor=DBP.rgbOfIncompleteFlow;
		resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen";
		Expected="The Chevron color should be "+expectedColor;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
		
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";

		Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
		
		Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		Result_ScopeName1=WF_V.Verify_ScopeName(Scope1);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		System.out.println("Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);
		
		Result_Sink=WF_V.Verify_SoiledArea(Location);
		System.out.println("Result_Sink=:"+Result_Sink);
		temp=Result_Sink.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Sink,OverallResult);

		Result_LTMCDate=WF_V.Verify_LTMCDate(Scope1SinkInTime);
		System.out.println("Result_LTMCDate=:"+Result_LTMCDate);
		temp=Result_LTMCDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LTMCDate,OverallResult);

		Result_SinkStaff=WF_V.Verify_SoiledStaff(StaffID);
		System.out.println("Result_SinkStaff=:"+Result_SinkStaff);
		temp=Result_SinkStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_SinkStaff,OverallResult);

		Result_LT=WF_V.Verify_LT("");
		System.out.println("Result_LT=:"+Result_LT);
		temp=Result_LT.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LT,OverallResult);
		
		Result_MCStart=WF_V.Verify_MCStart("");
		System.out.println("Result_MCStart=:"+Result_MCStart);
		temp=Result_MCStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_MCStart,OverallResult);

		Result_MCEnd=WF_V.Verify_MCEnd("");
		System.out.println("Result_MCEnd=:"+Result_MCEnd);
		temp=Result_MCEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_MCEnd,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		Result_SRM_Scope_Soiled="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1
				+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Sink="+Result_Sink
				+". Result_LTMCDate="+Result_LTMCDate+". Result_SinkStaff="+Result_SinkStaff
				+". Result_LT="+Result_LT+". Result_MCStart="+Result_MCStart+". Result_MCEnd="+Result_MCEnd;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope_Soiled);
		
		//Verify MAM details
		Description="Verify Management and Asset Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		String result_MAM=MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);

		//Scan the Leak Test result if a value is passed to the function.
		if(LT.equalsIgnoreCase("")){
			ResultLT="No Leak Test provided for "+Scope1;
			//System.out.println(ResultLT);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Test Result", "", LT, "");
			Description="Scan of "+LT+" is done in "+ Location;
			
			CycleEvent="Leak Test";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			String LTIH=Scope_IH[0];
			String LT_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(Scope1InIH+" = Leak test ItemHistory_PK");

			ResultLT=IHV.Result_Same_Assoc(Scope1InAssociationID,LT_Assoc);
			temp=ResultLT.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultLT, OverallResult);

			String ResultLTCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultLTCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultLTCycle, OverallResult);

			ResultLT=ResultLT+" for "+Scope1+" "+LT+". CycleEvent is also correct = "+ResultLTCycle;
			//System.out.println(ResultLT);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultLT);

		}
		
		//Scan Manual Clean Start if specified.
		if(MCStart.equalsIgnoreCase("No")){
			ResultMCStart="Do not perform MC Start for "+Scope1;
			//System.out.println(ResultMCStart);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Workflow Event", "", "Manual Clean Start", "");
			Description="Scan of Manual Clean Start is done in "+ Location;
			
			CycleEvent="Manual Clean Start";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			MCStart_IH=Scope_IH[0];
			MCStart_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(Scope1InIH+" = MC Start ItemHistory_PK");

			ResultMCStart=IHV.Result_Same_Assoc(Scope1InAssociationID,MCStart_Assoc);
			temp=ResultMCStart.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultMCStart, OverallResult);

			String ResultMCStartCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultMCStartCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultMCStartCycle, OverallResult);

			ResultMCStart=ResultMCStart+" for "+Scope1+" MC Start. CycleEvent is also correct = "+ResultMCStartCycle;
			//System.out.println(ResultMCStart);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultMCStart);

		}
		
		Thread.sleep(60000); //Wait 1 minute before scanning MC End 

		//Scan Manual Clean End if specified.
		if(MCEnd.equalsIgnoreCase("No")){
			ResultMCEnd="Do not perform MC End for "+Scope1;
			//System.out.println(ResultMCEnd);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Workflow Event", "", "Manual Clean End", "");
			Description="Scan of MC End is done in "+ Location;
			
			CycleEvent="Manual Clean End";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			MCEnd_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(Scope1InIH+" = MC End ItemHistory_PK");

			ResultMCEnd=IHV.Result_Same_Assoc(Scope1InAssociationID,MCEnd_Assoc);
			temp=ResultMCEnd.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultMCEnd, OverallResult);

			String ResultMCEndCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultMCEndCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultMCEndCycle, OverallResult);

			ResultMCEnd=ResultMCEnd+" for "+Scope1+" MC End. CycleEvent is also correct = "+ResultMCEndCycle;
			//System.out.println(ResultMCEnd);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultMCEnd);
		}
		//System.out.println("OverallResult="+OverallResult);
		//SRM Verification
		Thread.sleep(2000); //Wait 2 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		// Verify Date format in SRM table in workFlowStart Date Column
		IP_V.verifyDateFormatINSRMScreen();
		
		//Verifying Chevron Color
		if(Staff.equalsIgnoreCase("")||LT.equalsIgnoreCase("")||MCStart.equalsIgnoreCase("")||MCEnd.equalsIgnoreCase("")){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen";
		Expected="The Chevron color should be "+expectedColor;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
		
		IP_A.Click_Details(Scope1RefNo);
		Thread.sleep(2000); //Wait 2 sec
		
		Description="Verify Scope Record Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";

		Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
		
		Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		Result_ScopeName1=WF_V.Verify_ScopeName(Scope1);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);
		
		ScopeInfo=IHV.GetMCStartEndTime(Unifia_Admin_Selenium.connstring,Scope1InAssociationID);
		MCStartTime=ScopeInfo[0];
		MCEndTime=ScopeInfo[1];
		
		Result_Sink=WF_V.Verify_SoiledArea(Location);
		System.out.println("Result_Sink=:"+Result_Sink);
		temp=Result_Sink.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Sink,OverallResult);

		Result_LTMCDate=WF_V.Verify_LTMCDate(Scope1SinkInTime);
		System.out.println("Result_LTMCDate=:"+Result_LTMCDate);
		temp=Result_LTMCDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LTMCDate,OverallResult);

		Result_SinkStaff=WF_V.Verify_SoiledStaff(StaffID);
		System.out.println("Result_SinkStaff=:"+Result_SinkStaff);
		temp=Result_SinkStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_SinkStaff,OverallResult);
		
		String LeakTestResult[]=LT.split(" ");//Taking Pass/Fail from Leak Test Pass/Leak Test Fail
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
		Result_SRM_Scope_Soiled="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1
				+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Sink="+Result_Sink
				+". Result_LTMCDate="+Result_LTMCDate+". Result_SinkStaff="+Result_SinkStaff
				+". Result_LT="+Result_LT+". Result_MCStart="+Result_MCStart+". Result_MCEnd="+Result_MCEnd;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope_Soiled);
		
		//Verify MAM details
		Description="Verify Management and Asset Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		result_MAM=MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
		
		SoiledResult[0]=Scope1InAssociationID;
		SoiledResult[1]=OverallResult;
		return SoiledResult;
	}
}
