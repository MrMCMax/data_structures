package mrmcmax.data_structures.streaming;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class MultiplyModPrimeStrong extends IntegerHashFunction {
	
	private long p = -1;
	private Iterator<Long> randomInts;
	
	public MultiplyModPrimeStrong(long universe, long range) {
		super(universe, range);
		if (m > u) {
			throw new RuntimeException("Useless hash function: range is larger than universe");
		}
		p = nextPrime(u);
		randomInts = ThreadLocalRandom.current().longs(0, p).iterator();
	}

	public class MMPStrongInstance extends IntegerHashFunction.HashFunctionInstance {
		
		private long a;
		private long b;
		
		MMPStrongInstance(long a, long b) {
			this.a = a;
			this.b = b;
		}
		
		public long hash(long element) {
			return ((a*element + b) % p) % m;
		}
		
		public void printParameters() {
			System.out.println("a: " + Long.toUnsignedString(a) + ", b: " + Long.toUnsignedString(b));
		}
	}
	
	@Override
	public MMPStrongInstance getInstance() {
		long a = randomInts.next();
		long b = randomInts.next();
		MMPStrongInstance h = new MMPStrongInstance(a, b);
		return h;
	}
	
	/**
	 * Returns the next prime number greater than or equal to u.
	 * @param u
	 * @return
	 */
	public static long nextPrime(long u) {
		return (1L << 89) - 1;
	}
}
