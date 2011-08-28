package cn.elam.util.common;

/**
 * 数据类型 转换
 * @author Ethan.Lam   2011-2-26
 *
 */
public class Trans {

	public static long StringTolong(String txt) {
		if (!Checker.isNull(txt))
			return Long.parseLong(txt);
		else
			return 0;
	}

	public static int StringToInt(String txt) {
		if (!Checker.isNull(txt))
			return Integer.parseInt(txt);
		else
			return 0;
	}

}
