package TestFrameWork.RegressionTest.AdminScanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;

public class AdminRegression_Actions extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	static TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;


	public static String[] OutOfFacilityScans (String Location, String Scope1, String Staff,String StaffID, String Destination, String ExpectedCycleCount)throws InterruptedException{
		String[] OOFResult = new String[2];
		String [] temp= new String[2];
		String OverallResult="Pass";
		Boolean Res;
		String Description;
		String Expected;
		String Staff_IH[];
		String StaffIH;
		String Staff_Assoc;
		String ResultStaff="";
		String Destination_IH;
		String Destination_Assoc;
		String ResultDestination="";

		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ Location;
		String CycleEvent="Facility Checkout";
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		String Scope1OFFIH=Scope_IH[0];
		String Scope1OFFAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		System.out.println(Scope1OFFIH+" = Scope into Storage Area ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeInLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		int StaffPK=0;
		int LocalScope1ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
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

		String ExpectedState="1";
		String ExpectedSubLoc="0";
		int OtherScopeStateID=0;

		String ResultScopeOOFCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeOOFCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScopeOOFCycle,OverallResult);

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
		String ResultScopeOOFLoc=IHV.Result_Location(Location, ScopeInLoc,ExpectedSubLoc,ActualSubloc);
		temp=ResultScopeOOFLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOOFLoc, OverallResult);

		String ResultScopeOFFState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		temp=ResultScopeOFFState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOFFState, OverallResult);

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount);
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultOOFScope1="Scope1: ResultScopeOOFCycle= "+ResultScopeOOFCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeOOFLoc="+ResultScopeOOFLoc+". "
				+ "ResultScopeOFFState="+ResultScopeOFFState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		System.out.println(ResultOOFScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultOOFScope1);

		//Scan the destination if a value is passed to the function.
		if(Destination.equalsIgnoreCase("")){
			ResultDestination="No Destination provided for "+Scope1;
			System.out.println(ResultDestination);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Out of Facility Location", "", Destination, "");

			Description="Scan of Destination "+Destination+" is done in "+ Location;
			
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			Destination_IH=Staff_IH[0];
			Destination_Assoc=Staff_IH[1];
			System.out.println(Destination_IH+" = Cabinet ItemHistory_PK");

			ResultDestination=IHV.Result_Same_Assoc(Scope1OFFAssociationID,Destination_Assoc);
			temp=ResultDestination.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultDestination, OverallResult);

			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultDestination);

		}

		//Scan Staff if provided. 
		if(Staff.equalsIgnoreCase("")){
			ResultStaff="No Staff for Out of Facility provided for "+Scope1;
			System.out.println(ResultStaff);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope out of facility";
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			StaffIH=Staff_IH[0];
			Staff_Assoc=Staff_IH[1];
			ResultStaff=IHV.Result_Same_Assoc(Scope1OFFAssociationID,Staff_Assoc)+" for staff and scope out of facility.";
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
		
		//MAM Verification
		Thread.sleep(1000); //Wait 1 sec
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning out of "+Location;
		Expected=Description;

		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION==Out of Facility (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope1ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope1ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);


		System.out.println("OverallResult="+OverallResult);

		OOFResult[0]=Scope1OFFAssociationID;
		OOFResult[1]=OverallResult;
		
		return OOFResult;
	}

	public static String[] CheckIntoFacilityScans (String Location, String Scope1, String Staff,String StaffID, String ExpectedCycleCount)throws InterruptedException{
		String[] CheckInResult = new String[2];
		String [] temp= new String[2];
		String OverallResult="Pass";
		Boolean Res;
		String Description;
		String Expected;
		String Staff_IH[];
		String StaffIH;
		String Staff_Assoc;
		String ResultStaff="";
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' into Facility with scanner "+ Location;
		String CycleEvent="Facility Checkin";
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		String Scope1ChecKinIH=Scope_IH[0];
		String Scope1CheckInAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		System.out.println(Scope1ChecKinIH+" = Scope into Facility ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeCheckinLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		int StaffPK=0;
		int LocalScope1ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
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

		String ExpectedState="5";
		String ExpectedSubLoc="0";
		int OtherScopeStateID=0;
		
		String ResultScopeCheckinCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeCheckinCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScopeCheckinCycle,OverallResult);

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
		String ResultScopeCheckinLoc=IHV.Result_Location(Location, ScopeCheckinLoc, ExpectedSubLoc,ActualSubloc);
		temp=ResultScopeCheckinLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCheckinLoc, OverallResult);

		String ResultScopeCheckinState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		temp=ResultScopeCheckinState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCheckinState, OverallResult);

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount);
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultStorageOutScope1="Scope1: ResultScopeCheckinCycle= "+ResultScopeCheckinCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeCheckinLoc="+ResultScopeCheckinLoc+". "
				+ "ResultScopeCheckinState="+ResultScopeCheckinState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		System.out.println(ResultStorageOutScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStorageOutScope1);

		//Scan Staff if provided. 
		if(Staff.equalsIgnoreCase("")){
			ResultStaff="No Staff for Into Storage Area provided for "+Scope1;
			System.out.println(ResultStaff);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope into facaility";
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			StaffIH=Staff_IH[0];
			Staff_Assoc=Staff_IH[1];
			ResultStaff=IHV.Result_Same_Assoc(Scope1CheckInAssociationID,Staff_Assoc)+" for staff and scope check into facility.";
			temp=ResultStaff.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaff, OverallResult);
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
			
			ResultStaff=ResultStaff+". ResultLastStaff="+ResultLastStaff+".";
			//System.out.println(ResultStaff);
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaff);

		}
		
		//MAM Verification
		Thread.sleep(1000); //Wait 1 sec
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning out of "+Location;
		Expected=Description;

		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION==Returned to Facility (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+Unifia_Admin_Selenium.Scope1ExpectedExamCount+";REPROCESSING COUNT=="
				+Unifia_Admin_Selenium.Scope1ExpectedReproCount).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultScope1_MAM);
		
		System.out.println("OverallResult="+OverallResult);

		CheckInResult[0]=Scope1CheckInAssociationID;
		CheckInResult[1]=OverallResult;
		
		return CheckInResult;
	}
}
