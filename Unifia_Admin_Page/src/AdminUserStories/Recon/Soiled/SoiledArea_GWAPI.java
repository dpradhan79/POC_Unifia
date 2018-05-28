package AdminUserStories.Recon.Soiled;

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

import TestFrameWork.Unifia_Admin_Selenium;

public class SoiledArea_GWAPI extends ExecutionContext{
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
	
	private int Scenario=1;
	public String FileName="";
	public String Changes="No";

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
	public int LocationID_PK;
	public String ModLocationName;
	public Date CurrentMCStartDate=new Date(),CurrentMCStartTime=new Date();
	public Date ModMCStartDate=new Date(),ModMCStartTime=new Date();
	public Date CurrentMCEndDate=new Date(),CurrentMCEndTime=new Date();
	public Date ModMCEnd=new Date();
	public String CurrentMCStartDateTime, CurrentMCEndDateTime,CurrentMCStartDateTimeNoSec,CurrentMCEndDateTimeNoSec;
	public String ModMCStartDateTime, ModMCCompleteDateTime;
	
	public int CurrentStaffPK;  //staff's primary key
	public String CurrentStaffID;
	public int ModStaffPK; //the primary key of the updated staff for scanning the scope into Soiled Area
	public String ModStaffID;
	public String SoiledDate; //the date of the record being modified.
	public String SoiledTime; //the time of the record being modified.
	public String SoiledDateTime;
	public String SoiledDateTimeNoSec;
	public Date CurrentSoiledDate=new Date(),ModSoiledDate=new Date(),CurrentSoiledTime=new Date(),ModSoiledTime=new Date();
	public String StaffScanDate,LTScanDate,ModSoiledDateTime;
	public int CurrentLTPK;
	public String CurrentLT;
	public String ModLT;
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
	public String Result_ErrorMsg;
	public String Result_SoiledRoom;
	public String Result_SoiledDate;
	public String Result_Staff;
	public String Result_LTStataus;
	public String Result_MCStart;
	public String Result_MCEnd;

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
	private String Result_Audit_RowsCount;

