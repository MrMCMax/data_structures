package mrmcmax.data_structures.streaming;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class StreamTest {
	
	public static final int UNIVERSE = 1<<16;
	public static final int RANGE = 1<<10;
	public static final int MULTIPLIER = 4;
	public static final int STREAM_LENGTH = 500000; //to change to 500000
	
	public static final int HH1 = 2;
	public static final int HH2 = 3;
	
	private static int rr5 = 0; //for the heavy hitters generator
	
	private static Iterator<Integer> randomUniverseInts;
	//For the small subset universe test
	public static final int UNIVERSE_SUBSET_SIZE = 1<<9;
	public static int[] subset;
	public static Iterator<Integer> randomIndexInts;
	
	/**
	 * Stream iterator generator for the heavy hitters stream:
	 * Generates a stream of elements where elements HH1 and HH2 are each 40% of the stream, 
	 * and the 20% rest are random ints from the universe specified in this class (not containing HH1 or HH2)
	 * @param previous
	 * @return
	 */
	public static int heavyHittersGenerator() {
		int element = -1;
		if (rr5 == 0 || rr5 == 2) {
			element = HH1;
		} else if (rr5 == 1 || rr5 == 3) {
			element = HH2;
		} else {
			do {
				element = randomUniverseInts.next();
			} while (element == HH1 || element == HH2);
		}
		rr5++;
		if (rr5 == 5) rr5 = 0; //Mod 5
		return element;
	}
	
	/**
	 * Stream iterator generator for the heavy hitters stream:
	 * Generates a stream of elements where elements HH1 and HH2 are each 40% of the stream, 
	 * and the 20% rest are random ints from a very small subset of the universe
	 * @param previous
	 * @return
	 */
	public static int smallHeavyHittersGenerator() {
		int element = -1;
		if (rr5 == 0 || rr5 == 2) {
			element = HH1;
		} else if (rr5 == 1 || rr5 == 3) {
			element = HH2;
		} else {
			do {
				element = subset[randomIndexInts.next()];
			} while (element == HH1 || element == HH2);
		}
		rr5++;
		if (rr5 == 5) rr5 = 0; //Mod 5
		return element;
	}
	
	public static Stream<Integer> smallHeavyHittersStream() {
		//We create a small subset of the universe
		subset = new int[UNIVERSE_SUBSET_SIZE];
		for (int i = 0; i < UNIVERSE_SUBSET_SIZE; i++) {
			do {
				subset[i] = randomUniverseInts.next();
			} while (subset[i] == HH1 || subset[i] == HH2);
		}
		randomIndexInts = ThreadLocalRandom.current().
				ints(0, UNIVERSE_SUBSET_SIZE).iterator();
		return Stream.generate(StreamTest::smallHeavyHittersGenerator).limit(STREAM_LENGTH);	
	}
	
	/**
	 * Generates a random element from the universe.
	 * @return
	 */
	public static int randomStream() {
		return randomUniverseInts.next();
	}
	
	public static void main(String[] args) {
		randomUniverseInts = ThreadLocalRandom.current().ints(0, UNIVERSE).iterator();
		System.out.println("Universe: " + UNIVERSE);
		test2();
		test1();
		test3();
	}
	
	public static void test1() {
		//First test: Heavy hitters 40/40
		System.out.println("HEAVY HITTERS TEST");
		Stream<Integer> stream = Stream.generate(StreamTest::heavyHittersGenerator).limit(STREAM_LENGTH);
		int[] elementsToTest = new int[] {HH1, HH2, randomUniverseInts.next(), randomUniverseInts.next(), randomUniverseInts.next() }; //The last element is arbitrary
		long[][] results = test(stream, STREAM_LENGTH, elementsToTest);
		long[][] estimatedFrequencies = new long[][] { results[0], results[1] };
		printTestResults(elementsToTest, results[2], estimatedFrequencies);
	}
	
	public static void test2() {
		//Second test: Heavy Hitters 40/40 WITH SMALL UNIVERSE TEST
		System.out.println("HEAVY HITTERS WITH SMALL VARIANCE TEST");
		System.out.println("UNIVERSE SUBSET SIZE: " + UNIVERSE_SUBSET_SIZE);
		Stream<Integer> stream = smallHeavyHittersStream();
		int[] elementsToTest = new int[] { HH1, HH2, subset[0], subset[1], subset[2]};
		long[][] results = test(stream, STREAM_LENGTH, elementsToTest);
		long[][] estimatedFrequencies = new long[][] {results[0], results[1]};
		printTestResults(elementsToTest, results[2], estimatedFrequencies);
	}
	
	public static void test3() {
		System.out.println("RANDOM STREAM TEST");
		Stream<Integer> stream = Stream.generate(StreamTest::randomStream).limit(STREAM_LENGTH);
		int[] elementsToTest = new int[] { subset[0], subset[1], subset[2], subset[3], subset[4] }; //The last element is arbitrary
		long[][] results = test(stream, STREAM_LENGTH, elementsToTest);
		long[][] estimatedFrequencies = new long[][] { results[0], results[1] };
		printTestResults(elementsToTest, results[2], estimatedFrequencies);
	}
	
	public static void printTestResults(int[] elementsToTest, long[] realFrequencies, long[][] estimatedFrequencies) {
		StringBuilder firstLine = new StringBuilder("\t\t");
		for (int i = 0; i < elementsToTest.length; i++) {
			firstLine.append("\t").append("f(").append(elementsToTest[i]).append(")=").append(realFrequencies[i]);
		}
		System.out.println(firstLine);
		StringBuilder secondLine = new StringBuilder("CountMin");
		StringBuilder thirdLine = new StringBuilder("CountSketch");
		for (int i = 0; i < elementsToTest.length; i++) {
			secondLine.append("\t\t").append(estimatedFrequencies[0][i]); //CMin
			thirdLine.append("\t\t").append(estimatedFrequencies[1][i]); //CSketch
		}
		System.out.println(secondLine);
		System.out.println(thirdLine);
	}
	
	public static long[][] test(Stream<Integer> stream, int streamSize, int[] elementsToTest) {
		long[] CMinResults = new long[streamSize];
		long[] CSketchResults = new long[streamSize];
		long[] elementsToTestFrequency = new long[streamSize];
		FrequencySketch countMinSketch = new CountMin();
		FrequencySketch countSketch = new CountSketch();
		countMinSketch.initialize(UNIVERSE, RANGE, MULTIPLIER);
		countSketch.initialize(UNIVERSE, RANGE, MULTIPLIER);
		//Exact frequencies: This would not be possible in a real-world scenario
		long[] frequencies = new long[UNIVERSE];
		//Stream pass
		stream.forEach(element -> {
			for (int i = 0; i < elementsToTest.length; i++) {
				if (element == elementsToTest[i]) {
					elementsToTestFrequency[i] += 1;
				}
			}
			countMinSketch.update(element);
			countSketch.update(element);
			frequencies[element]++;
		});
		//Queries
		for (int i = 0; i < elementsToTest.length; i++) {
			CMinResults[i] = countMinSketch.queryFrequency(elementsToTest[i]);
			CSketchResults[i] = countSketch.queryFrequency(elementsToTest[i]);
		}
		//L2 norm: This would not be possible in a real-world scenario
		long l2 = 0;
		for (int i = 0; i < UNIVERSE; i++) {
			l2 += frequencies[i]*frequencies[i];
		}
		double L2Norm = Math.sqrt(l2);
		System.out.println("m: " + countMinSketch.computeMWithRow());
		System.out.println("w: " + ((CountMin) countMinSketch).w);
		System.out.println("Multiplier d: " +((CountMin) countMinSketch).d);
		System.out.println("L2 norm: " + L2Norm);
		return new long[][] {CMinResults, CSketchResults, elementsToTestFrequency};
	}
}
