package AdminUserStories.Recon.ProcedureRoom;

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

public class ProcedureRoom_GWAPI extends ExecutionContext{
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

	private int Scenario=1;
	public String FileName="";

	public static String actualResult="\t\t\t Recon_ProcedureRoom_TestSummary \r\n"; 
	public String Description;  //the description written to the test step log or printed to the console
	public String Expected; //The expected result written to the test step log
	public String GridID; //the GridID of the row being modified in Unifia
	
	public int CurrentPatientPK;  //patient's primary key
	public String CurrentPatientID;
	public int AssociationID; //association ID of the record being modified. 
	public int LocationID; //the location's primary key
	public String LocationName; //location name (i.e. Procedure Room 1) 
	public int LocationID_PK;
	public String ModLocationName;
	public int CurrentInStaffPK;
	public int CurrentPreCleanStaffPK;
	public int CurrentPhyPK;
	public String CurrentInStaffID; //the Staff ID of the updated staff for scanning the scope into the procedure room. 
	public String CurrentPreCleanStaff; //the Staff ID of the updated staff for scanning preclean complete
	public String CurrentPhyID; //the Staff ID of the updated physician
	public String CurrentPreCleanStatus;
	public String ModPreCleanStatus;
	public Date CurrentProcStartDate=new Date(),CurrentProcStartTime=new Date();
	public Date ModProcStartDate=new Date(),ModProcStartTime=new Date();
	public Date CurrentProcEndDate=new Date(),CurrentProcEndTime=new Date();
	public Date ModProcEnd=new Date();
	public String CurrentProcStartDateTime, CurrentProcEndDateTime,CurrentProcStartDateTimeNoSec,CurrentProcEndDateTimeNoSec;
	public String ModProcStartDateTime, ModProcEndDateTime;
	
	public int ModInStaffPK; //the primary key of the updated staff for scanning the scope into the procedure room. 
	public int ModPreCleanStaffPK; //the primary key of the updated staff for scanning preclean complete  
	public int ModPhyPK; //the primary key of the updated physician.
	public String ModInStaffID; //the Staff ID of the updated staff for scanning the scope into the procedure room. 
	public String PreCleanStaffChanged;
	public String ModPreCleanStaff; //the Staff ID of the updated staff for scanning preclean complete
	public String ModPhyID; //the Staff ID of the updated physician
	public int ModPatientPK; //the primary key of the updated patient 
	public String ModPatientID; //the Patient ID of the updated patient
	public String Comment; //the text entered into the comment box 
	public String Date; //the date of the record being modified.
	public String Time; //the time of the record being modified.
	public String RefNo; //the reference number of the record being modified.
	public String ScopeName;
	public String ScopeSerialNo;
	public String ScopeModel;
	public int ScopePK;
	public int CurrentIHPK; // used to get the current records scan history primary key
	public int RelatedItem;
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	public long cal = Calendar.getInstance().getTimeInMillis(); 
	public String ErrorMessage;

	public Connection conn= null;
    public String stmt;
    public String stmt1;
	public ResultSet PR_RS;  //result set used to get the PR record to be modified.
	public ResultSet Patient_RS;  //result set used to get the patient to be modified.
	public ResultSet PatientNew_RS;

	public String ExamDate; //the date of the record being modified.
	public String ExamTime; //the time of the record being modified.
	public String ExamDateTime;
	public String ExamDateTimeNoSec;
	public Date CurrentExamDate=new Date(),ModExamDate=new Date(),CurrentExamTime=new Date(),ModExamTime=new Date();
	public String StaffInScanDate,PatientScanDate,PhyScanDate,PreCleanScanDate,PrecleanStaffScanDate;
	public SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
	public SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	public String ModExamDateTime="";
	private String Result_Audit_RowsCount;
	
	Calendar cal2 = Calendar.getInstance();
	
	public String Result_ScopeModel;
	public String Result_ScopeName;
	public String Result_ScopeSerialNo;
	public String Result_RefNum;
	public String Result_ErrorMsg;
	public String Result_ProcRoom;
	public String Result_ExamDate;
	public String Result_StaffIn;
	public String Result_Patient;
	public String Result_Phy;
	public String Result_ProcStart;
	public String Result_ProcEnd;
	public String Result_PrecleanStatus;
	public String Result_PrecleanStaff;
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
	public String Changes="No";
	
	public int UTCTimeDiffInHours=0;
	private String Result_ChevronColor;
	private boolean incompleteDetailsFlag=false;
	
	private boolean isMultiPhy;
	
	private int CurrentPhyPK2;
	private String CurrentPhyID2;
	private String PhyScanDate2;
	private int extraRow;
	

	public void e_Start() throws ParseException{
		System.out.println(getCurrentElement().getName());
		FileName="Modify_Workflow_Details_ProcedureRoom_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		Scenario=0;
	   	UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs();
	   	System.out.println("UTCTimeDiffInHours = "+UTCTimeDiffInHours);
	}
	
