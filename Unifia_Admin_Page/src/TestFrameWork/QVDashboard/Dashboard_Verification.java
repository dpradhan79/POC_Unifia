package TestFrameWork.QVDashboard;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import DailyDashBoard.QvTabsAuthorization;
import TestFrameWork.Unifia_Admin_Selenium; 
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage;
import TestFrameWork.QVDashboard.*;
import TestFrameWork.UnifiaAdminGeneralFunctions.*;


public class Dashboard_Verification  extends Unifia_Admin_Selenium {
	public GeneralFunc gf;
	public static Dashboard_Actions DA;
	public static TestFrameWork.TestHelper TH;
	public static TestFrameWork.Unifia_Admin_Selenium UAS; 

	//public DailyDashBoard.QvTabsAuthorization QvTab;
	public static String TestSummary="";
	public static String ResFileName;
	public static String Flow; //Variable to store the tab selection order
	public static TestFrameWork.UnifiaAdminGeneralFunctions.dashboardpage DBP;
	public static TestFrameWork.Unifia_IP.IP_Actions IP_A;
	public static TestFrameWork.Unifia_SRM_WorkFlowDetails.WorkFlowDetails_Actions SRM_WFA;
	public static String Verify_PR_Status(String PR, String Status ) throws InterruptedException {
	
		Result="No Result";
		Thread.sleep(5000);
		Actual=driver.findElement(By.className("QvCaption")).findElement(By.xpath("//div[@title='"+PR+"']")).getCssValue("background-color");  
	    //System.out.println("Actual="+Actual);
	   	if(Actual.equalsIgnoreCase(Status)){
	   		Result="Pass";
	   	}else {
			UAS.resultFlag="#Failed!#";
	   		Result="#Failed!#: Room status is not updated correctly. It should be "+Status+" but it is "+Actual;
	   	}

	   //	System.out.println("Result="+Result);

		return Result;
	}
	
	 public static boolean FindImage(String TestImage){
	     try{
	        Screen screen=new Screen();
	        if (screen.exists(new Pattern(TestImage).similar((float) 0.97)) != null){
	        //if (screen.exists(new Pattern(TestImage).exact()) != null){
	        //if (screen.exists(TestImage) != null){
	        	screen.getLastMatch().highlight(3);
	        	return true;
	        }else{
	         if (screen.exists(new Pattern(TestImage).similar((float) 0.95)) != null){
	        	 screen.getLastMatch().highlight(3);
		        	return true;
	         }else{
	        	 return false;
	         }
	        	
	        } 
	      }catch(Exception ex){
	    	  System.out.println("Exception: " + ex.getMessage());
	        return false;
	      }
	    }
	 
	 public static void CheckDashboard(String Role, String FileName) throws InterruptedException {
		ResFileName = FileName;
		String text;
		switch (Role) {
		case "Dashboard":
			VerifyTabs("DAILY DASHBOARD", Role);
			break;
		case "Data Analyst":
			VerifyTabs("DAILY DASHBOARD;ANALYSIS", Role);
			break;
		case "Manager/Supervisor":
			VerifyTabs("DAILY DASHBOARD;INFECTION PREVENTION;MATERIALS AND ASSET MANAGEMENT;ANALYSIS;AUDIT LOG", Role);
			break;
		case "Materials Manager":
			VerifyTabs("DAILY DASHBOARD;MATERIALS AND ASSET MANAGEMENT", Role);
			break;
		case "Staff":
			VerifyTabs("DAILY DASHBOARD;INFECTION PREVENTION;MATERIALS AND ASSET MANAGEMENT", Role);
			break;
		case "Admin":
			//text = Unifia_Admin_Selenium.driver.findElementByXPath("/html/body/div[1]/div/div[1]/ul[2]/li/a").getText();
			text = Unifia_Admin_Selenium.driver.findElementByXPath("//*[@id='navbar']/ul[1]/li/a").getText();
			if (text.equalsIgnoreCase("ADMIN")) {
				QvTabsAuthorization.TestSummary += "\r\n" + text + " Role - #Passed!# \r\n";
			} else {
				UAS.resultFlag="#Failed!#";
				QvTabsAuthorization.TestSummary += "\r\n #Failed!#: Admin role is not correct. \r\n";
			}
			break;
		}
	}
	 
