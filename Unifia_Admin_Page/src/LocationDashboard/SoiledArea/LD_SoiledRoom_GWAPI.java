package LocationDashboard.SoiledArea;

import org.graphwalker.core.machine.ExecutionContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.DateFormat; 
import java.io.File;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.Emulator_Actions; 
import TestFrameWork.Unifia_LocationDashboard.*;

public class LD_SoiledRoom_GWAPI extends ExecutionContext{

	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
	public 	TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_LocationDashboard.LD_Actions LD_A;
	public static TestFrameWork.Unifia_LocationDashboard.LD_Verification LD_V;
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	
	public String Scope="NotScanned";
	public String Staff="NotScanned";
	public String LeakTest="NotScanned";
	public String MCStart="NotScanned";
	public String MCEnd="NotScanned";
	public String ScanScope="Scan scope";
	public String ScopeName="Scan scope";
	public String ScopeSerialNumber="";
	public int ScopeID;

	public String StaffID="";
	public String StaffFirstName="";
	public String StaffLastName="";
	public String StaffScan="";
	public String StaffNameTop="Scan Staff ID";
	public String StaffNameBottom="Scan Badge";
	public String TopLT="Leak Test Not Started";
	public String BottomLT="Scan Pass/Fail";
	public String BottomMC="Scan Manual Clean Start";
	public String LeakTestRes="";
	public String MCTimePeriod="";
	public String AssociationID="";
	public Boolean Res;
	public String Scanner="",Facility="",FacilityName="";
	public int LocationID_PK;
	public Integer Scenario=0;
	public Date MCStartTime= new Date();
	public Date MCEndTime= new Date();
	
	public Connection conn= null;
	public ResultSet Scope_rs;  //result set used to get ScopeName from the Test DB
	public ResultSet Staff_rs;
	public ResultSet Scanner_ID_rs;
	public ResultSet MCStart_rs;
	public ResultSet MCEnd_rs;
	public ResultSet OtherScopeState_rs;
	public String stmt; //used for SQL queries
	public String Description;
	public String ForFileName;
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	//For dB Verification
	public String ExpectedCycleEvent;
	public String Scope_IH[];
	public String Scope_Stat[];
	public String Staff_IH[];
	public String ScopeInIH;
	public String ScopeInAssociationID;
	public String StaffInIH;
	public String ActualCycleEvent;
	public String ActualScopeState;
	public String ScopeInLoc;
	public String ActualOtherScopeState;
	public String ActualSubloc;
	public String ExpectedState;
	public Integer OtherScopeStateID;
	public String ExpectedCabinet;
	public String ResultScopeInCycle;
	public String ResultScopeInLoc;
	public String ResultScopeInState;
	public String ResultPRIn;
	public String FileName="";
	public String Expected;
	public String StaffIn_Assoc;
	public String ResultStaffIn;
	public Integer ExpectedCycleEventID;
	public String ActualCycleEventID;
	public String ActualScanItemType;
	public String ExpectedScanItemType;
	public Integer ExpectedAssociationID;
	
	public String ResultLTCycle;
	public String ResultLTScanItemType;
	public String ResultLT;
		
	public String LT_IH[], LeakTestIH, LeakTestAssocID, NoLeakTestTAssocID;
	public String ResultLeakTestCycle,ResultLeakTest_SameAssocID,ResultLeakTestBlankAssocID,ResultLeakTestTScanItemType;
	public String MCS_IH[],ManCleanStartIH,ManCleanStartAssocID;
	public String  ResultMCStartCycle,ResultMCStartAssocID,ResultMCStartScanItemType,ResultSR;
	public String MCE_IH[],ManCleanEndIH,ManCleanEndAssocID;
	public String ResultMCEndAssocID,ResultMCEndCycle,ResultMCEndScopeState,ResulMCEndScanItemType;
	public String MT1h[],ExpectedCycleCount,ActualCycleCount;
	public String ResultCycleCountMoreThan1hr,ResultScopeInLoc1h,ResultScopeInState1h,ResultScopeInCycle1h;

	public String ResultLastStaff;
	public int StaffPK=0;
	public int LastScanStaffID_FK;
	public String ResultStaffCycle;
	
	private String ScopeSinkInTime="";
	public String ResultMessage="";
	public String Message="";
	public String ResultLocationFacility="";
	public String ResultTopScope,ResultBottomScope,ResultTopStaff,ResultBottomStaff,ResultTopLT,ResultBottomLT,ResultBottomMC;

	public void e_Start() throws InterruptedException{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		FileName="LD_SoiledRoom_";
		FileName=IHV.Start_Exec_Log(FileName);
		UAS.ScannerCount=0;
		UAS.XMLFileName=FileName;
		UAS.TestCaseNumber=1;
	}
	
	public void e_ScanScope() throws  InterruptedException{
		Scope="Scanned";
		Description="Scan of Scope '" +ScanScope+"' is done";
		if(FileName.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date);
			FileName="LD_SoiledRoom_";
			FileName=IHV.Start_Exec_Log(FileName);
			UAS.ScannerCount=0;
			UAS.XMLFileName=FileName;
			UAS.TestCaseNumber=1;
		}
		//System.out.println(getCurrentElement().getName());
		
