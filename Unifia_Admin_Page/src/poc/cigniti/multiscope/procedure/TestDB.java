package poc.cigniti.multiscope.procedure;

import java.sql.SQLException;
import java.util.List;

import org.testng.annotations.Test;

public class TestDB {
	
	@Test
	public void testDBConnection() throws SQLException
	{
		String conn = "jdbc:sqlserver://SPRINTTEST-03.mitlab.com\\UESQLSVR;databaseName=Unifia";
		String uName = "Unifia";
		String pwd = "Olympu$123";
		
		DBVerification dbTest = new DBVerification(conn, uName, pwd);
		String strSql = "select * from ItemHistory;";
		
		List<String>listColValues = dbTest.getColumnValues(strSql, "LocationID_FK");
		System.out.println(listColValues);
	}

}
