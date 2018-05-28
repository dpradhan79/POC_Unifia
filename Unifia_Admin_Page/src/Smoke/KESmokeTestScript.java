package Smoke;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import OER_Simulator.OERGeneralFunc;
import OER_Simulator.OERSimulator;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class KESmokeTestScript {
	
	private OERSimulator OERS;
	private OERGeneralFunc gf;
	private GeneralFunc GF;
	private TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private TestFrameWork.TestHelper TH;
	private TestDataFunc TDF;
	
	private int KE=1;
	private int Bioburden=0;
	private int Culture=0;
	private Connection conn=null;
	
	private int ActualRowCount;
	private int ExpectedRowCount=21;
	private String ExpectedScope="Scope1";
	private String ActualScope;
	private String ExpectedCycleEvent="Reprocessing Start";
	private String ActualCycleEvent;
	private String ExpectedLocationState="Available";
	private String ActualLocationState;
	private String ExpectedScanItem="KE OER Cycle ID";
	private String ActualScanItem;
	
	private String actualResult="\t\t\t KE_SmokeTestScript_TestSummary \n"; 
	private String Description;
	private String ForFileName;
	private String Result1;
	private String Result2;
	private String Result3;
	private String Result4;
	private String TestResFileName="KE_SmokeTestScript_TestSummary_";
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	
	/*Result Detail
	
	*********Step 1*********
	Result1 :  Verification of row count in ItemHistory Table is 21
	*********Step 2*********
	Result2 :  Verification of first row in ItemHistory Table that ScanItem is 'Scope1' and CycleEvent is 'Reprocessing Start'
	*********Step 3*********
	Result3 :  Verification of Location State is 'Available' 
	*********Step 4*********
	Result4 :  Verification of last row in ItemHistory Table that ScanItemType is 'KE OER Cycle ID' and CycleEvent is 'Oer Cycle' */

	 @Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	 public void Test(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, AWTException, ParseException, IOException, XPathExpressionException, SAXException, ParserConfigurationException, ClassNotFoundException, SQLException {
	 //select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
	    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
	    		System.exit(1);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date); 
		 
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.KE_Url="jdbc:oracle:thin:@"+Unifia_Admin_Selenium.KE_Env+":1521:FXDB";
		GF.SyncRemoteMachineTime(Unifia_Admin_Selenium.KE_Env, Unifia_Admin_Selenium.KEMachine_Username, Unifia_Admin_Selenium.KEMachine_pswd, URL);
		
		//Connection to ORacle DB
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		System.out.println("Inserting KE Seed data into Oracle Database");
		//gf.InsertOracleLData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url, Unifia_Admin_Selenium.KE_UserName, Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass, KE, Bioburden, Culture);
		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);
		Thread.sleep(5000);
		System.out.println("Inserting RMM Data into Oracle Database");
		gf.InsertRMM_Data(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		System.out.println("Waiting for 2 mins for the data to come into UNIFIA");
		Thread.sleep(120000);
		
		try{
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.url,Unifia_Admin_Selenium.user,Unifia_Admin_Selenium.pass);
			Statement st=conn.createStatement();
			
			//Verification 1
			String stmt1="select count(ItemHistoryID_PK) from ItemHistory";
			System.out.println(stmt1);
			ResultSet rs=st.executeQuery(stmt1);
			while(rs.next()){
				ActualRowCount=rs.getInt(1);
			}
			if(ActualRowCount==ExpectedRowCount){
				Result1="Pass- The Row Count '"+ActualRowCount+"' is as Expected";
			}else{
				Result1="#Failed!#- The Row count did not match. The row count is "+ActualRowCount+" however, it is supposed to be "+ExpectedRowCount;
			}
			System.out.println(Result1);
			actualResult=actualResult+"\r\n\n Verification of row count in ItemHistory Table---:\r\n\t"+Result1;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			//Verification 2
			String stmt2="select S.Name, CE.Name from ItemHistory IH join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK join Scope S on S.ScopeID_PK=IH.ScanItemID_FK where IH.LastUpdatedDateTime=(select MIN(LastUpdatedDateTime) from ItemHistory)";
			System.out.println(stmt2);
			rs=st.executeQuery(stmt2);
			while(rs.next()){
				ActualScope=rs.getString(1);
				ActualCycleEvent=rs.getString(2);
			}
			if(ActualScope.equalsIgnoreCase(ExpectedScope)){
				if(ActualCycleEvent.equalsIgnoreCase(ExpectedCycleEvent)){
					Result2="Pass- The Scope '"+ActualScope+"' and CycleEvent '"+ActualCycleEvent+"' are as Expected";
				}else{
					Result2="#Failed!#- The Scope '"+ActualScope+"' is as Expected. But, CycleEvent is "+ActualCycleEvent+" however, it is supposed to be "+ExpectedCycleEvent;
				}
			}else{
				if(ActualCycleEvent.equalsIgnoreCase(ExpectedCycleEvent)){
					Result2="#Failed!#- The CycleEvent '"+ActualCycleEvent+"' is as Expected. But, Scope is "+ActualScope+" however, it is supposed to be "+ExpectedScope;
				}else{
					Result2="#Failed!#- Both Scope and CycleEvent are not as expected.Scope is "+ActualScope+" however, it is supposed to be "+ExpectedScope+". CycleEvent is "+ActualCycleEvent+" however, it is supposed to be "+ExpectedCycleEvent;
				}
			}
			System.out.println(Result2);
			actualResult=actualResult+"\r\n\n Verification of first row in ItemHistory Table---:\r\n\t"+Result2;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			//Verification 3
			String stmt3="select ls.Name from LocationStatus l join LocationState ls on l.LocationStateID_FK=ls.LocationStateID_PK where  LocationID_FK in (51,52)";
			System.out.println(stmt3);
			rs=st.executeQuery(stmt3);
			while(rs.next()){
				ActualLocationState=rs.getString(1);
			}
			if(ActualLocationState.equalsIgnoreCase(ExpectedLocationState)){
				Result3="Pass- The Location State '"+ActualLocationState+"' is as Expected";
			}else{
				Result3="#Failed!#- The Location State did not match. The Location State is "+ActualLocationState+" however, it is supposed to be "+ExpectedLocationState;
			}
			System.out.println(Result3);
			actualResult=actualResult+"\r\n\n Verification of Location State---:\r\n\t"+Result3;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			//Verification 4
			ExpectedCycleEvent="Oer Cycle";
			String stmt4="select SI.Name, CE.Name from ItemHistory IH join ScanItemType SI on IH.ScanItemTypeID_FK=SI.ScanItemTypeID_PK Join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK where IH.CycleEventID_FK=36 and IH.LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory)";
			System.out.println(stmt4);
			rs=st.executeQuery(stmt4);
			while(rs.next()){
				ActualScanItem=rs.getString(1);
				ActualCycleEvent=rs.getString(2);
			}
			if(ActualScanItem.equalsIgnoreCase(ExpectedScanItem)){
				if(ActualCycleEvent.equalsIgnoreCase(ExpectedCycleEvent)){
					Result4="Pass- The ScanItem '"+ActualScanItem+"' and CycleEvent '"+ActualCycleEvent+"' are as Expected";
				}else{
					Result4="#Failed!#- The ScanItem '"+ActualScanItem+"' is as Expected. But, CycleEvent is "+ActualCycleEvent+" however, it is supposed to be "+ExpectedCycleEvent;
				}
			}else{
				if(ActualCycleEvent.equalsIgnoreCase(ExpectedCycleEvent)){
					Result4="#Failed!#- The CycleEvent '"+ActualCycleEvent+"' is as Expected. But, ScanItem is "+ActualScanItem+" however, it is supposed to be "+ExpectedScanItem;
				}else{
					Result4="#Failed!#- Both ScanItem and CycleEvent are not as expected.ScanItem is "+ActualScanItem+" however, it is supposed to be "+ExpectedScanItem+". CycleEvent is "+ActualCycleEvent+" however, it is supposed to be "+ExpectedCycleEvent;
				}
			}
			System.out.println(Result4);
			actualResult=actualResult+"\r\n\n Verification of last row in ItemHistory Table---:\r\n\t"+Result4;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException ex){
			 // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
		}
		if (actualResult.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
	}
	 
	@AfterTest
	public void PostTTest() throws IOException {
		LP_A.CloseDriver();
	}
}
