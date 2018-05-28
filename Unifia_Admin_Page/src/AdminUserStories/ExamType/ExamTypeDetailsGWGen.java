package AdminUserStories.ExamType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
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

import AdminUserStories.AdminConsole_GWAPI;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class ExamTypeDetailsGWGen {
	
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public AdminUserStories.ExamType.ExamTypeDetails_GWAPI AB;

	//public static String url = "jdbc:sqlserver://10.170.93.180:1433;databaseName=UnifiaIT";
	//URL with port #:  jdbc:sqlserver://10.170.93.180:1433;databaseName=UnifiaStagingComplex
    //public static String user = "unifia";
    //public static String pass = "0lympu$123";
    //public static String connstring = url+";user="+user+";password="+pass;
    //Connection conn= null;
    
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void edge(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		/*if (Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			Unifia_Admin_Selenium.setDriver(browserP);
    			Unifia_Admin_Selenium.setEnvironment(URL);
    		}else if(Unifia_Admin_Selenium.driverType.equalsIgnoreCase("GRID")){
    			Unifia_Admin_Selenium.setGridDriver(browserP,URL);
    		}*/
    		
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    		try{
    			Connection conn = DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
    			Statement update1 = conn.createStatement();
    			update1.executeUpdate("Update ScopeType Set IsActive=0;");
    			conn.close();
    		}
    		catch (SQLException ex){
    		    // handle any errors
    		    System.out.println("SQLException: " + ex.getMessage());
    		    System.out.println("SQLState: " + ex.getSQLState());
    		    System.out.println("VendorError: " + ex.getErrorCode());	
    		}
    		
		Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's modelhandler.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_ExamType.graphml";
	 String FileB = "/yED_graphs/ExamType.graphml";
	 String FileC = "/yED_graphs/ExamTypeDetail.graphml";
	 
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 //.addModel(FileB, new ExamType_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileC, new ExamTypeDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .execute();

	 // Print the statistics from graphwalker
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
		  FileWriter fstream = new FileWriter("ExamType_Edge_Summary.txt");
		  BufferedWriter out = new BufferedWriter(fstream);
		  out.write(actualResult);
		  //Close the output stream
		  out.close();
		  }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  } 
	 if (AB.actualResult.contains("#Failed!#")){
			org.testng.Assert.fail("Test has failed");
	 }
		  
		LP_A.CloseDriver();

	 }

/*	@Test
	public void vertex()throws InterruptedException, StopConditionException, URISyntaxException {
		 ModelHandler modelhandler = new ModelHandler();
		 // Get the model from resources
		 URL url1 = ModelHandler.class.getResource("/yED_graphs/BasicLogin_ExamType.graphml");
	 	 URL url2 = ModelHandler.class.getResource("/yED_graphs/ExamType.graphml");
	 	 URL url3 = ModelHandler.class.getResource("/yED_graphs/ExamTypeDetail.graphml");
		 File file1 = new File(url1.toURI());
		 File file2 = new File(url2.toURI());
		 File file3 = new File(url3.toURI());
		 // Connect the model to a java class, and add it to graphwalker's modelhandler.
		 // The model is to be executed using the following criteria:
		 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
		 // in the model
		 // Generator: random, walk through the model randomly
		 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
		 modelhandler.add("BL_Vertex", new AdminConsole_GWAPI(file1, true, new RandomPathGenerator(new VertexCoverage (1)), false));
		 modelhandler.add("ET_Vertex", new ExamType_GWAPI(file2, true, new RandomPathGenerator(new VertexCoverage (1)), false));
		 modelhandler.add("ETD_Vertex", new ExamTypeDetails_GWAPI(file3, true, new RandomPathGenerator(new VertexCoverage (1)), false));
		 // Start executing the test
		 modelhandler.execute("BL_Vertex");
		 // Verify that the execution is complete, fulfilling the criteria from above.
		 Assert.assertTrue(modelhandler.isAllModelsDone(), "Not all models are done");
		 // Print the statistics from graphwalker
		 String actualResult = modelhandler.getStatistics();
		 System.out.println(actualResult);
		 try{
		  // Create file 
		  FileWriter fstream = new FileWriter("ExamType_Vertex_Summary.txt");
		  BufferedWriter out = new BufferedWriter(fstream);
		  out.write(actualResult);
		  //Close the output stream
		  out.close();
		  }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  }
		 } */
	
	public class TestContext extends ExecutionContext {
		public TestContext(Model model, PathGenerator pathGenerator) {
			super(model, pathGenerator);
		}
	}
	
	@AfterTest
	public void PostTest() throws IOException{
		try{
			Connection conn = DriverManager.getConnection(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass);	
			Statement update1 = conn.createStatement();
			update1.executeUpdate("Update ScopeType Set IsActive=1;");
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		LP_A.CloseDriver();
		    
    }
		
}
