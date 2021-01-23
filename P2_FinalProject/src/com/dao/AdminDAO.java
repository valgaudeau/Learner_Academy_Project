package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.POJO.Admin;

public class AdminDAO 
{
	public Admin getAdminInfoFromDB()
	{
		Admin admin = new Admin();
		
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "select * from admin";
		
		try 
		{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
			ResultSet rs = stmt.executeQuery(query);
			
			// Only need to run this once since there's only 1 admin entry in the Database
			while(rs.next())
			{
				int adminId = rs.getInt(1);
				String adminUsername = rs.getString(2);
				String adminPassword = rs.getString(3);
				
				// Since there's only 1 entry in the Admin table, we can just retrieve the data and put it in a single Admin object
				admin.setAid(adminId);
				admin.setUsername(adminUsername);
				admin.setPassword(adminPassword);
			}
	
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return admin;
	}
}
