package mrmcmax.data_structures.trees;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BinaryPlungeHeapSteps {

	private static final String SEQUENCE_DELIMITER = ",";
	private static final String ITEM_DELIMITER = " ";

	protected PlungePriorityQueue<Integer> heap;

	@Given("a binary plunge heap of size {int}")
	public void aBinaryPlungeHeapOfSize(Integer size) {
		heap = new BinaryPlungeHeap<Integer>(size, 0);
	}

	@When("we add the values {string}")
	public void weAddTheValues(String raw) {
		String[] entries = raw.split(SEQUENCE_DELIMITER);
		for (int i = 0; i < entries.length; i++) {
			String[] values = entries[i].split(ITEM_DELIMITER);
			heap.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		}
	}

	@Then("the values retrieved are in the order {string}")
	public void theValuesRetrievedAreInTheOrder(String raw) {
		String[] expectedValues = raw.split(SEQUENCE_DELIMITER);
		try {
			for (int i = 0; i < expectedValues.length; i++) {
				PlungePriorityQueue.Entry<Integer> entry = heap.retrieveMin();
				assertEquals(i+1, entry.getKey(), "Order was not correct");
				assertEquals(Integer.parseInt(expectedValues[i]), entry.getValue(), "Value was not correct");
			}
		} catch (Exception e) {
			throw e;
			//fail(e.getMessage());
		}
	}
}
