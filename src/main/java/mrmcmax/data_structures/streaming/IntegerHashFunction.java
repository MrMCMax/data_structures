package mrmcmax.data_structures.streaming;

import java.util.function.Function;

public abstract class IntegerHashFunction {

	protected long u;
	protected long m;
	
	/**
	 * Represents a hash function from [universeSize] to [rangeSize],
	 * where [m] = {0, 1, ..., m-1}.
	 * @param universeSize The size of the universe
	 * @param rangeSize The size of the range
	 */
	public IntegerHashFunction(long universeSize, long rangeSize) {
		this.u = universeSize;
		this.m = rangeSize;
	}
	
	public abstract class HashFunctionInstance {
		public abstract long hash(long element);
	};
	
	/**
	 * Returns a random variable h(x): [universeSize] -> [rangeSize]
	 */
	public abstract HashFunctionInstance getInstance();
}
