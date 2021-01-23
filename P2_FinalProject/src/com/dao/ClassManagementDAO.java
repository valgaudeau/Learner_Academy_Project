package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.POJO.ClassManagement;
import com.POJO.Student;

public class ClassManagementDAO 
{
	public void assignSubjectAndTeacherToClass(int cid, int sid, int tid)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "insert into classmanagement values(?, ?, ?, ?, ?, ?, ?)";
		
		ClassesDAO classesDAO = new ClassesDAO();
		SubjectDAO subjectDAO = new SubjectDAO();
		TeacherDAO teacherDAO = new TeacherDAO();
		
		try
		{
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			
			/*
			 *  the check to see if the ids are correct will be called from the servlet. What I need here is to call the functions
			 *  which retrieve the matching classname, subjectname, teacherfname & lname and store the result in Strings			 
			 *  Then all of these variables can be inserted in the positional values of our query
			 */
			 
			String className = classesDAO.returnClassName(cid);
			String subjectName = subjectDAO.returnSubjectName(sid);
			String teacherFullName = teacherDAO.returnTeacherFullName(tid);
			
			preparedStatement.setInt(1,  cid);
			preparedStatement.setString(2, className);
			preparedStatement.setInt(3,  sid);
			preparedStatement.setString(4, subjectName);
			preparedStatement.setInt(5,  tid);
			preparedStatement.setString(6, teacherFullName);
			preparedStatement.setInt(7, 0);
			
			preparedStatement.executeUpdate();
		
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
	}
	
	public List displayAllAssignments() // Use this for the button which shows all teachers
	{
		List<ClassManagement> classManagementList = new ArrayList<ClassManagement>();
		
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "select * from classmanagement";
		
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
				int subjectId = rs.getInt(3);
				String subjectName = rs.getString(4);
				int teacherId = rs.getInt(5);
				String teacherFullName = rs.getString(6);
				int uniqueId = rs.getInt(7);
				
				// Now, we want to store the products in our database in one collection
				ClassManagement classManagement = new ClassManagement();
				
				// 2) We pass the retrieved data into our POJO class to create a list of objects that can be displayed in the app
				classManagement.setClassId(classId);
				classManagement.setClassName(className);
				classManagement.setSubjectId(subjectId);
				classManagement.setSubjectName(subjectName);
				classManagement.setTeacherId(teacherId);
				classManagement.setTeacherFullName(teacherFullName);
				classManagement.setUniqueId(uniqueId);
				
				classManagementList.add(classManagement);
			}
					
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classManagementList;		
	}
	
	public int deleteClassManagementEntryInDB(int cid, int sid, int tid)
	{
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		// Check if assignment exists in Servlet. Here we just execute the deletion
		int count = 0;
		
		String deleteQuery = "delete from classmanagement where classid =" + Integer.toString(cid) + " and subjectid=" + 
				Integer.toString(sid) + " and teacherid=" + Integer.toString(tid);
		
		try 
		{
			
			// The prepare statement method of connection allows us to write SQL code to the database directly
			PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
			
			count = preparedStatement.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	
	}
	
	public boolean checkAssignmentValidity(int cid, int sid, int tid)
	{
		// The purpose of this class is to check if a classid exists
		Connection conn = DatabaseConnectionManager.getDBConnection();
		String query = "select * from classmanagement where classid=" + Integer.toString(cid) + " and subjectid=" + 
		Integer.toString(sid) + " and teacherid=" + Integer.toString(tid);
		
		boolean classManagementValidityStatus = false;
		 
		try
		{	
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				int classId = rs.getInt(1);
				int subjectId = rs.getInt(3);
				int teacherId = rs.getInt(5);
				
				if ((classId == cid) && (subjectId == sid) && (teacherId == tid))
				{
					classManagementValidityStatus = true;
					break;
				}			
			}			
		
		}catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		return classManagementValidityStatus;
	}
	
	public List classManagementReport(int cid)
	{
		List<ClassManagement> classManagementReportList = new ArrayList<ClassManagement>();
		
		Connection conn = DatabaseConnectionManager.getDBConnection();
		
		String query = "select * from classmanagement where classid=" + Integer.toString(cid);
		try 
		{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// Result set is used to store the data which are returned from the database table after the execution of SQL statements
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				// 1) We store each of the values retrieved from the database in variables
				int classId = rs.getInt(1);
				String className = rs.getString(2);
				int subjectId = rs.getInt(3);
				String subjectName = rs.getString(4);
				int teacherId = rs.getInt(5);
				String teacherFullName = rs.getString(6);
				int uniqueId = rs.getInt(7);
				
				// Now, we want to store the products in our database in one collection
				ClassManagement classManagement = new ClassManagement();
				
				// 2) We pass the retrieved data into our POJO class to create a list of objects that can be displayed in the app
				classManagement.setClassId(classId);
				classManagement.setClassName(className);
				classManagement.setSubjectId(subjectId);
				classManagement.setSubjectName(subjectName);
				classManagement.setTeacherId(teacherId);
				classManagement.setTeacherFullName(teacherFullName);
				classManagement.setUniqueId(uniqueId);
				
				classManagementReportList.add(classManagement);
			}
					
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return classManagementReportList;
	}
	
}
