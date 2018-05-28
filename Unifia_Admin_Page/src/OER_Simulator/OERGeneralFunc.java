package OER_Simulator;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import TestFrameWork.OER_Simulator_SE_objects;
import TestFrameWork.Unifia_Admin_Selenium;

import java.io.IOException;
public class OERGeneralFunc extends OER_Simulator_SE_objects {
	// Generic Functions
	
	public static String Verify_ErrCode(String ErrCode) throws InterruptedException{
		Thread.sleep(5000);
		System.out.println("Error Code = '"+ErrCode+"'");
		Actual=driver.findElementById("error_code").getAttribute("value");
		System.out.println("Actual Code = '"+Actual+"'");
		if(ErrCode.equals(Actual)){
			Result="Pass";
		}else{
			Result="Fail - Error Code was supposed to be "+ErrCode+", but it is "+Actual ;
			System.out.println(Result);
		}
		return Result;
	}
	public static String Verify_Form_ErrMsg(String ErrMsg){
		Actual=driver.findElementById("FormError").getText();
		System.out.println("Actual message = '"+Actual+"'");
		System.out.println("Expected message = '"+ErrMsg+"'");
		//if(Actual.equalsIgnoreCase(ErrMsg)){
		if(Actual.startsWith(ErrMsg)){
			Result="Pass";
		}else{
			Result="Fail";
		}
		return Result;
	}
	public static void waitforOneSecond () throws InterruptedException{
		Thread.sleep(1000);
	}
	
	
	public static void InsertSQLData(String url, String user, String pass ) {
		Connection conn= null;
		String stmt;
			//deallocate cur
		String stmt1= "";
  	   	stmt="";
  	   	String stmt6="";
  	   	String stmt7="";
        System.out.println("stmt="+stmt);
	    
	   	try{
    		conn= DriverManager.getConnection(url, user, pass);		
    		Statement update1 = conn.createStatement();
    		System.out.println("stmt="+stmt1);
    		update1.executeUpdate(stmt1);
    		System.out.println("stmt6="+stmt6);
    		update1.executeUpdate(stmt6);
    		System.out.println("stmt7="+stmt7);
    		update1.executeUpdate(stmt7);
    		conn.close();
    	}
		catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
	}
	
   	public static int GetReproConditionKey(String url, String user, String pass ) throws SQLException {
		Connection conn= null;
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement update1 = conn.createStatement();
		int REPROCESS_CONDITION_KEY=0;
		ResultSet RS;
		String stmt1;
		stmt1="select max(REPROCESS_CONDITION_KEY) from T_Reprocess_Condition";
 		RS = update1.executeQuery(stmt1);
		while(RS.next()){
			REPROCESS_CONDITION_KEY = RS.getInt(1); 
			System.out.println("REPROCESS_CONDITION_KEY "+REPROCESS_CONDITION_KEY);
			}
		RS.close();
		return REPROCESS_CONDITION_KEY;
   	}

   	public static int GetReproScopeKey(String url, String user, String pass ) throws SQLException {
		Connection conn= null;
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement update1 = conn.createStatement();
		int REPROCESSED_SCOPES_KEY=0;
		ResultSet RS;
		String stmt1;
		stmt1="select max(REPROCESSED_SCOPES_KEY) from T_Reprocessed_Scopes";
 		RS = update1.executeQuery(stmt1);
		while(RS.next()){
			REPROCESSED_SCOPES_KEY = RS.getInt(1); 
			System.out.println("REPROCESSED_SCOPES_KEY "+REPROCESSED_SCOPES_KEY);
			}
		RS.close();
		return REPROCESSED_SCOPES_KEY;
   	}

   	public static int GetReproInfoKey(String url, String user, String pass, int REPROCESSOR_KEY ) throws SQLException {
		Connection conn= null;
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement update1 = conn.createStatement();
		int REPROCESSOR_INFO_KEY=0;
		ResultSet RS;
		String stmt1;
		stmt1="select REPROCESSOR_INFO_KEY from T_Reprocessor_Info where REPROCESSOR_KEY="+REPROCESSOR_KEY;
 		RS = update1.executeQuery(stmt1);
		while(RS.next()){
			REPROCESSOR_INFO_KEY = RS.getInt(1); 
			System.out.println("REPROCESSOR_INFO_KEY "+REPROCESSOR_INFO_KEY);
			}
		RS.close();
		if(REPROCESSOR_INFO_KEY==0){
			stmt1="select max(REPROCESSOR_INFO_KEY) from T_Reprocessor_Info";
	 		RS = update1.executeQuery(stmt1);
			while(RS.next()){
				REPROCESSOR_INFO_KEY = RS.getInt(1); 
				System.out.println("REPROCESSOR_INFO_KEY "+REPROCESSOR_INFO_KEY);
				}
			RS.close();
			if(REPROCESSOR_INFO_KEY==0){
				REPROCESSOR_INFO_KEY=REPROCESSOR_KEY;
			}else {
				REPROCESSOR_INFO_KEY++;
			}
		}
		
		return REPROCESSOR_INFO_KEY;
   	}

