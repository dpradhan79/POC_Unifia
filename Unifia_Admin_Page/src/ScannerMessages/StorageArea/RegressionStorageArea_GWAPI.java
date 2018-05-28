package ScannerMessages.StorageArea;

import org.graphwalker.core.machine.ExecutionContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import TestFrameWork.Emulator.*; 
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import Regression.MultiRoom.MultiRoomController_API;


public class RegressionStorageArea_GWAPI extends ExecutionContext{

	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
	public GeneralFunc gf;
	TestFrameWork.Emulator.GetIHValues IHV;
	public Regression.MultiRoom.MultiRoomController_API Multi;
	private static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	private static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	
	public Boolean MultiRoom=false;
	public String Cabinet="No";
	public String CabinetInvalid="";
	public String Staff="NotScanned";
	public String KEData;

	public String ScopeName="";
	public String ScopeSerialNumber="";
	public int ScopeID;
	public int AssociationID;
	public int CycleID;
	
	public String HangTime;
	public int ReproOut_AssociationID;
	
	public String StaffID="";
	public String StaffFirstName="";
	public String StaffLastName="";
	public Boolean Res;
	public String Location="",Facility="";
	public int LocationID_PK;
	public int NumCabinets;
	public int j=0;
	public Integer Scenario=0;
	public String Culture="NA";
	public Integer ScopeHangTime=0;
	public Integer KE=0;
	public String OtherKey="";
	public String CultureResult;

	public Connection conn= null;
	public ResultSet Scope_rs;  //result set used to get ScopeName from the Test DB
	public ResultSet CultureAssociation;
	public ResultSet Staff_rs;
	public ResultSet Scanner_ID_rs;
	public ResultSet Repro_rs;
	public String Scope_IH[];
	public String Staff_IH[];
	public String Culture_IH[];
	public String Cylce;
	public String ScopeStatus;
	public String ScopeInIH;
	public String ScopeInAssociationID;
	public String StaffInIH;
	public String StaffIn_Assoc;

	public String ScopeOutIH;
	public String ScopeOutAssociationID;
	public String StaffOutIH;
	public String StaffOut_Assoc;
	public String StaffOut_RI;
	
	public String CultureIH;
	public String CultureResult_Assoc;
	public String Culture_RI;
	
	public String stmt; //used for SQL queries
	public String stmt1;
	public static String actualResult="\t\t\t Regression_StorageArea_TestSummary \n"; 
	public String TestResFileName="Regression_StorageArea_TestSummary_";

	public String Description;
	public String ResultScopeInCycle;
	public String ResultScopeInLoc;
	public String ResultScopeInState;
	public String ResultStorage;
	public String ResultStaffIn;
	
	public String ResultScopeOutCycle;
	public String ResultScopeOutState;
	public String ResultScopeOutLoc;
	public String ResultStorageOut;
	public String ResultStaffOut;
	public String Result_RI;
	public String ResultCultureResult;
	public String ResultCulture_RI;

	public String Expected;
	public String ForFileName;
	public String FileName="";

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
	public int CultureAssociationID;
	
	public String NoStaffOut="",NoInStaff="";
	public String NoKeyEntry="";
	
	public String ScopeCultIH;
	public String ScopeCultAssociationID;
	public String StaffCultIH;
	public String StaffCult_Assoc;
	public String ResultScopeCultCycle;
	public String ResultScopeCultState;
	public String ResultScopeCultLoc;
	public String ResultCult;
	public String ResultStaffCult;
	public String ScopeCultLoc;

	public String ResultLastStaff;
	public int StaffPK=0;
	public int LastScanStaffID_FK;
	
	private String ExpectedReproCount;
	private String ExpectedExamCount;
	public static String result="";
	private String result_MAM="";
	private String cultureStatusResult="";
	
