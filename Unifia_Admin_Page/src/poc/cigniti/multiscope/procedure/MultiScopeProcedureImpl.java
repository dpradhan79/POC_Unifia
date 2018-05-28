package poc.cigniti.multiscope.procedure;

import org.apache.log4j.Logger;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.Edge;
import org.graphwalker.java.annotation.Vertex;
import org.openqa.selenium.WebDriver;

public class MultiScopeProcedureImpl extends ExecutionContext {
	private static final Logger LOG = Logger.getLogger(MultiScopeProcedureImpl.class);
	WebDriver driverScanner = null;
	WebDriver driverUnifia = null;
	ScannerPage scannerPage = null;
	UnifiaPage unifiaPage = null;

	public MultiScopeProcedureImpl() {
	}

	public MultiScopeProcedureImpl(WebDriver driverScanner, WebDriver driverUnifia) {
		this.driverScanner = driverScanner;
		this.driverUnifia = driverUnifia;
		this.scannerPage = new ScannerPage(this.driverScanner);
		this.unifiaPage = new UnifiaPage(this.driverUnifia);

	}

	@Edge
	public void e_scanRoomAvailable() {

		this.scannerPage.scanItem("Procedure Room 1", "Workflow Event", "Available");

	}

	@Edge
	public void e_scanScope1() {
		this.scannerPage.scanItem("Procedure Room 1", "Scope", "QVLT0001");
	}

	@Edge
	public void e_scanStaff1() {
		this.scannerPage.scanItem("Procedure Room 1", "Staff", "F1_FN_001 F1_LN_001(F1001)");
	}
	
	@Edge
	public void e_scanScope2() {
		this.scannerPage.scanItem("Procedure Room 1", "Scope", "QVLT0002");
	}
	
	@Edge
	public void e_scanStaff2() {
		this.scannerPage.scanItem("Procedure Room 1", "Staff", "F1_FN_002 F1_LN_002(F1002)");
	}
	
	@Edge
	public void e_scanPatient() {
		this.scannerPage.scanItem("Procedure Room 1", "Patient", "F1X000001");
	}
	
	@Edge
	public void e_scanPhysician() {
		//this.scannerPage.scanItem("Procedure Room 1", "Staff", "Physician", "F1_DR_001 F1_PHYS_001(F1Dr001)");
	}

	@Vertex
	public void v_RoomAvailable() {
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Room Available");
		this.scannerPage.isScannerCountValid(1);

		// Dashboard Validation
		this.unifiaPage.isProcedureRoomUpdated("Procedure Room 1", "Available");

		// TODO - DB Validation

	}

	@Vertex
	public void v_Scope1() {
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Hang time");
		this.scannerPage.isScannerResponseValid("days");
		this.scannerPage.isScannerCountValid(2);

		// Dashboard Validation
		this.unifiaPage.isProcedureRoomUpdated("Procedure Room 1", "QVLT0001");

		// TODO - DB Validation
	}

	@Vertex
	public void v_Staff1() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Staff");
		this.scannerPage.isScannerResponseValid("Scanned");
		this.scannerPage.isScannerCountValid(3);
		
		// TODO - DB Validation
		
	}
	
	@Vertex
	public void v_Scope2() {
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Hang time");
		this.scannerPage.isScannerResponseValid("days");
		this.scannerPage.isScannerCountValid(4);

		// Dashboard Validation
		this.unifiaPage.isProcedureRoomUpdated("Procedure Room 1", "QVLT0002");

		// TODO - DB Validation
	}
	
	@Vertex
	public void v_Staff2() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Staff");
		this.scannerPage.isScannerResponseValid("Scanned");
		this.scannerPage.isScannerCountValid(5);
		
		// TODO - DB Validation
		
	}
	
	@Vertex
	public void v_Patient() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Patient");
		this.scannerPage.isScannerResponseValid("Scanned");
		this.scannerPage.isScannerCountValid(6);
		
		// Dashboard Validation
		this.unifiaPage.isProcedureRoomUpdated("Procedure Room 1", "In Use");
		
		// TODO - DB Validation
		
	}
	
	@Vertex
	public void v_Physician() {
		
		// Scanner Validation
		this.scannerPage.isScannerResponseValid("Physician");
		this.scannerPage.isScannerResponseValid("Scanned");
		this.scannerPage.isScannerCountValid(7);
		
		// Dashboard Validation
		this.unifiaPage.isProcedureRoomUpdated("Procedure Room 1", "In Use");
		
		// TODO - DB Validation
		
	}
}
