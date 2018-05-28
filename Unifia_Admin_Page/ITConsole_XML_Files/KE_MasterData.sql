delete T_Reprocessed_Scopes;
commit;
delete t_reprocess_condition; 
delete t_reprocessor_mainte;
delete t_reprocessor_parts_change;
delete t_reprocessor_wash_info;
delete t_reprocessor_wash_log;
delete t_oer_error_log;
delete t_oer_maintenance_data;
delete t_oer_reprocessing_log;
delete T_REPROCESSOR_INFO;
commit;
delete from M_Scope where SCOPE_KEY>10;
delete from M_SCOPE_MODEL where Scope_Model_Key> 120 and Scope_Model_Key<100000000;
delete from m_reprocessor_programs;
delete from m_reprocessor;
delete from M_User where USER_KEY > 7 and USER_KEY<10000;
delete from M_CLINICAL_STAFF where CLINICAL_STAFF_KEY > 7 and CLINICAL_STAFF_KEY<10000;
commit;
insert into M_SCOPE_MODEL (Scope_Model_Key,Scope_Model_Name,Channel_Size,ACTIVE_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID,LINK_ALL_EXAM_TYPE_FLG) 
select 183,'GIF-H190','2',1,'Olympus Code',108,'nicole','nicole',1 from dual union all
select 184,'GIF-HQ190','2',1,'Olympus Code',109,'nicole','nicole',1 from dual union all
select 200,'CF-H190L','2',1,'Olympus Code',110,'nicole','nicole',1 from dual union all
select 201,'CF-HQ190L','2',1,'Olympus Code',111,'nicole','nicole',1 from dual union all
select 155,'TJF-Q180V','1.5',1,'Olympus Code',154,'nicole','nicole',1 from dual union all
select 170,'GIF-1TH190','1',1,'Olympus Code',155,'nicole','nicole',1 from dual union all
select 202,'BF-1TQ180','1',1,'Olympus Code',156,'nicole','nicole',1 from dual union all
select 203,'CF-30L','1.2',1,'Olympus Code',157,'nicole','nicole',1 from dual union all
select 204,'CF-30M','1',1,'Olympus Code',158,'nicole','nicole',1 from dual union all
select 205,'CF-40L','1',1,'Olympus Code',159,'nicole','nicole',1 from dual; 

