package test.edu.dao.mapper.edu;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import test.edu.dao.entity.edu.XjSchoolEntry;
import esfw.core.framework.dao.mapper.MyBatisMapper;
import esfw.core.framework.dao.mapper.OrderItem;

 

/**
 *
 * @description 学校信息 对应的Mapper（持久化接口类）
 * @version v1.0
 * @author Qtone  
 * @CreateTime Thu Jul 14 12:18:52 CST 2011
 *
 */
@Component("xjSchoolMapper")
public interface XjSchoolMapper extends MyBatisMapper {

    
     //自动生成的方法
	 //************************************************************************************************************************
    
   
	/**
	 * 根据主键查询对应的记录
	 * @param daoAbb    地区缩写（分表前缀名:如 “CS”）
	 * @param id  记录对应的主键
	 * @return XjSchoolEntry
	 */
	public XjSchoolEntry findOne(@Param("daoAbb")String daoAbb,@Param("id")long id);
	
	
	 /**
	   * 列表查询
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
	   * @param daoAbb    地区缩写（分表前缀名:如 “CS”）
     
	   * @param townId   所属镇区
	   * @param schoolName   学校的名称
	   * @param schoolType   学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
	   * @param establishDate1   建校时间 （大于或等于开始时间）
           * @param establishDate2   建校时间 （小于或等于结束时间）
	   * @param schoolMode   办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
	   * @param address   所在地（1城市、2农村、3县镇、4其他）
	   * @param schoolClass   学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设16职业高中、17职业初中、18普通中学附设职业班、19其它单位20学校职业班、21少数民职业初高中合设、22少数民族职业高中23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班32初中附设特教班、33工读学校、34普通中等专业技术学校、35成人中等专业技术学校、36其他）
	   * @param schoolBank   学校等级（1国家级、2省级、3市级、4县级、5其他）
	   * @param schoolAddr   学校地址
	   * @param postCode   邮编
	   * @param phone   电话
	   * @param website   网址
	   * @param email   E_MAIL
	   * @param schoolMaster   校长
	   * @param mobie   联系电话
	   * @param learnyear   学制(小学为6年，初高中为3年)
	   * @param areaId   地区id
	   * @param section   学段:0幼儿园，1小学，2初中，3高中，4综合
	   * @param createTime1   创建这个学校的时间 （大于或等于开始时间）
           * @param createTime2   创建这个学校的时间 （小于或等于结束时间）
	   * @param adcEcCode   对应BOSS系统集团编号
	   * @param schCode   
	   * @param shortName   
	   * @param isLong   
	   * @param siId   SIID
	   * @param siName   公司名称
	   * @param townName   镇区名称
	   * @param areaName   区域名称
	   * @param areaCode   区域代码
	   * @param areaAbb   区域简写
	   * @param orderList  //控制排序
	   * @return List<XjSchoolEntry>
	   */
	public List<XjSchoolEntry> qeuryXjSchools(@Param("startRow")int startRow, @Param("pageSize")int pageSize,@Param("daoAbb")String daoAbb,
				@Param("townId")long townId,@Param("schoolName")String schoolName,@Param("schoolType")long schoolType,@Param("establishDate1")Date establishDate1,@Param("establishDate2")Date establishDate2,@Param("schoolMode")long schoolMode,@Param("address")long address,@Param("schoolClass")long schoolClass,@Param("schoolBank")long schoolBank,@Param("schoolAddr")String schoolAddr,@Param("postCode")String postCode,@Param("phone")String phone,@Param("website")String website,@Param("email")String email,@Param("schoolMaster")String schoolMaster,@Param("mobie")String mobie,@Param("learnyear")long learnyear,@Param("areaId")long areaId,@Param("section")long section,@Param("createTime1")Date createTime1,@Param("createTime2")Date createTime2,@Param("adcEcCode")String adcEcCode,@Param("schCode")String schCode,@Param("shortName")String shortName,@Param("isLong")long isLong,@Param("siId")long siId,@Param("siName")String siName,@Param("townName")String townName,@Param("areaName")String areaName,@Param("areaCode")String areaCode,@Param("areaAbb")String areaAbb,@Param("orderList")List<OrderItem>orderList);


