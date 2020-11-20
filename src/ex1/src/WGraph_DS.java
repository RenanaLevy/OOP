package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class implements weighted_graph interface and
 * Serializable interface in order to save and load methods.
 */
public class WGraph_DS implements weighted_graph, Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer, node_info> g= new HashMap<>();
	private int numOfNodes;
	private int numOfEdge;
	private int mc;


	/**
	 * Constructor
	 */
	public WGraph_DS(){
		this.numOfNodes= 0;
		this.numOfEdge= 0;
		this.mc= 0;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param gr
	 */
	public WGraph_DS(weighted_graph gr) {
		if(gr==null) {
			this.g= null;
			return;
		}
		for (node_info node: gr.getV()) {
			node_info n = new NodeInfo(node);
			this.addNode(n.getKey());
		}
		for(node_info n: this.getV()) {
			if(gr.getV(n.getKey())==null)
				continue;
			for (node_info n2 : gr.getV(n.getKey())) {
				double weight= ((NodeInfo)n2).getWeightEdge(n.getKey());
				((NodeInfo)n).ni.put(n2.getKey(), weight);
			}
		}
		this.numOfEdge= gr.edgeSize();
		this.numOfNodes= gr.nodeSize();
		this.mc= gr.getMC();
	}



	/**
	 * This class implements node_info interface, 
	 * comparable interface which be used in Dijkstra algorithm,
	 * and Serializable interface in order to save and load methods.
	 */
	private class NodeInfo implements node_info, Comparable<Object>, Serializable{

		private static final long serialVersionUID = 1L;
		private int key;
		private Map<Integer, Double> ni= new HashMap<>();
		private String info;
		private double tag;

		/**
		 * Constructor.
		 * 
		 * @param key
		 */
		NodeInfo(int key){
			this.key= key;
			this.info= "";
			this.tag= Double.MAX_VALUE;
		}

		/**
		 * Copy constructor.
		 * 
		 * @param node
		 */
		NodeInfo(node_info node){
			this.key= node.getKey();
			this.info= node.getInfo();
			this.tag= node.getTag();
		}

		/**
		 * This method returns the key (id) associated with this node.
		 * 
		 *  @return
		 */
		@Override
		public int getKey() {
			return this.key;
		}

		/**
		 * This method returns the remark (meta data) associated with this node.
		 * 
		 * @return
		 */
		@Override
		public String getInfo() {
			return this.info;
		}

		/**
		 * This method allows changing the remark (meta data) associated with this node.
		 * 
		 * @param s
		 */
		@Override
		public void setInfo(String s) {
			this.info= s;
		}

		/**
		 * This method returns temporal data (tag) about this node -
		 * the distance from this node to another node in the graph,
		 * which can be used in algorithms
		 * 
		 * @return
		 */
		@Override
		public double getTag() {
			return this.tag;
		}

		/**
		 * This method allow setting the "tag" value for temporal marking (distance) of this node.
		 * 
		 * @param t - the new value of the tag
		 */
		@Override
		public void setTag(double t) {
			this.tag= t;
		}

		/**
		 * This method returns the edge's weight from this node to the key's node.
		 * 
		 * @param key
		 * @return
		 */
		double getWeightEdge(int key) {
			return this.ni.get(key);
		}

		/**
		 * This method implements compareTo from comparable interface - 
		 * compare between the tag of this method and the tag of the node that the method gives,
		 * which be used in the Dijkstra method.
		 * 
		 * @param o - the node to the compare.
		 * @return - 1 if the tag of this node bigger, -1 if the tag of o bigger or 0 if they equals.
		 */
		@Override
		public int compareTo(Object o) {
			node_info n= (node_info)o;
			if(this.tag>n.getTag())
				return 1;
			else if(this.tag<n.getTag())
				return -1;
			else
				return 0;
		}

		/**
		 * This method checks if this node and o node are equals - 
		 * which be used in the tests.
		 * 
		 * @param o - the node to the compare.
		 * @return
		 */
		@Override
		public boolean equals(Object o) {
			if(o==this) 
				return true;
			if(o==null) 
				return false;
			if(!(o instanceof node_info)) 
				return false;
			node_info n= (node_info)o;
			if(this.key!=n.getKey() || this.tag!=n.getTag() || (!(this.info.equals(n.getInfo()))))
				return false;
			for(int neighbor: this.ni.keySet()) {
				if (((NodeInfo)n).ni.containsKey(neighbor)) {
					if(this.getWeightEdge(neighbor)!=((NodeInfo)n).getWeightEdge(neighbor))
						return false;
				}
				else
					return false;
			}
			return true;
		}

		/**
		 * This method returns a string which represents neighbors list of this node_data.
		 *
		 * @return
		 */
		@Override
		public String toString(){
			String neighbors = "("+this.key+" - the neighbors is : [";
			int si= 0;
			for (int neighbor: this.ni.keySet()) {
				if(si==this.ni.keySet().size()-1)
					neighbors+=neighbor+"]";
				else
					neighbors+=neighbor+" , ";
				si++;
			}
			return neighbors+")";
		}
	}

	

	/**
	 * This method returns the node_data by the node_id (key).
     *
     * @param key - the node_id
     * @return the node_data by the node_id (key), null if none.
	 */
	@Override
	public node_info getNode(int key) {
		if(!this.g.containsKey(key))
			return null;
		return this.g.get(key);
	}

	/**
	 * This method returns true iff (if and only if) there is an edge between node1 and node2.
     *
     * @param node1
     * @param node2
     * @return
     */
	@Override
	public boolean hasEdge(int node1, int node2) {
		if(!this.g.containsKey(node1) || !this.g.containsKey(node2) || node1==node2)
			return false;
		return ((NodeInfo)getNode(node1)).ni.containsKey(node2);
	}

	/**
     * This method returns the weight of the edge between node1 and node2.
     * 
     * @param node1
     * @param node2
     * @return the weight of the edge between node1 and node2, -1 if none.
     */
	@Override
	public double getEdge(int node1, int node2) {
		if(!hasEdge(node1, node2))
			return -1;
		return ((NodeInfo)getNode(node1)).getWeightEdge(node2);
	}

	/**
	 * This method adds a new node to the graph with the given key.
	 * 
	 * @param key
	 */
	@Override
	public void addNode(int key) {
		if(!this.g.containsKey(key)) {
			node_info n=new NodeInfo(key);
			this.g.put(key, n);
			numOfNodes++;
			mc++;
		}
	}

	/**
	 * This method connects an edge between node1 and node2, with an edge with weight >=0.
	 * If the edge node1-node2 already exists - the method updates the weight of the edge.
	 * 
	 * @param node1, node2, w - the weight of the edge between node1 and node2.
	 */
	@Override
	public void connect(int node1, int node2, double w) {
		if(w<0) {
			throw new ArithmeticException("the weight should be non-negative");
		}
		if(hasEdge(node1, node2)) {
			((NodeInfo)getNode(node1)).ni.replace(node2, w); 
			((NodeInfo)getNode(node2)).ni.replace(node1, w);
			mc++;
		}
		else if(this.g.containsKey(node1) && this.g.containsKey(node2) && node1!=node2) {
			((NodeInfo)getNode(node1)).ni.put(node2, w);
			((NodeInfo)getNode(node2)).ni.put(node1, w);
			numOfEdge++;
			mc++;
		}
	}

	/**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     *
     * @return Collection<node_indo>
     */
	@Override
	public Collection<node_info> getV() {
		return this.g.values();
	}

	 /**
     * This method returns a collection containing all the nodes that
	 * connected to the node with the given node_id.
     *
	 * @param node_id
     * @return Collection<node_info> - collection of the node_id's neighbors.
     */
	@Override
	public Collection<node_info> getV(int node_id) {
		Collection<node_info> neighbors= new LinkedList<node_info>();
		if(this.g.containsKey(node_id)) {
			for(int node: ((NodeInfo)getNode(node_id)).ni.keySet()) {
				neighbors.add(getNode(node));
			}
		}
		return neighbors;
	}

	 /**
     * This method deletes the node (with the given key) from the graph,
     * and removes all edges that connected to this node.
     *
     * @param key
     * @return the node_info that removed from the graph, null if none.
     */
	@Override
	public node_info removeNode(int key) {
		if(!this.g.containsKey(key))
			return null;
		for(node_info node: getV(key)) {
			((NodeInfo)node).ni.remove(key);
			numOfEdge--;
			mc++;
		}
		mc++;
		numOfNodes--;
		return this.g.remove(key);
	}

	 /**
     * This method deletes the edge between node1 and node2 in the graph.
     *
     * @param node1
     * @param node2
     */
	@Override
	public void removeEdge(int node1, int node2) {
		if(hasEdge(node1, node2)) {
			((NodeInfo)getNode(node1)).ni.remove(node2);
			((NodeInfo)getNode(node2)).ni.remove(node1);
			numOfEdge--;
			mc++;
		}
	}

	/**
     * This method returns the number of vertices (nodes) in the graph.
     *
     * @return
     */
	@Override
	public int nodeSize() {
		return numOfNodes;
	}

	/**
     * This method returns the number of edges in the graph.
     *
     * @return
     */
	@Override
	public int edgeSize() {
		return numOfEdge;
	}

	/**
	 * This method returns the Mode Count.
	 * The Mode Count count any change in the graph.
	 */
	@Override
	public int getMC() {
		return mc;
	}

	/**
	 * This method checks if this graph and o graph are equals - 
	 * which be used in the tests.
	 * 
	 * @param o - the node to the compare.
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if(o==this) 
			return true;
		if(o==null) 
			return false;
		if(!(o instanceof weighted_graph)) 
			return false;
		weighted_graph gr= (weighted_graph)o;
		if(this.numOfNodes!=gr.nodeSize() || this.numOfEdge!=gr.edgeSize() || this.mc!=gr.getMC()) 
			return false;
		for(node_info ng: gr.getV()) {
			int keyG = ng.getKey();
			if(this.g.containsKey(keyG)) {
				node_info nt = this.getNode(keyG);
				if(!((NodeInfo)nt).equals((NodeInfo)ng))
					return false;
			}
			else
				return false;
		}
		return true;
	}

	/**
	 * This method returns a string that represents list of all the nodes in the graph with their neighbors.
	 *
	 * @return
	 */
	@Override
	public String toString(){
		return ""+this.getV();
	}
}
