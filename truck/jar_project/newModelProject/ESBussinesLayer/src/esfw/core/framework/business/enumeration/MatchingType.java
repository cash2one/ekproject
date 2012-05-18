package esfw.core.framework.business.enumeration;

/**
 * 字符串查询条件匹配类型
 * 
 * @author Ethan.Lam 2011-7-24
 * 
 */

public enum MatchingType {

	EXACT, // 精确匹配 content
	LEFT_FUZZY, // 左匹配 模糊查询 %content
	RIGHT_FUZZY, // 右匹配 模糊查询 content%
	ALL_FUZZY; // 模糊查询 %content%

}