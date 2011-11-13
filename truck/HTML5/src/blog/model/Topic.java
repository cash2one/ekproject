package blog.model;

import java.io.Serializable;

/**
 * 文章类型 栏目分类
 * @author Ethan.Lam  
 * @createTime 2011-11-13
 *
 */
public class Topic implements Serializable{
 
	private static final long serialVersionUID = 464062853954418426L;

	private long id;
	
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
