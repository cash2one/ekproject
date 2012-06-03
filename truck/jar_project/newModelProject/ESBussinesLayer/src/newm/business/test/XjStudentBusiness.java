package newm.business.test;
 

import java.util.*;

import newm.dao.entity.test.XjStudentEntity;
import newm.dao.inter.test.XjStudentDao;
import newm.vo.test.XjStudentVo;

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
 * @description 测试 对应的（业务逻辑类）
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Sun Jun 03 10:54:30 GMT 2012
 *
 */
@Scope("prototype") 
@Service("xjStudentBusiness")
public class XjStudentBusiness  extends BaseBusiness<Long> implements BaseQuery<XjStudentBusiness,Long,XjStudentVo>{
    
      //日志服务对象
      static Logger logger = Logger.getLogger(XjStudentBusiness.class);
    
      //实体属性
	  //*****************************************************************************************************************
	  private XjStudentEntity xjStudentEntity;
          
	 
	 //构造函数
	 //*****************************************************************************************************************
	
     /**
     * 默认构造函数
     */
	 public XjStudentBusiness() {
	     xjStudentEntity = new XjStudentEntity();
	 }
	
     /**
     * 默认构造函数
     */
	 public XjStudentBusiness(XjStudentEntity entity) {
	      this.xjStudentEntity = entity;
	 }
	
	
	//属性对应的get 和 set 方法
	//*****************************************************************************************************************
	
     
        /**
         * @param id 主键
         */
         public void setId(long id){
	        this.xjStudentEntity.setId(id);     
         }
        /**
         * @return id 主键
         */
         public long getId( ){ 
	        return this.xjStudentEntity.getId( );   
         }
     
        /**
         * @param stuSequence 学生编号（系统分配、全省唯一），生成规则：2位年份＋2位地区代码＋8位自动增长编号
         */
         public void setStuSequence(String stuSequence){
	        this.xjStudentEntity.setStuSequence(stuSequence);     
         }
        /**
         * @return stuSequence 学生编号（系统分配、全省唯一），生成规则：2位年份＋2位地区代码＋8位自动增长编号
         */
         public String getStuSequence( ){ 
	        return this.xjStudentEntity.getStuSequence( );   
         }
     
        /**
         * @param stuNo 学号
         */
         public void setStuNo(String stuNo){
	        this.xjStudentEntity.setStuNo(stuNo);     
         }
        /**
         * @return stuNo 学号
         */
         public String getStuNo( ){ 
	        return this.xjStudentEntity.getStuNo( );   
         }
     
        /**
         * @param name 姓名(长度改为100)
         */
         public void setName(String name){
	        this.xjStudentEntity.setName(name);     
         }
        /**
         * @return name 姓名(长度改为100)
         */
         public String getName( ){ 
	        return this.xjStudentEntity.getName( );   
         }
     
        /**
         * @param sex 性别(1男；0女)
         */
         public void setSex(long sex){
	        this.xjStudentEntity.setSex(sex);     
         }
        /**
         * @return sex 性别(1男；0女)
         */
         public long getSex( ){ 
	        return this.xjStudentEntity.getSex( );   
         }
     
        /**
         * @param birthday 出生日期
         */
         public void setBirthday(Date birthday){
	        this.xjStudentEntity.setBirthday(birthday);     
         }
        /**
         * @return birthday 出生日期
         */
         public Date getBirthday( ){ 
	        return this.xjStudentEntity.getBirthday( );   
         }
     
        /**
         * @param address 家庭住址
         */
         public void setAddress(String address){
	        this.xjStudentEntity.setAddress(address);     
         }
        /**
         * @return address 家庭住址
         */
         public String getAddress( ){ 
	        return this.xjStudentEntity.getAddress( );   
         }
     
        /**
         * @param origin 籍贯(用户直接输入)
         */
         public void setOrigin(String origin){
	        this.xjStudentEntity.setOrigin(origin);     
         }
        /**
         * @return origin 籍贯(用户直接输入)
         */
         public String getOrigin( ){ 
	        return this.xjStudentEntity.getOrigin( );   
         }
     
