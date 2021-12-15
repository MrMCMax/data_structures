package mrmcmax.data_structures.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mrmcmax.data_structures.streaming.MultiplyShiftStrong32.MSS32Instance;

public class MultiplyShiftStrong32Steps {

	IntegerHashFunction mss32;
	MSS32Instance hash;
	int range;
	int expRange;
	long[] longs;
	int[] retHashes;
	int retHash;

	@Given("a hash function MultiplyShiftStrong32 with range two to the {int}")
	public void aHashFunctionMultiplyShiftStrong32WithRangeTwoToThe(Integer int1) {
		range = 1 << int1;
		expRange = int1;
		mss32 = new MultiplyShiftStrong32(range);
	}

	@Given("one of its instances")
	public void oneOfItsInstances() {
		hash = (MSS32Instance) mss32.getInstance();
	}

	@When("we hash {int} random elements")
	public void weHashRandomElements(Integer int1) {
		retHashes = new int[int1];
		longs = new long[int1];
		Iterator<Long> randomLongs = ThreadLocalRandom.current().longs(int1).iterator();
		for (int i = 0; i < int1; i++) {
			longs[i] = randomLongs.next();
			retHashes[i] = (int) hash.hash(longs[i]);
		}
	}

	@Then("we get the correct values as if we were using BigInteger")
	public void weGetTheCorrectValuesAsIfWeWereUsingBigInteger() {
		for (int i = 0; i < longs.length; i++) {
			long element = longs[i];
			int retHash = retHashes[i];
			BigInteger value = new BigInteger(Long.toUnsignedString(element));
			long a = hash.a;
			long b = hash.b;
			value = value.multiply(new BigInteger(Long.toUnsignedString(a)))
					.add(new BigInteger(Long.toUnsignedString(b)));
			BigInteger value2 = value.mod(BigInteger.valueOf(2).pow(64));
			value = value.remainder(BigInteger.valueOf(2).pow(64));
			long finalDivide = 1L << (64 - expRange);
			value = value.divide(new BigInteger(Long.toUnsignedString(finalDivide)));
			assertEquals(retHash, value.intValue(), "Expected " + value.intValue() + " but found " + retHash);
		}
	}
}
