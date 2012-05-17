package esfw.base.util;

import java.text.DecimalFormat;

/**
 * 数值类型处理
 * @author Ethan.Lam  
 * @createTime 2011-9-14
 *
 */
public class NumericalUtil {

	static DecimalFormat df2  = new DecimalFormat("###.00");
	
	public static float format(float number){
		return Float.parseFloat(df2.format(number));
	}
	
}
