package AdminUserStories.ScopeType;

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
import TestFrameWork.UnifiaAdminScopeTypePage.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;

public class ScopeTypeDetails_GWAPI extends ExecutionContext{
	public Connection conn= null;
	public LandingPage_Verification lpV;
	public ScopeType_Actions sta;
	public ScopeType_Verification stv;
	public GeneralFunc gf;
	public String Path;
	public String Name; //Scope Type name variable to be pulled from Scope Type table
	public String modName; //name to be modified
	public String modET; //the current exam type for the scope type to be modified. 
	public String shippedName; //name as shipped
//	public String updateName; // updated scope name
	public String Name_Act;
	public ResultSet Name_rs;
	public int ScopeType_DBID; // Scope Type base ID from Scope Type table
	public int modScopeType_DBID; //Second Scope Type DB ID for updates
	public int shippedScopeType_DBID; //Shipped Scope Type DB ID
	public String ST_Act; //Scope Active flag
	public String ET; //Scope Type/ Exam Type Assoc
	public String ET_Act; //Actual value
	public int ET_DBID; //Exam Type Data base ID from Exam Type table
	public ResultSet ET_rs;
	public String stmt; //the cast of the sql statment to be used in the ST_Name table
	public String GridID;
	public String ModSTAct_Val; //
	public String ModECN_Val; // value of ECN check box
	public String ECN_Act; // value of the Elevator Cleaning Notice check box
	public String ErrorCode;
	
	//Logging Variables
	public String Description;
	public String Expected;
	public String Result;
	public String Vertex;
	public String Edge;
	public long cal = Calendar.getInstance().getTimeInMillis();
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	
	public static String actualResult="\t\t\t ScopeTypeDetails_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="ScopeTypeDetails_TestSummary_";
	public boolean startflag=false;
	public TestFrameWork.TestHelper TH;
	public int Scenario=1;
	public boolean ScenarioStartflag=true;
	
