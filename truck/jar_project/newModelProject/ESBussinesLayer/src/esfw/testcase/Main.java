package esfw.testcase;

import java.util.List;

import qtone.xxt.business.amass.AmassCentInfo;
import esfw.core.SpringUtil;
import esfw.core.business.BusinessContainer;
import esfw.core.helper.MethodAdapter;

/**
 * 
 * 测试类
 * @author Ethan.Lam  2012-5-18
 *
 */
public class Main {

	 public static void main(String...args) throws Exception{
		 BusinessContainer.getInstance();
		 case1(null);
	 }
	 
	 /**
	  * 检查框架是否正常
	  * @param arg
	  * @throws Exception
	  */
     public static void case1(String... arg) throws Exception{
			BusinessContainer.getInstance();
			AmassCentInfo info  = SpringUtil.getSpringBean(AmassCentInfo.class,"amassCentInfo");
//			AmassCentInfo info  = new  AmassCentInfo();
			 System.out.println("测试环境是否正常...");
			info.setDaoAbb("cs");
			List<AmassCentInfo> cents = (List<AmassCentInfo>)MethodAdapter.invoker(info, "queryAmassCents",true,new Object[][]{{"schoolId",1}});
			if(cents!=null)
			for(AmassCentInfo cent : cents){
			     System.out.println("学校ID："+cent.getSchoolId()+",用户名："+cent.getUserId()+"，积分情况： "+cent.getCent()+"  ");
		    }
		    System.out.println("SpringUtil.getSpringBean 正常...");
	 }
	
	
}