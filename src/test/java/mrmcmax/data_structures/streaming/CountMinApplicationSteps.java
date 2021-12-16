package mrmcmax.data_structures.streaming;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CountMinApplicationSteps {
	
	//Shared between all test classes
	CountMin cm;
		
	public CountMinApplicationSteps(CountMin cm) {
		this.cm = cm;
	}

	@When("We have the stream {int}, {int}, {int}, {int}, {int}, {int}, {int}, {int}, {int}")
	public void weHaveTheStream(Integer int1, Integer int2, Integer int3, Integer int4, Integer int5, Integer int6, Integer int7, Integer int8, Integer int9) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("We query the heavy hitters")
	public void weQueryTheHeavyHitters() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("We get {int} in the result")
	public void weGetInTheResult(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("We have the stream {int}, {int}, {int}, {int}, {int}, {int}, {int}, {int}, {int}, {int}")
	public void weHaveTheStream(Integer int1, Integer int2, Integer int3, Integer int4, Integer int5, Integer int6, Integer int7, Integer int8, Integer int9, Integer int10) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("We query the range [{int}, {int}]")
	public void weQueryTheRange(Integer int1, Integer int2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("We get at least {int} in the range")
	public void weGetAtLeastInTheRange(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
}
