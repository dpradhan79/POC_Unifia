package EmulatedScan;

import TestFrameWork.Unifia_Admin_Selenium;

public class AllEmulatedScans {
	public static TestFrameWork.Unifia_Admin_Selenium UAS;
	public static TestFrameWork.UnifiaAdminLoginPage.Login_Actions LP;
	public static TestFrameWork.Emulator.Emulator_Actions EM_A;
	public static String sLoc="";
	public static String sType="";
	public static String sSubType="";
	public static String sItem="";
	public static String keyVal="";
	public static Boolean RetV;	//Scan return value
	public static String url;
	
	public static void main(String[] args) throws InterruptedException {
		//Using following two lines for Unifia 2.2
		UAS.setup();
		url = "https://sprinttest-07.mitlab.com:9751";
		//Using following two lines for Unifia 2.1
		//System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
		//LP.Launch_Unifia(Unifia_Admin_Selenium.Emulator_URL);
		
		LP.Launch_Unifia(url);
		int scanItem=0;
		
		/*
		 * Examples of Cabinet scans (happy path)
		 		{"Storage Area A", "Scope", "", "Scope3", ""},	//Scan scope into cabinet
				{"Storage Area A", "Key Entry", "", "", "1"},	//Enter cabinet #
				{"Storage Area A", "Staff", "Tech", "Tech3 Tech3(T03)",""}, //Scan staff ID
				{"Storage Area A", "Scope", "", "Scope3", ""}, //Scan out of cabinet
				{"Storage Area A", "Key Entry", "", "", "1"}, //Enter culture test result
				{"Storage Area A", "Staff", "Tech", "Tech3 Tech3(T03)",""}, //Scan staff ID
		 * Examples of Procedure Room scans (happy path)
		 		{"Procedure Room 1", "Workflow Event", "", "Available",""}, //Set room available
				{"Procedure Room 1", "Scope", "", "Scope3",""},	//Scan scope into PR
				//{"Procedure Room 1", "Key Entry", "", "", "1"}, //Enter culture test result Pass
				{"Procedure Room 1", "Staff", "Tech", "Tech3 Tech3(T03)",""},	//Scan staff ID
				{"Procedure Room 1", "Workflow Event", "", "Patient/Procedure Status",""},	//Scan admit patient
				{"Procedure Room 1", "Patient", "", "PID121212",""}, //Scan patient ID
				{"Procedure Room 1", "Staff", "Physician", "Physician1 Physician1(MD01)",""}, //Scan physician ID
				{"Procedure Room 1", "Workflow Event", "", "Procedure Start",""}, //procedure start
				{"Procedure Room 1", "Workflow Event", "", "Procedure End",""}, //procedure end
				{"Procedure Room 1", "Scope", "", "Scope3",""}, //Scan scope out of procedure room
				{"Procedure Room 1", "Staff", "Tech", "Tech3 Tech3(T03)",""},	//Scan staff ID
				{"Procedure Room 1", "Workflow Event", "", "Needs Cleaning",""}, //Set room "Need Cleaning"
		 * Examples of Soiled Room scans (happy path)
		 		{"Sink 1", "Scope", "", "Scope3",""},	//Scan in
				{"Sink 1", "Staff", "Tech", "Tech3 Tech3(T03)",""},	//Scan staff
				{"Sink 1", "Test Result", "", "Leak Test Pass",""},	//Leak Test results
				{"Sink 1", "Workflow Event", "", "Manual Clean Start",""}, //Manual clean start
				{"Sink 1", "Workflow Event", "", "Manual Clean End",""},  //Manual clean end
				{"BioTest", "Scope", "", "Scope3",""}, //Scan scope for Bioburden testing
				{"BioTest", "Bioburden Testing", "", "Pass",""}, //Bioburden test result
				{"BioTest", "Staff", "Tech", "Tech3 Tech3(T03)",""} //staff
		 * Examples of Waiting Room scans (happy path)
		 		{"Waiting1", "Patient","", "PID333333",""}, //Scan patient
				{"Waiting1", "Exam Type","", "Gastrostomy",""}, //Scan exam type
				{"Waiting1", "Workflow Event","", "Send To ExamQueue",""}, //Scan "Send To ExamQueue"
		 * Examples of Out of Facility scans (happy path)
		 		{"Administration", "Scope", "", "Scope15",""}, //Scan scope to be sent out of facility
				{"Administration", "Out of Facility Location", "", "Destination 1",""}, //Scan destination
				{"Administration", "Staff", "Tech", "Tech3 Tech3(T03)",""} //Scan staff
		 */
		//Examples of Reprocessor scans (happy path)
		String scanV[][] = new String [][] {
				{"Reprocessor 1", "Scope", "", "Scope5", ""},	//Scan scope in
				{"Reprocessor 1", "Reason for Reprocessing", "", "Exceeded Hang Time", ""},	//Scan reason for reprocessing
				{"Reprocessor 1", "Staff", "Tech", "Tech3 Tech3(T03)", ""},	//Scan staff
				{"Reprocessor 1", "Test Result", "", "MRC Pass", ""},	//Scan MRC test result
				{"Reprocessor 1", "Staff", "Tech", "Tech3 Tech3(T03)", ""},	//Scan staff
				{"Reprocessor 1", "Scope", "", "Scope5", ""},	//Scan out of RP
				{"Reprocessor 1", "Staff", "Tech", "Tech3 Tech3(T03)", ""},	//Scan staff
				{"Culturing", "Scope", "", "Scope5", ""},	//Put scope on Culture Hold
				{"Culturing", "Staff", "Tech", "Tech3 Tech3(T03)", ""},	//Scan staff
				
		};
		
		//Get array values and scan them
		try{
			for (int i=0; i<scanV.length; i++) {
				for (int j=0; j<scanV[0].length; j++) {
					if(j==0) {
						sLoc=scanV[i][j];
					} 
					else if(j==1) {
						sType=scanV[i][j];
					}
					else if(j==2) {
						sSubType=scanV[i][j];
					}
					else if(j==3) {
						sItem=scanV[i][j];
					}
					else if(j==4){
						keyVal=scanV[i][j];
					}
					System.out.println (scanV[i][j]);					
				}
				RetV = EM_A.ScanItem(sLoc, sType, sSubType, sItem, keyVal);
				Thread.sleep(1600);
				System.out.println ("Scan Item "+scanItem +" return value: "+RetV);
				
				scanItem++;
														
				sLoc="";
				sType="";
				sSubType="";
				sItem="";
				keyVal="";
			}
		} catch(Exception e) {
			System.err.println("Error: " + e.getMessage());
			System.out.println(e);			
		}
	}
}
