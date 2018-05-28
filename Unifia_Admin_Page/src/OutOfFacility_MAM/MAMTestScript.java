package OutOfFacility_MAM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import dailydashboardmultifacility.multiFacilityVerify;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;
import TestFrameWork.UnifiaAdminUserPage.User_Actions;
import TestFrameWork.Unifia_MAM.MAM_Actions;

public class MAMTestScript {
	
	
	private static GeneralFunc gf;
	private static TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	private static TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	private static TestFrameWork.Unifia_MAM.MAM_Actions MAM_A;
	private static TestFrameWork.Unifia_MAM.MAM_Verification MAM_V;
	private static TestFrameWork.Emulator.GetIHValues IHV;
	private static TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	
	private int KE=0;
	private int Bioburden=1;
	private int Culture=0;
	private String result="";
	private String TestSummary= "\t\t\t MAM_TestSummary \r\n\r\n"; 
	private String ResFileName="MAM_TestSummary";
	private String ResFileNameXML="MAM_Result";
	private String TestRes;
	private String Expected,Description;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws Exception {
    	//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	
    	Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String ForFileName = dateFormat.format(date);  
		ResFileNameXML=ResFileNameXML+"_"+ForFileName;
		ResFileNameXML=IHV.Start_Exec_Log(ResFileNameXML);
		Unifia_Admin_Selenium.XMLFileName=ResFileNameXML;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.resultFlag="Pass";
    	
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		Thread.sleep(2000);		
		UA.selectUserRoleMultiFacilityNLogin(browserP, URL, UAS.roleMgr, UAS.userQV1, UAS.userQV1Pwd,"FAC2","FAC3");
		MAM_A.Click_MaterialsAndAssetManagement();
		Thread.sleep(2000);
		
		//checking whether Scope Owner (Facility) column is present on the MAM screen 
		Description="checking whether Scope Owner (Facility) column is present on the MAM screen";
		Expected="Scope Owner (Facility) column should be present on the MAM screen";
		List<String> tableColumns=MAM_A.getMAMColumnNames();
		if(tableColumns.contains("Scope Owner (Facility)")){
			result="Pass - Scope Owner (Facility) column is present on the MAM screen";
		}else{
			UAS.resultFlag="#Failed!#";
			result="#Failed!# - Scope Owner (Facility) column is not present on the MAM screen";
		}
		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
		
		//Verifying MAM Screen sorting both Ascending and Descending for all columns
		String result="";
	    for(String column: tableColumns) {
	        Unifia_Admin_Selenium.driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[1]/th[contains(text(),'"+column+"')]").click();
	        Thread.sleep(1000);
	        List<String> columnValues=MAM_A.getMAMColumnContent(column);
	        String[] columndata = new String[columnValues.size()];
	        columndata = columnValues.toArray(columndata);
			boolean isAscending=gf.isSortedInAscending(columndata);
			if(isAscending){
				result="Pass - "+column+" values in MAM table are displayed in Ascending order when sorted";
			}else if(column.equalsIgnoreCase("Time at Location (DD:HH:MM)")){
				UAS.resultFlag="#Failed!#";
				result="#Failed!#- "+column+" values in MAM table are not displayed in Ascending order when sorted. Bug 12893 - MAM - The data on the MAM screen is not sorting correctly for the field 'Time At Location' ";
			}else{
				UAS.resultFlag="#Failed!#";
				result="#Failed!#- "+column+" values in MAM table are not displayed in Ascending order when sorted";
			}
			System.out.println(result);
			Description="Verifying whether "+column+" values in MAM table are displayed in Ascending order when sorted";
			Expected =column+" values in MAM table should be displayed in Ascending order when sorted";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			
			Unifia_Admin_Selenium.driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[1]/th[contains(text(),'"+column+"')]").click();
	        Thread.sleep(1000);
	        columnValues=MAM_A.getMAMColumnContent(column);
	        columndata = columnValues.toArray(columndata);
			boolean isDescending=gf.isSortedInDecending(columndata);
			if(isDescending){
				result="Pass - "+column+" values in MAM table are displayed in Descending order when sorted";
			}else if(column.equalsIgnoreCase("Time at Location (DD:HH:MM)")){
				UAS.resultFlag="#Failed!#";
				result="#Failed!#- "+column+" values in MAM table are not displayed in Ascending order when sorted. Bug 12893 - MAM - The data on the MAM screen is not sorting correctly for the field 'Time At Location' ";
			}else{
				UAS.resultFlag="#Failed!#";
				result="#Failed!#- "+column+" values in MAM table are not displayed in Descending order when sorted";
			}
			System.out.println(result);
			Description="Verifying whether "+column+" values in MAM table are displayed in Descending order when sorted";
			Expected =column+" values in MAM table should be displayed in Descending order when sorted";
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
	    }
	    
	    //checking whether all filter fields are present or not
  		Description="Checking whether filter fields are present on MAM screen when search button is clicked";
  		Expected="Filter fields should be present on MAM screen when search button is clicked";
  		MAM_A.click_MAM_Search();
  		result=MAM_V.checkMAMFilters(); 
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
  		
  		//Verifying whether MAM table displays records only with Scope Owner (Facility) value as Facility 2 when filtered with F2 Scope5
  		MAM_A.click_MAMClearFilter();
  		List<String> columnData=null;
  		String facility="Facility 2";
  		String scope="F2 Scope5";
  		MAM_A.applyMAMColumnFilter("Scope Name", scope);
  		MAM_A.applyMAMColumnFilter("Scope Serial Number", "5566779");
  		columnData=MAM_A.getMAMColumnContent("Scope Owner (Facility)");
      	boolean filteredData=false;
      	
      	for(String columnContent:columnData){
  	    	if(!columnContent.contains(facility)){
  	    		filteredData=false;
  	    		break;
  	    	}else{
  	    		filteredData=true;
  	    	}
  	    }
  	    if(filteredData){
  			result="Pass - Scope Owner (Facility) values in MAM table when filtered with "+scope+" displayed records only with "+columnData.get(0);
  		}else{
  			UAS.resultFlag="#Failed!#";
  			result="#Failed!# - Scope Owner (Facility) values in MAM table when filtered with "+scope+" did not displayed records with "+facility+" but the data was "+columnData.get(0);
  		}
  	    System.out.println(result);
  		Description="Verifying whether MAM table displays records only with Scope Owner (Facility) value as "+facility+" when filtered with "+scope;
  		Expected ="MAM table should display records only with Scope Owner (Facility) value as "+facility+" when filtered with "+scope;
  		IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
	    
  		//Verifying all filters on MAM screen
	    MAM_A.click_MAMClearFilter();
  		Map<String,String> columnNameAndFilterText=new HashMap<String, String>();
	    columnNameAndFilterText.put("Scope Name", "F3 Scope1");
	    columnNameAndFilterText.put("Scope Serial Number", "6543218");
	    columnNameAndFilterText.put("Scope Model", "CF-H180AL");
	    columnNameAndFilterText.put("Scope Type", "Colon");
	    columnNameAndFilterText.put("Scope Owner (Facility)", "Facility 1");
	    columnNameAndFilterText.put("Last Scope Location", "F3 Reprocessor 4 (FAC3)");
	    columnNameAndFilterText.put("Last Scan Staff ID", "T10");
	    String columnName="";
	    String filterValue="";
	    filteredData=false;
	    for(Map.Entry entrySet: columnNameAndFilterText.entrySet()){
	    	columnName=entrySet.getKey().toString();
	    	filterValue=entrySet.getValue().toString();
	    	MAM_A.click_MAMClearFilter();
	    	//Verifying the clear filters option
			Description="Checking whether all MAM filter values are blank when clear column filters is clicked";
			Expected="All MAM filter values should be blank when clear column filters is clicked";
			result=MAM_V.areAllMAMFiltersBlank();
		    if(result.contains("#Failed!#")){
		    	UAS.resultFlag="#Failed!#";
		    }
		    System.out.println("Result for All MAM filters are Blank when clear column filters is clicked = "+result);
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
			
	    	MAM_A.applyMAMColumnFilter(columnName, filterValue);
	    	columnData=MAM_A.getMAMColumnContent(columnName);
	    	filteredData=false;
	    	
	    	for(String columnContent:columnData){
		    	if(!columnContent.contains(filterValue)){
		    		filteredData=false;
		    		break;
		    	}else{
		    		filteredData=true;
		    	}
		    }
		    if(filteredData){
				result="Pass - "+columnName+" values in MAM table when filtered with "+filterValue+" displayed records only with "+columnData.get(0);
			}else{
				UAS.resultFlag="#Failed!#";
				result="#Failed!# - "+columnName+" values in MAM table when filtered with "+filterValue+" did not displayed records with "+filterValue+" but the data was "+columnData.get(0);
			}
		    
		    System.out.println(result);
			Description="Verifying whether MAM table displays records only with "+columnName+" value as "+filterValue+" when filtered with "+filterValue;
			Expected ="MAM table should display records only with "+columnName+" value as "+filterValue+" when filtered with "+filterValue;
			IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
	    }
	    
	    //Checking MAM multi filter option
	    MAM_A.Click_MaterialsAndAssetManagement();
	    MAM_A.click_MAM_Search();
	    Description="Checking whether MAM screen search option supports multi filter data";
	    Expected="MAM screen search option should support multi filter data";
	    
	    String timeatLocation=calTimeAtLocation("F1 Scope6", "F1 Reprocessor 1 (FAC1)");
	    
	    MAM_A.applyMAMColumnFilter("Scope Type", "Colon");
	    MAM_A.applyMAMColumnFilter("Scope Model", "CF-H190L");
	    MAM_A.applyMAMColumnFilter("Last Scope Location", "F1 Reprocessor 1 (FAC1)");
	    MAM_A.applyMAMColumnFilter("Scope Serial Number", "6677889");
	    MAM_A.applyMAMColumnFilter("Scope Name", "F1 Scope6");
	    int numOfRows=gf.getTableRowCount("//*[@id='mamGrid']/div[2]/table/tbody");
	    if(numOfRows==1){
	    	result="Scope1 MAM Result ="+MAM_V.verifyScopeDetails("F1 Scope6", "Last Scope Location==F1 Reprocessor 1 (FAC1);Scope Serial Number==6677889"
					+";Scope Type==Colon;Scope Model==CF-H190L;Time at Location (DD:HH:MM)=="+timeatLocation).toString();
	    }else{
	    	UAS.resultFlag="#Failed!#";
	    	result="#Failed!# - More than one row is resulted after applying multiple filters";
	    }
	    IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
	    
	    //Checking whether MAM screen is getting auto refreshed and Time at location column values are getting changed
	    Description="Checking whether MAM screen is getting auto refreshed and Time at location column values are getting changed";
	    Expected="MAM screen should be auto refreshed and Time at location column values should be changing";
	    UAS.driver.navigate().refresh();
	    MAM_A.click_MAM_Search();
	    MAM_A.applyMAMColumnFilter("Scope Name", "F1 Scope6");
	    columnData=MAM_A.getMAMColumnContent("Time at Location (DD:HH:MM)");
	    String timeBefore=columnData.get(0);
	    System.out.println("Time at Location (DD:HH:MM) for F1 Scope6 at F1 Reprocessor 1 (FAC1) is "+columnData.get(0));
	    MAM_A.click_MAM_Search();
	    Thread.sleep(60*1000);
	    MAM_A.click_MAM_Search();
	    MAM_A.applyMAMColumnFilter("Scope Name", "F1 Scope6");
	    columnData=MAM_A.getMAMColumnContent("Time at Location (DD:HH:MM)");
	    String timeAfter=columnData.get(0);
	    System.out.println("Time at Location (DD:HH:MM) for F1 Scope6 at F1 Reprocessor 1 (FAC1) after waiting for 60 sec is "+columnData.get(0));
	    if(!timeAfter.equals(timeBefore)){
	    	result="Pass - MAM screen is getting auto refreshed and Time at location column values are getting changed";
	    }else{
	    	UAS.resultFlag="#Failed!#";
	    	result="#Failed!# - MAM screen is not getting auto refreshed and Time at location column values are not getting changed. Bug 12544 - MAM Screen : Time at Location is not refreshed automatically after every minute, user has to manually reload the page to refresh the time.";
	    }
	    System.out.println("AutoRefresh result - "+result);
	    IHV.Exec_Log_Result(ResFileNameXML, Description, Expected, result);
	    IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		
	    if (UAS.resultFlag.contains("#Failed!#")) {
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
   
   	@AfterTest
  	public void PostTest() throws IOException{
	  LP_A.CloseDriver();
  	}
   	
   	public static String calTimeAtLocation(String scope, String location){
   		Connection conn=null;
		Statement statement=null;
		String stmt="";
		int cycleEventID=0;
    	
		int scopeInLocTimeinSecs =0;
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select CycleEventID_FK from ItemHistory where scanitemid_fk=(select scopeid_pk from scope where name='"+scope+"') and "
						+ "LocationID_FK=(select LocationID_PK from Location where Name='"+location.split("\\(")[0].trim()+"')";
			
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				cycleEventID=RS.getInt(1);
			}
			
			stmt="select DateDiff(second, IH.ReceivedDateTime, GETUTCDATE()) as TimeInCabinet from itemhistory IH join Location L on "
						+ "IH.LocationID_FK=L.LocationID_PK where scanitemid_fk=(select scopeid_pk from scope where name='"+scope+"') "
						+ "and IH.ScanItemTypeid_Fk=1 and IH.CycleEventID_FK='"+cycleEventID+"' and IH.LocationID_FK=(select LocationID_PK from Location where Name='"+location.split("\\(")[0].trim()+"')";
			
			System.out.println("stmt="+stmt);
			RS = statement.executeQuery(stmt);
			
			while(RS.next()){
				scopeInLocTimeinSecs=RS.getInt(1);
			}
		
			RS.close();
			conn.close();
		}catch(SQLException e){
			
		}
			
		int minutes = (int)Math.floor((scopeInLocTimeinSecs % 3600) / 60);
		int hours = (scopeInLocTimeinSecs % 86400) / 3600;
		int days = (scopeInLocTimeinSecs % (86400 * 30)) / 86400;
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
		
		String timeatLocation=String.valueOf(days)+":"+reqHours+":"+reqMns;
		return timeatLocation;
   	}
}