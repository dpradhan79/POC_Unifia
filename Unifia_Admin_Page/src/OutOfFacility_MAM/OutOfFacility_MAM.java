package OutOfFacility_MAM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class OutOfFacility_MAM {
	
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	public QV_GeneralFunc QV_Gen;
	public GeneralFunc gf;
	private TestDataFunc TDF;
	private ITConsole.ITConScanSimActions IT_A;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.TestHelper TH;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	
	public int KE=0;
	public int Bioburden=1;
	public int Culture=0;
	private String result="";
	private String TestSummary= "\t\t\t OutOfFacility_TestSummary \r\n\r\n"; 
	private String ResFileName="OutOfFacility_TestSummary";
	private String ResFileNameXML="OutOfFacility_Result";
	private String TestRes;
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
	private String XMLFile="OutofFacilityScanData.xml";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws Exception {
    	//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    	
    	//Inserting Master Data
		gf.SyncRemoteMachineTime(UAS.KE_Env, UAS.KEMachine_Username, UAS.KEMachine_pswd, URL);
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
    	TDF.insertMultiFacilityMasterData(UAS.url, UAS.user, UAS.pass, KE, Bioburden, Culture);
		gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
		
		//Launching Unifia
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(2000);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, UAS.roleMgr, UAS.userQV1, UAS.userQV1Pwd,"FAC2","FAC3");
		
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Out of Facility");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		Unifia_Admin_Selenium.resultFlag="Pass";
		String Expected,Description;
		
		MAM_A.Click_MaterialsAndAssetManagement();
		Thread.sleep(2000);
		
		String OOFTableContent=UAS.driver.findElementByXPath("//*[@id='gridOutOfFacility']/div[2]/table/tbody/tr[1]/td").getText();
		System.out.println("Content shown in Out of Facility table after inserting Master Data '"+OOFTableContent+"'");
		Map<String, String> actual_OOF_Values=get_OFF_LocationAndCount();
		if(actual_OOF_Values.size()==0 && OOFTableContent.equalsIgnoreCase("Not found")){
			result="Pass - There are no scopes scanned out of Facility";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - There are scopes that are scanned out of Facility";
		}
		System.out.println(result);
		Description="Verifying that Out Of Facility table shows Not found before scanning scope to Out of Facility";
		Expected="Out Of Facility table should show Not found before scanning scope to Out of Facility";
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		//Step -1
		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
		
		//Copying XMLFile
		boolean isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, XMLFile,fileSource,fileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+XMLFile+" is copied to "+fileDestination);
		}else{
			System.out.println("#Failed!#- "+XMLFile+" is not copied to "+fileDestination);
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
		
		Map<String, String> expected_OOF_Values=new HashMap<String, String>();
		expected_OOF_Values.put("Olympus Repair", "5");
		expected_OOF_Values.put("Unknown", "3");
		expected_OOF_Values.put("Repair shop 1", "1");
		expected_OOF_Values.put("Facility 4", "3");
		expected_OOF_Values.put("Repair shop 2", "3");
		System.out.println("Expected OOF Locations and their count");
		for (Map.Entry<String, String> oof_LocationDetails : actual_OOF_Values.entrySet()) {
			System.out.println(oof_LocationDetails.getKey()+"=="+oof_LocationDetails.getValue());
		}
		
		UAS.driver.navigate().refresh();
		Thread.sleep(3000);
		actual_OOF_Values.clear();
		actual_OOF_Values=get_OFF_LocationAndCount();
		System.out.println("Actual OOF Locations and their count");
		for (Map.Entry<String, String> oof_LocationDetails : actual_OOF_Values.entrySet()) {
			System.out.println(oof_LocationDetails.getKey()+"=="+oof_LocationDetails.getValue());
		}
		
		//Checking whether Expected OOF locations with their count is equal to the Actual Location and their count on the UI
		if(actual_OOF_Values.equals(expected_OOF_Values)){
			result="Pass - Expected OOF locations with their count is equal to the Actual Location and their count on the UI";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Expected OOF locations with their count is not equal to the Actual Location and their count on the UI";
		}
		System.out.println(""+result);
		Description="Checking whether Expected OOF locations with their count is equal to the Actual Location and their count on the UI";
		Expected="Expected OOF locations with their count should be equal to the Actual Location and their count on the UI";
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		Statement statement= conn.createStatement(); 
		String stmt=null;
		String scopeName=null;
		boolean isUnknownOOFPresent=false;
		String oofLoc = "";
		String oof_AssociationIDs="";
		String scopeSerialNum="";
		String scopeModel="";
		int oofLocationCount=0;
		int expectedTotalOOFScopesCount=0;
		for (Map.Entry<String, String> oof_LocationDetails : actual_OOF_Values.entrySet()) {
			oofLoc=oof_LocationDetails.getKey();
			oofLocationCount=0;
			if(!oofLoc.equalsIgnoreCase("Unknown")){
				Map<String, String> ScopeName_Count=new HashMap<String, String>();
				stmt="select AssociationID_FK from itemhistory where ScanItemTypeID_FK=12 and ScanItemID_FK=(select BarcodeID_PK from Barcode where name='"+oofLoc+"');";
				ResultSet RS = statement.executeQuery(stmt);
				String AssociationIDs="";
				while(RS.next()){
					AssociationIDs+=RS.getString(1)+",";
				}
				RS.close();
				oof_AssociationIDs+=AssociationIDs;
				for(int j=0;j<AssociationIDs.split(",").length;j++){
					stmt="select name from Scope s join ItemHistory ih on ih.ScanItemID_FK=s.ScopeID_PK and ih.ScanItemTypeID_FK=1 and ih.AssociationID_FK="+AssociationIDs.split(",")[j];
					RS = statement.executeQuery(stmt);
					while(RS.next()){
						scopeName=RS.getString(1);
					}
					RS.close();
					if(ScopeName_Count.containsKey(scopeName)){
						int ScopeCount=Integer.parseInt(ScopeName_Count.get(scopeName));
						ScopeName_Count.put(scopeName, String.valueOf(ScopeCount+1));
					}else{
						ScopeName_Count.put(scopeName, "1");
					}
				}
				click_OOFLocation(oofLoc); //Expanding OOF Location on the UI
				for (Map.Entry<String, String> scope_details : ScopeName_Count.entrySet()) {
					stmt="select s.SerialNumber, st.Name from Scope s join ScopeType ST on st.ScopeTypeID_PK=s.ScopeTypeID_FK where s.Name='"+scope_details.getKey()+"'";
					System.out.println("stmt="+stmt);
					RS = statement.executeQuery(stmt);
					while(RS.next()){
						scopeSerialNum=RS.getString(1);
						scopeModel=RS.getString(2);
					}
					RS.close();
					oofLocationCount+=Integer.parseInt(scope_details.getValue());
					String colsNvalues="Scope Name=="+scope_details.getKey()+";Scope Serial Number=="+scopeSerialNum+";Scope Model=="+scopeModel+";Count=="+scope_details.getValue();
					System.out.println("Verifying Out of Facility table for "+colsNvalues);
				    StringBuffer resultDrillDown=gf.verifyTableContents(getOOFLocationTableXpath(oofLoc),colsNvalues,"Scope Name");
					System.out.println("result="+resultDrillDown.toString());
					Description="Verifying the Out of Facility table that "+scope_details.getKey()+" should be present in "+oofLoc+" with count "+scope_details.getValue();
					Expected=scope_details.getKey()+" should be present in "+oofLoc+" with count "+scope_details.getValue();
					TestRes=Description+":\r\n\t"+result+"\r\n";
					TestSummary=TestSummary+TestRes+"\r\n";
					IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
					
				}
				
				click_OOFLocation(oofLoc); //minimizing OOF Location on the UI
				expectedTotalOOFScopesCount+=oofLocationCount;
				//Verifying the scopes count for each OOF Location
				Description="Verifying the scopes count for "+oofLoc;
				Expected="The scopes count for "+oofLoc+" should be "+expected_OOF_Values.get(oofLoc);
				if(String.valueOf(oofLocationCount).equalsIgnoreCase(expected_OOF_Values.get(oofLoc))){
					result="Pass - The scopes count for "+oofLoc+" matched with the expected value = "+expected_OOF_Values.get(oofLoc);
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!# - The scopes count for "+oofLoc+" is expected to be "+expected_OOF_Values.get(oofLoc)+" but, however it was "+oofLocationCount;
				}
				System.out.println(result);
				TestRes=Description+":\r\n\t"+result+"\r\n";
				TestSummary=TestSummary+TestRes+"\r\n";
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			}else{
				isUnknownOOFPresent=true;
			}
		}
		if(isUnknownOOFPresent){
			oofLocationCount=0;
			oofLoc="Unknown";
			Map<String, String> ScopeName_Count=new HashMap<String, String>();
			stmt="select AssociationID_FK from ItemHistory where CycleEventID_FK=21 and AssociationID_FK Not in("+oof_AssociationIDs.substring(0, oof_AssociationIDs.length()-1)+");"; //eliminating the last comma in the AssociationID's list
			System.out.println(stmt);
			ResultSet RS = statement.executeQuery(stmt);
			String AssociationIDs="";
			while(RS.next()){
				AssociationIDs+=RS.getString(1)+",";
			}
			RS.close();
			for(int j=0;j<AssociationIDs.split(",").length;j++){
				stmt="select name from Scope s join ItemHistory ih on ih.ScanItemID_FK=s.ScopeID_PK and ih.ScanItemTypeID_FK=1 and ih.AssociationID_FK="+AssociationIDs.split(",")[j];
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					scopeName=RS.getString(1);
				}
				RS.close();
				if(ScopeName_Count.containsKey(scopeName)){
					int ScopeCount=Integer.parseInt(ScopeName_Count.get(scopeName));
					ScopeName_Count.put(scopeName, String.valueOf(ScopeCount+1));
				}else{
					ScopeName_Count.put(scopeName, "1");
				}
			}
			
			click_OOFLocation(oofLoc);//Expanding OOF Location on the UI
			for (Map.Entry<String, String> scope_details : ScopeName_Count.entrySet()) {
				stmt="select s.SerialNumber, st.Name from Scope s join ScopeType ST on st.ScopeTypeID_PK=s.ScopeTypeID_FK where s.Name='"+scope_details.getKey()+"'";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					scopeSerialNum=RS.getString(1);
					scopeModel=RS.getString(2);
				}
				RS.close();
				oofLocationCount+=Integer.parseInt(scope_details.getValue());
				String colsNvalues="Scope Name=="+scope_details.getKey()+";Scope Serial Number=="+scopeSerialNum+";Scope Model=="+scopeModel+";Count=="+scope_details.getValue();
				System.out.println("Verifying Out of Facility table for "+colsNvalues);
			    StringBuffer resultDrillDown=gf.verifyTableContents(getOOFLocationTableXpath(oofLoc),colsNvalues,"Scope Name");
				System.out.println("result="+resultDrillDown.toString());
				Description="Verifying the Out of Facility table that "+scope_details.getKey()+" should be present in "+oofLoc+" with count "+scope_details.getValue();
				Expected=scope_details.getKey()+" should be present in "+oofLoc+" with count "+scope_details.getValue();
				TestRes=Description+":\r\n\t"+result+"\r\n";
				TestSummary=TestSummary+TestRes+"\r\n";
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			}
			click_OOFLocation(oofLoc); //minimizing OOF Location on the UI
			expectedTotalOOFScopesCount+=oofLocationCount;
			//Verifying the scopes count for Unknown OOF Location
			Description="Verifying the scopes count for "+oofLoc;
			Expected="The scopes count for "+oofLoc+" should be "+expected_OOF_Values.get(oofLoc);
			if(String.valueOf(oofLocationCount).equalsIgnoreCase(expected_OOF_Values.get(oofLoc))){
				result="Pass - The scopes count for "+oofLoc+" matched with the expected value = "+expected_OOF_Values.get(oofLoc);
			}else{
				UAS.resultFlag="#Failed!#";
				result="#Failed!# - The scopes count for "+oofLoc+" is expected to be "+expected_OOF_Values.get(oofLoc)+" but, however it was "+oofLocationCount;
			}
			System.out.println(result);
			TestRes=Description+":\r\n\t"+result+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		}
		
		Description="Verifying the total scopes count that were scanned Out of Facility";
		Expected="The total scopes count that were scanned out of facility should be "+expectedTotalOOFScopesCount;
		String actualTotalOOFScopesCount=UAS.driver.findElementByXPath("//*[@id='gridOutOfFacility']/div[2]/table/tbody/tr[6]/td[2]/span").getText();
		if(actualTotalOOFScopesCount.equalsIgnoreCase(String.valueOf(expectedTotalOOFScopesCount))){
			result="Pass - The total number of scopes scanned out of facility matched with expected value which is "+expectedTotalOOFScopesCount;
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The total number of scopes scanned out of facility is expected to be "+expectedTotalOOFScopesCount+" but, however it was "+actualTotalOOFScopesCount;
		}
		System.out.println(result);
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		//Verifying that OOF Location is still shown in Out Of Facility table even after de-activating the location
		Description="Verifying that OOF Location is still shown in Out Of Facility table even after de-activating the location";
		Expected="The OOF Location should be shown in Out Of Facility table even after de-activating the location";
		oofLoc="Olympus Repair";
		stmt="update Barcode set IsActive=0 where Name='"+oofLoc+"'";
		statement.executeUpdate(stmt);
		Thread.sleep(2000);
		UAS.driver.navigate().refresh();
		expected_OOF_Values.clear();
		expected_OOF_Values=get_OFF_LocationAndCount(); //getting OOF table values from UI
		if(expected_OOF_Values.containsKey(oofLoc)){
			result="Pass - The OOF Location "+oofLoc+" is shown in Out Of Facility table even after de-activating the location";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - The OOF Location "+oofLoc+" is not shown in Out Of Facility table after de-activating the location";
		}
		System.out.println(result);
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		//Verifying that Out of Facility table shows the scope details even after de-activating the scope
		Description="Verifying that Out of Facility table shows the scope details even after de-activating the scope";
		Expected="Out of Facility table should show the scope details even after de-activating the scope";
		String Scope="F1 Scope5";
		oofLoc="Facility 4";
		stmt="update Scope set IsActive=0 where Name='"+Scope+"';";
		statement.executeUpdate(stmt);
		Thread.sleep(2000);
		UAS.driver.navigate().refresh();
		Thread.sleep(2000);
		click_OOFLocation(oofLoc);
		String colsNvalues="Scope Name=="+Scope;
		System.out.println("Verifying Out of Facility table for "+colsNvalues);
	    StringBuffer resultDrillDown=gf.verifyTableContents(getOOFLocationTableXpath(oofLoc),colsNvalues,"Scope Name");
	    if(!resultDrillDown.toString().contains("#Failed")){
	    	result="Pass - Out of Facility table show the scope details of "+Scope+" even after de-activating the scope";
	    }else{
	    	UAS.resultFlag="#Failed!#";
			result="#Failed!# - Out of Facility table does not show the scope details of "+Scope+" after de-activating the scope";
	    }
	    System.out.println(result);
	    TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		TH.WriteToTextFile(ResFileName, TestSummary);
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (TestSummary.contains("#Failed!#")||(UAS.resultFlag.toLowerCase().contains("#failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	@AfterTest
	public void PostTest() throws IOException {
		LP_A.CloseDriver();
	}
	
	private static Map<String, String> get_OFF_LocationAndCount(){
		Map<String, String> oof_Location_Scope_Count=new HashMap<String, String>();
		int i=1;
		String oofLocation=null;
		String scopeCount=null;
		while(UAS.driver.findElementsByXPath("//*[@id='gridOutOfFacility']/div[2]/table/tbody/tr["+i+"]/td[1]/a").size()>0){
			oofLocation=UAS.driver.findElementByXPath("//*[@id='gridOutOfFacility']/div[2]/table/tbody/tr["+i+"]/td[1]/a").getText();
			scopeCount=UAS.driver.findElementByXPath("//*[@id='gridOutOfFacility']/div[2]/table/tbody/tr["+i+"]/td[2]").getText();
			oof_Location_Scope_Count.put(oofLocation, scopeCount);
			i++;
		}
		return oof_Location_Scope_Count;
	}
	
	private static String getOOFLocationTableXpath(String OOFLocation){
		String xpath="//*[@id='gridOutOfFacility']/div[2]/table/tbody/tr[*]/td/a[contains(text(),'"+OOFLocation+"')]/../../td[2]/div[2]";
		return xpath;
	}
	
	private static void click_OOFLocation(String oofLocation) throws InterruptedException{
		UAS.driver.findElementByXPath("//*[@id='gridOutOfFacility']/div[2]/table/tbody/tr[*]/td/a[contains(text(),'"+oofLocation+"')]").click();
		Thread.sleep(1000);
	}

}
