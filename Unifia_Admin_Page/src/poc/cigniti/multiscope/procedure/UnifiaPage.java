package poc.cigniti.multiscope.procedure;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UnifiaPage {
	private static final Logger LOG = Logger.getLogger(UnifiaPage.class);
	private WebDriver driverUnifia;

	public UnifiaPage(WebDriver driverUnifia) {
		this.driverUnifia = driverUnifia;
	}

	public boolean isProcedureRoomStatusUpdated(String procedureRoom, String expectedStatus) {
		boolean status = false;
		String xpathProcedureRoomStatus = String
				.format("//span[text() = '%s']/parent::div/following-sibling::div[1]", procedureRoom);	
		String actualStatus = null;
		try {
			GenericUtil.waitUntilExpectedMessageAppears(this.driverUnifia, By.xpath(xpathProcedureRoomStatus),
					expectedStatus, ITestConstants.implicitTimeOut);
			actualStatus = this.driverUnifia.findElement(By.xpath(xpathProcedureRoomStatus)).getText();
			LOG.info(String.format("%s - Expected Status - %s Matches Actual Status - %s", "Passed", expectedStatus,
					actualStatus));
			status = true;
		} catch (Exception ex) {
			actualStatus = this.driverUnifia.findElement(By.xpath(xpathProcedureRoomStatus)).getText();			
			LOG.info(String.format("%s - Expected Status - %s Does Not Match Actual Status - %s", "Failed",
					expectedStatus, actualStatus));
			LOG.info(String.format("Expected Reason - Possibly this test executed under logical navigation test"));
		}

		return status;
	}
	
	public boolean isProcedureRoomScopeUpdated(String procedureRoom, String [] arrayScopeNames)
	{
		boolean status = false;
		List<Boolean>statusList = new ArrayList<>();
		String xpathProcedureRoomScopeList = String
				.format("//span[text() = '%s']/parent::div/following-sibling::div[2]/div", procedureRoom);
		List<WebElement> listElements = this.driverUnifia.findElements(By.xpath(xpathProcedureRoomScopeList));
		List<String> listElementsText = new ArrayList<>();
		for(WebElement element : listElements)
		{
			listElementsText.add(element.getText());
		}
		for(String scopeName : arrayScopeNames)
		{
			if(listElementsText.contains(scopeName))
			{
				LOG.info(String.format("%s->, %s Found In Actual Scope List - %s", "Passed", scopeName, listElementsText));
				statusList.add(true);
			}
			else
			{
				LOG.info(String.format("%s->, %s Was Not Found In Actual Scope List - %s", "Failed", scopeName, listElementsText));
				LOG.info(String.format("Expected Reason - Possibly this test executed under logical navigation test"));
				statusList.add(false);
			}
		}
		if(!statusList.contains(false))
		{
			status = true;
		}		
		return status;
	}

}
