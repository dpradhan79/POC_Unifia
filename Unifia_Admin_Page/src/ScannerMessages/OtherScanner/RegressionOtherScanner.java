package ScannerMessages.OtherScanner;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class RegressionOtherScanner {
	
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
	public TestFrameWork.Emulator.GetIHValues IHV;
	public GeneralFunc gf;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestDataFunc TDF;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	public Connection conn= null;
	public String stmt; //used for SQL queries
	public String stmt1;
	public ResultSet Scope_rs;
	public ResultSet Scanner_ID_rs;
	public ResultSet Staff_rs;
	public String Scanner,Facility;
	public String[] LocationInfo=new String[2]; 
	public int LocationID_PK;
	public Boolean Res;
	public String ScopeSerialNumber="";
	public String StaffID="";
	public String StaffFirstName="";
	public String StaffLastName="";
	public String StaffScan;
	public String ScopeName="";
	public int ScopeID;
	public int Scenario;
	public int CultureLocType=9;
	public int AdminLocType=1;
	public ResultSet Barcode_rs;
	public String Destination;
	public int BarcodeID_PK;
	public String actualResult="\t\t\t ScanMessages_OtherScanner_TestSummary \n"; 
	public String Description;
	public String ForFileName;
	public String TestResFileName="ScanMessages_Other_TestSummary_";
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	
	public String Scope_IH[];
	public String Staff_IH[];
	public String Destination_IH[];
	public String CycleEvent;
	public String ScopeStatus;
	public String ScopeCultIH;
	public String ScopeCultAssociationID;
	public String StaffCultIH;
	public String StaffCult_Assoc;
	public String ActualOtherScopeState;
	public String ActualCycleEvent;
	public String ScopeCultLoc;
	public String ExpectedCabinet="0";

	public String ResultScopeCultCycle;
	public String ResultScopeCultState;
	public String ResultScopeCultLoc;
	public String ResultCult;
	public String ResultStaffCult;
	
	public String ActualScopeState;
	public String ExpectedState;
	public int OtherScopeStateID;
	public String ActualSubloc;
	
	public String ScopeOOFIH; //scope check out of faculity Item History
	public String ScopeOOFAssociationID;
	public String DestinationIH;
	public String Destination_Assoc;
	public String StaffOOFIH;
	public String StaffOOF_Assoc;
	public String ScopeOOFLoc;

	public String ResultScopeOOFCycle;
	public String ResultScopeOOFLoc;
	public String ResultScopeOOFState;
	public String ResultOOF;
	public String ResultStaffOOF;
	public String ResultDestination;

	public String ScopeCIIH; //scope check in Item History
	public String ScopeCIAssociationID;
	public String StaffCIIH;
	public String StaffCI_Assoc;
	public String ScopeCILoc;
	public String ResultScopeCICycle;
	public String ResultScopeCILoc;
	public String ResultScopeCIState;
	public String ResultCI;
	public String ResultStaffCI;
	
	public String ResultLastStaff;
	public int StaffPK=0;
	public int LastScanStaffID_FK;

	
	public String Expected;
	public String FileName;
	
	private String ScopeRefNo="";
	private String ScopeSerialNo="";
	private String ScopeModel="";
	private String OverallResult="Pass";
	public String Changes="No";

	public int KE=0;
	public int Bioburden=0;
	public int Culture=1;
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, AWTException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.ScannerCount=0;
		Unifia_Admin_Selenium.resultFlag="Pass";
		//DB updates
		//gf.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
    	gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
		/*LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
		LP_A.CloseDriver();
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
		*/
				
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		
		
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date); 
		FileName="CycleMgmtOther_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		
		//Scenario 1 - Scan scope with Culture scanner then scan staff 
		Description ="Start of new Scenario "+Scenario;
		actualResult=actualResult+"\r\nScan a scope with a culture scanner then scan staff---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		LocationInfo=SelectScanner(CultureLocType);
		Scanner=LocationInfo[0];
		Facility=LocationInfo[1];
		System.out.println("Selected ScopeNames "+Scanner);
		Res=ScopeToScan(Scanner);
		if(Res){
			Res = EM_V.VerifyScanMsg("Culture Obtained", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Culture Obtained="+Scanner;

			CycleEvent="Culture Obtained";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			ScopeCultIH=Scope_IH[0];
			ScopeCultAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			System.out.println(ScopeCultIH+" = Scope checkin Culture Obtained ItemHistory_PK");
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			
			String []ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScopeName,ScopeCultAssociationID);
			ScopeRefNo=ScopeInfo[0];
			ScopeSerialNo=ScopeInfo[1];
			ScopeModel=ScopeInfo[2];
			

		}else{
			System.out.println("Error in Scanning Scope with Culture Scanner");
			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" culture scan with ="+Scanner+" Error.";

		}
		actualResult=actualResult+"\r\n --------:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);
		
		Res=ScanStaff(Scanner);
		if(Res){
			Res = EM_V.VerifyScanMsg("Staff "+StaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			actualResult=actualResult+"\r\n ------:\r\n\t"+UAS.Result;
			
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			StaffCultIH=Staff_IH[0];
			StaffCult_Assoc=Staff_IH[1];
			ResultStaffCult=IHV.Result_Same_Assoc(ScopeCultAssociationID,StaffCult_Assoc);
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			
			ResultStaffCult=ResultStaffCult+" ResultLastStaff="+ResultLastStaff;

			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Culture Obtained; Scanner="+Scanner+" and is associated to Staff="+StaffID;

		}else{
			System.out.println("Error in Scanning Staff with Culture Scanner");
			Expected="Error in Scanning Staff with Culture Scanner";
		}

		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		System.out.println(Scenario+":  "+ResultStaffCult);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffCult);
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
		
		ActualScopeState=Scope_IH[0];
		ScopeCultLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ExpectedState="5";
		ExpectedCabinet="0";
		OtherScopeStateID=7;

		ResultScopeCultCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeCultLoc=IHV.Result_Location(Scanner, ScopeCultLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeCultState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultCult="ResultScopeCultCycle= "+ResultScopeCultCycle+". ResultScopeCultLoc= "+ResultScopeCultLoc+". ResultScopeCultState="+ResultScopeCultState;
		System.out.println(Scenario+":  "+ResultCult);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCult);
		
		//SRM Verification for Culture
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(ScopeRefNo);
		
		Description="Verify Scope Record Management of "+ScopeName+" and Staff "+StaffID+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(ScopeRefNo);
		System.out.println("Result_RefNum="+Result_RefNum);
		String []temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(ScopeModel);
		System.out.println("Result_ScopeModel="+Result_ScopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScopeName);
		System.out.println("Result_ScopeName="+Result_ScopeName);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(ScopeSerialNo);
		System.out.println("Result_ScopeSerialNo="+Result_ScopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);
		
		String CultResultScope = WF_V.Verify_Culture("Awaiting Results");
		System.out.println("CultResultScope="+CultResultScope);
		temp=CultResultScope.split("-");
		OverallResult=GF.FinalResult(temp[0], CultResultScope,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String CultureResult="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". CultResultScope="+CultResultScope;
		System.out.println("CultureResult - "+CultureResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, CultureResult);
		
		//MAM Verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScopeName+" after scanning into "+Scanner;
		Expected=Description;

		String resultScope_MAM="Scope2 MAM Result ="+MAM_V.verifyScopeDetails(ScopeName, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT==0"+";REPROCESSING COUNT==0").toString();
		System.out.println("resultScope_MAM - "+resultScope_MAM);
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);

		
		//Scenario 2 - Scan scope out of facility, then destination, then staff 
		Scenario++;
		Description ="Start of new Scenario "+Scenario;
		actualResult=actualResult+"\r\nScan a scope Out of the Facility, scan the destination, then scan staff with Admin scanner---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		LocationInfo=SelectScanner(AdminLocType);
		Scanner=LocationInfo[0];
		Facility=LocationInfo[1];

		System.out.println("Selected Scanner is "+Scanner);

		//scan scope out of facility

   		try{ //Get a value that exists in Unifia to modify.
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; 
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ScopeName = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
				ScopeSerialNumber= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
				ScopeID = Scope_rs.getInt(3);

			}		
			Scope_rs.close();
			System.out.println("ScopeName = "+ScopeName);
			update.execute("Update Scope set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_PK='"+ScopeID+"';");
			update.close();
			statement.close();
			conn.close();
   		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}

		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScopeName, "");
		System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' out of facility with Admin Scanner";
		actualResult=actualResult+"\r\n --------:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		if(Res){
			Res = EM_V.VerifyScanMsg(ScopeSerialNumber+" Out of Facility", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Out of Facility Scanner="+Scanner;

			CycleEvent="Facility Checkout";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			ScopeOOFIH=Scope_IH[0];
			ScopeOOFAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);

			System.out.println(ScopeCultIH+" = Scope checked Out of Facility ItemHistory_PK");

		}else{
			System.out.println("Error in Scanning Scope out of facility with Admin Scanner");
		}
		actualResult=actualResult+"\r\n --------:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		

		//scan out of facility destination 

   		try{ //Get a value that exists in Unifia to modify.
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Name, BarcodeID_PK from Barcode where BarcodeTypeID_FK=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime)  from Barcode where BarcodeTypeID_FK=1 and BarcodeID_PK <>0 and IsActive=1)"; 
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Barcode_rs = statement.executeQuery(stmt);
			while(Barcode_rs.next()){
				Destination = Barcode_rs.getString(1); 
				BarcodeID_PK = Barcode_rs.getInt(2);

			}		
			Scope_rs.close();
			System.out.println("Destination = "+Destination);
			update.execute("Update Barcode set LastUpdatedDateTime=GETUTCDATE() WHERE BarcodeID_PK='"+BarcodeID_PK+"';");
			update.close();
			statement.close();
			conn.close();
   		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}

		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Out of Facility Location", "", Destination, "");
		System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' destination with Admin Scanner";
		actualResult=actualResult+"\r\n --------:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		if(Res){
			if(Destination.length()>13){
				Destination=Destination.substring(0,13);
				System.out.println(Destination);
				}
			Res = EM_V.VerifyScanMsg(ScopeSerialNumber+" Checkout to "+Destination, Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			actualResult=actualResult+"\r\n --------:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			Destination_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			DestinationIH=Destination_IH[0];
			Destination_Assoc=Destination_IH[1];
			ResultDestination=IHV.Result_Same_Assoc(ScopeOOFAssociationID,Destination_Assoc);
			
			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Out of Office; Scanner="+Scanner+" and is associated to Destination="+Destination;

			
		}else{
			System.out.println("Error in Scanning out of facility Destination with Admin Scanner");
			Expected="Error in Scanning out of facility Destination with Admin Scanner";
		}

		
		//Scan staff that checked scope out of facility
		Res=ScanStaff(Scanner);
		if(Res){
			Res = EM_V.VerifyScanMsg("Staff "+StaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			actualResult=actualResult+"\r\n ------:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			StaffOOFIH=Staff_IH[0];
			StaffOOF_Assoc=Staff_IH[1];
			ResultStaffOOF=IHV.Result_Same_Assoc(ScopeOOFAssociationID,StaffOOF_Assoc);
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			
			ResultStaffOOF=ResultStaffOOF+" ResultLastStaff="+ResultLastStaff;
			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Out of Office; Scanner="+Scanner+" and is associated to Staff="+StaffID;
			
		}else{
			System.out.println("Error in Scanning Staff with Admin Scanner");
		}

		
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		System.out.println(Scenario+":  "+ResultStaffOOF);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffOOF);
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
		
		ActualScopeState=Scope_IH[0];
		ScopeOOFLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ExpectedState="1";
		ExpectedCabinet="0";
		OtherScopeStateID=0;

		ResultScopeOOFCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeOOFLoc=IHV.Result_Location(Scanner, ScopeOOFLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeOOFState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultOOF="ResultScopeOOFCycle= "+ResultScopeOOFCycle+". ResultScopeOOFState="+ResultScopeOOFState;
		System.out.println(Scenario+":  "+ResultOOF);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultOOF);

		//MAM Verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScopeName+" after scanning into "+Scanner;
		Expected=Description;

		resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScopeName, "LAST SCOPE LOCATION==Out of Facility ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT==0"+";REPROCESSING COUNT==0").toString();
		System.out.println("resultScope_MAM - "+resultScope_MAM);
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
		
		
		//Scan scope back into facility:
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScopeName, "");
		System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' into Facility with Admin Scanner";
		actualResult=actualResult+"\r\n --------:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		if(Res){
			Res = EM_V.VerifyScanMsg(ScopeSerialNumber+" Returned to Facility", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			actualResult=actualResult+"\r\n --------:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked into Facility Scanner="+Scanner;

			CycleEvent="Facility Checkin";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			ScopeCIIH=Scope_IH[0];
			ScopeCIAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			System.out.println(ScopeCIIH+" = Scope checked into Facility ItemHistory_PK");
			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);

		}else{
			System.out.println("Error in Scanning Scope back into facility with Admin Scanner");
			Expected="Error in Scanning Scope back into facility with Admin Scanner";
		}
		
		//Scan staff that checked scope into facility
		Res=ScanStaff(Scanner);
		if(Res){
			Res = EM_V.VerifyScanMsg("Staff "+StaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			actualResult=actualResult+"\r\n ------:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			StaffCIIH=Staff_IH[0];
			StaffCI_Assoc=Staff_IH[1];
			ResultStaffCI=IHV.Result_Same_Assoc(ScopeCIAssociationID,StaffCI_Assoc);
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			ResultStaffCI=ResultStaffCI+" ResultLastStaff="+ResultLastStaff;
			
			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked into Facility; Scanner="+Scanner+" and is associated to Staff="+StaffID;
		}else{
			System.out.println("Error in Scanning Staff with Admin Scanner");
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffOOF);
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
		
		ActualScopeState=Scope_IH[0];
		ScopeCILoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ExpectedState="5";
		ExpectedCabinet="0";
		OtherScopeStateID=0;

		ResultScopeCICycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeCILoc=IHV.Result_Location(Scanner, ScopeCILoc, ExpectedCabinet,ActualSubloc);
		ResultScopeCIState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultCI="ResultScopeCICycle= "+ResultScopeCICycle+". ResultScopeCIState="+ResultScopeCIState;
		System.out.println(Scenario+":  "+ResultCI);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCI);
		
		//MAM Verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScopeName+" after scanning into "+Scanner;
		Expected=Description;

		resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScopeName, "LAST SCOPE LOCATION==Returned to Facility ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT==0"+";REPROCESSING COUNT==0").toString();
		System.out.println("resultScope_MAM - "+resultScope_MAM);
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
		
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (actualResult.contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.contains("#Failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	public String[] SelectScanner(int LocType) throws SQLException, InterruptedException{
		String[] Values=new String[2];
   		try{ //Get a value that exists in Unifia to modify.
   			//Thread.sleep(2000);
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select Loc.LocationID_PK, Loc.Name, Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where "
					+ "Loc.LocationTypeID_FK="+LocType+" and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where "
							+ "LocationTypeID_FK="+LocType+" and IsActive=1)"; //put sql statement here to find ID
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				Scanner = Scanner_ID_rs.getString(2);
				Facility = Scanner_ID_rs.getString(3);
			}		
			Scanner_ID_rs.close();
			System.out.println("Scanner= "+Scanner);
			update.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
			update.close();
			statement.close();
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
   		Values[0]=Scanner;
   		Values[1]=Facility;   		
		return Values;
	}
	
	public  Boolean ScopeToScan(String Scanner) throws SQLException, InterruptedException{
		
		
   		try{ //Get a value that exists in Unifia to modify.
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; //put sql statement here to find ScopeName
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ScopeName = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
				ScopeSerialNumber= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
				ScopeID = Scope_rs.getInt(3);

			}		
			Scope_rs.close();
			System.out.println("ScopeName = "+ScopeName);
			update.execute("Update Scope set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_PK='"+ScopeID+"';");
			update.close();
			statement.close();
			conn.close();
   		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}

		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScopeName, "");
		System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' is done";
		actualResult=actualResult+"\r\n --------:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		return Res;
	}
	
	public Boolean ScanStaff(String Scanner) throws SQLException, InterruptedException{
		
   		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5  and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				StaffID= Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			Staff_rs.close();
			System.out.println("StaffID = "+StaffID);
			update.execute("Update Staff set LastUpdatedDateTime=GETUTCDATE() WHERE StaffID='"+StaffID+"';");
			update.close();
			statement.close();
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
   		StaffScan=StaffFirstName+" "+StaffLastName+"("+StaffID+")";

		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Staff '" +StaffFirstName+" "+StaffLastName+"("+StaffID+")"+"' with Scanner "+Scanner +"is done";
		actualResult=actualResult+"\r\n ------:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		return Res;	
	}
  @AfterTest
  public void PostTest() throws IOException{
  	LP_A.CloseDriver();
  }
		  

}