   	public static int ReproInfoKeyAvailable(String url, String user, String pass, int REPROCESSOR_INFO_KEY ) throws SQLException {
		Connection conn= null;
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement update1 = conn.createStatement();
		int New_REPROCESSOR_INFO_KEY=0;
		ResultSet RS;
		String stmt1;
		stmt1="select REPROCESSOR_INFO_KEY from T_Reprocessor_Info where REPROCESSOR_KEY="+REPROCESSOR_INFO_KEY;
 		RS = update1.executeQuery(stmt1);
		while(RS.next()){
			New_REPROCESSOR_INFO_KEY = RS.getInt(1); 
			System.out.println("REPROCESSOR_INFO_KEY "+REPROCESSOR_INFO_KEY);
			}
		RS.close();
		
		return New_REPROCESSOR_INFO_KEY;
   	}

   	public static int GetReproCount(String url, String user, String pass, int REPROCESSOR_KEY ) throws SQLException {
		Connection conn= null;
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement update1 = conn.createStatement();
		int REPROCESS_USED_COUNT=0;
		ResultSet RS;
		String stmt1;
		stmt1="select REPROCESS_USED_COUNT from T_Reprocessor_Info where REPROCESSOR_KEY="+REPROCESSOR_KEY;
 		RS = update1.executeQuery(stmt1);
		while(RS.next()){
			REPROCESS_USED_COUNT = RS.getInt(1); 
			System.out.println("REPROCESS_USED_COUNT "+REPROCESS_USED_COUNT);
			}
		RS.close();
		if(REPROCESS_USED_COUNT==0){
			REPROCESS_USED_COUNT++;

		}
		return REPROCESS_USED_COUNT;
   	}

