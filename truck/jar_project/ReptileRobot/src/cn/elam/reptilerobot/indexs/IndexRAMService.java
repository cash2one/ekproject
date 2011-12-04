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
 * �����ڴ棨��ʱ��������
 * @author Ethan.Lam
 * @createTime 2011-12-4
 * 
 */
public class IndexRAMService implements IIndexService {
	
	Version version = Version.LUCENE_34;
	
	// �����ӣ����������������ڴ���
	Directory dir = new RAMDirectory();
	
	/**
	 * 
	 * ��������������
	 * 
	 * @param fields  �ؼ���
	 * @param docs    �ĵ�����
	 * @throws Exception
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public void createIndexs(Field[]fields,String[][] docs,boolean isAppend) throws Exception {
		
		if(docs==null||fields==null)
			return;
	
		// �ִ���ʹ��lucence��׼�ģ�����Versionָ��ʹ�õ�lucence�汾
		Analyzer analyzer = new StandardAnalyzer(version);
       
		/* 
         * IndexWriter ������ά������ 
         * param1  
         * param2 ָ����������ʹ�õķִ��� 
         * param3 �Ƿ񸲸��������� 
         * param4 ���Field���� 
         *  
         */  
        IndexWriter writer = new IndexWriter(dir,analyzer,true,IndexWriter.MaxFieldLength.UNLIMITED);  
        /* 
         * Document��Field���������������ĵ�Ԫ�������������doc����ʽ���� 
         * ÿ��Fieldӵ��Key��Value 
         */  
        int fieldIndex = 0;
        for(int i = 0;i<docs.length;i++){  
            Document d = new Document();
            fieldIndex = 0;
            for(Field field : fields){
            	field.setValue(docs[i][fieldIndex++]);
	            d.add(field); 
            }
            writer.addDocument(d);//��doc����writer����  
        }  
        writer.close();  
        
        
	}

	
	
	/**
	 * 
	 * ��������ѯ���
	 * 
	 * @param queryFeildKey  ��ѯ�ֶ�
	 * @param queryStr    �ؼ���
	 * @param top  ���Ͻ�� ��ǰ������¼
	 * @return
	 * @throws Exception
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public List<Document> query(String queryFeildKey,String queryStr,int top) throws Exception {
		
		// �ִ���ʹ��lucence��׼�ģ�����Versionָ��ʹ�õ�lucence�汾
		Analyzer analyzer = new StandardAnalyzer(version);
		// B ����
		// ������������
		// ����������Ŀ¼��ָ��Ϊ���洴��������
		IndexSearcher searcher = new IndexSearcher(dir);
		QueryParser parser = new QueryParser(version// ָ���汾
				, queryFeildKey // Ĭ��������Feild��key��
				, analyzer// �����õķִ����������洴������ʹ��һ����
		);
		Query query = parser.parse(queryStr /* ��Ҫ���������Ĺؼ��� */);
		// ������ȡ�����ǰ5��
		ScoreDoc[] tds = searcher.search(query,top).scoreDocs;
		System.out.println(tds.length);// ���Ͻ��doc��
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
        LoggerUtil.p("���������ĺ�ʱ��",(System.currentTimeMillis()-ct)+"����");
		
	
        ct = System.currentTimeMillis();
        List<Document> rdocs = indexSer.query("content", "����",5);
        if(rdocs!=null)
        for(Document d:rdocs){
        	LoggerUtil.p("ȫ���������:",d.get("content"));
        }
        LoggerUtil.p("ȫ�������ĺ�ʱ��",(System.currentTimeMillis()-ct)+"����");
        
	}
	
}
