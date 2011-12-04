package cn.elam.reptilerobot.indexs;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * 
 * 索引服务接口
 * @author Ethan.Lam  
 * @createTime 2011-12-4
 *
 */
public interface IIndexService {

	/**
	 * 
	 * 方法：创建索引
	 * 
	 * @param fields
	 * @param docs
	 * @param isAppend
	 * @throws Exception
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public void createIndexs(Field[]fields,String[][] docs,boolean isAppend)throws Exception; 
	
	
	/**
	 * 
	 * 方法：查询索引
	 * 
	 * @param queryFeildKey
	 * @param queryStr
	 * @param top
	 * @return
	 * @throws Exception
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public List<Document> query(String queryFeildKey,String queryStr,int top) throws Exception;
	
	
}
