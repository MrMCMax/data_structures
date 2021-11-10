package mrmcmax.data_structures.graphs;

import java.util.ArrayList;

public class UndirectedGraph extends DirectedGraph {
	
	public UndirectedGraph(int vertices) {
		super(vertices);
	}
	
	@Override
	public void addEdge(int v_in, int v_out) {
		array.get(v_in).add(new OneEndpointEdge(v_out));
		array.get(v_out).add(new OneEndpointEdge(v_in));
		numEdges++;
	}
	
	@Override
	public void addEdge(int v_in, int v_out, int capacity) {
		array.get(v_in).add(new OneEndpointEdge(v_out, capacity));
		array.get(v_out).add(new OneEndpointEdge(v_in, capacity));
		numEdges++;
	}
	
	public static Graph completeUndirectedGraph(int n) {
		Graph kn = new DirectedGraph(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j != i) {
					kn.addEdge(i, j);
				}
			}
		}
		return kn;
	}
}
