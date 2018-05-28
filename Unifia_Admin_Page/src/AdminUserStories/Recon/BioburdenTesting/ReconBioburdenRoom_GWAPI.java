package AdminUserStories.Recon.BioburdenTesting;

import org.graphwalker.core.machine.ExecutionContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.AWTException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;  
import java.util.Date;
import java.util.Random;

import TestFrameWork.Unifia_Admin_Selenium;


public class ReconBioburdenRoom_GWAPI  extends ExecutionContext{
	
	public static String user =Unifia_Admin_Selenium.user;
    public static String pass = Unifia_Admin_Selenium.pass;
    public static String connstring = Unifia_Admin_Selenium.connstring;
	public static String url = Unifia_Admin_Selenium.url;

	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	public 	TestFrameWork.Emulator.GetIHValues IHV;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;


	public String Description;  //the description written to the test step log or printed to the console
	public String Expected; //The expected result written to the test step log
	public String GridID; //the GridID of the row being modified in Unifia
	public String Changes="No";

	private int Scenario=1;
	public String FileName="";
	private String TestResFileName="Modify_Bioburden_TestSummary_";

	public String ForFileName;
	
	public String ScopeName;
	public String ScopeSerialNo;
	public String ScopeModel;
	public int ScopePK;
	public int CurrentIHPK; // used to get the current records scan history primary key
	public String Comment; //the text entered into the comment box 
	public String RefNo; //the reference number of the record being modified.
	public int AssociationID; //association ID of the record being modified. 
	public int LocationID; //the location's primary key
	public String LocationName; //location name (i.e. Sink 1) 

	public int CurrentStaffPK;  //staff's primary key
	public String CurrentStaffID;
	public int ModStaffPK; //the primary key of the updated staff for scanning the scope into Bio Area
	public String ModStaffID;
	public String BioDate; //the date of the record being modified.
	public String BioTime; //the time of the record being modified.
	public String BioDateTime;
	public String BioDateTimeNoSec;
	public Date CurrentBioDate=new Date(),ModBioDate=new Date(),CurrentBioTime=new Date(),ModBioTime=new Date();
	public String StaffScanDate,BioResultScanDate,BioScanResultScanDate,BioKeyResultScanDate;
	public int CurrentBioResultPK,CurrentBioScanResultPK,CurrentBioKeyResultPK,ModBioScanResultPK;
	public String CurrentBioResult;
	public String ModBioResult;
	public String CurrentBioScanResult;
	public String ModBioScanResult;
	public String CurrentBioKeyResult;
	public String ModBioKeyResult;

	public String ErrorMessage;
	
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	public long cal = Calendar.getInstance().getTimeInMillis(); 
	Calendar cal2 = Calendar.getInstance();

	public Connection conn= null;
    public String stmt;
    public String stmt1;
	public SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
	public SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

	public String Result_ScopeModel;
	public String Result_ScopeName;
	public String Result_ScopeSerialNo;
	public String Result_RefNum;
	public String Result_BioResult;
	public String Result_BioScan;
	public String Result_BioKey;
	public String Result_Staff;
	public String Result_ErrorMsg;

	public String Result_Comment;
	public String ReconDateTime;
	public String ScanDateTime="";
	public String UserName="qvtest01";
	public String Cancelled="No";
	public int NumChanges=0;
	public String[] WhatChanged=new String[10];
	
	public String Result_Audit_RefNo;
	public String Result_Audit_ReconDate;
	public String Result_Audit_ScopeName;
	public String Result_Audit_OriginalScanDate;
	public String Result_Audit_Comment;
	public String Result_Audit_UserName;
	public String Result_Audit_Location;
	public String Result_Audit_WhatChanged;
	public String Result_Audit_Previous;
	public String Result_Audit_Modified;
	public String Result_AuditLog;
	public String ClearValue;
	public String Result_Audit_RowsCount;

	public ResultSet Bio_RS;  //result set used to get the Bioburden record to be modified.
	public int UTCTimeDiffInHours=0;
	private String Result_ChevronColor;
	private boolean incompleteDetailsFlag=false;
	private int extraRow=0;

	public void e_Start() throws InterruptedException, ParseException{
		System.out.println(getCurrentElement().getName());
		FileName="Modify_Workflow_Details_Bioburden_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		Scenario=0;
		UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();
	   	System.out.println("UTCTimeDiffInHours = "+UTCTimeDiffInHours);
	}
	
