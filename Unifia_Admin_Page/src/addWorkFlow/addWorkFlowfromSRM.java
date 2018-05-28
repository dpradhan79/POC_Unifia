package addWorkFlow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.Unifia_SRM_WorkFlowDetails.workFlowDetailsTestData;

public class addWorkFlowfromSRM {
	private GeneralFunc GF;
	private TestDataFunc TDF;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.workFlowDetailsTestData WF_TD;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	private int KE=0;
	private int BioburdenEnabled=1;
	private int Culture=0;
	private String expected,description,result,currDate,errorMsg;
	public Connection conn=null;
	public Statement statement=null;
	private String inActiveRoom="";
	private String stmt;
	private ITConsole.ITConScanSimActions IT_A;
	
	private String File="Workflow_NewCycleMgmt.xml";
	private String fileDestFolder="\\XMLFolder";
	private String fileSource=System.getProperty("user.dir")+"\\src\\addWorkFlow";
	private String actualResult="\t\t\t AddWorkFlowSRM_CycleMgmt_TestSummary \r\n\r\n"; 
	
	private GeneralFunc gf;
	private TestFrameWork.TestHelper TH;
	private ResultSet rs;
	
	private String ForFileName;
	private String TestResFileName="AddWorkFlowSRM_CycleMgmt_TestSummary_";
	private String Result;
	private String FileName;
	private String Description;
	private String Expected;
	private String Sink="Sink 2";
	
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
	private int expectedScope1StaffID=21;
	private int expectedScope2StaffID=21;
	
	private List<String> ScopeAssocID=new ArrayList<String>();
	private String ProcedureRoom="Procedure Room 2";
	private String Reprocessor="Reprocessor 1";
	private String Scope2InReproAssocID;
	private String Scope2InPRAssoc_ID;
	private String Scope2InSinkAssocID;
	private int expectedCycleCount=2;
	private int actualCycleCount;
	
	private String scopeRefNum="2-2";
	private String whatChanged,Location,previousValue,newValue,scanDateOriginal,ReconDateTime,GridID;
	
	private static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	private static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	
	private static String user =Unifia_Admin_Selenium.user;
    private static String pass = Unifia_Admin_Selenium.pass;
		
	String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
	String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
	String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void  Test(String browserP, String URL,String AdminDB) throws Exception {
		
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.resultFlag="Pass";
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
			
		FileName="AddWorkFlowSRM_CycleMgmt_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		UAS.XMLFileName=FileName;
		UAS.TestCaseNumber=1;
		
		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
		
		boolean isFileCopied;
		
		isFileCopied=IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, "Workflow_NewCycleMgmt.xml",fileSource,fileDestination);
		if(isFileCopied){
			actualResult=actualResult+"\r\n\r\nPass- Workflow_NewCycleMgmt.xml is copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}else{
			actualResult=actualResult+"\r\n\r\n#Failed!# - Workflow_NewCycleMgmt.xml is not copied to "+fileDestination;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		
		fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.batchFile,fileSource,batchFileDestination);
		
		if(isFileCopied){
			System.out.println("Pass- "+UAS.batchFile+" is copied to "+batchFileDestination);
		}else{
			System.out.println("#Failed!#- "+UAS.batchFile+" is not copied to "+batchFileDestination);
		}
		
