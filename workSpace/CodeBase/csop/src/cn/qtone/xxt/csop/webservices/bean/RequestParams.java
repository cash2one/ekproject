package cn.qtone.xxt.csop.webservices.bean;

/**
 * ������������б�  ��װ��
 * 
 * @author LinHanSheng
 * 
 */
public abstract class RequestParams {

	// ƽ̨ platform=uniteview,Ŀǰͳһʹ��uniteviewƽ̨
	String platform;

	// ��־λ��0 ��ʽ 1 ����
	String flag;

	// �汾
	String version;

	// ҵ��ƽ̨��Ч����ԱID
	String operId;

	// ҵ��ƽ̨���룬��MD5����
	String password;

	// ҵ��ƽ̨����
	String sysCode;

	// ������� �ֻ�����
	String servNumber;

	/* 
	 *                           ҵ�����
	 * ҵ���������ѯ�ӿ�                    B005_01
	 * ҵ������ӿ�                                        B005_02
	 * ҵ����ʷ������¼��ѯ�ӿ�          B005_03
	 * ҵ��ʹ�ü�¼��ѯ�ӿ�                    B005_04
	 */
	String busiCode;

	// ������Ϣ
	String params;

	// ��ѯ�ֻ�����
	String telNo;

	// ��ʼʱ��
	String beginDate;

	// ����ʱ��
	String endDate;

	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getServNumber() {
		return servNumber;
	}

	public void setServNumber(String servNumber) {
		this.servNumber = servNumber;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
