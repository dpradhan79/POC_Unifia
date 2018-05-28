package TestFrameWork.RegressionTest.Culture;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;

public class CultureRegression_Actions extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	static TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	
	
	public static String[] CultureObtainedScans (String Location, String Scope1, String Staff,String StaffID,String ExpectedCycleCount)throws InterruptedException{
		String[] CultureObtainedResult = new String[2];
		String [] temp= new String[2];
		String OverallResult="Pass";
		Boolean Res;
		String Description;
		String Expected;
		String Staff_IH[];
		String StaffIH;
		String Staff_Assoc;
		String ResultStaff="";
		String ExpectedCabinet="0";
		String CultureObtained="Awaiting Results";
		int StaffPK=0;
		int LocalScope1ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
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
		String CycleEvent="Culture Obtained";  //Expected cycle Event
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		String Scope1InIH=Scope_IH[0]; //the ItemHistoryID_PK for the scope scan.
		String Scope1InAssociationID=Scope_IH[1];//the associationID for the scope scan
		String ActualCycleEvent=Scope_IH[5];//the actual cycle event
		
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,Scope1InAssociationID);
		String Scope1RefNo=ScopeInfo[0];
		String Scope1SerialNo=ScopeInfo[1];
		String Scope1Model=ScopeInfo[2];
		String Scope1ExamTime=ScopeInfo[3];
						
		//System.out.println(Scope1InIH+" = Scope with Culture Obtained ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeInLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		String ExpectedState="5"; //expected scope state = 5 which is intransit.
		int OtherScopeStateID=7; //expected otherscopestateID of 7 = culture obtained. 		

		String ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent); //verify the actual cycle event matches the expected cycle event
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

		String ResultScopeInLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc); //verify the actual location and subloaction matches the expected location and subloaction
		temp=ResultScopeInLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);

		String ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState); //verify the actual ScopeStateID and OtherScopeStateID matches the expected ScopeStateID and OtherScopeStateID
		temp=ResultScopeInState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount); //Verify the actual cycle count matches the expected cycle count.
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultSinkScope1="Scope1: ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". "
				+ "ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		//System.out.println(ResultSinkScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultSinkScope1);

		//Scan Staff if provided. 
		if(Staff.equalsIgnoreCase("")){
			ResultStaff="No Staff provided for culture obtained for "+Scope1;
			//System.out.println(ResultStaff);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope Culture Obtained";
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			StaffIH=Staff_IH[0];
			Staff_Assoc=Staff_IH[1];
			ResultStaff=IHV.Result_Same_Assoc(Scope1InAssociationID,Staff_Assoc)+" for staff and scope Culture Obtained.";//Verify the scope scan and staff scan have the same associationID
			temp=ResultStaff.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaff, OverallResult);

			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
			
			ResultStaff=ResultStaff+" ResultLastStaff="+ResultLastStaff+".";

			//System.out.println(ResultStaff);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaff);

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
		
		String CultureResult=WF_V.Verify_Culture(CultureObtained);
		temp=CultureResult.split("-");
		OverallResult=GF.FinalResult(temp[0], CultureResult,OverallResult);
		
		Expected=Description;

		System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". CultureResult= "+CultureResult);
		String finalRes="Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1 +". CultureResult= "+CultureResult;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, finalRes);
		
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		String result_MAM=MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
	
		CultureObtainedResult[0]=Scope1InAssociationID;
		CultureObtainedResult[1]=OverallResult;
		
		return CultureObtainedResult;
	}


}
