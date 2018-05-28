package TestFrameWork.Unifia_LocationDashboard;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;
import TestFrameWork.UnifiaAdminLoginPage.Login_Actions;

public class LD_Actions extends Unifia_Admin_Selenium{
	public static GeneralFunc SE_Gen;
	
	public static void ClickLocation(int Loc_ID) throws InterruptedException{
		Thread.sleep(500);
		//driver.findElementById("//a[@class='list-group-item' @href='/Sink/"+Loc_ID+"']").click();
		//driver.findElement(By.xpath("//a[@class='list-group-item' @href='/Sink/"+Loc_ID+"']")).click();
		int i=0;
		if(Loc_ID==41||Loc_ID==141||Loc_ID==241){
			i=1;
		}else if(Loc_ID==42||Loc_ID==142||Loc_ID==242){
			i=2;
		}else if(Loc_ID==43||Loc_ID==143||Loc_ID==243){
			i=3;
		}else if(Loc_ID==44||Loc_ID==144||Loc_ID==244){
			i=4;
		}else if(Loc_ID==45||Loc_ID==145||Loc_ID==245){
			i=5;
		}else if(Loc_ID==46||Loc_ID==146||Loc_ID==246){
			i=6;
		}		
		driver.findElement(By.xpath("//*[@id=\"bootstrapContainer\"]/div[2]/div/div/a["+i+"]")).click();

		Thread.sleep(500);
	}
	public static void ClickHome() throws InterruptedException{
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"hdrMain\"]/div/div/a")).click();
		Thread.sleep(500);
	}

}
