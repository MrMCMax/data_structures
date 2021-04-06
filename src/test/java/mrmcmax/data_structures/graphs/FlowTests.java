package mrmcmax.data_structures.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mrmcmax.TestUtils;
import mrmcmax.data_structures.examples.MainProgrammingCompetition;

public class FlowTests {

	ResidualGraphList g;
	int s, t;
	int flow;
	int n;

	@Given("a graph with {int} vertices")
	public void aGraphWithVertices(Integer v) {
		g = new ResidualGraphList(v);
		n = v;
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
	
	/* PUSH-RELABEL ALGORITHM */
	@When("Push-relabel algorithm is initialized")
	public void pushRelabelAlgorithmIsInitialized() {
		g.initialize(0, 7);
	}

	@Given("Push-relabel algorithm")
	public void pushRelabelAlgorithm() {
		MainProgrammingCompetition.setFlowAlgorithm(MainProgrammingCompetition.PUSH_RELABEL_1);
	}
	
	@Then("the height of s is {int} and all others are {int}")
	public void theHeightOfSIsAndAllOthersAre(Integer height, Integer others) {
		assertEquals(g.heights[s], height);
		for (int i = 1; i < n; i++) {
			assertEquals(g.heights[i], others);
		}
	}
	
	@When("push-relabel is initialized and does one iteration")
	public void pushRelabelIsInitializedAndDoesOneIteration() {
		g.PushRelabel1Algorithm(s, t, true);
	}

	@Then("the vertex {int} has excess {int}")
	public void theVertexHasExcess(Integer v, Integer ex) {
		assertEquals(ex, g.excesses[v]);
	}

	@Then("the vertex {int} increases its height to {int}")
	public void theVertexIncreasesItsHeightTo(Integer v, Integer h) {
		System.out.println(Arrays.toString(g.heights));
	    assertEquals(h, g.heights[v]);
	}
}
