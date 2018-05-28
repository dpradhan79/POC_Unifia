
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

public class StoredScans_StorageArea {
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

	private String Storage="Storage Area A";
	
	private String StoredScope1InAssocID;//The associationID_FK for stored scan of scope1 into the Storage Area.
	private String RealScope2InAssocID;//The associationID_FK for real time scan of scope2 into the Storage Area.
	private String StoredScope2OutAssocID; // The associationID_FK for stored scan of scope2 out of the Storage Area.
	private String RealScope1OutAssocID; //The associationID_FK for real time scan of scope1 out of the Storage Area.
	private String StoredScope2ItemHistID;//The ItemHistoryID_PK for stored scan of scope2 out of the storage area
	private String StoredStaffItemHistID; //The ItemHistoryID_PK for stored scan of staff out of the storage area.
	
	private int KE=0;
	private int Bioburden=0;
	private int Culture=0;
	
	private String actualResult="\t\t\t StoredScans_StorageArea_TestSummary \r\n\r\n"; 
	private String ForFileName;
	private String TestResFileName="StoredScans_StorageArea_TestSummary_";
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
	private int scope1LastScanStaffID_FK;
	private int scope2LastScanStaffID_FK;
	private int expectedScope1StaffID=33;
	private int expectedScope2StaffID=25;
	
	private List<String> ScopeAssocID=new ArrayList<String>();
	private List<String> SubLocationId=new ArrayList<String>();
	private boolean IsNULL=false;
	
