package TestFrameWork.RegressionTest;
import java.awt.AWTException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;
import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.RegressionTest.*;
import TestFrameWork.RegressionTest.ReprocessingRoom.*;
import TestFrameWork.RegressionTest.ProcedureRoom.*;
import TestFrameWork.RegressionTest.SoiledArea.*;
import TestFrameWork.RegressionTest.StorageArea.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage;

public class Regression_Actions extends Unifia_Admin_Selenium {
	public static Connection conn= null;
	public static ResultSet IH_RS;

	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	public static TestFrameWork.Emulator.GetIHValues IHV;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public static TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public static TestFrameWork.RegressionTest.ReprocessingRoom.ReprocessingRegression_Actions Repro_A;
	public static TestFrameWork.RegressionTest.ProcedureRoom.PR_Regression_Actions PR_A;
	public static TestFrameWork.RegressionTest.SoiledArea.SoiledRegression_Actions Soiled_A;
	public static TestFrameWork.RegressionTest.StorageArea.StorageRegression_Actions Storage_A;
	public static TestFrameWork.RegressionTest.Regression_Actions R_A;
	public static TestFrameWork.RegressionTest.Regression_Verifications R_V;
	public static TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private static Dashboard_Verification DV;
	
	public static int GetScopeID(String ConnString, String Scopename){
		int ScopeID=0;
		
		try{ //Get a value that exists in Unifia to modify.
			//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			
    		conn= DriverManager.getConnection(ConnString);	
    		Statement statement = conn.createStatement();
			String stmt="select ScopeID_PK from Scope where Name='"+Scopename+"';";
			//System.out.println(stmt);
			ResultSet Scope1_rs = statement.executeQuery(stmt);
			while(Scope1_rs.next()){
				ScopeID = Scope1_rs.getInt(1);
			}
			statement.close();

			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		return ScopeID;
	}
	
	public static int[] GetCycleIDs(String ConnString, int ScopeID, int CycleID) throws InterruptedException{
		int IH_PK = 0;
		int Assoc_Key = 0;
		String Scanner = null;
		int Size=1;
		int i=0;
		
		String CycleData= "select count(AssociationID_FK) from ScopeCycle where ScopeID_FK="+ScopeID+" and CycleID="+CycleID;
		//System.out.println(CycleData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(CycleData);
			while(IH_RS.next()){
				Size=IH_RS.getInt(1);		
			}
			IH_RS.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int Associations[]= new int[Size+1];
		Associations[i]=Size;
		i++;
		CycleData="select AssociationID_FK from ScopeCycle where ScopeID_FK="+ScopeID+" and CycleID="+CycleID+" Order by AssociationID_FK ASC";
		//System.out.println(CycleData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(CycleData);
			while(IH_RS.next()){
				Associations[i]=IH_RS.getInt(1);
				i++;
		
			}
			IH_RS.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Associations;
	}
	
	public static String[] InitialScans(String Scope1, String Scope2, String[][] Scope_Info) throws InterruptedException, AWTException{
		String ReturnInfo[]= new String[11];
		int[] ExpectedScope1Cycle0= new int[4];
		int[] ExpectedScope2Cycle0= new int[4];
		int ActualScope1Cycle0[];
		int ActualScope2Cycle0[];
		
		String ResultScope1Cycle0;
		String ResultScope2Cycle0;

		int[] ExpectedScope1Cycle1= new int[7];
		int[] ExpectedScope2Cycle1= new int[7];
		int ActualScope1Cycle1[];
		int ActualScope2Cycle1[];
		String ResultScope1Cycle1;
		String ResultScope2Cycle1;
		
		String Description;
		Boolean Res;
		String Expected;
		String [] temp= new String[2];
		String OverallResult="Pass";
		String Room="Available";
		String PR="";
		String Sink1="";
		String Sink2="";
		int Scope1ID=0;
		int Scope2ID=0;
		String Repro1 = "";
		String StorageA="";
		String StorageB="";
		String Cabinet1="";
		String Cabinet2="";
		String CultureObtained1="";
		String CultureObtained2="";
		String Staff1="";
		String Staff1ID="";
		String Staff2="";
		String Staff2ID="";
		String Staff3="";
		String Staff3ID="";
		String Staff4="";
		String Staff4ID="";
		String Staff5="";
		String Staff5ID="";
		String Reason1="";
		String Reason2="";
		String CycleZero="0";
		String CycleOne="1";
		String Phy="";
		String Phy2="";
		String AdmitPatientBarcode="";
		String Patient="";
		String StartProcScan="";
		String EndProcScan="";
		String Culture1="";
		String CultureAssociationID1="";
		String Culture2="";
		String CultureAssociationID2="";
		String LT1="";
		String MCStart1="";
		String MCEnd1="";
		String LT2="";
		String MCStart2="";
		String MCEnd2="";
		String MRC="";
		String Repro1_2;
		String Staff1_2;
		String Reason1_2="";
		String Reason2_2 = "";
		String MRC_2="";
		
		for(int i=0; i<7;i++){
			ExpectedScope1Cycle1[i]=999999999;
			ExpectedScope2Cycle1[i]=999999999;

		}
		
		try{
			for (int i=0; i<Scope_Info.length; i++) {
				if(Scope_Info[i][0].equalsIgnoreCase("Reprocessing1")) {
						for (int j=1; j<Scope_Info[0].length; j++) {
							if(j==1){
								Repro1=Scope_Info[i][j];
							}else if(j==2){
								Staff1=Scope_Info[i][j];
							}else if(j==3){
								Staff1ID=Scope_Info[i][j];
							}else if(j==4){
								Reason1=Scope_Info[i][j];
							}else if(j==5){
								Reason2=Scope_Info[i][j];
							}else if(j==6){
								MRC=Scope_Info[i][j];
							}
						}
					} else if(Scope_Info[i][0].equalsIgnoreCase("Reprocessing2")) {
						for (int j=1; j<Scope_Info[0].length; j++) {
							if(j==1){
								Repro1_2=Scope_Info[i][j];
							}else if(j==2){
								Staff1_2=Scope_Info[i][j];
							}else if(j==3){
								Staff1ID=Scope_Info[i][j];
							}else if(j==4){
								Reason1_2=Scope_Info[i][j];
							}else if(j==5){
								Reason2_2=Scope_Info[i][j];
							}else if(j==6){
								MRC_2=Scope_Info[i][j];
							}
						}
					}else if(Scope_Info[i][0].equalsIgnoreCase("ProcedureRoom")) {
						for (int j=1; j<Scope_Info[i].length; j++) {
							if(j==1){
								PR=Scope_Info[i][j];
							}else if(j==2){
								Staff2=Scope_Info[i][j];
							}else if(j==3){
								Staff2ID=Scope_Info[i][j];
							}else if(j==4){
								Phy=Scope_Info[i][j];
							}else if(j==5){
								AdmitPatientBarcode=Scope_Info[i][j];
							}else if(j==6){
								Patient=Scope_Info[i][j];
							}else if(j==7){
								StartProcScan=Scope_Info[i][j];
							}else if(j==8){
								EndProcScan=Scope_Info[i][j];
							}else if(j==9){
								Culture1=Scope_Info[i][j];
							}else if(j==10){
								CultureAssociationID1=Scope_Info[i][j];
							}else if(j==11){
								Culture2=Scope_Info[i][j];
							}else if(j==12){
								CultureAssociationID2=Scope_Info[i][j];
							}
						}
					}else if(Scope_Info[i][0].equalsIgnoreCase("Sink1")) {
						for (int j=1; j<Scope_Info[i].length; j++) {
							if(j==1){
								Sink1=Scope_Info[i][j];
							}else if(j==2){
								Staff3=Scope_Info[i][j];
							}else if(j==3){
								Staff3ID=Scope_Info[i][j];
							}else if(j==4){
								LT1=Scope_Info[i][j];
							}else if(j==5){
								MCStart1=Scope_Info[i][j];
							}else if(j==6){
								MCEnd1=Scope_Info[i][j];
							}
						}
					}else if(Scope_Info[i][0].equalsIgnoreCase("Sink2")) {
						for (int j=1; j<Scope_Info[i].length; j++) {
							if(j==1){
								Sink2=Scope_Info[i][j];
							}else if(j==2){
								Staff4=Scope_Info[i][j];
							}else if(j==3){
								Staff4ID=Scope_Info[i][j];
							}else if(j==4){
								LT2=Scope_Info[i][j];
							}else if(j==5){
								MCStart2=Scope_Info[i][j];
							}else if(j==6){
								MCEnd2=Scope_Info[i][j];
							}
						}
					}else if(Scope_Info[i][0].equalsIgnoreCase("StorageIn1")) {
						for (int j=1; j<Scope_Info[i].length; j++) {
							if(j==1){
								StorageA=Scope_Info[i][j];
							}else if(j==2){
								Staff5=Scope_Info[i][j];
							}else if(j==3){
								Staff5ID=Scope_Info[i][j];
							}else if(j==4){
								Cabinet1=Scope_Info[i][j];
							}else if(j==5){
								CultureObtained1=Scope_Info[i][j];
							}
						}
					}else if(Scope_Info[i][0].equalsIgnoreCase("StorageIn2")) {
						for (int j=1; j<Scope_Info[i].length; j++) {
							if(j==1){
								StorageB=Scope_Info[i][j];
							}else if(j==2){
								Staff5=Scope_Info[i][j];
							}else if(j==3){
								Staff5ID=Scope_Info[i][j];
							}else if(j==4){
								Cabinet2=Scope_Info[i][j];
							}else if(j==5){
								CultureObtained2=Scope_Info[i][j];
							}
					}
			}
			}
		} catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
			System.out.println(e);			
		}

		try{ //Get a value that exists in Unifia to modify.
			//    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			
    		conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    		Statement statement = conn.createStatement();
			String stmt="select ScopeID_PK from Scope where Name='"+Scope1+"';";
			//System.out.println(stmt);
			ResultSet Scope1_rs = statement.executeQuery(stmt);
			while(Scope1_rs.next()){
				Scope1ID = Scope1_rs.getInt(1);

			}
			
			statement.close();
    		Statement statement2 = conn.createStatement();
			String stmt2="select ScopeID_PK from Scope where Name='"+Scope2+"';";
			//System.out.println(stmt2);
			ResultSet Scope2_rs = statement2.executeQuery(stmt2);
			while(Scope2_rs.next()){
				Scope2ID = Scope2_rs.getInt(1);

			}
			
			statement2.close();

			conn.close();
   			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PR;
		Thread.sleep(20000);
		
		Expected="Room State is Avialable";
		String ExpectedRmSt="Available";
		
		//Daily Dashboard verification
		if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether "+PR+" is made Available or not";
			Expected=Description;
			String result=DV.verifyDashboard(PR, "", "", dashboardpage.expPRAvailableColor, dashboardpage.expPRAvailable);
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		
		String RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, PR);
		Result=IHV.Result_Room_State(RmState, ExpectedRmSt, PR)+" for PR";
		temp=Result.split("-");
		OverallResult=GF.FinalResult(temp[0], Result, OverallResult);
		//System.out.println("OverallResult="+OverallResult);

		//System.out.println(Unifia_Admin_Selenium.StepNum+":  "+Result);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
	
		
		//Step 2 scan scope 1 & 2 into reprocessor
		Unifia_Admin_Selenium.StepNum++;
		Reason1="New Scope";
		Reason2="New Scope";
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+Repro1;
		
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+Repro1);
		String [] ResultReproScans=Repro_A.ReprocessingRoomScans(Repro1, Scope1, Scope2, Staff1,Staff1ID, Reason1, Reason2,MRC,CycleZero,CycleZero);
		