	   	public static void InsertOracleLData(String url, String user, String pass ) throws SQLException {
		Connection conn= null;
		System.out.println("Gen Function to clear all data and insert seed data");
		//deallocate cur
		//String stmt1= "delete from M_Scope where SCOPE_KEY > '2';"
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement st = conn.createStatement();

		String stmt1="delete T_Reprocessed_Scopes;"
				+"commit;"
				+"delete t_reprocess_condition; "
				+"delete t_reprocessor_mainte;"
				+"delete t_reprocessor_parts_change;"
				+"delete t_reprocessor_wash_info;"
				+"delete t_reprocessor_wash_log;"
				+"delete t_oer_error_log;"
				+"delete t_oer_maintenance_data;"
				+"delete t_oer_reprocessing_log;"
				+"delete T_REPROCESSOR_INFO;"
				+"commit;"
				+"delete from M_Scope where SCOPE_KEY>10;"
				+"delete from M_SCOPE_MODEL where Scope_Model_Key> 120 and Scope_Model_Key<100000000;"
				+"delete from m_reprocessor_programs;"
				+"delete from m_reprocessor;"
				+"delete from M_User where USER_KEY > 7 and USER_KEY<10000;"
				+"delete from M_CLINICAL_STAFF where CLINICAL_STAFF_KEY > 7 and CLINICAL_STAFF_KEY<10000;"
				+"commit;";
		System.out.println("stmt1="+stmt1);
	    
	   	try{
    		String[] breakstmt=stmt1.split(";"); 
    		for(int i=0; i< breakstmt.length; i++ ){
    			System.out.println(breakstmt[i]);
    			st.addBatch(breakstmt[i]);
    			st.executeBatch();
    		}
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
	   	
	   	String stmt2="insert into M_SCOPE_MODEL (Scope_Model_Key,Scope_Model_Name,Channel_Size,ACTIVE_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID,LINK_ALL_EXAM_TYPE_FLG) "
	   			+" select 183,'GIF-H190','2',1,'Olympus Code',108,'nicole','nicole',1 from dual union all" 
	   			+" select 184,'GIF-HQ190','2',1,'Olympus Code',109,'nicole','nicole',1 from dual union all" 
	   			+" select 200,'CF-H190L','2',1,'Olympus Code',110,'nicole','nicole',1 from dual union all" 
	   			+" select 201,'CF-HQ190L','2',1,'Olympus Code',111,'nicole','nicole',1 from dual union all" 
	   			+" select 155,'TJF-Q180V','1.5',1,'Olympus Code',154,'nicole','nicole',1 from dual union all" 
	   			+" select 170,'GIF-1TH190','1',1,'Olympus Code',155,'nicole','nicole',1 from dual union all" 
	   			+" select 202,'BF-1TQ180','1',1,'Olympus Code',156,'nicole','nicole',1 from dual union all" 
	   			+" select 203,'CF-30L','1.2',1,'Olympus Code',157,'nicole','nicole',1 from dual union all" 
	   			+" select 204,'CF-30M','1',1,'Olympus Code',158,'nicole','nicole',1 from dual union all" 
	   			+" select 205,'CF-40L','1',1,'Olympus Code',159,'nicole','nicole',1 from dual; " 
	   			+" insert into M_Scope(SCOPE_KEY,SERIAL_NO,SCOPE_NAME,ADMIN_REG_FLG,SCOPE_MODEL_KEY,PURCHASE_DATE,PURCHASE_COST,NOTE,SCOPE_STATUS_KEY,EXTERNAL_CODE,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) " 
	   			+" select 101,'1122334','Scope1',1,183,NULL,NULL,NULL,1,NULL,1,0,'101',1,'nicole','nicole' from dual union all" 
	   			+" select 102,'2233445','Scope2',1,184,NULL,NULL,NULL,1,NULL,1,0,'102',2,'nicole','nicole' from dual union all" 
	   			+" select 103,'3344556','Scope3',1,94,NULL,NULL,NULL,1,NULL,1,0,'103',3,'nicole','nicole' from dual union all" 
	   			+" select 104,'4455667','Scope4',1,200,NULL,NULL,NULL,1,NULL,1,0,'104',4,'nicole','nicole' from dual union all" 
	   			+" select 105,'5566778','Scope5',1,201,NULL,NULL,NULL,1,NULL,1,0,'105',5,'nicole','nicole' from dual union all" 
	   			+" select 106,'6677889','Scope6',1,200,NULL,NULL,NULL,1,NULL,1,0,'106',6,'nicole','nicole' from dual union all" 
	   			+" select 107,'7654231','Scope7',1,155,NULL,NULL,NULL,1,NULL,1,0,'107',7,'nicole','nicole' from dual union all" 
	   			+" select 108,'6543216','Scope8',1,170,NULL,NULL,NULL,1,NULL,1,0,'108',8,'nicole','nicole' from dual union all" 
	   			+" select 109,'9876432','Scope9',1,202,NULL,NULL,NULL,1,NULL,1,0,'109',9,'nicole','nicole' from dual union all" 
	   			+" select 110,'7654321','Scope10',1,155,NULL,NULL,NULL,1,NULL,1,0,'110',10,'nicole','nicole' from dual union all" 
	   			+" select 111,'9988776','Scope11',1,184,NULL,NULL,NULL,1,NULL,1,0,'111',11,'nicole','nicole' from dual union all" 
	   			+" select 112,'2808645','Scope12',1,94,NULL,NULL,NULL,1,NULL,1,0,'112',12,'nicole','nicole' from dual union all" 
	   			+" select 113,'2600842','Scope13',1,94,NULL,NULL,NULL,1,NULL,1,0,'113',13,'nicole','nicole' from dual union all" 
	   			+" select 114,'2806734','Scope14',1,94,NULL,NULL,NULL,1,NULL,1,0,'114',14,'nicole','nicole' from dual union all" 
	   			+" select 115,'2200174','Scope15',1,183,NULL,NULL,NULL,1,NULL,1,0,'115',15,'nicole','nicole' from dual union all" 
	   			+" select 116,'2601311','Scope16',1,94,NULL,NULL,NULL,1,NULL,1,0,'116',16,'nicole','nicole' from dual union all" 
	   			+" select 117,'2600163','Scope17',1,94,NULL,NULL,NULL,1,NULL,1,0,'117',17,'nicole','nicole' from dual union all" 
	   			+" select 118,'2211009','Scope18',1,203,NULL,NULL,NULL,1,NULL,0,0,'118',18,'nicole','nicole' from dual union all" 
	   			+" select 119,'2809094','Scope19',1,88,NULL,NULL,NULL,1,NULL,0,0,'119',19,'nicole','nicole' from dual union all" 
	   			+" select 120,'0099887','Scope20',1,204,NULL,NULL,NULL,1,NULL,0,0,'120',20,'nicole','nicole' from dual union all" 
	   			+" select 124,'2600319','Scope24',1,96,NULL,NULL,NULL,1,NULL,1,0,'124',24,'nicole','nicole' from dual union all" 
	   			+" select 125,'2602572','Scope25',1,88,NULL,NULL,NULL,1,NULL,1,0,'125',25,'nicole','nicole' from dual union all" 
	   			+" select 126,'2601132','Scope26',1,94,NULL,NULL,NULL,1,NULL,1,0,'126',26,'nicole','nicole' from dual union all" 
	   			+" select 127,'2600266','Scope27',1,94,NULL,NULL,NULL,1,NULL,1,0,'127',27,'nicole','nicole' from dual union all" 
	   			+" select 128,'2707317','Scope28',1,88,NULL,NULL,NULL,1,NULL,1,0,'128',28,'nicole','nicole' from dual union all" 
	   			+" select 129,'5556667','Scope29',1,205,NULL,NULL,NULL,1,NULL,1,0,'129',29,'nicole','nicole' from dual union all" 
	   			+" select 130,'6667778','Scope30',1,205,NULL,NULL,NULL,1,NULL,1,0,'130',30,'nicole','nicole' from dual union all" 
	   			+" select 131,'7778889','Scope31',1,155,NULL,NULL,NULL,1,NULL,1,0,'131',31,'nicole','nicole' from dual union all" 
	   			+" select 132,'8889990','Scope32',1,155,NULL,NULL,NULL,1,NULL,1,0,'132',32,'nicole','nicole' from dual union all" 
	   			+" select 133,'2200687','Scope33',1,184,NULL,NULL,NULL,1,NULL,1,0,'133',33,'nicole','nicole' from dual union all" 
	   			+" select 134,'2500850','Scope34',1,88,NULL,NULL,NULL,1,NULL,1,0,'134',34,'nicole','nicole' from dual union all" 
	   			+" select 135,'2600714','Scope35',1,94,NULL,NULL,NULL,1,NULL,1,0,'135',35,'nicole','nicole' from dual;" 
	   			+" insert into m_reprocessor(REPROCESSOR_KEY,SERIAL_NO,REPROCESSOR_NAME,REPROCESSOR_MODEL_KEY,PURCHASE_DATE,PURCHASE_COST,FACILITY_KEY,DATA_COOP_FLG,REPROCESSING_ROOM_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) " 
	   			+" select 1,'2000001','Reprocessor 1',1,NULL,NULL,1,0,1,1,0,1,1,'nicole','nicole' from dual union all" 
	   			+" select 2,'2000002','Reprocessor 2',1,NULL,NULL,1,0,1,1,0,2,2,'nicole','nicole' from dual union all" 
	   			+" select 3,'2000003','Reprocessor 3',1,NULL,NULL,1,0,1,1,0,3,3,'nicole','nicole' from dual union all" 
	   			+" select 4,'2000004','Reprocessor 4',1,NULL,NULL,1,0,1,1,0,4,4,'nicole','nicole' from dual union all" 
	   			+" select 5,'2000005','Reprocessor 5',1,NULL,NULL,1,0,1,1,0,5,5,'nicole','nicole' from dual union all" 
	   			+" select 6,'2000006','Reprocessor 6',1,NULL,NULL,1,0,1,1,0,6,6,'nicole','nicole' from dual;" 
	   			+" commit;"
	   			+"insert into m_reprocessor_programs(REPROCESSOR_PROGRAMS_KEY,REPROCESSOR_KEY,INDEX_NO,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID)" 
	   			+" select 11,1,1,1,0,'11',1,'nicole','nicole' from dual union all" 
	   			+" select 12,1,2,1,0,'12',2,'nicole','nicole' from dual union all" 
	   			+" select 13,1,3,1,0,'13',3,'nicole','nicole' from dual;" 
	   			+" commit;";

		System.out.println("stmt2="+stmt2);
	    
	   	try{
    		String[] breakstmt=stmt2.split(";"); 
    		for(int i=0; i< breakstmt.length; i++ ){
    			System.out.println(breakstmt[i]);
    			st.addBatch(breakstmt[i]);
    			st.executeBatch();
    		}
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
	   	
	   	String stmt3=" insert into M_CLINICAL_STAFF(CLINICAL_STAFF_KEY,STAFF_ID,CDS_ID,NAME_PREFIX_KEY,NAME_SUFFIX_KEY,LAST_NAME,FIRST_NAME,MIDDLE_NAME,PHONETIC_LAST_NAME,PHONETIC_FIRST_NAME,SEARCH_AS_DOCTOR_FLG,SEARCH_AS_NURSE_FLG,BELONG_DEPARTMENT_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) " 
	   			+" select 101,'T01','T01',1,NULL,'Tech01','Tech01',NULL,NULL,NULL,0,0,NULL,1,0,101,101,'nicole','nicole' from dual union all" 
	   			+" select 102,'T02','T02',1,NULL,'Tech02','Tech02',NULL,NULL,NULL,0,0,NULL,1,0,102,102,'nicole','nicole' from dual union all" 
	   			+" select 103,'T03','T03',1,NULL,'Tech03','Tech03',NULL,NULL,NULL,0,0,NULL,1,0,103,103,'nicole','nicole' from dual union all" 
	   			+" select 104,'T04','T04',1,NULL,'Tech04','Tech04',NULL,NULL,NULL,0,0,NULL,1,0,104,104,'nicole','nicole' from dual union all" 
	   			+" select 105,'T05','T05',1,NULL,'Tech05','Tech05',NULL,NULL,NULL,0,0,NULL,1,0,105,105,'nicole','nicole' from dual union all" 
	   			+" select 106,'T06','T06',1,NULL,'Tech06','Tech06',NULL,NULL,NULL,0,0,NULL,1,0,106,106,'nicole','nicole' from dual union all" 
	   			+" select 107,'T07','T07',1,NULL,'Tech07','Tech07',NULL,NULL,NULL,0,0,NULL,1,0,107,107,'nicole','nicole' from dual union all" 
	   			+" select 108,'T08','T08',1,NULL,'Tech08','Tech08',NULL,NULL,NULL,0,0,NULL,1,0,108,108,'nicole','nicole' from dual union all" 
	   			+" select 109,'T09','T09',1,NULL,'Tech09','Tech09',NULL,NULL,NULL,0,0,NULL,1,0,109,109,'nicole','nicole' from dual union all" 
	   			+" select 110,'T10','T10',1,NULL,'Tech10','Tech10',NULL,NULL,NULL,0,0,NULL,1,0,110,110,'nicole','nicole' from dual union all" 
	   			+" select 111,'T11','T11',1,NULL,'Tech11','Tech11',NULL,NULL,NULL,0,0,NULL,1,0,111,111,'nicole','nicole' from dual union all" 
	   			+" select 112,'T12','T12',1,NULL,'Tech12','Tech12',NULL,NULL,NULL,0,0,NULL,1,0,112,112,'nicole','nicole' from dual union all" 
	   			+" select 113,'T13','T13',1,NULL,'Tech13','Tech13',NULL,NULL,NULL,0,0,NULL,1,0,113,113,'nicole','nicole' from dual union all" 
	   			+" select 114,'T14','T14',1,NULL,'Tech14','Tech14',NULL,NULL,NULL,0,0,NULL,1,0,114,114,'nicole','nicole' from dual union all" 
	   			+" select 115,'T15','T15',1,NULL,'Tech15','Tech15',NULL,NULL,NULL,0,0,NULL,1,0,115,115,'nicole','nicole' from dual union all" 
	   			+" select 116,'T16','T16',1,NULL,'Tech16','Tech16',NULL,NULL,NULL,0,0,NULL,1,0,116,116,'nicole','nicole' from dual union all" 
	   			+" select 117,'T17','T17',1,NULL,'Tech17','Tech17',NULL,NULL,NULL,0,0,NULL,1,0,117,117,'nicole','nicole' from dual union all" 
	   			+" select 118,'T18','T18',1,NULL,'Tech18','Tech18',NULL,NULL,NULL,0,0,NULL,1,0,118,118,'nicole','nicole' from dual union all" 
	   			+" select 119,'T19','T19',1,NULL,'Tech19','Tech19',NULL,NULL,NULL,0,0,NULL,1,0,119,119,'nicole','nicole' from dual union all" 
	   			+" select 120,'T20','T20',1,NULL,'Tech20','Tech20',NULL,NULL,NULL,0,0,NULL,1,0,120,120,'nicole','nicole' from dual;" 
	   			+" commit;" 
	   			+" insert into M_User(USER_KEY,USER_ID,ADMIN_FLG,CURRENT_PASSWORD,CLINICAL_STAFF_KEY, NEXT_CHANGE_PASSWORD_FLG,LAST_CHANGE_PASSWORD_DATE_TIME,REVOKED_FLG,REVOKED_PAST_DATE_TIME,INCORRECT_LOGIN_COUNT,ENABLED_DATE_FROM,ENABLED_DATE_TO,OLYMPUS_USER_FLG,USER_ROLE_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,CREATE_USER_ID,UPDATE_USER_ID) " 
	   			+" select 101,'T01',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',101,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,101,'nicole','nicole' from dual union all" 
	   			+" select 102,'T02',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',102,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,102,'nicole','nicole' from dual union all" 
	   			+" select 103,'T03',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',103,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,103,'nicole','nicole' from dual union all" 
	   			+" select 104,'T04',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',104,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,104,'nicole','nicole' from dual union all" 
	   			+" select 105,'T05',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',105,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,105,'nicole','nicole' from dual union all" 
	   			+" select 106,'T06',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',106,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,106,'nicole','nicole' from dual union all" 
	   			+" select 107,'T07',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',107,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,107,'nicole','nicole' from dual union all" 
	   			+" select 108,'T08',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',108,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,108,'nicole','nicole' from dual union all" 
	   			+" select 109,'T09',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',109,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,109,'nicole','nicole' from dual union all" 
	   			+" select 110,'T10',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',110,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,110,'nicole','nicole' from dual union all" 
	   			+" select 111,'T11',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',111,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,111,'nicole','nicole' from dual union all" 
	   			+" select 112,'T12',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',112,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,112,'nicole','nicole' from dual union all" 
	   			+" select 113,'T13',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',113,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,113,'nicole','nicole' from dual union all" 
	   			+" select 114,'T14',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',114,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,114,'nicole','nicole' from dual union all" 
	   			+" select 115,'T15',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',115,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,115,'nicole','nicole' from dual union all" 
	   			+" select 116,'T16',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',116,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,116,'nicole','nicole' from dual union all" 
	   			+" select 117,'T17',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',117,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,117,'nicole','nicole' from dual union all" 
	   			+" select 118,'T18',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',118,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,118,'nicole','nicole' from dual union all" 
	   			+" select 119,'T19',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',119,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,119,'nicole','nicole' from dual union all" 
	   			+" select 120,'T20',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',120,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,120,'nicole','nicole' from dual;" 
	   			+"commit;";


		System.out.println("stmt3="+stmt3);
	    
	   	try{
    		String[] breakstmt=stmt3.split(";"); 
    		for(int i=0; i< breakstmt.length; i++ ){
    			System.out.println(breakstmt[i]);
    			st.addBatch(breakstmt[i]);
    			st.executeBatch();
    		}
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}

		conn.close();

		System.out.println("Gen Function to clear and insert ");

	}
	
	   	public static int GetScope_Key(String ScopeSerialNo) throws SQLException{
	   		ResultSet RS;
	   		int SCOPE_KEY=0;
			Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);

			String stmt1="select SCOPE_KEY from M_Scope where SERIAL_NO='"+ScopeSerialNo+"'";
			Statement update1 = conn.createStatement();
			System.out.println("stmt1="+stmt1);
	 		RS = update1.executeQuery(stmt1);
			while(RS.next()){
				SCOPE_KEY = RS.getInt(1); 
				System.out.println("SCOPE_KEY "+SCOPE_KEY);
				}
			RS.close();
			return SCOPE_KEY;
	   	}

	   	public static int GetStaff_Key(String StaffID) throws SQLException{
	   		ResultSet RS;
	   		int CLINICAL_STAFF_KEY=0;
			Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			Statement update1 = conn.createStatement();
			String stmt1;
			
			stmt1="select CLINICAL_STAFF_KEY from M_User where USER_ID='"+StaffID+"'";
			System.out.println("stmt1="+stmt1);
	 		RS = update1.executeQuery(stmt1);
			while(RS.next()){
				CLINICAL_STAFF_KEY = RS.getInt(1); 
				System.out.println("CLINICAL_STAFF_KEY "+CLINICAL_STAFF_KEY);
				}
			RS.close();

			return CLINICAL_STAFF_KEY;
	   	}

	   	public static int GetReproModel_Key(String OERModel) throws SQLException{
	   		ResultSet RS;
	   		int REPROCESSOR_MODEL_KEY=0;
			Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			Statement update1 = conn.createStatement();
			String stmt1;
			
			stmt1="select REPROCESSOR_MODEL_KEY from M_REPROCESSOR_MODEL where REPROCESSOR_MODEL_NAME='"+OERModel+"'";
			System.out.println("stmt1="+stmt1);
	 		RS = update1.executeQuery(stmt1);
			while(RS.next()){
				REPROCESSOR_MODEL_KEY = RS.getInt(1); 
				System.out.println("REPROCESSOR_MODEL_KEY "+REPROCESSOR_MODEL_KEY);
				}
			RS.close();

			return REPROCESSOR_MODEL_KEY;
	   	}

	   	public static int GetRepro_Key(String OERSerialNum, int REPROCESSOR_MODEL_KEY) throws SQLException{
	   		ResultSet RS;
	   		int REPROCESSOR_KEY=0;
			Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			Statement update1 = conn.createStatement();
			String stmt1;
			
			stmt1="select REPROCESSOR_KEY from m_reprocessor where SERIAL_NO='"+OERSerialNum+"' and REPROCESSOR_MODEL_KEY="+REPROCESSOR_MODEL_KEY;
			System.out.println("stmt1="+stmt1);
	 		RS = update1.executeQuery(stmt1);
			while(RS.next()){
				REPROCESSOR_KEY = RS.getInt(1); 
				System.out.println("REPROCESSOR_KEY "+REPROCESSOR_KEY);
				}
			RS.close();

			return REPROCESSOR_KEY;
	   	}

	   	public static void InsertReproInfo(int REPROCESSOR_INFO_KEY, int REPROCESSOR_KEY, int REPROCESS_USED_COUNT, int REPROCESSOR_STATUS_KEY) throws SQLException{
			Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			Statement update1 = conn.createStatement();
			String stmt1;
			
			stmt1="insert into T_Reprocessor_Info(REPROCESSOR_INFO_KEY,REPROCESSOR_KEY,VERSION,DISINFECT_BOTTLECOUNT,REPROCESS_USED_COUNT,REPROCESSOR_STATUS_KEY,"
					+ "DISINFECTION_OPERATION_COUNT,DISINFECTION_ELAPSED_DAY_COUNT,TIME_LAG_MINUTES,NOTIFICATION_HIDE_FLG) "
					+ "values("+REPROCESSOR_INFO_KEY+","+REPROCESSOR_KEY+",'3.03',2,"+REPROCESS_USED_COUNT+","+REPROCESSOR_STATUS_KEY+",15,3,0,0)";
			System.out.println("stmt1="+stmt1);
			update1.executeQuery(stmt1);
	   	}

	   	public static void InsertReproCondition(int REPROCESS_CONDITION_KEY, int REPROCESSOR_KEY, String OERStartTime, String Complete, int REPROCESS_USED_COUNT, int CLINICAL_STAFF_KEY, int REPROCESS_RESULT_KEY) throws SQLException{
			Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			Statement update1 = conn.createStatement();
			String stmt1;
			
			stmt1="insert into T_Reprocess_Condition(REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY,DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME)"
					+ "values ("+REPROCESS_CONDITION_KEY+",1,"+REPROCESSOR_KEY+",to_date('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS'), to_date('"+Complete+"','MM-DD-YYYY HH24:MI:SS'),1,3,7,20,2,21,21,99,99,"+REPROCESS_USED_COUNT+",68,"+CLINICAL_STAFF_KEY+","+CLINICAL_STAFF_KEY+",0,"+REPROCESS_RESULT_KEY+",11,1,2,1,0,TO_TIMESTAMP('"+OERStartTime+"','MM-DD-YYYY HH24:MI:SS'))";			

			System.out.println("stmt1="+stmt1);
			update1.executeQuery(stmt1);
	   	}

	   	public static void InsertnReproScopes(int REPROCESSED_SCOPES_KEY, int REPROCESS_CONDITION_KEY, int INDEX_NO, int SCOPE_KEY, int CLINICAL_STAFF_KEY) throws SQLException{
			Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
			Statement update1 = conn.createStatement();
			String stmt1;
			
			stmt1="insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG)"
					+"values ("+REPROCESSED_SCOPES_KEY+","+REPROCESS_CONDITION_KEY+","+INDEX_NO+","+SCOPE_KEY+",0,1,"+CLINICAL_STAFF_KEY+","+CLINICAL_STAFF_KEY+",0,0)";
			System.out.println("stmt1="+stmt1);
			update1.executeQuery(stmt1);
	   	}

public static String ServerDateTime (String URL, String UserName, String Password){	
	Connection conn;
    String stmt;
    String stmt2;
    ResultSet RecSet;
    String currDateTime="";
    String FormattedcurrDateTime="";
	try{
		conn= DriverManager.getConnection(URL, UserName, Password);		
		Statement statement = conn.createStatement();
		stmt="Select Convert(varchar(26),GetDate(),22)";
		System.out.println("stmt="+stmt);
		RecSet = statement.executeQuery(stmt);
		while(RecSet.next()){
			currDateTime = RecSet.getString(1);
		}	
		stmt2="DECLARE @datetime DATETIME = '"+currDateTime+"' select Format(@datetime, 'yyyyMMdd_HHmmss')";
		RecSet = statement.executeQuery(stmt2);
		while(RecSet.next()){
			FormattedcurrDateTime = RecSet.getString(1);
		}
		RecSet.close();
		conn.close();
	}
	
	catch (SQLException ex){
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	return FormattedcurrDateTime;
  	}

public static String[] ServerDateTime_Duration (String URL, String UserName, String Password, int Duration){	
	Connection conn;
    String stmt;
    String stmt2;
    ResultSet RecSet;
    String currDateTime="";
    String completeDateTime="";
    String FormattedcurrDateTime[] =new String[2];

    
	try{
		conn= DriverManager.getConnection(URL, UserName, Password);		
		Statement statement = conn.createStatement();
		stmt="Select Convert(varchar(26),GetDate(),22)";
		System.out.println("stmt="+stmt);
		RecSet = statement.executeQuery(stmt);
		while(RecSet.next()){
			currDateTime = RecSet.getString(1);
		}	
		stmt2="DECLARE @datetime DATETIME = '"+currDateTime+"' select Format(@datetime, 'yyyyMMdd_HHmmss')";
		RecSet = statement.executeQuery(stmt2);
		while(RecSet.next()){
			FormattedcurrDateTime[0] = RecSet.getString(1);
		}
		RecSet.close();
		
		stmt="Select Convert(varchar(26),DATEADD(MINUTE, +"+Duration+", GetDate()),22)";
		System.out.println("stmt="+stmt);
		RecSet = statement.executeQuery(stmt);
		while(RecSet.next()){
			completeDateTime = RecSet.getString(1);
		}	
		stmt2="DECLARE @datetime DATETIME = '"+completeDateTime+"' select Format(@datetime, 'yyyyMMdd_HHmmss')";
		RecSet = statement.executeQuery(stmt2);
		while(RecSet.next()){
			FormattedcurrDateTime[1] = RecSet.getString(1);
		}
		RecSet.close();

		conn.close();
	}
	
	catch (SQLException ex){
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	return FormattedcurrDateTime;
  	}

public static String KEServerDateTime (String URL, String UserName, String Password){	
	Connection conn=null;
    String stmt;
    String stmt2;
    ResultSet RecSet;
    String currDateTime="";
    String FormattedcurrDateTime="";
	try{
		conn= DriverManager.getConnection(URL, UserName, Password);		
		Statement statement = conn.createStatement();
		stmt="SELECT TO_CHAR(SYSDATE, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL";
		System.out.println("stmt="+stmt);
		RecSet = statement.executeQuery(stmt);
		while(RecSet.next()){
			FormattedcurrDateTime = RecSet.getString(1);
		}	

		conn.close();
	}
	
	catch (SQLException ex){
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	return FormattedcurrDateTime;
  	}

public static String[] KEServerDateTime_Duration (String URL, String UserName, String Password, int Duration){	
	Connection conn;
    String stmt;
    String stmt2;
    ResultSet RecSet;
    String currDateTime="";
    String completeDateTime="";
    String FormattedcurrDateTime[] =new String[2];

    
	try{
		conn= DriverManager.getConnection(URL, UserName, Password);		
		Statement statement = conn.createStatement();
		stmt="SELECT TO_CHAR(SYSDATE, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL";
		System.out.println("stmt="+stmt);
		RecSet = statement.executeQuery(stmt);
		while(RecSet.next()){
			FormattedcurrDateTime[0] = RecSet.getString(1);
		}	

		
		stmt="SELECT TO_CHAR(SYSDATE + "+Duration+"/1440, 'MM-DD-YYYY HH24:MI:SS') \"NOW\" FROM DUAL";
		System.out.println("stmt="+stmt);
		RecSet = statement.executeQuery(stmt);
		while(RecSet.next()){
			FormattedcurrDateTime[1] = RecSet.getString(1);
		}	


		conn.close();
	}
	
	catch (SQLException ex){
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	return FormattedcurrDateTime;
  	}

	public static String GetLastRowDate(String strTableid,String DateColumn) throws InterruptedException{
		driver.findElementByXPath("//*[@class=\"ui-icon ui-icon-seek-end\"]").click();
		Thread.sleep(5000);
		System.out.println("Click on navigation arrow for navigating to last page.");
		WebElement webtable=driver.findElement(By.id(strTableid)); 
		List <WebElement> rowCollection=webtable.findElements(By.xpath("//*[@id='"+strTableid+"']/tbody/tr"));
		int LastRow = rowCollection.size();
		String Date = webtable.findElement(By.xpath("//*[@id='"+strTableid+"']/tbody/tr["+LastRow+"]/td["+DateColumn+"]")).getText();
		return Date;
	}

	public static boolean ValueExistenceInDropDown(String strValue, String strId){	
		boolean bflag = false;
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Select droplist = new Select(driver.findElementById(strId));   
		List<WebElement> valuesInDropdown = droplist.getOptions();
		for (WebElement element : valuesInDropdown) 
		{
			String elementValue = element.getText();
			if (strValue.equalsIgnoreCase(elementValue))
			{
				bflag= true;
				break;
			}
		}
		return bflag;
	}
	
	public static String[][] ConvertToDayLightSavingDateTime(String strTime, String strDate) throws ParseException{	
		String strTime1 = "";
		Date newDate = null;
		String[][] DateTime = new String[1][2];
		if(strTime.contains("AM"))
		{	strTime1 = strTime.replace("AM", "PM");}
		else{
			strTime1 = strTime ;}
	
		String dt = strDate + " " + strTime1;
		String dt1 = strDate + " " + strTime;
		Calendar calendar = Calendar.getInstance();
		TimeZone toTimeZone = TimeZone.getTimeZone("America/New_York");
		calendar.setTimeZone(toTimeZone);
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy h:mm a");
		Date date = sdf.parse(dt);
		Date date1 = sdf.parse(dt1);
		if (toTimeZone.inDaylightTime(date)) {
			newDate = new Date(date1.getTime() + toTimeZone.getDSTSavings());}
		else{
		newDate = date1;}
		String newdate1 = sdf.format(newDate);
		String newDate2[] = newdate1.split(" ");
		DateTime[0][0] = newDate2[0];
		DateTime[0][1] = newDate2[1] + " " + newDate2[2];
		System.out.println(DateTime[0][0]);
		System.out.println(DateTime[0][1]);
		return DateTime;
	}
	
	public static void CloseDriver() {
		driver.close();
	}
	
	public static void RelaunchDriver() {
		driver=new ChromeDriver();
	}
	
	public static boolean WaitForObjectExistence(String strXpath, int timeOutInSeconds) throws InterruptedException {
		int i =0;
		boolean blnStatus = false;
		
		while(i<=timeOutInSeconds){
			int intSize = driver.findElements(By.xpath(strXpath)).size();
			if(intSize==0)
			{
				Thread.sleep(1000);
			}else
			{
				blnStatus = true;
				break;
			}
			i++;
		}
		return blnStatus;
	}

public static String ServerDay (String URL, String UserName, String Password){	
		Connection conn;
	    String stmt;
	    ResultSet RecSet;
	    String currDay="";
		try{
			conn= DriverManager.getConnection(URL, UserName, Password);		
			Statement statement = conn.createStatement();
			stmt="SELECT DATENAME(dw,GETDATE())";
			System.out.println("stmt="+stmt);
			RecSet = statement.executeQuery(stmt);
			while(RecSet.next()){
				currDay = RecSet.getString(1);
			}	
			RecSet.close();
			conn.close();
		}
		
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		return currDay;
	  	}

	public static boolean CheckDriverExistence()
	{
		try{
			String strTitle = driver.getCurrentUrl();
			if(strTitle.equalsIgnoreCase("data:,"))
			{
				return false;
			}else
			{
				return true;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static void InsertRMM_Data(String url, String user, String pass) throws SQLException {
		//Taking 4mins as duration between the Start and End time
		String[] Times = KEServerDateTime_Duration(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd, 4);
		
		Connection conn= null;
		System.out.println("Gen Function to clear all data and insert seed data");
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement st = conn.createStatement();
		String stmt1="delete T_Reprocessed_Scopes where REPROCESSED_SCOPES_KEY>100 and REPROCESSED_SCOPES_KEY<200;"
				+ "delete t_reprocess_condition where REPROCESS_CONDITION_KEY>100 and REPROCESS_CONDITION_KEY<200;"
				+ "delete T_Reprocessor_Info where REPROCESSOR_INFO_KEY>100 and REPROCESSOR_INFO_KEY<200;"
				+ "commit;"
				+ "insert into T_Reprocessor_Info(REPROCESSOR_INFO_KEY,REPROCESSOR_KEY,VERSION,DISINFECT_BOTTLECOUNT,REPROCESS_USED_COUNT,REPROCESSOR_STATUS_KEY,DISINFECTION_OPERATION_COUNT,DISINFECTION_ELAPSED_DAY_COUNT,TIME_LAG_MINUTES,NOTIFICATION_HIDE_FLG)"
				+ "values(101,1,'3.03',2,19,4,15,3,0,0);"
				+ "insert into T_Reprocessor_Info(REPROCESSOR_INFO_KEY,REPROCESSOR_KEY,VERSION,DISINFECT_BOTTLECOUNT,REPROCESS_USED_COUNT,REPROCESSOR_STATUS_KEY,DISINFECTION_OPERATION_COUNT,DISINFECTION_ELAPSED_DAY_COUNT,TIME_LAG_MINUTES,NOTIFICATION_HIDE_FLG)"
				+ "values(102,2,'3.03',3,139,4,15,3,0,0);"
				+ "commit;"
				//--reprocessing normal
				+ "insert into T_Reprocess_Condition(REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY,"
				+ "DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME)"
				+ "values (101,1,1,to_date('"+Times[0]+"','MM-DD-YYYY HH24:MI:SS'),to_date('"+Times[1]+"','MM-DD-YYYY HH24:MI:SS'),1,3,7,20,2,21,21,99,99,19,68,101,101,0,3,11,1,2,1,0,TO_TIMESTAMP('"+Times[1]+"','MM-DD-YYYY HH24:MI:SS'));"
				//--reprocessing stopped with errors
				+ "insert into T_Reprocess_Condition(REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY,"
				+ "DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME,ERROR_VALUE,ERROR_CODE,PROGRESS_NO,PROGRESS_PASTTIME)"
				+ "values (102,1,2,to_date('"+Times[0]+"','MM-DD-YYYY HH24:MI:SS'),to_date('"+Times[1]+"','MM-DD-YYYY HH24:MI:SS'),2,3,7,20,2,21,21,99,99,139,68,101,101,0,2,11,1,2,1,0,TO_TIMESTAMP('"+Times[1]+"','MM-DD-YYYY HH24:MI:SS'),'ffffffffffffffffffffffff',0,'100E',182);"
				+ "commit;"
				+ "insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG)"
				+ "values (101,101,1,101,0,1,101,101,0,0);"
				//--scopes that had errors when reprocessed
				+ "insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG)"
				+ "values (102,102,1,102,0,1,101,101,0,0);"
				+ "insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG)"
				+ "values (103,102,2,103,0,1,101,101,0,0);"
				+ "commit;";
		System.out.println("stmt1="+stmt1);
	    
	   	try{
    		String[] breakstmt=stmt1.split(";"); 
    		for(int i=0; i< breakstmt.length; i++ ){
    			System.out.println(breakstmt[i]);
    			st.addBatch(breakstmt[i]);
    			st.executeBatch();
    		}
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
	}
}

