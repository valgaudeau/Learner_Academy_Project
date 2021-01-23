package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.POJO.Subject;

public class SubjectDAO 
{
	public int insertSubjectInDB(Subject subject)
	{
	
	Connection conn = DatabaseConnectionManager.getDBConnection();
	// We don't hardcode the values in here, we use "?" which is a positional parameter
	String insert = "insert into subject values(?, ?)"; 
	
	int count = 0;
	
	try 
	{
		
		// The prepare statement method of connection allows us to write SQL code to the database directly
		PreparedStatement preparedStatement = conn.prepareStatement(insert);
		
		/*
		 *  this is not always column position. It means position of the question mark to which we're sending data in the
		 *  positional parameter insert string above 
		 */

		preparedStatement.setInt(1, subject.getSubjectId());
		preparedStatement.setString(2, subject.getSubjectName()); 
		
		count = preparedStatement.executeUpdate();
			
	} catch (SQLException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return count;
	
}

	// Everything is the same here as in the insert method, just small changes like the insert statement changes obviously
	public int updateSubjectInDB(Subject subject)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		// The id of the database entry is needed to find what we want to change
		String update = "update subject set sname = ? where sid = ?"; 
	
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
			preparedStatement.setString(1, subject.getSubjectName());
			preparedStatement.setInt(2, subject.getSubjectId());
			
			count = preparedStatement.executeUpdate();
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
		
	}
	
	// We could pass the whole object here, then pname and price would be null.
	public int deleteSubjectInDB(int subjectId)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		// The id of the database entry is needed to find what we want to change
		
		int count = 0;
		
		String delete = "delete from subject where sid = ?";
		
		try 
		{
			
			// The prepare statement method of connection allows us to write SQL code to the database directly
			PreparedStatement preparedStatement = conn.prepareStatement(delete);
				
			preparedStatement.setInt(1, subjectId);
			
			count = preparedStatement.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	
	}
	
	public List<Subject> getAllSubjects() // Use this for the button which shows all teachers
	{
		List<Subject> subjectList = new ArrayList<Subject>();
		
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "select * from subject";
		
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
				int subjectId = rs.getInt(1);
				String subjectName = rs.getString(2);
				
				// in rs.getInt / getString, we can pass the column number (like 1 for getInt) or the column name like pname
				// System.out.println(rs.getInt(1) + " " + rs.getString("pname") + " " + rs.getDouble(3));
				
				// Now, we want to store the products in our database in one collection
				Subject subject = new Subject();
				
				// 2) We pass the retrieved data into our POJO class to create a list of objects that can be displayed in the app
				subject.setSubjectId(subjectId);
				subject.setSubjectName(subjectName);
				
				subjectList.add(subject);
			}
					
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subjectList;		
	}
	
	public boolean checkSubjectValidity(int sid)
	{
		// The purpose of this class is to check if a classid exists
		Connection conn = DatabaseConnectionManager.getDBConnection();
		String findSubject = "select * from subject where sid=".concat(Integer.toString(sid));
		
		boolean subjectValidityStatus = false;
		 
		try
		{	
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(findSubject);
			
			while (rs.next())
			{
				int classId = rs.getInt(1);
				
				if (classId == sid)
				{
					subjectValidityStatus = true;
					break;
				}			
			}			
		
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		return subjectValidityStatus;
	}
	
	public String returnSubjectName(int sid)
	{
		// The purpose of this function is to return matching className for a classid
		Connection conn = DatabaseConnectionManager.getDBConnection();
		String query = "select * from subject where sid=".concat(Integer.toString(sid));
		
		String subjectName = "";
		
		try
		{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				subjectName = rs.getString(2);
				break;
			}
			
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}	
		return subjectName;		
	}
		
}
