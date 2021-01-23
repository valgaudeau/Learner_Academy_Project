package com.POJO;

public class Classes 
{
	private int cid;
	private String classesName;
	
	public Classes()
	{
		
	}	
	
	public Classes(int cid, String classesName) 
	{
		this.cid = cid;
		this.classesName = classesName;
	}

	public int getClassesId() { return cid; }
			
	public void setClassesId(int cid) { this.cid = cid; }		
	
	public String getClassesName() { return classesName; }		
	
	public void setClassesName(String classesName) { this.classesName = classesName; }		
	
}
