package ScannerMessages.ExamQueue;

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
import  org.graphwalker.core.statistics.*;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class RegressionExamQueue_GWGen {
	TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	public GeneralFunc gf;
	public int KE=0;
	public int Bioburden=0;
	public int Culture=0;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.Emulator.GetIHValues IHV;
	public ScannerMessages.ExamQueue.RegressionExamQueue_GWAPI AB;
	private TestDataFunc TDF;

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void edge(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException, SQLException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}

		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		//DB updates
		//gf.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
       	String stmt1 = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
    	String stmt2="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
  		try{
    		Connection conn = DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
    		Statement update1 = conn.createStatement();
    		System.out.println("stmt1="+stmt1);
    		update1.executeUpdate(stmt1);
    		System.out.println("stmt2="+stmt2);
    		update1.executeUpdate(stmt2);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
  		//Restart of IIS 
  		gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);	
		//Model Starts
		Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's modelhandler.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
		LP_A.CloseDriver();
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL); 
	  String FileA = "/yED_graphs/ScannerMessages/ExamQueue.graphml";
	  PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Result result =  new TestBuilder()
		 .addModel(FileA, new RegressionExamQueue_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .execute();
	 // Print the statistics from graphwalker
	  IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
	  String actualResult = "Statistics for 100% Edge Coverage:";
		if (result.hasErrors()) {
	        for (String error : result.getErrors()) {
	        	actualResult = actualResult  + "\r\n\r\n" + error;
	            System.out.println(error);
	        }
	    }
		actualResult = actualResult + "\r\n\r\n" + result.getResultsAsString();
		System.out.println(actualResult);
	 try{
		  // Create file 
		  FileWriter fstream = new FileWriter("RegressionExamQueue_Edge_Summary.txt");
		  BufferedWriter out = new BufferedWriter(fstream);
		  out.write(actualResult);
		  //Close the output stream
		  out.close();
		  }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  }
	 if (AB.actualResult.contains("#Failed!#")||(Unifia_Admin_Selenium.resultFlag.contains("#Failed!#"))){
			org.testng.Assert.fail("Test has failed");
		}
		LP_A.CloseDriver();  
	 }
/**	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void vertex(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		 //DB Updates
		 Unifia_Admin_Selenium.ScannerCount=0;
		 
		gf.InsertSimulatedScanSeedData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
       	String stmt1 = "IF (OBJECT_ID('sp_GetPatients_EQUAL') IS NOT NULL) DROP PROCEDURE sp_GetPatients_EQUAL";
    	String stmt2="CREATE PROCEDURE [dbo].[sp_GetPatients_EQUAL] @PatientID_text varchar(50) AS BEGIN SET NOCOUNT ON;BEGIN TRY OPEN SYMMETRIC KEY UNIFIA_SYMKEY_01 DECRYPTION BY PASSWORD = 'A1HP5hI12hM14h@0UN1f1a'; SELECT [PatientID_PK], [LastUpdatedDateTime], CONVERT(varchar(50), DECRYPTBYKEY([PatientID])) AS 'PatientID' FROM [dbo].Patient WHERE [PatientId_PK]=+@PatientID_text CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01; END TRY BEGIN CATCH IF EXISTS (SELECT * FROM sys.openkeys WHERE key_name = 'UNIFIA_SYMKEY_01') CLOSE SYMMETRIC KEY UNIFIA_SYMKEY_01;END CATCH END";
  		try{
    		Connection conn = DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);
    		Statement update1 = conn.createStatement();
    		System.out.println("stmt1="+stmt1);
    		update1.executeUpdate(stmt1);
    		System.out.println("stmt2="+stmt2);
    		update1.executeUpdate(stmt2);
    		conn.close();
    	}
    	catch (SQLException ex){
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());	
    	}
	  	//Restart of IIS 
	  	 gf.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);				
		 LGPA.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL);
		 //Model Starts
		 Model model = new Model();
		 String FileA = "/yED_graphs/ScannerMessages/ExamQueue.graphml";
		 PathGenerator pathGenerator = new RandomPath(new VertexCoverage(100));
		  Context context = new TestContext(model, pathGenerator);
		  context.setModel(model.build());
		  context.setPathGenerator(new RandomPath(new VertexCoverage(100)));
		  Result result =  new TestBuilder()
			 .addModel(FileA, new RegressionExamQueue_GWAPI().setPathGenerator(new RandomPath((StopCondition) new VertexCoverage(100))))
			 .execute();
		 // Print the statistics from graphwalker
		  IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);
		  String actualResult = "Statistics for 100% Vertex Coverage:";
			if (result.hasErrors()) {
		        for (String error : result.getErrors()) {
		        	actualResult = actualResult  + "\r\n\r\n" + error;
		            System.out.println(error);
		        }
		    }
			actualResult = actualResult + "\r\n\r\n" + result.getResultsAsString();
			System.out.println(actualResult);
		 try{
			  // Create file 
			  FileWriter fstream = new FileWriter("RegressionExamQueue_Vertex_Summary.txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(actualResult);
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
			LP_A.CloseDriver();
		 }**/
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
