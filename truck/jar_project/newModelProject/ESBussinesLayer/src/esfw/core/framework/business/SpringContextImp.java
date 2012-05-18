package esfw.core.framework.business;

import org.springframework.context.ApplicationContext;

import esfw.core.framework.ISpringContext;



/**
 * 
 * 当单独运行业务模块时（单应用模式），
 * 可以用此作为默认的 Spring Context 环境，以此作为上下文
 * @author Ethan.Lam  2011-7-12
 *
 */
public class SpringContextImp implements ISpringContext{

	public <T> T getSpringBean(Class<T> clazz, String name) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = BusinessContainer.getInstance().getApplicationContext();
		return (T) ctx.getBean(name);
	}

	 
}
