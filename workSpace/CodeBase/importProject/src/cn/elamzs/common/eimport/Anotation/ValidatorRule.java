package cn.elamzs.common.eimport.Anotation;

/**
 * 定义数据导入中包含的列的属性设置
 * @author Ethan.Lam
 *
 */
public @interface ValidatorRule {

	String ename(); //导入的列名 ，需要唯一
	
	int column(); //对应导入文件的列序号
	
	boolean check() default false; //设置赋值后是否需要验证值的合法性
	
	
}
