package mrmcmax.data_structures.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mrmcmax.TestUtils;

public class DinicTests {

	ResidualGraphList g;
	int s, t;
	int flow;

	@Given("a graph with {int} vertices")
	public void aGraphWithVertices(Integer v) {
		g = new ResidualGraphList(v);
	}

	@Given("the source is {int} and the sink is {int}")
	public void theSourceIsAndTheSinkIs(Integer s, Integer t) {
		this.s = s;
		this.t = t;
	}

	@Given("the capacity edges {string}")
	public void theCapacityEdges(String raw) {
		Iterator<List<Integer>> it = TestUtils.level1Separation(raw);
		while (it.hasNext()) {
			List<Integer> edge = it.next();
			g.addEdge(edge.get(0), edge.get(1), edge.get(2));
		}
	}

	@When("Dinics algorithm is run")
	public void dinicsAlgorithmIsRun() {
		flow = g.DinicAlgorithm(s, t);
	}

	@Then("the maximum flow is {int}")
	public void theMaximumFlowIs(Integer maxFlow) {
		assertEquals(flow, maxFlow);
	}

	@Then("the flows from {int} are {string}")
	public void theFlowsFromAre(Integer vertex, String raw) {
		Iterator<List<Integer>> it = TestUtils.level1Separation(raw);
		if (it.hasNext()) {
			List<Integer> expected = it.next();
			List<Integer> actual = g.getAdjacencyList(vertex).stream()
					.filter((edge) -> edge.capacity > 0) // To filter  reverse edges
					.map((edge) -> edge.flow).collect(Collectors.toList());
			assertIterableEquals(expected, actual);
		}
	}
}
