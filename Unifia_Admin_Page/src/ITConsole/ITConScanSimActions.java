package ITConsole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import TestFrameWork.Unifia_Admin_Selenium;

public class ITConScanSimActions {
	
	public static String log="";
	public static String PWD;
	public static Connection conn;
	private static TestFrameWork.UnifiaAdminLandingPage.LandingPage_Actions LP_A;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	
	public static boolean ITConsole(String SprintMachine, String UserName, String Password, String BatchfilePath, Integer ExecWaitTime, int KE) throws Exception{
		log="";
		String command="cd \\ &cd "+UAS.psexecPath+" & psexec \\\\"+SprintMachine+" -u "+UserName+" -p "+Password+" cmd /c (cd \\ ^&"+BatchfilePath+"\\Runbatch.bat)";
    	String command2="tasklist /s \\\\"+SprintMachine+" /U "+UserName+" /P "+Password;
        String outputfile="c:\\temp.txt";
    	System.out.println(command);
    	try{
    		System.out.println("Starting ITConsole");
    		log+="\t\t\t\t\t ITConsole_Summary \r\n Starting ITConsole";
            ExecuteCommand(command,"");
            Thread.sleep(15000);
            System.out.println("ITConsole started successfully");
            log+="\r\n ITConsole execution started";
            ExecWaitTime=ExecWaitTime*60;
            Integer iExecLoop=ExecWaitTime/180;
            
            for(int iExeCntr =0; iExeCntr<=iExecLoop; iExeCntr++ ){
            	OER_Simulator.Simulator_Runtime sim =new OER_Simulator.Simulator_Runtime();
            	ExecuteCommand(command2,outputfile);
            	if(KE==1){
            		for(int i=1; i<=6; i++){
            			Thread.sleep(30000);
            			try{
            				conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
            				Statement statement=conn.createStatement();
            				String stmt="select count(*) from ItemHistory";
            				System.out.println(stmt);
            				ResultSet rs=statement.executeQuery(stmt);
            				int rowcount=0;
            				while(rs.next()){
            					rowcount=rs.getInt(1);
            				}
            				if(rowcount>=82){
            					Thread.sleep(60000);
            					LP_A.CloseDriver();
            					sim.Test(Unifia_Admin_Selenium.ExecBrowser, Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.DBName);
            					KE=0;
            					break;
            				}
            				
            			}catch(SQLException ex){
           				 // handle any errors
            			    System.out.println("SQLException: " + ex.getMessage());
            			    System.out.println("SQLState: " + ex.getSQLState());
            			    System.out.println("VendorError: " + ex.getErrorCode());
            			}
            		}
            	}else{
            		Thread.sleep(180000);
            	}
            	String content2=new String(Files.readAllBytes(Paths.get(outputfile)));
            	if (!content2.toUpperCase().contains("ITCONSOLE")){
            		System.out.println("ITConsole execution Completed");
            		log+="\r\n ITConsole execution Completed";
            		log+="\r\n ========================================";
            		return true;
            	}
            }
            System.out.println("ITConsole did not complete in "+Unifia_Admin_Selenium.ITConsoleExecTime +" mins");
            log+="\r\n ITConsole did not complete in "+Unifia_Admin_Selenium.ITConsoleExecTime +" mins";     
    	}catch(IOException e){
    		System.out.println("Start of ITConsole Failed");
    		log+="\r\n Start of ITConsole Failed";
    		System.out.println("Error in starting ITConsole \n"+e.getMessage());
    		log+="\r\n Error in starting ITConsole \n"+e.getMessage();
    	}
    	log+="\r\n ========================================";
		return false;
    }
	
