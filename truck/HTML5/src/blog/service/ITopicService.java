package blog.service;

import java.util.List;

import blog.model.Topic;

/**
 * 栏目管理接口
 * @author Ethan.Lam  
 * @createTime 2011-11-13
 *
 */
public interface ITopicService {
	
	public Topic load(long id);
	
	public void save(Topic topic);
	
	public void  update(Topic topic);
	
	public void delete(long id);
	
    public List<Topic> list(int page,int size);

}
