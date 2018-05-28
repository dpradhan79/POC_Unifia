
package Regression;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.Emulator.GetIHValues;
import TestFrameWork.Emulator.Emulator_Actions;
import TestFrameWork.Emulator.Emulator_Verifications;
import TestFrameWork.RegressionTest.*;
import TestFrameWork.RegressionTest.ReprocessingRoom.*;
import TestFrameWork.RegressionTest.ProcedureRoom.*;
import TestFrameWork.RegressionTest.SoiledArea.*;
import TestFrameWork.RegressionTest.StorageArea.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class PostUpgradeHappyPath_CycleMgmt {	
	/**
	 * Nicole McKinley 8/17/2016 
	 * 
	 * Regression Test of Cycle Mgmt - PostUpgradeHappyPath_CycleMgmt
	 * This test is used to verify the application after upgrading the Unifia application version from 2.2 to 2.3
	 * 
	 * Prior to executing this test, ExceedingHangTime_CycleMgmt script needs to be executed in 2.2 version of application
	 * ExceedingHangTime_CycleMgmt script will scan Scope 1 and Scope of 3 complete cycles ((Procedure Room, Soiled, Reprocessing check into storage)
	 * Upgrade to 2.3 version and execute ExceedingHangTime_CycleMgmt script 
	 * 
	 * This Test PostUpgradeHappyPath_CycleMgmt will continue with the scan of Scope 1 and Scope 2 out of the storage area and do a complete cycle of scans 
	 * (Procedure Room, Soiled, Reprocessing check into storage) of Cycle4 with reason = used in procedure
	 * 
	 * This test will not verify anything with KE, culture or bioburden
	 * 
	 * The Test Data it will use
	 * 
	 * 	Scopes:  Scope1, Scope2 
	 *  Location Scanners:
	 * 	-  Procedure Room:  Procedure Room 1
	 * 	-  Soiled Room:  Sink 1
	 * 	-  Reprocessing:  Reprocessor 1
	 * 	-  Cabinet:  Storage Area A (4 cabinets)
	 * 	-  Admin:  Administration
	 * 
	 * 	-  Staff1, Staff2, Staff3, Staff4, Staff5, Staff6
	 */
	
	public TestFrameWork.Emulator.GetIHValues IHV;
	public TestFrameWork.Emulator.Emulator_Actions EM_A;
	public TestFrameWork.Emulator.Emulator_Verifications EM_V;
	public TestFrameWork.RegressionTest.ReprocessingRoom.ReprocessingRegression_Actions Repro_A;
	public TestFrameWork.RegressionTest.ProcedureRoom.PR_Regression_Actions PR_A;
	public TestFrameWork.RegressionTest.SoiledArea.SoiledRegression_Actions Soiled_A;
	public TestFrameWork.RegressionTest.StorageArea.StorageRegression_Actions Storage_A;
	public TestFrameWork.RegressionTest.Regression_Actions R_A;
	public TestFrameWork.RegressionTest.Regression_Verifications R_V;
	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public GeneralFunc GF;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public String OverallResult="Pass";	
	
	public String StartProcScan="Yes"; //perform the Start Procedure Scan
	public String EndProcScan="Yes"; //perform the End Procedure Scan
	public String Room="";

	public String Reason1="New Scope", Reason2="New Scope";

	String [] temp= new String[2];

	public Boolean Res;
	
	//Scope names used in this test
	public String Scope1="Scope1";
	public String Scope2="Scope2";
	public int Scope1ID; //Scope1 primary key
	public int Scope2ID; //Scope2 primary key

	public String Staff1="Tech1 Tech1(T01)";
	public String Staff1ID="T01";
	public String Staff2="Tech2 Tech2(T02)";
	public String Staff2ID="T02";
	public String Staff3="Tech3 Tech3(T03)";
	public String Staff3ID="T03";
	public String Staff4="Tech4 Tech4(T04)";
	public String Staff4ID="T04";
	public String Staff5="Tech5 Tech5(T05)";
	public String Staff5ID="T05";
	public String Phy="Physician1 Physician1(MD01)";
	public String Phy2="";

	//Location names used for scanning.
	public String PR1="Procedure Room 1"; 
	public String Repro1="Reprocessor 1";
	public String Sink1="Sink 1";
	public String Sink2="Sink 2";
	public String StorageA="Storage Area A";
	
	public int CycleCount1=3;  //Cycle count for scope1
	public int CycleCount2=3; //Cycle count for scope2
	public String Patient="MRN111111"; //PatientID
	public String AdmitPatientBarcode="Yes"; //Scan the Admit Patient barcode
	public String PhyScannedInWaiting="No";
	public String Culture1="NA"; //Culture was not obtained for scope1
	public String CultureAssociationID1="0"; //associationID if culture was obtained for scope1, since it was not obtained =0
	public String Culture2="NA"; //Culture was not obtained for scope2
	public String CultureAssociationID2="0";//associationID if culture was obtained for scope2, since it was not obtained =0
	public String LTFail="Leak Test Fail";
	public String LTPass="Leak Test Pass";
	public String MRCPass="MRC Pass";
	public String MRCFail="MRC Fail";
	public String MCStart="Yes"; //perform the Manual Clean Start Scan 
	public String MCEnd="Yes"; //perform the Manual Clean End Scan 
	public String CultureObtained="No";//culture is not obtained
	public String Cabinet="1"; //check the scopes into cabinet 1
	public String Description;
	public static Connection conn= null;
	public int NumAssociations=7;
	public int[] ExpectedScope1Cycle3= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle3. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle3= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle3. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope1Cycle4= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope1 cycle4. the first item specifies how many associationIDs there will be
	public int[] ExpectedScope2Cycle4= new int[NumAssociations]; //an array of the associationID's in ScopeCycle table for scope2 cycle4. the first item specifies how many associationIDs there will be

	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;
	
	//[RK-4/26] - Expected Reason for Reprocessings  to be verified	for Scope1 and Scope2
	public String[] ReasonForReprocessingScope1={"New Scope","Used in Procedure","Used in Procedure","Exceeded Hang Time"};
	public String[] ReasonForReprocessingScope2={"New Scope","Used in Procedure","Used in Procedure","Exceeded Hang Time"};
	public static ResultSet Barcode_RS;
			
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void Test(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException, NumberFormatException, AWTException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		Scope1ID=R_A.GetScopeID(Unifia_Admin_Selenium.connstring, Scope1);
		Scope2ID=R_A.GetScopeID(Unifia_Admin_Selenium.connstring, Scope2);
		
		for(int i=0; i<NumAssociations;i++){ //initialize the arrays for scope cycles
			//NM will probably need to update these variables for different cycles. it will be cycle 2 or 3. need to check. 
			ExpectedScope1Cycle3[i]=999999999;
			ExpectedScope2Cycle3[i]=999999999;
			ExpectedScope1Cycle4[i]=999999999;
			ExpectedScope2Cycle4[i]=999999999;
		}

		Unifia_Admin_Selenium.XMLFileName="PostUpgradeHappyPathCycleMgmt_Regression_";
		Unifia_Admin_Selenium.XMLFileName=IHV.Start_Exec_Log(Unifia_Admin_Selenium.XMLFileName);
		Unifia_Admin_Selenium.TestCaseNumber=1;

		Unifia_Admin_Selenium.StepNum=1;
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL);
		//Start of the Test
		
		//Verify  Reason for Reprocessing is converted correctly after upgraded to 2.3
		String Expected="Reason for Reprocessing for Scope 1 for cycles 0,1,2,3 are updated correctly";
		Description="Verifying Reason for Reprocessing for Scope 1 is updated correctly after application is upgraded";
		String Result=VerifyReasonForReprocessing(1); // For Scope 1 - cycle 0,1,2,3
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		
		Expected="Reason for Reprocessing for Scope 2 for cycles 0,1,2,3 are updated correctly";
		Description=" Verifying Reason for Reprocessing for Scope 2 is updated correctly after application is upgraded";
		Result=VerifyReasonForReprocessing(2);// For Scope 1 - cycle 0,1,2,3
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
	
		//Step 1 scan scope 1 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" out of "+StorageA);
		Description="Scan Scope "+Scope1+" out of "+StorageA;
		Culture1="No"; //No culture obtained for scope
		
		//NM 4/10/2017 at this point, we check the scopes out of the storage area and a new cycle will begin when scanned into the procedure room. 
		//scope 1 and 2 are in cycle 3 at the end of the Exceeding Hang time test that was executed in 2.2 prior to the upgrade 
		//we need to verify that the scans into the procedure room start cycle 4 for both scope 1 and 2. 
		
		String[] ResultStorageOutScope1=Storage_A.OutOfStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Culture1,CultureAssociationID1,Integer.toString(CycleCount1));
		String StorageOut_AssocScope1=ResultStorageOutScope1[0]; //AssociationID for Scope1 Storage Out scans
		String OverallStorageOut_Result1=ResultStorageOutScope1[1]; //overall pass/fail of storage out scans. 
		Expected="Scope "+Scope1+" was scanned out of "+StorageA+". ";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallStorageOut_Result1);

		//Step 2 scan scope 2 out of Storage Area A
		Unifia_Admin_Selenium.StepNum++;
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" out of "+StorageA);
		Description="Scan Scope "+Scope2+" out of "+StorageA;
		Culture2="No";
			
