package mrmcmax.data_structures.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mrmcmax.data_structures.graphs.dijkstra.Dijkstra;

public class GraphTestSteps {
	
	private static final String ENDPOINT_DELIMITER = ", ";
	private static final String EDGE_SEPARATOR = " ";
	
	protected int vertices;
	
	protected TwoEndpointEdge[] edges;
	
	protected Graph graph;
	
	protected int[] distances;
	protected int[] parents;
	
	protected Dijkstra dijkstra;
	
	@Given("a set of {int} vertices")
	public void aSetOfVertices(Integer int1) {
		vertices = int1;
		graph = new Graph(vertices);
	}
	
	@Given("the edges {string}")
	public void theEdges(String raw) {
		String[] endpoints = raw.split(ENDPOINT_DELIMITER);
		edges = new TwoEndpointEdge[endpoints.length];
		for (int i = 0; i < edges.length; i++) {
			String[] edge = endpoints[i].split(EDGE_SEPARATOR);
			edges[i] = new TwoEndpointEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
		}
	}
	
	@When("the graph is built")
	public void theGraphIsBuilt() {
		for (int i = 0; i < edges.length; i++) {
			graph.addEdge(edges[i].u, edges[i].v);
		}
	}
	
	@Then("there exists an edge between {int} and {int}")
	public void thereExistsAnEdgeBetweenAnd(Integer int1, Integer int2) {
		assertTrue(graph.existsEdge(int1, int2), "Expected at least one edge between vertices" +
	    		int1 + " and " + int2 + ", but found none");
	}
	
	@Then("there does not exist an edge between {int} and {int}")
	public void thereDoesNotExistAnEdgeBetweenAnd(Integer int1, Integer int2) {
	    assertFalse(graph.existsEdge(int1, int2), "Expected no edge between vertices" +
	    		int1 + " and " + int2 + ", but found at least one");
	}
	
	@Then("there exist {int} edges between {int} and {int}")
	public void thereExistEdgesBetweenAnd(Integer count, Integer u, Integer v) {
		int actualCount = graph.edgeCount(u, v);
	    assertEquals(count, actualCount, "Expected " + count + " edges between vertices "
	    		+ u + " and " + v + ", but found " + actualCount);
	}
	
	/* DIJKSTRA'S ALGORITHM */
	
	@Given("the weighted edges {string}")
	public void theWeightedEdges(String raw) {
		String[] endpoints = raw.split(ENDPOINT_DELIMITER);
		for (int i = 0; i < endpoints.length; i++) {
			String[] edge = endpoints[i].split(EDGE_SEPARATOR);
			graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Integer.parseInt(edge[2]));
		}
	}

	@When("Dijkstras algorithm is run")
	public void dijkstrasAlgorithmIsRun() {
		graph.dijkstra(0);
		distances = graph.getDistances();
		parents = graph.getParents();
	}

	@Then("the distances are {string}")
	public void theDistancesAre(String raw) {
	    String[] expectedDistances = raw.split(" ");
	    for (int i = 0; i < expectedDistances.length; i++) {
	    	assertEquals(distances[i], Integer.parseInt(expectedDistances[i]), "Distances differ in Dijkstra's algorithm");
	    }
	}
	
	@Then("the parents are {string}")
	public void theParentsAre(String raw) {
	    String[] expectedParents = raw.split(" ");
	    for (int i = 0; i < expectedParents.length; i++) {
	    	assertEquals(parents[i], Integer.parseInt(expectedParents[i]), "Parent " + i + " differs in Dijkstra's algorithm");
	    }
	}
	
	/** DIJKSTRA INTERFACE */
	
	@When("Dijkstras algorithm is run with the basic interface")
	public void dijkstrasAlgorithmIsRunWithTheBasicInterface() {
		dijkstra = graph.dijkstraInterface(0);
	}

	@Then("the retrieved distances are {string}")
	public void theRetrievedDistancesAre(String raw) {
	    String[] expectedDistances = raw.split(" ");
	    Iterator<Integer> distances = dijkstra.getDistances();
	    for (int i = 0; i < expectedDistances.length; i++) {
	    	assertEquals(distances.next(), Integer.parseInt(expectedDistances[i]), "Distances differ in Dijkstra's algorithm");
	    }
	}

	@Then("the retrieved parents are {string}")
	public void theRetrievedParentsAre(String raw) {
	    String[] expectedParents = raw.split(" ");
	    Iterator<Integer> parents = dijkstra.getParents();
	    for (int i = 0; i < expectedParents.length; i++) {
	    	assertEquals(parents.next(), Integer.parseInt(expectedParents[i]), "Parent " + i + " differs in Dijkstra's algorithm");
	    }
	}
	
	@When("Dijkstras algorithm is run with the binary heap interface")
	public void dijkstrasAlgorithmIsRunWithTheBinaryHeapInterface() {
		dijkstra = graph.dijkstraBinaryHeapInterface(0);
	}

}

