package HistoricalDataConversion;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HistoryLogic {

	public int[] MonthsOfDataCalc(int Exams){
		
		int Result[]= new int[2];
		int Months=0;
		int Days=0;
		double calc=0;
		
		calc= (double) Exams/800;
		Months=(int) Math.floor(calc);
		calc= (double) Exams-(800*Months);
		Days= (int) Math.floor(calc);
		
		Result[0]=Months;
		Result[1]=Days;
		
		return Result;
		
	}
	
	public int[] YearOfDataCalc(int Exams){
		int[] Result= new int[2];
		int Yrs;
		int RemainingExms;
		double calc;
		
		calc= (double) Exams/40000;
		Yrs=(int) Math.floor(calc);
		RemainingExms= Exams-(Yrs*40000);
		
		Result[0]=Yrs;
		Result[1]=RemainingExms;
		
		return Result;
	}
	
	public static String ReturnStartDate(int Yrs, int Mnths, int Days){
		Date Result;
		String ResultDate;
		String DOW;
		//Calculate the milliseconds to subtract from current date
		// Y*365+M*30+D = The days (X)
		// x*24=Y the hrs
		// Y*60=A the minutes
		// A*60=B the seconds
		//B*1000= C the milliseconds
		long X; //The days
		long Y; //The hrs
		long A; // The minutes
		long B; //The Seconds
		long C; // The milliseconds
		
		X=(Yrs*365)+(Mnths*30)+Days;
		Y=X*24;
		A=Y*60;
		B=A*60;
		C=B*1000;
		Calendar date = Calendar.getInstance();
		long t= date.getTimeInMillis();
		Date StartDate=new Date(t-C);
		Result=StartDate;
		//System.out.println("Calculation X=  "+X);
		//System.out.println("Calculation Y=  "+Y);
		//System.out.println("Calculation A=  "+A);
		//System.out.println("Calculation B=  "+B);
		//System.out.println("Calculation C=  "+C);
		//System.out.println(StartDate);
		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		newDateFormat.applyPattern("EEEE");
		DOW=newDateFormat.format(StartDate);
		int trigger =0;
		while (trigger<1){
			//System.out.println("DOW:  "+DOW);
			if(DOW.equalsIgnoreCase("Saturday")){
				date = Calendar.getInstance();
				t= date.getTimeInMillis();
				StartDate=new Date(t-(C+((48*60)*60)*1000));
				Result=StartDate;
				DOW=newDateFormat.format(Result);
			}else if(DOW.equalsIgnoreCase("Sunday")){
				date = Calendar.getInstance();
				t= date.getTimeInMillis();
				StartDate=new Date(t-(C+((24*60)*60)*1000));
				Result=StartDate;
				DOW=newDateFormat.format(Result);
			}else{
				trigger++;
			}
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ResultDate=df.format(Result);
		return ResultDate;
		
		 
		
	}
	
	public static int RandomNumber(int minimum, int maximum){
		int Result =0;
		Random rn = new Random();
		int range = maximum - minimum + 1;
		Result=  rn.nextInt(range) + minimum;
		return Result;
	}
	
	public static long CalcTimeinMilli(String UOM, int increaseByMin){
		long Result;
		int A = increaseByMin;
		
		if(UOM.equalsIgnoreCase("Day")){
			Result=(((A*24)*60)*60)*1000;
		}else if(UOM.equalsIgnoreCase("Hr")){
			Result=((A*60)*60)*1000;
		}else if(UOM.equalsIgnoreCase("Min")){
			Result=(A*60)*1000;
		}else{
			//Assumes seconds
			Result=A*1000;
		}
		
		return Result;
	}
	
	public static String HistDateGen(String StartDate, String ScanObj, String ScanType, String LocationType) throws ParseException{
		//This will return a date to be inserted into scope history
		String ReturnDate= null;
		int HangtimeVar=0;
		int increaseByMin=0;
		long millisecNum = 10000;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date CalcDate=df.parse(StartDate);
		
		if(LocationType.equals("Procedure Room")){
			if(ScanObj.equals("Scope")){
				if(ScanType.equals("Pre-Procedure")){
					HangtimeVar=RandomNumber(1,15);
					if(HangtimeVar==15){
						//Set time between cycles between 6 and 8 days
						increaseByMin=RandomNumber(6,8);
						millisecNum=CalcTimeinMilli("Day",increaseByMin);
					}else{
						//Set time between cycle between 2 to 6 hrs
						increaseByMin=RandomNumber(2,6);
						millisecNum=CalcTimeinMilli("Hr",increaseByMin);
					}
				}else if(ScanType.equals("Pre-Clean Complete")){
					// SEt wait time between 2 and 3 minutes
					increaseByMin=RandomNumber(2,3);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else{
					//ReturnDate=null;
					//Do Nothing
				}
			}else if(ScanObj.equals("Staff")){
				if(ScanType.equals("Physician")){
					// set time between scans between 1 sec and 5 minutes
					increaseByMin=RandomNumber(0,5);
					if(increaseByMin==0){
						millisecNum=10000;
					}else{
						millisecNum=CalcTimeinMilli("min",increaseByMin);
					}
				}else{
					// set time between scans between 1 sec and 1 minute
					increaseByMin=RandomNumber(0,1);
					if(increaseByMin==0){
						millisecNum=10000;
					}else{
						millisecNum=CalcTimeinMilli("min",increaseByMin);
					}
				}
			}else{
				if(ScanType.equals("Procedure Start")){
					//set wait time between 3 min and 10 min
					increaseByMin=RandomNumber(3,10);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else if(ScanType.equals("Procedure End")){
					// set wait time between 20 and 65 minutes
					increaseByMin=RandomNumber(20,65);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else if(ScanType.equals("Room Status Change")){
					// Set wait time between 5 and 10 minutes
					increaseByMin=RandomNumber(5,10);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else{
					//ReturnDate=null;
					//Do Nothing
				}
			}
		}else if(LocationType.equals("Soiled Area")){
			if(ScanObj.equals("Scope")){
				//Set wait time between 5 minutes and 75 minutes
				increaseByMin=RandomNumber(5,75);
				millisecNum=CalcTimeinMilli("min",increaseByMin);
			}if (ScanObj.equals("Staff")){
				//Set Wait time between 1 sec and 2 min
				increaseByMin=RandomNumber(0,2);
				if(increaseByMin==0){
					millisecNum=10000;
				}else{
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}
			}else{
				if(ScanType.equals("Manual Clean Start")){
					//set wait time between 1 min and 5 min
					increaseByMin=RandomNumber(1,5);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else if(ScanType.equals("Manual Clean End")){
					// Set wait time between 6 min and 15 min
					increaseByMin=RandomNumber(6,15);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else{
					//ReturnDate=null;
					//Do Nothing
				}
			}
		}else if(LocationType.equals("Reprocessing Area")){
			if(ScanObj.equals("Scope")){
				if(ScanType.equals("Reprocessing In")){
				//Set wait time between 5 minutes and 75 minutes
				increaseByMin=RandomNumber(5,75);
				millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else if(ScanType.equals("Reprocessing Out")){
					//Set Wait time between 30 minutes and 75 minutes
					increaseByMin=RandomNumber(30,75);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else if(ScanType.equals("Reprocessing Start")){
					increaseByMin=RandomNumber(5,75);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				
				}else if(ScanType.equals("Reprocessing End")){
					increaseByMin=RandomNumber(18,30);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
					
				}else if(ScanType.equals("Reprocessing Status")){
					increaseByMin=RandomNumber(1,2);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				
				}else if(ScanType.equals("Disinfectant Temparature")){
					increaseByMin=RandomNumber(1,2);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				
				}else if(ScanType.equals("Oer Cycle")){
					increaseByMin=RandomNumber(1,2);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				
				}else{
					//ReturnDate=null;
					//Do Nothing
				}
			}else if (ScanObj.equals("Staff")){
				//Set Wait time between 1 sec and 2 min
				increaseByMin=RandomNumber(0,2);
				if(increaseByMin==0){
					millisecNum=10000;
				}else{
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}
			}else{
				if(ScanType.equals("Reprocessing Reason")){
					//Set Wait time between 1 sec and 2 min
					increaseByMin=RandomNumber(0,2);
					if(increaseByMin==0){
						millisecNum=10000;
					}else{
						millisecNum=CalcTimeinMilli("min",increaseByMin);
					}
				}else if(ScanType.equals("MRC Test")){
					//Set Wait time between 5 and 10 min
					millisecNum=CalcTimeinMilli("min",increaseByMin);
					increaseByMin=RandomNumber(5,10);
				}else if(ScanType.equals("Reprocessing Status")){
					increaseByMin=RandomNumber(1,2);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}else if(ScanType.equals("Disinfectant Temparature")){
					increaseByMin=RandomNumber(1,2);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				
				}else if(ScanType.equals("Oer Cycle")){
					increaseByMin=RandomNumber(1,2);
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				
				}else{
					//ReturnDate=null;
					//Do Nothing
				}
			}
		}else if(LocationType.equals("Waiting Room")){
			//There is a specific function for this location Type
			//Keep the date at 1900-01-01 00:00:00 in this function
			//("yyyy-MM-dd HH:mm:ss")
			ReturnDate="1900-01-01 00:00:00";
			
		}else if(LocationType.equals("Bioburden Testing")){
			if(ScanObj.equals("Scope")){
				increaseByMin=RandomNumber(10,20);
				millisecNum=CalcTimeinMilli("min",increaseByMin);
			}else{
				increaseByMin=RandomNumber(1,5);
				millisecNum=CalcTimeinMilli("min",increaseByMin);
			}
		}else if(LocationType.equals("Culturing")){
			if(ScanObj.equals("Scope")){
				increaseByMin=RandomNumber(10,20);
				millisecNum=CalcTimeinMilli("min",increaseByMin);
			}else{
				increaseByMin=RandomNumber(0,2);
				if(increaseByMin==0){
					millisecNum=10000;
				}else{
					millisecNum=CalcTimeinMilli("min",increaseByMin);
				}
			}
		}else if(LocationType.equals("Scope Storage Area")){
			///There is a specific function for this location Type
			//Keep the date at 1900-01-01 00:00:00 in this function
			//("yyyy-MM-dd HH:mm:ss")
			ReturnDate="1900-01-01 00:00:00";
			
		}else if(LocationType.equals("Administration")){
			///There is a specific function for this location Type
			//Keep the date at 1900-01-01 00:00:00 in this function
			//("yyyy-MM-dd HH:mm:ss")
			ReturnDate="1900-01-01 00:00:00";
		}else{
			//ReturnDate=null;
			//Do Nothing
			ReturnDate="1900-01-01 00:00:00";
		}
		
		if(millisecNum==0){
			//ReturnDate=StartDate;
			//System.out.println("Nothin Happened? Return 10000 milliseconds");
			long t= CalcDate.getTime();
			Date UpdateDate;
			UpdateDate=new Date(t+millisecNum);
			//System.out.println("UpdateDate="+t+"+"+millisecNum);
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ReturnDate=df.format(UpdateDate);
		}else{
			long t= CalcDate.getTime();
			Date UpdateDate;
			UpdateDate=new Date(t+millisecNum);
			//System.out.println("UpdateDate="+t+"+"+millisecNum);
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ReturnDate=df.format(UpdateDate);
			
		}
		return ReturnDate;
	}
	
	public static String ValidateDOWandTime(String SomeDate, String AllowWKND) throws ParseException{
		String ResultDate = null;
		String DOW;
		String HOD;//hour of day
		Date iDate;
		Date cDate;
		long t;
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int trigger =0;
		int trigger2 =0;
		while (trigger<1){
			iDate=df.parse(SomeDate);
			newDateFormat.applyPattern("EEEE");
			DOW=newDateFormat.format(iDate);
			if(DOW.equalsIgnoreCase("Saturday")){
				t= iDate.getTime();
				cDate=new Date(t+((48*60)*60)*1000);
				SomeDate=df.format(cDate);
				
			}else if(DOW.equalsIgnoreCase("Sunday")){
				t= iDate.getTime();
				cDate=new Date(t+((24*60)*60)*1000);
				SomeDate=df.format(cDate);
				
			}else{
				SomeDate=SomeDate;
				trigger++;
			}
			//System.out.println("Trigger 1 done");
		}
		int CntLp=0;
		while (trigger2<1||CntLp==10){
			//System.out.println("CntLp: "+CntLp);
			iDate=df.parse(SomeDate);
			newDateFormat.applyPattern("H");
			HOD=newDateFormat.format(iDate);
			//System.out.println("HOD:  "+HOD);
				String[] V_HOD= new String[10];
				V_HOD[0]="7";
				V_HOD[1]="8";
				V_HOD[2]="9";
				V_HOD[3]="10";
				V_HOD[4]="11";
				V_HOD[5]="12";
				V_HOD[6]="13";
				V_HOD[7]="14";
				V_HOD[8]="15";
				V_HOD[9]="16";
			if(Arrays.asList(V_HOD).contains(HOD)){
				ResultDate=SomeDate;
				trigger2++;
				CntLp++;
			}else{
				t= iDate.getTime();
				cDate=new Date(t+((1*60)*60)*1000);
				SomeDate=df.format(cDate);
				CntLp++;
			}
		}
		return ResultDate;
		
	}
	
	
	
}
