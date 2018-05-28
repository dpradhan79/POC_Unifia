package ITConsole;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;

public class ITConScanSim {
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.Unifia_Admin_Selenium UAS;
	private Integer KE=0;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void ITConsoleFlow(String browserP, String URL, String AdminDB) throws Exception {
		//select the Driver type Grid or local
		if (UAS.parallelExecutionType && UAS.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		if (!ITConScanSimActions.ITConsole(UAS.Env, UAS.userName, UAS.IISPass,UAS.ITConsoleBatchPath,UAS.ITConsoleExecTime,KE)){
			System.out.println(" ItConsole did not complete in "+UAS.ITConsoleExecTime +" mins");
		}
		
		ITConScanSimActions.VerifyITconsoleExecution(Unifia_Admin_Selenium.Env);
		try{
			  // Create file 
			  FileWriter fstream = new FileWriter("ITConsole_Summary.txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(ITConScanSimActions.log);
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		LP_A.CloseDriver();
	}
}
