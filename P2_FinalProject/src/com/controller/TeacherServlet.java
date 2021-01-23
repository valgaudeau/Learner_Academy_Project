package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.POJO.Teacher;
import com.dao.DatabaseConnectionManager;
import com.dao.TeacherDAO;

/**
 * Servlet implementation class Teacher_Servlet
 */
public class TeacherServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherServlet() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = null; // The object which allows us to dynamically change pages
		
		response.setContentType("text/html");
		
		// Here we create the object of the TeacherDAO class so we can call all the different methods
		TeacherDAO teacherDAO = new TeacherDAO();
		
		/*
		 *  Now we take data from the front-end, store in the Servlet and export it to the DAO layer which communicates to the DB.
		 *  Instead of passing all the parameters, we store them in a POJO class object which has multiple data attributes in it.
		 *  Note that we take everything as String here
		 */
		
		// The first thing we need to test is if user presses display all. CAN'T BE INCLUDED IN THE TRY SINCE NO TEXT INPUT HERE
		if (request.getParameter("displayallteachers") != null)
		{
			try
			{
				List<Teacher> teacherList = teacherDAO.getAllTeachers();
				
				for (Teacher teacher1: teacherList)
				{
					out.print("ID = " + teacher1.getTeacherId() + ", Fname = " + teacher1.getTeacherFname() + ", Lname = " + 
				teacher1.getTeacherLname());
					out.print("<br/>");
				}
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Sorry, the application ran into an error. Please try again");
			}
		}
		
		if (request.getParameter("addteacherbutton") != null)
		{
			try
			{
				String teacherId = request.getParameter("teacherid");
				String teacherFname = request.getParameter("teacherfname");
				String teacherLname = request.getParameter("teacherlname");
				
				 // First, check if an object with that id key already exists in the database
				if(teacherDAO.checkTeacherValidity(Integer.parseInt(teacherId)))
				{
					out.print("Operation unsuccessful, there is already a teacher with that ID in the database");
				}
				else
				{
					Teacher teacher = new Teacher();
					teacher.setTeacherId(Integer.parseInt(teacherId));
					teacher.setTeacherFname(teacherFname);
					teacher.setTeacherLname(teacherLname);
					
					int count = teacherDAO.insertTeacherInDB(teacher);
					out.print(count + " teacher successfully added");
					DatabaseConnectionManager.closeDBConnection();
				}
						
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Operation unsuccessful, invalid input");
				/*
				 * 		 
				rd = request.getRequestDispatcher("index.html");
				rd.include(request, response);
				*/
			}
		}	
					
		if (request.getParameter("updateteacherbutton") != null) // Update teacher database entry
		{
			try
			{
				String teacherIdUpdate = request.getParameter("teacheridupdate");
				String teacherFnameUpdate = request.getParameter("teacherfnameupdate");
				String teacherLnameUpdate = request.getParameter("teacherlnameupdate");
				
				if(teacherDAO.checkTeacherValidity(Integer.parseInt(teacherIdUpdate)))
				{
					Teacher teacher = new Teacher();
					teacher.setTeacherId(Integer.parseInt(teacherIdUpdate));
					teacher.setTeacherFname(teacherFnameUpdate);
					teacher.setTeacherLname(teacherLnameUpdate);
						
					int updateCount = teacherDAO.updateTeacherInDB(teacher);
					out.print(updateCount + " teacher successfully updated");
					DatabaseConnectionManager.closeDBConnection();
					
				}
				else
				{							
					out.print("Operation unsuccessful, there is no teacher with that ID in the database");
				}	
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Operation unsuccessful, invalid input");
			}
	
		}
				
		if (request.getParameter("deleteteacherbutton") != null)
		{
			try
			{
				String teacherIdDelete = request.getParameter("teacheriddelete");
				
				if(teacherDAO.checkTeacherValidity(Integer.parseInt(teacherIdDelete)))
				{
					int deleteCount = teacherDAO.deleteTeacherInDB(Integer.parseInt(teacherIdDelete));
					out.print(deleteCount + " teacher successfully deleted");
					DatabaseConnectionManager.closeDBConnection();
					
				}
				else
				{
					out.print("Operation unsuccessful, there is no teacher with that ID in the database");
				}
						
			} catch (Exception e) 
			{
			// e.printStackTrace();
			out.print("Operation unsuccessful, please make sure to enter valid input");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
