package AdminUserStories;
import java.io.File;
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
/**import AdminUserStories.ExamType.*;
import AdminUserStories.Facility.*;
import AdminUserStories.Location.*;
import AdminUserStories.Roles.*;
import AdminUserStories.Scanner.*;
import AdminUserStories.ScopeModel.*;
import AdminUserStories.Scopes.*;
import AdminUserStories.Staff.*;
import AdminUserStories.User.*;**/



import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.model.Model;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions;

public class AdminConsoleGWGen {
	
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void edge(String browserP, String URL,String AdminDB) throws InterruptedException, StopConditionException, URISyntaxException, IOException {
			//select the Driver type Grid or local
			if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
				System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
				System.exit(1);
			}
			Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
	 Model model = new Model();
	 // Get the model from resources
	// 	URL url_A = ModelHandler.class.getResource("/yED_graphs/BasicAdmin_V02.graphml"); 
	// 	URL url_B = ModelHandler.class.getResource("/yED_graphs/ExamType_v00.graphml"); 
	// 	URL url_C = ModelHandler.class.getResource("/yED_graphs/ExamTypeDetail_v00.graphml");
	// 	URL url_D = ModelHandler.class.getResource("/yED_graphs/Facility_V00.graphml");
	// 	URL url_E = ModelHandler.class.getResource("/yED_graphs/FacilityDetail_V01.graphml");
	// 	URL url_F = ModelHandler.class.getResource("/yED_graphs/Locations_v00.graphml");
	// 	URL url_G = ModelHandler.class.getResource("/yED_graphs/LocationsDetail_v00.graphml");
	// 	URL url_H = ModelHandler.class.getResource("/yED_graphs/Roles_v00.graphml");
	// 	URL url_I = ModelHandler.class.getResource("/yED_graphs/RolesDetail_v00.graphml");
	// 	URL url_J = ModelHandler.class.getResource("/yED_graphs/Scanners_v00.graphml");
	// 	URL url_K = ModelHandler.class.getResource("/yED_graphs/ScannersDetail_v00.graphml");
	// 	URL url_L = ModelHandler.class.getResource("/yED_graphs/ScopeModel_v00.graphml");
	// 	URL url_M = ModelHandler.class.getResource("/yED_graphs/ScopeModelDetail_v00.graphml");
	// 	URL url_N = ModelHandler.class.getResource("/yED_graphs/Scopes_v00.graphml");
	// 	URL url_O = ModelHandler.class.getResource("/yED_graphs/ScopesDetail_v00.graphml");
	// 	URL url_P = ModelHandler.class.getResource("/yED_graphs/Staff_v00.graphml");
	// 	URL url_Q = ModelHandler.class.getResource("/yED_graphs/StaffDetail_v01.graphml");
	// 	URL url_R = ModelHandler.class.getResource("/yED_graphs/Users_v00.graphml");
	// 	URL url_S = ModelHandler.class.getResource("/yED_graphs/UserDetail_v00.graphml");
	// File file_A = new File(url_A.toURI());
	 //File file_B = new File(url_B.toURI());
//	 File file_C = new File(url_C.toURI()); 
//	 File file_D = new File(url_D.toURI()); 
//	 File file_E = new File(url_E.toURI());
//	 File file_F = new File(url_F.toURI());
//	 File file_G = new File(url_G.toURI());
//	 File file_H = new File(url_H.toURI());
//	 File file_I = new File(url_I.toURI());
//	 File file_J = new File(url_J.toURI());
//	 File file_K = new File(url_K.toURI());
//	 File file_L = new File(url_L.toURI());
//	 File file_M = new File(url_M.toURI());
//	 File file_N = new File(url_N.toURI());
//	 File file_O = new File(url_O.toURI());
//	 File file_P = new File(url_P.toURI());
//	 File file_Q = new File(url_Q.toURI());
//	 File file_R = new File(url_R.toURI());
//	 File file_S = new File(url_S.toURI());
	 
	 // Connect the model to a java class, and add it to graphwalker's modelhandler.
	 // The model is to be executed using the following criteria:
	 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
	 // in the model
	 // Generator: random, walk through the model randomly
	 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
	 String FileA = "/yED_graphs/BasicAdmin_V02.graphml";	 
	 
	 PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
	  Context context = new TestContext(model, pathGenerator);
	  context.setModel(model.build());
	  context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
	  
	  Result result =  new TestBuilder()
		 .addModel(FileA, new AdminConsole_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .execute();
//	 modelhandler.add("BasicAdmin", new AdminConsole_GWAPI(file_A,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("ExamType", new ExamType_GWAPI(file_B,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("ExamTypeDetail", new ExamTypeDetails_GWAPI(file_C,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("Facility", new Facility_GWAPI(file_D,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("FacilityDetail", new FacilityDetail_GWAPI_v02(file_E,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("Location", new Location_GWAPI(file_F,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("LocationDetail", new LocationDetails_GWAPI(file_G,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("Role", new Role_GWAPI(file_H,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("RoleDetail", new RoleDetails_GWAPI(file_I,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("Scanners", new Scanner_GWAPI(file_J,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("ScannerDetail", new ScannerDetail_GWAPI(file_K,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("ScopeModel", new ScopeModel_GWAPI(file_L,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("ScopeModelDetail", new ScopeModelDetails_GWAPI(file_M,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("Scopes", new Scope_GWAPI(file_N,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("ScopeDetail", new ScopeDetails_GWAPI(file_O,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("Staff", new Staff_GWAPI(file_P,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("StaffDetail", new StaffDetails_GWAPI(file_Q,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("User", new User_GWAPI(file_R,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
//	 modelhandler.add("UserDetail", new UserDetails_GWAPI(file_S,true, new RandomPathGenerator(new EdgeCoverage(1)),false));
	 
	 // Start executing the test
	// modelhandler.execute("BasicAdmin");
	 // Verify that the execution is complete, fulfilling the criteria from above.
	// Assert.assertTrue(modelhandler.isAllModelsDone(), "Not all models are done");
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
		LP_A.CloseDriver();
	 }
	 