        /**
         * @param nation 主表字段:NATION
         */
         public void setNation(long nation){
	        this.xjStudentEntity.setNation(nation);     
         }
        /**
         * @return nation 主表字段:NATION
         */
         public long getNation( ){ 
	        return this.xjStudentEntity.getNation( );   
         }
     
        /**
         * @param originAddr 户口所在地
         */
         public void setOriginAddr(String originAddr){
	        this.xjStudentEntity.setOriginAddr(originAddr);     
         }
        /**
         * @return originAddr 户口所在地
         */
         public String getOriginAddr( ){ 
	        return this.xjStudentEntity.getOriginAddr( );   
         }
     
        /**
         * @param bloodType 血型(1A、2B、3AB、4O、5空白)
         */
         public void setBloodType(long bloodType){
	        this.xjStudentEntity.setBloodType(bloodType);     
         }
        /**
         * @return bloodType 血型(1A、2B、3AB、4O、5空白)
         */
         public long getBloodType( ){ 
	        return this.xjStudentEntity.getBloodType( );   
         }
     
        /**
         * @param politics 政治面貌(1少先队员、2共青团员、3中共预备党员、4中共党员、5民革会员、6民盟盟员、7民建会员、8民进会员、9农工党党员、10致公党员、11九三学社社员、12台盟盟员、13无党派民主人士、14群众,99空白)
         */
         public void setPolitics(long politics){
	        this.xjStudentEntity.setPolitics(politics);     
         }
        /**
         * @return politics 政治面貌(1少先队员、2共青团员、3中共预备党员、4中共党员、5民革会员、6民盟盟员、7民建会员、8民进会员、9农工党党员、10致公党员、11九三学社社员、12台盟盟员、13无党派民主人士、14群众,99空白)
         */
         public long getPolitics( ){ 
	        return this.xjStudentEntity.getPolitics( );   
         }
     
        /**
         * @param icNo IC卡号(前台不允许新增，修改，只可以查看，卡号通过客户端刷卡进入数据库)
         */
         public void setIcNo(String icNo){
	        this.xjStudentEntity.setIcNo(icNo);     
         }
        /**
         * @return icNo IC卡号(前台不允许新增，修改，只可以查看，卡号通过客户端刷卡进入数据库)
         */
         public String getIcNo( ){ 
	        return this.xjStudentEntity.getIcNo( );   
         }
     
        /**
         * @param homePhone 家庭电话
         */
         public void setHomePhone(String homePhone){
	        this.xjStudentEntity.setHomePhone(homePhone);     
         }
        /**
         * @return homePhone 家庭电话
         */
         public String getHomePhone( ){ 
	        return this.xjStudentEntity.getHomePhone( );   
         }
     
        /**
         * @param goodAt 特长
         */
         public void setGoodAt(String goodAt){
	        this.xjStudentEntity.setGoodAt(goodAt);     
         }
        /**
         * @return goodAt 特长
         */
         public String getGoodAt( ){ 
	        return this.xjStudentEntity.getGoodAt( );   
         }
     
        /**
         * @param identyNo 身份证号
         */
         public void setIdentyNo(String identyNo){
	        this.xjStudentEntity.setIdentyNo(identyNo);     
         }
        /**
         * @return identyNo 身份证号
         */
         public String getIdentyNo( ){ 
	        return this.xjStudentEntity.getIdentyNo( );   
         }
     
        /**
         * @param homeMacau 港澳台侨(1归侨、2华侨、3侨眷、4港澳、5台胞、6外籍华人、7华籍外人、8非华籍外人、9其他)
         */
         public void setHomeMacau(long homeMacau){
	        this.xjStudentEntity.setHomeMacau(homeMacau);     
         }
        /**
         * @return homeMacau 港澳台侨(1归侨、2华侨、3侨眷、4港澳、5台胞、6外籍华人、7华籍外人、8非华籍外人、9其他)
         */
         public long getHomeMacau( ){ 
	        return this.xjStudentEntity.getHomeMacau( );   
         }
     
