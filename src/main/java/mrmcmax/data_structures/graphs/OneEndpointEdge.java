package mrmcmax.data_structures.graphs;

public class OneEndpointEdge {
	public int endVertex;
	public int capacity;
	public int flow;
	public int reverseEdgeIndex;
	
	public OneEndpointEdge(int v) {
		this.endVertex = v;
		this.capacity = -1;
		this.flow = -1;
	}
	
	public OneEndpointEdge(int v, int capacity) {
		this(v, capacity, -1);
	}
	
	public OneEndpointEdge(int v, int capacity, int reverseEdgeIndex) {
		this(v, capacity, reverseEdgeIndex, 0);
	}
	
	public OneEndpointEdge(int v, int capacity, int reverseEdgeIndex, int flow) {
		this.endVertex = v;
		this.capacity = capacity;
		this.reverseEdgeIndex = reverseEdgeIndex;
		this.flow = flow;
	}
	
	public void augment(int cap) {
		if (remainingCapacity() < cap) {
			throw new GraphException("Tried to augment the flow above the capacity:"
					+ " Flow " + flow + ", capacity " + capacity + ", tried " + cap);
		} else {
			flow = flow + cap;
		}
	}
	
	public void decrement(int cap) {
//		if (remainingCapacity() < cap) {
//			throw new GraphException("Tried to decrement the flow below 0: "
//					+ "Flow " + flow + ", tried decrement " + cap);
//		} else {
			flow = flow - cap;
//		}
	}
	
	public int remainingCapacity() {
		return capacity - flow;
	}
}