	public void e_Search() throws InterruptedException, ParseException{
		System.out.println(getCurrentElement().getName());
		Changes="No";
		CurrentProcStartDate=new Date();
		CurrentProcStartTime=new Date();
		CurrentProcEndDate=new Date();
		CurrentProcEndTime=new Date();
		Cancelled="No";
		NumChanges=0;
		GridID="";
		StaffInScanDate="";
		PatientScanDate="";
		PhyScanDate="";
		PreCleanScanDate="";
		ReconDateTime="";
		PreCleanStaffChanged="No";
		ModExamDateTime="";
		CurrentProcStartDateTime="";
		CurrentProcEndDateTime="";
		CurrentProcStartDateTimeNoSec="";
		CurrentProcEndDateTimeNoSec="";
		ModProcStartDateTime="";
		ModProcEndDateTime="";
		ModPhyID="";
		ModInStaffID="";
		ModPreCleanStaff="";
		ModPatientID="";
		ModPreCleanStatus="";
	   	RelatedItem=0;
	   	AssociationID=0;
	   	RefNo="";
	   	LocationName="";
	   	ExamDate="";
	   	ExamTime="";
	   	CurrentIHPK=0;
	   	ScopeName="";
		ScopeSerialNo="";
		ScopePK=0;
		CurrentExamDate=new Date();
		CurrentExamTime=new Date();
		ScopeModel="";
		ExamDateTime="";
		ExamDateTimeNoSec="";
		CurrentInStaffPK=0; 
		CurrentInStaffID="";
		StaffInScanDate="";
		CurrentPhyPK=0; 
		CurrentPhyID="";
		PhyScanDate="";
		CurrentPatientPK = 0; 
		CurrentPatientID = "";
		PatientScanDate="";
	   	PreCleanScanDate="";
	   	CurrentPreCleanStatus="";
	   	CurrentPreCleanStaffPK=0; 
		CurrentPreCleanStaff="";
		PrecleanStaffScanDate="";
		ErrorMessage="No";
		incompleteDetailsFlag=false;
		extraRow=0;
		
		for(int i=0;i<10;i++){
			WhatChanged[i]="";
		}
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select IH.ReceivedDateTime from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK join ScopeCycle SC on "
					+ "IH.AssociationID_FK=SC.AssociationID_FK  join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
					+ "where IH.CycleEventID_FK=3 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=3)";
			System.out.println("stmt="+stmt);
			PR_RS = statement.executeQuery(stmt);
			Timestamp receivedDataTime=null;
			while(PR_RS.next()){
				receivedDataTime=PR_RS.getTimestamp(1);
			}
			PR_RS.close();	
			UTCTimeDiffInHours=GF.getUTCTimeDiffInHrs(receivedDataTime);
			System.out.println("UTC hours difference for "+receivedDataTime+" is "+UTCTimeDiffInHours);
			
			stmt="Select IH.AssociationID_FK, Concat(IH.ScanItemID_FK,'-', SC.CycleID) as RefNo, Loc.Name, convert(varchar(10), format(cast((IH.ReceivedDateTime) as date), 'MM/dd/yyyy'), 101) as SADate,"
					+ " FORMAT(CAST(dateadd(HH,-"+UTCTimeDiffInHours+", (IH.ReceivedDateTime)) AS DATETIME),'hh:mm tt') as SATime, ItemHistoryID_PK,Scope.name as SName,Scope.SerialNumber,Scope.ScopeID_PK,"
					+ "IH.ReceivedDateTime,SM.Name, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Location Loc on IH.LocationID_FK=Loc.LocationID_PK "
					+ "join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK  join Scope on IH.ScanItemID_FK=Scope.ScopeID_PK join ScopeType SM on Scope.ScopeTypeID_FK=SM.ScopeTypeID_PK "
					+ "where IH.CycleEventID_FK=3 and IH.LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from ItemHistory where CycleEventID_FK=3)";
			System.out.println("stmt="+stmt);
    		
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				AssociationID = PR_RS.getInt(1); 
				RefNo = PR_RS.getString(2);
				LocationName=PR_RS.getString(3);
				ExamDate=PR_RS.getString(4);
				ExamTime=PR_RS.getString(5);
				CurrentIHPK=PR_RS.getInt(6);
				ScopeName=PR_RS.getString(7);
				ScopeSerialNo=PR_RS.getString(8);
				ScopePK=PR_RS.getInt(9);
				CurrentExamDate=PR_RS.getDate(10);
				CurrentExamTime=PR_RS.getTime(10);
				ScopeModel=PR_RS.getString(11);
				ExamDateTime=PR_RS.getString(12);
				ExamDateTimeNoSec=PR_RS.getString(13);
			}		
			PR_RS.close();
			stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+CurrentIHPK; 	// we will update the LastUpdatedDateTime after
			statement.executeUpdate(stmt1);
			
			//Checking whether Another Scope is used for the same ProcedureRoom if so updating its LastUpdatedTime
			stmt="select ItemHistoryID_PK from ItemHistory where ScanItemTypeID_FK=1 and CycleEventID_FK=3 and ItemHistoryID_PK!="+CurrentIHPK+" and "
					+ "AssociationID_FK="+AssociationID+" and LocationID_FK=(select LocationID_PK from Location where Name='"+LocationName+"')";
			System.out.println("stmt="+stmt);
    		PR_RS = statement.executeQuery(stmt);
    		
    		String OtherScopesItemHistoryID_PK="";
			while(PR_RS.next()){
				OtherScopesItemHistoryID_PK+=PR_RS.getString(1)+";";
			}
			PR_RS.close();
			System.out.println("OtherScopesItemHistoryID_PK"+OtherScopesItemHistoryID_PK);
			if(!OtherScopesItemHistoryID_PK.equalsIgnoreCase("")){
				String ItemHistoryIDs[]=OtherScopesItemHistoryID_PK.split(";");
				for(int i=0; i<ItemHistoryIDs.length;i++){
					stmt1="update ItemHistory set LastUpdatedDateTime=GETUTCDATE() where ItemHistoryID_PK="+OtherScopesItemHistoryID_PK; 	// we will update the LastUpdatedDateTime after
					statement.executeUpdate(stmt1);
				}
			}
			
