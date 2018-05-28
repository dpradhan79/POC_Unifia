package TestFrameWork.Unifia_LocationDashboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import TestFrameWork.Unifia_Admin_Selenium;

public class LD_Verification extends Unifia_Admin_Selenium {
	private static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	private static TestFrameWork.Emulator.GetIHValues IHV;
	private static TestFrameWork.Unifia_Admin_Selenium UAS;
	
	public static String VerifyLocationFacility(String Location,String Facility) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[1]/div[4]/h3/span[1]")).getText();
		if(Actual.equalsIgnoreCase(Location)){
			Result="Pass - VerifyLocationFacility Location ="+Location;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyLocationFacility Location was supposed to be "+Location+" but it was "+Actual;
		}
		Thread.sleep(100);

		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[1]/div[4]/h3/span[2]")).getText();
		if(Actual.equalsIgnoreCase(Facility)){
			Result=Result+"; Pass - VerifyLocationFacility Facility ="+Facility;
		}else{
			UAS.resultFlag="#Failed!#";
			Result=Result+"; #Failed!# - VerifyLocationFacility Facility was supposed to be "+Facility+" but it was "+Actual;
		}
		return Result;
	}

	public static String VerifyMessage(String Message) throws InterruptedException{
		String Result="";
		Thread.sleep(3000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[2]/h1")).getText();
		Actual=String.format(Actual);
		if(Actual.equalsIgnoreCase(Message)){
			Result="Pass - VerifyMessage Message ="+Message;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyMessage Message was supposed to be '"+Message+"' but it was '"+Actual+"'.";
		}
		return Result;
	}
	
	public static String VerifyTopScopeName(String ScopeName) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[1]/div[1]/h3/span")).getText();
		if(Actual.equalsIgnoreCase(ScopeName)){
			Result="Pass - VerifyTopScopeName Scope Name ="+ScopeName;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyTopScopeName Scope Name was supposed to be '"+ScopeName+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		return Result;
	}
	
	public static String VerifyBottomScopeName(String ScopeName) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[3]/div/div[1]/div[3]")).getText();
		if(Actual.equalsIgnoreCase(ScopeName)){
			Result="Pass - VerifyBottomScopeName Scope Name ="+ScopeName;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyBottomScopeName Scope Name was supposed to be '"+ScopeName+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";;
		}
		return Result;
	}
	public static String VerifyTopStaff(String StaffName) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[1]/div[2]/h3/span")).getText();
		if(Actual.equalsIgnoreCase(StaffName)){
			Result="Pass - VerifyTopStaff Staff Name ="+StaffName;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyTopStaff Staff Name was supposed to be '"+StaffName+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		return Result;
	}
	public static String VerifyBottomStaff(String StaffName) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[3]/div/div[2]/div[3]")).getText();
		if(Actual.equalsIgnoreCase(StaffName)){
			Result="Pass - VerifyBottomStaff Staff Name ="+StaffName;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyBottomStaff Staff Name was supposed to be '"+StaffName+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		return Result;
	}
	
	public static String VerifyTopLT(String LT) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		if(LT.equalsIgnoreCase("Leak Test Not Started")){
			Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[1]/div[3]/h3/span[1]")).getText();
		}else if(LT.equalsIgnoreCase("Leak Test Passed")){
			Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[1]/div[3]/h3/span[2]")).getText();
		}else if(LT.equalsIgnoreCase("Leak Test Failed")){
			Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[1]/div[3]/h3/span[3]")).getText();
		}
		if(Actual.equalsIgnoreCase(LT)){
			Result="Pass - VerifyTopLT Leak Test ="+LT;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyTopLT Leak Test was supposed to be '"+LT+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		return Result;
	}
	public static String VerifyBottomLT(String LT) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[3]/div/div[3]/div[3]")).getText();
		if(Actual.equalsIgnoreCase(LT)){
			Result="Pass - VerifyBottomLT Leak Test ="+LT;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyBottomLT Leak Test was supposed to be '"+LT+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		return Result;
	}
	public static String VerifyBottomMCStart(String MC) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[3]/div/div[4]/div[3]")).getText();
		if(Actual.equalsIgnoreCase(MC)){
			Result="Pass - VerifyBottomMCStart Manual Clean ="+MC;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyBottomMCStart Manual Clean was supposed to be '"+MC+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		return Result;
	}

	public static String VerifyBottomMCEnd(String MC) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"workFlow\"]/div[2]/div/div[4]/div")).getText();
		if(Actual.equalsIgnoreCase(MC)){
			Result="Pass - VerifyBottomMCEnd Manual Clean ="+MC;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyBottomMCEnd Manual Clean was supposed to be '"+MC+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		return Result;
	}
	public static String VerifyCurrentStep(String StepNum,String StepText) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[3]/div[1]/div[2]/h1")).getText();
		if(Actual.equalsIgnoreCase(StepNum)){
			Result="Pass - Verify Current Step Number ="+StepNum;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Verify Current Step was Number supposed to be '"+StepNum+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[3]/div[2]/div[2]")).getText();
		if(Actual.equalsIgnoreCase(StepText)){
			Result=Result+" Pass - Verify Current Step text="+StepText;
		}else{
			UAS.resultFlag="#Failed!#";
			Result=Result+" #Failed!# - Verify Current Step text was supposed to be '"+StepText+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}		
		return Result;
	}
	public static String VerifyNextStep(String StepNum,String StepText) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[3]/div[1]/div[3]/h1")).getText();
		if(Actual.equalsIgnoreCase(StepNum)){
			Result="Pass - Verify Next Step Number ="+StepNum;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Verify Next Step was Number supposed to be '"+StepNum+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[3]/div[2]/div[3]")).getText();
		if(Actual.equalsIgnoreCase(StepText)){
			Result=Result+" Pass - Verify Next Step text="+StepText;
		}else{
			UAS.resultFlag="#Failed!#";
			Result=Result+" #Failed!# - Verify Next Step text was supposed to be '"+StepText+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}		
		return Result;
	}
	public static String VerifyPreviousStep(String StepNum,String StepText) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[3]/div[1]/div[1]/h1")).getText();
		if(Actual.equalsIgnoreCase(StepNum)){
			Result="Pass - Verify Previous Step Number ="+StepNum;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - Verify Previous Step was Number supposed to be '"+StepNum+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[3]/div[2]/div[1]")).getText();
		if(Actual.equalsIgnoreCase(StepText)){
			Result=Result+" Pass - Verify Previous Step text="+StepText;
		}else{
			UAS.resultFlag="#Failed!#";
			Result=Result+" #Failed!# - Verify Previous Step text was supposed to be '"+StepText+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}		
		return Result;
	}

	public static String VerifyFinalMessage(String Message) throws InterruptedException{
		String Result="";
		Thread.sleep(3000);
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[2]/span/div/h1")).getText();
		Actual=String.format(Actual);
		if(Actual.equalsIgnoreCase(Message)){
			Result="Pass - VerifyFinalMessage ="+Message;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="#Failed!# - VerifyFinalMessage: Message was supposed to be '"+Message+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		return Result;
	}

	public static String VerifyTJFBottomMessage(String FirstLine,String SecondLine) throws InterruptedException{
		String Result="";
		Thread.sleep(1000);
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[4]/div[1]")).getText();
		if(Actual.equalsIgnoreCase(FirstLine)){
			Result="VerifyTJFBottomMessage Pass - Verify first line for bottom message ="+FirstLine;
		}else{
			UAS.resultFlag="#Failed!#";
			Result="VerifyTJFBottomMessage #Failed!# - Verify first line for bottom message supposed to be '"+FirstLine+"' but it was '"+Actual+"', Bug 11885 is opened for this issue.";
		}
		Actual=driver.findElement(By.xpath("//*[@id=\"IFU\"]/div[4]/div[2]")).getText();
		if(Actual.equalsIgnoreCase(SecondLine)){
			Result=Result+" Pass - Verify second line for bottom message="+SecondLine;
		}else{
			UAS.resultFlag="#Failed!#";
			Result=Result+" #Failed!# - second line for bottom message was supposed to be '"+SecondLine+"' but it was '"+Actual+"'.";
		}		
		return Result;
	}
	public static String VerifyInactiveLocations(String[] InactiveItems,int NumInactiveItems){
		String Result="";
		String[] InactiveElements=new String[10];
		int j=0;
		for(int k=0;k<10;k++){
			InactiveElements[k]="zz";
		}
		for (int lendd=1;lendd<7;lendd++){			
			if(driver.findElement(By.xpath("//*[@id=\"bootstrapContainer\"]/div[2]/div/div/a["+lendd+"]")).getAttribute("class").contains("disabled")){
				InactiveElements[j]=driver.findElement(By.xpath("//*[@id=\"bootstrapContainer\"]/div[2]/div/div/a["+lendd+"]")).getText();
				System.out.println(driver.findElement(By.xpath("//*[@id=\"bootstrapContainer\"]/div[2]/div/div/a["+lendd+"]")).getText()+"=Disabled");
				j++;
			} else {
				System.out.println(driver.findElement(By.xpath("//*[@id=\"bootstrapContainer\"]/div[2]/div/div/a["+lendd+"]")).getText()+"=Enabled");
			}
		}
		if(j>1){
			Arrays.sort(InactiveElements); 
			Arrays.sort(InactiveItems);

		}
		for(int i=0;i<NumInactiveItems;i++){
			if(InactiveElements[i].equalsIgnoreCase(InactiveItems[i])){
				Result="VerifyInactiveLocations = Pass - elementID has inactive item ="+InactiveItems[i];
			}else{
				UAS.resultFlag="#Failed!#";
				Result="VerifyInactiveLocations #Failed!# - Inactive item "+InactiveItems[i]+" is not disabled. "+InactiveElements[i]+" is disabled.";
			}
		}
		return Result;
	}
}
