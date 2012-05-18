package esfw.core.framework.dao.batch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import esfw.core.framework.SpringUtil;



/**
 * 
 * 批量处理接口方法的调用方式
 * @author Ethan.Lam  2011-7-29
 *
 */
public class DataBatchDao{

	String sqlSessionFactoryBean ="sqlSessionFactory";
	
	
	/**
	 * 方法：执行对应的批量接口方法
	 * @param daoAbb    地区分表的前缀    "cs"   
	 * @param mapperClazz   
	 * @param datas     数据集合对象（实体类）
	 * @throws Exception
	 *    Add By Ethan Lam  At 2011-7-28
	 *  
	 */
    @SuppressWarnings("unchecked")
	public void insert(String daoAbb,Class mapperClazz,Collection<BatchData> datas) throws Exception { 
    	if(datas==null)
             return;
    	
        String mapperClazzName = mapperClazz.getName();
        //对应的插入方法接口
        String methodName = "insert"+mapperClazzName.substring(mapperClazzName.lastIndexOf(".")+1,mapperClazzName.indexOf("Mapper"));
        String entryName = mapperClazzName.replace("mapper","entity").replace("Mapper", "Entry");
       
        //分批执行新增操作
        int seq = 1;
        int times = 0;
        int bactchSize = 20;
        int allSize = datas.size();
        Collection<BatchData> tempDatas = new ArrayList<BatchData>();
        for(BatchData data:datas){
        	if(seq%bactchSize>0){
        		tempDatas.add(data);
        	}else{
        		action(daoAbb,mapperClazzName,mapperClazz,methodName,entryName,tempDatas);
        		tempDatas.clear();
        		seq = 1;
        		System.out.println("分批执行新增操作: 当前批次为:"+(++times)+"，共有 ："+(allSize/bactchSize)+"批次,"+allSize+"!");
        	}
        }
        if(tempDatas.size()>0){
        	action(daoAbb,mapperClazzName,mapperClazz,methodName,entryName,tempDatas);
        	System.out.println("分批执行新增操作: 当前批次为:"+(++times)+"，共有 ："+(allSize/bactchSize)+"批次,"+allSize+"!");
        }
        
    } 
    
    /**
     * 
     * @param daoAbb
     * @param mapperClazzName
     * @param mapperClazz
     * @param methodName
     * @param entryName
     * @param datas
     * @throws Exception
     */
    void action(String daoAbb,String mapperClazzName,Class mapperClazz,String methodName ,String entryName,Collection<BatchData> datas ) throws Exception{
    	SqlSessionFactory sqlSessionFactory = SpringUtil.getSpringBean(SqlSessionFactory.class,sqlSessionFactoryBean);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        Class entry = Class.forName(entryName);
        Class[] params = daoAbb==null||"".equals(daoAbb)?new Class[]{entry}:new Class[]{String.class,entry};
        Method insertMetod = mapperClazz.getMethod(methodName,params);
        try { 
        	BatchMapper datasetDataMapper = (BatchMapper) sqlSession.getMapper(mapperClazz); 
            for (BatchData data: datas) { 
            	  if(daoAbb==null||"".equals(daoAbb))
            	    insertMetod.invoke(datasetDataMapper,data);
            	  else
            	    insertMetod.invoke(datasetDataMapper,daoAbb,data);
            }
            sqlSession.commit(); 
        }catch(Exception e){
        	e.printStackTrace();
        }finally { 
            sqlSession.close();
            sqlSession = null;
            sqlSessionFactory = null;
        }
    
    }
    
    
    /**
     * 
	 * 方法：执行对应的批量接口方法
	 * @param mapperClazz   
	 * @param datas     数据集合对象（实体类）
	 * @throws Exception
	 *    Add By Ethan Lam  At 2011-7-28
	 *  
	 */
    @SuppressWarnings("unchecked")
	public void insert(Class mapperClazz,Collection<BatchData> datas) throws Exception { 
    	if(datas==null)
             return;
    	insert(null,mapperClazz,datas);
    } 
    
     
}
