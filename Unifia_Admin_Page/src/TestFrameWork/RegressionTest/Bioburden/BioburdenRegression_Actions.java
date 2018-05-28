package TestFrameWork.RegressionTest.Bioburden;
import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;

public class BioburdenRegression_Actions extends Unifia_Admin_Selenium {
	
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
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;

	public static String[] BioburdenScans (String Location, String Scope1, String BioStatus, String KeyOrScan, String Optional, String Staff,String StaffID, String ExpectedCycleCount)throws InterruptedException, AWTException{
		String[] BioburdenResult = new String[2];
		String [] temp= new String[2];
		String OverallResult="Pass";
		Boolean Res;
		String Description;
		String Expected;
		String Staff_IH[];
		String StaffIH;
		String Staff_Assoc;
		String ResultStaff="";
		String ResultBioStatus="";
		String ExpectedCabinet="0";
		String BarcodeID="";
		String BarcodeAssoc="";
		String BarcodeValue="";
		String ResultBarcode="";
		String ResultBioStatusAssoc="";
		String ResultStaffCycle="";
		
		String ResultOptional="";
		String OptionalAssoc="";
		String OptionalID="";
		String ResultOptionalAssoc="";
		String ResultKeyEntry="";
		String KeyentryValue="";
		int StaffPK=0;
		int LocalScope1ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
		
		String Result_RefNum1="";
		String Result_ScopeModel1="";
		String Result_ScopeName1="";
		String Result_ScopeSerialNo1="";
		String Changes="No";
		
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
		String CycleEvent="Bioburden Testing"; //The expected Cycle Event 
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		String Scope1InIH=Scope_IH[0]; //the ItemHistoryID_PK for the scope scan.
		String Scope1InAssociationID=Scope_IH[1]; //the associationID for the scope scan
		String ActualCycleEvent=Scope_IH[5]; //the actual cycle event
		System.out.println(Scope1InIH+" = Scope scanned with Bioburden scanner ItemHistory_PK");
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,Scope1InAssociationID);
		String Scope1RefNo=ScopeInfo[0];
		String Scope1SerialNo=ScopeInfo[1];
		String Scope1Model=ScopeInfo[2];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeInLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		
		String ExpectedState="2";
		int OtherScopeStateID=0;		

