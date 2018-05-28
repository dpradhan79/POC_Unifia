package AdminUserStories.Recon.MRC;

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
import java.text.DateFormat; 
import java.util.Calendar;  
import java.util.Date;
import java.util.TimeZone;

import TestFrameWork.Unifia_Admin_Selenium;

public class MRC_GWAPI extends ExecutionContext{

	public static String user =Unifia_Admin_Selenium.user;
    public static String pass = Unifia_Admin_Selenium.pass;
    public static String connstring = Unifia_Admin_Selenium.connstring;
	public static String url = Unifia_Admin_Selenium.url;
	
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	public static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	private static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	private static TestFrameWork.Emulator.GetIHValues IHV;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Verification MRC_V;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	
	private int Scenario=1;
	private String FileName="";

	public static String actualResult="\t\t\t Recon_MRC_TestSummary \r\n"; 
	private String Description;
	private String Expected;
	private String GridID;
	private String Comment; 
	private int calint;
	private String calchk;
	private long cal = Calendar.getInstance().getTimeInMillis();
	private int NumChanges=0;
	private String[] WhatChanged=new String[10];
	private String ReconDateTime;
	private String Cancelled="No";
	private String UserName="qvtest01";
	
	private Connection conn= null;
	private String stmt;
	private ResultSet MRC_RS;
    
	private String StaffID="";
	private String StaffScanned="No";
    private String currentMRCTestRes="";
    private String currentRepro="";
    private String currentMRCRes="";
    private String currentMRCScanDateTime="";
    private String reconFormatMRCScanTime="";
    private String reconFormatStaffScanTime="";
    private String currentStaffID="";
    private int MRCAssocID;
    private String Repro="";
    private String TestRes="";
    
    private String ModRepro="";
    private String ModMRCTestRes="";
    private String ModMRCRes="";
    private String ModStaffID="";
    
    private String Result_Audit_RefNo;
	private String Result_Audit_ReconDate;
	private String Result_Audit_OriginalScanDate;
	private String Result_Audit_Comment;
	private String Result_Audit_UserName;
	private String Result_Audit_WhatChanged;
	private String Result_Audit_Previous;
	private String Result_Audit_Modified;
	private String Result_AuditLog;
	private String Result_Audit_RowsCount;
	
	private int UTCTimeDiffInHours=0;
	
	private int extraRow=0;
	
	public void e_Start(){
		System.out.println(getCurrentElement().getName());
		FileName="Modify_Workflow_Details_MRC_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		Scenario=0;
	}
	
