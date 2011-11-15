package cn.elam.reptilerobot.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 爬虫的节点信息
 * @author Ethan.Lam  
 * @createTime 2011-11-15
 *
 */
public class Node implements Serializable{

	private long id;
	
	private String url;
	
	private String preUrl;
	
	private String title;
	
	private long updateTime;
	
	private Date lastVisitTime;

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

	public String getPreUrl() {
		return preUrl;
	}

	public void setPreUrl(String preUrl) {
		this.preUrl = preUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}
	
	
}
