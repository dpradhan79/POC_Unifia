package ScannerMessages.ProcedureRoom;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.james.mime4j.field.datetime.DateTime;
import org.graphwalker.core.machine.ExecutionContext;

import java.awt.AWTException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;  
import java.util.Date;

import TestFrameWork.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminLocationPage.Location_Actions;
import TestFrameWork.UnifiaAdminLocationPage.Location_Verification;
import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions;
import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Verification;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminScannerPage.*; 
import TestFrameWork.UnifiaAdminAccessPointPage.*;
import Regression.MultiRoom.MultiRoomController_API;

public class RegressionProcedureRoom_GWAPI  extends ExecutionContext{
	
	private TestFrameWork.Emulator.Emulator_Actions EM_A;
	private TestFrameWork.Emulator.Emulator_Verifications EM_V;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestFrameWork.TestHelper TH;
	private GeneralFunc gf;
	private 	TestFrameWork.Emulator.GetIHValues IHV;
	private Regression.MultiRoom.MultiRoomController_API Multi;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private TestFrameWork.Unifia_IP.IP_Verification IP_V;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	private static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	private static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;

	private Boolean MultiRoom=false;
	private String Scope;
	private String Scanner="",Facility="";
	private String Culture="NA";
	private String ScanScope;
	private Boolean Res;
	private String ScanScopeSerNum;
	private String ScopeHangTime;
	public static String actualResult="\t\t\t ScanMessages_ProcedureRoom_TestSummary \n"; 
	private String Description;
	private String ForFileName;
	private String FileName="";
	private String Expected;

	private String TestResFileName="Regression_ScanMessages_ProcedureRoom_TestSummary_";
	private Integer KE=0;
	private String OtherKey="No";
	private Integer OKey=1;
	private String ScanStaff="", ScanStaffID="", ScanPatient="";
	private String ScanPhysician="", ScanPhysicianID="";
	private String StartProcScan="";
	private String Room="";
	private Integer Scenario=0;
	private String StaffID="";
	private String StaffFirstName="";
	private String StaffLastName="";
	
	private Integer LocationID_PK;
	private Integer ReproOut_AssociationID;
	private Integer ScopeID;
	//private String Location;
	private Integer AssociationID;
	private Integer CycleID;
	private Integer CurrentPatientPK;
	
	private String Date="";
	private String Time="";
	
	private String Scope_IH[];
	private String Staff_IH[];
	private String Culture_IH[];

	private String ScopeInIH;
	private String ScopeInAssociationID;
	private String StaffInIH;
	private String StaffIn_Assoc;
	private String CultureIH;
	private String CultureResult_Assoc;
	private String Culture_RI;
	private int CultureAssociationID;
	private String CultureNM;
	private String CultureResult;

	private String AdmitPatient_IH;
	private String AdmitPatient_Assoc;
	private String ResultAdmitPatient;
	
	private String Patient_IH;
	private String Patient_Assoc;
	private String ResultPatient;
	private String ResultPatientCycle;

	private String Physician_IH;
	private String Physician_Assoc;
	private String ResultPhysician;
	private String ResultPhysicianCycle;

	private String ProcStart_IH;
	private String ProcStart_Assoc;
	private String ResultProcStart;
	private String ResultProcStartCycle;
	private String ProcStart;

	private String ProcEnd_IH;
	private String ProcEnd_Assoc;
	private String ResultProcEnd;
	private String ResultProcEndCycle;

	private String RoomStatus_IH;
	private String RoomStatus_Assoc;
	private String ResultRoomStatus;
	private String ResultRoomStatusCycle;

	private String ScopeOutIH;
	private String ScopeOutAssociationID;
	private String StaffOutIH;
	private String StaffOut_Assoc;
	
	private String ResultScopeInCycle;
	private String ResultScopeInLoc;
	private String ResultScopeInState;
	private String ResultPRIn;
	private String ResultStaffIn;
	private String StaffIn_RI;
	private String ResultIn_RI;
	private String ResultCultureResult;
	private String ResultCulture_RI;

	private String ResultScopeOutCycle;
	private String ResultScopeOutLoc;
	private String ResultScopeOutState;
	private String ResultPROut;
	private String ResultStaffOut;
	private String StaffOut_RI;
	private String ResultOut_RI;

	private String ExpectedCycleEvent;
	private String ExpectedCabinet;
	private String ActualScopeState;
	private String ExpectedState;
	private int OtherScopeStateID;
	private String ActualSubloc;
	private String ActualOtherScopeState;
	private String ActualCycleEvent;
	private String ScopeInLoc;
	private String ScopeOutLoc;
	private String NoStaff="No", NoStaffClose="No";
	
	private String ResultRmState;
	private String ExpectedRmSt;
	
	private String ExpectedReproCount;
	private String ExpectedExamCount;

	private String ResultReproCount;
	private String ResultExamCount;
	private String ResultLastStaff;
	private int StaffPK=0;
	private int LastScanStaffID_FK;
	private String ActualReproCount;
	private String ActualExamCount;
	public String ResultScopeIn1StaffCycle="";
	public String ResultScopeOut1StaffCycle="";
	
	private Connection conn= null;
	private String stmt,stmt1;
	private ResultSet Staff_rs,Scanner_ID_rs,Patient_ID_rs,PRStatus_RS,Scope_ID_rs,Repro_rs,Scope_rs,PatientNew_RS, CultureAssociation;
	
	private String scopeRefNo="";
	private String scopeSerialNo="";
	private String scopeModel="";
	private String scopeExamTime="";
	private String [] temp= new String[2];
	private String OverallResult="Pass";
	private String ProcStartTime="";
	private String ProcEndTime="";
	private String cultureStatusResult="";
	private String CulturePath="";
	private String ScopePreclean="No";
	public static String result="";
	public String Changes="No";
	
