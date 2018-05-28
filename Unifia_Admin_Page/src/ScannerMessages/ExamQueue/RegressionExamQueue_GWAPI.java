package ScannerMessages.ExamQueue;

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

public class RegressionExamQueue_GWAPI extends ExecutionContext{
	
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	public TestFrameWork.TestHelper TH;
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
	private String Scanner="";
	private Integer Scenario=0;
	
	private Connection conn= null;
	private ResultSet Patient_RS;  //result set used to get patientid from the Test DB
	private ResultSet PatientNew_RS;
	private ResultSet Exam_Name_rs;
	private ResultSet Scanner_ID_rs,phys_rs;
	private String stmt; //used for SQL queries
	public static String actualResult="\t\t\t Regression_ExamQueue_TestSummary \n"; 
	private String TestResFileName="Regression_ExamQueue_TestSummary_";
	private String Description;
	private String ForFileName;
	private String Expected, ExpectedCycleEvent, Patient_IH[], PatientInIH,ScopeInAssociationID,ActualCycleEvent,ResultScopeInCycle;
	private String ResultPRIn, PatientScanned="",ResultScopeAsso;
	private String Exam_IH[], ExamInIH,Phy_IH[], ScopeInAssociationIDExam;
	private String ExamScanned="",PatientInAssociationID,ExamInAssociationIDExam,EQInAssociationID;
	private String ResultPatientInCycle,ResultEQIn,ResultExamInSameAssoc,ResultSendToExamQAsso,ResultPatientInSameAssoc;
	private String Phy,PhyScanned,phyStaffIDPK, staffID,firstName, lastName,phyStaffIDPKMulti,firstNameMulti,lastNameMulti, staffIDMulti;
	private String PhyInAssociationIDPhy,ResultPatientInSameAssocWithPhysician,ResultExamInSameAssocWithPhysician,ResultPhysicianInSameAssoc,PhyInIH,FirstPhyInAssociationIDPhy;
	TestFrameWork.Emulator.GetIHValues IHV;
	
	public String Timeout="";
	private String CancelProcedure[];
	Map<String, String> Queue= new HashMap<String, String>();
	
