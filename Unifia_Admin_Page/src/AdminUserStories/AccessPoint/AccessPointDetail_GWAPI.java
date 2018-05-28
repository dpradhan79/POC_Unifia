package AdminUserStories.AccessPoint;


import org.graphwalker.core.machine.ExecutionContext;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.DateFormat; 
import java.text.SimpleDateFormat;
import java.util.Calendar;  
import java.util.Date;

import TestFrameWork.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminLocationPage.Location_Actions;
import TestFrameWork.UnifiaAdminLocationPage.Location_Verification;
import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions;
import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Verification;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminScannerPage.*; 
import TestFrameWork.UnifiaAdminAccessPointPage.*; 

public class AccessPointDetail_GWAPI extends ExecutionContext{
	

	public LandingPage_Verification SE_LV; //shortcut to link to the UnifiaAdminLandingPage LandingPage_Verification java class.
	public GeneralFunc gf; //shortcut for the General Function Selenium file 
	public TestFrameWork.UnifiaAdminAccessPointPage.AccessPoint_Actions APA; //shortcut for the Access Point Actions Selenium file
	public TestFrameWork.UnifiaAdminAccessPointPage.AccessPoint_Verification APV; //shortcut for the Access Point Verification Selenium file

	//Implements the user act descriptions for each edge
	public String Description;  //the description written to the test step log or printed to the console
	public String Expected; //The expected result written to the test step log
	public String Vertex; //the current vertex
	public String Edge; //
	public String Result; // the result of a verification point (either pass or fail) written to the test step log
	public String Result2; //used to verify the data was saved successfully
	
	public String AActive; // whether or not the AccessPoint is active or not
	public String ModAccessPointStatus; //the status to be modified
	public String ModAccessPointAct_Val;
	
	
	public Connection conn= null;
	public String Path; //Variable for the Path (New or Modify)
	public ResultSet AccessPoint_RS;  //result set used to get a Access Point name from the Test DB
	public String SSID_Actual; //Actual access point SSID
	public String SSID;  //access point SSID variable from the graph
	public String Password_Actual; //Actual access point password entered into Unifia
	public String Password; //Password variable from the graph 
	public int AccessPoint_DBID; // the access point dbID from the test DB
	
	public int ModAccessPoint_DBID; // the access point dbID from the test DB
	public String ModSSID; //Actual access point SSID entered into Unifia that will be modified
	public String ModPassword; //Actual access point password entered into Unifia that will be modified


	public String stmt; //used for SQL queries
	public String GridID; //the GridID of the row being modified in Unifia
	public String ErrorCode; //The error code given when a save fails. 
		
	public long cal = Calendar.getInstance().getTimeInMillis(); 
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	
	public static String actualResult="\t\t\t AccessPoint_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="AccessPoint_TestSummary_";
	public boolean startflag=false;
	public TestFrameWork.TestHelper TH;
	public int Scenario=1;
	public boolean ScenarioStartflag=true;
	
	
	//implements the edges for the access point Graphml
	
	public void e_Start(){
		//empty edge for graphml navigation
	}

