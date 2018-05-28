package AdminUserStories.AccessPoint;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import AdminUserStories.AccessPoint.AccessPointDetail_GWAPI;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mysql.jdbc.Driver;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;
import TestFrameWork.UnifiaAdminLoginPage.*;
import AdminUserStories.AccessPoint.AccessPointDetail_GWAPI;


public class AccessPointDetailsGWGen {
	
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.UnifiaAdminLoginPage .Login_Actions LA; 
	public AdminUserStories.AccessPoint.AccessPointDetail_GWAPI AB; 
	
@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
public void edge(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		//Model Starts
		Model model = new Model();
		// Get the model from resources
		 // Connect the model to a java class, and add it to graphwalker's modelhandler.
		 // The model is to be executed using the following criteria:
		 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
		 // in the model
		 // Generator: random, walk through the model randomly
		 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
		 String FileA = "/yED_graphs/BasicLogin_AccessPoint.graphml";
		 String FileB = "/yED_graphs/AccessPointDetails.graphml";
	 
	  PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new AccessPointDetail_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .execute();
	 // Start executing the test
	 // Verify that the execution is complete, fulfilling the criteria from above.
	 //Assert.assertTrue(modelhandler.isAllModelsDone(), "Not all models are done");
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
		  FileWriter fstream = new FileWriter("AccessPoint_Edge_Summary.txt");
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

	/*@Test
	public void vertex()throws InterruptedException, StopConditionException, URISyntaxException {
		 Model model = new Model();
		 // Get the model from resources
		 	/*URL url_A = ModelHandler.class.getResource("/yED_graphs/BasicLogin_AccessPoint.graphml"); 
		 	URL url_B = ModelHandler.class.getResource("/yED_graphs/AccessPointDetails.graphml");
		 File file_A = new File(url_A.toURI());
		 File file_B = new File(url_B.toURI());
		 // Connect the model to a java class, and add it to graphwalker's modelhandler.
		 // The model is to be executed using the following criteria:
		 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
		 // in the model
		 // Generator: random, walk through the model randomly
		 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
		 String FileA = "/yED_graphs/BasicLogin_AccessPoint.graphml";
		 String FileB = "/yED_graphs/AccessPointDetails.graphml";
		 
		 PathGenerator pathGenerator = new RandomPath(new VertexCoverage(100));
		  Context context = new TestContext(model, pathGenerator);
		  context.setModel(model.build());
		  context.setPathGenerator(new RandomPath(new VertexCoverage(100)));
		  
		  Result result =  new TestBuilder()
			 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new VertexCoverage(100))))
			 .addModel(FileB, new AccessPointDetail_GWAPI().setPathGenerator(new RandomPath((StopCondition) new VertexCoverage(100))))
			 .execute();
		 
		 /*modelhandler.add("BasicLogin", new AdminConsole_GWAPI(file_A,true, new RandomPathGenerator(new VertexCoverage(1)),false));
		 modelhandler.add("AccessPoint", new AccessPointDetail_GWAPI(file_B, true, new RandomPathGenerator(new VertexCoverage (1)), false));
		 //modelhandler.add("vertex", new ScannerDetail_GWAPI(file_B, true, new RandomPathGenerator(new VertexCoverage (1)), false));
		 // Start executing the test
		 modelhandler.execute("BasicLogin");
		 // Verify that the execution is complete, fulfilling the criteria from above.
		 Assert.assertTrue(modelhandler.isAllModelsDone(), "Not all models are done");
		 // Print the statistics from graphwalker
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
			  FileWriter fstream = new FileWriter("AccessPoint_Vertex_Summary.txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(actualResult);
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		 } */

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
