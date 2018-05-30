package poc.cigniti.multiscope.procedure;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

public class TestDB {
	
	@Test
	public void testDBConnection() throws SQLException
	{
		String conn = "jdbc:sqlserver://SPRINTTEST-03.mitlab.com\\UESQLSVR;databaseName=Unifia";
		String uName = "Unifia";
		String pwd = "Olympu$123";
		
		DBVerification dbTest = new DBVerification(conn, uName, pwd);
		/*String strSql = "select * from ItemHistory;";
		
		List<String>listColValues = dbTest.getColumnValues(strSql, "LocationID_FK");
		System.out.println(listColValues);*/
		
		String strSql = String.format("SELECT [ItemHistoryID_PK]\r\n" + 
				"      ,[ScanItemID_FK]\r\n" + 
				"      ,[ScanItemTypeID_FK]\r\n" + 
				"      ,[DataSourceID]\r\n" + 
				"      ,[ReceivedDateTime]\r\n" + 
				"     \r\n" + 
				"      ,[IsStored]\r\n" + 
				"      ,[ProcessedDateTime]\r\n" + 
				"      ,[DataSourceTypeID_FK]\r\n" + 
				"      ,i.[LocationID_FK]\r\n" + 
				"      ,[AssociationID_FK]\r\n" + 
				"      ,[CycleEventID_FK]\r\n" + 
				"  ,st.Name\r\n" + 
				"  ,ls.LocationStateID_FK\r\n" + 
				"  FROM [Unifia].[dbo].[ItemHistory] i\r\n" + 
				"  Join Location l on i.LocationID_FK=l.LocationID_PK \r\n" + 
				"  join ScanItemType st on i.scanItemTypeID_FK=st.ScanItemTypeID_PK\r\n" + 
				"  join LocationStatus ls on ls.LocationID_FK=i.LocationID_FK\r\n" + 
				"  Where l.Name='%s' and convert(varchar(10),i.LastUpdatedDateTime,101) = convert(varchar(10),getdate(),101)\r\n" + 
				"", "Procedure Room 1");
		try {
			Map<String, List<String>> mapDB = dbTest.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK");
			System.out.println(mapDB.get("Name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
