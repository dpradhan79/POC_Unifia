package ScannerMessages.ReprocessingRoom;

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
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminScannerPage.*; 
import Regression.MultiRoom.MultiRoomController_API;


public class RegressionReprocessingRoom_GWAPI  extends ExecutionContext{
	
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
	public GeneralFunc gf;
	public 	TestFrameWork.Emulator.GetIHValues IHV;
	public Regression.MultiRoom.MultiRoomController_API Multi;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	public static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	public static TestFrameWork.UnifiaAdminReconMRC.MRC_Verification MRC_V;

	public Boolean MultiRoom=false;
	public Boolean Res;
	public int Scenario=0;
	public String ScanScopeSerNum;
	public int ScopeHangTime=0;
	public static String actualResult="\t\t\t ScanMessages_ReprocessingRoom_TestSummary \n"; 
	public String Description;
	public String ForFileName;
	public String FileName="";
	public String Expected;

	public String TestResFileName="ScanMessages_ReprocessingRoom_TestSummary_";	
	public String ScanScope="",Scanner="",Facility="";
	public String WaitTimeinMins="";
	public String ScanStaff="";
	public String StaffFirstName ="", StaffLastName="", ScanStaffID="";
	public String ScopeStatus="";
	public String Reason="",MRCMsg="",ReasonName="";
	public Connection conn= null;
	public String stmt,stmt2;
	public ResultSet WaitingTime_RS,Staff_rs,Scanner_ID_rs,Scope_ID_rs,Reason_rs, OtherScopeState_rs;
	public int LocationID_PK;
	public int ScopeID;
	public int BarcodeID;
	public String ExpectedRepro;
	public String ResultReproCount;
	public String ResultLastStaff;
	public int StaffPK=0;
	public int LastScanStaffID_FK;
	
	public String Scope_IH[];
	public String Staff_IH[];

	public String ScopeInIH;
	public String Scope1InAssociationID;
	public String Scope2InAssociationID;
	public String StaffInIH;
	public String StaffIn_Assoc1;
	public String StaffIn_Assoc2;
	public String ReasonIH;
	public String Reason_Assoc1;
	public String Reason_Assoc2;
	
	public String MRC_IH;
	public String MRCAssociationID;

	public String StaffMRCIH;
	public String StaffMRC_Assoc;
	
	
	public String ScopeOutIH;
	public String Scope1OutAssociationID;
	public String Scope2OutAssociationID;
	public String StaffOutIH;
	public String StaffOut_Assoc1;
	public String StaffOut_Assoc2;
	
	public String ResultScopeInCycle;
	public String ResultScopeInLoc;
	public String ResultScopeInState;
	public String ResultReproIn;
	public String ResultStaffIn;
	public String ResultReason;
	public String ResultReasonCycle;
	public String ResultMRC;
	public String ResultMRCCycle;
	public String ResultStaffMRC;
	
	public String ResultScopeOutCycle;
	public String ResultScopeOutLoc;
	public String ResultScopeOutState;
	public String ResultReproOut;
	public String ResultStaffOut;
	
	public String CycleEvent;
	public String ExpectedCabinet;
	public String ActualScopeState;
	public String ExpectedState;
	public int OtherScopeStateID;
	public String ActualSubloc;
	public String ActualOtherScopeState;
	public String ActualCycleEvent;
	public String ScopeInLoc;
	public String ScopeOutLoc;
	public String ActualReproCount;
	public String ActualExamCount;

	private String [] temp= new String[2];
	private String OverallResult="Pass";
	private String Scope1RefNo="";
	private String Scope1SerialNo="";
	private String Scope1Model="";
	private String Scope1ReproInTime="";
	private String ExpectedExamCount;
	private String MRC="";
	private String MRCTime="";
	private String StaffID="";
	private String OutStaffID="";
	public static String result="";
	public String Changes="No";
	String ResultScopeIn1StaffCycle="";
	String ResultScopeOut1StaffCycle="";

	public void e_Start(){
		//empty edge for graphml navigation
		//empty edge for graphml navigation
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		Unifia_Admin_Selenium.ScannerCount=0;
		FileName="CycleMgmtReprocessingArea_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
	}
	
