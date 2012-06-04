package newm.dao.entity.test;

import java.util.*;

import esfw.core.framework.dao.GenericEntity;

/**
 * @description fsdf 对应的实体
 * @version v1.0.0
 * @author Ethanlam  
 * @CreateTime Mon Jun 04 13:00:20 GMT 2012
 */
public class AdcSyncLogEntity extends GenericEntity {
   
     
         private long  id; // 
         private String  xml; //adc响应的内容 服务开通成功  SI201 ADCProvisioningResponse 服务状态变更成功 SI202 ServiceStateResponseEC基本信息同步成功 SI107 ECInfoResponse 
         private String  eccode; //学校eccode 
         private long  type; //1 3正向接收，4正向响应 
         private Date  dt; //创建时间 
         private String  schoolName; //
    
    
     
        //默认空构造函数
	  public AdcSyncLogEntity(){
	
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
          * @param xml adc响应的内容 服务开通成功  SI201 ADCProvisioningResponse 服务状态变更成功 SI202 ServiceStateResponseEC基本信息同步成功 SI107 ECInfoResponse
          */
         public void setXml(String xml){
	        this.xml=xml;    
         }
         /**
          * @return xml adc响应的内容 服务开通成功  SI201 ADCProvisioningResponse 服务状态变更成功 SI202 ServiceStateResponseEC基本信息同步成功 SI107 ECInfoResponse
          */
         public String getXml(  ){ 
	        return this.xml;    
         }
     
         /**
          * @param eccode 学校eccode
          */
         public void setEccode(String eccode){
	        this.eccode=eccode;    
         }
         /**
          * @return eccode 学校eccode
          */
         public String getEccode(  ){ 
	        return this.eccode;    
         }
     
         /**
          * @param type 1 3正向接收，4正向响应
          */
         public void setType(long type){
	        this.type=type;    
         }
         /**
          * @return type 1 3正向接收，4正向响应
          */
         public long getType(  ){ 
	        return this.type;    
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
     
         /**
          * @param schoolName 
          */
         public void setSchoolName(String schoolName){
	        this.schoolName=schoolName;    
         }
         /**
          * @return schoolName 
          */
         public String getSchoolName(  ){ 
	        return this.schoolName;    
         }
    
    
     

}
