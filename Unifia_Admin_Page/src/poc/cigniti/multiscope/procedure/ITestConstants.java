package poc.cigniti.multiscope.procedure;

public interface ITestConstants {
	
	int implicitTimeOut = 20;
	int pageLoadTimeOut = 60;
	String strSQLForWorkFlowValidation = "SELECT top 1 [ItemHistoryID_PK]\r\n" + 
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
			"  order by i.LastUpdatedDateTime DESC ";
	
	String strSQLForScopeAndStaffValidation = "SELECT [ItemHistoryID_PK] \r\n" + 
			",[ScanItemID_FK] \r\n" + 
			",[ScanItemTypeID_FK] \r\n" + 
			",[DataSourceID] \r\n" + 
			",[ReceivedDateTime] \r\n" + 
			",[IsStored] \r\n" + 
			",[ProcessedDateTime] \r\n" + 
			",i.[DataSourceTypeID_FK] \r\n" + 
			",i.[LocationID_FK] \r\n" + 
			",i.[AssociationID_FK] \r\n" + 
			",a.[AssociationID_PK] \r\n" + 
			",[CycleEventID_FK] \r\n" + 
			",st.Name \r\n" + 
			",ls.LocationStateID_FK \r\n" + 
			",sp.ScopeStateID_FK\r\n" + 
			"FROM [Unifia].[dbo].[ItemHistory] i \r\n" + 
			"Join Location l on i.LocationID_FK=l.LocationID_PK \r\n" + 
			"join ScanItemType st on i.scanItemTypeID_FK=st.ScanItemTypeID_PK \r\n" + 
			"join LocationStatus ls on ls.LocationID_FK=i.LocationID_FK \r\n" + 
			"join Association a on a.AssociationID_PK = i.AssociationID_FK \r\n" + 
			"join ScopeStatus sp on sp.LocationID_FK=i.LocationID_FK\r\n" + 
			"Where l.Name='Procedure Room 1' and st.Name = 'Scope' and i.CycleEventID_FK = '3' and convert(varchar(10),i.LastUpdatedDateTime,101) = convert(varchar(10),getdate(),101)\r\n" +
			"order by i.LastUpdatedDateTime DESC";
	
	String strSqlForMultipleRowsCreatedInItemHistoryWithCycleEventUpdated = "SELECT [ItemHistoryID_PK]\r\n" + 
			",[ScanItemID_FK]\r\n" + 
			",[ScanItemTypeID_FK]\r\n" + 
			",[DataSourceID]\r\n" + 
			",[ReceivedDateTime]\r\n" + 
			",[IsStored]\r\n" + 
			",[ProcessedDateTime]\r\n" + 
			",i.[DataSourceTypeID_FK]\r\n" + 
			",i.[LocationID_FK]\r\n" + 
			",[AssociationID_FK]\r\n" + 
			",a.[AssociationID_PK]\r\n" + 
			",[CycleEventID_FK]\r\n" + 
			",st.Name\r\n" + 
			",ls.LocationStateID_FK\r\n" + 
			"FROM [Unifia].[dbo].[ItemHistory] i\r\n" + 
			"Join Location l on i.LocationID_FK=l.LocationID_PK\r\n" + 
			"join ScanItemType st on i.scanItemTypeID_FK=st.ScanItemTypeID_PK\r\n" + 
			"join LocationStatus ls on ls.LocationID_FK=i.LocationID_FK\r\n" + 
			"join Association a on a.AssociationID_PK = i.AssociationID_FK\r\n" + 
			"Where l.Name='Procedure Room 1' and i.CycleEventID_FK = '%s'\r\n" + 			
			"and i.LastUpdatedDateTime >= '%s'";
}
