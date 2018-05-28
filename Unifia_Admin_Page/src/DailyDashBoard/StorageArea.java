package DailyDashBoard;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
//import org.sikuli.script.*;
import org.testng.annotations.Test;

import com.beust.jcommander.Strings;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QlikView.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.QVDashboard.*;
public class StorageArea {
	
	public QV_GeneralFunc QV_Gen;
	public GeneralFunc gf;
	
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	//public String TestSummary= "\t\t\t DailyDashboard_StorageArea_TestSummary \r\n"; 
	public StringBuffer TestSummary= new StringBuffer("\t\t\t DailyDashboard_StorageArea_TestSummary \r\n");
	public String ResFileName="DailyDashboard_StorageArea_TestSummary";
	public TestFrameWork.TestHelper TH;
	public String TestRes=null;
	public String ForFileName;
	public Dashboard_Actions DA;
	public Dashboard_Verification DV;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private String ResFileNameXML="DailyDashboard_StorageArea_Result";
	public TestFrameWork.QVDashboard.Dashboard_Verification qvd_v;
	private Dashboard_Actions qvd_a;
	 
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void storageAreaVerifications(String browserP, String URL,String AdminDB) throws InterruptedException, IOException, AWTException, SQLException{
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
    	ResFileNameXML=IHV.Start_Exec_Log1(ResFileNameXML,"Storage Area - Verification");
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
		Unifia_Admin_Selenium.isMoveRight=false;
		String Expected,Description;
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(5000);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		qvd_v.clickDashBoard();
		
		Connection conn=null;
		Statement statement=null;
		String stmt="";
		List<String> roomsStorage = new ArrayList<String>();
		roomsStorage.add(DBP.expCultHoldName);
		roomsStorage.add(DBP.expSA1Name);
		roomsStorage.add(DBP.expSA2Name);
		roomsStorage.add(DBP.expSA3Name);
		roomsStorage.add(DBP.expSA4Name);
		String rnStorage=null, xpath=null, cabinet=null,locatioName=null; 
		String scopeName = null,scopeSerialNum = null,scopeModel = null,lastScannedLocation = null,resultNoRecords=null;
		String scopeHangTime=null;
		List<String> scopeID = new ArrayList<String>();
		for (int eachRoomXpath=0;eachRoomXpath<roomsStorage.size();eachRoomXpath++){
			rnStorage=roomsStorage.get(eachRoomXpath);
			if (rnStorage.equalsIgnoreCase("Culture Hold Cabinet-1")){
				locatioName="Culture Hold Cabinet";
				xpath=DBP.cultHoldCabinetName;
				cabinet="1";
			}
			else if (rnStorage.equalsIgnoreCase("Storage Area A-1")){
				locatioName="Storage Area A";
				xpath=DBP.sA1Name;
				cabinet="1";
			}else if (rnStorage.equalsIgnoreCase("Storage Area A-2")){
				locatioName="Storage Area A";
				xpath=DBP.sA2Name;
				cabinet="2";
			}else if (rnStorage.equalsIgnoreCase("Storage Area A-3")){
				locatioName="Storage Area A";
				xpath=DBP.sA3Name;
				cabinet="3";
			}else if (rnStorage.equalsIgnoreCase("Storage Area A-4")){
				locatioName="Storage Area A";
				xpath=DBP.sA4Name;
				cabinet="4";
				//Unifia_Admin_Selenium.isMoveRight=true;
			}
			//storageResult(Description,DBP.expSA4Name,DBP.sA4Name,false);
			if (Unifia_Admin_Selenium.isMoveRight){
				qvd_a.clickElement(DBP.cabNext);
			}
			qvd_a.clickElement(xpath);
			
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select name from scope where scopeid_pk in (select scopeid_fk from scopestatus where "
				+ "locationid_fk= (select locationid_pk from location where name='"+locatioName+"') and sublocationid="+cabinet+")";
				
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				scopeID.add(RS.getString(1));
			}
			System.out.println ("Need to verify "+scopeID.size()+" rows in "+rnStorage);
			if (scopeID.isEmpty()){
				resultNoRecords=gf.verifyNoRecords(DBP.sATableContent, DBP.noRecordsInPopup);
				Description="Verifying drill down popup contents of "+ rnStorage +"contains "+DBP.noRecordsInPopup;
				Expected =Description;
				TestRes=Description+":\r\n\t"+ resultNoRecords +"\r\n";
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
					stmt="Select round((DateDiff(hour, IH.ReceivedDateTime, GETUTCDATE()))/24,1) AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join "
						+ "ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK where ScopeCycle.ScopeID_FK=(select scopeid_pk from scope where name='"+scopeName+"') and "
						+ "ScopeCycle.CycleID=(select stat.cyclecount from scopestatus stat join scope sc on sc.scopeid_pk=stat.scopeid_fk where "
						+ "stat.ScopeStateID_FK IS NULL and stat.OtherScopeStateID_FK Is NULL and stat.LocationID_FK =(select locationid_pk from location where name='"+locatioName+"')"
						+ "and stat.scopeid_fk=(select scopeid_pk from scope where name='"+scopeName+"'))and IH.CycleEventID_FK=18";
					System.out.println("stmt="+stmt);
					RS = statement.executeQuery(stmt);
					
					//double hrs = 0;
					while(RS.next()){
						//hrs=(RS.getDouble(1));
						scopeHangTime=RS.getString(1);
					}
					//Get cabinet scope in time
					
					stmt="select DateDiff(second, IH.ReceivedDateTime, GETUTCDATE()) as TimeInCabinet from itemhistory as IH where scanitemid_fk=(select scopeid_pk from scope "
						+ "where name='"+scopeName+"') and ScanItemTypeid_Fk=1 and cycleeventid_fk=1";
					System.out.println("stmt="+stmt);
					RS = statement.executeQuery(stmt);
					int scopeInCabTimeinSecs =0;
					//double hrs = 0;
					String associationid;
					while(RS.next()){
						//scopeInCabTimeinSecs=RS.getString(1);
						scopeInCabTimeinSecs=RS.getInt(1);
					}
				
					int minutes = (scopeInCabTimeinSecs % 3600) / 60;
					int hours = (scopeInCabTimeinSecs % 86400) / 3600;
					int days = (scopeInCabTimeinSecs % (86400 * 30)) / 86400;
					String reqHours,reqMns;
					if (hours<10){
						reqHours="0"+hours;
					}else{
						reqHours=String.valueOf(hours);
					}
						
					if (minutes<10){
						reqMns="0"+minutes;
					}else{
						reqMns=String.valueOf(minutes);
					}
					
					String reqText=String.valueOf(days)+":"+reqHours+":"+reqMns;

					System.out.println("reqdays="+days);
					System.out.println("reqhrs="+hours);
					System.out.println("reqmns="+minutes);
				
					String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+"; Scope Serial Number=="+scopeSerialNum+"; Hang Time (Days)=="+scopeHangTime+"; Time In Cabinet (DD:HH:MM)=="+reqText;
					StringBuffer resultDrillDown=gf.verifyTableContents(DBP.sATableContent,colsNvalues,"Scope Name");
					System.out.println(resultDrillDown);
					Description="Verifying drill down popup contents of "+ rnStorage+ " for Row - "+(eachRec+1);
					Expected =Description;
					TestRes=Description+":\r\n\t"+resultDrillDown+"\r\n";
					//TestSummary=TestSummary+TestRes+"\r\n";
					TestSummary.append(TestRes+"\r\n");
					IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, resultDrillDown.toString());
					resultDrillDown.setLength(0);
				}
			}
			if (Unifia_Admin_Selenium.isMoveRight){
				qvd_a.clickElement(DBP.cabPrev);
			}

