package AdminUserStories.AssignBarCode;


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
import TestFrameWork.UnifiaAdminGeneralFunctions.*;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminAssignBarcodePage.*; 

public class AssignBarcodeDetail_GWAPI extends ExecutionContext{
	

	public LandingPage_Verification SE_LV; //shortcut to link to the UnifiaAdminLandingPage LandingPage_Verification java class.
	public GeneralFunc gf; //shortcut for the General Function Selenium file 
	public TestFrameWork.UnifiaAdminAssignBarcodePage.AssignBarcode_Actions ABCA; //shortcut for the AssignBarCode Actions Selenium file
	public TestFrameWork.UnifiaAdminAssignBarcodePage.AssignBarcode_Verification ABCV; //shortcut for the AssignBarCode Verification Selenium file

	//Implements the user act descriptions for each edge
	public String Description;  //the description written to the test step log or printed to the console
	public String Expected; //The expected result written to the test step log
	public String Vertex; //the current vertex
	public String Edge; //
	public String Result; // the result of a verification point (either pass or fail) written to the test step log
	public String Result2; //used to verify the data was saved successfully
	
	public String Active; // whether or not the Barcode is active or not
	public String ModBarcodeAct_Val; //the status to be modified
	
	public Connection conn= null;
	public String Path; //Variable for the Path (New or Modify)
	public ResultSet Barcode_RS;  //result set used to get a Barcode name from the Test DB
	public String BC_Name_Actual; //Actual Barcode Name  
	public String BC_Name;  //Barcode name variable from the graph
	public String Type_Actual; //Actual Barcode Type entered into Unifia
	public String BC_Type; //Barcode Type variable from the graph 
	public int Barcode_DBID; // the Barcode dbID from the test DB
	
	public int ModBarcode_DBID; // the Bar code dbID from the test DB
	public String ModName; //Actual access point Name entered into Unifia that will be modified
	public String ModType; //Actual access point Type entered into Unifia that will be modified

	public String stmt; //used for SQL queries
	public String GridID; //the GridID of the row being modified in Unifia
	public String ErrorCode; //The error code given when a save fails. 
		
	public long cal = Calendar.getInstance().getTimeInMillis(); 
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	
	public static String actualResult="\t\t\t AssignBarcodeDetail_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="AssignBarcodeDetail_TestSummary_";
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
				stmt="select BarcodeID,Name,BarcodeType,IsActive from barcode where TestKeyword='Existing' and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from barcode Where TestKeyword='Existing')"; //put sql statement here to find ID
				System.out.println(stmt);
				Statement statement = conn.createStatement();
				Statement update = conn.createStatement();
				Barcode_RS = statement.executeQuery(stmt);
				while(Barcode_RS.next()){
					ModBarcode_DBID = Barcode_RS.getInt(1); //the first variable in the set is the ID row in the database.
					ModName = Barcode_RS.getString(2); //the second variable in the set is the Name.
					ModType = Barcode_RS.getString(3); //the Third variable in the set is the Type
					ModBarcodeAct_Val=Barcode_RS.getString(4);
				}		
				Barcode_RS.close();
				System.out.println("ModBarcode_DBID = "+ModBarcode_DBID);
				System.out.println("ModName in e_Modify function = "+ModName);
				System.out.println("ModType in e_Modify function= "+ModType);
				System.out.println("ModBarcodeAct_Val in e_Modify function="+ModBarcodeAct_Val);
				
				update.execute("Update barcode set LastUpdatedDateTime=CURRENT_TIMESTAMP WHERE BarcodeID="+ModBarcode_DBID+";");
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
			
