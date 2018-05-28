package DailyDashBoard;
import java.awt.AWTException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class ReprocessorsWithoutKE {
	private QV_GeneralFunc QV_Gen;
	private GeneralFunc gf;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private String TestSummary= "\t\t\t DailyDashboard_ReprocessorsWithoutKE_TestSummary \r\n"; 
	private String ResFileName="DailyDashboard_ReprocessorsWithoutKE_TestSummary";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	private String ForFileName;
	private Dashboard_Actions DA;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private Dashboard_Verification DV;
	private String ResFileNameXML="DailyDashboard_ReprocessorWithoutKE_Result";
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void reprocessorAreaVerifications(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		Unifia_Admin_Selenium.resultFlag="Pass";
		//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	Unifia_Admin_Selenium.DriverSelection(browserP,URL, AdminDB);
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(20000);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		qvd_v.clickDashBoard();
		
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Reprocessor without KE");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
    	String Description;
    	
    	//Reprocessor1
    	Description="Verifying reprocessor1 Room Name";
    	reprocessorResult(Description,DBP.expRepro1Name,DBP.rep1Name,false);
    	Description="Verifying reprocessor1 Room does not have any scopes";
    	reprocessorResult(Description,DBP.expScopeRepro1,DBP.rep1ScopeName,false);
    	//Reprocessor2
    	Description="Verifying reprocessor2 Room Name";
    	reprocessorResult(Description,DBP.expRepro2Name,DBP.rep2Name,false);
    	Description="Verifying reprocessor2 Scope";
    	reprocessorResult(Description,DBP.expScopeRepro2,DBP.rep2ScopeName,false);
    	//Reprocessor4
    	Description="Verifying reprocessor4 Room Name";
    	reprocessorResult(Description,DBP.expRepro4Name,DBP.rep4Name,false);
    	Description="Verifying reprocessor4  first Scope";
    	reprocessorResult(Description,DBP.expScopeRepro4First,DBP.rep4ScopeName1,false);
    	Description="Verifying reprocessor4 second scope";
    	reprocessorResult(Description,DBP.expScopeRepro4Second,DBP.rep4ScopeName2,false);
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
