package cn.qtone.xxt.apps.web.misc;

/**
 * 投诉事件 bean
 * 
 * @author Ethan.Lam 2011-2-15
 * 
 */
public class ComplaintItem {

	String id;// 流水号
	String user; // 投诉用户
	String brand; // 品牌
	String area;// 投诉区域
	String rank;// 紧急程度
	String dealState; // 处理状态
	String content = ""; // 投诉内容

	String replyUrl = "";
	String createTime; // 派单时间
	String deadline; // 最后时限

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDealState() {
		return dealState;
	}

	public void setDealState(String dealState) {
		this.dealState = dealState;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public String getReplyUrl() {
		return replyUrl;
	}

	public void setReplyUrl(String replyUrl) {
		this.replyUrl = replyUrl;
	}

}
