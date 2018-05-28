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
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class ScopesApproachingHT {
	private static GeneralFunc gf;
	private static TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private static Dashboard_Actions qvd_a;
	private String TestSummary= "\t\t\t InfectionPrevention_ScopesApproachingHangTime_TestSummary \r\n\r\n"; 
	private String ResFileName="IP_ScopesApproachingHangTime_TestSummary";
	private String ResFileNameXML="IP_ScopesApproachingHangTime_Result";
	private TestFrameWork.TestHelper TH;
	private String TestRes;
	
	private String ForFileName;	
	private TestFrameWork.UnifiaAdminGeneralFunctions.InfectionPreventionDashboard IPD;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestFrameWork.Emulator.Emulator_Actions EM_A;
	
	private static int daysForAHT=6; //Days for Approaching Hang Time
	private static String tableXpath=DBP.sATableContent;
	private static String tableContentXpath=tableXpath+"/div[2]/table/tbody";
	private static int DashboardDataCounter=0;
	private String StorageA = "Storage Area A";
	private String cabinet="2";
	private String result="";
	
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void ScopesApproachingHT_Verification(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException{
		String result_SAHTHeader, result_SAHTCount, result_SAHTColor;
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
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Infection Prevention - Scopes Approaching Hang Time");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		String Expected,Description;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(20000);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		
		Unifia_Admin_Selenium.driverSelectionSecond(browserP,URL,AdminDB);
		LGPA.Launch_UnifiaSecond(Unifia_Admin_Selenium.Emulator_URL);
		
		Thread.sleep(5000);
		Map<String, String> DashBoardData=new HashMap<String, String>();
		Map<String, String> approachingHangTimeData=new HashMap<String, String>();
		
		//Getting the Data from Daily Dashboard Screen for the scopes that are approaching hang time 
		List<String> roomsStorage = new ArrayList<String>();
		roomsStorage.add(DBP.expCultHoldName);
		roomsStorage.add(DBP.expSA1Name);
		roomsStorage.add(DBP.expSA2Name);
		roomsStorage.add(DBP.expSA3Name);
		roomsStorage.add(DBP.expSA4Name);
		DashboardDataCounter=0;
		for (int room=0;room<roomsStorage.size();room++){
			DashBoardData.clear();
			DashBoardData=get_StorageAreaData(roomsStorage.get(room));
			int rowCount=gf.getTableRowCount(tableContentXpath);
			if(DashBoardData.size()>0){
				for(int i=1;i<=rowCount;i++){
					if(DashBoardData.get("HangTime"+i).equalsIgnoreCase("6")){
						DashboardDataCounter++;
						approachingHangTimeData.put("ScopeModel"+DashboardDataCounter, DashBoardData.get("ScopeModel"+i));
						approachingHangTimeData.put("ScopeName"+DashboardDataCounter, DashBoardData.get("ScopeName"+i));
						approachingHangTimeData.put("ScopeSerialNumber"+DashboardDataCounter, DashBoardData.get("ScopeSerialNumber"+i));
						approachingHangTimeData.put("HangTime"+DashboardDataCounter, DashBoardData.get("HangTime"+i));
						approachingHangTimeData.put("LocationName"+DashboardDataCounter, DashBoardData.get("LocationName"+i));
					}
				}
			}
		}
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		
		String sahtHeader=IP_A.getHeader(IPD.sahtHeader);
		System.out.println(sahtHeader);
		String sahtCount=IP_A.getScopeCount(IPD.sahtCount);
		System.out.println(sahtCount);
		String sahtColor=IP_A.getColor(IPD.sahtColor);
		System.out.println(sahtColor);
		
		//sahtHeader
		if (qvd_v.compareRes(IPD.expSAHTHeader,sahtHeader,true)){
			result_SAHTHeader="Passed: Expected= "+IPD.expSAHTHeader+"; Actual= "+sahtHeader;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SAHTHeader="#Failed!#: Expected= "+IPD.expSAHTHeader+"; Actual= "+sahtHeader;
		}
		System.out.println("result_SAHTHearder = "+result_SAHTHeader);
		Description="Verifying Scopes Approaching Hang Time Header in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SAHTHeader+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SAHTHeader);
		
		//sahtScope Count
		if (qvd_v.compareRes(String.valueOf(DashboardDataCounter),sahtCount,true)){
			result_SAHTCount="Passed: Expected= "+String.valueOf(DashboardDataCounter)+"; Actual= "+sahtCount;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SAHTCount="#Failed!#: Expected= "+String.valueOf(DashboardDataCounter)+"; Actual= "+sahtCount;
		}
		System.out.println("result_SAHTCount = "+result_SAHTCount);
		Description="Verifying Scopes Approaching Hang Time Scope Count in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SAHTCount+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SAHTCount);
		
		//sahtColor
		if (qvd_v.compareRes(IPD.expSAHTColor,sahtColor,true)){
			result_SAHTColor="Passed: Expected= "+IPD.expSAHTColor+"; Actual= "+sahtColor;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SAHTColor="#Failed!#: Expected= "+IPD.expSAHTColor+"; Actual= "+sahtColor;
		}
		System.out.println("result_SAHTColor = "+result_SAHTColor);
		Description="Verifying Color of Scopes Approaching Hang in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SAHTColor+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SAHTColor);
		IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		
		//Verifying the drilldown popup data with the DailyDashboard Screen
		qvd_a.clickElement(IPD.sahtCount);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		StringBuffer resultDrillDown=new StringBuffer();
		if(!result_SAHTCount.contains("#Failed!#")){
			for(int i=1;i<=DashboardDataCounter;i++){
				String colsNvalues="Scope Model=="+approachingHangTimeData.get("ScopeModel"+i)+";Scope Name=="+approachingHangTimeData.get("ScopeName"+i)+";Scope Serial Number=="+approachingHangTimeData.get("ScopeSerialNumber"+i)+";Location Name=="+approachingHangTimeData.get("LocationName"+i)+";Hang Time=="+approachingHangTimeData.get("HangTime"+i);
				resultDrillDown=gf.verifyTableContents(IPD.sahtTableContent,colsNvalues,"Scope Name");
				System.out.println(resultDrillDown);
				Description="Verifying drill down popup contents of Scopes Approaching Hang Time area on Infection Prevention Dashboard  for Row - "+i+" with the data that is taken from Daily Dashboard drilldowns";
				Expected =Description;
				TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
				TestSummary=TestSummary+TestRes+"\r\n";
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
			}
		}else{
			result="Scopes Count on Infection Prevention Dashboard screen did not match with the Scopes count taken from DailyDashboard";
			Description="Verifying drill down popup contents of Scopes Approaching Hang Time area on Infection Prevention Dashboard with the data that is taken from Daily Dashboard drilldowns";
			Expected =Description;
			TestRes=Description+":\r\n\t"+result+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		}
		
		//Verifying the drilldown popup data from database
		Connection conn=null;
		Statement statement=null;
		String stmt="";
		List<String> scopeIDs = new ArrayList<String>();
		List<String> scopes = new ArrayList<String>();
		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		statement = conn.createStatement();
		stmt="select ScopeID_FK from ScopeStatus where LocationID_FK in (31,32,33,34)";
		System.out.println("stmt="+stmt);
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			scopeIDs.add(RS.getString(1));
		}
		
		for (int eachRec=0;eachRec<scopeIDs.size();eachRec++){
			String scopeid=scopeIDs.get(eachRec);
			stmt="Select DateDiff(hh, IH.ReceivedDateTime, GETUTCDATE())/24 AS HangTime from ItemHistory IH join ScopeCycle on "
					+ "IH.AssociationID_FK=ScopeCycle.AssociationID_FK where ScopeCycle.ScopeID_FK="+scopeid+" and "
					+ "IH.CycleEventID_FK=18 and ScopeCycle.CycleID=(Select Max(CycleID) from ScopeCycle where ScopeID_FK="+scopeid+")";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				if(RS.getInt(1)==daysForAHT)
					scopes.add(scopeid);
			}
		}
		
		System.out.println ("Need to verify "+scopes.size()+" rows in Scopes Approaching Hang Time");
		String scopeName = null,scopeSerialNum = null,scopeModel = null,storageArea = null,subLocation = null,location = null;
		
		for (int eachRec=0;eachRec<scopes.size();eachRec++){
			String scopeid=scopes.get(eachRec);
			stmt="select s.Name, s.SerialNumber, st.Name from Scope s join ScopeType ST on st.ScopeTypeID_PK=s.ScopeTypeID_FK where s.ScopeID_PK="+scopeid;
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				scopeName=RS.getString(1);
				scopeSerialNum=RS.getString(2);
				scopeModel=RS.getString(3);
			}
			
			stmt="select l.Name,ss.SubLocationID as LocationName from Location l join ScopeStatus ss on l.LocationID_PK=ss.LocationID_FK join Scope s on ss.ScopeID_FK=s.ScopeID_PK where s.Name='"+scopeName+"'";
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			while(RS.next()){
				storageArea=RS.getString(1);
				subLocation=RS.getString(2);
			}
			
			location=storageArea+"-"+subLocation;
			
			//Verifying the drilldown table contents with the values fetched from Database.
			String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+";Scope Serial Number=="+scopeSerialNum+";Location Name=="+location+";Hang Time=="+daysForAHT;
			resultDrillDown=gf.verifyTableContents(IPD.sahtTableContent,colsNvalues,"Scope Name");
			System.out.println(resultDrillDown);
			Description="Verifying drill down popup contents of Scopes Approaching Hang Time area for Row - "+(eachRec+1);
			Expected =Description;
			TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		}
		
		String scopeNames[]=getScopeNames_SAHT();
		/*boolean isAscending=isSortedInAscending(scopeNames);
		if(!isAscending){
			result="Pass - ScopeNames are displayed in Descending order for Scopes Aprroaching Hang Time table";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!#- ScopeNames are not displayed in descending order for Scopes Aprroaching Hang Time table. Bug - 11117: UE 2.4 Final Ver. R1 - IPD Scopes Approaching Hang Time - Sort order is supposed to be by Scope name according to the test case, but it appears to be by Location, then Scope Name.";
		}
		System.out.println(result);
		Description="Verifying whether ScopeNames are displayed in Ascending order for Scopes Aprroaching Hang Time table";
		Expected ="ScopeNames should be displayed in Ascending order for Scopes Aprroaching Hang Time table";
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		*/
		
		String ScopeName=approachingHangTimeData.get("ScopeName1");
		
		boolean Res = EM_A.ScanItem(StorageA, "Scope", "", ScopeName, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem("Reprocessor 1", "Scope", "", ScopeName, "");
		System.out.println(Res);
		Res = EM_A.ScanItem("Reprocessor 1", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Thread.sleep(40000); //waiting 40 seconds to complete reprocessing
		Res = EM_A.ScanItem("Reprocessor 1", "Scope", "", ScopeName, "");
		System.out.println(Res);
		Res = EM_A.ScanItem("Reprocessor 1", "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Scope", "", ScopeName, "");
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Key Entry", "", "", cabinet);
		System.out.println(Res);
		Res = EM_A.ScanItem(StorageA, "Staff", "Tech", "Tech10 Tech10(T10)", "");
		System.out.println(Res);
		
		//Verify scope reprocessed now has a hang time of 0 days on the Daily Dashboard
		UAS.driver.navigate().refresh();
		Thread.sleep(2000);
		qvd_v.clickDashBoard();
		
		DashBoardData.clear();
		DashBoardData=get_StorageAreaData(StorageA+"-"+cabinet);
		int rowCount=gf.getTableRowCount(tableContentXpath);
		for(int i=1;i<=rowCount;i++){
			if(DashBoardData.get("ScopeName"+i).equalsIgnoreCase(ScopeName)){
				if(DashBoardData.get("HangTime"+i).equalsIgnoreCase("0")){
					result="Pass - The HangTime for "+ScopeName+" is 0"; 
				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!#- The HangTime for "+ScopeName+" is expected to be 0 but it was "+DashBoardData.get("HangTime"+i); 
				}
			}
		}
		System.out.println("The Result is "+result);
		Description="Verifying the hang time should be 0 for "+ScopeName;
		Expected =Description;
		TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		qvd_a.clickElement(IPD.sahtCount);
		
		String colsNvalues="Scope Name=="+ScopeName;
		resultDrillDown=gf.verifyTableContents(IPD.sahtTableContent,colsNvalues,"Scope Name");
		if(resultDrillDown.toString().contains("#Failed!#")){
			result="Pass - "+ScopeName+" is not present in Scopes Approaching Hang Time drilldown of Infection Prevention Dashboard screen";
		}else if(resultDrillDown.toString().contains("Pass")){
			UAS.resultFlag="#Failed!#";
			result="#Failed!#- "+ScopeName+" is present in Scopes Approaching Hang Time drilldown of Infection Prevention Dashboard screen";
		}
		Description="Verifying that "+ScopeName+" should be present in Scopes Approaching Hang Time drilldown of Infection Prevention Dashboard screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result.toString()+"\r\n";
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
	 
	public static Map<String, String> get_StorageAreaData(String StorageArea) throws InterruptedException {
		UAS.driver.navigate().refresh();
		Thread.sleep(2000);
		Map<String, String> DashBoardData = new HashMap<String, String>();
		String xpath = null;
		if (StorageArea.equalsIgnoreCase("Culture Hold Cabinet-1")) {
			xpath = DBP.cultHoldCabinetName;
		} else if (StorageArea.equalsIgnoreCase("Storage Area A-1")) {
			xpath = DBP.sA1Name;
		} else if (StorageArea.equalsIgnoreCase("Storage Area A-2")) {
			xpath = DBP.sA2Name;
		} else if (StorageArea.equalsIgnoreCase("Storage Area A-3")) {
			xpath = DBP.sA3Name;
		} else if (StorageArea.equalsIgnoreCase("Storage Area A-4")) {
			xpath = DBP.sA4Name;
			//Unifia_Admin_Selenium.isMoveRight = true;
		}
		if (Unifia_Admin_Selenium.isMoveRight) {
			//qvd_a.clickElement(DBP.cabNext);
		}
		if(UAS.driver.findElementsByXPath(xpath).size()>0){
			UAS.driver.findElementByXPath(xpath).click();
		}else{
			//UAS.driver.navigate().refresh();
			qvd_a.clickElement(DBP.cabNext);
			Thread.sleep(5000);
			UAS.driver.findElementByXPath(xpath).click();
		}

		int rowCount = gf.getTableRowCount(tableContentXpath);
		if (rowCount >= 1) {
			for (int i = 1; i <= rowCount; i++) {
				if (UAS.driver.findElementsByXPath(tableContentXpath + "/tr[" + i + "]/td[4]/div/span").size() > 0) {
					DashBoardData.put("ScopeModel" + i,UAS.driver.findElementByXPath(tableContentXpath + "/tr[" + i	+ "]/td[1]/span").getText());
					DashBoardData.put("ScopeName" + i,UAS.driver.findElementByXPath(tableContentXpath + "/tr[" + i+ "]/td[2]/span").getText());
					DashBoardData.put("ScopeSerialNumber" + i,UAS.driver.findElementByXPath(tableContentXpath + "/tr[" + i+ "]/td[3]/span").getText());
					DashBoardData.put("HangTime" + i,UAS.driver.findElementByXPath(tableContentXpath + "/tr[" + i+ "]/td[4]/div/span").getText());
					DashBoardData.put("LocationName" + i, StorageArea);
				}
			}
		}
		return DashBoardData;
	}
	 
	 public String[] getScopeNames_SAHT(){
		 String Scopes= "";
		 int rowCount=gf.getTableRowCount(IPD.sahtTableContent+"/div[2]/table/tbody");
		 for(int i=1;i<=rowCount;i++){
			 if(UAS.driver.findElementsByXPath(IPD.sahtTableContent+"/div[2]/table/tbody/tr["+i+"]/td").size()>0){
				 System.out.println("ScopeName"+UAS.driver.findElementByXPath(IPD.sahtTableContent+"/div[2]/table/tbody/tr["+i+"]/td[2]/span").getText());
				 Scopes+=UAS.driver.findElementByXPath(IPD.sahtTableContent+"/div[2]/table/tbody/tr["+i+"]/td[2]/span").getText()+";";
			 }
		 }
		 String ScopeNames[]=Scopes.split(";");
		 return ScopeNames;
	 }
	 
	public static boolean isSortedInAscending(String[] data) {
		boolean isAscending = false;
		if (data.length > 1) {
			for (int i = 0; i < data.length - 1; i++) {
				if (data[i].compareTo(data[i+1]) < 0) {
					isAscending = true;
				} else {
					isAscending = false;
					break;
				}
			}
		} else if (data.length == 1) {
			isAscending = true;
		}
		return isAscending;
	}
		
}
