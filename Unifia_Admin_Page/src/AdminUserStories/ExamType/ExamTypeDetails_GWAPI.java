package AdminUserStories.ExamType;


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
//import TestFrameWork.UnifiaAdminExamTypePage.*;
import TestFrameWork.UnifiaAdminExamTypePage.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;

public class ExamTypeDetails_GWAPI extends ExecutionContext{
public ExamType_Actions etA;
public ExamType_Verification etV;
public LandingPage_Verification lpV;
public GeneralFunc gf;

	
	public String Expected;
	public String Vertex;
	public String GridID;
	public String Path_; //Variable for the Path (New, Modify, Shipped)
	public String stmt; //the cast of the sql statment to be used in the ET_Name table
	public Connection conn= null;
	public int ETDBID;  //Exam Type Data base id from the ExamType table
	public int ETDBID2; //Second Exam Type DB ID for doing updates
	public String ET_Active_Value;
	 
	public String ET_Name; //Variable for the Exam type Name to be pulled from the test Data
	//ET_Name= String.valueOf(getMbt().getDataValue("ET_Name"));
	public String ET_Name_Act; // Actual value being used for the Exam Type Name
	public ResultSet ET_Name_rs;// The result set for the intial query of ET_Name
	public ResultSet ET_Abr_rs;
	public String ET_Abrv; // Variable for the Exam type Abbreviation to be pulled from the test data
	//ET_Abr= String.valueOf(getMbt().getDataValue("ET_Abr"));
	public String Time;//used to add time milliseconds to the abrv
	public String ET_Abr_Act; //Actual value being used for the Exam Type Abbreviation
	public String ETAct; // True false variable for the active inactive flag of the Exam Type
	//ET_Act= String.valueOf(getMbt().getDataValue("ET_Act"));
	public String ET_Abr_Selection;//Use this variable to select the row to modify 
	public String Description;
	public String Result;
	public String Actual;
	public String msg;
	public String ErrorCode; //The error code given when a save fails. 
	

	public long cal = Calendar.getInstance().getTimeInMillis();
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	
	public static String actualResult="\t\t\t ExamType_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="ExamType_TestSummary_";
	public boolean startflag=false;
	public TestFrameWork.TestHelper TH;
	public int Scenario=1;
	public boolean ScenarioStartflag=true;
	
	public String ABRStmp;
	
	
	//implements edges for the Exam type graph
	
	public void e_Start(){
		//Empty Edge to start graph
	}
	
