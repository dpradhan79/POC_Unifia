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

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class Awaiting_ReprocessingMulit {
	
	
	private QV_GeneralFunc QV_Gen;
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private Dashboard_Actions qvd_a;
	private String TestSummary= "\t\t\t DailyDashboard_AwaitingReprocessing_TestSummary \r\n\r\n"; 
	private String ResFileName="Multi_DailyDashboard_AwaitingReprocessing_TestSummary";
	private String ResFileNameXML="Multi_DailyDashboard_AwaitingReprocessing_Result";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	private String ForFileName;	
	private Dashboard_Actions DA;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	
	private int scopeID_Fk,scopeId_Fk2,scopeId_Fk3;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void AR_Verification(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException{
		
		String result_ARName, result_ARScopeCount, result_ARBelow45minColor, result_ARBetween45n59mnsColor, result_ARGreaterThan59mnsColor;
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
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Awaiting Reprocessing");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		String Expected,Description;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(20000);
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
			qvd_a.clickElement(DBP.arName);
			stmt="select name from scope where scopeid_pk in (select scopeid_fk from scopestatus where ScopeStateID_FK="+DBP.arScopeState+") and FacilityID_FK="+eachFac;
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				scopeID.add(RS.getString(1));
			}
			System.out.println ("Need to verify "+scopeID.size()+" rows in Awaiting Reprocessing for "+eachFac);
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
				StringBuffer resultDrillDown=gf.verifyTableContents(DBP.arTableContent,colsNvalues,"Scope Name");
				System.out.println(resultDrillDown);
				Description="Verifying drill down popup contents of Awaiting Reprocessing area for Row -"+(eachRec+1)+"for " +facilityName;
				Expected =Description;
				TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
				TestSummary=TestSummary+TestRes+"\r\n";
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
			}
			qvd_v.clickDashBoard();
			IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
			
			String arName=qvd_v.getARName();
			System.out.println(arName);
			String arScopesCount=qvd_v.getARScopeCount();
			System.out.println(arScopesCount);
			
			//AR Name
			if (qvd_v.compareRes(DBP.expARNameMulti,arName,true)){
				result_ARName="Passed: Expected= "+DBP.expARNameMulti+"; Actual= "+arName;
			}else{
				result_ARName="#Failed!#: Expected= "+DBP.expARNameMulti+"; Actual= "+arName;
			}
			System.out.println("result_ARName = "+result_ARName);
			Description="Verifying Awaiting Reprocessing Name in Daily Dashboard Screen for "+facilityName;;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_ARName+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ARName);
			//AR Scope Count
			if (qvd_v.compareRes(DBP.expARScopesCountMulti,arScopesCount,true)){
				result_ARScopeCount="Passed: Expected= "+DBP.expARScopesCountMulti+"; Actual= "+arScopesCount;
			}else{
				result_ARScopeCount="#Failed!#: Expected= "+DBP.expARScopesCountMulti+"; Actual= "+arScopesCount;
			}
			System.out.println("result_ARScopeCount = "+result_ARScopeCount);
			Description="Verifying Scopes Count which are Awaiting Reprocessing for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_ARScopeCount+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ARScopeCount);
			// Make AwaitingReprocessing to  wait time below 45 mins so that background color changes to blue
			if (eachFac==1){
				scopeID_Fk=25;
				scopeId_Fk2=12;
				scopeId_Fk3=5;
			}else if (eachFac==2){
				scopeID_Fk=225;
				scopeId_Fk2=212;
				scopeId_Fk3=205;
			}else if (eachFac==3){
				scopeID_Fk=325;
				scopeId_Fk2=312;
				scopeId_Fk3=305;
			}
			stmt="update ScopeStatus set LastUpdatedDateTime=DATEADD(MI, -0, GETUTCDATE()) where ScopeID_FK in ("+scopeID_Fk+","+ scopeId_Fk2+","+scopeId_Fk3+");";
			System.out.println("stmt="+stmt);
			conn= null;
			try{
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    		Statement update1 = conn.createStatement();
	    		update1.executeUpdate(stmt);//Making Awaiting Reprocessing less that 45 mins
	    		conn.close();
			}
			catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
			Thread.sleep(1000*3);
			String arBelow45minColor=qvd_v.getARColor();
			System.out.println(arBelow45minColor);
			//AR color below 45 minutes
			if (qvd_v.compareRes(DBP.expARBelow45minColorMulti,arBelow45minColor,true)){
				result_ARBelow45minColor="Passed: Expected= "+DBP.expARBelow45minColorMulti+"; Actual= "+arBelow45minColor;
			}else{
				result_ARBelow45minColor="#Failed!#: Expected= "+DBP.expARBelow45minColorMulti+"; Actual= "+arBelow45minColor;
			}
			System.out.println("result_ARBelow45minColor = "+result_ARBelow45minColor);
			Description="Verifying Blue color for Awaiting Reprocessing when time is below 45 minutes for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_ARBelow45minColor+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ARBelow45minColor);
			
			// Make AwaitingReprocessing to  wait time between 45 and 59 mins so that background color changes to muted yellow
			stmt="update ScopeStatus set LastUpdatedDateTime=DATEADD(MI, -46, GETUTCDATE()) where ScopeID_FK="+scopeID_Fk+";";
			System.out.println("stmt="+stmt);
			conn= null;
			try{
	    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	    		Statement update1 = conn.createStatement();
	    		update1.executeUpdate(stmt);//Making Awaiting Reprocessing time greater than 1 hour for Scope 12
	    		conn.close();
			}
			catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
			//Verify Image Awaiting Reprocessing in muted yellow color
			//Unifia_Admin_Selenium.driver.navigate().refresh();
			Thread.sleep(5000);
			String arBetween45n59mnsColor=qvd_v.getARColor();
			System.out.println(arBetween45n59mnsColor);
			//AR color between 45 to 59 minutes
			if (qvd_v.compareRes(DBP.expARBetween45n59mnsColorMulti,arBetween45n59mnsColor,true)){
				result_ARBetween45n59mnsColor="Passed: Expected= "+DBP.expARBetween45n59mnsColorMulti+"; Actual= "+arBetween45n59mnsColor;
			}else{
				result_ARBetween45n59mnsColor="#Failed!#: Expected= "+DBP.expARBetween45n59mnsColorMulti+"; Actual= "+arBetween45n59mnsColor;
			}
			System.out.println("result_ARBetween45n59mnsColor = "+result_ARBetween45n59mnsColor);
			Description="Verifying Mute Yellow color for Awaiting Reprocessing when time is between 45 to 59 minutes for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_ARBetween45n59mnsColor+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ARBetween45n59mnsColor);
			// Make Awaiting Reprocessing to  wait time greater than 59 mins so that background color changes to muted Red
			stmt="update ScopeStatus set LastUpdatedDateTime=DATEADD(hh, -1, GETUTCDATE()) where ScopeID_FK="+scopeID_Fk+";";
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
			//Verify Image AwaitingManualCleaning in muted red color
			//Unifia_Admin_Selenium.driver.navigate().refresh();
			Thread.sleep(5000);
			String arGreaterThan59mnsColor=qvd_v.getARColor();
			System.out.println(arGreaterThan59mnsColor);
			//AR color greater than 59 minutes
			if (qvd_v.compareRes(DBP.expARGreaterThan59mnsColorMulti,arGreaterThan59mnsColor,true)){
				result_ARGreaterThan59mnsColor="Passed: Expected= "+DBP.expARGreaterThan59mnsColorMulti+"; Actual= "+arGreaterThan59mnsColor;
			}else{
				result_ARGreaterThan59mnsColor="#Failed!#: Expected= "+DBP.expARGreaterThan59mnsColorMulti+"; Actual= "+arGreaterThan59mnsColor;
			}
			System.out.println("result_ARGreaterThan59mnsColor = "+result_ARGreaterThan59mnsColor);
			Description="Verifying Pink color for Awaiting Reprocessing when time is greater than 59 minutes for "+ facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_ARGreaterThan59mnsColor+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";	
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_ARGreaterThan59mnsColor);
			scopeID.clear();
		}
		TH.WriteToTextFile(ResFileName, TestSummary);
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

}
