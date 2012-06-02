package qtone.xxt.dao.mapper.amass;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import esfw.core.framework.dao.mapper.MyBatisMapper;
import esfw.core.framework.dao.mapper.OrderItem;
import esfw.core.framework.exception.DaoAccessException;

import qtone.xxt.dao.entity.amass.AmassCentEntry;


/**
 *
 * @description 教师积分 信息 对应的Mapper（持久化接口类）
 * @version v1.0
 * @author Qtone  
 * @CreateTime Fri May 18 09:41:49 CST 2012
 *
 */
@Component("amassCentMapper")
public interface AmassCentMapper extends MyBatisMapper {

    
     //自动生成的方法
	 //************************************************************************************************************************
    
   
	/**
	 * 根据主键查询对应的记录
	 * @param daoAbb    地区缩写（分表前缀名:如 “CS”）
	 * @param id  记录对应的主键
	 * @return AmassCentEntry
	 */
	public AmassCentEntry findOne(@Param("daoAbb")String daoAbb,@Param("id")long id) throws DaoAccessException;
	
	
	 /**
	   * 列表查询
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
	   * @param daoAbb    地区缩写（分表前缀名:如 “CS”）
     
	   * @param areaId   
	   * @param userId   教师帐号
	   * @param userName   教师名称
	   * @param siId   SiId
	   * @param schoolId   学校ID
	   * @param schoolName   学校名
	   * @param year   年
	   * @param month   月
	   * @param cent   该月的积分
	   * @param isLeader   表am_cent里面得is_leader=2为计算出来得积分 汇总核算教师该月获得总积分
	   * @param insertuserid   
	   * @param dt1   创建时间 （大于或等于开始时间）
           * @param dt2   创建时间 （小于或等于结束时间）
	   * @param amhId   
	   * @param isConfirm   是否是有效积分
	   * @param areaName   区域名称
	   * @param company   SI公司名称
	   * @param orderList  //控制排序
	   * @return List<AmassCentEntry>
	   */
	public List<AmassCentEntry> qeuryAmassCents(@Param("startRow")int startRow, @Param("pageSize")int pageSize,@Param("daoAbb")String daoAbb,
				@Param("areaId")long areaId,@Param("userId")String userId,@Param("userName")String userName,@Param("siId")long siId,@Param("schoolId")long schoolId,@Param("schoolName")String schoolName,@Param("year")long year,@Param("month")long month,@Param("cent")long cent,@Param("isLeader")long isLeader,@Param("insertuserid")String insertuserid,@Param("dt1")Date dt1,@Param("dt2")Date dt2,@Param("amhId")long amhId,@Param("isConfirm")long isConfirm,@Param("areaName")String areaName,@Param("company")String company,@Param("orderList")List<OrderItem>orderList) throws DaoAccessException;


	/**
	 * 列表的记录总数统计
       

 	 * @param areaId   
 	 * @param userId   教师帐号
 	 * @param userName   教师名称
 	 * @param siId   SiId
 	 * @param schoolId   学校ID
 	 * @param schoolName   学校名
 	 * @param year   年
 	 * @param month   月
 	 * @param cent   该月的积分
 	 * @param isLeader   表am_cent里面得is_leader=2为计算出来得积分 汇总核算教师该月获得总积分
 	 * @param insertuserid   
 	 * @param dt1   创建时间  （大于或等于开始时间）
         * @param dt2   创建时间 （小于或等于结束时间）
 	 * @param amhId   
 	 * @param isConfirm   是否是有效积分
 	 * @param areaName   区域名称
 	 * @param company   SI公司名称
	 * @return 列表的记录数
	 */
	public int qeuryAmassCentsRecordCount(@Param("daoAbb")String daoAbb,@Param("areaId")long areaId,@Param("userId")String userId,@Param("userName")String userName,@Param("siId")long siId,@Param("schoolId")long schoolId,@Param("schoolName")String schoolName,@Param("year")long year,@Param("month")long month,@Param("cent")long cent,@Param("isLeader")long isLeader,@Param("insertuserid")String insertuserid,@Param("dt1")Date dt1,@Param("dt2")Date dt2,@Param("amhId")long amhId,@Param("isConfirm")long isConfirm,@Param("areaName")String areaName,@Param("company")String company) throws DaoAccessException ;
	
	
	/**
	 * 新增记录
	 
	 * @param amassCent
	 * @return
	 */
	public int insertAmassCent( @Param("amassCent")AmassCentEntry amassCent) throws DaoAccessException;
	
	
	
	/**
	 * 更新记录 
	 
	 * @param amassCent
	 * @return
	 */
	public int updateAmassCent( @Param("amassCent") AmassCentEntry amassCent) throws DaoAccessException;
    
	
	
	/**
	 * 删除记录
	 
	 * @param ids  记录对应的主键
	 * @return
	 */
	public int deleteAmassCent( @Param("ids") Long[] ids) throws DaoAccessException ;
	
	
	
	//自定义方法
	//************************************************************************************************************************
	
	
	
}

