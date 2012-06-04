package newm.dao.entity.test;

import java.util.*;

import esfw.core.framework.dao.GenericEntity;

/**
 * @description fdsf 对应的实体
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:07:58 GMT 2012
 */
public class AdcSiLogEntity extends GenericEntity {
   
     
         private long  id; // 
         private String  phone; // 
         private String  studentid; // 
         private String  servicecode; //  0 订购成功     1 未知错误     3 学生编号长度不是11位或SPID错误或学生不存在     4 绑定的手机号码不合法     5 扣费手机不是移动号码     6 业务类型不存在     7 扣费模式不存在     8 已存在定购关系（成功的或正在处理中的）     20 SPInfo为空值     21 SPID为空     22 SPPassword为空     23 密码错误     9001 数据库操作异常     9 IP鉴权不通过 
         private String  ip; // 
         private Date  dt; //创建时间
    
    
     
        //默认空构造函数
	  public AdcSiLogEntity(){
	
	  }
	
	
	
	
	 
         /**
          * @param id 
          */
         public void setId(long id){
	        this.id=id;    
         }
         /**
          * @return id 
          */
         public long getId(  ){ 
	        return this.id;    
         }
     
         /**
          * @param phone 
          */
         public void setPhone(String phone){
	        this.phone=phone;    
         }
         /**
          * @return phone 
          */
         public String getPhone(  ){ 
	        return this.phone;    
         }
     
         /**
          * @param studentid 
          */
         public void setStudentid(String studentid){
	        this.studentid=studentid;    
         }
         /**
          * @return studentid 
          */
         public String getStudentid(  ){ 
	        return this.studentid;    
         }
     
         /**
          * @param servicecode   0 订购成功     1 未知错误     3 学生编号长度不是11位或SPID错误或学生不存在     4 绑定的手机号码不合法     5 扣费手机不是移动号码     6 业务类型不存在     7 扣费模式不存在     8 已存在定购关系（成功的或正在处理中的）     20 SPInfo为空值     21 SPID为空     22 SPPassword为空     23 密码错误     9001 数据库操作异常     9 IP鉴权不通过
          */
         public void setServicecode(String servicecode){
	        this.servicecode=servicecode;    
         }
         /**
          * @return servicecode   0 订购成功     1 未知错误     3 学生编号长度不是11位或SPID错误或学生不存在     4 绑定的手机号码不合法     5 扣费手机不是移动号码     6 业务类型不存在     7 扣费模式不存在     8 已存在定购关系（成功的或正在处理中的）     20 SPInfo为空值     21 SPID为空     22 SPPassword为空     23 密码错误     9001 数据库操作异常     9 IP鉴权不通过
          */
         public String getServicecode(  ){ 
	        return this.servicecode;    
         }
     
         /**
          * @param ip 
          */
         public void setIp(String ip){
	        this.ip=ip;    
         }
         /**
          * @return ip 
          */
         public String getIp(  ){ 
	        return this.ip;    
         }
     
         /**
          * @param dt 创建时间
          */
         public void setDt(Date dt){
	        this.dt=dt;    
         }
         /**
          * @return dt 创建时间
          */
         public Date getDt(  ){ 
	        return this.dt;    
         }
    
    
     

}
