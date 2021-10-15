package mrmcmax.data_structures.graphs.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mrmcmax.data_structures.graphs.Graph;
import mrmcmax.data_structures.graphs.OneEndpointEdge;

public class BasicDijkstra implements Dijkstra {
	
	private List<Integer> distances;
	private List<Integer> parents;
	
	private void createDataStructures(Graph g) {
		distances = new ArrayList<Integer>(Collections.nCopies(g.getNumVertices(), Integer.MAX_VALUE));
		parents = new ArrayList<Integer>(Collections.nCopies(g.getNumVertices(), -1));
	}

	public void computeDijkstra(Graph g, int s) {
		createDataStructures(g);
		distances.set(s, 0);
		Set<Integer> S = new HashSet<>();
		Set<Integer> V = new HashSet<>();
		for (int i = 0; i < g.getNumVertices(); i++) {
			V.add(i);
		}
		do {
			/* Retrieve the minimum by iterating through all the vertices in V*/
			Iterator<Integer> it = V.iterator();
			int startVertex = it.next();
			while (it.hasNext()) {
				int element = it.next();
				if (distances.get(element) < distances.get(startVertex)) {
					startVertex = element;
				}
			}
			S.add(startVertex);
			V.remove(startVertex);
			/* Dijkstra */
			List<OneEndpointEdge> adj = g.getAdjacencyList(startVertex);
			for (int i = 0; i < adj.size(); i++) {
				OneEndpointEdge edge = adj.get(i);
				int endVertex = edge.endVertex;
				if (!S.contains(endVertex)) {
					if (distances.get(endVertex) > edge.capacity + distances.get(startVertex)) {
						distances.set(endVertex, edge.capacity + distances.get(startVertex));
						parents.set(endVertex, startVertex);
					}
				}
			}
		} while (!V.isEmpty());
	}

	@Override
	public int getDistance(int s) {
		return distances.get(s);
	}

	@Override
	public int getParent(int s) {
		return parents.get(s);
	}

	@Override
	public Iterator<Integer> getDistances() {
		return distances.iterator();
	}

	@Override
	public Iterator<Integer> getParents() {
		return parents.iterator();
	}
	
	//Example of use
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new Graph(7);
		g.addEdge(0, 1);
		//....
		Dijkstra dij = new BasicDijkstra();
		dij.computeDijkstra(g, 0);
		Iterator<Integer> distances = dij.getDistances();
		Iterator<Integer> parents = dij.getParents();
	}
}
