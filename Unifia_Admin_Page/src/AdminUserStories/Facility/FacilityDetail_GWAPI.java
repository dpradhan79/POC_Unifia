package AdminUserStories.Facility;

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
import java.util.Set;
import java.util.concurrent.TimeUnit;

import TestFrameWork.TestHelper;
import TestFrameWork.Unifia_Admin_Selenium;
//import TestFrameWork.Unifia_Admin_SE_objects;
import TestFrameWork.UnifiaAdminFacilityPage.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminLandingPage.*;


public class FacilityDetail_GWAPI extends ExecutionContext {
//	static FirefoxDriver driver = new FirefoxDriver();

	public String FacName;  //Facility Name path from the graph.
	public ResultSet Facility_rs; //Result Set for Facility Name
	public int FacilityDB; //ID row from Test DB
	public String FacNameValue; //The Facility name entered into the application
	public String FacNameBaseValue;// base facility name in Test Database.
	public int ModifyFacilityDB; //The facility DB ID for the facility to be modified. 
	public String ModifyFacNameValue; //the facility name to be modified.
	public String ModFacNameBaseValue; //the base name of the facility to be modified.
	public String UpdatedFacNameValue; //updated facility name (base name + time stamp)
	public String ModFacAct_Val; //the facility name that will be modified.
	//public String ModFacHang_Time; //the facility hang time to be modified
	public String CustNum; // Facility Customer Number from graph (Valid or Null)
	public String CustNumValue; //Customer Number value to be entered into application from Test Database
	public String Abbreviation; // Facility Abbreviation from graph (Valid or Null)
	public String AbbreviationValue; //Abbreviation value to be entered into application from Test Database
	public String FacAct; //Facility Active?  True or False
	//public String HangTime; // Facility Hang Time specified
	public String Path;  //Tracks what path is being used Modify or New Facility
	public String ErrorCode;
	public String Expected;
	public String Vertex;
	public long cal = Calendar.getInstance().getTimeInMillis();
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	
	//These variables define the manual test step for user
	public String FacNDes; //Facility name description text output to the screen.
	public String CNumDes; //Customer Number description text output to the screen.
	public String FActDes; //Facility Active description text outpu to the screen.
	public String FSavDes; //Facility Save description text output to the screen.
	public String AbbreviationDes; //Facility Abbreviation description text output to the screen.
	//public String FacHangTimeDes; // Facility Scope Hang Time description test output to screen
	public static String GridID; //Grid ID of the row in the Facility List that will be edited or verified. 	
	public static String Result; //Result of a verification point 
	public String ResultAbbv; //Verify the abbreviation after successful save.
	//public String ResultHangTime; //Verify the hangtime after successful save.
	public String ErrMsg; //Error Message Text to be verified.
	public String Actual; //actual value in the application
	public String BrwDes;  //Description for launching the browser
	public String Browser; // Variable for Browser
	public String Description; //Description of output message

	//public static Unifia_Admin_SE_objects SE; //shortcut to link to the Unifia_Admin_SE_objects java class.
	public Facility_Actions SE_FA;
	public Facility_Verification SE_FV;
	public GeneralFunc SE_Gen;
	public LandingPage_Actions SE_LA;
	public LandingPage_Verification SE_LV;
	public TestFrameWork.TestHelper TH;
	
	public Connection conn= null;
	public java.lang.Object executeScript;
	
	public static String actualResult="\t\t\t Facility_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="Facility_TestSummary_";
	public boolean startflag=false;
	public int Scenario=1;
	public boolean ScenarioStartflag=true;
	
	private String SerialNum; // Facility Serial Number from graph (Valid or Null)
	private String SerialNumValue; //Serial Number value to be entered into application from Test Database
	private String SerialNumDes; //Facility Serial Number description text output to the screen.
	private String PrimaryFacVal; //Facility Active?  True or False
	private String ModFacPrimary_Val; //the facility name that will be modified.
	private String IsPrimary;
	private String ResultSerialNum;
	private String ModPrimaryFac_Val;

	//defines the edges of the Facility graphml
	