	 public static void VerifyTabs(String Tabs, String Role) throws InterruptedException{
		 	Thread.sleep(4000); 
		 	String[] tabs=Tabs.split(";");
			for(String tab:tabs){
	switch(tab){
	case "DAILY DASHBOARD":
			String text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.dashBoardTab).getText();
			if (text.equalsIgnoreCase("DAILY DASHBOARD")) {
				Unifia_Admin_Selenium.driver.findElementByXPath(DBP.dashBoardTab).click();
				text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.examQueueHeaderDDB).getText();
				if (text.equalsIgnoreCase("Exam Queue")) {
					QvTabsAuthorization.TestSummary += "\r\n Dashboard Role - #Passed!# \r\n";
				} else {
					UAS.resultFlag="#Failed!#";
					QvTabsAuthorization.TestSummary += "\r\n #Failed!#: Dashboard  role is not correct. " + text + " is not displayed \r\n";
				}
			} else {
				UAS.resultFlag="#Failed!#";
				QvTabsAuthorization.TestSummary += "\r\n #Failed!#: role is not correct."+ text + " is not displayed \r\n";
			text ="";
			}
			break;
	case "INFECTION PREVENTION":
		text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.ipTab).getText();
		if (text.equalsIgnoreCase("INFECTION PREVENTION")) {
			Unifia_Admin_Selenium.driver.findElementByXPath(DBP.ipTab).click();
			
			text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.ipDashboard).getText();
			if (text.equalsIgnoreCase("Dashboard")) {
				QvTabsAuthorization.TestSummary += "\r\n IP tab displayed and clicked, IP Dashboard displayed- #Passed!# \r\n";
			} else {
				UAS.resultFlag="#Failed!#";
				QvTabsAuthorization.TestSummary += "\r\n #Failed!#: IP tab displayed not displayed and IP " + text + " is not displayed \r\n";
			}
			text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.ipSRM).getText();
			if (text.equalsIgnoreCase("Scope Record Management")) {
				QvTabsAuthorization.TestSummary += "\r\n IP tab displayed and clicked, IP SRM tab displayed- #Passed!# \r\n";
				Unifia_Admin_Selenium.driver.findElementByXPath(DBP.ipSRM).click();
				IP_A.Click_Details("9-0");
				//*[@id="edit_"+//*]
				boolean button, expButton;	
				button=Unifia_Admin_Selenium.driver.findElementById("submitButton").isEnabled();
				//System.out.println(button);
				if(Role.equalsIgnoreCase("Manager/Supervisor")){
					expButton=true;
				}else{
					expButton=false;
				}	
				if (expButton){
					if (button){
						QvTabsAuthorization.TestSummary += "\r\n #Passed!#: Save button is enabled for "+ Role  + " \r\n " ;
					}else {
						UAS.resultFlag="#Failed!#";
						QvTabsAuthorization.TestSummary += "\r\n #Failed!#: Save button is disbaled for "+ Role  + " \r\n" ;
					}
				}else{
					if (!button){
						QvTabsAuthorization.TestSummary += "\r\n #Passed!#: Save button is disabled for "+ Role + "\r\n" ;
					}else {
						UAS.resultFlag="#Failed!#";
						QvTabsAuthorization.TestSummary += "\r\n #Failed!#: Save button is enabled for "+ Role  + "\r\n" ;
					}
				}
			} else {
				UAS.resultFlag="#Failed!#";
				QvTabsAuthorization.TestSummary += "\r\n #Failed!#: IP tab displayed and IP " + text + " is not displayed \r\n";				
			}
			text="";
			text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.ipMRC).getText();
			if (text.equalsIgnoreCase("MRC Record Management")) {
				QvTabsAuthorization.TestSummary += "\r\n IP tab displayed and clicked, IP MRC Dashboard displayed- Passed \r\n";
				 Unifia_Admin_Selenium.driver.findElementByXPath(DBP.ipMRC).click();
				int size= Unifia_Admin_Selenium.driver.findElementsByXPath(DBP.mrceditLink).size();			
				if(Role.equalsIgnoreCase("Manager/Supervisor")){			
				if (size>=1){
						QvTabsAuthorization.TestSummary += "\r\n #Passed!#: MRC Edit iss Visible for "+ Role + "\r\n ";
					}else {
						UAS.resultFlag="#Failed!#";
						QvTabsAuthorization.TestSummary += "\r\n #Failed!#: MRC Edit is not Visible for "+ Role + "\r\n ";
					}
				}else{
					if (size==0){
						QvTabsAuthorization.TestSummary += "\r\n #Passed!#: MRC Edit is  not Visible for "+ Role +" \r\n";
						}else {
						UAS.resultFlag="#Failed!#";
						QvTabsAuthorization.TestSummary += "\r\n #Failed!#: MRC Edit is  Visible for "+ Role +" \r\n";
					}
				}					
			} else {
				UAS.resultFlag="#Failed!#";
				QvTabsAuthorization.TestSummary += "\r\n #Failed!#: IP tab displayed and IP " + text + " is not displayed \r\n";
			}
			text="";
		}		
		break;		
		case "MATERIALS AND ASSET MANAGEMENT":
			text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.mamTab).getText();
				if (text.equalsIgnoreCase("MATERIALS AND ASSET MANAGEMENT")) {
					Unifia_Admin_Selenium.driver.findElementByXPath(DBP.mamTab).click();
					text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.sshLabel).getText();
					if (text.equalsIgnoreCase("Scope Status and History")) {
						QvTabsAuthorization.TestSummary += "\r\n MAM tab displayed and clicked, Scope Status and History table displayed- #Passed!# \r\n";
					} else {
						UAS.resultFlag="#Failed!#";
						QvTabsAuthorization.TestSummary += "\r\n #Failed!#: MAM displayed but  " + text + " is not displayed  \r\n";
					}
				} else {
					UAS.resultFlag="#Failed!#";
					QvTabsAuthorization.TestSummary += "\r\n #Failed!#: MAM tab is not correct. IP"+ text + " is not displayed  \r\n";
					text ="";
				}
				break;				
		case "ANALYSIS":
			text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.analysisTab).getText();
			if (text.equalsIgnoreCase("ANALYSIS")) {
				QvTabsAuthorization.TestSummary += "\r\n #Passed#: " +text+ "  tab is displayed \r\n";
				System.out.println("Analysis Pass");
			} else {
				UAS.resultFlag="#Failed!#";
				QvTabsAuthorization.TestSummary += "\r\n #Failed!#: Analysis tab is not correct. "+ text + " is not displayed \r\n";
				text ="";
			}
			//Verify if u r able to log into Qlikview
			/*String tabsToClick="";
			if(Role.equalsIgnoreCase("Manager/Supervisor")){
				tabsToClick="5";
			}else if (Role.equalsIgnoreCase("Data Analyst")){
				tabsToClick="4";
			}
			String[] credentials = new String[] {"C:\\Unifia\\QlikView\\AnalysisLogin.exe",tabsToClick,Unifia_Admin_Selenium.userQV1,Unifia_Admin_Selenium.userQV1Pwd};
			try {
			    	Runtime.getRuntime().exec(credentials);
			    	Thread.sleep(20000);
				} catch (Exception e) {
					System.out.println(e.getMessage());
			}
			
			driver.switchTo().defaultContent();
			driver.switchTo().frame(0);
			
			if (Unifia_Admin_Selenium.driver.findElementsByXPath(DBP.averageTimes).size()>0 && Unifia_Admin_Selenium.driver.findElementsByXPath(DBP.clear).size()>0){
				System.out.println("Logged into Analysis page successfully.");
				QvTabsAuthorization.TestSummary += "\r\n #Passed!#: Logged into Analysis page successfully. \r\n";
			}else{	
				System.out.println("Log into Analysis page failed.");
				QvTabsAuthorization.TestSummary += "\r\n #Failed!#: Log into Analysis page failed. \r\n";
			}*/
			
			break;
		case "AUDIT LOG":
			driver.switchTo().defaultContent();
				text = Unifia_Admin_Selenium.driver.findElementByXPath(DBP.auditTab).getText();
				if (text.equalsIgnoreCase("AUDIT LOG")) {			
					QvTabsAuthorization.TestSummary += "\r\n Audit log tab displayed - #Passed!# \r\n";
				}else{
					UAS.resultFlag="#Failed!#";
					QvTabsAuthorization.TestSummary += "\r\n #Failed!#: Audit tab is not displayed  \r\n";
				}					
					text ="";
					break;
				}
	
			}
	 }
							
	 //Method to verify whether given tab is selected or not
	 public static boolean verifyTabSelection(String Tab){
		 boolean clicked=false;
		 String tabBackgroundValue=Unifia_Admin_Selenium.driver.findElement(By.xpath("//td[contains(text(), '"+Tab+"')]/../../../../../div[@class='QvContent']")).getCssValue("background-color");
		 if(tabBackgroundValue.equals("rgba(8, 16, 123, 1)")){
			 clicked=true;
		 }
		 return clicked;
	 }
	 
