package poc.cigniti.multiscope.procedure;



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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;

public class TestMultiScopeProcedure {
	private static final Logger LOG = Logger.getLogger(TestMultiScopeProcedure.class);
	WebDriver driverScanner = null;
	WebDriver driverUnifia = null;
	String urlScanner = "https://sprinttest-03.mitlab.com:9751";
	String urlUnifiaDashboard = "https://sprinttest-03.mitlab.com/DailyDashboard/DailyDashboard";
	final int implicitTimeout = ITestConstants.implicitTimeOut;
	final int pageLoadTimeOut = ITestConstants.pageLoadTimeOut;
	@BeforeTest
	public void beforeTest(ITestContext testContext) throws InterruptedException
	{
		String chromedriverPath = "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromedriverPath);

		/* Chrome Settings */
		Map<String, Object> prefs = new HashMap<String, Object>();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions","--dns-prefetch-disable");		
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		/* Chrome settings Done */
		
		//intialize scanner
		this.driverScanner = new ChromeDriver(options);
		this.driverScanner.get(this.urlScanner);
		this.driverScanner.manage().timeouts().implicitlyWait(this.implicitTimeout, TimeUnit.SECONDS);
		this.driverScanner.manage().timeouts().pageLoadTimeout(this.pageLoadTimeOut, TimeUnit.SECONDS);
		this.driverScanner.manage().window().maximize();
		LOG.info(String.format("Browser Opened For scanner - %s", this.urlScanner));
		
		//initialize dashboard
		this.driverUnifia = new ChromeDriver(options);
		this.driverUnifia.get(this.urlUnifiaDashboard);
		this.driverUnifia.manage().timeouts().implicitlyWait(this.implicitTimeout, TimeUnit.SECONDS);
		this.driverUnifia.manage().timeouts().pageLoadTimeout(this.pageLoadTimeOut, TimeUnit.SECONDS);
		this.driverUnifia.manage().window().maximize();
		LOG.info(String.format("Browser Opened For dashboard - %s", this.urlUnifiaDashboard));
		
		//Code Re-used From Unifia_Admin_Selenium
		Unifia_Admin_Selenium.driver = (RemoteWebDriver) this.driverUnifia;
		Login_Actions.Logon_Username("qvtest01");
		Login_Actions.Logon_Password("0lympu$");
		Login_Actions.Click_Submit();
		
		
	}
	@AfterTest(enabled = false, alwaysRun = true)
	public void afterTest()
	{
		this.driverScanner.close();
		this.driverUnifia.close();
		this.driverScanner.quit();
		this.driverUnifia.quit();
	}
	
	@Test
	public void validateMultiScopeProcedure() {
		
		//String fileMultipScopeProcedure = "src/yED_graphs/poc_cigniti.graphml";
		String fileMultipScopeProcedure = "src/yED_graphs/poc_cigniti_linear.graphml";
		Result result = new TestBuilder().addModel(fileMultipScopeProcedure,
				new MultiScopeProcedureImpl(this.driverScanner, this.driverUnifia).setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
				.execute();
	}
}
