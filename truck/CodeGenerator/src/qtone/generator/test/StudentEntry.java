package qtone.generator.test;

import java.io.Serializable;

/**
 * 
 * @description StudentEntity
 * @version 1.0
 * @author xxt Tue Jul 05 14:59:56 CST 2011
 * 
 */
public class StudentEntry implements Serializable {

	private long id; // ����id

	private String stuSequence; // ѧ����

	private String name; // ѧ������

	private String icNo; // ѧ��

	private long classId; // �༶ID

	private String className; // �༶����

	private long schoolId; // ѧУID

	private String schoolName; // ѧУ����

	// Ĭ�Ͽչ��캯��
	public StudentEntry() {

	}

	// �������Ĺ��캯��
	public StudentEntry(long id, String stuSequence, String name, String icNo,
			long classId, String className, long schoolId, String schoolName) {
		this.id = id;
		this.stuSequence = stuSequence;
		this.name = name;
		this.icNo = icNo;
		this.classId = classId;
		this.className = className;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setStuSequence(String stuSequence) {
		this.stuSequence = stuSequence;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}

	public void setClassId(long classId) {
		this.classId = classId;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}