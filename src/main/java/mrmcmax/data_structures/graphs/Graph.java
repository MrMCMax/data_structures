package mrmcmax.data_structures.graphs;

public interface Graph {

	void addEdge(int v_in, int v_out);

	void addEdge(int v_in, int v_out, int capacity);

	boolean existsEdge(int v_in, int v_out);

	int edgeCount(int v_in, int v_out);

	boolean existsPathBFS(int s, int t);
	
	int[][] getAdjacencyMatrix();

}