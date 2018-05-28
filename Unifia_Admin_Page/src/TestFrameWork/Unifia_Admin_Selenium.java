package TestFrameWork;
import java.net.MalformedURLException;
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
import org.testng.annotations.DataProvider;

import java.net.URL;
import java.util.Arrays;

public class Unifia_Admin_Selenium {

	//public static FirefoxDriver driver= new FirefoxDriver();
	//public static ChromeDriver driver;// driver = new ChromeDriver();
	public static RemoteWebDriver driver;
	public static RemoteWebDriver driver2;
	public static String Result;
	public static String OverallResult="Pass";
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
	public static String FileName;
	public static int StepNum=0;
	//Selenium Grid
	public static String theBrowser=null;
	public static int TestCaseNumber=1; // Used for XML results 
	public static String XMLFileName; // Used for XML results
	public static String ExecBrowser;
	
	public static boolean IsHappyPath=false;
	public static boolean IsKEHappyPath=false;
	public static boolean PhyScannedInWaiting=false;
	public static String resultFlag;
	
	public static int Scope1ExpectedReproCount=0;
	public static int Scope1ExpectedExamCount=0;
	public static int Scope2ExpectedReproCount=0;
	public static int Scope2ExpectedExamCount=0;
	public static int Scope3ExpectedReproCount=0;
	public static int Scope3ExpectedExamCount=0;
	public static int Scope4ExpectedReproCount=0;
	public static int Scope4ExpectedExamCount=0;
	
	public static String driverCount="";
	public static boolean isEmulator=false;
	public static boolean isRecon=false;
	
	public static String expDateFormat="MM/dd/yyyy hh:mm a";
	public static String expDateExample="02/15/2018 12:08 AM";
	public static String expDateExample2="02/01/2018 01:08 AM";
	public static String expTimeFormat="hh:mm a";
	public static String expTimeFormatExample="01:08 AM";
	
	
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
   	 * Sprint03: Env="mitvsNm162.mitlab.com";
   	 * Sprint04: Env="sprinttest-04.mitlab.com";
   	 * Sprint05: Env="sprinttest-05.mitlab.com";
   	 * Sprint06: Env="sprinttest-06.mitlab.com";
   	 * Sprint07: Env="sprinttest-07.mitlab.com";
   	 * Sprint08: Env="sprinttest-08.mitlab.com";
   	 * Sprint09: Env="sprinttest-09.mitlab.com";
   	 * Sprint10: Env="sprinttest-10.mitlab.com";
   	 * Gautam SQL Server Standard: Env="mitvsbs160.mitlab.com";
   	 * 
   	 * KE Environments
   	 * Connecting to mitvsNm162: KE_Env="10.170.93.134"; (KE2.0)
   	 * Connecting to Sprint04: KE_Env="10.170.93.117"; (KE2.0)
   	 * Connecting to Sprint05: KE_Env="10.170.93.102"; (KE2.0)
   	 * Connecting to Gautam: KE_Env="10.170.93.112"; (KE2.0)
   	 * Connecting to Brett’s Unifia Experience Center server ec-unifia2-01: KE_Env="10.170.93.72";
   	 * 
   	 * */
	
	//public static String Env="te-fact-09.mitlab.com";
	public static String Env,QV_URL,QMC_URL,url,Admin_URL,Emulator_URL;
	
	public static String userName="administrator";
	public static String user = "unifia";
	public static String pass = "Olympu$123";
	public static String connstring = url+";user="+user+";password="+pass;
	
	public static String TestDataPass="0lympu$123";
	public static String IISPass="0lympu$123";
	
	//parallelExecutionType False for Default or Sequential execution and True for Parallel
	public static final boolean parallelExecutionType = false;   
	// Modify the servername Server name of your test env
	public static String browserURL="Chrome==perform-01.mitlab.com==unifia_admin_testdata_temp;";
	// sample below is with another test db name
	//public static String browserURL="Chrome==sprintTest-04.mitlab.com==unifia_admin_testdata_temp;";
	public static String driverType="Local"; //Local or Grid
	
