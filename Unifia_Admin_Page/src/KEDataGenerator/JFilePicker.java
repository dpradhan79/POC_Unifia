package KEDataGenerator;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.filechooser.FileFilter;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import KEDataGenerator.FileTypeFilter;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.lang.reflect.Array;
import java.sql.SQLException;

import KEDataGenerator.DatabaseRelated;
import KEDataGenerator.DataFlow;
import KEDataGenerator.Utilities;

public class JFilePicker extends JPanel {

	private JFrame frame;
	private String textFieldLabel;
    private String buttonLabel;
     
    private JLabel label;
    public static JTextField textField;
    private JButton button,buttonYes,buttonNo;
     
    private JFileChooser fileChooser;
     
    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;
    
    public static String ip,user,pwd,NumofCycles;
    public static String DataType,NumberofScopes,CycleEnd;
    public static String OERModel,OERSerialNumber,Duration,Lag,staff;
    public static String[] ScopeModel,ScopeSerialNumber;
    public static DatabaseRelated DB;
    public static DataFlow DF;
    public static String KE_Url;
    public static JFilePicker XMLData;
    public static Utilities Util;
    public static String UnifiaAppName;
    
		public JFilePicker(String textFieldLabel, String buttonLabel, String buttonUpload, String buttonCancel) {
			
			setAlignmentY(Component.BOTTOM_ALIGNMENT);
	        this.textFieldLabel = textFieldLabel;
	        this.buttonLabel = buttonLabel;
	         
	        fileChooser = new JFileChooser();
	        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
	        // creates the GUI
	        label = new JLabel(textFieldLabel);
	        textField = new JTextField(20);
	        button = new JButton(buttonLabel);
	        buttonYes=new JButton(buttonUpload);
	        buttonYes.setAlignmentX(2.0f);
	        buttonNo=new JButton(buttonCancel);
	        button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent evt) {
	                buttonActionPerformed(evt);            
	            }
	        });
	        //Sync time between KEserver and client 
	        
	        buttonYes.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent evt) {
	            	try {
						buttonYesActionPerformed(evt);
					} catch (XPathExpressionException | SAXException
							| IOException | ParserConfigurationException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}            
	            }
	        });
	         
	        add(label);
	        add(textField);
	        add(button);
	        
	        add(buttonYes);
	        //add(buttonNo);
	    }
	    private void buttonActionPerformed(ActionEvent evt) {
	        if (mode == MODE_OPEN) {
	            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
	            }
	        } else if (mode == MODE_SAVE) {
	            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
	                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
	            }
	        }
	    }
	    private void buttonYesActionPerformed(ActionEvent evt) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, ClassNotFoundException, NumberFormatException, InterruptedException {
	    	 //GET XML Data
	    	XMLParser();
	    }	 
	    public void addFileTypeFilter(String extension, String description) {
	        FileTypeFilter filter = new FileTypeFilter(extension, description);
	        fileChooser.addChoosableFileFilter(filter);
	    }
	     
	    public void setMode(int mode) {
	        this.mode = mode;
	    }
	     
	    public String getSelectedFilePath() {
	        return textField.getText();
	    }
	     
	    public JFileChooser getFileChooser() {
	        return this.fileChooser;
	    }
	    
	   	public static void XMLParser() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException, ClassNotFoundException,NumberFormatException,InterruptedException{
	   		
	   		//Get the DB connection details from XML
	   		ip=getXMLData("/Simulator/KEServer/ip");
	   		user=getXMLData("/Simulator/KEServer/User");
	   		pwd=getXMLData("/Simulator/KEServer/PW");
	   		System.out.println("IP : "+ ip);
            System.out.println("User : "+ user);
            System.out.println("Pwd : "+ pwd);
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            KE_Url="jdbc:oracle:thin:@"+ip+":1521:FXDB";
            
            //Get Number of Cycles
            NumofCycles=getXMLData("/Simulator/NumberofCycles");
            DataType=getXMLData("/Simulator/DataType");
            System.out.println("DataType : "+ DataType);
            for (int iCntr=1;iCntr<=Integer.parseInt(NumofCycles);iCntr++){
            	//Get Each Cycle Data
            	NumberofScopes=getXMLData("/Simulator/Cycle"+iCntr+"/NumberofScopes");
            	CycleEnd=getXMLData("/Simulator/Cycle"+iCntr+"/CycleEnd");
            	
            	OERModel=getXMLData("/Simulator/Cycle"+iCntr+"/OER/OERModel");
            	OERSerialNumber=getXMLData("/Simulator/Cycle"+iCntr+"/OER/OERSerialNumber");
            	Duration=getXMLData("/Simulator/Cycle"+iCntr+"/OER/Duration");
            	Lag=getXMLData("/Simulator/Cycle"+iCntr+"/OER/Lag");

                System.out.println("NumberofScopes : "+ NumberofScopes);
                System.out.println("CycleEnd : "+ CycleEnd);
                System.out.println("OERModel : "+ OERModel);
                System.out.println("OERSerialNumber : "+ OERSerialNumber);
                System.out.println("Duration : "+ Duration);
                System.out.println("Lag : "+ Lag);
                
                ScopeModel=new String[Integer.parseInt(NumberofScopes)];
                ScopeSerialNumber=new String[Integer.parseInt(NumberofScopes)];
            	for (int iScope=1;iScope<=Integer.parseInt(NumberofScopes);iScope++){
            		//Scope Data
            		ScopeModel[iScope-1]=getXMLData("/Simulator/Cycle"+iCntr+"/Scope"+iScope+"/ScopeModel");
            		ScopeSerialNumber[iScope-1]=getXMLData("/Simulator/Cycle"+iCntr+"/Scope"+iScope+"/ScopeSerialNumber");
            	}
            	System.out.println("ScopeModel : "+ ScopeModel[0]);
            	System.out.println("ScopeSerialNumber : "+ ScopeSerialNumber[0]);
            	System.out.println("ScopeModel : "+ ScopeModel[1]);
            	System.out.println("ScopeSerialNumber : "+ ScopeSerialNumber[1]);
            	staff=getXMLData("/Simulator/Cycle"+iCntr+"/Staff/StartStaffID");
            	System.out.println("staff:"+ staff);
            	
            	if (iCntr==1){
            		try{
            			String command = "net use N: /delete";
            			Process p = Runtime.getRuntime().exec(command);
            			Thread.sleep(2000);
            			
            			//command = "net use N: \\\\"+SprintMachine+Path+" /user:"+UserName+" "+Password;
            			command ="net use N: \\\\"+XMLData.ip+"\\c$ /user:"+Util.KEMachine_Username+" "+Util.KEMachine_pswd;
            			p = Runtime.getRuntime().exec(command);
            			Thread.sleep(8000);
            		}catch(Exception e){
            			e.printStackTrace();
            		}
            		
            		String filePath="Olympus\\UnifiaCore\\OlympusKEConnectorService.exe.Config";
            		String KEUnifiaConfigFile = "N:\\"+filePath;

	            	//String KEUnifiaConfigFile="\\\\"+XMLData.ip+"\\c$\\Olympus\\UnifiaCore\\OlympusKEConnectorService.exe.Config";
	    	    	UnifiaAppName=Utilities.GetUnifiaClientName(KEUnifiaConfigFile);
	    			UnifiaAppName=UnifiaAppName.replace("\"","");
	    			//Sync Time of KE Server and client
	    	    	Util.SyncRemoteMachineTime(ip, Util.KEMachine_Username,Util.KEMachine_pswd , UnifiaAppName);
            	}
    	    	
            	//Call DataFlow to insert related data into KE Server
            	try {
					DF.Data();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	   	}
    	    
    	    
	    public static String getXMLData(String expression )throws XPathExpressionException, SAXException, IOException, ParserConfigurationException,NumberFormatException,InterruptedException{
	    	
			FileInputStream file = new FileInputStream(new File(textField.getText()));
	        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder =  builderFactory.newDocumentBuilder();
	        Document xmlDocument = builder.parse(file);
	        XPath xPath =  XPathFactory.newInstance().newXPath();
	        //System.out.println("*************************");
	        //String expression="/Simulator/Cycle2/Scope1/ScopeSerialNumber";
	     	System.out.println(expression);
	     	String eleValue= xPath.compile(expression).evaluate(xmlDocument);
	     	//System.out.println("eleValue:  "+eleValue);
	     	
	     	 return eleValue;
		 }
    	  
	}
	    
	   
	    

