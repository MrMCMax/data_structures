package mrmcmax.data_structures.graphs;

public interface Graph {

	/**
	 * Adds an edge (v_in, v_out) to the graph.
	 * @param v_in first endpoint of the edge
	 * @param v_out second endpoint of the edge
	 */
	void addEdge(int v_in, int v_out);

	/**
	 * Adds an edge (v_in, v_out) to the graph with a specified capacity.
	 * @param v_in first endpoint of the edge
	 * @param v_out	second endpoint of the edge
	 * @param capacity capacity of the edge
	 */
	void addEdge(int v_in, int v_out, int capacity);

	/**
	 * Returns true if there exists an edge from v_in to v_out in the graph.
	 * @param v_in first endpoint
	 * @param v_out second endpoint
	 * @return true if (v_in, v_out) is in E(G)
	 */
	boolean existsEdge(int v_in, int v_out);

	/**
	 * Returns the number of edges from v_in to v_out (could be zero)
	 * @param v_in first endpoint
	 * @param v_out second endpoint
	 * @return the number of edges from v_in to v_out
	 */
	int edgeCount(int v_in, int v_out);

	/**
	 * Checks if there exists a path from s to t using a BFS search.
	 * @param s starting vertex.
	 * @param t ending vertex.
	 * @return true iff there exists a path from s to t.
	 */
	boolean existsPathBFS(int s, int t);
	
	/**
	 * Returns the adjacency matrix of the graph.
	 * @return A(G)
	 */
	int[][] getAdjacencyMatrix();

	/**
	 * Gets the number of vertices of the graph.
	 * @return |V(G)|
	 */
	int n();
	
	/**
	 * Gets the number of edges of the graph.
	 * @return |E(G)|
	 */
	int m();
}