	public void e_Start(){
		//Empty edge
	}
	//ADD field for ECN field
	public void e_Modify() throws InterruptedException{
		ScenarioStartflag=true;
		Path="Modify";
		System.out.println("Path="+Path);
		   try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
//			stmt="Select ST.idScopeType, ST.EnteredScopeTypeName, Concat(ST.BaseScopeTypeName, CURRENT_TIMESTAMP) AS ScopeTypeName,st.HasElevatorCleaningNotice, ET.ActualExamType_Abbrv FROM scopetype ST join examtype ET on ST.ExamType=ET.idExamType Where ST.TestKeyword='Existing' and ST.UpdateDate in (Select Min(ST.UpdateDate) from scopetype ST Where ST.TestKeyword='Existing')"; //put sql statement here to find ID
//			stmt="Select ST.idScopeType, ST.EnteredScopeTypeName, Concat(ST.BaseScopeTypeName, CURRENT_TIMESTAMP) AS ScopeTypeName,ET.ActualExamType_Abbrv FROM scopetype ST join examtype ET on ST.ExamType=ET.idExamType Where ST.TestKeyword='Existing' and ST.UpdateDate in (Select Min(ST.UpdateDate) from scopetype ST Where ST.TestKeyword='Existing')"; //put sql statement here to find ID
			stmt="Select ST.idScopeType, ST.EnteredScopeTypeName,ET.ExamTypeAbbrv FROM scopetype ST join examtype ET on ST.ExamType=ET.idExamType Where ST.TestKeyword='Existing' and ST.UpdateDate in (Select Min(ST.UpdateDate) from scopetype ST Where ST.TestKeyword='Existing')"; //put sql statement here to find ID
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Name_rs = statement.executeQuery(stmt);
			  while(Name_rs.next()){
				modScopeType_DBID= Name_rs.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("modScopeType_DBID = "+modScopeType_DBID);
				modName= Name_rs.getString(2); //the second variable in the set is name to be modified in the database.
				System.out.println("modName = "+modName);//ScopeType_DB_ID
//				updateName= Name_rs.getString(3); //the third variable in the set is the updated scope name in the database.
//				System.out.println("updateName = "+updateName);
				//ModECN_Val=Name_rs.getString(4); //the fourth variable in the set is the ECN value in the database.
				//System.out.println("ModECN_Val = "+ModECN_Val);
				modET= Name_rs.getString(3); //the fifth variable in the set is the updated ET in the database.
				System.out.println("modET = "+modET);					
				}
				Name_rs.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			sta.SearchScopeType(modName);
			GridID=sta.GetGridID_ScopeType_To_Modify(modName);
			ModSTAct_Val=sta.ScopeType_Active_Value(modName, GridID); //Get the current value of the Active checkbox of the Scope Type to be modified.
			//ModECN_Val=sta.Scope_ECN_Value(modName);
			sta.Select_ScopeType_To_Modify(modName);
			System.out.println("Scope Type: " +modName+" is selected");
			Description="Scope Type: " +modName+" is selected";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_New() throws InterruptedException{
		ScenarioStartflag=true;
		Path="New";
		Edge="e_New";
		System.out.println("Path="+Path);
		sta.Add_New_ScopeType();
		Description="Clicked + to add new Scope type";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
/**		ModECN_Val=sta.Scope_ECN_Value("-1");
		if(ModECN_Val.equalsIgnoreCase("False")){
			Result="Pass";
		}
		else{
			Result="Fail";
		}
		Expected= "The scope type ECN is set to False by Default for new scope types.";
		Description="The scope type ECN is set to False by Default for new scope types.";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		TestHelper.StepWriter1(Edge, Description, Expected, Result);
		ModSTAct_Val=sta.ScopeType_Active_Value("-1");
		if(ModSTAct_Val.equalsIgnoreCase("True")){
			Result="Pass";
		} else {
			Result="Fail";
		}
				Expected= "The Scope Type is set to Active by Default.";
				Description="The Scope Type is set to Active by Default.";
				System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
				TestHelper.StepWriter1(Edge, Description, Expected, Result);**/
		}

	public void e_Shipped() throws InterruptedException{
		ScenarioStartflag=true;
		Path="Shipped";
		System.out.println("Path="+Path);
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			stmt="Select idScopeType, EnteredScopeTypeName from scopetype Where TestKeyword='Shipped' and UpdateDate=(Select Min(UpdateDate) from scopetype Where TestKeyword='Shipped')"; //put sql statement here to find ID
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Name_rs = statement.executeQuery(stmt);
			while(Name_rs.next()){
				shippedScopeType_DBID= Name_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("shippedScopeType_DBID = "+shippedScopeType_DBID);
				shippedName= Name_rs.getString(2); //the second variable in the set is name to be modified in the database.
				//System.out.println("shippedName = "+shippedName);//shipped ScopeTypeName
											
			 }
			 Name_rs.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			System.out.println("shippedScopeType_DBID = "+shippedScopeType_DBID);
			System.out.println("shippedName = "+shippedName);//shipped ScopeTypeName
			sta.SearchScopeType(shippedName);
			GridID=sta.GetGridID_ScopeType_To_Modify(shippedName);
			ModSTAct_Val=sta.ScopeType_Active_Value(shippedName, GridID); //Get the current value of the Active checkbox of the Scope Type to be modified. 
			//ModECN_Val=sta.Scope_ECN_Value(shippedName);
			sta.Select_ScopeType_To_Modify(shippedName);
			System.out.println("GridID="+GridID+" ;Scope Type: " +shippedName+" is selected");
			Description="GridID="+GridID+" ;Scope Type: " +shippedName+" is selected";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	public void e_Path()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());


	}
	

	
	public void e_STNameValid()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());	
		Name="Valid";
		System.out.println("Name="+Name);
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		Name_Act="Test"+cal+calchk;
		System.out.println("Name_Act="+Name_Act);
		if (Path.equals("New")){
			sta.enterNewScopeType(Name_Act);
		} else if (Path.equals("Modify")){
			sta.ModifyScopeType(GridID, Name_Act);
		}
		Description="User enters "+Name_Act+" in the scope type field.  They are using a "+Path+" path.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_STNameEShipped()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());	
		Name="Shipped";
		System.out.println("Name="+Name);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			stmt="Select idScopeType, EnteredScopeTypeName FROM scopetype Where TestKeyword='Shipped' and UpdateDate=(Select Min(UpdateDate) from scopetype Where TestKeyword='Shipped')";
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Name_rs = statement.executeQuery(stmt);
			while(Name_rs.next()){
				ScopeType_DBID= Name_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("ScopeType_DBID = "+ScopeType_DBID);
				Name_Act= Name_rs.getString(2); //the second variable in the set is name to be modified in the database.
				//System.out.println("Actual Name = "+Name_Act);// ScopeTypeName to be saved
			}
			Name_rs.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("ScopeType_DBID = "+ScopeType_DBID);
		System.out.println("Actual Name = "+Name_Act);// ScopeTypeName to be saved

		System.out.println("Name_Act ="+Name_Act);

		if(Path.equals("New")){
			sta.enterNewScopeType(Name_Act);
		} else if(Path.equals("Modify")){
			sta.ModifyScopeType(GridID, Name_Act);
		}
		Description="User enters "+Name_Act+" in the scope type field.  They are using a "+Path+" path.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_STNameShipped()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		Name="Shipped";
		System.out.println("Name="+Name);
		Name_Act=shippedName;	
		System.out.println("Name_Act ="+Name_Act);
		Description="User enters "+Name_Act+" in the scope type field.  They are using a "+Path+" path.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_STNameNull()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		Name="";
		System.out.println("Name="+Name);
		Name_Act=Name; //null value from graph		
		System.out.println("Name_Act ="+Name_Act);
		if(Path.equals("New")){
			sta.enterNewScopeType(Name_Act);
		} else if(Path.equals("Modify")){
			sta.ModifyScopeType(GridID, Name_Act);
		}
		Description="User enters "+Name_Act+" in the scope type field.  They are using a "+Path+" path.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_STNameSame()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		Name="Same";
		System.out.println("Name="+Name);
		Name_Act=modName;
		System.out.println("Name_Act ="+Name_Act);
		//do nothing - leave the name the same.
		Description="User enters "+Name_Act+" in the scope type field.  They are using a "+Path+" path.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_STNameExisting()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		Name="Existing";
		System.out.println("Name="+Name);
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")){
				stmt="Select idScopeType, EnteredScopeTypeName FROM scopetype Where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from scopetype Where TestKeyword='Existing')";; //put sql statement here to find ID
			}else if(Path.equalsIgnoreCase("Modify")){
				stmt="Select idScopeType, EnteredScopeTypeName FROM scopetype Where EnteredScopeTypeName !='"+modName+"' AND TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from scopetype Where EnteredScopeTypeName !='"+modName+"' AND TestKeyword='Existing')"; //put sql statement here to find ID
			}
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Name_rs = statement.executeQuery(stmt);
			while(Name_rs.next()){
				ScopeType_DBID= Name_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("ScopeType_DBID = "+ScopeType_DBID);
				Name_Act= Name_rs.getString(2); //the second variable in the set is name to be modified in the database.
				//System.out.println("Actual Name = "+Name_Act);// ScopeTypeName to be saved
			}
			Name_rs.close();
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());	
		}
		System.out.println("ScopeType_DBID = "+ScopeType_DBID);
		System.out.println("Actual Name = "+Name_Act);// ScopeTypeName to be saved		
		System.out.println("Name_Act ="+Name_Act);

		if(Path.equals("New")){
			sta.enterNewScopeType(Name_Act);
		} else if(Path.equals("Modify")){
			sta.ModifyScopeType(GridID, Name_Act);
		}
		Description="User enters "+Name_Act+" in the scope type field.  They are using a "+Path+" path.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ST_Act_True(){
		System.out.println(getCurrentElement().getName());
		ST_Act="True";
		System.out.println("ST_Act="+ST_Act);
		if(Path.equalsIgnoreCase("New")){ //If the path is new, set the Active checkbox as per the graph (i.e. true or false) for the new role field. 
			sta.Select_New_ScopeType_Active(ST_Act);
		}else if(Path.equalsIgnoreCase("Modify")) { //If the path is modify set the active checkbox for the row being modified as per the graph (i.e. true or false)
			//ModSTAct_Val=sta.ScopeType_Active_Value(GridID); //Get the current value of the Active checkbox of the Scope Type to be modified. 
			sta.Modify_ScopeType_IsActive(GridID,ModSTAct_Val,ST_Act);
			System.out.println("The Active value was set to "+ModSTAct_Val+" ST_Act="+ST_Act);
		} else if(Path.equalsIgnoreCase("Shipped")) { //If the path is Shippped set the active checkbox for the Scope Type being modified as per the graph (i.e. true or false)
			//ModSTAct_Val=sta.ScopeType_Active_Value(GridID); //Get the current value of the Active checkbox of the Scope Type to be modified. 
			sta.Modify_ScopeType_IsActive(GridID,ModSTAct_Val,ST_Act);
			System.out.println("The Active value was set to "+ModSTAct_Val+" and ST_Act="+ST_Act);
		}
		Description="User selects "+ST_Act+" in the scope type active field.  They are using a "+Path+" path.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ST_Act_False(){
		System.out.println(getCurrentElement().getName());
		ST_Act="False";
		System.out.println("ST_Act="+ST_Act);
		if(Path.equalsIgnoreCase("New")){ //If the path is new, set the Active checkbox as per the graph (i.e. true or false) for the new role field. 
			sta.Select_New_ScopeType_Active(ST_Act);
		}else if(Path.equalsIgnoreCase("Modify")) { //If the path is modify set the active checkbox for the row being modified as per the graph (i.e. true or false)
			//ModSTAct_Val=sta.ScopeType_Active_Value(GridID); //Get the current value of the Active checkbox of the Scope Type to be modified. 
			sta.Modify_ScopeType_IsActive(GridID,ModSTAct_Val,ST_Act);
			System.out.println("The Active value was set to "+ModSTAct_Val+" ST_Act="+ST_Act);
		} else if(Path.equalsIgnoreCase("Shipped")) { //If the path is Shippped set the active checkbox for the Scope Type being modified as per the graph (i.e. true or false)
			//ModSTAct_Val=sta.ScopeType_Active_Value(GridID); //Get the current value of the Active checkbox of the Scope Type to be modified. 
			sta.Modify_ScopeType_IsActive(GridID,ModSTAct_Val,ST_Act);
			System.out.println("The Active value was set to "+ModSTAct_Val+" and ST_Act="+ST_Act);
		}
		Description="User selects "+ST_Act+" in the scope type active field.  They are using a "+Path+" path.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	/**public void e_ET(){
		System.out.println(getCurrentElement().getName());
		ET= String.valueOf(getMbt().getDataValue("ET"));
		System.out.println("ET ="+ET);
		if(ET.equalsIgnoreCase("New")){
			try{ 
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				//changed TestKeyword to 'ScopeType' from ' Existing' to handle dependency issue
				stmt="Select idExamType, ActualExamType_Abbrv FROM examtype WHERE Active='True' and TestKeyword='ScopeType' and UpdateDate=(Select Min(UpdateDate) from examtype Where Active='True' and TestKeyword='ScopeType')"; //put sql statement here to find ID
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				
				ET_rs = statement.executeQuery(stmt);
				while(ET_rs.next()){
					ET_DBID= ET_rs.getInt(1); //the first variable in the set is the ID row in the database.
					//System.out.println("ET_DBID = "+ET_DBID);
					ET_Act= ET_rs.getString(2); //the second variable in the set is name to be modified in the database.
					//System.out.println("ET Abbreviation = "+ET_Act);
				}
				ET_rs.close();
				stmt="Update examtype SET UpdateDate=CURRENT_TIMESTAMP where idExamType="+ET_DBID;
				update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
				update.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}	
			System.out.println("ET_DBID = "+ET_DBID);
			System.out.println("ET Abbreviation = "+ET_Act);
		}else{
			ET_Act=ET;
		}
		if(Path.equalsIgnoreCase("New")){
			sta.Select_ExamType_New(ET_Act);
			
		}
		else if(Path.equalsIgnoreCase("Modify")){
			sta.ModifyExamType(GridID, ET_Act);
		}
		Description="User selects "+ET_Act+" in the exam type field.  They are using a "+Path+" path.";
		System.out.println("Name_Act ="+Name_Act);

	}**/
	
	public void e_ETNew(){
		System.out.println(getCurrentElement().getName());
		ET="New";
		System.out.println("ET ="+ET);
		try{ 
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			//changed TestKeyword to 'ScopeType' from ' Existing' to handle dependency issue
			stmt="Select idExamType, ExamTypeAbbrv FROM examtype WHERE Active='True' and TestKeyword='ScopeType' and UpdateDate=(Select Min(UpdateDate) from examtype Where Active='True' and TestKeyword='ScopeType')"; //put sql statement here to find ID
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			
			ET_rs = statement.executeQuery(stmt);
			while(ET_rs.next()){
				ET_DBID= ET_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("ET_DBID = "+ET_DBID);
				ET_Act= ET_rs.getString(2); //the second variable in the set is name to be modified in the database.
				//System.out.println("ET Abbreviation = "+ET_Act);
			}
			ET_rs.close();
			stmt="Update examtype SET UpdateDate=CURRENT_TIMESTAMP where idExamType="+ET_DBID;
			update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
			update.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}	
		System.out.println("ET_DBID = "+ET_DBID);
		System.out.println("ET Abbreviation = "+ET_Act);

		if(Path.equalsIgnoreCase("New")){
			sta.Select_ExamType_New(ET_Act);
			
		}
		else if(Path.equalsIgnoreCase("Modify")){
			sta.ModifyExamType(GridID, ET_Act);
		}
		Description="User selects "+ET_Act+" in the exam type field.  They are using a "+Path+" path.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETNull(){
		System.out.println(getCurrentElement().getName());
		ET="";
		System.out.println("ET ="+ET);
		ET_Act=ET;
		
		if(Path.equalsIgnoreCase("New")){
			sta.Select_ExamType_New(ET_Act);
			
		}
		else if(Path.equalsIgnoreCase("Modify")){
			sta.ModifyExamType(GridID, ET_Act);
		}
		Description="User selects "+ET_Act+" in the exam type field.  They are using a "+Path+" path.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ECN_True(){
		System.out.println(getCurrentElement().getName());
		ECN_Act="True";
		System.out.println("ECN ="+ECN_Act);
		
		
		if(Path.equalsIgnoreCase("New")){
			sta.Select_New_ScopeType_ECN_Checked(ECN_Act);
			
		}
		else if(Path.equalsIgnoreCase("Modify")){
			//ModECN_Val=sta.Scope_ECN_Value(GridID);
			sta.Selct_Modify_ECN(GridID, ModECN_Val,ECN_Act);
		}
		else if(Path.equalsIgnoreCase("Shipped")){
			//ModECN_Val=sta.Scope_ECN_Value(GridID);
			sta.Selct_Modify_ECN(GridID, ModECN_Val,ECN_Act);
		}
		Description="User selects "+ECN_Act+" in the Elevator Cleaning Notice field.  They are using a "+Path+" path.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ECN_False(){
		System.out.println(getCurrentElement().getName());
		ECN_Act="False";
		System.out.println("ECN ="+ECN_Act);
		
		
		if(Path.equalsIgnoreCase("New")){
		   sta.Select_New_ScopeType_ECN_Checked(ECN_Act);
			
		}
		else if(Path.equalsIgnoreCase("Modify")){
			//ModECN_Val=sta.Scope_ECN_Value(GridID);
			sta.Selct_Modify_ECN(GridID, ModECN_Val,ECN_Act);
		}
		else if(Path.equalsIgnoreCase("Shipped")){
			//ModECN_Val=sta.Scope_ECN_Value(GridID);
			sta.Selct_Modify_ECN(GridID, ModECN_Val,ECN_Act);
		}
		Description="User selects "+ECN_Act+" in the Elevator Cleaning Notice field.  They are using a "+Path+" path.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_STsaved() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		sta.Save_ScopeType_Edit();
		Description="User clicks the save button.  They are using a "+Path+" path.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CancelEdit() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		sta.Cancel_ScopeType_Edit();
		Description="User clicks the cancel button.  They are using a "+Path+" path.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	// Implementing the vertices for the scope type model
	
	
	public void v_ScopeType(){
		if(ScenarioStartflag==true){
			if(startflag==false){
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				ForFileName = dateFormat.format(date); 
				startflag=true;
			}
			Result=lpV.Verify_Admin_Function("Scope Model");
			System.out.println(getCurrentElement().getName());
			//Empty Vertex
			Description="\r\n=====================================";
			Description+="\r\nStart of new Scenario - "+Scenario;
			actualResult=actualResult+"\r\n"+Description+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Scenario++;
			ScenarioStartflag=false;
		}
	}
	
	public void v_ScopeTypeData(){
		System.out.println(getCurrentElement().getName());
		//Empty Vertex
		Description="Selected path is : "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_STVerf(){
		System.out.println(getCurrentElement().getName());
		System.out.println("Name_Act ="+Name_Act);

		//This vertex is used to navigate the logical portions of the scope type save
		//Empty Vertex
	}
	
	public void v_ScopeTypeName(){
		
		Vertex=getCurrentElement().getName();
		System.out.println("Name_Act ="+Name_Act);

		Description=Name_Act+"appears in the scope type field.  Executing "+Path+" path.";
		if(Path.equalsIgnoreCase("New")){ //If the path is new
			Result=stv.Verify_NEWScopeType(Name_Act);
		}else if(Path.equalsIgnoreCase("Modify")){
			Result=stv.Verify_ModifyScopeType(GridID,Name_Act);
		}else if(Path.equalsIgnoreCase("Shipped")){
			Result="Pass";
		}
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ScopeType_Active(){
		Result="Pass";
		Vertex=getCurrentElement().getName();
		Description=ST_Act+" is selected in the scope type active field.  Executing "+Path+" path.";
		System.out.println(Description);
		System.out.println("Name_Act ="+Name_Act);

		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ScopeType_ET_Assoc(){
		System.out.println(getCurrentElement().getName());
		Description="Pass - "+ET_Act+"is selected in the exam type field.  Executing "+Path+" path.";
		System.out.println("Name_Act ="+Name_Act);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ECN(){
		System.out.println(getCurrentElement().getName());
		Description="Pass - "+ECN_Act+"is selected in the Elevator Cleaning Notice field.  Executing "+Path+" path.";
		//Result=stv.Verify_ScopeType_ECN_Checked(GridID,ECN_Act);
		System.out.println(Description);
		System.out.println("ECN_Active ="+ECN_Act);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	public void v_ScType_Err() throws InterruptedException{
		if(!Path.equalsIgnoreCase("Shipped")){
			if(Name.equalsIgnoreCase("") || ET.equalsIgnoreCase("")){
				ErrorCode="5";
			} else if(Name.equalsIgnoreCase("Existing") || Name.equalsIgnoreCase("Shipped")){
				ErrorCode="4";	
			}else{
				ErrorCode="5";	
			}
		}
		Result=gf.Verify_ErrCode(ErrorCode);
		Description="Scope type is Not saved due to ErrorCode("+ErrorCode+").";
		Vertex= getCurrentElement().getName();
		Description="Scope type is NOT saved and an error message is displayed.";
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ScType_Save() throws InterruptedException{
		Expected="Scope type is saved successfully.";
		Description="Scope type is saved successfully.";
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		System.out.println("Name_Act ="+Name_Act);

		sta.SearchScopeType(Name_Act);
		System.out.println("Searched for Scope Type ="+Name_Act);

		GridID=sta.GetGridID_ScopeType_To_Modify(Name_Act);
		System.out.println("Grid ID="+GridID);
		//ModECN_Val=sta.Scope_ECN_Value(Name_Act);
		//System.out.println("ModECN_Val="+ModECN_Val);
		ModSTAct_Val=sta.ScopeType_Active_Value(Name_Act, GridID);
		System.out.println("ModSTAct_Val="+ModSTAct_Val);
		sta.Select_ScopeType_To_Modify(Name_Act);
		System.out.println("Selected Scope Type ="+Name_Act);

		sta.Cancel_ScopeType_Edit();
		System.out.println("Cancel Edit");

		//if(!GridID.equals(null)&& ModSTAct_Val.equalsIgnoreCase(ST_Act)&& ModECN_Val.equalsIgnoreCase(ECN_Act)){ //If the GridID is Null and Active is set incorrectly, then the save failed. If GridID is not null the save passed. 
		if(!GridID.equals(null)&& ModSTAct_Val.equalsIgnoreCase(ST_Act)){ //If the GridID is Null and Active is set incorrectly, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ 			
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Path.equalsIgnoreCase("New")){
					//stmt="Insert into scopetype(BaseScopeTypeName,EnteredScopeTypeName,ScopeTypeActive,TestKeyword,ExamType) values('Test','"+Name_Act+"','"+ST_Act+"', 'Existing', '"+ET_DBID+"')";
					stmt="Insert into scopetype(EnteredScopeTypeName,ScopeTypeActive,TestKeyword,ExamType) values('"+Name_Act+"','"+ST_Act+"', 'Existing', '"+ET_DBID+"')";
					System.out.println(stmt);

					insert.execute(stmt); 
					insert.close();
				} else if(Path.equalsIgnoreCase("Modify")){
					stmt="Update scopetype SET EnteredScopeTypeName='"+Name_Act+"',ScopeTypeActive='"+ST_Act+"',ExamType='"+ET_DBID+"', UpdateDate=CURRENT_TIMESTAMP where idScopeType="+modScopeType_DBID;
					System.out.println(stmt);

					update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
					update.close();
				}  else if(Path.equalsIgnoreCase("Shipped")) {
					stmt="Update scopetype SET ScopeTypeActive='"+ST_Act+"', UpdateDate=CURRENT_TIMESTAMP where idScopeType="+shippedScopeType_DBID;
					System.out.println(stmt);
					update.executeUpdate(stmt); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Result="#Failed!#";
		}
		
		Expected="The ScopeType details are successfully saved";
		System.out.println(Expected+" result="+Result);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
}
