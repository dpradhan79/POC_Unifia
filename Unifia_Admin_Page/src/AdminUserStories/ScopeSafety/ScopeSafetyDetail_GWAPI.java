package AdminUserStories.ScopeSafety;


//import org.graphwalker.Util; 
//import org.graphwalker.conditions.EdgeCoverage;

//import org.graphwalker.multipleModels.ModelAPI;
import org.graphwalker.core.machine.ExecutionContext;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.text.DateFormat; 
import java.util.Calendar;  



import java.util.Date;

//import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
//import TestFrameWork.UnifiaAdminLocationPage.Location_Actions;
//import TestFrameWork.UnifiaAdminLocationPage.Location_Verification;
//import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Actions;
//import TestFrameWork.UnifiaAdminExamTypePage.ExamType_Verification;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
//import TestFrameWork.UnifiaAdminLandingPage.*;
//import TestFrameWork.UnifiaAdminScannerPage.*; 
import TestFrameWork.UnifiaAdminScopeSafetyPage.*;
//import TestFrameWork.UnifiaAdminAccessPointPage.*; 
public class ScopeSafetyDetail_GWAPI extends ExecutionContext{
	//Implements the user act descriptions for each edge
	public String Description;  //the description written to the test step log or printed to the console
	public String Expected; //The expected result written to the test step log
	public String Vertex; //the current vertex
	public String Result; // the result of a verification point (either pass or fail) written to the test step log
	public String HangTime; // Hang time value to enter
	public Connection conn= null;
	public String Path; //Variable for the Path (New or Modify)
	public ResultSet ScopeSafety_RS;  //result set used to get a Access Point name from the Test DB
	public String CultAct;
	public String BiobAct;
	public int ScopeFacilityDB; //ID row from Test DB
	public int ModifyScopeFacilityDB; //The facility DB ID for the facility to be modified. 
	public String ModScopeHangValue; //the facility name to be modified.
	public String ModBioTestingAct_Val; //the facility name that will be modified.
	public String ModCulturingAct_Val;
	public String ScopeSavDes;
	public String ResultHangTime;
	public String GridID; //the GridID of the row being modified in Unifia
	public String ErrorCode; //The error code given when a save fails. 
	public String ModCulturing;
	public String ModBioburdenTest;
	public String EntFacName;
	public String DefaultHangTime;
	
	public LandingPage_Verification SE_LV; //shortcut to link to the UnifiaAdminLandingPage LandingPage_Verification java class.
	public GeneralFunc SE_Gen; //shortcut for the General Function Selenium file 
	public ScopeSafety_Actions SE_SSA; //shortcut for the Scope Safety Actions Selenium file
	public ScopeSafety_Verification SE_SSV; //shortcut for the Scope Safety Verification Selenium file
	
	public static String actualResult="\t\t\t ScopeSafety_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="ScopeSafety_TestSummary_";
	public boolean startflag=false;
	public TestFrameWork.TestHelper TH;
	public int Scenario=1;
	public boolean ScenarioStartflag=true;

	//implements the edges for the scope safety Graphml
	public void e_Start(){
		//empty edge for graphml navigation
	}

