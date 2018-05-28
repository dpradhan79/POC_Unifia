package TestFrameWork.UnifiaAdminGeneralFunctions;

public class dashboardpage {
	//Dashboard objects
	// General to all
	public static String noRecordsInPopup="no Records";
	public static String selectFacility="//*[@id='ddlSelectedFacility']";
	//Exam Queue
	public static String dashBoardTab="//a[@href='/DailyDashboard/DailyDashboard']";
	public static String ipTab="//a[@href='/InfectionPrevention/InfectionPrevention']";
	public static String ipDashboard ="//a[@href='/InfectionPrevention/InfectionPrevention/Dashboard']";
	public static String ipSRM ="//a[@href='/InfectionPrevention/InfectionPrevention/ScopeRecordManagement']";
	public static String ipMRC ="//a[@href='/InfectionPrevention/InfectionPrevention/MrcRecordManagement']";
	public static String mamTab ="//a[contains(text(),'Materials & Asset Management')]";
	
	public static String sshLabel="/html/body/div[1]/div/div[2]/div/label";
	public static String analysisTab="//a[@href='/Analysis/Analysis']";
	public static String auditTab="//a[@href='/AuditLog/AuditLog']";
	public static String mrceditLink="//*[@id='mrcGrid']/div[2]/table/tbody/tr[1]/td[5]/input";
	public static String mrceditbutton="//*[@id='mrcGrid']/div[2]/table/tbody/tr[2]/td[5]/following::div//a[contains(@title,'Edit Row')]";
	public static String auditlogTable="//*[@id='gview_jqgrid_audit_log']/div[1]";
									
	public static String examQueueHeaderDDB="//*[@id='divExamQueue']/div[1]";
	public static String examQueuePatientsDDB="//*[@id='eqTotalPatients']";
	public static String examQueueExamsDDB="//*[@id='eqTotalExams']";
	public static String examType1="//*[@id='examTypeTitle1']";
	public static String examType2="//*[@id='examTypeTitle2']";
	public static String examType1Count="//*[@id='examTypeCount1']";
	public static String examType2Count="//*[@id='examTypeCount2']";
	
	public static String examQueueTableContent="//*[@id='eqPopover']";

	//Procedure Room
	public static String PR1Name="//*[@id='prName21']";
	public static String PR2Name="//*[@id='prName22']";
	public static String PR3Name="//*[@id='prName23']";
	public static String PR5Name="//*[@id='prName25']";
	
	public static String PR1Name2="//*[@id='prName121']";
	public static String PR2Name2="//*[@id='prName122']";
	public static String PR3Name2="//*[@id='prName123']";
	public static String PR5Name2="//*[@id='prName125']";

	public static String PR1Name3="//*[@id='prName221']";
	public static String PR2Name3="//*[@id='prName222']";
	public static String PR3Name3="//*[@id='prName223']";
	public static String PR5Name3="//*[@id='prName225']";
	

	public static String PR1color="//*[@id='prBox21']/div[1]";
	public static String PR2color="//*[@id='prBox22']/div[1]";
	public static String PR3color="//*[@id='prBox23']/div[1]";
	public static String PR5color="//*[@id='prBox25']/div[1]";
	
	public static String PR1color2="//*[@id='prBox121']/div[1]";
	public static String PR2color2="//*[@id='prBox122']/div[1]";
	public static String PR3color2="//*[@id='prBox123']/div[1]";
	public static String PR5color2="//*[@id='prBox125']/div[1]";
	
	public static String PR1color3="//*[@id='prBox221']/div[1]";
	public static String PR2color3="//*[@id='prBox222']/div[1]";
	public static String PR3color3="//*[@id='prBox223']/div[1]";
	public static String PR5color3="//*[@id='prBox225']/div[1]";

	public static String PR1Status="//*[@id='prStat21']";
	public static String PR2Status="//*[@id='prStat22']";
	public static String PR3Status="//*[@id='prStat23']";
	public static String PR5Status="//*[@id='prStat25']";

	public static String PR1Status2="//*[@id='prStat121']";
	public static String PR2Status2="//*[@id='prStat122']";
	public static String PR3Status2="//*[@id='prStat123']";
	public static String PR5Status2="//*[@id='prStat125']";
	
	public static String PR1Status3="//*[@id='prStat221']";
	public static String PR2Status3="//*[@id='prStat222']";
	public static String PR3Status3="//*[@id='prStat223']";
	public static String PR5Status3="//*[@id='prStat225']";
	