	public void e_Scope() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());

		if(FileName.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date);
			FileName="CycleMgmtReprocessingArea_Regression_";
			FileName=IHV.Start_Exec_Log(FileName);
			Unifia_Admin_Selenium.XMLFileName=FileName;
			//Unifia_Admin_Selenium.TestCaseNumber=1;
		}
		//System.out.println(getCurrentElement().getName());
		ScopeStatus="Normal";

		if(MultiRoom==false){
			try{ //Get a value that exists in Unifia to modify.
				//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				//stmt="select ScopeName,SerialNumber from Scope where TestKeyword='ScannerMessage' and CultureResultGot ='NA' and CultureAwaiting='NA' and UpdateDate=(Select Min(UpdateDate) from Scope Where TestKeyword='ScannerMessage' and CultureResultGot ='NA' and CultureAwaiting='NA')"; //put sql statement here to find ID
				stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; //put sql statement here to find ScopeName
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
				//update.execute("Update Scope set UpdateDate=CURRENT_TIMESTAMP WHERE ScopeName='"+ScanScope+"';");
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
					//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
					//stmt="select actual_ScanName from Scanner where TestKeyword='ScannerMessage' and actual_LocID ='5' and UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='ScannerMessage' and actual_LocID ='5' )"; //put sql statement here to find ID
					conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
					stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=5 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=5 and IsActive=1)"; //put sql statement here to find ID
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
					System.out.println("Scanner= "+Scanner);
					//update.execute("Update Scanner set UpdateDate=CURRENT_TIMESTAMP WHERE actual_ScanName='"+Scanner+"';");
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
		}else {
			try{
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select OtherScopeStateID_FK from ScopeStatus where ScopeID_FK="+ScopeID+";"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement(); //OtherScopeState_rs
				OtherScopeState_rs = statement.executeQuery(stmt);
				while(OtherScopeState_rs.next()){
					OtherScopeStateID = OtherScopeState_rs.getInt(1);
				}		
				OtherScopeState_rs.close();
				System.out.println("OtherScopeStateID= "+OtherScopeStateID);
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
	
	public void e_ScopeBioFail() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//Scope="Scanned";
		ScopeStatus="BioFail";
		try{ //Get a value that exists in Unifia to modify.
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			//stmt="select ScopeName,SerialNumber from Scope where TestKeyword='ScannerMessage' and CultureResultGot ='NA' and CultureAwaiting='NA' and UpdateDate=(Select Min(UpdateDate) from Scope Where TestKeyword='ScannerMessage' and CultureResultGot ='NA' and CultureAwaiting='NA')"; //put sql statement here to find ID
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; //put sql statement here to find ScopeName
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
			//update.execute("Update Scope set UpdateDate=CURRENT_TIMESTAMP WHERE ScopeName='"+ScanScope+"';");
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
				//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				//stmt="select actual_ScanName from Scanner where TestKeyword='ScannerMessage' and actual_LocID ='5' and UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='ScannerMessage' and actual_LocID ='5' )"; //put sql statement here to find ID
	   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=5 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=5 and IsActive=1)"; //put sql statement here to find ID
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
				System.out.println("Scanner= "+Scanner);
				//update.execute("Update Scanner set UpdateDate=CURRENT_TIMESTAMP WHERE actual_ScanName='"+Scanner+"';");
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
		//Make the Scope bio fail
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Bioburden1", "Scope", "", ScanScope, "");
		if(Unifia_Admin_Selenium.ScannerCount==1){
			Thread.sleep(20000);
		}else{
			Thread.sleep(1000);
		}
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Bioburden1", "Bioburden Testing", "", "Fail", "");
		Thread.sleep(1000);
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Bioburden1", "Staff", "Tech", "Tech1 Tech1(T01)", "");
		Thread.sleep(1000);
		//System.out.println(Res);	
		//Scan the scope in Reprocessing Area
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeMCMorethan1hr() throws InterruptedException, SQLException{
		//System.out.println(getCurrentElement().getName());
		ScopeStatus="WaitingAfterMC";
		try{ //Get a value that exists in Unifia to modify.
			//stmt="select ScopeName,SerialNumber from Scope where TestKeyword='ScannerMessage' and CultureResultGot ='NA' and CultureAwaiting='NA' and UpdateDate=(Select Min(UpdateDate) from Scope Where TestKeyword='ScannerMessage' and CultureResultGot ='NA' and CultureAwaiting='NA')"; //put sql statement here to find ID
			////System.out.println(stmt);
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; //put sql statement here to find ScopeName
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
			//update.execute("Update Scope set UpdateDate=CURRENT_TIMESTAMP WHERE ScopeName='"+ScanScope+"';");
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
				//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				//stmt="select actual_ScanName from Scanner where TestKeyword='ScannerMessage' and actual_LocID ='5' and UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='ScannerMessage' and actual_LocID ='5' )"; //put sql statement here to find ID
				////System.out.println(stmt);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=5 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=5 and IsActive=1)"; //put sql statement here to find ID
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
					Facility= Scanner_ID_rs.getString(3);
				}		
				Scanner_ID_rs.close();
				//System.out.println("Scanner= "+Scanner);
				//update.execute("Update Scanner set UpdateDate=CURRENT_TIMESTAMP WHERE actual_ScanName='"+Scanner+"';");
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
		//Make the Scope Manual Clean and more than 1 hour awaiting after manual clean end
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Sink 1", "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Sink 1", "Test Result", "", "Leak Test Pass", "");
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Sink 1", "Staff", "Tech", "Tech1 Tech1(T01)", "");
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Sink 1", "Workflow Event","","Manual Clean Start","");
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem("Sink 1", "Workflow Event","","Manual Clean End","");
		//System.out.println(Res);
		//Make the scope's manual clean end  done past 1 hour
		try{ //Get a value that exists in Unifia to modify.
			Thread.sleep(2000);
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="update itemhistory set ReceivedDateTime = DATEADD(hh,-1,GETDATE()),LastUpdatedDateTime = DATEADD(hh,-1,GETDATE()), ProcessedDateTime = DATEADD(hh,-1,GETDATE()) where ScanItemId_FK=2 and ScanItemTypeID_FK=7  and ItemHistoryID_PK=(Select max (ItemhistoryID_PK) from itemhistory where scanItemID_fK=2 and ScanItemTypeID_FK=7 )";
			//System.out.println(stmt);
			Statement update = conn.createStatement();
			update.execute(stmt);	
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		//Scan the scope in Reprocessing Area
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reason() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select BarcodeID_PK,Name from Barcode where BarcodeTypeID_FK=3 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Barcode where BarcodeTypeID_FK=3 and IsActive=1)"; 
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Reason_rs = statement.executeQuery(stmt);
			while(Reason_rs.next()){
				BarcodeID = Reason_rs.getInt(1); //the first variable in the set is the BarcodeID_PK row in the database.
				ReasonName= Reason_rs.getString(2); //the second variable in the set is the Barcode Name row in the database.
			}		
			Reason_rs.close();
			//System.out.println("ReasonName = "+ReasonName);
			update.execute("Update Barcode set LastUpdatedDateTime=GETUTCDATE() WHERE BarcodeID_PK="+BarcodeID+";");
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

		
		if(ReasonName.equalsIgnoreCase("Return from Repair")){
			Reason="Return From R";
		} else if(ReasonName.equalsIgnoreCase("New Scope")){
			Reason="New Scope";
		}else if(ReasonName.equalsIgnoreCase("Exceeded Hang Time")){
			Reason="Exceeded Hang";
		}else if(ReasonName.equalsIgnoreCase("Reprocessor Error")){
			Reason="Reprocessor E";
		}else if(ReasonName.equalsIgnoreCase("MRC Failed")){
			Reason="MRC Failed";
		}
		//Scan the Reason for Reprocessing 
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Reason for Reprocessing", "", ReasonName, "");
		//System.out.println(Res);
		Description="Scan of Reason for Reprocessing "+ReasonName+" is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SkipReason() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Reason="Skipped";
	}
	
	public void e_Staff() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		try{ //Get a value that exists in Unifia to modify.
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			//stmt="select StaffID,FirstName,LastName from Staff where TestKeyword='ScannerMessage' and StaffType='Tech' and UpdateDate=(Select Min(UpdateDate) from Staff Where TestKeyword='ScannerMessage' and StaffType='Tech')"; //put sql statement here to find Staff details
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
			//System.out.println("StaffID = "+ScanStaffID);
			//update.execute("Update Staff set UpdateDate=CURRENT_TIMESTAMP WHERE TestKeyword='ScannerMessage' and StaffID='"+ScanStaffID+"';");
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
		OutStaffID=ScanStaffID;
		ScanStaff=StaffFirstName+" "+StaffLastName+"("+ScanStaffID+")";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" ";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SkipStaff() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ScanStaff="Skipped";
	}

	public void e_MRCPass() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		MRCMsg="MRC Test Passed";
		MRC="MRC Pass";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Test Result", "", MRC, "");
		//System.out.println(Res);
		Description="Scan of 'MRC Pass' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MRCFail() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		MRCMsg="MRC Failed Follow Your Procedures";
		MRC="MRC Fail";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Test Result", "", MRC, "");
		//System.out.println(Res);
		Description="Scan of 'MRC Fail' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffMRCTest() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		
		if(ScanStaff.equals("")||ScanStaff.equals("Skipped")){
			try{ //Get a value that exists in Unifia to modify.
				//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				//stmt="select StaffID,FirstName,LastName from Staff where TestKeyword='ScannerMessage' and StaffType='Tech' and UpdateDate=(Select Min(UpdateDate) from Staff Where TestKeyword='ScannerMessage' and StaffType='Tech')"; //put sql statement here to find Staff details
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
				//System.out.println("StaffID = "+ScanStaffID);
				//update.execute("Update Staff set UpdateDate=CURRENT_TIMESTAMP WHERE TestKeyword='ScannerMessage' and StaffID='"+ScanStaffID+"';");
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
		OutStaffID=ScanStaffID;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" after MRCTest is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SkipStaffMRCTest() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ScanStaff="Skipped";
	}

	public void e_ScopeEndRep() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ScanStaffID=""; //Staff value in MAM screen will become blank when scope is scanned into a location
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner+" for closing Reprocessing";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffEndRep() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		
		if(ScanStaff.equals("")||ScanStaff.equals("Skipped")){
			try{ //Get a value that exists in Unifia to modify.
				//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				//stmt="select StaffID,FirstName,LastName from Staff where TestKeyword='ScannerMessage' and StaffType='Tech' and UpdateDate=(Select Min(UpdateDate) from Staff Where TestKeyword='ScannerMessage' and StaffType='Tech')"; //put sql statement here to find Staff details
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Staff_rs = statement.executeQuery(stmt);
				while(Staff_rs.next()){
					OutStaffID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
					StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
					StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
				}		
				Staff_rs.close();
				//System.out.println("StaffID = "+ScanStaffID);
				//update.execute("Update Staff set UpdateDate=CURRENT_TIMESTAMP WHERE TestKeyword='ScannerMessage' and StaffID='"+ScanStaffID+"';");
				update.execute("Update Staff set LastUpdatedDateTime=GETUTCDATE() WHERE StaffID='"+OutStaffID+"';");
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
			ScanStaff=StaffFirstName+" "+StaffLastName+"("+OutStaffID+")";
		}
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" for closing Reprocessing";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SkipStaffEndRep() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ScanStaff="Skipped";
	}


	
	public void e_Reset() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());	
		//Variables to default state
		ScopeStatus="";
		Scanner="";
		ScanScope="";
		ScanScopeSerNum="";
		Reason="";
		ScanStaff="";
		ScanStaffID="";
		StaffFirstName="";
		StaffLastName="";
		MRCMsg="";
		WaitTimeinMins="";
		Scope1RefNo="";
		Scope1SerialNo="";
		Scope1Model="";
		Scope1ReproInTime="";
		MRC="";
		StaffID="";
		OutStaffID="";
		Facility="";
	}
	
	public void e_NoStaff_MRCPass() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		MRCMsg="MRC Test Passed";
		MRC="MRC Pass";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Test Result", "", MRC, "");
		//System.out.println(Res);
		Description="Scan of 'MRC Pass' is done in "+ Scanner +" without scanning Staff";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoStaff_MRCFail() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		MRCMsg="MRC Failed Follow Your Procedures";
		MRC="MRC Fail";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Test Result", "", MRC, "");
		//System.out.println(Res);
		Description="Scan of 'MRC Fail' is done in "+ Scanner +" without scanning Staff";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoStaff_ScopeEndRep() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ScanStaffID=""; //Staff value in MAM screen will become blank when scope is scanned into a location
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner+" for closing Reprocessing without Scanning Staff";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoStaff_EndRep() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());	
		//Variables to default state
		ScopeStatus="";
		Scanner="";
		ScanScope="";
		ScanScopeSerNum="";
		Reason="";
		ScanStaff="";
		ScanStaffID="";
		StaffFirstName="";
		StaffLastName="";
		MRCMsg="";
		WaitTimeinMins="";
		Scope1RefNo="";
		Scope1SerialNo="";
		Scope1Model="";
		Scope1ReproInTime="";
		MRC="";
		StaffID="";
		OutStaffID="";
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



	public void v_ReprocessingArea(){
		//System.out.println(getCurrentElement().getName());
		Scenario=Scenario+1;
		System.out.println("-----------------------------");
		System.out.println("Start of new Scenario "+Scenario);
		Description ="Start of new Scenario "+Scenario;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}
	}
	
	public void v_ScopeInRepArea() throws InterruptedException, SQLException, AWTException{
		String ExpReproReason="",Description1="";
		if(MultiRoom==false){
			String ExpMsg="",ExpMsg2="", ExpMsg3="";
			Integer IntWaitTime=0,IntWaitTime2=0;
			//System.out.println(getCurrentElement().getName());
			Expected="Scope Scanned into reprocessor";
			//Verify the scan message received is correct
			if (ScopeStatus.equals("Normal")){
				ExpMsg=ScanScopeSerNum+" Reprocessing Started";
				ExpReproReason="New Scope";
			}else if (ScopeStatus.equals("BioFail")) {
				ExpMsg="Check Scope Bioburden Test Record";
				ExpReproReason="";
			}else if(ScopeStatus.equals("WaitingAfterMC")){
				ExpReproReason="New Scope";
				try{
					Thread.sleep(2000);
					conn=DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
					Statement statement = conn.createStatement();
					stmt2="select datediff(mi, Itemhistory.ReceivedDateTime,GETUTCDATE()) from Itemhistory where ScanItemId_FK=2 and ScanItemTypeID_FK=7  and ItemHistoryID_PK=(Select max (ItemhistoryID_PK) from itemhistory where scanItemID_fK=2 and ScanItemTypeID_FK=7 )";
					//System.out.println(stmt2);
					WaitingTime_RS=statement.executeQuery(stmt2);
					while(WaitingTime_RS.next()){
						WaitTimeinMins = WaitingTime_RS.getString(1); //the first variable in the set is the StaffID row in the database.
						IntWaitTime=WaitingTime_RS.getInt(1);
					}
					conn.close();
				}catch (SQLException ex){
		    	    // handle any errors
		    	    System.out.println("SQLException: " + ex.getMessage());
		    	    System.out.println("SQLState: " + ex.getSQLState());
		    	    System.out.println("VendorError: " + ex.getErrorCode());	
		    	}
				ExpMsg= WaitTimeinMins+" Minutes Since Manual Cleaning";
				IntWaitTime=IntWaitTime-1;
				ExpMsg2= Integer.toString(IntWaitTime)+" Minutes Since Manual Cleaning";
			}
			if (WaitTimeinMins.equals("")){
				Res = EM_V.VerifyScanMsg(ExpMsg, Unifia_Admin_Selenium.ScannerCount);
				
			}else{
				Res = EM_V.VerifyScanMsg(ExpMsg, Unifia_Admin_Selenium.ScannerCount);
				if (!Res){
					//System.out.println(Res +"Error due to difference of time and recomparing by decreasing 1 minute");
					Res = EM_V.VerifyScanMsg(ExpMsg2, Unifia_Admin_Selenium.ScannerCount);
				}
			}
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		CycleEvent="Reprocessing In";
		String ActualReason=null;
		if(MultiRoom==false){
			if (!ExpReproReason.equals("")){
				Scope_IH=IHV.GetReprocessorData(Unifia_Admin_Selenium.connstring, Scanner);
				//Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				ScopeInIH=Scope_IH[0];
				Scope1InAssociationID=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				
				Description1="Reason for Reprocessing - '"+ExpReproReason+"' is derived in "+ Scanner;
				String CycleEvent2="Reason For Reprocessing";
				ActualReason=IHV.GetReasonForReprocessing(Unifia_Admin_Selenium.connstring, Scope1InAssociationID);
				
			}else{
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				ScopeInIH=Scope_IH[0];
				Scope1InAssociationID=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				Description1="There is no derived reason for reprocessing in "+ Scanner;
			}
			

		}else{
			if(ScanScope.equalsIgnoreCase(Multi.ReproScope1Name)){
				//Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				
				if (!ExpReproReason.equals("")){
					Scope_IH=IHV.GetReprocessorData(Unifia_Admin_Selenium.connstring, Scanner);
					ScopeInIH=Scope_IH[0];
					Scope1InAssociationID=Scope_IH[1];
					ActualCycleEvent=Scope_IH[5];
					//System.out.println(ScopeInIH+" = Scope into Reprocessor ItemHistory_PK");
					Description1="Reason for Reprocessing - '"+ExpReproReason+"' is derived in "+ Scanner;
					String CycleEvent2="Reason For Reprocessing";
					ActualReason=IHV.GetReasonForReprocessing(Unifia_Admin_Selenium.connstring, Scope1InAssociationID);
				}else{
					Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
					ScopeInIH=Scope_IH[0];
					Scope1InAssociationID=Scope_IH[1];
					ActualCycleEvent=Scope_IH[5];
					Description1="There is no derived reason for reprocessing in "+ Scanner;
				}
				
			}else if(ScanScope.equalsIgnoreCase(Multi.ReproScope2Name)){
				//Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				if (!ExpReproReason.equals("")){
					Scope_IH=IHV.GetReprocessorData(Unifia_Admin_Selenium.connstring, Scanner);
					ScopeInIH=Scope_IH[0];
					Scope2InAssociationID=Scope_IH[1];
					ActualCycleEvent=Scope_IH[5];
					//System.out.println(ScopeInIH+" = Scope into Reprocessor ItemHistory_PK");
					Description1="Reason for Reprocessing - '"+ExpReproReason+"' is derived in "+ Scanner;
					String CycleEvent2="Reason For Reprocessing";
					ActualReason=IHV.GetReasonForReprocessing(Unifia_Admin_Selenium.connstring, Scope1InAssociationID);
				}else{
					Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
					ScopeInIH=Scope_IH[0];
					Scope2InAssociationID=Scope_IH[1];
					ActualCycleEvent=Scope_IH[5];
					Description1="There is no derived reason for reprocessing in "+ Scanner;
				}
			}
		}
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		ExpectedState="0";
		if(MultiRoom==false){
			OtherScopeStateID=0;
		}
		ExpectedCabinet="0";
		StaffPK=0;
		ExpectedRepro=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedRepro);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		
		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultReproIn="ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultLastStaff="+ResultLastStaff
				+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState;
		//System.out.println(Scenario+":  "+ResultReproIn);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultReproIn);
		
		if (!ExpReproReason.equalsIgnoreCase("")){
			String ResultReason1=IHV.Result_Same_Reason(ExpReproReason,ActualReason);
			String[] temp = ResultReason1.split("-");
			IHV.Exec_Log_Result(FileName, Description1, ExpReproReason, ResultReason1);
		}else{
			IHV.Exec_Log_Result(FileName, Description1, "No derived Reason is expected here", "Pass - No derived Reason");
		}
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,Scope1InAssociationID);
		Scope1RefNo=ScopeInfo[0];
		Scope1SerialNo=ScopeInfo[1];
		Scope1Model=ScopeInfo[2];
		Scope1ReproInTime=ScopeInfo[3];
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" and Staff "+ScanStaffID+" into "+Scanner+". ";

		String Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
		
		String Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		String Result_ScopeName1=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		//System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

		String Result_Scope1Reprocessor=WF_V.Verify_Reprossor(Scanner);
		System.out.println("Result_Scope1Reprocessor=:"+Result_Scope1Reprocessor);
		temp=Result_Scope1Reprocessor.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Reprocessor,OverallResult);

		String Result_Scope1ReproReason=WF_V.Verify_ReproReason(ExpReproReason);
		System.out.println("Result_Scope1ReproReason=:"+Result_Scope1ReproReason);
		temp=Result_Scope1ReproReason.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproReason,OverallResult);

		String Result_Scope1ReproInStaff=WF_V.Verify_ReproInStaff(ScanStaffID);
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
		
		String Result_Scope1ReproOutStaff=WF_V.Verify_ReproOutStaff(ScanStaffID);
		System.out.println("Result_Scope1ReproOutStaff=:"+Result_Scope1ReproOutStaff);
		temp=Result_Scope1ReproComplete.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutStaff,OverallResult);
		
		String Result_Scope1ReproOutTime =WF_V.Verify_ReproScopeOutTime("");
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
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope1_ReproIn);
		result=result+Result_SRM_Scope1_ReproIn;
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;
		if(ScanStaffID.equalsIgnoreCase("")){
			StaffID="-";
		}else{
			StaffID=ScanStaffID;
		}
		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedRepro).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope1_MAM);
		result=result+resultScope1_MAM;

	}
	
	public void v_ReasonforRep() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(!Reason.equalsIgnoreCase("Skipped")){			
			Res = EM_V.VerifyScanMsg(Reason, Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Expected="Reason for Reprocessing Scanned into reprocessor";
		
			CycleEvent="Reprocessing Reason";
			if(MultiRoom==false){
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				ReasonIH=Scope_IH[0];
				Reason_Assoc1=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				//System.out.println(ScopeInIH+" = Scope into Reprocessor ItemHistory_PK");
				ResultReason=IHV.Result_Same_Assoc(Scope1InAssociationID,Reason_Assoc1);
			}else{
				if(ScanScope.equalsIgnoreCase(Multi.ReproScope1Name)){
					Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
					ReasonIH=Scope_IH[0];
					Reason_Assoc1=Scope_IH[1];
					ActualCycleEvent=Scope_IH[5];
					//System.out.println(ScopeInIH+" = Scope into Reprocessor ItemHistory_PK");
	
					ResultReason=IHV.Result_Same_Assoc(Scope1InAssociationID,Reason_Assoc1);
				}else if(ScanScope.equalsIgnoreCase(Multi.ReproScope2Name)){
					Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
					ReasonIH=Scope_IH[0];
					Reason_Assoc2=Scope_IH[1];
					ActualCycleEvent=Scope_IH[5];
					//System.out.println(ScopeInIH+" = Scope into Reprocessor ItemHistory_PK");
	
					ResultReason=IHV.Result_Same_Assoc(Scope2InAssociationID,Reason_Assoc2);
				}
			}
	
			ResultReasonCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			ResultReason=ResultReason+" for Scope into Reprocessor and reason for reprocessing. CycleEvent is also correct = "+ResultReasonCycle;
			//System.out.println(Scenario+":  "+ResultReason);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultReason);
		}
	}
	
	public void v_StaffStartRep() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		if(!ScanStaff.equalsIgnoreCase("Skipped")){			

			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Staff "+ScanStaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Expected="Staff Scanned into reprocessor";
			
			if(MultiRoom==false){
				//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
				CycleEvent="ReprocessingInStaff";
				Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
				StaffInIH=Staff_IH[0];
				StaffIn_Assoc1=Staff_IH[1];
				ActualCycleEvent=Staff_IH[5];
				
				ResultScopeIn1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
				temp=ResultScopeIn1StaffCycle.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultScopeIn1StaffCycle,OverallResult);

				ResultStaffIn=IHV.Result_Same_Assoc(Scope1InAssociationID,StaffIn_Assoc1)+" for staff and scope into reprocessor. ResultScopeIn1StaffCycle="+ResultScopeIn1StaffCycle;
			}else{
				if(ScanScope.equalsIgnoreCase(Multi.ReproScope1Name)){
					//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
					CycleEvent="ReprocessingInStaff";
					Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
					StaffInIH=Staff_IH[0];
					StaffIn_Assoc1=Staff_IH[1];
					ActualCycleEvent=Staff_IH[5];
					
					ResultScopeIn1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
					ResultStaffIn=IHV.Result_Same_Assoc(Scope1InAssociationID,StaffIn_Assoc1)+" for staff and scope into reprocessor. ResultScopeIn1StaffCycle="+ResultScopeIn1StaffCycle;
				}else if(ScanScope.equalsIgnoreCase(Multi.ReproScope2Name)){
					//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
					CycleEvent="ReprocessingInStaff";
					Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
					StaffInIH=Staff_IH[0];
					StaffIn_Assoc2=Staff_IH[1];
					ActualCycleEvent=Staff_IH[5];
					
					ResultScopeIn1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
					ResultStaffIn=IHV.Result_Same_Assoc(Scope2InAssociationID,StaffIn_Assoc2)+" for staff and scope into reprocessor. ResultScopeIn1StaffCycle="+ResultScopeIn1StaffCycle;
				}
			}
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,ScanStaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);

			ResultStaffIn=ResultStaffIn+" ResultLastStaff="+ResultLastStaff;

			//System.out.println(Scenario+":  "+ResultStaffIn);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffIn);
		}
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" and Staff "+ScanStaffID+" into "+Scanner+". ";

		String Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
		
		String Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		String Result_ScopeName1=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);
		
		String Result_Scope1Reprocessor=WF_V.Verify_Reprossor(Scanner);
		System.out.println("Result_Scope1Reprocessor=:"+Result_Scope1Reprocessor);
		temp=Result_Scope1Reprocessor.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Reprocessor,OverallResult);

		String Result_Scope1ReproInStaff=WF_V.Verify_ReproInStaff(ScanStaffID);
		System.out.println("Result_Scope1ReproInStaff=:"+Result_Scope1ReproInStaff);
		temp=Result_Scope1ReproInStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproInStaff,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope1_ReproIn="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1Reprocessor="+Result_Scope1Reprocessor
				+". Result_Scope1ReproInStaff"+Result_Scope1ReproInStaff;
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope1_ReproIn);
		result=result+Result_SRM_Scope1_ReproIn;
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;
		if(ScanStaffID.equalsIgnoreCase("")){
			StaffID="-";
		}else{
			StaffID=ScanStaffID;
		}
		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedRepro).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope1_MAM);
		result=result+resultScope1_MAM;
	}
	
	public void v_MRCTest() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg(MRCMsg, Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		Expected="MRC results scanned for reprocessor";

		CycleEvent="MRC Test";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		MRC_IH=Scope_IH[0];
		MRCAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		
		MRCTime=MRC_A.GetMRCTime(Unifia_Admin_Selenium.connstring, MRCAssociationID); 
		
		//System.out.println(ScopeInIH+" = Scope into Reprocessor ItemHistory_PK");
		ResultMRCCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultMRC=IHV.Result_Different_Assoc(Scope1InAssociationID, MRCAssociationID)+" for ScopeIn and MRC.";

		ResultMRC=ResultMRC+"; MRCAssociationID="+MRCAssociationID+"; ResultMRCCycle"+ResultMRCCycle;
		//System.out.println(Scenario+":  "+ResultMRC);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMRC);		
		
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
			Description="Verify MRC screen for MRC Result of "+Scanner;
			Expected=Description;
			String MRCResult[]=MRC.split(" ");//Taking Pass/Fail from MRC Pass/MRC Fail
			StaffID=" ";
			String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(gridID,"MRC TEST DATE/TIME=="+MRCTime+";REPROCESSOR=="+Scanner+";MRC Test Result=="+MRCResult[1]+";MRC TEST STAFF ID=="+StaffID).toString();
			IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
			result=result+result_MRC;
		}

	}
	
	public void v_StaffMRCTest() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(!ScanStaff.equalsIgnoreCase("Skipped")){			

			Expected="Staff that Scanned MRC for reprocessor";
	
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Staff "+ScanStaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			StaffMRCIH=Staff_IH[0];
			StaffMRC_Assoc=Staff_IH[1];
			ResultStaffMRC=IHV.Result_Same_Assoc(MRCAssociationID,StaffMRC_Assoc)+" for staff and scope into reprocessor.";
			
			//System.out.println(Scenario+":  "+ResultStaffMRC);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffMRC);
			
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
				Description="Verify MRC screen for MRC Result of "+Scanner;
				Expected=Description;
				String MRCResult[]=MRC.split(" ");//Taking Pass/Fail from MRC Pass/MRC Fail
				if(ScanStaffID.equalsIgnoreCase("")){
					StaffID=" ";
				}else{
					StaffID=ScanStaffID;
				}
				String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(gridID,"MRC TEST DATE/TIME=="+MRCTime+";REPROCESSOR=="+Scanner+";MRC Test Result=="+MRCResult[1]+";MRC TEST STAFF ID=="+StaffID).toString();
				IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
				result=result+result_MRC;
			}
		}
	}
	
	public void v_ScopeEndRep() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg(ScanScopeSerNum+" Reprocessing Complete", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		Expected="Scope Scanned out of reprocessor";

		CycleEvent="Reprocessing Out";
		String ResultScopeOutAssoc="";
		if(MultiRoom==false){
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			ScopeOutIH=Scope_IH[0];
			Scope1OutAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeInIH+" = Scope out of Reprocessor ItemHistory_PK");
			ResultScopeOutAssoc=IHV.Result_Different_Assoc(Scope1InAssociationID, Scope1OutAssociationID)+" for Scope in and scope out. "+IHV.Result_Different_Assoc(MRCAssociationID, Scope1OutAssociationID)+" for MRC and Scope out.";
		}else{
			if(ScanScope.equalsIgnoreCase(Multi.ReproScope1Name)){
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				ScopeOutIH=Scope_IH[0];
				Scope1OutAssociationID=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				//System.out.println(ScopeInIH+" = Scope out of Reprocessor ItemHistory_PK");
				ResultScopeOutAssoc=IHV.Result_Different_Assoc(Scope1InAssociationID, Scope1OutAssociationID)+" for Scope in and scope out. "+IHV.Result_Different_Assoc(MRCAssociationID, Scope1OutAssociationID)+" for MRC and Scope out.";
				
			}else if(ScanScope.equalsIgnoreCase(Multi.ReproScope2Name)){
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				ScopeOutIH=Scope_IH[0];
				Scope2OutAssociationID=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				//System.out.println(ScopeInIH+" = Scope out of Reprocessor ItemHistory_PK");
				ResultScopeOutAssoc=IHV.Result_Different_Assoc(Scope2InAssociationID, Scope2OutAssociationID)+" for Scope in and scope out. "+IHV.Result_Different_Assoc(MRCAssociationID, Scope2OutAssociationID)+" for MRC and Scope out.";
				
			}
		}


		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		
		ActualScopeState=Scope_IH[0];
		ScopeOutLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		
		ExpectedState="5";
		StaffPK=0;
		if(MultiRoom==false){
			OtherScopeStateID=0;
		}
		OtherScopeStateID=0;
		ExpectedCabinet="0";
		ExpectedRepro=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		StaffPK=0;
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedRepro);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeOutLoc=IHV.Result_Location(Scanner, ScopeOutLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
				
		ResultReproOut="ResultScopeOutCycle= "+ResultScopeOutCycle+". ResultReproCount="+ResultReproCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeOutLoc= "+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState+". "
				+ "ResultScopeOutAssoc="+ResultScopeOutAssoc;
		//System.out.println(Scenario+":  "+ResultReproOut);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultReproOut);
		
		String []ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,Scope1OutAssociationID);
		String Scope1ReproOutTime=ScopeInfo[3];

		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" and Staff "+ScanStaffID+" into "+Scanner+". ";

		String Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
		
		String Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		String Result_ScopeName1=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		//System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

		String Result_Scope1Reprocessor=WF_V.Verify_Reprossor(Scanner);
		System.out.println("Result_Scope1Reprocessor=:"+Result_Scope1Reprocessor);
		temp=Result_Scope1Reprocessor.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Reprocessor,OverallResult);
		
		String Result_Scope1ReproOutStaff=WF_V.Verify_ReproOutStaff(ScanStaffID);
		System.out.println("Result_Scope1ReproOutStaff=:"+Result_Scope1ReproOutStaff);
		temp=Result_Scope1ReproOutStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutStaff,OverallResult);
		
		String Result_Scope1ReproOutTime =WF_V.Verify_ReproScopeOutTime(Scope1ReproOutTime);
		System.out.println("Result_Scope1ReproOutTime=:"+Result_Scope1ReproOutTime);
		temp=Result_Scope1ReproOutTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutTime,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope1_ReproIn="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1Reprocessor="+Result_Scope1Reprocessor+". Result_Scope1ReproOutStaff="+Result_Scope1ReproOutStaff
				+".  Result_Scope1ReproOutTime="+Result_Scope1ReproOutTime;
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope1_ReproIn);
		result=result+Result_SRM_Scope1_ReproIn;
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;
		if(ScanStaffID.equalsIgnoreCase("")){
			StaffID="-";
		}else{
			StaffID=ScanStaffID;
		}
		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedRepro).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope1_MAM);
		result=result+resultScope1_MAM;
	}
	
	public void v_StaffEndRep() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		if(!ScanStaff.equalsIgnoreCase("Skipped")){			
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Staff "+OutStaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Expected="Staff that Scanned scope out of reprocessor";

			CycleEvent="ReprocessingOutStaff";
			if(MultiRoom==false){
				//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
				Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
				StaffOutIH=Staff_IH[0];
				StaffOut_Assoc1=Staff_IH[1];
				ActualCycleEvent=Staff_IH[5];
				
				ResultScopeOut1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
				temp=ResultScopeIn1StaffCycle.split("-");
				OverallResult=GF.FinalResult(temp[0], ResultScopeIn1StaffCycle,OverallResult);
				
				ResultStaffOut=IHV.Result_Same_Assoc(Scope1OutAssociationID,StaffOut_Assoc1)+" for staff and scope out of the reprocessor. ResultScopeOut1StaffCycle="+ResultScopeOut1StaffCycle;
			}else{
				if(ScanScope.equalsIgnoreCase(Multi.ReproScope1Name)){
					//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
					Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
					StaffOutIH=Staff_IH[0];
					StaffOut_Assoc1=Staff_IH[1];
					ActualCycleEvent=Staff_IH[5];
					
					ResultScopeOut1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
					temp=ResultScopeIn1StaffCycle.split("-");
					OverallResult=GF.FinalResult(temp[0], ResultScopeIn1StaffCycle,OverallResult);

					ResultStaffOut=IHV.Result_Same_Assoc(Scope1OutAssociationID,StaffOut_Assoc1)+" for staff and scope out of the reprocessor.";
				}else if(ScanScope.equalsIgnoreCase(Multi.ReproScope2Name)){
					//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
					Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
					StaffOutIH=Staff_IH[0];
					StaffOut_Assoc2=Staff_IH[1];
					ActualCycleEvent=Staff_IH[5];
					
					ResultScopeOut1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
					temp=ResultScopeIn1StaffCycle.split("-");
					OverallResult=GF.FinalResult(temp[0], ResultScopeIn1StaffCycle,OverallResult);

					ResultStaffOut=IHV.Result_Same_Assoc(Scope2OutAssociationID,StaffOut_Assoc2)+" for staff and scope out of the reprocessor.";				
				}
			}
			
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,OutStaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);

			ResultStaffOut=ResultStaffOut+" ResultLastStaff="+ResultLastStaff;
			//System.out.println(Scenario+":  "+ResultStaffOut);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffOut);
		}
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(Scope1RefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" and Staff "+OutStaffID+" into "+Scanner+". ";

		String Result_RefNum1=WF_V.Verify_RefNum(Scope1RefNo);
		temp=Result_RefNum1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
		
		String Result_ScopeModel1=WF_V.Verify_ScopeModel(Scope1Model);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		String Result_ScopeName1=WF_V.Verify_ScopeName(ScanScope);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(Scope1SerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);

		//System.out.println("Result_RefNum1=:"+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1);

		String Result_Scope1Reprocessor=WF_V.Verify_Reprossor(Scanner);
		System.out.println("Result_Scope1Reprocessor=:"+Result_Scope1Reprocessor);
		temp=Result_Scope1Reprocessor.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1Reprocessor,OverallResult);

		String Result_Scope1ReproOutStaff=WF_V.Verify_ReproOutStaff(OutStaffID);
		System.out.println("Result_Scope1ReproOutStaff=:"+Result_Scope1ReproOutStaff);
		temp=Result_Scope1ReproOutStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_Scope1ReproOutStaff,OverallResult);
		
		WF_A.Cancel(Changes);
		Expected=Description;
		String Result_SRM_Scope1_ReproIn="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1+". Result_ScopeName1="+Result_ScopeName1+". "
				+ "Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_Scope1Reprocessor="+Result_Scope1Reprocessor+". Result_Scope1ReproOutStaff="+Result_Scope1ReproOutStaff;
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SRM_Scope1_ReproIn);
		result=result+Result_SRM_Scope1_ReproIn;
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Description="Verify MAM screen for "+ScanScope+" after scanning into "+Scanner;
		Expected=Description;
		String resultScope1_MAM="Scope1 MAM Result ="+MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+OutStaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedRepro).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, resultScope1_MAM);
		result=result+resultScope1_MAM;
	}
	
	public void v_Repro_Scp1_In(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Multi.ReproScope1Name;
		ScanScopeSerNum=Multi.ReproScope1SerialNo;
		Scanner=Multi.ReproScanner;
		ScopeID=Multi.ReproScope1ScopeID;
	}

	public void v_Scp1_InRepro(){
		//System.out.println(getCurrentElement().getName());
	}

	public void v_Repro_Scp2_In(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Multi.ReproScope2Name;
		ScanScopeSerNum=Multi.ReproScope2SerialNo;
		Scanner=Multi.ReproScanner;
		ScopeID=Multi.ReproScope2ScopeID;
	}
	
	public void v_Scp2_InRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void v_Repro_MRC(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Multi.ReproScope1Name;
		ScanScopeSerNum=Multi.ReproScope1SerialNo;
		Scanner=Multi.ReproScanner;
		ScanScope=Multi.ReproScope2Name;
		ScanScopeSerNum=Multi.ReproScope2SerialNo;
	}

	public void v_MRC_Done(){
		//System.out.println(getCurrentElement().getName());
	}

	public void v_Repro_Scp1_Out(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Multi.ReproScope1Name;
		ScanScopeSerNum=Multi.ReproScope1SerialNo;
		Scanner=Multi.ReproScanner;
	}

	public void v_Scp1_OutRepro(){
		//System.out.println(getCurrentElement().getName());
	}

	public void v_Repro_Scp2_Out(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Multi.ReproScope2Name;
		ScanScopeSerNum=Multi.ReproScope2SerialNo;
		Scanner=Multi.ReproScanner;
	}

	public void v_Scp2_OutRepro(){
		//System.out.println(getCurrentElement().getName());
	}

}
