package DailyDashBoard;
import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import ITConsole.ITConScanSimActions;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminUserPage.User_Actions;

public class DashboardDataWithoutKE {	
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	
	public QV_GeneralFunc QV_Gen;
	public GeneralFunc gf;
	public int KE=0;
	public int Bioburden=1;
	public int Culture=0;
	
	public String Scanner="";
	public Boolean Res;
	public String ScanScope;
	public User_Actions ua;
	public String GridID; 
	public String Description;	
	public LandingPage_Actions SE_LA;
	public LandingPage_Actions LP_A;
	private TestDataFunc TDF;
	private ITConsole.ITConScanSimActions IT_A;
	
	private String fileDestFolder="\\TestData\\AppTestData";
	private String scenarioFilePath="TestData\\AppTestData";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	private String testScenarioXML="TestData\\AppTestData\\TestScenarios.xml";
	private String ReconXMLFile="ReconScanData_ITConsole";
	
	   @Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	    public void Test(String browserP, String URL,String AdminDB) throws Exception {
	    	//select the Driver type Grid or local
	    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
	    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
	    		System.exit(1);
	    	}
	    	Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	    	
	    	String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
			String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
			String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
	    	
	    	//Inserting Master Data
			gf.SyncRemoteMachineTime(UAS.KE_Env, UAS.KEMachine_Username, UAS.KEMachine_pswd, URL);
			TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
	    	TDF.insertMasterData(UAS.url, UAS.user, UAS.pass, KE, Bioburden, Culture);
			gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);

	    	//gf.InsertSQLData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    	
	    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
			LGPA.Logon_Username("uadmin");
			LGPA.Logon_Password("Olympu$123");
			LGPA.Click_Submit();
			SE_LA.Click_Admin_Menu("User");
			Description ="The user clicks the navigational link: User ";
			Thread.sleep(2000);
			ua.ClearAllSearchCriteria(); //Clear all search criteria
			ua.Search_User_ByName("qvtest01"); //Search for the user name to be modified.
			GridID=ua.GetGridID_User_To_Modify("qvtest01"); //Get the grid id for the user name to be modified.
			ua.Select_User_To_Modify("qvtest01"); //Select the user name to be modified.
			ua.Select_User_Role("Manager/Supervisor");
			ua.Save_User_Edit();
			SE_LA.Click_Logout();
			   	
	    	Connection conn= null;
			//start
	    	boolean isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ReconScanData_ITConsole.xml",fileSource,fileDestination);
			if(isFileCopied){
				System.out.println("Pass- "+"ReconScanData_ITConsole.xml"+" is copied to "+fileDestination);
			}else{
				System.out.println("#Failed!#- "+"ReconScanData_ITConsole.xml" +" is not copied to "+fileDestination);
			}
			//Copying Runbatch.bat file to server machine
			isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.batchFile,fileSource,batchFileDestination);
										
			if(isFileCopied){
				System.out.println("Pass- "+UAS.batchFile+" is copied to "+batchFileDestination);
			}else{
				System.out.println("#Failed!#- "+UAS.batchFile+" is not copied to "+batchFileDestination);
			}
		
			//Update the environment and xml file in  Runbatch.bat file
			IT_A.modifyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.scanSimPath, UAS.batchFile,"ReconScanData_ITConsole.xml", xmlPath);
			//execute ScanSimUI
			IT_A.executeScanSim(UAS.Env, UAS.userName, UAS.IISPass,UAS.scanSimPathTool,UAS.ITConsoleExecTime,0);
			Thread.sleep(2000);
			// Run KE_UT
	    	//end 
	    	
	    	
			//Creating TestScenarios.xml
	    	
	    	
	    	
	    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
	    	
	    	//Inserting Data into Procedure Rooms
			Res = EM_A.ScanItem("Procedure Room 1", "Workflow Event", "", "Available", "");
			System.out.println(Res);

	    	Res = EM_A.ScanItem("Procedure Room 2", "Scope", "", "Scope16", "");//Scanning Scope 16 into procedure room 2
			System.out.println(Res);
			Thread.sleep(20000);
	    	
			Res = EM_A.ScanItem("Procedure Room 2", "Patient", "", "MRN111111", "");//Scanning Patient MRN111111 into Procedure room 2
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Procedure Room 3", "Workflow Event", "", "Needs Cleaning", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Procedure Room 5", "Workflow Event", "", "Closed", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 2", "Scope", "", "Scope11", "");
			System.out.println(Res);
					
			Res = EM_A.ScanItem("Reprocessor 2", "Scope", "", "Scope35", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 4", "Scope", "", "Scope24", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 4", "Scope", "", "Scope33", "");
			System.out.println(Res);
						
			Res = EM_A.ScanItem("Waiting1", "Patient", "", "MRN222222", "");//Scanning Patient MRN222222 into Waiting Room 1
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting1", "Staff", "Physician", "Physician10 Physician10(MD10)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting1", "Exam Type", "", "Colonoscopy", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting1", "Exam Type", "", "Esophagogastroduodenoscopy", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting1", "Workflow Event", "", "Send To ExamQueue", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting2", "Patient", "", "MRN333333", "");//Scanning Patient MRN333333 into Waiting Room 2
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting2", "Exam Type", "", "Colonoscopy", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting2", "Staff", "Physician", "Physician1 Physician1(MD01)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting2", "Staff", "Physician", "Physician2 Physician2(MD02)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Waiting2", "Workflow Event", "", "Send To ExamQueue", "");
			System.out.println(Res);
			
			//Data for Awaiting Reprocessing-1
			Res = EM_A.ScanItem("Sink 6", "Scope", "", "Scope7", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 6", "Staff", "Tech", "Tech2 Tech2(T02)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 6", "Test Result", "", "Leak Test Pass", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 6", "Workflow Event", "", "Manual Clean Start", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 6", "Workflow Event", "", "Manual Clean End", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 6", "Staff", "Tech", "Tech4 Tech4(T04)", "");
			System.out.println(Res);
			
			//Data for Awaiting Reprocessing-2
			Res = EM_A.ScanItem("Sink 5", "Scope", "", "Scope12", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Staff", "Tech", "Tech8 Tech8(T08)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Test Result", "", "Leak Test Pass", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Workflow Event", "", "Manual Clean Start", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Workflow Event", "", "Manual Clean End", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Staff", "Tech", "Tech3 Tech3(T03)", "");
			System.out.println(Res);
			
			//Data for Awaiting Reprocessing-3
			Res = EM_A.ScanItem("Sink 4", "Scope", "", "Scope25", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Staff", "Tech", "Tech15 Tech15(T15)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Test Result", "", "Leak Test Pass", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Workflow Event", "", "Manual Clean Start", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Workflow Event", "", "Manual Clean End", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Staff", "Tech", "Tech7 Tech7(T07)", "");
			System.out.println(Res);
			
			//Data for Awaiting Manual Cleaning - 1
			Res = EM_A.ScanItem("Sink 5", "Scope", "", "Scope3", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Staff", "Tech", "Tech4 Tech4(T04)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Test Result", "", "Leak Test Pass", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Workflow Event", "", "Manual Clean Start", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Workflow Event", "", "Manual Clean End", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 5", "Staff", "Tech", "Tech4 Tech4(T04)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Bioburden1", "Scope", "", "Scope3", "");
			System.out.println(Res);
			
			EM_A.ScanItem("Bioburden1", "Bioburden Testing", "", "Fail", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Bioburden1", "Staff", "Tech", "Tech4 Tech4(T04)", "");
			System.out.println(Res);
			
			//Data for Awaiting Manual Cleaning - 2
			Res = EM_A.ScanItem("Sink 4", "Scope", "", "Scope5", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Staff", "Tech", "Tech7 Tech7(T07)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Test Result", "", "Leak Test Pass", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Workflow Event", "", "Manual Clean Start", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Workflow Event", "", "Manual Clean End", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Sink 4", "Staff", "Tech", "Tech9 Tech9(T09)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Bioburden1", "Scope", "", "Scope7", "");
			System.out.println(Res);
			
			EM_A.ScanItem("Bioburden1", "Bioburden Testing", "", "Fail", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Bioburden1", "Staff", "Tech", "Tech4 Tech4(T04)", "");
			System.out.println(Res);
			
			//Inserting Data int Storage area A-2
			Res = EM_A.ScanItem("Reprocessor 5", "Scope", "", "Scope6", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 5", "Staff", "Tech", "Tech6 Tech6(T06)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 5", "Scope", "", "Scope6", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Scope", "", "Scope6", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Key Entry", "", "", "2");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
			System.out.println(Res);
			
			//Inserting Data int Storage area A-3
			Res = EM_A.ScanItem("Reprocessor 5", "Scope", "", "Scope29", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 5", "Staff", "Tech", "Tech10 Tech10(T10)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 5", "Scope", "", "Scope29", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Scope", "", "Scope29", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Key Entry", "", "", "3");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
			System.out.println(Res);
			
			//Inserting Data into Storage area A-4
			Res = EM_A.ScanItem("Reprocessor 6", "Scope", "", "Scope40", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 6", "Staff", "Tech", "Tech10 Tech10(T10)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 6", "Scope", "", "Scope40", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Scope", "", "Scope40", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Key Entry", "", "", "4");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
			System.out.println(Res);
			
			//Inserting Data int Storage area A-4
			Res = EM_A.ScanItem("Reprocessor 6", "Scope", "", "Scope9", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 6", "Staff", "Tech", "Tech10 Tech10(T10)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Reprocessor 6", "Scope", "", "Scope9", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Scope", "", "Scope9", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Key Entry", "", "", "4");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Administration", "Scope", "", "Scope2", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Administration", "Scope", "", "Scope10", "");
			System.out.println(Res);
			
			Res = EM_A.ScanItem("Procedure Room 1", "Workflow Event", "", "Available", "");//Making Procedure Room 1 as available.
			System.out.println(Res);
			Thread.sleep(20000);
		
			try{
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
	    		Statement update1 = conn.createStatement();
	    		String stmt1="update ItemHistory set ReceivedDateTime= DATEADD(dd,-6,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-6,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-6,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK=6 and CycleEventID_FK=18 and LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory where ScanItemID_FK=6 and CycleEventID_FK=18)";
	    		System.out.println("stmt="+stmt1);
	    		update1.executeUpdate(stmt1);//Making the Scope 6 to approach hang time in Storage Area A-2
	    		
	    		String stmt2="update ItemHistory set ReceivedDateTime= DATEADD(dd,-10,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-10,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-10,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK=29 and CycleEventID_FK=18 and LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory where ScanItemID_FK=29 and CycleEventID_FK=18)";
	    		System.out.println("stmt="+stmt2);
	    		update1.executeUpdate(stmt2);//Making the Scope 29 to exceed hang time in Storage Area A-3
	    		
	    		String stmt3="update ItemHistory set ReceivedDateTime= DATEADD(dd,-6,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-6,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-6,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK=40 and CycleEventID_FK=18 and LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory where ScanItemID_FK=40 and CycleEventID_FK=18)";
	    		System.out.println("stmt="+stmt3);
	    		update1.executeUpdate(stmt3);//Making the Scope 30 to approach hang time in Storage Area A-4
	    		
	    		String stmt4="update ItemHistory set ReceivedDateTime= DATEADD(dd,-10,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-10,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-10,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK=9 and CycleEventID_FK=18 and LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory where ScanItemID_FK=9 and CycleEventID_FK=18)";
	    		System.out.println("stmt="+stmt4);
	    		update1.executeUpdate(stmt4);//Making the Scope 9 to exceed hang time in Storage Area A-4
	    		
	    		String stmt5="update ItemHistory set ReceivedDateTime= DATEADD(dd,-3,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-3,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-3,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK=1 and CycleEventID_FK=18 and LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory where ScanItemID_FK=1 and CycleEventID_FK=18)";
	    		System.out.println("stmt="+stmt5);
	    		update1.executeUpdate(stmt5);//Making the Scope 1 not to have approaching hang time in Storage Area A-3
	 
	    		/*stmt="update ScopeStatus set LastUpdatedDateTime=DATEADD(MI, -46, GETUTCDATE()) where ScopeID_FK=12";
	    		System.out.println("stmt="+stmt);
	    		update1.executeUpdate(stmt);//Making Awaiting Reprocessing time greater than 1 hour for Scope 12
	    		conn.close();*/
	    		
	    	}
	    	catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
			Thread.sleep(60000);
			LandingPage_Actions.CloseDriver();
	   }	
	   
	   @AfterTest
		  public void PostTest() throws IOException{
		  	LandingPage_Actions.CloseDriver();
		  }
}