	// for Internet Explorer use the following line: 
	//public static String browserURL="Iexplore==sprintTest-09.mitlab.com=unifia_admin_testdata"; //

	//For sequential or parallel execution use the following line:
	//public static String browserURL="Chrome==sprintTest-09.mitlab.com==unifia_admin_testdata;Iexplore==sprintTest-04.mitlab.com=unifia_admin_testdata_temp";
	
	
	//KE related information:
	public static String KE_Url,KE_connstring;
	public static String KE_SimulatorInput="C:/Users/garren/Downloads/KE_Simulator_input.xml";
	//Modify the KE_Env variable with the IP address of the KEServer
	public static String KE_Env="KEServerIPAddress";
	public static String KE_UserName="ENDBACCS";
	public static String KE_Pwd="3ENDOWBS";
	
	public static String KEMachine_Username="administrator";
	public static String KEMachine_pswd="0lympu$123";
	
	//Application User Name
	public static String appUser = "UAdmin";
	public static String appPassword = "Olympu$123";
	
	//public static String QMCUser = Env+"\\qvservice";
	public static String QMCUser;
	public static String QMCPassword = "Olympu$123";
	
	//TestDataDBURL will be set via browserURL variable later when multiple testdB connections user story will be scripted
	public static String DBServer="10.170.93.135";
	//public static String DBName="unifia_admin_testdata";
	public static String DBName;
	public static String TestDataDBURL;
	//public static String TestDataDBURL="jdbc:sqlserver://"+DBServer+"\\sqlexpress;databaseName="+DBName;

	//QlikView Report related
	// This is only tested for column MRC Test Date
	public static String SortbyColumn="MRC Test Date";  //  MRC Test Date / MRC Test Time / Reprocessor / MRC Test(p/f) 
	public static String SortOrder="Descending";// Ascending or Descending	
	


	public static String itConsoleTestDataPath="\\c$\\Olympus\\UnifiaTools\\ITConsole\\TestData";
	public static String itConsolePath="\\c$\\Olympus\\UnifiaTools\\ITConsole";
	public static String itConsoleExecFile="ITConsole.exe.config";
	public static String ITConsoleBatchPath="c:\\olympus\\UnifiaTools\\ITconsole";
	
	public static String scanSimPath="\\c$\\olympus\\UnifiaTools\\ScanSim";
	public static String scanSimPathTool="c:\\olympus\\UnifiaTools\\ScanSim";
	public static String batchFile="Runbatch.bat";
	public static String scanSimSearchTxt="Olympus.Unifia.ScanSimulation";
	public static String scanSimTxt="Olympus.Unifia.ScanSim";
	public static String scanSimPort="9755";
	public static String scanSimExecOutput="c:\\Output.txt";
	
	public static String psexecPath=System.getProperty("user.dir")+"\\src\\Resources";


	public static Integer ITConsoleExecTime=120; // In Minutes
	public static String brVersion="65";
	public static String brDriverVersion="2.37";
	
	//Users n Roles
	public static String roleMgr="Manager/Supervisor";
	public static String userQV1="qvtest01";
	public static String userQV1Pwd="0lympu$";
	
	public static String userQV2="qvtest02";
	public static String userQV2Pwd="0lympu$";
	
	public static String userQV3="qvtest03";
	public static String userQV3Pwd="0lympu$";
	
	public static String facility1="FAC1";
	public static String facility2="FAC2";
	public static String facility3="FAC3";
	
	public static String  defaultFacility="FAC1";
	
	public static boolean isMoveRight=false;
	
//	public static ChromeDriver getDriver(){
//		return driver;
//	}

	public static void setup(){
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
		driver = new ChromeDriver(options);
	}
	
