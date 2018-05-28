delete from SoiledAreaSignOff;
delete from RelatedItem;
delete from KeyEntryScans;
delete from ReconciliationActivityLogValue;
delete from Barcode where IsShipped=0;
delete from ReconciliationActivityLog;
delete from ReconciliationReportComment;
DELETE FROM ItemHistory;
delete from ScopeCycle;
Delete from ProcedureRoomStatus;
DELETE FROM ScopeStatus;
DELETE FROM Association;
DELETE FROM ScopeCycle;
DELETE FROM LocationStatus;
Delete from ExamQueue;
DELETE FROM Patient;
Delete From ReprocessingStatus;
delete from ScopeStatus;
delete from ItemHistory;
delete from Scanner;
delete from Scope;
delete from ExamType where IsShipped=0;
delete from User_Facility_Assoc where UserID_PK>1;
delete from User_Role_Assoc where UserID_PK>1;
delete from [User] where UserID_PK > 1;
delete from ScopeType where IsShipped=0;
delete from AERDetail;
delete from Location;
delete from AccessPoint;
delete from Staff;
delete from Facility where FacilityID_PK > 1;
IF CURSOR_STATUS('global','cur')>=-1
BEGIN
 DEALLOCATE cur
END
update ExamType set IsActive=0 where ExamTypeID_PK=13;
update Facility set Name='Your Facility Name', Abbreviation='YFN', IsBioburdenTestingPerformed=0,  IsCulturingPerformed=1, IsKEEnabled=0 where FacilityID_PK=1;
update [User] set IsActive=1 where UserID_PK=1;


SET IDENTITY_INSERT AccessPoint ON;
Insert INTO AccessPoint(AccessPointID_PK, SSID, Password) values (1, 'Scanners',  '11111111'); --for TE and Dev in D2-10 or Unifia Experience Center
Insert INTO AccessPoint(AccessPointID_PK, SSID, Password) values (2, 'Linksys11854',  'dnkdj0afi8'); --for AWS/Azure testing in D2-10
Insert INTO AccessPoint(AccessPointID_PK, SSID, Password) values (3, 'Linksys11855',  'dnkdj0afi8'); --for AWS testing in Unifia Experience Center
SET IDENTITY_INSERT AccessPoint OFF;

SET IDENTITY_INSERT Location ON;
Insert INTO Location(LocationID_PK, Name, IsActive, FacilityID_FK, LocationTypeID_FK, AccessPointID_FK) 
values 
(1, 'Administration', 'True', 1, 1, 1),
(21, 'Procedure Room 1', 'True', 1, 2, 1),
(22, 'Procedure Room 2', 'True', 1, 2, 1),
(23, 'Procedure Room 3', 'True', 1, 2, 1),
(24, 'Procedure Room 4', 'False', 1, 2, 1),
(25, 'Procedure Room 5', 'True', 1, 2, 1),
(26, 'Procedure Room 6', 'True', 1, 2, 1),
(27, 'Procedure Room 7', 'True', 1, 2, 1),
(28, 'Procedure Room 8', 'True', 1, 2, 1),
(29, 'Procedure Room 9', 'True', 1, 2, 1),
(41, 'Sink 1', 'True', 1, 4, 1),
(42, 'Sink 2', 'True', 1, 4, 1),
(43, 'Sink 3', 'False', 1, 4, 1),
(44, 'Sink 4', 'True', 1, 4, 1),
(45, 'Sink 5', 'True', 1, 4, 1),
(46, 'Sink 6', 'True', 1, 4, 1),
(51, 'Reprocessor 1', 'True', 1, 5, 1),
(52, 'Reprocessor 2', 'True', 1, 5, 1),
(53, 'Reprocessor 3', 'False', 1, 5, 1),
(54, 'Reprocessor 4', 'True', 1, 5, 1),
(55, 'Reprocessor 5', 'True', 1, 5, 1),
(56, 'Reprocessor 6', 'True', 1, 5, 1),
(71, 'Waiting1', 'True', 1, 7, 1),
(72, 'Waiting2', 'True', 1, 7, 1),
(74, 'Culturing1', 'True', 1,9, 1);


Insert INTO Location(LocationID_PK, Name, IsActive, FacilityID_FK, LocationTypeID_FK, AccessPointID_FK, StorageCabinetCount) 
values 
(31, 'Storage Area A', 'True', 1, 3, 1, 4),
(32, 'Culture Hold Cabinet', 'True', 1, 3, 1, 1),
(33, 'Storage Area C', 'False', 1, 3, 1, 2),
(34, 'Storage Area D', 'True', 1, 3, 1, 4);
SET IDENTITY_INSERT Location OFF;

