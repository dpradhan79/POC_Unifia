package TestFrameWork.RegressionTest.ProcedureRoom;
import java.awt.AWTException;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class PR_Regression_Actions extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	static TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	private static TestFrameWork.QVDashboard.Dashboard_Verification qvd_v;
	private static Dashboard_Verification DV;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_Admin_Selenium UAS;
	public static Connection conn= null;
    public static String stmt1;

	public static String[] PR_Scans (String Location, String Scope1, String Scope2, String Staff,String StaffID, String Phy1,String Phy2, String Admit, String Patient, String Start, String End, String Culture1, int CultureAssociationID1, String Culture2, int CultureAssociationID2, String ExpectedCycleCount)throws InterruptedException, AWTException{
		String[] PRResult = new String[2];
		String [] temp= new String[2];
		String [] phy_split= new String[2];
		String [] phy_split1= new String[2];
		String OverallResult="Pass";
		Boolean Res;
		String Description;
		String Expected;
		String Staff_IH[];
		String StaffInIH;
		String StaffIn_Assoc="";
		String ResultStaffIn1="NA";
		String StaffIn_RI1;
		String ResultIn_RI1="";
		String ResultScopeIn1StaffCycle="";
		String ResultScopeIn2StaffCycle="";
		
		String ResultScope2Association;
		String StaffIn_RI2;
		String ResultIn_RI2;
		
		String ResultCulture1="";
		String CultureIH;
		String CultureResult_Assoc1="";
		String Culture_RI1;
		String ResultCulture_RI1;
		String CultureResult_Assoc2;
		String Culture_RI2;
		String ResultCulture_RI2;
		String ResultCulture2="";
		
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
		String ResultScopeOut1StaffCycle="";
		String ResultScopeOut2StaffCycle="";

		String RoomStatus_IH;
		String RoomStatus_Assoc;
		String ResultRoomStatusCycle;
		String ResultRoomStatus;
		String ResultScopeInState2;
		
		int LocalScope1ExpectedRepro=0;
		int LocalScope1ExpectedExamCount=0;
		int LocalScope2ExpectedRepro=0;
		int LocalScope2ExpectedExamCount=0;
		
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
		String Result_Scope1Preclean="";
		String Result_Scope2Preclean="";
		String Result_Scope1StaffPreclean="";
		String Result_Scope2StaffPreclean="";
		String Result_Scope1Phy="";
		String Result_Scope2Phy="";
		String Result_Scope1Patient="";
		String Result_Scope2Patient="";
		
		String PhyID1="";
		String PhyID2="";
		String nicole="";
		if(!Phy1.equalsIgnoreCase("")){
			phy_split=Phy1.split("\\(");
			nicole=phy_split[1];
			phy_split1=nicole.split("\\)");
			PhyID1=phy_split1[0];			
		}
		if(!Phy2.equalsIgnoreCase("")){
			phy_split=Phy2.split("\\(");
			nicole=phy_split[1];
			phy_split1=nicole.split("\\)");
			PhyID2=phy_split1[0];			
		}

		String Changes="No";
		
		String expectedColor=""; 
		String resultChevronColor="";
		String ChevronColor;
		int NumPhy=0;
		if(!Phy1.equalsIgnoreCase("")){
			NumPhy++;
		}
		if(!Phy2.equalsIgnoreCase("")){
			NumPhy++;
		}

		if(Scope1.equalsIgnoreCase("Scope1")){
			LocalScope1ExpectedRepro=UAS.Scope1ExpectedReproCount;
			LocalScope1ExpectedExamCount=UAS.Scope1ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope2")){
			LocalScope1ExpectedRepro=UAS.Scope2ExpectedReproCount;
			LocalScope1ExpectedExamCount=UAS.Scope2ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope3")){
			LocalScope1ExpectedRepro=UAS.Scope3ExpectedReproCount;
			LocalScope1ExpectedExamCount=UAS.Scope3ExpectedExamCount;
		}else if(Scope1.equalsIgnoreCase("Scope4")){
			LocalScope1ExpectedRepro=UAS.Scope4ExpectedReproCount;
			LocalScope1ExpectedExamCount=UAS.Scope4ExpectedExamCount;
		}
		
		UAS.ScannerCount=UAS.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ Location;
		String CycleEvent="Pre-Procedure";
		String Scope_IH[]=IHV.GetItemHistoryData(UAS.connstring, Location);
		String Scope1InIH=Scope_IH[0];
		String Scope1InAssociationID=Scope_IH[1];
		String ActualCycleEvent=Scope_IH[5];
		//System.out.println(Scope1InIH+" = Scope "+Scope1+" into PR ItemHistory_PK");
		
		String ScopeInfo[]=IHV.GetScopeInfo(UAS.connstring,Scope1,Scope1InAssociationID);
		String Scope1RefNo=ScopeInfo[0];
		String Scope1SerialNo=ScopeInfo[1];
		String Scope1Model=ScopeInfo[2];
		Scope1ExamTime=ScopeInfo[3];

		Scope_IH=IHV.Scp_State_Loc(UAS.connstring, Scope1);
		
		String ActualScopeState=Scope_IH[0];
		String ScopeInLoc=Scope_IH[1];
		String ActualOtherScopeState=Scope_IH[2];
		String ActualSubloc=Scope_IH[3];
		String ActualCycleCount=Scope_IH[4];
		String ActualReproCount=Scope_IH[5];
		String ActualExamCount=Scope_IH[6];
		String ExpectedState="0";
		int OtherScopeStateID1;
		int OtherScopeStateID2;
		String ExpectedCabinet="0";
		String CultureResult="";
		int StaffPK=0;
		int LastScanStaffID_FK=IHV.Scp_State_LastStaffID(UAS.connstring, Scope1);
		if(Culture1.equalsIgnoreCase("No")||Culture1.equalsIgnoreCase("NA")||Culture1.equalsIgnoreCase("")){
			OtherScopeStateID1=0;
		}else {
			OtherScopeStateID1=7;
		}
		
		if(Culture2.equalsIgnoreCase("No")||Culture2.equalsIgnoreCase("NA")||Culture2.equalsIgnoreCase("")){
			OtherScopeStateID2=0;
		}else {
			OtherScopeStateID2=7;
		}

		String ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeInCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScopeInCycle,OverallResult);
		
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

		String ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID1),ActualOtherScopeState);
		temp=ResultScopeInState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

		String ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount);
		temp=ResultScopeCycleCount.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

		String ResultPRInScope1=Scope1+": ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". "
				+ "ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
		//System.out.println(ResultPRInScope1);
		Expected=Description;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPRInScope1);

		if(Culture1.equalsIgnoreCase("NA")||Culture1.equalsIgnoreCase("No")){
			ResultCulture1="Culture results are not applicable.";
		} else { 
			
			if(Culture1.equalsIgnoreCase("Pass")){
				CultureResult="Pass";
				ResultCulture1="Culture result of Pass entered";
				UAS.ScannerCount=UAS.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Key Entry", "", "", "1");
				Description="Key Entry  option '1' is selected  for scope  "+ Scope1+" to specify culture Result is passed";
			} else if(Culture1.equalsIgnoreCase("Fail")){
				CultureResult="Fail";
				ResultCulture1="Culture result of Failed entered";
				UAS.ScannerCount=UAS.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Key Entry", "", "", "2");
				Description="Key Entry  option '2' is selected  for scope  "+ Scope1+" to specify culture Result is failed";				
			}else if(Culture1.equalsIgnoreCase("NoResults")){
				CultureResult="No Results";
				ResultCulture1="No Culture Results entered";
				UAS.ScannerCount=UAS.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Key Entry", "", "", "3");
				Description="Key Entry  option '3' is selected  for scope  "+ Scope1+" to specify culture Result of No Results";				
			}
			
			Scope_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
			CultureIH=Scope_IH[0];
			CultureResult_Assoc1=Scope_IH[1];
			String ResultCulturePR=IHV.Result_Different_Assoc(Scope1InAssociationID, CultureResult_Assoc1)+" "
					+ " for Culture Result and "+Scope1+" in the procedure room. ";
			temp=ResultCulturePR.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultCulturePR, OverallResult);

			ResultCulture1=IHV.Result_Same_Assoc(Integer.toString(CultureAssociationID1),CultureResult_Assoc1)+" for culture obtained"
					+ " and culture result of "+Culture1+". ";
			temp=ResultCulture1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultCulture1, OverallResult);
			ResultCulture1=ResultCulture1+ResultCulturePR;
			//System.out.println(ResultCulture1);
			
			
			Culture_RI1=IHV.GetRelatedITem_IHKey(UAS.connstring, Scope1InIH);
			ResultCulture_RI1= IHV.RelatedItem_Verf(CultureIH, Culture_RI1);
			temp=ResultCulture_RI1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultCulture_RI1, OverallResult);
			//System.out.println(ResultCulture_RI1);
			ResultCulture1="ResultCulture1: "+ResultCulture1+" ResultCulture_RI1:"+ResultCulture_RI1;
			//System.out.println(ResultCulture1);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultCulture1);
			OtherScopeStateID1=0;//NM 4/10/2017 once the culture result is provided, OtherScopeState is set to 0. (change as part of 2.3)
			
			String ScopeInfoCult[]=IHV.GetScopeInfo(UAS.connstring,Scope1,Integer.toString(CultureAssociationID1));
			String Scope1RefNoCult=ScopeInfoCult[0];
			
			//SRM Verification for Culture
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfCompletedFlow;
			ChevronColor=DBP.Green;

			resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNoCult,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+ChevronColor;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_V.verifyDateFormatINSRMScreen();
			
			IP_A.Click_Details(Scope1RefNoCult);
			
			String CultResultScope1 = WF_V.Verify_Culture(CultureResult);
			temp=CultResultScope1.split("-");
			OverallResult=GF.FinalResult(temp[0], CultResultScope1,OverallResult);
			System.out.println(OverallResult);
			Description="Verifying Culture Result for Scope ="+Scope1+"in location ="+Location;
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, CultResultScope1);
		}

		if(Staff.equalsIgnoreCase("")){
			ResultStaffIn1="No Staff for procedure room provided.";
			//System.out.println(ResultStaffIn1);
		}else {
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for "+Scope1+" into Procedure Room";
			
			//Staff_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
			CycleEvent="PreProcedureStaff";
			Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);			
			StaffInIH=Staff_IH[0];
			StaffIn_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			
			ResultScopeIn1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultScopeIn1StaffCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScopeIn1StaffCycle,OverallResult);
			
			ResultStaffIn1=IHV.Result_Same_Assoc(Scope1InAssociationID,StaffIn_Assoc)+" for staff and "+Scope1+" into PR.";
			temp=ResultStaffIn1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaffIn1, OverallResult);

			StaffPK=IHV.GetStaffPK(UAS.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(UAS.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);

			ResultStaffIn1=ResultStaffIn1+" ResultLastStaff="+ResultLastStaff+".";
			
			//System.out.println(ResultStaffIn1);
			StaffIn_RI1=IHV.GetRelatedITem_IHKey(UAS.connstring, Scope1InIH);
			ResultIn_RI1= IHV.RelatedItem_Verf(StaffInIH, StaffIn_RI1);
			temp=ResultIn_RI1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultIn_RI1, OverallResult);

			//System.out.println(ResultIn_RI1);
			ResultStaffIn1="ResultStaffIn1 for associations:"+ResultStaffIn1+". ResultScopeIn1StaffCycle="+ResultScopeIn1StaffCycle+". ResultIn_RI1="+ResultIn_RI1+". ResultLastStaff="+ResultLastStaff+".";
			
			//System.out.println(ResultStaffIn1);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultStaffIn1);

		}
		
		//DailyDashboard Verification
		if (UAS.IsHappyPath || UAS.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether "+Scope1+" is getting displayed in "+Location;
			Expected=Description;
			String result=DV.verifyDashboard(Location, Scope1, "", dashboardpage.expPRAvailableColor, dashboardpage.expPRAvailable);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, result);
		}

		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		//Verifying Chevron Color
		expectedColor=DBP.rgbOfIncompleteFlow;
		ChevronColor=DBP.Yellow;
		resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen";
		Expected="The Chevron color should be "+ChevronColor;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultChevronColor);
		
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

		String Result_Scope1PR=WF_V.Verify_PR(Location);
		System.out.println("Result_Scope1PR=:"+Result_Scope1PR);
		temp=Result_Scope1PR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1PR,OverallResult);
		
		String Result_Scope1ExamDate=WF_V.Verify_ExamDate(Scope1ExamTime);
		if(Result_Scope1ExamDate.contains("#Fail")){
			Result_Scope1ExamDate=Result_Scope1ExamDate;
		}
		System.out.println("Result_Scope1ExamDate=:"+Result_Scope1ExamDate);
		temp=Result_Scope1ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ExamDate,OverallResult);

		String Result_Scope1InStaff=WF_V.Verify_PRInStaff(StaffID);
		System.out.println("Result_Scope1InStaff=:"+Result_Scope1InStaff);
		temp=Result_Scope1InStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1InStaff,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope1_PRIn="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1PR="+Result_Scope1PR+". Result_Scope1ExamDate="+Result_Scope1ExamDate
				+". Result_Scope1InStaff="+Result_Scope1InStaff+".";
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_SRM_Scope1_PRIn);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning into "+Location;
		Expected=Description;

		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultScope1_MAM);
		
		//Scan scope 2 and the staff that brought scope 2 into PR if specified
		if(Scope2.equalsIgnoreCase("")){
			ResultPRInScope2="Scope2 not passed into the function.";
		} else {
			
			if(Scope2.equalsIgnoreCase("Scope1")){
				LocalScope2ExpectedRepro=UAS.Scope1ExpectedReproCount;
				LocalScope2ExpectedExamCount=UAS.Scope1ExpectedExamCount;
			}else if(Scope2.equalsIgnoreCase("Scope2")){
				LocalScope2ExpectedRepro=UAS.Scope2ExpectedReproCount;
				LocalScope2ExpectedExamCount=UAS.Scope2ExpectedExamCount;
			}else if(Scope2.equalsIgnoreCase("Scope3")){
				LocalScope2ExpectedRepro=UAS.Scope3ExpectedReproCount;
				LocalScope2ExpectedExamCount=UAS.Scope3ExpectedExamCount;
			}else if(Scope2.equalsIgnoreCase("Scope4")){
				LocalScope2ExpectedRepro=UAS.Scope4ExpectedReproCount;
				LocalScope2ExpectedExamCount=UAS.Scope4ExpectedExamCount;
			}

			
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Scope", "", Scope2, "");
			Description="Scan of Scope '" +Scope2+"' is done in "+ Location;
			CycleEvent="Pre-Procedure";
			Scope_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
			String Scope2InIH=Scope_IH[0];
			Scope2InAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(Scope2InIH+" = Scope into PR ItemHistory_PK");
			Scope_IH=IHV.Scp_State_Loc(UAS.connstring, Scope2);
			
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
			
			ScopeInfo=IHV.GetScopeInfo(UAS.connstring,Scope2,Scope2InAssociationID);
			Scope2RefNo=ScopeInfo[0];
			Scope2SerialNo=ScopeInfo[1];
			Scope2Model=ScopeInfo[2];
			Scope2ExamTime=ScopeInfo[3];

			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(UAS.connstring, Scope2);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
			ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(LocalScope2ExpectedRepro));
			temp=ResultReproCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
			
			ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(LocalScope2ExpectedExamCount));
			temp=ResultExamCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);
			
			ResultScope2Association=IHV.Result_Same_Assoc(Scope1InAssociationID,Scope2InAssociationID)+" for "+Scope2+" and "+Scope1+" into PR.";
			temp=ResultScope2Association.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScope2Association, OverallResult);

			ResultScopeInLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc);
			temp=ResultScopeInLoc.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeInLoc, OverallResult);

			ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID2),ActualOtherScopeState);
			temp=ResultScopeInState.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);

			ResultScopeCycleCount=IHV.Result_CycleCount(ActualCycleCount, ExpectedCycleCount);
			temp=ResultScopeCycleCount.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeCycleCount, OverallResult);

			ResultPRInScope2=Scope2+": ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScope2Association="+ResultScope2Association+". "
					+ "ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState+". ResultScopeCycleCount="+ResultScopeCycleCount;
			//System.out.println(ResultPRInScope2);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPRInScope2);
			
			if(Culture2.equalsIgnoreCase("NA")||Culture2.equalsIgnoreCase("No")||Culture2.equalsIgnoreCase("")){
				ResultCulture2="Culture results are not applicable";
			} else { 
				if(Culture2.equalsIgnoreCase("Pass")){
					ResultCulture2="Culture result of Pass entered";
					UAS.ScannerCount=UAS.ScannerCount+1;
					Res = EM_A.ScanItem(Location, "Key Entry", "", "", "1");
					Description="Key Entry  option '1' is selected  for scope  "+ Scope2+" to specify culture Result is passed";
				} else if(Culture2.equalsIgnoreCase("Fail")){
					ResultCulture2="Culture result of Failed entered";
					UAS.ScannerCount=UAS.ScannerCount+1;
					Res = EM_A.ScanItem(Location, "Key Entry", "", "", "2");
					Description="Key Entry  option '2' is selected  for scope  "+ Scope2+" to specify culture Result is failed";				
				}else if(Culture2.equalsIgnoreCase("NoResults")){
					ResultCulture2="Culture result of No results entered";
					UAS.ScannerCount=UAS.ScannerCount+1;
					Res = EM_A.ScanItem(Location, "Key Entry", "", "", "3");
					Description="Key Entry  option '3' is selected  for scope  "+ Scope2+" to specify culture Result of No Results";				
				}
				Scope_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
				CultureIH=Scope_IH[0];
				CultureResult_Assoc2=Scope_IH[1];

				String ResultCulturePR=IHV.Result_Different_Assoc(Scope2InAssociationID, CultureResult_Assoc2)+" "
						+ " for Culture Result and "+Scope2+" in the procedure room. ";
				temp=ResultCulturePR.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultCulturePR, OverallResult);

				ResultCulture2=IHV.Result_Same_Assoc(Integer.toString(CultureAssociationID2),CultureResult_Assoc2)+" for culture obtained"
						+ " and culture result of "+Culture2+". ";
				temp=ResultCulture2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultCulture2, OverallResult);
				//System.out.println(ResultCulture2);
				
				Culture_RI2=IHV.GetRelatedITem_IHKey(UAS.connstring, Scope2InIH);
				ResultCulture_RI2= IHV.RelatedItem_Verf(CultureIH, Culture_RI2);
				temp=ResultCulture_RI2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultCulture_RI2, OverallResult);
				//System.out.println(ResultCulture_RI2);
				ResultCulture2="ResultCulture1: "+ResultCulture2+" ResultCulture_RI2:"+ResultCulture_RI2;
				Expected=Description;
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultCulture2);
				
				
				String ScopeInfoCult2[]=IHV.GetScopeInfo(UAS.connstring,Scope2,Integer.toString(CultureAssociationID2));
				String Scope2RefNoCult2=ScopeInfoCult2[0];
				
				
				//SRM Verification for Culture
				Thread.sleep(1000); //Wait 1 sec
				IP_A.Click_InfectionPrevention();
				IP_A.Click_SRM();
				
				//Verifying Chevron Color
				expectedColor=DBP.rgbOfCompletedFlow;
				ChevronColor=DBP.Green;
				resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope2RefNoCult2,expectedColor);
				Description="Checking the Chevron Color on SRM Screen";
				Expected="The Chevron color should be "+ChevronColor;
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultChevronColor);
				
				IP_A.Click_Details(Scope2RefNoCult2);
				
				String CultResultScope2 = WF_V.Verify_Culture(CultureResult);
				temp=CultResultScope2.split("-");
				OverallResult=GF.FinalResult(temp[0], CultResultScope2,OverallResult);
				Description="Verifying Culture Result for Scope ="+Scope2+"in location ="+Location;
				Expected=Description;
				System.out.println(OverallResult);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, CultResultScope2);

			}

			if(Staff.equalsIgnoreCase("")){
				ResultStaffIn2="No Staff for procedure room provided";
				//System.out.println(ResultStaffIn2);
			}else {
				UAS.ScannerCount=UAS.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
				Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope into "+Location;
				//Staff_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
				CycleEvent="PreProcedureStaff";
				Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);			
				StaffInIH=Staff_IH[0];
				StaffIn_Assoc=Staff_IH[1];
				ActualCycleEvent=Staff_IH[5];
				
				ResultScopeIn2StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
				temp=ResultScopeIn2StaffCycle.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultScopeIn2StaffCycle,OverallResult);

				ResultStaffIn2=IHV.Result_Same_Assoc(Scope2InAssociationID,StaffIn_Assoc)+" for staff and "+Scope2+" into PR.";
				temp=ResultStaffIn2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultStaffIn2, OverallResult);

				StaffPK=IHV.GetStaffPK(UAS.connstring,StaffID);
				LastScanStaffID_FK=IHV.Scp_State_LastStaffID(UAS.connstring, Scope2);
				ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
				temp=ResultLastStaff.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);

				//System.out.println(ResultStaffIn2);
				StaffIn_RI2=IHV.GetRelatedITem_IHKey(UAS.connstring, Scope2InIH);
				ResultIn_RI2= IHV.RelatedItem_Verf(StaffInIH, StaffIn_RI2);
				temp=ResultIn_RI2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultIn_RI2, OverallResult);
				//System.out.println(ResultIn_RI2);
				ResultStaffIn2="ResultStaffIn2 for associations:"+ResultStaffIn2+". ResultScopeIn2StaffCycle="+ResultScopeIn2StaffCycle+". ResultIn_RI2="+ResultIn_RI2+". ResultLastStaff="+ResultLastStaff+".";
				//System.out.println(ResultStaffIn2);
				Expected=Description;
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultStaffIn2);
			}
			
			//DailyDashboard Verification
			if (UAS.IsHappyPath || UAS.IsKEHappyPath){
				Description="Verifying DailyDashboard tab to see whether "+Scope1+" "+Scope2+" is getting displayed in "+Location;
				Expected=Description;
				String result=DV.verifyDashboard(Location, Scope1+"\n"+Scope2, "", dashboardpage.expPRAvailableColor, dashboardpage.expPRAvailable);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, result);
			}
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			//Verifying Chevron Color
			expectedColor=DBP.rgbOfIncompleteFlow;
			ChevronColor=DBP.Yellow;
			resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope2RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+ChevronColor;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope2RefNo);
			
			Description="Verify Scope Record Management of "+Scope2+" and Staff "+Staff+" into "+Location+". ";

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

			Result_Scope2PR=WF_V.Verify_PR(Location);
			System.out.println("Result_Scope2PR=:"+Result_Scope2PR);
			temp=Result_Scope2PR.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2PR,OverallResult);

			Result_Scope2ExamDate=WF_V.Verify_ExamDate(Scope2ExamTime);
			if(Result_Scope2ExamDate.contains("#Fail")){
				Result_Scope2ExamDate=Result_Scope2ExamDate;
			}
			System.out.println("Result_Scope2ExamDate=:"+Result_Scope2ExamDate);
			temp=Result_Scope2ExamDate.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ExamDate,OverallResult);

			Result_Scope2InStaff=WF_V.Verify_PRInStaff(StaffID);
			System.out.println("Result_Scope2InStaff=:"+Result_Scope2InStaff);
			temp=Result_Scope2InStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2InStaff,OverallResult);

			WF_A.Cancel(Changes);
			Expected=Description;
			String Result_SRM_Scope2_PRIn="Result_RefNum2="+Result_RefNum2+". Result_ScopeModel2="+Result_ScopeModel2+". Result_ScopeName2="+Result_ScopeName2+". "
					+ "Result_ScopeSerialNo2="+Result_ScopeSerialNo2+". Result_Scope2PR="+Result_Scope2PR+". Result_Scope2ExamDate="+Result_Scope2ExamDate
					+". Result_Scope2InStaff="+Result_Scope2InStaff;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_SRM_Scope2_PRIn);
			
			MAM_A.Click_MaterialsAndAssetManagement();
			Description="Verify MAM screen for "+Scope2+" after scanning into "+Location;
			Expected=Description;

			String resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
					+";EXAM COUNT=="+LocalScope2ExpectedExamCount+";REPROCESSING COUNT=="
					+LocalScope2ExpectedRepro).toString();
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultScope2_MAM);

		}
				
		//Scan the Admit Patient barcode if specified
		if(Admit.equalsIgnoreCase("Yes")){
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Workflow Event", "", "Patient/Procedure Status", "");
			//System.out.println(Res);
			Description="Scan of Patient/Procedure Status is done in "+ Location;

			Staff_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
			AdmitPatient_IH=Staff_IH[0];
			AdmitPatient_Assoc=Staff_IH[1];
			ResultAdmitPatient=IHV.Result_Same_Assoc(Scope1InAssociationID,AdmitPatient_Assoc)+" for Admit patient barcode scan and scope into PR.";
			temp=ResultAdmitPatient.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultAdmitPatient, OverallResult);

			//System.out.println(ResultAdmitPatient);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultAdmitPatient);


		}else {
			ResultAdmitPatient="Admit Patient barcode not scanned";
			//System.out.println(ResultAdmitPatient);
		}
		
		//Scan Patient if provided.
		if(Patient.equalsIgnoreCase("")){
			ResultPatient="Patient not provided";
		}else {
			LocalScope1ExpectedExamCount++;
			if(Scope1.equalsIgnoreCase("Scope1")){
				UAS.Scope1ExpectedExamCount=LocalScope1ExpectedExamCount;
			}else if(Scope1.equalsIgnoreCase("Scope2")){
				UAS.Scope2ExpectedExamCount=LocalScope1ExpectedExamCount;
			}else if(Scope1.equalsIgnoreCase("Scope3")){
				UAS.Scope3ExpectedExamCount=LocalScope1ExpectedExamCount;
			}else if(Scope1.equalsIgnoreCase("Scope4")){
				UAS.Scope4ExpectedExamCount=LocalScope1ExpectedExamCount;
			}

			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Patient", "", Patient, "");
			Description="Scan of Patient '" +Patient+"' is done in "+ Location;

			Expected="Patient scanned into PR";
			
			if(UAS.PhyScannedInWaiting==true){
				Description="Scan of Patient '" +Patient+"' is done in "+ Location+" and the Physician(s) associated with the Patient in the Waiting Room "
						+ "were automatically added to the Procedure Room Association. ";

				CycleEvent="Physician";
				for(int i=0;i<NumPhy;i++){
					Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
					Phy_IH=Staff_IH[0];
					Phy_Assoc=Staff_IH[1];
					ActualCycleEvent=Staff_IH[5];
					ResultPhyCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
					temp=ResultPhyCycleEvent.split("-");
					OverallResult=GF.FinalResult(temp[0],ResultPhyCycleEvent, OverallResult);

					if(NumPhy==2&&i==0){
						ResultPhy=IHV.Result_Same_Assoc(Scope1InAssociationID,Phy_Assoc)+" for Physician "+Phy2+" that performed the exam.";
						temp=ResultPhy.split("-");
						OverallResult=GF.FinalResult(temp[0],ResultPhy, OverallResult);
					}else if(NumPhy==2&&i==1){
						ResultPhy=IHV.Result_Same_Assoc(Scope1InAssociationID,Phy_Assoc)+" for Physician "+Phy1+" that performed the exam.";
						temp=ResultPhy.split("-");
						OverallResult=GF.FinalResult(temp[0],ResultPhy, OverallResult);
					}else if(NumPhy==1){						
						ResultPhy=IHV.Result_Same_Assoc(Scope1InAssociationID,Phy_Assoc)+" for Physician "+Phy1+" that performed the exam.";
						temp=ResultPhy.split("-");
						OverallResult=GF.FinalResult(temp[0],ResultPhy, OverallResult);						
					}

					ResultPhy=ResultPhy+" ResultPhyCycleEvent="+ResultPhyCycleEvent;
					//System.out.println(ResultPhy);
					Expected=Description;
					IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPhy);
					try {
			    		conn= DriverManager.getConnection(url, user, pass);		
			    		//Statement update1 = conn.createStatement();
						Statement statement = conn.createStatement();
						stmt1="update ItemHistory set LastUpdatedDateTime=DATEADD(hh, -1, GETUTCDATE()) where ItemHistoryID_PK="+Phy_IH;
						statement.executeUpdate(stmt1);
			    		conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			CycleEvent="Patient In";
			Scope_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
			Patient_IH=Scope_IH[0];
			PatientAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(Patient_IH+" = Patient ItemHistory_PK");

			ResultPatientCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultPatientCycleEvent.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultPatientCycleEvent, OverallResult);

			ResultPatient=IHV.Result_Same_Assoc(Scope1InAssociationID,PatientAssociationID)+" for patient scan and scope into PR.";
			temp=ResultPatient.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultPatient, OverallResult);

			String ExpectedRmSt="In-use";
			
			String RmState=IHV.Room_State(UAS.connstring, Location);
			Result=IHV.Result_Room_State(RmState, ExpectedRmSt, Location);
			temp=Result.split("-");
			OverallResult=GF.FinalResult(temp[0], Result, OverallResult);
			
			Scope_IH=IHV.Scp_State_Loc(UAS.connstring, Scope1);
			ActualReproCount=Scope_IH[5];
			ActualExamCount=Scope_IH[6];

			ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(LocalScope1ExpectedRepro));
			temp=ResultReproCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
			
			ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(LocalScope1ExpectedExamCount));
			temp=ResultExamCount.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);

			ResultPatient=ResultPatient+" and ResultPatientCycle"+ResultPatientCycleEvent+" and RmState="+RmState+". "+Scope1+": ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+".";
			if(!Scope2.equalsIgnoreCase("")){
				LocalScope2ExpectedExamCount++;
				if(Scope2.equalsIgnoreCase("Scope1")){
					UAS.Scope1ExpectedExamCount=LocalScope2ExpectedExamCount;
				}else if(Scope2.equalsIgnoreCase("Scope2")){
					UAS.Scope2ExpectedExamCount=LocalScope2ExpectedExamCount;
				}else if(Scope2.equalsIgnoreCase("Scope3")){
					UAS.Scope3ExpectedExamCount=LocalScope2ExpectedExamCount;
				}else if(Scope2.equalsIgnoreCase("Scope4")){
					UAS.Scope4ExpectedExamCount=LocalScope2ExpectedExamCount;
				}

				Scope_IH=IHV.Scp_State_Loc(UAS.connstring, Scope2);
				ActualReproCount=Scope_IH[5];
				ActualExamCount=Scope_IH[6];

				ResultReproCount=IHV.Result_ReproCount(ActualReproCount,Integer.toString(LocalScope2ExpectedRepro));
				temp=ResultReproCount.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultReproCount,OverallResult);
				
				ResultExamCount=IHV.Result_ExamCount(ActualExamCount,Integer.toString(LocalScope2ExpectedExamCount));
				temp=ResultExamCount.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultExamCount,OverallResult);

				ResultPatient=ResultPatient+" "+Scope2+": ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
						 +". ResultLastStaff="+ResultLastStaff+".";
			}
			//System.out.println(ResultPatient);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPatient);
			
			//DailyDashboard Verification
			if (UAS.IsHappyPath || UAS.IsKEHappyPath){
				Description="Verifying DailyDashboard tab to see whether "+Scope1+" "+Scope2+" is getting displayed in "+Location;
				Expected=Description;
				String result=DV.verifyDashboard(Location, Scope1+"\n"+Scope2, "", dashboardpage.expPRInUseColor, dashboardpage.expPRInUse);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, result);
			}
		}

		
		//Scan Physician if provided. 
		if(Phy1.equalsIgnoreCase("")){
			ResultPhy="No Physician provided for Procedure Room.";
			//System.out.println(ResultPhy);
			PhyID1="";
		}else if(UAS.PhyScannedInWaiting==true){
			ResultPhy="Physician(s) was scanned with waiting room scanner and verified when patient was scanned into PR.";
		}else {			
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Physician", Phy1, "");
			Description="Scan of Physician '" +Phy1+"' is done in "+ Location+".";

			CycleEvent="Physician";
			Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
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
			//System.out.println(ResultPhy);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPhy);

		}
		
		//Scan Procedure Start if provided. 
		if(Start.equalsIgnoreCase("No")){
			ResultStart="Do not perform Start Procedure Scan for Exam.";
			//System.out.println(ResultStart);
		}else {
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Workflow Event", "", 	"Procedure Start", "");
			Description="Scan of Start Procedure is done in "+ Location+".";
			
			CycleEvent="Procedure Start";
			Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
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
			//System.out.println(ResultStart);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultStart);

		}
		
		Thread.sleep(60000); //Wait 1 minute before scanning Procedure End

		
		//Scan Procedure End if provided. 
		if(End.equalsIgnoreCase("No")){
			ResultEnd="Do not perform End Procedure Scan for Exam.";
			//System.out.println(ResultEnd);
		}else {
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Workflow Event", "", 	"Procedure End", "");
			Description="Scan of End Procedure is done in "+ Location+".";
			
			CycleEvent="Procedure End";
			Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
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
			//System.out.println(ResultEnd);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultEnd);
		}
		

		//Scan Scope1 Preclean
		UAS.ScannerCount=UAS.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Scope", "", Scope1, "");
		Description="Scan of Scope '" +Scope1+"' is done in "+ Location;
		CycleEvent="Pre-Clean Complete";
		Scope_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
		String Scope1OutIH=Scope_IH[0];
		String Scope1OutAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(Scope1OutIH+" = Scope Preclean Complete ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(UAS.connstring, Scope1);
		if(Culture1.equalsIgnoreCase("Pass")){
			//System.out.println("Scope_IH="+Scope_IH);
		}
		Scope1Preclean="Yes";
		ActualScopeState=Scope_IH[0];
		String ScopeOutLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ExpectedState="3";
		ExpectedCabinet="0";

		String ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultScopeOutCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutCycle, OverallResult);

		String ResultScope1OutAssociation=IHV.Result_Same_Assoc(Scope1InAssociationID,Scope1OutAssociationID)+" for "+Scope1+" into PR and Preclean Complete.";
		temp=ResultScope1OutAssociation.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScope1OutAssociation, OverallResult);

		String ResultScopeOutLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		temp=ResultScopeOutLoc.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutLoc, OverallResult);

		String ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID1),ActualOtherScopeState);
		temp=ResultScopeOutState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeOutState, OverallResult);

		String ResultPreCleanScope1=Scope1+": ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultScope1OutAssociation="+ResultScope1OutAssociation+". ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		//System.out.println(ResultPreCleanScope1);
		Expected=Description;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPreCleanScope1);

		if(Staff.equalsIgnoreCase("")){
			ResultStaffOut1="No Staff for procedure room provided";
			//System.out.println(ResultStaffOut1);
		}else {
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
			Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope Preclean complete";
			//Staff_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
			CycleEvent="PreCleanStaff";
			Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);			
			StaffOutIH=Staff_IH[0];
			StaffOut_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			
			ResultScopeOut1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultScopeOut1StaffCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScopeOut1StaffCycle,OverallResult);
			
			ResultStaffOut1=IHV.Result_Same_Assoc(Scope1OutAssociationID,StaffOut_Assoc)+" for staff that performed Preclean of "+Scope1;
			temp=ResultStaffOut1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultStaffOut1, OverallResult);
			//System.out.println(ResultStaffOut1);

			StaffPK=IHV.GetStaffPK(UAS.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(UAS.connstring, Scope1);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			temp=ResultLastStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);
			
			StaffOut_RI1=IHV.GetRelatedITem_IHKey(UAS.connstring, Scope1OutIH);
			ResultOut_RI1= IHV.RelatedItem_Verf(StaffOutIH, StaffOut_RI1);
			temp=ResultOut_RI1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultOut_RI1, OverallResult);

			//System.out.println(ResultOut_RI1);
			ResultStaffOut1="ResultStaffOut1 for associations:"+ResultStaffOut1+". ResultScopeOut1StaffCycle="+ResultScopeOut1StaffCycle+". ResultOut_RI1="+ResultOut_RI1+". ResultLastStaff="+ResultLastStaff;
			//System.out.println(ResultStaffOut1);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultStaffOut1);

		}
		
		//DailyDashboard Verification
		if (UAS.IsHappyPath || UAS.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether "+Scope2+" is getting displayed in "+Location;
			Expected=Description;
			String result=DV.verifyDashboard(Location, Scope2, "", dashboardpage.expPRInUseColor, dashboardpage.expPRInUse);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, result);
		}
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		//Verifying Chevron Color
		if(Staff.equalsIgnoreCase("")||Phy1.equalsIgnoreCase("")||Admit.equalsIgnoreCase("")||Patient.equalsIgnoreCase("")||Start.equalsIgnoreCase("")||End.equalsIgnoreCase("")){
			expectedColor=DBP.rgbOfIncompleteFlow;
			ChevronColor=DBP.Yellow;
		}else{
			expectedColor=DBP.rgbOfCompletedFlow;
			ChevronColor=DBP.Green;
		}
		resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope1RefNo,expectedColor);
		Description="Checking the Chevron Color on SRM Screen";
		Expected="The Chevron color should be "+ChevronColor;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultChevronColor);
		
		IP_A.Click_Details(Scope1RefNo);
		
		Result_Scope1ExamDate=WF_V.Verify_ExamDate(Scope1ExamTime);
		if(Result_Scope1ExamDate.contains("#Fail")){
			Result_Scope1ExamDate=Result_Scope1ExamDate;
		}
		System.out.println("Result_Scope1ExamDate=:"+Result_Scope1ExamDate);
		temp=Result_Scope1ExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ExamDate,OverallResult);
		
		if(NumPhy<=1){
			Result_Scope1Phy=WF_V.Verify_Physician(PhyID1);
			temp=Result_Scope1Phy.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1Phy,OverallResult);
			System.out.println("Result_Scope1Phy"+Result_Scope1Phy);			
		}else {
			Result_Scope1Phy=WF_V.Verify_Physician(PhyID1+", "+PhyID2);
			temp=Result_Scope1Phy.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope1Phy,OverallResult);
			System.out.println("Result_Scope1Phy"+Result_Scope1Phy);						
		}

		Result_Scope1Patient=WF_V.Verify_Patient(Patient);
		temp=Result_Scope1Patient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Patient,OverallResult);
		System.out.println("Result_Scope1Patient=:"+Result_Scope1Patient);
		
		ScopeInfo=IHV.GetProcStartEndTime(UAS.connstring,Scope1InAssociationID);
		ProcStartTime=ScopeInfo[0];
		ProcEndTime=ScopeInfo[1];
		
		String Result_Scope1ProcStart=WF_V.Verify_ProcStart(ProcStartTime);
		if(Result_Scope1ProcStart.contains("#Fail")){
			Result_Scope1ProcStart=Result_Scope1ProcStart;
		}
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

		Result_Scope1StaffPreclean=WF_V.Verify_PreCleanStaff(StaffID);		
		temp=Result_Scope1StaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1StaffPreclean,OverallResult);
		System.out.println("Result_Scope1StaffPreclean=:"+Result_Scope1StaffPreclean);

		WF_A.Cancel(Changes);
		Description="Scan "+Scope1+" Preclean in "+Location+" verify Scope Record Management";
		Expected=Description;
		String Result_SRM_Scope1_PR="Result_Scope1ExamDate="+Result_Scope1ExamDate+". Result_Scope1Phy="+Result_Scope1Phy+". Result_Scope1Patient="+Result_Scope1Patient+". Result_Scope1ProcStart="+Result_Scope1ProcStart+". "
				+ "Result_Scope1ProcEnd="+Result_Scope1ProcEnd+". Result_Scope1Preclean="+Result_Scope1Preclean+". Result_Scope1StaffPreclean="+Result_Scope1StaffPreclean;
		
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_SRM_Scope1_PR);
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+Scope1+" after scanning out of "+Location;
		Expected=Description;

		resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(Scope1, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+LocalScope1ExpectedExamCount+";REPROCESSING COUNT=="
				+LocalScope1ExpectedRepro).toString();
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultScope1_MAM);
		
		if(Scope2.equalsIgnoreCase("")){
			ResultPreCleanScope2="Scope 2 not passed into the function so skipping scans";
		} else {
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Scope", "", Scope2, "");
			Description="Scan of Scope '" +Scope2+"' is done in "+ Location;
			CycleEvent="Pre-Clean Complete";
			ExpectedState="3";
			Scope2Preclean="Yes";

			Scope_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
			String ScopeOutIH2=Scope_IH[0];
			Scope2OutAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeOutIH2+" = Scope Preclean ItemHistory_PK");
			Scope_IH=IHV.Scp_State_Loc(UAS.connstring, Scope2);
			
			ActualScopeState=Scope_IH[0];
			ScopeInLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];

			ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultScopeOutCycle.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeOutCycle, OverallResult);

			ResultScope2OutAssociation=IHV.Result_Same_Assoc(Scope2InAssociationID,Scope2OutAssociationID)+" for "+Scope2+" into PR and Preclean Complete.";
			temp=ResultScope2OutAssociation.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScope2OutAssociation, OverallResult);

			ResultScopeOutLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc);
			temp=ResultScopeOutLoc.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeOutLoc, OverallResult);

			ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID2),ActualOtherScopeState);
			temp=ResultScopeOutState.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeOutState, OverallResult);

			ResultPreCleanScope2=Scope2+": ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultScope2OutAssociation="+ResultScope2OutAssociation+". "
					+ "ResultScopeOutLoc="+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
			//System.out.println(ResultPreCleanScope2);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPreCleanScope2);
			
			//Scan staff that did the preclean
			if(Staff.equalsIgnoreCase("")){
				ResultStaffOut2="No Staff for procedure room";
				//System.out.println(ResultStaffOut2);
			}else {
				UAS.ScannerCount=UAS.ScannerCount+1;
				Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
				Description="Scan of Staff '" +Staff+"' is done in "+ Location+" for Scope Preclean complete";
				//Staff_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
				CycleEvent="PreCleanStaff";
				Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);			
				StaffOutIH=Staff_IH[0];
				StaffOut_Assoc=Staff_IH[1];
				ActualCycleEvent=Staff_IH[5];
				
				ResultScopeOut2StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
				temp=ResultScopeOut2StaffCycle.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultScopeOut2StaffCycle,OverallResult);
				
				ResultStaffOut2=IHV.Result_Same_Assoc(Scope2OutAssociationID,StaffOut_Assoc)+" for staff and "+Scope2+" Preclean complete.";
				temp=ResultStaffOut2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultStaffOut2, OverallResult);			
				//System.out.println(ResultStaffOut2);
				
				StaffPK=IHV.GetStaffPK(UAS.connstring,StaffID);
				LastScanStaffID_FK=IHV.Scp_State_LastStaffID(UAS.connstring, Scope2);
				ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
				temp=ResultLastStaff.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultLastStaff,OverallResult);

				StaffOut_RI2=IHV.GetRelatedITem_IHKey(UAS.connstring, ScopeOutIH2);
				ResultOut_RI2= IHV.RelatedItem_Verf(StaffOutIH, StaffOut_RI2);
				temp=ResultOut_RI2.split("-");
				OverallResult=GF.FinalResult(temp[0],ResultOut_RI2, OverallResult);

				//System.out.println(ResultOut_RI2);
				ResultStaffOut2="ResultStaffOut2 for associations:"+ResultStaffOut2+". ResultScopeOut2StaffCycle="+ResultScopeOut2StaffCycle+". ResultOut_RI2="+ResultOut_RI2+". ResultLastStaff="+ResultLastStaff+".";
				
				//System.out.println(ResultStaffOut2);
				Expected=Description;
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultStaffOut2);
			}
			//DailyDashboard Verification
			if (UAS.IsHappyPath || UAS.IsKEHappyPath){
				Description="Verifying DailyDashboard tab to see whether nothing is getting displayed in "+Location+" and status is shown as 'In Use'";
				Expected=Description;
				String result=DV.verifyDashboard(Location, "", "", dashboardpage.expPRInUseColor, dashboardpage.expPRInUse);
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, result);
			}
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			
			//Verifying Chevron Color
			if(Staff.equalsIgnoreCase("")||Phy1.equalsIgnoreCase("")||Admit.equalsIgnoreCase("")||Patient.equalsIgnoreCase("")||Start.equalsIgnoreCase("")||End.equalsIgnoreCase("")){
				expectedColor=DBP.rgbOfIncompleteFlow;
				ChevronColor=DBP.Yellow;
			}else{
				expectedColor=DBP.rgbOfCompletedFlow;
				ChevronColor=DBP.Green;
			}
			resultChevronColor=WF_V.Verify_ChevronColor(Location, Scope2RefNo,expectedColor);
			Description="Checking the Chevron Color on SRM Screen";
			Expected="The Chevron color should be "+ChevronColor;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultChevronColor);
			
			IP_A.Click_Details(Scope2RefNo);
			
			Result_Scope2ExamDate=WF_V.Verify_ExamDate(Scope2ExamTime);
			if(Result_Scope2ExamDate.contains("#Fail")){
				Result_Scope2ExamDate=Result_Scope2ExamDate;
			}
			System.out.println("Result_Scope2ExamDate=:"+Result_Scope2ExamDate);
			temp=Result_Scope2ExamDate.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2ExamDate,OverallResult);

			if(NumPhy<=1){
				Result_Scope2Phy=WF_V.Verify_Physician(PhyID1);
				temp=Result_Scope2Phy.split("-");
				OverallResult=GF.FinalResult(temp[0], Result_Scope2Phy,OverallResult);
				System.out.println("Result_Scope2Phy"+Result_Scope2Phy);
			}else {
				Result_Scope2Phy=WF_V.Verify_Physician(PhyID1+", "+PhyID2);
				temp=Result_Scope2Phy.split("-");
				OverallResult=GF.FinalResult(temp[0], Result_Scope2Phy,OverallResult);
				System.out.println("Result_Scope2Phy"+Result_Scope2Phy);
			}

			Result_Scope2Patient=WF_V.Verify_Patient(Patient);
			temp=Result_Scope2Patient.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2Patient,OverallResult);
			System.out.println("Result_Scope2Patient=:"+Result_Scope2Patient);
			
			ScopeInfo=IHV.GetProcStartEndTime(UAS.connstring,Scope2InAssociationID);
			ProcStartTime=ScopeInfo[0];
			ProcEndTime=ScopeInfo[1];
			
			Result_Scope2ProcStart=WF_V.Verify_ProcStart(ProcStartTime);
			if(Result_Scope2ProcStart.contains("#Fail")){
				Result_Scope2ProcStart=Result_Scope2ProcStart;
			}
			
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

			Result_Scope2StaffPreclean=WF_V.Verify_PreCleanStaff(StaffID);		
			temp=Result_Scope2StaffPreclean.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_Scope2StaffPreclean,OverallResult);
			System.out.println("Result_Scope2StaffPreclean=:"+Result_Scope2StaffPreclean);

			WF_A.Cancel(Changes);
			Description="Scan "+Scope2+" Preclean in "+Location+" verify Scope Record Management";
			Expected=Description;
			String Result_SRM_Scope2_PR="Result_Scope2ExamDate="+Result_Scope2ExamDate+". Result_Scope2Phy="+Result_Scope2Phy+". Result_Scope2Patient="+Result_Scope2Patient
					+". Result_Scope2ProcStart="+Result_Scope2ProcStart+". Result_Scope2ProcEnd="+Result_Scope2ProcEnd+". Result_Scope2Preclean="+Result_Scope2Preclean
					+". Result_Scope2StaffPreclean="+Result_Scope2StaffPreclean;
			
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, Result_SRM_Scope2_PR);
			
			MAM_A.Click_MaterialsAndAssetManagement();
			Description="Verify MAM screen for "+Scope2+" after scanning out of "+Location;
			Expected=Description;

			String resultScope2_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(Scope2, "LAST SCOPE LOCATION=="+Location+" (YFN);LAST SCAN STAFF ID=="+StaffID
					+";EXAM COUNT=="+LocalScope2ExpectedExamCount+";REPROCESSING COUNT=="
					+LocalScope2ExpectedRepro).toString();
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultScope2_MAM);
			
		}
		
		//Scan Room Needs Cleaning barcode
		UAS.ScannerCount=UAS.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Workflow Event", "", "Needs Cleaning", "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Needs Cleaning' is done in "+ Location;

		CycleEvent="Room Status Change";
		Thread.sleep(2000);
		//DailyDashboard Verification
		if (UAS.IsHappyPath || UAS.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether nothing is getting displayed in "+Location+" and status is shown as 'Needs Cleaning'";
			Expected=Description;
			String result=DV.verifyDashboard(Location, "", "", dashboardpage.expPRNeedsCleaningColor, dashboardpage.expPRNeedsCleaning);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, result);
		}
		
		Scope_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
		RoomStatus_IH=Scope_IH[0];
		RoomStatus_Assoc=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println("Room Status of Needs Cleaning AssociationID="+RoomStatus_Assoc);

		OtherScopeStateID1=0;  //OtherScopeState is changed to zero when the bucket is closed which is the room status change.  
		Scope_IH=IHV.Scp_State_Loc(UAS.connstring, Scope1);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		if(Culture1.equalsIgnoreCase("Pass")){
			//System.out.println("Scope_IH="+Scope_IH);
		}
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID1),ActualOtherScopeState);
		temp=ResultScopeInState.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultScopeInState, OverallResult);
		
		if(Scope2.equalsIgnoreCase("")){
			ResultScopeInState2="ResultScopeInState2 not applicable as second scope wasn't provided";
		}else {
			OtherScopeStateID2=0;  //OtherScopeState is changed to zero when the bucket is closed which is the room status change.  
			Scope_IH=IHV.Scp_State_Loc(UAS.connstring, Scope2);
			
			ActualScopeState=Scope_IH[0];
			ScopeInLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			
			ResultScopeInState2=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID2),ActualOtherScopeState);
			temp=ResultScopeInState2.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultScopeInState2, OverallResult);

		}
		
		ResultRoomStatusCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultRoomStatusCycle.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultRoomStatusCycle, OverallResult);

		ResultRoomStatus=IHV.Result_Same_Assoc(Scope1InAssociationID,RoomStatus_Assoc)+" for Room Status scan in PR.";
		temp=ResultRoomStatus.split("-");
		OverallResult=GF.FinalResult(temp[0],ResultRoomStatus, OverallResult);

		ResultRoomStatus=ResultRoomStatus+" and ResultRoomStatusCycle"+ResultRoomStatusCycle+". "+Scope1+" scopestatus result="+ResultScopeInState+". "
				+Scope2+ " scopestatus result="+ResultScopeInState2;
		//System.out.println(ResultRoomStatus);
		Expected=Description;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultRoomStatus);
		//System.out.println("OverallResult="+OverallResult);

		
		PRResult[0]=Scope1InAssociationID;
		PRResult[1]=OverallResult;

		
		return PRResult;
	}
}
