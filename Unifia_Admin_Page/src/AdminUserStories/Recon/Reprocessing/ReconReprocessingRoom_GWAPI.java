package AdminUserStories.Recon.Reprocessing;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.james.mime4j.field.datetime.DateTime;
import org.graphwalker.core.machine.ExecutionContext;

import java.awt.AWTException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;  
import java.util.Date;

import TestFrameWork.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminScannerPage.*; 
import Regression.MultiRoom.MultiRoomController_API;


public class ReconReprocessingRoom_GWAPI  extends ExecutionContext{
	
	private TestFrameWork.Emulator.Emulator_Actions EM_A;
	private TestFrameWork.Emulator.Emulator_Verifications EM_V;
	private TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	private static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Actions AL_A;
	private static TestFrameWork.UnifiaAdminReconAuditLog.ReconcilationAuditLog_Verification AL_V;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;

	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestFrameWork.TestHelper TH;
	private GeneralFunc gf;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private Regression.MultiRoom.MultiRoomController_API Multi;
	
	private static String user =Unifia_Admin_Selenium.user;
	private static String pass = Unifia_Admin_Selenium.pass;
	private static String connstring = Unifia_Admin_Selenium.connstring;
	private static String url = Unifia_Admin_Selenium.url;
	private Boolean MultiRoom=false;
	private Boolean Res;
	private int Scenario=0;
	private String ScanScopeSerNum;
	private int ScopeHangTime=0;
	private String Description;
	private String ForFileName;
	private String FileName="";
	private String Expected;

	private String TestResFileName="Recon_ReprocessingRoom_TestSummary_";	
	private String ScanScope="",Scanner="";
	private String WaitTimeinMins="";
	private String ScanStaff="";
	private String StaffFirstName ="", StaffLastName="", ScanStaffID="";
	private String ScopeStatus="";
	private String Reason="",MRCMsg="",ReasonName="";
	private Connection conn= null;
	private String stmt,stmt2;
	private ResultSet WaitingTime_RS,Staff_rs,Scanner_ID_rs,Scope_ID_rs,Reason_rs, OtherScopeState_rs,Repro_RS;
	private int ScopeID;
	private int BarcodeID;
	int ScopeCycleID;
	
	private String Scope_IH[];
	private String Staff_IH[];

	private String ScopeInIH;
	private String Scope1InAssociationID;
	private String Scope2InAssociationID;
	private String StaffInIH;
	private String StaffIn_Assoc1;
	private String StaffIn_Assoc2;
	private String ReasonIH;
	private String Reason_Assoc1;
	private String Reason_Assoc2;
	
	private String MRC_IH;
	private String MRCAssociationID;

	private String StaffMRCIH;
	private String StaffMRC_Assoc;
	
	
	private String ScopeOutIH;
	private String Scope1OutAssociationID;
	private String Scope2OutAssociationID;
	private int scopeOutAssociationID;
	private String StaffOutIH;
	private String StaffOut_Assoc1;
	private String StaffOut_Assoc2;
	
	private String ResultScopeInCycle;
	private String ResultScopeInLoc;
	private String ResultScopeInState;
	private String ResultReproIn;
	private String ResultStaffIn;
	private String ResultReason;

	private String Result_ScopeModel;
	private String Result_ScopeName;
	private String Result_ScopeSerialNo;
	private String Result_RefNum;
	private String Result_ErrorMsg;
	
	private String Result_InStaff,Result_ScopeInDateTime,Result_Reason,Result_OutDate,Result_OutStaff,Result_Comment;
	
	private String ResultScopeOutCycle;
	private String ResultScopeOutLoc;
	private String ResultScopeOutState;
	private String ResultReproOut;
	private String ResultStaffOut;
	private String Result_Reprocessor;
	
	private String CycleEvent;
	private String ExpectedCabinet;
	private String ActualScopeState;
	private String ExpectedState;
	private int OtherScopeStateID;
	private String ActualSubloc;
	private String ActualOtherScopeState;
	private String ActualCycleEvent;
	private String ScopeInLoc;
	private String ScopeOutLoc;
	
	private String ScopeName;
	private String ScopeSerialNo;
	private String ScopeModel;
	private int ScopePK;
	private int CurrentIHPK; // used to get the current records scan history primary key
	private String Comment; //the text entered into the comment box 
	private String RefNo; //the reference number of the record being modified.
	private int AssociationID; //association ID of the record being modified. 
	private int LocationID; //the location's primary key
	private String LocationName; //location name (i.e. Reprocessor 1) 
	private int LocationID_PK;
	private String ModLocationName;
	private String ReproDate="";
	private String ReproTime="";
	private String ReproDateTime="";
	private String ReproDateTimeNoSec="";
	private String currentScopeInStaffDatNoSec="";
	private String currentScopeOutStaffDatNoSec="";
	private Date CurrentReproDate=new Date(),ModReproDate=new Date(),CurrentReproTime=new Date(),ModReproTime=new Date();
	
