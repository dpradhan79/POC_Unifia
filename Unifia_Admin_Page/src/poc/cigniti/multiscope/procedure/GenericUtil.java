package poc.cigniti.multiscope.procedure;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericUtil {
	private static final Logger LOG = Logger.getLogger(GenericUtil.class);

	public static void forcedWait(int secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (Exception ex) {
		}

	}

	public static Boolean waitForPageToLoad(WebDriver webDriver, int timeOutInSecs)
	{
		WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecs);
		Boolean status = wait.until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver webDriver) {
				return ((JavascriptExecutor) webDriver).executeScript("return document.readyState").toString().equals("complete");
			}
		});
		
		return status;
	}
	public static Boolean waitUntilExpectedMessageAppears(WebDriver webDriver, final By byObject,
			final String expectedMessage, int timeOutInSecs) {
		WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecs);

		Boolean status = wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				WebElement webElement = webDriver.findElement(byObject);
				String actualMsg = webElement.getText();
				actualMsg = actualMsg.trim().replaceAll("\\s+", " ");
				return actualMsg.contains(expectedMessage);
			}
		});
		return status;
	}

	public static Boolean waitUntilExpectedAttributeAppears(WebDriver webDriver, final By byObject,
			final String attribute, final String expectedAttributeValue, int timeOutInSecs) {
		WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSecs);
		Boolean status = wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				WebElement webElement = webDriver.findElement(byObject);
				String actualMsg = webElement.getAttribute(attribute);
				actualMsg = actualMsg.trim().replaceAll("\\s+", " ");
				return actualMsg.contains(expectedAttributeValue);
			}
		});
		return status;
	}

	public static Boolean waitUntilListPopulates(WebDriver webDriver, final By byObject, int timeOutInSecs) {
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

	public static boolean fileExists(String sFileName) {
		try {
			File objFile = new File(sFileName);
			if (objFile.exists()) {
				return true;
			}
		}

		catch (Exception e) {
			LOG.error(e.getStackTrace());
		}
		return false;
	}

	public static boolean makeDir(String sFileName) {
		try {
			if (!fileExists(sFileName)) {
				File objFile = new File(sFileName);
				objFile.mkdir();
				return true;
			}
		}

		catch (Exception e) {
			LOG.error(e.getStackTrace());
		}
		return false;
	}
	
	private synchronized static String getScreenshotFile(String screenshotFolder, String extension)
	{
		String fileName = null;
		int count = 1;
		String strScreenshotFileName = String.format("%s%s%s_%d_%s_%d.%s", screenshotFolder, File.separatorChar, "Screenshot", Thread.currentThread().getId(), new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss_SSS").format(new Date()), count, extension);
		
		while(fileExists(strScreenshotFileName))
		{
			//get new file name
			//increase count
			strScreenshotFileName = String.format("%s%s%s_%d_%s_%d.%s", screenshotFolder, File.separatorChar, "Screenshot", Thread.currentThread().getId(),  new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss_SSS").format(new Date()), ++count, extension);
			
		}		
		fileName = strScreenshotFileName;
		return fileName;
	}
	
	public static synchronized String getScreenShotName() {		
		return GenericUtil.getScreenshotFile("ErrorScreenshots", "jpg");
	}

}
