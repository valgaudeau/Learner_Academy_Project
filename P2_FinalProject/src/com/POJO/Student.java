package com.POJO;

public class Student 
{
	private int studentId;
	private String studentFname;
	private String studentLname;
	private int associatedcid;
	// private Classes classAttended;
	
	public Student()
	{
		
	}
	
	public Student(int studentId, String studentFname, String studentLname, int associatedcid) 
	{
		this.studentId = studentId;
		this.studentFname = studentFname;
		this.studentLname = studentLname;
		this.associatedcid = associatedcid;
		// this.classAttended = classAttended;
	}
	
	public int getStudentId() { return studentId; }
	
	public void setStudentId(int studentId) { this.studentId = studentId; }
	
	public String getStudentFname() { return studentFname; }
	
	public void setStudentFname(String studentFname) { this.studentFname = studentFname; }
	
	public String getStudentLname() { return studentLname; }
			
	public void setStudentLname(String studentLname) { this.studentLname = studentLname; }
	
	public int getAssociatedcid() { return associatedcid; }
	
	public void setAssociatedcid(int associatedcid) { this.associatedcid = associatedcid; }
			
	// public Classes getClassAttended() { return classAttended; }
			 
	// public void setClassAttended(Classes classAttended) { this.classAttended = classAttended; }
	
}