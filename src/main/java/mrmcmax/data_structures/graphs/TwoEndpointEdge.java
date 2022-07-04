package mrmcmax.data_structures.graphs;

public class TwoEndpointEdge {
	
	public int u;
	public int v;
	
	public TwoEndpointEdge(int u, int v) {
		this.u = u;
		this.v = v;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + u;
		result = prime * result + v;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TwoEndpointEdge))
			return false;
		TwoEndpointEdge other = (TwoEndpointEdge) obj;
		if (u != other.u)
			return false;
		if (v != other.v)
			return false;
		return true;
	}
	
	
}
