package mrmcmax.data_structures.streaming;

public class SignHashFunction extends IntegerHashFunction {

	private MultiplyShiftStrong32 mss32HashFunction;
	
	public SignHashFunction(long universeSize) {
		super(universeSize, 2);
		mss32HashFunction = new MultiplyShiftStrong32(2);
	}
	
	public class SignHashFunctionInstance extends IntegerHashFunction.HashFunctionInstance {

		protected HashFunctionInstance h;
		
		public SignHashFunctionInstance() {
			h = mss32HashFunction.getInstance();
		}
		
		@Override
		public long hash(long element) {
			long hashValue = h.hash(element);
			if (hashValue == 0) {
				return -1L;
			} else {
				return +1L;
			}
		}

		@Override
		public void printParameters() {
			h.printParameters();
		}
		
	}

	@Override
	public HashFunctionInstance getInstance() {
		return new SignHashFunctionInstance();
	}

}
