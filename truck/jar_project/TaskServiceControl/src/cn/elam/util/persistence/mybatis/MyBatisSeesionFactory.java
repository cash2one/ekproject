package cn.elam.util.persistence.mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * MyBatis Session 工厂
 * @author Ethan.Lam  2011-6-23
 *
 */
public class MyBatisSeesionFactory {
	
	private static MyBatisSeesionFactory factory = new MyBatisSeesionFactory();
	private SqlSessionFactory sqlMapper = null;
	
	
	MyBatisSeesionFactory(){
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MyBatisSeesionFactory getSeesionFactory(){
		if(factory==null)
			factory = new MyBatisSeesionFactory();
		return factory;
	}
	
	/**
	 * 
	 * 方法：加载mybastis 配置文件
	 * @throws Exception
	 *  
	 *    Add By Ethan Lam  At 2011-8-26
	 */
	private void init() throws Exception{
//		DOMConfigurator.configure("src/configs/log4j.xml");
		String resource = Config.CONFIG_PATH;
		File configFileHander = new File(Config.CONFIG_PATH);
		if(configFileHander.exists()){
			sqlMapper = new SqlSessionFactoryBuilder().build(new FileInputStream(configFileHander));
		}else{
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		}
	}
	
	/**
	 * 
	 * 方法：创建一个Session
	 * 
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-8-26
	 */
	public SqlSession createSession(){
		if(sqlMapper!=null)
		   return sqlMapper.openSession();
		return null;
	}
	
	
	/**
	 * 
	 * 方法：创建一个批处理的Session
	 * 
	 * @param isBatchExecutor
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-8-26
	 */
	public SqlSession createSession(boolean isBatchExecutor){
		if(sqlMapper!=null)
			 return sqlMapper.openSession(ExecutorType.BATCH, isBatchExecutor);
		return null;
	}
	
}
