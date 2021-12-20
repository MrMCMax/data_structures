package mrmcmax.data_structures.utils;

public class BinaryUtils {

	/**
	 * Calculates efficiently the ceiling of the log in base 2 of an integer (long) 
	 * @param n the argument
	 * @return ceil(log_2(n))
	 */
	public static int ceil_log_2_int(long n) {
		return 64 - Long.numberOfLeadingZeros(n - 1);
	}
	
	/**
	 * Calculates efficiently the ceiling of the log in base 2 of an integer (long) 
	 * @param n the argument
	 * @return floor(log_2(n))
	 */
	public static int floor_log_2_int(long n) {
		return 63 - Long.numberOfLeadingZeros(n);
	}
}
