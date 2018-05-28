package Regression.MultiRoom;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.condition.StopConditionException;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.condition.VertexCoverage;
import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.machine.ExecutionStatus;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.machine.SimpleMachine;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Model;
import org.graphwalker.core.model.Model.RuntimeModel;
import org.graphwalker.core.model.Vertex;
import org.graphwalker.core.statistics.*;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestBuilder;

import ScannerMessages.SoiledRoom.RegressionSoiledRoom_GWAPI;
import ScannerMessages.ReprocessingRoom.RegressionReprocessingRoom_GWAPI;
import ScannerMessages.ProcedureRoom.RegressionProcedureRoom_GWAPI;
import ScannerMessages.StorageArea.RegressionStorageArea_GWAPI;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import Regression.MultiRoom.MultiRoomController_API;
import TestFrameWork.Unifia_Admin_Selenium;


public class MultiRoom_GWGen {
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public TestFrameWork.Emulator.GetIHValues IHV;
	public GeneralFunc gf;
	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void edge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			//System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}

	Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		//DB  Updates
		gf.InsertMultiRoomData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
	   	String stmt1 = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
    	String stmt2="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
  		try{
    		Connection conn = DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
    		Statement update1 = conn.createStatement();
    		//System.out.println("stmt1="+stmt1);
    		update1.executeUpdate(stmt1);
    		//System.out.println("stmt2="+stmt2);
    		update1.executeUpdate(stmt2);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
	  	gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass); 	

  		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
  		LP_A.CloseDriver();
  		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
  		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 

	   	Model model = new Model();

		String FileA = "/yED_graphs/MultiRoom/MultiRoom_Controller.graphml"; 
		//String FileA = "/yED_graphs/MultiRoom/MultiRoom_Controller_onlyStorage.graphml";
		String FileB = "/yED_graphs/MultiRoom/MultiRoom_SoiledArea.graphml";
		String FileC = "/yED_graphs/MultiRoom/MultiRoom_ReprocessingArea.graphml";
		String FileD = "/yED_graphs/MultiRoom/MultiRoom_ProcedureRoom.graphml";
		String FileE = "/yED_graphs/MultiRoom/MultiRoom_StorageArea.graphml";
		 	 
		PathGenerator pathGenerator = new RandomPath(new TimeDuration(60, TimeUnit.HOURS));
		Context context = new TestContext (model, pathGenerator);
		context.setModel(model.build());
		context.setPathGenerator(new RandomPath(new TimeDuration(60, TimeUnit.HOURS)));
	  
		Result result =  new TestBuilder()
		/*.addModel(FileA, new MultiRoomController_API().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		.addModel(FileB, new RegressionSoiledRoom_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		.addModel(FileC, new RegressionReprocessingRoom_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		.addModel(FileD, new RegressionProcedureRoom_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		.addModel(FileE, new RegressionStorageArea_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))*/

		.addModel(FileA, new MultiRoomController_API().setPathGenerator(new RandomPath(new TimeDuration(60, TimeUnit.HOURS))))
		.addModel(FileB, new RegressionSoiledRoom_GWAPI().setPathGenerator(new RandomPath(new TimeDuration(60, TimeUnit.HOURS))))
		.addModel(FileC, new RegressionReprocessingRoom_GWAPI().setPathGenerator(new RandomPath(new TimeDuration(60, TimeUnit.HOURS))))
		.addModel(FileD, new RegressionProcedureRoom_GWAPI().setPathGenerator(new RandomPath(new TimeDuration(60, TimeUnit.HOURS))))
		.addModel(FileE, new RegressionStorageArea_GWAPI().setPathGenerator(new RandomPath(new TimeDuration(60, TimeUnit.HOURS))))
		.execute();
	 
	
		// Print the statistics from graphwalker
		 IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);

		String actualResult = "Statistics for 100% Edge Coverage:";
		if (result.hasErrors()) {
	        for (String error : result.getErrors()) {
	        	actualResult = actualResult  + "\r\n\r\n" + error;
	            //System.out.println(error);
	        }
	    }
		actualResult = actualResult + "\r\n\r\n" + result.getResultsAsString();
		//System.out.println(actualResult);
	 
		try{
		  // Create file 
		  FileWriter fstream = new FileWriter("MultiRoom_Summary.txt");
		  BufferedWriter out = new BufferedWriter(fstream);
		  out.write(actualResult);
		  //Close the output stream
		  out.close();
		}catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		}
		LP_A.CloseDriver();

	}

	  @AfterTest
	  public void PostTest() throws IOException{
	  	LP_A.CloseDriver();
	  }
	  
	public class TestContext extends ExecutionContext {
		public TestContext(Model model, PathGenerator pathGenerator) {
			super(model, pathGenerator);
		}
	
}
	}

