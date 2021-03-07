package mrmcmax.data_structures.linear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LinkedCacheListSteps {
	
	LinkedCacheList list;
	
	boolean indexOutOfBounds;
	
	@Given("a LinkedCacheList of cache size {int}")
	public void aLinkedCacheListOfCacheSize(Integer cache) {
		list = new LinkedCacheList(cache);
	}
	
	@When("{int} numbers are added")
	public void numbersAreAdded(Integer n) {
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
	}
	
	@Then("we can retrieve them correctly")
	public void weCanRetrieveThemCorrectly() {
		for (int i = 0; i < 129; i++) {
			try {
				assertEquals(i, list.get(i));
			} catch (IndexOutOfBoundsException e) {
				fail(e.getMessage());
			}
		}
	}
	
	@Then("the size is {int}")
	public void theSizeIs(Integer size) {
		assertEquals(size, list.size());
	}
	
	@When("the position index {int} is seeked")
	public void thePositionIndexIsSeeked(Integer index) {
		try {
			list.get(index);
			indexOutOfBounds = false;
		} catch (IndexOutOfBoundsException e) {
			indexOutOfBounds = true;
		}
	}

	@Then("an IndexOutOfBounds error is thrown")
	public void anIndexOutOfBoundsErrorIsThrown() {
		assertTrue(indexOutOfBounds);
	}
}

