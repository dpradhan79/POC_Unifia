package TestFrameWork.QlikView;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver;

import TestFrameWork.Unifia_Admin_Selenium;

public class QV_GeneralFunc extends Unifia_Admin_Selenium {
	// Generic Functions

	 public Alert alert;
	 public static UserAndPassword user;
	public static void InfectionPrevention() throws InterruptedException{
		Thread.sleep(5000);
		System.out.println("waited 5 seconds, and Click Infection Prevention ");
		
		//driver.findElement(By.className("QvContent")).findElement(By.xpath("//div[199]/div[2]/table/tbody/tr/td")).click();
		//driver.findElementByXPath("//div[@class='QvFrame Document_Menu_IP']/div[2]/table/tbody/tr/td").click();
		driver.findElement(By.xpath("//td[text()='Infection Prevention']")).click();
		Thread.sleep(5000);
		int intSize = driver.findElements(By.xpath("//td[text()='Infection Prevention']")).size();
		System.out.println("waited 5 seconds, and Click Infection Prevention ");
		System.out.println(intSize);
		if(intSize!=2)
		{
			driver.findElement(By.xpath("//td[text()='Infection Prevention']")).click();
			Thread.sleep(5000);
		}
		
	}
	
	public static void InfectionPreventionFromIPScreen() throws InterruptedException{
		Thread.sleep(5000);
		System.out.println("waited 5 seconds, and Click Infection Prevention ");
		
		//driver.findElement(By.className("QvContent")).findElement(By.xpath("//div[199]/div[2]/table/tbody/tr/td")).click();
		driver.findElementByXPath("//div[@class='QvFrame Document_Menu_ICM']/div[2]/table/tbody/tr/td").click();
		Thread.sleep(2000);
	}

	public static void DailyDashboard() throws InterruptedException{
		Thread.sleep(3000);
		System.out.println("Click Daily Dashboard ");
		//driver.findElement(By.className("QvContent")).findElement(By.xpath("//div/div[61]/div[2]/table/tbody/tr/td[@text='Daily Dashboard']")).click();
		//driver.findElement(By.className("QvContent")).findElement(By.xpath("//div[61]/div[2]/table/tbody/tr/td")).click();
		driver.findElementByXPath("//div[@class='QvFrame Document_Menu_DD']/div[2]/table/tbody/tr/td").click();
		Thread.sleep(2000);
	}
	
	public static void Launch_UnifiaQlikView(String URL) throws InterruptedException, IOException{
		
		  if (ExecBrowser.equalsIgnoreCase("iexplore")){
				Unifia_Admin_Selenium.driver.get(Unifia_Admin_Selenium.QV_URL);
				Thread.sleep(3000);	
				user=new UserAndPassword("\\\\qvtest01","0lympu$");
		   		Unifia_Admin_Selenium.driver.switchTo().alert().authenticateUsing(user); 
		   		Thread.sleep(2000);
		}else{
			Thread.sleep(2000);
			Unifia_Admin_Selenium.driver.get(Unifia_Admin_Selenium.QV_URL);
			Thread.sleep(3000);
			String[] Credentials = new String[] {"C:\\Unifia\\QlikView\\Launch-Paramters.exe","qvtest01","0lympu$"};
			//String[] Credentials = new String[] {"C:\\Unifia\\QlikView\\Launch-Paramters.exe",Unifia_Admin_Selenium.appUser,Unifia_Admin_Selenium.appPassword};
	        Runtime.getRuntime().exec(Credentials);
			Thread.sleep(2000);
		}
	}
	
