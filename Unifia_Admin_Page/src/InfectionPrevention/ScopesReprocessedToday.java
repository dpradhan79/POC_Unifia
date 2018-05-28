package InfectionPrevention;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
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
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;


public class ScopesReprocessedToday {
	
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private Dashboard_Actions qvd_a;
	private String TestSummary= "\t\t\t InfectionPrevention_ScopesReprocessedToday_TestSummary \r\n\r\n"; 
	private String ResFileName="IP_ScopesReprocessedToday_TestSummary";
	private String ResFileNameXML="IP_ScopesReprocessedToday_Result";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	private String ForFileName;	
	private TestFrameWork.UnifiaAdminGeneralFunctions.InfectionPreventionDashboard IPD;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestFrameWork.Emulator.Emulator_Actions EM_A;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	
	private String StorageA = "Storage Area A";
	private String Reprocessor="Reprocessor 2";
	private String procedureRoom="Procedure Room 1";
	private String sink="Sink 1";

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void ScopesReprocessedToday_Verification(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException, ParseException{
		String result_SRTHeader, result_SRTCount, result_SRTColor;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		dateFormat=new SimpleDateFormat("YYYY-MM-dd");
		String todaysDate=dateFormat.format(date);
		System.out.println(todaysDate);
		ResFileName=ResFileName+"_"+ForFileName;
		//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Infection Prevention - Scopes Reprocessed Today");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		String Expected,Description;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(20000);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		int UTCTimeDiffInHours=gf.getUTCTimeDiffInHrs();
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		
		String srtHeader=IP_A.getHeader(IPD.srtHeader);
		System.out.println(srtHeader);
		String srtScopeCount=IP_A.getScopeCount(IPD.srtCount);
		System.out.println(srtScopeCount);
		String srtColor=IP_A.getColor(IPD.srtColor);
		System.out.println(srtColor);
		
		//srt Header
		if (qvd_v.compareRes(IPD.expSRTHeader,srtHeader,true)){
			result_SRTHeader="Passed: Expected= "+IPD.expSRTHeader+"; Actual= "+srtHeader;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SRTHeader="#Failed!#: Expected= "+IPD.expSRTHeader+"; Actual= "+srtHeader;
		}
		System.out.println("result_SRTHeader = "+result_SRTHeader);
		Description="Verifying Scopes Reprocessed Today Header in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SRTHeader+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SRTHeader);
		
		//srt Color
		if (qvd_v.compareRes(IPD.expSRTColor,srtColor,true)){
			result_SRTColor="Passed: Expected= "+IPD.expSRTColor+"; Actual= "+srtColor;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SRTColor="#Failed!#: Expected= "+IPD.expSRTColor+"; Actual= "+srtColor;
		}
		System.out.println("result_SRTColor = "+result_SRTColor);
		Description="Verifying Color of Scopes Reprocessed Today in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SRTColor+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SRTColor);IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		
		//Getting the drilldown popup data from database
		qvd_a.clickElement(IPD.srtCount);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		
		// checking the Time Format
		IP_V.verifyTimeFormat("Infection Prevention - Scopes Reprocessed Today ",IPD.srtTimeProcessed);
		
		Connection conn=null;
		Statement statement=null;
		String stmt="";
		List<String> scopeID = new ArrayList<String>();
		Map<String, String> Scope_ItemHistoryIDs = new HashMap<String, String>();
		
		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		statement = conn.createStatement();
		
		stmt="select name from scope where scopeid_pk in (select ScanItemID_FK from ItemHistory where CycleEventID_FK=18 and ReceivedDateTime > '"+todaysDate+" 0"+UTCTimeDiffInHours+":00:00')";
		System.out.println("stmt="+stmt);
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			scopeID.add(RS.getString(1));
		}
		
		for (int eachRec=0;eachRec<scopeID.size();eachRec++){
			stmt="select MAX(ItemHistoryID_PK) from ItemHistory where ScanItemTypeID_FK=1 and CycleEventID_FK=18 and ReceivedDateTime > '"
					+todaysDate+" 0"+UTCTimeDiffInHours+":00:00' and ScanItemID_FK=(select ScopeID_PK from Scope where name='"+scopeID.get(eachRec)+"')";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				Scope_ItemHistoryIDs.put(scopeID.get(eachRec), RS.getString(1));
			}
		}
		
		//srt count
		String expSRTCount=String.valueOf(scopeID.size());
		if (qvd_v.compareRes(expSRTCount,srtScopeCount,true)){
			result_SRTCount="Passed: Expected= "+expSRTCount+"; Actual= "+srtScopeCount;
		}else{
			result_SRTCount="#Failed!#: Expected= "+expSRTCount+"; Actual= "+srtScopeCount;
		}
		System.out.println("result_SRTCount = "+result_SRTCount);
		Description="Verifying Count of Scopes Reprocessed Today in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SRTCount+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SRTCount);
		
		System.out.println ("Need to verify "+scopeID.size()+" rows in Scopes Reprocessed Today");
		String scopeName= null,scopeSerialNum = null,scopeModel = null,reprocessor = null;
		