	public ResultSet SA_RS;  //result set used to get the Soiled record to be modified.
	private int UTCTimeDiffInHours=0;
	private String Result_ChevronColor;
	private boolean incompleteDetailsFlag=false;
	private int extraRow=0;
	
	
	
	
	public void e_Start() throws ParseException{
		System.out.println(getCurrentElement().getName());
		FileName="Modify_Workflow_Details_SoiledArea_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		Scenario=0;
		/*UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();
	   	System.out.println("UTCTimeDiffInHours = "+UTCTimeDiffInHours);*/
	}
	public void e_Search() throws InterruptedException, ParseException{
		System.out.println(getCurrentElement().getName());
		Changes="No";

		CurrentMCStartDate=new Date();
		CurrentMCStartTime=new Date();
		CurrentMCEndDate=new Date();
		CurrentMCEndTime=new Date();
		Cancelled="No";
		NumChanges=0;
		GridID="";
		StaffScanDate="";
		ReconDateTime="";
		ModSoiledDateTime="";
		CurrentMCStartDateTime="";
		CurrentMCEndDateTime="";
		CurrentMCStartDateTimeNoSec="";
		CurrentMCEndDateTimeNoSec="";
		ModMCStartDateTime="";
		ModMCCompleteDateTime="";
		ModStaffID="";
		ModStaffPK=0;
	   	AssociationID=0;
	   	RefNo="";
	   	LocationName="";
	   	SoiledDate="";
	   	SoiledTime="";
	   	CurrentIHPK=0;
	   	ScopeName="";
		ScopeSerialNo="";
		ScopePK=0;
		CurrentSoiledDate=new Date();
		CurrentSoiledTime=new Date();
		ScopeModel="";
		SoiledDateTime="";
		SoiledDateTimeNoSec="";
		CurrentStaffPK=0; 
		CurrentStaffID="";
		CurrentLT="";
		ModLT="";
		LTScanDate="";
		CurrentLTPK=0;
		ErrorMessage="No";
		Comment="";
		incompleteDetailsFlag=false;
		UTCTimeDiffInHours=0;
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
					+ "where IH.CycleEventID_FK=26 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=26)";
			System.out.println("stmt="+stmt);
    		
    		SA_RS = statement.executeQuery(stmt);
    		Timestamp receivedDataTime=null;
			while(SA_RS.next()){
				receivedDataTime=SA_RS.getTimestamp(1);
			}
			SA_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select IH.AssociationID_FK, Concat(IH.ScanItemID_FK,'-', SC.CycleID) as RefNo, Loc.Name, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ " FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime, ItemHistoryID_PK,Scope.name as SName,Scope.SerialNumber,Scope.ScopeID_PK,"
					+ "IH.ReceivedDateTime,SM.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK "
					+ "join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK  join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
					+ "where IH.CycleEventID_FK=26 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=26)";
			System.out.println("stmt="+stmt);
    		
    		SA_RS = statement.executeQuery(stmt);
			while(SA_RS.next()){
				AssociationID = SA_RS.getInt(1); 
				RefNo = SA_RS.getString(2);
				LocationName=SA_RS.getString(3);
				SoiledDate=SA_RS.getString(4);
				SoiledTime=SA_RS.getString(5);
				CurrentIHPK=SA_RS.getInt(6);
				ScopeName=SA_RS.getString(7);
				ScopeSerialNo=SA_RS.getString(8);
				ScopePK=SA_RS.getInt(9);
				CurrentSoiledDate=SA_RS.getDate(10);
				CurrentSoiledTime=SA_RS.getTime(10);
				ScopeModel=SA_RS.getString(11);
				SoiledDateTime=SA_RS.getString(12);
				SoiledDateTimeNoSec=SA_RS.getString(13);
			}		
			SA_RS.close();
			stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
			statement.executeUpdate(stmt1);
			
			stmt="select IH.ScanItemID_FK, ST.StaffID, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where CycleEventID_FK=40 and AssociationID_FK="+AssociationID;
    		SA_RS = statement.executeQuery(stmt);
			while(SA_RS.next()){
				CurrentStaffPK=SA_RS.getInt(1); 
				CurrentStaffID=SA_RS.getString(2);
				StaffScanDate=SA_RS.getString(3);
			}		
			SA_RS.close();

			stmt="select IH.ScanItemID_FK, TR.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join TestResultItem TR on ScanItemID_FK=TestResultItemID_PK where CycleEventID_FK=12 and AssociationID_FK="+AssociationID;
    		SA_RS = statement.executeQuery(stmt);
			while(SA_RS.next()){
				CurrentLTPK=SA_RS.getInt(1); 
				CurrentLT=SA_RS.getString(2);
				LTScanDate=SA_RS.getString(3);
			}		
			SA_RS.close();

			stmt="select ReceivedDateTime, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
					+ "where AssociationID_FK="+AssociationID+" and CycleEventID_FK=13";
			SA_RS = statement.executeQuery(stmt);
			while(SA_RS.next()){
				CurrentMCStartDate=SA_RS.getDate(1);
				CurrentMCStartTime=SA_RS.getTime(1);
				CurrentMCStartDateTime=SA_RS.getString(2);
				CurrentMCStartDateTimeNoSec=SA_RS.getString(3);
			}		
			SA_RS.close();

			stmt="select ReceivedDateTime, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
					+ "where AssociationID_FK="+AssociationID+" and CycleEventID_FK=14";

			SA_RS = statement.executeQuery(stmt);
			while(SA_RS.next()){
				CurrentMCEndDate=SA_RS.getDate(1);
				CurrentMCEndTime=SA_RS.getTime(1);
				CurrentMCEndDateTime=SA_RS.getString(2);
				CurrentMCEndDateTimeNoSec=SA_RS.getString(3);
			}		
			SA_RS.close();

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
	    IP_A.DateFilter(SoiledDate,SoiledDate);
	    IP_A.ApplyFilter();
	    
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(SoiledDate,SoiledDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
		Description="e_Search - Search for and select ScopeSerialNo="+ScopeSerialNo+" SoiledDate="+SoiledDate+" Ref#="+RefNo;
	
	}
	public void e_SASame(){
		System.out.println(getCurrentElement().getName());
		//do nothing keep the Soiled area the same.
		ModLocationName=LocationName;
		Description="e_SASame - keep the Soiled Area="+ModLocationName;
		
	}
	public void e_SADiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select LocationID_PK, Name from Location where IsActive=1 and LocationTypeID_FK=4 and Name!='"+LocationName+"' and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Location where IsActive=1 and LocationTypeID_FK=4 and Name!='"+LocationName+"')";
			// we will update the LastUpdatedDateTime after 
			// making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		SA_RS = statement.executeQuery(stmt);
			while(SA_RS.next()){
				LocationID_PK= SA_RS.getInt(1); 
				ModLocationName = SA_RS.getString(2);
			}		
			SA_RS.close();
			stmt1="update Location set LastUpdatedDateTime=GETUTCDATE() where LocationID_PK="+LocationID_PK;
			statement.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

