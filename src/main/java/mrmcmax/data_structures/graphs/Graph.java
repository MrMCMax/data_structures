package mrmcmax.data_structures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import mrmcmax.data_structures.graphs.Graph.Backtrack;
import mrmcmax.data_structures.linear.ArrayLimitQueue;
import mrmcmax.data_structures.linear.EasyQueue;

public class Graph {
	
	protected List<ArrayList<OneEndpointEdge>> array;

	protected Backtrack[] bfsBacktrack;			// First: v_in, Second: index
	protected EasyQueue<Integer> q;
	
	protected int vertexNum;
	
	public Graph(int vertices) {
		vertexNum = vertices;
		this.array = new ArrayList<>(vertexNum);
		for (int i = 0; i < vertices; i++) {
			array.add(new ArrayList<>());
		}
		initBFS();
	}
	
	private void initBFS() {
		bfsBacktrack = new Backtrack[vertexNum];
		q = new ArrayLimitQueue<>(Integer.class, vertexNum);
	}

	public void addEdge(int v_in, int v_out) {
		array.get(v_in).add(new OneEndpointEdge(v_out));
	}
	
	public void addEdge(int v_in, int v_out, int capacity) {
		array.get(v_in).add(new OneEndpointEdge(v_out, capacity));
	}
	
	public boolean existsEdge(int v_in, int v_out) {
		boolean found = false;
		int i = 0;
		ArrayList<OneEndpointEdge> adj = array.get(v_in);
		while (i < adj.size() && !found) {
			if (adj.get(i).v == v_out) {
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
			if (adj.get(i).v == v_out) count++;
			i++;
		}
		return count;
	}
	
	public int degreeOut(int v_in) {
		return array.get(v_in).size();
	}	
	
	public boolean existsPathBFS(int s, int t) {
		return existsPathWithConditionBFS(s, t, (c) -> true);
	}
	
	public boolean existsPathWithConditionBFS(int s, int t, Function<Integer, Boolean> labelCondition) {
		boolean[] visited = new boolean[vertexNum];
		q.reset();
		//Queue<Integer> q = new LinkedList<>();
		q.add(s);
		visited[s] = true;

		while (!q.isEmpty()) {
			int v_in = q.poll();
			List<OneEndpointEdge> adj = array.get(v_in);
			for (int j = 0; j < adj.size(); j++) {
				int v_out = adj.get(j).v; 
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
	
	protected static class Backtrack {
		public int v_in;
		public int index;
		
		public Backtrack(int v_in, int index) {
			this.v_in = v_in;
			this.index = index;
		}
	}
}
