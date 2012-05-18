package qtone.xxt.business.amass;
 
import java.util.*;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;


import org.apache.log4j.Logger;
import esfw.core.SpringUtil;
import esfw.core.annotation.SeacherFun;
import esfw.core.annotation.SearchParameter;
import esfw.core.business.BaseBusiness;
import esfw.core.business.enumeration.ActionType;
import esfw.core.dao.mapper.OrderItem;
import esfw.core.exception.BusinessException;
import esfw.core.exception.DaoAccessException;
import esfw.core.dao.mapper.OrderItem;

import qtone.xxt.dao.entity.amass.AmassCentEntry;
import qtone.xxt.dao.mapper.amass.AmassCentMapper;


/**
 *
 * @description 教师积分 信息 对应的（业务逻辑类）
 * @version v1.0
 * @author Qtone  
 * @CreateTime Fri May 18 09:41:49 CST 2012
 *
 */
@Scope("prototype") 
@Service("amassCentInfo")
public class AmassCentInfo extends BaseBusiness {
    
      //日志服务对象
      static Logger logger = Logger.getLogger(AmassCentInfo.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private AmassCentEntry amassCentEntry;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public AmassCentInfo() {
	     amassCentEntry = new AmassCentEntry();
	 }
	
     /**
     * 默认构造函数
     */
	 public AmassCentInfo(AmassCentEntry entry) {
	      this.amassCentEntry = entry;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 
         */
         public void setId(long id){
	        this.amassCentEntry.setId(id);     
         }
        /**
         * @return id 
         */
         public long getId( ){ 
	        return this.amassCentEntry.getId( );   
         }
     
        /**
         * @param areaId 
         */
         public void setAreaId(long areaId){
	        this.amassCentEntry.setAreaId(areaId);     
         }
        /**
         * @return areaId 
         */
         public long getAreaId( ){ 
	        return this.amassCentEntry.getAreaId( );   
         }
     
        /**
         * @param userId 教师帐号
         */
         public void setUserId(String userId){
	        this.amassCentEntry.setUserId(userId);     
         }
        /**
         * @return userId 教师帐号
         */
         public String getUserId( ){ 
	        return this.amassCentEntry.getUserId( );   
         }
     
        /**
         * @param userName 教师名称
         */
         public void setUserName(String userName){
	        this.amassCentEntry.setUserName(userName);     
         }
        /**
         * @return userName 教师名称
         */
         public String getUserName( ){ 
	        return this.amassCentEntry.getUserName( );   
         }
     
        /**
         * @param siId SiId
         */
         public void setSiId(long siId){
	        this.amassCentEntry.setSiId(siId);     
         }
        /**
         * @return siId SiId
         */
         public long getSiId( ){ 
	        return this.amassCentEntry.getSiId( );   
         }
     
        /**
         * @param schoolId 学校ID
         */
         public void setSchoolId(long schoolId){
	        this.amassCentEntry.setSchoolId(schoolId);     
         }
        /**
         * @return schoolId 学校ID
         */
         public long getSchoolId( ){ 
	        return this.amassCentEntry.getSchoolId( );   
         }
     
        /**
         * @param schoolName 学校名
         */
         public void setSchoolName(String schoolName){
	        this.amassCentEntry.setSchoolName(schoolName);     
         }
        /**
         * @return schoolName 学校名
         */
         public String getSchoolName( ){ 
	        return this.amassCentEntry.getSchoolName( );   
         }
     
        /**
         * @param year 年
         */
         public void setYear(long year){
	        this.amassCentEntry.setYear(year);     
         }
        /**
         * @return year 年
         */
         public long getYear( ){ 
	        return this.amassCentEntry.getYear( );   
         }
     
        /**
         * @param month 月
         */
         public void setMonth(long month){
	        this.amassCentEntry.setMonth(month);     
         }
        /**
         * @return month 月
         */
         public long getMonth( ){ 
	        return this.amassCentEntry.getMonth( );   
         }
     
        /**
         * @param cent 该月的积分
         */
         public void setCent(long cent){
	        this.amassCentEntry.setCent(cent);     
         }
        /**
         * @return cent 该月的积分
         */
         public long getCent( ){ 
	        return this.amassCentEntry.getCent( );   
         }
     
        /**
         * @param isLeader 表am_cent里面得is_leader=2为计算出来得积分 汇总核算教师该月获得总积分
         */
         public void setIsLeader(long isLeader){
	        this.amassCentEntry.setIsLeader(isLeader);     
         }
        /**
         * @return isLeader 表am_cent里面得is_leader=2为计算出来得积分 汇总核算教师该月获得总积分
         */
         public long getIsLeader( ){ 
	        return this.amassCentEntry.getIsLeader( );   
         }
     
        /**
         * @param insertuserid 
         */
         public void setInsertuserid(String insertuserid){
	        this.amassCentEntry.setInsertuserid(insertuserid);     
         }
        /**
         * @return insertuserid 
         */
         public String getInsertuserid( ){ 
	        return this.amassCentEntry.getInsertuserid( );   
         }
     
        /**
         * @param dt 创建时间
         */
         public void setDt(Date dt){
	        this.amassCentEntry.setDt(dt);     
         }
        /**
         * @return dt 创建时间
         */
         public Date getDt( ){ 
	        return this.amassCentEntry.getDt( );   
         }
     
        /**
         * @param amhId 
         */
         public void setAmhId(long amhId){
	        this.amassCentEntry.setAmhId(amhId);     
         }
        /**
         * @return amhId 
         */
         public long getAmhId( ){ 
	        return this.amassCentEntry.getAmhId( );   
         }
     
        /**
         * @param isConfirm 是否是有效积分
         */
         public void setIsConfirm(long isConfirm){
	        this.amassCentEntry.setIsConfirm(isConfirm);     
         }
        /**
         * @return isConfirm 是否是有效积分
         */
         public long getIsConfirm( ){ 
	        return this.amassCentEntry.getIsConfirm( );   
         }
    
    
               
        /**
         * @param areaName 区域名称
         */
         private void setAreaName(String areaName){
	        this.amassCentEntry.setAreaName(areaName);
         }
         
        /**
          * @return areaName 区域名称
         */
         public String getAreaName( ){ 
	        return this.amassCentEntry.getAreaName( );     
         }
              
        /**
         * @param company SI公司名称
         */
         private void setCompany(String company){
	        this.amassCentEntry.setCompany(company);
         }
         
        /**
          * @return company SI公司名称
         */
         public String getCompany( ){ 
	        return this.amassCentEntry.getCompany( );     
         }
    
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "AmassCentInfo";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "AmassCentInfo";
	}

	/**
	 * 设置获取模块名称
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		 return "";
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
     * 根据主键（id）查询某条记录
     * @param id
     * @return Boolean true:代表查询到结果,false：找不到记录
     */
	public Boolean findOne(long  id)  throws BusinessException {
	    AmassCentMapper amassCentMapper= SpringUtil.getSpringBean(AmassCentMapper.class,"amassCentMapper");
		AmassCentEntry entry = null;
	    try{
	         entry = amassCentMapper.findOne(this.getDaoAbb(),id);
	    }catch(DaoAccessException ex){
	       logger.error(getBusinessName()+"执行记录findOne操作时出现异常",ex);
	       throw new BusinessException("查询记录详细发生异常",ex.getMessage(),ex);
	    }
		 
		 if (entry != null){
	           this.setId(entry.getId());
	           this.setAreaId(entry.getAreaId());
	           this.setUserId(entry.getUserId());
	           this.setUserName(entry.getUserName());
	           this.setSiId(entry.getSiId());
	           this.setSchoolId(entry.getSchoolId());
	           this.setSchoolName(entry.getSchoolName());
	           this.setYear(entry.getYear());
	           this.setMonth(entry.getMonth());
	           this.setCent(entry.getCent());
	           this.setIsLeader(entry.getIsLeader());
	           this.setInsertuserid(entry.getInsertuserid());
	           this.setDt(entry.getDt());
	           this.setAmhId(entry.getAmhId());
	           this.setIsConfirm(entry.getIsConfirm());
                   this.setAreaName(entry.getAreaName());
                   this.setCompany(entry.getCompany());
		  
		   entry = null;
		   return true;
		  }else{ 
		        this.amassCentEntry = new AmassCentEntry();
		        return false;  
          }
           
	}
	 
	
	
  
	/**
	 * 新增
	 */
	@Override
	protected void onAdd() throws BusinessException {
		// TODO Auto-generated method stub
           try{
		       AmassCentMapper amassCentMapper= SpringUtil.getSpringBean(AmassCentMapper.class,"amassCentMapper");
		       amassCentMapper.insertAmassCent(this.amassCentEntry);
		        
	       }catch(DaoAccessException ex){
	          logger.error(getBusinessName()+"执行记录新增后，执行提交操作时出现数据库异常",ex);
	          throw new BusinessException("新建记录发生异常",ex.getMessage(),ex);
	       }
	       
	}
	
	
	/**
	 * 修改
	 */
	@Override
	protected void onModify() throws BusinessException {
		// TODO Auto-generated method stub
        try{
		    AmassCentMapper amassCentMapper= SpringUtil.getSpringBean(AmassCentMapper.class,"amassCentMapper");
			amassCentMapper.updateAmassCent(this.amassCentEntry);
		     
	    }catch(DaoAccessException ex){
	          logger.error(getBusinessName()+"执行记录修改操作后，执行提交操作时出现数据库异常",ex);
	          throw new BusinessException("修改记录信息发生异常",ex.getMessage(),ex);
	    }
	}

   	
   	
   	
   	

	
	/**
	 * 删除
	 */
	@Override
	protected void onDelete(long ids[]) throws BusinessException {
		// TODO Auto-generated method stub
		AmassCentMapper amassCentMapper= SpringUtil.getSpringBean(AmassCentMapper.class,"amassCentMapper");
		try{
		   amassCentMapper.deleteAmassCent(ids);
	    }catch(DaoAccessException ex){
	       logger.error(getBusinessName()+"执行记录删除操作后，进行提交时出现数据库异常",ex);
	       throw new BusinessException("删除记录发送异常",ex.getMessage(),ex);
	    }
	}



	
	  /**
	   * 查询结果集合
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
     
	   * @param   areaId   
	   * @param   userId   教师帐号
	   * @param   userName   教师名称
	   * @param   siId   SiId
	   * @param   schoolId   学校ID
	   * @param   schoolName   学校名
	   * @param   year   年
	   * @param   month   月
	   * @param   cent   该月的积分
	   * @param   isLeader   表am_cent里面得is_leader=2为计算出来得积分 汇总核算教师该月获得总积分
	   * @param   insertuserid   
	   * @param   dt1   创建时间 （大于或等于开始时间）
           * @param   dt2   创建时间 （小于或等于结束时间）
	   * @param   amhId   
	   * @param   isConfirm   是否是有效积分
	   * @param   areaName   区域名称
	   * @param   company   SI公司名称
	   * @param orderList  //控制排序
	   * @return List<AmassCentInfo>
	   */
	@SeacherFun(nameAlias="AmassCentInfoSeacher")
	public List<AmassCentInfo> queryAmassCents(@SearchParameter(defaultValue = "1",name = "startRow")int startRow, @SearchParameter(defaultValue = "20",name = "pageSize")int pageSize,
				@SearchParameter(name ="areaId", defaultValue="-1" )long areaId,@SearchParameter(name ="userId")String userId,@SearchParameter(name ="userName")String userName,@SearchParameter(name ="siId", defaultValue="-1" )long siId,@SearchParameter(name ="schoolId", defaultValue="-1" )long schoolId,@SearchParameter(name ="schoolName")String schoolName,@SearchParameter(name ="year", defaultValue="-1" )long year,@SearchParameter(name ="month", defaultValue="-1" )long month,@SearchParameter(name ="cent", defaultValue="-1" )long cent,@SearchParameter(name ="isLeader", defaultValue="-1" )long isLeader,@SearchParameter(name ="insertuserid")String insertuserid,@SearchParameter(name ="dt1")Date dt1,@SearchParameter(name ="dt2")Date dt2,@SearchParameter(name ="amhId", defaultValue="-1" )long amhId,@SearchParameter(name ="isConfirm", defaultValue="-1" )long isConfirm,@SearchParameter(name ="areaName")String areaName,@SearchParameter(name ="company")String company,
				@SearchParameter(name="orderList")List<OrderItem>orderList) throws BusinessException{
		   //实例化List对象		
		   List<AmassCentInfo> list = new ArrayList<AmassCentInfo>();
		   //查询结果实体
		   AmassCentMapper amassCentMapper= SpringUtil.getSpringBean(AmassCentMapper.class,"amassCentMapper");
		  try{ 
		   this.setQeuryRecordTotalNum(amassCentMapper.qeuryAmassCentsRecordCount(this.getDaoAbb(),areaId,userId,userName,siId,schoolId,schoolName,year,month,cent,isLeader,insertuserid,dt1,dt2,amhId,isConfirm,areaName,company));
		   List<AmassCentEntry> entryList = amassCentMapper.qeuryAmassCents(startRow,pageSize,this.getDaoAbb(),areaId,userId,userName,siId,schoolId,schoolName,year,month,cent,isLeader,insertuserid,dt1,dt2,amhId,isConfirm,areaName,company,orderList);
	       if (entryList != null){
			  for (AmassCentEntry entry : entryList) {
				   list.add(new AmassCentInfo(entry));
				   entry = null;
			  }
			  entryList = null;
		   }
		  }catch(DaoAccessException ex){
	          logger.error(getBusinessName()+"执行记录查询操作时出现数据库异常",ex);
	          throw new BusinessException("查询记录发生异常",ex.getMessage(),ex);
	      }
		return list;
	}
				
			

	
	
	
	//自定义方法
	//*****************************************************************************************************************
	
	
}