	public void e_Start(){
		//System.out.println(getCurrentElement().getName());
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date); 
		FileName="CycleMgmtStorageArea_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.ScannerCount=0;
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
	}
	public void e_ScopeCultureObtained() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(FileName.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date);
			FileName="CycleMgmt_StorageArea_Regression_";
			FileName=IHV.Start_Exec_Log(FileName);
			//Unifia_Admin_Selenium.ScannerCount=0;
			Unifia_Admin_Selenium.XMLFileName=FileName;
			Unifia_Admin_Selenium.TestCaseNumber=1;
		}
		Description ="Start of new Scenario "+Scenario;
		actualResult=actualResult+"\r\nScan a scope with a culture scanner then scan staff---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		//System.out.println("Selected ScopeNames "+ScopeName+" at location"+Location);
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Scope", "", ScopeName, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' is done";
		actualResult=actualResult+"\r\n --------:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_StaffCultureObtained() throws InterruptedException{
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
   		Staff=StaffFirstName+" "+StaffLastName+"("+StaffID+")";

		Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
		//System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount++;
		Description="Scan of Staff '" +StaffFirstName+" "+StaffLastName+"("+StaffID+")"+"' with Scanner "+Location +"is done";
		actualResult=actualResult+"\r\n ------:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_SkipStaffCulture() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Staff="Skipped";
	}
	
	public void e_OneCabinet() throws  InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(FileName.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date);
			FileName="CycleMgmt_StorageArea_Regression_";
			FileName=IHV.Start_Exec_Log(FileName);
			//Unifia_Admin_Selenium.ScannerCount=0;
			Unifia_Admin_Selenium.XMLFileName=FileName;
			Unifia_Admin_Selenium.TestCaseNumber=1;
		}
		Cabinet="Single";
		KEData="No";
		ExpectedCabinet="1";
		//Scope=String.valueOf(getMbt().getDataValue("Scope"));
		//System.out.println(getCurrentElement().getName());
		if(MultiRoom==false){
			try{ //Get a value that exists in Unifia to modify.
				//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
	
	    		stmt="select Scope.Name, Scope.SerialNumber, Status.ScopeID_FK, Status.OtherScopeStateID_FK from Scope join ScopeStatus Status on Scope.ScopeID_PK=Status.ScopeID_FK where Status.ScopeStateID_FK=5 and Status.LocationID_FK in (51,52,53,54,55,56,81) and status.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and LocationID_FK in (51,52,53,54,55,56,81))";
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					ScopeName = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
					ScopeSerialNumber = Scope_rs.getString(2);
					ScopeID = Scope_rs.getInt(3);
					OtherScopeStateID=Scope_rs.getInt(4);
				}		
				Scope_rs.close();
				//System.out.println("Scope= "+ScopeName);
				update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
				update.close();
				statement.close();
				
				Statement statement1 = conn.createStatement();
				stmt1="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=3 and "
						+ "Loc.StorageCabinetCount=1 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=3 and StorageCabinetCount=1 and IsActive=1)";
				//System.out.println(stmt1);
				Statement update1 = conn.createStatement();
	
				Scanner_ID_rs= statement1.executeQuery(stmt1);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Location = Scanner_ID_rs.getString(2);
					Facility = Scanner_ID_rs.getString(3);
				}		
				Scanner_ID_rs.close();
				//System.out.println("Location="+Location);
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
		}
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Scope", "", ScopeName, "");
		//System.out.println(Res);
		Description="e_OneCabinet: Scan of Scope '" +ScopeName+"' is done in "+ Location;
		Expected="e_OneCabinet: Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked into Storage Area="+Location;
		
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_KEOneCabinet() throws  InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Cabinet="Single";
		KEData="Yes";
		ExpectedCabinet="1";
		//Scope=String.valueOf(getMbt().getDataValue("Scope"));
		//System.out.println(getCurrentElement().getName());
		try{ //Get a value that exists in Unifia to modify.
			//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	

    		stmt="select Scope.Name, Scope.SerialNumber, Status.ScopeID_FK, Status.OtherScopeStateID_FK  from Scope "
    				+ "join ScopeStatus Status on Scope.ScopeID_PK=Status.ScopeID_FK "
    				+ "join ReprocessingStatus RS on Scope.ScopeID_PK=RS.ScopeID_FK where Status.ScopeStateID_FK=6 or Status.ScopeStateID_FK is null  and "
    				+ "Status.LocationID_FK in (51,52,53,54,55,56,81) and RS.ReprocessingStateID_FK=2 and "
    				+ "status.LastUpdatedDateTime=(Select Min(SS.LastUpdatedDateTime) from ScopeStatus SS "
    				+ "join ReprocessingStatus RS on SS.ScopeID_FK=RS.ScopeID_FK where ScopeStateID_FK=6 or Status.ScopeStateID_FK is null and "
    				+ "SS.LocationID_FK in (51,52,53,54,55,56,81) and RS.ReprocessingStateID_FK=2)";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ScopeName = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScopeSerialNumber = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				OtherScopeStateID=Scope_rs.getInt(4);
			}		
			Scope_rs.close();
			//System.out.println("Scope= "+ScopeName);
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();
			
			Statement statement1 = conn.createStatement();
			stmt1="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=3 and "
					+ "Loc.StorageCabinetCount=1 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=3 and StorageCabinetCount=1 and IsActive=1)";
			//System.out.println(stmt1);
			Statement update1 = conn.createStatement();

			Scanner_ID_rs= statement1.executeQuery(stmt1);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				Location = Scanner_ID_rs.getString(2);
				Facility = Scanner_ID_rs.getString(3);
			}		
			Scanner_ID_rs.close();
			//System.out.println("Location="+Location);
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
		
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Scope", "", ScopeName, "");
		//System.out.println(Res);
		Description="e_KEOneCabinet: Scan of Scope '" +ScopeName+"' is done in "+ Location;
		Expected="e_KEOneCabinet: Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked into Storage Area="+Location;

		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_MultiCabinet() throws  InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(FileName.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date);
			FileName="CycleMgmt_StorageArea_Regression_";
			FileName=IHV.Start_Exec_Log(FileName);
			//Unifia_Admin_Selenium.ScannerCount=0;
			Unifia_Admin_Selenium.XMLFileName=FileName;
			Unifia_Admin_Selenium.TestCaseNumber=1;
		}
		CabinetInvalid="No";
		Cabinet="Multi";
		KEData="No";
		//Scope=String.valueOf(getMbt().getDataValue("Scope")); e_CabinetNum
		//System.out.println(getCurrentElement().getName());
		if(MultiRoom==false){
			try{ //Get a value that exists in Unifia to modify.
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
	
	    		stmt="select Scope.Name, Scope.SerialNumber, Status.ScopeID_FK, Status.OtherScopeStateID_FK "
	    				+ "from Scope join ScopeStatus Status on Scope.ScopeID_PK=Status.ScopeID_FK "
	    				+ "where Status.ScopeStateID_FK=5 and Status.LocationID_FK in (51,52,53,54,55,56,81) "
	    				+ "and status.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus "
	    				+ "where ScopeStateID_FK=5 and LocationID_FK in (51,52,53,54,55,56,81))";
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					ScopeName = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
					ScopeSerialNumber = Scope_rs.getString(2);
					ScopeID = Scope_rs.getInt(3);
					OtherScopeStateID=Scope_rs.getInt(4);
				}		
				Scope_rs.close();
				//System.out.println("Scope= "+ScopeName);
				update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
				update.close();
				statement.close();
	
				Statement statement1 = conn.createStatement();
				stmt1="select Loc.LocationID_PK, Loc.Name, Loc.StorageCabinetCount,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where "
						+ "Loc.LocationTypeID_FK=3 and Loc.StorageCabinetCount!=1 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=3 and StorageCabinetCount!=1 and IsActive=1)";
				//System.out.println(stmt1);
				Statement update1 = conn.createStatement();
	
				Scanner_ID_rs= statement1.executeQuery(stmt1);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Location = Scanner_ID_rs.getString(2); 
					NumCabinets=Scanner_ID_rs.getInt(3);
					Facility = Scanner_ID_rs.getString(4);
				}		
				Scanner_ID_rs.close();
				//System.out.println("Location="+Location);
				//System.out.println("NumCabinets="+NumCabinets);
	
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
		}
		Unifia_Admin_Selenium.ScannerCount++;
		Thread.sleep(2000);
		Res = EM_A.ScanItem(Location, "Scope", "", ScopeName, "");
		//System.out.println(Res);
		Description="e_MultiCabinet: Scan of Scope '" +ScopeName+"' is done in "+ Location;
		Expected="e_MultiCabinet: Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked into Storage Area="+Location;

		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_KEMulitCabinet() throws  InterruptedException{
		//System.out.println(getCurrentElement().getName());
		CabinetInvalid="No";
		Cabinet="Multi";
		KEData="Yes";
		//Scope=String.valueOf(getMbt().getDataValue("Scope")); e_CabinetNum
		//System.out.println(getCurrentElement().getName());
		try{ //Get a value that exists in Unifia to modify.
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	

    		stmt="select Scope.Name, Scope.SerialNumber, Status.ScopeID_FK, Status.OtherScopeStateID_FK from "
    				+ "Scope join ScopeStatus Status on Scope.ScopeID_PK=Status.ScopeID_FK join "
    				+ "ReprocessingStatus RS on Scope.ScopeID_PK=RS.ScopeID_FK where Status.ScopeStateID_FK=6 or Status.ScopeStateID_FK is null "
    				+ "and Status.LocationID_FK in (51,52,53,54,55,56,81) and RS.ReprocessingStateID_FK=2 and "
    				+ "status.LastUpdatedDateTime=(Select Min(SS.LastUpdatedDateTime) from ScopeStatus SS join "
    				+ "ReprocessingStatus RS on SS.ScopeID_FK=RS.ScopeID_FK where Status.ScopeStateID_FK=6 or Status.ScopeStateID_FK is null and "
    				+ "SS.LocationID_FK in (51,52,53,54,55,56,81) and RS.ReprocessingStateID_FK=2)";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ScopeName = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScopeSerialNumber = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				OtherScopeStateID=Scope_rs.getInt(4);
			}		
			Scope_rs.close();
			//System.out.println("Scope= "+ScopeName);
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();

			Statement statement1 = conn.createStatement();
			stmt1="select Loc.LocationID_PK, Loc.Name,Loc.StorageCabinetCount,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where "
					+ "Loc.LocationTypeID_FK=3 and Loc.StorageCabinetCount!=1 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where "
					+ "LocationTypeID_FK=3 and StorageCabinetCount!=1 and IsActive=1)";
			//System.out.println(stmt1);
			Statement update1 = conn.createStatement();

			Scanner_ID_rs= statement1.executeQuery(stmt1);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				Location = Scanner_ID_rs.getString(2); 
				NumCabinets=Scanner_ID_rs.getInt(3);
				Facility = Scanner_ID_rs.getString(4);
			}		
			Scanner_ID_rs.close();
			//System.out.println("Location="+Location);
			//System.out.println("NumCabinets="+NumCabinets);

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
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Scope", "", ScopeName, "");
		//System.out.println(Res);
		Description="e_KEMulitCabinet: Scan of Scope '" +ScopeName+"' is done in "+ Location;
		Expected="e_KEMulitCabinet: Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked into Storage Area="+Location;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CabinetNum() throws  InterruptedException{
		//System.out.println(getCurrentElement().getName());
		CabinetInvalid="No";
		//Scope=String.valueOf(getMbt().getDataValue("Scope")); e_CabinetNum
		//System.out.println(getCurrentElement().getName());
		
		KE=NumCabinets-j;
		ExpectedCabinet=Integer.toString(KE);
		//System.out.println("KE="+KE);
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Key Entry", "", "", Integer.toString(KE));
		//System.out.println(Res);
		Description="e_CabinetNum: Key Entry cabinet Number"+KE+" is selected  for scope  "+ ScopeName;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		Expected="e_CabinetNum: Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked into Storage Area="+Location+" Cabinet="+ExpectedCabinet;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);			
		if(j<3){			
			j++;
		} else{
			j=0;
		}
	}
	
	public void e_CabinetInvalid() throws  InterruptedException{
		CabinetInvalid="Yes";
		//Scope=String.valueOf(getMbt().getDataValue("Scope")); e_CabinetNum
		//System.out.println(getCurrentElement().getName());
		
		KE=NumCabinets+1;
		//System.out.println("KE="+KE);
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Key Entry", "", "", Integer.toString(KE));
		//System.out.println(Res);
		Description="Key Entry cabinet Number"+KE+" is selected  for scope  "+ ScopeName;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);			
	}

	public void e_InStaff() throws  InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Staff="Scanned";
		NoInStaff="No";
		//System.out.println(getCurrentElement().getName());
   		try{ //Get a value that exists in Unifia to modify.
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
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
   		Staff=StaffFirstName+" "+StaffLastName+"("+StaffID+")";
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +Staff+"' is done in "+ Location;
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked into Storage Area="+Location+" and is associated to Staff="+Staff;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);		
	}
		
	public void e_ScopeNoCult() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(FileName.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date);
			FileName="CycleMgmt_StorageArea_Regression_";
			FileName=IHV.Start_Exec_Log(FileName);
			//Unifia_Admin_Selenium.ScannerCount=0;
			Unifia_Admin_Selenium.XMLFileName=FileName;
			Unifia_Admin_Selenium.TestCaseNumber=1;
		}
		NoKeyEntry="No";
		//System.out.println(getCurrentElement().getName());
		if(MultiRoom==false){
			try{ //Get a value that exists in Unifia to modify.
				//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
	
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name, Stat.AssociationID_FK, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK IS NULL and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (31,32,33,34) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK IS NULL and OtherScopeStateID_FK Is NULL and LocationID_FK in (31,32,33,34))";
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					ScopeName = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
					ScopeSerialNumber = Scope_rs.getString(2);
					ScopeID = Scope_rs.getInt(3);
					Location=Scope_rs.getString(4);
					AssociationID = Scope_rs.getInt(5);
					CycleID= Scope_rs.getInt(6);
					OtherScopeStateID=Scope_rs.getInt(7);
				}		
				Scope_rs.close();
				//System.out.println("Scope= "+ScopeName+"; Location= "+Location+"; AssociationID= "+AssociationID);
				
				update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
				update.close();
				statement.close();
				
				stmt1="Select DateDiff(hh, IH.ReceivedDateTime, GETUTCDATE())/24 AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK "
						+ " where ScopeCycle.ScopeID_FK="+ScopeID+" and ScopeCycle.CycleID="+CycleID+" and IH.CycleEventID_FK=18";
	
				//stmt1="Select DateDiff(dd, IH.ReceivedDateTime, GETUTCDATE()) AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK "
				//		+ " where ScopeCycle.ScopeID_FK="+ScopeID+" and ScopeCycle.CycleID="+CycleID+" and IH.CycleEventID_FK=18";
				//System.out.println(stmt1);
	
				Statement statement1 = conn.createStatement();
				Repro_rs = statement1.executeQuery(stmt1);
				while(Repro_rs.next()){
					HangTime = Repro_rs.getString(1); //the first variable in the set is the ID row in the database.
					ReproOut_AssociationID = Repro_rs.getInt(2);
				}
				Repro_rs.close();
				//System.out.println("HangTime="+HangTime);
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
		}
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Scope", "", ScopeName, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' is done in "+ Location;
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked out of Storage Area="+Location;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_ScopeCult() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(FileName.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			ForFileName = dateFormat.format(date);
			FileName="CycleMgmt_StorageArea_Regression_";
			FileName=IHV.Start_Exec_Log(FileName);
			//Unifia_Admin_Selenium.ScannerCount=0;
			Unifia_Admin_Selenium.XMLFileName=FileName;
			Unifia_Admin_Selenium.TestCaseNumber=1;
		}
		CultureResult="yes";
		//System.out.println(getCurrentElement().getName());
		if(MultiRoom==false){
			try{ //Get a value that exists in Unifia to modify.
				//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
	
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK IS NULL and Stat.OtherScopeStateID_FK=7 and Stat.LocationID_FK in (31,32,33,34) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK IS NULL and OtherScopeStateID_FK=7 and LocationID_FK in (31,32,33,34))";
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					ScopeName = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
					ScopeSerialNumber = Scope_rs.getString(2);
					ScopeID = Scope_rs.getInt(3);
					Location=Scope_rs.getString(4);
				}		
				Scope_rs.close();
				//System.out.println("Scope= "+ScopeName);
				//System.out.println("Location= "+Location);
				update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
				OtherScopeStateID=7;
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
		}
		OtherScopeStateID=7;
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Scope", "", ScopeName, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' is done in "+ Location;
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked out of Storage Area="+Location;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_CultResPass() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		CultureResult="Pass";

		KE=1;
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Key Entry", "", "", "1");
		//System.out.println(Res);
		Description="Key Entry  option '1' is selected  for scope  "+ ScopeName+" to specify culture Result is passed";
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked out of Storage Area="+Location+" Culture result of Pass entered";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void e_CultResFail() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		CultureResult="Fail";
		
		KE=2;
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Key Entry", "", "", "2");
		//System.out.println(Res);
		Description="Key Entry  option '2' is selected  for scope  "+ ScopeName+" to specify culture Result is failed";
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked out of Storage Area="+Location+" Culture result of Fail entered";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void e_CultResNoRes() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		KE=3;
		CultureResult="No Results";
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Key Entry", "", "", "3");
		//System.out.println(Res);
		Description="Key Entry  option '3' is selected  for scope  "+ ScopeName+" to specify No Results for culture test";
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked out of Storage Area="+Location+" Culture result of No Results entered";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_CultResOther() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		KE=4;
		CultureResult="Other";
		//System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Key Entry", "", "", "4");
		//System.out.println(Res);
		Description="Key Entry  option '4' is selected  for scope  "+ ScopeName+" which is not a correct option";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);		
	}
	
	public void e_BackForEnterRes(){
		//System.out.println(getCurrentElement().getName());
		OtherKey="Yes";
		//System.out.println(getCurrentElement().getName());
		Description="Looping back for entering the correct option 1 or 2 or 3";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);		
	}
	
	public void e_StaffNoCulture() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Culture="No";
		NoStaffOut="No";
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
   		Staff=StaffFirstName+" "+StaffLastName+"("+StaffID+")";
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +Staff+"' is done in "+ Location;
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked out of Storage Area="+Location+" and associated to staff="+Staff;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);		
	}
	
	public void e_StaffCulture() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Culture="Yes";
		NoStaffOut="No";
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
   		Staff=StaffFirstName+" "+StaffLastName+"("+StaffID+")";
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Staff", "Tech", Staff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +Staff+"' is done in "+ Location;
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked out of Storage Area="+Location+" and associated to staff="+Staff;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);				
	}
	
	public void e_Reset(){
		//System.out.println(getCurrentElement().getName());
		ScopeName="";
		ScopeSerialNumber="";
		ScopeID=0;
		ScopeInIH="";
		ScopeInAssociationID="";
		StaffInIH="";
		StaffIn_Assoc="";
		ScopeOutIH="";
		ScopeOutAssociationID="";
		StaffOutIH="";
		StaffOut_Assoc="";
		StaffOut_RI="";
		CultureIH="";
		CultureResult_Assoc="";
		Culture_RI="";
		CycleEvent="";
		ExpectedCabinet="";
		ActualScopeState="";
		ExpectedState="";
		OtherScopeStateID=0;
		ActualSubloc="";
		ActualOtherScopeState="";
		ActualCycleEvent="";
		ScopeInLoc="";
		ScopeOutLoc="";
		NoStaffOut="";
		NoInStaff="";
		NoKeyEntry="";
		StaffID="";
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

	public void v_StorageArea(){
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
	
	public void v_CabinetNum() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		if(KEData.equalsIgnoreCase("No")){
			if(CabinetInvalid.equalsIgnoreCase("No")){
				Res = EM_V.VerifyScanMsg(ScopeSerialNumber+" Enter Cabinet #", Unifia_Admin_Selenium.ScannerCount);
				CycleEvent="Cabinet Checkin";
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
				ScopeInIH=Scope_IH[0];
				ScopeInAssociationID=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				//System.out.println(ScopeInIH+" = Scope checkin to cabinet ItemHistory_PK");

			}else if(CabinetInvalid.equalsIgnoreCase("Yes")){
				Res = EM_V.VerifyScanMsg("Enter a Cabinet # Between 1-"+NumCabinets, Unifia_Admin_Selenium.ScannerCount);
	
			}
		} else if(KEData.equalsIgnoreCase("Yes")){
			
			if(CabinetInvalid.equalsIgnoreCase("No")){
				Res = EM_V.VerifyScanMsg("Prior Scope Reprocessing Cycle Failed", Unifia_Admin_Selenium.ScannerCount);
				CycleEvent="Cabinet Checkin";
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
				ScopeInIH=Scope_IH[0];
				ScopeInAssociationID=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				//System.out.println(ScopeInIH+" = Scope checkin to cabinet ItemHistory_PK");

			}else if(CabinetInvalid.equalsIgnoreCase("Yes")){
				Res = EM_V.VerifyScanMsg("Prior Scope Reprocessing Cycle Failed", Unifia_Admin_Selenium.ScannerCount);
			}
		}
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_InStaff() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		if (NoInStaff.equals("No")){
			Res = EM_V.VerifyScanMsg("Staff "+StaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			StaffInIH=Staff_IH[0];
			StaffIn_Assoc=Staff_IH[1];
			ResultStaffIn=IHV.Result_Same_Assoc(ScopeInAssociationID,StaffIn_Assoc);
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
						
			ResultStaffIn=ResultStaffIn+". ResultLastStaff="+ResultLastStaff;
			//System.out.println(Scenario+":  "+ResultStaffIn);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffIn);
			
		}else {
			Description="Staff Scan is not done after scope is checked into cabinet";
			//System.out.println(Description);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		if(StaffID.equalsIgnoreCase("")){
			StaffID="-";
		}
		
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+ScopeName+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		if(KEData.equalsIgnoreCase("Yes")){
			Thread.sleep(1000*20);
		}
		result_MAM=MAM_V.verifyScopeDetails(ScopeName, "LAST SCOPE LOCATION=="+Location+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		System.out.println(result_MAM);
		result=result+result_MAM;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);
		
		if(NoInStaff.equals("No")){
			ScopeName="";
			ScopeSerialNumber="";
			ScopeID=0;
			StaffID="";
		}

	}
	
	public void v_ScopeCheckedIn() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		
		if(KEData.equalsIgnoreCase("No")){
			if(Cabinet.equalsIgnoreCase("Single")){
				Res = EM_V.VerifyScanMsg("Scope "+ScopeSerialNumber+" Scanned", Unifia_Admin_Selenium.ScannerCount);
				CycleEvent="Cabinet Checkin";
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
				ScopeInIH=Scope_IH[0];
				ScopeInAssociationID=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				//System.out.println(ScopeInIH+" = Scope checkin to cabinet ItemHistory_PK");
				Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
						
			}else if(Cabinet.equalsIgnoreCase("Multi")){
				Res = EM_V.VerifyScanMsg("Cabinet "+Integer.toString(KE), Unifia_Admin_Selenium.ScannerCount);

			}
		} else if(KEData.equalsIgnoreCase("Yes")){
			if(Cabinet.equalsIgnoreCase("Single")){
				Res = EM_V.VerifyScanMsg("Prior Scope Reprocessing Cycle Failed", Unifia_Admin_Selenium.ScannerCount);
				CycleEvent="Cabinet Checkin";
				Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
				ScopeInIH=Scope_IH[0];
				ScopeInAssociationID=Scope_IH[1];
				ActualCycleEvent=Scope_IH[5];
				//System.out.println(ScopeInIH+" = Scope checkin to cabinet ItemHistory_PK");
				Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
						
			}else if(Cabinet.equalsIgnoreCase("Multi")){
				Res = EM_V.VerifyScanMsg("Cabinet "+Integer.toString(KE), Unifia_Admin_Selenium.ScannerCount);
			}
		}
		
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//System.out.println("ScopeName="+ScopeName+"; connstring="+Unifia_Admin_Selenium.connstring);

		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ExpectedState="0";
		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeInLoc=IHV.Result_Location(Location, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		
		StaffPK=0;
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		if(ResultLastStaff.contains("#Failed!#")){
			ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
		}
		
		ResultStorage="ResultScopeInCycle= "+ResultScopeInCycle+". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState;
		//System.out.println(Scenario+":  "+ResultStorage);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStorage);
		
		if(StaffID.equalsIgnoreCase("")){
			StaffID="-";
		}
		
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+ScopeName+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		if(KEData.equalsIgnoreCase("Yes")){
			Thread.sleep(1000*20);
		}
		result_MAM=MAM_V.verifyScopeDetails(ScopeName, "LAST SCOPE LOCATION=="+Location+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		if(result_MAM.contains("#Failed!# -lastscanstaffid was expected to be: -")){
			result_MAM=result_MAM+" bug 12603 open for lastscanstaffid Failure";
		}

		System.out.println(result_MAM);
		result=result+result_MAM;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);
	}

	public void v_ScopeOut_NoCulture() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if (NoKeyEntry.equals("No")){
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg(ScopeSerialNumber+" Hang Time "+ HangTime+" days", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);		
			
			CycleEvent="Cabinet Checkout";
			ExpectedCabinet="0";
			ExpectedState="5";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			ScopeOutIH=Scope_IH[0];
			ScopeOutAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeInIH+" = Scope checkin to cabinet ItemHistory_PK");
	
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);			
			ActualScopeState=Scope_IH[0];
			ScopeOutLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			if(ResultLastStaff.contains("#Failed!#")){
				ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
			}
			
			ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			ResultScopeOutLoc=IHV.Result_Location(Location, ScopeOutLoc, ExpectedCabinet,ActualSubloc);
			ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
			ResultStorageOut="v_ScopeOut_NoCulture: ResultScopeOutCycleEvent= "+ResultScopeOutCycle+". ResultLastStaff="+ResultLastStaff+". ResultScopeOutLoc= "+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
			//System.out.println(Scenario+":  "+ResultStorageOut);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStorageOut);
		}else if (NoKeyEntry.equals("Yes")){
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Results ? 1)Pass 2)Fail 3) No Results", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);		
			//DbCheck
			CycleEvent="Cabinet Checkout";
			ExpectedCabinet="0";
			ExpectedState="5";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			ScopeOutIH=Scope_IH[0];
			ScopeOutAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeInIH+" = Scope checked out of cabinet ItemHistory_PK");
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
			ActualScopeState=Scope_IH[0];
			ScopeOutLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
			StaffPK=0;
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
			if(ResultLastStaff.contains("#Failed!#")){
				ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
			}
			ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			ResultScopeOutLoc=IHV.Result_Location(Location, ScopeOutLoc, ExpectedCabinet,ActualSubloc);
			ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
			ResultStorageOut="v_ScopeOut_NoCulture: ResultScopeOutCycleEvent= "+ResultScopeOutCycle+". ResultLastStaff="+ResultLastStaff+". ResultScopeOutLoc= "+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
			//System.out.println(Scenario+":  "+ResultStorageOut);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStorageOut);

		}
		
		if(StaffID.equalsIgnoreCase("")){
			StaffID="-";
		}
		
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+ScopeName+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		result_MAM=MAM_V.verifyScopeDetails(ScopeName, "LAST SCOPE LOCATION=="+Location+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		if(result_MAM.contains("#Failed!# -lastscanstaffid was expected to be: -")){
			result_MAM=result_MAM+" bug 12603 open for lastscanstaffid Failure";
		}

		System.out.println(result_MAM);
		result=result+result_MAM;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);
	}

	public void v_ScopeOutCulture() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Results ? 1)Pass 2)Fail 3) No Results", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);		

		if(CultureResult.equalsIgnoreCase("Other")){
			//System.out.println("Culture Result - other Key - no DB check yet");
		}else {
			CycleEvent="Cabinet Checkout";
			ExpectedCabinet="0";
			ExpectedState="5";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			ScopeOutIH=Scope_IH[0];
			ScopeOutAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeInIH+" = Scope checked out of cabinet ItemHistory_PK");

			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
			
			ActualScopeState=Scope_IH[0];
			ScopeOutLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];
		}
		StaffPK=0;
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		if(ResultLastStaff.contains("#Failed!#")){
			ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
		}
		ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeOutLoc=IHV.Result_Location(Location, ScopeOutLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultStorageOut="v_ScopeOut_NoCulture: ResultScopeOutCycleEvent= "+ResultScopeOutCycle+". ResultLastStaff="+ResultLastStaff+". ResultScopeOutLoc= "+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
		//System.out.println(Scenario+":  "+ResultStorageOut);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStorageOut);
		
		if(StaffID.equalsIgnoreCase("")){
			StaffID="-";
		}
		
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+ScopeName+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		result_MAM=MAM_V.verifyScopeDetails(ScopeName, "LAST SCOPE LOCATION=="+Location+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		if(result_MAM.contains("#Failed!# -lastscanstaffid was expected to be: -")){
			result_MAM=result_MAM+" bug 12603 open for lastscanstaffid Failure";
		}

		System.out.println(result_MAM);
		result=result+result_MAM;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);

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
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg(ExpMsg, Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		
		if (OtherKey=="Yes" ){
			if (!Res){
				actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
			}
		else{
			CycleEvent="Culture Result";

			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
			
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			CultureIH=Scope_IH[0];
			CultureResult_Assoc=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			
			String ResultCultureResult=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			//System.out.println(Scenario+":  "+ResultCultureResult);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultCultureResult);

			ResultCultureResult=IHV.Result_Same_Assoc(Integer.toString(CultureAssociationID),CultureResult_Assoc);
			
			//System.out.println(Scenario+":  "+ResultCultureResult);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultCultureResult);
			
			Culture_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, ScopeOutIH);
			ResultCulture_RI= IHV.RelatedItem_Verf(CultureIH, Culture_RI);
			//System.out.println(Scenario+":  "+ResultCulture_RI);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultCulture_RI);
			}
		}
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScopeName,String.valueOf(CultureAssociationID));
		String ScopeRefNo=ScopeInfo[0];
		
		Thread.sleep(1000);
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.ClearFilter();
		System.out.println("ScopeSerialNumber"+ScopeSerialNumber);
		//Unifia_Admin_Selenium.driver.navigate().refresh();
		IP_A.ScopeFilter(ScopeSerialNumber);
		IP_A.ApplyFilter();
		IP_A.ClearFilter();
		IP_A.ScopeFilter(ScopeSerialNumber);
		IP_A.ApplyFilter();
		IP_A.Click_Details(ScopeRefNo);
		
		if(!CultureResult.equalsIgnoreCase("other")){
			Description="Verify Culture Result of '"+CultureResult+"' in SRM Screen for "+ScopeName+" into "+Location+". ";
			Expected=Description;
			//Get Scope info - with cultureObtained
			cultureStatusResult=WF_V.Verify_Culture(CultureResult);
			String temp[]=cultureStatusResult.split("-");
			System.out.println("cultureStatusResult="+cultureStatusResult);
			IHV.Exec_Log_Result(FileName, Description, Expected, cultureStatusResult);
		}else{
			Description="Verify Culture Result of 'Awaiting Results' in SRM Screen for "+ScopeName+" into "+Location+". ";
			Expected=Description;
			//Get Scope info - with cultureObtained
			cultureStatusResult=WF_V.Verify_Culture("Awaiting Results");
			String temp[]=cultureStatusResult.split("-");
			System.out.println("cultureStatusResult="+cultureStatusResult);
			IHV.Exec_Log_Result(FileName, Description, Expected, cultureStatusResult);
		}
		result=result+cultureStatusResult;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_OutStaff() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		if (NoStaffOut.equals("No")){
			Res = EM_V.VerifyScanMsg("Staff "+StaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Unifia_Admin_Selenium.Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			StaffOutIH=Staff_IH[0];
			StaffOut_Assoc=Staff_IH[1];
			ResultStaffOut=IHV.Result_Same_Assoc(ScopeOutAssociationID,StaffOut_Assoc);
			
			StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,StaffID);
			LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScopeName);
			ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);			
			ResultStaffOut=ResultStaffOut+". ResultLastStaff="+ResultLastStaff;
			//System.out.println(Scenario+":  "+ResultStaffOut);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffOut);
			
			StaffOut_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, ScopeOutIH);
			Result_RI= IHV.RelatedItem_Verf(StaffOutIH, StaffOut_RI);
			//System.out.println(Scenario+":  "+Result_RI);
			IHV.Exec_Log_Result(FileName, Description, Expected, Result_RI);
			
			if(Culture.equalsIgnoreCase("Yes")){
				OtherScopeStateID=0;
				Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
				
				ActualScopeState=Scope_IH[0];
				ScopeInLoc=Scope_IH[1];
				ActualOtherScopeState=Scope_IH[2];
				ActualSubloc=Scope_IH[3];
				ExpectedState="5";
				ResultScopeOutCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
				ResultScopeOutLoc=IHV.Result_Location(Location, ScopeOutLoc, ExpectedCabinet,ActualSubloc);
				ResultScopeOutState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
				ResultStorageOut="v_ScopeOut_NoCulture: ResultScopeOutCycleEvent= "+ResultScopeOutCycle+". ResultScopeOutLoc= "+ResultScopeOutLoc+". ResultScopeOutState="+ResultScopeOutState;
				//System.out.println(Scenario+":  "+ResultStorageOut);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultStorageOut);
			}
