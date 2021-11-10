package mrmcmax.data_structures.examples;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import mrmcmax.data_structures.graphs.DirectedGraph;
import mrmcmax.data_structures.graphs.UndirectedGraph;
import mrmcmax.data_structures.graphs.dijkstra.BinaryHeapDijkstra;
import mrmcmax.data_structures.graphs.dijkstra.Dijkstra;

public class ChinesePostmanExample {
	
	public static void main(String[] args) {
		DirectedGraph g = new UndirectedGraph(14);
		g.addEdge(0, 1, 1);
		g.addEdge(0, 4, 7);
		g.addEdge(0, 3, 2);
		g.addEdge(1, 2, 4);
		g.addEdge(1, 4, 1);
		g.addEdge(1, 5, 1);
		g.addEdge(2, 13,8);
		g.addEdge(2, 5, 5);
		g.addEdge(3, 4, 5);
		g.addEdge(3, 7,3);
		g.addEdge(3, 6,1);
		g.addEdge(4, 7,1);
		g.addEdge(4, 5, 3);
		g.addEdge(4, 8, 1);
		g.addEdge(5, 8, 1);
		g.addEdge(5, 13, 3);
		g.addEdge(5, 12, 2);
		g.addEdge(13, 12, 1);
		g.addEdge(6, 7, 1);
		g.addEdge(6, 9, 2);
		g.addEdge(7, 8, 1);
		g.addEdge(7, 9, 1);
		g.addEdge(7, 10, 1);
		g.addEdge(8, 12, 9);
		g.addEdge(8, 10, 8);
		g.addEdge(8, 11, 2);
		g.addEdge(12, 11, 4);
		g.addEdge(9, 10, 7);
		g.addEdge(10, 11, 7);
		
		System.out.println("Odd degree vertices: ");
		List<Integer> oddDegreeVertices = g.getOddDegreeVertices();
		System.out.println(oddDegreeVertices);
		int[] vertices = new int[g.getNumVertices()];
		for (int i = 0; i < vertices.length; i++)
			vertices[i] = i;
		for (Integer source : oddDegreeVertices) {
			System.out.println("Dijkstra from " + source + ":");
			Dijkstra d = new BinaryHeapDijkstra();
			d.computeDijkstra(g, source);
			Iterator<Integer> distances = d.getDistances();
			Iterator<Integer> parents = d.getParents();
			System.out.println("Distances: ");
			System.out.println(Arrays.toString(vertices));
			System.out.print("[");
			System.out.print(distances.next());
			while (distances.hasNext()) {
				System.out.print(", " + distances.next());
			}
			System.out.println("]");
			System.out.println("Parents: ");
			System.out.println(Arrays.toString(vertices));
			System.out.print("[");
			System.out.print(parents.next());
			while (parents.hasNext()) {
				System.out.print(", " + parents.next());
			}
			System.out.println("]");
		}
		
	}
	
}
