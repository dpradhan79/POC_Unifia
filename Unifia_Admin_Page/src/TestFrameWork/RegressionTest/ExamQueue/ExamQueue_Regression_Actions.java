package TestFrameWork.RegressionTest.ExamQueue;
import java.awt.AWTException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;

public class ExamQueue_Regression_Actions extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	static TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.QVDashboard.Dashboard_Verification qvd_v;
	private static Dashboard_Actions DA;
	private static Dashboard_Verification DV;
	private static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;
	public static TestFrameWork.Unifia_Admin_Selenium UAS;

	public static void EQ_Scans(String Location, String Exam, String Phy1,String Phy2,String Patient)throws InterruptedException, AWTException, SQLException{
		String [] temp= new String[2];
		String [] phy_split= new String[2];
		String [] phy_split1= new String[2];
		Boolean Res;
		String Description;
		String Expected;
		
		String Phy_IH;
		String Phy_Assoc="";
		String ResultPhy1="";
		String ResultPhy2="";
		String ResultPhyCycleEvent="";
		
		String Patient_IH;
		String PatientAssociationID="";
		String ResultPatientCycleEvent;
		String ResultPatient="";
		
		String PhyID="";
		String Staff_IH[];
		String Changes="No";
		String CycleEvent="";
		String Scope_IH[];
		String Scope1InIH;
		String ActualCycleEvent;
		String eachPatID=null,patientName=null; 
		StringBuffer exams = new StringBuffer("");
		StringBuffer physicians = new StringBuffer("");

		//Scan Patient if provided.
		if(Patient.equalsIgnoreCase("")){
			ResultPatient="Patient not provided";
		}else {

			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Patient", "", Patient, "");
			Description="Scan of Patient '" +Patient+"' is done in "+ Location;
			//nicole - temporary wait time for first scan. used for Nicole's debugging of new function. 
			Thread.sleep(20000);

			Expected="Patient scanned into Exam Queue";

			CycleEvent="Patient Checkin";
			Scope_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
			Patient_IH=Scope_IH[0];
			PatientAssociationID=Scope_IH[1];
			ActualCycleEvent=Scope_IH[5];
			//System.out.println(Patient_IH+" = Patient ItemHistory_PK");

			ResultPatientCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultPatientCycleEvent.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultPatientCycleEvent, OverallResult);


			//System.out.println(ResultPatient);
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPatientCycleEvent);
			System.out.println(ResultPatient);

		}

		
		//Scan Physician if provided. 
		if(Phy1.equalsIgnoreCase("")){
			ResultPhy1="No Physician provided for Procedure Room.";
			//System.out.println(ResultPhy);
			PhyID="";
		}else {
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Physician", Phy1, "");
			Description="Scan of Physician '" +Phy1+"' is done in "+ Location+".";
			phy_split=Phy1.split("\\(");
			String nicole=phy_split[1];
			phy_split1=nicole.split("\\)");
			PhyID=phy_split1[0];
			
			CycleEvent="Physician";
			Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
			Phy_IH=Staff_IH[0];
			Phy_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			ResultPhyCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultPhyCycleEvent.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultPhyCycleEvent, OverallResult);

			ResultPhy1=IHV.Result_Same_Assoc(PatientAssociationID,Phy_Assoc)+" for Physician that will perform the exam.";
			temp=ResultPhy1.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultPhy1, OverallResult);

			ResultPhy1=ResultPhy1+" ResultPhyCycleEvent="+ResultPhyCycleEvent;
			//System.out.println(ResultPhy);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPhy1);

		}
		//Scan Physician if provided. 
		if(Phy2.equalsIgnoreCase("")){
			ResultPhy2="No Physician provided for Procedure Room.";
			//System.out.println(ResultPhy);
			PhyID="";
		}else {
			UAS.ScannerCount=UAS.ScannerCount+1;
			Res = EM_A.ScanItem(Location, "Staff", "Physician", Phy2, "");
			Description="Scan of Physician '" +Phy2+"' is done in "+ Location+".";
			phy_split=Phy2.split("\\(");
			String nicole=phy_split[1];
			phy_split1=nicole.split("\\)");
			PhyID=phy_split1[0];
			
			CycleEvent="Physician";
			Staff_IH=IHV.GetItemHistoryData(UAS.connstring, Location);
			Phy_IH=Staff_IH[0];
			Phy_Assoc=Staff_IH[1];
			ActualCycleEvent=Staff_IH[5];
			ResultPhyCycleEvent=IHV.Result_CycleEvent(ActualCycleEvent, CycleEvent);
			temp=ResultPhyCycleEvent.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultPhyCycleEvent, OverallResult);

			ResultPhy2=IHV.Result_Same_Assoc(PatientAssociationID,Phy_Assoc)+" for Physician that performed the exam.";
			temp=ResultPhy2.split("-");
			OverallResult=GF.FinalResult(temp[0],ResultPhy2, OverallResult);

			ResultPhy2=ResultPhy2+" ResultPhyCycleEvent="+ResultPhyCycleEvent;
			//System.out.println(ResultPhy);
			Expected=Description;
			IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultPhy2);

		}

		UAS.ScannerCount=UAS.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Exam Type", "", Exam, "");
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
		String ExamType=Staff_IH[0];
		String ExamTypeAssociation=Staff_IH[1];
		String ResultExamType=IHV.Result_Same_Assoc(PatientAssociationID,ExamTypeAssociation)+" for Exam Type associated to patient.";
		Description="Scan Exam Type for the patient.";
		Expected=Description;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultExamType);

		UAS.ScannerCount=UAS.ScannerCount+1;
		Res = EM_A.ScanItem(Location, "Workflow Event", "", "Send To ExamQueue", "");
		System.out.println(Res);
		Description = "Scan of Send to ExamQueue is done with Patient and Exam scanned";
		Staff_IH=IHV.GetItemHistoryNoCycleEvent(UAS.connstring, Location);
		String SendToQAssociation=Staff_IH[1];
		String ResultSendtToQ=IHV.Result_Same_Assoc(PatientAssociationID,SendToQAssociation)+" for send to exam queue associated to patient.";
		Expected=Description;
		IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, ResultSendtToQ);

		
		//DailyDashboard Verification
		if(UAS.PhyScannedInWaiting){
			qvd_v.clickDashBoard();
			DA.clickElement(DBP.examQueueHeaderDDB);
			//Getting the drilldown popup data from database
			Connection conn=null;
			Statement statement=null;
			String stmt="";
			List<String> patID = new ArrayList<String>();
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			statement = conn.createStatement();
			stmt="select distinct (PatientID_FK) from ExamQueue";
			System.out.println("stmt="+stmt);
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				patID.add(RS.getString(1));
			}
			System.out.println ("Need to verify "+patID.size()+" rows in Exam Queue");
			
			for (int eachRec=0;eachRec<patID.size();eachRec++){
				eachPatID=patID.get(eachRec);
				
				stmt="select StaffID from Staff where StaffID_PK in "
						+ "(select Distinct(PhysicianID_FK) from examqueue where patientid_fk="+eachPatID+")";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					if (physicians.length()==0){
						physicians.append(RS.getString(1));
					}else{
						physicians.append (","+RS.getString(1));
					}
				}
				
				stmt="select abbreviation from examtype where examtypeid_pk in "
						+ "(select examtypeid_fk from examqueue where patientid_fk="+eachPatID+")";
				System.out.println("stmt="+stmt);
				RS = statement.executeQuery(stmt);
				while(RS.next()){
					if (exams.length()==0){
						exams.append(RS.getString(1));
					}else{
						exams.append (","+RS.getString(1));
					}
				}
				
				stmt = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
				Statement update1 = conn.createStatement();
	    		System.out.println(stmt);
	    		update1.executeUpdate(stmt);
		    	stmt="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
		    	System.out.println(stmt);
	    		update1.executeUpdate(stmt);
				stmt="Execute dbo.sp_GetPatients_EQUAL '"+ eachPatID+"'";
			  	RS=statement.executeQuery(stmt);
			  	System.out.println("stmt="+stmt);
				while(RS.next()){
			   		patientName = RS.getString(3);
				}
				String colsNvalues="Exam(s)=="+exams.toString()+";patientName=="+patientName+";Physician(s)=="+physicians.toString();
				StringBuffer resultDrillDown=GF.verifyTableContents(DBP.examQueueTableContent,colsNvalues,"patientName");
				System.out.println(resultDrillDown);
				Description="Verifying drill down popup contents of Exam Queue area for Row -"+(eachRec+1);
				Expected =Description;
				IHV.Exec_Log_Result(UAS.XMLFileName, Description, Expected, resultDrillDown.toString());
				exams.setLength(0);
				physicians.setLength(0);
				resultDrillDown.setLength(0);
			}
			qvd_v.clickDashBoard();
			IHV.Exec_Test_Case(UAS.XMLFileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			UAS.TestCaseNumber=UAS.TestCaseNumber+1;
			IHV.Exec_Test_Case(UAS.XMLFileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}

	}
}
