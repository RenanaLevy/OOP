package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ex1.src.*;

/**
 * Test to WGraph_DS class.
 */
class WGraph_DS_Test {
	
	weighted_graph g= new WGraph_DS();

	/**
	 * Building graph with 5 nodes, 6 edges
	 */
	@BeforeEach
	public void buildingGraph() {
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.connect(1, 2, 1);
		g.connect(1, 3, 6);
		g.connect(1, 4, 3);
		g.connect(1, 5, 7);
		g.connect(2, 5, 9);
		g.connect(5, 3, 5);
		assertEquals(5, g.nodeSize());
		assertEquals(6, g.edgeSize());
		assertEquals(11, g.getMC());
	}

	/**
	 * Test to getNode and addNode methods 
	 */
	@Test
	public void nodes() {
		assertNull(g.getNode(12));		//A node that isn't in the graph.
		assertNotNull(g.getNode(4));	//A node that is in the graph.
		g.addNode(5);							//Add node which there is in the graph.
		assertEquals(5, g.nodeSize());
		assertEquals(11, g.getMC());
	}

	/**
	 * Test to hasEdge method.
	 */
	@Test
	public void hasEdge() {
		assertTrue(g.hasEdge(5, 2));	//A edge that is in the graph.  		
		assertFalse(g.hasEdge(4, 3));	//A edge that isn't in the graph.
		assertFalse(g.hasEdge(1, 15));	//Edge from node that is in the graph to node that not.
		assertFalse(g.hasEdge(15, 1));	//Edge from node that isn't in the graph to node that their.
		assertFalse(g.hasEdge(11, 13));	//Edge between 2 nodes that aren't in the graph.
		assertFalse(g.hasEdge(5, 5));	//Edge from node that is in the graph to himself.
		assertFalse(g.hasEdge(12, 12));	//Edge from node that isn't in the graph to himself.
	}

	/**
	 * Test to connect and getEdge methods.
	 */
	@Test
	public void edges() {
		assertEquals(3, g.getEdge(1, 4));	
		g.connect(1, 4, 10);	//Connect 2 nodes that connected.
		assertEquals(10, g.getEdge(1, 4));	//The Edge 
		g.connect(2, 4, 0);		//Connect 2 nodes that didn't connected.
		assertEquals(0, g.getEdge(4, 2));
		g.connect(4, 12, 5);	//Connect 2 nodes- the first is in the graph and the second not.
		assertEquals(-1, g.getEdge(4, 12));
		g.connect(9, 3, 5);		//Connect 2 nodes- the first isn't in the graph and the second their.
		assertEquals(-1, g.getEdge(9, 3));
		g.connect(12, 13, 3);	//Connect 2 nodes that aren't in the graph.
		assertEquals(-1, g.getEdge(12, 13));
		g.connect(3, 3, 7);		//Connect node that is in the graph to himself.
		assertEquals(-1, g.getEdge(3, 3));
		g.connect(17, 17, 1);	//Connect node that isn't in the graph to himself.
		assertEquals(-1, g.getEdge(17, 17));
		
		assertEquals(7, g.edgeSize());
		assertEquals(13, g.getMC());
	}
	
	/**
	 * Test to getV method.
	 */
	@Test
	public void getV() {
		int length= g.getV(15).size();	//Neighbors of node that isn't in the graph.
		assertEquals(0, length);
		int length2= g.getV(1).size();
		assertEquals(4, length2);
	}
	
	/**
	 * Test to removeNode method.
	 */
	@Test
	public void removeNode() {
		node_info n= g.removeNode(1);	//Remove a node that is in the graph.
		assertEquals(1, n.getKey());
		assertEquals(2, g.edgeSize());
		assertEquals(4, g.nodeSize());
		n= g.removeNode(12);			//Remove a node that isn't in the graph.
		assertNull(n);
		assertEquals(2, g.edgeSize());
		assertEquals(4, g.nodeSize());
		assertEquals(16, g.getMC());
	}
	
	/**
	 * Test to removeEdge method.
	 */
	@Test
	public void removeEdge() {
		g.removeEdge(5, 1);		//Remove a edge that is in the graph.
		g.removeEdge(1, 5);		//Remove a edge that isn't in the graph.
		g.removeEdge(4, 11);	//Remove a edge between node that is in the graph to node that not.
		g.removeEdge(7, 2);		//Remove a edge between node that isn't in the graph to node that their.
		g.removeEdge(17, 13);	//Remove a edge between 2 nodes that aren't in the graph.
		g.removeEdge(2, 2);		//Remove a edge between node that is in the graph to himself.
		g.removeEdge(8, 8);		//Remove a edge between node that isn't in the graph to himself.
		assertEquals(5, g.nodeSize());
		assertEquals(5, g.edgeSize());
		assertEquals(12, g.getMC());
	}
}
