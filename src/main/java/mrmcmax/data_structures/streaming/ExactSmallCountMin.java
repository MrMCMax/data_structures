package mrmcmax.data_structures.streaming;

public class ExactSmallCountMin implements CountMinSketch {
	
	protected int u;
	protected int intervalBits;
	protected long m;
	protected long[] counters;
	protected long nQueries;
	
	public ExactSmallCountMin() {
		initialize(2);
	}
	
	public ExactSmallCountMin(int u, int intervalBits) {
		initialize(u);
		this.intervalBits = intervalBits;
	}
	
	/**
	 * The parameter d will be ignored (we don't need it)
	 */
	public void initialize(int u) {
		//I can guarantee there will not be an array of size greater than Integer.MAX_SIZE in these settings
		counters = new long[u];
		this.u = u;
	}

	protected int ID(long element) {
		return (int) (element >>> intervalBits);
	}
	
	@Override
	public void update(long element) {
		int ID = ID(element);
		counters[ID]++;
		m++;
	}

	@Override
	public long queryFrequency(long element) {
		nQueries++;
		int ID = ID(element);
		if (ID == -1) {
			System.out.println("Problems");
		}
		return counters[ID];
	}

	@Override
	public long m() {
		// TODO Auto-generated method stub
		return m;
	}

	@Override
	public long nQueries() {
		return nQueries;
	}
	
	@Override
	public long computeMWithRow() {
		long sum = 0;
		for (int i = 0; i < counters.length; i++) {
			sum += counters[i];
		}
		return sum;
	}

}
