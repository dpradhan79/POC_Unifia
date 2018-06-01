package poc.cigniti.multiscope.procedure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.Edge;
import org.graphwalker.java.annotation.Vertex;
import org.openqa.selenium.WebDriver;

public class MultiScopeProcedureImpl extends ExecutionContext {
	private static final Logger LOG = Logger.getLogger(MultiScopeProcedureImpl.class);
	private WebDriver driverScanner = null;
	private WebDriver driverUnifia = null;
	private ScannerPage scannerPage = null;
	private UnifiaPage unifiaPage = null;
	private DBVerification dbVerification = null;
	private String utcExecutionTime = null;

	public MultiScopeProcedureImpl() {
	}

	public MultiScopeProcedureImpl(WebDriver driverScanner, WebDriver driverUnifia) {
		this.driverScanner = driverScanner;
		this.driverUnifia = driverUnifia;
		this.scannerPage = new ScannerPage(this.driverScanner);
		this.unifiaPage = new UnifiaPage(this.driverUnifia);
		/*DB Initialization */
		String DBConnection = "jdbc:sqlserver://SPRINTTEST-03.mitlab.com\\UESQLSVR;databaseName=Unifia";
		String userName = "Unifia";
		String password = "Olympu$123";		
		try {
			this.dbVerification = new DBVerification(DBConnection, userName, password);			
			this.utcExecutionTime = this.dbVerification.getColumnValues("select GETUTCDATE() as CurExecTime", "CurExecTime").get(0);
		} catch (SQLException ex) {	
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}

	@Edge
	public void e_scanRoomAvailable() {	
		this.scannerPage.scanItem("Procedure Room 1", "Workflow Event", null, "Available");

	}

	@Edge
	public void e_scanScope1() {
		this.scannerPage.scanItem("Procedure Room 1", "Scope", null, "QVLT0001");
	}

	@Edge
	public void e_scanStaff1() {
		this.scannerPage.scanItem("Procedure Room 1", "Staff", null, "F1_FN_001 F1_LN_001(F1001)");
	}
	
	@Edge
	public void e_scanScope2() {
		this.scannerPage.scanItem("Procedure Room 1", "Scope", null, "QVLT0002");
	}
	
	@Edge
	public void e_scanStaff2() {
		this.scannerPage.scanItem("Procedure Room 1", "Staff", null, "F1_FN_002 F1_LN_002(F1002)");
	}
	
	@Edge
	public void e_scanPatient() {
		this.scannerPage.scanItem("Procedure Room 1", "Patient", null, "F1X000001");
	}
	
	@Edge
	public void e_scanPhysician() {
		this.scannerPage.scanItem("Procedure Room 1", "Staff", "Physician", "F1_DR_001 F1_PHYS_001(F1Dr001)");
	}
	
	@Edge
	public void e_scanProcedureStart() {
		this.scannerPage.scanItem("Procedure Room 1", "Workflow Event", null, "Procedure Start");
	}
	
	@Edge
	public void e_scanProcedureEnd() {
		this.scannerPage.scanItem("Procedure Room 1", "Workflow Event", null, "Procedure End");
	}
	
	@Edge
	public void e_scanRoomNeedsCleaning() {
		this.scannerPage.scanItem("Procedure Room 1", "Workflow Event", null, "Needs Cleaning");
	}

	@Vertex
	public void v_RoomAvailable() {
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Room Available");		

		// Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "Available");

		// DB Validation
		String strSql = String.format(ITestConstants.strSQLForWorkFlowValidation, "Procedure Room 1", "Workflow Event", "10");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("LocationStateID_FK").contains("10"))
			{
				LOG.info(String.format("%s, The Workflow Event Was Not Found With Required State", "Failed"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Vertex
	public void v_Scope1() {
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Hang time");
		this.scannerPage.isScannerResponseValid("days");		

		// Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "Available");
		this.unifiaPage.isProcedureRoomScopeUpdated("Procedure Room 1", new String [] {"QVLT0001"});

		// DB Validation
		String strSql = String.format(ITestConstants.strSQLForScopeAndStaffValidation, "Procedure Room 1", "Scope", "3");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("CycleEventID_FK").contains("3"))
			{
				LOG.info(String.format("%s, The Scope Was Not Found Associated", "Failed"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Vertex
	public void v_Staff1() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Staff");
		this.scannerPage.isScannerResponseValid("Scanned");		
		
		// DB Validation
		String strSql = String.format(ITestConstants.strSQLForScopeAndStaffValidation, "Procedure Room 1", "Staff", "38");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("CycleEventID_FK").contains("38"))
			{
				LOG.info(String.format("%s, The Scope Was Not Found Associated", "Failed"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Vertex
	public void v_Scope2() {
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Hang time");
		this.scannerPage.isScannerResponseValid("days");		

		// Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "Available");
		this.unifiaPage.isProcedureRoomScopeUpdated("Procedure Room 1", new String [] {"QVLT0001", "QVLT0002"});

		// DB Validation
		String strSql = String.format(ITestConstants.strSQLForScopeAndStaffValidation, "Procedure Room 1", "Scope", "3");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("CycleEventID_FK").contains("3"))
			{
				LOG.info(String.format("%s, The Scope Was Not Found Associated", "Failed"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Vertex
	public void v_Staff2() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Staff");
		this.scannerPage.isScannerResponseValid("Scanned");		
		
		// DB Validation
		String strSql = String.format(ITestConstants.strSQLForScopeAndStaffValidation, "Procedure Room 1", "Staff", "38");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("CycleEventID_FK").contains("38"))
			{
				LOG.info(String.format("%s, The Scope Was Not Found Associated", "Failed"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Vertex
	public void v_Patient() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Patient");
		this.scannerPage.isScannerResponseValid("Scanned");		
		
		// Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "In Use");
		this.unifiaPage.isProcedureRoomScopeUpdated("Procedure Room 1", new String [] {"QVLT0001", "QVLT0002"});
		
		// DB Validation
		String strSql = String.format(ITestConstants.strSqlForMultipleRowsCreatedInItemHistoryWithCycleEventUpdated, "4", this.utcExecutionTime);
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || (mapDB.get("CycleEventID_FK").size() < 3))
			{
				LOG.info(String.format("%s, Actual Rows - %d Found < 3", "Failed", mapDB.get("CycleEventID_FK").size()));
			}
			else
			{
				LOG.info(String.format("%s, Actual Rows - %d Found Linked To Association ", "Passed", mapDB.get("CycleEventID_FK").size()));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Vertex
	public void v_Physician() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Physician");
		this.scannerPage.isScannerResponseValid("Scanned");			
		
		// DB Validation
		String strSql = String.format(ITestConstants.strSqlForMultipleRowsCreatedInItemHistoryWithCycleEventUpdated, "5", this.utcExecutionTime);
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || (mapDB.get("CycleEventID_FK").size() < 3))
			{
				LOG.info(String.format("%s, Actual Rows - %d Found < 3", "Failed", mapDB.get("CycleEventID_FK").size()));
			}
			else
			{
				LOG.info(String.format("%s, Actual Rows - %d Found Linked To Association ", "Passed", mapDB.get("CycleEventID_FK").size()));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Vertex
	public void v_ProcedureStart() {
		
		//Scanner Validation
		this.scannerPage.isScannerResponseValid("Procedure Started");		
		
		//Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "In Use");
		this.unifiaPage.isProcedureRoomScopeUpdated("Procedure Room 1", new String [] {"QVLT0001", "QVLT0002"});
		
		// DB Validation
		String strSql = String.format(ITestConstants.strSqlForMultipleRowsCreatedInItemHistoryWithCycleEventUpdated, "6", this.utcExecutionTime);
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || (mapDB.get("CycleEventID_FK").size() < 3))
			{
				LOG.info(String.format("%s, Actual Rows - %d Found < 3", "Failed", mapDB.get("CycleEventID_FK").size()));
			}
			else
			{
				LOG.info(String.format("%s, Actual Rows - %d Found Linked To Association ", "Passed", mapDB.get("CycleEventID_FK").size()));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Vertex
	public void v_ProcedureEnd() {
		
		//Scanner Validation
		this.scannerPage.isScannerResponseValid("Procedure Completed");		
		
		// DB Validation
		String strSql = String.format(ITestConstants.strSqlForMultipleRowsCreatedInItemHistoryWithCycleEventUpdated, "7", this.utcExecutionTime);
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || (mapDB.get("CycleEventID_FK").size() < 3))
			{
				LOG.info(String.format("%s, Actual Rows - %d Found < 3", "Failed", mapDB.get("CycleEventID_FK").size()));
			}
			else
			{
				LOG.info(String.format("%s, Actual Rows - %d Found Linked To Association ", "Passed", mapDB.get("CycleEventID_FK").size()));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Vertex
	public void v_Scope1_PreClean() {
		
		//Scanner Validation
		this.scannerPage.isScannerResponseValid("Pre Clean Completed");		
		
		//Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "In Use");
		this.unifiaPage.isProcedureRoomScopeUpdated("Procedure Room 1", new String [] {"QVLT0002"});
		
		
		//DB Validation
		String strSql = String.format(ITestConstants.strSQLForScopeAndStaffValidation, "Procedure Room 1", "Scope", "8");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK", "ScopeStateID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("CycleEventID_FK").contains("8"))
			{
				LOG.info(String.format("%s, The Scope Was Not Found Associated", "Failed"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Vertex
	public void v_Staff1_PreClean() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Staff");
		this.scannerPage.isScannerResponseValid("Scanned");		
		
		//DB Validation
		String strSql = String.format(ITestConstants.strSQLForScopeAndStaffValidation, "Procedure Room 1", "Staff", "39");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("CycleEventID_FK").contains("39"))
			{
				LOG.info(String.format("%s, The Scope Was Not Found Associated", "Failed"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Vertex
	public void v_Scope2_PreClean() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Pre Clean Completed");		
		
		//Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "In Use");
		
		//DB Validation
		String strSql = String.format(ITestConstants.strSQLForScopeAndStaffValidation, "Procedure Room 1", "Scope", "8");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK",  "ScopeStateID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("CycleEventID_FK").contains("8"))
			{
				LOG.info(String.format("%s, The Scope Was Not Found Associated", "Failed"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Vertex
	public void v_Staff2_PreClean() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Staff");
		this.scannerPage.isScannerResponseValid("Scanned");		
		
		//DB Validation
		String strSql = String.format(ITestConstants.strSQLForScopeAndStaffValidation, "Procedure Room 1", "Staff", "39");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("CycleEventID_FK").contains("39"))
			{
				LOG.info(String.format("%s, The Scope Was Not Found Associated", "Failed"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Vertex
	public void v_RoomNeedsCleaning() {
		
		//Scanner Validation
		this.scannerPage.isScannerResponseValid("Room Needs Cleaning");		
		
		//DB Validation
		String strSql = String.format(ITestConstants.strSQLForWorkFlowValidation, "Procedure Room 1", "Workflow Event", "12");
		try {						
			Map<String, List<String>> mapDB = this.dbVerification.getColumnValues(strSql, "Name", "LocationID_FK", "LocationStateID_FK", "AssociationID_FK", "CycleEventID_FK");
			for(Entry<String, List<String>> mapEntry : mapDB.entrySet())
			{
				LOG.info(String.format("Values In %s -> %s", mapEntry.getKey(), mapEntry.getValue()));
			}
			if(mapDB.isEmpty() || !mapDB.get("LocationStateID_FK").contains("12"))
			{
				LOG.info(String.format("%s, The Workflow Event Was Not Found With Required State", "Failed"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
