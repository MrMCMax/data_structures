package mrmcmax.data_structures.streaming;

import static mrmcmax.TestUtils.failIfException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class DyadicIntervalsCountMinSteps {
	
	DyadicIntervalsCountMin dy;
	
	long u;
	int w, d, u_exp, w_exp;
	
	long test_interval_size;
	int test_level;
	
	int e1, e2;
	
	@Given("Dyadic Intervals for the problem u=two to the {int}, d={int}, range=two to the {int}")
	public void dyadicIntervalsForTheProblemUTwoToTheDRangeTwoToThe(Integer u_exp, Integer d, Integer w_exp) {
		u = 1L<<u_exp;
		this.w = 1<<w_exp;
		this.u_exp = u_exp;
		this.w_exp = w_exp;
		this.d = d;
	}

	@When("The Dyadic Intervals data structure is created")
	public void theDyadicIntervalsDataStructureIsCreated() {
		dy = new DyadicIntervalsCountMin(u, w, d);
	}
	
	@Then("There are {int} CountMins in the data structure")
	public void thereAreCountMinsInTheDataStructure(Integer levels) {
		assertTrue(dy.cmins.length == levels);
	}

	@Given("an interval size m={long} at level {int}")
	public void anIntervalSizeM(Long int1, Integer level) {
		test_interval_size = int1;
		test_level = level;
	}

	@Given("an universe n=two to the power of {int}")
	public void anUniverseNTwoToThePowerOf(Integer u_exp) {
		this.u = 1L<<u_exp;
		this.u_exp = u_exp;
		this.dy = new DyadicIntervalsCountMin(u, 4, 1);	
	}

	@When("we want to know the identifier of all the elements")
	public void weWantToKnowTheIdentifierOfAllTheElements() {
		failIfException(() -> {
			long currentID = 0;
			long repeatedValue = 0;
			for (long i = 0; i < u; i++) {
				long id = dy.identifier(test_level, i);
				if (repeatedValue < test_interval_size) {
					assertEquals(currentID, id);
					repeatedValue++;
				} else {
					currentID += test_interval_size;
					assertTrue(currentID == id, "Expected " + currentID + " but found " + id);
					repeatedValue = 1;
				}
			}
		});
	}

	@Then("the identifier only changes every m={int} elements")
	public void theIdentifierOnlyChangesEveryMElements(Integer int1) {
		//Tested above by not throwing exceptions
	}

	@Given("The next elements in the stream are {int} and {int}")
	public void theNextElementsInTheStreamAreAnd(Integer int1, Integer int2) {
		e1 = int1;
		e2 = int2;
	}

	@Given("The current state of the Heavy Hitters data structure")
	public void theCurrentStateOfTheHeavyHittersDataStructure() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("The next two elements are analysed")
	public void theNextTwoElementsAreAnalysed() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("All the countmins have two more elements")
	public void allTheCountminsHaveTwoMoreElements() {
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

	@Then("There are no more than {int} queries in each of the CountMins")
	public void thereAreNoMoreThanQueriesInEachOfTheCountMins(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
}