	public static String PR1Scopes="//*[@id='prScopes21']";
	public static String PR2Scopes="//*[@id='prScopes22']";
	public static String PR3Scopes="//*[@id='prScopes23']";
	public static String PR5Scopes="//*[@id='prScopes25']";
	
	public static String PR1Scopes2="//*[@id='prScopes121']";
	public static String PR2Scopes2="//*[@id='prScopes122']";
	public static String PR3Scopes2="//*[@id='prScopes123']";
	public static String PR5Scopes2="//*[@id='prScopes125']";
	
	public static String PR1Scopes3="//*[@id='prScopes221']";
	public static String PR2Scopes3="//*[@id='prScopes222']";
	public static String PR3Scopes3="//*[@id='prScopes223']";
	public static String PR5Scopes3="//*[@id='prScopes225']";
	
	public static String PRTableContent="//*[@id='prTableContent']";
	
	//Soiled Room
	public static String Sink1Name="//*[@id='srBox41']/div[1]";
	public static String Sink2Name="//*[@id='srBox42']/div[1]";
	
	public static String Sink1Name2="//*[@id='srBox141']/div[1]";
	public static String Sink2Name2="//*[@id='srBox142']/div[1]";
	
	public static String Sink1Name3="//*[@id='srBox241']/div[1]";
	public static String Sink2Name3="//*[@id='srBox242']/div[1]";
	
	public static String Sink1Scopes="//*[@id='srScopes41']";
	public static String Sink2Scopes="//*[@id='srScopes42']";
	
	public static String Sink1Scopes2="//*[@id='srScopes141']";
	public static String Sink2Scopes2="//*[@id='srScopes142']";
	
	public static String Sink1Scopes3="//*[@id='srScopes241']";
	public static String Sink2Scopes3="//*[@id='srScopes242']";
	
	//Cabinets(Storage Area)
	
	public static String sATableContent="//*[@id='cabTableContent']";
	public static String cultHoldCabinetName="//*[@id='cabName35-1']";
	public static String cultHoldTotalCount="//*[@id='cabTotal35-1']";
	public static String cultHoldApproachCount="//*[@id='cabApproach35-1']";
	public static String cultHoldExceedCount="//*[@id='cabExceed35-1']";
	
	public static String cultHoldCabinetName2="//*[@id='cabName135-1']";
	public static String cultHoldTotalCount2="//*[@id='cabTotal135-1']";
	public static String cultHoldApproachCount2="//*[@id='cabApproach135-1']";
	public static String cultHoldExceedCount2="//*[@id='cabExceed135-1']";
	
	public static String cultHoldCabinetName3="//*[@id='cabName235-1']";
	public static String cultHoldTotalCount3="//*[@id='cabTotal235-1']";
	public static String cultHoldApproachCount3="//*[@id='cabApproach235-1']";
	public static String cultHoldExceedCount3="//*[@id='cabExceed235-1']";

	public static String sA1Name="//*[@id='cabName31-1']";
	public static String sA1TotalCount="//*[@id='cabTotal31-1']";
	public static String sA1ApproachCount="//*[@id='cabApproach31-1']";
	public static String sA1ExceedCount="//*[@id='cabExceed31-1']";
	
	public static String sA1Name2="//*[@id='cabName131-1']";
	public static String sA1TotalCount2="//*[@id='cabTotal131-1']";
	public static String sA1ApproachCount2="//*[@id='cabApproach131-1']";
	public static String sA1ExceedCount2="//*[@id='cabExceed131-1']";
	
	
	public static String sA1Name3="//*[@id='cabName231-1']";
	public static String sA1TotalCount3="//*[@id='cabTotal231-1']";
	public static String sA1ApproachCount3="//*[@id='cabApproach231-1']";
	public static String sA1ExceedCount3="//*[@id='cabExceed231-1']";
	
	
	public static String sA2Name="//*[@id='cabName31-2']";
	public static String sA2TotalCount="//*[@id='cabTotal31-2']";
	public static String sA2ApproachCount="//*[@id='cabApproach31-2']";
	public static String sA2ExceedCount="//*[@id='cabExceed31-2']";
	
	public static String sA2Name2="//*[@id='cabName131-2']";
	public static String sA2TotalCount2="//*[@id='cabTotal131-2']";
	public static String sA2ApproachCount2="//*[@id='cabApproach131-2']";
	public static String sA2ExceedCount2="//*[@id='cabExceed131-2']";
	
	
	public static String sA2Name3="//*[@id='cabName231-2']";
	public static String sA2TotalCount3="//*[@id='cabTotal231-2']";
	public static String sA2ApproachCount3="//*[@id='cabApproach231-2']";
	public static String sA2ExceedCount3="//*[@id='cabExceed231-2']";
	