        /**
         * @param bornAddress 出生地
         */
         public void setBornAddress(String bornAddress){
	        this.xjStudentEntity.setBornAddress(bornAddress);     
         }
        /**
         * @return bornAddress 出生地
         */
         public String getBornAddress( ){ 
	        return this.xjStudentEntity.getBornAddress( );   
         }
     
        /**
         * @param health 健康状况
         */
         public void setHealth(String health){
	        this.xjStudentEntity.setHealth(health);     
         }
        /**
         * @return health 健康状况
         */
         public String getHealth( ){ 
	        return this.xjStudentEntity.getHealth( );   
         }
     
        /**
         * @param icStatus IC卡状态:未发卡(0),已发卡(1),挂失(2),补卡(3)
         */
         public void setIcStatus(long icStatus){
	        this.xjStudentEntity.setIcStatus(icStatus);     
         }
        /**
         * @return icStatus IC卡状态:未发卡(0),已发卡(1),挂失(2),补卡(3)
         */
         public long getIcStatus( ){ 
	        return this.xjStudentEntity.getIcStatus( );   
         }
     
        /**
         * @param inSchoolDate 入校日期
         */
         public void setInSchoolDate(Date inSchoolDate){
	        this.xjStudentEntity.setInSchoolDate(inSchoolDate);     
         }
        /**
         * @return inSchoolDate 入校日期
         */
         public Date getInSchoolDate( ){ 
	        return this.xjStudentEntity.getInSchoolDate( );   
         }
     
        /**
         * @param exchangeId 数据交互原始编号（保存其它地市上传数据的原始唯一编号，用户修改或删除该数据）
         */
         public void setExchangeId(String exchangeId){
	        this.xjStudentEntity.setExchangeId(exchangeId);     
         }
        /**
         * @return exchangeId 数据交互原始编号（保存其它地市上传数据的原始唯一编号，用户修改或删除该数据）
         */
         public String getExchangeId( ){ 
	        return this.xjStudentEntity.getExchangeId( );   
         }
     
        /**
         * @param createTime 创建时间
         */
         public void setCreateTime(Date createTime){
	        this.xjStudentEntity.setCreateTime(createTime);     
         }
        /**
         * @return createTime 创建时间
         */
         public Date getCreateTime( ){ 
	        return this.xjStudentEntity.getCreateTime( );   
         }
     
        /**
         * @param callTime 通话时间
         */
         public void setCallTime(long callTime){
	        this.xjStudentEntity.setCallTime(callTime);     
         }
        /**
         * @return callTime 通话时间
         */
         public long getCallTime( ){ 
	        return this.xjStudentEntity.getCallTime( );   
         }
     
        /**
         * @param stuType 
         */
         public void setStuType(long stuType){
	        this.xjStudentEntity.setStuType(stuType);     
         }
        /**
         * @return stuType 
         */
         public long getStuType( ){ 
	        return this.xjStudentEntity.getStuType( );   
         }
     
        /**
         * @param seatNumber 座位号码
         */
         public void setSeatNumber(long seatNumber){
	        this.xjStudentEntity.setSeatNumber(seatNumber);     
         }
        /**
         * @return seatNumber 座位号码
         */
         public long getSeatNumber( ){ 
	        return this.xjStudentEntity.getSeatNumber( );   
         }
     
        /**
         * @param cardNo IC卡号
         */
         public void setCardNo(String cardNo){
	        this.xjStudentEntity.setCardNo(cardNo);     
         }
        /**
         * @return cardNo IC卡号
         */
         public String getCardNo( ){ 
	        return this.xjStudentEntity.getCardNo( );   
         }
     
        /**
         * @param icNo2 IC卡号2
         */
         public void setIcNo2(String icNo2){
	        this.xjStudentEntity.setIcNo2(icNo2);     
         }
        /**
         * @return icNo2 IC卡号2
         */
         public String getIcNo2( ){ 
	        return this.xjStudentEntity.getIcNo2( );   
         }
     
