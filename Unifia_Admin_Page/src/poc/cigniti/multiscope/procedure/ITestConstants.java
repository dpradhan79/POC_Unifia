package poc.cigniti.multiscope.procedure;

public interface ITestConstants {
	
	int implicitTimeOut = 10;
	int pageLoadTimeOut = 10;
	String strSQLForWorkFlowValidation = "SELECT [ItemHistoryID_PK]\r\n" + 
			"      ,[ScanItemID_FK]\r\n" + 
			"      ,[ScanItemTypeID_FK]\r\n" + 
			"      ,[DataSourceID]\r\n" + 
			"      ,[ReceivedDateTime]\r\n" + 				
			"      ,[IsStored]\r\n" + 
			"      ,[ProcessedDateTime]\r\n" + 
			"      ,[DataSourceTypeID_FK]\r\n" + 
			"      ,i.[LocationID_FK]\r\n" + 
			"      ,[AssociationID_FK]\r\n" + 
			"      ,[CycleEventID_FK]\r\n" + 
			"  ,st.Name\r\n" + 
			"  ,ls.LocationStateID_FK\r\n" + 
			"  FROM [Unifia].[dbo].[ItemHistory] i\r\n" + 
			"  Join Location l on i.LocationID_FK=l.LocationID_PK \r\n" + 
			"  join ScanItemType st on i.scanItemTypeID_FK=st.ScanItemTypeID_PK\r\n" + 
			"  join LocationStatus ls on ls.LocationID_FK=i.LocationID_FK\r\n" + 
			"  Where l.Name='%s' and st.Name = '%s' and ls.LocationStateID_FK = '%s' and convert(varchar(10),i.LastUpdatedDateTime,101) = convert(varchar(10),getdate(),101)\r\n" + 
			"";
	
	String strSQLForScopeAndStaffValidation = "SELECT [ItemHistoryID_PK]\r\n" + 
			"      ,[ScanItemID_FK]\r\n" + 
			"      ,[ScanItemTypeID_FK]\r\n" + 
			"      ,[DataSourceID]\r\n" + 
			"      ,[ReceivedDateTime]\r\n" + 		
			"      ,[IsStored]\r\n" + 
			"      ,[ProcessedDateTime]\r\n" + 
			"      ,i.[DataSourceTypeID_FK]\r\n" + 
			"      ,i.[LocationID_FK]\r\n" + 
			"      ,[AssociationID_FK]\r\n" + 
			"      ,a.[AssociationID_PK]\r\n" + 
			"      ,[CycleEventID_FK]\r\n" + 
			"  ,st.Name\r\n" + 
			"  ,ls.LocationStateID_FK  \r\n" + 
			"  FROM [Unifia].[dbo].[ItemHistory] i\r\n" + 
			"  Join Location l on i.LocationID_FK=l.LocationID_PK \r\n" + 
			"  join ScanItemType st on i.scanItemTypeID_FK=st.ScanItemTypeID_PK\r\n" + 
			"  join LocationStatus ls on ls.LocationID_FK=i.LocationID_FK\r\n" + 
			"  join Association a on a.AssociationID_PK = i.AssociationID_FK\r\n" + 
			"  Where l.Name='%s' and st.Name = '%s' and i.CycleEventID_FK = '%s' and convert(varchar(10),i.LastUpdatedDateTime,101) = convert(varchar(10),getdate(),101)\r\n" + 
			"";
}
