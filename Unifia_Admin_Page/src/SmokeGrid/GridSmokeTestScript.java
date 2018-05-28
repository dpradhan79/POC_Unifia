package SmokeGrid;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

//import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import TestFrameWork.UnifiaAdminLoginPage.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminFacilityPage.*;
import TestFrameWork.UnifiaAdminLocationPage.*;
import TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification;
import TestFrameWork.UnifiaAdminScannerPage.*;
import TestFrameWork.UnifiaAdminAccessPointPage.*;
import TestFrameWork.UnifiaAdminStaffPage.*;
import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.QlikView.*;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Unifia_Admin_Selenium_Grid;

/**
 * Smoke Test Script - Verifies all Smoke test Scenarios to ensure new build is acceptable
The smoke test is not a detailed test but just touches upon key functionalities and would ideally be run 
after a new build is deployed
It would take about 10 minutes to complete which includes reloading the data via QMC.
 * 
 * 
Step 1- Clear current test data
Step 2- reset ship data
Step 3- Logon the admin desktop, verify the admin menu -- done
Step 4- Verify the Facility is set to My Facility--done
Step 5- Setup Access point-
step 6- Setup locations
Step 7- Setup Scanners
Step 8- Setup Staff
Step 9- Logout of admin
Step 10-access dashboard
step 11-Verify the dashboard matches the locations that where setup
step 12-Load some historical data
Step 13-Verify the IP page has the historical data displayed (simple verification)
Step 14-Load small sample of recon data. (Can use data from Step 12, 13) Verify its available on recon screens and make modifications. 
(Modify the patient and physician on the Procedure Room)*
*/
 
public class GridSmokeTestScript {
	
	
	/*public static String Env="te-fact-03.mitlab.com"; //the environment used for testing. 
	public String Admin_URL="https://"+Env+":9753/Default.cshtml"; //the Admin URL for the environment being tested
	public String QV_URL="https://"+Env+":9753/dashboard.cshtml"; //the QlikView URL for the environment being tested
	public static String url = "jdbc:sqlserver://"+Env+"\\sqlexpress;databaseName=Unifia"; //the database of the environment being tested. 
	public String QMC_URL="https://"+Env+":4780/QMC/default.htm";
	public static String user = "unifia";
    public static String pass = "0lympu$123";*/
    public boolean blnStatus;
    public String GridID;
    public long cal = Calendar.getInstance().getTimeInMillis(); 
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
    Connection conn= null;
    public String Green="rgba(0, 162, 55, 1)";
   	public String Purple="rgba(127, 0, 255, 1)";
   	public String Orange="rgba(251, 147, 22, 1)";
   	public String Black="rgba(40, 40, 40, 1)";
   	public String Red="rgba(208, 55, 37, 1)";
    
    public String FinalResult="------------------Smoke Test Script TestSummary-----------------------";
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
    
    Login_Actions LGPA;
    LandingPage_Actions LPA;
    GeneralFunc GF;
    Facility_Actions FPA;
    Facility_Verification FPV;
    Location_Actions LOCPA;
    Scanner_Actions SCNPA;
    AccessPoint_Actions APA;
    Staff_Actions STAFFA;
    LandingPage_Verification LPV;
    Login_Verification LV;
    QV_GeneralFunc QV_Gen;
    Dashboard_Verification DV;
    ReconcilationAuditLog_Verification ALV;
    
    //Grid
   public String browserType=null;
   public String URL=null;
   public long id;
   public Alert alert;
   public UserAndPassword user;
    //Grid
    
