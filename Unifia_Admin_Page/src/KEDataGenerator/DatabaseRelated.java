package KEDataGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRelated {

   	public static void InsertSeedData(String url, String user, String pass ) throws SQLException {
	Connection conn= null;
	System.out.println("Gen Function to clear all data and insert seed data");
	//deallocate cur
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
   			+" select 101,'tech01','tech01',1,NULL,'Tech01','Tech01',NULL,NULL,NULL,0,0,NULL,1,0,101,101,'nicole','nicole' from dual union all" 
   			+" select 102,'tech02','tech02',1,NULL,'Tech02','Tech02',NULL,NULL,NULL,0,0,NULL,1,0,102,102,'nicole','nicole' from dual union all" 
   			+" select 103,'tech03','tech03',1,NULL,'Tech03','Tech03',NULL,NULL,NULL,0,0,NULL,1,0,103,103,'nicole','nicole' from dual union all" 
   			+" select 104,'tech04','tech04',1,NULL,'Tech04','Tech04',NULL,NULL,NULL,0,0,NULL,1,0,104,104,'nicole','nicole' from dual union all" 
   			+" select 105,'tech05','tech05',1,NULL,'Tech05','Tech05',NULL,NULL,NULL,0,0,NULL,1,0,105,105,'nicole','nicole' from dual union all" 
   			+" select 106,'tech06','tech06',1,NULL,'Tech06','Tech06',NULL,NULL,NULL,0,0,NULL,1,0,106,106,'nicole','nicole' from dual union all" 
   			+" select 107,'tech07','tech07',1,NULL,'Tech07','Tech07',NULL,NULL,NULL,0,0,NULL,1,0,107,107,'nicole','nicole' from dual union all" 
   			+" select 108,'tech08','tech08',1,NULL,'Tech08','Tech08',NULL,NULL,NULL,0,0,NULL,1,0,108,108,'nicole','nicole' from dual union all" 
   			+" select 109,'tech09','tech09',1,NULL,'Tech09','Tech09',NULL,NULL,NULL,0,0,NULL,1,0,109,109,'nicole','nicole' from dual union all" 
   			+" select 110,'tech10','tech10',1,NULL,'Tech10','Tech10',NULL,NULL,NULL,0,0,NULL,1,0,110,110,'nicole','nicole' from dual union all" 
   			+" select 111,'tech11','tech11',1,NULL,'Tech11','Tech11',NULL,NULL,NULL,0,0,NULL,1,0,111,111,'nicole','nicole' from dual union all" 
   			+" select 112,'tech12','tech12',1,NULL,'Tech12','Tech12',NULL,NULL,NULL,0,0,NULL,1,0,112,112,'nicole','nicole' from dual union all" 
   			+" select 113,'tech13','tech13',1,NULL,'Tech13','Tech13',NULL,NULL,NULL,0,0,NULL,1,0,113,113,'nicole','nicole' from dual union all" 
   			+" select 114,'tech14','tech14',1,NULL,'Tech14','Tech14',NULL,NULL,NULL,0,0,NULL,1,0,114,114,'nicole','nicole' from dual union all" 
   			+" select 115,'tech15','tech15',1,NULL,'Tech15','Tech15',NULL,NULL,NULL,0,0,NULL,1,0,115,115,'nicole','nicole' from dual union all" 
   			+" select 116,'tech16','tech16',1,NULL,'Tech16','Tech16',NULL,NULL,NULL,0,0,NULL,1,0,116,116,'nicole','nicole' from dual union all" 
   			+" select 117,'tech17','tech17',1,NULL,'Tech17','Tech17',NULL,NULL,NULL,0,0,NULL,1,0,117,117,'nicole','nicole' from dual union all" 
   			+" select 118,'tech18','tech18',1,NULL,'Tech18','Tech18',NULL,NULL,NULL,0,0,NULL,1,0,118,118,'nicole','nicole' from dual union all" 
   			+" select 119,'tech19','tech19',1,NULL,'Tech19','Tech19',NULL,NULL,NULL,0,0,NULL,1,0,119,119,'nicole','nicole' from dual union all" 
   			+" select 120,'tech20','tech20',1,NULL,'Tech20','Tech20',NULL,NULL,NULL,0,0,NULL,1,0,120,120,'nicole','nicole' from dual;" 
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
   			+" select 111,'T01',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',111,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,111,'nicole','nicole' from dual union all" 
   			+" select 112,'T02',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',112,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,112,'nicole','nicole' from dual union all" 
   			+" select 113,'T03',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',113,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,113,'nicole','nicole' from dual union all" 
   			+" select 114,'T04',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',114,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,114,'nicole','nicole' from dual union all" 
   			+" select 115,'T05',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',115,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,115,'nicole','nicole' from dual union all" 
   			+" select 116,'T06',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',116,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,116,'nicole','nicole' from dual union all" 
   			+" select 117,'T07',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',117,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,117,'nicole','nicole' from dual union all" 
   			+" select 118,'T08',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',118,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,118,'nicole','nicole' from dual union all" 
   			+" select 119,'T09',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',119,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,119,'nicole','nicole' from dual union all" 
   			+" select 120,'T10',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',120,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,120,'nicole','nicole' from dual;" 
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

}
