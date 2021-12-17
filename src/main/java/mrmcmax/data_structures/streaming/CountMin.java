package mrmcmax.data_structures.streaming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import mrmcmax.data_structures.streaming.IntegerHashFunction.HashFunctionInstance;

public class CountMin {
	
	protected int d; //Number of functions
	protected int w; //Range of the hash functions
	protected long u; //Universe size
	protected long m = 0; //Recorded number of elements
	
	protected IntegerHashFunction strongHashFunction;
	protected HashFunctionInstance[] hashes;
	
	protected int nQueries = 0; //For debugging purposes: Number of frequency queries performed till now
	
	protected int counters[][]; //data structure of counters!
	
	public CountMin() {
		//dude
		initialize((long) 1<<32, 64, 8);
	}
	
	public CountMin(long universeSize, double epsilon, double delta) {
		int w = (int) Math.ceil(2 / epsilon);
		int d = (int) Math.ceil(Math.log(1/delta) / Math.log(2));
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
		hashes = new HashFunctionInstance[d];
		for (int i = 0; i < d; i++) {
			hashes[i] = strongHashFunction.getInstance();
		}
		counters = new int[d][w]; //Java initializes them to zeros
	}
	
	/**
	 * Processes an element of the stream
	 * @param element
	 */
	public void accept(long element) {
		int hash;
		for (int i = 0; i < d; i++) {
			hash = (int) hashes[i].hash(element);
			counters[i][hash]++;
		}
		m++;
	}
	
	/**
	 * Returns the estimated frequency of an element.
	 * @param element the element to estimate the frequency of
	 * @return the estimated frequency.
	 */
	public int queryFrequency(int element) {
		int hash = (int) hashes[0].hash(element);
		int countMin = counters[0][hash];
		for (int i = 1; i < d; i++) {
			hash = (int) hashes[i].hash(element);
			if (counters[i][hash] < countMin) {
				countMin = counters[i][hash];
			}
		}
		nQueries++;
		return countMin;
	}
	
	/**
	 * Returns the recorded m.
	 */
	public long m() {
		return m;
	}
	
	/**
	 * For debugging purposes, adds all the frequencies up, to m.
	 * @param args
	 */
	public long computeMWithRow() {
		long sum = 0;
		for (int i = 0; i < w; i++) {
			sum += counters[0][i];
		}
		return sum;
	}
	
	public static void main(String[] args) {
		CountMin cmin = new CountMin();
		for (int i = 0; i < cmin.d; i++) {
			cmin.hashes[i].printParameters();
		}
		//Test stream with 512 different integers and 10000 in total
		//Countmin has hash functions with range 64
		Iterator<Integer> randomInts = ThreadLocalRandom.current().ints(0, 512).iterator();
		int n50 = 0, n60 = 0, n101 = 0; //test numbers
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			int random = randomInts.next();
			cmin.accept(random);
			if (random == 50) n50++;
			else if (random == 60) n60++;
			else if (random == 101) n101++;
		}
		long finalTime = System.currentTimeMillis() - t1;
		System.out.println("Generated stream and hashed in " + finalTime + " ms");
		System.out.println("Real frequency of 50: " + n50 + ", estimated frequency: " + cmin.queryFrequency(50));
		System.out.println("Real frequency of 60: " + n60 + ", estimated frequency: " + cmin.queryFrequency(60));
		System.out.println("Real frequency of 101: " + n101 + ", estimated frequency: " + cmin.queryFrequency(101));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cmin.d; i++) {
			sb.append("[");
			sb.append(cmin.counters[i][0]);
			for (int j = 1; j < cmin.w; j++) {
				sb.append(", ").append(cmin.counters[i][j]);
			}
			sb.append("]\n");
		}
		System.out.println(sb.toString());
		System.out.println("Goodbye!");
	}
}
