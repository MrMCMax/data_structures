package mrmcmax.data_structures.streaming;

import java.util.LinkedList;
import java.util.List;

public class DyadicIntervalsCountMin {
	
	protected int levels;
	protected CountMin[] cmins;
	protected long u;
	
	public DyadicIntervalsCountMin() {	} //To be used with initialize
	
	public DyadicIntervalsCountMin(long u, int w, int d) {
		initialize(u, w, d);
	}
	
	public void initialize(long u, int w, int d) {
		this.levels = 64 - Long.numberOfLeadingZeros(u - 1); //ceil ( log_2 (u) )
		cmins = new CountMin[levels];
		for (int i = 0; i < levels; i++) {
			cmins[i] = new CountMin();
			cmins[i].initialize(u, w, d);
		}
		this.u = u;
	}
	
	/**
	 * Returns the log_2 of the length of a dyadic interval at a given level, defined by the size of the universe.
	 * @param level
	 * @return
	 */
	public int intervalLengthInBits(int level) {
		return levels - (level + 1);
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
		int nBits = intervalLengthInBits(level);
		return (element >>> (nBits)) << nBits;
	}

	public void accept(long e1) {
		for (int level = 0; level < levels; level++) {
			//In each level, we compute the identifier for e1 and add it as such
			long id = identifier(level, e1);
			cmins[level].accept(id);
		}
	}

	public List<Long> heavyHitters(Integer k) {
		List<Long> possibleHeavyHitters = new LinkedList<Long>();
		heavyHitters(0, 0, k, possibleHeavyHitters); //First interval starts at 0
		long secondChild = secondChild(0, -1);
		heavyHitters(0, secondChild, k, possibleHeavyHitters); //Second interval starts at n/2
		return possibleHeavyHitters;
	}
	
	protected void heavyHitters(int level, long idToQuery, int k, List<Long> heavyHitters) {
		CountMin cmin = cmins[level];
		long m  = cmin.m();
		if (cmin.queryFrequency(idToQuery) >= m/k) {
			//Base case: we have found a heavy hitter
			if (level == levels - 1) {
				heavyHitters.add(idToQuery);
			}
			//General case: this interval might contain heavy hitters, query both children
			else {
				heavyHitters(level + 1, idToQuery, k, heavyHitters);
				long secondChild = secondChild(idToQuery, level);
				heavyHitters(level + 1, secondChild, k, heavyHitters);
			}
		}
	}
	
	protected long secondChild(long id, int currLevel) {
		int toAdd = 1<<(intervalLengthInBits(currLevel + 1));
		return id + toAdd;
	}

	public long[] findSplittingNode(int start, int end) {
		long ID = 0;
		int level = -1;
		if (start > end) throw new RuntimeException("Empty range");
		if (start == end) {
			ID = start;
			level = levels - 1;
		} else {
			long secondChild = secondChild(0, -1);
			while ((end < secondChild) || (start > secondChild)) {
				if (start > secondChild) {
					ID = secondChild;
				} //In the other case we don't have to change the ID, because we are recursing left
				level++;
				secondChild = secondChild(ID, level);
			}
		}
		return new long[] {ID, level};
	}
	
	public static void main(String[] args) {
		DyadicIntervalsCountMin dy = new DyadicIntervalsCountMin(16, 4, 4);
		dy.accept(8);
		dy.accept(10);
		for (int i = 0; i < 8; i++) {
			dy.accept(9);
		}
		for (int i = 0; i < 8; i++) {
			dy.accept(2);
		}
		dy.accept(3);
	}
}






