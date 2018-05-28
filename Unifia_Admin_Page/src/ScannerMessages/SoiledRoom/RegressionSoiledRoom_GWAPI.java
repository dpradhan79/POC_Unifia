package ScannerMessages.SoiledRoom;

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

import TestFrameWork.Unifia_Admin_Selenium;

import java.io.File;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.Emulator_Actions; 
import Regression.MultiRoom.MultiRoomController_API;

public class RegressionSoiledRoom_GWAPI extends ExecutionContext{

	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
	public 	TestFrameWork.Emulator.GetIHValues IHV;
	public Regression.MultiRoom.MultiRoomController_API Multi;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.Unifia_IP.IP_Verification IP_V;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	private static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	private static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Verification MRC_V;
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	
	public String Scope="NotScanned";
	public String Staff="NotScanned";
	public String LeakTest="NotScanned";
	public String MCStart="NotScanned";
	public String MCEnd="NotScanned";
	public String ScanScope="";
	public String ScopeSerialNumber="";
	public int ScopeID;

	public String StaffID="";
	public String StaffFirstName="";
	public String StaffLastName="";
	public String StaffScan;
	public String LeakTestRes="";
	public String MCTimePeriod="";
	public String AssociationID="";
	public Boolean Res;
	public Boolean MultiRoom=false;
	public String Scanner="",Facility="";
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
	public static String actualResult="\t\t\t ScanMessages_ExamQueue_TestSummary \n"; 
	public String Description;
	public String ForFileName;
	public String TestResFileName="ScanMessages_SoliedRoom_TestSummary_";
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
	
	private String ScopeRefNo="";
	private String ScopeSerialNo="";
	private String ScopeModel="";
	private String ScopeSinkInTime="";
	private String [] temp= new String[2];
	private String OverallResult="Pass";
	private String result_MAM;
	

	public void e_Start() throws InterruptedException{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		FileName="CycleMgmt_SoiledRoom_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		//Unifia_Admin_Selenium.ScannerCount=0;
		Unifia_Admin_Selenium.XMLFileName=FileName;
		//Unifia_Admin_Selenium.TestCaseNumber=1;
	}
	
	public void e_ScanScope() throws  InterruptedException{
		Scope="Scanned";
		if(FileName.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date);
			FileName="CycleMgmt_SoiledRoom_Regression_";
			FileName=IHV.Start_Exec_Log(FileName);
			//Unifia_Admin_Selenium.ScannerCount=0;
			Unifia_Admin_Selenium.XMLFileName=FileName;
			Unifia_Admin_Selenium.TestCaseNumber=1;
		}
		//Scope=String.valueOf(getMbt().getDataValue("Scope"));
		//System.out.println(getCurrentElement().getName());
		
		if(MultiRoom==false){
	   		try{ //Get a value that exists in Unifia to modify.
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
				stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; //put sql statement here to find ScopeName
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
					conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
					stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
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

		} 
   		
   		//Scan the Patient
		//Res = EM_A.ScanItem("Sink1", "Scope", "", "Scope1", "");
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Scope '" +ScanScope+"' is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanStaff() throws  InterruptedException{
		Staff="Scanned";
		//Scope=String.valueOf(getMbt().getDataValue("Staff"));
		//System.out.println(getCurrentElement().getName());
		
   		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
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
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", StaffScan, "");
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Staff '" +StaffScan+"' is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTFail() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Fail", "");
		//System.out.println(Res);
		if(Res){
			LeakTest="Scanned";
			LeakTestRes="Fail";
		}
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Test Result - Leak Test Fail is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTPass() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
		//System.out.println(Res);
		if(Res){
			LeakTest="Scanned";
			LeakTestRes="Pass";
		}
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Test Result - Leak Test Pass is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
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
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Description="Scan of Test Result - Leak Test Fail is done after a LT Pass was scanned.";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		}else if(LeakTestRes.equalsIgnoreCase("Fail")){
			Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
			//System.out.println(Res);
			if(Res){
				LeakTest="Scanned";
				LeakTestRes="Pass";
			}
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Description="Scan of Test Result - Leak Test Pass is done after a LT Fail was scanned.";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}

	}
	
	
	public void e_LTFailNoScope() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")&& Scope.equals("NotScanned")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			//Thread.sleep(2000);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
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
   		