	private Date currentScopeInStaffDate=new Date(), currentScopeOutStaffDate=new Date();
	private Date currentScopeInStaffTime=new Date(), currentScopeOutStaffTime=new Date();

	private Date modScopeInStaffDate=new Date(), modScopeOutStaffDate=new Date();
	private Date modScopeInStaffTime=new Date(), modScopeOutStaffTime=new Date();
		
	private String modScopeInStaffDateTime;
	private String Result_AuditLog;
	
	
	private int ModStaffPK; //the primary key of the updated staff for scanning the scope into Soiled Area
	private String ModStaffID;
	private String SoiledDate; //the date of the record being modified.
	private String SoiledTime; //the time of the record being modified.
	private String SoiledDateTime;
	private String SoiledDateTimeNoSec;
	private Date CurrentSoiledDate=new Date(),ModSoiledDate=new Date(),CurrentSoiledTime=new Date(),ModSoiledTime=new Date();
	
	private int CurrentStaffInPK;  //staff's primary key
	private String CurrentStaffInID;
	private String StaffInScanDate;
	private int CurrentStaffOutPK;
	private String CurrentStaffOutID;
	private String CurrentStaffOutScanDate;

	private String modStaffInID;
	private int modStaffInPK;
	private String modStaffInScanDate;
	private int modStaffOutPK;
	private String modStaffOutID;
	private String modStaffOutScanDate;
	private String currentStaffOutScanDate;
	private String currentStaffOutScanDateID;
	
	private String ErrorMessage;
	private int reproReasonID;
	private String reproReason;
	
	private String currentreasonForRep;
	private String modReasonForRep;
	private String scopeOutScanDate;
	
	private String stmt1;
	private SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
	private SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	
	private String[] WhatChanged=new String[10];
	private int NumChanges=0;
	
	private String Changes;
	private String Cancelled;
	private String  reasonScanTime=null;
	
	private int calint; //integer counter for cal
	private String calchk; //change calint to a string
	public long cal = Calendar.getInstance().getTimeInMillis(); 
	Calendar cal2 = Calendar.getInstance();
	
	private String ReconDateTime=null;
	private String GridID;
	private String Result_Audit_OriginalScanDate,Result_Audit_Previous,Result_Audit_Modified,Result_Audit_RefNo,Result_Audit_ReconDate;
	private String Result_Audit_ScopeName,Result_Audit_Comment,Result_Audit_UserName,Result_Audit_WhatChanged;
	private String Result_Audit_RowsCount;
	private int UTCTimeDiffInHours=0;
	private String Result_ChevronColor;
	private boolean incompleteDetailsFlag=false;
	private int extraRow=0;

	public void e_Start() throws InterruptedException, ParseException{
		System.out.println(getCurrentElement().getName());
		FileName="Modify_Workflow_Details_ReprocessingArea_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		Scenario=0;
		/*UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();
	   	System.out.println("UTCTimeDiffInHours = "+UTCTimeDiffInHours);*/
	}
	
