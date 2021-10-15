package mrmcmax.data_structures.graphs.dijkstra;

import java.util.Iterator;

import mrmcmax.data_structures.graphs.Graph;

public interface Dijkstra {
	
	void computeDijkstra(Graph g, int s);
	
	int getDistance(int s);
	
	int getParent(int s);
	
	Iterator<Integer> getDistances();
	
	Iterator<Integer> getParents();
}
