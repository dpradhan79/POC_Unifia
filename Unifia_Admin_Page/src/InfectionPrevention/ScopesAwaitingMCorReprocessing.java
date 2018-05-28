package InfectionPrevention;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class ScopesAwaitingMCorReprocessing {
	
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private Dashboard_Actions qvd_a;
	
	private String TestSummary= "\t\t\t InfectionPrevention_ScopesAwaitingManualCleaningOrReprocessing_TestSummary \r\n\r\n"; 
	private String ResFileName="IP_ScopesAMCorAR_TestSummary";
	private String ResFileNameXML="IP_ScopesAMCorAR_Result";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	private String ForFileName;	
	
	private TestFrameWork.UnifiaAdminGeneralFunctions.InfectionPreventionDashboard IPD;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestFrameWork.Emulator.Emulator_Actions EM_A;
	private static InfectionPrevention.ScopesReprocessedToday SRT;
	
	private String amcTableContentXpath=DBP.amcTableContent+"/div[2]/table/tbody";
	private String arTableContentXpath=DBP.arTableContent+"/div[2]/table/tbody";
	
	private String StorageA = "Storage Area A";
	private String reprocessor="Reprocessor 1";
	private String procedureRoom="Procedure Room 2";
	private String sink="Sink 1";
	private String result="";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void ScopesAMCorAR_Verification(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException{
		
		String result_SAMCorARHeader, result_SAMCorARCount, result_SAMCorARColor;
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
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Infection Prevention - ScopesAwaitingManualCleaningOrReprocessing");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		String Expected,Description;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(20000);
		
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		
		Map<String, String> amcDailyDashboardData=get_AMC_DailyDashboardData();
		int amcTableRowCount = gf.getTableRowCount(amcTableContentXpath);
		int amcScopesCount=Integer.parseInt(qvd_v.getAMCScopeCount());
		
		Map<String, String> arDailyDashboardData=get_AR_DailyDashboardData();
		int arTableRowCount = gf.getTableRowCount(arTableContentXpath);
		int arScopesCount=Integer.parseInt(qvd_v.getARScopeCount());
		
		int amc_arScopesCount=arScopesCount+amcScopesCount;
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		
		String samcOrARHeader=IP_A.getHeader(IPD.samcOrARHeader);
		System.out.println(samcOrARHeader);
		String samcOrARCount=IP_A.getScopeCount(IPD.samcOrARCount);
		System.out.println(samcOrARCount);
		String samcOrARColor=IP_A.getColor(IPD.samcOrARColor);
		System.out.println(samcOrARColor);
		
		//samcOrARHeader
		if (qvd_v.compareRes(IPD.expSAMCOrARHeader,samcOrARHeader,true)){
			result_SAMCorARHeader="Passed: Expected= "+IPD.expSAMCOrARHeader+"; Actual= "+samcOrARHeader;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SAMCorARHeader="#Failed!#: Expected= "+IPD.expSAMCOrARHeader+"; Actual= "+samcOrARHeader;
		}
		System.out.println("result_SAMCorARHearder = "+result_SAMCorARHeader);
		Description="Verifying Scopes Awaiting Manual Cleaning or Reprocessing Header in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SAMCorARHeader+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SAMCorARHeader);
		
		//samcOrARScope Count
		if (qvd_v.compareRes(String.valueOf(amc_arScopesCount),samcOrARCount,true)){
			result_SAMCorARCount="Passed: Expected= "+String.valueOf(amc_arScopesCount)+"; Actual= "+samcOrARCount;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SAMCorARCount="#Failed!#: Expected= "+String.valueOf(amc_arScopesCount)+"; Actual= "+samcOrARCount;
		}
		System.out.println("result_SAMCorARCount = "+result_SAMCorARCount);
		Description="Verifying Scopes Awaiting Manual Cleaning or Reprocessing Scope Count in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SAMCorARCount+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SAMCorARCount);
		
		//samcOrARColor
		if (qvd_v.compareRes(IPD.expSAMCOrARColor,samcOrARColor,true)){
			result_SAMCorARColor="Passed: Expected= "+IPD.expSAMCOrARColor+"; Actual= "+samcOrARColor;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SAMCorARColor="#Failed!#: Expected= "+IPD.expSAMCOrARColor+"; Actual= "+samcOrARColor;
		}
		System.out.println("result_SAMCorARColor = "+result_SAMCorARColor);
		Description="Verifying Color of Scopes Awaiting Manual Cleaning or Reprocessing in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SAMCorARColor+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SAMCorARColor);
		IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		
		qvd_a.clickElement(IPD.samcOrARCount);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		
		for(int i=1;i<=amcTableRowCount;i++){
			//Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area with the data that is fetched from Daily Dashboard
			String colsNvalues="Scope Model=="+amcDailyDashboardData.get("ScopeModel"+i)+";Scope Name=="+amcDailyDashboardData.get("ScopeName"+i)+";Scope Serial Number=="+amcDailyDashboardData.get("ScopeSerialNumber"+i)+";Awaiting=="+amcDailyDashboardData.get("Awaiting"+i);
			StringBuffer resultDrillDown=gf.verifyTableContents(IPD.samcOrARTableContent,colsNvalues,"Scope Name");
			System.out.println(resultDrillDown);
			Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area with the data that is fetched from Daily Dashboard";
			Expected =Description;
			TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		}
		
		for(int i=1;i<=arTableRowCount;i++){
			//Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area with the data that is fetched from Daily Dashboard
			String colsNvalues="Scope Model=="+arDailyDashboardData.get("ScopeModel"+i)+";Scope Name=="+arDailyDashboardData.get("ScopeName"+i)+";Scope Serial Number=="+arDailyDashboardData.get("ScopeSerialNumber"+i)+";Awaiting=="+arDailyDashboardData.get("Awaiting"+i);
			StringBuffer resultDrillDown=gf.verifyTableContents(IPD.samcOrARTableContent,colsNvalues,"Scope Name");
			System.out.println(resultDrillDown);
			Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area with the data that is fetched from Daily Dashboard";
			Expected =Description;
			TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		}
		
		//Getting the drilldown popup data from database
		Connection conn=null;
		Statement statement=null;
		String stmt="";
		List<String> scopeID = new ArrayList<String>();
		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		statement = conn.createStatement();
		stmt="select name from scope where scopeid_pk in (select scopeid_fk from scopestatus where ScopeStateID_FK in ("+IPD.arScopeState+","+IPD.amcScopeState+"))";
		System.out.println("stmt="+stmt);
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			scopeID.add(RS.getString(1));
		}
		System.out.println ("Need to verify "+scopeID.size()+" rows in Scopes Awaiting Manual Cleaning Or Reprocessing");
		String scopeName,scopeSerialNum = null,scopeModel = null,ScopeState = null;
		
		for (int eachRec=0;eachRec<scopeID.size();eachRec++){
			scopeName=scopeID.get(eachRec);
			stmt="select s.SerialNumber, st.Name from Scope s join ScopeType ST on st.ScopeTypeID_PK=s.ScopeTypeID_FK where s.Name='"+scopeName+"'";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				scopeSerialNum=RS.getString(1);
				scopeModel=RS.getString(2);
			}
			stmt="select ST.Name from ScopeState ST join ScopeStatus SS on ST.ScopeStateID_PK=SS.ScopeStateID_FK join Scope S on S.ScopeID_PK=SS.ScopeID_FK where s.Name='"+scopeName+"'";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				ScopeState=(RS.getString(1));
			}
			if(ScopeState.equalsIgnoreCase("Awaiting Reprocessing")){
				ScopeState="Reprocessing";
			}else if(ScopeState.equalsIgnoreCase("Awaiting Manual Cleaning")){
				ScopeState="Manual Cleaning";
			}
			
			//Verifying the drilldown table contents with the values fetched from Database.
			String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+";Scope Serial Number=="+scopeSerialNum+";Awaiting=="+ScopeState;
			StringBuffer resultDrillDown=gf.verifyTableContents(IPD.samcOrARTableContent,colsNvalues,"Scope Name");
			System.out.println(resultDrillDown);
			Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area for Row - "+(eachRec+1);
			Expected =Description;
			TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		
		}
		
		//Scan scope THROUGH the Procedure Room	to get scope into Awaiting Manual Cleaning 
		String scope="Scope13";
		String Patient="MRN222222";
		//Checking out Scope from Storage Area
		boolean Res = EM_A.ScanItem(StorageA, "Scope", "", scope, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem(procedureRoom, "Scope", "", scope, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Patient", "", Patient, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Scope", "", scope, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Workflow Event", "", "Needs Cleaning", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Workflow Event", "", "Available", "");
		System.out.println(Res);
		
		//verifying the Daily dashboard and IP Dashboard to see that the scope is shown in Awaiting Manual Cleaning
		ScopeState="Manual Cleaning";
		stmt="select S.SerialNumber, ST.Name from Scope S join ScopeType ST on S.ScopeTypeID_FK=ST.ScopeTypeID_PK where S.Name='"+scope+"'";
		System.out.println("stmt="+stmt);
		
		RS = statement.executeQuery(stmt);
		while(RS.next()){
			scopeSerialNum=RS.getString(1);
			scopeModel=RS.getString(2);
		}
		RS.close();
		UAS.driver.navigate().refresh();
		qvd_v.clickDashBoard();
		Thread.sleep(2000);
		
		qvd_a.clickElement(DBP.amcName);
		
		//Verifying the Daily Dashboard's Scopes Awaiting Manual Cleaning table contents to see that scope is shown in Awaiting Manual Cleaning state
		String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scope+";Serial Number=="+scopeSerialNum;
		StringBuffer resultDrillDown=gf.verifyTableContents(DBP.amcTableContent,colsNvalues,"Scope Name");
		System.out.println(resultDrillDown);
		Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning area on Daily Dashboard screen for - "+scope;
		Expected =Description;
		TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		qvd_a.clickElement(IPD.samcOrARCount);
		
		//Verifying the Infection Prevention Dashboard's Scopes Awaiting Manual Cleaning Or Reprocessing table contents to see that scope is shown in Awaiting Manual Cleaning state
		colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scope+";Scope Serial Number=="+scopeSerialNum+";Awaiting=="+ScopeState;
		resultDrillDown=gf.verifyTableContents(IPD.samcOrARTableContent,colsNvalues,"Scope Name");
		System.out.println(resultDrillDown);
		Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area on Infection Prevention Dashboard screen for - "+scope;
		Expected =Description;
		TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		
		//Scan scope into Sink to get scope OUT of Awaiting Manual Cleaning 
		Res = EM_A.ScanItem(sink, "Scope", "", scope, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Staff", "Tech", "Tech7 Tech7(T07)", "");
		System.out.println(Res);
		
		UAS.driver.navigate().refresh();
		
		qvd_v.clickDashBoard();
		Thread.sleep(2000);
		
		qvd_a.clickElement(DBP.amcName);
		
		//Verifying the Daily Dashboard's Scopes Awaiting Manual Cleaning table contents to see that scope is not shown in Awaiting Manual Cleaning state
		colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scope+";Serial Number=="+scopeSerialNum;
		resultDrillDown=gf.verifyTableContents(DBP.amcTableContent,colsNvalues,"Scope Name");
		Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning area on Daily Dashboard screen for - "+scope;
		Expected =Description;
		if(resultDrillDown.toString().contains("#Failed!#")){
			result="Pass - Drill down popup contents of Scopes Awaiting Manual Cleaning area on Daily Dashboard screen doesnot contain "+scope;
		}else if(resultDrillDown.toString().contains("Pass")){
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Drill down popup contents of Scopes Awaiting Manual Cleaning area on Daily Dashboard screen contains "+scope;
		}
		System.out.println(result);
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		qvd_a.clickElement(IPD.samcOrARCount);
		
		//Verifying the Infection Prevention Dashboard's Scopes Awaiting Manual Cleaning Or Reprocessing table contents to see that scope is not shown in Awaiting Manual Cleaning state
		colsNvalues="Scope Name=="+scope;
		resultDrillDown=gf.verifyTableContents(IPD.samcOrARTableContent,colsNvalues,"Scope Name");
		Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area on Infection Prevention Dashboard screen to see that "+scope+" is not present in the table";
		Expected =Description;
		if(resultDrillDown.toString().contains("#Failed!#")){
			result="Pass - Drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area on Infection Prevention Dashboard screen doesnot contain "+scope;
		}else if(resultDrillDown.toString().contains("Pass")){
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area on Infection Prevention Dashboard screen contains "+scope;
		}
		System.out.println(result);
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		//Complete Sink scans to get scope out of sink to get scope INTO of Awaiting Reprocessing  
		Res = EM_A.ScanItem(sink, "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		
		
		UAS.driver.navigate().refresh();
		qvd_v.clickDashBoard();
		Thread.sleep(2000);
		
		qvd_a.clickElement(DBP.arName);
		
		//Verifying the Daily Dashboard's Scopes Awaiting Reprocessing table contents to see that scope is shown in Awaiting Reprocessing state
		colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scope+";Serial Number=="+scopeSerialNum;
		resultDrillDown=gf.verifyTableContents(DBP.arTableContent,colsNvalues,"Scope Name");
		System.out.println(resultDrillDown);
		Description="Verifying drill down popup contents of Scopes Awaiting Reprocessing area on Daily Dashboard screen for - "+scope;
		Expected =Description;
		TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		
		
		ScopeState="Reprocessing";
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		qvd_a.clickElement(IPD.samcOrARCount);
		
		//Verifying the Infection Prevention Dashboard's Scopes Awaiting Manual Cleaning Or Reprocessing table contents to see that scope is shown in Awaiting Manual Cleaning state
		colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scope+";Scope Serial Number=="+scopeSerialNum+";Awaiting=="+ScopeState;
		resultDrillDown=gf.verifyTableContents(IPD.samcOrARTableContent,colsNvalues,"Scope Name");
		System.out.println(resultDrillDown);
		Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area on Infection Prevention Dashboard screen for - "+scope;
		Expected =Description;
		TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		
		//Scan scope into OER (Not using KE) to get scope OUT of Awaiting Reprocessing  
		Res = EM_A.ScanItem(reprocessor, "Scope", "", scope, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(reprocessor, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		UAS.driver.navigate().refresh();
		qvd_v.clickDashBoard();
		Thread.sleep(2000);
		
		qvd_a.clickElement(DBP.arName);
		
		//Verifying the Daily Dashboard's Scopes Awaiting Reprocessing table contents to see that scope is not shown in Awaiting Reprocessing state
		colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scope+";Serial Number=="+scopeSerialNum;
		resultDrillDown=gf.verifyTableContents(DBP.arTableContent,colsNvalues,"Scope Name");
		Description="Verifying drill down popup contents of Scopes Awaiting Reprocessing area on Daily Dashboard screen for - "+scope;
		Expected =Description;
		if(resultDrillDown.toString().contains("#Failed!#")){
			result="Pass - Drill down popup contents of Scopes Awaiting Reprocessing area on Daily Dashboard screen doesnot contain "+scope;
		}else if(resultDrillDown.toString().contains("Pass")){
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Drill down popup contents of Scopes Awaiting Reprocessing area on Daily Dashboard screen contains "+scope;
		}
		System.out.println(result);
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		qvd_a.clickElement(IPD.samcOrARCount);
		
		//Verifying the Infection Prevention Dashboard's Scopes Awaiting Manual Cleaning Or Reprocessing table contents to see that scope is not shown in Awaiting Reprocessing state
		colsNvalues="Scope Name=="+scope;
		resultDrillDown=gf.verifyTableContents(IPD.samcOrARTableContent,colsNvalues,"Scope Name");
		Description="Verifying drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area on Infection Prevention Dashboard screen to see that "+scope+" is not present in the table";
		Expected =Description;
		if(resultDrillDown.toString().contains("#Failed!#")){
			result="Pass - Drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area on Infection Prevention Dashboard screen doesnot contain "+scope;
		}else if(resultDrillDown.toString().contains("Pass")){
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Drill down popup contents of Scopes Awaiting Manual Cleaning Or Reprocessing area on Infection Prevention Dashboard screen contains "+scope;
		}
		System.out.println(result);
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
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
	 
	 private Map<String, String> get_AMC_DailyDashboardData(){
		 Map<String, String> dailyDashboardData = new HashMap<String, String>();
		 qvd_a.clickElement(DBP.amcName);
		 int rowCount = gf.getTableRowCount(amcTableContentXpath);
			if (rowCount >= 1) {
				for (int i = 1; i <= rowCount; i++) {
					if (UAS.driver.findElementsByXPath(amcTableContentXpath + "/tr[" + i + "]/td[2]/span").size() > 0) {
						dailyDashboardData.put("ScopeModel" + i,UAS.driver.findElementByXPath(amcTableContentXpath + "/tr[" + i	+ "]/td[1]/span").getText());
						dailyDashboardData.put("ScopeName" + i,UAS.driver.findElementByXPath(amcTableContentXpath + "/tr[" + i+ "]/td[2]/span").getText());
						dailyDashboardData.put("ScopeSerialNumber" + i,UAS.driver.findElementByXPath(amcTableContentXpath + "/tr[" + i+ "]/td[3]/span").getText());
						dailyDashboardData.put("Awaiting" + i, "Manual Cleaning");
					}
				}
			}
		 return dailyDashboardData;
	 }
	 
	 private Map<String, String> get_AR_DailyDashboardData(){
		 Map<String, String> dailyDashboardData = new HashMap<String, String>();
		 qvd_a.clickElement(DBP.arName);
		 int rowCount = gf.getTableRowCount(arTableContentXpath);
			if (rowCount >= 1) {
				for (int i = 1; i <= rowCount; i++) {
					if (UAS.driver.findElementsByXPath(arTableContentXpath + "/tr[" + i + "]/td[2]/span").size() > 0) {
						dailyDashboardData.put("ScopeModel" + i,UAS.driver.findElementByXPath(arTableContentXpath + "/tr[" + i	+ "]/td[1]/span").getText());
						dailyDashboardData.put("ScopeName" + i,UAS.driver.findElementByXPath(arTableContentXpath + "/tr[" + i+ "]/td[2]/span").getText());
						dailyDashboardData.put("ScopeSerialNumber" + i,UAS.driver.findElementByXPath(arTableContentXpath + "/tr[" + i+ "]/td[3]/span").getText());
						dailyDashboardData.put("Awaiting" + i, "Reprocessing");
					}
				}
			}
		 return dailyDashboardData;
	 }

}
