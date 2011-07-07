package qtone.xxt.dao.entity.edu;

import java.io.Serializable;
/**
 * 
 * @author Ethan.Lam  2011-7-5
 *
 */
public class StudentEntry implements Serializable {

	private static final long serialVersionUID = -8937146012074873438L;

	private long id;

	private String stuSequence;

	private String name;

	private String icNo;
	
	
	//ÈßÓà×Ö¶Î
	private String className;
	
	private String schoolName;
	
	private long classId;
	
	private long schoolId;
	
	
	public StudentEntry(){
		
	}
	
	public StudentEntry(long id,String name,String stuSequence,String icNo,long schoolId,long classId){
        this.id = id;
        this.name = name;
        this.stuSequence = stuSequence;
        this.icNo = icNo;
        this.schoolId = schoolId;
        this.classId = classId;        
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStuSequence() {
		return stuSequence;
	}

	public void setStuSequence(String stuSequence) {
		this.stuSequence = stuSequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	private String stuNo;


	public String getIcNo() {
		return icNo;
	}

	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public long getClassId() {
		return classId;
	}

	public void setClassId(long classId) {
		this.classId = classId;
	}

	public long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

}