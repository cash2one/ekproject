package qtone.xxt.business.edu;
 
import java.util.*;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;


import org.apache.log4j.Logger;
import esfw.core.framework.SpringUtil;
import esfw.core.framework.annotation.SeacherFun;
import esfw.core.framework.annotation.SearchParameter;
import esfw.core.framework.business.BaseBusiness;
import esfw.core.framework.business.enumeration.ActionType;
import esfw.core.framework.dao.mapper.OrderItem;
import esfw.core.framework.exception.BusinessException;
import esfw.core.framework.exception.DaoAccessException;

import qtone.xxt.dao.entity.edu.XjStudentEntry;
import qtone.xxt.dao.mapper.edu.XjStudentMapper;


/**
 *
 * @description 学生信息 对应的（业务逻辑类）
 * @version v1.0
 * @author Qtone  
 * @CreateTime Sat Jun 02 03:38:53 GMT 2012
 *
 */
@Scope("prototype") 
@Service("xjStudentInfo")
public class XjStudentInfo extends BaseBusiness {
    
      //日志服务对象
      static Logger logger = Logger.getLogger(XjStudentInfo.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private XjStudentEntry xjStudentEntry;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public XjStudentInfo() {
	     xjStudentEntry = new XjStudentEntry();
	 }
	
     /**
     * 默认构造函数
     */
	 public XjStudentInfo(XjStudentEntry entry) {
	      this.xjStudentEntry = entry;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 主键
         */
         public void setId(long id){
	        this.xjStudentEntry.setId(id);     
         }
        /**
         * @return id 主键
         */
         public long getId( ){ 
	        return this.xjStudentEntry.getId( );   
         }
     
        /**
         * @param stuSequence 学生编号（系统分配、全省唯一），生成规则：2位年份＋2位地区代码＋8位自动增长编号
         */
         public void setStuSequence(String stuSequence){
	        this.xjStudentEntry.setStuSequence(stuSequence);     
         }
        /**
         * @return stuSequence 学生编号（系统分配、全省唯一），生成规则：2位年份＋2位地区代码＋8位自动增长编号
         */
         public String getStuSequence( ){ 
	        return this.xjStudentEntry.getStuSequence( );   
         }
     
        /**
         * @param stuNo 学号
         */
         public void setStuNo(String stuNo){
	        this.xjStudentEntry.setStuNo(stuNo);     
         }
        /**
         * @return stuNo 学号
         */
         public String getStuNo( ){ 
	        return this.xjStudentEntry.getStuNo( );   
         }
     
        /**
         * @param name 姓名(长度改为100)
         */
         public void setName(String name){
	        this.xjStudentEntry.setName(name);     
         }
        /**
         * @return name 姓名(长度改为100)
         */
         public String getName( ){ 
	        return this.xjStudentEntry.getName( );   
         }
     
        /**
         * @param sex 性别(1男；0女)
         */
         public void setSex(long sex){
	        this.xjStudentEntry.setSex(sex);     
         }
        /**
         * @return sex 性别(1男；0女)
         */
         public long getSex( ){ 
	        return this.xjStudentEntry.getSex( );   
         }
     
        /**
         * @param birthday 出生日期
         */
         public void setBirthday(Date birthday){
	        this.xjStudentEntry.setBirthday(birthday);     
         }
        /**
         * @return birthday 出生日期
         */
         public Date getBirthday( ){ 
	        return this.xjStudentEntry.getBirthday( );   
         }
     
        /**
         * @param address 家庭住址
         */
         public void setAddress(String address){
	        this.xjStudentEntry.setAddress(address);     
         }
        /**
         * @return address 家庭住址
         */
         public String getAddress( ){ 
	        return this.xjStudentEntry.getAddress( );   
         }
     
        /**
         * @param origin 籍贯(用户直接输入)
         */
         public void setOrigin(String origin){
	        this.xjStudentEntry.setOrigin(origin);     
         }
        /**
         * @return origin 籍贯(用户直接输入)
         */
         public String getOrigin( ){ 
	        return this.xjStudentEntry.getOrigin( );   
         }
     
        /**
         * @param nation 民族(1汉族、2壮族、3苗族、4柯尔克孜族、5阿昌族、6京族、7高山族、8塔塔尔族、9布朗族、10鄂伦春族
11土族、12傣族、13傈傈族、14怒族、15拉祜族、16中籍外国血统、17仫佬族、18乌孜别克族、19撒拉族
20瑶族、21东乡族、22俄罗斯族、23布依族、24黎族、25独龙族、26回族、27塔吉克族、28纳西族
29哈萨克族、30土家族、31毛难族、32维吾尔族、33白族、34崩龙族、35仡佬族、36基诺族、37珞巴族
38羌族、39哈尼族、40满族、41藏族、42普米族、43侗族、44鄂温克族、45彝族、46蒙古族、47锡伯族
48赫哲族、49达斡族、51安族、50门巴族、52佤族、53裕固族、54朝鲜族、55畲族、56其他
)
         */
         public void setNation(long nation){
	        this.xjStudentEntry.setNation(nation);     
         }
        /**
         * @return nation 民族(1汉族、2壮族、3苗族、4柯尔克孜族、5阿昌族、6京族、7高山族、8塔塔尔族、9布朗族、10鄂伦春族
11土族、12傣族、13傈傈族、14怒族、15拉祜族、16中籍外国血统、17仫佬族、18乌孜别克族、19撒拉族
20瑶族、21东乡族、22俄罗斯族、23布依族、24黎族、25独龙族、26回族、27塔吉克族、28纳西族
29哈萨克族、30土家族、31毛难族、32维吾尔族、33白族、34崩龙族、35仡佬族、36基诺族、37珞巴族
38羌族、39哈尼族、40满族、41藏族、42普米族、43侗族、44鄂温克族、45彝族、46蒙古族、47锡伯族
48赫哲族、49达斡族、51安族、50门巴族、52佤族、53裕固族、54朝鲜族、55畲族、56其他
)
         */
         public long getNation( ){ 
	        return this.xjStudentEntry.getNation( );   
         }
     
        /**
         * @param originAddr 户口所在地
         */
         public void setOriginAddr(String originAddr){
	        this.xjStudentEntry.setOriginAddr(originAddr);     
         }
        /**
         * @return originAddr 户口所在地
         */
         public String getOriginAddr( ){ 
	        return this.xjStudentEntry.getOriginAddr( );   
         }
     
        /**
         * @param bloodType 血型(1A、2B、3AB、4O、5空白)
         */
         public void setBloodType(long bloodType){
	        this.xjStudentEntry.setBloodType(bloodType);     
         }
        /**
         * @return bloodType 血型(1A、2B、3AB、4O、5空白)
         */
         public long getBloodType( ){ 
	        return this.xjStudentEntry.getBloodType( );   
         }
     
        /**
         * @param politics 政治面貌(1少先队员、2共青团员、3中共预备党员、4中共党员、5民革会员、6民盟盟员、7民建会员、8民进会员、9农工党党员、10致公党员、11九三学社社员、12台盟盟员、13无党派民主人士、14群众,99空白)
         */
         public void setPolitics(long politics){
	        this.xjStudentEntry.setPolitics(politics);     
         }
        /**
         * @return politics 政治面貌(1少先队员、2共青团员、3中共预备党员、4中共党员、5民革会员、6民盟盟员、7民建会员、8民进会员、9农工党党员、10致公党员、11九三学社社员、12台盟盟员、13无党派民主人士、14群众,99空白)
         */
         public long getPolitics( ){ 
	        return this.xjStudentEntry.getPolitics( );   
         }
     
        /**
         * @param icNo IC卡号(前台不允许新增，修改，只可以查看，卡号通过客户端刷卡进入数据库)
         */
         public void setIcNo(String icNo){
	        this.xjStudentEntry.setIcNo(icNo);     
         }
        /**
         * @return icNo IC卡号(前台不允许新增，修改，只可以查看，卡号通过客户端刷卡进入数据库)
         */
         public String getIcNo( ){ 
	        return this.xjStudentEntry.getIcNo( );   
         }
     
        /**
         * @param homePhone 家庭电话
         */
         public void setHomePhone(String homePhone){
	        this.xjStudentEntry.setHomePhone(homePhone);     
         }
        /**
         * @return homePhone 家庭电话
         */
         public String getHomePhone( ){ 
	        return this.xjStudentEntry.getHomePhone( );   
         }
     
        /**
         * @param bornAddress 出生地
         */
         public void setBornAddress(String bornAddress){
	        this.xjStudentEntry.setBornAddress(bornAddress);     
         }
        /**
         * @return bornAddress 出生地
         */
         public String getBornAddress( ){ 
	        return this.xjStudentEntry.getBornAddress( );   
         }
     
        /**
         * @param icStatus IC卡状态:未发卡(0),已发卡(1),挂失(2),补卡(3)
         */
         public void setIcStatus(long icStatus){
	        this.xjStudentEntry.setIcStatus(icStatus);     
         }
        /**
         * @return icStatus IC卡状态:未发卡(0),已发卡(1),挂失(2),补卡(3)
         */
         public long getIcStatus( ){ 
	        return this.xjStudentEntry.getIcStatus( );   
         }
     
        /**
         * @param inSchoolDate 入校日期
         */
         public void setInSchoolDate(Date inSchoolDate){
	        this.xjStudentEntry.setInSchoolDate(inSchoolDate);     
         }
        /**
         * @return inSchoolDate 入校日期
         */
         public Date getInSchoolDate( ){ 
	        return this.xjStudentEntry.getInSchoolDate( );   
         }
     
        /**
         * @param createTime 
         */
         public void setCreateTime(Date createTime){
	        this.xjStudentEntry.setCreateTime(createTime);     
         }
        /**
         * @return createTime 
         */
         public Date getCreateTime( ){ 
	        return this.xjStudentEntry.getCreateTime( );   
         }
     
        /**
         * @param callTime 
         */
         public void setCallTime(long callTime){
	        this.xjStudentEntry.setCallTime(callTime);     
         }
        /**
         * @return callTime 
         */
         public long getCallTime( ){ 
	        return this.xjStudentEntry.getCallTime( );   
         }
     
        /**
         * @param stuType 
         */
         public void setStuType(long stuType){
	        this.xjStudentEntry.setStuType(stuType);     
         }
        /**
         * @return stuType 
         */
         public long getStuType( ){ 
	        return this.xjStudentEntry.getStuType( );   
         }
     
        /**
         * @param seatNumber 
         */
         public void setSeatNumber(long seatNumber){
	        this.xjStudentEntry.setSeatNumber(seatNumber);     
         }
        /**
         * @return seatNumber 
         */
         public long getSeatNumber( ){ 
	        return this.xjStudentEntry.getSeatNumber( );   
         }
     
        /**
         * @param cardNo 
         */
         public void setCardNo(String cardNo){
	        this.xjStudentEntry.setCardNo(cardNo);     
         }
        /**
         * @return cardNo 
         */
         public String getCardNo( ){ 
	        return this.xjStudentEntry.getCardNo( );   
         }
     
        /**
         * @param lastUpdateTimestamp 
         */
         public void setLastUpdateTimestamp(Date lastUpdateTimestamp){
	        this.xjStudentEntry.setLastUpdateTimestamp(lastUpdateTimestamp);     
         }
        /**
         * @return lastUpdateTimestamp 
         */
         public Date getLastUpdateTimestamp( ){ 
	        return this.xjStudentEntry.getLastUpdateTimestamp( );   
         }
     
        /**
         * @param comments 
         */
         public void setComments(String comments){
	        this.xjStudentEntry.setComments(comments);     
         }
        /**
         * @return comments 
         */
         public String getComments( ){ 
	        return this.xjStudentEntry.getComments( );   
         }
    
    
               
        /**
         * @param schoolId 所属的学校
         */
         private void setSchoolId(long schoolId){
	        this.xjStudentEntry.setSchoolId(schoolId);
         }
         
        /**
          * @return schoolId 所属的学校
         */
         public long getSchoolId( ){ 
	        return this.xjStudentEntry.getSchoolId( );     
         }
              
        /**
         * @param classId 班级编号
         */
         private void setClassId(long classId){
	        this.xjStudentEntry.setClassId(classId);
         }
         
        /**
          * @return classId 班级编号
         */
         public long getClassId( ){ 
	        return this.xjStudentEntry.getClassId( );     
         }
              
        /**
         * @param townId 所属镇区
         */
         private void setTownId(long townId){
	        this.xjStudentEntry.setTownId(townId);
         }
         
        /**
          * @return townId 所属镇区
         */
         public long getTownId( ){ 
	        return this.xjStudentEntry.getTownId( );     
         }
              
        /**
         * @param schoolName 学校的名称
         */
         private void setSchoolName(String schoolName){
	        this.xjStudentEntry.setSchoolName(schoolName);
         }
         
        /**
          * @return schoolName 学校的名称
         */
         public String getSchoolName( ){ 
	        return this.xjStudentEntry.getSchoolName( );     
         }
              
        /**
         * @param townName 镇区名称
         */
         private void setTownName(String townName){
	        this.xjStudentEntry.setTownName(townName);
         }
         
        /**
          * @return townName 镇区名称
         */
         public String getTownName( ){ 
	        return this.xjStudentEntry.getTownName( );     
         }
              
        /**
         * @param className 班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改)
         */
         private void setClassName(String className){
	        this.xjStudentEntry.setClassName(className);
         }
         
        /**
          * @return className 班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改)
         */
         public String getClassName( ){ 
	        return this.xjStudentEntry.getClassName( );     
         }
    
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "XjStudentInfo";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "XjStudentInfo";
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
	    XjStudentMapper xjStudentMapper= SpringUtil.getSpringBean(XjStudentMapper.class,"xjStudentMapper");
		XjStudentEntry entry = null;
	    try{
	         entry = xjStudentMapper.findOne(this.getDaoAbb(),id);
	    }catch(DaoAccessException ex){
	       logger.error(getBusinessName()+"执行记录findOne操作时出现异常",ex);
	       throw new BusinessException("查询记录详细发生异常",ex.getMessage(),ex);
	    }
		 
		 if (entry != null){
	           this.setId(entry.getId());
	           this.setStuSequence(entry.getStuSequence());
	           this.setStuNo(entry.getStuNo());
	           this.setName(entry.getName());
	           this.setSex(entry.getSex());
	           this.setBirthday(entry.getBirthday());
	           this.setAddress(entry.getAddress());
	           this.setOrigin(entry.getOrigin());
	           this.setNation(entry.getNation());
	           this.setOriginAddr(entry.getOriginAddr());
	           this.setBloodType(entry.getBloodType());
	           this.setPolitics(entry.getPolitics());
	           this.setIcNo(entry.getIcNo());
	           this.setHomePhone(entry.getHomePhone());
	           this.setBornAddress(entry.getBornAddress());
	           this.setIcStatus(entry.getIcStatus());
	           this.setInSchoolDate(entry.getInSchoolDate());
	           this.setCreateTime(entry.getCreateTime());
	           this.setCallTime(entry.getCallTime());
	           this.setStuType(entry.getStuType());
	           this.setSeatNumber(entry.getSeatNumber());
	           this.setCardNo(entry.getCardNo());
	           this.setLastUpdateTimestamp(entry.getLastUpdateTimestamp());
	           this.setComments(entry.getComments());
                   this.setSchoolId(entry.getSchoolId());
                   this.setClassId(entry.getClassId());
                   this.setTownId(entry.getTownId());
                   this.setSchoolName(entry.getSchoolName());
                   this.setTownName(entry.getTownName());
                   this.setClassName(entry.getClassName());
		  
		   entry = null;
		   return true;
		  }else{ 
		        this.xjStudentEntry = new XjStudentEntry();
		        return false;  
          }
           
	}
	 
	
	
  
   	
   	
   	
	/**
	 * 新增
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
		      
		       XjStudentMapper xjStudentMapper= SpringUtil.getSpringBean(XjStudentMapper.class,"xjStudentMapper");
		       xjStudentMapper.insertXjStudent(this.getDaoAbb(),this.xjStudentEntry);
		        
		       
		       //请实现业务新增的逻辑过程
		       
		       
		       }catch(DaoAccessException ex){
		    	  logger.error(getBusinessName()+"执行记录新增后，进行业务提交时出现数据库异常",ex);
		    	  txManager.rollback(status); //事务回滚
		    	  throw new BusinessException(ex.getUserOperateExMsg(),ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
	
	
	/**
	 * 修改
	 */
	@Override
	protected void onModify() throws BusinessException {
		// TODO Auto-generated method stub
		
		//执行数据验证
		checkAndFilter(ActionType.mdf);
        
        //打开事务控制
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,"transactionManager");
		TransactionStatus status = txManager.getTransaction(def);
		try{
		    XjStudentMapper xjStudentMapper= SpringUtil.getSpringBean(XjStudentMapper.class,"xjStudentMapper");
			xjStudentMapper.updateXjStudent(this.getDaoAbb(),this.xjStudentEntry);
		     
	        
	        }catch(DaoAccessException ex){
		    	logger.error(getBusinessName()+"执行记录修改后，进行业务提交时出现数据库异常",ex);
		    	txManager.rollback(status); //事务回滚
		    	throw new BusinessException(ex.getUserOperateExMsg(),ex.getMessage(),ex);
		    } 
		    txManager = null;
		    def = null;
	}
   	

	
	/**
	 * 删除
	 */
	@Override
	protected void onDelete(long ids[]) throws BusinessException {
		// TODO Auto-generated method stub
		XjStudentMapper xjStudentMapper= SpringUtil.getSpringBean(XjStudentMapper.class,"xjStudentMapper");
		try{
		   xjStudentMapper.deleteXjStudent(this.getDaoAbb(),ids);
	    }catch(DaoAccessException ex){
	       logger.error(getBusinessName()+"执行记录删除操作后，进行提交时出现数据库异常",ex);
	       throw new BusinessException("删除记录发送异常",ex.getMessage(),ex);
	    }
	}



	
	  /**
	   * 查询结果集合
	   * @param startRow   开始记录的行数
	   * @param pageSize   设置每页显示的记录数
     
	   * @param   stuSequence   学生编号（系统分配、全省唯一），生成规则：2位年份＋2位地区代码＋8位自动增长编号
	   * @param   stuNo   学号
	   * @param   name   姓名(长度改为100)
	   * @param   sex   性别(1男；0女)
	   * @param   birthday1   出生日期 （大于或等于开始时间）
           * @param   birthday2   出生日期 （小于或等于结束时间）
	   * @param   address   家庭住址
	   * @param   origin   籍贯(用户直接输入)
	   * @param   nation   民族(1汉族、2壮族、3苗族、4柯尔克孜族、5阿昌族、6京族、7高山族、8塔塔尔族、9布朗族、10鄂伦春族
11土族、12傣族、13傈傈族、14怒族、15拉祜族、16中籍外国血统、17仫佬族、18乌孜别克族、19撒拉族
20瑶族、21东乡族、22俄罗斯族、23布依族、24黎族、25独龙族、26回族、27塔吉克族、28纳西族
29哈萨克族、30土家族、31毛难族、32维吾尔族、33白族、34崩龙族、35仡佬族、36基诺族、37珞巴族
38羌族、39哈尼族、40满族、41藏族、42普米族、43侗族、44鄂温克族、45彝族、46蒙古族、47锡伯族
48赫哲族、49达斡族、51安族、50门巴族、52佤族、53裕固族、54朝鲜族、55畲族、56其他
)
	   * @param   originAddr   户口所在地
	   * @param   bloodType   血型(1A、2B、3AB、4O、5空白)
	   * @param   politics   政治面貌(1少先队员、2共青团员、3中共预备党员、4中共党员、5民革会员、6民盟盟员、7民建会员、8民进会员、9农工党党员、10致公党员、11九三学社社员、12台盟盟员、13无党派民主人士、14群众,99空白)
	   * @param   icNo   IC卡号(前台不允许新增，修改，只可以查看，卡号通过客户端刷卡进入数据库)
	   * @param   homePhone   家庭电话
	   * @param   bornAddress   出生地
	   * @param   icStatus   IC卡状态:未发卡(0),已发卡(1),挂失(2),补卡(3)
	   * @param   inSchoolDate1   入校日期 （大于或等于开始时间）
           * @param   inSchoolDate2   入校日期 （小于或等于结束时间）
	   * @param   createTime1    （大于或等于开始时间）
           * @param   createTime2    （小于或等于结束时间）
	   * @param   callTime   
	   * @param   stuType   
	   * @param   seatNumber   
	   * @param   cardNo   
	   * @param   lastUpdateTimestamp1    （大于或等于开始时间）
           * @param   lastUpdateTimestamp2    （小于或等于结束时间）
	   * @param   comments   
	   * @param   schoolId   所属的学校
	   * @param   classId   班级编号
	   * @param   townId   所属镇区
	   * @param   schoolName   学校的名称
	   * @param   townName   镇区名称
	   * @param   className   班级名(页面上系统自动生成：年级名称+(班级序号)+班,可以由用户修改)
	   * @param orderList  //控制排序
	   * @return List<xjstudentinfo>
	   */
	@SeacherFun(nameAlias="XjStudentInfoSeacher")
	public List<xjstudentinfo> queryXjStudents(@SearchParameter(defaultValue = "1",name = "startRow")int startRow, @SearchParameter(defaultValue = "20",name = "pageSize")int pageSize,
				@SearchParameter(name ="stuSequence")String stuSequence,@SearchParameter(name ="stuNo")String stuNo,@SearchParameter(name ="name")String name,@SearchParameter(name ="sex", defaultValue="-1" )long sex,@SearchParameter(name ="birthday1")Date birthday1,@SearchParameter(name ="birthday2")Date birthday2,@SearchParameter(name ="address")String address,@SearchParameter(name ="origin")String origin,@SearchParameter(name ="nation", defaultValue="-1" )long nation,@SearchParameter(name ="originAddr")String originAddr,@SearchParameter(name ="bloodType", defaultValue="-1" )long bloodType,@SearchParameter(name ="politics", defaultValue="-1" )long politics,@SearchParameter(name ="icNo")String icNo,@SearchParameter(name ="homePhone")String homePhone,@SearchParameter(name ="bornAddress")String bornAddress,@SearchParameter(name ="icStatus", defaultValue="-1" )long icStatus,@SearchParameter(name ="inSchoolDate1")Date inSchoolDate1,@SearchParameter(name ="inSchoolDate2")Date inSchoolDate2,@SearchParameter(name ="createTime1")Date createTime1,@SearchParameter(name ="createTime2")Date createTime2,@SearchParameter(name ="callTime", defaultValue="-1" )long callTime,@SearchParameter(name ="stuType", defaultValue="-1" )long stuType,@SearchParameter(name ="seatNumber", defaultValue="-1" )long seatNumber,@SearchParameter(name ="cardNo")String cardNo,@SearchParameter(name ="lastUpdateTimestamp1")Date lastUpdateTimestamp1,@SearchParameter(name ="lastUpdateTimestamp2")Date lastUpdateTimestamp2,@SearchParameter(name ="comments")String comments,@SearchParameter(name ="schoolId", defaultValue="-1" )long schoolId,@SearchParameter(name ="classId", defaultValue="-1" )long classId,@SearchParameter(name ="townId", defaultValue="-1" )long townId,@SearchParameter(name ="schoolName")String schoolName,@SearchParameter(name ="townName")String townName,@SearchParameter(name ="className")String className,
				@SearchParameter(name="orderList")List<orderitem>orderList) throws BusinessException{
		   //实例化List对象		
		   List<xjstudentinfo> list = new ArrayList<xjstudentinfo>();
		   //查询结果实体
		   XjStudentMapper xjStudentMapper= SpringUtil.getSpringBean(XjStudentMapper.class,"xjStudentMapper");
		  try{ 
		   this.setQeuryRecordTotalNum(xjStudentMapper.qeuryXjStudentsRecordCount(this.getDaoAbb(),stuSequence,stuNo,name,sex,birthday1,birthday2,address,origin,nation,originAddr,bloodType,politics,icNo,homePhone,bornAddress,icStatus,inSchoolDate1,inSchoolDate2,createTime1,createTime2,callTime,stuType,seatNumber,cardNo,lastUpdateTimestamp1,lastUpdateTimestamp2,comments,schoolId,classId,townId,schoolName,townName,className));
		   List<xjstudententry> entryList = xjStudentMapper.qeuryXjStudents(startRow,pageSize,this.getDaoAbb(),stuSequence,stuNo,name,sex,birthday1,birthday2,address,origin,nation,originAddr,bloodType,politics,icNo,homePhone,bornAddress,icStatus,inSchoolDate1,inSchoolDate2,createTime1,createTime2,callTime,stuType,seatNumber,cardNo,lastUpdateTimestamp1,lastUpdateTimestamp2,comments,schoolId,classId,townId,schoolName,townName,className,orderList);
	       if (entryList != null){
			  for (XjStudentEntry entry : entryList) {
				   list.add(new XjStudentInfo(entry));
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
</xjstudententry></xjstudentinfo></xjstudentinfo></orderitem></xjstudentinfo></xjstudentinfo></body></html>