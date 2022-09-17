package mrmcmax.data_structures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import mrmcmax.data_structures.graphs.dijkstra.BasicDijkstra;
import mrmcmax.data_structures.graphs.dijkstra.Dijkstra;
import mrmcmax.data_structures.graphs.dijkstra.BinaryHeapDijkstra;
import mrmcmax.data_structures.linear.ArrayLimitQueue;
import mrmcmax.data_structures.linear.EasyQueue;

public class DirectedGraph implements Graph {
	//Directed graph
	//Git test 6
	protected List<ArrayList<OneEndpointEdge>> array;

	protected int numVertices;
	protected int numEdges;
	
	/* BFS */
	public Backtrack[] bfsBacktrack;			// First: v_in, Second: index
	public EasyQueue<Integer> q;
	
	/* DIJKSTRA */
	private int[] distances;
	private int[] parents;
	
	/************************/
	/****** SETTERS *********/
	/************************/
	
	public DirectedGraph(int vertices) {
		numVertices = vertices;
		this.array = new ArrayList<>(numVertices);
		for (int i = 0; i < vertices; i++) {
			array.add(new ArrayList<>());
		}
		initBFS();
	}
	
	private void initBFS() {
		bfsBacktrack = new Backtrack[numVertices];
		q = new ArrayLimitQueue<>(Integer.class, numVertices);
	}
	
	@Override
	public void addEdge(int v_in, int v_out) {
		array.get(v_in).add(new OneEndpointEdge(v_out));
		numEdges++;
	}
	
	@Override
	public void addEdge(int v_in, int v_out, int capacity) {
		array.get(v_in).add(new OneEndpointEdge(v_out, capacity));
		numEdges++;
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
	
	
	/************************/
	/****** GETTERS *********/
	/************************/
	
	
	@Override
	public int n() {
		return numVertices;
	}

	@Override
	public int m() {
		return numEdges;
	}
	
	public List<OneEndpointEdge> getAdjacencyList(int vertex) {
		return array.get(vertex);
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public double[][] getAdjacencyMatrix() {
		double[][] matrix = new double[numVertices][numVertices];
		Iterator<ArrayList<OneEndpointEdge>> vertices = array.iterator();
		for (int i = 0; i < numVertices; i++) {
			ArrayList<OneEndpointEdge> adj = vertices.next();
			for (OneEndpointEdge edge : adj) {
				matrix[i][edge.endVertex] = 1;
			}
		}
		return matrix;
	}
	
	public double[] getDegreeSequence() {
		double[] d = new double[numVertices];
		for (int i = 0; i < numVertices; i++) {
			d[i] = array.get(i).size();
		}
		return d;
	}

	/************************/
	/***** ALGORITHMS *******/
	/************************/
	

	@Override
	public boolean existsPathBFS(int s, int t) {
		return existsPathWithConditionBFS(s, t, (c) -> true);
	}
	
	public boolean existsPathWithConditionBFS(int s, int t, Function<OneEndpointEdge, Boolean> edgeCondition) {
		boolean[] visited = new boolean[numVertices];
		q.reset();
		//Queue<Integer> q = new LinkedList<>();
		q.add(s);
		visited[s] = true;

		while (!q.isEmpty()) {
			int v_in = q.poll();
			List<OneEndpointEdge> adj = array.get(v_in);
			for (int j = 0; j < adj.size(); j++) {
				OneEndpointEdge edge = adj.get(j);
				int v_out = edge.endVertex;
				if (edgeCondition.apply(edge) && !visited[v_out]) {
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
	
	public Dijkstra dijkstraInterface(int s) {
		Dijkstra d = new BasicDijkstra();
		d.computeDijkstra(this, s);
		return d;
	}
	
	public Dijkstra dijkstraBinaryHeapInterface(int s) {
		Dijkstra d = new BinaryHeapDijkstra();
		d.computeDijkstra(this, s);
		return d;
	}
	
	public static class Backtrack {
		public int v_in;
		public int edgeIndex;
		
		public Backtrack(int v_in, int index) {
			this.v_in = v_in;
			this.edgeIndex = index;
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