	public static String sA3Name="//*[@id='cabName31-3']";
	public static String sA3TotalCount="//*[@id='cabTotal31-3']";
	public static String sA3ApproachCount="//*[@id='cabApproach31-3']";
	public static String sA3ExceedCount="//*[@id='cabExceed31-3']";
	
	public static String sA3Name2="//*[@id='cabName131-3']";
	public static String sA3TotalCount2="//*[@id='cabTotal131-3']";
	public static String sA3ApproachCount2="//*[@id='cabApproach131-3']";
	public static String sA3ExceedCount2="//*[@id='cabExceed131-3']";
	
	
	public static String sA3Name3="//*[@id='cabName231-3']";
	public static String sA3TotalCount3="//*[@id='cabTotal231-3']";
	public static String sA3ApproachCount3="//*[@id='cabApproach231-3']";
	public static String sA3ExceedCount3="//*[@id='cabExceed231-3']";
	
	
	public static String sA4Name="//*[@id='cabName31-4']";
	public static String sA4TotalCount="//*[@id='cabTotal31-4']";
	public static String sA4ApproachCount="//*[@id='cabApproach31-4']";
	public static String sA4ExceedCount="//*[@id='cabExceed31-4']";
	
	public static String sA4Name2="//*[@id='cabName131-4']";
	public static String sA4TotalCount2="//*[@id='cabTotal131-4']";
	public static String sA4ApproachCount2="//*[@id='cabApproach131-4']";
	public static String sA4ExceedCount2="//*[@id='cabExceed131-4']";
	
	public static String sA4Name3="//*[@id='cabName231-4']";
	public static String sA4TotalCount3="//*[@id='cabTotal231-4']";
	public static String sA4ApproachCount3="//*[@id='cabApproach231-4']";
	public static String sA4ExceedCount3="//*[@id='cabExceed231-4']";
	
	//Awaiting Manual Cleaning
	public static String amcName="//*[@id='amcPanel']/div[1]/span";
	public static String amcScopesCount="//*[@id='amcNumScopes']"; 
	public static String amcColor="//*[@id='amcPanel']";
	public static String amcTableContent="//*[@id='amcTableContent']";
	public static String amcScopeState="3";
	
	//Awaiting Reprocessing
	public static String arName="//*[@id='arPanel']/div[1]/span";
	public static String arScopesCount="//*[@id='arNumScopes']";
	public static String arColor="//*[@id='arPanel']";
	public static String arTableContent="//*[@id='arTableContent']";
	public static String arScopeState="2";
	
	
	//Out of Facility
	public static String oofName="//*[@id='colOutOfFacility']/div[1]";
	public static String oofScopesCount="//*[@id='oofNumScopes']";
	public static String oofTableContent="//*[@id='oofTableContent']";
	public static String oofScopeState="1";
	public static String oofCycleEvent="21";
	public static String scanItemTypeId="12";
	
	//Reprocessor without KE
	public static String rep1Name="//*[@id='reprocBox51']/div[1]";
	public static String rep1ScopeName="//*[@id='reprocScopes51']";
	public static String rep2Name="//*[@id='reprocBox52']/div[1]";
	public static String rep2ScopeName="//*[@id='reprocScope35']";
	public static String rep4Name="//*[@id='reprocBox54']/div[1]";
	public static String rep4ScopeName1="//*[@id='reprocScope24']";
	public static String rep4ScopeName2="//*[@id='reprocScope33']";
	public static String rep5Name="//*[@id='reprocBox55']/div[1]";
	public static String rep6Name="//*[@id='reprocBox56']/div[1]";
	
	public static String rep1Name2="//*[@id='reprocBox151']/div[1]";
	public static String rep1ScopeName2="//*[@id='reprocScopes151']";
	public static String rep2Name2="//*[@id='reprocBox152']/div[1]";
	public static String rep2ScopeName2="//*[@id='reprocScope235']";
	public static String rep4Name2="//*[@id='reprocBox154']/div[1]";
	public static String rep4ScopeName12="//*[@id='reprocScope224']";
	public static String rep4ScopeName22="//*[@id='reprocScope233']";
	public static String rep5Name2="//*[@id='reprocBox=155']/div[1]";
	public static String rep6Name2="//*[@id='reprocBox156']/div[1]";
	
	
	public static String rep1Name3="//*[@id='reprocBox251']/div[1]";
	public static String rep1ScopeName3="//*[@id='reprocScopes251']";
	public static String rep2Name3="//*[@id='reprocBox252']/div[1]";
	public static String rep2ScopeName3="//*[@id='reprocScope335']";
	public static String rep4Name3="//*[@id='reprocBox254']/div[1]";
	public static String rep4ScopeName13="//*[@id='reprocScope324']";
	public static String rep4ScopeName23="//*[@id='reprocScope333']";
	public static String rep5Name3="//*[@id='reprocBox255']/div[1]";
	public static String rep6Name3="//*[@id='reprocBox256']/div[1]";
	