	//[RK]-01Jul16
	public void e_Start(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date); 
		FileName="CycleMgmtExamQueue_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		UAS.XMLFileName=FileName;
		UAS.ScannerCount=0;
		UAS.TestCaseNumber=1;
		
	}
	//[RK]-01Jul16
	public void e_PatientFirst() throws  InterruptedException{
		Patient="Scanned";
		Exam="NotScanned";
		PatientScanned="First";
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
		  //Fetch the decrypted patient id 
			Patient_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			System.out.println("stmtPatEncr="+stmtPatEncr);
	   		
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
		   	while(PatientNew_RS.next()){
		   		CurrentPatientPK = PatientNew_RS.getInt(1); 
				//LastUpdatedDate= PatientNew_RS.getString(2);
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
		
   		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		//Res = EM_A.ScanItem("Waiting2", "Patient", "", "PID888888", "");
		Res = EM_A.ScanItem(Scanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Patient '" +PatientID+"' is done First";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		
	}

	public void e_ExamFirst()throws  InterruptedException{
		Patient="NotScanned";
		Exam="Scanned";
		ExamScanned="First";
		//Patient=String.valueOf(getMbt().getDataValue("Patient"));
		//Exam=String.valueOf(getMbt().getDataValue("Patient"));
		System.out.println(getCurrentElement().getName());
		//stmt="Select ActualExamType_Name,ActualExamType_Abbrv  FROM examtype Where idExamtype<=14 and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Existing')";
		try{
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
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
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		//Res = EM_A.ScEanItem("Waiting2", "Exam Type", "", "Anoscopy", "");
		Res = EM_A.ScanItem(Scanner, "Exam Type", "", ScanExam, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Exam '"+ScanExam+"' is done First";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	public void e_ExamScan()throws  InterruptedException{
		Exam="Scanned";
		//Patient=String.valueOf(getMbt().getDataValue("Patient"));
		//Exam=String.valueOf(getMbt().getDataValue("Patient"));
		System.out.println(getCurrentElement().getName());
		//stmt="Select ActualExamType_Name,ActualExamType_Abbrv  FROM examtype Where idExamtype<=14 and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Existing')";
		try{
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
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
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		Res = EM_A.ScanItem(Scanner, "Exam Type", "", ScanExam, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Exam '"+ScanExam+"' is done after Physician";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_SendToQ()throws  InterruptedException{
		Exam="NotScanned";
		Patient="NotScanned";
		//Patient=String.valueOf(getMbt().getDataValue("Patient"));
		//Exam=String.valueOf(getMbt().getDataValue("Patient"));
		System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Description = "Scan of Send To ExamQueue barcode is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SendtoQfrmPhy()throws  InterruptedException{
		//Patient=String.valueOf(getMbt().getDataValue("Patient"));
		//Exam=String.valueOf(getMbt().getDataValue("Patient"));
		System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Description = "Scan of Send To ExamQueue barcode is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	
	public void e_SendtoQNoPatNoExam()throws  InterruptedException{
		Exam="NotScanned";
		Patient="NotScanned";
		Phy="Scanned";
		System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Description = "Scan of Send To ExamQueue barcode is done with only physician scanned";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PatientSecond() throws  InterruptedException{
		Patient="Scanned";
		PatientScanned="Second";
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
		  //Fetch the decrypted patient id 
			Patient_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			System.out.println("stmtPatEncr="+stmtPatEncr);
	   		
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
		   	while(PatientNew_RS.next()){
		   		CurrentPatientPK = PatientNew_RS.getInt(1); 
				//LastUpdatedDate= PatientNew_RS.getString(2);
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
   		//Scan the Patient
		//Res = EM_A.ScanItem("Waiting2", "Patient", "", "PID888888", "");
		Res = EM_A.ScanItem(Scanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Patient '"+PatientID+"' is done second";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;	
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	
	
	public void e_PatientScan() throws  InterruptedException{
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
		  //Fetch the decrypted patient id 
			Patient_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			System.out.println("stmtPatEncr="+stmtPatEncr);
	   		
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
		   	while(PatientNew_RS.next()){
		   		CurrentPatientPK = PatientNew_RS.getInt(1); 
				//LastUpdatedDate= PatientNew_RS.getString(2);
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
   		//Scan the Patient
		//Res = EM_A.ScanItem("Waiting2", "Patient", "", "PID888888", "");
		Res = EM_A.ScanItem(Scanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Patient '"+PatientID+"' is done after physician scan";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;	
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_ExamSecond() throws InterruptedException{
		Exam="Scanned";
		ExamScanned="Second";
		System.out.println(getCurrentElement().getName());
		//stmt="Select ActualExamType_Name,ActualExamType_Abbrv   FROM examtype Where idExamtype<=14 and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Existing')";
		//System.out.println(stmt);
		try{
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
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

		//Scan the Exam
		//Res = EM_A.ScEanItem("Waiting2", "Exam", "", "Anoscopy", "");
		Res = EM_A.ScanItem(Scanner, "Exam Type", "", ScanExam, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Exam '"+ScanExam+"' is done second";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoPatient() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//Res = EM_A.ScEanItem("Waiting2", "Exam", "", "Anoscopy", "");
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Send to ExamQueue is done without Patient scanned";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	} 
	
	public void e_NoExam() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Send to ExamQueue is done without Exam scanned";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ExamPatient() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Send to ExamQueue is done with Exam and Patient scanned";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PatientExam() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Send to ExamQueue is done with Patient and Exam scanned";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	public void e_Phys1()throws  InterruptedException{
		Patient="NotScanned";
		Exam="NotScanned";
		Phy="Scanned";
		ExamScanned="";
		PatientScanned="";
		PhyScanned="First";
		System.out.println(getCurrentElement().getName());
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1)";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				phyStaffIDPK=phys_rs.getString(1);
				firstName= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastName =phys_rs.getString(3); 
				staffID=phys_rs.getString(4);
			}
			phys_rs.close();
			update.executeUpdate("Update staff Set LastUpdatedDateTime=GETUTCDATE() WHERE staffid_pk='"+phyStaffIDPK+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("Scanned Physician = "+staffID);
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		//Scan the physician
		Res = EM_A.ScanItem(Scanner, "Staff", "Physician", firstName + " " + lastName+"("+staffID+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Physician '"+staffID+"' is done First";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Phys2()throws  InterruptedException{
		Phy="Scanned";
		PhyScanned="After";
		System.out.println(getCurrentElement().getName());
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1)";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				phyStaffIDPK=phys_rs.getString(1);
				firstName= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastName =phys_rs.getString(3); 
				staffID=phys_rs.getString(4);
			}
			phys_rs.close();
			update.executeUpdate("Update staff Set LastUpdatedDateTime=GETUTCDATE() WHERE staffid_pk='"+phyStaffIDPK+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("Scanned Physician = "+staffID);
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		//Scan the Physician
		Res = EM_A.ScanItem(Scanner, "Staff", "Physician", firstName +" "+ lastName+"("+staffID+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Physicin '"+staffID+"' is done after exam or patient scan is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Phys3()throws  InterruptedException{
		Phy="Scanned";
		PhyScanned="After";
		System.out.println(getCurrentElement().getName());
		try{
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1)";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				phyStaffIDPK=phys_rs.getString(1);
				firstName= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastName =phys_rs.getString(3); 
				staffID=phys_rs.getString(4);
			}
			phys_rs.close();
			update.executeUpdate("Update staff Set LastUpdatedDateTime=GETUTCDATE() WHERE staffid_pk='"+phyStaffIDPK+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("Scanned Physician = "+staffID);
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		Res = EM_A.ScanItem(Scanner, "Staff", "Physician", firstName+" "+lastName+"("+staffID+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Physician '"+staffID+"' is done after exam or patient scan is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MultiPhy()throws  InterruptedException{
		Phy="Scanned";
		ExamScanned="";
		PhyScanned="First";
		staffIDMulti="";
		System.out.println(getCurrentElement().getName());
		
		Phy_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		FirstPhyInAssociationIDPhy=Phy_IH[1];
		try{
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1)";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				phyStaffIDPKMulti=phys_rs.getString(1);
				firstNameMulti= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastNameMulti =phys_rs.getString(3); 
				staffIDMulti=phys_rs.getString(4);
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
		System.out.println("Scanned Physician = "+staffIDMulti);
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		//Scan the physician
		Res = EM_A.ScanItem(Scanner, "Staff", "Physician", firstNameMulti +" "+lastNameMulti+"("+staffIDMulti+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Physician '"+staffIDMulti+"' is done first";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_Reset(){
		System.out.println(getCurrentElement().getName());
		Description ="Reset is done";
		Patient="NotScanned";
		Exam="NotScanned";
		Scanner="";
		ScanExam="";
		PatientID="";
		Phy="NotScanned";
		PhyScanned="";
		staffID="";
		staffIDMulti="";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+"\n---------------------------------------------------------------";
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ExamQueue() throws InterruptedException{
		Scenario=Scenario+1;
		System.out.println(getCurrentElement().getName());
		Description ="Start of new Scenario "+Scenario;
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
			Expected="v_PatientScanned: "+PatientID+" scanned into "+Scanner;
			ExpectedCycleEvent="Patient Checkin";
			if(Queue.containsKey(Scanner) && Res){
				Queue.remove(Scanner);//Removing the Patient that was already in Queue from the last Scenario
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
							"(Select LocationID_PK FROM Location WHERE Name='"+Scanner+"')"+
							" AND ItemHistoryID_PK= "+
							"(Select MAX(ItemHistoryID_PK) FROM ItemHistory Where LocationID_FK=(Select LocationID_PK FROM Location WHERE Name='"+Scanner+"')));"; 
					System.out.println(stmt);
					Statement statement = conn.createStatement();
					Patient_RS = statement.executeQuery(stmt);
					while(Patient_RS.next()){
						PatientInIH=Integer.toString(Patient_RS.getInt(1));
						PatientInAssociationID=Integer.toString(Patient_RS.getInt(2));
						ActualCycleEvent=Patient_RS.getString(6);
					}		
					Patient_RS.close();
					System.out.println("Scanner= "+Scanner);
					statement.close();
					conn.close();
		   		}
				catch (SQLException ex){
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());	
				}
			}else{
				Patient_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
				PatientInIH=Patient_IH[0];
				PatientInAssociationID=Patient_IH[1];
				ActualCycleEvent=Patient_IH[5];
			}
			System.out.println(PatientInIH+" = Patient into exam queue- ItemHistory_PK");
			//ScopeStatus and location
			ResultPatientInCycle=IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent);
			System.out.println("Scenario verify db");
			System.out.println(Scenario+":  "+ResultPatientInCycle);
			ResultEQIn="ResultPatientInCycle= "+ResultPatientInCycle;
			
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
			if (PatientScanned.equals("Second")){
				System.out.println(PatientInIH+" = Patient into exam queue- ItemHistory_PK");
				//DB Verification
				ResultPatientInSameAssoc=IHV.Result_Same_Assoc(PatientInAssociationID,ExamInAssociationIDExam)+" for Patient and Exam in Waiting Room.";
				System.out.println(Scenario+":  "+ResultPatientInSameAssoc);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultPatientInSameAssoc);
	        }
			
			if (Phy.equals("Scanned")){
				ResultPatientInSameAssocWithPhysician=IHV.Result_Same_Assoc(PhyInAssociationIDPhy,PatientInAssociationID)+" for Physician and Patient in Waiting Room.";
				System.out.println(Scenario+":  "+ResultPatientInSameAssocWithPhysician);
				IHV.Exec_Log_Result(FileName, Description, Expected, ResultPatientInSameAssocWithPhysician);	
			}
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
		Expected="v_ExamScanned: "+Exam+" into "+Scanner;
		Exam_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		ExamInIH=Exam_IH[0];
		ExamInAssociationIDExam=Exam_IH[1];
		
		System.out.println(ExamInIH+" = Exam Scanned - ItemHistory_PK"); 
		ResultEQIn="ExamInAssociationIDExam= "+ExamInAssociationIDExam;
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);

		if(ExamScanned.equals("Second")){
			//DB Verification
			ResultExamInSameAssoc=IHV.Result_Same_Assoc(PatientInAssociationID,ExamInAssociationIDExam)+" for Exam and Patient in Waiting Room.";
			System.out.println(Scenario+":  "+ResultExamInSameAssoc);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultExamInSameAssoc);	
		}
		
		if (Phy.equals("Scanned")){
			ResultExamInSameAssocWithPhysician=IHV.Result_Same_Assoc(PhyInAssociationIDPhy,ExamInAssociationIDExam)+" for Exam and Physician in Waiting Room.";
			System.out.println(Scenario+":  "+ResultExamInSameAssocWithPhysician);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultExamInSameAssocWithPhysician);	
		}
	}
	
	public void v_SendToExamQueue() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		System.out.println("Exam is "+Exam+"; Patient is "+Patient+"; Physician is "+Phy);
		Expected="v_SendToExamQueue: SendToExamQueue scanned into "+Scanner;
		if (Patient.equals("Scanned")&&Exam.equals("NotScanned")){
			//Verify the scan message received is correct
			Res = EM_V.VerifyScanMsg("Patient Added without Exam Type", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			// DB Verify for "Same association for Send to Exam Queue record
			Patient_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			PatientInIH=Patient_IH[0];
			EQInAssociationID=Patient_IH[1];
			ResultSendToExamQAsso=IHV.Result_Same_Assoc(EQInAssociationID,PatientInAssociationID)+" for Patient and Exam in Waiting Room.";
			System.out.println(Scenario+":  "+ResultSendToExamQAsso);
			ResultEQIn="ResultSendToExamQAsso= "  + ResultSendToExamQAsso;
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
		}else if (Patient.equals("NotScanned")&&Exam.equals("Scanned")){
			Res = EM_V.VerifyScanMsg("Added to Queue", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			// DB Verify
			Patient_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			PatientInIH=Patient_IH[0];
			EQInAssociationID=Patient_IH[1];
			ResultSendToExamQAsso=IHV.Result_Same_Assoc(EQInAssociationID,ExamInAssociationIDExam)+" for Patient and Exam in Waiting Room.";
			System.out.println(Scenario+":  "+ResultSendToExamQAsso);
			ResultEQIn="ResultSendToExamQAsso= "  + ResultSendToExamQAsso;
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
		}else if (Patient.equals("NotScanned")&&Exam.equals("NotScanned")){
			Res = EM_V.VerifyScanMsg("Added to Queue", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			//DB Verify
			Patient_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			PatientInIH=Patient_IH[0];
			String EQInAssociationID=Patient_IH[1];
			System.out.println(Scenario+":  "+ResultSendToExamQAsso);
			ResultEQIn="AssociationID= "+EQInAssociationID;
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
		}else if (Patient.equals("Scanned")&&Exam.equals("Scanned")){
			Res = EM_V.VerifyScanMsg(PatientID+" Added to Queue", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			// DV Verification for same cycle event
			Patient_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			PatientInIH=Patient_IH[0];
			EQInAssociationID=Patient_IH[1];
			ResultSendToExamQAsso=IHV.Result_Same_Assoc(EQInAssociationID,PatientInAssociationID)+" for Patient and Exam in Waiting Room.";
			System.out.println(Scenario+":  "+ResultSendToExamQAsso);
			ResultEQIn="ResultSendToExamQAsso= "  + ResultSendToExamQAsso;
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
		}else if (Patient.equals("Scanned")&&Exam.equals("Scanned")&&Phy.equals("Scanned")){
			Res = EM_V.VerifyScanMsg(PatientID+" Added to Queue", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			// DV Verification for same cycle event
			Patient_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			PatientInIH=Patient_IH[0];
			EQInAssociationID=Patient_IH[1];
			ResultSendToExamQAsso=IHV.Result_Same_Assoc(EQInAssociationID,PatientInAssociationID)+" for Patient and Exam in Waiting Room.";
			System.out.println(Scenario+":  "+ResultSendToExamQAsso);
			ResultEQIn="ResultSendToExamQAsso= "  + ResultSendToExamQAsso;
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;	
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PatExPhySendToQ() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Patient="Scanned";;
		Exam="Scanned";
		Phy="Scanned";
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
		  //Fetch the decrypted patient id 
			Patient_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			System.out.println("stmtPatEncr="+stmtPatEncr);
	   		
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
		   	while(PatientNew_RS.next()){
		   		CurrentPatientPK = PatientNew_RS.getInt(1); 
				//LastUpdatedDate= PatientNew_RS.getString(2);
		   		PatientID = PatientNew_RS.getString(3);
				}
		   	PatientNew_RS.close();
	   		
	
			Statement update = conn.createStatement();
			update.execute("Update Patient set LastUpdatedDateTime=GETUTCDATE() WHERE PatientID_PK='"+CurrentPatientPK+"';");
			
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
			statement.close();
			conn.close();
		}
		catch (SQLException ex){
			// handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		
   		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		//Res = EM_A.ScanItem("Waiting2", "Patient", "", "PID888888", "");
		Res = EM_A.ScanItem(Scanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Patient '" +PatientID+"' is done First";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		try{ 
			Thread.sleep(2000);
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select IH.AssociationID_FK from ItemHistory IH where IH.ScanItemTypeID_FK=3 and IH.ScanItemID_FK="+CurrentPatientPK+" and IH.LastUpdatedDateTime=(select MAX(LastUpdatedDateTime) from ItemHistory)"; 
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				PatientInAssociationID = Scanner_ID_rs.getString(1);
			}		
			Scanner_ID_rs.close();
			System.out.println("PatientInAssociationID= "+PatientInAssociationID);
			statement.close();
			conn.close();
   		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		//Scan the Exam
		//Res = EM_A.ScEanItem("Waiting2", "Exam", "", "Anoscopy", "");
		Res = EM_A.ScanItem(Scanner, "Exam Type", "", ScanExam, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Exam '"+ScanExam+"' is done second";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		//Scan Physician
		
		System.out.println(getCurrentElement().getName());
		//stmt="Select ActualExamType_Name,ActualExamType_Abbrv  FROM examtype Where idExamtype<=14 and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Existing')";
		try{
			//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			stmt="select staffid_pk,firstName,LastName,StaffID from staff where stafftypeid_fk=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from staff where  stafftypeid_fk=1 and IsActive=1)";
			System.out.println(stmt);
			phys_rs = statement.executeQuery(stmt);
			while(phys_rs.next()){
				phyStaffIDPK=phys_rs.getString(1);
				firstName= phys_rs.getString(2); //the first variable in the set is the ID row in the database.
				lastName =phys_rs.getString(3); 
				staffID=phys_rs.getString(4);
			}
			phys_rs.close();
			update.executeUpdate("Update staff Set LastUpdatedDateTime=GETUTCDATE() WHERE staffid_pk='"+phyStaffIDPK+"';");
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		
		//Scan the physician
		Res = EM_A.ScanItem(Scanner, "Staff", "Physician", firstName+" "+lastName+"("+staffID+")","");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description="Scan of Physician '"+staffID+"' is done First";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
		
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Send to ExamQueue is done with Exam and Patient scanned";
		actualResult=actualResult+"\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_NoPatExPhySendToQ()throws  InterruptedException{
		Exam="NotScanned";
		Patient="NotScanned";
		Phy="NotScanned";
		//Patient=String.valueOf(getMbt().getDataValue("Patient"));
		//Exam=String.valueOf(getMbt().getDataValue("Patient"));
		System.out.println(getCurrentElement().getName());
		if (Scanner.equals("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=7 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=7 and IsActive=1)"; 
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
				}		
				Scanner_ID_rs.close();
				System.out.println("Scanner= "+Scanner);
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
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Description = "Scan of Send To ExamQueue barcode is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_cancelProc() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Res = EM_A.ScanItem(Scanner, "Workflow Event", "", "Cancel Procedure", "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Cancel Procedure is done";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PatInTime() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Timeout="INTIME";
		System.out.println("TimeOut="+Timeout);
		Res = EM_A.ScanItem(Scanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		/*Patient_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		PatientInIH=Patient_IH[0];*/
		Description = "Scan of Patient "+PatientID+" is done in Timeout period";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PatAfterTime() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Timeout="AFTERTIMEOUT";
		System.out.println("TimeOut="+Timeout);
		Thread.sleep(35000);//waiting to exceed the Timeout period 
		Res = EM_A.ScanItem(Scanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		/*Patient_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		PatientInIH=Patient_IH[0];*/
		Description = "Scan of Patient "+PatientID+" is done after Timeout period";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		Queue.put(Scanner, PatientID);//Storing these values because scanning the patient after 30 sec will create a new bucket for that patient
	}
	
	public void e_PatNotInQ() throws InterruptedException{
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
		  //Fetch the decrypted patient id 
			Patient_RS.close();
			String stmtPatEncr="Execute dbo.sp_GetPatients_EQUAL '"+ CurrentPatientPK+"'";
			System.out.println("stmtPatEncr="+stmtPatEncr);
	   		
	   		PatientNew_RS=statement.executeQuery(stmtPatEncr);
		   	while(PatientNew_RS.next()){
		   		CurrentPatientPK = PatientNew_RS.getInt(1); 
				//LastUpdatedDate= PatientNew_RS.getString(2);
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
		Res = EM_A.ScanItem(Scanner, "Patient", "", PatientID, "");
		System.out.println(Res);
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Description = "Scan of Patient "+PatientID+" is done who is not in Exam Queue";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void v_ScanPatExPhy() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		System.out.println("Exam is "+Exam+"; Patient is "+Patient+"Physician is "+Phy);
		Expected="v_SendToExamQueue: SendToExamQueue scanned into "+Scanner;
		if (Patient.equals("NotScanned")&&Exam.equals("NotScanned")){
			Res = EM_V.VerifyScanMsg("Added to Queue", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			//DB Verify
			Patient_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
			PatientInIH=Patient_IH[0];
			String EQInAssociationID=Patient_IH[1];
			System.out.println(Scenario+":  "+ResultSendToExamQAsso);
			ResultEQIn="AssociationID= "+EQInAssociationID;
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
		}else if (Patient.equals("Scanned")&&Exam.equals("Scanned")){
			Res = EM_V.VerifyScanMsg(PatientID+" Added to Queue", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			try{
				Thread.sleep(2000);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
				Statement statement = conn.createStatement();
				stmt="select CE.Name from CycleEvent CE join ItemHistory IH on CE.CycleEventID_PK=IH.CycleEventID_FK and ScanItemTypeID_FK=3 and ScanItemID_FK="+CurrentPatientPK;
				System.out.println("stmt="+stmt);
				Patient_RS = statement.executeQuery(stmt);
				while(Patient_RS.next()){
					ActualCycleEvent = Patient_RS.getString(1); 
				}
				Patient_RS.close();
				statement.close();
				conn.close();
				
				if(ActualCycleEvent.equalsIgnoreCase("Patient Checkin")){
					ResultEQIn="Pass- Patient added to Queue";
				}else{
					ResultEQIn="Error- Patient is not added to Queue";
				}
			}
			catch (SQLException ex){
				// handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			ResultEQIn="ResultSendToExamQ= "  + ResultEQIn;
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
			
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;	
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_CancelProcedure() throws InterruptedException {
		System.out.println(getCurrentElement().getName());
		Expected = "v_CancelProcedure: Cancel Procedure scanned with "+ Scanner;
		Res = EM_V.VerifyScanMsg("Please Scan PatientID",Unifia_Admin_Selenium.ScannerCount);
		System.out.println(Res);
		CancelProcedure = IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
		String cycleEvent = CancelProcedure[5];
		if (cycleEvent == null) {
			ResultEQIn = "Pass - Cancel Procedure scanned successfully";
		} else {
			ResultEQIn = "Error - Cancel Procedure not scanned successfully";
		}
		actualResult = actualResult + "\r\n" + getCurrentElement().getName()+ "---:\r\n\t" + UAS.Result;
		TH.WriteToTextFile(TestResFileName + ForFileName, actualResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
	}
	
	public void v_CancelPatient() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		if(Timeout.equalsIgnoreCase("INTIME")){
			Expected="Patient-"+PatientID+" Exam-"+ScanExam+" is removed from the Exam Queue";
			Res = EM_V.VerifyScanMsg(PatientID+" Procedure Canceled", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			try{
				Thread.sleep(2000);
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
				Statement statement = conn.createStatement();
				stmt="Select AssociationID_FK from ItemHistory where ScanItemTypeID_FK=3 and ScanItemID_FK="+CurrentPatientPK;
				System.out.println("stmt="+stmt);
				Patient_RS = statement.executeQuery(stmt);
				while(Patient_RS.next()){
					PatientInAssociationID = Patient_RS.getString(1); 
				}
				Patient_RS.close();
				statement.close();
				conn.close();
				
				if(PatientInAssociationID==null){
					ResultEQIn="Pass- Patient removed from Queue and Patient AssociationID is null in ItemHistory table";
				}else{
					ResultEQIn="Error- Patient is not removed from Queue and Patient AssociationID is not null in ItemHistory table";
				}
			}
			catch (SQLException ex){
				// handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			
		}else if(Timeout.equalsIgnoreCase("AFTERTIMEOUT")){
			Expected="Patient-"+PatientID+" is not removed from the Exam Queue";
			Res = EM_V.VerifyScanMsg("Patient "+PatientID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
			System.out.println(Res);
			try{
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);		
				Statement statement = conn.createStatement();
				stmt="Select AssociationID_FK from ItemHistory where ScanItemTypeID_FK=3 and ScanItemID_FK="+CurrentPatientPK;;
				System.out.println("stmt="+stmt);
				Patient_RS = statement.executeQuery(stmt);
				while(Patient_RS.next()){
					PatientInAssociationID = Patient_RS.getString(1); 
				}
				Patient_RS.close();
				statement.close();
				conn.close();
				
				if (!(PatientInAssociationID==null)) {
					ResultEQIn = "Pass- Patient is not removed from Queue as Time Exceeded and Patient AssociationID"+PatientInAssociationID;
				} else if (PatientInAssociationID.equalsIgnoreCase("NULL")) {
					ResultEQIn = "Error- Patient is removed from Queue";
				}
			}
			catch (SQLException ex){
				// handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		}else{
			Expected="Patient-"+PatientID+" Exam-"+ScanExam+" is not removed from the Exam Queue as Patient is not in Exam Queue";
			Res = EM_V.VerifyScanMsg(PatientID+" Not Exists In Exam Queue", Unifia_Admin_Selenium.ScannerCount);
			Patient_IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			PatientInAssociationID=Patient_IH[1];
			ActualCycleEvent=Patient_IH[5];
			if(ActualCycleEvent==null && PatientInAssociationID.equals("0")){
				ResultEQIn="Pass - Patient-"+PatientID+" is not removed from the Exam Queue as Patient is not in Exam Queue";
			}else{
				ResultEQIn="Error - Patient-"+PatientID+" is removed from the Exam Queue though Patient is not in Exam Queue";
			}
			Thread.sleep(60000);//1 min wait
		}
		
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;	
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
	}
	
	public void v_PhysicianScanned() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//System.out.println("Physician is "+Phy+"; Patient is "+Patient);
		//Verify the scan message received is correct
		if(!staffIDMulti.equalsIgnoreCase("")){
			Res = EM_V.VerifyScanMsg("Physician "+staffIDMulti+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		}else{
			Res = EM_V.VerifyScanMsg("Physician "+staffID+" Scanned", Unifia_Admin_Selenium.ScannerCount);
		}
		System.out.println(Res);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//Verify in DB
		Expected="v_PhysicianScanned: "+staffID+" into "+Scanner;
		Phy_IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		PhyInIH=Phy_IH[0];
		PhyInAssociationIDPhy=Phy_IH[1];
		
		System.out.println(PhyInIH+" = Physician Scanned - ItemHistory_PK"); 
		ResultEQIn="PhyInAssociationIDPhy= "+PhyInAssociationIDPhy;
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultEQIn);
		
		if(!staffIDMulti.equalsIgnoreCase("")){
			ResultPhysicianInSameAssoc=IHV.Result_Same_Assoc(FirstPhyInAssociationIDPhy,PhyInAssociationIDPhy)+" for Physician1"+staffID+" and Physician2 "+staffIDMulti+" in Waiting Room.";
			System.out.println(Scenario+":  "+ResultPhysicianInSameAssoc);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysicianInSameAssoc);	
		}
		
		if (Exam.equals("Scanned")){
			ResultPhysicianInSameAssoc=IHV.Result_Same_Assoc(ExamInAssociationIDExam,PhyInAssociationIDPhy)+" for Exam and Physician in Waiting Room.";
			System.out.println(Scenario+":  "+ResultPhysicianInSameAssoc);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysicianInSameAssoc);	
		}
				
		if (Patient.equals("Scanned")){
			ResultPhysicianInSameAssoc=IHV.Result_Same_Assoc(PatientInAssociationID,PhyInAssociationIDPhy)+" for Physician and Patient in Waiting Room.";
			System.out.println(Scenario+":  "+ResultPhysicianInSameAssoc);
			IHV.Exec_Log_Result(FileName, Description, Expected, ResultPhysicianInSameAssoc);	
		}
	}
}
