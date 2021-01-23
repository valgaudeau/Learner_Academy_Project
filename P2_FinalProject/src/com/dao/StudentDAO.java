package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.POJO.Student;

public class StudentDAO
{
	public int insertStudentInDB(Student student)
	{
	
		Connection conn = DatabaseConnectionManager.getDBConnection();
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		String insert = "insert into student values(?, ?, ?, ?)"; 
		
		// EXPERIMENT WITH NEW INSERT METHOD AFTER YOU SEE IF IT WORKS
		
		int count = 0;
		
		try 
		{
			
			// The prepare statement method of connection allows us to write SQL code to the database directly
			PreparedStatement preparedStatement = conn.prepareStatement(insert);
			
			/*
			 *  this is not always column position. It means position of the question mark to which we're sending data in the
			 *  positional parameter insert string above 
			 */
	
			preparedStatement.setInt(1, student.getStudentId());
			preparedStatement.setString(2, student.getStudentFname());
			preparedStatement.setString(3, student.getStudentLname());
			preparedStatement.setInt(4, student.getAssociatedcid());

			count = preparedStatement.executeUpdate();
				
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return count;
	}

	// Everything is the same here as in the insert method, just small changes like the insert statement changes obviously
	public int updateStudentInDB(Student student)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		// The id of the database entry is needed to find what we want to change
		String update = "update student set studfname = ?, studlname = ?, associatedcid = ? where studid = ?"; 
	
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
			preparedStatement.setString(1, student.getStudentFname());
			preparedStatement.setString(2, student.getStudentLname());
			preparedStatement.setInt(3, student.getAssociatedcid());
			preparedStatement.setInt(4, student.getStudentId()); 
			
			count = preparedStatement.executeUpdate();
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
		
	}
	
	// We could pass the whole object here, then pname and price would be null.
	public int deleteStudentInDB(int studentId)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// We don't hardcode the values in here, we use "?" which is a positional parameter
		// The id of the database entry is needed to find what we want to change
		
		int count = 0;
		
		String delete = "delete from student where studid = ?";
		
		try 
		{
			
			// The prepare statement method of connection allows us to write SQL code to the database directly
			PreparedStatement preparedStatement = conn.prepareStatement(delete);
				
			preparedStatement.setInt(1, studentId);
			
			count = preparedStatement.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	
	}
	
	public List getAllStudents() // Use this for the button which shows all teachers
	{
		List<Student> studentList = new ArrayList<Student>();
		
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "select * from student";
		
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
				int studentId = rs.getInt(1);
				String studentFname = rs.getString(2);
				String studentLname = rs.getString(3);
				int associatedcid = rs.getInt(4);
				
				// in rs.getInt / getString, we can pass the column number (like 1 for getInt) or the column name like pname
				// System.out.println(rs.getInt(1) + " " + rs.getString("pname") + " " + rs.getDouble(3));
				
				// Now, we want to store the products in our database in one collection
				Student student = new Student();
				
				// 2) We pass the retrieved data into our POJO class to create a list of objects that can be displayed in the app
				student.setStudentId(studentId);
				student.setStudentFname(studentFname);
				student.setStudentLname(studentLname);
				student.setAssociatedcid(associatedcid);
				
				studentList.add(student);
			}
					
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentList;		
	}
	
	public boolean checkStudentValidity(int studid)
	{
		// The purpose of this class is to check if a classid exists
		Connection conn = DatabaseConnectionManager.getDBConnection();
		String findStudent = "select * from student where studid=".concat(Integer.toString(studid));
		
		boolean studentValidityStatus = false;
		 
		try
		{	
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(findStudent);
			
			while (rs.next())
			{
				int studentId = rs.getInt(1);
				
				if (studentId == studid)
				{
					studentValidityStatus = true;
					break;
				}			
			}			
		
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		return studentValidityStatus;
	}
	
	public List retrieveStudentsByClassId(int cid)
	{
		List<Student> studentReportList = new ArrayList<Student>();
		
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "select * from student where associatedcid=" + Integer.toString(cid);
		
		try 
		{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// Result set is used to store the data which are returned from the database table after the execution of SQL statements
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				int studentId = rs.getInt(1);
				String studentFname = rs.getString(2);
				String studentLname = rs.getString(3);
				int associatedcid = rs.getInt(4);
				
				Student student = new Student();
				
				student.setStudentId(studentId);
				student.setStudentFname(studentFname);
				student.setStudentLname(studentLname);
				student.setAssociatedcid(associatedcid);
				
				studentReportList.add(student);
			}
					
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return studentReportList;
	}
}