   		//Scan the Test Result
		//Res = EM_A.ScanItem("Sink1", "Test Result", "", "Leak Test Fail", "");
		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Fail", "");
		//System.out.println(Res);
		if(Res){
			LeakTest="Scanned";
			LeakTestRes="Fail";
		}
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Test Result - Leak Test Fail is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_LTPassNoScope() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")&& Scope.equals("NotScanned")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			//Thread.sleep(2000);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
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
   		
   		//Scan the Test Result
		//Res = EM_A.ScanItem("Sink1", "Test Result", "", "Leak Test Pass", "");
		Res = EM_A.ScanItem(Scanner, "Test Result", "", "Leak Test Pass", "");
		//System.out.println(Res);
		if(Res){
			LeakTest="Scanned";
			LeakTestRes="Pass";
		}
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Test Result - Leak Test Pass is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MCStart() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		//Scan the Workflow Event
		//Res = EM_A.ScanItem("Sink1", "Workflow Event", "", "Manual Clean Start", "");
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		//System.out.println(Res);
		if(Res){
			MCStart="Scanned";
		}
		if(Scope.equals("Scanned")){
			try{ //Get a value that exists in Unifia to modify.
				Thread.sleep(2000);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
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
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Workflow Event - Manual Clean Start is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MCEnd() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		Thread.sleep(60000);//waiting for 1 minute to get the time difference between Manual clean Start and Manual clean End
		//Scan the Workflow Event
		//Res = EM_A.ScanItem("Sink1", "Workflow Event", "", "Manual Clean End", "");
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
		//System.out.println(Res);
		if(Res){
			MCEnd="Scanned";
		}
		if(Scope.equals("Scanned")){
			try{ //Get a value that exists in Unifia to modify.
				Thread.sleep(2000);
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
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
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Workflow Event - Manual Clean End is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MCStartNoScope() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")&& Scope.equals("NotScanned")&& LeakTest.equals("NotScanned")){
	   		try{ //Get a value that exists in Unifia to modify.
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
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
	   		
	   		//Scan the Workflow Event
			//Res = EM_A.ScanItem("Sink1", "Workflow Event", "", "Manual Clean Start", "");
			Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
			//System.out.println(Res);
			if(Res){
				MCStart="Scanned";
			}
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Description="Scan of Workflow Event - Manual Clean Start is done";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
   		}
	}
	
	public void e_MCEndNoScope() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")&& Scope.equals("NotScanned")&& LeakTest.equals("NotScanned")){
	   		try{ //Get a value that exists in Unifia to modify.
	   			//Thread.sleep(2000);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=4 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
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
	   		
	   		//Scan the Workflow Event
			//Res = EM_A.ScanItem("Sink1", "Workflow Event", "", "Manual Clean End", "");
			Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
			//System.out.println(Res);
			if(Res){
				MCEnd="Scanned";
			}
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Description="Scan of Workflow Event - Manual Clean End is done";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	}
	
	public void e_MCStartNoLTRes() throws InterruptedException, ParseException{
		//System.out.println(getCurrentElement().getName());
		if(LeakTest.equals("NotScanned")){
			//Scan the Workflow Event
			//Res = EM_A.ScanItem("Sink1", "Workflow Event", "", "Manual Clean Start", "");
			Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
			//System.out.println(Res);
			if(Res){
				MCStart="Scanned";
			}
			try{ //Get a value that exists in Unifia to modify.
				Thread.sleep(2000);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
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
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Description="Scan of Workflow Event - Manual Clean Start is done";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	}
	
	public void e_MCEndNoMCStart() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if (MCStart.equals("NotScanned") && (LeakTest.equals("Scanned"))) {
			// Scan the Workflow Event
			// Res = EM_A.ScanItem("Sink1", "Workflow Event", "","Manual Clean End", "");
			Res = EM_A.ScanItem(Scanner, "Workflow Event", "","Manual Clean End", "");
			//System.out.println(Res);
			if (Res) {
				MCEnd = "Scanned";
			}
			Unifia_Admin_Selenium.ScannerCount = Unifia_Admin_Selenium.ScannerCount + 1;
			Description = "Scan of Workflow Event - Manual Clean End is done";
			actualResult = actualResult + "\r\n"+ getCurrentElement().getName() + "---:\r\n\t"+ Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
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
		ScanScope="";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ResetNS5(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_ResetNS4(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_ResetNS3(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ResetNS2(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ResetNS(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MoreThan1hr() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Boolean Res;
   		try{ //Get a value that exists in Unifia to modify.
   			Thread.sleep(2000);
   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement update = conn.createStatement();
			update.execute("update itemhistory set ReceivedDateTime = DATEADD(hh,-1,GETDATE()),LastUpdatedDateTime = DATEADD(hh,-1,GETDATE()), ProcessedDateTime = DATEADD(hh,-1,GETDATE()) where ScanItemId_FK=2 and ScanItemTypeID_FK=7  and ItemHistoryID_PK=(Select max (ItemhistoryID_PK) from itemhistory where scanItemID_fK=2 and ScanItemTypeID_FK=7 )");//Updating the time so as to get the time difference between Manual Clean End and scope scan
			update.close();
   		}catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}		
   		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Scope '" +ScanScope+"' is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ResetTime(){
		//System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Scope="NotScanned";
		Staff="NotScanned";
		LeakTest="NotScanned";
		MCStart="NotScanned";
		MCEnd="NotScanned";
		ScanScope="";
		ScopeSerialNumber="";
		StaffID="";
		StaffFirstName="";
		StaffLastName="";
		LeakTestRes="";
		MCTimePeriod="";
		Scanner="";
		MCStartTime=null;
		MCEndTime=null;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
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

	public void e_Nav1(){
		System.out.println(getCurrentElement().getName());
	}
	public void e_Nav2(){
		System.out.println(getCurrentElement().getName());
	}

	public void v_SoiledRoom(){
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
	
	public void v_ScanScope() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//System.out.println("Scope is "+Scope);
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Scope "+ScopeSerialNumber+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//Verify in DB
		Expected="v_ScanScope: "+ScanScope+" scanned into "+Scanner;
		ExpectedCycleEvent="Scope in Sink";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ScopeInIH=Scope_IH[0];
		ScopeInAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeInIH+" = Scope into Sink Area- ItemHistory_PK");
		//ScopeStatus and location
		Scope_Stat=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		ActualScopeState=Scope_Stat[0];
		ScopeInLoc=Scope_Stat[1];
		ActualOtherScopeState=Scope_Stat[2];
		ActualSubloc=Scope_Stat[3];
		
		StaffPK=0;
		ExpectedState="0";
		if(MultiRoom==false){
			OtherScopeStateID=0;
		}else {
			if(ScanScope.equalsIgnoreCase(Multi.SoiledScope1Name)){
				Multi.SoiledScope1Association=ScopeInAssociationID;
			}else if(ScanScope.equalsIgnoreCase(Multi.SoiledScope2Name)){
				Multi.SoiledScope2Association=ScopeInAssociationID;
			}
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

		}
		ExpectedCabinet="0";
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		
		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultSR="ResultScopeInCycle= "+ResultScopeInCycle+". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState;
		//System.out.println(Scenario+":  "+ResultSR);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
		
		//SRM validations when the scope is scanned into Soiled Area
		Description="Verify Scope Record Management of "+ScanScope+" with Scope scanned into "+Scanner;
		Expected =Description;
		String result_ScopeinSA=ValidateSRM(ScopeInAssociationID,ScanScope,Scanner,"","","","");
		IHV.Exec_Log_Result(FileName, Description, Expected, result_ScopeinSA);
		//MAM validations
		Description="Verify Management and Asset Management of "+Scope+" into "+Scanner+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		result_MAM=MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+")").toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);
	}
	
	public void v_ScanStaff() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//System.out.println("Staff is "+StaffID);
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Staff "+StaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//DB Verification
		Expected="v_ScanStaff: "+StaffID+" scanned into "+Scanner;
		//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		ExpectedCycleEvent="SoiledRoomStaff";
		Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
		StaffInIH=Staff_IH[0];
		StaffIn_Assoc=Staff_IH[1];
		ActualCycleEvent=Staff_IH[5];
		
		ResultStaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		temp=ResultStaffCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultStaffCycle,OverallResult);

		ResultStaffIn=IHV.Result_Same_Assoc(ScopeInAssociationID,StaffIn_Assoc)+" for staff and scope into Sink Area.";
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		
		ResultStaffIn=ResultStaffIn+" ResultStaffCycle="+ResultStaffCycle+". ResultLastStaff="+ResultLastStaff;
		//System.out.println(Scenario+":  "+ResultStaffIn);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffIn);	

		//SRM Validation when the Staffid is scanned
		Description="Verify Scope Record Management of "+ScanScope+" in "+Scanner+" with staff "+StaffID+" scanned";
		Expected =Description;
		String result_StaffinSA=ValidateSRM(StaffIn_Assoc,ScanScope,Scanner,StaffID,"","","");
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result_StaffinSA);

		//Verify MAM details
		Description="Verify Management and Asset Management of "+Scope+" and Staff "+StaffID+" into "+Scanner+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();		
		result_MAM=MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);
	}
	
	public void v_LeakTest() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(Scope.equals("Scanned")){
			if(LeakTestRes.equals("Pass")){
				//Verify the scan message received is correct
				Res = EM_V.VerifyScanMsg("Leak Test Passed", Unifia_Admin_Selenium.ScannerCount);
				//System.out.println(Res);
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			}else if(LeakTestRes.equals("Fail")){
				//Verify the scan message received is correct
				Res = EM_V.VerifyScanMsg("LeakTest Fail Follow Your Procedures", Unifia_Admin_Selenium.ScannerCount);
				//System.out.println(Res);
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			}
			//Verify in DB
			Expected="v_LeakTest: Leak Test "+LeakTestRes+" scanned into "+Scanner;
			ExpectedCycleEvent="Leak Test";
			LT_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			LeakTestIH=LT_IH[0];
			LeakTestAssocID=LT_IH[1];
			ActualCycleEvent=LT_IH[5];
			//System.out.println(LeakTestIH+" = Leak Test in Sink Area- ItemHistory_PK");
			ExpectedState="0";
			if(MultiRoom==false){
				OtherScopeStateID=0;
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
			}
			ExpectedCabinet="0";
			ResultLeakTestCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			ResultLeakTest_SameAssocID=IHV.Result_Same_Assoc(ScopeInAssociationID,LeakTestAssocID)+" for scope into Sink Area and Leaktest.";
			ResultSR="ResultLeakTestCycle= "+ResultLeakTestCycle+". ResultLeakTest_SameAssocID="+ResultLeakTest_SameAssocID;
			//System.out.println(Scenario+":  "+ResultSR);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
			Description="Verify Scope Record Management of "+ScanScope+" with leak test ";
			//SRM Validations
			Description="Verify Scope Record Management of "+ScanScope+" in "+Scanner+" with leak Test = "+LeakTestRes;
			Expected =Description;
			String result_LTRes=ValidateSRM(LeakTestAssocID,ScanScope,Scanner,StaffID,LeakTestRes,"","");	
			IHV.Exec_Log_Result(FileName, Description, Expected, result_LTRes);
			
		}else if(Scope.equals("NotScanned")){
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Please Scan Scope First", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			//Verify DB
			Expected="v_LeakTest: Leak Test Pass/Fail scanned by skipping Scope scan into "+Scanner;
			ExpectedCycleEventID=0;
			ExpectedScanItemType="Test Result";
			ExpectedAssociationID=0;
					
			LT_IH=IHV.GetItemHistoryDataNoAssociationID(Unifia_Admin_Selenium.connstring, Scanner);
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
				//Verify the scan message received is correct
				Res = EM_V.VerifyScanMsg(ScopeSerialNumber+" Manual Clean Started", Unifia_Admin_Selenium.ScannerCount);
				//System.out.println(Res);
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				//DB
				Expected="v_ManualCleanStart: Manual Clean Start scanned into "+Scanner;
				ExpectedCycleEvent="Manual Clean Start";
				MCS_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
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
				//Verify the scan message received is correct
				Res = EM_V.VerifyScanMsg("Leak Test Pass or Fail?", Unifia_Admin_Selenium.ScannerCount);
				//System.out.println(Res);
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				Expected="v_ManualCleanStart: Manual Clean Start scanned by skipping Leak Test into "+Scanner;
				ExpectedCycleEvent="Manual Clean Start";
				MCS_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
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
			String []MCStartTime=IHV.GetMCStartTime(Unifia_Admin_Selenium.connstring, ManCleanStartAssocID);
			String MCStartDateTime=MCStartTime[0];
			String result_MCStart="";
		if (!LeakTest.equals("Scanned")){
			Description="Verify Scope Record Management of "+ScanScope+" in "+Scanner+" with leak Test skipped and manual clean started";
			result_MCStart=ValidateSRM(ScopeInAssociationID,ScanScope,Scanner,StaffID,"",MCStartDateTime,"");
		}else{
			Description="Verify Scope Record Management of "+ScanScope+" in "+Scanner+" with leak Test done and manual clean started";
			result_MCStart=ValidateSRM(ScopeInAssociationID,ScanScope,Scanner,StaffID,LeakTestRes,MCStartDateTime,"");
			}
		Expected =Description;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MCStart);
		
	}else if(Scope.equals("NotScanned")){
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Please Scan Scope First", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			//Verify DB
			Expected="v_ManualCleanStart: Manual Clean Start scanned by skipping Scope scan into "+Scanner;
			ExpectedAssociationID=0;
			ExpectedCycleEventID=0;
			ExpectedScanItemType="Workflow Event";
			MCS_IH=IHV.GetItemHistoryDataNoAssociationID(Unifia_Admin_Selenium.connstring, Scanner);
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
				//Verify the scan message received is correct
				Res = EM_V.VerifyScanMsg(ScopeSerialNumber+" Completed "+MCTimePeriod, Unifia_Admin_Selenium.ScannerCount);
				//System.out.println(Res);
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				//DB
				Expected="v_ManualCleanEnd: Manual Clean End scanned into "+Scanner;
				ExpectedCycleEvent="Manual Clean End";
				MCE_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				ManCleanEndIH=MCE_IH[0];
				ManCleanEndAssocID=MCE_IH[1];
				ActualCycleEvent=MCE_IH[5];
				//System.out.println(ManCleanEndIH+" = Manual Clean End in Sink Area- ItemHistory_PK");
				//ScopeStatus and location
				Scope_Stat=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
				ActualScopeState=Scope_Stat[0];
				ScopeInLoc=Scope_Stat[1];
				ActualOtherScopeState=Scope_Stat[2];
				ActualSubloc=Scope_Stat[3];
				
				ExpectedState="2";
				if(MultiRoom==false){
					OtherScopeStateID=0;
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
				}
				ResultMCEndAssocID=IHV.Result_Same_Assoc(ScopeInAssociationID,ManCleanEndAssocID)+" for scope into Sink Area and Manual Clean End.";
				ResultMCEndCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
				ResultMCEndScopeState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
				ResultSR="ResultMCEndAssocID="+ResultMCEndAssocID+". ResultMCEndCycle= "+ResultMCEndCycle+". ResultMCEndScopeState="+ResultMCEndScopeState;
				//System.out.println(Scenario+":  "+ResultSR);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
				
				//SRM validations
				// To be done MCStart and MCEnd Date
				String []MCStartEndTime=IHV.GetMCStartEndTime(Unifia_Admin_Selenium.connstring, ManCleanEndAssocID);
				String MCStartTime=MCStartEndTime[0];
				String MCEndTime=MCStartEndTime[1];
				Description="Verify Scope Record Management of "+ScanScope+"in "+Scanner+" with manual clean start done and manual clean end scanned";
				String result_MCEnd=ValidateSRM(ScopeInAssociationID,ScanScope,Scanner,StaffID,LeakTestRes,MCStartTime,MCEndTime);
				Expected =Description;
				IHV.Exec_Log_Result(FileName, Description, Expected, result_MCEnd);
				
			}else if(MCStart.equals("NotScanned")){
				//Verify the scan message received is correct
				Res = EM_V.VerifyScanMsg("Manual Clean Start Scan Skipped", Unifia_Admin_Selenium.ScannerCount);
				//System.out.println(Res);
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				
				//DB
				Expected="v_ManualCleanEnd: Manual Clean End scanned by skipping Manual Clean Start into "+Scanner;
				ExpectedCycleEvent="Manual Clean End";
				MCE_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				ManCleanEndIH=MCE_IH[0];
				ManCleanEndAssocID=MCE_IH[1];
				ActualCycleEvent=MCE_IH[5];
				//System.out.println(ManCleanEndIH+" = Manual Clean End into Sink Area- ItemHistory_PK");
				//ScopeStatus and location
				Scope_Stat=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
				ActualScopeState=Scope_Stat[0];
				ScopeInLoc=Scope_Stat[1];
				ActualOtherScopeState=Scope_Stat[2];
				ActualSubloc=Scope_Stat[3];
				
				ExpectedState="2";
				if(MultiRoom==false){
					OtherScopeStateID=0;
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

				}
				
				ResultMCEndAssocID=IHV.Result_Same_Assoc(ScopeInAssociationID,ManCleanEndAssocID)+" for scope into Sink Area and Manual Clean End.";
				ResultMCEndCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
				ResultMCEndScopeState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
				ResultSR="ResultMCEndAssocID="+ResultMCEndAssocID+". ResultMCEndCycle= "+ResultMCEndCycle+". ResultMCEndScopeState="+ResultMCEndScopeState;
				//System.out.println(Scenario+":  "+ResultSR);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
				
				//SRM validations
				String MCEndTime=IHV.GetMCEndTime(Unifia_Admin_Selenium.connstring, ManCleanEndAssocID);
				Description="Verify Scope Record Management of "+ScanScope+" in "+Scanner+" with manual clean end scanned amd manul clean start skipped";
				Expected = Description;
				String result_MCEndSkippedMCStart=ValidateSRM(ScopeInAssociationID,ScanScope,Scanner,StaffID,LeakTestRes,"",MCEndTime);
				IHV.Exec_Log_Result(FileName, Description, Expected, result_MCEndSkippedMCStart);
			}
		}else if(Scope.equals("NotScanned")){
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Please Scan Scope First", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			//Verify DB
			Expected="v_ManualCleanEnd: Manual Clean End scanned by skipping Scope scan into "+Scanner;
			ExpectedAssociationID=0;
			ExpectedCycleEventID=0;
			ExpectedScanItemType="Workflow Event";
			MCE_IH=IHV.GetItemHistoryDataNoAssociationID(Unifia_Admin_Selenium.connstring, Scanner);
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
   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
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
   		
   		Res = EM_V.VerifyScanMsg(MinDiff+" Minutes Since Manual Cleaning", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		
		Integer MinDiffInt=Integer.parseInt(MinDiff);
		if(!Res){
			Res = EM_V.VerifyScanMsg(MinDiffInt-1 +" Minutes Since Manual Cleaning", Unifia_Admin_Selenium.ScannerCount);
		}
		if(!Res){
			UAS.resultFlag="#Failed!#";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result+"\r\n\t"+Description;
		}
		else{
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
			}	
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		//Manual clean Start
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean Start", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		//manual clean end
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Manual Clean End", "");
		//Verify in DB
		Expected="v_MoreThan1hr: "+ScanScope+" scanned into "+Scanner+" after more than 1 hour";
		ExpectedCycleEvent="Manual Clean End";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ScopeInIH=Scope_IH[0];
		ScopeInAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeInIH+" = Scope into Sink Area- ItemHistory_PK");
		//ScopeStatus and location
		Scope_Stat=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		ActualScopeState=Scope_Stat[0];
		ScopeInLoc=Scope_Stat[1];
		ActualOtherScopeState=Scope_Stat[2];
		ActualSubloc=Scope_Stat[3];
		
		ExpectedState="2";
		if(MultiRoom==false){
			OtherScopeStateID=0;
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

		}
		ExpectedCabinet="0";
		ExpectedCycleCount="1";
		
		MT1h=IHV.GetCycleID(Unifia_Admin_Selenium.connstring, Scanner);
		ActualCycleCount=MT1h[3];
		ResultScopeInCycle1h=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ResultScopeInLoc1h=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState1h=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultCycleCountMoreThan1hr=IHV.Result_CycleCount(ActualCycleCount,ExpectedCycleCount);
		ResultSR="ResultScopeInCycle1h= "+ResultScopeInCycle1h+". ResultScopeInLoc1h ="+ResultScopeInLoc1h+". ResultScopeInState1h="+ResultScopeInState1h+"ResultCycleCountMoreThan1hr="+ResultCycleCountMoreThan1hr;
		//System.out.println(Scenario+":  "+ResultSR);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
	}
	
	
	public void v_Soiled_Scp1_In(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Regression.MultiRoom.MultiRoomController_API.SoiledScope1Name;
		ScopeSerialNumber=Multi.SoiledScope1SerialNo;
		Scanner=Multi.SoiledScanner1;
		ScopeID=Multi.SoiledScope1ScopeID;
		ScopeInAssociationID=Multi.SoiledScope1Association;
	}

	public void v_Scp1_LT(){
		//System.out.println(getCurrentElement().getName());
		ScanScope=Multi.SoiledScope1Name;
		ScopeSerialNumber=Multi.SoiledScope1SerialNo;
		Scanner=Multi.SoiledScanner1;
		ScopeID=Multi.SoiledScope1ScopeID;
		ScopeInAssociationID=Multi.SoiledScope1Association;
	}
	public void v_Scp1_MCStart(){
		//System.out.println(getCurrentElement().getName());
		ScanScope=Multi.SoiledScope1Name;
		ScopeSerialNumber=Multi.SoiledScope1SerialNo;
		Scanner=Multi.SoiledScanner1;
		ScopeID=Multi.SoiledScope1ScopeID;
		ScopeInAssociationID=Multi.SoiledScope1Association;
	}
	public void v_Scp1_MCEnd(){
		//System.out.println(getCurrentElement().getName());
		ScanScope=Multi.SoiledScope1Name;
		ScopeSerialNumber=Multi.SoiledScope1SerialNo;
		Scanner=Multi.SoiledScanner1;
		ScopeID=Multi.SoiledScope1ScopeID;
		ScopeInAssociationID=Multi.SoiledScope1Association;
	}
	public void v_Scp1_InSoiled(){
		//System.out.println(getCurrentElement().getName());

	}
	public void v_Scp1_LT_Done(){
		//System.out.println(getCurrentElement().getName());

	}
	public void v_Scp1_MCStarted(){
		//System.out.println(getCurrentElement().getName());

	}
	public void v_Scp1_Await_Repro(){
		//System.out.println(getCurrentElement().getName());

	}

	public void v_Soiled_Scp2_In(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScanScope=Regression.MultiRoom.MultiRoomController_API.SoiledScope2Name;
		ScopeSerialNumber=Multi.SoiledScope2SerialNo;
		Scanner=Multi.SoiledScanner2;
		ScopeID=Multi.SoiledScope2ScopeID;
		ScopeInAssociationID=Multi.SoiledScope2Association;
	}

	public void v_Scp2_LT(){
		//System.out.println(getCurrentElement().getName());
		ScanScope=Multi.SoiledScope2Name;
		ScopeSerialNumber=Multi.SoiledScope2SerialNo;
		Scanner=Multi.SoiledScanner2;
		ScopeID=Multi.SoiledScope2ScopeID;
		ScopeInAssociationID=Multi.SoiledScope2Association;
	}
	public void v_Scp2_MCStart(){
		//System.out.println(getCurrentElement().getName());
		ScanScope=Multi.SoiledScope2Name;
		ScopeSerialNumber=Multi.SoiledScope2SerialNo;
		Scanner=Multi.SoiledScanner2;
		ScopeID=Multi.SoiledScope2ScopeID;
		ScopeInAssociationID=Multi.SoiledScope2Association;
	}
	public void v_Scp2_MCEnd(){
		//System.out.println(getCurrentElement().getName());
		ScanScope=Regression.MultiRoom.MultiRoomController_API.SoiledScope2Name;
		ScopeSerialNumber=Multi.SoiledScope2SerialNo;
		Scanner=Multi.SoiledScanner2;
		ScopeID=Multi.SoiledScope2ScopeID;
		ScopeInAssociationID=Multi.SoiledScope2Association;
	}
	public void v_Scp2_InSoiled(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_LT_Done(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_MCStarted(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_Await_Repro(){
		//System.out.println(getCurrentElement().getName());
	}

	public String ValidateSRM(String ScopeAssociationID,String scopeName, String sinkArea, String staffID, String LTRes, String MCStartDateTime, String MCEndDateTime) throws InterruptedException{
		//SRM Validation
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,scopeName,ScopeAssociationID);
		ScopeRefNo=ScopeInfo[0];
		ScopeSerialNo=ScopeInfo[1];
		ScopeModel=ScopeInfo[2];
		ScopeSinkInTime=ScopeInfo[3];
		
		Thread.sleep(1000); //Wait 1
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(ScopeRefNo);
		
		String Result_RefNum1=WF_V.Verify_RefNum(ScopeRefNo);
		temp=Result_RefNum1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum1,OverallResult);
		
		String Result_ScopeModel1=WF_V.Verify_ScopeModel(ScopeModel);
		temp=Result_ScopeModel1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel1,OverallResult);

		String Result_ScopeName1=WF_V.Verify_ScopeName(scopeName);
		temp=Result_ScopeName1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName1,OverallResult);

		String Result_ScopeSerialNo1=WF_V.Verify_ScopeSerialNum(ScopeSerialNo);
		temp=Result_ScopeSerialNo1.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo1,OverallResult);
		
		//String Result_ScopeSinkArea=WF_V.Verify_SoiledArea(Scanner);
		String Result_ScopeSinkArea=WF_V.Verify_SoiledArea(sinkArea);
		temp=Result_ScopeSinkArea.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSinkArea,OverallResult);	
		
