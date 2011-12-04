package cn.elam.reptilerobot.indexs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cn.elam.reptilerobot.base.Page;
import cn.elam.reptilerobot.persistent.IPageService;
import cn.elam.reptilerobot.persistent.imps.PageServiceImp;
import cn.elam.reptilerobot.utils.LoggerUtil;

/**
 * 
 * 创建索引文件到 磁盘中
 * @author Ethan.Lam  
 * @createTime 2011-12-4
 *
 */
public class IndexDirServiceImp implements IIndexService{

	Version version = Version.LUCENE_34;
	
	private String indexDirPath;
	
	public IndexDirServiceImp(String indexDirPath){
		this.indexDirPath = indexDirPath;
	}
	
	
	@Override
	public void createIndexs(Field[] fields, String[][] docs,boolean isAppend) throws Exception {
		// TODO Auto-generated method stub
		
		// 注意：这里建立索引用的分词方法，在搜索时分词也应该采用同样的分词方法。不然搜索数据可能会不正确
		// 使用Lucene自带分词器
		Analyzer luceneAnalyzer = new StandardAnalyzer(version);
		
		IndexWriterConfig indexWriterconfig = new IndexWriterConfig(version, luceneAnalyzer);
		indexWriterconfig.setMaxBufferedDocs(100);
		indexWriterconfig.setOpenMode(isAppend?OpenMode.APPEND:OpenMode.CREATE_OR_APPEND);
		
		File indexDir = new File(indexDirPath);
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir),indexWriterconfig);
		long startTime = new Date().getTime();
		// 增加索引字段
		// 在Field中有三个内部类：Field.Index,Field.Store,Field.termVector，而构造函数也用到了它们。
		// 参数说明：
		// Field.Store：
		// Field.Store.NO：表示该Ｆield不需要存储。
		// Field.Store.Yes：表示该Ｆield需要存储。
		// Field.Store.COMPRESS：表示使用压缩方式来存储。
		// Field.Index：
		// Field.Index.NO：表示该Ｆield不需要索引。
		// Field.Index.TOKENIZED：表示该Ｆield先被分词再索引。
		// Field.Index.UN_TOKENIZED：表示不对该Ｆield进行分词，但要对其索引。
		// Field.Index.NO_NORMS：表示该Ｆield进行索引，但是要对它用Analyzer，同时禁止它参加评分，主要是为了减少内在的消耗。
		// TermVector这个参数也不常用，它有五个选项。
//		                Field.TermVector.NO表示不索引Token的位置属性；
//		                Field.TermVector.WITH_OFFSETS表示额外索引Token的结束点；
//		                Field.TermVector.WITH_POSITIONS表示额外索引Token的当前位置；
//		                Field.TermVector.WITH_POSITIONS_OFFSETS表示额外索引Token的当前和结束位置；
//		                Field.TermVector.YES则表示存储向量。
		// 增加文档 Field相当于增加数据库字段一样检索,获取都需要的内容,直接放index中,不过这样会增大index,保存文件的txt内容
	   /**
		* Field.Store 表示“是否存储”，即该Field内的信息是否要被原封不动的保存在索引中。
		* Field.Index 表示“是否索引”，即在这个Field中的数据是否在将来检索时需要被用户检索到，一个“不索引”的Field通常仅是提供辅助信息储存的功能。
		* Field.TermVector 表示“是否切词”，即在这个Field中的数据是否需要被切词。
		*/
	   int fieldIndex = 0;
	   for(int i = 0;i<docs.length;i++){  
	          Document d = new Document();
	          fieldIndex = 0;
	          for(Field field : fields){
	              field.setValue(docs[i][fieldIndex++]);
		          d.add(field); 
	          }
	          indexWriter.addDocument(d);//将doc交给writer处理  
	    }  
	    indexWriter.optimize();
		indexWriter.close();
		// 若需要从索引中删除某一个或者某一类文档，IndexReader提供了两种方法：
		// reader.DeleteDocument(int docNum)
		// reader.DeleteDocuments(Term term)
		// 前者是根据文档的编号来删除该文档，docNum是该文档进入索引时Lucene的编号，是按照顺序编的；后者是删除满足某一个条件的多个文档。
		// 在执行了DeleteDocument或者DeleteDocuments方法后，系统会生成一个*.del的文件，该文件中记录了删除的文档，但并未从物理上删除这些文档。此时，这些文档是受保护的，当使用Document
		// doc = reader.Document(i)来访问这些受保护的文档时，Lucene会报“Attempt to access a
		// deleted document”异常。如果一次需要删除多个文档时，可以用两种方法来解决：
		// 1. 删除一个文档后，用IndexWriter的Optimize方法来优化索引，这样我们就可以继续删除另一个文档。
		// 2. 先扫描整个索引文件，记录下需要删除的文档在索引中的编号。然后，一次性调用DeleteDocument删除这些文档，再调用IndexWriter的Optimize方法来优化索引。

		long endTime = new Date().getTime();
		System.out.println("\n这花费了" + (endTime - startTime) + " 毫秒增加到索引!");
	}

	
	@Override
	public List<Document> query(String queryFeildKey, String queryStr, int top)
			throws Exception {
		// 分词器使用lucence标准的，参数Version指定使用的lucence版本
		Analyzer analyzer = new StandardAnalyzer(version);
		// B 搜索
		// 创建搜索对象
		// 参数是索引目录，指定为上面创建的索引
		File indexDir = new File(indexDirPath);
		IndexSearcher searcher = new IndexSearcher(FSDirectory.open(indexDir));
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
		
    	IndexDirServiceImp indexSer = new IndexDirServiceImp("d:/luncene/sina_news");
		IPageService ps = new PageServiceImp();
		
        List<Page> pages = ps.loadByPage(1, 2000);
        long ct = System.currentTimeMillis();
        String[][] docs = new String[pages.size()][];
        int index = 0;
        for(Page n:pages){
//	    	LoggerUtil.p("testPage->loadListByPageIds: "+index+"",n.getId(),n.getUrl(),n.getSegment());
	        docs[index++]=new String[]{n.getSegment(),n.getId()+""};
	    }
        
        Field[] fields = new Field[]{
        		new Field("content", "", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS),
        		new Field("id", "", Field.Store.NO, Field.Index.NOT_ANALYZED, Field.TermVector.NO)
        }; 
        
        indexSer.createIndexs(fields,docs,false);
        LoggerUtil.p("建立索引的耗时：",(System.currentTimeMillis()-ct)+"毫秒");
	
        ct = System.currentTimeMillis();
        List<Document> rdocs = indexSer.query("content", "交通事故",20);
        if(rdocs!=null)
        for(Document d:rdocs){
        	LoggerUtil.p("全文搜索结果:",d.get("content"));
        }
        LoggerUtil.p("全文搜索的耗时：",(System.currentTimeMillis()-ct)+"毫秒");
        
	}

}
