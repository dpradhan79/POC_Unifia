package Regression.MultiRoom;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;

import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.machine.ExecutionContext;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;

public class MultiRoomController_API extends ExecutionContext{
	public 	TestFrameWork.Emulator.GetIHValues IHV;
	public TestFrameWork.TestHelper TH;
	public GeneralFunc gf;
	public TestFrameWork.Emulator.Emulator_Actions EM_A;

	public String TestResFileName="Regression_MultiRoom_TestSummary_";
	public String Description;
	public String ForFileName;
	public String FileName;
	public String Expected;
	public String actualResult="\t\t\t Regression_MultiRoom_TestSummary \n"; 
	public static String SoiledScope1Name;
	public static String SoiledScope1SerialNo;
	public static String SoiledScope1Association="";
	public static int SoiledScope1ScopeID;
	public static String SoiledScope2Name;
	public static String SoiledScope2SerialNo;
	public static String SoiledScope2Association="";
	public static int SoiledScope2ScopeID;
	public static String SoiledScanner1;
	public static String SoiledScanner2;
	public int ScopeID;
	public int LocationID_PK;

	public static String ReproScope1Name;
	public static String ReproScope1SerialNo;
	public static String ReproScope2Name;
	public static String ReproScope2SerialNo;
	public static String ReproScanner="";
	public static int ReproScope1ScopeID;
	public static int ReproScope2ScopeID;

	public static String PRScope1Name;
	public static String PRScope1SerialNo;
	public static String PRScope2Name;
	public static String PRScope2SerialNo;
	public static String PRScanner="";
	public String Room="";
	public static int PRScope1AssociationID, PRScope1CycleID,Scope1ReproOut_AssociationID;
	public static int PRScope2AssociationID, PRScope2CycleID,Scope2ReproOut_AssociationID;
	public static String Scope1HangTime,Scope2HangTime;
	public ResultSet Repro_rs;
	
	public static String StorageInScope1Name;
	public static String StorageInScope1SerialNo;
	public static String StorageInScope1Association="";
	public static int StorageInScope1ScopeID, StorageInScope1OtherState;
	public static String StorageInScope2Name;
	public static String StorageInScope2SerialNo;
	public static String StorageInScope2Association="";
	public static int StorageInScope2ScopeID, StorageInScope2OtherState, Storage2NumCabinets;
	public static String StorageInScanner1;
	public static String StorageInScanner2;
	
	public static String StorageOutScope1Name;
	public static String StorageOutScope1SerialNo;
	public static String StorageOutScope1Association="";
	public static String StorageOut1HangTime;
	public static int StorageOutScope1ScopeID,StorageOutScope1CycleID,StorageOutScope1OtherStateID,StorageOut1ReproOut_AssociationID;
	public static String StorageOutScope2Name;
	public static String StorageOutScope2SerialNo;
	public static String StorageOutScope2Association="";
	public static String StorageOut2HangTime;
	public static int StorageOutCultureAssociationID,StorageOutScope2CycleID,StorageOutScope2OtherStateID,StorageOut2ReproOut_AssociationID;
	public static String StorageOutScanner1;
	public static String StorageOutScanner2;
	public ResultSet CultureAssociation;

	public static String CultureScope1Name;
	public static String CultureScope1SerialNo;
	public static String CultureScope1Association="";
	public static String CultureScanner1;

	public Connection conn= null;
	public ResultSet Scope_rs;  //result set used to get ScopeName from the Test DB
	public ResultSet Staff_rs;
	public ResultSet Scanner_ID_rs;
	public ResultSet MCStart_rs;
	public ResultSet MCEnd_rs;
	public String stmt1,stmt; //used for SQL queries