    	WF_A.UpdateSoiledArea(ModLocationName);
    	WhatChanged[NumChanges]="Soiled Area";
    	NumChanges++;
		Description="e_SADiff - change the Soiled area from "+LocationName+" to "+ModLocationName;
		
	}
	public void e_SANo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModLocationName="";
    	WF_A.UpdateSoiledArea(ModLocationName);
		Description="e_SANo - remove the Soiled area from "+LocationName;
		ErrorMessage="Yes";

	}
	public void e_LTDateSame(){
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentSoiledTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		ModSoiledDateTime=df2.format(CurrentSoiledDate)+" "+df.format(cal2.getTime());
		Description="e_LTDSame - keep the Scope Scan In ="+ModSoiledDateTime;
		
	}
	public void e_LTDateDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentSoiledTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, -2); //change the time by subtracting two minute.
		ModSoiledDateTime=df2.format(CurrentSoiledDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateSoiledDate(ModSoiledDateTime);
    	WhatChanged[NumChanges]="Soiled Area Scope Scan In";
    	NumChanges++;			

		Description="e_LTDateDiff - changed the Scope Scan In to "+ModSoiledDateTime;

	}
	public void e_NoLTDate() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModSoiledDateTime=" ";
		WF_A.UpdateSoiledDate(ModSoiledDateTime);
		ErrorMessage="Yes";

	}
	public void e_LTStaffSame(){
		System.out.println(getCurrentElement().getName());
		ModStaffID=CurrentStaffID;
		Description="e_LTStaffSame - keep the Staff in ="+ModStaffID;

	}
	public void e_LTStaffNo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModStaffID="";
    	WF_A.UpdateSoiledStaff(ModStaffID);
		if(!CurrentStaffID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Soiled Area Staff ID";
	    	NumChanges++;			
		}
		Description="e_LTStaffNo - change the Soiled Area Staff ID to "+ModStaffID;

	}
	public void e_LTStaffDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentStaffPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentStaffPK+")";
			// we will update the LastUpdatedDateTime after 
			// making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		SA_RS = statement.executeQuery(stmt);
			while(SA_RS.next()){
				ModStaffPK= SA_RS.getInt(1); 
				ModStaffID = SA_RS.getString(2);

			}		
			SA_RS.close();
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

    	WF_A.UpdateSoiledStaff(ModStaffID);
    	WhatChanged[NumChanges]="Soiled Area Staff ID";
    	NumChanges++;			
		Description="e_DiffStaff - change the Soiled Area Staff ID to "+ModStaffID;
	}

	public void e_LTStaffDiffCancel() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentStaffPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentStaffPK+")";
			// we will update the LastUpdatedDateTime after 
			// making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		SA_RS = statement.executeQuery(stmt);
			while(SA_RS.next()){
				ModStaffPK= SA_RS.getInt(1); 
				ModStaffID = SA_RS.getString(2);

			}		
			SA_RS.close();
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

    	WF_A.UpdateSoiledStaff(ModStaffID);
		Description="e_LTStaffDiffCancel - change the Soiled Area Staff ID to "+ModStaffID;
	}

	public void e_LTStatSame(){
		System.out.println(getCurrentElement().getName());
		if(CurrentLT.contains("Pass")){
			ModLT="Pass";		
		}else if(CurrentLT.contains("Fail")){
			ModLT="Fail";		
		}
	}
	public void e_LTStatNo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModLT="";
		if(!CurrentLT.equals("")){
			WF_A.UpdateLTStatus(ModLT);
	    	WhatChanged[NumChanges]="Leak Test Result";
	    	NumChanges++;					
		}

		Description="e_LTStatNo - remove the Leak Test status";				
	}
	public void e_LTStatDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(CurrentLT.equalsIgnoreCase("Leak Test Pass")){
			ModLT="Fail";
		}else if(CurrentLT.equalsIgnoreCase("Leak Test Fail")){
			ModLT="Pass";
		}else {
			ModLT="Pass";
		}
		WF_A.UpdateLTStatus(ModLT);
    	WhatChanged[NumChanges]="Leak Test Result";
    	NumChanges++;			

		Description="e_LTStatDiff - change the Leak Test status to "+ModLT;				
	}
	public void e_MCStartSame(){
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentMCStartTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... TODO: need to make this smarter to account for different time zones and day light savings.
		ModMCStartDateTime=df2.format(CurrentMCStartDate)+" "+df.format(cal2.getTime());
		Description="e_SameMCStartTime - keep the MCedure Start Time ="+ModMCStartDateTime;		
		
	}
	public void e_MCStartNo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModMCStartDateTime=" ";
		WF_A.UpdateMCStart(ModMCStartDateTime);
		WF_A.EnterComment(Comment);
		if(!CurrentMCStartDate.equals("")){
	    	WhatChanged[NumChanges]="Manual Clean Start";
	    	NumChanges++;			
		}
		Description="e_MCStartNo - change the Manual Clean Start to "+ModMCStartDateTime;				
		ModMCStartDateTime="";

	}
	public void e_MCStartDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentMCStartTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... TODO: need to make this smarter to account for different time zones and day light savings.
		cal2.add(Calendar.MINUTE, -1); //change the time by subtracting one minute.
		ModMCStartDateTime=df2.format(CurrentMCStartDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateMCStart(ModMCStartDateTime);
		WF_A.EnterComment(Comment);
    	WhatChanged[NumChanges]="Manual Clean Start";
    	NumChanges++;			

		Description="e_MCStartDiff - change the Manual Clean Start to "+ModMCStartDateTime;				
		
	}
	public void e_MCEndSame(){
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentMCEndTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... TODO: need to make this smarter to account for different time zones and day light savings.
		ModMCCompleteDateTime=df2.format(CurrentMCEndDate)+" "+df.format(cal2.getTime());
		Description="e_MCEndSame - keep the Manual Clean Complete Time ="+ModMCCompleteDateTime;		
		
	}
	public void e_MCEndNo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModMCCompleteDateTime=" ";
		WF_A.UpdateMCEnd(ModMCCompleteDateTime);
		WF_A.EnterComment(Comment);
		if(!CurrentMCEndTime.equals("")){
	    	WhatChanged[NumChanges]="Manual Clean Complete";
	    	NumChanges++;			
		}
		Description="e_MCEndNo - Remove the Manual Clean Complete to "+ModMCCompleteDateTime;						
		ModMCCompleteDateTime="";

	}
	public void e_MCEndDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentMCEndTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... TODO: need to make this smarter to account for different time zones and day light savings.
		cal2.add(Calendar.MINUTE, +1); //change the time by adding one minute.
		ModMCCompleteDateTime=df2.format(CurrentMCEndDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateMCEnd(ModMCCompleteDateTime);
		WF_A.EnterComment(Comment);
    	WhatChanged[NumChanges]="Manual Clean Complete";
    	NumChanges++;			

		Description="e_MCEndDiff - change the Manual Clean Complete to "+ModMCCompleteDateTime;				
		
	}
	public void e_Comment() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//Create and enter text into the comment box. 
		  calint++;
		  calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
			  calint=0;
			  calchk="0";
			}
		Comment="Comment "+cal+calchk;
		WF_A.EnterComment(Comment);
		Description="e_Comment - comment= "+Comment;	
		extraRow=1;
	}
	public void e_CommentNo(){
		System.out.println(getCurrentElement().getName());
		Comment="";
		Description="e_NoComment - no comment entered.";					
		extraRow=0;
	}
	public void e_Cancel() throws AWTException, InterruptedException{
		System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		Changes="Yes";
		WF_A.Cancel(Changes);
		Description="e_Cancel.";
		Expected="e_Cancel - verify Soiled Area Cancel works.";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Passed - Successfully Cancel after making a change to the Soiled Area");

		
	}
	public void e_Save() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		incompleteDetailsFlag=WF_A.getStatusOfLocation(LocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description="e_Save.";					
		
	}
	public void e_SaveNoSA() throws InterruptedException{
		System.out.println(getCurrentElement().getName());	
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description="e_SaveNoSA.";						
	}
	
	public void e_SaveNoLTDate() throws InterruptedException{
		System.out.println(getCurrentElement().getName());	
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description="e_SaveNoLTDate.";						
	}
	public void e_NavAuditLog() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		IP_A.Click_AuditLog();
		Description="e_NavAuditLog - navigate to audit log page";					
		
	}
	public void e_SaveChangesNo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		WF_A.Save();
		Description="e_NoChangeSave - click save without making changes.";					
		
	}
	public void e_NavSRMError() throws AWTException, InterruptedException{
		System.out.println(getCurrentElement().getName());
		WF_A.Cancel(Changes);
		Description="e_NavSRMError - click cancel after verifying the error message.";					
		
	}
	public void e_NavSRM(){
		System.out.println(getCurrentElement().getName());
		
	}
	public void e_Nav1(){
		System.out.println(getCurrentElement().getName());
		//navigation edge
	}

	public void v_SRMSoiledArea(){
		Scenario++;
		//System.out.println(getCurrentElement().getName());
		Description ="Start of new Scenario "+Scenario;
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}
		System.out.println("=========================================\nScenario: "+Scenario+"\n"+getCurrentElement().getName());
	}
	public void v_Nav1(){
		System.out.println(getCurrentElement().getName());
		//navigation vertex
	}

	public void v_Nav2(){
		System.out.println(getCurrentElement().getName());
		//navigation vertex
	}
	
	public void v_Nav3(){
		System.out.println(getCurrentElement().getName());
		//navigation vertex
	}

	public void v_Nav4(){
		System.out.println(getCurrentElement().getName());
		//navigation vertex
	}
	
	public void v_SearchVerify(){
		System.out.println(getCurrentElement().getName());
		Result_ScopeModel=WF_V.Verify_ScopeModel(ScopeModel);
		Result_ScopeName=WF_V.Verify_ScopeName(ScopeName);
		Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(ScopeSerialNo);
		Result_RefNum=WF_V.Verify_RefNum(RefNo);
		String Result_Search="Result_ScopeModel="+Result_ScopeModel+" Result_ScopeName="+Result_ScopeName+" Result_ScopeSerialNo="+Result_ScopeSerialNo+" Result_RefNum="+Result_RefNum;
		Expected="v_SearchVerify - "+ScopeName+" with Serial Number="+ScopeSerialNo+" found and RefNo "+RefNo+" selected.";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Search);
		
	}
	public void v_SoiledArea(){
		System.out.println(getCurrentElement().getName());
		Expected="v_SoiledArea - verify Soiled Area Location is set to "+ModLocationName;
		Result_SoiledRoom=WF_V.Verify_SoiledArea(ModLocationName);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SoiledRoom);
		
	}
	public void v_LTDate(){
		System.out.println(getCurrentElement().getName());
		Expected="v_LTDate - verify Soiled Scope in Date/Time is set to "+ModSoiledDateTime;
		Result_SoiledDate=WF_V.Verify_LTMCDate(ModSoiledDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_SoiledDate);
		
	}
	public void v_LTStaff(){
		System.out.println(getCurrentElement().getName());
		Expected="v_LTStaff - verify Staff is set to "+ModStaffID;
		Result_Staff=WF_V.Verify_SoiledStaff(ModStaffID);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Staff);
		if(ModStaffID.equalsIgnoreCase("")&&!CurrentStaffID.equalsIgnoreCase("")){
			ModStaffID="-"; //update to - for verifying audit log.
		}
		if(CurrentStaffID.equalsIgnoreCase("")){
			CurrentStaffID="-";
		}
		
	}
	public void v_LTStatus(){
		System.out.println(getCurrentElement().getName());
		Expected="v_LTStatus - verify LT Status is set to "+ModLT;
		Result_LTStataus=WF_V.Verify_LT(ModLT);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_LTStataus);
		if(ModLT.equalsIgnoreCase("")&&!ModLT.equalsIgnoreCase("")){
			ModLT="-"; //update to - for varifying audit log.
		}
		//update the text for verifying the audit log 
		if(ModLT.equalsIgnoreCase("")){
			ModLT="-";
		}else if(ModLT.equalsIgnoreCase("Pass")){
			ModLT="Leak Test Pass";
		}else if(ModLT.equalsIgnoreCase("Fail")){
			ModLT="Leak Test Fail";
		}
		if(CurrentLT.equalsIgnoreCase("")){
			CurrentLT="-";
		}

	}
	public void v_MCStartDate(){
		System.out.println(getCurrentElement().getName());
		Expected="v_MCStartTime - verify Manual Clean Start is set to "+ModMCStartDateTime;
		Result_MCStart=WF_V.Verify_MCStart(ModMCStartDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_MCStart);
		if(ModMCStartDateTime.equalsIgnoreCase("")&&!CurrentMCStartDateTime.equalsIgnoreCase("")){
			ModMCStartDateTime="-";
		}
		if(CurrentMCStartDateTime.equalsIgnoreCase("")){
			CurrentMCStartDateTime="-";
		}
		
	}
	public void v_MCEndDate(){
		System.out.println(getCurrentElement().getName());
		Expected="v_MCEndTime - verify Manual Clean Complete is set to "+ModMCCompleteDateTime;
		Result_MCEnd=WF_V.Verify_MCEnd(ModMCCompleteDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_MCEnd);
		if(ModMCCompleteDateTime.equalsIgnoreCase("")&&!CurrentMCEndDateTime.equalsIgnoreCase("")){
			ModMCCompleteDateTime="-";
		}
		if(CurrentMCEndDateTime.equalsIgnoreCase("")){
			CurrentMCEndDateTime="-";
		}
		
	}
	public void v_Comment(){
		System.out.println(getCurrentElement().getName());
		Expected="v_Comment - verify the comment field is set to "+Comment;
		Result_Comment=WF_V.Verify_Comment(Comment);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Comment);
		if(Comment.equalsIgnoreCase("")){
			Comment="-";
		}
	}
	public void v_Save(){
		System.out.println(getCurrentElement().getName());
		if(ErrorMessage.equalsIgnoreCase("Yes")){
			Expected="v_Save - verify the error message when saving is set to Validation failed";
			Result_ErrorMsg=WF_V.Verify_ErrorMessage("Validation failed");
			IHV.Exec_Log_Result(FileName, Description, Expected, Result_ErrorMsg);
		}
		if(!(ModLocationName.equalsIgnoreCase("")||LTScanDate.equalsIgnoreCase(" "))){
			//verifying Chevron Color
			String expectedColor="";
			if(incompleteDetailsFlag){
				expectedColor=DBP.rgbOfIncompleteFlow;
			}else{
				expectedColor=DBP.rgbOfCompletedFlow;
			}
			Description="verifying Chevron Color";
			Expected="Chevron Color should be "+expectedColor;
		    Result_ChevronColor=WF_V.Verify_ChevronColor(ModLocationName, RefNo, expectedColor);
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
					case "Soiled Area":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
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
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, SoiledDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,LocationName);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModLocationName);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Soiled Area Scope Scan In":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
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
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, SoiledDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,SoiledDateTimeNoSec);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModSoiledDateTime);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Soiled Area Staff ID":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
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
					case "Leak Test Result":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
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
	
						if(LTScanDate.equalsIgnoreCase("")){
							LTScanDate=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, LTScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentLT);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModLT);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
	
					case "Manual Clean Start":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
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
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CurrentMCStartDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentMCStartDateTimeNoSec);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModMCStartDateTime);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Manual Clean Complete":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
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
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CurrentMCEndDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentMCEndDateTimeNoSec);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModMCCompleteDateTime);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					default:
	
						break;
	
					}
					Expected="v_AuditLog - verify the Audit Log for Ref No="+RefNo+" and WhatChanged="+WhatChanged[i];
					Result_AuditLog="Result_Audit_RefNo="+Result_Audit_RefNo+". Result_Audit_ReconDate="+Result_Audit_ReconDate+". Result_Audit_ScopeName"+Result_Audit_ScopeName
							+". Result_Audit_Comment="+Result_Audit_Comment+". Result_Audit_UserName="+Result_Audit_UserName+". Result_Audit_WhatChanged="+Result_Audit_WhatChanged
							+". Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate+". Result_Audit_Previous="+Result_Audit_Previous+". Result_Audit_Modified="+Result_Audit_Modified+".";
					IHV.Exec_Log_Result(FileName, Description, Expected, Result_AuditLog);				
				}
			}
		}
		
	}
	
}

