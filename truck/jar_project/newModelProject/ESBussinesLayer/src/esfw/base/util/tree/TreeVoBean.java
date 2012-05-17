package esfw.base.util.tree;

/**
 * 
 * @author Ethan.Lam 2011-5-19
 * 
 */
public class TreeVoBean{

	private int id;

	private int parentId;

	private String name;
	
	private String showName;
	
	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public TreeVoBean() {
		
	}
	
    public TreeVoBean(int id,String name,String showName,int parentId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.showName = showName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
