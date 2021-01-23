package com.POJO;

import java.util.ArrayList;

public class Teacher 
{
	private int teacherId;
	private String teacherFname;
	private String teacherLname;
	// private ArrayList<Classes> classesTaught = new ArrayList<Classes>();
	
	/*
	 *  Adding an empty constructor here. Allows us to construct an object that doesn't need parameters in the initialization
	 *  process. 
	 */
	
	public Teacher()
	{
		
	}
	
	public Teacher(int teacherId, String teacherFname, String teacherLname) 
	{
		super();
		this.teacherId = teacherId;
		this.teacherFname = teacherFname;
		this.teacherLname = teacherLname;
		// May need to change the below, don't want to create a new list each time?
	}

	public int getTeacherId() { return teacherId; }
		
	public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
		
	public String getTeacherFname() { return teacherFname; }
	
	public void setTeacherFname(String teacherFname) { this.teacherFname = teacherFname; }
	
	public String getTeacherLname() { return teacherLname; }
	
	public void setTeacherLname(String teacherLname) { this.teacherLname = teacherLname; }
	
	// public ArrayList<Classes> getClassesTaught() { return classesTaught; }
	
	// public void setClassesTaught(ArrayList<Classes> classesTaught) { this.classesTaught = classesTaught; }
		
}
