package dailydashboardmultifacility;

import java.awt.AWTException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import sun.security.krb5.internal.crypto.Des;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;


public class VerifyMutliFacilityFeatures {
	
	private static String user =Unifia_Admin_Selenium.user;
	private static String pass = Unifia_Admin_Selenium.pass;
	private static String connstring = Unifia_Admin_Selenium.connstring;
	private static String url = Unifia_Admin_Selenium.url;
	private String Expected,Description,result;
	private String ResFileName="MultiFacility_Features_Verification_TestSummary";
	private String ResFileNameXML="MultiFacility_Features_Verification_Result";
	private String ForFileName;
	private String scopeSerialNo,refNo,modifyRoom,scopeName,existingRoom,whatChanged;
	
	private String scopeSerialNo1="2808645";
	private String refNo1="12-1";
	private String PRExisting="F1 Procedure Room 5";
	private String PRtoChange="F1 Procedure Room 1";
	
	private String scopeSerialNo2="2233446";
	private String refNo2="202-1";
	private String sinkExisting="F2 Sink 2";
	private String sinktoChange="F2 Sink 1";
	
	private String scopeSerialNo3="2200689";
	private String refNo3="333-0";
	private String reptoChange="F3 Reprocessor 1";
	private String repExisting="F3 Reprocessor 4";
	
	private String  scopeSerNum, f1ScopeSerNum="9988776",  f2ScopeSerNum="9988777", f3ScopeSerNum="2630321";

	
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	private static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	private static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	private static TestFrameWork.Emulator.Emulator_Actions EM_A;
	private static TestFrameWork.QVDashboard.Dashboard_Verification DV;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralMethods gm;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.workFlowDetailsTestData WF_TD;
	private TestFrameWork.Unifia_Admin_Selenium UAS;