		   	stmt="select RelatedItemHistoryID_FK from RelatedItem RI  where ItemHistoryID_FK="+CurrentIHPK;
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				RelatedItem = PR_RS.getInt(1); 
			}		
			PR_RS.close();
			
			stmt="select IH.ScanItemID_FK, ST.StaffID, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+", IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where ItemHistoryID_PK="+RelatedItem;
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				CurrentInStaffPK=PR_RS.getInt(1); 
				CurrentInStaffID=PR_RS.getString(2);
				StaffInScanDate=PR_RS.getString(3);
			}		
			PR_RS.close();
			
			//for handling multiple physicians
			stmt="select IH.ScanItemID_FK, ST.StaffID,convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where AssociationID_FK="+AssociationID+" and CycleEventID_FK=5";

			//stmt="select IH.ScanItemID_FK, ST.StaffID,convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where AssociationID_FK="+AssociationID+" and CycleEventID_FK=5 and ReceivedDateTime=(select MAX(ReceivedDateTime) from ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=5)";
    		PR_RS = statement.executeQuery(stmt);
    		Integer cntr=1;
    		isMultiPhy=false;
			while(PR_RS.next()){
				if (cntr==1){
					CurrentPhyPK=PR_RS.getInt(1); 
					CurrentPhyID=PR_RS.getString(2);
					PhyScanDate=PR_RS.getString(3);
				}else if (cntr==2){
					isMultiPhy=true;
					CurrentPhyPK2=PR_RS.getInt(1); 
					CurrentPhyID2=PR_RS.getString(2);
					PhyScanDate2=PR_RS.getString(3);
				}
				cntr++;
			}		
			PR_RS.close();
			
			stmt="select ReceivedDateTime, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
					+ "where AssociationID_FK="+AssociationID+" and CycleEventID_FK=6";
			PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				CurrentProcStartDate=PR_RS.getDate(1);
				CurrentProcStartTime=PR_RS.getTime(1);
				CurrentProcStartDateTime=PR_RS.getString(2);
				CurrentProcStartDateTimeNoSec=PR_RS.getString(3);
			}		
			PR_RS.close();

			stmt="select ReceivedDateTime, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101),"
					+ "convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH "
					+ "where AssociationID_FK="+AssociationID+" and CycleEventID_FK=7";

			PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				CurrentProcEndDate=PR_RS.getDate(1);
				CurrentProcEndTime=PR_RS.getTime(1);
				CurrentProcEndDateTime=PR_RS.getString(2);
				CurrentProcEndDateTimeNoSec=PR_RS.getString(3);
			}		
			PR_RS.close();
			stmt="Select PatientID_PK, PatientID, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Patient on IH.ScanItemID_FK=Patient.PatientID_PK where AssociationID_FK="+AssociationID+" and IH.CycleEventID_FK=4";

			System.out.println("stmt="+stmt);
			Patient_RS = statement.executeQuery(stmt);
			while(Patient_RS.next()){
				CurrentPatientPK = Patient_RS.getInt(1); 
				CurrentPatientID = Patient_RS.getString(2);
				PatientScanDate=Patient_RS.getString(3);

			}
	
			Patient_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
		   	if (CurrentPatientID.equalsIgnoreCase("")){
		   		CurrentPatientID="";
			}
		   	else{
		   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
				while(PatientNew_RS.next()){
					CurrentPatientPK = PatientNew_RS.getInt(1); 
					//LastUpdatedDate= PatientNew_RS.getString(2);
			   		CurrentPatientID = PatientNew_RS.getString(3);
				}
			   	PatientNew_RS.close();
		   	}
		   	
		   	stmt="select ItemHistoryID_PK, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory where CycleEventID_FK=8 and ScanItemID_FK="+ScopePK+" and AssociationID_FK="+AssociationID;
		   	CurrentIHPK=0;
		   	RelatedItem=0;
			System.out.println("stmt="+stmt);

    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				CurrentIHPK =PR_RS.getInt(1); 
				PreCleanScanDate=PR_RS.getString(2);
			}		
			PR_RS.close();
			
			if(CurrentIHPK!=0){
				CurrentPreCleanStatus="Yes";

				stmt="select RelatedItemHistoryID_FK from RelatedItem RI  where ItemHistoryID_FK="+CurrentIHPK;
	    		PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					RelatedItem = PR_RS.getInt(1); 
				}		
				PR_RS.close();
				
				stmt="select IH.ScanItemID_FK, ST.StaffID, convert(varchar, format(cast(dateadd(HH,-"+UTCTimeDiffInHours+",IH.ReceivedDateTime) AS DATETIME), 'MM/dd/yyyy hh:mm tt'), 101) from ItemHistory IH join Staff ST on ScanItemID_FK=StaffID_PK where ItemHistoryID_PK="+RelatedItem;
	    		PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					CurrentPreCleanStaffPK=PR_RS.getInt(1); 
					CurrentPreCleanStaff=PR_RS.getString(2);
					PrecleanStaffScanDate=PR_RS.getString(3);
				}		
				PR_RS.close();

			}else {
				CurrentPreCleanStatus="No";
				CurrentPreCleanStaffPK=0;
				CurrentPreCleanStaff="";
				PrecleanStaffScanDate="";
			}


			conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
	    System.out.println("AssociationID="+AssociationID);
	    System.out.println("RefNo="+RefNo);
	    System.out.println("LocationName"+LocationName);
	    System.out.println("Date="+ExamDate);
	    System.out.println("Time="+ExamTime);
	    
	    Description="Search for Scope Scan In="+ExamDate+" Time="+ExamTime+" Scope Serial Number="+ScopeSerialNo+" RefNo= "+RefNo+" at Location="+LocationName;
	    System.out.println("Description="+Description);
	    
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(ExamDate,ExamDate);
	    IP_A.ApplyFilter();
	    IP_A.ClearFilter();
	    IP_A.ScopeFilter(ScopeSerialNo);
	    IP_A.DateFilter(ExamDate,ExamDate);
	    IP_A.ApplyFilter();
	    IP_A.Click_Details(RefNo);				
		
	}
	
	public void e_SamePR(){
		System.out.println(getCurrentElement().getName());
		//do nothing keep the Procedure Room the same.
		ModLocationName=LocationName;
		Description="e_SamePR - keep the Procedure Room="+ModLocationName;
		
	}
	public void e_DiffPR() throws InterruptedException{
		System.out.println(getCurrentElement().getName());

    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select LocationID_PK, Name from Location where IsActive=1 and LocationTypeID_FK=2 and Name!='"+LocationName+"' and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Location where IsActive=1 and LocationTypeID_FK=2 and Name!='"+LocationName+"')";
			// we will update the LastUpdatedDateTime after 
			// making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				LocationID_PK= PR_RS.getInt(1); 
				ModLocationName = PR_RS.getString(2);

			}		
			PR_RS.close();
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

    	WF_A.UpdateProcedureRoom(ModLocationName);
    	WhatChanged[NumChanges]="Procedure Room";
    	NumChanges++;
		Description="e_DiffPR - change the Procedure Room from "+LocationName+" to "+ModLocationName;
	
	}
	public void e_NoPR() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
    	ModLocationName="";

		WF_A.UpdateProcedureRoom(ModLocationName);
		Description="e_NoPR - change the Procedure Room from "+LocationName+" to "+ModLocationName;
		ErrorMessage="Yes";

	}

	public void e_SameED(){
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentExamTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time....
		ModExamDateTime=df2.format(CurrentExamDate)+" "+df.format(cal2.getTime());
		Description="e_SameED - keep the Scope Scan In ="+ModExamDateTime;
		
	}
	public void e_DiffED() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentExamTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time....
		cal2.add(Calendar.MINUTE, -2);
		ModExamDateTime=df2.format(CurrentExamDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateExamDate(ModExamDateTime);
    	WhatChanged[NumChanges]="Procedure Room Scope Scan In";
    	NumChanges++;

		Description="e_DiffED - change the Scope Scan In to "+ModExamDateTime;
		
	}
	public void e_NoED() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModExamDateTime=" ";
		WF_A.UpdateExamDate(ModExamDateTime);
		Description="e_NoED - change the Scope Scan In to "+ModExamDateTime;
		ErrorMessage="Yes";

	}
	public void e_SameStaff(){
		System.out.println(getCurrentElement().getName());
		//do nothing... keep the staff in the same. 
		ModInStaffID=CurrentInStaffID;
		Description="e_SameStaff - keep the Staff in ="+ModInStaffID;
	}
	public void e_DiffStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentInStaffPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentInStaffPK+")";
			// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				ModInStaffPK= PR_RS.getInt(1); 
				ModInStaffID = PR_RS.getString(2);

			}		
			PR_RS.close();
			stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModInStaffPK;
			statement.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

    	WF_A.UpdatePRInStaff(ModInStaffID);
    	WhatChanged[NumChanges]="Procedure Room Staff ID";
    	NumChanges++;

		Description="e_DiffStaff - change the PR Staff In to "+ModInStaffID;
		
	}

	public void e_DiffStaffCancel() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentInStaffPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentInStaffPK+")";
			// we will update the LastUpdatedDateTime after 
			// making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				ModInStaffPK= PR_RS.getInt(1); 
				ModInStaffID = PR_RS.getString(2);

			}		
			PR_RS.close();
			stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModInStaffPK;
			statement.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

    	WF_A.UpdatePRInStaff(ModInStaffID);

		Description="e_DiffStaff - change the PR Staff In to "+ModInStaffID;
		
	}

	public void e_NoStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
    	ModInStaffID="";
    	WF_A.UpdatePRInStaff(ModInStaffID);
		if(!CurrentInStaffID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Procedure Room Staff ID";
	    	NumChanges++;			
		}
		Description="e_NoStaff - change the PR Staff In to "+ModInStaffID;

	}
	
	public void e_SamePatientID(){
		System.out.println(getCurrentElement().getName());
		ModPatientID=CurrentPatientID;		
		Description="e_SamePatientID - keep the Patient ID ="+ModPatientID;

	}
	
	public void e_NoPatientID() throws InterruptedException{
		System.out.println(getCurrentElement().getName());		
		ModPatientID=" ";
		WF_A.UpdatePatient(ModPatientID);
		if(!CurrentPatientID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Patient ID";
	    	NumChanges++;		
	    	//PatientBug10188="Yes";
			Description="e_NoPatientID - change the Patient ID to "+ModPatientID;		
		}else {
			Description="e_NoPatientID - no change,the patient ID field was already blank.";		
		}
	}
	
	public void e_DiffPatientID() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
			Statement statement = conn.createStatement();
			Statement statement2 = conn.createStatement();

			if (CurrentPatientID.equalsIgnoreCase(""))
			{
				CurrentPatientID = "-";
				//strPatientQuery = "";
				stmt="select PatientID_PK, PatientID from Patient where	LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Patient)";
				System.out.println("stmt="+stmt);
	
			}else{
				stmt="select PatientID_PK, PatientID from Patient where	PatientID_PK !="+CurrentPatientPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Patient where PatientID_PK !="+CurrentPatientPK+")";
				System.out.println("stmt="+stmt);
	
				//[17/Mar/2016]- Start
				  //Fetch the decrypted patient id 
				String stmtPatEncr="dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
		   		if (CurrentPatientID!=""){
			   		//PatientNew_RS=statement2.executeQuery(stmtPatEncr);
			   		PatientNew_RS=statement2.executeQuery(stmtPatEncr);
			   		System.out.println("stmt="+stmtPatEncr);
			   		while(PatientNew_RS.next()){
			   			//CurrentPatientPK = PatientNew_RS.getInt(1); 
						//LastUpdatedDate= Patient_RS.getString(2);
			   			CurrentPatientID = PatientNew_RS.getString(3);
					}
			   			PatientNew_RS.close();
				}
			}

			Patient_RS.close();
			Patient_RS= statement.executeQuery(stmt);
		 	while(Patient_RS.next()){
		 		ModPatientPK=Patient_RS.getInt(1);
		 		ModPatientID=Patient_RS.getString(2);
		 	}
			Patient_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ ModPatientPK+"'";
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
	   		while(PatientNew_RS.next()){
	   			ModPatientPK = PatientNew_RS.getInt(1); 
				//LastUpdatedDate= PatientNew_RS.getString(2);
	   			ModPatientID = PatientNew_RS.getString(3);
			}
	   		PatientNew_RS.close();		

			Statement update = conn.createStatement();
			stmt1="update Patient set LastUpdatedDateTime=GETUTCDATE() where PatientID_PK="+ModPatientPK;
			update.executeUpdate(stmt1);
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		WF_A.UpdatePatient(ModPatientID);
    	WhatChanged[NumChanges]="Patient ID";
    	NumChanges++;			

		Description="e_DiffPatientID - change the Patient ID to "+ModPatientID;		
		
	}
	public void e_NewPatientID() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		ModPatientID="MRN"+cal+calchk;
		WF_A.UpdatePatient(ModPatientID);
    	WhatChanged[NumChanges]="Patient ID";
    	NumChanges++;			

		Description="e_NewPatientID - change the Patient ID to "+ModPatientID;		
		
	}
	public void e_SamePhysician(){
		System.out.println(getCurrentElement().getName());
		if (isMultiPhy){
			CurrentPhyID=CurrentPhyID2+","+CurrentPhyID;
		}
		ModPhyID=CurrentPhyID;		
		Description="e_SamePhysician - keep the Physician ID ="+ModPhyID;

	}
	public void e_DiffPhysician() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if (isMultiPhy){
			CurrentPhyID=CurrentPhyID2+","+CurrentPhyID;
			
		}
    	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		//Statement update1 = conn.createStatement();
			Statement statement = conn.createStatement();
			
			if (!isMultiPhy){
				stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=1 and StaffID_PK != "+CurrentPhyPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=1 and StaffID_PK != "+CurrentPhyPK+")";
			}else{
				stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=1 and StaffID_PK not in ("+CurrentPhyPK+","+CurrentPhyPK2+") and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=1 and StaffID_PK not in ("+CurrentPhyPK+","+CurrentPhyPK2+"))";
			}
			
			// we will update the LastUpdatedDateTime after 
			// making changes to the record so that next time it does not show up
			System.out.println("stmt="+stmt);
    		
    		PR_RS = statement.executeQuery(stmt);
			while(PR_RS.next()){
				ModPhyPK= PR_RS.getInt(1); 
				ModPhyID = PR_RS.getString(2);

			}		
			PR_RS.close();
			stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModPhyPK;
			statement.executeUpdate(stmt1);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
		WF_A.UpdatePhysician(ModPhyID);
    	WhatChanged[NumChanges]="Physician ID";
    	NumChanges++;			

		Description="e_DiffPhysician - change the Physician ID to "+ModPhyID;		
		
	}
	public void e_NoPhysician() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModPhyID="";
		WF_A.UpdatePhysician(ModPhyID);
		if(!CurrentPhyID.equalsIgnoreCase("")){
	    	WhatChanged[NumChanges]="Physician ID";
	    	NumChanges++;			
	    	//PhyBug10188="Yes";
			Description="e_NoPhysician - change the Physician ID to "+ModPhyID;				
		}else {
			Description="e_NoPhysician - no change, the Physician ID was already blank ";				

		}

	}
	public void e_SameProcStartTime(){
		System.out.println(getCurrentElement().getName());
		
		cal2.setTime(CurrentProcStartTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... 
		ModProcStartDateTime=df2.format(CurrentProcStartDate)+" "+df.format(cal2.getTime());
		Description="e_SameProcStartTime - keep the Procedure Start Time ="+ModProcStartDateTime;		
		
	}
	public void e_DiffProcStartTime() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentProcStartTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... 
		cal2.add(Calendar.MINUTE, -1); //change the time by subtracting one minute.
		ModProcStartDateTime=df2.format(CurrentProcStartDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateProcStart(ModProcStartDateTime);
    	WhatChanged[NumChanges]="Procedure Start";
    	NumChanges++;			

		Description="e_DiffProcStartTime - change the Procedure Start to "+ModProcStartDateTime;				

	}
	public void e_NoProcStartTime() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModProcStartDateTime=" ";
		WF_A.UpdateProcStart(ModProcStartDateTime);
		if(!CurrentProcStartDate.equals("")){
	    	WhatChanged[NumChanges]="Procedure Start";
	    	NumChanges++;			
		}
		Description="e_NoProcStartTime - change the Procedure Start to "+ModProcStartDateTime;				

	}
	public void e_SameProcEndTime(){
		System.out.println(getCurrentElement().getName());		
		cal2.setTime(CurrentProcEndTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... 
		ModProcEndDateTime=df2.format(CurrentProcEndDate)+" "+df.format(cal2.getTime());
		Description="e_SameProcEndTime - keep the Procedure End Time ="+ModProcEndDateTime;		

	}
	public void e_DiffProcEndTime() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		cal2.setTime(CurrentProcEndTime);
		cal2.add(Calendar.HOUR, -UTCTimeDiffInHours); //adjusting for time difference between UTC and local time.... 
		cal2.add(Calendar.MINUTE, +1); //change the time by adding one minute.
		ModProcEndDateTime=df2.format(CurrentProcEndDate)+" "+df.format(cal2.getTime());
		WF_A.UpdateProcEnd(ModProcEndDateTime);
    	WhatChanged[NumChanges]="Procedure End";
    	NumChanges++;			

		Description="e_DiffProcEndTime - change the Procedure End to "+ModProcEndDateTime;				
		
	}
	public void e_NoProcEndTime() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		ModProcEndDateTime=" ";
		WF_A.UpdateProcEnd(ModProcEndDateTime);
		if(!CurrentProcEndTime.equals("")){
	    	WhatChanged[NumChanges]="Procedure End";
	    	NumChanges++;			
		}
		Description="e_NoProcEndTime - change the Procedure End to "+ModProcEndDateTime;						
		
	}

	public void e_SamePreClean(){
		System.out.println(getCurrentElement().getName());
		ModPreCleanStatus=CurrentPreCleanStatus;
		Description="e_SamePreClean - keep the Preclean status ="+ModPreCleanStatus;		
		
	}
	public void e_DiffPreClean() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		
		if(CurrentPreCleanStatus.equalsIgnoreCase("No")){
			ModPreCleanStatus="Yes";
		}else if(CurrentPreCleanStatus.equalsIgnoreCase("Yes")){
			ModPreCleanStatus="No";
			ModPreCleanStaff="";
			PreCleanStaffChanged="Yes";// possible bug 10099
			if(!CurrentPreCleanStaff.equalsIgnoreCase("")){
		    	WhatChanged[NumChanges]="Pre-Clean Staff ID";
		    	NumChanges++;			

			}
		}		
		WF_A.UpdatePreClean(ModPreCleanStatus);
    	WhatChanged[NumChanges]="Pre-Clean Complete";
    	NumChanges++;			

		Description="e_DiffPreClean - change the Preclean status to "+ModPreCleanStatus;				
		
	}
	public void e_SamePreCleanStaff(){
		System.out.println(getCurrentElement().getName());
		ModPreCleanStaff=CurrentPreCleanStaff;
		Description="e_SamePreCleanStaff - keep the Preclean staff ="+ModPreCleanStaff;		
		
	}
	public void e_DiffPreCleanStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(PreCleanStaffChanged.equalsIgnoreCase("No")){
	    	try{
	    		conn= DriverManager.getConnection(url, user, pass);		
	    		//Statement update1 = conn.createStatement();
				Statement statement = conn.createStatement();
				stmt="Select StaffID_PK, StaffID from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentPreCleanStaffPK+" and LastUpdatedDateTime=(Select min(LastUpdatedDateTime) from Staff where IsActive=1 and StaffTypeID_FK=5 and StaffID_PK!="+CurrentPreCleanStaffPK+")";
				// we will update the LastUpdatedDateTime after making changes to the record so that next time it does not show up
				System.out.println("stmt="+stmt);
	    		
	    		PR_RS = statement.executeQuery(stmt);
				while(PR_RS.next()){
					ModPreCleanStaffPK= PR_RS.getInt(1); 
					ModPreCleanStaff = PR_RS.getString(2);

				}		
				PR_RS.close();
				stmt1="update Staff set LastUpdatedDateTime=GETUTCDATE() where StaffID_PK="+ModPreCleanStaffPK;
				statement.executeUpdate(stmt1);
	    		conn.close();
	    	}
	    	catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
			WF_A.UpdatePreCleanStaff(ModPreCleanStaff);
	    	WhatChanged[NumChanges]="Pre-Clean Staff ID";
	    	NumChanges++;			

			Description="e_DiffPreCleanStaff - change the Preclean staff to "+ModPreCleanStaff;						
		} else {
			Description="e_DiffPreCleanStaff - Preclean staff was removed when changing the Preclean Status from Yes to No; therefore do noth change the preclean staff again";						

		}
		
		
	}
	public void e_NoPreCleanStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ModPreCleanStaff="";
		WF_A.UpdatePreCleanStaff(ModPreCleanStaff);
		if(PreCleanStaffChanged.equalsIgnoreCase("No")){
			if(!CurrentPreCleanStaff.equalsIgnoreCase("")){
		    	WhatChanged[NumChanges]="Pre-Clean Staff ID";
		    	NumChanges++;			
				Description="e_NoPreCleanStaff - change the Preclean staff to "+ModPreCleanStaff;					
			}else {
				Description="e_NoPreCleanStaff - no change, preclean staff was already blank ";					
			}
		}else {
			Description="e_NoPreCleanStaff - preclean staff was changed to blank when changing the Preclean status from Yes to No.";	
		}

	}
	public void e_SkipPreCleanStaff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());

		Description="e_SkipPreCleanStaff - Skipping any changes to Preclean as the Status was changed.";	


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
	public void e_NoComment(){
		System.out.println(getCurrentElement().getName());
		Comment="";
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
	public void e_SaveNoPR() throws InterruptedException{
		System.out.println(getCurrentElement().getName());	
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description="e_Save.";						
	}
	
	public void e_SaveNoED() throws InterruptedException{
		System.out.println(getCurrentElement().getName());	
		ReconDateTime=GF.ServerDateTime(url, user, pass);
		WF_A.Save();
		Description="e_Save.";						
	}

	public void e_Cancel() throws AWTException, InterruptedException{
		System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		Changes="Yes";
		WF_A.Cancel(Changes);
		Description="e_Cancel.";
		Expected="e_Cancel - verify Soiled Area Cancel works.";
		IHV.Exec_Log_Result(FileName, Description, Expected, "Passed - Successfully Cancel after making a change to the Procedure Room");
		
	}
	public void e_NavAuditLog() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		IP_A.Click_AuditLog();
		Description="e_NavAuditLog - navigate to audit log page";					
		
	}
	public void e_NavSRMError() throws AWTException, InterruptedException{
		//click Cancel after verifying error message. 
		System.out.println(getCurrentElement().getName());		
		WF_A.Cancel(Changes);
		Description="e_NavSRMError - click cancel after verifying the error message.";					
	
	}
	public void e_NavSRM(){
		System.out.println(getCurrentElement().getName());
		
	}
	public void e_NoChangeSave() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Cancelled="Yes";
		WF_A.Save();
		Description="e_NoChangeSave - click save without making changes.";					
		
	}
	
	public void e_Nav1(){
		System.out.println(getCurrentElement().getName());
		//navigation edge
	}
	
	public void v_SRMProcRoom(){
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
	public void v_ProcRoom(){
		System.out.println(getCurrentElement().getName());
		Expected="v_ProcRoom - verify Procedure Room Location is set to "+ModLocationName;
		Result_ProcRoom=WF_V.Verify_PR(ModLocationName);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ProcRoom);
		
	}
	public void v_ExamDate(){
		System.out.println(getCurrentElement().getName());
		Expected="v_ExamDate - verify Scope Scan In is set to "+ModExamDateTime;
		Result_ExamDate=WF_V.Verify_ExamDate(ModExamDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ExamDate);
				
	}
	public void v_Staff(){
		System.out.println(getCurrentElement().getName());
		Expected="v_Staff - verify Staff in is set to "+ModInStaffID;
		Result_StaffIn=WF_V.Verify_PRInStaff(ModInStaffID);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_StaffIn);
		if(ModInStaffID.equalsIgnoreCase("")&&!CurrentInStaffID.equalsIgnoreCase("")){
			ModInStaffID="-"; //update to - for varifying audit log.
		}
		if(CurrentInStaffID.equalsIgnoreCase("")){
			CurrentInStaffID="-";
		}
				
	}
	public void v_Patient(){
		System.out.println(getCurrentElement().getName());		
		Expected="v_Patient - verify Patient is set to "+ModPatientID;
		Result_Patient=WF_V.Verify_Patient(ModPatientID);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Patient);
		if(ModPatientID.equalsIgnoreCase("")&&!CurrentPatientID.equalsIgnoreCase("")){
			ModPatientID="-"; //update to - for varifying audit log.
		}
		if(CurrentPatientID.equalsIgnoreCase("")){
			CurrentPatientID="-";
		}

	}
	public void v_Physician(){
		System.out.println(getCurrentElement().getName());
		Expected="v_Physician - verify Physician is set to "+ModPhyID;
		Result_Phy=WF_V.Verify_Physician(ModPhyID);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_Phy);
		if(ModPhyID.equalsIgnoreCase("")&&!CurrentPhyID.equalsIgnoreCase("")){
			ModPhyID="-"; //update to - for varifying audit log.
		}
		if(CurrentPhyID.equalsIgnoreCase("")){
			CurrentPhyID="-";
		}

	}
	public void v_ProcStartTime(){
		System.out.println(getCurrentElement().getName());
		Expected="v_ProcStartTime - verify Procedure Start is set to "+ModProcStartDateTime;
		Result_ProcStart=WF_V.Verify_ProcStart(ModProcStartDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ProcStart);
		if(ModProcStartDateTime.equalsIgnoreCase(" ")&&!CurrentProcStartDateTime.equalsIgnoreCase("")){
			ModProcStartDateTime="-";
		}
		if(CurrentProcStartDateTime.equalsIgnoreCase("")){
			CurrentProcStartDateTime="-";
		}
		
	}
	public void v_ProcEndTime(){
		System.out.println(getCurrentElement().getName());
		Expected="v_ProcEndTime - verify Procedure End is set to "+ModProcEndDateTime;
		Result_ProcEnd=WF_V.Verify_ProcEnd(ModProcEndDateTime);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_ProcEnd);
		if(ModProcEndDateTime.equalsIgnoreCase(" ")&&!CurrentProcEndDateTime.equalsIgnoreCase("")){
			ModProcEndDateTime="-";
		}
		if(CurrentProcEndDateTime.equalsIgnoreCase("")){
			CurrentProcEndDateTime="-";
		}

	}
	public void v_PreClean(){
		System.out.println(getCurrentElement().getName());
		Expected="v_PreClean - verify Preclean status is set to "+ModPreCleanStatus;
		Result_PrecleanStatus=WF_V.Verify_PreClean(ModPreCleanStatus);
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_PrecleanStatus);
		
	}
	public void v_PreCleanStaff(){
		System.out.println(getCurrentElement().getName());
		Expected="v_PreCleanStaff - verify Preclean staff is set to "+ModPreCleanStaff;
		Result_PrecleanStaff=WF_V.Verify_PreCleanStaff(ModPreCleanStaff);
		if(Result_PrecleanStaff.contains("#Failed!#")&&PreCleanStaffChanged.equalsIgnoreCase("Yes")){
			Result_PrecleanStaff=Result_PrecleanStaff+". Bug 10099 opened - preclean staff is not always cleared in the UI when the user changes Preclean status=yes to no.";
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, Result_PrecleanStaff);
		if(ModPreCleanStaff.equalsIgnoreCase("")&&!CurrentPreCleanStaff.equalsIgnoreCase("")){
			ModPreCleanStaff="-"; //update to - for varifying audit log.
		}
		if(CurrentPreCleanStaff.equalsIgnoreCase("")){
			CurrentPreCleanStaff="-";
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
		if(!(ModLocationName.equalsIgnoreCase("")||ModExamDateTime.equalsIgnoreCase(" ")||Cancelled.equalsIgnoreCase("Yes"))){
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
				Result_Audit_RowsCount="Pass - Num of changes done in SRM  matched with num of rows in audit log";
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
			
			if (rowComp==0||NumChanges<rowComp){ 			// do validations on audit log if row count matches only
				for(int i=0;i<NumChanges;i++){
					Thread.sleep(2000);
					switch (WhatChanged[i]) {
					case "Procedure Room":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						Thread.sleep(2000);
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ExamDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,LocationName);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModLocationName);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Procedure Room Scope Scan In":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						Thread.sleep(2000);
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, ExamDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,ExamDateTimeNoSec);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModExamDateTime);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Procedure Room Staff ID":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						
						Thread.sleep(2000);
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
						
						if(StaffInScanDate.equalsIgnoreCase("")){
							StaffInScanDate=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, StaffInScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentInStaffID);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModInStaffID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Patient ID":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
	
						Thread.sleep(2000);
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
	
						if(PatientScanDate.equalsIgnoreCase("")){
							PatientScanDate=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PatientScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPatientID);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPatientID);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
	
					case "Physician ID":
							AL_A.Search_RefNo(RefNo);
							AL_A.Search_ScopeName(ScopeName);
							AL_A.Search_Comments(Comment);
							AL_A.Search_Location(ModLocationName);
							AL_A.Search_WhatChanged(WhatChanged[i]);
							System.out.println("WhatChanged[i]="+WhatChanged[i]);
		
							Thread.sleep(2000);
							GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
		
							if(PhyScanDate.equalsIgnoreCase("")){
								PhyScanDate=ReconDateTime;
							}
							
							Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PhyScanDate);
							
							if (Result_Audit_OriginalScanDate.contains("#Failed!#")){
								Result_Audit_OriginalScanDate=Result_Audit_OriginalScanDate+". Bug 11950 opened - Audit log entry when changing the physician gives the Scan Date/Time field is set to the exam date/time instead of the orignal physician scan/date time";
							}
							
							System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
							Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPhyID);
							System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
							Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPhyID);
							System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
	
						break;
					case "Procedure Start":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						Thread.sleep(2000);
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CurrentProcStartDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentProcStartDateTimeNoSec);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModProcStartDateTime);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Procedure End":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						Thread.sleep(2000);
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
	
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, CurrentProcEndDateTime);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentProcEndDateTimeNoSec);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModProcEndDateTime);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Pre-Clean Complete":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						Thread.sleep(2000);
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
	
						if(PreCleanScanDate.equalsIgnoreCase("")){
							PreCleanScanDate=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PreCleanScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPreCleanStatus);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPreCleanStatus);
						System.out.println("Result_Audit_Modified="+Result_Audit_Modified);
						break;
					case "Pre-Clean Staff ID":
						AL_A.Search_RefNo(RefNo);
						AL_A.Search_ScopeName(ScopeName);
						AL_A.Search_Comments(Comment);
						AL_A.Search_Location(ModLocationName);
						AL_A.Search_WhatChanged(WhatChanged[i]);
						System.out.println("WhatChanged[i]="+WhatChanged[i]);
						Thread.sleep(2000);
						GridID=AL_A.GetGridID_AuditLog_ByRefNo(RefNo,WhatChanged[i]);
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
	
						if(PrecleanStaffScanDate.equalsIgnoreCase("")){
							PrecleanStaffScanDate=ReconDateTime;
						}
						Result_Audit_OriginalScanDate=AL_V.Verify_ScanDateTime(GridID, PrecleanStaffScanDate);
						System.out.println("Result_Audit_OriginalScanDate="+Result_Audit_OriginalScanDate);
						Result_Audit_Previous=AL_V.Verify_PreviousValue(GridID,CurrentPreCleanStaff);
						System.out.println("Result_Audit_Previous="+Result_Audit_Previous);
						Result_Audit_Modified=AL_V.Verify_ModifiedValue(GridID, ModPreCleanStaff);
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