SET IDENTITY_INSERT AERDetail ON;
Insert INTO AERDetail(AERDetailID_PK, Model, SerialNumber, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, LocationID_FK) 
values 
(1, 'OER-Pro', '2000001', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 51),
(2, 'OER-Pro', '2000002', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 52),
(3, 'OER-Pro', '2000003', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 53),
(4, 'OER-Pro', '2000004', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 54),
(5, 'OER-Pro', '2000005', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 55),
(6, 'OER-Pro', '2000006', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 56);
SET IDENTITY_INSERT AERDetail OFF;

SET IDENTITY_INSERT Scanner ON;
Insert INTO Scanner(ScannerID_PK, Name, ScannerID, IsActive, LocationID_FK) 
values 
(1, 'Administration', '016311', 'True', 1), 
(21, 'PR1', '024285', 'True', 21), 
(22, 'PR2', '024284', 'True', 22),  
(23, 'PR3', '016306', 'True', 23), 
(24, 'PR4', '016317', 'False', 24), 
(25, 'PR5', '017002', 'True', 25),
(26, 'PR6', '015308', 'True', 26),  
(27, 'PR7', '016307', 'True', 27),
(28, 'PR8', '016328', 'True', 28), 
(29, 'PR9', '016351', 'True', 29), 
(41, 'Sink1', '024286', 'True', 41), 
(42, 'Sink2', '015311', 'True', 42), 
(43, 'Sink3', '018878', 'True', 43), 
(44, 'Sink4', '016308', 'True', 44),
(45, 'Sink5', '017003', 'True', 45),
(46, 'Sink6', '015310', 'True', 46),
(51, 'Reprocessor1', '015314', 'True', 51), 
(52, 'Reprocessor2', '015313', 'True', 52), 
(53, 'Reprocessor3', '018879', 'False', 53),
(54, 'Reprocessor4', '015315', 'True', 54), 
(55, 'Reprocessor5', '016320', 'True', 55),
(56, 'Reprocessor6', '016321', 'True', 56),
(71, 'Waiting1', '015312', 'True', 71), 
(72, 'Waiting2', '003041', 'True', 72),
(31, 'StorageA', '018880', 'True', 31), 
(32, 'Culture Hold Cabinet', '018877', 'True', 32),
(33, 'StorageC', '017001', 'False', 33), 
(34, 'StorageD', '013509', 'True', 34),
(74, 'Culturing1','017020','True', 74);
SET IDENTITY_INSERT Scanner OFF;

