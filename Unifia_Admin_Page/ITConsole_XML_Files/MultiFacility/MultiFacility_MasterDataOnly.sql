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
update Facility set Name='Facility 1', Abbreviation='FAC1', IsBioburdenTestingPerformed=1,  IsCulturingPerformed=1, IsKEEnabled=0, IsPrimary=1,SerialNumber='111' where FacilityID_PK=1;
update [User] set IsActive=1 where UserID_PK=1;

SET IDENTITY_INSERT Facility ON;
Insert into Facility(FacilityID_PK,Name,IsActive,Abbreviation,CustomerNumber,HangTime,IsBioburdenTestingPerformed,IsCulturingPerformed,IsKEEnabled,IsPrimary,SerialNumber) values
(2,'Facility 2',1,'FAC2','2',7,0,0,0,0,'222'),
(3,'Facility 3',1,'FAC3','3',7,0,0,0,0,'333');
SET IDENTITY_INSERT Facility OFF;

SET IDENTITY_INSERT AccessPoint ON;
Insert INTO AccessPoint(AccessPointID_PK, SSID, Password) values 
(1, 'Scanners',  '11111111'),
(2, 'Linksys11854',  'dnkdj0afi8'),
(3, 'Linksys11855',  'dnkdj0afi8'); 
SET IDENTITY_INSERT AccessPoint OFF;

SET IDENTITY_INSERT Location ON;
Insert INTO Location(LocationID_PK, Name, IsActive, FacilityID_FK, LocationTypeID_FK, AccessPointID_FK) 
values 
(1, 'F1 Administration', 'True', 1, 1, 1),
(21, 'F1 Procedure Room 1', 'True', 1, 2, 1),
(22, 'F1 Procedure Room 2', 'True', 1, 2, 1),
(23, 'F1 Procedure Room 3', 'True', 1, 2, 1),
(24, 'F1 Procedure Room 4', 'False', 1, 2, 1),
(25, 'F1 Procedure Room 5', 'True', 1, 2, 1),
(26, 'F1 Procedure Room 6', 'True', 1, 2, 1),
(27, 'F1 Procedure Room 7', 'True', 1, 2, 1),
(28, 'F1 Procedure Room 8', 'True', 1, 2, 1),
(29, 'F1 Procedure Room 9', 'True', 1, 2, 1),
(41, 'F1 Sink 1', 'True', 1, 4, 1),
(42, 'F1 Sink 2', 'True', 1, 4, 1),
(43, 'F1 Sink 3', 'False', 1, 4, 1),
(44, 'F1 Sink 4', 'True', 1, 4, 1),
(45, 'F1 Sink 5', 'True', 1, 4, 1),
(46, 'F1 Sink 6', 'True', 1, 4, 1),
(51, 'F1 Reprocessor 1', 'True', 1, 5, 1),
(52, 'F1 Reprocessor 2', 'True', 1, 5, 1),
(53, 'F1 Reprocessor 3', 'False', 1, 5, 1),
(54, 'F1 Reprocessor 4', 'True', 1, 5, 1),
(55, 'F1 Reprocessor 5', 'True', 1, 5, 1),
(56, 'F1 Reprocessor 6', 'True', 1, 5, 1),
(71, 'F1 Waiting1', 'True', 1, 7, 1),
(72, 'F1 Waiting2', 'True', 1, 7, 1), 
(73, 'F1 Bioburden1', 'True',1,8,1),
(75, 'F1 Bioburden2', 'True',1,8,1),
(74, 'F1 CultureB', 'True', 1,9, 1),
(81, 'F1 CultureA', 'True', 1, 9, 2),
(101, 'F2 Administration', 'True', 2, 1, 2),
(121, 'F2 Procedure Room 1', 'True', 2, 2, 2),
(122, 'F2 Procedure Room 2', 'True', 2, 2, 2),
(123, 'F2 Procedure Room 3', 'True', 2, 2, 2),
(124, 'F2 Procedure Room 4', 'False', 2, 2, 2),
(125, 'F2 Procedure Room 5', 'True', 2, 2, 2),
(126, 'F2 Procedure Room 6', 'True', 2, 2, 2),
(127, 'F2 Procedure Room 7', 'True', 2, 2, 2),
(128, 'F2 Procedure Room 8', 'True', 2, 2, 2),
(129, 'F2 Procedure Room 9', 'True', 2, 2, 2),
(141, 'F2 Sink 1', 'True', 2, 4, 2),
(142, 'F2 Sink 2', 'True', 2, 4, 2),
(143, 'F2 Sink 3', 'False', 2, 4, 2),
(144, 'F2 Sink 4', 'True', 2, 4, 2),
(145, 'F2 Sink 5', 'True', 2, 4, 2),
(146, 'F2 Sink 6', 'True', 2, 4, 2),
(151, 'F2 Reprocessor 1', 'True', 2, 5, 2),
(152, 'F2 Reprocessor 2', 'True', 2, 5, 2),
(153, 'F2 Reprocessor 3', 'False', 2, 5, 2),
(154, 'F2 Reprocessor 4', 'True', 2, 5, 2),
(155, 'F2 Reprocessor 5', 'True', 2, 5, 2),
(156, 'F2 Reprocessor 6', 'True', 2, 5, 2),
(171, 'F2 Waiting1', 'True', 2, 7, 2),
(172, 'F2 Waiting2', 'True', 2, 7, 2), 
(173, 'F2 Bioburden1', 'True',2,8,2),
(175, 'F2 Bioburden2', 'True',2,8,2),
(174, 'F2 CultureB', 'True', 2,9, 2),
(181, 'F2 CultureA', 'True', 2, 9, 2),
(201, 'F3 Administration', 'True', 3, 1,3),
(221, 'F3 Procedure Room 1', 'True', 3, 2,3),
(222, 'F3 Procedure Room 2', 'True', 3, 2,3),
(223, 'F3 Procedure Room 3', 'True', 3, 2,3),
(224, 'F3 Procedure Room 4', 'False', 3, 2,3),
(225, 'F3 Procedure Room 5', 'True', 3, 2,3),
(226, 'F3 Procedure Room 6', 'True', 3, 2,3),
(227, 'F3 Procedure Room 7', 'True', 3, 2,3),
(228, 'F3 Procedure Room 8', 'True', 3, 2,3),
(229, 'F3 Procedure Room 9', 'True', 3, 2,3),
(241, 'F3 Sink 1', 'True', 3, 4,3),
(242, 'F3 Sink 2', 'True', 3, 4,3),
(243, 'F3 Sink 3', 'False', 3, 4,3),
(244, 'F3 Sink 4', 'True', 3, 4,3),
(245, 'F3 Sink 5', 'True', 3, 4,3),
(246, 'F3 Sink 6', 'True', 3, 4,3),
(251, 'F3 Reprocessor 1', 'True', 3, 5,3),
(252, 'F3 Reprocessor 2', 'True', 3, 5,3),
(253, 'F3 Reprocessor 3', 'False', 3, 5,3),
(254, 'F3 Reprocessor 4', 'True', 3, 5,3),
(255, 'F3 Reprocessor 5', 'True', 3, 5,3),
(256, 'F3 Reprocessor 6', 'True', 3, 5,3),
(271, 'F3 Waiting1', 'True', 3, 7,3),
(272, 'F3 Waiting2', 'True', 3, 7,3), 
(273, 'F3 Bioburden1', 'True',3,8,3),
(275, 'F3 Bioburden2', 'True',3,8,3),
(274, 'F3 CultureB', 'True', 3,9,3),
(281, 'F3 CultureA', 'True', 3, 9, 3);

