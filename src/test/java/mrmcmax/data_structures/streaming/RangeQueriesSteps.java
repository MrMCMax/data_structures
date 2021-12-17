package mrmcmax.data_structures.streaming;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RangeQueriesSteps {
	
	//Shared between all test classes
	DyadicIntervalsCountMin dy;
		
	public RangeQueriesSteps(DyadicIntervalsCountMin dy) {
		this.dy = dy;
	}
	
	@When("We query the range [{int}, {int}]")
	public void weQueryTheRange(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("The splitting node that is returned has ID {int} and level {int}")
	public void theSplittingNodeThatIsReturnedHasIDAndLevel(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("We have the stream {string}")
	public void weHaveTheStream(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
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
