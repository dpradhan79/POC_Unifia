package AdminUserStories.Staff;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.machine.ExecutionContext;

import TestFrameWork.TestHelper;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminStaffPage.Staff_Actions;
import TestFrameWork.UnifiaAdminStaffPage.Staff_Verification;

import com.sun.media.sound.InvalidDataException;

public class StaffDetailsGWAPI extends ExecutionContext{

	public GeneralFunc SE_Gen; //shortcut to link to the UnifiaAdminGeneralFunctions java class.
	public Staff_Actions sta;
	public Staff_Verification stv;
	public Connection conn = null;
	public String Result;
	public String Description;
	public String stm;
	public ResultSet Staff_rs;
	
	public long cal = Calendar.getInstance().getTimeInMillis();
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	public String ErrCode;
	
	public int StaffDBID; // DB id of staff record
	public String SIDact; // value of Staff ID for new
	public String SID; //Value of the Staff ID from graph (valid, existing, same, null)
	public String SBadge; //value of the staff badge from graph (valid, existing, same, null)
	public String Badge_Act; //actual value of the badge
	public String SType; // Value of the Staff Type
	public String SActive; // whether or not the staff is active or not
	public String Title; //Value of the the staff title
	public String LName; //Value of the Staff Last name from graph
	public String FName; //Value of the Staff First name from graph
	public String LName_Act; //Value of the Staff Last name to be input
	public String FName_Act; //Value of the Staff First name to be input
	public int ModStaffDB; // the ID row in the database.
	public String ModStaffTitle; // the title to be modified
	public String ModFName; // first name from DB to be modified.
	public String ModLName; // the last name from DB to be modified.
	public String ModID; // the staff ID to be modified.
	public String ModBadge; //the staff badge to be modified
	public String ModStaffType; //  the staff type
	public String ModStaffStatus; // the status to be modified
	
	//Implementing Description Variables for logging and test writing  
	//These variables will be set in the edges and reused in the vertices
	public String Path;
	public String Vertex;
	public String Edge;

	public String Expected;
	public static String GridID; //Grid ID of the row in the Staff List that will be edited or verified.
	public String SIDTest; // result of Staff ID to be verified after save 
	public String SBadgeTest; // result of Staff Badge to be verified after save 
	public String SFNameTest; // result of Staff First Name verification after save
	public String SLNameTest; // result of Staff Last Name verification after save
	public String STitleResult; // result of Staff title verification
	public String STypeResult; // result of Staff Type verification
	
	public static String actualResult="\t\t\t StaffDetails_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="StaffDetails_TestSummary_";
	public boolean startflag=false;
	public TestFrameWork.TestHelper TH;
	public int Scenario=1;
	public boolean ScenarioStartflag=true;
	//Implements the edges for the Staff details graph
	
