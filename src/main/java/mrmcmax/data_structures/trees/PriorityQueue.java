package mrmcmax.data_structures.trees;

public interface PriorityQueue<E extends Comparable<E>> {
	boolean isEmpty();
	
	E getMin();
	
	void insert(E e);
	
	E deleteMin();
}
