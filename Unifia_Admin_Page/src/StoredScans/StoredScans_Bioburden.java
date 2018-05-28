
package StoredScans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import ITConsole.ITConScanSimActions;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class StoredScans_Bioburden {
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private ITConsole.ITConScanSimActions IT_A;
	private GeneralFunc gf;
	private TestFrameWork.TestHelper TH;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestDataFunc TDF;
	
	private Connection conn;
	private String stmt;
	private ResultSet rs;

	private String Scanner="Bioburden1";
	private String StoredScope1AssocID;//The associationID_FK for stored scan of scope1 with Biburden Scanner.
	private String RealScope2AssocID;//The associationID_FK for real scan of scope2 with Biburden Scanner.
	
	private int KE=0;
	private int Bioburden=0;
	private int Culture=0;
	
	private String actualResult="\t\t\t StoredScans_Bioburden_TestSummary \r\n\r\n"; 
	private String ForFileName;
	private String TestResFileName="StoredScans_Bioburden_TestSummary_";
	private String Result;
	private String FileName;
	private String Description;
	private String Expected;
	
	private String scope1="Scope1";
	private String scope2="Scope2";
	private int scope1ID=1;
	private int scope2ID=2;
	private String Scope1ExpectedReproCount;
	private String Scope1ExpectedExamCount;
	private String Scope2ExpectedReproCount;
	private String Scope2ExpectedExamCount;
	private int actScope1LastScanStaffID_FK;
	private int actScope2LastScanStaffID_FK;
	private int expectedScope1StaffID=29;
	private int expectedScope2StaffID=32;
	
	private List<String> ScopeAssocID=new ArrayList<String>();
	
	private String File="ScenarioInitialScans_Bioburden_after_InitialScans.xml";
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Bioburden_StoredScans(String browserP, String URL, String AdminDB) throws Exception {
		if (UAS.parallelExecutionType && UAS.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		FileName="StoredScans_Bioburden_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		
		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
		
		//Inserting Master Data
    	//gf.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		
		TDF.insertMasterData(UAS.url, UAS.user, UAS.pass, KE, Bioburden, Culture);
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
		
		gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
    	    	
    	boolean isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.batchFile,fileSource,batchFileDestination);
		
		if(isFileCopied){
			System.out.println("Pass- "+UAS.batchFile+" is copied to "+batchFileDestination);
		}else{
			System.out.println("#Failed!#- "+UAS.batchFile+" is not copied to "+batchFileDestination);
		}
	
		//Update the environment and xml file in  Runbatch.bat file
		IT_A.modifyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.scanSimPath, UAS.batchFile,File, xmlPath);
		//execute ScanSimUI
		IT_A.executeScanSim(UAS.Env, UAS.userName, UAS.IISPass,UAS.scanSimPathTool,UAS.ITConsoleExecTime,0);
		Thread.sleep(2000);
  	
		//Verifying the ITConsole Execution
		//ITConScanSimActions.VerifyITconsoleExecution(Unifia_Admin_Selenium.Env);
		actualResult=actualResult+"\r\n\r\n"+IT_A.log;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		if(!IT_A.log.contains("#Failed!#")){
			try{
				conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
				Statement statement=conn.createStatement();
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=1 and IH.CycleEventID_FK=27 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Scanner+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope1AssocID=rs.getString(1);
				}
				System.out.println("StoredScope1AssocID = "+StoredScope1AssocID);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=27 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Scanner+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					RealScope2AssocID=rs.getString(1);
				}
				System.out.println("RealScope2AssocID = "+RealScope2AssocID);
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=1 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for scope1 into the reprocessor
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for scope1 scanned with Bioburden Scanner";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=2 should contain the AssociationID_FK for scope1 scanned with Bioburden Scanner";
				if(ScopeAssocID.contains(StoredScope1AssocID)){
					Result="Pass- Scope cycle of Scope1 for Cycle 2 contains the Stored AssociationID("+StoredScope1AssocID+") of Scope1 scanned with Bioburden Scanner";
				}else{
					Result="#Failed!#- Scope cycle of Scope1 for Cycle 2 does not contain the Stored AssociationID("+StoredScope1AssocID+") of Scope1 scanned with Bioburden Scanner";
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);			
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=2 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				//Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the real AssociationID_FK for scope2 scanned with Bioburden Scanner
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the Real AssociationID_FK for scope2 scanned with Bioburden Scanner";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=2 should contain the Real AssociationID_FK for scope2 scanned with Bioburden Scanner";
				if(ScopeAssocID.contains(RealScope2AssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 2 contains the Real AssociationID("+RealScope2AssocID+") of Scope2 scanned with Bioburden Scanner";
				}else{
					Result="#Failed!#- Scope cycle of Scope2 for Cycle 2 does not contain the Real AssociationID("+RealScope2AssocID+") of Scope2 scanned with Bioburden Scanner";
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				//Quering for Scope1 status
				String []Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, scope1);
				String Scope1ActualReproCount=Scope_IH[5];
				String Scope1ActualExamCount=Scope_IH[6];
				
				//Quering for Scope1 ExpectedReproCount
				Scope1ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, scope1ID);
				
				//Checking the ReprocessingCount for Scope1
				Description="Checking whether ReprocessingCount for Scope1 is "+Scope1ExpectedReproCount;
				Expected="ReprocessingCount for Scope1 Should be "+Scope1ExpectedReproCount;
				String ResultReproCount=IHV.Result_ReproCount(Scope1ActualReproCount,Scope1ExpectedReproCount);
				System.out.println(ResultReproCount);
				actualResult=actualResult+"\r\n\r\n"+ResultReproCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultReproCount);
				
				//Quering for Scope1 ExpectedExamCount
				Scope1ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, scope1ID);
				
				//Checking the ExamCount for Scope1
				Description="Checking whether ExamCount for Scope1 is "+Scope1ExpectedExamCount;
				Expected="ExamCount for Scope1 Should be "+Scope1ExpectedExamCount;
				String ResultExamCount=IHV.Result_ExamCount(Scope1ActualExamCount,Scope1ExpectedExamCount);
				System.out.println(ResultExamCount);
				actualResult=actualResult+"\r\n\r\n"+ResultExamCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultExamCount);
				
				//Quering for Scope1 LastScanStaffID
				actScope1LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, scope1);
				
				//Checking the LastScanStaffID for Scope1
				Description="Checking whether LastScanStaffID for Scope1 is "+expectedScope1StaffID;
				Expected="LastScanStaffID for Scope1 Should be "+expectedScope1StaffID;
				String ResultLastStaff=IHV.Result_LastScanStaffID(actScope1LastScanStaffID_FK, expectedScope1StaffID);
				if(ResultLastStaff.contains("#Failed!#")){
					ResultLastStaff=ResultLastStaff+". Bug 9827 open for this issue.";
				}

				System.out.println(ResultLastStaff);
				actualResult=actualResult+"\r\n\r\n"+ResultLastStaff;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);
				
				//Quering for Scope2 status
				Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, scope2);
				String Scope2ActualReproCount=Scope_IH[5];
				String Scope2ActualExamCount=Scope_IH[6];
				
				//Quering for Scope2 ExpectedReproCount
				Scope2ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, scope2ID);
				
				//Checking the ReprocessingCount for Scope2
				Description="Checking whether ReprocessingCount for Scope2 is "+Scope2ExpectedReproCount;
				Expected="ReprocessingCount for Scope2 Should be "+Scope2ExpectedReproCount;
				ResultReproCount=IHV.Result_ReproCount(Scope2ActualReproCount,Scope2ExpectedReproCount);
				System.out.println(ResultReproCount);
				actualResult=actualResult+"\r\n\r\n"+ResultReproCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultReproCount);
				
				//Quering for Scope2 ExpectedExamCount
				Scope2ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, scope2ID);
				
				//Checking the ExamCount for Scope2
				Description="Checking whether ExamCount for Scope2 is "+Scope2ExpectedExamCount;
				Expected="ExamCount for Scope2 Should be "+Scope2ExpectedExamCount;
				ResultExamCount=IHV.Result_ExamCount(Scope2ActualExamCount,Scope2ExpectedExamCount);
				System.out.println(ResultExamCount);
				actualResult=actualResult+"\r\n\r\n"+ResultExamCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultExamCount);
				
				//Quering for Scope2 LastScanStaffID
				actScope2LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, scope2);
				
				//Checking the LastScanStaffID for Scope2
				Description="Checking whether LastScanStaffID for Scope2 is "+expectedScope2StaffID;
				Expected="LastScanStaffID for Scope2 Should be "+expectedScope2StaffID;
				ResultLastStaff=IHV.Result_LastScanStaffID(actScope2LastScanStaffID_FK, expectedScope2StaffID);
				if(ResultLastStaff.contains("#Failed!#")){
					ResultLastStaff=ResultLastStaff+". Bug 9827 open for this issue.";
				}
				System.out.println(ResultLastStaff);
				actualResult=actualResult+"\r\n\r\n"+ResultLastStaff;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);

				rs.close();
				statement.close();
				conn.close();
			}catch(SQLException ex){
				 // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (actualResult.contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.contains("#Failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();	 
	
	}
	
	@AfterTest
		
		public void PostTest() throws IOException{
	  	LP_A.CloseDriver();
	  }
}
