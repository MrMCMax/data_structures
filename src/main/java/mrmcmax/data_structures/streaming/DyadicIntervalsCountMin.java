package mrmcmax.data_structures.streaming;

public class DyadicIntervalsCountMin {
	
	protected int levels;
	protected CountMin[] cmins;
	
	public DyadicIntervalsCountMin(long u, int w, int d) {
		this.levels = 64 - Long.numberOfLeadingZeros(u - 1); //ceil ( log_2 (u) )
		cmins = new CountMin[levels];
		for (int i = 0; i < levels; i++) {
			cmins[i] = new CountMin();
			cmins[i].initialize(u, w, d);
		}
	}
	
	/**
	 * Returns the identifier for element in the countMin at the specified level,
	 * following the dyadic intervals.
	 * @param level
	 * @param element
	 * @return
	 */
	public long identifier(int level, long element) {
		//We have to set to zero the LSB bits. We can do a right shift and then a left shift.
		int nBits = levels - (level + 1);
		return (element >>> (nBits)) << nBits;
	}
}
