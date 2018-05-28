package TestFrameWork.UnifiaAdminLandingPage;

import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;

public class LandingPage_Actions extends Unifia_Admin_Selenium{
	public static GeneralFunc SE_Gen;
	public static Login_Actions LGPA;
	
	public static void Click_Logout(){
		driver.switchTo().defaultContent(); //switches out of the iframe
		//driver.findElementByXPath("//*[@id='logoutForm']/ul/li/a").click(); 
		driver.findElementById("accountControl").click();
		//driver.findElementByXPath("//*[@id='accountGrid']/span").click();
		driver.findElementById("accountGrid").click();
		System.out.println("Clicked on logout button");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	public static void Click_Admin_Tab(){
		driver.findElementById("tab_admin").click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	public static void Click_Analysis_Tab(){
		driver.findElementById("tab_analysis").click();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	public static void Click_Recon_Tab() throws InterruptedException{
		driver.findElementById("tab_reconciliation").click();
		Thread.sleep(500);

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	public static void Click_Admin_Menu(String Menu) throws InterruptedException{
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(0); //switches into the iframe
		driver.findElementById("ft_menu_"+Menu).click();
		Thread.sleep(1500);
		if(Menu.equalsIgnoreCase("User")){
			Thread.sleep(1500);
		}
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	
	public static void Click_Recon_Menu(String Menu) throws InterruptedException{
		try {
			Thread.sleep(7000);
			if (Unifia_Admin_Selenium.ExecBrowser.equalsIgnoreCase("Iexplore")){
				Thread.sleep(3000);
			}
			WebElement TabEle=driver.findElementById("ft_menu_"+Menu);
			driver.findElementById("ft_menu_"+Menu).click();	
			Thread.sleep(7000);
			if (Unifia_Admin_Selenium.ExecBrowser.equalsIgnoreCase("Iexplore")){
				Thread.sleep(3000);
			}
			
			if (Unifia_Admin_Selenium.ExecBrowser.equalsIgnoreCase("Iexplore")){
				String tabselector=driver.findElementByXPath("//*[@id='ft_menu_"+Menu+"']/span").getAttribute("class"); 
				System.out.println(tabselector);
				if(tabselector.equalsIgnoreCase("fancytree-node external fancytree-exp-n fancytree-ico-c")||tabselector.equalsIgnoreCase("fancytree-node external fancytree-exp-n fancytree-ico-c ui-state-hover")){
					Unifia_Admin_Selenium.driver.navigate().refresh();
					Thread.sleep(9000);
					driver.findElementById("ft_menu_"+Menu).click();
					Thread.sleep(7000);
					System.out.println("Clicked on recon tab -"+Menu+"- second time");
				}
				
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				boolean bFlag=false;
				if (Menu.equalsIgnoreCase("AssetManagement")){
					//System.out.println("Recon Tab - AM");
					if (!SE_Gen.CheckElementExists("//*[@id='gview_jqgrid_asset_management']/div[1]/span")){
						bFlag=true;
					}
				}else if (Menu.equalsIgnoreCase("BioburdenTesting")){
					//System.out.println("REcon Tab - BT");
					if (!SE_Gen.CheckElementExists("//*[@id='gview_jqgrid_bioburden_testing']/div[1]/span")){
						bFlag=true;
					}
				}else if (Menu.equalsIgnoreCase("Culturing")){
					//System.out.println("Recon Tab - C");
					if (!SE_Gen.CheckElementExists("//*[@id='gview_jqgrid_culture_testing']/div[1]/span")){
						bFlag=true;
					}
				}else if (Menu.equalsIgnoreCase("MRCTestRecord")){
					//System.out.println("Recon Tab - MRC");
					if (!SE_Gen.CheckElementExists("//*[@id='gview_jqgrid_mrc_test_record']/div[1]/span")){
						bFlag=true;
					}
				}else if (Menu.equalsIgnoreCase("ProcedureRoom")){
					//System.out.println("Recon Tab - PR");
					if (!SE_Gen.CheckElementExists("//*[@id='gview_jqgrid_procedure_room']/div[1]/span")){
						bFlag=true;
					}
				}else if (Menu.equalsIgnoreCase("ReprocessingArea")){
					//System.out.println("Recon Tab - RA");
					if (!SE_Gen.CheckElementExists("//*[@id='gview_jqgrid_reprocessing_area']/div[1]/span")){
						bFlag=true;
					}
				}else if (Menu.equalsIgnoreCase("SoiledArea")){
					//System.out.println("Recon Tab - SA");
					if (!SE_Gen.CheckElementExists("//*[@id='gview_jqgrid_soiled_area']/div[1]/span")){
						bFlag=true;
					}
				}else if (Menu.equalsIgnoreCase("AuditLog")){
					//System.out.println("Recon Tab - AL");
					if (!SE_Gen.CheckElementExists("//*[@id='gview_jqgrid_audit_log']/div[1]/span")){
						bFlag=true;
					}
				}
				
				if (bFlag){
					CloseDriver();
					System.out.println("Recon Tab - Relaunching browser");
					try {
						System.out.println("RESTARTING BROWSER-2");
						Unifia_Admin_Selenium.DriverSelection(Unifia_Admin_Selenium.ExecBrowser, Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.url);
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("RESTARTING BROWSER-2eRR");
					}
					LGPA.Launch_UnifiaRecon(Unifia_Admin_Selenium.Admin_URL);
					Thread.sleep(5000);
					LGPA.Logon_Username("qvtest01"); 
					LGPA.Logon_Password("0lympu$");
					LGPA.Click_Submit();
					Thread.sleep(12000);
					Unifia_Admin_Selenium.driver.findElementById("ft_menu_"+Menu).click();
					Thread.sleep(12000);
				}
			}
		}catch (Exception e){
			System.out.println("Failed error");
		}
	}
	
	public static void CloseDriver() throws IOException{
		Unifia_Admin_Selenium.ScannerCount=0;
		driver.quit();
		if (driverCount.equalsIgnoreCase("second")){
			Unifia_Admin_Selenium.driverCount="";
			driver2.quit();
		}
		if (ExecBrowser.equalsIgnoreCase("iexplore")){
			Runtime.getRuntime().exec("taskkill /F /IM IEDriver*");
		}else if (ExecBrowser.equalsIgnoreCase("chrome")){
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver*");
		}
		try{
			Thread.sleep(10000);
		}catch (InterruptedException e){
			System.out.println(e.getMessage());
		}		
	}
	
}
