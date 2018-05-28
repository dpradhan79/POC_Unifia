package AdminUserStories.Location;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

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
import AdminUserStories.Location.LocationDetails_GWAPI;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class LocationDetail_ReprocessingFields_GWGen {
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public AdminUserStories.Location.LocationDetails_GWAPI AB;
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void OERModel_SNoEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingOERModel_SNo.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingOERModel_SNo_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingOERModel_SNo_Edge_Summary.txt");
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
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void VaporCycleEdge(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	Model model = new Model();
	 // Get the model from resources

	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingVaporCycles.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingVaporCycle_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingVaporCycle_Edge_Summary.txt");
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
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void DisinfectantDaysEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
    		
	 Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingDisinfectantDays.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingDisinfectantDays_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingDisinfectantDays_Edge_Summary.txt");
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
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void DisinfectantCycleEdge(String browserP, String URL, String AdminDB ) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	Model model = new Model();
	 // Get the model from resources
	 	URL url_A = Model.class.getResource("/yED_graphs/BasicLogin_Location.graphml"); 
	 	URL url_B = Model.class.getResource("/yED_graphs/LocationDetails_ReprocessingDisinfectantCycle.graphml");
	 File file_A = new File(url_A.toURI());
	 File file_B = new File(url_B.toURI());
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingDisinfectantCycle.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingDisinfectantCycle_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingDisinfectantCycle_Edge_Summary.txt");
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

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void WaterCycleEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingWaterCycle.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingWaterCycle_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingWaterCycle_Edge_Summary.txt");
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

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void WaterDaysEdge(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources

	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingWaterDays.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingWaterDays_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingWaterDays_Edge_Summary.txt");
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

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void AirCycleEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources

	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingAirCycle.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingAirCycle_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingAirCycle_Edge_Summary.txt");
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
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void AirDaysEdge(String browserP, String URL, String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);		
	Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingAirDays.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingAirDays_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingAirDays_Edge_Summary.txt");
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
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void CycleTimeEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingCycleTime.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingCycleTime_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingCycleTime_Edge_Summary.txt");
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
		
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void AlcoholCycleEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingAlcoholCycle.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingAlcoholCycle_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingAlcoholCycle_Edge_Summary.txt");
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
	
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void AlcoholDaysEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingAlcoholDays.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingAlcoholDays_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingAlcoholDays_Edge_Summary.txt");
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
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void DetergentCycleEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingDetergentCycle.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingDetergentCycle_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingDetergentCycle_Edge_Summary.txt");
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
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void DetergentDaysEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingDetergentDays.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingDetergentDays_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingDetergentDays_Edge_Summary.txt");
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

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void VaporDaysedge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingVaporDays.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingVaporDays_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingVaporDays_Edge_Summary.txt");
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
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void PMCycleEdge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingPMCycle.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingPMCycle_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingPMCycle_Edge_Summary.txt");
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
	
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
    public void PMDaysedge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
    		//select the Driver type Grid or local
    		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
    			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
    			System.exit(1);
    		}
    		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources
	 // Connect the model to a java class, and add it to graphwalker's model.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicLogin_Location.graphml";
	 String FileB = "/yED_graphs/LocationDetails_ReprocessingPMDays.graphml";
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  Unifia_Admin_Selenium.FileName="LocationReprocessingPMDays_TestSummary_";
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .addModel(FileB, new LocationDetails_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
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
		  FileWriter fstream = new FileWriter("LocationReprocessingPMDays_Edge_Summary.txt");
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
