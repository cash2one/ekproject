package cn.qtone.xxt.csop.util;

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
