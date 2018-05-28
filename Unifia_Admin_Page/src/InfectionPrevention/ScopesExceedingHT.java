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

public class ScopesExceedingHT {
	
	private GeneralFunc gf;
	private TestFrameWork.QVDashboard.Dashboard_Verification qvd_v; 
	private Dashboard_Actions qvd_a;
	private String TestSummary= "\t\t\t InfectionPrevention_ScopesExceedingHangTime_TestSummary \r\n\r\n"; 
	private String ResFileName="IP_ScopesExceedingHangTime_TestSummary";
	private String ResFileNameXML="IP_ScopesExceedingHangTime_Result";
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
	private static InfectionPrevention.ScopesApproachingHT SAHT;
	
	private static int daysForEHT=10; //Days for Exceeding Hang Time
	private String tableXpath=DBP.sATableContent;
	private String tableContentXpath=tableXpath+"/div[2]/table/tbody";
	private static int DashboardDataCounter=0;
	private String StorageA = "Storage Area A";
	private String cabinet="3";
	private String result="";
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void ScopesExceedingHT_Verification(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException{
		String result_SEHTHeader, result_SEHTCount, result_SEHTColor;
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
		ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML, "Infection Prevention - Scopes Exceeding Hang Time");
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
		//Map<String, String> DashBoardData=get_DailyDashBoardData();
		Map<String, String> DashBoardData=new HashMap<String, String>();
		Map<String, String> exceedingHangTimeData=new HashMap<String, String>();
		
		//Getting the Data from Daily Dashboard Screen for the scopes that are Exceeding hang time 
		List<String> roomsStorage = new ArrayList<String>();
		roomsStorage.add(DBP.expCultHoldName);
		roomsStorage.add(DBP.expSA1Name);
		roomsStorage.add(DBP.expSA2Name);
		roomsStorage.add(DBP.expSA3Name);
		roomsStorage.add(DBP.expSA4Name);
		DashboardDataCounter=0;
		for (int room=0;room<roomsStorage.size();room++){
			DashBoardData.clear();
			DashBoardData=SAHT.get_StorageAreaData(roomsStorage.get(room));
			int rowCount=gf.getTableRowCount(tableContentXpath);
			if(DashBoardData.size()>0){
				for(int i=1;i<=rowCount;i++){
					if(Integer.parseInt(DashBoardData.get("HangTime"+i))>=7){
						DashboardDataCounter++;
						exceedingHangTimeData.put("ScopeModel"+DashboardDataCounter, DashBoardData.get("ScopeModel"+i));
						exceedingHangTimeData.put("ScopeName"+DashboardDataCounter, DashBoardData.get("ScopeName"+i));
						exceedingHangTimeData.put("ScopeSerialNumber"+DashboardDataCounter, DashBoardData.get("ScopeSerialNumber"+i));
						exceedingHangTimeData.put("HangTime"+DashboardDataCounter, DashBoardData.get("HangTime"+i));
						exceedingHangTimeData.put("LocationName"+DashboardDataCounter, DashBoardData.get("LocationName"+i));
					}
				}
			}
		}
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_IP_Dashboard();
		
		String sehtHeader=IP_A.getHeader(IPD.sehtHeader);
		System.out.println(sehtHeader);
		String sehtCount=IP_A.getScopeCount(IPD.sehtCount);
		System.out.println(sehtCount);
		String sehtColor=IP_A.getColor(IPD.sehtColor);
		System.out.println(sehtColor);
		
		//sehtHeader
		if (qvd_v.compareRes(IPD.expSEHTHeader,sehtHeader,true)){
			result_SEHTHeader="Passed: Expected= "+IPD.expSEHTHeader+"; Actual= "+sehtHeader;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SEHTHeader="#Failed!#: Expected= "+IPD.expSEHTHeader+"; Actual= "+sehtHeader;
		}
		System.out.println("result_SEHTHearder = "+result_SEHTHeader);
		Description="Verifying Scopes Exceeding Hang Time Header in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SEHTHeader+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SEHTHeader);
		
		//sehtScope Count
		if (qvd_v.compareRes(String.valueOf(DashboardDataCounter),sehtCount,true)){
			result_SEHTCount="Passed: Expected= "+String.valueOf(DashboardDataCounter)+"; Actual= "+sehtCount;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SEHTCount="#Failed!#: Expected= "+String.valueOf(DashboardDataCounter)+"; Actual= "+sehtCount;
		}
		System.out.println("result_SEHTCount = "+result_SEHTCount);
		Description="Verifying Scopes Exceeding Hang Time Scope Count in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SEHTCount+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SEHTCount);
		
		//sehtColor
		if (qvd_v.compareRes(IPD.expSEHTColor,sehtColor,true)){
			result_SEHTColor="Passed: Expected= "+IPD.expSEHTColor+"; Actual= "+sehtColor;
		}else{
			UAS.resultFlag="#Failed!#";
			result_SEHTColor="#Failed!#: Expected= "+IPD.expSEHTColor+"; Actual= "+sehtColor;
		}
		System.out.println("result_SEHTColor = "+result_SEHTColor);
		Description="Verifying Color of Scopes Exceeding Hang in Infection Prevention Dashboard Screen";
		Expected =Description;
		TestRes=Description+":\r\n\t"+result_SEHTColor+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result_SEHTColor);
		IHV.Exec_Test_Case(ResFileNameXML,"End",Unifia_Admin_Selenium.TestCaseNumber);
		
