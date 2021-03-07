package mrmcmax.data_structures.examples;

import java.util.Arrays;
import java.util.List;

import mrmcmax.data_structures.graphs.Graph;

public class ChinesePostmanExample {
	
	public static void main(String[] args) {
		Graph g = new Graph(14);
		g.addUndirectedEdge(0, 1, 1);
		g.addUndirectedEdge(0, 4, 7);
		g.addUndirectedEdge(0, 3, 2);
		g.addUndirectedEdge(1, 2, 4);
		g.addUndirectedEdge(1, 4, 1);
		g.addUndirectedEdge(1, 5, 1);
		g.addUndirectedEdge(2, 13,8);
		g.addUndirectedEdge(2, 5, 5);
		g.addUndirectedEdge(3, 4, 5);
		g.addUndirectedEdge(3, 7,3);
		g.addUndirectedEdge(3, 6,1);
		g.addUndirectedEdge(4, 7,1);
		g.addUndirectedEdge(4, 5, 3);
		g.addUndirectedEdge(4, 8, 1);
		g.addUndirectedEdge(5, 8, 1);
		g.addUndirectedEdge(5, 13, 3);
		g.addUndirectedEdge(5, 12, 2);
		g.addUndirectedEdge(13, 12, 1);
		g.addUndirectedEdge(6, 7, 1);
		g.addUndirectedEdge(6, 9, 2);
		g.addUndirectedEdge(7, 8, 1);
		g.addUndirectedEdge(7, 9, 1);
		g.addUndirectedEdge(7, 10, 1);
		g.addUndirectedEdge(8, 12, 9);
		g.addUndirectedEdge(8, 10, 8);
		g.addUndirectedEdge(8, 11, 2);
		g.addUndirectedEdge(12, 11, 4);
		g.addUndirectedEdge(9, 10, 7);
		g.addUndirectedEdge(10, 11, 7);
		
		System.out.println("Odd degree vertices: ");
		List<Integer> oddDegreeVertices = g.getOddDegreeVertices();
		System.out.println(oddDegreeVertices);
		int[] vertices = new int[g.getNumVertices()];
		for (int i = 0; i < vertices.length; i++)
			vertices[i] = i;
		for (Integer source : oddDegreeVertices) {
			System.out.println("Dijkstra from " + source + ":");
			g.computeShortestPathsDijkstra(source);
			int[] distances = g.getDistances();
			int[] parents = g.getParents();
			System.out.println("Distances: ");
			System.out.println(Arrays.toString(vertices));
			System.out.println(Arrays.toString(distances));
			System.out.println("Parents: ");
			System.out.println(Arrays.toString(vertices));
			System.out.println(Arrays.toString(parents));
		}
		
	}
	
}
