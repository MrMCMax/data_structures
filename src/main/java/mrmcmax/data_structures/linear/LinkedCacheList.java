package mrmcmax.data_structures.linear;

public class LinkedCacheList {
	
	private LinkedCacheList next;
	private int[] array;
	private int position;
	private int size;
	
	public LinkedCacheList(int cacheSize) {
		array = new int[cacheSize];
		position = 0;
		next = null;
		size = 0;
	}
	
	public void add(int e) {
		if (position < array.length) {
			array[position++] = e;
		} else {
			if (next == null) {
				next = new LinkedCacheList(array.length);
			}
			next.add(e);
		}
		size++;
	}
	
	public int get(int index) {
		if (index < array.length) {
			if (index < position) {
				return array[index];
			} else {
				throw new IndexOutOfBoundsException();
			}
		} else {
			if (next == null) throw new IndexOutOfBoundsException();
			else return next.get(index - array.length);
		}
	}
	
	public int size() {
		return size;
	}
}
