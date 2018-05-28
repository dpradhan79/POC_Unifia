package dailydashboardmultifacility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminUserPage.User_Actions;

public class DashboardDataMulti {
	
	
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
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
	
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	private String XMLFile="Epic10883_Quality_Issue_Data_Today.xml";
	
	private ResultSet rs;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws Exception {
		Connection conn= null;
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
    	TDF.insertMultiFacilityMasterData(UAS.url, UAS.user, UAS.pass, KE, Bioburden, Culture);
		gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
		
		//
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
    	
		//start
    	boolean isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, XMLFile,fileSource,fileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+XMLFile+" is copied to "+fileDestination);
		}else{
			System.out.println("#Failed!#- "+XMLFile +" is not copied to "+fileDestination);
		}
		//Copying Runbatch.bat file to server machine
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.batchFile,fileSource,batchFileDestination);
									
		if(isFileCopied){
			System.out.println("Pass- "+UAS.batchFile+" is copied to "+batchFileDestination);
		}else{
			System.out.println("#Failed!#- "+UAS.batchFile+" is not copied to "+batchFileDestination);
		}
	
		//Update the environment and xml file in  Runbatch.bat file
		IT_A.modifyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.scanSimPath, UAS.batchFile,XMLFile, xmlPath);
		//execute ScanSimUI
		IT_A.executeScanSim(UAS.Env, UAS.userName, UAS.IISPass,UAS.scanSimPathTool,UAS.ITConsoleExecTime,0);
		Thread.sleep(2000);
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
	
		for (int datacntr=1;datacntr<=3;datacntr++){
		//Inserting Data into Procedure Rooms
		Res = EM_A.ScanItem("F"+datacntr+" Procedure Room 1", "Workflow Event", "", "Available", "");
		System.out.println(Res);

    	Res = EM_A.ScanItem("F"+datacntr+" Procedure Room 2", "Scope", "", "F"+datacntr+" Scope16", "");//Scanning Scope 16 into procedure room 2
		System.out.println(Res);
		Thread.sleep(20000);
    	
		Res = EM_A.ScanItem("F"+datacntr+" Procedure Room 2", "Patient", "", "MRN111111", "");//Scanning Patient MRN111111 into Procedure room 2
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Procedure Room 3", "Workflow Event", "", "Needs Cleaning", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Procedure Room 5", "Workflow Event", "", "Closed", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 2", "Scope", "", "F"+datacntr+" Scope11", "");
		System.out.println(Res);
				
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 2", "Scope", "", "F"+datacntr+" Scope35", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 4", "Scope", "", "F"+datacntr+" Scope24", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 4", "Scope", "", "F"+datacntr+" Scope33", "");
		System.out.println(Res);
					
		Res = EM_A.ScanItem("F"+datacntr+" Waiting1", "Patient", "", "MRN222222", "");//Scanning Patient MRN222222 into Waiting Room 1
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting1", "Staff", "Physician", "Physician15 Physician15(MD15)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting1", "Exam Type", "", "Colonoscopy", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting1", "Exam Type", "", "Esophagogastroduodenoscopy", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting1", "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting2", "Patient", "", "MRN333333", "");//Scanning Patient MRN333333 into Waiting Room 2
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting2", "Exam Type", "", "Colonoscopy", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting2", "Staff", "Physician", "Physician1 Physician1(MD01)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting2", "Staff", "Physician", "Physician2 Physician2(MD02)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Waiting2", "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		
		//Data for Awaiting Reprocessing-1
		Res = EM_A.ScanItem("F"+datacntr+" Sink 6", "Scope", "", "F"+datacntr+" Scope7", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 6", "Staff", "Tech", "Tech2 Tech2(T02)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 6", "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 6", "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 6", "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 6", "Staff", "Tech", "Tech4 Tech4(T04)", "");
		System.out.println(Res);
		
		//Data for Awaiting Reprocessing-2
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Scope", "", "F"+datacntr+" Scope12", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Staff", "Tech", "Tech8 Tech8(T08)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Staff", "Tech", "Tech3 Tech3(T03)", "");
		System.out.println(Res);
		
		//Data for Awaiting Reprocessing-3
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Scope", "", "F"+datacntr+" Scope25", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Staff", "Tech", "Tech15 Tech15(T15)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Staff", "Tech", "Tech7 Tech7(T07)", "");
		System.out.println(Res);
		
		//Data for Awaiting Manual Cleaning - 1
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Scope", "", "F"+datacntr+" Scope3", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Staff", "Tech", "Tech4 Tech4(T04)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 5", "Staff", "Tech", "Tech4 Tech4(T04)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Bioburden1", "Scope", "", "F"+datacntr+" Scope3", "");
		System.out.println(Res);
		
		EM_A.ScanItem("F"+datacntr+" Bioburden1", "Bioburden Testing", "", "Fail", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Bioburden1", "Staff", "Tech", "Tech4 Tech4(T04)", "");
		System.out.println(Res);
		
		//Data for Awaiting Manual Cleaning - 2
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Scope", "", "F"+datacntr+" Scope5", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Staff", "Tech", "Tech7 Tech7(T07)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Sink 4", "Staff", "Tech", "Tech9 Tech9(T09)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Bioburden1", "Scope", "", "F"+datacntr+" Scope7", "");
		System.out.println(Res);
		
		EM_A.ScanItem("F"+datacntr+" Bioburden1", "Bioburden Testing", "", "Fail", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Bioburden1", "Staff", "Tech", "Tech4 Tech4(T04)", "");
		System.out.println(Res);
		
		//Inserting Data into Storage area A-2
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope6", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Staff", "Tech", "Tech6 Tech6(T06)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope6", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope6", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "2");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Inserting Data int Storage area A-3
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope29", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope29", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope29", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "3");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Inserting Data into Storage area A-4
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 6", "Scope", "", "F"+datacntr+" Scope40", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 6", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 6", "Scope", "", "F"+datacntr+" Scope40", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope40", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "4");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Inserting Data int Storage area A-4
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 6", "Scope", "", "F"+datacntr+" Scope9", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 6", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 6", "Scope", "", "F"+datacntr+" Scope9", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope9", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "4");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Administration", "Scope", "", "F"+datacntr+" Scope2", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Administration", "Out of Facility Location", "", "Repair shop 1", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Administration", "Scope", "", "F"+datacntr+" Scope10", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Administration", "Out of Facility Location", "", "Olympus Repair", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Procedure Room 1", "Workflow Event", "", "Available", "");//Making Procedure Room 1 as available.
		System.out.println(Res);
		
		
		//Additional Data for multi Facility
		//Inserting Data int Storage area A-1
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope13", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Staff", "Tech", "Tech6 Tech6(T06)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope13", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope13", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "1");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Inserting Data int Storage area A-1
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope34", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Staff", "Tech", "Tech6 Tech6(T06)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope34", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope34", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "1");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Inserting Data into Storage area A-2
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope4", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Staff", "Tech", "Tech6 Tech6(T06)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope4", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope4", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "2");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Inserting Data int Storage area A-2
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope8", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Staff", "Tech", "Tech6 Tech6(T06)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope8", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope8", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "2");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Inserting Data int Storage area A-3
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope1", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope1", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Staff", "Tech", "Tech6 Tech6(T06)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Reprocessor 5", "Scope", "", "F"+datacntr+" Scope1", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Scope", "", "F"+datacntr+" Scope1", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Key Entry", "", "", "3");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F"+datacntr+" Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		}
		
		Thread.sleep(20000);

		try{
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
    		Statement update1 = conn.createStatement();
    		
    		String stmt11="select itemhistoryid_pk from itemhistory where ScanItemID_FK IN (6,206,306) and ScanItemTypeID_FK=1 and cycleeventid_fk=18";
    		Statement statement = conn.createStatement();
    		rs = statement.executeQuery(stmt11);
			String scopeids=null;
			int cntr=0;
			while(rs.next()){
				if (cntr==0){
					scopeids=rs.getString(1);
				}else{
					scopeids=scopeids+";"+rs.getString(1);
				}
				cntr++;
			}
			rs.close();

    		String[] arScopeids = scopeids.split(";");
			
			for (int scopeCntr =0; scopeCntr<arScopeids.length; scopeCntr++){
				String stmt1="update ItemHistory set ReceivedDateTime= DATEADD(dd,-6,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-6,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-6,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK in (6,206,306) and CycleEventID_FK=18 and itemhistoryid_pk="+arScopeids[scopeCntr];
				System.out.println("stmt="+stmt1);//Making the Scope 6 to approach hang time in Storage Area A-2
				update1.executeUpdate(stmt1);
				Thread.sleep(2000);
			}
    		 		
    		String stmt2="update ItemHistory set ReceivedDateTime= DATEADD(dd,-10,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-10,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-10,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK in (29,229,329) and CycleEventID_FK=18 and LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory where ScanItemID_FK in (29,229,329) and CycleEventID_FK=18)";
    		System.out.println("stmt="+stmt2);
    		update1.executeUpdate(stmt2);//Making the Scope 29 to exceed hang time in Storage Area A-3
    		
    		String stmt3="update ItemHistory set ReceivedDateTime= DATEADD(dd,-6,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-6,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-6,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK in (40,240,340) and CycleEventID_FK=18 and LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory where ScanItemID_FK in(40,240,340) and CycleEventID_FK=18)";
    		System.out.println("stmt="+stmt3);
    		update1.executeUpdate(stmt3);//Making the Scope 30 to approach hang time in Storage Area A-4
    		
    		stmt11="select itemhistoryid_pk from itemhistory where ScanItemID_FK  in (9,209,309) and ScanItemTypeID_FK=1 and cycleeventid_fk=18";
    		rs = statement.executeQuery(stmt11);
    		scopeids=null;
    		cntr=0;
			while(rs.next()){
				if (cntr==0){
					scopeids=rs.getString(1);
				}else{
					scopeids=scopeids+";"+rs.getString(1);
				}
				cntr++;
			}
			rs.close();
			
			arScopeids = scopeids.split(";");
			
			for (int scopeCntr =0; scopeCntr<arScopeids.length; scopeCntr++){
				String stmt4="update ItemHistory set ReceivedDateTime= DATEADD(dd,-10,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-10,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-10,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK in (9,209,309) and CycleEventID_FK=18 and itemhistoryid_pk="+arScopeids[scopeCntr];
				System.out.println("stmt="+stmt4);//Making the Scope 9 to exceed hang time in Storage Area A-4
				update1.executeUpdate(stmt4);
				Thread.sleep(2000);
			}    		
    		String stmt5="update ItemHistory set ReceivedDateTime= DATEADD(dd,-3,GETDATE()) ,LastUpdatedDateTime= DATEADD(dd,-3,GETDATE()) ,ProcessedDateTime= DATEADD(dd,-3,GETDATE()) where ScanItemTypeID_FK=1 and ScanItemID_FK in (1,201,301) and CycleEventID_FK=18 and LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory where ScanItemID_FK in (1,201,301) and CycleEventID_FK=18)";
    		System.out.println("stmt="+stmt5);
    		update1.executeUpdate(stmt5);//Making the Scope 1 not to have approaching hang time in Storage Area A-3

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