SET IDENTITY_INSERT Scope ON;
Insert INTO Scope (ScopeID_PK, RFUID, Name, SerialNumber, FacilityID_FK, ScopeTypeID_FK, IsActive, Comments, IsShipped, CompletedCycleCount) Values 
(1, 'e0040150409251e7', 'Scope1', '1122334', 1, 183, 1, '', 0, 0), 
(2, 'e004015040926c20', 'Scope2', '2233445', 1, 184, 1, '', 0, 0), 
(3, 'e004015040926294', 'Scope3', '3344556', 1, 181, 1, '', 0, 0), 
(4, 'e00401504092a32e', 'Scope4', '4455667', 1, 96, 1, '', 0, 0),    
(5, 'e004015040926037', 'Scope5', '5566778', 1, 97, 1, '', 0, 0), 
(6, 'e004015040924a80', 'Scope6', '6677889', 1, 96, 1, '', 0, 0), 
(7, 'e00401504092a30f', 'Scope7', '7654231', 1, 155, 1, '', 0, 0), 
(8, 'e00401504092737e', 'Scope8', '6543216', 1, 170, 1, '', 0, 0), 
(9, 'e004015040926fe2', 'Scope9', '9876432', 1, 14, 1, '', 0, 0), 
(10, 'e00401504092af6e', 'Scope10', '7654321', 1, 155, 1, '', 0, 5), 
(11, 'e00401504092482b', 'Scope11', '9988776', 1, 184, 1, '', 0, 2), 
(12, 'e00401001891a55e', 'Scope12', '2808645', 1, 181, 1, '', 0, 20), 
(13, 'e004010002faf288', 'Scope13', '2600842', 1, 181, 1, '', 0, 10),
(14, 'e004010010ab6695', 'Scope14', '2806734', 1, 181, 1, '', 0, 130),
(15, 'e00401005f093832', 'Scope15', '2200174', 1, 183, 1, '', 0, 25),
(16, 'e004010002fb0f38', 'Scope16', '2601311', 1, 181, 1, '', 0, 11),
(17, 'e004010002fb43fb', 'Scope17', '2600163', 1, 181, 1, '', 0, 0),
(18, 'e00401504092483f', 'Scope18', '2211009', 1, 86, 0, '', 0, 50), 
(19, 'e004010010ab6918', 'Scope19', '2809094', 1, 196, 0, '', 0, 55),
(20, 'e003020040bb6c18', 'Scope20', '0099887', 1, 87, 0, '', 0, 12), 
(24, 'e004010002faf238', 'Scope24', '2600319', 1, 94, 1, '', 0, 0), 
(25, 'e004010002fafd0e', 'Scope25', '2602572', 1, 196, 1, '', 0, 0),
(26, 'e004010002fb085e', 'Scope26', '2601132', 1, 181, 1, '', 0, 19),
(27, 'e004010002fafaff', 'Scope27', '2600266', 1, 181, 1, '', 0, 6),
(28, 'e004010010ab5baf', 'Scope28', '2707317', 1, 196, 1, '', 0, 9),
(29, 'e004015040926e8b', 'Scope29', '5556667', 1, 89, 1, '', 0, 14),
(30, 'e00301703198a8c2', 'Scope30', '6667778', 1, 89, 1, '', 0, 3),
(31, 'e00301890b2d245f', 'Scope31', '7778889', 1, 155, 1, '', 0, 323),
(32, 'e00301701100ba21', 'Scope32', '8889990', 1, 155, 1, '', 0, 23),
(33, 'e00401005f092678', 'Scope33', '2200687', 1, 184, 1, '', 0, 23), 
(34, 'e004010002fb4b21', 'Scope34', '2500850', 1, 196, 1, '', 0, 23), 
(35, 'e004010002fb18be', 'Scope35', '2600714', 1, 181, 1, '', 0, 23); 
SET IDENTITY_INSERT Scope OFF;

SET IDENTITY_INSERT Staff ON;
INSERT INTO staff(StaffID_PK,TitleID_FK,FirstName,LastName,StaffID,StaffTypeID_FK,IsActive)
VALUES
(1,4,'Physician1','Physician1','MD01',1,'True'),
(2,4,'Physician2','Physician2','MD02',1,'True'),
(3,4,'Physician3','Physician3','MD03',1,'True'),
(4,4,'Physician4','Physician4','MD04',1,'True'),
(5,4,'Physician5','Physician5','MD05',1,'True'),
(6,4,'Physician6','Physician6','MD06',1,'True'),
(7,4,'Physician7','Physician7','MD07',1,'True'),
(8,4,'Physician8','Physician8','MD08',1,'True'),
(9,4,'Physician9','Physician9','MD09',1,'True'),
(10,4,'Physician10','Physician10','MD10',1,'True'),
(11,4,'Physician11','Physician11','MD11',1,'True'),
(12,4,'Physician11','Physician12','MD12',1,'True'),
(13,4,'Physician11','Physician13','MD13',1,'True'),
(14,4,'Physician11','Physician14','MD14',1,'True'),
(15,4,'Physician11','Physician15','MD15',1,'True'),
(16,4,'Physician11','Physician16','MD16',1,'True'),
(17,4,'Physician11','Physician17','MD17',1,'True'),
(18,4,'Physician11','Physician18','MD18',1,'True'),
(19,4,'Physician11','Physician19','MD19',1,'False'),
(20,4,'Physician11','Physician20','MD20',1,'False'),
(41,3,'Nurse1','Nurse1','RN01',2,'True'),
(42,1,'Nurse2','Nurse2','RN02',2,'True'),
(43,3,'Nurse3','Nurse3','RN03',2,'True'),
(44,1,'Nurse4','Nurse4','RN04',2,'True'),
(45,3,'Nurse5','Nurse5','RN05',2,'True'),
(46,1,'Nurse6','Nurse6','RN06',2,'True'),
(47,3,'Nurse7','Nurse7','RN07',2,'True'),
(48,1,'Nurse8','Nurse8','RN08',2,'True'),
(49,3,'Nurse9','Nurse9','RN09',2,'True'),
(50,1,'Nurse10','Nurse10','RN10',2,'True'),
(51,3,'Nurse11','Nurse11','RN11',2,'True'),
(52,1,'Nurse12','Nurse12','RN12',2,'True'),
(53,3,'Nurse13','Nurse13','RN13',2,'True'),
(54,1,'Nurse14','Nurse14','RN14',2,'True'),
(55,3,'Nurse15','Nurse15','RN15',2,'True'),
(56,1,'Nurse16','Nurse16','RN16',2,'True'),
(57,3,'Nurse17','Nurse17','RN17',2,'True'),
(58,1,'Nurse18','Nurse18','RN18',2,'True'),
(59,3,'Nurse19','Nurse19','RN19',2,'False'),
(60,1,'Nurse20','Nurse20','RN20',2,'False'),
(61,2,'Admin1','Admin1','AD127943',4,'True');
INSERT INTO staff(StaffID_PK,TitleID_FK,FirstName,LastName,StaffID,StaffTypeID_FK,IsActive,BadgeID)
VALUES
(21,3,'Tech1','Tech1','T01',5,'True', '813F6F82D25204'),
(22,1,'Tech2','Tech2','T02',5,'True', '813F6F82B95204'),
(23,3,'Tech3','Tech3','T03',5,'True', '813F6F82975204'),
(24,1,'Tech4','Tech4','T04',5,'True', '813F6F82B35204'),
(25,3,'Tech5','Tech5','T05',5,'True', '813F6F82925204'),
(26,1,'Tech6','Tech6','T06',5,'True', '813F6F828D5204'),
(27,3,'Tech7','Tech7','T07',5,'True', '813F6F82875104'),
(28,1,'Tech8','Tech8','T08',5,'True', '813F6F82835204'),
(29,3,'Tech9','Tech9','T09',5,'True', '813F6F827E5204'),
(30,1,'Tech10','Tech10','T10',5,'True','813F6F82AE5204'),
(31,3,'Tech11','Tech11','T11',5,'True', '813F6F82CD5204'),
(32,1,'Tech12','Tech12','T12',5,'True', '813F6F82A95204'),
(33,3,'Tech13','Tech13','T13',5,'True', '813F6F82C85204'),
(34,1,'Tech14','Tech14','T14',5,'True', '813F6F82A55304'),
(35,3,'Tech15','Tech15','T15',5,'True', '813F6F82C35204'),
(36,1,'Tech16','Tech16','T16',5,'True', '813F6F82A15204'),
(37,3,'Tech17','Tech17','T17',5,'True', '813F6F82BE5204'),
(38,1,'Tech18','Tech18','T18',5,'True', '813F6F829C5204'),
(39,3,'Tech19','Tech19','T19',5,'False', '802908e2468704'),
(40,1,'Tech20','Tech20','T20',5,'False','');
SET IDENTITY_INSERT Staff OFF;

