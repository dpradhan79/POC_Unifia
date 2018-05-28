package DailyDashBoard;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class ProcedureRoom {
	public QV_GeneralFunc QV_Gen;
	public GeneralFunc gf;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	//public String TestSummary= "\t\t\t DailyDashboard_ProcedureRoom_TestSummary \r\n"; 
	public StringBuffer TestSummary=new StringBuffer("\t\t\t DailyDashboard_ProcedureRoom_TestSummary \r\n");
	public String ResFileName="DailyDashboard_ProcedureRoom_TestSummary";
	private String ResFileNameXML="DailyDashboard_ProcedureRoom_Result";
	public TestFrameWork.TestHelper TH;
	public String TestRes;
	public String ForFileName;
	private Dashboard_Actions DA;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private Dashboard_Actions qvd_a;
		
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void ImageVerification(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException{
		String result_PR1Name,result_PR1Status,result_PR1Scopes,result_PR1Color;
		String result_PR2Name,result_PR2Status,result_PR2Scopes,result_PR2Color;
		String result_PR3Name,result_PR3Status,result_PR3Scopes,result_PR3Color;
		String result_PR5Name,result_PR5Status,result_PR5Scopes,result_PR5Color;

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	Unifia_Admin_Selenium.DriverSelection(browserP,URL, AdminDB);
    	ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML,"Procedure Room - Verification");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		String Expected,Description;
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(5000);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		qvd_v.clickDashBoard();
		
		Connection conn=null;
		Statement statement=null;
		String stmt="";
		List<String> roomsPR = new ArrayList<String>();
		roomsPR.add(DBP.expPR1Name);
		roomsPR.add(DBP.expPR2Name);
		roomsPR.add(DBP.expPR3Name);
		roomsPR.add(DBP.expPR5Name);
		String rnPR="";
		String xpath=null;
		String scopeName = null,scopeSerialNum = null,scopeModel = null,lastScannedLocation = null,resultNoRecords=null;
		List<String> scopeID = new ArrayList<String>();
		for (int eachRoomXpath=0;eachRoomXpath<roomsPR.size();eachRoomXpath++){
			rnPR=roomsPR.get(eachRoomXpath);
			if (rnPR.equalsIgnoreCase("Procedure Room 1")){
				xpath=DBP.PR1Name;
			}else if (rnPR.equalsIgnoreCase("Procedure Room 2")){
				xpath=DBP.PR2Name;
			}else if (rnPR.equalsIgnoreCase("Procedure Room 3")){
				xpath=DBP.PR3Name;
			}else if (rnPR.equalsIgnoreCase("Procedure Room 5")){
				xpath=DBP.PR5Name;
			}
			qvd_a.clickElement(xpath);
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select name from scope where scopeid_pk in (select scopeid_fk from scopestatus where locationid_fk="
				+ "(select locationid_pk from location where name = '"+rnPR+"'))";
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			System.out.println ("Need to verify "+scopeID.size()+" rows in procedure Room "+rnPR);
			while(RS.next()){
				scopeID.add(RS.getString(1));
			}
			if (scopeID.isEmpty()){
				resultNoRecords=gf.verifyNoRecords(DBP.PRTableContent, DBP.noRecordsInPopup);
				Description="Verifying drill down popup contents of "+ rnPR+ " contains "+DBP.noRecordsInPopup;
				Expected =Description;
				TestRes=Description+":\r\n\t"+resultNoRecords+"\r\n";
				//TestSummary=TestSummary+TestRes+"\r\n";
				TestSummary.append(TestRes+"\r\n");
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultNoRecords.toString());
			}
			if (!scopeID.isEmpty()){
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
					String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+"; Serial Number=="+scopeSerialNum;
					StringBuffer resultDrillDown=gf.verifyTableContents(DBP.PRTableContent,colsNvalues,"Scope Name");
					System.out.println(resultDrillDown);
					Description="Verifying drill down popup contents of "+ rnPR+ " for Row - "+(eachRec+1);
					Expected =Description;
					TestRes=Description+":\r\n\t"+resultDrillDown+"\r\n";
					//TestSummary=TestSummary+TestRes+"\r\n";
					TestSummary.append(TestRes+"\r\n");
					IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
				}
			}
			scopeID.clear();
		}		
		qvd_a.clickElement(xpath);
//end
		String PR1Name=qvd_v.getPR1Name();
		System.out.println(PR1Name);

		String PR1Status=qvd_v.getPR1Status();
		System.out.println(PR1Status);

		String PR1Scopes=qvd_v.getPR1Scopes();
		System.out.println(PR1Scopes);
		
		String PR1Color=qvd_v.getPR1Color();
		System.out.println(PR1Color);

		String PR2Name=qvd_v.getPR2Name();
		System.out.println(PR2Name);

		String PR2Status=qvd_v.getPR2Status();
		System.out.println(PR2Status);

		String PR2Scopes=qvd_v.getPR2Scopes();
		System.out.println(PR2Scopes);

		String PR2Color=qvd_v.getPR2Color();
		System.out.println(PR2Color);

		String PR3Name=qvd_v.getPR3Name();
		System.out.println(PR3Name);

		String PR3Status=qvd_v.getPR3Status();
		System.out.println(PR3Status);

		String PR3Scopes=qvd_v.getPR3Scopes();
		System.out.println(PR3Scopes);

		String PR3Color=qvd_v.getPR3Color();
		System.out.println(PR3Color);

		String PR5Name=qvd_v.getPR5Name();
		System.out.println(PR5Name);

		String PR5Status=qvd_v.getPR5Status();
		System.out.println(PR5Status);

		String PR5Scopes=qvd_v.getPR5Scopes();
		System.out.println(PR5Scopes);

		String PR5Color=qvd_v.getPR5Color();
		System.out.println(PR5Color);
		
		IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		
		//Procedure Room 1 Name
		if (qvd_v.compareRes(DBP.expPR1Name,PR1Name,true)){
			result_PR1Name="Passed: Expected= "+DBP.expPR1Name+";Actual= "+PR1Name;
		}else{
			result_PR1Name="#Failed!#: Expected= "+DBP.expPR1Name+";Actual= "+PR1Name;
		}
		Description="Verifying PR1 Name Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR1Name+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR1Name);
		//PR1 Status=Available
		if (qvd_v.compareRes(DBP.expPRAvailable,PR1Status,true)){
			result_PR1Status="Passed: Expected= "+DBP.expPRAvailable+";Actual= "+PR1Status;
		}else{
			result_PR1Status="#Failed!#: Expected= "+DBP.expPRAvailable+";Actual= "+PR1Status;
		}
		Description="Verifying PR1 Status Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR1Status+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR1Status);
		//PR1 Scopes
		if (qvd_v.compareRes(DBP.expPR1Scopes,PR1Scopes,true)){
			result_PR1Scopes="Passed: Expected= "+DBP.expPR1Scopes+";Actual= "+PR1Scopes;
		}else{
			result_PR1Scopes="#Failed!#: Expected= "+DBP.expPR1Scopes+";Actual= "+PR1Scopes;
		}
		Description="Verifying PR1 Scopes Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR1Scopes+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR1Scopes);	
		//PR1 Color
		if (qvd_v.compareRes(DBP.expPRAvailableColor,PR1Color,true)){
			result_PR1Color="Passed: Expected= "+DBP.expPRAvailableColor+";Actual= "+PR1Color;
		}else{
			result_PR1Color="#Failed!#: Expected= "+DBP.expPRAvailableColor+";Actual= "+PR1Color;
		}
		Description="Verifying PR1 color Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR1Color+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR1Color);		
		//Procedure Room 2 Name
		if (qvd_v.compareRes(DBP.expPR2Name,PR2Name,true)){
			result_PR2Name="Passed: Expected= "+DBP.expPR2Name+";Actual= "+PR2Name;
		}else{
			result_PR2Name="#Failed!#: Expected= "+DBP.expPR2Name+";Actual= "+PR2Name;
		}
		Description="Verifying PR2 Name Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR2Name+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR2Name);
		//PR2 Status=In Use
		if (qvd_v.compareRes(DBP.expPRInUse,PR2Status,true)){
			result_PR2Status="Passed: Expected= "+DBP.expPRInUse+";Actual= "+PR2Status;
		}else{
			result_PR2Status="#Failed!#: Expected= "+DBP.expPRInUse+";Actual= "+PR2Status;
		}
		Description="Verifying PR2 Status Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR2Status+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR2Status);
		//PR2 Scopes
		if (qvd_v.compareRes(DBP.expPR2Scopes,PR2Scopes,true)){
			result_PR2Scopes="Passed: Expected= "+DBP.expPR2Scopes+";Actual= "+PR2Scopes;
		}else{
			result_PR2Scopes="#Failed!#: Expected= "+DBP.expPR2Scopes+";Actual= "+PR2Scopes;
		}
		Description="Verifying PR2 Scopes Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR2Scopes+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR2Scopes);		
		//PR2 Color
		if (qvd_v.compareRes(DBP.expPRInUseColor,PR2Color,true)){
			result_PR2Color="Passed: Expected= "+DBP.expPRInUseColor+";Actual= "+PR2Color;
		}else{
			result_PR2Color="#Failed!#: Expected= "+DBP.expPRInUseColor+";Actual= "+PR2Color;
		}
		Description="Verifying PR2 color Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR2Color+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR2Color);		
		//Procedure Room 3 Name
		if (qvd_v.compareRes(DBP.expPR3Name,PR3Name,true)){
			result_PR3Name="Passed: Expected= "+DBP.expPR3Name+";Actual= "+PR3Name;
		}else{
			result_PR3Name="#Failed!#: Expected= "+DBP.expPR3Name+";Actual= "+PR3Name;
		}
		Description="Verifying PR3 Name Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR3Name+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR3Name);
		//PR3 Status=Needs Cleaning
		if (qvd_v.compareRes(DBP.expPRNeedsCleaning,PR3Status,true)){
			result_PR3Status="Passed: Expected= "+DBP.expPRNeedsCleaning+";Actual= "+PR3Status;
		}else{
			result_PR3Status="#Failed!#: Expected= "+DBP.expPRNeedsCleaning+";Actual= "+PR3Status;
		}
		Description="Verifying PR3 Status Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR3Status+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR3Status);
		//PR3 Scopes
		if (qvd_v.compareRes(DBP.expPR3Scopes,PR3Scopes,true)){
			result_PR3Scopes="Passed: Expected= "+DBP.expPR3Scopes+";Actual= "+PR3Scopes;
		}else{
			result_PR3Scopes="#Failed!#: Expected= "+DBP.expPR3Scopes+";Actual= "+PR3Scopes;
		}
		Description="Verifying PR3 Scopes Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR3Scopes+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR3Scopes);		
		//PR3 Color
		if (qvd_v.compareRes(DBP.expPRNeedsCleaningColor,PR3Color,true)){
			result_PR3Color="Passed: Expected= "+DBP.expPRNeedsCleaningColor+";Actual= "+PR3Color;
		}else{
			result_PR3Color="#Failed!#: Expected= "+DBP.expPRNeedsCleaningColor+";Actual= "+PR3Color;
		}
		Description="Verifying PR3 color Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR3Color+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR3Color);		
		//Procedure Room 5 Name
		if (qvd_v.compareRes(DBP.expPR5Name,PR5Name,true)){
			result_PR5Name="Passed: Expected= "+DBP.expPR5Name+";Actual= "+PR5Name;
		}else{
			result_PR5Name="#Failed!#: Expected= "+DBP.expPR5Name+";Actual= "+PR5Name;
		}
		Description="Verifying PR5 Name Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR5Name+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR5Name);
		//PR5 Status=Closed
		if (qvd_v.compareRes(DBP.expPRClosed,PR5Status,true)){
			result_PR5Status="Passed: Expected= "+DBP.expPRClosed+";Actual= "+PR5Status;
		}else{
			result_PR5Status="#Failed!#: Expected= "+DBP.expPRClosed+";Actual= "+PR5Status;
		}
		Description="Verifying PR5 Status Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR5Status+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR5Status);
		//PR5 Scopes
		if (qvd_v.compareRes(DBP.expPR5Scopes,PR5Scopes,true)){
			result_PR5Scopes="Passed: Expected= "+DBP.expPR5Scopes+";Actual= "+PR5Scopes;
		}else{
			result_PR5Scopes="#Failed!#: Expected= "+DBP.expPR5Scopes+";Actual= "+PR5Scopes;
		}
		Description="Verifying PR5 Scopes Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR5Scopes+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR5Scopes);		
		//PR5 Color
		if (qvd_v.compareRes(DBP.expPRClosedColor,PR5Color,true)){
			result_PR5Color="Passed: Expected= "+DBP.expPRClosedColor+";Actual= "+PR5Color;
		}else{
			result_PR5Color="#Failed!#: Expected= "+DBP.expPRClosedColor+";Actual= "+PR5Color;
		}
		Description="Verifying PR5 color Dailydashboard Procedure Rooms";
		Expected =Description;
		TestRes=Description+result_PR5Color+"\r\n";
		TestSummary.append(TestRes+"\r\n");
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_PR5Color);
		//TestSummary=TestSummary+TestRes+"\r\n";
		TH.WriteToTextFile(ResFileName, TestSummary.toString());
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (TestSummary.toString().contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.toLowerCase().contains("#failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		
		LP_A.CloseDriver();
	}
	
	@AfterTest
	  public void PostTest() throws IOException{
	  	LandingPage_Actions.CloseDriver();
	  }
}