	//Reprocessor with KE
	public static String KERep1ScopeName1="//*[@id='reprocScope96']";
	public static String KERep1ScopeName2="//*[@id='reprocScope97']";
	public static String KERep2ScopeName1="//*[@id='reprocScope35']";
	public static String KERep2ScopeName2="//*[@id='reprocScope46']";
	public static String KERep4ScopeName1="//*[@id='reprocScope89']";
	public static String KERep4ScopeName1Multi="//*[@id='reprocScope33']";
	public static String KERep4ScopeName2="//*[@id='reprocScope90']";
	public static String KERep5ScopeName1="//*[@id='reprocScope92']";
	public static String KERep5ScopeName2="//*[@id='reprocScope93']";
	public static String KERep6ScopeName1="//*[@id='reprocScope94']";
	public static String KERep6ScopeName2="//*[@id='reprocScope95']";
	//ReprocessorStatus
	public static String KERep1Status ="//*[@id='reprocStat51']";
	public static String KERep2Status ="//*[@id='reprocStat52']";
	public static String KERep4Status ="//*[@id='reprocStat54']";
	public static String KERep5Status ="//*[@id='reprocStat55']";
	public static String KERep6Status ="//*[@id='reprocStat56']";
	//Reprocessor color
	public static String KERep1color="//*[@id='reprocBox51']/div[1]";
	public static String KERep2color="//*[@id='reprocBox52']/div[1]";
	public static String KERep4color="//*[@id='reprocBox54']/div[1]";
	public static String KERep5color="//*[@id='reprocBox55']/div[1]";
	public static String KERep6color="//*[@id='reprocBox56']/div[1]";

	//Move right
	public static String cabNext="//*[@id='cabNext']";
	public static String reproNext="//*[@id='reprocNext']";
	public static String cabPrev="//*[@id='cabPrev']";
	public static String prNext="//*[@id='prNext']";
	public static String srNext="//*[@id='srNext']";
	
	
	//Data
	//Exam Queue
	public static String expPatientCount="2";
	public static String expExamCount="3";
	public static String expExamType1="Colon";
	public static String expExamType2="EGD";
	public static String expExamType1Count="2";
	public static String expExamType2Count="1";
	
	//Procedure Room
	public static String expPR1Name="Procedure Room 1";
	public static String expPR2Name="Procedure Room 2";
	public static String expPR3Name="Procedure Room 3";
	public static String expPR5Name="Procedure Room 5";

	public static String expPRAvailable="Available";
	public static String expPRInUse="In Use";
	public static String expPRNeedsCleaning="Needs Cleaning";
	public static String expPRClosed="Closed";

	public static String expPR1Scopes="";
	public static String expPR2Scopes="Scope16";
	public static String expPR3Scopes="";
	public static String expPR5Scopes="";
	
	public static String expPRAvailableColor="rgba(137, 187, 64, 1)"; 
	public static String expPRInUseColor="rgba(134, 92, 161, 1)"; 
	public static String expPRNeedsCleaningColor="rgba(239, 113, 65, 1)"; 
	public static String expPRClosedColor="rgba(82, 82, 92, 1)"; 

	//Soiled Room
	public static String expSink1Name="Sink 1";
	public static String expSink2Name="Sink 2";
	
	public static String expSink1Scopes="-";
	public static String expSink2Scopes="Scope11";
	
	//Awaiting Manual Cleaning
	public static String expAMCName="Awaiting Manual Cleaning";
	public static String expAMCScopesCount="2";
	public static String expAMCBelow45minColor="rgba(202, 225, 241, 1)";
	public static String expAMCBetween45n59mnsColor="rgba(247, 205, 147, 1)";
	public static String expAMCGreaterThan59mnsColor="rgba(250, 198, 199, 1)";
	
