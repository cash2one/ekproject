package cn.elam.lucene.demo;

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
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

 
 
public class LuceneIndexHtml {
 
   
	public static void main(String...args) throws Exception{
		
		
		//A创建索引  
        //简单例子，创建索引保存在内存中  
        Directory dir = new RAMDirectory();  
        //分词器使用lucence标准的，参数Version指定使用的lucence版本  
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);  
        /* 
         * IndexWriter 创建和维护索引 
         * param1  
         * param2 指定创建索引使用的分词器 
         * param3 是否覆盖已有索引 
         * param4 最大Field长度 
         *  
         */  
        IndexWriter writer = new IndexWriter(  
                dir,analyzer,true,IndexWriter.MaxFieldLength.UNLIMITED);  
        //添加索引  
        String[][] docs ={  
        		{"java_test","hello java "},  
        		{"doc_test","search aha java"},  
        		{"java_run","friday hello"}  
        };  
        /* 
         * Document是Field的容器，被搜索的单元，搜索结果是已doc的形式返回 
         * 每个Field拥有Key，Value 
         */  
        for(int i = 0;i<docs.length;i++){  
            Document d = new Document();  
            d.add(new Field("title" //key  
                            ,docs[i][0]//value  
                            ,Store.YES//是否存储在索引中  
                            ,Index.ANALYZED//该Field是否被索引  
            )); 
            d.add(new Field("content" //key  
                    ,docs[i][1]//value  
                    ,Store.YES//是否存储在索引中  
                    ,Index.ANALYZED//该Field是否被索引  
    ));  
            writer.addDocument(d);//将doc交给writer处理  
        }  
        writer.close();  
        
        
        
      //B 搜索  
        //创建搜索对象  
        //参数是索引目录，指定为上面创建的索引  
        IndexSearcher searcher = new IndexSearcher(dir);  
        QueryParser parser = new QueryParser(  
                            Version.LUCENE_30 //指定版本  
                            ,"content" //默认搜索的Feild（key）  
                            ,analyzer//搜索用的分词器，和上面创建索引使用一样的  
                            );  
        Query query = parser.parse("java"/*需要解析搜索的关键词*/);  
        //搜索，取结果的前5条  
        ScoreDoc[] tds = searcher.search(query,5).scoreDocs;  
        System.out.println(tds.length);//符合结果doc数  
        for(int i=0;i<tds.length;i++){  
            Document doc = searcher.doc(tds[i].doc);  
            //打印doc中key是name的Field的value  
            System.out.println(doc.get("title")+" : "+doc.get("content"));  
        }  
        /*输出结果 
         *  2 
            hello java 
            friday hello 
         */  
		
		
	}
	
	
	
	
	
}