package mrmcmax.data_structures.graphs.dijkstra;

import java.util.Iterator;

public interface Dijkstra {
	
	void computeDijkstra(int s);
	
	int getDistance(int s);
	
	int getParent(int s);
	
	Iterator<Integer> getDistances();
	
	Iterator<Integer> getParents();
}
