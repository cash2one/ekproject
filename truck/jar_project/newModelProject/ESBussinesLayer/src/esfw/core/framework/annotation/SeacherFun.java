package esfw.core.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)   //用于构造方法
@Retention(RetentionPolicy.RUNTIME) //在运行时加载到Annotation到JVM中
public @interface SeacherFun {

	/**
	 * 函数的别名,当有多处重载时可以通过其来标别不同的函数
	 * 
	 * @return
	 */
	String nameAlias();

}
