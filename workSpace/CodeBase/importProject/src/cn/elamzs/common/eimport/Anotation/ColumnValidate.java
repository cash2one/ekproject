package cn.elamzs.common.eimport.Anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 定义数据导入中包含的列的属性设置
 * @author Ethan.Lam
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ColumnValidate {

	String ename(); //导入的列名 ，需要唯一
	
	String showName(); //文本中显示的列名
	
	int columnSeq(); //对应导入文件的列序号    从 0 开始
	
	IndexedColors color() default IndexedColors.BLACK;
	
	int width() default 50; //列宽设置
	
	String comment() default ""; //批注
	
	boolean check() default false; //设置赋值后是否需要验证值的合法性
	
	
}