	//Awaiting Reprocessing
	public static String expARName="Awaiting Reprocessing";
	public static String expARScopesCount="3";
	public static String expARBelow45minColor="rgba(202, 225, 241, 1)";
	public static String expARBetween45n59mnsColor="rgba(247, 205, 147, 1)";
	public static String expARGreaterThan59mnsColor="rgba(250, 198, 199, 1)";
	
	//Out of Facility
	public static String expOOFName="Out of Facility";
	public static String expOOFScopeCount="3";
	
	//Cabinets (Storage Areaa)
	public static String expCultHoldName="Culture Hold Cabinet-1";
	public static String expSA1Name="Storage Area A-1";
	public static String expSA2Name="Storage Area A-2";
	public static String expSA3Name="Storage Area A-3";
	public static String expSA4Name="Storage Area A-4";
	
	public static String expScopeCultHoldTotal="0";
	public static String expScopeCultHoldApproach="0";
	public static String expScopeCultHoldExceed="0";
	
	public static String expSA1ScopeTotal="2";
	public static String expSA1ScopeAppro="0";
	public static String expSA1ScopeExceed="0";
	
	public static String expSA2ScopeTotal="3";
	public static String expSA2ScopeAppro="1";
	public static String expSA2ScopeExceed="0";
	
	public static String expSA3ScopeTotal="2";
	public static String expSA3ScopeAppro="0";
	public static String expSA3ScopeExceed="1";
	
	public static String expSA4ScopeTotal="2";
	public static String expSA4ScopeAppro="1";
	public static String expSA4ScopeExceed="1";
	
	public static String expCabNormalColorGreen="rgba(137, 187, 64, 1)"; 
	public static String expCabApproachColorAmber="rgba(239, 113, 65, 1)"; 
	public static String expCabExceedColorRed="rgba(209, 51, 56, 1)";
		
	//Reprocessors 
	public static String expRepro1Name="Reprocessor 1";
	public static String expRepro2Name="Reprocessor 2";
	public static String expRepro4Name="Reprocessor 4";
	public static String expRepro5Name="Reprocessor 5";
	public static String expRepro6Name="Reprocessor 6";

	public static String expScopeRepro1="";
	public static String expScopeRepro2="Scope35";
	public static String expScopeRepro4First="Scope24";
	public static String expScopeRepro4Second="Scope33";
	
	//KE Reprocessors
	public static String KEexpScope1Repro1="Scope96";
	public static String KEexpScope2Repro1="Scope97";
	public static String KEexpScope1Repro2="Scope35";
	public static String KEexpScope2Repro2="Scope46";
	public static String KEexpScope1Repro4="Scope89";
	public static String KEexpScope2Repro4="Scope90";
	public static String KEexpScope1Repro5="Scope92";
	public static String KEexpScope2Repro5="Scope93";
	public static String KEexpScope1Repro6="Scope94";
	public static String KEexpScope2Repro6="Scope95";
	
	public static String KEexpStatusRepro1="Available";
	public static String KEexpStatusRepro2="Unexpected Termination";
	public static String KEexpStatusRepro4="Processing";
	public static String KEexpStatusRepro5="Processing Complete";
	public static String KEexpStatusRepro6="Unavailable";
	
	public static String KEexpRepAvailableColorGreen="rgba(137, 187, 64, 1)"; 
	public static String KEexpRepUnExpTermColorAmber="rgba(239, 113, 65, 1)"; 
	public static String KEexpRepProcessingColorPurple="rgba(134, 92, 161, 1)";
	public static String KEexpRepProcessingColorblack="rgba(82, 82, 92, 1)";
	
	//RegressionHappyPath
	public static String Cabinet="1";
	
	//RegressionKEHappyPath
	public static String KEexpReproStatusUnAva="Unavailable";
	public static String KEexpReproStatusProc="Processing";
	public static String KEexpReproStatusProcComp="Processing Complete";
	public static String KEexpReproStatusAva="Available";
	
	//SRM screen related
	public static String rgbOfCompletedFlow="rgb(137, 187, 64)";
	public static String Green="Green";
	public static String rgbOfIncompleteFlow="rgb(233, 178, 38)";
	public static String Yellow="Yellow";
	public static String addWorkFlowBtn="//*[@id='addWorkFlowLink']";
	public static String error="//*[@id='errorSection']/li";
	public static String scopeSerNum="//*[@id='ScopeSrNo']";
	public static String refNum="//*[@id='details_2-2']";
	
	//Analysis
	public static String averageTimes="//td[contains(text(),'AVERAGE TIMES')]";
	public static String clear="//td[contains(text(),'CLEAR')]";
	
