# Graph and Tree

## Part I Create Graph

writing a program that accepts information about the class dependencies in a Java program and creates a directed graph from that information. From the directed graph, it produces two different kinds of displays of those dependency relationships.

A sample input file is shown below:
```
ClassA ClassC ClassE ClassJ
ClassB ClassD ClassG
ClassC ClassA
ClassE ClassB ClassF ClassH
ClassJ ClassB
ClassI ClassC
```
The first name of each line is a Java class upon which other classes depend. The remaining names are the classes that depend upon the first class on that line. The first line of the above file, for example, indicates that ClassA has three classes that depend upon it, ClassC, ClassE and ClassJ. A class that have does any classes that depend on it, need not appear at the head of any line.

Create a class DirectedGraph, should be a generic class, whose generic parameter specifies the type of the labels that are associated with the vertices of the graph. The internal representation of the graph should be the alternate adjacency list. this graph will not be a weighted graph. 

It should contain a method that allows edges to be added to the graph, which is how the main method will initially build the graph. It should also contain a method that performs a depth-first search of that graph.

When the method in the DirectedGraph class that initiates the depth first search is called, it should first initialize all the vertices to the undiscovered state and begin the search at the vertex that corresponds to the first name in the input file.

Another method in the DirectedGraph class should then allow the main method to display any unreachable classes by examining all the vertices of the graph to see which remain undiscovered.

## Part II Convert to Tree

There should be two additional classes. The first, HierarchyList, should produce a hierarchical representation of the class dependencies. Circular dependencies should be flagged. For the above input file, the following hierarchical representation should be produced:
```
ClassA
	ClassC *
	ClassE
		ClassB
			ClassD
			ClassG
		ClassF
		ClassH
	ClassJ
		ClassB
			ClassD
			ClassG
```		
The asterisk after ClassC results from the fact that ClassC depends upon ClassA and ClassC depends upon ClassA. The Hierarchy class should override the toString method, which should return a string that contains the above, after having performed the depth-first search.

The other class is ParenthesizedList. It should produce an alternate representation that is also returned by its toString method. For the above input file, the following hierarchical representation should be produced:
```
( ClassA ( ClassC * ClassE ( ClassB ( ClassD ClassG ) ClassF ClassH ) ClassJ ( ClassB ( ClassD ClassG ))))		
```
				