package ex1.src;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class implements weighted_graph_algorithms - 
 * algorithms that calculates any acts on graph g. 
 */
public class WGraph_Algo implements weighted_graph_algorithms, Serializable {

	private static final long serialVersionUID = 1L;
	private weighted_graph g= new WGraph_DS();

	/**
     * This method initializes the graph on which this set of algorithms operates on.
     *
     * @param g
     */
	@Override
	public void init(weighted_graph g) {
		this.g= g;
	}

	/**
	 * This graph return the graph which this class works on it.
	 */
	@Override
	public weighted_graph getGraph() {
		return g;
	}

	/**
	 * This method copies (deep copy) this graph.
	 * 
	 * @return
	 */
	@Override
	public weighted_graph copy() {
		return new WGraph_DS(g);
	}

	/**
	 * This method used the Dijkstra algorithm.
	 * Calculates for each node in the graph the distance from the source node to it
	 * in accordance the weights of the graph's edges.
	 * 
	 * @param src - The node from which the calculation begins.
	 */
	private void Dijkstra(int src) {
		Queue<node_info> q= new PriorityQueue<>();
		for (node_info n: g.getV()) {
			if(n.getKey()==src)
				n.setTag(0);
			else
				n.setTag(Double.MAX_VALUE);
			n.setInfo("unvisited");
			q.add(n);
		}
		g.getNode(src).setTag(0);
		while(!q.isEmpty()) {
			node_info rm= q.poll();
			for(node_info node: g.getV(rm.getKey())) {
				if(node.getInfo().equals("unvisited")) {
					double weight= g.getEdge(rm.getKey(), node.getKey());
					double path= rm.getTag()+weight;
					if(node.getTag()>path) {
						q.remove(node);
						node.setTag(path);
						q.add(node);
					}
				}
			}
			rm.setInfo("visited");
		}
	}

	/**
     * This method returns true if and only if (iff) the graph is connected-
     * there is a valid path from every node to each other node.
     *
     * @return
     */
	@Override
	public boolean isConnected() {
		if(g!=null && g.nodeSize()>1) {
			int c = 0;
			while (g.getNode(c) == null)
				c++;
			Dijkstra(c);
			for (node_info n : g.getV()) {
				if (n.getTag() == Double.MAX_VALUE) {
					return false;
				}
			}
		}
		return true;
	}

	/**
     * This method returns the length of the shortest path between src to dest
     * in accordance the weights of the graph's edges.
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
	@Override
	public double shortestPathDist(int src, int dest) {
		if(g.getNode(src)==null || g.getNode(dest)==null || src==dest)
			return -1;
		Dijkstra(src);
		if(g.getNode(dest).getTag()==Double.MAX_VALUE)
			return -1;
		return g.getNode(dest).getTag();
	}

	/**
     * This method returns the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest (null if none).
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		List<node_info> path= new LinkedList<>();
		double dist= shortestPathDist(src,dest);
		if(dist==-1 || dist==Double.MAX_VALUE) {
			if(src==dest && g.getNode(dest)!=null) {
				path.add(g.getNode(dest));
				return path;
			}
			return null;
		}
		else {
			path.add(g.getNode(dest));
			int node0= dest;
			while(node0!=src) {
				for (node_info n: g.getV(node0)){
					if(g.getNode(node0).getTag()==n.getTag()+g.getEdge(node0, n.getKey())) {
						path.add(0, n);
						node0= n.getKey();
						break;
					}
				}
			}
			return path;
		}
	}

	/**
	 * This method saves this graph go the given file name.
	 * 
	 * @param file - the file name (may include a relative path).
     * @return true - if the file was successfully saved.
	 */
	@Override
	public boolean save(String file) {
		try {
			ObjectOutputStream graph= new ObjectOutputStream(new FileOutputStream(file));
			graph.writeObject(g);
			graph.close();
			return true;
		} 
		catch (Exception e) {
			return false;
		} 
	}

	/**
     * This method load a graph to this graph algorithm.
     * 
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
	@Override
	public boolean load(String file) {
		try {
			ObjectInputStream graph= new ObjectInputStream(new FileInputStream(file));
			weighted_graph newGraph= (weighted_graph)graph.readObject();
			graph.close();
			this.init(newGraph);
			return true;
		} 
		catch (Exception e) {
			return false;
		} 
	}


}
