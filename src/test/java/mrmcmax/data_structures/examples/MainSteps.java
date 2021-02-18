package mrmcmax.data_structures.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MainSteps {
	
	private final static String pathToResources = "./src/main/resources/mrmcmax/data_structures/examples/";
	
	String fileName;
	String result;
	
	@Given("the output from {string}")
	public void theOutputFrom(String fileName) {
		this.fileName = fileName;
	}
	
	@When("the script is run")
	public void theScriptIsRun() {
		try {
			
			result = MainProgrammingCompetition.startWithFile(pathToResources + fileName);
		} catch (IOException e) {
			fail("Exception was thrown: " + e);
		}
	}
	
	@Then("the output is {int} {int}")
	public void theOutputIs(Integer U, Integer M) {
		assertEquals(U + " " + M, result);
	}
	
	//TIMING STEPS
	
	@When("the script is run {int} times")
	public void theScriptIsRunTimes(Integer int1) {
		try {
			MainProgrammingCompetition.timeSolution(pathToResources + fileName);
		} catch (IOException e) {
			fail("Exception was thrown: " + e);
		}
	}
	
	@Then("the statistics for its runtime are printed")
	public void theStatisticsForItsRuntimeArePrinted() {
		
	}
}