	public static void setDriver(String browserType, String driverType) throws MalformedURLException {
		
		ExecBrowser=browserType;
		
		switch (browserType.toLowerCase()) {
		case "firefox":
			System.out.println("Opening firefox driver");
			if (driverType.equalsIgnoreCase("second")){
				driver2= new FirefoxDriver();
			}else{
				driver= new FirefoxDriver();
			}
			return;
		case "chrome":
			System.out.println("Opening chrome driver");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("chrome.switches","--disable-extensions","--dns-prefetch-disable");
			//System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			//chrome_options.add_argument('--dns-prefetch-disable')
			if (driverType.equalsIgnoreCase("second")){
				driver2= new ChromeDriver(options);
			}else{
				driver= new ChromeDriver(options);
			}
			
			Capabilities brCap=driver.getCapabilities();
			if (brCap.getVersion().contains(brVersion)){
				System.out.println("Pass-Expected and current browser versions are same - "+brCap.getVersion());
			}else{
				System.err.println("Warning: Expected browser version is "+brVersion +", but current browser version is "+brCap.getVersion());
			}
			
			if (brCap.getCapability("chrome").toString().split("chromedriverVersion=")[1].split(" ")[0].contains(brDriverVersion)){
				System.out.println("Pass-Expected and current driver versions are same - "+brCap.getCapability("chrome").toString().split("chromedriverVersion=")[1].split(" ")[0]);
			}else{
				System.err.println("Warning: Expected driver version is "+brDriverVersion +", but current driver version is "+brCap.getCapability("chrome").toString().split("chromedriverVersion=")[1].split(" ")[0]);
			}
			System.out.println("============================================================");
			return;
		case "iexplore":
			DesiredCapabilities capability;
			System.out.println("Opening IE driver");
			System.setProperty("webdriver.ie.driver", "C:/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability("nativeEvents", false);
			if (driverType.equalsIgnoreCase("second")){
				driver2 = new InternetExplorerDriver(capability);
			}else{
				driver = new InternetExplorerDriver(capability);
			}
			System.out.println("============================================================");
			return;
		default:
			ChromeOptions options1 = new ChromeOptions();
			options1.addArguments("chrome.switches","--disable-extensions");
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			Capabilities brCap1;
			if (driverType.equalsIgnoreCase("second")){
				driver2= new ChromeDriver(options1);
				brCap1=driver2.getCapabilities();
			}else{
				driver= new ChromeDriver(options1);
				brCap1=driver.getCapabilities();
			}
			
			if (brCap1.getVersion().equalsIgnoreCase(brVersion)){
				System.out.println("Pass-Expected and current browser versions are same - "+brCap1.getVersion());
			}else{
				System.err.println("Warning: Expected browser version is "+brVersion +", but current browser version is "+brCap1.getVersion());
			}
			
			if (brCap1.getCapability("chrome").toString().split("chromedriverVersion=")[1].split(" ")[0].equalsIgnoreCase(brDriverVersion)){
				System.out.println("Pass-Expected and current driver versions are same - "+brCap1.getCapability("chrome").toString().split("chromedriverVersion=")[1].split(" ")[0]);
			}else{
				System.err.println("Warning: Expected driver version is "+brDriverVersion +", but current driver version is "+brCap1.getCapability("chrome").toString().split("chromedriverVersion=")[1].split(" ")[0]);
			}
			System.out.println("============================================================");
			return;
		}
		
	}
	
	public static void setEnvironment(String unifiaServer){
			Env=unifiaServer;
			Admin_URL="https://"+unifiaServer+"/Account/Login";//"https://"+unifiaServer+":9753/Default.cshtml";
			QV_URL="https://"+unifiaServer+"/Account/Login";//"https://"+unifiaServer+":9753/dashboard.cshtml";
			QMC_URL="https://"+unifiaServer+":4780/QMC/default.htm";
			url = "jdbc:sqlserver://"+unifiaServer+"\\UESQLSVR;databaseName=Unifia"; 
			Emulator_URL="https://"+unifiaServer+":9751";
			connstring = url+";user="+user+";password="+pass;
			KE_Url="jdbc:oracle:thin:@"+KE_Env+":1521:FXDB";
			KE_connstring=KE_Url+","+KE_UserName+","+KE_Pwd;
			QMCUser = Env+"\\qvservice";
			TestDataDBURL="jdbc:sqlserver://"+DBServer+"\\sqlexpress;databaseName="+DBName;
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driver.manage().window().maximize();
	}
	
	public static void setGridDriver(String browserT,String unifiaServer) throws MalformedURLException {
		ExecBrowser=browserT;
		System.out.println(unifiaServer);
		theBrowser=browserT;
		driver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), getBrowserCapabilities(browserT));
		Env=unifiaServer;
		Admin_URL="https://"+unifiaServer+":9753/Default.cshtml";
		QV_URL="https://"+unifiaServer+":9753/dashboard.cshtml";
		QMC_URL="https://"+unifiaServer+":4780/QMC/default.htm";
		url = "jdbc:sqlserver://"+unifiaServer+"\\UESQLSVR;databaseName=Unifia"; 
		Emulator_URL="https://"+unifiaServer+":9751";
		KE_Url="jdbc:oracle:thin:@"+KE_Env+":1521:FXDB";
		KE_connstring=KE_Url+","+KE_UserName+","+KE_Pwd;
		QMCUser = Env+"\\qvservice";
		TestDataDBURL="jdbc:sqlserver://"+DBServer+"\\sqlexpress;databaseName="+DBName;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	private static DesiredCapabilities getBrowserCapabilities(String browserType) {
		DesiredCapabilities capability;
		switch (browserType.toUpperCase()) {
		case "FIREFOX":
			System.out.println("Opening firefox driver");
			capability=DesiredCapabilities.firefox();
			return capability;
			
		case "CHROME":
			System.out.println("Opening chrome driver");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			capability=DesiredCapabilities.chrome();
			//capability.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
			capability.setCapability(ChromeOptions.CAPABILITY, options);
			//System.setProperty("webdriver.chrome.driver", "C:/Grid_Hub/chromedriver.exe");
			return capability;
			
		case "IEXPLORE":
			System.out.println("Opening IE driver");
			System.setProperty("webdriver.ie.driver", "C:/Grid_Hub/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();		
			return capability;
		
		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Chrome as browser of choice..");
			System.out.println("Opening chrome driver");			
			capability=DesiredCapabilities.chrome();
			capability.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
			System.setProperty("webdriver.chrome.driver", "C:/Grid_Hub/chromedriver.exe");
			return capability;
		}
		
	}
	
	@DataProvider(name="BrowserandURL", parallel=Unifia_Admin_Selenium.parallelExecutionType)
	public static Object[][] BrowserandURL() {
		 String[][] arrColl = null;
		 String arrBrwnURL=Unifia_Admin_Selenium.browserURL;
		 String []arrEachSet=arrBrwnURL.split(";");
		 arrColl = new String[arrEachSet.length][3];
		 for (Integer cntr = 0; cntr <arrEachSet.length; cntr++) {
			 String[] arrEachEle=arrEachSet[cntr].split("==");
			 arrColl[cntr][0]=arrEachEle[0];
			 arrColl[cntr][1]=arrEachEle[1];
			 arrColl[cntr][2]=arrEachEle[2];
		 }
		 Object[][] arrayObject =arrColl; 
		 return (Object [][]) arrayObject;
	  }
	
	public static void DriverSelection(String browserTypes,String strURL,String DB) throws MalformedURLException{
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		DBName=DB;
		if (Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			Unifia_Admin_Selenium.setDriver(browserTypes, "first");
			Unifia_Admin_Selenium.setEnvironment(strURL);
		}else if(Unifia_Admin_Selenium.driverType.equalsIgnoreCase("GRID")){
			Unifia_Admin_Selenium.setGridDriver(browserTypes,strURL);
		}
	}	
	
	public static void driverSelectionSecond(String browserTypes,String strURL,String DB) throws MalformedURLException{
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		DBName=DB;
		driverCount="second";
		if (Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			Unifia_Admin_Selenium.setDriver(browserTypes, driverCount);
			Unifia_Admin_Selenium.setEnvironment(strURL);
		}else if(Unifia_Admin_Selenium.driverType.equalsIgnoreCase("GRID")){
			Unifia_Admin_Selenium.setGridDriver(browserTypes,strURL);
		}
	}	
}
