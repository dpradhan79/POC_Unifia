package poc.cigniti.multiscope.procedure;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import poc.cigniti.testreport.IReporter;

public class ScannerPage {
	private static final Logger LOG = Logger.getLogger(ScannerPage.class);
	private WebDriver driverScanner;
	private WebDriverWait wait = null;
	private IReporter testReporter = null;
	
	public ScannerPage(WebDriver driverScanner, IReporter testReporter) {
		this.driverScanner = driverScanner;
		this.wait = new WebDriverWait(this.driverScanner, ITestConstants.implicitTimeOut);
		this.testReporter = testReporter;
	}

	public void scanItem(String location, String scanType, String scanSubType, String scanItem) {
		// Location
		try
		{
		GenericUtil.waitUntilListPopulates(this.driverScanner, By.id("UID_DropDownLocations"), ITestConstants.implicitTimeOut);
		Select scannerLocation = new Select(this.driverScanner.findElement(By.id("UID_DropDownLocations")));		
		scannerLocation.selectByVisibleText(location);

		// scanType
		String xpathByLocationWait = "//tr[@id='idLocationOriginal']//span[@data-bind='text: myLocationSelected().Name']";
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpathByLocationWait), location));
		
		GenericUtil.waitUntilListPopulates(this.driverScanner, By.id("UID_DropDownScanType"), ITestConstants.implicitTimeOut);
		Select scannerScanType = new Select(this.driverScanner.findElement(By.id("UID_DropDownScanType")));
		scannerScanType.selectByVisibleText(scanType);
		
		//scansubtype
		if(scanSubType != null)
		{					
			GenericUtil.waitUntilListPopulates(this.driverScanner, By.id("UID_DropDownScanSubType"), ITestConstants.implicitTimeOut);
			Select scannerScanSubType = new Select(this.driverScanner.findElement(By.id("UID_DropDownScanSubType")));
			scannerScanSubType.selectByVisibleText(scanSubType);
			
		}
		// ScanItem
		GenericUtil.waitUntilListPopulates(this.driverScanner, By.id("UID_DropDownScanItem"), ITestConstants.implicitTimeOut);
		Select scannerScanItem = new Select(this.driverScanner.findElement(By.id("UID_DropDownScanItem")));
		scannerScanItem.selectByVisibleText(scanItem);

		// Scan Button
		this.driverScanner.findElement(By.id("UID_ButtonScan")).click();
		if(scanSubType != null)
		{
		this.testReporter.logSuccess("scanItem", String.format("Location - %s, ScanType - %s, ScanSubType - %s, ScanItem - %s Was Scanned", location, scanType, scanSubType, scanItem));
		}
		else
		{
			this.testReporter.logSuccess("scanItem", String.format("Location - %s, ScanType - %s, ScanItem - %s Was Scanned", location, scanType, scanItem));
		}
		}
		catch(Exception ex)
		{
			this.testReporter.logException(ex, GenericUtil.getScreenShotName(), this.driverScanner);
		}
	}

	public boolean isScannerResponseValid(final String expectedMsg) {
		boolean status = false;
		final String xpathScanMsg = "//*[@id='ScannerEntryPad']/tbody/tr[1]/td/textarea";
		String actualMsg = null;
		try {
			GenericUtil.waitUntilExpectedAttributeAppears(this.driverScanner, By.xpath(xpathScanMsg), "value",
					expectedMsg, ITestConstants.implicitTimeOut);
			WebElement webElement = driverScanner.findElement(By.xpath(xpathScanMsg));
			actualMsg = webElement.getAttribute("value");
			actualMsg = actualMsg.trim().replaceAll("\\s+", " ");
			status = true;
			LOG.info(String.format("Passed - Expected Scanner Message - %s Matches With Actual Message - %s",
					expectedMsg, actualMsg));	
			this.testReporter.logSuccess("isScannerResponseValid", String.format("Passed - Expected Scanner Message - %s Matches With Actual Message - %s",
					expectedMsg, actualMsg));
		} catch (WebDriverException ex) {
			WebElement webElement = driverScanner.findElement(By.xpath(xpathScanMsg));
			actualMsg = webElement.getAttribute("value");
			actualMsg = actualMsg.trim().replaceAll("\\s+", " ");
			LOG.info(String.format("Failed - Expected Scanner Message - %s Does Not Match With Actual Message - %s",
					expectedMsg, actualMsg));
			LOG.info(String.format("Expected Reason - Possibly this test executed under logical navigation test"));
			this.testReporter.logFailure("isScannerResponseValid", String.format("Failed - Expected Scanner Message - %s Does Not Match With Actual Message - %s",
					expectedMsg, actualMsg), GenericUtil.getScreenShotName(), this.driverScanner);
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
			this.testReporter.logSuccess("isScannerCountValid", String.format("Passed - Expected Scanner Count - %d Matches With Actual Message - %d",
					iExpectedCount, iActualCount));
		} else {
			LOG.info(String.format("Failed - Expected Scanner Count - %d Does Not Match With Actual Count - %d",
					iExpectedCount, iActualCount));
			this.testReporter.logFailure("isScannerCountValid", String.format("Failed - Expected Scanner Count - %d Does Not Match With Actual Count - %d",
					iExpectedCount, iActualCount), GenericUtil.getScreenShotName(), this.driverScanner);
		}
		return status;
	}

}
