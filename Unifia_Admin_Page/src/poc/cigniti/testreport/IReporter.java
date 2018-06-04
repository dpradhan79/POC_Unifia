package poc.cigniti.testreport;

import org.openqa.selenium.WebDriver;

/**
 * 
 * @author E001518 - Debasish Pradhan (Architect)
 *
 */
public interface IReporter {
	
	 void initTestCase(String testcaseName); 	 
	 
	 void createTestNgXMLTestTag(String testCaseName);	 
	 
	 void logSuccess(String stepName);
	 void logSuccess(String stepName, String stepDescription);
	 void logSuccess(String stepName, String stepDescription, String screenShotPath, WebDriver driver);
	 
	 void logFailure(String stepName);
	 void logFailure(String stepName, String stepDescription);
	 void logFailure(String stepName, String stepDescription, String screenShotPath, WebDriver driver);
	 
	 void logInfo(String message);
	 void logInfo(String message, String screenShotPath, WebDriver driver);
	 
	 void logWarning(String stepName);
	 void logWarning(String stepName, String stepDescription);
	 void logWarning(String stepName, String stepDescription, String screenShotPath, WebDriver driver);
	 
	 void logException(Throwable ex);
	 void logException(Throwable ex, String screenShotPath, WebDriver driver);
	 
	 void logFatal(Throwable ex);
	 void logFatal(Throwable ex, String screenShotPath, WebDriver driver);
	 
	 
	 void updateTestCaseStatus();
	 void close();
	 void manipulateTestReport(ITestReportManipulator objTestReportManipulator);
}
