package cn.elam.reptilerobot.indexs;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * 
 * ��������ӿ�
 * @author Ethan.Lam  
 * @createTime 2011-12-4
 *
 */
public interface IIndexService {

	/**
	 * 
	 * ��������������
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
	 * ��������ѯ����
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
