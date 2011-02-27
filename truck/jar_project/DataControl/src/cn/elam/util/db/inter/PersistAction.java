package cn.elam.util.db.inter;

import java.util.Map;

/**
 * 对象持久化 接口
 * @author Ethan.Lam 2011-2-27
 * 
 * @param <Model>
 */
public interface PersistAction<Model extends DataModel> {
      
	/**
	 * 创建持久化的字段信息
	 * @throws Exception
	 */
	public Map<String,Object> persistParamValues(Model data) throws Exception;

}