	public void e_Modify()throws  InterruptedException{
		ScenarioStartflag=true;
		Path="Modify";
		System.out.println("Path="+Path);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			ScopeSafety_RS = statement.executeQuery("Select idFacility, HangTime, BioburdenTest, Culturing, EnteredFacilityName from facility WHERE TestScenario='ScopeSafety' AND LastUsed=(Select MIN(LastUsed) from facility Where TestScenario ='ScopeSafety')");
			while(ScopeSafety_RS.next()){
				ModifyScopeFacilityDB= ScopeSafety_RS.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("ScopeFacilityDB = "+ModifyScopeFacilityDB);
				ModScopeHangValue= ScopeSafety_RS.getString(2); //the first variable is the Facility ID Hang Time
				System.out.println("Hang Time Value = "+ModScopeHangValue);	
				ModBioburdenTest= ScopeSafety_RS.getString(3);	//the third variable is the Bioburden Testing True/False
				System.out.println("Bioburden Testing is = "+ModBioburdenTest);	
				ModCulturing= ScopeSafety_RS.getString(4); //the fourth variable is the Culturing True/False.
				System.out.println("Culturing is = "+ModCulturing);
				EntFacName= ScopeSafety_RS.getString(5); //the fifth variable is the Entered facility name.
				System.out.println("For Facility = "+EntFacName);				
			}
			ScopeSafety_RS.close();
			SE_SSA.Search_FacilityName(EntFacName); //Enter the facility name in the scope safety grid.
			System.out.println("Search for Scope facility name to modify");	
			GridID=SE_SSA.GetGridID_ScopeSafety_To_Modify(EntFacName); // Get the GridID of the Scope Safety to be modified.
			System.out.println("GridID = "+GridID);
			ModBioTestingAct_Val=SE_SSA.ModiBioTesting_Active_Value(); //Get the current value of the Bioburden Testing checkbox for the searched facility. 
			System.out.println("Default Bioburden Testing checkbox : "+ModBioTestingAct_Val+" is selected");
			ModCulturingAct_Val=SE_SSA.ModiCulturing_Active_Value(); //Get the current value of the Culturing Testing checkbox for the searched facility. 
			System.out.println("Default Culturing checkbox: "+ModCulturingAct_Val+" is selected");	
			DefaultHangTime = SE_SSA.Default_ScopeHangTime_Value(); //Get the current value of the scope hang time for the searched facility.
			SE_SSA.Select_ScopeSafety_To_Modify(EntFacName); //Select the row of the facility for Scope Hang Time (Days) modify.
			System.out.println("Scope facility name: "+EntFacName+" is selected");
			
			update.execute("Update facility set LastUsed=CURRENT_TIMESTAMP WHERE idFacility="+ModifyScopeFacilityDB+";");
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
		Description="Scope facility name: "+EntFacName+" is selected";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_SS_Saved() throws InterruptedException{
		Vertex="v_SS_Saved";
		SE_SSA.Search_FacilityName(EntFacName);
		System.out.println("Search Scope facility name.");
		GridID=SE_SSA.GetGridID_ScopeSafety_To_Modify(EntFacName);
		System.out.println("Grid ID="+GridID);	
		ModBioTestingAct_Val=SE_SSA.ModiBioTesting_Active_Value(); //Get the value of the Bioburden Testing checkbox of the searched facility after modified. 
		System.out.println("Bioburden Testing "+ModBioTestingAct_Val+" is selected");
		ModCulturingAct_Val=SE_SSA.ModiCulturing_Active_Value(); //Get the current value of the Culturing Testing checkbox of the searched facility after modified. 
		System.out.println("Culturing "+ModCulturingAct_Val+" is selected");		
		SE_SSA.Select_ScopeSafety_To_Modify(EntFacName); //Select the row of the facility name after being modified
		System.out.println("Scope facility name: "+EntFacName+" is selected");
		ResultHangTime=SE_SSV.Verify_ModHangTime(GridID,HangTime); //Verify scope hang time
		SE_SSA.Cancel_ScopeSafety_Edit();
		if(!GridID.equals(null) && ModBioTestingAct_Val.equalsIgnoreCase(BiobAct) && ModCulturingAct_Val.equalsIgnoreCase(CultAct) && ResultHangTime.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed.
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Path is New or update the modified row if the path is Modify.
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				Statement update= conn.createStatement();
				if(Path.equalsIgnoreCase("New")){
				} else if(Path.equalsIgnoreCase("Modify")){
					update.executeUpdate("Update facility SET LastUsed=CURRENT_TIMESTAMP, HangTime='"+HangTime+"', BioburdenTest='"+BiobAct+"', Culturing='"+CultAct+"' WHERE idFacility="+ModifyScopeFacilityDB); // update the LastUsed variable of the row of data used to the current date/time stamp.
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
		Expected=Vertex+" The Scope Hang Time is "+HangTime+". GridID="+GridID+" Result = " +Result;
		System.out.println(Expected);
		//TestHelper.StepWriter1(Vertex, ScopeSavDes, Expected, Result);
		
		Description=Result+" - "+"The Scope Hang Time is "+HangTime+". GridID="+GridID;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_SS_SaveError()throws InterruptedException{
		Vertex="v_SS_SaveError";
		System.out.println("Vertex =  "+Vertex+"Scope Hang Time = "+HangTime);
		if(HangTime.equalsIgnoreCase("")){
			ErrorCode="5";
		} else if(HangTime.equalsIgnoreCase("-1") || HangTime.equalsIgnoreCase("0") ||HangTime.equalsIgnoreCase("Alpha")){
			ErrorCode="19";
		} else{
			ErrorCode="5";	
		}
		Result=SE_Gen.Verify_ErrCode(ErrorCode);
		Description="Scope Hang Time  is NOT saved due to ErrorCode("+ErrorCode+").";
		Vertex= getCurrentElement().getName();
		Expected="Scope Hang Time is NOT saved and an error message is displayed.";
		SE_SSA.Cancel_ScopeSafety_Edit(); //After the message is verified, click the cancel button to exit the Scope Safety edit.
		System.out.println(Expected);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Cancel() throws InterruptedException {
		Description= "The user Clicks the Cancel button.";
		System.out.println(Description);
		SE_SSA.Cancel_ScopeSafety_Edit();
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	

	public void v_ScopeSafetyDetail() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Vertex=getCurrentElement().getName();
		Expected= "The Bioburden Testing field is set to:  "+ModBioTestingAct_Val+" And The Culturing field is set to: "+ModCulturingAct_Val+ " and Scope Hang Time is set to: "+DefaultHangTime+ 	" for Selected facility: "+EntFacName+ ".";
		System.out.println(Expected);
		Result="Pass";
		//TestHelper.StepWriter1(Vertex, "Verify Scope Safety Details ", Expected, Result);
		
		Description=Result+" - "+"The Bioburden Testing field is set to:  "+ModBioTestingAct_Val+" And The Culturing field is set to: "+ModCulturingAct_Val+ " and Scope Hang Time is set to: "+DefaultHangTime+ 	" for Selected facility: "+EntFacName+ ".";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ScopeSafety(){
		if(ScenarioStartflag==true){
			if(startflag==false){
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				ForFileName = dateFormat.format(date); 
				startflag=true;
			}
			Description="\r\n=====================================";
			Description+="\r\nStart of new Scenario - "+Scenario;
			Vertex=getCurrentElement().getName();
			System.out.println(getCurrentElement().getName());
			Result = SE_SSV.Verify_ScopeSafety_Page();
			System.out.println("Verify 'Scope Safety' screen ="+Result);
			if(Result.equals("Pass")){
				//TestHelper.StepWriter1(Vertex, "Verify Scope Safety screen", "Scope Safety screen is displayed", Result);
					Description+="\r\n"+Result+" - "+"Scope Safety screen is displayed";
				}else{
				//TestHelper.StepWriter1(Vertex, "Verify Scope Safety screen", "Scope Safety screen is not displayed", Result); 
					Description+="\r\n"+Result+" - "+"Scope Safety screen is not displayed";
				}	
			actualResult=actualResult+"\r\n"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Scenario++;
			ScenarioStartflag=false;
		}
	}
	
	public void v_SS_Biob() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		System.out.println("Vertex:"+Vertex+"; Bioburden Testing checkbox status is verified in the v_SS_Saved");
		Result="Pass";
		Expected= Vertex+" The Bioburden Testing field is set to:  "+BiobAct;
		System.out.println(Expected);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		Description=Result+" - "+"The Bioburden Testing field is set to:  "+BiobAct;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_SS_Cult()throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		System.out.println("Vertex:"+Vertex+"; Culturing checkbox status is verified in the v_SS_Saved");
		Result="Pass";
		Expected= Vertex+" The Culturing field is set to:  "+CultAct;
		System.out.println(Expected);		
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		Description=Result+" - "+" The Culturing field is set to:  "+CultAct;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void v_SS_HT(){
		Vertex="v_SS_HT";
		Expected= Vertex+" The Scope Hang Time field is set to:  "+HangTime;
		System.out.println(Expected);
		Result=SE_SSV.Verify_ModHangTime(GridID,HangTime);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		Description=Result+" - "+" The Scope Hang Time field is set to:  "+HangTime;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Biob_True() {
		BiobAct ="True";
		SE_SSA.Modify_BiobTesting_Active(ModBioTestingAct_Val,BiobAct);
		System.out.println("The Bioburden Testing value is set to "+BiobAct);
		Description = "The user selects "+BiobAct+" in the Bioburden Testing field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Biob_False() {
		BiobAct ="False";
		SE_SSA.Modify_BiobTesting_Active(ModBioTestingAct_Val,BiobAct);
		System.out.println("The Bioburden Testing checkbox value is set to "+BiobAct);
		Description = "The user selects "+BiobAct+" in the Bioburden Testing field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void e_Cult_True() {
		CultAct ="True";
		SE_SSA.Modify_Culturing_Active(ModCulturingAct_Val,CultAct);
		System.out.println("The Culturing checkbox value is set to "+CultAct);
		Description = "The user selects "+CultAct+" in the Culturing field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Cult_False() {
		CultAct ="False";
		SE_SSA.Modify_Culturing_Active(ModCulturingAct_Val,CultAct);
		System.out.println("The Culturing checkbox value is set to "+CultAct);
		Description = "The user selects "+CultAct+" in the Culturing field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}		
	
	public void e_SS_HTBlank(){
		HangTime="";
		SE_SSA.Modify_ScopeSafety_HangTime(GridID,HangTime);
		System.out.println("The Facility Hang Time value is set to "+HangTime+" days");
		Description = "The user entered "+HangTime+" in the Scope Hang Time field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SS_HTAlpha(){
		HangTime="Alpha";
		SE_SSA.Modify_ScopeSafety_HangTime(GridID,HangTime);
		System.out.println("The Scope Hang Time value is set to "+HangTime+" days");
		Description = "The user entered "+HangTime+" in the Scope Hang Time field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SS_HTValid(){
		HangTime="6";
		SE_SSA.Modify_ScopeSafety_HangTime(GridID,HangTime);
		System.out.println("The Scope Hang Time value is set to "+HangTime+" days");
		Description = "The user entered "+HangTime+" in the Scope Hang Time field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SS_HTZero(){
		HangTime="0";
		SE_SSA.Modify_ScopeSafety_HangTime(GridID,HangTime);
		System.out.println("The Scope Hang Time value is set to "+HangTime+" days");
		Description = "The user entered "+HangTime+" in the Scope Hang Time field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SS_HTNegative(){
		HangTime="-1";
		SE_SSA.Modify_ScopeSafety_HangTime(GridID,HangTime);
		System.out.println("The Scope Hang Time value is set to "+HangTime+" days");
		Description = "The user entered "+HangTime+" in the Scope Hang Time field.  Executing workflow:  "+Path;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Save() throws InterruptedException{
		SE_SSA.Save_ScopeSafety_Edit();
		ScopeSavDes = "The user clicks the save button.";
		System.out.println(ScopeSavDes);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+ScopeSavDes;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void v_ver1(){
		System.out.println(getCurrentElement().getName());
		//Empty vertex for logical verification navigation
	}
	
	public void v_ver2(){
		System.out.println(getCurrentElement().getName());
		//Empty vertex for logical verification navigation
	}
	
	public void v_ver3(){
		System.out.println(getCurrentElement().getName());
		//Empty vertex for logical verification navigation
	}
	public void v_ver4(){
		System.out.println(getCurrentElement().getName());
		//Empty vertex for logical verification navigation
	}	
}