        /**
         * @param lastUpdateTimestamp 最后更新日期
         */
         public void setLastUpdateTimestamp(Date lastUpdateTimestamp){
	        this.xjStudentEntity.setLastUpdateTimestamp(lastUpdateTimestamp);     
         }
        /**
         * @return lastUpdateTimestamp 最后更新日期
         */
         public Date getLastUpdateTimestamp( ){ 
	        return this.xjStudentEntity.getLastUpdateTimestamp( );   
         }
     
        /**
         * @param sxUserid 思讯用户名
         */
         public void setSxUserid(String sxUserid){
	        this.xjStudentEntity.setSxUserid(sxUserid);     
         }
        /**
         * @return sxUserid 思讯用户名
         */
         public String getSxUserid( ){ 
	        return this.xjStudentEntity.getSxUserid( );   
         }
     
        /**
         * @param sxPsw 思讯密码
         */
         public void setSxPsw(String sxPsw){
	        this.xjStudentEntity.setSxPsw(sxPsw);     
         }
        /**
         * @return sxPsw 思讯密码
         */
         public String getSxPsw( ){ 
	        return this.xjStudentEntity.getSxPsw( );   
         }
     
        /**
         * @param comments 备注信息
         */
         public void setComments(String comments){
	        this.xjStudentEntity.setComments(comments);     
         }
        /**
         * @return comments 备注信息
         */
         public String getComments( ){ 
	        return this.xjStudentEntity.getComments( );   
         }
    
    
     
    
    
    //子类必须要实现父类的抽象方法（模块）。
	//******************************************************************************************************************************
	
	/**
	 * 设置业务逻辑名称
	 */
	@Override
	public String getBusinessName(){
	   // TODO Auto-generated method stub
		 return "test.XjStudentBusiness";
	}


	/**
	 * 设置业务逻辑功能标识 对应数据表的功能标识
	 */
	@Override
	public String getFunctionFlag() {
		// TODO Auto-generated method stub
		 return "XjStudentBusiness";
	}

	/**
	 * 设置获取模块名称
	 */
	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		 return "test";
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
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#load(java.lang.Object)
	 * 
	 */
	public Boolean load(Long id) throws BusinessException {
	// TODO Auto-generated method stub
	 try {
		        XjStudentDao xjStudentDao= SpringUtil.getSpringBean(XjStudentDao.class,"xjStudentDao");
			XjStudentEntity entity =  xjStudentDao.load(id);
			if(entity!=null){
				this.xjStudentEntity = entity;
				return true;
			}else{
				this.xjStudentEntity = new XjStudentEntity();
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
		      
		       XjStudentDao xjStudentDao= SpringUtil.getSpringBean(XjStudentDao.class,"xjStudentDao");
		       xjStudentDao.insert(this.xjStudentEntity);
		        
		       
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
		    XjStudentDao xjStudentDao= SpringUtil.getSpringBean(XjStudentDao.class,"xjStudentDao");
			xjStudentDao.update(this.xjStudentEntity);
		     
				  
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
	       XjStudentDao xjStudentDao= SpringUtil.getSpringBean(XjStudentDao.class,"xjStudentDao");
		   xjStudentDao.delete(ids);
	        
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
	 * 
	 * 功能描述：列表查询(分页)
	 * @author Ethan.Lam
	 * (non-Javadoc)
	 * @see esfw.core.framework.business.BaseQuery#query(java.io.Serializable)
	 */
	public List<XjStudentBusiness> query(XjStudentVo vo) throws BusinessException {
           // TODO Auto-generated method stub
         List<XjStudentBusiness> results = new ArrayList<XjStudentBusiness>();
		 try {
	            XjStudentDao xjStudentDao= SpringUtil.getSpringBean(XjStudentDao.class,"xjStudentDao");
			    PageBean<XjStudentEntity> pageBean = xjStudentDao.query(vo.getPageVo().getPage(),vo.getPageVo().getPageSize(),vo);
			    if(pageBean!=null && pageBean.getBeanList()!= null){
					for(XjStudentEntity et:pageBean.getBeanList()){
						results.add(new XjStudentBusiness(et));
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

