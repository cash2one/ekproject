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
 * ���������ļ��� ������
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
		
		// ע�⣺���ｨ�������õķִʷ�����������ʱ�ִ�ҲӦ�ò���ͬ���ķִʷ�������Ȼ�������ݿ��ܻ᲻��ȷ
		// ʹ��Lucene�Դ��ִ���
		Analyzer luceneAnalyzer = new StandardAnalyzer(version);
		
		IndexWriterConfig indexWriterconfig = new IndexWriterConfig(version, luceneAnalyzer);
		indexWriterconfig.setMaxBufferedDocs(100);
		indexWriterconfig.setOpenMode(isAppend?OpenMode.APPEND:OpenMode.CREATE_OR_APPEND);
		
		File indexDir = new File(indexDirPath);
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir),indexWriterconfig);
		long startTime = new Date().getTime();
		// ���������ֶ�
		// ��Field���������ڲ��ࣺField.Index,Field.Store,Field.termVector�������캯��Ҳ�õ������ǡ�
		// ����˵����
		// Field.Store��
		// Field.Store.NO����ʾ�ã�ield����Ҫ�洢��
		// Field.Store.Yes����ʾ�ã�ield��Ҫ�洢��
		// Field.Store.COMPRESS����ʾʹ��ѹ����ʽ���洢��
		// Field.Index��
		// Field.Index.NO����ʾ�ã�ield����Ҫ������
		// Field.Index.TOKENIZED����ʾ�ã�ield�ȱ��ִ���������
		// Field.Index.UN_TOKENIZED����ʾ���Ըã�ield���зִʣ���Ҫ����������
		// Field.Index.NO_NORMS����ʾ�ã�ield��������������Ҫ������Analyzer��ͬʱ��ֹ���μ����֣���Ҫ��Ϊ�˼������ڵ����ġ�
		// TermVector�������Ҳ�����ã��������ѡ�
//		                Field.TermVector.NO��ʾ������Token��λ�����ԣ�
//		                Field.TermVector.WITH_OFFSETS��ʾ��������Token�Ľ����㣻
//		                Field.TermVector.WITH_POSITIONS��ʾ��������Token�ĵ�ǰλ�ã�
//		                Field.TermVector.WITH_POSITIONS_OFFSETS��ʾ��������Token�ĵ�ǰ�ͽ���λ�ã�
//		                Field.TermVector.YES���ʾ�洢������
		// �����ĵ� Field�൱���������ݿ��ֶ�һ������,��ȡ����Ҫ������,ֱ�ӷ�index��,��������������index,�����ļ���txt����
	   /**
		* Field.Store ��ʾ���Ƿ�洢��������Field�ڵ���Ϣ�Ƿ�Ҫ��ԭ�ⲻ���ı����������С�
		* Field.Index ��ʾ���Ƿ����������������Field�е������Ƿ��ڽ�������ʱ��Ҫ���û���������һ��������������Fieldͨ�������ṩ������Ϣ����Ĺ��ܡ�
		* Field.TermVector ��ʾ���Ƿ��дʡ����������Field�е������Ƿ���Ҫ���дʡ�
		*/
	   int fieldIndex = 0;
	   for(int i = 0;i<docs.length;i++){  
	          Document d = new Document();
	          fieldIndex = 0;
	          for(Field field : fields){
	              field.setValue(docs[i][fieldIndex++]);
		          d.add(field); 
	          }
	          indexWriter.addDocument(d);//��doc����writer����  
	    }  
	    indexWriter.optimize();
		indexWriter.close();
		// ����Ҫ��������ɾ��ĳһ������ĳһ���ĵ���IndexReader�ṩ�����ַ�����
		// reader.DeleteDocument(int docNum)
		// reader.DeleteDocuments(Term term)
		// ǰ���Ǹ����ĵ��ı����ɾ�����ĵ���docNum�Ǹ��ĵ���������ʱLucene�ı�ţ��ǰ���˳���ģ�������ɾ������ĳһ�������Ķ���ĵ���
		// ��ִ����DeleteDocument����DeleteDocuments������ϵͳ������һ��*.del���ļ������ļ��м�¼��ɾ�����ĵ�������δ��������ɾ����Щ�ĵ�����ʱ����Щ�ĵ����ܱ����ģ���ʹ��Document
		// doc = reader.Document(i)��������Щ�ܱ������ĵ�ʱ��Lucene�ᱨ��Attempt to access a
		// deleted document���쳣�����һ����Ҫɾ������ĵ�ʱ�����������ַ����������
		// 1. ɾ��һ���ĵ�����IndexWriter��Optimize�������Ż��������������ǾͿ��Լ���ɾ����һ���ĵ���
		// 2. ��ɨ�����������ļ�����¼����Ҫɾ�����ĵ��������еı�š�Ȼ��һ���Ե���DeleteDocumentɾ����Щ�ĵ����ٵ���IndexWriter��Optimize�������Ż�������

		long endTime = new Date().getTime();
		System.out.println("\n�⻨����" + (endTime - startTime) + " �������ӵ�����!");
	}

	
	@Override
	public List<Document> query(String queryFeildKey, String queryStr, int top)
			throws Exception {
		// �ִ���ʹ��lucence��׼�ģ�����Versionָ��ʹ�õ�lucence�汾
		Analyzer analyzer = new StandardAnalyzer(version);
		// B ����
		// ������������
		// ����������Ŀ¼��ָ��Ϊ���洴��������
		File indexDir = new File(indexDirPath);
		IndexSearcher searcher = new IndexSearcher(FSDirectory.open(indexDir));
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
        LoggerUtil.p("���������ĺ�ʱ��",(System.currentTimeMillis()-ct)+"����");
	
        ct = System.currentTimeMillis();
        List<Document> rdocs = indexSer.query("content", "��ͨ�¹�",20);
        if(rdocs!=null)
        for(Document d:rdocs){
        	LoggerUtil.p("ȫ���������:",d.get("content"));
        }
        LoggerUtil.p("ȫ�������ĺ�ʱ��",(System.currentTimeMillis()-ct)+"����");
        
	}

}
