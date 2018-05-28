package HistoricalDataConversion;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import HistoricalDataConversion.CallDB;
import HistoricalDataConversion.HistoryLogic;
//HHistoricalDataConversion

public class ItemHistoryRewrite {

	public static CallDB CD;
	public static HistoryLogic HL;
	public static int RecordCnt;
	public static String ConnString;
	public static int MAXID;
	public static int YrsOfData;
	public static int MnthsOfData;
	public static int DaysOfData;
	public static int ExamCnt;
	public static int[] Result;
	public static int RemainingExms;
	public static String Continue;
	public static String StartDate;
	
	public static void main(String[] args) throws ParseException {
		HistoricalDataConversion.HistoryLogic HL = null;
		int Yrs = 0;
		int Mnths = 0;
		int Days = 0;
		String ResultDate;
		String ConnString;
		long Millis;
		String SQL;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("Processing Starting:  "+dateFormat.format(date));
		ConnString="jdbc:sqlserver://10.170.93.202\\sqlexpress;databaseName=Unifia;user=unifia;password=0lympu$123";
		RecordCnt=CD.RecordCount(ConnString);
		MAXID=CD.GetMaxItemHistory(ConnString);
		ExamCnt=CD.GetExamCnt(ConnString);
		//Process the db for how much data is available
		//0-40 exams 1 day
		//40-200 exams 1 week
		//200-800 exams 1 month
		//40000 exams is a years worth of exams
		// based on 6 room facility 6 exams a day
		//260 days a year
		//This assumes a minimum of 800 exams
		if(RecordCnt==0){
			System.out.println("Error-  No Records in ItemHistory Table");
			Continue="n";
		}else if(ExamCnt>800){
			System.out.println("Error-  NotEnough Data to process");
			Continue="y";
		}else if(ExamCnt>40000){
			YrsOfData=0;
			Result=HL.MonthsOfDataCalc(ExamCnt);
			MnthsOfData=Result[0];
			DaysOfData=Result[1];
			Continue="y";
		}else{
			Result=HL.YearOfDataCalc(ExamCnt);
			YrsOfData=Result[0];
			RemainingExms=Result[1];
			Result=HL.MonthsOfDataCalc(RemainingExms);
			MnthsOfData=Result[0];
			DaysOfData=Result[1];
			Continue="y";
		}
		
		if(Continue.equals('n')){
			System.out.println("Processes Ended!!!!!!!!!");
		}else if(Continue.equals('y')){
			StartDate=HL.ReturnStartDate(YrsOfData, MnthsOfData, DaysOfData);
			ResultDate=HistoricalDataConversion.HistoryLogic.ReturnStartDate(Yrs,Mnths,Days);
			System.out.println(ResultDate);
			ConnString="jdbc:sqlserver://10.170.93.202\\sqlexpress;databaseName=Unifia;user=unifia;password=0lympu$123";
			HistoricalDataConversion.CallDB.SetMarkData(ConnString);
			HistoricalDataConversion.CallDB.CreateTempTbls(ConnString);
			HistoricalDataConversion.CallDB.SetTempTbl_NewScnDT(ConnString, ResultDate);
			HistoricalDataConversion.CallDB.AddCYCID_to_Temp_FirstPRScp(ConnString);
			SQL="Update ItemHistory Set LastupdatedDateTime= New_ScanDT FROM Temp_FirstPRScp WHERE ItemHistoryID_PK=FirstPRScpScn";
			HistoricalDataConversion.CallDB.UpdateIHLUPDTT(ConnString, SQL);
			HistoricalDataConversion.CallDB.CreateCycleDateTbl(ConnString);
			HistoricalDataConversion.CallDB.InsertFirstCycleDate(ConnString);
			HistoricalDataConversion.CallDB.NEWScanDateTimeGeneration(ConnString, ResultDate);
			//HistoricalDataConversion.CallDB.DropTempTbls(ConnString);
			SQL="Update ItemHistory Set LastUpdatedDateTime=New_ScanDt FROM Temp_FirstCycleScans WHERE ItemHistoryID_PK=ScanID_FK;";
			HistoricalDataConversion.CallDB.UpdateIHLUPDTT(ConnString, SQL);
			HistoricalDataConversion.CallDB.DeleteDataFrom_Temp_FirstCycleScans(ConnString);
			HistoricalDataConversion.CallDB.MoveRemainingIHToTemp(ConnString);
			HistoricalDataConversion.CallDB.NEWScanDateTimeGeneration(ConnString, ResultDate);
			SQL="Update ItemHistory Set LastUpdatedDateTime=New_ScanDt FROM Temp_FirstCycleScans WHERE ItemHistoryID_PK=ScanID_FK AND New_ScandT is not null;";
			HistoricalDataConversion.CallDB.UpdateIHLUPDTT(ConnString, SQL);
			HistoricalDataConversion.CallDB.DeleteDataFrom_Temp_FirstCycleScans(ConnString);
			HistoricalDataConversion.CallDB.PullAdminWRSAScans(ConnString);
			//Second Pass to see if the data can be completely correctly except for culture results
			SQL="Update ItemHistory Set LastUpdatedDateTime=New_ScanDt FROM Temp_FirstCycleScans WHERE ItemHistoryID_PK=ScanID_FK AND New_ScandT is not null;;";
			HistoricalDataConversion.CallDB.UpdateIHLUPDTT(ConnString, SQL);
			HistoricalDataConversion.CallDB.DeleteDataFrom_Temp_FirstCycleScans(ConnString);
			HistoricalDataConversion.CallDB.PullAdminWRSAScans(ConnString);
			SQL="Update ItemHistory Set LastUpdatedDateTime=New_ScanDt FROM Temp_FirstCycleScans WHERE ItemHistoryID_PK=ScanID_FK AND New_ScandT is not null;;";
			HistoricalDataConversion.CallDB.UpdateIHLUPDTT(ConnString, SQL);
			HistoricalDataConversion.CallDB.DeleteDataFrom_Temp_FirstCycleScans(ConnString);
			HistoricalDataConversion.CallDB.FinalDataManipulation(ConnString, ResultDate);
			HistoricalDataConversion.CallDB.FinalizeItemHistoryUpdate(ConnString);
			HistoricalDataConversion.CallDB.DropTempTbls(ConnString);
			
			date = new Date();
			System.out.println("Processing Ending:  "+dateFormat.format(date));
			
		}else{
			System.out.println("Unexpected Error!!!!!!!!!");
		}
		
		
		
	}
}
