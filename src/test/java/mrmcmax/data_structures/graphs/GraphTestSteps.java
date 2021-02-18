package mrmcmax.data_structures.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GraphTestSteps {
	
	private static final String ENDPOINT_DELIMITER = ",";
	private static final String EDGE_SEPARATOR = " ";
	
	protected int vertices;
	
	protected TwoEndpointEdge[] edges;
	
	protected Graph graph;
	
	@Given("a set of {int} vertices")
	public void aSetOfVertices(Integer int1) {
		vertices = int1;
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
		graph = new Graph(vertices);
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
}