	public void e_SearchCancel() throws InterruptedException, ParseException{
		Changes="No";
		Cancelled="No";
		ClearValue="No";
		NumChanges=0;
		GridID="";
		StaffScanDate="";
		ReconDateTime="";
		ModStaffID="";
		ModStaffPK=0;
	   	AssociationID=0;
	   	RefNo="";
	   	BioDate="";
	   	BioTime="";
	   	CurrentIHPK=0;
	   	ScopeName="";
		ScopeSerialNo="";
		ScopePK=0;
		CurrentBioDate=new Date();
		CurrentBioTime=new Date();
		ScopeModel="";
		BioDateTime="";
		BioDateTimeNoSec="";
		CurrentStaffPK=0; 
		CurrentStaffID="";
		CurrentBioResult="";
		ModBioResult="";
		BioResultScanDate="";
		CurrentBioResultPK=0;
		CurrentBioScanResultPK=0;
		CurrentBioScanResult="";
		ModBioScanResult="";
		BioScanResultScanDate="";
		ModBioScanResultPK=0;
		CurrentBioKeyResultPK=0;
		CurrentBioKeyResult="";
		ModBioKeyResult="";
		ErrorMessage="No";
		Comment="";
		incompleteDetailsFlag=false;
		extraRow=0;
		
		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK "
					+ "join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
					+ "where IH.CycleEventID_FK=27 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=27)";
			
    		Bio_RS = statement.executeQuery(stmt);
    		Timestamp receivedDataTime=null;
			while(Bio_RS.next()){
				receivedDataTime=Bio_RS.getTimestamp(1);
			}
			Bio_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select IH.AssociationID_FK, Concat(IH.ScanItemID_FK,'-', SC.CycleID) as RefNo, Loc.Name, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ " FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime, ItemHistoryID_PK,Scope.name as SName,Scope.SerialNumber,Scope.ScopeID_PK,"
					+ "IH.ReceivedDateTime,SM.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK "
					+ "join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK  join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
					+ "where IH.CycleEventID_FK=27 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=27)";
			//System.out.println("stmt="+stmt);
    		
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				AssociationID = Bio_RS.getInt(1); 
				RefNo = Bio_RS.getString(2);
				LocationName=Bio_RS.getString(3);
				BioDate=Bio_RS.getString(4);
				BioTime=Bio_RS.getString(5);
				CurrentIHPK=Bio_RS.getInt(6);
				ScopeName=Bio_RS.getString(7);
				ScopeSerialNo=Bio_RS.getString(8);
				ScopePK=Bio_RS.getInt(9);
				CurrentBioDate=Bio_RS.getDate(10);
				CurrentBioTime=Bio_RS.getTime(10);
				ScopeModel=Bio_RS.getString(11);
				BioDateTime=Bio_RS.getString(12);
				BioDateTimeNoSec=Bio_RS.getString(13);
			}		
			Bio_RS.close();
			stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
			statement.executeUpdate(stmt1);
			
