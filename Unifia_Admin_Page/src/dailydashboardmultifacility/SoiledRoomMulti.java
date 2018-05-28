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
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class SoiledRoomMulti {
	public QV_GeneralFunc QV_Gen;
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private String TestSummary= "\t\t\t DailyDashboard_SoiledRoom_TestSummary \r\n\r\n"; 
	private String ResFileName="Multi_DailyDashboard_SoiledRoom_TestSummary";
	private String ResFileNameXML="Multi_DailyDashboard_SoiledRoom_Result";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	public String ForFileName;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
		
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void ImageVerification(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException{
		String result_Sink1Name,result_Sink1Scopes;
		String result_Sink2Name,result_Sink2Scopes;
		
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
		Thread.sleep(20000);
		//UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		qvd_v.clickDashBoard();
		
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Dashboard - Soiled Room");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		String Expected,Description;
		
		for (int eachFac=1;eachFac<=3;eachFac++){
			String facilityName= "Facility "+eachFac;
			System.out.println("Verifying for "+facilityName);
			gf.selectFromListBox(DBP.selectFacility,facilityName);
			Thread.sleep(2000);
			String Sink1Name="",Sink1Scopes="",Sink2Name="", Sink2Scopes="";
			String expSink1Name="",expSink1Scopes="",expSink2Name="",expSink2Scopes="";
			if (eachFac==1){
				Sink1Name=qvd_v.getSink1Name();
				System.out.println(Sink1Name);
		
				Sink1Scopes=qvd_v.getSink1Scopes();
				System.out.println(Sink1Scopes);
				
				Sink2Name=qvd_v.getSink2Name();
				System.out.println(Sink2Name);
		
				Sink2Scopes=qvd_v.getSink2Scopes();
				System.out.println(Sink2Scopes);
				
				expSink1Name=DBP.expSink1NameMulti;
				expSink1Scopes=DBP.expSink1ScopesMulti;
				
				expSink2Name=DBP.expSink2NameMulti;
				expSink2Scopes=DBP.expSink2ScopesMulti;
			}else if (eachFac==2){
				Sink1Name=qvd_v.getSink1Name2();
				System.out.println(Sink1Name);
		
				Sink1Scopes=qvd_v.getSink1Scopes2();
				System.out.println(Sink1Scopes);
				
				Sink2Name=qvd_v.getSink2Name2();
				System.out.println(Sink2Name);
		
				Sink2Scopes=qvd_v.getSink2Scopes2();
				System.out.println(Sink2Scopes);
				
				expSink1Name=DBP.expSink1NameMulti2;
				expSink1Scopes=DBP.expSink1ScopesMulti2;
				

				expSink2Name=DBP.expSink2NameMulti2;
				expSink2Scopes=DBP.expSink2ScopesMulti2;
			}else if (eachFac==3){
				Sink1Name=qvd_v.getSink1Name3();
				System.out.println(Sink1Name);
		
				Sink1Scopes=qvd_v.getSink1Scopes3();
				System.out.println(Sink1Scopes);
				
				Sink2Name=qvd_v.getSink2Name3();
				System.out.println(Sink2Name);
		
				Sink2Scopes=qvd_v.getSink2Scopes3();
				System.out.println(Sink2Scopes);
				
				expSink1Name=DBP.expSink1NameMulti3;
				expSink1Scopes=DBP.expSink1ScopesMulti3;
				

				expSink2Name=DBP.expSink2NameMulti3;
				expSink2Scopes=DBP.expSink2ScopesMulti3;
			}
	
			
			
			
			//Sink1 Name
			if (qvd_v.compareRes(expSink1Name,Sink1Name,true)){
				result_Sink1Name="Passed: Expected= "+DBP.expSink1NameMulti+";Actual= "+Sink1Name;
			}else{
				result_Sink1Name="#Failed!#: Expected= "+DBP.expSink1NameMulti+";Actual= "+Sink1Name;
			}
			Description="Verifying Sink1 Name in Daily Dashboard Screen for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_Sink1Name+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_Sink1Name);
			
			//Sink1 Scopes
			if (qvd_v.compareRes(expSink1Scopes,Sink1Scopes,true)){
				result_Sink1Scopes="Passed: Expected= "+DBP.expSink1ScopesMulti+";Actual= "+Sink1Scopes;
			}else{
				result_Sink1Scopes="#Failed!#: Expected= "+DBP.expSink1ScopesMulti+";Actual= "+Sink1Scopes;
			}
			Description="Verifying Sink1 Scopes in Dailydashboard Screen for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_Sink1Scopes+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_Sink1Scopes);
			
			//Sink2 Name
			if (qvd_v.compareRes(expSink2Name,Sink2Name,true)){
				result_Sink2Name="Passed: Expected= "+DBP.expSink2NameMulti+";Actual= "+Sink2Name;
			}else{
				result_Sink2Name="#Failed!#: Expected= "+DBP.expSink2NameMulti+";Actual= "+Sink2Name;
			}
			Description="Verifying Sink2 Name in Daily Dashboard Screen for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_Sink2Name+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_Sink2Name);
			
			//Sink2 Scopes
			if (qvd_v.compareRes(expSink2Scopes,Sink2Scopes,true)){
				result_Sink2Scopes="Passed: Expected= "+DBP.expSink2ScopesMulti+";Actual= "+Sink2Scopes;
			}else{
				result_Sink2Scopes="#Failed!#: Expected= "+DBP.expSink2ScopesMulti+";Actual= "+Sink2Scopes;
			}
			Description="Verifying Sink2 Scopes in Dailydashboard Screen for "+facilityName;
			Expected =Description;
			TestRes=Description+":\r\n\t"+result_Sink2Scopes+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_Sink2Scopes);
	
			TH.WriteToTextFile(ResFileName, TestSummary);
		}
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		
		if (TestSummary.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		
		LP_A.CloseDriver();
	}
	
	@AfterTest
	  public void PostTest() throws IOException{
		LP_A.CloseDriver();
	  }
}
