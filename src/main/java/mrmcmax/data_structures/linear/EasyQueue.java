package mrmcmax.data_structures.linear;

public interface EasyQueue<T> {
	void add(T element);
	
	T get();
	
	T poll();
	
	int size();
	
	boolean isEmpty();
	
	boolean contains(Object o);
	
	void reset();
}