		GF.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, BioburdenEnabled, Culture);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, BioburdenEnabled, Culture);		
		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
	
		//Update the environment and xml file in  Runbatch.bat file
		IT_A.modifyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.scanSimPath, UAS.batchFile,File, xmlPath);
		//execute ScanSimUI
		IT_A.executeScanSim(UAS.Env, UAS.userName, UAS.IISPass,UAS.scanSimPathTool,UAS.ITConsoleExecTime,0);
		Thread.sleep(2000);
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		// Verify Date format in SRM table in workFlowStart Date Column
		IP_V.verifyDateFormatINSRMScreen();
				
		UAS.driver.findElement(By.xpath(DBP.addWorkFlowBtn)).click();
		//Add fields
		WF_A.Save();
		errorMsg=UAS.driver.findElement(By.xpath(DBP.error)).getText();
		if (errorMsg.equalsIgnoreCase(WF_TD.expErrorMsg)){
			IHV.Exec_Log_Result(FileName, "Verify error message when scope is not selected", WF_TD.expErrorMsg,"Passed - expected error message is displayed");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify error message when scope is not selected", WF_TD.expErrorMsg,"#Failed!# - expected error message is not displayed. Bug 12564 opened - When adding a new Workflow, if the user hits save without selecting a scope the message was and should be 'Please select a Scope before continuing.'; however, it was changed to 'Validation failed'");
		}
		
		// Verify date fields does not allow future date
		//PR
		currDate=getDate(+1).toString();
		WF_A.UpdateExamDate(currDate);
		WF_A.selectSerNum(WF_TD.scopeSerNum);
				
		expected="Verify Exam date field is set to blank";
		description="Verify Exam date field does not allow future date";
		result=WF_V.Verify_ExamDate("");
		System.out.println(result);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateProcStart(currDate);
		WF_A.selectSerNum(WF_TD.scopeSerNum2);
		expected="Verify Procedure Start date field is set to blank";
		description="Verify Procedure Start date field does not allow future date";
		result=WF_V.Verify_ProcStart("");
		System.out.println(result);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateProcEnd(currDate);
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		expected="Verify Procedure End date field is set to blank";
		description="Verify Procedure End date field does not allow future date";
		result=WF_V.Verify_ProcEnd("");
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateSoiledDate(currDate);
		WF_A.selectSerNum(WF_TD.scopeSerNum2);
		expected="Verify Scope In Date field of Soiled Area is set to blank";
		description="Verify Scope In Date field of Soiled Area does not allow future date";
		result=WF_V.Verify_LTMCDate("");
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateMCStart(currDate);
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		expected="Verify Manual Clean Start field is set to blank";
		description="Verify Manual Clean Start field does not allow future date";
		result=WF_V.Verify_MCStart("");
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateMCEnd(currDate);
		WF_A.selectSerNum(WF_TD.scopeSerNum2);
		expected="Verify Manual Clean End field is set to blank";
		description="Verify Manual Clean End field does not allow future date";
		result=WF_V.Verify_MCEnd("");
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		
		WF_A.updateScopeInStaffDate(currDate);
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		expected="Verify scope in staff date is set to blank";
		description="Verify the scope in staff date field of Reprocessing Room does not allow future date";
		result=WF_V.Verify_ReproScopeInTime("");
		IHV.Exec_Log_Result(FileName, description, expected, result);

		WF_A.UpdateScopeOutStaffDate(currDate);
		WF_A.selectSerNum(WF_TD.scopeSerNum2);
		expected="Verify scope out staff date is set to blank";
		description="Verify scope out staff date field of Reprocessing Room does not allow future date";
		result=WF_V.Verify_ReproScopeOutTime("");
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		//Verify PR does not allow blank PRName and blank scope scan in date
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		WF_A.Save();
		errorMsg=UAS.driver.findElement(By.xpath(DBP.error)).getText();
		if (errorMsg.equalsIgnoreCase(WF_TD.expErrorMsgMandField)){
			IHV.Exec_Log_Result(FileName, "Verify error message when Procedure Room is not selected", WF_TD.expErrorMsgMandField,"Passed - expected error message is displayed");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify error message when Procedure Room is not selected", WF_TD.expErrorMsgMandField,"#Failed!# - expected error message is not displayed");
		}
		WF_A.UpdateProcedureRoom(WF_TD.procRoom);
		expected="Verify Procedure Room is set to "+WF_TD.procRoom;
		description="Adding the Procedure Room";
		result=WF_V.Verify_PR(WF_TD.procRoom);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.Save();
		errorMsg=UAS.driver.findElement(By.xpath(DBP.error)).getText();
		if (errorMsg.equalsIgnoreCase(WF_TD.expErrorMsgMandField)){
			IHV.Exec_Log_Result(FileName, "Verify error message when Scope scan in date of Procedure Room is not selected", WF_TD.expErrorMsgMandField,"Passed - expected error message is displayed");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify error message when Scope scan in date of Procedure Room s not selected", WF_TD.expErrorMsgMandField,"#Failed!# - expected error message is not displayed");
		}
		
		currDate=getDate(-1).toString();
		WF_A.UpdateExamDate(currDate);
		expected="Verify Exam Date is set to "+currDate;
		description="Adding the ExamDate";
		result=WF_V.Verify_ExamDate(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		WF_A.Save();
		
		if (UAS.driver.findElementsByXPath(DBP.addWorkFlowBtn).size()>0){
			IHV.Exec_Log_Result(FileName, "Verify SRM workflow is saved with valid Procedure Room and Scope Scan In date", "SRM workflow should be saved","Passed - SRM workflow is saved");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify SRM workflow is saved with valid Procedure Room and Scope Scan In date", "SRM workflow should be saved","#Failed!# - SRM workflow is not saved");
		}
		
		//Verify Workflow does not allow blank SA name and blank scope scan in date
		UAS.driver.findElement(By.xpath(DBP.addWorkFlowBtn)).click();
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		WF_A.Save();
		errorMsg=UAS.driver.findElement(By.xpath(DBP.error)).getText();
		if (errorMsg.equalsIgnoreCase(WF_TD.expErrorMsgMandField)){
			IHV.Exec_Log_Result(FileName, "Verify error message when Soiled Area is not selected", WF_TD.expErrorMsgMandField,"Passed - expected error message is displayed");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify error message when Soiled Area is not selected", WF_TD.expErrorMsgMandField,"#Failed!# - expected error message is not displayed");
		}
		WF_A.UpdateSoiledArea(WF_TD.soiledArea);
		expected="Verify Soiled Area is set to "+WF_TD.soiledArea;
		description="Adding the Solied Area";
		result=WF_V.Verify_SoiledArea(WF_TD.soiledArea);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		WF_A.Save();
		errorMsg=UAS.driver.findElement(By.xpath(DBP.error)).getText();
		if (errorMsg.equalsIgnoreCase(WF_TD.expErrorMsgMandField)){
			IHV.Exec_Log_Result(FileName, "Verify error message when Scope scan in date of Soiled Area is not selected", WF_TD.expErrorMsgMandField,"Passed - expected error message is displayed");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify error message when Scope scan in date of Soiled Area is not selected", WF_TD.expErrorMsgMandField,"#Failed!# - expected error message is not displayed");
		}
		currDate=getDate(-1).toString();
		WF_A.UpdateSoiledDate(currDate);
		expected="Verify Scope Scan in Date of Soiled Area is set to "+currDate;
		description="Adding the Scope Scan in Date of Solied Area";
		IHV.Exec_Log_Result(FileName, description, expected, result);
		WF_A.Save();
		if (UAS.driver.findElementsByXPath(DBP.addWorkFlowBtn).size()>0){
			IHV.Exec_Log_Result(FileName, "Verify SRM workflow is saved with valid Soiled Area and Scope Scan In date", "SRM workflow should be saved","Passed - SRM workflow is saved");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify SRM workflow is saved with valid Soiled Area and Scope Scan In date", "SRM workflow should be saved","#Failed!# - SRM workflow is not saved");
		}
		//Verify Workflow does not allow blank Reprocessor name and blank scope scan in date
		UAS.driver.findElement(By.xpath(DBP.addWorkFlowBtn)).click();
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		WF_A.Save();
		errorMsg=UAS.driver.findElement(By.xpath(DBP.error)).getText();
		if (errorMsg.equalsIgnoreCase(WF_TD.expErrorMsgMandField)){
			IHV.Exec_Log_Result(FileName, "Verify error message when Reprocessing Room is not selected", WF_TD.expErrorMsgMandField,"Passed - expected error message is displayed");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify error message when Reprocessing Room is not selected", WF_TD.expErrorMsgMandField,"#Failed!# - expected error message is not displayed");
		}
		
		WF_A.updateReprocessingArea(WF_TD.RepArea);
		expected="Verify Reprocessing Area is set to "+WF_TD.RepArea;
		description="Adding the Reprocessing Room";
		result=WF_V.Verify_Reprossor(WF_TD.RepArea);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		WF_A.Save();
		errorMsg=UAS.driver.findElement(By.xpath(DBP.error)).getText();
		if (errorMsg.equalsIgnoreCase(WF_TD.expErrorMsgMandField)){
			IHV.Exec_Log_Result(FileName, "Verify error message when Scope scan in date of Reprocessing Room  is not selected", WF_TD.expErrorMsgMandField,"Passed - expected error message is displayed");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify error message when Scope scan in date of Reprocessing RoomReprocessing Room is not selected", WF_TD.expErrorMsg,"#Failed!# - expected error message is not displayed");
		}
		
		currDate=getDate(-1).toString();
		WF_A.updateScopeInStaffDate(currDate);
		expected="Verify scope in staff date is set to "+currDate;
		description="Adding the scope in staff date in Reprocessing Room";
		result=WF_V.Verify_ReproScopeInTime(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		WF_A.Save();
		if (UAS.driver.findElementsByXPath(DBP.addWorkFlowBtn).size()>0){
			IHV.Exec_Log_Result(FileName, "Verify SRM workflow is saved with valid Reprocessing Room and Scope Scan In date", "SRM workflow should be saved","Passed - SRM workflow is saved");
		}else{
			IHV.Exec_Log_Result(FileName, "Verify SRM workflow is saved with valid Reprocessing Room and Scope Scan In date", "SRM workflow should be saved","#Failed!# - SRM workflow is not saved");
		}
		
		//Verify Inactive Procedure Room cannot be selected
		UAS.driver.findElement(By.xpath(DBP.addWorkFlowBtn)).click();
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select name from location where IsActive=0 and locationtypeid_FK=2 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			
			while(RS.next()){
				inActiveRoom=(RS.getString(1));
				}
		}catch (SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
		}
		WF_A.UpdateProcedureRoom(inActiveRoom);
		expected="Unable to select '"+inActiveRoom+"'"; 
		description="Verify inactive '"+inActiveRoom+"' should not be selected"; 
		result=WF_V.Verify_PR("");
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		stmt="update location set IsActive=1 where name = '"+inActiveRoom+"' and locationtypeid_FK=2 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
		System.out.println("stmt="+stmt);
		Statement update1 = conn.createStatement();
		update1.executeUpdate(stmt);
		
		// close srm screen and open again
		WF_A.Cancel("No");
		
		UAS.driver.findElement(By.xpath(DBP.addWorkFlowBtn)).click();
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		
		WF_A.UpdateProcedureRoom(inActiveRoom);
		expected="Able to select '"+inActiveRoom+"'"; 
		description="Verify able to select '"+inActiveRoom+"' after it is made active"; 
		result=WF_V.Verify_PR(inActiveRoom);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		stmt="update location set IsActive=0 where name = '"+inActiveRoom+"' and locationtypeid_FK=2 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
		System.out.println("stmt="+stmt);
		update1 = conn.createStatement();
		update1.executeUpdate(stmt);
		conn.close();
		
		//Verify Inactive Soiled Area cannot be selected
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select name from location where IsActive=0 and locationtypeid_FK=4 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				inActiveRoom=(RS.getString(1));
				}
		}catch (SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
		}
		WF_A.UpdateSoiledArea(inActiveRoom);
		expected="Unable to select '"+inActiveRoom+"'"; 
		description="Verify inactive '"+inActiveRoom+"' should not be selected"; 
		result=WF_V.Verify_SoiledArea("");
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		stmt="update location set IsActive=1 where name = '"+inActiveRoom+"' and locationtypeid_FK=4 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
		System.out.println("stmt="+stmt);
		update1 = conn.createStatement();
		update1.executeUpdate(stmt);
		
		// close srm screen and open again
		WF_A.Cancel("Yes");
		
		UAS.driver.findElement(By.xpath(DBP.addWorkFlowBtn)).click();
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		
		WF_A.UpdateSoiledArea(inActiveRoom);
		expected="Able to select '"+inActiveRoom+"'"; 
		description="Verify able to select '"+inActiveRoom+"' after it is made active"; 
		result=WF_V.Verify_SoiledArea(inActiveRoom);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		stmt="update location set IsActive=0 where name = '"+inActiveRoom+"' and locationtypeid_FK=4 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
		System.out.println("stmt="+stmt);
		update1 = conn.createStatement();
		update1.executeUpdate(stmt);
		conn.close();
		
		//Verify Inactive Reprocessing Room cannot be selected
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select name from location where IsActive=0 and locationtypeid_FK=5 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				inActiveRoom=(RS.getString(1));
				}
		}catch (SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
		}
		WF_A.updateReprocessingArea(inActiveRoom);
		expected="Unable to select '"+inActiveRoom+"'"; 
		description="Verify inactive '"+inActiveRoom+"' should not be selected"; 
		result=WF_V.Verify_Reprossor("");
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		stmt="update location set IsActive=1 where name = '"+inActiveRoom+"' and locationtypeid_FK=5 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
		System.out.println("stmt="+stmt);
		update1 = conn.createStatement();
		update1.executeUpdate(stmt);
		
		// close srm screen and open again
		WF_A.Cancel("Yes");
		
		UAS.driver.findElement(By.xpath(DBP.addWorkFlowBtn)).click();
		WF_A.selectSerNum(WF_TD.scopeSerNum);
		
		WF_A.updateReprocessingArea(inActiveRoom);
		expected="Able to select '"+inActiveRoom+"'"; 
		description="Verify able to select '"+inActiveRoom+"' after it is made active"; 
		result=WF_V.Verify_Reprossor(inActiveRoom);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		stmt="update location set IsActive=0 where name = '"+inActiveRoom+"' and locationtypeid_FK=5 and LastUpdatedDateTime=(select min(LastUpdatedDateTime) from location)";
		System.out.println("stmt="+stmt);
		update1 = conn.createStatement();
		update1.executeUpdate(stmt);
		conn.close();
				
		//creating workflow
		WF_A.selectSerNum(WF_TD.scopeSerNum2);
		WF_V.Verify_ScopeModel(WF_TD.scopeModel2);
		expected="Verify Scope model is set to "+WF_TD.scopeModel2;
		description=expected;
		result=WF_V.Verify_ScopeModel(WF_TD.scopeModel2);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_V.Verify_ScopeName(WF_TD.scopeName2);
		expected="Verify Scope name is set to "+WF_TD.scopeName2;
		description="Adding the Procedure Room";
		result=WF_V.Verify_ScopeName(WF_TD.scopeName2);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		//PR
		WF_A.UpdateProcedureRoom(WF_TD.procRoom);
		expected="Verify Procedure Room is set to "+WF_TD.procRoom;
		description="Adding the Procedure Room";
		result=WF_V.Verify_PR(WF_TD.procRoom);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		currDate=getDate(-1).toString();
		WF_A.UpdateExamDate(currDate);
		expected="Verify Exam Date is set to "+currDate;
		description="Adding the ExamDate";
		result=WF_V.Verify_ExamDate(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);		
		
		WF_A.UpdatePRInStaff(WF_TD.scopeInStaffPR);
		expected="Verify staff is set to "+WF_TD.scopeInStaffPR;
		description="Adding the Staff in PR";
		result=WF_V.Verify_PRInStaff(WF_TD.scopeInStaffPR);
		IHV.Exec_Log_Result(FileName, description, expected, result);		
		
		WF_A.UpdatePatient(WF_TD.patient);
		expected="Verify patient is set to "+WF_TD.patient;
		description="Adding the patient in PR";
		result=WF_V.Verify_Patient(WF_TD.patient);
		IHV.Exec_Log_Result(FileName, description, expected, result);	
		
		WF_A.UpdatePhysician(WF_TD.physStaff);
		expected="Verify physician is set to "+WF_TD.physStaff;
		description="Adding the patient in PR";
		result=WF_V.Verify_Physician(WF_TD.physStaff);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		currDate=getDate(-1).toString();
		WF_A.UpdateProcStart(currDate);
		expected="Verify procedure start date is set to "+currDate;
		description="Adding the procedure start date in PR";
		result=WF_V.Verify_ProcStart(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		WF_A.UpdateProcEnd(getDate(-1).toString());
		expected="Verify procedure end date is set to "+currDate;
		description="Adding the procedure end date in PR";
		result=WF_V.Verify_ProcEnd(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdatePreClean(WF_TD.preCleanStatus);
		expected="Verify pre clean is set to "+WF_TD.preCleanStatus;
		description="Adding the pre clean status  in PR";
		WF_V.Verify_PreClean(WF_TD.preCleanStatus);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdatePreCleanStaff(WF_TD.preStaff);
		expected="Verify pre clean staff is set to "+WF_TD.preStaff;
		description="Adding the pre clean staff  in PR";
		result=WF_V.Verify_PreCleanStaff(WF_TD.preStaff);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		//Soiled
		WF_A.UpdateSoiledArea(WF_TD.soiledArea);
		expected="Verify Soiled Area is set to "+WF_TD.sAStaff;
		description="Adding the Solied Area  in Soiled Area";
		result=WF_V.Verify_SoiledArea(WF_TD.soiledArea);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		currDate=getDate(-1).toString();
		WF_A.UpdateSoiledDate(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateSoiledStaff(WF_TD.sAStaff);
		expected="Verify soiled area staff is set to "+WF_TD.sAStaff;
		description="Adding the solied area staff in Soiled Area";
		result=WF_V.Verify_SoiledStaff(WF_TD.sAStaff);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateLTStatus(WF_TD.sALTStatus);
		expected="Verify LeakTest status is set to "+WF_TD.sALTStatus;
		description="Adding the LeakTest status in Soiled Area";
		result=WF_V.Verify_LT(WF_TD.sALTStatus);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		currDate=getDate(-1).toString();
		WF_A.UpdateMCStart(currDate);
		expected="Verify manual clean start is set to "+currDate;
		description="Adding the manual clean start in Soiled Area";
		result=WF_V.Verify_MCStart(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);
	
		currDate=getDate(-1).toString();
		WF_A.UpdateMCEnd(currDate);
		expected="Verify manual clean end is set to "+currDate;
		description="Adding the manual clean end in Soiled Area";
		result=WF_V.Verify_MCEnd(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		//Reprocessing Area
		WF_A.updateReprocessingArea(WF_TD.RepArea);
		expected="Verify Reprocessing Area is set to "+WF_TD.RepArea;
		description="Adding the Reprocessing Room";
		result=WF_V.Verify_Reprossor(WF_TD.RepArea);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.updateReasonforReprocessing(WF_TD.reasonForRepro);
		expected="Verify Reason for Reprocessing is set to "+WF_TD.reasonForRepro;
		description="Adding the Reason for Reprocessing in Reprocessing Room";
		result=WF_V.Verify_ReproReason(WF_TD.reasonForRepro);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.updateScopeInStaff(WF_TD.scopeInStaffRep);
		expected="Verify scope in staff is set to "+WF_TD.scopeInStaffRep;
		description="Adding the scope in staff in Reprocessing Room";
		result=WF_V.Verify_ReproInStaff(WF_TD.scopeInStaffRep);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		currDate=getDate(-1).toString();
		WF_A.updateScopeInStaffDate(currDate);
		expected="Verify scope in staff date is set to "+currDate;
		description="Adding the scope in staff date in Reprocessing Room";
		result=WF_V.Verify_ReproScopeInTime(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);

		WF_A.updateScopeOutStaff(WF_TD.scopeOutstaffRep);
		expected="Verify scope out staff is set to "+WF_TD.scopeOutstaffRep;
		description="Adding the scope out staff in Reprocessing Room";
		result=WF_V.Verify_ReproOutStaff(WF_TD.scopeOutstaffRep);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		currDate=getDate(-1).toString();
		WF_A.UpdateScopeOutStaffDate(currDate);
		expected="Verify scope out staff date is set to "+currDate;
		description="Adding the scope out staff date in Reprocessing Room";
		result=WF_V.Verify_ReproScopeOutTime(currDate);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		//Bioburden
		WF_A.UpdateBioStatus(WF_TD.bioRes);
		expected="Verify bioburden result is set to "+WF_TD.bioRes;
		description="Adding the bioburden result";
		result=WF_V.Verify_BioResult(WF_TD.bioRes);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateBioKeyResult(WF_TD.bioKeyStatus);
		expected="Verify bioburden key value is set to "+WF_TD.bioKeyStatus;
		description="Adding the bioburden kay value";
		result=WF_V.Verify_BioKeyValue(WF_TD.bioKeyStatus);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateBioScannedResult(WF_TD.bioSanRes);
		expected="Verify bioburden scanned reult is set to "+WF_TD.bioSanRes;
		description="Adding the bioburden scanned reult";
		result=WF_V.Verify_BioScanValue(WF_TD.bioSanRes);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		WF_A.UpdateBioStaff(WF_TD.bioStaff);
		expected="Verify bioburden staff is set to "+WF_TD.bioStaff;
		description="Adding the bioburden staff";
		result=WF_V.Verify_BioStaff(WF_TD.bioStaff);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		//Culture
		WF_A.UpdateCultureResult(WF_TD.cultResult);
		expected="Verify culture result is set to "+WF_TD.cultResult;
		description="Adding the culture result";
		result=WF_V.Verify_CultureResult(WF_TD.cultResult);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		WF_A.EnterComment(WF_TD.comment);
		expected="Verify comments are set to "+WF_TD.comment;
		description="Adding the comments";
		result=WF_V.Verify_Comment(WF_TD.comment);
		IHV.Exec_Log_Result(FileName, description, expected, result);
		String ReconDateTime=GF.ServerDateTime(Unifia_Admin_Selenium.url, user, pass);
		WF_A.Save();
		
		if (UAS.driver.findElements(By.xpath(DBP.refNum)).size()>0) {
			result="Pass - New workflow added successfully";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - New workflow is not added";
		}
		
		//verify audit log for newly created record
		whatChanged="New Scope Workflow";
		Location="-";
		previousValue="-";
		newValue="-";
		scanDateOriginal="-";
				
		IP_A.Click_AuditLog();
	    AL_A.click_AuditSearch();
	    AL_A.Search_RefNo(scopeRefNum);
		AL_A.Search_ScopeName(WF_TD.scopeName2);
		AL_A.Search_Location(Location);
		AL_A.Search_WhatChanged(whatChanged);
		
		GridID=AL_A.GetGridID_AuditLog_ByRefNo(scopeRefNum,whatChanged);
		String Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,scopeRefNum);
		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
		String Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
		String Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,WF_TD.scopeName2);
		System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
		String Result_Audit_Comment=AL_V.Verify_Comment(GridID, WF_TD.comment);
		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
		String Result_Audit_UserName=AL_V.Verify_Username(GridID, Unifia_Admin_Selenium.userQV1);
		System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
		String Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, whatChanged);
		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
		String Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,previousValue);
		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
		String Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, newValue);
		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
		
		result="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName="+Result_Audit_ScopeName+
				". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged+
				". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified;
		
		result =  result + "\r\n Result= Verify StaffIDChanged in SRM screen is reflected in auditlog screen; "+result;
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		//verify in DB
		expected="Verify new work flow is created";
		description=expected;
		IHV.Exec_Log_Result(FileName, description, expected, result);
		
		actualResult=actualResult+"\r\n\r\n"+IT_A.log;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		if(!IT_A.log.contains("#Failed!#")){
			try{
				conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
				Statement statement=conn.createStatement();

				stmt="select Max(cycleID) from ScopeCycle where ScopeID_FK=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					actualCycleCount=rs.getInt(1);
				}
				System.out.println("expectedCycleCount = "+expectedCycleCount);
				
				//Checking the ReprocessingCount for Scope2
				Description="Checking whether CycleCount for Scope2 is "+expectedCycleCount;
				Expected="CycleCount for Scope2 Should be "+expectedCycleCount;
				if(actualCycleCount==expectedCycleCount){
					Result="Pass- CycleCount for Scope2 is "+expectedCycleCount;
				}else{
					Result="#Failed!#- CycleCount for Scope2 should be "+expectedCycleCount+" however, it was "+actualCycleCount ;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	
				
				
				//Quering for Scope2 status
				String []Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, scope2);
				String Scope2ActualReproCount=Scope_IH[5];
				String Scope2ActualExamCount=Scope_IH[6];
				
				//Quering for Scope2 ExpectedReproCount
				Scope2ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, scope2ID);
				
				//Checking the ReprocessingCount for Scope2
				Description="Checking whether ReprocessingCount for Scope2 is "+Scope2ExpectedReproCount;
				Expected="ReprocessingCount for Scope2 Should be "+Scope2ExpectedReproCount;
				String ResultReproCount=IHV.Result_ReproCount(Scope2ActualReproCount,Scope2ExpectedReproCount);
				System.out.println(ResultReproCount);
				actualResult=actualResult+"\r\n\r\n"+ResultReproCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultReproCount);
				
				//Quering for Scope2 ExpectedExamCount
				Scope2ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, scope2ID);
				
				//Checking the ExamCount for Scope2
				Description="Checking whether ExamCount for Scope2 is "+Scope2ExpectedExamCount;
				Expected="ExamCount for Scope2 Should be "+Scope2ExpectedExamCount;
				String ResultExamCount=IHV.Result_ExamCount(Scope2ActualExamCount,Scope2ExpectedExamCount);
				System.out.println(ResultExamCount);
				actualResult=actualResult+"\r\n\r\n"+ResultExamCount;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultExamCount);
				
				//Quering for Scope2 LastScanStaffID
				scope2LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, scope2);
				
				//Checking the LastScanStaffID for Scope2
				Description="Checking whether LastScanStaffID for Scope2 is "+expectedScope2StaffID;
				Expected="LastScanStaffID for Scope2 Should be "+expectedScope2StaffID;
				String ResultLastStaff=IHV.Result_LastScanStaffID(scope2LastScanStaffID_FK, expectedScope2StaffID);
				System.out.println(ResultLastStaff);
				actualResult=actualResult+"\r\n\r\n"+ResultLastStaff;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultLastStaff);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=15 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Reprocessor+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Scope2InReproAssocID=rs.getString(1);
					break;
				}
				System.out.println("RealScope2InReproAssocID = "+Scope2InReproAssocID);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=3 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Scope2InPRAssoc_ID=rs.getString(1);
				}
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=26 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Sink+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Scope2InSinkAssocID=rs.getString(1);
				}
				System.out.println("RealScope2InSinkAssocID = "+Scope2InSinkAssocID);
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=2 and CycleID=1";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=1 contains the AssociationID_FK for Scope2 into the ProcedureRoom
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=1 contains the AssociationID_FK for Real scan of Scope2 into the ProcedureRoom";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=1 should contain the AssociationID_FK for  Real scan of Scope2 into the ProcedureRoom";
				if(ScopeAssocID.contains(Scope2InPRAssoc_ID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 1 contains the Real AssociationID("+Scope2InPRAssoc_ID+") for Real scan of Scope2 into "+ProcedureRoom;
				}else{
					Result="#Failed!#- Scope cycle of Scope2 for Cycle 1 does not contain the Real AssociationID("+Scope2InPRAssoc_ID+") for Real scan of Scope2 into "+ProcedureRoom;
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	

				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=1 contains the AssociationID_FK for Real scan of Scope2 into Sink1
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=1 contains the AssociationID_FK for Real scan of Scope2 into Sink1";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=1 should contain the AssociationID_FK for Real scan of Scope2 into Sink1";
				if(ScopeAssocID.contains(Scope2InSinkAssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 1 contains the Real AssociationID("+Scope2InSinkAssocID+") for Real scan of Scope2 into Sink1";
				}else{
					Result="#Failed!#- Scope cycle of Scope2 for Cycle 1 does not contain the Real AssociationID("+Scope2InSinkAssocID+") for Real scan of Scope2 into Sink1";
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	
				
				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=1 contains the AssociationID_FK for Real scan of Scope2 into the reprocessor
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=1 contains the AssociationID_FK for Real scan of Scope2 into the reprocessor";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=1 should contain the AssociationID_FK for Real scan of Scope2 into the reprocessor";
				if(ScopeAssocID.contains(Scope2InReproAssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 1 contains the Real AssociationID("+Scope2InReproAssocID+") for Real scan of  Scope2 into Reprocessor";
					System.out.println(Result);
				}else{
					Result="#Failed!# - Scope cycle of Scope2 for Cycle 1 does not contain the Real AssociationID("+Scope2InReproAssocID+") for Real scan of Scope2 into Reprocessor";
					System.out.println(Result);
				}
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
				//verification for workflow added through SRM 
				ProcedureRoom="Procedure Room 1";
				Sink="Sink 1";
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=15 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Reprocessor+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Scope2InReproAssocID=rs.getString(1);
				}
				System.out.println("RealScope2InReproAssocID = "+Scope2InReproAssocID);
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=3 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+ProcedureRoom+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Scope2InPRAssoc_ID=rs.getString(1);
				}
				
				stmt="select IH.AssociationID_FK from ItemHistory IH where IH.IsStored=0 and IH.ScanItemTypeID_FK=1 and IH.ScanItemID_FK=2 and IH.CycleEventID_FK=26 and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+Sink+"')";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					Scope2InSinkAssocID=rs.getString(1);
				}
				System.out.println("RealScope2InSinkAssocID = "+Scope2InSinkAssocID);
				
				stmt="select AssociationID_FK from ScopeCycle where ScopeID_FK=2 and CycleID=2";
				System.out.println(stmt);
				rs=statement.executeQuery(stmt);
				while(rs.next()){
					ScopeAssocID.add(rs.getString(1));
				}
				
				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK for Scope2 into the ProcedureRoom using SRM Workflow
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK of Scope2 into the ProcedureRoom using SRM Workflow";
				Expected="ScopeCycle table for ScopeID_FK=1 and CycleID=2 should contain the AssociationID_FK of Scope2 into the ProcedureRoom using SRM Workflow";
				if(ScopeAssocID.contains(Scope2InPRAssoc_ID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 2 contains the Real AssociationID("+Scope2InPRAssoc_ID+") of Scope2 into "+ProcedureRoom+" using SRM Workflow";
				}else{
					Result="#Failed!#- Scope cycle of Scope2 for Cycle 2 does not contain the Real AssociationID("+Scope2InPRAssoc_ID+") of Scope2 into "+ProcedureRoom+" using SRM Workflow";
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	

				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK of Scope2 into Sink1 using SRM Workflow
				Description="Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK of Scope2 into Sink1 using SRM Workflow";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=2 should contain the AssociationID_FK of Scope2 into Sink1 using SRM Workflow";
				if(ScopeAssocID.contains(Scope2InSinkAssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 2 contains the Real AssociationID("+Scope2InSinkAssocID+") of Scope2 into Sink1 using SRM Workflow";
				}else{
					Result="#Failed!#- Scope cycle of Scope2 for Cycle 2 does not contain the Real AssociationID("+Scope2InSinkAssocID+") of Scope2 into Sink1 using SRM Workflow";
				}
				System.out.println(Result);
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);	
				
				//Checking whether ScopeCycle table for ScopeID_FK=1 and CycleID=2 contains the AssociationID_FK of Scope2 into the reprocessor
				Description="Checking whether ScopeCycle table for ScopeID_FK=2 and CycleID=2 contains the AssociationID_FK of Scope2 into the reprocessor using SRM Workflow";
				Expected="ScopeCycle table for ScopeID_FK=2 and CycleID=2 should contain the AssociationID_FK of Scope2 into the reprocessor using SRM Workflow";
				if(ScopeAssocID.contains(Scope2InReproAssocID)){
					Result="Pass- Scope cycle of Scope2 for Cycle 2 contains the Real AssociationID("+Scope2InReproAssocID+") of  Scope2 into Reprocessor using SRM Workflow";
					System.out.println(Result);
				}else{
					Result="#Failed!# - Scope cycle of Scope2 for Cycle 2 does not contain the Real AssociationID("+Scope2InReproAssocID+") of Scope2 into Reprocessor using SRM Workflow";
					System.out.println(Result);
				}
				actualResult=actualResult+"\r\n\r\n"+Result;
				TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
				IHV.Exec_Log_Result(FileName, Description, Expected, Result);
				
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
		
		IHV.Close_Exec_Log(UAS.XMLFileName, "Test Completed", UAS.TestCaseNumber);
		if (Unifia_Admin_Selenium.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		conn.close();
		LP_A.CloseDriver();
	}
	
	@AfterTest
	  public void PostTest() throws IOException{
	  	LP_A.CloseDriver();
	  }
	
	public String getDate(int daystoAdd){
		Date date=DateUtils.addDays(new Date(), daystoAdd);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}
}