	/**
	 * 列表的记录总数统计
       

 	 * @param townId   所属镇区
 	 * @param schoolName   学校的名称
 	 * @param schoolType   学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
 	 * @param establishDate1   建校时间  （大于或等于开始时间）
         * @param establishDate2   建校时间 （小于或等于结束时间）
 	 * @param schoolMode   办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
 	 * @param address   所在地（1城市、2农村、3县镇、4其他）
 	 * @param schoolClass   学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设16职业高中、17职业初中、18普通中学附设职业班、19其它单位20学校职业班、21少数民职业初高中合设、22少数民族职业高中23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班32初中附设特教班、33工读学校、34普通中等专业技术学校、35成人中等专业技术学校、36其他）
 	 * @param schoolBank   学校等级（1国家级、2省级、3市级、4县级、5其他）
 	 * @param schoolAddr   学校地址
 	 * @param postCode   邮编
 	 * @param phone   电话
 	 * @param website   网址
 	 * @param email   E_MAIL
 	 * @param schoolMaster   校长
 	 * @param mobie   联系电话
 	 * @param learnyear   学制(小学为6年，初高中为3年)
 	 * @param areaId   地区id
 	 * @param section   学段:0幼儿园，1小学，2初中，3高中，4综合
 	 * @param createTime1   创建这个学校的时间  （大于或等于开始时间）
         * @param createTime2   创建这个学校的时间 （小于或等于结束时间）
 	 * @param adcEcCode   对应BOSS系统集团编号
 	 * @param schCode   
 	 * @param shortName   
 	 * @param isLong   
 	 * @param siId   SIID
 	 * @param siName   公司名称
 	 * @param townName   镇区名称
 	 * @param areaName   区域名称
 	 * @param areaCode   区域代码
 	 * @param areaAbb   区域简写
	 * @return 列表的记录数
	 */
	public int qeuryXjSchoolsRecordCount(@Param("daoAbb")String daoAbb,@Param("townId")long townId,@Param("schoolName")String schoolName,@Param("schoolType")long schoolType,@Param("establishDate1")Date establishDate1,@Param("establishDate2")Date establishDate2,@Param("schoolMode")long schoolMode,@Param("address")long address,@Param("schoolClass")long schoolClass,@Param("schoolBank")long schoolBank,@Param("schoolAddr")String schoolAddr,@Param("postCode")String postCode,@Param("phone")String phone,@Param("website")String website,@Param("email")String email,@Param("schoolMaster")String schoolMaster,@Param("mobie")String mobie,@Param("learnyear")long learnyear,@Param("areaId")long areaId,@Param("section")long section,@Param("createTime1")Date createTime1,@Param("createTime2")Date createTime2,@Param("adcEcCode")String adcEcCode,@Param("schCode")String schCode,@Param("shortName")String shortName,@Param("isLong")long isLong,@Param("siId")long siId,@Param("siName")String siName,@Param("townName")String townName,@Param("areaName")String areaName,@Param("areaCode")String areaCode,@Param("areaAbb")String areaAbb);
	
	
	/**
	 * 新增记录
	 
	 * @param xjSchool
	 * @return
	 */
	public int insertXjSchool( @Param("xjSchool")XjSchoolEntry xjSchool);
	
	
	
	/**
	 * 更新记录 
	 
	 * @param xjSchool
	 * @return
	 */
	public int updateXjSchool( @Param("xjSchool") XjSchoolEntry xjSchool);
    
	
	
	/**
	 * 删除记录
	 
	 * @param ids  记录对应的主键
	 * @return
	 */
	public int deleteXjSchool( @Param("ids") long[] ids);
	
	
	
	//自定义方法
	//************************************************************************************************************************
	
	
	
}

