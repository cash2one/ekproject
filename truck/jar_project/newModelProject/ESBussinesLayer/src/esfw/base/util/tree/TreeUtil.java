package esfw.base.util.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TreeUtil {
	
	private String dataSplit =":";
	private String dataToListSplit ="##";
	private String treeNodeViewSign ="┝";
	
	String [] sortResult = null; 
	
	/**
	 * 形成顺序的排列的树状 下拉列表
	 * @param datas  【格式为   节点ID，节点名称，父节点ID 】
	 */
	public String [] CreateTreeModel(List<String[]> datas){
		Map<String,Node> roots = new HashMap<String,Node>();
		for(String[] data:datas){
		    if("0".equals(data[2])||"".equals(data[2]))
		    	 roots.put(data[0],new Node(data[0],data[1]));
		}
		sortResult =new String[roots.size()];
	    int op = 0;
		for(Node node : roots.values()){
	    	findLeaf(node,datas);
	    	sortResult[op]=(sortNode(node,0));
	    	op++;
	    }
		datas.clear();
		datas = null;
		return sortResult;
	}
	
    String sortNode(Node node,int layer){
		int nextLay = layer+1;
		String str = (layer+dataSplit+node.root+dataSplit+ node.name);
		for(Node leaf : node.getLeafs()){
			 str+=dataToListSplit+sortNode(leaf,nextLay);
		}
		return str;
	}
	
    void findLeaf(Node parent,List<String[]> datas){
		if(datas!=null)
		for(String[] data:datas){
		    if(parent.root.equals(data[2])){
		    	 Node leaf = new Node(data[0],data[1]);
		    	 findLeaf(leaf,datas);
		    	 parent.addLeaf(leaf);
		    }
		}
	}
	
    /**
     * 排序号：节点ID：节点名称 ： 视图节点（）
     * @return
     */
    public List<String[]> tranToList(){
    	List<String[]> result = new ArrayList<String[]>();
    	String[] leafs = null;
    	int spaces = 0;
    	String aliasShow="";
    	if(this.sortResult!=null){
    		for(String root:this.sortResult){
    			leafs = root.split(this.dataToListSplit);
    			for(String node:leafs){
    				if(node!=null&&null!=node.split(this.dataSplit)){
    				   spaces = Integer.parseInt(node.split(this.dataSplit)[0]);
    				   aliasShow = spaces>0?appendSpaceStr(spaces)+treeNodeViewSign+node.split(this.dataSplit)[2]:node.split(this.dataSplit)[2];
    				   result.add(new String[]{node.split(this.dataSplit)[0],node.split(this.dataSplit)[1],node.split(this.dataSplit)[2],aliasShow});
    				} 
    			}
    		}
    	}
    	return result;
    }
    
    String appendSpaceStr(int num){
    	String str ="";
    	for(int n=1;n<=num;n++){
    		str+="&nbsp;&nbsp;&nbsp;&nbsp;";
//    		if(n==1)
//    		   str ="¨";
//    		else
//    		   str+="    ";
    	}
    	return str;
    }
    
    public static void main(String...arg){
    	List<String[]> datas  = new ArrayList<String[]>();
    	
    	datas.add(new String[]{"124579","学习","0"});
    	datas.add(new String[]{"124574","自定义评语","0"});
    	datas.add(new String[]{"124580","作业","0"});
    	datas.add(new String[]{"124650","测试","124580"});
    	datas.add(new String[]{"124651","斯蒂芬试","124650"});
    	datas.add(new String[]{"124652","范德萨","0"});
    	TreeUtil u = new TreeUtil();
    	for(String root : u.CreateTreeModel(datas)){
    		System.out.println(root);
    	}
    	
    	for(String[] root : u.tranToList()){
    		System.out.println(root[3]);
    	}
    	
    }
    
}
