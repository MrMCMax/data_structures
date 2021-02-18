package mrmcmax.data_structures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import mrmcmax.data_structures.linear.ArrayLimitQueue;
import mrmcmax.data_structures.linear.EasyQueue;

public class ResidualGraph {

	private List<Map<Integer, Integer>> graph; // Key: v_out, Value: capacity
	private OneEndpointEdge[] bfsBacktrack;
	private int maxCap;
	private EasyQueue<Integer> q;

	private int vertexNum;

	public ResidualGraph(int n_people, int n_wifis) {
		vertexNum = 2 + n_people + n_wifis;
		graph = new ArrayList<>(vertexNum);
		bfsBacktrack = new OneEndpointEdge[vertexNum];
		for (int i = 0; i < vertexNum; i++) {
			graph.add(new HashMap<>());
		}
		q = new ArrayLimitQueue<>(Integer.class, vertexNum);
	}

	public void addEdge(int v_in, int v_out, int capacity) {
		graph.get(v_in).put(v_out, capacity);
		graph.get(v_out).put(v_in, 0);
		maxCap = Math.max(maxCap, capacity);
	}

	public boolean hasAugmentingPath(int s, int t, Function<Integer, Boolean> labelCondition) {
		boolean[] visited = new boolean[vertexNum];
		q.reset();
		//Queue<Integer> q = new LinkedList<>();
		q.add(s);
		visited[s] = true;

		while (!q.isEmpty()) {
			int v_in = q.poll();
			Map<Integer, Integer> adj = graph.get(v_in);
			for (Map.Entry<Integer, Integer> entry : adj.entrySet()) {
				int v_out = entry.getKey(); 
				int cap = entry.getValue();
				if (labelCondition.apply(cap) && !visited[v_out]) {
					OneEndpointEdge e = new OneEndpointEdge(v_in, cap);
					bfsBacktrack[v_out] = e;
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
	
	public int EdmondsKarp(int s, int t) {
		return EdmondsKarpDelta(s, t, 0);
	}

	private int EdmondsKarpDelta(int s, int t, int delta) {
		int maxFlow = 0;

		while (hasAugmentingPath(s, t, (cap) -> cap > delta)) {
			int bottleneck = Integer.MAX_VALUE;
			int v_out = t;

			// Find the bottleneck value
			while (v_out != s) {
				OneEndpointEdge e = bfsBacktrack[v_out];
				bottleneck = Math.min(bottleneck, e.capacity);
				v_out = e.v;
			}

			// update the network
			v_out = t;
			while (v_out != s) {
				int v_in = bfsBacktrack[v_out].v;
				
				graph.get(v_in).merge(v_out, bottleneck, (old, flow) -> old - flow); // update forward
				graph.get(v_out).merge(v_in, bottleneck, (old, flow) -> old + flow); // update backward
				v_out = v_in;
			}
			maxFlow += bottleneck;
		}
		return maxFlow;
	}

	public int ScalingAlgorithm(int s, int t) {
		int maxFlow = 0;
		
		int delta = biggest2PowSmallerThan(maxCap);
		while (delta > 0) {
			int flow = EdmondsKarpDelta(s, t, delta);
			maxFlow += flow;
			delta >>= 1;
		}
		int flow = EdmondsKarpDelta(s, t, delta);
		maxFlow += flow;
		return maxFlow;
	}

	public int degreeOut(int v_in) {
		return graph.get(v_in).size();
	}
	
	/**
	 * Precondition: n > 0
	 * @param n the integer
	 * @return the biggest power of 2 that is smaller or equal than n
	 */
	public static int biggest2PowSmallerThan(int n) {
		if ((n & n-1) == 0)
			return n;
		int ret = 1;
		while (n > 1) {
			n >>= 1;
			ret <<= 1;
		}
		return ret;
	}
}
