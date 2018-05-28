package TestFrameWork;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.*;

public class TestHelper {

	public static String ipAddress;
	public static String hostname;
	
	
	public static String url =Unifia_Admin_Selenium.TestDataDBURL;
	//public static String url = "jdbc:sqlserver://10.170.93.135\\sqlexpress;databaseName=unifia_admin_TestData";
//	public static String url = "jdbc:sqlserver://10.170.93.180:1433;databaseName=unifia_admin_TestData";
	//public static String urlComplex = "jdbc:sqlserver://10.170.93.180:1433;databaseName=unifia_admin_TestData";

   /* public static String user ="unifia";
    public static String pass = "0lympu$123";*/
    public static String user =Unifia_Admin_Selenium.user;
    public static String pass = Unifia_Admin_Selenium.TestDataPass;
    //public static String ConnType;
    public static String ConnString;
	
	public static void StepWriter(String Vertex, String Description, String Expected){
		Connection conn= null;
		try{
			conn= DriverManager.getConnection(url, user, pass);		
			Statement update = conn.createStatement();
			update.executeUpdate("Insert Into dbo.manual_step_log (StepMethod, Description, ExpectedResult) values ('"+Vertex+"', '"+Description+"','"+Expected+"')");
						
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
	}
	
public static void StepWriter1(String Vertex, String Description, String Expected, String Result){
		
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
		
		ipAddress = addr.getHostAddress();
		String hostname = addr.getHostName();
	
		
		try{
			Connection conn = DriverManager.getConnection(FrameworkDBConnection());		
			Statement update = conn.createStatement();
			update.executeUpdate("Insert Into TestStepLog (MethodName, Description, Expected, Result, LocalHost, ipaddress) values ('"+Vertex+"', '"+Description+"','"+Expected+"','"+Result+"','"+hostname+"','"+ipAddress+"')");
						
			update.close();
			conn.close();
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());	
		}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public static void StepWriter1Complex(String Vertex, String Description, String Expected, String Result){
	
	InetAddress addr;
	try {
		addr = InetAddress.getLocalHost();
	
	ipAddress = addr.getHostAddress();
	String hostname = addr.getHostName();

	
	try{
		Connection conn = DriverManager.getConnection(FrameworkDBConnection());		
		Statement update = conn.createStatement();
		update.executeUpdate("Insert Into teststeplogComplex (MethodName, Description, Expected, Result, LocalHost, ipaddress) values ('"+Vertex+"', '"+Description+"','"+Expected+"','"+Result+"','"+hostname+"','"+ipAddress+"')");
					
		update.close();
		conn.close();
	}
	catch (SQLException ex){
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());	
	}
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	public static String LocalMachineIP() throws UnknownHostException{
		InetAddress addr = InetAddress.getLocalHost();
	     
        //Getting IPAddress of localhost - getHostAddress return IP Address
        // in textual format
       ipAddress = addr.getHostAddress();
     
        //System.out.println("IP address of localhost from Java Program: " + ipAddress);
     
        //Hostname
        String hostname = addr.getHostName();
        //System.out.println("Name of hostname : " + hostname);
        return hostname;

	}
	
	public static String FrameworkDBConnection (){
		String ConnType="Public";
		if(ConnType.equalsIgnoreCase("Local")){
			ConnString="jdbc:mysql://127.0.0.1/"+Unifia_Admin_Selenium.DBName+"?user=root&password=P@$$w0rd";
		}else if(ConnType.equalsIgnoreCase("Public")){
			ConnString=url+";user="+user+";password="+pass;
		}else{
			ConnString="#Failed!#:  bad connection type, the value value must be Public or Local";
			System.out.println(ConnString);
		}
		
		return ConnString;
	}
	
	//[RK]-07/Jul/16
public static void WriteToTextFile(String FileName, String TestRes){
	 try{
		  // Create file 
		  FileWriter fstream = new FileWriter(FileName+".txt");
		  BufferedWriter out = new BufferedWriter(fstream);
		  out.write(TestRes);
		  //Close the output stream
		  out.close();
		  }catch (Exception e){//Catch exception if any
			System.err.println("#Failed!#: " + e.getMessage());
		  }
}
	
	
}