   		try{ //Get a value that exists in Unifia to modify.
    		conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);	
			stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and ScopeTypeID_FK!=155 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1 and ScopeTypeID_FK!=155)"; //put sql statement here to find ScopeName
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ScanScope = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
				ScopeSerialNumber= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
				ScopeID = Scope_rs.getInt(3);

			}		
			Scope_rs.close();
			//System.out.println("ScopeName = "+ScanScope);
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
   		if (Scanner.equals("")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			//Thread.sleep(2000);
				conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation,Fac.Name from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); 
					Facility= Scanner_ID_rs.getString(3);
					FacilityName = Scanner_ID_rs.getString(4); 
				}		
				Scanner_ID_rs.close();
				//System.out.println("Scanner= "+Scanner);
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
	   		Expected="Verifying Location Dashboard default screen.";
	   		LD_A.ClickLocation(LocationID_PK);
	   		ResultLocationFacility=LD_V.VerifyLocationFacility(Scanner,FacilityName);
			System.out.println(Scenario+" ResultLocationFacility:  "+ResultLocationFacility);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLocationFacility);
	   		Message="Scan the scope.";
			ResultMessage=LD_V.VerifyMessage(Message);
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
			
			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
  		}
   		Expected="Verifying Location Dashboard after scanning a scope.";

		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		ScopeName=ScanScope;
		Message="Scan your badge.";
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}
	
	public void e_ScanStaff() throws  InterruptedException{
		Staff="Scanned";
		//System.out.println(getCurrentElement().getName());
		Description="Scan of Staff '" +StaffScan+"' is done";
		
   		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);	
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5  and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				StaffID= Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			Staff_rs.close();
			//System.out.println("StaffID = "+StaffID);
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
   		StaffNameTop=StaffFirstName+" "+StaffLastName;
   		StaffNameBottom=StaffNameTop;
   		Expected="Verifying Location Dashboard after scanning a staff.";

		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		Message="Conduct leak test then scan\n\"Leak Test Passed\"\nor \"Leak Test Failed\"";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}
	
	public void e_LTFail() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Description="Scan of Test Result - Leak Test Fail is done";
   		Expected="Verifying Location Dashboard after scanning Leak Test Fail.";

		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Fail", "");
		if(Res){
			LeakTest="Scanned";
			LeakTestRes="Fail";
		}
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		TopLT="Leak Test Failed";
		BottomLT="Leak test failed";
		UAS.ScannerCount=UAS.ScannerCount+1;
		Message="Leak Test Failed\nFollow standard procedures,\nthen scan Manual Clean Start.";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}
	
	public void e_LTPass() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Description="Scan of Test Result - Leak Test Pass is done";
   		Expected="Verifying Location Dashboard after scanning Leak Test Pass.";

		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
		if(Res){
			LeakTest="Scanned";
			LeakTestRes="Pass";
		}
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		TopLT="Leak Test Passed";
		BottomLT="Leak test passed";

		UAS.ScannerCount=UAS.ScannerCount+1;
		Message="Scan Manual Clean Start\nthen begin manual cleaning process.";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}	

	public void e_ChangeLT() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());

		if(LeakTestRes.equalsIgnoreCase("Pass")){
			Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Fail", "");
			//System.out.println(Res);
			if(Res){
				LeakTest="Scanned";
				LeakTestRes="Fail";
			}
			TopLT="Leak Test Failed";
			BottomLT="Leak test failed";
			UAS.ScannerCount=UAS.ScannerCount+1;
			Message="Leak Test Failed\nFollow standard procedures,\nthen scan Manual Clean Start.";
	   		Expected="e_ChangeLT Verifying Location Dashboard after scanning Leak Test Fail after scanning LT Pass.";
			Description="Scan of Test Result - Leak Test Fail is done";

		}else if(LeakTestRes.equalsIgnoreCase("Fail")){
			Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
			//System.out.println(Res);
			if(Res){
				LeakTest="Scanned";
				LeakTestRes="Pass";
			}
			TopLT="Leak Test Passed";
			BottomLT="Leak test passed";

			UAS.ScannerCount=UAS.ScannerCount+1;
			Message="Scan Manual Clean Start\nthen begin manual cleaning process.";
	   		Expected="e_ChangeLT Verifying Location Dashboard after scanning Leak Test Pass after scanning LT Fail.";
			Description="Scan of Test Result - Leak Test Pass is done";
		}
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);

	}

	
	public void e_LTFailNoScope() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Description="Scan of Test Result - Leak Test Fail is done";
		if (Scanner.equals("")&& Scope.equals("NotScanned")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			//Thread.sleep(2000);
				conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation,Fac.Name from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2);
					Facility= Scanner_ID_rs.getString(3);
					FacilityName = Scanner_ID_rs.getString(4); 
				}		
				Scanner_ID_rs.close();
				//System.out.println("Scanner= "+Scanner);
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
	   		Expected="Verifying Location Dashboard default screen.";
	   		LD_A.ClickLocation(LocationID_PK);
	   		ResultLocationFacility=LD_V.VerifyLocationFacility(Scanner,FacilityName);
			System.out.println(Scenario+" ResultLocationFacility:  "+ResultLocationFacility);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLocationFacility);
	   		Message="Scan the scope.";
			ResultMessage=LD_V.VerifyMessage(Message);
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
			
			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
		}
   		
   		//Scan the Test Result
   		Expected="Verifying Location Dashboard after scanning Leak Test Fail without scanning a scope first.";

		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Fail", "");
		if(Res){
			LeakTest="Scanned";
			LeakTestRes="Fail";
		}
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		Message="Scan the scope.";	
		ResultMessage=LD_V.VerifyMessage(Message);

		UAS.ScannerCount=UAS.ScannerCount+1;
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}
	
	public void e_LTPassNoScope() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")&& Scope.equals("NotScanned")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			//Thread.sleep(2000);
				conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation,Fac.Name from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2);
					Facility= Scanner_ID_rs.getString(3);
					FacilityName = Scanner_ID_rs.getString(4);
				}		
				Scanner_ID_rs.close();
				//System.out.println("Scanner= "+Scanner);
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
	   		Expected="Verifying Location Dashboard default screen.";
	   		LD_A.ClickLocation(LocationID_PK);
	   		ResultLocationFacility=LD_V.VerifyLocationFacility(Scanner,FacilityName);
			System.out.println(Scenario+" ResultLocationFacility:  "+ResultLocationFacility);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLocationFacility);
	   		Message="Scan the scope.";
			ResultMessage=LD_V.VerifyMessage(Message);
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
}
   		
   		//Scan the Test Result
   		Expected="Verifying Location Dashboard after scanning Leak Test Pass without scanning a scope first.";
		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
		if(Res){
			LeakTest="Scanned";
			LeakTestRes="Pass";
		}
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		Message="Scan the scope.";
		ResultMessage=LD_V.VerifyMessage(Message);
		UAS.ScannerCount=UAS.ScannerCount+1;
		Description="Scan of Test Result - Leak Test Pass is done";
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}
	
	public void e_MCStart() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		//Scan the Workflow Event
   		Expected="Verifying Location Dashboard after scanning MC Start (when a scope was scanned).";
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		if(Res){
			MCStart="Scanned";
		}
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		Message="Clean the scope following\nrecommended protocol.\nScan Manual Clean Complete\nwhen finished.";
		Message=String.format(Message);
		ResultMessage=LD_V.VerifyMessage(Message);
		if(Scope.equals("Scanned")){
			try{ //Get a value that exists in Unifia to modify.
				Thread.sleep(2000);
				conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
				stmt="select LastUpdatedDateTime,AssociationID_FK from ItemHistory where ScanItemTypeID_FK=7 and ScanItemID_FK =1 and LastUpdatedDateTime=(Select Max(LastUpdatedDateTime) from ItemHistory)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				MCStart_rs = statement.executeQuery(stmt);
				while(MCStart_rs.next()){
					MCStartTime = formatter.parse(MCStart_rs.getString(1));//the first variable in the set is the LastUpdatedDateTime row in the database.
					AssociationID=MCStart_rs.getString(2);//the Second variable in the set is the AssociationID row in the database.
				}		
				MCStart_rs.close();
				//System.out.println("MC Start Time= "+MCStartTime);
				statement.close();
				conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		}
		BottomMC="Scan Manual Clean Complete.";

		UAS.ScannerCount=UAS.ScannerCount+1;
		Description="Scan of Workflow Event - Manual Clean Start is done";
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomMC=LD_V.VerifyBottomMCEnd(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}
	
	public void e_MCEnd() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		Thread.sleep(60000);//waiting for 1 minute to get the time difference between Manual clean Start and Manual clean End
		//Scan the Workflow Event
   		Expected="Verifying Location Dashboard after scanning MC End (when a scope was scanned and MC End was scanned).";
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
		if(Res){
			MCEnd="Scanned";
		}
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}

		if(Scope.equals("Scanned")){
			try{ //Get a value that exists in Unifia to modify.
				Thread.sleep(2000);
				conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
				stmt="select LastUpdatedDateTime from ItemHistory where ScanItemTypeID_FK=7 and ScanItemID_FK =2 and AssociationID_FK="+AssociationID+" and LastUpdatedDateTime=(Select Max(LastUpdatedDateTime) from ItemHistory where ScanItemTypeID_FK=7 and ScanItemID_FK =2)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				MCEnd_rs = statement.executeQuery(stmt);
				while(MCEnd_rs.next()){
					MCEndTime = formatter.parse(MCEnd_rs.getString(1)); //the first variable in the set is the LastUpdatedDateTime row in the database.
				}		
				MCEnd_rs.close();
				//System.out.println("MC End Time= "+MCEndTime);
				statement.close();
				conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		Description="Scan of Workflow Event - Manual Clean End is done";
		Message="Scan the scope.";
		StaffScan="";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName="Scan scope";

		ResultMessage=LD_V.VerifyMessage(Message);

		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}
	
	
	public void e_MCStartNoScope() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")&& Scope.equals("NotScanned")&& LeakTest.equals("NotScanned")){
	   		try{ //Get a value that exists in Unifia to modify.
				conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation,Fac.Name from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); 
					Facility = Scanner_ID_rs.getString(3); 
					FacilityName = Scanner_ID_rs.getString(4); 
				}		
				Scanner_ID_rs.close();
				//System.out.println("Scanner= "+Scanner);
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
	   		Expected="Verifying Location Dashboard default screen.";
	   		LD_A.ClickLocation(LocationID_PK);
	   		ResultLocationFacility=LD_V.VerifyLocationFacility(Scanner,FacilityName);
			System.out.println(Scenario+" ResultLocationFacility:  "+ResultLocationFacility);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLocationFacility);
	   		Message="Scan the scope.";
			ResultMessage=LD_V.VerifyMessage(Message);
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);

			//Scan the Workflow Event
	   		Expected="Verifying Location Dashboard after scanning MC Start without a scope scanned.";
			Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
			if(Res){
				MCStart="Scanned";
			}
			if (UAS.ScannerCount.equals(0)){			
				Thread.sleep(20000);
			}else{				
				Thread.sleep(1000);
			}
			Message="Scan the scope.";
			ResultMessage=LD_V.VerifyMessage(Message);

			UAS.ScannerCount=UAS.ScannerCount+1;
			Description="Scan of Workflow Event - Manual Clean Start is done";
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
			
			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
   		}
	}
	
	public void e_MCEndNoScope() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")&& Scope.equals("NotScanned")&& LeakTest.equals("NotScanned")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			//Thread.sleep(2000);
				conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation,Fac.Name from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2);
					Facility = Scanner_ID_rs.getString(3);
					FacilityName = Scanner_ID_rs.getString(4); 
				}		
				Scanner_ID_rs.close();
				//System.out.println("Scanner= "+Scanner);
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
	   		Expected="Verifying Location Dashboard default screen.";
	   		LD_A.ClickLocation(LocationID_PK);
	   		ResultLocationFacility=LD_V.VerifyLocationFacility(Scanner,FacilityName);
			System.out.println(Scenario+" ResultLocationFacility:  "+ResultLocationFacility);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLocationFacility);
	   		Message="Scan the scope.";
			ResultMessage=LD_V.VerifyMessage(Message);
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);

	   		//Scan the Workflow Event
	   		Expected="Verifying Location Dashboard after scanning MC End without a scope scanned.";
			Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
			if(Res){
				MCEnd="Scanned";
			}
			if (UAS.ScannerCount.equals(0)){			
				Thread.sleep(20000);
			}else{				
				Thread.sleep(1000);
			}
			Message="Scan the scope.";
			ResultMessage=LD_V.VerifyMessage(Message);

			UAS.ScannerCount=UAS.ScannerCount+1;
			Description="Scan of Workflow Event - Manual Clean End is done";
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
			
			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
		}
	}
	
	public void e_MCStartNoLTRes() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		if(LeakTest.equals("NotScanned")){
			//Scan the Workflow Event
	   		Expected="Verifying Location Dashboard after scanning MC Start with a scope scanned but LT wasn't scanned.";

			Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
			if(Res){
				MCStart="Scanned";
			}
			if (UAS.ScannerCount.equals(0)){			
				Thread.sleep(20000);
			}else{				
				Thread.sleep(1000);
			}
			Message="Conduct leak test then scan\n\"Leak Test Passed\"\nor \"Leak Test Failed\"";
			Message=String.format(Message);			
			ResultMessage=LD_V.VerifyMessage(Message);

			try{ //Get a value that exists in Unifia to modify.
				Thread.sleep(2000);
				conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
				stmt="select LastUpdatedDateTime,AssociationID_FK from ItemHistory where ScanItemTypeID_FK=7 and ScanItemID_FK =1 and LastUpdatedDateTime=(Select Max(LastUpdatedDateTime) from ItemHistory)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				MCStart_rs = statement.executeQuery(stmt);
				while(MCStart_rs.next()){
					MCStartTime = formatter.parse(MCStart_rs.getString(1));//the first variable in the set is the MCStartTime row in the database.
					AssociationID=MCStart_rs.getString(2);//the first variable in the set is the AssociationID row in the database.
				}		
				MCStart_rs.close();
				//System.out.println("MC Start Time= "+MCStartTime);
				statement.close();
				conn.close();
	   			}
				catch (SQLException ex){
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());	
				}
			UAS.ScannerCount=UAS.ScannerCount+1;
			Description="Scan of Workflow Event - Manual Clean Start is done";
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
		}
	}
	
	public void e_MCEndNoMCStart() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if (MCStart.equals("NotScanned") && (LeakTest.equals("Scanned"))) {
			// Scan the Workflow Event
	   		Expected="Verifying Location Dashboard after scanning MC End with a scope scanned but no MC Start scan.";
			Res = EM_A.ScanItem(Scanner, "Workflow Event", "","Manual Clean End", "");
			if (Res) {
				MCEnd = "Scanned";
			}
			if (UAS.ScannerCount.equals(0)){			
				Thread.sleep(20000);
			}else{				
				Thread.sleep(1000);
			}
			Message="Scan the scope.";
			ScopeName="Scan scope";
			StaffScan="";
			StaffNameTop="Scan Staff ID";
			StaffNameBottom="Scan Badge";
			TopLT="Leak Test Not Started";
			BottomLT="Scan Pass/Fail";
			BottomMC="Scan Manual Clean Start";

			UAS.ScannerCount = UAS.ScannerCount + 1;
			Description = "Scan of Workflow Event - Manual Clean End is done";
			ResultMessage=LD_V.VerifyMessage(Message);
			System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);

			ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
			System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

			ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
			System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

			ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
			System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

			ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
			System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

			ResultTopLT=LD_V.VerifyTopLT(TopLT);
			System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

			ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
			System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

			ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
			System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
			
			ScopeName=ScanScope;
		}			
	}
	
	public void e_Reset(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="Scan scope";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		Message="";	
		StaffScan="";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName=ScanScope;		
	}
	
	public void e_ResetNS5(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="Scan scope";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		Message="";				
		StaffScan="";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName=ScanScope;
	}
	
	public void e_ResetNS4(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="Scan scope";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		Message="";				
		StaffScan="";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName=ScanScope;
	}
	public void e_ResetNS3(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="Scan scope";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		Message="";				
		StaffScan="";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName=ScanScope;
	}
	public void e_ResetNS2(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="Scan scope";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		Message="";				
		StaffScan="";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName=ScanScope;
	}
	
	public void e_ResetNS(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="Scan scope";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		Message="";				
		StaffScan="";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName=ScanScope;
	}
	
	public void e_MoreThan1hr() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ScopeName=ScanScope;
		Boolean Res;
   		try{ //Get a value that exists in Unifia to modify.
   			Thread.sleep(2000);
   			conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
			Statement update = conn.createStatement();
			update.execute("update itemhistory set ReceivedDateTime = DATEADD(hh,-1,GETDATE()),LastUpdatedDateTime = DATEADD(hh,-1,GETDATE()), ProcessedDateTime = DATEADD(hh,-1,GETDATE()) where ScanItemId_FK=2 and ScanItemTypeID_FK=7  and ItemHistoryID_PK=(Select max (ItemhistoryID_PK) from itemhistory where scanItemID_fK=2 and ScanItemTypeID_FK=7 )");//Updating the time so as to get the time difference between Manual Clean End and scope scan
			update.close();
   		}catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}		
   		Expected="Verifying Location Dashboard after scanning a scope that has been MC more than 60 min ago.";

   		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
   		Message="Scan your badge.";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;

		ResultMessage=LD_V.VerifyMessage(Message);
		Description="Scan of Scope '" +ScanScope+"' is done";
		System.out.println(Scenario+" ResultMessage:  "+ResultMessage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMessage);
		
		ResultTopScope=LD_V.VerifyTopScopeName(ScopeName);
		System.out.println(Scenario+" ResultTopScope:  "+ResultTopScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopScope);

		ResultBottomScope=LD_V.VerifyBottomScopeName(ScopeName);
		System.out.println(Scenario+" ResultBottomScope:  "+ResultBottomScope);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomScope);

		ResultTopStaff=LD_V.VerifyTopStaff(StaffNameTop);
		System.out.println(Scenario+" ResultTopStaff:  "+ResultTopStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopStaff);

		ResultBottomStaff=LD_V.VerifyBottomStaff(StaffNameBottom);
		System.out.println(Scenario+" ResultBottomStaff:  "+ResultBottomStaff);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomStaff);

		ResultTopLT=LD_V.VerifyTopLT(TopLT);
		System.out.println(Scenario+" ResultTopLT:  "+ResultTopLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultTopLT);

		ResultBottomLT=LD_V.VerifyBottomLT(BottomLT);
		System.out.println(Scenario+" ResultBottomLT:  "+ResultBottomLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomLT);

		ResultBottomMC=LD_V.VerifyBottomMCStart(BottomMC);
		System.out.println(Scenario+" ResultBottomMC:  "+ResultBottomMC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultBottomMC);
	}
	
	public void e_ResetTime(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="Scan scope";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		Message="";				
		StaffScan="";
		StaffNameTop="Scan Staff ID";
		StaffNameBottom="Scan Badge";
		TopLT="Leak Test Not Started";
		BottomLT="Scan Pass/Fail";
		BottomMC="Scan Manual Clean Start";
		ScopeName=ScanScope;
	}
	public void e_Nav1(){
		System.out.println(getCurrentElement().getName());
	}
	public void e_Nav2(){
		System.out.println(getCurrentElement().getName());
	}
		
	public void v_SoiledRoom() throws InterruptedException{
		Scenario=Scenario+1;
		Description ="Start of new Scenario "+Scenario;
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",UAS.TestCaseNumber);
			UAS.TestCaseNumber=UAS.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",UAS.TestCaseNumber);
			LD_A.ClickHome();
		}
   		IP_A.Click_LD();
		System.out.println(getCurrentElement().getName());

	}
	
	public void v_ScanScope() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//System.out.println("Scope is "+Scope);		
		//Verify in DB
		Expected="v_ScanScope: "+ScanScope+" scanned into "+Scanner;
		ExpectedCycleEvent="Scope in Sink";
		Scope_IH=IHV.GetItemHistoryData(UAS.connstring, Scanner);
		ScopeInIH=Scope_IH[0];
		ScopeInAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeInIH+" = Scope into Sink Area- ItemHistory_PK");
		//ScopeStatus and location
		Scope_Stat=IHV.Scp_State_Loc(UAS.connstring, ScanScope);
		ActualScopeState=Scope_Stat[0];
		ScopeInLoc=Scope_Stat[1];
		ActualOtherScopeState=Scope_Stat[2];
		ActualSubloc=Scope_Stat[3];
		
		StaffPK=0;
		ExpectedState="0";

		try{
			conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
			stmt="select OtherScopeStateID_FK from ScopeStatus where ScopeID_FK="+ScopeID+";"; //put sql statement here to find ID
			//System.out.println(stmt);
			Statement statement = conn.createStatement(); //OtherScopeState_rs
			OtherScopeState_rs = statement.executeQuery(stmt);
			while(OtherScopeState_rs.next()){
				OtherScopeStateID = OtherScopeState_rs.getInt(1);
			}		
			OtherScopeState_rs.close();
			//System.out.println("OtherScopeStateID= "+OtherScopeStateID);
			statement.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}

		ExpectedCabinet="0";
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(UAS.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		
		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultSR="ResultScopeInCycle= "+ResultScopeInCycle+". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState;
		//System.out.println(Scenario+":  "+ResultSR);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
		
	}
	
	public void v_ScanStaff() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//System.out.println("Staff is "+StaffID);
		//DB Verification
		Expected="v_ScanStaff: "+StaffID+" scanned into "+Scanner;
		//Staff_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Scanner);
		ExpectedCycleEvent="SoiledRoomStaff";
		Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Scanner);			
		StaffInIH=Staff_IH[0];
		StaffIn_Assoc=Staff_IH[1];
		ActualCycleEvent=Staff_IH[5];
		
		ResultStaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);

		ResultStaffIn=IHV.Result_Same_Assoc(ScopeInAssociationID,StaffIn_Assoc)+" for staff and scope into Sink Area.";
		
		StaffPK=IHV.GetStaffPK(UAS.connstring,StaffID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(UAS.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		
		ResultStaffIn=ResultStaffIn+" ResultStaffCycle="+ResultStaffCycle+". ResultLastStaff="+ResultLastStaff;
		//System.out.println(Scenario+":  "+ResultStaffIn);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffIn);	

	}
	
	public void v_LeakTest() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(Scope.equals("Scanned")){
			//Verify in DB
			Expected="v_LeakTest: Leak Test "+LeakTestRes+" scanned into "+Scanner;
			ExpectedCycleEvent="Leak Test";
			LT_IH=IHV.GetItemHistoryData(UAS.connstring, Scanner);
			LeakTestIH=LT_IH[0];
			LeakTestAssocID=LT_IH[1];
			ActualCycleEvent=LT_IH[5];
			//System.out.println(LeakTestIH+" = Leak Test in Sink Area- ItemHistory_PK");
			ExpectedState="0";
			OtherScopeStateID=0;

			ExpectedCabinet="0";
			ResultLeakTestCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			ResultLeakTest_SameAssocID=IHV.Result_Same_Assoc(ScopeInAssociationID,LeakTestAssocID)+" for scope into Sink Area and Leaktest.";
			ResultSR="ResultLeakTestCycle= "+ResultLeakTestCycle+". ResultLeakTest_SameAssocID="+ResultLeakTest_SameAssocID;
			//System.out.println(Scenario+":  "+ResultSR);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
			Description="Verify Scope Record Management of "+ScanScope+" with leak test ";
			
		}else if(Scope.equals("NotScanned")){
			//Verify DB
			Expected="v_LeakTest: Leak Test Pass/Fail scanned by skipping Scope scan into "+Scanner;
			ExpectedCycleEventID=0;
			ExpectedScanItemType="Test Result";
			ExpectedAssociationID=0;
					
			LT_IH=IHV.GetItemHistoryDataNoAssociationID(UAS.connstring, Scanner);
			LeakTestIH=LT_IH[0];
			NoLeakTestTAssocID=LT_IH[1];
			ActualCycleEventID=LT_IH[3];
			ActualScanItemType=LT_IH[4];
			//System.out.println(LeakTestIH+" = Leak Test in Sink Area- ItemHistory_PK");
			ResultLeakTestBlankAssocID=IHV.Result_AssociationID(NoLeakTestTAssocID,Integer.toString(ExpectedAssociationID));
			ResultLeakTestCycle=IHV.Result_CycleEvent(ActualCycleEventID, Integer.toString(ExpectedCycleEventID));
			ResultLeakTestTScanItemType=IHV.Result_ScanItemType(ActualScanItemType,ExpectedScanItemType);
			ResultSR="ResultLeakTestBlankAssocID= "+ResultLeakTestBlankAssocID+". ResultLeakTestCycle= "+ResultLeakTestCycle+". ResultLeakTestTScanItemType="+ResultLeakTestTScanItemType;
			//System.out.println(Scenario+":  "+ResultSR);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
		}
	}
	
	public void v_ManualCleanStart() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(Scope.equals("Scanned")){
			if(LeakTest.equals("Scanned")){
				//DB
				Expected="v_ManualCleanStart: Manual Clean Start scanned into "+Scanner;
				ExpectedCycleEvent="Manual Clean Start";
				MCS_IH=IHV.GetItemHistoryData(UAS.connstring, Scanner);
				ManCleanStartIH=MCS_IH[0];
				ManCleanStartAssocID=MCS_IH[1];
				ActualCycleEvent=MCS_IH[5];
				//System.out.println(ManCleanStartIH+" = Manual Clean Start in Sink Area- ItemHistory_PK");
				ResultMCStartCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
				ResultMCStartAssocID=IHV.Result_Same_Assoc(ManCleanStartAssocID,ScopeInAssociationID) +" for scope into Sink Area and Manual Clean Start.";
				ResultSR="ResultMCStartCycle= "+ResultMCStartCycle+". ResultMCStart_AssocID="+ResultMCStartAssocID;
				//System.out.println(Scenario+":  "+ResultSR);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
			}else if(LeakTest.equals("NotScanned")){
				Expected="v_ManualCleanStart: Manual Clean Start scanned by skipping Leak Test into "+Scanner;
				ExpectedCycleEvent="Manual Clean Start";
				MCS_IH=IHV.GetItemHistoryData(UAS.connstring, Scanner);
				ManCleanStartIH=MCS_IH[0];
				ManCleanStartAssocID=MCS_IH[1];
				ActualCycleEvent=MCS_IH[5];
				//System.out.println(ManCleanStartIH+" = Manual Clean Start in Sink Area- ItemHistory_PK");
				ResultMCStartCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
				ResultMCStartAssocID=IHV.Result_Same_Assoc(ManCleanStartAssocID,ScopeInAssociationID)+" for scope into Sink Area and Manual Clean Start.";
				ResultSR="ResultMCStartCycle= "+ResultMCStartCycle+". ResultMCStart_AssocID="+ResultMCStartAssocID;
				//System.out.println(Scenario+":  "+ResultSR);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
			}
			//SRM Validation when LTPassorFail
			String []MCStartTime=IHV.GetMCStartTime(UAS.connstring, ManCleanStartAssocID);
			String MCStartDateTime=MCStartTime[0];
			String result_MCStart="";
		Expected =Description;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MCStart);
		
	}else if(Scope.equals("NotScanned")){
			//Verify DB
			Expected="v_ManualCleanStart: Manual Clean Start scanned by skipping Scope scan into "+Scanner;
			ExpectedAssociationID=0;
			ExpectedCycleEventID=0;
			ExpectedScanItemType="Workflow Event";
			MCS_IH=IHV.GetItemHistoryDataNoAssociationID(UAS.connstring, Scanner);
			ManCleanStartIH=MCS_IH[0];
			ManCleanStartAssocID=MCS_IH[1];
			ActualCycleEventID=MCS_IH[3];
			ActualScanItemType=MCS_IH[4];
			//System.out.println(ManCleanStartIH+" = Manual Clean Start in Sink Area- ItemHistory_PK");			
			ResultMCStartAssocID=IHV.Result_AssociationID(ManCleanStartAssocID,Integer.toString(ExpectedAssociationID));
			ResultMCStartCycle=IHV.Result_CycleEvent(ActualCycleEventID, Integer.toString(ExpectedCycleEventID));
			ResultMCStartScanItemType=IHV.Result_ScanItemType(ActualScanItemType,ExpectedScanItemType);
			ResultSR="ResultMCStartAssocID= "+ResultMCStartAssocID+". ResultMCStartCycle= "+ResultMCStartCycle+". ResultMCStartScanItemType="+ResultMCStartScanItemType;
			//System.out.println(Scenario+":  "+ResultSR);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
		}
	}
	
	public void v_ManualCleanEnd() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(Scope.equals("Scanned")){
			if(MCStart.equals("Scanned")){
				long diff=MCEndTime.getTime()-MCStartTime.getTime();//Calculating time difference between MCStartTime and MCEndTime
				long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
				if (diffInMinutes == 0) {
					MCTimePeriod = "00:00";
				} else if (diffInMinutes < 10) {
					MCTimePeriod = "00:0" + Long.toString(diffInMinutes);
				} else {
					Long Hours = diffInMinutes / 60;
					Long Minutes = diffInMinutes % 60;
					if (Hours < 10 && Minutes < 10) {
						MCTimePeriod = "0" + Long.toString(Hours) + ":0"
								+ Long.toString(Minutes);
					} else if (Hours > 10 && Minutes < 10) {
						MCTimePeriod = Long.toString(Hours) + ":0"
								+ Long.toString(Minutes);
					} else if (Hours > 10 && Minutes > 10) {
						MCTimePeriod = Long.toString(Hours)
								+ ":"+ Long.toString(Minutes);
					} else if (Hours < 10 && Minutes > 10) {
						MCTimePeriod = "0" + Long.toString(Hours) + ":"
								+ Long.toString(Minutes);
					}
				}
				//DB
				Expected="v_ManualCleanEnd: Manual Clean End scanned into "+Scanner;
				ExpectedCycleEvent="Manual Clean End";
				MCE_IH=IHV.GetItemHistoryData(UAS.connstring, Scanner);
				ManCleanEndIH=MCE_IH[0];
				ManCleanEndAssocID=MCE_IH[1];
				ActualCycleEvent=MCE_IH[5];
				//System.out.println(ManCleanEndIH+" = Manual Clean End in Sink Area- ItemHistory_PK");
				//ScopeStatus and location
				Scope_Stat=IHV.Scp_State_Loc(UAS.connstring, ScanScope);
				ActualScopeState=Scope_Stat[0];
				ScopeInLoc=Scope_Stat[1];
				ActualOtherScopeState=Scope_Stat[2];
				ActualSubloc=Scope_Stat[3];
				
				ExpectedState="2";
				OtherScopeStateID=0;

				ResultMCEndAssocID=IHV.Result_Same_Assoc(ScopeInAssociationID,ManCleanEndAssocID)+" for scope into Sink Area and Manual Clean End.";
				ResultMCEndCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
				ResultMCEndScopeState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
				ResultSR="ResultMCEndAssocID="+ResultMCEndAssocID+". ResultMCEndCycle= "+ResultMCEndCycle+". ResultMCEndScopeState="+ResultMCEndScopeState;
				//System.out.println(Scenario+":  "+ResultSR);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
				
				//SRM validations
				// To be done MCStart and MCEnd Date
				String []MCStartEndTime=IHV.GetMCStartEndTime(UAS.connstring, ManCleanEndAssocID);
				String MCStartTime=MCStartEndTime[0];
				String MCEndTime=MCStartEndTime[1];
				
			}else if(MCStart.equals("NotScanned")){
				//DB
				Expected="v_ManualCleanEnd: Manual Clean End scanned by skipping Manual Clean Start into "+Scanner;
				ExpectedCycleEvent="Manual Clean End";
				MCE_IH=IHV.GetItemHistoryData(UAS.connstring, Scanner);
				ManCleanEndIH=MCE_IH[0];
				ManCleanEndAssocID=MCE_IH[1];
				ActualCycleEvent=MCE_IH[5];
				//System.out.println(ManCleanEndIH+" = Manual Clean End into Sink Area- ItemHistory_PK");
				//ScopeStatus and location
				Scope_Stat=IHV.Scp_State_Loc(UAS.connstring, ScanScope);
				ActualScopeState=Scope_Stat[0];
				ScopeInLoc=Scope_Stat[1];
				ActualOtherScopeState=Scope_Stat[2];
				ActualSubloc=Scope_Stat[3];
				
				ExpectedState="2";
				OtherScopeStateID=0;
			
				ResultMCEndAssocID=IHV.Result_Same_Assoc(ScopeInAssociationID,ManCleanEndAssocID)+" for scope into Sink Area and Manual Clean End.";
				ResultMCEndCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
				ResultMCEndScopeState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
				ResultSR="ResultMCEndAssocID="+ResultMCEndAssocID+". ResultMCEndCycle= "+ResultMCEndCycle+". ResultMCEndScopeState="+ResultMCEndScopeState;
				//System.out.println(Scenario+":  "+ResultSR);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
				
				//SRM validations
				String MCEndTime=IHV.GetMCEndTime(UAS.connstring, ManCleanEndAssocID);
			}
		}else if(Scope.equals("NotScanned")){
			//Verify DB
			Expected="v_ManualCleanEnd: Manual Clean End scanned by skipping Scope scan into "+Scanner;
			ExpectedAssociationID=0;
			ExpectedCycleEventID=0;
			ExpectedScanItemType="Workflow Event";
			MCE_IH=IHV.GetItemHistoryDataNoAssociationID(UAS.connstring, Scanner);
			ManCleanEndIH=MCE_IH[0];
			ManCleanEndAssocID=MCE_IH[1];
			ActualCycleEventID=MCE_IH[3];
			ActualScanItemType=MCE_IH[4];
			//System.out.println(ManCleanEndIH+" = Manual Clean End in Sink Area- ItemHistory_PK");						
			ResultMCEndAssocID=IHV.Result_AssociationID(ManCleanEndAssocID,Integer.toString(ExpectedAssociationID));
			ResultMCEndCycle=IHV.Result_CycleEvent(ActualCycleEventID, Integer.toString(ExpectedCycleEventID));
			ResulMCEndScanItemType=IHV.Result_ScanItemType(ActualScanItemType,ExpectedScanItemType);
			ResultSR="ResultLTAssocID= "+ResultMCEndAssocID+". ResultMCEndCycle= "+ResultMCEndCycle+". ResulMCEndScanItemType="+ResulMCEndScanItemType;
			//System.out.println(Scenario+":  "+ResultSR);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
		}
	}
	
	public void v_MoreThan1hr() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Boolean Res;
		String MinDiff="";
   		try{ //Get a value that exists in Unifia to modify.
   			Thread.sleep(2000);
   			conn= DriverManager.getConnection(UAS.url, UAS.user, UAS.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			
			stmt="select datediff(mi, Itemhistory.ReceivedDateTime,GETUTCDATE()) from Itemhistory where ScanItemId_FK=2 and "
				+ "ScanItemTypeID_FK=7  and ItemHistoryID_PK=(Select max (ItemhistoryID_PK) from itemhistory where scanItemID_fK=2 "
				+ "and ScanItemTypeID_FK=7 )"; //put sql statement here to find ScopeName
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				MinDiff = Scope_rs.getString(1); //the first variable in the set is the TimeDifference row in the database.	
			}		
			Scope_rs.close();
			statement.close();
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
   		
		
		//Manual clean Start
		UAS.ScannerCount=UAS.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		UAS.ScannerCount=UAS.ScannerCount+1;
		//manual clean end
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
		if (UAS.ScannerCount.equals(0)){			
			Thread.sleep(20000);
		}else{				
			Thread.sleep(1000);
		}
		//Verify in DB
		Expected="v_MoreThan1hr: "+ScanScope+" scanned into "+Scanner+" after more than 1 hour";
		ExpectedCycleEvent="Manual Clean End";
		Scope_IH=IHV.GetItemHistoryData(UAS.connstring, Scanner);
		ScopeInIH=Scope_IH[0];
		ScopeInAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeInIH+" = Scope into Sink Area- ItemHistory_PK");
		//ScopeStatus and location
		Scope_Stat=IHV.Scp_State_Loc(UAS.connstring, ScanScope);
		ActualScopeState=Scope_Stat[0];
		ScopeInLoc=Scope_Stat[1];
		ActualOtherScopeState=Scope_Stat[2];
		ActualSubloc=Scope_Stat[3];
		
		ExpectedState="2";
		OtherScopeStateID=0;
		ExpectedCabinet="0";
		ExpectedCycleCount="1";
		
		MT1h=IHV.GetCycleID(UAS.connstring, Scanner);
		ActualCycleCount=MT1h[3];
		ResultScopeInCycle1h=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ResultScopeInLoc1h=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState1h=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultCycleCountMoreThan1hr=IHV.Result_CycleCount(ActualCycleCount,ExpectedCycleCount);
		ResultSR="ResultScopeInCycle1h= "+ResultScopeInCycle1h+". ResultScopeInLoc1h ="+ResultScopeInLoc1h+". ResultScopeInState1h="+ResultScopeInState1h+"ResultCycleCountMoreThan1hr="+ResultCycleCountMoreThan1hr;
		//System.out.println(Scenario+":  "+ResultSR);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
	}
	public void v_Nav1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Nav2(){
		//System.out.println(getCurrentElement().getName());
	}


}
