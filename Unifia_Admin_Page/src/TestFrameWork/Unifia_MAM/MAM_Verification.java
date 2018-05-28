package TestFrameWork.Unifia_MAM;

import java.util.LinkedHashMap;
import java.util.Map;
import TestFrameWork.Unifia_Admin_Selenium;

public class MAM_Verification extends Unifia_Admin_Selenium {
	public static TestFrameWork.Unifia_Admin_Selenium UAS; 
	private static TestFrameWork.Unifia_IP.IP_Verification IP_V;

	public static StringBuffer verifyScopeDetails(String scope, String columnsWithValues) throws InterruptedException{
		StringBuffer result= new StringBuffer();
		Map<String,String> actualColumnValues=new LinkedHashMap<String, String>();
		Map<String,String> expectedColumnValues=new LinkedHashMap<String, String>();
		
		Map<String,String> columnsWithIndex=MAM_Actions.getMAMColumns();//this method will return the column names with their index in the table
		
		//Clicking LastScanDateTime column twice so as to get the latest updated record at the top
		String index = columnsWithIndex.get("Last Scan Date Time".toLowerCase().replaceAll("\\s+","")); //getting the column index of Last Scan Staff ID
		driver.findElementByXPath("//div[@id='mamGrid']/div[1]/table/tr[1]/th["+index+"]").click();
		Thread.sleep(4000);
		driver.findElementByXPath("//div[@id='mamGrid']/div[1]/table/tr[1]/th["+index+"]").click();
		Thread.sleep(4000);
		String lastScanDate=driver.findElementByXPath("//*[@id='mamGrid']/div[2]/table/tbody/tr[1]/td["+index+"]/div/span").getText();
		IP_V.verifyDateFormat("Material And Asset Management - Last Scan Date Time",lastScanDate );
		
		String []getcolumns=columnsWithValues.split(";");
		
		for(String column: getcolumns){
			String []columnValue=column.split("==");
			expectedColumnValues.put(columnValue[0].toLowerCase().replaceAll("\\s+",""), columnValue[1]);
		}
		
		//getting the UI values for the columns requested
		String actualValue;
		String columnIndex;
		for(String column: expectedColumnValues.keySet()){
			if(columnsWithIndex.containsKey(column)){
				columnIndex=columnsWithIndex.get(column); //getting the index of the given column in the MAM table 
				actualValue=driver.findElementByXPath("//table/tbody/tr[*]/td[*]/span[text()='"+scope+"']//ancestor::tr/td["+columnIndex+"]/span").getText(); //getting the value of the given column
				actualColumnValues.put(column, actualValue);
			}else{
				UAS.resultFlag="#Failed!#";
				result.append("#Failed!# - "+column+" doesnot exists in the table");
			}
		}
		
		try{
	        for (String columnName : expectedColumnValues.keySet())
	        {
	            if((actualColumnValues.get(columnName).replaceAll("\\s+","").trim().equalsIgnoreCase("")||actualColumnValues.get(columnName).replaceAll("\\s+","").trim().equalsIgnoreCase("-"))&&(expectedColumnValues.get(columnName).replaceAll("\\s+","").trim().equalsIgnoreCase("0"))){
	            	System.out.println("Pass - "+columnName+" values matched");
	                result.append("Pass - "+columnName+" values matched; ");
	            }else if ((actualColumnValues.get(columnName).replaceAll("\\s+","").trim()).equalsIgnoreCase(expectedColumnValues.get(columnName).replaceAll("\\s+","").trim())) {
	            	System.out.println("Pass - "+columnName+" values matched");
	                result.append("Pass - "+columnName+" values matched; ");
	            }else{
	            	if(columnName.equalsIgnoreCase("lastscanstaffID")&&(expectedColumnValues.get("lastscopelocation").equalsIgnoreCase("Out of Facility")||expectedColumnValues.get("lastscopelocation").equalsIgnoreCase("Returned to Facility")||expectedColumnValues.get("lastscopelocation").equalsIgnoreCase("Bioburden"))){
	        			UAS.resultFlag="#Failed!#";
		            	//System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+". Bug 9827 opened.");
		            	System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+".");
		            	//result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+". Bug 9827 opened. ");
		            	result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+".");
	            	}else if(columnName.equalsIgnoreCase("lastscanstaffID")&&(expectedColumnValues.get("lastscopelocation").contains("Procedure Room"))){	
	        			UAS.resultFlag="#Failed!#";
		            	System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+". Bug 12603 opened.");
		            	result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+". Bug 12603 opened. ");
		            	
		            	/*System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+".");
		            	result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+".");*/
		            	
	            	}else if(columnName.equalsIgnoreCase("reprocessingcount")&&(expectedColumnValues.get("lastscopelocation").contains("Reprocessor"))){	
	        			UAS.resultFlag="#Failed!#";
		            	//System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+". Bug 9833 opened.");
		            	//result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+". Bug 9833 opened. ");
		            	
		            	System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+".");
		            	result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+".");

	            	}else if (columnName.equalsIgnoreCase("timeatlocation(dd:hh:mm)")){
            			String expColVal=expectedColumnValues.get(columnName);
    					String[] arrExpColVal=expColVal.trim().split(":");
    					String actColVal=actualColumnValues.get(columnName);
    					String[] arrActColVal=actColVal.trim().split(":");

    					if (arrExpColVal[0].equalsIgnoreCase(arrActColVal[0]) && arrExpColVal[1].equalsIgnoreCase(arrActColVal[1])){

    						if (Integer.parseInt(arrExpColVal[2])-1==Integer.parseInt(arrActColVal[2]) ||Integer.parseInt(arrExpColVal[2])+1==Integer.parseInt(arrActColVal[2])){
    							System.out.println("Passed - difference of less than 1 minute");
    							System.out.println("Passed - "+columnName+":"+actualColumnValues.get(columnName)+" actual value matched with difference of less than 1 minute with "+expectedColumnValues.get(columnName)+" estimated value.");
    	            			result.append("Passed - "+columnName+"="+actualColumnValues.get(columnName)+" actual value matched with difference of less than 1 minute with "+expectedColumnValues.get(columnName)+" estimated value.");
    						}else{
        					Unifia_Admin_Selenium.resultFlag="#FAILED!#";
       	            		System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName));
       			            result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+"; ");
    						}
    					}
            		}else{
	        			UAS.resultFlag="#Failed!#";
		            	System.out.println("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName));
		            	result.append("#Failed!# -"+columnName+" was expected to be: "+expectedColumnValues.get(columnName)+"; However it was "+actualColumnValues.get(columnName)+"; ");

	            	}
	            }
	        } 
	    } catch (NullPointerException np) {
	    	System.out.println("NullPointerException: " + np.getMessage());
	    }
		
		return result;
	}
	
	public static String checkMAMFilters(){
		StringBuffer result= new StringBuffer();
		int index =0;
		Map<String,String> columnsWithIndex=MAM_Actions.getMAMColumns();//this method will return the column names with their index in the table
		for(Map.Entry column : columnsWithIndex.entrySet()){
			index = Integer.parseInt(columnsWithIndex.get(column.getKey().toString().toLowerCase().replaceAll("\\s+",""))); //getting the column index of Last Scan Staff ID
			if(index<=7){
				if(driver.findElementsByXPath("//*[@id='mamGrid']/div[1]/table/tr[2]/td["+index+"]/input").size()>0){
					result.append("Pass - "+column.getKey()+" filter is present. ");
				}else{
					result.append("#Failed!# - "+column.getKey()+" filter is not present. ");
				}	
			}
		}
		if(result.toString().contains("#Failed!#")){
			UAS.resultFlag="#Failed!#";
		}
		return result.toString();
	}
	
	public static String areAllMAMFiltersBlank(){
		StringBuffer result=new StringBuffer();
		int index =0;
		Map<String,String> columnsWithIndex=MAM_Actions.getMAMColumns();//this method will return the column names with their index in the table
		for(Map.Entry column : columnsWithIndex.entrySet()){
			index = Integer.parseInt(columnsWithIndex.get(column.getKey().toString().toLowerCase().replaceAll("\\s+",""))); //getting the column index of Last Scan Staff ID
			if(index<=7){
				String filterText=driver.findElementByXPath("//*[@id='mamGrid']/div[1]/table/tr[2]/td["+index+"]/input").getText();
				if(!filterText.equals("")){
					result.append("#Failed!# - "+column.getKey().toString()+" filter value is suppossed to be blank however, it was "+filterText+". ");
				}		
			}
		}
		if(!result.toString().contains("#Failed!#")){
			return "Pass";
		}else{
			return result.toString();
		}
		
	}

}
