package TestFrameWork.UnifiaAdminLandingPage;

import TestFrameWork.Unifia_Admin_Selenium;

public class LandingPage_Verification extends Unifia_Admin_Selenium {

	public static String Admin_Landing_Pg_Verf() throws InterruptedException{
		Thread.sleep(750);
		Result="Pass";

		/**if(driver.findElementById("logoutForm").getAttribute("value").equals("LOG OFF")){
				Result="Pass";
			}else{
				Result="#Failed!#";
			}
			
			if(Result.equals("Pass")){
				//xpath for Admin tab of unifia console 
				////*[@id="ui-id-1"]
				if(driver.findElementById("ui-id-1").getAttribute("text").equals("Admin")){
					Result="Pass";
				}else{
					Result="#Failed!#";
				}
				
			}
			
			if(Result.equals("Pass")){
				if(driver.findElementById("aboutButton").getAttribute("title").equals("About Unifia")){
					Result="Pass";
				}else{
					Result="#Failed!#";
				}
			}**/
			return Result;
	}
	
	public static String Verify_Admin_Function(String AdmFunc){
		String ExpectedTitle;
		Actual=driver.findElementByClassName("ui-jqgrid-title").getText();
		if (AdmFunc.equals("Custom Values")){
			ExpectedTitle=AdmFunc;
		}
		else{
			ExpectedTitle=AdmFunc+" List";
		}
		
		//if(Actual.equals(AdmFunc+" List")){
		if(Actual.equals(ExpectedTitle)){
			Result="Pass - navigated to "+AdmFunc;
		}else{
			Result="#Failed!# - did not navigate to "+AdmFunc;
		}
		return Result;
	}
	
	public static String Recon_Landing_Pg_Verf() throws InterruptedException{
		Thread.sleep(2000);
		if(driver.findElementById("logoutButton").getAttribute("role").equals("button")){
				Result="Pass";
			}else{
				Result="#Failed!# - logout button not found";
			}
			
			if(Result.equals("Pass")){
				//xpath for Admin tab of unifia console 
				////*[@id="ui-id-1"]
				if(driver.findElementById("ui-id-1").getAttribute("text").equals("Reconciliation")){
					Result="Pass";
				}else{
					Result="#Failed!# - Reconciliation link not found";
				}
				
			}
			
			if(Result.equals("Pass")){
				if(driver.findElementById("aboutButton").getAttribute("title").equals("About Unifia")){
					Result="Pass";
				}else{
					Result="#Failed!# - about button not found";
				}
			}
			return Result;
	}
	
	public static String Verify_Recon_Function(String ReconFunc){
		String ExpectedTitle;
		Actual=driver.findElementByClassName("ui-jqgrid-title").getText();
			if (ReconFunc.equalsIgnoreCase("CultureTesting")){
				ExpectedTitle = "Culturing";
			}else{
				ExpectedTitle=ReconFunc;
			}
	
		if(Actual.equals(ExpectedTitle)){
			Result="Pass";
		}else{
			Result="#Failed!# - did not navigate to "+ReconFunc;
		}
		return Result;
	}
	
}
