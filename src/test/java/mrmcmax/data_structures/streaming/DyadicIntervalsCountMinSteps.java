package mrmcmax.data_structures.streaming;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DyadicIntervalsCountMinSteps {
	
	//Shared between all test classes
	CountMin cm;
		
	public DyadicIntervalsCountMinSteps(CountMin cm) {
		this.cm = cm;
	}
	
	@Then("There are {int} CountMins in the data structure")
	public void thereAreCountMinsInTheDataStructure(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("Each CountMin object has {int} hash functions")
	public void eachCountMinObjectHasHashFunctions(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("Each CountMin object has a table of {int} x {int} counters")
	public void eachCountMinObjectHasATableOfXCounters(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("The next elements in the stream are {int} and {int}")
	public void theNextElementsInTheStreamAreAnd(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("The current state of the Heavy Hitters data structure")
	public void theCurrentStateOfTheHeavyHittersDataStructure() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("The next element is analysed")
	public void theNextElementIsAnalysed() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("The counters increase by two in each row of all CountMins but the last")
	public void theCountersIncreaseByTwoInEachRowOfAllCountMinsButTheLast() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("In the last CountMin, two different values increase by {int}")
	public void inTheLastCountMinTwoDifferentValuesIncreaseBy(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("We query with k equal to {int}")
	public void weQueryWithKEqualTo(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("We retrieve {int} and {int}")
	public void weRetrieveAnd(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
}
