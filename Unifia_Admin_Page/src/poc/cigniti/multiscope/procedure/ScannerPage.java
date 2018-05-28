package poc.cigniti.multiscope.procedure;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScannerPage {
	private static final Logger LOG = Logger.getLogger(ScannerPage.class);
	private WebDriver driverScanner;
	private WebDriverWait wait = null;

	public ScannerPage(WebDriver driverScanner) {
		this.driverScanner = driverScanner;
		this.wait = new WebDriverWait(this.driverScanner, ITestConstants.implicitTimeOut);
	}

	public void scanItem(String location, String scanType, String scanItem) {
		// Location
		GenericUtil.waitUntilListPopulates(this.driverScanner, By.id("UID_DropDownLocations"), ITestConstants.implicitTimeOut);
		Select scannerLocation = new Select(this.driverScanner.findElement(By.id("UID_DropDownLocations")));		
		scannerLocation.selectByVisibleText(location);

		// scanType
		String xpathByLocationWait = "//tr[@id='idLocationOriginal']//span[@data-bind='text: myLocationSelected().Name']";
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpathByLocationWait), location));
		
		GenericUtil.waitUntilListPopulates(this.driverScanner, By.id("UID_DropDownScanType"), ITestConstants.implicitTimeOut);
		Select scannerScanType = new Select(this.driverScanner.findElement(By.id("UID_DropDownScanType")));
		scannerScanType.selectByVisibleText(scanType);

		// ScanItem
		GenericUtil.waitUntilListPopulates(this.driverScanner, By.id("UID_DropDownScanItem"), ITestConstants.implicitTimeOut);
		Select scannerScanItem = new Select(this.driverScanner.findElement(By.id("UID_DropDownScanItem")));
		scannerScanItem.selectByVisibleText(scanItem);

		// Scan Button
		this.driverScanner.findElement(By.id("UID_ButtonScan")).click();
	}

	public boolean isScannerResponseValid(final String expectedMsg) {
		boolean status = false;
		final String xpathScanMsg = "//*[@id='ScannerEntryPad']/tbody/tr[1]/td/textarea";
		String actualMsg = null;
		try {
			GenericUtil.waitUntilExpectedAttributeAppears(this.driverScanner, By.xpath(xpathScanMsg), "value",
					expectedMsg, ITestConstants.implicitTimeOut);			
			status = true;
			LOG.info(String.format("Passed - Expected Scanner Message - %s Matches With Actual Message - %s",
					expectedMsg, actualMsg));			
		} catch (WebDriverException ex) {
			LOG.info(String.format("Failed - Expected Scanner Message - %s Does Not Match With Actual Message - %s",
					expectedMsg, actualMsg));
		}
		return status;

	}

	public boolean isScannerCountValid(int iExpectedCount) {
		boolean status = false;
		String xpathScanCount = "//*[@id='ScanCount']";
		WebElement webElement = this.driverScanner.findElement(By.xpath(xpathScanCount));

		String actualCount = webElement.getText();
		int iActualCount = Integer.parseInt(actualCount);
		if (iActualCount == iExpectedCount) {
			status = true;
			LOG.info(String.format("Passed - Expected Scanner Count - %d Matches With Actual Message - %d",
					iExpectedCount, iActualCount));
		} else {
			LOG.info(String.format("Failed - Expected Scanner Count - %d Does Not Match With Actual Count - %d",
					iExpectedCount, iActualCount));
		}
		return status;
	}

}
