package TestFrameWork.Emulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;

import TestFrameWork.Unifia_Admin_Selenium;

public class GetIHValues {

	public static Connection conn= null;
	public static ResultSet IH_RS;
	public static TestFrameWork.Unifia_Admin_Selenium UAS; 
	
	public static String[] GetScopeInfo(String ConnString, String ScopeName,String AssociationID) throws InterruptedException{
		//Thread.sleep(4000);
		String ar[]= new String[4];
		String Scope_PK = "";
		String ScopeSerialNum = null;
		String ScopeModel = null;
		String CycleID=null;
		String RefNo="";
		Timestamp ReceivedDateTime  = null;
		String ScanDateTime="";
		String stmt="select S.ScopeID_PK, S.SerialNumber, ST.Name,SC.CycleID from scope S join ScopeType ST on S.ScopeTypeID_FK=ST.ScopeTypeID_PK join ScopeCycle SC "
				+ "on S.ScopeID_PK=SC.ScopeID_FK where S.Name='"+ScopeName+"' and AssociationID_FK='"+AssociationID+"';";
		System.out.println(stmt);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(stmt);
			while(IH_RS.next()){
				Scope_PK=IH_RS.getString(1);
				ScopeSerialNum=IH_RS.getString(2);
				ScopeModel=IH_RS.getString(3);
				CycleID=IH_RS.getString(4);
			}
			IH_RS.close();
			
			//stmt="select ReceivedDateTime from ItemHistory where AssociationID_FK="+AssociationID+" and ScanItemID_FK="+Scope_PK+" and ScanItemTypeID_FK=1;";

			
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and ScanItemID_FK="+Scope_PK+" and ScanItemTypeID_FK=1;";
			Statement Statement = conn.createStatement();
			IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				ReceivedDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
		ScanDateTime = format.format(ReceivedDateTime );
		System.out.println (ScanDateTime);
		RefNo=Scope_PK+"-"+CycleID;
		ar[0]=RefNo;
		ar[1]=ScopeSerialNum;
		ar[2]=ScopeModel;
		ar[3]=ScanDateTime;
		
		//System.out.println("IH_PK:  "+ar[0]+" Assoc_Key:  "+ar[1]+"  CycleEvent:  "+ar[5]);
		return ar;
		
	}
	