	public void e_Search() throws InterruptedException, ParseException{
		System.out.println(getCurrentElement().getName());
		
		StaffID = "";
		currentMRCTestRes = "";
		currentRepro = "";
		currentMRCRes = "";
		currentMRCScanDateTime = "";
		reconFormatMRCScanTime = "";
		reconFormatStaffScanTime = "";
		currentStaffID = "";
		MRCAssocID = 0;
		Repro = "";
		TestRes = "";
		ReconDateTime = "";
		Cancelled = "No";
		NumChanges = 0;

		ModRepro = "";
		ModMRCRes = "";
		ModMRCTestRes = "";
		ModStaffID = "";
		StaffScanned="No";
		extraRow=0;
		
	    for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		
		Timestamp MRCDateTime  = null;
		Timestamp StaffDateTime = null;
		try{
			conn= DriverManager.getConnection(connstring);		
    		Statement statement = conn.createStatement();
		/*	NM 11/30/2017 - This is a temporary change to avoid records prior to Day Light Saving Time of Nov. 5 due to bug 11168.
		 * Once bug 11168 is fixed, please uncomment out this stmt, and delete the one directly below. 
		 * 
		 * stmt="select DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), IH.ReceivedDateTime) AS MRCTime, L.Name, "
					+ "TR.Name, IH.AssociationID_FK from ItemHistory IH Join ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK "
					+ "Join TestResultItem TR on TR.TestResultItemID_PK=IH.ScanItemID_FK Join Location L on L.LocationID_PK=IH.LocationID_FK "
					+ "where IH.CycleEventID_FK=17 and IH.LastUpdatedDateTime in (Select min(LastUpdatedDateTime) from ItemHistory "
					+ "where CycleEventID_FK=17 and AssociationID_FK in (select AssociationID_FK from ItemHistory "
					+ "group by AssociationID_FK having Count(ItemHistoryID_PK)=2 and AssociationID_FK in "
					+ "(Select AssociationID_FK from ItemHistory where CycleEventID_FK=17)))";*/
    		
    		stmt="select IH.ReceivedDateTime from ItemHistory IH Join ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK "
					+ "Join TestResultItem TR on TR.TestResultItemID_PK=IH.ScanItemID_FK Join Location L on L.LocationID_PK=IH.LocationID_FK "
					+ "where IH.CycleEventID_FK=17 and IH.LastUpdatedDateTime in (Select min(LastUpdatedDateTime) from ItemHistory "
					+ "where CycleEventID_FK=17 and ReceivedDateTime>'2017-11-05' and AssociationID_FK in (select AssociationID_FK from ItemHistory "
					+ "group by AssociationID_FK having Count(ItemHistoryID_PK)=2 and AssociationID_FK in "
					+ "(Select AssociationID_FK from ItemHistory where CycleEventID_FK=17)))";
    		
    		System.out.println("stmt="+stmt);
    		
			MRC_RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(MRC_RS.next()){
				receivedDataTime=MRC_RS.getTimestamp(1);
			}
			MRC_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="select DATEADD(hh, -"+UTCTimeDiffInHours+", IH.ReceivedDateTime) AS MRCTime, L.Name, "
					+ "TR.Name, IH.AssociationID_FK from ItemHistory IH Join ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK "
					+ "Join TestResultItem TR on TR.TestResultItemID_PK=IH.ScanItemID_FK Join Location L on L.LocationID_PK=IH.LocationID_FK "
					+ "where IH.CycleEventID_FK=17 and IH.LastUpdatedDateTime in (Select min(LastUpdatedDateTime) from ItemHistory "
					+ "where CycleEventID_FK=17 and ReceivedDateTime>'2017-11-05' and AssociationID_FK in (select AssociationID_FK from ItemHistory "
					+ "group by AssociationID_FK having Count(ItemHistoryID_PK)=2 and AssociationID_FK in "
					+ "(Select AssociationID_FK from ItemHistory where CycleEventID_FK=17)))";

			
			System.out.println("stmt="+stmt);
    		
			MRC_RS = statement.executeQuery(stmt);
			
			while(MRC_RS.next()){
				MRCDateTime=MRC_RS.getTimestamp(1);
				currentRepro=MRC_RS.getString(2);
				currentMRCTestRes=MRC_RS.getString(3);
				MRCAssocID=MRC_RS.getInt(4);
			}		
			MRC_RS.close();	
			currentMRCRes=currentMRCTestRes.split(" ")[1];
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
			currentMRCScanDateTime=format.format(MRCDateTime );
			System.out.println ("currentMRCScanDateTime = "+currentMRCScanDateTime);
			
			format=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			reconFormatMRCScanTime=format.format(MRCDateTime );
			System.out.println ("reconFormatMRCScanTime = "+reconFormatMRCScanTime);
			
			System.out.println ("currentRepro = "+currentRepro);
			System.out.println ("currentMRCRes = "+currentMRCRes);
			
			stmt="select IH.ReceivedDateTime from ItemHistory IH join Staff S on IH.ScanItemID_FK=S.StaffID_PK where IH.ScanItemTypeID_FK=2 and IH.AssociationID_FK="+MRCAssocID;
			MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				receivedDataTime=MRC_RS.getTimestamp(1);
			}
			MRC_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			
			stmt="select S.StaffID, DATEADD(hh, -"+UTCTimeDiffInHours+", IH.ReceivedDateTime) from ItemHistory IH join Staff S on IH.ScanItemID_FK=S.StaffID_PK where IH.ScanItemTypeID_FK=2 and IH.AssociationID_FK="+MRCAssocID;
			MRC_RS = statement.executeQuery(stmt);
			
			while(MRC_RS.next()){
				currentStaffID=MRC_RS.getString(1);
				StaffDateTime=MRC_RS.getTimestamp(2);
			}
			
			reconFormatStaffScanTime=format.format(StaffDateTime );
			
			System.out.println("reconFormatStaffScanTime = "+reconFormatStaffScanTime);
			if(!currentStaffID.equalsIgnoreCase("")){
				StaffScanned="Yes";
			}else{
				StaffScanned="No";
			}
			stmt="update Itemhistory set LastUpdatedDateTime=GETUTCDATE() where AssociationID_FK='"+MRCAssocID+"'";
			statement.executeUpdate(stmt);
			
			System.out.println ("currentStaffID = "+currentStaffID);
			MRC_RS.close();
			conn.close();
		}catch(SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		
		IP_A.Click_InfectionPrevention();
		MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
		String mrcDateTime[]=currentMRCScanDateTime.split(" ");
		String MRCDate=mrcDateTime[0];
		System.out.println("Applying date filter from "+MRCDate+" to "+MRCDate);
		IP_A.DateFilter(MRCDate, MRCDate);
		Thread.sleep(2000);
		IP_A.ApplyMRCFilter();
		Thread.sleep(2000);
		GridID=MRC_A.getMRCGridID(currentMRCScanDateTime);
		System.out.println("GridID="+GridID);
	}
	
