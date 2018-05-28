
package dailydashboardmultifacility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminUserPage.User_Actions;
//this is to verify defect 11191  
public class multiFacilityVerify {
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public QV_GeneralFunc QV_Gen;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	
	public GeneralFunc gf;
	public int KE=0;
	public int Bioburden=1;
	public int Culture=0;
	
	public String Scanner="";
	public Boolean Res;
	public String ScanScope;
	public User_Actions ua;
	public String GridID; 
	public String Description;	
	public LandingPage_Actions SE_LA;
	public LandingPage_Actions LP_A;
	private TestDataFunc TDF;
	private ITConsole.ITConScanSimActions IT_A;
	private ResultSet rs;
	private String ResFileNameXML="MultiFacility_Verification_Result";
	private TestFrameWork.Emulator.GetIHValues IHV;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws Exception {
		Connection conn= null;
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML,"MultiFacility - Verification");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		Unifia_Admin_Selenium.isMoveRight=false;
		String Expected,Description;

		//Inserting Master Data
		gf.SyncRemoteMachineTime(UAS.KE_Env, UAS.KEMachine_Username, UAS.KEMachine_pswd, URL);
		TDF.insertKEMasterData(UAS.KE_Url,UAS.KE_UserName,UAS.KE_Pwd);
		TDF.insertMultiFacilityMasterData(UAS.url, UAS.user, UAS.pass, KE, Bioburden, Culture);
		gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(20000);
		
		//UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		ua.loginUnifia(Unifia_Admin_Selenium.appUser, Unifia_Admin_Selenium.appPassword);
		