	//multifacility Data
	//Exam Queue
	public static String expPatientCountMulti="2";
	public static String expExamCountMulti="3";
	public static String expExamType1Multi="Colon";
	public static String expExamType2Multi="EGD";
	public static String expExamType1CountMulti="2";
	public static String expExamType2CountMulti="1";
	
	//Procedure Room
	public static String expPR1NameMulti="F1 Procedure Room 1";
	public static String expPR2NameMulti="F1 Procedure Room 2";
	public static String expPR3NameMulti="F1 Procedure Room 3";
	public static String expPR5NameMulti="F1 Procedure Room 5";
	
	public static String expPR1NameMulti2="F2 Procedure Room 1";
	public static String expPR2NameMulti2="F2 Procedure Room 2";
	public static String expPR3NameMulti2="F2 Procedure Room 3";
	public static String expPR5NameMulti2="F2 Procedure Room 5";
	
	public static String expPR1NameMulti3="F3 Procedure Room 1";
	public static String expPR2NameMulti3="F3 Procedure Room 2";
	public static String expPR3NameMulti3="F3 Procedure Room 3";
	public static String expPR5NameMulti3="F3 Procedure Room 5";

	public static String expPRAvailableMulti="Available";
	public static String expPRInUseMulti="In Use";
	public static String expPRNeedsCleaningMulti="Needs Cleaning";
	public static String expPRClosedMulti="Closed";
	
	public static String expPRAvailableMulti2="Available";
	public static String expPRInUseMulti2="In Use";
	public static String expPRNeedsCleaningMulti2="Needs Cleaning";
	public static String expPRClosedMulti2="Closed";
	
	public static String expPRAvailableMulti3="Available";
	public static String expPRInUseMulti3="In Use";
	public static String expPRNeedsCleaningMulti3="Needs Cleaning";
	public static String expPRClosedMulti3="Closed";

	public static String expPR1ScopesMulti="";
	public static String expPR2ScopesMulti="F1 Scope16";
	public static String expPR3ScopesMulti="";
	public static String expPR5ScopesMulti="";
	
	public static String expPR1ScopesMulti2="";
	public static String expPR2ScopesMulti2="F2 Scope16";
	public static String expPR3ScopesMulti2="";
	public static String expPR5ScopesMulti2="";
	
	public static String expPR1ScopesMulti3="";
	public static String expPR2ScopesMulti3="F3 Scope16";
	public static String expPR3ScopesMulti3="";
	public static String expPR5ScopesMulti3="";
	
	public static String expPRAvailableColorMulti="rgba(137, 187, 64, 1)"; 
	public static String expPRInUseColorMulti="rgba(134, 92, 161, 1)"; 
	public static String expPRNeedsCleaningColorMulti="rgba(239, 113, 65, 1)"; 
	public static String expPRClosedColorMulti="rgba(82, 82, 92, 1)"; 
	
	
	public static String expPRAvailableColorMulti2="rgba(137, 187, 64, 1)"; 
	public static String expPRInUseColorMulti2="rgba(134, 92, 161, 1)"; 
	public static String expPRNeedsCleaningColorMulti2="rgba(239, 113, 65, 1)"; 
	public static String expPRClosedColorMulti2="rgba(82, 82, 92, 1)"; 
	
	
	public static String expPRAvailableColorMulti3="rgba(137, 187, 64, 1)"; 
	public static String expPRInUseColorMulti3="rgba(134, 92, 161, 1)"; 
	public static String expPRNeedsCleaningColorMulti3="rgba(239, 113, 65, 1)"; 
	public static String expPRClosedColorMulti3="rgba(82, 82, 92, 1)"; 

	//Soiled Room
	public static String expSink1NameMulti="F1 Sink 1";
	public static String expSink2NameMulti="F1 Sink 2";
	
	public static String expSink1NameMulti2="F2 Sink 1";
	public static String expSink2NameMulti2="F2 Sink 2";
	
	public static String expSink1NameMulti3="F3 Sink 1";
	public static String expSink2NameMulti3="F3 Sink 2";
	
	public static String expSink1ScopesMulti="-";
	public static String expSink2ScopesMulti="F1 Scope11";
	
	public static String expSink1ScopesMulti2="-";
	public static String expSink2ScopesMulti2="F2 Scope11";
	
	public static String expSink1ScopesMulti3="-";
	public static String expSink2ScopesMulti3="F3 Scope11";
	
