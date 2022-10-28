package mrmcmax.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SortUtils {

	/**
	 * Selects the k-th biggest element of the array in expected linear time, worst case O(n^2).
	 * @param array the array
	 * @param k the ordered position of the element to return
	 * @return the k-th biggest element in the array
	 */
	public static long select(long[] array, int k) {
		return select(array, array.length, k);
	}
	
	/**
	 * Select recursive method.
	 * @param array
	 * @param end EXCLUSIVE
	 * @param k
	 * @return
	 */
	protected static int select(long[] array, int begin, int k) {
		int pivotPos = ThreadLocalRandom.current().nextInt(begin, array.length);
		long pivot = array[pivotPos];
		long[] arrayL = new long[] {1L, 2L, 3L, 4L};
		
		return 0;
	}
	
	/**
	 * Modifies the array in such a way that, if k is the return value,
	 * [begin, k) has elements strictly smaller than array[pivotPos], and 
	 * [k, end) has elements greater than or equal than array[pivotPos].
	 * @param array
	 * @param begin
	 * @param end
	 * @param pivotPos the position of the pivot
	 * @return the index of the partition
	 */
	protected static int hoarePartition(long[] array, int begin, int end, int pivotPos) {
		long pivot = array[pivotPos];
		//Hide pivot in the beginning
		long aux = array[begin];
		array[begin] = pivot;
		array[pivotPos] = aux;
		int i = begin;
		int j = end;
		while (true) {
			do {
				j--;
			} while (array[j] > pivot);
			while (array[i] < pivot) {
				i++;
			}
			if (i < j) {
				//Swap i and j
				aux = array[i];
				array[i] = array[j];
				array[j] = aux;
				i++;
			} else {
				return j + 1;
			}
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 40; i++) {
			System.out.println(ThreadLocalRandom.current().nextInt(0, 11));
		}
	}
}
