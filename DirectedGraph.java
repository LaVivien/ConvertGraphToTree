package graphToTree;

import java.util.*;

@SuppressWarnings("unchecked")
public class DirectedGraph<T extends Comparable<? super T>> {
    TreeMap<T, List<T>> adj = new TreeMap<>();    											
    Set<T> reachable = new HashSet<>(); 
    Set<T> unreachable = new HashSet<>(); 
    Set<T> cycleNodes = new HashSet<>(); 
    T root = null;
    
    //constructor, build adjacent representation of directed graph from input file
    public DirectedGraph(String[] input) {	
		for (String line: input) {		       
			addLine(line);	
		}				
    	root = adj.firstKey();
    	dfs(root);   	
    }
   
    //add edge between source and destination vertex
    private void addEdge(T source, T dest) { 
         adj.get(source).add(dest); 
    } 
    
    //add each line in input file to adjacent list
    private void addLine(String line) {
    	String[] strs= line.split(" "); 
    	if(!adj.containsKey(strs[0])) 
    		adj.put((T)strs[0], new ArrayList<>());
    	for(int i=1; i < strs.length; i++) { 
    		if(!adj.containsKey(strs[i]))
        		adj.put((T)strs[i], new ArrayList<>());
    		addEdge((T)strs[0], (T)strs[i]);   	
    	}
    }
    
    //depth first search of graph
    private void dfs(T src) {
    	Map<T, Boolean> discovered = new HashMap<>();
        for(T x: adj.keySet()) 
            discovered.put(x,false); 
        Map<T, Boolean> finished = new HashMap<>();  
        for(T x: adj.keySet()) 
        	finished.put(x,false);  
        Set<T> path = new LinkedHashSet<>();
	    dfsHelper(src, discovered, finished, path);
	    for(T t: discovered.keySet()) 
	    	if (!discovered.get(t)) 
	    		unreachable.add(t);
	    reachable = path;
	}
    
    //dfs helper
	private void dfsHelper(T v, Map<T,Boolean> discovered, Map<T, Boolean> finished, Set<T> path) {
		if(discovered.get(v)) {
			if(detectCycle())
				return;
		}
		if(discovered.get(v))
			return;
		path.add(v);
	    discovered.put(v, true);   
	    List<T> list = adj.get(v);	
	    Collections.sort(list, Collections.reverseOrder());	
	    for (T ne : list) {
            dfsHelper(ne, discovered, finished, path);
	    }
	    finished.put(v, true);
	    list = adj.get(v);
	    Collections.sort(list);		 
	}
	
	//Detect cycle 
    public boolean detectCycle()   { 
   	 	Map<T, Boolean> visited = new HashMap<>(); 
        for(T x: adj.keySet()) 
            visited.put(x,false);   
        Map<T, Boolean> stack = new HashMap<>(); 
        for(T x: adj.keySet()) 
            stack.put(x,false);   
        for(T t: adj.keySet()) {
           if (detectCycleHelper(t, visited, stack)) 
               return true; 
        }
       return false; 
    } 
    
    //detect helper, recursion
    private boolean detectCycleHelper(T i, Map<T, Boolean> visited, Map<T, Boolean> stack)   { 
        if (stack.get(i)) 
            return true;   
        if (visited.get(i)) 
            return false;              
        visited.put(i,true);   
        stack.put(i, true); 
        List<T> children = adj.get(i);           
        for (T c: children) {
            if (detectCycleHelper(c, visited, stack)) 
                return true;                  
        }
        stack.put(i,false);  
        return false; 
    } 
    
    //convert graph to tree, by removing cycle
    public void convertToTree() {
    	removeCycle();
    	buildHierarchyTree(root);
    }
    
    //remove cycle in graph
    private void removeCycle() {
    	if(!detectCycle())
    		return;
    	for (T t: adj.keySet()) {
    		for (T t1: adj.get(t)) {
    			List<T> list = adj.get(t1);
    			if (list.contains(t)){
    				list.remove(t);
    				cycleNodes.add(t1);
    			}
    		}
    	}   	
    }
    
    //build hierarchy tree using recursion
	private void buildHierarchyTree(T root) {
		T p = root;
		 List<T> subs = adj.get(p);
		 if (subs.size() == 0)
			 return;
		 for (T em : subs) 
	    	buildHierarchyTree(em);
	}
      
	//print graph as adjacent list 
    public void printGraph() {
    	System.out.println("Graph Adjacent list:");
    	for(T t: adj.keySet())
    		System.out.println(t + " "+ adj.get(t));
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
    	DirectedGraph<String> g1 = new DirectedGraph<>(input); 

    	System.out.println("DFS:"+g1.reachable);
    	System.out.println("Unreachable nodes:" + g1.unreachable);

    	g1.removeCycle();
    	g1.printGraph();       
    } 
}
