package qtone.xxt.dao.mapper.edu;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import qtone.xxt.dao.entity.edu.StudentEntry;
import qtone.xxt.dao.mapper.MyBatisMapper;
import qtone.xxt.dao.mapper.OrderItem;

/**
 * 
 * 学生信息
 * @author Administrator
 *
 */
@Component("studentMapper")
public interface StudentMapper extends  MyBatisMapper {

	
	/**
	 * 查询学生
	 * @param id
	 * @return
	 */
	public StudentEntry findOne(@Param("areaAbb")String areaAbb,@Param("id")long id);
	
	
	/**
	 * 学生列表查询
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
	 * 学生列表的记录总数统计
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
	 * 记录新增
	 * @param student
	 * @return
	 */
	public int insertStudent(@Param("areaAbb")String areaAbb,@Param("student")StudentEntry student);
	
	
	/**
	 * 更新学生基本信息 
	 * @param student
	 * @return
	 */
	public int updateStudent(@Param("areaAbb")String areaAbb,@Param("student")StudentEntry student);
	
	
	/**
	 * 删除学生信息
	 * @param id
	 * @return
	 */
	public int deleteStudent(@Param("areaAbb")String areaAbb,@Param("ids") String[] ids);
	
	
	
	//自定义方法接口
	//************************************************************************************************************************
	//************************************************************************************************************************
	

	/**
	 * 从数据库中获取新的学籍号
	 * @param areaAbb  地区简称   "cs"
	 * @param areaCode 地区代码
	 * @return
	 */
	public String getNextStuSequence(@Param("areaAbb")String areaAbb,@Param("areaCode")String areaCode);
	
	
	
}