	public void e_start(){
		//Empty Edge No Action Taken
	}
	public void e_Modify() throws InterruptedException{
		ScenarioStartflag=true;
		Path="Modify";
		System.out.println("Path: " +Path);

		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			stm="Select StaffID_PK, Title, FirstName, LastName, StaffID, StaffType, Status, BadgeID from staff WHERE TestKeyword='Existing' AND UpdateDate=(Select Min(UpdateDate) from staff where TestKeyword='Existing')";
			Staff_rs = statement.executeQuery(stm);
			while(Staff_rs.next()){
				ModStaffDB= Staff_rs.getInt(1); //the first variable in the set is the ID row in the database.
				ModStaffTitle= Staff_rs.getString(2); // the second variable in the set is the title
				ModFName= Staff_rs.getString(3); //the third variable is the first name
				ModLName= Staff_rs.getString(4); //the fourth variable is the last name
				ModID= Staff_rs.getString(5); //the sixth variable is the staff ID
				ModStaffType= Staff_rs.getString(6); // the seventh variable is the staff type
				ModStaffStatus= Staff_rs.getString(7); //the eighth variable is the staff's status
				ModBadge= Staff_rs.getString(8); //the eighth variable is the staff's status

			}
			Staff_rs.close();
			 System.out.println("ModStaffDB: " +ModStaffDB);
			 System.out.println("ModStaffTitle: " +ModStaffTitle);
			 System.out.println("ModFName: " +ModFName);
			 System.out.println("ModLName: " +ModLName);
			 System.out.println("ModID: " +ModID);
			 System.out.println("ModStaffType: " +ModStaffType);
			 System.out.println("ModStaffStatus: " +ModStaffStatus);
			 System.out.println("ModBadge: " +ModBadge);
			 
			statement.close(); //close the query to get the variable information from the DB
			conn.close();
		}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		sta.Search_Staff_ByLastName(ModLName);
		sta.Search_Staff_ByFirstName(ModFName);
		sta.Search_Staff_ByStaffID(ModID);
		GridID=sta.GetGridID_Staff_To_Modify(ModID);
		ModStaffStatus=sta.Staff_Active_Value(ModID);
		sta.Select_Staff_To_Modify(ModID);
		System.out.println("e_Modify; Staff: First Name="+ModFName +" Last Name="+ ModLName+" Staff ID="+ModID+" is selected");
		Description="Staff: First Name="+ModFName +" Last Name="+ ModLName+" Staff ID="+ModID+" is selected";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	public void e_New() throws InterruptedException{
		ScenarioStartflag=true;
		Path="New";
		Edge=getCurrentElement().getName();
		//System.out.println("e_New; Path="+Path);
		sta.Add_New_Staff();
		Description="Clicked + to add new Staff";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
/**NM 8/19/2015 - removing this test for chrome support. Need to update how this test is performed. 		
 * ModStaffStatus=sta.Staff_Active_Value("-1");
		if(ModStaffStatus.equalsIgnoreCase("True")){
			Result="Pass";
		}
		else{
			Result="Fail - new staff is not defaulted to true.";
		}
		Expected= "The staff is set to Active by Default.";
		Description="The staff is set to Active by Default.";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		TestHelper.StepWriter1(Edge, Description, Expected, Result);**/
		
		}
		
	
	
