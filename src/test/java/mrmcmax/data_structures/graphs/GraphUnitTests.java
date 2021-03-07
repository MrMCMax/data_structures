package mrmcmax.data_structures.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GraphUnitTests {
	
	Set<Integer> set;
	int minimum;
	
	@Given("a set of integers")
	public void aSetOfIntegers() {
		set = new HashSet<>();
	}
	
	@When("the elements {int}, {int}, {int}, {int}, {int}, {int} are added")
	public void theElementsAreAdded(Integer int1, Integer int2, Integer int3, Integer int4, Integer int5, Integer int6) {
		set.add(int1);
		set.add(int2);
		set.add(int3);
		set.add(int4);
		set.add(int5);
		set.add(int6);
	}
	
	@When("the minimum is retrieved")
	public void theMinimumIsRetrieved() {
		minimum = Graph.minSet(set);
	}
	
	@Then("the minimum is {int}")
	public void theMinimumIs(Integer int1) {
		assertEquals(minimum, int1, "Testing the minimum of a set");
	}
}

