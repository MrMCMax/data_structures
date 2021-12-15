package mrmcmax.data_structures.streaming;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class MultiplyShiftStrong32 extends IntegerHashFunction {

	private Iterator<Long> randomLongs;
	private boolean rangeIsAPowerOf2;
	private int rangeExponent;
	
	public MultiplyShiftStrong32(int rangeSize) {
		super((long) 1<<32L, rangeSize);
		if (rangeSize > (long) 1<<32) {
			throw new RuntimeException("Range too big to do operations fast");
		}
		//Universe of size 32 bits
		//We want a random number generator for a and b in \bar{w}
		randomLongs = ThreadLocalRandom.current().longs().iterator(); //This goes till Long.MAX_VALUE, which is 1>>>64 (-1)
		rangeIsAPowerOf2 = Long.bitCount(rangeSize) == 1;
		if (rangeIsAPowerOf2) {
			rangeExponent = Long.numberOfTrailingZeros(rangeSize);
			System.out.println("The range is a power of 2. It is 2 to the " + rangeExponent);
		}
	}
	
	public class MSS32Instance extends IntegerHashFunction.HashFunctionInstance {

		protected long a, b;
		
		public MSS32Instance() {
			this.a = randomLongs.next();
			this.b = randomLongs.next();
		}
		
		@Override
		public long hash(long element) {
			if (rangeIsAPowerOf2) {
				return (a*element + b) >>> (64 - rangeExponent);
			} else {
				return (((a*element + b) >>> 32)*m)>>32;
			}
		}
		
		public long hashWithoutDiv(long element) {
			return (a*element) + b;
		}
		
		public void printParameters() {
			System.out.println("a: " + Long.toUnsignedString(a) + ", b: " + Long.toUnsignedString(b));
		}
	}
	
	@Override
	public HashFunctionInstance getInstance() {
		return new MSS32Instance();
	}

}
