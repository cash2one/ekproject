package blog.service;

import java.util.List;

import service.base.BaseServiceImpl;
import blog.model.Article;

public class ArticleServiceImp extends BaseServiceImpl<Article> implements IArticleService {

	static final String GLOBAL_ARTICLE_ID = "global:nextArticleId";
	
	static final String  ARTICLES_ID_FORMAT = "Articles:%d";
	
	private String getFormatId(long id) {
		return String.format(ARTICLES_ID_FORMAT, id);
	}
	
	public Article load(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Article article) {
		// TODO Auto-generated method stub
		
	}

	public void update(Article article) {
		// TODO Auto-generated method stub
		
	}

	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	public List<Article> list(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