	//Awaiting Manual Cleaning
	public static String expAMCNameMulti="Awaiting Manual Cleaning";
	public static String expAMCScopesCountMulti="2";
	public static String expAMCBelow45minColorMulti="rgba(202, 225, 241, 1)";
	public static String expAMCBetween45n59mnsColorMulti="rgba(247, 205, 147, 1)";
	public static String expAMCGreaterThan59mnsColorMulti="rgba(250, 198, 199, 1)";
	
	//Awaiting Reprocessing
	public static String expARNameMulti="Awaiting Reprocessing";
	public static String expARScopesCountMulti="3";
	public static String expARBelow45minColorMulti="rgba(202, 225, 241, 1)";
	public static String expARBetween45n59mnsColorMulti="rgba(247, 205, 147, 1)";
	public static String expARGreaterThan59mnsColorMulti="rgba(250, 198, 199, 1)";
	
	//Out of Facility
	public static String expOOFNameMulti="Out of Facility";
	public static String expOOFScopeCountMulti="2";
	
	//Storage area	
	public static String expCultHoldNameMulti="F1 Culture Hold Cabinet-1";
	public static String expSA1NameMulti="F1 Storage Area A-1";
	public static String expSA2NameMulti="F1 Storage Area A-2";
	public static String expSA3NameMulti="F1 Storage Area A-3";
	public static String expSA4NameMulti="F1 Storage Area A-4";
	
	public static String expCultHoldNameMulti2="F2 Culture Hold Cabinet-1";
	public static String expSA1NameMulti2="F2 Storage Area A-1";
	public static String expSA2NameMulti2="F2 Storage Area A-2";
	public static String expSA3NameMulti2="F2 Storage Area A-3";
	public static String expSA4NameMulti2="F2 Storage Area A-4";
	
	public static String expCultHoldNameMulti3="F3 Culture Hold Cabinet-1";
	public static String expSA1NameMulti3="F3 Storage Area A-1";
	public static String expSA2NameMulti3="F3 Storage Area A-2";
	public static String expSA3NameMulti3="F3 Storage Area A-3";
	public static String expSA4NameMulti3="F3 Storage Area A-4";
	
	
	public static String expScopeCultHoldTotalMulti="0";
	public static String expScopeCultHoldApproachMulti="0";
	public static String expScopeCultHoldExceedMulti="0";

	public static String expScopeCultHoldTotalMulti2="0";
	public static String expScopeCultHoldApproachMulti2="0";
	public static String expScopeCultHoldExceedMulti2="0";
	
	public static String expScopeCultHoldTotalMulti3="0";
	public static String expScopeCultHoldApproachMulti3="0";
	public static String expScopeCultHoldExceedMulti3="0";
	
	
	public static String expSA1ScopeTotalMulti="2";
	public static String expSA1ScopeApproMulti="0";
	public static String expSA1ScopeExceedMulti="0";
	
	public static String expSA1ScopeTotalMulti2="2";
	public static String expSA1ScopeApproMulti2="0";
	public static String expSA1ScopeExceedMulti2="0";
	
	public static String expSA1ScopeTotalMulti3="2";
	public static String expSA1ScopeApproMulti3="0";
	public static String expSA1ScopeExceedMulti3="0";
	
	public static String expSA2ScopeTotalMulti="3";
	public static String expSA2ScopeApproMulti="1";
	public static String expSA2ScopeExceedMulti="0";
	
	public static String expSA2ScopeTotalMulti2="3";
	public static String expSA2ScopeApproMulti2="1";
	public static String expSA2ScopeExceedMulti2="0";
	
	public static String expSA2ScopeTotalMulti3="3";
	public static String expSA2ScopeApproMulti3="1";
	public static String expSA2ScopeExceedMulti3="0";
	
	public static String expSA3ScopeTotalMulti="2";
	public static String expSA3ScopeApproMulti="0";
	public static String expSA3ScopeExceedMulti="0";
	
	public static String expSA3ScopeTotalMulti2="2";
	public static String expSA3ScopeApproMulti2="0";
	public static String expSA3ScopeExceedMulti2="0";
	
	public static String expSA3ScopeTotalMulti3="2";
	public static String expSA3ScopeApproMulti3="0";
	public static String expSA3ScopeExceedMulti3="1";
	
	public static String expSA4ScopeTotalMulti="2";
	public static String expSA4ScopeApproMulti="0";
	public static String expSA4ScopeExceedMulti="1";
	
	public static String expSA4ScopeTotalMulti2="2";
	public static String expSA4ScopeApproMulti2="0";
	public static String expSA4ScopeExceedMulti2="1";
	
