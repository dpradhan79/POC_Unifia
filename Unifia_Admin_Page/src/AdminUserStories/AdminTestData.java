package AdminUserStories;

import java.io.IOException;
import java.net.URISyntaxException;

import org.graphwalker.core.condition.StopConditionException;
import org.testng.annotations.Test;

import TestFrameWork.TestHelper;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class AdminTestData {
	
	public static GeneralFunc gf;
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public static void InsertData(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		//Inserting Application Data
		gf.InsertDefaultData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
		
		//Inserting Test Data
		gf.Insert_SQLSERVERTestData(Unifia_Admin_Selenium.TestDataDBURL, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.TestDataPass);
		
		LandingPage_Actions.CloseDriver();
	}

}
