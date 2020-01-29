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
        	
        	Class.forName("com.mysql.jdbc.Driver");
        	
            String SQL = "SELECT TOP (10) [ID], [ObjectName], [ObjectLocator], [WaitTime], " + 
            		"[ObjectOrder], [PageNameID], [UserID] " + 
            		"FROM [" + dbName + "].[dbo].[ObjectRepository]";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set.
            while (rs.next()) {
                System.out.println(rs.getString("ObjectName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
    }
}