package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager 
{
	
	/*
	 *  made the class static so we can directly call the methods without having to create an object of the class
	 *  This class handles everything that has to do with connecting to the database
	 */
	
	static Connection conn = null;
	
	public static Connection getDBConnection() 
	{	
			
		try 
		{
			
		// Step 1 - register driver class. Given by Database company, everything else is given by Java company (sql packages)
		Class.forName("com.mysql.cj.jdbc.Driver"); 
							
		// Step 2 - Getting connection
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/p2finalprojectdb", "root", "n00bcream");
			
		} catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Error encountered of SQL type");
		} 
		return conn;
		
	}
	
	public static void closeDBConnection()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
