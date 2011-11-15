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
		
		
		//A��������  
        //�����ӣ����������������ڴ���  
        Directory dir = new RAMDirectory();  
        //�ִ���ʹ��lucence��׼�ģ�����Versionָ��ʹ�õ�lucence�汾  
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);  
        /* 
         * IndexWriter ������ά������ 
         * param1  
         * param2 ָ����������ʹ�õķִ��� 
         * param3 �Ƿ񸲸��������� 
         * param4 ���Field���� 
         *  
         */  
        IndexWriter writer = new IndexWriter(  
                dir,analyzer,true,IndexWriter.MaxFieldLength.UNLIMITED);  
        //�������  
        String[][] docs ={  
        		{"java_test","hello java "},  
        		{"doc_test","search aha java"},  
        		{"java_run","friday hello"}  
        };  
        /* 
         * Document��Field���������������ĵ�Ԫ�������������doc����ʽ���� 
         * ÿ��Fieldӵ��Key��Value 
         */  
        for(int i = 0;i<docs.length;i++){  
            Document d = new Document();  
            d.add(new Field("title" //key  
                            ,docs[i][0]//value  
                            ,Store.YES//�Ƿ�洢��������  
                            ,Index.ANALYZED//��Field�Ƿ�����  
            )); 
            d.add(new Field("content" //key  
                    ,docs[i][1]//value  
                    ,Store.YES//�Ƿ�洢��������  
                    ,Index.ANALYZED//��Field�Ƿ�����  
    ));  
            writer.addDocument(d);//��doc����writer����  
        }  
        writer.close();  
        
        
        
      //B ����  
        //������������  
        //����������Ŀ¼��ָ��Ϊ���洴��������  
        IndexSearcher searcher = new IndexSearcher(dir);  
        QueryParser parser = new QueryParser(  
                            Version.LUCENE_30 //ָ���汾  
                            ,"content" //Ĭ��������Feild��key��  
                            ,analyzer//�����õķִ����������洴������ʹ��һ����  
                            );  
        Query query = parser.parse("java"/*��Ҫ���������Ĺؼ���*/);  
        //������ȡ�����ǰ5��  
        ScoreDoc[] tds = searcher.search(query,5).scoreDocs;  
        System.out.println(tds.length);//���Ͻ��doc��  
        for(int i=0;i<tds.length;i++){  
            Document doc = searcher.doc(tds[i].doc);  
            //��ӡdoc��key��name��Field��value  
            System.out.println(doc.get("title")+" : "+doc.get("content"));  
        }  
        /*������ 
         *  2 
            hello java 
            friday hello 
         */  
		
		
	}
	
	
	
	
	
}