		//Getting the drilldown popup data with the DailyDashboard Screen
		qvd_a.clickElement(IPD.sehtCount);
		Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
		IHV.Exec_Test_Case(ResFileNameXML,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		StringBuffer resultDrillDown=new StringBuffer();
		if(!result_SEHTCount.contains("#Failed!#")){
			for(int i=1;i<=DashboardDataCounter;i++){
				String colsNvalues="Scope Model=="+exceedingHangTimeData.get("ScopeModel"+i)+";Scope Name=="+exceedingHangTimeData.get("ScopeName"+i)+";Scope Serial Number=="+exceedingHangTimeData.get("ScopeSerialNumber"+i)+";Location Name=="+exceedingHangTimeData.get("LocationName"+i)+";Hang Time=="+exceedingHangTimeData.get("HangTime"+i);
				resultDrillDown=gf.verifyTableContents(IPD.sehtTableContent,colsNvalues,"Scope Name");
				System.out.println(resultDrillDown);
				Description="Verifying drill down popup contents of Scopes Exceeding Hang Time area on Infection Prevention Dashboard  for Row - "+i+" with the data that is taken from Daily Dashboard drilldowns";
				Expected =Description;
				TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
				TestSummary=TestSummary+TestRes+"\r\n";
				IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
			}
		}else{
			result="Scopes Count on Infection Prevention Dashboard screen did not match with the Scopes count taken from DailyDashboard";
			Description="Verifying drill down popup contents of Scopes Exceeding Hang Time area on Infection Prevention Dashboard with the data that is taken from Daily Dashboard drilldowns";
			Expected =Description;
			TestRes=Description+":\r\n\t"+result+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		}
		
		//Getting the drilldown popup data from database
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
				if(RS.getInt(1)==daysForEHT)
					scopes.add(scopeid);
			}
		}
		
		System.out.println ("Need to verify "+scopes.size()+" rows in Scopes Exceeding Hang Time");
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
			String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+";Scope Serial Number=="+scopeSerialNum+";Location Name=="+location+";Hang Time=="+daysForEHT;
			resultDrillDown=gf.verifyTableContents(IPD.sehtTableContent,colsNvalues,"Scope Name");
			System.out.println(resultDrillDown);
			Description="Verifying drill down popup contents of Scopes Exceeding Hang Time area for Row - "+(eachRec+1);
			Expected =Description;
			TestRes=Description+":\r\n\t"+resultDrillDown.toString()+"\r\n";
			TestSummary=TestSummary+TestRes+"\r\n";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
		}		
		
		String HangTimes[]=getHangTimes_SEHT();
		boolean isDecending=isSortedInDecending(HangTimes);
		if(!isDecending){
			result="Pass - HangTime are displayed in Ascending order for Scopes Exceeding Hang Time table";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!#- HangTime are not displayed in Ascending order for Scopes Exceeding Hang Time table. ";
		}
		System.out.println(result);
		Description="Verifying whether HangTime are displayed in Decending order for Scopes Exceeding Hang Time table";
		Expected ="HangTime should be displayed in Decending order for Scopes Exceeding Hang Time table";
		TestRes=Description+":\r\n\t"+result+"\r\n";
		TestSummary=TestSummary+TestRes+"\r\n";
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		
		String ScopeName=exceedingHangTimeData.get("ScopeName1");
		
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
		
		UAS.driver.navigate().refresh();
		Thread.sleep(2000);
		qvd_v.clickDashBoard();
		
		DashBoardData.clear();
		DashBoardData=SAHT.get_StorageAreaData(StorageA+"-"+cabinet);
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
		qvd_a.clickElement(IPD.sehtCount);
		
		String colsNvalues="Scope Name=="+ScopeName;
		resultDrillDown=gf.verifyTableContents(IPD.sehtTableContent,colsNvalues,"Scope Name");
		if(resultDrillDown.toString().contains("#Failed!#")){
			result="Pass - "+ScopeName+" is not present in Scopes Exceeding Hang Time drilldown of Infection Prevention Dashboard screen";
		}else if(resultDrillDown.toString().contains("Pass")){
			UAS.resultFlag="#Failed!#";
			result="#Failed!#- "+ScopeName+" is present in Scopes Exceeding Hang Time drilldown of Infection Prevention Dashboard screen";
		}
		Description="Verifying that "+ScopeName+" should be present in Scopes Exceeding Hang Time drilldown of Infection Prevention Dashboard screen";
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
	 
	 public String[] getHangTimes_SEHT(){
		 String Scopes= "";
		 int rowCount=gf.getTableRowCount(IPD.sehtTableContent+"/div[2]/table/tbody");
		 for(int i=1;i<=rowCount;i++){
			 if(UAS.driver.findElementsByXPath(IPD.sehtTableContent+"/div[2]/table/tbody/tr["+i+"]/td").size()>0){
				 System.out.println("ScopeName"+UAS.driver.findElementByXPath(IPD.sehtTableContent+"/div[2]/table/tbody/tr["+i+"]/td[5]/div/span").getText());
				 Scopes+=UAS.driver.findElementByXPath(IPD.sehtTableContent+"/div[2]/table/tbody/tr["+i+"]/td[5]/div/span").getText()+";";
			 }
		 }
		 String ScopeNames[]=Scopes.split(";");
		 return ScopeNames;
	 }
	 
	public static boolean isSortedInDecending(String[] data) {
		boolean isDecending = false;
		if (data.length > 1) {
			for (int i = 0; i < data.length - 1; i++) {
				if (data[i+1].compareTo(data[i]) > 0) {
					isDecending = true;
				} else {
					isDecending = false;
					break;
				}
			}
		} else if (data.length == 1) {
			isDecending = true;
		}
		return isDecending;
	}
}
