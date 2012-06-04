package newm;

import java.util.ArrayList;
import java.util.List;

import newm.business.test.XjClassBusiness;
import newm.business.test.XjStudentBusiness;
import newm.business.test.XjTeacherBusiness;
import newm.vo.test.XjClassVo;
import newm.vo.test.XjStudentVo;
import newm.vo.test.XjTeacherVo;
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
//		 case_query2(); //新的模式
		 case_query_stu("测试%");
//		 case_del();
//		 case_insert();
//		 case_query(); //新的模式
		 
	 }
	 
	 /**
	  * 检查框架是否正常
	  * @param arg
	  * @throws Exception
	  */
     public static void case_query() {
    	 try {
    		 
	    	 XjClassBusiness info  = SpringUtil.getSpringBean(XjClassBusiness.class,"xjClassBusiness");
    	     XjClassVo vo = new XjClassVo();
	    	 //分页设置
	    	 vo.getPageVo().setPage(1);
	    	 vo.getPageVo().setPageSize(50);
	    	 
	         //条件查询
//	    	 vo.setSchoolName("%测试%");
	    	 vo.setSchoolId(1);
	    	 vo.setGradeId(3);
	    	 
	    	 //排序控制
	    	 List<OrderItem> orderSet = new ArrayList<OrderItem>();
	    	 orderSet.add(new OrderItem("className",OrderOption.DESC));
	         vo.setOrderList(orderSet);
	         //执行查询
	    	 List<XjClassBusiness> results = info.query(vo);
	    	 for(XjClassBusiness school:results){
	    		 System.out.println("id:" +school.getId() +"  class : "+school.getClassName());
	    	 }
	    	 
		  } catch (BusinessException e) {
		 
		  }
	 }
     
     public static void case_query_stu(String stuName) {
    	 try {
    		 
	    	 XjStudentBusiness info  = SpringUtil.getSpringBean(XjStudentBusiness.class,"xjStudentBusiness");
	    	 XjStudentVo vo = new  XjStudentVo();
	    	 //分页设置
	    	 vo.getPageVo().setPage(1);
	    	 vo.getPageVo().setPageSize(50);
	    	 vo.setDaoAbb("cs");
	    	 
	    	 //条件查询
//	    	 vo.setSchoolName("%测试%");
	    	 vo.setName(stuName);
	    	 
	    	 //排序控制
	    	 List<OrderItem> orderSet = new ArrayList<OrderItem>();
	    	 orderSet.add(new OrderItem("className",OrderOption.DESC));
	         vo.setOrderList(orderSet);
	         
	         //执行查询
	    	 List<XjStudentBusiness> results = info.query(vo);
	    	 for(XjStudentBusiness stu:results){
	    		 System.out.println("id:" +stu.getId() +"  sqe : "+stu.getStuSequence()+"  name : "+stu.getName()+"  "+stu.getClassName());
	    	 }
	    	 
	    	 System.out.println(".........................查询学生数据.........................");
	    	 XjTeacherBusiness xjTeacherBusiness = SpringUtil.getSpringBean(XjTeacherBusiness.class,"xjTeacherBusiness");
	    	 XjTeacherVo vot = new XjTeacherVo();
	    	 vot.setSchoolId(1);
	    	 List<XjTeacherBusiness> res = xjTeacherBusiness.query(vot);
	    	 for(XjTeacherBusiness t:res){
	    		 System.out.println("id:" +t.getId() +"  name : "+t.getUsername()+"  schoolId :"+t.getSchoolId());
	    	 }
	    	 System.out.println(".........................查询教师.........................");
	    	 
	    	 xjTeacherBusiness.load((long)1296594);
	    	 System.out.println(".........................查询教师.........................id:"+xjTeacherBusiness.getId() +" name:"+xjTeacherBusiness.getUsername());
	    	 xjTeacherBusiness.delete(new Long[]{(long)1296594});
	    	 xjTeacherBusiness.load((long)1296594);
	    	 System.out.println(".........................删除后查询教师.........................id:"+xjTeacherBusiness.getId() +" name:"+xjTeacherBusiness.getUsername());
		  } catch (BusinessException e) {
		 
		  }
	 }
     
     public static void case_mdf() {
    	 try {
    		 XjStudentBusiness info  = SpringUtil.getSpringBean(XjStudentBusiness.class,"xjStudentBusiness");
    		 info.setDaoAbb("cs");
    		 
//    		 info.load((long)611677); //查找记录
//    		 System.out.println("before case_mdf: id:"+info.getId()+" , "+info.getName());
//	    	 info.setName(info.getName()+"---Test");
//	    	 info.modify();//修改
	    	 
	    	 info.load((long)611677); //查找记录
	    	 System.out.println("atfer case_mdf: id:"+info.getId()+" , "+info.getName());
		} catch (BusinessException e) {
		}
	 }
     
     public static void case_insert(){
    	 try {
    		 XjStudentBusiness info  = SpringUtil.getSpringBean(XjStudentBusiness.class,"xjStudentBusiness");
    		 info.setDaoAbb("cs");
    		 case_query_stu("新导测_18%"); //查找
    		 System.out.println("..........新增前查找到的记录...................");
    		 info.load((long)611677); //查找记录
    		 info.add(); //新增同样的记录
    		 System.out.println("..........新增后记录的主键为:"+info.getId());
    		 case_query_stu("新导测_18%"); //查找
    		 System.out.println("..........新增后查找到的记录.....................");
		 } catch (Exception e) {
		 }
	 }
     
 
	
     
     public static void case_del(){
    	 try {
    	    XjStudentBusiness info  = SpringUtil.getSpringBean(XjStudentBusiness.class,"xjStudentBusiness");
    	    info.setDaoAbb("cs");
   		    case_query_stu("新导测_18%"); //查找
   	    	System.out.println("..........删除前查找到的记录...................");
			info.delete(new Long[]{(long) 612140,(long) 612141,(long) 612142});
   		    case_query_stu("新导测_18%"); //查找
   		    System.out.println("..........删除后查找到的记录...................");
		 } catch (Exception e) {
		 }
	 }
}
