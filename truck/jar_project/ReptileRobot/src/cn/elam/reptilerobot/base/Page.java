package cn.elam.reptilerobot.base;

import java.io.Serializable;

/**
 * 
 * Ò³ÃæÊý¾Ý
 * @author Ethan.Lam  
 * @createTime 2011-11-15
 *
 */
public class Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3448119704668745432L;

	private String title;
	
	private String segment;

	private String url;
	
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

    	
}
