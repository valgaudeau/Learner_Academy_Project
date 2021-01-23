package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.POJO.Teacher;

public class TeacherDAO 
{
	public int insertTeacherInDB(Teacher teacher)
	{
	
	Connection conn = DatabaseConnectionManager.getDBConnection();
	// We don't hardcode the values in here, we use "?" which is a positional parameter
	String insert = "insert into teacher values(?, ?, ?)"; 
	
	int count = 0;
	
	try 
	{
		
		// The prepare statement method of connection allows us to write SQL code to the database directly
		PreparedStatement preparedStatement = conn.prepareStatement(insert);
		
		/*
		 *  this is not always column position. It means position of the question mark to which we're sending data in the
		 *  positional parameter insert string above 
		 */

		preparedStatement.setInt(1, teacher.getTeacherId());
		preparedStatement.setString(2, teacher.getTeacherFname());
		preparedStatement.setString(3, teacher.getTeacherLname());  
		
		count = preparedStatement.executeUpdate();
			
	} catch (SQLException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return count;
	
}

	// Everything is the same here as in the insert method, just small changes like the insert statement changes obviously
	public int updateTeacherInDB(Teacher teacher)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		// The id of the database entry is needed to find what we want to change
		String update = "update teacher set fname = ?, lname = ? where id = ?"; 
	
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
			preparedStatement.setString(1, teacher.getTeacherFname());
			preparedStatement.setString(2, teacher.getTeacherLname());
			preparedStatement.setInt(3, teacher.getTeacherId());  
			
			count = preparedStatement.executeUpdate();
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
		
	}
	
	// We could pass the whole object here, then pname and price would be null.
	public int deleteTeacherInDB(int teacherId)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		// The id of the database entry is needed to find what we want to change
		
		int count = 0;
		
		String delete = "delete from teacher where id = ?";
		
		try 
		{
			
			// The prepare statement method of connection allows us to write SQL code to the database directly
			PreparedStatement preparedStatement = conn.prepareStatement(delete);
				
			preparedStatement.setInt(1, teacherId);
			
			count = preparedStatement.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	
	}
	
	public List getAllTeachers() // Use this for the button which shows all teachers
	{
		List<Teacher> teacherList = new ArrayList<Teacher>();
		
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "select * from teacher";
		
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
				int teacherId = rs.getInt(1);
				String teacherFname = rs.getString(2);
				String teacherLname = rs.getString(3);
				
				// in rs.getInt / getString, we can pass the column number (like 1 for getInt) or the column name like pname
				// System.out.println(rs.getInt(1) + " " + rs.getString("pname") + " " + rs.getDouble(3));
				
				// Now, we want to store the products in our database in one collection
				Teacher teacher = new Teacher();
				
				// 2) We pass the retrieved data into our POJO class to create a list of objects that can be displayed in the app
				teacher.setTeacherId(teacherId);
				teacher.setTeacherFname(teacherFname);
				teacher.setTeacherLname(teacherLname);
				
				teacherList.add(teacher);
			}
					
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teacherList;		
	}
	
	public boolean checkTeacherValidity(int tid)
	{
		// The purpose of this class is to check if a classid exists
		Connection conn = DatabaseConnectionManager.getDBConnection();
		String findTeacher = "select * from teacher where id=".concat(Integer.toString(tid));
		
		boolean teacherValidityStatus = false;
		 
		try
		{	
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(findTeacher);
			
			while (rs.next())
			{
				int classId = rs.getInt(1);
				
				if (classId == tid)
				{
					teacherValidityStatus = true;
					break;
				}			
			}			
		
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		return teacherValidityStatus;
	}
	
	public String returnTeacherFullName(int tid)
	{
		// The purpose of this function is to return matching className for a classid
		Connection conn = DatabaseConnectionManager.getDBConnection();
		String query = "select * from teacher where id=".concat(Integer.toString(tid));
		
		String teacherFname = "";
		String teacherLname = "";
		
		try
		{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				teacherFname = rs.getString(2);
				teacherLname = rs.getString(3);
				break;
			}
			
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		
		String teacherFullName = teacherFname + " " + teacherLname;
		
		return teacherFullName;		
	}
		
}