	public void e_Search() throws SQLException, InterruptedException, ParseException{
		Changes="No";
		Cancelled="No";
		NumChanges=0;
		AssociationID=0;
		RefNo="";
		LocationName="";
		ReproDate="";
		ReproTime="";
		CurrentIHPK=0;
		ScopeName="";
		ScopeSerialNo="";
		ScopePK=0;
		CurrentReproDate=new Date();
		CurrentReproTime=new Date();
		ScopeModel="";
		ReproDateTime="";
		ReproDateTimeNoSec="";
		ErrorMessage="No";
		Comment="";
		reproReasonID=0;
		reproReason="";
		CurrentStaffInPK=0;
		CurrentStaffInID="";
		currentScopeInStaffTime=new Date();;
		currentScopeInStaffDate=new Date();
		StaffInScanDate="";
		scopeOutAssociationID=0;
		CurrentStaffOutPK=0;
		CurrentStaffOutID="";
		currentScopeOutStaffTime=new Date();
		currentScopeOutStaffDate=new Date();
		currentScopeInStaffDate=new Date();
		CurrentStaffOutScanDate="";
		ModLocationName="";
		modStaffInScanDate="";
		modScopeInStaffDateTime="";
		modStaffInID="";
		modReasonForRep="";
		modStaffOutScanDate="";
		modStaffOutID="";
		currentreasonForRep="";
		modReasonForRep="";
		currentStaffOutScanDate="";
		reasonScanTime="";
		reasonScanTime="";
		Result_AuditLog="";
		scopeOutScanDate="";
		incompleteDetailsFlag=false;
		UTCTimeDiffInHours=0;
		extraRow=0;
		
		UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();
	   	System.out.println("UTCTimeDiffInHours = "+UTCTimeDiffInHours);
	   	
		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
		conn= DriverManager.getConnection(url, user, pass);		
		//Statement update1 = conn.createStatement();
		Statement statement = conn.createStatement();
		
		stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK "
				+"join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK  join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
				+ "where IH.CycleEventID_FK=15 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=15)";
		System.out.println("stmt="+stmt);
		Repro_RS = statement.executeQuery(stmt);
		Timestamp receivedDataTime=null;
		while(Repro_RS.next()){
			receivedDataTime=Repro_RS.getTimestamp(1);
		}
		Repro_RS.close();	
		UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
		System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
		
		stmt="Select IH.AssociationID_FK, Concat(IH.ScanItemID_FK,'-', SC.CycleID) as RefNo,  Loc.Name, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
				+ " FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime, ItemHistoryID_PK,Scope.name as SName,Scope.SerialNumber,Scope.ScopeID_PK,"
				+ "IH.ReceivedDateTime,SM.Name, convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
				+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
				+ "join Location Loc on IH.LocationID_FK=Loc.LocationID_PK "
				+"join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK  join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
				+ "where IH.CycleEventID_FK=15 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=15)";
		System.out.println("stmt="+stmt);
		Repro_RS = statement.executeQuery(stmt);
		while(Repro_RS.next()){
			AssociationID = Repro_RS.getInt(1); 
			RefNo = Repro_RS.getString(2);
			LocationName=Repro_RS.getString(3);
			ReproDate=Repro_RS.getString(4);
			ReproTime=Repro_RS.getString(5);
			CurrentIHPK=Repro_RS.getInt(6);
			ScopeName=Repro_RS.getString(7);
			ScopeSerialNo=Repro_RS.getString(8);
			ScopePK=Repro_RS.getInt(9);
			CurrentReproDate=Repro_RS.getDate(10);
			CurrentReproTime=Repro_RS.getTime(10);
			ScopeModel=Repro_RS.getString(11);
			ReproDateTime=Repro_RS.getString(12);
			ReproDateTimeNoSec=Repro_RS.getString(13);
		}		
		Repro_RS.close();
		stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
		statement.executeUpdate(stmt1);
		//Reason for Reprocessing
		stmt="select IH.ScanItemID_FK, BC.Name,convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) as ReceievedDate from ItemHistory IH join barcode BC on ScanItemID_FK=barcodeid_PK where CycleEventID_FK=37 and AssociationID_FK="+AssociationID;
		System.out.println("stmt="+stmt);
		Repro_RS = statement.executeQuery(stmt);
		while(Repro_RS.next()){
			reproReasonID=Repro_RS.getInt(1); 
			currentreasonForRep=Repro_RS.getString(2);
			reasonScanTime=Repro_RS.getString(3);
		}		
		Repro_RS.close();
		// Scope ScanIn StaffID
		stmt="select IH.ScanItemID_FK, ST.StaffID,IH.ReceivedDateTime, "
				+ " convert(varchar,FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'MM/dd/yyyy hh:mm tt'),101)  as ScopInDate, "
				+ " convert(varchar,FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'MM/dd/yyyy hh:mm tt'),101) as ScopInTime "
				+ " from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where CycleEventID_FK=41 and AssociationID_FK="+AssociationID;
		System.out.println("stmt="+stmt);
		Repro_RS = statement.executeQuery(stmt);
		while(Repro_RS.next()){
			CurrentStaffInPK=Repro_RS.getInt(1); 
			CurrentStaffInID=Repro_RS.getString(2);
			currentScopeInStaffDate=Repro_RS.getDate(3);
			currentScopeInStaffTime=Repro_RS.getTime(3);
			StaffInScanDate=Repro_RS.getString(4);
			currentScopeInStaffDatNoSec=Repro_RS.getString(5);
		}		
		Repro_RS.close();
		String []scopeCyleID=RefNo.split("-");
		stmt="select associationid_fk from itemhistory where scanitemtypeid_fk=1 and ScanItemid_fk=(select scopeid_Pk from scope where name='"+ ScopeName+"' ) and "
		+ "locationid_fk= (select locationid_Pk from location where name = '"+LocationName+"' and cycleeventid_fk=18 and associationid_fk in "
		+ "(select associationid_fk from ScopeCycle where ScopeID_FK=(select scopeid_Pk from scope where name='"+ScopeName +"' ) and "
		+ "cycleid="+scopeCyleID[1]+"))";
		System.out.println("stmt="+stmt);
		Repro_RS = statement.executeQuery(stmt);
		while(Repro_RS.next()){
			scopeOutAssociationID = Repro_RS.getInt(1); 
		}	
		Repro_RS.close();
		// Scope ScanOut StaffID
		stmt="select IH.ScanItemID_FK, ST.StaffID,IH.ReceivedDateTime,"
			+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
			+ "convert(varchar, format(cast(dateadd(HOUR,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory "
			+"IH join Staff ST on ScanItemID_FK=StaffID_PK where CycleEventID_FK=42 and AssociationID_FK="+scopeOutAssociationID;
		System.out.println("stmt="+stmt);
		Repro_RS = statement.executeQuery(stmt);
		while(Repro_RS.next()){
			CurrentStaffOutPK=Repro_RS.getInt(1); 
			CurrentStaffOutID=Repro_RS.getString(2);
			
			currentScopeOutStaffTime=Repro_RS.getTime(3);
			currentScopeOutStaffDate=Repro_RS.getDate(3);
			
			scopeOutScanDate=Repro_RS.getString(4);
			currentScopeOutStaffDatNoSec=Repro_RS.getString(5);
		
		}		
		Repro_RS.close();
		// Scope ScanOut StaffID DateTime
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(ReproDate,ReproDate);
	    IP_A.ApplyFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(ReproDate,ReproDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);		
		Description="e_Search - Search for and select ScopeSerialNo="+ScopeSerialNo+" ReproDate="+ReproDate+" Ref#="+RefNo;
	}
	