Insert INTO Location(LocationID_PK, Name, IsActive, FacilityID_FK, LocationTypeID_FK, AccessPointID_FK, StorageCabinetCount) 
values 
(31, 'F1 Storage Area A', 'True', 1, 3, 1, 4),
(32, 'F1 Storage Area B', 'True', 1, 3, 1, 1),
(33, 'F1 Storage Area C', 'False', 1, 3, 1, 2),
(34, 'F1 Storage Area D', 'True', 1, 3, 1, 4),
(35, 'F1 Culture Hold Cabinet', 'True', 1, 3, 1, 1),
(131, 'F2 Storage Area A', 'True', 2, 3, 2, 4),
(132, 'F2 Storage Area B', 'True', 2, 3, 2, 1),
(133, 'F2 Storage Area C', 'False', 2, 3, 2, 2),
(134, 'F2 Storage Area D', 'True', 2, 3, 2, 4),
(135, 'F2 Culture Hold Cabinet', 'True', 2, 3, 2, 1),
(231, 'F3 Storage Area A', 'True', 3, 3, 3, 4),
(232, 'F3 Storage Area B', 'True', 3, 3, 3, 1),
(233, 'F3 Storage Area C', 'False', 3, 3, 3, 2),
(234, 'F3 Storage Area D', 'True', 3, 3, 3, 4),
(235, 'F3 Culture Hold Cabinet', 'True', 3, 3, 3, 1);
SET IDENTITY_INSERT Location OFF;