		String ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);  //verify the actual cycle event matches the expected cycle event
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

		String ResultBioburdenScope1="Scope1: ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". "
				+ "ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		System.out.println(ResultBioburdenScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultBioburdenScope1);
		
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		// Verify Date format in SRM table in workFlowStart Date Column
		IP_V.verifyDateFormatINSRMScreen();
		
		//Verifying Chevron Color
		expectedColor=DBP.rgbOfIncompleteFlow;
		resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen for "+Location;
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

		System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);
		
		
		String Result_BioResult=WF_V.Verify_BioResult("");
		System.out.println("Result_BioResult=:"+Result_BioResult);
		temp=Result_BioResult.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioResult,OverallResult);

		String Result_BioScanValue=WF_V.Verify_BioScanValue("");
		System.out.println("Result_BioScanValue=:"+Result_BioScanValue);
		temp=Result_BioScanValue.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioScanValue,OverallResult);
		
		String Result_BioKeyValue=WF_V.Verify_BioKeyValue("");
		System.out.println("Result_BioKeyValue=:"+Result_BioKeyValue);
		temp=Result_BioKeyValue.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioKeyValue,OverallResult);

		String Result_BioStaff=WF_V.Verify_BioStaff("");
		System.out.println("Result_BioStaff=:"+Result_BioStaff);
		temp=Result_BioStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioStaff,OverallResult);

		WF_A.Cancel(Changes);
		
		Expected=Description;
		String result_SRM_Scope_Bioburden="Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1
				+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_BioResult="+Result_BioResult
				+". Result_BioScanValue="+Result_BioScanValue+". Result_BioKeyValue"+Result_BioKeyValue+". Result_BioStaff="+Result_BioStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_SRM_Scope_Bioburden);
		
		//scan Bioburden Pass or Fail 
		if(BioStatus.equalsIgnoreCase("")){
			ResultBioStatus="Bioburden Pass/Fail test status was not provided.";
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res=EM_A.ScanItem(Location, "Bioburden Testing", "", BioStatus, "");
			Description="Bioburden test Status "+BioStatus+" Scanned";
			CycleEvent="Bioburden Pass/Fail";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			BarcodeID=Scope_IH[0];
			BarcodeAssoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			if(BioStatus.equalsIgnoreCase("Pass")){
				ExpectedState="2";				
			}else if(BioStatus.equalsIgnoreCase("Fail")){
				//if Bioburden fail is scanned, the scope is set to awaiting manual cleaning instead of awaiting reprocessing
				ExpectedState="3";
			}
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
			
			ActualScopeState=Scope_IH[0];
			ScopeInLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			ActualCycleCount=Scope_IH[4];

			ResultBioStatus=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultScopeInCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeInCycle, OverallResult);

			ResultBioStatusAssoc=IHV.Result_Same_Assoc(Scope1InAssociationID, BarcodeAssoc);
			temp=ResultBioStatusAssoc.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultBioStatusAssoc, OverallResult);

			BarcodeValue=IHV.GetBarcodeValue(Unifia_Admin_Selenium.connstring, BarcodeID);
			ResultBarcode=IHV.Result_Same_Barcode(BarcodeValue, BioStatus);
			temp=ResultBarcode.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultBarcode, OverallResult);

			ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState); //verify the actual ScopeStateID and OtherScopeStateID matches the expected ScopeStateID and OtherScopeStateID
			temp=ResultScopeInState.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

			
			ResultBioStatus="Bioburden Status Cycle Event= "+ResultBioStatus+". ResultBioStatusAssoc="+ResultBioStatusAssoc+". ResultBarcode="+ResultBarcode+". "
					+ "ResultScopeInState="+ResultScopeInState+". Barcode Value="+BarcodeValue;
			System.out.println(ResultBioStatus);
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultBioStatus);

		}
		
		if(KeyOrScan.equalsIgnoreCase("")||Optional.equalsIgnoreCase("")){
			ResultOptional="Optional Test Resutl not provided. ";
		}else if(KeyOrScan.equalsIgnoreCase("Scan")){
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			EM_A.ScanItem(Location, "Bioburden Testing", "", Optional, "");
			
			Description="Bioburden Scan Result "+Optional+" Scanned";
			CycleEvent="Bioburden Result";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			OptionalID=Scope_IH[0];
			OptionalAssoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			ResultOptional=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultOptional.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultOptional, OverallResult);

			ResultOptionalAssoc=IHV.Result_Same_Assoc(Scope1InAssociationID, OptionalAssoc);
			temp=ResultOptionalAssoc.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultOptionalAssoc, OverallResult);

			BarcodeValue=IHV.GetBarcodeValue(Unifia_Admin_Selenium.connstring, OptionalID);
			ResultBarcode=IHV.Result_Same_Barcode(BarcodeValue, Optional);
			temp=ResultBarcode.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultBarcode, OverallResult);
			
			ResultOptional="ResultOptional Cycle Event="+ResultOptional+". ResultOptionalAssoc="+ResultOptionalAssoc+" Result Barcode:"+ResultBarcode+". Barcode Value="+BarcodeValue;
			System.out.println(ResultOptional);
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultOptional);

			
		}else if(KeyOrScan.equalsIgnoreCase("Key Entry")){
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Key Entry", "", "", Optional);

			Description="Bioburden Scan Result "+Optional+" Scanned";
			CycleEvent="Bioburden Result";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			OptionalID=Scope_IH[0];
			OptionalAssoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			ResultOptional=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultOptional.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultOptional, OverallResult);

			ResultOptionalAssoc=IHV.Result_Same_Assoc(Scope1InAssociationID, OptionalAssoc);
			temp=ResultOptionalAssoc.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultOptionalAssoc, OverallResult);

			KeyentryValue=IHV.GetKeyEntryValue(Unifia_Admin_Selenium.connstring, OptionalID);
			ResultKeyEntry=IHV.Result_Same_KeyEntry(KeyentryValue, Optional);
			temp=ResultKeyEntry.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultKeyEntry, OverallResult);
			
			ResultOptional="ResultOptional Cycle Event="+ResultOptional+". ResultOptionalAssoc="+ResultOptionalAssoc+" Result Key Entry:"+ResultKeyEntry+". Key Entry Value="+KeyentryValue;
			System.out.println(ResultOptional);
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultOptional);

		}
			
			
		//Scan Staff if provided. 
		if(Staff.equalsIgnoreCase("")){
			ResultStaff="No Staff provided for bioburden for "+Scope1;
			System.out.println(ResultStaff);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope Bioburden Test";
			//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			CycleEvent="BioBurdenStaff";
			Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			StaffIH=Staff_IH[0];
			Staff_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			
			ResultStaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultStaffCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultStaffCycle,OverallResult);

			ResultStaff=IHV.Result_Same_Assoc(Scope1InAssociationID,Staff_Assoc)+" for staff and scope Bioburden test.";
			temp=ResultStaff.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaff, OverallResult);

			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
			
			ResultStaff=ResultStaff+" ResultStaffCycle="+ResultStaffCycle+". ResultLastStaff="+ResultLastStaff+".";
			//System.out.println(ResultStaff);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaff);

		}
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		//Verifying Chevron Color
		expectedColor=DBP.rgbOfCompletedFlow;
		resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen for "+Location;
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

		System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);
		
		
		Result_BioResult=WF_V.Verify_BioResult(BioStatus);
		System.out.println("Result_BioResult=:"+Result_BioResult);
		temp=Result_BioResult.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioResult,OverallResult);

		if(KeyOrScan.equalsIgnoreCase("Scan")){
			Result_BioScanValue=WF_V.Verify_BioScanValue(Optional);
			System.out.println("Result_BioScanValue=:"+Result_BioScanValue);
			temp=Result_BioScanValue.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_BioScanValue,OverallResult);
			
			Result_BioKeyValue=WF_V.Verify_BioKeyValue("");
			System.out.println("Result_BioKeyValue=:"+Result_BioKeyValue);
			temp=Result_BioKeyValue.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_BioKeyValue,OverallResult);
		}else{
			Result_BioScanValue=WF_V.Verify_BioScanValue("");
			System.out.println("Result_BioScanValue=:"+Result_BioScanValue);
			temp=Result_BioScanValue.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_BioScanValue,OverallResult);
			
			Result_BioKeyValue=WF_V.Verify_BioKeyValue(Optional);
			System.out.println("Result_BioKeyValue=:"+Result_BioKeyValue);
			temp=Result_BioKeyValue.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_BioKeyValue,OverallResult);
		}

		Result_BioStaff=WF_V.Verify_BioStaff(StaffID);
		System.out.println("Result_BioStaff=:"+Result_BioStaff);
		temp=Result_BioStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioStaff,OverallResult);
		
		WF_A.Cancel(Changes);
		
		Expected=Description;
		result_SRM_Scope_Bioburden="Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1
				+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_BioResult="+Result_BioResult
				+". Result_BioScanValue="+Result_BioScanValue+". Result_BioKeyValue"+Result_BioKeyValue+". Result_BioStaff="+Result_BioStaff;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_SRM_Scope_Bioburden);
		
		//Verify MAM details
		Description="Verify Management and Asset Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		String result_MAM=MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
		
		//Thread.sleep(60000); //Wait 1 minute before scanning MC End 
		
		System.out.println("OverallResult="+OverallResult);
		
		BioburdenResult[0]=Scope1InAssociationID;
		BioburdenResult[1]=OverallResult;
		
		return BioburdenResult;
	}


}