//ExamQueue
	public static void clickDashBoard() throws InterruptedException{
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.switchTo().defaultContent(); //switches out of the iframe
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath(dashboardpage.dashBoardTab)).click();
	}
	 
	public static String getPatients(){
		 String patientCount=driver.findElementByXPath(dashboardpage.examQueuePatientsDDB).getText();
		 return patientCount;
	 }
	 
	public static String getExams(){	
		 String ExamCount=driver.findElementByXPath(dashboardpage.examQueueExamsDDB).getText();
		 return ExamCount;
	}
	public static String getExamType1(){	
		 String ExamType=driver.findElementByXPath(dashboardpage.examType1).getText();
		 return ExamType;
	}
	public static String getExamType1Count(){	
		 String ExamTypeCount=driver.findElementByXPath(dashboardpage.examType1Count).getText();
		 return ExamTypeCount;
	}
	
	public static String getExamType2(){	
		 String ExamType=driver.findElementByXPath(dashboardpage.examType2).getText();
		 return ExamType;
	}
	
	public static String getExamType2Count(){	
		 String ExamTypeCount=driver.findElementByXPath(dashboardpage.examType2Count).getText();
		 return ExamTypeCount;
	}
	
	//procedure room
	//facility 1
	public static String getPR1Name(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR1Name).getText();
		 return PRName;
	 }

	public static String getPR1Status(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR1Status).getText();
		 return PRStatus;
	 }
	
	public static String getPR1Scopes(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR1Scopes).getText();
		 return PRScopes;
	 }
	
	public static String getPR1Color(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR1color).getCssValue("background-color");
		 return PRColor;
	 }
	//facility 2
	public static String getPR1Name2(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR1Name2).getText();
		 return PRName;
	 }

	public static String getPR1Status2(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR1Status2).getText();
		 return PRStatus;
	 }
	
	public static String getPR1Scopes2(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR1Scopes2).getText();
		 return PRScopes;
	 }
	
	public static String getPR1Color2(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR1color2).getCssValue("background-color");
		 return PRColor;
	 }
	
	//facility 3
	public static String getPR1Name3(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR1Name3).getText();
		 return PRName;
	 }

	public static String getPR1Status3(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR1Status3).getText();
		 return PRStatus;
	 }
	
	public static String getPR1Scopes3(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR1Scopes3).getText();
		 return PRScopes;
	 }
	
	public static String getPR1Color3(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR1color3).getCssValue("background-color");
		 return PRColor;
	 }

	
	//facility1
	public static String getPR2Name(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR2Name).getText();
		 return PRName;
	 }

	public static String getPR2Status(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR2Status).getText();
		 return PRStatus;
	 }
	
	public static String getPR2Scopes(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR2Scopes).getText();
		 return PRScopes;
	 }
	public static String getPR2Color(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR2color).getCssValue("background-color");
		 return PRColor;
	 }
	
	//facility2
	public static String getPR2Name2(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR2Name2).getText();
		 return PRName;
	 }

	public static String getPR2Status2(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR2Status2).getText();
		 return PRStatus;
	 }
	
	public static String getPR2Scopes2(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR2Scopes2).getText();
		 return PRScopes;
	 }
	public static String getPR2Color2(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR2color2).getCssValue("background-color");
		 return PRColor;
	 }
	//facility3
	public static String getPR2Name3(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR2Name3).getText();
		 return PRName;
	 }

	public static String getPR2Status3(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR2Status3).getText();
		 return PRStatus;
	 }
	
	public static String getPR2Scopes3(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR2Scopes3).getText();
		 return PRScopes;
	 }
	public static String getPR2Color3(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR2color3).getCssValue("background-color");
		 return PRColor;
	 }
	//facility1
	public static String getPR3Name(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR3Name).getText();
		 return PRName;
	 }

	public static String getPR3Status(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR3Status).getText();
		 return PRStatus;
	 }
	
	public static String getPR3Scopes(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR3Scopes).getText();
		 return PRScopes;
	 }
	public static String getPR3Color(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR3color).getCssValue("background-color");
		 return PRColor;
	 }
	//facility2
	public static String getPR3Name2(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR3Name2).getText();
		 return PRName;
	 }

	public static String getPR3Status2(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR3Status2).getText();
		 return PRStatus;
	 }
	
	public static String getPR3Scopes2(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR3Scopes2).getText();
		 return PRScopes;
	 }
	public static String getPR3Color2(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR3color2).getCssValue("background-color");
		 return PRColor;
	 }
	//facility3
	public static String getPR3Name3(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR3Name3).getText();
		 return PRName;
	 }
	public static String getPR3Status3(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR3Status3).getText();
		 return PRStatus;
	 }
	
	public static String getPR3Scopes3(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR3Scopes3).getText();
		 return PRScopes;
	 }
	public static String getPR3Color3(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR3color3).getCssValue("background-color");
		 return PRColor;
	 }


	//facility1
	public static String getPR5Name(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR5Name).getText();
		 return PRName;
	 }
	public static String getPR5Status(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR5Status).getText();
		 return PRStatus;
	 }
	
	public static String getPR5Scopes(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR5Scopes).getText();
		 return PRScopes;
	 }
	public static String getPR5Color(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR5color).getCssValue("background-color");
		 return PRColor;
	 }
	
	//facility2
	public static String getPR5Name2(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR5Name2).getText();
		 return PRName;
	 }
	public static String getPR5Status2(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR5Status2).getText();
		 return PRStatus;
	 }
	
	public static String getPR5Scopes2(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR5Scopes2).getText();
		 return PRScopes;
	 }
	public static String getPR5Color2(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR5color2).getCssValue("background-color");
		 return PRColor;
	 }
	//facility3
	public static String getPR5Name3(){
		 String PRName=driver.findElementByXPath(dashboardpage.PR5Name3).getText();
		 return PRName;
	 }
	public static String getPR5Status3(){
		 String PRStatus=driver.findElementByXPath(dashboardpage.PR5Status3).getText();
		 return PRStatus;
	 }
	
	public static String getPR5Scopes3(){
		 String PRScopes=driver.findElementByXPath(dashboardpage.PR5Scopes3).getText();
		 return PRScopes;
	 }
	public static String getPR5Color3(){
		 String PRColor=driver.findElementByXPath(dashboardpage.PR5color3).getCssValue("background-color");
		 return PRColor;
	 }
	
	//Soiled Room
	public static String getSink1Name(){
		String SinkName=driver.findElementByXPath(dashboardpage.Sink1Name).getText();
		return SinkName;
	}
	
	public static String getSink1Name2(){
		String SinkName=driver.findElementByXPath(dashboardpage.Sink1Name2).getText();
		return SinkName;
	}
	
	public static String getSink1Name3(){
		String SinkName=driver.findElementByXPath(dashboardpage.Sink1Name3).getText();
		return SinkName;
	}
	
	public static String getSink1Scopes(){
		 String SinkScopes=driver.findElementByXPath(dashboardpage.Sink1Scopes).getText();
		 return SinkScopes;
	}
	
	public static String getSink1Scopes2(){
		 String SinkScopes=driver.findElementByXPath(dashboardpage.Sink1Scopes2).getText();
		 return SinkScopes;
	}
	
	public static String getSink1Scopes3(){
		 String SinkScopes=driver.findElementByXPath(dashboardpage.Sink1Scopes3).getText();
		 return SinkScopes;
	}
	
	public static String getSink2Name(){
		String SinkName=driver.findElementByXPath(dashboardpage.Sink2Name).getText();
		return SinkName;
	}
	
	public static String getSink2Name2(){
		String SinkName=driver.findElementByXPath(dashboardpage.Sink2Name2).getText();
		return SinkName;
	}
	
	public static String getSink2Name3(){
		String SinkName=driver.findElementByXPath(dashboardpage.Sink2Name3).getText();
		return SinkName;
	}
	
	public static String getSink2Scopes(){
		 String SinkScopes=driver.findElementByXPath(dashboardpage.Sink2Scopes).getText();
		 return SinkScopes;
	}
	
	public static String getSink2Scopes2(){
		 String SinkScopes=driver.findElementByXPath(dashboardpage.Sink2Scopes2).getText();
		 return SinkScopes;
	}
	
	public static String getSink2Scopes3(){
		 String SinkScopes=driver.findElementByXPath(dashboardpage.Sink2Scopes3).getText();
		 return SinkScopes;
	}
	//Storage, Reprocessor
	public static String getText(String xpath) throws InterruptedException{
		String act="";
		act=driver.findElementByXPath(xpath).getText();
		if (act.trim().equals("")){
			driver.findElementByXPath(DBP.cabNext).click();
			Thread.sleep(5000);
			act=driver.findElementByXPath(xpath).getText();
		}
		return act;
	}
	//Reprocessor
	public static String getTextReproc(String xpath,boolean verifyBlank) throws InterruptedException{
		String act="";
		act=driver.findElementByXPath(xpath).getText();
		if (!verifyBlank){
			if (act.trim().equals("")){
				driver.findElementByXPath(DBP.reproNext).click();
				Thread.sleep(2000);
				act=driver.findElementByXPath(xpath).getText();
			}
		}
		
		return act;
	}
	
	public static String getColor(String xpath){
		 String roomColor=driver.findElementByXPath(xpath).getCssValue("background-color");
		 return roomColor;
	 }
	
	//Out of Facility
	public static String getOOFName(){
		String oofName=driver.findElementByXPath(dashboardpage.oofName).getText();
		return oofName;
	}
	
	public static String getOOFScopeCount(){
		String scopeCount=driver.findElementByXPath(dashboardpage.oofScopesCount).getText();
		return scopeCount;
	}
	
	//Awaiting Manual Cleaning
	public static String getAMCName(){
		String amcName=driver.findElementByXPath(dashboardpage.amcName).getText();
		return amcName;
	}
	
	public static String getAMCScopeCount(){
		String amcScopeCount=driver.findElementByXPath(dashboardpage.amcScopesCount).getText();
		return amcScopeCount;
	}
	
	public static String getAMCColor(){
		String amcColor=driver.findElementByXPath(dashboardpage.amcColor).getCssValue("background-color");
		return amcColor;
	}
	
	//Awaiting Reprocessing
	public static String getARName(){
		String arName=driver.findElementByXPath(dashboardpage.arName).getText();
		return arName;
	}
	
	public static String getARScopeCount(){
		String arScopeCount=driver.findElementByXPath(dashboardpage.arScopesCount).getText();
		return arScopeCount;
	}
	
	public static String getARColor(){
		String arColor=driver.findElementByXPath(dashboardpage.arColor).getCssValue("background-color");
		return arColor;
	}
	
	public static boolean compareRes(String var1, String var2, boolean caseComparision){
		if (caseComparision){
			if (var1.replaceAll("\\s", "").equalsIgnoreCase(var2.replaceAll("\\s", ""))){
				return true;
			}else{
				return false;
			}
		}else{
			if (var1.replaceAll("\\s", "").equals(var2.replaceAll("\\s", ""))){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public static String verifyDashboard(String Location, String ScopeName, String ScopeCount, String Color, String status) throws InterruptedException{
		clickDashBoard();
		Thread.sleep(1000); //Wait 1 sec
		String Res="";
		switch(Location){
		case "Reprocessor 1": 
			String ReproName=getText(dashboardpage.rep1Name);
			if (compareRes(Location, ReproName,true)){
				Res="Passed: Expected= "+Location+";Actual= "+ReproName+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res="#Failed!#: Expected= "+Location+";Actual= "+ReproName+". ";
			}
			
			String ActualScopeName=getText(dashboardpage.rep1ScopeName);
			if(ActualScopeName.contains("\n")){
				ActualScopeName=ActualScopeName.replace("\n", " ");
			}
			if(ScopeName.contains("\n")){
				ScopeName=ScopeName.replace("\n", " ");
			}
			
			if (compareRes(ScopeName, ActualScopeName,true)){
				Res+="Passed: Expected= "+ScopeName+";Actual= "+ActualScopeName+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res+="#Failed!#: Expected= "+ScopeName+";Actual= "+ActualScopeName+". ";
			}
			
			if(!Color.equalsIgnoreCase("")){
				String reproColor=getColor(dashboardpage.KERep1color);
				if (compareRes(Color,reproColor,true)){
					Res+="Passed: Expected= "+Color+";Actual= "+reproColor+". ";
				}else{
					UAS.resultFlag="#Failed!#";
					Res+="#Failed!#: Expected= "+Color+";Actual= "+reproColor+". Bug 12860 opened. ";
				}
			}
			
			if(!status.equalsIgnoreCase("")){
				String reproStatus=getTextReproc(dashboardpage.KERep1Status,false);
				if (compareRes(status,reproStatus,true)){
					Res+="Passed: Expected= "+status+";Actual= "+reproStatus+". ";
				}else{
					UAS.resultFlag="#Failed!#";
					Res+="#Failed!#: Expected= "+status+";Actual= "+reproStatus+". Bug 12860 opened. ";
				}
			}
			break;
		case "Procedure Room 1": 
			String ProceRoomName=getText(dashboardpage.PR1Name);
			if (compareRes(Location, ProceRoomName,true)){
				Res="Passed: Expected= "+Location+";Actual= "+ProceRoomName+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res="#Failed!#: Expected= "+Location+";Actual= "+ProceRoomName+". ";
			}
			
			String ProcColor=getPR1Color();
			if (compareRes(Color,ProcColor,true)){
				Res+="Passed: Expected= "+Color+";Actual= "+ProcColor+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res+="#Failed!#: Expected= "+Color+";Actual= "+ProcColor+". ";
			}
			
			String ProcStatus=getPR1Status();
			if (compareRes(status, ProcStatus,true)){
				Res+="Passed: Expected= "+status+";Actual= "+ProcStatus+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res+="#Failed!#: Expected= "+status+";Actual= "+ProcStatus+". ";
			}
			
			if(!ScopeName.equalsIgnoreCase("")){
				String Proc1Scopes=getPR1Scopes();
				if(Proc1Scopes.contains("\n")){
					Proc1Scopes=Proc1Scopes.replace("\n", " ");
				}
				if(ScopeName.contains("\n")){
					ScopeName=ScopeName.replace("\n", " ");
				}
				if (compareRes(ScopeName, Proc1Scopes,true)){
					Res+="Passed: Expected= "+ScopeName+";Actual= "+Proc1Scopes+". ";
				}else{
					UAS.resultFlag="#Failed!#";
					Res+="#Failed!#: Expected= "+ScopeName+";Actual= "+Proc1Scopes+". ";
				}
			}
			break;
			
		case "Storage Area A-1": 
			String StorageAreaName=getText(dashboardpage.sA1Name);
			if (compareRes(Location, StorageAreaName,true)){
				Res="Passed: Expected= "+Location+";Actual= "+StorageAreaName+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res="#Failed!#: Expected= "+Location+";Actual= "+StorageAreaName+". ";
			}
			
			String StorageAreaCount=getText(dashboardpage.sA1TotalCount);
			if (compareRes(ScopeCount, StorageAreaCount,true)){
				Res+="Passed: Expected= "+ScopeCount+";Actual= "+StorageAreaCount+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res+="#Failed!#: Expected= "+ScopeCount+";Actual= "+StorageAreaCount+". ";
			}
			
			String StorageAreaColor=getColor(dashboardpage.sA1Name);
			if (compareRes(Color, StorageAreaColor,true)){
				Res+="Passed: Expected= "+Color+";Actual= "+StorageAreaColor+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res+="#Failed!#: Expected= "+Color+";Actual= "+StorageAreaColor+". ";
			}
			break;
			
		case "Sink1": 
			String Sink1Name=getSink1Name();
			if (compareRes(Location, Sink1Name,true)){
				Res="Passed: Expected= "+Location+";Actual= "+Sink1Name+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res="#Failed!#: Expected= "+Location+";Actual= "+Sink1Name+". ";
			}
			
			String Sink1Scopes=getSink1Scopes();
			if (compareRes(ScopeName, Sink1Scopes,true)){
				Res+="Passed: Expected= "+ScopeName+";Actual= "+Sink1Scopes+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res+="#Failed!#: Expected= "+ScopeName+";Actual= "+Sink1Scopes+". ";
			}
			break;
			
		case "Sink2": 
			String Sink2Name=getSink2Name();
			if (compareRes(Location, Sink2Name,true)){
				Res="Passed: Expected= "+Location+";Actual= "+Sink2Name+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res="#Failed!#: Expected= "+Location+";Actual= "+Sink2Name+". ";
			}
			
			String Sink2Scopes=getSink2Scopes();
			if (compareRes(ScopeName, Sink2Scopes,true)){
				Res+="Passed: Expected= "+ScopeName+";Actual= "+Sink2Scopes+". ";
			}else{
				UAS.resultFlag="#Failed!#";
				Res+="#Failed!#: Expected= "+ScopeName+";Actual= "+Sink2Scopes+". ";
			}
			break;
		}
		System.out.println("DailyDashboard Result: "+Res);
		return Res;
	}
}
