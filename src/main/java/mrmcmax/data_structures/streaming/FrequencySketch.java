package mrmcmax.data_structures.streaming;

public interface FrequencySketch {

	/**
	 * Processes an element of the stream
	 * @param element
	 */
	void update(long element);

	/**
	 * Returns the estimated frequency of an element.
	 * @param element the element to estimate the frequency of
	 * @return the estimated frequency.
	 */
	long queryFrequency(long element);

	/**
	 * Returns the recorded m.
	 */
	long m();
	
	/**
	 * Computes m by adding the values of one array. For debugging purposes.
	 * @return
	 */
	long computeMWithRow();
	
	/**
	 * The number of queries made till now.
	 * @return The number of queries performed on this CountMinSketch
	 */
	long nQueries();

}