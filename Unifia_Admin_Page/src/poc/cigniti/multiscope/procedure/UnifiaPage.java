package poc.cigniti.multiscope.procedure;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import poc.cigniti.testreport.IReporter;

public class UnifiaPage {
	private static final Logger LOG = Logger.getLogger(UnifiaPage.class);
	private WebDriver driverUnifia;
	private IReporter testReporter = null;

	public UnifiaPage(WebDriver driverUnifia, IReporter testReporter) {
		this.driverUnifia = driverUnifia;
		this.testReporter = testReporter;
	}

	public boolean isProcedureRoomStatusUpdated(String procedureRoom, String expectedStatus) {
		boolean status = false;
		String xpathProcedureRoomStatus = String
				.format("//span[text() = '%s']/parent::div/following-sibling::div[1]", procedureRoom);	
		String actualStatus = null;
		try
		{
			GenericUtil.waitForPageToLoad(this.driverUnifia, ITestConstants.pageLoadTimeOut);
		}
		catch(Exception ex) {};
		try {
			GenericUtil.waitUntilExpectedMessageAppears(this.driverUnifia, By.xpath(xpathProcedureRoomStatus),
					expectedStatus, ITestConstants.implicitTimeOut);
			actualStatus = this.driverUnifia.findElement(By.xpath(xpathProcedureRoomStatus)).getText();
			LOG.info(String.format("%s - Expected Status - %s Matches Actual Status - %s", "Passed", expectedStatus,
					actualStatus));
			this.testReporter.logSuccess("isProcedureRoomStatusUpdated", String.format("%s - Expected Status - %s Matches Actual Status - %s", "Passed", expectedStatus,
					actualStatus));
			
			status = true;
		} catch (Exception ex) {
			actualStatus = this.driverUnifia.findElement(By.xpath(xpathProcedureRoomStatus)).getText();			
			LOG.info(String.format("%s - Expected Status - %s Does Not Match Actual Status - %s", "Failed",
					expectedStatus, actualStatus));
			LOG.info(String.format("Expected Reason - Possibly this test executed under logical navigation test"));
			this.testReporter.logFailure("isProcedureRoomStatusUpdated", String.format("%s - Expected Status - %s Does Not Match Actual Status - %s", "Failed",
					expectedStatus, actualStatus), GenericUtil.getScreenShotName(), this.driverUnifia);			
		}

		return status;
	}
	
	public boolean isProcedureRoomScopeUpdated(String procedureRoom, String [] arrayScopeNames)
	{
		boolean status = false;
		List<Boolean>statusList = new ArrayList<>();
		String xpathProcedureRoomScopeList = String
				.format("//span[text() = '%s']/parent::div/following-sibling::div[2]/div", procedureRoom);
		try
		{
			GenericUtil.forcedWait(3);
			GenericUtil.waitForPageToLoad(this.driverUnifia, ITestConstants.pageLoadTimeOut);
		}
		catch(Exception ex) {};
		List<WebElement> listElements = this.driverUnifia.findElements(By.xpath(xpathProcedureRoomScopeList));
		List<String> listElementsText = new ArrayList<>();
		/*for(WebElement element : listElements)
		{
			listElementsText.add(element.getText());
		}*/
		for(int countElement = 0; countElement < listElements.size(); countElement ++ )
		{
			listElements = this.driverUnifia.findElements(By.xpath(xpathProcedureRoomScopeList));
			try
			{
			listElementsText.add(listElements.get(countElement).getText());
			}
			catch(StaleElementReferenceException ex)
			{
				listElements = this.driverUnifia.findElements(By.xpath(xpathProcedureRoomScopeList));
				listElementsText.add(listElements.get(countElement).getText());
			}
		}
		for(String scopeName : arrayScopeNames)
		{
			if(listElementsText.contains(scopeName))
			{
				LOG.info(String.format("%s->, %s Found In Actual Scope List - %s", "Passed", scopeName, listElementsText));
				this.testReporter.logSuccess("isProcedureRoomScopeUpdated", String.format("%s->, %s Found In Actual Scope List - %s", "Passed", scopeName, listElementsText));
				statusList.add(true);
			}
			else
			{
				LOG.info(String.format("%s->, %s Was Not Found In Actual Scope List - %s", "Failed", scopeName, listElementsText));
				LOG.info(String.format("Expected Reason - Possibly this test executed under logical navigation test"));
				statusList.add(false);
				this.testReporter.logFailure("isProcedureRoomScopeUpdated", String.format("%s->, %s Was Not Found In Actual Scope List - %s", "Failed", scopeName, listElementsText), GenericUtil.getScreenShotName(), this.driverUnifia);
			}
		}
		if(!statusList.contains(false))
		{
			status = true;
		}		
		return status;
	}

}
