package graphToTree;

import java.util.*;

public class HierarchyList<T extends Comparable<? super T>> {
	
	DirectedGraph<T> g;

	public HierarchyList(String[] input) {
		g = new DirectedGraph<>(input); 
		g.convertToTree();		
	}
 
	//print out the the hierarchy tree with recursion
	private void hierarchyOrder(T root, int level, StringBuilder sb) {			
		for (int i = 0; i < level; i++) 
			sb.append("\t");
		if(g.cycleNodes.contains(root)) 
			sb.append(root+" *\r\n"); 
		else 
			sb.append(root+"\r\n");
		List<T> subs = g.adj.get(root);
		for (T em : subs) 
			hierarchyOrder(em, level+1, sb);
	}
	
	//the wrapper method to call hierarchyOrder
	 public String toString() {
		 StringBuilder sb = new StringBuilder(); 
		 hierarchyOrder(g.root, 0, sb); 
		 return sb.toString(); 
	 }

	 public static void main(String[] args) { 
		 String[] input={
    			"ClassA ClassC ClassE ClassJ",
    			"ClassB ClassD ClassG",
    			"ClassC ClassA",
    			"ClassE ClassB ClassF ClassH",
    			"ClassJ ClassB",
    			"ClassI ClassC"
		 };
		 HierarchyList<String> h = new HierarchyList<>(input);		    
	     System.out.print(h);
	 }
}
