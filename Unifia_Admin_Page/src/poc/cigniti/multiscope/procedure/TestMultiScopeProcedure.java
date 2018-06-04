package poc.cigniti.multiscope.procedure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;
import poc.cigniti.testreport.ExtentReporter;
import poc.cigniti.testreport.ExtentReporter.ExtentTestVisibilityMode;
import poc.cigniti.testreport.IReporter;
import poc.cigniti.testreport.ReportFactory;
import poc.cigniti.testreport.ReportFactory.ReportType;

public class TestMultiScopeProcedure {
	private static final Logger LOG = Logger.getLogger(TestMultiScopeProcedure.class);
	private WebDriver driverScanner = null;
	private WebDriver driverUnifia = null;
	private String urlScanner = "https://sprinttest-03.mitlab.com:9751";
	private String urlUnifiaDashboard = "https://sprinttest-03.mitlab.com/DailyDashboard/DailyDashboard";
	private final int implicitTimeout = ITestConstants.implicitTimeOut;
	private final int pageLoadTimeOut = ITestConstants.pageLoadTimeOut;
	private static IReporter testReport = null;
	@BeforeSuite
	public void beforeSuite(ITestContext testContext) throws Exception {
		TestMultiScopeProcedure.testReport = ReportFactory.getInstance(ReportType.ExtentHtml,
				ExtentTestVisibilityMode.valueOf("TestNGTestMethodsAsTestAtLeft"));
	}
	
	@AfterSuite
	protected void afterSuite(ITestContext testContext) {		
		TestMultiScopeProcedure.testReport.updateTestCaseStatus();
	}

	@BeforeTest
	public void beforeTest(ITestContext testContext) throws InterruptedException {
		String chromedriverPath = "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromedriverPath);

		/* Chrome Settings */
		Map<String, Object> prefs = new HashMap<String, Object>();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches", "--disable-extensions", "--dns-prefetch-disable");
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		/* Chrome settings Done */

		// intialize scanner
		this.driverScanner = new ChromeDriver(options);
		this.driverScanner.get(this.urlScanner);
		this.driverScanner.manage().timeouts().implicitlyWait(this.implicitTimeout, TimeUnit.SECONDS);
		this.driverScanner.manage().timeouts().pageLoadTimeout(this.pageLoadTimeOut, TimeUnit.SECONDS);
		this.driverScanner.manage().window().maximize();
		LOG.info(String.format("Browser Opened For scanner - %s", this.urlScanner));

		// initialize dashboard
		this.driverUnifia = new ChromeDriver(options);
		this.driverUnifia.get(this.urlUnifiaDashboard);
		this.driverUnifia.manage().timeouts().implicitlyWait(this.implicitTimeout, TimeUnit.SECONDS);
		this.driverUnifia.manage().timeouts().pageLoadTimeout(this.pageLoadTimeOut, TimeUnit.SECONDS);
		this.driverUnifia.manage().window().maximize();
		LOG.info(String.format("Browser Opened For dashboard - %s", this.urlUnifiaDashboard));

		// Code Re-used From Unifia_Admin_Selenium
		Unifia_Admin_Selenium.driver = (RemoteWebDriver) this.driverUnifia;
		Login_Actions.Logon_Username("qvtest01");
		Login_Actions.Logon_Password("0lympu$");
		Login_Actions.Click_Submit();

	}

	@AfterTest(enabled = true, alwaysRun = true)
	public void afterTest() {
		TestMultiScopeProcedure.testReport.updateTestCaseStatus();
		this.driverScanner.close();
		this.driverUnifia.close();
		this.driverScanner.quit();
		this.driverUnifia.quit();
	}
	@BeforeMethod
	public void beforeMethod(ITestContext testContext, Method m)
	{
		if (TestMultiScopeProcedure.testReport instanceof ExtentReporter) {

			if (((ExtentReporter) TestMultiScopeProcedure.testReport)
					.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestTagAsTestsAtLeft) {
				TestMultiScopeProcedure.testReport.initTestCase(String.format("%s", m.getName()));
			} else if (((ExtentReporter) TestMultiScopeProcedure.testReport)
					.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestMethodsAsTestAtLeft) {
				TestMultiScopeProcedure.testReport.initTestCase(
						String.format("%s-%s", "Unifia_POC", m.getName()));
			}
		}
	}
	
	@Test
	public void validateMultiScopeProcedure() {

		String fileMultipScopeProcedure = "src/yED_graphs/poc_cigniti.graphml";
		//String fileMultipScopeProcedure = "src/yED_graphs/poc_cigniti_linear.graphml";
		Result result = new TestBuilder()
				.addModel(fileMultipScopeProcedure, new MultiScopeProcedureImpl(this.driverScanner, this.driverUnifia, TestMultiScopeProcedure.testReport)
						.setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
				.execute();
	}
}