	public void e_Start() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date); 
		FileName="CycleMgmt_MultiRoom_Regression_";
		FileName=IHV.Start_Exec_Log(FileName);
		Unifia_Admin_Selenium.XMLFileName=FileName;
		Unifia_Admin_Selenium.TestCaseNumber=1;
		Unifia_Admin_Selenium.ScannerCount=0;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		ReproScanner="";

		Room="Available";
		PRScanner="Procedure Room 1";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		boolean Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		PRScanner="Procedure Room 2";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		PRScanner="Procedure Room 3";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		PRScanner="Procedure Room 5";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		PRScanner="Procedure Room 6";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		PRScanner="Procedure Room 7";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		PRScanner="Procedure Room 8";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		PRScanner="Procedure Room 9";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PRScanner, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PRScanner;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		PRScanner="";

	}
	
	
	public void v_Controller1(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void v_Controller2(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void v_Main(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Nav1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav2(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav3(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav4(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav5(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav6(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav7(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav8(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav9(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav10(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav11(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav12(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav13(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav14(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav15(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav16(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav17(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav18(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav19(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav20(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav21(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav22(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav23(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav24(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav25(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav26(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav27(){ 
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Nav28(){//available
		//System.out.println(getCurrentElement().getName());
	}


	//for Soiled
	public void e_Scope1InSoiled(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scope1LT(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scope1MCStarted(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scp1_AwaitingRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_ResetScp1(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scope2InSoiled(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scope2LT(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scope2MCStarted(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_ResetScp2(){
		//System.out.println(getCurrentElement().getName());
	}
	
	//for Reprocessing 
	public void e_Scope1InRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scope2InRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_MRC(){ 
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scope1OutRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void e_Scope2OutRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ResetRepro1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ResetRepro2(){
		//System.out.println(getCurrentElement().getName());
		ReproScanner="";
	}
	public void e_ResetRepro3(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Debug2(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope1IntoRepro1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope1IntoRepro2(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope1IntoRepro3(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2IntoRepro1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2IntoRepro2(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2IntoRepro3(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2IntoRepro4(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scp1MRC(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scp2MRC(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ScanScp1Out(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ScanScp2Out(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ScanScp2Out2(){
		//System.out.println(getCurrentElement().getName());
	}

	//For Procedure Room  
	public void e_Scope1IntoPR1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope1InPR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2IntoPR1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2InPR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_PatientIntoPR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Phys_IntoPR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_PatientIn(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_PhyIn(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ScanProcStart(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ProcStarted(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ScanEndProc(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ProcEnd(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope1OutPR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2OutPR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope1WaitingMC(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2WaitingMC(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ScanNeedsCleaning(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_Scope2OutPR2(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_PRNeedsCleaning(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ScanPRAvailable(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_ScanPRAvailable2(){
		//System.out.println(getCurrentElement().getName());
	}
	public void e_PRReset(){
		//System.out.println(getCurrentElement().getName());
		PRScanner="";
	}
	
	//Storage Area
	public void e_Scope1IntoStorage(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope1InStorage(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope1IntoStorageKE(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope1InStorageKE(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope1CheckoutCulture(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope1CheckedOut(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope2IntoStorage(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope2InStorage(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope2CheckoutNoCulture(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope2CheckedOut(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_ResetStorageIn1(){
		//System.out.println(getCurrentElement().getName());	
		StorageInScanner1="";
	}
	public void e_ResetStorageIn2(){
		//System.out.println(getCurrentElement().getName());	
		StorageInScanner2="";
	}
	public void e_ResetStorageOut1(){
		//System.out.println(getCurrentElement().getName());		
		StorageOutScanner1="";
	}
	public void e_ResetStorageOut2(){
		//System.out.println(getCurrentElement().getName());		
		StorageOutScanner2="";
	}
	//Culture obtained
	public void e_Scope1ObtainCulture(){
		//System.out.println(getCurrentElement().getName());		
	}
	public void e_Scope1CultureObtained(){
		//System.out.println(getCurrentElement().getName());		
	}


	/**
	 * SHARED e_ScanNeedsCleaning
	 */
	//For Soiled
	public void v_Soiled_Scp1_In(){
		//System.out.println(getCurrentElement().getName());
		SoiledScope1Name="";
		SoiledScope1SerialNo="";
		SoiledScanner1="";
   		try{ 
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			//stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; //put sql statement here to find ScopeName
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=3 and Stat.LocationID_FK in (21,22,23,24,25,26,27,28,29) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=3 and LocationID_FK in (21,22,23,24,25,26,27,28,29))";
    		//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				SoiledScope1Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
				SoiledScope1SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
				SoiledScope1ScopeID = Scope_rs.getInt(3);

			}		
			Scope_rs.close();
			//System.out.println("SoiledScope1Name = "+SoiledScope1Name);
			if(SoiledScope1Name.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=5 and Stat.OtherScopeStateID_FK Is Null and Stat.LocationID_FK in (31,32,33,34,51,52,53,54,55,56) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and OtherScopeStateID_FK Is Null and LocationID_FK in (31,32,33,34,51,52,53,54,55,56))";
	    		//System.out.println(stmt);
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					SoiledScope1Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
					SoiledScope1SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
					SoiledScope1ScopeID = Scope_rs.getInt(3);
				}		
				Scope_rs.close();
				//System.out.println("SoiledScope1Name = "+SoiledScope1Name);
			}
			update.execute("Update Scope set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_PK='"+SoiledScope1ScopeID+"';");
			update.close();
			statement.close();
			stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=4 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
			//System.out.println(stmt);
			statement = conn.createStatement();
			update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				SoiledScanner1 = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
			}		
			Scanner_ID_rs.close();
			//System.out.println("SoiledScanner1= "+SoiledScanner1);
			update.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
			conn.close();
   		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}

	}

	public void v_Scp1_LT(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp1_MCStart(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp1_MCEnd(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp1_InSoiled(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp1_LT_Done(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp1_MCStarted(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp1_Await_Repro(){
		//System.out.println(getCurrentElement().getName());
	}
	

	public void v_Soiled_Scp2_In(){
		//System.out.println(getCurrentElement().getName());
		SoiledScope2Name="";
		SoiledScope2SerialNo="";
		SoiledScanner2="";
   		try{ 
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			//stmt="select Name, SerialNumber, ScopeID_PK from Scope where IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Scope where IsActive=1)"; //put sql statement here to find ScopeName
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=3 and Stat.LocationID_FK in (21,22,23,24,25,26,27,28,29) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=3 and LocationID_FK in (21,22,23,24,25,26,27,28,29))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				SoiledScope2Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
				SoiledScope2SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
				SoiledScope2ScopeID = Scope_rs.getInt(3);

			}		
			Scope_rs.close();
			//System.out.println("SoiledScope2Name = "+SoiledScope2Name);
			if(SoiledScope2Name.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=5 and Stat.OtherScopeStateID_FK Is Null and Stat.LocationID_FK in (31,32,33,34,51,52,53,54,55,56) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and OtherScopeStateID_FK Is Null and LocationID_FK in (31,32,33,34,51,52,53,54,55,56))";
	    		//System.out.println(stmt);
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					SoiledScope2Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
					SoiledScope2SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
					SoiledScope2ScopeID = Scope_rs.getInt(3);
				}		
				Scope_rs.close();
				//System.out.println("SoiledScope2Name = "+SoiledScope2Name);
			}
			update.execute("Update Scope set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_PK='"+SoiledScope2ScopeID+"';");
			update.close();
			statement.close();
			stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=4 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=4 and IsActive=1)"; //put sql statement here to find ID
			//System.out.println(stmt);
			statement = conn.createStatement();
			update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				SoiledScanner2 = Scanner_ID_rs.getString(2); //the first variable in the set is the ScannerName row in the database.
			}		
			Scanner_ID_rs.close();
			//System.out.println("SoiledScanner2= "+SoiledScanner2);
			update.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");

			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}

	public void v_Scp2_LT(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_MCStart(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_MCEnd(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_InSoiled(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_LT_Done(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_MCStarted(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_Await_Repro(){
		//System.out.println(getCurrentElement().getName());
	}
	
	//For Reprocessing   
	public void v_Repro_Scp1_In(){
		//System.out.println(getCurrentElement().getName());
		ReproScope1Name="";
		ReproScope1SerialNo="";
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope "
					+ "join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
					+ "where Stat.ScopeStateID_FK =2 and Stat.OtherScopeStateID_FK Is NULL and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) "
					+ "from ScopeStatus where ScopeStateID_FK =2 and OtherScopeStateID_FK Is NULL )";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ReproScope1Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				ReproScope1SerialNo = Scope_rs.getString(2);
				ReproScope1ScopeID = Scope_rs.getInt(3);
			}		
			Scope_rs.close();
			//System.out.println("ReproScope1Name= "+ReproScope1Name);
			if(ReproScope1Name.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=5 and Stat.OtherScopeStateID_FK Is Null and Stat.LocationID_FK in (31,32,33,34,51,52,53,54,55,56) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and OtherScopeStateID_FK Is Null and LocationID_FK in (31,32,33,34,51,52,53,54,55,56))";
	    		//System.out.println(stmt);
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					ReproScope1Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
					ReproScope1SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
					ReproScope1ScopeID = Scope_rs.getInt(3);
				}		
				Scope_rs.close();
				//System.out.println("ReproScope1Name = "+ReproScope1Name);
			}
			update.execute("Update Scope set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_PK='"+ReproScope1ScopeID+"';");
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
		if(ReproScanner.equalsIgnoreCase("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=5 and IsActive=1)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					ReproScanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
				}		
				Scanner_ID_rs.close();
				//System.out.println("ReproScanner= "+ReproScanner);
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
	}

	public void v_Scp1_InRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Repro_Scp2_In(){
		//System.out.println(getCurrentElement().getName());
		ReproScope2Name="";
		ReproScope2SerialNo="";
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope "
					+ "join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
					+ "where Stat.ScopeStateID_FK =2 and Stat.OtherScopeStateID_FK Is NULL and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) "
					+ "from ScopeStatus where ScopeStateID_FK =2 and OtherScopeStateID_FK Is NULL )";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				ReproScope2Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				ReproScope2SerialNo = Scope_rs.getString(2);
				ReproScope2ScopeID = Scope_rs.getInt(3);
			}		
			Scope_rs.close();
			//System.out.println("ReproScope2Name= "+ReproScope2Name);
			if(ReproScope2Name.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=5 and Stat.OtherScopeStateID_FK Is Null and Stat.LocationID_FK in (31,32,33,34,51,52,53,54,55,56) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and OtherScopeStateID_FK Is Null and LocationID_FK in (31,32,33,34,51,52,53,54,55,56))";
	    		//System.out.println(stmt);
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					ReproScope2Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
					ReproScope2SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
					ReproScope2ScopeID = Scope_rs.getInt(3);
				}		
				Scope_rs.close();
				//System.out.println("ReproScope2Name = "+ReproScope2Name);
			}
			update.execute("Update Scope set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_PK='"+ReproScope2ScopeID+"';");
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
		if(ReproScanner.equalsIgnoreCase("")){
	   		try{ 
				conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
				stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=5 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=5 and IsActive=1)"; //put sql statement here to find ID
				//System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Scanner_ID_rs = statement.executeQuery(stmt);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					ReproScanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
				}		
				Scanner_ID_rs.close();
				//System.out.println("ReproScanner= "+ReproScanner);
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
	}
	public void v_Scp2_InRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_BothScopesIn(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Repro_MRC(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_MRC_Done(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Repro_Scp1_Out(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp1_OutRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Repro_Scp2_Out(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scp2_OutRepro(){
		//System.out.println(getCurrentElement().getName());
	}		
	public void v_ResetRepro(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_BothScopesOut1(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_BothScopesOut2(){
		//System.out.println(getCurrentElement().getName());
	}
	
	//For Procedure Room 
	public void v_PR_Scp1_In(){
		//System.out.println(getCurrentElement().getName());
		PRScope1Name="";
		PRScope1SerialNo="";
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
					+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
					+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (31,32,33,34) "
					+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK =5 "
					+ "and OtherScopeStateID_FK Is NULL and LocationID_FK in (31,32,33,34))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				PRScope1Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				PRScope1SerialNo = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				PRScope1AssociationID = Scope_rs.getInt(4);
				PRScope1CycleID= Scope_rs.getInt(5);
			}		
			Scope_rs.close();
			//System.out.println("PRScope1Name= "+PRScope1Name);
			//System.out.println("PRScope1AssociationID= "+PRScope1AssociationID);
			
			if(PRScope1SerialNo.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
						+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
						+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (51,52,53,54,55,56) "
						+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 "
						+ "and OtherScopeStateID_FK Is NULL and LocationID_FK in (51,52,53,54,55,56))";
				//System.out.println(stmt);
				statement = conn.createStatement();
				update = conn.createStatement();
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					PRScope1Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
					PRScope1SerialNo = Scope_rs.getString(2);
					ScopeID = Scope_rs.getInt(3);
					PRScope1AssociationID = Scope_rs.getInt(4);
					PRScope1CycleID= Scope_rs.getInt(5);
				}		
				Scope_rs.close();
			}
			
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();
			
			//Get the hangTime
			stmt1="Select DateDiff(hh, IH.ReceivedDateTime, GETUTCDATE())/24 AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK "
					+ " where ScopeCycle.ScopeID_FK="+ScopeID+" and ScopeCycle.CycleID="+PRScope1CycleID+" and IH.CycleEventID_FK=18";
	
			//System.out.println(stmt1);
			Statement statement1 = conn.createStatement();
			Repro_rs = statement1.executeQuery(stmt1);
			while(Repro_rs.next()){
				Scope1HangTime = Repro_rs.getString(1); //the first variable in the set is the ID row in the database.
				Scope1ReproOut_AssociationID = Repro_rs.getInt(2);
			}
			Repro_rs.close();
			//System.out.println("Scope1HangTime="+Scope1HangTime);
			//System.out.println("Scope1ReproOut_AssociationID= "+Scope1ReproOut_AssociationID);
			statement1.close();
			
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
			try{ //Get a value that exists in Unifia to modify.
			
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=2 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)"; //put sql statement here to find ID
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				PRScanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
			}		
			Scanner_ID_rs.close();
			//System.out.println("Scanner= "+PRScanner);
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

	}		
	public void v_Scp1_InPR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_PR_Scp2_In(){
		//System.out.println(getCurrentElement().getName());
		PRScope2Name="";
		PRScope2SerialNo="";
		try{ 
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
					+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
					+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (31,32,33,34) "
					+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK =5 "
					+ "and OtherScopeStateID_FK Is NULL and LocationID_FK in (31,32,33,34))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				PRScope2Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				PRScope2SerialNo = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				PRScope2AssociationID = Scope_rs.getInt(4);
				PRScope2CycleID= Scope_rs.getInt(5);
			}		
			Scope_rs.close();
			//System.out.println("PRScope2Name= "+PRScope2Name);
			//System.out.println("PRScope2AssociationID= "+PRScope2AssociationID);
			if(PRScope2SerialNo.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.AssociationID_FK, Stat.CycleCount from Scope join "
						+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK "
						+ "where Stat.ScopeStateID_FK =5 and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (51,52,53,54,55,56) "
						+ "and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 "
						+ "and OtherScopeStateID_FK Is NULL and LocationID_FK in (51,52,53,54,55,56))";
				//System.out.println(stmt);
				statement = conn.createStatement();
				update = conn.createStatement();
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					PRScope2Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
					PRScope2SerialNo = Scope_rs.getString(2);
					ScopeID = Scope_rs.getInt(3);
					PRScope2AssociationID = Scope_rs.getInt(4);
					PRScope2CycleID= Scope_rs.getInt(5);
				}		
				Scope_rs.close();
			}
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();
			
			//Get the hangTime
			stmt1="Select DateDiff(hh, IH.ReceivedDateTime, GETUTCDATE())/24 AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK "
					+ " where ScopeCycle.ScopeID_FK="+ScopeID+" and ScopeCycle.CycleID="+PRScope1CycleID+" and IH.CycleEventID_FK=18";
	
			//System.out.println(stmt1);
			Statement statement1 = conn.createStatement();
			Repro_rs = statement1.executeQuery(stmt1);
			while(Repro_rs.next()){
				Scope2HangTime = Repro_rs.getString(1); //the first variable in the set is the ID row in the database.
				Scope2ReproOut_AssociationID = Repro_rs.getInt(2);
			}
			Repro_rs.close();
			//System.out.println("Scope2HangTime="+Scope2HangTime);
			//System.out.println("Scope2ReproOut_AssociationID= "+Scope2ReproOut_AssociationID);
			statement1.close();
			
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
			try{ //Get a value that exists in Unifia to modify.
			
			conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			stmt="select LocationID_PK, Name from Location where LocationTypeID_FK=2 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=2 and IsActive=1)"; //put sql statement here to find ID
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scanner_ID_rs = statement.executeQuery(stmt);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				PRScanner = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
			}		
			Scanner_ID_rs.close();
			//System.out.println("Scanner= "+PRScanner);
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
	}
	public void v_Scp2_InPR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_BothScopesInPR(){
		//System.out.println(getCurrentElement().getName());
	}		
	public void v_PatientInto_PR(){
		//System.out.println(getCurrentElement().getName());
	}		
	public void v_PatientIn_PR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_PatientScanned(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Phy_Into_PR(){
		//System.out.println(getCurrentElement().getName());
	}		
	public void v_Phy_In_PR(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Patient_Phy_InPR(){
		//System.out.println(getCurrentElement().getName());
	}		
	public void v_ProcStart(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_ProcStarted(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_EndProc(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_ProcEnded(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Nav2(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Nav3(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Nav4(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Nav5(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Nav6(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_PR_Scp1_Out(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scope1AwaitingMC(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_PR_Scp2_Out(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scope2AwaitingMC(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_PR_NeedsCleaning(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_PRCleaning(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_PR_Available(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_PR_Reset(){
		//System.out.println(getCurrentElement().getName());
	}
	
	//Storage Area  
	public void v_Scope1IntoStorageSingle(){
		//System.out.println(getCurrentElement().getName());
		StorageInScope1Name="";
		StorageInScope1SerialNo="";
		try{ //Get a value that exists in Unifia to modify.
			//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	

			stmt="select Scope.Name, Scope.SerialNumber, Status.ScopeID_FK, Status.OtherScopeStateID_FK from Scope join ScopeStatus Status on "
					+ "Scope.ScopeID_PK=Status.ScopeID_FK where Status.ScopeStateID_FK=5 and Status.LocationID_FK in (51,52,53,54,55,56,81) and "
					+ "status.LastUpdatedDateTime=(Select Min(SS.LastUpdatedDateTime) from ScopeStatus SS where ScopeStateID_FK=5 and "
					+ "SS.LocationID_FK in (51,52,53,54,55,56,81))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				StorageInScope1Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				StorageInScope1SerialNo = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				StorageInScope1OtherState=Scope_rs.getInt(4);
			}		
			Scope_rs.close();
			//System.out.println("StorageInScope1Name= "+StorageInScope1Name);
			
			if(StorageInScope1Name.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=5 and Stat.LocationID_FK in (31,32,33,34) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and LocationID_FK in (31,32,33,34))";
	    		//System.out.println(stmt);
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					StorageInScope1Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
					StorageInScope1SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
					ScopeID = Scope_rs.getInt(3);
					StorageInScope1OtherState=Scope_rs.getInt(4);
				}		
				Scope_rs.close();
				//System.out.println("StorageInScope1Name = "+StorageInScope1Name);
			}
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();
			
			Statement statement1 = conn.createStatement();
			stmt1="select LocationID_PK, Name from Location where LocationTypeID_FK=3 and StorageCabinetCount=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=3 and StorageCabinetCount=1 and IsActive=1)";
			//System.out.println(stmt1);
			Statement update1 = conn.createStatement();

			Scanner_ID_rs= statement1.executeQuery(stmt1);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				StorageInScanner1 = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
			}		
			Scanner_ID_rs.close();
			//System.out.println("StorageInScanner1="+StorageInScanner1);
			update1.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
			update1.close();
			statement1.close();

			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		
	}
	public void v_Scope1InStorage(){
		//System.out.println(getCurrentElement().getName());

	}
	public void v_Scope1InStorageKE(){
		//System.out.println(getCurrentElement().getName());

	}


	
	public void v_Scope1CheckoutCulture() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		StorageOutScope1Name="";
		StorageOutScope1SerialNo="";
		StorageOutScanner1="";
		try{ //Get a value that exists in Unifia to modify.
			//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	

			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK "
					+ "join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK IS NULL and Stat.OtherScopeStateID_FK=7 "
					+ "and Stat.LocationID_FK in (31,32,33,34) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus "
					+ "where ScopeStateID_FK IS NULL and OtherScopeStateID_FK=7 and LocationID_FK in (31,32,33,34))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				StorageOutScope1Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				StorageOutScope1SerialNo = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				StorageOutScanner1=Scope_rs.getString(4);
			}		
			Scope_rs.close();
			//System.out.println("StorageOutScope1Name= "+StorageOutScope1Name);
			//System.out.println("StorageOutScanner1= "+StorageOutScanner1);
			
			if(StorageOutScope1Name.equalsIgnoreCase("")){
				//if there are not any scopes checked into the storage area awaiting culture results, then get a scope that is in transit and obtain a culture and check it into the storage area. 
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=5 and Stat.OtherScopeStateID_FK is null and Stat.LocationID_FK in (31,32,33,34) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and OtherScopeStateID_FK is null and LocationID_FK in (31,32,33,34))";
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					StorageOutScope1Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
					StorageOutScope1SerialNo = Scope_rs.getString(2);
					ScopeID = Scope_rs.getInt(3);
				}		
				Scope_rs.close();
				if(StorageOutScope1Name.equalsIgnoreCase("")){
					stmt="No scopes available to scan culture results ";
					System.out.println(stmt);

				}
				Statement statement1 = conn.createStatement();
				stmt1="select LocationID_PK, Name from Location where LocationTypeID_FK=9 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=9 and IsActive=1)";
				//System.out.println(stmt1);
				Statement update1 = conn.createStatement();

				Scanner_ID_rs= statement1.executeQuery(stmt1);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					CultureScanner1 = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
				}		
				Scanner_ID_rs.close();
				//System.out.println("CultureScanner1="+CultureScanner1);
				update1.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
				update1.close();
				statement1.close();
				Unifia_Admin_Selenium.ScannerCount++;

				boolean Res = EM_A.ScanItem(CultureScanner1, "Scope", "", StorageOutScope1Name, "");
				Thread.sleep(1200);
				statement1 = conn.createStatement();
				stmt1="select LocationID_PK, Name from Location where LocationTypeID_FK=3 and StorageCabinetCount=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=3 and StorageCabinetCount=1 and IsActive=1)";
				//System.out.println(stmt1);
				update1 = conn.createStatement();

				Scanner_ID_rs= statement1.executeQuery(stmt1);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					StorageInScanner1 = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
				}		
				Scanner_ID_rs.close();
				//System.out.println("StorageInScanner1="+StorageInScanner1);
				update1.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
				update1.close();
				statement1.close();
				Unifia_Admin_Selenium.ScannerCount++;
				Res = EM_A.ScanItem(StorageInScanner1, "Scope", "", StorageOutScope1Name, "");
				Thread.sleep(1200);
				StorageOutScanner1=StorageInScanner1;
			}
			
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			
			stmt1="select AssociationID_FK from ItemHistory where ScanItemID_FK="+ScopeID+" and CycleEventID_FK=30";
			//System.out.println(stmt1);

			CultureAssociation=statement.executeQuery(stmt1);
			while(CultureAssociation.next()){
				StorageOutCultureAssociationID = CultureAssociation.getInt(1);
			}
			update.close();
			statement.close();
			//System.out.println("StorageOutCultureAssociationID= "+StorageOutCultureAssociationID);
					
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}

	}
	

	public void v_Scope1CheckedOut(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scope2IntoStorageMultiple(){
		//System.out.println(getCurrentElement().getName());
		StorageInScope2Name="";
		StorageInScope2SerialNo="";
		StorageInScanner2="";
		Storage2NumCabinets=0;
		try{ //Get a value that exists in Unifia to modify.
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	

			stmt="select Scope.Name, Scope.SerialNumber, Status.ScopeID_FK, Status.OtherScopeStateID_FK from Scope join ScopeStatus Status on "
					+ "Scope.ScopeID_PK=Status.ScopeID_FK where Status.ScopeStateID_FK=5 and Status.LocationID_FK in (51,52,53,54,55,56,81) and "
					+ "status.LastUpdatedDateTime=(Select Min(SS.LastUpdatedDateTime) from ScopeStatus SS where ScopeStateID_FK=5 and "
					+ "SS.LocationID_FK in (51,52,53,54,55,56,81))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				StorageInScope2Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				StorageInScope2SerialNo = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				StorageInScope2OtherState=Scope_rs.getInt(4);
			}		
			Scope_rs.close();
			//System.out.println("StorageInScope2Name= "+StorageInScope2Name);
			if(StorageInScope2Name.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.OtherScopeStateID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=5 and Stat.LocationID_FK in (31,32,33,34) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and LocationID_FK in (31,32,33,34))";
	    		//System.out.println(stmt);
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					StorageInScope2Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
					StorageInScope2SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
					ScopeID = Scope_rs.getInt(3);
					StorageInScope2OtherState=Scope_rs.getInt(4);
				}		
				Scope_rs.close();
				//System.out.println("StorageInScope2Name = "+StorageInScope2Name);
			}
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();

			Statement statement1 = conn.createStatement();
			stmt1="select LocationID_PK, Name, StorageCabinetCount from Location where LocationTypeID_FK=3 and StorageCabinetCount!=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=3 and StorageCabinetCount!=1 and IsActive=1)";
			//System.out.println(stmt1);
			Statement update1 = conn.createStatement();

			Scanner_ID_rs= statement1.executeQuery(stmt1);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				StorageInScanner2 = Scanner_ID_rs.getString(2); 
				Storage2NumCabinets=Scanner_ID_rs.getInt(3);
			}		
			Scanner_ID_rs.close();
			//System.out.println("StorageInScanner2="+StorageInScanner2);
			//System.out.println("Storage2NumCabinets="+Storage2NumCabinets);

			update1.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
			update1.close();
			statement1.close();

			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}
	public void v_Scope2InStorage(){
		//System.out.println(getCurrentElement().getName());
	}
	public void v_Scope2CheckoutNoCulture() throws InterruptedException{
		//System.out.println(getCurrentElement().getName());
		StorageOutScope2Name="";
		StorageOutScope2SerialNo="";
		StorageOutScanner2="";
		try{ //Get a value that exists in Unifia to modify.
			//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	

			stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Loc.Name, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join "
					+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where "
					+ "Stat.ScopeStateID_FK IS NULL and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (31,32,33,34) and "
					+ "Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK IS NULL and "
					+ "OtherScopeStateID_FK Is NULL and LocationID_FK in (31,32,33,34))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				StorageOutScope2Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				StorageOutScope2SerialNo = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
				StorageOutScanner2=Scope_rs.getString(4);
				StorageOutScope2CycleID= Scope_rs.getInt(5);
				StorageOutScope2OtherStateID=Scope_rs.getInt(6);
			}		
			Scope_rs.close();
			//System.out.println("StorageOutScope2Name= "+StorageOutScope2Name);
			//System.out.println("StorageOutScanner2= "+StorageOutScanner2);
			
			if(StorageOutScope2Name.equalsIgnoreCase("")){
				//if there are no scopes checked into the cabinet without culture results, then scan a scope that was reprocessed into a cabinet. 
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK, Stat.CycleCount, Stat.OtherScopeStateID_FK from Scope join "
						+ "ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where "
						+ "Stat.ScopeStateID_FK=5 and Stat.OtherScopeStateID_FK Is NULL and Stat.LocationID_FK in (51,52,53,54,55,56,31,32,33,34) and "
						+ "Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and "
						+ "OtherScopeStateID_FK Is NULL and LocationID_FK in (51,52,53,54,55,56,31,32,33,34))";
				//System.out.println(stmt);
				statement = conn.createStatement();
				update = conn.createStatement();
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					StorageOutScope2Name = Scope_rs.getString(1); 
					StorageOutScope2SerialNo = Scope_rs.getString(2);
					ScopeID = Scope_rs.getInt(3);
					StorageOutScope2CycleID= Scope_rs.getInt(4);
					StorageOutScope2OtherStateID=Scope_rs.getInt(5);
				}		
				Scope_rs.close();
				if(StorageOutScope2Name.equalsIgnoreCase("")){
					stmt="no scopes available";
				}
				Statement statement1 = conn.createStatement();
				stmt1="select LocationID_PK, Name from Location where LocationTypeID_FK=3 and StorageCabinetCount=1 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=3 and StorageCabinetCount=1 and IsActive=1)";
				//System.out.println(stmt1);
				Statement update1 = conn.createStatement();
	
				Scanner_ID_rs= statement1.executeQuery(stmt1);
				while(Scanner_ID_rs.next()){
					LocationID_PK = Scanner_ID_rs.getInt(1);
					StorageOutScanner2 = Scanner_ID_rs.getString(2); 
				}		
				Scanner_ID_rs.close();
				//System.out.println("Location="+Location);
				update1.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
				update1.close();
				statement1.close();
				Unifia_Admin_Selenium.ScannerCount++;
				boolean Res = EM_A.ScanItem(StorageOutScanner2, "Scope", "", StorageOutScope2Name, "");

			}
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();
			stmt1="Select DateDiff(hh, IH.ReceivedDateTime, GETUTCDATE())/24 AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK "
					+ " where ScopeCycle.ScopeID_FK="+ScopeID+" and ScopeCycle.CycleID="+StorageOutScope1CycleID+" and IH.CycleEventID_FK=18";

			//stmt1="Select DateDiff(dd, IH.ReceivedDateTime, GETUTCDATE()) AS HangTime, ScopeCycle.AssociationID_FK from ItemHistory IH join ScopeCycle on IH.AssociationID_FK=ScopeCycle.AssociationID_FK "
			//		+ " where ScopeCycle.ScopeID_FK="+ScopeID+" and ScopeCycle.CycleID="+CycleID+" and IH.CycleEventID_FK=18";
			//System.out.println(stmt1);

			Statement statement1 = conn.createStatement();
			Repro_rs = statement1.executeQuery(stmt1);
			while(Repro_rs.next()){
				StorageOut2HangTime = Repro_rs.getString(1); //the first variable in the set is the ID row in the database.
				StorageOut2ReproOut_AssociationID = Repro_rs.getInt(2);
			}
			Repro_rs.close();
			//System.out.println("StorageOut2HangTime="+StorageOut2HangTime);
			//System.out.println("StorageOut2ReproOut_AssociationID= "+StorageOut2ReproOut_AssociationID);
			statement1.close();
			
			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}
	public void v_Scope2CheckedOut(){
		//System.out.println(getCurrentElement().getName());
	}
	
	public void v_Scope1ObtainCulture(){
		//System.out.println(getCurrentElement().getName());
		CultureScope1Name="";
		CultureScope1SerialNo="";
		CultureScanner1="";
		try{ //Get a value that exists in Unifia to modify.			
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	

			stmt="select Scope.Name, Scope.SerialNumber, Status.ScopeID_FK from Scope join ScopeStatus Status on Scope.ScopeID_PK=Status.ScopeID_FK "
					+ "where Status.ScopeStateID_FK=5 and Status.LocationID_FK in (51,52,53,54,55,56) and "
					+ "status.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from ScopeStatus where ScopeStateID_FK=5 and "
					+ "LocationID_FK in (51,52,53,54,55,56))";
			//System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Scope_rs = statement.executeQuery(stmt);
			while(Scope_rs.next()){
				CultureScope1Name = Scope_rs.getString(1); //the first variable in the set is the ID row in the database.
				CultureScope1SerialNo = Scope_rs.getString(2);
				ScopeID = Scope_rs.getInt(3);
			}		
			Scope_rs.close();
			//System.out.println("CultureScope1Name = "+CultureScope1Name);
			if(CultureScope1Name.equalsIgnoreCase("")){
				stmt="select Scope.Name, Scope.SerialNumber, Stat.ScopeID_FK from Scope join ScopeStatus Stat on Scope.ScopeID_PK=Stat.ScopeID_FK join "
						+ "Location Loc on Stat.LocationID_FK=Loc.LocationID_PK where Stat.ScopeStateID_FK=5 and Stat.OtherScopeStateID_FK Is Null and "
						+ "Stat.LocationID_FK in (31,32,33,34,51,52,53,54,55,56) and Stat.LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) "
						+ "from ScopeStatus where ScopeStateID_FK=5 and OtherScopeStateID_FK Is Null and LocationID_FK in (31,32,33,34,51,52,53,54,55,56))";
	    		//System.out.println(stmt);
				Scope_rs = statement.executeQuery(stmt);
				while(Scope_rs.next()){
					CultureScope1Name = Scope_rs.getString(1); //the first variable in the set is the ScopeName row in the database.
					CultureScope1SerialNo= Scope_rs.getString(2); //the second variable in the set is the ScopeSerialNumber row in the database.
					ScopeID = Scope_rs.getInt(3);
				}		
				Scope_rs.close();
			}
			//System.out.println("CultureScope1Name = "+CultureScope1Name);
			
			update.execute("Update ScopeStatus set LastUpdatedDateTime=GETUTCDATE() WHERE ScopeID_FK='"+ScopeID+"';");
			update.close();
			statement.close();
			
			Statement statement1 = conn.createStatement();
			stmt1="select LocationID_PK, Name from Location where LocationTypeID_FK=9 and IsActive=1 and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from Location where LocationTypeID_FK=9 and IsActive=1)";
			//System.out.println(stmt1);
			Statement update1 = conn.createStatement();

			Scanner_ID_rs= statement1.executeQuery(stmt1);
			while(Scanner_ID_rs.next()){
				LocationID_PK = Scanner_ID_rs.getInt(1);
				CultureScanner1 = Scanner_ID_rs.getString(2); //the first variable in the set is the ID row in the database.
			}		
			Scanner_ID_rs.close();
			//System.out.println("CultureScanner1="+CultureScanner1);
			update1.execute("Update Location set LastUpdatedDateTime=GETUTCDATE() WHERE LocationID_PK='"+LocationID_PK+"';");
			update1.close();
			statement1.close();

			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}

	}
	public void v_Scope1CultureObtained(){
		//System.out.println(getCurrentElement().getName());
	}
}
