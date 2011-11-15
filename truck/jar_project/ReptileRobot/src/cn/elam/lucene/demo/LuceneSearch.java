package cn.elam.lucene.demo;

import java.util.Date;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
 
public class LuceneSearch {
  private IndexSearcher searcher = null;
  private Query query = null;
  private Analyzer analyzer = new StandardAnalyzer();
 
  public LuceneSearch() {
    try {
      // ������ԃ��
      searcher = new IndexSearcher(IndexReader.open("d:\\index"));
 
    } catch (Exception e) {
 
    }
  }
 
  // hits�ǲ�ԃ�ĽY����
  public final Hits search(String keyword) throws Exception {
    System.out.println("���ڙz���P�I�� : " + keyword);
    // ����Ҫ��ԃ��("Ŀ��",������)
    QueryParser qp = new QueryParser(null, "content", analyzer);
    // parse(��ԃ��) ,keyword�����ÿհ׸��_,��Փ�ϕ����
    // A AND B ,A,B ���N�Y��,����ͬһ���Y����
    // AND OR ����logic�Д���
    query = qp.parse(keyword);
    Date start = new Date();
    // hits�ǲ�ԃ�ĽY����
    Hits hits = searcher.search(query);
    Date end = new Date();
    System.out
        .println("�z����ɣ��Õr" + (end.getTime() - start.getTime()) + "����");
 
    return hits;
  }
 
  // ��ӡ�Y����
  public void printResult(Hits h) {
    if (h.length() == 0) {
      System.out.println("�����𣡛]����Ҫ�ҵ��Y��!");
    } else {
      // hits��length�ǲ鵽�����нY��
      for (int i = 0; i < h.length(); i++) {
        try {
          // ȡ�õ�n����ԃ�Y��,��̎get("contents")����null,
          // ���content�ǲ�ԃ�Ù�λ
          // ��get("path")�t�ǔ�����λ
          // Ո�ڽ���index�r�Ͷ��x��
          Document doc = h.doc(i);
          // System.out.println("�@�ǵ�"+(i+1)+"���z�����ĽY��,�n���� :
          // "+doc.get("path"));
          System.out.println(doc.get("url"));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("---------------------------");
  }
 
  public static void main(String[] args) throws Exception {
    LuceneSearch test = new LuceneSearch();
 
    test.printResult(test.search("FileUpload"));
 
  }
 
}
