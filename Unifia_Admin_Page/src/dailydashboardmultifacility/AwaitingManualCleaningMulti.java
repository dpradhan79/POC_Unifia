package dailydashboardmultifacility;

import java.awt.AWTException;
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
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class AwaitingManualCleaningMulti {
	
	private QV_GeneralFunc QV_Gen;
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private String TestSummary= "\t\t\t DailyDashboard_AwaitingManualCleaning_TestSummary \r\n\r\n"; 
	private String ResFileName="Multi_DailyDashboard_AwaitingManualCleaning_TestSummary";
	private String ResFileNameXML="Multi_DailyDashboard_AwaitingManualCleaning_Result";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	private String ForFileName;
	private Dashboard_Actions qvd_a;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	
	
	private int scopeID_Fk,scopeID_Fk2;
		
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void AMC_Verification(String browserP, String URL, String AdminDB ) throws InterruptedException, IOException, AWTException, SQLException{
		
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
    	/*ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Exam Queue");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;*/
		
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML,"Awaiting Manual Cleaning - Verification");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		String Expected,Description;
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		//Thread.sleep(5000);
		//UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		qvd_v.clickDashBoard();
		
		//Getting the drilldown popup data from database
		Connection conn=null;
		Statement statement=null;
		String stmt="";
		List<String> scopeID = new ArrayList<String>();
		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		statement = conn.createStatement();
		
		for (int eachFac=1;eachFac<=3;eachFac++){
			scopeID.clear();
			String facilityName= "Facility "+eachFac;
			System.out.println("Verifying for "+facilityName);
			gf.selectFromListBox(DBP.selectFacility,facilityName);
			Thread.sleep(2000);
			qvd_a.clickElement(DBP.amcName);
			stmt="select name from scope where scopeid_pk in (select scopeid_fk from scopestatus where ScopeStateID_FK="+DBP.amcScopeState+") and FacilityID_FK="+eachFac;
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				scopeID.add(RS.getString(1));
			}
			System.out.println ("Need to verify "+scopeID.size()+" rows in Awaiting Manaul Cleaning");
			String scopeName,scopeSerialNum = null,scopeModel = null,lastScannedLocation = null;
			for (int eachRec=0;eachRec<scopeID.size();eachRec++){
				scopeName=scopeID.get(eachRec);
				stmt="select serialNumber from Scope where name='"+scopeName+"'";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					scopeSerialNum=(RS.getString(1));
				}
				stmt="select name from scopetype where scopetypeid_pk=(select ScopeTypeID_fK from scope where name='"+scopeName+"')";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					scopeModel=(RS.getString(1));
				}
				stmt="select name from location where locationid_pk=(select LocationID_FK from scopestatus where scopeid_fk=(select ScopeID_pK from scope where name='"+scopeName+"'))";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					lastScannedLocation=(RS.getString(1));
				}
				String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+"; Serial Number=="+scopeSerialNum+";Last Scanned=="+lastScannedLocation;
				StringBuffer resultDrillDown=gf.verifyTableContents(DBP.amcTableContent,colsNvalues,"Scope Name");
				System.out.println(resultDrillDown);
				
				Description="Verifying drill down popup contents of Awaiting Manual Cleaning area for Row -"+(eachRec+1)+" for "+facilityName;
				Expected =Description;
				TestRes=Description+":\r\n\t"+resultDrillDown+"\r\n";
				TestSummary=TestSummary+TestRes+"\r\n";
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
			}
			qvd_v.clickDashBoard();
			IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
			
			String amcName=qvd_v.getAMCName();
			System.out.println(amcName);
			String amcScopesCount=qvd_v.getAMCScopeCount();
			System.out.println(amcScopesCount);
			
			//AMC Name
			if (qvd_v.compareRes(DBP.expAMCNameMulti,amcName,true)){
				result_AMCName="Passed: Expected= "+DBP.expAMCNameMulti+"; Actual= "+amcName;
			}else{
				result_AMCName="#Failed!#: Expected= "+DBP.expAMCNameMulti+"; Actual= "+amcName;
			}
			System.out.println("result_AMCName = "+result_AMCName);
			Description="Verifying Awaiting Manual Clean Name in Daily Dashboard Screen for "+facilityName;;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_AMCName+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_AMCName);		
			//AMC Scope Count
			if (qvd_v.compareRes(DBP.expAMCScopesCountMulti,amcScopesCount,true)){
				result_AMCScopeCount="Passed: Expected= "+DBP.expAMCScopesCountMulti+"; Actual= "+amcScopesCount;
			}else{
				result_AMCScopeCount="#Failed!#: Expected= "+DBP.expAMCScopesCountMulti+"; Actual= "+amcScopesCount;
			}
			System.out.println("result_AMCScopeCount = "+result_AMCScopeCount);
			Description="Verifying Scopes Count which are Awaiting Manual Clean for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_AMCScopeCount+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_AMCScopeCount);
			//AMC color below 45 minutes
			if (eachFac==1){
				scopeID_Fk=3;
				scopeID_Fk2=7;
			}else if (eachFac==2){
				scopeID_Fk=203;
				scopeID_Fk2=207;
			}else if (eachFac==3){
				scopeID_Fk=303;
				scopeID_Fk2=307;
			}
			// Make AwaitingManualCleaning to  wait time below 45 mins so that background color changes to blue
			stmt="update ScopeStatus set LastUpdatedDateTime=DATEADD(MI, -0, GETUTCDATE()) where ScopeID_FK in ("+scopeID_Fk+","+scopeID_Fk2+")";
			System.out.println("stmt="+stmt);
			conn= null;
			try{
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    		Statement update1 = conn.createStatement();
	    		update1.executeUpdate(stmt);
	    		conn.close();
			}
			catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
			//Verify Image AwaitingManualCleaning in mute yellow color
			//Unifia_Admin_Selenium.driver.navigate().refresh();
			Thread.sleep(2000);	
			String amcBelow45minColor=qvd_v.getAMCColor();
			
			if (qvd_v.compareRes(DBP.expAMCBelow45minColorMulti,amcBelow45minColor,true)){
				result_AMCBelow45minColor="Passed: Expected= "+DBP.expAMCBelow45minColorMulti+"; Actual= "+amcBelow45minColor;
			}else{
				result_AMCBelow45minColor="#Failed!#: Expected= "+DBP.expAMCBelow45minColorMulti+"; Actual= "+amcBelow45minColor;
			}
			System.out.println("result_AMCBelow45minColor = "+result_AMCBelow45minColor);
			Description="Verifying Blue color for Awaiting Manual Clean when time is below 45 minutes for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_AMCBelow45minColor+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_AMCBelow45minColor);
			
			// Make AwaitingManualCleaning to  wait time between 45 and 59 mins so that background color changes to muted yellow
			
			stmt="update ScopeStatus set LastUpdatedDateTime=DATEADD(MI, -46, GETUTCDATE()) where ScopeID_FK="+scopeID_Fk;
			System.out.println("stmt="+stmt);
			conn= null;
			try{
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    		Statement update1 = conn.createStatement();
	    		update1.executeUpdate(stmt);
	    		conn.close();
			}
			catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
			//Verify Image AwaitingManualCleaning in mute yellow color
			//Unifia_Admin_Selenium.driver.navigate().refresh();
			Thread.sleep(2000);	
			String amcBetween45n59mnsColor=qvd_v.getAMCColor();
			
			System.out.println(amcBetween45n59mnsColor);		
			//AMC color between 45 to 59 minutes
			if (qvd_v.compareRes(DBP.expAMCBetween45n59mnsColorMulti,amcBetween45n59mnsColor,true)){
				result_AMCBetween45n59mnsColor="Passed: Expected= "+DBP.expAMCBetween45n59mnsColorMulti+"; Actual= "+amcBetween45n59mnsColor;
			}else{
				result_AMCBetween45n59mnsColor="#Failed!#: Expected= "+DBP.expAMCBetween45n59mnsColorMulti+"; Actual= "+amcBetween45n59mnsColor;
			}
			System.out.println("result_AMCBetween45n59mnsColor = "+result_AMCBetween45n59mnsColor);
			Description="Verifying  Mute Yellow color for Awaiting Manual Clean when time is between 45 to 59 minutes for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_AMCBetween45n59mnsColor+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_AMCBetween45n59mnsColor);	
			// Make AwaitingManualCleaning to  wait time greater than 59 mins so that background color changes to muted Red
			stmt="update ScopeStatus set LastUpdatedDateTime=DATEADD(hh, -1, GETUTCDATE()) where ScopeID_FK="+scopeID_Fk;
			System.out.println("stmt="+stmt);
			try{
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    		Statement update1 = conn.createStatement();
	    		update1.executeUpdate(stmt);
	    		conn.close();
			}
			catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
			//Verify Image AwaitingManualCleaning in mute yellow color
			//Unifia_Admin_Selenium.driver.navigate().refresh();
			Thread.sleep(2000);
			String amcGreaterThan59mnsColor=qvd_v.getAMCColor();
			System.out.println(amcGreaterThan59mnsColor);
			//AMC color greater than 59 minutes
			if (qvd_v.compareRes(DBP.expAMCGreaterThan59mnsColorMulti,amcGreaterThan59mnsColor,true)){
				result_AMCGreaterThan59mnsColor="Passed: Expected= "+DBP.expAMCGreaterThan59mnsColorMulti+"; Actual= "+amcGreaterThan59mnsColor;
			}else{
				result_AMCGreaterThan59mnsColor="#Failed!#: Expected= "+DBP.expAMCGreaterThan59mnsColorMulti+"; Actual= "+amcGreaterThan59mnsColor;
			}
			System.out.println("result_AMCGreaterThan59mnsColor = "+result_AMCGreaterThan59mnsColor);
			Description="Verifying Pink Color for Awaiting Manual Clean when time is greater than 59 minutes for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_AMCGreaterThan59mnsColor+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_AMCGreaterThan59mnsColor);
		}
		TH.WriteToTextFile(ResFileName, TestSummary);
		//IHV.Exec_Test_Case(Unifia_Admin_Selenium.XMLFileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);

		if (TestSummary.contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.toLowerCase().contains("#failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	@AfterTest
	  public void PostTest() throws IOException{
	  	LP_A.CloseDriver();
	  }
	
	public void selectFromListBox(String listBoxXpath,String listEle){
		Unifia_Admin_Selenium.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(Unifia_Admin_Selenium.driver.findElementByXPath(listBoxXpath));   
		droplist.selectByVisibleText(listEle);
	}	
}