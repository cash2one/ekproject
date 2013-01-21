package esfw.core.framework.dao.extend.interceptor;

import java.util.HashMap;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;


@Intercepts( { @Signature(type = Executor.class, method = "query", args = {  
    MappedStatement.class, Object.class, RowBounds.class,  
    ResultHandler.class }) })  
public class TestExecutor  implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("...................TestExecutor...............");
		Object parameter = invocation.getArgs()[1]; 
//		((HashMap)(invocation.getArgs()[1])).put("TEST","fdsfdsfds");
//		((HashMap)(invocation.getArgs()[1])).put("siId",100);
//		((HashMap)(invocation.getArgs()[1])).put("siId",100);
		
		 return invocation.proceed();
	}

	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

}
