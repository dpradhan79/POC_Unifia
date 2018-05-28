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
	public void v_scope1() {
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
		
		// Dashboard Validation
		
		// TODO - DB Validation
		
	}
}
