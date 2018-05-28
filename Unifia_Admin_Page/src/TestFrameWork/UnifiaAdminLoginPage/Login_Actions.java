package TestFrameWork.UnifiaAdminLoginPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.remote.RemoteWorker;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class Login_Actions  extends Unifia_Admin_Selenium{

	
	public static GeneralFunc gf;
	public static void Launch_Unifia(String url) throws InterruptedException{
		boolean loopFlag=false;
		
		driver.get(url);
		
		if(url.contains(":9751")){
			//Get the location list box elements from Data base
			List<String> DBLocation=new ArrayList<String>();
			List<String> EmulatorLocation=new ArrayList<String>();
			try{ 
				Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
	    		String stmt="Select Name from Location";
				Statement statement = conn.createStatement();
				ResultSet Locations=statement.executeQuery(stmt);
				while(Locations.next()){
					DBLocation.add(Locations.getString(1));
				}
				statement.close();
				conn.close();
	   			}
				catch (SQLException ex){
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());	
				}
			//checking if webservices are up and running and emulator page is displayed
			boolean flag = false;
			Integer pageLoadTimeout=1;
			String errorMsg="";
			while (!flag&&pageLoadTimeout<=90){
				try {
					if (errorMsg.toLowerCase().contains("alert")){
						Alert alert=driver.switchTo().alert();
						alert.accept();
					}
					driver.get(url);
					driver.navigate().refresh();
					Thread.sleep(3000);
					int size=driver.findElements(By.id("UID_DropDownLocations")).size();
					flag = true;
				}catch (Exception e){
					pageLoadTimeout++;
					Thread.sleep(2000);
					flag=false;
					errorMsg=e.getMessage();
					System.out.println(e.getMessage());
					System.out.println("Alert error");
					System.out.println(pageLoadTimeout);
					//System.out.println(e.getStackTrace());
				}
			}
			
			if (!flag){
				System.out.println("Emulator page is not loaded after waiting for 2 mins. UnifiaWebservices is not started.");
				driver.close();
			}
				
				//Checking for Location list box presence
				Integer timeout=1;
				while (!loopFlag&&timeout<=30){
					if (driver.findElements(By.id("UID_DropDownLocations")).size()!=0){
						loopFlag=true;
					}else{
						Thread.sleep(1000);
						System.out.println("waited for "+timeout+"secs");
						timeout++;	
					}	
				}
				if (!loopFlag){
					System.out.println("Emulator page Location dropdown is not loaded after waiting for 30 secs");
					driver.close();
				}
				//Verifying all the elements of Location list box are populated
				loopFlag=false;
				timeout=1;
				while (!loopFlag&&timeout<=30){
					Thread.sleep(1000);
					Select LocationDroplist=new Select(driver.findElementById("UID_DropDownLocations"));
					List<WebElement> valuesInDropDown=LocationDroplist.getOptions();
					if((valuesInDropDown.size()==DBLocation.size())){
						for(WebElement loc: valuesInDropDown){
							EmulatorLocation.add(loc.getText());
						}
						if(EmulatorLocation.equals(DBLocation)){
							System.out.println("Emulator page Location dropdown is loaded properly");
							loopFlag=true;
						}else{
							//System.out.println("Emulator page is not loaded properly");
							System.out.println("waited for "+timeout+"secs");
							timeout++;
							
						}
					}else{
						//System.out.println("Emulator page is not loaded properly");
						System.out.println("waited for "+timeout+"secs");
						timeout++;
					}
				}
				
				if (!loopFlag){
					System.out.println("Emulator page Location dropdown is notloaded after waiting for 30 secs");
					driver.close();
				}
				
				//Get the scan type list box elements from Data base
				List<String> DBScanType=new ArrayList<String>();
				List<String> EmulatorScanType=new ArrayList<String>();
				String bioburdenEnabled="",cultureEnabled="";
				try{ 
					Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
					String stmt2="select IsBioburdenTestingPerformed,IsCulturingPerformed from facility where name='Your Facility Name'";
					Statement statement = conn.createStatement();
					ResultSet scanType=statement.executeQuery(stmt2);
					while(scanType.next()){
						bioburdenEnabled=scanType.getString(1);
						cultureEnabled=scanType.getString(2);
					}
					String DBItemstoExclude="'AER Machine','Consumables','Travel Cart Location'";
					if (bioburdenEnabled.equals("0")){
						DBItemstoExclude=DBItemstoExclude+",'Bioburden Testing'";
					}
					if (cultureEnabled.equals("0")){
						DBItemstoExclude=DBItemstoExclude+",'Culture'";
					}
					String stmt="select Name from scanitemtype where name not like ('KE %') "
		    				+ "and name not in ("+DBItemstoExclude+")"; 
					statement = conn.createStatement();
					scanType=statement.executeQuery(stmt);
					while(scanType.next()){
						if((!scanType.getString(1).equalsIgnoreCase("LSIControlCommand"))&&(!scanType.getString(1).equalsIgnoreCase("Auto Leak Test"))){
							DBScanType.add(scanType.getString(1));
						}
					}
					
					statement.close();
					conn.close();
		   			}
					catch (SQLException ex){
					    // handle any errors
					    System.out.println("SQLException: " + ex.getMessage());
					    System.out.println("SQLState: " + ex.getSQLState());
					    System.out.println("VendorError: " + ex.getErrorCode());	
					}
				
				//Checking for Scan Type list box presence
				timeout=1;
				loopFlag=false;
				while (!loopFlag&&timeout<=30){
					if (driver.findElements(By.id("UID_DropDownScanType")).size()!=0){
						loopFlag=true;
					}else{
						Thread.sleep(1000);
						System.out.println("waited for "+timeout+"secs");
						timeout++;	
					}	
				}
				if (!loopFlag){
					System.out.println("Emulator page Scan Type dropdown is not loaded after waiting for 30 secs");
					driver.close();
				}
				//Verifying all the elements of Scan Type list box are populated
				loopFlag=false;
				timeout=1;
				while (!loopFlag&&timeout<=30){
					Thread.sleep(1000);
					Select ScanTypeDroplist=new Select(driver.findElementById("UID_DropDownScanType"));
					List<WebElement> valuesInDropDown=ScanTypeDroplist.getOptions();
					if((valuesInDropDown.size()==DBScanType.size())){
						for(WebElement scanType: valuesInDropDown){
							EmulatorScanType.add(scanType.getText());
						}
						if(EmulatorScanType.equals(DBScanType)){
							System.out.println("Emulator page Scan Type dropdown is loaded properly");
							loopFlag=true;
						}else{
							//System.out.println("Emulator page is not loaded properly");
							System.out.println("waited for "+timeout+"secs");
							timeout++;
							
						}
					}else{
						//System.out.println("Emulator page is not loaded properly");
						System.out.println("waited for "+timeout+"secs");
						timeout++;
					}
				}
				
				if (!loopFlag){
					System.out.println("Emulator page  Scan Type dropdown is not loaded after waiting for 30 secs");
					driver.close();
				}
		}else{
			Thread.sleep(4200);
		}
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	
	public static void Launch_UnifiaSecond(String url) throws InterruptedException{
		boolean loopFlag=false;
		
		driver2.get(url);
		
		if(url.contains(":9751")){
			//Get the location list box elements from Data base
			List<String> DBLocation=new ArrayList<String>();
			List<String> EmulatorLocation=new ArrayList<String>();
			try{ 
				Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
	    		String stmt="Select Name from Location";
				Statement statement = conn.createStatement();
				ResultSet Locations=statement.executeQuery(stmt);
				while(Locations.next()){
					DBLocation.add(Locations.getString(1));
				}
				statement.close();
				conn.close();
	   			}
				catch (SQLException ex){
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());	
				}
			
			//checking if webservices are up and running and emulator page is displayed
			boolean flag = false;
			Integer pageLoadTimeout=1;
			String errorMsg="";
			while (!flag&&pageLoadTimeout<=90){
				try {
					if (errorMsg.toLowerCase().contains("alert")){
						Alert alert=driver2.switchTo().alert();
						alert.accept();
					}
					driver2.get(url);
					driver2.navigate().refresh();
					Thread.sleep(3000);
					int size=driver2.findElements(By.id("UID_DropDownLocations")).size();
					flag = true;
				}catch (Exception e){
					pageLoadTimeout++;
					Thread.sleep(2000);
					flag=false;
					errorMsg=e.getMessage();
					System.out.println(e.getMessage());
					System.out.println("Alert error");
					System.out.println(pageLoadTimeout);
					//System.out.println(e.getStackTrace());
				}
			}
			if (!flag){
				System.out.println("Emulator page is not loaded after waiting for 2 mins. UnifiaWebservices is not started.");
				driver2.close();
			}
			
				
				//Checking for Location list box presence
				Integer timeout=1;
				while (!loopFlag&&timeout<=30){
					if (driver2.findElements(By.id("UID_DropDownLocations")).size()!=0){
						loopFlag=true;
					}else{
						Thread.sleep(1000);
						System.out.println("waited for "+timeout+"secs");
						timeout++;	
					}	
				}
				if (!loopFlag){
					System.out.println("Emulator page Location dropdown is not loaded after waiting for 30 secs");
					driver2.close();
				}
				//Verifying all the elements of Location list box are populated
				loopFlag=false;
				timeout=1;
				while (!loopFlag&&timeout<=30){
					Thread.sleep(1000);
					Select LocationDroplist=new Select(driver2.findElementById("UID_DropDownLocations"));
					List<WebElement> valuesInDropDown=LocationDroplist.getOptions();
					if((valuesInDropDown.size()==DBLocation.size())){
						for(WebElement loc: valuesInDropDown){
							EmulatorLocation.add(loc.getText());
						}
						if(EmulatorLocation.equals(DBLocation)){
							System.out.println("Emulator page Location dropdown is loaded properly");
							loopFlag=true;
						}else{
							//System.out.println("Emulator page is not loaded properly");
							System.out.println("waited for "+timeout+"secs");
							timeout++;
							
						}
					}else{
						//System.out.println("Emulator page is not loaded properly");
						System.out.println("waited for "+timeout+"secs");
						timeout++;
					}
				}
				
				if (!loopFlag){
					System.out.println("Emulator page Location dropdown is notloaded after waiting for 30 secs");
					driver2.close();
				}
				
				//Get the scan type list box elements from Data base
				List<String> DBScanType=new ArrayList<String>();
				List<String> EmulatorScanType=new ArrayList<String>();
				String bioburdenEnabled="",cultureEnabled="";
				try{ 
					Connection conn= DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
					String stmt2="select IsBioburdenTestingPerformed,IsCulturingPerformed from facility where name='Your Facility Name'";
					Statement statement = conn.createStatement();
					ResultSet scanType=statement.executeQuery(stmt2);
					while(scanType.next()){
						bioburdenEnabled=scanType.getString(1);
						cultureEnabled=scanType.getString(2);
					}
					String DBItemstoExclude="'AER Machine','Consumables','Travel Cart Location'";
					if (bioburdenEnabled.equals("0")){
						DBItemstoExclude=DBItemstoExclude+",'Bioburden Testing'";
					}
					if (cultureEnabled.equals("0")){
						DBItemstoExclude=DBItemstoExclude+",'Culture'";
					}
					String stmt="select Name from scanitemtype where name not like ('KE %') "
		    				+ "and name not in ("+DBItemstoExclude+")"; 
					statement = conn.createStatement();
					scanType=statement.executeQuery(stmt);
					while(scanType.next()){
						if((!scanType.getString(1).equalsIgnoreCase("LSIControlCommand"))&&(!scanType.getString(1).equalsIgnoreCase("Auto Leak Test"))){
							DBScanType.add(scanType.getString(1));
						}
					}
					
					statement.close();
					conn.close();
		   			}
					catch (SQLException ex){
					    // handle any errors
					    System.out.println("SQLException: " + ex.getMessage());
					    System.out.println("SQLState: " + ex.getSQLState());
					    System.out.println("VendorError: " + ex.getErrorCode());	
					}
				
				//Checking for Scan Type list box presence
				timeout=1;
				loopFlag=false;
				while (!loopFlag&&timeout<=30){
					if (driver2.findElements(By.id("UID_DropDownScanType")).size()!=0){
						loopFlag=true;
					}else{
						Thread.sleep(1000);
						System.out.println("waited for "+timeout+"secs");
						timeout++;	
					}	
				}
				if (!loopFlag){
					System.out.println("Emulator page Scan Type dropdown is not loaded after waiting for 30 secs");
					driver2.close();
				}
				//Verifying all the elements of Scan Type list box are populated
				loopFlag=false;
				timeout=1;
				while (!loopFlag&&timeout<=30){
					Thread.sleep(1000);
					Select ScanTypeDroplist=new Select(driver2.findElementById("UID_DropDownScanType"));
					List<WebElement> valuesInDropDown=ScanTypeDroplist.getOptions();
					if((valuesInDropDown.size()==DBScanType.size())){
						for(WebElement scanType: valuesInDropDown){
							EmulatorScanType.add(scanType.getText());
						}
						if(EmulatorScanType.equals(DBScanType)){
							System.out.println("Emulator page Scan Type dropdown is loaded properly");
							loopFlag=true;
						}else{
							//System.out.println("Emulator page is not loaded properly");
							System.out.println("waited for "+timeout+"secs");
							timeout++;
							
						}
					}else{
						//System.out.println("Emulator page is not loaded properly");
						System.out.println("waited for "+timeout+"secs");
						timeout++;
					}
				}
				
				if (!loopFlag){
					System.out.println("Emulator page  Scan Type dropdown is not loaded after waiting for 30 secs");
					driver2.close();
				}
		}else{
			Thread.sleep(4200);
		}
        driver2.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void Launch_UnifiaRecon(String url){
		//Unifia_Admin_Selenium.setup();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void Logon_Username(String UN){
		driver.findElementById("txt_userid").clear();
		driver.findElementById("txt_userid").sendKeys(UN);
	}

	public static void Logon_Password(String PW){
		driver.findElementById("txt_password").clear();
		driver.findElementById("txt_password").sendKeys(PW);
	}
	
	public static void Click_Submit() throws InterruptedException{
		driver.findElementById("loginButton").click();
		//driver.findElementByXPath("//*[@id='loginButton']").click();
		Thread.sleep(500);

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public static void Click_Reset(){
		driver.findElementById("txt_userid").clear();
		driver.findElementById("txt_password").clear();
		//driver.findElementById("resetButton").click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public static void Launch_Unifia_ComplexStaging(){
		System.out.println("https://mit-sql-dev:44309");
		driver.get("https://mit-sql-dev:44309");
		System.out.println("Complex Staging Site");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	
	
	
}