	public static void Launch_UnifiaQMC(String URL, String Env) throws InterruptedException, IOException{
		if (ExecBrowser.equalsIgnoreCase("iexplore"))	{			
			Unifia_Admin_Selenium.driver.get(Unifia_Admin_Selenium.QMC_URL);
			Thread.sleep(3000);	
			user=new UserAndPassword(Unifia_Admin_Selenium.QMCUser,Unifia_Admin_Selenium.QMCPassword);
			Unifia_Admin_Selenium.driver.switchTo().alert().authenticateUsing(user);    	
			Thread.sleep(3000);
			Unifia_Admin_Selenium.driver.findElement(By.xpath("//img[contains(@src,'/QMCCommon/Images/plus.png')]")).click();
			Thread.sleep(1000);
			Unifia_Admin_Selenium.driver.findElement(By.xpath("//img[contains(@src,'/QMCCommon/Images/plus.png')]")).click();
			System.out.println("click default expand");
			Thread.sleep(1000);
		}else{
			Thread.sleep(1000);
			driver.get(Unifia_Admin_Selenium.QMC_URL);
			Thread.sleep(1000);
			String[] Credentials = new String[] {"C:\\Unifia\\QlikView\\Launch-Paramters.exe",Unifia_Admin_Selenium.QMCUser,Unifia_Admin_Selenium.QMCPassword};
	        Runtime.getRuntime().exec(Credentials);
			Thread.sleep(20000);
			//expand 
			driver.findElement(By.className("ContentDivQMC")).findElement(By.xpath("//*[@id='TreeImageXS:37062ac6-2726-5342-e417-9bc64943b914:00000000-0000-0000-0000-000000000000']")).click();
	//		driver.findElement(By.className("ContentDivQMC")).findElement(By.xpath("//*[@id='TreeImageCY:f817210b-711d-23b1-769a-816d159a6938:c3542b9a-e993-421c-b777-5551b9836ee6']")).click();
			System.out.println("logged into QMC.  click first expand");
			Thread.sleep(1500);
			driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/div[2]/div/div[1]/img[1]")).click();
			System.out.println("click default expand");
			Thread.sleep(1000);
		}

	}

	public static void Run_Reports() throws InterruptedException, IOException{
		System.out.println("Wait 1 min for Unifia-DataExtract.qvw to execute, then Run reports and wait 10 seconds");
		Thread.sleep(61000);
//		driver.findElement(By.className("ContentDivQMC")).findElement(By.xpath("//*[@id='StartImgTK:56c4e39e-6b26-a86d-401e-bc15850824fa:c3542b9a-e993-421c-b777-5551b9836ee6']")).click();
		driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/div[2]/div/div[2]/div[2]/div[1]/img[4]")).click();
		Thread.sleep(10000);
	}
	
	public static void waitforOneSecond () throws InterruptedException{
		Thread.sleep(1000);
	}
	
	public static void waitforOneMinute () throws InterruptedException{
		Thread.sleep(61000);
	}
	
	public static String GetTableParentID(String Header) throws InterruptedException{
		String strId = "";
		String Expression = "/parent::Div";
		Integer cntr=0;
		while(strId.isEmpty())
		{
			strId = driver.findElement(By.xpath("//*[@title='"+Header+"']" +Expression)).getAttribute("id");
			if (cntr>0){
				Expression = Expression + "/parent::Div";
			}
			cntr=cntr+1;
		}
		return strId;
	}
	
	public static boolean GetLocationExistence(String title){
		boolean blnStatus = false;
		int intSize;
		intSize = driver.findElements(By.xpath("//*[@title= '"+title+"']")).size();
		if(intSize!=0)
		{
			blnStatus = true;
		}
		return blnStatus;
	}
	
	public static boolean VerifyDashBoardPage(){
		boolean blnStatus = false;
		int intSize;
		intSize = driver.findElements(By.xpath("//td[text()='DAILY DASHBOARD']")).size();
		if(intSize!=0)
		{
			blnStatus = true;
		}
		return blnStatus;
	}
	
	public static void SelectClearSelections() throws InterruptedException{
		//Click on the Clear Selections.
		GeneralFunc.WaitForObjectExistence("//td[text()='Clear Selections']", 5);
		driver.findElement(By.xpath("//td[text()='Clear Selections']")).click();

	}
	
	public static boolean VerifyCurrentSelections(String strColumn, String strValue) throws InterruptedException{
		boolean blnStatus = false;
		GeneralFunc.WaitForObjectExistence("//*[@class='QvFrame Document_CurrentSelection']/div[2]/div/div[1]/div[5]/div/div[1]", 5);
		String strColumnName = driver.findElement(By.xpath("//*[@class='QvFrame Document_CurrentSelection']/div[2]/div/div[1]/div[5]/div/div[1]")).getAttribute("title");
		String strColumnValue = driver.findElement(By.xpath("//*[@class='QvFrame Document_CurrentSelection']/div[2]/div/div[1]/div[5]/div/div[2]")).getAttribute("title");
		if(strColumnName.equalsIgnoreCase(strColumn)&&strColumnValue.equalsIgnoreCase(strValue))
		{
			blnStatus = true;
		}
		return blnStatus;
	}
	
