package mrmcmax.data_structures.trees;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BinaryPlungeHeap<Key extends Comparable<Key>> implements PlungePriorityQueue<Key> {
	
	private static final int DEFAULT_CAPACITY = 10;
	
	private Entry<Key>[] array;
	private int[] positions;
	private int size;
	
	@SuppressWarnings("unchecked")
	public BinaryPlungeHeap(int n, Key example) {
		array = (Entry<Key>[]) Array.newInstance(new Entry<Key>(null, 0).getClass(), n+1); //First one is not used
		positions = new int[n+1];
		Arrays.fill(positions, -1);
		size = 0;
	}
	
	public BinaryPlungeHeap(Key example) {
		this(DEFAULT_CAPACITY, example);
	}
	
	public void add(Key key, int value) {
		if (size == array.length - 1) duplicateArray();
		//We increment the size already.
		size++;
		array[size] = new Entry<Key>(key, value);
		positions[value] = size;
		refloat(size);
	}
	
	public Entry<Key> peekMin() {
		return array[1];
	}
	
	public Entry<Key> retrieveMin() {
		Entry<Key> min = array[1];
		array[1] = array[size];
		array[size] = null;
		size--;
		if (size > 0)
			plunge(1);
		positions[min.getValue()] = -1;
		return min;
	}
	
	public void plunge(int value, Key newV) {
		int pos = positions[value];
		if (array[pos].getKey().compareTo(newV) < 0) {
			throw new RuntimeException("Increasing a key");
		} else {
			array[pos].setKey(newV);
			refloat(pos);
		}
	}
	
	public Key keyOf(int value) {
		int pos = positions[value];
		if (pos == -1) return null;
		return array[pos].getKey();
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	private void duplicateArray() {
		
	}
	
	private void refloat(int p) {
		int pos = p;
		Entry<Key> node;
		//The node is not root and its parent is bigger -> refloat
		while (p > 1 && array[p].getKey().compareTo(array[p / 2].getKey()) < 0) {
			node = array[p];
			array[p] = array[p / 2];
			positions[array[p].getValue()] = p;
			array[p / 2] = node;
			positions[node.getValue()] = p / 2;
			p = p / 2;
		}
	}
	
	private void plunge(int p) {
		int pos = p;
		boolean finished = false;
		int childPos = p << 1; //*2
		Entry<Key> node = array[p];
		//If it is not a child already (the supposed child is out of range)
		//and it has not finished
		while (childPos <= size && !finished) {
			if (node == null) {
				System.err.println("Node null");
			}
			//If there is a right child and it is smaller than the left child
			//The reason is that the parent has to be smaller than both children
			if (childPos < size && 
					array[childPos + 1].getKey().compareTo(array[childPos].getKey()) < 0) {
				childPos++;
			}
			//If the smallest child is smaller than the father, refloat
			if (array[childPos].getKey().compareTo(node.getKey()) < 0) {
				array[p] = array[childPos];
				positions[array[p].getValue()] = p;
				p = childPos;
				childPos = childPos << 1; //*2
			} else {
				//Otherwise, we're done. Both children are smaller
				finished = true;
			}
		}
		array[p] = node;
		positions[node.getValue()] = p;
	}
}
