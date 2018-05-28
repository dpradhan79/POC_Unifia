package TestFrameWork.RegressionTest.ReprocessingRoom;
import java.awt.AWTException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage;
import TestFrameWork.UnifiaAdminReconMRC.MRC_Verification;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;
import TestFrameWork.Unifia_MAM.*;
import OER_Simulator.*;

public class ReprocessingRegression_Actions extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	static TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public static OER_Simulator.OERGeneralFunc OER_GF;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	public static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	public static TestFrameWork.UnifiaAdminReconMRC.MRC_Verification MRC_V;
	private static TestFrameWork.QVDashboard.Dashboard_Verification qvd_v;
	private static Dashboard_Verification DV;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	
	public static String[] ReprocessingRoomScans (String Location, String Scope1, String Scope2, String Staff,String StaffID, String Reason1, String Reason2, String MRC, String ExpectedCycleCount1, String ExpectedCycleCount2)throws InterruptedException, AWTException{
		String Changes="No";
		String[] ReproResult = new String[8];
		Boolean Res;
		String [] temp= new String[2];
		String OverallResult="Pass";
		String Description;
		String Expected;
		String ResultReason1="";
		String Staff_IH[];
		String StaffInIH;
		String StaffIn_Assoc;
		String ResultScopeIn1StaffCycle="";
		String ResultScopeIn2StaffCycle="";
		String ResultScopeOut1StaffCycle="";
		String ResultScopeOut2StaffCycle="";
		
		String ResultStaffIn1="";
		String MRCAssociationID="";
		String StaffMRCIH;
		String StaffMRC_Assoc;
		String ResultStaffMRC="";
		String ResultReproInScope2;
		String ResultScopesIn_AssocDiff;
		String ResultReason2="";
		String ResultStaffIn2="";
		String StaffOut_Assoc;
		String ResultStaffOut1="";
		String ResultStaffOut2="";
		String ResultReproOutScope2="";
		String StaffOutIH;
		String Scope2OutAssociationID="";
		String Scope2InAssociationID="";
		String Scope2RefNo="";
		String Scope2SerialNo="";
		String Scope2Model="";
		String Result_RefNum2="";
		String Result_ScopeModel2="";
		String Result_ScopeName2="";
		String Result_ScopeSerialNo2="";
		String Result_Scope2Reprocessor="";
		String Result_Scope2ReproReason="";
		String Result_Scope2ReproInStaff="";
		String Result_Scope2ReproOutStaff="";
		String Result_Scope2ReproInTime="";
		String Result_Scope2ReproStart="";
		String Result_Scope2ReproComplete="";
		String Result_Scope2ReproStartStaff="";
		String Result_Scope2ReproTemp="";
		String Result_Scope2ReproStatus="";
		String Result_SRM_Scope2_ReproIn="";
		String Scope1ReproInTime="";
		String Scope2ReproInTime="";
		String Scope1ReproOutTime="";
		String Scope2ReproOutTime="";

		String ResultScopesOut_AssocDiff2;
		String ResultMRC;
		String ResultLastStaff="";
		
		String ActualReason=null;
		String MRCTime=null;
		
		//System.out.println("Unifia_Admin_Selenium.Scope1ExpectedReproCount="+Unifia_Admin_Selenium.Scope1ExpectedReproCount);
		int LocalScope1ExpectedRepro=0;
		int LocalScope2ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
		int LocalScope2ExpectedExamCount=0;
		
		String expectedColor=""; 
		String resultChevronColor="";

		if(Scope1.equalsIgnoreCase("Scope1")){
			Unifia_Admin_Selenium.Scope1ExpectedReproCount++;
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope1ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope1ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope3")){
			Unifia_Admin_Selenium.Scope3ExpectedReproCount++;
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope3ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope3ExpectedExamCount;
		}
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ Location;
		String CycleEvent="Reprocessing In";
		String Scope_IH[];
		Scope_IH=IHV.GetReprocessorData(Unifia_Admin_Selenium.connstring, Location);
			
		//String Scope1InIH=Scope_IH[0];
		String Scope1InAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,Scope1InAssociationID);
		String Scope1RefNo=ScopeInfo[0];
		String Scope1SerialNo=ScopeInfo[1];
		String Scope1Model=ScopeInfo[2];
		Scope1ReproInTime=ScopeInfo[3];
		
		//System.out.println(Scope1InIH+" = Scope into Reprocessor ItemHistory_PK");
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

		int StaffPK=0;
		int LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);

		String ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeInCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScopeInCycle,OverallResult);
		
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

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount1);
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultReproInScope1=Scope1+": ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		//System.out.println(ResultReproInScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReproInScope1);

		//Scan the Reason for Reprocessing if a value is passed to the function.
		if(Reason1.equalsIgnoreCase("")){
			//ResultReason1="No reason for reprocesing provided for "+Scope1;
			ResultReason1="There is no derived reason for reprocesing for "+Scope1;
			//System.out.println(ResultReason1);
			ActualReason="";

		}else {
			/*Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Reason for Reprocessing", "", Reason1, "");*/
			Description="Reason for Reprocessing "+Reason1+" is derived in "+ Location;
			CycleEvent="Reason For Reprocessing";
			ActualReason=IHV.GetReasonForReprocessing(Unifia_Admin_Selenium.connstring, Scope1InAssociationID);
			ResultReason1=IHV.Result_Same_Reason(Reason1,ActualReason);
			temp=ResultReason1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultReason1, OverallResult);
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			//String ReasonIH=Scope_IH[0];
			String Reason_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(Scope1InIH+" = Scope into Reprocessor ItemHistory_PK");

			ResultReason1=IHV.Result_Same_Assoc(Scope1InAssociationID,Reason_Assoc);
			temp=ResultReason1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultReason1, OverallResult);

			String ResultReasonCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultReasonCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultReasonCycle, OverallResult);

			ResultReason1=ResultReason1+" for "+Scope1+" into "+Location+" and reason for reprocessing of "+Reason1+". "
					+ "CycleEvent is also correct = "+ResultReasonCycle;
			//System.out.println(ResultReason1);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReason1);

		}
		if(Staff.equalsIgnoreCase("")){
			ResultStaffIn1="No Staff for reprocesing provided for "+Scope1;
			//System.out.println(ResultStaffIn1);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope into Reprocessor";
			//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			CycleEvent="ReprocessingInStaff";
			Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			StaffInIH=Staff_IH[0];
			StaffIn_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			
			ResultScopeIn1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultScopeIn1StaffCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScopeIn1StaffCycle,OverallResult);
			
			ResultStaffIn1=IHV.Result_Same_Assoc(Scope1InAssociationID,StaffIn_Assoc)+" for staff and scope into reprocessor.";
			temp=ResultStaffIn1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaffIn1, OverallResult);
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);

			ResultStaffIn1=ResultStaffIn1+". ResultScopeIn1StaffCycle="+ResultScopeIn1StaffCycle+". ResultLastStaff="+ResultLastStaff+".";
			//System.out.println(ResultStaffIn1);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffIn1);

		}
		
		//Daily Dashboard verification
		if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether "+Scope1+" is getting displayed in "+Location;
			Expected=Description;
			String result=DV.verifyDashboard(Location, Scope1, "", "", "");
			if(result.contains("#Failed!#")){
				result=result+" Bug 12881 opened";
			}
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		//Verifying Chevron Color
		expectedColor=DBP.rgbOfIncompleteFlow;
		resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen";
		Expected="The Chevron color should be "+expectedColor;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
		
		// Verify Date format in SRM table in workFlowStart Date Column
		IP_V.verifyDateFormatINSRMScreen();
		
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";

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

		//System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

		String Result_Scope1Reprocessor=WF_V.Verify_Reprossor(Location);
		System.out.println("Result_Scope1Reprocessor=:"+Result_Scope1Reprocessor);
		temp=Result_Scope1Reprocessor.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Reprocessor,OverallResult);

		String Result_Scope1ReproReason=WF_V.Verify_ReproReason(Reason1);
		System.out.println("Result_Scope1ReproReason=:"+Result_Scope1ReproReason);
		temp=Result_Scope1ReproReason.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproReason,OverallResult);

		String Result_Scope1ReproInStaff=WF_V.Verify_ReproInStaff(StaffID);
		System.out.println("Result_Scope1ReproInStaff=:"+Result_Scope1ReproInStaff);
		temp=Result_Scope1ReproInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInStaff,OverallResult);
		
		
		String Result_Scope1ReproInTime=WF_V.Verify_ReproScopeInTime(Scope1ReproInTime);
		System.out.println("Result_Scope1ReproInTime=:"+Result_Scope1ReproInTime);
		temp=Result_Scope1ReproInTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInTime,OverallResult);

		String Result_Scope1ReproStart=WF_V.Verify_ReproStartTime("");
		System.out.println("Result_Scope1ReproStart=:"+Result_Scope1ReproStart);
		temp=Result_Scope1ReproStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStart,OverallResult);

		String Result_Scope1ReproStartStaff=WF_V.Verify_ReproStartStaff("");
		System.out.println("Result_Scope1ReproStartStaff=:"+Result_Scope1ReproStartStaff);
		temp=Result_Scope1ReproStartStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStartStaff,OverallResult);

		String Result_Scope1ReproTemp=WF_V.Verify_ReproTemp("");
		System.out.println("Result_Scope1ReproTemp=:"+Result_Scope1ReproTemp);
		temp=Result_Scope1ReproTemp.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproTemp,OverallResult);
	
		String Result_Scope1ReproStatus=WF_V.Verify_ReproStatus("Unknown");
		System.out.println("Result_Scope1ReproStatus=:"+Result_Scope1ReproStatus);
		temp=Result_Scope1ReproStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStatus,OverallResult);
		
		String Result_Scope1ReproComplete=WF_V.Verify_ReproCompleteTime("");
		System.out.println("Result_Scope1ReproComplete=:"+Result_Scope1ReproComplete);
		temp=Result_Scope1ReproComplete.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproComplete,OverallResult);
		
		String Result_Scope1ReproOutStaff=WF_V.Verify_ReproOutStaff("");
		System.out.println("Result_Scope1ReproOutStaff=:"+Result_Scope1ReproOutStaff);
		temp=Result_Scope1ReproComplete.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutStaff,OverallResult);
		
		String Result_Scope1ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope1ReproOutTime);
		System.out.println("Result_Scope1ReproOutTime=:"+Result_Scope1ReproOutTime);
		temp=Result_Scope1ReproOutTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutTime,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope1_ReproIn="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1Reprocessor="+Result_Scope1Reprocessor+". Result_Scope1ReproReason"+Result_Scope1ReproReason
				+". Result_Scope1ReproInStaff"+Result_Scope1ReproInStaff+". Result_Scope1ReproInTime="+Result_Scope1ReproInTime
				+". Result_Scope1ReproStart="+Result_Scope1ReproStart+". Result_Scope1ReproStartStaff="+Result_Scope1ReproStartStaff+". Result_Scope1ReproTemp="+Result_Scope1ReproTemp
				+". Result_Scope1ReproStatus="+Result_Scope1ReproStatus+". Result_Scope1ReproComplete="+Result_Scope1ReproComplete+". Result_Scope1ReproOutStaff="+Result_Scope1ReproOutStaff
				+".  Result_Scope1ReproOutTime="+Result_Scope1ReproOutTime;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_ReproIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning into "+Location;
		Expected=Description;

		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);

		if(Scope2.equalsIgnoreCase("")){
			ResultReproInScope2="Scope2 not passed into the function.";
		} else {
			if(Scope2.equalsIgnoreCase("Scope2")){
				Unifia_Admin_Selenium.Scope2ExpectedReproCount++;
				LocalScope2ExpectedRepro=Unifia_Admin_Selenium.Scope2ExpectedReproCount;
				LocalScope2ExpectedExamCount=Unifia_Admin_Selenium.Scope2ExpectedExamCount;

			}else if(Scope2.equalsIgnoreCase("Scope4")){
				Unifia_Admin_Selenium.Scope4ExpectedReproCount++;
				LocalScope2ExpectedRepro=Unifia_Admin_Selenium.Scope4ExpectedReproCount;
				LocalScope2ExpectedExamCount=Unifia_Admin_Selenium.Scope4ExpectedExamCount;
			}
			
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Scope", "", Scope2, "");
			Description="Scan of Scope '" +Scope2+"' is done in "+ Location;
			CycleEvent="Reprocessing In";
			
			if(!Reason2.equalsIgnoreCase("")){
				Scope_IH=IHV.GetReprocessorData(Unifia_Admin_Selenium.connstring, Location);
			}else{
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			}
			//String Scope2InIH=Scope_IH[0];
			Scope2InAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope2,Scope2InAssociationID);
			Scope2RefNo=ScopeInfo[0];
			Scope2SerialNo=ScopeInfo[1];
			Scope2Model=ScopeInfo[2];
			Scope2ReproInTime=ScopeInfo[3];


			//System.out.println(Scope2InIH+" = "+Scope2+" into Reprocessor ItemHistory_PK");
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
			ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(LocalScope2ExpectedRepro));
			temp=ResultReproCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
			
			ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(LocalScope2ExpectedExamCount));
			temp=ResultExamCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
			ResultScopeInLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc);
			temp=ResultScopeInLoc.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);

			ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
			temp=ResultScopeInState.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

			ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount2);
			temp=ResultScopeCycleCount.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);
			
			ResultScopesIn_AssocDiff=IHV.Result_Different_Assoc(Scope1InAssociationID, Scope2InAssociationID)+" Scopes have different associationIDs";
			temp=ResultScopesIn_AssocDiff.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopesIn_AssocDiff, OverallResult);

			ResultReproInScope2=Scope2+": ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState+". ResultScopesIn_AssocDiff="+ResultScopesIn_AssocDiff;
			//System.out.println(ResultReproInScope2);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReproInScope2);

			//Scan the Reason for Reprocessing if a value is passed to the function.
			if(Reason2.equalsIgnoreCase("")){
				//ResultReason2="No reason for reprocesing provided for "+Scope2;
				ResultReason2="There is no derived reason for reprocesing for "+Scope2;
				ActualReason="";
				//System.out.println(ResultReason2);
			}else {
				/*Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Reason for Reprocessing", "", Reason2, "");*/
				Description="Reason for Reprocessing "+Reason2+" is derived in "+ Location;
				CycleEvent="Reason For Reprocessing";
				ActualReason=IHV.GetReasonForReprocessing(Unifia_Admin_Selenium.connstring, Scope2InAssociationID);
				ResultReason2=IHV.Result_Same_Reason(Reason2,ActualReason);
				temp=ResultReason2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultReason2, OverallResult);
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
				String ReasonIH2=Scope_IH[0];
				String Reason2_Assoc=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				//System.out.println(Scope2InIH+" = Scope into Reprocessor ItemHistory_PK");

				ResultReason2=IHV.Result_Same_Assoc(Scope2InAssociationID,Reason2_Assoc);
				temp=ResultReason2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultReason2, OverallResult);

				String ResultReasonCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
				OverallResult=GF.FinalResult(temp[0],ResultReasonCycle, OverallResult);

				ResultReason2=ResultReason2+" for Scope2 into Reprocessor and reason for reprocessing. CycleEvent is also correct = "+ResultReasonCycle;
				Expected=Description;
				IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReason2);

			}
			if(Staff.equalsIgnoreCase("")){
				ResultStaffIn2="No Staff for reprocesing provided for "+Scope2;
				//System.out.println(ResultStaffIn2);
			}else {
				Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
				Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope into Reprocessor";
				//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
				CycleEvent="ReprocessingInStaff";
				Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
				StaffInIH=Staff_IH[0];
				StaffIn_Assoc=Staff_IH[1];
				ActualCycleEvent=Staff_IH[5];
				
				ResultScopeIn2StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
				temp=ResultScopeIn2StaffCycle.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultScopeIn2StaffCycle,OverallResult);
				
				ResultStaffIn2=IHV.Result_Same_Assoc(Scope2InAssociationID,StaffIn_Assoc)+" for staff and scope into reprocessor.";
				temp=ResultStaffIn2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultStaffIn2, OverallResult);

				StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
				LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
				ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
				temp=ResultLastStaff.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
				
				ResultStaffIn2=ResultStaffIn2+" ResultScopeIn2StaffCycle="+ResultScopeIn2StaffCycle+". ResultLastStaff="+ResultLastStaff+".";
				//System.out.println(ResultStaffIn2);
				Expected=Description;
				IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffIn2);
			}
			
			//Daily Dashboard verification
			if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
				Description="Verifying DailyDashboard tab to see whether "+Scope1+" "+Scope2+" is getting displayed in "+Location;
				Expected=Description;
				String result=DV.verifyDashboard(Location, Scope1+"\n"+Scope2, "", "", "");
				if(result.contains("#Failed!#")){
					result=result+" Bug 12881 opened";
				}

				IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
			}
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope2RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope2RefNo);
			
			Description="Verify Scope Record Management of "+Scope2+" and Staff "+Staff+" into "+Location+". ";

			Result_RefNum2=WF_V.Verify_RefNum(Scope2RefNo);
			temp=Result_RefNum1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
			
			Result_ScopeModel2=WF_V.Verify_ScopeModel(Scope2Model);
			temp=Result_ScopeModel2.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeModel2,OverallResult);

			Result_ScopeName2=WF_V.Verify_ScopeName(Scope2);
			temp=Result_ScopeName2.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeName2,OverallResult);

			Result_ScopeSerialNo2=WF_V.Verify_ScopeSerialNum(Scope2SerialNo);
			temp=Result_ScopeSerialNo2.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo2,OverallResult);

			//System.out.println("Result_RefNum2=:"+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". Result_ScopeSerialNo2="+Result_ScopeSerialNo2);

			Result_Scope2Reprocessor=WF_V.Verify_Reprossor(Location);
			System.out.println("Result_Scope2Reprocessor=:"+Result_Scope2Reprocessor);
			temp=Result_Scope2Reprocessor.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2Reprocessor,OverallResult);

			Result_Scope2ReproReason=WF_V.Verify_ReproReason(Reason2);
			System.out.println("Result_Scope2ReproReason=:"+Result_Scope2ReproReason);
			temp=Result_Scope2ReproReason.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproReason,OverallResult);

			Result_Scope2ReproInStaff=WF_V.Verify_ReproInStaff(StaffID);
			System.out.println("Result_Scope2ReproInStaff=:"+Result_Scope2ReproInStaff);
			temp=Result_Scope2ReproInStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproInStaff,OverallResult);

			Result_Scope2ReproInTime=WF_V.Verify_ReproScopeInTime(Scope2ReproInTime);
			System.out.println("Result_Scope2ReproInTime=:"+Result_Scope2ReproInTime);
			temp=Result_Scope2ReproInTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproInTime,OverallResult);

			Result_Scope2ReproStart=WF_V.Verify_ReproStartTime("");
			System.out.println("Result_Scope2ReproStart=:"+Result_Scope2ReproStart);
			temp=Result_Scope2ReproStart.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStart,OverallResult);

			Result_Scope2ReproStartStaff=WF_V.Verify_ReproStartStaff("");
			System.out.println("Result_Scope2ReproStartStaff=:"+Result_Scope2ReproStartStaff);
			temp=Result_Scope2ReproStartStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStartStaff,OverallResult);

			Result_Scope2ReproTemp=WF_V.Verify_ReproTemp("");
			System.out.println("Result_Scope2ReproTemp=:"+Result_Scope2ReproTemp);
			temp=Result_Scope2ReproTemp.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproTemp,OverallResult);
		
			Result_Scope2ReproStatus=WF_V.Verify_ReproStatus("Unknown");
			System.out.println("Result_Scope2ReproStatus=:"+Result_Scope2ReproStatus);
			temp=Result_Scope2ReproStatus.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStatus,OverallResult);
			
			Result_Scope2ReproComplete=WF_V.Verify_ReproCompleteTime("");
			System.out.println("Result_Scope2ReproComplete=:"+Result_Scope2ReproComplete);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproComplete,OverallResult);
			
			Result_Scope2ReproOutStaff=WF_V.Verify_ReproOutStaff("");
			System.out.println("Result_Scope2ReproOutStaff=:"+Result_Scope2ReproOutStaff);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutStaff,OverallResult);
			
			String Result_Scope2ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope2ReproOutTime);
			System.out.println("Result_Scope2ReproOutTime=:"+Result_Scope2ReproOutTime);
			temp=Result_Scope2ReproOutTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutTime,OverallResult);
			WF_A.Cancel(Changes);
			Expected=Description;
			Result_SRM_Scope2_ReproIn="Result_RefNum2=:"+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". "
					+ "Result_ScopeSerialNo2="+Result_ScopeSerialNo2+". Result_Scope2Reprocessor="+Result_Scope2Reprocessor+". Result_Scope2ReproReason"+Result_Scope2ReproReason
					+". Result_Scope2ReproInStaff"+Result_Scope2ReproInStaff+". Result_Scope2ReproTemp="+Result_Scope2ReproTemp
					+". Result_Scope2ReproStatus="+Result_Scope2ReproStatus+". Result_Scope2ReproComplete="+Result_Scope2ReproComplete+". Result_Scope2ReproOutStaff="+Result_Scope2ReproOutStaff
					+". Result_Scope2ReproOutTime="+Result_Scope2ReproOutTime;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_ReproIn);
			
			MAM_A.Click_MaterialsAndAssetManagement();
			Description="Verify MAM screen for "+Scope2+" after scanning into "+Location;
			Expected=Description;

			String resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
					+";EXAM COUNT=="+LocalScope2ExpectedExamCount+";REPROCESSING COUNT=="
					+LocalScope2ExpectedRepro).toString();
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);

		}
		
		if(MRC.equalsIgnoreCase("")){
			
		} else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Test Result", "", MRC, "");
			Description="Scan of "+MRC+" is done in "+ Location;

			CycleEvent="MRC Test";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			String MRC_IH=Scope_IH[0];
			 MRCAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(MRC_IH+" = MRC Test for Reprocessor ItemHistory_PK");

			String ResultMRCCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultMRCCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultMRCCycle, OverallResult);
			
			String ResultMRCScope1_Assoc=IHV.Result_Different_Assoc(MRCAssociationID,Scope1InAssociationID)+" for MRC and "+Scope1;
			temp=ResultMRCScope1_Assoc.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultMRCScope1_Assoc, OverallResult);
			
			MRCTime=MRC_A.GetMRCTime(Unifia_Admin_Selenium.connstring, MRCAssociationID);
			
			String ResultMRCScope2_Assoc;
			if(Scope2.equalsIgnoreCase("")){
				ResultMRCScope2_Assoc="Second scope not provided.";
			} else{
				ResultMRCScope2_Assoc=IHV.Result_Different_Assoc(MRCAssociationID,Scope2InAssociationID)+" for MRC and "+Scope2;
				temp=ResultMRCScope2_Assoc.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultMRCScope2_Assoc, OverallResult);

			}

			ResultMRC="Pass: MRC_IH="+MRC_IH+"; MRCAssociationID="+MRCAssociationID+"; ResultMRCScope1_Assoc="+ResultMRCScope1_Assoc+"; "
					+ "ResultMRCScope2_Assoc="+ResultMRCScope2_Assoc+"; ResultMRCCycle"+ResultMRCCycle;
			//System.out.println(ResultMRC);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultMRC);

			if(Staff.equalsIgnoreCase("")){
				ResultStaffMRC="No Staff for reprocesing provided for MRC test";
				//System.out.println(ResultStaffMRC);
			}else {
				Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
				Description="Scan of Staff '" +Staff+"' is done in "+ Location+" after MRCTest is done";
				Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
				StaffMRCIH=Staff_IH[0];
				StaffMRC_Assoc=Staff_IH[1];
				ResultStaffMRC=IHV.Result_Same_Assoc(MRCAssociationID,StaffMRC_Assoc)+" for staff that performed the MRC Test.";
				temp=ResultStaffMRC.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultStaffMRC, OverallResult);

				//System.out.println(ResultStaffMRC);
				Expected=Description;
				IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffMRC);

			}
		}

		Thread.sleep(60000); //Wait 1 minute before scanning the scope out of the reprocessor

		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ Location;
		CycleEvent="Reprocessing Out";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		String Scope1OutIH=Scope_IH[0];
		String Scope1OutAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		StaffPK=0;
		//System.out.println(Scope1OutIH+" = Scope out of Reprocessor ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		ActualScopeState=Scope_IH[0];
		String ScopeOutLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		ExpectedState="5";
		OtherScopeStateID=0;
		ExpectedCabinet="0";

		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		temp=ResultLastStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(LocalScope1ExpectedRepro));
		temp=ResultReproCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(LocalScope1ExpectedExamCount));
		temp=ResultExamCount.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
		
		String ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent)+" for "+Description;
		temp=ResultScopeOutCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutCycle, OverallResult);

		String ResultScopesOut_AssocDiff=IHV.Result_Different_Assoc(Scope1InAssociationID, Scope1OutAssociationID)+" for Scope into and out of Reprocessor";
		temp=ResultScopesOut_AssocDiff.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopesOut_AssocDiff, OverallResult);

		String ResultScopeOutLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc)+" for "+Description;
		temp=ResultScopeOutLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutLoc, OverallResult);

		String ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState)+" for "+Description;
		temp=ResultScopeOutState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutState, OverallResult);

		String ResultReproOutScope1=Scope1+": ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		//System.out.println(ResultReproOutScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReproOutScope1);

		if(Staff.equalsIgnoreCase("")){
			ResultStaffOut1="No Staff for reprocesing provided for "+Scope1;
			//System.out.println(ResultStaffIn1);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope into Reprocessor";
			//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			CycleEvent="ReprocessingOutStaff";
			Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);			
			StaffInIH=Staff_IH[0];
			StaffOut_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			
			ResultScopeOut1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultScopeOut1StaffCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScopeOut1StaffCycle,OverallResult);

			ResultStaffOut1=IHV.Result_Same_Assoc(Scope1OutAssociationID,StaffOut_Assoc)+" for staff and scope out of reprocessor.";
			temp=ResultStaffOut1.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultStaffOut1,OverallResult);
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);

			ResultStaffOut1=ResultStaffOut1+" ResultScopeOut1StaffCycle="+ResultScopeOut1StaffCycle+". ResultLastStaff="+ResultLastStaff+".";
			//System.out.println(ResultStaffOut1);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffOut1);
		}
		
		//DailyDashboard Verification
		if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether "+Scope2+" is getting displayed in "+Location;
			Expected=Description;
			String result=DV.verifyDashboard(Location, Scope2, "", "", "");
			if(result.contains("#Failed!#")){
				result=result+" Bug 12881 opened";
			}

			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		//Verifying Chevron Color
		if(Staff.equalsIgnoreCase("")||Reason1.equalsIgnoreCase("")){
			expectedColor=DBP.rgbOfIncompleteFlow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
		}
		resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen";
		Expected="The Chevron color should be "+expectedColor;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
		
		IP_A.Click_Details(Scope1RefNo);
		
		ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,Scope1OutAssociationID);
		Scope1ReproOutTime=ScopeInfo[3];
		
		Result_Scope1ReproStatus=WF_V.Verify_ReproStatus("Unknown");
		System.out.println("Result_Scope1ReproStatus=:"+Result_Scope1ReproStatus);
		temp=Result_Scope1ReproStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStatus,OverallResult);
		
		Result_Scope1ReproComplete=WF_V.Verify_ReproCompleteTime("");
		System.out.println("Result_Scope1ReproComplete=:"+Result_Scope1ReproComplete);
		temp=Result_Scope1ReproComplete.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproComplete,OverallResult);
		
		String Result_ReproScope1OutStaff=WF_V.Verify_ReproOutStaff(StaffID);
		System.out.println("Result_ReproScope1OutStaff=:"+Result_ReproScope1OutStaff);
		temp=Result_ReproScope1OutStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ReproScope1OutStaff,OverallResult);

		Result_Scope1ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope1ReproOutTime);
		System.out.println("Result_Scope1ReproOutTime=:"+Result_Scope1ReproOutTime);
		temp=Result_Scope1ReproOutTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutTime,OverallResult);

		WF_A.Cancel(Changes);
		Description="Scan "+Scope1+" out of "+Location+" verify Scope Record Management";
		Expected=Description;
		String Result_SRM_Scope1_ReproOut="Result_Scope1ReproStatus="+Result_Scope1ReproStatus+". Result_Scope1ReproComplete="+Result_Scope1ReproComplete
				+". Result_ReproScope1OutStaff="+Result_ReproScope1OutStaff+". Result_Scope1ReproOutTime="+Result_Scope1ReproOutTime;
		
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_ReproOut);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning out of "+Location;
		Expected=Description;

		resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);

		if(Scope2.equalsIgnoreCase("")){
			ResultReproOutScope2="Scope 2 not passed into the function so skipping scans";
		} else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Scope", "", Scope2, "");
			Description="Scan of Scope '" +Scope2+"' is done in "+ Location;
			CycleEvent="Reprocessing Out";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			String ScopeOutIH2=Scope_IH[0];
			Scope2OutAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeOutIH2+" = Scope into Reprocessor ItemHistory_PK");
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope2);
			
			ActualScopeState=Scope_IH[0];
			ScopeInLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			ActualReproCount=Scope_IH[5];
			ActualExamCount=Scope_IH[6];
			
			ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultScopeOutCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScopeOutCycle,OverallResult);
			
			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
			
			ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(LocalScope2ExpectedRepro));
			temp=ResultReproCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
			
			ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(LocalScope2ExpectedExamCount));
			temp=ResultExamCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
			
			ResultScopesOut_AssocDiff=IHV.Result_Different_Assoc(Scope2InAssociationID, Scope2OutAssociationID)+" for Scope into and out of Reprocessor";
			temp=ResultScopesOut_AssocDiff.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopesOut_AssocDiff, OverallResult);

			ResultScopesOut_AssocDiff2=IHV.Result_Different_Assoc(Scope1InAssociationID, Scope2OutAssociationID)+" for "+Scope1+" and "+Scope2+" out of Reprocessor";
			temp=ResultScopesOut_AssocDiff2.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopesOut_AssocDiff2, OverallResult);

			ResultScopeOutLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc);
			temp=ResultScopeOutLoc.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScopeOutLoc,OverallResult);

			ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
			OverallResult=GF.FinalResult(temp[0], ResultScopeOutState,OverallResult);

			ResultReproOutScope2=Scope2+": ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopesOut_AssocDiff="+ResultScopesOut_AssocDiff+". "
					+ "ResultScopesOut_AssocDiff2="+ResultScopesOut_AssocDiff2+". ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
			//System.out.println(ResultReproOutScope2);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultReproOutScope2);

			if(Staff.equalsIgnoreCase("")){
				ResultStaffIn2="No Staff for reprocesing provided for scope2";
				//System.out.println(ResultStaffIn2);
			}else {
				Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
				Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope into Reprocessor";
				//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
				CycleEvent="ReprocessingOutStaff";
				Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
				StaffOutIH=Staff_IH[0];
				StaffOut_Assoc=Staff_IH[1];
				ActualCycleEvent=Staff_IH[5];
				
				ResultScopeOut2StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
				temp=ResultScopeOut2StaffCycle.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultScopeOut2StaffCycle,OverallResult);

				ResultStaffOut2=IHV.Result_Same_Assoc(Scope2OutAssociationID,StaffOut_Assoc)+" for staff and scope into reprocessor.";
				temp=ResultStaffOut2.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultStaffOut2,OverallResult);

				StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
				LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope2);
				ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
				temp=ResultLastStaff.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
				
				ResultStaffOut2=ResultStaffOut2+" ResultScopeOut2StaffCycle="+ResultScopeOut2StaffCycle+". ResultLastStaff="+ResultLastStaff+".";
				//System.out.println(ResultStaffOut2);
				Expected=Description;
				IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffOut2);
			}
			
			//DailyDashboard Verification
			if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
				Description="Verifying DailyDashboard tab to see whether nothing is getting displayed in "+Location;
				Expected=Description;
				String result=DV.verifyDashboard(Location, "", "", "", "");
				if(result.contains("#Failed!#")){
					result=result+" Bug 12881 opened";
				}

				IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
			}
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			//Verifying Chevron Color
			if(Staff.equalsIgnoreCase("")||Reason2.equalsIgnoreCase("")){
				expectedColor=DBP.rgbOfIncompleteFlow;
			}else{
				expectedColor=DBP.rgbOfCompletedFlow;
			}
			resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope2RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope2RefNo);
			
			ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope2,Scope2OutAssociationID);
			Scope2ReproOutTime=ScopeInfo[3];
			
			Result_Scope2ReproStatus=WF_V.Verify_ReproStatus("Unknown");
			System.out.println("Result_Scope2ReproStatus=:"+Result_Scope2ReproStatus);
			temp=Result_Scope2ReproStatus.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStatus,OverallResult);
			
			Result_Scope2ReproComplete=WF_V.Verify_ReproCompleteTime("");
			System.out.println("Result_Scope2ReproComplete=:"+Result_Scope2ReproComplete);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproComplete,OverallResult);
			
			String Result_ReproScope2OutStaff=WF_V.Verify_ReproOutStaff(StaffID);
			System.out.println("Result_ReproScope2OutStaff=:"+Result_ReproScope2OutStaff);
			temp=Result_ReproScope2OutStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ReproScope2OutStaff,OverallResult);

			String Result_Scope2ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope2ReproOutTime);
			System.out.println("Result_Scope2ReproOutTime=:"+Result_Scope2ReproOutTime);
			temp=Result_Scope2ReproOutTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutTime,OverallResult);

			WF_A.Cancel(Changes);
			Description="Scan "+Scope2+" out of "+Location+" verify Scope Record Management";
			Expected=Description;
			String Result_SRM_Scope2_ReproOut="Result_Scope2ReproStatus="+Result_Scope2ReproStatus+". Result_Scope2ReproComplete="+Result_Scope2ReproComplete
					+". Result_ReproScope2OutStaff="+Result_ReproScope2OutStaff+". Result_Scope2ReproOutTime="+Result_Scope2ReproOutTime;
			
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_ReproOut);
			
			//MAM verification
			MAM_A.Click_MaterialsAndAssetManagement();
			Description="Verify MAM screen for "+Scope2+" after scanning out of "+Location;
			Expected=Description;

			String resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
					+";EXAM COUNT=="+LocalScope2ExpectedExamCount+";REPROCESSING COUNT=="
					+LocalScope2ExpectedRepro).toString();
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);
			
		}
		//MRC verification
		if(!MRC.equalsIgnoreCase("")){
			IP_A.Click_InfectionPrevention();
			MRC_A.Click_MRCRecordManagement();
			String MRCDateTime[]=MRCTime.split(" ");
			String MRCDate=MRCDateTime[0];
			IP_A.DateFilter(MRCDate, MRCDate);
			IP_A.ApplyMRCFilter();
			Thread.sleep(2000);
			String gridID=MRC_A.getMRCGridID(MRCTime);
			Description="Verify MRC screen for MRC Result of "+Location;
			Expected=Description;
			String MRCResult[]=MRC.split(" ");//Taking Pass/Fail from Leak Test Pass/Leak Test Fail
			
			String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(gridID,"MRC TEST DATE/TIME=="+MRCTime+";REPROCESSOR=="+Location+";MRC Test Result=="+MRCResult[1]+";MRC TEST STAFF ID=="+StaffID).toString();
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MRC);
		}
		
		//System.out.println("OverallResult="+OverallResult);
		//System.out.println("OverallResult="+OverallResult);

		ReproResult[0]=Scope1InAssociationID;
		ReproResult[1]=Scope2InAssociationID;
		ReproResult[2]=MRCAssociationID;
		ReproResult[3]=Scope1OutAssociationID;
		ReproResult[4]=Scope2OutAssociationID;
		ReproResult[5]=OverallResult;
		ReproResult[6]=ResultReason1;
		ReproResult[7]=ResultReason2;
		
		return ReproResult;
	}


	public static String[] KEReprocessingRoomScans (String RoomScanner, String Reprocessor, String Scope1, String Scope2, String Staff, String StaffID, String Reason1, 
			String Reason2, String MRC, String ExpectedCycleCount1, String ExpectedCycleCount2, String OERModel, String OERSerialNum, String CycleEnd,
			int ScopeCnt, String Scope1SerialNo, String Scope1Model, String Scope2SerialNo, String Scope2Model, int duration, String DataType)throws InterruptedException, SQLException, ClassNotFoundException, AWTException{
		String[] ReproResult = new String[8];
		String Changes="No";
		Boolean Res;
		String [] temp= new String[2];
		String OverallResult="Pass";
		String Description;
		String Expected;
		String ResultReason1="";
		String Staff_IH[];
		String StaffInIH;
		String StaffIn_Assoc;
		String ResultStaffIn1="";
		String MRCAssociationID="";
		String StaffMRCIH;
		String StaffMRC_Assoc;
		String ResultStaffMRC="";
		String ResultReproInScope2;
		String ResultScopesIn_AssocDiff;
		String ResultReason2="";
		String ResultStaffIn2="";
		String StaffOut_Assoc;
		String ResultStaffOut1="";
		String ResultStaffOut2="";
		String ResultReproOutScope2="";
		String StaffOutIH;
		//String Scope2OutAssociationID="";
		//String Scope2InAssociationID="";
		String ResultScopesOut_AssocDiff2;
		String ResultMRC;
		int REPROCESSOR_KEY;
		int SCOPE1_KEY;
		int SCOPE2_KEY=0;
		int REPROCESSOR_MODEL_KEY;
		int REPROCESSOR_INFO_KEY;
		int REPROCESS1_USED_COUNT=2;
		int REPROCESS2_USED_COUNT=50;
		int REPROCESS_CONDITION_KEY=100;
		int REPROCESSED_SCOPES_KEY=100;
		int CLINICAL_STAFF_KEY;
		int Reprocessor_Available=4;
		int Reprocessor_Processing=5;
		int Reprocessor_Complete=9;
		int Reprocessor_Error=7;
		int Reprocessor_NotConnected=2;
		int ReproResult_Processing=1;
		int ReproResult_UnexpectedTerm=2;
		int ReproResult_ScopeRemoved=3;
		int ReproResult_ProcessComplete=5;
		String OERComplete2;
		String stmt1;
		String stmt2;
		ResultSet RS;
		int NumKECycleEvents=6;
		int [] ExpectedKECycleEvents=new int[NumKECycleEvents];
		int [] Scope1ActualKECycleEvents=new int[NumKECycleEvents];
		int [] Scope2ActualKECycleEvents=new int[NumKECycleEvents];
		String Scope1KEAssociationID=null;
		String Scope2KEAssociationID = null;
		String DataSourceID;
		String ResultsKECycleEventsScope1="";
		String ResultsKECycleEventsScope2="";
		String ActualLocationStatus;
		String ExpectedLocationStatus;
		String ResultLocationStauts;
		String ActualReproStatus1;
		String ActualReproStatus2;
		String ExpectedReproStatus;
		String ResultReproStatus1;
		String ResultReproStatus2;
		
		String Scope2RefNo="";
		String Scope1ModelName="";
		String Scope2ModelName="";
		String Result_RefNum2="";
		String Result_ScopeModel2="";
		String Result_ScopeName2="";
		String Result_ScopeSerialNo2="";
		String Result_Scope2Reprocessor="";
		String Result_Scope2ReproReason="";
		String Result_Scope2ReproInStaff="";
		String Result_Scope2ReproOutStaff="";
		String Result_Scope2ReproInTime="";
		String Result_Scope2ReproStart="";
		String Result_Scope2ReproComplete="";
		String Result_Scope2ReproStartStaff="";
		String Result_Scope2ReproTemp="";
		String Result_Scope2ReproStatus="";
		String Result_SRM_Scope2_ReproIn="";
		String Scope1ReproInTime="";
		String Scope2ReproInTime="";
		String Scope1ReproStartTime="";
		String Scope2ReproStartTime="";
		String Scope1ReproCompleteTime="";
		String Scope2ReproCompleteTime="";
		String Scope1ReproOutTime="";
		String Scope2ReproOutTime="";
		String ScopeInfo[];
		String ActualReason=null;
		String Scope1RefNo="";
		String MRCTime=null;

		ExpectedKECycleEvents[0]=31;
		ExpectedKECycleEvents[1]=31;
		ExpectedKECycleEvents[2]=34;
		ExpectedKECycleEvents[3]=35;
		ExpectedKECycleEvents[4]=36;
		ExpectedKECycleEvents[5]=1111;
		
		
		/*Commented the Scans using Reprocessor so as to use a Single Room Scanner. In this flow the
		 *  Reprocessor and Scopes information is fetched from KE and then Using Room Scanner first the 
		 *  Reprocessor is Scanned and then the MRC result is scanned. [YB-28Apr17]*/

		/* deleted old code that was commented out - it's maintained in TFS if we need to revert 
		 * added test to verify reprocessor count, exam count, and last staff id scanned.- NM 7july2017*/

		int StaffPK=0;
		int LocalScope1ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
		int LocalScope2ExpectedRepro=0;
		int LocalScope2ExpectedExamCount=0;
		
		String expectedColor=""; 
		String resultChevronColor="";

		if(Scope1.equalsIgnoreCase("Scope1")){
			Unifia_Admin_Selenium.Scope1ExpectedReproCount++;
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope1ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope1ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope2")){
			Unifia_Admin_Selenium.Scope2ExpectedReproCount++;
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope2ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope2ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope3")){
			Unifia_Admin_Selenium.Scope3ExpectedReproCount++;
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope3ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope3ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope4")){
			Unifia_Admin_Selenium.Scope4ExpectedReproCount++;
			LocalScope1ExpectedRepro=Unifia_Admin_Selenium.Scope4ExpectedReproCount;
			LocalScope1ExpectedExamCount=Unifia_Admin_Selenium.Scope4ExpectedExamCount;
		}
		
		if(Scope2.equalsIgnoreCase("Scope1")){
			Unifia_Admin_Selenium.Scope1ExpectedReproCount++;
			LocalScope2ExpectedRepro=Unifia_Admin_Selenium.Scope1ExpectedReproCount;
			LocalScope2ExpectedExamCount=Unifia_Admin_Selenium.Scope1ExpectedExamCount;
		}else if(Scope2.equalsIgnoreCase("Scope2")){
			Unifia_Admin_Selenium.Scope2ExpectedReproCount++;
			LocalScope2ExpectedRepro=Unifia_Admin_Selenium.Scope2ExpectedReproCount;
			LocalScope2ExpectedExamCount=Unifia_Admin_Selenium.Scope2ExpectedExamCount;
		}else if(Scope2.equalsIgnoreCase("Scope3")){
			Unifia_Admin_Selenium.Scope3ExpectedReproCount++;
			LocalScope2ExpectedRepro=Unifia_Admin_Selenium.Scope3ExpectedReproCount;
			LocalScope2ExpectedExamCount=Unifia_Admin_Selenium.Scope3ExpectedExamCount;
		}else if(Scope2.equalsIgnoreCase("Scope4")){
			Unifia_Admin_Selenium.Scope4ExpectedReproCount++;
			LocalScope2ExpectedRepro=Unifia_Admin_Selenium.Scope4ExpectedReproCount;
			LocalScope2ExpectedExamCount=Unifia_Admin_Selenium.Scope4ExpectedExamCount;
		}

		//DailyDashboard Verification
		if (Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether no scopes are getting displayed in "+Reprocessor+" and the status is Unavailable";
			Expected=Description;
			String result=DV.verifyDashboard(Reprocessor, "", "", dashboardpage.KEexpRepProcessingColorblack, dashboardpage.KEexpReproStatusUnAva);
			if(result.contains("#Failed!#")){
				result=result+" Bug 12881 opened";
			}

			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}

		
		//start Reprocessor in KE. 
		REPROCESSOR_MODEL_KEY=OER_GF.GetReproModel_Key(OERModel);
		REPROCESSOR_KEY=OER_GF.GetRepro_Key(OERSerialNum, REPROCESSOR_MODEL_KEY);
		REPROCESSOR_INFO_KEY=OER_GF.GetReproInfoKey(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd, REPROCESSOR_KEY);

		//Connection to ORacle DB
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

		//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		
		Statement update1 = conn.createStatement();
		SCOPE1_KEY=OER_GF.GetScope_Key(Scope1SerialNo);

		if(ScopeCnt==2){
			SCOPE2_KEY=OER_GF.GetScope_Key(Scope2SerialNo);		
		}

		CLINICAL_STAFF_KEY=OER_GF.GetStaff_Key(StaffID);
		//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("OER ("+OERSerialNum+") is about to start.");
		//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		Calendar date=Calendar.getInstance();
		
		long t=date.getTimeInMillis();
		String[] Times = OER_GF.KEServerDateTime_Duration(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd, duration);

		String OERStartTime=Times[0];
		
		System.out.println("The OER Started at:  "+OERStartTime );
		Date OERCompleteTime=new Date(t+ ((duration*60)*1000));
		String Complete= new SimpleDateFormat("yyyyMMdd_HHmmss").format(OERCompleteTime.getTime());
		Complete=Times[1];
		System.out.println("The OER cycle should complete at:  "+Complete);
		
		//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		//System.out.println("Cycle1 should end:  "+CycleEnd);
		
		if(DataType.equalsIgnoreCase("New")){
			REPROCESSOR_INFO_KEY=REPROCESSOR_KEY;
			OER_GF.InsertReproInfo(REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS1_USED_COUNT, Reprocessor_Processing);
		}else{
			REPROCESS_CONDITION_KEY=OER_GF.GetReproConditionKey(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			REPROCESS_CONDITION_KEY++;
			REPROCESSED_SCOPES_KEY=OER_GF.GetReproScopeKey(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			REPROCESSED_SCOPES_KEY++;
			REPROCESS1_USED_COUNT=OER_GF.GetReproCount(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd,REPROCESSOR_KEY);
			if(OER_GF.ReproInfoKeyAvailable(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd,REPROCESSOR_KEY)==0){
				OER_GF.InsertReproInfo(REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS1_USED_COUNT, Reprocessor_Processing);
			}else {
				stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Processing+", REPROCESS_USED_COUNT="+REPROCESS1_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+REPROCESSOR_INFO_KEY;
				//System.out.println("stmt1="+stmt1);
				update1.executeQuery(stmt1);
			}


		}
		OER_GF.InsertReproCondition(REPROCESS_CONDITION_KEY, REPROCESSOR_KEY, OERStartTime, Complete, REPROCESS1_USED_COUNT, CLINICAL_STAFF_KEY,ReproResult_Processing);
		if(ScopeCnt==2){
			//System.out.println("The OER is Started with 2 Scopes by Staff:  "+StaffID);
			//System.out.println("Scope1:  "+Scope1SerialNo);
			//System.out.println("Scope2:  "+Scope2SerialNo);
			OER_GF.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
			REPROCESSED_SCOPES_KEY++;
			OER_GF.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 2, SCOPE2_KEY, CLINICAL_STAFF_KEY);
			REPROCESSED_SCOPES_KEY++;

		}else{
			//System.out.println("The OER is Started with 1 Scope by Staff:  "+StaffID);
			//System.out.println("Scope1:  "+Scope1SerialNo);
			OER_GF.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
			REPROCESSED_SCOPES_KEY++;
		}


		//Wait 2 minutes and verify DB Fields 
		Thread.sleep((2*60)*1000);
		String ResultKEScope2;
		ExpectedLocationStatus="Processing";
		ExpectedReproStatus="Processing";

		//DailyDashboard Verification
		if (Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether Scope1 Scope2 is getting displayed in "+Reprocessor+" and the status is Processing";
			Expected=Description;
			String result=DV.verifyDashboard(Reprocessor, Scope1+"\n"+Scope2, "", dashboardpage.KEexpRepProcessingColorPurple, dashboardpage.KEexpReproStatusProc);
			if(result.contains("#Failed!#")){
				result=result+" Bug 12881 opened";
			}

			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		
		//Verify the LocationStatus is set to Processing
		ActualLocationStatus=IHV.Room_State(Unifia_Admin_Selenium.connstring, Reprocessor);
		ResultLocationStauts=IHV.Result_Room_State(ActualLocationStatus, ExpectedLocationStatus, Reprocessor);
		temp=ResultLocationStauts.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultLocationStauts, OverallResult);
		
		String[] Scope_IH;
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
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

		String ResultKEScope1="Scope1: ResultsKECycleEventsScope1= "+ResultsKECycleEventsScope1+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultLocationStauts="+ResultLocationStauts+".";
		
		Scope_IH=IHV.GetKEItemHistoryData(Unifia_Admin_Selenium.connstring, Reprocessor);
		DataSourceID=Scope_IH[2];

		if(Scope2.equalsIgnoreCase("")){
			Description="KE Data for "+DataSourceID+" with "+Scope1+" has started";

			Scope1KEAssociationID=Scope_IH[1];
			
			ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,Scope1KEAssociationID);
			Scope1RefNo=ScopeInfo[0];
			Scope1ModelName=ScopeInfo[2];
			Scope1ReproStartTime=ScopeInfo[3];
			
			//System.out.println("Scope1KEAssociationID:  "+Scope1KEAssociationID);
			Scope1ActualKECycleEvents=IHV.GetKECycleEventID(Unifia_Admin_Selenium.connstring, Scope1KEAssociationID);
			//System.out.println("Scope1ActualKECycleEvents:  "+Scope1ActualKECycleEvents);
			//System.out.println("Scope1ActualKECycleEvents:  "+Scope1ActualKECycleEvents[0]+", "+Scope1ActualKECycleEvents[1]+", "+Scope1ActualKECycleEvents[2]+", "+Scope1ActualKECycleEvents[3]+", "+Scope1ActualKECycleEvents[4]);
			Arrays.sort(Scope1ActualKECycleEvents);
			ResultsKECycleEventsScope1=IHV.Result_KECycleEvents(Scope1ActualKECycleEvents,ExpectedKECycleEvents,5);
			temp=ResultsKECycleEventsScope1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultsKECycleEventsScope1, OverallResult);
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			IP_V.verifyDateFormatINSRMScreen();
			
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(Reprocessor, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			Description="Verify Scope Record Management of "+Scope1+" KE Start Reprocessor Information "+Reprocessor+". ";

			String Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
			temp=Result_RefNum1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
			
			String Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1ModelName);
			temp=Result_ScopeModel1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

			String Result_ScopeName1=WF_V.Verify_ScopeName(Scope1);
			temp=Result_ScopeName1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

			String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
			temp=Result_ScopeSerialNo1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);
			//System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

			String Result_Scope1Reprocessor=WF_V.Verify_Reprossor(Reprocessor);
			System.out.println("Result_Scope1Reprocessor=:"+Result_Scope1Reprocessor);
			temp=Result_Scope1Reprocessor.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1Reprocessor,OverallResult);

			String Result_Scope1ReproReason=WF_V.Verify_ReproReason(Reason1);
			System.out.println("Result_Scope1ReproReason=:"+Result_Scope1ReproReason);
			temp=Result_Scope1ReproReason.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproReason,OverallResult);

			String Result_Scope1ReproInStaff=WF_V.Verify_ReproInStaff("");
			System.out.println("Result_Scope1ReproInStaff=:"+Result_Scope1ReproInStaff);
			temp=Result_Scope1ReproInStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInStaff,OverallResult);

			String Result_Scope1ReproInTime=WF_V.Verify_ReproScopeInTime(Scope1ReproInTime);
			
			System.out.println("Result_Scope1ReproInTime=:"+Result_Scope1ReproInTime);
			temp=Result_Scope1ReproInTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInTime,OverallResult);

			String Result_Scope1ReproStart=WF_V.Verify_ReproStartTime(Scope1ReproStartTime);
			System.out.println("Result_Scope1ReproStart=:"+Result_Scope1ReproStart);
			temp=Result_Scope1ReproStart.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStart,OverallResult);

			String Result_Scope1ReproStartStaff=WF_V.Verify_ReproStartStaff(StaffID);
			System.out.println("Result_Scope1ReproStartStaff=:"+Result_Scope1ReproStartStaff);
			temp=Result_Scope1ReproStartStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStartStaff,OverallResult);

			String Result_Scope1ReproTemp=WF_V.Verify_ReproTemp("21");
			System.out.println("Result_Scope1ReproTemp=:"+Result_Scope1ReproTemp);
			temp=Result_Scope1ReproTemp.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproTemp,OverallResult);
		
			String Result_Scope1ReproStatus=WF_V.Verify_ReproStatus(ExpectedReproStatus);
			System.out.println("Result_Scope1ReproStatus=:"+Result_Scope1ReproStatus);
			temp=Result_Scope1ReproStatus.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStatus,OverallResult);
			
			String Result_Scope1ReproComplete=WF_V.Verify_ReproCompleteTime(Scope1ReproCompleteTime);
			System.out.println("Result_Scope1ReproComplete=:"+Result_Scope1ReproComplete);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproComplete,OverallResult);
			
			String Result_Scope1ReproOutStaff=WF_V.Verify_ReproOutStaff("");
			System.out.println("Result_Scope1ReproOutStaff=:"+Result_Scope1ReproOutStaff);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutStaff,OverallResult);
			
			String Result_Scope1ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope1ReproOutTime);
			System.out.println("Result_Scope1ReproOutTime=:"+Result_Scope1ReproOutTime);
			temp=Result_Scope1ReproOutTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutTime,OverallResult);

			WF_A.Cancel(Changes);
			Expected=Description;
			String Result_SRM_Scope1_ReproIn="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
					+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1Reprocessor="+Result_Scope1Reprocessor+". Result_Scope1ReproReason"+Result_Scope1ReproReason
					+". Result_Scope1ReproInTime="+Result_Scope1ReproInTime+". Result_Scope1ReproInStaff"+Result_Scope1ReproInStaff
					+". Result_Scope1ReproStart="+Result_Scope1ReproStart+". Result_Scope1ReproStartStaff="+Result_Scope1ReproStartStaff+". Result_Scope1ReproTemp="+Result_Scope1ReproTemp
					+". Result_Scope1ReproStatus="+Result_Scope1ReproStatus+". Result_Scope1ReproComplete="+Result_Scope1ReproComplete+". Result_Scope1ReproOutStaff="+Result_Scope1ReproOutStaff
					+". Result_Scope1ReproOutTime="+Result_Scope1ReproOutTime;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_ReproIn);
			
			MAM_A.Click_MaterialsAndAssetManagement();

			Description="Verify MAM screen for "+Scope1+" KE Start Reprocessor Information "+Reprocessor+". ";
			Expected=Description;

			String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Reprocessor+" (YFN);LAST SCAN STAFF ID=="+StaffID
					+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
					+LocalScope1ExpectedRepro).toString();
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);


		} else {
			Description="KE Data for "+DataSourceID+" with "+Scope1+" and "+Scope2+" has started.";

			Scope2KEAssociationID=Scope_IH[1];
			
			ScopeInfo=ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope2,Scope2KEAssociationID);
			Scope2RefNo=ScopeInfo[0];
			Scope2ModelName=ScopeInfo[2];
			Scope2ReproStartTime=ScopeInfo[3];
			
			//System.out.println("Scope2KEAssociationID:  "+Scope2KEAssociationID);
			Scope2ActualKECycleEvents=IHV.GetKECycleEventID(Unifia_Admin_Selenium.connstring, Scope2KEAssociationID);
			//System.out.println("Scope2ActualKECycleEvents:  "+Scope2ActualKECycleEvents[0]+", "+Scope2ActualKECycleEvents[1]+", "+Scope2ActualKECycleEvents[2]+", "+Scope2ActualKECycleEvents[3]+", "+Scope2ActualKECycleEvents[4]);
			Arrays.sort(Scope2ActualKECycleEvents);
			ResultsKECycleEventsScope2=IHV.Result_KECycleEvents(Scope2ActualKECycleEvents,ExpectedKECycleEvents,5);
			temp=ResultsKECycleEventsScope2.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultsKECycleEventsScope2, OverallResult);

			Scope1KEAssociationID=IHV.GetKESecondAssociationID(Unifia_Admin_Selenium.connstring, Scope2KEAssociationID, DataSourceID);
			ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,Scope1KEAssociationID);
			Scope1RefNo=ScopeInfo[0];
			Scope1ModelName=ScopeInfo[2];
			Scope1ReproStartTime=ScopeInfo[3];

			//System.out.println("Scope1KEAssociationID:  "+Scope1KEAssociationID);
			Scope1ActualKECycleEvents=IHV.GetKECycleEventID(Unifia_Admin_Selenium.connstring, Scope1KEAssociationID);
			//System.out.println("Scope1ActualKECycleEvents:  "+Scope1ActualKECycleEvents[0]+", "+Scope1ActualKECycleEvents[1]+", "+Scope1ActualKECycleEvents[2]+", "+Scope1ActualKECycleEvents[3]+", "+Scope1ActualKECycleEvents[4]);
			Arrays.sort(Scope1ActualKECycleEvents);
			ResultsKECycleEventsScope1=IHV.Result_KECycleEvents(Scope1ActualKECycleEvents,ExpectedKECycleEvents,5);
			temp=ResultsKECycleEventsScope1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultsKECycleEventsScope1, OverallResult);

			//Verify the ReprocessingStatus is set to Processing
			ActualReproStatus2=IHV.Repro_Status(Unifia_Admin_Selenium.connstring, Reprocessor, Scope2);
			ResultReproStatus2=IHV.Result_Repro_State(ActualReproStatus2, ExpectedReproStatus, Reprocessor,Scope2);
			temp=ResultReproStatus2.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultReproStatus2, OverallResult);		
			
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope2);
			ActualReproCount=Scope_IH[5];
			ActualExamCount=Scope_IH[6];
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
			
			ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(LocalScope1ExpectedRepro));
			temp=ResultReproCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
			
			ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(LocalScope1ExpectedExamCount));
			temp=ResultExamCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);

			
			ResultKEScope2="Scope2: ResultsKECycleEventsScope2= "+ResultsKECycleEventsScope2+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultLocationStauts="+ResultLocationStauts+". ResultReproStatus2="+ResultReproStatus2;
			//System.out.println(ResultKEScope2);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultKEScope2);
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			IP_V.verifyDateFormatINSRMScreen();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(Reprocessor, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			Description="Verify Scope Record Management for "+Scope1+" KE Start Reprocessor Information "+Reprocessor+". ";

			String Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
			temp=Result_RefNum1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
			
			String Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1ModelName);
			temp=Result_ScopeModel1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

			String Result_ScopeName1=WF_V.Verify_ScopeName(Scope1);
			temp=Result_ScopeName1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

			String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
			temp=Result_ScopeSerialNo1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

			//System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

			String Result_Scope1Reprocessor=WF_V.Verify_Reprossor(Reprocessor);
			System.out.println("Result_Scope1Reprocessor=:"+Result_Scope1Reprocessor);
			temp=Result_Scope1Reprocessor.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1Reprocessor,OverallResult);

			String Result_Scope1ReproReason=WF_V.Verify_ReproReason(Reason1);
			System.out.println("Result_Scope1ReproReason=:"+Result_Scope1ReproReason);
			temp=Result_Scope1ReproReason.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproReason,OverallResult);

			String Result_Scope1ReproInStaff=WF_V.Verify_ReproInStaff("");
			System.out.println("Result_Scope1ReproInStaff=:"+Result_Scope1ReproInStaff);
			temp=Result_Scope1ReproInStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInStaff,OverallResult);

			String Result_Scope1ReproInTime=WF_V.Verify_ReproScopeInTime(Scope1ReproInTime);
			System.out.println("Result_Scope1ReproInTime=:"+Result_Scope1ReproInTime);
			temp=Result_Scope1ReproInTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInTime,OverallResult);

			String Result_Scope1ReproStart=WF_V.Verify_ReproStartTime(Scope1ReproStartTime);
			System.out.println("Result_Scope1ReproStart=:"+Result_Scope1ReproStart);
			temp=Result_Scope1ReproStart.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStart,OverallResult);

			String Result_Scope1ReproStartStaff=WF_V.Verify_ReproStartStaff(StaffID);
			System.out.println("Result_Scope1ReproStartStaff=:"+Result_Scope1ReproStartStaff);
			temp=Result_Scope1ReproStartStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStartStaff,OverallResult);

			String Result_Scope1ReproTemp=WF_V.Verify_ReproTemp("21");
			System.out.println("Result_Scope1ReproTemp=:"+Result_Scope1ReproTemp);
			temp=Result_Scope1ReproTemp.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproTemp,OverallResult);
		
			String Result_Scope1ReproStatus=WF_V.Verify_ReproStatus(ExpectedReproStatus);
			System.out.println("Result_Scope1ReproStatus=:"+Result_Scope1ReproStatus);
			temp=Result_Scope1ReproStatus.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStatus,OverallResult);

			String Result_Scope1ReproComplete=WF_V.Verify_ReproCompleteTime(Scope1ReproCompleteTime);
			System.out.println("Result_Scope1ReproComplete=:"+Result_Scope1ReproComplete);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproComplete,OverallResult);
			
			String Result_Scope1ReproOutStaff=WF_V.Verify_ReproOutStaff("");
			System.out.println("Result_Scope1ReproOutStaff=:"+Result_Scope1ReproOutStaff);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutStaff,OverallResult);

			String Result_Scope1ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope1ReproOutTime);
			System.out.println("Result_Scope1ReproOutTime=:"+Result_Scope1ReproOutTime);
			temp=Result_Scope1ReproOutTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutTime,OverallResult);

			WF_A.Cancel(Changes);
			Expected=Description;
			String Result_SRM_Scope1_ReproIn="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
					+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1Reprocessor="+Result_Scope1Reprocessor+". Result_Scope1ReproReason"+Result_Scope1ReproReason
					+". Result_Scope1ReproInTime="+Result_Scope1ReproInTime+". Result_Scope1ReproInStaff"+Result_Scope1ReproInStaff
					+". Result_Scope1ReproStart="+Result_Scope1ReproStart+". Result_Scope1ReproStartStaff="+Result_Scope1ReproStartStaff+". Result_Scope1ReproTemp="+Result_Scope1ReproTemp
					+". Result_Scope1ReproStatus="+Result_Scope1ReproStatus+". Result_Scope1ReproComplete="+Result_Scope1ReproComplete+". Result_Scope1ReproOutStaff="+Result_Scope1ReproOutStaff
					+". Result_Scope1ReproOutTime="+Result_Scope1ReproOutTime;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_ReproIn);
			
			MAM_A.Click_MaterialsAndAssetManagement();
			Description="Verify MAM screen for "+Scope1+" KE Start Reprocessor Information "+Reprocessor+". ";
			Expected=Description;

			String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Reprocessor+" (YFN);LAST SCAN STAFF ID=="+StaffID
					+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
					+LocalScope1ExpectedRepro).toString();
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			IP_V.verifyDateFormatINSRMScreen();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(Reprocessor, Scope2RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope2RefNo);
			
			Description="Verify Scope Record Management for "+Scope2+" KE Start Reprocessor Information "+Reprocessor+". ";

			Result_RefNum2=WF_V.Verify_RefNum(Scope2RefNo);
			temp=Result_RefNum1.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
			
			Result_ScopeModel2=WF_V.Verify_ScopeModel(Scope2ModelName);
			temp=Result_ScopeModel2.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeModel2,OverallResult);

			Result_ScopeName2=WF_V.Verify_ScopeName(Scope2);
			temp=Result_ScopeName2.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeName2,OverallResult);

			Result_ScopeSerialNo2=WF_V.Verify_ScopeSerialNum(Scope2SerialNo);
			temp=Result_ScopeSerialNo2.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo2,OverallResult);
			//System.out.println("Result_RefNum2=:"+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". Result_ScopeSerialNo2="+Result_ScopeSerialNo2);

			Result_Scope2Reprocessor=WF_V.Verify_Reprossor(Reprocessor);
			System.out.println("Result_Scope2Reprocessor=:"+Result_Scope2Reprocessor);
			temp=Result_Scope2Reprocessor.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2Reprocessor,OverallResult);

			Result_Scope2ReproReason=WF_V.Verify_ReproReason(Reason2);
			System.out.println("Result_Scope2ReproReason=:"+Result_Scope2ReproReason);
			temp=Result_Scope2ReproReason.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproReason,OverallResult);

			Result_Scope2ReproInStaff=WF_V.Verify_ReproInStaff("");
			System.out.println("Result_Scope2ReproInStaff=:"+Result_Scope2ReproInStaff);
			temp=Result_Scope2ReproInStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproInStaff,OverallResult);

			Result_Scope2ReproInTime=WF_V.Verify_ReproScopeInTime(Scope2ReproInTime);
			System.out.println("Result_Scope2ReproInTime=:"+Result_Scope2ReproInTime);
			temp=Result_Scope2ReproInTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproInTime,OverallResult);

			Result_Scope2ReproStart=WF_V.Verify_ReproStartTime(Scope2ReproStartTime);
			System.out.println("Result_Scope2ReproStart=:"+Result_Scope2ReproStart);
			temp=Result_Scope2ReproStart.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStart,OverallResult);

			Result_Scope2ReproStartStaff=WF_V.Verify_ReproStartStaff(StaffID);
			System.out.println("Result_Scope2ReproStartStaff=:"+Result_Scope2ReproStartStaff);
			temp=Result_Scope2ReproStartStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStartStaff,OverallResult);

			Result_Scope2ReproTemp=WF_V.Verify_ReproTemp("21");
			System.out.println("Result_Scope2ReproTemp=:"+Result_Scope2ReproTemp);
			temp=Result_Scope2ReproTemp.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproTemp,OverallResult);
		
			Result_Scope2ReproStatus=WF_V.Verify_ReproStatus(ExpectedReproStatus);
			System.out.println("Result_Scope2ReproStatus=:"+Result_Scope2ReproStatus);
			temp=Result_Scope2ReproStatus.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStatus,OverallResult);
			
			Result_Scope2ReproComplete=WF_V.Verify_ReproCompleteTime(Scope2ReproCompleteTime);
			System.out.println("Result_Scope2ReproComplete=:"+Result_Scope2ReproComplete);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproComplete,OverallResult);
			
			Result_Scope2ReproOutStaff=WF_V.Verify_ReproOutStaff("");
			System.out.println("Result_Scope2ReproOutStaff=:"+Result_Scope2ReproOutStaff);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutStaff,OverallResult);
			
			String Result_Scope2ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope2ReproOutTime);
			System.out.println("Result_Scope2ReproOutTime=:"+Result_Scope2ReproOutTime);
			temp=Result_Scope2ReproOutTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutTime,OverallResult);

			WF_A.Cancel(Changes);
			Expected=Description;
			Result_SRM_Scope2_ReproIn="Result_RefNum2="+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". "
					+ "Result_ScopeSerialNo2="+Result_ScopeSerialNo2+". Result_Scope2Reprocessor="+Result_Scope2Reprocessor+". Result_Scope2ReproReason"+Result_Scope2ReproReason
					+". Result_Scope2ReproInStaff"+Result_Scope2ReproInStaff+". Result_Scope2ReproInTime="+Result_Scope2ReproInTime
					+".Result_Scope2ReproStart="+Result_Scope2ReproStart+". Result_Scope2ReproStartStaff="+Result_Scope2ReproStartStaff+". Result_Scope2ReproTemp="+Result_Scope2ReproTemp
					+". Result_Scope2ReproStatus="+Result_Scope2ReproStatus+". Result_Scope2ReproComplete="+Result_Scope2ReproComplete+". Result_Scope2ReproOutStaff="+Result_Scope2ReproOutStaff
					+". Result_Scope2ReproOutTime="+Result_Scope2ReproOutTime;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_ReproIn);
			
			MAM_A.Click_MaterialsAndAssetManagement();
			Description="Verify MAM screen for "+Scope2+" KE Start Reprocessor Information "+Reprocessor+". ";
			Expected=Description;

			String resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+Reprocessor+" (YFN);LAST SCAN STAFF ID=="+StaffID
					+";EXAM COUNT=="+LocalScope2ExpectedExamCount+";REPROCESSING COUNT=="
					+LocalScope2ExpectedRepro).toString();
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);
			

		}
		
		//Verify the ReprocessingStatus is set to Processing
		ActualReproStatus1=IHV.Repro_Status(Unifia_Admin_Selenium.connstring, Reprocessor, Scope1);
		
		ResultReproStatus1=IHV.Result_Repro_State(ActualReproStatus1, ExpectedReproStatus, Reprocessor,Scope1);
		temp=ResultReproStatus1.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultReproStatus1, OverallResult);
				
		ResultKEScope1=ResultKEScope1+" ResultReproStatus1="+ResultReproStatus1;
		//System.out.println(ResultKEScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultKEScope1);

		//Calculate the remaining time for OER processing after 2 minutes has passed.
		duration=duration-2;
		
		Thread.sleep((duration*60)*1000);
		OERComplete2 = OER_GF.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

		//System.out.println("The OER cycle2 completed at:  "+OERComplete2);
		
		if(CycleEnd.equalsIgnoreCase("Normal")){
			stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_ProcessComplete+", REPROCESS_END_DATE=to_date('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS'), LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
			//System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Complete+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+REPROCESSOR_INFO_KEY;
			//System.out.println(stmt1);
			update1.executeQuery(stmt1);
			ExpectedLocationStatus="Processing Complete";
			ExpectedReproStatus="Processing Complete";

		}else if (CycleEnd.equalsIgnoreCase("Unexpected Termination")){
			stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_UnexpectedTerm+", REPROCESS_END_DATE=to_date('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS'), LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
			//System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Error+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+REPROCESSOR_INFO_KEY;
			//System.out.println(stmt1);
			update1.executeQuery(stmt1);
			ExpectedLocationStatus="Error";
			ExpectedReproStatus="Unexpected Termination";			

		}
		Thread.sleep((2*60)*1000);//Wait for data to be transferred to Unifia.
		
		//DailyDashboard Verification
		if (Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether Scope1 Scope2 is getting displayed in "+Reprocessor+" and the status is Processing Complete";
			Expected=Description;
			String result=DV.verifyDashboard(Reprocessor, Scope1+"\n"+Scope2, "", dashboardpage.KEexpRepUnExpTermColorAmber, dashboardpage.KEexpReproStatusProcComp);
			if(result.contains("#Failed!#")){
				result=result+" Bug 12881 opened";
			}

			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		
		//Verify the LocationStatus is set to Processing Complete or Unexpected Termination
		Description="KE Data for "+DataSourceID+" "+ExpectedReproStatus+".";
		ActualLocationStatus=IHV.Room_State(Unifia_Admin_Selenium.connstring, Reprocessor);
		ResultLocationStauts=IHV.Result_Room_State(ActualLocationStatus, ExpectedLocationStatus, Reprocessor);
		temp=ResultLocationStauts.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultLocationStauts, OverallResult);
		
		//Verify the ReprocessingStatus is set to Processing Complete or Unexpected Termination
		ActualReproStatus1=IHV.Repro_Status(Unifia_Admin_Selenium.connstring, Reprocessor, Scope1);
		
		ResultReproStatus1=IHV.Result_Repro_State(ActualReproStatus1, ExpectedReproStatus, Reprocessor,Scope1);
		temp=ResultReproStatus1.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultReproStatus1, OverallResult);
				
		ResultKEScope1="Scope1: ResultLocationStauts="+ResultLocationStauts+". ResultReproStatus1="+ResultReproStatus1;
		//System.out.println(ResultKEScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultKEScope1);

		Scope1ReproCompleteTime=IHV.GetReproCompleteTime(Unifia_Admin_Selenium.connstring, Scope1, Scope1KEAssociationID);

		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		//Verifying Chevron Color
		expectedColor=DBP.rgbOfIncompleteFlow;
		resultChevronColor=WF_V.Verify_ChevronColor(Reprocessor, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen";
		Expected="The Chevron color should be "+expectedColor;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
		
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management for "+Scope1+" KE data - oer complete with status="+ExpectedReproStatus;

		String Result_Scope1Reprocessor=WF_V.Verify_Reprossor(Reprocessor);
		System.out.println("Result_Scope1Reprocessor=:"+Result_Scope1Reprocessor);
		temp=Result_Scope1Reprocessor.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Reprocessor,OverallResult);

		String Result_Scope1ReproReason=WF_V.Verify_ReproReason(Reason1);
		System.out.println("Result_Scope1ReproReason=:"+Result_Scope1ReproReason);
		temp=Result_Scope1ReproReason.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproReason,OverallResult);

		String Result_Scope1ReproInStaff=WF_V.Verify_ReproInStaff("");
		System.out.println("Result_Scope1ReproInStaff=:"+Result_Scope1ReproInStaff);
		temp=Result_Scope1ReproInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInStaff,OverallResult);

		String Result_Scope1ReproInTime=WF_V.Verify_ReproScopeInTime(Scope1ReproInTime);
		System.out.println("Result_Scope1ReproInTime=:"+Result_Scope1ReproInTime);
		temp=Result_Scope1ReproInTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInTime,OverallResult);

		String Result_Scope1ReproStart=WF_V.Verify_ReproStartTime(Scope1ReproStartTime);
		System.out.println("Result_Scope1ReproStart=:"+Result_Scope1ReproStart);
		temp=Result_Scope1ReproStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStart,OverallResult);

		String Result_Scope1ReproStartStaff=WF_V.Verify_ReproStartStaff(StaffID);
		System.out.println("Result_Scope1ReproStartStaff=:"+Result_Scope1ReproStartStaff);
		temp=Result_Scope1ReproStartStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStartStaff,OverallResult);

		String Result_Scope1ReproTemp=WF_V.Verify_ReproTemp("21");
		System.out.println("Result_Scope1ReproTemp=:"+Result_Scope1ReproTemp);
		temp=Result_Scope1ReproTemp.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproTemp,OverallResult);
	
		String Result_Scope1ReproStatus=WF_V.Verify_ReproStatus(ExpectedReproStatus);
		System.out.println("Result_Scope1ReproStatus=:"+Result_Scope1ReproStatus);
		temp=Result_Scope1ReproStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStatus,OverallResult);

		String Result_Scope1ReproComplete=WF_V.Verify_ReproCompleteTime(Scope1ReproCompleteTime);
		System.out.println("Result_Scope1ReproComplete=:"+Result_Scope1ReproComplete);
		temp=Result_Scope1ReproComplete.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproComplete,OverallResult);
		
		String Result_Scope1ReproOutStaff=WF_V.Verify_ReproOutStaff("");
		System.out.println("Result_Scope1ReproOutStaff=:"+Result_Scope1ReproOutStaff);
		temp=Result_Scope1ReproComplete.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutStaff,OverallResult);

		String Result_Scope1ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope1ReproOutTime);
		System.out.println("Result_Scope1ReproOutTime=:"+Result_Scope1ReproOutTime);
		temp=Result_Scope1ReproOutTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutTime,OverallResult);

		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope1_ReproIn="Result_Scope1Reprocessor="+Result_Scope1Reprocessor+". Result_Scope1ReproReason"+Result_Scope1ReproReason
				+". Result_Scope1ReproInStaff"+Result_Scope1ReproInStaff+". Result_Scope1ReproInTime="+Result_Scope1ReproInTime
				+". Result_Scope1ReproStart="+Result_Scope1ReproStart+". Result_Scope1ReproStartStaff="+Result_Scope1ReproStartStaff+". Result_Scope1ReproTemp="+Result_Scope1ReproTemp
				+". Result_Scope1ReproStatus="+Result_Scope1ReproStatus+". Result_Scope1ReproComplete="+Result_Scope1ReproComplete+". Result_Scope1ReproOutStaff="+Result_Scope1ReproOutStaff
				+". Result_Scope1ReproOutTime="+Result_Scope1ReproOutTime;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_ReproIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" KE data - oer complete with status="+ExpectedReproStatus;
		Expected=Description;

		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Reprocessor+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);

		
		if(Scope2.equalsIgnoreCase("")){
			//do nothing as scope2 is not used
		} else {
			ActualReproStatus2=IHV.Repro_Status(Unifia_Admin_Selenium.connstring, Reprocessor, Scope2);
			ResultReproStatus2=IHV.Result_Repro_State(ActualReproStatus2, ExpectedReproStatus, Reprocessor,Scope2);
			temp=ResultReproStatus2.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultReproStatus2, OverallResult);		
			
			ResultKEScope2="Scope2: ResultLocationStauts="+ResultLocationStauts+". ResultReproStatus2="+ResultReproStatus2;
			//System.out.println(ResultKEScope2);
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultKEScope2);
			
			Scope2ReproCompleteTime=IHV.GetReproCompleteTime(Unifia_Admin_Selenium.connstring, Scope2, Scope2KEAssociationID);

			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(Reprocessor, Scope2RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope2RefNo);
			Description="Verify Scope Record Management for "+Scope2+" KE data - oer complete with status="+ExpectedReproStatus;

			Result_Scope2Reprocessor=WF_V.Verify_Reprossor(Reprocessor);
			System.out.println("Result_Scope2Reprocessor=:"+Result_Scope2Reprocessor);
			temp=Result_Scope2Reprocessor.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2Reprocessor,OverallResult);

			Result_Scope2ReproReason=WF_V.Verify_ReproReason(Reason2);
			System.out.println("Result_Scope2ReproReason=:"+Result_Scope2ReproReason);
			temp=Result_Scope2ReproReason.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproReason,OverallResult);

			Result_Scope2ReproInStaff=WF_V.Verify_ReproInStaff("");
			System.out.println("Result_Scope2ReproInStaff=:"+Result_Scope2ReproInStaff);
			temp=Result_Scope2ReproInStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproInStaff,OverallResult);

			Result_Scope2ReproInTime=WF_V.Verify_ReproScopeInTime(Scope2ReproInTime);
			System.out.println("Result_Scope2ReproInTime=:"+Result_Scope2ReproInTime);
			temp=Result_Scope2ReproInTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproInTime,OverallResult);

			Result_Scope2ReproStart=WF_V.Verify_ReproStartTime(Scope2ReproStartTime);
			System.out.println("Result_Scope2ReproStart=:"+Result_Scope2ReproStart);
			temp=Result_Scope2ReproStart.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStart,OverallResult);

			Result_Scope2ReproStartStaff=WF_V.Verify_ReproStartStaff(StaffID);
			System.out.println("Result_Scope2ReproStartStaff=:"+Result_Scope2ReproStartStaff);
			temp=Result_Scope2ReproStartStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStartStaff,OverallResult);

			Result_Scope2ReproTemp=WF_V.Verify_ReproTemp("21");
			System.out.println("Result_Scope2ReproTemp=:"+Result_Scope2ReproTemp);
			temp=Result_Scope2ReproTemp.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproTemp,OverallResult);
		
			Result_Scope2ReproStatus=WF_V.Verify_ReproStatus(ExpectedReproStatus);
			System.out.println("Result_Scope2ReproStatus=:"+Result_Scope2ReproStatus);
			temp=Result_Scope2ReproStatus.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStatus,OverallResult);
			
			Result_Scope2ReproComplete=WF_V.Verify_ReproCompleteTime(Scope2ReproCompleteTime);
			System.out.println("Result_Scope2ReproComplete=:"+Result_Scope2ReproComplete);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproComplete,OverallResult);
			
			Result_Scope2ReproOutStaff=WF_V.Verify_ReproOutStaff("");
			System.out.println("Result_Scope2ReproOutStaff=:"+Result_Scope2ReproOutStaff);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutStaff,OverallResult);
			
			String Result_Scope2ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope2ReproOutTime);
			System.out.println("Result_Scope2ReproOutTime=:"+Result_Scope2ReproOutTime);
			temp=Result_Scope2ReproOutTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutTime,OverallResult);

			WF_A.Cancel(Changes);
			Expected=Description;
			Result_SRM_Scope2_ReproIn="Result_Scope2Reprocessor="+Result_Scope2Reprocessor+". Result_Scope2ReproReason"+Result_Scope2ReproReason
					+". Result_Scope2ReproInStaff"+Result_Scope2ReproInStaff+". Result_Scope2ReproInTime="+Result_Scope2ReproInTime
					+". Result_Scope2ReproStart="+Result_Scope2ReproStart+". Result_Scope2ReproStartStaff="+Result_Scope2ReproStartStaff+". Result_Scope2ReproTemp="+Result_Scope2ReproTemp
					+". Result_Scope2ReproStatus="+Result_Scope2ReproStatus+". Result_Scope2ReproComplete="+Result_Scope2ReproComplete+". Result_Scope2ReproOutStaff="+Result_Scope2ReproOutStaff
					+". Result_Scope2ReproOutTime="+Result_Scope2ReproOutTime;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_ReproIn);
			
			MAM_A.Click_MaterialsAndAssetManagement();

			Description="Verify MAM screen for "+Scope2+" KE data - oer complete with status="+ExpectedReproStatus;
			Expected=Description;

			String resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+Reprocessor+" (YFN);LAST SCAN STAFF ID=="+StaffID
					+";EXAM COUNT=="+LocalScope2ExpectedExamCount+";REPROCESSING COUNT=="
					+LocalScope2ExpectedRepro).toString();
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope2_MAM);

		}
		
		OERComplete2 = OER_GF.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		if(CycleEnd.equalsIgnoreCase("Normal")){
			stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_ScopeRemoved+", LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
			//System.out.println(stmt1);
			update1.executeQuery(stmt1);
			ExpectedReproStatus="Scope Removed";

		}else if (CycleEnd.equalsIgnoreCase("Unexpected Termination")){
			ExpectedReproStatus="Unexpected Termination";			

		}
		
		stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Available+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+REPROCESSOR_INFO_KEY;

		//System.out.println(stmt1);
		update1.executeQuery(stmt1);
		
		REPROCESS_CONDITION_KEY++;
		ExpectedKECycleEvents[0]=31;
		ExpectedKECycleEvents[1]=32;
		ExpectedKECycleEvents[2]=32;
		ExpectedKECycleEvents[3]=34;
		ExpectedKECycleEvents[4]=35;
		ExpectedKECycleEvents[5]=36;

		Thread.sleep((2*60)*1000);//wait for data to be sent to Unifia.
		
		//DailyDashboard Verification
		if (Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether Scope1 Scope2 is getting displayed in "+Reprocessor+" and the status is Available";
			Expected=Description;
			String result=DV.verifyDashboard(Reprocessor, Scope1+"\n"+Scope2, "", dashboardpage.KEexpRepAvailableColorGreen, dashboardpage.KEexpReproStatusAva);
			if(result.contains("#Failed!#")){
				result=result+" Bug 12881 opened";
			}

			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		//Verify the LocationStatus is set to Processing Complete
		Description="KE Data for "+DataSourceID+" Scopes were removed and "+Reprocessor+" is Available.";

		ExpectedLocationStatus="Available";
		ActualLocationStatus=IHV.Room_State(Unifia_Admin_Selenium.connstring, Reprocessor);
		ResultLocationStauts=IHV.Result_Room_State(ActualLocationStatus, ExpectedLocationStatus, Reprocessor);
		temp=ResultLocationStauts.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultLocationStauts, OverallResult);
		
		//Verify the ReprocessingStatus is set to Scope Removed  
		ActualReproStatus1=IHV.Repro_Status(Unifia_Admin_Selenium.connstring, Reprocessor, Scope1);
		
		ResultReproStatus1=IHV.Result_Repro_State(ActualReproStatus1, ExpectedReproStatus, Reprocessor,Scope1);
		temp=ResultReproStatus1.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultReproStatus1, OverallResult);

		Description="Verify Scope Record Management for "+Scope1+" KE data - oer complete with status="+ExpectedReproStatus;

		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);

		Result_Scope1ReproStatus=WF_V.Verify_ReproStatus(ExpectedReproStatus);
		System.out.println("Result_Scope1ReproStatus=:"+Result_Scope1ReproStatus);
		temp=Result_Scope1ReproStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproStatus,OverallResult);

		Result_Scope1ReproComplete=WF_V.Verify_ReproCompleteTime(Scope1ReproCompleteTime);
		System.out.println("Result_Scope1ReproComplete=:"+Result_Scope1ReproComplete);
		temp=Result_Scope1ReproComplete.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproComplete,OverallResult);

		Result_Scope1ReproOutStaff=WF_V.Verify_ReproOutStaff("");
		System.out.println("Result_Scope1ReproOutStaff=:"+Result_Scope1ReproOutStaff);
		temp=Result_Scope1ReproComplete.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutStaff,OverallResult);

		Result_Scope1ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope1ReproOutTime);
		System.out.println("Result_Scope1ReproOutTime=:"+Result_Scope1ReproOutTime);
		temp=Result_Scope1ReproOutTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutTime,OverallResult);

		WF_A.Cancel(Changes);
		Expected=Description;
		Result_SRM_Scope1_ReproIn="Result_Scope1ReproStatus="+Result_Scope1ReproStatus+". Result_Scope1ReproComplete="+Result_Scope1ReproComplete
				+". Result_Scope1ReproOutStaff="+Result_Scope1ReproOutStaff+". Result_Scope1ReproOutTime="+Result_Scope1ReproOutTime;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope1_ReproIn);
		
		if(Scope2.equalsIgnoreCase("")){
			Scope1ActualKECycleEvents=IHV.GetKECycleEventID(Unifia_Admin_Selenium.connstring, Scope1KEAssociationID);
			//System.out.println("Scope1ActualKECycleEvents:  "+Scope1ActualKECycleEvents);
			//System.out.println("Scope1ActualKECycleEvents:  "+Scope1ActualKECycleEvents[0]+", "+Scope1ActualKECycleEvents[1]+", "+Scope1ActualKECycleEvents[2]+", "+Scope1ActualKECycleEvents[3]+", "+Scope1ActualKECycleEvents[4]);
			Arrays.sort(Scope1ActualKECycleEvents);
			ResultsKECycleEventsScope1=IHV.Result_KECycleEvents(Scope1ActualKECycleEvents,ExpectedKECycleEvents,6);
			temp=ResultsKECycleEventsScope1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultsKECycleEventsScope1, OverallResult);

		} else {
			Scope2ActualKECycleEvents=IHV.GetKECycleEventID(Unifia_Admin_Selenium.connstring, Scope2KEAssociationID);
			//System.out.println("Scope2ActualKECycleEvents:  "+Scope2ActualKECycleEvents[0]+", "+Scope2ActualKECycleEvents[1]+", "+Scope2ActualKECycleEvents[2]+", "+Scope2ActualKECycleEvents[3]+", "+Scope2ActualKECycleEvents[4]);
			Arrays.sort(Scope2ActualKECycleEvents);
			ResultsKECycleEventsScope2=IHV.Result_KECycleEvents(Scope2ActualKECycleEvents,ExpectedKECycleEvents,6);
			temp=ResultsKECycleEventsScope2.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultsKECycleEventsScope2, OverallResult);

			Scope1ActualKECycleEvents=IHV.GetKECycleEventID(Unifia_Admin_Selenium.connstring, Scope1KEAssociationID);
			//System.out.println("Scope1ActualKECycleEvents:  "+Scope1ActualKECycleEvents[0]+", "+Scope1ActualKECycleEvents[1]+", "+Scope1ActualKECycleEvents[2]+", "+Scope1ActualKECycleEvents[3]+", "+Scope1ActualKECycleEvents[4]);
			Arrays.sort(Scope1ActualKECycleEvents);
			ResultsKECycleEventsScope1=IHV.Result_KECycleEvents(Scope1ActualKECycleEvents,ExpectedKECycleEvents,5);
			temp=ResultsKECycleEventsScope1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultsKECycleEventsScope1, OverallResult);
			
			//Verify the ReprocessingStatus is set to Scope Removed
			ActualReproStatus2=IHV.Repro_Status(Unifia_Admin_Selenium.connstring, Reprocessor, Scope2);
			ResultReproStatus2=IHV.Result_Repro_State(ActualReproStatus2, ExpectedReproStatus, Reprocessor,Scope2);
			temp=ResultReproStatus2.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultReproStatus2, OverallResult);					
			
			ResultKEScope2="Scope2: ResultsKECycleEventsScope2= "+ResultsKECycleEventsScope2+". ResultLocationStauts="+ResultLocationStauts+". ResultReproStatus2="+ResultReproStatus2;
			//System.out.println(ResultKEScope2);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultKEScope2);

			Description="Verify Scope Record Management for "+Scope2+" KE data - oer complete with status="+ExpectedReproStatus;

			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			IP_A.Click_Details(Scope2RefNo);

			Result_Scope2ReproStatus=WF_V.Verify_ReproStatus(ExpectedReproStatus);
			System.out.println("Result_Scope2ReproStatus=:"+Result_Scope2ReproStatus);
			temp=Result_Scope2ReproStatus.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproStatus,OverallResult);

			Result_Scope2ReproComplete=WF_V.Verify_ReproCompleteTime(Scope2ReproCompleteTime);
			System.out.println("Result_Scope2ReproComplete=:"+Result_Scope2ReproComplete);
			temp=Result_Scope2ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproComplete,OverallResult);
			
			Result_Scope2ReproOutStaff=WF_V.Verify_ReproOutStaff("");
			System.out.println("Result_Scope2ReproOutStaff=:"+Result_Scope2ReproOutStaff);
			temp=Result_Scope1ReproComplete.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutStaff,OverallResult);
			
			String Result_Scope2ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope2ReproOutTime);
			System.out.println("Result_Scope2ReproOutTime=:"+Result_Scope2ReproOutTime);
			temp=Result_Scope2ReproOutTime.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ReproOutTime,OverallResult);

			WF_A.Cancel(Changes);
			Expected=Description;
			Result_SRM_Scope2_ReproIn="Result_Scope2ReproStatus="+Result_Scope2ReproStatus+". Result_Scope2ReproComplete="+Result_Scope2ReproComplete
					+". Result_Scope2ReproOutStaff="+Result_Scope2ReproOutStaff+". Result_Scope2ReproOutTime="+Result_Scope2ReproOutTime;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result_SRM_Scope2_ReproIn);
			

		}
		
		ResultKEScope1="Scope1: ResultsKECycleEventsScope1= "+ResultsKECycleEventsScope1+". ResultLocationStauts="+ResultLocationStauts+". ResultReproStatus1="+ResultReproStatus1;
		//System.out.println(ResultKEScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultKEScope1);		

		
		//Scanning Reprocessor with Reprocessing Room Scanner
		System.out.println("Scanning Reprocessor with Reprocessing Room Scanner");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(RoomScanner, "Reprocessor", "", Reprocessor, "");
		
		
		if(MRC.equalsIgnoreCase("")){
			
		} else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(RoomScanner, "Test Result", "", MRC, "");
			Description="Scan of "+MRC+" is done in "+ RoomScanner;

			String CycleEvent="MRC Test";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, RoomScanner);
			String MRC_IH=Scope_IH[0];
			 MRCAssociationID=Scope_IH[1];
			String ActualCycleEvent=Scope_IH[5];
			//System.out.println(MRC_IH+" = MRC Test for Reprocessor ItemHistory_PK");

			String ResultMRCCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultMRCCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultMRCCycle, OverallResult);

			ResultMRC="Pass: MRC_IH="+MRC_IH+"; MRCAssociationID="+MRCAssociationID+"; ResultMRCCycle"+ResultMRCCycle;
			//System.out.println(ResultMRC);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultMRC);

			if(Staff.equalsIgnoreCase("")){
				ResultStaffMRC="No Staff for reprocesing provided for MRC test";
				//System.out.println(ResultStaffMRC);
			}else {
				Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
				Res = EM_A.ScanItem(RoomScanner, "Staff", "Tech", Staff, "");
				Description="Scan of Staff '" +Staff+"' is done in "+ RoomScanner+" after MRCTest is done";
				Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, RoomScanner);
				StaffMRCIH=Staff_IH[0];
				StaffMRC_Assoc=Staff_IH[1];
				ResultStaffMRC=IHV.Result_Same_Assoc(MRCAssociationID,StaffMRC_Assoc)+" for staff that performed the MRC Test.";
				temp=ResultStaffMRC.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultStaffMRC, OverallResult);
				MRCTime=MRC_A.GetMRCTime(Unifia_Admin_Selenium.connstring, MRCAssociationID);
				//System.out.println(ResultStaffMRC);
				Expected=Description;
				IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaffMRC);

			}
		}

		//MRC verification
		if(!MRC.equalsIgnoreCase("")){
			IP_A.Click_InfectionPrevention();
			MRC_A.Click_MRCRecordManagement();
			String MRCDateTime[]=MRCTime.split(" ");
			String MRCDate=MRCDateTime[0];
			IP_A.DateFilter(MRCDate, MRCDate);
			IP_A.ApplyMRCFilter();
			Thread.sleep(2000);
			String gridID=MRC_A.getMRCGridID(MRCTime);
			Description="Verify MRC screen for MRC Result of "+RoomScanner;
			Expected=Description;
			String MRCResult[]=MRC.split(" ");//Taking Pass/Fail from Leak Test Pass/Leak Test Fail
			
			String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(gridID,"MRC TEST DATE/TIME=="+MRCTime+";REPROCESSOR=="+Reprocessor+";MRC Test Result=="+MRCResult[1]+";MRC TEST STAFF ID=="+StaffID).toString();
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MRC);
		}
		
		ReproResult[0]=Scope1KEAssociationID;
		ReproResult[1]=Scope2KEAssociationID;
		ReproResult[2]=MRCAssociationID;
		ReproResult[3]=OverallResult;
		
		return ReproResult;
	}

}
