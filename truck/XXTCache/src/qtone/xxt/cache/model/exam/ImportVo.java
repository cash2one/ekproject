package qtone.xxt.cache.model.exam;

import java.io.Serializable;

/**
 * 封装表格数据的一行   (成绩导入)
 * @author Administrator
 *
 */
public class ImportVo  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5986062836315735349L;
	
	
	private String stuName;//学生姓名
	private String[] scoreList;//分数列表
	private String result;//处理结果
	private String stuSequence;//学生编号
	
	
	public String getStuSequence() {
		return stuSequence;
	}
	
	public void setStuSequence(String stuSequence) {
		this.stuSequence = stuSequence;
	}
	
	public String getStuName() {
		return stuName;
	}
	
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	public String[] getScoreList() {
		return scoreList;
	}
	
	public void setScoreList(String[] scoreList) {
		this.scoreList = scoreList;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