		String Result_LTMCDate=WF_V.Verify_LTMCDate(ScopeSinkInTime);
		System.out.println("Result_LTMCDate=:"+Result_LTMCDate);
		temp=Result_LTMCDate.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LTMCDate,OverallResult);
		
		String Result_SinkStaff=WF_V.Verify_SoiledStaff(staffID);
		temp=Result_SinkStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_SinkStaff,OverallResult);
		
		String Result_LTStatus=WF_V.Verify_LT(LTRes);
		temp=Result_LTStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_LTStatus,OverallResult);
		
		String Result_MCStartDateTime=WF_V.Verify_MCStart(MCStartDateTime);
		temp=Result_MCStartDateTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_MCStartDateTime,OverallResult);
		
		String Result_MCEndDateTime=WF_V.Verify_MCEnd(MCEndDateTime);
		temp=Result_MCEndDateTime.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_MCEndDateTime,OverallResult);
		
		//IHV.Exec_Log_Result(FileName, Description, Expected, ResultSR);
		
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	
		String Result_SRM_Scope_Soiled="Result_RefNum1="+Result_RefNum1+". Result_ScopeModel1="+Result_ScopeModel1
				+". Result_ScopeName1="+Result_ScopeName1+". Result_ScopeSerialNo1="+Result_ScopeSerialNo1+". Result_ScopeSinkArea="+Result_ScopeSinkArea
				+". Result_LTMCDate="+Result_LTMCDate+". Result_SinkStaff="+Result_SinkStaff
				+". Result_LTStatus="+Result_LTStatus+". Result_MCStartDateTime="+Result_MCStartDateTime+". Result_MCEndDateTime="+Result_MCEndDateTime;
		return Result_SRM_Scope_Soiled;
	}
	public void v_Nav1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Nav2(){
		//System.out.println(getCurrentElement().getName());
	}
}