	private String File="Scenario_Initialize_Stored_StorageArea.xml";
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
		FileName="StoredScans_StorageArea_";
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
				//-- Query 1 The associationID_FK for stored scan of scope1 into the Storage Area. (StoredScope1InAssocID)
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=1 and IH.CycleEventID_FK=1 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Storage+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope1InAssocID=rs.getString(1);
				}
				System.out.println("StoredScope1InAssocID = "+StoredScope1InAssocID);
				
				//-- Query 2 The associationID_FK for real time scan of scope2 into the Storage Area. (RealScope2InAssocID)
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=1 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Storage+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					RealScope2InAssocID=rs.getString(1);
				}
				System.out.println("RealScope2InAssocID = "+RealScope2InAssocID);
				
				//-- Query 3 The associationID_FK for stored scan of scope2 out of the Storage Area.  (StoredScope2OutAssocID)
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=2 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Storage+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2OutAssocID=rs.getString(1);
				}
				System.out.println("StoredScope2OutAssocID = "+StoredScope2OutAssocID);
				
				//-- Query 4 The associationID_FK for real time scan of scope1 out of the Storage Area. (RealScope1OutAssocID)
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=1 and IH.CycleEventID_FK=2 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Storage+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					RealScope1OutAssocID=rs.getString(1);
				}
				System.out.println("RealScope1OutAssocID = "+RealScope1OutAssocID);
				
				//-- Query 5 The ItemHistoryID_PK for stored scan of scope2 out of the storage area (StoredScope2ItemHistID)
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=2 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Storage+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredScope2ItemHistID=rs.getString(1);
				}
				System.out.println("StoredScope2ItemHistID = "+StoredScope2ItemHistID);
				
				// -- Query 6 The ItemHistoryID_PK for stored scan of staff out of the storage area. (StoredStaffItemHistID)
				stmt="select IH.ItemHistoryID_PK from ItemHistory IH where IH.IsStored=1 and IH.ScanItemTypeID_FK=2 and IH.AssociationID_FK = (select AssociationID_FK from ItemHistory where ScanItemTypeID_FK=1 and ScanItemID_FK=2 and IsStored=1 and LocationID_FK=(select LocationID_PK from Location where Name='"+Storage+"'))";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					StoredStaffItemHistID=rs.getString(1);
					}
				System.out.println("StoredStaffItemHistID = "+StoredStaffItemHistID);
				
				// step 3 Check that ScopeCycle table for ScopeID_FK=1 and CycleID=1 does NOT contain  the AssociationID_FK for scope1 into the storage area noted in step2a.
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=1 and CycleID=1";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=1 does NOT contain  the AssociationID_FK for scope1 into the storage area";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=1 shoult NOT contain  the AssociationID_FK for scope1 into the storage area";
				if(!ScopeAssocID.contains(StoredScope1InAssocID)){
					Result="Pass- Scope cycle of Scope1 for Cycle 1 does not contains the Stored AssociationID("+StoredScope1InAssocID+") of Scope1 into storage area";
				}else{
					Result="#Failed!# - Scope cycle of Scope1 for Cycle 1 does not contain the Stored AssociationID("+StoredScope1InAssocID+") of Scope1 into storage area";
				}
				
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);

				ScopeAssocID.clear();
				
				// step 4 Check that the ScopeCycle table for ScopeID_FK=1 and CycleID=2 does NOT contain the AssociationID_FK for scope1 out of the storage area noted in step2d.
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=1 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 does NOT contain the AssociationID_FK for scope1 out of the storage area";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=2 should NOT contain the AssociationID_FK for scope1 out of the storage area";
				if(!ScopeAssocID.contains(RealScope1OutAssocID)){
					Result="Pass- Scope cycle of Scope1 for Cycle 2 does not contain the AssociationID("+RealScope1OutAssocID+") of Scope1 out of storage area";
					
				}else{
					Result="#Failed!# - Scope cycle of Scope1 for Cycle 2 contain the AssociationID("+RealScope1OutAssocID+") of Scope1 out of storage area";
					
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				ScopeAssocID.clear();
				//step 5 The ScopeCycle table for ScopeID_FK=2 and CycleID=1 does NOT contain the AssociationID_FK for scope2  out of the storage area noted in step2c.
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=2 and CycleID=1";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=1 does NOT contain the AssociationID_FK for scope2  out of the storage area";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=1 should NOT contain the AssociationID_FK for scope2  out of the storage area";
				if(!ScopeAssocID.contains(StoredScope2OutAssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 1 does not contain the Stored AssociationID("+StoredScope2OutAssocID+") of Scope out of storage area";
					
				}else{
					Result="#Failed!# - Scope cycle of Scope2 for Cycle 1 does not contain the Stored AssociationID("+StoredScope2OutAssocID+") of Scope out of storage area";
					}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				ScopeAssocID.clear();
				// step 6 The ScopeCycle table for ScopeID_FK=2 and CycleID=1 does NOT contain  the AssociationID_FK for scope2 into the storage area noted in step2b.
				
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=1 does NOT contain  the AssociationID_FK for scope2 into the storage area";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=1 should NOT contain  the AssociationID_FK for scope2 into the storage area noted";
				if(!ScopeAssocID.contains(RealScope2InAssocID)){
				Result="Pass- Scope cycle of Scope2 for Cycle 1 does not contain the  AssociationID("+RealScope2InAssocID+") of Scope into Reprocessor";
				}else{
					Result="#Failed!# - Scope cycle of Scope2 for Cycle 1 contain the AssociationID("+RealScope2InAssocID+") of Scope into Reprocessor";	
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					
				ScopeAssocID.clear();
			// step 7 Check	The RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of scope2 out of the storage area noted in step 2e 
			//	and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T05 for scope1 out of the storage area noted in step 2f
				
				stmt="select count(*) from RelatedItem where ItemHistoryID_FK="+StoredScope2ItemHistID+" and "
					+ "relatedItemHistoryID_FK="+StoredStaffItemHistID;
					System.out.println(stmt);
					Integer rowCnt=0;
					rs=statement.executeQuery(stmt);
					while(rs.next()){
						rowCnt=rs.getInt(1);
					}
					
					Description="Checking whether the RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of scope2 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T05 for scope1 out of the storage area";
					Expected="The RelatedItem table should have a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of scope2 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for stored scan of staff T05 for scope1 out of the storage area";
					if (rowCnt>=1){
						Result="Pass- The RelatedItem table has a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of"
								+ "scope2 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for"
								+ " stored scan of staff T05 for scope1 out of the storage area";
					}else{
						Result="#Failed!# - The RelatedItem table does not have a row with ItemHistoryID_FK= The ItemHistoryID_PK for stored scan of"
						+ "scope2 out of the storage area and RelatedItemHistoryID_FK=The ItemHistoryID_PK for"
						+ " stored scan of staff T05 for scope1 out of the storage area";
					}
					
					System.out.println(Result);
					actualResult=actualResult+"\r\n\r\n"+Result;
					TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
					IHV.Exec_Log_Result(FileName, Description, Expected, Result);
					
				// step 8 Open the ScopeStatus table and verify that the  SubLocationID field for Scope1 and 2 is = to null
					Description="Checking whether the ScopeStatus table has SubLocationID value equal to null for Scope1 and Scope2";
					Expected="The ScopeStatus table should have the SubLocationID value equal to null for Scope1 and Scope2";
					stmt="select SubLocationID from ScopeStatus"; 
					System.out.println(stmt);
					rs=statement.executeQuery(stmt);
					while(rs.next()){
						SubLocationId.add(rs.getString(1));
					}
						
					Iterator itr=SubLocationId.iterator();
					while(itr.hasNext()){
						if(!(itr.next()==null)){
							IsNULL=false;
							break;
						}else{
							IsNULL=true;
						}
					}
					if(IsNULL){
						Result="Pass- SubLocationID field for Scope1 and 2 is ("+SubLocationId+") ";
					}else{
						Result="#Failed!# - SubLocationID field for Scope1 and 2 contains ("+SubLocationId+") ";
					}
					SubLocationId.clear();
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
