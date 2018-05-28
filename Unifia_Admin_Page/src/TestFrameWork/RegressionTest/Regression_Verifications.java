package TestFrameWork.RegressionTest;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;
import TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc;

public class Regression_Verifications extends Unifia_Admin_Selenium {
	
	public static TestFrameWork.UnifiaAdminGeneralFunctions.GeneralFunc GF;
	public static String VerifyScopeCycleAssociations(int ExpectAssociations[], int ActualAssociations[]) throws InterruptedException{
		int i=1;
		String result="";
		if(ExpectAssociations[0]==ActualAssociations[0]){
			result="Pass- The number of expected and actual associations match. ";
			//System.out.println("The number of expected and actual associations match. ");
			while(i<=ExpectAssociations[0]){
				if(ExpectAssociations[i]==ActualAssociations[i]){
					result="Pass- "+result+"AssociationID="+ExpectAssociations[i]+" is in the ScopeCycle Table as expected. ";
					//System.out.println("AssociationID="+ExpectAssociations[i]+" is in the ScopeCycle Table as expected. ");

				}else{
					result="#Failed!#- "+ExpectAssociations[i]+" should be in ScopeCycle table but it is not. ";
				}
				i++;
			}
		}else {
			result="#Failed!#- The number of expected and actual associations do not match. the expected # is"+ExpectAssociations[0]+" however, the actual # is "+ActualAssociations[0];
		}
		return result;

	}
	
}
