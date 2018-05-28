package ScannerMessages.ExamQueue;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.graphwalker.core.machine.ExecutionContext;

import TestFrameWork.Unifia_Admin_Selenium;

public class RegressionExamQueue_ProcRoom_GWAPI extends ExecutionContext{
	
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions WF_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Verification WF_V;
	public static TestFrameWork.Emulator.GetIHValues IHV;
	public String FileName;
	public String Patient="";
	public String Exam="";
	public String ScanExam="";
	public String ScanExam_Abbr="";
	public int ExamTypeID_PK;
	public String PatientID="";
	public int CurrentPatientPK;
	public int LocationID_PK;
	public Boolean Res;
	public String WRScanner="",Facility="",PRScanner="";
	public Integer Scenario=0;
	private String ScanScope;
	private String Scope_IH[];
	private Integer ScopeID;
	private String ScopeInIH;
	private String ScanScopeSerNum;
	private String ScanStaff="", ScanStaffID="";
	private String StaffID="";
	private String StaffFirstName="";
	private String StaffLastName="";
	
	public Connection conn= null;
	public ResultSet Patient_RS;  //result set used to get patientid from the Test DB
	public ResultSet PatientNew_RS;
	public ResultSet Exam_Name_rs, PR_RS;
	public ResultSet Scanner_ID_rs,phys_rs,Scope_ID_rs,Staff_rs;
	public String stmt; //used for SQL queries
	public static String actualResult="\t\t\t Regression_ExamQueue_ProcRoom_TestSummary \n"; 
	public String TestResFileName="Regression_ExamQueue_ProcRoom_TestSummary_";
	public String Description;
	public String ForFileName;
	public String Expected, ExpectedCycleEvent,PatientInIH,ScopeInPRAssocID,ActualCycleEvent,ResultScopeInCycle;
	public String ResultPRIn, PatientScanned="",ResultScopeAsso;
	public String Exam_IH[], ExamInIH,Phy_IH[], ScopeInAssociationIDExam;
	public String ExamScanned="",PatientInAssociationID,ExamInAssociationIDExam,EQInAssociationID;
	public String ResultPatientInCycle,ResultEQIn,ResultExamInSameAssoc,ResultSendToExamQAsso,ResultPatientInSameAssoc;
	private String waitPhy="",waitPhyIDPK, waitPhyID,firstName,lastName,phyStaffIDPKMulti,waitMultiPhy="",waitMultiPhyID;
	private String prPhy="",prPhyIDPK, prPhyID,PhysList="", prMultiPhy="", prMultiPhyID,PhyIDList="";
	private String PhyInAssociationIDPhy,ResultPatientInSameAssocWithPhysician,ResultExamInSameAssocWithPhysician,ResultPhysicianInSameAssoc,PhyInIH,FirstPhyInPRAssocIDPhy,FirstPhyInWRAssocIDPhy;
	
	private String Patient_IH[];
	private String patInPR_IHID;
	private String Patient_Assoc;
	private String ResultPatient;
	private String ResultPatientCycle;
	
	private String Physician_IH;
	private String Physician_Assoc;
	private String ResultPhysician;
	private String ResultPhysicianCycle;

	
	private String ExpectedCabinet;
	private String ActualScopeState;
	private String ExpectedState;
	private int OtherScopeStateID;
	private String ActualSubloc;
	private String ActualOtherScopeState;
	private String ScopeInLoc;
	private String ScopeOutLoc;
	
	private String ResultRmState;
	private String ExpectedRmSt;
	
	private String ExpectedReproCount;
	private String ExpectedExamCount;

	private String ResultReproCount;
	private String ResultExamCount;
	private String ResultLastStaff;
	private int StaffPK=0;
	private int LastScanStaffID_FK;
	private String ActualReproCount;
	private String ActualExamCount;
	
	private String scopeRefNo="";
	private String scopeSerialNo="";
	private String scopeModel="";
	private String scopeExamTime="";
	
	private String Staff_IH[];
	private String StaffInIH;	
	private String StaffIn_Assoc;
	private String ResultScopeInLoc;
	private String ResultScopeInState;
	private String ResultStaffIn;
	private String StaffIn_RI;
	private String ResultIn_RI;
	private String ResultCultureResult;
	private String ResultCulture_RI;

