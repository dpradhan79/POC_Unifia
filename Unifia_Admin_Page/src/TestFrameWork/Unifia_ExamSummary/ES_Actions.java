package TestFrameWork.Unifia_ExamSummary;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import TestFrameWork.Unifia_Admin_Selenium;

public class ES_Actions extends Unifia_Admin_Selenium{
	
	/*public static String get_SelectedFacility(){
		Select droplist = new Select(driver.findElementById("ddlSelectedFacility")); 
		WebElement selected=droplist.getFirstSelectedOption();
		String selectedFacility=selected.getText();
		return selectedFacility;
	}
*/
	public static void DateFilter(String StartDate,String EndDate) throws InterruptedException{
		int divNum=4;
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"reportrange\"]")).click();
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[1]/ul/li[7]")).click(); //this is for custom rang and then need to click 'apply' 
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[2]/div[1]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), StartDate);	
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[3]/div[1]/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"),EndDate); 		
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[2]/div[1]/input")).click();
		driver.findElement(By.xpath("/html/body/div["+divNum+"]/div[1]/div/button[1]")).click();
		Thread.sleep(500);
	}
	
	public static void ApplyFilter() throws InterruptedException{
		driver.findElementById("btnApplyExamSummaryFilter").click();
		Thread.sleep(500);
	}
	
	public static void Clear_Filter() throws InterruptedException {
		Thread.sleep(100);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElementById("btnClearExamSummaryFilter").click();
		Thread.sleep(100);
	}

	public static String get_ActiveProcedureRoomCount(){
		String activePRCount=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[1]/td[2]").getText();
		return activePRCount;
	}
	
	public static String get_ScopesUsedCount(){
		String scopesCount=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[2]/td[2]").getText();
		return scopesCount;
	}
	
	public static String get_ExamWithPatientCount(){
		String scopesCount=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[3]/td[2]").getText();
		return scopesCount;
	}
	
	public static String get_ExamWithScope_NoPatCount(){
		String examWithScope_NoPatCount=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[4]/td[2]").getText();
		return examWithScope_NoPatCount;
	}
	
	public static String get_ExamWithScopePreclean_NoPatCount(){
		String examWithScopePreclean_NoPatCount=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[5]/td[2]").getText();
		return examWithScopePreclean_NoPatCount;
	}
	
	public static String get_ExamWithPatScope_NoPrecleanCount(){
		String examWithPatScope_NoPrecleanCount=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[6]/td[2]").getText();
		return examWithPatScope_NoPrecleanCount;
	}
	
	public static String get_ExamWithPatAfterPrecleanCount(){
		String examWithPatAfterPrecleanCount=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[7]/td[2]").getText();
		return examWithPatAfterPrecleanCount;
	}
	
	public static String get_ExamWithPat_NoScopeCount(){
		String examWithPat_NoScopeCount=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[8]/td[2]").getText();
		return examWithPat_NoScopeCount;
	}
	
	public static void click_ActiveProcedureRoomCount() throws InterruptedException{
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[1]/td[2]").click();
		Thread.sleep(2000);
	}
	
	public static void click_ScopesUsedCount() throws InterruptedException{
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[2]/td[2]").click();
		Thread.sleep(2000);
	}
	
	public static void click_ExamWithPatientCount() throws InterruptedException{
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[3]/td[2]").click();
		Thread.sleep(2000);
	}
	
	public static void click_ExamWithScope_NoPatCount() throws InterruptedException{
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[4]/td[2]").click();
		Thread.sleep(2000);
	}
	
	public static void click_ExamWithScopePreclean_NoPatCount() throws InterruptedException{
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[5]/td[2]").click();
		Thread.sleep(2000);
	}
	
	public static void click_ExamWithPatScope_NoPrecleanCount() throws InterruptedException{
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[6]/td[2]").click();
		Thread.sleep(2000);
	}
	
	public static void click_ExamWithPatAfterPrecleanCount() throws InterruptedException{
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[7]/td[2]").click();
		Thread.sleep(2000);
	}
	
	public static void click_ExamWithPat_NoScopeCount() throws InterruptedException{
		driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr[8]/td[2]").click();
		Thread.sleep(2000);
	}
	
	public static Map<String, String> get_ES_TableValues(){
		Map<String, String> allValuesOfES = new LinkedHashMap<String, String>();
		int i=1,j=1;
		while(driver.findElementsByXPath("//*[@id='divDashboardSummary']/table/tbody/tr["+(i)+"]/td["+(j)+"]").size()>0){
			String name=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr["+(i)+"]/td["+(j)+"]/label").getText();
			String value=driver.findElementByXPath("//*[@id='divDashboardSummary']/table/tbody/tr["+(i)+"]/td["+(j+1)+"]").getText();
			allValuesOfES.put(name, value);
			i++;
		}
		return allValuesOfES;
	}
	
}
