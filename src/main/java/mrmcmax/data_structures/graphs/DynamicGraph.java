package mrmcmax.data_structures.graphs;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Directed dynamic graph implementation.
 * @author max
 *
 */
public class DynamicGraph implements Graph {
	
	/**
	 * Maps a vertex index to its list. 
	 * The TreeMap will avoid space loss when adding and deleting many vertices
	 * in the same session.
	 * If the graphs get big, a predecessor data structure with 
	 * O(log log u) = O(log log INT_MAX_SIZE) running time would be theoretically faster
	 * (when O(log log u)>O(log n) where n=|V(G)|)
	 */
	protected TreeMap<Integer, ArrayList<OneEndpointEdge>> array;

	protected int numEdges;
	protected int nextVertex;
	
	public DynamicGraph() {
		array = new TreeMap<>();
		numEdges = 0;
		nextVertex = 0;
	}
	
	/**
	 * Adds a new vertex and returns its ID.
	 * @return the ID of the new vertex.
	 */
	public int addVertex() {
		array.put(nextVertex, new ArrayList<OneEndpointEdge>());
		return nextVertex++;
	}

	/**
	 * Tries to add a vertex with the given ID to the graph.
	 * @param n the ID of the new vertex.
	 * @return the ID of the new vertex (equal to the parameter).
	 * @throws GraphException if a vertex with that ID is already present.
	 */
	public int addVertex(int id) {
		if (array.containsKey(id)) {
			throw new GraphException("Vertex " + id + " already exists");
		}
		array.put(id, new ArrayList<OneEndpointEdge>());
		return id;
	}
	
	@Override
	public void addEdge(int v_in, int v_out) {
		ArrayList<OneEndpointEdge> adj = array.get(v_in);
		if (adj == null) throw new GraphException("The vertex " + v_in + " does not exist");
		if (!array.containsKey(v_out)) throw new GraphException("The vertex " + v_out + " does not exist");
		adj.add(new OneEndpointEdge(v_out));
	}

	@Override
	public void addEdge(int v_in, int v_out, int capacity) {
		ArrayList<OneEndpointEdge> adj = array.get(v_in);
		if (adj == null) throw new GraphException("The vertex " + v_in + " does not exist");
		if (!array.containsKey(v_out)) throw new GraphException("The vertex " + v_out + " does not exist");
		adj.add(new OneEndpointEdge(v_out, capacity));
	}

	@Override
	public boolean existsEdge(int v_in, int v_out) {
		ArrayList<OneEndpointEdge> adj = array.get(v_in);
		if (adj == null) return false;
		return adj.contains(new OneEndpointEdge(v_out));
	}

	@Override
	public int edgeCount(int v_in, int v_out) {
		ArrayList<OneEndpointEdge> adj = array.get(v_in);
		if (adj == null || adj.size() == 0) return 0;
		int count = 0;
		for (OneEndpointEdge edge : adj) {
			if (edge.endVertex == v_out) {
				count++;
			}
		}
		return count;
	}

	@Override
	public boolean existsPathBFS(int s, int t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[][] getAdjacencyMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int n() {
		return array.size();
	}

	@Override
	public int m() {
		return numEdges;
	}
	
	public static void main(String[] args) {
		DynamicGraph dyn = new DynamicGraph();
		int first = dyn.addVertex();
		int second = dyn.addVertex();
		int third = dyn.addVertex();
		int fourth = dyn.addVertex();
		dyn.addEdge(first, second);
		dyn.addEdge(second, third);
		dyn.addEdge(first, fourth);
		dyn.addEdge(first, third);
		dyn.addEdge(second, fourth);
		System.out.println(dyn.array.get(first));
		System.out.println(dyn.array.get(second));
		System.out.println(dyn.array.get(third));
		System.out.println(dyn.array.get(fourth));
		
	}
}
