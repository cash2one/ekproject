package esfw.core.dao.batch;

import esfw.core.dao.mapper.MyBatisMapper;

/**
 * 
 * 批量处理接口类
 * 
 * @author Ethan.Lam
 * @createTime 2011-7-28
 * 
 */
public interface BatchMapper extends MyBatisMapper {

	public void insert(BatchData data);

}