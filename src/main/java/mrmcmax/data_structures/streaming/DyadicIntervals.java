package mrmcmax.data_structures.streaming;

import java.util.LinkedList;
import java.util.List;

import mrmcmax.utils.BinaryUtils;

public class DyadicIntervals {
	
	protected int levels;
	protected FrequencySketch[] cmins;
	protected long u;
	
	public DyadicIntervals() {	} //To be used with initialize
	
	public DyadicIntervals(long u, int w, int d) {
		initialize(u, w, d);
	}
	
	public void initialize(long u, int w, int d) {
		this.levels = BinaryUtils.ceil_log_2_int(u); //ceil ( log_2 (u) )
		cmins = new FrequencySketch[levels];
		for (int i = 0; i < levels; i++) {
			if (1L<<(i+1) <= w) {
				//The universe can be fitted into an array of size <=w
				//i is the level. The universe is 2^(i+1)
				FrequencySketch ecmin = new ExactSmallCountMin(1<<(i+1), intervalLengthInBits(i, levels));
				cmins[i] = ecmin;
			} else {
				CountMin cmin = new CountMin();
				cmin.initialize(u, w, d);
				cmins[i] = cmin;
			}
		}
		this.u = u;
	}
	
	/**
	 * Returns the log_2 of the length of a dyadic interval at a given level, defined by the size of the universe.
	 * @param level
	 * @return
	 */
	public static int intervalLengthInBits(int level, int levels) {
		return levels - (level + 1);
	}
	
	private int intervalLengthInBits(int level) {
		return intervalLengthInBits(level, this.levels);
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
		return identifier(level, element, this.levels);
	}
	
	public static long identifier(int level, long element, int levels) {
		int nBits = intervalLengthInBits(level, levels);
		return (element >>> (nBits)) << nBits;
	}

	public void accept(long e1) {
		for (int level = 0; level < levels; level++) {
			//In each level, we compute the identifier for e1 and add it as such
			long id = identifier(level, e1);
			cmins[level].update(id);
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
		FrequencySketch cmin = cmins[level];
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
		long toAdd = 1L<<(intervalLengthInBits(currLevel + 1));
		return id + toAdd;
	}
	
	/**
	 * Query the frequency of the elements between start and end, both inclusive.
	 * @param start
	 * @param end
	 * @return
	 */
	public long rangeQuery(long start, long end) {
		long[] splittingNode = findSplittingNode(start, end);
		long ID = splittingNode[0];
		int level = (int) splittingNode[1];
		if (ID == start && ID + intervalLengthInBits(level) - 1 == end) {
			//The whole splitting node is the range. This also covers the full range [n]
			if (level == -1) return cmins[0].m();
			return cmins[level].queryFrequency(ID);
		}
		//Else, we have work to do (branching)
		long sum = predecessorBranch(splittingNode[0], (int) splittingNode[1], start, end);
		sum += successorBranch(splittingNode[0], (int) splittingNode[1], start, end);
		return sum;
	}

	protected long[] findSplittingNode(long start, long end) {
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
	
	protected long predecessorBranch(long ID, int level, long start, long end) {
		//If start is 0, the node on the left of the splitting node is full
		if (start == 0) {
			return cmins[level + 1].queryFrequency(ID);
		} else {
			//We have to search for the predecessor and output all the values in the right nodes.
			//Literally sum all of them
			long predecessor = start - 1;
			long sum = 0;
			//The level we get is the one of the splitting node
			level++;
			long secondChild = secondChild(ID, level);
			while (level != levels - 1) { //If we are not on a leaf
				level++; //Check children
				if (predecessor < secondChild) {
					//Add the node on the right. Already increment level
					sum += cmins[level].queryFrequency(secondChild);
					//System.out.println("Adding in predecessor the node " + secondChild + " on level " + level);
					//Continue to the left
				} else {
					//Just recurse to the right. Don't add node
					ID = secondChild;
				}
				secondChild = secondChild(ID, level);
			}
			//We are on the leaf of a predecessor. Nothing to do anymore
			return sum;
		}
	}
	
	protected long successorBranch(long ID, int level, long start, long end) {
		long secondChild = secondChild(ID, level);
		//If end is the last element, the node on the right is full
		if (end == u-1) {
			return cmins[level + 1].queryFrequency(secondChild);
		} else {
			//We have to search for the successor and add nodes on the left
			long successor = end + 1;
			long sum = 0;
			//The level we get is that of the splitting node
			level++;
			//To set ourselves in the right node
			ID = secondChild;
			secondChild = secondChild(ID, level); //To enter the while
			while (level != levels - 1) { //If we are not on a leaf, we might have to add nodes
				level++;
				if (successor >= secondChild) {
					//We have to add the node on the left. We already increment the level
					sum += cmins[level].queryFrequency(ID);
					//System.out.println("Adding in successor the node " + ID + " on level " + level);
					//And recurse on the right
					ID = secondChild;
				}
				//else we just recurse to the left (we increased the level already. ID is the same
				secondChild = secondChild(ID, level);
			}
			//We have reached the leaf of the predecessor. Nothing else to do
			return sum;
		}
	}
}






