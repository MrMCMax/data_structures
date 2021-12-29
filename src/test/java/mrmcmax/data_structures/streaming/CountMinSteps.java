package mrmcmax.data_structures.streaming;

import static mrmcmax.TestUtils.failIfException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CountMinSteps {
	
	//Shared between all test classes bc it is passed in the constructors
	CountMin cmin;
	
	//blah
	long u;
	int w, d;
	
	int nextElement;
	long[][] currentCounters;
	long resultQuery;
	
	public CountMinSteps(CountMin cm) {
		this.cmin = cm;
	}
	
	@Given("The no. of functions is {int}")
	public void theNoOfFunctionsIs(Integer int1) {
		this.d = int1;
	}

	@Given("the Universe n is two to the power of {long}")
	public void theUniverseNIsTwoToThePowerOf(Long int1) {
		this.u = 1L << int1; //Important to specify that 1 is a long
	}

	@Given("The Range m is two to the power of {int}")
	public void theRangeMIsTwoToThePowerOf(Integer int1) {
		this.w = 1 << int1;
	}

	@When("The CountMin is created")
	public void theCountMinIsCreated() {
		failIfException(() -> cmin.initialize(this.u, this.w, this.d));
		assertTrue((long) cmin.u == 4294967296L, "Expected value "+ 4294967296L + " but found " + cmin.u);
		assertTrue(cmin.w == 64);
		assertTrue(cmin.d == 4);
	}

	@Then("The CountMin object has chosen {int} hash functions")
	public void theCountMinObjectHasChosenHashFunctions(Integer int1) {
		failIfException(() -> {
			for (int i = 0; i < d; i++) {
				assertNotNull(cmin.hashes[i]); //Upon creation, they should work (refer to hash function tests)
			}
		});
	}

	@Then("The CountMin object has a table of {int} x {int} counters")
	public void theCountMinObjectHasATableOfXCounters(Integer d, Integer w) {
		assertTrue(cmin.counters.length == d);
		for (int i = 0; i < d; i++) {
			assertTrue(cmin.counters[i].length == w);
		}
	}

	@Given("The next element in the stream is {int}")
	public void theNextElementInTheStreamIs(Integer int1) {
		this.nextElement = int1;
	}

	@Given("The current state of the CountMin is known")
	public void theCurrentStateOfTheCountMinIsKnown() {
		currentCounters = new long[d][w];
		for (int i = 0; i < d; i++) {
			System.arraycopy(cmin.counters[i], 0, currentCounters[i], 0, w);
		}
	}

	@When("The CountMin hashes the next element")
	public void theCountMinHashesTheNextElement() {
		failIfException(() -> cmin.update(nextElement));
	}

	@Then("The counters increase by one in each row of the CountMin")
	public void theCountersIncreaseByOneInEachRowOfTheCountMin() {
		for (int i = 0; i < d; i++) {
			int sum = 0;
			for (int j = 0; j < w; j++) {
				sum += currentCounters[i][j];
			}
			int cminSum = 0;
			for (int j = 0; j < w; j++) {
				cminSum += cmin.counters[i][j];
			}
			assertTrue(cminSum == sum + 1);
		}
	}

	@When("We query the element {int}")
	public void weQueryTheElement(Integer int1) {
		failIfException(() -> { resultQuery = cmin.queryFrequency(int1); });
	}

	@Then("The CountMin does not undercount and returns at least {int}")
	public void theCountMinDoesNotUndercountAndReturnsAtLeast(Integer int1) {
		assertTrue(resultQuery >= int1);
		System.out.println("query of element " + int1 + ": estimated frequency " + resultQuery);
	}

}
