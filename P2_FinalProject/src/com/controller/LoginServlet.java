package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.POJO.Admin;
import com.dao.AdminDAO;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// First, we capture the username and password which are entered by the user
		String usernameInput = request.getParameter("usernameinput");
		String passwordInput = request.getParameter("passwordinput");
		
		// Now we need to retrieve the username & password entry from the database and compare it to what the user entered
		AdminDAO adminDAO = new AdminDAO();
		Admin admin = adminDAO.getAdminInfoFromDB();
			
		RequestDispatcher rd = null;
		
		if (usernameInput.equals(admin.getUsername()) && passwordInput.equals(admin.getPassword()))
		{
			rd = request.getRequestDispatcher("index.html"); 
			rd.include(request, response);   // this is needed for the Servlet to respond to the client
		}
		else
		{
			rd = request.getRequestDispatcher("LoginPage.html");
			PrintWriter out = response.getWriter();
			rd.include(request, response);
			out.println("<span style='color:red'>Invalid Username or Password!</span>");
			
			/*
			 * out.println("input" +" " + usernameInput + " " + passwordInput);
			 * out.println("stored" + " " + admin.getUsername() + " " + admin.getPassword());
			 */
			
		}	
	}
}
