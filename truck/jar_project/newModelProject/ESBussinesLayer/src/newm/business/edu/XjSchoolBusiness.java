package newm.business.edu;
 

import java.util.*;

import newm.dao.entity.edu.XjSchoolEntity;
import newm.dao.inter.edu.XjSchoolDao;
import newm.vo.edu.XjSchoolVo;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import esfw.core.framework.SpringUtil;
import esfw.core.framework.business.BaseBusiness;
import esfw.core.framework.business.BaseQuery;
import esfw.core.framework.business.enumeration.ActionType;
import esfw.core.framework.dao.PageBean;
import esfw.core.framework.exception.BusinessException;
import esfw.core.framework.exception.DaoAccessException;





/**
 *
 * @description 学校信息 对应的（业务逻辑类）
 * @version v1.0
 * @author Qtone  
 * @CreateTime Sun Jun 03 05:15:27 GMT 2012
 *
 */
@Scope("prototype") 
@Service("xjSchoolBusiness")
public class XjSchoolBusiness  extends BaseBusiness<Long> implements BaseQuery<XjSchoolBusiness,Long,XjSchoolVo>{

      //日志服务对象
      static Logger logger = Logger.getLogger(XjSchoolBusiness.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private XjSchoolEntity xjSchoolEntity;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public XjSchoolBusiness() {
	     xjSchoolEntity = new XjSchoolEntity();
	 }
	
     /**
     * 默认构造函数
     */
	 public XjSchoolBusiness(XjSchoolEntity entity) {
	      this.xjSchoolEntity = entity;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 学校ID
         */
         public void setId(long id){
	        this.xjSchoolEntity.setId(id);     
         }
        /**
         * @return id 学校ID
         */
         public long getId( ){ 
	        return this.xjSchoolEntity.getId( );   
         }
     
        /**
         * @param townId 所属镇区
         */
         public void setTownId(long townId){
	        this.xjSchoolEntity.setTownId(townId);     
         }
        /**
         * @return townId 所属镇区
         */
         public long getTownId( ){ 
	        return this.xjSchoolEntity.getTownId( );   
         }
     
        /**
         * @param schoolName 学校的名称
         */
         public void setSchoolName(String schoolName){
	        this.xjSchoolEntity.setSchoolName(schoolName);     
         }
        /**
         * @return schoolName 学校的名称
         */
         public String getSchoolName( ){ 
	        return this.xjSchoolEntity.getSchoolName( );   
         }
     
        /**
         * @param schoolType 学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
         */
         public void setSchoolType(long schoolType){
	        this.xjSchoolEntity.setSchoolType(schoolType);     
         }
        /**
         * @return schoolType 学校类别(1国家重点、2省重点、3市重点、4县重点、5少数民族学校、6其他)
         */
         public long getSchoolType( ){ 
	        return this.xjSchoolEntity.getSchoolType( );   
         }
     
        /**
         * @param establishDate 建校时间
         */
         public void setEstablishDate(Date establishDate){
	        this.xjSchoolEntity.setEstablishDate(establishDate);     
         }
        /**
         * @return establishDate 建校时间
         */
         public Date getEstablishDate( ){ 
	        return this.xjSchoolEntity.getEstablishDate( );   
         }
     
        /**
         * @param schoolMode 办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
         */
         public void setSchoolMode(long schoolMode){
	        this.xjSchoolEntity.setSchoolMode(schoolMode);     
         }
        /**
         * @return schoolMode 办学模式(1教育部门和集体办、2社会力量办、3其他部门办)
         */
         public long getSchoolMode( ){ 
	        return this.xjSchoolEntity.getSchoolMode( );   
         }
     
        /**
         * @param address 所在地（1城市、2农村、3县镇、4其他）
         */
         public void setAddress(long address){
	        this.xjSchoolEntity.setAddress(address);     
         }
        /**
         * @return address 所在地（1城市、2农村、3县镇、4其他）
         */
         public long getAddress( ){ 
	        return this.xjSchoolEntity.getAddress( );   
         }
     
        /**
         * @param schoolClass 学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点
5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校
10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学
13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设
16职业高中、17职业初中、18普通中学附设职业班、19其它单位
20学校职业班、21少数民职业初高中合设、22少数民族职业高中
23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班
27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班
32初中附设特教班、33工读学校、34普通中等专业技术学校、
35成人中等专业技术学校、36其他）
         */
         public void setSchoolClass(long schoolClass){
	        this.xjSchoolEntity.setSchoolClass(schoolClass);     
         }
        /**
         * @return schoolClass 学校类型（1小学、2独立设置少数民族小学、3一贯制学校小学部、4小学教学点
5其他学校附设小学班、6完全中学、7高级中学、8初级中学、9一贯制学校
10其他学校附设初中班、11少数民族完全中学、12少数民族高级中学
13少数民族初级中学、14少数民族一贯制学校、15职业初高中合设
16职业高中、17职业初中、18普通中学附设职业班、19其它单位
20学校职业班、21少数民职业初高中合设、22少数民族职业高中
23少数民族职业初中、24幼儿园、25独立少数民族幼儿园、26独立设置学前班
27盲人学校、28聋人学校、29弱智学校、30特殊教育其他学校、31小学附设特教班
32初中附设特教班、33工读学校、34普通中等专业技术学校、
35成人中等专业技术学校、36其他）
         */
         public long getSchoolClass( ){ 
	        return this.xjSchoolEntity.getSchoolClass( );   
         }
     
        /**
         * @param schoolBank 学校等级（1国家级、2省级、3市级、4县级、5其他）
         */
         public void setSchoolBank(long schoolBank){
	        this.xjSchoolEntity.setSchoolBank(schoolBank);     
         }
        /**
         * @return schoolBank 学校等级（1国家级、2省级、3市级、4县级、5其他）
         */
         public long getSchoolBank( ){ 
	        return this.xjSchoolEntity.getSchoolBank( );   
         }
     
        /**
         * @param schoolAddr 学校地址
         */
         public void setSchoolAddr(String schoolAddr){
	        this.xjSchoolEntity.setSchoolAddr(schoolAddr);     
         }
        /**
         * @return schoolAddr 学校地址
         */
         public String getSchoolAddr( ){ 
	        return this.xjSchoolEntity.getSchoolAddr( );   
         }
     
        /**
         * @param postCode 邮编
         */
         public void setPostCode(String postCode){
	        this.xjSchoolEntity.setPostCode(postCode);     
         }
        /**
         * @return postCode 邮编
         */
         public String getPostCode( ){ 
	        return this.xjSchoolEntity.getPostCode( );   
         }
     
        /**
         * @param phone 电话
         */
         public void setPhone(String phone){
	        this.xjSchoolEntity.setPhone(phone);     
         }
        /**
         * @return phone 电话
         */
         public String getPhone( ){ 
	        return this.xjSchoolEntity.getPhone( );   
         }
     
        /**
         * @param website 网址
         */
         public void setWebsite(String website){
	        this.xjSchoolEntity.setWebsite(website);     
         }
        /**
         * @return website 网址
         */
         public String getWebsite( ){ 
	        return this.xjSchoolEntity.getWebsite( );   
         }
     
        /**
         * @param email E_MAIL
         */
         public void setEmail(String email){
	        this.xjSchoolEntity.setEmail(email);     
         }
        /**
         * @return email E_MAIL
         */
         public String getEmail( ){ 
	        return this.xjSchoolEntity.getEmail( );   
         }
     
        /**
         * @param schoolMaster 校长
         */
         public void setSchoolMaster(String schoolMaster){
	        this.xjSchoolEntity.setSchoolMaster(schoolMaster);     
         }
        /**
         * @return schoolMaster 校长
         */
         public String getSchoolMaster( ){ 
	        return this.xjSchoolEntity.getSchoolMaster( );   
         }
     
        /**
         * @param mobie 联系电话
         */
         public void setMobie(String mobie){
	        this.xjSchoolEntity.setMobie(mobie);     
         }
        /**
         * @return mobie 联系电话
         */
         public String getMobie( ){ 
	        return this.xjSchoolEntity.getMobie( );   
         }
     
        /**
         * @param learnyear 学制(小学为6年，初高中为3年)
         */
         public void setLearnyear(long learnyear){
	        this.xjSchoolEntity.setLearnyear(learnyear);     
         }
        /**
         * @return learnyear 学制(小学为6年，初高中为3年)
         */
         public long getLearnyear( ){ 
	        return this.xjSchoolEntity.getLearnyear( );   
         }
     
        /**
         * @param areaId 地区id
         */
         public void setAreaId(long areaId){
	        this.xjSchoolEntity.setAreaId(areaId);     
         }
        /**
         * @return areaId 地区id
         */
         public long getAreaId( ){ 
	        return this.xjSchoolEntity.getAreaId( );   
         }
     
        /**
         * @param section 学段:0幼儿园，1小学，2初中，3高中，4综合
         */
         public void setSection(long section){
	        this.xjSchoolEntity.setSection(section);     
         }
        /**
         * @return section 学段:0幼儿园，1小学，2初中，3高中，4综合
         */
         public long getSection( ){ 
	        return this.xjSchoolEntity.getSection( );   
         }
     
        /**
         * @param createTime 创建这个学校的时间
         */
         public void setCreateTime(Date createTime){
	        this.xjSchoolEntity.setCreateTime(createTime);     
         }
        /**
         * @return createTime 创建这个学校的时间
         */
         public Date getCreateTime( ){ 
	        return this.xjSchoolEntity.getCreateTime( );   
         }
     
        /**
         * @param adcEcCode 对应BOSS系统集团编号
         */
         public void setAdcEcCode(String adcEcCode){
	        this.xjSchoolEntity.setAdcEcCode(adcEcCode);     
         }
        /**
         * @return adcEcCode 对应BOSS系统集团编号
         */
         public String getAdcEcCode( ){ 
	        return this.xjSchoolEntity.getAdcEcCode( );   
         }
     
        /**
         * @param schCode 
         */
         public void setSchCode(String schCode){
	        this.xjSchoolEntity.setSchCode(schCode);     
         }
        /**
         * @return schCode 
         */
         public String getSchCode( ){ 
	        return this.xjSchoolEntity.getSchCode( );   
         }
     
        /**
         * @param shortName 
         */
         public void setShortName(String shortName){
	        this.xjSchoolEntity.setShortName(shortName);     
         }
        /**
         * @return shortName 
         */
         public String getShortName( ){ 
	        return this.xjSchoolEntity.getShortName( );   
         }
     
        /**
         * @param isLong 
         */
         public void setIsLong(long isLong){
	        this.xjSchoolEntity.setIsLong(isLong);     
         }
        /**
         * @return isLong 
         */
         public long getIsLong( ){ 
	        return this.xjSchoolEntity.getIsLong( );   
         }
    
    
               
        /**
         * @param siId SIID
         */
         private void setSiId(long siId){
	        this.xjSchoolEntity.setSiId(siId);
         }
         
        /**
          * @return siId SIID
         */
         public long getSiId( ){ 
	        return this.xjSchoolEntity.getSiId( );     
         }
              
        /**
         * @param siName 公司名称
         */
         private void setSiName(String siName){
	        this.xjSchoolEntity.setSiName(siName);
         }
         
        /**
          * @return siName 公司名称
         */
         public String getSiName( ){ 
	        return this.xjSchoolEntity.getSiName( );     
         }
              
        /**
         * @param townName 镇区名称
         */
         private void setTownName(String townName){
	        this.xjSchoolEntity.setTownName(townName);
         }
         
        /**
          * @return townName 镇区名称
         */
         public String getTownName( ){ 
	        return this.xjSchoolEntity.getTownName( );     
         }
              
        /**
         * @param areaName 区域名称
         */
         private void setAreaName(String areaName){
	        this.xjSchoolEntity.setAreaName(areaName);
         }
         
        /**
          * @return areaName 区域名称
         */
         public String getAreaName( ){ 
	        return this.xjSchoolEntity.getAreaName( );     
         }
              
        /**
         * @param areaCode 区域代码
         */
         private void setAreaCode(String areaCode){
	        this.xjSchoolEntity.setAreaCode(areaCode);
         }
         
        /**
          * @return areaCode 区域代码
         */
         public String getAreaCode( ){ 
	        return this.xjSchoolEntity.getAreaCode( );     
         }
              
        /**
         * @param areaAbb 区域简写
         */
         private void setAreaAbb(String areaAbb){
	        this.xjSchoolEntity.setAreaAbb(areaAbb);
         }
         
        /**
          * @return areaAbb 区域简写
         */
         public String getAreaAbb( ){ 
	        return this.xjSchoolEntity.getAreaAbb( );     
         }
    
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "edu.XjSchoolBusiness";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "XjSchoolBusiness";
	}

	/**
	 * 设置获取模块名称
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		 return "edu";
	}

    /**
     *  数据验证接口
     *      注意：当进行记录新增、修改时 进行的数据校验操作,针对异常的数据或操作直接抛出异常，
     *      由外层处理
     * @param  type :  1.新增 ,2.修改 
     *
     */
	@Override
	protected void checkAndFilter(ActionType type) throws BusinessException {
		// TODO Auto-generated method stub
		String checkMsg = "";
		boolean isOk = true;
		if(type==ActionType.add){	        
		}
		if(type==ActionType.mdf){
		}
		if(!isOk)
		   throw new BusinessException(checkMsg,"执行操作时，数据验证不通过。");
	}
	
	
	
	//实现对应具体的业务功能
	//*****************************************************************************************************************
	
	
   
    /**
	 * 
	 * 功能描述：Load 根据主键 查询对应的记录
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#load(java.lang.Object)
	 * 
	 */
	public Boolean load(Long id) throws BusinessException {
	// TODO Auto-generated method stub
	 try {
		    XjSchoolDao xjSchoolDao= SpringUtil.getSpringBean(XjSchoolDao.class,"xjSchoolDao");
			XjSchoolEntity entity =  xjSchoolDao.load(id);
			if(entity!=null){
				this.xjSchoolEntity = entity;
				return true;
			}else{
				this.xjSchoolEntity = new XjSchoolEntity();
				// 没有对应的记录是，返回空集对象
				return false;
			}
		} catch (DaoAccessException ex) {
		    logger.error(getBusinessName()+" 执行记录  load 操作时出现异常",ex);
		    throw new BusinessException("查询记录详细发生异常",ex.getMessage(),ex);
		}
	}


	 
	
	
  
   	
   	
   	
	/**
	 * 新增记录（事务控制类型）
	 */
	@Override
	protected void onAdd() throws BusinessException {
		// TODO Auto-generated method stub

		   //打开事务控制
           DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		   def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		   DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		   TransactionStatus status = txManager.getTransaction(def);
		   try{
		      
		       XjSchoolDao xjSchoolDao= SpringUtil.getSpringBean(XjSchoolDao.class,"xjSchoolDao");
		       xjSchoolDao.insert(this.xjSchoolEntity);
		        
		       
		       //请实现业务新增的逻辑过程
		       
			   txManager.commit(status);		       
		       }catch(DaoAccessException ex){
		    	  logger.error(getBusinessName()+"执行记录新增后，进行业务提交时出现数据库异常",ex);
		    	  txManager.rollback(status); //事务回滚
		    	  throw new BusinessException("新增记录发生异常",ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
	
	
	/**
	 * 修改 （事务控制类型）
	 */
	@Override
	protected void onModify() throws BusinessException {
		// TODO Auto-generated method stub
		
        //打开事务控制
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		TransactionStatus status = txManager.getTransaction(def);
		try{
		    XjSchoolDao xjSchoolDao= SpringUtil.getSpringBean(XjSchoolDao.class,"xjSchoolDao");
			xjSchoolDao.update(this.xjSchoolEntity);
		     
				  
		    txManager.commit(status);	        
	        }catch(DaoAccessException ex){
		    	logger.error(getBusinessName()+"执行记录修改后，进行业务提交时出现数据库异常",ex);
		    	txManager.rollback(status); //事务回滚
		    	throw new BusinessException("修改记录详细信息发生异常",ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
	
	
    /**
     * 删除记录（事务控制类型）
     */
	@Override
      protected void onDelete(Long[] ids) throws BusinessException {
		// TODO Auto-generated method stub
		        
        //打开事务控制
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		TransactionStatus status = txManager.getTransaction(def);
		try{
	       XjSchoolDao xjSchoolDao= SpringUtil.getSpringBean(XjSchoolDao.class,"xjSchoolDao");
		   xjSchoolDao.delete(ids);
	        
		    txManager.commit(status);	        
	        }catch(DaoAccessException ex){
		    	logger.error(getBusinessName()+"执行记录删除后，进行业务提交时出现数据库异常",ex);
		    	txManager.rollback(status); //事务回滚
		    	throw new BusinessException("删除记录时发生异常",ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
   	

	


	/**
	 * 功能描述：列表查询(分页)
	 * @author Ethan.Lam
	 * @date 2012-5-31
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#query(java.io.Serializable)
	 */
	public List<XjSchoolBusiness> query(XjSchoolVo vo) throws BusinessException {
           // TODO Auto-generated method stub
        List<XjSchoolBusiness> results = new ArrayList<XjSchoolBusiness>();
		 try {
	            XjSchoolDao xjSchoolDao= SpringUtil.getSpringBean(XjSchoolDao.class,"xjSchoolDao");
			    PageBean<XjSchoolEntity> pageBean = xjSchoolDao.query(vo.getPageVo().getPage(),vo.getPageVo().getPageSize(),vo);
			    if(pageBean!=null && pageBean.getBeanList()!= null){
					for(XjSchoolEntity et:pageBean.getBeanList()){
						results.add(new XjSchoolBusiness(et));
					}
			        this.setQeuryRecordTotalNum(pageBean.getTotalRecords());
			    }
			} catch (DaoAccessException ex) {
			    logger.error(getBusinessName()+"执行记录查询操作时出现数据库异常",ex);
			    throw new BusinessException("查询记录发生异常",ex.getMessage(),ex);
			}
		return results;
	}

						

	
	
	
	//自定义方法
	//*****************************************************************************************************************
	
	
}
