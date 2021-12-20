package mrmcmax.data_structures.streaming;

public class ExactSmallCountMin implements CountMinSketch {
	
	protected int w;
	protected int m;
	protected int[] counters;
	
	public ExactSmallCountMin() {
		initialize(2);
	}
	
	public ExactSmallCountMin(int u) {
		initialize(u);
	}
	
	/**
	 * The parameter d will be ignored (we don't need it)
	 */
	public void initialize(int u) {
		counters = new int[u];
	}

	@Override
	public void accept(long element) {
		counters[(int) element]++;
	}

	@Override
	public long queryFrequency(long element) {
		return counters[(int) element];
	}

	@Override
	public long m() {
		// TODO Auto-generated method stub
		return 0;
	}

}
