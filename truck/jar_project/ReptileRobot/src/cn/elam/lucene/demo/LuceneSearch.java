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
      // 建立查詢器
      searcher = new IndexSearcher(IndexReader.open("d:\\index"));
 
    } catch (Exception e) {
 
    }
  }
 
  // hits是查詢的結果集
  public final Hits search(String keyword) throws Exception {
    System.out.println("正在檢索關鍵字 : " + keyword);
    // 建立要查詢的("目標",分析器)
    QueryParser qp = new QueryParser(null, "content", analyzer);
    // parse(查詢字) ,keyword可以用空白格開,理論上會查出
    // A AND B ,A,B 三種結果,放入同一個結果集
    // AND OR 大寫是logic判斷用
    query = qp.parse(keyword);
    Date start = new Date();
    // hits是查詢的結果集
    Hits hits = searcher.search(query);
    Date end = new Date();
    System.out
        .println("檢索完成，用時" + (end.getTime() - start.getTime()) + "毫杪");
 
    return hits;
  }
 
  // 列印結果集
  public void printResult(Hits h) {
    if (h.length() == 0) {
      System.out.println("對不起！沒有您要找的資料!");
    } else {
      // hits的length是查到的所有結果
      for (int i = 0; i < h.length(); i++) {
        try {
          // 取得第n個查詢結果,此處get("contents")會是null,
          // 因為content是查詢用欄位
          // 而get("path")則是敘述欄位
          // 請在建立index時就定義好
          Document doc = h.doc(i);
          // System.out.println("這是第"+(i+1)+"個檢索到的結果,檔案為 :
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