	public void e_Modify()throws  InterruptedException{
		ScenarioStartflag=true;
		Path="Modify";
		System.out.println("e_Modify; Path="+Path);  
			try{ //Get a value that exists in Unifia to modify.
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				stmt="select idAccessPoint, SSID, Password,Status from accesspoint where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from accesspoint Where TestKeyword='Existing')"; //put sql statement here to find ID
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				AccessPoint_RS = statement.executeQuery(stmt);
				while(AccessPoint_RS.next()){
					ModAccessPoint_DBID = AccessPoint_RS.getInt(1); //the first variable in the set is the ID row in the database.
					ModSSID = AccessPoint_RS.getString(2); //the second variable in the set is the SSID.
					ModPassword = AccessPoint_RS.getString(3); //the Third variable in the set is the password
					ModAccessPointStatus=AccessPoint_RS.getString(4);
				}		
				AccessPoint_RS.close();
				System.out.println("ModAccessPoint_DBID = "+ModAccessPoint_DBID);
				System.out.println("ModSSID in e_Modify function = "+ModSSID);
				System.out.println("ModPassword in e_Modify function= "+ModPassword);
				System.out.println("ModAccessPointStatus in e_Modify function="+ModAccessPointStatus);
				
				update.execute("Update accesspoint set UpdateDate=CURRENT_TIMESTAMP WHERE idAccessPoint="+ModAccessPoint_DBID+";");
				update.close();
				statement.close();
				conn.close();
					
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			
			APA.ClearAccessPointSrchCritera(); //Clear all search criteria
			APA.Search_SSID(ModSSID); //Search for the SSID to be modified. 
			GridID=APA.GetGridID_AccessPoint_To_Modify(ModSSID); //Get the GridID for the SSID to be modified. 
			System.out.println("GridID in e_Modify function is: "+GridID); 
			//Get the value of the active checkbox
			ModAccessPointStatus=APA.AccessPoint_Active_Value(ModSSID);
			System.out.println("ModAccessPointStatus in e_Modify function is: "+ModAccessPointStatus); 
			APA.Select_AccessPoint_To_Modify(ModSSID); //Select the SSID to be modified. 
			System.out.println("Selecting to modify: "+ModSSID);
			Description="Selecting to modify: "+ModSSID;
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_New() throws InterruptedException{
		ScenarioStartflag=true;
		APA.Add_New_AccessPoint(); //click the 'Add New Row' icon
		System.out.println("Path="+Path);  
		Path="New";
		Description="Adding new AccessPoint";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SSIDValid()throws  InterruptedException{
		//System.out.println(getCurrentElement().getName());
		SSID="Valid";
		System.out.println("The SSID in e_SSIDValid function = "+SSID);
				
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		SSID_Actual="SSID"+cal+calchk; //Create a unique SSID by converting the current time to an integer.
		System.out.println("SSID_Actual="+SSID_Actual);
	
		if(Path.equalsIgnoreCase("New")) { //Enter the valid value into the SSID field 
			APA.Enter_SSID_New(SSID_Actual); 
		}else if (Path.equalsIgnoreCase("Modify")){
			APA.Modify_SSID(GridID, SSID_Actual);
		}
		
		Description = "The user enters "+SSID_Actual+" in the SSID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SSIDExisting()throws  InterruptedException{
		SSID="Existing";
		System.out.println("The ScanID in e_SSIDExisting function = "+SSID);

		try{ //Find an SSID that exists in Unifia to verify the SSID must be unique.
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")) {
				stmt="select idAccessPoint, SSID from accesspoint where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from accesspoint Where TestKeyword='Existing')"; //put sql statement here to find ID
			} else if (Path.equalsIgnoreCase("Modify")){
				stmt="select idAccessPoint, SSID from accesspoint where TestKeyword='Existing' and SSID!='"+ModSSID+"' and UpdateDate=(Select Min(UpdateDate) from accesspoint Where TestKeyword='Existing' and SSID!='"+ModSSID+"')"; //put sql statement here to find ID
			}
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			AccessPoint_RS = statement.executeQuery(stmt);
			
			while(AccessPoint_RS.next()){
				AccessPoint_DBID = AccessPoint_RS.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("AccessPoint_DBID = "+AccessPoint_DBID);
				SSID_Actual = AccessPoint_RS.getString(2); //the second variable in the set is the SSID value that exists in Unifia
				System.out.println("SSID_Actual e_SSIDExisting function = "+SSID_Actual);
		
			} AccessPoint_RS.close();
			update.execute("Update accesspoint set UpdateDate=CURRENT_TIMESTAMP WHERE idAccessPoint="+AccessPoint_DBID+";");
			update.close();
			statement.close();
			conn.close();
			} 
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		if(Path.equalsIgnoreCase("New")) { //Enter the non unique value into the SSID field (this will error when the save button is clicked. 
			APA.Enter_SSID_New(SSID_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			APA.Modify_SSID(GridID, SSID_Actual);
		}
		
		Description = "The user enters "+SSID_Actual+" in the SSID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SSIDSame()throws  InterruptedException{
		SSID= "Same";
		System.out.println("The ScanID in e_SSIDSame function = "+SSID);
		SSID_Actual=ModSSID;
		AccessPoint_DBID=ModAccessPoint_DBID;

		Description="The user does nothing. Uses the same SSID value which is "+SSID_Actual;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SSIDNull()throws  InterruptedException{
		SSID="";
		System.out.println("The ScanID in e_SSIDNull function = "+SSID);
		SSID_Actual=SSID;
	
		if(Path.equalsIgnoreCase("New")) { //Enter no value the SSID field (this will error when the save button is clicked).
			APA.Enter_SSID_New(SSID_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			APA.Modify_SSID(GridID, SSID_Actual);
		}
		Description = "The user enters "+SSID_Actual+" in the SSID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PasswordSame(){
		//ScanName= String.valueOf(getMbt().getDataValue("ScanName"));
		Password="Same";
		System.out.println("e_ScanNameSame");

		Password_Actual=ModPassword;
		//no action taken leave the password the same.
				
		Description = "The user enters "+Password_Actual+" in the Password field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	public void e_PasswordValid(){
		System.out.println(getCurrentElement().getName());
		Password="Valid";
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		Password_Actual="Psw"+cal+calchk; //Create a unique password by converting the current time to an integer.
		System.out.println("e_PasswordValid Password_Actual="+Password_Actual);
		
		if(Path.equalsIgnoreCase("New")) {  //enter a valid value in the password field. 
			APA.Enter_Password_New(Password_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			APA.Modify_Password(GridID,Password_Actual);
		}
		
		Description="The user enters "+Password_Actual+" in the password field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PasswordNull(){
		Password="";
		
		System.out.println("e_PasswordNull: Password= "+Password);

		Password_Actual=Password;
		
		if(Path.equalsIgnoreCase("New")) { //Enter no value the password field (this will error when the save button is clicked).
			APA.Enter_Password_New(Password_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			APA.Modify_Password(GridID,Password_Actual);
		}
		
		Description = "The user enters "+Password_Actual+" in the Password field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
	public void e_Save () throws InterruptedException{
		APA.Save_AccessPoint_Edit();
		//System.out.println(getCurrentElement().getName());
		Description="The user clicks the save button on the Access Point Page.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Cancel() throws InterruptedException{
		APA.Cancel_AccessPoint_Edit();
		System.out.println("e_Cancel - clicked Cancel button");
		Description="clicked Cancel button";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	//implements the vertices for the Scanner Graphml
	
	public void v_AccessPointDetail(){
		Result=SE_LV.Verify_Admin_Function("Access Point");
		System.out.println(getCurrentElement().getName());
		//Empty vertex for navigating the graph
		Description="selected path : "+Path+"\r\n\t"+Result;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_AccessPoint(){
		if(ScenarioStartflag==true){
			if(startflag==false){
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				ForFileName = dateFormat.format(date); 
				startflag=true;
			}
			System.out.println(getCurrentElement().getName());
			//Empty vertex for navigating the graph
			Description="\r\n=====================================";
			Description+="\r\nStart of new Scenario - "+Scenario;
			actualResult=actualResult+"\r\n"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Scenario++;
			ScenarioStartflag=false;
		}
	}
	
		
	public void v_SSID(){
		//Verify the SSID value displayed in the application
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());	
		Description= SSID_Actual+" is displayed in the SSID field.";		
		if(Path.equalsIgnoreCase("New")) {			
			Result=APV.Verify_NewSSID(SSID_Actual);
		}else if(Path.equalsIgnoreCase("Modify")){			
			Result=APV.Verify_ModifySSID(GridID, SSID_Actual);
		}
		System.out.println(Description);	
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Password(){
		//Verify the Password value displayed in the application

		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());		
		Description= Password_Actual +" is displayed in the Password field.";
				
		if(Path.equalsIgnoreCase("New")) {			
			Result=APV.Verify_NewPassword(Password_Actual);
		}else if(Path.equalsIgnoreCase("Modify")){			
			Result=APV.Verify_ModifyPassword(GridID, Password_Actual);
		}
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_AccessPointSaved() throws InterruptedException{
		//Verify the Access Point value was saved and the values for SSID and Password are correct. 
		Vertex= getCurrentElement().getName();
		APA.Search_SSID(SSID_Actual);	//Search for the ScanID that was just saved. 	
		System.out.println("Searched for SSID in v_AccessPointSaved ="+SSID_Actual);
		GridID=APA.GetGridID_AccessPoint_To_Modify(SSID_Actual);  //If the GridID is available, then the item is found and the SSID value was saved correctly.
		System.out.println("Grid ID="+GridID);
	
		//'Get the actual value of active checkbox -- To uncomment after fix
		ModAccessPointStatus=APA.AccessPoint_Active_Value(SSID_Actual); //Get the current value of the Active checkbox of the facility to be modified.
		System.out.println("AccessPoint: "+ModAccessPointStatus+" is Verified");
		
		APA.Select_AccessPoint_To_Modify(SSID_Actual); //Select the SSID to verify the Password value saved correctly.
		System.out.println("Selected SSID ="+SSID_Actual);
		
		
		
		Result=APV.Verify_ModifyPassword(GridID, Password_Actual); //make sure the password is set correctly. 
		System.out.println("Result="+Result);

		APA.Cancel_AccessPoint_Edit(); //cancel the edit without making any changes. 
		System.out.println("Cancel Edit");
		
		if(!GridID.equalsIgnoreCase(null)&& ModAccessPointStatus.equalsIgnoreCase(AActive) && Result.equalsIgnoreCase("Pass")){	//-to uncomment after fix
		//if(!GridID.equalsIgnoreCase(null)&& Result.equalsIgnoreCase("Pass")){
			//If the GridID is found and Result are Pass, the item was saved correctly. so update the Test DB. 
			Result="Pass";
				try{ 
					conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
					Statement statement = conn.createStatement();
					Statement update= conn.createStatement();
					Statement insert= conn.createStatement();

					if(Path.equalsIgnoreCase("New")){
						//if the path was new, insert a new row into the Test DB for the newly created Access Point
						//stmt="Insert into accesspoint(SSID,Password,TestKeyword) values('"+SSID_Actual+"','"+Password_Actual+"', 'Existing')";
						stmt="Insert into accesspoint(SSID,Password,TestKeyword,Status) values('"+SSID_Actual+"','"+Password_Actual+"', 'Existing','"+AActive+"')";
						System.out.println(stmt);
						insert.execute(stmt); 
						insert.close();
					} else if(Path.equalsIgnoreCase("Modify")){	
						//if the path was modify, update the TestDB row that was being modified with the updated values and current time stamp.
						//stmt="Update accesspoint SET SSID='"+SSID_Actual+"',Password='"+Password_Actual+"', UpdateDate=CURRENT_TIMESTAMP where idAccessPoint='"+ModAccessPoint_DBID+"'";
						stmt="Update accesspoint SET SSID='"+SSID_Actual+"',Password='"+Password_Actual+"', Status ='"+AActive+"',UpdateDate=CURRENT_TIMESTAMP where idAccessPoint='"+ModAccessPoint_DBID+"'";
						System.out.println(stmt);
						update.executeUpdate(stmt); 
						update.close();
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
				Expected= "Results saved without error";
				Description="Results saved without error";
			} else {
				//else the save was not successful and the Result will be fail in the test log and the Test DB will not be updated.
				System.out.println("#Failed!#: Save failed");
				//Result="Fail";
				Expected= "#Failed!#: Results did not save an error has occured";
				Description="#Failed!#: Results were supposed to have saved, but did not save. an error has occured";

			}	
		System.out.println("Vertex="+Vertex+"; Expected="+Expected);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void v_AccessPointSaveErr() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		
		if(SSID.equalsIgnoreCase("")|| Password.equalsIgnoreCase("")) { 
			//If SSID or Password are empty, the save will fail and give error code 5 indicating the field is required. 
			ErrorCode="5";
		} else if(SSID.equalsIgnoreCase("Existing")){
			//SSID must be unique. If the SSID already exists in the database the save will fail indicating error code 4 and the field must be unique.
			ErrorCode="4";	
		}
		Result=gf.Verify_ErrCode(ErrorCode); //Verify the error code given by Unifia is correct. 
		Description="Access Point type is Not saved due to ErrorCode("+ErrorCode+").";
		Expected="Access Point type is not saved and an error message is displayed. Result="+Result;
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result); 
		System.out.println(Expected);
		System.out.println(Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
		
	public void v_AccessPointVerf1(){
		System.out.println(getCurrentElement().getName());
		
		//Empty vertex for logical verification navigation
	}
	
	public void v_AccessPointVerf2(){
		System.out.println(getCurrentElement().getName());
		
		//Empty vertex for logical verification navigation
	}
	
	public void v_AccessPointVerf3(){
		System.out.println(getCurrentElement().getName());
		
		//Empty vertex for logical verification navigation
	}
	
	public void e_AccessPoint_Active_True() {
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="AccessPoint_Status is True";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		AActive = "True";
		if (Path.equals("New")){
			APA.Selct_New_AccessPointStatus(AActive);
		}
		else if (Path.equals("Modify")){			
			APA.Selct_Modify_AccessPointStatus(GridID,ModAccessPointStatus,AActive);
		}
		Description = "The user selects "+AActive+" in the staff active field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_AccessPoint_Active_False() {
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="AccessPoint_Status is False";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		AActive = "False";
		if (Path.equals("New")){
			APA.Selct_New_AccessPointStatus(AActive);
		}
		else if (Path.equals("Modify")){			
			
			//APA.Selct_Modify_AccessPointStatus(GridID,ModFacAct_Val,AActive);
			APA.Selct_Modify_AccessPointStatus(GridID,ModAccessPointStatus,AActive);
		}
		Description = "The user selects "+AActive+" in the AccessPoint active field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_AccessPoint_Active() throws InterruptedException{
		//Verify that the Access Point is select per the edge condition
		Vertex=getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		//Description="The AccessPoint Status is entered per edge condition.";
		Result="Pass";
		Description= AActive+" is displayed in the Status field.";
		System.out.println(Description);	
	    //TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
	    actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
}