SET IDENTITY_INSERT Barcode ON;
insert into Barcode(BarcodeID_PK, Name, BarcodeTypeID_FK, IsActive)
values
(14, 'Olympus Repair', 1, 1),
(15, 'Scope Repair Shop', 1, 1),
(16, 'Facility 2', 1, 1),
(17, 'Blue', 4, 1),
(18, 'Red', 4, 1),
(19, 'Return from loan', 3, 1), 
(20, 'MRC Fail', 3, 1), 
(21, 'Received Loaner Scope', 3, 1),
(22, 'Double Wash', 3, 1),
(23,'Custom Reason1', 3, 1),
(24,'Custom Reason2', 3, 1),
(25,'Custom Reason3', 3, 1);
SET IDENTITY_INSERT Barcode OFF;

SET IDENTITY_INSERT Patient ON;

/**

The below adds a temporary table of Patients
Add a test stored procedure to load the data
loads the data
then drops the temp table and test stored procedure
**/
--  New sql code 3/10/2016 jdf

CREATE TABLE temp_Patient(
	[PatientID_PK] [int] NOT NULL,
	[PatientID] [VarChar](50) NOT NULL,
	
)

GO

INSERT INTO temp_Patient (PatientID_PK, PatientID)
VALUES
(38, 'MRN111111'),
(39, 'MRN222222'),
(40, 'MRN333333'),
(41, 'MRN444444'),
(42, 'MRN555555'),
(43, 'MRN666666'),
(44, 'MRN777777'),
(45, 'MRN888888'),
(46, 'MRN999999'),
(47, 'MRN101010'),
(48, 'MRN010101'),
(49, 'MRN121212'),
(50, 'MRN131313'),
(51, 'MRN141414'),
(52, 'MRN151515'),
(53, 'MRN161616'),
(54, 'MRN171717'),
(55, 'MRN181818'),
(56, 'MRN191919'),
(57, 'MRN212121'),
(58, 'MRN242424'),
(59, 'MRN202020'),
(60, 'MRN252525'),
(61, 'MRN262626'),
(62, 'MRN272727'),
(63, 'MRN282828'),
(64, 'MRN292929'),
(65, 'MRN00001'),
(66, 'MRN00002'),
(67, 'MRN00003'),
(68, 'MRN00004'),
(69, 'MRN00005'),
(70,'MRN00006'),
(71,'MRN00007'),
(72,'MRN00008'),
(73,'MRN00009'),
(74,'MRN00010'),
(75,'MRN00011'),
(76,'MRN00012'),
(77,'MRN00013'),
(78,'MRN00014'),
(79,'MRN00015'),
(80,'MRN00016'),
(81,'MRN00017'),
(82,'MRN00018'),
(83,'MRN00019'),
(84,'MRN00020'),
(85,'MRN00021'),
(86,'MRN00022'),
(87,'MRN00023'),
(88,'MRN00024'),
(89,'MRN00025'),
(90,'MRN00026'),
(91,'MRN00027'),
(92,'MRN00028'),
(93,'MRN00029'),
(94,'MRN00030'),
(95,'MRN00031'),
(96,'MRN00032'),
(97,'MRN00033'),
(98,'MRN00034'),
(99,'MRN00035'),
(100,'MRN00036'),
(101,'MRN00037'),
(102,'MRN00038'),
(103,'MRN00039'),
(104,'MRN00040'),
(105,'MRN00041'),
(106,'MRN00042'),
(107,'MRN00043'),
(108,'MRN00044'),
(109,'MRN00045'),
(110,'MRN00046'),
(111,'MRN00047'),
(112,'MRN00048'),
(113,'MRN00049'),
(114,'MRN00050'),
(115,'MRN00051'),
(116,'MRN00052'),
(117,'MRN00053'),
(118,'MRN00054'),
(119,'MRN00055'),
(120,'MRN00056'),
(121,'MRN00057'),
(122,'MRN00058'),
(123,'MRN00059'),
(124,'MRN00060'),
(125,'MRN00061'),
(126,'MRN00062'),
(127,'MRN00063'),
(128,'MRN00064'),
(129,'MRN00065'),
(130,'MRN00066'),
(131,'MRN00067'),
(132,'MRN00068'),
(133,'MRN00069'),
(134,'MRN00070'),
(135,'MRN00071'),
(136,'MRN00072'),
(137,'MRN00073'),
(138,'MRN00074'),
(139,'MRN00075'),
(140,'MRN00076'),
(141,'MRN00077'),
(142,'MRN00078'),
(143,'MRN00079'),
(144,'MRN00080'),
(145,'MRN00081'),
(146,'MRN00082'),
(147,'MRN00083'),
(148,'MRN00084'),
(149,'MRN00085'),
(150,'MRN00086'),
(151,'MRN00087'),
(152,'MRN00088'),
(153,'MRN00089'),
(154,'MRN00090'),
(155,'MRN00091'),
(156,'MRN00092'),
(157,'MRN00093'),
(158,'MRN00094'),
(159,'MRN00095'),
(160,'MRN00096'),
(161,'MRN00097'),
(162,'MRN00098'),
(163,'MRN00099'),
(164,'MRN00100');


