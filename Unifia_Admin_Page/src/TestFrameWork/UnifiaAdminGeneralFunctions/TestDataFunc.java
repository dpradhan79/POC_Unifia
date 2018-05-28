package TestFrameWork.UnifiaAdminGeneralFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDataFunc {
	private static Connection conn= null;
	public static void insertMasterData(String url, String user, String pass, int KE_Enabled, int Bioburden, int Culture) {
		String stmt0="delete from ExamQueue;delete from ProcedureRoomStatus;delete from Patient;\r\n IF CURSOR_STATUS('global','cur')>=-1\r\n"
				+ "BEGIN \r\nDEALLOCATE cur\r\n"
				+ "END\r\n";
		String stmt1 = "CREATE TABLE temp_Patient([PatientID_PK] [int] NOT NULL, [PatientID] [VarChar](50) NOT NULL) " 
			+"INSERT INTO temp_Patient (PatientID_PK, PatientID) VALUES"
			+"(38, 'MRN111111'),"
			+"(39, 'MRN222222'),"
			+"(40, 'MRN333333'),"
			+"(41, 'MRN444444'),"
			+"(42, 'MRN555555'),"
			+"(43, 'MRN666666'),"
			+"(44, 'MRN777777'),"
			+"(45, 'MRN888888'),"
			+"(46, 'MRN999999'),"
			+"(47, 'MRN101010'),"
			+"(48, 'MRN010101'),"
			+"(49, 'MRN121212'),"
			+"(50, 'MRN131313'),"
			+"(51, 'MRN141414'),"
			+"(52, 'MRN151515'),"
			+"(53, 'MRN161616'),"
			+"(54, 'MRN171717'),"
			+"(55, 'MRN181818'),"
			+"(56, 'MRN191919'),"
			+"(57, 'MRN212121'),"
			+"(58, 'MRN242424'),"
			+"(59, 'MRN202020'),"
			+"(60, 'MRN252525'),"
			+"(61, 'MRN262626'),"
			+"(62, 'MRN272727'),"
			+"(63, 'MRN282828'),"
			+"(64, 'MRN292929'),"
			+"(65, 'MRN00001'),"
			+"(66, 'MRN00002'),"
			+"(67, 'MRN00003'),"
			+"(68, 'MRN00004'),"
			+"(69, 'MRN00005'),"
			+"(70,'MRN00006'),"
			+"(71,'MRN00007'),"
			+"(72,'MRN00008'),"
			+"(73,'MRN00009'),"
			+"(74,'MRN00010'),"
			+"(75,'MRN00011'),"
			+"(76,'MRN00012'),"
			+"(77,'MRN00013'),"
			+"(78,'MRN00014'),"
			+"(79,'MRN00015'),"
			+"(80,'MRN00016'),"
			+"(81,'MRN00017'),"
			+"(82,'MRN00018'),"
			+"(83,'MRN00019'),"
			+"(84,'MRN00020'),"
			+"(85,'MRN00021'),"
			+"(86,'MRN00022'),"
			+"(87,'MRN00023'),"
			+"(88,'MRN00024'),"
			+"(89,'MRN00025'),"
			+"(90,'MRN00026'),"
			+"(91,'MRN00027'),"
			+"(92,'MRN00028'),"
			+"(93,'MRN00029'),"
			+"(94,'MRN00030'),"
			+"(95,'MRN00031'),"
			+"(96,'MRN00032'),"
			+"(97,'MRN00033'),"
			+"(98,'MRN00034'),"
			+"(99,'MRN00035'),"
			+"(100,'MRN00036'),"
			+"(101,'MRN00037'),"
			+"(102,'MRN00038'),"
			+"(103,'MRN00039'),"
			+"(104,'MRN00040'),"
			+"(105,'MRN00041'),"
			+"(106,'MRN00042'),"
			+"(107,'MRN00043'),"
			+"(108,'MRN00044'),"
			+"(109,'MRN00045'),"
			+"(110,'MRN00046'),"
			+"(111,'MRN00047'),"
			+"(112,'MRN00048'),"
			+"(113,'MRN00049'),"
			+"(114,'MRN00050'),"
			+"(115,'MRN00051'),"
			+"(116,'MRN00052'),"
			+"(117,'MRN00053'),"
			+"(118,'MRN00054'),"
			+"(119,'MRN00055'),"
			+"(120,'MRN00056'),"
			+"(121,'MRN00057'),"
			+"(122,'MRN00058'),"
			+"(123,'MRN00059'),"
			+"(124,'MRN00060'),"
			+"(125,'MRN00061'),"
			+"(126,'MRN00062'),"
			+"(127,'MRN00063'),"
			+"(128,'MRN00064'),"
			+"(129,'MRN00065'),"
			+"(130,'MRN00066'),"
			+"(131,'MRN00067'),"
			+"(132,'MRN00068'),"
			+"(133,'MRN00069'),"
			+"(134,'MRN00070'),"
			+"(135,'MRN00071'),"
			+"(136,'MRN00072'),"
			+"(137,'MRN00073'),"
			+"(138,'MRN00074'),"
			+"(139,'MRN00075'),"
			+"(140,'MRN00076'),"
			+"(141,'MRN00077'),"
			+"(142,'MRN00078'),"
			+"(143,'MRN00079'),"
			+"(144,'MRN00080'),"
			+"(145,'MRN00081'),"
			+"(146,'MRN00082'),"
			+"(147,'MRN00083'),"
			+"(148,'MRN00084'),"
			+"(149,'MRN00085'),"
			+"(150,'MRN00086'),"
			+"(151,'MRN00087'),"
			+"(152,'MRN00088'),"
			+"(153,'MRN00089'),"
			+"(154,'MRN00090'),"
			+"(155,'MRN00091'),"
			+"(156,'MRN00092'),"
			+"(157,'MRN00093'),"
			+"(158,'MRN00094'),"
			+"(159,'MRN00095'),"
			+"(160,'MRN00096'),"
			+"(161,'MRN00097'),"
			+"(162,'MRN00098'),"
			+"(163,'MRN00099'),"
			+"(164,'MRN00100');"
			+"DELETE FROM Patient; SET IDENTITY_INSERT Patient ON; SET ANSI_NULLS ON; SET QUOTED_IDENTIFIER ON;";
			// Create stored procedure 
			String stmt2= "Create PROCEDURE [dbo].[sp_InsertPatient_test] @PatientID_PK int, @PatientID varchar(50) AS BEGIN SET NOCOUNT ON; BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; "+"INSERT INTO [dbo].[Patient] ([PatientID_PK],[PatientID]) VALUES (@PatientID_PK, ENCRYPTBYKEY(KEY_GUID('UNIFIA_SYMKEY_01'), @PatientID))  CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS(SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END CATCH END;"; 
			// create cursor call sp and insert decrypted values into Patient
			String stmt3="Declare @PatientID_PK1 int, @PatientID1 VarChar(50); Declare Cur Cursor For Select PatientID_PK, PatientID FROM temp_Patient; Open Cur; Fetch Next from Cur into @PatientID_PK1, @PatientID1 While @@Fetch_Status=0 Begin Execute sp_InsertPatient_test @PatientID_PK1, @PatientID1; Fetch NEXT FROM Cur Into @PatientID_PK1, @PatientID1; END; CLOSE Cur; DEALLOCATE Cur;";
			// drop patient 
			String stmt4="Drop Table dbo.temp_Patient;";
			// drop sp
			String stmt5=" DROP PRocedure dbo.sp_InsertPatient_test;";
			//end of patient data
			String stmt6="SET IDENTITY_INSERT Association OFF;"
				+"SET IDENTITY_INSERT dbo.ItemHistory OFF;"
				+"SET IDENTITY_INSERT  dbo.keyentryScans OFF;"
				+"SET IDENTITY_INSERT Patient OFF;"
				+ "delete from LeakTesterDetail;"
				+"delete from SoiledAreaSignOff;"
				+"delete from RelatedItem;"
				+"delete from KeyEntryScans;"
				+"delete from ReconciliationActivityLogValue;"
				+"delete from Barcode where IsShipped=0;"
				+"delete from ReconciliationActivityLog;"
				+"delete from ReconciliationReportComment;"
				+"delete from ItemHistory;"
				+"delete from ScopeCycle;"
				+"delete from ScopeStatus;"
				+"delete from Association;"
				+"delete from ScopeCycle;"
				+"delete from LocationStatus;"
				+ "delete FROM LeakTesterDetail;"
				+"delete from ReprocessingStatus;"
				+"delete from ScopeStatus;"
				+"delete from ItemHistory;"
				+"delete from Scanner;"
				+"delete from Scope;"
				+"delete from ExamType where IsShipped=0;"
				+"delete from User_Facility_Assoc where UserID_PK>1;"
				+"delete from User_Role_Assoc where UserID_PK>1;"
				+"delete from [User] where UserID_PK > 1;"
				+"delete from ScopeType where IsShipped=0;"
				+"delete from AERDetail;"
				+"delete from Location;"
				+"delete from AccessPoint;"
				+"delete from Staff;"
				+"delete from Facility where FacilityID_PK > 1;"
				+"update ExamType set IsActive=0 where ExamTypeID_PK=13;"
				+"update Facility set Name='Your Facility Name', Abbreviation='YFN', IsBioburdenTestingPerformed="+Bioburden+", "
				+ "IsCulturingPerformed="+Culture+", IsKEEnabled="+KE_Enabled+", IsPrimary=1 where FacilityID_PK=1;"
				+"update [User] set IsActive=1 where UserID_PK=1;"
				+"SET IDENTITY_INSERT AccessPoint ON;"
				+"Insert INTO AccessPoint(AccessPointID_PK, SSID, Password) values"
				+"(1, 'Scanners',  '11111111'),"
				+"(2, 'Linksys11854',  'dnkdj0afi8'),"
				+"(3, 'Linksys11855',  'dnkdj0afi8'); "
				+"SET IDENTITY_INSERT AccessPoint OFF;"
				+"SET IDENTITY_INSERT Location ON;"
				+"Insert INTO Location(LocationID_PK, Name, IsActive, FacilityID_FK, LocationTypeID_FK, AccessPointID_FK)"
				+"values "
				+"(1, 'Administration', 'True', 1, 1, 1),"
				+"(21, 'Procedure Room 1', 'True', 1, 2, 1),"
				+"(22, 'Procedure Room 2', 'True', 1, 2, 1),"
				+"(23, 'Procedure Room 3', 'True', 1, 2, 1),"
				+"(24, 'Procedure Room 4', 'False', 1, 2, 1),"
				+"(25, 'Procedure Room 5', 'True', 1, 2, 1),"
				+"(26, 'Procedure Room 6', 'True', 1, 2, 1),"
				+"(27, 'Procedure Room 7', 'True', 1, 2, 1),"
				+"(28, 'Procedure Room 8', 'True', 1, 2, 1),"
				+"(29, 'Procedure Room 9', 'True', 1, 2, 1),"
				+"(41, 'Sink 1', 'True', 1, 4, 1),"
				+"(42, 'Sink 2', 'True', 1, 4, 1),"
				+"(43, 'Sink 3', 'False', 1, 4, 1),"
				+"(44, 'Sink 4', 'True', 1, 4, 1),"
				+"(45, 'Sink 5', 'True', 1, 4, 1),"
				+"(46, 'Sink 6', 'True', 1, 4, 1),"
				+"(51, 'Reprocessor 1', 'True', 1, 5, 1),"
				+"(52, 'Reprocessor 2', 'True', 1, 5, 1),"
				+"(53, 'Reprocessor 3', 'False', 1, 5, 1),"
				+"(54, 'Reprocessor 4', 'True', 1, 5, 1),"
				+"(55, 'Reprocessor 5', 'True', 1, 5, 1),"
				+"(56, 'Reprocessor 6', 'True', 1, 5, 1),"
				+"(71, 'Waiting1', 'True', 1, 7, 1),"
				+"(72, 'Waiting2', 'True', 1, 7, 1), "
				+"(73, 'Bioburden1', 'True',1,8,1),"
				+"(74, 'Bioburden2', 'True',1,8,1),"
				+"(75, 'Culturing1', 'True', 1,9, 1),"
				+"(81, 'CultureA', 'True', 1, 9, 2),"
				+"(133, 'ReprocessingRoom1', 'True', 1, 10, 1);"
				+"Insert INTO Location(LocationID_PK, Name, IsActive, FacilityID_FK, LocationTypeID_FK, AccessPointID_FK, StorageCabinetCount) values "
				+"(31, 'Storage Area A', 'True', 1, 3, 1, 4),"
				+"(32, 'Storage Area B', 'True', 1, 3, 1, 1),"
				+"(33, 'Storage Area C', 'False', 1, 3, 1, 2),"
				+"(34, 'Storage Area D', 'True', 1, 3, 1, 4),"
				+"(35, 'Culture Hold Cabinet', 'True', 1, 3, 1, 1);"
				+"SET IDENTITY_INSERT Location OFF;"
				+"SET IDENTITY_INSERT AERDetail ON;"
				+"Insert INTO AERDetail(AERDetailID_PK, Model, SerialNumber, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, LocationID_FK)"
				+"values "
				+"(1, 'OER-Pro', '2000001', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 51),"
				+"(2, 'OER-Pro', '2000002', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 52),"
				+"(3, 'OER-Pro', '2000003', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 53),"
				+"(4, 'OER-Pro', '2000004', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 54),"
				+"(5, 'OER-Pro', '2000005', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 55),"
				+"(6, 'OER-Pro', '2000006', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 56);"
				+"SET IDENTITY_INSERT AERDetail OFF;"
				+"SET IDENTITY_INSERT Scanner ON;"
				+"Insert INTO Scanner(ScannerID_PK, Name, ScannerID, IsActive, LocationID_FK) "
				+"values"
				+"(1, 'Administration', '016311', 'True', 1),"
				+"(21, 'PR1', '024285', 'True', 21), "
				+"(22, 'PR2', '024284', 'True', 22),  "
				+"(23, 'PR3', '016306', 'True', 23), "
				+"(24, 'PR4', '016317', 'False', 24), "
				+"(25, 'PR5', '017002', 'True', 25), "
				+"(26, 'PR6', '015308', 'True', 26),  "
				+"(27, 'PR7', '016307', 'True', 27),  "
				+"(28, 'PR8', '016328', 'True', 28), "
				+"(29, 'PR9', '016351', 'True', 29), "
				+"(41, 'Sink1', '024286', 'True', 41), "
				+"(42, 'Sink2', '015311', 'True', 42), "
				+"(43, 'Sink3', '018878', 'True', 43), "
				+"(44, 'Sink4', '016308', 'True', 44), "
				+"(45, 'Sink5', '017003', 'True', 45), "
				+"(46, 'Sink6', '015310', 'True', 46), "
				+"(51, 'Reprocessor1', '015314', 'True', 51), "
				+"(52, 'Reprocessor2', '015313', 'True', 52), "
				+"(53, 'Reprocessor3', '018879', 'False', 53), "
				+"(54, 'Reprocessor4', '015315', 'True', 54), "
				+"(55, 'Reprocessor5', '016320', 'True', 55), "
				+"(56, 'Reprocessor6', '016321', 'True', 56), "
				+"(71, 'Waiting1', '015312', 'True', 71), "
				+"(72, 'Waiting2', '003041', 'True', 72),  "
				+"(73, 'Bioburden1','017010', 'True', 73),"
				+"(74, 'Bioburden2','017011', 'True', 74),"
				+"(75, 'Culturing1','017020','True', 75),"
				+"(31, 'StorageA', '018880', 'True', 31),"
				+"(35, 'Culture Hold Cabinet', '019001', 'True', 35),"
				+"(32, 'StorageB', '018877', 'True', 32), "
				+"(33, 'StorageC', '017001', 'False', 33), "
				+"(34, 'StorageD', '013509', 'True', 34),  "
				+"(81, 'CultureA', '019313', 'True', 81),"
				+"(133, 'ReprocessingRoom1', '012345', 'True', 133);"
				+"SET IDENTITY_INSERT Scanner OFF;"
				+"SET IDENTITY_INSERT Scope ON;"
				+"Insert INTO Scope (ScopeID_PK, RFUID, Name, SerialNumber, FacilityID_FK, ScopeTypeID_FK, IsActive, Comments, IsShipped, CompletedCycleCount) Values "
				+"(1, 'e0040150409251e7', 'Scope1', '1122334', 1, 183, 1, '', 0, 0), "
				+"(2, 'e004015040926c20', 'Scope2', '2233445', 1, 184, 1, '', 0, 0), "
				+"(3, 'e004015040926294', 'Scope3', '3344556', 1, 181, 1, '', 0, 0), "
				+"(4, 'e00401504092a32e', 'Scope4', '4455667', 1, 96, 1, '', 0, 0),    "
				+"(5, 'e004015040926037', 'Scope5', '5566778', 1, 97, 1, '', 0, 0), "
				+"(6, 'e004015040924a80', 'Scope6', '6677889', 1, 96, 1, '', 0, 0), "
				+"(7, 'e00401504092a30f', 'Scope7', '7654231', 1, 155, 1, '', 0, 0), "
				+"(8, 'e00401504092737e', 'Scope8', '6543216', 1, 170, 1, '', 0, 0), "
				+"(9, 'e004015040926fe2', 'Scope9', '9876432', 1, 14, 1, '', 0, 0), "
				+"(10, 'e00401504092af6e', 'Scope10', '7654321', 1, 155, 1, '', 0, 5), "
				+"(11, 'e00401504092482b', 'Scope11', '9988776', 1, 184, 1, '', 0, 2), "
				+"(12, 'e00401001891a55e', 'Scope12', '2808645', 1, 181, 1, '', 0, 20), "
				+"(13, 'e004010002faf288', 'Scope13', '2600842', 1, 181, 1, '', 0, 10),"
				+"(14, 'e004010010ab6695', 'Scope14', '2806734', 1, 181, 1, '', 0, 130),"
				+"(15, 'e00401005f093832', 'Scope15', '2200174', 1, 183, 1, '', 0, 25),"
				+"(16, 'e004010002fb0f38', 'Scope16', '2601311', 1, 181, 1, '', 0, 11),"
				+"(17, 'e004010002fb43fb', 'Scope17', '2600163', 1, 181, 1, '', 0, 0),"
				+"(18, 'e00401504092483f', 'Scope18', '2211009', 1, 86, 1, '', 0, 50), "
				+"(19, 'e004010010ab6918', 'Scope19', '2809094', 1, 196, 1, '', 0, 55),"
				+"(20, 'e003020040bb6c18', 'Scope20', '0099887', 1, 87, 1, '', 0, 12), "
				+"(21, 'e003001452ca51b1', 'Scope21', '2112233', 1, 87, 1, '', 0, 13),"
				+"(22, 'e003001780332ab2', 'Scope22', '2223344', 1, 86, 1, '', 0, 22), "
				+"(23, 'e0030013762ce232', 'Scope23', '2334455', 1, 86, 1, '', 0, 11), "
				+"(24, 'e004010002faf238', 'Scope24', '2600319', 1, 94, 1, '', 0, 0), "
				+"(25, 'e004010002fafd0e', 'Scope25', '2602572', 1, 196, 1, '', 0, 0),"
				+"(26, 'e004010002fb085e', 'Scope26', '2601132', 1, 181, 1, '', 0, 19),"
				+"(27, 'e004010002fafaff', 'Scope27', '2600266', 1, 181, 1, '', 0, 6),"
				+"(28, 'e004010010ab5baf', 'Scope28', '2707317', 1, 196, 1, '', 0, 9),"
				+"(29, 'e004015040926e8b', 'Scope29', '5556667', 1, 89, 1, '', 0, 14),"
				+"(30, 'e00301703198a8c2', 'Scope30', '6667778', 1, 89, 1, '', 0, 3),"
				+"(31, 'e00301890b2d245f', 'Scope31', '7778889', 1, 155, 1, '', 0, 323),"
				+"(32, 'e00301701100ba21', 'Scope32', '8889990', 1, 155, 1, '', 0, 23),"
				+"(33, 'e00401005f092678', 'Scope33', '2200687', 1, 184, 1, '', 0, 23), "
				+"(34, 'e004010002fb4b21', 'Scope34', '2500850', 1, 196, 1, '', 0, 23), "
				+"(35, 'e004010002fb18be', 'Scope35', '2600714', 1, 181, 1, '', 0, 23), "
				+"(36, 'e00300129643dc16', 'Scope36', '2700854', 1, 87, 1, '', 0, 13),"
				+"(37, 'e003003013561ab2', 'Scope37', '8165413', 1, 86, 1, '', 0, 22), "
				+"(38, 'e004010003fb18be', 'Scope38', '2600715', 1, 181, 1, '', 0, 0), "
				+"(39, 'e004010004fb18be', 'Scope39', '2600716', 1, 181, 1, '', 0, 0), "
				+"(40, 'e004010005fb18be', 'Scope40', '2600717', 1, 181, 1, '', 0, 0), "
				+"(41, 'e004010006fb18be', 'Scope41', '2600718', 1, 181, 1, '', 0, 0), "
				+"(42, 'e004010007fb18be', 'Scope42', '2600719', 1, 181, 1, '', 0, 0), "
				+"(43, 'e004010008fb18be', 'Scope43', '2600720', 1, 181, 1, '', 0, 0), "
				+"(44, 'e004010009fb18be', 'Scope44', '2600721', 1, 181, 1, '', 0, 0), "
				+"(45, 'e004010012fb18be', 'Scope45', '2600814', 1, 181, 1, '', 0, 0), "
				+"(46, 'e004010013fb18be', 'Scope46', '2600815', 1, 181, 1, '', 0, 0), "
				+"(47, 'e004010014fb18be', 'Scope47', '2600816', 1, 181, 1, '', 0, 0), "
				+"(48, 'e004010033fb18be', 'Scope48', '2600825', 1, 181, 1, '', 0, 0), "
				+"(49, 'e004010034fb18be', 'Scope49', '2600826', 1, 181, 1, '', 0, 0), "
				+"(50, 'e004010015fb18be', 'Scope50', '2600817', 1, 181, 1, '', 0, 0), "
				+"(51, 'e004010016fb18be', 'Scope51', '2600818', 1, 181, 1, '', 0, 0), "
				+"(52, 'e004010017fb18be', 'Scope52', '2600819', 1, 181, 1, '', 0, 0), "
				+"(53, 'e004010018fb18be', 'Scope53', '2600820', 1, 181, 1, '', 0, 0), "
				+"(54, 'e004010019fb18be', 'Scope54', '2600821', 1, 181, 1, '', 0, 0), "
				+"(55, 'e004010022fb18be', 'Scope55', '2600834', 1, 181, 1, '', 0, 0), "
				+"(56, 'e004000023fb18be', 'Scope56', '3600835', 1, 181, 1, '', 0, 0), "
				+"(57, 'e004020014fb18be', 'Scope57', '3600816', 1, 181, 1, '', 0, 0), "
				+"(58, 'e004020033fb18be', 'Scope58', '3600825', 1, 181, 1, '', 0, 0), "
				+"(59, 'e004020034fb18be', 'Scope59', '3600826', 1, 181, 1, '', 0, 0), "
				+"(60, 'e004020015fb18be', 'Scope60', '3600817', 1, 181, 1, '', 0, 0), "
				+"(61, 'e004020016fb18be', 'Scope61', '3600818', 1, 181, 1, '', 0, 0), "
				+"(62, 'e004020017fb18be', 'Scope62', '3600819', 1, 181, 1, '', 0, 0), "
				+"(63, 'e004020018fb18be', 'Scope63', '3600820', 1, 181, 1, '', 0, 0), "
				+"(64, 'e004020019fb18be', 'Scope64', '3600821', 1, 181, 1, '', 0, 0), "
				+"(65, 'e004020022fb18be', 'Scope65', '3600834', 1, 181, 1, '', 0, 0), "
				+"(66, 'e004030023fb18be', 'Scope66', '4600835', 1, 181, 1, '', 0, 0), "
				+"(67, 'e004030014fb18be', 'Scope67', '4600816', 1, 181, 1, '', 0, 0), "
				+"(68, 'e004030033fb18be', 'Scope68', '4600825', 1, 181, 1, '', 0, 0), "
				+"(69, 'e004030034fb18be', 'Scope69', '4600826', 1, 181, 1, '', 0, 0), "
				+"(70, 'e004030015fb18be', 'Scope70', '4600817', 1, 181, 1, '', 0, 0), "
				+"(71, 'e004030016fb18be', 'Scope71', '4600818', 1, 181, 1, '', 0, 0), "
				+"(72, 'e004030017fb18be', 'Scope72', '4600819', 1, 181, 1, '', 0, 0), "
				+"(73, 'e004030018fb18be', 'Scope73', '4600820', 1, 181, 1, '', 0, 0), "
				+"(74, 'e004030019fb18be', 'Scope74', '4600821', 1, 181, 1, '', 0, 0), "
				+"(75, 'e004030022fb18be', 'Scope75', '4600834', 1, 181, 1, '', 0, 0), "
				+"(76, 'e004040023fb18be', 'Scope76', '5600835', 1, 181, 1, '', 0, 0), "
				+"(77, 'e004040014fb18be', 'Scope77', '5600816', 1, 181, 1, '', 0, 0), "
				+"(78, 'e004040033fb18be', 'Scope78', '5600825', 1, 181, 1, '', 0, 0), "
				+"(79, 'e004040034fb18be', 'Scope79', '5600826', 1, 181, 1, '', 0, 0), "
				+"(80, 'e004040015fb18be', 'Scope80', '5600817', 1, 181, 1, '', 0, 0), "
				+"(81, 'e004040016fb18be', 'Scope81', '5600818', 1, 181, 1, '', 0, 0), "
				+"(82, 'e004040017fb18be', 'Scope82', '5600819', 1, 181, 1, '', 0, 0), "
				+"(83, 'e004040018fb18be', 'Scope83', '5600820', 1, 181, 1, '', 0, 0), "
				+"(84, 'e004040019fb18be', 'Scope84', '5600821', 1, 181, 1, '', 0, 0), "
				+"(85, 'e004040022fb18be', 'Scope85', '5600834', 1, 181, 1, '', 0, 0), "
				+"(86, 'e004050023fb18be', 'Scope86', '6600835', 1, 181, 1, '', 0, 0), "
				+"(87, 'e004050014fb18be', 'Scope87', '6600816', 1, 181, 1, '', 0, 0), "
				+"(88, 'e004050033fb18be', 'Scope88', '6600825', 1, 181, 1, '', 0, 0), "
				+"(89, 'e004050034fb18be', 'Scope89', '6600826', 1, 181, 1, '', 0, 0), "
				+"(90, 'e004050015fb18be', 'Scope90', '6600817', 1, 181, 1, '', 0, 0), "
				+"(91, 'e0030030135612ab', 'Scope91', '8165412', 1, 86,  1, '', 0, 22),"
				+"(92, 'e004060017fb18be', 'Scope92', '5500819', 1, 181, 1, '', 0, 0), "
				+"(93, 'e004060018fb18be', 'Scope93', '5500820', 1, 181, 1, '', 0, 0), "
				+"(94, 'e004060019fb18be', 'Scope94', '5500821', 1, 181, 1, '', 0, 0), "
				+"(95, 'e004060022fb18be', 'Scope95', '5500834', 1, 181, 1, '', 0, 0), "
				+"(96, 'e004060023fb18be', 'Scope96', '6500835', 1, 181, 1, '', 0, 0), "
				+"(97, 'e004060014fb18be', 'Scope97', '6500816', 1, 181, 1, '', 0, 0), "
				+"(101, 'e0030150409251e7', 'Scope101', '9122334', 1, 183, 1, '', 0, 0),"
				+"(102, 'e003015040926c20', 'Scope102', '9233445', 1, 184, 1, '', 0, 0),"
				+"(103, 'e003015040926294', 'Scope103', '9344556', 1, 181, 1, '', 0, 0),"
				+"(104, 'e00301504092a32e', 'Scope104', '9455667', 1, 96, 1, '', 0, 0), "
				+"(105, 'e003015040926037', 'Scope105', '9566778', 1, 97, 1, '', 0, 0), "
				+"(106, 'e003015040924a80', 'Scope106', '9677889', 1, 96, 1, '', 0, 0), "
				+"(107, 'e00301504092a30f', 'Scope107', '9654231', 1, 155, 1, '', 0, 0), "
				+"(108, 'e00301504092737e', 'Scope108', '9543216', 1, 170, 1, '', 0, 0), "
				+"(109, 'e003015040926fe2', 'Scope109', '8876432', 1, 14, 1, '', 0, 0), "
				+"(110, 'e00301504092af6e', 'Scope110', '9654321', 1, 155, 1, '', 0, 0),"
				+"(111, 'e00301504092482b', 'Scope111', '8988776', 1, 184, 1, '', 0, 0),"
				+"(112, 'e00301001891a55e', 'Scope112', '9808645', 1, 181, 1, '', 0, 0),"
				+"(158, 'e003020033fb18be', 'Scope158', '9600825', 1, 181, 1, '', 0, 0),"
				+"(159, 'e003020034fb18be', 'Scope159', '9600826', 1, 181, 1, '', 0, 0),"
				+"(160, 'e003020015fb18be', 'Scope160', '9600817', 1, 181, 1, '', 0, 0),"
				+"(161, 'e003020016fb18be', 'Scope161', '9600818', 1, 181, 1, '', 0, 0),"
				+"(162, 'e003020017fb18be', 'Scope162', '9600819', 1, 181, 1, '', 0, 0),"
				+"(163, 'e003020018fb18be', 'Scope163', '9600820', 1, 181, 1, '', 0, 0),"
				+"(164, 'e003020019fb18be', 'Scope164', '9600821', 1, 181, 1, '', 0, 0);"
				+"SET IDENTITY_INSERT Scope OFF;"
				+"SET IDENTITY_INSERT Staff ON;"
				+"INSERT INTO staff(StaffID_PK,TitleID_FK,FirstName,LastName,StaffID,StaffTypeID_FK,IsActive)"
				+"VALUES"
				+"(1,4,'Physician1','Physician1','MD01',1,'True'),"
				+"(2,4,'Physician2','Physician2','MD02',1,'True'),"
				+"(3,4,'Physician3','Physician3','MD03',1,'True'),"
				+"(4,4,'Physician4','Physician4','MD04',1,'True'),"
				+"(5,4,'Physician5','Physician5','MD05',1,'True'),"
				+"(6,4,'Physician6','Physician6','MD06',1,'True'),"
				+"(7,4,'Physician7','Physician7','MD07',1,'True'),"
				+"(8,4,'Physician8','Physician8','MD08',1,'True'),"
				+"(9,4,'Physician9','Physician9','MD09',1,'True'),"
				+"(10,4,'Physician10','Physician10','MD10',1,'True'),"
				+"(11,4,'Physician11','Physician11','MD11',1,'True'),"
				+"(12,4,'Physician11','Physician12','MD12',1,'True'),"
				+"(13,4,'Physician11','Physician13','MD13',1,'True'),"
				+"(14,4,'Physician11','Physician14','MD14',1,'True'),"
				+"(15,4,'Physician11','Physician15','MD15',1,'True'),"
				+"(16,4,'Physician11','Physician16','MD16',1,'True'),"
				+"(17,4,'Physician11','Physician17','MD17',1,'True'),"
				+"(18,4,'Physician11','Physician18','MD18',1,'True'),"
				+"(19,4,'Physician11','Physician19','MD19',1,'False'),"
				+"(20,4,'Physician11','Physician20','MD20',1,'False'),"
				+"(41,3,'Nurse1','Nurse1','RN01',2,'True'),"
				+"(42,1,'Nurse2','Nurse2','RN02',2,'True'),"
				+"(43,3,'Nurse3','Nurse3','RN03',2,'True'),"
				+"(44,1,'Nurse4','Nurse4','RN04',2,'True'),"
				+"(45,3,'Nurse5','Nurse5','RN05',2,'True'),"
				+"(46,1,'Nurse6','Nurse6','RN06',2,'True'),"
				+"(47,3,'Nurse7','Nurse7','RN07',2,'True'),"
				+"(48,1,'Nurse8','Nurse8','RN08',2,'True'),"
				+"(49,3,'Nurse9','Nurse9','RN09',2,'True'),"
				+"(50,1,'Nurse10','Nurse10','RN10',2,'True'),"
				+"(51,3,'Nurse11','Nurse11','RN11',2,'True'),"
				+"(52,1,'Nurse12','Nurse12','RN12',2,'True'),"
				+"(53,3,'Nurse13','Nurse13','RN13',2,'True'),"
				+"(54,1,'Nurse14','Nurse14','RN14',2,'True'),"
				+"(55,3,'Nurse15','Nurse15','RN15',2,'True'),"
				+"(56,1,'Nurse16','Nurse16','RN16',2,'True'),"
				+"(57,3,'Nurse17','Nurse17','RN17',2,'True'),"
				+"(58,1,'Nurse18','Nurse18','RN18',2,'True'),"
				+"(59,3,'Nurse19','Nurse19','RN19',2,'False'),"
				+"(60,1,'Nurse20','Nurse20','RN20',2,'False'),"
				+"(61,2,'Admin1','Admin1','AD127943',4,'True');"
				+"INSERT INTO staff(StaffID_PK,TitleID_FK,FirstName,LastName,StaffID,StaffTypeID_FK,IsActive,BadgeID)"
				+"VALUES"
				+"(21,3,'Tech1','Tech1','T01',5,'True', '813F6F82D25204'),"
				+"(22,1,'Tech2','Tech2','T02',5,'True', '813F6F82B95204'),"
				+"(23,3,'Tech3','Tech3','T03',5,'True', '813F6F82975204'),"
				+"(24,1,'Tech4','Tech4','T04',5,'True', '813F6F82B35204'),"
				+"(25,3,'Tech5','Tech5','T05',5,'True', '813F6F82925204'),"
				+"(26,1,'Tech6','Tech6','T06',5,'True', '813F6F828D5204'),"
				+"(27,3,'Tech7','Tech7','T07',5,'True', '813F6F82875104'),"
				+"(28,1,'Tech8','Tech8','T08',5,'True', '813F6F82835204'),"
				+"(29,3,'Tech9','Tech9','T09',5,'True', '813F6F827E5204'),"
				+"(30,1,'Tech10','Tech10','T10',5,'True','813F6F82AE5204'),"
				+"(31,3,'Tech11','Tech11','T11',5,'True', '813F6F82CD5204'),"
				+"(32,1,'Tech12','Tech12','T12',5,'True', '813F6F82A95204'),"
				+"(33,3,'Tech13','Tech13','T13',5,'True', '813F6F82C85204'),"
				+"(34,1,'Tech14','Tech14','T14',5,'True', '813F6F82A55304'),"
				+"(35,3,'Tech15','Tech15','T15',5,'True', '813F6F82C35204'),"
				+"(36,1,'Tech16','Tech16','T16',5,'True', '813F6F82A15204'),"
				+"(37,3,'Tech17','Tech17','T17',5,'True', '813F6F82BE5204'),"
				+"(38,1,'Tech18','Tech18','T18',5,'True', '813F6F829C5204'),"
				+"(39,3,'Tech19','Tech19','T19',5,'False', '802908e2468704'),"
				+"(40,1,'Tech20','Tech20','T20',5,'False','');"
				+"SET IDENTITY_INSERT Staff OFF;"
				+"SET IDENTITY_INSERT Barcode ON;"
				+"insert into Barcode(BarcodeID_PK, Name, BarcodeTypeID_FK, IsActive)"
				+"values"
				+"(11, 'Destination 1', 1, 1),"
				+"(12, 'Destination 2', 1, 1),"
				+"(13, 'Destination 3', 1, 1),"
				+"(14, 'Olympus Repair', 1, 1),"
				+"(15, 'Scope Repair Shop', 1, 1),"
				+"(16, 'Facility 2', 1, 1),"
				+"(17, 'Blue', 4, 1),"
				+"(18, 'Red', 4, 1),"
				+"(20, 'MRC Fail', 3, 1), "
				+"(21, 'Received Loaner Scope', 3, 1),"
				+"(23,'Custom Reason1', 3, 1),"
				+"(24,'Custom Reason2', 3, 1),"
				+"(25,'Custom Reason3', 3, 1),"
				+ "(26,'InactiveBioburden', 4, 0),"
				+ "(27,'InactiveOOF', 1, 0),"
				+ "(28,'InactiveReason', 3, 0);"
				+"SET IDENTITY_INSERT Barcode OFF;"
				+"SET IDENTITY_INSERT dbo.keyentryScans ON;"
				+"Insert into dbo.keyentryScans  (keyentryid_pk,keyentryvalue) Values"
				+"(1,1),"
				+"(2,2),"
				+"(3,3),"
				+"(4,4);"
				+"SET IDENTITY_INSERT keyentryScans OFF;";
			try{
	    		conn= DriverManager.getConnection(url, user, pass);		
	    		Statement update1 = conn.createStatement();
	    		update1.executeUpdate(stmt0);
	    		System.out.println("stmt0="+stmt0);
	    		update1.executeUpdate(stmt1);
	    		System.out.println("stmt1="+stmt1);
	    		update1.executeUpdate(stmt2);
	    		System.out.println("stmt2="+stmt2);
	    		update1.executeUpdate(stmt3);
	    		System.out.println("stmt3="+stmt3);
	    		update1.executeUpdate(stmt4);
	    		System.out.println("stmt4="+stmt4);
	    		update1.executeUpdate(stmt5);
	    		System.out.println("stmt5="+stmt5);
	    		update1.executeUpdate(stmt6);
	    		System.out.println("stmt6="+stmt6);
	    		
	    		conn.close();
	    	}
	    	catch (SQLException ex){
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());	
	    	}
	}
	
	public static void insertMultiFacilityMasterData(String url, String user, String pass, int KE_Enabled, int Bioburden, int Culture) {
		String stmt0="delete from ExamQueue;delete from ProcedureRoomStatus;delete from Patient;\r\n IF CURSOR_STATUS('global','cur')>=-1\r\n"
				+ "BEGIN \r\nDEALLOCATE cur\r\n"
				+ "END\r\n";
		String stmt1 = "CREATE TABLE temp_Patient([PatientID_PK] [int] NOT NULL, [PatientID] [VarChar](50) NOT NULL) " 
			+"INSERT INTO temp_Patient (PatientID_PK, PatientID) VALUES"
			+"(38, 'MRN111111'),"
			+"(39, 'MRN222222'),"
			+"(40, 'MRN333333'),"
			+"(41, 'MRN444444'),"
			+"(42, 'MRN555555'),"
			+"(43, 'MRN666666'),"
			+"(44, 'MRN777777'),"
			+"(45, 'MRN888888'),"
			+"(46, 'MRN999999'),"
			+"(47, 'MRN101010'),"
			+"(48, 'MRN010101'),"
			+"(49, 'MRN121212'),"
			+"(50, 'MRN131313'),"
			+"(51, 'MRN141414'),"
			+"(52, 'MRN151515'),"
			+"(53, 'MRN161616'),"
			+"(54, 'MRN171717'),"
			+"(55, 'MRN181818'),"
			+"(56, 'MRN191919'),"
			+"(57, 'MRN212121'),"
			+"(58, 'MRN242424'),"
			+"(59, 'MRN202020'),"
			+"(60, 'MRN252525'),"
			+"(61, 'MRN262626'),"
			+"(62, 'MRN272727'),"
			+"(63, 'MRN282828'),"
			+"(64, 'MRN292929'),"
			+"(65, 'MRN00001'),"
			+"(66, 'MRN00002'),"
			+"(67, 'MRN00003'),"
			+"(68, 'MRN00004'),"
			+"(69, 'MRN00005'),"
			+"(70,'MRN00006'),"
			+"(71,'MRN00007'),"
			+"(72,'MRN00008'),"
			+"(73,'MRN00009'),"
			+"(74,'MRN00010'),"
			+"(75,'MRN00011'),"
			+"(76,'MRN00012'),"
			+"(77,'MRN00013'),"
			+"(78,'MRN00014'),"
			+"(79,'MRN00015'),"
			+"(80,'MRN00016'),"
			+"(81,'MRN00017'),"
			+"(82,'MRN00018'),"
			+"(83,'MRN00019'),"
			+"(84,'MRN00020'),"
			+"(85,'MRN00021'),"
			+"(86,'MRN00022'),"
			+"(87,'MRN00023'),"
			+"(88,'MRN00024'),"
			+"(89,'MRN00025'),"
			+"(90,'MRN00026'),"
			+"(91,'MRN00027'),"
			+"(92,'MRN00028'),"
			+"(93,'MRN00029'),"
			+"(94,'MRN00030'),"
			+"(95,'MRN00031'),"
			+"(96,'MRN00032'),"
			+"(97,'MRN00033'),"
			+"(98,'MRN00034'),"
			+"(99,'MRN00035'),"
			+"(100,'MRN00036'),"
			+"(101,'MRN00037'),"
			+"(102,'MRN00038'),"
			+"(103,'MRN00039'),"
			+"(104,'MRN00040'),"
			+"(105,'MRN00041'),"
			+"(106,'MRN00042'),"
			+"(107,'MRN00043'),"
			+"(108,'MRN00044'),"
			+"(109,'MRN00045'),"
			+"(110,'MRN00046'),"
			+"(111,'MRN00047'),"
			+"(112,'MRN00048'),"
			+"(113,'MRN00049'),"
			+"(114,'MRN00050'),"
			+"(115,'MRN00051'),"
			+"(116,'MRN00052'),"
			+"(117,'MRN00053'),"
			+"(118,'MRN00054'),"
			+"(119,'MRN00055'),"
			+"(120,'MRN00056'),"
			+"(121,'MRN00057'),"
			+"(122,'MRN00058'),"
			+"(123,'MRN00059'),"
			+"(124,'MRN00060'),"
			+"(125,'MRN00061'),"
			+"(126,'MRN00062'),"
			+"(127,'MRN00063'),"
			+"(128,'MRN00064'),"
			+"(129,'MRN00065'),"
			+"(130,'MRN00066'),"
			+"(131,'MRN00067'),"
			+"(132,'MRN00068'),"
			+"(133,'MRN00069'),"
			+"(134,'MRN00070'),"
			+"(135,'MRN00071'),"
			+"(136,'MRN00072'),"
			+"(137,'MRN00073'),"
			+"(138,'MRN00074'),"
			+"(139,'MRN00075'),"
			+"(140,'MRN00076'),"
			+"(141,'MRN00077'),"
			+"(142,'MRN00078'),"
			+"(143,'MRN00079'),"
			+"(144,'MRN00080'),"
			+"(145,'MRN00081'),"
			+"(146,'MRN00082'),"
			+"(147,'MRN00083'),"
			+"(148,'MRN00084'),"
			+"(149,'MRN00085'),"
			+"(150,'MRN00086'),"
			+"(151,'MRN00087'),"
			+"(152,'MRN00088'),"
			+"(153,'MRN00089'),"
			+"(154,'MRN00090'),"
			+"(155,'MRN00091'),"
			+"(156,'MRN00092'),"
			+"(157,'MRN00093'),"
			+"(158,'MRN00094'),"
			+"(159,'MRN00095'),"
			+"(160,'MRN00096'),"
			+"(161,'MRN00097'),"
			+"(162,'MRN00098'),"
			+"(163,'MRN00099'),"
			+"(164,'MRN00100');"
			+"DELETE FROM Patient; SET IDENTITY_INSERT Patient ON; SET ANSI_NULLS ON; SET QUOTED_IDENTIFIER ON;";
			// Create stored procedure 
			String stmt2= "Create PROCEDURE [dbo].[sp_InsertPatient_test] @PatientID_PK int, @PatientID varchar(50) AS BEGIN SET NOCOUNT ON; BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; "+"INSERT INTO [dbo].[Patient] ([PatientID_PK],[PatientID]) VALUES (@PatientID_PK, ENCRYPTBYKEY(KEY_GUID('UNIFIA_SYMKEY_01'), @PatientID))  CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS(SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END CATCH END;"; 
			// create cursor call sp and insert decrypted values into Patient
			String stmt3="Declare @PatientID_PK1 int, @PatientID1 VarChar(50); Declare Cur Cursor For Select PatientID_PK, PatientID FROM temp_Patient; Open Cur; Fetch Next from Cur into @PatientID_PK1, @PatientID1 While @@Fetch_Status=0 Begin Execute sp_InsertPatient_test @PatientID_PK1, @PatientID1; Fetch NEXT FROM Cur Into @PatientID_PK1, @PatientID1; END; CLOSE Cur; DEALLOCATE Cur;";
			// drop patient 
			String stmt4="Drop Table dbo.temp_Patient;";
			// drop sp
			String stmt5=" DROP PRocedure dbo.sp_InsertPatient_test;";
			//end of patient data
			String stmt6="SET IDENTITY_INSERT Association OFF;"
					+"SET IDENTITY_INSERT dbo.ItemHistory OFF;"
					+"SET IDENTITY_INSERT  dbo.keyentryScans OFF;"
					+"SET IDENTITY_INSERT Patient OFF;"
					+ "delete from LeakTesterDetail;"					
					+"delete from SoiledAreaSignOff;"
					+"delete from RelatedItem;"
			+"delete from KeyEntryScans;"
			+"delete from ReconciliationActivityLogValue;"
			+"delete from Barcode where IsShipped=0;"
			+"delete from ReconciliationActivityLog;"
			+"delete from ReconciliationReportComment;"
			+"delete FROM ItemHistory;"
			+"delete from ScopeCycle;"
			+"delete from ProcedureRoomStatus;"
			+"delete FROM ScopeStatus;"
			+"delete FROM Association;"
			+"delete FROM ScopeCycle;"
			+"delete FROM LocationStatus;"
			+ "delete FROM LeakTesterDetail;"
			+"delete from ExamQueue;"
			+"delete From ReprocessingStatus;"
			+"delete from ScopeStatus;"
			+"delete from ItemHistory;"
			+"delete from Scanner;"
			+"delete from Scope;"
			+"delete from ExamType where IsShipped=0;"
			+"delete from User_Facility_Assoc where UserID_PK>1;"
			+"delete from User_Role_Assoc where UserID_PK>1;"
			+"delete from [User] where UserID_PK > 1;"
			+"delete from ScopeType where IsShipped=0;"
			+"delete from AERDetail;"
			+"delete from Location;"
			+"delete from AccessPoint;"
			+"delete from Staff;"
			+"delete from Facility where FacilityID_PK > 1;"
			+"update ExamType set IsActive=0 where ExamTypeID_PK=13;"
			+"update Facility set Name='Facility 1', Abbreviation='FAC1', IsBioburdenTestingPerformed=1,  IsCulturingPerformed=1, IsKEEnabled=0, IsPrimary=1,SerialNumber='111' where FacilityID_PK=1;"
			+"update [User] set IsActive=1 where UserID_PK=1;"
			+"set IDENTITY_Insert Facility ON;"
			+"Insert into Facility(FacilityID_PK,Name,IsActive,Abbreviation,CustomerNumber,HangTime,IsBioburdenTestingPerformed,IsCulturingPerformed,IsKEEnabled,IsPrimary,SerialNumber) values"
			+"(2,'Facility 2',1,'FAC2','2',7,0,0,0,0,'222'),"
			+"(3,'Facility 3',1,'FAC3','3',7,0,0,0,0,'333');"
			+"set IDENTITY_Insert Facility OFF;"
			+"set IDENTITY_Insert AccessPoint ON;"
			+"Insert INTO AccessPoint(AccessPointID_PK, SSID, Password) values" 
			+"(1, 'Scanners',  '11111111'),"
			+"(2, 'Linksys11854',  'dnkdj0afi8'),"
			+"(3, 'Linksys11855',  'dnkdj0afi8');" 
			+"set IDENTITY_Insert AccessPoint OFF;"
			+"set IDENTITY_Insert Location ON;"
			+"Insert INTO Location(LocationID_PK, Name, IsActive, FacilityID_FK, LocationTypeID_FK, AccessPointID_FK) values" 
			+"(1, 'F1 Administration', 'True', 1, 1, 1),"
			+"(21, 'F1 Procedure Room 1', 'True', 1, 2, 1),"
			+"(22, 'F1 Procedure Room 2', 'True', 1, 2, 1),"
			+"(23, 'F1 Procedure Room 3', 'True', 1, 2, 1),"
			+"(24, 'F1 Procedure Room 4', 'False', 1, 2, 1),"
			+"(25, 'F1 Procedure Room 5', 'True', 1, 2, 1),"
			+"(26, 'F1 Procedure Room 6', 'True', 1, 2, 1),"
			+"(27, 'F1 Procedure Room 7', 'True', 1, 2, 1),"
			+"(28, 'F1 Procedure Room 8', 'True', 1, 2, 1),"
			+"(29, 'F1 Procedure Room 9', 'True', 1, 2, 1),"
			+"(41, 'F1 Sink 1', 'True', 1, 4, 1),"
			+"(42, 'F1 Sink 2', 'True', 1, 4, 1),"
			+"(43, 'F1 Sink 3', 'False', 1, 4, 1),"
			+"(44, 'F1 Sink 4', 'True', 1, 4, 1),"
			+"(45, 'F1 Sink 5', 'True', 1, 4, 1),"
			+"(46, 'F1 Sink 6', 'True', 1, 4, 1),"
			+"(51, 'F1 Reprocessor 1', 'True', 1, 5, 1),"
			+"(52, 'F1 Reprocessor 2', 'True', 1, 5, 1),"
			+"(53, 'F1 Reprocessor 3', 'False', 1, 5, 1),"
			+"(54, 'F1 Reprocessor 4', 'True', 1, 5, 1),"
			+"(55, 'F1 Reprocessor 5', 'True', 1, 5, 1),"
			+"(56, 'F1 Reprocessor 6', 'True', 1, 5, 1),"
			+"(71, 'F1 Waiting1', 'True', 1, 7, 1),"
			+"(72, 'F1 Waiting2', 'True', 1, 7, 1)," 
			+"(73, 'F1 Bioburden1', 'True',1,8,1),"
			+"(75, 'F1 Bioburden2', 'True',1,8,1),"
			+"(74, 'F1 CultureB', 'True', 1,9, 1),"
			+"(81, 'F1 CultureA', 'True', 1, 9, 2),"
			+"(101, 'F2 Administration', 'True', 2, 1, 2),"
			+"(121, 'F2 Procedure Room 1', 'True', 2, 2, 2),"
			+"(122, 'F2 Procedure Room 2', 'True', 2, 2, 2),"
			+"(123, 'F2 Procedure Room 3', 'True', 2, 2, 2),"
			+"(124, 'F2 Procedure Room 4', 'False', 2, 2, 2),"
			+"(125, 'F2 Procedure Room 5', 'True', 2, 2, 2),"
			+"(126, 'F2 Procedure Room 6', 'True', 2, 2, 2),"
			+"(127, 'F2 Procedure Room 7', 'True', 2, 2, 2),"
			+"(128, 'F2 Procedure Room 8', 'True', 2, 2, 2),"
			+"(129, 'F2 Procedure Room 9', 'True', 2, 2, 2),"
			+"(141, 'F2 Sink 1', 'True', 2, 4, 2),"
			+"(142, 'F2 Sink 2', 'True', 2, 4, 2),"
			+"(143, 'F2 Sink 3', 'False', 2, 4, 2),"
			+"(144, 'F2 Sink 4', 'True', 2, 4, 2),"
			+"(145, 'F2 Sink 5', 'True', 2, 4, 2),"
			+"(146, 'F2 Sink 6', 'True', 2, 4, 2),"
			+"(151, 'F2 Reprocessor 1', 'True', 2, 5, 2),"
			+"(152, 'F2 Reprocessor 2', 'True', 2, 5, 2),"
			+"(153, 'F2 Reprocessor 3', 'False', 2, 5, 2),"
			+"(154, 'F2 Reprocessor 4', 'True', 2, 5, 2),"
			+"(155, 'F2 Reprocessor 5', 'True', 2, 5, 2),"
			+"(156, 'F2 Reprocessor 6', 'True', 2, 5, 2),"
			+"(171, 'F2 Waiting1', 'True', 2, 7, 2),"
			+"(172, 'F2 Waiting2', 'True', 2, 7, 2)," 
			+"(173, 'F2 Bioburden1', 'True',2,8,2),"
			+"(175, 'F2 Bioburden2', 'True',2,8,2),"
			+"(174, 'F2 CultureB', 'True', 2,9, 2),"
			+"(181, 'F2 CultureA', 'True', 2, 9, 2),"
			+"(201, 'F3 Administration', 'True', 3, 1,3),"
			+"(221, 'F3 Procedure Room 1', 'True', 3, 2,3),"
			+"(222, 'F3 Procedure Room 2', 'True', 3, 2,3),"
			+"(223, 'F3 Procedure Room 3', 'True', 3, 2,3),"
			+"(224, 'F3 Procedure Room 4', 'False', 3, 2,3),"
			+"(225, 'F3 Procedure Room 5', 'True', 3, 2,3),"
			+"(226, 'F3 Procedure Room 6', 'True', 3, 2,3),"
			+"(227, 'F3 Procedure Room 7', 'True', 3, 2,3),"
			+"(228, 'F3 Procedure Room 8', 'True', 3, 2,3),"
			+"(229, 'F3 Procedure Room 9', 'True', 3, 2,3),"
			+"(241, 'F3 Sink 1', 'True', 3, 4,3),"
			+"(242, 'F3 Sink 2', 'True', 3, 4,3),"
			+"(243, 'F3 Sink 3', 'False', 3, 4,3),"
			+"(244, 'F3 Sink 4', 'True', 3, 4,3),"
			+"(245, 'F3 Sink 5', 'True', 3, 4,3),"
			+"(246, 'F3 Sink 6', 'True', 3, 4,3),"
			+"(251, 'F3 Reprocessor 1', 'True', 3, 5,3),"
			+"(252, 'F3 Reprocessor 2', 'True', 3, 5,3),"
			+"(253, 'F3 Reprocessor 3', 'False', 3, 5,3),"
			+"(254, 'F3 Reprocessor 4', 'True', 3, 5,3),"
			+"(255, 'F3 Reprocessor 5', 'True', 3, 5,3),"
			+"(256, 'F3 Reprocessor 6', 'True', 3, 5,3),"
			+"(271, 'F3 Waiting1', 'True', 3, 7,3),"
			+"(272, 'F3 Waiting2', 'True', 3, 7,3)," 
			+"(273, 'F3 Bioburden1', 'True',3,8,3),"
			+"(275, 'F3 Bioburden2', 'True',3,8,3),"
			+"(274, 'F3 CultureB', 'True', 3,9,3),"
			+"(281, 'F3 CultureA', 'True', 3, 9, 3);"
			+"Insert INTO Location(LocationID_PK, Name, IsActive, FacilityID_FK, LocationTypeID_FK, AccessPointID_FK, StorageCabinetCount) values" 
			+"(31, 'F1 Storage Area A', 'True', 1, 3, 1, 4),"
			+"(32, 'F1 Storage Area B', 'True', 1, 3, 1, 1),"
			+"(33, 'F1 Storage Area C', 'False', 1, 3, 1, 2),"
			+"(34, 'F1 Storage Area D', 'True', 1, 3, 1, 4),"
			+"(35, 'F1 Culture Hold Cabinet', 'True', 1, 3, 1, 1),"
			+"(131, 'F2 Storage Area A', 'True', 2, 3, 2, 4),"
			+"(132, 'F2 Storage Area B', 'True', 2, 3, 2, 1),"
			+"(133, 'F2 Storage Area C', 'False', 2, 3, 2, 2),"
			+"(134, 'F2 Storage Area D', 'True', 2, 3, 2, 4),"
			+"(135, 'F2 Culture Hold Cabinet', 'True', 2, 3, 2, 1),"
			+"(231, 'F3 Storage Area A', 'True', 3, 3, 3, 4),"
			+"(232, 'F3 Storage Area B', 'True', 3, 3, 3, 1),"
			+"(233, 'F3 Storage Area C', 'False', 3, 3, 3, 2),"
			+"(234, 'F3 Storage Area D', 'True', 3, 3, 3, 4),"
			+"(235, 'F3 Culture Hold Cabinet', 'True', 3, 3, 3, 1);"
			+"set IDENTITY_Insert Location OFF;"
			+"set IDENTITY_Insert AERDetail ON;"
			+"Insert INTO AERDetail(AERDetailID_PK, Model, SerialNumber, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, LocationID_FK) values" 
			+"(1, 'OER-Pro', '2000001', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 51),"
			+"(2, 'OER-Pro', '2000002', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 52),"
			+"(3, 'OER-Pro', '2000003', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 53),"
			+"(4, 'OER-Pro', '2000004', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 54),"
			+"(5, 'OER-Pro', '2000005', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 55),"
			+"(6, 'OER-Pro', '2000006', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 56),"
			+"(11, 'OER-Pro', '3000001', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 151),"
			+"(12, 'OER-Pro', '3000002', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 152),"
			+"(13, 'OER-Pro', '3000003', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 153),"
			+"(14, 'OER-Pro', '3000004', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 154),"
			+"(15, 'OER-Pro', '3000005', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 155),"
			+"(16, 'OER-Pro', '3000006', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 156),"
			+"(21, 'OER-Pro', '4000001', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 251),"
			+"(22, 'OER-Pro', '4000002', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 252),"
			+"(23, 'OER-Pro', '4000003', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 253),"
			+"(24, 'OER-Pro', '4000004', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 254),"
			+"(25, 'OER-Pro', '4000005', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 255),"
			+"(26, 'OER-Pro', '4000006', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 256);"
			+"set IDENTITY_Insert AERDetail OFF;"
			+"set IDENTITY_Insert Scanner ON;"
			+"Insert INTO Scanner(ScannerID_PK, Name, ScannerID, IsActive, LocationID_FK) values" 
			+"(1, 'F1 Administration', '016311', 'True', 1)," 
			+"(21, 'F1 PR1', '024285', 'True', 21)," 
			+"(22, 'F1 PR2', '024284', 'True', 22),"  
			+"(23, 'F1 PR3', '016306', 'True', 23)," 
			+"(24, 'F1 PR4', '016317', 'False', 24)," 
			+"(25, 'F1 PR5', '017002', 'True', 25)," 
			+"(26, 'F1 PR6', '015308', 'True', 26),"  
			+"(27, 'F1 PR7', '016307', 'True', 27),"  
			+"(28, 'F1 PR8', '016328', 'True', 28)," 
			+"(29, 'F1 PR9', '016351', 'True', 29)," 
			+"(41, 'F1 Sink1', '024286', 'True', 41)," 
			+"(42, 'F1 Sink2', '015311', 'True', 42)," 
			+"(43, 'F1 Sink3', '018878', 'False', 43)," 
			+"(44, 'F1 Sink4', '016308', 'True', 44)," 
			+"(45, 'F1 Sink5', '017003', 'True', 45)," 
			+"(46, 'F1 Sink6', '015310', 'True', 46),"   
			+"(51, 'F1 Reprocessor1', '015314', 'True', 51)," 
			+"(52, 'F1 Reprocessor2', '015313', 'True', 52)," 
			+"(53, 'F1 Reprocessor3', '018879', 'False', 53)," 
			+"(54, 'F1 Reprocessor4', '015315', 'True', 54)," 
			+"(55, 'F1 Reprocessor5', '016320', 'True', 55)," 
			+"(56, 'F1 Reprocessor6', '016321', 'True', 56)," 
			+"(71, 'F1 Waiting1', '015312', 'True', 71)," 
			+"(72, 'F1 Waiting2', '003041', 'True', 72),"  
			+"(73, 'F1 Bioburden1','017010', 'True', 73),"
			+"(74, 'F1 CultureB','017020','True', 74),"
			+"(75, 'F1 Bioburden2','017110', 'True', 75),"
			+"(31, 'F1 StorageA', '018880', 'True', 31),"   
			+"(35, 'F1 Culture Hold Cabinet', '019001', 'True', 35),"
			+"(32, 'F1 StorageB', '018877', 'True', 32)," 
			+"(33, 'F1 StorageC', '017001', 'False', 33)," 
			+"(34, 'F1 StorageD', '013509', 'True', 34),"  
			+"(81, 'F1 CultureA', '019313', 'True', 81)," 
			+"(11, 'F2 Administration', '116311', 'True', 101)," 
			+"(121, 'F2 PR1', '124285', 'True', 121)," 
			+"(122, 'F2 PR2', '124284', 'True', 122),"  
			+"(123, 'F2 PR3', '116306', 'True', 123)," 
			+"(124, 'F2 PR4', '116317', 'False', 124)," 
			+"(125, 'F2 PR5', '117002', 'True', 125)," 
			+"(126, 'F2 PR6', '115308', 'True', 126),"  
			+"(127, 'F2 PR7', '116307', 'True', 127),"  
			+"(128, 'F2 PR8', '116328', 'True', 128)," 
			+"(129, 'F2 PR9', '116351', 'True', 129)," 
			+"(141, 'F2 Sink1', '124286', 'True', 141)," 
			+"(142, 'F2 Sink2', '115311', 'True', 142)," 
			+"(143, 'F2 Sink3', '118878', 'True', 143)," 
			+"(144, 'F2 Sink4', '116308', 'True', 144)," 
			+"(145, 'F2 Sink5', '117003', 'True', 145)," 
			+"(146, 'F2 Sink6', '115310', 'True', 146),"   
			+"(151, 'F2 Reprocessor1', '115314', 'True', 151)," 
			+"(152, 'F2 Reprocessor2', '115313', 'True', 152)," 
			+"(153, 'F2 Reprocessor3', '118879', 'False', 153)," 
			+"(154, 'F2 Reprocessor4', '115315', 'True', 154)," 
			+"(155, 'F2 Reprocessor5', '116320', 'True', 155)," 
			+"(156, 'F2 Reprocessor6', '116321', 'True', 156)," 
			+"(171, 'F2 Waiting1', '115312', 'True', 171)," 
			+"(172, 'F2 Waiting2', '103041', 'True', 172),"  
			+"(173, 'F2 Bioburden1','117010', 'True', 173),"
			+"(175, 'F2 Bioburden2','117110', 'True', 175),"
			+"(174, 'F2 CultureB','117020','True', 174),"
			+"(131, 'F2 StorageA', '118880', 'True', 131),"   
			+"(135, 'F2 Culture Hold Cabinet', '119001', 'True', 135),"
			+"(132, 'F2 StorageB', '118877', 'True', 132)," 
			+"(133, 'F2 StorageC', '117001', 'False', 133)," 
			+"(134, 'F2 StorageD', '113509', 'True', 134),"  
			+"(181, 'F2 CultureA', '119313', 'True', 181)," 
			+"(201, 'F3 Administration', '216311', 'True', 201)," 
			+"(221, 'F3 PR1', '224285', 'True', 221)," 
			+"(222, 'F3 PR2', '224284', 'True', 222),"  
			+"(223, 'F3 PR3', '216306', 'True', 223)," 
			+"(224, 'F3 PR4', '216317', 'False', 224)," 
			+"(225, 'F3 PR5', '217002', 'True', 225)," 
			+"(226, 'F3 PR6', '215308', 'True', 226),"  
			+"(227, 'F3 PR7', '216307', 'True', 227),"  
			+"(228, 'F3 PR8', '216328', 'True', 228)," 
			+"(229, 'F3 PR9', '216351', 'True', 229)," 
			+"(241, 'F3 Sink1', '224286', 'True', 241)," 
			+"(242, 'F3 Sink2', '215311', 'True', 242)," 
			+"(243, 'F3 Sink3', '218878', 'True', 243)," 
			+"(244, 'F3 Sink4', '216308', 'True', 244)," 
			+"(245, 'F3 Sink5', '217003', 'True', 245)," 
			+"(246, 'F3 Sink6', '215310', 'True', 246),"   
			+"(251, 'F3 Reprocessor1', '215314', 'True', 251)," 
			+"(252, 'F3 Reprocessor2', '215313', 'True', 252)," 
			+"(253, 'F3 Reprocessor3', '218879', 'False', 253)," 
			+"(254, 'F3 Reprocessor4', '215315', 'True', 254)," 
			+"(255, 'F3 Reprocessor5', '216320', 'True', 255)," 
			+"(256, 'F3 Reprocessor6', '216321', 'True', 256)," 
			+"(271, 'F3 Waiting1', '215312', 'True', 271)," 
			+"(272, 'F3 Waiting2', '203041', 'True', 272),"  
			+"(273, 'F3 Bioburden1','217010', 'True', 273),"
			+"(275, 'F3 Bioburden2','217110', 'True', 275),"
			+"(274, 'F3 CultureB','217020','True', 274),"
			+"(231, 'F3 StorageA', '218880', 'True', 231),"   
			+"(235, 'F3 Culture Hold Cabinet', '219001', 'True', 235),"
			+"(232, 'F3 StorageB', '218877', 'True', 232)," 
			+"(233, 'F3 StorageC', '217001', 'False', 233)," 
			+"(234, 'F3 StorageD', '213509', 'True', 234),"  
			+"(281, 'F3 CultureA', '219313', 'True', 281);"
			+"set IDENTITY_Insert Scanner OFF;";
			String stmt7="set IDENTITY_Insert Scope ON;"
			+"Insert INTO Scope (ScopeID_PK, RFUID, Name, SerialNumber, FacilityID_FK, ScopeTypeID_FK, IsActive, Comments, IsShipped, CompletedCycleCount) values" 
			+"(1, 'e0040150409251e7', 'F1 Scope1', '1122334', 1, 183, 1, '', 0, 0)," 
			+"(2, 'e004015040926c20', 'F1 Scope2', '2233445', 1, 184, 1, '', 0, 0)," 
			+"(3, 'e004015040926294', 'F1 Scope3', '3344556', 1, 181, 1, '', 0, 0)," 
			+"(4, 'e00401504092a32e', 'F1 Scope4', '4455667', 1, 96, 1, '', 0, 0),"    
			+"(5, 'e004015040926037', 'F1 Scope5', '5566778', 1, 97, 1, '', 0, 0)," 
			+"(6, 'e004015040924a80', 'F1 Scope6', '6677889', 1, 96, 1, '', 0, 0)," 
			+"(7, 'e00401504092a30f', 'F1 Scope7', '7654231', 1, 155, 1, '', 0, 0)," 
			+"(8, 'e00401504092737e', 'F1 Scope8', '6543216', 1, 170, 1, '', 0, 0)," 
			+"(9, 'e004015040926fe2', 'F1 Scope9', '9876432', 1, 14, 1, '', 0, 0)," 
			+"(10, 'e00401504092af6e', 'F1 Scope10', '7654321', 1, 155, 1, '', 0, 5)," 
			+"(11, 'e00401504092482b', 'F1 Scope11', '9988776', 1, 184, 1, '', 0, 2)," 
			+"(12, 'e00401001891a55e', 'F1 Scope12', '2808645', 1, 181, 1, '', 0, 20)," 
			+"(13, 'e004010002faf288', 'F1 Scope13', '2600842', 1, 181, 1, '', 0, 10),"
			+"(14, 'e004010010ab6695', 'F1 Scope14', '2806734', 1, 181, 1, '', 0, 130),"
			+"(15, 'e00401005f093832', 'F1 Scope15', '2200174', 1, 183, 1, '', 0, 25),"
			+"(16, 'e004010002fb0f38', 'F1 Scope16', '2601311', 1, 181, 1, '', 0, 11),"
			+"(17, 'e004010002fb43fb', 'F1 Scope17', '2600163', 1, 181, 1, '', 0, 0),"
			+"(18, 'e00401504092483f', 'F1 Scope18', '2211009', 1, 86, 1, '', 0, 50)," 
			+"(19, 'e004010010ab6918', 'F1 Scope19', '2809094', 1, 196, 1, '', 0, 55),"
			+"(20, 'e003020040bb6c18', 'F1 Scope20', '0099887', 1, 87, 1, '', 0, 12)," 
			+"(21, 'e003001452ca51b1', 'F1 Scope21', '2112233', 1, 87, 1, '', 0, 13),"
			+"(22, 'e003001780332ab2', 'F1 Scope22', '2223344', 1, 86, 1, '', 0, 22)," 
			+"(23, 'e0030013762ce232', 'F1 Scope23', '2334455', 1, 86, 1, '', 0, 11)," 
			+"(24, 'e004010002faf238', 'F1 Scope24', '2600319', 1, 94, 1, '', 0, 0)," 
			+"(25, 'e004010002fafd0e', 'F1 Scope25', '2602572', 1, 196, 1, '', 0, 0),"
			+"(26, 'e004010002fb085e', 'F1 Scope26', '2601132', 1, 181, 1, '', 0, 19),"
			+"(27, 'e004010002fafaff', 'F1 Scope27', '2600266', 1, 181, 1, '', 0, 6),"
			+"(28, 'e004010010ab5baf', 'F1 Scope28', '2707317', 1, 196, 1, '', 0, 9),"
			+"(29, 'e004015040926e8b', 'F1 Scope29', '5556667', 1, 89, 1, '', 0, 14),"
			+"(30, 'e00301703198a8c2', 'F1 Scope30', '6667778', 1, 89, 1, '', 0, 3),"
			+"(31, 'e00301890b2d245f', 'F1 Scope31', '7778889', 1, 155, 1, '', 0, 323),"
			+"(32, 'e00301701100ba21', 'F1 Scope32', '8889990', 1, 155, 1, '', 0, 23),"
			+"(33, 'e00401005f092678', 'F1 Scope33', '2200687', 1, 184, 1, '', 0, 23)," 
			+"(34, 'e004010002fb4b21', 'F1 Scope34', '2500850', 1, 196, 1, '', 0, 23)," 
			+"(35, 'e004010002fb18be', 'F1 Scope35', '2600714', 1, 181, 1, '', 0, 23)," 
			+"(36, 'e00300129643dc16', 'F1 Scope36', '2700854', 1, 87, 1, '', 0, 13),"
			+"(37, 'e003003013561ab2', 'F1 Scope37', '8165413', 1, 86, 1, '', 0, 22)," 
			+"(38, 'e004010003fb18be', 'F1 Scope38', '2600715', 1, 181, 1, '', 0, 0)," 
			+"(39, 'e004010004fb18be', 'F1 Scope39', '2600716', 1, 181, 1, '', 0, 0)," 
			+"(40, 'e004010005fb18be', 'F1 Scope40', '2600717', 1, 181, 1, '', 0, 0)," 
			+"(41, 'e004010006fb18be', 'F1 Scope41', '2600718', 1, 181, 1, '', 0, 0)," 
			+"(42, 'e004010007fb18be', 'F1 Scope42', '2600719', 1, 181, 1, '', 0, 0)," 
			+"(43, 'e004010008fb18be', 'F1 Scope43', '2600720', 1, 181, 1, '', 0, 0)," 
			+"(44, 'e004010009fb18be', 'F1 Scope44', '2600721', 1, 181, 1, '', 0, 0)," 
			+"(45, 'e004010012fb18be', 'F1 Scope45', '2600814', 1, 181, 1, '', 0, 0)," 
			+"(46, 'e004010013fb18be', 'F1 Scope46', '2600815', 1, 181, 1, '', 0, 0)," 
			+"(47, 'e004010014fb18be', 'F1 Scope47', '2600816', 1, 181, 1, '', 0, 0)," 
			+"(48, 'e004010033fb18be', 'F1 Scope48', '2600825', 1, 181, 1, '', 0, 0)," 
			+"(49, 'e004010034fb18be', 'F1 Scope49', '2600826', 1, 181, 1, '', 0, 0)," 
			+"(50, 'e004010015fb18be', 'F1 Scope50', '2600817', 1, 181, 1, '', 0, 0)," 
			+"(51, 'e004010016fb18be', 'F1 Scope51', '2600818', 1, 181, 1, '', 0, 0)," 
			+"(52, 'e004010017fb18be', 'F1 Scope52', '2600819', 1, 181, 1, '', 0, 0)," 
			+"(53, 'e004010018fb18be', 'F1 Scope53', '2600820', 1, 181, 1, '', 0, 0)," 
			+"(54, 'e004010019fb18be', 'F1 Scope54', '2600821', 1, 181, 1, '', 0, 0)," 
			+"(55, 'e004010022fb18be', 'F1 Scope55', '2600834', 1, 181, 1, '', 0, 0)," 
			+"(56, 'e004000023fb18be', 'F1 Scope56', '3600835', 1, 181, 1, '', 0, 0)," 
			+"(57, 'e004020014fb18be', 'F1 Scope57', '3600816', 1, 181, 1, '', 0, 0)," 
			+"(58, 'e004020033fb18be', 'F1 Scope58', '3600825', 1, 181, 1, '', 0, 0)," 
			+"(59, 'e004020034fb18be', 'F1 Scope59', '3600826', 1, 181, 1, '', 0, 0)," 
			+"(60, 'e004020015fb18be', 'F1 Scope60', '3600817', 1, 181, 1, '', 0, 0)," 
			+"(61, 'e004020016fb18be', 'F1 Scope61', '3600818', 1, 181, 1, '', 0, 0)," 
			+"(62, 'e004020017fb18be', 'F1 Scope62', '3600819', 1, 181, 1, '', 0, 0)," 
			+"(63, 'e004020018fb18be', 'F1 Scope63', '3600820', 1, 181, 1, '', 0, 0)," 
			+"(64, 'e004020019fb18be', 'F1 Scope64', '3600821', 1, 181, 1, '', 0, 0)," 
			+"(65, 'e004020022fb18be', 'F1 Scope65', '3600834', 1, 181, 1, '', 0, 0)," 
			+"(66, 'e004030023fb18be', 'F1 Scope66', '4600835', 1, 181, 1, '', 0, 0)," 
			+"(67, 'e004030014fb18be', 'F1 Scope67', '4600816', 1, 181, 1, '', 0, 0)," 
			+"(68, 'e004030033fb18be', 'F1 Scope68', '4600825', 1, 181, 1, '', 0, 0)," 
			+"(69, 'e004030034fb18be', 'F1 Scope69', '4600826', 1, 181, 1, '', 0, 0)," 
			+"(70, 'e004030015fb18be', 'F1 Scope70', '4600817', 1, 181, 1, '', 0, 0)," 
			+"(71, 'e004030016fb18be', 'F1 Scope71', '4600818', 1, 181, 1, '', 0, 0)," 
			+"(72, 'e004030017fb18be', 'F1 Scope72', '4600819', 1, 181, 1, '', 0, 0)," 
			+"(73, 'e004030018fb18be', 'F1 Scope73', '4600820', 1, 181, 1, '', 0, 0)," 
			+"(74, 'e004030019fb18be', 'F1 Scope74', '4600821', 1, 181, 1, '', 0, 0)," 
			+"(75, 'e004030022fb18be', 'F1 Scope75', '4600834', 1, 181, 1, '', 0, 0)," 
			+"(76, 'e004040023fb18be', 'F1 Scope76', '5600835', 1, 181, 1, '', 0, 0)," 
			+"(77, 'e004040014fb18be', 'F1 Scope77', '5600816', 1, 181, 1, '', 0, 0)," 
			+"(78, 'e004040033fb18be', 'F1 Scope78', '5600825', 1, 181, 1, '', 0, 0)," 
			+"(79, 'e004040034fb18be', 'F1 Scope79', '5600826', 1, 181, 1, '', 0, 0)," 
			+"(80, 'e004040015fb18be', 'F1 Scope80', '5600817', 1, 181, 1, '', 0, 0)," 
			+"(81, 'e004040016fb18be', 'F1 Scope81', '5600818', 1, 181, 1, '', 0, 0)," 
			+"(82, 'e004040017fb18be', 'F1 Scope82', '5600819', 1, 181, 1, '', 0, 0)," 
			+"(83, 'e004040018fb18be', 'F1 Scope83', '5600820', 1, 181, 1, '', 0, 0)," 
			+"(84, 'e004040019fb18be', 'F1 Scope84', '5600821', 1, 181, 1, '', 0, 0)," 
			+"(85, 'e004040022fb18be', 'F1 Scope85', '5600834', 1, 181, 1, '', 0, 0)," 
			+"(86, 'e004050023fb18be', 'F1 Scope86', '6600835', 1, 181, 1, '', 0, 0)," 
			+"(87, 'e004050014fb18be', 'F1 Scope87', '6600816', 1, 181, 1, '', 0, 0)," 
			+"(88, 'e004050033fb18be', 'F1 Scope88', '6600825', 1, 181, 1, '', 0, 0)," 
			+"(89, 'e004050034fb18be', 'F1 Scope89', '6600826', 1, 181, 1, '', 0, 0)," 
			+"(90, 'e004050015fb18be', 'F1 Scope90', '6600817', 1, 181, 1, '', 0, 0)," 
			+"(91, 'e0030030135612ab', 'F1 Scope91', '8165412', 1, 86, 1, '', 0, 22),"
			+"(92, 'e004060017fb18be', 'F1 Scope92', '5500819', 1, 181, 1, '', 0, 0)," 
			+"(93, 'e004060018fb18be', 'F1 Scope93', '5500820', 1, 181, 1, '', 0, 0)," 
			+"(94, 'e004060019fb18be', 'F1 Scope94', '5500821', 1, 181, 1, '', 0, 0)," 
			+"(95, 'e004060022fb18be', 'F1 Scope95', '5500834', 1, 181, 1, '', 0, 0)," 
			+"(96, 'e004060023fb18be', 'F1 Scope96', '6500835', 1, 181, 1, '', 0, 0)," 
			+"(97, 'e004060014fb18be', 'F1 Scope97', '6500816', 1, 181, 1, '', 0, 0)," 
			+"(101, 'e0030150409251e7', 'F1 Scope101', '9122334', 1, 183, 1, '', 0, 0)," 
			+"(102, 'e003015040926c20', 'F1 Scope102', '9233445', 1, 184, 1, '', 0, 0)," 
			+"(103, 'e003015040926294', 'F1 Scope103', '9344556', 1, 181, 1, '', 0, 0)," 
			+"(104, 'e00301504092a32e', 'F1 Scope104', '9455667', 1, 96, 1, '', 0, 0),"    
			+"(105, 'e003015040926037', 'F1 Scope105', '9566778', 1, 97, 1, '', 0, 0)," 
			+"(106, 'e003015040924a80', 'F1 Scope106', '9677889', 1, 96, 1, '', 0, 0)," 
			+"(107, 'e00301504092a30f', 'F1 Scope107', '9654231', 1, 155, 1, '', 0, 0)," 
			+"(108, 'e00301504092737e', 'F1 Scope108', '9543216', 1, 170, 1, '', 0, 0)," 
			+"(109, 'e003015040926fe2', 'F1 Scope109', '8876432', 1, 14, 1, '', 0, 0)," 
			+"(110, 'e00301504092af6e', 'F1 Scope110', '9654321', 1, 155, 1, '', 0, 0)," 
			+"(111, 'e00301504092482b', 'F1 Scope111', '8988776', 1, 184, 1, '', 0, 0)," 
			+"(112, 'e00301001891a55e', 'F1 Scope112', '9808645', 1, 181, 1, '', 0, 0)," 
			+"(158, 'e003020033fb18be', 'F1 Scope158', '9600825', 1, 181, 1, '', 0, 0)," 
			+"(159, 'e003020034fb18be', 'F1 Scope159', '9600826', 1, 181, 1, '', 0, 0)," 
			+"(160, 'e003020015fb18be', 'F1 Scope160', '9600817', 1, 181, 1, '', 0, 0)," 
			+"(161, 'e003020016fb18be', 'F1 Scope161', '9600818', 1, 181, 1, '', 0, 0)," 
			+"(162, 'e003020017fb18be', 'F1 Scope162', '9600819', 1, 181, 1, '', 0, 0)," 
			+"(163, 'e003020018fb18be', 'F1 Scope163', '9600820', 1, 181, 1, '', 0, 0)," 
			+"(164, 'e003020019fb18be', 'F1 Scope164', '9600821', 1, 181, 1, '', 0, 0),"
			+"(201, 'e0040250409251e7', 'F2 Scope1', '1122335', 2, 183, 1, '', 0, 0)," 
			+"(202, 'e004025040926c20', 'F2 Scope2', '2233446', 2, 184, 1, '', 0, 0)," 
			+"(203, 'e004025040926294', 'F2 Scope3', '3344557', 2, 181, 1, '', 0, 0)," 
			+"(204, 'e00402504092a32e', 'F2 Scope4', '4455668', 2, 96, 1, '', 0, 0),"    
			+"(205, 'e004025040926037', 'F2 Scope5', '5566779', 2, 97, 1, '', 0, 0)," 
			+"(206, 'e004025040924a80', 'F2 Scope6', '6677880', 2, 96, 1, '', 0, 0)," 
			+"(207, 'e00402504092a30f', 'F2 Scope7', '7654232', 2, 155, 1, '', 0, 0)," 
			+"(208, 'e00402504092737e', 'F2 Scope8', '6543217', 2, 170, 1, '', 0, 0)," 
			+"(209, 'e004025040926fe2', 'F2 Scope9', '9876433', 2, 14, 1, '', 0, 0)," 
			+"(210, 'e00402504092af6e', 'F2 Scope10', '7654322', 2, 155, 1, '', 0, 5)," 			
			+"(301, 'e0040350409251e7', 'F3 Scope1', '1122336', 3, 183, 1, '', 0, 0)," 
			+"(302, 'e004035040926c20', 'F3 Scope2', '2233447', 3, 184, 1, '', 0, 0)," 
			+"(303, 'e004035040926294', 'F3 Scope3', '3344558', 3, 181, 1, '', 0, 0)," 
			+"(304, 'e00403504092a32e', 'F3 Scope4', '4455669', 3, 96, 1, '', 0, 0),"    
			+"(305, 'e004035040926037', 'F3 Scope5', '5566770', 3, 97, 1, '', 0, 0)," 
			+"(306, 'e004035040924a80', 'F3 Scope6', '6677881', 3, 96, 1, '', 0, 0)," 
			+"(307, 'e00403504092a30f', 'F3 Scope7', '7654233', 3, 155, 1, '', 0, 0)," 
			+"(308, 'e00403504092737e', 'F3 Scope8', '6543218', 3, 170, 1, '', 0, 0)," 
			+"(309, 'e004035040926fe2', 'F3 Scope9', '9876434', 3, 14, 1, '', 0, 0)," 
			+"(310, 'e00403504092af6e', 'F3 Scope10', '7654323', 3, 155, 1, '', 0, 5),"
			+"(211, 'e00402504092482b', 'F2 Scope11', '9988777', 2, 184, 1, '', 0, 2),"
			+"(212, 'e00402001891a55e', 'F2 Scope12', '2808646', 2, 181, 1, '', 0, 20),"
			+"(213, 'e004020002faf288', 'F2 Scope13', '2600843', 2, 181, 1, '', 0, 10),"
			+"(214, 'e004020010ab6695', 'F2 Scope14', '2806735', 2, 181, 1, '', 0, 130),"
			+"(216, 'e004020002fb0f38', 'F2 Scope16', '2601312', 2, 181, 1, '', 0, 11),"
			+"(224, 'e004020002faf239', 'F2 Scope24', '2600320', 2, 94, 1, '', 0, 0), "
			+"(225, 'e004020002fafd0e', 'F2 Scope25', '2602573', 2, 196, 1, '', 0, 0),"
			+"(229, 'e004025040926e8b', 'F2 Scope29', '5556668', 2, 89, 1, '', 0, 14),"
			+"(231, 'e00302890b2d245f', 'F2 Scope31', '7778888', 2, 155, 1, '', 0, 323),"
			+"(233, 'e00402005f092678', 'F2 Scope33', '2200688', 2, 184, 1, '', 0, 23),"
			+"(234, 'e004010202fb4b21', 'F2 Scope34', '2520850', 2, 196, 1, '', 0, 23)," 
			+"(235, 'e004020002fb18be', 'F2 Scope35', '2620716', 2, 181, 1, '', 0, 23),"
			+"(240, 'e004020005fb18be', 'F2 Scope40', '2620718', 2, 181, 1, '', 0, 0),"
			+"(241, 'e004020006fb18be', 'F2 Scope41', '2620719', 2, 181, 1, '', 0, 0),"
			+"(311, 'e00403504092482b', 'F3 Scope11', '9988778', 3, 184, 1, '', 0, 2),"
			+"(312, 'e00403001891a55e', 'F3 Scope12', '2808647', 3, 181, 1, '', 0, 20),"
			+"(313, 'e004030002faf288', 'F3 Scope13', '2630844', 3, 181, 1, '', 0, 10),"
			+"(314, 'e004030010ab6695', 'F3 Scope14', '2806736', 3, 181, 1, '', 0, 130),"
			+"(316, 'e004030002fb0f38', 'F3 Scope16', '2631313', 3, 181, 1, '', 0, 11),"
			+"(324, 'e004030002faf239', 'F3 Scope24', '2630321', 3, 94, 1, '', 0, 0)," 
			+"(325, 'e004030002fafd0e', 'F3 Scope25', '2632574', 3, 196, 1, '', 0, 0),"
			+"(329, 'e004035040926e8b', 'F3 Scope29', '5556669', 3, 89, 1, '', 0, 14),"
			+"(331, 'e00303890b2d245f', 'F3 Scope31', '7778891', 3, 155, 1, '', 0, 323),"
			+"(333, 'e00403005f092678', 'F3 Scope33', '2200689', 3, 184, 1, '', 0, 23),"
			+"(334, 'e004010302fb4b21', 'F3 Scope34', '2530850', 3, 196, 1, '', 0, 23)," 
			+"(335, 'e004030062fb18be', 'F3 Scope35', '2630717', 3, 181, 1, '', 0, 23),"
			+"(340, 'e004030005fb18bf', 'F3 Scope40', '2630719', 3, 181, 1, '', 0, 0),"
			+"(341, 'e004030006fb18be', 'F3 Scope41', '2630720', 3, 181, 1, '', 0, 0);"
			+"set IDENTITY_Insert Scope OFF;"
			+"set IDENTITY_Insert Staff ON;"
			+"Insert INTO staff(StaffID_PK,TitleID_FK,FirstName,LastName,StaffID,StaffTypeID_FK,IsActive) values"
			+"(1,4,'Physician1','Physician1','MD01',1,'True'),"
			+"(2,4,'Physician2','Physician2','MD02',1,'True'),"
			+"(3,4,'Physician3','Physician3','MD03',1,'True'),"
			+"(4,4,'Physician4','Physician4','MD04',1,'True'),"
			+"(5,4,'Physician5','Physician5','MD05',1,'True'),"
			+"(6,4,'Physician6','Physician6','MD06',1,'True'),"
			+"(7,4,'Physician7','Physician7','MD07',1,'True'),"
			+"(8,4,'Physician8','Physician8','MD08',1,'True'),"
			+"(9,4,'Physician9','Physician9','MD09',1,'True'),"
			+"(10,4,'Physician10','Physician10','MD10',1,'True'),"
			+"(11,4,'Physician11','Physician11','MD11',1,'True'),"
			+"(12,4,'Physician12','Physician12','MD12',1,'True'),"
			+"(13,4,'Physician13','Physician13','MD13',1,'True'),"
			+"(14,4,'Physician14','Physician14','MD14',1,'True'),"
			+"(15,4,'Physician15','Physician15','MD15',1,'True'),"
			+"(16,4,'Physician16','Physician16','MD16',1,'True'),"
			+"(17,4,'Physician17','Physician17','MD17',1,'True'),"
			+"(18,4,'Physician18','Physician18','MD18',1,'True'),"
			+"(19,4,'Physician19','Physician19','MD19',1,'False'),"
			+"(20,4,'Physician20','Physician20','MD20',1,'False'),"
			+"(41,3,'Nurse1','Nurse1','RN01',2,'True'),"
			+"(42,1,'Nurse2','Nurse2','RN02',2,'True'),"
			+"(43,3,'Nurse3','Nurse3','RN03',2,'True'),"
			+"(44,1,'Nurse4','Nurse4','RN04',2,'True'),"
			+"(45,3,'Nurse5','Nurse5','RN05',2,'True'),"
			+"(46,1,'Nurse6','Nurse6','RN06',2,'True'),"
			+"(47,3,'Nurse7','Nurse7','RN07',2,'True'),"
			+"(48,1,'Nurse8','Nurse8','RN08',2,'True'),"
			+"(49,3,'Nurse9','Nurse9','RN09',2,'True'),"
			+"(50,1,'Nurse10','Nurse10','RN10',2,'True'),"
			+"(51,3,'Nurse11','Nurse11','RN11',2,'True'),"
			+"(52,1,'Nurse12','Nurse12','RN12',2,'True'),"
			+"(53,3,'Nurse13','Nurse13','RN13',2,'True'),"
			+"(54,1,'Nurse14','Nurse14','RN14',2,'True'),"
			+"(55,3,'Nurse15','Nurse15','RN15',2,'True'),"
			+"(56,1,'Nurse16','Nurse16','RN16',2,'True'),"
			+"(57,3,'Nurse17','Nurse17','RN17',2,'True'),"
			+"(58,1,'Nurse18','Nurse18','RN18',2,'True'),"
			+"(59,3,'Nurse19','Nurse19','RN19',2,'False'),"
			+"(60,1,'Nurse20','Nurse20','RN20',2,'False'),"
			+"(61,2,'Admin1','Admin1','AD127943',4,'True');"
			+"Insert INTO staff(StaffID_PK,TitleID_FK,FirstName,LastName,StaffID,StaffTypeID_FK,IsActive,BadgeID) values"
			+"(21,3,'Tech1','Tech1','T01',5,'True', '813F6F82D25204'),"
			+"(22,1,'Tech2','Tech2','T02',5,'True', '813F6F82B95204'),"
			+"(23,3,'Tech3','Tech3','T03',5,'True', '813F6F82975204'),"
			+"(24,1,'Tech4','Tech4','T04',5,'True', '813F6F82B35204'),"
			+"(25,3,'Tech5','Tech5','T05',5,'True', '813F6F82925204'),"
			+"(26,1,'Tech6','Tech6','T06',5,'True', '813F6F828D5204'),"
			+"(27,3,'Tech7','Tech7','T07',5,'True', '813F6F82875104'),"
			+"(28,1,'Tech8','Tech8','T08',5,'True', '813F6F82835204'),"
			+"(29,3,'Tech9','Tech9','T09',5,'True', '813F6F827E5204'),"
			+"(30,1,'Tech10','Tech10','T10',5,'True','813F6F82AE5204'),"
			+"(31,3,'Tech11','Tech11','T11',5,'True', '813F6F82CD5204'),"
			+"(32,1,'Tech12','Tech12','T12',5,'True', '813F6F82A95204'),"
			+"(33,3,'Tech13','Tech13','T13',5,'True', '813F6F82C85204'),"
			+"(34,1,'Tech14','Tech14','T14',5,'True', '813F6F82A55304'),"
			+"(35,3,'Tech15','Tech15','T15',5,'True', '813F6F82C35204'),"
			+"(36,1,'Tech16','Tech16','T16',5,'True', '813F6F82A15204'),"
			+"(37,3,'Tech17','Tech17','T17',5,'True', '813F6F82BE5204'),"
			+"(38,1,'Tech18','Tech18','T18',5,'True', '813F6F829C5204'),"
			+"(39,3,'Tech19','Tech19','T19',5,'False', '802908e2468704'),"
			+"(40,1,'Tech20','Tech20','T20',5,'False','');"
			+"set IDENTITY_Insert Staff OFF;"
			+"set IDENTITY_Insert Barcode ON;"
			+"Insert into Barcode(BarcodeID_PK, Name, BarcodeTypeID_FK, IsActive) values"
			+"(14, 'Olympus Repair', 1, 1),"
			+"(15, 'Repair shop 1', 1, 1),"
			+"(16, 'Facility 4', 1, 1),"
			+"(17, 'Blue', 4, 1),"
			+"(18, 'Red', 4, 1),"
			+"(20, 'MRC Fail', 3, 1)," 
			+"(21, 'Received Loaner Scope', 3, 1),"
			+"(23,'Custom Reason1', 3, 1),"
			+"(24,'Custom Reason2', 3, 1),"
			+"(25,'Custom Reason3', 3, 1),"
			+"(26, 'Repair shop 2', 1, 1);"
			+"set IDENTITY_Insert Barcode OFF;";
			try{
	    		conn= DriverManager.getConnection(url, user, pass);		
	    		Statement update1 = conn.createStatement();
	    		System.out.println("stmt0="+stmt0);
	    		update1.executeUpdate(stmt0);
	    		System.out.println("stmt1="+stmt1);
	    		update1.executeUpdate(stmt1);
	    		System.out.println("stmt2="+stmt2);
	    		update1.executeUpdate(stmt2);
	    		System.out.println("stmt3="+stmt3);
	    		update1.executeUpdate(stmt3);
	    		System.out.println("stmt4="+stmt4);
	    		update1.executeUpdate(stmt4);
	    		System.out.println("stmt5="+stmt5);
	    		update1.executeUpdate(stmt5);
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

	public static void insertKEMasterData(String url, String user, String pass ) throws SQLException {
		Connection conn= null;
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
		+"select 183,'GIF-H190','2',1,'Olympus Code',108,'nicole','nicole',1 from dual union all "
		+"select 184,'GIF-HQ190','2',1,'Olympus Code',109,'nicole','nicole',1 from dual union all "
		+"select 200,'CF-H190L','2',1,'Olympus Code',110,'nicole','nicole',1 from dual union all "
		+"select 201,'CF-HQ190L','2',1,'Olympus Code',111,'nicole','nicole',1 from dual union all "
		+"select 155,'TJF-Q180V','1.5',1,'Olympus Code',154,'nicole','nicole',1 from dual union all "
		+"select 170,'GIF-1TH190','1',1,'Olympus Code',155,'nicole','nicole',1 from dual union all "
		+"select 202,'BF-1TQ180','1',1,'Olympus Code',156,'nicole','nicole',1 from dual union all "
		+"select 203,'CF-30L','1.2',1,'Olympus Code',157,'nicole','nicole',1 from dual union all "
		+"select 204,'CF-30M','1',1,'Olympus Code',158,'nicole','nicole',1 from dual union all "
		+"select 205,'CF-40L','1',1,'Olympus Code',159,'nicole','nicole',1 from dual; "
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
		String stmt3="insert into M_Scope(SCOPE_KEY,SERIAL_NO,SCOPE_NAME,ADMIN_REG_FLG,SCOPE_MODEL_KEY,PURCHASE_DATE,PURCHASE_COST,NOTE,SCOPE_STATUS_KEY,EXTERNAL_CODE,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) "
		+"select 101,'1122334','Scope1',1,183,NULL,NULL,NULL,1,NULL,1,0,'101',1,'nicole','nicole' from dual union all "
		+"select 102,'2233445','Scope2',1,184,NULL,NULL,NULL,1,NULL,1,0,'102',2,'nicole','nicole' from dual union all "
		+"select 103,'3344556','Scope3',1,94,NULL,NULL,NULL,1,NULL,1,0,'103',3,'nicole','nicole' from dual union all "
		+"select 104,'4455667','Scope4',1,200,NULL,NULL,NULL,1,NULL,1,0,'104',4,'nicole','nicole' from dual union all "
		+"select 105,'5566778','Scope5',1,201,NULL,NULL,NULL,1,NULL,1,0,'105',5,'nicole','nicole' from dual union all "
		+"select 106,'6677889','Scope6',1,200,NULL,NULL,NULL,1,NULL,1,0,'106',6,'nicole','nicole' from dual union all "
		+"select 107,'7654231','Scope7',1,155,NULL,NULL,NULL,1,NULL,1,0,'107',7,'nicole','nicole' from dual union all "
		+"select 108,'6543216','Scope8',1,170,NULL,NULL,NULL,1,NULL,1,0,'108',8,'nicole','nicole' from dual union all "
		+"select 109,'9876432','Scope9',1,202,NULL,NULL,NULL,1,NULL,1,0,'109',9,'nicole','nicole' from dual union all "
		+"select 110,'7654321','Scope10',1,155,NULL,NULL,NULL,1,NULL,1,0,'110',10,'nicole','nicole' from dual union all "
		+"select 111,'9988776','Scope11',1,184,NULL,NULL,NULL,1,NULL,1,0,'111',11,'nicole','nicole' from dual union all "
		+"select 112,'2808645','Scope12',1,94,NULL,NULL,NULL,1,NULL,1,0,'112',12,'nicole','nicole' from dual union all "
		+"select 113,'2600842','Scope13',1,94,NULL,NULL,NULL,1,NULL,1,0,'113',13,'nicole','nicole' from dual union all "
		+"select 114,'2806734','Scope14',1,94,NULL,NULL,NULL,1,NULL,1,0,'114',14,'nicole','nicole' from dual union all "
		+"select 115,'2200174','Scope15',1,183,NULL,NULL,NULL,1,NULL,1,0,'115',15,'nicole','nicole' from dual union all "
		+"select 116,'2601311','Scope16',1,94,NULL,NULL,NULL,1,NULL,1,0,'116',16,'nicole','nicole' from dual union all "
		+"select 117,'2600163','Scope17',1,94,NULL,NULL,NULL,1,NULL,1,0,'117',17,'nicole','nicole' from dual union all "
		+"select 118,'2211009','Scope18',1,203,NULL,NULL,NULL,1,NULL,1,0,'118',18,'nicole','nicole' from dual union all "
		+"select 119,'2809094','Scope19',1,88,NULL,NULL,NULL,1,NULL,1,0,'119',19,'nicole','nicole' from dual union all "
		+"select 120,'0099887','Scope20',1,204,NULL,NULL,NULL,1,NULL,1,0,'120',20,'nicole','nicole' from dual union all "
		+"select 121,'2112233','Scope21',1,204,NULL,NULL,NULL,1,NULL,1,0,'121',21,'nicole','nicole' from dual union all "
		+"select 122,'2223344','Scope22',1,203,NULL,NULL,NULL,1,NULL,1,0,'122',22,'nicole','nicole' from dual union all "
		+"select 123,'2334455','Scope23',1,203,NULL,NULL,NULL,1,NULL,1,0,'123',23,'nicole','nicole' from dual union all "
		+"select 124,'2600319','Scope24',1,96,NULL,NULL,NULL,1,NULL,1,0,'124',24,'nicole','nicole' from dual union all "
		+"select 125,'2602572','Scope25',1,88,NULL,NULL,NULL,1,NULL,1,0,'125',25,'nicole','nicole' from dual union all "
		+"select 126,'2601132','Scope26',1,94,NULL,NULL,NULL,1,NULL,1,0,'126',26,'nicole','nicole' from dual union all "
		+"select 127,'2600266','Scope27',1,94,NULL,NULL,NULL,1,NULL,1,0,'127',27,'nicole','nicole' from dual union all "
		+"select 128,'2707317','Scope28',1,88,NULL,NULL,NULL,1,NULL,1,0,'128',28,'nicole','nicole' from dual union all  "
		+"select 129,'5556667','Scope29',1,205,NULL,NULL,NULL,1,NULL,1,0,'129',29,'nicole','nicole' from dual union all "
		+"select 130,'6667778','Scope30',1,205,NULL,NULL,NULL,1,NULL,1,0,'130',30,'nicole','nicole' from dual union all "
		+"select 131,'7778889','Scope31',1,155,NULL,NULL,NULL,1,NULL,1,0,'131',31,'nicole','nicole' from dual union all "
		+"select 132,'8889990','Scope32',1,155,NULL,NULL,NULL,1,NULL,1,0,'132',32,'nicole','nicole' from dual union all "
		+"select 133,'2200687','Scope33',1,184,NULL,NULL,NULL,1,NULL,1,0,'133',33,'nicole','nicole' from dual union all "
		+"select 134,'2500850','Scope34',1,88,NULL,NULL,NULL,1,NULL,1,0,'134',34,'nicole','nicole' from dual union all "
		+"select 135,'2600714','Scope35',1,94,NULL,NULL,NULL,1,NULL,1,0,'135',35,'nicole','nicole' from dual union all "
		+"select 136,'2700854','Scope36',1,204,NULL,NULL,NULL,1,NULL,1,0,'136',36,'nicole','nicole' from dual union all "
		+"select 137,'8165413','Scope37',1,203,NULL,NULL,NULL,1,NULL,1,0,'137',37,'nicole','nicole' from dual union all "
		+"select 138,'2600715','Scope38',1,94,NULL,NULL,NULL,1,NULL,1,0,'138',38,'nicole','nicole' from dual union all "
		+"select 139,'2600716','Scope39',1,94,NULL,NULL,NULL,1,NULL,1,0,'139',39,'nicole','nicole' from dual union all "
		+"select 140,'2600717','Scope40',1,94,NULL,NULL,NULL,1,NULL,1,0,'140',40,'nicole','nicole' from dual union all "
		+"select 141,'2600718','Scope41',1,94,NULL,NULL,NULL,1,NULL,1,0,'141',41,'nicole','nicole' from dual union all "
		+"select 142,'2600719','Scope42',1,94,NULL,NULL,NULL,1,NULL,1,0,'142',42,'nicole','nicole' from dual union all "
		+"select 143,'2600720','Scope43',1,94,NULL,NULL,NULL,1,NULL,1,0,'143',43,'nicole','nicole' from dual union all "
		+"select 144,'2600721','Scope44',1,94,NULL,NULL,NULL,1,NULL,1,0,'144',44,'nicole','nicole' from dual union all "
		+"select 145,'2600814','Scope45',1,94,NULL,NULL,NULL,1,NULL,1,0,'145',45,'nicole','nicole' from dual union all "
		+"select 146,'2600815','Scope46',1,94,NULL,NULL,NULL,1,NULL,1,0,'146',46,'nicole','nicole' from dual union all "
		+"select 147,'2600816','Scope47',1,94,NULL,NULL,NULL,1,NULL,1,0,'147',47,'nicole','nicole' from dual union all "
		+"select 148,'2600825','Scope48',1,94,NULL,NULL,NULL,1,NULL,1,0,'148',48,'nicole','nicole' from dual union all "
		+"select 149,'2600826','Scope49',1,94,NULL,NULL,NULL,1,NULL,1,0,'149',49,'nicole','nicole' from dual union all "
		+"select 150,'2600817','Scope50',1,94,NULL,NULL,NULL,1,NULL,1,0,'150',50,'nicole','nicole' from dual union all "
		+"select 151,'2600818','Scope51',1,94,NULL,NULL,NULL,1,NULL,1,0,'151',51,'nicole','nicole' from dual union all "
		+"select 152,'2600819','Scope52',1,94,NULL,NULL,NULL,1,NULL,1,0,'152',52,'nicole','nicole' from dual union all "
		+"select 153,'2600820','Scope53',1,94,NULL,NULL,NULL,1,NULL,1,0,'153',53,'nicole','nicole' from dual union all "
		+"select 154,'2600821','Scope54',1,94,NULL,NULL,NULL,1,NULL,1,0,'154',54,'nicole','nicole' from dual union all "
		+"select 155,'2600834','Scope55',1,94,NULL,NULL,NULL,1,NULL,1,0,'155',55,'nicole','nicole' from dual union all "
		+"select 156,'3600835','Scope56',1,94,NULL,NULL,NULL,1,NULL,1,0,'156',56,'nicole','nicole' from dual union all "
		+"select 157,'3600816','Scope57',1,94,NULL,NULL,NULL,1,NULL,1,0,'157',57,'nicole','nicole' from dual union all "
		+"select 158,'3600825','Scope58',1,94,NULL,NULL,NULL,1,NULL,1,0,'158',58,'nicole','nicole' from dual union all "
		+"select 159,'3600826','Scope59',1,94,NULL,NULL,NULL,1,NULL,1,0,'159',59,'nicole','nicole' from dual union all "
		+"select 160,'3600817','Scope60',1,94,NULL,NULL,NULL,1,NULL,1,0,'160',60,'nicole','nicole' from dual union all "
		+"select 161,'3600818','Scope61',1,94,NULL,NULL,NULL,1,NULL,1,0,'161',61,'nicole','nicole' from dual union all "
		+"select 162,'3600819','Scope62',1,94,NULL,NULL,NULL,1,NULL,1,0,'162',62,'nicole','nicole' from dual union all "
		+"select 163,'3600820','Scope63',1,94,NULL,NULL,NULL,1,NULL,1,0,'163',63,'nicole','nicole' from dual union all "
		+"select 164,'3600821','Scope64',1,94,NULL,NULL,NULL,1,NULL,1,0,'164',64,'nicole','nicole' from dual union all "
		+"select 165,'3600834','Scope65',1,94,NULL,NULL,NULL,1,NULL,1,0,'165',65,'nicole','nicole' from dual union all "
		+"select 166,'4600835','Scope66',1,94,NULL,NULL,NULL,1,NULL,1,0,'166',66,'nicole','nicole' from dual union all "
		+"select 167,'4600816','Scope67',1,94,NULL,NULL,NULL,1,NULL,1,0,'167',67,'nicole','nicole' from dual union all "
		+"select 168,'4600825','Scope68',1,94,NULL,NULL,NULL,1,NULL,1,0,'168',68,'nicole','nicole' from dual union all " 
		+"select 169,'4600826','Scope69',1,94,NULL,NULL,NULL,1,NULL,1,0,'169',69,'nicole','nicole' from dual union all "
		+"select 170,'4600817','Scope70',1,94,NULL,NULL,NULL,1,NULL,1,0,'170',70,'nicole','nicole' from dual union all  "
		+"select 171,'4600818','Scope71',1,94,NULL,NULL,NULL,1,NULL,1,0,'171',71,'nicole','nicole' from dual union all "
		+"select 172,'4600819','Scope72',1,94,NULL,NULL,NULL,1,NULL,1,0,'172',72,'nicole','nicole' from dual union all "
		+"select 173,'4600820','Scope73',1,94,NULL,NULL,NULL,1,NULL,1,0,'173',73,'nicole','nicole' from dual union all "
		+"select 174,'4600821','Scope74',1,94,NULL,NULL,NULL,1,NULL,1,0,'174',74,'nicole','nicole' from dual union all "
		+"select 175,'4600834','Scope75',1,94,NULL,NULL,NULL,1,NULL,1,0,'175',75,'nicole','nicole' from dual union all "
		+"select 176,'5600835','Scope76',1,94,NULL,NULL,NULL,1,NULL,1,0,'176',76,'nicole','nicole' from dual union all "
		+"select 177,'5600816','Scope77',1,94,NULL,NULL,NULL,1,NULL,1,0,'177',77,'nicole','nicole' from dual union all "
		+"select 178,'5600825','Scope78',1,94,NULL,NULL,NULL,1,NULL,1,0,'178',78,'nicole','nicole' from dual union all "
		+"select 179,'5600826','Scope79',1,94,NULL,NULL,NULL,1,NULL,1,0,'179',79,'nicole','nicole' from dual union all "
		+"select 180,'5600817','Scope80',1,94,NULL,NULL,NULL,1,NULL,1,0,'180',80,'nicole','nicole' from dual union all "
		+"select 181,'5600818','Scope81',1,94,NULL,NULL,NULL,1,NULL,1,0,'181',81,'nicole','nicole' from dual union all "
		+"select 182,'5600819','Scope82',1,94,NULL,NULL,NULL,1,NULL,1,0,'182',82,'nicole','nicole' from dual union all "
		+"select 183,'5600820','Scope83',1,94,NULL,NULL,NULL,1,NULL,1,0,'183',83,'nicole','nicole' from dual union all "
		+"select 184,'5600821','Scope84',1,94,NULL,NULL,NULL,1,NULL,1,0,'184',84,'nicole','nicole' from dual union all "
		+"select 185,'5600834','Scope85',1,94,NULL,NULL,NULL,1,NULL,1,0,'185',85,'nicole','nicole' from dual union all "
		+"select 186,'6600835','Scope86',1,94,NULL,NULL,NULL,1,NULL,1,0,'186',86,'nicole','nicole' from dual union all "
		+"select 187,'6600816','Scope87',1,94,NULL,NULL,NULL,1,NULL,1,0,'187',87,'nicole','nicole' from dual union all "
		+"select 188,'6600825','Scope88',1,94,NULL,NULL,NULL,1,NULL,1,0,'188',88,'nicole','nicole' from dual union all "
		+"select 189,'6600826','Scope89',1,94,NULL,NULL,NULL,1,NULL,1,0,'189',89,'nicole','nicole' from dual union all "
		+"select 190,'6600817','Scope90',1,94,NULL,NULL,NULL,1,NULL,1,0,'190',90,'nicole','nicole' from dual union all "
		+"select 191,'8165412','Scope91',1,203,NULL,NULL,NULL,1,NULL,1,0,'191',91,'nicole','nicole' from dual union all "
		+"select 192,'5500819','Scope92',1,94,NULL,NULL,NULL,1,NULL,1,0,'192',92,'nicole','nicole' from dual union all "
		+"select 193,'5500820','Scope93',1,94,NULL,NULL,NULL,1,NULL,1,0,'193',93,'nicole','nicole' from dual union all "
		+"select 194,'5500821','Scope94',1,94,NULL,NULL,NULL,1,NULL,1,0,'194',94,'nicole','nicole' from dual union all "
		+"select 195,'5500834','Scope95',1,94,NULL,NULL,NULL,1,NULL,1,0,'195',95,'nicole','nicole' from dual union all "
		+"select 196,'6500835','Scope96',1,94,NULL,NULL,NULL,1,NULL,1,0,'196',96,'nicole','nicole' from dual union all "
		+"select 197,'6500816','Scope97',1,94,NULL,NULL,NULL,1,NULL,1,0,'197',97,'nicole','nicole' from dual union all "
		+"select 201,'9122334','Scope101',1,183,NULL,NULL,NULL,1,NULL,1,0,'201',101,'nicole','nicole' from dual union all "
		+"select 202,'9233445','Scope102',1,184,NULL,NULL,NULL,1,NULL,1,0,'202',102,'nicole','nicole' from dual union all "
		+"select 203,'9344556','Scope103',1,94,NULL,NULL,NULL,1,NULL,1,0,'203',103,'nicole','nicole' from dual union all "
		+"select 204,'9455667','Scope104',1,200,NULL,NULL,NULL,1,NULL,1,0,'204',104,'nicole','nicole' from dual union all "
		+"select 205,'9566778','Scope105',1,201,NULL,NULL,NULL,1,NULL,1,0,'205',105,'nicole','nicole' from dual union all "
		+"select 206,'9677889','Scope106',1,200,NULL,NULL,NULL,1,NULL,1,0,'206',106,'nicole','nicole' from dual union all "
		+"select 207,'9654231','Scope107',1,155,NULL,NULL,NULL,1,NULL,1,0,'207',107,'nicole','nicole' from dual union all "
		+"select 208,'9543216','Scope108',1,170,NULL,NULL,NULL,1,NULL,1,0,'208',108,'nicole','nicole' from dual union all "
		+"select 209,'8876432','Scope109',1,202,NULL,NULL,NULL,1,NULL,1,0,'209',109,'nicole','nicole' from dual union all "
		+"select 210,'9654321','Scope110',1,155,NULL,NULL,NULL,1,NULL,1,0,'210',110,'nicole','nicole' from dual union all "
		+"select 211,'8988776','Scope111',1,184,NULL,NULL,NULL,1,NULL,1,0,'211',111,'nicole','nicole' from dual union all "
		+"select 212,'9808645','Scope112',1,94,NULL,NULL,NULL,1,NULL,1,0,'212',112,'nicole','nicole' from dual union all "
		+"select 258,'9600825','Scope158',1,94,NULL,NULL,NULL,1,NULL,1,0,'258',158,'nicole','nicole' from dual union all "
		+"select 259,'9600826','Scope159',1,94,NULL,NULL,NULL,1,NULL,1,0,'259',159,'nicole','nicole' from dual union all "
		+"select 260,'9600817','Scope160',1,94,NULL,NULL,NULL,1,NULL,1,0,'260',160,'nicole','nicole' from dual union all "
		+"select 261,'9600818','Scope161',1,94,NULL,NULL,NULL,1,NULL,1,0,'261',161,'nicole','nicole' from dual union all "
		+"select 262,'9600819','Scope162',1,94,NULL,NULL,NULL,1,NULL,1,0,'262',162,'nicole','nicole' from dual union all "
		+"select 263,'9600820','Scope163',1,94,NULL,NULL,NULL,1,NULL,1,0,'263',163,'nicole','nicole' from dual union all "
		+"select 264,'9600821','Scope164',1,94,NULL,NULL,NULL,1,NULL,1,0,'264',164,'nicole','nicole' from dual; "
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
		String stmt4="insert into m_reprocessor(REPROCESSOR_KEY,SERIAL_NO,REPROCESSOR_NAME,REPROCESSOR_MODEL_KEY,PURCHASE_DATE,PURCHASE_COST,FACILITY_KEY,DATA_COOP_FLG,REPROCESSING_ROOM_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) "
		+"select 1,'2000001','Reprocessor 1',1,NULL,NULL,1,0,1,1,0,1,1,'nicole','nicole' from dual union all "
		+"select 2,'2000002','Reprocessor 2',1,NULL,NULL,1,0,1,1,0,2,2,'nicole','nicole' from dual union all "
		+"select 3,'2000003','Reprocessor 3',1,NULL,NULL,1,0,1,1,0,3,3,'nicole','nicole' from dual union all "
		+"select 4,'2000004','Reprocessor 4',1,NULL,NULL,1,0,1,1,0,4,4,'nicole','nicole' from dual union all "
		+"select 5,'2000005','Reprocessor 5',1,NULL,NULL,1,0,1,1,0,5,5,'nicole','nicole' from dual union all "
		+"select 6,'2000006','Reprocessor 6',1,NULL,NULL,1,0,1,1,0,6,6,'nicole','nicole' from dual; "
		+"commit;";
		System.out.println("stmt4="+stmt4);
		try{
				String[] breakstmt=stmt4.split(";"); 
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
		String stmt5="insert into m_reprocessor_programs(REPROCESSOR_PROGRAMS_KEY,REPROCESSOR_KEY,INDEX_NO,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) "
		+"select 11,1,1,1,0,'11',1,'nicole','nicole' from dual union all "
		+"select 12,1,2,1,0,'12',2,'nicole','nicole' from dual union all "
		+"select 13,1,3,1,0,'13',3,'nicole','nicole' from dual; "
		+"commit;";
		System.out.println("stmt5="+stmt5);
		try{
				String[] breakstmt=stmt5.split(";"); 
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
		String stmt6="insert into M_CLINICAL_STAFF(CLINICAL_STAFF_KEY,STAFF_ID,CDS_ID,NAME_PREFIX_KEY,NAME_SUFFIX_KEY,LAST_NAME,FIRST_NAME,MIDDLE_NAME,PHONETIC_LAST_NAME,PHONETIC_FIRST_NAME,SEARCH_AS_DOCTOR_FLG,SEARCH_AS_NURSE_FLG,BELONG_DEPARTMENT_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,DISP_ORDER,CREATE_USER_ID,UPDATE_USER_ID) "
		+"select 101,'T01','T01',1,NULL,'Tech01','Tech01',NULL,NULL,NULL,0,0,NULL,1,0,101,101,'nicole','nicole' from dual union all "
		+"select 102,'T02','T02',1,NULL,'Tech02','Tech02',NULL,NULL,NULL,0,0,NULL,1,0,102,102,'nicole','nicole' from dual union all "
		+"select 103,'T03','T03',1,NULL,'Tech03','Tech03',NULL,NULL,NULL,0,0,NULL,1,0,103,103,'nicole','nicole' from dual union all "
		+"select 104,'T04','T04',1,NULL,'Tech04','Tech04',NULL,NULL,NULL,0,0,NULL,1,0,104,104,'nicole','nicole' from dual union all "
		+"select 105,'T05','T05',1,NULL,'Tech05','Tech05',NULL,NULL,NULL,0,0,NULL,1,0,105,105,'nicole','nicole' from dual union all "
		+"select 106,'T06','T06',1,NULL,'Tech06','Tech06',NULL,NULL,NULL,0,0,NULL,1,0,106,106,'nicole','nicole' from dual union all "
		+"select 107,'T07','T07',1,NULL,'Tech07','Tech07',NULL,NULL,NULL,0,0,NULL,1,0,107,107,'nicole','nicole' from dual union all "
		+"select 108,'T08','T08',1,NULL,'Tech08','Tech08',NULL,NULL,NULL,0,0,NULL,1,0,108,108,'nicole','nicole' from dual union all "
		+"select 109,'T09','T09',1,NULL,'Tech09','Tech09',NULL,NULL,NULL,0,0,NULL,1,0,109,109,'nicole','nicole' from dual union all "
		+"select 110,'T10','T10',1,NULL,'Tech10','Tech10',NULL,NULL,NULL,0,0,NULL,1,0,110,110,'nicole','nicole' from dual union all "
		+"select 111,'T11','T11',1,NULL,'Tech11','Tech11',NULL,NULL,NULL,0,0,NULL,1,0,111,111,'nicole','nicole' from dual union all "
		+"select 112,'T12','T12',1,NULL,'Tech12','Tech12',NULL,NULL,NULL,0,0,NULL,1,0,112,112,'nicole','nicole' from dual union all "
		+"select 113,'T13','T13',1,NULL,'Tech13','Tech13',NULL,NULL,NULL,0,0,NULL,1,0,113,113,'nicole','nicole' from dual union all "
		+"select 114,'T14','T14',1,NULL,'Tech14','Tech14',NULL,NULL,NULL,0,0,NULL,1,0,114,114,'nicole','nicole' from dual union all "
		+"select 115,'T15','T15',1,NULL,'Tech15','Tech15',NULL,NULL,NULL,0,0,NULL,1,0,115,115,'nicole','nicole' from dual union all "
		+"select 116,'T16','T16',1,NULL,'Tech16','Tech16',NULL,NULL,NULL,0,0,NULL,1,0,116,116,'nicole','nicole' from dual union all "
		+"select 117,'T17','T17',1,NULL,'Tech17','Tech17',NULL,NULL,NULL,0,0,NULL,1,0,117,117,'nicole','nicole' from dual union all "
		+"select 118,'T18','T18',1,NULL,'Tech18','Tech18',NULL,NULL,NULL,0,0,NULL,1,0,118,118,'nicole','nicole' from dual union all "
		+"select 119,'T19','T19',1,NULL,'Tech19','Tech19',NULL,NULL,NULL,0,0,NULL,1,0,119,119,'nicole','nicole' from dual union all "
		+"select 120,'T20','T20',1,NULL,'Tech20','Tech20',NULL,NULL,NULL,0,0,NULL,1,0,120,120,'nicole','nicole' from dual;"
		+"commit;";
		System.out.println("stmt6="+stmt6);
		try{
				String[] breakstmt=stmt6.split(";"); 
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
		String stmt7="insert into M_User(USER_KEY,USER_ID,ADMIN_FLG,CURRENT_PASSWORD,CLINICAL_STAFF_KEY, NEXT_CHANGE_PASSWORD_FLG,LAST_CHANGE_PASSWORD_DATE_TIME,REVOKED_FLG,REVOKED_PAST_DATE_TIME,INCORRECT_LOGIN_COUNT,ENABLED_DATE_FROM,ENABLED_DATE_TO,OLYMPUS_USER_FLG,USER_ROLE_KEY,ACTIVE_FLG,DELETED_FLG,OLYMPUS_CODE,CREATE_USER_ID,UPDATE_USER_ID) "
		+"select 101,'T01',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',101,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,101,'nicole','nicole' from dual union all "
		+"select 102,'T02',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',102,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,102,'nicole','nicole' from dual union all "
		+"select 103,'T03',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',103,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,103,'nicole','nicole' from dual union all "
		+"select 104,'T04',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',104,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,104,'nicole','nicole' from dual union all "
		+"select 105,'T05',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',105,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,105,'nicole','nicole' from dual union all "
		+"select 106,'T06',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',106,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,106,'nicole','nicole' from dual union all "
		+"select 107,'T07',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',107,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,107,'nicole','nicole' from dual union all "
		+"select 108,'T08',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',108,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,108,'nicole','nicole' from dual union all "
		+"select 109,'T09',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',109,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,109,'nicole','nicole' from dual union all "
		+"select 110,'T10',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',110,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,110,'nicole','nicole' from dual union all "
		+"select 111,'T11',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',111,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,111,'nicole','nicole' from dual union all "
		+"select 112,'T12',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',112,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,112,'nicole','nicole' from dual union all "
		+"select 113,'T13',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',113,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,113,'nicole','nicole' from dual union all "
		+"select 114,'T14',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',114,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,114,'nicole','nicole' from dual union all "
		+"select 115,'T15',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',115,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,115,'nicole','nicole' from dual union all "
		+"select 116,'T16',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',116,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,116,'nicole','nicole' from dual union all "
		+"select 117,'T17',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',117,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,117,'nicole','nicole' from dual union all "
		+"select 118,'T18',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',118,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,118,'nicole','nicole' from dual union all "
		+"select 119,'T19',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',119,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,119,'nicole','nicole' from dual union all "
		+"select 120,'T20',0,'DA79F638CBB5AFE14F7AB105D6B205DF2D989E5C544B99AD7F1A450C2A62C4B5',120,0,NULL,0,NULL,0,NULL,NULL,0,5,1,0,120,'nicole','nicole' from dual; "
		+"commit;";
		System.out.println("stmt7="+stmt7);
		try{
				String[] breakstmt=stmt7.split(";"); 
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
	
	public static void insertKE_UT_Data(String url, String user, String pass) throws SQLException {
		Connection conn= null;
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement st = conn.createStatement();
		String stmt1="insert into T_Reprocessor_Info (REPROCESSOR_INFO_KEY,REPROCESSOR_KEY,VERSION,DISINFECT_BOTTLECOUNT,REPROCESS_USED_COUNT,REPROCESSOR_STATUS_KEY,DISINFECTION_OPERATION_COUNT,DISINFECTION_ELAPSED_DAY_COUNT,TIME_LAG_MINUTES,NOTIFICATION_HIDE_FLG) "
			+" (select 101,1,'3.03',3,1,4,15,3,0,0 from DUAL union all "
			+" select 102,2,'3.03',3,1,4,15,3,0,0 from DUAL union all "
			+" select 103,4,'3.03',3,1,4,15,3,0,0 from DUAL union all "
			+" select 104,5,'3.03',3,1,4,15,3,0,0 from DUAL union all "
			+" select 105,6,'3.03',3,1,4,15,3,0,0 from DUAL);"
			+" commit;";
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
			String stmt2="insert into T_Reprocess_Condition (REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY, "
			+" DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME,ERROR_VALUE,ERROR_CODE,PROGRESS_NO,PROGRESS_PASTTIME) "
			+" (select 101,1,1,to_date(to_char(trunc(sysdate-1)+12/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+5/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,1,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+12/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from DUAL union all "
			+" select 102,1,2,to_date(to_char(trunc(sysdate-1)+12/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+5/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,1,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+12/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from DUAL union all "
			+" select 103,1,4,to_date(to_char(trunc(sysdate-1)+12/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+5/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,1,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+12/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from DUAL union all "
			+" select 104,1,5,to_date(to_char(trunc(sysdate-1)+12/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+5/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,1,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+12/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from DUAL union all "
			+" select 105,1,6,to_date(to_char(trunc(sysdate-1)+12/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+5/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,1,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+12/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from DUAL); "
			+" commit;";
	
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
				
			String stmt3="insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG) "
			+" (select 101,101,1,145,0,1,114,114,0,0 from DUAL union all "
			+" select 102,102,1,146,0,1,114,114,0,0 from DUAL union all "
			+" select 103,103,1,147,0,1,114,114,0,0 from DUAL union all "
			+" select 104,103,2,148,0,1,114,114,0,0 from DUAL union all "
			+" select 105,104,1,149,0,1,114,114,0,0 from DUAL union all "
			+" select 106,104,2,150,0,1,114,114,0,0 from DUAL union all "
			+" select 107,105,1,151,0,1,114,114,0,0 from DUAL union all "
			+" select 108,105,2,152,0,1,114,114,0,0 from DUAL); "
			+"commit;";
	
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
			String stmt4="update T_Reprocessor_Info set REPROCESS_USED_COUNT=2 where REPROCESSOR_KEY=6; "
			+"update T_Reprocessor_Info set REPROCESS_USED_COUNT=2 where REPROCESSOR_KEY=5; "
			+"update T_Reprocessor_Info set REPROCESS_USED_COUNT=2 where REPROCESSOR_KEY=4; "
			+"commit;";
			try{
					String[] breakstmt=stmt4.split(";"); 
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
			String stmt5=" insert into T_Reprocess_Condition (REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY, "
			+" DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME,ERROR_VALUE,ERROR_CODE,PROGRESS_NO,PROGRESS_PASTTIME) "
			+" (select 106,1,6,to_date(to_char(trunc(sysdate-1)+13/24+16/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+35/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,2,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+13/24+16/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual union all "
			+" select 107,1,5,to_date(to_char(trunc(sysdate-1)+13/24+16/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+35/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,2,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+13/24+16/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual union all "
			+" select 108,1,4,to_date(to_char(trunc(sysdate-1)+15/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+16/24+6/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,2,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+15/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual); "
			+" commit;";
			try{
					String[] breakstmt=stmt5.split(";"); 
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
			String stmt6="insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG) "
			+" (select 109,106,1,153,0,1,114,114,0,0 from dual union all "
			+" select 110,106,2,154,0,1,114,114,0,0 from dual union all "
			+" select 111,107,1,155,0,1,114,114,0,0 from dual union all "
			+" select 112,107,2,156,0,1,114,114,0,0 from dual union all "
			+" select 113,108,1,189,0,1,114,114,0,0 from dual union all "
			+" select 114,108,2,190,0,1,114,114,0,0 from dual);"
			+" commit;";
			try{
					String[] breakstmt=stmt6.split(";"); 
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
			String stmt7="update T_Reprocessor_Info set REPROCESS_USED_COUNT=3 where REPROCESSOR_KEY=5; "
			+"update T_Reprocessor_Info set REPROCESS_USED_COUNT=3 where REPROCESSOR_KEY=6; "
			+"update T_Reprocessor_Info set REPROCESS_USED_COUNT=2 where REPROCESSOR_KEY=1; "
			+"commit;";
			try{
					String[] breakstmt=stmt7.split(";"); 
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
			String stmt8="insert into T_Reprocess_Condition(REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY, "
			+"DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME,ERROR_VALUE,ERROR_CODE,PROGRESS_NO,PROGRESS_PASTTIME) "
			+" (Select 109,1,5,to_date(to_char(trunc(sysdate-1)+15/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+16/24+6/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,3,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+15/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual union all "
			+" Select 110,1,6, to_date(to_char(trunc(sysdate-1)+13/24+16/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+35/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,3,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+15/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual union all "
			+" Select 111,1,1, to_date(to_char(trunc(sysdate-1)+15/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+16/24+6/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,2,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+15/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual); "
			+" commit;"
			+" insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG) "
			+" (Select 115,109,1,192,0,1,114,114,0,0 from dual union all "
			+" Select 116,109,2,193,0,1,114,114,0,0 from dual union all "
			+" Select 117,110,1,194,0,1,114,114,0,0 from dual union all "
			+" Select 118,110,2,195,0,1,114,114,0,0 from dual union all "
			+" Select 119,111,1,196,0,1,114,114,0,0 from dual union all "
			+" Select 120,111,2,197,0,1,114,114,0,0 from dual); "
			+" commit;";
			try{
					String[] breakstmt=stmt8.split(";"); 
					for(int i=0; i< breakstmt.length; i++ ){
						System.out.println(breakstmt[i]);
						st.addBatch(breakstmt[i]);
						st.executeBatch();
					}
					}	catch (SQLException ex){
					// handle any errors
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());	
				}
	}

	public static void insertKE_DD_Data(String url, String user, String pass) throws SQLException {
		Connection conn= null;
		conn= DriverManager.getConnection(url, user, pass);		 
		Statement st = conn.createStatement();
		String stmt1="insert into T_Reprocessor_Info (REPROCESSOR_INFO_KEY,REPROCESSOR_KEY,VERSION,DISINFECT_BOTTLECOUNT,REPROCESS_USED_COUNT,REPROCESSOR_STATUS_KEY,DISINFECTION_OPERATION_COUNT,DISINFECTION_ELAPSED_DAY_COUNT,TIME_LAG_MINUTES,NOTIFICATION_HIDE_FLG) "
				+" (select 101,1,'3.03',3,1,4,15,3,0,0 from DUAL union all "
				+" select 102,2,'3.03',3,1,4,15,3,0,0 from DUAL union all "
				+" select 103,4,'3.03',3,1,4,15,3,0,0 from DUAL union all "
				+" select 104,5,'3.03',3,1,4,15,3,0,0 from DUAL union all "
				+" select 105,6,'3.03',3,1,4,15,3,0,0 from DUAL);"
				+" commit;";
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
			String stmt2="insert into T_Reprocess_Condition (REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY, "
			+" DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME,ERROR_VALUE,ERROR_CODE,PROGRESS_NO,PROGRESS_PASTTIME) "
			+" (select 102,1,2,to_date(to_char(trunc(sysdate-1)+12/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+5/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,1,68,114,114,0,2,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+12/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from DUAL); "
			+" commit;";
	
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
				
			String stmt3="insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG) "
			+" (select 102,102,1,146,0,1,114,114,0,0 from DUAL); "
			+"commit;";
	
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

			String stmt5=" insert into T_Reprocess_Condition (REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY, "
			+" DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME,ERROR_VALUE,ERROR_CODE,PROGRESS_NO,PROGRESS_PASTTIME) "
			+" (select 108,1,4,to_date(to_char(trunc(sysdate-1)+15/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+16/24+6/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,2,68,114,114,0,1,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+15/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual); "
			+" commit;";
			try{
					String[] breakstmt=stmt5.split(";"); 
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
			String stmt6="insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG) "
			+" (select 113,108,1,189,0,1,114,114,0,0 from dual union all "
			+" select 114,108,2,190,0,1,114,114,0,0 from dual);"
			+" commit;";
			try{
					String[] breakstmt=stmt6.split(";"); 
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
			String stmt8="insert into T_Reprocess_Condition(REPROCESS_CONDITION_KEY,AUTO_DETECTED_FLG,REPROCESSOR_KEY,REPROCESS_START_DATE,REPROCESS_END_DATE,PROGRAM_NO,SCOPEWASH_TIME_SETTING,DISINFECT_TIME_SETTING,DISINFECT_TEMPERATURE_SETTING,LEAK_AND_ALCOHOL,WATER_TEMPERATURE,DISINFECT_TEMPERATURE,DISINFECT_COUNT,DISINFECT_PASTDATE,TOTAL_WASH_COUNT,WATERSUPPLY_TIME,START_PERSON_KEY,END_PERSON_KEY, "
			+"DELETED_FLG,REPROCESS_RESULT_KEY,REPROCESSOR_PROGRAMS_KEY,AUTO_REGISTERD_FROM_DC_FLG,ALCOHOL_FLUSH_KEY,REPROCESSING_ROOM_KEY,LOCK_VERSION, CREATED_DATE_TIME,ERROR_VALUE,ERROR_CODE,PROGRESS_NO,PROGRESS_PASTTIME) "
			+" (Select 109,1,5,to_date(to_char(trunc(sysdate-1)+15/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+16/24+6/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,3,68,114,114,0,5,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+15/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual union all "
			+" Select 110,1,6, to_date(to_char(trunc(sysdate-1)+13/24+16/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+13/24+35/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,3,68,114,114,0,3,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+15/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual union all "
			+" Select 111,1,1, to_date(to_char(trunc(sysdate-1)+15/24+36/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),to_date(to_char(trunc(sysdate-1)+16/24+6/1440, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),2,3,7,20,2,21,21,99,99,2,68,114,114,0,3,11,1,2,1,0,to_date(to_char(trunc(sysdate-1)+15/24+36/1440+28/86400, 'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss'),'ffffffffffffffffffffffff',0,'100E',182 from dual); "
			+" commit;"
			+" insert into T_Reprocessed_Scopes (REPROCESSED_SCOPES_KEY,REPROCESS_CONDITION_KEY,INDEX_NO,SCOPE_KEY,ASSOCIATED_STATUS,AUTO_REGISTERD_FROM_DC_FLG,START_PERSON_KEY,END_PERSON_KEY,NOTIFICATION_HIDE_FLG,DELETED_FLG) "
			+" (Select 115,109,1,192,0,1,114,114,0,0 from dual union all "
			+" Select 116,109,2,193,0,1,114,114,0,0 from dual union all "
			+" Select 117,110,1,194,0,1,114,114,0,0 from dual union all "
			+" Select 118,110,2,195,0,1,114,114,0,0 from dual union all "
			+" Select 119,111,1,196,0,1,114,114,0,0 from dual union all "
			+" Select 120,111,2,197,0,1,114,114,0,0 from dual); "
			+" commit;";
			try{
					String[] breakstmt=stmt8.split(";"); 
					for(int i=0; i< breakstmt.length; i++ ){
						System.out.println(breakstmt[i]);
						st.addBatch(breakstmt[i]);
						st.executeBatch();
					}
					}	catch (SQLException ex){
					// handle any errors
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());	
				}
			String stmt7="update T_Reprocessor_Info set REPROCESS_USED_COUNT=3 where REPROCESSOR_KEY=5; "
			+" update T_Reprocessor_Info set REPROCESS_USED_COUNT=3 where REPROCESSOR_KEY=6; "
			+" update T_Reprocessor_Info set REPROCESS_USED_COUNT=2 where REPROCESSOR_KEY=1; "
			+" Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=4 where REPROCESSOR_KEY=1;"
			+" Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=7 where REPROCESSOR_KEY=2;"
			+" Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=5 where REPROCESSOR_KEY=4;"
			+" Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=9 where REPROCESSOR_KEY=5;"
			+" Update T_Reprocessor_Info set REPROCESSOR_STATUS_KEY=2 where REPROCESSOR_KEY=6;"    
			+" update T_Reprocess_Condition set REPROCESS_RESULT_KEY=3 where REPROCESS_CONDITION_KEY=111;"
			+" update T_Reprocess_Condition set REPROCESS_RESULT_KEY=2 where REPROCESS_CONDITION_KEY=102;"
			+" update T_Reprocess_Condition set REPROCESS_RESULT_KEY=1 where REPROCESS_CONDITION_KEY=108;"
			+" update T_Reprocess_Condition set REPROCESS_RESULT_KEY=5 where REPROCESS_CONDITION_KEY=109;"
			+"commit;";
			try{
					String[] breakstmt=stmt7.split(";"); 
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
