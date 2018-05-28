package AdminUserStories;

import org.graphwalker.core.condition.StopConditionException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminFacilityPage.*;
import TestFrameWork.UnifiaAdminLocationPage.*;
import TestFrameWork.UnifiaAdminExamTypePage.*;
import TestFrameWork.UnifiaAdminScopeTypePage.*;
import TestFrameWork.UnifiaAdminScannerPage.*;
import TestFrameWork.UnifiaAdminScopePage.*;
import TestFrameWork.UnifiaAdminAccessPointPage.*;
import TestFrameWork.UnifiaAdminUserPage.*;
import TestFrameWork.UnifiaAdminStaffPage.*;
import TestFrameWork.UnifiaAdminScopeSafetyPage.*; //RK - 28Jan2016 
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Verification; //RK - 26Feb2016
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

/**
 * @author FETROJ
 * The purpose of this test is to verify the complex acceptance criteria that can not be easily tested by our 
 * model based testing approach. This scripted test will cover items from the following product backlog items:
 * PBI-1:  System Admin - Create and update Location Master Data.
 * 	AC-  If there are multiple facilities in the system the facility field shall not have a default.
 * 	Description - If there is only one active facility the facility in location shall default to that facility
 *  Description - the facility drop down shall only display active facilities 
 *  
 * PBI- 2:  Create and update Scope Master data.
 * 	AC- As a system admin, I would like to be able to associate a facility to a scope, from a list of active facilities so that I can report on scope availability for scheduled exams.
 *  
 * PBI- 3:  Create and update Scanner Master Data.
 * 
 * PBI- 4:  Create and update Scanner Master Data.
 * 
 * PBI- 5:  Create and update Scope Type Master Data.
 */
//Nicole please check test results sections since it seems even a Failed test will Pass whole test if test runs to completion
//NM 11/24/2015 - linking to user stories for tracing to user story 1182 and 1147

//NM 3feb2016 - added tests to verify the Barcode type of 'Bioburden Testing' is only available if the 'Bioburden' check box on the Scope Safty screen is selected. 
public class ComplexAdminTesting {
		
	/*public static String Env="sprinttest-03.mitlab.com"; //the environment used for testing. 
	public String Admin_URL="https://"+Env+":9753/Default.cshtml"; //the Admin URL for the environment being tested
//	public String Admin_URL="https://mit-sql-dev:44311/Default.cshtml"; //the Admin URL for the environment being tested
	public String QV_URL="https://"+Env+":9753/dashboard.cshtml"; //the QlikView URL for the environment being tested
	public static String url = "jdbc:sqlserver://"+Env+"\\sqlexpress;databaseName=Unifia"; //the database of the environment being tested. */
	
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public String A;
	//public static String url = "jdbc:sqlserver://10.170.93.205\\sqlexpress;databaseName=Unifia";  //NM 1/18/2016 this is the connection for TE-FACT-03. Must be updated if using another environment
	//public static String url = "jdbc:sqlserver://10.170.93.180:1433;databaseName=UnifiaStagingComplex";
	//public static String url = "jdbc:sqlserver://10.170.93.180:1433;databaseName=UnifiaIT";
	//public static String url = "jdbc:sqlserver://10.170.93.225\\sqlexpress;databaseName=Unifia";
	//public static String url = "jdbc:sqlserver://10.170.93.225\\sqlexpress;databaseName=Unifia";
	//URL with port #:  jdbc:sqlserver://10.170.93.180:1433;databaseName=UnifiaStagingComplex
   /* public static String user = "unifia";
    public static String pass = "0lympu$123";
    public static String connstring = url+";user="+user+";password="+pass;*/
	
    public String del_Scanner;
    public String del_Scope;
    public String ModLocationAct_Val; // result of current Active value of Location
    public String ModETAct_Val; // result of current Active value of Exam Type
    public String ModScopeTypeAct_Val; //result of current active value of Scope Type
    public String ModScopeTypeECN_Val; //result of current ECN value of Scope Type
    public String ModFacAct_Val; //result of current active value of facility
    public String ModScopeAct_Val; //result of current active value of scope
    public String ModStaffAct_Val; //result of current active value of Staff
    public String ModRoleAct_Val; //result of current active value of Role
    public String ResultRole1; //Verify the role is selected 
    public int Role_Val1; //used to get the current status of the role (selected=1, not selected=0)
    public String ResultRole2; //Verify the role is selected 
    public int Role_Val2; //used to get the current status of the role (selected=1, not selected=0)
    public String ResultRole3; //Verify the role is selected 
    public int Role_Val3; //used to get the current status of the role (selected=1, not selected=0)
    public String ResultRole4; //Verify the role is selected 
    public int Role_Val4; //used to get the current status of the role (selected=1, not selected=0)
    public String ResultRole5; //Verify the role is selected 
    public int Role_Val5; //used to get the current status of the role (selected=1, not selected=0)
    public String ResultRole6; //Verify the role is selected 
    public int Role_Val6; //used to get the current status of the role (selected=1, not selected=0)
    
    public String ResultFacility1; //Verify the Facility is selected 
    public int Facility_Val1; //used to get the current status of the facility (selected=1, not selected=0)
    public String ResultFacility2; //Verify the Facility is selected 
    public int Facility_Val2; //used to get the current status of the facility (selected=1, not selected=0)
    public String ResultFacility3; //Verify the Facility is selected 
    public int Facility_Val3; //used to get the current status of the facility (selected=1, not selected=0)
    public String ResultFacility4; //Verify the Facility is selected 
    public int Facility_Val4; //used to get the current status of the facility (selected=1, not selected=0)
    public String ResultFacility5; //Verify the Facility is selected 
    public String ResultFacility6; //[RK25Jan16] -Verify the Facility is selected  
    public int Facility_Val5; //used to get the current status of the facility (selected=1, not selected=0)
    public int Facility_Val6;//[RK25Jan16] - used to get the current status of the facility (selected=1, not selected=0)
    
    public String ScopeSafety_DefBioBurdenTesting; //RK-28Jan16 - Used to get the status of the Bioburden Testing
    public String ScopeSafety_DefCulturing; //RK-28Jan16 - Used to get the status of the Culturing
    public String ScopeSafety_DefScopeHangTime; //RK-28Jan16 - Used to get the value of the ScopeHangTime
    public String PrintBarcodeID18; //NM 3feb2016 - used to verify print barcode screen
    public String stmt_3;
    public String stmt_4;
    public String stmt_5;
    public String stmt_6;
    public int Facility_Val;
    
    public int Reconciliation_Val; //NM 26feb16 added to verify the Manager/Supervisor's default selections
	public int Anaylsis_Val; //NM 26feb16 added to verify the Manager/Supervisor's default selections

	public String FinalResult ="------------------Complex Admin Script TestSummary-----------------------";
    public String Result1;
    public String Result2;
    public String Result3;
    public String Result4;
    public String Result5;
    public String Result6;
    public String Result7;
    public String Result8;
    public String Result9;
    public String Result10;
    public String Result11;
    public String Result;
    public String Result12;
    public String Result13;
    public String Result14;
    public String Result15;
    public String Result16;
    public String Result17;
    public String Result18;
    public String Result19;
    public String Result20;
    public String Result21;
    public String Result22;
    public String Result23;
    public String Result24;
    public String Result25;
    public String Result26;
    public String Result27;
    public String Result28;
    public String Result29;
    public String Result30;
    public String Result31;
    public String Result32;
    public String Result33;
    public String Result34;
    public String Result35;
    public String Result36;
    public String Result37;
    public String Result38;
    public String Result39;
    public String Result40;
    public String Result41;
    public String Result42;
    public String Result43;
    public String Result44;
    public String Result45;
    public String Result46;
    public String Result48;
    public String Result49;
    public String Result53;
    public String Result54;
    public String Result55;
    public String Result56;
    public String Result57;
    public String Result58;
    public String Result59;
    public String Result60;
    public String Result61;
    public String Result62; //RK - 28Jan2016
    public String Result63; //RK - 28Jan2016
    public String Result64; //RK - 28Jan2016
    public String Result65; //RK - 28Jan2016
    public String Result66; //NM - 3feb2016
    public String Result67; //NM - 3feb2016
    public String Result68; //NM - 3feb2016
    public String Result69; //RK - 26Feb016
    public String Result71;

    
    public String GridID;
    
    Connection conn= null;
    TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
    TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LPA;
    TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
    TestFrameWork.UnifiaAdminFacilityPage.Facility_Actions FPA;
    TestFrameWork.UnifiaAdminFacilityPage.Facility_Verification FPV;
    TestFrameWork.UnifiaAdminLocationPage.Location_Actions LOCPA;
    TestFrameWork.UnifiaAdminLocationPage.Location_Verification LOCPV;
    TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions ETPA;
    TestFrameWork.UnifiaAdminScopeTypePage.ScopeType_Actions STPA;
    TestFrameWork.UnifiaAdminScopeTypePage.ScopeType_Verification STPV;
    TestFrameWork.UnifiaAdminScannerPage.Scanner_Actions SCNPA;
    TestFrameWork.UnifiaAdminScannerPage.Scanner_Verification SCNPV;
    TestFrameWork.UnifiaAdminScopePage.Scope_Actions SPPA;
    TestFrameWork.UnifiaAdminScopePage.Scope_Verification SPPV;
    TestFrameWork.UnifiaAdminAccessPointPage.AccessPoint_Actions APA;
    TestFrameWork.UnifiaAdminAccessPointPage.AccessPoint_Verification APV;
    TestFrameWork.UnifiaAdminStaffPage.Staff_Actions STAFFA;
    TestFrameWork.UnifiaAdminStaffPage.Staff_Verification STAFFV;
    TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
    TestFrameWork.UnifiaAdminUserPage.User_Verification UV;
    TestFrameWork.UnifiaAdminScopeSafetyPage.ScopeSafety_Actions SSA; //RK - 28Jan2016
    TestFrameWork.UnifiaAdminScopeSafetyPage.ScopeSafety_Verification SSV; //RK - 28Jan2016
    TestFrameWork.UnifiaAdminAssignBarcodePage.AssignBarcode_Actions ABA; //NM - 3feb2016
    TestFrameWork.UnifiaAdminAssignBarcodePage.AssignBarcode_Verification ABV; //NM - 3feb2016
    TestFrameWork.UnifiaAdminLandingPage.LandingPage_Verification LPV; //RK - 26Feb2016
	private TestDataFunc TDF;

