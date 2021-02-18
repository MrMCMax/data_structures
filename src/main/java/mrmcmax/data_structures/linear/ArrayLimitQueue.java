package mrmcmax.data_structures.linear;

import java.lang.reflect.Array;

public class ArrayLimitQueue<T> implements EasyQueue<T>{

	private T[] array;
	
	private int headPos, backPos;
	
	@SuppressWarnings("unchecked")
	public ArrayLimitQueue(Class<T> clazz, int capacity) {
		array = (T[]) Array.newInstance(clazz, capacity);
		headPos = 0;
		backPos = 0;
	}
	
	@Override
	public int size() {
		return backPos - headPos;
	}

	@Override
	public boolean isEmpty() {
		return headPos == backPos;
	}

	@Override
	public boolean contains(Object o) {
		try {
			@SuppressWarnings("unchecked")
			T other = (T) o;
			int j = headPos;
			while (j < backPos && !other.equals(array[j]))
				j++;
			return j != backPos;
		} catch (ClassCastException e) {
			return false;
		}
	}

	public void add(T element) {
		array[backPos++] = element;
	}
	
	public T get() {
		return array[headPos];
	}
	
	public T poll() {
		T ret = array[headPos];
		array[headPos++] = null;
		return ret;
	}
	
	public void reset() {
		headPos = backPos = 0;
	}
}
