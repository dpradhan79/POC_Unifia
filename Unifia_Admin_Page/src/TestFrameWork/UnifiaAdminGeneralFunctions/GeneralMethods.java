package TestFrameWork.UnifiaAdminGeneralFunctions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.support.ui.Select;

import TestFrameWork.Unifia_Admin_Selenium;

public class GeneralMethods  extends Unifia_Admin_Selenium {
	public static TestFrameWork.Unifia_Admin_Selenium UAS; 
	
	public static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	public static Calendar cal = Calendar.getInstance();
	public static String timeZone="UTC";
	
	public static String get_TodayDate() {
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date currentDate = new Date();
		String today = df.format(currentDate);
		return today;
	}

	public static String get_YesterdayDate() {
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date DayMinus1 = cal.getTime();
		String yesterday = df.format(DayMinus1);
		return yesterday;
	}
	
	public static String[] get_last7DaysDate(){
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date currentDate = new Date();
		String today = df.format(currentDate);
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, -6);
		Date DayMinus6 = cal.getTime();
		String dayMinus6 = df.format(DayMinus6);
		String dates[]={dayMinus6,today};
		return dates;
	}
	
	public static String[] get_last30DaysDate(){
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date currentDate = new Date();
		String today = df.format(currentDate);
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, -29);
		Date DayMinus29 = cal.getTime();
		String dayMinus29 = df.format(DayMinus29);
		String dates[]={dayMinus29,today};
		return dates;
	}
	
	public static String[] get_ThisMonthDates(){
		Date begining, ending;
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date currentDate = new Date();
		
		Calendar calStart = Calendar.getInstance();
		calStart.setTime(currentDate);
		calStart.set(Calendar.DAY_OF_MONTH,calStart.getActualMinimum(Calendar.DAY_OF_MONTH));
		begining = calStart.getTime();
		String start=df.format(begining);

		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(currentDate);
		calEnd.set(Calendar.DAY_OF_MONTH,calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
		ending = calEnd.getTime();
		String end=df.format(ending);
		
		String dates[]={start,end};
		return dates;
	}
	
	public static String[] get_LastMonthDates(){
		Date begining, ending;
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date currentDate = new Date();
		
		Calendar calStart = Calendar.getInstance();
		calStart.setTime(currentDate);
		calStart.add(Calendar.MONTH, -1);
		calStart.set(Calendar.DAY_OF_MONTH,calStart.getActualMinimum(Calendar.DAY_OF_MONTH));
		begining = calStart.getTime();
		String start=df.format(begining);

		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(currentDate);
		calEnd.add(Calendar.MONTH, -1);
		calEnd.set(Calendar.DAY_OF_MONTH,calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
		ending = calEnd.getTime();
		String end=df.format(ending);
		
		String dates[]={start,end};
		return dates;
	}
	  
	public static String get_CustomDate(int DateDiff) {
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date currentDate = new Date();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, -DateDiff);
		Date custom = cal.getTime();
		String customdate = df.format(custom);
		return customdate;
	}
	
	public static String Verify_EM_DateRange(List<String> columnValues, Date StartDate, Date EndDate, DateFormat df) throws ParseException{
		String result="";
		String start=df.format(StartDate);
		String end=df.format(EndDate);
		for(String columnValue: columnValues){
			String date=columnValue.split(" ")[0];
			Date dateValue=df.parse(date);
			if(StartDate.equals(EndDate)){
				if(!date.equals(start)){
					UAS.resultFlag="#Failed!#";
					result+="#Failed!#- expected date was "+start+" but it was "+date;
				}
			}else if((dateValue.before(StartDate)||dateValue.after(EndDate))){
				UAS.resultFlag="#Failed!#";
				result+="#Failed!#- "+date+" is not in the range of "+start+" and "+end+"; ";
			}
		}
		if(!result.contains("#Failed!#")){
			result="Pass - The dates displayed are in the range of "+start+" and "+end;
		}
		return result;
	}
	
	public static boolean verifyIsMultiFacilityList(String listBoxXpath){
		Select droplist = new Select(Unifia_Admin_Selenium.driver.findElementByXPath(listBoxXpath));   
		Integer eleCount=droplist.getOptions().size();
		System.out.println("eleCount="+eleCount);
		if (eleCount==1){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isMultiFacListFound(String listBoxXpath){
		int size=UAS.driver.findElementsByXPath(listBoxXpath).size();
		System.out.println("List boxsize="+size);
		if (size>0){
			return true;
		}else{
			return false;
		}
	}
	
}