	private String Result_Audit_RefNo;
	private String Result_Audit_ScopeName;
	private String Result_Audit_ReconDate;
	private String Result_Audit_OriginalScanDate;
	private String Result_Audit_Comment;
	private String Result_Audit_UserName;
	private String Result_Audit_WhatChanged;
	private String Result_Audit_Previous;
	private String Result_Audit_Modified;
	private String oofScopesCount="",oofScope="",locAdmin="",actoofScopesCount="";
	private String currDate;
	String selectMultiFacility="Facility 1;Facility 2;Facility 3";
	private String facName="", 	roomName="", facAbr="";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void multiFacility_Verification(String browserP, String URL, String AdminDB ) throws InterruptedException, IOException, AWTException, SQLException, ParseException{
	
		String result_AMCName, result_AMCScopeCount, result_AMCBelow45minColor, result_AMCBetween45n59mnsColor, result_AMCGreaterThan59mnsColor;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    	
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(5000);
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "MultiFacility_Verification");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		//Assign user single facility and verify no option to select other facility on DDB, SRM,  MRC and audit log screen
		Description="Verifying there is no option to change facility on Daily dashboard screen for a User with single facility";
		Expected="There should be no option to change facility on Daily dashboard screen for a User with single facility";
		Thread.sleep(5000);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV2, Unifia_Admin_Selenium.userQV2Pwd);
		qvd_v.clickDashBoard();
		if (!gm.isMultiFacListFound(DBP.selectFacility)){
			result="Pass - There is no list box displayed to change facility on daily dashboard screen";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - There is list box displayed to change facility on daily dashboard screen";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		Description="Verifying there is no option to change facility on SRM screen for a User with single facility";
		Expected="There should be no option to change facility on SRM screen for a User with single facility";
		if (!gm.isMultiFacListFound(DBP.selectFacility)){
			result="Pass - There is no list box displayed to change facility on SRM screen";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - There is list box displayed to change facility on SRM screen";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		Description="Verifying there is no option to change facility on MRC screen for a User with single facility";
		Expected="There should be no option to change facility on MRC screen for a User with single facility";
	    MRC_A.Click_MRCRecordManagement();
	    if (!gm.isMultiFacListFound(DBP.selectFacility)){
	    	result="Pass - There is no list box displayed to change facility on MRC screen";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - There is list box displayed to change facility on MRC screen";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		Description="Verifying there is no option to change facility on Audit Log screen for a User with single facility";
		Expected="There should be no option to change facility on Audit Log screen for a User with single facility";
		IP_A.Click_AuditLog(); //Navigate to Audit log
	    if (!gm.isMultiFacListFound(DBP.selectFacility)){
	    	result="Pass - There is no list box displayed to change facility on Audit Log screen";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - There is list box displayed to change facility on Audit Log screen";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		LP_A.Click_Logout();
		Thread.sleep(5000);
		
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		qvd_v.clickDashBoard();
		
    	List<String> multiFacility = new ArrayList<String>();
    	
    	String verifyMultiFacility="Facility 1;Facility 2;Facility 3";
    	String facName="",roomName="";
    	String[] eachFacility=verifyMultiFacility.split(";");
    	String Result_AuditLog;
    	
    	for (int i=1; i<=eachFacility.length;i++){
    		if (i==1){
    			facName="Facility 1";
    			roomName="Procedure Room";
    			scopeSerialNo=scopeSerialNo1;
    			refNo=refNo1;
    			modifyRoom=PRtoChange;
    			scopeName="F1 Scope12";
    			existingRoom=PRExisting;
    			whatChanged="Procedure Room";
    			
    		}else if (i==2){
    			facName="Facility 2";
    			roomName="Soiled Area";
    			scopeSerialNo=scopeSerialNo2;
    			refNo=refNo2;
    			modifyRoom=sinktoChange;
    			scopeName="F2 Scope2";
    			existingRoom=sinkExisting;
    			whatChanged="Soiled Area";
    		}else if (i==3){
    			facName="Facility 3";
    			roomName="Reprocessor";
    			scopeSerialNo=scopeSerialNo3;
    			refNo=refNo3;
    			modifyRoom=reptoChange;
    			scopeName="F3 Scope33";
    			existingRoom=repExisting;
    			whatChanged="Reprocessor";
    		}
    		
    		System.out.println("------------"+facName);
    		
    		//Navigate to SRM 
    		IP_A.Click_InfectionPrevention();
    		IP_A.Click_SRM();
    		
    		 if (i==1){
    			 //verify users default facility data is displayed on SRM Screen
    			 Description="Verifying SRM screen displays data of default facility "+facName;
    			 Expected="The page should display only "+facName+" data";
    			 Thread.sleep(4000);
    			 if(verifyFacilityPageContents(facName)){
    	    			result="Pass - The SRM screen displayed only default facility "+facName+" data";
    	    		}else{
    	    			Unifia_Admin_Selenium.resultFlag="#Failed!#";
    	    			result="#Failed!# - The SRM screen did not display default facility "+facName+" data";
    	    	}
    			 IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
    			 IP_A.Click_InfectionPrevention();
    			 MRC_A.Click_MRCRecordManagement();
    			 Thread.sleep(4000);
    			 if(verifyFacilityPageContents(facName)){
 	    			result="Pass - The MRC screen displayed only default facility "+facName+" data";
 	    		}else{
 	    			Unifia_Admin_Selenium.resultFlag="#Failed!#";
 	    			result="#Failed!# - The MRC screen did not display default facility "+facName+" data";
 	    		}
    			//Navigate to SRM 
	    		IP_A.Click_InfectionPrevention();
	    		IP_A.Click_SRM();
    		 }
    		
    		gf.selectFromListBox(DBP.selectFacility,facName);
    		Thread.sleep(5000);
    		
    		//verify pagecontent has only facility data
    		Description="Verifying that page displays only "+facName+" data";
    		Expected="The page should display only "+facName+" data";
    		Thread.sleep(4000);
    		if(verifyFacilityPageContents(facName)){
    			result="Pass - The SRM screen displays only "+facName+" data";
    		}else{
    			Unifia_Admin_Selenium.resultFlag="#Failed!#";
    			result="#Failed!# - The SRM screen displays other facilities data also, instead of displaying only "+facName+" data";
    		}
    		System.out.println(result);
    		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
    		
			// open the srm details page and verify list box
			IP_A.ScopeFilter(scopeSerialNo);
			IP_A.ApplyFilter();
			Thread.sleep(2000);
			IP_A.ClearFilter();
			Thread.sleep(2000);
			IP_A.ScopeFilter(scopeSerialNo);
			IP_A.ApplyFilter();
			Thread.sleep(5000);
			IP_A.Click_Details(refNo);
    		 
    		//verify facility dropdown is disabled
			Description="Verifying that Facility drop down is disabled when SRM details are opened";
    		Expected="The Facility drop down should be disabled when SRM details are opened";
     		if (!GF.VerifyElementIsEnabled(DBP.selectFacility)){
     			result="passed - Facility drop down is disabled when SRM details are opened";
     		}else{
     			Unifia_Admin_Selenium.resultFlag="#Failed!#";
     			result="#Failed!# - Facility drop down is not disabled  when SRM details are opened";
     		}
     		System.out.println(result);
     		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
    		
     		//verifying list boxes
     		Description="Verifying that list boxes has only "+facName+" related locations";
    		Expected="The list boxes should contain only "+facName+" related locations";
    		result=compareListBoxElements(facName, roomName);
    		System.out.println(result);
     		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
    		
    		//change PR and verify audit log
    		if (whatChanged.equalsIgnoreCase("Procedure Room")){
    			WF_A.UpdateProcedureRoom(modifyRoom);
    		}else if (whatChanged.equalsIgnoreCase("Soiled Area")){
    			WF_A.UpdateSoiledArea(modifyRoom);
    		}else if (whatChanged.equalsIgnoreCase("Reprocessor")){
    			WF_A.updateReprocessingArea(modifyRoom);
    		}
    		WF_A.Save();
    		
    		
    		IP_A.Click_AuditLog();
    		AL_A.click_AuditSearch();
    		AL_A.Search_RefNo(refNo);
			AL_A.Search_Location(modifyRoom);
			AL_A.Search_WhatChanged(whatChanged);
			String GridID=AL_A.GetGridID_AuditLog_ByRefNo(refNo,whatChanged);
			Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,refNo);
			System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
			Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,scopeName);
			System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
			
			Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, whatChanged);
			System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
			Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,existingRoom);
			System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
			Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, modifyRoom);
			System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
			
			Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ScopeName="+Result_Audit_ScopeName
    				+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
    		System.out.println(Result_AuditLog);
    		if(Result_AuditLog.contains("#Failed!#")){
    			Unifia_Admin_Selenium.resultFlag="#Failed!#";
    		}
    		
    		Description="Updated the room " + whatChanged + " to "+ modifyRoom + " in SRM screeen and verifying these changes reflected in the Audit log screen";
    		Expected="The changes should be correctly displayed on Audit log screen";
    		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, Result_AuditLog);
    		
    	}
    	
    	IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
			
    	//verify able to change facilities on DDB, MRC, SRM and audit log screens
    	Description="Verifying whether Facility can be changed on Daliy Dashboard screen";
    	Expected="Facility can be changed on Daliy Dashboard screen";
		qvd_v.clickDashBoard();
		if (!isListBoxMulti(DBP.selectFacility)){
			result="Passed - the dropdown doesnot allow to select multiple facilities on Daily dashboard screen";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - the dropdown allows to select multiple facilities on Daily dashboard screen";
		}
		System.out.println(result);		
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			
		facName="Facility 1";
		gf.selectFromListBox(DBP.selectFacility,facName);
		System.out.println("Facility 1 selected on daily dashboard screen");

		//verify pagecontent has only F1 facility data
		Description="Verifying that page displays only "+facName+" data";
		Expected="The page should display only "+facName+" data";
		Thread.sleep(4000);
		if(verifyFacilityPageContents(facName)){
			result="Pass - The Daily Dashboard screen displays only "+facName+" data";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - The Daily Dashboard screen displays other facilities data also, instead of displaying only "+facName+" data";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    
	    System.out.println("Navigated to MRC screen and verifying Facility 1 selected in Daily dashboard screen is same on MRC screen ");
	    //verify pagecontent has only F1 facility data
  		Description="Verifying that after navigating to MRC screen, Facility 1 that is selected in Daily dashboard screen is same on MRC screen ";
  		Expected="Facility 1 that is selected in Daily dashboard screen should be same on MRC screen";
  		Thread.sleep(4000);
  		if(verifyFacilityPageContents(facName)){
  			result="Pass - The MRC screen displays only "+facName+" data";
  		}else{
  			Unifia_Admin_Selenium.resultFlag="#Failed!#";
  			result="#Failed!# - The MRC screen displays other facilities data also, instead of displaying only "+facName+" data";
  		}
  		System.out.println(result);
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
  		//Verifying that the dropdown does not allow to select multiple facilities on MRC screen
  		Description="Verifying that the dropdown doesnot allow to select multiple facilities on MRC screen";
  		Expected="The dropdown should not allow to select multiple facilities on MRC screen";
	    if (!isListBoxMulti(DBP.selectFacility)){
	    	result="Passed - the dropdown doesnot allow to select multiple facilities on MRC screen";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - the dropdown allows to select multiple facilities on MRC screen";
		}
	    System.out.println(result);
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
  		
  		//verify audit log drop down shows the same facility selected on the DDB
  		IP_A.Click_AuditLog(); 
  		Description="Verifying that after navigating to Audit Log screen, "+facName+" that is selected in Daily dashboard screen is same on Audit log screen ";
  		Expected=facName+" that is selected in Daily dashboard screen should be same on Audit Log screen";
  		Thread.sleep(4000);
  		if(verifyFacilityPageContents(facName)){
  			result="Pass - The Facility 1 is displayed in the list box on audit log and it displays only "+facName+" data";
  		}else{
  			Unifia_Admin_Selenium.resultFlag="#Failed!#";
  			result="#Failed!# - The audit log screen displays other facilities data also, instead of displaying only "+facName+" data";
  		}
  		System.out.println(result);
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
	    
  		facName="Facility 2";
	    gf.selectFromListBox(DBP.selectFacility,facName);
	    System.out.println("Facility 2 selected on Audit Log screen");
	    
	    IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    //verify pagecontent has only F2 facility data
  		Description="Verifying that after navigating to MRC screen, Facility 2 that is selected in Audit Log screen is same on MRC screen ";
  		Expected="Facility 2 that is selected in Audit Log screen should be same on MRC screen";
  		Thread.sleep(000);
  		if(verifyFacilityPageContents(facName)){
  			result="Pass - The MRC screen displays only "+facName+" data";
  		}else{
  			Unifia_Admin_Selenium.resultFlag="#Failed!#";
  			result="#Failed!# - The MRC screen displays other facilities data also, instead of displaying only "+facName+" data";
  		}
  		System.out.println(result);
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
	    
	    IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		
		System.out.println("Navigated to SRM screen and verifying Facility 2 selected in Audit Log screen is same on SRM screen ");
		 //verify pagecontent has only F2 facility data
  		Description="Verifying that after navigating to SRM screen, Facility 2 that is selected in Audit Log screen is same on SRM screen ";
  		Expected="Facility 2 that is selected in Audit Log screen should be same on SRM screen";
  		Thread.sleep(4000);
  		if(verifyFacilityPageContents(facName)){
  			result="Pass - The SRM screen displays only "+facName+" data";
  		}else{
  			Unifia_Admin_Selenium.resultFlag="#Failed!#";
  			result="#Failed!# - The SRM screen displays other facilities data also, instead of displaying only "+facName+" data";
  		}
  		System.out.println(result);
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
  		//Verifying that the drop down does not allow to select multiple facilities on SRM screen
  		Description="Verifying that the dropdown doesnot allow to select multiple facilities on SRM screen";
  		Expected="The dropdown should not allow to select multiple facilities on SRM screen";
		if (!isListBoxMulti(DBP.selectFacility)){
			result="Passed - the dropdown doesnot allow to select multiple facilities on SRM screen";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - the dropdown allows to select multiple facilities on SRM screen";
		}
		System.out.println(result);
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
  		facName="Facility 3";
		gf.selectFromListBox(DBP.selectFacility,facName);
		//verify pagecontent has only F3 facility data
		Description="Verifying that page displays only "+facName+" data";
		Expected="The page should display only "+facName+" data";
		Thread.sleep(4000);
		if(verifyFacilityPageContents(facName)){
			result="Pass - The SRM screen displays only "+facName+" data";
		}else{
			Unifia_Admin_Selenium.resultFlag="#Failed!#";
			result="#Failed!# - The SRM screen displays other facilities data also, instead of displaying only "+facName+" data";
		}
		System.out.println(result);
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		// back to DDB and verify F3 is selected
		qvd_v.clickDashBoard();
		System.out.println("Navigated to Daily dashboard screen and verifying Facility 3 selected in SRM sreen is same on Daily dashboard screen ");
		//verify pagecontent has only F3 facility data
  		Description="Verifying that after navigating to Daily dashboard screen, Facility 3 that is selected in SRM screen is same on Daily dashboard screen ";
  		Thread.sleep(4000);
  		Expected="Facility 3 that is selected in SRM screen should be same on Daily dashboard screen";
  		if(verifyFacilityPageContents(facName)){
  			result="Pass - The Daily dashboard screen displays only "+facName+" data";
  		}else{
  			Unifia_Admin_Selenium.resultFlag="#Failed!#";
  			result="#Failed!# - The Daily dashboard screen displays other facilities data also, instead of displaying only "+facName+" data";
  		}
  		System.out.println(result);
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
  		
  		//verify audit log drop down shows the same facility selected on the DDB
  		IP_A.Click_AuditLog(); 
  		Description="Verifying that after navigating to Audit Log screen, "+facName+" that is selected in SRM screen is same on Audit log screen ";
  		Expected=facName+" that is selected in SRM screen should be same on Audit Log screen";
  		Thread.sleep(4000);
  		if(verifyFacilityPageContents(facName)){
  			result="Pass - The Facility 3 is displayed in the list box on Audit Log and it displays only "+facName+" data";
  		}else{
  			Unifia_Admin_Selenium.resultFlag="#Failed!#";
  			result="#Failed!# - The audit log screen displays other facilities data also, instead of displaying only "+facName+" data";
  		}
  		System.out.println(result);
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
  		
		
		//Verify assigned facilities are displayed in facility selection dropdown
		Description="Verify assigned facilities are displayed in facility selection dropdown";
		Expected="Assigned facilities should be displayed in facility selection dropdown";
  		List<String> facNameAct = new ArrayList<String>();
		facNameAct=getListBoxElements(DBP.selectFacility);
		
		List<String> facNameExp = new ArrayList<String>();
		facNameExp.add("Facility 1");
		facNameExp.add("Facility 2");
		facNameExp.add("Facility 3");
		
		if(facNameExp != null && facNameAct != null && (facNameExp.size() == facNameAct.size())){
			facNameExp.removeAll(facNameAct);
	        if(facNameExp.isEmpty()){
	            result="Pass - Both list are same.";
	        }else{
	        	Unifia_Admin_Selenium.resultFlag="#Failed!#";
	            result="#Failed!# - Both list are not same";
	        }
	    }
		System.out.println(result);
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		//Verify DDB displays 
		IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);   		
		
		qvd_v.clickDashBoard();
		
		verifyMultiFacility="Facility 1;Facility 2";
    	facName="";
    	roomName="";
    	eachFacility=verifyMultiFacility.split(";");
		
		for (int i=1; i<=eachFacility.length;i++){
    		if (i==1){
    			facName="Facility 1";
    		}else if (i==2){
    			facName="Facility 2";
    		}
    		roomName="Reprocessor";
    		
    		IP_A.Click_InfectionPrevention();
    		MRC_A.Click_MRCRecordManagement();
    		IP_A.ClearFilter();
    		gf.selectFromListBox(DBP.selectFacility,facName);
    	    Thread.sleep(2000);
    		
    		//verify pagecontent has only selected facility data
      		Description="Verifying that only "+facName+" is displayed on MRC screen ";
      		Expected="Only "+facName+" should be displayed on MRC screen ";
      		Thread.sleep(4000);
      		if(verifyFacilityPageContents(facName)){
      			result="Pass - The MRC screen displays only "+facName+" data";
      		}else{
      			Unifia_Admin_Selenium.resultFlag="#Failed!#";
      			result="#Failed!# - The MRC screen displays other facilities data also, instead of displaying only "+facName+" data";
      		}
      		System.out.println(result);
      		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
    		
    		String GridID="1";
    		String ModRepro="F"+i+" Reprocessor 4";
    		
    		String currentRepro=MRC_A.getReprocessor(GridID);
    		Thread.sleep(4000);
    		MRC_A.editMRCRecord(GridID);
    		Thread.sleep(4000);
    		
    		//verifying list boxes
     		Description="Verifying that list boxes has only "+facName+" related locations";
    		Expected="The list boxes should contain only "+facName+" related locations";
    		result=compareListBoxElements(facName, "MRC"+roomName);
    		System.out.println(result);
     		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
    		
    		MRC_A.updateReprocessor(ModRepro);
    		Thread.sleep(4000);
    		
        	String WhatChanged="Reprocessor";
        	String Comment = "Changed the F"+i+" Reprocessor";
    		MRC_A.EnterComment(Comment);
        	String ReconDateTime=GF.ServerDateTime(Unifia_Admin_Selenium.url, user, pass);
    		MRC_A.saveMRCChanges();
    		Thread.sleep(3000);
    		
        	
        	IP_A.Click_AuditLog();
        	AL_A.click_AuditSearch();
        	AL_A.ClearAuditLogSrchCritera();
        	AL_A.Search_WhatChanged(WhatChanged);
    		AL_A.Search_Location(ModRepro);
    		Thread.sleep(3000);
    		
    		Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,"-");
    		System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
    		Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
    		System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
    		Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
    		System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
    		Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged);
    		System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
    		Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,currentRepro);
    		System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
    		Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModRepro);
    		System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
    		
    		Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate
    				+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
    				+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
    		System.out.println(Result_AuditLog);
    		
    		Description="Verifying that the changes done in MRC screen are correctly displayed on the Audit log screen";
    		Expected="The changes should be correctly displayed on Audit log screen";
    		if(Result_AuditLog.contains("#Failed!#")){
    			Unifia_Admin_Selenium.resultFlag="#Failed!#";
    		}
    		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, Result_AuditLog);
    	}
		
		IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);   	
		
		//Verify DDB does not display inactive locations
		qvd_v.clickDashBoard();
		String searchFac;
		boolean isInactivePresent = false;
		for (int cntr = 0; cntr < eachFacility.length; cntr++) {
			isInactivePresent = false;
			gf.selectFromListBox(DBP.selectFacility, eachFacility[cntr]);
			List<String> inActiveLoc = new ArrayList<String>();
			inActiveLoc = getInactiveLocations(cntr + 1);

			for (int cntrInactiveLoc = 0; cntrInactiveLoc < inActiveLoc.size(); cntrInactiveLoc++) {
				Thread.sleep(3000);
				if (verifyFacilityPageContents(inActiveLoc.get(cntrInactiveLoc))) {
					isInactivePresent = true;
					System.out.println(inActiveLoc.get(cntrInactiveLoc)+ " location is inactive location but it is displayed on DDB");
				} else {
					System.out.println(inActiveLoc.get(cntrInactiveLoc)+ " location is not displayed on DDB");
				}
			}

			// moving right and checking for the inactive locations
			Unifia_Admin_Selenium.driver.findElementByXPath(DBP.cabNext).click();
			Unifia_Admin_Selenium.driver.findElementByXPath(DBP.prNext).click();
			Unifia_Admin_Selenium.driver.findElementByXPath(DBP.srNext).click();
			Unifia_Admin_Selenium.driver.findElementByXPath(DBP.reproNext).click();

			for (int cntrInactiveLoc = 0; cntrInactiveLoc < inActiveLoc.size(); cntrInactiveLoc++) {
				Thread.sleep(4000);
				if (verifyFacilityPageContents(inActiveLoc.get(cntrInactiveLoc))) {
					isInactivePresent = true;
					System.out.println(inActiveLoc.get(cntrInactiveLoc)+ " location is inactive location but it is displayed on DDB");
				}
			}

			Description = "Verifying inactive locations are not displayed on Daily Dashboard screen for "+ eachFacility[cntr];
			Expected = "Inactive locations should not be displayed on Daily Dashboard screen for "+ eachFacility[cntr];
			if (!isInactivePresent) {
				result = "Passed - Inactive locations are not displayed for "+ eachFacility[cntr];
			} else {
				Unifia_Admin_Selenium.resultFlag = "#Failed!#";
				result = "#Failed!# - Inactive locations are displayed for "+ eachFacility[cntr];
			}
			System.out.println(result);
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		}	
		IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		facNameExp.clear();
		facNameExp.add("Facility 1");
		facNameExp.add("Facility 2");
		Boolean Res;
		String scope1="",scope2="",scope3="",Repro1="",PR1="",scope1Xpath="",scope2Xpath="",scope3Xpath="",scope1InPRxpath="";
		for (int facCntr=1; facCntr<=facNameExp.size();facCntr++){
			if (facCntr==1){
				scope1="F1 Scope21";
	    		scope2="F1 Scope12";
	    		scope3="F1 Scope13";
	    		Repro1="F1 Reprocessor 1";
	    		PR1="F1 Procedure Room 1";
	    		scope1Xpath="//*[@id='reprocScope21']";
	    		scope2Xpath="//*[@id='reprocScope12']";
	    		scope3Xpath="//*[@id='reprocScope13']";
	    		scope1InPRxpath="//*[@id='prScope21']";
			}else if (facCntr==2){
				scope1="F2 Scope1";
	    		scope2="F2 Scope2";
	    		scope3="F2 Scope3";
	    		Repro1="F2 Reprocessor 1";
	    		PR1="F2 Procedure Room 1";
	    		scope1Xpath="//*[@id='reprocScope201']";
	    		scope2Xpath="//*[@id='reprocScope202']";
	    		scope3Xpath="//*[@id='reprocScope203']";
	    		scope1InPRxpath="//*[@id='prScope201']";
			}
			
			gf.selectFromListBox(DBP.selectFacility,facNameExp.get(facCntr-1));
			//Verify DDB is reflected by scanning the scopes in parallel
			Res = EM_A.ScanItem(Repro1, "Scope", "", scope1, "");
			Thread.sleep(40*1000);
			//Verify Scope 1 is visible in DDB
			Description="Verifying only 2 scopes in Reprocessor. Verifying that "+scope1+" is displayed in "+Repro1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1);
			Expected=scope1+" should be displayed in "+Repro1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1);
			if (verifyRoomScopes(scope1, scope1Xpath)){
				result="Passed - "+scope1+ " is displayed in "+Repro1;
			}else{
				Unifia_Admin_Selenium.resultFlag = "#Failed!#";
				result="#Failed!# - "+scope1+ " is not displayed in "+Repro1;
			}
			System.out.println(result);
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			
			Res = EM_A.ScanItem(Repro1, "Scope", "", scope2, "");
			Description="Verifying that "+scope2+" is displayed in "+Repro1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1);
			Expected=scope2+" should be displayed in "+Repro1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1);
			Thread.sleep(4000);
			if (verifyRoomScopes(scope2, scope2Xpath)){
				result="Passed - "+scope2+ " is displayed in "+Repro1;
			}else{
				Unifia_Admin_Selenium.resultFlag = "#Failed!#";
				result="#Failed!# - "+scope2+ " is not displayed in "+Repro1;
			}
			System.out.println(result);
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			
			Res = EM_A.ScanItem(Repro1, "Scope", "", scope3, "");
			Description="Verifying that "+scope3+" is displayed in "+Repro1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1);
			Expected=scope3+" should be displayed in "+Repro1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1);
			Thread.sleep(4000);
			if (verifyRoomScopes(scope3, scope3Xpath)){
				result="Passed - "+scope3+ " is displayed in "+Repro1;
			}else{
				Unifia_Admin_Selenium.resultFlag = "#Failed!#";
				result="#Failed!# - "+scope3+ " is not displayed in "+Repro1;
			}
			System.out.println(result);
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			//verify Scope 1 is not found in Rep1
			Description="Verifying that "+scope1+" is not displayed in "+Repro1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1)+" after scanning "+scope3;
			Expected=scope1+" should not be displayed in "+Repro1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1)+" after scanning "+scope3;
			Thread.sleep(4000);
			if (!GF.CheckElementExists(scope1Xpath)){
				result="Passed - "+scope1+ " is not displayed in "+Repro1;
			}else{
				Unifia_Admin_Selenium.resultFlag = "#Failed!#";
				result="#Failed!# - "+scope1+ " is displayed in "+Repro1;
			}
			System.out.println(result);
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			
			Res = EM_A.ScanItem(PR1, "Scope", "", scope1, "");
			Description="Verifying that "+scope1+" is displayed in "+PR1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1);
			Expected=scope1+" should be displayed in "+PR1+" on Daily Dashboard screen for "+facNameExp.get(facCntr-1);
			Thread.sleep(4000);
			if (verifyRoomScopes(scope1, scope1InPRxpath)){
				result="Passed - "+scope1+ " is displayed in "+PR1;
			}else{
				Unifia_Admin_Selenium.resultFlag = "#Failed!#";
				result="#Failed!# - "+scope1+ " is not displayed in "+PR1;
			}
			System.out.println(result);
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		} 

		for (int facCntr=1; facCntr<=facNameExp.size();facCntr++){
			if (facCntr==1){
				oofScope="F1 Scope10";
				locAdmin="F1 Administration";
			}else if (facCntr==2){
				oofScope="F2 Scope10";
				locAdmin="F2 Administration";
			}
			
			gf.selectFromListBox(DBP.selectFacility,facNameExp.get(facCntr-1));
			Thread.sleep(5000);
			//scan a scope from OOF to facility and verify scope count is decreased
			oofScopesCount=qvd_v.getOOFScopeCount();
			
			Integer expScopeCount=(Integer.parseInt(oofScopesCount))-1;
			System.out.println(oofScopesCount);	
			Description="Verify Out of Facility scope count decreases once scope is moved backed into facility."; 
			Expected ="Total scope count before scanning into facility is  "+oofScopesCount+". After scanning the scope count should be "+ expScopeCount;
			Res = EM_A.ScanItem(locAdmin, "Scope", "", oofScope,"");
			Thread.sleep(5000);
			actoofScopesCount=qvd_v.getOOFScopeCount();
			if (expScopeCount==(Integer.parseInt(actoofScopesCount))){
				result="Passed - the total scope count in oof location is decreased from "+ oofScopesCount+" to "+actoofScopesCount+" after "+oofScope+" is scanned into facility";
			}else{
				Unifia_Admin_Selenium.resultFlag="#Failed!#";
				result="#Failed!#  - the total scope count in oof location did not decrease after "+oofScope+" is scanned into facility";
			}
			
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		}
		
		//Create 3 new workflows, one each for one facility and verify in audit log that only specific data for each workflow is displayed
		UAS.driverCount="first";
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		Thread.sleep(4000);
		eachFacility=selectMultiFacility.split(";");
		for (int cntr=1; cntr<=eachFacility.length;cntr++){
    		if (cntr==1){
    			facName="Facility 1";
    			scopeSerNum=f1ScopeSerNum;
    			facAbr="F1 ";
    		}else if (cntr==2){
    			facName="Facility 2";
    			scopeSerNum=f2ScopeSerNum;
    			facAbr="F2 ";
    		}	else if (cntr==3){
    			facName="Facility 3";
    			scopeSerNum=f3ScopeSerNum;
    			facAbr="F3 ";
    		}
    		gf.selectFromListBox(DBP.selectFacility,facName);
    		Thread.sleep(4000);
			//creating workflow
    		UAS.driver.findElement(By.xpath(DBP.addWorkFlowBtn)).click();
			WF_A.selectSerNum(scopeSerNum);		
			WF_A.UpdateProcedureRoom(facAbr+ WF_TD.procRoom);
			currDate=getDate(-1).toString();
			WF_A.UpdateExamDate(currDate);
			WF_A.UpdatePRInStaff(WF_TD.scopeInStaffPR);
			WF_A.UpdatePatient(WF_TD.patient);
			WF_A.UpdatePhysician(WF_TD.physStaff);
			currDate=getDate(-1).toString();
			WF_A.UpdateProcStart(currDate);
			WF_A.UpdateProcEnd(getDate(-1).toString());
			WF_A.UpdatePreClean(WF_TD.preCleanStatus);
			WF_A.UpdatePreCleanStaff(WF_TD.preStaff);
			//Soiled
			WF_A.UpdateSoiledArea(facAbr+WF_TD.soiledArea);
			currDate=getDate(-1).toString();
			WF_A.UpdateSoiledDate(currDate);
			WF_A.UpdateSoiledStaff(WF_TD.sAStaff);
			WF_A.UpdateLTStatus(WF_TD.sALTStatus);
			currDate=getDate(-1).toString();
			WF_A.UpdateMCStart(currDate);
			currDate=getDate(-1).toString();
			WF_A.UpdateMCEnd(currDate);
			//Reprocessing Area
			WF_A.updateReprocessingArea(facAbr+WF_TD.RepArea);
			WF_A.updateReasonforReprocessing(WF_TD.reasonForRepro);
			WF_A.updateScopeInStaff(WF_TD.scopeInStaffRep);
			currDate=getDate(-1).toString();
			WF_A.updateScopeInStaffDate(currDate);
			WF_A.updateScopeOutStaff(WF_TD.scopeOutstaffRep);
			currDate=getDate(-1).toString();
			WF_A.UpdateScopeOutStaffDate(currDate);
			//Bioburden
			WF_A.UpdateBioStatus(WF_TD.bioRes);
			WF_A.UpdateBioKeyResult(WF_TD.bioKeyStatus);
			WF_A.UpdateBioScannedResult(WF_TD.bioSanRes);
			WF_A.UpdateBioStaff(WF_TD.bioStaff);
			//Culture
			WF_A.UpdateCultureResult(WF_TD.cultResult);
			WF_A.EnterComment(WF_TD.comment);
			String ReconDateTime=GF.ServerDateTime(Unifia_Admin_Selenium.url, user, pass);
			WF_A.Save();

			Description="New workflow created for Facility "+cntr;
			Expected = "New Facility should be created";
			result="New Facility is created";
			
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		}
		
		//Navigate to Audit Log and verify only facility data is displayed
		eachFacility=selectMultiFacility.split(";");
		IP_A.Click_AuditLog();
		eachFacility=selectMultiFacility.split(";");
		for (int cntr=1; cntr<=eachFacility.length;cntr++){
    		if (cntr==1){
    			facName="Facility 1";
    			scopeSerNum="f1ScopeSerNum";
    			facAbr="F1 ";
    		}else if (cntr==2){
    			facName="Facility 2";
    			scopeSerNum="f2ScopeSerNum";
    			facAbr="F2 ";
    		}	else if (cntr==3){
    			facName="Facility 3";
    			scopeSerNum="f3ScopeSerNum";
    			facAbr="F3 ";
    		}
    		gf.selectFromListBox(DBP.selectFacility,facName);
    		//verify pagecontent has only facility data
    		Description="Verifying that Audilt Log screen displays only "+facName+" data";
    		Expected="The page should display only "+facName+" data";
    		Thread.sleep(4000);
    		if(verifyFacilityPageContents(facName)){
    			result="Pass - The Audit Log screen displays only "+facName+" data";
    		}else{
    			Unifia_Admin_Selenium.resultFlag="#Failed!#";
    			result="#Failed!# - The Audit Log screen displays other facilities data also, instead of displaying only "+facName+" data";
    		}
    		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		}
		
		
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if ((Unifia_Admin_Selenium.resultFlag.toLowerCase().contains("#failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		
		LP_A.CloseDriver();
	
	}

	@AfterTest
	public void PostTest() throws IOException {
		LP_A.CloseDriver();
	}

	public Boolean verifyFacilityPageContents(String facilityName){
		String pageContent="";
		Boolean res=null;
		pageContent= Unifia_Admin_Selenium.driver.findElement(By.tagName("body")).getText();
		String selectedFac="",otherFac1="", otherFac2="";
		if (facilityName.equalsIgnoreCase("Facility 1")){
			selectedFac="f1 ";
			otherFac1="f2 ";
			otherFac2="f3 ";
		}else if (facilityName.equalsIgnoreCase("Facility 2")){
			selectedFac="f2 ";
			otherFac1="f1 ";
			otherFac2="f3 ";
		}else if (facilityName.equalsIgnoreCase("Facility 3")){
			selectedFac="f3 ";
			otherFac1="f1 ";
			otherFac2="f2 ";
		}
    	if (pageContent.toLowerCase().contains(otherFac1) || pageContent.toLowerCase().contains(otherFac2)){
    		res=false;
    	}else{
    		if (pageContent.toLowerCase().contains(selectedFac)){
    			res= true;
    		}
    	}
		return res;
	}
	
	public String compareListBoxElements(String facility, String roomName) throws IOException, SQLException{
		String res=null;
		List<String> locNameDB = new ArrayList<String>();
		List<String> locNameApp = new ArrayList<String>();
		String listBoxxpath="";
		Integer locationType=null, facilityId=null;
		
		String PR="//*[@id='ProcedureRoom']";
		String SA="//*[@id='SoiledArea']";
		String Rep="//*[@id='Reprocessor']";
		String MRCRep="//*[@id='ReprocessorLocationId']";
		
		if (roomName.equalsIgnoreCase("Procedure Room")){
			listBoxxpath=PR;
			locationType=2;
		}else if (roomName.equalsIgnoreCase("Soiled Area")){
			listBoxxpath=SA;
			locationType=4;
		}else if (roomName.equalsIgnoreCase("Reprocessor")){
			listBoxxpath=Rep;
			locationType=5;
		}else if (roomName.equalsIgnoreCase("MRCReprocessor")){
			listBoxxpath=MRCRep;
			locationType=5;
		}
		
		if (facility.equalsIgnoreCase("Facility 1")){
			facilityId=1;
		}else if (facility.equalsIgnoreCase("Facility 2")){
			facilityId=2;
		}else if (facility.equalsIgnoreCase("Facility 3")){
			facilityId=3;
		}
		
		locNameApp=getListBoxElements(listBoxxpath);
		locNameDB=getLocationElefrmDB(facilityId,locationType);
				
		if(locNameDB != null && locNameApp != null && (locNameDB.size() == locNameApp.size())){
			locNameDB.removeAll(locNameApp);
            if(locNameDB.isEmpty()){
            	res="Pass";
                System.out.println("Both list are same");
            }else{
            	res="#Failed!#";
                System.out.println("Both list are not same");
            }
        }
		return res;
	}
	
	public List getListBoxElements(String xpath) throws IOException, SQLException{
		Select dropdown = new Select(Unifia_Admin_Selenium.driver.findElement(By.xpath(xpath)));
		 List<WebElement> listOptions = dropdown.getOptions();
		 List<String> dropdownList = new ArrayList<String>();

		    //Get the length
		    System.out.println(listOptions.size());

		    for (int cntr = 0; cntr < listOptions.size(); cntr++) {
		    	if (listOptions.get(cntr).getAttribute("text").length()!=0){
		    		dropdownList.add(listOptions.get(cntr).getAttribute("text"));
		    		System.out.println(listOptions.get(cntr).getAttribute("text"));
				}
		    }
			return dropdownList;
	}
	
	public List getLocationElefrmDB(Integer facilityID, Integer locationType) throws SQLException{
		 List<String> dropdownList = new ArrayList<String>();
		Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		Statement statement = conn.createStatement();
		String stmt="select name from location where facilityid_fk='"+facilityID+"' and locationtypeid_fk="+locationType;
		System.out.println("stmt="+stmt);
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			if (RS.getString(1)!=null || RS.getString(1)!=""||RS.getString(1)!=" "){
				dropdownList.add(RS.getString(1));
			}
		}
		conn.close();
		return dropdownList;
	}
	
	public boolean isListBoxMulti(String listBoxXpath)
	{
		boolean res;
	    Select select = new Select(Unifia_Admin_Selenium.driver.findElementByXPath(listBoxXpath));   
	    if (!select.isMultiple()) {
	    	res= false;
	    }else{
	    	res= true;
	    }
	    return res;
	}
	
	public List getInactiveLocations(Integer facilityID ) throws SQLException {
		List<String> dropdownList = new ArrayList<String>();
		Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		Statement statement = conn.createStatement();
		String stmt="select name from location where facilityid_fk="+facilityID+" and isactive=0";
		System.out.println("stmt="+stmt);
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			if (RS.getString(1)!=null || RS.getString(1)!=""||RS.getString(1)!=" "){
				dropdownList.add(RS.getString(1));
			}
		}
		conn.close();
		return dropdownList;
	}
		
	public boolean verifyRoomScopes(String expVal,String objXpath) throws InterruptedException{
		String applicationVal;
		boolean result;
		applicationVal=DV.getText(objXpath);
		if (expVal.equalsIgnoreCase(applicationVal)){
			result = true;
		}else{
			result=false;
		}
		return result;
	}
	
	public String getDate(int daystoAdd){
		Date date=DateUtils.addDays(new Date(), daystoAdd);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);
	}
	
}
