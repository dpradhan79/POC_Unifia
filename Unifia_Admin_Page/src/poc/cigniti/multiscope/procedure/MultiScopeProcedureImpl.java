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
		this.scannerPage.isScannerCountValid(1);

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
		this.scannerPage.isScannerCountValid(2);

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
		this.scannerPage.isScannerCountValid(3);
		
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
		this.scannerPage.isScannerCountValid(4);

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
		this.scannerPage.isScannerCountValid(5);
		
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
		this.scannerPage.isScannerCountValid(6);
		
		// Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "In Use");
		this.unifiaPage.isProcedureRoomScopeUpdated("Procedure Room 1", new String [] {"QVLT0001", "QVLT0002"});
		
		// TODO - DB Validation
		
	}
	
	@Vertex
	public void v_Physician() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Physician");
		this.scannerPage.isScannerResponseValid("Scanned");
		this.scannerPage.isScannerCountValid(7);
		
		
		// TODO - DB Validation
		
	}
	
	@Vertex
	public void v_ProcedureStart() {
		
		//Scanner Validation
		this.scannerPage.isScannerResponseValid("Procedure Started");
		this.scannerPage.isScannerCountValid(8);
		
		//Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "In Use");
		this.unifiaPage.isProcedureRoomScopeUpdated("Procedure Room 1", new String [] {"QVLT0001", "QVLT0002"});
		
		// TODO - DB Validation
		
	}
	
	@Vertex
	public void v_ProcedureEnd() {
		
		//Scanner Validation
		this.scannerPage.isScannerResponseValid("Procedure Completed");
		this.scannerPage.isScannerCountValid(9);
		
		// TODO - DB Validation
		
	}
	
	@Vertex
	public void v_Scope1_PreClean() {
		
		//TODO - Scanner Validation
		this.scannerPage.isScannerResponseValid("Pre Clean Completed");
		this.scannerPage.isScannerCountValid(10);
		
		//Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "In Use");
		this.unifiaPage.isProcedureRoomScopeUpdated("Procedure Room 1", new String [] {"QVLT0002"});
		
		
		//TODO - DB Validation
		
	}
	
	@Vertex
	public void v_Staff1_PreClean() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Staff");
		this.scannerPage.isScannerResponseValid("Scanned");
		this.scannerPage.isScannerCountValid(11);
		
		//TODO - DB Validation
		
	}
	
	@Vertex
	public void v_Scope2_PreClean() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Pre Clean Completed");
		this.scannerPage.isScannerCountValid(12);
		
		//Dashboard Validation
		this.unifiaPage.isProcedureRoomStatusUpdated("Procedure Room 1", "In Use");
		
		//TODO - DB Validation
		
	}
	
	@Vertex
	public void v_Staff2_PreClean() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Staff");
		this.scannerPage.isScannerResponseValid("Scanned");
		this.scannerPage.isScannerCountValid(13);
		
		//TODO - DB Validation
		
	}
	
	@Vertex
	public void v_RoomNeedsCleaning() {
		
		//Scanner Validation
		this.scannerPage.isScannerResponseValid("Room Needs Cleaning");
		this.scannerPage.isScannerCountValid(14);
		
		//TODO - DB Validation
		
	}
}
