package mrmcmax.data_structures.graphs.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import mrmcmax.data_structures.graphs.Graph;
import mrmcmax.data_structures.graphs.OneEndpointEdge;
import mrmcmax.data_structures.trees.BinaryPlungeHeap;
import mrmcmax.data_structures.trees.PlungePriorityQueue;
import static mrmcmax.data_structures.trees.PlungePriorityQueue.Entry;

public class BinaryHeapDijkstra implements Dijkstra {
	
	/** Keys: distances, Values: vertices */
	private PlungePriorityQueue<Integer> distances;
	private List<Integer> shortestPathParents;
	private List<Integer> shortestDistances;
	
	public BinaryHeapDijkstra() {}
	
	private void createDataStructure(Graph g) {
		distances = new BinaryPlungeHeap<Integer>(g.getNumVertices());
		shortestPathParents = new ArrayList<Integer>(Collections.nCopies(g.getNumVertices(), -1));
		shortestDistances = new ArrayList<Integer>(Collections.nCopies(g.getNumVertices(), -1));
	}

	@Override
	public void computeDijkstra(Graph g, int s) {
		createDataStructure(g);
		shortestPathParents.set(s, s);
		shortestDistances.set(s, 0);
		distances.add(0, s);
		int startVertex = 0;
		int distance = 0;
		do {
			/* Retrieve the minimum by searching the minimum in the distance tree */
			Entry<Integer> smallestEntry = null;
			try {
				smallestEntry = distances.retrieveMin();
				startVertex = smallestEntry.getValue();
				distance = smallestEntry.getKey();
				shortestDistances.set(startVertex, distance);
			} catch (Exception e) {
				System.out.println(e);
			}
			/* Dijkstra */
			List<OneEndpointEdge> adj = g.getAdjacencyList(startVertex);
			for (int i = 0; i < adj.size(); i++) {
				OneEndpointEdge edge = adj.get(i);
				int endVertex = edge.endVertex;
				if (shortestDistances.get(endVertex) == -1) {
					Integer endVertexDistance = distances.keyOf(endVertex);
					if (endVertexDistance == null) {
						distances.add(edge.capacity + distance, endVertex);
						
					} else if (endVertexDistance > edge.capacity + distance) {
						distances.plunge(endVertex, edge.capacity + distance);
						//distances.put(endVertex, edge.capacity + distance);
					}
					shortestPathParents.set(endVertex, startVertex);
				}
			}
		} while (!distances.isEmpty());
		shortestPathParents.set(s, -1);
	}

	@Override
	public int getDistance(int s) {
		return shortestDistances.get(s);
	}

	@Override
	public int getParent(int s) {
		return shortestPathParents.get(s);
	}

	@Override
	public Iterator<Integer> getDistances() {
		return shortestDistances.iterator();
	}

	@Override
	public Iterator<Integer> getParents() {
		return shortestPathParents.iterator();
	}
	
	//Example of use
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new Graph(7);
		g.addEdge(0, 1);
		//....
		Dijkstra dij = new BinaryHeapDijkstra();
		dij.computeDijkstra(g, 0);
		Iterator<Integer> distances = dij.getDistances();
		Iterator<Integer> parents = dij.getParents();
	}
}
