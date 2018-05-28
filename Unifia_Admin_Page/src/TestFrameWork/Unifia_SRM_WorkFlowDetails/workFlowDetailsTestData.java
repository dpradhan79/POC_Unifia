package TestFrameWork.Unifia_SRM_WorkFlowDetails;

import java.util.Date;

public class workFlowDetailsTestData {
	
	public static String scopeModel="GIF-H190";
	public static String scopeName="Scope1";
	public static String scopeSerNum="1122334";
	
	public static String scopeSerNum2="2233445";
	public static String scopeName2="Scope2";
	public static String scopeModel2="GIF-HQ190";
	
	public static String procRoom = "Procedure Room 1";
	public static String scopeInStaffPR="T01";
	public static String patient ="MRN00001";
	public static String physStaff="MD01";
	public static String preCleanStatus="Yes";
	public static String preStaff="T01";

	public static String soiledArea="Sink 1";
	public static String sAStaff="T01";
	public static String sALTStatus="Pass";
	public static String sAMCStartDate;
	public static String sAMCEndDate;

	public static String RepArea="Reprocessor 1";
	public static String reasonForRepro="Used in Procedure";
	public static String scopeInStaffRep="T01";
	public static String scopeOutstaffRep="T01";
	public static String scopeOutDateRep;

	public static String bioRes="Pass";
	public static String bioSanRes="Blue";
	public static String bioStaff="T02";
	public static String bioKeyStatus="1";
	
	public static String  cultResult="Pass";
	public static String  comment="New Workflow";
	public static String expErrorMsg="Please select a Scope before continuing.";
	public static String expErrorMsgMandField="Validation failed";
	
	public static String physDropDown="//*[@id='workflowform']/div/div[1]/div[5]/div/div[2]/div/div[1]/div[5]/div[2]/div/ul";
}
