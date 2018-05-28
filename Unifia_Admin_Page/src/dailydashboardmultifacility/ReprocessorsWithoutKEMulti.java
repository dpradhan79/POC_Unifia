package dailydashboardmultifacility;
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

public class ReprocessorsWithoutKEMulti {
	
	private QV_GeneralFunc QV_Gen;
	private GeneralFunc gf;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private String TestSummary= "\t\t\t DailyDashboard_ReprocessorsWithoutKE_TestSummary \r\n"; 
	private String ResFileName="Multi_DailyDashboard_ReprocessorsWithoutKE_TestSummary";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	private String ForFileName;
	private Dashboard_Actions DA;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private Dashboard_Verification DV;
	private String ResFileNameXML="Multi_DailyDashboard_ReprocessorWithoutKE_Result";
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
		//UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		qvd_v.clickDashBoard();
		
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Reprocessor without KE");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
    	String Description;
    	
    	for (int eachFac=2;eachFac<=3;eachFac++){
			String facilityName= "Facility "+eachFac;
			System.out.println("Verifying for "+facilityName);
			gf.selectFromListBox(DBP.selectFacility,facilityName);
			Thread.sleep(2000);
			String expRepro1Name="",xpathRepro1Name="", expScopeRepro1Multi="";
			String expRepro2Name="",xpathRepro2Name="", expScopeRepro2Multi="";
			String expRepro4Name="",xpathRepro4Name="", expScopeRepro4Multi="";
			String expScopeRepro4First="",expScopeRepro4Second="",xpathRep1ScopeName="",xpathRep2ScopeName="";
			String xpathrep4ScopeName1="", xpathrep4ScopeName2="";
			if (eachFac==1){
				expRepro1Name=DBP.expRepro1NameMulti;
				xpathRepro1Name=DBP.rep1Name;
				expScopeRepro1Multi=DBP.expScopeRepro1Multi;
				xpathRep1ScopeName=DBP.rep1ScopeName;
				
				expRepro2Name=DBP.expRepro2NameMulti;
				xpathRepro2Name=DBP.rep2Name;
				expScopeRepro2Multi=DBP.expScopeRepro2Multi;
				xpathRep2ScopeName=DBP.rep2ScopeName;
				
				expRepro4Name=DBP.expRepro4NameMulti;
				xpathRepro4Name=DBP.rep4Name;
				expScopeRepro4First=DBP.expScopeRepro4FirstMulti;
				expScopeRepro4Second=DBP.expScopeRepro4SecondMulti;
				xpathrep4ScopeName1=DBP.rep4ScopeName1;
				xpathrep4ScopeName2=DBP.rep4ScopeName2;
			}else if (eachFac==2){
				expRepro1Name=DBP.expRepro1NameMulti2;
				xpathRepro1Name=DBP.rep1Name2;
				expScopeRepro1Multi=DBP.expScopeRepro1Multi2;
				xpathRep1ScopeName=DBP.rep1ScopeName2;
				
				expRepro2Name=DBP.expRepro2NameMulti2;
				xpathRepro2Name=DBP.rep2Name2;
				expScopeRepro2Multi=DBP.expScopeRepro2Multi2;
				xpathRep2ScopeName=DBP.rep2ScopeName2;
				
				expRepro4Name=DBP.expRepro4NameMulti2;
				xpathRepro4Name=DBP.rep4Name2;
				expScopeRepro4First=DBP.expScopeRepro4FirstMulti2;
				expScopeRepro4Second=DBP.expScopeRepro4SecondMulti2;
				xpathrep4ScopeName1=DBP.rep4ScopeName12;
				xpathrep4ScopeName2=DBP.rep4ScopeName22;
			}else if (eachFac==3){
				expRepro1Name=DBP.expRepro1NameMulti3;
				xpathRepro1Name=DBP.rep1Name3;
				expScopeRepro1Multi=DBP.expScopeRepro1Multi3;
				xpathRep1ScopeName=DBP.rep1ScopeName3;
				
				expRepro2Name=DBP.expRepro2NameMulti3;
				xpathRepro2Name=DBP.rep2Name3;
				expScopeRepro2Multi=DBP.expScopeRepro2Multi3;
				xpathRep2ScopeName=DBP.rep2ScopeName3;
				
				expRepro4Name=DBP.expRepro4NameMulti3;
				xpathRepro4Name=DBP.rep4Name3;
				expScopeRepro4First=DBP.expScopeRepro4FirstMulti3;
				expScopeRepro4Second=DBP.expScopeRepro4SecondMulti3;
				xpathrep4ScopeName1=DBP.rep4ScopeName13;
				xpathrep4ScopeName2=DBP.rep4ScopeName23;
			}
    	
	    	//Reprocessor1
	    	Description="Verifying reprocessor1 Room Name for "+facilityName ;
	    	reprocessorResult(Description,expRepro1Name,xpathRepro1Name,false);
	    	Description="Verifying reprocessor1 Room does not have any scopes for "+facilityName ;
	    	reprocessorResult(Description,expScopeRepro1Multi,xpathRep1ScopeName,false);
	    	//Reprocessor2
	    	Description="Verifying reprocessor2 Room Name for "+facilityName ;
	    	reprocessorResult(Description,expRepro2Name,xpathRepro2Name,false);
	    	Description="Verifying reprocessor2 Scope for "+facilityName ;
	    	reprocessorResult(Description,expScopeRepro2Multi,xpathRep2ScopeName,false);
	    	//Reprocessor4
	    	Description="Verifying reprocessor4 Room Name for "+facilityName ;
	    	reprocessorResult(Description,expRepro4Name,xpathRepro4Name,false);
	    	Description="Verifying reprocessor4  first Scope for "+facilityName ;
	    	reprocessorResult(Description,expScopeRepro4First,xpathrep4ScopeName1,false);
	    	Description="Verifying reprocessor4 second scope for "+facilityName ;
	    	reprocessorResult(Description,expScopeRepro4Second,xpathrep4ScopeName2,false);
	    	
	    	//final result
	    	TestSummary=TestSummary+TestRes+"\r\n";
	    	TH.WriteToTextFile(ResFileName, TestSummary);
	    	
    	}
    	
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
