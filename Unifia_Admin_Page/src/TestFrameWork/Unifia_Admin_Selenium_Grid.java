package TestFrameWork;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.*;

public class Unifia_Admin_Selenium_Grid {

	//public static FirefoxDriver driver= new FirefoxDriver();
	//public static ChromeDriver driver;// driver = new ChromeDriver();
	public static RemoteWebDriver driver;// driver = new ChromeDriver();
	
	public static String Result;
	public static String Actual;
	public String QVActual;
	public static String GridID; 
	public static String TaskID;
	public static String Task_UI_ID;
	public static String TaskCheckBoxStatus;
	public static int TaskSelected;
	public static String ModFacAct_Val; 
	public static String ModRoleAct_Val;
	public static String ModETAct_Val;
	public static String ModLocationAct_Val;
	public static String ModScopeTypeAct_Val;
	public static String ModScannerAct_Val;
	public static String ModScopeAct_Val;
	public static String ModAccessPointStatus;
	public static String ModBarcodeStatus;
	public static String ModECN_Val; // value of ECN check box to be modified
	public static String ModStaffAct_Val; // value of staff active value
	public static String ModUserAct_Val;
	public static int RoleSelected;
	public static String UserRoleID;
	public static String UserRole_UI_ID;
	public static String RoleCheckBoxStatus;
	public static int FacilitySelected;
	public static String UserFacilityID;
	public static String UserFacility_UI_ID;
	public static String FacilityCheckBoxStatus;
	public static String ModScopBiobTest_Val;
	public static String ModCult_Val;
	public static String ScopBiobTest_Val;
	public static String ScopCult_Val;
	public static String DefaultHangTime_Val;
	public static Integer ScannerCount=0;
	
	
	/*NOTE: the Env variable will change based on the site being tested. The current Env is for sprinttest-03.mitlab.com 
   	 * Here are the QV_URL :
   	 * 
   	 * FACT03: Env="te-fact-03.mitlab.com";
   	 * FACT04: Env="te-fact-04.mitlab.com";
   	 * FACT05: Env="te-fact-05.mitlab.com";
   	 * FACT06: Env="te-fact-06.mitlab.com";
   	 * FACT07: Env="te-fact-07.mitlab.com";
   	 * FACT08: Env="te-fact-08.mitlab.com";
   	 * FACT09: Env="te-fact-09.mitlab.com";
   	 * FACT10: Env="te-fact-10.mitlab.com";
   	 * Sprint03: Env="sprinttest-03.mitlab.com";
   	 * Sprint04: Env="sprinttest-04.mitlab.com";
   	 * Sprint05: Env="sprinttest-05.mitlab.com";
   	 * Sprint06: Env="sprinttest-06.mitlab.com";
   	 * Sprint07: Env="sprinttest-07.mitlab.com";
   	 * Sprint08: Env="sprinttest-08.mitlab.com";
   	 * Sprint09: Env="sprinttest-09.mitlab.com";
   	 * Sprint10: Env="sprinttest-10.mitlab.com";
   	 * */
	
	//public static String Env="te-fact-09.mitlab.com";
	public static String Env="sprintTest-05.mitlab.com";
		
	public static String QV_URL="https://"+Env+":9753/dashboard.cshtml";
	public static String QMC_URL="https://"+Env+":4780/QMC/default.htm";
	public static String url = "jdbc:sqlserver://"+Env+"\\sqlexpress;databaseName=Unifia"; 
	public static String user = "unifia";
	public static String pass = "0lympu$123";
	public static String connstring = url+";user="+user+";password="+pass;
	public static String Admin_URL="https://"+Env+":9753/Default.cshtml";
	//Emulator
	public static String Emulator_URL="https://"+Env+":9751";
	
	//QlikView Report related
	// This is only tested for column MRC Test Date
	public static String SortbyColumn="MRC Test Date";  //  MRC Test Date / MRC Test Time / Reprocessor / MRC Test(p/f) 
	public static String SortOrder="Descending"; // Ascending or Descending
	
	public RemoteWebDriver getDriver() {	
		return driver;
	}	
	
	public static void setup(){
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
		driver = new ChromeDriver(options);
	}

public static void setDriver(String browserType) throws MalformedURLException {
	switch (browserType) {
	case "firefox":
		System.out.println("Opening firefox driver");
		driver= new FirefoxDriver();		
	case "chrome":
		System.out.println("Opening chrome driver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
		driver= new ChromeDriver(options);
	case "iexplore":	
		System.out.println("Opening IE driver");
		System.setProperty("webdriver.ie.driver", "C:/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
	
	default:
		System.out.println("browser : " + browserType + " is invalid, Launching Chrome as browser of choice..");
		System.out.println("Opening chrome driver");			
		System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
		driver= new ChromeDriver();
	}
	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	driver.manage().window().maximize();
}
public static void setGridDriver(String browserT,String unifiaServer) throws MalformedURLException {
	/* Revert this after merge to Unifia_Admin_Selenium
	driver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getBrowserCapabilities(browserT));
	Env=unifiaServer;
	Admin_URL="https://"+unifiaServer+":9753/Default.cshtml";
	QV_URL="https://"+unifiaServer+":9753/dashboard.cshtml";
	QMC_URL="https://"+unifiaServer+":4780/QMC/default.htm";
	url = "jdbc:sqlserver://"+unifiaServer+"\\sqlexpress;databaseName=Unifia"; 
	Emulator_URL="https://"+unifiaServer+":9751";
	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	*/
	Unifia_Admin_Selenium.driver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getBrowserCapabilities(browserT));
	Unifia_Admin_Selenium.Env=unifiaServer;
	Unifia_Admin_Selenium.Admin_URL="https://"+unifiaServer+":9753/Default.cshtml";
	Unifia_Admin_Selenium.QV_URL="https://"+unifiaServer+":9753/dashboard.cshtml";
	Unifia_Admin_Selenium.QMC_URL="https://"+unifiaServer+":4780/QMC/default.htm";
	Unifia_Admin_Selenium.url = "jdbc:sqlserver://"+unifiaServer+"\\sqlexpress;databaseName=Unifia"; 
	Unifia_Admin_Selenium.Emulator_URL="https://"+unifiaServer+":9751";
	Unifia_Admin_Selenium.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	Unifia_Admin_Selenium.driver.manage().window().maximize();
}
private static DesiredCapabilities getBrowserCapabilities(String browserType) {
	DesiredCapabilities capability;
	switch (browserType) {
	case "firefox":
		System.out.println("Opening firefox driver");
		capability=DesiredCapabilities.firefox();
		return capability;
		
	case "chrome":
		System.out.println("Opening chrome driver");			
		capability=DesiredCapabilities.chrome();
		capability.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
		System.setProperty("webdriver.chrome.driver", "C:/Grid_Hub/chromedriver.exe");
		return capability;
		
	case "iexplore":
		System.out.println("Opening IE driver");
		System.setProperty("webdriver.ie.driver", "C:/Grid_Hub/IEDriverServer.exe");
		capability=DesiredCapabilities.internetExplorer();		
		return capability;
	
	default:
		System.out.println("browser : " + browserType + " is invalid, Launching Chrome as browser of choice..");
		System.out.println("Opening chrome driver");			
		capability=DesiredCapabilities.chrome();
		return capability;
	}
	
}
}

