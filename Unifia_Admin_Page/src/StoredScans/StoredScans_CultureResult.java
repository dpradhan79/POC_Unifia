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
import java.util.Iterator;
import java.util.List;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import ITConsole.ITConScanSimActions;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class StoredScans_CultureResult {
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

	private String CultureRoom="CultureA";
	private String ProcRoom="Procedure Room 3";
	private String StorageRoom="Storage Area B";
	
	private String StoredScope1InAssocID; //4a
	private String RealScope2InAssocID; //4b
	private String RealScope3CultObtaAssocID; //4c


	private String StoredScope3OutAssocID; //6a
	private String StoredScope3CultResOutAssocID; //6b
	
	private String StoredScope3OutItemHistID; //6c
	private String StoredScope3CultResItemHistID; //6d
	private String Storedstaff3StorageOutItemHistID; //6e
	
	private String StoredScope2InPRAssocID; //10a
	private String StoredScope2CulResInPRAssocID; //10b
	private String StoredScope2InPRItemHistID; //10c
	private String StoredScope2CulResInPRItemHistID; //10d
	private String StoredStaffInPRItemHistID; //10e
	
	
	private int KE=0;
	private int Bioburden=0;
	private int Culture=0;
	
	private String actualResult="\t\t\t StoredScans_CultureResult_TestSummary \r\n\r\n"; 
	private String ForFileName;
	private String TestResFileName="StoredScans_CultureResult_TestSummary_";
	private String Result;
	private String FileName;
	private String Description;
	private String Expected;
	
	private String scope1="Scope1";
	private String scope2="Scope2";
	private String scope3="Scope3";
	private int scope1ID=1;
	private int scope2ID=2;
	private int scope3ID=3;
	private String Scope1ExpectedReproCount;
	private String Scope1ExpectedExamCount;
	private String Scope2ExpectedReproCount;
	private String Scope2ExpectedExamCount;
	private String Scope3ExpectedReproCount;
	private String Scope3ExpectedExamCount;
	private int scope1LastScanStaffID_FK;
	private int scope2LastScanStaffID_FK;
	private int scope3LastScanStaffID_FK;
	private int expectedScope1StaffID=32;
	private int expectedScope2StaffID=36;
	private int expectedScope3StaffID=33;
	
	private List<String> ScopeAssocID=new ArrayList<String>();
	private List<String> SubLocationId=new ArrayList<String>();
	private boolean IsNULL=false;
	
	private String File="ScenarioInitialScans_Culture_StoredScans.xml";
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void StorageArea_StoredScans(String browserP, String URL, String AdminDB) throws Exception {
		if (UAS.parallelExecutionType && UAS.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		FileName="StoredScans_CultureResult_";
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
				
				//-- Query 4a The associationID_FK for stored scan of scope1 Culture Obtained. 
				stmt="select AssociationID_FK from ItemHistory IH where IsStored=1 and ScanItemTypeID_FK=1 and "
						+ "ScanItemID_FK=1 and CycleEventID_FK=30 and "
						+ "LocationID_FK=(select LocationID_PK from Location where Name='"+CultureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope1InAssocID=rs.getString(1);
				}
				System.out.println("StoredScope1InAssocID = "+StoredScope1InAssocID);
				
				//-- Query 4b The associationID_FK for real time scan of scope2 culture obtained. 
				stmt="select AssociationID_FK from ItemHistory IH where IsStored=0 and ScanItemTypeID_FK=1 and "
						+ "ScanItemID_FK=2 and CycleEventID_FK=30 and "
						+ "LocationID_FK=(select LocationID_PK from Location where Name='"+CultureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					RealScope2InAssocID=rs.getString(1);
				}
				System.out.println("RealScope2InAssocID = "+RealScope2InAssocID);
				
				//-- Query 4c The associationID_FK for real time scan of scope3 culture obtained. 
				stmt="select AssociationID_FK from ItemHistory IH where IsStored=0 and ScanItemTypeID_FK=1 and "
						+ "ScanItemID_FK=3 and CycleEventID_FK=30 and LocationID_FK="
						+ "(select LocationID_PK from Location where Name='"+CultureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					RealScope3CultObtaAssocID=rs.getString(1);
				}
				System.out.println("RealScope3CultObtaAssocID = "+RealScope3CultObtaAssocID);
				
				//-- Query 6a The associationID_FK for stored scan of scope3 out of the Storage Area.
				stmt="select AssociationID_FK from ItemHistory IH where IsStored=1 and ScanItemTypeID_FK=1 and ScanItemID_FK=3 "
						+ "and CycleEventID_FK=2 and LocationID_FK=(select LocationID_PK from location where Name='"+StorageRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope3OutAssocID=rs.getString(1);
				}
				System.out.println("StoredScope3OutAssocID = "+StoredScope3OutAssocID);
				
				//-- Query 6b The associationID_FK for stored scan of scope3 culture results when checking the scope out of the storage area. 
				stmt="select AssociationID_FK from ItemHistory where IsStored=1 and ScanItemTypeID_FK=8 and ScanItemID_FK=7 and "
						+ "CycleEventID_FK=33 and LocationID_FK=(select LocationID_PK from location where Name='"+StorageRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope3CultResOutAssocID=rs.getString(1);
				}
				System.out.println("StoredScope3CultResOutAssocID = "+StoredScope3CultResOutAssocID);
				
				//-- Query 6c The ItemHistoryID_PK for stored scan of scope3 out of the storage area 
				stmt="select ItemHistoryID_PK from ItemHistory  where IsStored=1 and ScanItemTypeID_FK=1 and "
				+ "ScanItemID_FK=3 and CycleEventID_FK=2 and LocationID_FK=(select LocationID_PK from location where Name='"+StorageRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope3OutItemHistID=rs.getString(1);
				}
				System.out.println("StoredScope3OutItemHistID = "+StoredScope3OutItemHistID);
				
				// -- Query 6d The ItemHistoryID_PK for stored scan of culture Result for Scope3 using Storage are scanner. 
				stmt="select ItemHistoryID_PK from ItemHistory  where IsStored=1  and CycleEventID_FK=33 "
				+ "and ScanItemTypeID_FK=8 and LocationID_FK=(select LocationID_PK from location where Name='"+StorageRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope3CultResItemHistID=rs.getString(1);
					}
				System.out.println("StoredScope3CultResItemHistID = "+StoredScope3CultResItemHistID);
				
				// -- Query 6e The ItemHistoryID_PK for stored scan of staff out of the storage area. 
				stmt="select ItemHistoryID_PK from itemhistory where scanitemtypeID_fk=2 and isstored=1 and "
				+ "locationid_FK=(select LocationID_PK from location where Name='"+StorageRoom+"')";
				
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Storedstaff3StorageOutItemHistID=rs.getString(1);
					}
				System.out.println("Storedstaff3StorageOutItemHistID = "+Storedstaff3StorageOutItemHistID);
				
				
				// step 4 Check that ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains  the AssociationID_FK for scope1 culture obtained step4a.
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=1 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contain  the AssociationID_FK for scope1 culture obtained";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=2 shoult contain  the AssociationID_FK for scope1 culture obtained";
				if(ScopeAssocID.contains(StoredScope1InAssocID)){
					Result="Pass - Scope cycle of Scope1 for Cycle 2 contains the Stored AssociationID '"+StoredScope1InAssocID+"' of Scope1 culuture obtained";
				}else{
					Result="#Failed!# - Scope cycle of Scope1 for Cycle 2 does not contain the Stored AssociationID '"+StoredScope1InAssocID+"' of Scope1 culuture obtained";
				}
				
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);

				ScopeAssocID.clear();
				
				// Step 7a Verify that the associationid_FK for stored scan of scope3 culture result in 6b
				//matches the associationid_fk for scope3 culture obtained noted in step 4c.
								
				Description="Checking associationid_fk for stored scan of scope 3 culture result matches with "
						+ "the associationid_fk for scope3 culture obtained";
				Expected="The associationid_fk for stored scan of scope 3 culture result should match with "
						+ "the associationid_fk for scope3 culture obtained";
				
				if(StoredScope3CultResOutAssocID.equalsIgnoreCase(RealScope3CultObtaAssocID)){
					Result="Pass- The associationid_fk for stored scan of scope 3 culture result '"+StoredScope3CultResOutAssocID +"' matches with "
							+" the associationid_fk for scope3 culture obtained '"+RealScope3CultObtaAssocID+"'";							
				}else{
					Result="#Failed!# - The associationid_fk for stored scan of scope 3 culture result '"+StoredScope3CultResOutAssocID +"' does not match with "
							+" the associationid_fk for scope3 culture obtained '"+RealScope3CultObtaAssocID+"'";
				}
				
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				// Step 7b Verify that the associationid_FK for stored scan of scope3 culture result in 6b
				//does not match the associationid_fk for stored scan of scope3 out of storage area in step 6a.
								
				Description="Checking associationid_fk for stored scan of scope 3 culture result does not match with "
						+ "the associationid_fk for stored scan of scope3 out of storage area";
				Expected="The associationid_fk for stored scan of scope 3 culture result should not match with "
						+ "the associationid_fk for stored scan of scope3 out of storage area";
				
				if(!StoredScope3CultResOutAssocID.equalsIgnoreCase(StoredScope3OutAssocID)){
					Result="Pass- The associationid_fk for stored scan of scope 3 culture result '"+StoredScope3CultResOutAssocID +"' does not match with "
							+" the associationid_fk for stored scan of scope3 out of storage area '"+StoredScope3OutAssocID+"'";							
				}else{
					Result="#Failed!# - The associationid_fk for stored scan of scope 3 culture result '"+StoredScope3CultResOutAssocID +"' matched with "
							+" the associationid_fk for stored scan of scope3 out of storage area '"+StoredScope3OutAssocID+"'";				
				}
				
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					
				// 	step 8 - Check the RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for 
				//stored scan of scope3 out of the storage area noted in step 6c and RelatedItemHisotryID_FK=The ItemhistoryId_PK for stored scan of staff
				//for stored scan of staff for scope3 out of the storage are noted in step 6e
			stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope3OutItemHistID+" and "
				+ "relatedItemHistoryID_FK="+StoredScope3CultResItemHistID;
				System.out.println(stmt);
				Integer rowCnt=0;
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowCnt=rs.getInt(1);
				}
				Description="Checking whether the RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of scope3 out of the storage area and  "
						+ "RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage area";
						
				Expected="The RelatedItem table should have a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of scope3 out of the storage area "
						+ "RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage are";
				if (rowCnt>=1){
					Result="Pass - The RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of "
							+ "scope3 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage area";
				}else{
					Result="#Failed!# - - The RelatedItem table does not has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of "
							+ "scope3 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage area";
				}
				
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				// step 9 Check	The RelatedItem table has a row with ItemHistoryID_FK= 
				//The ItemHistoryID_PK for stored scan of scope3 out of the storage area noted in step 6c 
				//and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff for scope3 out of storage area noted in step 6e
				
				stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope3OutItemHistID+" and "
					+ "relatedItemHistoryID_FK="+Storedstaff3StorageOutItemHistID;
				System.out.println(stmt);
				rowCnt=0;
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowCnt=rs.getInt(1);
				}
				Description="Checking whether the RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of scope3 out of the storage area and  "
						+ "RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage area";
						
				Expected="The RelatedItem table should have a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of scope3 out of the storage area "
						+ "RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage are";
				if (rowCnt>=1){
					Result="Pass - The RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of "
							+ "scope3 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage area";
				}else{
					Result="#Failed!# - - The RelatedItem table does not has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of "
							+ "scope3 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage area";
				}
				
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				//Query 10a
				stmt="select associationid_fk from itemhistory where scanitemid_fk=2 and scanitemtypeID_fk=1 and isstored=1 and CycleEventID_FK=3 "
					+ "and locationid_FK=(select locationid_pk from location where Name='"+ProcRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2InPRAssocID=rs.getString(1);
					}
				System.out.println("StoredScope2InPRAssocID = "+StoredScope2InPRAssocID);
				//Query 10b
				stmt="select associationid_fk from itemhistory where scanitemid_fk=5 and scanitemtypeID_fk=8 and isstored=1 and cycleeventid_fk=33 and "
				+ " locationid_FK=(select locationid_pk from location where Name='"+ProcRoom+"')";
				
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2CulResInPRAssocID=rs.getString(1);
					}
				System.out.println("StoredScope2CulResInPRAssocID = "+StoredScope2CulResInPRAssocID);
				//Query 10c
				stmt="select itemhistoryid_pk from itemhistory where scanitemid_fk=2 and scanitemtypeID_fk=1 and isstored=1 and CycleEventID_FK=3 "
					+ "and locationid_FK=(select locationid_pk from location where Name='"+ProcRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2InPRItemHistID=rs.getString(1);
					}
				System.out.println("StoredScope2InPRItemHistID = "+StoredScope2InPRItemHistID);
				
				//Query 10d
				stmt="select itemhistoryid_pk,* from itemhistory where scanitemid_fk=5 and scanitemtypeID_fk=8 and isstored=1 and cycleeventid_fk=33 and "
				+ " locationid_FK=(select locationid_pk from location where Name='"+ProcRoom+"')";
				
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2CulResInPRItemHistID=rs.getString(1);
					}
				System.out.println("StoredScope2CulResInPRAssocID = "+StoredScope2CulResInPRItemHistID);
				
				//Query 10e
				stmt="select itemhistoryid_pk from itemhistory where scanitemtypeID_fk=2 and  isstored=1 and "
				+ "associationid_fk="+StoredScope2InPRAssocID+" and locationid_FK=(select LocationID_PK from location where Name='"+ProcRoom+"')";
				
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredStaffInPRItemHistID=rs.getString(1);
					break;
					}
				System.out.println("StoredStaffInPRItemHistID = "+StoredStaffInPRItemHistID);
				
				//Step11
				stmt="select count(*) from relatedItem where itemhistoryid_FK="+StoredScope2InPRItemHistID+" and "
				+"RelatedItemHistoryID_FK="+StoredScope2CulResInPRItemHistID;
				
				System.out.println(stmt);
				rowCnt=0;
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowCnt=rs.getInt(1);
				}
				Description="Checking whether the RelatedItem table has a row with ItemHistoryID_FK=  The ItemHistoryID_PK for "
						+ "stored scan of  scope2 into PR3 noted in step 10c and RelatedItemHistoryID_FK=The ItemHistoryID_PK for "
						+ "stored scan culture results for scope 2 after scanning the scope into PR3 noted in step 10d";
						
				Expected="The RelatedItem table shuld have a row with ItemHistoryID_FK=  The ItemHistoryID_PK for "
						+ "stored scan of  scope2 into PR3 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for "
						+ "stored scan culture results for scope 2 after scanning the scope into PR3";
				if (rowCnt>=1){
					Result="Pass - The RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of "
							+ "scope3 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage area ";
				}else{
					Result="#Failed!# - - The RelatedItem table does not has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of "
							+ "scope3 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan culture results for scope3 after scanning scope out of the storage area ";
				}
				
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				//Step12
				stmt="select count(*) from relatedItem where itemhistoryid_FK="+StoredScope2InPRItemHistID+" and "
				+"RelatedItemHistoryID_FK="+StoredStaffInPRItemHistID;
				
				System.out.println(stmt);
				rowCnt=0;
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					rowCnt=rs.getInt(1);
				}
				
				Description="Checking whether the RelatedItem table has a row with ItemHistoryID_FK=  "
						+ "The ItemHistoryID_PK for stored scan of scope2 into PR3 noted in step 10c and "
						+ "RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff for scope2 "
						+ "into PR3 noted in step 10e.";
						
				Expected="The RelatedItem table should have a row with ItemHistoryID_FK=  "
						+ "The ItemHistoryID_PK for stored scan of scope2 into PR3 and "
						+ "RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff for scope2 "
						+ "into PR3";
				if (rowCnt>=1){
					Result="Pass - The RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for "
							+ "stored scan of scope2 into PR3 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for "
							+ "stored scan of staff for scope2 into PR3";
				}else{
					Result="#Failed!# - The RelatedItem table does not have a row with ItemHistoryID_FK= The ItemHistoryID_PK for "
							+ "stored scan of scope2 into PR3 and RelatedItemHistoryID_FK=The ItemHistoryID_PK for "
							+ "stored scan of staff for scope2 into PR3";
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
				
				//Checking the ReprocessingCount for Scope2
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
				
				//closing record set
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
