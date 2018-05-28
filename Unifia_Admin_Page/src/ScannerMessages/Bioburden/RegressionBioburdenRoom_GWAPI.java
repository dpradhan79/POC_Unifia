package ScannerMessages.Bioburden;

import java.awt.AWTException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.graphwalker.core.machine.ExecutionContext;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class RegressionBioburdenRoom_GWAPI extends ExecutionContext {
	
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
	public GeneralFunc gf;
	public 	TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	
	public String BioStatus="";
	//public String Color[];
	public String ScanResult="";
	public Boolean Res;
	public String KeyEntry="";
	public Integer Scenario=0;
	public String ScanScopeSerNum;
	public static String actualResult="\t\t\t ScanMessages_BioBurdenRoom_TestSummary \n"; 
	public String Description;
	public String ForFileName;
	public String TestResFileName="ScanMessages_BioBurdenRoom_TestSummary_";	
	public String ScanScope="",Scanner="",Facility="";
	//public String WaitTimeinMins="";
	public String ScanStaff="";
	public String StaffFirstName ="", StaffLastName="", ScanStaffID="";
	//public String ScopeStatus="";
	public String Reason="";
	public Connection conn= null;
	public String stmt,stmt2;
	public ResultSet WaitingTime_RS,Staff_rs,Scanner_ID_rs,Scope_ID_rs;
	public Integer LocationID_PK;
	public Integer ScopeID;
	
	public String FileName;
	public String Expected;
	public String Scope_IH[];
	public String Staff_IH[];

	
	public String ItemHistoryID;
	public String AssociationID;
	public String StaffInIH;
	public String StaffIn_Assoc;
	public String ReasonIH;
	public String Reason_Assoc;
	
	public String MRC_IH;
	public String MRCAssociationID;

	public String StaffMRCIH;
	public String StaffMRC_Assoc;
	
	public String StaffOut_Assoc;
	public String Status="";
	public String BarcodeValue;
	public String KeyentryValue;
	
	public String ResultScopeInCycle;
	public String ResultTestStatusCycle;
	public String ResultScanResultCycle;
	public String ResultKeyEntryCycle;
	public String ResultScopeInLoc;
	public String ResultScopeInState;
	public String Result;
	public String ResultAssoc;
	public String ResultBarcode;
	public String ResultKeyEntry;
	public String ResultStaffOut;
	
	public String CycleEvent;
	public String ExpectedCabinet;
	public String ActualScopeState;
	public String ExpectedState;
	public String OtherScopeStateID;
	public String ActualSubloc;
	public String ActualOtherScopeState;
	public String ActualCycleEvent;
	public String ScopeInLoc;
	public String ScopeOutLoc;
	
	public String ResultLastStaff;
	public int StaffPK=0;
	public int LastScanStaffID_FK;
	public String ResultStaffCycle="";

	private String ScopeInfo[]=null;
	private String ScopeRefNo="";
	private String ScopeSerialNo="";
	private String ScopeModel="";
	private String Result_RefNum="";
	private String Result_ScopeModel="";
	private String Result_ScopeName="";
	private String Result_ScopeSerialNo="";
	private String [] temp= new String[2];
	private String OverallResult="Pass";
	private String actualExamCount="";
	private String actualReproCount="";
	public String Changes="No";

	public void e_Start(){
		//empty edge for graphml navigation
		//empty edge for graphml navigation
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date); 
		Unifia_Admin_Selenium.ScannerCount=0;
		FileName="CycleMgmtBioburdenRoom_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
	}
	
	public void e_Scope() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//ScopeStatus="Normal";
		//Scope="Scanned";
		try{ //Get a value that exists in Unifia to modify.
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			//stmt="select ScopeName,SerialNumber from Scope where TestKeyword='ScannerMessage' and CultureResultGot ='NA' and CultureAwaiting='NA' and UpdateDate=(Select Min(UpdateDate) from Scope Where TestKeyword='ScannerMessage' and CultureResultGot ='NA' and CultureAwaiting='NA')"; //put sql statement here to find ID
			stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; //put sql statement here to find ScopeName
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_ID_rs = statement.executeQuery(stmt);
			while(Scope_ID_rs.next()){
				ScanScope = Scope_ID_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanScopeSerNum = Scope_ID_rs.getString(2);
				ScopeID = Scope_ID_rs.getInt(3);
			}		
			Scope_ID_rs.close();
			System.out.println("Scope= "+ScanScope);
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
		
	   		try{ //Get a value that exists in Unifia to modify.
				//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				//stmt="select actual_ScanName from Scanner where TestKeyword='ScannerMessage' and actual_LocID ='5' and UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='ScannerMessage' and actual_LocID ='5' )"; //put sql statement here to find ID
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=8 and Loc.IsActive=1 and Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=8 and IsActive=1)"; //put sql statement here to find ID
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
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Scope", "", ScanScope, "");
		System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	
	public void e_TestStatusFail() throws InterruptedException{
		// Scan Bio Fail
		System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Scanner, "Bioburden Testing", "", "Fail", "");
		System.out.println(Res);
		Description="Scan of Bioburden fail is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		BioStatus="Bioburden Test Fail";
		Status="Fail";
				
	}
	
	public void e_TestStatusPass() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Scanner, "Bioburden Testing", "", "Pass", "");
		System.out.println(Res);
		Description="Scan of Bioburden Pass is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		BioStatus="Bioburden Test Pass";
		Status="Pass";
		}
	
	public void e_KENoTSNoSR() throws InterruptedException{
		//Scan Key Entry without Bio Pass/Fail or Red/Blue
		System.out.println(getCurrentElement().getName());
		String[] RadomKE = {"1","2","3","4","5","6","7","8","9","0"};
		KeyEntry = (RadomKE[new Random().nextInt(RadomKE.length)]);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Scanner, "Key Entry", "", "", KeyEntry);
		System.out.println(Res);
		Description="Scan of  KeyEntry " +KeyEntry + " in "+ Scanner ;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_ScanResult() throws InterruptedException{
		//scan BioPass/Fail and Then scan Red/Blue
		System.out.println(getCurrentElement().getName());
		String[] Color = {"Red","Blue"};
		ScanResult = (Color[new Random().nextInt(Color.length)]);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Scanner, "Bioburden Testing", "", ScanResult, "");
		System.out.println(Res);
		Description="Scan of  " + ScanResult + "is done in "+ Scanner ;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	

	 
	public void e_KEYesTSNoSR() throws InterruptedException{
		// go from Bio Pass/ Fail to KeyEntry
		System.out.println(getCurrentElement().getName());
		String[] RadomKE = {"1","2","3","4","5","6","7","8","9","0"};
		KeyEntry = (RadomKE[new Random().nextInt(RadomKE.length)]);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		EM_A.ScanItem(Scanner, "Key Entry", "", "", KeyEntry);
		System.out.println(Res);
		Description="Scan of  KeyEntry " +KeyEntry + " in "+ Scanner ;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		}
	
	public void e_StaffNoTRNoKE() throws InterruptedException{
		//go directly to Staff after Test Status without Test Result or Key Entry
		// Example Bio Pass/ Fail and then staff
		System.out.println(getCurrentElement().getName());
		try{ //Get a value that exists in Unifia to modify.
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			//stmt="select StaffID,FirstName,LastName from Staff where TestKeyword='ScannerMessage' and StaffType='Tech' and UpdateDate=(Select Min(UpdateDate) from Staff Where TestKeyword='ScannerMessage' and StaffType='Tech')"; //put sql statement here to find Staff details
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanStaffID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			Staff_rs.close();
			System.out.println("StaffID = "+ScanStaffID);
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
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" after Reprocessing reason is provided";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffNoKEYesTR() throws InterruptedException{
		// go from staff after Pass/Fail is scanned and No KeyEntry
		System.out.println(getCurrentElement().getName());
		try{ //Get a value that exists in Unifia to modify.
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			//stmt="select StaffID,FirstName,LastName from Staff where TestKeyword='ScannerMessage' and StaffType='Tech' and UpdateDate=(Select Min(UpdateDate) from Staff Where TestKeyword='ScannerMessage' and StaffType='Tech')"; //put sql statement here to find Staff details
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanStaffID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			Staff_rs.close();
			System.out.println("StaffID = "+ScanStaffID);
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
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" after Reprocessing reason is provided";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff() throws InterruptedException {
		
			System.out.println(getCurrentElement().getName());
			try{ //Get a value that exists in Unifia to modify.
				//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				//stmt="select StaffID,FirstName,LastName from Staff where TestKeyword='ScannerMessage' and StaffType='Tech' and UpdateDate=(Select Min(UpdateDate) from Staff Where TestKeyword='ScannerMessage' and StaffType='Tech')"; //put sql statement here to find Staff details
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Staff_rs = statement.executeQuery(stmt);
				while(Staff_rs.next()){
					ScanStaffID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
					StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
					StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
				}		
				Staff_rs.close();
				System.out.println("StaffID = "+ScanStaffID);
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
			Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
			Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
			System.out.println(Res);
			Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" after Reprocessing reason is provided";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	
	public void e_Reset() throws InterruptedException{
		System.out.println(getCurrentElement().getName());	
		//Initialize all Variables to default state
		System.out.println(getCurrentElement().getName());	
		//Variables to default state
		Scanner="";
		ScanScope="";
		ScanScopeSerNum="";
		ScanStaff="";
		ScanStaffID="";
		StaffFirstName="";
		StaffLastName="";
		ScanResult="";
		BioStatus="";
		Status="";
		KeyEntry="";
	}
	
	public void e_NoStaff(){
		Scanner="";
		ScanScope="";
		ScanScopeSerNum="";
		ScanStaff="";
		ScanStaffID="";
		StaffFirstName="";
		StaffLastName="";
		ScanResult="";
		BioStatus="";
		Status="";
		KeyEntry="";
		
		System.out.println(getCurrentElement().getName());
		Description="Staff Scan skipped after Entring a Key Entry";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoStaffNoKEYesTR(){
		Scanner="";
		ScanScope="";
		ScanScopeSerNum="";
		ScanStaff="";
		ScanStaffID="";
		StaffFirstName="";
		StaffLastName="";
		ScanResult="";
		BioStatus="";
		Status="";
		KeyEntry="";
		
		System.out.println(getCurrentElement().getName());
		Description="Staff Scan skipped after Scanning a ScanResult";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoStaffNoTRNoKE(){
		Scanner="";
		ScanScope="";
		ScanScopeSerNum="";
		ScanStaff="";
		ScanStaffID="";
		StaffFirstName="";
		StaffLastName="";
		ScanResult="";
		BioStatus="";
		Status="";
		KeyEntry="";
		
		System.out.println(getCurrentElement().getName());
		Description="Staff Scan skipped after Scanning a TestStatus";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeOnly(){
		Scanner="";
		ScanScope="";
		ScanScopeSerNum="";
		ScanStaff="";
		ScanStaffID="";
		StaffFirstName="";
		StaffLastName="";
		ScanResult="";
		BioStatus="";
		Status="";
		KeyEntry="";
		
		System.out.println(getCurrentElement().getName());
		Description="Scenario Completed After Scanning Scope";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScopeStaff_NoTSNoSRNoKE() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		try{ //Get a value that exists in Unifia to modify.
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			//stmt="select StaffID,FirstName,LastName from Staff where TestKeyword='ScannerMessage' and StaffType='Tech' and UpdateDate=(Select Min(UpdateDate) from Staff Where TestKeyword='ScannerMessage' and StaffType='Tech')"; //put sql statement here to find Staff details
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanStaffID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			Staff_rs.close();
			System.out.println("StaffID = "+ScanStaffID);
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
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" Scanning the Scope";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_BioRoom(){
		System.out.println(getCurrentElement().getName());
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
	
	public void v_Scope() throws InterruptedException, AWTException{
		// Scope Scanned
		System.out.println(getCurrentElement().getName());
		Res = EM_V.VerifyScanMsg("Scope "+ScanScopeSerNum + " Scanned", Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		Expected="Scope Scanned into BioBurden Room";
		CycleEvent="Bioburden Testing";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ItemHistoryID=Scope_IH[0];
		AssociationID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(CycleEvent+" Started for "+ScanScope);
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		actualReproCount=Scope_IH[5];
		actualExamCount=Scope_IH[6];
		
		ExpectedState="2";
		OtherScopeStateID="0";
		ExpectedCabinet="0";
		
		StaffPK=0;
		
		ScopeInfo=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,AssociationID);
		ScopeRefNo=ScopeInfo[0];
		ScopeSerialNo=ScopeInfo[1];
		ScopeModel=ScopeInfo[2];

		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultScopeInLoc=IHV.Result_Location(Scanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, OtherScopeStateID,ActualOtherScopeState);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);

		
		Result="ResultScopeInCycle= "+ResultScopeInCycle+". ResultLastStaff="+ResultLastStaff+". ResultScopeInLoc="+ResultScopeInLoc+". ResultScopeInState="+ResultScopeInState;
		System.out.println(Scenario+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(ScopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";

		Result_RefNum=WF_V.Verify_RefNum(ScopeRefNo);
		System.out.println("Result_RefNum="+Result_RefNum);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		Result_ScopeModel=WF_V.Verify_ScopeModel(ScopeModel);
		System.out.println("Result_ScopeModel="+Result_ScopeModel);
		temp=Result_ScopeModel.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeModel,OverallResult);

		Result_ScopeName=WF_V.Verify_ScopeName(ScanScope);
		System.out.println("Result_ScopeName="+Result_ScopeName);
		temp=Result_ScopeName.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeName,OverallResult);

		Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(ScopeSerialNo);
		System.out.println("Result_ScopeSerialNo="+Result_ScopeSerialNo);
		temp=Result_ScopeSerialNo.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_ScopeSerialNo,OverallResult);

		System.out.println("Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo);
		
		
		String Result_BioStatus=WF_V.Verify_BioResult("");
		System.out.println("Result_BioStatus=:"+Result_BioStatus);
		temp=Result_BioStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioStatus,OverallResult);

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
		String result_SRM_Scope_Bioburden="Result_RefNum=:"+Result_RefNum+". Result_ScopeModel="+Result_ScopeModel
				+". Result_ScopeName="+Result_ScopeName+". Result_ScopeSerialNo="+Result_ScopeSerialNo+". Result_BioStatus="+Result_BioStatus
				+". Result_BioScanValue="+Result_BioScanValue+". Result_BioKeyValue"+Result_BioKeyValue+". Result_BioStaff="+Result_BioStaff;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_SRM_Scope_Bioburden);
		
		
		//Verify MAM details
		Description="Verify Management and Asset Management of "+ScanScope+" into "+Scanner+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		String result_MAM=MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID==-"
				+";EXAM COUNT=="+actualExamCount+";REPROCESSING COUNT=="+actualReproCount).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);
		
	}
	
	public void v_TestStatus() throws InterruptedException, AWTException{
		// Bio Pass Fail
		System.out.println(getCurrentElement().getName());
		Res = EM_V.VerifyScanMsg(BioStatus, Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		Expected="Bioburden test Status "+Status+" Scanned";
		CycleEvent="Bioburden Pass/Fail";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ItemHistoryID=Scope_IH[0];
		String BioStatusAssocID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(CycleEvent+" Started for "+ScanScope);
		
		if(Status.equalsIgnoreCase("Pass")){
			ExpectedState="2";				
		}else if(Status.equalsIgnoreCase("Fail")){
			//if Bioburden fail is scanned, the scope is set to awaiting manual cleaning instead of awaiting reprocessing
			ExpectedState="3";
		}
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		
		ResultTestStatusCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultAssoc=IHV.Result_Same_Assoc(AssociationID, BioStatusAssocID);
		String ResultScopeState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, OtherScopeStateID,ActualOtherScopeState);

		
		BarcodeValue=IHV.GetBarcodeValue(Unifia_Admin_Selenium.connstring, ItemHistoryID);
		ResultBarcode=IHV.Result_Same_Barcode(BarcodeValue, Status);
		Result="ResultTestStatusCycle= "+ResultTestStatusCycle+". ResultAssoc="+ResultAssoc+". ResultScopeState="+ResultScopeState+". ResultBarcode="+ResultBarcode+". Barcode Value="+BarcodeValue;
		System.out.println(Scenario+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(ScopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";
		
		Result_RefNum=WF_V.Verify_RefNum(ScopeRefNo);
		System.out.println("Result_RefNum="+Result_RefNum);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_BioStatus=WF_V.Verify_BioResult(Status);
		System.out.println("Result_BioStatus=:"+Result_BioStatus);
		temp=Result_BioStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioStatus,OverallResult);
		WF_A.Cancel(Changes);
		
		Expected=Description;
		String result_SRM_Scope_Bioburden="Result_RefNum=:"+Result_RefNum+". Result_BioStatus="+Result_BioStatus;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_SRM_Scope_Bioburden);
	}
	
	public void v_ScanResult() throws InterruptedException, AWTException{
		// Scan Red Blue
		System.out.println(getCurrentElement().getName());
		Res = EM_V.VerifyScanMsg(ScanResult + " scanned", Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		Expected="Bioburden Scan Result "+ScanResult+" Scanned";
		CycleEvent="Bioburden Result";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ItemHistoryID=Scope_IH[0];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(CycleEvent+" Started for "+ScanScope);
		
		ResultScanResultCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultAssoc=IHV.Result_Same_Assoc(AssociationID, Scope_IH[1]);
		BarcodeValue=IHV.GetBarcodeValue(Unifia_Admin_Selenium.connstring, ItemHistoryID);
		ResultBarcode=IHV.Result_Same_Barcode(BarcodeValue, ScanResult);
		
		Result="ResultScanResultCycle= "+ResultScanResultCycle+". ResultAssoc="+ResultAssoc+" Result Barcode:"+ResultBarcode+". Barcode Value="+BarcodeValue;
		System.out.println(Scenario+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(ScopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";
		
		Result_RefNum=WF_V.Verify_RefNum(ScopeRefNo);
		System.out.println("Result_RefNum="+Result_RefNum);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_BioStatus=WF_V.Verify_BioResult(Status);
		System.out.println("Result_BioStatus=:"+Result_BioStatus);
		temp=Result_BioStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioStatus,OverallResult);
		
		String Result_BioScanValue=WF_V.Verify_BioScanValue(ScanResult);
		System.out.println("Result_BioScanValue=:"+Result_BioScanValue);
		temp=Result_BioScanValue.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioScanValue,OverallResult);
		WF_A.Cancel(Changes);
		
		Expected=Description;
		String result_SRM_Scope_Bioburden="Result_RefNum=:"+Result_RefNum+". Result_BioStatus="+Result_BioStatus
				+". Result_BioScanValue="+Result_BioScanValue;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_SRM_Scope_Bioburden);
	}
	
	public void v_KeyEntry() throws InterruptedException, AWTException{
		// Key Entry
		System.out.println(getCurrentElement().getName());
		Res = EM_V.VerifyScanMsg(KeyEntry+" Entered", Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		Expected="Key Entry "+KeyEntry+" Entered";
		CycleEvent="Bioburden Result";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		ItemHistoryID=Scope_IH[0];
		ActualCycleEvent=Scope_IH[5];
		System.out.println(CycleEvent+" Started for "+ScanScope);
		KeyentryValue=IHV.GetKeyEntryValue(Unifia_Admin_Selenium.connstring, ItemHistoryID);
		
		ResultKeyEntryCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		ResultAssoc=IHV.Result_Same_Assoc(AssociationID, Scope_IH[1]);
		ResultKeyEntry=IHV.Result_Same_KeyEntry(KeyentryValue, KeyEntry);
		Result="ResultKeyEntryCycle= "+ResultKeyEntryCycle+". ResultAssoc="+ResultAssoc+" Result KeyEntry:"+ResultKeyEntry+". KeyEntry Value="+KeyentryValue;
		System.out.println(Scenario+":  "+Result);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(ScopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";
		
		Result_RefNum=WF_V.Verify_RefNum(ScopeRefNo);
		System.out.println("Result_RefNum="+Result_RefNum);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_BioStatus=WF_V.Verify_BioResult(Status);
		System.out.println("Result_BioStatus=:"+Result_BioStatus);
		temp=Result_BioStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioStatus,OverallResult);
		
		String Result_BioScanValue=WF_V.Verify_BioScanValue(ScanResult);
		System.out.println("Result_BioScanValue=:"+Result_BioScanValue);
		temp=Result_BioScanValue.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioScanValue,OverallResult);
		
		String Result_BioKeyValue=WF_V.Verify_BioKeyValue(KeyEntry);
		System.out.println("Result_BioKeyValue=:"+Result_BioKeyValue);
		temp=Result_BioKeyValue.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioKeyValue,OverallResult);
		WF_A.Cancel(Changes);
		
		Expected=Description;
		String result_SRM_Scope_Bioburden="Result_RefNum=:"+Result_RefNum+". Result_BioStatus="+Result_BioStatus
				+". Result_BioScanValue="+Result_BioScanValue+". Result_BioKeyValue="+Result_BioKeyValue;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_SRM_Scope_Bioburden);
	}
	
	public void v_Staff() throws InterruptedException, AWTException {
		// Staff message verify
		System.out.println(getCurrentElement().getName());
		Res = EM_V.VerifyScanMsg("Staff " + ScanStaffID + " Scanned",Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult = actualResult + "\r\n" + getCurrentElement().getName()+ "---:\r\n\t" + UAS.Result;
		TH.WriteToTextFile(TestResFileName + ForFileName, actualResult);
		Expected="Staff that Scanned scope out of Bioburden";

		//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		CycleEvent="BioBurdenStaff";
		Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);			
		StaffOut_Assoc=Staff_IH[1];
		ActualCycleEvent=Staff_IH[5];
		
		ResultStaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
		temp=ResultStaffCycle.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultStaffCycle,OverallResult);

		ResultStaffOut=IHV.Result_Same_Assoc(AssociationID,StaffOut_Assoc)+" for staff and scope out of the bioburden.";

		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,ScanStaffID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		
		ResultStaffOut=ResultStaffOut+" ResultStaffCycle="+ResultStaffCycle+". ResultLastStaff="+ResultLastStaff;
		
		System.out.println(Scenario+":  "+ResultStaffOut);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffOut);
		
		//SRM Verification
		Thread.sleep(1000); //Wait 1 sec
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		IP_A.Click_Details(ScopeRefNo);
		
		Description="Verify Scope Record Management of "+ScanScope+" into "+Scanner+". ";
		
		Result_RefNum=WF_V.Verify_RefNum(ScopeRefNo);
		System.out.println("Result_RefNum="+Result_RefNum);
		temp=Result_RefNum.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_RefNum,OverallResult);
		
		String Result_BioStatus=WF_V.Verify_BioResult(Status);
		System.out.println("Result_BioStatus=:"+Result_BioStatus);
		temp=Result_BioStatus.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioStatus,OverallResult);
		
		String Result_BioScanValue=WF_V.Verify_BioScanValue(ScanResult);
		System.out.println("Result_BioScanValue=:"+Result_BioScanValue);
		temp=Result_BioScanValue.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioScanValue,OverallResult);
		
		String Result_BioKeyValue=WF_V.Verify_BioKeyValue(KeyEntry);
		System.out.println("Result_BioKeyValue=:"+Result_BioKeyValue);
		temp=Result_BioKeyValue.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioKeyValue,OverallResult);
		
		String Result_BioStaff=WF_V.Verify_BioStaff(ScanStaffID);
		System.out.println("Result_BioStaff=:"+Result_BioStaff);
		temp=Result_BioStaff.split("-");
		OverallResult=GF.FinalResult(temp[0], Result_BioStaff,OverallResult);
		WF_A.Cancel(Changes);
		
		Expected=Description;
		String result_SRM_Scope_Bioburden="Result_RefNum=:"+Result_RefNum+". Result_BioStatus="+Result_BioStatus
				+". Result_BioScanValue="+Result_BioScanValue+". Result_BioKeyValue="+Result_BioKeyValue+". Result_BioStaff="+Result_BioStaff;;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_SRM_Scope_Bioburden);
		
		//Verify MAM details
		Description="Verify Management and Asset Management of "+ScanScope+" into "+Scanner+". ";
		Expected=Description;
		MAM_A.Click_MaterialsAndAssetManagement();
		String result_MAM=MAM_V.verifyScopeDetails(ScanScope, "LAST SCOPE LOCATION=="+Scanner+" ("+Facility+");LAST SCAN STAFF ID=="+ScanStaffID
				+";EXAM COUNT=="+actualExamCount+";REPROCESSING COUNT=="+actualReproCount).toString();
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MAM);
	}
	
}