//		String[] ResultStorageOutScope2=Storage_A.OutOfStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Culture2,CultureAssociationID2,Integer.toString(CycleCount2));
		String StorageOut_AssocScope2=ResultStorageOutScope1[0]; //AssociationID for Scope1 Storage Out scans
		String OverallStorageOut_Result2=ResultStorageOutScope1[1];//overall pass/fail of storage out scans. 
		Expected="Scope "+Scope2+" was scanned out of "+StorageA+".";
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, OverallStorageOut_Result2);
		System.out.println("OverallResult"+OverallResult);
		
		//Step 3 scan scope 1 & 2 into PR1 - cycle count increases 
		Unifia_Admin_Selenium.StepNum++;
		CycleCount1++;
		CycleCount2++;
		Culture1="NA";
		Culture2="NA";

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+PR1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+PR1;
		String[] ResultPRScans=PR_A.PR_Scans(PR1,Scope1,Scope2,Staff2,Staff2ID,Phy,Phy2,AdmitPatientBarcode,Patient,StartProcScan,EndProcScan,Culture1,Integer.parseInt(CultureAssociationID1),Culture2,Integer.parseInt(CultureAssociationID2),Integer.toString(CycleCount1));
		int PR_Assoc=Integer.parseInt(ResultPRScans[0]); //Procedure Room associationID
		String OverallPR_Result=ResultPRScans[1];
		temp=OverallPR_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallPR_Result, OverallResult);

		ExpectedScope1Cycle4[0]=1;//there's one item in ScopeCycle table for scope1 cycle 1
		ExpectedScope1Cycle4[1]=PR_Assoc;
		ExpectedScope2Cycle4[0]=1;//there's one item in ScopeCycle table for scope2 cycle 1
		ExpectedScope2Cycle4[1]=PR_Assoc;

		int[] ActualScope1Cycle4=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
		int[] ActualScope2Cycle4=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		String ResultScope1Cycle4=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle4, ActualScope1Cycle4);
		temp=ResultScope1Cycle4.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle4, OverallResult);

		String ResultScope2Cycle4=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle4, ActualScope2Cycle4);
		temp=ResultScope2Cycle4.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle4, OverallResult);
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle4="+ResultScope1Cycle4);
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle4="+ResultScope2Cycle4);
		Result="ResultScope1Cycle4="+ResultScope1Cycle4+". ResultScope2Cycle4="+ResultScope2Cycle4;

		Expected="Scopes "+Scope1+" and "+Scope2+" were used in "+PR1+". The ScopeCycle table is correct for CycleCount="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//Step 4 scan scope 1 into Sink1
		Unifia_Admin_Selenium.StepNum++;
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+Sink1);
		Description="Scan Scopes "+Scope1+" into "+Sink1+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans1=Soiled_A.SoiledRoomScans(Sink1,Scope1,Staff3,Staff3ID,LTPass,MCStart,MCEnd,Integer.toString(CycleCount1));
		int Scope1_SR_Assoc=Integer.parseInt(ResultSoiledScans1[0]);
		String OverallSoiled_Result=ResultSoiledScans1[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);
		
		ExpectedScope1Cycle4[0]=2;
		ExpectedScope1Cycle4[2]=Scope1_SR_Assoc;

		Arrays.sort(ExpectedScope1Cycle4);

		ActualScope1Cycle4=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
		ResultScope1Cycle4=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle4, ActualScope1Cycle4);
		temp=ResultScope1Cycle4.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle4, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle4="+ResultScope1Cycle4);
		
		Expected="Scopes "+Scope1+" was manually cleaned in "+Sink1+". The ScopeCycle table is correct for CycleCount1="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope1Cycle4);

		//Step 5 scan scope 2 into Sink2
		Unifia_Admin_Selenium.StepNum++;
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+Sink2);
		Description="Scan Scopes "+Scope2+" into "+Sink2+" and perform Leak Test and Manual Cleaning.";
		
		String[] ResultSoiledScans2=Soiled_A.SoiledRoomScans(Sink2,Scope2,Staff4,Staff4ID,LTPass,MCStart,MCEnd,Integer.toString(CycleCount2));
		int Scope2_SR_Assoc=Integer.parseInt(ResultSoiledScans2[0]);
		OverallSoiled_Result=ResultSoiledScans2[1];
		temp=OverallSoiled_Result.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallSoiled_Result, OverallResult);

		ExpectedScope2Cycle4[0]=2;
		ExpectedScope2Cycle4[2]=Scope2_SR_Assoc;
		
		Arrays.sort(ExpectedScope2Cycle4);
		
		ActualScope2Cycle4=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);
		ResultScope2Cycle4=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle4, ActualScope2Cycle4);
		temp=ResultScope2Cycle4.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle4, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle4="+ResultScope2Cycle4);
		Expected="Scopes "+Scope2+" was manually cleaned in "+Sink2+". The ScopeCycle table is correct for CycleCount2="+CycleCount2;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, ResultScope2Cycle4);

		//set PR to available
		Unifia_Admin_Selenium.StepNum++;
		Room="Available";
		Unifia_Admin_Selenium.ScannerCount=Unifia_Admin_Selenium.ScannerCount+1;
		Res = EM_A.ScanItem(PR1, "Workflow Event", "", Room, "");
		System.out.println(Res);
		Description="Scan of Work flow event 'Available' is done in "+ PR1;
		Thread.sleep(8000);
		
		Expected="Room State is Avialable";
		String ExpectedRmSt="Available";
		
		String RmState=IHV.Room_State(Unifia_Admin_Selenium.connstring, PR1);
		Result=IHV.Result_Room_State(RmState, ExpectedRmSt, PR1);
		temp=Result.split("-");
		OverallResult=GF.FinalResult(temp[0], Result, OverallResult);

		System.out.println(Unifia_Admin_Selenium.StepNum+":  "+RmState);
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, RmState);
		
		//Step 6 scan scope 1 & 2 into reprocessor
		Unifia_Admin_Selenium.StepNum++;
		Reason1="Used in Procedure";
		Reason2="Used in Procedure";
		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" and "+Scope2+" into "+Repro1);
		Description="Scan Scopes "+Scope1+" and "+Scope2+" into "+Repro1;
		
		String[] ResultReproScans=Repro_A.ReprocessingRoomScans(Repro1,Scope1,Scope2,Staff1,Staff1ID,Reason1,Reason2,MRCPass,Integer.toString(CycleCount1),Integer.toString(CycleCount2));
		
		int Scope1InReproAssociationID=Integer.parseInt(ResultReproScans[0]);
		int Scope2InReproAssociationID=Integer.parseInt(ResultReproScans[1]);
		int MRCAssociationID=Integer.parseInt(ResultReproScans[2]);
		int Scope1OutReproAssociationID=Integer.parseInt(ResultReproScans[3]);
		int Scope2OutReproAssociationID=Integer.parseInt(ResultReproScans[4]);
		String OverallReproResult2=ResultReproScans[5];
		String ResultScope1Reason=ResultReproScans[6];
		String ResultScope2Reason=ResultReproScans[7];
		temp=OverallReproResult2.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallReproResult2, OverallResult);

		System.out.println("OverallResult="+OverallResult);

		ExpectedScope1Cycle4[0]=5;
		ExpectedScope1Cycle4[3]=Scope1InReproAssociationID;
		ExpectedScope1Cycle4[4]=MRCAssociationID;
		ExpectedScope1Cycle4[5]=Scope1OutReproAssociationID;

		ExpectedScope2Cycle4[0]=5;
		ExpectedScope2Cycle4[3]=Scope2InReproAssociationID;
		ExpectedScope2Cycle4[4]=MRCAssociationID;
		ExpectedScope2Cycle4[5]=Scope2OutReproAssociationID;
		Arrays.sort(ExpectedScope1Cycle4);
		Arrays.sort(ExpectedScope2Cycle4);

		ActualScope1Cycle4=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);
		ActualScope2Cycle4=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		ResultScope1Cycle4=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle4, ActualScope1Cycle4);
		temp=ResultScope1Cycle4.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle4, OverallResult);

		ResultScope2Cycle4=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle4, ActualScope2Cycle4);
		temp=ResultScope2Cycle4.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle4, OverallResult);

		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle4="+ResultScope1Cycle4);
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle4="+ResultScope2Cycle4);
		Result="ResultScope1Cycle4="+ResultScope1Cycle4+". ResultScope1Reason="+ResultScope1Reason+". ResultScope2Cycle4="+ResultScope2Cycle4+". ResultScope2Reason="+ResultScope2Reason;

		Expected="Scopes "+Scope1+" and "+Scope2+" were reprocessed in "+Repro1+". The ScopeCycle table is correct for CycleCount="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//Step 7 scan scope 1 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope1+" into "+StorageA);
		Description="Scan Scopes "+Scope1+" into "+StorageA;
		
		String[] ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope1,Staff5,Staff5ID,Cabinet,CultureObtained,Integer.toString(CycleCount1));
		
		String StorageIn_AssocScope1=ResultStorageInScope1[0];
		String OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);
		
		ActualScope1Cycle4=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope1ID, CycleCount1);

		ResultScope1Cycle4=R_V.VerifyScopeCycleAssociations(ExpectedScope1Cycle4, ActualScope1Cycle4);
		temp=ResultScope1Cycle4.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope1Cycle4, OverallResult);

		
		System.out.println("end Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope1Cycle4="+ResultScope1Cycle4);
		Result="ResultScope1Cycle4="+ResultScope1Cycle4;

		Expected="Scopes "+Scope1+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount1;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);

		//Step 8 scan scope 2 into Storage Area A
		Unifia_Admin_Selenium.StepNum++;

		System.out.println("Starting Step:  "+Unifia_Admin_Selenium.StepNum+" scan "+Scope2+" into "+StorageA);
		Description="Scan Scopes "+Scope2+" into "+StorageA;
		
		ResultStorageInScope1=Storage_A.IntoStorageAreaScans(StorageA,Scope2,Staff5,Staff5ID,Cabinet,CultureObtained,Integer.toString(CycleCount2));
		
		String StorageIn_AssocScope2=ResultStorageInScope1[0];
		OverallStorageIn_Result1=ResultStorageInScope1[1];
		temp=OverallStorageIn_Result1.split("-");
		OverallResult=GF.FinalResult(temp[0], OverallStorageIn_Result1, OverallResult);

		ActualScope2Cycle4=R_A.GetCycleIDs(Unifia_Admin_Selenium.connstring, Scope2ID, CycleCount2);

		ResultScope2Cycle4=R_V.VerifyScopeCycleAssociations(ExpectedScope2Cycle4, ActualScope2Cycle4);
		temp=ResultScope2Cycle4.split("-");
		OverallResult=GF.FinalResult(temp[0], ResultScope2Cycle4, OverallResult);
		
		System.out.println("End Step:  "+Unifia_Admin_Selenium.StepNum+" ResultScope2Cycle4="+ResultScope2Cycle4);
		Result="ResultScope2Cycle4="+ResultScope2Cycle4;

		Expected="Scopes "+Scope2+" were put in "+StorageA+". The ScopeCycle table is correct for CycleCount="+CycleCount2;
		IHV.Exec_Log_Result(Unifia_Admin_Selenium.XMLFileName, Description, Expected, Result);
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		if (Unifia_Admin_Selenium.resultFlag.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();
	}
	
	
	public String VerifyReasonForReprocessing(Integer ScopeId){
		//int[] Scope1Barcode = null;
		int Size=1;
		int i=0;
		String Result=null;
		String OverallResult="";
		String RecCount="select count(*) from itemhistory where associationid_fk in "
				+ "(select AssociationID_FK from scopecycle where associationid_fk in (select AssociationID_FK from itemhistory "
				+ "where scanitemid_FK = "+ScopeId+" and ScanItemTypeID_FK=1 and LocationID_FK=51 and cycleeventid_fk=15)) "
				+ "and cycleeventid_fk=37";
		try {
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
			Statement statement = conn.createStatement();
			ResultSet Barcode_RS=statement.executeQuery(RecCount);
			while(Barcode_RS.next()){
				Size=Barcode_RS.getInt(1);		
			}
			Barcode_RS.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int Scope1Barcode[]= new int[Size];
		String stmt2="select scanItemId_FK from itemhistory where associationid_fk in "
				+ "(select AssociationID_FK from scopecycle where associationid_fk in (select AssociationID_FK from itemhistory "
				+ "where scanitemid_FK ="+ScopeId+" and ScanItemTypeID_FK=1 and LocationID_FK=51 and cycleeventid_fk=15)) "
				+ "and cycleeventid_fk=37 order by AssociationID_FK";
		
		System.out.println(stmt2);
		try {
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
			Statement statement = conn.createStatement();
			ResultSet Barcode_RS = statement.executeQuery(stmt2);
			while(Barcode_RS.next()){
				Scope1Barcode[i]=Barcode_RS.getInt(1);
				System.out.println(Scope1Barcode[i]);
				i++;
			}
			Barcode_RS.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ActualReasonforRep;
		for (int cntr=0; cntr<Scope1Barcode.length;cntr++)
		{
			String BarCodeStmt="select name from barcode where barcodeid_pk = "+Scope1Barcode[cntr];
			ActualReasonforRep=ExecuteQuery(BarCodeStmt);
			System.out.println(ActualReasonforRep);
			if (ReasonForReprocessingScope1[cntr].equalsIgnoreCase(ActualReasonforRep)){
				Result="Pass - Verifying Reason for Reprocessing for Scope "+ScopeId+", cycle"+cntr+"; Expected="+ReasonForReprocessingScope1[cntr] +"; Actual="+ActualReasonforRep+";";
				OverallResult+=Result;
				System.out.println(Result);
			}else{
				Result="Error - Verifying Reason for Reprocessing for Scope "+ScopeId+", cycle"+cntr+"; Expected="+ReasonForReprocessingScope1[cntr] +"; Actual="+ActualReasonforRep+";";
				OverallResult+=Result;
				System.out.println(Result);
			}
		}
		return OverallResult;
	}
	
	public String ExecuteQuery(String stmt){
		String Res = null;
		try {
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
			Statement statement = conn.createStatement();
			ResultSet RS = statement.executeQuery(stmt);
			while(RS.next()){
				Res=RS.getString(1);
			}
			RS.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Res;
	}
	
	@AfterTest
	public void PostTTest() throws IOException{
		//System.out.println("The Test Case Run was:  "+TCResult);
		//IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, TCResult);
		LP_A.CloseDriver();
	}
}
