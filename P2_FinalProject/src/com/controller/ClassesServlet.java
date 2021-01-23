package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.POJO.Classes;
import com.POJO.Subject;
import com.dao.ClassesDAO;
import com.dao.DatabaseConnectionManager;
import com.dao.SubjectDAO;
import com.dao.TeacherDAO;

/**
 * Servlet implementation class ClassServlet
 */
public class ClassesServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassesServlet() 
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
		ClassesDAO classesDAO = new ClassesDAO();
		SubjectDAO subjectDAO = new SubjectDAO();
		TeacherDAO teacherDAO = new TeacherDAO();
		
		/*
		 *  Now we take data from the front-end, store in the Servlet and export it to the DAO layer which communicates to the DB.
		 *  Instead of passing all the parameters, we store them in a POJO class object which has multiple data attributes in it.
		 *  Note that we take everything as String here
		 */
		
		if (request.getParameter("displayallclasses") != null)
		{
			try
			{
				List<Classes> classesList = classesDAO.getAllClasses();
				
				for (Classes classes: classesList)
				{
					out.print("ID = " + classes.getClassesId() + ", Name = " + classes.getClassesName());
					out.print("<br/>");
				}
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("The application ran into an error. Please try again");
			}
		}
		
		if (request.getParameter("initialaddclassesbutton") != null) // The first insert in the form
		{
			try
			{
				String classIdInitial = request.getParameter("classesidinitial");
				String classNameInitial = request.getParameter("classnameinitial");
				
				if(classesDAO.checkClassValidity(Integer.parseInt(classIdInitial)))
				{
					out.print("Operation unsuccessful, there is already a class with that ID in the database");
				}
				else
				{
				// Note that this time, we don't store the values in a POJO before passing them to the DB
				int count = classesDAO.initialInsertClassesInDB(Integer.parseInt(classIdInitial), classNameInitial);
				out.print(count + " class successfully added");
				DatabaseConnectionManager.closeDBConnection();
				}
				
			} catch (Exception e) 
			{
				out.print("Operation unsuccessful, invalid input");
			// rd = request.getRequestDispatcher("index.html");
			// rd.include(request, response);
			}
		}
		
		if (request.getParameter("updateclassesbutton") != null) // Update teacher database entry	
		{		
			try
			{
				String classIdUpdate = request.getParameter("classidupdate");
				String classNameUpdate = request.getParameter("classnameupdate");
				
				if(classesDAO.checkClassValidity(Integer.parseInt(classIdUpdate)))
				{
					Classes classes = new Classes();
					classes.setClassesId(Integer.parseInt(classIdUpdate));
					classes.setClassesName(classNameUpdate);
					
					int updateCount = classesDAO.updateClassInDB(classes);
					out.print(updateCount + " class successfully updated");
					DatabaseConnectionManager.closeDBConnection();
					
				}
				else
				{
					out.print("Class ID not found");
				}
				
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Operation unsuccessful, invalid input");
			}
		}	
		
		if (request.getParameter("deleteclassesbutton") != null)
		{
			try
			{
				String classIdToDelete = request.getParameter("classiddelete");
				
				if(classesDAO.checkClassValidity(Integer.parseInt(classIdToDelete)))
				{
					int deleteCount = classesDAO.deleteClassInDB(Integer.parseInt(classIdToDelete));
					out.print(deleteCount + " class successfully deleted");
					DatabaseConnectionManager.closeDBConnection();
					
				}
				else
				{
					out.print("Class ID not found");
				}
				
			} catch (Exception e) 
			{
			out.print("Operation unsuccessful,p lease enter a valid ID");
			// rd = request.getRequestDispatcher("index.html");
			// rd.include(request, response);
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

