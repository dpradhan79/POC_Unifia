package AdminUserStories.Location;
import static org.junit.Assert.fail;

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

import TestFrameWork.TestHelper;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.*;
import TestFrameWork.UnifiaAdminLocationPage.*;


public class LocationDetails_GWAPI extends ExecutionContext{

	public GeneralFunc SE_Gen; //shortcut to link to the UnifiaAdminGeneralFunctions java class.
	public LandingPage_Actions SE_LA; //shortcut to link to the UnifiaAdminLandingPage LandingPage_Actions java class.
	public LandingPage_Verification SE_LV; //shortcut to link to the UnifiaAdminLandingPage LandingPage_Verification java class.
	public Location_Actions SE_LocA; //shortcut to link to the UnifiaAdminRolePage Location_Actions java class.
	public Location_Verification SE_LocV; //shortcut to link to the UnifiaAdminRolePage Location_Verification java class.
	
	public String LocName;  //Location Name
	public String LocType; //Location type, it will eventually be a selection of predefined types
	public String Cabinets="Not Used"; //Storage Cabinets field value from the Graph. NR 14may15 added for user story 918 values: Same, Valid, ""(null), alpha, zero, negative, 100, special
	public String LocFacility; //Location association to Facility
	public String LocActive;  //True/False value to determine in Location is active.
	public String LocSSID; // value of Access Point field
	public String OERModel; //NR 14may15 added for user story 875 values: Same, Valid, ""(null)
	public String OERSerialNo; //NR 14may15 added for user story 875 values: Same, Valid, ""(null)
	public String DisinfectantCycle="Not Used"; //NR 14may15 added for user story 875 values: Same, Valid, ""(null), negative, zero, alpha, special 
	public String DisinfectantDays="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String WaterFilterCycle="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String WaterFilterDays="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String AirFilterCycle="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String AirFilterDays="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String VaporFilterCycle="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String VaporFilterDays="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 	 
	public String DetergentCycle="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String DetergentDays="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String AlcoholCycle="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String AlcoholDays="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String PMCycle="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String PMDays="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 
	public String CycleTime="Not Used"; //NR 14may15 added for user story 875 values:  Same, Valid, ""(null), negative, zero, alpha, special 


	public ResultSet Location_rs; //Result Set for Location Name

	public int ModifyLocationDB; //The Location DB ID for the location to be modified. 
	public int ModifyFacilityDB;
	public int ModifySSIDDB;
	public String ModifyLocNameValue; //the Location name to be modified.
	//public String ModLocNameBaseValue; //the base name of the Location to be modified.
	//public String UpdatedLocNameValue; //updated Location name (base name + time stamp)
	public String ModLocAct_Val; //the current Active value of the Location name that will be modified.
	public String ModLocFacility; //The facility associated with the location that is being modified.
	public String ModLocType; // The location's type with the location that is being modified
	public String ModLocSSID; //the SSID that is associated to the location that is being modified. 
	public int ModStorageCabinets; //NR 14may15 added for user story 918. the value of the Storage Cabinets of the location being modified. 
	public String ModOERModel; //NR 14may15 added for user story 875 OER Model with the location that is being modified
	public String ModOERSerialNo; //NR 14may15 added for user story 875 OER Serial Number with the location that is being modified
	public int ModDisinfectantCycle; //NR 14may15 added for user story 875 OER Disinfectant Cycles with the location that is being modified, 
	public int ModDisinfectantDays; //NR 14may15 added for user story 875 OER Disinfectant Days with the location that is being modified, 
	public int ModWaterFilterCycle; //NR 14may15 added for user story 875 OER Water Filter Cycles with the location that is being modified, 
	public int ModWaterFilterDays; //NR 14may15 added for user story 875 OER Water Filter days with the location that is being modified, 
	public int ModAirFilterCycle; //NR 14may15 added for user story 875 OER Air Filter Cycles with the location that is being modified, 
	public int ModAirFilterDays; //NR 14may15 added for user story 875 OER Air Filter Days with the location that is being modified, 
	public int ModVaporFilterCycle; //NR 14may15 added for user story 875 OER Vapor Filter Cycles with the location that is being modified, 
	public int ModVaporFilterDays; //NR 14may15 added for user story 875 OER Vapor Filter Days with the location that is being modified, 
	public int ModDetergentCycle; //NR 14may15 added for user story 875 OER Detergent Cycles with the location that is being modified, 
	public int ModDetergentDays; //NR 14may15 added for user story 875 OER Detergent Days with the location that is being modified, 
	public int ModAlcoholCycle; //NR 14may15 added for user story 875 OER Alcohol cycles with the location that is being modified, 
	public int ModAlcoholDays; //NR 14may15 added for user story 875 OER Alcohol Days with the location that is being modified, 
	public int ModPMCycle; //NR 14may15 added for user story 875 OER Preventive Maintenance (PM) Cycle with the location that is being modified, 
	public int ModPMDays; //NR 14may15 added for user story 875 OER Preventive Maintenance (PM) Days with the location that is being modified, 
	public int ModCycleTime; //NR 14may15 added for user story 875 OER Cycle Time with the location that is being modified, 
	
	public int LocationDB; //The Location DB ID for the Location to be modified. 
	public int FacilityDBValue;
	//public String LocNameBaseValue; //the base name of the Location to be modified.
	public String LocNameValue; //updated Location name (base name + time stamp)
	public String LocFacilityValue; // the location facility value 
	public String LocFacilityValue1; // the location facility value 
	public String LocFacilityValue2; // the location facility value 
	public String LocActiveValue; //the Active value from the Test DB
	public String LocAct_Val; //the Location active Value name that will be modified.
	public String LocSSIDValue; // the Access Point value to be modified
	public String StorageCabinetsValue; //The Actual value for Storage Cabinets field. NR 14may15 added for user story 918
	public int StorageCabinetsCounter=0; //NR 14may15 a counter used to set the Storage Cabinet field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int DisinfectantCycleCounter=0; //NR 14may15 a counter used to set the Disinfectant Cycle field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int DisinfectantDaysCounter=0; //NR 14may15 a counter used to set the Disinfectant Days field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int WaterFilterCycleCounter=0; //NR 14may15 a counter used to set the Water Filter Cycle field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int WaterFilterDaysCounter=0; //NR 14may15 a counter used to set the Water Filter Days field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int AirFilterCycleCounter=0; //NR 14may15 a counter used to set the Air Filter Cycle field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int AirFilterDaysCounter=0; //NR 14may15 a counter used to set the Air Filter Days field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int VaporFilterCycleCounter=0; //NR 14may15 a counter used to set the Air Filter Cycle field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int VaporFilterDaysCounter=0; //NR 14may15 a counter used to set the Air Filter Days field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int DetergentCycleCounter=0; //NR 14may15 a counter used to set the Detergent Cycle field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int DetergentDaysCounter=0; //NR 14may15 a counter used to set the Detergent Days field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int AlcoholCycleCounter=0; //NR 14may15 a counter used to set the Alcohol Cycle field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int AlcoholDaysCounter=0; //NR 14may15 a counter used to set the Alcohol Days field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int PMCycleCounter=0; //NR 14may15 a counter used to set the Preventive Maintenance (PM) Cycle field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int PMDaysCounter=0; //NR 14may15 a counter used to set the Preventive Maintenance (PM) Days field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public int CycleTimeCounter=0; //NR 14may15 a counter used to set the Cycle Time field to different values between 1 and 99 and then reset to 1 after reaching 99.
	public String OERModelValue; //NR 14may15 added for user story 875 OER Model entered into the application
	public String OERSerialNoValue; //NR 14may15 added for user story 875 OER Serial # entered into the application
	public String DisinfectantCycleValue; //NR 14may15 added for user story 875 OER Disinfectant Cycles entered into the application, 
	public String DisinfectantDaysValue; //NR 14may15 added for user story 875 OER Disinfectant Days entered into the application, 
	public String WaterFilterCycleValue; //NR 14may15 added for user story 875 OER Water Filter Cycles entered into the application, 
	public String WaterFilterDaysValue; //NR 14may15 added for user story 875 OER Water Filter Days entered into the application, 
	public String AirFilterCycleValue; //NR 14may15 added for user story 875 OER Air Filter Cycles entered into the application, 
	public String AirFilterDaysValue; //NR 14may15 added for user story 875 OER Air Filter Days entered into the application, 
	public String VaporFilterCycleValue; //NR 14may15 added for user story 875 OER Air Filter Cycles entered into the application, 
	public String VaporFilterDaysValue; //NR 14may15 added for user story 875 OER Air Filter Days entered into the application, 
	public String DetergentCycleValue; //NR 14may15 added for user story 875 OER Detergent Cycles entered into the application, 
	public String DetergentDaysValue; //NR 14may15 added for user story 875 OER Detergent Days entered into the application, 
	public String AlcoholCycleValue; //NR 14may15 added for user story 875 OER Alcohol Cycles entered into the application, 
	public String AlcoholDaysValue; //NR 14may15 added for user story 875 OER Alcohol Days entered into the application, 
	public String PMCycleValue; //NR 14may15 added for user story 875 OER Preventive Maintenance (PM) Cycle entered into the application, 
	public String PMDaysValue; //NR 14may15 added for user story 875 OER Preventive Maintenance (PM) Days entered into the application, 
	public String CycleTimeValue; //NR 14may15 added for user story 875 OER Cycle Time entered into the application,
	public int OERLocationDBID; //The DBID for an existing OER Name.
	public int FacilityDB; //Facility DB when LocName==Fac2Exist
	public int UpdatedFacilityDB;
	public int FacilityDB2; //Facility DB when LocName==Fac2Exist
	public int AccessPointDB;

	public String UpdatedLocFacility; // the location facility value
	public long cal = Calendar.getInstance().getTimeInMillis();
	public int calint; //integer counter for cal
	public String calchk; //change calint to a string
	public String Expected;
	public String Vertex;
	public String Edge;
	public String stm;
	
	//These variables define the manual test step for user
	public String Description;

	public static String Result; //Result of a verification point
	public static String GridID; //Grid ID of the row in the Location List that will be edited or verified. 	
	public String ErrCode; //Error Code to be verified.
	
	public String ResultConfirmation; //Result of verifying the confirmation window when changing the location type from Scope Storage Area or Reprocessing Area to something else.
	public String Result_LocType; //Result of verifying the location type is set correctly after a successful save.
	public String Result_LocFacility; //Result of verifying the location facility is set correctly after a successful save.
	public String Result_LocSSID; //Result of verifying the location SSID is set correctly after a successful save.
	public String Result_StorageCabinets; //Result of verifying the location StorageCabinets is set correctly after a successful save.
	public String Result_DisinfectantCycle; //Result of verifying the location DisinfectantCycle is set correctly after a successful save.
	public String Result_DisinfectantDays; //Result of verifying the location DisinfectantDays is set correctly after a successful save.
	public String Result_WaterFilterCycle; //Result of verifying the location WaterFilterCycle is set correctly after a successful save.
	public String Result_WaterFilterDays; //Result of verifying the location WaterFilterDays is set correctly after a successful save.
	public String Result_AirFilterCycle; //Result of verifying the location AirFilterCycle is set correctly after a successful save.
	public String Result_AirFilterDays; //Result of verifying the location AirFilterDays is set correctly after a successful save.
	public String Result_VaporFilterCycle; //Result of verifying the location VaporFilterCycle is set correctly after a successful save.
	public String Result_VaporFilterDays; //Result of verifying the location VaporFilterDays is set correctly after a successful save.
	public String Result_DetergentCycle; //Result of verifying the location DetergentCycle is set correctly after a successful save.
	public String Result_DetergentDays; //Result of verifying the location DetergentDays is set correctly after a successful save.
	public String Result_AlcoholCycle; //Result of verifying the location AlcoholCycle is set correctly after a successful save.
	public String Result_AlcoholDays; //Result of verifying the location AlcoholDays is set correctly after a successful save.
	public String Result_PMCycle; //Result of verifying the location PMCycle is set correctly after a successful save.
	public String Result_PMDays; //Result of verifying the location PMDays is set correctly after a successful save.
	public String Result_CycleTime; //Result of verifying the location CycleTime is set correctly after a successful save.
	public String Result_OERModel; //Result of verifying the location OERModel is set correctly after a successful save.
	public String Result_OERSerialNo; //Result of verifying the location OERSerialNo is set correctly after a successful save.
	public String Result_ReprocessingFields; //Overall results for all the reprocessing fields.
	public ResultSet OER_rs;
	
	public String ExistingAERSerialNoSame,ExistingAERSerialNo,AERModel,OERSerialNumber,ExistingAERModel,error_code;

	public String Flow;//Implements a variable to determine new or modify for expected result.
	public Connection conn= null;
	
	public static String actualResult="\t\t\t Location_TestSummary \n"; 
	public String ForFileName;
	public String TestResFileName="StaffDetails_TestSummary_";
	public boolean startflag=false;
	public TestFrameWork.TestHelper TH;
	public int Scenario=1;
	public boolean ScenarioStartflag=true;
	
	private String LTModel=null; //LeakTestModel
	private String LTModelVal=null; //LeakTestModel value
	private String LTSerNum=null; //LeakTestSerialNumber
	private String LTSerNumVal=null; //LeakTestSerialNumber Value
	private String ResLTModel=null;
	private String ResLTSerNum=null;
	private String LTModelSerNum=null;

	//implementing the edges for the Location Graphml
	
