package mrmcmax.data_structures.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mrmcmax.data_structures.streaming.MultiplyShiftStrong32.MSS32Instance;

public class MultiplyShiftStrong32Steps {
	
	IntegerHashFunction mss32;
	MSS32Instance hash;
	int range;
	int expRange;
	long element;
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
	
	@When("we hash the element {long}")
	public void weHashTheElement(Long int1) {
		element = int1;
		retHash = (int) hash.hash(int1);
	}
	
	@Then("we get the correct value as if we were using BigInteger")
	public void weGetTheCorrectValueAsIfWeWereUsingBigInteger() {
		BigInteger value = BigInteger.valueOf(element);
		long a = hash.a;
		long b = hash.b;
		value = value.multiply(new BigInteger(Long.toUnsignedString(a))).add(new BigInteger(Long.toUnsignedString(b)));
		BigInteger value2 = value.mod(BigInteger.valueOf(2).pow(64));
		value = value.remainder(BigInteger.valueOf(2).pow(64));
		long finalDivide = 1L<<(64-expRange);
		value = value.divide(new BigInteger(Long.toUnsignedString(finalDivide)));
		assertEquals(retHash, value.intValue(), "Expected " + value.intValue() + " but found " + retHash);
	}
}