		int Scope1InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		int MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		int Scope1OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		int Scope2InReproAssociationID;
		int Scope2OutReproAssociationID;
		
		if(Scope2.equalsIgnoreCase("")){
			Scope2InReproAssociationID=0;
			Scope2OutReproAssociationID=0;
		}else {
			Scope2InReproAssociationID=Integer.parseInt(ResultReproScans[1]);
			Scope2OutReproAssociationID=Integer.parseInt(ResultReproScans[4]);
		}
		String OverallReproResult1=ResultReproScans[5];
		
		OverallResult=GF.FinalResult(OverallReproResult1, OverallReproResult1,OverallResult);
		//System.out.println("OverallResult="+OverallResult);


		ExpectedScope1Cycle0[0]=3;
		ExpectedScope1Cycle0[1]=Scope1InReproAssociationID;
		ExpectedScope1Cycle0[2]=MRCAssociationID;
		ExpectedScope1Cycle0[3]=Scope1OutReproAssociationID;
		ActualScope1Cycle0=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, 0);

		ResultScope1Cycle0=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle0, ActualScope1Cycle0);
		temp=ResultScope1Cycle0.split("-");
		//System.out.println("temp[0]="+temp[0]);

		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle0, OverallResult);
		//System.out.println("OverallResult="+OverallResult);
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle0="+ResultScope1Cycle0);

		if(Scope2.equalsIgnoreCase("")){
			Result="ResultScope1Cycle0="+ResultScope1Cycle0+".";
			Expected="Scopes "+Scope1+" was reprocessed in "+Repro1+". The ScopeCycle table is correct.";
		} else {
			ExpectedScope2Cycle0[0]=3;
			ExpectedScope2Cycle0[1]=Scope2InReproAssociationID;
			ExpectedScope2Cycle0[2]=MRCAssociationID;
			ExpectedScope2Cycle0[3]=Scope2OutReproAssociationID;
			ActualScope2Cycle0=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, 0);
			ResultScope2Cycle0=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle0, ActualScope2Cycle0);
			temp=ResultScope2Cycle0.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle0, OverallResult);
			//System.out.println("OverallResult="+OverallResult);
			//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle0="+ResultScope2Cycle0);
			Result="ResultScope1Cycle0="+ResultScope1Cycle0+". ResultScope2Cycle0="+ResultScope2Cycle0;
			Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro1+". The ScopeCycle table is correct.";

		}

		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//Step 3 scan scope 1 & 2 in room
		Unifia_Admin_Selenium.StepNum++;
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+PR);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+PR;
		
		String[] ResultPRScans=PR_A.PR_Scans(PR, Scope1, Scope2, Staff2,Staff2ID, Phy,Phy2, AdmitPatientBarcode, Patient, StartProcScan, EndProcScan, Culture1, Integer.parseInt(CultureAssociationID1), Culture2, Integer.parseInt(CultureAssociationID2), CycleOne);
		
		int PR_Assoc=Integer.parseInt(ResultPRScans[0]);
		String OverallPR_Result=ResultPRScans[1];
		temp=OverallPR_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallPR_Result, OverallResult);

		ExpectedScope1Cycle1[0]=1;
		ExpectedScope1Cycle1[1]=PR_Assoc;
		
		ActualScope1Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, 1);
		ResultScope1Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle1, ActualScope1Cycle1);
		temp=ResultScope1Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle1, OverallResult);
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle1="+ResultScope1Cycle1);


		if(Scope2.equalsIgnoreCase("")){
			Result="ResultScope1Cycle1="+ResultScope1Cycle1+".";
		}else {
			ExpectedScope2Cycle1[0]=1;
			ExpectedScope2Cycle1[1]=PR_Assoc;
			ActualScope2Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, 1);
			ResultScope2Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle1, ActualScope2Cycle1);
			temp=ResultScope2Cycle1.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);
			//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle1="+ResultScope2Cycle1);
			Result="ResultScope1Cycle1="+ResultScope1Cycle1+". ResultScope2Cycle1="+ResultScope2Cycle1;
		}

		Expected="Scopes "+Scope1+" and "+Scope2+" were used in "+PR+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//Step 4 scan scope 1 into Sink1
		Unifia_Admin_Selenium.StepNum++;
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+Sink1);
		Description="Scan Scopes "+Scope1+" into "+Sink1+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans1=Soiled_A.SoiledRoomScans(Sink1, Scope1, Staff3,Staff3ID, LT1, MCStart1, MCEnd1, CycleOne);
		int Scope1_SR_Assoc=Integer.parseInt(ResultSoiledScans1[0]);
		String OverallSoiled_Result=ResultSoiledScans1[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);
		
		ExpectedScope1Cycle1[0]=2;
		ExpectedScope1Cycle1[2]=Scope1_SR_Assoc;

		Arrays.sort(ExpectedScope1Cycle1);

		ActualScope1Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, 1);
		ResultScope1Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle1, ActualScope1Cycle1);
		temp=ResultScope1Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle1, OverallResult);
		
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle1="+ResultScope1Cycle1);
		
		Expected="Scopes "+Scope1+" was manually cleaned in "+Sink1+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope1Cycle1);

		
		//Step 5 scan scope 2 into Sink2
		int Scope2_SR_Assoc;
		if(Scope2.equalsIgnoreCase("")){
			//skip scope2 not provided
			Scope2_SR_Assoc=0;
		} else{
			Unifia_Admin_Selenium.StepNum++;
			//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+Sink2);
			Description="Scan Scopes "+Scope2+" into "+Sink2+" and perform Leak Test and Manual Cleaning.";
			
			String[] ResultSoiledScans2=Soiled_A.SoiledRoomScans(Sink2,Scope2,Staff4,Staff4ID,LT2,MCStart2,MCEnd2,CycleOne);
			Scope2_SR_Assoc=Integer.parseInt(ResultSoiledScans2[0]);
			OverallSoiled_Result=ResultSoiledScans2[1];
			temp=OverallSoiled_Result.split("-");
			OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);
	
			ExpectedScope2Cycle1[0]=2;
			ExpectedScope2Cycle1[2]=Scope2_SR_Assoc;
			
			Arrays.sort(ExpectedScope2Cycle1);
			
			ActualScope2Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, 1);
			ResultScope2Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle1, ActualScope2Cycle1);
			temp=ResultScope2Cycle1.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);
			
			//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle1="+ResultScope2Cycle1);
			Expected="Scopes "+Scope2+" was manually cleaned in "+Sink2+". The ScopeCycle table is correct.";
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope2Cycle1);
		}
		
		//set PR to available step 6
		//Step 1-  Set PR to Available
		Unifia_Admin_Selenium.StepNum++;
		Room="Available";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR, "Workflow Event", "", Room, "");
		//System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PR;
		Thread.sleep(8000);
		
		Expected="Room State is Avialable";
		ExpectedRmSt="Available";
		
		//Daily Dashboard verification
		if (Unifia_Admin_Selenium.IsHappyPath || Unifia_Admin_Selenium.IsKEHappyPath){
			Description="Verifying DailyDashboard tab to see whether "+PR+" is made Available or not";
			Expected=Description;
			String result=DV.verifyDashboard(PR, "", "", dashboardpage.expPRAvailableColor, dashboardpage.expPRAvailable);
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, result);
		}
		
		RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, PR);
		Result=IHV.Result_Room_State(RmState, ExpectedRmSt, PR);
		temp=Result.split("-");
		OverallResult=GF.FinalResult(temp[0], Result, OverallResult);

		//System.out.println(Unifia_Admin_Selenium.StepNum+":  "+Result);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		////TCResult=IHV.TCResult(TCResult, Result); -- NM need to figure out an overal test pass/fail strategy -- NM need to figure out an overal test pass/fail strategy

		
		//Step 7 scan scope 1 & 2 into reprocessor
		Unifia_Admin_Selenium.StepNum++;
		Reason1="";
		Reason2="";
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+Repro1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+Repro1;
		
		ResultReproScans=Repro_A.ReprocessingRoomScans(Repro1, Scope1, Scope2, Staff1,Staff1ID, Reason1_2, Reason2_2,MRC_2,CycleOne,CycleOne);
		
		Scope1InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		Scope1OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		String OverallReproResult2=ResultReproScans[5];
		temp=OverallReproResult2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallReproResult2, OverallResult);

		//System.out.println("OverallResult="+OverallResult);

		ExpectedScope1Cycle1[0]=5;
		ExpectedScope1Cycle1[3]=Scope1InReproAssociationID;
		ExpectedScope1Cycle1[4]=MRCAssociationID;
		ExpectedScope1Cycle1[5]=Scope1OutReproAssociationID;

		Arrays.sort(ExpectedScope1Cycle1);

		ActualScope1Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, 1);

		ResultScope1Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle1, ActualScope1Cycle1);
		temp=ResultScope1Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle1, OverallResult);


		
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle1="+ResultScope1Cycle1);

		Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro1+". The ScopeCycle table is correct.";

		if(Scope2.equalsIgnoreCase("")){
			Result="ResultScope1Cycle1="+ResultScope1Cycle1+".";
			Expected="Scopes "+Scope1+" was reprocessed in "+Repro1+". The ScopeCycle table is correct.";
		}else {
			Scope2InReproAssociationID=Integer.parseInt(ResultReproScans[1]);
			Scope2OutReproAssociationID=Integer.parseInt(ResultReproScans[4]);
			ExpectedScope2Cycle1[0]=5;
			ExpectedScope2Cycle1[3]=Scope2InReproAssociationID;
			ExpectedScope2Cycle1[4]=MRCAssociationID;
			ExpectedScope2Cycle1[5]=Scope2OutReproAssociationID;
			Arrays.sort(ExpectedScope2Cycle1);
			ActualScope2Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, 1);
			ResultScope2Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle1, ActualScope2Cycle1);
			temp=ResultScope2Cycle1.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);
			//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle1="+ResultScope2Cycle1);
			Result="ResultScope1Cycle1="+ResultScope1Cycle1+". ResultScope2Cycle1="+ResultScope2Cycle1;
			Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro1+". The ScopeCycle table is correct.";

		}

		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//Step 8 scan scope 1 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+StorageA);
		Description="Scan Scopes "+Scope1+" into "+StorageA;
		

		String[] ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Cabinet1,CultureObtained1,CycleOne);
		
		String StorageIn_AssocScope1=ResultStorageInScope1[0];
		String OverallStorageIn_Result1=ResultStorageInScope1[1];
		ActualScope1Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, 1);

		ResultScope1Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle1, ActualScope1Cycle1);
		temp=ResultScope1Cycle1.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle1, OverallResult);

		
		//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle1="+ResultScope1Cycle1);
		Result="ResultScope1Cycle1="+ResultScope1Cycle1;

		Expected="Scopes "+Scope1+" were put in "+StorageA+". The ScopeCycle table is correct.";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		
		//Step 9 scan scope 2 into Storage Area A
		if(Scope2.equalsIgnoreCase("")){
			//Skip - Scope2 not provided. 
		}else{
			Unifia_Admin_Selenium.StepNum++;
	
			//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+StorageA);
			Description="Scan Scopes "+Scope2+" into "+StorageA;
			
			ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Cabinet2,CultureObtained2,CycleOne);
			
			String StorageIn_AssocScope2=ResultStorageInScope1[0];
			OverallStorageIn_Result1=ResultStorageInScope1[1];
			ActualScope2Cycle1=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, 1);
	
			ResultScope2Cycle1=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle1, ActualScope2Cycle1);
			temp=ResultScope2Cycle1.split("-");
			OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle1, OverallResult);
			
			//System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle1="+ResultScope2Cycle1);
			Result="ResultScope2Cycle1="+ResultScope2Cycle1;
	
			Expected="Scopes "+Scope2+" were put in "+StorageA+". The ScopeCycle table is correct.";
			IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		}


		ReturnInfo[0]=OverallResult;
		ReturnInfo[1]=ResultPRScans[0];
		ReturnInfo[2]=ResultSoiledScans1[0];
		ReturnInfo[3]=Integer.toString(Scope2_SR_Assoc);
		ReturnInfo[4]=ResultReproScans[0];
		ReturnInfo[5]=ResultReproScans[1];
		ReturnInfo[6]=ResultReproScans[2];
		ReturnInfo[7]=ResultReproScans[3];
		ReturnInfo[8]=ResultReproScans[4];
		
		return ReturnInfo;

	}

}
