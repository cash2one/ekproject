package cn.elamzs.common.eimport.Anotation;

/**
 * 定义数据导入中包含的列的属性设置
 * @author Ethan.Lam
 *
 */
public @interface ValidatorRule {

	String name(); //导入的列名 ，需要唯一
	
	boolean check() default false; //设置赋值后是否需要验证值的合法性
	
	
}
