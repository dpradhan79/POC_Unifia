package AdminUserStories.Recon.Soiled;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.model.Model;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestBuilder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.TestDataFunc;

public class SoiledAreaGWGen {
	public AdminUserStories.Recon.Soiled.SoiledArea_GWAPI SA;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public TestFrameWork.UnifiaAdminUserPage.User_Actions UA;
	public TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	public TestFrameWork.Emulator.GetIHValues IHV;
	TestFrameWork.UnifiaAdminLoginPage.Login_Actions LGPA;
	private ITConsole.ITConScanSimActions IT_A;
	private TestFrameWork.Unifia_Admin_Selenium UAS;
	private TestDataFunc TDF;
	private GeneralFunc GF;
	public int KE=1;
	public int Bioburden=0;
	public int Culture=0;
	private String presWorkDir=System.getProperty("user.dir");
	private String fileSource=System.getProperty("user.dir")+"\\ITConsole_XML_Files";

	private String fileDestFolder="\\XMLFolder";
	private String ReconScanDataXMLFile="ReconScanData_ITConsole";

	@Test(dataProvider="BrowserandURL",dataProviderClass=Unifia_Admin_Selenium.class)
	public void edge(String browserP, String URL,String AdminDB) throws Exception {
		//select the Driver type Grid or local
		if (Unifia_Admin_Selenium.parallelExecutionType && Unifia_Admin_Selenium.driverType.equalsIgnoreCase("LOCAL")){
			System.out.println("If execution type is parallel i.e., 'parallelExecutionType = true' then driverType should be always 'GRID'");
			System.exit(1);
		}
		Unifia_Admin_Selenium.DriverSelection(browserP,URL,AdminDB);
		Unifia_Admin_Selenium.resultFlag="Pass";
		Unifia_Admin_Selenium.Result="Pass";
		Unifia_Admin_Selenium.isRecon=true;
		//
		//String fileDestination="\\\\"+UAS.Env+UAS.itConsoleTestDataPath+fileDestFolder;
		String fileDestination="\\\\"+UAS.Env+UAS.scanSimPath+fileDestFolder;
		String batchFileDestination="\\\\"+UAS.Env+UAS.scanSimPath;
		String xmlPath=UAS.scanSimPathTool+fileDestFolder+"\\";
		//DB  Updates
		GF.SyncRemoteMachineTime(Unifia_Admin_Selenium.KE_Env, Unifia_Admin_Selenium.KEMachine_Username, Unifia_Admin_Selenium.KEMachine_pswd, URL);
		TDF.insertKEMasterData(Unifia_Admin_Selenium.KE_Url,Unifia_Admin_Selenium.KE_UserName,Unifia_Admin_Selenium.KE_Pwd);
		TDF.insertMasterData(Unifia_Admin_Selenium.url, Unifia_Admin_Selenium.user, Unifia_Admin_Selenium.pass,KE, Bioburden, Culture);
		GF.RestartIISServices(Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.userName, Unifia_Admin_Selenium.IISPass);

		boolean isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, ReconScanDataXMLFile+".xml",fileSource,fileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+ReconScanDataXMLFile+".xml"+" is copied to "+fileDestination);
		}else{
			System.out.println("#Failed!#- "+ReconScanDataXMLFile+".xml"+" is not copied to "+fileDestination);
		}
		//Copying Runbatch.bat file to server machine
		isFileCopied= IT_A.copyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.batchFile,fileSource,batchFileDestination);
		if(isFileCopied){
			System.out.println("Pass- "+UAS.batchFile+" is copied to "+batchFileDestination);
		}else{
			System.out.println("#Failed!#- "+UAS.batchFile+" is not copied to "+batchFileDestination);
		}
		//Update the environment and xml file in  Runbatch.bat file
		IT_A.modifyFile(UAS.Env, UAS.userName, UAS.IISPass, UAS.scanSimPath, UAS.batchFile,ReconScanDataXMLFile+".xml", xmlPath);
		//execute ScanSimUI
		IT_A.executeScanSim(UAS.Env, UAS.userName, UAS.IISPass,UAS.scanSimPathTool,UAS.ITConsoleExecTime,0);

		LGPA.Launch_Unifia(Unifia_Admin_Selenium.Admin_URL);
		UA.selectUserRoleNLogin(browserP, URL, Unifia_Admin_Selenium.roleMgr, Unifia_Admin_Selenium.userQV1, Unifia_Admin_Selenium.userQV1Pwd);

		Model model = new Model();
		String FileA = "/yED_graphs/Reconciliation/SoiledArea.graphml";
		PathGenerator pathGenerator = new RandomPath(new EdgeCoverage(100));
		Context context = new TestContext(model, pathGenerator);
		context.setModel(model.build());
		context.setPathGenerator(new RandomPath(new EdgeCoverage(100)));
		Result result =  new TestBuilder()
		 .addModel(FileA, new SoiledArea_GWAPI().setPathGenerator(new RandomPath((StopCondition) new EdgeCoverage(100))))
		 .execute();
		
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
		  FileWriter fstream = new FileWriter("SoiledArea_Edge_Summary.txt");
		  BufferedWriter out = new BufferedWriter(fstream);
		  out.write(actualResult);
		  //Close the output stream
		  out.close();
		  }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
		  }
		IHV.Close_Exec_Log(Unifia_Admin_Selenium.XMLFileName, "Test Completed", Unifia_Admin_Selenium.TestCaseNumber);

	 if (Unifia_Admin_Selenium.resultFlag.contains("#Failed!#")){
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

