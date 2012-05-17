package esfw.base.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树状节点结构
 * @author Ethan.Lam  2011-5-19
 *
 */
public class Node {
	String root;
	String name;
	List<Node> leafs = new ArrayList<Node>();

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getLeafs() {
		return leafs;
	}

	public Node(String root, String name) {
		this.root = root;
		this.name = name;
	}

	public void addLeaf(Node leaf) {
		if (!leafs.contains(leaf))
			leafs.add(leaf);
	}
}
