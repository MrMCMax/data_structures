package mrmcmax.data_structures.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mrmcmax.TestUtils;

public class RangeQueriesSteps {

	// Shared between all test classes
	DyadicIntervalsCountMin dy;

	int start, end;

	public RangeQueriesSteps(DyadicIntervalsCountMin dy) {
		this.dy = dy;
	}

	@When("We query the range [{int}, {int}]")
	public void weQueryTheRange(Integer int1, Integer int2) {
		start = int1;
		end = int2;
	}

	@Then("The splitting node that is returned has ID {long} and level {int}")
	public void theSplittingNodeThatIsReturnedHasIDAndLevel(Long int1, Integer int2) {
		long[] splittingNode = dy.findSplittingNode(start, end);
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

	@Then("The predecessor branch returns at least {int}")
	public void thePredecessorBranchReturnsAtLeast(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("The successor branch returns at least {int}")
	public void theSuccessorBranchReturnsAtLeast(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("The final result is at least {int}")
	public void theFinalResultIsAtLeast(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}
}
