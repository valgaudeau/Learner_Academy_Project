package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.POJO.Classes;
import com.POJO.Subject;

public class ClassesDAO 
{
	// The first insert. Note we don't pass the whole object here, since subjectid and teacherid will be blank
	public int initialInsertClassesInDB(int cid, String className) 
	{
	
	Connection conn = DatabaseConnectionManager.getDBConnection();
	// Note that here (no positional args) we have to include the '' around strings we pass in the SQL command (className here)
	String valuesToInsert = "(" + Integer.toString(cid) + ", " + "'" + className + "')";
	
	String insert = "insert into classes(classid, classname) values".concat(valuesToInsert);
	
	int count = 0;
	
	try 
	{
		
		// The prepare statement method of connection allows us to write SQL code to the database directly
		PreparedStatement preparedStatement = conn.prepareStatement(insert);
		
		/*
		 *  this is not always column position. It means position of the question mark to which we're sending data in the
		 *  positional parameter insert string above 
		 */
		
		count = preparedStatement.executeUpdate();
			
	} catch (SQLException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return count;
	
	}
	
	public int updateClassInDB(Classes classes)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		// The id of the database entry is needed to find what we want to change
		String update = "update classes set classname = ? where classid = ?"; 
	
		int count = 0;
		
		try 
		{
			
			// The prepare statement method of connection allows us to write SQL code to the database directly
			PreparedStatement preparedStatement = conn.prepareStatement(update);
			
			/*
			 *  this is not always column position. It means position of the question mark to which we're sending data in the
			 *  positional parameter insert string above 
			 */
			
			// WATCH THE ORDER FOR UPDATE - ID DOESN'T COME FIRST
			preparedStatement.setString(1, classes.getClassesName());
			preparedStatement.setInt(2, classes.getClassesId());
			
			count = preparedStatement.executeUpdate();
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
		
	}
	
	
	// We could pass the whole object here, then pname and price would be null.
	public int deleteClassInDB(int cid)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		// The id of the database entry is needed to find what we want to change
		
		int count = 0;
		
		String delete = "delete from classes where classid = ".concat(Integer.toString(cid));
		
		try 
		{
			
			// The prepare statement method of connection allows us to write SQL code to the database directly
			PreparedStatement preparedStatement = conn.prepareStatement(delete);
			
			count = preparedStatement.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	
	}
	
	public List<Classes> getAllClasses() // Use this for the button which shows all classes
	{
		List<Classes> classesList = new ArrayList<Classes>();
		
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "select * from classes";
		
		try 
		{
			/*
			 *  Note that this time, we set our result set type to scroll sensitive and concurrent read only.
			 *  Scroll sensitive means that the result set will constantly update whatever its called to display.
			 *  Concurrent read means that the result set object cannot be updated, as opposed to CONCUR_UPDATABLE.
			 */
	 
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// Result set is used to store the data which are returned from the database table after the execution of SQL statements
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				// 1) We store each of the values retrieved from the database in variables
				int classId = rs.getInt(1);
				String className = rs.getString(2);
				
				// 2) We pass the retrieved data into our POJO class to create a list of objects that can be displayed in the app
				Classes classes = new Classes();
				classes.setClassesId(classId);
				classes.setClassesName(className);
				
				classesList.add(classes);
			}
					
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classesList;		
	}
	
	public boolean checkClassValidity(int cid)
	{
		// The purpose of this function is to check if a classid exists
		Connection conn = DatabaseConnectionManager.getDBConnection();
		String findClass = "select * from classes where classid=".concat(Integer.toString(cid));
		
		boolean classValidityStatus = false;
		 
		try
		{	
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(findClass);
			
			while (rs.next())
			{
				int classId = rs.getInt(1);
				
				if (classId == cid)
				{
					classValidityStatus = true;
					break;
				}			
			}			
		
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		return classValidityStatus;
	}
	
	public String returnClassName(int cid)
	{
		// The purpose of this function is to return matching className for a classid
		Connection conn = DatabaseConnectionManager.getDBConnection();
		String query = "select * from classes where classid=".concat(Integer.toString(cid));
		
		String className = "";
		
		try
		{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				className = rs.getString(2);
				break;
			}
			
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}	
		return className;		
	}
	
}