			ABCA.ClearBarcodeSrchCritera(); //Clear all search criteria
			ABCA.Search_Name(ModName); //Search for the Name to be modified. 
			GridID=ABCA.GetGridID_Barcode_To_Modify(ModName); //Get the GridID for the Name to be modified. 
			System.out.println("GridID in e_Modify function is: "+GridID); 
			//Get the value of the active checkbox
			ModBarcodeAct_Val=ABCA.Barcode_Active_Value(ModName);
			System.out.println("ModBarcodeAct_Val in e_Modify function is: "+ModBarcodeAct_Val); 
			ABCA.Select_Barcode_To_Modify(ModName);//Select the Name to be modified. 
			System.out.println("Selecting to modify: "+ModName);
			Description="Selecting to modify: "+ModName;
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_New() throws InterruptedException{
		ScenarioStartflag=true;
		Path="New";
		ABCA.Add_New_Barcode(); //click the 'Add New Row' icon
		System.out.println("Path="+Path);  
		Description="Adding new Barcode";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reset() throws InterruptedException{
			BC_Name="Reset";
		    BC_Type="Reset";
			System.out.println("Reset");  
			Description="Resetting Name and Type";
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	
	public void e_BC_Name_Valid()throws  InterruptedException{
		BC_Name="Valid";
		System.out.println("The Name in e_BC_Name_Valid function = "+BC_Name);	
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		BC_Name_Actual="Name"+cal+calchk; //Create a unique Name by converting the current time to an integer.
		System.out.println("Name_Actual="+BC_Name_Actual);
	
		if(Path.equalsIgnoreCase("New")) { //Enter the valid value into the Name field 
			ABCA.Enter_Name_New(BC_Name_Actual); 
		}else if (Path.equalsIgnoreCase("Modify")){
			ABCA.Modify_Name(GridID, BC_Name_Actual);
		}
		Description = "The user enters "+BC_Name_Actual+" in the Name field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BC_Name_Existing()throws  InterruptedException{
		BC_Name="Existing";
		System.out.println("The BC_Name in e_BC_Name_Existing function = "+BC_Name);

		try{ //Find an Name that exists in Unifia to verify the Name must be unique.
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			if(Path.equalsIgnoreCase("New")) {
				stmt="Select BarcodeID, Name from barcode where TestKeyword='Existing' and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from barcode Where TestKeyword='Existing')"; //put sql statement here to find ID
			} else if (Path.equalsIgnoreCase("Modify")){
				// Rahul : Modified
				stmt="select BarcodeID, Name from barcode where TestKeyword='Existing' and Name!='"+ModName+"' and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from barcode Where TestKeyword='Existing' and Name!='"+ModName+"')"; //put sql statement here to find ID
			}
			System.out.println(stmt);
			Statement statement = conn.createStatement();
			Statement update = conn.createStatement();
			Barcode_RS = statement.executeQuery(stmt);
			
			while(Barcode_RS.next()){
				Barcode_DBID = Barcode_RS.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("Barcode_DBID = "+Barcode_DBID);
				BC_Name_Actual = Barcode_RS.getString(2); //the second variable in the set is the Name value that exists in Unifia
				System.out.println("Name_Actual e_NameExisting function = "+BC_Name_Actual);
		
			} Barcode_RS.close();
			update.execute("Update barcode set LastUpdatedDateTime=CURRENT_TIMESTAMP WHERE Barcodeid="+Barcode_DBID+";");
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
		if(Path.equalsIgnoreCase("New")) { //Enter the non unique value into the Name field (this will error when the save button is clicked. 
			ABCA.Enter_Name_New(BC_Name_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			ABCA.Modify_Name(GridID, BC_Name_Actual);
		}
		Description = "The user enters "+BC_Name_Actual+" in the Name field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BC_Name_Same()throws  InterruptedException{
		BC_Name= "Same";
		System.out.println("The BC_Name in e_BC_Name_Same function = "+BC_Name);
		BC_Name_Actual=ModName;
		Barcode_DBID=ModBarcode_DBID;
		Description="The user does nothing. Uses the same Name value which is "+BC_Name_Actual;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BC_Name_Null()throws  InterruptedException{
		BC_Name="";
		System.out.println("The BC_Name in e_BC_Name_Null function = "+BC_Name);
		BC_Name_Actual=BC_Name;
		if(Path.equalsIgnoreCase("New")) { //Enter no value the Name field (this will error when the save button is clicked).
			ABCA.Enter_Name_New(BC_Name_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			ABCA.Modify_Name(GridID, BC_Name_Actual);
		}
		Description = "The user enters "+BC_Name_Actual+" in the Name field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	

	public void e_BC_Type_OOF()throws  InterruptedException{
		BC_Type="Out of Facility Location";
		System.out.println("e_BC_Type_OOF: Type= "+BC_Type);
		Type_Actual=BC_Type;
		if(Path.equalsIgnoreCase("New")) { //Enter no value the Type field (this will error when the save button is clicked).
			ABCA.Select_New_Barcode_Type(Type_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			ABCA.Select_Modify_Barcode_Type(GridID,Type_Actual);
		}
		Description = "The user selects "+Type_Actual+" in the barcode Type field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	/* Bioburden Testing */
	public void e_BC_Type_BIO()throws  InterruptedException{
		BC_Type="Bioburden Testing";
		System.out.println("e_BC_Type_BIO: Type= "+BC_Type);
		Type_Actual=BC_Type;
		if(Path.equalsIgnoreCase("New")) { //Enter no value the Type field (this will error when the save button is clicked).
			ABCA.Select_New_Barcode_Type(Type_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			ABCA.Select_Modify_Barcode_Type(GridID,Type_Actual);
		}
		Description = "The user selects "+Type_Actual+" in the barcode Type field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BC_Type_Null()throws InterruptedException{
		Expected="BC_Type_Null";
		BC_Type="";
		Edge=getCurrentElement().getName();
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		System.out.println("e_TypeNull: Type= "+BC_Type+"is selected");
		Type_Actual=BC_Type;
		if(Path.equalsIgnoreCase("New")) { //Enter no value the Type field (this will error when the save button is clicked).
			ABCA.Select_New_Barcode_Type(Type_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			ABCA.Select_Modify_Barcode_Type(GridID,Type_Actual);
		}
		
		Description = "The user selects "+Type_Actual+" in the staff type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BC_Type_Same(){
		BC_Type="Same";
		System.out.println("e_BC_Type_Same: Type= "+BC_Type);
		Type_Actual=ModType;
		Description = "The user does nothing. Uses the same Barcode Type value which "+Type_Actual;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BC_Type_RFR()throws  InterruptedException{
		BC_Type="Reason for Reprocessing";
		System.out.println("e_BC_Type_RFR: Type= "+BC_Type);
		Type_Actual=BC_Type;
		if(Path.equalsIgnoreCase("New")) { //Enter no value the Type field (this will error when the save button is clicked).
			ABCA.Select_New_Barcode_Type(Type_Actual);
		}else if (Path.equalsIgnoreCase("Modify")){
			ABCA.Select_Modify_Barcode_Type(GridID,Type_Actual);
		}
		Description = "The user selects "+Type_Actual+" in the barcode Type field.";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BC_Active_True() {
		Edge=getCurrentElement().getName();
		Expected="Barcode_Status is True";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Active = "True";
		if (Path.equalsIgnoreCase("New")){
			ABCA.Selct_New_BarcodeStatus(Active);
		}
		else if (Path.equalsIgnoreCase("Modify")){			
			ABCA.Selct_Modify_BarcodeStatus(ModBarcodeAct_Val,Active);
		}
		Description = "The user selects "+Active+" in the Barcode active field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_BC_Active_False() {
		Edge=getCurrentElement().getName();
		Expected="Barcode_Status is False";
		System.out.println("Edge:"+Edge+"; Expected=:"+Expected);
		Active = "False";
		if (Path.equalsIgnoreCase("New")){
			ABCA.Selct_New_BarcodeStatus(Active);
		}
		else if (Path.equalsIgnoreCase("Modify")){			
			ABCA.Selct_Modify_BarcodeStatus(ModBarcodeAct_Val,Active);
		}
		Description = "The user selects "+Active+" in the Barcode active field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Save () throws InterruptedException{
		ABCA.Save_Barcode_Edit();
		System.out.println(getCurrentElement().getName());
		Description="The user clicks the save button on the Barcode Assignment Page.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Cancel() throws InterruptedException{
		ABCA.Cancel_Barcode_Edit();
		System.out.println("e_Cancel - clicked Cancel button");
		Description="clicked Cancel button";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	//implements the vertices for the Scanner Graphml
	
	public void v_Barcode(){
		if(ScenarioStartflag==true){
			if(startflag==false){
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				ForFileName = dateFormat.format(date); 
				startflag=true;
			}
			Result=SE_LV.Verify_Admin_Function("Custom Values");
			System.out.println(getCurrentElement().getName());
			Description="\r\n=====================================";
			Description+="\r\nStart of new Scenario - "+Scenario;
			actualResult=actualResult+"\r\n"+Description+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Scenario++;
			ScenarioStartflag=false;
			//Empty vertex for navigating the graph
		}
	}
	
	public void v_BarcodeAssignDetails(){
		System.out.println(getCurrentElement().getName());
		Description="Selected path : "+Path;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//Empty vertex for navigating the graph
	}
		
	public void v_Barcode_Name(){
		//Verify the Name value displayed in the application
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());	
		Description= BC_Name_Actual+" is displayed in the Name field.";		
		if(Path.equalsIgnoreCase("New")) {			
			Result=ABCV.Verify_NewName(BC_Name_Actual);
		}else if(Path.equalsIgnoreCase("Modify")){			
			Result=ABCV.Verify_ModifyName(GridID, BC_Name_Actual);
		}
		System.out.println(Description);	
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Barcode_Type () throws InterruptedException{
		//Verify the Type value displayed in the application
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());		
		//Vertex=getCurrentElement().getName();
		//System.out.println(getCurrentElement().getName());
		if(Path.equalsIgnoreCase("New")){
			Result=ABCV.Verify_NewBarcodeType(Type_Actual);	
			}
		else{				
			Result=ABCV.Verify_ModBarcodeType(GridID,Type_Actual);
			}
		Description= BC_Type+" is displayed in the Barcode Assignment screen.";
		//Description="The barcode Type is entered per edge condition.";
		System.out.println(Description);		
	    //TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
	    actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Barcode_Active(){
		//Verify that the Access Point is select per the edge condition
		Vertex=getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		System.out.println("Vertex:"+Vertex+"; Barcode Status is verified in the v_Barcode_Saved");
		//Description="The Barcode Status is entered per edge condition.";
		Result="Pass";
		Expected= Active+" is displayed in the Status field.";
		System.out.println(Expected);	
		//TestFrameWork.TestHelper.StepWriter1(Vertex, "Barcode Status is verified in the v_Barcode_Saved", Expected, Result);
		Description="Barcode Status is verified in the v_Barcode_Saved";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Barcode_Saved() throws InterruptedException{
		//Verify the Barcode value was saved and the values for Name and Type are correct. 
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		System.out.println("v_Barcode_Saved =");
		ABCA.Search_Name(BC_Name_Actual);	//Search for the Barocde name that was saved 	
		System.out.println("Searched for Name in v_Barcode_Saved ="+BC_Name_Actual);
		GridID=ABCA.GetGridID_Barcode_To_Modify(BC_Name_Actual);  //If the GridID is available, then the item is found and the Name value was saved correctly.
		System.out.println("Grid ID="+GridID);
		//'Get the actual value of active checkbox -- To uncomment after fix
		ModBarcodeAct_Val=ABCA.Barcode_Active_Value(BC_Name_Actual); //Get the current value of the Active checkbox of the facility to be modified.
		System.out.println("Barcode status: "+ModBarcodeAct_Val+" is Verified");
		ABCA.Select_Barcode_To_Modify(BC_Name_Actual); //Select the Name to verify the Type value saved correctly.
		System.out.println("Selected Name ="+BC_Name_Actual);
		Result=ABCV.Verify_ModBarcodeType(GridID, Type_Actual); //make sure the Type is set correctly. 
		System.out.println("Result="+Result);
		ABCA.Cancel_Barcode_Edit(); //cancel the edit without making any changes. 
		System.out.println("Cancel Edit");
		
		if(!GridID.equalsIgnoreCase(null)&& ModBarcodeAct_Val.equalsIgnoreCase(Active) && Result.equalsIgnoreCase("Pass")){	
			//If the GridID is found and Result are Pass, the item was saved correctly. so update the Test DB. 
			Result="Pass";
				try{ 
					conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
					Statement statement = conn.createStatement();
					Statement update= conn.createStatement();
					Statement insert= conn.createStatement();

					if(Path.equalsIgnoreCase("New")){
						//if the path was new, insert a new row into the Test DB for the newly created barcode assignment
						stmt="Insert into barcode(Name,BarcodeType,IsActive,TestKeyword) values('"+BC_Name_Actual+"','"+Type_Actual+"', '"+ModBarcodeAct_Val+"', 'Existing')";
						System.out.println(stmt);
						insert.execute(stmt); 
						insert.close();
					} else if(Path.equalsIgnoreCase("Modify")){	
						//if the path was modify, update the TestDB row that was being modified with the updated values and current time stamp.
						stmt="Update barcode Set Name='"+BC_Name_Actual+"',BarcodeType='"+Type_Actual+"', IsActive ='"+ModBarcodeAct_Val+"',LastUpdatedDateTime=CURRENT_TIMESTAMP where BarcodeId='"+ModBarcode_DBID+"'";
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
				System.out.println("Save failed");
				//Result="Fail";
				Expected= "#Failed!#: Results did not save an error has occured";
				Description="#Failed!#: Results were supposed to have saved, but did not save. an error has occured";

			}	
		System.out.println("Vertex="+Vertex+"; Expected="+Expected);
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}	
	
	public void v_Barcode_SaveError() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println(getCurrentElement().getName());
		System.out.println(getCurrentElement().getName());

		if(BC_Name.equalsIgnoreCase("")|| BC_Type.equalsIgnoreCase("")) { 
			//If Name or Type are empty, the save will fail and give error code 5 indicating the field is required. 
			ErrorCode="5";
		} else if(BC_Name.equalsIgnoreCase("Existing")){
			//Name must be unique. If the Name already exists in the database the save will fail indicating error code 4 and the field must be unique.
			ErrorCode="4";	
		}
		Result=gf.Verify_ErrCode(ErrorCode); //Verify the error code given by Unifia is correct. 
		Description="Barcode Assignment is not saved due to ErrorCode("+ErrorCode+").";
		Expected="Barcode Assignment is not saved and an error message is displayed. Result="+Result;
		//TestHelper.StepWriter1(Vertex, Description, Expected, Result); 
		System.out.println(Expected);
		System.out.println(Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
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

		
}
