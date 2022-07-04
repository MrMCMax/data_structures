package mrmcmax.data_structures.examples;

import java.io.IOException;

import mrmcmax.data_structures.graphs.UndirectedGraph;
import mrmcmax.persistence.TikZReader;

public class PersistenceTest {

	public static void main(String[] args) throws IOException {
		TikZReader reader = new TikZReader();
		UndirectedGraph g = (UndirectedGraph) reader.loadGraphAbsolute("/home/max/Documents/TikZit/augPaths/AK_Nodes.tikz");
		for (int i = 0; i < g.m(); i++) {
			System.out.println("Adjacency list for vertex " + i + ":");
			System.out.println(g.getAdjacencyList(i));
		}
	}
}