    @Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, AWTException, IOException, SQLException {
    		//select the Driver type Grid or local

		
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}

    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
    		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,0, 0, 0);		
    		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);

    		try{		
    			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
    			Statement update1 = conn.createStatement();
    			Statement update2 = conn.createStatement();
    			Statement update3 = conn.createStatement();
    			Statement update4 = conn.createStatement();
    			Statement update5 = conn.createStatement();
    			Statement update6 = conn.createStatement();
    			Statement insertFAC = conn.createStatement();
    			Statement updateET= conn.createStatement();
    			Statement updateST= conn.createStatement();
    			Statement updateStaff= conn.createStatement();
    			Statement updateUser= conn.createStatement();
    			Statement insertAccessPoint= conn.createStatement();

    			update1.executeUpdate("delete from ProcedureRoomStatus;delete from ExamQueue;delete from SoiledAreaSignOff;delete from relateditem;delete from KeyEntryScans;delete from ReconciliationActivityLogValue;delete from Barcode where IsShipped=0;delete from ReconciliationActivityLog;delete from ReconciliationReportComment;;Delete FROM ScopeStatus;");
    			update1.executeUpdate("DELETE FROM ScopeCycle;Delete FROM ItemHistory;DELETE FROM Association;DELETE FROM LocationStatus;DELETE FROM Patient;Delete From ReprocessingStatus;");
    					
    			update1.executeUpdate("Delete FROM Scanner;");
    			//update1.close();
    			update2.executeUpdate("Delete FROM Scope;");
    			//update2.close();
    			update3.executeUpdate("Delete FROM ScopeType where IsShipped<>1;");	
    			update1.executeUpdate("Delete FROM AERDetail;"); //delete from AERDetail

    			//update3.close();
    			update1.executeUpdate("delete from association");
    			update4.executeUpdate("Delete FROM Location;");
    			update1.executeUpdate("Delete FROM AccessPoint;");
    			
    			//NR 11may15 added statements to delete all but the Admin user and delete all the staff.
    			updateUser.executeUpdate("Delete FROM User_Role_Assoc where UserID_PK>1;");
    			updateUser.executeUpdate("Delete FROM User_Facility_Assoc where UserID_PK>1;");
    			updateUser.executeUpdate("Delete FROM [User] where UserID_PK>1;");
    			updateStaff.executeUpdate("Delete FROM Staff "); 


    			update4.executeUpdate("Update [User] Set IsActive=1 Where UserID_PK IN (1);");
    			update5.executeUpdate("Delete FROM ExamType Where IsShipped<>1;");
    			//update5.close();
    			update6.executeUpdate("Delete FROM Facility where FacilityID_PK > 1;");
    			update6.executeUpdate("update Facility set Name='Your Facility Name' , Abbreviation ='YFN',IsPrimary=1,SerialNumber='111' where FacilityID_PK=1;");
    			update6.executeUpdate("Update Facility set IsBioburdenTestingPerformed=0, IsCulturingPerformed=0, where FacilityID_PK = 1;");
    			//update6.close();
    			//NR 1may15 the system is shipped with 'Your Facility Name' by default. therefore there is no need to insert a row. Changed all references of Default_Facility to Your Facility Name
    			//insertFAC.executeUpdate("Insert INTO Facility (FacilityId_PK,Name, IsActive,Abbreviation, CustomerNumber) Values (1,'Your Facility Name',1,'YFN', '');");
    			insertFAC.executeUpdate("Insert INTO Facility (Name, IsActive,Abbreviation, CustomerNumber, IsPrimary, SerialNumber) Values ('Inactive_Facility',0,'INACT', '',0,'444');");
    			//insertFAC.close();
    			//Need to Change this to only have a handful of Exam Types as Active
    			updateET.executeUpdate("Update ExamType Set IsActive=0 Where ExamTypeID_PK NOT IN (1,2,3);");
    			//updateET.close();
    			updateET.executeUpdate("Update ExamType Set IsActive=1 Where ExamTypeID_PK IN (1,2,3);");
    			//updateET.close();
    			//Need to Change this to only have a handful of Scope Types as Active
    			updateST.executeUpdate("Update ScopeType Set IsActive=0 Where ScopeTypeID_PK NOT IN (1,2,3);");
    			//updateST.close();
    			updateST.executeUpdate("Update ScopeType Set IsActive=1 Where ScopeTypeID_PK IN (0, 1,2,3);");
    			//updateST.close();		
    			//insert Access Point into Database
    			insertAccessPoint.executeUpdate("Insert INTO AccessPoint (SSID, Password) Values ('SSID1', 'SSID1');");		
    			conn.close();
    		}
    		catch (SQLException ex){
    		    // handle any errors
    		    System.out.println("SQLException: " + ex.getMessage());
    		    System.out.println("SQLState: " + ex.getSQLState());
    		    System.out.println("VendorError: " + ex.getErrorCode());	
    		}

		/**
		 *!!The assumption for this test is that before test runs and that all non-shipped data has been delete and 2 Facilities are added
		 *!!The two facilities are "Your Facility Name" and "Inactive_Facility", only "Your Facility Name" is active
		 * The plan for this test is to login and verify that the facility "Your Facility Name"  will be displayed in the Location Facility field.
		 * 
		 * Result 66 NM 3feb2016 verify the default for barcode type is blank 
		 * Result 67 NM 3feb2016 added test to verify 'Bioburden Testing' is not available on the Assign barcode screen when the checkbox on
		 * the scope safety screen for bio burden is not selected. 
		 * Result 68 NM 3feb2016 added test to verify 'Bioburden Testing' is  available on the Assign barcode screen when the checkbox on
		 * the scope safety screen for bio burden is  selected. 
		 * 
		 * Result 1  Verifies if only one Facility is active it is defaulted
		 * Result 30 NR 28apr15 added to Verifies the location SSID list defaults to 'SSID' when there is only one active SSID.
		 * Result 2  Verifies the Location Facility list contains only active values are in the list
		 * Result 3  Verifies that there is no default value in Location/ location type
		 * Result 4  Verifies that there is no location default value in Scanner
		 * Result 5  Verifies that Scanner Location list contains only active values
		 * Result 6  Verifies that the Scope type Exam type defaults to ""
		 * Result 7  Verifies that the Scope type Exam type list contains only active values
		 * Result 8  Scope Verifies that the Facility is defaulted to ""
		 * Result 9  Scope Verifies that the only active Facilities are displayed.
		 * Result 10  Scope Verifies that only active Scope Types are displayed
		 * Result 11  Scope Verifies that the Scope type default is an empty String
		 * Result  Scope Verifies that the Scope Status defaults to Active
		 * Result 12  Verifies that adding second facility removes a default value and the active list changed for Location Facility
		 * Result 13  Verifies that adding second facility the active list changed for Location Facility
		 * Result 14  Verifies that Scope facility is still defaulted to =""
		 * Result 15  Verifies that only active Scope Facilities are displayed.
		 * Result 16  Verifies that a duplicate Location name can be added, as long as the name is unique to a given facility
		 * Result 17  Verifies that a Location name can be modifies to a duplicate name, as long as the name is unique to a given facility
		 * Result 19 Verifies that a new scanner name defaults to null
		 * Result 20 Verifies that a new scanner name when saved as a null defaults to a concatenation of facility/location scanner name
		 * NR 26mar15 Result 20 is no longer a valid test and will be removed as per product backlog bug item 204 and 205.
		 * Result 21 Verifies that a modified scanner name when saved as a null defaults to a concatenation of facility/location scanner name
		 * NR 26mar15 Result 21 is no longer a valid test and will be removed as per product backlog bug item 204 and 205.
		 * Result 22 verifies that a Location cannot be deactivated with one or more active scanners
		 * Result 23 verifies that a Exam Type cannot be deactivated with one or more active scope types
		 * Result 24 verifies that a Scope Type cannot be deactivated with one or more active scopes
		 * Result 25 verifies that a Facility cannot be deactivated with one or more active scopes
		 * Result 26 verifies that facility with one or more active locations cannot be deactivated
		 * Result 27 verifies that the scope type of 'unknown' is available in the drop down on the scope page when adding or modifying a scope .  (NR 29apr15 added)
		 * Result 28 verifies that the scope type filter has 'unknown'  available in the drop down on the scope page .  (NR 29apr15 added)
		 * Result 29 verifies that the scope type page does not have a scope type of 'unknown' available. (NR 29apr15 added)
		 * Result 30 verifies that if there is only one SSID created, the location SSID field defaults to that value (NR 29apr15 added)
		 * Result 31 verifies the list of values in the location SSID field contains only 1 value when only 1 ssid is created.  (NR 29apr15 added)
		 * Result 32 verifies that if there are more than one SSID created, the location SSID field defaults to the empty/blank value. (NR 29apr15 added)
		 * Result 33 verifies the list of values in the location SSID field.  (NR 29apr15 added)
		 * Result34 verifies that special characters are not allowed in the Staff ID field on the Staff page.
		 * Result 35 verifies that scope types associated with ERCP have ECN checked by default
		 * Result 36 verifies that scope types NOT associated with ERCP have ECN unchecked by default
		 * Result 37 verifies that new scope types added do not have the ECN checked by default
		 * Result38 NR 11may15 added to verify the Staff is automatically set to Staff1, Staff1 when there is only one staff available.
		 * Result32 NM 21aug15 - updated verification that staff is empty by default. 
		 * Result39 NR 11may15 added to verify the User Default Facility is set to YFN when there is only one facility available.
		 * Result40 NR 11may15 added to verify the User Staff is automatically set to blank when there is more than one staff available.  
		 * Result41 NR 11may15 added to verify the User Default Facility is set to blank when there is more than one facility available. 
		 * Result42 NR 11may15 added to verify the user staff list with 2 available staff
		 * Result43 NR 11may15 added to verify the user default facility list with 2 available facilities.
		 * Result44 NR 11may15 added to verify Error code 8 Default Facility not in selected Facility Associations list: <Default Facility Abbreviation> does not exist in the selected facilities table.  error code: 8
		 * Result45 NR 11may15 added to verify the user is saved after correcting error code 8 (change default facility to User)
		 * Result46 NR 11may15 added to verify an error is given when attempting to mark a Staff as inactive when it is associated to a user
		 * Result47 NR 11may15 added to verify error is given when trying to disable a Role that is associated to a user --NM 10/13/2016 role screen is removed from UI. removing test
		 * Result48 NR 11may15 add to verify error is given when trying to disable a facility that is associated to a user.
		 * Result49 NR 11may15 add to verify the default facility can be changed without changing the facility association list.
		 * Result50 NR 11may15 added to verify a Staff can be marked as inactive, if the user associated to it is inactive. --NM10/6/2016 test was removed due to changes in user story 7063
		 * Result51 NR 11may15 added to verify if the user is inactive, the role associated to it can be made inactive --NM10/6/2016 test was removed due to changes in user story 7063
		 * Result52 NR 11may15 added to verify if the user is inactive, the facility associated to it can be made inactive  --NM10/6/2016 test was removed due to changes in user story 7063
		 * Result53 NR 11may15 add more facilities and role associations 
		 * Result54 NR 11may15 verify roles were added after clicking save. 
		 * Result55 NR 11may15 verify facilities were added after clicking save. 
		 * Result56 NR 11may15 verify the user saved without errors
		 * Result57 NR 11may15 verify the roles for the user were saved correctly.
		 * Result58 NR 11may15 verify the facilities for the user were saved correctly.
		 * Result59 NR 11may15 verify the user saved without errors
		 * Result60 NR 11may15 verify the roles for the user were saved correctly.
		 * Result61 NR 11may15 verify the facilities for the user were saved correctly.
		 * Result62 RK 28Jan16 Verify the Bioburden Testing field in Scope Safety List page  is by default unchecked when new facility is created
		 * Result63 RK 28Jan16 Verify the Culturing field in Scope Safety List page is by default unchecked when new facility is created
		 * Result64 RK 28Jan16 Verify the default value of Hang Time in Scope Safety List page  is '7'
		 * Result65 RK 28Jan16 Verify the inactive facility is not displayed in Scope Safety List page
		 * Result69 RK 26Feb16 Verify the Reconciliation tab is displayed for the user having Manager/Supervisor Role
		 * Result70 NM 26feb16 verify the default values of the Manager/Supervisor role (i.e. all analysis items and reconciliation) NM 13oct2016 removed test as role is no longer available in the admin UI
		 * Result71 RK 26Mar17 verify by default facility Your Facility Name is set as main facility (primary)
		 * 
		 */
		
		//The user logs onto the application
		//LGPA.Launch_Unifia();
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		LGPA.Logon_Username("uadmin"); //NR 1may15 changed user to admin was all
		LGPA.Logon_Password("Olympu$123");//NR 1may15 changed password to admin was all
		LGPA.Click_Submit();
		
		// verify facility yfn is defaulted as primary
		LPA.Click_Admin_Menu("Facility");
		FPA.Search_Facility_ByName("Your Facility Name");
		String isPrimary=FPV.getfacilityPrimaryValue("Your Facility Name");
		if (isPrimary.equalsIgnoreCase("true"))	{
			Result71="Passed: Facility 'Your facility Name' is set as main(primary) facility in shipped data";
		}else{
			Result71="#Failed!# : Facility 'Your facility Name' is not set as main(primary) facility in shipped data";
		}
		FinalResult =  FinalResult + "\r\n Result71 : " +Result71;
			
		//NM 3feb2016 add steps to verify Assign Barcode > Barcode Type defaults to blank and then the available options in the dropdown list  
		LPA.Click_Admin_Menu("AssignBarcode");
		ABA.Add_New_Barcode(); //click the 'Add New Row' icon
		Result=ABV.Verify_NewBarcodeType("");
		if(!(Result.contains("Fail")))
		{
			Result66="Passed : verified that the default for barcode type is blank";
		} else
		{
			Result66="#Failed!# : New Barcode type Verification Failed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result66 : " +Result66;
		System.out.println("Result66="+Result66);
		Result=ABV.Verify_Barcode_Type_List_Options(", Out of Facility Location, Reason for Reprocessing");
		
		if(!(Result.contains("Fail")))
		{
			Result67="Passed : verified that 'Bioburden Testing' is not available on the Assign barcode screen when the checkbox on the scope safety screen for bio burden is not selected.";
		} else
		{
			Result67="#Failed!# : Barcode type list options Verification Failed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result67 : " +Result67;
		System.out.println("Result67="+Result67);
		ABA.Cancel_Barcode_Edit();
		
		//NM 3feb2016 go to scope safety for Your Facility Name and select the Bioburden checkbox
		LPA.Click_Admin_Menu("ScopeSafety");
		SSA.Search_FacilityName("Your Facility Name"); //Enter the facility name in the scope safety grid.
		System.out.println("Search for Scope facility name to modify");	
		GridID=SSA.GetGridID_ScopeSafety_To_Modify("Your Facility Name"); // Get the GridID of the Scope Safety to be modified.
		System.out.println("GridID = "+GridID);
		SSA.Select_ScopeSafety_To_Modify("Your Facility Name"); //Select the row of the facility for Scope Hang Time (Days) modify.
		SSA.Modify_BiobTesting_Active("False","True");
		SSA.Save_ScopeSafety_Edit();

		//NM3feb2016 verify Barcode type list includes Bioburden Testing
		LPA.Click_Admin_Menu("AssignBarcode");
		ABA.Add_New_Barcode(); //click the 'Add New Row' icon
		Result=ABV.Verify_Barcode_Type_List_Options(", Bioburden Testing, Out of Facility Location, Reason for Reprocessing");
		if(!(Result.contains("Fail")))
		{
			Result68="Passed : verified that 'Bioburden Testing' is  available on the Assign barcode screen when the checkbox on the scope safety screen for bio burden is  selected. ";
		} else
		{
			Result68="#Failed!# : Verification to see whether Barcode type list includes Bioburden Testing Failed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result68 : " +Result68;
		System.out.println("Result68="+Result68);
		ABA.Cancel_Barcode_Edit();
	
		LPA.Click_Admin_Menu("Location");
		LOCPA.Add_New_Location();
		//Verifies that the only active Facility is defaulted
		Result=LOCPV.Verify_LocationFacility("Your Facility Name");
		if(!(Result.contains("Fail")))
		{
			Result1="Passed : Verified that only one Facility is active it is defaulted";
		} else
		{
			Result1="#Failed!# : Not only active Facility is defaulted \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result1 : " +Result1;
		System.out.println("Result1="+Result1);
		//Verifies that only the Active Facility is in the List
		Result=LOCPV.Verify_Location_Facility_List_Options("Your Facility Name");
		if(!(Result.contains("Fail")))
		{
			Result2="Passed : Verified that the Location Facility list contains only active values are in the list";
		} else
		{
			Result2="#Failed!# : Not only the Active Facility is in the List \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result2 : " +Result2;
		System.out.println("Result2="+Result2);
		//Verifies that the Location Type is not defaulted
		Result=LOCPV.Verify_LocationType("");
		if(!(Result.contains("Fail")))
		{
			Result3="Passed : Verified that there is no default value in Location/ location type";
		} else
		{
			Result3="#Failed!# : Location Type is not defaulted \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result3 : " +Result3;
		System.out.println("Result3="+Result3);
		
		//NR 29apr15 added tests for result 30 and 31.
		//Verifies that the only SSID is defaulted 
		Result=LOCPV.Verify_LocationSSID("SSID1");
		if(!(Result.contains("Fail")))
		{
			Result30="Passed : Verified that the location SSID list defaults to 'SSID' when there is only one active SSID.";
		} else
		{
			Result30="#Failed!# : Not only SSID is defaulted \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result30 : " +Result30;
		System.out.println("Result30="+Result30);
		//Verifies that only one SSID is in the List
		Result=LOCPV.Verify_Location_SSID_List_Options("SSID1");
		if(!(Result.contains("Fail")))
		{
			Result31="Passed : only one SSID is in the List";
		} else
		{
			Result31="#Failed!# : Not only one SSID is in the List \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result31 : " +Result31;
		System.out.println("Result31"+Result31);
		
		LOCPA.Enter_Location_Name("Default_Procedure_Room");
		LOCPA.Selct_Location_Type("Procedure Room");//Procedure Room
		LOCPA.Selct_New_Location_Active("True");
		//We need to create a function to verify that value is not in the 
		LOCPA.Save_Location_Edit();
		
		//NR 29ap15 add creation of a second access point and verify when more than one SSID is defined there is no default set and that all SSIDs are in the list of options
		LPA.Click_Admin_Menu("AccessPoint");
		APA.Add_New_AccessPoint();
		APA.Enter_SSID_New("SSID2");
		APA.Enter_Password_New("SSID2");
		APA.Save_AccessPoint_Edit();
		
		//Add a second active Location
		LPA.Click_Admin_Menu("Location");
		LOCPA.Add_New_Location();
		//NR 29apr15 added to verify the SSID is not defaulted when there is more than one SSID created.
		Result=LOCPV.Verify_LocationSSID("");
		if(!(Result.contains("Fail")))
		{
			Result32="Passed : the SSID is not defaulted when there is more than one SSID created.";
		} else
		{
			Result32="#Failed!# : the SSID is defaulted when there is more than one SSID created. \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result32 : " +Result32;
		System.out.println("Result32="+Result32);
		//NR 29apr15 added to Verifies that all SSIDs are in the List
		Result=LOCPV.Verify_Location_SSID_List_Options(", SSID1, SSID2");
		if(!(Result.contains("Fail")))
		{
			Result33="Passed : all SSIDs are in the List";
		} else
		{
			Result33="#Failed!# : all SSIDs are not in the List \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result33 : " +Result33;
		System.out.println("Result33="+Result33);
		//NR 29apr15 continue with test flow. 
		LOCPA.Selct_Location_SSID("SSID2");

		LOCPA.Enter_Location_Name("Default_Soiled_Area");
		LOCPA.Selct_Location_Type("Soiled Area");//Soiled Area
		LOCPA.Selct_New_Location_Active("True");
		LOCPA.Save_Location_Edit();
		//add a third inactive Location
		LOCPA.Add_New_Location();
		LOCPA.Enter_Location_Name("Default_Admin");
		LOCPA.Selct_Location_SSID("SSID2");

		LOCPA.Selct_Location_Type("Administration");//Administration
		LOCPA.Selct_New_Location_Active("False");
		LOCPA.Save_Location_Edit();
		//Switching to the Verify the locations in the (SCNPA, SCNPV)
		LPA.Click_Admin_Menu("Scanner");
		System.out.println("Click Scanner page");

		//Click add new Scanner
		SCNPA.Add_New_Scanner();
		//Verify that Location is defaulted to empty value-  a new verify function needs to be created for that
		// Expect value ="";
		
		Result=SCNPV.Verify_New_ScannerLocation("");
		if(!(Result.contains("Fail")))
		{
			Result4="Passed : Verified that there is no location default value in Scanner";
		} else
		{
			Result4="#Failed!# : Location is not defaulted to empty value- \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result4 : " +Result4;
		System.out.println("Result4="+Result4);
				
		//Verify that the Location list only contains active locations - a new verify function needs to be created for that
		//List of expected locations =", Default_Procedure_Room (YFN), Default_Soiled_Area (YFN)";
		
		Result=SCNPV.Verify_Scanner_Location_List_Options(", Default_Procedure_Room (YFN), Default_Soiled_Area (YFN)");
		if(!(Result.contains("Fail")))
		{
			Result5="Passed : Verified that Scanner Location list contains only active values";
		} else
		{
			Result5="#Failed!# : the Location list not only contains active locations \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result5 : " +Result5;
		System.out.println("Result5="+Result5);
		SCNPA.Cancel_Scanner_Edit();
		
		//Create scanner record with active = True, assign to an active location and test
		// deactivating location returns error
		SCNPA.Add_New_Scanner(); // add new scanner to test deactivating location with an active scanner
		SCNPA.Enter_Location_New("Default_Procedure_Room (YFN)");
		SCNPA.Enter_ScanID_New("RFSCID1");
		SCNPA.Enter_ScanName_New("Scanner1");
		SCNPA.Save_Scanner_Edit();
		LPA.Click_Admin_Menu("Location");
		GridID=LOCPA.GetGridID_Location_To_Modify("Default_Procedure_Room");
		LOCPA.Select_Location_To_Modify("Default_Procedure_Room");		
		System.out.println("GridID="+GridID);
		ModLocationAct_Val=LOCPA.Location_Active_Value();
		LOCPA.Modify_Location_Active(ModLocationAct_Val,"False"); // attempt to deactivate location
		LOCPA.Save_Location_Edit();
		Result=GF.Verify_ErrCode("9");
		if(!(Result.contains("Fail")))
		{
			Result22="Passed : Error code verified";
		} else
		{
			Result22="#Failed!# : Error code verification failed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result22 : " +Result22;
		System.out.println("Result22="+Result22);
		LOCPA.Cancel_Location_Edit();
		
		//reset location to active
		GridID=LOCPA.GetGridID_Location_To_Modify("Default_Procedure_Room");
		LOCPA.Select_Location_To_Modify("Default_Procedure_Room");
		LOCPA.Modify_Location_Active(ModLocationAct_Val,"True"); // attempt to deactivate location
		LOCPA.Save_Location_Edit();
		
		//
		//------------------------------------
		//Switching to Scope Type
		LPA.Click_Admin_Menu("ScopeModel");
		//Click add new Scope Model
		STPA.Add_New_ScopeType();
		//Verify that Exam type is empty
		//Expected result ="";
		Result=STPV.Verify_New_Exam_Type("");
		if(!(Result.contains("Fail")))
		{
			Result6="Passed : Verified that the Scope type Exam type defaults to empty";
		} else
		{
			Result6="#Failed!# : Exam type is not empty \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result6 : " +Result6;
		System.out.println("Result6="+Result6);
		//Verify that the Exam Type list only contains only active Exam Types
		//Verify that the list =", Anoscopy, Bronch, Colon"; should be abbrv
		Result=STPV.Verify_Exam_Type_List_Options(", Anoscopy, Bronch, Colon");
		if(!(Result.contains("Fail")))
		{
			Result7="Passed : Verified that the Scope type Exam type list contains only active values";
		} else
		{
			Result7="#Failed!# : Exam type list doesnot contain Anoscopy, Bronch, Colon \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result7 : " +Result7;
		System.out.println("Result7="+Result7);
		//Create and Save New Scope Type
		STPA.enterNewScopeType("NewScopeType");
		STPA.Select_ExamType_New("Colon");//Choose of the Active ETs
		STPA.Select_New_ScopeType_Active("True");
		STPA.Save_ScopeType_Edit();
		
		//----------------------------------------------
		//Switch to Scope (SPPA, SPPV)
		LPA.Click_Admin_Menu("Scope");
		//Click add scope
		SPPA.Add_New_Scope();
		//Verify that Facility is defaulted to ""
		Result=SPPV.Verify_New_Facility_Selection("Your Facility Name");
		if(!(Result.contains("Fail")))
		{
			Result8="Passed : Scope Verified that the Facility is defaulted to empty ";
		} else
		{
			Result8="#Failed!# : Facility is not defaulted to empty \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result8 : " +Result8;
		System.out.println("Result8="+Result8);
		//Verify that only active Facilities are displayed.
		//Verify that the list is =", Your Facility Name";
		Result=SPPV.Verify_Facility_List_Options("Your Facility Name");
		if(!(Result.contains("Fail")))
		{
			Result9="Passed : Scope Verified that the only active Facilities are displayed. ";
		} else
		{
			Result9="#Failed!# : Not only active Facilities are displayed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result9 : " +Result9;
		System.out.println("Result9="+Result9);
		//Verify that only active Scope Types are displayed, this list should include the new scope type added in the section above
		// Verify that the list is =", BF-10, BF-160, BF-1T10, NewScopeType"
		Result=SPPV.Verify_ScopeType_List_Options(", BF-10, BF-160, BF-1T10, NewScopeType, Unknown");
		if(!(Result.contains("Fail")))
		{
			Result10="Passed : only active Scope Types are displayed, this list should include the new scope type added in the section above";
		} else
		{
			Result10="#Failed!# : Not only active Scope Types are displayed, this list should include the new scope type added in the section above \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result10 : " +Result10;
		System.out.println("Result10="+Result10);
		//Verify that Scope type default is an empty String.
		//VErify that the value is ="";
		Result=SPPV.Verify_New_Facility_Selection("Your Facility Name");// Change to Scope type
		if(!(Result.contains("Fail")))
		{
			Result11="Passed : Scope type default is an empty String.";
		} else
		{
			Result11="#Failed!# : Scope type default is not an empty String. \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result11 : " +Result11;
		System.out.println("Result11="+Result11);
		Result=SPPV.Verify_NewScopeStatus("True");
		if(!(Result.contains("Fail")))
		{
			Result="Passed : New Scope Status verified";
		} else
		{
			Result="#Failed!# : New Scope Status verification failed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result : " +Result;
		System.out.println("Result="+Result);
		//LCS 05/06/2015 Scope Status is no longer a drop down
		
		//Add aCancel edit action
		SPPA.Cancel_Scope_Edit();
		
		//Verify Exam Type cannot be deactivated with an active scope type.
		LPA.Click_Admin_Menu("ExamType");
		ETPA.ClearExamTypeSrchCritera();
		ETPA.SearchExamTypeByAbbrv("Colon");

		GridID=ETPA.GetGridID_ExamType_To_Modify("Colon");
		System.out.println("GridID="+GridID);
		ModETAct_Val=ETPA.ExamType_Active_Value("Colon");
		System.out.println("ModETAct_Val="+ModETAct_Val);
		ETPA.Select_ExamType_To_Modify("Colon");
		ETPA.Modify_ExamType_IsActive(GridID,ModETAct_Val,"False");
		ETPA.Save_ExamType_Edit();
		Result=GF.Verify_ErrCode("9");
		if(!(Result.contains("Fail")))
		{
			Result23="Passed : verified that a Exam Type cannot be deactivated with one or more active scope types";
		} else
		{
			Result23="#Failed!# : Error code 9 verification failed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result23 : " +Result23;
		System.out.println("Result23="+Result23);
		ETPA.Cancel_ExamType_Edit();
		// 
		
		//add scope to test deactivating scope type with and active scope
		LPA.Click_Admin_Menu("Scope");
		SPPA.Add_New_Scope();
		SPPA.Selct_New_ScopeType("NewScopeType");
		SPPA.Enter_New_RFUID("RFUIDCTest");
		SPPA.Enter_New_Scope_Name("ComplexTest");
		SPPA.Enter_New_SerialNumber("06211954");
		SPPA.Selct_New_Scope_Facility("Your Facility Name");
		SPPA.Selct_New_ScopeStatus("Active");
		SPPA.Save_Scope_Edit();
		
		//test deactivating scope type with active scope
		LPA.Click_Admin_Menu("ScopeModel");
		STPA.SearchScopeType("NewScopeType");
		GridID=STPA.GetGridID_ScopeType_To_Modify("NewScopeType");
		ModScopeTypeAct_Val=STPA.ScopeType_Active_Value("NewScopeType",GridID);		
		STPA.Select_ScopeType_To_Modify("NewScopeType");
		STPA.Modify_ScopeType_IsActive(GridID,ModScopeTypeAct_Val, "False");
		STPA.Save_ScopeType_Edit();
		Result=GF.Verify_ErrCode("9");
		if(!(Result.contains("Fail")))
		{
			Result24="Passed : verified that a Scope Type cannot be deactivated with one or more active scopes";
		} else
		{
			Result24="#Failed!# : Error code 9 verification failed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result24 : " +Result24;
		System.out.println("Result24="+Result24);
		STPA.Cancel_ScopeType_Edit();
		

		
		//NR 11may15 added test to verify that a new user has Default Facility set to YFN when only one facility is active.
		LPA.Click_Admin_Menu("User");
        Thread.sleep(2000);
		UA.ClearAllSearchCriteria(); //Clear all search criteria
		UA.Search_User_ByName("qvtest01"); //Search for the user name to be modified.
		GridID=UA.GetGridID_User_To_Modify("qvtest01"); //Get the grid id for the user name to be modified.
		UA.Select_User_To_Modify("qvtest01"); //Select the user name to be modified.

		Result=UV.Verify_Staff_Selection("");
		if(!(Result.contains("Fail")))
		{
			Result38="Passed : Staff is empty";
		} else
		{
			Result38="#Failed!# : Staff is not empty \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result38 : " +Result38;
	  	System.out.println("Result38: " + Result38);
		Result=UV.Verify_DefaultFacility_Selection("YFN");
		if(!(Result.contains("Fail")))
		{
			Result39="Passed : new user has Default Facility set to YFN when only one facility is active.";
		} else
		{
			Result39="#Failed!# : new user does not have Default Facility set to YFN when only one facility is active. \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result39 : " +Result39;
	  	System.out.println("Result39: " + Result39);
		UA.Cancel_User_Edit();
		

		//-----------------------------------------------------
		//Switch to facility and add a second facility and verify the change in default value and the active list
		System.out.println("Click Facility Link");
		LPA.Click_Admin_Menu("Facility");
		System.out.println("Click add new facility");
		FPA.Add_New_Facility();
		
		FPA.Enter_New_Facility_Name("Second Facility");
		FPA.Enter_New_Facility_Abbreviation("2nd");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("11111");
		
		//FPA.Enter_New_Facility_HangTime("7");
		FPA.Save_Facility_Edit();
		//Check the second facility removes a default value and the active list changed.
		//Check in Location
		LPA.Click_Admin_Menu("Location");
		LOCPA.Add_New_Location();
		Result=LOCPV.Verify_LocationFacility("");
		if(!(Result.contains("Fail")))
		{
			Result12="Passed : Empty Location verified";
		} else
		{
			Result12="#Failed!# : Location verification Failed \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result12 : " +Result12;
		System.out.println("Result12="+Result12);
		//Verifies that only the Active Facility is in the List
		Result=LOCPV.Verify_Location_Facility_List_Options(", Second Facility, Your Facility Name");
		if(!(Result.contains("Fail")))
		{
			Result13="Passed : only the Active Facility is in the List";
		} else
		{
			Result13="#Failed!# : Not only the Active Facility is in the List \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result13 : " +Result13;
		System.out.println("Result13="+Result13);
		LOCPA.Cancel_Location_Edit();
		//Check in Scope
		LPA.Click_Admin_Menu("Scope");
		//Click add scope
		//Verify that Facility is defaulted to ="";
		SPPA.Add_New_Scope();
		Result=SPPV.Verify_New_Facility_Selection("");
		if(!(Result.contains("Fail")))
		{
			Result14="Passed : Facility is defaulted to empty";
		} else
		{
			Result14="#Failed!# : Facility is not defaulted to empty \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result14 : " +Result14;
		System.out.println("Result14="+Result14);
		//Verify that only active Facilities are displayed.
		//Verify that the list is =", Second Facility, Your Facility Name";
		Result=SPPV.Verify_Facility_List_Options(", Second Facility, Your Facility Name");
		if(!(Result.contains("Fail")))
		{
			Result15="Passed : only active Facilities are displayed.";
		} else
		{
			Result15="#Failed!# : Not only active Facilities are displayed. \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result15 : " +Result15;
		System.out.println("Result15="+Result15);
		SPPA.Cancel_Scope_Edit();
		
		//NR 11may15 added test to verify User default facility and staff are set to blank when there are more than one facility and staff available.
		LPA.Click_Admin_Menu("Staff");
		STAFFA.Add_New_Staff();
		STAFFA.Selct_New_Title("Dr.");
		STAFFA.Enter_New_StaffID("ComplexID2");
		STAFFA.Enter_New_Staff_FirstName("Jane");
		STAFFA.Enter_New_Staff_LastName("Thomas");
		STAFFA.Selct_New_Staff_Type("Physician");
		STAFFA.Save_Staff_Edit();

		LPA.Click_Admin_Menu("User");
		UA.ClearAllSearchCriteria(); //Clear all search criteria
		UA.Search_User_ByName("qvtest01"); //Search for the user name to be modified.
		GridID=UA.GetGridID_User_To_Modify("qvtest01"); //Get the grid id for the user name to be modified.
		UA.Select_User_To_Modify("qvtest01"); //Select the user name to be modified.

		Result=UV.Verify_Staff_Selection("");
		if(!(Result.contains("Fail")))
		{
			Result40="Passed : Staff is empty";
		} else
		{
			Result40="#Failed!# : Staff is not empty \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result40 : " +Result40;
	  	System.out.println("Result40: " + Result40);
		Result=UV.Verify_DefaultFacility_Selection("YFN");//NM 4oct16 change from "" to "YFN" as per user story 7063
		if(!(Result.contains("Fail")))
		{
			Result41="Passed : Default facility is YFN";
		} else
		{
			Result41="#Failed!# : Default facility is not YFN \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result41 : " +Result41;
	  	System.out.println("Result41: " + Result41);
		Result=UV.Verify_Staff_List_Options(", Thomas, Jane");
		if(!(Result.contains("Fail")))
		{
			Result42="Passed : Staff list contain Thomas, Jane";
		} else
		{
			Result42="#Failed!# : Staff list does not contain Thomas, Jane \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result42 : " +Result42;
	  	System.out.println("Result42: " + Result42);
		Result=UV.Verify_DefaultFacility_List_Options(", 2nd, YFN");
		if(!(Result.contains("Fail")))
		{
			Result43="Passed : Facility list contain 2nd, YFN";
		} else
		{
			Result43="#Failed!# : Facility list does not contain 2nd, YFN \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result43 : " +Result43;
	  	System.out.println("Result43: " + Result43);
		UA.Cancel_User_Edit();
		
		//Adding verification of the duplicate name
		//a duplicate name can be entered, however the name must unique per facility
		//This will verify for the new location
		LPA.Click_Admin_Menu("Location");
		LOCPA.Add_New_Location();
		//Entering a duplicate location for Default_Procedure_Room
		//Default_Procedure_Room already exists in the Default facility
		LOCPA.Enter_Location_Name("Default_Procedure_Room");
		LOCPA.Selct_Location_Facility("Second Facility");
		LOCPA.Selct_Location_Type("Procedure Room");//Procedure Room
		LOCPA.Selct_Location_SSID("SSID2");
		LOCPA.Selct_New_Location_Active("True");
		LOCPA.Save_Location_Edit();
		LOCPA.Search_Location_ByFacility("Second Facility");
		LOCPA.Search_Location_ByName("Default_Procedure_Room");
		GridID=LOCPA.GetGridID_Location_To_Modify("Default_Procedure_Room");
		LOCPA.Select_Location_To_Modify("Default_Procedure_Room");
		Result=LOCPV.Verify_LocationName("Default_Procedure_Room");
		if(!(Result.contains("Fail")))
		{
			Result16="Passed : Location Name Default_Procedure_Room Verified";
		} else
		{
			Result16="#Failed!# : Location Name not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result16 : " +Result16;
		System.out.println("Result16="+Result16);
		LOCPA.Cancel_Location_Edit();
		//Need to add verification of the save
		//Adding a new location to modify to be a duplicate name
		LOCPA.Add_New_Location();
		LOCPA.Enter_Location_Name("Soiled Area 2");
		LOCPA.Selct_Location_Facility("Second Facility");
		LOCPA.Selct_Location_Type("Procedure Room");//Procedure Room
		LOCPA.Selct_Location_SSID("SSID2");
		LOCPA.Selct_New_Location_Active("True");
		LOCPA.Save_Location_Edit();
		//Modify soiled room 2 to be Default_Soiled_Area
		//Exists in Your Facility Name, this should create a Default_Soiled_Area in Second Facility
		LOCPA.Search_Location_ByName("Soiled Area 2");
		GridID=LOCPA.GetGridID_Location_To_Modify("Soiled Area 2");
		LOCPA.Select_Location_To_Modify("Soiled Area 2");
		LOCPA.Enter_Location_Name("Default_Soiled_Area");
		LOCPA.Save_Location_Edit();
		LOCPA.Search_Location_ByFacility("Second Facility");
		LOCPA.Search_Location_ByName("Default_Soiled_Area");
		GridID=LOCPA.GetGridID_Location_To_Modify("Default_Soiled_Area");
		LOCPA.Select_Location_To_Modify("Default_Soiled_Area");
		Result=LOCPV.Verify_LocationName("Default_Soiled_Area");
		if(!(Result.contains("Fail")))
		{
			Result17="Passed : Location Name Default_Soiled_Area Verified";
		} else
		{
			Result17="#Failed!# : Location Name not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result17 : " +Result17;
		System.out.println("Result17="+Result17);
		LOCPA.Cancel_Location_Edit();
		
		//Test whether facility with one or more active scopes can be deactivated
		
		// add 3rd Facility
		LPA.Click_Admin_Menu("Facility");
		System.out.println("Click Facility Link");
		System.out.println("Click add new facility");
		FPA.Add_New_Facility();
		FPA.Enter_New_Facility_Name("Third Facility");
		FPA.Enter_New_Facility_Abbreviation("3rd");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("22222");
		//FPA.Enter_New_Facility_HangTime("7");
		FPA.Save_Facility_Edit();
		
		// add scope to 3rd Facility with no locations
		LPA.Click_Admin_Menu("Scope");
		SPPA.Add_New_Scope();
		SPPA.Selct_New_ScopeType("BF-10");
		SPPA.Enter_New_RFUID("RFUIDCTest2");
		SPPA.Enter_New_Scope_Name("ComplexTest25");
		SPPA.Enter_New_SerialNumber("05212054");
		SPPA.Selct_New_Scope_Facility("Third Facility");
		SPPA.Selct_New_ScopeStatus("True");
		SPPA.Save_Scope_Edit();
		// edit facility active to false
		LPA.Click_Admin_Menu("Facility");
		FPA.Search_Facility_ByName("Third Facility");  //Search for the Facility Name to be modified in the Application.
		GridID=FPA.GetGridID_Facility_To_Modify("Third Facility");
		ModFacAct_Val=FPA.Facility_Active_Value("Third Facility");		
		FPA.Select_Facility_To_Modify("Third Facility");
		FPA.Modify_Facility_Active(GridID,ModFacAct_Val,"False");
		FPA.Save_Facility_Edit();
		Result=GF.Verify_ErrCode("9");
		if(!(Result.contains("Fail")))
		{
			Result25="Passed : Error code 9 verified";
		} else
		{
			Result25="#Failed!# : Error code 9 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result25 : " +Result25;
		System.out.println("Result25="+Result25);
		FPA.Cancel_Facility_Edit();
		
		// test 26 to try to deactivate a facility with one or more active locations
		
		//deactivate scope from test 25
		LPA.Click_Admin_Menu("Scope");
		GridID=SPPA.GetGridID_Scope_To_Modify("ComplexTest25");
		SPPA.Select_Scope_To_Modify("ComplexTest25");
		SPPA.Selct_Modify_ScopeStatus(GridID,"False","True");
		SPPA.Save_Scope_Edit();
		
		//add location to 3rd Facility
		LPA.Click_Admin_Menu("Location");
		LOCPA.Add_New_Location();
		LOCPA.Enter_Location_Name("Soiled Area 3");
		LOCPA.Selct_Location_Facility("Third Facility");
		LOCPA.Selct_Location_Type("Soiled Area");//Procedure Room
		LOCPA.Selct_Location_SSID("SSID1");
		LOCPA.Selct_New_Location_Active("True");
		LOCPA.Save_Location_Edit();
		
		//attempt to deactivate facility
		LPA.Click_Admin_Menu("Facility");
		FPA.Search_Facility_ByName("Third Facility");  //Search for the Facility Name to be modified in the Application.

		GridID=FPA.GetGridID_Facility_To_Modify("Third Facility");
		ModFacAct_Val=FPA.Facility_Active_Value("Third Facility");		
		FPA.Select_Facility_To_Modify("Third Facility");
		FPA.Modify_Facility_Active(GridID,ModFacAct_Val,"False");
		FPA.Save_Facility_Edit();
		Result26=GF.Verify_ErrCode("9");
		if(!(Result.contains("Fail")))
		{
			Result26="Passed : Error code 9 verified";
		} else
		{
			Result26="#Failed!# : Error code 9 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result26 : " +Result26;
		System.out.println("Result26="+Result26);
		FPA.Cancel_Facility_Edit();
		
		//verify 'unknown' is available when creating a new scope. 
		
		//-------------------------------------------------------
		LPA.Click_Admin_Menu("Scope");
		SPPA.Add_New_Scope();
		SPPA.Selct_New_ScopeType("Unknown");
		Result=SPPV.Verify_NewScopeType("Unknown");
		if(!(Result.contains("Fail")))
		{
			Result27="Passed : Scope type unknown is verified";
		} else
		{
			Result27="#Failed!# : Scope type unknown is not verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result27 : " +Result27;
		SPPA.Enter_New_RFUID("RF181818181");
		SPPA.Enter_New_Scope_Name("Scope181818181");
		SPPA.Enter_New_SerialNumber("SNO181818181");
		SPPA.Selct_New_Scope_Facility("Your Facility Name");
		SPPA.Save_Scope_Edit();
		//verify 'unknown' is available in the scope type filter drop down on the scope page 

		SPPA.ClearAllSearchCriteria();
		SPPA.Search_Scope_ByScopeType("Unknown");
		GridID=SPPA.GetGridID_Scope_To_Modify("Scope181818181");
		if(!GridID.equalsIgnoreCase(null)){
			Result28="Passed: GridID identified";
		} else {
			Result28="#Failed!#: GridID unidentified";
		}
		FinalResult =  FinalResult + "\r\n Result28 : " +Result28;
	    System.out.println("Result28: " + Result28);

		//Result 29 verifies that the scope type page does not have a scope type of 'unknown' available.
	    LPA.Click_Admin_Menu("ScopeModel");
		STPA.SearchScopeType("Unknown");
		Result=STPV.Verify_ScopeType_Page("0");
		if(!(Result.contains("Fail")))
		{
			Result29="Passed: Scope Type Page 0 verified";
		} else {
			Result29="#Failed!#: Scope Type Page 0 is not verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result29 : " +Result29;
	    System.out.println("Result29: " + Result29);

	    //NR 29apr15 - added test to verify  Staff ID does not allow special Characters
		LPA.Click_Admin_Menu("Staff");
	    //NR 29apr15 - added test to verify  Staff ID does not allow special Characters
		LPA.Click_Admin_Menu("Staff");
		STAFFA.Add_New_Staff();
		STAFFA.Enter_New_Staff_FirstName("First Name");
		STAFFA.Enter_New_Staff_LastName("Last Name");
		STAFFA.Enter_New_StaffID("ID!");
		STAFFA.Selct_New_Staff_Type("Physician");
		STAFFA.Selct_New_StaffStatus("True");
		STAFFA.Save_Staff_Edit();
		Result=GF.Verify_ErrCode("15");
		if(!(Result.contains("Fail")))
		{
			Result34="Passed : verified that special characters are not allowed in the Staff ID field on the Staff page.";
		} else
		{
			Result34="#Failed!# : Error code 15 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result34 : " +Result34;
	    System.out.println("Result34: " + Result34);
		STAFFA.Cancel_Staff_Edit();
		
		//Reactivate scope types
		try{
			//conn= DriverManager.getConnection(url, user, pass);
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement updateET= conn.createStatement();
			Statement updateST= conn.createStatement();
			updateET.executeUpdate("Update ExamType Set IsActive=1 Where IsShipped=1;");
			updateET.close();
			updateST.executeUpdate("Update ScopeType Set IsActive=1 Where IsShipped=1;");
			updateST.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	
		
		//Result 35 verifies that scope types associated with ERCP have ECN checked by default
/**		LPA.Click_Admin_Menu("ScopeModel");
		STPA.SearchScopeType("JF-130");
		GridID=STPA.GetGridID_ScopeType_To_Modify("JF-130");
		ModScopeTypeECN_Val=STPA.Scope_ECN_Value(GridID);
		STPA.Select_ScopeType_To_Modify("JF-130");
		if(ModScopeTypeECN_Val.equalsIgnoreCase("True")){
			Result35="Pass";
		}
		else{
			Result35="Fail";
		}
	    System.out.println("Result35: " + Result35);
	    STPA.Cancel_ScopeType_Edit();
	    
	  //Result 36 verifies that scope types NOT associated with ERCP have ECN unchecked by default
	  	LPA.Click_Admin_Menu("ScopeModel");
	  	STPA.SearchScopeType("BF-10");
	  	GridID=STPA.GetGridID_ScopeType_To_Modify("BF-10");
	  	ModScopeTypeECN_Val=STPA.Scope_ECN_Value(GridID);
	  	STPA.Select_ScopeType_To_Modify("BF-10");
	  	if(ModScopeTypeECN_Val.equalsIgnoreCase("False")){
	  		Result36="Pass";
	  	}
	  	else{
	  		Result36="Fail";
	  	}
	  	System.out.println("Result36: " + Result36);
	  	   STPA.Cancel_ScopeType_Edit();
	  	
	  //Result 37 verifies that new scope types added do not have the ECN checked by default   
	  	LPA.Click_Admin_Menu("ScopeModel");
	  	STPA.Add_New_ScopeType();
	  	STPA.Select_ExamType_New("ERCP");
	  	STPA.enterNewScopeType("STTest");
	  	STPA.Save_ScopeType_Edit();
	  	STPA.SearchScopeType("STTest");
	  	GridID=STPA.GetGridID_ScopeType_To_Modify("STTest");
	  	ModScopeTypeECN_Val=STPA.Scope_ECN_Value(GridID);
	  	STPA.Select_ScopeType_To_Modify("STTest");
	  	if(ModScopeTypeECN_Val.equalsIgnoreCase("False")){
	  		Result37="Pass";
	  	}
	  	else{
	  		Result37="Fail";
	  	}
	  	System.out.println("Result37: " + Result37);
	  	STPA.Cancel_ScopeType_Edit();
	  	**/
	  	
	  	//NR 11may15 added to verify User Screen Default Facility cannot be a value that is not associated to the user.
	  	// Also create a facility to assign to a user and make sure it cannot be marked as inactive unless the user is marked as inactive first. (cascading test)
	  	//Also verify that a staff associated to a user cannot be marked inactive until after the user is marked inactive.
	  	
		LPA.Click_Admin_Menu("Facility");
		System.out.println("Click add new facility");
		FPA.Add_New_Facility();		
		FPA.Enter_New_Facility_Name("User Facility");
		FPA.Enter_New_Facility_Abbreviation("User");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("33333");
		//FPA.Enter_New_Facility_HangTime("7");
		FPA.Save_Facility_Edit();
		
		LPA.Click_Admin_Menu("User");
		UA.ClearAllSearchCriteria(); //Clear all search criteria
		UA.Search_User_ByName("qvtest01"); //Search for the user name to be modified.
		GridID=UA.GetGridID_User_To_Modify("qvtest01"); //Get the grid id for the user name to be modified.
		UA.Select_User_To_Modify("qvtest01"); //Select the user name to be modified.
		UA.Select_User_DefaultFacility("User");
		//UA.Select_UserFacility("User"); //Select User to associate User Facility to the user.
		UA.Select_User_Staff("Thomas, Jane");
	  	System.out.println("break point");

		UA.Select_User_Role("Manager/Supervisor");
		UA.Save_User_Edit();
		Result=GF.Verify_ErrCode("8");
		if(!(Result.contains("Fail")))
		{
			Result44="Passed : verified Error code 8 Default Facility not in selected Facility Associations list: <Default Facility Abbreviation> does not exist in the selected facilities table.  error code: 8";
		} else
		{
			Result44="#Failed!# : Error code 8 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result44 : " +Result44;
	  	System.out.println("Result44: " + Result44);
		UA.Select_UserFacility("User"); //Select YFN to associate Your Facility Name to the user.

		UA.Save_User_Edit();
		Result=GF.Verify_ErrCode("0");
		if(!(Result.contains("Fail")))
		{
			Result45="Passed : verified that the user is saved after correcting error code 8 (change default facility to User)";
		} else
		{
			Result45="#Failed!# : Error code 0 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result45 : " +Result45;
	  	System.out.println("Result45: " + Result45);

	  	//NR 11may15 added to verify error is given when trying to disable a staff that is associated to a user
		LPA.Click_Admin_Menu("Staff");
		STAFFA.Search_Staff_ByStaffID("ComplexID2");
		GridID=STAFFA.GetGridID_Staff_To_Modify("ComplexID2");
		ModStaffAct_Val=STAFFA.Staff_Active_Value("ComplexID2");
		STAFFA.Select_Staff_To_Modify("ComplexID2");
		STAFFA.Selct_Modify_StaffStatus(GridID, ModStaffAct_Val, "False");
		STAFFA.Save_Staff_Edit();
		Result=GF.Verify_ErrCode("9");
		if(!(Result.contains("Fail")))
		{
			Result46="Passed : verified an error is given when attempting to mark a Staff as inactive when it is associated to a user";
		} else
		{
			Result46="#Failed!# : Error code 9 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result46 : " +Result46;
	  	System.out.println("Result46: " + Result46);
		STAFFA.Cancel_Staff_Edit();


		
		//NR 11may15 add to verify error is given when trying to disable a facility that is associated to a user.
		LPA.Click_Admin_Menu("Facility");
		FPA.Search_Facility_ByName("User Facility");
		GridID=FPA.GetGridID_Facility_To_Modify("User Facility");
		ModFacAct_Val=FPA.Facility_Active_Value("User Facility");		
		FPA.Select_Facility_To_Modify("User Facility");
		FPA.Modify_Facility_Active(GridID,ModFacAct_Val,"False");
		FPA.Save_Facility_Edit();
		Result=GF.Verify_ErrCode("9");
		if(!(Result.contains("Fail")))
		{
			Result48="Passed : verified an error is given when trying to disable a facility that is associated to a user.";
		} else
		{
			Result48="#Failed!# : Error code 9 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result48 : " +Result48;
		System.out.println("Result48="+Result48);
		FPA.Cancel_Facility_Edit();

		//NR 11may15 add to verify the default facility can be changed without changing the facility association list.
		LPA.Click_Admin_Menu("User");
		UA.Search_User_ByName("qvtest01");
		GridID=UA.GetGridID_User_To_Modify("qvtest01");
		UA.Select_User_To_Modify("qvtest01");

		UA.Select_User_DefaultFacility("YFN");
		UA.Save_User_Edit();
		Result=GF.Verify_ErrCode("0");
		if(!(Result.contains("Fail")))
		{
			Result49="Passed : verified that the default facility can be changed without changing the facility association list.";
		} else
		{
			Result49="#Failed!# : Error code 0 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result49 : " +Result49;
	  	System.out.println("Result49: " + Result49);
		UA.Select_User_To_Modify("qvtest01");
		
		UA.Save_User_Edit();

	  	//NR 11may15 added to verify if the user is inactive, the staff associated to it can be made inactive
	  	
	 	//Start - RK 28Jan16 Adding a new facility to verify default values in scope safety screen when a facility is created
		LPA.Click_Admin_Menu("Facility");
	  	FPA.Add_New_Facility();		
		FPA.Enter_New_Facility_Name("Default Values Facility");
		FPA.Enter_New_Facility_Abbreviation("DVF");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("44444");
		FPA.Save_Facility_Edit();
		//Navigate to Scope Safety screen and verify the default values
		LPA.Click_Admin_Menu("ScopeSafety");
		//Search for newly created facility in the Scope Safety screen
		SSA.Search_FacilityName("Default Values Facility");
		//Verify the default values for the newly create facility
		ScopeSafety_DefBioBurdenTesting=SSA.ModiBioTesting_Active_Value();
		if (ScopeSafety_DefBioBurdenTesting.equalsIgnoreCase("False")) {
			Result62="Passed : Verified the Bioburden Testing field in Scope Safety List page  is by default unchecked when new facility is created";
		}
		else{
			Result62="#Failed!# - In Scope Safety List page the Bioburden Testing field is checked whereas it should be actually unchecked";
		}
		FinalResult =  FinalResult + "\r\n Result62 : " +Result62;
		System.out.println ("Result62 ="+Result62);
		ScopeSafety_DefCulturing=SSA.ModiCulturing_Active_Value();
		if (ScopeSafety_DefCulturing.equalsIgnoreCase("False")) {
			Result63="Passed : Verified the Culturing field in Scope Safety List page is by default unchecked when new facility is created";
		}
		else{
			Result63="#Failed!# - In Scope Safety List page the Culturing field is checked whereas it should be actually unchecked";
		}
		FinalResult =  FinalResult + "\r\n Result63 : " +Result63;
		System.out.println ("Result63 ="+Result63);
		ScopeSafety_DefScopeHangTime =SSA.Default_ScopeHangTime_Value();
		if (ScopeSafety_DefScopeHangTime.equalsIgnoreCase("7")) {
			Result64="Passed : Verified the default value of Hang Time in Scope Safety List page  is '7'";	
		}
		else{
			System.out.println ("#Failed!# -In Scope Safety List page the Scope Hang Time value is by default '"+ScopeSafety_DefScopeHangTime+"' days whereas it should be actually '7' days ");
		}
		FinalResult =  FinalResult + "\r\n Result64 : " +Result64;
		System.out.println ("Result64 ="+Result64);
		//Deactivate a facility and verify it should not be found in scope safety list page

		LPA.Click_Admin_Menu("Facility");
		FPA.Search_Facility_ByName("Default Values Facility");
		GridID=FPA.GetGridID_Facility_To_Modify("Default Values Facility");
		ModFacAct_Val=FPA.Facility_Active_Value("Default Values Facility");		
		FPA.Select_Facility_To_Modify("Default Values Facility");
		FPA.Modify_Facility_Active(GridID,ModFacAct_Val,"False");
		FPA.Save_Facility_Edit();
		//Navigate to Scope Safety screen and verify the inactive facility is not found
		LPA.Click_Admin_Menu("ScopeSafety");
		//Search for newly created facility in the Scope Safety screen
		SSA.Search_FacilityName("Default Values Facility");
		//Verify the default values for the newly create facility
		String Result_FacilityExists=SSV.Verify_ScopeSafety_Facilty();
		if (Result_FacilityExists.equalsIgnoreCase("No records to view")) {
			Result65="Passed : Verified the inactive facility is not displayed in Scope Safety List page";
		}
		else{
			Result65="#Failed!# - The Inactive facility 'Default Values Facility' is dispalyed on 'Scope Safety List' page whereas actually it should not be displayed";
		}
		FinalResult =  FinalResult + "\r\n Result65 : " +Result65;
		System.out.println ("Result65 ="+Result65);
		//End - RK 28Jan16 Adding a new facility to verify default values in scope safety screen when a facility is created

	  	//NR 11may15 added to verify users can be associated to multiple facilities (more than 3)
		LPA.Click_Admin_Menu("Facility");  
		FPA.Add_New_Facility();		
		FPA.Enter_New_Facility_Name("User Facility 2");
		FPA.Enter_New_Facility_Abbreviation("User2");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("55555");
		//FPA.Enter_New_Facility_HangTime("7");
		FPA.Save_Facility_Edit();
		
		FPA.Add_New_Facility();		
		FPA.Enter_New_Facility_Name("User Facility 3");
		FPA.Enter_New_Facility_Abbreviation("User3");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("66666");
		//FPA.Enter_New_Facility_HangTime("7");
		FPA.Save_Facility_Edit();
		
		FPA.Add_New_Facility();		
		FPA.Enter_New_Facility_Name("User Facility 4");
		FPA.Enter_New_Facility_Abbreviation("User4");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("77777");
		//FPA.Enter_New_Facility_HangTime("7");
		FPA.Save_Facility_Edit();
		
		FPA.Add_New_Facility();		
		FPA.Enter_New_Facility_Name("User Facility 5");
		FPA.Enter_New_Facility_Abbreviation("User5");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("88888");
		//FPA.Enter_New_Facility_HangTime("7");
		FPA.Save_Facility_Edit();
		
		//[RK - 25Feb2016] - Added the new Facility for Reconciliation
		LPA.Click_Admin_Menu("Facility");  
		FPA.Add_New_Facility();		
		FPA.Enter_New_Facility_Name("User Facility 6");
		FPA.Enter_New_Facility_Abbreviation("User6");
		FPA.Selct_New_Facility_Active("True");
		FPA.Enter_New_Facility_USSN("99999");
		FPA.Save_Facility_Edit();
	

		
		LPA.Click_Admin_Menu("User");
		UA.Search_User_ByName("qvtest01");
		GridID=UA.GetGridID_User_To_Modify("qvtest01");
		UA.Select_User_To_Modify("qvtest01");
		UA.Select_UserFacility("User2");
		UA.Select_UserFacility("User3");
		UA.Select_UserFacility("User4");
		UA.Select_UserFacility("User5");
		UA.Select_UserFacility("User6");//nm 13oct2016 updated role names 
		UA.Select_User_Role("Staff");
		UA.Save_User_Edit();
		Result=GF.Verify_ErrCode("0");
		if(!(Result.contains("Fail")))
		{
			Result53="Passed : Error code 0 verified(add more facilities and role associations)";
		} else
		{
			Result53="#Failed!# : Error code 0 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result53 : " +Result53;
	  	System.out.println("Result53: " + Result53);
	  	
	  	//NR 11may15 verify the users and facilities were selected. 
	  	//nm 13oct2016 updated role names being verified 
		UA.Select_User_To_Modify("qvtest01");
		ResultRole1=UV.Verify_Role_Selection("Staff"); 

		FinalResult =  FinalResult + "\r\n ResultRole1 : " +ResultRole1;
		System.out.println("ResultRole1 ="+ResultRole1);	

		//[RK-26Feb16] - Added for ResultRole6
		//NM 14oct2016 - updated only one role can be selected at a time. 
		if(ResultRole1.contains("Pass")){
			Result54="Passed : verified that roles were added after clicking save."; //All roles are set correctly
		} else{
			Result54="#Failed!# - The roles were not saved correctly."; 
		}
		FinalResult =  FinalResult + "\r\n Result54 : " +Result54;
		System.out.println("Result54 ="+Result54);	
		
		Facility_Val1=UA.Facility_Value("User2"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val1==1){
			ResultFacility1="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility1="#Failed!# - User2 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility1 : " +ResultFacility1;
		System.out.println("ResultFacility1 ="+ResultFacility1);	
		
		Facility_Val2=UA.Facility_Value("User3"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val2==1){
			ResultFacility2="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility2="#Failed!# - User3 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility2 : " +ResultFacility2;
		System.out.println("ResultFacility2 ="+ResultFacility2);	
		
		Facility_Val3=UA.Facility_Value("User4"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val3==1){
			ResultFacility3="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility3="#Failed!# - User4 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility3 : " +ResultFacility3;
		System.out.println("ResultFacility3 ="+ResultFacility3);	
		
		Facility_Val4=UA.Facility_Value("User5"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val4==1){
			ResultFacility4="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility4="#Failed!# - User5 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility4 : " +ResultFacility4;
		System.out.println("ResultFacility4 ="+ResultFacility4);
		
		Facility_Val5=UA.Facility_Value("User6"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val5==1){
			ResultFacility5="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility5="#Failed!# - User6 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility5 : " +ResultFacility5;
		System.out.println("ResultFacility5 ="+ResultFacility5);
		
		if(ResultFacility1.contains("Pass")&& ResultFacility2.contains("Pass")&& ResultFacility3.contains("Pass")&& ResultFacility4.contains("Pass")&& ResultFacility5.contains("Pass")){
			Result55="Passed : Verified that facilities were added after clicking save."; //All facilities are set correctly
		} else{
			Result55="#Failed!# - The Facilities were not saved correctly."; //FacilitySaved
		}
		FinalResult =  FinalResult + "\r\n Result55 : " +Result55;
		System.out.println("Result55 ="+Result55);
	  	//NR 11may15 added to remove user selections.
		// NM 14oct2016 only one role can be selected at a time. update to select the Admin role
		UA.Select_User_Role("Admin");
		//UA.Select_UserRole("Data Analyst");
		UA.Select_UserFacility("User2");
		UA.Select_UserFacility("User3");
		UA.Select_UserFacility("User5");
		UA.Select_UserFacility("User6");
		UA.Save_User_Edit();
		Result56=GF.Verify_ErrCode("0");
		if(!(Result.contains("Fail")))
		{
			Result56="Passed : verified Error code 0";
		} else
		{
			Result56="#Failed!# : Error code 0 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result56 : " +Result56;
	  	System.out.println("Result56: " + Result56);
	  	
		UA.Select_User_To_Modify("qvtest01");
		//Verify the user's roles and facilities are selected (or not selected) correctly.

		ResultRole1=UV.Verify_Role_Selection("Admin");

		FinalResult =  FinalResult + "\r\n ResultRole1 : " +ResultRole1;
		System.out.println("ResultRole1 ="+ResultRole1);
		

		if(ResultRole1.contains("Pass")){ 
			Result57="Passed : verified that the roles for the user were saved correctly."; //All roles are set correctly
		} else{
			Result57="#Failed!# - The roles were not saved correctly."; 
		}
		FinalResult =  FinalResult + "\r\n Result57 : " +Result57;
		System.out.println("Result57 ="+Result57);	
		
		Facility_Val5=UA.Facility_Value("User"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val5==1){
			ResultFacility5="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility5="#Failed!# - User should not be selected but it is. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility5 : " +ResultFacility5;
		System.out.println("ResultFacility1 ="+ResultFacility1);	
		Facility_Val1=UA.Facility_Value("User2"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val1==0){
			ResultFacility1="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility1="#Failed!# - User2 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility1 : " +ResultFacility1;
		System.out.println("ResultFacility1 ="+ResultFacility1);	
		
		Facility_Val2=UA.Facility_Value("User3"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val2==0){
			ResultFacility2="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility2="#Failed!# - User3 should not be selected but it is. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility2 : " +ResultFacility2;
		System.out.println("ResultFacility2 ="+ResultFacility2);	
		
		Facility_Val3=UA.Facility_Value("User4"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val3==1){
			ResultFacility3="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility3="#Failed!# - User4 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility3 : " +ResultFacility3;
		System.out.println("ResultFacility3 ="+ResultFacility3);	
		
		Facility_Val4=UA.Facility_Value("User5"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val4==0){
			ResultFacility4="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility4="#Failed!# - User5 should not be selected but it is. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility4 : " +ResultFacility4;
		System.out.println("ResultFacility4 ="+ResultFacility4);
		
		if(ResultFacility1.contains("Pass")&& ResultFacility2.contains("Pass")&& ResultFacility3.contains("Pass")&& ResultFacility4.contains("Pass")){
			Result58="Passed : verified that the facilities for the user were saved correctly."; //All facilities are set correctly
		} else{
			Result58="#Failed!# - The Facilities were not saved correctly."; //FacilitySaved
		}
		FinalResult =  FinalResult + "\r\n Result58 : " +Result58;
		System.out.println("Result58 ="+Result58);
		
		UA.Select_User_Role("Manager/Supervisor");
		UA.Select_UserFacility("User2");
		UA.Select_UserFacility("User3");
		UA.Select_UserFacility("User4");
		UA.Select_UserFacility("User5");
		UA.Select_UserFacility("User6");

		UA.Save_User_Edit();
		Result59=GF.Verify_ErrCode("0");
		if(!(Result.contains("Fail")))
		{
			Result59=" Passed : Error code 0 verified";
		} else
		{
			Result59="#Failed!# : Error code 0 not Verified \n" +Result+" .";
		}
		FinalResult =  FinalResult + "\r\n Result59 : " +Result59;
	  	System.out.println("Result59" + Result59);
	  	
		UA.Select_User_To_Modify("qvtest01");
		//Verify the user's roles and facilities are selected (or not selected) correctly.

		ResultRole1=UV.Verify_Role_Selection("Manager/Supervisor");

		if(ResultRole1.contains("Pass")){
			Result60="Passed : verified that the roles for the user were saved correctly."; //All roles are set correctly
		} else{
			Result60="#Failed!# - The roles were not saved correctly."; 
		}
		FinalResult =  FinalResult + "\r\n Result60 : " +Result60;
		System.out.println("Result60 ="+Result60);	
		
		Facility_Val5=UA.Facility_Value("YFN"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val5==1){
			ResultFacility5="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility5="#Failed!# - YFN should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility5 : " +ResultFacility5;
		System.out.println("ResultFacility1 ="+ResultFacility1);	
		Facility_Val1=UA.Facility_Value("User2"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val1==1){
			ResultFacility1="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility1="#Failed!# - User2 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility1 : " +ResultFacility1;
		System.out.println("ResultFacility1 ="+ResultFacility1);	
		
		Facility_Val2=UA.Facility_Value("User3"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val2==1){
			ResultFacility2="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility2="#Failed!# - User3 should not be selected but it is. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility2 : " +ResultFacility2;
		System.out.println("ResultFacility2 ="+ResultFacility2);	
		
		Facility_Val3=UA.Facility_Value("User4"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val3==0){
			ResultFacility3="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility3="#Failed!# - User4 should be selected but it is not. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility3 : " +ResultFacility3;
		System.out.println("ResultFacility3 ="+ResultFacility3);	
		
		Facility_Val4=UA.Facility_Value("User5"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val4==1){
			ResultFacility4="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility4="#Failed!# - User5 should not be selected but it is. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility4 : " +ResultFacility4;
		System.out.println("ResultFacility4 ="+ResultFacility4);
		
		Facility_Val6=UA.Facility_Value("User6"); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val6==1){
			ResultFacility6="Pass : verified that the data is saved correctly";
		} else {
			ResultFacility6="#Failed!# - User6 should not be selected but it is. ";
		}
		FinalResult =  FinalResult + "\r\n ResultFacility6 : " +ResultFacility6;
		System.out.println("ResultFacility6 ="+ResultFacility6);
		
		if(ResultFacility1.contains("Pass")&& ResultFacility2.contains("Pass")&& ResultFacility3.contains("Pass")&& ResultFacility4.contains("Pass")&& ResultFacility6.contains("Pass")){
			Result61="Passed : verified that the facilities for the user were saved correctly."; //All facilities are set correctly
		} else{
			Result61="#Failed!# - The Facilities were not saved correctly."; //FacilitySaved
		}
		FinalResult =  FinalResult + "\r\n Result61 : " +Result61;
		System.out.println("Result61 ="+Result61);
		UA.Cancel_User_Edit();
		
		//RK-26Jan2016 - Creating new staff and user for assigning  Manager/Supervisor role to verify Reconciliation tab
		LPA.Click_Admin_Menu("Staff");
		STAFFA.Add_New_Staff();
		STAFFA.Selct_New_Title("Mrs.");
		STAFFA.Enter_New_StaffID("ComplexIDNM1");
		STAFFA.Enter_New_Staff_FirstName("James");
		STAFFA.Enter_New_Staff_LastName("Cook");
		STAFFA.Selct_New_Staff_Type("Nurse");
		STAFFA.Save_Staff_Edit();
		
		LPA.Click_Admin_Menu("User");
		UA.ClearAllSearchCriteria(); //Clear all search criteria
		UA.Search_User_ByName("qvtest02"); //Search for the user name to be modified.
		GridID=UA.GetGridID_User_To_Modify("qvtest02"); //Get the grid id for the user name to be modified.
		UA.Select_User_To_Modify("qvtest02"); //Select the user name to be modified.


		UA.Select_User_Staff("Cook, James");
		UA.Select_User_Role("Manager/Supervisor");
		UA.Save_User_Edit();
		
		LPA.Click_Logout();
		Thread.sleep(6000);
		//LGPA.Launch_Unifia(Unifia_Admin_Selenium.url);
//		LGPA.Logon_Username("qvtest02"); 
	//	LGPA.Logon_Password("0lympu$");
		//LGPA.Click_Submit();
	//	Result69=LPV.Recon_Landing_Pg_Verf();
	//	if(Result69=="Pass"){
	//		Result69="Passed : Verified that the Reconciliation tab is displayed for the user having Manager/Supervisor Role";
	//	} else{
//			Result69="#Failed!# - The Reconciliation tab is not displayed for user having only Manager/Supervisor Role."; 
//		}
//		FinalResult =  FinalResult + "\r\n Result69 : " +Result69;
//		System.out.println("Result69 ="+Result69);
		//------------------------------------------------------
		//The final decision on whether test passed as a whole
		if(!Result1.contains("Pass")){
			Result18="#Failed!#- See details for Result1 "+Result1;
		}else if(!Result2.contains("Pass")){
			Result18="#Failed!#- See details for Result2 "+Result2;
		}else if(!Result3.contains("Pass")){
			Result18="#Failed!#- See details for Result3 "+Result3;
		}else if(!Result4.contains("Pass")){
			Result18="#Failed!#- See details for Result4 "+Result4;
		}else if(!Result5.contains("Pass")){
			Result18="#Failed!#- See details for Result5 "+Result5;
		}else if(!Result6.contains("Pass")){
			Result18="#Failed!#- See details for Result6 "+Result6;
		}else if(!Result7.contains("Pass")){
			Result18="#Failed!#- See details for Result7 "+Result7;
		}else if(!Result8.contains("Pass")){
			Result18="#Failed!#- See details for Result8 "+Result8;
		}else if(!Result9.contains("Pass")){
			Result18="#Failed!#- See details for Result9 "+Result9;
		}else if(!Result10.contains("Pass")){
			Result18="#Failed!#- See details for Result10 "+Result10;
		}else if(!Result11.contains("Pass")){
			Result18="#Failed!#- See details for Result11 "+Result11;
		}else if(!Result.contains("Pass")){
			Result18="#Failed!#- See details for Result "+Result;
		}else if(!Result12.contains("Pass")){
			Result18="#Failed!#- See details for Result12 "+Result12;
		}else if(!Result13.contains("Pass")){
			Result18="#Failed!#- See details for Result13 "+Result13;
		}else if(!Result14.contains("Pass")){
			Result18="#Failed!#- See details for Result14 "+Result14;
		}else if(!Result15.contains("Pass")){
			Result18="#Failed!#- See details for Result15 "+Result15;
		}else if(!Result16.contains("Pass")){
			Result18="#Failed!#- See details for Result16 "+Result16;
		}else if(!Result17.contains("Pass")){
			Result18="#Failed!#- See details for Result17 "+Result17;
		}else if(!Result22.contains("Pass")){
			Result18="#Failed!#- See details for Result22 "+Result22;
		}else if(!Result23.contains("Pass")){
			Result18="#Failed!#- See details for Result23 "+Result23;
		}else if(!Result24.contains("Pass")){
			Result18="#Failed!#- See details for Result24 "+Result24;
		}else if(!Result25.contains("Pass")){
			Result18="#Failed!#- See details for Result25 "+Result25;
		}else if(!Result26.contains("Pass")){
			Result18="#Failed!#- See details for Result26 "+Result26;
		} else if(!Result27.contains("Pass")){
			Result18="#Failed!#- See details for Result27 "+Result27;
		} else if(!Result28.contains("Pass")){
			Result18="#Failed!#- See details for Result28 "+Result28;
		} else if(!Result29.contains("Pass")){
			Result18="#Failed!#- See details for Result29 "+Result29;
		} else if(!Result30.contains("Pass")){
			Result18="#Failed!#- See details for Result30 "+Result30;
		} else if(!Result31.contains("Pass")){
			Result18="#Failed!#- See details for Result31 "+Result31;
		} else if(!Result32.contains("Pass")){
			Result18="#Failed!#- See details for Result32 "+Result32;
		} else if(!Result33.contains("Pass")){
			Result18="#Failed!#- See details for Result33 "+Result33;
		}else if(!Result34.contains("Pass")){
			Result18="#Failed!#- See details for Result34 "+Result34;			
		} else if(!Result38.contains("Pass")){
		    Result18="#Failed!#- See details for Result38 "+Result38;
		}else if(!Result39.contains("Pass")){
		    Result18="#Failed!#- See details for Result39 "+Result39;
		}else if(!Result40.contains("Pass")){
		    Result18="#Failed!#- See details for Result40 "+Result40;
		}else if(!Result41.contains("Pass")){
		    Result18="#Failed!#- See details for Result41 "+Result41;
		}else if(!Result42.contains("Pass")){
		    Result18="#Failed!#- See details for Result42 "+Result42;
		}else if(!Result43.contains("Pass")){
		    Result18="#Failed!#- See details for Result43 "+Result43;
		}else if(!Result44.contains("Pass")){
		    Result18="#Failed!#- See details for Result44 "+Result44;
		}else if(!Result45.contains("Pass")){
		    Result18="#Failed!#- See details for Result45 "+Result45;
		}else if(!Result46.contains("Pass")){
		    Result18="#Failed!#- See details for Result46 "+Result46;   
		}else if(!Result48.contains("Pass")){
		    Result18="#Failed!#- See details for Result48 "+Result48;  
		}else if(!Result53.contains("Pass")){
		    Result18="#Failed!#- See details for Result53 "+Result53;  
		}else if(!Result54.contains("Pass")){
		    Result18="#Failed!#- See details for Result54 "+Result54;   
		}else if(!Result55.contains("Pass")){
		    Result18="#Failed!#- See details for Result55 "+Result55;   
		}else if(!Result56.contains("Pass")){
		    Result18="#Failed!#- See details for Result56 "+Result56;  
		}else if(!Result57.contains("Pass")){
		    Result18="#Failed!#- See details for Result57 "+Result57;   
		}else if(!Result58.contains("Pass")){
		    Result18="#Failed!#- See details for Result58 "+Result58;    
		}else if(!Result59.contains("Pass")){
		    Result18="#Failed!#- See details for Result59 "+Result59;    
		}else if(!Result60.contains("Pass")){
		    Result18="#Failed!#- See details for Result60 "+Result60;    
		}else if(!Result61.contains("Pass")){
		    Result18="#Failed!#- See details for Result61 "+Result61;    
		}else if(!Result62.contains("Pass")){
		    Result18="#Failed!#- See details for Result62 "+Result62; 
		}else if(!Result63.contains("Pass")){
			Result18="#Failed!#- See details for Result63 "+Result63;	    
		}else if(!Result64.contains("Pass")){
			Result18="#Failed!#- See details for Result64 "+Result64;		    
		}else if(!Result65.contains("Pass")){
			Result18="#Failed!#- See details for Result65 "+Result65;	    
		}else if(!Result66.contains("Pass")){
		    Result18="#Failed!#- See details for Result66 "+Result66;    
		}else if(!Result67.contains("Pass")){
		    Result18="#Failed!#- See details for Result67 "+Result67;    
		}else if(!Result68.contains("Pass")){
		    Result18="#Failed!#- See details for Result68 "+Result68;   
		/*}else if(!Result69.contains("Pass")){
		    Result18="#Failed!#- See details for Result69 "+Result69;   */
		}else if(!Result71.contains("Pass")){
		    Result18="#Failed!#- See details for Result71 "+Result71;   
		}else{
			Result18="Result18 Test Passed";
		}
		FinalResult =  FinalResult + "\r\n Result18 : " +Result18;
		System.out.println("Overall Test status is "+Result18);
		
		try{ 
			  // Create file 
			  FileWriter fstream = new FileWriter("Complex Admin Script TestSummary.txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(FinalResult);
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
				  System.err.println("Error: " + e.getMessage());
			  }
		
		if (FinalResult.contains("#Failed!#")){
	 		org.testng.Assert.fail("Test has failed");
	 	}
		
		LP_A.CloseDriver();
	}
	
	@AfterTest
	public void PostTest() throws IOException{
		try{
			//conn= DriverManager.getConnection(url, user, pass);		
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement updateET= conn.createStatement();
			Statement updateST= conn.createStatement();
			updateET.executeUpdate("Update ExamType Set IsActive=1 Where IsShipped=1;");
			updateET.close();
			updateST.executeUpdate("Update ScopeType Set IsActive=1 Where IsShipped=1;");
			updateST.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		  
		LP_A.CloseDriver();
	}
	
}
