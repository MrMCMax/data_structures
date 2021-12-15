package mrmcmax.data_structures.streaming;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CountMinSteps {
	
	//Shared between all test classes
	CountMin cm;
	
	
	public CountMinSteps(CountMin cm) {
		this.cm = cm;
	}
	
	@Given("The no. of functions is {int}")
	public void theNoOfFunctionsIs(Integer int1) {
		System.out.println(cm);
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("the Universe n is two to the power of {int}")
	public void theUniverseNIsTwoToThePowerOf(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("The Range m is two to the power of {int}")
	public void theRangeMIsTwoToThePowerOf(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("The CountMin is created")
	public void theCountMinIsCreated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("The CountMin object has chosen {int} hash functions")
	public void theCountMinObjectHasChosenHashFunctions(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("The CountMin object has a table of {int} x {int} counters")
	public void theCountMinObjectHasATableOfXCounters(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("The next element in the stream is {int}")
	public void theNextElementInTheStreamIs(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("The current state of the CountMin")
	public void theCurrentStateOfTheCountMin() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("The CountMin hashes the next element")
	public void theCountMinHashesTheNextElement() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("The counters increase by one in each row of the CountMin")
	public void theCountersIncreaseByOneInEachRowOfTheCountMin() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("We query the element {int}")
	public void weQueryTheElement(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("The CountMin does not undercount and returns at least {int}")
	public void theCountMinDoesNotUndercountAndReturnsAtLeast(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

}