	public void e_SearchNoStaff() throws InterruptedException, ParseException{
		System.out.println(getCurrentElement().getName());
		
		StaffID = "";
		currentMRCTestRes = "";
		currentRepro = "";
		currentMRCRes = "";
		currentMRCScanDateTime = "";
		reconFormatMRCScanTime = "";
		reconFormatStaffScanTime = "";
		currentStaffID = "";
		MRCAssocID = 0;
		Repro = "";
		TestRes = "";
		ReconDateTime = "";
		Cancelled = "No";
		NumChanges = 0;

		ModRepro = "";
		ModMRCRes = "";
		ModMRCTestRes = "";
		ModStaffID = "";
		StaffScanned="No";
		extraRow=0;
		
	    for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		Timestamp MRCDateTime  = null;
		try{
			conn= DriverManager.getConnection(connstring);		
    		Statement statement = conn.createStatement();			
    		/*	NM 11/30/2017 - This is a temporary change to avoid records prior to Day Light Saving Time of Nov. 5 due to bug 11168.
    		 * Once bug 11168 is fixed, please uncomment out this stmt, and delete the one directly below. 
    		 * 
    		 * stmt="select DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), IH.ReceivedDateTime) AS MRCTime, L.Name, "
					+ "TR.Name, IH.AssociationID_FK from ItemHistory IH Join ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK Join "
					+ "TestResultItem TR on TR.TestResultItemID_PK=IH.ScanItemID_FK Join Location L on L.LocationID_PK=IH.LocationID_FK "
					+ "where IH.CycleEventID_FK=17 and IH.LastUpdatedDateTime in (Select min(LastUpdatedDateTime) from ItemHistory "
					+ "where CycleEventID_FK=17 and AssociationID_FK in (select AssociationID_FK from ItemHistory group by AssociationID_FK "
					+ "having Count(ItemHistoryID_PK)=1 and AssociationID_FK in (Select AssociationID_FK from ItemHistory where CycleEventID_FK=17)))";*/
    		
    		stmt="select IH.ReceivedDateTime from ItemHistory IH Join ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK Join "
					+ "TestResultItem TR on TR.TestResultItemID_PK=IH.ScanItemID_FK Join Location L on L.LocationID_PK=IH.LocationID_FK "
					+ "where IH.CycleEventID_FK=17 and IH.LastUpdatedDateTime in (Select min(LastUpdatedDateTime) from ItemHistory "
					+ "where CycleEventID_FK=17 and ReceivedDateTime>'2017-11-05' and AssociationID_FK in (select AssociationID_FK from ItemHistory group by AssociationID_FK "
					+ "having Count(ItemHistoryID_PK)=1 and AssociationID_FK in (Select AssociationID_FK from ItemHistory where CycleEventID_FK=17)))";
    		
    		System.out.println("stmt="+stmt);
    		
			MRC_RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(MRC_RS.next()){
				receivedDataTime=MRC_RS.getTimestamp(1);
			}
			System.out.println(receivedDataTime);
			MRC_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
    		
    		stmt="select DATEADD(hh, -"+UTCTimeDiffInHours+", IH.ReceivedDateTime) AS MRCTime, L.Name, "
					+ "TR.Name, IH.AssociationID_FK from ItemHistory IH Join ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK Join "
					+ "TestResultItem TR on TR.TestResultItemID_PK=IH.ScanItemID_FK Join Location L on L.LocationID_PK=IH.LocationID_FK "
					+ "where IH.CycleEventID_FK=17 and IH.LastUpdatedDateTime in (Select min(LastUpdatedDateTime) from ItemHistory "
					+ "where CycleEventID_FK=17 and ReceivedDateTime>'2017-11-05' and AssociationID_FK in (select AssociationID_FK from ItemHistory group by AssociationID_FK "
					+ "having Count(ItemHistoryID_PK)=1 and AssociationID_FK in (Select AssociationID_FK from ItemHistory where CycleEventID_FK=17)))";
			System.out.println("stmt="+stmt);
    		
			MRC_RS = statement.executeQuery(stmt);
			
			while(MRC_RS.next()){
				MRCDateTime=MRC_RS.getTimestamp(1);
				currentRepro=MRC_RS.getString(2);
				currentMRCTestRes=MRC_RS.getString(3);
				MRCAssocID=MRC_RS.getInt(4);
			}
			MRC_RS.close();
			
			currentMRCRes=currentMRCTestRes.split(" ")[1];
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
			currentMRCScanDateTime = format.format(MRCDateTime );
			System.out.println ("currentMRCScanDateTime = "+currentMRCScanDateTime);
			
			format=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			reconFormatMRCScanTime=format.format(MRCDateTime);
			System.out.println ("reconFormatMRCScanTime = "+reconFormatMRCScanTime);
			
			System.out.println ("currentRepro = "+currentRepro);
			System.out.println ("currentMRCRes = "+currentMRCRes);
			
			stmt="select S.StaffID from ItemHistory IH join Staff S on IH.ScanItemID_FK=S.StaffID_PK where IH.ScanItemTypeID_FK=2 and IH.AssociationID_FK="+MRCAssocID;
			MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				currentStaffID=MRC_RS.getString(1);
			}
			System.out.println ("currentStaffID = "+currentStaffID);
			if(!currentStaffID.equalsIgnoreCase("")){
				StaffScanned="Yes";
			}else{
				StaffScanned="No";
			}

			stmt="update Itemhistory set LastUpdatedDateTime=GETUTCDATE() where AssociationID_FK='"+MRCAssocID+"'";
			statement.executeUpdate(stmt);
			
			MRC_RS.close();
			conn.close();
		}catch(SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		IP_A.Click_InfectionPrevention();
	    MRC_A.Click_MRCRecordManagement();
	    IP_A.ClearFilter();
		String mrcDateTime[]=currentMRCScanDateTime.split(" ");
		
		String MRCDate=mrcDateTime[0];
		IP_A.DateFilter(MRCDate, MRCDate);
		IP_A.ApplyMRCFilter();
		Thread.sleep(2000);
		GridID=MRC_A.getMRCGridID(currentMRCScanDateTime);
		System.out.println("GridID = "+GridID);
	}
	