			stmt="select IH.ScanItemID_FK, ST.StaffID, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where CycleEventID_FK=43 and AssociationID_FK="+AssociationID;
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentStaffPK=Bio_RS.getInt(1); 
				CurrentStaffID=Bio_RS.getString(2);
				StaffScanDate=Bio_RS.getString(3);
			}		
			Bio_RS.close();

			stmt="select IH.ScanItemID_FK, Barcode.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Barcode on ScanItemID_FK=BarcodeID_PK where CycleEventID_FK=28 and AssociationID_FK="+AssociationID;
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentBioResultPK=Bio_RS.getInt(1); 
				CurrentBioResult=Bio_RS.getString(2);
				BioResultScanDate=Bio_RS.getString(3);
			}		
			Bio_RS.close();

			stmt="select IH.ScanItemID_FK, Barcode.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Barcode on ScanItemID_FK=BarcodeID_PK where CycleEventID_FK=29 and AssociationID_FK="+AssociationID;
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentBioScanResultPK=Bio_RS.getInt(1); 
				CurrentBioScanResult=Bio_RS.getString(2);
				BioScanResultScanDate=Bio_RS.getString(3);
			}		
			Bio_RS.close();

			stmt="select IH.ScanItemID_FK, KeyEntryScans.KeyEntryValue, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join KeyEntryScans on ScanItemID_FK=KeyEntryID_PK where CycleEventID_FK=29 and AssociationID_FK="+AssociationID;
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentBioKeyResultPK=Bio_RS.getInt(1); 
				CurrentBioKeyResult=Bio_RS.getString(2);
				BioKeyResultScanDate=Bio_RS.getString(3);
			}		
			Bio_RS.close();
			
			conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(BioDate,BioDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    Description=getCurrentElement().getName()+" - Search for and select ScopeSerialNo="+ScopeSerialNo+" BioDate="+BioDate+" Ref#="+RefNo;
	}
	
	public void e_SearchScanValue() throws InterruptedException, ParseException{
		Changes="No";
		Cancelled="No";
		ClearValue="No";
		NumChanges=0;
		GridID="";
		StaffScanDate="";
		ReconDateTime="";
		ModStaffID="";
		ModStaffPK=0;
	   	AssociationID=0;
	   	RefNo="";
	   	BioDate="";
	   	BioTime="";
	   	CurrentIHPK=0;
	   	ScopeName="";
		ScopeSerialNo="";
		ScopePK=0;
		CurrentBioDate=new Date();
		CurrentBioTime=new Date();
		ScopeModel="";
		BioDateTime="";
		BioDateTimeNoSec="";
		CurrentStaffPK=0; 
		CurrentStaffID="";
		CurrentBioResult="";
		ModBioResult="";
		BioResultScanDate="";
		CurrentBioResultPK=0;
		CurrentBioScanResultPK=0;
		CurrentBioScanResult="";
		ModBioScanResult="";
		BioScanResultScanDate="";
		ModBioScanResultPK=0;
		CurrentBioKeyResultPK=0;
		CurrentBioKeyResult="";
		ModBioKeyResult="";
		ErrorMessage="No";
		Comment="";
		incompleteDetailsFlag=false;
		extraRow=0;
		
		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join Barcode on ScanItemID_FK=BarcodeID_PK "
					+ "where IH.CycleEventID_FK=29 and ScanItemTypeID_FK=14 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=29 and ScanItemTypeID_FK=14);";
			//System.out.println("stmt="+stmt);
    		
			Bio_RS = statement.executeQuery(stmt);
    		Timestamp receivedDataTime=null;
			while(Bio_RS.next()){
				receivedDataTime=Bio_RS.getTimestamp(1);
			}
			Bio_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select IH.AssociationID_FK,IH.ItemHistoryID_PK, Loc.Name as LocName,IH.ScanItemID_FK as BioScanValue, Barcode.Name,convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME),"
					+ "'MM/dd/yyyy hh:mm tt'),101) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join Barcode on ScanItemID_FK=BarcodeID_PK "
					+ "where IH.CycleEventID_FK=29 and ScanItemTypeID_FK=14 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=29 and ScanItemTypeID_FK=14);";
			//System.out.println("stmt="+stmt);
    		
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				AssociationID = Bio_RS.getInt(1); 
				CurrentIHPK=Bio_RS.getInt(2);
				LocationName=Bio_RS.getString(3);
				CurrentBioScanResultPK=Bio_RS.getInt(4); 
				CurrentBioScanResult=Bio_RS.getString(5);
				BioScanResultScanDate=Bio_RS.getString(6);
			
			}		
			Bio_RS.close();
			stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
			statement.executeUpdate(stmt1);
			
			stmt="Select  IH.ItemHistoryID_PK,Concat(IH.ScanItemID_FK,'-', SC.CycleID) as RefNo,Scope.Name, Scope.SerialNumber, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as BioScopeScanDate, "
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as BioScopeScanTime,IH.ReceivedDateTime,SM.Name,"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
					+ "join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK  join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
					+ "where IH.AssociationID_FK="+AssociationID+" and CycleEventID_FK=27;";
			//System.out.println("stmt="+stmt);
			
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentIHPK = Bio_RS.getInt(1); 
				RefNo = Bio_RS.getString(2);
				ScopeName=Bio_RS.getString(3);
				ScopeSerialNo=Bio_RS.getString(4);
				BioDate=Bio_RS.getString(5);
				BioTime=Bio_RS.getString(6);
				CurrentBioDate=Bio_RS.getDate(7);
				CurrentBioTime=Bio_RS.getTime(7);
				ScopeModel=Bio_RS.getString(8);
				BioDateTime=Bio_RS.getString(9);
				BioDateTimeNoSec=Bio_RS.getString(10);
			}		
			Bio_RS.close();
			stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
			statement.executeUpdate(stmt1);

			
			stmt="select IH.ScanItemID_FK, ST.StaffID, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where CycleEventID_FK=43 and AssociationID_FK="+AssociationID;
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentStaffPK=Bio_RS.getInt(1); 
				CurrentStaffID=Bio_RS.getString(2);
				StaffScanDate=Bio_RS.getString(3);
			}		
			Bio_RS.close();

			stmt="select IH.ScanItemID_FK, Barcode.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Barcode on ScanItemID_FK=BarcodeID_PK where CycleEventID_FK=28 and AssociationID_FK="+AssociationID;
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentBioResultPK=Bio_RS.getInt(1); 
				CurrentBioResult=Bio_RS.getString(2);
				BioResultScanDate=Bio_RS.getString(3);
			}		
			Bio_RS.close();
			
			conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(BioDate,BioDate);
	    IP_A.ApplyFilter();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(BioDate,BioDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    Description=getCurrentElement().getName()+" - Search for and select ScopeSerialNo="+ScopeSerialNo+" BioDate="+BioDate+" Ref#="+RefNo;
	}
	
	public void e_SearchKeyValue() throws InterruptedException, ParseException{
		Changes="No";
		Cancelled="No";
		ClearValue="No";
		NumChanges=0;
		GridID="";
		StaffScanDate="";
		ReconDateTime="";
		ModStaffID="";
		ModStaffPK=0;
	   	AssociationID=0;
	   	RefNo="";
	   	BioDate="";
	   	BioTime="";
	   	CurrentIHPK=0;
	   	ScopeName="";
		ScopeSerialNo="";
		ScopePK=0;
		CurrentBioDate=new Date();
		CurrentBioTime=new Date();
		ScopeModel="";
		BioDateTime="";
		BioDateTimeNoSec="";
		CurrentStaffPK=0; 
		CurrentStaffID="";
		CurrentBioResult="";
		ModBioResult="";
		BioResultScanDate="";
		CurrentBioResultPK=0;
		CurrentBioScanResultPK=0;
		CurrentBioScanResult="";
		ModBioScanResult="";
		BioScanResultScanDate="";
		ModBioScanResultPK=0;
		CurrentBioKeyResultPK=0;
		CurrentBioKeyResult="";
		ModBioKeyResult="";
		ErrorMessage="No";
		Comment="";
		incompleteDetailsFlag=false;
		extraRow=0;

		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			
			stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join KeyEntryScans on ScanItemID_FK=KeyEntryID_PK where "
					+ "IH.CycleEventID_FK=29 and ScanItemTypeID_FK=10 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=29 and ScanItemTypeID_FK=10);";
			
			Bio_RS = statement.executeQuery(stmt);
    		Timestamp receivedDataTime=null;
			while(Bio_RS.next()){
				receivedDataTime=Bio_RS.getTimestamp(1);
			}
			Bio_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select IH.AssociationID_FK,IH.ItemHistoryID_PK, Loc.Name as LocName,IH.ScanItemID_FK as BioKeyValue, KeyEntryScans.KeyEntryValue,"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME),'MM/dd/yyyy hh:mm tt'),101) from ItemHistory IH "
					+ "join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join KeyEntryScans on ScanItemID_FK=KeyEntryID_PK where IH.CycleEventID_FK=29 and ScanItemTypeID_FK=10 and "
					+ "IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=29 and ScanItemTypeID_FK=10);";
			//System.out.println("stmt="+stmt);    	
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				AssociationID = Bio_RS.getInt(1); 
				CurrentIHPK=Bio_RS.getInt(2);
				LocationName=Bio_RS.getString(3);
				CurrentBioKeyResultPK=Bio_RS.getInt(4); 
				CurrentBioKeyResult=Bio_RS.getString(5);
				BioKeyResultScanDate=Bio_RS.getString(6);
			}		
			Bio_RS.close();
			stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
			statement.executeUpdate(stmt1);
			
			stmt="Select  IH.ItemHistoryID_PK,Concat(IH.ScanItemID_FK,'-', SC.CycleID) as RefNo,Scope.Name, Scope.SerialNumber, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as BioScopeScanDate, "
					+ "FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as BioScopeScanTime,IH.ReceivedDateTime,SM.Name,"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
					+ "join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK  join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
					+ "where IH.AssociationID_FK="+AssociationID+" and CycleEventID_FK=27;";
			//System.out.println("stmt="+stmt);    	
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentIHPK = Bio_RS.getInt(1); 
				RefNo = Bio_RS.getString(2); 
				ScopeName=Bio_RS.getString(3);
				ScopeSerialNo=Bio_RS.getString(4);
				BioDate=Bio_RS.getString(5);
				BioTime=Bio_RS.getString(6);
				CurrentBioDate=Bio_RS.getDate(7);
				CurrentBioTime=Bio_RS.getTime(7);
				ScopeModel=Bio_RS.getString(8);
				BioDateTime=Bio_RS.getString(9);
				BioDateTimeNoSec=Bio_RS.getString(10);
			}		
			Bio_RS.close();
			stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
			statement.executeUpdate(stmt1);

			
			stmt="select IH.ScanItemID_FK, ST.StaffID, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where CycleEventID_FK=43 and AssociationID_FK="+AssociationID;
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentStaffPK=Bio_RS.getInt(1); 
				CurrentStaffID=Bio_RS.getString(2);
				StaffScanDate=Bio_RS.getString(3);
			}		
			Bio_RS.close();

			stmt="select IH.ScanItemID_FK, Barcode.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Barcode on ScanItemID_FK=BarcodeID_PK where CycleEventID_FK=28 and AssociationID_FK="+AssociationID;
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				CurrentBioResultPK=Bio_RS.getInt(1); 
				CurrentBioResult=Bio_RS.getString(2);
				BioResultScanDate=Bio_RS.getString(3);
			}		
			Bio_RS.close();
			
			conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(BioDate,BioDate);
	    IP_A.ApplyFilter();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(BioDate,BioDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
	    Description=getCurrentElement().getName()+" - Search for and select ScopeSerialNo="+ScopeSerialNo+" BioDate="+BioDate+" Ref#="+RefNo;
	}
	
	public void e_TestStatusSame() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModBioResult=CurrentBioResult;
		Description=getCurrentElement().getName()+" - Keep the same Bioburden result. ";		
	}

	public void e_TestStatusDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(CurrentBioResult.equalsIgnoreCase("Pass")){
			ModBioResult="Fail";
		}else if(CurrentBioResult.equalsIgnoreCase("Fail")){
			ModBioResult="Pass";
		}else {
			ModBioResult="Pass";
		}
		WF_A.UpdateBioStatus(ModBioResult);
    	WhatChanged[NumChanges]="Bioburden Test Result";
    	NumChanges++;			
    	Description=getCurrentElement().getName()+" - change the Bioburden Test Result to "+ModBioResult;				
	}
		
	public void e_TestStatusBlank() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModBioResult="";
		WF_A.UpdateBioStatus(ModBioResult);
    	WhatChanged[NumChanges]="Bioburden Test Result";
    	NumChanges++;			
    	Description=getCurrentElement().getName()+" - Update the Bioburden result to Blank. ";
	}
	
	
	public void e_BioScanValueSame() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModBioScanResult=CurrentBioScanResult;
		Description=getCurrentElement().getName()+" - Same Scanned bioburden value ";		
	}

	public void e_BioScanValueDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());

    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
			Statement statement = conn.createStatement();
			stmt="Select BarcodeID_PK, Name from Barcode where IsActive=1 and IsShipped=0 and BarcodeTypeID_FK=4 and BarcodeID_PK!="+CurrentBioScanResultPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Barcode where IsActive=1 and IsShipped=0 and BarcodeTypeID_FK=4 and BarcodeID_PK!="+CurrentBioScanResultPK+")";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			//System.out.println("stmt="+stmt);
    		
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				ModBioScanResultPK= Bio_RS.getInt(1); 
				ModBioScanResult = Bio_RS.getString(2);
			}		
			Bio_RS.close();
			stmt1="update Barcode set LastUpdatedDateTime=GETUTCDATE() where BarcodeID_PK="+ModBioScanResultPK;
			statement.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		WF_A.UpdateBioScannedResult(ModBioScanResult);
    	WhatChanged[NumChanges]="Bioburden Scanned Result";
    	NumChanges++;
    	Description=getCurrentElement().getName()+" - Different Scanned bioburden value ";
		
	}
	
	public void e_BioScanValueBlank() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModBioScanResult="";
		if(!CurrentBioScanResult.equalsIgnoreCase("")){
			WF_A.UpdateBioScannedResult(ModBioScanResult);
	    	WhatChanged[NumChanges]="Bioburden Scanned Result";
	    	NumChanges++;			
		}
		Description=getCurrentElement().getName()+" - Blank Bioburden scan value or key";		
	}
	
	public void e_KeyToScan() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select BarcodeID_PK, Name from Barcode where IsActive=1 and IsShipped=0 and BarcodeTypeID_FK=4 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Barcode where IsActive=1 and IsShipped=0 and BarcodeTypeID_FK=4)";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			//System.out.println("stmt="+stmt);
    		
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				ModBioScanResultPK= Bio_RS.getInt(1); 
				ModBioScanResult = Bio_RS.getString(2);

			}		
			Bio_RS.close();
			stmt1="update Barcode set LastUpdatedDateTime=GETUTCDATE() where BarcodeID_PK="+ModBioScanResultPK;
			statement.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

		WF_A.UpdateBioScannedResult(ModBioScanResult);
    	WhatChanged[NumChanges]="Bioburden Scanned Result";
    	NumChanges++;			
		
    	WhatChanged[NumChanges]="Bioburden Key Entry Result";
    	NumChanges++;					
    	Description=getCurrentElement().getName()+" - clear Bioburden Key value when selecting a scanned value.";
		ModBioKeyResult="";

	}

	public void e_ClearValue() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ClearValue="Yes";
		ModBioScanResult="";
		ModBioKeyResult="";
		if(!CurrentBioScanResult.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Bioburden Scanned Result";
	    	NumChanges++;			
		}
		if(!CurrentBioKeyResult.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Bioburden Key Entry Result";
	    	NumChanges++;			
		}
		Description=getCurrentElement().getName()+" - cleared Bioburden scan value or key entry when Test Result is removed.";		
	}
	
	public void e_BioKeyValueDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModBioKeyResult=Integer.toString(RandomNumber(10,20));
		ModBioScanResult="";
		WF_A.UpdateBioKeyResult(ModBioKeyResult);
    	WhatChanged[NumChanges]="Bioburden Key Entry Result";
    	NumChanges++;					
    	Description=getCurrentElement().getName()+" - Different Key Entry for BioBurden Value";		
	}
	
	public void e_BioKeyValueSame() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModBioKeyResult=CurrentBioKeyResult;
		ModBioScanResult="";
		Description=getCurrentElement().getName()+"  - Same Bioburden Key Entry value ";
		
	}
	public void e_BioKeyValueBlank() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModBioScanResult="";
		ModBioKeyResult=" ";
		if(!CurrentBioKeyResult.equalsIgnoreCase("")){
			WF_A.UpdateBioKeyResult(ModBioKeyResult);
	    	WhatChanged[NumChanges]="Bioburden Key Entry Result";
	    	NumChanges++;			
		}
		Description=getCurrentElement().getName()+" - Blank Bioburden Key value or key";		
		ModBioKeyResult="";
	}
	
	public void e_ScantoKey() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModBioScanResult="";
		ModBioKeyResult=Integer.toString(RandomNumber(10,20));
		WF_A.UpdateBioKeyResult(ModBioKeyResult);
    	WhatChanged[NumChanges]="Bioburden Key Entry Result";
    	NumChanges++;					
    	Description=getCurrentElement().getName()+" - Different Key Entry for BioBurden Value";		
		if(!CurrentBioScanResult.equalsIgnoreCase("")){
			WhatChanged[NumChanges]="Bioburden Scanned Result";
	    	NumChanges++;			
		}
	}
	
	public void e_StaffSame() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModStaffID=CurrentStaffID;
		Description=getCurrentElement().getName()+" - Keep the same staff. ";		
	}
	
	public void e_StaffDiff() throws InterruptedException{
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentStaffPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentStaffPK+")";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			//System.out.println("stmt="+stmt);
    		
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				ModStaffPK= Bio_RS.getInt(1); 
				ModStaffID = Bio_RS.getString(2);

			}		
			Bio_RS.close();
			stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModStaffPK;
			statement.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	WF_A.UpdateBioStaff(ModStaffID);
    	WhatChanged[NumChanges]="Bioburden Staff ID";
    	NumChanges++;			
    	Description=getCurrentElement().getName()+" - change the Bioburden Staff In to "+ModStaffID;		
	}
	
	public void e_StaffBlank() throws InterruptedException{
		ModStaffID="";
    	WF_A.UpdateBioStaff(ModStaffID);
		if(!CurrentStaffID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Bioburden Staff ID";
	    	NumChanges++;			
		}
		Description=getCurrentElement().getName()+" - change the Bio Staff In to "+ModStaffID;
	}
	public void e_ClearStaff() throws InterruptedException{
		ModStaffID="";
		ClearValue="Yes";
		if(!CurrentStaffID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Bioburden Staff ID";
	    	NumChanges++;			
		}
		Description=getCurrentElement().getName()+" - the Bio Staff ID was cleared when the Test Result was removed.";
	}
	public void e_Comment() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		  calint++;
		  calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
			  calint=0;
			  calchk="0";
			}
		Comment="Comment "+cal+calchk;
		WF_A.EnterComment(Comment);
		if(NumChanges==0){
	    	WhatChanged[NumChanges]="Comments";
	    	NumChanges++;			
		}
		Description=getCurrentElement().getName()+" - comment= "+Comment;	
		extraRow=1;
	}
	
	public void e_NoComment() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Comment="";
		Description=getCurrentElement().getName()+" - no comment entered.";	
		extraRow=0;
	}
	public void e_Cancel() throws InterruptedException, AWTException{
		System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		WF_A.Cancel(Changes);
		Description=getCurrentElement().getName()+".";
		Expected="e_Cancel - verify Soiled Area Cancel works.";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Passed - Successfully Cancel after making a change to the Soiled Area");		
	}
	public void e_Save() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		incompleteDetailsFlag=WF_A.getStatusOfLocation(LocationName);
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description=getCurrentElement().getName()+".";							
	}

	public void e_NavAuditLog() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(Cancelled.equalsIgnoreCase("No")){
			IP_A.Click_AuditLog();
			Description=getCurrentElement().getName()+" - navigate to audit log page";				
		}		
	}
	
	public void e_StaffDifferentCancel() throws InterruptedException{
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
			Statement statement = conn.createStatement();
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentStaffPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentStaffPK+")";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			//System.out.println("stmt="+stmt);
    		Bio_RS = statement.executeQuery(stmt);
			while(Bio_RS.next()){
				ModStaffPK= Bio_RS.getInt(1); 
				ModStaffID = Bio_RS.getString(2);
			}		
			Bio_RS.close();
			stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModStaffPK;
			statement.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	WF_A.UpdateBioStaff(ModStaffID);
		Description=getCurrentElement().getName()+" - change the Bioburden Staff In to "+ModStaffID+" prior to clicking cancel.";
		Changes="Yes";
	}

	public void e_Nav1() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_Nav2() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_Nav3() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_Nav4() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_Nav5() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_Nav6() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_Nav7() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_Nav8() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_Nav9() throws InterruptedException{
		Description=getCurrentElement().getName()+" ";
		//System.out.println(getCurrentElement().getName());		
	}
	
	public void e_NavBio() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		Description=getCurrentElement().getName()+" - Navigate back to BioBurden screen. ";
	}
	
	public void v_Nav1(){
		//System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" v_Nav1- ";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Temp entry in xml file for debugging");
		//navigation vertex
	}

	public void v_Nav2(){
		//System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" v_Nav2- ";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Temp entry in xml file for debugging");
		//navigation vertex
	}

	public void v_Nav3(){
		//System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" v_Nav3- ";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Temp entry in xml file for debugging");
		//navigation vertex
	}

	public void v_Nav4(){
		//System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" v_Nav4- ";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Temp entry in xml file for debugging");
		//navigation vertex
	}

	public void v_Nav5(){
		//System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" v_Nav5- ";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Temp entry in xml file for debugging");
		//navigation vertex
		if(ClearValue.equalsIgnoreCase("Yes")){
			ModBioScanResult="-";
			ModBioKeyResult="-";
		}
	}

	public void v_BioArea(){
		Scenario++;
		System.out.println(getCurrentElement().getName());
		//Description ="Start of new Scenario "+Scenario;
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}
		System.out.println("=========================================\nScenario: "+Scenario+"\n"+getCurrentElement().getName());        
	}
	
	public void v_SearchVerify() throws InterruptedException, SQLException{
		System.out.println(getCurrentElement().getName());
		Result_ScopeModel=WF_V.Verify_ScopeModel(ScopeModel);
		Result_ScopeName=WF_V.Verify_ScopeName(ScopeName);
		Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(ScopeSerialNo);
		Result_RefNum=WF_V.Verify_RefNum(RefNo);
		String Result_Search="Result_ScopeModel="+Result_ScopeModel+" Result_ScopeName="+Result_ScopeName+" Result_ScopeSerialNo="+Result_ScopeSerialNo+" Result_RefNum="+Result_RefNum;
		Expected=getCurrentElement().getName()+" - "+ScopeName+" with Serial Number="+ScopeSerialNo+" found and RefNo "+RefNo+" selected.";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Search);
		
	}
	
	public void v_BioResult() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" - verify Bioburden Result is set to "+ModBioResult;
		Result_BioResult=WF_V.Verify_BioResult(ModBioResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_BioResult);
		if(ModBioResult.equalsIgnoreCase("")){
			ModBioResult="-";
		}
		if(CurrentBioResult.equalsIgnoreCase("")){
			CurrentBioResult="-";
		}		
	}
	
	public void v_BioScanValue() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" - verify Bioburden Scanned is set to "+ModBioScanResult;
		Result_BioScan=WF_V.Verify_BioScanValue(ModBioScanResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_BioScan);
		if(ModBioScanResult.equalsIgnoreCase("")){
			ModBioScanResult="-";
		}
		if(CurrentBioScanResult.equalsIgnoreCase("")){
			CurrentBioScanResult="-";
		}
	}
	
	public void v_BioKeyValue() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" - verify Bioburden Key entry value is set to "+ModBioKeyResult;
		Result_BioKey=WF_V.Verify_BioKeyValue(ModBioKeyResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_BioKey);
		if(ModBioKeyResult.equalsIgnoreCase("")){
			ModBioKeyResult="-";
		}
		if(CurrentBioKeyResult.equalsIgnoreCase("")){
			CurrentBioKeyResult="-";
		}
	}
	public void v_Staff() throws InterruptedException, SQLException{
		System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" - verify Staff is set to "+ModStaffID;
		Result_Staff=WF_V.Verify_BioStaff(ModStaffID);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Staff);
		if(ModStaffID.equalsIgnoreCase("")&&!CurrentStaffID.equalsIgnoreCase("")){
			ModStaffID="-"; //update to - for verifying audit log.
		}
		if(CurrentStaffID.equalsIgnoreCase("")){
			CurrentStaffID="-";
		}
		if(ClearValue.equalsIgnoreCase("Yes")){
			ModStaffID="-";
		}
	}
	
	public void v_Comment() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected=getCurrentElement().getName()+" - verify the comment field is set to "+Comment;
		Result_Comment=WF_V.Verify_Comment(Comment);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Comment);
		if(Comment.equalsIgnoreCase("")){
			Comment="-";
		}
	}
	
	public void v_Save() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(ErrorMessage.equalsIgnoreCase("Yes")){
			Expected=getCurrentElement().getName()+" - verify the error message when saving is set to Validation failed";
			Result_ErrorMsg=WF_V.Verify_ErrorMessage("Validation failed");
			IHV.Exec_Log_Result(FileName, Description, Expected, Result_ErrorMsg);
		}
		
		if(!ModBioResult.equalsIgnoreCase("-")){
			//verifying Chevron Color
			String expectedColor="";
			if(incompleteDetailsFlag){
				expectedColor=DBP.rgbOfIncompleteFlow;
			}else{
				expectedColor=DBP.rgbOfCompletedFlow;
			}
			Description="verifying Chevron Color";
			Expected="Chevron Color should be "+expectedColor;
		    Result_ChevronColor=WF_V.Verify_ChevronColor(LocationName, RefNo, expectedColor);
		    IHV.Exec_Log_Result(FileName, Description, Expected, Result_ChevronColor);
		}	
	}
	
	public void v_AuditLog() throws InterruptedException, ParseException{
		System.out.println(getCurrentElement().getName());
		if(Cancelled.equalsIgnoreCase("No") && NumChanges>0){
			AL_A.click_AuditSearch();
			AL_A.ClearAuditLogSrchCritera();
			Integer rowComp=AL_V.compRowsCountinAL(RefNo,ScopeName,Comment,NumChanges);
			if (rowComp==0){				
				Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
			}else{
				UAS.resultFlag="#Failed!#";
				if (NumChanges+extraRow==rowComp){
					Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
				}else{
					Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', did not match with num of rows in audit log, '"+rowComp+"'";
				}
			}
			System.out.println("Result_Audit_RowsCount = "+Result_Audit_RowsCount);
			Description="Num of changes done in SRM matched with num of rows in audit log";
			Expected="Num of changes done in SRM equals num of rows in audit log";
			IHV.Exec_Log_Result(FileName, Description, Expected, Result_Audit_RowsCount);	
			
			if (rowComp==0||NumChanges<rowComp){ 	
				for(int i=0;i<NumChanges;i++){
					switch (WhatChanged[i]) {
					case "Bioburden Test Result":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(LocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
						System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
						if(BioDateTime.equalsIgnoreCase("")){
							BioDateTime=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, BioDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentBioResult);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioResult);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Bioburden Scanned Result":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(LocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
						System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
						if(BioScanResultScanDate.equalsIgnoreCase("")){
							BioScanResultScanDate=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, BioScanResultScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentBioScanResult);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioScanResult);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Bioburden Key Entry Result":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(LocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
						System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
						
						if (BioKeyResultScanDate==null){
							BioKeyResultScanDate=ReconDateTime;
						}else{ 
							if(BioKeyResultScanDate.equalsIgnoreCase("")){
							BioKeyResultScanDate=ReconDateTime;
							}
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, BioKeyResultScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentBioKeyResult);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModBioKeyResult);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Bioburden Staff ID":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(LocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
						System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
						
						if(StaffScanDate.equalsIgnoreCase("")){
							StaffScanDate=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, StaffScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentStaffID);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModStaffID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Comments":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location("-");
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
						System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
	
						if(BioDateTime.equalsIgnoreCase("")){
							BioDateTime=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, "-");
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,"-");
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, "-");
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
	
					default:
	
						break;
	
					}
					Expected=getCurrentElement().getName()+" - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
					Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
							+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
							+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
					IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
				}
			}
		}
	}
	public static int RandomNumber(int minimum, int maximum){
		int Result =0;
		Random rn = new Random();
		int range = maximum - minimum + 1;
		Result=  rn.nextInt(range) + minimum;
		return Result;
	}

}