	public static boolean executeScanSim(String SprintMachine, String UserName, String Password, String BatchfilePath, Integer ExecWaitTime, int KE) throws Exception{
		log="";
		String command="cd \\ &cd "+UAS.psexecPath+" & psexec \\\\"+SprintMachine+" -u "+UserName+" -p "+Password+" cmd /c (cd \\ ^&"+BatchfilePath+"\\Runbatch.bat)";
		System.out.println(command);
		ExecuteCommand(command,"");
		
    	String command2="tasklist /s \\\\"+SprintMachine+" /U "+UserName+" /P "+Password;
    	String outputfile=System.getProperty("user.dir");
        outputfile=outputfile+"\\temp.txt";
    	System.out.println(command2);
    	System.out.println(outputfile);
    	try{
    		System.out.println("Starting scanSim");
    		log+="\t\t\t\t\t scanSim_Summary \r\n Starting scanSim";
            ExecuteCommand(command,"");
            Thread.sleep(15000);
            System.out.println("scanSim started successfully");
            log+="\r\n scanSim execution started";
            ExecWaitTime=ExecWaitTime*60;
            Integer iExecLoop=ExecWaitTime/180;
            
            for(int iExeCntr =0; iExeCntr<=iExecLoop; iExeCntr++ ){
            	OER_Simulator.Simulator_Runtime sim =new OER_Simulator.Simulator_Runtime();
            	ExecuteCommand(command2,outputfile);
            	Thread.sleep(15000);
            	if(KE==1){
            		for(int i=1; i<=6; i++){
            			Thread.sleep(30000);
            			try{
            				conn=DriverManager.getConnection(Unifia_Admin_Selenium.connstring);
            				Statement statement=conn.createStatement();
            				String stmt="select count(*) from ItemHistory";
            				System.out.println(stmt);
            				ResultSet rs=statement.executeQuery(stmt);
            				int rowcount=0;
            				while(rs.next()){
            					rowcount=rs.getInt(1);
            				}
            				if(rowcount>=82){
            					Thread.sleep(60000);
            					LP_A.CloseDriver();
            					sim.Test(Unifia_Admin_Selenium.ExecBrowser, Unifia_Admin_Selenium.Env, Unifia_Admin_Selenium.DBName);
            					KE=0;
            					break;
            				}
            			}catch(SQLException ex){
           				 // handle any errors
            			    System.out.println("SQLException: " + ex.getMessage());
            			    System.out.println("SQLState: " + ex.getSQLState());
            			    System.out.println("VendorError: " + ex.getErrorCode());
            			}
            		}
            	}else{
            		Thread.sleep(165000);
            	}
	            	
            	String content2=new String(Files.readAllBytes(Paths.get(outputfile)));
            	if (!content2.contains(Unifia_Admin_Selenium.scanSimTxt)){
            		System.out.println("scamSim execution Completed");
            		log+="\r\n scamSim execution Completed";
            		log+="\r\n ========================================";
            		return true;
            	}
            }
            System.out.println("scamSim did not complete in "+Unifia_Admin_Selenium.ITConsoleExecTime +" mins");
            log+="\r\n scamSim did not complete in "+Unifia_Admin_Selenium.ITConsoleExecTime +" mins";     
    	}catch(IOException e){
    		System.out.println("Start of scamSim Failed");
    		log+="\r\n Start of scamSim Failed";
    		System.out.println("Error in starting scamSim \n"+e.getMessage());
    		log+="\r\n Error in starting scamSim \n"+e.getMessage();
    	}
    	log+="\r\n ========================================";
		return false;
	}
	
