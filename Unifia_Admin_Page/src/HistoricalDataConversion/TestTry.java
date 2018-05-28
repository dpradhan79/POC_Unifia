package HistoricalDataConversion;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

import HistoricalDataConversion.HistoryLogic;
import HistoricalDataConversion.CallDB;

public class TestTry {
	public static HistoricalDataConversion.HistoryLogic HL;
	public static int Yrs;
	public static int Mnths;
	public static int Days;
	public static String ResultDate;
	public static String ConnString;
	public static long Millis;
	public static String SQL;
	@Test
	public void Test()throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("Processing Starting:  "+dateFormat.format(date));
		Yrs=4;
		Mnths=5;
		Days=20;
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
		
	}
		
}
