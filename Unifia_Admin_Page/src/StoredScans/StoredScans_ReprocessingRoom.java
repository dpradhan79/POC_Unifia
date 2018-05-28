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


public class StoredScans_ReprocessingRoom {
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

	private String Reprocessor="Reprocessor 1";
	private String StoredScope1InAssocID;//The associationID_FK for stored scan of scope1 into the reprocessor.
	private String StoredScope2InAssocID;//The associationID_FK for stored scan of scope2 into the reprocessor.
	private String StoredMRCAssocID;//The associationID_FK for stored scan of MRC Pass for the reprocessor.
	private String StoredScope1OutAssocID;//The associationID_FK for stored scan of scope1 out of the reprocessor.
	private String StoredScope2OutAssocID;//The associationID_FK for stored scan of scope2 out of the reprocessor.
	private int ScanItem;
	private String Barcode;
	
	private int KE=0;
	private int Bioburden=0;
	private int Culture=0;
	
	private String actualResult="\t\t\t StoredScans_ReprocessingRoom_TestSummary \r\n\r\n"; 
	private String ForFileName;
	private String TestResFileName="StoredScans_ReprocessingRoom_TestSummary_";
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
	private int expectedScope1StaffID=24;
	private int expectedScope2StaffID=24;
	private int expectedScope3StaffID=27;
	
	private List<String> ScopeAssocID=new ArrayList<String>();
	