	public void e_Exam() throws  InterruptedException{
		//This should be the only required get of Path!!!
		Object path = getAttribute("Path");
		Path_ = path.toString();
		//Path_= String.valueOf(getMbt().getDataValue("Path"));
		System.out.println(getCurrentElement().getName());
		System.out.println("Path=  "+Path_);
		Description="Path =  "+Path_;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_New() throws  InterruptedException{
		ScenarioStartflag=true;
		//This should be the only required get of Path!!!
		Path_= "New";
		System.out.println("Path=  "+Path_);
		etA.Add_New_ExamType();
		Description="Clicked on + to add new Exam type";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Shipped() throws  InterruptedException{
		ScenarioStartflag=true;
		Path_="Shipped";
		System.out.println("Path=  "+Path_);
		stmt="Select idExamType, ExamTypeAbbrv, ExamTypeName FROM examtype Where TestKeyword='Shipped' and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Shipped')";
		System.out.println(stmt);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			ET_Name_rs = statement.executeQuery(stmt);
			while(ET_Name_rs.next()){
				ETDBID= ET_Name_rs.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("ETDBID = "+ETDBID);
				ET_Abr_Act= ET_Name_rs.getString(2); //the second variable is the concatenated Exam Type Abbreviation which maybe modified in the next method
				System.out.println("Search ET_Abr_Selection = "+ET_Abr_Selection);	
				ET_Name_Act= ET_Name_rs.getString(3); //the second variable is the concatenated Exam Type Abbreviation which maybe modified in the next method
				System.out.println("Search ET_Name_Act = "+ET_Name_Act);	
				
			}
			ET_Name_rs.close();
			update.executeUpdate("Update ExamType Set UpdateDate=CURRENT_TIMESTAMP WHERE ExamTypeAbbrv='"+ET_Abr_Selection+"';");
			
			update.close();

			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		//gather the grid ID and active cb value
		System.out.println("Exam Type Abbreviation to Modify:  "+ET_Abr_Act);
		etA.ClearExamTypeSrchCritera();
		etA.SearchExamTypeByAbbrv(ET_Abr_Act);
		GridID=etA.GetGridID_ExamType_To_Modify(ET_Abr_Act);
		System.out.println("GridID is:  "+GridID);
		ET_Active_Value=etA.ExamType_Active_Value(ET_Abr_Act);
		System.out.println("Current Active Value  "+ET_Active_Value);
		System.out.println("Selecting to modify:  "+ET_Abr_Act);
		etA.Select_ExamType_To_Modify(ET_Abr_Act);
		Description="Selecting to modify:  "+ET_Abr_Act;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	public void e_Modify() throws  InterruptedException{
		ScenarioStartflag=true;
		Path_= "Modify";
		System.out.println("Path=  "+Path_);
		stmt="Select idExamType, ExamTypeAbbrv FROM examtype Where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Existing')";
		System.out.println(stmt);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			ET_Name_rs = statement.executeQuery(stmt);
			while(ET_Name_rs.next()){
				ETDBID= ET_Name_rs.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("ETDBID = "+ETDBID);
				ET_Abr_Selection= ET_Name_rs.getString(2); //the second variable is the concatenated Exam Type Abbreviation which maybe modified in the next method
				System.out.println("Search ET_Abr_Selection = "+ET_Abr_Selection);	
				
			}
			ET_Name_rs.close();
			update.executeUpdate("Update ExamType Set UpdateDate=CURRENT_TIMESTAMP WHERE ExamTypeAbbrv='"+ET_Abr_Selection+"';");
			
			update.close();

			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		//gather the grid ID and active cb value
		System.out.println("Exam Type Abbreviation to Modify:  "+ET_Abr_Selection);
		etA.ClearExamTypeSrchCritera();
		etA.SearchExamTypeByAbbrv(ET_Abr_Selection);
		GridID=etA.GetGridID_ExamType_To_Modify(ET_Abr_Selection);
		System.out.println("GridID is:  "+GridID);
		ET_Active_Value=etA.ExamType_Active_Value(ET_Abr_Selection);
		System.out.println("Selecting to modify:  "+ET_Abr_Selection);
		etA.Select_ExamType_To_Modify(ET_Abr_Selection);
		Description="Selecting to modify:  "+ET_Abr_Selection;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	// Entering the Exam Name 
	public void e_ETNameValid()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Name="Valid";
		System.out.println(ET_Name);
				
		//Set the actual name value
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		ET_Name_Act="Test"+cal+calchk;
		System.out.println("ET_Name_Act="+ET_Name_Act);
		
		if(Path_.equalsIgnoreCase("Modify")){
			etA.ModifyExamTypeName(GridID, ET_Name_Act);
			
		}else if(Path_.equalsIgnoreCase("New")){
			//add new exam type
			etA.Enter_New_ExamType_Name(ET_Name_Act);
		}
			
		Description="User enters "+ET_Name_Act+" in the exam type name field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_ETNameShipped()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Name="Shipped";
		System.out.println(ET_Name);
		
		stmt="Select idExamType, ExamTypeName FROM examtype Where TestKeyword='Shipped' and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Shipped')";
		System.out.println(stmt);

		//Call Test DB to pull the data
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			ET_Name_rs = statement.executeQuery(stmt);
			while(ET_Name_rs.next()){
				ETDBID2= ET_Name_rs.getInt(1); //the first variable in the set is the ID row in the database.
				ET_Name_Act= ET_Name_rs.getString(2); //the second variable is the concatenated Exam type name
	
			}
			ET_Name_rs.close();
			System.out.println("ET_Name_Act = "+ET_Name_Act);	
			//System.out.println("ETDBID = "+ETDBID);

			update.execute("Update examtype set Updatedate= CURRENT_TIMESTAMP WHERE idExamType="+ETDBID);
			update.close();
			
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	
		if(Path_.equalsIgnoreCase("Modify")){
			etA.ModifyExamTypeName(GridID, ET_Name_Act);
		}else if(Path_.equalsIgnoreCase("New")){
			//add new exam type
			etA.Enter_New_ExamType_Name(ET_Name_Act);
		}
			
		Description="Path="+Path_+"; User enters "+ET_Name_Act+" in the exam type name field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETNameNull()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Name="";
		System.out.println(ET_Name);
		ET_Name_Act=ET_Name;	
		if(Path_.equalsIgnoreCase("Modify")){			
			etA.ModifyExamTypeName(GridID, ET_Name_Act);			
		} else if(Path_.equalsIgnoreCase("New")){			
			etA.Enter_New_ExamType_Name(ET_Name_Act);
		}
			
		Description="Path="+Path_+"; User enters "+ET_Name_Act+" in the exam type name field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETNameSame()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Name="Same";
		System.out.println(ET_Name);
		ET_Name_Act=etA.GetCurrentExamTypeName(GridID);
		System.out.println("Re-enter the same name:  "+ET_Name_Act);
		System.out.println("The current name is :"+etA.GetCurrentExamTypeName(GridID));
		System.out.println("The script entered:"+ET_Name_Act);
		etA.ModifyExamTypeName(GridID, ET_Name_Act);
			
		Description="Path="+Path_+"; User enters "+ET_Name_Act+" in the exam type name field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETNameExisting()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Name="Existing";
		System.out.println(ET_Name);
		stmt="Select idExamType, ExamTypeName FROM examtype Where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Existing')";				
		System.out.println(stmt);

		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
		
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			ET_Name_rs = statement.executeQuery(stmt);
			while(ET_Name_rs.next()){
				ETDBID2= ET_Name_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("ETDBID = "+ETDBID);
				ET_Name_Act= ET_Name_rs.getString(2); //the second variable is the concatenated Exam type name
			}
			ET_Name_rs.close();
			System.out.println("ET_Name_Act = "+ET_Name_Act);		
			update.execute("Update examtype set Updatedate= CURRENT_TIMESTAMP WHERE idExamType="+ETDBID);
			update.close();
				
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	
		if(Path_.equalsIgnoreCase("Modify")){
			etA.ModifyExamTypeName(GridID, ET_Name_Act);			
		}else if(Path_.equalsIgnoreCase("New")){
			etA.Enter_New_ExamType_Name(ET_Name_Act);
		}
			
		Description="Path="+Path_+"; User enters "+ET_Name_Act+" in the exam type name field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETNamePathShipped()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Name="Shipped";
		System.out.println(ET_Name);
		
		Description="Path="+Path_+"; No action taken. Name is read only for Shipped Exam Types";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETAbrvShipped()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Abrv="Shipped";

		System.out.println("ET_Abrv="+ET_Abrv);
		stmt="Select ExamTypeAbbrv FROM examtype Where TestKeyword='Shipped' and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Shipped')";

		System.out.println(stmt);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();
			Statement update= conn.createStatement();
			ET_Abr_rs = statement.executeQuery(stmt);
			while(ET_Abr_rs.next()){
			
				ET_Abr_Act= ET_Abr_rs.getString(1); //the third variable is the concatenated Exam Type Abbreviation which maybe modified in the next method
				System.out.println("ET_Abr_Act = "+ET_Abr_Act);	
			}
			ET_Abr_rs.close();
			update.executeUpdate("Update ExamType Set UpdateDate=CURRENT_TIMESTAMP WHERE ExamTypeAbbrv='"+ET_Abr_Act+"';");
			
			update.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}

		if(Path_.equalsIgnoreCase("New")){
			etA.Enter_New_ExamType_Abbreviation(ET_Abr_Act);
		}else if(Path_.equalsIgnoreCase("Modify")){
			etA.ModifyExamTypeAbbrv(GridID, ET_Abr_Act);
		}
		Description="Path="+Path_+"; User enters "+ET_Abr_Act+" in the exam type Abr field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void e_ETAbrvValid()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Abrv="Valid";
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		ET_Abr_Act="ABR:"+cal+calchk;
		System.out.println(ET_Abr_Act);
		if(Path_.equalsIgnoreCase("New")){
			etA.Enter_New_ExamType_Abbreviation(ET_Abr_Act);
		}else if(Path_.equalsIgnoreCase("Modify")){
			etA.ModifyExamTypeAbbrv(GridID, ET_Abr_Act);
		}
		Description="Path_="+Path_+"; User enters "+ET_Abr_Act+" in the exam type Abr field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETAbrvNull()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Abrv="";
		ET_Abr_Act=ET_Abrv;
		if(Path_.equalsIgnoreCase("New")){
			etA.Enter_New_ExamType_Abbreviation(ET_Abr_Act);
		}else if(Path_.equalsIgnoreCase("Modify")){
			etA.ModifyExamTypeAbbrv(GridID, ET_Abr_Act);
		}
		Description="Path="+Path_+"; User enters "+ET_Abr_Act+" in the exam type Abr field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETAbrvSame()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Abrv="Same";
		ET_Abr_Act=etA.GetCurrentExamTypeAbr(GridID);
		System.out.println("Re-enter the same Abbreviation:  "+ET_Abr_Act);
		etA.ModifyExamTypeAbbrv(GridID, ET_Abr_Act);

		Description="Path=Modify; User enters "+ET_Abr_Act+" in the exam type Abr field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETAbrvExisting()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Abrv="Existing";
		stmt="Select ExamTypeAbbrv FROM examtype Where TestKeyword='Existing' and UpdateDate=(Select Min(UpdateDate) from examtype Where TestKeyword='Existing')";
		System.out.println(stmt);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();
			Statement update= conn.createStatement();
			ET_Abr_rs = statement.executeQuery(stmt);
			while(ET_Abr_rs.next()){					
				ET_Abr_Act= ET_Abr_rs.getString(1); //the third variable is the concatenated Exam Type Abbreviation which maybe modified in the next method
				System.out.println("ET_Abr_Act = "+ET_Abr_Act);	
			}
			ET_Abr_rs.close();
			update.executeUpdate("Update ExamType Set UpdateDate=CURRENT_TIMESTAMP WHERE ExamTypeAbbrv='"+ET_Abr_Act+"';");
			
			update.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		if(Path_.equalsIgnoreCase("New")){
			etA.Enter_New_ExamType_Abbreviation(ET_Abr_Act);
		}else if(Path_.equalsIgnoreCase("Modify")){
			etA.ModifyExamTypeAbbrv(GridID, ET_Abr_Act);
		}
		Description="Path="+Path_+"; User enters "+ET_Abr_Act+" in the exam type Abr field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETAbrvPathShipped()throws  InterruptedException{
		System.out.println(getCurrentElement().getName());
		ET_Abrv="Shipped";

		System.out.println("ET_Abrv=  "+ET_Abrv);
		System.out.println("Path is set to:  "+Path_);
		//Do nothing, the Abbreviation field is set to read only when the Path=Shipped.
		Description="Path=Shipped; User enters "+ET_Abr_Act+" in the exam type Abr field";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void e_ETActTrue(){
		//System.out.println(getCurrentElement().getName());
		ETAct="True";
		Description="User sets the exam type active to :  "+ETAct;
		if (Path_.equalsIgnoreCase("New")){
			etA.Selct_New_ExamType_Active(ETAct);
		}else if (Path_.equalsIgnoreCase("Modify")){
			etA.Modify_ExamType_IsActive(GridID, ET_Active_Value, ETAct);
		} else if(Path_.equalsIgnoreCase("Shipped")){
			etA.Modify_ExamType_IsActive(GridID, ET_Active_Value, ETAct);
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ETActFalse(){
		//System.out.println(getCurrentElement().getName());
		ETAct="False";

		Description="User sets the exam type active to :  "+ETAct;
		if (Path_.equalsIgnoreCase("New")){
			etA.Selct_New_ExamType_Active(ETAct);
		}else if (Path_.equalsIgnoreCase("Modify")){
			etA.Modify_ExamType_IsActive(GridID, ET_Active_Value, ETAct);
		} else if(Path_.equalsIgnoreCase("Shipped")){
			etA.Modify_ExamType_IsActive(GridID, ET_Active_Value, ETAct);

		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
		
	public void e_ETSave(){
		System.out.println(getCurrentElement().getName());
		//Putting this in to see if it resolves an issue?
		Object path = getAttribute("Path");
		Path_ = path.toString();
		//Path_= String.valueOf(getMbt().getDataValue("Path"));
		Description="User clicks the save button";
		System.out.println("Path is set to:  "+Path_);
		etA.Save_ExamType_Edit();
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_CancelEdit() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		//System.out.println("Path is set to:  "+Path_);
		//etA.ClearExamTypeSrchCritera();
		Description="User clicks the cancel button";
		etA.Cancel_ExamType_Edit();
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	//implement the vertices for the exam type graph
	public void v_ModifyExamType(){
		Vertex="getCurrentElement().getName()";
		System.out.println(getCurrentElement().getName());
		System.out.println("Path is set to:  "+Path_);
		Expected="Exam Type page is displayed";
		Result=lpV.Verify_Admin_Function("Exam Type");
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ExamTypeDetail(){
		if(ScenarioStartflag==true){
			if(startflag==false){
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				ForFileName = dateFormat.format(date); 
				startflag=true;
			}
			//Vertex="getCurrentElement().getName()";
			//Putting this in to see if it resolves an issue?
			/*Object path = getAttribute("Path");
			Path_ = path.toString();*/		
			//Path_= String.valueOf(getMbt().getDataValue("Path"));
			System.out.println(getCurrentElement().getName());
			Result=lpV.Verify_Admin_Function("Exam Type");
			//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
			Description="\r\n=====================================";
			Description+="\r\nStart of new Scenario - "+Scenario;
			actualResult=actualResult+"\r\n"+Description+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Scenario++;
			ScenarioStartflag=false;
		}
	}
	
	public void v_ExamType(){
		//Needed only for GraphWalker
		System.out.println(getCurrentElement().getName());
		Description="Selected path is - "+Path_;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void v_ETName(){
		Vertex="getCurrentElement().getName()";
		System.out.println(getCurrentElement().getName());

		Description=ET_Name_Act+" is entered in the exam type Name field";	
		System.out.println("The ET_Name_Act value is:  "+ET_Name_Act);
		System.out.println(Description);		
		
		if(Path_.equalsIgnoreCase("New")){
			if(ET_Name.equalsIgnoreCase(null)){
				Result=etV.Verify_NEWExamTypeName(etA.Valueof_NEW_ExamTypeName());	
			}else{				
				Result=etV.Verify_NEWExamTypeName(ET_Name_Act);
			}
		}else{
			if(ET_Name.equalsIgnoreCase(null)){
				Result=etV.Verify_ModifyExamTypeName(GridID,etA.GetCurrentExamTypeName(GridID));	
			}else{
				Result=etV.Verify_ModifyExamTypeName(GridID,ET_Name_Act);
			}
		}
		
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ETAbrv(){
		Vertex=(getCurrentElement().getName());
		System.out.println(getCurrentElement().getName());

		Description=ET_Abr_Act+" is entered in the exam type Abbreviation field";	
		System.out.println(Description);
		if(Path_.equalsIgnoreCase("New")){
			if(ET_Abrv.equalsIgnoreCase(null)){
				Result=etV.Verify_NEWExamTypeAbbrv(ET_Abr_Act);
			}else{
				Result=etV.Verify_NEWExamTypeAbbrv(ET_Abr_Act);
			}
		}else{
			if(ET_Abrv.equalsIgnoreCase(null)){
				Result=etV.Verify_ModifyExamTypeAbbrv(GridID,ET_Abr_Act);
			}else{
				Result=etV.Verify_ModifyExamTypeAbbrv(GridID,ET_Abr_Act);
			}
		}
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ETActive(){
		System.out.println(getCurrentElement().getName());
		//Not being used at this time cannot verify active value until save is clicked. 
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\tPass - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ETVerf(){
		//Needed only for GraphWalke
		System.out.println(getCurrentElement().getName());
	}

	
	public void v_ETSaved() throws InterruptedException{
		Vertex=(getCurrentElement().getName());
		System.out.println(getCurrentElement().getName());
		Expected="The Exam Type is saved successfully.";
		Result=etV.VerifyExamTypeSave(ET_Name_Act, ET_Abr_Act);
		etA.SearchExamTypeByAbbrv(ET_Abr_Act);
		GridID=etA.GetGridID_ExamType_To_Modify(ET_Abr_Act);
		System.out.println("GridID is:  "+GridID);
		System.out.println("Selecting to modify:  "+ET_Abr_Act);
		ET_Active_Value=etA.ExamType_Active_Value(ET_Abr_Act);
		etA.Select_ExamType_To_Modify(ET_Abr_Act);
		etA.Cancel_ExamType_Edit();
		if(Result.startsWith("Pass")&& ET_Active_Value.equalsIgnoreCase(ETAct)){ //&& ET_Active_Value.equalsIgnoreCase(ETAct)
			Description="The Exam Type saved correctly. Updating TestDB with new or updated values. The result is "+Result;

			if(Path_.equalsIgnoreCase("New")){
				stmt="Insert into examtype (ExamTypeName,ExamTypeAbbrv,Active,TestKeyword) Values ('"+ET_Name_Act+"','"+ET_Abr_Act+"','"+ETAct+"','Existing')";
			}else if((Path_.equalsIgnoreCase("Modify"))||(Path_.equalsIgnoreCase("Shipped"))){
				stmt="Update examtype Set ExamTypeName='"+ET_Name_Act+"', ExamTypeAbbrv='"+ET_Abr_Act+"', Active= '"+ETAct+"', Updatedate= CURRENT_TIMESTAMP Where idExamType="+ETDBID;
			}
			
			System.out.println(stmt);
			try{
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				statement.executeUpdate(stmt);
						
				statement.close();
				conn.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
		} else{
			Result="#Failed!#: ";
			Description="#Failed!#: The Exam Type did not save correctly. The result is "+Result;

		}
		
		/**
		 * Write the newly saved record to the DB
		 */

		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

		
	}
	
	public void v_ETSaveError() throws InterruptedException{
		Vertex=(getCurrentElement().getName());
		System.out.println(getCurrentElement().getName());
		
		if(ET_Name.isEmpty()){
			//msg="Invalid value entered for field 'Name'. This field cannot be empty.";
			ErrorCode="5";
		}else if(ET_Abrv.isEmpty()){
			//msg="Invalid value entered for field 'Abbreviation'. This field cannot be empty.";
			ErrorCode="5";		
		}else if(ET_Abrv.equalsIgnoreCase("Existing")){
			//msg="Violation of UNIQUE KEY constraint 'IX_ExamType_Abbreviation'. Cannot insert duplicate key in object 'dbo.ExamType'. The duplicate key value is ("+ET_Abr_Act+"). The statement has been terminated.";
			ErrorCode="4";
		}else if(ET_Abrv.equalsIgnoreCase("Shipped")){
			//msg="Violation of UNIQUE KEY constraint 'IX_ExamType_Abbreviation'. Cannot insert duplicate key in object 'dbo.ExamType'. The duplicate key value is ("+ET_Abr_Act+"). The statement has been terminated.";
			ErrorCode="4";
		}
		
		//Result=gf.Verify_ErrMsg(msg);
		Result = gf.Verify_ErrCode(ErrorCode); 
		Description="Exam Type is not saved due to ErrorCode("+ErrorCode+").";
		Expected="Exam Type is not saved and an error message is displayed. Result="+Result;
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		System.out.println(Expected);
		System.out.println(Result);		
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

}
