package AdminUserStories.Scanner;


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

public class ScannerDetail_GWAPI extends ExecutionContext{
	

	public LandingPage_Verification SE_LV; //shortcut to link to the UnifiaAdminLandingPage LandingPage_Verification java class.
	public GeneralFunc gf; //shortcut for the General Function Selenium file 
	public TestFrameWork.UnifiaAdminScannerPage.Scanner_Actions sa; //shortcut for the Scanner Actions Selenium file
	public TestFrameWork.UnifiaAdminScannerPage.Scanner_Verification sv; //shortcut for the Scanner Verification Selenium file
	//Implements the user act descriptions for each edge
	public String Description;  //the description written to the test step log or printed to the console
	public String Expected; //The expected result written to the test step log
	public String Vertex; //the current vertex
	public String Result; // the result of a verification point (either pass or fail) written to the test step log
	public String Result2; //used to verify the data was saved successfully 
	
	public Connection conn= null;
	public String Path; //Variable for the Path (Valid,Same, Exits, Null)
	public String ScanName; //Scanner name variable from the graph 
	public ResultSet ScanName_RS;  //result set used to get a scanner name from the Test DB
	public String ScanName_Act; //Actual scanner name
	public String ScanID;  //ScannerID variable from the graph
	public ResultSet ScanID_RS; //result set used to get a scanner ID from the Test DB
	public String ScanID_Act; //Actual scanner id entered into Unifia
	public String Loc;  //Scanner location variable from the graph
	public ResultSet Loc_RS; //result set used to get a location from the Test DB
	public String Loc_Act; //Location actual entered into Unifia 
	public int Loc_DB_ID; // the location dbID from the test DB
	public int Scanner_DB_ID; // the scanner dbID from the test DB
	public int Facility_DB_ID; // the facility dbID from the test DB
	public String Facility; //the facility abbreviation from the test DB associated with the location being selected.
	public String ScanAct;  //Scanner active/inactive taken from the graph
	public String ModScanAct_Val; //the current value of the scanner's Active/Inactive checkbox - 
	
	public int ModScanner_DB_ID; // the scanner dbID from the test DB
	public int ModFacility_DB_ID; // the facility dbID from the test DB
	public String ModScanID_Act; //Actual scanner id entered into Unifia
	public String ModScanName_Act; //Actual scanner name
	public String ModLoc_Act; //Location actual entered into Unifia 
	public String ModFacility; //the facility abbreviation from the test DB associated with the location being selected.

	public String stmt; //used for SQL queiers
	public String GridID; //the GridID of the row being modified in Unifia
	public String ErrorCode; //The error code given when a save fails. 
		
	public long cal = Calendar.getInstance().getTimeInMillis(); 
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	
	public static String actualResult="\t\t\t ScannerDetail_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="ScannerDetail_TestSummary_";
	public boolean startflag=false;
	public TestFrameWork.TestHelper TH;
	public int Scenario=1;
	public boolean ScenarioStartflag=true;
	
	//implements the edges for the Scanner Graphml
	
	public void e_Start(){
		//empty edge for graphml navigation
	}