		String UserRoleFac=Unifia_Admin_Selenium.userQV1 + "==" + Unifia_Admin_Selenium.roleMgr + "==" + Unifia_Admin_Selenium.facility1 + ";" + Unifia_Admin_Selenium.userQV2 + "==" + Unifia_Admin_Selenium.roleMgr + "==" + Unifia_Admin_Selenium.facility2 + ";" + Unifia_Admin_Selenium.userQV3 + "==" + Unifia_Admin_Selenium.roleMgr + "==" + Unifia_Admin_Selenium.facility3;
		ua.selectUserRoleMultiFacility(browserP, URL,UserRoleFac);
		
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		ua.loginUnifia(Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		
		//Inserting data into facility 2 and facility 3
		//PR
		Res = EM_A.ScanItem("F2 Procedure Room 1", "Workflow Event", "", "Available", "");
		System.out.println(Res);

    	Res = EM_A.ScanItem("F2 Procedure Room 1", "Scope", "", "F2 Scope1", "");//Scanning Scope 16 into procedure room 2
		System.out.println(Res);
		
		//Rep
		Res = EM_A.ScanItem("F2 Reprocessor 1", "Scope", "", "F2 Scope2", "");
		System.out.println(Res);
		
		//storage
		Res = EM_A.ScanItem("F2 Storage Area A", "Scope", "", "F2 Scope3", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F2 Storage Area A", "Key Entry", "", "", "1");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F2 Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Sink
		Res = EM_A.ScanItem("F2 Sink 2", "Scope", "", "F2 Scope4", "");
		System.out.println(Res);
		
		//Inserting data into facility 1 and facility 3
		//PR
		Res = EM_A.ScanItem("F3 Procedure Room 1", "Workflow Event", "", "Available", "");
		System.out.println(Res);

    	Res = EM_A.ScanItem("F3 Procedure Room 2", "Scope", "", "F3 Scope33", "");//Scanning Scope 16 into procedure room 2
		System.out.println(Res);
		
		//Rep
		Res = EM_A.ScanItem("F3 Reprocessor 4", "Scope", "", "F3 Scope34", "");
		System.out.println(Res);
		
		//storage
		Res = EM_A.ScanItem("F3 Storage Area D", "Scope", "", "F3 Scope35", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F3 Storage Area D", "Key Entry", "", "", "1");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F3 Storage Area D", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Sink
		Res = EM_A.ScanItem("F3 Sink 1", "Scope", "", "F3 Scope40", "");
		System.out.println(Res);
		
		
		Description="Verifying Scans done for facility 2 are not displayed in facility 1 dashboard";
		Expected =Description;
		
		if (UAS.driver.findElements(By.id("prScope201")).size()>0 ||UAS.driver.findElements(By.id("prScope202")).size()>0 || 
			    UAS.driver.findElements(By.id("prScope203")).size()>0 ||UAS.driver.findElements(By.id("prScope204")).size()>0){
				System.out.println("fail");
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Fail");
		}else{
			System.out.println("pass");
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Pass");
		}
		
		Description="Verifying Scans done for facility 3 are not displayed in facility 1 dashboard";
		Expected =Description;
		
		
		if (UAS.driver.findElements(By.id("prScope333")).size()>0 ||UAS.driver.findElements(By.id("prScope334")).size()>0 || 
		    UAS.driver.findElements(By.id("prScope334")).size()>0 ||UAS.driver.findElements(By.id("prScope340")).size()>0){
			System.out.println("fail");
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Fail");
		}else{
			System.out.println("Pass");
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Pass");
		}
		
		//
		SE_LA.Click_Logout();
		ua.loginUnifia(Unifia_Admin_Selenium.userQV2, Unifia_Admin_Selenium.userQV2Pwd);
		
		//Inserting data into facility 1 and facility3
		//PR
		Res = EM_A.ScanItem("F1 Procedure Room 4", "Workflow Event", "", "Available", "");
		System.out.println(Res);

    	Res = EM_A.ScanItem("F1 Procedure Room 4", "Scope", "", "F1 Scope1", "");//Scanning Scope 16 into procedure room 2
		System.out.println(Res);
		
		//Rep
		Res = EM_A.ScanItem("F1 Reprocessor 1", "Scope", "", "F1 Scope2", "");
		System.out.println(Res);
		
		//storage
		Res = EM_A.ScanItem("F1 Storage Area A", "Scope", "", "F1 Scope3", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F1 Storage Area A", "Key Entry", "", "", "1");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F1 Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Sink
		Res = EM_A.ScanItem("F1 Sink 2", "Scope", "", "F1 Scope4", "");
		System.out.println(Res);
		
		//Facility 3
		//PR
		Res = EM_A.ScanItem("F3 Procedure Room 1", "Workflow Event", "", "Available", "");
		System.out.println(Res);

    	Res = EM_A.ScanItem("F3 Procedure Room 2", "Scope", "", "F3 Scope11", "");//Scanning Scope 16 into procedure room 2
		System.out.println(Res);
		
		//Rep
		Res = EM_A.ScanItem("F3 Reprocessor 4", "Scope", "", "F3 Scope12", "");
		System.out.println(Res);
		
		//storage
		Res = EM_A.ScanItem("F3 Storage Area A", "Scope", "", "F3 Scope13", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F3 Storage Area A", "Key Entry", "", "", "1");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F3 Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Sink
		Res = EM_A.ScanItem("F3 Sink 1", "Scope", "", "F3 Scope14", "");
		System.out.println(Res);
		
		Description="Verifying Scans done for facility 1 are not displayed in facility 2 dashboard";
		Expected =Description;
		
		if (UAS.driver.findElements(By.id("prScope1")).size()>0 ||UAS.driver.findElements(By.id("prScope2")).size()>0 || 
		    UAS.driver.findElements(By.id("prScope3")).size()>0 ||UAS.driver.findElements(By.id("prScope4")).size()>0){
			System.out.println("fail");
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Fail");
		}else{
			System.out.println("pass");
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Pass");
		}
		
		Description="Verifying Scans done for facility 3 are not displayed in facility 2 dashboard";
		Expected =Description;
		
		if (UAS.driver.findElements(By.id("prScope311")).size()>0 ||UAS.driver.findElements(By.id("prScope312")).size()>0 || 
			    UAS.driver.findElements(By.id("prScope313")).size()>0 ||UAS.driver.findElements(By.id("prScope314")).size()>0){
				System.out.println("fail");
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Fail");
		}else{
			System.out.println("pass");
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Pass");
		}
		
		SE_LA.Click_Logout();
		ua.loginUnifia(Unifia_Admin_Selenium.userQV3, Unifia_Admin_Selenium.userQV3Pwd);
		
		//Inserting data into facility 1 and facility2
		//PR
		Res = EM_A.ScanItem("F1 Procedure Room 3", "Workflow Event", "", "Available", "");
		System.out.println(Res);

    	Res = EM_A.ScanItem("F1 Procedure Room 3", "Scope", "", "F1 Scope5", "");//Scanning Scope 16 into procedure room 2
		System.out.println(Res);
		
		//Rep
		Res = EM_A.ScanItem("F1 Reprocessor 1", "Scope", "", "F1 Scope6", "");
		System.out.println(Res);
		
		//storage
		Res = EM_A.ScanItem("F1 Storage Area B", "Scope", "", "F1 Scope7", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F1 Storage Area B", "Key Entry", "", "", "1");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F1 Storage Area B", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Sink
		Res = EM_A.ScanItem("F1 Sink 2", "Scope", "", "F1 Scope8", "");
		System.out.println(Res);
		
	//Facility 2
		//PR
		Res = EM_A.ScanItem("F2 Procedure Room 1", "Workflow Event", "", "Available", "");
		System.out.println(Res);

    	Res = EM_A.ScanItem("F2 Procedure Room 2", "Scope", "", "F2 Scope5", "");//Scanning Scope 16 into procedure room 2
		System.out.println(Res);
		
		//Rep
		Res = EM_A.ScanItem("F2 Reprocessor 4", "Scope", "", "F2 Scope6", "");
		System.out.println(Res);
		
		//storage
		Res = EM_A.ScanItem("F2 Storage Area A", "Scope", "", "F2 Scope7", "");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F2 Storage Area A", "Key Entry", "", "", "1");
		System.out.println(Res);
		
		Res = EM_A.ScanItem("F2 Storage Area A", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Sink
		Res = EM_A.ScanItem("F2 Sink 1", "Scope", "", "F2 Scope7", "");
		System.out.println(Res);
		
		Description="Verifying Scans done for facility 1 are not displayed in facility 3 dashboard";
		Expected =Description;

		if (UAS.driver.findElements(By.id("prScope5")).size()>0 ||UAS.driver.findElements(By.id("prScope6")).size()>0 || 
			    UAS.driver.findElements(By.id("prScope7")).size()>0 ||UAS.driver.findElements(By.id("prScope8")).size()>0){
				System.out.println("fail");
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Fail");
		}else{
			System.out.println("pass");
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Pass");
		}
		
		Description="Verifying Scans done for facility 2 are not displayed in facility 3 dashboard";
		Expected =Description;
		
		if (UAS.driver.findElements(By.id("prScope205")).size()>0 ||UAS.driver.findElements(By.id("prScope206")).size()>0 || 
			    UAS.driver.findElements(By.id("prScope207")).size()>0 ||UAS.driver.findElements(By.id("prScope208")).size()>0){
				System.out.println("fail");
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Fail");
		}else{
			System.out.println("pass");
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, "Pass");
		}
		
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if ((Unifia_Admin_Selenium.resultFlag.contains("#Failed!#"))) {
			org.testng.Assert.fail("Test has failed");
		}
		
		LP_A.CloseDriver();
		
	}
	
	@AfterTest
	public void PostTest() throws IOException{
	  	LandingPage_Actions.CloseDriver();
	  }
	
}
