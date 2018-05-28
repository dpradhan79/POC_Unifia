package TestFrameWork.Emulator;
import java.util.concurrent.TimeUnit;




import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class Emulator_Actions extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	//[RK]-27June16
	public static boolean ScanItem (String LocationVal, String ScanTypeVal, String ScanSubTypeVal, String ScanItemVal, String KeyEntryVal)throws InterruptedException{
		Unifia_Admin_Selenium.isEmulator=true;
		String KeyEntryVal2="";
		Thread.sleep(800);
		boolean Res;
		if (driverCount.equalsIgnoreCase("second")){
			driver2.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}else{
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}
		//Location
		if (LocationVal!=""){
			if (!GF.SelectFromListBox(GF.EmulatorID_lst_Location,LocationVal)){
				System.out.println("Emulator: Could not select the element '"+LocationVal+"' from location drop down. Try to refresh and re-scan.");
				Res = ReTryScanItem(LocationVal,ScanTypeVal,ScanSubTypeVal,ScanItemVal,KeyEntryVal);
				if(Res==false){
					return false;	
				}
			}
		}else{
			System.out.println("Emulator: Location Cannot be empty");
			return false;
		}
		//Scan Type
		if (ScanTypeVal!=""){
			Thread.sleep(800);
			if (!GF.SelectFromListBox(GF.EmulatorID_lst_ScanType,ScanTypeVal)){
				System.out.println("Emulator: Could not select the element '"+ScanTypeVal+"' from Scan Type drop down. Try to refresh and re-scan.");
				Res = ReTryScanItem(LocationVal,ScanTypeVal,ScanSubTypeVal,ScanItemVal,KeyEntryVal);
				if(Res==false){
					return false;	
				}
			}
		}else{
			System.out.println("Emulator: Scan Type Cannot be empty");
			return false;
		}
		//Staff 
		if (ScanTypeVal.equals("Staff")){
			Thread.sleep(800);
			if (ScanSubTypeVal!=""){
				if (!GF.SelectFromListBox(GF.EmulatorID_lst_ScanSubType,ScanSubTypeVal)){
					System.out.println("Emulator: Could not select the element '"+ScanSubTypeVal+"' from Staff drop down. Try to refresh and re-scan");
					Res = ReTryScanItem(LocationVal,ScanTypeVal,ScanSubTypeVal,ScanItemVal,KeyEntryVal);
					if(Res==false){
						return false;	
					}
				}
			}else{
				System.out.println("Emulator: Staff Type Cannot be empty as Scan Type is 'Staff'");
				return false;
			}
		}
		//Scan Item
		if (ScanItemVal!=""){
			Thread.sleep(800);
			if (!GF.SelectFromListBox(GF.EmulatorID_lst_ScanItem,ScanItemVal)){
				System.out.println("Emulator: Could not select the element '"+ScanItemVal+"' from Scan Item drop down. Try to refresh and re-scan.");	
				Res = ReTryScanItem(LocationVal,ScanTypeVal,ScanSubTypeVal,ScanItemVal,KeyEntryVal);
				if(Res==false){
					return false;	
				}
			}
		}else{
			if (!ScanTypeVal.equals("Key Entry")){
				System.out.println("Emulator: Scan Item Cannot be empty");	
				return false;
			}
		}
		//Key Entry
		if (ScanTypeVal.equals("Key Entry")){
			Thread.sleep(800);
			if (KeyEntryVal!=""){
				if (KeyEntryVal.equals("*")){
					KeyEntryVal2="Asterisk";
				}else if (KeyEntryVal.equals("#")){
					KeyEntryVal2="HashTag";
				}else
					KeyEntryVal2=KeyEntryVal;	
				}else{
					System.out.println("Emulator: Key Entry value Cannot be empty when Scan Type is selected as 'Key Entry'. Try to refresh and re-scan.");	
					Res = ReTryScanItem(LocationVal,ScanTypeVal,ScanSubTypeVal,ScanItemVal,KeyEntryVal);
					if(Res==false){
						return false;	
					}
				}
				if (!GF.ClickObjectbyID(GF.EmulatorID_Btn_KeyPad+KeyEntryVal2)){
					System.out.println("Emulator: Could not click the element '"+KeyEntryVal+"' on scan key pad. Try to refresh and re-scan.");	
					Res = ReTryScanItem(LocationVal,ScanTypeVal,ScanSubTypeVal,ScanItemVal,KeyEntryVal);
					if(Res==false){
						return false;	
					}
				}
		}
		//Scan button
		Thread.sleep(800);
		if (!GF.ClickObjectbyID(GF.EmulatorID_Btn_Scan)){
			System.out.println("Emulator: Could not click the element 'Scan' on scan key pad");	
			return false;
		}else{
			return true;
		}
	}

	public static boolean ReTryScanItem (String LocationVal, String ScanTypeVal, String ScanSubTypeVal, String ScanItemVal, String KeyEntryVal)throws InterruptedException{
		Unifia_Admin_Selenium.isEmulator=true;
		Unifia_Admin_Selenium.ScannerCount=0;
		if (driverCount.equalsIgnoreCase("second")){
			driver2.navigate().refresh();
		}else{
			driver.navigate().refresh();
		}
		
		String KeyEntryVal2="";
		Thread.sleep(1500);
		if (driverCount.equalsIgnoreCase("second")){
			driver2.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}else{
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}
		
		//Location
		if (LocationVal!=""){
			if (!GF.SelectFromListBox(GF.EmulatorID_lst_Location,LocationVal)){
				System.out.println("Emulator: Could not select the element '"+LocationVal+"' from location drop down");
				return false;
			}
		}else{
			System.out.println("Emulator: Location Cannot be empty");
			return false;
		}
		//Scan Type
		if (ScanTypeVal!=""){
			Thread.sleep(800);
			if (!GF.SelectFromListBox(GF.EmulatorID_lst_ScanType,ScanTypeVal)){
				System.out.println("Emulator: Could not select the element '"+ScanTypeVal+"' from Scan Type drop down");
				return false;
			}
		}else{
			System.out.println("Emulator: Scan Type Cannot be empty");
			return false;
		}
		//Staff 
		if (ScanTypeVal.equals("Staff")){
			Thread.sleep(800);
			if (ScanSubTypeVal!=""){
				if (!GF.SelectFromListBox(GF.EmulatorID_lst_ScanSubType,ScanSubTypeVal)){
					System.out.println("Emulator: Could not select the element '"+ScanSubTypeVal+"' from Staff drop down");
					return false;
				}
			}else{
				System.out.println("Emulator: Staff Type Cannot be empty as Scan Type is 'Staff'");
				return false;
			}
		}
		//Scan Item
		if (ScanItemVal!=""){
			Thread.sleep(800);
			if (!GF.SelectFromListBox(GF.EmulatorID_lst_ScanItem,ScanItemVal)){
				System.out.println("Emulator: Could not select the element '"+ScanItemVal+"' from Scan Item drop down");	
				return false;
			}
		}else{
			if (!ScanTypeVal.equals("Key Entry")){
				System.out.println("Emulator: Scan Item Cannot be empty");	
				return false;
			}
		}
		//Key Entry
		if (ScanTypeVal.equals("Key Entry")){
			Thread.sleep(800);
			if (KeyEntryVal!=""){
				if (KeyEntryVal.equals("*")){
					KeyEntryVal2="Asterisk";
				}else if (KeyEntryVal.equals("#")){
					KeyEntryVal2="HashTag";
				}else
					KeyEntryVal2=KeyEntryVal;	
				}else{
					System.out.println("Emulator: Key Entry value Cannot be empty when Scan Type is selected as 'Key Entry'");	
					return false;
				}
				if (!GF.ClickObjectbyID(GF.EmulatorID_Btn_KeyPad+KeyEntryVal2)){
					System.out.println("Emulator: Could not click the element '"+KeyEntryVal+"' on scan key pad");	
					return false;
				}
		}
		//Scan button
		Thread.sleep(800);
		if (!GF.ClickObjectbyID(GF.EmulatorID_Btn_Scan)){
			System.out.println("Emulator: Could not click the element 'Scan' on scan key pad");	
			return false;
		}else{
			return true;
		}
	}
	
}
