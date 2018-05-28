package AdminUserStories;

import org.graphwalker.core.machine.ExecutionContext;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import TestFrameWork.Unifia_Admin_Selenium;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;



//import org.openqa.selenium.WebElement;
//import AdminUserStories.SE_LoginPage.*;
import TestFrameWork.TestHelper;
//import TestFrameWork.Unifia_Admin_SE_objects;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminLoginPage.*;


public class AdminConsole_GWAPI extends ExecutionContext {
	
	public String Browser; // Variable for Browser
	
	public ResultSet UNID_rs;
	public int UNID;
	
	public String PW;  //Password
	public ResultSet PW_rs;
	public String UserPassword_Value;
	public String PWDes;
	
	public String UN; //Username
	public ResultSet UN_rs;
	public String UserName_Value;
	public String UNDes;
	
	public String LoginDes;
	public String ResetDes;
	public String LogoutDes;

	
	public String AdminLink; //A variable for admin console navigation
	public String AdminMenuVerf;
	public String BrwDes;  //Description for launching the browser
	public String LogonDes; //Description for entering the Username and Password and clicking logon button
	public String Expected;
	public String Vertex;
	public String Description;
	public String Path;//Implements a variable to determine new or modify for expected result.
	public String Result;
	public String Actual;
	public String Msg;
	
//	public static Unifia_Admin_SE_objects SE;
	public GeneralFunc SE_Gen;
	public LandingPage_Actions SE_LA;
	public LandingPage_Verification SE_LV;
	public Login_Actions SE_INA;
	public Login_Verification SE_INV;
	
	public Connection conn= null;

		
	//Implements the edges for the admin console graphml
	
