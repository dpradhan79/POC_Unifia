package TestFrameWork.UnifiaAdminLoginPage;

import TestFrameWork.Unifia_Admin_Selenium;

public class Login_Verification extends Unifia_Admin_Selenium{

	public static String Admin_Login_Pg_Verf(){
		if(driver.findElementById("txt_userid").getAttribute("type").equals("text")){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
	
		if(Result.equals("Pass")){
			if(driver.findElementById("txt_password").getAttribute("type").equals("password")){
				Result="Pass";
			}else{
				Result="#Failed!#";
			}
		}
		
		if(Result.equals("Pass")){
			if(driver.findElementById("loginButton").getAttribute("value").equals("Log On")){
				Result="Pass";
			}else{
				Result="#Failed!#";
			}
		}
		
		
		return Result;
	}
	
	public static String Verify_Username(String UN){
		Actual=driver.findElementById("txt_userid").getAttribute("value");
		if(UN.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	
	public static String Verify_Password(String PW){
		Actual=driver.findElementById("txt_password").getAttribute("value");
		
		System.out.println(Actual);
		
		if(PW.equals(Actual)){
			Result="Pass";
		}else{
			Result="#Failed!#";
		}
		return Result;
	}
	
	
}
