package blog.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章
 * @author Ethan.Lam  
 * @createTime 2011-11-13
 *
 */
public class Article implements Serializable {

	private static final long serialVersionUID = -1745247545783808292L;

	private long id;
	
    private String author;
	
    private String title;
    
	private String content;
	
    private int topic;

	private Date createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTopic() {
		return topic;
	}

	public void setTopic(int topic) {
		this.topic = topic;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