	private String File="ScenarioInitialScans_Reprocessing_StoredScans.xml";
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Reprocessing_StoredScans(String browserP, String URL, String AdminDB) throws Exception {
		if (UAS.parallelExecutionType && UAS.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		FileName="StoredScans_ReprocessingRoom_";
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
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=1 and IH.CycleEventID_FK=15 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Reprocessor+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope1InAssocID=rs.getString(1);
				}
				System.out.println("StoredScope1InAssocID = "+StoredScope1InAssocID);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=15 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Reprocessor+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2InAssocID=rs.getString(1);
				}
				System.out.println("StoredScope2InAssocID = "+StoredScope2InAssocID);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=8 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Reprocessor+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredMRCAssocID=rs.getString(1);
				}
				System.out.println("StoredMRCAssocID = "+StoredMRCAssocID);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=1 and IH.CycleEventID_FK=18 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Reprocessor+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope1OutAssocID=rs.getString(1);
				}
				System.out.println("StoredScope1OutAssocID = "+StoredScope1OutAssocID);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=18 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Reprocessor+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2OutAssocID=rs.getString(1);
				}
				System.out.println("StoredScope2OutAssocID = "+StoredScope2OutAssocID);
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=1 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for scope1 into the reprocessor
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for scope1 into the reprocessor";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=2 should contain the AssociationID_FK for scope1 into the reprocessor";
				if(ScopeAssocID.contains(StoredScope1InAssocID)){
					Result="Pass- Scope cycle of Scope1 for Cycle 2 contains the Stored AssociationID("+StoredScope1InAssocID+") of Scope1 into Reprocessor";
					System.out.println(Result);
				}else{
					Result="#Failed!# - Scope cycle of Scope1 for Cycle 2 does not contain the Stored AssociationID("+StoredScope1InAssocID+") of Scope1 into Reprocessor";
					System.out.println(Result);
				}
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for MRC Pass for the reprocessor
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for MRC Pass for the reprocessor";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=2 should contain the AssociationID_FK for MRC Pass for the reprocessor";
				if(ScopeAssocID.contains(StoredMRCAssocID)){
					Result="Pass- Scope cycle of Scope1 for Cycle 2 contains the Stored AssociationID("+StoredMRCAssocID+") of MRC Test Result";
					System.out.println(Result);
				}else{
					Result="#Failed!# - Scope cycle of Scope1 for Cycle 2 does not contain the Stored AssociationID("+StoredMRCAssocID+") of MRC Test Result";
					System.out.println(Result);
				}
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
			
				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for scope1 out of the reprocessor 
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for scope1 out of the reprocessor";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=2 should contain the AssociationID_FK for scope1 out of the reprocessor";
				if(ScopeAssocID.contains(StoredScope1OutAssocID)){
					Result="Pass- Scope cycle of Scope1 for Cycle 2 contains the Stored AssociationID("+StoredScope1OutAssocID+") of Scope out of Reprocessor";
					System.out.println(Result);
				}else{
					Result="#Failed!# - Scope cycle of Scope1 for Cycle 2 does not contain the Stored AssociationID("+StoredScope1OutAssocID+") of Scope out of Reprocessor";
					System.out.println(Result);
				}
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
			
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=2 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				//Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK for scope2 into the reprocessor
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK for scope2 into the reprocessor";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=2 should contain the AssociationID_FK for scope2 into the reprocessor";
				if(ScopeAssocID.contains(StoredScope2InAssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 2 contains the Stored AssociationID("+StoredScope2InAssocID+") of Scope into Reprocessor";
					System.out.println(Result);
				}else{
					Result="#Failed!# - Scope cycle of Scope2 for Cycle 2 does not contain the Stored AssociationID("+StoredScope2InAssocID+") of Scope into Reprocessor";
					System.out.println(Result);
				}
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
			
				//Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK for MRC Pass for the reprocessor
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK for MRC Pass for the reprocessor";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=2 should contain the AssociationID_FK for MRC Pass for the reprocessor";
				if(ScopeAssocID.contains(StoredMRCAssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 2 contains the Stored AssociationID("+StoredMRCAssocID+") of MRC Test Result";
					System.out.println(Result);
				}else{
					Result="#Failed!# - Scope cycle of Scope2 for Cycle 2 does not contain the Stored AssociationID("+StoredMRCAssocID+") of MRC Test Result";
					System.out.println(Result);
				}
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
			
				//Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK for scope2 out of the reprocessor
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK for scope2 out of the reprocessor";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=2 should contain the AssociationID_FK for scope2 out of the reprocessor";
				if(ScopeAssocID.contains(StoredScope2OutAssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 2 contains the Stored AssociationID("+StoredScope2OutAssocID+") of Scope out of Reprocessor";
					System.out.println(Result);
				}else{
					Result="#Failed!# - Scope cycle of Scope2 for Cycle 2 does not contain the Stored AssociationID("+StoredScope2OutAssocID+") of Scope out of Reprocessor";
					System.out.println(Result);
				}
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.CycleEventID_FK=37 and IsStored=0";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				//Checking whether ItemHistory table for CycleEventID_FK=37 and IsStored=0 contains AssociationID_FK for Scope1 into the reprocessor1
				Description="Checking whether ItemHistory table for CycleEventID_FK=37 and IsStored=0 contains AssociationID_FK for Scope1 into the reprocessor1";
				Expected="ItemHistory table for CycleEventID_FK=37 and IsStored=0 should contain AssociationID_FK for Scope1 into the reprocessor1";
				if(ScopeAssocID.contains(StoredScope1InAssocID)){
					Result="Pass- ItemHistory table contains the StoredScope1InAssocID("+StoredScope1InAssocID+") when queried with CycleEvent=37 and IsStored=0";
					System.out.println(Result);
					actualResult=actualResult+"\r\n\r\n"+Result;
					TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
					IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					
					stmt="select IH.ScanItemID_FK from ItemHistory IH where IH.CycleEventID_FK=37 and IsStored=0 and IH.AssociationID_FK="+StoredScope1InAssocID;
					System.out.println(stmt);
					rs=statement.executeQuery(stmt);
					while(rs.next()){
						ScanItem=rs.getInt(1);
					}
					
					//Checking whether ItemHistory table for CycleEventID_FK=37 and IsStored=1 and AssociationID_FK = the value that is calculated above contains the ScanItemID_FK=4.
					Description="Checking whether ItemHistory table for CycleEventID_FK=37 and IsStored=1 and AssociationID_FK = AssociationID_FK for Scope1 into the reprocessor1 contains the ScanItemID_FK=4.";
					Expected="ScanItemID_FK should be 4 in ItemHistory table for CycleEventID_FK=37 and IsStored=0 and AssociationID_FK = AssociationID_FK for Scope1 into the reprocessor1";
					if(ScanItem==4){
						Result="Pass- The ScanItem Value is '4' when queried with CycleEvent=37, IsStored=0 and AssociationID="+StoredScope1InAssocID;
						System.out.println(Result);
						actualResult=actualResult+"\r\n\r\n"+Result;
						TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
						IHV.Exec_Log_Result(FileName, Description, Expected, Result);
						
						stmt="select Name from Barcode where BarcodeID_PK="+ScanItem;
						System.out.println(stmt);
						rs=statement.executeQuery(stmt);
						while(rs.next()){
							Barcode=rs.getString(1);
						}
						
						//Checking whether the Reason for Reprocessing is 'Used in Procedure'
						Description="Checking whether the Reason for Reprocessing is 'Used in Procedure'";
						Expected="The Reason for Reprocessing should be 'Used in Procedure'";
						if(Barcode.equalsIgnoreCase("Used in Procedure")){
							Result="Pass- The Reason for Reprocessing is 'Used in Procedure'";
							System.out.println(Result);
						}else{
							Result="#Failed!# - The Reason for Reprocessing is Expected to be 'Used in Procedure'. However it is '"+Barcode+"'";
							System.out.println(Result);
						}
						actualResult=actualResult+"\r\n\r\n"+Result;
						TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
						IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					
					}else{
						Result="#Failed!# - The ScanItem Value is Expected to be '4' when queried with CycleEvent=37, IsStored=1 and AssociationID="+StoredScope1InAssocID+". However the value is '"+ScanItem+"'";
						System.out.println(Result);
						actualResult=actualResult+"\r\n\r\n"+Result;
						TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
						IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					}
				}else{
					Result="#Failed!# - ItemHistory table does not contain the StoredScope1InAssocID("+StoredScope1InAssocID+") when queried with CycleEvent=37 and IsStored=0";
					System.out.println(Result);
					actualResult=actualResult+"\r\n\r\n"+Result;
					TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
					IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				}
				
				//Checking whether ItemHistory table for CycleEventID_FK=37 and IsStored=0 contains AssociationID_FK for Scope2 into the reprocessor1
				Description="Checking whether ItemHistory table for CycleEventID_FK=37 and IsStored=1 contains AssociationID_FK for Scope2 into the reprocessor1";
				Expected="ItemHistory table for CycleEventID_FK=37 and IsStored=0 should contain AssociationID_FK for Scope1 into the reprocessor1";
				if(ScopeAssocID.contains(StoredScope2InAssocID)){
					Result="Pass- ItemHistory table contains the StoredScope2InAssocID("+StoredScope2InAssocID+") when queried with CycleEvent=37 and IsStored=0";
					System.out.println(Result);
					actualResult=actualResult+"\r\n\r\n"+Result;
					TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
					IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					
					stmt="select IH.ScanItemID_FK from ItemHistory IH where IH.CycleEventID_FK=37 and IsStored=1 and IH.AssociationID_FK="+StoredScope2InAssocID;
					System.out.println(stmt);
					rs=statement.executeQuery(stmt);
					while(rs.next()){
						ScanItem=rs.getInt(1);
					}
					
					//Checking whether ItemHistory table for CycleEventID_FK=37 and IsStored=1 and AssociationID_FK = AssociationID_FK for Scope2 into the reprocessor1 contains the ScanItemID_FK=4.
					Description="Checking whether ItemHistory table for CycleEventID_FK=37 and IsStored=1 and AssociationID_FK = AssociationID_FK for Scope2 into the reprocessor1 contains the ScanItemID_FK=4.";
					Expected="ScanItemID_FK should be 4 in ItemHistory table for CycleEventID_FK=37 and IsStored=0 and AssociationID_FK = AssociationID_FK for Scope2 into the reprocessor1";
					if(ScanItem==4){
						Result="Pass- The ScanItem Value is '4' when queried with CycleEvent=37, IsStored=1 and AssociationID="+StoredScope2InAssocID;
						System.out.println(Result);
						actualResult=actualResult+"\r\n\r\n"+Result;
						TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
						IHV.Exec_Log_Result(FileName, Description, Expected, Result);
						
						stmt="select Name from Barcode where BarcodeID_PK="+ScanItem;
						System.out.println(stmt);
						rs=statement.executeQuery(stmt);
						while(rs.next()){
							Barcode=rs.getString(1);
						}
						
						//Checking whether Barcode table for BarcodeID_Pk=4 contains name "Used in Procedure"
						Description="Checking whether the Reason for Reprocessing is 'Used in Procedure'";
						Expected="The Reason for Reprocessing should be 'Used in Procedure'";
						if(Barcode.equalsIgnoreCase("Used in Procedure")){
							Result="Pass- The Reason for Reprocessing is 'Used in Procedure'";
							System.out.println(Result);
						}else{
							Result="#Failed!# - The Reason for Reprocessing is Expected to be 'Used in Procedure'. However it is '"+Barcode+"'";
							System.out.println(Result);
						}
						actualResult=actualResult+"\r\n\r\n"+Result;
						TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
						IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					
					}else{
						Result="#Failed!# - The ScanItem Value is Expected to be '4' when queried with CycleEvent=37, IsStored=1 and AssociationID="+StoredScope2InAssocID+". However the value is '"+ScanItem+"'";
						System.out.println(Result);
						actualResult=actualResult+"\r\n\r\n"+Result;
						TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
						IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					}
				}else{
					Result="#Failed!# - ItemHistory table does not contain the StoredScope2InAssocID("+StoredScope2InAssocID+") when queried with CycleEvent=37 and IsStored=0";
					System.out.println(Result);
					actualResult=actualResult+"\r\n\r\n"+Result;
					TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
					IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				}
				
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
