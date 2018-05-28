package HistoricalDataConversion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import HistoricalDataConversion.HistoryLogic;

public class CallDB {

	public static Connection conn= null;
	static HistoricalDataConversion.HistoryLogic HL;
	
	public static int RecordCount(String ConnString){
		int Result = 0;
		String SQLstmt;
		ResultSet rs;
		SQLstmt= "Select Count(*) FROM ITEMHistory";
		
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			rs=statement.executeQuery(SQLstmt);
			while(rs.next()){
				Result=rs.getInt(1);
				
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Result;
	}
	
	public static int GetMaxItemHistory(String ConnString){
		int Result = 0;
		String SQLstmt;
		ResultSet rs;
		SQLstmt= "Select Max(ItemHistoryID_PK) FROM ITEMHistory";
		
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			rs=statement.executeQuery(SQLstmt);
			while(rs.next()){
				Result=rs.getInt(1);
				
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Result;
	}
	
	public static int GetExamCnt(String ConnString){
		int Result=0;
		String SQLstmt;
		ResultSet rs;
		SQLstmt="Select Count(*) AS Exams"+
				" FROM"+
				" (Select Distinct IH.AssociationID_FK"+
				" FROM ItemHistory IH"+
				" Join Location L on IH.LocationID_FK=L.LocationID_PK"+
				" Join LocationType LT on L.LocationTypeID_FK=LT.LocationTypeID_PK"+
				" WHERE LT.Name='Procedure Room')Ex;";
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			rs=statement.executeQuery(SQLstmt);
			while(rs.next()){
				Result=rs.getInt(1);
				
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Result;
		
	}
	
	public static void CreateTempTbls(String ConnString){
		String SQLstmt="Create Table Temp_FirstPRScp"+
				"(CycleID VarChar(25),"+
				"FirstPRScpScn int,"+
				" LocationID_FK int,"+
				" ScopeID_FK int,"+
				" NEW_ScanDT DateTime);";
		try {
		conn=DriverManager.getConnection(ConnString);
		Statement statement = conn.createStatement();
		//statement.executeQuery(SQLstmt);
		statement.execute(SQLstmt);
		conn.close();
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		String SQLstmt2="Insert into Temp_FirstPRScp"+
				" (FirstPRScpScn, LocationID_FK, ScopeID_FK)"+
				" Select SCNID.ItemHistoryID_PK, SCNID.LocationID_FK, IH.ScanItemID_FK"+
				" FROM"+
				" (Select IH.LocationID_FK,Min (IH.ItemHistoryID_PK) AS ItemHistoryID_PK"+
				" FROM ItemHistory IH"+
				" Join Location L on IH.LocationID_FK=L.LocationID_PK"+
				" Join LocationType LT on L.LocationTypeID_FK=LT.LocationTypeID_PK"+
				" Left Join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"+
				" WHERE IH.ScanItemTypeID_FK=1 AND CE.Name='Pre-Procedure' AND LT.Name='Procedure Room'"+
				" Group BY IH.LocationID_FK) SCNID"+
				" Join ItemHistory IH on SCNID.ItemHistoryID_PK=IH.ItemHistoryID_PK";
		try {
		conn=DriverManager.getConnection(ConnString);
		Statement statement = conn.createStatement();
		//statement.executeQuery(SQLstmt2);
		statement.execute(SQLstmt2);
		conn.close();
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		String SQLstmt3="Delete FROM Temp_FirstPRScp WHERE"+
				" FirstPRScpScn IN (Select DeleteList FROM"+
				" (Select Y.ScopeID_FK, Max(Y.FirstPRScpScn) AS DeleteList"+
				" FROM"+
				" (Select SCnt.ScopeID_FK"+
				" FROM (Select ScopeID_FK, Count(*) ScpCnt"+
				" FROM Temp_FirstPRScp"+
				" Group BY ScopeID_FK) SCnt"+
				" WHERE SCnt.ScpCnt>1)X"+
				" Join Temp_FirstPRScp Y on X.ScopeID_FK=Y.ScopeID_FK"+
				" Group BY Y.ScopeID_FK)DL);";
		try {
		conn=DriverManager.getConnection(ConnString);
		Statement statement = conn.createStatement();
		//statement.executeQuery(SQLstmt2);
		statement.execute(SQLstmt3);
		conn.close();
		}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
	}
	
	public static void SetTempTbl_NewScnDT(String ConnString, String StrtDt) throws ParseException{
		//Count of row to be updated
		ResultSet rs;
		int LoopCnt=0;
		String SQLstmt="Select Count(*) FROM Temp_FirstPRScp;";
		String Strt="Y";
		String iDate= null;
		String fDate= null;
		Long milliscalc = null;
		Date idate = null;
		Date odate;
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			rs=statement.executeQuery(SQLstmt);
			while(rs.next()){
				LoopCnt=rs.getInt(1);
				
			}
			rs.close();
			conn.close();
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while (LoopCnt>0){
			if(Strt.equals("Y")){
				iDate=StrtDt;
				Strt="N";
				
			}else{
				//Do some calc of idate
				//do a random incr from that date
			milliscalc=HL.CalcTimeinMilli("min", HL.RandomNumber(5, 10));
			idate = df.parse(iDate);
			//iDate=df.format(idate);
			long t= idate.getTime();
			odate=new Date(t+milliscalc);
			iDate=df.format(odate);	
			}
			
			iDate=HL.ValidateDOWandTime(iDate, "N");
			
			SQLstmt="Update Temp_FirstPRScp"+
					" Set New_ScanDT=convert(datetime,'"+iDate+"', 120)"+
					" Where FirstPRScpScn=(Select Min(FirstPRScpScn) FROM Temp_FirstPRScp WHERE New_ScanDT is null)";
			try {
				conn=DriverManager.getConnection(ConnString);
				Statement statement = conn.createStatement();
				//statement.executeQuery(SQLstmt2);
				statement.execute(SQLstmt);
				conn.close();
				}
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			LoopCnt--;
		}
		
	}
	
	public static void DropTempTbls(String ConnString){
		String SQLstmt="Drop Table Temp_FirstPRScp;"+
				" Drop Table Temp_FirstCycleScans;";
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					//statement.executeQuery(SQLstmt2);
					statement.execute(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
	}
	
	public static void UpdateMinExamDate(String ConnString, String ExmDT){
		String SQLstmt="Update ScopeCyclConv Set PRScanDate=convert(datetime,'"+ExmDT+"', 120)"+
						" Where PRAssoc_IH_FK=(Select Max(PRAssoc_IH_FK)"+
						" From  ScopeCyclConv);";
			//System.out.println(SQLstmt);	
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					statement.executeQuery(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
	}
	
	public static void UpdateOriginalCyclStrDt (String ConnString){
		String SQLstmt="Update ScopeCyclConv"+
				" Set CyclStrtDt=CS.CycleStrt"+
				" FROM"+
				" (Select ASt.CycleID, Min(Ast.AssocStrt) AS CycleStrt"+
				" From"+
				" (Select Concat(SC.ScopeID_FK,'-',SC.CycleID) AS CycleID, SC.AssociationID_FK, Min(IH.ProcessedDateTime) AS AssocStrt"+
				" From ScopeCycle SC"+
				" Join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK"+
				" GROUP BY Concat(SC.ScopeID_FK,'-',SC.CycleID), SC.AssociationID_FK)ASt"+
				" GRoup BY Ast.CycleID) CS"+
				" WHere ScopeCycleID=CS.CycleID;";
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					statement.executeQuery(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
	}
	
	public static void UpdateOriginalCyclEndDt (String ConnString){
		String SQLstmt=" Update ScopeCyclConv"+ 
				" Set CycleEndDt=ED.EndDt"+
				" FROM (Select Concat(SC.ScopeID_FK,'-',SC.CycleID) AS CycleID, MAX(IH.ProcessedDateTime) AS EndDt"+
				" FROM ScopeCycle SC"+
				" Join ItemHistory IH on SC.AssociationID_FK=IH.AssociationID_FK"+
				" GROUP BY Concat(SC.ScopeID_FK,'-',SC.CycleID))ED"+
				" WHERE ScopeCycleID =ED.CycleID;";
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					statement.executeQuery(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
	}
	
	public static void SetNewCycleStrtDts(String ConnString, String MaxCyclDate){
		String SQLstmt="Update ScopeCyclConv"+
				" Set PRScanDate ="+
				" Case "+
				" When PRScanDate is not null then convert(datetime,'"+MaxCyclDate+"', 120)"+
				" Else Null"+
				" End,"+
				" STScanDate="+
				" Case "+
				" When PRScanDate is not null then NULL"+
				" When PRScanDate is NULL and STScanDate is not null then convert(datetime,'"+MaxCyclDate+"', 120)"+
				" Else Null"+
				" End,"+
				" RINScanDate="+
				" Case"+
				" When PRScanDate is not NULL or STScanDate is not null then NULL"+
				" When PRScanDate is NULL or STScanDate is NULL and RINScanDate is not NULL then convert(datetime,'"+MaxCyclDate+"', 120)"+
				" Else NULL End,"+
				" KEINScanDate="+
				" Case"+
				" When PRScanDate is not NULL or STScanDate is not null or RINSCanDate is not null then NULL"+
				" When PRScanDate is NULL or STScanDate is NULL and RINScanDate is NULL and KEINSCanDate is not null then convert(datetime,'"+MaxCyclDate+"', 120)"+
				" Else NULL End,"+
				" CultScanDate="+
				" Case"+
				" When PRScanDate is not NULL or STScanDate is not null or RINSCanDate is not null or KEINScanDate is not null then NULL"+
				" When PRScanDate is NULL or STScanDate is NULL and RINScanDate is NULL and KEinSCanDate is null and CultScanDate is not null then convert(datetime,'"+MaxCyclDate+"', 120)"+
				" Else NULL End"+
				" Where ScopeCyclConv.TblPK in ("+
				" Select TblPK"+
				" FROM ScopeCyclConv"+
				" Where CyclStrtDt=(Select MAX(CyclStrtDt) from ScopeCyclConv));";
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					statement.executeQuery(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
	}

	public static String ScanDTCalcDt(String ConnString, String TblNAME,String ScanItemType,int ScanItemID_FK, int LocationID_FK){
		String ResultDate = null;
		ResultSet rs;
		String SQLstmt = null;
		if(TblNAME.equalsIgnoreCase("Temp_FirstCycleScans")){
			
		}if(TblNAME.equalsIgnoreCase("ItemHistory")){
			if (ScanItemType.equals("Scope")){
				SQLstmt="Select convert(VarChar,ScanDtCalcDt, 120)"+
								" FROM (Select Case"+ 
								" When I.MaxScpDate is Null AND MaxLocDate is Null Then MaxScanDt"+
								" When I.MaxScpDate > MaxLocDate then MaxScpDate"+
								" When I.MaxLocDate > MaxScpDate Then MaxLocDate"+
								" Else MaxScanDt"+
								" End AS ScanDtCalcDt"+
								" FROM (Select IHM.MaxScanDt, SPM.MaxScpDate, LM.MaxLocDate"+ 
								" FROM (Select MAX(LastUpdatedDateTime) AS MaxScanDt From ItemHistory WHERE LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120)) IHM,"+ 
								" (Select MAX(LastUpdatedDateTime) AS MaxScpDate FROM ItemHistory WHERE ScanItemID_FK="+ScanItemID_FK+" AND LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120)) SPM,"+ 
								" (Select Max(LastUpdatedDateTime) AS MaxLocDate FROM ItemHistory Where LocationID_FK="+LocationID_FK+" AND LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120)) LM) I)X;";
				}else{
					SQLstmt="Select convert(VarChar,ScanDtCalcDt, 120)"+
							" FROM (Select Case"+ 
							" When MaxLocDate is Null Then MaxScanDt"+
							" Else MaxLocDate"+
							" End AS ScanDtCalcDt"+
							" FROM (Select IHM.MaxScanDt, LM.MaxLocDate"+ 
							" FROM (Select MAX(LastUpdatedDateTime) AS MaxScanDt From ItemHistory WHERE LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120)) IHM,"+ 
							" (Select Max(LastUpdatedDateTime) AS MaxLocDate FROM ItemHistory Where LocationID_FK="+LocationID_FK+" AND LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120)) LM) I)X;";

				}
		}else{
			//System.out.println("Error:  invlaid Table name CALL BD line 348");
			if (ScanItemType.equals("Scope")){
				SQLstmt="Select convert(VarChar,DateToBeUsed, 120)"+
						" From"+
						" (Select CASE"+
						" When MAXLastScanLoc IS NULL and MAXLastScanScope is null then MaxLastScanTotal"+
						" When MAXLastScanLoc > MAXLastScanScope Then MAXLastScanLoc"+
						" When MAXLastScanLoc < MAXLastScanScope Then MAXLastScanScope"+
						" Else MaxLastScanTotal"+
						" End AS DateToBeUsed"+
						" FROM"+
						" (Select"+
						" Case "+
						" When LastScanIH IS NULL Then LastScanNS"+
						" When LastScanNS is null then LastScanIH"+
						" When LastScanIH > LastScanNS Then LastScanIH"+
						" WHen LastScanIH < LastScanNS Then LastScanNS"+
						" End AS MaxLastScanTotal,"+
						" Case "+
						" When LastScanScpIH is null Then LastScanNSScp"+
						" When LastScanNSScp is null then LastScanScpIH"+
						" When LastScanScpIH > LastScanNSScp Then LastScanScpIH"+
						" When LastScanScpIH < LastScanNSScp Then LastScanNSScp"+
						" End AS MAXLastScanScope,"+
						" Case "+
						" WHEN LastScanLocIH IS NULL Then LastScanNSLoc"+
						" When LastScanNSLoc IS NULL Then LastScanLocIH"+
						" When LastScanLocIH > LastScanNSLoc Then LastScanLocIH"+
						" When LastScanLocIH > LastScanNSLoc Then LastScanNSLoc"+
						" End AS MAXLastScanLoc"+
						" FROM"+
						" (Select MXIHS.LastScanIH, IHS.LastScanScpIH, IHL.LastScanLocIH, MXNS.LastScanNS, MNSS.LastScanNSScp, MNLS.LastScanNSLoc"+
						" FROM "+
						" (Select MAX(LastUpdatedDateTime) AS LastScanIH FROM ItemHistory WHERE LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120) ) MXIHS,"+
						" (Select MAX(LastUpdatedDateTime) AS LastScanScpIH FROM ItemHistory WHERE LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120) AND ScanItemID_FK="+ScanItemID_FK+" )IHS,"+
						" (Select MAX(LastUpdatedDateTime) AS LastScanLocIH FROM ItemHistory WHERE LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120) AND LocationID_FK=26)IHL,"+
						" (Select MAX(NEW_ScanDT) AS LastScanNS FROM Temp_FirstCycleScans) MXNS,"+
						" (Select MAX(NEW_ScanDT) AS LastScanNSScp FROM Temp_FirstCycleScans WHERE ScanItemID_FK="+ScanItemID_FK+" )MNSS,"+
						" (Select MAX(NEW_ScanDT) AS LastScanNSLoc FROM Temp_FirstCycleScans WHERE LocationID_FK="+LocationID_FK+")MNLS)x)y)z;";
			}else{
				SQLstmt="Select convert(VarChar,DateToBeUsed, 120)"+
						" From"+
						" (Select CASE"+
						" When MAXLastScanLoc IS NULL then MaxLastScanTotal"+
						" Else MaxLastScanLoc"+
						" End AS DateToBeUsed"+
						" FROM"+
						" (Select"+
						" Case "+
						" When LastScanIH IS NULL Then LastScanNS"+
						" When LastScanNS is null then LastScanIH"+
						" When LastScanIH > LastScanNS Then LastScanIH"+
						" WHen LastScanIH < LastScanNS Then LastScanNS"+
						" End AS MaxLastScanTotal,"+
						" Case "+
						" WHEN LastScanLocIH IS NULL Then LastScanNSLoc"+
						" When LastScanNSLoc IS NULL Then LastScanLocIH"+
						" When LastScanLocIH > LastScanNSLoc Then LastScanLocIH"+
						" When LastScanLocIH > LastScanNSLoc Then LastScanNSLoc"+
						" End AS MAXLastScanLoc"+
						" FROM"+
						" (Select MXIHS.LastScanIH, IHL.LastScanLocIH, MXNS.LastScanNS, MNLS.LastScanNSLoc"+
						" FROM "+
						" (Select MAX(LastUpdatedDateTime) AS LastScanIH FROM ItemHistory WHERE LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120) ) MXIHS,"+
						" (Select MAX(LastUpdatedDateTime) AS LastScanLocIH FROM ItemHistory WHERE LastUpdatedDateTime != convert(datetime,'1900-01-01 00:00:00', 120) AND LocationID_FK=26)IHL,"+
						" (Select MAX(NEW_ScanDT) AS LastScanNS FROM Temp_FirstCycleScans) MXNS,"+
						" (Select MAX(NEW_ScanDT) AS LastScanNSLoc FROM Temp_FirstCycleScans WHERE LocationID_FK="+LocationID_FK+")MNLS)x)y)z;";
			}
		
			
		}
		
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			rs=statement.executeQuery(SQLstmt);
			while(rs.next()){
				ResultDate=rs.getString(1);
			}
			rs.close();
			conn.close();
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return ResultDate;
	}
	
		
	public static void CreateCycleDateTbl(String ConnString){
		String SQLstmt="Create Table Temp_FirstCycleScans"+
				" (TblPK int Identity(1,1),"+
				" ScpCycle VarChar(25),"+
				" ScanID_FK int,"+
				" LocationType Varchar(50),"+
				" LocationID_FK int,"+
				" ScanItemType Varchar(50),"+
				" ScanItemID_FK int,"+
				" CycleEvent Varchar(50),"+
				" New_ScanDT DateTime,"+
				" Admin_Min_Dt DateTime,"+
				" Admin_Max_Dt DateTime"+
				" CONSTRAINT [PK_Tbl] PRIMARY KEY CLUSTERED"+
				" (	[TblPK] ASC"+
				" )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"+
				" ) ON [PRIMARY];";
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			//statement.executeQuery(SQLstmt2);
			statement.execute(SQLstmt);
			conn.close();
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	
	public static void InsertFirstCycleDate(String ConnString){
		String SQLstmt=	"INSERT into Temp_FirstCycleScans"+
				" (ScpCycle, ScanID_FK, LocationType, LocationID_FK, ScanItemType, ScanItemID_FK, CycleEvent)"+
				" Select Concat(SC.ScopeID_FK,'-',SC.CycleID), IH.ItemHistoryID_PK, LT.Name, IH.LocationID_FK, SIT.Name, IH.ScanItemID_FK, CE.Name"+
				" FROM ItemHistory IH"+
				" Left Join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK"+
				" Join Temp_FirstPRScp x on Concat(SC.ScopeID_FK,'-',SC.CycleID)=x.CycleID"+
				" JOIN Location L on IH.LocationID_FK=L.LocationID_PK"+
				" JOin LocationType LT on L.LocationTypeID_FK=LT.LocationTypeID_PK"+
				" Left JOIN CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"+
				" JOIN ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK"+
				" WHERE IH.LastUpdatedDateTime = convert(datetime,'1900-01-01 00:00:00', 120)";
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			//statement.executeQuery(SQLstmt2);
			statement.execute(SQLstmt);
			conn.close();
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
		public static void SetMarkData(String ConnString){
			String SQLstmt=	" Update ItemHistory SET LastUpdatedDateTime = convert(datetime,'1900-01-01 00:00:00', 120);";
			try {
				conn=DriverManager.getConnection(ConnString);
				Statement statement = conn.createStatement();
				//statement.executeQuery(SQLstmt2);
				statement.execute(SQLstmt);
				conn.close();
				}
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		
		public static void UpdateIHLUPDTT(String ConnString, String SQL){
			String SQLstmt=	SQL;
			try {
				conn=DriverManager.getConnection(ConnString);
				Statement statement = conn.createStatement();
				//statement.executeQuery(SQLstmt2);
				statement.execute(SQLstmt);
				conn.close();
				}
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		
		public static void AddCYCID_to_Temp_FirstPRScp(String ConnString){
		
			String SQLstmt=	"Update Temp_FirstPRScp"+ 
					" Set CycleID= ConCAT(i.ScopeID_FK,'-',i.CycleID)"+
					" FROM"+
					" (Select ScopeID_FK, CycleID"+
					" FROM ScopeCycle SC"+
					" WHERE SC.AssociationID_FK IN("+
					" Select IH.AssociationID_FK"+
					" FROm ItemHistory IH"+
					" JOIN Temp_FirstPRScp x on IH.ItemHistoryID_PK=x.FirstPRScpScn)"+
					" AND SC.ScopeID_FK IN"+
					" (Select x.ScopeID_FK FROM Temp_FirstPRScp x))i"+
					" WHERE i.ScopeID_FK=Temp_FirstPRScp.ScopeID_FK;";
			try {
				conn=DriverManager.getConnection(ConnString);
				Statement statement = conn.createStatement();
				//statement.executeQuery(SQLstmt2);
				statement.execute(SQLstmt);
				conn.close();
				}
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		
		
		/**
		 * This is the Gut ofs the routine
		 * This will tak einformation and calculate New_ScanDT
		 * Based on what information the previous functions did.
		 * @throws ParseException 
		 */
		//TODO
		
		public static void NEWScanDateTimeGeneration(String ConnString, String date) throws ParseException{
			//Assumes scans you want set dates for are in Temp_FirstCycleScans
			int loopcnt=0;
			ResultSet rs;
			ResultSet rs1;
			String  DT;
			String ScanObj = null;
			String ScanEvent = null;
			String LocationType = null;
			int ScanItemID_FK = 0;
			int LocationID_FK =0;
			String  SQLStmt="Select Count(*) FROM Temp_FirstCycleScans;";
				
					try {
						conn=DriverManager.getConnection(ConnString);
						Statement statement = conn.createStatement();
						//statement.executeQuery(SQLstmt2);
						rs=statement.executeQuery(SQLStmt);
						//statement.execute(SQLstmt);
						while(rs.next()){
							loopcnt=rs.getInt(1);
							//gets a count of the rows to be updated
						}
						rs.close();
						conn.close();
						}
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					int ScanID_FK = 0;
			while(loopcnt>0){
				String sql1="Select ScanID_FK,"+
							" ScanItemType, "+
							" Case "+
							" When CycleEvent is NULL then '-'"+
							" Else CycleEvent END ,LocationType, ScanItemID_FK, LocationID_FK"+
							" FROM"+
							" (Select ScanID_FK, ScanItemType, CycleEvent ,LocationType, ScanItemID_FK, LocationID_FK From Temp_FirstCycleScans Where ScanID_FK=(Select MIN(SCanID_FK) FROM Temp_FirstCycleScans WHERE New_ScanDT is NULL))x;";
				//System.out.println(sql1);
				
					try {
						conn=DriverManager.getConnection(ConnString);
						Statement statement = conn.createStatement();
						//statement.executeQuery(SQLstmt2);
						rs1=statement.executeQuery(sql1);
						//statement.execute(SQLstmt);
						while(rs1.next()){
							ScanID_FK=rs1.getInt(1);
							ScanObj=rs1.getString(2);
							ScanEvent=rs1.getString(3);
							LocationType=rs1.getString(4);
							ScanItemID_FK=rs1.getInt(5);
							LocationID_FK=rs1.getInt(6);
							
						}
						rs1.close();
						conn.close();
						}
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					//ScanDTCalcDt(String ConnString, String TblNAME,String ScanItemType,int ScanItemID_FK, int LocationID_FK)
					date=ScanDTCalcDt(ConnString, "-",ScanObj,ScanItemID_FK, LocationID_FK);
					//System.out.println("DatetobeUSED:  "+date);
					DT=HL.HistDateGen(date, ScanObj, ScanEvent, LocationType);
					
					String sql2="Update Temp_FirstCycleScans Set New_ScanDT=convert(datetime,'"+DT+"', 120) WHERE SCANID_FK="+ScanID_FK;
					try {
						conn=DriverManager.getConnection(ConnString);
						Statement statement = conn.createStatement();
						//statement.executeQuery(SQLstmt2);
						statement.execute(sql2);
						conn.close();
						}
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
							
				loopcnt--;
			//End of While	
			}
		}
		
		public static void DeleteDataFrom_Temp_FirstCycleScans(String ConnString){
			String SQLstmt;
			SQLstmt="Delete FROM Temp_FirstCycleScans;";
			try {
				conn=DriverManager.getConnection(ConnString);
				Statement statement = conn.createStatement();
				//statement.executeQuery(SQLstmt2);
				statement.execute(SQLstmt);
				conn.close();
				}
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}

		
	public static void MoveRemainingIHToTemp(String ConnString){
		String SQLstmt=" Insert into Temp_FirstCycleScans"+
				" (ScpCycle, ScanID_FK, LocationType, LocationID_FK, ScanItemType, ScanItemID_FK, CycleEvent)"+
				" Select ConCat(Sc.ScopeID_FK,'-',SC.CycleID) AS ScpCycl, IH.ItemHistoryID_PK, LT.Name AS LocationType, IH.LocationID_FK, SIT.NAME AS ScanItemType, IH.ScanItemID_FK, CE.Name AS CycleEvent"+
				" FROM"+
				" (Select ItemHistoryID_PK, AssociationID_FK, LocationID_FK, ScanItemTypeID_FK, ScanItemID_FK, CycleEventID_FK FROM ItemHistory WHERE LastupdatedDateTime=convert(datetime,'1900-01-01 00:00:00', 120) AND ItemHistoryID_PK > "+
				" (Select MIN(ItemHistoryID_PK) FROM ItemHistory WHERE LastUpdatedDateTime !=convert(datetime,'1900-01-01 00:00:00', 120)))IH"+
				" JOIN Location L on IH.LocationID_FK=L.LocationID_PK"+
				" Join LocationType LT on L.LocationTypeID_FK=LT.LocationTypeID_PK"+
				" Join ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK"+
				" Join ScopeCycle SC on IH.AssociationID_FK=SC.AssociationID_FK"+
				" Left Join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"+
				" WHERE LT.NAME != 'Administration' AND LT.NAME != 'Scope Storage Area' AND LT.NAME !='Waiting Room';";
				
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					//statement.executeQuery(SQLstmt2);
					statement.execute(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
	}
	
	public static void PullAdminWRSAScans(String ConnString){
		String SQLstmt=" Insert Into Temp_FirstCycleScans"+
				" (ScanID_FK, LocationType, LocationID_FK, ScanItemType, ScanItemID_FK, CycleEvent)"+
				" Select IH.ItemHistoryID_PK, LT.Name, IH.LocationID_FK, SIT.Name, IH.ScanItemID_FK, CE.Name"+
				" FROM ItemHistory IH"+
				" JOIN Location L on IH.LocationID_FK=L.LocationID_PK"+
				" JOIN LocationType LT on L.LocationTypeID_FK=LT.LocationTypeID_PK"+
				" JOIN ScanItemType SIT on IH.ScanItemTypeID_FK=SIT.ScanItemTypeID_PK"+
				" Left Join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"+
				" WHERE (LT.Name='Administration' or LT.Name= 'Scope Storage Area' or LT.Name='Waiting Room')"+
				" AND IH.LastupdatedDateTime=convert(datetime,'1900-01-01 00:00:00', 120);";
				
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					//statement.executeQuery(SQLstmt2);
					statement.execute(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				
				SQLstmt="Update Temp_FirstCycleScans"+
						" Set"+
						" Admin_Min_Dt=X.MAXMIN_DT_Scope,"+
						" Admin_Max_Dt=X.MINMAX_DT_SCOPE"+
						" FROM"+
						" Temp_FirstCycleScans T"+
						" Join"+
						" (Select IH.ItemHistoryID_PK,MN.MAXMIN_DT_Scope, MX.MINMAX_DT_Scope"+
						" FROM "+
						" ItemHistory IH"+
						" Left Join"+
						" (Select R.ItemHistoryID_PK, MAX(IH.LastupdatedDateTime) AS MINMAX_DT_Scope"+
						" FRom"+
						" (Select ItemHistoryID_PK, ScanItemTypeID_FK, ScanItemID_FK, LocationID_FK"+
						" FROM ItemHistory IH"+
						" )R"+
						" Join ItemHistory IH"+
						" on R.ItemHistoryID_PK<IH.ItemHistoryID_PK "+
						" AND R.ScanItemTypeID_FK=IH.ScanItemTypeID_FK"+ 
						" AND R.ScanItemID_FK=IH.ScanItemID_FK"+
						" GROUP BY R.ItemHistoryID_PK) MX on IH.ItemHistoryID_PK=MX.ItemHistoryID_PK"+
						" Left Join "+
						" (Select R.ItemHistoryID_PK, Min(IH.LastupdatedDateTime) AS MAXMIN_DT_Scope"+
						" FRom"+
						" (Select ItemHistoryID_PK, ScanItemTypeID_FK, ScanItemID_FK, LocationID_FK"+
						" FROM ItemHistory IH"+
						" )R"+
						" Join ItemHistory IH"+
						" on R.ItemHistoryID_PK>IH.ItemHistoryID_PK "+
						" AND R.ScanItemTypeID_FK=IH.ScanItemTypeID_FK"+ 
						" AND R.ScanItemID_FK=IH.ScanItemID_FK"+
						" GROUP BY R.ItemHistoryID_PK)MN on IH.ItemHistoryID_PK=MN.ItemHistoryID_PK)X"+
						" on T.ScanID_FK=X.ItemHistoryID_PK;";
				
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					//statement.executeQuery(SQLstmt2);
					statement.execute(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				
				SQLstmt="Update Temp_FirstCycleScans"+
						" Set New_ScanDT = UpdateDt"+
						" FROM"+
						" Temp_FirstCycleScans t"+
						" JOIN"+
						" (Select ScanID_FK, Admin_Min_DT, Admin_Max_Dt,"+
						" Case"+
						" When updatedt is not null then updatedt"+
						" When admin_min_dt =convert(datetime,'1900-01-01 00:00:00', 120) Then Dateadd(mi,(ScanID_FK)*-1,Admin_Max_Dt)"+
						" When admin_min_dt !=convert(datetime,'1900-01-01 00:00:00', 120) Then Dateadd(mi,(ScanID_FK),admin_min_dt)"+
						" END AS UpdateDt"+
						" FROM"+
						" (select ScanID_FK, Admin_MIN_DT, ADmin_MAX_DT, DaysDiff,"+
						" Case"+
						" WHEN DaysDiff IS nULL Then NULL"+
						" When Admin_Min_Dt=convert(datetime,'1900-01-01 00:00:00', 120) Then NULL"+
						" When DaysDiff >(20*24) Then DATEADD(hh, DaysDiff/4, Admin_Min_DT)"+
						" When DaysDIFF <=(20*24) Then DateAdd(hh, (DaysDiff/4)*-1, Admin_Max_DT)"+
						" End AS UpdateDT, mn.somdt"+
						" FROM"+
						" (Select ScanID_FK, LocationID_FK, Admin_Min_Dt, Admin_Max_Dt, DATEDIFF(hh, Admin_Min_Dt, Admin_Max_Dt) AS DaysDiff"+
						" FROM Temp_FirstCycleScans"+
						" WHERE ScanItemType='Scope')y,"+
						" (Select MIN(Admin_MIN_DT) as somdt FROM Temp_FirstCycleScans  WHERE Admin_MIN_DT !=convert(datetime,'1900-01-01 00:00:00', 120)) mn)fb)dt"+
						" on t.ScanID_FK=dt.ScanID_FK;";
				
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					//statement.executeQuery(SQLstmt2);
					statement.execute(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				
				SQLstmt="Update Temp_FirstCycleScans"+
						" Set New_ScanDT=New_New_ScanDT"+
						" FROM Temp_FirstCycleScans t "+
						" Join "+
						" (Select x.AssociationID_FK, x.ScanID_FK, x.New_ScanDT,"+
						" Case "+
						" When ScanItemType ='Scope' and New_ScanDT is not null then New_ScanDT"+
						" When New_ScanDT is Null then Null"+
						" When KeyDiff=1 Then DATEADD(second, 15, New_ScanDt)"+
						" When Keydiff=2 Then DATEADD(second, 20, New_ScanDt)"+
						" When Keydiff=3 Then DATEADD(second, 25, New_ScanDt)"+
						" When Keydiff=4 Then DATEADD(second, 30, New_ScanDt)"+
						" When Keydiff=5 Then DATEADD(second, 35, New_ScanDt)"+
						" When Keydiff=6 Then DATEADD(second, 40, New_ScanDt)"+
						" When Keydiff=7 Then DATEADD(second, 45, New_ScanDt)"+
						" When Keydiff=8 Then DATEADD(second, 50, New_ScanDt)"+
						" When Keydiff=9 Then DATEADD(second, 55, New_ScanDt)"+
						" Else Null End as New_New_ScanDT"+
						" FROM"+
						" (Select IH.AssociationID_FK, t.ScanID_FK, t.ScanItemType, sdt.New_ScanDT, t.Admin_Min_Dt, t.Admin_Max_Dt,"+
						" t.ScanID_FK-sdt.ScanID_FK AS KeyDiff"+
						" FROM Temp_FirstCycleScans t"+
						" Join ItemHistory IH on t.ScanID_FK=IH.ItemHistoryID_PK"+
						" Left Join "+
						" (Select IH.AssociationID_FK, t.New_ScanDT, t.ScanID_FK"+
						" FROM ITemHistory IH"+
						" JOIN Temp_FirstCycleScans t on IH.ItemHistoryID_PK=t.ScanID_FK"+
						" WHERE IH.ScanItemTypeID_FK=1 and t.New_ScanDT is not null) sdt on IH.AssociationID_FK=sdt.AssociationID_FK)x)y"+
						" on t.ScanID_FK=y.ScanID_FK"+
						" Where t.ScanItemType !='Scope';";
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					//statement.executeQuery(SQLstmt2);
					statement.execute(SQLstmt);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
	}
	
	public static void FinalDataManipulation(String ConnString, String ResultDate){
		int RecordCnt = 0;
		String FirstRecord;
		Date FirstRecdDate = null;
		String UpdateFR;
		ResultSet rs;
		 String SQLstmt="Select LastupdatedDateTime FROm ITemHistory WHERE ItemHistoryID_PK= (Select MIN(ItemHistoryID_PK) From ItemHistory);";
		
		 try {
				conn=DriverManager.getConnection(ConnString);
				Statement statement = conn.createStatement();
				//statement.executeQuery(SQLstmt2);
				rs=statement.executeQuery(SQLstmt);
				//statement.execute(SQLstmt);
				while(rs.next()){
					FirstRecdDate=rs.getDate(1);
					//gets a count of the rows to be updated
				}
				rs.close();
				conn.close();
				}
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		 
		 SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 FirstRecord=newDateFormat.format(FirstRecdDate);
		 if(FirstRecord.equals("1900-01-01 00:00:00")){
			 		SQLstmt="Update ItemHistory Set LastupdatedDateTime = convert(datetime,'"+ResultDate+"', 120)"+ 
			 				" WHERE ItemHistoryID_PK=Select MIN(ItemHistoryID_PK) From ItemHistory);";	
			 		try {
						conn=DriverManager.getConnection(ConnString);
						Statement statement = conn.createStatement();
						statement.execute(SQLstmt);
						conn.close();
						}
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
		 }else{
			 //Do Nothing
		 }
		 
		 SQLstmt="Select Count(*) FROM ITemHistory Where LastUpdatedDateTime=convert(datetime,'1900-01-01 00:00:00', 120)";
		 try {
				conn=DriverManager.getConnection(ConnString);
				Statement statement = conn.createStatement();
				//statement.executeQuery(SQLstmt2);
				rs=statement.executeQuery(SQLstmt);
				//statement.execute(SQLstmt);
				while(rs.next()){
					RecordCnt=rs.getInt(1);
					//gets a count of the rows to be updated
					
				}
				rs.close();
				conn.close();
				}
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		 SQLstmt=" Update ItemHistory"+
				 " Set LastUpdatedDateTime=New_DT"+
				 " FROM "+
				 " ItemHistory IH JOIN "+
				 " (Select K.FKey, d.New_Dt"+
				 " FRom"+
				 " (Select MIN(ItemHistoryID_PK) AS FKey"+
				 				  " FROM ItemHistory"+
				 				  " WHERE LastUpdatedDateTime=convert(datetime,'1900-01-01 00:00:00', 120)) k,"+
				 " (Select ItemHistoryID_PK, LastUpdatedDateTime, DateAdd(Second,5,LastUpdatedDateTime) AS New_DT"+
				 				  " FROM ItemHistory Where ItemHistoryID_PK=(Select Max(ItemHistoryID_PK)"+
				 				  " From ItemHistory"+
				 				  " WHERE ItemHistoryID_PK<(Select MIN(ItemHistoryID_PK)"+
				 				  " FROM ItemHistory"+
				 				  " WHERE LastUpdatedDateTime=convert(datetime,'1900-01-01 00:00:00', 120))))d)nv"+
				 " on IH.ItemHistoryID_PK=nv.FKey;";
		 	
		 
			 		 try {
			 			conn=DriverManager.getConnection(ConnString);
						Statement statement = conn.createStatement();
						while(RecordCnt>0){
							//System.out.println("Processing Record:  "+RecordCnt);
							statement.execute(SQLstmt);
							RecordCnt--;
						}
					 conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
		 }
	}
	
	public static void FinalizeItemHistoryUpdate(String ConnString){
		int DupScanTimes=0;
		int LoopCnt=0;
		ResultSet rs;
		String SQL1;
		String SQL2;
		String SQL3;
		
		SQL1="Select Count(*) AS DuplicateScanTimes"+
				" FROM"+
				" (Select DataSourceTypeID_FK, DataSourceID, LocationID_FK, LastUpdatedDateTime, Count(*) AS DataCount"+
				" FROm ItemHistory"+
				" GROUP BY DataSourceTypeID_FK, DataSourceID, LocationID_FK, LastUpdatedDateTime)x"+
				" Where DataCount>1;";
		
		SQL2="Update ItemHistory"+
				" Set LastUpdatedDateTime= X.NEW_DT"+
				" FROM ItemHistory IH Join"+
				" (Select IH.DataSourceID,IH.LastUpdatedDateTime, MAx(IH.ItemHistoryID_PK) As ScanID, DateAdd(Second, 1, IH.LastUpdatedDateTime) AS NEW_DT"+
				" FRom ItemHistory IH"+
				" JOIN "+
				" (Select Distinct DataSourceID, LastUpdatedDateTime"+
				" FROM"+
				" (Select DataSourceTypeID_FK, DataSourceID, LocationID_FK, LastUpdatedDateTime, Count(*) AS DataCount"+
				" FROm ItemHistory"+
				" GROUP BY DataSourceTypeID_FK, DataSourceID, LocationID_FK, LastUpdatedDateTime)x"+
				" Where DataCount >1) x"+
				" On IH.DataSourceID=X.DataSourceID AND IH.LastUpdatedDateTime=x.LastUpdatedDateTime"+
				" GROUP BY IH.DataSourceID,IH.LastUpdatedDateTime) X on IH.ItemHistoryID_PK=X.ScanID;";
		
		SQL3="Update ItemHistory Set ReceivedDateTime=LastupdatedDateTime;"+
				" Update ItemHistory Set ProcessedDateTime=LastUpdatedDateTime;";
		
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			//statement.executeQuery(SQLstmt2);
			rs=statement.executeQuery(SQL1);
			//statement.execute(SQLstmt);
			while(rs.next()){
				DupScanTimes=rs.getInt(1);
				//gets a count of the rows to be updated
				System.out.println("DupScanTiems= "+DupScanTimes);
				
			}
			rs.close();
			conn.close();
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		//if(DupScanTimes>0){
			while(DupScanTimes>0){
				try{ 
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					//statement.executeQuery(SQLstmt2);
					statement.execute(SQL2);
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				
				
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					//statement.executeQuery(SQLstmt2);
					rs=statement.executeQuery(SQL1);
					//statement.execute(SQLstmt);
					while(rs.next()){
						DupScanTimes=rs.getInt(1);
						//gets a count of the rows to be updated
						
					}
					rs.close();
					conn.close();
					}
					catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				LoopCnt++;
				//System.out.println("LoopCnt:  "+LoopCnt+":  DupScanTimes:  "+DupScanTimes);
				
			}
		//}else{
			//Do Nothing
		//}
		
		try{ 
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			//statement.executeQuery(SQLstmt2);
			statement.execute(SQL3);
			conn.close();
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
//End of Class	
	}

	