	public static String[][] FilteredData(String strColumn) throws InterruptedException{
		
		String[][] strData = null;
		String[][] InRoomTime = {{"26","11","25","20","27","15","25","-","23"},
    			{"19","19","29","24","19","22","21","-","22"},
    			{"26","19","27","21","22","33","24","-","24"},
    			{"24","-","27","22","22","24","24","-","24"},
    			{"24","-","27","22","22","24","24","-","24"},
    			{"23","18","-","-","-","-","-","-","19"},
    			{"24","-","27","22","22","24","24","-","24"},
    			{"24","-","27","22","22","24","24","-","24"}};
    	String[][] AwaitingLTTime = {{"11","2","7","6","9","6","8","-","7"},
    			{"4","10","11","5","3","5","5","-","7"},
    			{"6","9","5","7","3","4","5","-","5"},
    			{"7","-","9","6","5","5","6","-","6"},
    			{"7","-","9","6","5","5","6","-","6"},
    			{"7","9","-","-","-","-","-","-","8"},
    			{"7","-","9","6","5","5","6","-","6"},
    			{"7","-","9","6","5","5","6","-","6"}};
    	String[][] ManualCleanTime = {{"10","10","9","12","16","9","11","-","11"},
    			{"10","10","12","8","12","11","12","-","11"},
    			{"17","21","15","9","11","9","10","-","12"},
    			{"13","-","11","9","13","10","11","-","11"},
    			{"13","-","11","9","13","10","11","-","11"},
    			{"10","11","-","-","-","-","-","-","11"},
    			{"13","-","11","9","13","10","11","-","11"},
    			{"13","-","11","9","13","10","11","-","11"}};
    	String[][] AwaitingReproTime = {{"9","5","5","5","6","11","10","-","7"},
    			{"6","6","4","5","6","10","5","-","6"},
    			{"12","8","6","3","8","4","6","-","7"},
    			{"7","-","4","4","6","8","8","-","6"},
    			{"7","-","4","4","6","8","8","-","6"},
    			{"13","6","-","-","-","-","-","-","7"},
    			{"7","-","4","4","6","8","8","-","6"},
    			{"7","-","4","4","6","8","8","-","6"}};
    	String[][] ReprocessingTime = {{"31","6","27","36","40","26","32","-","28"},
    			{"33","9","29","31","50","29","38","-","27"},
    			{"27","12","32","30","36","35","34","-","29"},
    			{"29","-","28","32","39","30","34","-","32"},
    			{"29","-","28","32","39","30","34","-","32"},
    			{"33","9","29","-","-","-","-","-","19"},
    			{"29","-","28","32","39","30","34","-","32"},
    			{"29","-","28","32","39","30","34","-","32"}};
    	String[][] TurnAroundTime = {{"62","21","50","58","72","53","62","-","57"},
    			{"54","36","58","50","72","57","67","-","52"},
    			{"63","43","59","51","58","54","55","-","54"},
    			{"58","-","55","53","66","55","61","-","57"},
    			{"58","-","55","53","66","55","61","-","57"},
    			{"64","36","-","-","-","-","-","-","42"},
    			{"58","-","55","53","66","55","61","-","57"},
    			{"58","-","55","53","66","55","61","-","57"}};
    	String[][] InUseTime = {{"88","47","75","78","99","69","87","-","78"},
    			{"73","55","87","74","89","79","89","-","74"},
    			{"90","62","86","72","80","87","80","-","79"},
    			{"82","-","82","75","89","80","85","-","82"},
    			{"82","-","82","75","89","80","85","-","82"},
    			{"87","55","-","-","-","-","-","-","61"},
    			{"82","-","82","75","89","80","85","-","82"},
    			{"82","-","82","75","89","80","85","-","82"},};
    	
    	switch (strColumn){
		case "ScopeInRoomTime":
			strData = InRoomTime;
		break;
		case "AwaitingLTTime":
			strData = AwaitingLTTime;
		break;
		case "ManualCleanTime":
			strData = ManualCleanTime;
		break;
		case "AwaitingReproTime":
			strData = AwaitingReproTime;
		break;
		case "ReprocessingTime":
			strData = ReprocessingTime;
		break;
		case "ScopeTurnAroundTime":
			strData = TurnAroundTime;
		break;
		case "ScopeInUseTime":
			strData = InUseTime;
		break;
		}
    	
    	return strData;
	}	


public static void shiftRight(int i, String[][] array, int intNo) {
		int j =0;
		String temp = "";
		while(j<intNo)
		{
	        int m = array[i].length;
	        String temp1 = array[i][m-1];
	        String temp2 = array[i][m-2];
	        for (int k=m-1; k>=1; k--){
	            array[i][k] = array[i][k-1];
	            if(k==7){
	            	temp = array[i][k];}
	            System.out.println(array[i][k]);
	        }
	        array[i][m-1] = temp1;
	        array[i][m-2] = temp2;
	        array[i][0] = temp;
	        System.out.println(array[i][0]);
	        j++;
		}
    }
	
}
