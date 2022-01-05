package mrmcmax.data_structures.streaming;

import java.util.Arrays;

import mrmcmax.data_structures.streaming.IntegerHashFunction.HashFunctionInstance;

public class CountSketch implements FrequencySketch {
	protected int d; //Number of functions
	protected int w; //Range of the hash functions
	protected long u; //Universe size
	protected long m = 0; //Recorded number of elements
	
	protected IntegerHashFunction strongHashFunction;
	protected IntegerHashFunction signHashFunction;
	protected HashFunctionInstance[] hashFunctions;
	protected HashFunctionInstance[] signHashFunctions;
	
	protected long nQueries = 0; //For debugging purposes: Number of frequency queries performed till now
	
	protected long counters[][]; //data structure of counters!
	
	public CountSketch() {
		//dude
		initialize((long) 1<<32, 64, 8);
	}
	
	public CountSketch(long universeSize, double epsilon, double delta) {
		int w = (int) Math.ceil(3 / (epsilon*epsilon));
		int d = (int) Math.ceil(Math.log(1/delta) / Math.log(3));
		//Very improvable efficiency-wise but hey, let's leave it for another day
		initialize(universeSize, w, d);
	}
	
	public void initialize(long u, int w, int d) {
		//We will make sure that our hash function doesn't return hashes that
		//don't fit into a signed int
		this.u = u;
		this.w = w;
		this.d = d;
		strongHashFunction = new MultiplyShiftStrong32(w);
		signHashFunction = new SignHashFunction(u);
		hashFunctions = new HashFunctionInstance[d];
		signHashFunctions = new HashFunctionInstance[d];
		for (int i = 0; i < d; i++) {
			hashFunctions[i] = strongHashFunction.getInstance();
			signHashFunctions[i] = signHashFunction.getInstance();
		}
		counters = new long[d][w]; //Java initializes them to zeros
	}
	
	/**
	 * Processes an element of the stream
	 * @param element
	 */
	@Override
	public void update(long element) {
		int hash;
		int sign;
		for (int i = 0; i < d; i++) {
			hash = (int) hashFunctions[i].hash(element);
			sign = (int) signHashFunctions[i].hash(element);
			counters[i][hash] += sign;
		}
		m++;
	}
	
	/**
	 * Returns the estimated frequency of an element.
	 * @param element the element to estimate the frequency of
	 * @return the estimated frequency.
	 */
	@Override
	public long queryFrequency(long element) {
		long[] frequencies = new long[d];
		int hash, sign;
		for (int i = 0; i < d; i++) {
			hash = (int) hashFunctions[i].hash(element);
			sign = (int) signHashFunctions[i].hash(element);
			frequencies[i] = counters[i][hash] * sign;
		}
		//Median with sorting cuz any other thing is wasting time rn
		Arrays.sort(frequencies);
		long ret = 0;
		int half = (int)(d/2);
		if (d % 2 == 0) {
			ret = frequencies[half+1];
		} else {
			ret += (frequencies[half] + frequencies[half + 1]) / 2;
		}
		nQueries++;
		return ret;
	}
	
	/**
	 * Returns the recorded m.
	 */
	@Override
	public long m() {
		return m;
	}
	
	@Override
	public long nQueries() {
		return nQueries;
	}
	
	/**
	 * Does not work with CountSketch. Should subspecify the interface
	 * @param args
	 */
	public long computeMWithRow() {
		long sum = 0;
		for (int i = 0; i < w; i++) {
			sum += counters[0][i];
		}
		return sum;
	}
}