	public void e_Modify()throws  InterruptedException{
		ScenarioStartflag=true;
		Path="Modify";
		System.out.println("Path="+Path);  
			try{ 
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				stmt="select s.ScanDB_id, s.actual_ScanID, s.actual_ScanName, l.EnteredLocationName, l.Facility from scanner s join location l on l.idLocation=s.actual_LocID where s.TestKeyword='Existing' and s.UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='Existing')"; //put sql statement here to find ID
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				ScanID_RS = statement.executeQuery(stmt);
				while(ScanID_RS.next()){
										
					//Scanner_DB_ID
					ModScanner_DB_ID = ScanID_RS.getInt(1); //the first variable in the set is the ID row in the database.
					ModScanID_Act = ScanID_RS.getString(2);
					ModScanName_Act = ScanID_RS.getString(3);
					ModLoc_Act = ScanID_RS.getString(4);
					ModFacility_DB_ID = ScanID_RS.getInt(5); //the first variable in the set is the ID row in the database.
				}		
					ScanID_RS.close();
					System.out.println("ModScanner_DB_ID = "+ModScanner_DB_ID);
					System.out.println("ModScanID_Act in e_Modify function = "+ModScanID_Act);
					System.out.println("ModScanName_Act in e_Modify function= "+ModScanName_Act);
					System.out.println("ModLoc_Act in e_Modify function = "+ModLoc_Act);
					System.out.println("ModFacility_DB_ID = "+ModFacility_DB_ID);

					stmt="select Abbreviation from facility where idFacility='"+ModFacility_DB_ID+"'";
					ScanID_RS = statement.executeQuery(stmt);
					while(ScanID_RS.next()){
						ModFacility=ScanID_RS.getString(1);
					}
					ModLoc_Act=ModLoc_Act+" ("+ModFacility+")";
					System.out.println("ModLoc_Act in e_Modify function = "+ModLoc_Act);

					update.execute("Update scanner set UpdateDate=CURRENT_TIMESTAMP WHERE ScanDB_id="+ModScanner_DB_ID+";");
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
			
			sa.ClearScannerSrchCritera();
			//System.out.println("The ScanId has been entered in the search");
			sa.Search_ScanID(ModScanID_Act);
			GridID=sa.GetGridID_Scanner_To_Modify(ModScanID_Act);
			System.out.println("GridID in e_Path function is: "+GridID);
			ModScanAct_Val=sa.Scanner_Active_Value(ModScanID_Act);
			sa.Select_Scanner_To_Modify(ModScanID_Act);
			System.out.println("Selecting to modify: "+ModScanID_Act);
			//sa.Search_ScanID(ScanID_Act);
			Description="Selecting to modify: "+ModScanID_Act;
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_New() throws InterruptedException{
		ScenarioStartflag=true;
		Path="New";
		sa.Add_New_Scanner();
		System.out.println("Path="+Path);  
		Path="New";
		Description="Adding a new scanner";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	/**public void e_ScanID()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ScanID= String.valueOf(getMbt().getDataValue("ScanID"));
		//GridID=sa.GetGridID_Scanner_To_Modify(ScanID_Act);
		System.out.println("The ScanID in e_ScanID function = "+ScanID);
		
		if (ScanID.equalsIgnoreCase("Valid")){
		
			calint++;
			calchk=String.valueOf(calint);
			if(calchk.equalsIgnoreCase(1000)){
				calint=0;
				calchk="0";
			}
			System.out.println("Name Timestamp:  "+cal+calchk);
			ScanID_Act=cal+calchk;
		} else if (ScanID.equalsIgnoreCase("Existing")){
			try{ 
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				stmt="select s.ScanDB_id, s.actual_ScanID from scanner s  where s.TestKeyword='Existing' and s.UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='Existing')"; //put sql statement here to find ID
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				ScanID_RS = statement.executeQuery(stmt);
				
				while(ScanID_RS.next()){
					DB_ID = ScanID_RS.getInt(1); //the first variable in the set is the ID row in the database.
					System.out.println("DB_ID = "+DB_ID);
					ScanID_Act = ScanID_RS.getString(2);
					System.out.println("ScanID_Act e_ScanID function = "+ScanID_Act);
					
				} ScanID_RS.close();
				update.execute("Update scanner set UpdateDate=CURRENT_TIMESTAMP WHERE ScanDB_id="+DB_ID+";");
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
			
		} else if (ScanID.equalsIgnoreCase("Same")){
			ScanID_Act=ScanID_Act;
		} else {
			ScanID_Act=ScanID;
		}
	
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanID_New(ScanID_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanID(GridID, ScanID_Act);
		}else {
			sa.Enter_ScanID_New(ScanID_Act);
		}
		Description = "The user enters "+ScanID_Act+" in the scanner ID field.";
		System.out.println(Description);
	}**/
	

	
	public void e_ScanIDValid()throws  InterruptedException{
		//System.out.println(getCurrentElement().getName());
		ScanID="Valid";
		//GridID=sa.GetGridID_Scanner_To_Modify(ScanID_Act);
		System.out.println("The ScanID in e_ScanIDValid function = "+ScanID);
				
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		ScanID_Act="ScanID"+cal+calchk;
		System.out.println("ScanID_Act="+ScanID_Act);
	
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanID_New(ScanID_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanID(GridID, ScanID_Act);
		}else {
			sa.Enter_ScanID_New(ScanID_Act);
		}
		Description = "The user enters "+ScanID_Act+" in the scanner ID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanIDExisting()throws  InterruptedException{
		ScanID="Existing";
		//GridID=sa.GetGridID_Scanner_To_Modify(ScanID_Act);
		System.out.println("The ScanID in e_ScanIDExisting function = "+ScanID);

		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")) {
				stmt="select s.ScanDB_id, s.actual_ScanID from scanner s  where s.TestKeyword='Existing' and s.UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='Existing')"; //put sql statement here to find ID
			} else if (Path.equalsIgnoreCase("Modify")){
				stmt="select s.ScanDB_id, s.actual_ScanID from scanner s  where s.TestKeyword='Existing' and s.actual_ScanID!='"+ModScanID_Act+"' and s.UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='Existing' and actual_ScanID!='"+ModScanID_Act+"')"; //put sql statement here to find ID
			}
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			ScanID_RS = statement.executeQuery(stmt);
			
			while(ScanID_RS.next()){
				Scanner_DB_ID = ScanID_RS.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("Scanner_DB_ID = "+Scanner_DB_ID);
				ScanID_Act = ScanID_RS.getString(2);
				System.out.println("ScanID_Act e_ScanID function = "+ScanID_Act);
		
			} ScanID_RS.close();
			update.execute("Update scanner set UpdateDate=CURRENT_TIMESTAMP WHERE ScanDB_id="+Scanner_DB_ID+";");
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
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanID_New(ScanID_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanID(GridID, ScanID_Act);
		}
		
		Description = "The user enters "+ScanID_Act+" in the scanner ID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanIDSame()throws  InterruptedException{
		ScanID= "Same";
		//GridID=sa.GetGridID_Scanner_To_Modify(ScanID_Act);
		System.out.println("The ScanID in e_ScanIDSame function = "+ScanID);
		ScanID_Act=ModScanID_Act;

		/**if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanID_New(ScanID_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanID(GridID, ScanID_Act);
		}**/
		Description="The user does nothing. Uses the same Scanner ID value which is "+ScanID_Act;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanIDNull()throws  InterruptedException{
		ScanID="";
		//GridID=sa.GetGridID_Scanner_To_Modify(ScanID_Act);
		System.out.println("The ScanID in e_ScanIDNull function = "+ScanID);
		ScanID_Act=ScanID;
	
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanID_New(ScanID_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanID(GridID, ScanID_Act);
		}else {
			sa.Enter_ScanID_New(ScanID_Act);
		}
		Description = "The user enters "+ScanID_Act+" in the scanner ID field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	
/**	public void e_ScanName(){
		System.out.println(getCurrentElement().getName());
		ScanName= String.valueOf(getMbt().getDataValue("ScanName"));
		
		System.out.println("ScanName= "+ScanName);
		if (ScanName.equalsIgnoreCase("Valid")){
			calint++;
			calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
				calint=0;
				calchk="0";
			}
			System.out.println("Name Timestamp in e_ScanName function:  "+cal+calchk);
			ScanName_Act="Test"+cal+calchk;
		} else if (ScanName.equalsIgnoreCase("Existing")){
			try{ 
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				stmt="select s.ScanDB_id, s.actual_ScanName from scanner s  where s.TestKeyword='Existing' and s.UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='Existing')"; //put sql statement here to find ID
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				ScanName_RS = statement.executeQuery(stmt);
				
				while(ScanName_RS.next()){
					DB_ID = ScanName_RS.getInt(1); //the first variable in the set is the ID row in the database.
					System.out.println("DB_ID in e_ScanName function = "+DB_ID);
					ScanName_Act = ScanName_RS.getString(2);
					System.out.println("ScanName_Act in e_ScanName function = "+ScanName_Act);
				} ScanName_RS.close();
				update.execute("Update scanner set UpdateDate=CURRENT_TIMESTAMP WHERE ScanDB_id="+DB_ID+";");
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
			
		} else if (ScanName.equalsIgnoreCase("Same")){
			ScanName_Act=ScanName_Act;
		} else {
		ScanName_Act=ScanName;
		}
		
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanName_New(ScanName_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanName(GridID,ScanName_Act);
		}else {
			sa.Enter_ScanName_New(ScanName_Act);
		}
		
		Description = "The user enters "+ScanName_Act+" in the scanner name field.";
	}**/
	
	public void e_ScanNameSame(){
		//ScanName= String.valueOf(getMbt().getDataValue("ScanName"));
		ScanName="Same";
		System.out.println("e_ScanNameSame");

		ScanName_Act=ModScanName_Act;
		//no action taken leave the ScanName the same.
				
		Description = "The user enters "+ScanName_Act+" in the scanner name field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanNameExisting()throws  InterruptedException{
		//ScanName= String.valueOf(getMbt().getDataValue("ScanName"));
		ScanName="Existing";
		System.out.println("ScanName= "+ScanName);
			try{ 
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				if(Path.equalsIgnoreCase("New")) {
					stmt="select s.ScanDB_id, s.actual_ScanName from scanner s  where s.TestKeyword='Existing' and s.UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='Existing')"; //put sql statement here to find ID
				} else if (Path.equalsIgnoreCase("Modify")){
					stmt="select s.ScanDB_id, s.actual_ScanName from scanner s  where s.TestKeyword='Existing' and s.actual_ScanName!='"+ModScanName_Act+"'and s.UpdateDate=(Select Min(UpdateDate) from scanner Where TestKeyword='Existing'and actual_ScanName!='"+ModScanName_Act+"')"; //put sql statement here to find ID
				}
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				ScanName_RS = statement.executeQuery(stmt);
				
				while(ScanName_RS.next()){
					Scanner_DB_ID = ScanName_RS.getInt(1); //the first variable in the set is the ID row in the database.
					System.out.println("Scanner_DB_ID in e_ScanName function = "+Scanner_DB_ID);
					ScanName_Act = ScanName_RS.getString(2);
					System.out.println("ScanName_Act in e_ScanName function = "+ScanName_Act);
				} ScanName_RS.close();
				update.execute("Update scanner set UpdateDate=CURRENT_TIMESTAMP WHERE ScanDB_id="+Scanner_DB_ID+";");
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
					
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanName_New(ScanName_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanName(GridID,ScanName_Act);
		}else {
			sa.Enter_ScanName_New(ScanName_Act);
		}
		
		Description ="The user enters "+ScanName_Act+" in the scanner name field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanNameValid()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		//ScanName= String.valueOf(getMbt().getDataValue("ScanName"));
		ScanName="Valid";
		//System.out.println("ScanName= "+ScanName);
			calint++;
			calchk=String.valueOf(calint);
			if(calchk.equals(1000)){
				calint=0;
				calchk="0";
			}
			//System.out.println("Name Timestamp in e_ScanName function:  "+cal+calchk);
			ScanName_Act="Test"+cal+calchk;
			System.out.println("e_ScanNameValid ScanName_Act="+ScanName_Act);
		
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanName_New(ScanName_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanName(GridID,ScanName_Act);
		}else {
			sa.Enter_ScanName_New(ScanName_Act);
		}
		
		Description="The user enters "+ScanName_Act+" in the scanner name field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanNameNull()throws  InterruptedException{
		ScanName="";
		
		System.out.println("e_ScanNameNull: ScanName= "+ScanName);

		ScanName_Act=ScanName;
		
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_ScanName_New(ScanName_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_ScanName(GridID,ScanName_Act);
		}else {
			sa.Enter_ScanName_New(ScanName_Act);
		}
		
		Description = "The user enters "+ScanName_Act+" in the scanner name field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanLocActive() throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		Loc="Active";
		//System.out.println(Loc);//wei

			stmt= "select l.idLocation, l.EnteredLocationName, f.Abbreviation from location l join facility f on l.Facility=f.idFacility where l.Active='True' and l.TestKeyword='Scanner' AND l.UpdateDate=(Select MIN(l.UpdateDate) from location l Where l.Active='True' and l.TestKeyword ='Scanner')";
			System.out.println(stmt);
			try{ 
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Loc_RS = statement.executeQuery(stmt);
				while(Loc_RS.next()){
					Loc_DB_ID = Loc_RS.getInt(1); //the first variable in the set is the ID row in the database.
					Loc_Act = Loc_RS.getString(2);
					Facility = Loc_RS.getString(3);
				}
				Loc_Act=Loc_Act+" ("+Facility+")";
				System.out.println("Loc_DB_ID in e_ScanLocActive function = "+Loc_DB_ID);
				System.out.println("Loc_Act in e_ScanLocActive function = "+Loc_Act);

				Loc_RS.close();
				stmt="Update location set UpdateDate=CURRENT_TIMESTAMP WHERE idLocation="+Loc_DB_ID;
				update.execute(stmt);
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
	
	
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_Location_New(Loc_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_Location(GridID,Loc_Act);
		}
		
		Description="The user selects "+Loc_Act+" in scanner location field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanLocNull() throws  InterruptedException{
		Loc="";
		//System.out.println(Loc);//wei

		Loc_Act = Loc;
	
		if(Path.equalsIgnoreCase("New")) {
			sa.Enter_Location_New(Loc_Act);
		}else if (Path.equalsIgnoreCase("Modify")){
			sa.Modify_Location(GridID,Loc_Act);
		}
		
		Description="The user selects "+Loc_Act+" in scanner location field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanActTrue() {
		ScanAct="True";
		Description="The user set the scanner active field as:  "+ScanAct;
		System.out.println(Description); 
		
		if(Path.equalsIgnoreCase("New")) {
			sa.Select_New_Scanner_Active(ScanAct);
		}else if(Path.equalsIgnoreCase("Modify")){
			sa.Modify_Scanner_IsActive(GridID, ModScanAct_Val, ScanAct);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ScanActFalse() {
		ScanAct="False";
		Description="The user set the scanner active field as:  "+ScanAct;
		System.out.println(Description); 
		
		if(Path.equalsIgnoreCase("New")) {
			sa.Select_New_Scanner_Active(ScanAct);
		}else if(Path.equalsIgnoreCase("Modify")){
			sa.Modify_Scanner_IsActive(GridID, ModScanAct_Val, ScanAct);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	public void e_ScannerSave () throws InterruptedException{
		sa.Save_Scanner_Edit();
		//System.out.println(getCurrentElement().getName());
		Description="The user clicks the save button.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Cancel() throws InterruptedException{
		sa.Cancel_Scanner_Edit();
		System.out.println("e_Cancel - clicked Cancel button");
		Description="clicked Cancel button";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	//implements the vertices for the Scanner Graphml
	
	public void v_ScannerDetail(){
		if(ScenarioStartflag==true){
			if(startflag==false){
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				ForFileName = dateFormat.format(date); 
				startflag=true;
			}
			Result=SE_LV.Verify_Admin_Function("Scanner");
			System.out.println(getCurrentElement().getName());
			//Empty vertex, will eventually have automation details to verify the scanner details page is displayed
			Description="\r\n=====================================";
			Description+="\r\nStart of new Scenario - "+Scenario;
			actualResult=actualResult+"\r\n"+Description+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Scenario++;
			ScenarioStartflag=false;
		}
	}
	
	public void v_ScannerData(){
		//System.out.println(getCurrentElement().getName());
		//Empty vertex, will eventually have automation details to verify the scanner details page is displayed
		Description="selected path : "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
		
	public void v_ScannerName(){
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());	
		Description= ScanName_Act+" is displayed in the scanner name field.";		
		if(Path.equalsIgnoreCase("New")) {			
			Result=sv.Verify_NewScanName(ScanName_Act);
		}else if(Path.equalsIgnoreCase("Modify")){			
			Result=sv.Verify_ModifyScanName(GridID, ScanName_Act);
		}
		System.out.println(Description);	
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ScannerID(){
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());		
		Description= ScanID_Act +" is displayed in the scanner ID field.";
				
		if(Path.equalsIgnoreCase("New")) {			
			Result=sv.Verify_NewScanID(ScanID_Act);
		}else if(Path.equalsIgnoreCase("Modify")){			
			Result=sv.Verify_ModifyScanID(GridID, ScanID_Act);
		}
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ScannerLocation(){
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		Description= Loc_Act+" is displayed in the scanner location field.";
				
		if(Path.equalsIgnoreCase("New")) {	
			Result=sv.Verify_New_ScannerLocation(Loc_Act);
		} else if (Path.equalsIgnoreCase("Modify")){			
			Result=sv.Verify_ModifyScanLoc(GridID, Loc_Act);
		}
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ScannerActive(){
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());		
		Description= "The scanner active field is set to :"+ScanAct;
		Result="Pass";
		System.out.println(Description);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	public void v_ScannerSave() throws InterruptedException{
		Vertex= getCurrentElement().getName();

		sa.Search_ScanID(ScanID_Act);	//Search for the ScanID that was just saved. 	
		System.out.println("Searched for Scanner in v_ScannerSave ="+ScanID_Act);
		GridID=sa.GetGridID_Scanner_To_Modify(ScanID_Act);  //If the GridID is available, then the item is found and the ScanID was saved correctly.
		System.out.println("Grid ID="+GridID);
		ModScanAct_Val=sa.Scanner_Active_Value(ScanID_Act); //Get the Active checkbox value to verify it is set correctly.
		System.out.println("ModScanAct_Val="+ModScanAct_Val);
		sa.Select_Scanner_To_Modify(ScanID_Act); //Select the ID to verify the values are saved correctly.
		System.out.println("Selected Scanner ="+ScanID_Act);
		Result=sv.Verify_ModifyScanName(GridID, ScanName_Act); //make sure the scanner name is set correctly. 
		System.out.println("Result="+Result);
		Result2=sv.Verify_ModifyScanLoc(GridID, Loc_Act); //make sure the location name is set correctly. 
		System.out.println("Result2="+Result2);
		sa.Cancel_Scanner_Edit(); //cancel the edit without making any changes. 
		System.out.println("Cancel Edit");
		
		if(!GridID.equalsIgnoreCase(null) && Result.equalsIgnoreCase("Pass") && ModScanAct_Val.equalsIgnoreCase(ScanAct) && Result2.equalsIgnoreCase("Pass") ){	
			//If the GridID is found and Result and Result2 are Pass and the active value matches the expected Active value, the item was saved correctly. so update the Test DB. 
			Result="Pass";
				try{ 
					conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
					Statement statement = conn.createStatement();
					Statement update= conn.createStatement();
					Statement insert= conn.createStatement();

					if(Path.equalsIgnoreCase("New")){
						//if the path was new, insert a new row into the Test DB for scanner for the newly created scanner
						stmt="Insert into scanner(actual_ScanID,actual_ScanName,actual_LocID,actual_ScanActive,TestKeyword) values('"+ScanID_Act+"','"+ScanName_Act+"',"+Loc_DB_ID+", '"+ScanAct+"', 'Existing')";
						System.out.println(stmt);
						insert.execute(stmt); 
						insert.close();
					} else if(Path.equalsIgnoreCase("Modify")){	
						//if the path was modify, update the TestDB row that was being modified with the updated values and current time stamp.
						stmt="Update scanner SET actual_ScanID='"+ScanID_Act+"',actual_ScanName='"+ScanName_Act+"',actual_LocID="+Loc_DB_ID+",actual_ScanActive='"+ScanAct+"', UpdateDate=CURRENT_TIMESTAMP where ScanDB_id='"+ModScanner_DB_ID+"'";
						System.out.println(stmt);
						update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
				Expected= "Results saved withotu error";
				Description="Results saved withotu error";
			} else {
				//else the save was not successful and the Result will be fail in the test log and the Test DB will not be updated.
				System.out.println("Save failed");
				Result="#Failed!#";
				Expected= "#Failed!#: Results did not save an error has occured";
				Description="#Failed!#: Results were supposed to have saved, but did not save. an error has occured";

			}	
		System.out.println("Vertex="+Vertex+"; Expected="+Expected);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	

	

	public void v_ScanSaveErr() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		
		if(ScanID.equalsIgnoreCase("")|| Loc_Act.equalsIgnoreCase("") || ScanName_Act.equalsIgnoreCase("") ) { 
			//If ScanID, Location or Scan Name are empty, the save will fail and give error code 5 indicating the field is required. 
			ErrorCode="5";
		} else if(ScanID.equalsIgnoreCase("Existing")){
			//Scanner ID must be unique. If the Scanner ID already exists in the database the save will fail indicating error code 4 and the field must be unique.
			ErrorCode="4";	
		}else {
			ErrorCode="5";	
		}
		Result=gf.Verify_ErrCode(ErrorCode); //Make sure the error code given is correct. 
		Description="Scope type is Not saved due to ErrorCode("+ErrorCode+").";
		Expected="Scope type is NOT saved and an error message is displayed. Result="+Result;
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		System.out.println(Expected);
		System.out.println(Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
		
	public void v_ScanVerf(){
		//System.out.println(getCurrentElement().getName());
		
		//Empty vertex for logical verification navigation
	}
}