	private String ResultScopeOutCycle;
	private String ResultScopeOutLoc;
	private String ResultScopeOutState;
	private String ResultPROut;
	private String ResultStaffOut;
	private String StaffOut_RI;
	private String ResultOut_RI;
	public String ResultScopeIn1StaffCycle="";
	public String ResultScopeOut1StaffCycle="";
	
	public String Timeout="";
	private String CancelProcedure[];
	Map<String, String> Queue= new HashMap<String, String>();
	
	
	public void e_start() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date); 
		FileName="CycleMgmtExamQueue_ProcRoom_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.ScannerCount=0;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		
		// set all PR rooms to available:
		String Room="Available";
		String Scanner="Procedure Room 1";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 2";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 3";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 5";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 6";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		Scanner="Procedure Room 7";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 8";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="Procedure Room 9";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ Scanner;
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		Scanner="";
	}
	public void e_ScanPatient() throws InterruptedException{
		Patient="Scanned";
		System.out.println(getCurrentElement().getName());
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
			Statement statement = conn.createStatement();
			stmt="Select PatientID_PK, PatientID from Patient where LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Patient)";
			System.out.println("stmt="+stmt);
			Patient_RS = statement.executeQuery(stmt);
			while(Patient_RS.next()){
				CurrentPatientPK = Patient_RS.getInt(1); 
				PatientID = Patient_RS.getString(2);
			}
			Patient_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			System.out.println("stmtPatEncr="+stmtPatEncr);
	   		
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
		   	while(PatientNew_RS.next()){
		   		CurrentPatientPK = PatientNew_RS.getInt(1); 
				PatientID = PatientNew_RS.getString(3);
			}
		   	PatientNew_RS.close();
	   		Statement update = conn.createStatement();
			update.execute("Update Patient set LastUpdatedDateTime=GETUTCDATE() WHERE PatientID_PK='"+CurrentPatientPK+"';");
			update.close();
			statement.close();
			conn.close();
		}
		catch (SQLException ex){
			// handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		
   		if (WRScanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					WRScanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+WRScanner);
				update.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
				update.close();
				statement.close();
				conn.close();
	   		}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
   		}
   		
   		//Scan the Patient
		Res = EM_A.ScanItem(WRScanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Patient '" +PatientID+"' is done First";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
	}
	public void e_ScanExam() throws InterruptedException{
		Exam="Scanned";
		System.out.println(getCurrentElement().getName());
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="Select Name, Abbreviation, ExamTypeID_PK from ExamType where IsShipped=1 and IsActive=1 and ExamTypeID_PK!=0 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ExamType where IsShipped=1 and IsActive=1 and ExamTypeID_PK!=0)";
			System.out.println(stmt);
			Exam_Name_rs = statement.executeQuery(stmt);
			while(Exam_Name_rs.next()){
				ScanExam= Exam_Name_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanExam_Abbr= Exam_Name_rs.getString(2); 
				ExamTypeID_PK=Exam_Name_rs.getInt(3);
			}
			Exam_Name_rs.close();
			update.executeUpdate("Update ExamType Set LastUpdatedDateTime=GETUTCDATE() WHERE ExamTypeID_PK='"+ExamTypeID_PK+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("Scanned Exam = "+ScanExam);
		if (WRScanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					WRScanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+WRScanner);
				update.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
				update.close();
				statement.close();
				conn.close();
	   			}
				catch (SQLException ex){
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());	
				}

   		}
		//Scan the Exam
		Res = EM_A.ScanItem(WRScanner, "Exam Type", "", ScanExam, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Exam '"+ScanExam+"' is done First";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_ScanWaitPhysician() throws InterruptedException{
		waitPhy="Scanned";
		System.out.println(getCurrentElement().getName());
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1)";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				waitPhyIDPK=phys_rs.getString(1);
				firstName= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastName =phys_rs.getString(3); 
				waitPhyID=phys_rs.getString(4);
			}
			phys_rs.close();
			update.executeUpdate("Update staff Set LastUpdatedDateTime=GETUTCDATE() WHERE staffid_pk='"+waitPhyIDPK+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("Scanned Physician = "+waitPhyID);
		PhysList+=waitPhyID;
		PhyIDList+=waitPhyIDPK;
		//Scan the Physician
		Res = EM_A.ScanItem(WRScanner, "Staff", "Physician", firstName +" "+ lastName+"("+waitPhyID+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Physicin '"+waitPhyID+"' is done after exam or patient scan is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_NoPhySendToQueue() throws InterruptedException{
		waitPhy="NotScanned";
		System.out.println(getCurrentElement().getName());
		Res = EM_A.ScanItem(WRScanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Description = "Scan of Send To ExamQueue barcode is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_WaitMultiPhysician() throws InterruptedException{
		waitPhy="Scanned";
		waitMultiPhy="Yes";
		System.out.println(getCurrentElement().getName());
		Phy_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, WRScanner);
		FirstPhyInWRAssocIDPhy=Phy_IH[1];
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and StaffID_PK != "+waitPhyIDPK+" and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1 and StaffID_PK != "+waitPhyIDPK+")";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				phyStaffIDPKMulti=phys_rs.getString(1);
				firstName= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastName =phys_rs.getString(3); 
				waitMultiPhyID=phys_rs.getString(4);
			}
			phys_rs.close();
			update.executeUpdate("Update staff Set LastUpdatedDateTime=GETUTCDATE() WHERE staffid_pk='"+phyStaffIDPKMulti+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("Scanned Physician = "+waitMultiPhyID);
		//Scan the physician
		PhysList+=","+waitMultiPhyID;
		PhyIDList+=","+phyStaffIDPKMulti;
		Res = EM_A.ScanItem(WRScanner, "Staff", "Physician", firstName+" "+lastName+"("+waitMultiPhyID+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Multi Physician '"+waitMultiPhyID+"' is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_SendToQueue() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Res = EM_A.ScanItem(WRScanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Description = "Scan of Send To ExamQueue barcode is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_ScanScopeInPR() throws InterruptedException{
		try{ 
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    		stmt="select Name,SerialNumber,ScopeID_PK from scope where scopeid_PK not in "
    			 + "(select Scopeid_FK from scopestatus) and LastUpdatedDateTime="
    			 + "(Select Min(LastUpdatedDateTime) from Scope where scopeid_PK not in  (select Scopeid_FK from scopestatus))";

			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_ID_rs = statement.executeQuery(stmt);
			while(Scope_ID_rs.next()){
				ScanScope = Scope_ID_rs.getString(1); //the first variable in the set is the ID row in the database.
				ScanScopeSerNum = Scope_ID_rs.getString(2);
				ScopeID = Scope_ID_rs.getInt(3);
			}		
			Scope_ID_rs.close();
			
			update.execute("Update Scope set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_PK='"+ScopeID+"';");
			update.close();
			statement.close();
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		
		//Get Scanner
		if (PRScanner.equals("")){
			try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select Loc.LocationID_PK, Loc.Name,Fac.Abbreviation from Location Loc join Facility Fac on Loc.FacilityID_FK=Fac.FacilityID_PK where Loc.LocationTypeID_FK=2 and Loc.IsActive=1 and "
					+"Loc.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				PRScanner = Scanner_ID_rs.getString(2);
				Facility = Scanner_ID_rs.getString(3);
			}		
			Scanner_ID_rs.close();
			//System.out.println("Scanner= "+Scanner);
			update.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
			update.close();
			statement.close();
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		}
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Scope", "", ScanScope, "");
		//System.out.println(Res);
		Description="Scan of Scope '" +ScanScope+"' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_Staff() throws InterruptedException{
		try{ 
   			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
   			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanStaffID= Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
				StaffFirstName= Staff_rs.getString(2); //the second variable in the set is the FirstName row in the database.
				StaffLastName=Staff_rs.getString(3); //the third variable in the set is the LastName row in the database.
			}		
			Staff_rs.close();
			update.execute("Update Staff set LastUpdatedDateTime=GETUTCDATE() WHERE StaffID='"+ScanStaffID+"';");
			update.close();
			statement.close();
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
   		ScanStaff=StaffFirstName+" "+StaffLastName+"("+ScanStaffID+")";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Staff", "Tech", ScanStaff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_ScanPatientInPR() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//Scan the Patient
		Res = EM_A.ScanItem(PRScanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Patient '" +PatientID+"' is done First";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
	}
	public void e_ScanPhysicianInPR() throws InterruptedException{
		prPhy="Scanned";
		System.out.println(getCurrentElement().getName());
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1)";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				prPhyIDPK=phys_rs.getString(1);
				firstName= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastName =phys_rs.getString(3); 
				prPhyID=phys_rs.getString(4);
			}
			phys_rs.close();
			update.executeUpdate("Update staff Set LastUpdatedDateTime=GETUTCDATE() WHERE staffid_pk='"+prPhyIDPK+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("Scanned Physician = "+prPhyID);
		if(PhysList.equals("")){
			PhysList+=prPhyID;
			PhyIDList+=prPhyIDPK;
		}else{
			PhysList+=","+prPhyID;
			PhyIDList+=","+prPhyIDPK;
		}
		//Scan the Physician
		Res = EM_A.ScanItem(PRScanner, "Staff", "Physician", firstName +" "+ lastName+"("+prPhyID+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="e_ScanPhysicianInPR - Scanning the Physician ID "+prPhyID;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);				
	}
	public void e_PRMultiPhysicians() throws InterruptedException{
		prPhy="Scanned";
		prMultiPhy="Yes";
		System.out.println(getCurrentElement().getName());
		Phy_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, PRScanner);
		FirstPhyInPRAssocIDPhy=Phy_IH[1];
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and StaffID_PK Not in ("+PhyIDList+") and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1 and StaffID_PK Not in ("+PhyIDList+"))";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				phyStaffIDPKMulti=phys_rs.getString(1);
				firstName= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastName =phys_rs.getString(3); 
				prMultiPhyID=phys_rs.getString(4);
			}
			phys_rs.close();
			update.executeUpdate("Update staff Set LastUpdatedDateTime=GETUTCDATE() WHERE staffid_pk='"+phyStaffIDPKMulti+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("Scanned Physician = "+prMultiPhyID);
		//Scan the physician
		PhysList+=","+prMultiPhyID;
		PhyIDList+=","+phyStaffIDPKMulti;
		Res = EM_A.ScanItem(PRScanner, "Staff", "Physician", firstName+" "+lastName+"("+prMultiPhyID+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Multi Physician '"+prMultiPhyID+"' is done in "+PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_NoPhySRM() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		String Scope_IH[]=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PRScanner);
		String ScopeInIH=Scope_IH[0];
		String ScopeInAssociationID=Scope_IH[1];
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,ScopeInPRAssocID);
		String ScopeRefNo=ScopeInfo[0];
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		Thread.sleep(2000);
	    IP_A.Click_Details(ScopeRefNo);
	}
	
	public void e_CancelSRM() throws AWTException, InterruptedException{
		System.out.println(getCurrentElement().getName());
		WF_A.Cancel("No");
	}
	
	public void e_NavSRM() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		System.out.println(getCurrentElement().getName());
		
		String ScopeInfo[]=IHV.GetScopeInfo(Unifia_Admin_Selenium.connstring,ScanScope,ScopeInPRAssocID);
		String ScopeRefNo=ScopeInfo[0];
		IP_A.Click_InfectionPrevention();
		IP_A.Click_SRM();
		Thread.sleep(2000);
	    IP_A.Click_Details(ScopeRefNo);
	}
	
	public void e_Reset() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		waitPhy="";
		waitMultiPhy="";
		waitPhy="";
		waitPhyIDPK=""; 
		waitPhyID="";
		phyStaffIDPKMulti="";
		waitMultiPhy="";
		waitMultiPhyID="";
		prPhy="";
		prPhyIDPK=""; 
		prPhyID="";
		PhysList=""; 
		prMultiPhy="";
		prMultiPhyID="";
		PhyIDList="";
		
		Res = EM_A.ScanItem(PRScanner, "Scope", "", ScanScope, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		System.out.println(Res);
		
		Res = EM_A.ScanItem(PRScanner, "Staff", "Tech", ScanStaff, "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		System.out.println(Res);
		
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", "Needs Cleaning", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		System.out.println(Res);
		
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", "Available", "");
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		System.out.println(Res);
		Description ="Resetting the variables";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ExamQueue(){
		Scenario=Scenario+1;
		System.out.println(getCurrentElement().getName());
		Description ="Start of new Scenario "+Scenario;
		actualResult=actualResult+"\r\n---------------------------------------------------------------";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}
	}
	
	public void v_PatientScanned() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		System.out.println("Exam is "+Exam+"; Patient is "+Patient);
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Patient "+PatientID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//Verify in DB
		Description="verifying the Patient scanned in Waiting room";
		Expected="v_PatientScanned: "+PatientID+" scanned into "+WRScanner;
		ExpectedCycleEvent="Patient Checkin";
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="Select Distinct IH.ItemHistoryID_PK, IH.AssociationID_FK,IH.DataSourceID, S.Name As Scanner,S.ScannerID, CE.Name"+
					" From ItemHistory IH"+
					" Join Association A on IH.AssociationID_FK=IH.AssociationID_FK"+
					" Join Location L on IH.LocationID_FK=L.LocationID_PK"+
					" Join Scanner S on L.LocationID_PK=S.LocationID_FK"+
					" Join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"+
					" WHERE IH.ItemHistoryID_PK="+
					"(Select ItemHistoryID_PK FRom ItemHistory"+
					" Where LocationID_FK="+
					"(Select LocationID_PK FROM Location WHERE Name='"+WRScanner+"')"+
					" AND ItemHistoryID_PK= "+
					"(Select MAX(ItemHistoryID_PK) FROM ItemHistory Where LocationID_FK=(Select LocationID_PK FROM Location WHERE Name='"+WRScanner+"')));"; 
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Patient_RS = statement.executeQuery(stmt);
			while(Patient_RS.next()){
				PatientInIH=Integer.toString(Patient_RS.getInt(1));
				PatientInAssociationID=Integer.toString(Patient_RS.getInt(2));
				ActualCycleEvent=Patient_RS.getString(6);
			}		
			Patient_RS.close();
			System.out.println("Scanner= "+WRScanner);
			statement.close();
			conn.close();
   		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println(PatientInIH+" = Patient into exam queue- ItemHistory_PK");
		ResultPatientInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		System.out.println("Scenario verify db");
		ResultEQIn="ResultPatientInCycle= "+ResultPatientInCycle;
		
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
	}
	
	public void v_ExamScanned() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		System.out.println("Exam is "+Exam+"; Patient is "+Patient);
		//Verify the scan message received is correct
		Res = EM_V.VerifyScanMsg("Exam Type "+ScanExam_Abbr+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//Verify in DB
		Description="verifying the Exam scanned in Waiting room";
		Expected="v_ExamScanned: "+Exam+" into "+WRScanner;
		Exam_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, WRScanner);
		ExamInIH=Exam_IH[0];
		ExamInAssociationIDExam=Exam_IH[1];
		ResultExamInSameAssoc=IHV.Result_Same_Assoc(PatientInAssociationID,ExamInAssociationIDExam)+" for Exam and Patient in Waiting Room.";
		System.out.println(Scenario+":  "+ResultExamInSameAssoc);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultExamInSameAssoc);	
	}
	
	public void v_WaitPhysician() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//Verify the scan message received is correct
		if(waitMultiPhy.equalsIgnoreCase("Yes")){
			Res = EM_V.VerifyScanMsg("Physician "+waitMultiPhyID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			Expected="v_PhysicianScanned: "+waitMultiPhyID+" into "+WRScanner;
		}else{
			Res = EM_V.VerifyScanMsg("Physician "+waitPhyID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			Expected="v_PhysicianScanned: "+waitPhyID+" into "+WRScanner;
		}
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		//Verify in DB
		Expected="v_Physician: Physician Scanned in "+WRScanner;
		ExpectedCycleEvent="Physician";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, WRScanner);
		Physician_IH=Scope_IH[0];
		PhyInAssociationIDPhy=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		ResultPhysicianCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysicianCycle);
		
		System.out.println(Physician_IH+" = Physician Scanned - ItemHistory_PK"); 
		ResultEQIn="PhyInAssociationIDPhy= "+PhyInAssociationIDPhy;
		
		if(waitMultiPhy.equalsIgnoreCase("Yes")){
			ResultPhysicianInSameAssoc=IHV.Result_Same_Assoc(FirstPhyInWRAssocIDPhy,PhyInAssociationIDPhy)+" for Physician1"+waitPhyID+" and Physician2 "+waitMultiPhyID+" in Waiting Room.";
			System.out.println(Scenario+":  "+ResultPhysicianInSameAssoc);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysicianInSameAssoc);	
		}else{	
			ResultPhysicianInSameAssoc=IHV.Result_Same_Assoc(ExamInAssociationIDExam,PhyInAssociationIDPhy)+" for Exam and Physician in Waiting Room.";
			System.out.println(Scenario+":  "+ResultPhysicianInSameAssoc);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysicianInSameAssoc);
		}
	}
	
	public void v_SendToQueue() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Res = EM_V.VerifyScanMsg(PatientID+" Added to Queue", Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		// DV Verification for same cycle event
		Patient_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, WRScanner);
		PatientInIH=Patient_IH[0];
		EQInAssociationID=Patient_IH[1];
		ResultSendToExamQAsso=IHV.Result_Same_Assoc(EQInAssociationID,PatientInAssociationID)+" for Patient and Sent to Queue in Waiting Room.";
		System.out.println(Scenario+":  "+ResultSendToExamQAsso);
		ResultEQIn="ResultSendToExamQAsso= "  + ResultSendToExamQAsso;
		Expected="v_SendToQueue: Send to Queue Scanned in "+WRScanner;
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
	}
	
	public void v_scopeInPR() throws InterruptedException{
		Res = EM_V.VerifyScanMsg("Check Scope Reprocessing Record", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		Expected="v_scopeInPR: ScanScope scanned into "+PRScanner;

		ExpectedCycleEvent="Pre-Procedure";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PRScanner);
		ScopeInIH=Scope_IH[0];
		ScopeInPRAssocID=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		
		Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
		ActualScopeState=Scope_IH[0];
		ScopeInLoc=Scope_IH[1];
		ActualOtherScopeState=Scope_IH[2];
		ActualSubloc=Scope_IH[3];
		ActualReproCount=Scope_IH[5];
		ActualExamCount=Scope_IH[6];
		ExpectedState="0";
		OtherScopeStateID=0;
		ExpectedCabinet="0";

		ResultScopeInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
		ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);
		StaffPK=0;
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		if(ResultLastStaff.contains("#Failed!#")){
			ResultLastStaff=ResultLastStaff+" Bug 12603 opened.";
		}
		ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);
		
		ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);
		ResultScopeInLoc=IHV.Result_Location(PRScanner, ScopeInLoc, ExpectedCabinet,ActualSubloc);
		ResultScopeInState=IHV.Result_ScopeState(ExpectedState, ActualScopeState, Integer.toString(OtherScopeStateID),ActualOtherScopeState);
		ResultPRIn="ResultScopeInCycle= "+ResultScopeInCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount
				 +". ResultLastStaff="+ResultLastStaff+". ResultScopeInState="+ResultScopeInState;
		//System.out.println(Scenario+":  "+ResultPRIn);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPRIn);
	}
	public void v_Staff() throws InterruptedException{
		Res = EM_V.VerifyScanMsg("Staff "+ScanStaffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//DB Verification
		Description="Verifying the staff scan in "+PRScanner;
		Expected="v_Staff: Staff that brought the scope into "+PRScanner;
		//Staff_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		ExpectedCycleEvent="PreProcedureStaff";
		Staff_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PRScanner);
		StaffInIH=Staff_IH[0];
		StaffIn_Assoc=Staff_IH[1];
		ActualCycleEvent=Staff_IH[5];
		
		ResultScopeIn1StaffCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		ResultStaffIn=IHV.Result_Same_Assoc(ScopeInPRAssocID,StaffIn_Assoc)+" for staff and scope into PR.";
		
		StaffPK=IHV.GetStaffPK(Unifia_Admin_Selenium.connstring,ScanStaffID);
		LastScanStaffID_FK=IHV.Scp_State_LastStaffID(Unifia_Admin_Selenium.connstring, ScanScope);
		ResultLastStaff=IHV.Result_LastScanStaffID(LastScanStaffID_FK, StaffPK);
		
		ResultStaffIn=ResultStaffIn+"ResultScopeIn1StaffCycle="+ResultScopeIn1StaffCycle+". ResultLastStaff="+ResultLastStaff;
		
		StaffIn_RI=IHV.GetRelatedITem_IHKey(Unifia_Admin_Selenium.connstring, ScopeInIH);
		ResultIn_RI= IHV.RelatedItem_Verf(StaffInIH, StaffIn_RI);
		ResultStaffIn+=". ResultIn_RI="+ResultIn_RI;
		//System.out.println(Scenario+":  "+ResultIn_RI);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaffIn);
		
	}
	public void v_PatientInPR() throws InterruptedException{
		Res = EM_V.VerifyScanMsg("Patient "+PatientID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		//System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		if(!PhyIDList.equals("")){//verifying that physicians scanned in waiting room are correctly associated with the Patient scanned in Procedure room
			String phyIDListInDB="";
			try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				Statement statement = conn.createStatement();
				stmt="select IH.AssociationID_FK,CE.Name from ItemHistory IH join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK join Location L "
						+ "on IH.LocationID_FK=L.LocationID_PK where IH.ScanItemTypeID_FK=2 and IH.LocationID_FK=(select LocationID_PK from location where Name='"+PRScanner+"') "
						+ "and IH.LastUpdatedDateTime=(select Max(LastUpdatedDateTime) from ItemHistory)";
				System.out.println(stmt);
				Patient_RS = statement.executeQuery(stmt);
				while(Patient_RS.next()){
					Physician_Assoc=Patient_RS.getString(1);
					ActualCycleEvent=Patient_RS.getString(2);
				}
				
				Description="verifying that physicians scanned in waiting room are correctly associated to PR bucket when same patient is scanned in Procedure room";
				Expected="v_PatientInPR: Patient Scanned into Procedure Room and "+PhysList+" should be associated to Procedure Room that were scanned in Waiting Room";
				ExpectedCycleEvent="Physician";
	
				ResultPhysicianCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
				ResultPhysician=IHV.Result_Same_Assoc(ScopeInPRAssocID,Physician_Assoc)+" for Physician scan in Waiting room and scope into PR.";
				ExpectedRmSt="In-use";
				
				String RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, PRScanner);
				ResultRmState=IHV.Result_Room_State(RmState, ExpectedRmSt, PRScanner);
	
				ResultPhysician=ResultPhysician+" and ResultRmState"+ResultRmState+".";
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysician);
				
				Expected="Physicians that are scanned in Waiting Room should be associated to PR bucket when same patient is scanned in Procedure room";
				stmt="select ItemHistoryID_PK from ItemHistory where ScanItemTypeID_FK=3 and ScanItemID_FK="+CurrentPatientPK+" and LocationID_FK="+LocationID_PK;
				System.out.println(stmt);
				Patient_RS = statement.executeQuery(stmt);
				while(Patient_RS.next()){
					patInPR_IHID=Patient_RS.getString(1);
				}
				
				//Getting the Physicians ID's from DB which are associated with the patient in Procedure Room
				stmt="select scanItemID_FK from ItemHistory where ScanItemTypeID_FK=2 and AssociationID_FK="+ScopeInPRAssocID+" and ItemHistoryID_PK>"+patInPR_IHID;
				System.out.println(stmt);
				
				Patient_RS = statement.executeQuery(stmt);
				while(Patient_RS.next()){
					phyIDListInDB+=Patient_RS.getString(1)+",";
				}		
				Patient_RS.close();
				statement.close();
				conn.close();
	   		}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			
			phyIDListInDB=phyIDListInDB.substring(0, phyIDListInDB.length()-1);
			if(PhyIDList.equals(phyIDListInDB)){//comparing Physicians ID's in Procedure room and the Physicians ID's scanned in Waiting room 
				ResultPatient="Physicians("+PhysList+") scanned in "+WRScanner+" are properly associated when "+PatientID+" is scanned in "+PRScanner;
			}else{
				UAS.resultFlag="#Failed!#";
				ResultPatient="#Failed!# - Physicians("+PhysList+") scanned in "+WRScanner+" are not properly associated when "+PatientID+" is scanned in "+PRScanner+", expected physicians ID's: "+PhyIDList+" but actual ID's: "+phyIDListInDB;
			}
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPatient);
		
		}else{ //If no physicians are scanned in Procedure room then verifying whether patient is added to Procedure room bucket or not
			Description="verifying the Patient scanned in "+PRScanner;
			Expected="v_PatientInPR: Patient Scanned into Procedure Room";
			ExpectedCycleEvent="Patient In";
			Patient_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PRScanner);
			patInPR_IHID=Patient_IH[0];
			Patient_Assoc=Patient_IH[1];
			ActualCycleEvent=Patient_IH[5];
			
			ResultPatientCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			ResultPatient=IHV.Result_Same_Assoc(ScopeInPRAssocID,Patient_Assoc)+" for patient scan and scope into PR.";
			ExpectedRmSt="In-use";
			
			String RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, PRScanner);
			ResultRmState=IHV.Result_Room_State(RmState, ExpectedRmSt, PRScanner);

			Scope_IH=IHV.Scp_State_Loc(Unifia_Admin_Selenium.connstring, ScanScope);
			ActualReproCount=Scope_IH[5];
			ActualExamCount=Scope_IH[6];

			ExpectedReproCount=IHV.Get_ExpectedReproCount(Unifia_Admin_Selenium.connstring, ScopeID);
			ExpectedExamCount=IHV.Get_ExpectedExamCount(Unifia_Admin_Selenium.connstring, ScopeID);

			ResultReproCount=IHV.Result_ReproCount(ActualReproCount,ExpectedReproCount);
			ResultExamCount=IHV.Result_ExamCount(ActualExamCount,ExpectedExamCount);

			ResultPatient=ResultPatient+" and ResultPatientCycle"+ResultPatientCycle+". ResultReproCount="+ResultReproCount+". ResultExamCount="+ResultExamCount+".";
			//System.out.println(Scenario+":  "+ResultPatient);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPatient);
		}
	}
	
	public void v_PhysicianInPR() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Description="Verify Scope Record Management of "+ScanScope+" into "+PRScanner+". ";
		//Verify the scan message received is correct
		if(prMultiPhy.equalsIgnoreCase("Yes")){
			Res = EM_V.VerifyScanMsg("Physician "+prMultiPhyID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			Expected="v_PhysicianInPR: "+prMultiPhyID+" into "+PRScanner;
		}else{
			Res = EM_V.VerifyScanMsg("Physician "+prPhyID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			Expected="v_PhysicianInPR: "+prMultiPhyID+" into "+PRScanner;
		}
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		//Verify in DB
		Expected="v_PhysicianInPR: Physician Scanned into Procedure Room ";
		ExpectedCycleEvent="Physician";
		Scope_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, PRScanner);
		Physician_IH=Scope_IH[0];
		PhyInAssociationIDPhy=Scope_IH[1];
		ActualCycleEvent=Scope_IH[5];
		ResultPhysicianCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
		
		System.out.println(Physician_IH+" = Physician Scanned - ItemHistory_PK"); 
		ResultEQIn="ResultPhysicianCycle= "+ResultPhysicianCycle;
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
		
		if(prMultiPhy.equalsIgnoreCase("Yes")){
			ResultPhysicianInSameAssoc=IHV.Result_Same_Assoc(FirstPhyInPRAssocIDPhy,PhyInAssociationIDPhy)+" for Physician1"+prPhyID+" and Physician2 "+prMultiPhyID+" in Procedure Room.";
			System.out.println(Scenario+":  "+ResultPhysicianInSameAssoc);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysicianInSameAssoc);	
		}
		
		ResultPhysicianInSameAssoc=IHV.Result_Same_Assoc(ScopeInPRAssocID,PhyInAssociationIDPhy)+" for Scope and Physician in Procedure Room.";
		System.out.println(Scenario+":  "+ResultPhysicianInSameAssoc);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysicianInSameAssoc);
	}
	
	public void v_SRM(){
		System.out.println(getCurrentElement().getName());
		Description="Verifying SRM details screen of "+ScanScope+" to see Physicians("+PhysList+") are selected properly.";
		Expected="v_SRM: Physicians are properly displayed on SRM details screen";
		if(!waitPhy.equalsIgnoreCase("Scanned")&&!prPhy.equalsIgnoreCase("Scanned")){
			ResultPhysician=WF_V.Verify_SelectedPhysician("");
			Expected="No Physician should be selected";
		}else{
			ResultPhysician=WF_V.Verify_SelectedPhysician(PhysList);
			Expected=PhysList+" should be selected";
		}
		System.out.println("ResultPhysician="+ResultPhysician);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysician);	
	}
	
	public void v_CancelSRM(){
		System.out.println(getCurrentElement().getName());
	}
}