/*		}else if (NoStaffOut.equals("NoKeyEntry")){
			CycleEvent="Cabinet Checkout";
			ExpectedCabinet="0";
			ExpectedState="5";
			Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
			ScopeOutIH=Scope_IH[0];
			ScopeOutAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(ScopeInIH+" = Scope checked out of cabinet ItemHistory_PK");
			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
			ActualScopeState=Scope_IH[0];
			ScopeOutLoc=Scope_IH[1];
			ActualOtherScopeState=Scope_IH[2];
			ActualSubloc=Scope_IH[3];*/
		}else if (NoStaffOut.equals("Yes")){
			Description="Staff Scan is not done after scope is checked out of cabinet";
			//System.out.println(Description);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		if(StaffID.equalsIgnoreCase("")){
			StaffID="-";
		}
		
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		
		//Verify MAM details
		Thread.sleep(1000); //Wait 1 sec
		Description="Verify Management and Asset Management of "+ScopeName+" into "+Location+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		result_MAM=MAM_V.verifyScopeDetails(ScopeName, "LAST SCOPE LOCATION=="+Location+" ("+Facility+");LAST SCAN STAFF ID=="+StaffID
				+";EXAM COUNT=="+ExpectedExamCount+";REPROCESSING COUNT=="
				+ExpectedReproCount).toString();
		System.out.println(result_MAM);
		result=result+result_MAM;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);
	}

	public void e_NoInStaff() throws InterruptedException{
		NoInStaff="Yes";
		//System.out.println(getCurrentElement().getName());
		Description="Staff Scan is not done after scope is checked into cabinet";
		//System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_NoStaffNoCulture() throws InterruptedException{
		NoStaffOut="Yes";
		//System.out.println(getCurrentElement().getName());
		Description="Staff Scan is not done after scope  is checked out of cabinet";
		//System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoStaffCulture() throws InterruptedException{
		NoStaffOut="Yes";
		//System.out.println(getCurrentElement().getName());
		Description="Staff Scan is not done after scopes  culture result is entered";
		//System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoKEScopeCult() throws InterruptedException{
		NoKeyEntry="Yes";
		CultureResult="yes";
		//System.out.println(getCurrentElement().getName());
		try{ //Get a value that exists in Unifia to modify.
			//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	

			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK IS NULL and Stat.OtherScopeStateID_FK=7 and Stat.LocationID_FK in (31,32,33,34) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK IS NULL and OtherScopeStateID_FK=7 and LocationID_FK in (31,32,33,34))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ScopeName = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScopeSerialNumber = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				Location=Scope_rs.getString(4);
			}		
			Scope_rs.close();
			//System.out.println("Scope= "+ScopeName);
			//System.out.println("Location= "+Location);
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
		OtherScopeStateID=7;
		Unifia_Admin_Selenium.ScannerCount++;
		Res = EM_A.ScanItem(Location, "Scope", "", ScopeName, "");
		OtherScopeStateID=7;
		//System.out.println(Res);
		Description="Scan of Scope '" +ScopeName+"' is done in "+ Location;
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Checked out of Storage Area="+Location;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void v_Scope1IntoStorageSingle(){
		MultiRoom=true;
		//System.out.println(getCurrentElement().getName());
		ScopeName=Multi.StorageInScope1Name;
		ScopeSerialNumber=Multi.StorageInScope1SerialNo;
		Location=Multi.StorageInScanner1;
		OtherScopeStateID=Multi.StorageInScope1OtherState;
	}
	public void v_Scope1InStorage(){
		//System.out.println(getCurrentElement().getName());
	}