	public void e_New() throws InterruptedException{
		ScenarioStartflag=true;
		Flow="e_New";
		Edge=getCurrentElement().getName();
		Description=Edge+" Click the new row button";
		System.out.println(Description+" Flow = "+Flow);
		SE_LocA.Add_New_Location();
		//System.out.println("Click new row button");
		//Reset all values to zero or blank
		OERModelValue="";
		OERSerialNoValue="";
		ModDisinfectantCycle=0;
		ModDisinfectantDays=0;
		ModWaterFilterCycle=0;
		ModWaterFilterDays=0;
		ModAirFilterCycle=0;
		ModAirFilterDays=0;
		ModVaporFilterCycle=0;
		ModVaporFilterDays=0;
		ModDetergentCycle=0;
		ModDetergentDays=0;
		ModAlcoholCycle=0;
		ModAlcoholDays=0;
		ModPMCycle=0;
		ModPMDays=0;
		ModCycleTime=0;
		ModStorageCabinets=0;
		LTModel="";
		LTModelVal="";
		LTSerNum="";
		LTSerNumVal="";
		LTModelSerNum="";
		Description="Adding new location";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Modify() throws InterruptedException{
		ScenarioStartflag=true;
		Edge=getCurrentElement().getName();
		Description="Modify an existing location.";
		Flow="e_Modify";
		OERModelValue="";
		OERSerialNoValue="";
		LTModel="";
		LTModelVal="";
		LTSerNum="";
		LTSerNumVal="";
		LTModelSerNum="";
		System.out.println(Description+" Flow = "+Flow);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			stm="Select l.idLocation, l.EnteredLocationName, l.LocationType, l.Facility, f.EnteredFacilityName, l.AccessPoint, ap.SSID, l.AERModel, l.AERSerialNo, l.DisinfectantCycles, l.DisinfectantDays, l.WaterFilterCycles, l.WaterFilterDays, l.AirFilterCycles, l.AirFilterDays, l.VaporFilterCycles, l.VaporFilterDays, l.DetergentCycles, l.DetergentDays, l.AlcoholCycles, l.AlcoholDays, l.PMCycles, l.PMDays, l.CycleTime, l.StorageCabinets from location l join facility f on l.Facility=f.idFacility join accesspoint ap on l.AccessPoint=ap.idAccessPoint WHERE l.TestKeyword='Existing' AND LocationType!='Auto Leak Test' AND l.UpdateDate=(Select MIN(l.UpdateDate) from location l Where l.TestKeyword ='Existing' and LocationType!='Auto Leak Test')";
			Location_rs = statement.executeQuery(stm);
			while(Location_rs.next()){
				ModifyLocationDB= Location_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//ModLocNameBaseValue= Location_rs.getString(2); //the second variable is the location base name
				ModifyLocNameValue= Location_rs.getString(2); //the third variable is the Entered location name
				//UpdatedLocNameValue= Location_rs.getString(3); //the forth variable is the concatenated location name
				ModLocType= Location_rs.getString(3); //the fifth variable is the Location's Facility Association
				ModifyFacilityDB= Location_rs.getInt(4); //the first variable in the set is the ID row in the database.
				ModLocFacility= Location_rs.getString(5); //the fifth variable is the Location's Type
				ModifySSIDDB= Location_rs.getInt(6); //the first variable in the set is the ID row in the database.
				ModLocSSID=Location_rs.getString(7);
				ModOERModel=Location_rs.getString(8);
				ModOERSerialNo=Location_rs.getString(9);
				ModDisinfectantCycle= Location_rs.getInt(10);
				ModDisinfectantDays= Location_rs.getInt(11);
				ModWaterFilterCycle= Location_rs.getInt(12);
				ModWaterFilterDays= Location_rs.getInt(13);
				ModAirFilterCycle= Location_rs.getInt(14);
				ModAirFilterDays= Location_rs.getInt(15);
				ModVaporFilterCycle= Location_rs.getInt(16);
				ModVaporFilterDays= Location_rs.getInt(17);
				ModDetergentCycle= Location_rs.getInt(18);
				ModDetergentDays= Location_rs.getInt(19);
				ModAlcoholCycle= Location_rs.getInt(20);
				ModAlcoholDays= Location_rs.getInt(21);
				ModPMCycle= Location_rs.getInt(22);
				ModPMDays= Location_rs.getInt(23);
				ModCycleTime= Location_rs.getInt(24);
				ModStorageCabinets=Location_rs.getInt(25); //NR 14may15 added for user story 918

			}
			Location_rs.close();
			/**System.out.println("ModifyLocationDB = "+ModifyLocationDB);
			//System.out.println("ModLocNameBaseValue = "+ModLocNameBaseValue);	
			System.out.println("ModifyLocNameValue = "+ModifyLocNameValue);	
			//System.out.println("UpdatedLocNameValue = "+UpdatedLocNameValue);	
			System.out.println("ModLocType = "+ModLocType);
			System.out.println("ModifyFacilityDB = "+ModifyFacilityDB);
			System.out.println("ModLocFacility = "+ModLocFacility);
			System.out.println("ModifySSIDDB = "+ModifySSIDDB);
			System.out.println("ModLocSSID = "+ModLocSSID);
			System.out.println("ModOERModel = "+ModOERModel);
			System.out.println("ModOERSerialNo = "+ModOERSerialNo);
			System.out.println("ModDisinfectantCycle = "+ModDisinfectantCycle);
			System.out.println("ModDisinfectantDays = "+ModDisinfectantDays);
			System.out.println("ModWaterFilterCycle = "+ModWaterFilterCycle);
			System.out.println("ModWaterFilterDays = "+ModWaterFilterDays);
			System.out.println("ModAirFilterCycle = "+ModAirFilterCycle);
			System.out.println("ModAirFilterDays = "+ModAirFilterDays);
			System.out.println("ModVaporFilterCycle = "+ModVaporFilterCycle);
			System.out.println("ModDetergentCycle = "+ModDetergentCycle);
			System.out.println("ModDetergentDays = "+ModDetergentDays);
			System.out.println("ModAlcoholCycle = "+ModAlcoholCycle);
			System.out.println("ModAlcoholDays = "+ModAlcoholDays);
			System.out.println("ModPMCycle = "+ModPMCycle);
			System.out.println("ModPMDays = "+ModPMDays);
			System.out.println("ModCycleTime = "+ModCycleTime);
			System.out.println("ModStorageCabinets = "+ModStorageCabinets);**/

		
			stm="Select idFacility, EnteredFacilityName from facility where EnteredFacilityName!='"+ModLocFacility+"' and TestScenario='Location' and Active='True'";
			Location_rs = statement.executeQuery(stm); //Get a facility name not equal to the Facility currently associated to the location being modified.
			while(Location_rs.next()){
				UpdatedFacilityDB= Location_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("UpdatedFacilityDB = "+UpdatedFacilityDB);
				UpdatedLocFacility= Location_rs.getString(2); //the second variable is the entered location name
				//System.out.println("UpdatedLocFacility = "+UpdatedLocFacility);
			}
			Location_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			
			SE_LocA.Search_Location_ByName(ModifyLocNameValue);  //Search for the Location Name to be modified in the Application.
			System.out.println("Search for Location to be modified which equals "+ModifyLocNameValue);	
			SE_LocA.Search_Location_ByFacility(ModLocFacility);
			System.out.println("Search for Facility name assocated with Location to be modified which equals "+ModLocFacility);	

			GridID=SE_LocA.GetGridID_Location_To_Modify(ModifyLocNameValue); // Get the GridID of the location to be modified.
			System.out.println("GridID = "+GridID);
			SE_LocA.Select_Location_To_Modify(ModifyLocNameValue); //Select the row of the location to be modified.
			//System.out.println(Edge+" Location "+ModifyLocNameValue+" is selected");
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	

	
	public void e_Fac1Exist(){
		Edge=getCurrentElement().getName();
		LocName="Fac1Exist";
		Description="Enter an existing location name with the same facility ";
		try{ // get a location name that already exists in the application.
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			Statement update= conn.createStatement();
			if (Flow.equalsIgnoreCase("e_New")){ // get a location name that already exists in the application.
				stm="Select l.idLocation, l.EnteredLocationName, l.Facility, f.EnteredFacilityName from location l join facility f on l.Facility=f.idFacility WHERE l.TestKeyword='Existing' AND l.UpdateDate=(Select MIN(UpdateDate) from location Where TestKeyword ='Existing')";
				Location_rs = statement.executeQuery(stm);// get a location name that already exists in the application that is not the current name of the location being modified.
			} else if(Flow.equalsIgnoreCase("e_Modify")){
				stm="Select l.idLocation, l.EnteredLocationName, l.Facility, f.EnteredFacilityName from location l join facility f on l.Facility=f.idFacility WHERE l.EnteredLocationName!='"+ModifyLocNameValue+"' AND l.TestKeyword='Existing' AND l.UpdateDate=(Select MIN(l.UpdateDate) from location l Where  l.EnteredLocationName!='"+ModifyLocNameValue+"' AND l.TestKeyword ='Existing')";
				Location_rs = statement.executeQuery(stm);
			}
			while(Location_rs.next()){
				LocationDB= Location_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("LocationDB = "+LocationDB);
				LocNameValue= Location_rs.getString(2); //the second variable is the entered location name
				System.out.println("LocNameValue = "+LocNameValue);
				FacilityDB= Location_rs.getInt(3); //the first variable in the set is the ID row in the database.
				//System.out.println("FacilityDB = "+FacilityDB);
				LocFacilityValue1=Location_rs.getString(4); //the fourth variable is the facility name associated with an existing location name
				System.out.println("LocFacilityValue1 = "+LocFacilityValue1);

			}
			Location_rs.close();
			//LocNameBaseValue=LocNameValue;
			//statement.close(); //close the query to get the variable information from the DB
			update.executeUpdate("Update location SET UpdateDate=CURRENT_TIMESTAMP WHERE idLocation="+LocationDB); // update the UpdateDate variable of the row of data used to the current date/time stamp.
			update.close();

			conn.close();
			SE_LocA.Enter_Location_Name(LocNameValue);
			System.out.println(Edge+", Flow="+Flow+"; LocName="+LocName+"; Location Name = "+LocNameValue);
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		} 
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Valid(){
		Edge=getCurrentElement().getName();
		Description="Enter a Valid Location Name";
		LocName="Valid";
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
		  calint=0;
		  calchk="0";
		}
		LocNameValue="Loc"+cal+calchk;
		System.out.println("LocNameValue="+LocNameValue);
		if(Flow.equalsIgnoreCase("e_New")){  //If the path is new, Click the Add new row icon and enter the Role name generated from the Test Database.
			try{
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();

				
				stm="Select idFacility, EnteredFacilityName from facility where TestScenario='Location' and Active='True'";
				Location_rs = statement.executeQuery(stm); //Get an active facility name to associate the new location with.
				while(Location_rs.next()){
					FacilityDB= Location_rs.getInt(1); //the first variable in the set is the ID row in the database.
					//System.out.println("FacilityDB = "+FacilityDB);
					LocFacilityValue= Location_rs.getString(2); //the second variable is the entered location name
					//System.out.println("LocFacilityValue = "+LocFacilityValue);
				}
				Location_rs.close();
				statement.close(); //close the query to get the variable information from the DB
				
				conn.close();
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());	
			}
			SE_LocA.Enter_Location_Name(LocNameValue);
			System.out.println(Edge+", Flow="+Flow+"; LocName="+LocName+"; Location Name = "+LocNameValue);
		} else if(Flow.equalsIgnoreCase("e_Modify")){ //If the path is Modify, set the LocNameValue to the Updated Location name and enter that value into the Name field. 
			//LocNameValue=UpdatedLocNameValue;
			//System.out.println("Valid, Flow="+Flow+"; LocName="+LocName+";  Original Location Name = "+ModifyLocNameValue+" ; Grid ID="+GridID+"; New Location Name="+LocNameValue);
			SE_LocA.Enter_Location_Name(LocNameValue); //modify the selected Location name
			System.out.println(Edge+", Flow="+Flow+"; LocName="+LocName+";  Location Name = "+LocNameValue);
		} 
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Null(){
		Edge=getCurrentElement().getName();
		LocName="";
		Description="Enter a blank Location Name";

		LocNameValue=""; //Set the location name value to an empty string 
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			stm="Select idFacility, EnteredFacilityName from facility where TestScenario='Location' and Active='True'";
			Location_rs = statement.executeQuery(stm); //Get an active facility name to associate the new location with.
			while(Location_rs.next()){
				FacilityDB= Location_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("FacilityDB = "+FacilityDB);
				LocFacilityValue= Location_rs.getString(2); //the second variable is the entered location name
				//System.out.println("LocFacilityValue = "+LocFacilityValue);
			}
			Location_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, Click the Add new row icon and enter the location name generated from the graph.
			SE_LocA.Enter_Location_Name(LocNameValue);
			System.out.println("e_Null, Flow="+Flow+"; LocName="+LocName+"; Location Name = "+LocNameValue);
		} else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is Modify, set the LocNameValue to the Location name value in the graph and enter that value into the Name field of the Location to be modified.
			SE_LocA.Enter_Location_Name(LocNameValue);
			System.out.println(Edge+"; Flow="+Flow+"; LocName="+LocName+"; Location Name = "+LocNameValue);
		} 
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Same(){
		//do nothing, the name is to remain the same. NOTE: this will only occur when Flow==Modify.
		Edge=getCurrentElement().getName();
		LocName="Same";
		Description="keep the Location Name the same";

		LocNameValue=ModifyLocNameValue;
		System.out.println(Edge+ "LocName="+LocName+"; LocNameValue="+LocNameValue+" ; Flow="+Flow);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_Start(){
		//empty edge to start graph
	}

	public void e_Reset1(){
		ScenarioStartflag=true;
		//edge to reset variables
		DisinfectantCycle="not used";
		DisinfectantDays="not used";
		WaterFilterCycle="not used";
		WaterFilterDays="not used";
		AirFilterCycle="not used";
		AirFilterDays="not used";
		VaporFilterCycle="not used";
		VaporFilterDays="not used";
		DetergentCycle="not used";
		DetergentDays="not used";
		AlcoholCycle="not used";
		AlcoholDays="not used";
		PMCycle="not used";
		PMDays="not used";
		CycleTime="not used";
		Cabinets="not used";
		Description="Reseting variable values";
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}

	public void e_Reset2(){
		//empty edge to reset variables 
		System.out.println(getCurrentElement().getName());
	}

	public void e_Reset3(){
		//empty edge to reset variables 
		System.out.println(getCurrentElement().getName());
	}

	public void e_Navigate(){
		//empty edge to reset variables 
		System.out.println(getCurrentElement().getName());
	}
	public void e_Navigate2(){
		//empty edge to reset variables 
		System.out.println(getCurrentElement().getName());
	}
	
	public void e_SavePass() {
		//empty edge to start graph
		//Flow=Integer.valueOf(getMbt().getDataValue("Flow")); //Get the value of Flow from the graph (new or modify)
		Edge=getCurrentElement().getName();
		System.out.println(Edge+" go back to start. Flow = "+Flow);
		Description="go back to start. Flow = "+Flow;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	

	
	public void e_SameLocType() { 
		Edge=getCurrentElement().getName();
		LocType=ModLocType; //If the path is modify and LocType is set to 'Same' do nothing and set the LocType to ModLocType
		
		if(LocType.equalsIgnoreCase("Scope Storage Area")){			
			StorageCabinetsValue=""+ModStorageCabinets+"";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){
			OERModelValue=ModOERModel;
			OERSerialNoValue=ModOERSerialNo;
			if(ModDisinfectantCycle!=0){
				DisinfectantCycleValue=""+ModDisinfectantCycle+"";
			}else {
				DisinfectantCycleValue="";				
			}
			if(ModDisinfectantDays!=0){
				DisinfectantDaysValue=""+ModDisinfectantDays+"";
			}else {
				DisinfectantDaysValue="";				
			}			
			if(ModWaterFilterCycle!=0){
				WaterFilterCycleValue=""+ModWaterFilterCycle+"";
			}else {
				WaterFilterCycleValue="";				
			}
			if(ModWaterFilterDays!=0){
				WaterFilterDaysValue=""+ModWaterFilterDays+"";
			}else {
				WaterFilterDaysValue="";				
			}
			if(ModAirFilterCycle!=0){
				AirFilterCycleValue=""+ModAirFilterCycle+"";
			}else {
				AirFilterCycleValue="";				
			}
			if(ModAirFilterDays!=0){
				AirFilterDaysValue=""+ModAirFilterDays+"";
			}else {
				AirFilterDaysValue="";				
			}
			if(ModVaporFilterCycle!=0){
				VaporFilterCycleValue=""+ModVaporFilterCycle+"";
			}else {
				VaporFilterCycleValue="";				
			}
			if(ModVaporFilterDays!=0){
				VaporFilterDaysValue=""+ModVaporFilterDays+"";
			}else {
				VaporFilterDaysValue="";				
			}
			if(ModDetergentCycle!=0){
				DetergentCycleValue=""+ModDetergentCycle+"";
			}else {
				DetergentCycleValue="";				
			}
			if(ModDetergentDays!=0){
				DetergentDaysValue=""+ModDetergentDays+"";
			}else {
				DetergentDaysValue="";				
			}
			if(ModAlcoholCycle!=0){
				AlcoholCycleValue=""+ModAlcoholCycle+"";
			}else {
				AlcoholCycleValue="";				
			}
			if(ModAlcoholDays!=0){
				AlcoholDaysValue=""+ModAlcoholDays+"";
			}else {
				AlcoholDaysValue="";				
			}
			if(ModPMCycle!=0){
				PMCycleValue=""+ModPMCycle+"";
			}else {
				PMCycleValue="";				
			}
			if(ModPMDays!=0){
				PMDaysValue=""+ModPMDays+"";
			}else {
				PMDaysValue="";				
			}
			if(ModCycleTime!=0){
				CycleTimeValue=""+ModCycleTime+"";
			}else {
				CycleTimeValue="";				
			}

		}
		
		//SE_LocA.Selct_Location_Type(LocType);

		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaitingRoom() throws  InterruptedException{ 
		Edge=getCurrentElement().getName();
		LocType="Waiting Room";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
			SE_LocA.Selct_Location_Type( LocType);
			System.out.println("LocType="+LocType);
			if(ModLocType.equalsIgnoreCase("Reprocessor")){
				//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
				if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
					//if all the values are empty the confirmation window will not be displayed. 
				}else {
					SE_LocA.Confirmation_Window("Yes");
					OERModelValue="";
					OERSerialNoValue="";
					ModDisinfectantCycle=0;
					ModDisinfectantDays=0;
					ModWaterFilterCycle=0;
					ModWaterFilterDays=0;
					ModAirFilterCycle=0;
					ModAirFilterDays=0;
					ModVaporFilterCycle=0;
					ModVaporFilterDays=0;
					ModDetergentCycle=0;
					ModDetergentDays=0;
					ModAlcoholCycle=0;
					ModAlcoholDays=0;
					ModPMCycle=0;
					ModPMDays=0;
					ModCycleTime=0;

				}
			} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
				//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
				/**ResultConfirmation=SE_LocV.Verify_Confirmation();
				Description= Edge+": The Confirmation Window verification is "+ResultConfirmation+".";
				Expected="Confirmation window is displayed with correct text";
				System.out.println(Expected);
				TestFrameWork.TestHelper.StepWriter1(Edge, Description, Expected, ResultConfirmation);**/
				SE_LocA.Confirmation_Window("Yes");
				ModStorageCabinets=0;
			}
		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ProcedureRoom() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Procedure Room";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;
				}

		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_BioburdenTesting() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Bioburden Testing";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Scope Testing, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Scope Testing, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;
				}

		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_CultureTesting() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Culturing";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Scope Testing, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Scope Testing, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;
				}

		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_ScopeStorage() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Scope Storage Area";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} /* else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
				}*/
		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_ReprocessingRoom() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Reprocessing Room";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;
				}

		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LocTypeNull() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;
				}
		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_SoiledArea() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Soiled Area";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;
				}
		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Reprocessing() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Reprocessor";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
			SE_LocA.Selct_Location_Type( LocType);
			System.out.println("LocType="+LocType);
			if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
				//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
				ModStorageCabinets=0;
				SE_LocA.Confirmation_Window("Yes");	
			}
		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_TravelCart() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Travel Cart";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;
				}
		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Admin() throws InterruptedException { 
		Edge=getCurrentElement().getName();
		LocType="Administration";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e.Administration, Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e.Administration, Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;

				}
		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	public void e_LocFacilityFacName() throws  InterruptedException{
		Edge=getCurrentElement().getName();
		LocFacility= "FacName";
		if (LocName.equalsIgnoreCase("Fac1Exist")){
			LocFacilityValue=LocFacilityValue1;
			FacilityDBValue=FacilityDB;
		} else {
			if(Flow.equalsIgnoreCase("e_New")){
				FacilityDBValue=FacilityDB;
				LocFacilityValue=LocFacilityValue;
			} else if(Flow.equalsIgnoreCase("e_Modify")){
				LocFacilityValue=UpdatedLocFacility;
				FacilityDBValue=UpdatedFacilityDB;
			}
		}

		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			if(LocFacility.equalsIgnoreCase("Same")){
				//do nothing keep the current value of the location being modified.
				FacilityDBValue=ModifyFacilityDB;
				LocFacilityValue=ModLocFacility;
			} else {
				SE_LocA.Selct_Location_Facility(LocFacilityValue);
			}
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
			if(LocFacility.equalsIgnoreCase("Same")){
				//do nothing keep the current value of the location being modified.
				FacilityDBValue=ModifyFacilityDB;
				LocFacilityValue=ModLocFacility;
			} else {
				SE_LocA.Selct_Location_Facility( LocFacilityValue);
				System.out.println("LocFacilityValue="+LocFacilityValue);
			}

		}
		Description="The user selects "+LocFacilityValue+" in the Facility field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_LocFacilityNull() throws  InterruptedException{
		Edge=getCurrentElement().getName();
		LocFacility="";

		LocFacilityValue=LocFacility;
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
				SE_LocA.Selct_Location_Facility(LocFacilityValue);

		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Facility(LocFacilityValue);
		}
		Description="The user selects "+LocFacilityValue+" in the Facility field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_LocFacilitySame() throws  InterruptedException{
		Edge=getCurrentElement().getName();
		LocFacility="Same";
		LocFacilityValue=ModLocFacility;
		FacilityDBValue=ModifyFacilityDB;
		//do nothing keep the current value of the location being modified.

		Description="The user selects "+LocFacilityValue+" in the Facility field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	

	
	public void e_LocActiveTrue() {
		Edge=getCurrentElement().getName();
		LocActive ="True";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Active checkbox as per the graph (i.e. true or false) for the new location field. 
			SE_LocA.Selct_New_Location_Active(LocActive);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the active checkbox for the row being modified as per the graph (i.e. true or false)
			ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
			SE_LocA.Modify_Location_Active( ModLocAct_Val,LocActive);
			System.out.println("The Active value was set to "+ModLocAct_Val+" LocActive="+LocActive);
		}
		
		Description="The user set the active flag for Location as:  "+LocActive;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LocActiveFalse() {
		Edge=getCurrentElement().getName();
		LocActive ="False";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Active checkbox as per the graph (i.e. true or false) for the new location field. 
			SE_LocA.Selct_New_Location_Active(LocActive);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the active checkbox for the row being modified as per the graph (i.e. true or false)
			ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
			SE_LocA.Modify_Location_Active( ModLocAct_Val,LocActive);
			System.out.println("The Active value was set to "+ModLocAct_Val+" LocActive="+LocActive);
		}
		
		Description= "The user set the active flag for Location as:  "+LocActive;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void e_LocSSIDNull() throws  InterruptedException{
		Edge=getCurrentElement().getName();
		LocSSID="";

		LocSSIDValue=LocSSID;
		SE_LocA.Selct_Location_SSID(LocSSIDValue);
		
		/**if(Flow.equalsIgnoreCase("e_New")){ 
				

		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_SSID( LocSSIDValue);
		}**/
		Description="The user selects "+LocSSIDValue+" in the Access Point field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	public void e_LocSSIDValid() throws  InterruptedException{
		Edge=getCurrentElement().getName();
		LocSSID="Valid";
		//Update sql query to look at access point table and get an active SSID from that table.  
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			if(Flow.equalsIgnoreCase("e_New")){
				stm="Select idAccessPoint, SSID from accesspoint where TestKeyword='Location' and UpdateDate=(Select MIN(UpdateDate) from accesspoint Where TestKeyword ='Location') ";
			}else if(Flow.equalsIgnoreCase("e_Modify")){
				stm="Select idAccessPoint, SSID from accesspoint where idAccessPoint!="+ModifySSIDDB+" and TestKeyword='Location' and UpdateDate=(Select MIN(UpdateDate) from accesspoint Where idAccessPoint!="+ModifySSIDDB+" and TestKeyword ='Location') ";

			}
			//stm="Select idLocation, AccessPoint from location where TestKeyword='Valid'";
			Location_rs = statement.executeQuery(stm); //Get an active facility name to associate the new location with.
			while(Location_rs.next()){
				AccessPointDB= Location_rs.getInt(1); //the first variable in the set is the ID row in the database.
				System.out.println("AccessPointDB = "+AccessPointDB);
				LocSSIDValue= Location_rs.getString(2); //the second variable is the entered SSID
				System.out.println("LocSSIDValue = "+LocSSIDValue);
			}
			Location_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}

		SE_LocA.Selct_Location_SSID(LocSSIDValue);
			
		/**if(Flow.equalsIgnoreCase("e_New")){ 
				SE_LocA.Selct_Location_SSID(LocSSIDValue);

		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_SSID( LocSSIDValue);
		}**/
		Description="The user selects "+LocSSIDValue+" in the Access Point field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		
	}
	
	public void e_CabinetsSame() throws InterruptedException{
		Edge=getCurrentElement().getName();
		//NR 14may15 added for user story 918
		Cabinets="Same";
		if(ModStorageCabinets!=0){
			StorageCabinetsValue=""+ModStorageCabinets+"";
			//SE_LocA.Enter_StorageCabinets(StorageCabinetsValue);
			Description="leave the Storage Cabinets field the same";
		} else {
			if(StorageCabinetsCounter<100){
				StorageCabinetsCounter++; //If it's less than 100 increase the value by one
			} else {
				StorageCabinetsCounter=1; //If it's 100 or greater then reset it back to 1 
			}
			StorageCabinetsValue=""+StorageCabinetsCounter+"";
			ModStorageCabinets=StorageCabinetsCounter;
			SE_LocA.Enter_StorageCabinets(StorageCabinetsValue);
			Description="Storage Cabinets field should be the same, but the previous value was null, so setting StorageCabinetsValue to "+StorageCabinetsValue;
		}
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CabinetsValid() throws InterruptedException{
		//NR 14may15 added for user story 918
		Edge=getCurrentElement().getName();
		Cabinets="Valid";
		if(StorageCabinetsCounter<100){
			StorageCabinetsCounter++; //If it's less than 100 increase the value by one
		} else {
			StorageCabinetsCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		StorageCabinetsValue=""+StorageCabinetsCounter+"";
		ModStorageCabinets=StorageCabinetsCounter;

		SE_LocA.Enter_StorageCabinets(StorageCabinetsValue);
		Description="StorageCabinetsValue="+StorageCabinetsValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_CabinetsNegative() throws InterruptedException{
		//NR 14may15 added for user story 918
		Edge=getCurrentElement().getName();
		Cabinets="negative";
		StorageCabinetsValue="-1";
		SE_LocA.Enter_StorageCabinets(StorageCabinetsValue);
		Description="StorageCabinetsValue="+StorageCabinetsValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_Cabinets0() throws InterruptedException{
		//NR 14may15 added for user story 918
		Edge=getCurrentElement().getName();
		Cabinets="zero";
		StorageCabinetsValue="0";
		SE_LocA.Enter_StorageCabinets(StorageCabinetsValue);
		Description="StorageCabinetsValue="+StorageCabinetsValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CabinetsAlpha() throws InterruptedException{
		//NR 14may15 added for user story 918
		Edge=getCurrentElement().getName();
		Cabinets="alpha";
		StorageCabinetsValue="ab";
		SE_LocA.Enter_StorageCabinets(StorageCabinetsValue);
		Description="StorageCabinetsValue="+StorageCabinetsValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CabinetsSpecialChar() throws InterruptedException{
		//NR 14may15 added for user story 918
		Edge=getCurrentElement().getName();
		Cabinets="special";
		StorageCabinetsValue="$";
		SE_LocA.Enter_StorageCabinets(StorageCabinetsValue);
		Description="StorageCabinetsValue="+StorageCabinetsValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CabinetsNull() throws InterruptedException{
		//NR 14may15 added for user story 918
		Edge=getCurrentElement().getName();
		Cabinets="";
		StorageCabinetsValue="";
		SE_LocA.Enter_StorageCabinets(StorageCabinetsValue);
		Description="StorageCabinetsValue="+StorageCabinetsValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
/**	public void e_Cabinets100() throws InterruptedException{
		//NR 14may15 added for user story 918
		Edge=getCurrentElement().getName();
		Cabinets="100";
		StorageCabinetsValue="100";
		Description=Edge+" - StorageCabinetsValue="+StorageCabinetsValue;
		System.out.println(Description);
	}**/
	
	public void e_OERSerialNoSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		OERSerialNo="Same";
		
		OERSerialNoValue=ModOERSerialNo;
		Description="leave the OER Serial Number field the same";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_OERSerialNoValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		OERSerialNo="Valid";
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		OERSerialNoValue="SNO"+cal+calchk;
		SE_LocA.Enter_AERSerialNo(OERSerialNoValue);
		Description="OERSerialNoValue="+OERSerialNoValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_OERSerialNoNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		OERSerialNo="";
		OERSerialNoValue="";
		SE_LocA.Enter_AERSerialNo(OERSerialNoValue);
		Description="OERSerialNoValue="+OERSerialNoValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_OERModelSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		OERModel="Same";
		OERModelValue=ModOERModel;
		Description="leave the OER Model field the same";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_OERModelValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		OERModel="Valid";
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		OERModelValue="Model"+cal+calchk;
		SE_LocA.Enter_AERModel(OERModelValue);
		Description="OERModelValue="+OERModelValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_OERModelNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		OERModel="";
		OERModelValue="";
		SE_LocA.Enter_AERModel(OERModelValue);
		Description="OERModelValue="+OERModelValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_DisinfectantCycleSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantCycle="Same";
		if(ModDisinfectantCycle!=0){
			DisinfectantCycleValue=""+ModDisinfectantCycle+"";
		}else {
			DisinfectantCycleValue="";
			
		}
		Description="leave the OER Disinfectant Cycle field the same. DisinfectantCycleValue="+DisinfectantCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantCycleValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantCycle="Valid";
		if(DisinfectantCycleCounter<100){
			DisinfectantCycleCounter++; //If it's less than 100 increase the value by one
		} else {
			DisinfectantCycleCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModDisinfectantCycle=DisinfectantCycleCounter;
		DisinfectantCycleValue=""+DisinfectantCycleCounter+"";
		SE_LocA.Enter_DisinfectantCycles(DisinfectantCycleValue);
		Description="DisinfectantCycleValue="+DisinfectantCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_DisinfectantCycleNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantCycle="";
		ModDisinfectantCycle=0;
		DisinfectantCycleValue="";
		SE_LocA.Enter_DisinfectantCycles(DisinfectantCycleValue);
		Description="DisinfectantCycleValue="+DisinfectantCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantCycleNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantCycle="negative";
		DisinfectantCycleValue="-1";
		SE_LocA.Enter_DisinfectantCycles(DisinfectantCycleValue);
		Description="DisinfectantCycleValue="+DisinfectantCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	public void e_DisinfectantCycleZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantCycle="zero";
		DisinfectantCycleValue="0";
		SE_LocA.Enter_DisinfectantCycles(DisinfectantCycleValue);
		Description="DisinfectantCycleValue="+DisinfectantCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantCycleAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantCycle="alpha";
		DisinfectantCycleValue="ab";
		SE_LocA.Enter_DisinfectantCycles(DisinfectantCycleValue);
		Description="DisinfectantCycleValue="+DisinfectantCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantCycleSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantCycle="special";
		DisinfectantCycleValue="$";
		SE_LocA.Enter_DisinfectantCycles(DisinfectantCycleValue);
		Description="DisinfectantCycleValue="+DisinfectantCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantDaysSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantDays="Same";
		if(ModDisinfectantDays!=0){
			DisinfectantDaysValue=""+ModDisinfectantDays+"";
		}else {
			DisinfectantDaysValue="";
		}

		Description="leave the OER Disinfectant Days field the same. DisinfectantDaysValue="+DisinfectantDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantDaysValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantDays="Valid";
		if(DisinfectantDaysCounter<100){
			DisinfectantDaysCounter++; //If it's less than 100 increase the value by one
		} else {
			DisinfectantDaysCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModDisinfectantDays=DisinfectantDaysCounter;
		DisinfectantDaysValue=""+DisinfectantDaysCounter+"";
		SE_LocA.Enter_DisinfectantDays(DisinfectantDaysValue);
		Description="DisinfectantDaysValue="+DisinfectantDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantDaysNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantDays="";
		DisinfectantDaysValue="";
		ModDisinfectantDays=0;
		SE_LocA.Enter_DisinfectantDays(DisinfectantDaysValue);
		Description="DisinfectantDaysValue="+DisinfectantDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantDaysNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantDays="negative";
		DisinfectantDaysValue="-1";
		SE_LocA.Enter_DisinfectantDays(DisinfectantDaysValue);
		Description="DisinfectantDaysValue="+DisinfectantDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantDaysZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantDays="zero";
		DisinfectantDaysValue="0";
		SE_LocA.Enter_DisinfectantDays(DisinfectantDaysValue);
		Description="DisinfectantDaysValue="+DisinfectantDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantDaysAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantDays="alpha";
		DisinfectantDaysValue="ab";
		SE_LocA.Enter_DisinfectantDays(DisinfectantDaysValue);
		Description="DisinfectantDaysValue="+DisinfectantDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DisinfectantDaysSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DisinfectantDays="special";
		DisinfectantDaysValue="$";
		SE_LocA.Enter_DisinfectantDays(DisinfectantDaysValue);
		Description="DisinfectantDaysValue="+DisinfectantDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterCycleSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterCycle="Same";
		if(ModWaterFilterCycle!=0){
			WaterFilterCycleValue=""+ModWaterFilterCycle+""; 
		}else {
			WaterFilterCycleValue="";
		}
		Description="leave the OER Water Filter Cycles field the same. WaterFilterCycleValue="+WaterFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterCycleValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterCycle="Valid";
		if(WaterFilterCycleCounter<100){
			WaterFilterCycleCounter++; //If it's less than 100 increase the value by one
		} else {
			WaterFilterCycleCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModWaterFilterCycle=WaterFilterCycleCounter;
		WaterFilterCycleValue=""+WaterFilterCycleCounter+"";
		SE_LocA.Enter_WaterFilterCycles(WaterFilterCycleValue);
		Description="WaterFilterCycleValue="+WaterFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_WaterFilterCycleNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterCycle="";
		ModWaterFilterCycle=0;
		WaterFilterCycleValue="";
		SE_LocA.Enter_WaterFilterCycles(WaterFilterCycleValue);
		Description="WaterFilterCycleValue="+WaterFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterCycleNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterCycle="negative";
		WaterFilterCycleValue="-1";
		SE_LocA.Enter_WaterFilterCycles(WaterFilterCycleValue);
		Description="WaterFilterCycleValue="+WaterFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterCycleZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterCycle="zero";
		WaterFilterCycleValue="0";
		SE_LocA.Enter_WaterFilterCycles(WaterFilterCycleValue);
		Description="WaterFilterCycleValue="+WaterFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
				
	public void e_WaterFilterCycleAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterCycle="alpha";
		WaterFilterCycleValue="ab";
		SE_LocA.Enter_WaterFilterCycles(WaterFilterCycleValue);
		Description="WaterFilterCycleValue="+WaterFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterCycleSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterCycle="special";
		WaterFilterCycleValue="$";
		SE_LocA.Enter_WaterFilterCycles(WaterFilterCycleValue);
		Description="WaterFilterCycleValue="+WaterFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterDaysSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterDays="Same";
		if(ModWaterFilterDays!=0){
			WaterFilterDaysValue=""+ModWaterFilterDays+"";
		}else {
			WaterFilterDaysValue="";
		}
		Description="leave the OER Water Filter Days field the same. WaterFilterDaysValue="+WaterFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterDaysValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterDays="Valid";
		if(WaterFilterDaysCounter<100){
			WaterFilterDaysCounter++; //If it's less than 100 increase the value by one
		} else {
			WaterFilterDaysCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModWaterFilterDays=WaterFilterDaysCounter;
		WaterFilterDaysValue=""+WaterFilterDaysCounter+"";
		SE_LocA.Enter_WaterFilterDays(WaterFilterDaysValue);
		Description="WaterFilterDaysValue="+WaterFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_WaterFilterDaysNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterDays="";
		ModWaterFilterDays=0;
		WaterFilterDaysValue="";
		SE_LocA.Enter_WaterFilterDays(WaterFilterDaysValue);
		Description="WaterFilterDaysValue="+WaterFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterDaysNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterDays="negative";
		WaterFilterDaysValue="-1";
		SE_LocA.Enter_WaterFilterDays(WaterFilterDaysValue);
		Description="WaterFilterDaysValue="+WaterFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterDaysZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterDays="zero";
		WaterFilterDaysValue="0";
		SE_LocA.Enter_WaterFilterDays(WaterFilterDaysValue);
		Description="WaterFilterDaysValue="+WaterFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterDaysAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterDays="alpha";
		WaterFilterDaysValue="ab";
		SE_LocA.Enter_WaterFilterDays(WaterFilterDaysValue);
		Description="WaterFilterDaysValue="+WaterFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_WaterFilterDaysSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		WaterFilterDays="special";
		WaterFilterDaysValue="$";
		SE_LocA.Enter_WaterFilterDays(WaterFilterDaysValue);
		Description="WaterFilterDaysValue="+WaterFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterCycleSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterCycle="Same";
		if(ModAirFilterCycle!=0){
			AirFilterCycleValue=""+ModAirFilterCycle+""; 
		}else {
			AirFilterCycleValue="";
		}
		Description="leave the OER Air Filter Cycles field the same. AirFilterCycleValue="+AirFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterCycleValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterCycle="Valid";
		if(AirFilterCycleCounter<100){
			AirFilterCycleCounter++; //If it's less than 100 increase the value by one
		} else {
			AirFilterCycleCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModAirFilterCycle=AirFilterCycleCounter;
		AirFilterCycleValue=""+AirFilterCycleCounter+"";
		SE_LocA.Enter_AirFilterCycles(AirFilterCycleValue);
		Description="AirFilterCycleValue="+AirFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_AirFilterCycleNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterCycle="";
		ModAirFilterCycle=0;
		AirFilterCycleValue="";
		SE_LocA.Enter_AirFilterCycles(AirFilterCycleValue);
		Description="AirFilterCycleValue="+AirFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterCycleNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterCycle="negative";
		AirFilterCycleValue="-1";
		SE_LocA.Enter_AirFilterCycles(AirFilterCycleValue);
		Description="AirFilterCycleValue="+AirFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterCycleZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterCycle="zero";
		AirFilterCycleValue="0";
		SE_LocA.Enter_AirFilterCycles(AirFilterCycleValue);
		Description="AirFilterCycleValue="+AirFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterCycleAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterCycle="alpha";
		AirFilterCycleValue="ab";
		SE_LocA.Enter_AirFilterCycles(AirFilterCycleValue);
		Description="AirFilterCycleValue="+AirFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterCycleSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterCycle="special";
		AirFilterCycleValue="$";
		SE_LocA.Enter_AirFilterCycles(AirFilterCycleValue);
		Description="AirFilterCycleValue="+AirFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterDaysSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterDays="Same";
		if(ModAirFilterDays!=0){
			AirFilterDaysValue=""+ModAirFilterDays+"";
		}else {
			AirFilterDaysValue="";
		}
		Description="leave the OER Air Filter Days field the same. AirFilterDaysValue="+AirFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterDaysValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterDays="Valid";
		if(AirFilterDaysCounter<100){
			AirFilterDaysCounter++; //If it's less than 100 increase the value by one
		} else {
			AirFilterDaysCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModAirFilterDays=AirFilterDaysCounter;
		AirFilterDaysValue=""+AirFilterDaysCounter+"";
		SE_LocA.Enter_AirFilterDays(AirFilterDaysValue);
		Description="AirFilterDaysValue="+AirFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterDaysNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterDays="";
		ModAirFilterDays=0;
		AirFilterDaysValue="";
		SE_LocA.Enter_AirFilterDays(AirFilterDaysValue);
		Description="AirFilterDaysValue="+AirFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterDaysNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterDays="negative";
		AirFilterDaysValue="-1";
		SE_LocA.Enter_AirFilterDays(AirFilterDaysValue);
		Description="AirFilterDaysValue="+AirFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterDaysZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterDays="zero";
		AirFilterDaysValue="0";
		SE_LocA.Enter_AirFilterDays(AirFilterDaysValue);
		Description="AirFilterDaysValue="+AirFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterDaysAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterDays="alpha";
		AirFilterDaysValue="ab";
		SE_LocA.Enter_AirFilterDays(AirFilterDaysValue);
		Description="AirFilterDaysValue="+AirFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AirFilterDaysSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AirFilterDays="special";
		AirFilterDaysValue="$";
		SE_LocA.Enter_AirFilterDays(AirFilterDaysValue);
		Description="AirFilterDaysValue="+AirFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterCycleSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterCycle="Same";
		if(ModVaporFilterCycle!=0){
			VaporFilterCycleValue=""+ModVaporFilterCycle+""; 
		}else {
			VaporFilterCycleValue="";
		}
		Description="leave the OER Vapor Filter Cycles field the same. VaporFilterCycleValue="+VaporFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterCycleValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterCycle="Valid";
		if(VaporFilterCycleCounter<100){
			VaporFilterCycleCounter++; //If it's less than 100 increase the value by one
		} else {
			VaporFilterCycleCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModVaporFilterCycle=VaporFilterCycleCounter;
		VaporFilterCycleValue=""+VaporFilterCycleCounter+"";
		SE_LocA.Enter_VaporFilterCycles(VaporFilterCycleValue);
		Description="VaporFilterCycleValue="+VaporFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterCycleNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterCycle="";
		ModVaporFilterCycle=0;
		VaporFilterCycleValue="";
		SE_LocA.Enter_VaporFilterCycles(VaporFilterCycleValue);
		Description="VaporFilterCycleValue="+VaporFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterCycleNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterCycle="negative";
		VaporFilterCycleValue="-1";
		SE_LocA.Enter_VaporFilterCycles(VaporFilterCycleValue);
		Description="VaporFilterCycleValue="+VaporFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterCycleZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterCycle="zero";
		VaporFilterCycleValue="0";
		SE_LocA.Enter_VaporFilterCycles(VaporFilterCycleValue);
		Description="VaporFilterCycleValue="+VaporFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterCycleAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterCycle="alpha";
		VaporFilterCycleValue="ab";
		SE_LocA.Enter_VaporFilterCycles(VaporFilterCycleValue);
		Description="VaporFilterCycleValue="+VaporFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterCycleSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterCycle="special";
		VaporFilterCycleValue="$";
		SE_LocA.Enter_VaporFilterCycles(VaporFilterCycleValue);
		Description="VaporFilterCycleValue="+VaporFilterCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterDaysSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterDays="Same";
		if(ModVaporFilterDays!=0){
			VaporFilterDaysValue=""+ModVaporFilterDays+"";
		}else {
			VaporFilterDaysValue="";
		}
		Description="leave the OER Vapor Filter Days field the same. VaporFilterDaysValue="+VaporFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterDaysValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterDays="Valid";
		if(VaporFilterDaysCounter<100){
			VaporFilterDaysCounter++; //If it's less than 100 increase the value by one
		} else {
			VaporFilterDaysCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModVaporFilterDays=VaporFilterDaysCounter;
		VaporFilterDaysValue=""+VaporFilterDaysCounter+"";
		SE_LocA.Enter_VaporFilterDays(VaporFilterDaysValue);
		Description="VaporFilterDaysValue="+VaporFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_VaporFilterDaysNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterDays="";
		ModVaporFilterDays=0;
		VaporFilterDaysValue="";
		SE_LocA.Enter_VaporFilterDays(VaporFilterDaysValue);
		Description="VaporFilterDaysValue="+VaporFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterDaysNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterDays="negative";
		VaporFilterDaysValue="-1";
		SE_LocA.Enter_VaporFilterDays(VaporFilterDaysValue);
		Description="VaporFilterDaysValue="+VaporFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterDaysZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterDays="zero";
		VaporFilterDaysValue="0";
		SE_LocA.Enter_VaporFilterDays(VaporFilterDaysValue);
		Description="VaporFilterDaysValue="+VaporFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterDaysAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterDays="alpha";
		VaporFilterDaysValue="ab";
		SE_LocA.Enter_VaporFilterDays(VaporFilterDaysValue);
		Description="VaporFilterDaysValue="+VaporFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_VaporFilterDaysSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		VaporFilterDays="special";
		VaporFilterDaysValue="$";
		SE_LocA.Enter_VaporFilterDays(VaporFilterDaysValue);
		Description="VaporFilterDaysValue="+VaporFilterDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentCycleSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentCycle="Same";
		if(ModDetergentCycle!=0){
			DetergentCycleValue=""+ModDetergentCycle+""; 
		}else {
			DetergentCycleValue="";
		}
		Description="leave the OER Detergent Cycles field the same. DetergentCycleValue="+DetergentCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentCycleValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentCycle="Valid";
		if(DetergentCycleCounter<100){
			DetergentCycleCounter++; //If it's less than 100 increase the value by one
		} else {
			DetergentCycleCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModDetergentCycle=DetergentCycleCounter;
		DetergentCycleValue=""+DetergentCycleCounter+"";
		SE_LocA.Enter_DetergentCycles(DetergentCycleValue);
		Description="DetergentCycleValue="+DetergentCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentCycleNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentCycle="";
		DetergentCycleValue="";
		ModDetergentCycle=0;
		SE_LocA.Enter_DetergentCycles(DetergentCycleValue);
		Description="DetergentCycleValue="+DetergentCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentCycleNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentCycle="negative";
		DetergentCycleValue="-1";
		SE_LocA.Enter_DetergentCycles(DetergentCycleValue);
		Description="DetergentCycleValue="+DetergentCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentCycleZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentCycle="zero";
		DetergentCycleValue="0";
		SE_LocA.Enter_DetergentCycles(DetergentCycleValue);
		Description="DetergentCycleValue="+DetergentCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentCycleAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentCycle="alpha";
		DetergentCycleValue="ab";
		SE_LocA.Enter_DetergentCycles(DetergentCycleValue);
		Description="DetergentCycleValue="+DetergentCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentCycleSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentCycle="special";
		DetergentCycleValue="$";
		SE_LocA.Enter_DetergentCycles(DetergentCycleValue);
		Description="DetergentCycleValue="+DetergentCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentDaysSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentDays="Same";
		if(ModDetergentDays!=0){
			DetergentDaysValue=""+ModDetergentDays+"";
		}else {
			DetergentDaysValue="";
		}
		Description="leave the OER Detergent Days field the same. DetergentDaysValue="+DetergentDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentDaysValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentDays="Valid";
		if(DetergentDaysCounter<100){
			DetergentDaysCounter++; //If it's less than 100 increase the value by one
		} else {
			DetergentDaysCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModDetergentDays=DetergentDaysCounter;
		DetergentDaysValue=""+DetergentDaysCounter+"";
		SE_LocA.Enter_DetergentDays(DetergentDaysValue);
		Description="DetergentDaysValue="+DetergentDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentDaysNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentDays="";
		ModDetergentDays=0;
		DetergentDaysValue="";
		SE_LocA.Enter_DetergentDays(DetergentDaysValue);
		Description="DetergentDaysValue="+DetergentDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentDaysNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentDays="negative";
		DetergentDaysValue="-1";
		SE_LocA.Enter_DetergentDays(DetergentDaysValue);
		Description="DetergentDaysValue="+DetergentDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentDaysZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentDays="zero";
		DetergentDaysValue="0";
		SE_LocA.Enter_DetergentDays(DetergentDaysValue);
		Description="DetergentDaysValue="+DetergentDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentDaysAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentDays="alpha";
		DetergentDaysValue="ab";
		SE_LocA.Enter_DetergentDays(DetergentDaysValue);
		Description="DetergentDaysValue="+DetergentDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_DetergentDaysSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		DetergentDays="special";
		DetergentDaysValue="$";
		SE_LocA.Enter_DetergentDays(DetergentDaysValue);
		Description="DetergentDaysValue="+DetergentDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholCycleSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholCycle="Same";
		if(ModAlcoholCycle!=0){
			AlcoholCycleValue=""+ModAlcoholCycle+""; 
		}else {
			AlcoholCycleValue="";
		}
		Description="leave the OER Alcohol Cycles field the same. AlcoholCycleValue="+AlcoholCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholCycleValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholCycle="Valid";
		if(AlcoholCycleCounter<100){
			AlcoholCycleCounter++; //If it's less than 100 increase the value by one
		} else {
			AlcoholCycleCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModAlcoholCycle=AlcoholCycleCounter;
		AlcoholCycleValue=""+AlcoholCycleCounter+"";
		SE_LocA.Enter_AlcoholCycles(AlcoholCycleValue);
		Description="AlcoholCycleValue="+AlcoholCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholCycleNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholCycle="";
		ModAlcoholCycle=0;
		AlcoholCycleValue="";
		SE_LocA.Enter_AlcoholCycles(AlcoholCycleValue);
		Description="AlcoholCycleValue="+AlcoholCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholCycleNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholCycle="negative";
		AlcoholCycleValue="-1";
		SE_LocA.Enter_AlcoholCycles(AlcoholCycleValue);
		Description="AlcoholCycleValue="+AlcoholCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholCycleZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholCycle="zero";
		AlcoholCycleValue="0";
		SE_LocA.Enter_AlcoholCycles(AlcoholCycleValue);
		Description="AlcoholCycleValue="+AlcoholCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholCycleAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholCycle="alpha";
		AlcoholCycleValue="ab";
		SE_LocA.Enter_AlcoholCycles(AlcoholCycleValue);
		Description="AlcoholCycleValue="+AlcoholCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholCycleSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholCycle="special";
		AlcoholCycleValue="$";
		SE_LocA.Enter_AlcoholCycles(AlcoholCycleValue);
		Description="AlcoholCycleValue="+AlcoholCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholDaysSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholDays="Same";
		if(ModAlcoholDays!=0){
			AlcoholDaysValue=""+ModAlcoholDays+"";
		}else {
			AlcoholDaysValue="";
		}
		Description="leave the OER Air Filter Days field the same. AlcoholDaysValue="+AlcoholDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholDaysValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholDays="Valid";
		if(AlcoholDaysCounter<100){
			AlcoholDaysCounter++; //If it's less than 100 increase the value by one
		} else {
			AlcoholDaysCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModAlcoholDays=AlcoholDaysCounter;
		AlcoholDaysValue=""+AlcoholDaysCounter+"";
		SE_LocA.Enter_AlcoholDays(AlcoholDaysValue);
		Description="AlcoholDaysValue="+AlcoholDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_AlcoholDaysNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholDays="";
		ModAlcoholDays=0;
		AlcoholDaysValue="";
		SE_LocA.Enter_AlcoholDays(AlcoholDaysValue);
		Description="AlcoholDaysValue="+AlcoholDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholDaysNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholDays="negative";
		AlcoholDaysValue="-1";
		SE_LocA.Enter_AlcoholDays(AlcoholDaysValue);
		Description="AlcoholDaysValue="+AlcoholDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholDaysZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholDays="zero";
		AlcoholDaysValue="0";
		SE_LocA.Enter_AlcoholDays(AlcoholDaysValue);
		Description="AlcoholDaysValue="+AlcoholDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholDaysAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholDays="alpha";
		AlcoholDaysValue="ab";
		SE_LocA.Enter_AlcoholDays(AlcoholDaysValue);
		Description="AlcoholDaysValue="+AlcoholDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AlcoholDaysSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		AlcoholDays="special";
		AlcoholDaysValue="$";
		SE_LocA.Enter_AlcoholDays(AlcoholDaysValue);
		Description="AlcoholDaysValue="+AlcoholDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMCycleSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMCycle="Same";
		if(ModPMCycle!=0){
			PMCycleValue=""+ModPMCycle+""; 
		}else {
			PMCycleValue="";
		}
		Description="leave the OER PM Cycles field the same. PMCycleValue="+PMCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMCycleValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMCycle="Valid";
		if(PMCycleCounter<100){
			PMCycleCounter++; //If it's less than 100 increase the value by one
		} else {
			PMCycleCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModPMCycle=PMCycleCounter;
		PMCycleValue=""+PMCycleCounter+"";
		SE_LocA.Enter_PMCycles(PMCycleValue);
		Description="PMCycleValue="+PMCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_PMCycleNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMCycle="";
		ModPMCycle=0;
		PMCycleValue="";
		SE_LocA.Enter_PMCycles(PMCycleValue);
		Description="PMCycleValue="+PMCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMCycleNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMCycle="negative";
		PMCycleValue="-1";
		SE_LocA.Enter_PMCycles(PMCycleValue);
		Description="PMCycleValue="+PMCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMCycleZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMCycle="zero";
		PMCycleValue="0";
		SE_LocA.Enter_PMCycles(PMCycleValue);
		Description="PMCycleValue="+PMCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMCycleAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMCycle="alpha";
		PMCycleValue="ab";
		SE_LocA.Enter_PMCycles(PMCycleValue);
		Description="PMCycleValue="+PMCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMCycleSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMCycle="special";
		PMCycleValue="$";
		SE_LocA.Enter_PMCycles(PMCycleValue);
		Description="PMCycleValue="+PMCycleValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMDaysSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMDays="Same";
		if(ModPMDays!=0){
			PMDaysValue=""+ModPMDays+"";
		}else {
			PMDaysValue="";
		}
		Description="leave the OER PM Days field the same. PMDaysValue="+PMDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMDaysValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMDays="Valid";
		if(PMDaysCounter<100){
			PMDaysCounter++; //If it's less than 100 increase the value by one
		} else {
			PMDaysCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModPMDays=PMDaysCounter;
		PMDaysValue=""+PMDaysCounter+"";
		SE_LocA.Enter_PMDays(PMDaysValue);
		Description="PMDaysValue="+PMDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void e_PMDaysNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMDays="";
		ModPMDays=0;
		PMDaysValue="";
		SE_LocA.Enter_PMDays(PMDaysValue);
		Description="PMDaysValue="+PMDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMDaysNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMDays="negative";
		PMDaysValue="-1";
		SE_LocA.Enter_PMDays(PMDaysValue);
		Description="PMDaysValue="+PMDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMDaysZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMDays="zero";
		PMDaysValue="0";
		SE_LocA.Enter_PMDays(PMDaysValue);
		Description="PMDaysValue="+PMDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMDaysAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMDays="alpha";
		PMDaysValue="ab";
		SE_LocA.Enter_PMDays(PMDaysValue);
		Description="PMDaysValue="+PMDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_PMDaysSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		PMDays="special";
		PMDaysValue="$";
		SE_LocA.Enter_PMDays(PMDaysValue);
		Description="PMDaysValue="+PMDaysValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CycleTimeSame() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		CycleTime="Same";
		if(ModCycleTime!=0){
			CycleTimeValue=""+ModCycleTime+"";
		}else {
			CycleTimeValue="";
		}
		Description="leave the OER Cycle Time field the same. CycleTimeValue="+CycleTimeValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CycleTimeValid() throws InterruptedException{ 
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		CycleTime="Valid";
		if(CycleTimeCounter<100){
			CycleTimeCounter++; //If it's less than 100 increase the value by one
		} else {
			CycleTimeCounter=1; //If it's 100 or greater then reset it back to 1 
		}
		ModCycleTime=CycleTimeCounter;
		CycleTimeValue=""+CycleTimeCounter+"";
		SE_LocA.Enter_CycleTime(CycleTimeValue);
		Description="CycleTimeValue="+CycleTimeValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CycleTimeNull() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		CycleTime="";
		ModCycleTime=0;
		CycleTimeValue="";
		SE_LocA.Enter_CycleTime(CycleTimeValue);
		Description="CycleTimeValue="+CycleTimeValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CycleTimeNegative() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		CycleTime="negative";
		CycleTimeValue="-1";
		SE_LocA.Enter_CycleTime(CycleTimeValue);
		Description="CycleTimeValue="+CycleTimeValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CycleTimeZero() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		CycleTime="zero";
		CycleTimeValue="0";
		SE_LocA.Enter_CycleTime(CycleTimeValue);
		Description="CycleTimeValue="+CycleTimeValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CycleTimeAlpha() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		CycleTime="alpha";
		CycleTimeValue="ab";
		SE_LocA.Enter_CycleTime(CycleTimeValue);
		Description="CycleTimeValue="+CycleTimeValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_CycleTimeSpecial() throws InterruptedException{
		//NR 14may15 added for user story 875
		Edge=getCurrentElement().getName();
		CycleTime="special";
		CycleTimeValue="$";
		SE_LocA.Enter_CycleTime(CycleTimeValue);
		Description="CycleTimeValue="+CycleTimeValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LocSave() throws InterruptedException{
		Edge=getCurrentElement().getName();
		SE_LocA.Save_Location_Edit();
		Description="The user clicks the save button";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	
	public void e_Cancel() throws  InterruptedException{
		Edge=getCurrentElement().getName();
		Description="The user clicks the Cancel button";
		System.out.println(Description);
		SE_LocA.Cancel_Location_Edit();
		//Flow=Integer.valueOf(getMbt().getDataValue("Flow")); //Get the value of Flow from the graph (new or modify)
		System.out.println(Edge+" Flow = "+Flow);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	
	//Implement the vertices for the Location graphml

	public void v_Location() {
		if(ScenarioStartflag==true){
			TestResFileName=Unifia_Admin_Selenium.FileName;
			Result=SE_LV.Verify_Admin_Function("Location");
			Vertex= getCurrentElement().getName();
			if(startflag==false){
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				ForFileName = dateFormat.format(date); 
				startflag=true;
				actualResult="\t\t\t "+Unifia_Admin_Selenium.FileName+"\n";
			}	
			Description="\r\n=====================================";
			Description+="\r\nStart of new Scenario - "+Scenario;
			actualResult=actualResult+"\r\n"+Description+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			Scenario++;
			System.out.println("Scenario="+Scenario);
			ScenarioStartflag=false;
			//System.out.println("v_Location; Vertex="+Vertex);
			//Flow=Integer.valueOf(getMbt().getDataValue("Flow")); //Get the value of Flow from the graph (new or modify)
			//System.out.println("v_Location Flow = "+Flow);
		}
	}
	
	public void v_LocationDetail() {
		Vertex= getCurrentElement().getName();
		Description="Selected flow is : "+Flow;
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		//System.out.println("Vertex="+Vertex);
		//Flow=Integer.valueOf(getMbt().getDataValue("Flow")); //Get the value of Flow from the graph (new or modify)
	//	System.out.println("v_LocationDetail Flow = "+Flow);
	}
	
	public void v_LocName() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocName; Vertex="+Vertex);
		Result=SE_LocV.Verify_LocationName(LocNameValue);
/**		if(Flow.equalsIgnoreCase("e_New")){
			Result=SE_LocV.Verify_LocationName(LocNameValue);

		} else if (Flow.equalsIgnoreCase("e_Modify")){
			Result=SE_LocV.Verify_LocationName( LocNameValue);

		}**/
		//Description="v_LocName: LocName="+LocName+" LocNameValue="+LocNameValue;
		Description= LocNameValue+" is displayed in the location name field.";
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocType() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_LocationType(LocType);
/**		if(Flow.equalsIgnoreCase("e_New")){
			Result=SE_LocV.Verify_LocationType(LocType);
		} else if (Flow.equalsIgnoreCase("e_Modify")){
			Result=SE_LocV.Verify_LocationType(LocType);

		}**/

		Description= LocType+" is displayed in the Location type field.";
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);

	}
	
	public void v_LocFacility() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		/**if(Flow.equalsIgnoreCase("e_New")){
			Result=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		} else if (Flow.equalsIgnoreCase("e_Modify")){
			Result=SE_LocV.Verify_LocationFacility(LocFacilityValue);

		}**/
		Description= LocFacilityValue+" is displayed in the in the facility assocation field.";
		//Description="Vertex= "+Vertex+" Facility drop down list verification "+Result;
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		System.out.println(Description);
		//System.out.println(Expected);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
}
	
	
	public void v_LocActive(){
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Description= "The field for Location active is set to:  "+LocActive;
		//Description="The active checkbox cannot be verified until after saving.";
		Result="Pass";
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void v_LocAccessPoint(){
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Description= "The field for Location Access Point is set to:  "+LocSSIDValue;
		//Description="Access Point is set for location";
		Result=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		/**if(Flow.equalsIgnoreCase("e_New")){
			Result=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		} else if (Flow.equalsIgnoreCase("e_Modify")){
			Result=SE_LocV.Verify_LocationSSID(LocSSIDValue);

		}**/
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocTypeFields(){
		//NR 14may15 added for user stories 918 and 875
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_OERNavigation(){
		//NR 14may15 added for user stories 918 and 875
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_Navigation(){
		//NR 14may15 added for user stories 918 and 875
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}

	public void v_LocTypeFields2(){ 
		//NR 14may15 added for user stories 918 and 875
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_ScopeStorage(){ 
		//NR 14may15 added for user stories 918 
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_StorageCabinets() throws InterruptedException{ 
		//NR 14may15 added for user stories 918 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
		//Description="Verify Storage Cabinets is set to "+StorageCabinetsValue;
		Description= "The field for Storage Cabinets is set to:  "+StorageCabinetsValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_ReprocessingArea(){ 
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_OERModel() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_AERModel(OERModelValue);
		//Description="Verify AER Model is set to "+OERModelValue;
		Description= "The field for Location AER Model is set to:  "+OERModelValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_OERSerialNo() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_AERSerialNo(OERSerialNoValue);
		//Description="Verify AER Serial Number is set to "+OERSerialNoValue;
		Description= "The field for Location AER Serial Number is set to:  "+OERSerialNoValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_DisinfectantCycle() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_DisinfectantCycles(DisinfectantCycleValue);
		//Description="Verify AER Disinfectant Cycle is set to "+DisinfectantCycleValue;
		Description= "The field for Location AER Disinfectant Cycle is set to:  "+DisinfectantCycleValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_DisinfectantDays() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_DisinfectantDays(DisinfectantDaysValue);
		//Description="Verify AER Disinfectant Days is set to "+DisinfectantDaysValue;
		Description= "The field for Location AER Disinfectant Days is set to:  "+DisinfectantDaysValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_WaterFilterCycle() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_WaterFilterCycles(WaterFilterCycleValue);
		//Description="Verify AER Water Filter Cycles is set to "+WaterFilterCycleValue;
		Description= "The field for Location AER Water Filter Cycles is set to:  "+WaterFilterCycleValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_WaterFilterDays() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_WaterFilterDays(WaterFilterDaysValue);
		//Description="Verify AER Water Filter Days is set to "+WaterFilterDaysValue;
		Description= "The field for Location AER Water Filter Days is set to:  "+WaterFilterDaysValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_AirFilterCycle() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_AirFilterCycles(AirFilterCycleValue);
		//Description="Verify AER Air Filter Cycle is set to "+AirFilterCycleValue;
		Description= "The field for Location AER Air Filter Cycle is set to:  "+AirFilterCycleValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_AirFilterDays() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_AirFilterDays(AirFilterDaysValue);
		//Description="Verify AER Air Filter Days is set to "+AirFilterDaysValue;
		Description= "The field for Location AER Air Filter Days is set to:  "+AirFilterDaysValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_DetergentCycle() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_DetergentCycles(DetergentCycleValue);
		//Description="Verify AER Detergent Cycle is set to "+DetergentCycleValue;
		Description= "The field for Location AER Detergent Cycle is set to:  "+DetergentCycleValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_DetergentDays() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_DetergentDays(DetergentDaysValue);
		//Description="Verify AER Detergent Days is set to "+DetergentDaysValue;
		Description= "The field for Location AER Detergent Days is set to:  "+DetergentDaysValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_AlcoholCycles() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_AlcoholCycles(AlcoholCycleValue);
		//Description="Verify AER Alcohol Cycle is set to "+AlcoholCycleValue;
		Description= "The field for Location AER Alcohol Cycle is set to:  "+AlcoholCycleValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	public void v_AlcoholDays() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_AlcoholDays(AlcoholDaysValue);
		//Description="Verify AER Alcohol Days is set to "+AlcoholDaysValue;
		Description= "The field for Location AER Alcohol Days is set to:  "+AlcoholDaysValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_PMCycle() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_PMCycles(PMCycleValue);
		//Description="Verify AER PM Cycle is set to "+PMCycleValue;
		Description= "The field for Location AER PM Cycle is set to:  "+PMCycleValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_PMDays() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_PMDays(PMDaysValue);
		//Description="Verify AER PM Days is set to "+PMDaysValue;
		Description= "The field for Location AER PM Days is set to:  "+PMDaysValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_VaporFilterCycles() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_VaporFilterCycles(VaporFilterCycleValue);
		//Description="Verify AER Vapor Filter Cycle is set to "+VaporFilterCycleValue;
		Description= "The field for Location AER Vapor Filter Cycle is set to:  "+VaporFilterCycleValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_VaporFilterDays() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_VaporFilterDays(VaporFilterDaysValue);
		//Description="Verify AER Vapor Filter Days is set to "+VaporFilterDaysValue;
		Description= "The field for Location AER Vapor Filter Days is set to:  "+VaporFilterDaysValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_CycleTime() throws InterruptedException{ 
		//NR 14may15 added for user stories 875 
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		Result=SE_LocV.Verify_CycleTime(CycleTimeValue);
		//Description="Verify AER Cycle Time is set to "+CycleTimeValue;
		Description= "The field for Location AER Cycle Time is set to:  "+CycleTimeValue;
		System.out.println(Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_Reset1(){
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_Reset2(){
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_Reset3(){
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVerf1(){
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVerf2(){ //Nicole
		//empty vertex for logical navigation
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVerf3(){
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer4(){
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		//System.out.println(" Vertex="+Vertex);
	}
	
	public void v_LocVer5(){
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer6(){
		//NR 14may15 added for user stories 918 
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer7(){
		//NR 14may15 added for user stories 918 
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer8(){
		//NR 14may15 added for user stories 918 
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer9(){
		//NR 14may15 added for user stories 918 
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer10(){
		//NR 14may15 added for user stories 918 
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex); 
	}
	
	public void v_LocVer15(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DisinfectantCycle==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	
	public void v_LocVer16(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DisinfectantCycle==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	
	public void v_LocVer17(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		Edge="[DisinfectantCycle==alpha]";
		//System.out.println("Vertex="+Vertex);
	}
	
	
	public void v_LocVer18(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DisinfectantCycle==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	
	public void v_LocVer20(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DisinfectantDays==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	
	public void v_LocVer21(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Vertex= getCurrentElement().getName();
		Edge="[DisinfectantDays==negative]";
		//System.out.println("Vertex="+Vertex);
	}
	
	
	public void v_LocVer22(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DisinfectantDays==alpha]";
		Vertex= getCurrentElement().getName();
		//'System.out.println("Vertex="+Vertex);
	}
	
	
	public void v_LocVer23(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DisinfectantDays==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
		

	public void v_LocVer25(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[WaterFilterCycle==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer26(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[WaterFilterCycle==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer27(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[WaterFilterCycle==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer28(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[WaterFilterCycle==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer30(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[WaterFilterDays==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer31(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[WaterFilterDays==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer32(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[WaterFilterDays==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer33(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[WaterFilterDays==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer35(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AirFilterCycle==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer36(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AirFilterCycle==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer37(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AirFilterCycle==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer38(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AirFilterCycle==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer40(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AirFilterDays==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer41(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AirFilterDays==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer42(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AirFilterDays==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer43(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AirFilterDays==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer45(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DetergentCycle==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer46(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DetergentCycle==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer47(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DetergentCycle==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer48(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DetergentCycle==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer50(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DetergentDays==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer51(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DetergentDays==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer52(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DetergentDays==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer53(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[DetergentDays==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer55(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AlcoholCycle==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer56(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AlcoholCycle==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer57(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AlcoholCycle==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer58(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AlcoholCycle==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	

	public void v_LocVer60(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AlcoholDays==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer61(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AlcoholDays==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer62(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AlcoholDays==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer63(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[AlcoholDays==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer65(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[PMCycle==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer66(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[PMCycle==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer67(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[PMCycle==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer68(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[PMCycle==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer70(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[PMDays==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer71(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[PMDays==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer72(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[PMDays==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer73(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[PMDays==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer75(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation  
		Edge="[CycleTime==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer76(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[CycleTime==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer77(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[CycleTime==aplha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer78(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[CycleTime==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	

	public void v_LocVer80(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[VaporFilterCycle==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer81(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[VaporFilterCycle==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer82(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[VaporFilterCycle==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer83(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[VaporFilterCycle==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer85(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[VaporFilterDays==zero]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer86(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[VaporFilterDays==negative]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_LocVer87(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[VaporFilterDays==alpha]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	
	public void v_LocVer88(){
		//NR 14may15 added for user stories 875 
		//empty vertex for logical navigation 
		Edge="[VaporFilterDays==special]";
		Vertex= getCurrentElement().getName();
		//System.out.println("Vertex="+Vertex);
	}
	
	public void v_Fac2(){
		//empty vertex for logical navigation v_Fac2
		Vertex= getCurrentElement().getName();
		//System.out.println("v_Fac2. Vertex="+Vertex);
	}
	public void v_LocSaved() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocSaved. Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){
			//OERModelValue
			Result_OERModel=SE_LocV.Verify_AERModel(OERModelValue);
			System.out.println("Result_OERModel = "+Result_OERModel);
			Result_OERSerialNo=SE_LocV.Verify_AERSerialNo(OERSerialNoValue);
			System.out.println("Result_OERSerialNo = "+Result_OERSerialNo);
			Result_DisinfectantCycle=SE_LocV.Verify_DisinfectantCycles(DisinfectantCycleValue);
			System.out.println("Result_DisinfectantCycle = "+Result_DisinfectantCycle);
			Result_DisinfectantDays=SE_LocV.Verify_DisinfectantDays(DisinfectantDaysValue);
			System.out.println("Result_DisinfectantDays = "+Result_DisinfectantDays);
			Result_WaterFilterCycle=SE_LocV.Verify_WaterFilterCycles(WaterFilterCycleValue);
			System.out.println("Result_WaterFilterCycle = "+Result_WaterFilterCycle);
			Result_WaterFilterDays=SE_LocV.Verify_WaterFilterDays(WaterFilterDaysValue);
			System.out.println("Result_WaterFilterDays = "+Result_WaterFilterDays);
			Result_AirFilterCycle=SE_LocV.Verify_AirFilterCycles(AirFilterCycleValue);
			System.out.println("Result_AirFilterCycle = "+Result_AirFilterCycle);
			Result_AirFilterDays=SE_LocV.Verify_AirFilterDays(AirFilterDaysValue);
			System.out.println("Result_AirFilterDays = "+Result_AirFilterDays);
			Result_VaporFilterCycle=SE_LocV.Verify_VaporFilterCycles(VaporFilterCycleValue);
			System.out.println("Result_VaporFilterCycle = "+Result_VaporFilterCycle);
			Result_VaporFilterDays=SE_LocV.Verify_VaporFilterDays(VaporFilterDaysValue);
			System.out.println("Result_VaporFilterDays = "+Result_VaporFilterDays);
			Result_DetergentCycle=SE_LocV.Verify_DetergentCycles(DetergentCycleValue);
			System.out.println("Result_DetergentCycle = "+Result_DetergentCycle);
			Result_DetergentDays=SE_LocV.Verify_DetergentDays(DetergentDaysValue);
			System.out.println("Result_DetergentDays = "+Result_DetergentDays);
			Result_AlcoholCycle=SE_LocV.Verify_AlcoholCycles(AlcoholCycleValue);
			System.out.println("Result_AlcoholCycle = "+Result_AlcoholCycle);
			Result_AlcoholDays=SE_LocV.Verify_AlcoholDays(AlcoholDaysValue);
			System.out.println("Result_AlcoholDays = "+Result_AlcoholDays);
			Result_PMCycle=SE_LocV.Verify_PMCycles(PMCycleValue);
			System.out.println("Result_PMCycle = "+Result_PMCycle);
			Result_PMDays=SE_LocV.Verify_PMDays(PMDaysValue);
			System.out.println("Result_PMDays = "+Result_PMDays);
			Result_CycleTime=SE_LocV.Verify_CycleTime(CycleTimeValue);
			System.out.println("Result_CycleTime = "+Result_CycleTime);

			if(Result_OERModel.equalsIgnoreCase("Pass") && Result_OERSerialNo.equalsIgnoreCase("Pass") && Result_DisinfectantCycle.equalsIgnoreCase("Pass") && Result_DisinfectantDays.equalsIgnoreCase("Pass") && Result_WaterFilterCycle.equalsIgnoreCase("Pass") && Result_WaterFilterDays.equalsIgnoreCase("Pass") && Result_AirFilterCycle.equalsIgnoreCase("Pass") && Result_AirFilterDays.equalsIgnoreCase("Pass") && Result_VaporFilterCycle.equalsIgnoreCase("Pass") && Result_VaporFilterDays.equalsIgnoreCase("Pass") && Result_DetergentCycle.equalsIgnoreCase("Pass") && Result_DetergentDays.equalsIgnoreCase("Pass") && Result_AlcoholCycle.equalsIgnoreCase("Pass") && Result_AlcoholDays.equalsIgnoreCase("Pass") && Result_PMCycle.equalsIgnoreCase("Pass") && Result_PMDays.equalsIgnoreCase("Pass") && Result_CycleTime.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else if(LocType.equalsIgnoreCase("Auto Leak Test")){
			ResLTModel=SE_LocV.Verify_LTModel(LTModelVal);
			ResLTSerNum=SE_LocV.Verify_LTSernum(LTSerNumVal);
			if(ResLTModel.contains("Pass") && ResLTSerNum.contains("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
			}
		}else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					if(LocType.equalsIgnoreCase("Auto Leak Test")){
						stm="Insert into LeakTesterDetail(Model, SerialNumber, LocationName) values('"+LTModelVal+"', '"+LTSerNumVal+"', '"+LocNameValue+"')";
						System.out.println(stm);
						insert.execute(stm); 
					}
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
					if(LocType.equalsIgnoreCase("Auto Leak Test")){
						int LeakTesterID=0;
						stm="select LeakTesterDetailID_PK from LeakTesterDetail where LocationName='"+ModifyLocNameValue+"';";
						Location_rs=statement.executeQuery(stm);
						while(Location_rs.next()){
							LeakTesterID=Location_rs.getInt(1);
						}
						if(LeakTesterID!=0){
							stm="Update LeakTesterDetail SET Model='"+LTModelVal+"', SerialNumber='"+LTSerNumVal+"',LocationName='"+LocNameValue+"' where LocationName='"+ModifyLocNameValue+"';";
							System.out.println(stm);
							update.executeUpdate(stm);
						}else{
							stm="Insert into LeakTesterDetail(Model, SerialNumber, LocationName) values('"+LTModelVal+"', '"+LTSerNumVal+"', '"+LocNameValue+"')";
							System.out.println(stm);
							insert.execute(stm);
							insert.close();
						}
					}
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void v_LocSavedOERModel_SNo() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		if (OERModel.equalsIgnoreCase("Same")){
			if (!OERModelValue.equalsIgnoreCase("")){
				if (OERSerialNo.equalsIgnoreCase("Valid") ||OERSerialNo.equalsIgnoreCase("Existing")){
					error_code="0";
				}else if (OERSerialNo.equalsIgnoreCase("Same")) {
					if (OERSerialNoValue.equalsIgnoreCase("")){
						error_code="5";
					}else{
						error_code="0";
					}
				}else if (OERSerialNo.equalsIgnoreCase("")){
					error_code="5";
				}
			}else if (OERModelValue.equalsIgnoreCase("")){
					error_code="0";
			}
		}

		if (OERModel.equalsIgnoreCase("Existing")){
			if (OERSerialNo.equalsIgnoreCase("Valid")){
				error_code="0";
			}else if (OERSerialNo.equalsIgnoreCase("ExistingSame")){
				error_code="4";
			}else if (OERSerialNo.equalsIgnoreCase("Same")){
				if (OERSerialNoValue.equalsIgnoreCase("")){
					error_code="5";
				}else{
					error_code="0";
				}
			}else if (OERSerialNo.equalsIgnoreCase("")){
				error_code="5";
			}else if (OERSerialNo.equalsIgnoreCase("Existing")){
				error_code="0";
			}
		}

		if (OERModel.equalsIgnoreCase("Valid")){
			if (OERSerialNo.equalsIgnoreCase("Same")){
				if (OERSerialNoValue.equalsIgnoreCase("")){
					error_code="5";
				}else{
					error_code="0";
				}
			}else if (OERSerialNo.equalsIgnoreCase("Existing")||OERSerialNo.equalsIgnoreCase("Valid")){
				error_code="0";
			}else if (OERSerialNo.equalsIgnoreCase("")){
				error_code="5";
			}
		}
		
		if (OERModel.equalsIgnoreCase("")){
			error_code="0";
		}
		
		System.out.println(error_code);
		
		if (!error_code.equalsIgnoreCase("0")){
			Result=SE_Gen.Verify_ErrCode(error_code); //Verify the error code given by Unifia is correct. 
			Description="Location is not saved due to ErrorCode("+error_code+").";
			Expected="Location is not saved and an error message is displayed. Result="+Result;
			TestHelper.StepWriter1(Vertex, Description, Expected, Result); 
			System.out.println(Expected);
			System.out.println(Result);
			SE_LocA.Cancel_Location_Edit();
		}else{
			System.out.println("v_LocSaved. Vertex="+Vertex);
			SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
			System.out.println("Search Location by name = "+LocNameValue);
			SE_LocA.Search_Location_ByFacility(LocFacilityValue);
			System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
			GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
			System.out.println("Grid ID="+GridID);
			SE_LocA.Select_Location_To_Modify(LocNameValue);
			Result_LocType=SE_LocV.Verify_LocationType(LocType);
			System.out.println("Result_LocType = "+Result_LocType);
			Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
			System.out.println("Result_LocFacility = "+Result_LocFacility);
			Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
			System.out.println("Result_LocSSID = "+Result_LocSSID);
			ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
			System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
			if(LocType.equalsIgnoreCase("Scope Storage Area")){
				Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
				System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
				Result_ReprocessingFields="Pass";
			} else if(LocType.equalsIgnoreCase("Reprocessor")){
	
				Result_OERModel=SE_LocV.Verify_AERModel(OERModelValue);
				System.out.println("Result_OERModel = "+Result_OERModel);
				Result_OERSerialNo=SE_LocV.Verify_AERSerialNo(OERSerialNoValue);
				System.out.println("Result_OERSerialNo = "+Result_OERSerialNo);
	
				if(Result_OERModel.equalsIgnoreCase("Pass")&&Result_OERSerialNo.equalsIgnoreCase("Pass")){
					Result_ReprocessingFields="Pass";
					Result_StorageCabinets="Pass";
					System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
				} else {
					//Add the condition
					
					Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
					Result_StorageCabinets="Pass";
					System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
				}
			} else {
				Result_StorageCabinets="Pass";
				Result_ReprocessingFields="Pass";
			}
			SE_LocA.Cancel_Location_Edit();
			if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
				Result="Pass";
				try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
					//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
					conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
	
					Statement statement = conn.createStatement();
					Statement update= conn.createStatement();
					Statement insert= conn.createStatement();	
					if(Flow.equalsIgnoreCase("e_New")){
						stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
						System.out.println(stm);
						insert.execute(stm); 
						insert.close();
					} else if(Flow.equalsIgnoreCase("e_Modify")){
						stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
						System.out.println(stm);
						update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
				Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";
			}
			else{
				Result="#Failed!# - The values were not saved correctly.";
				Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";
	
			}
			Expected="The Location details are successfully saved";
			System.out.println("Expected= "+Expected+". Description ="+Description);
			//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}
	}
	
	public void v_LocSavedDisinfectantCycle() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_DisinfectantCycle=SE_LocV.Verify_DisinfectantCycles(DisinfectantCycleValue);
			System.out.println("Result_DisinfectantCycle = "+Result_DisinfectantCycle);
			if(Result_DisinfectantCycle.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";
		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";
		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedDisinfectantDays() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_DisinfectantDays=SE_LocV.Verify_DisinfectantDays(DisinfectantDaysValue);
			System.out.println("Result_DisinfectantDays = "+Result_DisinfectantDays);
			
			if(Result_DisinfectantDays.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedWaterCycle() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_WaterFilterCycle=SE_LocV.Verify_WaterFilterCycles(WaterFilterCycleValue);
			System.out.println("Result_WaterFilterCycle = "+Result_WaterFilterCycle);
			if(Result_WaterFilterCycle.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedWaterDays() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_WaterFilterDays=SE_LocV.Verify_WaterFilterDays(WaterFilterDaysValue);
			System.out.println("Result_WaterFilterDays = "+Result_WaterFilterDays);
			
			if(Result_WaterFilterDays.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedAirCycle() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocSaved. Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_AirFilterCycle=SE_LocV.Verify_AirFilterCycles(AirFilterCycleValue);
			System.out.println("Result_AirFilterCycle = "+Result_AirFilterCycle);
			if(Result_AirFilterCycle.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedAirDays() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_AirFilterDays=SE_LocV.Verify_AirFilterDays(AirFilterDaysValue);
			System.out.println("Result_AirFilterDays = "+Result_AirFilterDays);
			
			if(Result_AirFilterDays.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedAlcoholCycle() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocSaved. Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_AlcoholCycle=SE_LocV.Verify_AlcoholCycles(AlcoholCycleValue);
			System.out.println("Result_AlcoholCycle = "+Result_AlcoholCycle);			
			if(Result_AlcoholCycle.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedAlcoholDays() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_AlcoholDays=SE_LocV.Verify_AlcoholDays(AlcoholDaysValue);
			System.out.println("Result_AlcoholDays = "+Result_AlcoholDays);
			
			if(Result_AlcoholDays.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedDetergentCycles() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocSaved. Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_DetergentCycle=SE_LocV.Verify_DetergentCycles(DetergentCycleValue);
			System.out.println("Result_DetergentCycle = "+Result_DetergentCycle);
			
			if(Result_DetergentCycle.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_SavedDetergentDays() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){
			Result_DetergentDays=SE_LocV.Verify_DetergentDays(DetergentDaysValue);
			System.out.println("Result_DetergentDays = "+Result_DetergentDays);
			if(Result_DetergentDays.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedVaporCycle() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_VaporFilterCycle=SE_LocV.Verify_VaporFilterCycles(VaporFilterCycleValue);
			System.out.println("Result_VaporFilterCycle = "+Result_VaporFilterCycle);
			
			if(Result_VaporFilterCycle.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedVaporDays() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocSaved. Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){
			//OERModelValue
			Result_VaporFilterDays=SE_LocV.Verify_VaporFilterDays(VaporFilterDaysValue);
			System.out.println("Result_VaporFilterDays = "+Result_VaporFilterDays);

			if(Result_VaporFilterDays.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedPMCycle() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocSaved. Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){
			//OERModelValue
			Result_PMCycle=SE_LocV.Verify_PMCycles(PMCycleValue);
			System.out.println("Result_PMCycle = "+Result_PMCycle);

			if(Result_PMCycle.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedPMDays() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocSaved. Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){
			//OERModelValue
			Result_PMDays=SE_LocV.Verify_PMDays(PMDaysValue);
			System.out.println("Result_PMDays = "+Result_PMDays);

			if(Result_PMDays.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSavedCycleTime() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("v_LocSaved. Vertex="+Vertex);
		SE_LocA.Search_Location_ByName(LocNameValue);  //Search for the Location Name that was just saved.
		System.out.println("Search Location by name = "+LocNameValue);
		SE_LocA.Search_Location_ByFacility(LocFacilityValue);
		System.out.println("Search for Facility name assocated with Location saved: "+LocFacilityValue);	
		GridID=SE_LocA.GetGridID_Location_To_Modify(LocNameValue); // Get the GridID of the location
		System.out.println("Grid ID="+GridID);
		SE_LocA.Select_Location_To_Modify(LocNameValue);
		Result_LocType=SE_LocV.Verify_LocationType(LocType);
		System.out.println("Result_LocType = "+Result_LocType);
		Result_LocFacility=SE_LocV.Verify_LocationFacility(LocFacilityValue);
		System.out.println("Result_LocFacility = "+Result_LocFacility);
		Result_LocSSID=SE_LocV.Verify_LocationSSID(LocSSIDValue);
		System.out.println("Result_LocSSID = "+Result_LocSSID);
		ModLocAct_Val=SE_LocA.Location_Active_Value(); //Get the current value of the Active checkbox of the Location to be modified. 
		System.out.println("ModLocAct_Val = "+ModLocAct_Val+" and it is supposed to be LocActive= "+LocActive);
		if(LocType.equalsIgnoreCase("Scope Storage Area")){
			Result_StorageCabinets=SE_LocV.Verify_StorageCabinets(StorageCabinetsValue);
			System.out.println("Result_StorageCabinets = "+Result_StorageCabinets);
			Result_ReprocessingFields="Pass";
		} else if(LocType.equalsIgnoreCase("Reprocessor")){

			Result_CycleTime=SE_LocV.Verify_CycleTime(CycleTimeValue);
			System.out.println("Result_CycleTime = "+Result_CycleTime);
			
			if(Result_CycleTime.equalsIgnoreCase("Pass")){
				Result_ReprocessingFields="Pass";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			} else {
				Result_ReprocessingFields="#Failed!# - one of the reprocessing fields is not set correctlly";
				Result_StorageCabinets="Pass";
				System.out.println("Result_ReprocessingFields = "+Result_ReprocessingFields);
			}
		} else {
			Result_StorageCabinets="Pass";
			Result_ReprocessingFields="Pass";
		}
		SE_LocA.Cancel_Location_Edit();
		if(!GridID.equals(null) && Result_LocType.equalsIgnoreCase("Pass")&& Result_LocFacility.equalsIgnoreCase("Pass")&& Result_LocSSID.equalsIgnoreCase("Pass")&& ModLocAct_Val.equalsIgnoreCase(LocActive)&& Result_StorageCabinets.equalsIgnoreCase("Pass")&& Result_ReprocessingFields.equalsIgnoreCase("Pass")){ //If the GridID is Null, then the save failed. If GridID is not null the save passed. 
			Result="Pass";
			try{ //If the save was successful then update the Test Database with the new row if the Flow is New or update the modified row if the path is Modify.
				//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/unifia_admin_testdata?user=root&password=P@$$w0rd"); //connect to the test data DB.				
				conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

				Statement statement = conn.createStatement();
				Statement update= conn.createStatement();
				Statement insert= conn.createStatement();	
				if(Flow.equalsIgnoreCase("e_New")){
					stm="Insert into location(EnteredLocationName, LocationType, Facility, Active, TestKeyword, UpdateDate, AccessPoint, AERModel, AERSerialNo, DisinfectantCycles, DisinfectantDays, WaterFilterCycles, WaterFilterDays, AirFilterCycles, AirFilterDays, VaporFilterCycles, VaporFilterDays, DetergentCycles, DetergentDays, AlcoholCycles, AlcoholDays, PMCycles, PMDays, CycleTime, StorageCabinets) values('"+LocNameValue+"', '"+LocType+"', '"+FacilityDBValue+"', '"+LocActive+"', 'Existing', CURRENT_TIMESTAMP, '"+AccessPointDB+"', '"+OERModelValue+"', '"+OERSerialNoValue+"', "+ModDisinfectantCycle+", "+ModDisinfectantDays+", "+ModWaterFilterCycle+", "+ModWaterFilterDays+", "+ModAirFilterCycle+", "+ModAirFilterDays+", "+ModVaporFilterCycle+", "+ModVaporFilterDays+", "+ModDetergentCycle+", "+ModDetergentDays+", "+ModAlcoholCycle+", "+ModAlcoholDays+", "+ModPMCycle+", "+ModPMDays+", "+ModCycleTime+", "+ModStorageCabinets+")";
					System.out.println(stm);
					insert.execute(stm); 
					insert.close();
				} else if(Flow.equalsIgnoreCase("e_Modify")){
					stm="Update location SET EnteredLocationName='"+LocNameValue+"', LocationType='"+LocType+"', Facility="+FacilityDBValue+", Active='"+LocActive+"', UpdateDate=CURRENT_TIMESTAMP, AccessPoint='"+AccessPointDB+"', AERModel='"+OERModelValue+"', AERSerialNo='"+OERSerialNoValue+"', DisinfectantCycles="+ModDisinfectantCycle+", DisinfectantDays="+ModDisinfectantDays+", WaterFilterCycles="+ModWaterFilterCycle+", WaterFilterDays="+ModWaterFilterDays+", AirFilterCycles="+ModAirFilterCycle+", AirFilterDays="+ModAirFilterDays+", VaporFilterCycles="+ModVaporFilterCycle+", VaporFilterDays="+ModVaporFilterDays+", DetergentCycles="+ModDetergentCycle+", DetergentDays="+ModDetergentDays+", AlcoholCycles="+ModAlcoholCycle+", AlcoholDays="+ModAlcoholDays+", PMCycles="+ModPMCycle+", PMDays="+ModPMDays+", CycleTime="+ModCycleTime+", StorageCabinets="+ModStorageCabinets+"  WHERE idLocation="+ModifyLocationDB;
					System.out.println(stm);
					update.executeUpdate(stm); // update the UpdateDate variable of the row of data used to the current date/time stamp.
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
			Description="Flow="+Flow+"; LocName = "+LocName+" Save successful";

		}
		else{
			Result="#Failed!# - The values were not saved correctly.";
			Description="Flow="+Flow+"; LocName = "+LocName+" Save NOT successful and it should be.";

		}
		Expected="The Location details are successfully saved";
		System.out.println("Expected= "+Expected+". Description ="+Description);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSaveErr() throws InterruptedException {
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex "+Vertex);
		if(LTModelSerNum.equalsIgnoreCase("Existing")){
			ErrCode="4";
		}else if((!LocType.equalsIgnoreCase("Auto Leak Test"))&& (LocName.equalsIgnoreCase("") || LocFacility.equalsIgnoreCase("")||LocType.equalsIgnoreCase("")|| Cabinets.equalsIgnoreCase(""))){ //Entering 'null' in the location name will result in an error message indicating the name cannot be empty.
			//Entering null in the location facility will result in an error message.  //Entering null in the location access point will result in an error message.
			//Entering 'null' in the location type will result in an error message indicating the name cannot be empty.			//NR 14may15 added Cabinets.equalsIgnoreCase("") for user story 918
			ErrCode="5";
		}else if((LocType.equalsIgnoreCase("Auto Leak Test"))&& (LocName.equalsIgnoreCase("") || LocFacility.equalsIgnoreCase("")||LocType.equalsIgnoreCase("")|| Cabinets.equalsIgnoreCase("") || LTModel.equals("") || LTSerNum.equals("") || LTSerNumVal.equals("") || LTModelVal.equals(""))){
			ErrCode="5";
		}  else if(DisinfectantCycle.equalsIgnoreCase("negative")||DisinfectantCycle.equalsIgnoreCase("zero")||DisinfectantCycle.equalsIgnoreCase("alpha")||DisinfectantCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(DisinfectantDays.equalsIgnoreCase("negative")||DisinfectantDays.equalsIgnoreCase("zero")||DisinfectantDays.equalsIgnoreCase("alpha")||DisinfectantDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(WaterFilterCycle.equalsIgnoreCase("negative")||WaterFilterCycle.equalsIgnoreCase("zero")||WaterFilterCycle.equalsIgnoreCase("alpha")||WaterFilterCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(WaterFilterDays.equalsIgnoreCase("negative")||WaterFilterDays.equalsIgnoreCase("zero")||WaterFilterDays.equalsIgnoreCase("alpha")||WaterFilterDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(AirFilterCycle.equalsIgnoreCase("negative")||AirFilterCycle.equalsIgnoreCase("zero")||AirFilterCycle.equalsIgnoreCase("alpha")||AirFilterCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(AirFilterDays.equalsIgnoreCase("negative")||AirFilterDays.equalsIgnoreCase("zero")||AirFilterDays.equalsIgnoreCase("alpha")||AirFilterDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(VaporFilterCycle.equalsIgnoreCase("negative")||VaporFilterCycle.equalsIgnoreCase("zero")||VaporFilterCycle.equalsIgnoreCase("alpha")||VaporFilterCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(VaporFilterDays.equalsIgnoreCase("negative")||VaporFilterDays.equalsIgnoreCase("zero")||VaporFilterDays.equalsIgnoreCase("alpha")||VaporFilterDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(DetergentCycle.equalsIgnoreCase("negative")||DetergentCycle.equalsIgnoreCase("zero")||DetergentCycle.equalsIgnoreCase("alpha")||DetergentCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(DetergentDays.equalsIgnoreCase("negative")||DetergentDays.equalsIgnoreCase("zero")||DetergentDays.equalsIgnoreCase("alpha")||DetergentDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(AlcoholCycle.equalsIgnoreCase("negative")||AlcoholCycle.equalsIgnoreCase("zero")||AlcoholCycle.equalsIgnoreCase("alpha")||AlcoholCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(AlcoholDays.equalsIgnoreCase("negative")||AlcoholDays.equalsIgnoreCase("zero")||AlcoholDays.equalsIgnoreCase("alpha")||AlcoholDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(PMCycle.equalsIgnoreCase("negative")||PMCycle.equalsIgnoreCase("zero")||PMCycle.equalsIgnoreCase("alpha")||PMCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(PMDays.equalsIgnoreCase("negative")||PMDays.equalsIgnoreCase("zero")||PMDays.equalsIgnoreCase("alpha")||PMDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(CycleTime.equalsIgnoreCase("negative")||CycleTime.equalsIgnoreCase("zero")||CycleTime.equalsIgnoreCase("alpha")||CycleTime.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(Cabinets.equalsIgnoreCase("negative")||Cabinets.equalsIgnoreCase("zero")||Cabinets.equalsIgnoreCase("alpha")||Cabinets.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 918
		} else if(LocName.equalsIgnoreCase("Fac1Exist")){ //Entering an existing location in the location Facility Association field will result in an error message.
			ErrCode="4";
		} else {
			ErrCode="100";
		}
		System.out.println("Error Code="+ErrCode);

		Description="Failing Edge="+Edge+"; Vertex "+Vertex+"; Flow="+Flow+"; LocName = "+LocName+"; Error message is "+ErrCode;
		System.out.println(Description);
		Result=SE_Gen.Verify_ErrCode(ErrCode); //Verify the error message in the application matches the expected error message. 
		System.out.println("result = "+Result);
		if(Result.contains("#Failed!#") && LTSerNum.equalsIgnoreCase("Existing")){
			Result+="; Bug 12834 - Duplicate serial number is allowed while creating new location of type Auto Leak Test";
		}
		
		Expected="The Location details are NOT saved and an Error message is displayed";
		System.out.println(Expected);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LocSaveErr2() throws InterruptedException{
		Vertex= getCurrentElement().getName();
		System.out.println("Vertex "+Vertex);
		if(LocName.equalsIgnoreCase("") || LocFacility.equalsIgnoreCase("")||LocSSIDValue.equalsIgnoreCase("")||LocType.equalsIgnoreCase("")|| Cabinets.equalsIgnoreCase("")){ //Entering 'null' in the location name will result in an error message indicating the name cannot be empty.
			//Entering null in the location facility will result in an error message.  //Entering null in the location access point will result in an error message.
			//Entering 'null' in the location type will result in an error message indicating the name cannot be empty.			//NR 14may15 added Cabinets.equalsIgnoreCase("") for user story 918
			ErrCode="5";
		}  else if(DisinfectantCycle.equalsIgnoreCase("negative")||DisinfectantCycle.equalsIgnoreCase("zero")||DisinfectantCycle.equalsIgnoreCase("alpha")||DisinfectantCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(DisinfectantDays.equalsIgnoreCase("negative")||DisinfectantDays.equalsIgnoreCase("zero")||DisinfectantDays.equalsIgnoreCase("alpha")||DisinfectantDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(WaterFilterCycle.equalsIgnoreCase("negative")||WaterFilterCycle.equalsIgnoreCase("zero")||WaterFilterCycle.equalsIgnoreCase("alpha")||WaterFilterCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(WaterFilterDays.equalsIgnoreCase("negative")||WaterFilterDays.equalsIgnoreCase("zero")||WaterFilterDays.equalsIgnoreCase("alpha")||WaterFilterDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(AirFilterCycle.equalsIgnoreCase("negative")||AirFilterCycle.equalsIgnoreCase("zero")||AirFilterCycle.equalsIgnoreCase("alpha")||AirFilterCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(AirFilterDays.equalsIgnoreCase("negative")||AirFilterDays.equalsIgnoreCase("zero")||AirFilterDays.equalsIgnoreCase("alpha")||AirFilterDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(VaporFilterCycle.equalsIgnoreCase("negative")||VaporFilterCycle.equalsIgnoreCase("zero")||VaporFilterCycle.equalsIgnoreCase("alpha")||VaporFilterCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(VaporFilterDays.equalsIgnoreCase("negative")||VaporFilterDays.equalsIgnoreCase("zero")||VaporFilterDays.equalsIgnoreCase("alpha")||VaporFilterDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(DetergentCycle.equalsIgnoreCase("negative")||DetergentCycle.equalsIgnoreCase("zero")||DetergentCycle.equalsIgnoreCase("alpha")||DetergentCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(DetergentDays.equalsIgnoreCase("negative")||DetergentDays.equalsIgnoreCase("zero")||DetergentDays.equalsIgnoreCase("alpha")||DetergentDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(AlcoholCycle.equalsIgnoreCase("negative")||AlcoholCycle.equalsIgnoreCase("zero")||AlcoholCycle.equalsIgnoreCase("alpha")||AlcoholCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(AlcoholDays.equalsIgnoreCase("negative")||AlcoholDays.equalsIgnoreCase("zero")||AlcoholDays.equalsIgnoreCase("alpha")||AlcoholDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(PMCycle.equalsIgnoreCase("negative")||PMCycle.equalsIgnoreCase("zero")||PMCycle.equalsIgnoreCase("alpha")||PMCycle.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(PMDays.equalsIgnoreCase("negative")||PMDays.equalsIgnoreCase("zero")||PMDays.equalsIgnoreCase("alpha")||PMDays.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(CycleTime.equalsIgnoreCase("negative")||CycleTime.equalsIgnoreCase("zero")||CycleTime.equalsIgnoreCase("alpha")||CycleTime.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 875
		} else if(Cabinets.equalsIgnoreCase("negative")||Cabinets.equalsIgnoreCase("zero")||Cabinets.equalsIgnoreCase("alpha")||Cabinets.equalsIgnoreCase("special")){
			ErrCode="19";//NR 14may15 Added for user story 918
		} else if(LocName.equalsIgnoreCase("Fac1Exist")){ //Entering an existing location in the location Facility Association field will result in an error message.
			ErrCode="4";
		} else {
			ErrCode="100";
		}
		System.out.println("Error Code="+ErrCode);

		Description="Failing Edge="+Edge+"; Vertex "+Vertex+"; Flow="+Flow+"; LocName = "+LocName+" Error message is "+ErrCode;
		System.out.println(Description);

		Result=SE_Gen.Verify_ErrCode(ErrCode); //Verify the error message in the application matches the expected error message. 
		System.out.println("result = "+Result);
		
		Expected="The Location details are NOT saved and an Error message is displayed";
		System.out.println(Expected);
		//TestFrameWork.TestHelper.StepWriter1(Vertex, Description, Expected, Result);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}

	public void e_OERModelExisting() throws InterruptedException{ 
		//RK24Aug16
		Edge=getCurrentElement().getName();
		System.out.println(Edge);
		OERModel="Existing";
		try{
			Connection conn = DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update= conn.createStatement();
			stm="select Model,SerialNumber from AERDetail where Model is not Null and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from AERDetail where Model is not Null)";
			OER_rs = statement.executeQuery(stm);
			while(OER_rs.next()){
				ExistingAERModel= OER_rs.getString(1); //the first variable in the set is the ID row in the database.
				ExistingAERSerialNoSame= OER_rs.getString(2); //the second variable is the entered location name
				//System.out.println("UpdatedLocFacility = "+UpdatedLocFacility);
			}
			OER_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			update.executeUpdate("update AERDetail set LastUpdatedDateTime=CURRENT_TIMESTAMP WHERE Model= '"+ExistingAERModel+"' and SerialNumber='"+ExistingAERSerialNoSame+"';");
			update.close();
			conn.close();
			OERModelValue=ExistingAERModel;
			SE_LocA.Enter_AERModel(OERModelValue);
			Description="ExistingAERModel="+OERModelValue;
			System.out.println(Description);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
			
		}catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}
	
	public void e_OERSNoExistingSame() throws InterruptedException{ 
		//RK24Aug16
		Edge=getCurrentElement().getName();
		System.out.println(Edge);
		OERSerialNo="ExistingSame";
		OERSerialNoValue=ExistingAERSerialNoSame;
		SE_LocA.Enter_AERSerialNo(OERSerialNoValue);
		Description="ExistingAERSerialNoSame="+OERSerialNoValue;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_OERSerialNoExisting() throws InterruptedException{ 
		//RK24Aug16
		Edge=getCurrentElement().getName();
		System.out.println(Edge);
		OERSerialNo="Existing";
		try{
			Connection conn = DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
			Statement statement = conn.createStatement();
			Statement update= conn.createStatement();
			stm="select Model,SerialNumber from AERDetail where SerialNumber is not null and SerialNumber !='"+ExistingAERSerialNoSame+"' and LastUpdatedDateTime=(Select Min(LastUpdatedDateTime) from AERDetail where SerialNumber is not null and SerialNumber !='"+ExistingAERSerialNoSame+"')";
			OER_rs = statement.executeQuery(stm);
			while(OER_rs.next()){
				AERModel= OER_rs.getString(1);
				ExistingAERSerialNo= OER_rs.getString(2); //the second variable is the entered location name
				System.out.println("AERModel= "+AERModel);
			}
			OERSerialNoValue=ExistingAERSerialNo;
			OER_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			update.executeUpdate("update AERDetail set LastUpdatedDateTime=CURRENT_TIMESTAMP WHERE Model= '"+AERModel+"' and SerialNumber='"+ExistingAERSerialNo+"';");
			update.close();
			conn.close();
			SE_LocA.Enter_AERSerialNo(OERSerialNoValue);
			Description="ExistingAERSerialNo="+OERSerialNoValue;
			System.out.println(Description);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}
	
	public void e_ModifyAuto() throws InterruptedException{
		ScenarioStartflag=true;
		Edge=getCurrentElement().getName();
		Description="Modify an existing Auto Leak Test location.";
		Flow="e_Modify";
		OERModelValue="";
		OERSerialNoValue="";
		LTModel="";
		LTModelVal="";
		LTSerNum="";
		LTSerNumVal="";
		LTModelSerNum="";
		System.out.println(Description+" Flow = "+Flow);
		try{
			conn=DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());

			Statement statement = conn.createStatement();
			stm="Select l.idLocation, l.EnteredLocationName, l.LocationType, l.Facility, f.EnteredFacilityName, l.AccessPoint, ap.SSID, l.AERModel, l.AERSerialNo, l.DisinfectantCycles, l.DisinfectantDays, l.WaterFilterCycles, l.WaterFilterDays, l.AirFilterCycles, l.AirFilterDays, l.VaporFilterCycles, l.VaporFilterDays, l.DetergentCycles, l.DetergentDays, l.AlcoholCycles, l.AlcoholDays, l.PMCycles, l.PMDays, l.CycleTime, l.StorageCabinets from location l join facility f on l.Facility=f.idFacility join accesspoint ap on l.AccessPoint=ap.idAccessPoint WHERE l.TestKeyword='Existing' AND l.UpdateDate=(Select MIN(l.UpdateDate) from location l Where l.TestKeyword ='Existing' and LocationType='Auto Leak Test')";
			Location_rs = statement.executeQuery(stm);
			while(Location_rs.next()){
				ModifyLocationDB= Location_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//ModLocNameBaseValue= Location_rs.getString(2); //the second variable is the location base name
				ModifyLocNameValue= Location_rs.getString(2); //the third variable is the Entered location name
				//UpdatedLocNameValue= Location_rs.getString(3); //the forth variable is the concatenated location name
				ModLocType= Location_rs.getString(3); //the fifth variable is the Location's Facility Association
				ModifyFacilityDB= Location_rs.getInt(4); //the first variable in the set is the ID row in the database.
				ModLocFacility= Location_rs.getString(5); //the fifth variable is the Location's Type
				ModifySSIDDB= Location_rs.getInt(6); //the first variable in the set is the ID row in the database.
				ModLocSSID=Location_rs.getString(7);
				ModOERModel=Location_rs.getString(8);
				ModOERSerialNo=Location_rs.getString(9);
				ModDisinfectantCycle= Location_rs.getInt(10);
				ModDisinfectantDays= Location_rs.getInt(11);
				ModWaterFilterCycle= Location_rs.getInt(12);
				ModWaterFilterDays= Location_rs.getInt(13);
				ModAirFilterCycle= Location_rs.getInt(14);
				ModAirFilterDays= Location_rs.getInt(15);
				ModVaporFilterCycle= Location_rs.getInt(16);
				ModVaporFilterDays= Location_rs.getInt(17);
				ModDetergentCycle= Location_rs.getInt(18);
				ModDetergentDays= Location_rs.getInt(19);
				ModAlcoholCycle= Location_rs.getInt(20);
				ModAlcoholDays= Location_rs.getInt(21);
				ModPMCycle= Location_rs.getInt(22);
				ModPMDays= Location_rs.getInt(23);
				ModCycleTime= Location_rs.getInt(24);
				ModStorageCabinets=Location_rs.getInt(25); //NR 14may15 added for user story 918

			}
			Location_rs.close();
			
			stm="Select idFacility, EnteredFacilityName from facility where EnteredFacilityName!='"+ModLocFacility+"' and TestScenario='Location' and Active='True'";
			Location_rs = statement.executeQuery(stm); //Get a facility name not equal to the Facility currently associated to the location being modified.
			while(Location_rs.next()){
				UpdatedFacilityDB= Location_rs.getInt(1); //the first variable in the set is the ID row in the database.
				//System.out.println("UpdatedFacilityDB = "+UpdatedFacilityDB);
				UpdatedLocFacility= Location_rs.getString(2); //the second variable is the entered location name
				//System.out.println("UpdatedLocFacility = "+UpdatedLocFacility);
			}
			Location_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			
			SE_LocA.Search_Location_ByName(ModifyLocNameValue);  //Search for the Location Name to be modified in the Application.
			System.out.println("Search for Location to be modified which equals "+ModifyLocNameValue);	
			SE_LocA.Search_Location_ByFacility(ModLocFacility);
			System.out.println("Search for Facility name assocated with Location to be modified which equals "+ModLocFacility);	

			GridID=SE_LocA.GetGridID_Location_To_Modify(ModifyLocNameValue); // Get the GridID of the location to be modified.
			System.out.println("GridID = "+GridID);
			SE_LocA.Select_Location_To_Modify(ModifyLocNameValue); //Select the row of the location to be modified.
			//System.out.println(Edge+" Location "+ModifyLocNameValue+" is selected");
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_AutoLeakTest() throws InterruptedException{ 
		Edge=getCurrentElement().getName();
		LocType="Auto Leak Test";
		
		if(Flow.equalsIgnoreCase("e_New")){ //If the path is new, set the Location Type as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart) for the new location field. 
			SE_LocA.Selct_Location_Type(LocType);
		}else if(Flow.equalsIgnoreCase("e_Modify")) { //If the path is modify set the Location Type for the row being modified as per the graph (i.e. Procedure Room, Scope Storage Area, Soiled Area, Reprocessing Area, and Travel Cart)
				SE_LocA.Selct_Location_Type( LocType);
				System.out.println("LocType="+LocType);
				if(ModLocType.equalsIgnoreCase("Reprocessor")){
					//If the previous location type was reprocessing area  a confirmation message appears confirming the user wants to change as values for the reprocessor will be deleted
					if((ModOERModel.equalsIgnoreCase(""))&&(ModOERSerialNo.equalsIgnoreCase(""))&&(ModDisinfectantCycle==0) && (ModDisinfectantDays==0) && (ModWaterFilterCycle==0) && (ModWaterFilterDays==0) && (ModAirFilterCycle==0) && (ModAirFilterDays==0) && (ModVaporFilterCycle==0) && ModVaporFilterDays==0 && ModDetergentCycle==0 && ModDetergentDays==0 && ModAlcoholCycle==0 && ModAlcoholDays==0 && ModPMCycle==0 && ModPMDays==0 && ModCycleTime==0){
						//if all the values are empty the confirmation window will not be displayed. 
					}else {
						SE_LocA.Confirmation_Window("Yes");
						OERModelValue="";
						OERSerialNoValue="";
						ModDisinfectantCycle=0;
						ModDisinfectantDays=0;
						ModWaterFilterCycle=0;
						ModWaterFilterDays=0;
						ModAirFilterCycle=0;
						ModAirFilterDays=0;
						ModVaporFilterCycle=0;
						ModVaporFilterDays=0;
						ModDetergentCycle=0;
						ModDetergentDays=0;
						ModAlcoholCycle=0;
						ModAlcoholDays=0;
						ModPMCycle=0;
						ModPMDays=0;
						ModCycleTime=0;

					}
				} else if(ModLocType.equalsIgnoreCase("Scope Storage Area")){
					//If the previous location type was scope storage area, a confirmation message appears confirming the user wants to change as values for the scope storage will be deleted
					SE_LocA.Confirmation_Window("Yes");
					ModStorageCabinets=0;
				}

		}
		Description="The user selects "+LocType+" in the Location Type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTModel_Null() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTModel=""; 
		LTModelVal="";
		SE_LocA.enter_LTModel(LTModelVal);
		Description="LTModel_Null="+LTModelVal;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTModel_Valid() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTModel="valid"; 
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		LTModelVal="LT"+cal+calchk;
		SE_LocA.enter_LTModel(LTModelVal);
		Description="LTModel_Valid="+LTModelVal;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTModel_Existing() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTModel="Existing";
		try{
			Connection conn = DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();
			stm="select Model from LeakTesterDetail";
			OER_rs = statement.executeQuery(stm);
			while(OER_rs.next()){
				LTModelVal= OER_rs.getString(1);
			}
			OER_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			conn.close();
			SE_LocA.enter_LTModel(LTModelVal);
			Description="e_LTModel_Existing="+LTModelVal;
			System.out.println(Description);
			actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
			TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
		}catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}
	
	public void e_LTModel_Same() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTModel="Same";
		Description="keep the LT Serial Number same";
		LTModelVal=SE_LocA.getLTModel();
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+" - "+LTModelVal;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTSerNumNull() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTSerNum=""; 
		LTSerNumVal="";
		SE_LocA.enter_LTSerNum(LTSerNumVal);
		Description="LTSerNumNull="+LTSerNumVal;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTSerNumValid() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTSerNum="Valid";
		calint++;
		calchk=String.valueOf(calint);
		if(calchk.equals(1000)){
			calint=0;
			calchk="0";
		}
		LTSerNumVal="LT"+cal+calchk;
		SE_LocA.enter_LTSerNum(LTSerNumVal);
		Description="LTSerNumValid="+LTSerNumVal;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTSerNumExisting() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTSerNum="Existing";
		try{
			Connection conn = DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();
			stm="select SerialNumber from LeakTesterDetail where Model!='"+LTModelVal+"'";
			OER_rs = statement.executeQuery(stm);
			while(OER_rs.next()){
				LTSerNumVal= OER_rs.getString(1);
			}
			OER_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			conn.close();
		}catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		SE_LocA.enter_LTSerNum(LTSerNumVal);
		Description="LTSerNumExisting="+LTSerNumVal;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTModel_SerNumExisting() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTModel="Existing";
		LTModelSerNum="Existing";
		try{
			Connection conn = DriverManager.getConnection(TestFrameWork.TestHelper.FrameworkDBConnection());
			Statement statement = conn.createStatement();

			stm="select SerialNumber from LeakTesterDetail where Model='"+LTModelVal+"'";
			OER_rs = statement.executeQuery(stm);
			while(OER_rs.next()){
				LTSerNumVal= OER_rs.getString(1);
			}
			OER_rs.close();
			statement.close(); //close the query to get the variable information from the DB
			conn.close();
			
		}catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		SE_LocA.enter_LTSerNum(LTSerNumVal);
		Description="LTModelExisting="+LTModelVal+", LTSerNumExisting="+LTSerNumVal;
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void e_LTSerNumSame() throws InterruptedException{ 
		System.out.println(getCurrentElement().getName());
		LTSerNum="Same";
		Description="keep the LT Serial Number same";
		LTSerNumVal=SE_LocA.getLTSerNum();
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Description+" - "+LTSerNumVal;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_AutoLeakTest() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Result=SE_LocV.Verify_LocationType(LocType);
		Description= LocType+" is displayed in the Location type field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LeakTesterModel() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Result=SE_LocV.Verify_LTModel(LTModelVal);
		Description= LTModel+" is displayed in the Leak Test Model field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
	public void v_LeakTestSerialNum() throws InterruptedException{
		System.out.println(getCurrentElement().getName());
		Result=SE_LocV.Verify_LTSernum(LTSerNumVal);
		Description= LTSerNum+" is displayed in the Leak Test Serial Number field.";
		System.out.println(Description);
		actualResult=actualResult+"\r\n"+getCurrentElement().getName()+"---:\r\n\t"+Result+" - "+Description;
		TH.WriteToTextFile(TestResFileName+ForFileName,actualResult);
	}
	
}
