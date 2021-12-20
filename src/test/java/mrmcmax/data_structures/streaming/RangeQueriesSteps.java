package mrmcmax.data_structures.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mrmcmax.TestUtils;

public class RangeQueriesSteps {

	// Shared between all test classes
	DyadicIntervalsCountMin dy;

	long start, end;
	long[] splittingNode;

	public RangeQueriesSteps(DyadicIntervalsCountMin dy) {
		this.dy = dy;
	}

	@When("We query the range [{long}, {long}]")
	public void weQueryTheRange(Long int1, Long int2) {
		start = int1;
		end = int2;
	}

	@Then("The splitting node that is returned has ID {long} and level {int}")
	public void theSplittingNodeThatIsReturnedHasIDAndLevel(Long int1, Integer int2) {
		splittingNode = dy.findSplittingNode(start, end);
		assertEquals(splittingNode[0], int1,
				"Expected ID of splitting node to be " + int1 + " but was " + splittingNode[0]);
		assertEquals((int) splittingNode[1], int2,
				"Expected level of splitting node to be " + int2 + " but was " + splittingNode[1]);
	}

	@When("We have the stream {string}")
	public void weHaveTheStream(String string) {
		String[] items = TestUtils.level0Separation(string);
		for (int i = 0; i < items.length; i++) {
			dy.accept(Integer.parseInt(items[i]));
		}
	}

	@Then("The predecessor branch returns at least {long}")
	public void thePredecessorBranchReturnsAtLeast(Long int1) {
		long sum = dy.predecessorBranch(splittingNode[0], (int) splittingNode[1], start, end);
		assertTrue(sum >= int1, "Expected predecessor sum " + int1 + " but found " + sum);
		System.out.println("Actual predecessor sum: " + sum + ", compared to the real one: " + int1);
	}

	@Then("The successor branch returns at least {long}")
	public void theSuccessorBranchReturnsAtLeast(Long int1) {
		// Write code here that turns the phrase above into concrete actions
		long sum = dy.successorBranch(splittingNode[0], (int) splittingNode[1], start, end);
		assertTrue(sum >= int1, "Expected successor sum " + int1 + " but found " + sum);
		System.out.println("Actual successor sum: " + sum + ", compared to the real one: " + int1);
	}

	@Then("The final result is at least {long}")
	public void theFinalResultIsAtLeast(Long int1) {
		long sum = dy.rangeQuery(start, end);
		assertTrue(sum >= int1, "Expected total sum " + int1 + " but found " + sum);
		System.out.println("Actual sum: " + sum + ", compared to the real one: " + int1);
	}
}
