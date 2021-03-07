package mrmcmax.data_structures.graphs;

public class OneEndpointEdge {
	public int endVertex;
	public int capacity;
	public int reverseEdgeIndex;
	
	public OneEndpointEdge(int v) {
		this.endVertex = v;
		this.capacity = -1;
	}
	
	public OneEndpointEdge(int v, int capacity) {
		this(v, capacity, -1);
	}
	
	public OneEndpointEdge(int v, int capacity, int reverseEdgeIndex) {
		this.endVertex = v;
		this.capacity = capacity;
		this.reverseEdgeIndex = reverseEdgeIndex;
	}
}
