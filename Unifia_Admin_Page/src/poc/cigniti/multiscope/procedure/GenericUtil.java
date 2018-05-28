package poc.cigniti.multiscope.procedure;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericUtil {
	
	public static void forcedWait(int secs)
	{
		try
		{
			Thread.sleep(secs*1000);
		}
		catch(Exception ex) {}
		
	}
	
	public static void waitUntilExpectedMessageAppears(WebDriver webDriver, final By byObject, final String expectedMessage, int timeOutInSecs)
	{
		WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecs);
		
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {				
				WebElement webElement = webDriver.findElement(byObject);
				String actualMsg = webElement.getText();
				actualMsg = actualMsg.trim().replaceAll("\\s+"," ");
				return actualMsg.contains(expectedMessage);
			}
		});		
	}
	
	public static Boolean waitUntilExpectedAttributeAppears(WebDriver webDriver, final By byObject, final String attribute, final String expectedAttributeValue, int timeOutInSecs)
	{
		WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecs);		
		Boolean status = wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {				
				WebElement webElement = webDriver.findElement(byObject);
				String actualMsg = webElement.getAttribute(attribute);
				actualMsg = actualMsg.trim().replaceAll("\\s+"," ");
				return actualMsg.contains(expectedAttributeValue);
			}
		});	
		return status;
	}
	
	public static Boolean waitUntilListPopulates(WebDriver webDriver, final By byObject, int timeOutInSecs)
	{
		WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecs);
		
		Boolean status = wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {				
				Select selectElement = new Select(webDriver.findElement(byObject));
				return (selectElement.getOptions().size() > 1);
			}
		});		
		return status;
	}
	
	//dummy method
	public void foobar() {}
	
	

}
