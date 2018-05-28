package StoredScans;

import java.io.IOException;
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

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import ITConsole.ITConScanSimActions;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;


public class StoredScans_PR3Scopes {
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
	
	private int KE=0;
	private int Bioburden=0;
	private int Culture=0;
	
	private String ProcedureRoom="Procedure Room 1";
	private int StoredScope1InPRAssoc_ID;
	private int StoredScope1InPRIH_ID;
	private int Stored1StaffInPRIH_ID;
	private int StoredScope1OutPRIH_ID;
	private int Stored1StaffOutPRIH_ID;
	private int StoredScope2InPRIH_ID;
	private int Stored2StaffInPRIH_ID;
	private int StoredScope2OutPRIH_ID;
	private int Stored2StaffOutPRIH_ID;
	private int StoredScope3InPRIH_ID;
	private int Stored3StaffInPRIH_ID;
	private int StoredScope3OutPRIH_ID;
	private int Stored3StaffOutPRIH_ID;
	private int rowcount;
	private List<Integer> ScopeAssocID=new ArrayList<Integer>();
	
	private String actualResult="\t\t\t StoredScans_PR3Scopes_TestSummary \r\n\r\n"; 
	private String ForFileName;
	private String TestResFileName="StoredScans_PR3Scopes_TestSummary_";
	private String Result;
	private String FileName;
	private String Description;
	private String Expected;
	
	private String scope1="Scope1";
	private String scope2="Scope2";
	private String scope3="Scope3";
	private String scope4="Scope4";
	private int scope1ID=1;
	private int scope2ID=2;
	private int scope3ID=3;
	private int scope4ID=4;
	private String Scope1ExpectedReproCount;
	private String Scope1ExpectedExamCount;
	private String Scope2ExpectedReproCount;
	private String Scope2ExpectedExamCount;
	private String Scope3ExpectedReproCount;
	private String Scope3ExpectedExamCount;
	private String Scope4ExpectedReproCount;
	private String Scope4ExpectedExamCount;
	private int scope1LastScanStaffID_FK;
	private int scope2LastScanStaffID_FK;
	private int scope3LastScanStaffID_FK;
	private int scope4LastScanStaffID_FK;
	private int expectedScope1StaffID=32;
	private int expectedScope2StaffID=33;
	private int expectedScope3StaffID=34;
	private int expectedScope4StaffID=29;
	
	private String File="ScenarioInitialScans_PR3Scopes_StoredScans.xml";
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void PR3Scopes_StoredScans(String browserP, String URL, String AdminDB) throws Exception {
		if (UAS.parallelExecutionType && UAS.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		FileName="StoredScans_PR3Scopes_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;

		//Inserting Master Data
    	//gf.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		
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
		
		actualResult=actualResult+"\r\n"+IT_A.log+"\r\n========================================";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		if(!(IT_A.log.contains("#Failed!#"))){
			try{
				conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
				Statement statement=conn.createStatement();
				
				//4a. The associationID_FK for stored scan of scope1 into the PR1.
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=1 and IH.CycleEventID_FK=3 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope1InPRAssoc_ID=rs.getInt(1);
				}
				
				//4b. The ItemHistoryID_PK for stored scan of scope1 into the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=1 and IH.CycleEventID_FK=3 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope1InPRIH_ID=rs.getInt(1);
				}
				
				//4c. The ItemHistoryID_PK for stored scan of staff T10 for scope1 into the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=2 and IH.ScanItemID_FK=30 and IH.AssociationID_FK="+StoredScope1InPRAssoc_ID+" and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Stored1StaffInPRIH_ID=rs.getInt(1);
				}
				
				//4d. The ItemHistoryID_PK for stored scan of scope2 into the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=3 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2InPRIH_ID=rs.getInt(1);
				}
				
