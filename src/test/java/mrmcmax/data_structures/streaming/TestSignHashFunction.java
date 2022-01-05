package mrmcmax.data_structures.streaming;

import mrmcmax.data_structures.streaming.IntegerHashFunction.HashFunctionInstance;

public class TestSignHashFunction {
	public static void main(String[] args) {
		int universe = 1<<16;
		IntegerHashFunction shf = new SignHashFunction(universe);
		HashFunctionInstance h1 = shf.getInstance();
		for (int i = 3*universe / 4; i < universe; i++) {
			System.out.println(h1.hash(i));
		}
	}
}
