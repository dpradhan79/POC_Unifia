package dailydashboardmultifacility;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
//import org.sikuli.script.*;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QlikView.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.QVDashboard.*;
public class OutOfFacilityMulti {
	
	
	private QV_GeneralFunc QV_Gen;
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private Dashboard_Actions qvd_a;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private String TestSummary= "\t\t\t DailyDashboard_OOF_TestSummary \r\n\r\n"; 
	private String ResFileName="Multi_DailyDashboard_OOF_TestSummary";
	private String ResFileNameXML="Multi_DailyDashboard_OOF_Result";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	private String ForFileName;	
	private Dashboard_Actions DA;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void outofFacility_verification(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException{
		String result_OOFName,result_OOFScopeCount;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Out Of Facility");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(20000);
		//UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		qvd_v.clickDashBoard();
		qvd_a.clickElement(DBP.oofName);
		//Getting the drilldown popup data from database
		Connection conn=null;
		Statement statement=null;
		String Expected,Description;
		String stmt="";
		List<String> scopename = new ArrayList<String>();
		List<String> scopeID = new ArrayList<String>();
		for (int eachFac=1;eachFac<=3;eachFac++){
			scopename.clear();
			scopeID.clear();
			String facilityName= "Facility "+eachFac;
			System.out.println("Verifying for "+facilityName);
			gf.selectFromListBox(DBP.selectFacility,facilityName);
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select name,scopeid_pk from scope where scopeid_pk in (select scopeid_fk from scopestatus where ScopeStateID_FK="+DBP.oofScopeState+") and FacilityID_FK="+eachFac;;
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				scopename.add(RS.getString(1));
				scopeID.add(RS.getString(2));
			}
			System.out.println ("Need to verify "+scopeID.size()+" rows in out of facility");
			String scopeName,scopeSerialNum = null,scopeModel = null,destination = null,idofScope=null;
			String daysOutofFacility=null;
			for (int eachRec=0;eachRec<scopename.size();eachRec++){
				scopeName=scopename.get(eachRec);
				idofScope=scopeID.get(eachRec);
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
				stmt="select round((DATEDIFF(hour,ih.ReceivedDateTime,GETUTCDATE()))/24,1) from itemhistory ih where cycleeventid_fk="+DBP.oofCycleEvent+" and ScanItemID_FK="+idofScope;
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				double hrs = 0;
				while(RS.next()){
					//daysOutofFacility=(RS.getDouble(1));
					daysOutofFacility=(RS.getString(1));
				}
				stmt="select name from barcode where barcodeid_pk=(select scanitemid_fk from itemhistory where ScanItemTypeID_FK="+DBP.scanItemTypeId+" and "
					+ "associationid_fk=(select associationid_fk from itemhistory where cycleeventid_fk="+DBP.oofCycleEvent+" and ScanItemID_FK="+idofScope+"))";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					destination=(RS.getString(1));
				}
				System.out.println(daysOutofFacility);
				String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+"; Serial Number=="+scopeSerialNum+";Days Out of Facility=="+daysOutofFacility+";Destination=="+destination;
				StringBuffer resultDrillDown=gf.verifyTableContents(DBP.oofTableContent,colsNvalues,"Scope Name");
				System.out.println(resultDrillDown);
				Description="Verifying drill down popup contents of Out of Facility area for Row -"+(eachRec+1) +" for "+facilityName;;
				Expected =Description;
				TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
				TestSummary=TestSummary+TestRes+"\r\n";
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
			}
			qvd_v.clickDashBoard();
			IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);	
			String oofName=qvd_v.getOOFName();
			System.out.println(oofName);
			String oofScopesCount=qvd_v.getOOFScopeCount();
			System.out.println(oofScopesCount);		
			//OOF Name
			if (qvd_v.compareRes(DBP.expOOFNameMulti,oofName,true)){
				result_OOFName="Passed: Expected= "+DBP.expOOFNameMulti+"; Actual= "+oofName;
			}else{
				result_OOFName="#Failed!#: Expected= "+DBP.expOOFNameMulti+"; Actual= "+oofName;
			}
			System.out.println("result_OOFName = "+result_OOFName);
			Description="Verifying Out Of Facility Name in Daily Dashboard Screen for " +facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_OOFName+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_OOFName);	
			//OOF Scope Count
			if (qvd_v.compareRes(DBP.expOOFScopeCountMulti,oofScopesCount,true)){
				result_OOFScopeCount="Passed: Expected= "+DBP.expOOFScopeCountMulti+"; Actual= "+oofScopesCount;
			}else{
				result_OOFScopeCount="#Failed!#: Expected= "+DBP.expOOFScopeCountMulti+"; Actual= "+oofScopesCount;
			}
			System.out.println("result_OOFScopeCount = "+result_OOFScopeCount);
			Description="Verifying Scopes Count which are in Out Of Facility for " +facilityName;;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_OOFScopeCount+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_OOFScopeCount);
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
	  	LandingPage_Actions.CloseDriver();
	  }

}