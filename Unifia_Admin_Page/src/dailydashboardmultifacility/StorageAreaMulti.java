package dailydashboardmultifacility;

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
import java.util.List;

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
public class StorageAreaMulti {
	
	
	public QV_GeneralFunc QV_Gen;
	public GeneralFunc gf;
	
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	//public String TestSummary= "\t\t\t DailyDashboard_StorageArea_TestSummary \r\n"; 
	public StringBuffer TestSummary= new StringBuffer("\t\t\t DailyDashboard_StorageArea_TestSummary \r\n");
	public String ResFileName="Multi_DailyDashboard_StorageArea_TestSummary";
	public TestFrameWork.TestHelper TH;
	public String TestRes=null;
	public String ForFileName;
	public Dashboard_Actions DA;
	public Dashboard_Verification DV;
	private TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private String ResFileNameXML="Multi_DailyDashboard_StorageArea_Result";
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
		//UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd, "FAC2","FAC3");
		qvd_v.clickDashBoard();
		
		Connection conn=null;
		Statement statement=null;
		String stmt="";
		List<String> roomsStorage = new ArrayList<String>();
		List<String> scopeID = new ArrayList<String>();
		
		for (int eachFac=1;eachFac<=3;eachFac++){
			scopeID.clear();
			roomsStorage.clear();
			String facilityName= "Facility "+eachFac;
			System.out.println("Verifying for "+facilityName);
			gf.selectFromListBox(DBP.selectFacility,facilityName);
			
			if (eachFac==1){
				roomsStorage.add(DBP.expCultHoldNameMulti);
				roomsStorage.add(DBP.expSA1NameMulti);
				roomsStorage.add(DBP.expSA2NameMulti);
				roomsStorage.add(DBP.expSA3NameMulti);
				roomsStorage.add(DBP.expSA4NameMulti);
			}else if (eachFac==2){
				roomsStorage.add(DBP.expCultHoldNameMulti2);
				roomsStorage.add(DBP.expSA1NameMulti2);
				roomsStorage.add(DBP.expSA2NameMulti2);
				roomsStorage.add(DBP.expSA3NameMulti2);
				roomsStorage.add(DBP.expSA4NameMulti2);
			}else if (eachFac==2){
				roomsStorage.add(DBP.expCultHoldNameMulti3);
				roomsStorage.add(DBP.expSA1NameMulti3);
				roomsStorage.add(DBP.expSA2NameMulti3);
				roomsStorage.add(DBP.expSA3NameMulti3);
				roomsStorage.add(DBP.expSA4NameMulti3);
			}
			
			String rnStorage=null, xpath=null, cabinet=null,locatioName=null; 
			String scopeName = null,scopeSerialNum = null,scopeModel = null,lastScannedLocation = null,resultNoRecords=null;
			String scopeHangTime=null;
			
			for (int eachRoomXpath=0;eachRoomXpath<roomsStorage.size();eachRoomXpath++){
				Unifia_Admin_Selenium.isMoveRight=false;
				rnStorage=roomsStorage.get(eachRoomXpath);
				if (rnStorage.equalsIgnoreCase("F1 Culture Hold Cabinet-1")){
					locatioName="F1 Culture Hold Cabinet";
					xpath=DBP.cultHoldCabinetName;
					cabinet="1";
				}
				else if (rnStorage.equalsIgnoreCase("F1 Storage Area A-1")){
					locatioName="F1 Storage Area A";
					xpath=DBP.sA1Name;
					cabinet="1";
				}else if (rnStorage.equalsIgnoreCase("F1 Storage Area A-2")){
					locatioName="F1 Storage Area A";
					xpath=DBP.sA2Name;
					cabinet="2";
				}else if (rnStorage.equalsIgnoreCase("F1 Storage Area A-3")){
					locatioName="F1 Storage Area A";
					xpath=DBP.sA3Name;
					cabinet="3";
				}else if (rnStorage.equalsIgnoreCase("F1 Storage Area A-4")){
					locatioName="F1 Storage Area A";
					xpath=DBP.sA4Name;
					cabinet="4";
					Unifia_Admin_Selenium.isMoveRight=true;
				}else if (rnStorage.equalsIgnoreCase("F2 Culture Hold Cabinet-1")){
					locatioName="F2 Culture Hold Cabinet";
					xpath=DBP.cultHoldCabinetName2;
					cabinet="1";
				}
				else if (rnStorage.equalsIgnoreCase("F2 Storage Area A-1")){
					locatioName="F2 Storage Area A";
					xpath=DBP.sA1Name2;
					cabinet="1";
				}else if (rnStorage.equalsIgnoreCase("F2 Storage Area A-2")){
					locatioName="F2 Storage Area A";
					xpath=DBP.sA2Name2;
					cabinet="2";
				}else if (rnStorage.equalsIgnoreCase("F2 Storage Area A-3")){
					locatioName="F2 Storage Area A";
					xpath=DBP.sA3Name2;
					cabinet="3";
				}else if (rnStorage.equalsIgnoreCase("F2 Storage Area A-4")){
					locatioName="F2 Storage Area A";
					xpath=DBP.sA4Name2;
					cabinet="4";
					Unifia_Admin_Selenium.isMoveRight=true;
				}else if (rnStorage.equalsIgnoreCase("F3 Culture Hold Cabinet-1")){
					locatioName="F3 Culture Hold Cabinet";
					xpath=DBP.cultHoldCabinetName3;
					cabinet="1";
				}else if (rnStorage.equalsIgnoreCase("F3 Storage Area A-1")){
					locatioName="F3 Storage Area A";
					xpath=DBP.sA1Name3;
					cabinet="1";
				}else if (rnStorage.equalsIgnoreCase("F3 Storage Area A-2")){
					locatioName="F3 Storage Area A";
					xpath=DBP.sA2Name3;
					cabinet="2";
				}else if (rnStorage.equalsIgnoreCase("F3 Storage Area A-3")){
					locatioName="F3 Storage Area A";
					xpath=DBP.sA3Name3;
					cabinet="3";
				}else if (rnStorage.equalsIgnoreCase("F3 Storage Area A-4")){
					locatioName="F3 Storage Area A";
					xpath=DBP.sA4Name3;
					cabinet="4";
					Unifia_Admin_Selenium.isMoveRight=true;
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
					Description="Verifying drill down popup contents of "+ rnStorage +"contains "+DBP.noRecordsInPopup+" for "+facilityName;
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
						
						String colsNvalues="Scope Model=="+scopeModel+";Scope Name=="+scopeName+"; Scope Serial Number=="+scopeSerialNum+"; Hang Time (Days)=="+scopeHangTime+"; Time In Cabinet (DD:HH:MM)=="+reqText;
						StringBuffer resultDrillDown=gf.verifyTableContents(DBP.sATableContent,colsNvalues,"Scope Name");
						System.out.println(resultDrillDown);
						Description="Verifying drill down popup contents of "+ rnStorage+ " for Row - "+(eachRec+1)+" for "+facilityName;
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
			String expCultHoldName="",xpathCultHoldName="", expCabColor="", expTotal="",xpathTotal="", expCultApproach="",expCultExceeding="";
			String expSA1Name="",xpathSA1Name="",expSA1Color="",expSA1Total="",xpathSA1Total="",expSA1Appro="",xpathSA1Approach="",expSA1Exceed="",xpathSA1Exceed="";
			String expSA2Name="",xpathSA2Name="",expSA2Color="",expSA2Total="",xpathSA2Total="",expSA2Appro="",xpathSA2Approach="",expSA2Exceed="",xpathSA2Exceed="";
			String expSA3Name="",xpathSA3Name="",expSA3Color="",expSA3Total="",xpathSA3Total="",expSA3Appro="",xpathSA3Approach="",expSA3Exceed="",xpathSA3Exceed="";
			String expSA4Name="",xpathSA4Name="",expSA4Color="",expSA4Total="",xpathSA4Total="",expSA4Appro="",xpathSA4Approach="",expSA4Exceed="",xpathSA4Exceed="";
			if (eachFac==1){
				//cult
				expCultHoldName=DBP.expCultHoldNameMulti;
				xpathCultHoldName=DBP.cultHoldCabinetName;
				expCabColor=DBP.expCabNormalColorGreenMulti;
				expTotal=DBP.expScopeCultHoldTotalMulti;
				xpathTotal=DBP.cultHoldTotalCount;
				expCultApproach=DBP.expScopeCultHoldApproachMulti;
				expCultExceeding=DBP.expScopeCultHoldExceedMulti;
				
				expSA1Name=DBP.expSA1NameMulti;
				xpathSA1Name=DBP.sA1Name;
				expSA1Color=DBP.expCabNormalColorGreenMulti;
				expSA1Total=DBP.expSA1ScopeTotalMulti;
				xpathSA1Total=DBP.sA1TotalCount;
				expSA1Appro=DBP.expSA1ScopeApproMulti;
				xpathSA1Approach=DBP.sA1ApproachCount;				
				expSA1Exceed=DBP.expSA1ScopeExceedMulti;
				xpathSA1Exceed=DBP.sA1ExceedCount;
				
				expSA2Name=DBP.expSA2NameMulti;
				xpathSA2Name=DBP.sA2Name;
				expSA2Color=DBP.expCabApproachColorAmberMulti;
				expSA2Total=DBP.expSA2ScopeTotalMulti;
				xpathSA2Total=DBP.sA2TotalCount;
				expSA2Appro=DBP.expSA2ScopeApproMulti;
				xpathSA2Approach=DBP.sA2ApproachCount;
				expSA2Exceed=DBP.expSA2ScopeExceedMulti;
				xpathSA2Exceed=DBP.sA2ExceedCount;
				
				expSA3Name = DBP.expSA3NameMulti;
				xpathSA3Name = DBP.sA3Name;
				expSA3Color = DBP.expCabNormalColorGreenMulti;
				expSA3Total = DBP.expSA3ScopeTotalMulti;
				xpathSA3Total = DBP.sA3TotalCount;
				expSA3Appro = DBP.expSA3ScopeApproMulti;
				xpathSA3Approach = DBP.sA3ApproachCount;
				expSA3Exceed = DBP.expSA3ScopeExceedMulti;
				xpathSA3Exceed = DBP.sA3ExceedCount;
				
				expSA4Name=DBP.expSA4NameMulti;
				xpathSA4Name=DBP.sA4Name;
				expSA4Color =DBP.expCabExceedColorRedMulti;
				expSA4Total = DBP.expSA4ScopeTotalMulti;
				xpathSA4Total=DBP.sA4TotalCount;
				expSA4Appro=DBP.expSA4ScopeApproMulti;
				xpathSA4Approach=DBP.sA4ApproachCount;
				expSA4Exceed=DBP.expSA4ScopeExceedMulti;
				xpathSA4Exceed=DBP.sA4ExceedCount;

			}else if(eachFac==2){
				expCultHoldName=DBP.expCultHoldNameMulti2;
				xpathCultHoldName=DBP.cultHoldCabinetName2;
				expCabColor=DBP.expCabNormalColorGreenMulti2;
				expTotal=DBP.expScopeCultHoldTotalMulti2;
				xpathTotal=DBP.cultHoldTotalCount2;
				expCultApproach=DBP.expScopeCultHoldApproachMulti2;
				expCultExceeding=DBP.expScopeCultHoldExceedMulti2;
				
				expSA1Name=DBP.expSA1NameMulti2;
				xpathSA1Name=DBP.sA1Name2;
				expSA1Color=DBP.expCabNormalColorGreenMulti2;
				expSA1Total=DBP.expSA1ScopeTotalMulti2;
				xpathSA1Total=DBP.sA1TotalCount2;
				expSA1Appro=DBP.expSA1ScopeApproMulti2;
				xpathSA1Approach=DBP.sA1ApproachCount2;				
				expSA1Exceed=DBP.expSA1ScopeExceedMulti2;
				xpathSA1Exceed=DBP.sA1ExceedCount2;
				
				expSA2Name=DBP.expSA2NameMulti2;
				xpathSA2Name=DBP.sA2Name2;
				expSA2Color=DBP.expCabApproachColorAmberMulti2;
				expSA2Total=DBP.expSA2ScopeTotalMulti2;
				xpathSA2Total=DBP.sA2TotalCount2;
				expSA2Appro=DBP.expSA2ScopeApproMulti2;
				xpathSA2Approach=DBP.sA2ApproachCount2;
				expSA2Exceed=DBP.expSA2ScopeExceedMulti2;
				xpathSA2Exceed=DBP.sA2ExceedCount2;
				
				expSA3Name=DBP.expSA3NameMulti2;
				xpathSA3Name=DBP.sA3Name2;
				expSA3Color=DBP.expCabNormalColorGreenMulti2;
				expSA3Total=DBP.expSA3ScopeTotalMulti2;
				xpathSA3Total=DBP.sA3TotalCount2;
				expSA3Appro=DBP.expSA3ScopeApproMulti2;
				xpathSA3Approach=DBP.sA3ApproachCount2;
				expSA3Exceed=DBP.expSA3ScopeExceedMulti2;
				xpathSA3Exceed=DBP.sA3ExceedCount2;
				
				expSA4Name=DBP.expSA4NameMulti2;
				xpathSA4Name=DBP.sA4Name2;
				expSA4Color =DBP.expCabExceedColorRedMulti2;
				expSA4Total = DBP.expSA4ScopeTotalMulti2;
				xpathSA4Total=DBP.sA4TotalCount2;
				expSA4Appro=DBP.expSA4ScopeApproMulti2;
				xpathSA4Approach=DBP.sA4ApproachCount2;
				expSA4Exceed=DBP.expSA4ScopeExceedMulti2;
				xpathSA4Exceed=DBP.sA4ExceedCount2;
				
				
			}else if(eachFac==3){
				expCultHoldName=DBP.expCultHoldNameMulti3;
				xpathCultHoldName=DBP.cultHoldCabinetName3;
				expCabColor=DBP.expCabNormalColorGreenMulti3;
				expTotal=DBP.expScopeCultHoldTotalMulti3;
				xpathTotal=DBP.cultHoldTotalCount3;
				expCultApproach=DBP.expScopeCultHoldApproachMulti3;
				expCultExceeding=DBP.expScopeCultHoldExceedMulti3;
				
				expSA1Name=DBP.expSA1NameMulti3;
				xpathSA1Name=DBP.sA1Name3;
				expSA1Color=DBP.expCabNormalColorGreenMulti3;
				expSA1Total=DBP.expSA1ScopeTotalMulti3;
				xpathSA1Total=DBP.sA1TotalCount3;
				expSA1Appro=DBP.expSA1ScopeApproMulti3;
				xpathSA1Approach=DBP.sA1ApproachCount3;				
				expSA1Exceed=DBP.expSA1ScopeExceedMulti3;
				xpathSA1Exceed=DBP.sA1ExceedCount3;
				
				expSA2Name=DBP.expSA2NameMulti3;
				xpathSA2Name=DBP.sA2Name3;
				expSA2Color=DBP.expCabApproachColorAmberMulti3;
				expSA2Total=DBP.expSA2ScopeTotalMulti3;
				xpathSA2Total=DBP.sA2TotalCount3;
				expSA2Appro=DBP.expSA2ScopeApproMulti3;
				xpathSA2Approach=DBP.sA2ApproachCount3;
				expSA2Exceed=DBP.expSA2ScopeExceedMulti3;
				xpathSA2Exceed=DBP.sA2ExceedCount3;
				
				expSA3Name=DBP.expSA3NameMulti3;
				xpathSA3Name=DBP.sA3Name3;
				expSA3Color=DBP.expCabExceedColorRedMulti3;
				expSA3Total=DBP.expSA3ScopeTotalMulti3;
				xpathSA3Total=DBP.sA3TotalCount3;
				expSA3Appro=DBP.expSA3ScopeApproMulti3;
				xpathSA3Approach=DBP.sA3ApproachCount3;
				expSA3Exceed=DBP.expSA3ScopeExceedMulti3;
				xpathSA3Exceed=DBP.sA3ExceedCount3;
				
				expSA4Name=DBP.expSA4NameMulti3;
				xpathSA4Name=DBP.sA4Name3;
				expSA4Color =DBP.expCabExceedColorRedMulti3;
				expSA4Total = DBP.expSA4ScopeTotalMulti3;
				xpathSA4Total=DBP.sA4TotalCount3;
				expSA4Appro=DBP.expSA4ScopeApproMulti3;
				xpathSA4Approach=DBP.sA4ApproachCount3;
				expSA4Exceed=DBP.expSA4ScopeExceedMulti3;
				xpathSA4Exceed=DBP.sA4ExceedCount3;
				
			}
		
	    	//Culture Hold Cabinet
			Thread.sleep(5000);
	    	Description="Verifying CultureHold Cabinet Name for "+facilityName;
	    	storageResult(Description,expCultHoldName,xpathCultHoldName,false);
	    	//verifying color
	    	Description="Verifying CultureHold Cabinet color is Green for "+facilityName;
	    	storageResult(Description,DBP.expCabNormalColorGreenMulti,xpathCultHoldName,true);
	    	//Total
	    	Description="Verifying CultureHold Cabinet Total scope count for "+facilityName;
	    	storageResult(Description,expTotal,xpathTotal,false);
	    	//Approaching
	    	Description="Verifying CultureHold Cabinet Approaching scope count for "+facilityName;
	    	storageResult(Description,expCultApproach,xpathTotal,false);
	    	//Exceeding
	    	Description="Verifying CultureHold Exceeding scope count in Cabinets Section for "+facilityName;
	    	storageResult(Description,expCultExceeding,xpathTotal,false);
	    	
	    	//SA1
	    	Description="Verifying Storage Area A-1 name in Cabinets Section for "+facilityName;
	    	storageResult(Description,expSA1Name,xpathSA1Name,false);
	    	//verifying color
	    	Description="Verifying Storage Area A-1's color is Green for "+facilityName;
	    	storageResult(Description,expSA1Color,xpathSA1Name,true);
	    	//Total
	    	Description="Verifying Storage Area A-1's total scope count in Cabinets Section for "+facilityName;
	    	storageResult(Description,expSA1Total,xpathSA1Total,false);
	    	//Approaching
	    	Description="Verifying Storage Area A-1's approaching hang time  scope count for "+facilityName;
	    	storageResult(Description,expSA1Appro,xpathSA1Approach,false);
	    	//Exceeding
	    	Description="Verifying Storage Area A-1's exceeding hang time scope count for "+facilityName;
	    	storageResult(Description,expSA1Exceed,xpathSA1Exceed,false);
	    	
	    	//SA2
	    	Description="Verifying Storage Area A-2 name in Cabinets Section for "+facilityName;
	    	storageResult(Description,expSA2Name,xpathSA2Name, false);
	    	//verifying color
	    	Description="Verifying Storage Area A-2's color is Orange";
	    	storageResult(Description,expSA2Color,xpathSA2Name,true);
	    	//Total
	    	Description="Verifying Storage Area A-2's total scope count in Cabinets Section for "+facilityName;
	    	storageResult(Description,expSA2Total,xpathSA2Total,false);
	    	//Approaching
	    	Description="Verifying Storage Area A-2's approaching hang time  scope count for "+facilityName;
	    	storageResult(Description,expSA2Appro,xpathSA2Approach,false);
	    	//ExceedingxpathSA2Approach
	    	Description="Verifying Storage Area A-2's exceeding hang time scope count for "+facilityName;
	    	storageResult(Description,expSA2Exceed,xpathSA2Exceed,false);
	    	
	    	//SA3
	    	Description="Verifying Storage Area A-3 name in Cabinets Section for "+facilityName;
	    	storageResult(Description,expSA3Name,xpathSA3Name,false);
	    	//verifying color
	    	Description="Verifying Storage Area A-3's color is Red";
	    	storageResult(Description,expSA3Color,xpathSA3Name,true);
	    	//Total
	    	Description="Verifying Storage Area A-3's total scope count in Cabinets Section for "+facilityName;
	    	storageResult(Description,expSA3Total,xpathSA3Total,false);
	    	//Approaching
	    	Description="Verifying Storage Area A-3's approaching hang time  scope count for "+facilityName;
	    	storageResult(Description,expSA3Appro,xpathSA3Approach,false);
	    	//Exceeding
	    	Description="Verifying Storage Area A-3's exceeding hang time scope count for "+facilityName;
	    	storageResult(Description,expSA3Exceed,xpathSA3Exceed,false);
	    	
	    	//SA4
	    	Description="Verifying Storage Area A-4 name in Cabinets Section for "+facilityName;
	    	storageResult(Description,expSA4Name,xpathSA4Name,false);
	    	//verifying color
	    	Description="Verifying Storage Area A-4's color is Red for "+facilityName;
	    	storageResult(Description,expSA4Color,xpathSA4Name,true);
	    	//Total
	    	Description="Verifying Storage Area A-4's total scope count in Cabinets Section for "+facilityName;
	    	storageResult(Description,expSA4Total,xpathSA4Total,false);
	    	//Approaching
	    	Description="Verifying Storage Area A-4's approaching hang time  scope count for "+facilityName;
	    	storageResult(Description,expSA4Appro,xpathSA4Approach	,false);
	    	//Exceeding
	    	Description="Verifying Storage Area A-4's exceeding hang time scope count for "+facilityName;
	    	storageResult(Description,expSA4Exceed,xpathSA4Exceed,false);
	    	//TestSummary=TestSummary+TestRes+"\r\n";
	    	TestSummary.append(TestRes+"\r\n");
	    	TH.WriteToTextFile(ResFileName, TestSummary.toString());
		}
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