package qtone.xxt.dao.mapper.edu;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import qtone.xxt.dao.entity.edu.StudentEntry;
import qtone.xxt.dao.mapper.MyBatisMapper;
import qtone.xxt.dao.mapper.OrderItem;

/**
 * 
 * ѧ����Ϣ
 * @author Administrator
 *
 */
@Component("studentMapper")
public interface StudentMapper extends  MyBatisMapper {

	
	/**
	 * ��ѯѧ��
	 * @param id
	 * @return
	 */
	public StudentEntry findOne(@Param("areaAbb")String areaAbb,@Param("id")long id);
	
	
	/**
	 * ѧ���б��ѯ
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
	public List<StudentEntry> qeuryStudents(@Param("startRow")int startRow, @Param("pageSize")int pageSize,@Param("areaAbb")String areaAbb,
			@Param("schoolId")long schoolId, @Param("classId") long classId,
			@Param("stuName") String stuName,
			@Param("icNo") String icNo,
			@Param("className") String className,
			@Param("schoolName") String schoolName,
			@Param("stuSequence") String stuSequence,@Param("orderList")List<OrderItem>orderList);

	
	/**
	 * ѧ���б�ļ�¼����ͳ��
	 * @param areaAbb
	 * @param schoolId
	 * @param classId
	 * @param stuName
	 * @param icNo
	 * @param className
	 * @param famName
	 * @param phone
	 * @param stuSequence
	 * @return
	 */
	public int qeuryStudentsRecordCount(@Param("areaAbb")String areaAbb,
			@Param("schoolId")long schoolId, @Param("classId") long classId,
			@Param("stuName") String stuName,
			@Param("icNo") String icNo,
			@Param("className") String className,
			@Param("schoolName") String schoolName,
			@Param("stuSequence") String stuSequence);
	
	
	/**
	 * ��¼����
	 * @param student
	 * @return
	 */
	public int insertStudent(@Param("areaAbb")String areaAbb,@Param("student")StudentEntry student);
	
	
	/**
	 * ����ѧ��������Ϣ 
	 * @param student
	 * @return
	 */
	public int updateStudent(@Param("areaAbb")String areaAbb,@Param("student")StudentEntry student);
	
	
	/**
	 * ɾ��ѧ����Ϣ
	 * @param id
	 * @return
	 */
	public int deleteStudent(@Param("areaAbb")String areaAbb,@Param("ids") String[] ids);
	
	
	
	//�Զ��巽���ӿ�
	//************************************************************************************************************************
	//************************************************************************************************************************
	

	/**
	 * �����ݿ��л�ȡ�µ�ѧ����
	 * @param areaAbb  �������   "cs"
	 * @param areaCode ��������
	 * @return
	 */
	public String getNextStuSequence(@Param("areaAbb")String areaAbb,@Param("areaCode")String areaCode);
	
	
	
}