		//Verifying UI values with the data fetched from database
		for (int eachRec=0;eachRec<scopeID.size();eachRec++){
			scopeName=scopeID.get(eachRec);
			stmt="select s.SerialNumber, st.Name from Scope s join ScopeType ST on st.ScopeTypeID_PK=s.ScopeTypeID_FK where s.Name='"+scopeName+"'";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				scopeSerialNum=RS.getString(1);
				scopeModel=RS.getString(2);
			}
			
			stmt="select l.name from Location l join ItemHistory IH on IH.LocationID_FK=l.LocationID_PK where IH.ItemHistoryID_PK="+Scope_ItemHistoryIDs.get(scopeName);
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				reprocessor=RS.getString(1);
			}
			
			//Verifying the drilldown table contents with the values fetched from Database.
			String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+";Scope Serial Number=="+scopeSerialNum+";Reprocessor=="+reprocessor;
			StringBuffer resultDrillDown=gf.verifyTableContents(IPD.srtTableContent,colsNvalues,"Scope Name");
			System.out.println(resultDrillDown);
			Description="Verifying drill down popup contents of Scopes Reprocessed Today area for Row - "+(eachRec+1);
			Expected =Description;
			TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		}
		
		//Reprocess single scope from cabinet  for NON KE OER
		scopeName="Scope1";
		boolean Res = EM_A.ScanItem(StorageA, "Scope", "", scopeName, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(Reprocessor, "Scope", "", scopeName, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(Reprocessor, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Thread.sleep(40000); //waiting 40 seconds to complete reprocessing
		Res = EM_A.ScanItem(Reprocessor, "Scope", "", scopeName, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(Reprocessor, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		stmt="select S.SerialNumber, ST.Name from Scope S join ScopeType ST on S.ScopeTypeID_FK=ST.ScopeTypeID_PK where S.Name='"+scopeName+"'";
		System.out.println("stmt="+stmt);
		
		RS = statement.executeQuery(stmt);
		while(RS.next()){
			scopeSerialNum=RS.getString(1);
			scopeModel=RS.getString(2);
		}
		RS.close();
		UAS.driver.navigate().refresh();
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		qvd_a.clickElement(IPD.srtCount);
		
		String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+";Scope Serial Number=="+scopeSerialNum+";Reprocessor=="+Reprocessor;
		StringBuffer resultDrillDown=gf.verifyTableContents(IPD.srtTableContent,colsNvalues,"Scope Name");
		System.out.println(resultDrillDown);
		Description="Verifying drill down popup contents of Scopes Reprocessed Today area contains the scope details that is reprocessed now";
		Expected =Description;
		TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		
		//Reprocess multiple scopes while going through full workflow
		String scope1="Scope4";
		String scope2="Scope8";
		String Patient="MRN111111";
		//Checking out Scope from Storage Area
		Res = EM_A.ScanItem(StorageA, "Scope", "", scope1, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Scope", "", scope2, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem(procedureRoom, "Scope", "", scope1, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Scope", "", scope2, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Patient", "", Patient, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Scope", "", scope1, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Scope", "", scope2, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(procedureRoom, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem(sink, "Scope", "", scope1, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Staff", "Tech", "Tech7 Tech7(T07)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem(sink, "Scope", "", scope2, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Staff", "Tech", "Tech7 Tech7(T07)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Test Result", "", "Leak Test Pass", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Workflow Event", "", "Manual Clean Start", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(sink, "Workflow Event", "", "Manual Clean End", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem(Reprocessor, "Scope", "", scope1, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(Reprocessor, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Thread.sleep(40000); //waiting 40 seconds to complete reprocessing
		Res = EM_A.ScanItem(Reprocessor, "Scope", "", scope1, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(Reprocessor, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem(Reprocessor, "Scope", "", scope2, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(Reprocessor, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Thread.sleep(40000); //waiting 40 seconds to complete reprocessing
		Res = EM_A.ScanItem(Reprocessor, "Scope", "", scope2, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(Reprocessor, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		String scopes[]={scope1,scope2};
		for(String scope: scopes){
			stmt="select S.SerialNumber, ST.Name from Scope S join ScopeType ST on S.ScopeTypeID_FK=ST.ScopeTypeID_PK where S.Name='"+scope+"'";
			System.out.println("stmt="+stmt);
			
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				scopeSerialNum=RS.getString(1);
				scopeModel=RS.getString(2);
			}
			RS.close();
			UAS.driver.navigate().refresh();
			IP_A.Click_InfectionPrevention();
			IP_A.Click_IP_Dashboard();
			qvd_a.clickElement(IPD.srtCount);
			
			colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scope+";Scope Serial Number=="+scopeSerialNum+";Reprocessor=="+Reprocessor;
			resultDrillDown=gf.verifyTableContents(IPD.srtTableContent,colsNvalues,"Scope Name");
			System.out.println(resultDrillDown);
			Description="Verifying drill down popup contents of Scopes Reprocessed Today area contains the scope details of "+scope+" that is reprocessed now";
			Expected =Description;
			TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
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