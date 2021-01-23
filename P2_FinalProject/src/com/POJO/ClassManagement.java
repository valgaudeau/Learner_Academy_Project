package com.POJO;

public class ClassManagement 
{
	private int classId;
	private String className;
	private int subjectId;
	private String subjectName;
	private int teacherId;
	private String teacherFullName;
	private int uniqueID;
	
	public ClassManagement()
	{
		
	}
	
	public ClassManagement(int classId, String className, int subjectId, String subjectName, int teacherId,
			String teacherFullName, int uniqueID) 
	{
		super();
		this.classId = classId;
		this.className = className;
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.teacherId = teacherId;
		this.teacherFullName = teacherFullName;
		this.uniqueID = uniqueID;
	}

	public int getClassId() { return classId; }
		
	public void setClassId(int classId) { this.classId = classId; }
		
	public String getClassName() { return className; }
		
	public void setClassName(String className) { this.className = className; }
		
	public int getSubjectId() { return subjectId; }

	public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
		
	public String getSubjectName() { return subjectName; }
	
	public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

	public int getTeacherId() { return teacherId; }
		
	public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
	
	public String getTeacherFullName() { return teacherFullName; }
    
	public void setTeacherFullName(String teacherFullName) { this.teacherFullName = teacherFullName; }
	
	public int getUniqueId() { return uniqueID; }
		
	public void setUniqueId(int uniqueID) { this.uniqueID = uniqueID; }
			
}
