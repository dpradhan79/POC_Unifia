package poc.cigniti.multiscope.procedure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBVerification {

	Connection connection = null;
	public DBVerification(String strConnection, String userName, String password) throws SQLException
	{
		String completeConnection = String.format("%s;user=%s;password=%s;", strConnection, userName, password);
		this.connection = DriverManager.getConnection(completeConnection);
	}
	
	public ResultSet getQueryExecutionResult(String sqlQuery) throws SQLException
	{
		ResultSet resultSet = null;
		Statement statement = this.connection.createStatement();
		resultSet = statement.executeQuery(sqlQuery);
		return resultSet;
	}
	
	public <T> List<T> colValues(String sqlQuery, String tableColumn) throws SQLException
	{
		List<T> listValues = null;
		ResultSet resultSet = this.getQueryExecutionResult(sqlQuery);
		while(resultSet.next())
		{
			Object obj = resultSet.getObject(tableColumn);
			
		}
		return listValues;
	}
}
