package ScannerMessages.ReprocessingRoom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.graphwalker.core.machine.ExecutionContext;

import TestFrameWork.Unifia_Admin_Selenium;

public class RegressionReprocessingRoom_MRC_GWAPI extends ExecutionContext{

	private TestFrameWork.TestHelper TH;
	private TestFrameWork.Emulator.Emulator_Actions EM_A;
	private TestFrameWork.Emulator.Emulator_Verifications EM_V;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestFrameWork.Emulator.GetIHValues IHV;
	private static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Actions MRC_A;
	private static TestFrameWork.UnifiaAdminReconMRC.MRC_Verification MRC_V;
	
	private Integer Scenario=0;
	static String actualResult="\t\t\t ScanMessages_ReprocessingRoom_MRC_TestSummary \n"; 
	private String Description;
	private String ForFileName;
	private String FileName="";
	private String TestResFileName="ScanMessages_ReprocessingRoom_MRC_TestSummary_";	
	
	private Connection conn= null;
	private String stmt;
	private String ScanStaff="";
	private String StaffFirstName ="", StaffLastName="", ScanStaffID="";
	private ResultSet Staff_rs,Scanner_ID_rs;
	private String MRCMsg;
	private String MRCResult="";
	private Boolean Res;
	private String Scanner;
	private String Reprocessor;
	private int LocationID_PK;
	private int OERSerialNumber;
	private String OERModel;
	
	private String ReprocessorSkipped;
	private String IH[];
	private String ReproInRoomAssoID;
	private String ActualCycleEvent;
	private String ExpectedCycleEvent;
	private String ResultMRCCycleEvent;
	private String ResultReproMRCAsso;
	private String ResultReprocessor;
	private String ResultStaff;
	private String MRCAssocID;
	private String Expected;
	private String StaffAssocID;
	private ResultSet rs;
	List<Integer> IH_ID = new ArrayList<Integer>();
	private String MRCTime="";
	