				//4e. The ItemHistoryID_PK for stored scan of staff T11 for scope2 into the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=2 and IH.ScanItemID_FK=31 and IH.AssociationID_FK="+StoredScope1InPRAssoc_ID+" and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Stored2StaffInPRIH_ID=rs.getInt(1);
				}
				
				//4f. The ItemHistoryID_PK for stored scan of scope3 into the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=3 and IH.CycleEventID_FK=3 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope3InPRIH_ID=rs.getInt(1);
				}
				
				//4g. The ItemHistoryID_PK for stored scan of staff T14 for scope3 into the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=2 and IH.ScanItemID_FK=34 and IH.AssociationID_FK="+StoredScope1InPRAssoc_ID+" and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Stored3StaffInPRIH_ID=rs.getInt(1);
					break;
				}
				
				//4h. The ItemHistoryID_PK for stored scan of scope1 out of the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=1 and IH.CycleEventID_FK=8 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope1OutPRIH_ID=rs.getInt(1);
				}
				
				//4i. The ItemHistoryID_PK for stored scan of staff T12 for scope1 out of the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=2 and IH.ScanItemID_FK=32 and IH.AssociationID_FK="+StoredScope1InPRAssoc_ID+" and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Stored1StaffOutPRIH_ID=rs.getInt(1);
				}
				
				//4j. The ItemHistoryID_PK for stored scan of scope2 out of the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=8 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2OutPRIH_ID=rs.getInt(1);
				}
				
				//4k. The ItemHistoryID_PK for stored scan of staff T13 for scope2 out of the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=2 and IH.ScanItemID_FK=33 and IH.AssociationID_FK="+StoredScope1InPRAssoc_ID+" and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Stored2StaffOutPRIH_ID=rs.getInt(1);
				}
				
				//4l. The ItemHistoryID_PK for stored scan of scope3 out of the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=3 and IH.CycleEventID_FK=8 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope3OutPRIH_ID=rs.getInt(1);
				}
				
				//4m. The ItemHistoryID_PK for stored scan of staff T14 for scope3 out of the PR1.
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=2 and IH.ScanItemID_FK=34 and IH.AssociationID_FK="+StoredScope1InPRAssoc_ID+" and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Stored3StaffOutPRIH_ID=rs.getInt(1);
				}
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=1 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getInt(1));
				}
				
				//5. Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for scope1 into the ProcedureRoom
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for scope1 into the ProcedureRoom";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=2 should contain the AssociationID_FK for scope1 into the ProcedureRoom";
				if(ScopeAssocID.contains(StoredScope1InPRAssoc_ID)){
					Result="Pass- Scope cycle of Scope1 for Cycle 2 contains the Stored AssociationID("+StoredScope1InPRAssoc_ID+") of Scope1 into "+ProcedureRoom;
				}else{
					Result="#Failed!#- Scope cycle of Scope1 for Cycle 2 does not contain the Stored AssociationID("+StoredScope1InPRAssoc_ID+") of Scope1 into "+ProcedureRoom;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				ScopeAssocID.clear();
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=2 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getInt(1));
				}
				
				//6. Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK for scope1 into the ProcedureRoom
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK for scope2 into the ProcedureRoom";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=2 should contain the AssociationID_FK for scope2 into the ProcedureRoom";
				if(ScopeAssocID.contains(StoredScope1InPRAssoc_ID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 2 contains the Stored AssociationID("+StoredScope1InPRAssoc_ID+") of Scope2 into "+ProcedureRoom;
				}else{
					Result="#Failed!#- Scope cycle of Scope2 for Cycle 2 does not contain the Stored AssociationID("+StoredScope1InPRAssoc_ID+") of Scope2 into "+ProcedureRoom;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				ScopeAssocID.clear();
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=3 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getInt(1));
				}
				
				//7. Checking whether ScopeCycle table for ScopeID_FK=3 and CycleID=2 contains the AssociationID_FK for scope3 into the ProcedureRoom
				Description="Checking whether ScopeCycle table for ScopeID_FK=3 and CycleID=2 contains the AssociationID_FK for scope3 into the ProcedureRoom";
				Expected="ScopeCycle table for ScopeID_FK=3 and CycleID=2 should contain the AssociationID_FK for scope3 into the ProcedureRoom";
				if(ScopeAssocID.contains(StoredScope1InPRAssoc_ID)){
					Result="Pass- Scope cycle of Scope3 for Cycle 2 contains the Stored AssociationID("+StoredScope1InPRAssoc_ID+") of Scope3 into "+ProcedureRoom;
				}else{
					Result="#Failed!#- Scope cycle of Scope3 for Cycle 2 does not contain the Stored AssociationID("+StoredScope1InPRAssoc_ID+") of Scope3 into "+ProcedureRoom;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope1InPRIH_ID+" and RelatedItemHistoryID_FK="+Stored1StaffInPRIH_ID;
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowcount=rs.getInt(1);
				}
				
				//8. Checking whether RelatedItem table has a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope1 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T10 for scope1 into PR1";
				Description="Checking whether RelatedItem table has a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope1 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T10 for scope1 into PR1";
				Expected="RelatedItem table should have a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope1 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T10 for scope1 into PR1";
				if(rowcount>=1){
					Result="Pass- RelatedItem Table contains a row with "+StoredScope1InPRIH_ID+", "+Stored1StaffInPRIH_ID;
				}else{
					Result="#Failed!#- RelatedItem Table does not contain a row with "+StoredScope1InPRIH_ID+", "+Stored1StaffInPRIH_ID;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	
			

				stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope2InPRIH_ID+" and RelatedItemHistoryID_FK="+Stored2StaffInPRIH_ID;
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowcount=rs.getInt(1);
				}
				
				//9. Checking whether RelatedItem table has a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope2 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T11 for scope2 into PR1";
				Description="Checking whether RelatedItem table has a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope2 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T11 for scope2 into PR1";
				Expected="RelatedItem table should have a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope2 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T11 for scope2 into PR1";
				if(rowcount>=1){
					Result="Pass- RelatedItem Table contains a row with "+StoredScope2InPRIH_ID+", "+Stored2StaffInPRIH_ID;
				}else{
					Result="#Failed!#- RelatedItem Table does not contain a row with "+StoredScope2InPRIH_ID+", "+Stored2StaffInPRIH_ID;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	
			

				stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope3InPRIH_ID+" and RelatedItemHistoryID_FK="+Stored3StaffInPRIH_ID;
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowcount=rs.getInt(1);
				}
				
				//10. Checking whether RelatedItem table has a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope3 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T14 for scope3 into PR1";
				Description="Checking whether RelatedItem table has a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope3 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T14 for scope3 into PR1";
				Expected="RelatedItem table should have a row with ItemHistoryID_FK=The ItemHistoryID_PK for stored scan of scope3 into PR1 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T14 for scope3 into PR1";
				if(rowcount>=1){
					Result="Pass- RelatedItem Table contains a row with "+StoredScope3InPRIH_ID+", "+Stored3StaffInPRIH_ID;
				}else{
					Result="#Failed!#- RelatedItem Table does not contain a row with "+StoredScope3InPRIH_ID+", "+Stored3StaffInPRIH_ID;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	
			
				
				stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope1OutPRIH_ID+" and RelatedItemHistoryID_FK="+Stored1StaffOutPRIH_ID;
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowcount=rs.getInt(1);
				}
				//11. Checking whether RelatedItem table has a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope1 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T12 for scope1 out of PR1
				Description="Checking whether RelatedItem table has a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope1 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T12 for scope1 out of PR1";
				Expected="RelatedItem table should have a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope1 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T12 for scope1 out of PR1";
				if(rowcount>=1){
					Result="Pass- RelatedItem Table contains a row with "+StoredScope1OutPRIH_ID+", "+Stored1StaffOutPRIH_ID;
				}else{
					Result="#Failed!#- RelatedItem Table does not contain a row with "+StoredScope1OutPRIH_ID+", "+Stored1StaffOutPRIH_ID;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	
				
				stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope2OutPRIH_ID+" and RelatedItemHistoryID_FK="+Stored2StaffOutPRIH_ID;
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowcount=rs.getInt(1);
				}
				//12. Checking whether RelatedItem table has a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope2 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T13 for scope2 out of PR1
				Description="Checking whether RelatedItem table has a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope2 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T13 for scope2 out of PR1";
				Expected="RelatedItem table should have a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope2 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T13 for scope2 out of PR1";
				if(rowcount>=1){
					Result="Pass- RelatedItem Table contains a row with "+StoredScope2OutPRIH_ID+", "+Stored2StaffOutPRIH_ID;
				}else{
					Result="#Failed!#- RelatedItem Table does not contain a row with "+StoredScope2OutPRIH_ID+", "+Stored2StaffOutPRIH_ID;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	
				
				stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope3OutPRIH_ID+" and RelatedItemHistoryID_FK="+Stored3StaffOutPRIH_ID;
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowcount=rs.getInt(1);
				}
				//13. Checking whether RelatedItem table has a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope3 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T14 for scope3 out of PR1
				Description="Checking whether RelatedItem table has a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope3 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T14 for scope3 out of PR1";
				Expected="RelatedItem table should have a row with ItemHistoryID_FK=  The ItemHistoryID_PK for stored scan of scope3 out of PR1 (Preclean) and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T14 for scope3 out of PR1";
				if(rowcount>=1){
					Result="Pass- RelatedItem Table contains a row with "+StoredScope3OutPRIH_ID+", "+Stored3StaffOutPRIH_ID;
				}else{
					Result="#Failed!#- RelatedItem Table does not contain a row with "+StoredScope3OutPRIH_ID+", "+Stored3StaffOutPRIH_ID;
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
				scope1LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, scope1);
				
				//Checking the LastScanStaffID for Scope1
				Description="Checking whether LastScanStaffID for Scope1 is "+expectedScope1StaffID;
				Expected="LastScanStaffID for Scope1 Should be "+expectedScope1StaffID;
				String ResultLastStaff=IHV.Result_LastScanStaffID(scope1LastScanStaffID_FK, expectedScope1StaffID);
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
				scope2LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, scope2);
				
				//Checking the LastScanStaffID for Scope2
				Description="Checking whether LastScanStaffID for Scope2 is "+expectedScope2StaffID;
				Expected="LastScanStaffID for Scope2 Should be "+expectedScope2StaffID;
				ResultLastStaff=IHV.Result_LastScanStaffID(scope2LastScanStaffID_FK, expectedScope2StaffID);
				System.out.println(ResultLastStaff);
				actualResult=actualResult+"\r\n\r\n"+ResultLastStaff;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);
				
				//Quering for Scope3 status
				Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, scope3);
				String Scope3ActualReproCount=Scope_IH[5];
				String Scope3ActualExamCount=Scope_IH[6];
				
				//Quering for Scope3 ExpectedReproCount
				Scope3ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, scope3ID);
				
				//Checking the ReprocessingCount for Scope3
				Description="Checking whether ReprocessingCount for Scope3 is "+Scope3ExpectedReproCount;
				Expected="ReprocessingCount for Scope3 Should be "+Scope3ExpectedReproCount;
				ResultReproCount=IHV.Result_ReproCount(Scope3ActualReproCount,Scope3ExpectedReproCount);
				
				System.out.println(ResultReproCount);
				actualResult=actualResult+"\r\n\r\n"+ResultReproCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultReproCount);
				
				//Quering for Scope3 ExpectedExamCount
				Scope3ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, scope3ID);
				
				//Checking the ExamCount for Scope3
				Description="Checking whether ExamCount for Scope3 is "+Scope3ExpectedExamCount;
				Expected="ExamCount for Scope3 Should be "+Scope3ExpectedExamCount;
				ResultExamCount=IHV.Result_ExamCount(Scope3ActualExamCount,Scope3ExpectedExamCount);
				System.out.println(ResultExamCount);
				actualResult=actualResult+"\r\n\r\n"+ResultExamCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultExamCount);
				
				//Quering for Scope3 LastScanStaffID
				scope3LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, scope3);
				
				//Checking the LastScanStaffID for Scope3
				Description="Checking whether LastScanStaffID for Scope3 is "+expectedScope3StaffID;
				Expected="LastScanStaffID for Scope3 Should be "+expectedScope3StaffID;
				ResultLastStaff=IHV.Result_LastScanStaffID(scope3LastScanStaffID_FK, expectedScope3StaffID);
				System.out.println(ResultLastStaff);
				actualResult=actualResult+"\r\n\r\n"+ResultLastStaff;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);
				
				//Quering for Scope4 status
				Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, scope4);
				String Scope4ActualReproCount=Scope_IH[5];
				String Scope4ActualExamCount=Scope_IH[6];
				
				//Quering for Scope4 ExpectedReproCount
				Scope4ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, scope4ID);
				if(Scope4ExpectedReproCount.equalsIgnoreCase("")){
					Scope4ExpectedReproCount="0";
				}
				
				//Checking the ReprocessingCount for Scope4
				Description="Checking whether ReprocessingCount for Scope4 is "+Scope4ExpectedReproCount;
				Expected="ReprocessingCount for Scope4 Should be "+Scope4ExpectedReproCount;
				ResultReproCount=IHV.Result_ReproCount(Scope4ActualReproCount,Scope4ExpectedReproCount);
				
				

				System.out.println(ResultReproCount);
				actualResult=actualResult+"\r\n\r\n"+ResultReproCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultReproCount);
				
				//Quering for Scope4 ExpectedExamCount
				Scope4ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, scope4ID);
				if(Scope4ExpectedExamCount.equalsIgnoreCase("")){
					Scope4ExpectedExamCount="0";
				}

				//Checking the ExamCount for Scope4
				Description="Checking whether ExamCount for Scope4 is "+Scope4ExpectedExamCount;
				Expected="ExamCount for Scope4 Should be "+Scope4ExpectedExamCount;
				ResultExamCount=IHV.Result_ExamCount(Scope4ActualExamCount,Scope4ExpectedExamCount);
				
			
				System.out.println(ResultExamCount);
				actualResult=actualResult+"\r\n\r\n"+ResultExamCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultExamCount);
				
				//Quering for Scope4 LastScanStaffID
				scope4LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, scope4);
				
				//Checking the LastScanStaffID for Scope4
				Description="Checking whether LastScanStaffID for Scope4 is "+expectedScope4StaffID;
				Expected="LastScanStaffID for Scope4 Should be "+expectedScope4StaffID;
				ResultLastStaff=IHV.Result_LastScanStaffID(scope4LastScanStaffID_FK, expectedScope4StaffID);
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
