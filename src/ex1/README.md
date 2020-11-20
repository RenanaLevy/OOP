## ex1 task.

The progect implements an weighted (undirectional) graph.

#### NodeInfo class
In NodeInfo class I used HashMap that contains all the keys of neighbors of that node in the graph, and the weight of the edge between them.
I used HashMap in order to access data of the neighbors and the edges at complexity O(1).
The node contains also key, info and tag which can be used be algorithms.
This class is internal class of WGraph_DS and have get and set functions that returns and gets data about the node.
In addition this class implements compareTo function which used in Dijkstra's algorithm (compares between the tags).

  
#### WGraph_DS class
In WGraph_DS class I used HashMap that contains all the nodes in the graph.
I used HashMap in order to access data of the nodes at complexity O(1).
This class have functions that acts on a graph, like: add node, remove node, connect between 2 nodes, remove edge etc.

  
#### WGraph_Algo class
In WGraph_Algo class there are functions that make calculations on the graph-
copy the graph, check if the graph is connected, find the shortest distance between 2 nodes and find the path between them,
save a graph to a file and load a graph from a file.
I used Dijkstra's algorithm for calculating for each node in the graph the short distance from a given source to it,
in accordance the weights of the graph's edges.