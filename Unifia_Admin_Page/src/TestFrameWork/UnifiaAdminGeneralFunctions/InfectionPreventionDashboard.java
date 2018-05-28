package TestFrameWork.UnifiaAdminGeneralFunctions;

public class InfectionPreventionDashboard {
	
	//Scopes Awaiting Manual Cleaning or Awaiting Reprocessing
	public static String samcOrARHeader="//div[@id='awaitingPanelHeading']/span";
	public static String samcOrARCount="//*[@id='scopesAwaiting']";
	public static String samcOrARColor="//*[@id='awaitingPanelHeading']";
	public static String samcOrARTableContent="//*[@id='awaitingTableContent']";
	public static String amcScopeState="3";
	public static String arScopeState="2";
	
	//Scopes Reprocessed Today
	public static String srtHeader="//*[@id='srtPanel']/div[1]/span";
	public static String srtCount="//*[@id='scopesReprocessed']";
	public static String srtColor="//*[@id='srtPanel']/div[1]";
	public static String srtTableContent="//*[@id='srtTableContent']";
	public static String srtScopeState="5";
	public static String srtTimeProcessed="//*[@id='srtTableContent']/div[2]/table/tbody/tr[1]/td[5]/div/span";

	//Scopes Approaching Hang Time
	public static String sahtHeader="//*[@id='sahtPanel']/div[1]/span";
	public static String sahtCount="//*[@id='scopesApproaching']";
	public static String sahtColor="//*[@id='sahtPanel']/div[1]";
	public static String sahtTableContent="//*[@id='sahtTableContent']";
	
	//Scopes Exceeding Hang Time
	public static String sehtHeader="//*[@id='sehtPanel']/div[1]/span";
	public static String sehtCount="//*[@id='scopesExceeding']";
	public static String sehtColor="//*[@id='sehtPanel']/div[1]";
	public static String sehtTableContent="//*[@id='sehtTableContent']";
	
	//Data
	//Scopes Awaiting Manual Cleaning or Reprocessing
	public static String expSAMCOrARHeader="Scopes Awaiting Manual Cleaning or Reprocessing";
	public static String expSAMCOrARCount="5";
	public static String expSAMCOrARColor="rgba(73, 176, 168, 1)";
	
	//Scopes Reprocessed Today
	public static String expSRTHeader="Scopes Reprocessed Today";
	public static String expSRTCount="4";
	public static String expSRTColor="rgba(76, 157, 193, 1)";
	
	//Scopes Approaching Hang Time
	public static String expSAHTHeader="Scopes Approaching Hang Time";
	public static String expSAHTCount="2";
	public static String expSAHTColor="rgba(239, 113, 65, 1)";
	
	//Scopes Exceeding Hang Time
	public static String expSEHTHeader="Scopes Exceeding Hang Time";
	public static String expSEHTCount="2";
	public static String expSEHTColor="rgba(209, 51, 56, 1)";
	
}
