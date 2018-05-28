package TestFrameWork.QVDashboard;
import java.awt.AWTException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import DailyDashBoard.QvTabsAuthorization;
import TestFrameWork.Unifia_Admin_Selenium;

//import QlikViewUserStories.IWebElement;
//import QlikViewUserStories.List;
//import QlikViewUserStories.string;
import TestFrameWork.Unifia_Admin_Selenium; 
import TestFrameWork.Unifia_Admin_Selenium; 
import TestFrameWork.QlikView.QV_GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Verification;
import TestFrameWork.UnifiaAdminUserPage.User_Actions;
public class Dashboard_Actions  extends Unifia_Admin_Selenium{
	
	public static TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public GeneralFunc gf;
	public static LandingPage_Actions SE_LA;
	public static LandingPage_Verification SE_LV;
	public static User_Actions ua;
	public static String GridID; 
	public static String Description;
	public static QV_GeneralFunc QV_Gen; //shortcut to link to the QV_GeneralFunc java class.
	public static TestFrameWork.TestHelper TH;
	public static String tab_Locator=null;
	public static int reclick=1;
	
	public static void SelectRole(String browserP, String URL, String Role, String AdminDB) throws InterruptedException, AWTException, IOException{
    	LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		LGPA.Logon_Username("uadmin");
		LGPA.Logon_Password("Olympu$123");
		LGPA.Click_Submit();
		SE_LA.Click_Admin_Menu("User");
		Description ="The user clicks the navigational link: User ";
		Thread.sleep(2000);
		ua.ClearAllSearchCriteria(); //Clear all search criteria
		ua.Search_User_ByName("qvtest01"); //Search for the user name to be modified.
		GridID=ua.GetGridID_User_To_Modify("qvtest01"); //Get the grid id for the user name to be modified.
		ua.Select_User_To_Modify("qvtest01"); //Select the user name to be modified.
		ua.Select_User_Role(Role);
		ua.Save_User_Edit();
		SE_LA.Click_Logout();
		LGPA.Logon_Username("qvtest01");
		LGPA.Logon_Password("0lympu$");
		LGPA.Click_Submit();
		Thread.sleep(5500);
	}

	public static void ClickTab(String tab) throws InterruptedException{
		SetTabXpath(tab);
		Thread.sleep(3000);
			Unifia_Admin_Selenium.driver.findElement(By.xpath("//td[contains(text(), '"+tab_Locator+"')]")).click();
		System.out.println("Clicked "+tab);
		Thread.sleep(8000);
		if(!(Dashboard_Verification.verifyTabSelection(tab_Locator)) && reclick<=3){
			reclick++;
			ClickTab(tab);
		}else{
			QvTabsAuthorization.TestSummary+="Clicked on "+tab+" tab.\r\n";
		}
		reclick=1;
	}
	
	public static void ReclickTabs(String Flow) throws InterruptedException{
		if(Flow!=null){
			QvTabsAuthorization.TestSummary+="Reclicking tabs because of unexpected pop-up\r\n";
			String[] flowTabs=Flow.split(";");
			for(String flowtab:flowTabs){
				Thread.sleep(5000);
				ClickTab(flowtab);
				System.out.println("Reclicked "+flowtab);
				Thread.sleep(8000);
				if(!Dashboard_Verification.verifyTabSelection(tab_Locator)){
					ReclickTabs(Flow);
				}
			}
		}
	}

	public static String SetTabXpath(String tab) {
		switch (tab) {
		case "Daily Dashboard":
			tab_Locator = "DAILY DASHBOARD";
			break;
		case "Infection Prevention":
			tab_Locator = "INFECTION PREVENTION";
			break;
		case "Materials And Asset Management":
			tab_Locator = "MATERIALS AND ASSET MANAGEMENT";
			break;
		case "Analysis":
			tab_Locator = "ANALYSIS";
			break;
		case "Dashboard":
			tab_Locator = "DASHBOARD";
			break;
		case "Records":
			tab_Locator = "RECORDS";
			break;
		case "Procedure and Reprocessing Record":
			tab_Locator = "PROCEDURE AND REPROCESSING RECORD";
			break;
		case "Scope Procedure and Reprocessing Times":
			tab_Locator = "SCOPE PROCEDURE AND REPROCESSING TIMES";
			break;
		case "Scope Reprocessing Details":
			tab_Locator = "SCOPE REPROCESSING DETAILS";
			break;
		case "Average Times":
			tab_Locator = "AVERAGE TIMES";
			break;
		case "AER Statistics":
			tab_Locator = "AER STATISTICS";
			break;
		case "Reprocessing Activity":
			tab_Locator = "REPROCESSING ACTIVITY";
			break;
		case "Scope Cleaning":
			tab_Locator = "SCOPE CLEANING";
			break;
		case "Scope Testing":
			tab_Locator = "SCOPE TESTING";
			break;
		}
		return tab_Locator;
	}
	
	public static void clickElement(String xpath){
		driver.findElementByXPath(xpath).click();
	}
}
