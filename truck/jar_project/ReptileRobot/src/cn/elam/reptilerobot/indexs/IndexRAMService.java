package cn.elam.reptilerobot.indexs;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import cn.elam.reptilerobot.base.Page;
import cn.elam.reptilerobot.persistent.IPageService;
import cn.elam.reptilerobot.persistent.imps.PageServiceImp;
import cn.elam.reptilerobot.utils.LoggerUtil;

/**
 * 
 * 建立内存（临时）索引器
 * @author Ethan.Lam
 * @createTime 2011-12-4
 * 
 */
public class IndexRAMService implements IIndexService {
	
	Version version = Version.LUCENE_34;
	
	// 简单例子，创建索引保存在内存中
	Directory dir = new RAMDirectory();
	
	/**
	 * 
	 * 方法：创建索引
	 * 
	 * @param fields  关键字
	 * @param docs    文档内容
	 * @throws Exception
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public void createIndexs(Field[]fields,String[][] docs,boolean isAppend) throws Exception {
		
		if(docs==null||fields==null)
			return;
	
		// 分词器使用lucence标准的，参数Version指定使用的lucence版本
		Analyzer analyzer = new StandardAnalyzer(version);
       
		/* 
         * IndexWriter 创建和维护索引 
         * param1  
         * param2 指定创建索引使用的分词器 
         * param3 是否覆盖已有索引 
         * param4 最大Field长度 
         *  
         */  
        IndexWriter writer = new IndexWriter(dir,analyzer,true,IndexWriter.MaxFieldLength.UNLIMITED);  
        /* 
         * Document是Field的容器，被搜索的单元，搜索结果是已doc的形式返回 
         * 每个Field拥有Key，Value 
         */  
        int fieldIndex = 0;
        for(int i = 0;i<docs.length;i++){  
            Document d = new Document();
            fieldIndex = 0;
            for(Field field : fields){
            	field.setValue(docs[i][fieldIndex++]);
	            d.add(field); 
            }
            writer.addDocument(d);//将doc交给writer处理  
        }  
        writer.close();  
        
        
	}

	
	
	/**
	 * 
	 * 方法：查询结果
	 * 
	 * @param queryFeildKey  查询字段
	 * @param queryStr    关键字
	 * @param top  符合结果 的前几条记录
	 * @return
	 * @throws Exception
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public List<Document> query(String queryFeildKey,String queryStr,int top) throws Exception {
		
		// 分词器使用lucence标准的，参数Version指定使用的lucence版本
		Analyzer analyzer = new StandardAnalyzer(version);
		// B 搜索
		// 创建搜索对象
		// 参数是索引目录，指定为上面创建的索引
		IndexSearcher searcher = new IndexSearcher(dir);
		QueryParser parser = new QueryParser(version// 指定版本
				, queryFeildKey // 默认搜索的Feild（key）
				, analyzer// 搜索用的分词器，和上面创建索引使用一样的
		);
		Query query = parser.parse(queryStr /* 需要解析搜索的关键词 */);
		// 搜索，取结果的前5条
		ScoreDoc[] tds = searcher.search(query,top).scoreDocs;
		System.out.println(tds.length);// 符合结果doc数
		List<Document> searchResultDocs = new ArrayList<Document>();
		if(tds!=null)
		for (int i = 0; i < tds.length; i++) {
			searchResultDocs.add(searcher.doc(tds[i].doc));
		}
		return searchResultDocs;
	}

	
	
	public static void main(String...args) throws Exception{
		
		IndexRAMService indexSer = new IndexRAMService();
		IPageService ps = new PageServiceImp();
		
        List<Page> pages = ps.loadByPage(1, 1000);
        long ct = System.currentTimeMillis();
        String[][] docs = new String[pages.size()][];
        int index = 0;
        for(Page n:pages){
	    	LoggerUtil.p("testPage->loadListByPageIds: "+index+"",n.getId(),n.getUrl(),n.getSegment());
	        docs[index++]=new String[]{n.getSegment(),n.getId()+""};
	    }
        
        Field[] fields = new Field[]{
        		new Field("content", "", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS),
        		new Field("id", "", Field.Store.NO, Field.Index.NOT_ANALYZED, Field.TermVector.NO)
        }; 
        
        indexSer.createIndexs(fields,docs,false);
        LoggerUtil.p("建立索引的耗时：",(System.currentTimeMillis()-ct)+"毫秒");
		
	
        ct = System.currentTimeMillis();
        List<Document> rdocs = indexSer.query("content", "美国",5);
        if(rdocs!=null)
        for(Document d:rdocs){
        	LoggerUtil.p("全文搜索结果:",d.get("content"));
        }
        LoggerUtil.p("全文搜索的耗时：",(System.currentTimeMillis()-ct)+"毫秒");
        
	}
	
}
