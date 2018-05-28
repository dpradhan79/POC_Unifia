package KEDataGenerator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import KEDataGenerator.JFilePicker;
import KEDataGenerator.DatabaseRelated;
import KEDataGenerator.Utilities;

public class DataFlow {
	
	public static JFilePicker XMLData;
	public static DatabaseRelated DB;
	public static Connection conn;
	public static ResultSet rs;
	public static int dbRepModelKey,dbRepKey,dbRepInfoKey,dbRepComplete,ReproResScopeRem;
	public static String dbClinicalStaff,dbOERStartTime,dbOERCompleteTime,dbOERComplete2;
	public static Integer dbRepConditionKey=100,dbRepUsedCount,dbRepScopesKey=100,dbIndexNo,dbRepResProcessComplete,dbRepAvailable;
	public static Integer[] dbScopeKey;
	public static Boolean bFlag=false;
	public static Integer iCntr=0;
	
	public static Integer repResProcessing=1;
	public static Integer repResUnexpectedTermination=2;
	public static Integer repResScopeRemoved=3;
	public static Integer repResProcessingComplete=5;
	
	public static Integer repStatusAvaialbe=4;
	public static Integer repStatusProcessing=5;
	public static Integer repStatusError=7;
	public static Integer repStatusProcCompelete=9;
	public static Integer iCycleCount=0;
	public static void Data() throws SQLException, ClassNotFoundException, NumberFormatException, InterruptedException, IOException{
		iCycleCount++;
		if (XMLData.DataType.equalsIgnoreCase("NEW")){
			iCntr=iCntr+1;
			//Connect to Oracle and insert Data
			if (!bFlag){
				try {
					DB.InsertSeedData(XMLData.KE_Url,XMLData.user,XMLData.pwd);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// refresh Unifia DB + Restart IIS
				String UnifiaDBurl = "jdbc:sqlserver://"+XMLData.UnifiaAppName+"\\sqlexpress;databaseName=Unifia"; 
				Utilities.InsertSimulatedScanSeedData(UnifiaDBurl, Utilities.UnifiaDBUserName,Utilities.UnifiaDBPassword,1, 0, 0);
				Utilities.RestartIISServices(XMLData.UnifiaAppName, Utilities.UnifiaMachineUserName, Utilities.UnifiaMachinePassword);
			}
			bFlag=true;
		}
		
		Class.forName("oracle.jdbc.driver.OracleDriver"); 			
		conn= DriverManager.getConnection(XMLData.KE_Url, XMLData.user, XMLData.pwd);
		Statement st = conn.createStatement();
		if ((XMLData.DataType.equalsIgnoreCase("APPEND"))||(iCntr>1)){
			//dbRepConditionKey
			rs=st.executeQuery("select max(REPROCESS_CONDITION_KEY) from T_Reprocess_Condition");
			while(rs.next()){
				dbRepConditionKey= rs.getInt(1);
			}
			dbRepConditionKey++;
		}
		
		//Reprocessor Model Key
		st = conn.createStatement();
		rs = st.executeQuery("select REPROCESSOR_MODEL_KEY from M_REPROCESSOR_MODEL where REPROCESSOR_MODEL_NAME='"+XMLData.OERModel+"'");
		while(rs.next()){
			dbRepModelKey= rs.getInt(1);
		}
		
		//Reprocessor Info Key
		rs = st.executeQuery("select REPROCESSOR_KEY from m_reprocessor where SERIAL_NO='"+XMLData.OERSerialNumber+"' and REPROCESSOR_MODEL_KEY="+dbRepModelKey);
		while(rs.next()){
			dbRepKey= rs.getInt(1);
		}
		
		dbRepInfoKey=dbRepKey;
		
		if ((XMLData.DataType.equalsIgnoreCase("APPEND"))||(iCntr>1)){
			st = conn.createStatement();
			rs = st.executeQuery("select max(REPROCESSED_SCOPES_KEY) from T_Reprocessed_Scopes");
			while(rs.next()){
				dbRepScopesKey= rs.getInt(1);
			}
			dbRepScopesKey++;
			st = conn.createStatement();
			dbRepUsedCount=0;
			rs = st.executeQuery("select REPROCESS_USED_COUNT from T_Reprocessor_Info where REPROCESSOR_KEY="+dbRepKey);
			while(rs.next()){
				dbRepUsedCount= rs.getInt(1);
			}
			dbRepUsedCount++;
			dbRepAvailable=0;
			st = conn.createStatement();
			rs = st.executeQuery("select REPROCESSOR_INFO_KEY from T_Reprocessor_Info where REPROCESSOR_KEY="+dbRepInfoKey);
			while(rs.next()){
				dbRepAvailable= rs.getInt(1);
			}
		}else{
			dbRepUsedCount=1;
		}
			
		//ScopeKey
		dbScopeKey=new Integer[Integer.parseInt(XMLData.NumberofScopes)];
		for (int scopecnt=0; scopecnt<Integer.parseInt(XMLData.NumberofScopes);scopecnt++){
			rs =st.executeQuery("select SCOPE_KEY from M_Scope where SERIAL_NO='"+XMLData.ScopeSerialNumber[scopecnt]+"'");
			while(rs.next()){
				dbScopeKey[scopecnt]= rs.getInt(1);
			}
		}
		rs =st.executeQuery("select CLINICAL_STAFF_KEY from M_User where USER_ID='"+XMLData.staff+"'");
		while(rs.next()){
			dbClinicalStaff= rs.getString(1);
		}
		rs =st.executeQuery("SELECT TO_CHAR(SYSDATE, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL");
		while(rs.next()){
			dbOERStartTime= rs.getString(1);
		}
		rs =st.executeQuery("SELECT TO_CHAR(SYSDATE + "+XMLData.Duration+"/1440, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL");
		while(rs.next()){
			dbOERCompleteTime= rs.getString(1);
		}
		
		//T_Reprocessor_Info
		Statement update= conn.createStatement();
		System.out.println("Changing the reprocessor and location status to Processing");
		if ((XMLData.DataType.equalsIgnoreCase("APPEND"))||(iCntr>1)){
			
			if (dbRepAvailable<=0){
				Statement insert= conn.createStatement();
				//Making location to processing
				insert.execute("insert into T_Reprocessor_Info(REPROCESSOR_INFO_KEY,REPROCESSOR_KEY,VERSION,DISINFECT_BOTTLECOUNT,"
				+ "REPROCESS_USED_COUNT,REPROCESSOR_STATUS_KEY,	DISINFECTION_OPERATION_COUNT,DISINFECTION_ELAPSED_DAY_COUNT,"
				+ "TIME_LAG_MINUTES,NOTIFICATION_HIDE_FLG) values("+dbRepInfoKey+","+dbRepKey+","
				+ "'3.03',2,"+dbRepUsedCount+","+repStatusProcessing+",15,3,0,0)");
			}else{
				//Making location to available
				rs =st.executeQuery("SELECT TO_CHAR(SYSDATE + "+XMLData.Duration+"/1440, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL");
				while(rs.next()){
					dbOERComplete2= rs.getString(1);
				}
				update.executeQuery("update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+repStatusAvaialbe+", "
				+ "QV_UPDATE_DATE=TO_TIMESTAMP('"+dbOERComplete2+"','MM-DD-YYYY HH24:MI:SS') "
				+ "where REPROCESSOR_INFO_KEY="+dbRepInfoKey);
				//Thread.sleep(60000);
				//Making location to processing
				//Statement update= conn.createStatement();
				update.executeQuery("Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+repStatusProcessing+",REPROCESS_USED_COUNT="+dbRepUsedCount+", "
				+ "QV_UPDATE_DATE=TO_TIMESTAMP('"+dbOERStartTime+"','MM-DD-YYYY HH24:MI:SS') where REPROCESSOR_INFO_KEY="+dbRepInfoKey);
			}
		}else{
			Statement insert= conn.createStatement();
			//Making location to processing
			insert.execute("insert into T_Reprocessor_Info(REPROCESSOR_INFO_KEY,REPROCESSOR_KEY,VERSION,DISINFECT_BOTTLECOUNT,"
					+ "REPROCESS_USED_COUNT,REPROCESSOR_STATUS_KEY,	DISINFECTION_OPERATION_COUNT,DISINFECTION_ELAPSED_DAY_COUNT,"
					+ "TIME_LAG_MINUTES,NOTIFICATION_HIDE_FLG) values("+dbRepInfoKey+","+dbRepKey+","
					+ "'3.03',2,"+dbRepUsedCount+","+repStatusProcessing+",15,3,0,0)");
		}
		
		//T_Reprocess_Condition
		Statement insert= conn.createStatement();
		insert.execute("insert into T_Reprocess_Condition(REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG, REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,"
		+ "PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,"
		+ "WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,"
		+ "START_PERSON_KEY,END_PERSON_KEY,DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_"
		+ "FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME)"
		+ "	values ("+dbRepConditionKey+",1,"+dbRepKey+",to_date('"+dbOERStartTime+"',"
		+ "'MM-DD-YYYY HH24:MI:SS'), to_date('"+dbOERCompleteTime+"','MM-DD-YYYY HH24:MI:SS'),1,3,7,20,2,21,"
		+ "21,99,99,"+dbRepUsedCount+",68,"+dbClinicalStaff+","+dbClinicalStaff+",0,"
		+repResProcessing+",11,1,2,1,0,TO_TIMESTAMP('"+dbOERStartTime+"','MM-DD-YYYY HH24:MI:SS'))");	
		
		
		//Reprocessed_Scopes
		rs =st.executeQuery("select count(*) from T_Reprocessed_Scopes");
		while(rs.next()){
			dbIndexNo= rs.getInt(1);
		}
		
		if (dbIndexNo==0||dbIndexNo==null){
			dbIndexNo=1;
		}else{
			dbIndexNo=dbIndexNo+1;
		}
		for (int scopecnt=0; scopecnt<Integer.parseInt(XMLData.NumberofScopes);scopecnt++){
			insert.execute("insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,"
			+ "SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,"
			+ "NOTIFICATION_HIDE_FLG,DELETED_FLG) values ("+dbRepScopesKey+","+dbRepConditionKey+","
			+ ""+dbIndexNo+","+dbScopeKey[scopecnt]+",0,1,"+dbClinicalStaff+","+dbClinicalStaff+",0,0)");
			dbRepScopesKey=dbRepScopesKey+1;
			dbIndexNo=dbIndexNo+1;
		}
		insert.close();
		
		System.out.println("Waiting for "+XMLData.Duration+" minutes for Cycle - "+iCycleCount +" for processing");
		Thread.sleep((Integer.parseInt(XMLData.Duration)*60)*1000);
		rs =st.executeQuery("SELECT TO_CHAR(SYSDATE + "+XMLData.Duration+"/1440, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL");
		while(rs.next()){
			dbOERComplete2= rs.getString(1);
		}
		
		//Update Queries
		if (XMLData.CycleEnd.equalsIgnoreCase("Normal")){
			System.out.println("Changing Reprocssing status and Location Status to Processing Complete");
			dbRepResProcessComplete=repResProcessingComplete; ///5
			//Statement update= conn.createStatement();
			update.executeQuery("update T_Reprocess_Condition set REPROCESS_RESULT_KEY="
			+ "'"+dbRepResProcessComplete+"', REPROCESS_END_DATE=to_date('"+dbOERComplete2+"',"
			+ "'MM-DD-YYYY HH24:MI:SS'), LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+dbOERComplete2+"',"
			+ "'MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+dbRepConditionKey);
			
			dbRepComplete=repStatusProcCompelete; //9
			update.executeQuery("update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+dbRepComplete+", "
			+ "QV_UPDATE_DATE=TO_TIMESTAMP('"+dbOERComplete2+"','MM-DD-YYYY HH24:MI:SS') "
			+ "where REPROCESSOR_INFO_KEY="+dbRepInfoKey);
			
			System.out.println("Waiting for 2 minutes");
			Thread.sleep((2*60)*1000);
			
			rs =st.executeQuery("SELECT TO_CHAR(SYSDATE + "+XMLData.Duration+"/1440, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL");
			while(rs.next()){
				dbOERComplete2= rs.getString(1);
			}
			
			// changing the reprocessor status to Scope Removed
			System.out.println("Changing the reprocessor status to Scope Removed");
			ReproResScopeRem= repResScopeRemoved; //3	
			
			System.out.println("Waiting for 2 minutes for Cycle - "+iCycleCount+" before changing the Reprocess result to scope removed");
			update.executeQuery("update T_Reprocess_Condition set REPROCESS_RESULT_KEY="+ReproResScopeRem+""
			+ ",LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+dbOERComplete2+"','MM-DD-YYYY HH24:MI:SS')"
			+ "where REPROCESS_CONDITION_KEY="+dbRepConditionKey);
			if(Integer.parseInt(XMLData.NumofCycles)>1){
				dbRepConditionKey=dbRepConditionKey+1;
			}
	}
		else if (XMLData.CycleEnd.equalsIgnoreCase("Unexpected Termination")){
			dbRepResProcessComplete=repResUnexpectedTermination; ///2
			System.out.println("Changing the reprocessor status to Unexpected Termination and location status to Error");
			//Statement update= conn.createStatement();
			update.executeQuery("update T_Reprocess_Condition set REPROCESS_RESULT_KEY="
			+ "'"+dbRepResProcessComplete+"', REPROCESS_END_DATE=to_date('"+dbOERComplete2+"',"
			+ "'MM-DD-YYYY HH24:MI:SS'), LAST_UPDATE_DATE_TIME=TO_TIMESTAMP('"+dbOERComplete2+"',"
			+ "'MM-DD-YYYY HH24:MI:SS') where REPROCESS_CONDITION_KEY="+dbRepConditionKey);
			
			dbRepComplete=repStatusError; ///7
			//Changing the Location status to Error
			String str1="update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+dbRepComplete+", "
					+ "QV_UPDATE_DATE=TO_TIMESTAMP('"+dbOERComplete2+"','MM-DD-YYYY HH24:MI:SS') "
					+ "where REPROCESSOR_INFO_KEY="+dbRepInfoKey;
			//System.out.println(str1);
			update.executeQuery(str1);
			
		}
		System.out.println("Waiting for 2 minutes");
		Thread.sleep((2*60)*1000);
		//repStatusAvaialbe=4;
		//Changing the Location status to Available
		
		rs =st.executeQuery("SELECT TO_CHAR(SYSDATE + "+XMLData.Duration+"/1440, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL");
		while(rs.next()){
			dbOERComplete2= rs.getString(1);
		}
		
		System.out.println("Changing the Location status to Available");
		update.executeQuery("update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY="+repStatusAvaialbe+", "
		+ "QV_UPDATE_DATE=TO_TIMESTAMP('"+dbOERComplete2+"','MM-DD-YYYY HH24:MI:SS') "
		+ "where REPROCESSOR_INFO_KEY="+dbRepInfoKey);
		System.out.println("Waiting for 2 minutes");
		Thread.sleep((2*60)*1000);
		
			if(Integer.parseInt(XMLData.NumofCycles)>1){
				dbRepConditionKey=dbRepConditionKey+1;
			}
		}
				
}
