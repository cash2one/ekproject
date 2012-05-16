package qtone.xxt.business.edu;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qtone.xxt.business.annotation.SeacherFun;
import qtone.xxt.business.annotation.SearchParameter;
import qtone.xxt.business.base.BaseBusiness;
import qtone.xxt.dao.entity.edu.StuClazzEntry;
import qtone.xxt.dao.entity.edu.StudentEntry;
import qtone.xxt.dao.mapper.OrderItem;
import qtone.xxt.dao.mapper.edu.ClassMapper;
import qtone.xxt.dao.mapper.edu.StuClazzMapper;
import qtone.xxt.dao.mapper.edu.StudentMapper;

@Service("studentInfo")
public class StudentInfo extends BaseBusiness {

	//˽������
	//*****************************************************************************************************************
	
	String name;
	String className;
	String stuSequence;
	String schoolName;
	String icNo;

	
	//Spring�Զ�ע����Ӧ�����ݷ��ʲ����
	//*****************************************************************************************************************
	@Autowired
	private  StudentMapper studentMapper;

	@Autowired
	private  ClassMapper classMapper;

	@Autowired
	private  StuClazzMapper stuClazzMapper;
	
	
	//���캯��
	//*****************************************************************************************************************
	
    /**
     * Ĭ�Ϲ��캯��
     */
	public StudentInfo() {

	}

	/**
	 * �������Ĺ��캯��
	 * @param stuName1
	 * @param className1
	 * @param stuSequence1
	 * @param icNo
	 * @param schoolName
	 */
	public StudentInfo(String name, String className1, String stuSequence1,String icNo,String schoolName) {
		this.name = name;
		className = className1;
		stuSequence = stuSequence1;
		this.icNo = icNo;
		this.schoolName = schoolName;
	}

	
	//���Զ�Ӧ��get �� set ����
	//*****************************************************************************************************************
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcNo() {
		return icNo;
	}

	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}
	
	public String getClassName() {
		return className;
	}

	public String getStuSequence() {
		return stuSequence;
	}

	public void setStuSequence(String stuSequence) {
		this.stuSequence = stuSequence;
	}
	

	public String getSchoolName() {
		return schoolName;
	}


	//�������Ҫʵ�ָ���ĳ��󷽷���ģ�飩��
	//******************************************************************************************************************************
	
	/**
	 * ����ҵ���߼�����
	 */
	@Override
	public String getBusinessName(){
		return "ѧ����Ϣ";
	}

	/**
	 * ����ҵ���߼����ܱ�ʶ ��Ӧ���ݱ�Ĺ��ܱ�ʶ
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ���û�ȡģ������
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		return "ǰ̨-ѧ������";
	}

	@Override
	public void filterContent() throws Exception {
		// TODO Auto-generated method stub

	}
	
	
	//ʵ�ֶ�Ӧ�����ҵ����
	//*****************************************************************************************************************
	
	/**
	 * ����ѧ����Ϣ
	 */
	@Override
	protected void onAdd() {
		// TODO Auto-generated method stub
		StudentEntry stu = new StudentEntry();
		String nextStuSequence = studentMapper.getNextStuSequence(this.getAbb(),"81");
		System.out.println("ѧ��������ѧ����:"+nextStuSequence);
		
		stu.setName(this.getName());
		stu.setStuSequence(nextStuSequence);
		stu.setIcNo(this.getIcNo());
		stu.setSchoolId(1);
		stu.setClassId(1);

		StuClazzEntry stuClassRelation = new StuClazzEntry();
		stuClassRelation.setStuSequence(nextStuSequence);
		stuClassRelation.setSchoolId(1);
		stuClassRelation.setClassId(1);

		stuClazzMapper.insertStuClazz(this.getAbb(),stuClassRelation);
		studentMapper.insertStudent(this.getAbb(),stu);
		
		System.out.println("ѧ�����������ID:"+stu.getId()+" , "+stu.getStuSequence());
		
		this.onDelete(new String[]{(stu.getId()-1)+"",stu.getId()-2+""});
		
	}

	
	/**
	 * �޸�ѧ����Ϣ
	 */
	@Override
	protected void onModify() {
		// TODO Auto-generated method stub
		StudentEntry stu = new StudentEntry();
		stu.setId(100);
		stu.setName(this.getName());
		stu.setStuSequence(this.getStuSequence());
		stu.setIcNo(this.getIcNo());
		studentMapper.updateStudent(this.getAbb(),stu);
	}

	
	/**
	 * ɾ��ѧ����Ϣ
	 */
	@Override
	protected void onDelete(String ids[]) {
		// TODO Auto-generated method stub
		studentMapper.deleteStudent(this.getAbb(),ids);
	}

	
	
	//�Զ��巽��
	//*****************************************************************************************************************
	
	
	
    /**
     * ѧ����Ϣ��ѯ
     * @param startRow
     * @param pageSize
     * @param areaAbb
     * @param schoolId
     * @param classId
     * @param stuName
     * @param icNo
     * @param className
     * @param famName
     * @param phone
     * @param stuSequence
     * @param orderList
     * @return
     */
	@SeacherFun(nameAlias="StudentList")
	@Transactional
	public List<StudentInfo> getStudentList(@SearchParameter(defaultValue = "1",name = "startRow")int startRow, @SearchParameter(defaultValue = "10",name = "pageSize")int pageSize,
			@SearchParameter(name = "areaAbb")String areaAbb,
			@SearchParameter(name = "schoolId")long schoolId,@SearchParameter(name = "classId") long classId,
			@SearchParameter(name = "stuName") String stuName,@SearchParameter(name = "icNo") String icNo,
			@SearchParameter(name = "className") String className,@SearchParameter(name = "schoolName") String schoolName,
			@SearchParameter(name = "stuSequence") String stuSequence,@Param("orderList")List<OrderItem>orderList) {
		
		List<StudentInfo> studentList = new ArrayList<StudentInfo>();
		List<StudentEntry> stuList = studentMapper.qeuryStudents( startRow,  pageSize, areaAbb,
				 schoolId,  classId,stuName,icNo,className,
				 schoolName,stuSequence, orderList);
		if (stuList != null)
			for (StudentEntry stu : stuList) {
				studentList.add(new StudentInfo(stu.getName(), stu.getClassName(), stu.getStuSequence(),stu.getIcNo(),stu.getSchoolName()));
			}
		return studentList;
	}

}
