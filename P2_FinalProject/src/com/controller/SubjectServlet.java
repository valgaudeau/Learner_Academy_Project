package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.POJO.Subject;
import com.dao.DatabaseConnectionManager;
import com.dao.SubjectDAO;

/**
 * Servlet implementation class SubjectServlet
 */
public class SubjectServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectServlet() 
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
		SubjectDAO subjectDAO = new SubjectDAO();
		
		/*
		 *  Now we take data from the front-end, store in the Servlet and export it to the DAO layer which communicates to the DB.
		 *  Instead of passing all the parameters, we store them in a POJO class object which has multiple data attributes in it.
		 *  Note that we take everything as String here
		 */
		
		// The first thing we need to test is if user presses display all. CAN'T BE INCLUDED IN THE TRY SINCE NO TEXT INPUT HERE
		if (request.getParameter("displayallsubjects") != null)
		{
			try
			{
				List<Subject> subjectList = subjectDAO.getAllSubjects();
				
				for (Subject subject1: subjectList)
				{
					out.print("ID = " + subject1.getSubjectId() + ", Name = " + subject1.getSubjectName());
					out.print("<br/>");
				}
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("The application ran into an error. Please try again");
			}
		}
		
		if (request.getParameter("addsubjectbutton") != null) // Add teacher to database
		{
			try
			{
				String subjectId = request.getParameter("subjectid");
				String subjectName = request.getParameter("subjectname");
				
				if(subjectDAO.checkSubjectValidity(Integer.parseInt(subjectId)))
				{
					out.print("Operation unsuccessful, subject ID already exists");
				}
				else
				{
					Subject subject = new Subject();
					subject.setSubjectId(Integer.parseInt(subjectId));
					subject.setSubjectName(subjectName);
					
					int addCount = subjectDAO.insertSubjectInDB(subject);
					out.print(addCount + " subject successfully added");
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
				
		if (request.getParameter("updatesubjectbutton") != null) // Update teacher database entry	
		{		
			try
			{
				String subjectIdUpdate = request.getParameter("subjectidupdate");
				String subjectNameUpdate = request.getParameter("subjectnameupdate");
				
				if(subjectDAO.checkSubjectValidity(Integer.parseInt(subjectIdUpdate)))
				{
					Subject subject = new Subject();
					subject.setSubjectId(Integer.parseInt(subjectIdUpdate));
					subject.setSubjectName(subjectNameUpdate);
					
					int updateCount = subjectDAO.updateSubjectInDB(subject);
					out.print(updateCount + " subject successfully updated");
					DatabaseConnectionManager.closeDBConnection();
					
				}
				else
				{
					out.print("Subject ID not found");
				}
				
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Operation unsuccessful, invalid input");
			}
		}	
			
		if (request.getParameter("deletesubjectbutton") != null)	
		{	
			try
			{
				String subjectIdDelete = request.getParameter("subjectiddelete");
			
				if (subjectDAO.checkSubjectValidity(Integer.parseInt(subjectIdDelete)))
				{
					int deleteCount = subjectDAO.deleteSubjectInDB(Integer.parseInt(subjectIdDelete));
					out.print(deleteCount + " subject successfully deleted");
					DatabaseConnectionManager.closeDBConnection();
				}
				else
				{
					out.print("Subject ID not found");
				}
				
			} catch (Exception e) 
			{
			// e.printStackTrace();
			out.print("Operation unsuccessful, please make sure to enter an existing ID");
			/*	
			rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
			 */
			// Problem regarding message is that it would need to be printed next to where we clicked. Is it necessary anyway?
			// out.println("<span style='color:red'>Invalid input, please try again</span>");
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
