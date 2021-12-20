package mrmcmax.data_structures.streaming;

public interface CountMinSketch {

	/**
	 * Processes an element of the stream
	 * @param element
	 */
	void accept(long element);

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

}