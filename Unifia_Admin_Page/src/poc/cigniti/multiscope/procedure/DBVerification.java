package poc.cigniti.multiscope.procedure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class DBVerification {
	private static final Logger LOG = Logger.getLogger(DBVerification.class);
	private Connection connection = null;
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
	
	public List<String> getColumnValues(String sqlQuery, String tableColumn) throws SQLException
	{
		List<String> outColumnValues = new ArrayList<>();
		outColumnValues.clear();
		ResultSet resultSet = this.getQueryExecutionResult(sqlQuery);
		while(resultSet.next())
		{
			String colValue = resultSet.getString(tableColumn);				
			outColumnValues.add(colValue);
		}
		return outColumnValues;
		
	}
	
	public <T> List<T> getColumnValues(String sqlQuery, String tableColumn, T dataType) throws SQLException
	{
		List<T> outColumnValues = new ArrayList<>();
		outColumnValues.clear();
		ResultSet resultSet = this.getQueryExecutionResult(sqlQuery);
		while(resultSet.next())
		{
			Object obj = resultSet.getObject(tableColumn);	
			@SuppressWarnings("unchecked")
			T t = (T) obj;
			outColumnValues.add(t);
		}
		return outColumnValues;
		
	}
	
	public <T> void getColumnValues(String sqlQuery, String tableColumn, List<T> outColumnValues) throws SQLException
	{
		outColumnValues.clear();
		ResultSet resultSet = this.getQueryExecutionResult(sqlQuery);
		while(resultSet.next())
		{
			Object obj = resultSet.getObject(tableColumn);
			T t = (T) obj;
			outColumnValues.add(t);
		}
		
	}
	
	public Map<String, List<String>> getColumnValues(String sqlQuery, String ... tableColumns) throws SQLException 
	{
		List<String> list = new ArrayList<>();
		Map<String, List<String>> map = new HashMap<>();
		
		for(String tableColumn : tableColumns)
		{
			list = this.getColumnValues(sqlQuery, tableColumn);
			map.put(tableColumn, list);
		}		
		return map;
	}
	
	public <T> boolean isExpectedColumnValuesPresent(Map<String, List<T>> mapTableColumnKeyWithListOfExpectedValues, String sqlQuery) throws SQLException
	{
		boolean status = false;
		for(Entry<String, List<T>> mapEntry : mapTableColumnKeyWithListOfExpectedValues.entrySet())
		{
			String tableColumn = mapEntry.getKey();
			List<T> listExpectedColValues = mapEntry.getValue();
			T dataType = null;
			List<T> listActColValues = this.getColumnValues(sqlQuery, tableColumn, dataType);
			try
			{
				Assert.assertEquals(listActColValues, listExpectedColValues);
				LOG.info(String.format("%s ->Column - %s Expected Values - %s Match With Actual Values - %s", "Passed", tableColumn, listExpectedColValues, listActColValues));
			}
			catch(Exception ex)
			{
				LOG.info(String.format("%s ->Column - %s Expected Values - %s Does Not Match With Actual Values - %s", "Failed", tableColumn, listExpectedColValues, listActColValues));
				LOG.info(String.format("Expected Reason - Possibly this test executed under logical navigation test"));
			}
		}
		
		return status;
		
	}
}
