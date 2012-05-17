package esfw.core.inter;

/**
 * 
 * Spring 容器接口方法
 * @author Ethan.Lam  2011-7-12
 *
 */
public interface ISpringContext {
	public  <T> T getSpringBean(Class<T> clazz,String name); 
}
