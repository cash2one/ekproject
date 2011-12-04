package cn.elam.reptilerobot.core;

import java.util.BitSet;

import cn.elam.reptilerobot.base.Node;

/**
 * 
 * 布隆过滤器
 * @author Ethan.Lam  
 * @createTime 2011-12-4
 *
 */
public class SimpleBloomFilter {

	private static final int DEFAULT_SIZE = 2 << 24;

	private static final int[] seeds = new int[] { 7, 11, 13, 31, 37, 61 };

	private BitSet bits = new BitSet(DEFAULT_SIZE);

	private SimpleHash[] func = new SimpleHash[seeds.length];

	
	public SimpleBloomFilter() {
		for (int i = 0; i < seeds.length; i++) {
			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
		}
	}

	/**
	 * 
	 * 方法：添加新的URL
	 * 
	 * @param node
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public void add(Node node) {
		if (node != null) {
			add(node.getUrl());
		}
	}

	private void add(String value) {
		for (SimpleHash f : func) {
			bits.set(f.hash(value), true);
		}
	}

	/**
	 * 
	 * 方法：判断该URL是否已经访问过
	 * 
	 * @param node
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public boolean contains(Node node) {
		return contains(node.getUrl());
	}

	private boolean contains(String value) {
		if (value == null) {
			return false;
		}
		boolean ret = true;
		for (SimpleHash f : func) {
			ret = ret && bits.get(f.hash(value));
		}
		return ret;
	}

	/**
	 * 
	 * @author Ethan.Lam  
	 * @createTime 2011-12-4
	 * SimpleBloomFilter
	 */
	public static class SimpleHash {

		private int cap;
		private int seed;

		public SimpleHash(int cap, int seed) {
			this.cap = cap;
			this.seed = seed;
		}

		public int hash(String value) {
			int result = 0;
			int len = value.length();
			for (int i = 0; i < len; i++) {
				result = seed * result + value.charAt(i);
			}
			return (cap - 1) & result;
		}
	}
	
	public static void main(String...args){
		String value = "testaaa";
		SimpleBloomFilter filter = new SimpleBloomFilter();
		System.out.println(filter.contains(value));
		filter.add(value);
		System.out.println(filter.contains(value));
	}

}