	public static void ExecuteCommand(String cmnd, String outputfile) throws IOException{
		if (!outputfile.isEmpty()){
		 cmnd=cmnd+">"+outputfile;
		}
		System.out.println(cmnd);
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmnd);
        Process p = builder.start();
	}
	
	public static void VerifyITconsoleExecution(String SprintMachine) {
		try {
			
			URL url = new URL("file://" + SprintMachine + "/c$/output.txt");
			Thread.sleep(10000);
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			while (line != null) {
				line = br.readLine();
				System.out.println(line);
				if(line != null){
					if (line.contains("Total Scenarios")) {
						String[] a = line.split(";");
						System.out.println(a[0].trim());
						log+="\r\n "+a[0];
						System.out.println(a[1].trim());
						log+="\r\n "+a[1];
						System.out.println(a[2].trim());
						log+="\r\n "+a[2];
						String[] FailCount = a[1].split(":");

						if (FailCount[1].trim().equalsIgnoreCase("0")) {
							System.out.println(FailCount[1].trim() + "-Scenarios failed");
							System.out.println("All Scenarios passed");
							log+="\r\n===================================\r\n All Scenarios passed";
							break;
						} else {
							System.out.println("Failed: " + FailCount[1]+"-Scenarios failed");
							log+="\r\n Failed: " + FailCount[1]+ "-Scenarios failed";
							break;
						}
					}
				}else if(line==null){
					log+="\r\n #Failed!# - ITConsole did not execute properly";
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static boolean createTestScenarioXML(String fileName, String filePath) throws InterruptedException{
		boolean fileCreated=false;
		String PWD=System.getProperty("user.dir");
		System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("TestScenarios");
			doc.appendChild(rootElement);
			
			Element TestScenario = doc.createElement("TestScenario");
			rootElement.appendChild(TestScenario);
			
			TestScenario.setAttribute("sequence", "1");
			TestScenario.setAttribute("name", fileName);
			TestScenario.setAttribute("path", filePath);
			TestScenario.setAttribute("runflag", "true");
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(PWD+"\\TestScenarios.xml"));
			
			transformer.transform(source, result);
			fileCreated=true;
			Thread.sleep(1000);
			//Checking the whether the file is created or not
			File TestScenarios = new File(PWD+"\\TestScenarios.xml");
			if(TestScenarios.exists()){
				System.out.println("TestScenarios.xml is create at "+PWD);
			}else{
				fileCreated=false;
				System.out.println("TestScenarios.xml is not created at "+PWD);
			}
			System.out.println("Filesaved!");
			
		}catch(ParserConfigurationException e){
			fileCreated=false;
			System.out.println(e.getMessage());
		}catch(TransformerException e){
			fileCreated=false;
			System.out.println(e.getMessage());
		}
		return fileCreated;
	}
	
	public static boolean copyFile(String SprintMachine, String UserName, String Password, String File, String Source, String Destination){
		boolean fileCopied=false;
		String command1="cd \\ & net use \\\\"+SprintMachine+"\\c$ "+Password+" /USER:"+UserName+" persistent:no || xcopy /F /Y "+Source+"\\"+File+" "+Destination+"\\";
		System.out.println(command1);
		String command2="cd \\ &cd "+UAS.psexecPath+" &  psexec \\\\"+SprintMachine+" -u "+UserName+" -p "+Password+" cmd /c dir /b/s "+Destination+"\\"+File;
		System.out.println(command2);
		try{
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command1);
	    	builder.redirectErrorStream(true);
	        System.out.println("Copying "+File+" to "+Destination);
	        Process p = builder.start();
	        System.out.println("Finished copying "+File+" to "+Destination);
	        
	        builder = new ProcessBuilder("cmd.exe", "/c", command2);
	    	builder.redirectErrorStream(true);
	        p = builder.start();
	        
	        InputStream in=p.getInputStream();
	        InputStreamReader isr=new InputStreamReader(in);
	        BufferedReader br = new BufferedReader(isr);
	        
	        String line=null;
	        while((line=br.readLine())!=null){
	        	if(line.contains(Destination+"\\"+File)){
	        		fileCopied=true;
	        		System.out.println(File+" found at "+Destination+"\n");
	        		break;
	        	}else if(line.contains("File Not Found")){
	        		fileCopied=false;
	        		System.out.println(File+" not found at "+Destination+"\n");
	        		break;
	        	}
	        }
	        
	       if (!fileCopied){
	    	   System.out.println(File+" not found at "+Destination+"\n");
	       }
			
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return fileCopied;
	}
	
	public static void ModifyXML(String SprintMachine, String UserName, String Password, String Path, String File, String XMLName){
		try{
			String command = "net use Q: /delete";
			Process p = Runtime.getRuntime().exec(command);
			Thread.sleep(2000);
			
			command = "net use Q: \\\\"+SprintMachine+Path+" /user:"+UserName+" "+Password;
			p = Runtime.getRuntime().exec(command);
			Thread.sleep(10000);
		}catch(Exception e){
			e.printStackTrace();
		}
		String FilePath = "Q:\\"+File;
		try{
			File inputFile = new File(FilePath); 	    	
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		    Document doc=docBuilder.parse(inputFile);
		      
		    Node TestScenario = doc.getElementsByTagName("TestScenario").item(0);
		    NamedNodeMap attr = TestScenario.getAttributes();
		    Node nodeAttr = attr.getNamedItem("name");
		    nodeAttr.setTextContent(XMLName);
		    
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(inputFile);
			transformer.transform(source, result);
			System.out.println("Modified the name attribute to "+XMLName+ "in "+FilePath);
		}catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}   
		
		try{
			String command = "net use Q: /delete";
			Process p = Runtime.getRuntime().exec(command);
			Thread.sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void ModifyConfigXML(String SprintMachine, String UserName, String Password, String Path, String File,String XMLPath) {
		try{
			String command = "net use Q: /delete";
			Process p = Runtime.getRuntime().exec(command);
			
			Thread.sleep(2000);
			command = "net use Q: \\\\"+SprintMachine+Path+" /user:"+UserName+" "+Password;
			p = Runtime.getRuntime().exec(command);
			Thread.sleep(10000);
		}catch(Exception e){
			e.printStackTrace();
		}
		String FilePath = "Q:\\"+File;
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(FilePath));
			StringBuffer inputBuffer = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("<add key=\"ITSchedulingFile\"")){
					line = "<add key=\"ITSchedulingFile\" value=\"" + XMLPath+"\"/>";
				}
				inputBuffer.append(line);
				inputBuffer.append(System.getProperty("line.separator"));
			}
			String inputStr = inputBuffer.toString();
			br.close();

			FileOutputStream fileOut = new FileOutputStream(FilePath);
			fileOut.write(inputStr.getBytes());
			fileOut.close();
			
			System.out.println("Modified the Config file to have the path of TestScenarios.xml");
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		try{
			String command = "net use Q: /delete";
			Process p = Runtime.getRuntime().exec(command);
			Thread.sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static void Modify_KE_Input(String FilePath, String KE_IP){
		try {
			BufferedReader br = new BufferedReader(new FileReader(FilePath));
			StringBuffer inputBuffer = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("<ip>")){
					line = "	<ip>"+KE_IP+"</ip>";
				}
				inputBuffer.append(line);
				inputBuffer.append(System.getProperty("line.separator"));
			}
			String inputStr = inputBuffer.toString();
			br.close();

			FileOutputStream fileOut = new FileOutputStream(FilePath);
			fileOut.write(inputStr.getBytes());
			fileOut.close();
			
			System.out.println("Modified the KE_Simulator_Input.xml file to have the IP as "+KE_IP);
			br = new BufferedReader(new FileReader(FilePath));
			while ((line = br.readLine()) != null) {
				if (line.contains("<ip>")){
					System.out.println("Modified: "+line);
				}
			}
			br.close();
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}

	public static void modifyFile(String sprintMachine, String userName, String password, String path, String file,String XMLFiletoProcess, String xmlPath) {
		String command ="net use Q: /delete";
		String command2="net use \\\\"+sprintMachine+"\\c$ \\del";
		try{
			Process p = Runtime.getRuntime().exec(command);
			Thread.sleep(2000);
			p = Runtime.getRuntime().exec(command2);
			Thread.sleep(2000);
			
			command = "net use Q: \\\\"+sprintMachine+path+" /user:"+userName+" "+password;
			System.out.println(command);
			p = Runtime.getRuntime().exec(command);
			Thread.sleep(2000);
			
			command = "net use Q: \\\\"+sprintMachine+path;
			System.out.println(command);
			p = Runtime.getRuntime().exec(command);
			
			Thread.sleep(10000);
		}catch(Exception e){
			e.printStackTrace();
		}
		String FilePath = "Q:\\"+file;
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(FilePath));
			StringBuffer inputBuffer = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				
				if (line.contains(Unifia_Admin_Selenium.scanSimSearchTxt)){
					//line = "Olympus.Unifia.ScanSimulation.exe -u https://"+sprintMachine+":9755/  -x "+xmlPath+XMLFiletoProcess+">c:\\Output.txt";
					line = Unifia_Admin_Selenium.scanSimSearchTxt+".exe -u https://"+sprintMachine+":"+Unifia_Admin_Selenium.scanSimPort+"/  -x "+xmlPath+XMLFiletoProcess+">"+Unifia_Admin_Selenium.scanSimExecOutput;
				}
				inputBuffer.append(line);
				inputBuffer.append(System.getProperty("line.separator"));
			}
			String inputStr = inputBuffer.toString();
			br.close();

			FileOutputStream fileOut = new FileOutputStream(FilePath);
			fileOut.write(inputStr.getBytes());
			fileOut.close();
			
			System.out.println("Modified the batch file to have the path of sprint machine "+sprintMachine+" and xml file "+XMLFiletoProcess);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		try{
			command = "net use Q: /delete";
			Process p = Runtime.getRuntime().exec(command);
			Thread.sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