	/*@Test
	public void vertex()throws InterruptedException, StopConditionException, URISyntaxException {
		 ModelHandler modelhandler = new ModelHandler();
		 // Get the model from resources
		 	URL url_A = ModelHandler.class.getResource("/yED_graphs/BasicAdmin_V01.graphml"); 
//		 	URL url_B = ModelHandler.class.getResource("/yED_graphs/ExamType_v00.graphml"); 
//		 	URL url_C = ModelHandler.class.getResource("/yED_graphs/ExamTypeDetail_v00.graphml");
//		 	URL url_D = ModelHandler.class.getResource("/yED_graphs/Facility_V00.graphml");
//		 	URL url_E = ModelHandler.class.getResource("/yED_graphs/FacilityDetail_V01.graphml");
//		 	URL url_F = ModelHandler.class.getResource("/yED_graphs/Locations_v00.graphml");
//		 	URL url_G = ModelHandler.class.getResource("/yED_graphs/LocationsDetail_v00.graphml");
//		 	URL url_H = ModelHandler.class.getResource("/yED_graphs/Roles_v00.graphml");
//		 	URL url_I = ModelHandler.class.getResource("/yED_graphs/RolesDetail_v00.graphml");
//		 	URL url_J = ModelHandler.class.getResource("/yED_graphs/Scanners_v00.graphml");
//		 	URL url_K = ModelHandler.class.getResource("/yED_graphs/ScannersDetail_v00.graphml");
//		 	URL url_L = ModelHandler.class.getResource("/yED_graphs/ScopeModel_v00.graphml");
//		 	URL url_M = ModelHandler.class.getResource("/yED_graphs/ScopeModelDetail_v00.graphml");
//		 	URL url_N = ModelHandler.class.getResource("/yED_graphs/Scopes_v00.graphml");
//		 	URL url_O = ModelHandler.class.getResource("/yED_graphs/ScopesDetail_v00.graphml");
//		 	URL url_P = ModelHandler.class.getResource("/yED_graphs/Staff_v00.graphml");
//		 	URL url_Q = ModelHandler.class.getResource("/yED_graphs/StaffDetail_v01.graphml");
//		 	URL url_R = ModelHandler.class.getResource("/yED_graphs/Users_v00.graphml");
//		 	URL url_S = ModelHandler.class.getResource("/yED_graphs/UserDetail_v00.graphml");
		 File file_A = new File(url_A.toURI());
//		 File file_B = new File(url_B.toURI());
//		 File file_C = new File(url_C.toURI()); 
//		 File file_D = new File(url_D.toURI()); 
//		 File file_E = new File(url_E.toURI());
//		 File file_F = new File(url_F.toURI());
//		 File file_G = new File(url_G.toURI());
//		 File file_H = new File(url_H.toURI());
//		 File file_I = new File(url_I.toURI());
//		 File file_J = new File(url_J.toURI());
//		 File file_K = new File(url_K.toURI());
//		 File file_L = new File(url_L.toURI());
//		 File file_M = new File(url_M.toURI());
//		 File file_N = new File(url_N.toURI());
//		 File file_O = new File(url_O.toURI());
//		 File file_P = new File(url_P.toURI());
//		 File file_Q = new File(url_Q.toURI());
//		 File file_R = new File(url_R.toURI());
//		 File file_S = new File(url_S.toURI());
		 // Connect the model to a java class, and add it to graphwalker's modelhandler.
		 // The model is to be executed using the following criteria:
		 // EFSM: Extended finite state machine is set to true, which means we are using the data domain
		 // in the model
		 // Generator: random, walk through the model randomly
		 // Stop condition: Edge coverage 100%, we want to walk every edge in the model.
		 modelhandler.add("BasicAdmin", new AdminConsole_GWAPI(file_A,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("ExamType", new ExamType_GWAPI(file_B,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("ExamTypeDetail", new ExamTypeDetails_GWAPI(file_C,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("Facility", new Facility_GWAPI(file_D,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("FacilityDetail", new FacilityDetail_GWAPI_v02(file_E,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("Location", new Location_GWAPI(file_F,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("LocationDetail", new LocationDetails_GWAPI(file_G,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("Role", new Role_GWAPI(file_H,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("RoleDetail", new RoleDetails_GWAPI(file_I,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("Scanners", new Scanner_GWAPI(file_J,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("ScannerDetail", new ScannerDetail_GWAPI(file_K,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("ScopeModel", new ScopeModel_GWAPI(file_L,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("ScopeModelDetail", new ScopeModelDetails_GWAPI(file_M,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("Scopes", new Scope_GWAPI(file_N,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("ScopeDetail", new ScopeDetails_GWAPI(file_O,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("Staff", new Staff_GWAPI(file_P,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("StaffDetail", new StaffDetails_GWAPI(file_Q,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("User", new User_GWAPI(file_R,true, new RandomPathGenerator(new VertexCoverage(1)),false));
//		 modelhandler.add("UserDetail", new UserDetails_GWAPI(file_S,true, new RandomPathGenerator(new VertexCoverage(1)),false));


		 // Start executing the test
		 modelhandler.execute("BasicAdmin");
		 // Verify that the execution is complete, fulfilling the criteria from above.
		 Assert.assertTrue(modelhandler.isAllModelsDone(), "Not all models are done");
		 // Print the statistics from graphwalker
		 String actualResult = modelhandler.getStatistics();
		 System.out.println(actualResult);
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