	public static String expSA4ScopeTotalMulti3="2";
	public static String expSA4ScopeApproMulti3="1";
	public static String expSA4ScopeExceedMulti3="1";
	
	public static String expCabNormalColorGreenMulti="rgba(137, 187, 64, 1)"; 
	public static String expCabApproachColorAmberMulti="rgba(239, 113, 65, 1)"; 
	public static String expCabExceedColorRedMulti="rgba(209, 51, 56, 1)";
	
	public static String expCabNormalColorGreenMulti2="rgba(137, 187, 64, 1)"; 
	public static String expCabApproachColorAmberMulti2="rgba(239, 113, 65, 1)"; 
	public static String expCabExceedColorRedMulti2="rgba(209, 51, 56, 1)";
	
	public static String expCabNormalColorGreenMulti3="rgba(137, 187, 64, 1)"; 
	public static String expCabApproachColorAmberMulti3="rgba(239, 113, 65, 1)"; 
	public static String expCabExceedColorRedMulti3="rgba(209, 51, 56, 1)";
	
	//Reprocessors 
	public static String expRepro1NameMulti="F1 Reprocessor 1";
	public static String expRepro2NameMulti="F1 Reprocessor 2";
	public static String expRepro4NameMulti="F1 Reprocessor 4";
	public static String expRepro5NameMulti="F1 Reprocessor 5";
	public static String expRepro6NameMulti="F1 Reprocessor 6";
	
	public static String expRepro1NameMulti2="F2 Reprocessor 1";
	public static String expRepro2NameMulti2="F2 Reprocessor 2";
	public static String expRepro4NameMulti2="F2 Reprocessor 4";
	public static String expRepro5NameMulti2="F2 Reprocessor 5";
	public static String expRepro6NameMulti2="F2 Reprocessor 6";
	
	public static String expRepro1NameMulti3="F3 Reprocessor 1";
	public static String expRepro2NameMulti3="F3 Reprocessor 2";
	public static String expRepro4NameMulti3="F3 Reprocessor 4";
	public static String expRepro5NameMulti3="F3 Reprocessor 5";
	public static String expRepro6NameMulti3="F3 Reprocessor 6";


	public static String expScopeRepro1Multi="";
	public static String expScopeRepro2Multi="F1 Scope35";
	public static String expScopeRepro4FirstMulti="F1 Scope24";
	public static String expScopeRepro4SecondMulti="F1 Scope33";
	
	public static String expScopeRepro1Multi2="";
	public static String expScopeRepro2Multi2="F2 Scope35";
	public static String expScopeRepro4FirstMulti2="F2 Scope24";
	public static String expScopeRepro4SecondMulti2="F2 Scope33";
	
	
	public static String expScopeRepro1Multi3="";
	public static String expScopeRepro2Multi3="F3 Scope35";
	public static String expScopeRepro4FirstMulti3="F3 Scope24";
	public static String expScopeRepro4SecondMulti3="F3 Scope33";
	
	//KE Reprocessors
	public static String KEexpScope1Repro1Multi="F1 Scope96";
	public static String KEexpScope2Repro1Multi="F1 Scope97";
	public static String KEexpScope1Repro2Multi="F1 Scope35";
	public static String KEexpScope2Repro2Multi="F1 Scope46";
	public static String KEexpScope1Repro4Multi="F1 Scope33";
	public static String KEexpScope2Repro4Multi="F1 Scope89";
	public static String KEexpScope1Repro5Multi="F1 Scope92";
	public static String KEexpScope2Repro5Multi="F1 Scope93";
	public static String KEexpScope1Repro6Multi="F1 Scope94";
	public static String KEexpScope2Repro6Multi="F1 Scope95";
	
	public static String KEexpStatusRepro1Multi="Available";
	public static String KEexpStatusRepro2Multi="Unexpected Termination";
	public static String KEexpStatusRepro4Multi="Processing";
	public static String KEexpStatusRepro5Multi="Processing Complete";
	public static String KEexpStatusRepro6Multi="Unavailable";
	
	public static String KEexpRepAvailableColorGreenMulti="rgba(137, 187, 64, 1)"; 
	public static String KEexpRepUnExpTermColorAmberMulti="rgba(239, 113, 65, 1)"; 
	public static String KEexpRepProcessingColorPurpleMulti="rgba(134, 92, 161, 1)";
	public static String KEexpRepProcessingColorblackMulti="rgba(82, 82, 92, 1)";
	
}