	/*Result Detail
	
	*********Step 3*********
	Result1 :  Verify whether 'Admin' Tab is displayed in the application Home page
	*********Step 4*********
	Result2 :  Verify the Facility is set to My Facility
	*********Step 5*********
	Result3 :  Verify if the user is able to create 'Access Point' 
	*********Step 6*********
	Result4 :  Verify if the user is able to create 'Location - New_Procedure_Room' 
	Result5 :  Verify if the user is able to create 'Location - New_Scope_Storage_Area' 
	*********Step 7*********
	Result6 :  Verify if the user is able to create 'Scanner - Scanner1' 
	Result7 :  Verify if the user is able to create 'Scanner - Scanner2' 
	*********Step 8*********
	Result8 :  Verify if the user is able to create 'Staff - Jane Thomas' 
	*********Step 9*********
	Result9 : Verify if the user is able to log out of Admin
	*********Step 10*********
	Result10 : Verify if the Dashboard page is displayed
	*********Step 11*********
	Result11 :  Verify if the 'Location - Storage Area A-1' is displayed in dashboard page.
	Result12 :  Verify if the 'Location - Storage Area A-2' is displayed in dashboard page.
	Result13 :  Verify if the 'Location - Storage Area A-31' is displayed in dashboard page.
	Result14 :  Verify if the 'Location - Procedure Room 1' is displayed in dashboard page.
	Result15 :  Verify if the 'Location - Procedure Room 2' is displayed in dashboard page.
	Result16 :  Verify if the 'Location - Procedure Room 3' is displayed in dashboard page.
	*********Step 13*********
	Result17 :  Verify if the Data is displayed in the 'Scope usage and preclean' section of IP page
	*********Step 14*********
	Result18 : Verify if the Data is displayed for MRC Screen
	Result19 : Verify if the Data is displayed for Soiled Area Screen
	Result20 : Verify if the Data is displayed for Procedure Room Screen
	Result21 : Verify if the Data is displayed for Reprocessing Area Screen
	Result22 : Verify if the Data is displayed for Bioburden Testing Screen
	Result23 : Verify if the row is inserted in the Audit Log screen when Physician ID is changed for Procedure Room.
	Result24 : Verify if the row is inserted in the Audit Log screen when Patient ID is changed for Procedure Room. 
	*/
  
