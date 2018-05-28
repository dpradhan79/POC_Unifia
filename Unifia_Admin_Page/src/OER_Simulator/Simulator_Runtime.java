package OER_Simulator;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import OER_Simulator.OERGeneralFunc;
import OER_Simulator.OERSimulator;
import TestFrameWork.Unifia_Admin_Selenium;

import java.sql.ResultSet;
public class Simulator_Runtime {
	public OERGeneralFunc gf;
	public static String CycleCount;
	public static String Cycle1ScopeCount;
	public static String Cycle2ScopeCount;
	public static int Cycle1ScopeCnt;
	public static int Cycle2ScopeCnt;
	public static String Cycle1End;
	public static String Cycle2End;
	public static String KEip;
	public static String User;
	public static String PW;
	public static String Cycle1OERModel;
	public static String Cycle2OERModel;
	public static String Cycle1OERSerialNum;
	public static String Cycle2OERSerialNum;
	public static String Cycle1Duration;
	public static String Cycle2Duration;
	public int Cycle1DurationTime;
	public int Cycle2DurationTime;
	public static String Cycle1Lag;
	public static String Cycle2Lag;
	public int Cycle1LagInt;
	public int Cycle2LagInt;
	public static String Cycle1Scope1Model;
	public static String Cycle2Scope1Model;
	public static String Cycle1Scope2Model;
	public static String Cycle2Scope2Model;
	public static String Cycle1StaffID;
	public static String Cycle2StaffID;
	public static String Cycle1Scope1SerialNum;
	public static String Cycle2Scope1SerialNum;
	public static String Cycle1Scope2SerialNum;
	public static String Cycle2Scope2SerialNum;
	public int REPROCESSOR_KEY;
	public int SCOPE1_KEY;
	public int SCOPE2_KEY;
	public int REPROCESSOR_MODEL_KEY;
	public int REPROCESSOR_INFO_KEY;
	public int Cycle1REPROCESSOR_INFO_KEY;
	public int Cycle2REPROCESSOR_INFO_KEY;
	public int REPROCESS1_USED_COUNT=2;
	public int REPROCESS2_USED_COUNT=50;
	public int REPROCESS_CONDITION_KEY=100;
	public int REPROCESSED_SCOPES_KEY=100;
	public int CLINICAL_STAFF_KEY;
	public int Reprocessor_Available=4;
	public int Reprocessor_Processing=5;
	public int Reprocessor_Complete=9;
	public int Reprocessor_Error=7;
	public int Reprocessor_NotConnected=2;
	public int ReproResult_Processing=1;
	public int ReproResult_UnexpectedTerm=2;
	public int ReproResult_ScopeRemoved=3;
	public int ReproResult_ProcessComplete=5;

	public String OERComplete;
	public String OERComplete2;
	public String stmt1;
	public String stmt2;
	public ResultSet RS;
	public String DataType;

	OERSimulator OERS;
	