SET IDENTITY_INSERT AERDetail ON;
Insert INTO AERDetail(AERDetailID_PK, Model, SerialNumber, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, LocationID_FK) 
values 
(1, 'OER-Pro', '2000001', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 51),
(2, 'OER-Pro', '2000002', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 52),
(3, 'OER-Pro', '2000003', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 53),
(4, 'OER-Pro', '2000004', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 54),
(5, 'OER-Pro', '2000005', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 55),
(6, 'OER-Pro', '2000006', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 56),
(11, 'OER-Pro', '3000001', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 151),
(12, 'OER-Pro', '3000002', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 152),
(13, 'OER-Pro', '3000003', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 153),
(14, 'OER-Pro', '3000004', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 154),
(15, 'OER-Pro', '3000005', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 155),
(16, 'OER-Pro', '3000006', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 156),
(21, 'OER-Pro', '4000001', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 251),
(22, 'OER-Pro', '4000002', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 252),
(23, 'OER-Pro', '4000003', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 253),
(24, 'OER-Pro', '4000004', 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 50, 25, 82, 254),
(25, 'OER-Pro', '4000005', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 255),
(26, 'OER-Pro', '4000006', 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 60, 30, 54, 256);
SET IDENTITY_INSERT AERDetail OFF;

SET IDENTITY_INSERT Scanner ON;
Insert INTO Scanner(ScannerID_PK, Name, ScannerID, IsActive, LocationID_FK) 
values 
(1, 'F1 Administration', '016311', 'True', 1), 
(21, 'F1 PR1', '024285', 'True', 21), 
(22, 'F1 PR2', '024284', 'True', 22),  
(23, 'F1 PR3', '016306', 'True', 23), 
(24, 'F1 PR4', '016317', 'False', 24), 
(25, 'F1 PR5', '017002', 'True', 25), 
(26, 'F1 PR6', '015308', 'True', 26),  
(27, 'F1 PR7', '016307', 'True', 27),  
(28, 'F1 PR8', '016328', 'True', 28), 
(29, 'F1 PR9', '016351', 'True', 29), 
(41, 'F1 Sink1', '024286', 'True', 41), 
(42, 'F1 Sink2', '015311', 'True', 42), 
(43, 'F1 Sink3', '018878', 'True', 43), 
(44, 'F1 Sink4', '016308', 'True', 44), 
(45, 'F1 Sink5', '017003', 'True', 45), 
(46, 'F1 Sink6', '015310', 'True', 46),   
(51, 'F1 Reprocessor1', '015314', 'True', 51), 
(52, 'F1 Reprocessor2', '015313', 'True', 52), 
(53, 'F1 Reprocessor3', '018879', 'False', 53), 
(54, 'F1 Reprocessor4', '015315', 'True', 54), 
(55, 'F1 Reprocessor5', '016320', 'True', 55), 
(56, 'F1 Reprocessor6', '016321', 'True', 56), 
(71, 'F1 Waiting1', '015312', 'True', 71), 
(72, 'F1 Waiting2', '003041', 'True', 72),  
(73, 'F1 Bioburden1','017010', 'True', 73),
(74, 'F1 CultureB','017020','True', 74),
(75, 'F1 Bioburden2','017110', 'True', 75),
(31, 'F1 StorageA', '018880', 'True', 31),   
(35, 'F1 Culture Hold Cabinet', '019001', 'True', 35),
(32, 'F1 StorageB', '018877', 'True', 32), 
(33, 'F1 StorageC', '017001', 'False', 33), 
(34, 'F1 StorageD', '013509', 'True', 34),  
(81, 'F1 CultureA', '019313', 'True', 81), 
(11, 'F2 Administration', '116311', 'True', 101), 
(121, 'F2 PR1', '124285', 'True', 121), 
(122, 'F2 PR2', '124284', 'True', 122),  
(123, 'F2 PR3', '116306', 'True', 123), 
(124, 'F2 PR4', '116317', 'False', 124), 
(125, 'F2 PR5', '117002', 'True', 125), 
(126, 'F2 PR6', '115308', 'True', 126),  
(127, 'F2 PR7', '116307', 'True', 127),  
(128, 'F2 PR8', '116328', 'True', 128), 
(129, 'F2 PR9', '116351', 'True', 129), 
(141, 'F2 Sink1', '124286', 'True', 141), 
(142, 'F2 Sink2', '115311', 'True', 142), 
(143, 'F2 Sink3', '118878', 'True', 143), 
(144, 'F2 Sink4', '116308', 'True', 144), 
(145, 'F2 Sink5', '117003', 'True', 145), 
(146, 'F2 Sink6', '115310', 'True', 146),   
(151, 'F2 Reprocessor1', '115314', 'True', 151), 
(152, 'F2 Reprocessor2', '115313', 'True', 152), 
(153, 'F2 Reprocessor3', '118879', 'False', 153), 
(154, 'F2 Reprocessor4', '115315', 'True', 154), 
(155, 'F2 Reprocessor5', '116320', 'True', 155), 
(156, 'F2 Reprocessor6', '116321', 'True', 156), 
(171, 'F2 Waiting1', '115312', 'True', 171), 
(172, 'F2 Waiting2', '103041', 'True', 172),  
(173, 'F2 Bioburden1','117010', 'True', 173),
(175, 'F2 Bioburden2','117110', 'True', 175),
(174, 'F2 CultureB','117020','True', 174),
(131, 'F2 StorageA', '118880', 'True', 131),   
(135, 'F2 Culture Hold Cabinet', '119001', 'True', 135),
(132, 'F2 StorageB', '118877', 'True', 132), 
(133, 'F2 StorageC', '117001', 'False', 133), 
(134, 'F2 StorageD', '113509', 'True', 134),  
(181, 'F2 CultureA', '119313', 'True', 181), 
(201, 'F3 Administration', '216311', 'True', 201), 
(221, 'F3 PR1', '224285', 'True', 221), 
(222, 'F3 PR2', '224284', 'True', 222),  
(223, 'F3 PR3', '216306', 'True', 223), 
(224, 'F3 PR4', '216317', 'False', 224), 
(225, 'F3 PR5', '217002', 'True', 225), 
(226, 'F3 PR6', '215308', 'True', 226),  
(227, 'F3 PR7', '216307', 'True', 227),  
(228, 'F3 PR8', '216328', 'True', 228), 
(229, 'F3 PR9', '216351', 'True', 229), 
(241, 'F3 Sink1', '224286', 'True', 241), 
(242, 'F3 Sink2', '215311', 'True', 242), 
(243, 'F3 Sink3', '218878', 'True', 243), 
(244, 'F3 Sink4', '216308', 'True', 244), 
(245, 'F3 Sink5', '217003', 'True', 245), 
(246, 'F3 Sink6', '215310', 'True', 246),   
(251, 'F3 Reprocessor1', '215314', 'True', 251), 
(252, 'F3 Reprocessor2', '215313', 'True', 252), 
(253, 'F3 Reprocessor3', '218879', 'False', 253), 
(254, 'F3 Reprocessor4', '215315', 'True', 254), 
(255, 'F3 Reprocessor5', '216320', 'True', 255), 
(256, 'F3 Reprocessor6', '216321', 'True', 256), 
(271, 'F3 Waiting1', '215312', 'True', 271), 
(272, 'F3 Waiting2', '203041', 'True', 272),  
(273, 'F3 Bioburden1','217010', 'True', 273),
(275, 'F3 Bioburden2','217110', 'True', 275),
(274, 'F3 CultureB','217020','True', 274),
(231, 'F3 StorageA', '218880', 'True', 231),   
(235, 'F3 Culture Hold Cabinet', '219001', 'True', 235),
(232, 'F3 StorageB', '218877', 'True', 232), 
(233, 'F3 StorageC', '217001', 'False', 233), 
(234, 'F3 StorageD', '213509', 'True', 234),  
(281, 'F3 CultureA', '219313', 'True', 281);
SET IDENTITY_INSERT Scanner OFF;

