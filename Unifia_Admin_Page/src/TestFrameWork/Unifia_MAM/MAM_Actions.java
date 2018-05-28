package TestFrameWork.Unifia_MAM;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminReconMRC.MRC_Actions;

public class MAM_Actions extends Unifia_Admin_Selenium{
	public static GeneralFunc gf;
	
	public static void Click_MaterialsAndAssetManagement(){
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.switchTo().defaultContent(); //switches out of the iframe
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//a[@href='/MaterialsAndAssetManagement/MaterialsAndAssetManagement']")).click();

	}
	
	public static Map<String,String> getMAMColumns(){
		Map<String,String> columns=new LinkedHashMap<String, String>();
		int i=1;
		String columnName;
		while(driver.findElementsByXPath("//div[@id='mamGrid']/div[1]/table/tr[1]/th["+i+"]").size()!=0){
			columnName=driver.findElementByXPath("//div[@id='mamGrid']/div[1]/table/tr[1]/th["+i+"]").getText().replaceAll("\\s+","");
			columns.put(columnName.toLowerCase(), Integer.toString(i));
			i++;
		}
		return columns;
	}
	
	public static List<String> getMAMColumnNames(){
		List<String> columns=new ArrayList<String>();
		int i=1;
		String columnName;
		while(driver.findElementsByXPath("//div[@id='mamGrid']/div[1]/table/tr[1]/th["+i+"]").size()!=0){
			columnName=driver.findElementByXPath("//div[@id='mamGrid']/div[1]/table/tr[1]/th["+i+"]").getText();
			if(!columnName.equals("")){ //eliminating last column as it is for editing a record
				columns.add(columnName);
			}
			i++;
		}
		return columns;
	}
	
	public static List<String> getMAMColumnContent(String column){
		List<String> columnValues=new ArrayList<String>();
		String content="";
		String mamTableXpath="//*[@id='mamGrid']/div[2]/table/tbody";
		int rowCount=gf.getTableRowCount(mamTableXpath);
		Map<String,String> columnsWithIndex=getMAMColumns();//this method will return the column names with their index in the table
		String columnIndex=columnsWithIndex.get(column.toLowerCase().replaceAll("\\s+","")); //getting the index of the given column in the MRC table 
		if(rowCount>0){
			if(rowCount==1){
				if(driver.findElementsByXPath(mamTableXpath+"/tr[1]/td["+columnIndex+"]").size()>0){
					content=driver.findElementByXPath(mamTableXpath+"/tr[1]/td["+columnIndex+"]").getText();
					columnValues.add(content);
				}else{
					content+=driver.findElementByXPath(mamTableXpath+"/tr[1]/td[1]").getText();
					columnValues.add(content);
				}
			}else{
				for(int i=1;i<=rowCount;i++){
					content=driver.findElementByXPath(mamTableXpath+"/tr["+i+"]/td["+columnIndex+"]").getText(); //getting the value of the given column")
					columnValues.add(content);
				}
			}
		}
		return columnValues;
	}
	
	public static void click_MAM_Search() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[1]/th[12]/input").click();
		Thread.sleep(1500);
	}
	
	public static void search_MAM_ScopeName(String scopeName) throws InterruptedException{
		driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[2]/td[1]/input").clear();
		driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[2]/td[1]/input").sendKeys(scopeName);
		driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[2]/td[1]/input").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}
	
	public static void applyMAMColumnFilter(String columnName, String filterValue) throws InterruptedException{
		Map<String,String> columnsWithIndex=MAM_Actions.getMAMColumns();//this method will return the column names with their index in the table
		String index = columnsWithIndex.get(columnName.toLowerCase().replaceAll("\\s+","")); //getting the column index of Last Scan Staff ID
		driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[2]/td["+index+"]/input").clear();
		driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[2]/td["+index+"]/input").sendKeys(filterValue);
		driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[2]/td["+index+"]/input").sendKeys(Keys.RETURN);
		Thread.sleep(1000);
	}
	
	public static void click_MAMClearFilter() throws InterruptedException{
		Thread.sleep(500);
		driver.findElementByXPath("//input[@title='Clear filter']").click();
		Thread.sleep(1500);
	}

}
