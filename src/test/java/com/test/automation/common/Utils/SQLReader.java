package com.test.automation.common.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLReader {
    public SQLReader() {

    	//These strings can eventually be set in config.properties
        String serverIP = "40.87.15.187";
        String dbName = "NoCode_QA_Practice";
        String dbUser = "nocodedev";
        String dbPassword = "nocode@123";
        
        String connectionUrl = "jdbc:sqlserver:"+serverIP+";databaseName="+dbName+";user="+dbUser+";password=" + dbPassword;

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT TOP (1000) [ID], [ObjectName], [ObjectLocator], [WaitTime], " + 
            		"[ObjectOrder], [PageNameID], [UserID] " + 
            		"FROM [" + dbName + "].[dbo].[ObjectRepository]";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set.
            while (rs.next()) {
            	String desiredDataSet = null;
                System.out.println(rs.getString(desiredDataSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}