SET IDENTITY_INSERT Scope ON;
Insert INTO Scope (ScopeID_PK, RFUID, Name, SerialNumber, FacilityID_FK, ScopeTypeID_FK, IsActive, Comments, IsShipped, CompletedCycleCount) Values 
(1, 'e0040150409251e7', 'F1 Scope1', '1122334', 1, 183, 1, '', 0, 0), 
(2, 'e004015040926c20', 'F1 Scope2', '2233445', 1, 184, 1, '', 0, 0), 
(3, 'e004015040926294', 'F1 Scope3', '3344556', 1, 181, 1, '', 0, 0), 
(4, 'e00401504092a32e', 'F1 Scope4', '4455667', 1, 96, 1, '', 0, 0),    
(5, 'e004015040926037', 'F1 Scope5', '5566778', 1, 97, 1, '', 0, 0), 
(6, 'e004015040924a80', 'F1 Scope6', '6677889', 1, 96, 1, '', 0, 0), 
(7, 'e00401504092a30f', 'F1 Scope7', '7654231', 1, 155, 1, '', 0, 0), 
(8, 'e00401504092737e', 'F1 Scope8', '6543216', 1, 170, 1, '', 0, 0), 
(9, 'e004015040926fe2', 'F1 Scope9', '9876432', 1, 14, 1, '', 0, 0), 
(10, 'e00401504092af6e', 'F1 Scope10', '7654321', 1, 155, 1, '', 0, 5), 
(11, 'e00401504092482b', 'F1 Scope11', '9988776', 1, 184, 1, '', 0, 2), 
(12, 'e00401001891a55e', 'F1 Scope12', '2808645', 1, 181, 1, '', 0, 20), 
(13, 'e004010002faf288', 'F1 Scope13', '2600842', 1, 181, 1, '', 0, 10),
(14, 'e004010010ab6695', 'F1 Scope14', '2806734', 1, 181, 1, '', 0, 130),
(15, 'e00401005f093832', 'F1 Scope15', '2200174', 1, 183, 1, '', 0, 25),
(16, 'e004010002fb0f38', 'F1 Scope16', '2601311', 1, 181, 1, '', 0, 11),
(17, 'e004010002fb43fb', 'F1 Scope17', '2600163', 1, 181, 1, '', 0, 0),
(18, 'e00401504092483f', 'F1 Scope18', '2211009', 1, 86, 1, '', 0, 50), 
(19, 'e004010010ab6918', 'F1 Scope19', '2809094', 1, 196, 1, '', 0, 55),
(20, 'e003020040bb6c18', 'F1 Scope20', '0099887', 1, 87, 1, '', 0, 12), 
(21, 'e003001452ca51b1', 'F1 Scope21', '2112233', 1, 87, 1, '', 0, 13),
(22, 'e003001780332ab2', 'F1 Scope22', '2223344', 1, 86, 1, '', 0, 22), 
(23, 'e0030013762ce232', 'F1 Scope23', '2334455', 1, 86, 1, '', 0, 11), 
(24, 'e004010002faf238', 'F1 Scope24', '2600319', 1, 94, 1, '', 0, 0), 
(25, 'e004010002fafd0e', 'F1 Scope25', '2602572', 1, 196, 1, '', 0, 0),
(26, 'e004010002fb085e', 'F1 Scope26', '2601132', 1, 181, 1, '', 0, 19),
(27, 'e004010002fafaff', 'F1 Scope27', '2600266', 1, 181, 1, '', 0, 6),
(28, 'e004010010ab5baf', 'F1 Scope28', '2707317', 1, 196, 1, '', 0, 9),
(29, 'e004015040926e8b', 'F1 Scope29', '5556667', 1, 89, 1, '', 0, 14),
(30, 'e00301703198a8c2', 'F1 Scope30', '6667778', 1, 89, 1, '', 0, 3),
(31, 'e00301890b2d245f', 'F1 Scope31', '7778889', 1, 155, 1, '', 0, 323),
(32, 'e00301701100ba21', 'F1 Scope32', '8889990', 1, 155, 1, '', 0, 23),
(33, 'e00401005f092678', 'F1 Scope33', '2200687', 1, 184, 1, '', 0, 23), 
(34, 'e004010002fb4b21', 'F1 Scope34', '2500850', 1, 196, 1, '', 0, 23), 
(35, 'e004010002fb18be', 'F1 Scope35', '2600714', 1, 181, 1, '', 0, 23), 
(36, 'e00300129643dc16', 'F1 Scope36', '2700854', 1, 87, 1, '', 0, 13),
(37, 'e003003013561ab2', 'F1 Scope37', '8165413', 1, 86, 1, '', 0, 22), 
(38, 'e004010003fb18be', 'F1 Scope38', '2600715', 1, 181, 1, '', 0, 0), 
(39, 'e004010004fb18be', 'F1 Scope39', '2600716', 1, 181, 1, '', 0, 0), 
(40, 'e004010005fb18be', 'F1 Scope40', '2600717', 1, 181, 1, '', 0, 0), 
(41, 'e004010006fb18be', 'F1 Scope41', '2600718', 1, 181, 1, '', 0, 0), 
(42, 'e004010007fb18be', 'F1 Scope42', '2600719', 1, 181, 1, '', 0, 0), 
(43, 'e004010008fb18be', 'F1 Scope43', '2600720', 1, 181, 1, '', 0, 0), 
(44, 'e004010009fb18be', 'F1 Scope44', '2600721', 1, 181, 1, '', 0, 0), 
(45, 'e004010012fb18be', 'F1 Scope45', '2600814', 1, 181, 1, '', 0, 0), 
(46, 'e004010013fb18be', 'F1 Scope46', '2600815', 1, 181, 1, '', 0, 0), 
(47, 'e004010014fb18be', 'F1 Scope47', '2600816', 1, 181, 1, '', 0, 0), 
(48, 'e004010033fb18be', 'F1 Scope48', '2600825', 1, 181, 1, '', 0, 0), 
(49, 'e004010034fb18be', 'F1 Scope49', '2600826', 1, 181, 1, '', 0, 0), 
(50, 'e004010015fb18be', 'F1 Scope50', '2600817', 1, 181, 1, '', 0, 0), 
(51, 'e004010016fb18be', 'F1 Scope51', '2600818', 1, 181, 1, '', 0, 0), 
(52, 'e004010017fb18be', 'F1 Scope52', '2600819', 1, 181, 1, '', 0, 0), 
(53, 'e004010018fb18be', 'F1 Scope53', '2600820', 1, 181, 1, '', 0, 0), 
(54, 'e004010019fb18be', 'F1 Scope54', '2600821', 1, 181, 1, '', 0, 0), 
(55, 'e004010022fb18be', 'F1 Scope55', '2600834', 1, 181, 1, '', 0, 0), 
(56, 'e004000023fb18be', 'F1 Scope56', '3600835', 1, 181, 1, '', 0, 0), 
(57, 'e004020014fb18be', 'F1 Scope57', '3600816', 1, 181, 1, '', 0, 0), 
(58, 'e004020033fb18be', 'F1 Scope58', '3600825', 1, 181, 1, '', 0, 0), 
(59, 'e004020034fb18be', 'F1 Scope59', '3600826', 1, 181, 1, '', 0, 0), 
(60, 'e004020015fb18be', 'F1 Scope60', '3600817', 1, 181, 1, '', 0, 0), 
(61, 'e004020016fb18be', 'F1 Scope61', '3600818', 1, 181, 1, '', 0, 0), 
(62, 'e004020017fb18be', 'F1 Scope62', '3600819', 1, 181, 1, '', 0, 0), 
(63, 'e004020018fb18be', 'F1 Scope63', '3600820', 1, 181, 1, '', 0, 0), 
(64, 'e004020019fb18be', 'F1 Scope64', '3600821', 1, 181, 1, '', 0, 0), 
(65, 'e004020022fb18be', 'F1 Scope65', '3600834', 1, 181, 1, '', 0, 0), 
(66, 'e004030023fb18be', 'F1 Scope66', '4600835', 1, 181, 1, '', 0, 0), 
(67, 'e004030014fb18be', 'F1 Scope67', '4600816', 1, 181, 1, '', 0, 0), 
(68, 'e004030033fb18be', 'F1 Scope68', '4600825', 1, 181, 1, '', 0, 0), 
(69, 'e004030034fb18be', 'F1 Scope69', '4600826', 1, 181, 1, '', 0, 0), 
(70, 'e004030015fb18be', 'F1 Scope70', '4600817', 1, 181, 1, '', 0, 0), 
(71, 'e004030016fb18be', 'F1 Scope71', '4600818', 1, 181, 1, '', 0, 0), 
(72, 'e004030017fb18be', 'F1 Scope72', '4600819', 1, 181, 1, '', 0, 0), 
(73, 'e004030018fb18be', 'F1 Scope73', '4600820', 1, 181, 1, '', 0, 0), 
(74, 'e004030019fb18be', 'F1 Scope74', '4600821', 1, 181, 1, '', 0, 0), 
(75, 'e004030022fb18be', 'F1 Scope75', '4600834', 1, 181, 1, '', 0, 0), 
(76, 'e004040023fb18be', 'F1 Scope76', '5600835', 1, 181, 1, '', 0, 0), 
(77, 'e004040014fb18be', 'F1 Scope77', '5600816', 1, 181, 1, '', 0, 0), 
(78, 'e004040033fb18be', 'F1 Scope78', '5600825', 1, 181, 1, '', 0, 0), 
(79, 'e004040034fb18be', 'F1 Scope79', '5600826', 1, 181, 1, '', 0, 0), 
(80, 'e004040015fb18be', 'F1 Scope80', '5600817', 1, 181, 1, '', 0, 0), 
(81, 'e004040016fb18be', 'F1 Scope81', '5600818', 1, 181, 1, '', 0, 0), 
(82, 'e004040017fb18be', 'F1 Scope82', '5600819', 1, 181, 1, '', 0, 0), 
(83, 'e004040018fb18be', 'F1 Scope83', '5600820', 1, 181, 1, '', 0, 0), 
(84, 'e004040019fb18be', 'F1 Scope84', '5600821', 1, 181, 1, '', 0, 0), 
(85, 'e004040022fb18be', 'F1 Scope85', '5600834', 1, 181, 1, '', 0, 0), 
(86, 'e004050023fb18be', 'F1 Scope86', '6600835', 1, 181, 1, '', 0, 0), 
(87, 'e004050014fb18be', 'F1 Scope87', '6600816', 1, 181, 1, '', 0, 0), 
(88, 'e004050033fb18be', 'F1 Scope88', '6600825', 1, 181, 1, '', 0, 0), 
(89, 'e004050034fb18be', 'F1 Scope89', '6600826', 1, 181, 1, '', 0, 0), 
(90, 'e004050015fb18be', 'F1 Scope90', '6600817', 1, 181, 1, '', 0, 0), 
(91, 'e0030030135612ab', 'F1 Scope91', '8165412', 1, 86, 1, '', 0, 22),
(92, 'e004060017fb18be', 'F1 Scope92', '5500819', 1, 181, 1, '', 0, 0), 
(93, 'e004060018fb18be', 'F1 Scope93', '5500820', 1, 181, 1, '', 0, 0), 
(94, 'e004060019fb18be', 'F1 Scope94', '5500821', 1, 181, 1, '', 0, 0), 
(95, 'e004060022fb18be', 'F1 Scope95', '5500834', 1, 181, 1, '', 0, 0), 
(96, 'e004060023fb18be', 'F1 Scope96', '6500835', 1, 181, 1, '', 0, 0), 
(97, 'e004060014fb18be', 'F1 Scope97', '6500816', 1, 181, 1, '', 0, 0), 
(101, 'e0030150409251e7', 'F1 Scope101', '9122334', 1, 183, 1, '', 0, 0), 
(102, 'e003015040926c20', 'F1 Scope102', '9233445', 1, 184, 1, '', 0, 0), 
(103, 'e003015040926294', 'F1 Scope103', '9344556', 1, 181, 1, '', 0, 0), 
(104, 'e00301504092a32e', 'F1 Scope104', '9455667', 1, 96, 1, '', 0, 0),    
(105, 'e003015040926037', 'F1 Scope105', '9566778', 1, 97, 1, '', 0, 0), 
(106, 'e003015040924a80', 'F1 Scope106', '9677889', 1, 96, 1, '', 0, 0), 
(107, 'e00301504092a30f', 'F1 Scope107', '9654231', 1, 155, 1, '', 0, 0), 
(108, 'e00301504092737e', 'F1 Scope108', '9543216', 1, 170, 1, '', 0, 0), 
(109, 'e003015040926fe2', 'F1 Scope109', '8876432', 1, 14, 1, '', 0, 0), 
(110, 'e00301504092af6e', 'F1 Scope110', '9654321', 1, 155, 1, '', 0, 0), 
(111, 'e00301504092482b', 'F1 Scope111', '8988776', 1, 184, 1, '', 0, 0), 
(112, 'e00301001891a55e', 'F1 Scope112', '9808645', 1, 181, 1, '', 0, 0), 
(158, 'e003020033fb18be', 'F1 Scope158', '9600825', 1, 181, 1, '', 0, 0), 
(159, 'e003020034fb18be', 'F1 Scope159', '9600826', 1, 181, 1, '', 0, 0), 
(160, 'e003020015fb18be', 'F1 Scope160', '9600817', 1, 181, 1, '', 0, 0), 
(161, 'e003020016fb18be', 'F1 Scope161', '9600818', 1, 181, 1, '', 0, 0), 
(162, 'e003020017fb18be', 'F1 Scope162', '9600819', 1, 181, 1, '', 0, 0), 
(163, 'e003020018fb18be', 'F1 Scope163', '9600820', 1, 181, 1, '', 0, 0), 
(164, 'e003020019fb18be', 'F1 Scope164', '9600821', 1, 181, 1, '', 0, 0),
(201, 'e0040250409251e7', 'F2 Scope1', '1122335', 2, 183, 1, '', 0, 0), 
(202, 'e004025040926c20', 'F2 Scope2', '2233446', 2, 184, 1, '', 0, 0), 
(203, 'e004025040926294', 'F2 Scope3', '3344557', 2, 181, 1, '', 0, 0), 
(204, 'e00402504092a32e', 'F2 Scope4', '4455668', 2, 96, 1, '', 0, 0),    
(205, 'e004025040926037', 'F2 Scope5', '5566779', 2, 97, 1, '', 0, 0), 
(206, 'e004025040924a80', 'F2 Scope6', '6677880', 2, 96, 1, '', 0, 0), 
(207, 'e00402504092a30f', 'F2 Scope7', '7654232', 2, 155, 1, '', 0, 0), 
(208, 'e00402504092737e', 'F2 Scope8', '6543217', 2, 170, 1, '', 0, 0), 
(209, 'e004025040926fe2', 'F2 Scope9', '9876433', 2, 14, 1, '', 0, 0), 
(210, 'e00402504092af6e', 'F2 Scope10', '7654322', 2, 155, 1, '', 0, 5),
(301, 'e0040350409251e7', 'F3 Scope1', '1122336', 3, 183, 1, '', 0, 0), 
(302, 'e004035040926c20', 'F3 Scope2', '2233447', 3, 184, 1, '', 0, 0), 
(303, 'e004035040926294', 'F3 Scope3', '3344558', 3, 181, 1, '', 0, 0), 
(304, 'e00403504092a32e', 'F3 Scope4', '4455669', 3, 96, 1, '', 0, 0),    
(305, 'e004035040926037', 'F3 Scope5', '5566770', 3, 97, 1, '', 0, 0), 
(306, 'e004035040924a80', 'F3 Scope6', '6677881', 3, 96, 1, '', 0, 0), 
(307, 'e00403504092a30f', 'F3 Scope7', '7654233', 3, 155, 1, '', 0, 0), 
(308, 'e00403504092737e', 'F3 Scope8', '6543218', 3, 170, 1, '', 0, 0), 
(309, 'e004035040926fe2', 'F3 Scope9', '9876434', 3, 14, 1, '', 0, 0), 
(310, 'e00403504092af6e', 'F3 Scope10', '7654323', 3, 155, 1, '', 0, 5),
(211, 'e00402504092482b', 'F2 Scope11', '9988777', 2, 184, 1, '', 0, 2),
(212, 'e00402001891a55e', 'F2 Scope12', '2808646', 2, 181, 1, '', 0, 20),
(213, 'e004020002faf288', 'F2 Scope13', '2600843', 2, 181, 1, '', 0, 10),
(214, 'e004020010ab6695', 'F2 Scope14', '2806735', 2, 181, 1, '', 0, 130),
(216, 'e004020002fb0f38', 'F2 Scope16', '2601312', 2, 181, 1, '', 0, 11),
(224, 'e004020002faf239', 'F2 Scope24', '2600320', 2, 94, 1, '', 0, 0), 
(225, 'e004020002fafd0e', 'F2 Scope25', '2602573', 2, 196, 1, '', 0, 0),
(229, 'e004025040926e8b', 'F2 Scope29', '5556668', 2, 89, 1, '', 0, 14),
(231, 'e00302890b2d245f', 'F2 Scope31', '7778888', 2, 155, 1, '', 0, 323),
(233, 'e00402005f092678', 'F2 Scope33', '2200688', 2, 184, 1, '', 0, 23),
(234, 'e004010202fb4b21', 'F2 Scope34', '2520850', 2, 196, 1, '', 0, 23),
(235, 'e004020002fb18be', 'F2 Scope35', '2620716', 2, 181, 1, '', 0, 23),
(240, 'e004020005fb18be', 'F2 Scope40', '2620718', 2, 181, 1, '', 0, 0),
(241, 'e004020006fb18be', 'F2 Scope41', '2620719', 2, 181, 1, '', 0, 0),
(311, 'e00403504092482b', 'F3 Scope11', '9988778', 3, 184, 1, '', 0, 2),
(312, 'e00403001891a55e', 'F3 Scope12', '2808647', 3, 181, 1, '', 0, 20),
(313, 'e004030002faf288', 'F3 Scope13', '2630844', 3, 181, 1, '', 0, 10),
(314, 'e004030010ab6695', 'F3 Scope14', '2806736', 3, 181, 1, '', 0, 130),
(316, 'e004030002fb0f38', 'F3 Scope16', '2631313', 3, 181, 1, '', 0, 11),
(324, 'e004030002faf239', 'F3 Scope24', '2630321', 3, 94, 1, '', 0, 0),
(325, 'e004030002fafd0e', 'F3 Scope25', '2632574', 3, 196, 1, '', 0, 0),
(329, 'e004035040926e8b', 'F3 Scope29', '5556669', 3, 89, 1, '', 0, 14),
(331, 'e00303890b2d245f', 'F3 Scope31', '7778891', 3, 155, 1, '', 0, 323),
(333, 'e00403005f092678', 'F3 Scope33', '2200689', 3, 184, 1, '', 0, 23),
(334, 'e004010302fb4b21', 'F3 Scope34', '2530850', 3, 196, 1, '', 0, 23), 
(335, 'e004030062fb18be', 'F3 Scope35', '2630717', 3, 181, 1, '', 0, 23),
(340, 'e004030005fb18bf', 'F3 Scope40', '2630719', 3, 181, 1, '', 0, 0),
(341, 'e004030006fb18be', 'F3 Scope41', '2630720', 3, 181, 1, '', 0, 0);
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
(12,4,'Physician12','Physician12','MD12',1,'True'),
(13,4,'Physician13','Physician13','MD13',1,'True'),
(14,4,'Physician14','Physician14','MD14',1,'True'),
(15,4,'Physician15','Physician15','MD15',1,'True'),
(16,4,'Physician16','Physician16','MD16',1,'True'),
(17,4,'Physician17','Physician17','MD17',1,'True'),
(18,4,'Physician18','Physician18','MD18',1,'True'),
(19,4,'Physician19','Physician19','MD19',1,'False'),
(20,4,'Physician20','Physician20','MD20',1,'False'),
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
(15, 'Repair shop 1', 1, 1),
(16, 'Facility 4', 1, 1),
(17, 'Blue', 4, 1),
(18, 'Red', 4, 1),
(20, 'MRC Fail', 3, 1), 
(21, 'Received Loaner Scope', 3, 1),
(23,'Custom Reason1', 3, 1),
(24,'Custom Reason2', 3, 1),
(25,'Custom Reason3', 3, 1),
(26, 'Repair shop 2', 1, 1);
SET IDENTITY_INSERT Barcode OFF;

SET IDENTITY_INSERT Patient ON;
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

SET IDENTITY_INSERT Patient OFF;

SET IDENTITY_INSERT dbo.keyentryScans ON;
Insert into dbo.keyentryScans  (keyentryid_pk,keyentryvalue) Values
(1,1),
(2,2),
(3,3),
(4,4);
SET IDENTITY_INSERT keyentryScans OFF;