	public static String[] GetProcStartEndTime(String ConnString,String AssociationID) throws InterruptedException{
		//Thread.sleep(4000);
		String ar[]= new String[2];

		Timestamp ProcStartDateTime  = null;
		Timestamp ProcEndDateTime  = null;

		String ProcStartScanDateTime="";
		String ProcEndScanDateTime="";
		String stmt="";
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=6;";
			System.out.println(stmt);
			Statement Statement = conn.createStatement();
			IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				ProcStartDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=7;";
			System.out.println(stmt);
			IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				ProcEndDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
		ProcStartScanDateTime = format.format(ProcStartDateTime );
		ProcEndScanDateTime = format.format(ProcEndDateTime );
		System.out.println (ProcStartScanDateTime);
		System.out.println (ProcEndScanDateTime);
		
		ar[0]=ProcStartScanDateTime;
		ar[1]=ProcEndScanDateTime;
		
		//System.out.println("IH_PK:  "+ar[0]+" Assoc_Key:  "+ar[1]+"  CycleEvent:  "+ar[5]);
		return ar;
		
	}
	public static String GetReproCompleteTime(String ConnString, String ScopeName,String AssociationID) throws InterruptedException{
		//Thread.sleep(4000);
		Timestamp ReceivedDateTime  = null;
		String ScanDateTime="";
		String stmt;
		try {
			conn=DriverManager.getConnection(ConnString);
			
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and ScanItemID_FK=0 and ScanItemTypeID_FK=2;";
			System.out.println(stmt);
			Statement Statement = conn.createStatement();
			IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				ReceivedDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
		ScanDateTime = format.format(ReceivedDateTime );
		System.out.println (ScanDateTime);
		return ScanDateTime;		
	}

	public static String[] GetReprocessorData(String ConnString, String LocationName) throws InterruptedException{
		Thread.sleep(4000);
		String ar[]= new String[6];
		int IH_PK = 0;
		int Assoc_Key=0;
		int DataSource_Key=0;
		String Scanner = null;
		String ScannerID = null;
		String CycleEvent = null;
		
		//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
		//ConnString=url+";user="+user+";password="+pass;
		String IHData="Select Distinct IH.ItemHistoryID_PK, IH.AssociationID_FK,IH.DataSourceID, S.Name As Scanner,S.ScannerID, CE.Name"+
		" From ItemHistory IH"+
		" Join Association A on IH.AssociationID_FK=IH.AssociationID_FK"+
		" Join Location L on IH.LocationID_FK=L.LocationID_PK"+
		" Join Scanner S on L.LocationID_PK=S.LocationID_FK"+
		" Join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"+
		" WHERE IH.ItemHistoryID_PK="+
		"(Select ItemHistoryID_PK FRom ItemHistory"+
		" Where LocationID_FK="+
		"(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')"+
		" AND LastUpdatedDateTime= "+
		"(Select MAX(LastUpdatedDateTime) from ItemHistory where LastUpdatedDateTime!="+
		"(Select MAX(LastUpdatedDateTime) FROM ItemHistory Where LocationID_FK=(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"'))));";
		System.out.println(IHData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(IHData);
			while(IH_RS.next()){
				IH_PK=IH_RS.getInt(1);
				Assoc_Key=IH_RS.getInt(2);
				DataSource_Key=IH_RS.getInt(3);
				Scanner=IH_RS.getString(4);
				ScannerID=IH_RS.getString(5);
				CycleEvent=IH_RS.getString(6);
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ar[0]=Integer.toString(IH_PK);
		ar[1]=Integer.toString(Assoc_Key);
		ar[2]=Integer.toString(DataSource_Key);
		ar[3]=Scanner;
		ar[4]=ScannerID;
		ar[5]=CycleEvent;
		
		//System.out.println("IH_PK:  "+ar[0]+" Assoc_Key:  "+ar[1]+"  CycleEvent:  "+ar[5]);
		return ar;
		
	}

	public static String[] GetItemHistoryData(String ConnString, String LocationName) throws InterruptedException{
		Thread.sleep(4000);
		String ar[]= new String[6];
		int IH_PK = 0;
		int Assoc_Key=0;
		int DataSource_Key=0;
		String Scanner = null;
		String ScannerID = null;
		String CycleEvent = "";
		
		//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
		//ConnString=url+";user="+user+";password="+pass;
		String IHData="Select Distinct IH.ItemHistoryID_PK, IH.AssociationID_FK,IH.DataSourceID, S.Name As Scanner,S.ScannerID, CE.Name"+
		" From ItemHistory IH"+
		" Join Association A on IH.AssociationID_FK=IH.AssociationID_FK"+
		" Join Location L on IH.LocationID_FK=L.LocationID_PK"+
		" Join Scanner S on L.LocationID_PK=S.LocationID_FK"+
		" Join CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"+
		" WHERE IH.ItemHistoryID_PK="+
		"(Select ItemHistoryID_PK FRom ItemHistory"+
		" Where LocationID_FK="+
		"(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')"+
		" AND LastUpdatedDateTime= "+
		"(Select MAX(LastUpdatedDateTime) FROM ItemHistory Where LocationID_FK=(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')));";
		System.out.println(IHData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(IHData);
			while(IH_RS.next()){
				IH_PK=IH_RS.getInt(1);
				Assoc_Key=IH_RS.getInt(2);
				DataSource_Key=IH_RS.getInt(3);
				Scanner=IH_RS.getString(4);
				ScannerID=IH_RS.getString(5);
				CycleEvent=IH_RS.getString(6);
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ar[0]=Integer.toString(IH_PK);
		ar[1]=Integer.toString(Assoc_Key);
		ar[2]=Integer.toString(DataSource_Key);
		ar[3]=Scanner;
		ar[4]=ScannerID;
		ar[5]=CycleEvent;
		
		//System.out.println("IH_PK:  "+ar[0]+" Assoc_Key:  "+ar[1]+"  CycleEvent:  "+ar[5]);
		return ar;
		
	}
	
	public static String[] GetKEItemHistoryData(String ConnString, String LocationName) throws InterruptedException{
		String ar[]= new String[3];
		int IH_PK = 0;
		int Assoc_Key=0;
		String DataSource_Key="";
		
		String IHData="Select Distinct IH.ItemHistoryID_PK, IH.AssociationID_FK,IH.DataSourceID From ItemHistory IH"
		+" Join Association A on IH.AssociationID_FK=IH.AssociationID_FK Join Location L on IH.LocationID_FK=L.LocationID_PK WHERE "
		+" IH.ItemHistoryID_PK=(Select Max(ItemHistoryID_PK) FRom ItemHistory Where LocationID_FK=(Select LocationID_PK FROM Location "
		+ " WHERE Name='"+LocationName+"') AND LastUpdatedDateTime= (Select MAX(LastUpdatedDateTime) FROM ItemHistory"
		+ " Where LocationID_FK=(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')));";
		//System.out.println(IHData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(IHData);
			while(IH_RS.next()){
				IH_PK=IH_RS.getInt(1);
				Assoc_Key=IH_RS.getInt(2);
				DataSource_Key=IH_RS.getString(3);

			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ar[0]=Integer.toString(IH_PK);
		ar[1]=Integer.toString(Assoc_Key);
		ar[2]=DataSource_Key;

		
		//System.out.println("IH_PK:  "+ar[0]+" Assoc_Key:  "+ar[1]+"  DataSource_Key:  "+ar[2]);
		return ar;		
	}
	
	public static String GetKESecondAssociationID(String ConnString, String Scope2AssociationID, String DataSourceID ) throws InterruptedException{
		int Assoc_Key=0;

		String IHData="Select Distinct AssociationID_FK from ItemHistory where AssociationID_FK!='"+Scope2AssociationID+"' "
				+ "and DataSourceID='"+DataSourceID+"'";
		//System.out.println(IHData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(IHData);
			while(IH_RS.next()){
				Assoc_Key=IH_RS.getInt(1);

			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return Integer.toString(Assoc_Key);		
	}
	
	public static int[] GetKECycleEventID(String ConnString, String AssociationID) throws InterruptedException{
		//String CycleEventIDs[]= new String[6];
		ArrayList<String> CycleID = new ArrayList<String>();
		String IHData="Select count(CycleEventID_FK) from ItemHistory where AssociationID_FK='"+AssociationID+"';";
		int size=0;
		int i=0;
		//System.out.println(IHData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(IHData);			
			while(IH_RS.next()){
				size=IH_RS.getInt(1);
			}
			IH_RS.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] CycleEventIDs = new int[size];

		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IHData="Select CycleEventID_FK from ItemHistory where AssociationID_FK='"+AssociationID+"';";

			IH_RS=statement.executeQuery(IHData);			
			while(IH_RS.next()){
				CycleEventIDs[i]=IH_RS.getInt(1);
				i++;
			}
			IH_RS.close();			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String CycleEventIDs[]= new String[CycleID.size()];
		//CycleEventIDs=(String[]) CycleID.toArray();
		//System.out.println("Break point");

		//String[] CycleEventIDs = CycleID.toArray(new String[CycleID.size()]);
		//System.out.println("Break point");

		return 	CycleEventIDs;
	}
	
	public static String[] GetItemHistoryNoCycleEvent(String ConnString, String LocationName) throws InterruptedException{
		Thread.sleep(1500);
		String ar[]= new String[6];
		int IH_PK = 0;
		int Assoc_Key=0;
		int DataSource_Key=0;
		String Scanner = null;
		String ScannerID = null;
		String CycleEvent = null;
		
		
		//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
		//ConnString=url+";user="+user+";password="+pass;
		String IHData="Select Distinct IH.ItemHistoryID_PK, IH.AssociationID_FK,IH.DataSourceID, S.Name As Scanner,S.ScannerID"+
		" From ItemHistory IH"+
		" Join Association A on IH.AssociationID_FK=IH.AssociationID_FK"+
		" Join Location L on IH.LocationID_FK=L.LocationID_PK"+
		" Join Scanner S on L.LocationID_PK=S.LocationID_FK"+
		" WHERE IH.ItemHistoryID_PK="+
		"(Select ItemHistoryID_PK FRom ItemHistory"+
		" Where LocationID_FK="+
		"(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')"+
		" AND LastUpdatedDateTime= "+
		"(Select MAX(LastUpdatedDateTime) FROM ItemHistory Where LocationID_FK=(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')));";
		//System.out.println(IHData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(IHData);
			while(IH_RS.next()){
				IH_PK=IH_RS.getInt(1);
				Assoc_Key=IH_RS.getInt(2);
				DataSource_Key=IH_RS.getInt(3);
				Scanner=IH_RS.getString(4);
				ScannerID=IH_RS.getString(5);
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ar[0]=Integer.toString(IH_PK);
		ar[1]=Integer.toString(Assoc_Key);
		ar[2]=Integer.toString(DataSource_Key);
		ar[3]=Scanner;
		ar[4]=ScannerID;
		
		//System.out.println("IH_PK:  "+ar[0]+" Assoc_Key:  "+ar[1]);
		return ar;
		
	}
	
	public static String[] GetCycleID(String ConnString, String LocationName) throws InterruptedException{
		Thread.sleep(1500);
		String ar[]= new String[4];
		int IH_PK = 0;
		int Assoc_Key = 0;
		String Scanner = null;
		int CycleID = 0;
		
		String CycleData= "Select Distinct IH.ItemHistoryID_PK, IH.AssociationID_FK, S.Name,SC.CycleID"+
				" From ItemHistory IH"+
				" JOIN ScopeCycle SC on SC.AssociationID_FK=IH.AssociationID_FK"+
				" Join Scope S on SC.ScopeID_FK=S.ScopeID_PK"+
				" WHERE IH.ItemHistoryID_PK="+
				"(Select ItemHistoryID_PK FRom ItemHistory Where LocationID_FK="+
				"(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')"+
				" AND LastUpdatedDateTime="+
				" (Select MAX(LastUpdatedDateTime) FROM ItemHistory Where LocationID_FK="+
				"(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')))";
				//System.out.println(CycleData);
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					IH_RS=statement.executeQuery(CycleData);
					while(IH_RS.next()){
						IH_PK=IH_RS.getInt(1);
						Assoc_Key=IH_RS.getInt(2);
						Scanner=IH_RS.getString(3);
						CycleID=IH_RS.getInt(4);
						
					}
					IH_RS.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				ar[0]=Integer.toString(IH_PK);
				ar[1]=Integer.toString(Assoc_Key);
				ar[2]=Scanner;
				ar[3]=Integer.toString(CycleID);
				return ar;
	}
	
	public static String GetRelatedITem_IHKey(String ConnString, String ItemHistoryID_PK) throws InterruptedException{
		Thread.sleep(1500);
		String RelatedItem_Key = null;
		int IH_PK = 0;
		String RI ="Select RelatedItemHistoryID_FK"+
		" FROM RelatedItem"+
		" WHERE RelatedItemID_PK="+
		"(Select Max(RelatedItemID_PK) FROM RelatedItem WHERE ItemHistoryID_FK='"+ItemHistoryID_PK+"')";
		//System.out.println("RI="+RI);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(RI);
			while(IH_RS.next()){
				IH_PK=IH_RS.getInt(1);
								
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		RelatedItem_Key=Integer.toString(IH_PK);
		return RelatedItem_Key;
	}
	
	public static String[] GetItemHistoryDataNoAssociationID(String ConnString, String LocationName) throws InterruptedException{
		Thread.sleep(1500);
		String ar[]= new String[6];
		int IH_PK = 0;
		int Assoc_Key=0;
		int DataSource_Key=0;
		String Scanner = null;
		String ScannerID = null;
		int CycleEvent = 0;
		String ScanItemType=null;
		
		//conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
		//ConnString=url+";user="+user+";password="+pass;
		String IHData="select IH.ItemHistoryID_PK, IH.AssociationID_FK, S.Name,IH.cycleeventid_FK,SI.Name from itemhistory IH "+
				"Join Location L on IH.LocationID_FK=L.LocationID_PK "+
				"Join Scanner S on L.LocationID_PK=S.LocationID_FK "+
				"join ScanItemtype SI on IH.ScanItemTypeID_FK=SI.ScanItemTypeID_PK WHERE "+
				"IH.ItemHistoryID_PK= (Select ItemHistoryID_PK FRom ItemHistory "+
				"Where LocationID_FK= (Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')"+ 
				"AND LastUpdatedDateTime=  (Select MAX(LastUpdatedDateTime) FROM ItemHistory  "+
				"Where LocationID_FK=(Select LocationID_PK FROM Location WHERE Name='"+LocationName+"')));";
		//System.out.println(IHData);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(IHData);
			while(IH_RS.next()){
				IH_PK=IH_RS.getInt(1);
				Assoc_Key=IH_RS.getInt(2);
				Scanner=IH_RS.getString(3);
				CycleEvent=IH_RS.getInt(4);
				ScanItemType=IH_RS.getString(5);
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ar[0]=Integer.toString(IH_PK);
		ar[1]=Integer.toString(Assoc_Key);
		ar[2]=Scanner;
		ar[3]=Integer.toString(CycleEvent);
		ar[4]=ScanItemType;
		
		//System.out.println("IH_PK:  "+ar[0]+" Assoc_Key:  "+ar[1]+"  CycleEvent:  "+ar[3]+"ScanItemType:  "+ar[4]);
		return ar;
		
	}
	
	
	public static int GetRelatedItem_Count(String ConnString, String IHID) throws InterruptedException{
		int RelatedItemCount =0;
		Thread.sleep(1500);
		
		String RC ="Select ItemHistoryID_PK, "+
				" Case "+
				" When RelatedItemCount IS NULL Then 0"+
				" Else RelatedItemCount"+
				" END AS RelatedItemCount"+
				" FROM("+
				" Select IH.ItemHistoryID_PK, RI.RelatedItemCount "+
				" FROM ItemHistory IH"+
				" Left Join"+
				" (Select ItemHistoryID_FK, Count(*) as RelatedITemCount"+
				" FROM RelatedItem GROUP BY ItemHistoryID_FK) RI"+
				" on IH.ItemHistoryID_PK=RI.ItemHistoryID_FK"+
				" Where IH.ItemHistoryID_PK="+IHID+") RC";
				
				try {
					conn=DriverManager.getConnection(ConnString);
					Statement statement = conn.createStatement();
					IH_RS=statement.executeQuery(RC);
					while(IH_RS.next()){
						RelatedItemCount=IH_RS.getInt(2);
										
					}
					IH_RS.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return RelatedItemCount;
	}
	
	public static String Room_State(String ConnString, String LocationName) throws InterruptedException{
		Thread.sleep(1500);
		String RoomState= null;
		ResultSet RSt= null;
		
		String sqlstmt=""+
				"Select LSt.Name"+
				" FROM LocationStatus LS"+
				" Join LocationState LSt on LS.LocationStateID_FK=LSt.LocationStateID_PK"+
				" Join Location L on LS.LocationID_FK=L.LocationID_PK"+
				" WHERE L.Name='"+LocationName+"';";
		//System.out.println(sqlstmt);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(sqlstmt);
			while(IH_RS.next()){
				RoomState=IH_RS.getString(1);
								
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return RoomState;
		
	}
	
	public static String Repro_Status(String ConnString, String LocationName, String ScopeName) throws InterruptedException{
		Thread.sleep(1500);
		String ReproState= null;
		ResultSet RSt= null;
		
		String sqlstmt="Select RSt.Name FROM ReprocessingStatus RS Join ReprocessingState RSt on RS.ReprocessingStateID_FK=RSt.ReprocessingStateID_PK "
				+ "Join Location L on RS.LocationID_FK=L.LocationID_PK join Scope S on RS.ScopeID_FK=S.ScopeID_PK "
				+ "WHERE L.Name='"+LocationName+"' and S.Name='"+ScopeName+"';";
		//System.out.println(sqlstmt);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(sqlstmt);
			while(IH_RS.next()){
				ReproState=IH_RS.getString(1);
								
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return ReproState;		
	}
	
	public static String[] Scp_State_Loc(String ConnString, String Scope) throws InterruptedException{
		Thread.sleep(1500);
		int ScopeState= 0;
		String ScopeLoc = null;
		int OtherState = 0;
		int SubLocationID=0;
		int CycleCount=0;
		int ReproCount=0;
		int ExamCount=0;
		String[] ar = new String[7];
		String ScpInfo="Select SS.ScopeStateID_FK,L.Name, SS.OtherScopeStateID_FK, SS.SubLocationID, "+
		" SS.CycleCount,SS.ReprocessingCount,SS.ExamCount From ScopeStatus SS"+
		" Join Scope S on SS.ScopeID_FK=S.ScopeID_PK"+
		" Join Location L on SS.LocationID_FK=L.LocationID_PK"+
		" WHERE S.Name='"+Scope+"';";
		//System.out.println("Scp_State_Loc sql: "+ScpInfo);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(ScpInfo);
			while(IH_RS.next()){
				ScopeState=IH_RS.getInt(1);
				ScopeLoc=IH_RS.getString(2);
				OtherState=IH_RS.getInt(3);
				SubLocationID=IH_RS.getInt(4);
				CycleCount=IH_RS.getInt(5);
				ReproCount=IH_RS.getInt(6);
				ExamCount=IH_RS.getInt(7);
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ar[0]=Integer.toString(ScopeState);
		ar[1]=ScopeLoc;
		ar[2]=Integer.toString(OtherState);
		ar[3]=Integer.toString(SubLocationID);
		ar[4]=Integer.toString(CycleCount);
		ar[5]=Integer.toString(ReproCount);
		ar[6]=Integer.toString(ExamCount);
		
		return ar;
	}
	
	public static int Scp_State_LastStaffID(String ConnString, String Scope) throws InterruptedException{
		Thread.sleep(1500);
		int LastScanStaffID_FK = 0;

		String ScpInfo="Select SS.LastScanStaffID_FK From ScopeStatus SS Join Scope S on SS.ScopeID_FK=S.ScopeID_PK WHERE S.Name='"+Scope+"';";
		//System.out.println("Scp_State_Loc sql: "+ScpInfo);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(ScpInfo);
			while(IH_RS.next()){
				LastScanStaffID_FK=IH_RS.getInt(1);

			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return LastScanStaffID_FK;
	}

	public static int GetStaffPK(String ConnString, String StaffID) throws InterruptedException{
		Thread.sleep(1500);

		int StaffID_PK = 0;

		String ScpInfo="Select StaffID_PK From Staff WHERE StaffID='"+StaffID+"';";
		//System.out.println("Scp_State_Loc sql: "+ScpInfo);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(ScpInfo);
			while(IH_RS.next()){
				StaffID_PK=IH_RS.getInt(1);
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return StaffID_PK;
	}

	/**
	 * These Functions are for logging the test case
	 */
	
	public static String Start_Exec_Log(String FileName){
		String DateStamp = FastDateFormat.getInstance("ddMMHHssyyyy").format(System.currentTimeMillis( ));
		String FN;
		FN=FileName+DateStamp+".xml";	
		//System.out.println("Search for:  "+FN);
		File file = new File(FN);
		try(FileWriter fw = new FileWriter(file, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    
			    out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				out.println("<Scenario Name=\"Automation Testing using Scan Emulator\">");
				out.println("<TestCase-1>");
				out.close();
			} catch (IOException e) {
				 System.out.println("#Failed!# writing to file");
			}
		return FN;
	}
	
	public static void Exec_Test_Case(String FileName, String StartOrEnd, int TestCaseNumber){
		File file = new File(FileName);
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))){
			if(StartOrEnd.equalsIgnoreCase("Start")){
				out.println("<TestCase-"+TestCaseNumber+">");
			}else if(StartOrEnd.equalsIgnoreCase("End")){
				out.println("</TestCase-"+TestCaseNumber+">");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Exec_Log_Result(String FileName, String Description, String Expected, String Result){
		File file = new File(FileName);
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))){
			out.println("	<TestStep>");
			out.println("		<Description Name=\""+Description+"\"/>");
			out.println("		<ExpectedResult Name=\""+Expected+"\"/>");
			out.println("		<Result Name=\""+Result+"\"/>");
			out.println("	</TestStep>");
			out.close();
			if (Result.toLowerCase().contains("#Failed!#")){
				UAS.resultFlag="#Failed!#";
				System.out.println("UAS.resultFlag"+UAS.resultFlag);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Close_Exec_Log(String FileName, String TCResult, int TestCaseNumber){
		File file = new File(FileName);
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))){
			out.println("</TestCase-"+TestCaseNumber+">");
			out.println("<TestCaseREsult Value=\""+TCResult+"\"/>");
			out.println("</Scenario>");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String Result_Room_State(String Actual, String Expected, String RM){
		String Result=null;
		
		if(Actual.equalsIgnoreCase(Expected)){
			Result="Pass- Room State ="+Expected;
			
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!#- "+RM+" room state, Expected:  "+Expected+".  Actual:  "+Actual;
		}
		return Result;
	}
	
	public static String Result_Repro_State(String Actual, String Expected, String RM, String Scope){
		String Result=null;
		
		if(Actual.equalsIgnoreCase(Expected)){
			Result="Pass- Reprocessor Status ="+Expected;
			
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!#- "+RM+" room status for "+Scope+", Expected:  "+Expected+".  Actual:  "+Actual;
		}
		return Result;
	}
	
	public static String Result_CycleEvent(String ActualEvent, String ExpectedEvent){
		String Result_Cycle=null;
		
		if(ActualEvent.equalsIgnoreCase(ExpectedEvent)){
			Result_Cycle="Pass-  Cycle Event is okay";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_Cycle="#Failed!#-  Cycle Event was expected to be:  "+ExpectedEvent+"; However it was "+ActualEvent;						
		
		}
		return Result_Cycle;
		
	}
	
	public static String Get_ExpectedReproCount(String ConnString, int ScopePK){
		String ExpectedReproCount="";
		String Scope;
		int ScopeID;
		String ScopeSerialNum;
		String ScopeType;
		String ExpectedExamCount;
		String RI ="Select Scope, ScopeID_PK, SerialNumber, ScopeType, Count(UsedInExam) AS Count_UsedInExam, Count(Reprocessed) AS Count_Reprocessed"
	+" FROM(Select sum_1._CycleID, sum_1.Scope, sum_1.ScopeID_PK, sum_1.SerialNumber, sum_1.ScopeType, "
	+" sum_1.PreProcedureScan, sum_1.PatientScan,"
	+" Case"
	+" When sum_1.PreProcedureScan is not null And sum_1.PatientScan is not null then 'Yes'"
	+" Else null"
	+" End AS UsedInExam,"
	+" sum_1.ScanInRep, sum_1.KEStartRep,"
	+" Case"
	+" when sum_1.ScanInRep is not null then 'Yes'"
	+" when sum_1.KEStartRep is not null then 'Yes'"
	+" Else null"
	+" End AS Reprocessed"
	+" from("
	+" Select SC._CycleID, S.ScopeID_PK,S.Name AS Scope, S.SerialNumber, ST.Name AS ScopeType, PR.PreProcedureScan, PR.PatientScan, RepData.ScanInRep, RepData.KEStartRep"
	+" from"
	+" (Select Distinct concat(ScopeID_FK, '-', CycleID) AS _CycleID, ScopeID_FK FROM ScopeCycle) SC"
	+" Join Scope S on SC.ScopeID_FK=S.ScopeID_PK"
	+" Join ScopeType ST on ST.ScopeTypeID_PK=S.ScopeTypeID_FK"
	+" Left Join"
	+" (Select S.Name AS Scope, S.SerialNumber,ST.Name AS ScopeType, IH.AssociationID_FK, CE.Name AS CycleEvent, A.LocationType,A._CycleID ,IH.ItemHistoryID_PK AS PreProcedureScan, Pt.ItemHistoryID_PK AS PatientScan"
	+" from"
	+" (Select ItemHistoryID_PK,ScanItemID_FK, AssociationID_FK,CONCAT(AssociationID_FK,'-', ScanItemID_FK)As KEY_ID , CycleEventID_FK from ItemHistory WHERE ScanItemTypeID_FK=1 AND CycleEventID_FK=3)  IH"
	+" JOIN CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"
	+" JOIN Scope S on IH.ScanItemID_FK=S.ScopeID_PK"
	+" JOIN ScopeType ST on S.ScopeTypeID_FK=ST.ScopeTypeID_PK"
	+" Left JOin"
	+" (Select LT.Name AS LocationType, A.AssociationID_PK,CONCAT (SC.ScopeID_FK,'-',SC.CycleID) AS _CycleID, CONCAT(AssociationID_FK,'-', SC.ScopeID_FK)As KEY_ID"
	+" FROm Association A "
	+" JOIN Location L on A.LocationID_FK=L.LocationID_PK"
	+" JOIN LocationType LT on L.LocationTypeID_FK=LT.LocationTypeID_PK"
	+" Left Join ScopeCycle SC on A.AssociationID_PK=SC.AssociationID_FK) A on A.KEY_ID=IH.KEY_ID"
	+" Left Join"
	+" (Select IH.ItemHistoryID_PK, IH.ScanItemID_FK, A.AssociationID_PK, ConCat(SC.ScopeID_FK,'-',SC.CycleID) AS _CycleID"
	+" FROM ItemHistory IH"
	+" JOIN Association A on IH.AssociationID_FK=A.AssociationID_PK"
	+" JOIN ScopeCycle SC on A.AssociationID_PK=SC.AssociationID_FK"
	+" WHERE CycleEventID_FK=4) Pt on A._CycleID=Pt._CycleID )PR on PR._CycleID=SC._CycleID"
	+" Left Join"
	+" (Select SC._CycleID, RepIn.ItemHistoryID_PK AS ScanInRep, RepStart.ItemHistoryID_PK AS KEStartRep"
	+" FROM "
	+" (Select Distinct concat(ScopeID_FK, '-', CycleID) AS _CycleID FROM ScopeCycle) SC "
	+" Left Join"
	+" (Select IH.ItemHistoryID_PK, A.AssociationID_PK, CONCAT(SC.ScopeID_FK,'-', SC.CycleID) AS _CycleID"
	+" FROM ItemHistory IH"
	+" Left Join Association A on IH.AssociationID_FK=A.AssociationID_PK"
	+" Left Join ScopeCycle SC on A.AssociationID_PK=SC.AssociationID_FK"
	+" Where IH.CycleEventID_FK=15) RepIn on SC._CycleID=RepIn._CycleID"
	+" Left Join"
	+" (Select IH.ItemHistoryID_PK, A.AssociationID_PK, CONCAT(SC.ScopeID_FK,'-', SC.CycleID) AS _CycleID"
	+" FROM ItemHistory IH"
	+" Left Join Association A on IH.AssociationID_FK=A.AssociationID_PK"
	+" Left Join ScopeCycle SC on A.AssociationID_PK=SC.AssociationID_FK"
	+" Where IH.CycleEventID_FK=31 and IH.ScanItemTypeID_FK=1) RepStart on SC._CycleID=RepStart._CycleID) RepData on SC._CycleID=RepData._CycleID"
	+" ) sum_1) sum2"
	+" where ScopeID_PK="+ScopePK
	+" Group BY Scope, ScopeID_PK, SerialNumber, ScopeType";
		//System.out.println("RI="+RI);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(RI);
			while(IH_RS.next()){
				Scope=IH_RS.getString(1);					
				ScopeID=IH_RS.getInt(2);					
				ScopeSerialNum=IH_RS.getString(3);					
				ScopeType=IH_RS.getString(4);					
				ExpectedExamCount=IH_RS.getString(5);					
				ExpectedReproCount=IH_RS.getString(6);					
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ExpectedReproCount;
	}

	public static String Get_ExpectedExamCount(String ConnString, int ScopePK){
		String ExpectedReproCount="";
		String Scope;
		int ScopeID;
		String ScopeSerialNum;
		String ScopeType;
		String ExpectedExamCount="";
		String RI ="Select Scope, ScopeID_PK, SerialNumber, ScopeType, Count(UsedInExam) AS Count_UsedInExam, Count(Reprocessed) AS Count_Reprocessed"
	+" FROM(Select sum_1._CycleID, sum_1.Scope, sum_1.ScopeID_PK, sum_1.SerialNumber, sum_1.ScopeType, "
	+" sum_1.PreProcedureScan, sum_1.PatientScan,"
	+" Case"
	+" When sum_1.PreProcedureScan is not null And sum_1.PatientScan is not null then 'Yes'"
	+" Else null"
	+" End AS UsedInExam,"
	+" sum_1.ScanInRep, sum_1.KEStartRep,"
	+" Case"
	+" when sum_1.ScanInRep is not null then 'Yes'"
	+" when sum_1.KEStartRep is not null then 'Yes'"
	+" Else null"
	+" End AS Reprocessed"
	+" from("
	+" Select SC._CycleID, S.ScopeID_PK,S.Name AS Scope, S.SerialNumber, ST.Name AS ScopeType, PR.PreProcedureScan, PR.PatientScan, RepData.ScanInRep, RepData.KEStartRep"
	+" from"
	+" (Select Distinct concat(ScopeID_FK, '-', CycleID) AS _CycleID, ScopeID_FK FROM ScopeCycle) SC"
	+" Join Scope S on SC.ScopeID_FK=S.ScopeID_PK"
	+" Join ScopeType ST on ST.ScopeTypeID_PK=S.ScopeTypeID_FK"
	+" Left Join"
	+" (Select S.Name AS Scope, S.SerialNumber,ST.Name AS ScopeType, IH.AssociationID_FK, CE.Name AS CycleEvent, A.LocationType,A._CycleID ,IH.ItemHistoryID_PK AS PreProcedureScan, Pt.ItemHistoryID_PK AS PatientScan"
	+" from"
	+" (Select ItemHistoryID_PK,ScanItemID_FK, AssociationID_FK,CONCAT(AssociationID_FK,'-', ScanItemID_FK)As KEY_ID , CycleEventID_FK from ItemHistory WHERE ScanItemTypeID_FK=1 AND CycleEventID_FK=3)  IH"
	+" JOIN CycleEvent CE on IH.CycleEventID_FK=CE.CycleEventID_PK"
	+" JOIN Scope S on IH.ScanItemID_FK=S.ScopeID_PK"
	+" JOIN ScopeType ST on S.ScopeTypeID_FK=ST.ScopeTypeID_PK"
	+" Left JOin"
	+" (Select LT.Name AS LocationType, A.AssociationID_PK,CONCAT (SC.ScopeID_FK,'-',SC.CycleID) AS _CycleID, CONCAT(AssociationID_FK,'-', SC.ScopeID_FK)As KEY_ID"
	+" FROm Association A "
	+" JOIN Location L on A.LocationID_FK=L.LocationID_PK"
	+" JOIN LocationType LT on L.LocationTypeID_FK=LT.LocationTypeID_PK"
	+" Left Join ScopeCycle SC on A.AssociationID_PK=SC.AssociationID_FK) A on A.KEY_ID=IH.KEY_ID"
	+" Left Join"
	+" (Select IH.ItemHistoryID_PK, IH.ScanItemID_FK, A.AssociationID_PK, ConCat(SC.ScopeID_FK,'-',SC.CycleID) AS _CycleID"
	+" FROM ItemHistory IH"
	+" JOIN Association A on IH.AssociationID_FK=A.AssociationID_PK"
	+" JOIN ScopeCycle SC on A.AssociationID_PK=SC.AssociationID_FK"
	+" WHERE CycleEventID_FK=4) Pt on A._CycleID=Pt._CycleID )PR on PR._CycleID=SC._CycleID"
	+" Left Join"
	+" (Select SC._CycleID, RepIn.ItemHistoryID_PK AS ScanInRep, RepStart.ItemHistoryID_PK AS KEStartRep"
	+" FROM "
	+" (Select Distinct concat(ScopeID_FK, '-', CycleID) AS _CycleID FROM ScopeCycle) SC "
	+" Left Join"
	+" (Select IH.ItemHistoryID_PK, A.AssociationID_PK, CONCAT(SC.ScopeID_FK,'-', SC.CycleID) AS _CycleID"
	+" FROM ItemHistory IH"
	+" Left Join Association A on IH.AssociationID_FK=A.AssociationID_PK"
	+" Left Join ScopeCycle SC on A.AssociationID_PK=SC.AssociationID_FK"
	+" Where IH.CycleEventID_FK=15) RepIn on SC._CycleID=RepIn._CycleID"
	+" Left Join"
	+" (Select IH.ItemHistoryID_PK, A.AssociationID_PK, CONCAT(SC.ScopeID_FK,'-', SC.CycleID) AS _CycleID"
	+" FROM ItemHistory IH"
	+" Left Join Association A on IH.AssociationID_FK=A.AssociationID_PK"
	+" Left Join ScopeCycle SC on A.AssociationID_PK=SC.AssociationID_FK"
	+" Where IH.CycleEventID_FK=31) RepStart on SC._CycleID=RepStart._CycleID) RepData on SC._CycleID=RepData._CycleID"
	+" ) sum_1) sum2"
	+" where ScopeID_PK="+ScopePK
	+" Group BY Scope, ScopeID_PK, SerialNumber, ScopeType";
		//System.out.println("RI="+RI);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(RI);
			while(IH_RS.next()){
				Scope=IH_RS.getString(1);					
				ScopeID=IH_RS.getInt(2);					
				ScopeSerialNum=IH_RS.getString(3);					
				ScopeType=IH_RS.getString(4);					
				ExpectedExamCount=IH_RS.getString(5);					
				ExpectedReproCount=IH_RS.getString(6);					
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ExpectedExamCount;
	}

	public static String Result_ReproCount(String ActualReproCount, String ExpectedReproCount){
		String Result_ReproCount=null;		
		if(ActualReproCount.equalsIgnoreCase(ExpectedReproCount)){
			Result_ReproCount="Pass- Reprocessing Count is okay";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_ReproCount="#Failed!#- Reprocessing Count was ecpected to be:  "+ExpectedReproCount+"; However it was "+ActualReproCount;								
		}
		return Result_ReproCount;		
	}
	
	public static String Result_ExamCount(String ActualExamCount, String ExpectedExamCount){
		String Result_ExamCount=null;		
		if(ActualExamCount.equalsIgnoreCase(ExpectedExamCount)){
			Result_ExamCount="Pass- Exam Count is okay";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_ExamCount="#Failed!#- Exam Count was ecpected to be:  "+ExpectedExamCount+"; However it was "+ActualExamCount;								
		}
		return Result_ExamCount;		
	}
	
	public static String Result_LastScanStaffID(int ActualStaffPK,int ExpectedStaffPK){
		String Result_LastStaff=null;		
		if(ActualStaffPK==ExpectedStaffPK){
			Result_LastStaff="Pass- LastScanStaffID_FK is okay";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_LastStaff="#Failed!#- LastScanStaffID_FK was ecpected to be:  "+ExpectedStaffPK+"; However it was "+ActualStaffPK;								
		}
		return Result_LastStaff;		
	}

	public static String Result_KECycleEvents(int[] ActualEvent, int[] ExpectedEvent,int size){
		String Result_Cycle=null;
		int i=0;
		String result="";
		
			//result="Pass- The number of expected and actual associations match. ";
			//System.out.println("The number of expected and actual associations match. ");
			while(i<size){
				if(ActualEvent[i]==ExpectedEvent[i]){
					result="Pass- Cycle Event was expected to be "+ExpectedEvent[i]+" is in the ItemHistory Table as expected. "+result;
					//System.out.println("Cycle Event="+ExpectedEvent[i]+" is in the ItemHistory Table as expected. ");

				}else{
					UAS.resultFlag="#Failed!#";
					result="#Failed!#- "+ExpectedEvent[i]+" should be in ItemHistory table but it is not. "+result;
				}
				i++;
			}
		return result;
	}
	
	public static String Result_CycleCount(String ActualCount, String ExpectedCount){
		String Result_Count=null;
		
		if(ActualCount.equalsIgnoreCase(ExpectedCount)){
			Result_Count="Pass-  Cycle Count is okay";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_Count="#Failed!#-  Cycle Count was ecpected to be:  "+ExpectedCount+"; However it was "+ActualCount;	
		}
		return Result_Count;
		
	}
	
	public static String Result_Location(String ExpectedLoc, String ActualLoc, String ExpectedCabinet, String ActualCabinet){
		String Result_Location=null;
		
		if(ActualLoc.equalsIgnoreCase(ExpectedLoc)){
			if(ExpectedCabinet.equalsIgnoreCase(ActualCabinet)){
				Result_Location="Pass-  ScopeLocation and Sublocation are okay";
			} else {
				UAS.resultFlag="#Failed!#";
				Result_Location="#Failed!#-  ScopeLocation is okay, but the Sublocation was ecpected to be:  "+ExpectedCabinet+".  However it was "+ActualCabinet;										
			}
		}else{
			if(ExpectedCabinet.equalsIgnoreCase(ActualCabinet)){
				UAS.resultFlag="#Failed!#";
				Result_Location="#Failed!#- Sublocation is ok, but ScopeLocation was expected to be:  "+ExpectedLoc+".  However it was "+ActualLoc;									
			} else {
				UAS.resultFlag="#Failed!#";
				Result_Location="#Failed!#-  ScopeLocation was expected to be:  "+ExpectedLoc+".  However it was "+ActualLoc+". "
					+ "Sublocation was expected to be:  "+ExpectedCabinet+".  However it was "+ActualCabinet;							
			
			}
		
		}
		return Result_Location;
		
	}
	
	public static String Result_ScopeState(String ExpectedScopeState, String ActualScopeState, String ExpectedOtherState, String ActualOtherState){
		String Result_ScopeState=null;
		
		if(ExpectedScopeState.equalsIgnoreCase(ActualScopeState)){
			if(ExpectedOtherState.equalsIgnoreCase(ActualOtherState)){
				Result_ScopeState="Pass- ScopeState and OtherScopeState are okay";
			} else {
				UAS.resultFlag="#Failed!#";
					Result_ScopeState="#Failed!#-  ScopeState is OK, but the OtherScopeState was ecpected to be:  "+ExpectedOtherState+".  However it was "+ActualOtherState;
			}
		} else {
			if(ExpectedOtherState.equalsIgnoreCase(ActualOtherState)){
				UAS.resultFlag="#Failed!#";
				Result_ScopeState="#Failed!#-  OtherScopeState is okay, but the ScopeState was ecpected to be:  "+ExpectedScopeState+".  However it was "+ActualScopeState;
			} else {
				UAS.resultFlag="#Failed!#";
				Result_ScopeState="#Failed!#- ScopeState was ecpected to be:  "+ExpectedScopeState+".  However it was "+ActualScopeState+". "
						+ "OtherScopeState was ecpected to be:  "+ExpectedOtherState+".  However it was "+ActualOtherState;						
			}
		}
				
		return Result_ScopeState;
		
	}
	
	public static String Result_Same_Assoc(String Assoc1, String Assoc2){
		String Result_Same_Assoc=null;		
		
		if(Assoc1.equals(Assoc2)){
			Result_Same_Assoc="Pass- The two association ID's match";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_Same_Assoc="#Failed!#-  The two association do not match.  First Assoc:  "+Assoc1+"; Second Assoc:  "+Assoc2;
		}
		
		return Result_Same_Assoc;
	}
	
	public static String Result_Different_Assoc(String Assoc1, String Assoc2){
		String Result_Diff_Assoc=null;
		
		if(Assoc1.equals(Assoc2)){
			UAS.resultFlag="#Failed!#";
			Result_Diff_Assoc="#Failed!#-  The two association match, but they should not match.  First Assoc:  "+Assoc1+"; Second Assoc:  "+Assoc2;
		}else{
			Result_Diff_Assoc="Pass- The two association ID's do not match as expected.";
		}
		
		return Result_Diff_Assoc;
	}

	public static String TCResult(String TCResult, String Result){
		
		String ResultSubstring=Result.substring(1, 4);
		if(TCResult.equals("Error")){
			TCResult="Error";
		}else{
			if(ResultSubstring.equals("Pass")){
				TCResult="Pass";
			}else{
				TCResult="Error";
			}
		}
		
		return TCResult;
	}
	
	public static String RelatedItem_Verf(String Expected, String Actual){
		String Result_RI;
		
		if(Actual.equals(Expected)){
			Result_RI="Pass- RelatedItem Table correct";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_RI="#Failed!#-  Expected:  "+Expected+", Actual:  "+Actual;
		}
		
		return Result_RI;
	
	}
	
	public static String RelatedItemCount_Verf(int RICount, int Expected){
		String Result;
		if(RICount==Expected){
			Result="Pass";
		}else{
			UAS.resultFlag="#Failed!#";
			Result= "#Failed!#-  Expected count was:  "+Expected+" , but the actual count was:  "+RICount;
		}
		return Result;
	}
	
	public static String Result_ScanItemType(String Actual, String Expected){
		String Result_ScanItemType=null;
		
		if(Actual.equalsIgnoreCase(Expected)){
			Result_ScanItemType="Pass-  Scan Item Type is okay";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_ScanItemType="#Failed!#-  Scan Item Type was expected to be:  "+Expected+"; However it was "+Actual;						
		
		}
		return Result_ScanItemType;
		
	}

	public static String Result_AssociationID(String Actual, String Expected){
		String Result_AssociatonID=null;
		
		if(Actual.equalsIgnoreCase(Expected)){
			Result_AssociatonID="Pass-  AssociationID Type is Null and is okay";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_AssociatonID="#Failed!#- AssociationID was expected to be:  "+Expected+"; However it was "+Actual;						
		
		}
		return Result_AssociatonID;
		
	}
	
	public static String GetBarcodeValue(String ConnString,String ItemHistoryID) throws InterruptedException{
		String RelatedItem_Key = null;
		String BarCodeName = null;
		String RI ="select B.Name from Barcode B JOIN ItemHistory IH "
				+ " on B.BarcodeID_PK=IH.ScanItemID_FK"
				+ " where IH.ScanItemTypeID_FK=14 and IH.ItemHistoryID_PK="+ItemHistoryID;
		//System.out.println("RI="+RI);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(RI);
			while(IH_RS.next()){
				BarCodeName=IH_RS.getString(1);					
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BarCodeName;
	}
	
	public static String Result_Same_Barcode(String Expected, String Actual) {
		String Result_RI;
		if (Actual.equals(Expected)) {
			Result_RI = "Pass- Barcodes matched";
		} else {
			UAS.resultFlag="#Failed!#";
			Result_RI = "#Failed!#-  Expected:  " + Expected + ", Actual:  "+ Actual;
		}
		return Result_RI;
	}
	
	public static String GetKeyEntryValue(String ConnString,String ItemHistoryID) throws InterruptedException{
		String RelatedItem_Key = null;
		String KeyEntry = null;
		
		String RI ="Select KE.KeyEntryValue from KeyEntryScans KE JOIN ItemHistory IH "
				+ "ON KE.KeyEntryID_PK=IH.ScanItemID_FK and IH.ScanItemID_FK=(Select ScanItemID_FK "
				+ "From ItemHistory where ScanItemTypeID_FK=10 and ItemHistoryID_PK="+ItemHistoryID+")";
		//System.out.println("RI="+RI);
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(RI);
			while(IH_RS.next()){
				KeyEntry=IH_RS.getString(1);						
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return KeyEntry;
	}
	
	public static String Result_Same_KeyEntry(String Expected, String Actual) {
		String Result_RI;
		if (Actual.equals(Expected)) {
			Result_RI = "Pass- KeyEntry matched";
		} else {
			UAS.resultFlag="#Failed!#";
			Result_RI = "#Failed!#-  Expected:  " + Expected + ", Actual:  "
					+ Actual;
		}
		return Result_RI;
	}
	
	public static String GetReasonForReprocessing(String ConnString, String AssociationID){
		String Reason=null;
		String stmt="select B.Name from Barcode B join ItemHistory IH on IH.ScanItemID_FK=B.BarcodeID_PK where IH.ScanItemTypeID_FK=11 and IH.CycleEventID_FK=37 and IH.AssociationID_FK="+AssociationID;
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement statement = conn.createStatement();
			IH_RS=statement.executeQuery(stmt);
			while(IH_RS.next()){
				Reason=IH_RS.getString(1);						
			}
			IH_RS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Reason;
	}
	
	public static String Result_Same_Reason(String Expected, String Actual){
		String Result_Same_Assoc=null;		
		
		if(Actual.equals(Expected)){
			Result_Same_Assoc="Pass- Reason matched";
		}else{
			UAS.resultFlag="#Failed!#";
			Result_Same_Assoc="#Failed!#-  Expected:  " + Expected + ", Actual:  "+ Actual;
		}
		
		return Result_Same_Assoc;
	}
	
	public static String[] GetMCStartEndTime(String ConnString, String AssociationID){
		String time[]= new String[2];

		Timestamp MCStartDateTime  = null;
		Timestamp MCEndDateTime  = null;

		String MCStartScanDateTime="";
		String MCEndScanDateTime="";
		String stmt="";
		try {
			conn=DriverManager.getConnection(ConnString);
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=13;";
			System.out.println(stmt);
			Statement Statement = conn.createStatement();
			IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				MCStartDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=14;";
			System.out.println(stmt);
			IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				MCEndDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
		MCStartScanDateTime = format.format(MCStartDateTime );
		MCEndScanDateTime = format.format(MCEndDateTime );
		System.out.println (MCStartScanDateTime);
		System.out.println (MCEndScanDateTime);
		
		time[0]=MCStartScanDateTime;
		time[1]=MCEndScanDateTime;
		
		return time;
		
	}
	
	public static String[] GetMCStartTime(String ConnString, String AssociationID){
		String time[]= new String[2];

		Timestamp MCStartDateTime  = null;
		
		String MCStartScanDateTime="";
		String stmt="";
		try {
			conn=DriverManager.getConnection(ConnString);
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=13;";
			System.out.println(stmt);
			Statement Statement = conn.createStatement();
			IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				MCStartDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
		MCStartScanDateTime = format.format(MCStartDateTime );
		System.out.println (MCStartScanDateTime);
		
		time[0]=MCStartScanDateTime;
		
		return time;
		
	}
		
	public static String GetMCEndTime(String ConnString, String AssociationID){
		Timestamp MCEndDateTime  = null;
		String MCEndScanDateTime="";
		String stmt="";
		try {
			conn=DriverManager.getConnection(ConnString);
			Statement Statement = conn.createStatement();	
			stmt="SELECT DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), ItemHistory.ReceivedDateTime) AS ColumnInLocalTime FROM ItemHistory where AssociationID_FK="+AssociationID+" and CycleEventID_FK=14;";
			System.out.println(stmt);
			IH_RS = Statement.executeQuery(stmt);
			while(IH_RS.next()){
				MCEndDateTime = IH_RS.getTimestamp(1);
			}
			IH_RS.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a" );
		MCEndScanDateTime = format.format(MCEndDateTime );
		System.out.println (MCEndScanDateTime);		
		return MCEndScanDateTime;
		
	}
	
	
	public static String Start_Exec_Log1(String FileName, String scenarioName){
		String DateStamp = FastDateFormat.getInstance("ddMMHHssyyyy").format(System.currentTimeMillis( ));
		String FN;
		FN=FileName+DateStamp+".xml";	
		//System.out.println("Search for:  "+FN);
		File file = new File(FN);
		try(FileWriter fw = new FileWriter(file, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				//out.println("<Scenario Name=\"Automation Testing using Scan Emulator\">");
			    out.println("<Scenario Name=\"'"+scenarioName+"'\">");
				out.println("<TestCase-1>");
				out.close();
			} catch (IOException e) {
				 System.out.println("#Failed!# writing to file");
			}
		return FN;
	}
	

}