/*	public void v_Scope1CheckoutNoCulture(){ 
		MultiRoom=true;
		//System.out.println(getCurrentElement().getName());
		ScopeName=Multi.StorageOutScope1Name;
		ScopeSerialNumber=Multi.StorageOutScope1SerialNo;
		HangTime=Multi.StorageOut1HangTime;
		OtherScopeStateID=Multi.StorageOutScope1OtherStateID;
		Location=Multi.StorageOutScanner1;
	}*/
	public void v_Scope1CheckoutCulture(){ 
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScopeName=Multi.StorageOutScope1Name;
		ScopeSerialNumber=Multi.StorageOutScope1SerialNo;
		HangTime=Multi.StorageOut1HangTime;
		OtherScopeStateID=Multi.StorageOutScope1OtherStateID;
		CultureAssociationID=Multi.StorageOutCultureAssociationID;
		Location=Multi.StorageOutScanner1;
	}
	public void v_Scope1CheckedOut(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scope2IntoStorageMultiple(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScopeName=Multi.StorageInScope2Name;
		ScopeSerialNumber=Multi.StorageInScope2SerialNo;
		Location=Multi.StorageInScanner2;
		NumCabinets=Multi.Storage2NumCabinets;
		OtherScopeStateID=Multi.StorageInScope2OtherState;
	}
	public void v_Scope2InStorage(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scope2CheckoutNoCulture(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScopeName=Multi.StorageOutScope2Name;
		ScopeSerialNumber=Multi.StorageOutScope2SerialNo;
		HangTime=Multi.StorageOut2HangTime;
		OtherScopeStateID=Multi.StorageOutScope2OtherStateID;
		Location=Multi.StorageOutScanner2;
	}
	public void v_Scope2CheckedOut(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scope1ObtainCulture(){
		//System.out.println(getCurrentElement().getName());
		MultiRoom=true;
		ScopeName=Multi.CultureScope1Name;
		ScopeSerialNumber=Multi.CultureScope1SerialNo;
		Location=Multi.CultureScanner1;
	}
	public void v_Scope1CultureObtained(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_CultureObtained() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Res = EM_V.VerifyScanMsg("Culture Obtained", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Culture Obtained="+Location;

		CycleEvent="Culture Obtained";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Location);
		ScopeCultIH=Scope_IH[0];
		ScopeCultAssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		//System.out.println(ScopeCultIH+" = Scope checkin Culture Obtained ItemHistory_PK");
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScopeName);
		
		ActualScopeState=Scope_IH[0];
		ScopeCultLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ExpectedState="5";
		ExpectedCabinet="0";
		OtherScopeStateID=7;

		ResultScopeCultCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeCultLoc=IHV.Result_Location(Location, ScopeCultLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeCultState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultCult="ResultScopeCultCycle= "+ResultScopeCultCycle+". ResultScopeCultLoc= "+ResultScopeCultLoc+". ResultScopeCultState="+ResultScopeCultState;
		//System.out.println(Scenario+":  "+ResultCult);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultCult);

	}
	public void v_CultureStaff() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		if(!Staff.equalsIgnoreCase("Skipped")){
			Res = EM_V.VerifyScanMsg("Staff "+StaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			//System.out.println(Res);
			actualResult=actualResult+"\r\n ------:\r\n\t"+UAS.Result;
			
			Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Location);
			StaffCultIH=Staff_IH[0];
			StaffCult_Assoc=Staff_IH[1];
			ResultStaffCult=IHV.Result_Same_Assoc(ScopeCultAssociationID,StaffCult_Assoc);
			
			Expected="Scope="+ScopeName+" Serial #="+ScopeSerialNumber+" Culture Obtained; Scanner="+Location+" and is associated to Staff="+StaffID;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			//System.out.println(Scenario+":  "+ResultStaffCult);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffCult);
		}
	}
}