	public void e_Staff_FirstName_Null() {
		//User Enters the Staff First Name
		Edge=getCurrentElement().getName();
		Expected="First Name set to null";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		FName = "";
		FName_Act="";
		Description= "The user enters null in the first name field.";
		if (Path.equals("New")){
			sta.Enter_New_Staff_FirstName(FName_Act);
		}
		else if (Path.equals("Modify")){
			sta.Modify_First_Name(GridID,FName_Act);
		}
		
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_FirstName_Valid() {
		//User Enters the Staff First Name
		Edge=getCurrentElement().getName();
		Expected="FirstName is valid";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		FName = "Valid";
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}

		FName_Act="FN"+cal+calchk;
		Description= "The user enters "+FName_Act+" in the first name field.";
		if(Path.equalsIgnoreCase("New")){		
			sta.Enter_New_Staff_FirstName(FName_Act);
		}
		else if (Path.equals("Modify")){
			sta.Modify_First_Name(GridID,FName_Act);			
		}
		Description= "The user enters "+FName_Act+" in the first name field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_FirstName_Existing() {
		//User Enters the Staff First Name
		Edge=getCurrentElement().getName();
		Expected="FirstName is an Existing name";
		
		FName = "Existing";
		
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")){
				stm="Select StaffID_PK, FirstName FROM staff Where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from staff Where TestKeyword='Existing')"; 
			}else if(Path.equalsIgnoreCase("Modify")){
				stm="Select StaffID_PK, FirstName FROM staff Where FirstName !='"+ModFName+"' AND TestKeyword='Existing'and UpdateDate=(Select Min(UpdateDate) from staff Where FirstName !='"+ModFName+"' AND TestKeyword='Existing')"; //and UpdateDate=(Select Min(UpdateDate) from staff Where staff.FirstName !='"+ModFName+"' AND staff.LasttName'"+ModLName+"' AND TestKeyword='Existing')";; //put sql statement here to find ID
			}
			System.out.println(stm);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stm);
			while(Staff_rs.next()){
				StaffDBID= Staff_rs.getInt(1); //the first variable in the set is the ID row in the database.
				FName_Act= Staff_rs.getString(2); //the second variable in the set is name to be modified in the database.
			}
			Expected="FirstName is existing";
			System.out.println("Edge:"+Edge+"; Expected=:"+Expected);					
			System.out.println("StaffDBID="+StaffDBID+"; FName_Act="+FName_Act);
			Staff_rs.close();
			
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		if (Path.equals("New")){
			sta.Enter_New_Staff_FirstName(FName_Act);
		}
		else if (Path.equals("Modify")){
			sta.Modify_First_Name(GridID,FName_Act);
		}
		Description= "The user enters "+FName_Act+" in the first name field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_FirstName_Same() {
		//User Enters the Staff First Name
		Edge=getCurrentElement().getName();
		Expected="FirstName is the same";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		FName = "Same";

		FName_Act=ModFName;
		Description= "The user enters "+FName_Act+" in the first name field.";
		if(Path.equalsIgnoreCase("New")){		
			//do nothing this should only be done when modifying an existing staff
		}
		else if (Path.equals("Modify")){
			sta.Modify_First_Name(GridID,FName_Act);			
		}
		//Description= "The user enters "+FName_Act+" in the first name field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_LastName_Null () {
		//User Enters the staff Last Name
		Edge=getCurrentElement().getName();
		Expected="LastName is null";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		LName = "";
		LName_Act="";
		Description= "The user enters "+LName+" in the last name field.";
		System.out.println(Description);
		if (Path.equals("New")){
			sta.Enter_New_Staff_LastName(LName_Act);
		}
		else if (Path.equals("Modify")){
			sta.Modify_Last_Name(GridID,LName_Act);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_Staff_LastName_Valid () {
		//User Enters the staff Last Name
		Edge=getCurrentElement().getName();
		Expected="LastName is valid";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		LName = "Valid";
		
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}

		LName_Act="LN"+cal+calchk;

		if(Path.equalsIgnoreCase("New")){		
			sta.Enter_New_Staff_LastName(LName_Act);
		}
		else if (Path.equals("Modify")){
			sta.Modify_Last_Name(GridID,LName_Act);
		}
		Description= "The user enters "+LName_Act+" in the last name field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_Staff_LastName_Existing () {
		//User Enters the staff Last Name
		Edge=getCurrentElement().getName();
		Expected="LastName is Existing";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		LName = "Existing";
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")){
				stm="Select StaffID_PK, lastname FROM staff Where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from staff Where TestKeyword='Existing')"; 
			}else if(Path.equalsIgnoreCase("Modify")){
				stm="Select StaffID_PK, LastName FROM staff Where LastName !='"+ModLName+"' AND TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from staff Where LastName !='"+ModLName+"' AND TestKeyword='Existing')";
			}
			
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stm);
			while(Staff_rs.next()){
				StaffDBID= Staff_rs.getInt(1); //the first variable in the set is the ID row in the database.
				LName_Act= Staff_rs.getString(2); //the second variable in the set is name to be modified in the database.
			}
			Staff_rs.close();
							
			
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	
		Description= "The user enters "+LName_Act+" in the last name field.";
		System.out.println(Description);
		if (Path.equals("New")){
			sta.Enter_New_Staff_LastName(LName_Act);
		}
		else if (Path.equals("Modify")){
			
			sta.Modify_Last_Name(GridID,LName_Act);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_LastName_Same () {
		//User Enters the staff Last Name
		Edge=getCurrentElement().getName();
		Expected="LastName is Same";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		LName = "Same";
		
		LName_Act=ModLName;

		if(Path.equalsIgnoreCase("New")){		
			//do nothing. this should only be used when modifying 
		}
		else if (Path.equals("Modify")){
			sta.Modify_Last_Name(GridID,LName_Act);
		}
		Description= "The user enters "+LName_Act+" in the last name field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffTitle_Null() throws  InterruptedException{
		//User Enters the Staff Prefix (Mr., Mrs., Ms., Dr., Etc)
		Edge=getCurrentElement().getName();
		Expected="Title is null";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Title = "";
		Description= "The user selects "+Title+" in the prefix field.";
		System.out.println(Description);
		if (Path.equals("New")){
			sta.Selct_New_Title(Title);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Title(GridID,Title);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffTitle_Dr() throws  InterruptedException{
		//User Enters the Staff Prefix (Mr., Mrs., Ms., Dr., Etc)
		Edge=getCurrentElement().getName();
		Expected="Title is Dr.";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Title = "Dr.";
		Description= "The user selects "+Title+" in the prefix field.";
		System.out.println(Description);
		if (Path.equals("New")){
			sta.Selct_New_Title(Title);
		}
		else if (Path.equals("Modify")){
			sta.Selct_Modify_Staff_Title(GridID,Title);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffTitle_Miss() throws  InterruptedException{
		//User Enters the Staff Prefix (Mr., Mrs., Ms., Dr., Etc)
		Edge=getCurrentElement().getName();
		Expected="Title is Miss";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Title = "Miss";
		Description= "The user selects "+Title+" in the prefix field.";
		System.out.println(Description);
		if (Path.equals("New")){
			sta.Selct_New_Title(Title);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Title(GridID,Title);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffTitle_Mr() throws  InterruptedException{
		//User Enters the Staff Prefix (Mr., Mrs., Ms., Dr., Etc)
		Edge=getCurrentElement().getName();
		Expected="Title is Mr.";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Title = "Mr.";
		Description= "The user selects "+Title+" in the prefix field.";
		System.out.println(Description);
		if (Path.equals("New")){
			sta.Selct_New_Title(Title);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Title(GridID,Title);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffTitle_Mrs() throws  InterruptedException{
		//User Enters the Staff Prefix (Mr., Mrs., Ms., Dr., Etc)
		Edge=getCurrentElement().getName();
		Expected="Title is Mrs.";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Title = "Mrs.";
		Description= "The user selects "+Title+" in the prefix field.";
		System.out.println(Description);
		if (Path.equals("New")){
			sta.Selct_New_Title(Title);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Title(GridID,Title);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffTitle_Ms() throws  InterruptedException{
		//User Enters the Staff Prefix (Mr., Mrs., Ms., Dr., Etc)
		Edge=getCurrentElement().getName();
		Expected="Title is Ms.";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Title = "Ms.";
		Description= "The user selects "+Title+" in the prefix field.";
		System.out.println(Description);
		if (Path.equals("New")){
			sta.Selct_New_Title(Title);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Title(GridID,Title);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_StaffTitle_Same() throws  InterruptedException{
		//User Enters the Staff Prefix (Mr., Mrs., Ms., Dr., Etc)
		Edge=getCurrentElement().getName();
		Expected="Title is the same";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Title =ModStaffTitle;
		Description= "The user selects "+Title+" in the prefix field.";
		System.out.println(Description);
		if (Path.equals("New")){
			//do nothing this should only occur when modifying a staff
		}
		else if (Path.equals("Modify")){
			sta.Selct_Modify_Staff_Title(GridID,Title);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	
	public void e_Staff_ID_Valid() {
		//User Enters the Staff Badge ID
		Edge=getCurrentElement().getName();
		Expected="Staff_ID is Valid";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SID = "Valid";
		
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		SIDact="SID"+cal+calchk;
		if (Path.equals("New")){
			sta.Enter_New_StaffID(SIDact);
		}
		else if (Path.equals("Modify")){			
			sta.Modify_StaffID(GridID,SIDact);
		}
		Description = "The user enters "+SIDact+" in the Staff ID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_Staff_ID_Null() {
		//User Enters the Staff Badge ID
		Edge=getCurrentElement().getName();
		Expected="Staff_ID is Null";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SID="";
		SIDact="";			
		if (Path.equals("New")){
			sta.Enter_New_StaffID(SIDact);
		}
		else if (Path.equals("Modify")){			
			sta.Modify_StaffID(GridID,SIDact);
		}
		Description = "The user enters "+SIDact+" in the Staff ID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_ID_Existing() {
		//User Enters the Staff Badge ID
		Edge=getCurrentElement().getName();
		Expected="Staff_ID is Existing";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SID = "Existing";
		
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")){
				stm="Select staff.StaffID_PK, staff.staffID FROM staff Where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from staff Where TestKeyword='Existing')"; 
			}else if(Path.equalsIgnoreCase("Modify")){
				stm="Select staff.StaffID_PK, staff.staffID FROM staff Where staff.staffID !='"+ModID+"' AND TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from staff Where staff.staffID !='"+ModID+"' AND TestKeyword='Existing')";; //put sql statement here to find ID
			}
			System.out.println(stm);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stm);
			while(Staff_rs.next()){
				StaffDBID= Staff_rs.getInt(1); //the first variable in the set is the ID row in the database.
				SIDact= Staff_rs.getString(2); //the second variable in the set is name to be modified in the database.
			}
			System.out.println("StaffDBID="+StaffDBID+"; SIDact="+SIDact);
			Staff_rs.close();							
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		if (Path.equals("New")){
			sta.Enter_New_StaffID(SIDact);
		}
		else if (Path.equals("Modify")){			
			sta.Modify_StaffID(GridID,SIDact);
		}
		Description = "The user enters "+SIDact+" in the Staff ID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_ID_Same() {
		//User Enters the Staff Badge ID
		Edge=getCurrentElement().getName();
		Expected="Staff_ID is Same";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SID = "Same";
		
		SIDact=ModID;
		if (Path.equals("New")){
			//Do nothing. this should only be done when modifying a staff
		}
		else if (Path.equals("Modify")){			
			sta.Modify_StaffID(GridID,SIDact);
		}
		Description = "The user enters "+SIDact+" in the Staff ID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_Type_Null() throws  InterruptedException{
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Type_Null";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SType="";
		System.out.println("+SType Stype is selected ");
		if (Path.equals("New")){
			sta.Selct_New_Staff_Type(SType);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Type(GridID,SType);
		}
		Description = "The user selects "+SType+" in the staff type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffBadge_Valid() {
		//User Enters the Staff Badge ID
		Edge=getCurrentElement().getName();
		Expected="Staff_Badge is Valid";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SBadge = "Valid";
		
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		Badge_Act="BID"+cal+calchk;
		if (Path.equals("New")){
			sta.Enter_New_StaffBadge(Badge_Act);
		}
		else if (Path.equals("Modify")){			
			sta.Modify_StaffBadge(GridID,Badge_Act);
		}
		Description = "The user enters "+Badge_Act+" in the Staff Badge field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_StaffBadge_Null() {
		//User Enters the Staff Badge ID
		Edge=getCurrentElement().getName();
		Expected="Staff Badge is Null";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SBadge="";
		Badge_Act="";			
		if (Path.equals("New")){
			sta.Enter_New_StaffBadge(Badge_Act);
		}
		else if (Path.equals("Modify")){			
			sta.Modify_StaffBadge(GridID,Badge_Act);
		}
		Description = "The user enters "+Badge_Act+" in the Staff Badge field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffBadge_Existing() {
		//User Enters the Staff Badge ID
		Edge=getCurrentElement().getName();
		Expected="Staff Badge is Existing";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SBadge = "Existing";
		
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")){
				stm="Select staff.StaffID_PK, staff.BadgeID FROM staff Where BadgeID!='' and TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from staff Where BadgeID!='' and TestKeyword='Existing')"; 
			}else if(Path.equalsIgnoreCase("Modify")){
				stm="Select staff.StaffID_PK, staff.BadgeID FROM staff Where staff.BadgeID!='' and staff.BadgeID !='"+ModBadge+"' AND TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from staff Where staff.BadgeID!='' and staff.BadgeID !='"+ModBadge+"' AND TestKeyword='Existing')";; //put sql statement here to find ID
			}
			System.out.println(stm);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Staff_rs = statement.executeQuery(stm);
			while(Staff_rs.next()){
				StaffDBID= Staff_rs.getInt(1); //the first variable in the set is the ID row in the database.
				Badge_Act= Staff_rs.getString(2); //the second variable in the set is name to be modified in the database.
			}
			System.out.println("StaffDBID="+StaffDBID+"; Badge_Act="+Badge_Act);
			Staff_rs.close();							
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		if (Path.equals("New")){
			sta.Enter_New_StaffBadge(Badge_Act);
		}
		else if (Path.equals("Modify")){			
			sta.Modify_StaffBadge(GridID,Badge_Act);
		}
		Description = "The user enters "+Badge_Act+" in the Staff Badge field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffBadge_Same() {
		//User Enters the Staff Badge ID
		Edge=getCurrentElement().getName();
		Expected="Staff_Badge is Same";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SBadge = "Same";
		
		Badge_Act=ModBadge;
		if (Path.equals("New")){
			//Do nothing. this should only be done when modifying a staff
		}
		else if (Path.equals("Modify")){			
			sta.Modify_StaffBadge(GridID,Badge_Act);
		}
		Description = "The user enters "+SIDact+" in the Staff ID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
	public void e_Staff_Type_Admin() throws  InterruptedException{
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Type is Admin";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SType = "Admin";
		if (Path.equals("New")){
			sta.Selct_New_Staff_Type(SType);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Type(GridID,SType);
		}
		Description = "The user selects "+SType+" in the staff type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_Type_Nurse() throws  InterruptedException{
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Type is Nurse";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SType = "Nurse";
		if (Path.equals("New")){
			sta.Selct_New_Staff_Type(SType);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Type(GridID,SType);
		}
		Description = "The user selects "+SType+" in the staff type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_Type_Physician() throws  InterruptedException{
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Type is Physician";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SType = "Physician";
		if (Path.equals("New")){
			sta.Selct_New_Staff_Type(SType);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Type(GridID,SType);
		}
		Description = "The user selects "+SType+" in the staff type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_Type_Supervisor() throws  InterruptedException{
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Type is Supervisor";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SType = "Supervisor";
		if (Path.equals("New")){
			sta.Selct_New_Staff_Type(SType);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Type(GridID,SType);
		}
		Description = "The user selects "+SType+" in the staff type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_Type_Tech() throws  InterruptedException{
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Type is Tech";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SType = "Tech";
		if (Path.equals("New")){
			sta.Selct_New_Staff_Type(SType);
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Type(GridID,SType);
		}
		Description = "The user selects "+SType+" in the staff type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_Type_Same() throws  InterruptedException{
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Type is Same";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SType =ModStaffType;
		if (Path.equals("New")){
			//Do nothing. this should only be done when modifying a staff
		}
		else if (Path.equals("Modify")){			
			sta.Selct_Modify_Staff_Type(GridID,SType);
		}
		Description = "The user selects "+SType+" in the staff type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_Active_True() {
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Status is True";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SActive = "True";
		if (Path.equals("New")){
			sta.Selct_New_StaffStatus(SActive);
		}
		else if (Path.equals("Modify")){			
			//ModStaffStatus=sta.Staff_Active_Value(GridID);
			sta.Selct_Modify_StaffStatus(GridID,ModStaffStatus,SActive);
		}
		
		Description = "The user selects "+SActive+" in the staff active field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Staff_Active_False() {
		//User Selects the Staff Type
		Edge=getCurrentElement().getName();
		Expected="Staff_Status is False";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		SActive = "False";
		if (Path.equals("New")){
			sta.Selct_New_StaffStatus(SActive);
		}
		else if (Path.equals("Modify")){			
			//ModStaffStatus=sta.Staff_Active_Value(GridID);
			sta.Selct_Modify_StaffStatus(GridID,ModStaffStatus,SActive);
		}
		Description = "The user selects "+SActive+" in the staff active field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
	
	
	public void e_save() throws InterruptedException{
		//The user user clicks the save button
		Description="The user clicks the save button.";
		System.out.println(Description);
		sta.Save_Staff_Edit();
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_cancel() throws InterruptedException{
		//The user user clicks the cancel button
		Description="The user clicks the cancel button.";
		System.out.println(Description);
		sta.Cancel_Staff_Edit();
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reset() throws InterruptedException{
		//The user user clicks the cancel button
		Description="Reset variables.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	//Implements the vertices for the Staff details graph
	
	public void v_Staff_Saved() throws InterruptedException{
		Expected="The Staff details are saved.";
		Description="The user clicked the Save button";
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex+"; Expected="+Expected);
		
		sta.Search_Staff_ByLastName(LName_Act);
		sta.Search_Staff_ByFirstName(FName_Act);
		sta.Search_Staff_ByStaffID(SIDact);
		System.out.println("Searched for Staff First Name=" +FName_Act+" Last Name="+LName_Act+" Staff ID="+SIDact);

		GridID=sta.GetGridID_Staff_To_Modify(SIDact);
		System.out.println("Grid ID="+GridID);
		ModStaffStatus=sta.Staff_Active_Value(SIDact);
		System.out.println("Staff Status is set to "+ModStaffStatus+" and it should be set to "+SActive);
		sta.Select_Staff_To_Modify(SIDact);
		System.out.println("Selected Staff ID ="+SIDact);
		
		// verifications for status checked after save
		SFNameTest=stv.Verify_ModStaffFirstName(GridID,FName_Act);
		System.out.println("Staff First Name is set correctly? ="+SFNameTest);
		SLNameTest=stv.Verify_ModStaffLastName(GridID,LName_Act);
		System.out.println("Staff Last Name is set correctly? ="+SFNameTest);
		SIDTest=stv.Verify_ModStaffID(GridID,SIDact);
		System.out.println("Staff ID is set correctly? ="+SIDTest);
		SBadgeTest=stv.Verify_ModStaffBadge(GridID,Badge_Act);
		System.out.println("Staff Badge is set correctly? ="+SBadgeTest);
		STitleResult=stv.Verify_ModStaffTitle(GridID,Title);
		System.out.println("Staff Title is set to "+Title+" STitleResult="+STitleResult);
		STypeResult=stv.Verify_ModStaffType(GridID,SType);
		System.out.println("Staff Type is set to "+SType+"; STypeResult="+STypeResult);
		sta.Cancel_Staff_Edit();
		System.out.println("Cancel Edit");
		//NR 14may15 removed the following: && STitleResult.equalsIgnoreCase("Pass") from the save check. There is a bug 513 when Title field was empty, if you click on it to edit it the value defults to the first item in the list.
		//once this bug is fixed, put this back into the save check.
		if(!GridID.equals(null) && ModStaffStatus.equalsIgnoreCase(SActive) && SIDTest.equalsIgnoreCase("Pass")&& SBadgeTest.equalsIgnoreCase("Pass")&&SFNameTest.equalsIgnoreCase("Pass")&& STitleResult.equalsIgnoreCase("Pass") && SLNameTest.equalsIgnoreCase("Pass")&& STypeResult.equalsIgnoreCase("Pass")){
			//If the GridID is Null and current Staff Status doesn't equal expected Staff Status and the current Staff ID  is incorrect then the save failed. If GridID is not null and Staff Active equals the expected active status and StaffID is correct, and the first and last name are correct, and the title and staff type are correct the save passed.
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Path.equalsIgnoreCase("New")){
					stm="Insert into staff(Title, FirstName, LastName, StaffID, StaffType, Status,TestKeyword, BadgeID) values('"+Title+"','"+FName_Act+"', '"+LName_Act+"', '"+SIDact+"', '"+SType+"', '"+SActive+"','Existing', '"+Badge_Act+"')";
					insert.execute(stm); 
					insert.close();
				} else if(Path.equalsIgnoreCase("Modify")){
					stm="Update staff SET Title='"+Title+"', FirstName='"+FName_Act+"', LastName='"+LName_Act+"', StaffID='"+SIDact+"', BadgeID='"+Badge_Act+"', StaffType='"+SType+"', Status='"+SActive+"', UpdateDate=CURRENT_TIMESTAMP WHERE StaffID_PK="+ModStaffDB;
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
					update.close();
				}
				//System.out.println(stm);

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
			Result="#Failed!#";
		}
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
}
	
	
	public void v_Staff_SaveError() throws InterruptedException{
		Expected="The Staff details are NOT saved and an Error Message is displayed.";
		Description="There was an error saving the record";
		System.out.println(Expected);
		System.out.println("FName from graph="+FName);
		System.out.println("FName_Act="+FName_Act);
		System.out.println("LName from graph="+LName);
		System.out.println("LName_Act="+LName_Act);
		System.out.println("SID from graph="+SID);
		System.out.println("SIDact="+SIDact);
		System.out.println("SType"+SType);
		Vertex= getCurrentElement().getName();
		if(FName.equalsIgnoreCase("")|| FName_Act.equalsIgnoreCase("")||LName.equalsIgnoreCase("") ||LName_Act.equalsIgnoreCase("")||SID.equalsIgnoreCase("")|| SIDact.equalsIgnoreCase("") || SType.equalsIgnoreCase("")){ //Entering 'null' in the Staff name will result in an error message indicating the name cannot be empty. 
			ErrCode="5";
		} else if(SID.equalsIgnoreCase("Existing") || SIDact.equalsIgnoreCase("Existing")|| SBadge.equalsIgnoreCase("Existing")){ //Entering 'existing' in the StaffID or Badge will result in an error message indicating the ID or Badge must be unique.
			ErrCode="4";
		} else {
			ErrCode="20";
			System.out.println("ErrCode="+ErrCode);

		}
		Result=SE_Gen.Verify_ErrCode(ErrCode);
		Description= "The staff details are NOT saved and an error message is displayed due to Error Code="+ErrCode;
		System.out.println("Vertex:"+Vertex+"; Expected=:"+Expected);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		

	
	public void v_Staff(){
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
			// Verify the staff Screen
			Vertex= getCurrentElement().getName();
			System.out.println("v_Staff; Vertex="+Vertex);
			ScenarioStartflag=false;
		}
	}
	public void v_Staff_Detail(){
		// Verify the staff Screen
		Vertex= getCurrentElement().getName();
		System.out.println("v_Staff_Detail; Vertex="+Vertex);
		Description="Selected path is : "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Staff_FirstName() throws InterruptedException{
		//Verify that the Staff name is entered per edge condition
		Vertex=getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		Description="The Staff First name is entered per edge condition.";
		
		if(Path.equalsIgnoreCase("New")){
			Result=stv.Verify_NewStaffFirstName(FName_Act);
			}
		else{			
			Result=stv.Verify_ModStaffFirstName(GridID,FName_Act);	
			}
			
		Description= FName_Act+" is displayed in the first name field.";
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Staff_LastName() throws InterruptedException{
		//Verify that the Staff Last name is entered per edge condition
		Vertex=getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		Description="The Staff Last name is entered per edge condition.";
		
		if(Path.equalsIgnoreCase("New")){
			if(LName.equalsIgnoreCase("")){
				Result=stv.Verify_NewStaffLastName(LName_Act);
			}
		}else{			
				Result=stv.Verify_ModStaffLastName(GridID,LName_Act);	
			}
			
		Description= LName_Act+" is displayed in the last name field.";
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

		
	public void v_Staff_Title() throws InterruptedException{
		//Verify that the staff prefix is selected per edge condition 
		Vertex=getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		Description="The Staff Title is entered per edge condition.";
		if(Path.equalsIgnoreCase("New")){
			Result=stv.Verify_NewStaffTitle(Title);	
			}else{				
			Result=stv.Verify_ModStaffTitle(GridID,Title);
			}
					
		Description= Title+ " is displayed in the Title field.";
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Staff_ID() throws InterruptedException{
		//Verify the staff badge Id is entered per edge condition
		Vertex=getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		Description="The Staff ID is entered per edge condition.";
		if(Path.equalsIgnoreCase("New")){
			Result=stv.Verify_NewStaffID(SIDact);	
			}else{				
			Result=stv.Verify_ModStaffID(GridID,SIDact);
			}
		Description= SIDact+" is displayed in the Staff ID field.";
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void v_StaffBadge() throws InterruptedException{
		//Verify the staff badge Id is entered per edge condition
		Vertex=getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		Description="The Staff Badge is entered per edge condition.";
		if(Path.equalsIgnoreCase("New")){
			Result=stv.Verify_NewStaffBadge(Badge_Act);	
			}else{				
			Result=stv.Verify_ModStaffBadge(GridID,Badge_Act);
			}
		Description= Badge_Act+" is displayed in the Staff Badge field.";
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);	
	}
	
	public void v_Staff_Type()throws InterruptedException{
		//Verify that the staff type is select per the edge condition
			Vertex=getCurrentElement().getName();
			System.out.println(getCurrentElement().getName());
			Description="The Staff Type is entered per edge condition.";
			if(Path.equalsIgnoreCase("New")){
				Result=stv.Verify_NewStaffType(SType);	
				}else{				
				Result=stv.Verify_ModStaffType(GridID,SType);
				}
			Description= SType+" is displayed in the Staff Type field.";
			System.out.println(Description);		
		    //TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void v_Staff_Active() throws InterruptedException{
		//Verify that the staff type is select per the edge condition
		Vertex=getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		Description="The Staff Status is entered per edge condition.";
		Result="Pass";
		Description= SActive+" is displayed in the Status field.";
		System.out.println(Description);	
	    //TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
	
	public void v_verf1(){
		//Needed this vertex to implement all the verification points on the save it does nothing
		System.out.println("verf1");
	}
	
	public void v_verf2(){
		//Needed this vertex to implment all the verification points on the save it does nothing
		System.out.println("verf2");
	}
	
	public void v_verf3(){
		//Needed this vertex to implment all the verification points on the save it does nothing
		System.out.println("verf3");
	}
	public void v_verf4(){
		//Needed this vertex to implment all the verification points on the save it does nothing
		System.out.println("verf4");
	}
	public void v_verf5(){
		//Needed this vertex to implment all the verification points on the save it does nothing
		System.out.println("verf5");
	}
    public void v_verf6(){
	//Needed this vertex to implment all the verification points on the save it does nothing
    	System.out.println("verf6");
    }
	
}
