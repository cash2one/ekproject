package cn.qtone.xxt.apps.web.misc;

/**
 * Ͷ���¼� bean
 * 
 * @author Ethan.Lam 2011-2-15
 * 
 */
public class ComplaintItem {

	String id;// ��ˮ��
	String user; // Ͷ���û�
	String brand; // Ʒ��
	String area;// Ͷ������
	String rank;// �����̶�
	String dealState; // ����״̬
	String content = ""; // Ͷ������

	String replyUrl = "";
	String createTime; // �ɵ�ʱ��
	String deadline; // ���ʱ��

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
