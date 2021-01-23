package com.POJO;

import java.util.ArrayList;

public class Subject 
{
	private int subjectId;
	private String subjectName;
	// private ArrayList<Classes> classesWhereTaught = new ArrayList<Classes>();
	
	/*
	 *  Adding an empty constructor here. Allows us to construct an object that doesn't need parameters in the initialization
	 *  process. 
	 */
	
	public Subject()
	{
		
	}
	
	public Subject(int subjectId, String subjectName)
	{
		this.subjectId = subjectId;
		this.subjectName = subjectName;	
	}
	
	public int getSubjectId() { return subjectId; }
		
	public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
		
	public String getSubjectName() { return subjectName; }
		
	public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
		
	// public ArrayList<Classes> getClasses() { return classesWhereTaught; }
	
	// public void setClasses(ArrayList<Classes> classes) { this.classesWhereTaught = classes; }
	
}