    @BeforeTest     
    @Parameters({ "browser","url"})
   	public void PreTest(String browserT,String url) throws MalformedURLException {
       	browserType=browserT;
   		URL=url;
   		id = Thread.currentThread().getId();
   		System.out.println("Before Test setUp. Thread id is: " + id+";Browser: "+browserType+";URL: "+URL);
   		Unifia_Admin_Selenium_Grid.setGridDriver(browserT,url);		  			   
   		
	try{		
		 
		
		//Step 1- Clear current test data
		//Step 2- reset ship data
		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user,Unifia_Admin_Selenium.pass);
		String stmt;
		stmt = "delete from KeyEntryScans;delete from ReconciliationActivityLogValue;delete from Barcode where IsShipped=0;delete from ReconciliationActivityLog;"
				+ "delete from ReconciliationReportComment;DELETE FROM ItemHistory;delete from ScopeCycle;DELETE FROM ScopeStatus;DELETE FROM Association;"
				+ "DELETE FROM LocationStatus;DELETE FROM Patient;delete from Scanner;delete from Scope;delete from ExamType where IsShipped=0;"
				+ "delete from User_Facility_Assoc where UserID_PK>2;delete from User_Role_Assoc where UserID_PK>2;delete from [User] where UserID_PK > 2;"
				+ "delete from ScopeType where IsShipped=0;delete from AERDetail;delete from Location;delete from AccessPoint;delete from Staff;"
				+ "delete from Facility where FacilityID_PK > 1;delete from Role_Task_Assoc where RoleID_PK>7;delete from Role where RoleID_PK>7;"
				+ "update ExamType set IsActive=0 where ExamTypeID_PK=13;"
				+ "update Facility set Name='Your Facility Name', Abbreviation='YFN', IsBioburdenTestingPerformed=1 where FacilityID_PK=1;";
		
		System.out.println("stmt="+stmt);
		Statement update1 = conn.createStatement();
		update1.executeUpdate(stmt);
		conn.close();
		FinalResult =  FinalResult + "\r\n Reset ShipData";
		FinalResult=FinalResult+"\r\n -------------------Step 1 & 2 - done----------";
	}
	catch (SQLException ex){
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());	
	}

}
    @AfterTest
    public void afterTest(){
    	System.out.println("After Test:"+";Browser: "+browserType+";URL: "+URL);
    	Unifia_Admin_Selenium.driver.quit();
    }
	
	@Test
	public void Test() throws Exception{
		
		/**
		 *!!The assumption for this test is that before test runs and that all non-shipped data has been delete and 2 Facilities are added
		 *!!The two facilities are "Your Facility Name" and "Inactive_Facility", only "Your Facility Name" is active
		 * The plan for this test is to login and verify that the facility "Your Facility Name"  will be displayed in the Location Facility field.
		 * Result 18:  Final Test Result
		 * 
		 */
		
		
		//Step 3-  Logon the admin desktop, verify the admin menu		
		//The user logs onto the application
		//LGPA.Launch_UnifiaRecon(Admin_URL);
		//LGPA.Launch_UnifiaRecon(Unifia_Admin_Selenium.Admin_URL);
		Unifia_Admin_Selenium.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Unifia_Admin_Selenium.driver.get(Unifia_Admin_Selenium.Admin_URL);
		LGPA.Logon_Username("admin"); 
		LGPA.Logon_Password("admin");
		LGPA.Click_Submit();
		
		
		//Verify Admin Tab exists
		String Login=LPV.Admin_Landing_Pg_Verf();
		if (Login.equalsIgnoreCase("Pass")){
			Result1="Passed: 'Admin' Tab is displayed in the application Home page.";
		}else{
			Result1="Failed: 'Admin' Tab is not displayed in the application Home page.";
		}
		FinalResult =  FinalResult + "\r\n Result1="+Result1;
		FinalResult=FinalResult+"\r\n -------------------Step 3 - done----------";
		
		//Step 4-  Verify the Facility is set to My Facility
		
		String myFacility="Your Facility Name";
		String AbbreviationValue="YFN";
		
		FPA.Search_Facility_ByName(myFacility);
		System.out.println("Search facility by name");
		GridID=FPA.GetGridID_Facility_To_Modify(myFacility);
		
		String ResultFacilityActivee=FPA.Facility_Active_Value(myFacility); 
		FPA.Select_Facility_To_Modify(myFacility);
		String ResultAbbv=FPV.Verify_Abbreviation(GridID,AbbreviationValue);
		if (!GridID.equals(null) && ResultFacilityActivee.equalsIgnoreCase("true")&& ResultAbbv.equalsIgnoreCase("Pass")){
			Result2="Passed: 'Your Facility Name' is available in the application and it is active";
		}else{
			Result2="Failed: 'Your Facility Name' is not available in the application and/or it is not active";
		}
		FinalResult =  FinalResult + "\r\n Result2="+Result2;
		FinalResult=FinalResult+"\r\n -------------------Step 4 - done----------";
		
		
		//Step 5-  Setup Access point
		
		LPA.Click_Admin_Menu("AccessPoint");
		APA.Add_New_AccessPoint();
		calint++;
		calchk=String.valueOf(calint);
		String SSID="SSID"+cal+calchk; //Create a unique SSID by converting the current time to an integer.
		APA.Enter_SSID_New(SSID); 
		calint++;
		calchk=String.valueOf(calint);
		String Password="Psw"+cal+calchk; 
		APA.Enter_Password_New(Password);
		APA.Selct_New_AccessPointStatus("True");
		APA.Save_AccessPoint_Edit();
		
		APA.Search_SSID(SSID);
		GridID=APA.GetGridID_AccessPoint_To_Modify(SSID);
		System.out.println("GridID in e_Modify function is: "+GridID); 
		if (!GridID.equals(null)){
			Result3="Passed: 'Access Point' is created successfully.";
		}else{
			Result3="Failed: Failed to create' Access Point.";
		}
		FinalResult =  FinalResult + "\r\n Result3="+Result3;
		FinalResult=FinalResult+"\r\n -------------------Step 5 - done----------";
		
		//Step 6-  Setup locations
		
		LPA.Click_Admin_Menu("Location");
		LOCPA.Add_New_Location();
		LOCPA.Enter_Location_Name("New_Procedure_Room");
		LOCPA.Selct_Location_SSID(SSID);
		LOCPA.Selct_Location_Type("Procedure Room");
		LOCPA.Selct_New_Location_Active("True");
		LOCPA.Save_Location_Edit();
		
		LOCPA.Search_Location_ByName("New_Procedure_Room");  
		GridID=LOCPA.GetGridID_Location_To_Modify("New_Procedure_Room");
		System.out.println("GridID = "+GridID);
		if (!GridID.equals(null)){
			Result4="Passed: 'Location - New_Procedure_Room' is created successfully.";
		}else{
			Result4="Failed: Failed to create Location - New_Procedure_Room.";
		}
		FinalResult =  FinalResult + "\r\n Result4="+Result4;
		
		LOCPA.Add_New_Location();
		LOCPA.Enter_Location_Name("New_Scope_Storage_Area");
		LOCPA.Selct_Location_SSID(SSID);
		LOCPA.Selct_Location_Type("Scope Storage Area");
		LOCPA.Selct_New_Location_Active("True");
		LOCPA.Enter_StorageCabinets("1");
		LOCPA.Save_Location_Edit();
		
		LOCPA.Search_Location_ByName("New_Scope_Storage_Area"); 
		GridID=LOCPA.GetGridID_Location_To_Modify("New_Scope_Storage_Area");
		System.out.println("GridID = "+GridID);
		if (!GridID.equals(null)){
			Result5="Passed: 'Location - New_Scope_Storage_Area' is created successfully.";
		}else{
			Result5="Failed: Failed to create Location - New_Scope_Storage_Area.";
		}
		FinalResult =  FinalResult + "\r\n Result5="+Result5;
		FinalResult=FinalResult+"\r\n -------------------Step 6 - done----------";
		
		//Step 7- Setup Scanners
		
		LPA.Click_Admin_Menu("Scanner");
		SCNPA.Add_New_Scanner(); 
		SCNPA.Enter_Location_New("New_Procedure_Room (YFN)");
		SCNPA.Enter_ScanID_New("RFSCID1");
		SCNPA.Enter_ScanName_New("Scanner1");
		SCNPA.Save_Scanner_Edit();
		
		SCNPA.Search_ScanID("RFSCID1");
		GridID=SCNPA.GetGridID_Scanner_To_Modify("RFSCID1");
		if (!GridID.equals(null)){
			Result6="Passed: 'Scanner - Scanner1' is created successfully.";
		}else{
			Result6="Failed: Failed to create Scanner - Scanner1.";
		}
		FinalResult =  FinalResult + "\r\n Result6="+Result6;
		
		SCNPA.Add_New_Scanner(); 
		SCNPA.Enter_Location_New("New_Scope_Storage_Area (YFN)");
		SCNPA.Enter_ScanID_New("RFSCID2");
		SCNPA.Enter_ScanName_New("Scanner2");
		SCNPA.Save_Scanner_Edit();
		
		SCNPA.Search_ScanID("RFSCID2");
		GridID=SCNPA.GetGridID_Scanner_To_Modify("RFSCID2");
		if (!GridID.equals(null)){
			Result7="Passed: 'Scanner - Scanner2' is created successfully.";
		}else{
			Result7="Failed: Failed to create Scanner - Scanner2.";
		}
		FinalResult =  FinalResult + "\r\n Result7="+Result7;
		FinalResult=FinalResult+"\r\n -------------------Step 7 - done----------";
		
		//Step 8- Setup Staff
		
		LPA.Click_Admin_Menu("Staff");
		STAFFA.Add_New_Staff();
		STAFFA.Selct_New_Title("Dr.");
		STAFFA.Enter_New_StaffID("SmokeID1");
		STAFFA.Enter_New_Staff_FirstName("Jane");
		STAFFA.Enter_New_Staff_LastName("Thomas");
		STAFFA.Selct_New_Staff_Type("Physician");
		STAFFA.Save_Staff_Edit();
		
		STAFFA.Search_Staff_ByLastName("Thomas");
		STAFFA.Search_Staff_ByFirstName("Jane");
		STAFFA.Search_Staff_ByStaffID("SmokeID1");
		GridID=STAFFA.GetGridID_Staff_To_Modify("SmokeID1");
		
		if (!GridID.equals(null)){
			Result8="Passed: 'Staff - Jane Thomas' is created successfully.";
		}else{
			Result8="Failed: Failed to create Staff - Jane Thomas.";
		}
		FinalResult =  FinalResult + "\r\n Result8="+Result8;
		FinalResult=FinalResult+"\r\n -------------------Step 8 - done----------";
		
		//Step 9-  Logout of admin
		
		LPA.Click_Logout();
		String Result=LV.Admin_Login_Pg_Verf();	
		if (Result.equalsIgnoreCase("Pass")){
			Result9="Passed: Logged out of Admin screen successfully.";
		}else{
			Result9="Failed: Unable to log out out of Admin screen.";
		}
		FinalResult =  FinalResult + "\r\n Result9="+Result9;
		FinalResult=FinalResult+"\r\n -------------------Step 9 - done----------";
		
		//Step 12-  Load some historical data 
		
		//GF.InsertSQLData(user, pass, url);
		GF.InsertSQLData(Unifia_Admin_Selenium.url,Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		GF.InsertBioburdenSQLData(Unifia_Admin_Selenium.url,Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		
		FinalResult =  FinalResult + "\r\n Load some historical data";
		FinalResult=FinalResult+"\r\n -------------------Step 12 - done----------";
		
		GF.CloseDriver();
		Unifia_Admin_Selenium.driver.quit();
		Unifia_Admin_Selenium_Grid.setGridDriver(browserType,URL);//This will re-launch driver
		Thread.sleep(1000);
	
		//Step 10-  Access dashboard		
		
		if (browserType.equals("iexplore"))	{	//Internet Explorer				
			Unifia_Admin_Selenium.driver.get(Unifia_Admin_Selenium.QMC_URL);
	   		Thread.sleep(3000);	
	   		user=new UserAndPassword("sprinttest-05.mitlab.com\\QvService", "0lympu$123");
	   		Unifia_Admin_Selenium.driver.switchTo().alert().authenticateUsing(user);    	
	   		Thread.sleep(3000);
	   		Unifia_Admin_Selenium.driver.findElement(By.xpath("//img[contains(@src,'/QMCCommon/Images/plus.png')]")).click();
			Thread.sleep(1000);
			Unifia_Admin_Selenium.driver.findElement(By.xpath("//img[contains(@src,'/QMCCommon/Images/plus.png')]")).click();	
			
	}
		else {QV_Gen.Launch_UnifiaQMC(Unifia_Admin_Selenium.QMC_URL,Unifia_Admin_Selenium.Env);}
		
		
		QV_Gen.Run_Reports();
		
		if (browserType.equals("iexplore")){
			Unifia_Admin_Selenium.driver.get(Unifia_Admin_Selenium.QV_URL);
			Thread.sleep(3000);	
			user=new UserAndPassword("mitlab\\qvtest01", "0lympu$");
	   		Unifia_Admin_Selenium.driver.switchTo().alert().authenticateUsing(user); 
	   		Thread.sleep(2000);
	}
		else {QV_Gen.Launch_UnifiaQlikView(Unifia_Admin_Selenium.QV_URL);}
		
		
		blnStatus = QV_Gen.VerifyDashBoardPage();
		if (blnStatus){
			Result10="Passed: Dashboard page is displayed successfully.";
		}else{
			Result10="Failed: Dashboard page is not displayed.";
		}
		FinalResult =  FinalResult + "\r\n Result10="+Result10;
		FinalResult=FinalResult+"\r\n -------------------Step 10 - done----------";
		
		//Step 11-  Verify the dashboard matches the locations that where setup
		
		Result=DV.Verify_PR_Status("Storage Area A-1", Red);
		if(Result.equalsIgnoreCase("Pass"))
		{
			Result11="Passed: 'Location - Storage Area A-1' is displayed in dashboard page.";
		}else{
			Result11="Failed: 'Location - Storage Area A-1' is not displayed in dashboard page.";
		}
		
		FinalResult =  FinalResult + "\r\n Result11="+Result11;
		
		Result=DV.Verify_PR_Status("Storage Area A-2", Green);
		if(Result.equalsIgnoreCase("Pass"))
		{
			Result12="Passed: 'Location - Storage Area A-2' is displayed in dashboard page.";
		}else{
			Result12="Failed: 'Location - Storage Area A-2' is not displayed in dashboard page.";
		}
		FinalResult =  FinalResult + "\r\n Result12="+Result12;
		
		Result=DV.Verify_PR_Status("Storage Area A-3", Green);
		if(Result.equalsIgnoreCase("Pass"))
		{
			Result13="Passed: 'Location - Storage Area A-3' is displayed in dashboard page.";
		}else{
			Result13="Failed: 'Location - Storage Area A-3' is not displayed in dashboard page.";
		}
		FinalResult =  FinalResult + "\r\n Result13="+Result13;
		
		Result=DV.Verify_PR_Status("Procedure Room 1", Green);
		if(Result.equalsIgnoreCase("Pass"))
		{
			Result14="Passed: 'Location - Procedure Room 1' is displayed in dashboard page.";
		}else{
			Result14="Failed: 'Location - Procedure Room 1' is not displayed in dashboard page.";
		}
		FinalResult =  FinalResult + "\r\n Result14="+Result14;
		
		Result=DV.Verify_PR_Status("Procedure Room 2", Green);
		if(Result.equalsIgnoreCase("Pass"))
		{
			Result15="Passed: 'Location - Procedure Room 2' is displayed in dashboard page.";
		}else{
			Result15="Failed: 'Location - Procedure Room 2' is not displayed in dashboard page.";
		}
		FinalResult =  FinalResult + "\r\n Result15="+Result15;
		
		Result=DV.Verify_PR_Status("Procedure Room 3", Green);
		if(Result.equalsIgnoreCase("Pass"))
		{
			Result16="Passed: 'Location - Procedure Room 3' is displayed in dashboard page.";
		}else{
			Result16="Failed: 'Location - Procedure Room 3' is not displayed in dashboard page.";
		}
		FinalResult =  FinalResult + "\r\n Result16="+Result16;
		FinalResult=FinalResult+"\r\n -------------------Step 11 - done----------";
		

		
		System.out.println("Overall Test status is "+FinalResult);	
		try{ 
			  // Create file 
			  FileWriter fstream = new FileWriter("SmokeTestScript_TestSummary.txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(FinalResult);
			  //Close the output stream
			  out.close();
		  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		  }
		
		
		
		
	}
	
	
}
