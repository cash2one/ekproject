package test.edu.bussiness;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import test.edu.dao.entity.edu.XjSchoolEntry;
import test.edu.dao.mapper.edu.XjSchoolMapper;
import esfw.core.framework.MethodAdapter;
import esfw.core.framework.SpringUtil;
import esfw.core.framework.annotation.SeacherFun;
import esfw.core.framework.annotation.SearchParameter;
import esfw.core.framework.business.BaseBusiness;
import esfw.core.framework.business.enumeration.ActionType;
import esfw.core.framework.dao.mapper.OrderItem;
import esfw.core.framework.exception.BusinessException;


/**
 *
 * @description 学校信息 对应的（业务逻辑类）
 * @version v1.0
 * @author Qtone  
 * @CreateTime Thu Jul 14 12:18:52 CST 2011
 *
 */
@Scope("prototype")
@Service("xjSchoolInfo")
public class XjSchoolInfo extends BaseBusiness {
    
      //日志服务对象
      static Logger logger = Logger.getLogger(XjSchoolInfo.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private XjSchoolEntry xjSchoolEntry;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public XjSchoolInfo() {
	     xjSchoolEntry = new XjSchoolEntry();
	 }
	
     /**
     * 默认构造函数
     */
	 public XjSchoolInfo(XjSchoolEntry entry) {
	      this.xjSchoolEntry = entry;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 学校ID
         */
         public void setId(long id){
	        this.xjSchoolEntry.setId(id);     
         }
        /**
         * @return id 学校ID
         */
         public long getId( ){ 
	        return this.xjSchoolEntry.getId( );   
         }
     
        /**
         * @param townId 所属镇区
         */
         public void setTownId(long townId){
	        this.xjSchoolEntry.setTownId(townId);     
         }
        /**
         * @return townId 所属镇区
         */
         public long getTownId( ){ 
	        return this.xjSchoolEntry.getTownId( );   
         }
     
        /**
         * @param schoolName 学校的名称
         */
         public void setSchoolName(String schoolName){
	        this.xjSchoolEntry.setSchoolName(schoolName);     
         }
        /**
         * @return schoolName 学校的名称
         */
         public String getSchoolName( ){ 
	        return this.xjSchoolEntry.getSchoolName( );   
         }
     
        /**
         * @param schoolType 学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
         */
         public void setSchoolType(long schoolType){
	        this.xjSchoolEntry.setSchoolType(schoolType);     
         }
        /**
         * @return schoolType 学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
         */
         public long getSchoolType( ){ 
	        return this.xjSchoolEntry.getSchoolType( );   
         }
     
        /**
         * @param establishDate 建校时间
         */
         public void setEstablishDate(Date establishDate){
	        this.xjSchoolEntry.setEstablishDate(establishDate);     
         }
        /**
         * @return establishDate 建校时间
         */
         public Date getEstablishDate( ){ 
	        return this.xjSchoolEntry.getEstablishDate( );   
         }
     
        /**
         * @param schoolMode 办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
         */
         public void setSchoolMode(long schoolMode){
	        this.xjSchoolEntry.setSchoolMode(schoolMode);     
         }
        /**
         * @return schoolMode 办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
         */
         public long getSchoolMode( ){ 
	        return this.xjSchoolEntry.getSchoolMode( );   
         }
     
        /**
         * @param address 所在地（1城市、2农村、3县镇、4其他）
         */
         public void setAddress(long address){
	        this.xjSchoolEntry.setAddress(address);     
         }
        /**
         * @return address 所在地（1城市、2农村、3县镇、4其他）
         */
         public long getAddress( ){ 
	        return this.xjSchoolEntry.getAddress( );   
         }
     
        /**
         * @param schoolClass 学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设16职业高中、17职业初中、18普通中学附设职业班、19其它单位20学校职业班、21少数民职业初高中合设、22少数民族职业高中23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班32初中附设特教班、33工读学校、34普通中等专业技术学校、35成人中等专业技术学校、36其他）
         */
         public void setSchoolClass(long schoolClass){
	        this.xjSchoolEntry.setSchoolClass(schoolClass);     
         }
        /**
         * @return schoolClass 学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设16职业高中、17职业初中、18普通中学附设职业班、19其它单位20学校职业班、21少数民职业初高中合设、22少数民族职业高中23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班32初中附设特教班、33工读学校、34普通中等专业技术学校、35成人中等专业技术学校、36其他）
         */
         public long getSchoolClass( ){ 
	        return this.xjSchoolEntry.getSchoolClass( );   
         }
     
        /**
         * @param schoolBank 学校等级（1国家级、2省级、3市级、4县级、5其他）
         */
         public void setSchoolBank(long schoolBank){
	        this.xjSchoolEntry.setSchoolBank(schoolBank);     
         }
        /**
         * @return schoolBank 学校等级（1国家级、2省级、3市级、4县级、5其他）
         */
         public long getSchoolBank( ){ 
	        return this.xjSchoolEntry.getSchoolBank( );   
         }
     
        /**
         * @param schoolAddr 学校地址
         */
         public void setSchoolAddr(String schoolAddr){
	        this.xjSchoolEntry.setSchoolAddr(schoolAddr);     
         }
        /**
         * @return schoolAddr 学校地址
         */
         public String getSchoolAddr( ){ 
	        return this.xjSchoolEntry.getSchoolAddr( );   
         }
     
        /**
         * @param postCode 邮编
         */
         public void setPostCode(String postCode){
	        this.xjSchoolEntry.setPostCode(postCode);     
         }
        /**
         * @return postCode 邮编
         */
         public String getPostCode( ){ 
	        return this.xjSchoolEntry.getPostCode( );   
         }
     
        /**
         * @param phone 电话
         */
         public void setPhone(String phone){
	        this.xjSchoolEntry.setPhone(phone);     
         }
        /**
         * @return phone 电话
         */
         public String getPhone( ){ 
	        return this.xjSchoolEntry.getPhone( );   
         }
     
        /**
         * @param website 网址
         */
         public void setWebsite(String website){
	        this.xjSchoolEntry.setWebsite(website);     
         }
        /**
         * @return website 网址
         */
         public String getWebsite( ){ 
	        return this.xjSchoolEntry.getWebsite( );   
         }
     
        /**
         * @param email E_MAIL
         */
         public void setEmail(String email){
	        this.xjSchoolEntry.setEmail(email);     
         }
        /**
         * @return email E_MAIL
         */
         public String getEmail( ){ 
	        return this.xjSchoolEntry.getEmail( );   
         }
     
        /**
         * @param schoolMaster 校长
         */
         public void setSchoolMaster(String schoolMaster){
	        this.xjSchoolEntry.setSchoolMaster(schoolMaster);     
         }
        /**
         * @return schoolMaster 校长
         */
         public String getSchoolMaster( ){ 
	        return this.xjSchoolEntry.getSchoolMaster( );   
         }
     
        /**
         * @param mobie 联系电话
         */
         public void setMobie(String mobie){
	        this.xjSchoolEntry.setMobie(mobie);     
         }
        /**
         * @return mobie 联系电话
         */
         public String getMobie( ){ 
	        return this.xjSchoolEntry.getMobie( );   
         }
     
        /**
         * @param learnyear 学制(小学为6年，初高中为3年)
         */
         public void setLearnyear(long learnyear){
	        this.xjSchoolEntry.setLearnyear(learnyear);     
         }
        /**
         * @return learnyear 学制(小学为6年，初高中为3年)
         */
         public long getLearnyear( ){ 
	        return this.xjSchoolEntry.getLearnyear( );   
         }
     
        /**
         * @param areaId 地区id
         */
         public void setAreaId(long areaId){
	        this.xjSchoolEntry.setAreaId(areaId);     
         }
        /**
         * @return areaId 地区id
         */
         public long getAreaId( ){ 
	        return this.xjSchoolEntry.getAreaId( );   
         }
     
        /**
         * @param section 学段:0幼儿园，1小学，2初中，3高中，4综合
         */
         public void setSection(long section){
	        this.xjSchoolEntry.setSection(section);     
         }
        /**
         * @return section 学段:0幼儿园，1小学，2初中，3高中，4综合
         */
         public long getSection( ){ 
	        return this.xjSchoolEntry.getSection( );   
         }
     
        /**
         * @param createTime 创建这个学校的时间
         */
         public void setCreateTime(Date createTime){
	        this.xjSchoolEntry.setCreateTime(createTime);     
         }
        /**
         * @return createTime 创建这个学校的时间
         */
         public Date getCreateTime( ){ 
	        return this.xjSchoolEntry.getCreateTime( );   
         }
     
        /**
         * @param adcEcCode 对应BOSS系统集团编号
         */
         public void setAdcEcCode(String adcEcCode){
	        this.xjSchoolEntry.setAdcEcCode(adcEcCode);     
         }
        /**
         * @return adcEcCode 对应BOSS系统集团编号
         */
         public String getAdcEcCode( ){ 
	        return this.xjSchoolEntry.getAdcEcCode( );   
         }
     
        /**
         * @param schCode 
         */
         public void setSchCode(String schCode){
	        this.xjSchoolEntry.setSchCode(schCode);     
         }
        /**
         * @return schCode 
         */
         public String getSchCode( ){ 
	        return this.xjSchoolEntry.getSchCode( );   
         }
     
        /**
         * @param shortName 
         */
         public void setShortName(String shortName){
	        this.xjSchoolEntry.setShortName(shortName);     
         }
        /**
         * @return shortName 
         */
         public String getShortName( ){ 
	        return this.xjSchoolEntry.getShortName( );   
         }
     
        /**
         * @param isLong 
         */
         public void setIsLong(long isLong){
	        this.xjSchoolEntry.setIsLong(isLong);     
         }
        /**
         * @return isLong 
         */
         public long getIsLong( ){ 
	        return this.xjSchoolEntry.getIsLong( );   
         }
    
    
               
        /**
         * @param siId SIID
         */
         private void setSiId(long siId){
	        this.xjSchoolEntry.setSiId(siId);
         }
         
        /**
          * @return siId SIID
         */
         public long getSiId( ){ 
	        return this.xjSchoolEntry.getSiId( );     
         }
              
        /**
         * @param siName 公司名称
         */
         private void setSiName(String siName){
	        this.xjSchoolEntry.setSiName(siName);
         }
         
        /**
          * @return siName 公司名称
         */
         public String getSiName( ){ 
	        return this.xjSchoolEntry.getSiName( );     
         }
              
        /**
         * @param townName 镇区名称
         */
         private void setTownName(String townName){
	        this.xjSchoolEntry.setTownName(townName);
         }
         
        /**
          * @return townName 镇区名称
         */
         public String getTownName( ){ 
	        return this.xjSchoolEntry.getTownName( );     
         }
              
        /**
         * @param areaName 区域名称
         */
         private void setAreaName(String areaName){
	        this.xjSchoolEntry.setAreaName(areaName);
         }
         
        /**
          * @return areaName 区域名称
         */
         public String getAreaName( ){ 
	        return this.xjSchoolEntry.getAreaName( );     
         }
              
        /**
         * @param areaCode 区域代码
         */
         private void setAreaCode(String areaCode){
	        this.xjSchoolEntry.setAreaCode(areaCode);
         }
         
        /**
          * @return areaCode 区域代码
         */
         public String getAreaCode( ){ 
	        return this.xjSchoolEntry.getAreaCode( );     
         }
              
        /**
         * @param areaAbb 区域简写
         */
         private void setAreaAbb(String areaAbb){
	        this.xjSchoolEntry.setAreaAbb(areaAbb);
         }
         
        /**
          * @return areaAbb 区域简写
         */
         public String getAreaAbb( ){ 
	        return this.xjSchoolEntry.getAreaAbb( );     
         }
    
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
	  return "学校信息";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		return "";
	}

	/**
	 * 设置获取模块名称
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		return "";
	}

 
	
	
	
	//实现对应具体的业务功能
	//*****************************************************************************************************************
	
	
	/**
	 * 新增
	 */
	@Override
	protected void onAdd() throws BusinessException {
		// TODO Auto-generated method stub
		//检查重名
		Map<String,Object> params  = new HashMap<String,Object>();
		   params.put("schoolName", this.getSchoolName());
		   params.put("areaAbb", this.getDaoAbb());
		   List<XjSchoolInfo> xjSchool=(List<XjSchoolInfo>) MethodAdapter.invoker(this, "qeuryXjSchools", params);
		   if(xjSchool!=null&&xjSchool.size()>0)
			   throw new BusinessException("此地区已经存在同名学校，不可增加");
		
	       XjSchoolEntry xjSchoolEntry = new XjSchoolEntry();
		    
	       xjSchoolEntry.setId(this.getId()); 
	       xjSchoolEntry.setTownId(this.getTownId()); 
	       xjSchoolEntry.setSchoolName(this.getSchoolName()); 
	       xjSchoolEntry.setSchoolType(this.getSchoolType()); 
	       xjSchoolEntry.setEstablishDate(this.getEstablishDate()); 
	       xjSchoolEntry.setSchoolMode(this.getSchoolMode()); 
	       xjSchoolEntry.setAddress(this.getAddress()); 
	       xjSchoolEntry.setSchoolClass(this.getSchoolClass()); 
	       xjSchoolEntry.setSchoolBank(this.getSchoolBank()); 
	       xjSchoolEntry.setSchoolAddr(this.getSchoolAddr()); 
	       xjSchoolEntry.setPostCode(this.getPostCode()); 
	       xjSchoolEntry.setPhone(this.getPhone()); 
	       xjSchoolEntry.setWebsite(this.getWebsite()); 
	       xjSchoolEntry.setEmail(this.getEmail()); 
	       xjSchoolEntry.setSchoolMaster(this.getSchoolMaster()); 
	       xjSchoolEntry.setMobie(this.getMobie()); 
	       xjSchoolEntry.setLearnyear(this.getLearnyear()); 
	       xjSchoolEntry.setAreaId(this.getAreaId()); 
	       xjSchoolEntry.setSection(this.getSection()); 
	       xjSchoolEntry.setCreateTime(this.getCreateTime()); 
	       xjSchoolEntry.setAdcEcCode(this.getAdcEcCode()); 
	       xjSchoolEntry.setSchCode(this.getSchCode()); 
	       xjSchoolEntry.setShortName(this.getShortName()); 
	       xjSchoolEntry.setIsLong(this.getIsLong());
	       XjSchoolMapper xjSchoolMapper= SpringUtil.getSpringBean(XjSchoolMapper.class,"xjSchoolMapper");
	       xjSchoolMapper.insertXjSchool(xjSchoolEntry);
	       xjSchoolEntry = null;
	}

	
   
    /**
     * 根据主键（id）返回单条记录
     * @param id
     * @return  XjSchoolInfo
     */
	public XjSchoolInfo findOne(long  id)  throws BusinessException {
	    XjSchoolMapper xjSchoolMapper= SpringUtil.getSpringBean(XjSchoolMapper.class,"xjSchoolMapper");
		XjSchoolEntry entry = xjSchoolMapper.findOne(this.getDaoAbb(),id);
		if (entry != null){
	           this.setId(entry.getId());
	           this.setTownId(entry.getTownId());
	           this.setSchoolName(entry.getSchoolName());
	           this.setSchoolType(entry.getSchoolType());
	           this.setEstablishDate(entry.getEstablishDate());
	           this.setSchoolMode(entry.getSchoolMode());
	           this.setAddress(entry.getAddress());
	           this.setSchoolClass(entry.getSchoolClass());
	           this.setSchoolBank(entry.getSchoolBank());
	           this.setSchoolAddr(entry.getSchoolAddr());
	           this.setPostCode(entry.getPostCode());
	           this.setPhone(entry.getPhone());
	           this.setWebsite(entry.getWebsite());
	           this.setEmail(entry.getEmail());
	           this.setSchoolMaster(entry.getSchoolMaster());
	           this.setMobie(entry.getMobie());
	           this.setLearnyear(entry.getLearnyear());
	           this.setAreaId(entry.getAreaId());
	           this.setSection(entry.getSection());
	           this.setCreateTime(entry.getCreateTime());
	           this.setAdcEcCode(entry.getAdcEcCode());
	           this.setSchCode(entry.getSchCode());
	           this.setShortName(entry.getShortName());
	           this.setIsLong(entry.getIsLong());
               this.setSiId(entry.getSiId());
               this.setSiName(entry.getSiName());
               this.setTownName(entry.getTownName());
               this.setAreaName(entry.getAreaName());
               this.setAreaCode(entry.getAreaCode());
               this.setAreaAbb(entry.getAreaAbb());
               entry = null;
		}else{
			this.xjSchoolEntry = new XjSchoolEntry();
		}
		return this;
	}
	 
	
	
	
	/**
	 * 修改
	 */
	@Override
	protected void onModify() throws BusinessException {
		// TODO Auto-generated method stub
		//检查重名
		Map<String,Object> params  = new HashMap<String,Object>();
		   params.put("schoolName", this.getSchoolName());
		   params.put("areaAbb", this.getDaoAbb());
		   List<XjSchoolInfo> xjSchool=(List<XjSchoolInfo>) MethodAdapter.invoker(this, "qeuryXjSchools", params);
		   if(xjSchool!=null&&xjSchool.size()>0)
			   throw new BusinessException("此地区已经存在同名学校，不可增加");
		XjSchoolEntry xjSchoolEntry = new XjSchoolEntry();
	     
	        xjSchoolEntry.setId(this.getId()); 
	        xjSchoolEntry.setTownId(this.getTownId()); 
	        xjSchoolEntry.setSchoolName(this.getSchoolName()); 
	        xjSchoolEntry.setSchoolType(this.getSchoolType()); 
	        xjSchoolEntry.setEstablishDate(this.getEstablishDate()); 
	        xjSchoolEntry.setSchoolMode(this.getSchoolMode()); 
	        xjSchoolEntry.setAddress(this.getAddress()); 
	        xjSchoolEntry.setSchoolClass(this.getSchoolClass()); 
	        xjSchoolEntry.setSchoolBank(this.getSchoolBank()); 
	        xjSchoolEntry.setSchoolAddr(this.getSchoolAddr()); 
	        xjSchoolEntry.setPostCode(this.getPostCode()); 
	        xjSchoolEntry.setPhone(this.getPhone()); 
	        xjSchoolEntry.setWebsite(this.getWebsite()); 
	        xjSchoolEntry.setEmail(this.getEmail()); 
	        xjSchoolEntry.setSchoolMaster(this.getSchoolMaster()); 
	        xjSchoolEntry.setMobie(this.getMobie()); 
	        xjSchoolEntry.setLearnyear(this.getLearnyear()); 
	        xjSchoolEntry.setAreaId(this.getAreaId()); 
	        xjSchoolEntry.setSection(this.getSection()); 
	        xjSchoolEntry.setCreateTime(this.getCreateTime()); 
	        xjSchoolEntry.setAdcEcCode(this.getAdcEcCode()); 
	        xjSchoolEntry.setSchCode(this.getSchCode()); 
	        xjSchoolEntry.setShortName(this.getShortName()); 
	        xjSchoolEntry.setIsLong(this.getIsLong());
	    XjSchoolMapper xjSchoolMapper= SpringUtil.getSpringBean(XjSchoolMapper.class,"xjSchoolMapper");
		xjSchoolMapper.updateXjSchool(xjSchoolEntry);
	    xjSchoolEntry = null;
	}

	
	/**
	 * 删除
	 */
	@Override
	protected void onDelete(long ids[]) throws BusinessException {
		// TODO Auto-generated method stub
		XjSchoolMapper xjSchoolMapper= SpringUtil.getSpringBean(XjSchoolMapper.class,"xjSchoolMapper");
		xjSchoolMapper.deleteXjSchool(ids);
	}

	
	  /**
	   * 查询
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
     
	   * @param   townId   所属镇区
	   * @param   schoolName   学校的名称
	   * @param   schoolType   学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
	   * @param   establishDate1   建校时间 （大于或等于开始时间）
           * @param   establishDate2   建校时间 （小于或等于结束时间）
	   * @param   schoolMode   办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
	   * @param   address   所在地（1城市、2农村、3县镇、4其他）
	   * @param   schoolClass   学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设16职业高中、17职业初中、18普通中学附设职业班、19其它单位20学校职业班、21少数民职业初高中合设、22少数民族职业高中23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班32初中附设特教班、33工读学校、34普通中等专业技术学校、35成人中等专业技术学校、36其他）
	   * @param   schoolBank   学校等级（1国家级、2省级、3市级、4县级、5其他）
	   * @param   schoolAddr   学校地址
	   * @param   postCode   邮编
	   * @param   phone   电话
	   * @param   website   网址
	   * @param   email   E_MAIL
	   * @param   schoolMaster   校长
	   * @param   mobie   联系电话
	   * @param   learnyear   学制(小学为6年，初高中为3年)
	   * @param   areaId   地区id
	   * @param   section   学段:0幼儿园，1小学，2初中，3高中，4综合
	   * @param   createTime1   创建这个学校的时间 （大于或等于开始时间）
           * @param   createTime2   创建这个学校的时间 （小于或等于结束时间）
	   * @param   adcEcCode   对应BOSS系统集团编号
	   * @param   schCode   
	   * @param   shortName   
	   * @param   isLong   
	   * @param   siId   SIID
	   * @param   siName   公司名称
	   * @param   townName   镇区名称
	   * @param   areaName   区域名称
	   * @param   areaCode   区域代码
	   * @param   areaAbb   区域简写
	   * @param orderList  //控制排序
	   * @return List<XjSchoolInfo>
	   */
	@SeacherFun(nameAlias="XjSchoolInfoSeacher")
	public List<XjSchoolInfo> qeuryXjSchools(@SearchParameter(defaultValue = "1",name = "startRow")int startRow, @SearchParameter(defaultValue = "20",name = "pageSize")int pageSize,
				@SearchParameter(name ="townId", defaultValue="-1" )long townId,@SearchParameter(name ="schoolName")String schoolName,@SearchParameter(name ="schoolType", defaultValue="-1" )long schoolType,@SearchParameter(name ="establishDate1")Date establishDate1,@SearchParameter(name ="establishDate2")Date establishDate2,@SearchParameter(name ="schoolMode", defaultValue="-1" )long schoolMode,@SearchParameter(name ="address", defaultValue="-1" )long address,@SearchParameter(name ="schoolClass", defaultValue="-1" )long schoolClass,@SearchParameter(name ="schoolBank", defaultValue="-1" )long schoolBank,@SearchParameter(name ="schoolAddr")String schoolAddr,@SearchParameter(name ="postCode")String postCode,@SearchParameter(name ="phone")String phone,@SearchParameter(name ="website")String website,@SearchParameter(name ="email")String email,@SearchParameter(name ="schoolMaster")String schoolMaster,@SearchParameter(name ="mobie")String mobie,@SearchParameter(name ="learnyear", defaultValue="-1" )long learnyear,@SearchParameter(name ="areaId", defaultValue="-1" )long areaId,@SearchParameter(name ="section", defaultValue="-1" )long section,@SearchParameter(name ="createTime1")Date createTime1,@SearchParameter(name ="createTime2")Date createTime2,@SearchParameter(name ="adcEcCode")String adcEcCode,@SearchParameter(name ="schCode")String schCode,@SearchParameter(name ="shortName")String shortName,@SearchParameter(name ="isLong", defaultValue="-1" )long isLong,@SearchParameter(name ="siId", defaultValue="-1" )long siId,@SearchParameter(name ="siName")String siName,@SearchParameter(name ="townName")String townName,@SearchParameter(name ="areaName")String areaName,@SearchParameter(name ="areaCode")String areaCode,@SearchParameter(name ="areaAbb")String areaAbb,
				@SearchParameter(name="orderList")List<OrderItem>orderList) throws BusinessException{
		   //实例化List对象		
		   List<XjSchoolInfo> list = new ArrayList<XjSchoolInfo>();
		   //查询结果实体
		   XjSchoolMapper xjSchoolMapper= SpringUtil.getSpringBean(XjSchoolMapper.class,"xjSchoolMapper");
		   this.setQeuryRecordTotalNum(xjSchoolMapper.qeuryXjSchoolsRecordCount(this.getDaoAbb(),townId,schoolName,schoolType,establishDate1,establishDate2,schoolMode,address,schoolClass,schoolBank,schoolAddr,postCode,phone,website,email,schoolMaster,mobie,learnyear,areaId,section,createTime1,createTime2,adcEcCode,schCode,shortName,isLong,siId,siName,townName,areaName,areaCode,areaAbb));
		   List<XjSchoolEntry> entryList = xjSchoolMapper.qeuryXjSchools(startRow,pageSize,this.getDaoAbb(),townId,schoolName,schoolType,establishDate1,establishDate2,schoolMode,address,schoolClass,schoolBank,schoolAddr,postCode,phone,website,email,schoolMaster,mobie,learnyear,areaId,section,createTime1,createTime2,adcEcCode,schCode,shortName,isLong,siId,siName,townName,areaName,areaCode,areaAbb,orderList);
	       if (entryList != null){
			  for (XjSchoolEntry entry : entryList) {
				   list.add(new XjSchoolInfo(entry));
				   entry = null;
			  }
			  entryList = null;
		   }
		return list;
	}
				
			

	
	
	
	//自定义方法
	//*****************************************************************************************************************
	
	 
	@Override
	protected void checkAndFilter(ActionType type) throws BusinessException {
		// TODO Auto-generated method stub
		
	}
	
}