	public void e_AddStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		try{
    		conn= DriverManager.getConnection(connstring);		
    		Statement statement = conn.createStatement();
			stmt="Select StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5)";
			System.out.println("stmt="+stmt);
    		
    		MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				ModStaffID = MRC_RS.getString(1);
			}		
			MRC_RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID='"+ModStaffID+"'";
			statement.executeUpdate(stmt);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

		MRC_A.editMRCRecord(GridID);
    	MRC_A.updateMRCStaff(ModStaffID);
    	WhatChanged[NumChanges]="MRC Test Staff ID";
    	NumChanges++;

		Description="e_AddStaff - change the MRC Staff to "+ModStaffID;
	}
	
	public void e_DiffReproCancel() throws InterruptedException{
		System.out.println(getCurrentElement().getName());

    	try{
    		conn= DriverManager.getConnection(connstring);		
    		Statement statement = conn.createStatement();
			stmt="Select Name from Location where IsActive=1 and LocationTypeID_FK=5 and Name!='"+currentRepro+"' and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Location where IsActive=1 and LocationTypeID_FK=5 and Name!='"+currentRepro+"')";
			System.out.println("stmt="+stmt);
    		
    		MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				ModRepro = MRC_RS.getString(1);
			}		
			MRC_RS.close();
			stmt="update Location set LastUpdatedDateTime=GETUTCDATE() where Name='"+ModRepro+"'";
			statement.executeUpdate(stmt);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
    	MRC_A.editMRCRecord(GridID);
    	MRC_A.updateReprocessor(ModRepro);
    	WhatChanged[NumChanges]="Reprocessor";
    	NumChanges++;
		Description="e_DiffReproCancel - change the Reprocessor from "+currentRepro+" to "+ModRepro;
	}
	
	public void e_SameRepro() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//do nothing keep the Reprocessor same.
		ModRepro=currentRepro;
		MRC_A.editMRCRecord(GridID);
    	MRC_A.updateReprocessor(ModRepro);
		Description="e_SameRepro - keep the Reprocessor = "+currentRepro;
	}
	
	public void e_NoRepro() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModRepro="";
		MRC_A.editMRCRecord(GridID);
    	MRC_A.updateReprocessor(ModRepro);
		Description="e_NoRepro - select blank value for Reprocessor = "+ModRepro;
	}
	
	public void e_DiffRepro() throws InterruptedException{
		System.out.println(getCurrentElement().getName());

    	try{
    		conn= DriverManager.getConnection(connstring);		
    		Statement statement = conn.createStatement();
			stmt="Select Name from Location where IsActive=1 and LocationTypeID_FK=5 and Name!='"+currentRepro+"' and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Location where IsActive=1 and LocationTypeID_FK=5 and Name!='"+currentRepro+"')";
			System.out.println("stmt="+stmt);
    		
    		MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				ModRepro = MRC_RS.getString(1);
			}		
			MRC_RS.close();
			stmt="update Location set LastUpdatedDateTime=GETUTCDATE() where Name='"+ModRepro+"'";
			statement.executeUpdate(stmt);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
    	
    	MRC_A.editMRCRecord(GridID);
    	MRC_A.updateReprocessor(ModRepro);
    	WhatChanged[NumChanges]="Reprocessor";
    	NumChanges++;
		Description="e_DiffRepro - change the Reprocessor from "+currentRepro+" to "+ModRepro;
	
	}
	
	public void e_SameResult() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//do nothing keep the MRC Result same.
		ModMRCRes=currentMRCRes;
		ModMRCTestRes=currentMRCTestRes;
		Description="e_SameResult - keep the MRC Result = "+currentMRCRes;
	}
	
	public void e_NoResult() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModMRCRes="";
    	MRC_A.updateMRCResult(ModMRCRes);
		Description="e_NoResult - select blank value for MRC Test Result = "+ModMRCRes;
	}
	
	public void e_DiffResult() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(currentMRCRes.equalsIgnoreCase("Pass")){
			ModMRCRes="Fail";
			ModMRCTestRes="MRC Fail";
		}else if(currentMRCRes.equalsIgnoreCase("Fail")){
			ModMRCRes="Pass";
			ModMRCTestRes="MRC Pass";
		}
		
		MRC_A.updateMRCResult(ModMRCRes);
    	WhatChanged[NumChanges]="MRC Test Result";
    	NumChanges++;
		Description="e_DiffResult - change the MRC Result from "+currentMRCRes+" to "+ModMRCRes;
	}
	
	public void e_SameStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//do nothing... keep the staff in the same. 
		ModStaffID=currentStaffID;
		Description="e_SameStaff - keep the MRC Staff = "+ModStaffID;
	}
	
	public void e_DiffStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
    	try{
    		conn= DriverManager.getConnection(connstring);		
    		Statement statement = conn.createStatement();
			stmt="Select StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID!='"+currentStaffID+"' and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID!='"+currentStaffID+"')";
			System.out.println("stmt="+stmt);
    		
    		MRC_RS = statement.executeQuery(stmt);
			while(MRC_RS.next()){
				ModStaffID = MRC_RS.getString(1);

			}		
			MRC_RS.close();
			stmt="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID='"+ModStaffID+"'";
			statement.executeUpdate(stmt);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

    	MRC_A.updateMRCStaff(ModStaffID);
    	WhatChanged[NumChanges]="MRC Test Staff ID";
    	NumChanges++;

		Description="e_DiffStaff - change the MRC Staff to "+ModStaffID;
	}
	
	public void e_NoStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModStaffID="";
		MRC_A.updateMRCStaff(ModStaffID);
    	WhatChanged[NumChanges]="MRC Test Staff ID";
    	NumChanges++;

		Description="e_NoStaff - change the MRC Staff to "+ModStaffID;
	}
	
	public void e_Comment() throws InterruptedException {
		System.out.println(getCurrentElement().getName());
		// Create and enter text into the comment box.
		calint++;
		calchk = String.valueOf(calint);
		if (calchk.equals(1000)) {
			calint = 0;
			calchk = "0";
		}
		Comment = "Comment " + cal + calchk;
		MRC_A.EnterComment(Comment);
		Description = "e_Comment - comment= " + Comment;
		extraRow=1;
	}
	
	public void e_NoComment() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Comment="";
		Description="e_NoComment - no comment entered.";
		extraRow=0;
	}
	
	public void e_Save() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		MRC_A.saveMRCChanges();
		Description="e_Save.";	
	}
	
	
	public void e_CloseError() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		MRC_A.click_MRCError();
		Description="e_CloseError";
	}
	
	
	
	public void e_NavAuditLog() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		IP_A.Click_AuditLog();
		Description="e_NavAuditLog - navigate to audit log page";					
	}
	
	public void e_NoChangeSave() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		MRC_A.editMRCRecord(GridID);
		MRC_A.saveMRCChanges();
		Description="e_NoChangeSave - click save without making changes.";					
	}
	public void e_Cancel() throws InterruptedException, AWTException{
		System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		MRC_A.cancelMRCChanges();
		Description="e_Cancel.";
		Expected="e_Cancel - verify MRC Cancel works.";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Passed - Successfully Cancelled after making a change to the Reprocessor.");
		
	}
	public void e_NavMRC() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//do-nothing
	}
	
	public void v_MRC() throws InterruptedException{
		Scenario++;
		System.out.println(getCurrentElement().getName());
		Description ="Start of new Scenario "+Scenario;
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}
		System.out.println("=========================================\nScenario: "+Scenario+"\n"+getCurrentElement().getName());
		
	}
	
	public void v_Search() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(currentStaffID.equalsIgnoreCase("")){
			StaffID=" ";
		}else{
			StaffID=currentStaffID;
		}
		String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(GridID,"MRC TEST DATE/TIME=="+currentMRCScanDateTime+";REPROCESSOR=="+currentRepro+";MRC TEST Result=="+currentMRCRes+";MRC TEST STAFF ID=="+StaffID).toString();
		Expected="v_Search - "+currentRepro+" with MRC Result as "+currentMRCRes+" with StaffID = "+currentStaffID+" is found";
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
	}
	
	public void v_Reprocessor() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		String result_MRC="MRC Result ="+MRC_V.verifyReprocessor(ModRepro);
		Expected="v_Reprocessor - Reprocessor value should be "+ModRepro;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
	}
	
	public void v_MRCTestResult() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		String result_MRC="MRC Result ="+MRC_V.verifyMRCRes(ModMRCRes);
		Expected="v_MRCTestResult - MRC test result should be "+ModMRCRes;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
	}
	
	public void v_MRCStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		String result_MRC="MRC Result ="+MRC_V.verifyMRCStaff(ModStaffID);
		Expected="v_MRCStaff - MRC StaffID value should be "+StaffID;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
	}
	
	public void v_Comment() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected="v_Comment - verify the comment field is set to "+Comment;
		String Result_Comment=MRC_V.Verify_Comment(Comment);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Comment);
		if(Comment.equalsIgnoreCase("")){
			Comment="-";
		}
	}
	
	public void v_Save() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(ModStaffID.equalsIgnoreCase("")){
			if(Cancelled.equalsIgnoreCase("Yes")){
				if(currentStaffID.equalsIgnoreCase("")){
					StaffID=" ";
				}else{
					StaffID=currentStaffID;
				}
			}else{
				StaffID=" ";
			}
		}else{
			StaffID=ModStaffID;
		}
		
		if(currentStaffID.equalsIgnoreCase("")||Cancelled.equalsIgnoreCase("Yes")){
			Repro=currentRepro;
			TestRes=currentMRCRes;
		}else{
			Repro=ModRepro;
			TestRes=ModMRCRes;
		}
		String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(GridID,"MRC TEST DATE/TIME=="+currentMRCScanDateTime+";REPROCESSOR=="+Repro+";MRC TEST Result=="+TestRes+";MRC TEST STAFF ID=="+StaffID).toString();
		Expected="v_Save - Reprocessor value should be "+ModRepro+"; MRC test result should be "+ModMRCRes+"; MRC StaffID value should be "+StaffID;
		IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
	}
	
	public void v_AuditLog() throws InterruptedException, ParseException{
		System.out.println(getCurrentElement().getName());
		if(Cancelled.equalsIgnoreCase("No") && NumChanges>0){
			AL_A.click_AuditSearch();
			AL_A.ClearAuditLogSrchCritera();
			
			Integer rowComp=AL_V.compRowsCountinAL_MRC(Comment,NumChanges);
			if (rowComp==0){				
				Result_Audit_RowsCount="Pass - Num of changes done in SRM matched with num of rows in audit log ";
			}else{
				UAS.resultFlag="#Failed!#";
				if (NumChanges+extraRow==rowComp){
					Result_Audit_RowsCount="#Failed!# - Num of changes done in SRM '"+NumChanges+"', match with num of rows in audit log, '"+rowComp+"'. Bug 12812 opened - Audit Log - Two rows are being created when adding a new workflow containing comments.  One for the new row and one for comments added. Should only be 1 row.";
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
					case "Reprocessor":
						//AL_A.Search_ScanDateTime(reconFormatMRCScanTime);
						//AL_A.Search_AL_Date(ReconDateTime);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						AL_A.Search_Location(ModRepro);
						AL_A.Search_UserName(UserName);
						//AL_A.Search_Comments(Comment);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						
						if(StaffScanned.equalsIgnoreCase("No")){						
							GridID=AL_A.GetGridID_AuditLog_ByScanDate(reconFormatMRCScanTime);
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, reconFormatMRCScanTime);
						}else if(StaffScanned.equalsIgnoreCase("Yes")){
							GridID=AL_A.GetGridID_AuditLog_ByScanDate(reconFormatStaffScanTime);
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, reconFormatStaffScanTime);
						}
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
	
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,"-");
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,currentRepro);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModRepro);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						
						break;
					case "MRC Test Result":
						//AL_A.Search_ScanDateTime(reconFormatMRCScanTime);
						//AL_A.Search_AL_Date(ReconDateTime);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						AL_A.Search_Location(ModRepro);
						AL_A.Search_UserName(UserName);
						//AL_A.Search_Comments(Comment);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						
						GridID=AL_A.GetGridID_AuditLog_ByScanDate(reconFormatMRCScanTime);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,"-");
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, reconFormatMRCScanTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,currentMRCTestRes);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModMRCTestRes);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
					
						break;
					case "MRC Test Staff ID":
						String searchScanTime=null;
						if(currentStaffID.equalsIgnoreCase("")){
							Timestamp StaffDateTime = null;
							try{
					    		conn= DriverManager.getConnection(connstring);		
					    		//Statement update1 = conn.createStatement();
								Statement statement = conn.createStatement();
								stmt="select IH.ReceivedDateTime from ItemHistory IH where IH.ScanItemTypeID_FK=2 and IH.AssociationID_FK="+MRCAssocID;
								MRC_RS = statement.executeQuery(stmt);
								
								while(MRC_RS.next()){
									StaffDateTime=MRC_RS.getTimestamp(1);
								}
								DateFormat format=new SimpleDateFormat("MM/dd/yyyy hh:mm a");
								searchScanTime=format.format(StaffDateTime );
								/*reconFormatStaffScanTime=format.format(StaffDateTime );
								searchScanTime=reconFormatStaffScanTime.substring(0, reconFormatStaffScanTime.length()-4);*/
								
								conn.close();
					    	}
					    	catch (SQLException ex){
					    	    // handle any errors
					    	    System.out.println("SQLException: " + ex.getMessage());
					    	    System.out.println("SQLState: " + ex.getSQLState());
					    	    System.out.println("VendorError: " + ex.getErrorCode());	
					    	}
						}else{
							searchScanTime=reconFormatStaffScanTime;
						}
						//AL_A.Search_ScanDateTime(searchScanTime);
						//AL_A.Search_AL_Date(ReconDateTime);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						AL_A.Search_Location(ModRepro);
						AL_A.Search_UserName(UserName);
						//AL_A.Search_Comments(Comment);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						
						GridID=AL_A.GetGridID_AuditLog_ByScanDate(searchScanTime);
						Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,"-");
						System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
						Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
						System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, searchScanTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
						System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
						Result_Audit_UserName=AL_V.Verify_Username(GridID, UserName);
						System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
						Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
						System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
						
						if(currentStaffID.equalsIgnoreCase("")){
							StaffID="-";
						}else{
							StaffID=currentStaffID;
						}
						
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,StaffID);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						
						if(ModStaffID.equalsIgnoreCase("")){
							StaffID="-";
						}else{
							StaffID=ModStaffID;
						}
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, StaffID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						
						break;
					
					default:
	
						break;
	
					}
					Expected="v_AuditLog - verify the Audit Log for Ref No=-  and WhatChanged="+WhatChanged[i];
					Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate
							+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
							+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
					IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);
				}
			}
		}
		
	}
	public void v_NavCancel() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
	}
	
	public void v_SaveError() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		String resultError=MRC_V.verify_MRCError(ModRepro,ModMRCRes);
		Expected="v_SaveError - verify error message is displayed";
		IHV.Exec_Log_Result(FileName, Description, Expected, resultError);
	}
}