insert into M_Scope(SCOPE_KEY,SERIAL_NO,SCOPE_NAME,ADMIN_REG_FLG,SCOPE_MODEL_KEY,PURCHASE_DATE,PURCHASE_COST,NOTE,SCOPE_STATUS_KEY,EXTERNAL_CODE,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) 
select 101,'1122334','Scope1',1,183,NULL,NULL,NULL,1,NULL,1,0,'101',1,'nicole','nicole' from dual union all
select 102,'2233445','Scope2',1,184,NULL,NULL,NULL,1,NULL,1,0,'102',2,'nicole','nicole' from dual union all
select 103,'3344556','Scope3',1,94,NULL,NULL,NULL,1,NULL,1,0,'103',3,'nicole','nicole' from dual union all
select 104,'4455667','Scope4',1,200,NULL,NULL,NULL,1,NULL,1,0,'104',4,'nicole','nicole' from dual union all
select 105,'5566778','Scope5',1,201,NULL,NULL,NULL,1,NULL,1,0,'105',5,'nicole','nicole' from dual union all
select 106,'6677889','Scope6',1,200,NULL,NULL,NULL,1,NULL,1,0,'106',6,'nicole','nicole' from dual union all
select 107,'7654231','Scope7',1,155,NULL,NULL,NULL,1,NULL,1,0,'107',7,'nicole','nicole' from dual union all
select 108,'6543216','Scope8',1,170,NULL,NULL,NULL,1,NULL,1,0,'108',8,'nicole','nicole' from dual union all
select 109,'9876432','Scope9',1,202,NULL,NULL,NULL,1,NULL,1,0,'109',9,'nicole','nicole' from dual union all
select 110,'7654321','Scope10',1,155,NULL,NULL,NULL,1,NULL,1,0,'110',10,'nicole','nicole' from dual union all
select 111,'9988776','Scope11',1,184,NULL,NULL,NULL,1,NULL,1,0,'111',11,'nicole','nicole' from dual union all
select 112,'2808645','Scope12',1,94,NULL,NULL,NULL,1,NULL,1,0,'112',12,'nicole','nicole' from dual union all
select 113,'2600842','Scope13',1,94,NULL,NULL,NULL,1,NULL,1,0,'113',13,'nicole','nicole' from dual union all
select 114,'2806734','Scope14',1,94,NULL,NULL,NULL,1,NULL,1,0,'114',14,'nicole','nicole' from dual union all
select 115,'2200174','Scope15',1,183,NULL,NULL,NULL,1,NULL,1,0,'115',15,'nicole','nicole' from dual union all
select 116,'2601311','Scope16',1,94,NULL,NULL,NULL,1,NULL,1,0,'116',16,'nicole','nicole' from dual union all
select 117,'2600163','Scope17',1,94,NULL,NULL,NULL,1,NULL,1,0,'117',17,'nicole','nicole' from dual union all
select 118,'2211009','Scope18',1,203,NULL,NULL,NULL,1,NULL,1,0,'118',18,'nicole','nicole' from dual union all
select 119,'2809094','Scope19',1,88,NULL,NULL,NULL,1,NULL,1,0,'119',19,'nicole','nicole' from dual union all
select 120,'0099887','Scope20',1,204,NULL,NULL,NULL,1,NULL,1,0,'120',20,'nicole','nicole' from dual union all
select 121,'2112233','Scope21',1,204,NULL,NULL,NULL,1,NULL,1,0,'121',21,'nicole','nicole' from dual union all
select 122,'2223344','Scope22',1,203,NULL,NULL,NULL,1,NULL,1,0,'122',22,'nicole','nicole' from dual union all
select 123,'2334455','Scope23',1,203,NULL,NULL,NULL,1,NULL,1,0,'123',23,'nicole','nicole' from dual union all
select 124,'2600319','Scope24',1,96,NULL,NULL,NULL,1,NULL,1,0,'124',24,'nicole','nicole' from dual union all
select 125,'2602572','Scope25',1,88,NULL,NULL,NULL,1,NULL,1,0,'125',25,'nicole','nicole' from dual union all
select 126,'2601132','Scope26',1,94,NULL,NULL,NULL,1,NULL,1,0,'126',26,'nicole','nicole' from dual union all
select 127,'2600266','Scope27',1,94,NULL,NULL,NULL,1,NULL,1,0,'127',27,'nicole','nicole' from dual union all
select 128,'2707317','Scope28',1,88,NULL,NULL,NULL,1,NULL,1,0,'128',28,'nicole','nicole' from dual union all
select 129,'5556667','Scope29',1,205,NULL,NULL,NULL,1,NULL,1,0,'129',29,'nicole','nicole' from dual union all
select 130,'6667778','Scope30',1,205,NULL,NULL,NULL,1,NULL,1,0,'130',30,'nicole','nicole' from dual union all
select 131,'7778889','Scope31',1,155,NULL,NULL,NULL,1,NULL,1,0,'131',31,'nicole','nicole' from dual union all
select 132,'8889990','Scope32',1,155,NULL,NULL,NULL,1,NULL,1,0,'132',32,'nicole','nicole' from dual union all
select 133,'2200687','Scope33',1,184,NULL,NULL,NULL,1,NULL,1,0,'133',33,'nicole','nicole' from dual union all
select 134,'2500850','Scope34',1,88,NULL,NULL,NULL,1,NULL,1,0,'134',34,'nicole','nicole' from dual union all
select 135,'2600714','Scope35',1,94,NULL,NULL,NULL,1,NULL,1,0,'135',35,'nicole','nicole' from dual union all
select 136,'2700854','Scope36',1,204,NULL,NULL,NULL,1,NULL,1,0,'136',36,'nicole','nicole' from dual union all
select 137,'8165413','Scope37',1,203,NULL,NULL,NULL,1,NULL,1,0,'137',37,'nicole','nicole' from dual union all
select 138,'2600715','Scope38',1,94,NULL,NULL,NULL,1,NULL,1,0,'138',38,'nicole','nicole' from dual union all
select 139,'2600716','Scope39',1,94,NULL,NULL,NULL,1,NULL,1,0,'139',39,'nicole','nicole' from dual union all
select 140,'2600717','Scope40',1,94,NULL,NULL,NULL,1,NULL,1,0,'140',40,'nicole','nicole' from dual union all
select 141,'2600718','Scope41',1,94,NULL,NULL,NULL,1,NULL,1,0,'141',41,'nicole','nicole' from dual union all
select 142,'2600719','Scope42',1,94,NULL,NULL,NULL,1,NULL,1,0,'142',42,'nicole','nicole' from dual union all
select 143,'2600720','Scope43',1,94,NULL,NULL,NULL,1,NULL,1,0,'143',43,'nicole','nicole' from dual union all
select 144,'2600721','Scope44',1,94,NULL,NULL,NULL,1,NULL,1,0,'144',44,'nicole','nicole' from dual union all
select 145,'2600814','Scope45',1,94,NULL,NULL,NULL,1,NULL,1,0,'145',45,'nicole','nicole' from dual union all
select 146,'2600815','Scope46',1,94,NULL,NULL,NULL,1,NULL,1,0,'146',46,'nicole','nicole' from dual union all
select 147,'2600816','Scope47',1,94,NULL,NULL,NULL,1,NULL,1,0,'147',47,'nicole','nicole' from dual union all
select 148,'2600825','Scope48',1,94,NULL,NULL,NULL,1,NULL,1,0,'148',48,'nicole','nicole' from dual union all
select 149,'2600826','Scope49',1,94,NULL,NULL,NULL,1,NULL,1,0,'149',49,'nicole','nicole' from dual union all
select 150,'2600817','Scope50',1,94,NULL,NULL,NULL,1,NULL,1,0,'150',50,'nicole','nicole' from dual union all
select 151,'2600818','Scope51',1,94,NULL,NULL,NULL,1,NULL,1,0,'151',51,'nicole','nicole' from dual union all
select 152,'2600819','Scope52',1,94,NULL,NULL,NULL,1,NULL,1,0,'152',52,'nicole','nicole' from dual union all
select 153,'2600820','Scope53',1,94,NULL,NULL,NULL,1,NULL,1,0,'153',53,'nicole','nicole' from dual union all
select 154,'2600821','Scope54',1,94,NULL,NULL,NULL,1,NULL,1,0,'154',54,'nicole','nicole' from dual union all
select 155,'2600834','Scope55',1,94,NULL,NULL,NULL,1,NULL,1,0,'155',55,'nicole','nicole' from dual union all
select 156,'3600835','Scope56',1,94,NULL,NULL,NULL,1,NULL,1,0,'156',56,'nicole','nicole' from dual union all
select 157,'3600816','Scope57',1,94,NULL,NULL,NULL,1,NULL,1,0,'157',57,'nicole','nicole' from dual union all
select 158,'3600825','Scope58',1,94,NULL,NULL,NULL,1,NULL,1,0,'158',58,'nicole','nicole' from dual union all
select 159,'3600826','Scope59',1,94,NULL,NULL,NULL,1,NULL,1,0,'159',59,'nicole','nicole' from dual union all
select 160,'3600817','Scope60',1,94,NULL,NULL,NULL,1,NULL,1,0,'160',60,'nicole','nicole' from dual union all
select 161,'3600818','Scope61',1,94,NULL,NULL,NULL,1,NULL,1,0,'161',61,'nicole','nicole' from dual union all
select 162,'3600819','Scope62',1,94,NULL,NULL,NULL,1,NULL,1,0,'162',62,'nicole','nicole' from dual union all
select 163,'3600820','Scope63',1,94,NULL,NULL,NULL,1,NULL,1,0,'163',63,'nicole','nicole' from dual union all
select 164,'3600821','Scope64',1,94,NULL,NULL,NULL,1,NULL,1,0,'164',64,'nicole','nicole' from dual union all
select 165,'3600834','Scope65',1,94,NULL,NULL,NULL,1,NULL,1,0,'165',65,'nicole','nicole' from dual union all
select 166,'4600835','Scope66',1,94,NULL,NULL,NULL,1,NULL,1,0,'166',66,'nicole','nicole' from dual union all
select 167,'4600816','Scope67',1,94,NULL,NULL,NULL,1,NULL,1,0,'167',67,'nicole','nicole' from dual union all
select 168,'4600825','Scope68',1,94,NULL,NULL,NULL,1,NULL,1,0,'168',68,'nicole','nicole' from dual union all
select 169,'4600826','Scope69',1,94,NULL,NULL,NULL,1,NULL,1,0,'169',69,'nicole','nicole' from dual union all
select 170,'4600817','Scope70',1,94,NULL,NULL,NULL,1,NULL,1,0,'170',70,'nicole','nicole' from dual union all
select 171,'4600818','Scope71',1,94,NULL,NULL,NULL,1,NULL,1,0,'171',71,'nicole','nicole' from dual union all
select 172,'4600819','Scope72',1,94,NULL,NULL,NULL,1,NULL,1,0,'172',72,'nicole','nicole' from dual union all
select 173,'4600820','Scope73',1,94,NULL,NULL,NULL,1,NULL,1,0,'173',73,'nicole','nicole' from dual union all
select 174,'4600821','Scope74',1,94,NULL,NULL,NULL,1,NULL,1,0,'174',74,'nicole','nicole' from dual union all
select 175,'4600834','Scope75',1,94,NULL,NULL,NULL,1,NULL,1,0,'175',75,'nicole','nicole' from dual union all
select 176,'5600835','Scope76',1,94,NULL,NULL,NULL,1,NULL,1,0,'176',76,'nicole','nicole' from dual union all
select 177,'5600816','Scope77',1,94,NULL,NULL,NULL,1,NULL,1,0,'177',77,'nicole','nicole' from dual union all
select 178,'5600825','Scope78',1,94,NULL,NULL,NULL,1,NULL,1,0,'178',78,'nicole','nicole' from dual union all
select 179,'5600826','Scope79',1,94,NULL,NULL,NULL,1,NULL,1,0,'179',79,'nicole','nicole' from dual union all
select 180,'5600817','Scope80',1,94,NULL,NULL,NULL,1,NULL,1,0,'180',80,'nicole','nicole' from dual union all
select 181,'5600818','Scope81',1,94,NULL,NULL,NULL,1,NULL,1,0,'181',81,'nicole','nicole' from dual union all
select 182,'5600819','Scope82',1,94,NULL,NULL,NULL,1,NULL,1,0,'182',82,'nicole','nicole' from dual union all
select 183,'5600820','Scope83',1,94,NULL,NULL,NULL,1,NULL,1,0,'183',83,'nicole','nicole' from dual union all
select 184,'5600821','Scope84',1,94,NULL,NULL,NULL,1,NULL,1,0,'184',84,'nicole','nicole' from dual union all
select 185,'5600834','Scope85',1,94,NULL,NULL,NULL,1,NULL,1,0,'185',85,'nicole','nicole' from dual union all
select 186,'6600835','Scope86',1,94,NULL,NULL,NULL,1,NULL,1,0,'186',86,'nicole','nicole' from dual union all
select 187,'6600816','Scope87',1,94,NULL,NULL,NULL,1,NULL,1,0,'187',87,'nicole','nicole' from dual union all
select 188,'6600825','Scope88',1,94,NULL,NULL,NULL,1,NULL,1,0,'188',88,'nicole','nicole' from dual union all
select 189,'6600826','Scope89',1,94,NULL,NULL,NULL,1,NULL,1,0,'189',89,'nicole','nicole' from dual union all
select 190,'6600817','Scope90',1,94,NULL,NULL,NULL,1,NULL,1,0,'190',90,'nicole','nicole' from dual union all
select 191,'8165412','Scope91',1,203,NULL,NULL,NULL,1,NULL,1,0,'191',91,'nicole','nicole' from dual union all
select 192,'5500819','Scope92',1,94,NULL,NULL,NULL,1,NULL,1,0,'192',92,'nicole','nicole' from dual union all
select 193,'5500820','Scope93',1,94,NULL,NULL,NULL,1,NULL,1,0,'193',93,'nicole','nicole' from dual union all
select 194,'5500821','Scope94',1,94,NULL,NULL,NULL,1,NULL,1,0,'194',94,'nicole','nicole' from dual union all
select 195,'5500834','Scope95',1,94,NULL,NULL,NULL,1,NULL,1,0,'195',95,'nicole','nicole' from dual union all
select 196,'6500835','Scope96',1,94,NULL,NULL,NULL,1,NULL,1,0,'196',96,'nicole','nicole' from dual union all
select 197,'6500816','Scope97',1,94,NULL,NULL,NULL,1,NULL,1,0,'197',97,'nicole','nicole' from dual union all
select 201,'9122334','Scope101',1,183,NULL,NULL,NULL,1,NULL,1,0,'201',101,'nicole','nicole' from dual union all
select 202,'9233445','Scope102',1,184,NULL,NULL,NULL,1,NULL,1,0,'202',102,'nicole','nicole' from dual union all
select 203,'9344556','Scope103',1,94,NULL,NULL,NULL,1,NULL,1,0,'203',103,'nicole','nicole' from dual union all
select 204,'9455667','Scope104',1,200,NULL,NULL,NULL,1,NULL,1,0,'204',104,'nicole','nicole' from dual union all
select 205,'9566778','Scope105',1,201,NULL,NULL,NULL,1,NULL,1,0,'205',105,'nicole','nicole' from dual union all
select 206,'9677889','Scope106',1,200,NULL,NULL,NULL,1,NULL,1,0,'206',106,'nicole','nicole' from dual union all
select 207,'9654231','Scope107',1,155,NULL,NULL,NULL,1,NULL,1,0,'207',107,'nicole','nicole' from dual union all
select 208,'9543216','Scope108',1,170,NULL,NULL,NULL,1,NULL,1,0,'208',108,'nicole','nicole' from dual union all
select 209,'8876432','Scope109',1,202,NULL,NULL,NULL,1,NULL,1,0,'209',109,'nicole','nicole' from dual union all
select 210,'9654321','Scope110',1,155,NULL,NULL,NULL,1,NULL,1,0,'210',110,'nicole','nicole' from dual union all
select 211,'8988776','Scope111',1,184,NULL,NULL,NULL,1,NULL,1,0,'211',111,'nicole','nicole' from dual union all
select 212,'9808645','Scope112',1,94,NULL,NULL,NULL,1,NULL,1,0,'212',112,'nicole','nicole' from dual union all
select 258,'9600825','Scope158',1,94,NULL,NULL,NULL,1,NULL,1,0,'258',158,'nicole','nicole' from dual union all
select 259,'9600826','Scope159',1,94,NULL,NULL,NULL,1,NULL,1,0,'259',159,'nicole','nicole' from dual union all
select 260,'9600817','Scope160',1,94,NULL,NULL,NULL,1,NULL,1,0,'260',160,'nicole','nicole' from dual union all
select 261,'9600818','Scope161',1,94,NULL,NULL,NULL,1,NULL,1,0,'261',161,'nicole','nicole' from dual union all
select 262,'9600819','Scope162',1,94,NULL,NULL,NULL,1,NULL,1,0,'262',162,'nicole','nicole' from dual union all
select 263,'9600820','Scope163',1,94,NULL,NULL,NULL,1,NULL,1,0,'263',163,'nicole','nicole' from dual union all
select 264,'9600821','Scope164',1,94,NULL,NULL,NULL,1,NULL,1,0,'264',164,'nicole','nicole' from dual;
insert into m_reprocessor(REPROCESSOR_KEY,SERIAL_NO,REPROCESSOR_NAME,REPROCESSOR_MODEL_KEY,PURCHASE_DATE,PURCHASE_COST,FACILITY_KEY,DATA_COOP_FLG,REPROCESSING_ROOM_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) 
select 1,'2000001','Reprocessor 1',1,NULL,NULL,1,0,1,1,0,1,1,'nicole','nicole' from dual union all
select 2,'2000002','Reprocessor 2',1,NULL,NULL,1,0,1,1,0,2,2,'nicole','nicole' from dual union all
select 3,'2000003','Reprocessor 3',1,NULL,NULL,1,0,1,1,0,3,3,'nicole','nicole' from dual union all
select 4,'2000004','Reprocessor 4',1,NULL,NULL,1,0,1,1,0,4,4,'nicole','nicole' from dual union all
select 5,'2000005','Reprocessor 5',1,NULL,NULL,1,0,1,1,0,5,5,'nicole','nicole' from dual union all
select 6,'2000006','Reprocessor 6',1,NULL,NULL,1,0,1,1,0,6,6,'nicole','nicole' from dual;
commit;
insert into m_reprocessor_programs(REPROCESSOR_PROGRAMS_KEY,REPROCESSOR_KEY,INDEX_NO,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID)
select 11,1,1,1,0,'11',1,'nicole','nicole' from dual union all
select 12,1,2,1,0,'12',2,'nicole','nicole' from dual union all
select 13,1,3,1,0,'13',3,'nicole','nicole' from dual;
commit;

