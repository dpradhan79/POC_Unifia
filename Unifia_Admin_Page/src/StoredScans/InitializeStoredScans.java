package StoredScans;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import TestFrameWork.Unifia_Admin_Selenium;

public class InitializeStoredScans {

	private TestFrameWork.TestHelper TH;
	private ITConsole.ITConScanSimActions IT_A;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private String fileSource=System.getProperty("user.dir")+"\\src\\StoredScans";
			
	private String actualResult="\t\t\t InitializeStoredScans_TestSummary \r\n\r\n"; 
	private String ForFileName;
	private String TestResFileName="InitializeStoredScans_TestSummary_";
	private String fileDestFolder="\\XMLFolder";
	/*
	 * This Test will Create a TestScenarios.xml in the Current WorkSpace with
	 * the given XML file Name. And then this file is copied to specified
	 * location on Unifia Server. Also all XML files that are there in
	 * src\StoredScans folder are copied to specified location on Unifia Server
	 * one by one.
	 */ 
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void copyFilesToServer(String browserP, String URL, String AdminDB) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, InterruptedException{
		if (UAS.parallelExecutionType && UAS.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
				
		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
	
		
		boolean isFileCopied;
		
		isFileCopied=IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ScenarioInitialScans_SinkStored.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ScenarioInitialScans_SinkStored.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ScenarioInitialScans_SinkStored.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ScenarioInitialScans_Reprocessing_StoredScans.xml",fileSource,fileDestination);	
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ScenarioInitialScans_Reprocessing_StoredScans.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ScenarioInitialScans_Reprocessing_StoredScans.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ScenarioInitial_and_PR_StoredScans.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ScenarioInitial_and_PR_StoredScans.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ScenarioInitial_and_PR_StoredScans.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "Scenario_Initialize_Stored_StorageArea.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- Scenario_Initialize_Stored_StorageArea.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - Scenario_Initialize_Stored_StorageArea.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
					
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ScenarioInitialScans_Bioburden_after_InitialScans.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ScenarioInitialScans_Bioburden_after_InitialScans.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ScenarioInitialScans_Bioburden_after_InitialScans.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ScenarioInitialScans_StoredReprocessing_WithKE.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ScenarioInitialScans_StoredReprocessing_WithKE.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ScenarioInitialScans_StoredReprocessing_WithKE.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ScenarioInitialScans_Culture_StoredScans.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ScenarioInitialScans_Culture_StoredScans.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ScenarioInitialScans_Culture_StoredScans.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ScenarioInitialScans_PR3Scopes_StoredScans.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ScenarioInitialScans_PR3Scopes_StoredScans.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ScenarioInitialScans_PR3Scopes_StoredScans.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "Scenario_Initialize_UsePatient_as_BucketClosure.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- Scenario_Initialize_UsePatient_as_BucketClosure.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - Scenario_Initialize_UsePatient_as_BucketClosure.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
				
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ScenarioInitialScans_SwitchProcRooms_beforeStartExam_Stored.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ScenarioInitialScans_SwitchProcRooms_beforeStartExam_Stored.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ScenarioInitialScans_SwitchProcRooms_beforeStartExam_Stored.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ReprocessingArea_NewCycleMgmt.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ReprocessingArea_NewCycleMgmt.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ReprocessingArea_NewCycleMgmt.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ProcedureRoom_Missed_CycleMgmt.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ProcedureRoom_Missed_CycleMgmt.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ProcedureRoom_Missed_CycleMgmt.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "Repeat_ReprocessingArea_NewCycleMgmt.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- Repeat_ReprocessingArea_NewCycleMgmt.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - Repeat_ReprocessingArea_NewCycleMgmt.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "ProcedureRoom_CycleMgmt.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- ProcedureRoom_CycleMgmt.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - ProcedureRoom_CycleMgmt.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "SoiledArea_NewCycleMgmt.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- SoiledArea_NewCycleMgmt.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - SoiledArea_NewCycleMgmt.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
	}
	@AfterTest
	public void postTest() throws IOException{
		LP_A.CloseDriver();
	}
}
