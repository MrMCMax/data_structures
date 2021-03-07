package mrmcmax.data_structures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

import mrmcmax.data_structures.linear.ArrayLimitQueue;
import mrmcmax.data_structures.linear.EasyQueue;

public class Graph {
	
	protected List<ArrayList<OneEndpointEdge>> array;

	/* BFS */
	protected Backtrack[] bfsBacktrack;			// First: v_in, Second: index
	protected EasyQueue<Integer> q;
	
	/* DIJKSTRA */
	private int[] distances;
	private int[] parents;
	
	protected int numVertices;
	protected int numEdges;
	
	public Graph(int vertices) {
		numVertices = vertices;
		this.array = new ArrayList<>(numVertices);
		for (int i = 0; i < vertices; i++) {
			array.add(new ArrayList<>());
		}
		initBFS();
	}
	
	public static Graph completeUndirectedGraph(int n) {
		Graph kn = new Graph(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j != i) {
					kn.addEdge(i, j);
				}
			}
		}
		return kn;
	}
	
	private void initBFS() {
		bfsBacktrack = new Backtrack[numVertices];
		q = new ArrayLimitQueue<>(Integer.class, numVertices);
	}
	
	public int getNumVertices() {
		return numVertices;
	}

	public void addEdge(int v_in, int v_out) {
		array.get(v_in).add(new OneEndpointEdge(v_out));
		numEdges++;
	}
	
	public void addEdge(int v_in, int v_out, int capacity) {
		array.get(v_in).add(new OneEndpointEdge(v_out, capacity));
		numEdges++;
	}
	
	public void addUndirectedEdge(int v_in, int v_out) {
		addEdge(v_in, v_out);
		addEdge(v_out, v_in);
	}
	
	public void addUndirectedEdge(int v_in, int v_out, int capacity) {
		addEdge(v_in, v_out, capacity);
		addEdge(v_out, v_in, capacity);
	}
	
	public int getNumEdges() {
		return numEdges;
	}
	
	public int getNumUndirectedEdges() {
		return numEdges / 2;
	}
	
	public void setEdgeCapacity(int v_in, int v_out, int capacity) {
		List<OneEndpointEdge> outEdges = array.get(v_in);
		boolean found = false;
		for (int i = 0; i < outEdges.size() && !found; i++) {
			if (outEdges.get(i).endVertex == v_out) {
				outEdges.get(i).capacity = capacity;
				found = true;
			}
		}
		if (!found) {
			throw new NoSuchElementException("Edge not found");
		}
	}
	
	public boolean existsEdge(int v_in, int v_out) {
		boolean found = false;
		int i = 0;
		ArrayList<OneEndpointEdge> adj = array.get(v_in);
		while (i < adj.size() && !found) {
			if (adj.get(i).endVertex == v_out) {
				found = true;
			} else {
				i++;
			}
		}
		return found;
	}
	
	public int edgeCount(int v_in, int v_out) {
		int count = 0;
		int i = 0;
		ArrayList<OneEndpointEdge> adj = array.get(v_in);
		while (i < adj.size()) {
			if (adj.get(i).endVertex == v_out) count++;
			i++;
		}
		return count;
	}
	
	public int degreeOut(int v_in) {
		return array.get(v_in).size();
	}	
	
	public List<Integer> getOddDegreeVertices() {
		List<Integer> res = new LinkedList<>();
		for (int i = 0; i < numVertices; i++) {
			if (degreeOut(i) % 2 == 1) {
				res.add(i);
			}
		}
		return res;
	}
	
	public boolean existsPathBFS(int s, int t) {
		return existsPathWithConditionBFS(s, t, (c) -> true);
	}
	
	public boolean existsPathWithConditionBFS(int s, int t, Function<Integer, Boolean> labelCondition) {
		boolean[] visited = new boolean[numVertices];
		q.reset();
		//Queue<Integer> q = new LinkedList<>();
		q.add(s);
		visited[s] = true;

		while (!q.isEmpty()) {
			int v_in = q.poll();
			List<OneEndpointEdge> adj = array.get(v_in);
			for (int j = 0; j < adj.size(); j++) {
				int v_out = adj.get(j).endVertex; 
				int cap = adj.get(j).capacity;
				if (labelCondition.apply(cap) && !visited[v_out]) {
					Backtrack back = new Backtrack(v_in, j);
					bfsBacktrack[v_out] = back;
					visited[v_out] = true;
					if (v_out == t) {
						return true;
					}
					q.add(v_out);
				}
			}
			if (v_in == t)
				return true;
		}
		return false;
	}
	
	public void computeShortestPathsDijkstra(int s) {
		distances = new int[numVertices];
		parents = new int[numVertices];
		Arrays.fill(distances, Integer.MAX_VALUE);
		Arrays.fill(parents, -1);
		distances[s] = 0;
		Set<Integer> S = new HashSet<>();
		Set<Integer> V = new HashSet<>();
		for (int i = 0; i < numVertices; i++) {
			V.add(i);
		}
		do {
			/* Retrieve the minimum by iterating through all the vertices in V*/
			Iterator<Integer> it = V.iterator();
			int startVertex = it.next();
			while (it.hasNext()) {
				int element = it.next();
				if (distances[element] < distances[startVertex]) {
					startVertex = element;
				}
			}
			S.add(startVertex);
			V.remove(startVertex);
			/* Dijkstra */
			List<OneEndpointEdge> adj = array.get(startVertex);
			for (int i = 0; i < adj.size(); i++) {
				OneEndpointEdge edge = adj.get(i);
				int endVertex = edge.endVertex;
				if (!S.contains(endVertex)) {
					if (distances[endVertex] > edge.capacity + distances[startVertex]) {
						distances[endVertex] = edge.capacity + distances[startVertex];
						parents[endVertex] = startVertex;
					}
				}
			}
		} while (!V.isEmpty());
	}

	public int[] getDistances() {
		return distances;
	}
	
	public int[] getParents() {
		return parents;
	}
	
	protected static class Backtrack {
		public int v_in;
		public int index;
		
		public Backtrack(int v_in, int index) {
			this.v_in = v_in;
			this.index = index;
		}
	}
	
	public static <E extends Comparable<E>> E minSet(Set<E> set) {
		if (set.isEmpty()) throw new NoSuchElementException("Set is empty");
		E min;
		Iterator<E> it = set.iterator();
		min = it.next();
		while (it.hasNext()) {
			E element = it.next();
			if (element.compareTo(min) < 0) {
				min = element;
			}
		}
		return min;
	}
}
