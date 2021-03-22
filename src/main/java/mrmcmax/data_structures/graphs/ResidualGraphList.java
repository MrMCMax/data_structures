package mrmcmax.data_structures.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ResidualGraphList extends Graph {

	private int maxCap;

	public ResidualGraphList(int n_people, int n_wifis) {
		super(2 + n_people + n_wifis);
	}

	@Override
	public void addEdge(int v_in, int v_out, int capacity) {
		int pos = array.get(v_in).size();
		int reverse = array.get(v_out).size();
		array.get(v_in).add(new OneEndpointEdge(v_out, capacity, reverse));
		array.get(v_out).add(new OneEndpointEdge(v_in, 0, pos));
		maxCap = Math.max(maxCap, capacity);
	}

	public boolean hasAugmentingPath(int s, int t, Function<OneEndpointEdge, Boolean> labelCondition) {
		return existsPathWithConditionBFS(s, t, labelCondition);
	}
	
	public int EdmondsKarp(int s, int t) {
		return EdmondsKarpDelta(s, t, 0);
	}

	private int EdmondsKarpDelta(int s, int t, int delta) {
		int maxFlow = 0;

		while (hasAugmentingPath(s, t, (edge) -> edge.remainingCapacity() > delta)) {
			int bottleneck = Integer.MAX_VALUE;
			int v_out = t;

			// Find the bottleneck value
			while (v_out != s) {
				Backtrack back = bfsBacktrack[v_out];
				OneEndpointEdge e = array.get(back.v_in).get(back.index);
				bottleneck = Math.min(bottleneck, e.remainingCapacity());
				v_out = back.v_in;
			}

			// update the network
			v_out = t;
			while (v_out != s) {
				Backtrack back = bfsBacktrack[v_out];
				int v_in = back.v_in;
				OneEndpointEdge forward = array.get(v_in).get(back.index);
				forward.augment(bottleneck);
				array.get(v_out).get(forward.reverseEdgeIndex).decrement(bottleneck);
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
