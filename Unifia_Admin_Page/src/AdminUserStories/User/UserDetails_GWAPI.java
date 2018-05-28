package AdminUserStories.User;

//This script will verify all the fields for the user screen. It will also verify error conditions for null vaules and will insure the username is unique.
//The script will add 0 to 3 facilities to the user. Adding and removing more than 3 facilities will be covered in the ComplexAdminTesting
//The script will add 0 to 3 roles to the user. Adding and removing more than 3 roles will be covered in the ComplexAdminTesting

import org.graphwalker.core.machine.ExecutionContext;

import java.awt.AWTException;
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
import java.util.Random;

import TestFrameWork.TestHelper;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Verification;
import TestFrameWork.UnifiaAdminUserPage.*;


public class UserDetails_GWAPI extends ExecutionContext{

	public GeneralFunc SE_Gen; //shortcut to link to the UnifiaAdminGeneralFunctions java class.
	public User_Actions ua; //shortcut to link to the UnifiaAdminUserPage.User_Actions java class.
	public User_Verification uv; //shortcut to link to the UnifiaAdminUserPage.User_Verification java class.
	
	//public int UsrRolCnt;  //Counts the number of times roles have been associated to a user
	public int UsrFacCnt;  //Counts the number of times facilities have been associated to a user
	public String UserName;  //Set's the keyword to determine what username is being tested: Valid, Null, Existing or Same
	public String Staff;  //Set's the keyword to determine what Staff is being tested: Valid, Null, 
	public String Role; //Set's the keyword to determine what Role is being tested: Valid, Null, 
	public String Facility;  //Set's the keyword to determine what Facility is being tested: Valid, Null,
	public String DefFacility; //Set's the keyword to determine what default facility is being tested, valid for multiple facilities, null for multiple facilities or one facility is automatically selected
	public String UsrActive;  //True/False value to set if the user is active
	public String Path; //Sets the path to follow: New or Modify 
	public long cal = Calendar.getInstance().getTimeInMillis();
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	
	public int UserDB; //The user DB ID for existing user entry
	public String UserNameEntered; //the username entered into Unifia
	public String StaffEntered; //the Staff entered into Unifia
	public int StaffDBID; //The staff's DBID in the Test DB
	public String Role1Entered; //the Role entered into Unifia
	public int Role1DBID; //The Role's DBID in the Test DB
	//public String Role2Entered; //the Role entered into Unifia
	//public int Role2DBID; //The Role's DBID in the Test DB
	//public String Role3Entered; //the Role entered into Unifia
	//public int Role3DBID; //The Role's DBID in the Test DB
	public String Facility1Entered; //the Facility entered into Unifia
	public int Facility1DBID; //The Facility's DBID in the Test DB
	public String Facility2Entered; //the Facility entered into Unifia
	public int Facility2DBID; //The Facility's DBID in the Test DB
	public String Facility3Entered; //the Facility entered into Unifia
	public int Facility3DBID; //The Facility's DBID in the Test DB
	public String DefaultFacEntered; //The actual facility set to default.
	public int DefaultFacDBID; //The Default Facility's DBID in the Test DB.


	public int ModifyUserDB; //The DBID for the user to be modified.
	public String ModUserName; //the username entered into Unifia
	public String ModStaff; //the Staff entered into Unifia
	public int ModifyStaffDB;////The Staff DB ID for the user to be modified.
	public String ModRole1; //the Role entered into Unifia
	public int ModifyRoleDB1;////The Role DB ID for the user to be modified.
	//public String ModRole2; //the Role entered into Unifia
	//public int ModifyRoleDB2;////The Role DB ID for the user to be modified.
	//public String ModRole3; //the Role entered into Unifia
	//public int ModifyRoleDB3;////The Role DB ID for the user to be modified.public String ModFacility; //the Facility entered into Unifia
	public String ModFacility1; //the Facility entered into Unifia
	public int ModifyFacilityDB1;////The Facility DB ID for the user to be modified.
	public String ModFacility2; //the Facility entered into Unifia
	public int ModifyFacilityDB2;////The Facility DB ID for the user to be modified.
	public String ModFacility3; //the Facility entered into Unifia
	public int ModifyFacilityDB3;////The Facility DB ID for the user to be modified.
	public String ModDefFacility; //the Facility entered into Unifia
	public int ModifyDefFacilityDB;////The Default Facility DB ID for the user to be modified.
	public String ModUserAct_Val; //the current Active value of the user name that will be modified.

//	public int Role_Val; //The User's Role association value before it's modified.
//	public int Role_Val1; //Used to verify the first role associated to the user 
	//public int Role_Val2; //Used to verify the second role associated to the user 
	//public int Role_Val3; //Used to verify the third role associated to the user 
//	public int ModRole_Val1; //Used to verify the first role associated that was associated to the user before being modified is no longer selected. 
	//public int ModRole_Val2; //Used to verify the second role associated that was associated to the user before being modified is no longer selected. 
	//public int ModRole_Val3; //Used to verify the third role associated that was associated to the user before being modified is no longer selected. 
	public int Facility_Val; //The User's facility association value before it's modified.
	public int Facility_Val1; //Used to verify the first facility associated to the user 
	public int Facility_Val2; //Used to verify the second facility associated to the user 
	public int Facility_Val3; //Used to verify the Third facility associated to the user 
	public int ModFacility_Val1; //Used to verify the first facility associated that was associated to the user before being modified is no longer selected. 
	public int ModFacility_Val2; //Used to verify the second facility associated that was associated to the user before being modified is no longer selected. 
	public int ModFacility_Val3; //Used to verify the second facility associated that was associated to the user before being modified is no longer selected. 


	public Connection conn= null;
	public String stmt;
	public ResultSet User_rs; //Result Set for User Name
	public ResultSet Facility_rs; //result set for facilities
	public ResultSet Role_rs; //result set for Roles 
	public ResultSet Staff_rs; //result set for Staff
	public ResultSet ModFacility_rs;
	public ResultSet ModRole_rs;

	public String Description;
	public String Expected;
	public String Vertex;
	public String Result;
	public String ResultDef;
	public String ResultStaff;
	//public String RoleSaved;
	public String ResultRole1;
	//public String ResultRole2;
	//public String ResultRole3;
	public String ModResultRole1;
	//public String ModResultRole2;
	//public String ModResultRole3;
	
	public String ResultFacility1;
	public String ResultFacility2;
	public String ResultFacility3;
	public String ModResultFacility1;
	public String ModResultFacility2;
	public String ModResultFacility3;
	public String FacilitySaved;
	public boolean InsertData=true;
	
	public static String GridID; //Grid ID of the row in the user List that will be edited or verified. 	
	public String ErrCode; //Error Code value to be verified.
	//Implements the user act descriptions for each edge

	public static String actualResult="\t\t\t UserDetails_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="UserDetails_TestSummary_";
	public boolean startflag=false;
	public int Scenario=1;
	public TestFrameWork.TestHelper TH;
	public boolean ScenarioStartflag=true;
	
