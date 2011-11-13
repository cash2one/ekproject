package blog.service;

import java.util.List;

import blog.model.Article;

/**
 * 
 * Blog文章管理接口
 * @author Ethan.Lam  
 * @createTime 2011-11-13
 *
 */
public interface IArticleService{

	public Article load(long id);
	
	public void save(Article article);
	
	public void  update(Article article);
	
	public void delete(long id);
	
    public List<Article> list(int page,int size);
    
    
    
}
