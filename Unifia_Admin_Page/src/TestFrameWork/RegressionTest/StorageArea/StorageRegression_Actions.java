package TestFrameWork.RegressionTest.StorageArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;

public class StorageRegression_Actions extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	static TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.QVDashboard.Dashboard_Verification qvd_v;
	private static Dashboard_Verification DV;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static Regression.CultureHappyPath_CycleMgmt CHP;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;

	public static String[] IntoStorageAreaScans (String Location, String Scope1, String Staff,String StaffID,String Cabinet, String CultureObtained, String ExpectedCycleCount)throws InterruptedException{
		String[] StorageInResult = new String[2];
		String [] temp= new String[2];
		String OverallResult="Pass";
		Boolean Res;
		String Description;
		String Expected;
		String Staff_IH[];
		String StaffIH;
		String Staff_Assoc;
		String ResultStaff="";
		String Cabinet_IH;
		String Cabinet_Assoc;
		String ResultCabinet="";
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

		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ Location;
		String CycleEvent="Cabinet Checkin";
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		String Scope1InIH=Scope_IH[0];
		String Scope1InAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		//System.out.println(Scope1InIH+" = Scope into Storage Area ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeInLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		String ExpectedState="0";
		int OtherScopeStateID;
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

		if(CultureObtained.equalsIgnoreCase("Yes")){
			OtherScopeStateID=7;
		}else {
			OtherScopeStateID=0;
		}

		String ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeInCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScopeInCycle,OverallResult);

		String ResultScopeInLoc=IHV.Result_Location(Location, ScopeInLoc, "0",ActualSubloc);
		temp=ResultScopeInLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);

		String ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		temp=ResultScopeInState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount);
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultScope1In="Scope1: ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". "
				+ "ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		//System.out.println(ResultScope1In);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope1In);

		//Enter the Cabinet number if a value is passed to the function.
		if(Cabinet.equalsIgnoreCase("")){
			ResultCabinet="No Cabinet provided for "+Scope1;
			//System.out.println(ResultCabinet);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Key Entry", "", "", Cabinet);

			Description="Enter Cabinet # of "+Cabinet+" is done in "+ Location;
			
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			Cabinet_IH=Staff_IH[0];
			Cabinet_Assoc=Staff_IH[1];
			//System.out.println(Cabinet_IH+" = Cabinet ItemHistory_PK");

			ResultCabinet=IHV.Result_Same_Assoc(Scope1InAssociationID,Cabinet_Assoc);
			temp=ResultCabinet.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultCabinet, OverallResult);

			String ResultCabinetCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultCabinetCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultCabinetCycle, OverallResult);

			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
			
			ActualScopeState=Scope_IH[0];
			ScopeInLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			ActualCycleCount=Scope_IH[4];
			ResultScopeInLoc=IHV.Result_Location(Location, ScopeInLoc,Cabinet ,ActualSubloc);
			temp=ResultScopeInLoc.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);
			
			ResultCabinet=ResultCabinet+" for "+Scope1+" cabinet of "+Cabinet+". ResultScopeInLoc="+ResultScopeInLoc +". "
					+ "CycleEvent is also correct = "+ResultCabinetCycle;
			//System.out.println(ResultCabinet);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultCabinet);

		}

		//Scan Staff if provided. 
		if(Staff.equalsIgnoreCase("")){
			ResultStaff="No Staff for Into Storage Area provided for "+Scope1;
			//System.out.println(ResultStaff);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope into Storage Area";
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			StaffIH=Staff_IH[0];
			Staff_Assoc=Staff_IH[1];
			ResultStaff=IHV.Result_Same_Assoc(Scope1InAssociationID,Staff_Assoc)+" for staff and scope into Storage.";
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
		
		//Daily Dashboard verification
		if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
			String ScopeCount="0";
			try{
				String stmt="select COUNT(ScopeID_FK) from ScopeStatus where LocationID_FK=(select LocationID_PK from Location where Name='"+Location+"') and SubLocationID="+Cabinet;
				System.out.println(stmt);
				Connection conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
				Statement statement = conn.createStatement();
				ResultSet IH_RS=statement.executeQuery(stmt);
				while(IH_RS.next()){
					ScopeCount=IH_RS.getString(1);
				}
				IH_RS.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Description="Verifying DailyDashboard tab to see whether "+ScopeCount+" is getting displayed on "+Location+"-"+Cabinet;
			Expected=Description;
			String result=DV.verifyDashboard(Location+"-"+Cabinet, "", ScopeCount, dashboardpage.expCabNormalColorGreen, "");
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		String result_MAM=MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
		
		//Thread.sleep(60000); //Wait 1 minute before scanning MC End 
		//System.out.println("OverallResult="+OverallResult);

		StorageInResult[0]=Scope1InAssociationID;
		StorageInResult[1]=OverallResult;
		
		return StorageInResult;
	}

	public static String[] OutOfStorageAreaScans (String Location, String Scope1, String Staff,String StaffID,String CultureResult, String CultureAssocID, String ExpectedCycleCount)throws InterruptedException{
		String[] StorageOutResult = new String[2];
		String [] temp= new String[2];
		String OverallResult="Pass";
		Boolean Res;
		String Description;
		String Expected;
		String Staff_IH[];
		String StaffIH;
		String Staff_Assoc;
		String ResultStaff="";
		String CultureIH;
		String Culture_Assoc;
		String ResultCulture="";
		String Culture_RI;
		int StaffPK=0;
		int LocalScope1ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
		String cultureStatusResult;
		String CultObtained ="Awaiting Results";
		String Scope1RefNo ="";

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
		if(Unifia_Admin_Selenium.ScannerCount==1){
			Thread.sleep(20000);
		}
		Description="Scan of Scope '" +Scope1+"' out of "+ Location;
		String CycleEvent="Cabinet Checkout";
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		String Scope1OutIH=Scope_IH[0];
		String Scope1OutAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		//System.out.println(Scope1OutIH+" = Scope out of Storage Area ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeOutLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		//String ResultCulture;
		String ExpectedState="5";
		String ExpectedSubLoc="0";
		int OtherScopeStateID;
		
		if(CultureResult.equalsIgnoreCase("No")){
			OtherScopeStateID=0;
		}else {
			OtherScopeStateID=7;
		}
		
		String ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeOutCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScopeOutCycle,OverallResult);

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
		String ResultScopeOutLoc=IHV.Result_Location(Location, ScopeOutLoc, ExpectedSubLoc,ActualSubloc);
		temp=ResultScopeOutLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutLoc, OverallResult);

		String ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		temp=ResultScopeOutState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutState, OverallResult);

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount);
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultStorageOutScope1="Scope1: ResultScopeInCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+".  ResultScopeOutLoc="+ResultScopeOutLoc+". "
				+ "ResultScopeOutState="+ResultScopeOutState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		//System.out.println(ResultStorageOutScope1);
		Expected=Description;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStorageOutScope1);

		//Enter the Culture Result if a value is passed to the function.
		
		if(CultureResult.equalsIgnoreCase("Yes")){//Yes means culture was obtained, but the user should not enter a result when scanning scope out of the storage area
			OtherScopeStateID=7;
			//SRM Verification of culture obtained before entering key value 
			String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,CultureAssocID);
			Scope1RefNo=ScopeInfo[0];
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			IP_V.verifyDateFormatINSRMScreen();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(CHP.CultureScanner, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			String cultureStatusAwaitingResult=WF_V.Verify_Culture(CultObtained);
			temp=cultureStatusAwaitingResult.split("-");
			OverallResult=GF.FinalResult(temp[0], cultureStatusAwaitingResult,OverallResult);
			System.out.println("OverallResult="+OverallResult);
			Description="Verify Culture Result of 'Awaiting Results' in SRM Screen for "+Scope1+" into "+Location+". ";
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, cultureStatusAwaitingResult);
			
		}else if(CultureResult.equalsIgnoreCase("Pass")){//enter a culture result of pass
			
			//SRM Verification of culture obtained before entering key value 
			String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,CultureAssocID);
			Scope1RefNo=ScopeInfo[0];
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			IP_V.verifyDateFormatINSRMScreen();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(CHP.CultureScanner, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			//Get Scope info - with cultureObtained
			String cultureStatusAwaitingResult=WF_V.Verify_Culture(CultObtained);
			temp=cultureStatusAwaitingResult.split("-");
			OverallResult=GF.FinalResult(temp[0], cultureStatusAwaitingResult,OverallResult);
			System.out.println("OverallResult="+OverallResult);
			Description="Verify Culture Result of 'Awaiting Results' in SRM Screen for "+Scope1+" into "+Location+". ";
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, cultureStatusAwaitingResult);
			
			OtherScopeStateID=0;
			CycleEvent="Culture Result";

			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Key Entry", "", "", "1");
			//System.out.println(Res);
			Description="Key Entry  option '1' is selected  for scope  "+ Scope1+" to specify culture Result is passed";

			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			CultureIH=Scope_IH[0];
			Culture_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			String ResultCultureResult=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultCultureResult.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultCultureResult,OverallResult);
			System.out.println("OverallResult="+OverallResult);
			String ResultCultureAssoc=IHV.Result_Same_Assoc(CultureAssocID,Culture_Assoc);
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultCultureAssoc);
			
			Culture_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope1OutIH);
			String ResultCulture_RI= IHV.RelatedItem_Verf(CultureIH, Culture_RI);
			//System.out.println(ResultCulture_RI);
			
			ResultCulture="ResultCultureResult="+ResultCultureResult+". ResultCultureAssoc="+ResultCultureAssoc+". ResultCulture_RI="+ResultCulture_RI;
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultCulture_RI);
		
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			IP_V.verifyDateFormatINSRMScreen();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfCompletedFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(CHP.CultureScanner, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			//Get Scope info - with cultureObtained
			cultureStatusResult=WF_V.Verify_Culture(CultureResult);
			temp=cultureStatusResult.split("-");
			OverallResult=GF.FinalResult(temp[0], cultureStatusResult,OverallResult);
			
			Description="Verify Culture Result of 'Awaiting Results' in SRM Screen for "+Scope1+" into "+Location+". ";
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, cultureStatusResult);

			
		}else if(CultureResult.equalsIgnoreCase("Fail")){//enter a culture result of Fail
			
			//SRM Verification of culture obtained before entering key value 
			String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,CultureAssocID);
			Scope1RefNo=ScopeInfo[0];
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			IP_V.verifyDateFormatINSRMScreen();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(CHP.CultureScanner, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			//Get Scope info - with cultureObtained
			String cultureStatusAwaitingResult=WF_V.Verify_Culture(CultObtained);
			temp=cultureStatusAwaitingResult.split("-");
			OverallResult=GF.FinalResult(temp[0], cultureStatusAwaitingResult,OverallResult);
			System.out.println("OverallResult="+OverallResult);
			
			Description="Verify Culture Result of 'Awaiting Results' in SRM Screen for "+Scope1+" into "+Location+". ";
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, cultureStatusAwaitingResult);
			
			
			OtherScopeStateID=0;
			CycleEvent="Culture Result";		
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Key Entry", "", "", "2");
			//System.out.println(Res);
			Description="Key Entry  option '2' is selected  for scope  "+ Scope1+" to specify culture Result is failed";
									
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			CultureIH=Scope_IH[0];
			Culture_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			
			String ResultCultureResult=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultCultureResult.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultCultureResult,OverallResult);

			String ResultCultureAssoc=IHV.Result_Same_Assoc(CultureAssocID,Culture_Assoc);
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultCultureAssoc);
			
			Culture_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope1OutIH);
			String ResultCulture_RI= IHV.RelatedItem_Verf(CultureIH, Culture_RI);
			//System.out.println(ResultCulture_RI);
			
			ResultCulture="ResultCultureResult="+ResultCultureResult+". ResultCultureAssoc="+ResultCultureAssoc+". ResultCulture_RI="+ResultCulture_RI;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultCulture_RI);
			
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			
			IP_V.verifyDateFormatINSRMScreen();
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfCompletedFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(CHP.CultureScanner, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			cultureStatusResult=WF_V.Verify_Culture(CultureResult);
			temp=cultureStatusResult.split("-");
			OverallResult=GF.FinalResult(temp[0], cultureStatusResult,OverallResult);
			
			Description="Verify Culture Result of 'Fail' in SRM Screen for "+Scope1+" into "+Location+". ";
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, cultureStatusResult);
			
		}else if(CultureResult.equalsIgnoreCase("No Results")){//enter a culture result of No results
			
			//SRM Verification of culture obtained before entering key value 
			String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,Scope1,CultureAssocID);
			Scope1RefNo=ScopeInfo[0];
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(CHP.CultureScanner, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			//Get Scope info - with cultureObtained
			String cultureStatusAwaitingResult=WF_V.Verify_Culture(CultObtained);
			temp=cultureStatusAwaitingResult.split("-");
			OverallResult=GF.FinalResult(temp[0], cultureStatusAwaitingResult,OverallResult);
			System.out.println("OverallResult="+OverallResult);
			Description="Verify Culture Result of 'Awaiting Results' in SRM Screen for "+Scope1+" into "+Location+". ";
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, cultureStatusAwaitingResult);
			
			
			OtherScopeStateID=0;
			CycleEvent="Culture Result";

			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Key Entry", "", "", "3");
			//System.out.println(Res);
			Description="Key Entry  option '3' is selected  for scope  "+ Scope1+" to specify No Results for culture test";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			CultureIH=Scope_IH[0];
			Culture_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			String ResultCultureResult=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultCultureResult.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultCultureResult,OverallResult);
			System.out.println("OverallResult="+OverallResult);

			String ResultCultureAssoc=IHV.Result_Same_Assoc(CultureAssocID,Culture_Assoc);
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultCultureAssoc);
			
			Culture_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope1OutIH);
			String ResultCulture_RI= IHV.RelatedItem_Verf(CultureIH, Culture_RI);
			//System.out.println(ResultCulture_RI);
			
			ResultCulture="ResultCultureResult="+ResultCultureResult+". ResultCultureAssoc="+ResultCultureAssoc+". ResultCulture_RI="+ResultCulture_RI;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, ResultCulture_RI);
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			IP_V.verifyDateFormatINSRMScreen();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfCompletedFlow;
			resultChevronColor=WF_V.Verify_ChevronColor(CHP.CultureScanner, Scope1RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+expectedColor;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope1RefNo);
			
			cultureStatusResult=WF_V.Verify_Culture(CultureResult);
			temp=cultureStatusResult.split("-");
			OverallResult=GF.FinalResult(temp[0], cultureStatusResult,OverallResult);
			
			Description="Verify Culture Result of 'No Results' in SRM Screen for "+Scope1+" into "+Location+". ";
			Expected=Description;
			IHV.Exec_Log_Result(XMLFileName, Description, Expected, cultureStatusResult);

		}else if(CultureResult.equalsIgnoreCase("No")){//no culture obtained. 
			OtherScopeStateID=0;
			ResultCulture="Culture not obtained for "+Scope1;
			//System.out.println(ResultCulture);
		}

		//Scan Staff if provided. 
		if(Staff.equalsIgnoreCase("")){
			ResultStaff="No Staff for out of Storage Area provided for "+Scope1;
			//System.out.println(ResultStaff);
		}else {
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope out of Storage Area";
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			StaffIH=Staff_IH[0];
			Staff_Assoc=Staff_IH[1];
			ResultStaff=IHV.Result_Same_Assoc(Scope1OutAssociationID,Staff_Assoc)+" for staff and scope out of Storage.";
			temp=ResultStaff.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaff, OverallResult);
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
			
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, Scope1);
			
			ActualScopeState=Scope_IH[0];
			ScopeOutLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			ActualCycleCount=Scope_IH[4];
			
			String StaffOut_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, Scope1OutIH);
			String ResultStaff_RI= IHV.RelatedItem_Verf(StaffIH, StaffOut_RI);
			//System.out.println(ResultStaff_RI);
			temp=ResultStaff_RI.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaff_RI, OverallResult);

			ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
			temp=ResultScopeOutState.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeOutState, OverallResult);
			
			ResultStaff=ResultStaff+". ResultStaff_RI="+ResultStaff_RI+". ResultScopeOutState="+ResultScopeOutState+". ResultLastStaff="+ResultLastStaff;
			//System.out.println(ResultStaff);
			Expected=Description;
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultStaff);

		}
		
		//Daily Dashboard verification
		if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
			String ScopeCount="0";
			try{
				String stmt="select COUNT(ScopeID_FK) from ScopeStatus where LocationID_FK=(select LocationID_PK from Location where Name='"+Location+"') and SubLocationID="+dashboardpage.Cabinet;
				System.out.println(stmt);
				Connection conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
				Statement statement = conn.createStatement();
				ResultSet IH_RS=statement.executeQuery(stmt);
				while(IH_RS.next()){
					ScopeCount=IH_RS.getString(1);
				}
				IH_RS.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Description="Verifying DailyDashboard tab to see whether "+ScopeCount+" is getting displayed on "+Location+"-"+dashboardpage.Cabinet;
			Expected=Description;
			String result=DV.verifyDashboard(Location+"-"+dashboardpage.Cabinet, "", ScopeCount, dashboardpage.expCabNormalColorGreen, "");
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		//MAM Verification
		Description="Verify Management and Asset Management of "+Scope1+" and Staff "+Staff+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		String result_MAM=MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_MAM);
		
		//System.out.println("OverallResult="+OverallResult);

		StorageOutResult[0]=Scope1OutAssociationID;
		StorageOutResult[1]=OverallResult;
		
		return StorageOutResult;
	}
}
