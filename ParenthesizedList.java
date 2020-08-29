package graphToTree;

public class ParenthesizedList<T extends Comparable<? super T>> {
	
	private DirectedGraph<T> g;
	private String preorderStr = ""; 
	
	public ParenthesizedList(String[] input) {
		g = new DirectedGraph<>(input); 
		g.convertToTree(); 
	}

	//pre-order the tree with recursion
    private void preOrder(T root) {   	
        if (root == null) 
            return ;      
        if(g.cycleNodes.contains(root)) 
        	preorderStr +=root+" *"; 
        else
        	preorderStr += root; 
        if (g.adj.get(root)==null)  
	        return;  
	    preorderStr += ("("); 
        for (T ch : g.adj.get(root)) 
            preOrder(ch);         
        preorderStr += (")");  
    }
    
    //wrapper to call preOrder
	public String toString() {
		preOrder(g.root); //call pre-order
		String s = preorderStr.replaceAll("\\(\\)"," ");
	    s = s.replaceAll("\\("," ( "); 
	    s = s.replaceAll("\\)",") "); 
	    return "( "+s+")";
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
		 ParenthesizedList<String> h = new ParenthesizedList<>(input);	
	     System.out.println(h);
	}
}