	//implemented a troubleshooting variable to go with method e_Edge
	public String Edge;
	
	//Implements the edges for the UserDetails graphml
	
	public void e_Start() {
		Edge=getCurrentElement().getName();

		//The edge is just need to navigate start/restart of pass through the model
	}
	
	public void e_Modify() throws InterruptedException, SQLException, AWTException {
		ScenarioStartflag=true;
		Edge=getCurrentElement().getName();
		System.out.println(Edge);
		Path="Modify";
		//UsrRolCnt=0;
		UsrFacCnt=0;
		//ModifyRoleDB2=0;
		//ModifyRoleDB3=0;
		ModifyFacilityDB1=0;
		ModifyFacilityDB2=0;
		ModifyFacilityDB3=0;
		DefaultFacEntered="";
		Facility1Entered="";
		Facility2Entered="";
		Facility3Entered="";
		DefaultFacDBID=0;
		Facility1DBID=0;
		Facility2DBID=0;
		Facility3DBID=0;
		ModFacility1="";
		ModFacility2="";
		ModFacility3="";
		ModifyFacilityDB1=0;
		ModifyFacilityDB2=0;
		ModifyFacilityDB3=0;
		Facility_Val=0;
		Facility_Val1=0;
		Facility_Val2=0;
		Facility_Val3=0;
		ModFacility_Val1=0;
		ModFacility_Val2=0;
		ModFacility_Val3=0;
		ResultFacility1="";
		ResultFacility2="";
		ResultFacility3="";
		ModResultFacility1="";
		ModResultFacility2="";
		ModResultFacility3="";

		
		try{  //Select a user to modify that exists in the application
			
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			stmt="Select usr.idUsers, usr.UserName, usr.FacilityID1, usr.FacilityID2, usr.FacilityID3, usr.DefaultFacID, usr.roleID1, usr.staffID from users usr  WHERE usr.TestKeyword='Test' AND usr.UpdateDate=(Select Min(UpdateDate) FROM users where TestKeyword='Test')";
			System.out.println(stmt);
	
			User_rs = statement.executeQuery(stmt);
			while(User_rs.next()){
				ModifyUserDB= User_rs.getInt(1); //the first variable in the set is the ID row in the database.
				ModUserName= User_rs.getString(2); //the second variable is the user name entered in the application
				ModifyFacilityDB1= User_rs.getInt(3); //the fourth variable in the set is the DBID of the first facility associated with the user.
				ModifyFacilityDB2= User_rs.getInt(4); // the fifth variable is the DBID of the second facility associated with the user
				ModifyFacilityDB3= User_rs.getInt(5); //the sixth variable is the DBID of the third facility associated with the user
				ModifyDefFacilityDB= User_rs.getInt(6); //the seventh variable in the set is the DBID of the default facility associated with the user
				ModifyRoleDB1= User_rs.getInt(7); //the eighth variable is the DBID of the first role associated with the user
				//ModifyRoleDB2= User_rs.getInt(8); //the ninth variable is the DBID of the second role associated with the user
				//ModifyRoleDB3= User_rs.getInt(9); //the tenth variable in the set is the DBID of the third role associated with the user
				ModifyStaffDB=User_rs.getInt(8); //the eleventh variable is the DBID of the staff associated with the user
			}
									
			User_rs.close();
			
			//Get the Staff ID if ModifyStaffDB is not zero (no staff associated to the user) 
			if(ModifyStaffDB!=0){
				stmt="Select Concat(LastName, ', ',  FirstName) AS StaffName from staff where StaffID_PK="+ModifyStaffDB;
				System.out.println(stmt);

				Staff_rs=statement.executeQuery(stmt);
				while(Staff_rs.next()){
					ModStaff=Staff_rs.getString(1); //the variable is the Staffs 'Lastname, Firstname' 
				}
				Staff_rs.close();
			} else {
				ModStaff="";
			}

			
			//Get the abbreviation of the Default facility associated with the user 

			stmt="Select Abbreviation from facility where idFacility='"+ModifyDefFacilityDB+"'";
			System.out.println(stmt);
	
			ModFacility_rs = statement.executeQuery(stmt);
			while(ModFacility_rs.next()){
				ModDefFacility= ModFacility_rs.getString(1); //the first variable in the set is the ID row in the database.
			}
			System.out.println("ModDefFacility="+ModDefFacility);			
			ModFacility_rs.close();
			
			//Get the abbreviation of the first facility associated with the user 
			stmt="Select Abbreviation from facility where idFacility='"+ModifyFacilityDB1+"'";
			System.out.println(stmt);	
			ModFacility_rs = statement.executeQuery(stmt);
			while(ModFacility_rs.next()){
				ModFacility1= ModFacility_rs.getString(1); //the first variable in the set is the ID row in the database.
			}
			System.out.println("ModFacility1="+ModFacility1);
			
			ModFacility_rs.close();
			if(ModifyFacilityDB2!=0){
				//If there is a second facility associated with the user, get the facility abbreviation.
				stmt="Select Abbreviation from facility where idFacility='"+ModifyFacilityDB2+"'";
				System.out.println(stmt);
		
				ModFacility_rs = statement.executeQuery(stmt);
				while(ModFacility_rs.next()){
					ModFacility2= ModFacility_rs.getString(1); //the first variable in the set is the ID row in the database.
				}
				System.out.println("ModFacility2="+ModFacility2);
				
				ModFacility_rs.close();
			}

			if(ModifyFacilityDB3!=0){
				//If there is a third facility associated with the user, get the facility abbreviation.
				stmt="Select Abbreviation from facility where idFacility='"+ModifyFacilityDB3+"'";
				System.out.println(stmt);
		
				ModFacility_rs = statement.executeQuery(stmt);
				while(ModFacility_rs.next()){
					ModFacility3= ModFacility_rs.getString(1); //the first variable in the set is the ID row in the database.
				}
				System.out.println("ModFacility3="+ModFacility3);
					
				ModFacility_rs.close();
			}
			if (ModifyRoleDB1 != 0) {
				// Get the Role Name of the first role associated with the user
				stmt = "Select Name from role where idrole='"+ModifyRoleDB1 + "'";
						
				System.out.println(stmt);

				ModRole_rs = statement.executeQuery(stmt);
				while (ModRole_rs.next()) {
					ModRole1 = ModRole_rs.getString(1); // the first variable in the set is the ID row in the database.
				}
				System.out.println("ModRole1=" + ModRole1);
				ModRole_rs.close();
			}else if (ModifyRoleDB1==0){
				
	    		ModRole1 = "Admin";
				try{
					conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
					Statement statement1 = conn.createStatement();
					stmt="Select idrole from role where Name='"+ModRole1+"'";
					System.out.println(stmt);
					ModRole_rs = statement1.executeQuery(stmt);
					while(ModRole_rs.next()){
						ModifyRoleDB1=ModRole_rs.getInt(1);
					}
					ModRole_rs.close();
					
					Statement update=conn.createStatement();
					stmt="update users SET roleID1="+ModifyRoleDB1+" where UserName='"+ModUserName+"'";
					System.out.println(stmt);
					update.executeUpdate(stmt);
					update.close();
					
				}catch (SQLException ex){
				    // handle any errors
				    System.out.println("SQLException: " + ex.getMessage());
				    System.out.println("SQLState: " + ex.getSQLState());
				    System.out.println("VendorError: " + ex.getErrorCode());
				}
				ua.ClearAllSearchCriteria(); //Clear all search criteria
				ua.Search_User_ByName(ModUserName); //Search for the user name to be modified.
				GridID=ua.GetGridID_User_To_Modify(ModUserName); //Get the grid id for the user name to be modified.
				ua.Select_User_To_Modify(ModUserName); //Select the user name to be modified.

				//ua.Select_UserRole(ModRole1);
				ua.Select_User_Role(ModRole1);
				ua.Save_User_Edit();
				System.out.println(ModRole1+" role is Selected as there is no role associated to "+ModUserName);
				System.out.println("ModifyRoleDB1="+ModifyRoleDB1);
				
			}
			
/**			if(ModifyRoleDB2!=0){
				//If there is a second role associated with the user, get the role name.
				stmt="Select Name from role where idrole='"+ModifyRoleDB2+"'";
				System.out.println(stmt);
		
				ModRole_rs = statement.executeQuery(stmt);
				while(ModRole_rs.next()){
					ModRole2= ModRole_rs.getString(1); //the first variable in the set is the ID row in the database.
				}
				System.out.println("ModRole2="+ModRole2);
						
				ModRole_rs.close();
			}

			if(ModifyRoleDB3!=0){
				//If there is a third role associated with the user, get the role name.
				stmt="Select Name from role where idrole='"+ModifyRoleDB3+"'";
				System.out.println(stmt);
		
				ModRole_rs = statement.executeQuery(stmt);
				while(ModRole_rs.next()){
					ModRole3= ModRole_rs.getString(1); //the first variable in the set is the ID row in the database.
				}
				System.out.println("ModRole3="+ModRole3);
				
				ModRole_rs.close();
			}**/
			
			 //Print out the variables assigned in the while loop.
			System.out.println("ModifyUserDB = "+ModifyUserDB);
			System.out.println("ModUserName = "+ModUserName);	
			System.out.println("ModifyFacilityDB1 = "+ModifyFacilityDB1);
			System.out.println("ModifyFacilityDB2 = "+ModifyFacilityDB2);
			System.out.println("ModifyFacilityDB3 = "+ModifyFacilityDB3);
			System.out.println("ModifyDefFacilityDB = "+ModifyDefFacilityDB);
			System.out.println("ModifyRoleDB1 = "+ModifyRoleDB1);
			//System.out.println("ModifyRoleDB2 = "+ModifyRoleDB2);
			//System.out.println("ModifyRoleDB3 = "+ModifyRoleDB3);
			System.out.println("ModifyStaffDB = "+ModifyStaffDB);
			System.out.println("ModStaff = "+ModStaff);
			System.out.println("ModDefFacility = "+ModDefFacility);

			statement.close(); //close the query to get the variable information from the DB
			conn.close();
		}
	
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		ua.ClearAllSearchCriteria(); //Clear all search criteria
		ua.Search_User_ByName(ModUserName); //Search for the user name to be modified.
		GridID=ua.GetGridID_User_To_Modify(ModUserName); //Get the grid id for the user name to be modified.
		ua.Select_User_To_Modify(ModUserName); //Select the user name to be modified.
		System.out.println("e_Modify; User: " +ModUserName+" is selected");	
		Description="User: " +ModUserName+" is selected";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_RoleValid() throws InterruptedException, AWTException {
		//This function is to associate the first role to a user. 

		Edge=getCurrentElement().getName();
		Role="Valid";
		System.out.println(Edge);
		//remove all roles associated with the user
		//nm 10/17/2016 commenting out code as roles are now a single select drop down instead of checkboxes
/**		Role_Val=ua.Role_Value(ModRole1); //Get the current value of the Role check box of the user to be modified.
		System.out.println("Role associated to the user is "+Role_Val);
		if(Role_Val==1){
			//ua.Select_UserRole(ModRole1);
			ua.Select_User_Role(ModRole1);
			Description=ModRole1+ " checkbox is unselected ";
			System.out.println(Description);

		} else {
			Description=ModRole1+" is already unselected, no action taken";
		}**/
		/**if(ModifyRoleDB2!=0){
			Role_Val=ua.Role_Value(ModRole2); //Get the current value of the Role check box of the user to be modified.
			System.out.println("Role associated to the user is "+Role_Val);
			if(Role_Val==1){
				ua.Select_UserRole(ModRole2);
				Description=ModRole2+ " checkbox is unselected ";
				System.out.println(Description);

			} else {
				Description=ModRole2+" checkbox is already  unselected, no action taken";
				System.out.println(Description);
			}
		}
		if(ModifyRoleDB3!=0){
			Role_Val=ua.Role_Value(ModRole3); //Get the current value of the Role check box of the user to be modified.
			System.out.println("Role associated to the user is "+Role_Val);
			if(Role_Val==1){
				ua.Select_UserRole(ModRole3);
				Description=ModRole3+ " checkbox is unselected ";
				System.out.println(Description);

			} else {
				Description=ModRole3+" checkbox is already  unselected, no action taken";
				System.out.println(Description);
			}
		}**/
		//System.out.println("Nicole Verify Roles are unchecked");
		
		//UsrRolCnt=1;
		//Set the DBID for role2 and 3 to zero to clear any previous data. 
		//Role2DBID=0;
		//Role3DBID=0;
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();

			stmt="Select idRole, Name from role WHERE Active='True' and idRole!='"+ModifyRoleDB1+"' AND UpdateDate=(Select Min(UpdateDate) FROM role where Active='True' and idRole!='"+ModifyRoleDB1+"')";
			System.out.println(stmt);

			Role_rs = statement.executeQuery(stmt);
			while(Role_rs.next()){
				Role1DBID= Role_rs.getInt(1); //the first variable in the set is the ID row in the database.
				Role1Entered= Role_rs.getString(2); //the second variable in the set is the role to be selected.
			}
			Role_rs.close();
			stmt="Update role SET UpdateDate=CURRENT_TIMESTAMP WHERE idRole="+Role1DBID;
			update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
			update.close();
			statement.close(); //close the query to get the variable information from the DB
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		//nm 10/17/2016 updated code as roles are now a single select drop down instead of checkboxes
		/**Role_Val=ua.Role_Value(Role1Entered); //Get the current value of the Role check box of the user to be modified.
		System.out.println("Role associated to the user is "+Role_Val);
		if(Role_Val==1){
			Description=Edge+": The User Role association is a valid value. "+Role1Entered+" checkbox is already selected, no action taken";
			} else {
			ua.Select_UserRole(Role1Entered);
			Description=Edge+": The User Role association is a valid value. "+Role1Entered+ " checkbox is selected ";
		}**/
		ua.Select_User_Role(Role1Entered);
		Description="The User Role association is a valid value. "+Role1Entered+ " checkbox is selected ";

		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
/**	public void e_MultiRoleValid() throws InterruptedException, AWTException {
		//This function is to associate multiple roles to a user. It will only associate up to 3 roles. 
		Edge=getCurrentElement().getName();
		Role="Valid";
		System.out.println(Edge);

		UsrRolCnt++;
		if(UsrRolCnt<4){
			try{ 
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				if(UsrRolCnt==2){
					stmt="Select idRole, Name from role WHERE Active='True' and idRole!='"+Role1DBID+"' and idRole!='"+ModifyRoleDB1+"' and idRole!='"+ModifyRoleDB2+"' and idRole!='"+ModifyRoleDB3+"' AND UpdateDate=(Select Min(UpdateDate) FROM role where Active='True' and idRole!='"+Role1DBID+"' and idRole!='"+ModifyRoleDB1+"' and idRole!='"+ModifyRoleDB2+"' and idRole!='"+ModifyRoleDB3+"')";
					System.out.println(stmt);
					Role_rs = statement.executeQuery(stmt);
					while(Role_rs.next()){
						Role2DBID= Role_rs.getInt(1); //the first variable in the set is the ID row in the database.
						Role2Entered= Role_rs.getString(2); //the second variable in the set is name to be modified in the database.
					}
					Role_rs.close();
					stmt="Update role SET UpdateDate=CURRENT_TIMESTAMP WHERE idRole="+Role2DBID;
					update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
					update.close();
					Role_Val=ua.Role_Value(Role2Entered); //Get the current value of the Role check box of the user to be modified.
					System.out.println("Role associated to the user is "+Role_Val);
					if(Role_Val==1){
						Description=Role2Entered+" checkbox is already selected, no action taken";
						System.out.println(Description);
						} else {
						ua.Select_UserRole(Role2Entered);
						Description=Edge+": The User has multiple valid Role associations. "+Role2Entered+ " checkbox is selected ";
						System.out.println(Description);
					}

				}else if(UsrRolCnt==3){	
					stmt="Select idRole, Name from role WHERE Active='True' and idRole!='"+Role1DBID+"' and idRole!='"+Role2DBID+"' and idRole!='"+ModifyRoleDB1+"' and idRole!='"+ModifyRoleDB2+"' and idRole!='"+ModifyRoleDB3+"' AND UpdateDate=(Select Min(UpdateDate) FROM role where Active='True' and idRole!='"+Role1DBID+"' and idRole!='"+Role2DBID+"' and idRole!='"+ModifyRoleDB1+"' and idRole!='"+ModifyRoleDB2+"' and idRole!='"+ModifyRoleDB3+"')";
					System.out.println(stmt);
					Role_rs = statement.executeQuery(stmt);
					while(Role_rs.next()){
						Role3DBID= Role_rs.getInt(1); //the first variable in the set is the ID row in the database.
						Role3Entered= Role_rs.getString(2); //the second variable in the set is name to be modified in the database.
					}
					Role_rs.close();
					stmt="Update role SET UpdateDate=CURRENT_TIMESTAMP WHERE idRole="+Role3DBID;
					update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
					update.close();
					Role_Val=ua.Role_Value(Role3Entered); //Get the current value of the Role check box of the user to be modified.
					System.out.println("Role associated to the user is "+Role_Val);
					if(Role_Val==1){
						Description=Edge+": The User has multiple valid Role associations. "+Role3Entered+" checkbox is already selected, no action taken";
						} else {
						ua.Select_UserRole(Role3Entered);
						Description=Edge+": The User has multiple valid Role associations. "+Role2Entered+ " checkbox is selected ";
					}

				}
				statement.close(); //close the query to get the variable information from the DB
				conn.close();												
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		}
		System.out.println(Description);
	}**/
	
	public void e_RoleNull() throws InterruptedException, AWTException {
		Edge=getCurrentElement().getName();
		System.out.println(Edge);
		Role="";
		//remove all roles associated with the user
		//NM 17oct2016 remove code for role check boxes as roles were changed to a single select drop down. 
		/**Role_Val=ua.Role_Value(ModRole1); //Get the current value of the Role check box of the user to be modified.
		System.out.println("Role associated to the user is "+Role_Val);
		if(Role_Val==1){
			ua.Select_UserRole(ModRole1);
			Description=ModRole1+ " checkbox is unselected ";
			System.out.println(Description);
	
		} else {
			Description=ModRole1+" checkbox is already  unselected, no action taken";
			System.out.println(Description);
		}**/
	/**			if(ModifyRoleDB2!=0){
					Role_Val=ua.Role_Value(ModRole2); //Get the current value of the Role check box of the user to be modified.
					System.out.println("Role associated to the user is "+Role_Val);
					if(Role_Val==1){
						ua.Select_UserRole(ModRole2);
						Description=ModRole2+ " checkbox is unselected ";
						System.out.println(Description);
	
					} else {
						Description=ModRole2+" checkbox is already  unselected, no action taken";
						System.out.println(Description);
					}
				}
				if(ModifyRoleDB3!=0){
					Role_Val=ua.Role_Value(ModRole3); //Get the current value of the Role check box of the user to be modified.
					System.out.println("Role associated to the user is "+Role_Val);
					if(Role_Val==1){
						ua.Select_UserRole(ModRole3);
						Description=ModRole3+ " checkbox is unselected ";
						System.out.println(Description);
	
					} else {
						Description=ModRole3+" checkbox is already  unselected, no action taken";
						System.out.println(Description);
					}
				}**/
		ua.Select_User_Role("");
			
		Description="The User Role association is set to null.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void e_RoleSame() {
		Edge=getCurrentElement().getName();
		Role="Same";
		Role1DBID=ModifyRoleDB1;
		Role1Entered=ModRole1;
		/**Role2DBID=ModifyRoleDB2;
		Role2Entered=ModRole2;
		Role3DBID=ModifyRoleDB3;
		Role3Entered=ModRole3;**/
		if(ModifyRoleDB1==0){
			setAttribute("Role",null);
		}

		//Do nothing. don't change the current role selections. 
		Description="The User Role association remains the same value.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffValid() throws InterruptedException {
		//This function is to associate a Staff to a user. 
		Edge=getCurrentElement().getName();
		System.out.println(Edge);
		Staff="Valid";
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			if(Path.equalsIgnoreCase("New")){
				stmt="Select StaffID_PK, Concat(LastName, ', ',  FirstName) AS StaffName from staff WHERE Status='True' and TestKeyword='User' AND UpdateDate=(Select Min(UpdateDate) FROM staff where Status='True' and TestKeyword='User')";
			}else if(Path.equalsIgnoreCase("Modify")){	
				stmt="Select StaffID_PK, Concat(LastName, ', ',  FirstName) AS StaffName from staff WHERE StaffID_PK!='"+ModifyStaffDB+"' and Status='True' and TestKeyword='User' AND UpdateDate=(Select Min(UpdateDate) FROM staff where StaffID_PK!='"+ModifyStaffDB+"' and Status='True' and TestKeyword='User')";
			}
			System.out.println(stmt);

			Staff_rs = statement.executeQuery(stmt);
			while(Staff_rs.next()){
				StaffDBID= Staff_rs.getInt(1); //the first variable in the set is the ID row in the database.
				StaffEntered= Staff_rs.getString(2); //the second variable in the set is the Staff's 'Lastname, Firstname'.
			}
			Staff_rs.close();
			System.out.println("Staff Entered = "+StaffEntered);
			stmt="Update staff SET UpdateDate=CURRENT_TIMESTAMP WHERE StaffID_PK="+StaffDBID;
			update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
			update.close();
			statement.close(); //close the query to get the variable information from the DB
			conn.close();			
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		ua.Select_User_Staff(StaffEntered);

		Description="The User Staff association is a valid value. StaffEntered="+StaffEntered;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_StaffNull() throws InterruptedException {
		Edge=getCurrentElement().getName();
		Staff="";
		StaffEntered="";
		StaffDBID=0;
		ua.Select_User_Staff(StaffEntered);

		Description="The User Staff association is set to null. StaffEntered="+StaffEntered;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void e_StaffSame() {
		Edge=getCurrentElement().getName();
		Staff="Same";
		if(ModifyStaffDB==0){
			StaffEntered="";
			StaffDBID=ModifyStaffDB;
		} else {
			StaffEntered=ModStaff;
			StaffDBID=ModifyStaffDB;
		}

		//do nothing. leave the Staff the same. 
		Description="The User Staff association remains the same value. StaffEntered="+StaffEntered;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_FacilityValid() throws InterruptedException, AWTException {
		//This function is to associate the first facility to a user. e_MultiFacilityValid will be used to associate additional facilities to the user. 

		Edge=getCurrentElement().getName();
		System.out.println(Edge);
		Facility="Valid";
		//remove all Facilities associated with the user
		Facility_Val=ua.Facility_Value(ModFacility1); //Get the current value of the Role check box of the user to be modified.
		System.out.println("Facility associated to the user is "+Facility_Val);
		if(Facility_Val==1){
			ua.Select_UserFacility(ModFacility1);
			Description=ModFacility1+ " checkbox is unselected ";
			System.out.println(Description);
		} else {
			Description=ModFacility1+" checkbox is already  unselected, no action taken";

			System.out.println(Description);
		}
		if(ModifyFacilityDB2!=0){
			Facility_Val=ua.Facility_Value(ModFacility2); //Get the current value of the Role check box of the user to be modified.
			System.out.println("Facility associated to the user is "+Facility_Val);
			if(Facility_Val==1){
				ua.Select_UserFacility(ModFacility2);
				Description=ModFacility2+ " checkbox is unselected ";
				System.out.println(Description);
			} else {
				Description=ModFacility2+" checkbox is already  unselected, no action taken";

				System.out.println(Description);
			}
		}
		if(ModifyFacilityDB3!=0){
			Facility_Val=ua.Facility_Value(ModFacility3); //Get the current value of the Role check box of the user to be modified.
			System.out.println("Facility associated to the user is "+Facility_Val);
			if(Facility_Val==1){
				ua.Select_UserFacility(ModFacility3);
				Description=ModFacility3+ " checkbox is unselected ";
				System.out.println(Description);
			} else {
				Description=ModFacility3+" checkbox is already  unselected, no action taken";

				System.out.println(Description);
			}
		}
		
		System.out.println("Nicole Verify Facilities are unchecked");		
		UsrFacCnt=1;
		//Set the DBID for Facility 2 and 3 to zero to clear any previous data. 
		Facility2DBID=0;
		Facility3DBID=0;
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")){
				stmt="Select idFacility, Abbreviation from facility WHERE Active='True' and TestScenario='User' AND LastUsed=(Select Min(LastUsed) FROM facility where Active='True' and TestScenario='User')";
			}else if(Path.equalsIgnoreCase("Modify")){	
				stmt="Select idFacility, Abbreviation from facility WHERE idFacility!='"+ModifyFacilityDB1+"' and idFacility!='"+ModifyFacilityDB2+"' and idFacility!='"+ModifyFacilityDB3+"' and Active='True' and TestScenario='User' AND LastUsed=(Select Min(LastUsed) FROM facility where idFacility!='"+ModifyFacilityDB1+"' and idFacility!='"+ModifyFacilityDB2+"' and idFacility!='"+ModifyFacilityDB3+"' and Active='True' and TestScenario='User')";
			}
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Facility_rs = statement.executeQuery(stmt);
			while(Facility_rs.next()){
				Facility1DBID= Facility_rs.getInt(1); //the first variable in the set is the ID row in the database.
				Facility1Entered= Facility_rs.getString(2); //the second variable in the set is name to be modified in the database.
			}
			Facility_rs.close();
			stmt="Update facility SET LastUsed=CURRENT_TIMESTAMP WHERE idFacility="+Facility1DBID;
			update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
			update.close();
			statement.close(); //close the query to get the variable information from the DB
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		Facility_Val=ua.Facility_Value(Facility1Entered); //Get the current value of the Role check box of the user to be modified.
		System.out.println("Facility associated to the user is "+Facility_Val);
		if(Facility_Val==1){
			Description="The User Facility association is a valid value. "+Facility1Entered+" checkbox is already selected, no action taken";
			} else {
			ua.Select_UserFacility(Facility1Entered);
			Description="The User Facility association is a valid value. "+Facility1Entered+ " checkbox is selected ";
		}

		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_MultiFacilityValid() throws AWTException, InterruptedException {
		//This function is to associate multiple facilities to a user. It will only associate up to 3 facilities. 

		Edge=getCurrentElement().getName();
		Facility="Valid";
		UsrFacCnt++;

		if(UsrFacCnt<4){

			try{ 
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				if(UsrFacCnt==2){
					stmt="Select idFacility, Abbreviation from facility WHERE idFacility!='"+Facility1DBID+"' and idFacility!='"+ModifyFacilityDB1+"' and idFacility!='"+ModifyFacilityDB2+"' and idFacility!='"+ModifyFacilityDB3+"' and Active='True' and TestScenario='User' AND LastUsed=(Select Min(LastUsed) FROM facility where idFacility!='"+Facility1DBID+"' and idFacility!='"+ModifyFacilityDB1+"' and idFacility!='"+ModifyFacilityDB2+"' and idFacility!='"+ModifyFacilityDB3+"' and Active='True' and TestScenario='User')";
					System.out.println(stmt);
					Facility_rs = statement.executeQuery(stmt);
					while(Facility_rs.next()){
						Facility2DBID= Facility_rs.getInt(1); //the first variable in the set is the ID row in the database.
						Facility2Entered= Facility_rs.getString(2); //the second variable in the set is name to be modified in the database.
					}
					Facility_rs.close();
					stmt="Update facility SET LastUsed=CURRENT_TIMESTAMP WHERE idFacility="+Facility2DBID;

					update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
					update.close();
					Facility_Val=ua.Facility_Value(Facility2Entered); //Get the current value of the Role check box of the user to be modified.
					System.out.println("Facility associated to the user is "+Facility_Val);
					if(Facility_Val==1){
						Description="The User has multiple valid Facility associations. "+Facility2Entered+" checkbox is already selected, no action taken";
						} else {
						ua.Select_UserFacility(Facility2Entered);
						Description="The User has multiple valid Facility associations. "+Facility2Entered+ " checkbox is selected ";
					}

				}else if(UsrFacCnt==3){	
					stmt="Select idFacility, Abbreviation from facility WHERE idFacility!='"+Facility1DBID+"' and idFacility!='"+Facility2DBID+"' and idFacility!='"+ModifyFacilityDB1+"' and idFacility!='"+ModifyFacilityDB2+"' and idFacility!='"+ModifyFacilityDB3+"' and Active='True' and TestScenario='User' AND LastUsed=(Select Min(LastUsed) FROM facility where idFacility!='"+Facility1DBID+"' and idFacility!='"+Facility2DBID+"' and idFacility!='"+ModifyFacilityDB1+"' and idFacility!='"+ModifyFacilityDB2+"' and idFacility!='"+ModifyFacilityDB3+"' and Active='True' and TestScenario='User')";
					System.out.println(stmt);
					Facility_rs = statement.executeQuery(stmt);
					while(Facility_rs.next()){
						Facility3DBID= Facility_rs.getInt(1); //the first variable in the set is the ID row in the database.
						Facility3Entered= Facility_rs.getString(2); //the second variable in the set is name to be modified in the database.
					}
					Facility_rs.close();
					stmt="Update facility SET LastUsed=CURRENT_TIMESTAMP WHERE idFacility="+Facility3DBID;
					update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
					update.close();
					Facility_Val=ua.Facility_Value(Facility3Entered); //Get the current value of the Role check box of the user to be modified.
					System.out.println("Facility associated to the user is "+Facility_Val);
					if(Facility_Val==1){
						Description="The User has multiple valid Facility associations. "+Facility3Entered+" checkbox is already selected, no action taken";
						} else {
						ua.Select_UserFacility(Facility3Entered);
						Description="The User has multiple valid Facility associations. "+Facility3Entered+ " checkbox is selected ";
					}

				}
				statement.close(); //close the query to get the variable information from the DB
				conn.close();												
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		}
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_FacilityNull() throws AWTException, InterruptedException {
		Edge=getCurrentElement().getName();
		System.out.println(Edge);

		Facility="";
		DefFacility="";
		DefaultFacEntered="";
		UsrFacCnt=0;
		Facility1Entered="";
		if(Path.equalsIgnoreCase("Modify")){
			//remove all Facilities associated with the user
			ua.Select_User_DefaultFacility(DefaultFacEntered);

			Facility_Val=ua.Facility_Value(ModFacility1); //Get the current value of the Role check box of the user to be modified.
			System.out.println("Facility associated to the user is "+Facility_Val);
			if(Facility_Val==1){
				ua.Select_UserFacility(ModFacility1);
				Description=ModFacility1+ " checkbox is unselected ";
				System.out.println(Description);
			} else {
				Description=ModFacility1+" checkbox is already  unselected, no action taken";

				System.out.println(Description);
			}
			if(ModifyFacilityDB2!=0){
				Facility_Val=ua.Facility_Value(ModFacility2); //Get the current value of the Role check box of the user to be modified.
				System.out.println("Facility associated to the user is "+Facility_Val);
				if(Facility_Val==1){
					ua.Select_UserFacility(ModFacility2);
					Description=ModFacility2+ " checkbox is unselected ";
					System.out.println(Description);
				} else {
					Description=ModFacility2+" checkbox is already  unselected, no action taken";

					System.out.println(Description);
				}
			}
			if(ModifyFacilityDB3!=0){
				Facility_Val=ua.Facility_Value(ModFacility3); //Get the current value of the Role check box of the user to be modified.
				System.out.println("Facility associated to the user is "+Facility_Val);
				if(Facility_Val==1){
					ua.Select_UserFacility(ModFacility3);
					Description=ModFacility3+ " checkbox is unselected ";
					System.out.println(Description);
				} else {
					Description=ModFacility3+" checkbox is already  unselected, no action taken";

					System.out.println(Description);
				}
			}
			
			
		}
		
		Description="The User Facility association is set to null.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void e_FacilitySame() {
		Edge=getCurrentElement().getName();
		Facility="Same";
		DefFacility="Same";
		UsrFacCnt=1;
		Facility1DBID=ModifyFacilityDB1;
		Facility1Entered=ModFacility1;
		Facility2DBID=ModifyFacilityDB2;
		Facility2Entered=ModFacility2;
		Facility3DBID=ModifyFacilityDB3;
		Facility3Entered=ModFacility3;
		DefaultFacEntered=ModDefFacility;
		DefaultFacDBID=ModifyDefFacilityDB;
		if(ModifyFacilityDB1==0){
			setAttribute("Facility", null);
		}
		Description="The User Facility association remains the same value.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DefaultFacValid() throws AWTException, InterruptedException {
		Edge=getCurrentElement().getName();
		DefFacility="Valid";
		if(UsrFacCnt==2){
			DefaultFacEntered=Facility1Entered;
			DefaultFacDBID=Facility1DBID;
		} else if(UsrFacCnt==3){
			DefaultFacEntered=Facility2Entered;
			DefaultFacDBID=Facility2DBID;
		} else if(UsrFacCnt==4){
			DefaultFacEntered=Facility3Entered;
			DefaultFacDBID=Facility3DBID;
		}
		ua.Select_User_DefaultFacility(DefaultFacEntered);

		Description="The User has a valid default Facility selected. DefaultFacEntered="+DefaultFacEntered;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DefaultFacNull() throws AWTException, InterruptedException {
		Edge=getCurrentElement().getName();
		DefFacility="";
		DefaultFacEntered="";
		ua.Select_User_DefaultFacility(DefaultFacEntered);

		Description="The User has no default Facility selected. DefaultFacEntered="+DefaultFacEntered;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DefaultOne() throws AWTException, InterruptedException {
		Edge=getCurrentElement().getName();
		DefFacility="One";
		DefaultFacEntered=Facility1Entered;
		DefaultFacDBID=Facility1DBID;
		ua.Select_User_DefaultFacility(DefaultFacEntered);

		DefaultFacEntered=Facility1Entered;
		DefaultFacDBID=Facility1DBID;
		Description="The User has a valid default Facility selected. DefaultFacEntered="+DefaultFacEntered;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Save() throws InterruptedException, AWTException{
		Edge=getCurrentElement().getName();
		ua.Save_User_Edit();
		Description="The user clicks the Save button.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Cancel() throws InterruptedException, AWTException{
		Edge=getCurrentElement().getName();
		ua.Cancel_User_Edit();

		Description="The user clicks the Cancel button.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	//Implements the vertexes for the UserDetails graphml v_User
	
	public void v_User(){
		if(ScenarioStartflag==true){
			//used for navigation
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
	}
	
	public void v_UserDetails(){
		//used for navigation
		Description="User: " +ModUserName+" is selected";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_UserName() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);
		Result = uv.Verify_UserName(UserNameEntered);

		Description= UserName+" is displayed in the username field.";
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Role() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);
		//Cannot verify check box values until after the data is saved. The verification will be done in v_UserSaved
		Description= "Pass - "+Role+" is displayed in the role field.";
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Staff(){
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);
		Result=uv.Verify_Staff_Selection(StaffEntered);
		Description= StaffEntered+" is displayed in the Staff field.";
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Facility(){
		Vertex= getCurrentElement().getName();
		//Cannot verify check box values until after the data is saved. The verification will be done in v_UserSaved
		System.out.println(Vertex);
		Description= "Pass - "+Facility+" is displayed in the Facility field.  There are "+UsrFacCnt+" Facilities associated to the user.";
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_DefaultFac1(){
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);

		//do nothing placeholder for guarded edge.

	}
	
	public void v_DefaultFac(){
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);

		Result=uv.Verify_DefaultFacility_Selection(DefaultFacEntered);
		Description= "Default Facility="+DefaultFacEntered+". Keyword for the default facility="+DefFacility;
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_UserVerf3(){
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);
		//this vertex is just for logical nagivation
	}
	
	public void v_UserVerf5(){
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);
		//this vertex is just for logical nagivation
	}
	
	public void v_UserVerf6(){
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);
		//this vertex is just for logical nagivation
	}
	
	public void v_UserSaved() throws InterruptedException, AWTException{
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);
		
		Description="The user clicked the Save button";
		Expected="User is saved successfully.";
		System.out.println("Vertex="+Vertex+"; Expected="+Expected);
		
		ua.Search_User_ByName(ModUserName);

		System.out.println("Searched for User ="+ModUserName);

		GridID=ua.GetGridID_User_To_Modify(ModUserName);
		System.out.println("Grid ID="+GridID);
		Result="Grid ID="+GridID;
		ua.Select_User_To_Modify(ModUserName);
		Thread.sleep(2000);
		
		System.out.println("Selected User ="+ModUserName);		

		ResultDef=uv.Verify_DefaultFacility_Selection(DefaultFacEntered); //Verify the Default Facility Value is set correctly. 
		System.out.println("ResultDef ="+ResultDef);
		Result+="\r\n\tResultDef ="+ResultDef;
		ResultStaff=uv.Verify_Staff_Selection(StaffEntered);
		System.out.println("ResultStaff ="+ResultStaff);
		Result+="\r\n\tResultStaff ="+ResultStaff;
		
		
		//NM 17oct2016 remove code for role check boxes as roles were changed to a single select drop down.
		/**Role_Val1=ua.Role_Value(Role1Entered); //Get the value of the Role check box to verify the data was saved correctly.
		System.out.println("Role_Val1 ="+Role_Val1);		
		if(Role_Val1==1){
			ResultRole1="Pass";
		} else {
			if(Role_Val1==0 && ModRole1==null){
				ResultRole1="Pass";
			}else{
				ResultRole1="Failed - "+Role1Entered+" should be selected but it is not. ";
			}
			
		}**/
		
		ResultRole1=uv.Verify_Role_Selection(Role1Entered);
		System.out.println("ResultRole1 ="+ResultRole1);
		Result+="\r\n\tResultRole1 ="+ResultRole1;

/**		if(Role2DBID!=0){
			Role_Val2=ua.Role_Value(Role2Entered); //Get the value of the Role check box to verify the data was saved correctly
			if(Role_Val2==1){
				ResultRole2="Pass";
			} else {
				ResultRole2="Failed - "+Role2Entered+" should be selected but it is not. ";
			}
		} else {
			ResultRole2="Pass";
		}
		System.out.println("ResultRole2 ="+ResultRole2);		

		if(Role3DBID!=0){
			Role_Val3=ua.Role_Value(Role3Entered); //Get the value of the Role check box to verify the data was saved correctly
			if(Role_Val3==1){
				ResultRole3="Pass";
			} else {
				ResultRole3="Failed - "+Role3Entered+" should be selected but it is not. ";
			}
		} else {
			ResultRole3="Pass";
		}
		System.out.println("ResultRole3 ="+ResultRole3);	**/	
		
		//NM 17oct2016 remove code for role check boxes as roles were changed to a single select drop down.
/**		if(Role!="Same"){
			if(ModifyRoleDB1!=0){
				ModRole_Val1=ua.Role_Value(ModRole1); //Get the value of the Role check box to verify the data was saved correctly
				if(ModRole_Val1==0){
					ModResultRole1="Pass";
				} else {
					ModResultRole1="Failed - "+ModRole1+" should not be selected but it is.";
				}
			} else {
				ModResultRole1="Pass";

			}
			System.out.println("ModResultRole1 ="+ModResultRole1);		

			if(ModifyRoleDB2!=0){
				ModRole_Val2=ua.Role_Value(ModRole2); //Get the value of the Role check box to verify the data was saved correctly
				if(ModRole_Val2==0){
					ModResultRole2="Pass";
				} else {
					ModResultRole2="Failed - "+ModRole2+" should be not be selected but it is.";
				}
			} else {
				ModResultRole2="Pass";
			}
			System.out.println("ModResultRole2 ="+ModResultRole2);		

			if(ModifyRoleDB3!=0){
				ModRole_Val3=ua.Role_Value(ModRole3); //Get the value of the Role check box to verify the data was saved correctly
				if(ModRole_Val3==0){
					ModResultRole3="Pass";
				} else {
					ModResultRole3="Failed - "+ModRole3+" should be not be selected but it is.";
				}
			} else {
				ModResultRole3="Pass";
			}
			System.out.println("ModResultRole3 ="+ModResultRole3);	
		} else {
			ModResultRole1="Pass";
			//ModResultRole2="Pass";
			//ModResultRole3="Pass";
		}
	

//		if(ResultRole1.equalsIgnoreCase("Pass")&& ResultRole2.equalsIgnoreCase("Pass")&& ResultRole3.equalsIgnoreCase("Pass")&& ModResultRole1.equalsIgnoreCase("Pass")&& ModResultRole2.equalsIgnoreCase("Pass")&& ModResultRole3.equalsIgnoreCase("Pass")){
		if(ResultRole1.equalsIgnoreCase("Pass")&& ModResultRole1.equalsIgnoreCase("Pass")){
			RoleSaved="Pass"; //All roles are set correctly
		} else{
			RoleSaved="Failed - The role were not saved correctly."; 
		}
		System.out.println("RoleSaved ="+RoleSaved);	**/


		Facility_Val1=ua.Facility_Value(Facility1Entered); //Get the value of the Facility check box to verify the data was saved correctly.
		if(Facility_Val1==1){
			ResultFacility1="Pass";
		} else {
			ResultFacility1="#Failed!# - "+Facility1Entered+" should be selected but it is not. ";
		}
		System.out.println("ResultFacility1 ="+ResultFacility1);	
		Result+="\r\n\tResultFacility1 ="+ResultFacility1;

		if(Facility2DBID!=0){
			Facility_Val2=ua.Facility_Value(Facility2Entered); //Get the value of the Facility check box to verify the data was saved correctly.
			if(Facility_Val2==1){
				ResultFacility2="Pass";
			} else {
				ResultFacility2="#Failed!# - "+Facility2Entered+" should be selected but it is not. ";
			}
		} else {
			ResultFacility2="Pass";
		}
		System.out.println("ResultFacility2 ="+ResultFacility2);	
		Result+="\r\n\tResultFacility2 ="+ResultFacility2;
		if(Facility3DBID!=0){
			Facility_Val3=ua.Facility_Value(Facility3Entered); //Get the value of the Facility check box to verify the data was saved correctly.
			if(Facility_Val3==1){
				ResultFacility3="Pass";
			} else {
				ResultFacility3="#Failed!# - "+Facility3Entered+" should be selected but it is not. ";
			}
		} else {
			ResultFacility3="Pass";
		}
		System.out.println("ResultFacility3 ="+ResultFacility3);	
		Result+="\r\n\tResultFacility3 ="+ResultFacility3;
		if(Facility!="Same"){
			if(ModifyFacilityDB1!=0){
				ModFacility_Val1=ua.Facility_Value(ModFacility1); //Get the value of the Facility check box to verify the data was saved correctly.
				if(ModFacility_Val1==0){
					ModResultFacility1="Pass";
				} else {
					ModResultFacility1="#Failed!# - "+ModFacility1+" should not be selected but it is .";
				}
			} else {
				ModResultFacility1="Pass";
			}
			System.out.println("ModResultFacility1 ="+ModResultFacility1);		
			Result+="\r\n\tModResultFacility1 ="+ModResultFacility1;
			if(ModifyFacilityDB2!=0){
				ModFacility_Val2=ua.Facility_Value(ModFacility2); //Get the value of the Facility check box to verify the data was saved correctly.
				if(ModFacility_Val2==0){
					ModResultFacility2="Pass";
				} else {
					ModResultFacility2="#Failed!# - "+ModFacility2+" should not be selected but it is .";
				}
			} else {
				ModResultFacility2="Pass";
			}
			System.out.println("ModResultFacility2 ="+ModResultFacility2);	
			Result+="\r\n\tModResultFacility2 ="+ModResultFacility2;
			if(ModifyFacilityDB3!=0){
				ModFacility_Val3=ua.Facility_Value(ModFacility3); //Get the value of the Facility check box to verify the data was saved correctly.
				if(ModFacility_Val3==0){
					ModResultFacility3="Pass";
				} else {
					ModResultFacility3="#Failed!# - "+ModFacility3+" should not be selected but it is .";
				}
			} else {
				ModResultFacility3="Pass";
			}
			System.out.println("ModResultFacility3 ="+ModResultFacility3);		
			Result+="\r\n\tModResultFacility3 ="+ModResultFacility3;
		} else {
			ModResultFacility1="Pass";
			ModResultFacility2="Pass";
			ModResultFacility3="Pass";
		}
	
		
		if(ResultFacility1.equalsIgnoreCase("Pass")&& ResultFacility2.equalsIgnoreCase("Pass")&& ResultFacility3.equalsIgnoreCase("Pass")&& ModResultFacility1.equalsIgnoreCase("Pass")&& ModResultFacility2.equalsIgnoreCase("Pass")&& ModResultFacility3.equalsIgnoreCase("Pass")){
			FacilitySaved="Pass"; //All facilities are set correctly
		} else{
			FacilitySaved="#Failed!# - The Facilities were not saved correctly."; //FacilitySaved
		}
		System.out.println("FacilitySaved ="+FacilitySaved);
		Result+="\r\n\tFacilitySaved ="+FacilitySaved;
		
		ua.Cancel_User_Edit();
		System.out.println("Cancel Edit");

		if(!GridID.equals(null) && ResultDef.equalsIgnoreCase("Pass")&& ResultStaff.equalsIgnoreCase("Pass")&& ResultRole1.equalsIgnoreCase("Pass")&& FacilitySaved.equalsIgnoreCase("Pass")){ //If the GridID is Null and current Scope Status equals expected Scope Status then the save failed. If GridID is not null the save passed. 
			Result+="\r\n\tPass - The data was saved correctly";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				//stmt="Update users SET UserName='"+UserNameEntered+"', FacilityID1="+Facility1DBID+", FacilityID2="+Facility2DBID+", FacilityID3="+Facility3DBID+", DefaultFacID="+DefaultFacDBID+", roleID1="+Role1DBID+", roleID2="+Role2DBID+", roleID3="+Role3DBID+", staffID="+StaffDBID+", UpdateDate=CURRENT_TIMESTAMP WHERE idUsers="+ModifyUserDB;
				stmt="Update users SET FacilityID1="+Facility1DBID+", FacilityID2="+Facility2DBID+", FacilityID3="+Facility3DBID+", DefaultFacID="+DefaultFacDBID+", roleID1="+Role1DBID+", staffID="+StaffDBID+", UpdateDate=CURRENT_TIMESTAMP WHERE idUsers="+ModifyUserDB;
				update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
				update.close();
				System.out.println(stmt);
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
			Result+="\r\n\t#Failed!# - The data was saved incorrectly";
		}
		
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_UserSaveErr() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println(Vertex);
		if(Facility.equalsIgnoreCase("")){
			ErrCode="6";
		} 
		else if(DefFacility.equalsIgnoreCase("")||Role.equalsIgnoreCase("")) {//NM 14oct2016 when role is null error code changed to 5 was 6
			ErrCode="5";
		} else{
			ErrCode="5";
		}
		System.out.println("ErrCode="+ErrCode);
		Result=SE_Gen.Verify_ErrCode(ErrCode);
		Expected= "The user details are not saved and an error message is displayed.";
		Description="Expected Error Code="+ErrCode;
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

}
