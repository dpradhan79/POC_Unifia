package OER_Simulator;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.openqa.selenium.chrome.ChromeDriver;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import TestFrameWork.Unifia_Admin_Selenium;




import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class OERSimulator {

	public static String CycleCnt;
	public static String ScopeCnt;
	public static String CycleEnd;
	public static String KEip;
	public static String User;
	public static String PW;
	public static String OERModel;
	public static String OERSerialNum;
	public static String CycleDuration;
	public static String Lag;
	public static String Scope1SerNum;
	public static String Scope1Model;
	public static String Scope2SerNum;
	public static String Scope2Model;
	public static String StaffID;
	public static String expression;
	public static String Scope1SerialNum;
	public static String Scope2SerialNum;
	public static String DataType;

	 
	 
	 public static String getDataType() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/DataType";
         System.out.println(expression);
         DataType = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("DataType:  "+DataType);
		 return DataType;
	 }
	 
	 public static String getNumCycles() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/NumberofCycles";
         System.out.println(expression);
         CycleCnt = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("#of Cycles:  "+CycleCnt);
		 return CycleCnt;
	 }
	 
	 public static String getIPAddress()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/KEServer/ip";
         System.out.println(expression);
         KEip = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("KE Server ip:  "+KEip);
		 return KEip;
	 }
	 
	 public static String getUser()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/KEServer/User";
         System.out.println(expression);
         User = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("User:  "+User);
		 return User;
	 }
	 
	 public static String getPW()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/KEServer/PW";
         System.out.println(expression);
         PW = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("PW:  "+PW);
		 return PW;
	 }
	 
	 public static String getCycle1NumScopes() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle1/NumberofScopes";
         System.out.println(expression);
         ScopeCnt = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("#of Scopes:  "+ScopeCnt);
		 return ScopeCnt;
	 }
	 
	 public static String getCycle2NumScopes() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle2/NumberofScopes";
         System.out.println(expression);
         ScopeCnt = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("#of Scopes:  "+ScopeCnt);
		 return ScopeCnt;
	 }
	 
	 public static String getCycle1End() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         
         String expression = "/Simulator/Cycle1/CycleEnd";
         System.out.println(expression);
         CycleEnd = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("Processing:  "+CycleEnd);
         
		 return CycleEnd;
	 }
	 
	 public static String getCycle2End() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         
         String expression = "/Simulator/Cycle2/CycleEnd";
         System.out.println(expression);
         CycleEnd = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("Processing:  "+CycleEnd);
         
		 return CycleEnd;
	 }
	 
	 public static String getCycle1OERModel()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();          
         Document xmlDocument = builder.parse(file);
         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle1/OER/OERModel";
         System.out.println(expression);
         OERModel = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("OER model:  "+OERModel);
		 return OERModel;
	 }
	 
	 public static String getCycle2OERModel()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();          
         Document xmlDocument = builder.parse(file);
         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle2/OER/OERModel";
         System.out.println(expression);
         OERModel = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("OER model:  "+OERModel);
		 return OERModel;
	 }
	 
	 public static String getCylce1OERSerial()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle1/OER/OERSerialNumber";
         System.out.println(expression);
         OERSerialNum = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("OER Serial Number: "+OERSerialNum);
         return OERSerialNum;
	 }
	 
	 public static String getCylce2OERSerial()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle2/OER/OERSerialNumber";
         System.out.println(expression);
         OERSerialNum = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("OER Serial Number: "+OERSerialNum);
         return OERSerialNum;
	 }
	 
	 public static String getCycle1Duration()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle1/OER/Duration";
         System.out.println(expression);
         CycleDuration = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("Cycle Duration:  "+CycleDuration+" minutes");
		 return CycleDuration;
	 }
	 
	 public static String getCycle2Duration()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle2/OER/Duration";
         System.out.println(expression);
         CycleDuration = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("Cycle Duration:  "+CycleDuration+" minutes");
		 return CycleDuration;
	 }
	 
	 public static String getCycle1Lag()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle1/OER/Lag";
         System.out.println(expression);
         Lag = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("Lag:  "+Lag+" minutes");
		 return Lag;
	 }
	 
	 public static String getCycle2Lag()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle2/OER/Lag";
         System.out.println(expression);
         Lag = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("Lag:  "+Lag+" minutes");
		 return Lag;
	 }
	 
	 public static String getCycle1ScopeModel1()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression="/Simulator/Cycle1/Scope1/ScopeModel";
     	System.out.println(expression);
     	Scope1Model= xPath.compile(expression).evaluate(xmlDocument);
     	System.out.println("Scope 1 Model:  "+Scope1Model);
		 return Scope1Model;
	 }
	 
	 public static String getCycle2ScopeModel1()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression="/Simulator/Cycle2/Scope1/ScopeModel";
     	System.out.println(expression);
     	Scope1Model= xPath.compile(expression).evaluate(xmlDocument);
     	System.out.println("Scope 1 Model:  "+Scope1Model);
		 return Scope1Model;
	 }
	 
	 public static String getCycle1ScopeSerialNum1()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
			 
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();
         
         System.out.println("*************************");
         expression="/Simulator/Cycle1/Scope1/ScopeSerialNumber";
     	System.out.println(expression);
     	Scope1SerialNum= xPath.compile(expression).evaluate(xmlDocument);
     	System.out.println("Scope 1 Serial Number:  "+Scope1SerialNum);
     	
     	 return Scope1SerialNum;
	 }
	 
	 public static String getCycle2ScopeSerialNum1()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();
         
         System.out.println("*************************");
         expression="/Simulator/Cycle2/Scope1/ScopeSerialNumber";
     	System.out.println(expression);
     	Scope1SerialNum= xPath.compile(expression).evaluate(xmlDocument);
     	System.out.println("Scope 1 Serial Number:  "+Scope1SerialNum);
     	
     	 return Scope1SerialNum;
	 }
	 
	 public static String getCycle1ScopeModel2()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression="/Simulator/Cycle1/Scope2/ScopeModel";
     	System.out.println(expression);
     	Scope2Model= xPath.compile(expression).evaluate(xmlDocument);
     	System.out.println("Scope 2 Model:  "+Scope1Model);
		 return Scope2Model;
	 }
	 
	 public static String getCycle2ScopeModel2()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression="/Simulator/Cycle2/Scope2/ScopeModel";
     	System.out.println(expression);
     	Scope2Model= xPath.compile(expression).evaluate(xmlDocument);
     	System.out.println("Scope 2 Model:  "+Scope1Model);
		 return Scope2Model;
	 }
	 
	 public static String getCycle1ScopeSerialNum2()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression="/Simulator/Cycle1/Scope2/ScopeSerialNumber";
     	System.out.println(expression);
     	Scope2SerialNum= xPath.compile(expression).evaluate(xmlDocument);
     	System.out.println("Scope 2 Serial Numbers:  "+Scope2SerialNum);
		 return Scope2SerialNum;
	 }
	 
	 public static String getCycle2ScopeSerialNum2()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression="/Simulator/Cycle2/Scope2/ScopeSerialNumber";
     	System.out.println(expression);
     	Scope2SerialNum= xPath.compile(expression).evaluate(xmlDocument);
     	System.out.println("Scope 2 Serial Numbers:  "+Scope2SerialNum);
		 return Scope2SerialNum;
	 }
	 
	 public static String getCycle1StaffID()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle1/Staff/StartStaffID";
         System.out.println(expression);
         StaffID = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("StaffID:  "+StaffID);

		 return StaffID;
	 }

	 public static String getCycle2StaffID()throws XPathExpressionException, SAXException, IOException, ParserConfigurationException{
		 FileInputStream file = new FileInputStream(new File(Unifia_Admin_Selenium.KE_SimulatorInput));
         
         DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
          
         DocumentBuilder builder =  builderFactory.newDocumentBuilder();
          
         Document xmlDocument = builder.parse(file);

         XPath xPath =  XPathFactory.newInstance().newXPath();

         System.out.println("*************************");
         expression = "/Simulator/Cycle2/Staff/StartStaffID";
         System.out.println(expression);
         StaffID = xPath.compile(expression).evaluate(xmlDocument);
         System.out.println("StaffID:  "+StaffID);

		 return StaffID;
	 }
}