insert into M_CLINICAL_STAFF(CLINICAL_STAFF_KEY,STAFF_ID,CDS_ID,NAME_PREFIX_KEY,NAME_SUFFIX_KEY,LAST_NAME,FIRST_NAME,MIDDLE_NAME,PHONETIC_LAST_NAME,PHONETIC_FIRST_NAME,SEARCH_AS_DOCTOR_FLG,SEARCH_AS_NURSE_FLG,BELONG_DEPARTMENT_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) 
select 101,'T01','T01',1,NULL,'Tech01','Tech01',NULL,NULL,NULL,0,0,NULL,1,0,101,101,'nicole','nicole' from dual union all
select 102,'T02','T02',1,NULL,'Tech02','Tech02',NULL,NULL,NULL,0,0,NULL,1,0,102,102,'nicole','nicole' from dual union all
select 103,'T03','T03',1,NULL,'Tech03','Tech03',NULL,NULL,NULL,0,0,NULL,1,0,103,103,'nicole','nicole' from dual union all
select 104,'T04','T04',1,NULL,'Tech04','Tech04',NULL,NULL,NULL,0,0,NULL,1,0,104,104,'nicole','nicole' from dual union all
select 105,'T05','T05',1,NULL,'Tech05','Tech05',NULL,NULL,NULL,0,0,NULL,1,0,105,105,'nicole','nicole' from dual union all
select 106,'T06','T06',1,NULL,'Tech06','Tech06',NULL,NULL,NULL,0,0,NULL,1,0,106,106,'nicole','nicole' from dual union all
select 107,'T07','T07',1,NULL,'Tech07','Tech07',NULL,NULL,NULL,0,0,NULL,1,0,107,107,'nicole','nicole' from dual union all
select 108,'T08','T08',1,NULL,'Tech08','Tech08',NULL,NULL,NULL,0,0,NULL,1,0,108,108,'nicole','nicole' from dual union all
select 109,'T09','T09',1,NULL,'Tech09','Tech09',NULL,NULL,NULL,0,0,NULL,1,0,109,109,'nicole','nicole' from dual union all
select 110,'T10','T10',1,NULL,'Tech10','Tech10',NULL,NULL,NULL,0,0,NULL,1,0,110,110,'nicole','nicole' from dual union all
select 111,'T11','T11',1,NULL,'Tech11','Tech11',NULL,NULL,NULL,0,0,NULL,1,0,111,111,'nicole','nicole' from dual union all
select 112,'T12','T12',1,NULL,'Tech12','Tech12',NULL,NULL,NULL,0,0,NULL,1,0,112,112,'nicole','nicole' from dual union all
select 113,'T13','T13',1,NULL,'Tech13','Tech13',NULL,NULL,NULL,0,0,NULL,1,0,113,113,'nicole','nicole' from dual union all
select 114,'T14','T14',1,NULL,'Tech14','Tech14',NULL,NULL,NULL,0,0,NULL,1,0,114,114,'nicole','nicole' from dual union all
select 115,'T15','T15',1,NULL,'Tech15','Tech15',NULL,NULL,NULL,0,0,NULL,1,0,115,115,'nicole','nicole' from dual union all
select 116,'T16','T16',1,NULL,'Tech16','Tech16',NULL,NULL,NULL,0,0,NULL,1,0,116,116,'nicole','nicole' from dual union all
select 117,'T17','T17',1,NULL,'Tech17','Tech17',NULL,NULL,NULL,0,0,NULL,1,0,117,117,'nicole','nicole' from dual union all
select 118,'T18','T18',1,NULL,'Tech18','Tech18',NULL,NULL,NULL,0,0,NULL,1,0,118,118,'nicole','nicole' from dual union all
select 119,'T19','T19',1,NULL,'Tech19','Tech19',NULL,NULL,NULL,0,0,NULL,1,0,119,119,'nicole','nicole' from dual union all
select 120,'T20','T20',1,NULL,'Tech20','Tech20',NULL,NULL,NULL,0,0,NULL,1,0,120,120,'nicole','nicole' from dual;
commit;
insert into M_User(USER_KEY,USER_ID,ADMIN_FLG,CURRENT_PASSWORD,CLINICAL_STAFF_KEY, NEXT_CHANGE_PASSWORD_FLG,LAST_CHANGE_PASSWORD_DATE_TIME,REVOKED_FLG,REVOKED_PAST_DATE_TIME,INCORRECT_LOGIN_COUNT,ENABLED_DATE_FROM,ENABLED_DATE_TO,OLYMPUS_USER_FLG,USER_ROLE_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,CREATE_USER_ID,UPDATE_USER_ID) 
select 101,'T01',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',101,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,101,'nicole','nicole' from dual union all
select 102,'T02',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',102,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,102,'nicole','nicole' from dual union all
select 103,'T03',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',103,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,103,'nicole','nicole' from dual union all
select 104,'T04',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',104,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,104,'nicole','nicole' from dual union all
select 105,'T05',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',105,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,105,'nicole','nicole' from dual union all
select 106,'T06',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',106,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,106,'nicole','nicole' from dual union all
select 107,'T07',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',107,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,107,'nicole','nicole' from dual union all
select 108,'T08',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',108,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,108,'nicole','nicole' from dual union all
select 109,'T09',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',109,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,109,'nicole','nicole' from dual union all
select 110,'T10',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',110,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,110,'nicole','nicole' from dual union all
select 111,'T11',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',111,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,111,'nicole','nicole' from dual union all
select 112,'T12',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',112,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,112,'nicole','nicole' from dual union all
select 113,'T13',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',113,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,113,'nicole','nicole' from dual union all
select 114,'T14',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',114,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,114,'nicole','nicole' from dual union all
select 115,'T15',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',115,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,115,'nicole','nicole' from dual union all
select 116,'T16',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',116,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,116,'nicole','nicole' from dual union all
select 117,'T17',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',117,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,117,'nicole','nicole' from dual union all
select 118,'T18',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',118,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,118,'nicole','nicole' from dual union all
select 119,'T19',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',119,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,119,'nicole','nicole' from dual union all
select 120,'T20',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',120,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,120,'nicole','nicole' from dual;
commit;