			scopeID.clear();
		}
    	
		//end
		
    	//Culture Hold Cabinet
		Thread.sleep(5000);
    	Description="Verifying CultureHold Cabinet Name";
    	storageResult(Description,DBP.expCultHoldName,DBP.cultHoldCabinetName,false);
    	//verifying color
    	Description="Verifying CultureHold Cabinet color is Green";
    	storageResult(Description,DBP.expCabNormalColorGreen,DBP.cultHoldCabinetName,true);
    	//Total
    	Description="Verifying CultureHold Cabinet Total scope count";
    	storageResult(Description,DBP.expScopeCultHoldTotal,DBP.cultHoldTotalCount,false);
    	//Approaching
    	Description="Verifying CultureHold Cabinet Approaching scope count";
    	storageResult(Description,DBP.expScopeCultHoldApproach,DBP.cultHoldTotalCount,false);
    	//Exceeding
    	Description="Verifying CultureHold Exceeding scope count in Cabinets Section";
    	storageResult(Description,DBP.expScopeCultHoldExceed,DBP.cultHoldTotalCount,false);
    	//SA1
    	Description="Verifying Storage Area A-1 name in Cabinets Section";
    	storageResult(Description,DBP.expSA1Name,DBP.sA1Name,false);
    	//verifying color
    	Description="Verifying Storage Area A-1's color is Green";
    	storageResult(Description,DBP.expCabNormalColorGreen,DBP.sA1Name,true);
    	//Total
    	Description="Verifying Storage Area A-1's total scope count in Cabinets Section";
    	storageResult(Description,DBP.expSA1ScopeTotal,DBP.sA1TotalCount,false);
    	//Approaching
    	Description="Verifying Storage Area A-1's approaching hang time  scope count";
    	storageResult(Description,DBP.expSA1ScopeAppro,DBP.sA1ApproachCount,false);
    	//Exceeding
    	Description="Verifying Storage Area A-1's exceeding hang time scope count";
    	storageResult(Description,DBP.expSA1ScopeExceed,DBP.sA1ExceedCount,false);
    	//SA2
    	Description="Verifying Storage Area A-2 name in Cabinets Section";
    	storageResult(Description,DBP.expSA2Name,DBP.sA2Name, false);
    	//verifying color
    	Description="Verifying Storage Area A-2's color is Amber";
    	storageResult(Description,DBP.expCabApproachColorAmber,DBP.sA2Name,true);
    	//Total
    	Description="Verifying Storage Area A-2's total scope count in Cabinets Section";
    	storageResult(Description,DBP.expSA2ScopeTotal,DBP.sA2TotalCount,false);
    	//Approaching
    	Description="Verifying Storage Area A-2's approaching hang time  scope count";
    	storageResult(Description,DBP.expSA2ScopeAppro,DBP.sA2ApproachCount,false);
    	//Exceeding
    	Description="Verifying Storage Area A-2's exceeding hang time scope count";
    	storageResult(Description,DBP.expSA2ScopeExceed,DBP.sA2ExceedCount,false);
    	//SA3
    	Description="Verifying Storage Area A-3 name in Cabinets Section";
    	storageResult(Description,DBP.expSA3Name,DBP.sA3Name,false);
    	//verifying color
    	Description="Verifying Storage Area A-3's color is Red";
    	storageResult(Description,DBP.expCabExceedColorRed,DBP.sA3Name,true);
    	//Total
    	Description="Verifying Storage Area A-3's total scope count in Cabinets Section";
    	storageResult(Description,DBP.expSA3ScopeTotal,DBP.sA3TotalCount,false);
    	//Approaching
    	Description="Verifying Storage Area A-3's approaching hang time  scope count";
    	storageResult(Description,DBP.expSA3ScopeAppro,DBP.sA3ApproachCount,false);
    	//Exceeding
    	Description="Verifying Storage Area A-3's exceeding hang time scope count";
    	storageResult(Description,DBP.expSA3ScopeExceed,DBP.sA3ExceedCount,false);
    	//SA4
    	Description="Verifying Storage Area A-4 name in Cabinets Section";
    	storageResult(Description,DBP.expSA4Name,DBP.sA4Name,false);
    	//verifying color
    	Description="Verifying Storage Area A-4's color is Red";
    	storageResult(Description,DBP.expCabExceedColorRed,DBP.sA4Name,true);
    	//Total
    	Description="Verifying Storage Area A-4's total scope count in Cabinets Section";
    	storageResult(Description,DBP.expSA4ScopeTotal,DBP.sA4TotalCount,false);
    	//Approaching
    	Description="Verifying Storage Area A-4's approaching hang time  scope count";
    	storageResult(Description,DBP.expSA4ScopeAppro,DBP.sA4ApproachCount,false);
    	//Exceeding
    	Description="Verifying Storage Area A-4's exceeding hang time scope count";
    	storageResult(Description,DBP.expSA4ScopeExceed,DBP.sA4ExceedCount,false);
    	//TestSummary=TestSummary+TestRes+"\r\n";
    	TestSummary.append(TestRes+"\r\n");
    	TH.WriteToTextFile(ResFileName, TestSummary.toString());
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (TestSummary.toString().contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.contains("#Failed!#"))) {
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	@AfterTest
	public void PostTest() throws IOException{
	  	LandingPage_Actions.CloseDriver();
	  }
	
	public void storageResult(String description,String expVal,String objXpath, boolean colorVerify) throws InterruptedException{
		String applicationVal, result;
		if (colorVerify){
			applicationVal=DV.getColor(objXpath);
		}else{
			applicationVal=DV.getText(objXpath);
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