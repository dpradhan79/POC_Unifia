package poc.cigniti.multiscope.procedure;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UnifiaPage {
	private static final Logger LOG = Logger.getLogger(UnifiaPage.class);
	private WebDriver driverUnifia;

	public UnifiaPage(WebDriver driverUnifia) {
		this.driverUnifia = driverUnifia;
	}

	public boolean isProcedureRoomUpdated(String procedureRoom, String expectedStatus) {
		boolean status = false;
		String xpathProcedureRoomStatus = String
				.format("//span[contains(text(), '%s')]/parent::div/following-sibling::div", procedureRoom);
		String actualStatus = this.driverUnifia.findElement(By.xpath(xpathProcedureRoomStatus)).getText();
		try {
			GenericUtil.waitUntilExpectedMessageAppears(this.driverUnifia, By.xpath(xpathProcedureRoomStatus),
					expectedStatus, ITestConstants.implicitTimeOut);
			LOG.info(String.format("%s - Expected Status - %s Matches Actual Status - %s", "Passed", expectedStatus,
					actualStatus));
			status = true;
		} catch (Exception ex) {
			LOG.info(String.format("%s - Expected Status - %s Does Not Match Actual Status - %s", "Failed",
					expectedStatus, actualStatus));
		}

		return status;
	}

}