	public void e_Start() throws InterruptedException{
		//empty edge for graphml navigation
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date); 
		FileName="CycleMgmt_ProcedureRoom_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		Scenario=0;
		// set all PR rooms to available:
		Room="Available";
		Scanner="Procedure Room 1";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 2";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 3";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 5";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 6";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		Scanner="Procedure Room 7";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 8";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 9";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="";

	}
	
	public void e_ScopeNoRep() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		CultureResult="No";
		try{ 
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    		stmt="select Name,SerialNumber,ScopeID_PK from scope where scopeid_PK not in "
    			 + "(select Scopeid_FK from scopestatus) and LastUpdatedDateTime="
    			 + "(Select Min(LastUpdatedDateTime) from Scope where scopeid_PK not in  (select Scopeid_FK from scopestatus))";

			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_ID_rs = statement.executeQuery(stmt);
			while(Scope_ID_rs.next()){
				ScanScope = Scope_ID_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanScopeSerNum = Scope_ID_rs.getString(2);
				ScopeID = Scope_ID_rs.getInt(3);
			}		
			Scope_ID_rs.close();
			//System.out.println("Scope= "+ScanScope);
			//System.out.println("AssociationID= "+AssociationID);
			
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
		
		//Get Scanner
		if (Scanner.equals("")){
			try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			//stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=2 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)"; //put sql statement here to find ID
			stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=2 and Loc.IsActive=1 and "
					+ "Loc.locationid_PK not in (Select locationid_FK from locationstatus where locationstateid_FK=12) and "
					+ "Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1 and "
					+ "locationid_PK not in (Select locationid_FK from locationstatus where locationstateid_FK=12))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				Scanner = Scanner_ID_rs.getString(2);
				Facility = Scanner_ID_rs.getString(3);
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
		}
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeKEError() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		CultureResult="No";
		try{ //Get a value that exists in Unifia to modify.	
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Scope.Name, Scope.SerialNumber, Status.ScopeID_FK from Scope join ScopeStatus Status on "
					+ "Scope.ScopeID_PK=Status.ScopeID_FK join ReprocessingStatus RS on Scope.ScopeID_PK=RS.ScopeID_FK "
					+ "where Status.ScopeStateID_FK=6 or Status.ScopeStateID_FK is null and Status.LocationID_FK in (51,52,53,54,55,56,81) and "
					+ "RS.ReprocessingStateID_FK=2 and status.LastUpdatedDateTime=(Select Min(SS.LastUpdatedDateTime) "
					+ "from ScopeStatus SS join ReprocessingStatus RS on SS.ScopeID_FK=RS.ScopeID_FK where "
					+ "ScopeStateID_FK=6 or Status.ScopeStateID_FK is null and SS.LocationID_FK in (51,52,53,54,55,56,81) and RS.ReprocessingStateID_FK=2)";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ScanScope = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanScopeSerNum = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
			}		
			Scope_rs.close();
			//System.out.println("Scope= "+ScanScope);
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();
			Statement statement1 = conn.createStatement();
			stmt1="select LocationID_PK, Name from Location where LocationTypeID_FK=2 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)"; //put sql statement here to find ID
			//System.out.println(stmt1);
			Statement update1 = conn.createStatement();
			Scanner_ID_rs= statement1.executeQuery(stmt1);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
			}		
			Scanner_ID_rs.close();
			//System.out.println("Location="+Scanner);
			update1.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
			update1.close();
			statement1.close();
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeCultResFail() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Scope="Scanned";
		Culture="Failed";
		CultureResult="No";
		try{ 
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
					+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
					+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK=7 and Stat.LocationID_FK in (31,32,33,34,81) "
					+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK =5 "
					+ "and OtherScopeStateID_FK =7 and LocationID_FK in (31,32,33,34,81))"; 
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_ID_rs = statement.executeQuery(stmt);
			while(Scope_ID_rs.next()){
				ScanScope = Scope_ID_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanScopeSerNum = Scope_ID_rs.getString(2);
				ScopeID = Scope_ID_rs.getInt(3);				
				AssociationID = Scope_ID_rs.getInt(4);
				CycleID= Scope_ID_rs.getInt(5);
			}		
			Scope_ID_rs.close();
			//System.out.println("Scope= "+ScanScope);
			//System.out.println("AssociationID= "+AssociationID);
			//System.out.println("CycleID= "+CycleID);
			
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
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
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=2 "
						+ "and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)";
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2);
					Facility= Scanner_ID_rs.getString(3);
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
			}
	   		try{ //Get a value that exists in Unifia to modify.
	   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	   			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Staff_rs = statement.executeQuery(stmt);
				while(Staff_rs.next()){
					ScanStaffID= Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
					StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
					StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
				}		
				Staff_rs.close();
				//System.out.println("StaffID = "+ScanStaffID);
				update.execute("Update Staff set LastUpdatedDateTime=GETUTCDATE() WHERE StaffID='"+ScanStaffID+"';");
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
	   		ScanStaff=StaffFirstName+" "+StaffLastName+"("+ScanStaffID+")";
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");


		//Make the scope to culture fail 
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Storage Area A", "Scope", "", ScanScope, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Storage Area A", "Key Entry", "", "", "1");
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Storage Area A", "Staff", "Tech", ScanStaff, "");

		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Storage Area A", "Scope", "", ScanScope, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Storage Area A", "Key Entry", "", "", "2");
		//System.out.println("Scope "+ScanScope+" is made to culture result failure state");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Storage Area A", "Staff", "Tech", ScanStaff, "");

		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeCultResPass() throws InterruptedException{
		Scope="Scanned";
		Culture="Obtained";
		CultureResult="No";
		//System.out.println(getCurrentElement().getName());
		try{ 
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
					+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
					+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (31,32,33,34) "
					+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK =5 "
					+ "and OtherScopeStateID_FK Is NULL and LocationID_FK in (31,32,33,34))";
    		
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_ID_rs = statement.executeQuery(stmt);
			while(Scope_ID_rs.next()){
				ScanScope = Scope_ID_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanScopeSerNum = Scope_ID_rs.getString(2);
				ScopeID = Scope_ID_rs.getInt(3);
				AssociationID = Scope_ID_rs.getInt(4);
				CycleID= Scope_ID_rs.getInt(5);
			}		
			Scope_ID_rs.close();
			//System.out.println("Scope= "+ScanScope);
			//System.out.println("AssociationID= "+AssociationID);
			//System.out.println("CycleID= "+CycleID);
			
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();
			
			//Get the hangTime
			stmt1="Select DateDiff(hh, IH.ReceivedDateTime, GETUTCDATE())/24 AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK "
					+ " where ScopeCycle.ScopeID_FK="+ScopeID+" and ScopeCycle.CycleID="+CycleID+" and IH.CycleEventID_FK=18";

			//System.out.println(stmt1);
			Statement statement1 = conn.createStatement();
			Repro_rs = statement1.executeQuery(stmt1);
			while(Repro_rs.next()){
				ScopeHangTime = Repro_rs.getString(1); //the first variable in the set is the ID row in the database.
				ReproOut_AssociationID = Repro_rs.getInt(2);
			}
			Repro_rs.close();
			//System.out.println("HangTime="+ScopeHangTime);
			//System.out.println("ReproOut_AssociationID= "+ReproOut_AssociationID);
			statement1.close();
			
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
				
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=2 "
						+ "and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)"; 
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2);
					Facility = Scanner_ID_rs.getString(3);
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
			}
		
		//Get the scopes which are having culture Res as pass
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeCultResNA() throws InterruptedException{
		CultureResult="Yes";
		//System.out.println(getCurrentElement().getName());
		Scope="Scanned";
		Culture="Awaiting";

		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
					+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
					+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK =7 and Stat.LocationID_FK in (31,32,33,34,81)"
					+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK =5 "
					+ "and OtherScopeStateID_FK =7 and LocationID_FK in (31,32,33,34,81))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_ID_rs = statement.executeQuery(stmt);
			while(Scope_ID_rs.next()){
				ScanScope = Scope_ID_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanScopeSerNum = Scope_ID_rs.getString(2);
				ScopeID = Scope_ID_rs.getInt(3);
				AssociationID = Scope_ID_rs.getInt(4);
				CycleID= Scope_ID_rs.getInt(5);
			}		
			Scope_ID_rs.close();
			//System.out.println("Scope= "+ScanScope);
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			
			stmt1="select AssociationID_FK from ItemHistory where ScanItemID_FK="+ScopeID+" and CycleEventID_FK=30";
			//System.out.println(stmt1);

			CultureAssociation=statement.executeQuery(stmt1);
			while(CultureAssociation.next()){
				CultureAssociationID = CultureAssociation.getInt(1);
			}
			update.close();
			statement.close();
			//System.out.println("CultureAssociationID= "+CultureAssociationID);
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		//Scanner="Procedure Room 3";
		if (Scanner.equals("")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=2 "
						+ "and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)"; 
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2);
					Facility = Scanner_ID_rs.getString(3);
				}		
				Scanner_ID_rs.close();
				//System.out.println("Scanner= "+Scanner);
				//System.out.println("LocationID_PK= "+LocationID_PK);
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
		}
		//scan in culture room again as from the db the correct msg is not displayed
		/**Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("CultureA", "Scope", "", ScanScope, "");
		//System.out.println(Res);**/
		//Get the scopes which are having culture not done
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeCultNotReq() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Scope="Scanned";
		Culture="NotApp";
		CultureResult="No";
		if(FileName.equalsIgnoreCase("")){

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date); 
			FileName="CycleMgmt_ProcedureRoom_Regression_";
			FileName=IHV.Start_Exec_Log(FileName);
			Unifia_Admin_Selenium.XMLFileName=FileName;
		}
		if(MultiRoom==false){
			try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
						+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
						+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (31,32,33,34) "
						+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK =5 "
						+ "and OtherScopeStateID_FK Is NULL and LocationID_FK in (31,32,33,34))";
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scope_ID_rs = statement.executeQuery(stmt);
				while(Scope_ID_rs.next()){
					ScanScope = Scope_ID_rs.getString(1); //the first variable in the set is the ID row in the database.
					ScanScopeSerNum = Scope_ID_rs.getString(2);
					ScopeID = Scope_ID_rs.getInt(3);
					AssociationID = Scope_ID_rs.getInt(4);
					CycleID= Scope_ID_rs.getInt(5);
				}		
				Scope_ID_rs.close();
				//System.out.println("Scope= "+ScanScope);
				//System.out.println("AssociationID= "+AssociationID);
				
				update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
				update.close();
				statement.close();
				
				//Get the hangTime
				stmt1="Select DateDiff(hh, IH.ReceivedDateTime, GETUTCDATE())/24 AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK "
						+ " where ScopeCycle.ScopeID_FK="+ScopeID+" and ScopeCycle.CycleID="+CycleID+" and IH.CycleEventID_FK=18";
		
				//System.out.println(stmt1);
				Statement statement1 = conn.createStatement();
				Repro_rs = statement1.executeQuery(stmt1);
				while(Repro_rs.next()){
					ScopeHangTime = Repro_rs.getString(1); //the first variable in the set is the ID row in the database.
					ReproOut_AssociationID = Repro_rs.getInt(2);
				}
				Repro_rs.close();
				//System.out.println("HangTime="+ScopeHangTime);
				//System.out.println("ReproOut_AssociationID= "+ReproOut_AssociationID);
				statement1.close();
				
				conn.close();
				}
				catch (SQLException ex){
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());	
				}
			
			//Get Scanner
			if (Scanner.equals("")){
				try{ //Get a value that exists in Unifia to modify.
				
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=2 and "
						+ "Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2);
					Facility = Scanner_ID_rs.getString(3);
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
			}
		}		
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff() throws InterruptedException{
		CultureNM="No";
		//System.out.println(getCurrentElement().getName());
   		try{ //Get a value that exists in Unifia to modify.
   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
   			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanStaffID= Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			Staff_rs.close();
			//System.out.println("StaffID = "+ScanStaffID);
			update.execute("Update Staff set LastUpdatedDateTime=GETUTCDATE() WHERE StaffID='"+ScanStaffID+"';");
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
   		ScanStaff=StaffFirstName+" "+StaffLastName+"("+ScanStaffID+")";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BackForEnterRes(){
		//KE=4;
		OtherKey="Yes";
		//System.out.println(getCurrentElement().getName());
		Description="Looping back for entering the correct option 1 or 2 or 3";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CultResPass() throws InterruptedException{
		CultureResult="Pass";

		KE=1;
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Key Entry", "", "", "1");
		//System.out.println(Res);
		Description="Key Entry  option '1' is selected  for scope  "+ ScanScope+" to specify culture Result is passed";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void e_CultResFail() throws InterruptedException{
		CultureResult="Fail";
		KE=2;
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Key Entry", "", "", "2");
		//System.out.println(Res);
		Description="Key Entry  option '2' is selected  for scope  "+ ScanScope+" to specify culture Result is failed";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void e_CultResNoRes() throws InterruptedException{
		CultureResult="No Results";
		KE=3;
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Key Entry", "", "", "3");
		//System.out.println(Res);
		Description="Key Entry  option '3' is selected  for scope  "+ ScanScope+" to specify No Results for culture test";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CultResOther() throws InterruptedException{
		CultureResult="Other";
		KE=4;
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Key Entry", "", "", "4");
		//System.out.println(Res);
		Description="Key Entry  option '4' is selected  for scope  "+ ScanScope+" which is not a correct option";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffCultResDone() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		CultureNM="Yes";
		//ScanStaff="Tech2 Tech2(T02)";
		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanStaffID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			Staff_rs.close();
			//System.out.println("StaffID = "+StaffID);
			update.execute("Update Staff set LastUpdatedDateTime=GETUTCDATE() WHERE StaffID='"+ScanStaffID+"';");
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
		ScanStaff=StaffFirstName+" "+StaffLastName+"("+ScanStaffID+")";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Patient() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//ScanPatient="PID010101";
		
		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
			stmt="Select PatientID_PK, PatientID from Patient where LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Patient)";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Patient_ID_rs = statement.executeQuery(stmt);
			while(Patient_ID_rs.next()){
				CurrentPatientPK = Patient_ID_rs.getInt(1); 
				ScanPatient = Patient_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
			}		
			Patient_ID_rs.close();
			//Fetch the decrypted patient id 
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			//System.out.println("stmtPatEncr="+stmtPatEncr);
	   		
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
		   	while(PatientNew_RS.next()){
		   		CurrentPatientPK = PatientNew_RS.getInt(1); 
		   		ScanPatient = PatientNew_RS.getString(3);
				}
		   	PatientNew_RS.close();
		   	
			//System.out.println("ScanPatientID = "+ScanPatient);
			update.execute("Update Patient set LastUpdatedDateTime=GETUTCDATE() WHERE PatientID_PK='"+CurrentPatientPK+"';");
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
		Res = EM_A.ScanItem(Scanner, "Patient", "", ScanPatient, "");
		//System.out.println(Res);
		Description="Scan of Patient '" +ScanPatient+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanAdmitPat() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Patient/Procedure Status", "");
		//System.out.println(Res);
		Description="Scan of Patient/Procedure Status is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void e_ScanPatAfterAdmit() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//ScanPatient="PID020202";

		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
			stmt="Select PatientID_PK, PatientID from Patient where LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Patient)";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Patient_ID_rs = statement.executeQuery(stmt);
			while(Patient_ID_rs.next()){
				CurrentPatientPK = Patient_ID_rs.getInt(1); 
				ScanPatient = Patient_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
			}		
			Patient_ID_rs.close();
			//Fetch the decrypted patient id 
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			//System.out.println("stmtPatEncr="+stmtPatEncr);
	   		
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
		   	while(PatientNew_RS.next()){
		   		CurrentPatientPK = PatientNew_RS.getInt(1); 
				//LastUpdatedDate= PatientNew_RS.getString(2);
		   		ScanPatient = PatientNew_RS.getString(3);
				}
		   	PatientNew_RS.close();
		   	
			//System.out.println("ScanPatientID = "+ScanPatient);
			update.execute("Update Patient set LastUpdatedDateTime=GETUTCDATE() WHERE PatientID_PK='"+CurrentPatientPK+"';");
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
		Res = EM_A.ScanItem(Scanner, "Patient", "", ScanPatient, "");
		//System.out.println(Res);
		Description="Scan of Patient '" +ScanPatient+"' is done afer Admit Patint barcode is scanned in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void e_ScanPhys() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//ScanPhysician="Physician2 Physician2";
		//ScanPhysicianID="MD02";
		String PhysFirstName="",PhysLastName="";
		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=1 and IsActive=1 )"; //put sql statement here to find Staff details

			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanPhysicianID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				PhysFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				PhysLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			ScanPhysician = PhysFirstName +" "+PhysLastName+"("+ScanPhysicianID+")";
			Staff_rs.close();
			//System.out.println("StaffID = "+ScanPhysicianID);
			update.execute("Update Staff set LastUpdatedDateTime=GETUTCDATE() WHERE StaffID='"+ScanPhysicianID+"';");
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
		Res = EM_A.ScanItem(Scanner, "Staff", "Physician", ScanPhysician, "");
		//System.out.println(Res);
		Description="Scan of Physician '" +ScanPhysician+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_EndProcWithNoStartProc() throws InterruptedException{
		StartProcScan="No";
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", 	"Procedure End", "");
		//System.out.println(Res);
		Description="Scan of 'Procedure End' is done by skipping Start Procedure in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanStartProc() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ProcStart="Scanned";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", 	"Procedure Start", "");
		//System.out.println(Res);
		Description="Scan of 'Procedure Start' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SkipStartProc() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ProcStart="Skipped";
		//Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		//Res = EM_A.ScanItem(Scanner, "Workflow Event", "", 	"Procedure Start", "");
		////System.out.println(Res);
		Description="Scan of 'Procedure Start' is skipped in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reset() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Room="Available";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Available", "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		String ExpMsg="";

		ExpMsg="Room Available";
		ExpectedRmSt="Available";
		
		
		Res = EM_V.VerifyScanMsg(ExpMsg, Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		String RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, Scanner);
		ResultRmState=IHV.Result_Room_State(RmState, ExpectedRmSt, Scanner);
		Expected="Room State is Avialable";

		//System.out.println(Scenario+":  "+ResultRmState);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultRmState);

		Scanner="";
		Scope="";
		ScanScope="";
		ScanScopeSerNum="";
		Culture="";
		ScopeHangTime="";
		KE=0;
		OtherKey="No";
		ScanStaff="";
		ScanStaffID="";
		ScanPatient="";
		ScanPhysician=""; 
		ScanPhysicianID="";
		StartProcScan="";
		ScopeID=0;
		Room="";
		Description ="Reset is done";
		NoStaff="No";
		NoStaffClose="No";
		
		scopeRefNo="";
		scopeSerialNo="";
		scopeModel="";
		scopeExamTime="";
		ProcStartTime="";
		ProcEndTime="";
		CulturePath="";
		ExpectedExamCount="0";
		ExpectedReproCount="0";
		StaffID="";
		ScopePreclean="No";

		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reset2() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Room="Available";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Available", "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="";
		Scope="";
		ScanScope="";
		ScanScopeSerNum="";
		Culture="";
		ScopeHangTime="";
		KE=0;
		OtherKey="No";
		ScanStaff="";
		ScanStaffID="";
		ScanPatient="";
		ScanPhysician=""; 
		ScanPhysicianID="";
		StartProcScan="";
		ScopeID=0;
		Room="";
		Description ="Reset is done";
		NoStaff="No";
		NoStaffClose="No";
		scopeRefNo="";
		scopeSerialNo="";
		scopeModel="";
		scopeExamTime="";
		ProcStartTime="";
		ProcEndTime="";
		CulturePath="";
		ExpectedExamCount="0";
		ExpectedReproCount="0";
		StaffID="";
		ScopePreclean="No";

		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reset3() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Room="Available";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Available", "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="";
		Scope="";
		ScanScope="";
		ScanScopeSerNum="";
		Culture="";
		ScopeHangTime="";
		KE=0;
		OtherKey="No";
		ScanStaff="";
		ScanStaffID="";
		ScanPatient="";
		ScanPhysician=""; 
		ScanPhysicianID="";
		StartProcScan="";
		ScopeID=0;
		Room="";
		Description ="Reset is done";
		NoStaff="No";
		NoStaffClose="No";
		scopeRefNo="";
		scopeSerialNo="";
		scopeModel="";
		scopeExamTime="";
		ProcStartTime="";
		ProcEndTime="";
		CulturePath="";
		ExpectedExamCount="0";
		ExpectedReproCount="0";
		StaffID="";
		ScopePreclean="No";

		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_Reset4() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Room="Available";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Available", "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="";
		Scope="";
		ScanScope="";
		ScanScopeSerNum="";
		Culture="";
		ScopeHangTime="";
		KE=0;
		OtherKey="No";
		ScanStaff="";
		ScanStaffID="";
		ScanPatient="";
		ScanPhysician=""; 
		ScanPhysicianID="";
		StartProcScan="";
		ScopeID=0;
		Room="";
		Description ="Reset is done";
		NoStaff="No";
		NoStaffClose="No";
		scopeRefNo="";
		scopeSerialNo="";
		scopeModel="";
		scopeExamTime="";
		ProcStartTime="";
		ProcEndTime="";
		CulturePath="";
		ExpectedExamCount="0";
		ExpectedReproCount="0";
		StaffID="";
		ScopePreclean="No";

		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanEndProc() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", 	"Procedure End", "");
		//System.out.println(Res);
		Description="Scan of 'Procedure End' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeClose() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' for preclean complete is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffClose() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if (ScanStaff.equals("")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	   			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Staff_rs = statement.executeQuery(stmt);
				while(Staff_rs.next()){
					ScanStaffID= Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
					StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
					StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
				}		
				Staff_rs.close();
				//System.out.println("StaffID = "+ScanStaffID);
				update.execute("Update Staff set LastUpdatedDateTime=GETUTCDATE() WHERE StaffID='"+ScanStaffID+"';");
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
	   		ScanStaff=StaffFirstName+" "+StaffLastName+"("+ScanStaffID+")";
		}
   			
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' for preclosure is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_RoomStatusNC() throws InterruptedException{
		Room="NeedsCleaning";
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Needs Cleaning", "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Needs Cleaning' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_RoomStatusClose() throws InterruptedException{
		Room="Closed";
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Closed", "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Closed' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_Return1(){
		//System.out.println(getCurrentElement().getName());
	}

	public void e_Return2(){
		//System.out.println(getCurrentElement().getName());
	}

	public void e_Return3(){
		//System.out.println(getCurrentElement().getName());
	}

	public void e_Return4(){
		//System.out.println(getCurrentElement().getName());
	}

	public void e_Return5(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Return6(){
		//System.out.println(getCurrentElement().getName());
	}

	public void e_Return7(){
		//System.out.println(getCurrentElement().getName());
	}

	public void e_Return8(){
		//System.out.println(getCurrentElement().getName());
	}

	public void e_Return9(){
		//System.out.println(getCurrentElement().getName());
	}

	public void e_Return10(){
		//System.out.println(getCurrentElement().getName());
	}

	
	
	public void v_ProcedureRoom(){
		//System.out.println(getCurrentElement().getName());
		Scenario=Scenario+1;
		//System.out.println(getCurrentElement().getName());
		Description ="Start of new Scenario "+Scenario;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}
	}
	
	public void v_ScopeInPRCultResA() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		
		Res = EM_V.VerifyScanMsg(ScanScopeSerNum+" Hang Time "+ ScopeHangTime+" days", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		Expected="v_ScopeInPRCultResA: ScanScope scanned into "+Scanner;

		ExpectedCycleEvent="Pre-Procedure";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ScopeInIH=Scope_IH[0];
		ScopeInAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeInIH+" = Scope into Procedure Room ItemHistory_PK");
		
		Scope_IH=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,ScopeInAssociationID);
		scopeRefNo=Scope_IH[0];
		scopeSerialNo=Scope_IH[1];
		scopeModel=Scope_IH[2];
		scopeExamTime=Scope_IH[3];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		ExpectedState="0";
		OtherScopeStateID=0;
		ExpectedCabinet="0";

		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		StaffPK=0;
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		if(ResultLastStaff.contains("#Failed!#")){
			ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
		}
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);
		ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultPRIn="ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInState="+ResultScopeInState;
		//System.out.println(Scenario+":  "+ResultPRIn);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPRIn);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);

		String Result_ScopeExamDate=WF_V.Verify_ExamDate(scopeExamTime);
		if(Result_ScopeExamDate.contains("#Fail")){
			Result_ScopeExamDate=Result_ScopeExamDate;
		}
		System.out.println("Result_ScopeExamDate=:"+Result_ScopeExamDate);
		temp=Result_ScopeExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeExamDate,OverallResult);

		String Result_ScopeInStaff=WF_V.Verify_PRInStaff("");
		System.out.println("Result_ScopeInStaff=:"+Result_ScopeInStaff);
		temp=Result_ScopeInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeInStaff,OverallResult);
		
		String Result_ScopePhy=WF_V.Verify_Physician("");
		temp=Result_ScopePhy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePhy,OverallResult);
		System.out.println("Result_ScopePhy"+Result_ScopePhy);

		String Result_ScopePatient=WF_V.Verify_Patient("");
		temp=Result_ScopePatient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePatient,OverallResult);
		System.out.println("Result_ScopePatient=:"+Result_ScopePatient);
		
		String Result_ScopeProcStart=WF_V.Verify_ProcStart("");
		temp=Result_ScopeProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeProcStart,OverallResult);
		System.out.println("Result_ScopeProcStart=:"+Result_ScopeProcStart);

		String Result_ScopeProcEnd=WF_V.Verify_ProcEnd("");
		temp=Result_ScopeProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeProcEnd,OverallResult);
		System.out.println("Result_ScopeProcEnd=:"+Result_ScopeProcEnd);

		String Result_ScopePreclean=WF_V.Verify_PreClean(ScopePreclean);
		temp=Result_ScopePreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePreclean,OverallResult);
		System.out.println("Result_ScopePreclean=:"+Result_ScopePreclean);

		String Result_ScopeStaffPreclean=WF_V.Verify_PreCleanStaff("");		
		temp=Result_ScopeStaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeStaffPreclean,OverallResult);
		System.out.println("Result_ScopeStaffPreclean=:"+Result_ScopeStaffPreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeExamDate="+Result_ScopeExamDate
				+". Result_ScopeInStaff="+Result_ScopeInStaff+". Result_ScopePhy="+Result_ScopePhy+". Result_ScopePatient="+Result_ScopePatient
				+". Result_ScopeProcStart="+Result_ScopeProcStart+". Result_ScopeProcEnd="+Result_ScopeProcEnd+". Result_ScopePreclean="+Result_ScopePreclean
				+". Result_ScopeStaffPreclean="+Result_ScopeStaffPreclean;
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		result=result+Result_SRM_Scope_PRIn;
		
		//MAM verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;

		String resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID==-"+
				";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
		result=result+resultScope_MAM;

	}
	
	public void v_ScopeInPRCultResNA() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Results ? 1)Pass 2)Fail 3) No Results", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		Expected="v_ScopeInPRCultResNA: ScanScope scanned into "+Scanner+" and prompted for Culture Result";
		
		if(CultureResult.equalsIgnoreCase("Other")){
			//System.out.println("Culture Result - other Key - no DB check yet");

		} else {
			ExpectedCycleEvent="Pre-Procedure";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			ScopeInIH=Scope_IH[0];
			ScopeInAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeInIH+" = Scope into PR ItemHistory_PK");
			
			Scope_IH=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,ScopeInAssociationID);
			scopeRefNo=Scope_IH[0];
			scopeSerialNo=Scope_IH[1];
			scopeModel=Scope_IH[2];
			scopeExamTime=Scope_IH[3];
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
			
			ActualScopeState=Scope_IH[0];
			ScopeInLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			ActualReproCount=Scope_IH[5];
			ActualExamCount=Scope_IH[6];
			ExpectedState="0";
			OtherScopeStateID=7;
			ExpectedCabinet="0";

			ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
			ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);

			ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);
			ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);
			ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
			ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
			ResultPRIn="ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
					 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInState="+ResultScopeInState;
			//System.out.println(Scenario+":  "+ResultPRIn);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPRIn);
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			IP_A.Click_Details(scopeRefNo);
			
			Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

			String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
			temp=Result_RefNum.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
			
			String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
			temp=Result_ScopeModel.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

			String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
			temp=Result_ScopeName.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

			String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
			temp=Result_ScopeSerialNo.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

			String Result_ScopePR=WF_V.Verify_PR(Scanner);
			System.out.println("Result_ScopePR=:"+Result_ScopePR);
			temp=Result_ScopePR.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);

			String Result_ScopeExamDate=WF_V.Verify_ExamDate(scopeExamTime);
			if(Result_ScopeExamDate.contains("#Fail")){
				Result_ScopeExamDate=Result_ScopeExamDate;
			}
			System.out.println("Result_ScopeExamDate=:"+Result_ScopeExamDate);
			temp=Result_ScopeExamDate.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeExamDate,OverallResult);

			String Result_ScopeInStaff=WF_V.Verify_PRInStaff("");
			System.out.println("Result_ScopeInStaff=:"+Result_ScopeInStaff);
			temp=Result_ScopeInStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeInStaff,OverallResult);
			
			String Result_ScopePhy=WF_V.Verify_Physician("");
			temp=Result_ScopePhy.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePhy,OverallResult);
			System.out.println("Result_ScopePhy"+Result_ScopePhy);

			String Result_ScopePatient=WF_V.Verify_Patient("");
			temp=Result_ScopePatient.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePatient,OverallResult);
			System.out.println("Result_ScopePatient=:"+Result_ScopePatient);
			
			String Result_ScopeProcStart=WF_V.Verify_ProcStart("");
			temp=Result_ScopeProcStart.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeProcStart,OverallResult);
			System.out.println("Result_ScopeProcStart=:"+Result_ScopeProcStart);

			String Result_ScopeProcEnd=WF_V.Verify_ProcEnd("");
			temp=Result_ScopeProcEnd.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeProcEnd,OverallResult);
			System.out.println("Result_ScopeProcEnd=:"+Result_ScopeProcEnd);

			String Result_ScopePreclean=WF_V.Verify_PreClean(ScopePreclean);
			temp=Result_ScopePreclean.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePreclean,OverallResult);
			System.out.println("Result_ScopePreclean=:"+Result_ScopePreclean);

			String Result_ScopeStaffPreclean=WF_V.Verify_PreCleanStaff("");		
			temp=Result_ScopeStaffPreclean.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeStaffPreclean,OverallResult);
			System.out.println("Result_ScopeStaffPreclean=:"+Result_ScopeStaffPreclean);
			
			WF_A.Cancel(Changes);
			Expected=Description;
			String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
					+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeExamDate="+Result_ScopeExamDate
					+". Result_ScopeInStaff="+Result_ScopeInStaff+". Result_ScopePhy="+Result_ScopePhy+". Result_ScopePatient="+Result_ScopePatient
					+". Result_ScopeProcStart="+Result_ScopeProcStart+". Result_ScopeProcEnd="+Result_ScopeProcEnd+". Result_ScopePreclean="+Result_ScopePreclean
					+". Result_ScopeStaffPreclean="+Result_ScopeStaffPreclean;
			IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
			result=result+Result_SRM_Scope_PRIn;
			
			//MAM verification
			MAM_A.Click_MaterialsAndAssetManagement();
			Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
			Expected=Description;

			String resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID==-"+
					";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
					+ExpectedReproCount).toString();
			IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
			result=result+resultScope_MAM;

		}
		
	}
	
	public void v_CultureResult() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		String ExpMsg="";
		if (KE==1){
			ExpMsg="Culture Pass";
		}else if (KE==2){
			ExpMsg="Culture Fail";
		}else if (KE==3){
			ExpMsg="No Results";
		}else if (KE==4){
			ExpMsg="Results ? 1)Pass 2)Fail 3) No Results";
		}
		Expected="v_CultureResult: Enter Culture Result of "+KE;

		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg(ExpMsg, Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		
		if (OtherKey=="Yes" ){
			if (!Res){
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
				ResultCultureResult="4 entered for culture result- no DB check yet.";
				//System.out.println(Scenario+":  "+ResultCultureResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultCultureResult);
			}
		else{
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
			Culture_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			CultureIH=Culture_IH[0];
			CultureResult_Assoc=Culture_IH[1];
			ResultCultureResult=IHV.Result_Same_Assoc(Integer.toString(CultureAssociationID),CultureResult_Assoc);
			
			//System.out.println(Scenario+":  "+ResultCultureResult);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultCultureResult);
			
			Culture_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, ScopeInIH);
			ResultCulture_RI= IHV.RelatedItem_Verf(CultureIH, Culture_RI);
			//System.out.println(Scenario+":  "+ResultCulture_RI);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultCulture_RI);

			}
		}
		
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,String.valueOf(CultureAssociationID));
		String ScopeRefNo=ScopeInfo[0];
		
		Thread.sleep(1000);
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.ClearFilter();
		System.out.println("ScopeSerialNumber"+ScanScopeSerNum);
		//Unifia_Admin_Selenium.driver.navigate().refresh();
		IP_A.ScopeFilter(ScanScopeSerNum);
		IP_A.ApplyFilter();
		IP_A.ClearFilter();
		IP_A.ScopeFilter(ScanScopeSerNum);
		IP_A.ApplyFilter();
		IP_A.Click_Details(ScopeRefNo);
		
		if(!CultureResult.equalsIgnoreCase("other")){
			Description="Verify Culture Result of '"+CultureResult+"' in SRM Screen for "+ScanScope+" into "+Scanner+". ";
			Expected=Description;
			//Get Scope info - with cultureObtained
			cultureStatusResult=WF_V.Verify_Culture(CultureResult);
			String temp[]=cultureStatusResult.split("-");
			System.out.println("cultureStatusResult="+cultureStatusResult);
			IHV.Exec_Log_Result(FileName, Description, Expected, cultureStatusResult);
			result=result+cultureStatusResult;
		}else{
			Description="Verify Culture Result of 'Awaiting Results' in SRM Screen for "+ScanScope+" into "+Scanner+". ";
			Expected=Description;
			//Get Scope info - with cultureObtained
			cultureStatusResult=WF_V.Verify_Culture("Awaiting Results");
			String temp[]=cultureStatusResult.split("-");
			System.out.println("cultureStatusResult="+cultureStatusResult);
			IHV.Exec_Log_Result(FileName, Description, Expected, cultureStatusResult);
			result=result+cultureStatusResult;
		}
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ScanStaff() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		
		if  (NoStaff.equals("No")){
			Res = EM_V.VerifyScanMsg("Staff "+ScanStaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			//DB Verification
			Expected="v_ScanStaff: Staff that brought the scope into the PR scanned";
			//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			ExpectedCycleEvent="PreProcedureStaff";
			Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			StaffInIH=Staff_IH[0];
			StaffIn_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			
			ResultScopeIn1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			temp=ResultScopeIn1StaffCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScopeIn1StaffCycle,OverallResult);

			ResultStaffIn=IHV.Result_Same_Assoc(ScopeInAssociationID,StaffIn_Assoc)+" for staff and scope into PR.";
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,ScanStaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			
			ResultStaffIn=ResultStaffIn+"ResultScopeIn1StaffCycle="+ResultScopeIn1StaffCycle+". ResultLastStaff="+ResultLastStaff;
			
			//System.out.println(Scenario+":  "+ResultStaffIn);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffIn);

			StaffIn_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, ScopeInIH);
			ResultIn_RI= IHV.RelatedItem_Verf(StaffInIH, StaffIn_RI);
			//System.out.println(Scenario+":  "+ResultIn_RI);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultIn_RI);
			
			if(CultureNM.equalsIgnoreCase("Yes")){ // move this to 'needs cleaning' or closed?
				Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
				OtherScopeStateID=0;

				ActualScopeState=Scope_IH[0];
				ScopeInLoc=Scope_IH[1];
				ActualOtherScopeState=Scope_IH[2];
				ActualSubloc=Scope_IH[3];
				ExpectedState="0";
				ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
				ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
				ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
				ResultPRIn="v_ScanStaff: ResultScopeInCycle= "+ResultScopeInCycle+". ResultScopeInLoc= "+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState;
				//System.out.println(Scenario+":  "+ResultPRIn);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultPRIn);
			}
			
		}else if (NoStaff.equals("Yes")){
			Description="No Staff scan is done";
			Expected="v_ScanStaff: No staff Scan is done. verify LastScanStaffID=0";

			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);

			//System.out.println(Description);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else if (NoStaff.equals("NoKey")){
			Res = EM_V.VerifyScanMsg("Results ? 1)Pass 2)Fail 3) No Results", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			//DB Verification
			Expected="v_ScanStaff: "+ScanScope+" scanned into "+Scanner+" and prompted for Culture Result but didn't enter a culture result. Also did not scan Staff";
			ExpectedCycleEvent="Pre-Procedure";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			ScopeInIH=Scope_IH[0];
			ScopeInAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeInIH+" = Scope into PR ItemHistory_PK");
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
			
			ActualScopeState=Scope_IH[0];
			ScopeInLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			ExpectedState="0";
			OtherScopeStateID=7;
			ExpectedCabinet="0";

			ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
			ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
			ResultPRIn="ResultScopeInCycle= "+ResultScopeInCycle+". ResultScopeInState="+ResultScopeInState+". ResultLastStaff="+ResultLastStaff;
			//System.out.println(Scenario+":  "+ResultPRIn);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPRIn);
		}
		
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		
		Scope_IH=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,ScopeInAssociationID);
		scopeRefNo=Scope_IH[0];
		scopeSerialNo=Scope_IH[1];
		scopeModel=Scope_IH[2];
		scopeExamTime=Scope_IH[3];
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);
		
		String Result_ScopeExamDate=WF_V.Verify_ExamDate(scopeExamTime);
		if(Result_ScopeExamDate.contains("#Fail")){
			Result_ScopeExamDate=Result_ScopeExamDate;
		}
		System.out.println("Result_ScopeExamDate=:"+Result_ScopeExamDate);
		temp=Result_ScopeExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeExamDate,OverallResult);
		
		String Result_ScopePatient=WF_V.Verify_Patient(ScanPatient);
		temp=Result_ScopePatient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePatient,OverallResult);
		System.out.println("Result_ScopePatient=:"+Result_ScopePatient);
		
		if(CulturePath.equalsIgnoreCase("Yes")){
			StaffID="";
		}else{
			StaffID=ScanStaffID;
		}
		String Result_ScopeInStaff=WF_V.Verify_PRInStaff(StaffID);
		System.out.println("Result_ScopeInStaff=:"+Result_ScopeInStaff);
		temp=Result_ScopeInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeInStaff,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeExamDate="+Result_ScopeExamDate
				+". Result_ScopePatient="+Result_ScopePatient+". Result_ScopeInStaff="+Result_ScopeInStaff;
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		result=result+Result_SRM_Scope_PRIn;
		
		//MAM verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;
		
		if(ScanStaffID==null||ScanStaffID.equalsIgnoreCase("")){
			StaffID="-";
		}else{
			StaffID=ScanStaffID;
		}

		String resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID+
				";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
		result=result+resultScope_MAM;

	}
	
	public void v_AdmitPatScanned() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
				Res = EM_V.VerifyScanMsg("Scan Patient ID # to Admit", Unifia_Admin_Selenium.ScannerCount);
				//System.out.println(Res);
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				Expected="v_AdmitPatScanned: Admit patient barcode scanned";
				
				Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
				AdmitPatient_IH=Staff_IH[0];
				AdmitPatient_Assoc=Staff_IH[1];
				ResultAdmitPatient=IHV.Result_Same_Assoc(ScopeInAssociationID,AdmitPatient_Assoc)+" for Admit patient barcode scan and scope into PR.";
				
				//System.out.println(Scenario+":  "+ResultAdmitPatient);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultAdmitPatient);

	}
	
	public void v_PatScannednRoomStatus() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		String StaffID="-";
		Res = EM_V.VerifyScanMsg("Patient "+ScanPatient+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		Expected="v_PatScannednRoomStatus: Patient Scanned into Procedure Room ";

		ExpectedCycleEvent="Patient In";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		Patient_IH=Scope_IH[0];
		Patient_Assoc=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println("Patient AssociationID="+Patient_Assoc);

		ResultPatientCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ResultPatient=IHV.Result_Same_Assoc(ScopeInAssociationID,Patient_Assoc)+" for patient scan and scope into PR.";
		ExpectedRmSt="In-use";
		
		String RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, Scanner);
		ResultRmState=IHV.Result_Room_State(RmState, ExpectedRmSt, Scanner);

		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];

		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);

		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);

		ResultPatient=ResultPatient+" and ResultPatientCycle"+ResultPatientCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount+".";
		//System.out.println(Scenario+":  "+ResultPatient);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPatient);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);
		
		String Result_ScopePatient=WF_V.Verify_Patient(ScanPatient);
		temp=Result_ScopePatient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePatient,OverallResult);
		System.out.println("Result_ScopePatient=:"+Result_ScopePatient);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopePatient="+Result_ScopePatient+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		result=result+Result_SRM_Scope_PRIn;
		
		//MAM verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;
		
		if(ScanStaffID==null||ScanStaffID.equalsIgnoreCase("")){
			StaffID="-";
		}else{
			StaffID=ScanStaffID;
		}

		String resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID+
				";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
		result=result+resultScope_MAM;

	}
	
	public void v_PhysScanned() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Physician "+ScanPhysicianID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		
		Expected="v_PhysScanned: Physician Scanned into Procedure Room ";
		ExpectedCycleEvent="Physician";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		Physician_IH=Scope_IH[0];
		Physician_Assoc=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println("Physician AssociationID="+Physician_Assoc);

		ResultPhysicianCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ResultPhysician=IHV.Result_Same_Assoc(ScopeInAssociationID,Physician_Assoc)+" for Physican scan and scope into PR.";

		ResultPhysician=ResultPhysician+" and ResultPhysicianCycle"+ResultPhysicianCycle;
		//System.out.println(Scenario+":  "+ResultPhysician);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysician);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);
		
		String Result_ScopePhy=WF_V.Verify_Physician(ScanPhysicianID);
		temp=Result_ScopePhy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePhy,OverallResult);
		System.out.println("Result_ScopePhy"+Result_ScopePhy);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopePhy="+Result_ScopePhy+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		
		result=result+Result_SRM_Scope_PRIn;

	}
	
	public void v_StartProcScanned() throws InterruptedException, ParseException, AWTException{
		String ProcedureStartTime="";
		//System.out.println(getCurrentElement().getName());
		Timestamp ProcReceivedDateTime  = null;
		if(!ProcStart.equalsIgnoreCase("Skipped")){
			try{
				Thread.sleep(2000);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
				//stmt="select ReceivedDateTime from itemhistory where scanitemid_Fk=7 and ScanItemTypeID_Fk=7 and lastUpdatedDateTime=(Select max(LastUpdatedDateTime) from itemhistory where scanitemid_Fk=7 and ScanItemTypeID_Fk=7)";
				stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where ScanItemID_FK='7' and ScanItemTypeID_FK='7' and lastUpdatedDateTime=(Select max(LastUpdatedDateTime) from itemhistory where scanitemid_Fk=7 and ScanItemTypeID_Fk=7)";
				Statement Statement = conn.createStatement();
				PRStatus_RS = Statement.executeQuery(stmt);
				while(PRStatus_RS.next()){
					ProcReceivedDateTime = PRStatus_RS.getTimestamp(1);
				}
				conn.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			
			DateFormat format = new SimpleDateFormat( "hh:mm a" );
			ProcedureStartTime = format.format(ProcReceivedDateTime );
			System.out.println (ProcedureStartTime);
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Procedure Started "+ ProcedureStartTime, Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		
			Expected="v_StartProcScanned: Procedure Start Scanned";
			ExpectedCycleEvent="Procedure Start";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			ProcStart_IH=Scope_IH[0];
			ProcStart_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println("Procedure Start AssociationID="+ProcStart_Assoc);
		
			ResultProcStartCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			ResultProcStart=IHV.Result_Same_Assoc(ScopeInAssociationID,ProcStart_Assoc)+" for Procedure Start scan in PR.";
		
			ResultProcStart=ResultProcStart+" and ResultProcStartCycle"+ResultProcStartCycle;
			//System.out.println(Scenario+":  "+ResultProcStart);
			
			format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
			ProcedureStartTime = format.format(ProcReceivedDateTime );
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			IP_A.Click_Details(scopeRefNo);
			
			Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

			String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
			temp=Result_RefNum.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
			
			String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
			temp=Result_ScopeModel.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

			String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
			temp=Result_ScopeName.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

			String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
			temp=Result_ScopeSerialNo.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

			String Result_ScopePR=WF_V.Verify_PR(Scanner);
			System.out.println("Result_ScopePR=:"+Result_ScopePR);
			temp=Result_ScopePR.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);
			
			String Result_ScopeProcStart=WF_V.Verify_ProcStart(ProcedureStartTime);
			if(Result_ScopeProcStart.contains("#Fail")){
				Result_ScopeProcStart=Result_ScopeProcStart;
			}
			temp=Result_ScopeProcStart.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeProcStart,OverallResult);
			System.out.println("Result_ScopeProcStart=:"+Result_ScopeProcStart);
			
			WF_A.Cancel(Changes);
			Expected=Description;
			String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
					+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeProcStart="+Result_ScopeProcStart+".";
			IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
			result=result+Result_SRM_Scope_PRIn;
			
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultProcStart);
	}
	
	public void v_EndProcScanned() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		String ExpMsg="";
		String ProcedureEndDateTime="";
		Timestamp ProcReceivedDateTimeEnd=null;
		
		try{
			Thread.sleep(2000);
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
    		stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where ScanItemID_FK='8' and ScanItemTypeID_FK='7' and lastUpdatedDateTime=(Select max(LastUpdatedDateTime) from itemhistory where scanitemid_Fk=8 and ScanItemTypeID_Fk=7)";
    		Statement Statement = conn.createStatement();
    		PRStatus_RS = Statement.executeQuery(stmt);
			while(PRStatus_RS.next()){
				ProcReceivedDateTimeEnd = PRStatus_RS.getTimestamp(1);
			}
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		
		//Verify the scan message received is correct
		if (StartProcScan.equals("No")){
			ExpMsg="Start Procedure Skipped";
			ProcedureEndDateTime="";
		}else{
			DateFormat format = new SimpleDateFormat( "hh:mm a" );
			ProcedureEndDateTime = format.format(ProcReceivedDateTimeEnd );
			
			ExpMsg="Procedure Completed "+ProcedureEndDateTime;
		}
		
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
		ProcedureEndDateTime = format.format(ProcReceivedDateTimeEnd );
		
		Res = EM_V.VerifyScanMsg(ExpMsg, Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;

		Expected="v_EndProcScanned: Procedure End Scanned";
		ExpectedCycleEvent="Procedure End";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ProcEnd_IH=Scope_IH[0];
		ProcEnd_Assoc=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println("Procedure End AssociationID="+ProcEnd_Assoc);

		ResultProcEndCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ResultProcEnd=IHV.Result_Same_Assoc(ScopeInAssociationID,ProcEnd_Assoc)+" for Procedure End scan in PR.";

		ResultProcEnd=ResultProcEnd+" and ResultProcEndCycle"+ResultProcEndCycle;
		//System.out.println(Scenario+":  "+ResultProcEnd);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultProcEnd);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);
		
		String Result_ScopeProcEnd=WF_V.Verify_ProcEnd(ProcedureEndDateTime);
		if(Result_ScopeProcEnd.contains("#Fail")){
			Result_ScopeProcEnd=Result_ScopeProcEnd;
		}
		temp=Result_ScopeProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeProcEnd,OverallResult);
		System.out.println("Result_ScopeProcEnd=:"+Result_ScopeProcEnd);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeProcEnd="+Result_ScopeProcEnd+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		result=result+Result_SRM_Scope_PRIn;

	}
	
	public void v_PreCleanScopeScanned() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		String ScopePreclean="Yes";
		Expected="v_PreCleanScopeScanned: Scope preclean scanned.";
		Res = EM_V.VerifyScanMsg(ScanScopeSerNum +" Pre Clean Completed" , Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		
		ExpectedCycleEvent="Pre-Clean Complete";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ScopeOutIH=Scope_IH[0];
		ScopeOutAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeInIH+" = Scope out of Procedure Room ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		
		ActualScopeState=Scope_IH[0];
		ScopeOutLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		
		ExpectedState="3";
		ExpectedCabinet="0";
		
		/*if(CultureResult.equalsIgnoreCase("No")){
			OtherScopeStateID=0;
		} else {
			OtherScopeStateID=7;
		}*/
		OtherScopeStateID=0;
		if(KE==0 && CultureResult.equalsIgnoreCase("Yes")){
			OtherScopeStateID=7;
		}

		ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		StaffPK=0;
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		if(ResultLastStaff.contains("#Failed!#")){
			ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
		}
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);
		
		ResultScopeOutLoc=IHV.Result_Location(Scanner, ScopeOutLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultPROut="ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeOutLoc= "+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		//System.out.println(Scenario+":  "+ResultPROut);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPROut);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);
		
		String Result_ScopeExamDate=WF_V.Verify_ExamDate(scopeExamTime);
		if(Result_ScopeExamDate.contains("#Fail")){
			Result_ScopeExamDate=Result_ScopeExamDate;
		}
		System.out.println("Result_ScopeExamDate=:"+Result_ScopeExamDate);
		temp=Result_ScopeExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeExamDate,OverallResult);
		
		String Result_ScopePreclean=WF_V.Verify_PreClean(ScopePreclean);
		temp=Result_ScopePreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePreclean,OverallResult);
		System.out.println("Result_ScopePreclean=:"+Result_ScopePreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName
				+". Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeExamDate="+Result_ScopeExamDate
				+". Result_ScopePreclean="+Result_ScopePreclean+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		result=result+Result_SRM_Scope_PRIn;

	}
	
	public void v_PreCleanStaffScanned() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		if (NoStaffClose.equals("No")){
			Res = EM_V.VerifyScanMsg("Staff "+ScanStaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			//DBVerification
			Expected="v_PreCleanStaffScanned: Staff that did the Scope preclean.";
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Staff "+ScanStaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			ExpectedCycleEvent="PreCleanStaff";
			Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
			StaffOutIH=Staff_IH[0];
			StaffOut_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			
			ResultScopeOut1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			temp=ResultScopeOut1StaffCycle.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScopeOut1StaffCycle,OverallResult);

			
			ResultStaffOut=IHV.Result_Same_Assoc(ScopeOutAssociationID,StaffOut_Assoc)+" for staff and scope out of the Procedure Room.";
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,ScanStaffID);

			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			
			ResultStaffOut=ResultStaffOut+" ResultScopeOut1StaffCycle="+ResultScopeOut1StaffCycle+". ResultLastStaff="+ResultLastStaff;

			//System.out.println(Scenario+":  "+ResultStaffOut);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffOut);

			StaffOut_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, ScopeOutIH);
			ResultOut_RI= IHV.RelatedItem_Verf(StaffOutIH, StaffOut_RI);
			//System.out.println(Scenario+":  "+ResultOut_RI);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultOut_RI);
		}else if (NoStaffClose.equals("Yes")){
			Description="No Staff scan is done afte preclean scope is done";
			Expected="v_PreCleanStaffScanned: No staff Scan is done. verify LastScanStaffID=0";

			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);
			//System.out.println(Description);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);
		
		if (NoStaffClose.equals("Yes")){
			StaffID="";
		}else{
			StaffID=ScanStaffID;
		}
		String Result_ScopeStaffPreclean=WF_V.Verify_PreCleanStaff(StaffID);		
		temp=Result_ScopeStaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeStaffPreclean,OverallResult);
		System.out.println("Result_ScopeStaffPreclean=:"+Result_ScopeStaffPreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeStaffPreclean="+Result_ScopeStaffPreclean+".";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		result=result+Result_SRM_Scope_PRIn;
		
		//MAM verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;
		
		if (NoStaffClose.equals("Yes")){
			StaffID="-";
		}else{
			StaffID=ScanStaffID;
		}

		String resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID+
				";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
		result=result+resultScope_MAM;
	}
	
	public void v_RoomStatus() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		String ExpMsg="";
		//Verify the scan message received is correct
		if (Room.equals("NeedsCleaning")){
			ExpMsg="Room Needs Cleaning";
		}else if ((Room.equals("Closed"))){
			ExpMsg="Room Closed";
		}
		Res = EM_V.VerifyScanMsg(ExpMsg, Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		Expected="Room Status changed to "+Room;
		ExpectedCycleEvent="Room Status Change";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		RoomStatus_IH=Scope_IH[0];
		RoomStatus_Assoc=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println("Room Status of "+Room+" AssociationID="+RoomStatus_Assoc);
		ExpectedRmSt="";
		ResultRoomStatusCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		if(Room.equalsIgnoreCase("Closed")){
			ResultRoomStatus=IHV.Result_Different_Assoc(ScopeInAssociationID,RoomStatus_Assoc)+" for Room Status scan in PR.";			
			ExpectedRmSt="Closed";
			
		}else if (Room.equalsIgnoreCase("NeedsCleaning")){
			ResultRoomStatus=IHV.Result_Same_Assoc(ScopeInAssociationID,RoomStatus_Assoc)+" for Room Status scan in PR.";
			ExpectedRmSt="Needs Cleaning";
		}
		String RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, Scanner);
		ResultRmState=IHV.Result_Room_State(RmState, ExpectedRmSt, Scanner);

		ResultRoomStatus=ResultRoomStatus+" and ResultRoomStatusCycle"+ResultRoomStatusCycle+" and ResultRmState="+ResultRmState;
		//System.out.println(Scenario+":  "+ResultRoomStatus);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultRoomStatus);
		
		//NM update need to have the initial room available scans before starting the test. --- maybe create a function to make sure associationID's are different

	}

	public void v_ScopeInPRCultResFail() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Check Scope Culturing Test Record", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		Expected="v_ScopeInPRCultResFail: Scan scope that had a culture result of failed entered.";
		if(CultureResult.equalsIgnoreCase("Other")){
			//System.out.println("Culture Result - other Key - no DB check yet");
		}else {

			ExpectedCycleEvent="Pre-Procedure";
			
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			ScopeInIH=Scope_IH[0];
			ScopeInAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeInIH+" = Scope into Procedure Room ItemHistory_PK");
			
			Scope_IH=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,ScopeInAssociationID);
			scopeRefNo=Scope_IH[0];
			scopeSerialNo=Scope_IH[1];
			scopeModel=Scope_IH[2];
			scopeExamTime=Scope_IH[3];
			
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
			ActualScopeState=Scope_IH[0];
			ScopeInLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			ActualReproCount=Scope_IH[5];
			ActualExamCount=Scope_IH[6];
			ExpectedState="0";
			OtherScopeStateID=0;
			ExpectedCabinet="0";
	
			ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
			ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			if(ResultLastStaff.contains("#Failed!#")){
				ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
			}
			ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);

			ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);
			ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
			ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
			ResultPRIn="ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
					 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInState="+ResultScopeInState;
			//System.out.println(Scenario+":  "+ResultPRIn);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPRIn);
			
			//SRM Verification
			Thread.sleep(1000); //Wait 1 sec
			IP_A.Click_InfectionPrevention();
			IP_A.Click_SRM();
			IP_A.Click_Details(scopeRefNo);
			
			Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

			String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
			temp=Result_RefNum.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
			
			String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
			temp=Result_ScopeModel.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

			String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
			temp=Result_ScopeName.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

			String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
			temp=Result_ScopeSerialNo.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

			String Result_ScopePR=WF_V.Verify_PR(Scanner);
			System.out.println("Result_ScopePR=:"+Result_ScopePR);
			temp=Result_ScopePR.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);

			String Result_ScopeExamDate=WF_V.Verify_ExamDate(scopeExamTime);
			if(Result_ScopeExamDate.contains("#Fail")){
				Result_ScopeExamDate=Result_ScopeExamDate;
			}
			System.out.println("Result_ScopeExamDate=:"+Result_ScopeExamDate);
			temp=Result_ScopeExamDate.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeExamDate,OverallResult);

			String Result_ScopeInStaff=WF_V.Verify_PRInStaff("");
			System.out.println("Result_ScopeInStaff=:"+Result_ScopeInStaff);
			temp=Result_ScopeInStaff.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeInStaff,OverallResult);
			
			String Result_ScopePhy=WF_V.Verify_Physician("");
			temp=Result_ScopePhy.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePhy,OverallResult);
			System.out.println("Result_ScopePhy"+Result_ScopePhy);

			String Result_ScopePatient=WF_V.Verify_Patient("");
			temp=Result_ScopePatient.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePatient,OverallResult);
			System.out.println("Result_ScopePatient=:"+Result_ScopePatient);
			
			String Result_ScopeProcStart=WF_V.Verify_ProcStart("");
			temp=Result_ScopeProcStart.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeProcStart,OverallResult);
			System.out.println("Result_ScopeProcStart=:"+Result_ScopeProcStart);

			String Result_ScopeProcEnd=WF_V.Verify_ProcEnd("");
			temp=Result_ScopeProcEnd.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeProcEnd,OverallResult);
			System.out.println("Result_ScopeProcEnd=:"+Result_ScopeProcEnd);

			String Result_ScopePreclean=WF_V.Verify_PreClean(ScopePreclean);
			temp=Result_ScopePreclean.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopePreclean,OverallResult);
			System.out.println("Result_ScopePreclean=:"+Result_ScopePreclean);

			String Result_ScopeStaffPreclean=WF_V.Verify_PreCleanStaff("");		
			temp=Result_ScopeStaffPreclean.split("-");
			OverallResult=GF.FinalResult(temp[0], Result_ScopeStaffPreclean,OverallResult);
			System.out.println("Result_ScopeStaffPreclean=:"+Result_ScopeStaffPreclean);
			
			WF_A.Cancel(Changes);
			Expected=Description;
			String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
					+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeExamDate="+Result_ScopeExamDate
					+". Result_ScopeInStaff="+Result_ScopeInStaff+". Result_ScopePhy="+Result_ScopePhy+". Result_ScopePatient="+Result_ScopePatient
					+". Result_ScopeProcStart="+Result_ScopeProcStart+". Result_ScopeProcEnd="+Result_ScopeProcEnd+". Result_ScopePreclean="+Result_ScopePreclean
					+". Result_ScopeStaffPreclean="+Result_ScopeStaffPreclean;
			IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
			result=result+Result_SRM_Scope_PRIn;
			
			//MAM verification
			MAM_A.Click_MaterialsAndAssetManagement();
			Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
			Expected=Description;

			String resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID==-"+
					";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
					+ExpectedReproCount).toString();
			IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
			result=result+resultScope_MAM;
		}
		
	}
	
	public void v_ScopeInKEError() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Prior Scope Reprocessing Cycle Failed", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Expected="v_ScopeInKEError: Scan scope that had an unexpected termination in the reprocessor.";

		ExpectedCycleEvent="Pre-Procedure";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ScopeInIH=Scope_IH[0];
		ScopeInAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeInIH+" = Scope into Procedure Room ItemHistory_PK");
		
		Scope_IH=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,ScopeInAssociationID);
		scopeRefNo=Scope_IH[0];
		scopeSerialNo=Scope_IH[1];
		scopeModel=Scope_IH[2];
		scopeExamTime=Scope_IH[3];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		ExpectedState="0";
		OtherScopeStateID=0;
		ExpectedCabinet="0";

		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		StaffPK=0;
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		if(ResultLastStaff.contains("#Failed!#")){
			ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
		}

		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);

		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);

		ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultPRIn="ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInState="+ResultScopeInState;
		//System.out.println(Scenario+":  "+ResultPRIn);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPRIn);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);

		String Result_ScopeExamDate=WF_V.Verify_ExamDate(scopeExamTime);
		if(Result_ScopeExamDate.contains("#Fail")){
			Result_ScopeExamDate=Result_ScopeExamDate;
		}
		System.out.println("Result_ScopeExamDate=:"+Result_ScopeExamDate);
		temp=Result_ScopeExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeExamDate,OverallResult);

		String Result_ScopeInStaff=WF_V.Verify_PRInStaff("");
		System.out.println("Result_ScopeInStaff=:"+Result_ScopeInStaff);
		temp=Result_ScopeInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeInStaff,OverallResult);
		
		String Result_ScopePhy=WF_V.Verify_Physician("");
		temp=Result_ScopePhy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePhy,OverallResult);
		System.out.println("Result_ScopePhy"+Result_ScopePhy);

		String Result_ScopePatient=WF_V.Verify_Patient("");
		temp=Result_ScopePatient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePatient,OverallResult);
		System.out.println("Result_ScopePatient=:"+Result_ScopePatient);
		
		String Result_ScopeProcStart=WF_V.Verify_ProcStart("");
		temp=Result_ScopeProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeProcStart,OverallResult);
		System.out.println("Result_ScopeProcStart=:"+Result_ScopeProcStart);

		String Result_ScopeProcEnd=WF_V.Verify_ProcEnd("");
		temp=Result_ScopeProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeProcEnd,OverallResult);
		System.out.println("Result_ScopeProcEnd=:"+Result_ScopeProcEnd);

		String Result_ScopePreclean=WF_V.Verify_PreClean(ScopePreclean);
		temp=Result_ScopePreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePreclean,OverallResult);
		System.out.println("Result_ScopePreclean=:"+Result_ScopePreclean);

		String Result_ScopeStaffPreclean=WF_V.Verify_PreCleanStaff("");		
		temp=Result_ScopeStaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeStaffPreclean,OverallResult);
		System.out.println("Result_ScopeStaffPreclean=:"+Result_ScopeStaffPreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeExamDate="+Result_ScopeExamDate
				+". Result_ScopeInStaff="+Result_ScopeInStaff+". Result_ScopePhy="+Result_ScopePhy+". Result_ScopePatient="+Result_ScopePatient
				+". Result_ScopeProcStart="+Result_ScopeProcStart+". Result_ScopeProcEnd="+Result_ScopeProcEnd+". Result_ScopePreclean="+Result_ScopePreclean
				+". Result_ScopeStaffPreclean="+Result_ScopeStaffPreclean;
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		result=result+Result_SRM_Scope_PRIn;
		
		//MAM verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;

		String resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID==-"+
				";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		System.out.println("resultScope_MAM: "+resultScope_MAM);
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
		result=result+resultScope_MAM;
	}
	
	public void v_ScopeNoRepRec() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Check Scope Reprocessing Record", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		Expected="v_ScopeNoRepRec: Scan scope that was not reprocessed.";
		ExpectedCycleEvent="Pre-Procedure";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ScopeInIH=Scope_IH[0];
		ScopeInAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeInIH+" = Scope into Procedure Room ItemHistory_PK");
		
		Scope_IH=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,ScopeInAssociationID);
		scopeRefNo=Scope_IH[0];
		scopeSerialNo=Scope_IH[1];
		scopeModel=Scope_IH[2];
		scopeExamTime=Scope_IH[3];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		ExpectedState="0";
		OtherScopeStateID=0;
		ExpectedCabinet="0";

		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		StaffPK=0;
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		if(ResultLastStaff.contains("#Failed!#")){
			ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
		}
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);

		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);
		ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultPRIn="ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInState="+ResultScopeInState;
		//System.out.println(Scenario+":  "+ResultPRIn);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPRIn);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(scopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		String Result_RefNum=WF_V.Verify_RefNum(scopeRefNo);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_ScopeModel=WF_V.Verify_ScopeModel(scopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		String Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		String Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(scopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		String Result_ScopePR=WF_V.Verify_PR(Scanner);
		System.out.println("Result_ScopePR=:"+Result_ScopePR);
		temp=Result_ScopePR.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePR,OverallResult);

		String Result_ScopeExamDate=WF_V.Verify_ExamDate(scopeExamTime);
		if(Result_ScopeExamDate.contains("#Fail")){
			Result_ScopeExamDate=Result_ScopeExamDate;
		}
		System.out.println("Result_ScopeExamDate=:"+Result_ScopeExamDate);
		temp=Result_ScopeExamDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeExamDate,OverallResult);

		String Result_ScopeInStaff=WF_V.Verify_PRInStaff("");
		System.out.println("Result_ScopeInStaff=:"+Result_ScopeInStaff);
		temp=Result_ScopeInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeInStaff,OverallResult);
		
		String Result_ScopePhy=WF_V.Verify_Physician("");
		temp=Result_ScopePhy.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePhy,OverallResult);
		System.out.println("Result_ScopePhy"+Result_ScopePhy);

		String Result_ScopePatient=WF_V.Verify_Patient("");
		temp=Result_ScopePatient.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePatient,OverallResult);
		System.out.println("Result_ScopePatient=:"+Result_ScopePatient);
		
		String Result_ScopeProcStart=WF_V.Verify_ProcStart("");
		temp=Result_ScopeProcStart.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeProcStart,OverallResult);
		System.out.println("Result_ScopeProcStart=:"+Result_ScopeProcStart);

		String Result_ScopeProcEnd=WF_V.Verify_ProcEnd("");
		temp=Result_ScopeProcEnd.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeProcEnd,OverallResult);
		System.out.println("Result_ScopeProcEnd=:"+Result_ScopeProcEnd);

		String Result_ScopePreclean=WF_V.Verify_PreClean(ScopePreclean);
		temp=Result_ScopePreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopePreclean,OverallResult);
		System.out.println("Result_ScopePreclean=:"+Result_ScopePreclean);

		String Result_ScopeStaffPreclean=WF_V.Verify_PreCleanStaff("");		
		temp=Result_ScopeStaffPreclean.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeStaffPreclean,OverallResult);
		System.out.println("Result_ScopeStaffPreclean=:"+Result_ScopeStaffPreclean);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope_PRIn="Result_RefNum="+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". "
				+ "Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_ScopePR="+Result_ScopePR+". Result_ScopeExamDate="+Result_ScopeExamDate
				+". Result_ScopeInStaff="+Result_ScopeInStaff+". Result_ScopePhy="+Result_ScopePhy+". Result_ScopePatient="+Result_ScopePatient
				+". Result_ScopeProcStart="+Result_ScopeProcStart+". Result_ScopeProcEnd="+Result_ScopeProcEnd+". Result_ScopePreclean="+Result_ScopePreclean
				+". Result_ScopeStaffPreclean="+Result_ScopeStaffPreclean;
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope_PRIn);
		result=result+Result_SRM_Scope_PRIn;
		
		//MAM verification
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;

		String resultScope_MAM="Scope MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID==-"+
				";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope_MAM);
		result=result+resultScope_MAM;

	}

	public void e_NoStaff() throws InterruptedException{
		NoStaff="Yes";
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Description="No Staff scan is done";
		//System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoStaffCultResDone() throws InterruptedException{
		NoStaff="Yes";
		CulturePath="Yes";
		//System.out.println(getCurrentElement().getName());
		Description="No Staff scan is done after cult Res key entry is done";
		//System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoStaffClose() throws InterruptedException{
		NoStaffClose="Yes";
		//System.out.println(getCurrentElement().getName());
		Description="No Staff scan is done afte preclean scope is done";
		//System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_NoKeyEntry() throws InterruptedException{
		NoStaff="NoKey";
		CultureResult="Yes";
		//System.out.println(getCurrentElement().getName());
		Scope="Scanned";
		Culture="Awaiting";
		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
					+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
					+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK =7 and Stat.LocationID_FK in (31,32,33,34,81)"
					+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK =5 "
					+ "and OtherScopeStateID_FK =7 and LocationID_FK in (31,32,33,34,81))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_ID_rs = statement.executeQuery(stmt);
			while(Scope_ID_rs.next()){
				ScanScope = Scope_ID_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanScopeSerNum = Scope_ID_rs.getString(2);
				ScopeID = Scope_ID_rs.getInt(3);
				AssociationID = Scope_ID_rs.getInt(4);
				CycleID= Scope_ID_rs.getInt(5);
			}		
			Scope_ID_rs.close();
			//System.out.println("Scope= "+ScanScope);
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			
			stmt1="select AssociationID_FK from ItemHistory where ScanItemID_FK="+ScopeID+" and CycleEventID_FK=30";
			//System.out.println(stmt1);

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
		//Scanner="Procedure Room 3";
		if (Scanner.equals("")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=2 and "
						+ "Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)"; 
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2);
					Facility = Scanner_ID_rs.getString(3);
				}		
				Scanner_ID_rs.close();
				//System.out.println("Scanner= "+Scanner);
				//System.out.println("LocationID_PK= "+LocationID_PK);
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
		}
		//Get the scopes which are having culture not done
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//System.out.println(getCurrentElement().getName());
		
		Description="No Key Entry is done for culture awaiting Scope";
		//System.out.println(Description);
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void v_PR_Scp1_In(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
		ScanScope=Multi.PRScope1Name;
		ScanScopeSerNum=Multi.PRScope1SerialNo;
		ScopeHangTime=Multi.Scope1HangTime;
		
	}		
	public void v_Scp1_InPR(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
		ScanScope=Multi.PRScope1Name;
		ScanScopeSerNum=Multi.PRScope1SerialNo;
		ScopeHangTime=Multi.Scope1HangTime;
	}
	public void v_PR_Scp2_In(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Multi.PRScope2Name;
		ScanScopeSerNum=Multi.PRScope2SerialNo;
		Scanner=Multi.PRScanner;
		ScopeHangTime=Multi.Scope2HangTime;
	}
	public void v_Scp2_InPR(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Multi.PRScope2Name;
		ScanScopeSerNum=Multi.PRScope2SerialNo;
		Scanner=Multi.PRScanner;
		ScopeHangTime=Multi.Scope2HangTime;
	}
	public void v_PatientInto_PR(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}		
	public void v_PatientIn_PR(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}
	public void v_Phy_Into_PR(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}		
	public void v_Phy_In_PR(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}
	public void v_ProcStart(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}
	public void v_ProcStarted(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}
	public void v_EndProc(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}
	public void v_ProcEnded(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}

	public void v_PR_Scp1_Out(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
		ScanScope=Multi.PRScope1Name;
		ScanScopeSerNum=Multi.PRScope1SerialNo;
		ScopeHangTime=Multi.Scope1HangTime;
	}
	public void v_Scope1AwaitingMC(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
		ScanScope=Multi.PRScope1Name;
		ScanScopeSerNum=Multi.PRScope1SerialNo;
		ScopeHangTime=Multi.Scope1HangTime;
	}
	public void v_PR_Scp2_Out(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Multi.PRScope2Name;
		ScanScopeSerNum=Multi.PRScope2SerialNo;
		Scanner=Multi.PRScanner;
		ScopeHangTime=Multi.Scope2HangTime;
	}
	public void v_Scope2AwaitingMC(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
		ScanScope=Multi.PRScope2Name;
		ScanScopeSerNum=Multi.PRScope2SerialNo;
		ScopeHangTime=Multi.Scope2HangTime;
	}
	public void v_PR_NeedsCleaning(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}
	public void v_PRCleaning(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}
	public void v_PR_Available(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}
	public void v_PR_Reset(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		Scanner=Multi.PRScanner;
	}

}
