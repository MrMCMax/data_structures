package mrmcmax.data_structures.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class ResidualGraphList extends Graph {

	private int maxCap;
	
	//For Dinics
	private int[] level;
	private int[] next;
	//For Push-relabel
	protected int[] heights;
	protected int[] excesses;
	//For any
	private int s;
	private int t;

	public ResidualGraphList(int n_people, int n_wifis) {
		super(2 + n_people + n_wifis);
	}
	
	public ResidualGraphList(int n) {
		super(n);
	}

	@Override
	public void addEdge(int v_in, int v_out, int capacity) {
		int pos = array.get(v_in).size();
		int reverse = array.get(v_out).size();
		array.get(v_in).add(new OneEndpointEdge(v_out, capacity, reverse));
		array.get(v_out).add(new OneEndpointEdge(v_in, 0, pos));
		maxCap = Math.max(maxCap, capacity);
		numEdges+=2;
	}
	
	public void setSource(int s) {
		this.s = s;
	}
	
	public void setSink(int t) {
		this.t = t;
	}
	
	public int getSource() { return s; }
	public int getSink() { return t; }
	
	/**
	 * Resets all flows to the zero flow.
	 */
	public void resetFlowsToZero() {
		for (List<OneEndpointEdge> adj : array) {
			for (OneEndpointEdge edge : adj) {
				edge.resetFlowToZero();
			}
		}
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
				OneEndpointEdge e = array.get(back.v_in).get(back.edgeIndex);
				bottleneck = Math.min(bottleneck, e.remainingCapacity());
				v_out = back.v_in;
			}

			// update the network
			v_out = t;
			while (v_out != s) {
				Backtrack back = bfsBacktrack[v_out];
				int v_in = back.v_in;
				OneEndpointEdge forward = array.get(v_in).get(back.edgeIndex);
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

	public int DinicAlgorithm(int s, int t) {
		level = new int[numVertices];
		// For stopping dead ends
		next = new int[numVertices];
		this.t = t;
		int maxFlow = 0;
		// Too obscure to use the BFS algorithm of Graph.
		// However we can use the data structures of bactrack and queue
		boolean theresPath = false;
		do {		
			q.reset();
			q.add(s);
			Arrays.fill(level, -1);		
			level[s] = 0;
			theresPath = false;
			int maxDistance = Integer.MAX_VALUE;
			while (!q.isEmpty()) {
				int v_in = q.poll();
				if (level[v_in] >= maxDistance) continue;
				List<OneEndpointEdge> adj = array.get(v_in);
				Iterator<OneEndpointEdge> it = adj.iterator();
				for (int j = 0; j < adj.size(); j++) {
					OneEndpointEdge edge = it.next();
					int v_out = edge.endVertex;
					if (edge.remainingCapacity() > 0 && level[v_out] == -1) {
						level[v_out] = level[v_in] + 1;
						// To backtrack from the shortest path
						// Backtrack back = new Backtrack(v_in, j);
						// bfsBacktrack[v_out] = back;
						// There may be several paths of the same length
						// between s and t. We can't stop bfs if we've found
						// t, because we wouldn't be finding all s-t paths
						// of a given length
						q.add(v_out);
					}
				}
				if (v_in == t) {
					theresPath = true;
					maxDistance = level[t];
				}
			}
			if (theresPath) {
				// Now, find a blocking flow
				// Next edge to consider in BFS (useful to stop dead ends)
				Arrays.fill(next, 0);
				int flow = Integer.MAX_VALUE;
				while (flow > 0) {
					flow = dinicDFS(s, Integer.MAX_VALUE);
					maxFlow += flow;
				}
			}
		} while (theresPath);

		return maxFlow;
	}

	private int dinicDFS(int s, int bottleneck) {
		if (s == t) {
			return bottleneck;
		}
		List<OneEndpointEdge> adj = array.get(s);
		int forwardFlow = 0;
		while (next[s] < adj.size() && forwardFlow == 0) {
			OneEndpointEdge edge = adj.get(next[s]);
			if (level[edge.endVertex] > level[s] &&
					edge.remainingCapacity() > 0) {
				forwardFlow = dinicDFS(edge.endVertex, 
						Math.min(bottleneck, edge.remainingCapacity()));
				if (forwardFlow > 0) {
					edge.augment(forwardFlow);
					array.get(edge.endVertex).get(edge.reverseEdgeIndex).decrement(forwardFlow);
					if (edge.remainingCapacity() == 0) {
						//Saturated edge
						next[s]++;
					}
				} else {
					//Dead end
					next[s]++;
				}
			} else {
				//Not an edge in the level graph
				next[s]++;
			}
		}
		return forwardFlow;
	}

	public int PushRelabel1Algorithm(int s, int t) {
		return PushRelabel1Algorithm(s, t, false);
	}
	
	protected int PushRelabel1Algorithm(int s, int t, boolean earlyStop) {
		int maxFlow = 0;
		initialize(s, t);
		computeExcesses();
		while (thereIsExcess()) {
			int height = -1;
			int maxHeight = -1;
			for (int i = 1; i < numVertices; i++) {
				if (excesses[i] == 0) continue;
				if (heights[i] > height) {
					height = heights[i];
					maxHeight = i;
				}
			}
			List<OneEndpointEdge> adj = array.get(maxHeight);
			int i = 0;
			while (i < adj.size()) {
				OneEndpointEdge edge = adj.get(i);
				if (edge.remainingCapacity() > 0 &&
						heights[maxHeight] == (heights[edge.endVertex] + 1)) {
					int delta = Math.min(excesses[maxHeight], edge.remainingCapacity());
					edge.augment(delta);
					array.get(edge.endVertex).get(edge.reverseEdgeIndex).decrement(delta);
					break;
				} else {
					i++;
				}
			}
			if (i == adj.size()) {
				//relabel
				heights[maxHeight]++;
			}
			if (earlyStop) break;
			computeExcesses();
		}
		//Compute max flow
		List<OneEndpointEdge> adj = array.get(s);
		for (int i = 0; i < adj.size(); i++) {
			maxFlow += adj.get(i).flow;
		}
		return maxFlow;
	}
	
	protected void initialize(int s, int t) {
		this.s = s;
		this.t = t;
		this.heights = new int[numVertices];
		this.excesses = new int[numVertices];
		Arrays.fill(heights, 0);
		heights[s] = numVertices;
		List<OneEndpointEdge> adj = array.get(s);
		OneEndpointEdge edge;
		for (int i = 0; i < adj.size(); i++) {
			//Saturate all edges going out
			edge = adj.get(i);
			edge.augment(edge.capacity);
			array.get(edge.endVertex).get(edge.reverseEdgeIndex).decrement(edge.capacity);
		}
	}
	
	protected void computeExcesses() {
		for (int i = 0; i < numVertices; i++) {
			List<OneEndpointEdge> adj = array.get(i);
			int flow_in = 0;
			int flow_out = 0;
			for (OneEndpointEdge edge: adj) {
				if (edge.capacity > 0) {
					flow_out += edge.flow;
				} else {
					flow_in += edge.remainingCapacity();
				}
			}
			excesses[i] = flow_in - flow_out;
		}
	}
	
	protected boolean thereIsExcess() {
		int i = 0;
		while (i < numVertices && excesses[i] == 0) {
			i++;
		}
		return i < numVertices;
	}
	
	/**
	 * Precondition: n > 0
	 * 
	 * @param n the integer
	 * @return the biggest power of 2 that is smaller or equal than n
	 */
	public static int biggest2PowSmallerThan(int n) {
		if ((n & n - 1) == 0)
			return n;
		int ret = 1;
		while (n > 1) {
			n >>= 1;
			ret <<= 1;
		}
		return ret;
	}
}
