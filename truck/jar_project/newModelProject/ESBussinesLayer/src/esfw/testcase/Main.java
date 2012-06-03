package esfw.testcase;

import java.util.ArrayList;
import java.util.List;

import model.business.ModelBusiness;
import model.vo.ModelVo;
import qtone.xxt.business.amass.AmassCentInfo;
import esfw.core.framework.MethodAdapter;
import esfw.core.framework.SpringUtil;
import esfw.core.framework.business.BusinessContainer;
import esfw.core.framework.dao.mapper.OrderItem;
import esfw.core.framework.dao.mapper.OrderItem.OrderOption;
import esfw.core.framework.exception.BusinessException;

/**
 * 
 * 测试类
 * @author Ethan.Lam  2012-5-18
 *
 */
public class Main {

	 public static void main(String...args) throws Exception{
		 BusinessContainer.getInstance();
//		 case1(null); //原来模式的
//		 case_mdf();
//		 case_del();
		 case_query(); //新的模式
//		 case_mdf();
//		 case_insert();
//		 case_query(); //新的模式
		 
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
	
     
     public static void case_query() {
    	 try {
	    	 ModelBusiness info  = SpringUtil.getSpringBean(ModelBusiness.class,"modelBusiness");
	    	 ModelVo vo = new ModelVo();
	    	 //分页设置
	    	 vo.getPageVo().setPage(1);
	    	 vo.getPageVo().setPageSize(50);
	    	 
	    	 //条件查询
	    	 vo.setSchoolName("%测试%");
	    	 
	    	 //排序控制
	    	 List<OrderItem> orderSet = new ArrayList<OrderItem>();
	    	 orderSet.add(new OrderItem("schoolName",OrderOption.DESC));
	         vo.setOrderList(orderSet);
	         //执行查询
	    	 info.query(vo);
		  } catch (BusinessException e) {
		 
		  }
	 }
     
     public static void case_mdf() {
    	 try {
	    	 ModelBusiness info  = SpringUtil.getSpringBean(ModelBusiness.class,"modelBusiness");
	    	 info.load((long)1); //查找记录
	    	 System.out.println("before case_mdf: id:"+info.getId()+" , "+info.getSchoolName());
	    	 info.setSchoolName("测试学校");
	    	 info.modify(); // 提交修改的
	    	 info.load((long)1);
	    	 System.out.println("after case_mdf: id:"+info.getId()+" , "+info.getSchoolName());
		} catch (BusinessException e) {
		}
	 }
     
     public static void case_insert(){
    	 ModelBusiness info  = SpringUtil.getSpringBean(ModelBusiness.class,"modelBusiness");
    	 try {
			info.add();
		 } catch (Exception e) {
		 }
	 }
	
     
     public static void case_del(){
    	 ModelBusiness info  = SpringUtil.getSpringBean(ModelBusiness.class,"modelBusiness");
    	 try {
			info.delete(new Long[]{(long) 9363});
		 } catch (Exception e) {
		 }
	 }
}