	public void e_Start () {
		//null edge to start graph. 
		
	}
	public void e_Modify() throws InterruptedException{
		ScenarioStartflag=true;
		Path="Modify";
		System.out.println("Path="+Path);
		//code copied from e_FacName
		try{
			//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_TestData?user=root&password=P@$$w0rd");				
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			// NEED TO ADD FacHangTime
			Statement statement = conn.createStatement();
			//Facility_rs = statement.executeQuery("Select idFacility, FacilityBaseName, EnteredFacilityName, Concat(FacilityBaseName, CURRENT_TIMESTAMP) AS FacilityName, Abbreviation,HangTime from facility WHERE TestScenario='Existing' AND LastUsed=(Select MIN(LastUsed) from facility Where TestScenario ='Existing')");
			Facility_rs = statement.executeQuery("Select idFacility, FacilityBaseName, EnteredFacilityName, Concat(FacilityBaseName, CURRENT_TIMESTAMP) AS FacilityName, Abbreviation, SerialNumber, IsPrimary from facility WHERE TestScenario='Existing' AND LastUsed=(Select MIN(LastUsed) from facility Where TestScenario ='Existing')");
			while(Facility_rs.next()){
				ModifyFacilityDB= Facility_rs.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("FacilityDB = "+ModifyFacilityDB);
				ModFacNameBaseValue= Facility_rs.getString(2); //the second variable is the entered facility base name
				System.out.println("FacNameBaseValue = "+FacNameBaseValue);	
				ModifyFacNameValue= Facility_rs.getString(3); //the third variable is the entered facility name
				System.out.println("ModifyFacNameValue = "+ModifyFacNameValue);	
				UpdatedFacNameValue= Facility_rs.getString(4);
				System.out.println("UpdatedFacNameValue = "+UpdatedFacNameValue);	
				AbbreviationValue= Facility_rs.getString(5); //the fifth variable is the facility abbreviation.
				System.out.println("AbbreviationValue = "+AbbreviationValue);
				SerialNumValue= Facility_rs.getString(6); //the sixth variable is the facility Serial number.
				System.out.println("SerialNumValue = "+SerialNumValue);
				IsPrimary= Facility_rs.getString(7); //the sixth variable is the facility Serial number.
				System.out.println("IsPrimary = "+IsPrimary);
				//ModFacHang_Time= Facility_rs.getString(6); //the sixth variable is the facility abbreviation.
				//System.out.println("Facility Hang Time = "+ModFacHang_Time);
			}
			Facility_rs.close();

			SE_FA.Search_Facility_ByName(ModifyFacNameValue);  //Search for the Facility Name to be modified in the Application.
			System.out.println("Search for facility to be modified which equals "+ModifyFacNameValue);	
			
			GridID=SE_FA.GetGridID_Facility_To_Modify(ModifyFacNameValue); // Get the GridID of the facility to be modified.
			System.out.println("GridID = "+GridID);
//			ModFacAct_Val=SE_FA.Facility_Active_Value(GridID); //Get the current value of the Active checkbox of the facility to be modified.
			ModFacPrimary_Val=SE_FV.getfacilityPrimaryValue(ModifyFacNameValue); //Get the current value of the Active checkbox of the facility to be modified. 
			System.out.println(ModifyFacNameValue+" Primary facility value is "+ModFacPrimary_Val);
			ModFacAct_Val=SE_FA.Facility_Active_Value(ModifyFacNameValue); //Get the current value of the Active checkbox of the facility to be modified. 
			System.out.println("Facility: "+ModifyFacNameValue+" is selected");
			SE_FA.Select_Facility_To_Modify(ModifyFacNameValue); //Select the row of the facility to be modified.
			System.out.println("Facility "+ModifyFacNameValue+" is selected");
			
			Description="Facility: "+ModifyFacNameValue+" is selected";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}
	public void e_New(){
		ScenarioStartflag=true;
		Path="New";
		System.out.println("Path="+Path);
		SE_FA.Add_New_Facility();
		System.out.println("Click new row button");
		
		Description="Click new row button. Executing workflow: "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_FacName_valid() throws  InterruptedException{
		FacName="Valid";
		System.out.println(getCurrentElement().getName());
		 calint++;
		  calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
			  calint=0;
			  calchk="0";
			}
			System.out.println("Name Timestamp:  "+cal+calchk);
			FacNameValue="Test"+cal+calchk;
			FacNameBaseValue="Test";
		if(Path.equalsIgnoreCase("New")){  //If the path is new, Click the Add new row icon and enter the facility name generated from the Test Database.
			SE_FA.Enter_New_Facility_Name(FacNameValue);
			System.out.println("e_FacName, Path=New Valid; Facility Name = "+FacNameValue);
			
			Description="Facility Name = "+FacNameValue+".  Executing workflow: New Valid";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		} else if(Path.equalsIgnoreCase("Modify")){ //If the path is Modify, set the FacNameValue to the Updated facility name and enter that value into the Name field. 
			UpdatedFacNameValue=FacNameValue;
			System.out.println("e_FacName, Path=Modify Name=Valid; Original Facility Name = "+ModifyFacNameValue+" ; Grid ID="+GridID+"; New Facility Name="+FacNameValue);
			SE_FA.Modify_Facility_Name(GridID, FacNameValue); //modify the selected facility name
			System.out.println("e_FacName_valid, Path=Modify Valid; Facility Name = "+FacNameValue);
			
			Description="Original Facility Name = "+ModifyFacNameValue+" ; Grid ID="+GridID+"; New Facility Name="+FacNameValue+".  Executing workflow: Modify Valid";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}	
	}
	
	public void e_FacName_same() throws  InterruptedException{
		FacName="Same";
		System.out.println(getCurrentElement().getName());
		FacNameValue=ModifyFacNameValue;
		System.out.println("e_FacName_same; Path=Modify Same; - No action taken; Facility Name remains "+FacNameValue);
		
		Description="No action taken; Facility Name remains "+FacNameValue+".  Executing workflow: Modify Same";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_FacName_null() throws  InterruptedException{
		FacName="";
		FacNameValue=""; //Set the facility name value to the value in the graph.
		System.out.println(getCurrentElement().getName());
		if(Path.equalsIgnoreCase("New")){ //If the path is new, Click the Add new row icon and enter the facility name generated from the Test Database.
			SE_FA.Enter_New_Facility_Name(FacNameValue);
			System.out.println("e_FacName_null; Path=New; Facility Name = NULL");
			Description="Path=New; Facility Name = NULL.  Executing workflow: New";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		} else if(Path.equalsIgnoreCase("Modify")) { //If the path is Modify, set the FacNameValue to the facility name value in the graph and enter that value into the Name field of the facility to be modified.
			SE_FA.Modify_Facility_Name(GridID, FacNameValue);
			System.out.println("e_FacName_null; Path=Modify; Facility Name = NULL");
			Description="Facility Name = NULL.  Executing workflow: Modify";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	
	}
	public void e_FacName_existing() throws  InterruptedException{
		FacName="Existing";
		System.out.println(getCurrentElement().getName());
		try{//connect to the database with the test data.
			//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd");				
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			
			Statement statement = conn.createStatement();
			Statement update= conn.createStatement();
			Statement insert= conn.createStatement();
			
			if(Path.equalsIgnoreCase("New")){ //create a new facility by setting the facility name to match an existing facility name to test requirement that the facility name must be unique
				Facility_rs = statement.executeQuery("Select idFacility, EnteredFacilityName from facility WHERE TestScenario='Existing' AND LastUsed=(Select MIN(LastUsed) from facility Where TestScenario ='Existing')");
			} else if(Path.equalsIgnoreCase("Modify")){ //select an existing facility and modify the Facility name  to match an existing facility name 
				Facility_rs = statement.executeQuery("Select idFacility, EnteredFacilityName from facility WHERE EnteredFacilityName!='"+ModifyFacNameValue+"' AND TestScenario='Existing' AND LastUsed=(Select MIN(LastUsed) from facility Where EnteredFacilityName!='"+ModifyFacNameValue+"' AND TestScenario ='Existing')");
			}
		
			while(Facility_rs.next()){
				FacilityDB= Facility_rs.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("FacilityDB = "+FacilityDB);
				FacNameValue= Facility_rs.getString(2); //the fifth variable is the customer abbreviation.
				System.out.println("Existing Name = "+FacNameValue);
			}
			Facility_rs.close();
			statement.close();
			conn.close();}
			
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		if(Path.equalsIgnoreCase("New")){ //If the path is new, Click the Add new row icon and enter the facility name generated from the Test Database.
			SE_FA.Enter_New_Facility_Name(FacNameValue);
			System.out.println("e_FacName_existing; Path=New Existing; Facility Name = "+FacNameValue);
			Description="Facility Name = "+FacNameValue+".  Executing workflow: New Existing";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		if(Path.equalsIgnoreCase("Modify")){ //If the path is Modify, set the FacNameValue to the Updated facility name and enter that value into the Name field.
			SE_FA.Modify_Facility_Name(GridID, FacNameValue); 
			System.out.println("e_FacName_existing; Path=Modify Existing; Facility Name = "+FacNameValue);
			Description="Facility Name = "+FacNameValue+".  Executing workflow: Modify Existing";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}	
	}
	

	public void e_Abbreviation_Existing() {
		Abbreviation ="Existing";
		AbbreviationDes = "The user enters "+Abbreviation+" in the Facility Abbreviation field. Executing workflow: "+Path;
		System.out.println(AbbreviationDes);
		Description=AbbreviationDes;
		//Enter conditions
				try{//connect to the database with the test data.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd");				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				
				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();
				
				if(Path.equalsIgnoreCase("New")){ //create a new facility by setting the facility name to match an existing facility name to test requirement that the facility name must be unique
					Facility_rs = statement.executeQuery("Select idFacility, Abbreviation from facility WHERE TestScenario='Existing' AND LastUsed=(Select MIN(LastUsed) from facility Where TestScenario ='Existing')");
				} else if(Path.equalsIgnoreCase("Modify")){ //select an existing facility and modify the Facility name  to match an existing facility name 
					Facility_rs = statement.executeQuery("Select idFacility, Abbreviation from facility WHERE Abbreviation!='"+AbbreviationValue+"' AND TestScenario='Existing' AND LastUsed=(Select MIN(LastUsed) from facility Where Abbreviation!='"+AbbreviationValue+"' AND TestScenario ='Existing')");
				}
			
				while(Facility_rs.next()){
					FacilityDB= Facility_rs.getInt(1); //the first variable in the set is the ID row in the database.
					System.out.println("FacilityDB = "+FacilityDB);
					AbbreviationValue= Facility_rs.getString(2); //the fifth variable is the customer abbreviation.
					System.out.println("AbbreviationValue = "+AbbreviationValue);
				}
				Facility_rs.close();
				statement.close();
				conn.close();}
				
				catch (SQLException ex){
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());	
				}
		if(Path.equalsIgnoreCase("New")){ // If the path is modify enter the abbreviation value into the facility row being modified.
					SE_FA.Enter_New_Facility_Abbreviation(AbbreviationValue);
					System.out.println("e_Abbreviation Abbreviation = "+AbbreviationValue);
					Description+="\r\n\tAbbreviation = "+AbbreviationValue;
					actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
					TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		else if(Path.equalsIgnoreCase("Modify")){ // If the path is modify enter the abbreviation value into the facility row being modified.
			SE_FA.Modify_Facility_Abbreviation(GridID,AbbreviationValue);
			System.out.println("e_Abbreviation Abbreviation = "+AbbreviationValue);	
			Description+="\r\n\tAbbreviation = "+AbbreviationValue;
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	}
	public void e_Abbreviation_Same() {
		Abbreviation = "Same";
		AbbreviationDes = "The user enters "+Abbreviation+" in the Facility Abbreviation field. Executing workflow: "+Path;
		System.out.println(AbbreviationDes);
		// No action taken value remains the same
		
		Description=AbbreviationDes;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	
	public void e_Abbreviation_null() {
		 //Else enter the abbreviation to be the value noted in the graph (i.e. null).
			Abbreviation = "";
			AbbreviationDes = "The user enters "+Abbreviation+" in the Facility Abbreviation field. Executing workflow: "+Path;
			System.out.println(AbbreviationDes);
			AbbreviationValue=Abbreviation;
			if(Path.equalsIgnoreCase("New")){
			  SE_FA.Enter_New_Facility_Abbreviation(AbbreviationValue);
			}else if(Path.equalsIgnoreCase("Modify")){
			SE_FA.Modify_Facility_Abbreviation(GridID,AbbreviationValue);
			}
			System.out.println("e_Abbreviation Abbreviation = "+AbbreviationValue);
			Description="Abbreviation = null";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void e_Abbreviation_Valid() {
		Abbreviation = "Valid";
		AbbreviationDes = "The user enters "+Abbreviation+" in the Facility Abbreviation field. Executing workflow: "+Path;
		System.out.println(AbbreviationDes);
		
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		AbbreviationValue="Abbrv"+cal+calchk;
		System.out.println("Abbreviation: "+AbbreviationValue);
		if(Path.equalsIgnoreCase("New")){
			SE_FA.Enter_New_Facility_Abbreviation(AbbreviationValue);
			System.out.println("e_Abbreviation Abbreviation = "+AbbreviationValue);
		}else if(Path.equalsIgnoreCase("Modify")){
			SE_FA.Modify_Facility_Abbreviation(GridID,AbbreviationValue);
			System.out.println("e_Abbreviation Abbreviation = "+AbbreviationValue);
		}
		Description ="e_Abbreviation_Valid; The user enters "+AbbreviationValue+" in the facility abbreviation field. Path="+Path;
		System.out.println(Description);
		
		Description="The user enters "+AbbreviationValue+" in the facility abbreviation field. Executing workflow:"+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	

	
	public void e_FacAct_True() {
		FacAct ="True";
		if(Path.equalsIgnoreCase("New")){ //If the path is new, set the Active checkbox as per the graph (i.e. true or false) for the new facility field. 
			SE_FA.Selct_New_Facility_Active(FacAct);
		}else if(Path.equalsIgnoreCase("Modify")) { //If the path is modify set the active checbox for the row being modified as per the graph (i.e. true or false)
			SE_FA.Modify_Facility_Active(GridID,ModFacAct_Val,FacAct);
			System.out.println("The Active value was set to "+ModFacAct_Val+" GridID="+GridID+" FacAct="+FacAct);
		}
		FActDes = "e_FacAct: The user selects "+FacAct+" in the facility active field.  Executing workflow:  "+Path;
		System.out.println(FActDes);
		Description="The user selects "+FacAct+" in the facility active field.  Executing workflow:  "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_FacAct_False() {
		FacAct ="False";
		if(Path.equalsIgnoreCase("New")){ //If the path is new, set the Active checkbox as per the graph (i.e. true or false) for the new facility field. 
			SE_FA.Selct_New_Facility_Active(FacAct);
		}else if(Path.equalsIgnoreCase("Modify")) { //If the path is modify set the active checbox for the row being modified as per the graph (i.e. true or false)
			SE_FA.Modify_Facility_Active(GridID,ModFacAct_Val,FacAct);
			System.out.println("The Active value was set to "+ModFacAct_Val+" GridID="+GridID+" FacAct="+FacAct);
		}
		FActDes = "e_FacAct: The user selects "+FacAct+" in the facility active field.  Executing workflow:  "+Path;
		System.out.println(FActDes);
		
		Description="The user selects "+FacAct+" in the facility active field.  Executing workflow:  "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_FacSave() throws InterruptedException{
		
		SE_FA.Save_Facility_Edit();
		FSavDes ="e_FacSave The user clicks the save button.";
		System.out.println(FSavDes);
		
		Description="The user clicks the save button.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
	}
	
	public void e_cancel()throws InterruptedException {
		Description= "The user Clicks the Cancel button.";
		System.out.println(Description);
		SE_FA.Cancel_Facility_Edit();
		SE_FA.clearFacilitySearch();
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
		FacName=null;
		Abbreviation=null;
		SerialNum=null;
		PrimaryFacVal=null;
		FacAct=null;
	}
	
	public void e_SerialNum_Valid() throws InterruptedException {
		SerialNum = "Valid";
		SerialNumDes = "The user enters "+SerialNum+" Serial Number in the Facility Abbreviation field. Executing workflow: "+Path;
		System.out.println(SerialNumDes);
		
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		SerialNumValue=cal+calchk;
		if(SerialNumValue.length()>10){
			SerialNumValue=SerialNumValue.substring(0, 9);
		}
		System.out.println("SerialNum: "+SerialNumValue);
		if(Path.equalsIgnoreCase("New")){
			SE_FA.Enter_New_Facility_SerialNum(SerialNumValue);
			System.out.println("e_SerialNum_Valid SerialNum = "+SerialNumValue);
		}else if(Path.equalsIgnoreCase("Modify")){
			SE_FA.Modify_Facility_SerialNum(GridID,SerialNumValue);
			System.out.println("e_SerialNum_Valid SerialNum = "+SerialNumValue);
		}
		Description ="e_SerialNum_Valid; The user enters "+SerialNumValue+" in the facility Serial Number field. Path="+Path;
		System.out.println(Description);
		
		Description="The user enters "+SerialNumValue+" in the facility serial number field. Executing workflow:"+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SerialNum_Existing() throws InterruptedException {
		SerialNum ="Existing";
		SerialNumDes = "The user enters "+SerialNum+" Serial Number in the Facility Serial Number field. Executing workflow: "+Path;
		System.out.println(SerialNumDes);
		try{//connect to the database with the test data.
		conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
		
		Statement statement = conn.createStatement();
		Statement update= conn.createStatement();
		Statement insert= conn.createStatement();
		
		if(Path.equalsIgnoreCase("New")){ //create a new facility by setting the facility name to match an existing facility name to test requirement that the facility name must be unique
			Facility_rs = statement.executeQuery("Select idFacility, SerialNumber from facility WHERE TestScenario='Existing' AND LastUsed=(Select MIN(LastUsed) from facility Where TestScenario ='Existing')");
		} else if(Path.equalsIgnoreCase("Modify")){ //select an existing facility and modify the Facility name  to match an existing facility name 
			Facility_rs = statement.executeQuery("Select idFacility, SerialNumber from facility WHERE SerialNumber!='"+SerialNumValue+"' AND TestScenario='Existing' AND LastUsed=(Select MIN(LastUsed) from facility Where SerialNumber!='"+SerialNumValue+"' AND TestScenario ='Existing')");
		}
	
		while(Facility_rs.next()){
			FacilityDB= Facility_rs.getInt(1); //the first variable in the set is the ID row in the database.
			System.out.println("FacilityDB = "+FacilityDB);
			SerialNumValue= Facility_rs.getString(2); //the second variable is the serial number.
			System.out.println("SerialNumValue = "+SerialNumValue);
		}
		Facility_rs.close();
		statement.close();
		conn.close();}
		
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		if(Path.equalsIgnoreCase("New")){ // If the path is modify enter the SerialNumber value into the facility row being modified.
					SE_FA.Enter_New_Facility_SerialNum(SerialNumValue);
					System.out.println("e_SerialNum SerialNumber = "+SerialNumValue);
					Description+="\r\n\tSerialNumber = "+SerialNumValue;
					actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
					TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		else if(Path.equalsIgnoreCase("Modify")){ // If the path is modify enter the SerialNumber value into the facility row being modified.
			SE_FA.Modify_Facility_SerialNum(GridID,SerialNumValue);
			System.out.println("e_SerialNum SerialNumber = "+SerialNumValue);	
			SerialNumDes+="\r\n\tSerialNumber = "+SerialNumValue;
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+SerialNumDes;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	}
	
	public void e_SerialNum_Same() {
		SerialNum = "Same";
		SerialNumDes = "The user enters "+Abbreviation+" in the Facility Serial Number field. Executing workflow: "+Path;
		System.out.println(SerialNumDes);
		Description=SerialNumDes;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SerialNum_null() throws InterruptedException {
		SerialNum = "";
		SerialNumDes = "The user enters "+SerialNum+" in the Facility Serial Number field. Executing workflow: "+Path;
		System.out.println(SerialNumDes);
		SerialNumValue=SerialNum;
		if(Path.equalsIgnoreCase("New")){
			SE_FA.Enter_New_Facility_SerialNum(SerialNumValue);
		}else if(Path.equalsIgnoreCase("Modify")){
			SE_FA.Modify_Facility_SerialNum(GridID,SerialNumValue);
		}
		System.out.println("e_SerialNum Serial Number = "+SerialNumValue);
		Description="Serial Number = null";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void e_PrimaryFac_True() throws InterruptedException {
		PrimaryFacVal ="True";
		if(Path.equalsIgnoreCase("New")){ //If the path is new, set the Main checkbox as per the graph (i.e. true or false) for the new facility field. 
			SE_FA.Select_New_Facility_Prime(PrimaryFacVal);
		}else if(Path.equalsIgnoreCase("Modify")) { //If the path is modify set the Main checkbox for the row being modified as per the graph (i.e. true or false)
			SE_FA.Modify_Facility_Prime(GridID,ModFacPrimary_Val,PrimaryFacVal);
			System.out.println("The Primary facility value was set to "+ModFacPrimary_Val+" GridID="+GridID+" PrimaryAct="+PrimaryFacVal);
		}
		FActDes = "e_PrimaryFac: The user selects "+PrimaryFacVal+" in the facility active field.  Executing workflow:  "+Path;
		System.out.println(FActDes);
		Description="The user selects "+PrimaryFacVal+" in the facility active field.  Executing workflow:  "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PrimaryFac_False() throws InterruptedException {
		PrimaryFacVal ="False";
		if(Path.equalsIgnoreCase("New")){ //If the path is new, set the Main checkbox as per the graph (i.e. true or false) for the new facility field. 
			SE_FA.Select_New_Facility_Prime(PrimaryFacVal);
		}else if(Path.equalsIgnoreCase("Modify")) { //If the path is modify set the Main checbox for the row being modified as per the graph (i.e. true or false)
			SE_FA.Modify_Facility_Prime(GridID,ModFacPrimary_Val,PrimaryFacVal);
			System.out.println("The Active value was set to "+ModFacPrimary_Val+" GridID="+GridID+" PrimaryFacVal="+PrimaryFacVal);
		}
		FActDes = "e_PrimaryFac: The user selects "+PrimaryFacVal+" in the facility active field.  Executing workflow:  "+Path;
		System.out.println(FActDes);
		
		Description="The user selects "+PrimaryFacVal+" in the facility active field.  Executing workflow:  "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ModifyPrimaryFac_False() throws InterruptedException {
		PrimaryFacVal ="False";
		FacAct="True";
		ScenarioStartflag=true;
		Path="Modify";
		FacName="Same";
		Abbreviation="Same";
		SerialNum="Same";
		System.out.println("Path="+Path);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();
			Facility_rs = statement.executeQuery("Select idFacility, FacilityBaseName, EnteredFacilityName, Concat(FacilityBaseName, CURRENT_TIMESTAMP) AS FacilityName, Abbreviation, SerialNumber, IsPrimary from facility WHERE IsPrimary='1' AND LastUsed=(Select MIN(LastUsed) from facility Where IsPrimary='1')");
			while(Facility_rs.next()){
				ModifyFacilityDB= Facility_rs.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("FacilityDB = "+ModifyFacilityDB);
				ModFacNameBaseValue= Facility_rs.getString(2); //the second variable is the entered facility base name
				System.out.println("FacNameBaseValue = "+FacNameBaseValue);	
				ModifyFacNameValue= Facility_rs.getString(3); //the third variable is the entered facility name
				System.out.println("ModifyFacNameValue = "+ModifyFacNameValue);	
				UpdatedFacNameValue= Facility_rs.getString(4);
				System.out.println("UpdatedFacNameValue = "+UpdatedFacNameValue);	
				AbbreviationValue= Facility_rs.getString(5); //the fifth variable is the facility abbreviation.
				System.out.println("AbbreviationValue = "+AbbreviationValue);
				SerialNumValue= Facility_rs.getString(6); //the sixth variable is the facility Serial number.
				System.out.println("SerialNumValue = "+SerialNumValue);
				IsPrimary= Facility_rs.getString(7); //the sixth variable is the facility Serial number.
				System.out.println("IsPrimary = "+IsPrimary);
			}
			Facility_rs.close();

			SE_FA.Search_Facility_ByName(ModifyFacNameValue);  //Search for the Facility Name to be modified in the Application.
			System.out.println("Search for facility to be modified which equals "+ModifyFacNameValue);	
			
			ModFacPrimary_Val=SE_FV.getfacilityPrimaryValue(ModifyFacNameValue); //Get the current value of the Active checkbox of the facility to be modified. 
			System.out.println(ModifyFacNameValue+" Primary facility value is "+ModFacPrimary_Val);
			GridID=SE_FA.GetGridID_Facility_To_Modify(ModifyFacNameValue); // Get the GridID of the facility to be modified.
			System.out.println("GridID = "+GridID);
			ModFacAct_Val=SE_FV.getfacilityPrimaryValue(ModifyFacNameValue); //Get the current value of the Primary Facility checkbox of the facility to be modified. 
			System.out.println("Facility: "+ModifyFacNameValue+" is selected");
			SE_FA.Select_Facility_To_Modify(ModifyFacNameValue); //Select the row of the facility to be modified.
			System.out.println("Facility "+ModifyFacNameValue+" is selected");
			
			SE_FA.Modify_Facility_Prime(GridID,ModFacPrimary_Val,PrimaryFacVal);
			System.out.println("The Active value was set to "+ModFacPrimary_Val+" GridID="+GridID+" PrimaryFacVal="+PrimaryFacVal);
			
			SE_FA.Modify_Facility_Active(GridID,ModFacAct_Val,FacAct);
			System.out.println("The Active value was set to "+ModFacAct_Val+" GridID="+GridID+" FacAct="+FacAct);
			
			String ModPrimaryFac_Val=SE_FV.getfacilityPrimaryValue(ModifyFacNameValue); //Get the current value of the Primary Facility checkbox of the facility to be modified. 
			
			Description="e_ModifyPrimaryFac_False: "+ModifyFacNameValue+" primary facility value is to be set as "+PrimaryFacVal+" and the actual value as "+ModPrimaryFac_Val;
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}
	
	public void e_PrimaryFacAct_False() throws InterruptedException {
		FacAct ="False";
		if(Path.equalsIgnoreCase("New")){ //If the path is new, set the Active checkbox as per the graph (i.e. true or false) for the new facility field. 
			SE_FA.Selct_New_Facility_Active(FacAct);
		}else if(Path.equalsIgnoreCase("Modify")) { //If the path is modify set the active checbox for the row being modified as per the graph (i.e. true or false)
			SE_FA.Modify_Facility_Active(GridID,ModFacAct_Val,FacAct);
			System.out.println("The Active value was set to "+ModFacAct_Val+" GridID="+GridID+" FacAct="+FacAct);
		}
		FActDes = "e_FacAct: The user selects "+FacAct+" in the facility active field.  Executing workflow:  "+Path;
		System.out.println(FActDes);
		
		Description="The user selects "+FacAct+" in the facility active field.  Executing workflow:  "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reset() throws InterruptedException{
		SE_FA.clearFacilitySearch();
		FacName=null;
		Abbreviation=null;
		SerialNum=null;
		PrimaryFacVal=null;
		FacAct=null;
	}
	
	//defines the vertices of the Facility graphml
	public void v_Facility(){
		if(ScenarioStartflag==true){
			if(startflag==false){
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				ForFileName = dateFormat.format(date); 
				startflag=true;
			}
			Description="\r\n=====================================";
			Description+="\r\nStart of new Scenario - "+Scenario;
			actualResult=actualResult+"\r\n"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Scenario++;
			ScenarioStartflag=false;
		}
		//When automation is implemented will verify the facility details screen
	}
	
	public void v_FacilityDetail(){
		//When automation is implemented will verify the facility details screen
		Description="Selected path is - "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Facility_Name() throws InterruptedException{
		//Vertex="v_Facility_Name";

		if(Path.equalsIgnoreCase("New")){ //If the path is new, verify the Facility Name is entered in the new row's Facility Name field.
			Result=SE_FV.Verify_NewFacilityName(FacNameValue);  
		} else if(Path.equalsIgnoreCase("Modify")){ //If the path is Modify, verify the Facility Name is entered in the Facility being edited Facility Name field.
			Result=SE_FV.Verify_ModifyFacilityName(GridID,FacNameValue);
		}
		
		Vertex="v_Facility_Name";
		Expected=Vertex+" "+FacNameValue+" is displayed in the facility name field. Result is "+Result;
		System.out.println(Expected);
		//TestHelper.StepWriter1(Vertex, FacNDes, Expected, Result);
		
		//Description=Result+" - "+FacNameValue+" is displayed in the facility name field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	

	
	public void v_Facility_Abbreviation() throws InterruptedException{
		if(Path.equalsIgnoreCase("New")){ //If the path is new, verify the Facility abbreviation is entered in the new row's Abbreviation field.
			Result=SE_FV.Verify_NewAbbreviation(AbbreviationValue); 
		} else if(Path.equalsIgnoreCase("Modify")){ //If the path is Modify, verify the Facility abbreviation is entered in the Facility being edited Abbreviation field.
			Result=SE_FV.Verify_Abbreviation(GridID,AbbreviationValue);
		}
		
		Vertex= "v_Facility_Abbreviation";
		Expected=Vertex+" The Facility Abbreviation is displayed as: "+AbbreviationValue+". Result is "+Result;
		System.out.println(Expected);
		//TestHelper.StepWriter1(Vertex, AbbreviationDes, Expected, Result);
		
		//Description=Result+" - The Facility Abbreviation is displayed as: "+AbbreviationValue;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
	public void v_Facility_Active(){
		Vertex="v_Facility_Active";
		Expected= Vertex+" The Facility Active field is set to:  "+FacAct;
		System.out.println(Expected);
		Result="Pass";
		//TestHelper.StepWriter1(Vertex, FActDes, Expected, Result);
		
		Description=Result+" -  The Facility Active field is set to:  "+FacAct;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_FacilitySaved() throws InterruptedException{
		Vertex="v_FacilitySaved";
		SE_FA.Search_Facility_ByName(FacNameValue);
		Thread.sleep(1000);
		System.out.println("Search facility by name");

		GridID=SE_FA.GetGridID_Facility_To_Modify(FacNameValue);
		
		System.out.println("Grid ID="+GridID);
		ModFacAct_Val=SE_FA.Facility_Active_Value(FacNameValue); //Get the current value of the Active checkbox of the facility to be modified.
		ModPrimaryFac_Val=SE_FV.getfacilityPrimaryValue(FacNameValue); //Get the current value of the Primary Facility checkbox of the facility to be modified. 
		System.out.println("Facility: "+FacNameValue+" is selected");
		SE_FA.Select_Facility_To_Modify(FacNameValue); //Select the row of the facility to be modified.
		System.out.println("Facility "+FacNameValue+" is selected");
		ResultAbbv=SE_FV.Verify_Abbreviation(GridID,AbbreviationValue);
		//ResultHangTime=SE_FV.Verify_ModHangTime(GridID,HangTime);
		ResultSerialNum=SE_FV.Verify_SerialNumber(GridID,SerialNumValue);
		SE_FA.Cancel_Facility_Edit();
		int PrimaryFacDBValue=0;
		if(!GridID.equals(null) && ModFacAct_Val.equalsIgnoreCase(FacAct)&& ResultAbbv.contains("Pass")&& ResultSerialNum.contains("Pass") && ModPrimaryFac_Val.equalsIgnoreCase(PrimaryFacVal)){ //If the GridID is Null, then the save failed. If GridID is not null the save passed.
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Path is New or update the modified row if the path is Modify.
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(PrimaryFacVal.equals("True")){
					PrimaryFacDBValue=1;
				}
				if(Path.equalsIgnoreCase("New")){
					if(PrimaryFacDBValue==1){
						update.executeUpdate("Update facility SET IsPrimary=0 Where IsPrimary=1"); // update the IsPrimary variable if Primary facility is changed
					}
					//need to insert base name
					update.executeUpdate("Update facility SET LastUsed=CURRENT_TIMESTAMP WHERE idFacility="+FacilityDB); // update the LastUsed variable of the row of data used to the current date/time stamp.
					update.close();
					insert.execute("Insert into facility(FacilityBaseName, Abbreviation, TestScenario, EnteredFacilityName, LastUsed, Active, SerialNumber, IsPrimary) values('"+FacNameBaseValue+"', '"+AbbreviationValue+"', 'Existing', '"+FacNameValue+"', CURRENT_TIMESTAMP, '"+FacAct+"', '"+SerialNumValue+"', '"+PrimaryFacDBValue+"')");
					insert.close();
				} else if(Path.equalsIgnoreCase("Modify")){
					if(PrimaryFacDBValue==1){
						update.executeUpdate("Update facility SET IsPrimary=0 Where IsPrimary=1"); // update the IsPrimary variable if Primary facility is changed
					}
					update.executeUpdate("Update facility SET LastUsed=CURRENT_TIMESTAMP, EnteredFacilityName='"+FacNameValue+"', Abbreviation='"+AbbreviationValue+"', Active='"+FacAct+"', SerialNumber='"+SerialNumValue+"', IsPrimary='"+PrimaryFacDBValue+"' WHERE idFacility="+ModifyFacilityDB); // update the LastUsed variable of the row of data used to the current date/time stamp.
					update.close();
				}
				conn.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		}
		else{
			Result="#Failed!# - The Facility was not saved properly. GridID="+GridID+", FacilityName="+FacNameValue+", ModFacAct_Val="+ModFacAct_Val+", ResultAbbv="+ResultAbbv+", ResultSerialNum="+ResultSerialNum+", ModPrimaryFac_Val="+ModPrimaryFac_Val;
		}
		
		Expected=Vertex+" The Facility Name is "+FacNameValue+". GridID="+GridID+" Result = " +Result;
		System.out.println(Expected);
		//TestHelper.StepWriter1(Vertex, FSavDes, Expected, Result);
		
		Description=Result+" -  The Facility Name is "+FacNameValue+". GridID="+GridID;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_FacilitySaveErr() throws InterruptedException{
		Vertex="v_FacilitySaveErr";
		System.out.println("Vertex =  "+Vertex+"FacName = "+FacName);
		if(FacName.equalsIgnoreCase("") || Abbreviation.equalsIgnoreCase("") || SerialNum.equalsIgnoreCase("")){
			ErrorCode="5";
		}else if(PrimaryFacVal.equals("False") && FacAct.equals("True") && Path.equals("Modify")){
			ErrorCode="21";
		}else if(PrimaryFacVal.equals("True") && FacAct.equals("False")){
			ErrorCode="23";
		}else if(FacName.equalsIgnoreCase("Existing")|| Abbreviation.equalsIgnoreCase("Existing")){
			ErrorCode="4";
		}else{
			ErrorCode="5";	
		}
		Result=SE_Gen.Verify_ErrCode(ErrorCode);
		Description="Facility is NOT saved due to ErrorCode("+ErrorCode+").";
		Vertex= getCurrentElement().getName();
		Expected="Facility is NOT saved and an error message is displayed.";
		SE_FA.Cancel_Facility_Edit(); //After the message is verified, click the cancel button to exit the facility edit.
		System.out.println(Expected);
		System.out.println("Error code verification result = "+Result);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		try{ //Checking whether there is atleast one primary facility
			conn=DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			String stmt="Select count(*) from Facility where IsPrimary=1";
			Facility_rs=statement.executeQuery(stmt);
			int primaryFacCount=0;
			while(Facility_rs.next()){
				primaryFacCount=Facility_rs.getInt(1);
			}
			if(primaryFacCount==0){
				Result+=" Bug - 12897: Admin : On the Facility details screen able to save a facility without primary being checked.";
			}
			
		}catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_SerialNumber() throws InterruptedException{
		if(Path.equalsIgnoreCase("New")){ //If the path is new, verify the Facility abbreviation is entered in the new row's Abbreviation field.
			Result=SE_FV.Verify_NewSerialNum(SerialNumValue); 
		} else if(Path.equalsIgnoreCase("Modify")){ //If the path is Modify, verify the Facility abbreviation is entered in the Facility being edited Abbreviation field.
			Result=SE_FV.Verify_SerialNumber(GridID,SerialNumValue);
		}
		
		Vertex= "v_SerialNumber";
		Expected=Vertex+" The Facility Serial Number is displayed as: "+SerialNumValue+". Result is "+Result;
		System.out.println(Expected);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Primary(){
		Vertex="v_Primary";
		Expected= Vertex+" The Primary field is set to: "+PrimaryFacVal;
		System.out.println(Expected);
		Result="Pass";
		Description=Result+" -  The Primary field is set to: "+PrimaryFacVal;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ModifyFacility(){
		//Empty Vertex for empty vertex to set a modify path only after selecting a facility to modify
	}
	
	public void v_FacVerf1(){
		//This is an empty vertex meant to help only in verification logic/navigation
		//There is no function associated to this vertex
		System.out.println(getCurrentElement().getName());
	}
	public void v_FacVerf2(){
		//This is an empty vertex meant to help only in verification logic/navigation
		//There is no function associated to this vertex
		System.out.println(getCurrentElement().getName());
	}
	public void v_FacVerf3(){
		//This is an empty vertex meant to help only in verification logic/navigation
		//There is no function associated to this vertex
		System.out.println(getCurrentElement().getName());
	}
	public void v_FacVerf4(){
		//This is an empty vertex meant to help only in verification logic/navigation
		//There is no function associated to this vertex
		System.out.println(getCurrentElement().getName());
	}
	public void v_FacVerf5(){
		//This is an empty vertex meant to help only in verification logic/navigation
		//There is no function associated to this vertex
		System.out.println(getCurrentElement().getName());
	}
	public void v_FacVerf6(){
		//This is an empty vertex meant to help only in verification logic/navigation
		//There is no function associated to this vertex
		System.out.println(getCurrentElement().getName());
	}
	public void v_FacVerf7(){
		//This is an empty vertex meant to help only in verification logic/navigation
		//There is no function associated to this vertex
		System.out.println(getCurrentElement().getName());
	}
	public void v_FacVerf8(){
		//This is an empty vertex meant to help only in verification logic/navigation
		//There is no function associated to this vertex
		System.out.println(getCurrentElement().getName());
	}
}
