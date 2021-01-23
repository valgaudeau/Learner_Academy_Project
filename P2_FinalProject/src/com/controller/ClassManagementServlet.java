package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.POJO.ClassManagement;
import com.POJO.Student;
import com.dao.ClassManagementDAO;
import com.dao.ClassesDAO;
import com.dao.DatabaseConnectionManager;
import com.dao.StudentDAO;
import com.dao.SubjectDAO;
import com.dao.TeacherDAO;

/**
 * Servlet implementation class ClassManagementServlet
 */
public class ClassManagementServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassManagementServlet() 
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
		
		response.setContentType("text/html");
		
		ClassManagementDAO classManagementDAO = new ClassManagementDAO();
		ClassesDAO classesDAO = new ClassesDAO();
		SubjectDAO subjectDAO = new SubjectDAO();
		TeacherDAO teacherDAO = new TeacherDAO();
		StudentDAO studentDAO = new StudentDAO();
		
		if (request.getParameter("classmanagementassign") != null)
		{
			try
			{
				String classId = request.getParameter("classidmanagement");
				String subjectId = request.getParameter("subjectidmanagement");
				String teacherId = request.getParameter("teacheridmanagement");
				
				// First, check if the passed cid, sid and tid are valid
				if ((classesDAO.checkClassValidity(Integer.parseInt(classId))) && 
						subjectDAO.checkSubjectValidity(Integer.parseInt(subjectId)) && 
						teacherDAO.checkTeacherValidity(Integer.parseInt(teacherId)))
				{
					// Check if assignment already exists in the table
					if(classManagementDAO.checkAssignmentValidity(Integer.parseInt(classId), Integer.parseInt(subjectId), 
							Integer.parseInt(teacherId)))
					{
						out.print("Operation unsuccessful, this assignment already exists");
					}
					else
					{
						out.print("Success, you have the subjectID " + subjectId + " with the teacherID " + teacherId + 
								" to the classID " + classId);
						classManagementDAO.assignSubjectAndTeacherToClass(Integer.parseInt(classId), Integer.parseInt(subjectId), 
								Integer.parseInt(teacherId));
						DatabaseConnectionManager.closeDBConnection();
					}
				}
				else
				{
					out.print("Operation unsuccessful, please enter a valid classID, subjectID and teacherID");
				}
				
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Operation unsuccessful, invalid input");
			}		
			
		}
		
		if (request.getParameter("displayallassignments") != null)
		{
			try 
			{
				List<ClassManagement> classManagementList = classManagementDAO.displayAllAssignments();
				
				for (ClassManagement classManagement: classManagementList)
				{
					String temp = "";
					String temp2 = ",  ";
					temp += "classID = ";
					temp += classManagement.getClassId();
					temp += temp2;
					temp += "SubjectID = ";
					temp += classManagement.getSubjectId();
					temp += temp2;
					temp += "TeacherID = ";
					temp += classManagement.getTeacherId();
					temp += temp2;
					temp += "Class Name = ";
					temp += classManagement.getClassName();
					temp += temp2;
					temp += "Subject Name = ";
					temp += classManagement.getSubjectName();
					temp += temp2;
					temp += "Teacher Full Name = ";
					temp += classManagement.getTeacherFullName();
					// temp += temp2;
					temp += "<br/>";
					out.print(temp);
				}
			} catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Sorry, the application ran into an error");
			}
		}
		
		if (request.getParameter("classmanagementdelete") != null)
		{
			try
			{
				String classIdDelete = request.getParameter("classidmanagementdelete");
				String subjectIdDelete = request.getParameter("subjectidmanagementdelete");
				String teacherIdDelete = request.getParameter("teacheridmanagementdelete");
				
				if (classManagementDAO.checkAssignmentValidity(Integer.parseInt(classIdDelete), Integer.parseInt(subjectIdDelete),
						Integer.parseInt(teacherIdDelete)))
				{
					int deleteCount = classManagementDAO.deleteClassManagementEntryInDB(Integer.parseInt(classIdDelete), Integer.parseInt(subjectIdDelete),
						Integer.parseInt(teacherIdDelete));
					out.print(deleteCount + " assignment successfully deleted");
					DatabaseConnectionManager.closeDBConnection();
				}
				else
				{
					out.print("Operation unsuccessful, this assignment doesn't exist in the database.");
				}
			
			}catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Operation unsuccessful, please make sure to enter correct ID's");
			}
		}
		
		if (request.getParameter("classmanagementreport") != null)
		{
			try
			{
				String classIdReport = request.getParameter("classidreport");
				
				List<ClassManagement> classManagementReportList = classManagementDAO.classManagementReport(Integer.parseInt(classIdReport));
				List<Student> studentReportList = studentDAO.retrieveStudentsByClassId(Integer.parseInt(classIdReport));
				
				out.print("<b>Classes, Subjects and Teachers: </b>");
				out.print("<br/>");
				
				for (ClassManagement classManagement: classManagementReportList)
				{
					String temp = "";
					String temp2 = ",  ";
					temp += "classID = ";
					temp += classManagement.getClassId();
					temp += temp2;
					temp += "SubjectID = ";
					temp += classManagement.getSubjectId();
					temp += temp2;
					temp += "TeacherID = ";
					temp += classManagement.getTeacherId();
					temp += temp2;
					temp += "Class Name = ";
					temp += classManagement.getClassName();
					temp += temp2;
					temp += "Subject Name = ";
					temp += classManagement.getSubjectName();
					temp += temp2;
					temp += "Teacher Full Name = ";
					temp += classManagement.getTeacherFullName();
					// temp += temp2;
					temp += "<br/>";
					out.print(temp);
					// out.print("<br/>");
					
					/* 
					 
					out.print("ClassID = " + classManagement.getClassId() + ", Class Name = " + classManagement.getClassName() + 
							", SubjectID = " + classManagement.getSubjectId() + ", Subject Name = " + classManagement.getSubjectName()
							+ ", TeacherID = " + classManagement.getTeacherId() + ", Teacher Full Name = " + classManagement.getTeacherFullName());
					out.print("<br/>");
					
					*/
				}
				
				out.print("<b>Students for given classID: </b>");
				out.print("<br/>");
				
				for (Student student: studentReportList)
				{
					out.print("ID = " + student.getStudentId() + ", Fname = " + student.getStudentFname() + 
							", Lname = " + student.getStudentLname() + ", Assigned Class ID = " + student.getAssociatedcid());
					out.print("<br/>");
				}
				
			}catch (Exception e) 
			{
				// e.printStackTrace();
				out.print("Sorry, the application ran into an error");
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
