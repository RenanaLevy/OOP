package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ex1.src.*;

/**
 * Test to WGraph_Algo class.
 */
class WGraph_Algo_Test {

	weighted_graph g= new WGraph_DS();
	weighted_graph_algorithms ga= new WGraph_Algo();

	/**
	 * Building graph with 10 nodes, 13 edges
	 */
	@BeforeEach
	public void buildingGraph() {
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.addNode(4);
		g.addNode(5);
		g.addNode(6);
		g.addNode(7);
		g.addNode(8);
		g.addNode(9);
		g.addNode(10);
		g.connect(1, 2, 8.9);
		g.connect(1, 4, 6.3);
		g.connect(1, 3, 8.9);
		g.connect(2, 9, 1);
		g.connect(2, 4, 1);
		g.connect(2, 5, 3);
		g.connect(3, 7, 11);
		g.connect(3, 4, 13.9);
		g.connect(4, 8, 18.5);
		g.connect(4, 5, 2.1);
		g.connect(5, 6, 9);
		g.connect(5, 10, 3.2);
		g.connect(8, 10, 5.7);
		ga.init(g);
	}
	
	/**
	 * Test to copy method.
	 */
	@Test
	public void copy() {
		weighted_graph gc= ga.copy();
		System.out.println(g.toString());
		System.out.println(gc.toString());
		//assertTrue(g.equals(gc));
		assertEquals(g, gc);
	}
	
	/**
	 * Test to isConnected method.
	 */
	@Test
	public void isConnected() {
		assertTrue(ga.isConnected());	//The graph is connected. 
		g.removeEdge(3, 7);
		assertFalse(ga.isConnected());	//The graph isn't connected. 
		
		ga.init(null);
		assertTrue(ga.isConnected());	//The graph is null.
		
		g= new WGraph_DS();
		ga.init(g);
		assertTrue(ga.isConnected());	//The graph is empty.
		
		g.addNode(0);
		assertTrue(ga.isConnected());	//A graph with 1 node.
		
		g.addNode(1);
		assertFalse(ga.isConnected());	//A graph with 2 nodes that not connected.
		
		g.connect(1, 0, 5.62);
		assertTrue(ga.isConnected());	//A graph with 2 nodes that connected.
	}
	
	/**
	 * Test to shortestPathDist and shortestPath methods.
	 */
	@Test
	public void shortestPath() {
		assertEquals(19.9, ga.shortestPathDist(7, 1));	//Path between 2 node that in the graph and connected. 
		assertEquals(3, ga.shortestPath(7, 1).size());
		assertEquals(17.3, ga.shortestPathDist(8, 1));	//Path between 2 node that in the graph and connected.
		assertEquals(5, ga.shortestPath(8, 1).size());
		g.removeEdge(2, 9);
		assertEquals(-1, ga.shortestPathDist(4, 9));	//Path between 2 node that in the graph and not connected.
		assertNull(ga.shortestPath(4, 9));
		assertEquals(-1, ga.shortestPathDist(9, 4));	//Path between 2 node that in the graph and not connected.
		assertNull(ga.shortestPath(9, 4));
		assertEquals(-1, ga.shortestPathDist(6, 12));	//Path between node in the graph to node that not in the graph.
		assertNull(ga.shortestPath(6, 12));
		assertEquals(-1, ga.shortestPathDist(13, 1));	//Path between node that not in the graph to node in the graph.
		assertNull(ga.shortestPath(13, 1));
		assertEquals(-1, ga.shortestPathDist(13, 11));	//Path between 2 node that not in the graph and connected.
		assertNull(ga.shortestPath(13, 11));
		assertEquals(-1, ga.shortestPathDist(3, 3));	//Path between node in the graph to himself.
		assertEquals(1, ga.shortestPath(3, 3).size());
		assertEquals(-1, ga.shortestPathDist(15, 15));	//Path between node that not in the graph to himself.
		assertNull(ga.shortestPath(15, 15));
	}
	
	/**
	 * Test to save and load methods.
	 */
	@Test
	public void file() {
		assertTrue(ga.save("test1.txt"));	//Save graph to file.
		assertTrue(ga.load("test1.txt"));	//Loading graph from a file found.  
		assertFalse(ga.load("test2.txt"));	//Loading graph from a file that not found. 
		assertFalse(ga.load(""));			//Loading graph from nothing file. 
		assertFalse(ga.save(""));			//Save graph to nothing file. 
	}
}
