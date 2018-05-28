package DailyDashBoard;

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

public class ReprocessorsWithKE {	
	public QV_GeneralFunc QV_Gen;
	public GeneralFunc gf;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public String TestSummary= "\t\t\t DailyDashboard_ReprocessorsWithKE_TestSummary \r\n"; 
	public String ResFileName="DailyDashboard_ReprocessorsWithKE_TestSummary";
	private String ResFileNameXML="DailyDashboard_ReprocessorsWithKE_Result";
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
	public int KE=1;
	public int Bioburden=0;
	public int Culture=0;

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void reprocessorAreaWithKEVerifications(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, SQLException, AWTException, ParseException, IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		if (UAS.parallelExecutionType && UAS.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}

		
		UAS.DriverSelection(browserP,URL,AdminDB);
		UAS.resultFlag="Pass";
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Reporecssors KE");
		UAS.XMLFileName=ResFileNameXML;
		UAS.TestCaseNumber=1;
    	String Description;
		gf.SyncRemoteMachineTime(UAS.KE_Env, UAS.KEMachine_Username, UAS.KEMachine_pswd, URL);
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
    	TDF.insertMasterData(UAS.url, UAS.user, UAS.pass, KE, Bioburden, Culture);
		gf.RestartIISServices(UAS.Env, UAS.userName, UAS.IISPass);
	
    	TDF.insertKE_DD_Data(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
    	Thread.sleep(120000);
    
    	LGPA.Launch_Unifia(UAS.Admin_URL);
		Thread.sleep(20000);
		UA.selectUserRoleNLogin(browserP, URL, UAS.roleMgr, UAS.userQV1, UAS.userQV1Pwd);
		qvd_v.clickDashBoard();
		
		//Reprocessor1
    	Description="Verifying reprocessor1 Room Name";
    	reprocessorResult(Description,DBP.expRepro1Name,DBP.rep1Name,false);
    	Description="Verifying reprocessor1 Status is Available";
    	reprocessorResult(Description,DBP.KEexpStatusRepro1,DBP.KERep1Status,false);
    	Description="Verifying reprocessor1 color is green";
    	reprocessorResult(Description,DBP.KEexpRepAvailableColorGreen,DBP.KERep1color,true);
    	Description="Verifying reprocessor1 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro1,DBP.KERep1ScopeName1,false);
    	Description="Verifying reprocessor1 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro1,DBP.KERep1ScopeName2,false);
    	
    	//Reprocessor2
    	Description="Verifying reprocessor2 Room Name";
    	reprocessorResult(Description,DBP.expRepro2Name,DBP.rep2Name,false);
    	Description="Verifying reprocessor2 Status";
    	reprocessorResult(Description,DBP.KEexpStatusRepro2,DBP.KERep2Status,false);
    	Description="Verifying reprocessor2 color is Red";
    	reprocessorResult(Description,DBP.KEexpRepUnExpTermColorAmber,DBP.KERep2color,true);
    	/*Description="Verifying reprocessor2 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro2,DBP.KERep2ScopeName1,false);
    	Description="Verifying reprocessor2 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro2,DBP.KERep2ScopeName2,false);*/
    	Description="Verifying reprocessor2 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro2,DBP.KERep2ScopeName2,false);
    	//Reprocessor4
    	Description="Verifying reprocessor4 Room Name";
    	reprocessorResult(Description,DBP.expRepro4Name,DBP.rep4Name,false);
    	Description="Verifying reprocessor4 Status";
    	reprocessorResult(Description,DBP.KEexpStatusRepro4,DBP.KERep4Status,false);
    	Description="Verifying reprocessor4 color is purple";
    	reprocessorResult(Description,DBP.KEexpRepProcessingColorPurple,DBP.KERep4color,true);
    	Description="Verifying reprocessor4 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro4,DBP.KERep4ScopeName1,false);
    	Description="Verifying reprocessor4 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro4,DBP.KERep4ScopeName2,false);
    	
    	//Reprocessor5
    	Description="Verifying reprocessor5 Room Name";
    	reprocessorResult(Description,DBP.expRepro5Name,DBP.rep5Name,false);
    	Description="Verifying reprocessor5 Status";
    	reprocessorResult(Description,DBP.KEexpStatusRepro5,DBP.KERep5Status,false);
    	Description="Verifying reprocessor5 color is orange";
    	reprocessorResult(Description,DBP.KEexpRepUnExpTermColorAmber,DBP.KERep5color,true);
    	Description="Verifying reprocessor5 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro5,DBP.KERep5ScopeName1,false);
    	Description="Verifying reprocessor5 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro5,DBP.KERep5ScopeName2,false);

    	//Reprocessor6
    	Description="Verifying reprocessor6 Room Name";
    	reprocessorResult(Description,DBP.expRepro6Name,DBP.rep6Name,false);
    	Description="Verifying reprocessor6 Status";
    	reprocessorResult(Description,DBP.KEexpStatusRepro6,DBP.KERep6Status,false);
    	Description="Verifying reprocessor6 color is black";
    	reprocessorResult(Description,DBP.KEexpRepProcessingColorblack,DBP.KERep6color,true);
    	Description="Verifying reprocessor6 Scope1 Name";
    	reprocessorResult(Description,DBP.KEexpScope1Repro6,DBP.KERep6ScopeName1,false);
    	Description="Verifying reprocessor5 Scope2 Name";
    	reprocessorResult(Description,DBP.KEexpScope2Repro6,DBP.KERep6ScopeName2,false);
    	//final result
    	TestSummary=TestSummary+TestRes+"\r\n";
    	TH.WriteToTextFile(ResFileName, TestSummary);
    	IHV.Close_Exec_Log(UAS.XMLFileName, "Test Completed", UAS.TestCaseNumber);
		if (TestSummary.contains("#Failed!#")||(UAS.resultFlag.contains("#Failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		//LP_A.CloseDriver();
	}
	@AfterTest
	  public void PostTest() throws IOException{
		LP_A.CloseDriver();
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
			result="#Failed!#: Expected= "+expVal+";Actual= "+applicationVal+". Bug 12860 opened.";			
		}
		if (TestRes!=(null) && !TestRes.isEmpty()){
			TestRes=TestRes+description+"--  "+result+"\r\n";
		}else{
			TestRes=description+"--  "+result+"\r\n";
		}
		IHV.Exec_Log_Result(ResFileNameXML, description, description, result);
	}
	
	
}
