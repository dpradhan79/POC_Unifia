package poc.cigniti.testreport;

import org.apache.log4j.Logger;
import poc.cigniti.testreport.ExtentReporter.ExtentTestVisibilityMode;
import poc.cigniti.multiscope.procedure.GenericUtil;

/**
 * 
 * @author E001518  - Debasish Pradhan (Architect)
 *
 */

public class ReportFactory {
	
	private static IReporter testReport = null;	
	private static final Logger LOG = Logger.getLogger(ReportFactory.class);
	
	public enum ReportType
	{
		CignitiHtml,
		ExtentHtml
	}
	
	private ReportFactory()
	{
		
	}
	
	public synchronized static IReporter getInstance(ReportType reportType, ExtentTestVisibilityMode extentTestVisibilityMode) throws Exception
	{
		if(ReportFactory.testReport == null)
		{
			switch(reportType)	
			{
				case ExtentHtml :
					
					String htmlReportName = "TestReport.html";
					String screenShotLocation = "ErrorScreenshots";		
					String strBoolAppendExisting = "false";
					String strIsCignitiLogoRequired = "false";
					String extentConfigFile = "extentConfig.xml";	
					boolean boolAppendExisting = false;
					boolean boolIsCignitiLogoRequired = false;
					if(strBoolAppendExisting != null && strBoolAppendExisting.equalsIgnoreCase("true"))
					{
						boolAppendExisting = true;
					}
					
					if(strIsCignitiLogoRequired != null && strIsCignitiLogoRequired.equalsIgnoreCase("true"))
					{
						boolIsCignitiLogoRequired = true;
					}
					
					GenericUtil.makeDir(screenShotLocation);
					String filePath = String.format("%s", htmlReportName);
					ReportFactory.testReport = new ExtentReporter(filePath, extentConfigFile, boolAppendExisting, boolIsCignitiLogoRequired, extentTestVisibilityMode);
										
					break;				
				
				default:
					throw new Exception("Html Report Other Than Extent Is Not Currently Supported...");
					
			}
			
		}
		return ReportFactory.testReport;
			
	}
	
	
}