	public void e_Start(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);
		Unifia_Admin_Selenium.ScannerCount=0;
		FileName="CycleMgmtReprocessingArea_MRC_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
	}
	
	public void e_ReprocessorBarcode() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ReprocessorSkipped="No";
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=10 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=10 and IsActive=1)"; //put sql statement here to find ID
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the LocationID row in the database.
			}		
			Scanner_ID_rs.close();
			System.out.println("Scanner= "+Scanner);
			update.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
			stmt="select L.LocationID_PK, L.Name, A.Model, A.SerialNumber from Location L join AERDetail A on L.LocationID_PK=A.LocationID_FK where L.LocationTypeID_FK=5 and L.IsActive=1 and L.LastUpdatedDateTime=(select MIN(LastUpdatedDateTime) from Location where LocationTypeID_FK=5 and IsActive=1)"; //put sql statement here to find ID
			//System.out.println(stmt);
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1); 
				Reprocessor = Scanner_ID_rs.getString(2); 
				OERModel = Scanner_ID_rs.getString(3);
				OERSerialNumber = Scanner_ID_rs.getInt(4); 
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
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Reprocessor", "", Reprocessor, "");
		//System.out.println(Res);
		Description="Scan of '" +Reprocessor+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MRCPorFailNoRepo() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		ReprocessorSkipped="Yes";
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=10 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=10 and IsActive=1)"; //put sql statement here to find ID
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				Scanner = Scanner_ID_rs.getString(2); //the first variable in the set is the LocationID row in the database.
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
		String[] RandomTestResult = {"MRC Pass","MRC Fail"};
		MRCResult = (RandomTestResult[new Random().nextInt(RandomTestResult.length)]);
		MRCMsg="Please Scan Reprocessor First";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Test Result", "", MRCResult, "");
		//System.out.println(Res);
		Description="Scan of '" +MRCResult+"' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_MRCPass() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		MRCMsg="MRC Test Passed";
		MRCResult="MRC Pass";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(Scanner, "Test Result", "", "MRC Pass", "");
		//System.out.println(Res);
		Description="Scan of 'MRC Pass' is done in "+ Scanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MRCFail() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		MRCMsg = "MRC Failed Follow Your Procedures";
		MRCResult="MRC Fail";
		Unifia_Admin_Selenium.ScannerCount = Unifia_Admin_Selenium.ScannerCount + 1;
		Res = EM_A.ScanItem(Scanner, "Test Result", "", "MRC Fail", "");
		// System.out.println(Res);
		Description = "Scan of 'MRC Fail' is done in " + Scanner;
		actualResult = actualResult + "\r\n" + getCurrentElement().getName()+ "---:\r\n\t" + Description;
		TH.WriteToTextFile(TestResFileName + ForFileName, actualResult);
	}

	public void e_Staff_NoMRC() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanStaffID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
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
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		//System.out.println(Res);
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" ";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select StaffID,FirstName,LastName from Staff where StaffTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Staff Where StaffTypeID_FK=5 and IsActive=1 )"; //put sql statement here to find Staff details
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				ScanStaffID = Staff_rs.getString(1); //the first variable in the set is the StaffID row in the database.
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
		Res = EM_A.ScanItem(Scanner, "Staff", "Tech", ScanStaff, "");
		Description="Scan of Staff '" +ScanStaff+"' is done in "+ Scanner+" ";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reset(){
		System.out.println(getCurrentElement().getName());
		Description="Resetting Variables";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		conn= null;
		stmt=null;
		ScanStaff="";
		StaffFirstName ="";
		StaffLastName="";
		ScanStaffID="";
		MRCMsg=null;
		Res=false;
		Scanner=null;
		Reprocessor=null;
		LocationID_PK=0;
		OERSerialNumber=0;
		OERModel=null;
		ReprocessorSkipped="";
		MRCResult="";
		MRCTime="";
		IH_ID.clear();
	}

	public void e_NoStaffReset(){
		System.out.println(getCurrentElement().getName());
		Description="Resetting Variables";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		conn= null;
		stmt=null;
		ScanStaff="";
		StaffFirstName ="";
		StaffLastName="";
		ScanStaffID="";
		MRCMsg=null;
		Res=false;
		Scanner=null;
		Reprocessor=null;
		LocationID_PK=0;
		OERSerialNumber=0;
		OERModel=null;
		ReprocessorSkipped="";
		MRCResult="";
		MRCTime="";
		IH_ID.clear();
	}

	public void v_ReprocessingArea(){
		System.out.println(getCurrentElement().getName());
		Scenario=Scenario+1;
		System.out.println("-----------------------------");
		System.out.println("Start of new Scenario "+Scenario);
		Description ="=========================================";
		actualResult=actualResult+"\r\n"+Description+"\r\n"+getCurrentElement().getName()+"\r\n\t Start of new Scenario "+Scenario;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		if(Scenario>1){
			IHV.Exec_Test_Case(FileName,"End",Unifia_Admin_Selenium.TestCaseNumber);
			Unifia_Admin_Selenium.TestCaseNumber=Unifia_Admin_Selenium.TestCaseNumber+1;
			IHV.Exec_Test_Case(FileName,"Start",Unifia_Admin_Selenium.TestCaseNumber);
		}
	}
	
	public void v_Reprocessor() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected=Reprocessor+" is Scanned with "+Scanner;
		EM_V.VerifyScanMsg("Reprocessor "+OERModel+" "+OERSerialNumber, Unifia_Admin_Selenium.ScannerCount);
		Description="Scan of "+Reprocessor+" is done in "+Scanner+". ";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description +UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		ExpectedCycleEvent=null;
		IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		ReproInRoomAssoID=IH[1];
		ActualCycleEvent=IH[5];
		if(ActualCycleEvent==null){
			ResultReprocessor="ResultReprocessor= Pass-  Cycle Event is okay";
		}else{
			ResultReprocessor="ResultReprocessor= #Failed!# -  Cycle Event was expected to be:  null; However it was "+ActualCycleEvent;						
		
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultReprocessor);
	}
	
	public void v_MRCTest() throws InterruptedException{
		System.out.println(getCurrentElement().getName());

		EM_V.VerifyScanMsg(MRCMsg, Unifia_Admin_Selenium.ScannerCount);
		Description="Scan of "+MRCResult+" is done in "+Scanner+". ";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+UAS.Result;
		String IHData="Select ItemHistoryID_PK From ItemHistory"+
		" Where ScanItemTypeID_FK=8 and LocationID_FK="+
		"(Select LocationID_PK FROM Location WHERE Name='"+Scanner+"')"+
		" AND LastUpdatedDateTime= "+
		"(Select MAX(LastUpdatedDateTime) FROM ItemHistory Where LocationID_FK=(Select LocationID_PK FROM Location WHERE Name='"+Scanner+"'))";
		try{
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
			Statement statement = conn.createStatement();
			rs=statement.executeQuery(IHData);
			while(rs.next()){
				IH_ID.add(rs.getInt(1));
			}
		}catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		if(IH_ID.size()>1){
			ResultMRCCycleEvent="ResultReproMRCAsso= #Failed!# - A new Association is not created and previous MRC Test Result record also updated due to Bug 9230: Location ReprocessingRoom scanner updates previous MRC Test association record to null if staff is not scanned and same reprocessor is used.";
			actualResult=actualResult+"\r\n\t #Failed!# - A new Association is not created and previous MRC Test Result record also updated due to Bug 9230: Location ReprocessingRoom scanner updates previous MRC Test association record to null if staff is not scanned and same reprocessor is used.";
		}else{
			IH=IHV.GetItemHistoryData(Unifia_Admin_Selenium.connstring, Scanner);
			if(ReprocessorSkipped.equalsIgnoreCase("No")){
				ExpectedCycleEvent="MRC Test";
			}else if(ReprocessorSkipped.equalsIgnoreCase("Yes")){
				ExpectedCycleEvent="";
				ReproInRoomAssoID="0";
			}
			MRCAssocID=IH[1];
			ActualCycleEvent=IH[5];
			ResultMRCCycleEvent="ResultMRCCycleEvent= "+IHV.Result_CycleEvent(ActualCycleEvent, ExpectedCycleEvent)+" for MRC Test Result in "+Scanner;
			ResultReproMRCAsso=IHV.Result_Same_Assoc(ReproInRoomAssoID, MRCAssocID);
			ResultMRCCycleEvent="ResultReproMRCAsso= "+ResultReproMRCAsso+" for MRC Test Result Scan and Scan of "+Reprocessor+" with "+Scanner+". "+ResultMRCCycleEvent;
			if(ReprocessorSkipped.equalsIgnoreCase("No")){
				MRCTime=MRC_A.GetMRCTime(Unifia_Admin_Selenium.connstring, MRCAssocID);
			}
		}
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultMRCCycleEvent);
		
		//MRC verification
		if(ReprocessorSkipped.equalsIgnoreCase("No")){
			IP_A.Click_InfectionPrevention();
			MRC_A.Click_MRCRecordManagement();
			String MRCDateTime[]=MRCTime.split(" ");
			String MRCDate=MRCDateTime[0];
			IP_A.DateFilter(MRCDate, MRCDate);
			IP_A.ApplyMRCFilter();
			Thread.sleep(2000);
			String gridID=MRC_A.getMRCGridID(MRCTime);
			Description="Verify MRC screen for MRC Result of "+Scanner;
			Expected=Description;
			String MRCresult[]=MRCResult.split(" ");//Taking Pass/Fail from Leak Test Pass/Leak Test Fail
			ScanStaffID=" ";
			String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(gridID,"MRC TEST DATE/TIME=="+MRCTime+";REPROCESSOR=="+Reprocessor+";MRC Test Result=="+MRCresult[1]+";MRC TEST STAFF ID=="+ScanStaffID).toString();
			actualResult=actualResult+result_MRC;
			IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
		}
	}
	
	public void v_Staff() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Expected=ScanStaffID+" is Scanned with "+Scanner;
		String Msg="";
		if(ReprocessorSkipped.equalsIgnoreCase("No")){
			Msg="Staff "+ScanStaffID+" Scanned";
		}else if(ReprocessorSkipped.equalsIgnoreCase("Yes")){
			Msg="Please Scan Reprocessor First";
		}
		EM_V.VerifyScanMsg(Msg, Unifia_Admin_Selenium.ScannerCount);
		Description="Scan of "+ScanStaffID+" is done in "+Scanner+". ";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description +UAS.Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		IH=IHV.GetItemHistoryNoCycleEvent(Unifia_Admin_Selenium.connstring, Scanner);
		StaffAssocID=IH[1];
		if(ReprocessorSkipped.equalsIgnoreCase("No")){
			ResultStaff="ResultStaff= "+IHV.Result_Same_Assoc(ReproInRoomAssoID, StaffAssocID)+" for Staff Scan with "+Scanner+" and Scan of "+Reprocessor+" with "+Scanner+". ";
		}else if(ReprocessorSkipped.equalsIgnoreCase("Yes")){
			ResultStaff="ResultStaff= "+IHV.Result_Same_Assoc("0", StaffAssocID)+" for Staff Scan with "+Scanner+" and Scan of "+MRCResult+" with "+Scanner+". ";
		}
		IHV.Exec_Log_Result(FileName, Description, Expected, ResultStaff);
		
		//MRC verification
		if(!MRCResult.equalsIgnoreCase("")&&(ReprocessorSkipped.equalsIgnoreCase("No"))){
			IP_A.Click_InfectionPrevention();
			MRC_A.Click_MRCRecordManagement();
			String MRCDateTime[]=MRCTime.split(" ");
			String MRCDate=MRCDateTime[0];
			IP_A.DateFilter(MRCDate, MRCDate);
			IP_A.ApplyMRCFilter();
			Thread.sleep(2000);
			String gridID=MRC_A.getMRCGridID(MRCTime);
			Description="Verify MRC screen for MRC Result of "+Scanner;
			Expected=Description;
			String MRCresult[]=MRCResult.split(" ");//Taking Pass/Fail from Leak Test Pass/Leak Test Fail
			
			String result_MRC="MRC Result ="+MRC_V.verifyMRCDetails(gridID,"MRC TEST DATE/TIME=="+MRCTime+";REPROCESSOR=="+Reprocessor+";MRC Test Result=="+MRCresult[1]+";MRC TEST STAFF ID=="+ScanStaffID).toString();
			actualResult=actualResult+result_MRC;
			IHV.Exec_Log_Result(FileName, Description, Expected, result_MRC);
		}
		
	}
}
