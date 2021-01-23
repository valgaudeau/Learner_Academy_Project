package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.POJO.Student;
import com.dao.ClassesDAO;
import com.dao.DatabaseConnectionManager;
import com.dao.StudentDAO;

/**
 * Servlet implementation class StudentServlet
 */
public class StudentServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() 
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
		StudentDAO studentDAO = new StudentDAO();
		ClassesDAO classesDAO = new ClassesDAO();
		
		/*
		 *  Now we take data from the front-end, store in the Servlet and export it to the DAO layer which communicates to the DB.
		 *  Instead of passing all the parameters, we store them in a POJO class object which has multiple data attributes in it.
		 *  Note that we take everything as String here
		 */
		
		if (request.getParameter("displayallstudents") != null)
		{
			try 
			{
				List<Student> studentList = studentDAO.getAllStudents();
				
				for (Student student1: studentList)
				{
					out.print("ID = " + student1.getStudentId() + ", Fname = " + student1.getStudentFname() + 
							", Lname = " + student1.getStudentLname() + ", Assigned Class ID = " + student1.getAssociatedcid());
					out.print("<br/>");
				}
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Sorry, the application ran into an error. Please try again");
			}
		}
		
		if (request.getParameter("addstudentbutton") != null)
		{
			try
			{
				String studentId = request.getParameter("studentid");
				String studentFname = request.getParameter("studentfname");
				String studentLname = request.getParameter("studentlname");
				String associatedcid = request.getParameter("associatedcid");
				
				if(studentDAO.checkStudentValidity(Integer.parseInt(studentId)))
				{
					out.print("Operation unsuccessful, there is already a student in the database with that ID");
				}
				else
				{
					Student student = new Student();
					student.setStudentId(Integer.parseInt(studentId));
					student.setStudentFname(studentFname);
					student.setStudentLname(studentLname);
					student.setAssociatedcid(Integer.parseInt(associatedcid));
					
					if(classesDAO.checkClassValidity(Integer.parseInt(associatedcid)))
					{
						int addCount = studentDAO.insertStudentInDB(student);
						out.print(addCount + " student successfully added");
						DatabaseConnectionManager.closeDBConnection();
					}
					else
					{
						out.print("Operation unsuccessful, please make sure to use an existing classID");
					}	
					
				}
					
			}catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("The operation was unsuccessful, invalid input");
			}
		}
		
		if (request.getParameter("updatestudentbutton") != null)
		{
			try
			{
				String studentIdUpdate = request.getParameter("studentidupdate");
				String studentFnameUpdate = request.getParameter("studentfnameupdate");
				String studentLnameUpdate = request.getParameter("studentlnameupdate");
				String associatedcidUpdate = request.getParameter("associatedcidupdate");
				
				if(studentDAO.checkStudentValidity(Integer.parseInt(studentIdUpdate)))
				{
					Student student = new Student();
					student.setStudentId(Integer.parseInt(studentIdUpdate));
					student.setStudentFname(studentFnameUpdate);
					student.setStudentLname(studentLnameUpdate);
					student.setAssociatedcid(Integer.parseInt(associatedcidUpdate));
					
					if(classesDAO.checkClassValidity(Integer.parseInt(associatedcidUpdate)))
					{
						int updateCount = studentDAO.updateStudentInDB(student);
						out.print(updateCount + " student successfully updated");
						DatabaseConnectionManager.closeDBConnection();
					}
					else
					{
						out.print("Operation unsuccessful, please make sure to use an existing classID");
					}				
				}
				else
				{
					out.print("Sorry, there is no student with that ID in the database");
				}
			}catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Operation unsuccessful, invalid input");
			}
		}
		
		if (request.getParameter("deletestudentbutton") != null)
		{
			try
			{
				String studentId = request.getParameter("studentiddelete");
				
				if(studentDAO.checkStudentValidity(Integer.parseInt(studentId)))
				{
					int deleteCount = studentDAO.deleteStudentInDB(Integer.parseInt(studentId));
					out.print(deleteCount + " student successfully deleted");
					DatabaseConnectionManager.closeDBConnection();
				}
				else
				{
					out.print("Operation unsuccessful, there is no student with that ID in the database.");
				}
				
			}catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Sorry, the application ran into an error. Please try again");
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
