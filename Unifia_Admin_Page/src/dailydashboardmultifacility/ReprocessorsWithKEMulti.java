package dailydashboardmultifacility;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.graphwalker.core.condition.StopConditionException;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class ReprocessorsWithKEMulti {	
	public QV_GeneralFunc QV_Gen;
	public GeneralFunc gf;
	
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public String TestSummary= "\t\t\t DailyDashboard_ReprocessorsWithKE_TestSummary \r\n"; 
	public String ResFileName="Multi_DailyDashboard_ReprocessorsWithKE_TestSummary";
	private String ResFileNameXML="Multi_DailyDashboard_ReprocessorsWithKE_Result";
	public TestFrameWork.TestHelper TH;
	public String TestRes;
	public String ForFileName;
	public Dashboard_Actions DA;
	private TestDataFunc TDF;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	
	private TestFrameWork.Emulator.GetIHValues IHV;
	private Dashboard_Verification DV;

	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private Dashboard_Actions qvd_a;
	public int KE=1;
	public int Bioburden=0;
	public int Culture=0;

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void reprocessorAreaWithKEVerifications(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, SQLException, AWTException, ParseException, IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Reporecssors KE");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
    	String Description;
	
		gf.SyncRemoteMachineTime(UAS.KE_Env, UAS.KEMachine_Username, UAS.KEMachine_pswd, URL);
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
    	TDF.insertMasterData(UAS.url, UAS.user, UAS.pass, KE, Bioburden, Culture);
		gf.RestartIISServices(UAS.Env, UAS.userName, UAS.IISPass);
	
    	TDF.insertKE_DD_Data(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
    	Thread.sleep(120000);
    
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(20000);
		//UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		qvd_v.clickDashBoard();
		
		//Reprocessor1
    	Description="Verifying reprocessor1 Room Name";
    	reprocessorResult(Description,DBP.expRepro1NameMulti,DBP.rep1Name,false);
    	Description="Verifying reprocessor1 Status is Available";
    	reprocessorResult(Description,DBP.KEexpStatusRepro1Multi,DBP.KERep1Status,false);
    	Description="Verifying reprocessor1 color is green";
    	reprocessorResult(Description,DBP.KEexpRepAvailableColorGreenMulti,DBP.KERep1color,true);
    	Description="Verifying reprocessor1 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro1Multi,DBP.KERep1ScopeName1,false);
    	Description="Verifying reprocessor1 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro1Multi,DBP.KERep1ScopeName2,false);
    	
    	//Reprocessor2
    	Description="Verifying reprocessor2 Room Name";
    	reprocessorResult(Description,DBP.expRepro2NameMulti,DBP.rep2Name,false);
    	Description="Verifying reprocessor2 Status";
    	reprocessorResult(Description,DBP.KEexpStatusRepro2Multi,DBP.KERep2Status,false);
    	Description="Verifying reprocessor2 color is Red";
    	reprocessorResult(Description,DBP.KEexpRepUnExpTermColorAmberMulti,DBP.KERep2color,true);
    	Description="Verifying reprocessor2 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro2Multi,DBP.KERep2ScopeName1,false);
    	Description="Verifying reprocessor2 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro2Multi,DBP.KERep2ScopeName2,false);
    	
    	//Reprocessor4
    	Description="Verifying reprocessor4 Room Name";
    	reprocessorResult(Description,DBP.expRepro4NameMulti,DBP.rep4Name,false);
    	Description="Verifying reprocessor4 Status";
    	reprocessorResult(Description,DBP.KEexpStatusRepro4Multi,DBP.KERep4Status,false);
    	Description="Verifying reprocessor4 color is purple";
    	reprocessorResult(Description,DBP.KEexpRepProcessingColorPurpleMulti,DBP.KERep4color,true);
    	Description="Verifying reprocessor4 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro4Multi,DBP.KERep4ScopeName1Multi,false);
    	Description="Verifying reprocessor4 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro4Multi,DBP.KERep4ScopeName2,false);
    	
    	//Reprocessor5
    	Description="Verifying reprocessor5 Room Name";
    	reprocessorResult(Description,DBP.expRepro5NameMulti,DBP.rep5Name,false);
    	Description="Verifying reprocessor5 Status";
    	reprocessorResult(Description,DBP.KEexpStatusRepro5Multi,DBP.KERep5Status,false);
    	Description="Verifying reprocessor5 color is purple";
    	reprocessorResult(Description,DBP.KEexpRepUnExpTermColorAmberMulti,DBP.KERep5color,true);
    	Description="Verifying reprocessor5 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro5Multi,DBP.KERep5ScopeName1,false);
    	Description="Verifying reprocessor5 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro5Multi,DBP.KERep5ScopeName2,false);
    	
		qvd_a.clickElement(DBP.cabNext);

    	//Reprocessor6
    	Description="Verifying reprocessor6 Room Name";
    	reprocessorResult(Description,DBP.expRepro6NameMulti,DBP.rep6Name,false);
    	Description="Verifying reprocessor6 Status";
    	reprocessorResult(Description,DBP.KEexpStatusRepro6Multi,DBP.KERep6Status,false);
    	Description="Verifying reprocessor6 color is black";
    	reprocessorResult(Description,DBP.KEexpRepProcessingColorblackMulti,DBP.KERep6color,true);
    	Description="Verifying reprocessor6 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro6Multi,DBP.KERep6ScopeName1,false);
    	Description="Verifying reprocessor5 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro6Multi,DBP.KERep6ScopeName2,false);
    	//final result
    	TestSummary=TestSummary+TestRes+"\r\n";
    	TH.WriteToTextFile(ResFileName, TestSummary);
    	IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (TestSummary.contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.contains("#Failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	@AfterTest
	  public void PostTest() throws IOException{
	  	LandingPage_Actions.CloseDriver();
	  }
	
	public void reprocessorResult(String description,String expVal,String objXpath, boolean colorVerify) throws InterruptedException{
		String applicationVal, result;
		boolean isExpBlank=false;
		if (expVal.equals("")){
			isExpBlank=true;
		}
		if (colorVerify){
			applicationVal=DV.getColor(objXpath);
		}else{
			applicationVal=DV.getTextReproc(objXpath,isExpBlank);
		}
		if (DV.compareRes(expVal,applicationVal,true)){
			result="Passed: Expected= "+expVal+";Actual= "+applicationVal;
		}else{
			result="#Failed!#: Expected= "+expVal+";Actual= "+applicationVal;
		}
		if (TestRes!=(null) && !TestRes.isEmpty()){
			TestRes=TestRes+description+"--  "+result+"\r\n";
		}else{
			TestRes=description+"--  "+result+"\r\n";
		}
		IHV.Exec_Log_Result(ResFileNameXML, description, description, result);
	}
	

	
	
	
}
