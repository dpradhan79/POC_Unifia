/*- The Manager/Supervisor role will have the ability to Add a new Scope Workflow
 - The Manager/Supervisor role will have the ability to modify data and view the Audit log.    Any other roles will NOT be able to modify data or view the Audit log. 
Admin will display Admin
Dashboard will only display Dashboard option
DataAnalyst will have access to Analysis and Dashboard option
Manager/Supervisor role will have access to DailyDashboard, IP, MAM, Analysis, Audit Log
					    Under IP->SRM it will have Save button visible
                                            Under MRC it will have edit icon visible

Material Manager will have access to only Dashboard and MAM
Staff role will have access to DailyDashboard, IP, MAM only
					    Under IP->SRM it will have Save button disabled
                        Under MRC it will have edit icon not visible*/

package DailyDashBoard;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.graphwalker.core.condition.StopConditionException;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.QVDashboard.Dashboard_Actions;
import TestFrameWork.QVDashboard.Dashboard_Verification;
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminUserPage.User_Actions;
import AdminUserStories.*;

public class QvTabsAuthorization {

	public TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	
	
	public GeneralFunc gf;
	public QV_GeneralFunc QV_Gen;	
	public Dashboard_Actions DA;
	public Dashboard_Verification DV;
	public LandingPage_Actions SE_LA;
	public User_Actions ua;
	public String GridID; 
	public String Description;
	public String tab_Locator;
	public static String TestSummary= "\t\t\t QvTabs_Authorization_TestSummary \r\n"; 
	public String ResFileName="QvTabs_Authorization_TestSummary";
	public TestFrameWork.TestHelper TH;
	public String TestRes;
	public String ForFileName;
	public String LoginRole;
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void Test(String browserP, String URL, String AdminDB) throws  StopConditionException, URISyntaxException, SQLException, AWTException, ParseException, IOException, InterruptedException {
    	//select the Driver type Grid or local
    	if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    		System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    		System.exit(1);
    	}
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		ForFileName = dateFormat.format(date);  
		ResFileName=ResFileName+"_"+ForFileName;
		
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    	
    	//list of roles that needs to be checked
    	String AllRoles= "Admin,Dashboard,Data Analyst,Manager/Supervisor,Materials Manager,Staff";
    	//String AllRoles= "Dashboard,Admin";
    	//String AllRoles= "Staff";
    	String[] Roles=AllRoles.split(",");
    	System.out.println(Roles);
    	for(String role: Roles){
    		//Selecting role 
    		DA.SelectRole(browserP, URL, role,AdminDB);
    		
    		System.out.println("\n Checking for "+role+" role:");
        	System.out.println("==================================");
        	LoginRole=role;
        	TestRes="\r\n===========================================================";
        	TestRes+="\r\nChecking for "+role+" role:\r\n";
        	//TestRes+="===========================================================";
			System.out.println(TestRes);
			TestSummary=TestSummary+TestRes+"\r\n";
			
			//Checking the Authorization of QV tabs for the selected role
			DV.CheckDashboard(role,ResFileName);
			SE_LA.Click_Logout();
			
		/*	Unifia_Admin_Selenium.driver.switchTo().defaultContent();
    		Unifia_Admin_Selenium.driver.findElementByXPath("//*[@id='logoutForm']/ul/li/a").click();*/
    		
    	
    		
    	}
    	
    	try{ 
			  // Create file 
			  FileWriter fstream = new FileWriter(ResFileName+".txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(TestSummary);
			  //Close the output stream
			  out.close();
			  TestSummary="";
			  }catch (Exception e){//Catch exception if any
				  System.err.println("Error: " + e.getMessage());
			}
    	
    	//TH.WriteToTextFile(ResFileName, TestSummary);
    	if (TestSummary.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
		}
    	
    	LandingPage_Actions.CloseDriver();	
	}
	@AfterTest
	  public void PostTest() throws IOException{
	  	LandingPage_Actions.CloseDriver();
	  }
	
}