    @Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL,String AdminDB) throws Exception{
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		/*if (Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			Unifia_Admin_Selenium.setDriver(browserP);
			Unifia_Admin_Selenium.setEnvironment(URL);
		}else if(Unifia_Admin_Selenium.driverType.equalsIgnoreCase("GRID")){
			Unifia_Admin_Selenium.setGridDriver(browserP,URL);
		}*/
		
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		
		Unifia_Admin_Selenium.KE_Env=OERS.getIPAddress();
		Unifia_Admin_Selenium.KE_UserName=OERS.getUser();
		Unifia_Admin_Selenium.KE_Pwd=OERS.getPW();
		Unifia_Admin_Selenium.KE_Url="jdbc:oracle:thin:@"+Unifia_Admin_Selenium.KE_Env+":1521:FXDB";

		DataType=OERS.getDataType();
		CycleCount=OERS.getNumCycles();

        System.out.println("*************************");
        System.out.println("****** Cycle 1 ********");
        System.out.println("*************************");

		Cycle1ScopeCount=OERS.getCycle1NumScopes();
		System.out.println("Cycle1ScopeCountnt=  "+Cycle1ScopeCount);
		Cycle1ScopeCnt=Integer.parseInt(Cycle1ScopeCount);
		System.out.println("Cycle1ScopeCnt=  "+Cycle1ScopeCnt);
		if(Cycle1ScopeCnt==2){
			Cycle1Scope1SerialNum=OERS.getCycle1ScopeSerialNum1();
			Cycle1Scope1Model=OERS.getCycle1ScopeModel1();
			Cycle1Scope2SerialNum=OERS.getCycle1ScopeSerialNum2();
			Cycle1Scope2Model=OERS.getCycle1ScopeModel2();
		}else{
			Cycle1Scope1SerialNum=OERS.getCycle1ScopeSerialNum1();
			Cycle1Scope1Model=OERS.getCycle1ScopeModel1();
		}
		Cycle1End=OERS.getCycle1End();
		Cycle1OERModel=OERS.getCycle1OERModel();
		Cycle1OERSerialNum=OERS.getCylce1OERSerial();
		Cycle1Duration=OERS.getCycle1Duration();
		Cycle1DurationTime=Integer.parseInt(Cycle1Duration);
		Cycle1Lag=OERS.getCycle1Lag();
		Cycle1StaffID=OERS.getCycle1StaffID();
		
		if(CycleCount.equalsIgnoreCase("2")){
	        System.out.println("*************************");
	        System.out.println("****** Cycle 2 ********");
	        System.out.println("*************************");

			Cycle2ScopeCount=OERS.getCycle2NumScopes();
			System.out.println("Cycle2ScopeCountnt=  "+Cycle2ScopeCount);
			Cycle2ScopeCnt=Integer.parseInt(Cycle2ScopeCount);
			System.out.println("Cycle2ScopeCnt=  "+Cycle2ScopeCnt);
			if(Cycle2ScopeCnt==2){
				Cycle2Scope1SerialNum=OERS.getCycle2ScopeSerialNum1();
				Cycle2Scope1Model=OERS.getCycle2ScopeModel1();
				Cycle2Scope2SerialNum=OERS.getCycle2ScopeSerialNum2();
				Cycle2Scope2Model=OERS.getCycle2ScopeModel2();
			}else{
				Cycle2Scope1SerialNum=OERS.getCycle2ScopeSerialNum1();
				Cycle2Scope1Model=OERS.getCycle2ScopeModel1();
			}
			Cycle2End=OERS.getCycle2End();
			Cycle2OERModel=OERS.getCycle2OERModel();
			Cycle2OERSerialNum=OERS.getCylce2OERSerial();
			Cycle2Duration=OERS.getCycle2Duration();
			Cycle2DurationTime=Integer.parseInt(Cycle2Duration);
			Cycle2Lag=OERS.getCycle2Lag();
			Cycle2StaffID=OERS.getCycle2StaffID();

		}
		REPROCESSOR_MODEL_KEY=gf.GetReproModel_Key(Cycle1OERModel);
		REPROCESSOR_KEY=gf.GetRepro_Key(Cycle1OERSerialNum, REPROCESSOR_MODEL_KEY);

		//Connection to ORacle DB
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		
		if(DataType.equalsIgnoreCase("New")){
			gf.InsertOracleLData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			Cycle1REPROCESSOR_INFO_KEY=REPROCESSOR_KEY;

		} else if(DataType.equalsIgnoreCase("Append")){
			REPROCESS_CONDITION_KEY=gf.GetReproConditionKey(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			REPROCESS_CONDITION_KEY++;
			REPROCESSED_SCOPES_KEY=gf.GetReproScopeKey(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			REPROCESSED_SCOPES_KEY++;
			Cycle1REPROCESSOR_INFO_KEY=gf.GetReproInfoKey(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd,REPROCESSOR_KEY);
			REPROCESS1_USED_COUNT=gf.GetReproCount(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd,REPROCESSOR_KEY);
		}

		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		
		Statement update1 = conn.createStatement();
		SCOPE1_KEY=gf.GetScope_Key(Cycle1Scope1SerialNum);

		if(Cycle1ScopeCnt==2){
			SCOPE2_KEY=gf.GetScope_Key(Cycle1Scope2SerialNum);		
		}

		CLINICAL_STAFF_KEY=gf.GetStaff_Key(Cycle1StaffID);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		Cycle1LagInt= Integer.parseInt(Cycle1Lag);
		
		System.out.println("OER ("+Cycle1OERSerialNum+") is about to start.");
		Calendar date=Calendar.getInstance();
		
		long t=date.getTimeInMillis();
		String[] Times = gf.KEServerDateTime_Duration(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd, Cycle1DurationTime);

		String OERStartTime=Times[0];
		
		System.out.println("The OER Started at:  "+OERStartTime );
		Date OERCompleteTime=new Date(t+ ((Cycle1DurationTime*60)*1000));
		String Complete= new SimpleDateFormat("yyyyMMdd_HHmmss").format(OERCompleteTime.getTime());
		Complete=Times[1];
		System.out.println("The OER cycle should complete at:  "+Complete);
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Cycle1 should end:  "+Cycle1End);

		
		if(Cycle1End.equalsIgnoreCase("Normal")){
			if(DataType.equalsIgnoreCase("New")){
				gf.InsertReproInfo(Cycle1REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS1_USED_COUNT, Reprocessor_Processing);

			}else if(DataType.equalsIgnoreCase("Append")){
				stmt1="select REPROCESSOR_INFO_KEY from T_Reprocessor_Info where REPROCESSOR_KEY="+REPROCESSOR_KEY;
				System.out.println("stmt1="+stmt1);
				RS=update1.executeQuery(stmt1);
				while(RS.next()){
					REPROCESSOR_INFO_KEY = RS.getInt(1); 
					System.out.println("REPROCESSOR_INFO_KEY "+REPROCESSOR_INFO_KEY);
					}
				RS.close();
				if(REPROCESSOR_INFO_KEY==0){
					gf.InsertReproInfo(Cycle1REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS1_USED_COUNT, Reprocessor_Processing);

				}else {
					stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5, REPROCESS_USED_COUNT="+REPROCESS1_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;					
					System.out.println("stmt1="+stmt1);
					update1.executeQuery(stmt1);
				}

				
				stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5, REPROCESS_USED_COUNT="+REPROCESS1_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;					
				System.out.println("stmt1="+stmt1);
				update1.executeQuery(stmt1);
			}
			gf.InsertReproCondition(REPROCESS_CONDITION_KEY, REPROCESSOR_KEY, OERStartTime, Complete, REPROCESS1_USED_COUNT, CLINICAL_STAFF_KEY,ReproResult_Processing);
			
			if(Cycle1ScopeCnt==2){
				System.out.println("The OER is Started with 2 Scopes by Staff:  "+Cycle1StaffID);
				System.out.println("Scope1:  "+Cycle1Scope1SerialNum);
				System.out.println("Scope2:  "+Cycle1Scope2SerialNum);
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 2, SCOPE2_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;

			}else{
				System.out.println("The OER is Started with 1 Scope by Staff:  "+Cycle1StaffID);
				System.out.println("Scope1:  "+Cycle1Scope1SerialNum);
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;
			}
			Thread.sleep((Cycle1DurationTime*60)*1000);
			OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

			System.out.println("The OER cycle completed at:  "+OERComplete);
			System.out.println("The OER cycle2 completed at:  "+OERComplete2);

			stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_ProcessComplete+", REPROCESS_END_DATE=to_date('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS'), LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
			System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Complete+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;
			System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			Thread.sleep((2*60)*1000);
			OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_ScopeRemoved+", LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
			System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Available+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;

			System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			REPROCESS_CONDITION_KEY++;
		}else if (Cycle1End.equalsIgnoreCase("Unexpected Termination")){
			
			if(DataType.equalsIgnoreCase("New")){
				gf.InsertReproInfo(Cycle1REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS1_USED_COUNT, Reprocessor_Processing);

			}else if(DataType.equalsIgnoreCase("Append")){
				stmt1="select REPROCESSOR_INFO_KEY from T_Reprocessor_Info where REPROCESSOR_KEY="+REPROCESSOR_KEY;
				System.out.println("stmt1="+stmt1);
				RS=update1.executeQuery(stmt1);
				while(RS.next()){
					REPROCESSOR_INFO_KEY = RS.getInt(1); 
					System.out.println("REPROCESSOR_INFO_KEY "+REPROCESSOR_INFO_KEY);
					}
				RS.close();
				if(REPROCESSOR_INFO_KEY==0){
					gf.InsertReproInfo(Cycle1REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS1_USED_COUNT, Reprocessor_Processing);

				}else {
					stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5, REPROCESS_USED_COUNT="+REPROCESS1_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;					
					System.out.println("stmt1="+stmt1);
					update1.executeQuery(stmt1);
				}

				
				stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5, REPROCESS_USED_COUNT="+REPROCESS1_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;					
				System.out.println("stmt1="+stmt1);
				update1.executeQuery(stmt1);
			}

			gf.InsertReproCondition(REPROCESS_CONDITION_KEY, REPROCESSOR_KEY, OERStartTime, Complete, REPROCESS1_USED_COUNT, CLINICAL_STAFF_KEY,ReproResult_Processing);
			
			if(Cycle1ScopeCnt==2){
				System.out.println("The OER is Started with 2 Scopes by Staff:  "+Cycle1StaffID);
				System.out.println("Scope1:  "+Cycle1Scope1SerialNum);
				System.out.println("Scope2:  "+Cycle1Scope2SerialNum);
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 2, SCOPE2_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;

			}else{
				System.out.println("The OER is Started with 1 Scope by Staff:  "+Cycle1StaffID);
				System.out.println("Scope1:  "+Cycle1Scope1SerialNum);
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;

			}
			Thread.sleep((Cycle1DurationTime*60)*1000);
			OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

			System.out.println("The OER cycle completed at:  "+OERComplete2);

			stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_UnexpectedTerm+", REPROCESS_END_DATE=to_date('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS'), LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
			System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Error+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;
			System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			Thread.sleep((3*60)*1000);
			OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			stmt1="update T_Reprocess_Condition set LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
			System.out.println(stmt1);
			update1.executeQuery(stmt1);
			
			stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Available+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;
			System.out.println(stmt1);
			update1.executeQuery(stmt1);
			REPROCESS_CONDITION_KEY++;
		}else if (Cycle1End.equalsIgnoreCase("Unavailable")){
			if(DataType.equalsIgnoreCase("New")){
				gf.InsertReproInfo(Cycle1REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS1_USED_COUNT, Reprocessor_Processing);

			}else if(DataType.equalsIgnoreCase("Append")){
				stmt1="select REPROCESSOR_INFO_KEY from T_Reprocessor_Info where REPROCESSOR_KEY="+REPROCESSOR_KEY;
				System.out.println("stmt1="+stmt1);
				RS=update1.executeQuery(stmt1);
				while(RS.next()){
					REPROCESSOR_INFO_KEY = RS.getInt(1); 
					System.out.println("REPROCESSOR_INFO_KEY "+REPROCESSOR_INFO_KEY);
					}
				RS.close();
				if(REPROCESSOR_INFO_KEY==0){
					gf.InsertReproInfo(Cycle1REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS1_USED_COUNT, Reprocessor_Processing);

				}else {
					stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5, REPROCESS_USED_COUNT="+REPROCESS1_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;					
					System.out.println("stmt1="+stmt1);
					update1.executeQuery(stmt1);
				}

				
				stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5, REPROCESS_USED_COUNT="+REPROCESS1_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;					
				System.out.println("stmt1="+stmt1);
				update1.executeQuery(stmt1);
			}
			gf.InsertReproCondition(REPROCESS_CONDITION_KEY, REPROCESSOR_KEY, OERStartTime, Complete, REPROCESS1_USED_COUNT, CLINICAL_STAFF_KEY,ReproResult_Processing);
			
			if(Cycle1ScopeCnt==2){
				System.out.println("The OER is Started with 2 Scopes by Staff:  "+Cycle1StaffID);
				System.out.println("Scope1:  "+Cycle1Scope1SerialNum);
				System.out.println("Scope2:  "+Cycle1Scope2SerialNum);
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 2, SCOPE2_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;

			}else{
				System.out.println("The OER is Started with 1 Scope by Staff:  "+Cycle1StaffID);
				System.out.println("Scope1:  "+Cycle1Scope1SerialNum);
				gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
				REPROCESSED_SCOPES_KEY++;

			}
			Thread.sleep((Cycle1DurationTime*60)*1000);
			OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

			stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_NotConnected+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle1REPROCESSOR_INFO_KEY;
			System.out.println(stmt1);
			update1.executeQuery(stmt1);

		}else{
			System.out.println("Why is this happening?");
		}

		if(CycleCount.equalsIgnoreCase("2")){
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("++++++++++++++Cycle 2++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");

			Statement update2 = conn.createStatement();

			SCOPE1_KEY=gf.GetScope_Key(Cycle2Scope1SerialNum);
			if(Cycle2ScopeCnt==2){
				SCOPE2_KEY=gf.GetScope_Key(Cycle2Scope2SerialNum);
			}
			CLINICAL_STAFF_KEY=gf.GetStaff_Key(Cycle2StaffID);
			REPROCESSOR_MODEL_KEY=gf.GetReproModel_Key(Cycle2OERModel);
			REPROCESSOR_KEY=gf.GetRepro_Key(Cycle2OERSerialNum, REPROCESSOR_MODEL_KEY);
			if(DataType.equalsIgnoreCase("New")){
				Cycle2REPROCESSOR_INFO_KEY=REPROCESSOR_KEY;
			}else if(DataType.equalsIgnoreCase("Append")){
				Cycle2REPROCESSOR_INFO_KEY=gf.GetReproInfoKey(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd,REPROCESSOR_KEY);
				REPROCESS2_USED_COUNT=gf.GetReproCount(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd,REPROCESSOR_KEY);
			}
			
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
			Cycle2LagInt= Integer.parseInt(Cycle2Lag);			
			System.out.println("OER ("+Cycle2OERSerialNum+") is about to start.");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
			date=Calendar.getInstance();
			
			t=date.getTimeInMillis();
			Times = gf.KEServerDateTime_Duration(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd, Cycle2DurationTime);
			OERStartTime=Times[0];			
			System.out.println("The OER Started at:  "+OERStartTime );
			Complete=Times[1];
			System.out.println("The OER cycle should complete at:  "+Complete);			
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Cycle2 should end:  "+Cycle2End);
			
			if(Cycle2End.equalsIgnoreCase("Normal")){
				if(Cycle2REPROCESSOR_INFO_KEY!=Cycle1REPROCESSOR_INFO_KEY){
					gf.InsertReproInfo(Cycle2REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS2_USED_COUNT, Reprocessor_Processing);
				}else if(Cycle2REPROCESSOR_INFO_KEY==Cycle1REPROCESSOR_INFO_KEY){
					REPROCESS1_USED_COUNT++;
					REPROCESS2_USED_COUNT=REPROCESS1_USED_COUNT;
					stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5, REPROCESS_USED_COUNT="+REPROCESS1_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle2REPROCESSOR_INFO_KEY;					
					System.out.println("stmt1="+stmt1);
					update2.executeQuery(stmt1);
				}
				gf.InsertReproCondition(REPROCESS_CONDITION_KEY, REPROCESSOR_KEY, OERStartTime, Complete, REPROCESS2_USED_COUNT, CLINICAL_STAFF_KEY,ReproResult_Processing);

				if(Cycle2ScopeCnt==2){
					System.out.println("The OER is Started with 2 Scopes by Staff:  "+Cycle2StaffID);
					System.out.println("Scope1:  "+Cycle2Scope1SerialNum);
					System.out.println("Scope2:  "+Cycle2Scope2SerialNum);
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
					REPROCESSED_SCOPES_KEY++;
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 2, SCOPE2_KEY, CLINICAL_STAFF_KEY);
					REPROCESSED_SCOPES_KEY++;

				}else{
					System.out.println("The OER is Started with 1 Scope by Staff:  "+Cycle2StaffID);
					System.out.println("Scope1:  "+Cycle2Scope1SerialNum);
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
					REPROCESSED_SCOPES_KEY++;

				}
				Thread.sleep((Cycle2DurationTime*60)*1000);
				OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
				System.out.println("The OER cycle2 completed at:  "+OERComplete2);

				stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_ProcessComplete+", REPROCESS_END_DATE=to_date('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS'), LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);
				
				stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Complete+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle2REPROCESSOR_INFO_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);
				
				Thread.sleep((2*60)*1000);
				OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
				stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_ScopeRemoved+", LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);
				
				stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Available+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle2REPROCESSOR_INFO_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);
				REPROCESS_CONDITION_KEY++;
			}else if (Cycle2End.equalsIgnoreCase("Unexpected Termination")){								
				if(Cycle2REPROCESSOR_INFO_KEY!=Cycle1REPROCESSOR_INFO_KEY){
					gf.InsertReproInfo(Cycle2REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS2_USED_COUNT, Reprocessor_Processing);
				}else if(Cycle2REPROCESSOR_INFO_KEY==Cycle1REPROCESSOR_INFO_KEY){
					REPROCESS1_USED_COUNT++;
					REPROCESS2_USED_COUNT=REPROCESS1_USED_COUNT;
					stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Processing+", REPROCESS_USED_COUNT="+REPROCESS2_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle2REPROCESSOR_INFO_KEY;
					System.out.println("stmt1="+stmt1);
					update2.executeQuery(stmt1);
				}

				gf.InsertReproCondition(REPROCESS_CONDITION_KEY, REPROCESSOR_KEY, OERStartTime, Complete, REPROCESS2_USED_COUNT, CLINICAL_STAFF_KEY,ReproResult_Processing);								
				if(Cycle2ScopeCnt==2){
					System.out.println("The OER is Started with 2 Scopes by Staff:  "+Cycle2StaffID);
					System.out.println("Scope1:  "+Cycle2Scope1SerialNum);
					System.out.println("Scope2:  "+Cycle2Scope2SerialNum);
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
					REPROCESSED_SCOPES_KEY++;
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 2, SCOPE2_KEY, CLINICAL_STAFF_KEY);
					REPROCESSED_SCOPES_KEY++;

				}else{
					System.out.println("The OER is Started with 1 Scope by Staff:  "+Cycle2StaffID);
					System.out.println("Scope1:  "+Cycle2Scope1SerialNum);
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
					REPROCESSED_SCOPES_KEY++;
				}
				Thread.sleep((Cycle2DurationTime*60)*1000);
				OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

				System.out.println("The OER cycle completed at:  "+OERComplete);
				System.out.println("The OER cycle2 completed at:  "+OERComplete2);

				stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_UnexpectedTerm+", REPROCESS_END_DATE=to_date('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS'), LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);
				
				stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Error+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle2REPROCESSOR_INFO_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);
				
				Thread.sleep((3*60)*1000);
				OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
				stmt1="update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResult_ScopeRemoved+", LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+REPROCESS_CONDITION_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);
				
				stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_Available+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle2REPROCESSOR_INFO_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);
				REPROCESS_CONDITION_KEY++;

			}else if (Cycle2End.equalsIgnoreCase("Unavailable")){
				if(Cycle2REPROCESSOR_INFO_KEY!=Cycle1REPROCESSOR_INFO_KEY){
					gf.InsertReproInfo(Cycle2REPROCESSOR_INFO_KEY, REPROCESSOR_KEY, REPROCESS2_USED_COUNT, Reprocessor_Processing);
				}else if(Cycle2REPROCESSOR_INFO_KEY==Cycle1REPROCESSOR_INFO_KEY){
					REPROCESS1_USED_COUNT++;
					REPROCESS2_USED_COUNT=REPROCESS1_USED_COUNT;
					stmt1="Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5, REPROCESS_USED_COUNT="+REPROCESS2_USED_COUNT+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle2REPROCESSOR_INFO_KEY;
					System.out.println("stmt1="+stmt1);
					update2.executeQuery(stmt1);
				}
				gf.InsertReproCondition(REPROCESS_CONDITION_KEY, REPROCESSOR_KEY, OERStartTime, Complete, REPROCESS2_USED_COUNT, CLINICAL_STAFF_KEY,ReproResult_Processing);
				
				if(Cycle2ScopeCnt==2){
					System.out.println("The OER is Started with 2 Scopes by Staff:  "+Cycle2StaffID);
					System.out.println("Scope1:  "+Cycle2Scope1SerialNum);
					System.out.println("Scope2:  "+Cycle2Scope2SerialNum);
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
					REPROCESSED_SCOPES_KEY++;
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 2, SCOPE2_KEY, CLINICAL_STAFF_KEY);			
					REPROCESSED_SCOPES_KEY++;

				}else{
					System.out.println("The OER is Started with 1 Scope by Staff:  "+Cycle2StaffID);
					System.out.println("Scope1:  "+Cycle2Scope1SerialNum);
					gf.InsertnReproScopes(REPROCESSED_SCOPES_KEY, REPROCESS_CONDITION_KEY, 1, SCOPE1_KEY, CLINICAL_STAFF_KEY);
					REPROCESSED_SCOPES_KEY++;
				}
				Thread.sleep((Cycle2DurationTime*60)*1000);
				OERComplete2 = gf.KEServerDateTime(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

				stmt1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+Reprocessor_NotConnected+", QV_UPDATE_DATE=TO_TIMESTAMP('"+OERComplete2+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+Cycle2REPROCESSOR_INFO_KEY;
				System.out.println(stmt1);
				update2.executeQuery(stmt1);

			}else{
				System.out.println("Why is this happening?");
			}


		}
	}
}