	public void e_ReproSame() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//do nothing keep the Reprocessing area the same.
		ModLocationName=LocationName;
		Description="e_ReproSame - keep the Reprocessor same="+ModLocationName;
	}
	
	public void e_ReproDiff() throws InterruptedException, SQLException{
		System.out.println(getCurrentElement().getName());
		conn= DriverManager.getConnection(url, user, pass);		
		Statement statement = conn.createStatement();
		stmt="Select LocationID_PK, Name from Location where IsActive=1 and LocationTypeID_FK=5 and Name!='"+LocationName+"' "
		+ "and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Location where IsActive=1 and LocationTypeID_FK=5 "
		+ "and Name!='"+LocationName+"')";
		System.out.println("stmt="+stmt);
		Repro_RS = statement.executeQuery(stmt);
		
		while(Repro_RS.next()){
			LocationID_PK= Repro_RS.getInt(1); 
			ModLocationName = Repro_RS.getString(2);
		}		
		Repro_RS.close();
		stmt1="update Location set LastUpdatedDateTime=GETUTCDATE() where LocationID_PK="+LocationID_PK;
		statement.executeUpdate(stmt1);
		conn.close();
		WF_A.updateReprocessingArea(ModLocationName);
    	WhatChanged[NumChanges]="Reprocessor";
    	NumChanges++;
		Description="e_ReproDiff - Change the Reprocssing area from "+LocationName+" to "+ModLocationName;
	}
	
	public void e_NoRep() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		WF_A.updateReprocessingArea("");
    	Description="e_NoRep - remove the Reprocessing Area from "+LocationName;
		ErrorMessage="Yes";
	}
	
	public void e_InDTSame() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(currentScopeInStaffTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		modScopeInStaffDateTime=df2.format(currentScopeInStaffDate)+" "+df.format(cal2.getTime());
		Description="e_InDTSame - Keep the Scope Scan In Staff Date the same="+modScopeInStaffDateTime;
	}
	
	public void e_InDTDiff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(currentScopeInStaffTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, -2); //change the time by subtracting two minute.
		modScopeInStaffDateTime=df2.format(currentScopeInStaffDate)+" "+df.format(cal2.getTime());
		WF_A.updateScopeInStaffDate(modScopeInStaffDateTime);
    	WhatChanged[NumChanges]="Reprocessor Scope Scan In";
    	NumChanges++;			
		Description="e_InDTDiff - Change the Scope Scan in Date from "+StaffInScanDate+" to "+modScopeInStaffDateTime;
	}
	
	public void e_NoDate() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		modScopeInStaffDateTime=" ";
		WF_A.updateScopeInStaffDate(modScopeInStaffDateTime);
		ErrorMessage="Yes";
		Description="e_NoDate-Change the scope scan in staff Date to blank. ";
	}
	
	public void e_InStaffSame() throws InterruptedException{
		System.out.println(getCurrentElement().getName());		
		modStaffInID=CurrentStaffInID;
		Description="e_InStaffSame - Keep the Staff in same ="+modStaffInID;
	}
	
	public void e_InStaffNo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		modStaffInID="";
		Description="e_InStaffNo - make the Staff to blank ="+modStaffInID;
		WF_A.updateScopeInStaff(modStaffInID);
		
		if(!CurrentStaffInID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Scope Scan In Staff ID";
	    	NumChanges++;			
		}
		Description="e_InStaffNo - Updated the Scope in StaffID to blank="+modStaffInID;
	}
	
	public void e_InStaffDiff() throws InterruptedException, SQLException{
				
		stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and "
			+ "StaffID_PK!='"+CurrentStaffInPK+"' and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) "
			+ "from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!='"+CurrentStaffInPK+"')";
		
		System.out.println("stmt="+stmt);
		conn= DriverManager.getConnection(url, user, pass);	
		Statement statement=conn.createStatement();
		
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			modStaffInPK= RS.getInt(1); 
			modStaffInID = RS.getString(2);
		}		
		RS.close();
		stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+modStaffInPK;
		statement.executeUpdate(stmt1);
		conn.close();
		
		WF_A.updateScopeInStaff(modStaffInID);
	
		if(!CurrentStaffInID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Scope Scan In Staff ID";
	    	NumChanges++;			
		}
		Description="e_InStaffDiff-Change the Scope Scan in - Staff to  "+modStaffInID;
	}
	
	public void e_DiffStaffCancel() throws InterruptedException, AWTException, SQLException{
		stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and "
		+ "StaffID_PK!='"+CurrentStaffInPK+"' and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) "
		+ "from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!='"+CurrentStaffInPK+"')";

		System.out.println("stmt="+stmt);
		conn= DriverManager.getConnection(url, user, pass);	
		Statement statement=conn.createStatement();
		
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			modStaffInPK= RS.getInt(1); 
			modStaffInID = RS.getString(2);
		}		
		RS.close();
		stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+modStaffInPK;
		statement.executeUpdate(stmt1);
		conn.close();
		
		WF_A.updateScopeInStaff(modStaffInID);
		
		Description="e_DiffStaffCancel - change the scope scan in - staff to  "+modStaffInID;
	}
	
	public void e_ReasonSame() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		modReasonForRep=currentreasonForRep;
		Description="e_ReasonSame - Keep the reprocessing reason the same. "+modReasonForRep;
	}
	
	public void e_ReasonNo() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		modReasonForRep="";
		if(!currentreasonForRep.trim().equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Reprocessing Reason";
	    	NumChanges++;	
		}
		WF_A.updateReasonforReprocessing(modReasonForRep);
		Description="e_ReasonNo - Update the Reprocessing Reason to blank="+modReasonForRep;
	}
	
	public void e_ReasonDiff() throws InterruptedException, SQLException{
		//System.out.println(getCurrentElement().getName());
		stmt="select name from barcode where barcodeTypeID_fk=3 and barcodeid_pk!="+ reproReasonID +" and IsActive=1 "
				+"and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from barcode where barcodeTypeID_fk=3 and "
				+ "barcodeid_pk!="+ reproReasonID +")";
		System.out.println("stmt="+stmt);
		conn= DriverManager.getConnection(url, user, pass);	
		Statement statement=conn.createStatement();
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			//modBarcodeIDPK= RS.getInt(1); 
			modReasonForRep = RS.getString(1);
		}		
		RS.close();
		stmt1="update barcode set LastUpdatedDateTime=GETUTCDATE() where name= '"+modReasonForRep+"'";
		statement.executeUpdate(stmt1);
		conn.close();
		
		WF_A.updateReasonforReprocessing(modReasonForRep);
		WhatChanged[NumChanges]="Reprocessing Reason";
		NumChanges++;			
		Description="e_ReasonDiff- update the Reprocessing Reason to "+ modReasonForRep;
	}
	
	public void e_OutDTSame() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(currentScopeOutStaffTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		modStaffOutScanDate=df2.format(currentScopeOutStaffDate)+" "+df.format(cal2.getTime());
		Description="e_OutDTSame - Keep the Scope Scan Out Date the same="+modStaffOutScanDate;
	}
	
	public void e_OutDTNo() throws InterruptedException{
		modStaffOutScanDate=" ";
		//if(!currentStaffOutScanDate.equalsIgnoreCase("")){
		if(!currentScopeOutStaffDatNoSec.trim().equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Reprocessor Scope Scan Out";
	    	NumChanges++;			
		}
		WF_A.UpdateScopeOutStaffDate(modStaffOutScanDate);
		Description="e_OutDTNo - make the Scope Scan out date to blank = "+modStaffOutScanDate;
	}
	
	public void e_OutDTDiff() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		//method to change the date
		cal2.setTime(currentScopeOutStaffTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours);
		cal2.add(Calendar.MINUTE, -2); //change the time by subtracting two minute.
		modStaffOutScanDate=df2.format(currentScopeOutStaffDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateScopeOutStaffDate(modStaffOutScanDate);	
		WhatChanged[NumChanges]="Reprocessor Scope Scan Out";
    	NumChanges++;	
    	Description="e_OutDTDiff - Change the Scope Scan out date from "+currentStaffOutScanDate+currentScopeOutStaffTime+" to "+modStaffOutScanDate;
	}
	
	public void e_OutStaffSame() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		modStaffOutID=CurrentStaffOutID;
		Description="e_OutStaffSame - keep the Staff in same ="+modStaffOutID;
	}
	
	public void e_OutStaffNo() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		modStaffOutID="";
		WF_A.updateScopeOutStaff(modStaffOutID);
		
		if(!CurrentStaffInID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Scope Scan Out Staff ID";
	    	NumChanges++;			
		}
		Description="e_OutStaffNo - make the Scope out StaffID to ="+modStaffOutID;
	}
	
	public void e_OutStaffDiff() throws InterruptedException, SQLException{
		stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and "
			+ "StaffID_PK!='"+CurrentStaffOutPK+"' and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) "
			+ "from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!='"+CurrentStaffOutPK+"')";
		System.out.println("stmt="+stmt);
		conn= DriverManager.getConnection(url, user, pass);	
		Statement statement=conn.createStatement();
		
		ResultSet RS = statement.executeQuery(stmt);
		while(RS.next()){
			modStaffOutPK= RS.getInt(1); 
			modStaffOutID = RS.getString(2);
		}		
		RS.close();
		stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+modStaffOutPK;
		statement.executeUpdate(stmt1);
		conn.close();
		WF_A.updateScopeOutStaff(modStaffOutID);
		if(!CurrentStaffInID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Scope Scan Out Staff ID";
	    	NumChanges++;			
		}
		Description="Change the scope scan out - staff to  "+modStaffOutID;
	}
	
	public void e_Cancel() throws InterruptedException, AWTException{
		//System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		Changes="Yes";

		WF_A.Cancel(Changes);
		Description="e_Cancel.";
		Expected="e_Cancel - Verify Reprocessing Area Cancel works.";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Passed - Successfully Cancelled after making a change to the Reprocessing Room");
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
	
	public void e_CommentNo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Comment="";
		WF_A.EnterComment(Comment);
		Description="e_NoComment - no comment entered.";	
		extraRow=0;
	}
	
	public void e_Save() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		incompleteDetailsFlag=WF_A.getStatusOfLocation(LocationName); //Getting chevron flag value
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description="e_Save.";	
	}
	
	public void e_NavAuditLog() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		System.out.println(getCurrentElement().getName());
		IP_A.Click_AuditLog();
		Description="e_NavAuditLog - navigate to audit log page";	
	}
	
	public void e_NavSRMError() throws InterruptedException, AWTException{
		//click Cancel after verifying error message. 
		System.out.println(getCurrentElement().getName());		
		//WF_A.Cancel(Changes);
		Description="e_NavSRMError - click cancel after verifying the error message.";	
	}
	
	public void e_SaveChangesNo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		WF_A.Save();
		Description="e_NoChangeSave - click save without making changes.";	
	}
	
	public void e_SaveNoDate() throws InterruptedException{
		System.out.println(getCurrentElement().getName());	
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description="e_SaveNoDate.";	
	}
	
	public void e_SaveNoRep() throws InterruptedException{
		System.out.println(getCurrentElement().getName());	
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description="e_SaveNoRep.";
	}
	
	public void e_NavSRM() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
	}
	
	public void e_Nav1() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
	}
	
	public void v_SRMReprocessor(){
		//System.out.println(getCurrentElement().getName());
		Scenario++;
		System.out.println("Start of new Scenario "+Scenario);
		Description ="Start of new Scenario "+Scenario;
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}
	}
	public void v_SearchVerify() throws InterruptedException, SQLException{
		System.out.println(getCurrentElement().getName());
		Result_ScopeModel=WF_V.Verify_ScopeModel(ScopeModel);
		Result_ScopeName=WF_V.Verify_ScopeName(ScopeName);
		Result_ScopeSerialNo=WF_V.Verify_ScopeSerialNum(ScopeSerialNo);
		Result_RefNum=WF_V.Verify_RefNum(RefNo);
		String Result_Search="Result_ScopeModel="+Result_ScopeModel+" Result_ScopeName="+Result_ScopeName+" Result_ScopeSerialNo="+Result_ScopeSerialNo+" Result_RefNum="+Result_RefNum;
		Expected="v_SearchVerify - "+ScopeName+" with Serial Number="+ScopeSerialNo+" found and RefNo "+RefNo+" selected.";
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Search);
	}
	
	public void v_Reprocessor() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected="v_Reprocessor - verify Reprocessing Area is changed to "+ModLocationName;
		Result_Reprocessor=WF_V.Verify_Reprossor(ModLocationName);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Reprocessor);
	}
	
	public void v_InDateTime() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected="v_InDateTime - verify Scope Scan in Date/Time is set to "+modScopeInStaffDateTime;
		Result_ScopeInDateTime=WF_V.Verify_ReproScopeInTime(modScopeInStaffDateTime);
		//if(modStaffInScanDate.equalsIgnoreCase("")&&!StaffInScanDate.equalsIgnoreCase("")){
		if(modScopeInStaffDateTime.equalsIgnoreCase("")&&!currentScopeInStaffDatNoSec.equalsIgnoreCase("")){
			modScopeInStaffDateTime="-"; //update to - for verifying audit log.
		}
		if(currentScopeInStaffDatNoSec.equalsIgnoreCase("")){
			currentScopeInStaffDatNoSec="-";
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ScopeInDateTime);
	
	}
	
	public void v_InStaff() throws InterruptedException, SQLException{
		System.out.println(getCurrentElement().getName());
		Result_InStaff=WF_V.Verify_ReproInStaff(modStaffInID);
		Expected="v_InStaff - verify Scope Scan in Staff is set to "+modStaffInID;
		if(modStaffInID.equalsIgnoreCase("")&&!CurrentStaffInID.equalsIgnoreCase("")){
			modStaffInID="-"; //update to - for verifying audit log.
		}
		if(CurrentStaffInID.equalsIgnoreCase("")){
			CurrentStaffInID="-";
		}
		
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_InStaff);
	}
	
	public void v_Reason() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected="v_Reason - verify Reprocessing Reason is set to "+modReasonForRep;
		Result_Reason=WF_V.Verify_ReproReason(modReasonForRep);
		if(modReasonForRep.trim().equalsIgnoreCase("")&&!currentreasonForRep.trim().equalsIgnoreCase("")){
			modReasonForRep="-"; //update to - for verifying audit log.
		}
		if (currentreasonForRep.trim().equalsIgnoreCase("")){
			currentreasonForRep="-"; 
		}
		
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_InStaff);
	}
	
	public void v_OutDateTime() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		System.out.println ("modStaffOutScanDate"+modStaffOutScanDate);
		System.out.println ("currentScopeOutStaffDatNoSec"+currentScopeOutStaffDatNoSec);
		Expected="v_OutDateTime - verify Scope Scan out Date is set to "+modStaffOutScanDate;
		Result_OutDate=WF_V.Verify_ReproScopeOutTime(modStaffOutScanDate);
		if(modStaffOutScanDate.trim().equalsIgnoreCase("")&&!currentScopeOutStaffDatNoSec.trim().equalsIgnoreCase("")){
			modStaffOutScanDate="-"; //update to - for verifying audit log.
		}
		//if(currentStaffOutScanDate.equalsIgnoreCase("")){
		if(currentScopeOutStaffDatNoSec.trim().equalsIgnoreCase("")){
			System.out.println ("in if2");
			currentScopeOutStaffDatNoSec="-";
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_OutDate);
	}
	
	public void v_OutStaff() throws InterruptedException, SQLException{
		System.out.println(getCurrentElement().getName());
		Expected="v_OutStaff - verify Scope Scan out Staff is set to "+modStaffOutID;
		Result_OutStaff=WF_V.Verify_ReproOutStaff(modStaffOutID);
		if(modStaffOutID.equalsIgnoreCase("")&&!CurrentStaffOutID.equalsIgnoreCase("")){
			modStaffOutID="-"; //update to - for verifying audit log.
		}
		if(CurrentStaffOutID.equalsIgnoreCase("")){
			CurrentStaffOutID="-";
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_OutStaff);
	}
	
	public void v_Comment() throws InterruptedException, SQLException{
		System.out.println(getCurrentElement().getName());
		Expected="v_Comment - verify the comment field is set to "+Comment;
		Result_Comment=WF_V.Verify_Comment(Comment);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Comment);
		if(Comment.equalsIgnoreCase("")){
			Comment="-";
		}
	}
	
	public void v_Save() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(ErrorMessage.equalsIgnoreCase("Yes")){
			Expected="v_Save - verify the error message when saving is set to Validation failed";
			Result_ErrorMsg=WF_V.Verify_ErrorMessage("Validation failed");
			IHV.Exec_Log_Result(FileName, Description, Expected, Result_ErrorMsg);
		}else{
			IHV.Exec_Log_Result(FileName, Description, "Changes are saved", "Changes are saved");
		}
		
		if(!(ModLocationName.equalsIgnoreCase("")||modScopeInStaffDateTime.equalsIgnoreCase(" ")||Cancelled.equalsIgnoreCase("Yes"))){
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
					//AL_A.ClearAuditLogSrchCritera();
					AL_A.Search_RefNo(RefNo);
					AL_A.Search_ScopeName(ScopeName);
					if (!Comment.equalsIgnoreCase("")){
						AL_A.Search_Comments(Comment);
					}
					
					AL_A.Search_Location(ModLocationName);
					AL_A.Search_WhatChanged(WhatChanged[i]);
					
					GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo, WhatChanged[i]);
					Result_Audit_RefNo=AL_V.Verify_RefNum(GridID,RefNo);
					System.out.println("Result_Audit_RefNo="+Result_Audit_RefNo);
					Result_Audit_ReconDate=AL_V.Verify_ReconDate(GridID, ReconDateTime);
					System.out.println("Result_Audit_ReconDate="+Result_Audit_ReconDate);
					Result_Audit_ScopeName=AL_V.Verify_ScopeName(GridID,ScopeName);
					System.out.println("Result_Audit_ScopeName="+Result_Audit_ScopeName);
					Result_Audit_Comment=AL_V.Verify_Comment(GridID, Comment);
					System.out.println("Result_Audit_Comment="+Result_Audit_Comment);
					Result_Audit_UserName=AL_V.Verify_Username(GridID, Unifia_Admin_Selenium.userQV1);
					System.out.println("Result_Audit_UserName="+Result_Audit_UserName);
					Result_Audit_WhatChanged=AL_V.Verify_WhatChanged(GridID, WhatChanged[i]);
					System.out.println("Result_Audit_WhatChanged="+Result_Audit_WhatChanged);
					System.out.println("WhatChanged[i]="+WhatChanged[i]);
					
					switch (WhatChanged[i]) {
						case "Reprocessor":
							if (ReproDateTime.equalsIgnoreCase("")){
								ReproDateTime=ReconDateTime;
							}
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ReproDateTime);
							System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
							Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,LocationName);
							System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
							Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModLocationName);
							System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
							break;
						case "Reprocessing Reason":
							if (reasonScanTime.equalsIgnoreCase("")){
								reasonScanTime=ReconDateTime;
							}
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, reasonScanTime);
							System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
							Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,currentreasonForRep);
							System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
							Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, modReasonForRep);
							System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
							break;
						case "Reprocessor Scope Scan In":
							if (StaffInScanDate.equalsIgnoreCase("")){
								StaffInScanDate=ReconDateTime;
							}
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, StaffInScanDate);
							System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
							
							Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,currentScopeInStaffDatNoSec);
							System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
							Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, modScopeInStaffDateTime);
							System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
							break;
						case "Scope Scan In Staff ID":
							if (StaffInScanDate.equalsIgnoreCase("")){
								StaffInScanDate=ReconDateTime;
							}
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, StaffInScanDate);
							System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
							Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentStaffInID);
							System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
							Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, modStaffInID);
							System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
							break;
						case "Reprocessor Scope Scan Out":
							if (scopeOutScanDate.equalsIgnoreCase("")){
								scopeOutScanDate=ReconDateTime;
							}
							System.out.println("scopeOutScanDate="+scopeOutScanDate);
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, scopeOutScanDate);
							System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
							//Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,currentScopeOutStaffDatNoSec);
							Result_Audit_Previous=AL_V.verify_PreviousValueDate(GridID,currentScopeOutStaffDatNoSec);
							System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
							Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, modStaffOutScanDate);
							System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
							break;
						case "Scope Scan Out Staff ID":
							if (scopeOutScanDate.equalsIgnoreCase("")){
								scopeOutScanDate=ReconDateTime;
							}
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, scopeOutScanDate);
							System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
							Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentStaffOutID);
							System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
							Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, modStaffOutID);
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
	
	public void v_Nav1(){
		System.out.println(getCurrentElement().getName());
		//navigation vertex
	}
	
	public void v_NavCancel(){
		System.out.println(getCurrentElement().getName());
		//navigation vertex
	}

	public void v_NavNoRep(){
		System.out.println(getCurrentElement().getName());
		//navigation vertex
	}
	
	public void v_NavNoDate(){
		System.out.println(getCurrentElement().getName());
		//navigation vertex
	}
}