	public void e_LaunchAdminPg() throws InterruptedException {
		Browser="Chrome"; //String.valueOf(getMbt().getDataValue("Browser"));
		BrwDes="The user enters the URL of the admin console in the following browser:  "+Browser;
		SE_INA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);

	}
		
	public void e_UserName() {
		Object UserName = getAttribute("UN");
		  UN = UserName.toString();
		if(UN.equals("Valid")){
			
			try{
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				UNID_rs = statement.executeQuery("Select idUsers FROM Users WHERE TestKeyword='"+UN+"' AND UpdateDate =(Select Min(UpdateDate) From Users where TestKeyword='"+UN+"')");
				while(UNID_rs.next()){
					UNID= UNID_rs.getInt(1);
					System.out.println("UNID = "+UNID);
					}
				UNID_rs.close();
				
				
				
				UN_rs = statement.executeQuery("Select Username FROM USers WHERE idUsers="+UNID);
				while(UN_rs.next()){
					UserName_Value= UN_rs.getString(1);
					System.out.println("UserName_Value = "+UserName_Value);
					}
				UN_rs.close();
				
				PW_rs= statement.executeQuery("Select Password FROM USers WHERE idUsers="+UNID);
				while(PW_rs.next()){
					UserPassword_Value= PW_rs.getString(1);
					System.out.println("UserPassword_Value ="+UserPassword_Value);
				}
				
				PW_rs.close();
				statement.close();
				
				update.executeUpdate("update users Set UpdateDate=CURRENT_TIMESTAMP WHERE idUsers="+UNID);
				
				update.close();
				conn.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			
		}else if(UN.equals("Invalid")){
			try{
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				
				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				UNID_rs = statement.executeQuery("Select idUsers FROM Users WHERE TestKeyword='"+UN+"' AND UpdateDate =(Select Min(UpdateDate) From Users where TestKeyword='"+UN+"')");
				while(UNID_rs.next()){
					UNID= UNID_rs.getInt(1);
					System.out.println("UNID = "+UNID);
					}
				UNID_rs.close();
				
				
				
				UN_rs = statement.executeQuery("Select Username FROM USers WHERE idUsers="+UNID);
				while(UN_rs.next()){
					UserName_Value= UN_rs.getString(1);
					System.out.println("UserName_Value = "+UserName_Value);
					}
				UN_rs.close();
				
				PW_rs= statement.executeQuery("Select Password FROM USers WHERE idUsers="+UNID);
				while(PW_rs.next()){
					UserPassword_Value= PW_rs.getString(1);
					System.out.println("UserPassword_Value ="+UserPassword_Value);
				}
				
				PW_rs.close();
				statement.close();
				
				update.executeUpdate("update users Set UpdateDate=CURRENT_TIMESTAMP WHERE idUsers="+UNID);
				
				update.close();
				conn.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		
		}else{
			UserName_Value=UN;
			
		}
		SE_INA.Logon_Username(UserName_Value);
		UNDes="The user enters "+UserName_Value+" in the username field.";
	}
	
	public void e_Password(){
		Object Password = getAttribute("PW");
		  PW = Password.toString();
	
		if(PW.equals("Valid")){
			UserPassword_Value = UserPassword_Value;
			
		}else if(PW.equals("Invalid")){
			UserPassword_Value = UserPassword_Value+"INVALID";
			
		}else{
			UserPassword_Value = PW;
			
		}
		SE_INA.Logon_Password(UserPassword_Value);
		PWDes="The user enters "+UserPassword_Value+" in the password field.";
	}
	
	public void e_Login() throws InterruptedException{
		SE_INA.Click_Submit();
		LoginDes="The user clicks the login button.";
		}
	
	public void e_AdminNav() throws  InterruptedException{
		Object adminLink = getAttribute("AdminLink");
		AdminLink = adminLink.toString();
		System.out.println(AdminLink);

		if(AdminLink.equals("Exam Type")){
			AdminLink="ExamType";
			AdminMenuVerf="Exam Type";
			
		}else if(AdminLink.equals("ExamType")){
			AdminLink="ExamType";
			AdminMenuVerf="Exam Type";
		}else if(AdminLink.equals("Scope Model")){
				AdminLink="ScopeModel";
				AdminMenuVerf="Scope Model";
		}else if(AdminLink.equals("ScopeModel")){
			AdminLink="ScopeModel";
			AdminMenuVerf="Scope Model";
		}else if(AdminLink.equals("AccessPoint")){
			AdminLink="AccessPoint";
			AdminMenuVerf="Access Point";
		}else if(AdminLink.equals("Access Point")){
			AdminLink="AccessPoint";
			AdminMenuVerf="Access Point";
		}else if(AdminLink.equals("Assign")){
			AdminLink="AssignBarcode";
			AdminMenuVerf="Barcode Assignment";
		}else if(AdminLink.equals("ScopeSafety")){  //Vijay 19-01-2016
			AdminLink="ScopeSafety"; //Vijay 19-01-2016
			AdminMenuVerf="Scope Safety"; //Vijay 19-01-2016			
		}else{AdminLink=AdminLink;
			AdminMenuVerf=AdminLink;
		}
		SE_LA.Click_Admin_Menu(AdminLink);
		Description ="The user clicks the navigational link:  "+AdminLink;
		
	}
	
	public void e_Reset(){
		SE_INA.Click_Reset();
		ResetDes="The user clears the user ID and password fields.";
	}
	
	public void e_Logout(){
		//driver.findElementById("logoutButton").click();
		SE_LA.Click_Logout();
		LogoutDes="The user clicks the logout button";
	}
	
	
	
	//Implements the vertices for the admin console graphml
	
	public void v_AdminLandingPg() throws InterruptedException{
		
		Result=SE_LV.Admin_Landing_Pg_Verf();		
		Vertex = getCurrentElement().getName();
		Expected="The Admin console home page is displayed";
		// AGS This is changed to get the TestStepLog to get Landing page description correctly populated
		//TestHelper.StepWriter1(Vertex, BrwDes, Expected, Result);
		//TestHelper.StepWriter1(Vertex, LoginDes, Expected, Result);
	}
	
	
	public void v_AdminLogonPg(){
		Result=SE_INV.Admin_Login_Pg_Verf();
		Vertex = getCurrentElement().getName();
		Expected="The Unifia logon page is displayed.";
		// AGS This is changed to get the TestStepLog to get browser description correctly populated 
		//TestHelper.StepWriter1(Vertex, LogonDes, Expected, Result);
		//TestHelper.StepWriter1(Vertex, BrwDes, Expected, Result);
	}
	
	public void v_UserName(){
		Result=SE_INV.Verify_Username(UserName_Value);
		Vertex = getCurrentElement().getName();
		Expected="The username field displays  :"+UserName_Value;
		//TestHelper.StepWriter1(Vertex, UNDes, Expected, Result);
	}
	
	public void v_Password(){
		Result=SE_INV.Verify_Password(UserPassword_Value);
		Vertex = getCurrentElement().getName();
		Expected="The password field displays an obfuscated value.";
		//TestHelper.StepWriter1(Vertex, UNDes, Expected, Result);
		// AGS This is changed to get the TestStepLog to get description correctly populated for password entry
		//TestHelper.StepWriter1(Vertex, PWDes, Expected, Result);
	}
	
	public void v_Roles(){
		//switch vertex
		//switches to the Roles graphml and then role details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Roles screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_ExamType(){
		//switch vertex
		//switches to the ExamType graphml and then Exam type details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Exam Type screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_Staff(){
		//switch vertex
		//switches to the staff graphml and then staff details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Staff screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_ScopeType(){
		//switch vertex
		//switches to the scopes graphml and then scope details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Scopes screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_Scopes(){
		//switch vertex
		//switches to the scopes graphml and then scope details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Scopes screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_AccessPoint(){
		//switch vertex
		//switches to the scopes graphml and then scope details graphml
		Vertex = getCurrentElement().getName();
		Expected="The v_Access Point screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_Barcode(){
		//switch vertex
		//switches to the scopes graphml and then scope details graphml
		Vertex = getCurrentElement().getName();
		Expected="The v_AssignBarcode screen is displayed.";
		System.out.println("Vertex="+Vertex+"; Expected"+Expected);
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_ScopeModel(){
		//switch vertex
		//switches to the scope models graphml and then scope model details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Scope Model screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
		
	
	public void v_ScannerDetail(){
		//switch vertex
		//switches to the Scanners graphml and then scanner details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Scanners screen is displayed.";
		//System.out.println("Vertex="+Vertex+"; Expected"+Expected);
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_Locations(){
		//switch vertex
		//switches to the locations graphml and then location details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Locations screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_Scope(){
		//switch vertex
		//switches to the Scope details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Scope screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	public void v_User(){
		//switch vertex
		//switches to the Users graphml and then user details graphml
		Vertex = getCurrentElement().getName();
		//Result="Pass";
		Expected="The Users screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_Facility(){
		//switch vertex
		//switches to the Facility graphml and then facility details graphml
		Vertex = getCurrentElement().getName();
		Result="Pass";
		Expected="The Facility screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_Location(){
		//switch vertex
		//switches to the Location details graphml
		Vertex = getCurrentElement().getName();
		Result="Pass";
		Expected="The Location screen is displayed.";
		Description="The Location screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_NewRole(){
		//switch vertex
		//switches to the Location details graphml
		Vertex = getCurrentElement().getName();
		Result="Pass";
		Expected="The Roles screen is displayed.";
		Description="The Roles screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_ExamTypeDetail(){
		//switch vertex
		//switches to the Location details graphml
		Vertex = getCurrentElement().getName();
		Result="Pass";
		Expected="The Exam Type screen is displayed.";
		Description="The Exam Type screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}
	
	public void v_LogOnVerf(){
		//empty vertex for logic verification
	}
	

	public void v_InlineError() throws InterruptedException{
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		//Actual=driver.findElementById("message").getText();
		//if(UN.equals(null)||(PW.equals(null))){
		if(UN.equals(null)||(PW.equals(null))||(UN.equals(""))||(PW.equals(""))){
			Msg="Please enter data for all mandatory fields (*).";
			//   Please enter data for all mandatory fields (*).
		}else if(!UN.equals(null)&&!PW.equals(null)){
			Msg="Login failed.\nAn incorrect User ID or Password was entered.";
		}
		Result=SE_Gen.Verify_ErrMsg(Msg);
		Vertex = getCurrentElement().getName();
		Expected="The user is not successfully logged in and an error message is displayed.";
		//TestHelper.StepWriter(Vertex, LogonDes, Expected);
		
			//TestHelper.StepWriter1(Vertex, LoginDes, Expected, Result);
		
	}
	
	public void v_Logout(){
		Result=SE_INV.Admin_Login_Pg_Verf();
		Vertex = getCurrentElement().getName();
		Expected="The Unifia logon page is displayed.";
		//TestHelper.StepWriter1(Vertex, LogonDes, Expected, Result);
	}
	public void v_ScopeSafety(){ // Vijay 19-01-0216
		//switch vertex
		//switches to the scope safety graphml and then scope details graphml
		Vertex = getCurrentElement().getName();
		Expected="The Scope Safety screen is displayed.";
		//TestHelper.StepWriter1(Vertex, Description, Expected,Result);
	}	
}

	