/**
Create temporary stored procedure
**/
SET IDENTITY_INSERT Patient ON;
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create PROCEDURE [dbo].[sp_InsertPatient_test]
	@PatientID_PK int,
	@PatientID varchar(50)
	
AS
BEGIN
	SET NOCOUNT ON;

BEGIN TRY
	OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a';

	INSERT INTO [dbo].[Patient]
		([PatientID_PK],[PatientID])
		
		VALUES
			(@PatientID_PK,
			ENCRYPTBYKEY(KEY_GUID('UNIFIA_SYMKEY_01'), @PatientID))

	CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;

END TRY

BEGIN CATCH
	
	IF EXISTS(SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01')
		CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;

END CATCH
END;
GO

Declare @PatientID_PK int, @PatientID VarChar(50)

Declare Cur Cursor For 
Select PatientID_PK, PatientID FROM temp_Patient

Open Cur;
Fetch Next from Cur into @PatientID_PK, @PatientID
While @@Fetch_Status=0

Begin
	Execute sp_InsertPatient_test @PatientID_PK, @PatientID
	Fetch NEXT FROM Cur Into @PatientID_PK, @PatientID
END
GO

Drop Table dbo.temp_Patient;
DROP PRocedure dbo.sp_InsertPatient_test;

--End of new code from 3/10/16 jdf

SET IDENTITY_INSERT Patient OFF;

